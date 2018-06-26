package com.key.common.utils.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.key.common.utils.DiaowenProperty;

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
		System.out.println("getContextPath:"+servletContext.getContextPath());
		contextPath = servletContext.getContextPath();
		
	}

	
	
}
