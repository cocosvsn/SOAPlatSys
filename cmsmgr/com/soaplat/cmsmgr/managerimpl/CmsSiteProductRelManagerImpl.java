package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.CmsSiteProductRel;
import com.soaplat.cmsmgr.manageriface.ICmsSiteProductRelManager;
import com.soaplat.sysmgr.common.IBaseDAO;

public class CmsSiteProductRelManagerImpl implements ICmsSiteProductRelManager {
	private IBaseDAO baseDAO;
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	/**
	 * 添加关系
	 * @param siteId
	 * @param productIds
	 * @param inputManId
	 */
	@SuppressWarnings("unchecked")
	public synchronized void save(String siteId, List<String> productIds, String inputManId) {
		List<CmsSiteProductRel> rels = this.baseDAO.findByProperty(
				"CmsSiteProductRel", "cmsSiteId", siteId);
		this.delete(rels);
		
		String tempCurrMaxPk = this.baseDAO.getMaxPropertyValue("CmsSiteProductRel", "id");
		Long currMaxPk = "".equals(tempCurrMaxPk) ? 0 : Long.valueOf(tempCurrMaxPk);
		Date currDate = new Date();
		for (String productId : productIds) {
			CmsSiteProductRel cmsSiteProductRel = new CmsSiteProductRel(
					String.format("%012d", ++ currMaxPk),
					siteId,
					productId,
					inputManId,
					currDate,
					null
			);
			this.baseDAO.save(cmsSiteProductRel);
		}
	}
	
	/**
	 * 根据主键ID删除
	 * @param ids
	 */
	public void deleteById(String... ids) {
		for (String id : ids) {
			this.baseDAO.deleteById(CmsSiteProductRel.class, id);
		}
	}
	
	/**
	 * 根据主键ID删除
	 * @param ids
	 */
	public void deleteById(String siteId, List<String> ids) {
		String sql = "delete CMS.CmsSiteProductRel rel where rel.site_id = :siteId and rel.product_id = :productId";
		for (String id : ids) {
			Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("siteId", siteId);
			params.put("productId", id);
			this.baseDAO.executeSQL(sql, params);
		}
	}
	
	/**
	 * 删除
	 * @param rels
	 */
	public void delete(List<CmsSiteProductRel> rels) {
		for (CmsSiteProductRel cmsSiteProductRel : rels) {
			this.baseDAO.delete(cmsSiteProductRel);
		}
	}
	
	/**
	 * 删除
	 * @param cmsSiteProductRels
	 */
	public void delete(CmsSiteProductRel... cmsSiteProductRels) {
		for (CmsSiteProductRel cmsSiteProductRel : cmsSiteProductRels) {
			this.baseDAO.delete(cmsSiteProductRel);
		}
	}

	/**
	 * 修改
	 * @param cmsSiteProductRels
	 */
	public void update(CmsSiteProductRel... cmsSiteProductRels) {
		for (CmsSiteProductRel cmsSiteProductRel : cmsSiteProductRels) {
			this.baseDAO.update(cmsSiteProductRel);
		}
	}

	/**
	 * 根据产品ID列表查询这些产品所属的品牌Code集合
	 * @param keyIds 产品列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getSiteCodeByKeyIds(List<String> keyIds) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("keyIds", keyIds);
		List<?> list = this.baseDAO.queryNamed("query.CmsSite.siteCode.by.keyIds", params);
		return (List<String>) list;
	}
	
	
}
