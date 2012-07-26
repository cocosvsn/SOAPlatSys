package com.soaplat.cmsmgr.manageriface;

import java.util.List;
import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface ICmsSiteManager extends IBaseManager {
	/**
	 * 添加品牌
	 * @param cmsSites
	 */
	public void saveCmsSite(CmsSite... cmsSites);
	
	/**
	 * 删除品牌
	 * @param ids
	 */
	public void deleteCmsSite(String... ids);
	
	/**
	 * 删除品牌
	 * @param ids
	 */
	public void deleteCmsSite(CmsSite... cmsSites);
	
	/**
	 * 修改品牌
	 * @param cmsSites
	 */
	public void updateCmsSite(CmsSite... cmsSites);
	
	/**
	 * 查询所有有效品牌信息
	 * @return 返回品牌信息集合 List<CmsSite>
	 */
	public List<CmsSite> queryAllCmsSites();

	/**
	 * 根据品牌Code取得品牌名称
	 * @param siteCode 品牌Code
	 * @return 品牌名称
	 */
	public String getSiteNameBySiteCode(String siteCode);
	
	/**
	 * 是否存在指定品牌属性(品牌Code, 节目预告文件名)
	 * @param cmsSite
	 * @return
	 */
	public boolean isExistsSite(CmsSite cmsSite);
}
