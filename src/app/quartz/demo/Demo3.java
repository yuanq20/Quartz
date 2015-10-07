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
 * org.quartz.Calendar ���Ϊ java.util.Calendar �ļ��ϣ�����һЩ�ض�ʱ���ļ���

 * ����ʱ����ų��������ų�ÿ��5.1 �� 10.1
 * 
 * ʾ����ÿ60s����һ�����񣬵�����һ�������ų����⡣
 */
public class Demo3 {
	public static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		
		// ��Ϊ10.1��5.1 ����Ϊ���ڣ����ԣ�ʹ��AnnualCalendar
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
		
		//��schedulerע������
		scheduler.addCalendar("holidays", cal, false, false); 

		// ���ù����࣬��һ�ֶ���SimpleTrigger��ʽ��ÿ60s����һ��
		Trigger trigger = TriggerUtils.makeImmediateTrigger("myTrigger", -1, 60000);
		// �� ������ Ӧ��ָ����������
		trigger.setCalendarName("holidays");
		
		
		JobDetail jobDetail = new JobDetail("Demo1Job2", Scheduler.DEFAULT_GROUP, Demo1Job2.class);
		
		//��schedulerע��job��trigger
		scheduler.scheduleJob(jobDetail, trigger);
		
		scheduler.start();
	}
}
