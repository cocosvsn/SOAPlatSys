
package com.soaplat.sysmgr.common;

import org.springframework.web.context.WebApplicationContext;

import com.soaplat.sysmgr.manageriface.IDictManager;
import com.soaplat.sysmgr.manageriface.IOperatorManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ApplicationContextHolder.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ApplicationContextHolder {
	
//	/** The web application context. */
	/** The web application context. */
public static WebApplicationContext webApplicationContext=null;
//	
//	/**
//	 * Gets the operator service.
//	 * 
//	 * @return the operator service
//	 */
//	public static IDictManager getDictService(){
//		IDictManager dictManager=(IDictManager)webApplicationContext.getBean("dictManager");
//		System.out.println("****ACHoler:IDictManager:dictManager****");
//		return dictManager;
//	}
//	public static IOperatorManager getOperatorService(){
//		IOperatorManager operatorManager=(IOperatorManager)webApplicationContext.getBean("operatorManager");
//		System.out.println("****ACHoler:IOperatorManager:operatorManager****");
//		return operatorManager;
//	}
}
