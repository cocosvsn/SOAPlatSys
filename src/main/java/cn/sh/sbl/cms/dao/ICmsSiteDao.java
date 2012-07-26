/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * cn.sh.sbl.cms.dao.ICmsSiteDao.java
 * Create By: bunco
 * Create Date: 2012-7-11 下午5:11:27
 */
package cn.sh.sbl.cms.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.soaplat.cmsmgr.bean.CmsSite;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date 2012-7-11 下午5:11:27
 * @description 品牌Dao接口
 */
@Repository
public interface ICmsSiteDao {
	/**
	 * 删除 品牌
	 * @param id 品牌编号
	 */
	public void delete(Serializable id);
	
	/**
	 * 查询所有 品牌
	 * @return @see CmsSite
	 */
	public List<CmsSite> findAll();
	
	/**
	 * 获取 品牌
	 * @param pkid 品牌编号
	 * @return @see CmsSite
	 */
	public CmsSite getById(String pkid);
	
	/**
	 * 保存品牌对象
	 * @param cmsSite 品牌
	 * @return 持久化对象的主键编号
	 */
	public Serializable save(CmsSite cmsSite);
	
	/**
	 * 修改品牌对象
	 * @param cmsSite 品牌
	 */
	public void update(CmsSite cmsSite);
}
