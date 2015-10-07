package app.quartz.demo;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Demo1Job1 implements Job {
	
	/**
	 * JobExecutionContext 代表运行时环境，包括注册到 Scheduler 上与该 Job 相关联的 JobDetail 和 Trigger
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();   

        String jobName = jobDetail.getName();  // Demo3Job1 名称
        System.out.println("【Name:】 " + jobDetail.getFullName()); // DEFAULT.Demo3Job1   组+名称
        System.out.println("【Job Class:】 " + jobDetail.getJobClass()); // com.test.demo.Demo3Job1
        
        // 本次调用开始时间
        System.out.println(jobName + " 【fired at】 " + context.getFireTime());
        // 下次调用开始时间
        System.out.println("【Next fire time】 " + context.getNextFireTime());   
        
        // getJobDataMap 传递
        System.out.println("Demo1Job1:" + jobDetail.getJobDataMap().get("param"));
	}

}
