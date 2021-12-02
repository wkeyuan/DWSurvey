package net.diaowen.common.utils.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.diaowen.common.utils.DiaowenProperty;

public class InitAppliction implements ServletContextListener {

	public  static  String contextPath = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContext servletContext = sce.getServletContext();
		contextPath = servletContext.getContextPath();

	}



}
