FROM maven:3.5-jdk-8-alpine as builder

ARG LOCAL_MAVEN_MIRROR=http://maven.aliyun.com/nexus/content/groups/public/

# used to edit maven settings.xml
RUN apk add --no-cache xmlstarlet
# change default local repository location. parent image set ~/.m2 as volume, so data won't be persisted for following build cmds
RUN xmlstarlet ed --inplace -N 's=http://maven.apache.org/SETTINGS/1.0.0' \
	--subnode '/s:settings' --type elem -n localRepository -v '${user.home}/m2/repository' \
	/usr/share/maven/conf/settings.xml
RUN if test -n "$LOCAL_MAVEN_MIRROR"; then \
	xmlstarlet ed --inplace -N 's=http://maven.apache.org/SETTINGS/1.0.0' \
		--subnode '/s:settings/s:mirrors' --type elem -n mirror -v '' \
		/usr/share/maven/conf/settings.xml \
	&& xmlstarlet ed --inplace -N 's=http://maven.apache.org/SETTINGS/1.0.0' \
		--subnode '/s:settings/s:mirrors/s:mirror' --type elem -n id -v 'custom-mirror' \
		/usr/share/maven/conf/settings.xml \
	&& xmlstarlet ed --inplace -N 's=http://maven.apache.org/SETTINGS/1.0.0' \
		--subnode '/s:settings/s:mirrors/s:mirror' --type elem -n name -v 'custom-mirror' \
		/usr/share/maven/conf/settings.xml \
	&& xmlstarlet ed --inplace -N 's=http://maven.apache.org/SETTINGS/1.0.0' \
		--subnode '/s:settings/s:mirrors/s:mirror' --type elem -n url -v "$LOCAL_MAVEN_MIRROR" \
		/usr/share/maven/conf/settings.xml \
	&& xmlstarlet ed --inplace -N 's=http://maven.apache.org/SETTINGS/1.0.0' \
		--subnode '/s:settings/s:mirrors/s:mirror' --type elem -n mirrorOf -v 'central' \
		/usr/share/maven/conf/settings.xml \
	;fi

# copy lib/ and src/ seperately to leverage cache (lib/ rarely changes)
COPY ./lib /DWSurvey/lib
RUN mvn install:install-file -Dfile=/DWSurvey/lib/QRCode.jar -DgroupId=net.qrcode -DartifactId=qrcode -Dversion=1.0 -Dpackaging=jar
RUN mvn install:install-file -Dfile=/DWSurvey/lib/spssw-1.66.jar -DgroupId=net.spssw -DartifactId=spssw -Dversion=1.66 -Dpackaging=jar
RUN mvn install:install-file -Dfile=/DWSurvey/lib/xssProtect-0.1.jar -DgroupId=net.xssprotect -DartifactId=xssprotest -Dversion=1.0 -Dpackaging=jar

# if pom.xml is not updates, m2/ may be cached as well
COPY pom.xml /DWSurvey/pom.xml
RUN cd /DWSurvey && mvn dependency:resolve

# copy src/ and build, src changes constantly
COPY ./src /DWSurvey/src
RUN cd /DWSurvey \
	&& find /DWSurvey/src -type f -exec chmod 640 {} \; \
	&& mvn install

# ------------------------- 8< -------------------------

# there is a bug with 8.0-jre8-alpine, which was introduced in alpine3.6
# see: https://bugs.alpinelinux.org/issues/7372
# image layers: tomcat:8.0-jre8 -> openjdk:8-jre-alpine -> alpine:3.6
# let's switch to 8.0-jre8-alpine after alpine has fixed the bug (alpine 3.6.3 release)
#FROM tomcat:8.0-jre8-alpine
FROM davidcaste/alpine-tomcat:jre8tomcat8

ENV MYSQL_HOST= MYSQL_PORT=3306 MYSQL_DATABASE= MYSQL_USER= MYSQL_PASSWORD=
ENV ADMIN_EMAIL= ADMIN_PASSWORD=
ENV CONTEXT_ROOT=/

RUN apk add --no-cache mysql-client

COPY --from=builder /DWSurvey/target/diaowen.war /diaowen.war
COPY docker-entry.sh /docker-entry.sh

# TODO this is a dirty hack
VOLUME ["/dwsurvey/WEB-INF/wjHtml", "/dwsurvey/WEB-INF/classes/conf/site"]

EXPOSE 8080

ENTRYPOINT [ "/docker-entry.sh" ]
