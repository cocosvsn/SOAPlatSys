/**
 * Copyright © 2012, Bunco All Rights Reserved.
 * Project: HotelVodSys
 * cn.sh.sbl.hotelvod.common.impl.CommonImpl<POJO>.java
 * Create By: Bunco
 * Create Date: 2012-5-15 上午10:13:05
 */
package cn.sh.sbl.cms.common.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.sh.sbl.cms.common.ICommon;

/**
 * @author Bunco
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0
 * @date 2012-5-15 上午10:13:05
 * @description 持久化通用类, 定义实体对象的添, 删, 改, 查等通用操作
 */
@Repository
public class CommonImpl<POJO> extends HibernateDaoSupport implements
		ICommon<POJO> {
	/**
	 * 保存实体对象
	 * @param pojo
	 */
	public Serializable save(POJO pojo) {
		return this.getHibernateTemplate().save(pojo);
	}
	
	/**
	 * 批量保存实体对象
	 * @param pojos
	 */
	public void save(POJO... pojos) {
		for (POJO pojo : pojos) {
			this.getHibernateTemplate().save(pojo);
		}
	}

	/**
	 * 删除实体对象
	 * @param clazz
	 * @param id
	 */
	public void delete(Class<POJO> clazz, Serializable... ids) {
		for (Serializable serializable : ids) {
			this.getHibernateTemplate().delete(
					this.getHibernateTemplate().load(clazz, serializable));
		}
	}

	/**
	 * 修改实体对象, 未进行持久化判断....使用时应先加载再修改
	 * @param pojo
	 */
	public void update(POJO... pojos) {
		for (POJO pojo : pojos) {
			this.getHibernateTemplate().update(pojo);
		}
	}

	/**
	 * 根据对象ID加载对象
	 * @param clazz
	 * @param id
	 * @return POJO
	 */
	public POJO load(Class<POJO> clazz, Serializable id) {
		return (POJO) this.getHibernateTemplate().load(clazz, id);
	}

	/**
	 * 根据对象ID加载对象, 和Load()功能相似, 作用范围不同
	 * @param clazz
	 * @param id
	 * @return POJO
	 */
	public POJO get(Class<POJO> clazz, Serializable id) {
		return (POJO) this.getHibernateTemplate().get(clazz, id);
	}
	
	/**
	 * 根据指定属性名和值查询对象是否存在
	 * @param entityName 查询是否存在的对象
	 * @param propertyName 对象属性名
	 * @param propertyValue 对象属性值
	 * @return 是否存在该对象
	 */
	public boolean isExist(String entityName, String propertyName, final Object propertyValue) {
		final String hql = "select count(*) from " + entityName + " as t where t." + propertyName + " = ?";
		List<?> list = this.getHibernateTemplate().execute(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				CommonImpl.this.bindParameter(query, propertyValue);
				return query.list();
			}
		});
		
		if (0 < list.size()) {
			if (0 < Long.valueOf(list.get(0).toString())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 查询记录是否存在, 非命名
	 * @param hql HQL语句
	 * @param params 参数列表
	 * @return 是否存在该对象
	 */
	public boolean isExist(final String hql, final Object... params) {
		return 0 < this.sizeOf(hql, params);
	}
	
	/**
	 * 查询记录是否存在, 命名查询
	 * @param hql 命名HQL语句
	 * @param params 参数列表
	 * @return 是否存在该对象
	 */
	public boolean isExistNamed(final String hql, final Object... params) {
		return 0 < this.sizeOfNamed(hql, params);
	}
	
	/**
	 * 查询记录数, 非命名查询
	 * @param hql HQL语句
	 * @param params 参数列表
	 * @return 记录数
	 */
	public long sizeOf(final String hql, final Object... params) {
		List<?> list = this.getHibernateTemplate().execute(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				CommonImpl.this.bindParameter(query, params);
				return query.list();
			}
		});
		if (0 < list.size()) {
			return Long.valueOf(list.get(0).toString());
		}
		return 0;
	}
	
	/**
	 * 查询记录数, 命名查询
	 * @param hql 命名HQL语句
	 * @param params 参数列表
	 * @return 记录数
	 */
	public long sizeOfNamed(final String hql, final Object... params) {
		List<?> list = this.getHibernateTemplate().execute(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				CommonImpl.this.bindParameter(query, params);
				return query.list();
			}
		});
		if (0 < list.size()) {
			return Long.valueOf(list.get(0).toString());
		}
		return 0;
	}
	
	/**
	 * 执行HQL语句, 非命名, 非查询
	 * @param hql  hql语句
	 * @param params 参数列表
	 * @return
	 */
	public int execute(final String hql, final Object... params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				CommonImpl.this.bindParameter(query, params);
				return query.executeUpdate();
			}
		});
	}
	
	/**
	 * 执行HQL语句, 非查询
	 * @param hql  hql语句
	 * @param params 参数列表
	 * @return
	 */
	public int executeNamed(final String hql, final Object... params) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) {
				Query query = session.getNamedQuery(hql);
				CommonImpl.this.bindParameter(query, params);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 查询结果
	 * @param hql HQL语句
	 * @param params 参数列表
	 * @return List<?>
	 */
	public List<?> query(final String hql, final Object... params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				CommonImpl.this.bindParameter(query, params);
				return query.list();
			}
		});
	}

	/**
	 * 查询结果, 分页
	 * @param hql HQL语句
	 * @param currPage 当前页
	 * @param pageSize 页大小
	 * @param params 参数列表
	 * @return List<?>
	 */
	public List<?> queryPage(final String hql, final int currPage, final int pageSize,
			final Object... params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql)
						.setFirstResult((currPage - 1) * pageSize)
						.setMaxResults(pageSize);
				CommonImpl.this.bindParameter(query, params);
				return query.list();
			}
		});
	}

	/**
	 * 采用命名查询结果
	 * @param hql 命名HQL语句
	 * @param params 参数列表
	 * @return
	 */
	public List<?> queryNamed(final String hql, final Object... params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(hql);
				CommonImpl.this.bindParameter(query, params);
				return query.list();
			}
		});
	}

	/**
	 * 采用命名查询结果, 分页, 指定结果集大小
	 * @param hql 命名HQL语句
	 * @param currPage 当前页
	 * @param pageSize 页大小
	 * @param params 参数列表
	 * @return
	 */
	public List<?> queryNamedPage(final String hql, final int currPage,
			final int pageSize, final Object... params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(hql)
						.setFirstResult((currPage - 1) * pageSize)
						.setMaxResults(pageSize);
				CommonImpl.this.bindParameter(query, params);
				return query.list();
			}
		});
	}

	/**
	 * 查询结果, 指定结果集大小
	 * @param maxResult 指定结果集大小
	 * @param hql HQL语句
	 * @param params 参数列表
	 * @return List<?>
	 */
	public List<?> query(final int maxResult, final String hql,
			final Object... params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql).setMaxResults(maxResult);
				CommonImpl.this.bindParameter(query, params);
				return query.list();
			}
		});
	}

	/**
	 * 采用命名查询结果, 指定结果集大小
	 * @param maxResult 指定结果集大小
	 * @param hql 命名HQL语句
	 * @param params 参数列表
	 * @return List<?>
	 */
	public List<?> queryNamed(final int maxResult, final String hql,
			final Object... params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(hql).setMaxResults(
						maxResult);
				CommonImpl.this.bindParameter(query, params);
				return query.list();
			}
		});
	}

	/**
	 * 分页查询
	 * @param offset 从多少条记录开始查询
	 * @param pageSize 页大小
	 * @param hql 用于查询HQL语句
	 * @param params 参数列表
	 * @return List<?>
	 */
	public List<?> query(final int offset, final int pageSize, final String hql,
			final Object... params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql).setFirstResult(offset)
						.setMaxResults(pageSize);
				CommonImpl.this.bindParameter(query, params);
				return query.list();
			}
		});
	}

	/**
	 * 采用命名分页查询
	 * @param offset 偏移量
	 * @param pageSize 页大小
	 * @param hql 命名查询名称
	 * @param params 参数列表
	 * @return
	 */
	public List<?> queryNamed(final int offset, final int pageSize,
			final String hql, final Object... params) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<?>>() {
			public List<?> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(hql).setFirstResult(offset)
						.setMaxResults(pageSize);
				CommonImpl.this.bindParameter(query, params);
				return query.list();
			}
		});
	}

	/**
	 * 绑定参数
	 * @param query
	 * @param params
	 */
	public void bindParameter(Query query, Object... params) {
		if (params == null)
			return;
		int i = 0;
		for (Object object : params) {
			if (object instanceof Map) {
				Map<?, ?> map = (Map<?, ?>) object;
				for (Iterator<?> iter = map.entrySet().iterator(); iter.hasNext();) {
					Map.Entry<?, ?> entry = (Entry<?, ?>) iter.next();
				    String key = (String) entry.getKey();
				    if (entry.getValue() instanceof List) {
				    	query.setParameterList(key, (List<?>)entry.getValue());
					} else if (entry.getValue().getClass().isArray()) {
						query.setParameterList(key, (List<?>)entry.getValue());
					} else {
						query.setParameter(key, entry.getValue());
					};
				}
			} else if (object instanceof List) {
				throw new IllegalArgumentException("List 形式的参数请以Map集合传递!");
			} else if (object.getClass().isArray()) {
				throw new IllegalArgumentException("Array 形式的参数请以Map集合传递!");
			}else {
				query.setParameter(i++, object);
			}
		}
	}
	
	/**
	 * 计算总页数
	 * @param totalRecords 
	 * @param pagesize 
	 * 
	 * @return  总页数
	 */
	public   int   getTotalPages(int totalRecords,int pagesize) 
	{
		//总页数totalPages=totalRecords/pageNum(每页纪录数); 
		//int totalPages=totalRecords/pageNum;
		int allpages=0;
		if (totalRecords<pagesize) {
			allpages=1;
		}else {
			if (totalRecords%2==0) {
				 allpages=totalRecords/pagesize;
				
			}else {
				 allpages=totalRecords/pagesize+1;
				
			}
		}
		
		return allpages;
	} 
	
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory)    {  
      super.setSessionFactory(sessionFactory);  
    } 
}