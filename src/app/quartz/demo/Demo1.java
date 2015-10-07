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
 * 编程式：无需编写quartz.properties，因为它会加载quartz-all-1.5.2.jar中默认的quartz.properties
 * 
 * 若编写了quartz.properties，且放在了根目录下，则会自动加载读取该配置文件。
 * 当然，若不放在根目录下也行，换种方式读取【示例：db.quartz.demo.Test】
 */
public class Demo1 {

	public static void main(String[] args) throws Exception {
		// 获取scheduler，若有直接返回，否则创建一个。该scheduler的instanceName、instanceId配置在文件中
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		/** ******** 一个job实例 ********* 
		 * 1、SimpleTrigger 构造方法: 
		 * 立即开始，无结束时间，重复无数次，间隔时间1分钟
		 */
		JobDetail jobDetail1 = new JobDetail("Demo3Job1", Scheduler.DEFAULT_GROUP, Demo1Job1.class);
		jobDetail1.getJobDataMap().put("param", "aa");
		
		Trigger trigger1 = new SimpleTrigger("trigger1", Scheduler.DEFAULT_GROUP, new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY, 10000L);
		
		
		/** ******** 二个job实例 *********
		 * 2、CronTrigger 构造方法: 
		 * 星期一和星期六的晚上 22:00-23:00 间每2分钟执行一次
		 */
		JobDetail jobDetail2 = new JobDetail("Demo3Job2", Scheduler.DEFAULT_GROUP, Demo1Job2.class);
		CronTrigger trigger2 = new CronTrigger("trigger2", Scheduler.DEFAULT_GROUP, "0 0/2 22 ? * MON,Sat");
		
		// 指定触发开始时间、结束时间，否则从当前时间开始，这样有点类似SimpleTrigger的起止时间
		// 例如：下一天才开始，并且只执行两天
		Calendar cal = Calendar.getInstance();   
        cal.add(Calendar.DATE, 1);   
        trigger2.setStartTime(cal.getTime()); //*********//  
        cal.add(Calendar.DATE, 2);   
        trigger2.setEndTime(cal.getTime());   //*********//
        
		
		scheduler.scheduleJob(jobDetail1, trigger1);
		scheduler.scheduleJob(jobDetail2, trigger2);
		
		
		// 1、更新job，替换原有的job ———— addJob(jobDetail, boolean replace), 新的Job名+组,必须老的相同
        jobDetail1.getJobDataMap().put("param", "bb");
        scheduler.addJob(jobDetail1, true);
        
        // 2、从Scheduler中移除Job
        scheduler.deleteJob("Demo3Job2", Scheduler.DEFAULT_GROUP);
        
        // 3、更新trigger，替换原有的trigger ———— 新的trigger跟上面的构造方法不同，必须多2个参数：jobName,jobGroup
        Trigger newTrigger = new SimpleTrigger("trigger1", Scheduler.DEFAULT_GROUP, "Demo3Job1", Scheduler.DEFAULT_GROUP, new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY, 3000L);
        newTrigger.setStartTime(new Date());
        scheduler.rescheduleJob("trigger1", Scheduler.DEFAULT_GROUP, newTrigger);
        
        // 4、添加trigger，与scheduler.scheduleJob(jobDetail1, trigger1)不一样，少一个参数
        //scheduler.scheduleJob(trigger);
		
		// 启动
		scheduler.start();
		// 停止
		scheduler.shutdown();
	}
	
}
