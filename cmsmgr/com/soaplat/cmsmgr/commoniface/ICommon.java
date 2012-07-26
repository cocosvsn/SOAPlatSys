/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.commoniface;

import java.io.Serializable;
import java.util.List;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-1 上午10:55:34
 */
public interface ICommon<POJO> {
	/**
	 * 持久化实体对象
	 * @param entity 待持久化的对象
	 */
	public void persist(POJO entity);

	/**
	 * 持久化实体对象 主键ID由数据库自动生成的情况下, 可将数据库生成的ID返回
	 * @param entity 待持久化的对象
	 * @return 主键ID
	 */
	public Serializable save(POJO entity);

	/**
	 * 删除已持久化的对象
	 * @param clazz 对象类型
	 * @param id 对象主键ID
	 */
	public void delete(Class<POJO> clazz, Serializable id);
	
	/**
	 * 修改持久化对象
	 * @param entity 待持久化的对象
	 */
	public void update(POJO entity);
	
	/**
	 * get持久化对象
	 * @param clazz 对象类型
	 * @param id 对象主键ID
	 * @return 持久化对象
	 */
	public POJO get(Class<POJO> clazz, Serializable id);
	
	/**
	 * load持久化对象
	 * @param clazz 对象类型
	 * @param id 对象主键ID
	 * @return 持久化对象
	 */
	public POJO load(Class<POJO> clazz, Serializable id);
	
	/**
	 * 非命名查询, 并可指定结果集大小
	 * @param maxResult 如需指定结果集大小, 此值设定为1~Max(int)之前, 否则此值设为0
	 * @param hql 进行查询操作的HQL语句
	 * @param params HQL语句所需绑定的?参数列表
	 * @return 查询结果集
	 */
	public List<?> query(final int maxResult, final String hql, final Object... params);
	
	/**
	 * 非命名查询, 分页
	 * @param offset 查询结果的偏移量
	 * @param maxResult 指定查询结果大小
	 * @param hql 进行查询操作的HQL语句
	 * @param params HQL语句所需绑定的?参数列表
	 * @return 查询结果集
	 */
	public List<?> query(final int offset, final int maxResult, 
			final String hql, final Object... params);
	
	/**
	 * 命名查询, 并可指定结果集大小
	 * @param maxResult 如需指定结果集大小, 此值设定为1~Max(int)之前, 否则此值设为0
	 * @param hql 命名查询名称
	 * @param params 命名查询所需绑定的?参数列表
	 * @return 查询结果集
	 */
	public List<?> queryNamed(final int maxResult, final String hql, final Object... params);
	
	/**
	 * 命名查询, 分页
	 * @param offset 查询结果的偏移量
	 * @param maxResult 指定查询结果大小
	 * @param hql 进行查询操作的HQL语句
	 * @param params HQL语句所需绑定的?参数列表
	 * @return 查询结果集
	 */
	public List<?> queryNamed(final int offset, final int maxResult, 
			final String hql, final Object... params);
}
