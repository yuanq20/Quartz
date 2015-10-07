package spring.quartz.demo1;


public class TestJob {
	/**
	 * 业务逻辑处理，方法不能有参数
	 */
	public void doBiz() {
		System.out.println("定时触发...");
	}
}
