package com.soaplat.sysmgr.manageriface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title 		:the Interface IRoleOperRelManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IRoleOperRelManager extends IBaseManager {

	public List getRoleOperators(java.lang.String  roleid);
}
