package spring.quartz.demo2;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

public class MyJob implements Job {
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String size = (String)context.getJobDetail().getJobDataMap().get("size");
		System.out.println(size); //10

		ApplicationContext app = (ApplicationContext)context.getJobDetail().getJobDataMap().get("appKey");
		//Xxx xx = app.getBean("xxx");
	}

}
