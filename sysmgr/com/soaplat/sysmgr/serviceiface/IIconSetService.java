package com.soaplat.sysmgr.serviceiface;

import java.util.List;

/**
 * Title 		:the Interface IIconSetService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IIconSetService extends IBaseService {
	/**
	 * Gets the icon set name：获取所有的不同图标集名称
	 * 
	 * @return the icon set name
	 */
	public List getIconSetName();
	
	/**
	 * Gets the icon set name list：获取所有的不同图标集名称以及各个名称对应的图标集列表
	 * 
	 * @return the icon set name list
	 */
	public List getIconSetNameList();
}
