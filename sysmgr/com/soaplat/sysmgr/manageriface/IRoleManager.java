package com.soaplat.sysmgr.manageriface;

import java.util.List;

/**
 * Title :the Interface IRoleManager. Description : Copyright :copyright (c)
 * 2009 Company :SMET Create Date :2009-06-16
 * 
 * @author :SOAPlat Group (Fanyanhua)
 * @version :1.0
 */
public interface IRoleManager extends IBaseManager {

	// andy 20091214 12:49
	public List getRolesByUserId(String userId);
}
