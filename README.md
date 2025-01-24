# 调问开源问卷系统（DWSurvey）

    此代码仓库为基于 Spring Boot、Vue 实现的后端仓库

[![star](https://gitee.com/wkeyuan/DWSurvey/badge/star.svg?theme=dark)](https://gitee.com/wkeyuan/DWSurvey/stargazers)
[![fork](https://gitee.com/wkeyuan/DWSurvey/badge/fork.svg?theme=dark)](https://gitee.com/wkeyuan/DWSurvey/members)
![GitHub Repo stars](https://img.shields.io/github/stars/wkeyuan/DWSurvey?style=social)
![GitHub forks](https://img.shields.io/github/forks/wkeyuan/DWSurvey?style=social)

DWSurvey是一款简单、高效、成熟、稳定、专业的开源问卷系统，自2012年启动至今经过9年的积累，已经形成一套完善的基于 JAVA WEB 的开源问卷表单解决方案。

<div style="padding: 10px;border-left:solid 6px #278bee;background: #f3f5f7;font-size: 16px;color: #2c3e50;">
<div style="font-weight: 600;padding: 10px;color: #278bee;"> 🎉🎉🎉  企业版 </div>
<div>
全新的 <strong>企业版</strong> 已经发布，前后端分别采用 React（Ant design）、VUE（Element）、Spring Boot，详情可前往体验
<p>调问网官网地址：<a href="https://www.diaowen.net">https://www.diaowen.net</a></p>
<p><strong>企业版V8新Vue3版体验地址</strong></p>
<p><strong>V8新版本：<a href="https://demo.diaowen.net">https://demo.diaowen.net</a></strong></p>
<p><strong>企业版V6新Vue2版体验地址</strong></p>
<p><strong>V6新版本：<a href="https://ent.surveyform.cn">https://ent.surveyform.cn</a></strong>账号密码(service@diaowen.net/123456)</p>
<p><strong>企业版V5体验地址</strong></p>
<p><strong>V5版本：<a href="https://element-ent.surveyform.cn">https://element-ent.surveyform.cn</a></strong>账号密码(service@diaowen.net/123456)</p>
</div>
</div>
<hr/>

<div style="padding: 10px;border-left:solid 6px #9211dc;background: #f3f5f7;font-size: 16px;color: #2c3e50;">
<div style="font-weight: 600;padding: 10px;color: #9211dc;"> 🎉🎉🎉  专业版 </div>
<div>
新一代的 <strong>专业版</strong> 已经发布，前端采用<strong>纯VUE实现</strong>（ElementUI）、后端使用 Spring Boot、Elasticsearch，敬请关注
<p>调问网官网地址：<a href="https://www.diaowen.net">https://www.diaowen.net</a></p>
<p><strong>V8新版本：<a href="https://pro.surveyform.cn">https://pro.surveyform.cn</a></strong>账号密码(service@diaowen.net/123456)</p>
</div>
</div>

<hr/>
<div style="padding: 10px;border-left:solid 6px #42b983;background: #f3f5f7;font-size: 16px;color: #2c3e50;">
<div style="font-weight: 600;padding: 10px;"> ☀️☀️☀️  社区版 </div>
<div>
️ 全新的 <strong>社区版</strong> 也已经发布，前后端分别采用Vue、ElementUI、Spring Boot，详情可前往体验。
<p>社区版文档地址：<a href="https://www.diaowen.net/docs">https://www.diaowen.net/docs</a></p>

[comment]: <> (<p>社区版体验地址：<a href="https://oss.surveyform.cn">https://oss.surveyform.cn</a></p>)
</div>
</div>

<p style="color: #333;">后续我们将持续迭代更新，<strong>并有专人维护</strong>，敬请关注，别忘了<a href="https://gitee.com/wkeyuan/DWSurvey">点下源码仓库</a> 右上角Star关注，便于下次查找。</p>

### 快速安装（一行命令完成）

1､下载调问问卷社区版安装包

最新的JAR包放在QQ群3（`811287103`已满），请加群4（`398556555`），加群备注`安装包`。

2、输入启动命令

``` cmd
    java  -Dfile.encoding=utf-8 -jar dwsurvey-oss-vue-v.*.*.jar --server.port=8080 --spring.datasource.username=root --spring.datasource.password=123456
```
3、打开浏览器访问 http://localhost:8080，输入账号密码： service@diaowen.net/123456


``` js
    //可选，文件编码，windows上如果有乱码则使用，linux一般不会出现
    -Dfile.encoding=utf-8 
    //必填，安装包名称， 后面的v.*.*.jar依据您下载的最新安装包来定。
    dwsurvey-oss-vue-v.*.*.jar
    //可选，指定启动服务占用的端口，默认值8080
    --server.port=8080 
    //可选，数据库账号，默认值为root
    --spring.datasource.username=root 
    //可选，数据库账密码，默认值123456,.
    --spring.datasource.password=123456
```

更详细参数说明请查看[配置说明文档](http://www.diaowen.net/docs/)

## 特性

![输入图片说明](https://images.gitee.com/uploads/images/2021/0416/132431_5d99a296_1401416.gif "dwsurvey-2.gif")

### 汇总

* 多种技术方案，满足不同的技术选型需求
* 完善的浏览器兼容、保证传统客户也能正常使用
* 部署简单，一行命令完成部署
* 更新方便，直接替换原安装文件不用担心数据被覆盖
* 最高支持多达40多种题型，如单选题、多选题、填空题、评分题、排序题、分页、分段...
* 可见即所得设计理念、所有内容支持快速富文本编辑
* 多种端适配，不管是PC还是移动端同一个地址系统自动适配
* 支持答卷密码、结束跳转、仅微信答卷限制等
* 数据实时统计，答卷的数据以可视化的方案展现，不同的统计图表
* 后端满足JPA规范，为未来支持更多数据库提供条件
* 成熟稳定，经过多年技术发展，技术体系完善
* 完善的支持服务，QQ群，微信群全天24小时技术服务

### 完善的浏览器兼容

**支持现代浏览器和IE6**

在实际收集问卷过程中，不是所有用户都会安装最新的浏览器，DWSurvey在答卷端支持所有的浏览器环境，方案B、方案C更是全站完全支持

| IE / Edge | Firefox | Chrome | Safari | Opera |
| :-----| ----: | :----: | :----: | :----: | 
| IE6+,Edge | 支持 | 支持 | 支持 | 支持 | 


### 各技术方案简介

<table>
<tr><th><strong>方案A</strong></th><th colspan="2" align="left"><strong>前后端分离，基于 Vue、Element ui、Spring Boot</strong></th></tr>
<tr><td colspan="3">方案特性：基于VUE、前后分离开发更容易，部署维护更简单</td></tr>
<tr><td rowspan="2">前端</td><td>Gitee</td><td><a href="https://gitee.com/wkeyuan/dwsurvey-vue">https://gitee.com/wkeyuan/dwsurvey-vue</a></td></tr>
<tr><td>GitHub</td><td><a href="https://github.com/wkeyuan/DWSurvey_Vue">https://github.com/wkeyuan/DWSurvey_Vue</a></td></tr>
<tr><td rowspan="2">后端</td><td>Gitee</td><td><a href="https://gitee.com/wkeyuan/DWSurvey">https://gitee.com/wkeyuan/DWSurvey</a></td></tr>
<tr><td>GitHub</td><td><a href="https://github.com/wkeyuan/DWSurvey">https://github.com/wkeyuan/DWSurvey</a></td></tr>

<tr><th><strong>方案B</strong></th><th colspan="2" align="left"><strong>Spring Boot + JSP</strong></th></tr>
<tr><td colspan="3">方案特性: 原生JS+HTML技术，支持更多浏览器，如IE6</td></tr>
<tr><td colspan="1">Gitee</td><td colspan="2"><a href="https://gitee.com/wkeyuan/dwsurvey-springboot-jsp">https://gitee.com/wkeyuan/dwsurvey-springboot-jsp</a></td></tr>
<tr><td colspan="1">GitHub</td><td colspan="2"><a href="https://gitee.com/wkeyuan/dwsurvey-springboot-jsp">https://gitee.com/wkeyuan/dwsurvey-springboot-jsp</a></td></tr>

<tr><th><strong>方案C</strong></th><th colspan="2" align="left"><strong>Struts2 + JSP</strong></th></tr>
<tr><td colspan="3">方案特性: 原生JS+HTML技术，支持更多浏览器，如IE6 |</td></tr>
<tr><td colspan="1">Gitee</td><td colspan="2"><a href="https://gitee.com/wkeyuan/dwsurvey-struts2-jsp">https://gitee.com/wkeyuan/dwsurvey-struts2-jsp</a></td></tr>
<tr><td colspan="1">GitHub</td><td colspan="2"><a href="https://gitee.com/wkeyuan/dwsurvey-struts2-jsp">https://gitee.com/wkeyuan/dwsurvey-struts2-jsp</a></td></tr>
</table>


## 各版本简介

我们提供各种不版本及服务，保证您不同场景的需求都得到满足

| 版本  | 特性及地址 |  
| :-----| ---- |  
| 社区版 | 源码全量开源，可独立部署，可二次开发 | 
| 官网地址 | [http://www.diaowen.net](http://www.diaowen.net) |
| 企业版 | 源码全量开源，前后端分离，可独立部署，可二次开发 |
| 企业版地址 | [http://ent.surveyform.cn](http://ent.surveyform.cn) |
| 在线平台 | 提供标准API，可快速集成到应用中，功能更丰富，不需要部署，可直接发布问卷收集 |
| 平台地址 | [https://www.surveyform.cn](https://www.surveyform.cn) |

下面是如何使用Spring Boot方案的快速指南。

## 本地开发

环境版本：jdk1.8, tomcat8.5.59

### 基本步骤

1、下载代码后

具体代码地址参考[多种技术方案](#多种技术方案)

2、创建数据库进行初始化

数据库脚本在resources/sql/目录下的dwsurvey.sql数据库脚本文件，如下：

    https://gitee.com/wkeyuan/DWSurvey/blob/master/src/main/resources/conf/sql/

说明：升级后的数据库与老版本兼容

3、用IDEA打开，进行本地开发

现在您可以进行本地开发了

4、开发完成，编译打包

进入 `cd DWSurvey`:

```bash
mvn package
```

配置文件地址

    源码配置文件地址
    resources/application-***.yml

    war包配置文件地址
    /dwsurvey-oss-v.4.0/WEB-INF/classes/application-dev.yml

	#database settings
	datasource:
    url: jdbc:mysql://localhost:3306/dwsurvey_21test?useUnicode=true&characterEncoding=utf8
    username: root
    password: 123456,.

    分别修改```url、username、password```

5、启动浏览器访问

输入 http://localhost:8080

初始账号：```service@diaowen.net``` 密码：```123456```

## 特色

### 全新体验、流程简单

以一种全新的设计体验，告别繁琐的设计流程，通过简单有趣的方式，轻轻松松完成问卷设计，多种技术方案，只为呈现更精美的表单问卷.

丰富的题型支持，通过拖拽即可完成题目选择，并可以随意拖动其位置，还可置入所需图片、企业LOGO、设置答题逻辑，一份优美的问卷就是这么简单。

![我的问卷](src/main/resources/static/img/WX20211203-092339@2x.png)

![问卷编辑](src/main/resources/static/img/WX20211203-092358@2x.png)

![问卷预览](src/main/resources/static/img/WX20211203-092406@2x.png)

![答卷地址](src/main/resources/static/img/WX20211203-092416@2x.png)

![答卷规则](src/main/resources/static/img/WX20211203-092427@2x.png)

![数据统计](src/main/resources/static/img/WX20211203-092439@2x.png)

![原始数据](src/main/resources/static/img/WX20211203-092458@2x.png)

## 有问题怎么办？

对于调问网问卷系统安装及使用的问题，可以在用户交流群里，向作者或其它同学提问。

调问网交流QQ群 ```群1：635994795(满)```，```群2：301105635(满)```， ```群3：811287103(满)```，群4：398556555（可加）（加群时请说明来由）

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
