package com.cdsmgr.serviceimpl;

import java.util.List;


import com.cbs.cbsmgr.manageriface.IProductManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class PackageListService 
{

	public IProductManager productManager = null;
	
	public PackageListService()
	{
		productManager=(IProductManager)ApplicationContextHolder.webApplicationContext.getBean("productManager");
	}
	public List FindPackageId(String propertyName, Object value)
	{
		List list=productManager.findByProperty(propertyName, value);
		return list;
	}
	
}

