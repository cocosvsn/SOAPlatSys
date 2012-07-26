package com.soaplat.cmsmgr.ConfigService;

import java.util.List;

import com.soaplat.cmsmgr.manageriface.IPackStyleFileTypeManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class PackStyleFileTypeService implements IPackStyleFileTypeService {

	public IPackStyleFileTypeManager packStyleFileTypeManager=null;
	public PackStyleFileTypeService()
	{
		packStyleFileTypeManager = (IPackStyleFileTypeManager)ApplicationContextHolder.webApplicationContext.getBean("packStyleFileTypeManager");	
	}
	
	
	public List searchPackStyleFileTypeAll() {
		List searchList=packStyleFileTypeManager.findAll();
		return searchList;
	}

}
