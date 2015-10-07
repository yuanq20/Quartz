package app.quartz.demo;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 编程式：无需编写quartz.properties，因为它会加载quartz-all-1.5.2.jar中默认的quartz.properties
 */
public class Demo2 {
	
	public static void main(String[] args) throws Exception {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		/** 一个job实例 **/
		scheduleJob(scheduler, "Demo3Job1", Demo1Job1.class, "aa", new Date());
		/** 二个job实例 **/
		scheduleJob(scheduler, "Demo3Job2", Demo1Job2.class, "bb", new Date());
		
		// 启动
		scheduler.start();
	}

	private static void scheduleJob(Scheduler scheduler, String jobName, Class jobClass, String param, Date startTime) throws SchedulerException {
		JobDetail jobDetail = new JobDetail(jobName, Scheduler.DEFAULT_GROUP, jobClass); //名称+组：唯一标识
		jobDetail.getJobDataMap().put("param", param);
		
		Trigger trigger = new SimpleTrigger(jobName, Scheduler.DEFAULT_GROUP, startTime, null, SimpleTrigger.REPEAT_INDEFINITELY, 60000L); //名称+组：唯一标识
		
		scheduler.scheduleJob(jobDetail, trigger);
	}

}
