package com.cbs.cbsmgr.serviceiface;

import java.util.List;



// TODO: Auto-generated Javadoc
/**
 * Title 		:the Interface IBaseService.
 * Description	:定义对外�?放的服务操作接口
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IBaseService {
	
	/**
	 * Save:保存对象
	 * 
	 * @param object the object
	 * 
	 * @return the object
	 */
	 public Object save(Object object) ;
	 
 	/**
	  * Save ret all:保存对象，并�?索返回当前对象的�?有列�?
	  * 
	  * @param object the object
	  * 
	  * @return the list
	  */
 	public List saveRetAll(Object object);
	 
		 /**
 		 * Delete:删除�?组对�?
 		 * 
 		 * @param object the object
 		 */
	  public void delete(Object[] object);
	  
  	/**
	   * Delete ret all:删除�?组对象，并检索返回当前对象的�?有列�?
	   * 
	   * @param object the object
	   * 
	   * @return the list
	   */
  	public List deleteRetAll(Object[] object);
	
	/**
	 * Delete by id：根据对象的主键删除对象
	 * 
	 * @param pkid the pkid
	 */
	public void deleteById(java.lang.String pkid);
	
	/**
	 * Delete by id ret all：根据对象的主键删除对象，并�?索返回当前对象的�?有列�?
	 * 
	 * @param pkid the pkid
	 * 
	 * @return the list
	 */
	public List deleteByIDRetAll(java.lang.String pkid);
	
	/**
	 * Update：更新对�?
	 * 
	 * @param object the object
	 */
	public void update(Object object);
	
	/**
	 * Update ret all：更新对象，并检索返回当前对象的�?有列�?
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List updateRetAll(Object object);
	 
 	/**
	  * Gets the by id：根据对象主键查询对�?
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
	 * Findby example:	把已经设置某些属性的对象作为模板，查询跟这个对象已有属�?��?�相同的列表
	 * 
	 * @param exampleentity the exampleentity
	 * 
	 * @return the list
	 */
	public List findbyExample(Object exampleentity);
	 
	 /**
 	 * Find by property：根据对象的某个属�?�，查询符合条件的对象列�?
 	 * 
 	 * @param propertyName the property name
 	 * @param value the value
 	 * 
 	 * @return the list
 	 */
	public List findByProperty(String propertyName, Object value) ;
	
	/**
	 * Find all：查询某个对象的�?有列�?
	 * 
	 * @return the list
	 */
	public List findAll() ;
	
	/**
	 * Update:更新�?组对�?
	 * 
	 * @param object the object
	 */
	public void update(Object[] object);
	
	/**
	 * Update ret all:更新�?组对象，并检索返回当前对象的�?有列�?
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List updateRetAll(Object[] object);
	
	/**
	 * Save：更新一组对�?
	 * 
	 * @param object the object
	 */
	public void save(Object[] object) ;
	
	/**
	 * Save ret all：更新一组对象，并检索返回当前对象的�?有列�?
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List saveRetAll(Object[] object);
}
