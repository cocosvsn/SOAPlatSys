package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface ICmsServiceManager extends IBaseManager {

	public List getCmsServicesByProgramCategoryId(String genre);
	
	public List getCmsServicesByProductid(String productid);
//	
//	public void savetest1();
//	public void savetest2();
//	
//	public void savetransactionTest(IProgPackageManager progPackageManager);
}
