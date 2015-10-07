package app.quartz.demo;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Demo1Job1 implements Job {
	
	/**
	 * JobExecutionContext ��������ʱ����������ע�ᵽ Scheduler ����� Job ������� JobDetail �� Trigger
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();   

        String jobName = jobDetail.getName();  // Demo3Job1 ����
        System.out.println("��Name:�� " + jobDetail.getFullName()); // DEFAULT.Demo3Job1   ��+����
        System.out.println("��Job Class:�� " + jobDetail.getJobClass()); // com.test.demo.Demo3Job1
        
        // ���ε��ÿ�ʼʱ��
        System.out.println(jobName + " ��fired at�� " + context.getFireTime());
        // �´ε��ÿ�ʼʱ��
        System.out.println("��Next fire time�� " + context.getNextFireTime());   
        
        // getJobDataMap ����
        System.out.println("Demo1Job1:" + jobDetail.getJobDataMap().get("param"));
	}

}
