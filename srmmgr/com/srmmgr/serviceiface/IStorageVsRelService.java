package com.srmmgr.serviceiface;

import java.util.List;

import com.soaplat.sysmgr.common.IBaseService;

/**
 * Title 		:the Interface IStorageVsRelService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IStorageVsRelService extends IBaseService {
	public List getVsStorages(java.lang.String vodsid);
}
