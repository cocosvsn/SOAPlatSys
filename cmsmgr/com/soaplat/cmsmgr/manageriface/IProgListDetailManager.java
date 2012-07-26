package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgListDetailManager extends IBaseManager {

	// 根据栏目和栏目单编号，查询得到栏目单的栏目下的节目包
//	public List getProgListDetailsByPdateAndColumnclassid(String pdate, String columnclassid);

	// 根据栏目单编号和节目包编号，查询得到栏目单细表记录
//	public List getProgListDetailsByPdateAndProductidAndColumnclassid(String pdate, String productid, String columnclassid);
	
	// 根据栏目编号和节目状态，查询得到栏目单细表记录
//	public List getProgListDetailsByColumnclassidAndState1NotEqual(String columnclassid, Long state1);
	
	// 根据编单日期，查编单
	public List<ProgListDetail> getProgListDetailsByDate(String date);
	
	// Added by Andy at 20091206 17:44
	public List getProgramFilesesByDate(String date);
	
	// 
	public List getProgListDetailsByDateAndDefcatseq(String date, String defcatseq);
	
	// 这就是传说中的方法6
	// 20100115 9:28
	// 根据日期和栏目单code序列，查询，得到栏目单详细和节目包
	public List getProgListDetailsProgPackagesByDateAndDefcatseq(String date, String defcatseq);
	
	// 根据scheduledate和columnclassid，查询TPROGLISTDETAIL，得到proglistdetail和progpackage
	public List getProgListDetailsByScheduledateAndColumnclassid(String date, String columnclassid);
	
	// Edited by Andy at 20100109 17:42
	// 根据Date、Defcatseq和操作人员，查询，得到栏目单详细
	public List getProgListDetailsByDateAndDefcatseqAndOper(String date, String defcatseq, String operatorid);
	
	// Edited by Andy at 20100112 22:30
	// 根据 节目包id，查询得到上线的栏目单详细列表
	public List getOnlineProgListDetailsByProductid(String productid);
	
	// 20100223 15:41
	// 根据节目包和栏目，查询栏目单详细
	public List getProgListDetailsByProductidColumnclassid(
			String productid, 
			String columnclassid
			);
	// 20100223 16:08
	// 根据Date、Defcatseq和操作人员，查询，得到栏目单详细。如果节目包不存在，也可以查询得到栏目单详细记录
	public List getProgListDetailsByDateAndDefcatseqAndOperWithoutProgPackage(
			String date, 
			String defcatseq, 
			String operatorid
			);
	
	// 20100419 13:22
	// 查询节目包，根据节目包名称、栏目id
	public List getProgPackagesByProductnameColumnclassid(
			String productname,
			String columnclassid
			);
	
	// 20100427 17:12
	// 根据日期和栏目单code序列，查询当日上线的节目包，得到栏目单详细和节目包
	public List getTodayOnlineProgListDetailsByDateAndDefcatseq(
			String date, 
			String defcatseq
			);
	
	/**
	 * 根据节目包ID查询, 节目包上下线日期
	 * @param progPackageId 节目包ID 
	 * @return  List<Object[3]>
	 * object[0]: ProgListDetail.proglistdetailid
	 * object[1]: ProgListDetail.validFrom
	 * object[2]: ProgListDetail.validTo
	 */
	public Object[] queryProgListDetailByProgramFileId(String progPackageId);
	
	/**
	 * HuangBo addition by 2010年10月14日 14时25分
	 * 根据节目包产品ID 查询该产品所被应用到的编单明细
	 * @param productInfoId 产品ID
	 * @return 返回编单明细集合
	 */
	public List<ProgListDetail> queryProgListDetailByProductInfoID(String productInfoId);

	/**
	 * 根据编单日期, 栏目ID查询指定编单日期, 栏目下的所有编单明细信息
	 * @param scheduleDateStr 编单日期, 格式: 20100909000000
	 * @param columnId 栏目ID
	 * @return 返回编单明细集合
	 */
	public List<ProgListDetail> queryDetailsByScheduleAndColumnId(
			String scheduleDateStr, String columnId);

	/**
	 * 修改编单明细表记录的JsFileId属性
	 * @param scheduleDate 编单日期ID
	 * @param progPackageId 节目包ID
	 * @param jsFileId JS文件ID
	 */
	public void updateJsFileId(String scheduleDate, String progPackageId, String jsFileId);
	
	/**
	 * 根据编单日期ID, 查询是否存在JsFileId属性为null的编单明细
	 * @param scheduleDate 编单日期Id
	 * @return 返回是否存在JsFileId属性为null的编单明细
	 */
	public boolean existsNullJsFileId(String scheduleDate);
	
	/**
	 * 根据编单日期ID, 查询前台页面显示所需要的对象集合
	 * @param scheduleDate 编单日期ID
	 * @return 
	 */
	public List<?> queryByScheduleDate(String scheduleDate);
	
	/**
	 * 根据编单日期ID, 查询当天及往后日期的编单明细节目包ID
	 * @param scheduleDate 编单日期ID
	 * @return 返回节目包ID集合
	 */
	public List<String> queryProgPackageIdsByScheduleDate(String scheduleDate);
	
	/**
	 * 根据编单日期和栏目id，查询栏目的编单（ProgListDetail）
	 * @param scheduledate 编单日期，格式：yyyyMMdd000000
	 * @param columnclassid 栏目id
	 * @since 2010-09-21
	 * @调用hql getProgListDetailsByScheduledateColumnclassid
	 */
	public List getProgListDetailsByScheduledateColumnclassid(
			String scheduledate,
			String columnclassid
			);

	/**
	 * 根据编单日期和节目包ID, 查询节目包当天在哪些品牌下编单
	 * @param scheduleDate 编单日期
	 * @param progPackageId 节目包ID
	 * @return
	 */
	public List<String> querySiteCodeByScheduleDateAndPackageId(
			String scheduleDate, String progPackageId);

	/**
	 * 根据节目包ID, 查询该节目包编单日期集合
	 * @param progPackageId 节目包ID
	 * @return List<String> 编单日期ID集合
	 */
	public List<String> queryScheduleDateByProgPackageId(String progPackageId);
	
	/**
	 * 根据编单日期和节目包ID, 查询当天节目包的编单细表
	 * @param scheduleDate 编单日期
	 * @param progPackageId 节目包ID
	 * @return
	 */
	public List<ProgListDetail> queryProgListDetailsByScheduleDateAndPackageId(
			String scheduleDate, String progPackageId);

	/**
	 * 根据编单日期, 查询当前已经编单的节目包ID集合
	 * @param scheduleDate 编单日期ID
	 * @return
	 */
	public List<String> queryProgPackageIdByScheduleDate(String scheduleDate);
	
	/**
	 * 根据上线日期区间, 编单细表中的节目包集合
	 * @param startTime 上线日期开始
	 * @param endTime 上线日期结束
	 * @return 编单细表节目包集合
	 */
	public List<ProgListDetail> queryByScheduleArea(String startTime, String endTime);
	
	/**
	 * 根据当前日期和节目包编号查询该节目包是否存在于当天及以后的编单中
	 * @param scheduleDate 当前日期编单ID
	 * @param progPackageId 节目包ID
	 * @return 返回指定节目包存在的大于等于当前日期的编单ID
	 */
	public List<String> queryScheduleDateByScheduleDateAndProgPackage(
			String scheduleDate, String progPackageId);

	/**
	 * 根据编单日期, 统计各品牌播发量大小
	 * @param scheduleDate 编单日期ID
	 * @return
	 */
	public List<Object[]> checkProgSize(String scheduleDate);
	
	/**
	 * 根据栏目ID查询到栏目未完成的编单日期和所处的流程
	 * @param columnId 栏目ID
	 * @return List<Object[]>
	 * objects[0]: 编单日期ID
	 * objects[1]: 流程活动名称
	 */
	public List<Object[]> queryScheduleDateAndActionName(String columnId);
}
