package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IPackageItemManager extends IBaseManager {

	//
	public List getPackageItemsByPackageId(Long packageId);
}
