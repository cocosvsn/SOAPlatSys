package com.soaplat.sysmgr.serviceiface;

import java.util.List;

import com.soaplat.sysmgr.bean.Role;

/**
 * Title :the Interface IRoleService. Description : Copyright :copyright (c)
 * 2009 Company :SMET Create Date :2009-06-16
 * 
 * @author :SOAPlat Group (Fanyanhua)
 * @version :1.0
 */
public interface IRoleService extends IBaseService {

	// andy 20091214 12:51
	public List<Role> getRolesByOperId(String operId);
}
