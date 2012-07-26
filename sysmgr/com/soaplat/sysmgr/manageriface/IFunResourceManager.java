package com.soaplat.sysmgr.manageriface;

import java.util.List;

/**
 * Title 		:the Interface IFunResourceManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IFunResourceManager extends IBaseManager {
 
 /**
  * Gets the func type：获取所有的功能类别列表
  * 
  * @return the func type
  */
 public List getFuncType();
 
 /**
  * Gets the fun resource type list：获取所有的功能类别以及每个功能类别对应功能集合
  * 
  * @return the fun resource type list
  */
 public List getFunResourceTypeList();
}
