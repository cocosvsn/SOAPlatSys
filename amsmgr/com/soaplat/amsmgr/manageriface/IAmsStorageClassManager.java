package com.soaplat.amsmgr.manageriface;

import java.util.List;
import com.soaplat.sysmgr.manageriface.IBaseManager;

/**
 * Title 		:the Interface IAmsStorageClassManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IAmsStorageClassManager extends IBaseManager {
	/**
	 * 查询所有有效存储等级
	 * @return
	 */
	public List<?> queryAllValidateStorage();
}
