/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * cn.sh.sbl.cms.service.impl.ConfigServiceImpl.java
 * Create By: bunco
 * Create Date: Jul 26, 2012 4:48:57 PM
 */
package cn.sh.sbl.cms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.sh.sbl.cms.beans.Config;
import cn.sh.sbl.cms.dao.IConfigDao;
import cn.sh.sbl.cms.service.IConfigService;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date Jul 26, 2012 4:48:57 PM
 * @description 配置Service实现
 */
@Service
public class ConfigServiceImpl implements IConfigService {
	@Autowired
	private Logger logger;
	@Autowired
	private IConfigDao configDao;
	
	/**
	 * 添加配置信息
	 * @param name 名称
	 * @param value 值
	 * @param comment 说明
	 * @return message
	 */
	public String addConfig(String name, String value, String comment) {
		try {
			Assert.notNull(name, "添加配置失败, name 为空!");
			Assert.notNull(value, "添加配置失败, value 为空!");
		} catch (IllegalArgumentException e) {
			this.logger.warn("", e);
			return e.getMessage();
		}
		
		Config newConfig = new Config(name, value, comment, true);
		this.configDao.save(newConfig);
		return "添加配置成功!";
	}
	
	/**
	 * 更新配置信息
	 * @param name 名称
	 * @param value 值
	 * @param comment 说明
	 * @return message
	 */
	public String updateConfig(String name, String value, String comment) {
		try {
			Assert.notNull(name, "更新配置失败, name 为空!");
			Assert.notNull(value, "更新配置失败, value 为空!");
		} catch (IllegalArgumentException e) {
			this.logger.warn("", e);
			return e.getMessage();
		}
		Config config = this.configDao.getById(name);
		if (null == config) {
			config = new Config();
			config.setName(name);
			config.setValue(value);
			config.setComment(comment);
			config.setValid(true);
			this.configDao.save(config);
		} else {
			config.setValue(value);
			config.setComment(comment);
			this.configDao.update(config);
		}
		return "更新配置成功!";
	}
	
	/**
	 * 删除配置信息 不开放
	 * @param name 名称
	 * @return message
	 */
	public String deleteConfig(String name) {
		try {
			Assert.notNull(name, "删除配置失败, name 为空!");
		} catch (IllegalArgumentException e) {
			this.logger.warn("", e);
			return e.getMessage();
		}
		Config config = this.configDao.getById(name);
		config.setValid(false);
		return "删除配置成功!";
	}
	
	/**
	 * 查询所有配置信息
	 * @return List<Config>
	 */
	public List<Config> getAllConfigs() {
		return this.configDao.findAll();
	}
}
