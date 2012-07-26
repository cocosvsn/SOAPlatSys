package com.cdsmgr.serviceiface;

import java.util.List;

import com.soaplat.sysmgr.serviceiface.IBaseService;

/**
 * Title 		:the Interface IAmsFileServerService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IListGenerateService extends IBaseService {
	public List findstoragedirandprogid(Object progobject,String stclasscode);
}
