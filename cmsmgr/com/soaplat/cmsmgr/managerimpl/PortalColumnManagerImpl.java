package com.soaplat.cmsmgr.managerimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;

/**
 * Title :the Class PortalColumnManagerImpl. Description : Copyright :copyright
 * (c) 2009 Company :SMET Create Date :2009-06-16
 * 
 * @author :SOAPlat Group (Fanyanhua)
 * @version :1.0
 */
public class PortalColumnManagerImpl implements IPortalColumnManager {

	/** The PortalColumndao. */
	IBaseDAO baseDAO;

	/** The getcmspk. */
	IGetPK getcmspk;

	// public IProgPackageManager progPackageManager = null;
	// public ProgPackageManager progPackageManager

	public PortalColumnManagerImpl() {
		// progPackageManager =
		// (IProgPackageManager)ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		// ��ɾ��
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

		PortalColumn PortalColumn = (PortalColumn) baseDAO.getById(
				PortalColumn.class, pkid);
		if (PortalColumn != null) {
			baseDAO.delete(PortalColumn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List portalColumnlist = baseDAO.findAll("PortalColumn", "displayorder");
		return portalColumnlist;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang
	 * .String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List findByProperty(String propertyName, Object value) {
		List portalColumnlist = baseDAO.findByProperty("PortalColumn",
				propertyName, value);
		return portalColumnlist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		PortalColumn portalColumn = (PortalColumn) baseDAO.getById(
				PortalColumn.class, pkid);
		return portalColumn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		PortalColumn portalColumn = (PortalColumn) baseDAO.loadById(
				PortalColumn.class, pkid);
		return portalColumn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		PortalColumn portalColumn = new PortalColumn();
		portalColumn = (PortalColumn) object;

		String strCurMaxPK = baseDAO.getMaxPropertyValue("PortalColumn",
				"columnclassid");
		String strMaxPK = getcmspk.GetTablePK("PortalColumn", strCurMaxPK);
		portalColumn.setColumnclassid(strMaxPK);
		baseDAO.save(portalColumn);

		return baseDAO.getById(PortalColumn.class, strMaxPK);
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

	/**
	 * Sets the baseDAO.
	 * 
	 * @param baseDAO
	 *            the new baseDAO
	 */

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

	public List getPortalColumnsByDefcatseq(String defcatseq) {
		defcatseq = defcatseq + "%";
		Map map = new HashMap();
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery(
				"select_PortalColumnsByDefcatseq", map);
		return list;
	}

	// 根据序列查询所有栏目叶子节点
	public List getLeafPortalColumnsByDefcatseq(String defcatseq) {
		defcatseq = defcatseq + "%";
		Map map = new HashMap();
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery(
				"select_LeafPortalColumnsByDefcatseq", map);
		return list;
	}

	// 20100114 16:52
	// 根据序列查询所有栏目叶子节点(有效的)
	public List getValidLeafPortalColumnsByDefcatseq(String defcatseq) {
		defcatseq = defcatseq + "%";
		Map map = new HashMap();
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery(
				"select_ValidLeafPortalColumnsByDefcatseq", map);
		return list;
	}

	// 20100114 16:10
	// 根据栏目code序列，查询，得到所有有效的需要生成发布文件的栏目
	public List getAllPublishPortalColumnsByDefcatseq(String defcatseq) {
		defcatseq = defcatseq + "%";
		Map map = new HashMap();
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery(
				"select_AllPublishPortalColumnsByDefcatseq", map);
		return list;
	}

	/**
	 * 查询报纸的栏目
	 * @return List<PortalColumn>
	 */
	@SuppressWarnings("unchecked")
	public List<PortalColumn> getPaperPortalColumn() {
		return this.baseDAO.queryByNamedQuery(
						"query.PortalColumn.by.portalColumnName");
	}

	/**
	 * 根据编单日期ID, 查询该日期下所有栏目
	 * @param scheduleDate 编单日期ID, 格式: 20100909000000
	 * @return List<PortalColumn>
	 */
	@SuppressWarnings("unchecked")
	public List<PortalColumn> queryPortalColumnsByScheduleDate(String scheduleDate) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("scheduleDate", scheduleDate);
		return this.baseDAO.queryByNamedQuery(
				"query.PortalColumn.by.scheduleDate", params);
	}
	
	/**
	 * 根据编单日期ID, 品牌Code, 查询该日期下所有栏目
	 * @param scheduleDate 编单日期ID, 格式: 20100909000000
	 * @param siteCode 品牌Code
	 * @return List<PortalColumn>
	 */
	@SuppressWarnings("unchecked")
	public List<PortalColumn> queryPortalColumnsByScheduleDate(String scheduleDate, String siteCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("scheduleDate", scheduleDate);
//		params.put("siteCode", siteCode);
		/**
		 *  该死的需求, 现要求合并品牌预告JS. 
		 *  实现思路, 以前分品牌生成预告, 改为一个品牌查询出所有栏目, 将所有栏目存放到一个品牌中生成预告JS
		 *  HuangBo update by 2012年07月23日 13时58分
		 */
		return this.baseDAO.queryByNamedQuery(
				"query.PortalColumn.by.scheduleDate.-siteCode", params);
//				"query.PortalColumn.by.scheduleDate.siteCode", params);
	}
	
	/**
	 * 根据编单细表的节目包ID查询该节目包曾经编单过的所有栏目
	 * @param progPackageId 节目包ID
	 * @return 返回节目包编单的所有栏目集合
	 */
	@SuppressWarnings("unchecked")
	public List<PortalColumn> queryPortalColumns(String progPackageId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("progPackageId", progPackageId);
		return this.baseDAO.queryByNamedQuery(
				"query.PortalColumn.by.progPackageId", params);
	}
	
	/**
	 * 查询终端使用的有效栏目, 用于生成栏目JS
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PortalColumn> queryPortalColumnsByValid() {
		return this.baseDAO.queryByNamedQuery("query.PortalColumn.by.validFlag");
	}
	
	/**
	 * 根据栏目序列,查询该栏目的所有子节点
	 * @param seq 栏目序列
	 * @return 返回子栏目集合
	 */
	@SuppressWarnings("unchecked")
	public List<PortalColumn> queryChilds(String seq) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		return this.baseDAO.queryByNamedQuery("query.Child.PortalColumn.by.seq", map);
	}
	
	/**
	 * 查询停用的父栏目
	 * @param parentCodes 父栏目Code集合
	 * @param validFlag 启用之前的栏目状态(停用: 2)
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<PortalColumn> queryDiffParents(List<String> parentCodes, Long validFlag) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("columnCode", parentCodes);
		map.put("validFlag", validFlag);
		return (List<PortalColumn>) this.baseDAO.queryNamed(
				"query.PortalColumn.by.columnCode.validFlag", map);
	} 
	
	/**
	 * 删除栏目时判断该栏目下是否存在有效子栏目
	 * @param columnCode 栏目Code
	 * @return true:存在有效子栏目, false:不存在有效子栏目
	 */
	@SuppressWarnings("unchecked")
	public boolean isExistValidChild(String columnCode) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("columnCode", "columnCode");
		List<BigDecimal> list = (List<BigDecimal>) this.baseDAO.queryNamed(
				"query.count.of.child.PortalColumn", params);
		
		if (0 < list.size()) {
			if(0 < list.get(0).longValue())
				return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<PortalColumn> queryPortalColumnsByRolesAndValid(
			String scheduleDate, List<String> roles, String currAction) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("roles", roles);
		params.put("scheduleDate", scheduleDate);
		List<String> defcatseq = (List<String>) this.baseDAO.queryBySQL(
				"SELECT  p.DEFCATSEQ FROM CMSADMIN.TPORTALCOLUMN p " +
				"RIGHT JOIN CMSADMIN.TPROGLISTMANGDETAIL d " +
				"ON p.COLUMNCLASSID = d.COLUMNCLASSID " +
				"WHERE d.SCHEDULEDATE = :scheduleDate", params);
		Set<String> columnCodes = new HashSet<String>();
		for (String string : defcatseq) {
			columnCodes.addAll(Arrays.asList(string.split("\\\\")));
		}
		params.put("currAction", currAction);
		params.put("columnCodes", new ArrayList<String>(columnCodes));
		List<Object[]> list = (List<Object[]>) this.baseDAO.queryBySQL(
				"SELECT P.COLUMNCLASSID, P.COLUMNNAME, P.DEFCATCODE, P.SITECODE, " +
				"P.ISLEAF, P.DEFCATLEVEL, P.ROOTID, P.PARENTSID, P.DISPLAYORDER, " +
				"P.DEFCATSEQ, P.SCHEDULE_TAG, P.VALID_FLAG, P.REMARK, P.ISPUBLISH, " +
				"P.UPDATEDATE, P.ARCHIVEDAYS, P.INPUTMANID, P.INPUTTIME, " +
				"P.FOCUS_IMG_NAME, P.BLUR_IMG_NAME, CASE (SELECT ID_ACT FROM " +
				"CMSADMIN.TPROGLISTMANGDETAIL D WHERE D.COLUMNCLASSID = P.COLUMNCLASSID " +
				"AND D.SCHEDULEDATE = :scheduleDate) WHEN :currAction THEN 1 ELSE 0 END " +
				"FROM CMSADMIN.TPORTALCOLUMN P WHERE P.DEFCATCODE IN " +
				"(:columnCodes) ORDER BY P.SITECODE, P.COUNTNUMB", params);
		List<PortalColumn> portalColumns = new ArrayList<PortalColumn>();
		for (Object[] objects : list) {
//			PortalColumn portalColumn = new PortalColumn(
//					(String)objects[0],
//					(String)objects[1],
//					(String)objects[2],
//					(String)objects[3],
//					(String)objects[4],
//					(Long)objects[5],
//					(String)objects[6],
//					(String)objects[7],
//					(Long)objects[8],
//					(String)objects[9],
//					(String)objects[10],
//					(Long)objects[11],
//					(String)objects[12],
//					(Long)objects[13],
//					(Date)objects[14],
//					(Long)objects[15],
//					(String)objects[16],
//					(Date)objects[17],
//					(String)objects[18],
//					(String)objects[19],
//					(Long)objects[20]);
			PortalColumn portalColumn = new PortalColumn(
					(String)objects[0],
					(String)objects[1],
					(String)objects[2],
					(String)objects[3],
					(String)objects[4],
					Long.valueOf(objects[5].toString()),
					(String)objects[6],
					(String)objects[7],
					Long.valueOf(objects[8].toString()),
					(String)objects[9],
					(String)objects[10],
					Long.valueOf(objects[11].toString()),
					(String)objects[12],
					Long.valueOf(objects[13].toString()),
					(Date)objects[14],
					Long.valueOf(objects[15].toString()),
					(String)objects[16],
					(Date)objects[17],
					(String)objects[18],
					(String)objects[19],
					Long.valueOf(objects[20].toString()));
			portalColumns.add(portalColumn);
		}
		return portalColumns;
	}
	
	// ---------------- 华丽的分割线 20100921 ------------------------------------------------------------------
	// 以下方法适用于cms1.2版
	/**
	 * 根据品牌code、validtag、叶子节点，查询栏目
	 * @param siteCode 品牌code
	 * @param validFlag 有效标识
	 * @param isleaf 叶子节点标识
	 * @since 2010-09-21
	 * @调用hql 
	 */
	public List getPortalColumnsBySitecodeValidtagIsleaf(
			String siteCode,
			Long validFlag,
			String isleaf
			)
	{
		List list = null;
		
		Map map = new HashMap(0);
		map.put("siteCode", siteCode);			// 
		map.put("validFlag", validFlag);				// 
		map.put("isleaf", isleaf);
		list = baseDAO.queryByNamedQuery("getPortalColumnsBySitecodeValidtagIsleaf", map);
		
		return list;
	}

	/**
	 * 根据品牌Code查询该品牌下所有栏目
	 * @param siteCode 品牌Code
	 * @return 返回栏目列表
	 */
	@SuppressWarnings("unchecked")
	public List<PortalColumn> getPortalColumnsBySiteCode(String siteCode) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("siteCode", siteCode);
		return this.baseDAO.queryByNamedQuery("query.PortalColumn.by.siteCode", map);
	}
	
	/**
	 * 刷新Root栏目的修改时间
	 * @return
	 */
	public int updateRootTime() {
//		<!-- 刷新ROOT栏目的修改时间 -->
//		<sql-query name="refresh.root.PortalColumn.update.time">
//			<![CDATA[
//			UPDATE CMSADMIN.TPORTALCOLUMN SET UPDATEDATE= :currDate WHERE DEFCATCODE='ROOT'
//			]]>
//		</sql-query>
		String sql = "UPDATE CMSADMIN.TPORTALCOLUMN SET UPDATEDATE= :currDate WHERE DEFCATCODE='ROOT'";
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("currDate", new Date());
		return this.baseDAO.executeSQL(sql, params);
	} 
	
	/**
	 * 查询指定栏目Code是否已经使用
	 * @param columnCode 栏目Code
	 * @return 返回栏目Code是否存在
	 */
	public boolean isExistsColumnCode(String columnCode) {
		return this.baseDAO.isExist("PortalColumn", "defcatcode", columnCode);
	}
}
