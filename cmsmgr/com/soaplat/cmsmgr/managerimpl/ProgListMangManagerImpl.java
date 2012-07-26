package com.soaplat.cmsmgr.managerimpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.soaplat.cmsmgr.bean.Bpmc;
import com.soaplat.cmsmgr.bean.FlowActivityOrder;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.bean.ProgListMangDetail;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.dto.ColumnState;
import com.soaplat.cmsmgr.dto.ProgListState;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IProgListMangManager;
import com.soaplat.cmsmgr.util.DateUtil;

/**
 * Title 		:the Class ProgListMangManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgListMangManagerImpl implements IProgListMangManager {
	private static final Logger cmsLog = Logger.getLogger("Cms");
	private List<String> afterActionList = null;
	
	/** The ProgListMangdao. */
	IBaseDAO baseDAO;

	/** The getcmspk. */
	IGetPK getcmspk;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		if (object.length > 0) {
			for (int i = 0; i < object.length; i++) {
				baseDAO.delete(object[i]);
			}
		} else {
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {

		ProgListMang progListMang = (ProgListMang) baseDAO.getById(
				ProgListMang.class, pkid);
		if (progListMang != null) {
			baseDAO.delete(progListMang);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List progListManglist = baseDAO.findAll("ProgListMang",
				"progListMangid");
		return progListManglist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang
	 * .String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List progListManglist = baseDAO.findByProperty("ProgListMang",
				propertyName, value);
		return progListManglist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgListMang progListMang = (ProgListMang) baseDAO.getById(
				ProgListMang.class, pkid);
		return progListMang;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgListMang progListMang = (ProgListMang) baseDAO.loadById(
				ProgListMang.class, pkid);
		return progListMang;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		baseDAO.save(object);
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		if (object.length > 0) {
			for (int i = 0; i < object.length; i++) {
				this.save(object[i]);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		if (object.length > 0) {
			for (int i = 0; i < object.length; i++) {
				this.update(object[i]);
			}
		}
	}

	public List findbyExample(Object exampleentity) {
		List list = baseDAO.findbyExample(exampleentity);
		return list;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}

	// 20100108 10:24
	// 根据日期，查询栏目单总表或者主表管理表TPROGLISTMANG，
	public List getProgListMangsByDate(String dateFrom, // 日期起始，格式：yyyy-MM-dd
			String dateTo // 日期终止，格式：yyyy-MM-dd
	) {
		// 返回：
		// List<Object[]>
		// (ProgListMang)Object[]
		// (FlowAction)Object[]
		Map map = new HashMap();
		map.put("datefrom", dateFrom);
		map.put("dateto", dateTo);
		List list = baseDAO
				.queryByNamedQuery("select_ProgListMangsByDate", map);
		return list;
	}

	/**
	 * 查询编单状态不为数据导入的节目包编号
	 * @param before
	 * @param end
	 * @param act
	 * @return
	 */
	public List getProgListMangsByDate2(
			String before,							// 日期起始，格式：yyyyMMddHHmmss 20100601000000
			String end,								// 日期终止，格式：同上
			String act
	) {
		// 返回：
		// List<Object[]>
		// 		(ProgListMang)Object[]
		// 		(FlowAction)Object[]
		Map map = new HashMap();
		map.put("before", before);
		map.put("end", end);
		map.put("act", act);
		//map.put("playDate", new Date());
		List list = baseDAO.queryByNamedQuery("query.proglistmang.by.date", map);
		return list;
	}

	/**
	 * 根据编单日期字符串获取当月每一天的编单状态
	 * @param dateStr 编单日期
	 * @param operator 操作人员ID
	 * @return 返回当月每一天的编单状态是否为可编辑, 状态为0表示为可编单状态, 为-1表示不可编单状态..
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListState> getState(String dateStr, 
			String currAct, String operator) {
		if (null == currAct || 0 == currAct.trim().length()) {
			currAct = "FU00000077";						// 当前活动编号
		}
		
//		String maxProgDate = this.baseDAO.getMaxPropertyValue("ProgListMang", "scheduledate");
//		String beforeDateStr = DateUtil.getBeforeDaysStrList(dateStr, 1).get(0);
//		if (!this.baseDAO.isExist("ProgListMang", "scheduledate", beforeDateStr)) {
//			throw new Exception("前一天的编单未初始化, 不允许跳跃式初始化编单!");
//		}
//		
//		if (!this.baseDAO.isExist("ProgListMang", "scheduledate", currDateStr)) {
//			List<PortalColumn> valiColumns = this.baseDAO
//					.queryByNamedQuery("query.PortalColumn.by.isleaf.validFlag");
//			ProgListMang progListMang = new ProgListMang(currDateStr,  
//					currAct, operator, new Date());
//			this.save(progListMang);
//			
//			for (PortalColumn portalColumn : valiColumns) {
//				String strCurMaxPK = this.baseDAO.getMaxPropertyValue(
//						"ProgListMangDetail", "mangdetailid");
//				String strMaxPK = this.getcmspk.GetTablePK(
//						"ProgListMangDetail", strCurMaxPK);
//				ProgListMangDetail progListMangDetail = new ProgListMangDetail(
//						strMaxPK, currDateStr, portalColumn.getColumnclassid());
//				progListMangDetail.setIdAct(currAct);
//				progListMangDetail.setInputmanid(operator);
//				progListMangDetail.setInputtime(new Date());
//				this.save(progListMangDetail);
//			}
//		}
		
//		List<String> progIDList = DateUtil.getCurrMonthDateStrList(dateStr);
		String currDateStr = dateStr.replaceAll("[-|/| |\\.]", "") + "000000";
		List<ProgListState> progListStates = new ArrayList<ProgListState>();
		
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("scheduleDate", currDateStr.substring(0, 6) + "%");
		List<ProgListMang> progListMangs = (List<ProgListMang>) 
				this.baseDAO.queryNamed(
						"query.ProgListMang.by.scheduledate.like", map);
//		List<PortalColumn> valiColumns = this.baseDAO
//				.queryByNamedQuery("query.PortalColumn.by.isleaf.validFlag");
		
		for (ProgListMang p : progListMangs) {
			ProgListState progListState = new ProgListState(p.getScheduledate());
			if (currAct.equalsIgnoreCase(p.getIdAct())) {
				progListState.setState(0);
			} else {
				progListState.setState(-1);
			}
			progListStates.add(progListState);
		}
//		
//		for (String string : progIDList) {
//			ProgListState progListState = new ProgListState(string, 0);
//			progListStates.add(progListState);
//			
//			ProgListMang progListMang = new ProgListMang(string,  
//					currAct, operator, new Date());
//			this.save(progListMang);
//			
//			for (PortalColumn portalColumn : valiColumns) {
//				String strCurMaxPK = this.baseDAO.getMaxPropertyValue(
//						"ProgListMangDetail", "mangdetailid");
//				String strMaxPK = this.getcmspk.GetTablePK(
//						"ProgListMangDetail", strCurMaxPK);
//				ProgListMangDetail progListMangDetail = new ProgListMangDetail(
//						strMaxPK, string, portalColumn.getColumnclassid());
//				progListMangDetail.setIdAct(currAct);
//				progListMangDetail.setInputmanid(operator);
//				progListMangDetail.setInputtime(new Date());
//				this.save(progListMangDetail);
//			}
//		}
//		
		return progListStates;
	}
	
	/**
	 * 初始化指定日期的编单
	 * @param dateStr 日期字符串 , 格式: yyyy-MM-dd
	 * @param currAct 当前初始化活动编号
	 * @param operator 操作人员
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void saveProgMangAndDetail(String scheduleDate, 
			String currAct, String operator) throws Exception {
		Date currDate = new Date();
		if (null == currAct || 0 == currAct.trim().length()) {
			currAct = "FU00000077";						// 当前活动编号
		}
		
		String beforeDateStr = DateUtil.getBeforeDaysStrList(scheduleDate, 1).get(0);
		if (!this.baseDAO.isExist("ProgListMang", "scheduledate", beforeDateStr)) {
			throw new Exception("前一天[ " + beforeDateStr + " ]的编单未初始化, 不允许跳跃式初始化编单!");
		}
		
		if (!this.baseDAO.isExist("ProgListMang", "scheduledate", scheduleDate)) {
			List<PortalColumn> valiColumns = this.baseDAO
					.queryByNamedQuery("query.PortalColumn.by.isleaf.validFlag");
			ProgListMang progListMang = new ProgListMang(scheduleDate,  
					currAct, operator, currDate);
			this.save(progListMang);
			cmsLog.debug("初始化编单总表成功, 编单日期: " + scheduleDate);
			
			for (PortalColumn portalColumn : valiColumns) {
				String strCurMaxPK = this.baseDAO.getMaxPropertyValue(
						"ProgListMangDetail", "mangdetailid");
				String strMaxPK = this.getcmspk.GetTablePK(
						"ProgListMangDetail", strCurMaxPK);
				ProgListMangDetail progListMangDetail = new ProgListMangDetail(
						strMaxPK, scheduleDate, portalColumn.getColumnclassid());
				progListMangDetail.setIdAct(currAct);
				progListMangDetail.setInputmanid(operator);
				progListMangDetail.setInputtime(currDate);
				this.save(progListMangDetail);
				cmsLog.debug("初始化编单细表成功, 编单日期: " + scheduleDate + 
						" , 栏目编号: " + portalColumn.getColumnclassid() + 
						" , 栏目名称: " + portalColumn.getColumnname());
			}
		}
	}

	/**
	 * 根据日期和栏目ID查询编单明细
	 * @param dateStr 编单日期
	 * @param columnId 栏目ID
	 * @return List<ProgListDetail>
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListDetail> queryProgList(String dateStr, String columnId) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("scheduledate", dateStr);
		map.put("columnclassid", columnId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.by.scheduledate.and.columnclassid", map);
	}
	
	/**
	 * 添加编单明细记录
	 * @param dateStr 编单日期
	 * @param progPackageId 节目包ID
	 * @param columnId 栏目ID
	 * @param scheduleTag 是否加入节目预告
	 * @param operator 操作人员ID
	 */
	@SuppressWarnings("unchecked")
	public void saveAddProgListDetail(String dateStr, String progPackageId, 
			String columnId, String scheduleTag, String operator) {
//		String currAct = "FU00000077";						// 当前活动编号
		ProgPackage progPackage = (ProgPackage) this.baseDAO.getById(
				ProgPackage.class, progPackageId);
		PortalColumn portalColumn = (PortalColumn) this.baseDAO.getById(
				PortalColumn.class, columnId);
		
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("productid", progPackageId);
		List<String> eTitleList = this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.ETitle.by.productid", map);
		String eTitle = eTitleList.toString().replaceAll(" ", "");
		
		ProgListDetail progListDetail = new ProgListDetail(dateStr, columnId, progPackageId);
		String strCurMaxPK = baseDAO.getMaxPropertyValue("ProgListDetail", "proglistdetailid");
		String strMaxPK = getcmspk.GetTablePK("ProgListDetail", strCurMaxPK);
		progListDetail.setProglistdetailid(strMaxPK);
		progListDetail.setScheduleTag(scheduleTag);
		progListDetail.setProductName(progPackage.getProductname());
		progListDetail.setProductname(progPackage.getProductname());
		progListDetail.setDefcatseq(portalColumn.getDefcatseq());
		progListDetail.setETitle(eTitle.substring(1, eTitle.length() - 1));
		progListDetail.setLnum(0L);
//		progListDetail.setIdAct(currAct);
		progListDetail.setPop(operator);
		progListDetail.setUploaddate(new Date());
		this.baseDAO.save(progListDetail);
	}
	
	/**
	 * 删除编单明细记录
	 * @param progListDetailID 编单明细ID
	 * @param operator 操作人员ID
	 */
	public void deleteProgListDetail(String progListDetailID, String operator) {
		this.baseDAO.deleteById(ProgListDetail.class, progListDetailID);
	}
	
	/**
	 * 修改编单活动
	 * @param dateStr 编单日期
	 * @param columnID 栏目ID
	 * @param actionSeq 活动顺序[P:顺序]
	 * @param operator 操作人员ID
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void updateProgAct(String currAct, String dateStr, String columnID, 
			String actionSeq, String operator) throws Exception {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("scheduledate", dateStr);
		map.put("columnclassid", columnID);
		List<ProgListMangDetail> progListMangDetails = this.baseDAO.queryByNamedQuery(
				"query.ProgListMangDetail.by.productid", map);
		for (ProgListMangDetail progListMangDetail : progListMangDetails) {
//			if (currAct.equalsIgnoreCase(progListMangDetail.getIdAct())) {
			map.clear();
			map.put("currAct", currAct);
			map.put("state", actionSeq);
			List<String> action = this.baseDAO.queryByNamedQuery(
					"query.FlowActivityOrder.by.flowactivityidparent", map);
			
			if (!currAct.equalsIgnoreCase(progListMangDetail.getIdAct())) {
				cmsLog.debug("当前栏目[ " + progListMangDetail.getColumnclassid() + " ]状态不符合要求, 不修改活动编号!");
				continue;
//				throw new Exception("栏目编单的流程活动不是当前流程活动, 流程活动发送失败!");
			}
			
			if (0 < action.size()) {
				progListMangDetail.setIdAct(action.get(0));
				this.baseDAO.update(progListMangDetail);
			
				boolean flag = true;
				List<ProgListMangDetail> progListMangDetailList = this.baseDAO.findByProperty(
						"ProgListMangDetail", "scheduledate", dateStr);
				List<FlowActivityOrder> flowActivityOrders = this.baseDAO.findByProperty(
						"FlowActivityOrder", "state2", actionSeq);
				this.afterActionList = new ArrayList<String>();
				this.getAfterActionList(flowActivityOrders, currAct);
				for (ProgListMangDetail progListMangDetail2 : progListMangDetailList) {
					if (!this.afterActionList.contains(progListMangDetail2.getIdAct())) {
						flag = false;
						break;
					}
				}
					
				if (flag) {
					ProgListMang progListMang = (ProgListMang) this.baseDAO.getById(
							ProgListMang.class, dateStr);
					progListMang.setIdAct(action.get(0));
					this.baseDAO.save(progListMang);
				}
			}
//			}
		}
		
		/**
		 * 记录操作历史
		 */
		Bpmc bpmc = new Bpmc(dateStr, null, operator, currAct, null, null, columnID, actionSeq);
		this.baseDAO.save(bpmc);
	}
	
	/**
	 * 不使用
	 * @param dateStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProgListState getColumnState(String dateStr) {
		String currAct = "FU00000077";						// 编单定义活动编号
		Map<String, String> map = new HashMap<String, String>();
		map.put("scheduledate", dateStr);
		List<ProgListMangDetail> progListMangDetails = baseDAO.queryByNamedQuery(
				"select_ProgListMangDetailsByScheduledate", map);
		
		ProgListState progListState = new ProgListState(dateStr, 0);
		List<ColumnState> list = new ArrayList<ColumnState>();
		for (ProgListMangDetail progListMangDetail : progListMangDetails) {
			ColumnState columnState = new ColumnState();
			if (currAct.equalsIgnoreCase(progListMangDetail.getIdAct())) {
				columnState.setColumnID(progListMangDetail.getColumnclassid());
				columnState.setColumnState(0);
			} else {
				columnState.setColumnID(progListMangDetail.getColumnclassid());
				columnState.setColumnState(-1);
			}
			list.add(columnState);
		}
		progListState.setColumnStates(list);
		
		return progListState;
	}
	
	/**
	 * 获取栏目编单状态
	 * @param dateStr 编单日期
	 * @param columnID 栏目ID
	 * @return 返回栏目编单状态[0:可编单状态, -1:不可编单状态]
	 */
	@SuppressWarnings("unchecked")
	public int getCulumnState(String dateStr, String columnID, String currAct) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("scheduledate", dateStr);
		map.put("columnclassid", columnID);
		List<ProgListMangDetail> progListMangDetails = baseDAO.queryByNamedQuery(
				"select_ProgListMangDetailsByScheduleDateAndColumnclassid", map);
		ProgListMangDetail progListMangDetail = 0 < progListMangDetails.size() ? 
				progListMangDetails.get(0) : null;
				
		if (null == progListMangDetail) {
			throw new NullPointerException("栏目编单不存在!");
		}
		return currAct.equalsIgnoreCase(progListMangDetail.getIdAct()) ? 0 : -1;
	}
	
	/**
	 * 修改明细明细是否生成发布文件状态
	 * @param progListDetailID 编单明细ID
	 * @param scheduleTag 是否生成发布文件[Y:生成, N:不生成]
	 */
	public void updateProgListDetailScheduleTag(
			String progListDetailID, String scheduleTag) {
		ProgListDetail progListDetail = (ProgListDetail) this.baseDAO.getById(
				ProgListDetail.class, progListDetailID);
		if (null == progListDetail) {
			throw new NullPointerException("编单明细不存在!");
		} else {
			progListDetail.setScheduleTag(scheduleTag);
			this.baseDAO.update(progListDetail);
		}
	}
	
	/**
	 * 根据当前流程活动编号, 从流程活动列表中获取当前流程活动之后的活动列表
	 * @param flowActivityOrders 流程活动列表
	 * @param currentAct 当前流程活动
	 */
	public void getAfterActionList(List<FlowActivityOrder> flowActivityOrders, String currentAct) {
		for (FlowActivityOrder flowActivityOrder : flowActivityOrders) {
			if (currentAct.equalsIgnoreCase(flowActivityOrder.getFlowactivityidparent())) {
				this.afterActionList.add(flowActivityOrder.getFlowactivityidchild());
				getAfterActionList(flowActivityOrders, flowActivityOrder.getFlowactivityidchild());
			}
		}
	}
	
	/**
	 * 根据流程活动ID, 日期区间查询ProgListMang表信息
	 * @param currentActions 当前活动ID列表
	 * @param timeStart 日期区间起始, 格式: 20100909000000
	 * @param timeEnd
	 * @return List<ProgListMang>
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListMang> queryProgListMangs(List<String> currentActions, 
			String timeStart, String timeEnd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("currentAction", currentActions);
		params.put("timeStart", timeStart);
		params.put("timeEnd", timeEnd);
		return (List<ProgListMang>) this.baseDAO.queryNamed(
				"query.ProgListMang.by.currentAction.scheduleDate", params);
	}
	
	/**
	 * 根据编单日期集合, 查询该集合中的编单总表的活动集合
	 * @param scheduleDates 编单日期集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryActionsByScheduleDates(List<String> scheduleDates) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("scheduleDates", scheduleDates);
		return (List<String>) this.baseDAO.queryNamed(
				"query.ProgListMang.idAct.by.scheduleDates", params);
	}
	
	public static void main(String[] args) throws ParseException {
	}
}
