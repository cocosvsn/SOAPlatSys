/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * cn.sh.sbl.cms.service.IConfigService.java
 * Create By: bunco
 * Create Date: Jul 26, 2012 4:47:43 PM
 */
package cn.sh.sbl.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.sh.sbl.cms.beans.Config;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date Jul 26, 2012 4:47:43 PM
 * @description 配置Service
 */
@Service
public interface IConfigService {

	/**
	 * 添加配置信息
	 * @param name 名称
	 * @param value 值
	 * @param comment 说明
	 * @return message
	 */
	public String addConfig(String name, String value, String comment);
	
	/**
	 * 修改配置信息
	 * @param name 名称
	 * @param value 值
	 * @param comment 说明
	 * @return message
	 */
	public String updateConfig(String name, String value, String comment);
	
	/**
	 * 查询所有配置信息
	 * @return List<Config>
	 */
	public List<Config> getAllConfigs();
}
