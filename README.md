# Quartz
一、与java.util.Timer的区别
1．Java 定时器没有持久化机制。
2．Java 定时器没有使用线程池(每个Java 定时器使用一个线程)  
3．Java 定时器没有切实的管理方案，你不得不自己完成存储、组织、恢复任务的措施。
二、Quartz 基本应用所需的 JAR 包
名称	位置
quartz-all-1.6.5.jar
Commons BeanUtils	<quartz-download>/ lib/optional
Commons Collections	<quartz-download>/ lib/core
Commons Digester	<quartz-download>/ lib/optional
Commons Logging	<quartz-download>/ lib/core
数据库存储：	<quartz-download>/docs/dbTables
commons-dbcp	
commons-pool	
三、相关说明
1.	quartz.properties 文件
Quartz 包括一个名为 quartz.properties 的配置文件，它允许你对 Quartz 的很多方面的配置。在 Quartz JAR 包中有一个默认的 quartz.properties 文件，但是假如你需要修改任何默认配置项时，你需要放置一个 quartz.properties 文件持贝在 classpath 下。
2.	Scheduler会为每一次执行创建新的Job实例 (无状态)
3.	一个 Job 在同一个Scheduler实例中通过 名称+组名 唯一标识
4.	SimpleTrigger 和  CronTrigger
5.	① 编程式部署一个 Job
② 声明式部署一个 Job (常用, 需要 JobInitializationPlugin 插件)
6.	Scheduler代表一个调度容器，一个调度容器中可以注册多个JobDetail和Trigger。当Trigger与JobDetail组合，就可以被Scheduler容器调度了。
四、Cron 表达式
 
名称	是否必须	允许值	特殊字符
秒	是	0-59	, - * /
分	是	0-59	, - * /
时	是	0-23	, - * /
日	是	1-31	, - * ? / L W C
月	是	1-12 或 JAN-DEC	, - * /
周	是	1-7 或 SUN-SAT	, - * ? / L C #
年	否(一般省略)	空 或 1970-2099	, - * /

解释：
1、可以是列表，用 - 或 , 分隔，例如： 
2、* 表示：每一天、每一月、每一分、每一秒等
3、/ 表示：从第xx分钟开始，每隔xx分钟，例如：3/15 从第3分钟开始，每隔15分钟
4、? 表示：不需要指定值，例如：要么指定月中的天，要么指定周中的天
5、L 表示：（全称：Last）
① Day-of-Month中，表示月中最后一天，例如31日
② Day-of-Week中，表示周中最后一天，例如，周六，等同于7 (西方最后一天为周六)
  Day-of-Week中，若L前面还有数值，例如 6L，表示本月的最后一个周xx（6L表示本月的最后一个周五）(西方最后一天为周六)
6、# 表示：月中第xx个周xx，例如：6#3表示月中第三个周五。
4#5表示月中第5个周三，若当月没有第5个周三，忽略不触发。
7、W 只能用在day-of-month字段。表示离该日最近的工作日（周一到周五）。
例：15W表示离该月15日最近的工作日，如果15日是周六，则匹配14日周五；如果15日是周日，则匹配16日周一；如果15日是周二，则就是15日这天。
注意：关联的匹配日期不能跨月。如用户指定1W，如果1号是周六，结果匹配时3号周一，而非上月最后那天。
“W”字符仅能指定单一日期，而不能指定日期范围。
也可以用“LW”来指定这个月的最后一个工作日。

举例： 
0 15 10 * * ?  			每天上午10:15触发 
0 15 10 * * ? 2005 		2005年的每天上午10:15触发 
0 * 14 * * ? 			在每天下午2点到下午2:59期间的每1分钟触发 
0 0/5 14 * * ? 			在每天下午2点到下午2:55期间的每5分钟触发 
0 0/5 14,18 * * ? 	    每天下午2点到2:55间和下午6点到6:55间的每5min触发 
0 0-5 14 * * ? 			在每天下午2点到下午2:05期间的每1分钟触发 
0 10,44 14 ? 3 WED 		每年三月的星期三的下午2:10和2:44触发 
0 15 10 ? * MON-FRI 	周一至周五的上午10:15触发 
0 15 10 15 * ? 			每月15日上午10:15触发 
0 15 10 L * ? 			每月最后一日的上午10:15触发 
0 15 10 ? * 6L 			每月的最后一个星期五上午10:15触发 
0 15 10 ? * 6L 2002-2005 2002年至2005年的每月的最后一个星期五上午10:15触发 
0 15 10 ? * 6#3 		每月的第三个星期五上午10:15触发
0 0 12 1/5 * ? 			每月从第一天算起每五天的 12:00 PM (中午)
0 11 11 11 11 ? 		每一个 11 月 11 号的 11:11 AM
0 10,44 14 ? 3 WED		三月份每个周三的 2:10 PM 和 2:44 PM

注意：
① 当你创建一个 CronTrigger 实例，假如没为它指定一个开始时间，即从当前时间算起；
//指定触发开始时间、结束时间，否则从当前时间开始
trigger3.setStartTime(startTime);
trigger3.setEndTime(endTime);
这样，就有点类似SimpleTrigger的起止时间
五、DB存储
步骤：
 1、配置quartz.properties，加入db相关配置
 2、执行<quartz-download>/docs/dbTables下tables_mysql_innodb.sql脚本
 3、与RAMJobStore时，操作一样，只是定时器会存入db

Quartz JDBC存储时的各表简介
表名	描述
QRTZ_CALENDARS	以 Blob 类型存储 Quartz 的 Calendar 信息
QRTZ_CRON_TRIGGERS	存储 Cron Trigger，包括 Cron 表达式和时区信息
QRTZ_FIRED_TRIGGERS	存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息
QRTZ_PAUSED_TRIGGER_GRPS	存储已暂停的 Trigger 组的信息
QRTZ_SCHEDULER_STATE	存储少量的有关 Scheduler 的状态信息，和别的 Scheduler 实例(假如是用于一个集群中)
QRTZ_LOCKS	存储程序的非观锁的信息(假如使用了悲观锁)
QRTZ_JOB_DETAILS	存储每一个已配置的 Job 的详细信息
QRTZ_JOB_LISTENERS	存储有关已配置的 JobListener 的信息
QRTZ_SIMPLE_TRIGGERS	存储简单的 Trigger，包括重复次数，间隔，以及已触的次数
QRTZ_BLOB_TRIGGERS	Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，JobStore 并不知道如何存储实例的时候)
QRTZ_TRIGGER_LISTENERS	存储已配置的 TriggerListener 的信息
QRTZ_TRIGGERS	存储已配置的 Trigger 的信息

添加一个Job在表qrtz_job_details插入一条记录 
添加一个Simple Trigger在表qrtz_simple_triggers插入一条记录 
添加一个Cron Trigger 在表qrtz_cron_triggers插入一条记录 
添加Simple Trigger和Cron Trigger都会同进在表qrtz_triggers插入一条记录，一般获取所有调度任务列表数据就是从qrtz_triggers表获取。

triggers的三种状态：
"ACQUIRED" -- "运行中";
"PAUSED" -- "暂停中";
"WAITING" -- "等待中";
六、Quartz 和 Web 应用
1.	J2EE时，通过编写Servlet，启动时读取配置文件，启动定时器。
不用我们自己写系统启动时的servlet啦，它已帮我们提供。
实际中，通过配置下面Servlet + 配置式的Scheduler即完成定时器任务
<servlet>  
 <servlet-name>QuartzInitializer</servlet-name>  
<servlet-class>  
   org.quartz.ee.servlet.QuartzInitializerServlet   
 </servlet-class>  
  
 <init-param>  
<!—不配置的话，读取默认quartz.properties 文件 -->
   <param-name>config-file</param-name>  
   <param-value>quartz.properties</param-value>  
 </init-param>  
  
 <init-param>  
<!—关闭工程时，会调用scheduler.shutdown()，也关闭定时器，默认也是true -->
   <param-name>shutdown-on-unload</param-name>  
   <param-value>true</param-value>  
 </init-param>  
  
 <init-param> 
<!—启动工程时，会调用scheduler.star()，也启动定时器，默认也是true，若改成false，需要手工调用 scheduler.start()启动 --> 
   <param-name>start-scheduler-on-load</param-name>  
   <param-value>true</param-value>  
 </init-param>  
  
<load-on-startup>1</load-on-startup>
</servlet>

2.	通过阅读QuartzInitializerServlet的init()方法，启动Servlet创建后，将schdulerFactory放在ServletContext中，程序中可以随时从中取出，进行操作。
StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);
//获取scheduler，若有直接返回，否则创建一个
Scheduler scheduler = factory.getScheduler();
scheduler.start();
	
七、Spring集成Quartz

两种思路：
1.作业类不继承org.springframework.scheduling.quartz.QuartzJobBean类的方式
2.作业类继承 org.springframework.scheduling.quartz.QuartzJobBean类的方式

注：个人比较推崇第一种，因为这种方式下的作业类仍然是POJO。
八、Quartz 任务监控管理（图形化）
Quartz 任务监控管理 (1) - 曾文锋开发日志 - ITeye技术网站.mht
Quartz 任务监控管理 (2) - 曾文锋开发日志 - ITeye技术网站.mht

参考QuartzMonitor工程：可界面添加、查看定时器
