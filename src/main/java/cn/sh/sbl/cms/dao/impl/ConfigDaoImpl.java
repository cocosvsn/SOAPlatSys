/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * cn.sh.sbl.cms.dao.impl.ConfigDaoImpl.java
 * Create By: bunco
 * Create Date: Jul 26, 2012 4:44:35 PM
 */
package cn.sh.sbl.cms.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.sh.sbl.cms.beans.Config;
import cn.sh.sbl.cms.common.ICommon;
import cn.sh.sbl.cms.dao.IConfigDao;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date Jul 26, 2012 4:44:35 PM
 * @description 配置Dao实现
 */
@Repository
public class ConfigDaoImpl implements IConfigDao {
	@Autowired
	private Logger logger;
	@Autowired
	private ICommon<Config> common;

	/**
	 * 删除 Config
	 * @param id Config编号
	 */
	public void delete(Serializable id) {
		this.logger.debug("deleting Config instance");
		try {
			this.common.delete(Config.class, id);
			this.logger.debug("delete successful");
		} catch (RuntimeException re) {
			this.logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * 查询所有 Config
	 * @return @see Config
	 */
	public List<Config> findAll() {
		this.logger.debug("finding all Config instance");
		try {
			String hql = "from Config as model";
			@SuppressWarnings("unchecked")
			List<Config> list = (List<Config>) this.common.query(hql);
			this.logger.debug("find Config size: {{}}", list.size());
			return list;
		} catch (RuntimeException re) {
			this.logger.error("find all instance failed", re);
			throw re;
		}
	}

	/**
	 * 获取 Config
	 * @param pkid 主键编号
	 * @return @see Config
	 */
	public Config getById(String pkid) {
		this.logger.debug("getting Config instance by id: {{}}", pkid);
		try {
			Config pojo = this.common.get(Config.class, pkid);
			this.logger.debug("get successful: {{}}", pojo);
			return pojo;
		} catch (RuntimeException re) {
			this.logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * 获取配置值
	 * @param pkid 配置名称
	 * @return Config.value
	 */
	public String getValueById(String pkid) {
		this.logger.debug("getting Config.value instance by id: {{}}", pkid);
		try {
			Config pojo = this.common.get(Config.class, pkid);
			this.logger.debug("get successful: {{}}", pojo);
			return pojo.getValue();
		} catch (RuntimeException re) {
			this.logger.error("get failed", re);
			throw re;
		}
	}

	/**
	 * 保存 Config 对象
	 * @param pojo Config
	 * @return 持久化对象的主键编号
	 */
	public Serializable save(Config pojo) {
		this.logger.debug("saving Config instance");
		try {
			Serializable id = this.common.save(pojo);
			this.logger.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			this.logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * 修改 Config 对象
	 * @param pojo Config
	 */
	public void update(Config pojo) {
		this.logger.debug("updating Config instance");
		try {
			this.common.update(pojo);
			this.logger.debug("update successful");
		} catch (RuntimeException re) {
			this.logger.error("update failed", re);
			throw re;
		}
	}
}
