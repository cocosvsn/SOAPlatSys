package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IPackStylePortalColumnManager extends IBaseManager {

	// 20100522 17:30
	// 查询关系，根据样式id和栏目code
	public List getPackStylePortalColumnsByStyleidDefcatcode(
			Long styleid,
			String defcatcode
			);
	
	/**
	 * 根据栏目Code查询该栏目所能应用的样式ID列表
	 * @param defcatcode 栏目Code
	 * @return 返回样式ID集合
	 */
	public List<Long> getPackStyleIdByDefcatcode(String defcatcode);
	
	/**
	 * 根据栏目Code和栏目样式编号删除
	 * @param defcatcode
	 * @param styleId
	 */
	public void deleteByStyleIdDefcatcode(String defcatcode, Long styleId);
}
