/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.amsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.soaplat.amsmgr.bean.AmsFileServer;
import com.soaplat.amsmgr.manageriface.IAmsFileServerManager;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;


/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-11 下午07:07:56
 */
public class AmsFileServerManagerImpl implements IAmsFileServerManager {
	private static Logger cmsLogger = Logger.getLogger("Cms");
	private IBaseDAO baseDAO;
	private IGetPK getamspk;
	
	/**
	 * 删除AmsFileServer对象, 多个对象删除
	 * @param object AmsFileServer对象数组
	 */
	public void delete(Object[] object) {
		if(object.length > 0){
			for(int i = 0; i < object.length; i ++){
				baseDAO.delete(object[i]);
			}
		}	
	}

	/**
	 * 根据AmsFileServer对象主键ID删除, 单个对象删除
	 * @param pkid AmsFileServer主键ID
	 */
	public void deleteById(String pkid) {
		this.baseDAO.delete(this.baseDAO.getById(AmsFileServer.class, pkid));
	}

	/**
	 * 查询所有AmsFileServer对象
	 * @return 返回AmsFileServer对象集合
	 */
	public List<?> findAll() {
		return this.baseDAO.findAll("AmsFileServer","filesgloabalid");
	}

	/**
	 * 根据AmsFileServer对象属性名和属性值, 查询符合条件的对象
	 * @param propertyName AmsFileServer 对象属性名
	 * @param value AmsFileServer 对象属性值
	 * @return 返回查询条件为propertyName = value 的AmsFileServer对象集合
	 */
	public List<?> findByProperty(String propertyName, Object value) {
		return this.baseDAO.findByProperty("AmsFileServer", propertyName, value);
	}

	/**
	 * 根据AmsFileServer对象主键ID查询
	 * @param pkid AmsFileServer主键ID
	 * @return 返回AmsFileServer对象
	 */
	public Object getById(String pkid) {
		AmsFileServer fileserver = (AmsFileServer) this.baseDAO.getById(AmsFileServer.class, pkid);
		return fileserver;
	}

	/**
	 * 根据AmsFileServer对象主键ID查询, 支持延时加载
	 * @param pkid AmsFileServer主键ID
	 * @return 返回AmsFileServer对象
	 */
	public Object loadById(String pkid) {
		AmsFileServer fileserver = (AmsFileServer) this.baseDAO.loadById(AmsFileServer.class, pkid);
		return fileserver;
	}

	/**
	 * 保存AmsFileServer对象, 单个对象保存
	 * @param object 待持久化的AmsFileServer对象
	 * @return 返回持久化后的AmsFileServer对象
	 */
	public Object save(Object object) {
		AmsFileServer fileserver = (AmsFileServer) object;
		fileserver.setInputtime(new Date());
		String strMaxPK = this.baseDAO.getMaxPropertyValue("AmsFileServer", "filesgloabalid");
		strMaxPK = this. getamspk.GetTablePK("AmsFileServer",strMaxPK);
		fileserver.setFilesgloabalid(strMaxPK);
		this.baseDAO.save(fileserver);
		return this.baseDAO.getById(AmsFileServer.class, strMaxPK);

	}

	/**
	 * 修改AmsFileServer对象属性, 单个对象修改
	 * @param object 属性修改后的AmsFileServer对象
	 */
	public void update(Object object) {
		this.baseDAO.update(object);
	}

	/**
	 * 保存AmsFileServer对象, 多个对象保存
	 * @param object 待持久化的AmsFileServer对象数组
	 */
	public void save(Object[] object) {
		if (object.length > 0){
			for(int i = 0; i < object.length; i ++){
				this.save(object[i]);
			}
		}
	}

	/**
	 * 修改AmsFileServer对象属性, 多个对象修改
	 * @param object 属性修改后的AmsFileServer对象数组
	 */
	public void update(Object[] object) {
		if (object.length > 0){
			for(int i = 0; i < object.length; i ++){
				this.update(object[i]);
			}
		}
	}

	/**
	 * 把已经设置某些属性的对象作为模板，查询跟这个对象已有属性值相同的列表
	 * @param exampleentity 模板对象
	 * @return 属性相同的对象集合
	 */
	public List<?> findbyExample(Object exampleentity) {
		return this.baseDAO.findbyExample(exampleentity);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<Object[]> queryFilesByScheduleDateAndStorage(String startScheduleDate, 
			String endScheduleDate, String storageClassCode, String safeScheduleDate, 
			Long styleId, String packageName, String siteCode) {
		Map<String, Object> params = new HashMap<String, Object>(4);
		params.put("startScheduleDate", startScheduleDate);
		params.put("endScheduleDate", endScheduleDate);
		params.put("storageClassCode", storageClassCode);
		params.put("safeScheduleDate", safeScheduleDate);
		
		String sql = "";
		if ("Online".equals(storageClassCode)) {
//			hql = "query.file.by.scheduleDate.storageClassCode.Online";
			sql = "SELECT " +
					"DISTINCT PD.PROGLISTDETAILID AS 编单号, PK.PRODUCTID AS 节目包编号, PK.PRODUCTNAME AS 节目包名称, PI.PRODUCTINFONAME AS 产品名称, " +
					"C.STCLASSNAME AS 存储库, \"TO_CHAR\"(PK.SUBSCRIBERETIME, 'yyyy-MM-dd') AS 版权期终, \"TO_CHAR\"(PK.INPUTTIME, 'yyyy-MM-dd') AS 节目包录入时间, " +
					"\"SUM\"(PF.CONTENTFILESIZE) AS 节目包大小, " +
					"(CASE WHEN PK.PRODUCTID IN (SELECT PLD.PRODUCTID FROM CMSADMIN.TPROGLISTDETAIL PLD WHERE PLD.SCHEDULEDATE > :safeScheduleDate) THEN '否' ELSE '是' END) AS 是否可删除, C.STCLASSCODE, ST.SITENAME AS 品牌名称, PS.STYLENAME AS 样式名称, " +
					"\"SUM\"(PF.CONTENTFILESIZE) AS 节目包大小 " +
					"FROM " +
					"CMSADMIN.TPROGLISTDETAIL PD INNER JOIN CMSADMIN.TCMSPROGPACKAGE PK ON PD.PRODUCTID = PK.PRODUCTID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGEPRGREL REL ON PK.PRODUCTID = REL.PRGLOBALID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGEDIR DIR ON REL.STDIRGLOBALID = DIR.STDIRGLOBALID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGE S ON REL.STGLOBALID = S.STGLOBALID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGECLASS C ON C.STCLASSGLOBALID = S.STCLASSGLOBALID " +
					"INNER JOIN CMSADMIN.TPRODUCTINFO PI ON PD.PRODUCTINFOID = PI.PRODUCTINFOID " +
					"INNER JOIN CMSADMIN.TCMSPACKSTYLE PS ON PK.STYLEID = PS.STYLEID " +
					"LEFT JOIN CMSADMIN.TCMSSITE ST ON PK.SITECODE = ST.SITECODE " +
					"LEFT JOIN CMSADMIN.TCMSPROGRAMFILES PF ON PF.FILENAME = REL.FILENAME " +
					"WHERE " +
					"PD.SCHEDULEDATE BETWEEN :startScheduleDate AND :endScheduleDate AND C.STCLASSCODE = :storageClassCode AND DIR.FILECODE NOT IN ('KEY','Clip') AND REL.ISDEL IS NULL ";
			if (0 < styleId) {
				sql += "AND PK.STYLEID = :styleId ";
				params.put("styleId", styleId);
			}
			if (!"%%".equals(packageName)) {
				sql += "AND PK.PRODUCTNAME like :packageName ";
				params.put("packageName", packageName);
			}
			if (null != siteCode 
					&& 0 < siteCode.trim().length()) {
				if ("全".equals(siteCode)) {
					sql += "AND PK.SITECODE IS NULL ";
				} else {
					sql += "AND PK.SITECODE = :siteCode ";
					params.put("siteCode", siteCode);
				}
			}
			sql += "GROUP BY " +
					"PD.PROGLISTDETAILID, PK.PRODUCTID, PK.PRODUCTNAME, PK.STATE, PK.DEALSTATE, PI.PRODUCTINFONAME, C.STCLASSNAME, PK.SUBSCRIBERETIME, \"TO_CHAR\"(PK.INPUTTIME, 'yyyy-MM-dd'), ST.SITENAME, PS.STYLENAME, C.STCLASSCODE " +
					"ORDER BY PK.PRODUCTID";
		} else if ("CaOnline".equals(storageClassCode)) {
//			hql = "query.file.by.scheduleDate.storageClassCode.CaOnline";
			sql = "SELECT " +
					"DISTINCT PD.PROGLISTDETAILID AS 编单号, PK.PRODUCTID AS 节目包编号, PK.PRODUCTNAME AS 节目包名称, PI.PRODUCTINFONAME AS 产品名称, " +
					"C.STCLASSNAME AS 存储库, \"TO_CHAR\"(PK.SUBSCRIBERETIME, 'yyyy-MM-dd') AS 版权期终, \"TO_CHAR\"(PK.INPUTTIME, 'yyyy-MM-dd') AS 节目包录入时间, " +
					"\"SUM\"(PF.CONTENTFILESIZE) AS 节目包大小, " +
					"(CASE WHEN PK.PRODUCTID IN (SELECT PLD.PRODUCTID FROM CMSADMIN.TPROGLISTDETAIL PLD WHERE PLD.SCHEDULEDATE > :safeScheduleDate) THEN '否' " +
					"WHEN 1 < (SELECT \"COUNT\"(*) FROM SOAPLATDB.TSOAAMSSTORAGEPRGREL INNER JOIN SOAPLATDB.TSOAAMSSTORAGEDIR " +
					"ON SOAPLATDB.TSOAAMSSTORAGEPRGREL.STDIRGLOBALID = SOAPLATDB.TSOAAMSSTORAGEDIR.STDIRGLOBALID WHERE SOAPLATDB.TSOAAMSSTORAGEPRGREL.PRGLOBALID = PK.PRODUCTID " +
					"AND SOAPLATDB.TSOAAMSSTORAGEPRGREL.STGLOBALID IN ('20090903143323000954') AND SOAPLATDB.TSOAAMSSTORAGEDIR.FILECODE NOT IN ('Clip', 'KEY') AND SOAPLATDB.TSOAAMSSTORAGEPRGREL.ISDEL IS NULL) THEN '否' ELSE '是' END) AS 是否可删除, C.STCLASSCODE, ST.SITENAME AS 品牌名称, PS.STYLENAME AS 样式名称 , " +
					"\"SUM\"(PF.CONTENTFILESIZE) AS 节目包大小 " +
					"FROM " +
					"CMSADMIN.TPROGLISTDETAIL PD INNER JOIN CMSADMIN.TCMSPROGPACKAGE PK ON PD.PRODUCTID = PK.PRODUCTID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGEPRGREL REL ON PK.PRODUCTID = REL.PRGLOBALID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGEDIR DIR ON REL.STDIRGLOBALID = DIR.STDIRGLOBALID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGE S ON REL.STGLOBALID = S.STGLOBALID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGECLASS C ON C.STCLASSGLOBALID = S.STCLASSGLOBALID " +
					"INNER JOIN CMSADMIN.TPRODUCTINFO PI ON PD.PRODUCTINFOID = PI.PRODUCTINFOID " +
					"INNER JOIN CMSADMIN.TCMSPACKSTYLE PS ON PK.STYLEID = PS.STYLEID " +
					"LEFT JOIN CMSADMIN.TCMSSITE ST ON PK.SITECODE = ST.SITECODE " +
					"LEFT JOIN CMSADMIN.TCMSPROGRAMFILES PF ON PF.FILENAME = REL.FILENAME " +
					"WHERE " +
					"PD.SCHEDULEDATE BETWEEN :startScheduleDate AND :endScheduleDate AND C.STCLASSCODE = :storageClassCode AND REL.ISDEL IS NULL ";
			if (0 < styleId) {
				sql += "AND PK.STYLEID = :styleId ";
				params.put("styleId", styleId);
			}
			if (!"%%".equals(packageName)) {
				sql += "AND PK.PRODUCTNAME like :packageName ";
				params.put("packageName", packageName);
			}
			if (null != siteCode 
					&& 0 < siteCode.trim().length()) {
				if ("全".equals(siteCode)) {
					sql += "AND PK.SITECODE IS NULL ";
				} else {
					sql += "AND PK.SITECODE = :siteCode ";
					params.put("siteCode", siteCode);
				}
			}
			sql += "GROUP BY " +
					"PD.PROGLISTDETAILID, PK.PRODUCTID, PK.PRODUCTNAME, PK.STATE, PK.DEALSTATE, PI.PRODUCTINFONAME, C.STCLASSNAME, PK.SUBSCRIBERETIME, \"TO_CHAR\"(PK.INPUTTIME, 'yyyy-MM-dd'), ST.SITENAME, PS.STYLENAME, C.STCLASSCODE " +
					"ORDER BY PK.PRODUCTID";
		} else {
//			hql = "query.file.by.scheduleDate.storageClassCode.NearOnline";
			sql = "SELECT " +
					"DISTINCT CMSADMIN.TPROGLISTDETAIL.PROGLISTDETAILID AS 编单号, " +
					"CMSADMIN.TCMSPROGPACKAGE.PRODUCTID AS 节目包编号, " +
					"CMSADMIN.TCMSPROGPACKAGE.PRODUCTNAME AS 节目包名称, " +
					"CMSADMIN.TPRODUCTINFO.PRODUCTINFONAME AS 产品名称, " +
					"SOAPLATDB.TSOAAMSSTORAGECLASS.STCLASSNAME AS 存储库, " +
					"\"TO_CHAR\"(CMSADMIN.TCMSPROGPACKAGE.SUBSCRIBERETIME, 'yyyy-MM-dd') AS 版权期终, " +
					"\"TO_CHAR\"(CMSADMIN.TCMSPROGPACKAGE.INPUTTIME, 'yyyy-MM-dd') AS 节目包录入时间, " +
					"CMSADMIN.TCMSPROGRAMFILES.CONTENTFILESIZE AS 节目包大小, " +
					"(CASE WHEN CMSADMIN.TCMSPROGPACKAGE.PRODUCTID IN (SELECT PLD.PRODUCTID FROM CMSADMIN.TPROGLISTDETAIL PLD WHERE PLD.SCHEDULEDATE > :safeScheduleDate) THEN '否' " +
					"WHEN 0 < (SELECT \"COUNT\"(*) FROM SOAPLATDB.TSOAAMSSTORAGEPRGREL INNER JOIN SOAPLATDB.TSOAAMSSTORAGEDIR " +
					"ON SOAPLATDB.TSOAAMSSTORAGEPRGREL.STDIRGLOBALID = SOAPLATDB.TSOAAMSSTORAGEDIR.STDIRGLOBALID WHERE SOAPLATDB.TSOAAMSSTORAGEPRGREL.PRGLOBALID = CMSADMIN.TCMSPROGPACKAGE.PRODUCTID " +
					"AND SOAPLATDB.TSOAAMSSTORAGEPRGREL.STGLOBALID IN ('20090903143323000954', '20090903143323000955') AND SOAPLATDB.TSOAAMSSTORAGEDIR.FILECODE <> 'Clip' AND SOAPLATDB.TSOAAMSSTORAGEPRGREL.ISDEL IS NULL) THEN '否' ELSE '是' END) AS 是否可删除, " +
					"SOAPLATDB.TSOAAMSSTORAGECLASS.STCLASSCODE, CMSADMIN.TCMSSITE.SITENAME AS 品牌名称, CMSADMIN.TCMSPACKSTYLE.STYLENAME AS 样式名称, " +
					"CMSADMIN.TCMSPROGRAMFILES.CONTENTFILESIZE AS 节目包大小 " +
					"FROM " +
					"CMSADMIN.TCMSPACKAGEFILES INNER JOIN CMSADMIN.TPROGLISTDETAIL ON CMSADMIN.TPROGLISTDETAIL.PRODUCTID = CMSADMIN.TCMSPACKAGEFILES.PRODUCTID " +
					"INNER JOIN CMSADMIN.TPRODUCTINFO ON CMSADMIN.TPRODUCTINFO.PRODUCTINFOID = CMSADMIN.TPROGLISTDETAIL.PRODUCTINFOID " +
					"INNER JOIN CMSADMIN.TCMSPROGRAMFILES ON CMSADMIN.TCMSPACKAGEFILES.PROGFILEID = CMSADMIN.TCMSPROGRAMFILES.PROGFILEID " +
					"INNER JOIN CMSADMIN.TCMSPROGPACKAGE ON CMSADMIN.TCMSPROGPACKAGE.PRODUCTID = CMSADMIN.TPROGLISTDETAIL.PRODUCTID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGEPRGREL ON CMSADMIN.TCMSPACKAGEFILES.PROGRAMID = SOAPLATDB.TSOAAMSSTORAGEPRGREL.PRGLOBALID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGEDIR ON SOAPLATDB.TSOAAMSSTORAGEPRGREL.STDIRGLOBALID = SOAPLATDB.TSOAAMSSTORAGEDIR.STDIRGLOBALID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGE ON SOAPLATDB.TSOAAMSSTORAGEPRGREL.STGLOBALID = SOAPLATDB.TSOAAMSSTORAGE.STGLOBALID " +
					"INNER JOIN SOAPLATDB.TSOAAMSSTORAGECLASS ON SOAPLATDB.TSOAAMSSTORAGE.STCLASSGLOBALID = SOAPLATDB.TSOAAMSSTORAGECLASS.STCLASSGLOBALID " +
					"INNER JOIN CMSADMIN.TCMSPACKSTYLE ON CMSADMIN.TCMSPROGPACKAGE.STYLEID = CMSADMIN.TCMSPACKSTYLE.STYLEID " +
					"LEFT JOIN CMSADMIN.TCMSSITE ON CMSADMIN.TCMSPROGPACKAGE.SITECODE = CMSADMIN.TCMSSITE.SITECODE " +
					"WHERE " +
					"SOAPLATDB.TSOAAMSSTORAGECLASS.STCLASSCODE = :storageClassCode AND " +
					"CMSADMIN.TPROGLISTDETAIL.SCHEDULEDATE BETWEEN :startScheduleDate AND :endScheduleDate AND " +
					"CMSADMIN.TCMSPROGPACKAGE.PRODUCTID NOT IN (SELECT PLD.PRODUCTID FROM CMSADMIN.TPROGLISTDETAIL PLD WHERE PLD.SCHEDULEDATE > :safeScheduleDate) AND " +
					"CMSADMIN.TCMSPROGRAMFILES.PROGRANK = 1 AND " +
					"CMSADMIN.TCMSPACKAGEFILES.PROGRAMID IS NOT NULL AND SOAPLATDB.TSOAAMSSTORAGEPRGREL.ISDEL IS NULL ";
			if (0 < styleId) {
				sql += "AND CMSADMIN.TCMSPROGPACKAGE.STYLEID = :styleId ";
				params.put("styleId", styleId);
			}
			if (!"%%".equals(packageName)) {
				sql += "AND CMSADMIN.TCMSPROGPACKAGE.PRODUCTNAME like :packageName ";
				params.put("packageName", packageName);
			}
			if (null != siteCode 
					&& 0 < siteCode.trim().length()) {
				if ("全".equals(siteCode)) {
					sql += "AND CMSADMIN.TCMSPROGPACKAGE.SITECODE IS NULL ";
				} else {
					sql += "AND CMSADMIN.TCMSPROGPACKAGE.SITECODE = :siteCode ";
					params.put("siteCode", siteCode);
				}
			}
		}
		List<Object[]> list = null;
		try {
			list = (List<Object[]>) this.baseDAO.queryBySQL(sql, params);
		} catch (Exception e) {
			cmsLogger.error(e);
			this.baseDAO.executeSQL("UPDATE CMSADMIN.TCMSPROGRAMFILES SET CONTENTFILESIZE = 0 WHERE CONTENTFILESIZE = 'NaN'", null);
			list = (List<Object[]>) this.baseDAO.queryBySQL(sql, params);
		}
		return list;
	}
	
	/**
	 * 查询所有存储
	 * @return 返回存储体信息集合 List<Object[]>
	 * objects[0]: AmsStorageClass.stclassname
	 * objects[1]: AmsStorageClass.stclasscode
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getStorages() {
		return (List<Object[]>) this.baseDAO.queryByNamedQuery("query.Storages");
	}
	
	/**
	 * 根据编单细表ID集合, 存储等级Code, 安全编单日期ID, 查询可以删除的节目包文件信息
	 * @param progListIds 编单细表ID集合
	 * @param storageClassCode 存储体等级Code
	 * @param safeScheduleDate 安全编单日期
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryCanDelPackageFiles(List<String> progListIds,
			String storageClassCode, String safeScheduleDate) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("progListIds", progListIds);
		params.put("storageClassCode", storageClassCode);
		params.put("safeScheduleDate", safeScheduleDate);
		String hql = "";
		if ("Online".equals(storageClassCode)) {
			hql = "query.FileInfo.by.progListIds.storageClassCode.safeScheduleDate.Online";
		} else if ("CaOnline".equals(storageClassCode)) {
			hql = "query.FileInfo.by.progListIds.storageClassCode.safeScheduleDate.CaOnline";
		} else {
			hql = "query.FileInfo.by.progListIds.storageClassCode.safeScheduleDate.NearOnline";
		}
		List<Object[]> list = (List<Object[]>) this.baseDAO.queryNamed(hql, params);
		return list;
	} 
	
	/**
	 * 查询片花是否被其它节目包包含量
	 * @param progPackageId 节目包ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isContainerOfOther(String progPackageId) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("progPackageId", progPackageId);
		List<Object> list = (List<Object>) this.baseDAO.queryNamed("query.Clip.container.of.others.count", params);
		if (0 < list.size()) {
			if (0 < Long.valueOf(list.get(0).toString())) {
				return true;
			}
		}
		return false;
	}

	/** ---------------- setter and getter ----------------- **/
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetamspk(IGetPK getamspk) {
		this.getamspk = getamspk;
	}
}
