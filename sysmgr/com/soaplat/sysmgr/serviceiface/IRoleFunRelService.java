package com.soaplat.sysmgr.serviceiface;

import java.util.List;

/**
 * Title 		:the Interface IRoleFunRelService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IRoleFunRelService extends IBaseService {
	public List getRoleFunResources(java.lang.String  roleid);
}
