/*
 * 
 */
package com.soaplat.sysmgr.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.soaplat.sysmgr.common.IBaseDAO;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class DAOImpl.
 * Description	:定义所有DAO对象的实现
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class DAOImpl extends HibernateDaoSupport implements IBaseDAO {

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#delete(java.lang.Object)
	 */
	public void delete(Object object) {
		this.getHibernateTemplate().delete(object);

	}


	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#save(java.lang.Object)
	 */
	public void save(Object object) {
		this.getHibernateTemplate().save(object);
		
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#update(java.lang.Object)
	 */
	public void update(Object object) {
		this.getHibernateTemplate().update(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#findAll(java.lang.String)
	 */
	public List findAll(String entityname,String ordercolname) {
		List list = new ArrayList();
		list=this.getHibernateTemplate().find("from "+entityname+" as t order by t."+ordercolname);
		return list;
	}
	
	/**
	 * HuangBo addition by 2010年10月8日 10时34分
	 * @param entityname 
	 * @param ordercolname
	 * @param orderType
	 * @return
	 */
	public List findAll(String entityname,String ordercolname, String orderType) {
		return this.getHibernateTemplate().find("from "+entityname+" order by "+ordercolname + " " + orderType);
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#findByProperty(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String entityname, String propertyName,
			Object value) {
		 String queryString = "from "+entityname+" as t where t." 
				+ propertyName + "= ?";
		Query queryObject =this.getSession().createQuery(queryString);
		queryObject.setParameter(0, value);
		return queryObject.list();
	}
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#getById(java.lang.Class, java.lang.String)
	 */
	public Object getById(Class entityclass, String pkid) {
		
		Object object=this.getHibernateTemplate().get(entityclass, pkid);
		Hibernate.initialize(object);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#loadById(java.lang.Class, java.lang.String)
	 */
	public Object loadById(Class entityclass, String pkid) {

		
		Object object=this.getHibernateTemplate().load(entityclass, pkid);
		
		Hibernate.initialize(object);
		return object;
	}
	
	public Object getById(Class entityclass, Long pkid) {
		
		Object object=this.getHibernateTemplate().get(entityclass, pkid);
		Hibernate.initialize(object);
		return object;
	}

	public Object loadById(Class entityclass, Long pkid) {

		
		Object object=this.getHibernateTemplate().load(entityclass, pkid);
		
		Hibernate.initialize(object);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#deleteById(java.lang.Class, java.lang.String)
	 */
	public void deleteById(Class entityclass, String pkid) {
		Object object=this.getById(entityclass, pkid);
		this.getHibernateTemplate().delete(object);
		
	}

	/* (non-Javadoc)
 * @see com.soaplat.sysmgr.common.IBaseDAO#queryByNamedQuery(java.lang.String, java.lang.Object)
 */
	public List queryByNamedQuery(String queryname, Object paramobject) {
		
		Query query=this.getSession().getNamedQuery(queryname);
		
		Map map = new HashMap();
		map=(HashMap)paramobject;
		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    Object key = entry.getKey();
		    Object val = entry.getValue();
		    query.setParameter(key.toString(), val);
		}
		List list=query.list();
		return list;
		
	}
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#findbyExample(java.lang.Object)
	 */
	public List findbyExample(Object exampleentity) {
		List list=this.getHibernateTemplate().findByExample(exampleentity);
		return list;

	}


	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	public List findByCriteria(DetachedCriteria criteria) {
		List list=this.getHibernateTemplate().findByCriteria(criteria);
		return list;
	}


	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IBaseDAO#getMaxPropertyValue(java.lang.String, java.lang.String)
	 */
	public String getMaxPropertyValue(String entityname, String propertyname) 
	{
		String hql = "select max(" + propertyname + ") from " + entityname;
		
		// Edited by Andy at 20100111 14:16
		String str;
		Object object = this.getSession().createQuery(hql).uniqueResult();
		if (object == null) {
			str = "";
		} else {
			str = object.toString();
		}
		return str;
	}


	public List executeHQL(String querystring) {
//		Query queryObject =this.getSession().createQuery(querystring);
//		List list=queryObject.list();
		return this.getSession().createQuery(querystring).list();
		
	}


	public List queryByNamedQuery(String queryname) {
		Query query=this.getSession().getNamedQuery(queryname);
		return query.list();
	}
	
	public Criteria returnCriteria(String entityName)
	{
		return this.getSession().createCriteria(entityName);
	}

	public List queryByCriteria(Criteria crit) {
		// TODO Auto-generated method stub
		return crit.list();
	}


	public int updateByNamedQuery(String queryname, Object paramobject) {
		
		Query query=this.getSession().getNamedQuery(queryname);
		Map map = new HashMap();
		map=(HashMap)paramobject;
		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    Object key = entry.getKey();
		    Object val = entry.getValue();
		    query.setParameter(key.toString(), val);
		}
		int resultnum=query.executeUpdate();
		return resultnum;
	}
	
	// 分页查询，Edited by Andy at 20091224 10:15
	// 关于oracle分页查询的说明：
	// oracle的分页查询是3层嵌套的：先排序、后取指定数量的记录
	// 例如：select * from (select * from (select * from user order by userName) where rownum <= 20) where rownum >=10
	// 而非：select * from user where rownum <= 20 and rownum > 10 order by userName
	public List queryByNamedQueryWithCountLimited(
			String queryname, 				// Hql名称
			Object paramobject, 			// 参数
			int firstResult, 				// 首条记录位置
			int maxResults					// 返回记录数量
			) 
	{
		Query query = this.getSession().getNamedQuery(queryname);
		
		Map map = new HashMap();
		map = (HashMap)paramobject;
		
		// 输入参数
		for (Iterator iter = map.entrySet().iterator(); iter.hasNext(); ) 
		{
		    Map.Entry entry = (Map.Entry) iter.next();
		    Object key = entry.getKey();
		    Object val = entry.getValue();
		    query.setParameter(key.toString(), val);
		}
		
		// 输入分页参数
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		
		List list = query.list();
		return list;
	}

	/**
	 * HuangBo addition by 2010年8月30日 13时46分
	 * @param hql 直接用HQL语句进行查询, 非命名查询
	 * @param params 可绑定集合参数
	 * @return
	 */
	public List<?> query(final String hql, final Map<String, Object> params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<?>>() {
			@SuppressWarnings("rawtypes")
			public List<?> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
//				query.setMaxResults(50);
				for (Iterator iter = params.entrySet().iterator(); iter.hasNext();) {
				    Map.Entry entry = (Map.Entry) iter.next();
				    String key = (String) entry.getKey();
				    if (entry.getValue() instanceof List) {
				    	query.setParameterList(key, (List<?>)entry.getValue());
					} else {
						query.setParameter(key, entry.getValue());
					};
				}
				return query.list();
			}
		});
	}
	
	/**
	 * HuangBo addition by 2010年8月30日 17时4分
	 * 用于绑定集合参数
	 * @param hql 命名HQL
	 * @param params 
	 * @return
	 */
	public List<?> queryNamed(final String hql, final Map<String, Object> params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<?>>() {
			@SuppressWarnings("rawtypes")
			public List<?> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.getNamedQuery(hql);
//				query.setMaxResults(50);
				for (Iterator iter = params.entrySet().iterator(); iter.hasNext();) {
				    Map.Entry entry = (Map.Entry) iter.next();
				    String key = (String) entry.getKey();
				    if (entry.getValue() instanceof List) {
				    	query.setParameterList(key, (List<?>)entry.getValue());
					} else {
						query.setParameter(key, entry.getValue());
					};
				}
				return query.list();
			}
		});
	}
	
	/**
	 * HuangBo addition by 2010年11月26日 16时20分
	 * 用SQL语句查询
	 * @param sql 用于查询的SQL语句,非命名
	 * @param params 可绑定集合参数
	 * @return
	 */
	public List<?> queryBySQL(final String sql, final Map<String, Object> params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<?>>() {
			@SuppressWarnings({ "rawtypes" })
			public List doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				if (null != params) {
					for (Iterator iter = params.entrySet().iterator(); iter.hasNext();) {
						Map.Entry entry = (Map.Entry) iter.next();
					    String key = (String) entry.getKey();
					    if (entry.getValue() instanceof List) {
					    	sqlQuery.setParameterList(key, (List<?>)entry.getValue());
						} else {
							sqlQuery.setParameter(key, entry.getValue());
						};
					}
				}
				return sqlQuery.list();
			}
		});
	}
	
	/**
	 * HuangBo addition by 2010年12月9日 15时58分
	 * 执行SQL语句, 非命名
	 * @param sql  执行SQL语句,非命名, 非查询
	 * @param params 可绑定集合参数
	 * @return
	 */
	public int executeSQL(final String sql, final Map<String, Object> params) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@SuppressWarnings({ "rawtypes" })
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				if (null != params) {
					for (Iterator iter = params.entrySet().iterator(); iter.hasNext();) {
						Map.Entry entry = (Map.Entry) iter.next();
					    String key = (String) entry.getKey();
					    if (entry.getValue() instanceof List) {
					    	sqlQuery.setParameterList(key, (List<?>)entry.getValue());
						} else {
							sqlQuery.setParameter(key, entry.getValue());
						};
					}
				}
				return sqlQuery.executeUpdate();
			}
		});
	}

	/**
	 * 根据指定属性名和值查询对象是否存在
	 * @param entityName 查询是否存在的对象
	 * @param propertyName 对象属性名
	 * @param propertyValue 对象属性值
	 * @return 是否存在该对象
	 */
	public boolean isExist(String entityName, String propertyName, Object propertyValue) {
		String hql = "select count(*) from " + entityName + " as t where t." + propertyName + " = ?";
		Query query =this.getSession().createQuery(hql);
		query.setParameter(0, propertyValue);
		List<?> list = query.list();
		
		if (0 < list.size()) {
			if (0 < Long.valueOf(list.get(0).toString())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
