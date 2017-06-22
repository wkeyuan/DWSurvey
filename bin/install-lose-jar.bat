@echo [信息] 安装中央仓库缺失jar。
mvn install:install-file -Dfile=../lib/QRCode.jar -DgroupId=net.qrcode -DartifactId=qrcode -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=../lib/spssw-1.66.jar -DgroupId=net.spssw -DartifactId=spssw -Dversion=1.66 -Dpackaging=jar
mvn install:install-file -Dfile=../lib/xssProtect-0.1.jar -DgroupId=net.xssprotect -DartifactId=xssprotest -Dversion=1.0 -Dpackaging=jar