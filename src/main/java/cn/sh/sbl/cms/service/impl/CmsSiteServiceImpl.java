/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * cn.sh.sbl.cms.service.impl.CmsSiteServiceImpl.java
 * Create By: bunco
 * Create Date: 2012-7-11 上午10:52:35
 */
package cn.sh.sbl.cms.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soaplat.cmsmgr.bean.CmsSite;

import cn.sh.sbl.cms.dao.ICmsSiteDao;
import cn.sh.sbl.cms.service.ICmsSiteService;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date 2012-7-11 上午10:52:35
 * @description 品牌分组业务实现
 */
@Service
public class CmsSiteServiceImpl implements ICmsSiteService {
	@Autowired
	private Logger logger;
	@Autowired
	private ICmsSiteDao cmsSiteDao;

	/**
	 * 添加 品牌
	 * @param cmsSite 品牌
	 */
	public void addSite(CmsSite cmsSite) {
		this.cmsSiteDao.save(cmsSite);
	}
}
