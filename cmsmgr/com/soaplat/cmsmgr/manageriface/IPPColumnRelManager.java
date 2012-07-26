package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IPPColumnRelManager extends IBaseManager {

	public List getPpColumnRelsByProductIdAndColumnclassid(String productId, String columnclassid);
	
	public List getProgPackagesByColumnclassid(String columnclassid);
}
