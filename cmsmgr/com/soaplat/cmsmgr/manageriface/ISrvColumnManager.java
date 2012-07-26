package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface ISrvColumnManager extends IBaseManager {

	
	public List getPortalColumnsBySrvid(String srvid);
	
	// 根据栏目id，得到栏目与服务的配置关系 SrvColumn
	public List getCmsServicesByColumnclassid(String columnclassid);
}
