package com.soaplat.amsmgr.serviceiface;

import java.util.List;

import com.soaplat.sysmgr.serviceiface.IBaseService;

/**
 * Title 		:the Interface IAmsStorageDirService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IAmsStorageDirService extends IBaseService{
	public Object[] findstorageanddirlistbystorageclass(String stclasscode,String filecode);

}
