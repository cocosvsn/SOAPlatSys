/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.commonimpl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.soaplat.cmsmgr.commoniface.ICommon;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-1 上午11:01:15
 */
public class CommonImpl<POJO> extends HibernateDaoSupport implements ICommon<POJO> {

	/**
	 * 持久化实体对象
	 * @param entity 待持久化的对象
	 */
	public void persist(POJO entity) {
		this.getHibernateTemplate().persist(entity);
	}

	/**
	 * 持久化实体对象 主键ID由数据库自动生成的情况下, 可将数据库生成的ID返回
	 * @param entity 待持久化的对象
	 * @return 主键ID
	 */
	public Serializable save(POJO entity) {
		return this.getHibernateTemplate().save(entity);
	}

	/**
	 * 删除已持久化的对象
	 * @param clazz 对象类型
	 * @param id 对象主键ID
	 */
	public void delete(Class<POJO> clazz, Serializable id) {
		this.getHibernateTemplate().delete(
				this.getHibernateTemplate().load(clazz, id));
	}
	
	/**
	 * 修改持久化对象
	 * @param entity 待持久化的对象
	 */
	public void update(POJO entity) {
		this.getHibernateTemplate().update(entity);
	}
	
	/**
	 * get持久化对象
	 * @param clazz 对象类型
	 * @param id 对象主键ID
	 * @return 持久化对象
	 */
	public POJO get(Class<POJO> clazz, Serializable id) {
		return (POJO) this.getHibernateTemplate().get(clazz, id);
	}
	
	/**
	 * load持久化对象
	 * @param clazz 对象类型
	 * @param id 对象主键ID
	 * @return 持久化对象
	 */
	public POJO load(Class<POJO> clazz, Serializable id) {
		return (POJO) this.getHibernateTemplate().load(clazz, id);
	}
	
	/**
	 * 非命名查询, 并可指定结果集大小
	 * @param maxResult 如需指定结果集大小, 此值设定为1~Max(int)之前, 否则此值设为0
	 * @param hql 进行查询操作的HQL语句
	 * @param params HQL语句所需绑定的?参数列表
	 * @return 查询结果集
	 */
	public List<?> query(final int maxResult, final String hql, final Object... params) {
		return (List<?>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				if (maxResult > 0) {
					query.setMaxResults(maxResult);
				}
				CommonImpl.this.bindParams(query, params);
				return query.list();
			}
			
		});
	}
	
	/**
	 * 非命名查询, 分页
	 * @param offset 查询结果的偏移量
	 * @param maxResult 指定查询结果大小
	 * @param hql 进行查询操作的HQL语句
	 * @param params HQL语句所需绑定的?参数列表
	 * @return 查询结果集
	 */
	public List<?> query(final int offset, final int maxResult, 
			final String hql, final Object... params) {
		return (List<?>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(offset);
				query.setMaxResults(maxResult);
				CommonImpl.this.bindParams(query, params);
				return query.list();
			}
			
		});
	}
	
	/**
	 * 命名查询, 并可指定结果集大小
	 * @param maxResult 如需指定结果集大小, 此值设定为1~Max(int)之前, 否则此值设为0
	 * @param hql 命名查询名称
	 * @param params 命名查询所需绑定的?参数列表
	 * @return 查询结果集
	 */
	public List<?> queryNamed(final int maxResult, final String hql, final Object... params) {
		return (List<?>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(hql);
				if (maxResult > 0) {
					query.setMaxResults(maxResult);
				}
				CommonImpl.this.bindParams(query, params);
				return query.list();
			}});
	}
	
	/**
	 * 命名查询, 分页
	 * @param offset 查询结果的偏移量
	 * @param maxResult 指定查询结果大小
	 * @param hql 进行查询操作的HQL语句
	 * @param params HQL语句所需绑定的?参数列表
	 * @return 查询结果集
	 */
	public List<?> queryNamed(final int offset, final int maxResult, 
			final String hql, final Object... params) {
		return (List<?>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(hql);
				query.setMaxResults(maxResult);
				query.setFirstResult(offset);
				CommonImpl.this.bindParams(query, params);
				return query.list();
			}
		});
	}
	
	/**
	 * 绑定HQL语句参数
	 * @param query Hibernate Query
	 * @param params 待绑定的参数列表
	 */
	@SuppressWarnings("unchecked")
	private void bindParams(Query query, Object... params) {
		if (params == null) return;
		int i = 0;
		if (params[0] instanceof Map) {
			Map<String, ?> map = (Map<String, ?>) params[0];
			for (String key : map.keySet()) {
				if (map.get(key) instanceof List) {
					query.setParameter(key, map.get(key));
				} else {
					query.setParameterList(key, (List) map.get(key));
				}
			}
		} else {
			for (Object object : params) {
				query.setParameter(i ++, object);
			}
		}
	}
}
