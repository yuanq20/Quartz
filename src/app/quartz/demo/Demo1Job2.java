package app.quartz.demo;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Demo1Job2 implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("invoke..." + new Date());

		JobDetail jobDetail = context.getJobDetail();
		System.out.println("Demo1Job2:" + jobDetail.getJobDataMap().get("param"));
	}

}
