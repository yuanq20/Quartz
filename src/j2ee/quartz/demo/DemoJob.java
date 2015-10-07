package j2ee.quartz.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DemoJob implements Job {
	
	/**
	 * J2EEʱ��ͨ����дServlet������ʱ��ȡ�����ļ���������ʱ����
	 * ���ԣ�����ֻ��Ҫдʵ��ҵ���job
	 * 
	 * ���������Լ�дServlet��quartz����Ѿ��������ṩ��org.quartz.ee.servlet.QuartzInitializerServlet
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("invoke...");
	}

}
