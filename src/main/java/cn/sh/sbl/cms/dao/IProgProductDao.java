/**
 * Copyright © 2012, bunco All Rights Reserved.
 * Project: SOAPlatSys
 * cn.sh.sbl.cms.dao.IProgProductDao.java
 * Create By: bunco
 * Create Date: Jul 23, 2012 12:28:09 PM
 */
package cn.sh.sbl.cms.dao;

import java.io.Serializable;
import java.util.List;

import com.soaplat.cmsmgr.bean.ProgProduct;

/**
 * @author bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date Jul 23, 2012 12:28:09 PM
 * @description 产品Dao接口
 */
public interface IProgProductDao {
	/**
	 * 删除 ProgProduct
	 * @param id ProgProduct编号
	 */
	public void delete(Serializable id);
	
	/**
	 * 查询所有 ProgProduct
	 * @return @see ProgProduct
	 */
	public List<ProgProduct> findAll();
	
	/**
	 * 获取 ProgProduct
	 * @param pkid 主键编号
	 * @return @see ProgProduct
	 */
	public ProgProduct getById(String pkid);
	
	/**
	 * 保存 ProgProduct 对象
	 * @param pojo ProgProduct
	 * @return 持久化对象的主键编号
	 */
	public Serializable save(ProgProduct pojo);
	
	/**
	 * 修改 ProgProduct 对象
	 * @param pojo ProgProduct
	 */
	public void update(ProgProduct pojo);
}
