# Hello Job
# 调度系统
hello job 是使用j2ee技术开发的调度系统，提供交互简单的中文操作界面，40秒上手。目前业界有不少调度系统，比如oozie（太难用）、xxl-job（太重量）、airflow（python写的，依赖linux的crontab，只能够部署在linux）， hello job致力于打造一个轻量级的、简单好用的跨平台调度系统，希望可以成为调度界的一股清流。

# 系统特征：
1. 支持时间调度、作业依赖触发、手工执行三种调度方式。
* 时间调度：底层基于quartz实现，支持cron命令，实现灵活的时间调度方式。
* 作业依赖触发：一个子作业可以依赖多个父作业，一个父作业可以有多个子作业，系统同时做死循环判断，避免作业依赖形成环。
* 手工执行：对任何作业都可以手动触发一次。
2. 系统自身并不承担业务逻辑，通过ssh 协议执行远程机器的命令，支持hive、spark、kettle、python、shell等脚本的执行。
3. 本系统实现了邮件告警功能，配置作业的收件人邮箱，当作业执行失败会发报错信息到该邮箱。
4. 本系统带有一个名为dt的日期变量（yyyyMMdd格式），可以在“执行命令”中使用${dt}。如“echo ${dt}”。dt的值默认为昨天。所以本系统特别适合用于etl按天增量同步数据的作业的调度。
5. 本系统对于作业有个“自依赖”的选项，自依赖约束该作业在当天dt能够执行，要求前一天dt已经成功执行。
6. 可以部署在windows 或者linux 服务器。

# 系统部分截图
![作业管理](https://github.com/iture123/helloJob/blob/master/helloJob/doc/job.png)

![添加作业](https://github.com/iture123/helloJob/blob/master/helloJob/doc/addJob.png)

![作业日志](https://github.com/iture123/helloJob/blob/master/helloJob/doc/jobLog.png)

# 技术栈：
jdk1.8、spring、springmvc、mybatis、quartz、mysql

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
* 本猿qq：1011699225（默默向上游）
* 对大数据技术感兴趣的朋友也欢迎加Q群：644036039
