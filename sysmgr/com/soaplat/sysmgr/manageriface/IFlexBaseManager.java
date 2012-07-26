package com.soaplat.sysmgr.manageriface;

import java.util.List;

import flex.messaging.io.ArrayCollection;



// TODO: Auto-generated Javadoc
/**
 * Title 		:the Interface IBaseManager.
 * Description	:定义所有POJO对象的Manager接口
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IFlexBaseManager {
  
 	
	 /**
 	 * Delete：删除对象
 	 * 
 	 * @param object the object
 	 */
  public void delete(ArrayCollection arr);
  

	/**
 * Update:更新一组对象
 * 
 * @param object the object
 */
public void update(ArrayCollection arr);

/**
 * Save:保存一组对象
 * 
 * @param object the object
 */
public void save(ArrayCollection arr) ;

}
