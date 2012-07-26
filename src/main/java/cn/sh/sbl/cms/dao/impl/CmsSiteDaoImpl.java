/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * cn.sh.sbl.cms.dao.impl.CmsSiteDaoImpl.java
 * Create By: bunco
 * Create Date: 2012-7-11 下午5:12:19
 */
package cn.sh.sbl.cms.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.soaplat.cmsmgr.bean.CmsSite;

import cn.sh.sbl.cms.common.ICommon;
import cn.sh.sbl.cms.dao.ICmsSiteDao;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date 2012-7-11 下午5:12:19
 * @description 品牌Dao实现
 */
@Repository
public class CmsSiteDaoImpl implements ICmsSiteDao {
	@Autowired
	private Logger logger;
	@Autowired
	private ICommon<CmsSite> common;

	/**
	 * 删除 CmsSite
	 * @param id CmsSite编号
	 */
	public void delete(Serializable id) {
		this.logger.debug("deleting CmsSite instance");
		try {
			this.common.delete(CmsSite.class, id);
			this.logger.debug("delete successful");
		} catch (RuntimeException re) {
			this.logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * 查询所有 CmsSite
	 * @return @see CmsSite
	 */
	public List<CmsSite> findAll() {
		this.logger.debug("finding all CmsSite instance");
		try {
			String hql = "from CmsSite as model";
			@SuppressWarnings("unchecked")
			List<CmsSite> list = (List<CmsSite>) this.common.query(hql);
			this.logger.debug("find CmsSite size: {{}}", list.size());
			return list;
		} catch (RuntimeException re) {
			this.logger.error("find all instance failed", re);
			throw re;
		}
	}

	/**
	 * 获取 CmsSite
	 * @param pkid 主键编号
	 * @return @see CmsSite
	 */
	public CmsSite getById(String pkid) {
		this.logger.debug("getting CmsSite instance by id: {{}}", pkid);
		try {
			CmsSite pojo = this.common.get(CmsSite.class, pkid);
			this.logger.debug("get successful: {{}}", pojo);
			return pojo;
		} catch (RuntimeException re) {
			this.logger.error("get failed", re);
			throw re;
		}
	}

	/**
	 * 保存 CmsSite 对象
	 * @param pojo CmsSite
	 * @return 持久化对象的主键编号
	 */
	public Serializable save(CmsSite pojo) {
		this.logger.debug("saving CmsSite instance");
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
	 * 修改 CmsSite 对象
	 * @param pojo CmsSite
	 */
	public void update(CmsSite pojo) {
		this.logger.debug("updating CmsSite instance");
		try {
			this.common.update(pojo);
			this.logger.debug("update successful");
		} catch (RuntimeException re) {
			this.logger.error("update failed", re);
			throw re;
		}
	}
}
