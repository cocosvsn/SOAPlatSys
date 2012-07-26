/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * cn.sh.sbl.cms.dao.impl.ProgProductDaoImpl.java
 * Create By: bunco
 * Create Date: Jul 23, 2012 12:28:57 PM
 */
package cn.sh.sbl.cms.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.soaplat.cmsmgr.bean.ProgProduct;

import cn.sh.sbl.cms.common.ICommon;
import cn.sh.sbl.cms.dao.IProgProductDao;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date Jul 23, 2012 12:28:57 PM
 * @description 产品Dao实现
 */
public class ProgProductDaoImpl implements IProgProductDao {
	@Autowired
	private Logger logger;
	@Autowired
	private ICommon<ProgProduct> common;
	
	/**
	 * 删除 ProgProduct
	 * @param id ProgProduct编号
	 */
	public void delete(Serializable id) {
		this.logger.debug("deleting ProgProduct instance");
		try {
			this.common.delete(ProgProduct.class, id);
			this.logger.debug("delete successful");
		} catch (RuntimeException re) {
			this.logger.error("delete failed", re);
			throw re;
		}
	}
	
	/**
	 * 查询所有 ProgProduct
	 * @return @see ProgProduct
	 */
	public List<ProgProduct> findAll() {
		this.logger.debug("finding all ProgProduct instance");
		try {
			String hql = "from ProgProduct as model";
			@SuppressWarnings("unchecked")
			List<ProgProduct> list = (List<ProgProduct>) this.common.query(hql);
			this.logger.debug("find ProgProduct size: {{}}", list.size());
			return list;
		} catch (RuntimeException re) {
			this.logger.error("find all instance failed", re);
			throw re;
		}
	}
	
	/**
	 * 获取 ProgProduct
	 * @param pkid 主键编号
	 * @return @see ProgProduct
	 */
	public ProgProduct getById(String pkid) {
		this.logger.debug("getting ProgProduct instance by id: {{}}", pkid);
		try {
			ProgProduct pojo = this.common.get(ProgProduct.class, pkid);
			this.logger.debug("get successful: {{}}", pojo);
			return pojo;
		} catch (RuntimeException re) {
			this.logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * 保存 ProgProduct 对象
	 * @param pojo ProgProduct
	 * @return 持久化对象的主键编号
	 */
	public Serializable save(ProgProduct pojo) {
		this.logger.debug("saving ProgProduct instance");
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
	 * 修改 ProgProduct 对象
	 * @param pojo ProgProduct
	 */
	public void update(ProgProduct pojo) {
		this.logger.debug("updating ProgProduct instance");
		try {
			this.common.update(pojo);
			this.logger.debug("update successful");
		} catch (RuntimeException re) {
			this.logger.error("update failed", re);
			throw re;
		}
	}
}
