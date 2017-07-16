#!/bin/bash
set -e

WAR_FILE=/diaowen.war
UNPACK_DIR=/dwsurvey
WEBAPP_BASE=$CATALINA_HOME/webapps

# determine context root path
if [[ "x${CONTEXT_ROOT}x" == "xx" || "$CONTEXT_ROOT" == "/" ]]; then
    CONTEXT_PATH=ROOT
else
    CONTEXT_PATH=${CONTEXT_ROOT}
fi
WEBAPP_DIR=${WEBAPP_BASE}/${CONTEXT_PATH}
CONF_DIR=${WEBAPP_DIR}/WEB-INF/classes/conf

require_env() {
    local ENV_NAME="$1"
    # if docker provides secret, secret wins env
    if [[ -r "/run/secrets/$ENV_NAME" ]]; then
        eval $ENV_NAME=$(cat /run/secrets/$ENV_NAME)
    fi

    eval ENV_VALUE=\$$ENV_NAME

    if [ -z "$ENV_VALUE" ]; then
        echo "Missing required environment variable: $ENV_NAME"
        exit 1
    fi
}

init_run() {
    # check variables required for initialization
    require_env MYSQL_HOST
    require_env MYSQL_PORT
    require_env MYSQL_DATABASE
    require_env MYSQL_USER
    require_env MYSQL_PASSWORD

    echo "Unpacking war ..."
    mkdir -p "$UNPACK_DIR"
    unzip -q -x "$WAR_FILE" -d "$UNPACK_DIR" \
             -x "WEB-INF/wjHtml/*" \
             -x "WEB-INF/classes/conf/site/*"
    ln -snf "$UNPACK_DIR" "$WEBAPP_DIR"

    echo "Configuring dwsurvey ..."
    sed -i "s^jdbc.url=.*\$jdbc.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE}?useUnicode=true\&characterEncoding=utf8g;
            s^jdbc.username=.*\$jdbc.username=${MYSQL_USER}g;
            s^jdbc.password=.*\$jdbc.password=${MYSQL_PASSWORD}g" \
                ${CONF_DIR}/application.properties
    migrate
}

migrate() {
    # TODO We should use some migration mechanism.
    # For now, let's do it in a dirty hacky way.
    require_env ADMIN_EMAIL
    require_env ADMIN_PASSWORD

    # DIRTY hack, check if table t_user exist, if exist, skip migration
    echo "Checking if table t_user exist ..."
    if [[ $(mysql -N -s -h "$MYSQL_HOST" -P "$MYSQL_PORT" -u "$MYSQL_USER" --password="$MYSQL_PASSWORD" -e \
             "SELECT count(*) FROM information_schema.TABLES
                WHERE (TABLE_SCHEMA='${MYSQL_DATABASE}') AND (TABLE_NAME='t_user')") \
            -eq 1 ]]; then
        echo "table t_user exists, skipping migration."
        return
    fi

    local MIGRATION_FILE=/tmp/migrate.sql
    cp ${WEBAPP_DIR}/WEB-INF/classes/conf/sql/dwsurvey.sql $MIGRATION_FILE

    # remove "CREATE DATABASE" clouse, db should be created outside, so no root db account required
    sed -i '/CREATE DATABASE/d' $MIGRATION_FILE
    # use the specified database name
    sed -i "s/USE \`dwsurvey\`;/USE \`${MYSQL_DATABASE}\`;/g" $MIGRATION_FILE
    # remove default user
    sed -i '/INSERT INTO `t_user`/d' $MIGRATION_FILE
    # remove example survey (just don't INSERT anything), meaningless for production usage
    sed -i '/INSERT INTO/d' $MIGRATION_FILE
    # apply migration file
    echo "Applying migration ..."
    mysql -h "$MYSQL_HOST" -P "$MYSQL_PORT" -u "$MYSQL_USER" --password="$MYSQL_PASSWORD" < $MIGRATION_FILE

    rm -f "$MIGRATION_FILE"

    # create admin user
    local ADMIN_LOGIN=${ADMIN_EMAIL%%@*} # use email prefix for login
    local ADMIN_SHA_PASSWORD=$(echo -n "$ADMIN_PASSWORD" | sha1sum | cut -d' ' -f 1)
    echo "Creating admin user ..."
    mysql -h "$MYSQL_HOST" -P "$MYSQL_PORT" -u "$MYSQL_USER" --password="$MYSQL_PASSWORD" "$MYSQL_DATABASE" -e \
        "INSERT INTO \`t_user\`
                       (id, email,         login_name,     name,            sha_password,         status, version)
                VALUES (1, '$ADMIN_EMAIL', '$ADMIN_LOGIN', '$ADMIN_LOGIN', '$ADMIN_SHA_PASSWORD', 1, 1);"
}

# if webapp directory does not exist, it's first run
if [ ! -d "$WEBAPP_DIR" ]; then
    init_run
fi

# On some boxes with low entropy, tomcat will stuck at start up. It often happens with docker-machine VMs on macos and cloud VM hosts.
# The dirty hack is to use /dev/urandom as entropy source, it is not recommemded as it reduces security.
# See: https://wiki.apache.org/tomcat/HowTo/FasterStartUp#Entropy_Source
export JAVA_OPTS="-Djava.security.egd=file:///dev/urandom $JAVA_OPTS"

# start tomcat
if [ -z "$@" ]; then
    exec $CATALINA_HOME/bin/catalina.sh run
else
    exec "$@"
fi
