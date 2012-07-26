package com.soaplat.cmsmgr.serviceiface;

import java.util.List;



// TODO: Auto-generated Javadoc
/**
 * Title 		:the Interface IBaseService.
 * Description	:瀹氫箟瀵瑰寮�鏀剧殑鏈嶅姟鎿嶄綔鎺ュ彛
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IBaseService {
	
	/**
	 * Save:淇濆瓨瀵硅薄
	 * 
	 * @param object the object
	 * 
	 * @return the object
	 */
	 public Object save(Object object) ;
	 
 	/**
	  * Save ret all:淇濆瓨瀵硅薄锛屽苟妫�绱㈣繑鍥炲綋鍓嶅璞＄殑鎵�鏈夊垪琛�
	  * 
	  * @param object the object
	  * 
	  * @return the list
	  */
 	public List saveRetAll(Object object);
	 
		 /**
 		 * Delete:鍒犻櫎涓�缁勫璞�
 		 * 
 		 * @param object the object
 		 */
	  public void delete(Object[] object);
	  
  	/**
	   * Delete ret all:鍒犻櫎涓�缁勫璞★紝骞舵绱㈣繑鍥炲綋鍓嶅璞＄殑鎵�鏈夊垪琛�
	   * 
	   * @param object the object
	   * 
	   * @return the list
	   */
  	public List deleteRetAll(Object[] object);
	
	/**
	 * Delete by id锛氭牴鎹璞＄殑涓婚敭鍒犻櫎瀵硅薄
	 * 
	 * @param pkid the pkid
	 */
	public void deleteById(java.lang.String pkid);
	
	/**
	 * Delete by id ret all锛氭牴鎹璞＄殑涓婚敭鍒犻櫎瀵硅薄锛屽苟妫�绱㈣繑鍥炲綋鍓嶅璞＄殑鎵�鏈夊垪琛�
	 * 
	 * @param pkid the pkid
	 * 
	 * @return the list
	 */
	public List deleteByIDRetAll(java.lang.String pkid);
	
	/**
	 * Update锛氭洿鏂板璞�
	 * 
	 * @param object the object
	 */
	public void update(Object object);
	
	/**
	 * Update ret all锛氭洿鏂板璞★紝骞舵绱㈣繑鍥炲綋鍓嶅璞＄殑鎵�鏈夊垪琛�
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List updateRetAll(Object object);
	 
 	/**
	  * Gets the by id锛氭牴鎹璞′富閿煡璇㈠璞�
	  * 
	  * @param pkid the pkid
	  * 
	  * @return the by id
	  */
	public Object getById(java.lang.String pkid) ;   
	 
	 /**
 	 * Load by id锛氭牴鎹璞′富閿煡璇㈠璞★紝鏀寔寤舵椂鍔犺浇
 	 * 
 	 * @param pkid the pkid
 	 * 
 	 * @return the object
 	 */
	public Object loadById(java.lang.String pkid) ; 
	/**
	 * Findby example:	鎶婂凡缁忚缃煇浜涘睘鎬х殑瀵硅薄浣滀负妯℃澘锛屾煡璇㈣窡杩欎釜瀵硅薄宸叉湁灞炴�у�肩浉鍚岀殑鍒楄〃
	 * 
	 * @param exampleentity the exampleentity
	 * 
	 * @return the list
	 */
	public List findbyExample(Object exampleentity);
	 
	 /**
 	 * Find by property锛氭牴鎹璞＄殑鏌愪釜灞炴�э紝鏌ヨ绗﹀悎鏉′欢鐨勫璞″垪琛�
 	 * 
 	 * @param propertyName the property name
 	 * @param value the value
 	 * 
 	 * @return the list
 	 */
	public List findByProperty(String propertyName, Object value) ;
	
	/**
	 * Find all锛氭煡璇㈡煇涓璞＄殑鎵�鏈夊垪琛�
	 * 
	 * @return the list
	 */
	public List findAll() ;
	
	/**
	 * Update:鏇存柊涓�缁勫璞�
	 * 
	 * @param object the object
	 */
	public void update(Object[] object);
	
	/**
	 * Update ret all:鏇存柊涓�缁勫璞★紝骞舵绱㈣繑鍥炲綋鍓嶅璞＄殑鎵�鏈夊垪琛�
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List updateRetAll(Object[] object);
	
	/**
	 * Save锛氭洿鏂颁竴缁勫璞�
	 * 
	 * @param object the object
	 */
	public void save(Object[] object) ;
	
	/**
	 * Save ret all锛氭洿鏂颁竴缁勫璞★紝骞舵绱㈣繑鍥炲綋鍓嶅璞＄殑鎵�鏈夊垪琛�
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List saveRetAll(Object[] object);
}
