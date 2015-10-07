package j2ee.quartz.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DemoJob implements Job {
	
	/**
	 * J2EE时，通过编写Servlet，启动时读取配置文件，启动定时器。
	 * 所以，我们只需要写实现业务的job
	 * 
	 * 不用我们自己写Servlet，quartz框架已经替我们提供了org.quartz.ee.servlet.QuartzInitializerServlet
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("invoke...");
	}

}
