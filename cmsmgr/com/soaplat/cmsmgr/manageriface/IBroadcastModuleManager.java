package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.dto.CmsResultDto;

//import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IBroadcastModuleManager
{
	/**
	 * 20110113 10:52 1.23 生成播发单
	 * @param date
	 * @param operatorId
	 * @param plandate
	 */
	public CmsResultDto saveGenerateBroadcastxml_123(
			String date, 			// yyyy-MM-dd
			String operatorId, 		// 操作人员id
			String plandate, 		// 计划播发日期，格式："yyyy-MM-dd HH:mm:ss"
			boolean forceToGenerate	// 强制生成播发单，如果为true，当某个品牌或节目包出现错误的情况，也继续生成播发单xml文件。
			);
	
	/**
	 * 20110114 10:52 1.23 查询编单删除详细记录
	 * @param date	Format:"2011-01-14"
	 * @return
	 */
	public CmsResultDto getProgListDeleteDetails(
			String date, String operatorId);
	
	/**
	 * 20110114 10:52 1.23 保存（创建）编单删除详细记录
	 * @param date	Format:"2011-01-14"
	 * @param progPackages	List<ProgPackage>
	 * @param operatorId
	 * @return
	 */
	public CmsResultDto saveProgListDeleteDetails(
			String date, List<ProgPackage> progPackages, String operatorId);
	
	/**
	 * 20110114 10:52 1.23 删除编单删除详细记录
	 * @param progListDeleteDetailids	List<String>
	 * @param operatorId
	 * @return
	 */
	public CmsResultDto deleteProgListDeleteDetails(
			List<String> progListDeleteDetailids, String operatorId);
}
