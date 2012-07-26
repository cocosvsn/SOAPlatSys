package com.soaplat.sysmgr.manageriface;

import java.util.List;

/**
 * Title 		:the Interface IGroupRoleRelManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IGroupRoleRelManager extends IBaseManager {
	public List getGroupRoles(java.lang.String  groupid);
}
