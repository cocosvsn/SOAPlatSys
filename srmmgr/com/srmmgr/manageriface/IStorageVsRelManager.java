package com.srmmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.common.IBaseManager;

/**
 * Title 		:the Interface IStorageVsRelManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IStorageVsRelManager extends IBaseManager {
	
	/**
	 * Gets the vs storages:通过vodsid获取某个Vod Server对应的存储体对象列表
	 * 
	 * @param vodsid the vodsid
	 * 
	 * @return the vs storages
	 */
	public List getVsStorages(java.lang.String vodsid);
}
