package com.soaplat.amsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

/**
 * Title 		:the Interface IAmsStorageDirManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IAmsStorageDirManager  extends IBaseManager{
	public List findstoragedirbystorage(String stdirglobalid,String progfileid);
	public List findstorageanddirlistbyvsid(String vodsid);
	public Object[] findstorageanddirlistbystorageclass(String stclasscode,String filecode);

	
	// 20100202 16:07
	// 根据存储体等级code和filecode，查存储体和目录
	public List getStorageStoragedirsByStclasscodeFilecode(
			String stclasscode,
			String filecode
			);
}


