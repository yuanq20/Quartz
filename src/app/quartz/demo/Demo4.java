package app.quartz.demo;

import java.util.Calendar;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 */
public class Demo4 {

	public static void main(String[] args) throws Exception {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		JobDetail jobDetail2 = new JobDetail("job2", "jGroup_2", Demo1Job2.class);
		CronTrigger trigger2 = new CronTrigger("trigger1", "tGroup_1");
		trigger2.setCronExpression("0/5 * * * * ?");
		
		// 例如：下一分钟才开始
//		Calendar cal = Calendar.getInstance();   
//      cal.add(Calendar.MINUTE, 1);   
//      trigger2.setStartTime(cal.getTime()); 
//		
		scheduler.scheduleJob(jobDetail2, trigger2);
		
		// 启动
		scheduler.start();
	}
	
}
