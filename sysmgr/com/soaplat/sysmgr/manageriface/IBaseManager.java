package com.soaplat.sysmgr.manageriface;

import java.util.List;



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
public interface IBaseManager {
  
  /**
   * Save:保存对象
   * 
   * @param object the object
   * 
   * @return the object
   */
 	public Object save(Object object) ;
 	
	 /**
 	 * Delete：删除对象
 	 * 
 	 * @param object the object
 	 */
  public void delete(Object[] object);
  
  /**
   * Delete by id：根据对象的主键删除对象
   * 
   * @param pkid the pkid
   */
public void deleteById(java.lang.String pkid);
//	���¶���
	/**
 * Update：更新对象
 * 
 * @param object the object
 */
public void update(Object object);

    /**
 * Gets the by id：根据对象主键查询对象
 * 
 * @param pkid the pkid
 * 
 * @return the by id
 */
public Object getById(java.lang.String pkid) ;   

    /**
 * Load by id：根据对象主键查询对象，支持延时加载
 * 
 * @param pkid the pkid
 * 
 * @return the object
 */
public Object loadById(java.lang.String pkid) ; 
/**
 * Findby example:	把已经设置某些属性的对象作为模板，查询跟这个对象已有属性值相同的列表
 * 
 * @param exampleentity the exampleentity
 * 
 * @return the list
 */
public List findbyExample(Object exampleentity);
 
 /**
  * Find by property：根据对象的某个属性，查询符合条件的对象列表
  * 
  * @param propertyName the property name
  * @param value the value
  * 
  * @return the list
  */
public List findByProperty(String propertyName, Object value) ;

	/**
 * Find all：查询某个对象的所有列表
 * 
 * @return the list
 */
public List findAll() ;

	/**
 * Update:更新一组对象
 * 
 * @param object the object
 */
public void update(Object[] object);
//	������
	/**
 * Save:保存一组对象
 * 
 * @param object the object
 */
public void save(Object[] object) ;

}
