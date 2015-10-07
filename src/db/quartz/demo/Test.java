package db.quartz.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import app.quartz.demo.Demo1Job2;

/**
 * 步骤：
 * 1、配置quartz.properties，加入db相关配置
 * 2、执行<quartz-download>/docs/dbTables下tables_mysql_innodb.sql脚本
 * 3、与RAMJobStore时，操作一样，只是定时器会存入db
 * 
 ******	加入Job信息的三种方式：*******
 *	· 程序中硬编码加入Job
 *	· 使用JobInitializationPlugin
 *	· 使用Quartz Web图形界面添加
 *   
 */
public class Test {
	static Log logger = LogFactory.getLog(Test.class);

	public static void main(String[] args) {
		Test app = new Test();
		app.startScheduler();
	}

	public void startScheduler() {
		try {
			// 读取指定文件
			StdSchedulerFactory factory = new StdSchedulerFactory("db/quartz/demo/quartz_.properties");   
			Scheduler scheduler = factory.getScheduler();
			
			// 读取根目录下quartz.properties文件
			//Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail jobDetail2 = new JobDetail("Demo3Job2", Scheduler.DEFAULT_GROUP, Demo1Job2.class);
			CronTrigger trigger3 = new CronTrigger("trigger2", Scheduler.DEFAULT_GROUP, "0 0/2 22 ? * MON,Sat");
			scheduler.scheduleJob(jobDetail2, trigger3);
			
			scheduler.start();

		} catch (Exception ex) {
			logger.error(ex);
		}
	}

}
