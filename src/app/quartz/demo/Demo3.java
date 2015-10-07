package app.quartz.demo;

import java.util.ArrayList;
import java.util.Calendar;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;

/**
 * org.quartz.Calendar 理解为 java.util.Calendar 的集合，代表一些特定时间点的集合

 * 特殊时间的排除：比如排除每年5.1 和 10.1
 * 
 * 示例：每60s触发一次任务，但是五一、国庆排除在外。
 */
public class Demo3 {
	public static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		
		// 因为10.1、5.1 以年为周期，所以，使用AnnualCalendar
		AnnualCalendar cal = new AnnualCalendar();
		
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.MONTH, Calendar.MAY); //5.1
		c1.set(Calendar.DATE, 1);
		Calendar c2 = Calendar.getInstance(); 
		c2.set(Calendar.MONTH, Calendar.OCTOBER); //10.1
		c2.set(Calendar.DATE, 1);

		ArrayList<Calendar> list = new ArrayList<Calendar>();
		list.add(c1);
		list.add(c2);
		
		cal.setDaysExcluded(list);
		
		//向scheduler注册日历
		scheduler.addCalendar("holidays", cal, false, false); 

		// 利用工具类，另一种定义SimpleTrigger方式：每60s触发一次
		Trigger trigger = TriggerUtils.makeImmediateTrigger("myTrigger", -1, 60000);
		// 让 触发器 应用指定日历规则
		trigger.setCalendarName("holidays");
		
		
		JobDetail jobDetail = new JobDetail("Demo1Job2", Scheduler.DEFAULT_GROUP, Demo1Job2.class);
		
		//向scheduler注册job、trigger
		scheduler.scheduleJob(jobDetail, trigger);
		
		scheduler.start();
	}
}
