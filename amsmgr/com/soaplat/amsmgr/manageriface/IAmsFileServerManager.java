package com.soaplat.amsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

/**
 * Title 		:the Interface IAmsFileServerManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IAmsFileServerManager extends IBaseManager {

	/**
	 * 根据编单日期区间和存储体等级Code查询出该区间可以删除的节目包及文件信息集合
	 * @param startScheduleDate 编单日期开始
	 * @param endScheduleDate 编单日期结束
	 * @param storageClassCode 存储体等级Code
	 * @param safeScheduleDate 安全编单日期
	 * @param styleId 节目包样式编号
	 * @param packageName 节目包名称模糊查询
	 * @param siteCode 品牌Code
	 * @return List<Object[]>
	 */
	public List<Object[]> queryFilesByScheduleDateAndStorage(String startScheduleDate, 
			String endScheduleDate, String storageClassCode, String safeScheduleDate, 
			Long styleId, String packageName, String siteCode);
	
	/**
	 * 查询所有存储
	 * @return 返回存储体信息集合 List<Object[]>
	 * objects[0]: AmsStorageClass.stclassname
	 * objects[1]: AmsStorageClass.stclasscode
	 */
	public List<Object[]> getStorages();
	
	/**
	 * 根据编单细表ID集合, 存储等级Code, 安全编单日期ID, 查询可以删除的节目包文件信息
	 * @param progListIds 编单细表ID集合
	 * @param storageClassCode 存储体等级Code
	 * @param safeScheduleDate 安全编单日期
	 * @return List<Object[]>
	 */
	public List<Object[]> queryCanDelPackageFiles(List<String> progListIds,
			String storageClassCode, String safeScheduleDate);
	
	/**
	 * 查询片花是否被其它节目包包含量
	 * @param progPackageId 节目包ID
	 * @return
	 */
	public boolean isContainerOfOther(String progPackageId);
}
