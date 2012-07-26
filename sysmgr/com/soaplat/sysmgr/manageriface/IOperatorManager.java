package com.soaplat.sysmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.bean.Operator;



// TODO: Auto-generated Javadoc
/**
 * Title 		:the Interface IOperatorManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IOperatorManager extends IBaseManager {
	
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
	 * @throws Exception 
	 */
	public Object Login(java.lang.String  userid,java.lang.String password) throws Exception;

}
