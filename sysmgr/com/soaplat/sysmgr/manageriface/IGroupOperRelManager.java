package com.soaplat.sysmgr.manageriface;

import java.util.List;

/**
 * Title 		:the Interface IGroupOperRelManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IGroupOperRelManager extends IBaseManager {
	public List getGroupOperators(java.lang.String  groupid);
	public List getGroupRoleOperators(java.lang.String  groupid,java.lang.String roleid);
}
