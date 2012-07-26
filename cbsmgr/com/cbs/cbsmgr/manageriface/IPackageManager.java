package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IPackageManager extends IBaseManager {

	public List getPackagesByDescription(String description);
}
