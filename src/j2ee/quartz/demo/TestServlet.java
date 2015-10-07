package j2ee.quartz.demo;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;

public class TestServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext ctx = request.getSession().getServletContext();
		
		// 获取放在 ServletContext 中的 schdulerFactory
		StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);
		
		Scheduler scheduler = factory.getScheduler();
		
		scheduler.start();//当然，web.xml配置文件中，已经启动了
		
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
