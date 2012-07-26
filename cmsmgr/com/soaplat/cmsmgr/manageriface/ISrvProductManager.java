package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface ISrvProductManager extends IBaseManager {

	public List getProductCategoriesBySrvid(String srvid);
	
	public List getCmsServicesByProductCategoryId(Long productCategoryId);
}
