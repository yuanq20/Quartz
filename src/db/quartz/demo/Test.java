package db.quartz.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import app.quartz.demo.Demo1Job2;

/**
 * ���裺
 * 1������quartz.properties������db�������
 * 2��ִ��<quartz-download>/docs/dbTables��tables_mysql_innodb.sql�ű�
 * 3����RAMJobStoreʱ������һ����ֻ�Ƕ�ʱ�������db
 * 
 ******	����Job��Ϣ�����ַ�ʽ��*******
 *	�� ������Ӳ�������Job
 *	�� ʹ��JobInitializationPlugin
 *	�� ʹ��Quartz Webͼ�ν������
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
			// ��ȡָ���ļ�
			StdSchedulerFactory factory = new StdSchedulerFactory("db/quartz/demo/quartz_.properties");   
			Scheduler scheduler = factory.getScheduler();
			
			// ��ȡ��Ŀ¼��quartz.properties�ļ�
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
