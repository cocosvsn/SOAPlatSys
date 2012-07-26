/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * cn.sh.sbl.cms.service.ICmsSiteService.java
 * Create By: bunco
 * Create Date: 2012-7-11 上午10:50:05
 */
package cn.sh.sbl.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soaplat.cmsmgr.bean.CmsSite;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date 2012-7-11 上午10:50:05
 * @description 品牌业务接口
 */
@Service
public interface ICmsSiteService {
	
	
	/**
	 * 查询所有品牌
	 * @return List<CmsSite>
	 */
	public List<CmsSite> getAllSites();
}
