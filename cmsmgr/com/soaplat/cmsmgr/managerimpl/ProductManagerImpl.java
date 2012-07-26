package com.soaplat.cmsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgProduct;
import com.soaplat.cmsmgr.manageriface.IProductManager;
import com.soaplat.sysmgr.common.IBaseDAO;

public class ProductManagerImpl implements IProductManager {
	private IBaseDAO baseDAO;
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public ProgProduct getById(String id) {
		return (ProgProduct) this.baseDAO.getById(ProgProduct.class, id);
	}
	
	/**
	 * 添加产品
	 * @param products
	 */
	public synchronized void saveProduct(ProgProduct... products) {
		String tempCurrMaxPk = this.baseDAO.getMaxPropertyValue("ProgProduct", "id");
		Long currMaxPk = "".equals(tempCurrMaxPk) ? 0 : Long.valueOf(tempCurrMaxPk);
		for (ProgProduct product : products) {
			product.setId(String.format("%012d", ++currMaxPk));
			this.baseDAO.save(product);
		}
	}
	
	/**
	 * 删除产品
	 * @param id
	 */
	public void delProduct(String... ids) {
		for (String id : ids) {
			this.baseDAO.deleteById(ProgProduct.class, id);
		}
	}
	
	/**
	 * 修改产品信息
	 * @param products
	 */
	public void updateProduct(ProgProduct... products) {
		for (ProgProduct product : products) {
			this.baseDAO.update(product);
		}
	}
	
	/**
	 * 查询所有产品信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProgProduct> queryAllProducts() {
		return this.baseDAO.findAll("ProgProduct", "inputDate");
	}
	
	/**
	 * 根据品牌ID查询该品牌下的产品信息
	 * @param siteId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProgProduct> queryProductsBySiteId(String siteId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteId);
		return this.baseDAO.queryByNamedQuery("query.ProgProduct.by.siteId", map);
	}
	
	/**
	 * 根据栏目编号, 查询该栏目绑定的产品信息
	 * @param columnId 栏目编号
	 * @return List<ProgProduct>
	 */
	@SuppressWarnings("unchecked")
	public List<ProgProduct> queryProducts(String columnId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("columnId", columnId);
		return this.baseDAO.queryByNamedQuery("query.ProgProduct.by.columnId", map);
	}
	
	/**
	 * 根据产品ID查询所属的品牌
	 * @param productId 产品编号
	 * @return 返回所属品牌集合 List<CmsSite>
	 */
	@SuppressWarnings("unchecked")
	public List<CmsSite> querySiteByProdcutId(String productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		return (List<CmsSite>) this.baseDAO.queryNamed("query.CmsSite.by.productId", map);
	}
	
	/**
	 * 根据产品ID查询所属的栏目
	 * @param productId 产品编号
	 * @return 返回所属栏目集合 List<PortalColumn>
	 */
	@SuppressWarnings("unchecked")
	public List<PortalColumn> queryColumnByProdcutId(String productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		return (List<PortalColumn>) this.baseDAO.queryNamed("query.PortalColumn.by.productId", map);
	}
	
	/**
	 * 根据产品编号集合, 查询所有的keyId
	 * @param ids 产品编号(主键)
	 * @return 返回KeyId集合 List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryByids(List<String> ids) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("ids", ids);
		List<?> list = this.baseDAO.queryNamed("query.ProgProduct.keyId.by.id", params);
		return (List<String>) list;
	}
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}
