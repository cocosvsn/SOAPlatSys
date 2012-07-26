/*
 * 
 */
package com.soaplat.sysmgr.common;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class InitApplicationContext.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class InitApplicationContext extends HttpServlet implements Servlet {
	
	/** The web application context. */
	public static WebApplicationContext webApplicationContext=null;
	
	/**
	 * Instantiates a new inits the application context.
	 */
	public InitApplicationContext(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException{
		super.init();
		try{
			
			ApplicationContextHolder.webApplicationContext=WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			System.out.println("***********InitApplicationContext.WebApplicationContextUtils*************");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
