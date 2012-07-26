package com.soaplat.cmsmgr.manageriface;

import java.text.ParseException;
import java.util.List;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.dto.ProgListState;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgListMangManager extends IBaseManager {

	// 20100108 10:24
	// 根据日期，查询栏目单总表或者主表管理表TPROGLISTMANG，
	@SuppressWarnings("rawtypes")
	public List getProgListMangsByDate(
			String dateFrom,							// 日期起始，格式：yyyy-MM-dd
			String dateTo								// 日期终止，格式：yyyy-MM-dd
			);
	
	/**
	 * 查询编单状态不为数据导入的节目包编号
	 * @param before
	 * @param end
	 * @param act
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getProgListMangsByDate2(
			String before,							// 日期起始，格式：yyyyMMddHHmmss 20100601000000
			String end,								// 日期终止，格式：同上
			String act
			);
	
	/**
	 * 根据编单日期字符串获取当月每一天的编单状态
	 * @param dateStr 编单日期
	 * @param operator 操作人员ID
	 * @return 返回当月每一天的编单状态是否为可编辑, 状态为0表示为可编单状态, 为-1表示不可编单状态..
	 * @throws ParseException 日期字符串格式错误解析异常
	 */
	public List<ProgListState> getState(String dateStr, 
			String currAct, String operator) throws ParseException;

	/**
	 * 初始化指定日期的编单
	 * @param dateStr 日期字符串 , 格式: yyyy-MM-dd
	 * @param currAct 当前初始化活动编号
	 * @param operator 操作人员
	 * @throws Exception
	 */
	public void saveProgMangAndDetail(String scheduleDate, 
			String currAct, String operator) throws Exception;
	
	/**
	 * 根据日期和栏目ID查询编单明细
	 * @param dateStr 编单日期
	 * @param columnId 栏目ID
	 * @return List<ProgListDetail>
	 */
	public List<ProgListDetail> queryProgList(String dateStr, String columnId);
	
	/**
	 * 添加编单明细记录
	 * @param dateStr 编单日期
	 * @param progPackageId 节目包ID
	 * @param columnId 栏目ID
	 * @param scheduleTag 是否加入节目预告
	 * @param operator 操作人员ID
	 */
	public void saveAddProgListDetail(String dateStr, String progPackageId, 
			String columnId, String scheduleTag, String operator);
	
	/**
	 * 删除编单明细记录
	 * @param progListDetailID 编单明细ID
	 * @param operator 操作人员ID
	 */
	public void deleteProgListDetail(String progListDetailID, String operator);
	
	/**
	 * 修改编单活动
	 * @param dateStr 编单日期
	 * @param columnID 栏目ID
	 * @param actionSeq 活动顺序[P:顺序]
	 * @param operator 操作人员ID
	 */
	public void updateProgAct(String currAct, String dateStr, String columnID, 
			String actionSeq, String operator) throws Exception;

	/**
	 * 获取栏目编单状态
	 * @param dateStr 编单日期
	 * @param columnID 栏目ID
	 * @return 返回栏目编单状态[0:可编单状态, -1:不可编单状态]
	 */
	public int getCulumnState(String dateStr, String columnID, String currAct);

	/**
	 * 修改明细明细是否生成发布文件状态
	 * @param progListDetailID 编单明细ID
	 * @param scheduleTag 是否生成发布文件[Y:生成, N:不生成]
	 */
	public void updateProgListDetailScheduleTag(
			String progListDetailID, String scheduleTag);

	/**
	 * 根据流程活动ID, 日期区间查询ProgListMang表信息
	 * @param currentAction 当前活动ID列表
	 * @param timeStart 日期区间起始, 格式: 20100909000000
	 * @param timeEnd
	 * @return List<ProgListMang>
	 */
	public List<ProgListMang> queryProgListMangs(List<String> currentActions, 
			String timeStart, String timeEnd);

	/**
	 * 根据编单日期集合, 查询该集合中的编单总表的活动集合
	 * @param scheduleDates 编单日期集合
	 * @return
	 */
	public List<String> queryActionsByScheduleDates(List<String> scheduleDates);
}
