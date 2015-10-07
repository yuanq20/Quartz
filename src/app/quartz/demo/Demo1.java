package app.quartz.demo;

import java.util.Calendar;
import java.util.Date;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * ���ʽ�������дquartz.properties����Ϊ�������quartz-all-1.5.2.jar��Ĭ�ϵ�quartz.properties
 * 
 * ����д��quartz.properties���ҷ����˸�Ŀ¼�£�����Զ����ض�ȡ�������ļ���
 * ��Ȼ���������ڸ�Ŀ¼��Ҳ�У����ַ�ʽ��ȡ��ʾ����db.quartz.demo.Test��
 */
public class Demo1 {

	public static void main(String[] args) throws Exception {
		// ��ȡscheduler������ֱ�ӷ��أ����򴴽�һ������scheduler��instanceName��instanceId�������ļ���
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		/** ******** һ��jobʵ�� ********* 
		 * 1��SimpleTrigger ���췽��: 
		 * ������ʼ���޽���ʱ�䣬�ظ������Σ����ʱ��1����
		 */
		JobDetail jobDetail1 = new JobDetail("Demo3Job1", Scheduler.DEFAULT_GROUP, Demo1Job1.class);
		jobDetail1.getJobDataMap().put("param", "aa");
		
		Trigger trigger1 = new SimpleTrigger("trigger1", Scheduler.DEFAULT_GROUP, new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY, 10000L);
		
		
		/** ******** ����jobʵ�� *********
		 * 2��CronTrigger ���췽��: 
		 * ����һ�������������� 22:00-23:00 ��ÿ2����ִ��һ��
		 */
		JobDetail jobDetail2 = new JobDetail("Demo3Job2", Scheduler.DEFAULT_GROUP, Demo1Job2.class);
		CronTrigger trigger2 = new CronTrigger("trigger2", Scheduler.DEFAULT_GROUP, "0 0/2 22 ? * MON,Sat");
		
		// ָ��������ʼʱ�䡢����ʱ�䣬����ӵ�ǰʱ�俪ʼ�������е�����SimpleTrigger����ֹʱ��
		// ���磺��һ��ſ�ʼ������ִֻ������
		Calendar cal = Calendar.getInstance();   
        cal.add(Calendar.DATE, 1);   
        trigger2.setStartTime(cal.getTime()); //*********//  
        cal.add(Calendar.DATE, 2);   
        trigger2.setEndTime(cal.getTime());   //*********//
        
		
		scheduler.scheduleJob(jobDetail1, trigger1);
		scheduler.scheduleJob(jobDetail2, trigger2);
		
		
		// 1������job���滻ԭ�е�job �������� addJob(jobDetail, boolean replace), �µ�Job��+��,�����ϵ���ͬ
        jobDetail1.getJobDataMap().put("param", "bb");
        scheduler.addJob(jobDetail1, true);
        
        // 2����Scheduler���Ƴ�Job
        scheduler.deleteJob("Demo3Job2", Scheduler.DEFAULT_GROUP);
        
        // 3������trigger���滻ԭ�е�trigger �������� �µ�trigger������Ĺ��췽����ͬ�������2��������jobName,jobGroup
        Trigger newTrigger = new SimpleTrigger("trigger1", Scheduler.DEFAULT_GROUP, "Demo3Job1", Scheduler.DEFAULT_GROUP, new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY, 3000L);
        newTrigger.setStartTime(new Date());
        scheduler.rescheduleJob("trigger1", Scheduler.DEFAULT_GROUP, newTrigger);
        
        // 4�����trigger����scheduler.scheduleJob(jobDetail1, trigger1)��һ������һ������
        //scheduler.scheduleJob(trigger);
		
		// ����
		scheduler.start();
		// ֹͣ
		scheduler.shutdown();
	}
	
}
