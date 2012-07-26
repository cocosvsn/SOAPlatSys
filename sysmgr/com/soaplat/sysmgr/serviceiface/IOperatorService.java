package com.soaplat.sysmgr.serviceiface;

import java.util.List;

import com.soaplat.sysmgr.bean.Operator;



/**
 * Title 		:the Interface IOperatorService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IOperatorService extends IBaseService {
	
	/**
	 * Gets the oper menu.
	 * 
	 * @param operatorid the operatorid
	 * 
	 * @return the oper menu
	 */
	public List getOperMenu(java.lang.String  operatorid);
	
	/**
	 * Login.
	 * 
	 * @param userid the userid
	 * @param password the password
	 * 
	 * @return the object
	 */
	public Object Login(String userid, String password) throws Exception;
	
}
