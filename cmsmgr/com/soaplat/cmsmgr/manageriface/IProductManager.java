package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgProduct;

public interface IProductManager {
	/**
	 * 根据ID查询
	 * @param id 主键ID
	 * @return
	 */
	public ProgProduct getById(String id);
	
	/**
	 * 添加产品
	 * @param products
	 */
	public void saveProduct(ProgProduct... products);
	
	/**
	 * 删除产品
	 * @param id
	 */
	public void delProduct(String... ids);
	
	/**
	 * 修改产品信息
	 * @param products
	 */
	public void updateProduct(ProgProduct... products);
	
	/**
	 * 查询所有产品信息
	 * @return
	 */
	public List<ProgProduct> queryAllProducts();
	
	/**
	 * 根据品牌ID查询该品牌下的产品信息
	 * @param siteId
	 * @return
	 */
	public List<ProgProduct> queryProductsBySiteId(String siteId);
	
	/**
	 * 根据栏目编号, 查询该栏目绑定的产品信息
	 * @param columnId 栏目编号
	 * @return List<ProgProduct>
	 */
	public List<ProgProduct> queryProducts(String columnId);
	
	/**
	 * 根据产品ID查询所属的品牌
	 * @param productId 产品编号
	 * @return 返回所属品牌集合 List<CmsSite>
	 */
	public List<CmsSite> querySiteByProdcutId(String productId);
	
	/**
	 * 根据产品ID查询所属的栏目
	 * @param productId 产品编号
	 * @return 返回所属栏目集合 List<PortalColumn>
	 */
	public List<PortalColumn> queryColumnByProdcutId(String productId);
	
	/**
	 * 根据产品编号集合, 查询所有的keyId
	 * @param ids 产品编号(主键)
	 * @return 返回KeyId集合 List<String>
	 */
	public List<String> queryByids(List<String> ids);
}
