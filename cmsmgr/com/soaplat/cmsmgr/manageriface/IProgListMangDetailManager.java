package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.ProgListMangDetail;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgListMangDetailManager extends IBaseManager {

	// 根据scheduleDate、defcatseq查询，得到progListMangDetails
	public List getProgListMangDetailsByScheduleDateAndDefcatseq(
			String scheduledate, 
			String defcatseq
			);
	
	// 20100121 14:09
	// 根据scheduleDate、defcatseq查询，得到本地progListMangDetails
	public List getLocalProgListMangDetailsByScheduleDateAndDefcatseq(
			String scheduledate, 
			String defcatseq
			);
	
	// 20091229 13:26
	// 根据scheduleDate和Columnclassid，查询得到progListMangDetail
	public List getProgListMangDetailsByScheduleDateAndColumnclassid(
			String scheduledate, 
			String columnclassid
			);
	
	// 20100108 10:24
	// 根据栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
	public List getProgListMangDetailsByScheduledate(
			String scheduledate							// 栏目单总表的主键
			);
	
	// 这就是传说中的方法15
	// 20100121 13:46
	// 根据本地栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
	public List getLocalProgListMangDetailsByScheduledate(
			String scheduledate							// 栏目单总表的主键
			);
	
	/**
	 * 根据编单日期集合查询所有栏目编单集合
	 * @param scheduleDateStr 编单日期集合, 格式: 20100909000000
	 * @return 返回所有栏目编单集合
	 */
	public List<ProgListMangDetail> queryDetailsByScheduleDate(
			List<String> scheduleDateStr);
	
	/**
	 * 根据编单日期, 修改编单总表和分表的流程活动
	 * @param scheduleDate 编单日期ID, 格式: 20100909000000
	 * @param nextAction 将要发送到的下一个活动ID
	 */
	public int updateAction(String scheduleDate, String nextAction);
	
	/**
	 * 根据栏目Code查询该栏目是否已被初始化编单
	 * @param columnCode 栏目Code
	 * @return 
	 */
	public boolean existProgListDetail(String columnCode);
}
