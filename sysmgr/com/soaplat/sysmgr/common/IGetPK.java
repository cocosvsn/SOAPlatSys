/*
 * 
 */
package com.soaplat.sysmgr.common;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Interface IGetPK.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IGetPK {
	
	/**
	 * Gets the table pk.
	 * 
	 * @param entityname the entityname
	 * 
	 * @return the string
	 */
	public String GetTablePK(String entityname);
	
	/**
	 * Gets the table pk.
	 * 
	 * @param entityname the entityname
	 * @param currentmax the currentmax
	 * 
	 * @return the string
	 */
	public String GetTablePK(String entityname,String currentmax);
}
