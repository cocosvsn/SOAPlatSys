package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.PortalRoleOperRel;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IPortalRoleOperRelManager extends IBaseManager {

	public List<PortalRoleOperRel> getPortalRoleOperRelsByRoleidAndColumnclassid(String roleid, String columnclassid);
	
	public List<PortalColumn> getPortalColumnsByRoleid(String roleid);
}
