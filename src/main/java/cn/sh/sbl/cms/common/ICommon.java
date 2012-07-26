/**
 * Copyright © 2012, Bunco All Rights Reserved.
 * Project: HotelVodSys
 * cn.sh.sbl.hotelvod.common.ICommon<POJO>.java
 * Create By: Bunco
 * Create Date: 2012-5-15 上午10:13:05
 */
package cn.sh.sbl.cms.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author Bunco
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0
 * @date 2012-5-15 上午10:13:05
 * @description 持久化通用类, 定义实体对象的添, 删, 改, 查等通用操作
 */
@Repository
public interface ICommon<POJO> {
	
	/**
	 * 保存实体对象
	 * @param pojo
	 */
	public Serializable save(POJO pojo);
	
	/**
	 * 批量保存实体对象
	 * @param pojos
	 */
	public void save(POJO... pojos);

	/**
	 * 删除实体对象
	 * @param clazz
	 * @param id
	 */
	public void delete(Class<POJO> clazz, Serializable... ids);

	/**
	 * 修改实体对象, 未进行持久化判断....使用时应先加载再修改
	 * @param pojo
	 */
	public void update(POJO... pojos);

	/**
	 * 根据对象ID加载对象
	 * @param clazz
	 * @param id
	 * @return POJO
	 */
	public POJO load(Class<POJO> clazz, Serializable id);

	/**
	 * 根据对象ID加载对象, 和Load()功能相似, 作用范围不同
	 * @param clazz
	 * @param id
	 * @return POJO
	 */
	public POJO get(Class<POJO> clazz, Serializable id);
	
	/**
	 * 根据指定属性名和值查询对象是否存在
	 * @param entityName 查询是否存在的对象
	 * @param propertyName 对象属性名
	 * @param propertyValue 对象属性值
	 * @return 是否存在该对象
	 */
	public boolean isExist(String entityName, String propertyName, Object propertyValue);
	
	/**
	 * 查询记录是否存在, 非命名
	 * @param hql HQL语句
	 * @param params 参数列表
	 * @return 是否存在该对象
	 */
	public boolean isExist(final String hql, final Object... params);
	
	/**
	 * 查询记录是否存在, 命名查询
	 * @param hql 命名HQL语句
	 * @param params 参数列表
	 * @return 是否存在该对象
	 */
	public boolean isExistNamed(final String hql, final Object... params);
	
	/**
	 * 查询记录数, 非命名查询
	 * @param hql HQL语句
	 * @param params 参数列表
	 * @return 记录数
	 */
	public long sizeOf(final String hql, final Object... params);
	
	/**
	 * 查询记录数, 命名查询
	 * @param hql 命名HQL语句
	 * @param params 参数列表
	 * @return 记录数
	 */
	public long sizeOfNamed(final String hql, final Object... params);
	
	/**
	 * 执行HQL语句, 非命名, 非查询
	 * @param hql  hql语句
	 * @param params 参数列表
	 * @return
	 */
	public int execute(final String hql, final Object... params);
	
	/**
	 * 执行HQL语句, 非查询
	 * @param hql  hql语句
	 * @param params 参数列表
	 * @return
	 */
	public int executeNamed(final String hql, final Object... params);

	/**
	 * 查询结果
	 * @param hql HQL语句
	 * @param params 参数列表
	 * @return List<?>
	 */
	public List<?> query(final String hql, final Object... params);

	/**
	 * 查询结果, 分页
	 * @param hql HQL语句
	 * @param currPage 当前页
	 * @param pageSize 页大小
	 * @param params 参数列表
	 * @return List<?>
	 */
	public List<?> queryPage(final String hql, final int currPage, final int pageSize,
			final Object... params);

	/**
	 * 采用命名查询结果
	 * @param hql 命名HQL语句
	 * @param params 参数列表
	 * @return
	 */
	public List<?> queryNamed(final String hql, final Object... params);

	/**
	 * 采用命名查询结果, 分页, 指定结果集大小
	 * @param hql 命名HQL语句
	 * @param currPage 当前页
	 * @param pageSize 页大小
	 * @param params 参数列表
	 * @return
	 */
	public List<?> queryNamedPage(final String hql, final int currPage,
			final int pageSize, final Object... params);

	/**
	 * 查询结果, 指定结果集大小
	 * @param maxResult 指定结果集大小
	 * @param hql HQL语句
	 * @param params 参数列表
	 * @return List<?>
	 */
	public List<?> query(final int maxResult, final String hql,
			final Object... params);

	/**
	 * 采用命名查询结果, 指定结果集大小
	 * @param maxResult 指定结果集大小
	 * @param hql 命名HQL语句
	 * @param params 参数列表
	 * @return List<?>
	 */
	public List<?> queryNamed(final int maxResult, final String hql,
			final Object... params);

	/**
	 * 分页查询
	 * @param offset 从多少条记录开始查询
	 * @param pageSize 页大小
	 * @param hql 用于查询HQL语句
	 * @param params 参数列表
	 * @return List<?>
	 */
	public List<?> query(final int offset, final int pageSize, final String hql,
			final Object... params);

	/**
	 * 采用命名分页查询
	 * @param offset 偏移量
	 * @param pageSize 页大小
	 * @param hql 命名查询名称
	 * @param params 参数列表
	 * @return
	 */
	public List<?> queryNamed(final int offset, final int pageSize,
			final String hql, final Object... params);
	
	/**
	 * 计算总页数
	 * @param totalRecords 
	 * @param pagesize 
	 * 
	 * @return  总页数
	 */
	public   int   getTotalPages(int totalRecords,int pagesize) ;
}