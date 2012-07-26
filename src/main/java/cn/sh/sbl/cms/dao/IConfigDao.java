/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * cn.sh.sbl.cms.dao.IConfigDao.java
 * Create By: bunco
 * Create Date: Jul 26, 2012 4:43:56 PM
 */
package cn.sh.sbl.cms.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sh.sbl.cms.beans.Config;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date Jul 26, 2012 4:43:56 PM
 * @description 配置Dao
 */
@Repository
public interface IConfigDao {

	/**
	 * 删除 Config
	 * @param id Config编号
	 */
	public void delete(Serializable id);

	/**
	 * 查询所有 Config
	 * @return @see Config
	 */
	public List<Config> findAll();

	/**
	 * 获取 Config
	 * @param pkid 主键编号
	 * @return @see Config
	 */
	public Config getById(String pkid);
	
	/**
	 * 获取配置值
	 * @param pkid 配置名称
	 * @return Config.value
	 */
	public String getValueById(String pkid);

	/**
	 * 保存 Config 对象
	 * @param pojo Config
	 * @return 持久化对象的主键编号
	 */
	public Serializable save(Config pojo);

	/**
	 * 修改 Config 对象
	 * @param pojo Config
	 */
	public void update(Config pojo);
}
