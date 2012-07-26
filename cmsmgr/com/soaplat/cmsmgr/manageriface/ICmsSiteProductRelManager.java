package com.soaplat.cmsmgr.manageriface;

import java.util.List;

public interface ICmsSiteProductRelManager {
	/**
	 * 添加关系
	 * @param siteId
	 * @param productIds
	 * @param inputManId
	 */
	public void save(String siteId, List<String> productIds, String inputManId);
	
	/**
	 * 根据主键ID删除
	 * @param ids
	 */
	public void deleteById(String siteId, List<String> ids);
	
	/**
	 * 根据产品ID列表查询这些产品所属的品牌Code集合
	 * @param keyIds 产品列表
	 * @return
	 */
	public List<String> getSiteCodeByKeyIds(List<String> keyIds);
}
