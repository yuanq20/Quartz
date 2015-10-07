package app.quartz.demo;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * ���ʽ�������дquartz.properties����Ϊ�������quartz-all-1.5.2.jar��Ĭ�ϵ�quartz.properties
 */
public class Demo2 {
	
	public static void main(String[] args) throws Exception {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		/** һ��jobʵ�� **/
		scheduleJob(scheduler, "Demo3Job1", Demo1Job1.class, "aa", new Date());
		/** ����jobʵ�� **/
		scheduleJob(scheduler, "Demo3Job2", Demo1Job2.class, "bb", new Date());
		
		// ����
		scheduler.start();
	}

	private static void scheduleJob(Scheduler scheduler, String jobName, Class jobClass, String param, Date startTime) throws SchedulerException {
		JobDetail jobDetail = new JobDetail(jobName, Scheduler.DEFAULT_GROUP, jobClass); //����+�飺Ψһ��ʶ
		jobDetail.getJobDataMap().put("param", param);
		
		Trigger trigger = new SimpleTrigger(jobName, Scheduler.DEFAULT_GROUP, startTime, null, SimpleTrigger.REPEAT_INDEFINITELY, 60000L); //����+�飺Ψһ��ʶ
		
		scheduler.scheduleJob(jobDetail, trigger);
	}

}
