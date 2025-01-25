# DWSurvey Open source questionnaire form system

![img.png](readmeimgs/logoimg.png)

    Twelve years of open-source accumulation, helping you build your own questionnaire form system

<p align="center">
<a href='https://gitee.com/wkeyuan/DWSurvey'>
    <img src='https://gitee.com/wkeyuan/DWSurvey/badge/star.svg?theme=dark' alt='star'></img>
</a>
<a href='https://gitee.com/wkeyuan/DWSurvey'>
    <img src='https://gitee.com/wkeyuan/DWSurvey/badge/fork.svg?theme=dark' alt='star'></img>
</a>
<a href='https://github.com/wkeyuan/DWSurvey'>
    <img src='https://img.shields.io/github/stars/wkeyuan/DWSurvey?style=social' alt='star' title="GitHub Repo stars"></img>
</a>
<a href='https://github.com/wkeyuan/DWSurvey'>
    <img src='https://img.shields.io/github/forks/wkeyuan/DWSurvey?style=social' alt='star'></img>
</a>
</p>
<p align="center">
<a href="https://www.diaowen.net/" target="_blank">Website</a>&nbsp;
<a href="https://www.diaowen.net/" target="_blank">Experience</a>&nbsp;
<a href="https://www.diaowen.net/install/docker" target="_blank" >Deployment</a>&nbsp;
<a href="https://gitee.com/wkeyuan/DWSurvey/issues" target="_blank">Community</a>&nbsp;
</p>

**<p align="center">🫣 If you find the project helpful, please click on the Star button in the upper right corner 🌟  Encourage us and support the inquiry of open source teams</p>**

简体中文 |  [English](./README_en.md)

## About dwsurvey

DWSurvey questionnaire form 🎉🎉 Since the project was launched in 2012, it has accumulated 12 years of accumulation, and has undergone 6 major framework upgrades. The front-end has completed the transformation from JSP, jQuery to Vue framework. We have served thousands of real clients, including enterprises, government departments, and vocational colleges.

These customers have made us aware of many overlooked details in the questionnaire form and the requirements that will be used in practical scenarios. These feedbacks are like nutrients constantly nourishing the growth of the survey form.

In the 13th year of the DWSurvey questionnaire form 🎉🎉， We have completed the upgrade of VUE3 version and launched a new framework and an open-source version based on Vue. On the path of open source questionnaire forms, we are still moving forward seriously and striving.

If you have any needs or suggestions, please feel free to contact the product team for inquiries, or leave a message directly in the community. We will take every user's voice seriously.

We need your encouragement to do every function with care! Star in the upper right corner 🌟 Follow and wait for you to light up!

![产品微信](readmeimgs/2024img/img_3.png)

![调问问卷公众号](readmeimgs/2024img/img_4.png)

## Technical Architecture

The front-end and back-end are completely separated, with Vue2 and Vue3 frameworks provided for implementation. The back-end development language is mainly based on JAVA, integrating service calls through HTTP APIs to achieve business functions. The database is stored in MySQL and Elasticsearch, and the cache is stored in Redis to ensure high performance, reliability, and scalability of the business.

This product's application service supports one click deployment of Docker images, and requires a Docker environment to be prepared on the server in advance. Currently, the community version provides one click installation of Docker images that can be downloaded for free, making the overall implementation relatively simple.

Regarding subsequent updates, upgrading can be done by updating the container image after deployment is completed.

## Product Introduction

<p>
DWSurvey Official website address：
<strong>
<a href="https://www.diaowen.net">https://www.diaowen.net</a>
</strong>
</p>

### Community 

Since its inception, Zhaowen has consistently adhered to 100% open source for all front-end and back-end code 💯， Ensure the stability and adaptability of all functions 👏。

<p><strong>Installation and download address (installation package)：<a href="https://www.diaowen.net/install/docker">https://www.diaowen.net/install/docker</a></strong></p>

<p>
<strong>
Source code download address (including front-end and back-end)：<a href="https://gitee.com/wkeyuan/DWSurvey">https://gitee.com/wkeyuan/DWSurvey</a>
</strong>
</p>

### Enterprise 

<p>The Enterprise Edition currently offers two versions of services: Vue3 and Vue2</p>
<p><strong>V8 new version：<a href="https://demo.diaowen.net">https://demo.diaowen.net</a></strong></p>

## System Highlights&Core Functions

The latest open source version is V7, which has mature basic capabilities and is sufficient to meet the questionnaire needs in general scenarios. The front-end and back-end adopt Vue2, Element UI, and Spring Boot frameworks respectively.

If you have more advanced functional requirements, you can also experience the latest commercial V8 version, which uses Vue3, Element Plus, and Spring Boot frameworks for the front-end and back-end respectively.

Experience the functional differences of several versions, see here for details

Please provide feedback on any requirements or bugs in the community or contact product customer service, and we will arrange for improvement as soon as possible.

At the same time, the historical old versions adopt Jsp+Struts2 Jsp+SpringBoot、Vue2+JQuery+SpringBoot,  Click on the details (only fix bugs later, no further feature iterations), and place the link addresses of each historical version repository at the end of the article.

We strive to create an open-source questionnaire form system that is stable, commercially viable, and suitable for various questionnaire scenarios.

In comparison, the questionnaire form has the following two points: 🎉🎉🎉

* 首创性🎉🥇引入拖拽式创建问卷
* 可见即所得设计理念、所有内容支持快速富文本编辑
* 部署简单，一行命令完成部署
* 更新方便，直接替换原安装文件不用担心数据被覆盖
* 多种端适配，不管是PC还是移动端同一个地址系统自动适配
* 支持 **50+自定义组件** ，可拖拽快速生成问卷表单。
* **每个微信、账号、ip、设备、答题次数限制、支持设置允许填写时间、记录微信个人信息** 。
* 最高支持多达40多种题型，如单选题、多选题、填空题、评分题、排序题、分页、分段...
* 支持答卷密码、结束跳转、仅微信答卷限制等
* 数据实时统计，答卷的数据以可视化的方案展现，不同的统计图表
* 后端满足JPA规范，为未来支持更多数据库提供条件
* **率先🎉🥇引入调问独有的问卷编程语言，像编程一样配置问卷逻辑**
* **调问DSL自定义逻辑可以快速地操作问卷底层能力**，实现复杂的逻辑定制，提高问卷逻辑设置的效率。目前已经实现了多条件组合的显示逻辑、非逻辑、题目内容引用替换、题目选项分组随机、计算逻辑等功能，更多功能正在陆续开发中。
* 成熟稳定，经过多年技术发展，技术体系完善
* 完善的支持服务，QQ群，微信群全天24小时技术服务

<strong>  🎉🎉 目前已支持功能包括  </strong> 

* 支持题型：

> 单选题，多选题，填空题，评分题，排序题，多行填空题，多项填空题，日期题，时间题，下拉单选题，文件上传题，分页，分段，量表题，滑块题，级联题，矩阵单选题，矩阵多选题，矩阵评分题，矩阵填空题，矩陈量表题，矩陈滑块题，签名题，定位题，双重定位 ，模糊定位，精确定位，矩阵数值题，矩阵组合题，矩阵下拉题，矩阵自增题，甄别题，甄别数据记录，图片单选题，图片多选题，绘图题，地图组件，分割线，图片轮播，图片上传，水印设置

* 题型扩展能力

> 🎉 题目高级编辑，时间题高级设置，量表题，滑块题，多项填空高级设置，表单14种格式验证，填空题高级设置，多选题选项，单选题高级设置，多选题高级设置，下拉题高级设置，排序题高级设置，填空题数据验证长度

* 支持题库

> 专业选择题，院校选择题，行业选择题，职业选择题，行政区选择题，常用题库（姓名、性别、学历等）

*  **逻辑控制**

> 🎉✨ 显示逻辑，跳转逻辑，隐藏逻辑，DSL逻辑_显示语句，DSL逻辑_隐藏语句，DSL逻辑_跳转语句，DSL逻辑_条件语句，DSL逻辑_多条件组合，DSL逻辑_互斥语句，DSL逻辑_验证语句，DSL逻辑_赋值语句，DSL逻辑_计算语句

* 样式管理

> 🎉 页眉图设置，背景图设置，Logo图设置，主题色设置，预置主题，背景色设置，Logo行背景色，进度条色设置，显示题号设置，显示进度设置，显示表头设置，显示题型设置，显示标题设置，显示问卷说明设置，显示题目分数设置，矩陈题移动端适配，PC与移动端自适应

## 运行环境

社区版环境版本：jdk1.8, tomcat8.5.59

数据库：Mysql5.7+，

建议最低运行配置：Linux cpu & 2核4G

## 安装&使用文档

### 快速安装（最快3分钟完成）

<strong>推荐通过Docker一键安装包完成</strong> <a href="https://www.diaowen.net/install/docker">一键安装</a>

更详细安装说明请查看[说明文档](http://www.diaowen.net/docs/)

## 本地开发

开发工具：vscode, idea
node版本：v18.16+

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

## 加入社群

对于调问问卷系统的任何问题，都可以加客服微信进行咨询，也可以关注我们的公众号，我们每周都将会将会在公众号同步更迭代信息。

<p>
<img src="readmeimgs/2024img/img_3.png" style="width: 130px"/>
</p>

<p>
<img src="readmeimgs/2024img/img_4.png" style="width: 130px"/>
</p>

调问现在有4个供大家交流的QQ群：用户群1：635994795（已满）、用户群2：301105635（已满）、用户群3：811287103（已满）、用户群4：398556555，

如果遇到加群被拒绝的情况，说明交流群已满，请先加客服微信，我们将根据您的情况，拉到对应的QQ群中。

## 社区版

### Vue实现预览

![img_1.png](readmeimgs/202501/img_1.png)
![img_2.png](readmeimgs/202501/img_2.png)
![img_3.png](readmeimgs/202501/img_3.png)
![img_4.png](readmeimgs/202501/img_4.png)
![img_5.png](readmeimgs/202501/img_5.png)
![img_6.png](readmeimgs/202501/img_6.png)

### JQuery实现预览

![输入图片说明](https://images.gitee.com/uploads/images/2021/0416/132431_5d99a296_1401416.gif "dwsurvey-2.gif")

### 各技术版本简介

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

## 社区版源代码地址

gitee: http://gitee.com/wkeyuan/DWSurvey

github: https://github.com/wkeyuan/DWSurvey

## 版权说明

DWSurvey以通用公共许可证AGPL3.0为开源协议，部署使用要求保留"Power by diaowen.net"标识，且衍生版本需要及时开源。
需要更优质的服务可以购买我们的专业版与企业版！更多信息，请查看调问官网

