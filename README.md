# DWSurvey 调问表单问卷系统（Spring Boot的实现）

[![star](https://gitee.com/wkeyuan/DWSurvey/badge/star.svg?theme=dark)](https://gitee.com/wkeyuan/DWSurvey/stargazers)
[![fork](https://gitee.com/wkeyuan/DWSurvey/badge/fork.svg?theme=dark)](https://gitee.com/wkeyuan/DWSurvey/members)
![GitHub Repo stars](https://img.shields.io/github/stars/wkeyuan/DWSurvey?style=social)
![GitHub forks](https://img.shields.io/github/forks/wkeyuan/DWSurvey?style=social)

### 全新的企业版已经发布，前后端分别采用 Ant design、Spring Boot，详情可参考官网。

### 社区版已经升级到Spring Boot

其它版本也在更新计划中，敬请期待。

关注我们更新，记得Star一下哦。 

DWSurvey是一款方便、高效、稳定的调研问卷系统，一款基于 JAVA WEB 的开源问卷表单系统。

![输入图片说明](https://images.gitee.com/uploads/images/2021/0416/132431_5d99a296_1401416.gif "dwsurvey-2.gif")

## 浏览器兼容
### 支持现代浏览器和IE6
| IE / Edge | Firefox | Chrome | Safari | Opera |
| :-----| ----: | :----: | :----: | :----: | 
| IE6+,Edge | 支持 | 支持 | 支持 | 支持 | 

## 演示地址

社区版服务，源码全量开源，可独立部署，可二次开发。

 官网地址：[http://www.diaowen.net](http://www.diaowen.net)

企业版服务，源码全量开源，前后端分离，可独立部署，可二次开发。

企业版地址：[http://ent.surveyform.cn](http://ent.surveyform.cn)

在线平台服务，提供标准API，可快速集成到自己应用中，功能更丰富，不需要部署，可直接发布问卷进行数据收集。

在线服务地址：[https://www.surveyform.cn](https://www.surveyform.cn)

这是基于Spring Boot开发的实现. Struts2老方案在master_struts2分支.

下面是如何使用Spring Boot方案的快速指南。

## 本地开发

1、下载代码后，用IDEA打开。

2、然后在idea里面可以直接启动。

4、浏览器访问 localhost:8080

## 数据库脚本、数据初始化

数据库脚本在resources/sql/目录下的dwsurvey.sql数据库脚本文件

说明：升级后的数据库与老版本兼容

## 编译打包

进入 `cd DWSurvey`:

```bash
mvn package
```

配置文件地址

    resources/application-***.yml

	#database settings
	datasource:
    url: jdbc:mysql://localhost:3306/dwsurvey_21test?useUnicode=true&characterEncoding=utf8
    username: root
    password: 123456,.

    分别修改```url、username、password```

### 部署前创建并初始化dwsurvey数据库

创建数据库，并初始化数据库。

    数据库名：dwsurvey

初始化脚本下载地址：

    https://gitee.com/wkeyuan/DWSurvey/blob/master/src/main/resources/conf/sql/


### 启动访问

打成包后，将war包放入tomcat的webapps

配置完成后，启动服务在浏览器中输入如```localhost:8080```相应的地址看到登录页面，表示已经安装成功。

初始账号：```service@diaowen.net``` 密码：```123456```

### 直接下载编译好的程序包 

如果不想自己编译可以直接使用我们已经编译好的war包安装

最新的war包下载可以前往交流QQ ```群1：635994795(满)```，```群2：301105635(满)```, 群3：811287103(可加) （加群时请说明来由）

下载最新的dwsurvey-oss-v***.war（注意看后面的版本号），再考到tomcat wabapps下

打包环境版本：jdk1.8, tomcat8.5.59

外部解压命令：jar xvf dwsurvey-oss-v***.war 

## Docker 方式部署 

在项目wiki里面有docker部署操作说明，省去环境配置的问题，下载直接部署就可以使用。 

## 特色

### 全新体验、流程简单

![pic](http://diaowenwebfile.oss-cn-shenzhen.aliyuncs.com/images/gif/newUi.png)

以一种全新的设计体验，告别繁琐的设计流程，通过简单有趣的方式，轻轻松松完成问卷设计，多种问卷样式模板选择，只为显现更精美的表单问卷.

### 丰富的题型 

丰富的题型支持，通过拖拽即可完成题目选择，并可以随意拖动其位置，还可置入所需图片、企业LOGO、设置答题逻辑，一份优美的问卷就是这么简单。

### 问卷表单静态化

对于问卷表单系统，因为所有的表单字段都是后台数据库来维护，所以对于每一次答卷请求，如果都从后端数据库去取每一题及选项的话，必定会对性能造成不小影响。

所以在发布的表单问卷时会对数据进行的页面静态化，生成一个真实的表单存档。

## 有问题怎么办？

对于调问网问卷系统安装及使用的问题，可以在用户交流群里，向作者或其它同学提问。

调问网交流QQ群 ```群1：635994795(满)```，```群2：301105635(满)```, 群3：811287103(可加)（加群时请说明来由）

微信公众号

<img src="http://www.diaowen.net/images/dw_ewm.png" alt="图片替换文本" width="200" height="200" align="top" />

## 源代码地址

gitee: http://gitee.com/wkeyuan/DWSurvey

github: https://github.com/wkeyuan/DWSurvey

## 开源协议

DWSurvey以通用公共许可证AGPL3.0为开源协议，需要更优质的服务可以购买我们的商业版与企业版！

- - -

## 关于调问网

没事喜欢瞎折腾，业余时间也想做点有意思的事情，同时对产品也比较感兴趣，一翻调研之后就决定来做个问卷系统。
于是便利用业余时间来做这款问卷表单平台，因为在2012的时候，国内的问卷系统都还比较初级，问卷设计流程复杂，页面也不美观。
所以就开始边调研、边画原型、边写前后端代码，最初的想法是一定要好用美观，在这之后的一年里就不断的设计开发，前前后后修改过3个版本。

- - -

## 作者

 柯远 ```keyuan258@gmail.com```

### 谢谢您的支持

 觉得不错就点下右上角的star，随时关注我们的动态，非常感谢！
