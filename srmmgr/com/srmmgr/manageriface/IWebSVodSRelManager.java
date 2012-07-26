package com.srmmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.common.IBaseManager;

/**
 * Title 		:the Interface IWebSVodSRelManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IWebSVodSRelManager extends IBaseManager {
	
	/**
	 * Gets the web s vod ss：通过websid获取某个Web Server对应的Vod Server列表
	 * 
	 * @param websid the websid
	 * 
	 * @return the web s vod ss
	 */
	public List getWebSVodSs(java.lang.String websid);
	
	
}
