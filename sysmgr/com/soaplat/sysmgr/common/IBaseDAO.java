/*
 * 
 */
package com.soaplat.sysmgr.common;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Title :the Interface IBaseDAO. Description : Copyright :copyright (c) 2009
 * Company :SMET Create Date :2009-06-16
 * 
 * @author :SOAPlat Group (Fanyanhua)
 * @version :1.0
 */

public interface IBaseDAO {

	/**
	 * Save.
	 * 
	 * @param object
	 *            the object
	 */
	public void save(Object object);

	/**
	 * Delete.
	 * 
	 * @param object
	 *            the object
	 */
	public void delete(Object object);

	/**
	 * Delete by id.
	 * 
	 * @param entityclass
	 *            the entityclass
	 * @param pkid
	 *            the pkid
	 */
	public void deleteById(Class entityclass, java.lang.String pkid);

	/**
	 * Update.
	 * 
	 * @param object
	 *            the object
	 */
	public void update(Object object);

	/**
	 * Gets the by id.
	 * 
	 * @param entityclass
	 *            the entityclass
	 * @param pkid
	 *            the pkid
	 * 
	 * @return the by id
	 */
	public Object getById(Class entityclass, java.lang.String pkid);

	/**
	 * Load by id.
	 * 
	 * @param entityclass
	 *            the entityclass
	 * @param pkid
	 *            the pkid
	 * 
	 * @return the object
	 */
	public Object loadById(Class entityclass, java.lang.String pkid);

	/**
	 * Find by property.
	 * 
	 * @param entityname
	 *            the entityname
	 * @param propertyName
	 *            the property name
	 * @param value
	 *            the value
	 * 
	 * @return the list
	 */
	public List findByProperty(java.lang.String entityname,
			String propertyName, Object value);

	/**
	 * Find all.
	 * 
	 * @param entityname
	 *            the entityname
	 * @param ordercolname
	 *            the ordercolname
	 * 
	 * @return the list
	 */
	public List findAll(java.lang.String entityname,
			java.lang.String ordercolname);

	/**
	 * HuangBo addition by 2010年10月8日 10时34分
	 * @param entityname 
	 * @param ordercolname
	 * @param orderType
	 * @return
	 */
	public List findAll(String entityname,String ordercolname, String orderType);
	
	/**
	 * Query by named query.
	 * 
	 * @param queryname
	 *            the queryname
	 * @param paramobject
	 *            the paramobject
	 * 
	 * @return the list
	 */
	public List queryByNamedQuery(java.lang.String queryname, Object paramobject);

	/**
	 * Findby example.
	 * 
	 * @param exampleentity
	 *            the exampleentity
	 * 
	 * @return the list
	 */
	public List findbyExample(Object exampleentity);

	/**
	 * Find by criteria.
	 * 
	 * @param criteria
	 *            the criteria
	 * 
	 * @return the list
	 */
	public List findByCriteria(DetachedCriteria criteria);

	/**
	 * Gets the max property value.
	 * 
	 * @param entityname
	 *            the entityname
	 * @param propertyname
	 *            the propertyname
	 * 
	 * @return the max property value
	 */
	public String getMaxPropertyValue(java.lang.String entityname,
			java.lang.String propertyname);

	/**
	 * Execute hql.
	 * 
	 * @param querystring
	 *            the querystring
	 * 
	 * @return the list
	 */
	public List executeHQL(java.lang.String querystring);

	/**
	 * Query by named query.
	 * 
	 * @param queryname
	 *            the queryname
	 * 
	 * @return the list
	 */
	public List queryByNamedQuery(String queryname);

	public List queryByCriteria(Criteria crit);

	public Criteria returnCriteria(String entityName);

	public Object getById(Class entityclass, java.lang.Long pkid);

	public Object loadById(Class entityclass, java.lang.Long pkid);

	public int updateByNamedQuery(String queryname, Object paramobject);

	// 分页查询，Edited by Andy at 20091224 10:15
	// 关于oracle分页查询的说明：
	// oracle的分页查询是3层嵌套的：先排序、后取指定数量的记录
	// 例如：select * from (select * from (select * from user order by userName)
	// where rownum <= 20) where rownum >=10
	// 而非：select * from user where rownum <= 20 and rownum > 10 order by
	// userName
	public List queryByNamedQueryWithCountLimited(String queryname, // Hql名称
			Object paramobject, // 参数
			int firstResult, // 首条记录位置
			int maxResults // 返回记录数量
	);
	
	/**
	 * HuangBo addition by 2010年8月30日 13时46分
	 * @param hql 直接用HQL语句进行查询, 非命名查询
	 * @param params
	 * @return
	 */
	public List<?> query(final String hql, final Map<String, Object> params);
	
	/** HuangBo addition by 2010年8月30日 17时4分
	 * 用于绑定集合参数
	 * @param hql 命名HQL
	 * @param params 
	 * @return
	 */
	public List<?> queryNamed(final String hql, final Map<String, Object> params);
	
	/**
	 * HuangBo addition by 2010年11月26日 16时20分
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<?> queryBySQL(final String sql, final Map<String, Object> params);
	
	/**
	 * HuangBo addition by 2010年12月9日 15时58分
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeSQL(final String sql, final Map<String, Object> params);
	
	/**
	 * 根据指定属性名和值查询对象是否存在
	 * @param entityName 查询是否存在的对象
	 * @param propertyName 对象属性名
	 * @param propertyValue 对象属性值
	 * @return 是否存在该对象
	 */
	public boolean isExist(String entityName, 
			String propertyName, Object propertyValue);
}
