package com.soaplat.cmsmgr.managerimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;

/**
 * Title 		:the Class ProgListDetailManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgListDetailManagerImpl implements IProgListDetailManager {
	
	/** The ProgListDetaildao. */
	IBaseDAO baseDAO;
	
	/** The getcmspk. */
	IGetPK getcmspk;
	
	
	public ProgListDetailManagerImpl() {}
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		//锟斤拷删锟斤拷
		if(object.length>0){
			for(int i=0;i<object.length;i++){
				baseDAO.delete(object[i]);
			}
		}else{
			return ;
		}	
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {

		ProgListDetail ProgListDetail=(ProgListDetail)baseDAO.getById(ProgListDetail.class, pkid);
		if(ProgListDetail!=null){
			baseDAO.delete(ProgListDetail);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List ProgListDetaillist=baseDAO.findAll("ProgListDetail","proglistdetailid");
		return ProgListDetaillist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List ProgListDetaillist=baseDAO.findByProperty("ProgListDetail", propertyName, value);
		return ProgListDetaillist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgListDetail ProgListDetail=(ProgListDetail)baseDAO.getById(ProgListDetail.class, pkid);
		return ProgListDetail;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgListDetail ProgListDetail=(ProgListDetail)baseDAO.loadById(ProgListDetail.class, pkid);
		return ProgListDetail;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		ProgListDetail ProgListDetail = new ProgListDetail();
		ProgListDetail = (ProgListDetail)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("ProgListDetail", "proglistdetailid");
		String strMaxPK = getcmspk.GetTablePK("ProgListDetail", strCurMaxPK);
		ProgListDetail.setProglistdetailid(strMaxPK);
		baseDAO.save(ProgListDetail);

		return baseDAO.getById(ProgListDetail.class, strMaxPK);
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);
		

	}

	/**
	 * Sets the baseDAO.
	 * 
	 * @param baseDAO the new baseDAO
	 */

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		if (object.length>0){
			for(int i=0;i<object.length;i++){
				this.save(object[i]);
			}
			
		}
		
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		if (object.length>0){
			for(int i=0;i<object.length;i++){
				this.update(object[i]);
			}
			
		}
		
	}

	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}
	
//	// 根据栏目和栏目单编号，查询得到栏目单的栏目下的节目包
//	public List getProgListDetailsByPdateAndColumnclassid(String pdate, String columnclassid)
//	{
//		Map map = new HashMap();
//		map.put("pdate", pdate);
//		map.put("columnclassid", columnclassid);
//		List list = baseDAO.queryByNamedQuery("select_progListDetailsByPdateAndColumnclassid", map);
//		return list;
//	}
	
	// 根据栏目单编号和节目包编号，查询得到栏目单细表记录
//	public List getProgListDetailsByPdateAndProductidAndColumnclassid(String pdate, String productid, String columnclassid)
//	{
//		Map map = new HashMap();
//		map.put("pdate", pdate);
//		map.put("productid", productid);
//		map.put("columnclassid", columnclassid);
//		List list = baseDAO.queryByNamedQuery("select_progListDetailsByPdateAndProductidAndColumnclassid", map);
//		return list;
//	}
	
	// 根据栏目编号和节目状态，查询得到栏目单细表记录
//	public List getProgListDetailsByColumnclassidAndState1NotEqual(String columnclassid, Long state1)
//	{
//		Map map = new HashMap();
//		map.put("columnclassid", columnclassid);
//		map.put("state1", state1);
//		List list = baseDAO.queryByNamedQuery("select_ProgListDetailsByColumnclassidAndState1NotEqual", map);
//		return list;
//	}
	
	// 根据编单日期，查编单
	public List<ProgListDetail> getProgListDetailsByDate(String date)
	{
//		SimpleDateFormat dfB = new SimpleDateFormat("yyyy-mm-dd 00:00:00");
//		SimpleDateFormat dfE = new SimpleDateFormat("yyyy-mm-dd 23:59:59");
		
//		DateFormat formatB = new SimpleDateFormat("yyyy-mm-dd 00:00:00");
//		DateFormat formatE = new SimpleDateFormat("yyyy-mm-dd 23:59:59"); 
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date dB = format.parse(date + " 00:00:00");
			Date dE = format.parse(date + " 23:59:59");
//			String strB = format.format(dB);
//			String strE = format.format(dE);
//			java.sql.Date dateB = java.sql.Date.valueOf(strB);
//			java.sql.Date dateE = java.sql.Date.valueOf(strE);
			java.sql.Timestamp dateB = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp dateE = new java.sql.Timestamp(dE.getTime());
			
			Map map = new HashMap();
			map.put("dateB", dateB);
			map.put("dateE", dateE);
			List<ProgListDetail> list = baseDAO.queryByNamedQuery("select_ProgListDetailsByDate", map);
			return list;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	// Added by Andy at 20091206 17:44
	public List getProgramFilesesByDate(String date)
	{
//		SimpleDateFormat formatB = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//		SimpleDateFormat formatE = new SimpleDateFormat("yyyy-MM-dd 23:59:59"); 
//		String strB = formatB.format(date);
//		String strE = formatE.format(date);
		
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
			Date dB = format.parse(date + " 00:00:00");
			Date dE = format.parse(date + " 23:59:59");
//			String strB = format.format(dB);
//			String strE = format.format(dE);
//			java.sql.Date validFrom = java.sql.Date.valueOf(strB);
//			java.sql.Date validTo = java.sql.Date.valueOf(strE);
//			java.sql.Date validFrom = new java.sql.Date(dB.getTime());
//			java.sql.Date validTo = new java.sql.Date(dE.getTime());
			java.sql.Timestamp validFrom = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp validTo = new java.sql.Timestamp(dE.getTime());
			
			Map map = new HashMap(0);
			map.put("validFrom", validFrom);			// 00:00:00
			map.put("validTo", validTo);				// 23:59:59
			List list = baseDAO.queryByNamedQuery("select_ProgramFilesesByDate", map);
			return list;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}	
	}
	
	// 
	public List getProgListDetailsByDateAndDefcatseq(String date, String defcatseq)
	{
		try
		{
			List list = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date dB = format.parse(date + " 00:00:00");
			Date dE = format.parse(date + " 23:59:59");
			java.sql.Timestamp validFrom = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp validTo = new java.sql.Timestamp(dE.getTime());
			

			defcatseq = defcatseq + "%";

			Map map = new HashMap(0);
			map.put("validFrom", validFrom);			// 00:00:00
			map.put("validTo", validTo);				// 23:59:59
			map.put("defcatseq", defcatseq);
			list = baseDAO.queryByNamedQuery("select_progListDetailsByDateAndDefcatseq", map);

			return list;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	// 根据scheduledate和columnclassid，查询TPROGLISTDETAIL，得到proglistdetail和progpackage
	public List getProgListDetailsByScheduledateAndColumnclassid(
			String date, 
			String columnclassid
			)
	{
		// 返回：List<Object[]>
		// 		(ProgListDetail)Object[0]
		// 		(ProgPackage)Object[1]
		
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
			Date dB = format.parse(date + " 00:00:00");
			Date dE = format.parse(date + " 23:59:59");
			java.sql.Timestamp validFrom = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp validTo = new java.sql.Timestamp(dE.getTime());
			
			Map map = new HashMap(0);
			map.put("validFrom", validFrom);			// 00:00:00
			map.put("validTo", validTo);				// 23:59:59
			map.put("columnclassid", columnclassid);
			List list = baseDAO.queryByNamedQuery("select_ProgListDetailsByScheduledateAndColumnclassid", map);
			return list;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	// Edited by Andy at 20100109 17:42
	// 根据Date、Defcatseq和操作人员，查询，得到栏目单详细
	public List getProgListDetailsByDateAndDefcatseqAndOper(
			String date, 
			String defcatseq, 
			String operatorid
			)
	{
		// 返回：
		// List<Object[]>
		//		(ProgListDetail)Object[0]
		//		(ProgPackage)Object[1]
		
		try
		{
			List list = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date dB = format.parse(date + " 00:00:00");
			Date dE = format.parse(date + " 23:59:59");
			java.sql.Timestamp validFrom = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp validTo = new java.sql.Timestamp(dE.getTime());
			

			defcatseq = defcatseq + "%";

			Map map = new HashMap(0);
			map.put("validFrom", validFrom);			// 00:00:00
			map.put("validTo", validTo);				// 23:59:59
			map.put("defcatseq", defcatseq);
//			map.put("operatorid", operatorid);
			list = baseDAO.queryByNamedQuery("select_progListDetailsByDateAndDefcatseqAndOper", map);

			return list;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	// Edited by Andy at 20100112 22:30
	// 根据 节目包id，查询得到上线的栏目单详细列表
	public List getOnlineProgListDetailsByProductid(String productid)
	{
		// 返回：
		// List<ProgListDetail>
		Map map = new HashMap(0);
		map.put("productid", productid);			// 
		List list = baseDAO.queryByNamedQuery("select_onlineProgListDetailsByProductid", map);
		return list;
	}
	
	// 这就是传说中的方法6
	// 20100115 9:28
	// 根据日期和栏目单code序列，查询，得到栏目单详细和节目包
	public List getProgListDetailsProgPackagesByDateAndDefcatseq(
			String date, 				// 格式 yyyy-MM-dd
			String defcatseq			// 栏目code序列
			)
	{
		// 返回：List<Object[]>
		//			(ProgListDetail)Object[0]
		//			(ProgPackage)Object[1]
		
		try
		{
			List list = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date dB = format.parse(date + " 00:00:00");
			Date dE = format.parse(date + " 23:59:59");
			java.sql.Timestamp validFrom = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp validTo = new java.sql.Timestamp(dE.getTime());
			

			defcatseq = defcatseq + "%";

			Map map = new HashMap(0);
			map.put("validFrom", validFrom);			// 00:00:00
			map.put("validTo", validTo);				// 23:59:59
			map.put("defcatseq", defcatseq);
			list = baseDAO.queryByNamedQuery("select_progListDetailsProgPackagesByDateAndDefcatseq", map);

			return list;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	// 20100223 15:41
	// 根据节目包和栏目，查询栏目单详细
	public List getProgListDetailsByProductidColumnclassid(
			String productid, 
			String columnclassid
			)
	{
		try
		{
			List list = null;
			
			Map map = new HashMap(0);
			map.put("productid", productid);			// 00:00:00
			map.put("columnclassid", columnclassid);				// 23:59:59
			list = baseDAO.queryByNamedQuery("select_ProgListDetailsByProductidColumnclassid", map);
			
			return list;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	// 20100223 16:08
	// 根据Date、Defcatseq和操作人员，查询，得到栏目单详细。如果节目包不存在，也可以查询得到栏目单详细记录
	public List getProgListDetailsByDateAndDefcatseqAndOperWithoutProgPackage(
			String date, 
			String defcatseq, 
			String operatorid
			)
	{
		try
		{
			List list = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date dB = format.parse(date + " 00:00:00");
			Date dE = format.parse(date + " 23:59:59");
			java.sql.Timestamp validFrom = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp validTo = new java.sql.Timestamp(dE.getTime());
			

			defcatseq = defcatseq + "%";

			Map map = new HashMap(0);
			map.put("validFrom", validFrom);			// 00:00:00
			map.put("validTo", validTo);				// 23:59:59
			map.put("defcatseq", defcatseq);
			list = baseDAO.queryByNamedQuery("select_progListDetailsByDateAndDefcatseqAndOperWithoutProgPackage", map);

			return list;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	// 20100419 13:22
	// 查询节目包，根据节目包名称、栏目id
	public List getProgPackagesByProductnameColumnclassid(
			String productname,
			String columnclassid
			)
	{
		// 返回：List<Object[]>
		//			(ProgPackage)Object[0]
		//			(ProgListDetail)Object[1]
		Map map = new HashMap(0);
		List list = null;
		if(productname == null || productname.equalsIgnoreCase(""))
		{
			map.put("columnclassid", columnclassid);				// 23:59:59
			list = baseDAO.queryByNamedQuery("select_ProgPackagesByColumnclassidOrderbyproductname", map);
		}
		else
		{
			productname = "%" + productname + "%";
			map.put("productname", productname);			// 00:00:00
			map.put("columnclassid", columnclassid);				// 23:59:59
			list = baseDAO.queryByNamedQuery("select_ProgPackagesByProductnameColumnclassid", map);
		}
		return list;
	}
	
	// 20100427 17:12
	// 根据日期和栏目单code序列，查询当日上线的节目包，得到栏目单详细和节目包
	public List getTodayOnlineProgListDetailsByDateAndDefcatseq(
			String date, 
			String defcatseq
	) {
		try {
			List list = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date dB = format.parse(date + " 00:00:00");
			Date dE = format.parse(date + " 23:59:59");
			java.sql.Timestamp validFrom = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp validTo = new java.sql.Timestamp(dE.getTime());
			

			defcatseq = defcatseq + "%";

			Map map = new HashMap(0);
			map.put("validFrom", validFrom);			// 00:00:00
			map.put("validTo", validTo);				// 23:59:59
			map.put("defcatseq", defcatseq);
			list = baseDAO.queryByNamedQuery("select_todayOnlineProgListDetailsByDateAndDefcatseq", map);

			return list;
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	/**
	 * 根据节目包ID查询, 节目包上下线日期
	 * @param progPackageId 节目包ID 
	 * @return  List<Object[3]>
	 * object[0]: ProgListDetail.proglistdetailid
	 * object[1]: ProgListDetail.validFrom
	 * object[2]: ProgListDetail.validTo
	 */
	@SuppressWarnings("unchecked")
	public Object[] queryProgListDetailByProgramFileId(String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageId", progPackageId);
		List<Object[]> list = this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.by.progPackageId", map);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 根据节目包产品ID 查询该产品所被应用到的编单明细
	 * @param productInfoId 产品ID
	 * @return 返回编单明细集合
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListDetail> queryProgListDetailByProductInfoID(
			String productInfoId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("productInfoID", productInfoId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.by.productInfoId", map);
	}

	/**
	 * 根据编单日期, 栏目ID查询指定编单日期, 栏目下的所有编单明细信息
	 * @param scheduleDateStr 编单日期, 格式: 20100909000000
	 * @param columnId 栏目ID
	 * @return 返回编单明细集合
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListDetail> queryDetailsByScheduleAndColumnId(
			String scheduleDateStr, String columnId) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("scheduleDateStr", scheduleDateStr);
		map.put("columnId", columnId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.by.scheduleDate.columnId", map);
	}
	
	/**
	 * 修改编单明细表记录的JsFileId属性
	 * @param scheduleDate 编单日期ID
	 * @param progPackageId 节目包ID
	 * @param jsFileId JS文件ID
	 */
	@SuppressWarnings("unchecked")
	public void updateJsFileId(String scheduleDate, String progPackageId, String jsFileId) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("scheduleDate", scheduleDate);
		map.put("progPackageId", progPackageId);
		List<ProgListDetail> progListDetails = this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.by.scheduleDate.progPackageId", map);
		for (ProgListDetail progListDetail : progListDetails) {
			progListDetail.setJsFileID(jsFileId);
			this.baseDAO.update(progListDetail);
		}
	}

	/**
	 * 根据编单日期ID, 查询是否存在JsFileId属性为null的编单明细
	 * @param scheduleDate 编单日期Id
	 * @return 返回是否存在JsFileId属性为null的编单明细
	 */
	@SuppressWarnings("unchecked")
	public boolean existsNullJsFileId(String scheduleDate) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("scheduleDate", scheduleDate);
		List<Object> list = this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.count.by.scheduleDate.jsFileId", map);
		if (0 < list.size()) {
			int nullJsFileIdCount = Integer.parseInt(list.get(0).toString());
			return 0 != nullJsFileIdCount;
		}
		return true;
	}
	
	/**
	 * 根据编单日期ID, 查询前台页面显示所需要的对象集合
	 * @param scheduleDate 编单日期ID
	 * @return 
	 */
	public List<?> queryByScheduleDate(String scheduleDate) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("scheduleDate", scheduleDate);
		return this.baseDAO.queryByNamedQuery("query.ProgListDetails.Views.by.scheduleDate", map);
	}
	
	/**
	 * 根据编单日期ID, 查询当天及往后日期的编单明细节目包ID
	 * @param scheduleDate 编单日期ID
	 * @return 返回节目包ID集合
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryProgPackageIdsByScheduleDate(String scheduleDate) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("scheduleDate", scheduleDate);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.progPackageIds.by.scheduleDate", map);
	}
	
	/**
	 * 根据编单日期, 查询当前已经编单的节目包ID集合
	 * @param scheduleDate 编单日期ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryProgPackageIdByScheduleDate(String scheduleDate) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("scheduleDate", scheduleDate);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.progPackageId.by.scheduleDate", map);
	}
	
	/**
	 * 根据编单日期和节目包ID, 查询节目包当天在哪些品牌下编单
	 * @param scheduleDate 编单日期
	 * @param progPackageId 节目包ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> querySiteCodeByScheduleDateAndPackageId(
			String scheduleDate, String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("scheduleDate", scheduleDate);
		map.put("progPackageId", progPackageId);
		return this.baseDAO.queryByNamedQuery(
				"query.CmsSite.siteCode.by.scheduleDate.progPackageId", map);
	}
	
	/**
	 * 根据编单日期和节目包ID, 查询当天节目包的编单细表
	 * @param scheduleDate 编单日期
	 * @param progPackageId 节目包ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListDetail> queryProgListDetailsByScheduleDateAndPackageId(
			String scheduleDate, String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("scheduleDate", scheduleDate);
		map.put("progPackageId", progPackageId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.by.scheduleDate.progPackageId", map);
	}
	
	/**
	 * 根据节目包ID, 查询该节目包编单日期集合
	 * @param progPackageId 节目包ID
	 * @return List<String> 编单日期ID集合
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryScheduleDateByProgPackageId(String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageId", progPackageId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.scheduleDate.by.progPackageId", map);
	}
	
	/**
	 * 根据上线日期区间, 编单细表中的节目包集合
	 * @param startTime 上线日期开始
	 * @param endTime 上线日期结束
	 * @return 编单细表节目包集合
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListDetail> queryByScheduleArea(String startTime, String endTime) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return this.baseDAO.queryByNamedQuery(
				"query.ProglistDetail.by.scheduleDate.Area", map);
	}
	
	/**
	 * 根据当前日期和节目包编号查询该节目包是否存在于当天及以后的编单中
	 * @param scheduleDate 当前日期编单ID
	 * @param progPackageId 节目包ID
	 * @return 返回指定节目包存在的大于等于当前日期的编单ID
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryScheduleDateByScheduleDateAndProgPackage(
			String scheduleDate, String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("scheduleDate", scheduleDate);
		map.put("progPackageId", progPackageId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgListDetail.scheduleDate.by.scheduleDate.progPackageId", map);
	}
	
	/**
	 * 根据编单日期, 统计各品牌播发量大小
	 * @param scheduleDate 编单日期ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> checkProgSize(String scheduleDate) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("scheduleDate", scheduleDate);
		return this.baseDAO.queryByNamedQuery(
				"query.Prog.size.scheduleDate", map);
	}
	
	/**
	 * 根据栏目ID查询到栏目未完成的编单日期和所处的流程
	 * @param columnId 栏目ID
	 * @return List<Object[]>
	 * objects[0]: 编单日期ID
	 * objects[1]: 流程活动名称
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryScheduleDateAndActionName(String columnId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("columnId", columnId);
		return this.baseDAO.queryByNamedQuery(
				"query.scheduleDate.action.by.PortalColumn.id", map);
	}
	
	// ---------------- 华丽的分割线 20100919 ------------------------------------------------------------------
	// 以下方法适用于cms1.2版
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
			)
	{
		List list = null;
		
		Map map = new HashMap(0);
		map.put("scheduledate", scheduledate);			// 
		map.put("columnclassid", columnclassid);				// 
		list = baseDAO.queryByNamedQuery("getProgListDetailsByScheduledateColumnclassid", map);
		
		return list;
	}
}
