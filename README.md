# helloJob
# 调度系统

# 系统简介：
1. 本系统使用java开发，支持quartz cron命令的时间调度、作业依赖触发、手工执行三种调度方式。
2. 系统自身并不承担业务逻辑，通过ssh 协议执行远程机器的命令，支持hive、spark、kettle、python、shell等脚本的执行。
3. 本系统实现了邮件告警功能，配置作业的收件人邮箱，当作业执行失败会发报错信息到该邮箱。

# 系统部分截图
![作业管理](https://github.com/iture123/helloJob/blob/master/helloJob/doc/job.png)

![添加作业](https://github.com/iture123/helloJob/blob/master/helloJob/doc/addJob.png)

![作业日志](https://github.com/iture123/helloJob/blob/master/helloJob/doc/jobLog.png)

# 技术栈：
spring、springmvc、mybatis、quartz、mysql

# 配置文件
* 数据库：doc/helloJob.sql
* 数据库连接配置：src/main/application.properties
* 作业告警发件邮箱配置：src/main/email.properties

# 开发环境
* 开发工具：eclipse，lombok，maven、tomcat 8.55，mysql5.1+
*  lombok 的主要作用是通过注解减少setter 和getter方法的生成，保持代码简洁。eclipse 务必要先安装lombok插件
*  部署好项目后，登陆http://localhost:8080/helloJob ，默认账号 admin/test

# 特别鸣谢
* 本系统的权限控制用的是轩少_开源的spring-shiro-training，在此特别感谢
* [ spring-shiro-training 开源中国地址 ](https://www.oschina.net/p/spring-shiro-training)
 
# 联系本猿
本猿qq：1011699225（默默向上游）
