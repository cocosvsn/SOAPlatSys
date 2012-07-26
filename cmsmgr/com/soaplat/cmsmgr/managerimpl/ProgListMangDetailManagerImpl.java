package com.soaplat.cmsmgr.managerimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.ProgListMangDetail;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IProgListMangDetailManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ProgListMangDetailManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgListMangDetailManagerImpl implements IProgListMangDetailManager {
	
	/** The ProgListMangDetaildao. */
	IBaseDAO baseDAO;
	
	/** The getcmspk. */
	IGetPK getcmspk;
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		//��ɾ��
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

		ProgListMangDetail progListMangDetail=(ProgListMangDetail)baseDAO.getById(ProgListMangDetail.class, pkid);
		if(progListMangDetail!=null){
			baseDAO.delete(progListMangDetail);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List progListMangDetaillist=baseDAO.findAll("ProgListMangDetail","progListMangDetailid");
		return progListMangDetaillist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List progListMangDetaillist=baseDAO.findByProperty("ProgListMangDetail", propertyName, value);
		return progListMangDetaillist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgListMangDetail progListMangDetail=(ProgListMangDetail)baseDAO.getById(ProgListMangDetail.class, pkid);
		return progListMangDetail;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgListMangDetail progListMangDetail=(ProgListMangDetail)baseDAO.loadById(ProgListMangDetail.class, pkid);
		return progListMangDetail;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		ProgListMangDetail progListMangDetail = new ProgListMangDetail();
		progListMangDetail = (ProgListMangDetail)object;

		String strCurMaxPK = baseDAO.getMaxPropertyValue("ProgListMangDetail", "mangdetailid");
		String strMaxPK = getcmspk.GetTablePK("ProgListMangDetail", strCurMaxPK);
		progListMangDetail.setMangdetailid(strMaxPK);
		
		baseDAO.save(progListMangDetail);
		return baseDAO.getById(ProgListMangDetail.class, strMaxPK);
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

	// 根据scheduleDate、defcatseq查询，得到progListMangDetails
	public List getProgListMangDetailsByScheduleDateAndDefcatseq(
			String scheduledate, 
			String defcatseq
			)
	{
		// 返回：List<ProgListMangDetail>
		defcatseq = defcatseq + "%";
		
		Map map = new HashMap();
		map.put("scheduledate", scheduledate);
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery("select_ProgListMangDetailsByScheduleDateAndDefcatseq", map);
		return list;
	}
	
	// 20100121 14:09
	// 根据scheduleDate、defcatseq查询，得到本地progListMangDetails
	public List getLocalProgListMangDetailsByScheduleDateAndDefcatseq(
			String scheduledate, 
			String defcatseq
			)
	{
		// 返回：List<ProgListMangDetail>
		if(defcatseq == null)
			defcatseq = "";
		defcatseq = defcatseq + "%";
		
		Map map = new HashMap();
		map.put("scheduledate", scheduledate);
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery("select_LocalProgListMangDetailsByScheduleDateAndDefcatseq", map);
		return list;
	}
	
	// 20091229 13:26
	// 根据scheduleDate和Columnclassid，查询得到progListMangDetail
	public List getProgListMangDetailsByScheduleDateAndColumnclassid(String scheduledate, String columnclassid)
	{
		Map map = new HashMap();
		map.put("scheduledate", scheduledate);
		map.put("columnclassid", columnclassid);
		List list = baseDAO.queryByNamedQuery("select_ProgListMangDetailsByScheduleDateAndColumnclassid", map);
		return list;
	}
	
	// 20100108 10:24
	// 根据栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
	public List getProgListMangDetailsByScheduledate(
			String scheduledate							// 栏目单总表的主键
			)
	{
		// 返回：List<Object[]>
		// 			(ProgListMangDetail)Object[0]
		//			(PortalColumn)Object[1]
		//			(FlowAction)Object[2]
		
		Map map = new HashMap();
		map.put("scheduledate", scheduledate);
		List list = baseDAO.queryByNamedQuery("select_ProgListMangDetailsByScheduledate", map);
		return list;
	}
	
	// 这就是传说中的方法15
	// 20100121 13:46
	// 根据本地栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
	public List getLocalProgListMangDetailsByScheduledate(
			String scheduledate							// 栏目单总表的主键
			)
	{
		// 返回：List<Object[]>
		// 			(ProgListMangDetail)Object[0]
		//			(PortalColumn)Object[1]
		//			(FlowAction)Object[2]
		
		Map map = new HashMap();
		map.put("scheduledate", scheduledate);
		List list = baseDAO.queryByNamedQuery("select_LocalProgListMangDetailsByScheduledate", map);
		return list;
	}
	
	/**
	 * 根据编单日期集合查询所有栏目编单集合
	 * @param scheduleDateStr 编单日期集合, 格式: 20100909000000
	 * @return 返回所有栏目编单集合
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListMangDetail> queryDetailsByScheduleDate(
			List<String> scheduleDateStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scheduleDateStr", scheduleDateStr);
		return (List<ProgListMangDetail>) baseDAO.queryNamed(
				"query.ProgListMangDetail.by.scheduleDate", map);
	}
	
	/**
	 * 根据编单日期, 修改编单总表和分表的流程活动
	 * @param scheduleDate 编单日期ID, 格式: 20100909000000
	 * @param nextAction 将要发送到的下一个活动ID
	 */
	public int updateAction(String scheduleDate, String nextAction) {
		String updateProgListMangDetailSQL = "update CMSADMIN.TPROGLISTMANGDETAIL set ID_ACT = :nextAction where SCHEDULEDATE = :scheduleDate";
		String updateProgListMangSQL = "update CMSADMIN.TPROGLISTMANG set ID_ACT = :nextAction where scheduledate = :scheduleDate";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleDate", scheduleDate);
		params.put("nextAction", nextAction);
		int updateCount = this.baseDAO.executeSQL(updateProgListMangSQL, params);
		return this.baseDAO.executeSQL(updateProgListMangDetailSQL, params) + updateCount;
	}
	
	/**
	 * 根据栏目Code查询该栏目是否已被初始化编单
	 * @param columnCode 栏目Code
	 * @return 
	 */
	public boolean existProgListDetail(String columnCode) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("columnCode", columnCode);
		List<?> list = this.baseDAO.queryNamed(
				"query.count.of.prog.list.detail.by.columnCode", params);
		if (0 < list.size()) {
			return true;
		}
		return false;
	}
}
