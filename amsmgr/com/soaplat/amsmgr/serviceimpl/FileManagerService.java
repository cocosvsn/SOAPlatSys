/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.amsmgr.serviceimpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sbl.common.FlexProxy;
import com.soaplat.amsmgr.bean.AmsStorageHistory;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.bean.AmsStoragePrgRelBack;
import com.soaplat.amsmgr.common.GetAmsPkImpl;
import com.soaplat.amsmgr.manageriface.IAmsFileServerManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageHistoryManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelBackManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.amsmgr.serviceiface.IFileManager;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.soaplat.cmsmgr.util.DateUtil;
import com.soaplat.cmsmgr.util.SmbFileUtils;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

/**
 * Title 		: 文件删除业务类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		: 2011年8月17日 14时14分
 */
public class FileManagerService implements IFileManager {
	private static Logger logger = Logger.getLogger("Cms");
	private static Logger delFileLog = Logger.getLogger(FileManagerService.class);
	private IProgramInfoManager programInfoManager;
	private IProgPackageManager progPackageManager;
	private IProductInfoManager productInfoManager;
	private IAmsFileServerManager fileServerManager;
	private IAmsStoragePrgRelManager amsStoragePrgRelManager;
	private IAmsStorageHistoryManager amsStorageHistoryManager;
	private IAmsStoragePrgRelBackManager amsStoragePrgRelBackManager;
	
	public FileManagerService() {
		this.amsStorageHistoryManager = (IAmsStorageHistoryManager) ApplicationContextHolder.webApplicationContext.getBean("amsStorageHistoryManager");
		this.amsStoragePrgRelBackManager = (IAmsStoragePrgRelBackManager) ApplicationContextHolder.webApplicationContext.getBean("amsStoragePrgRelBackManager");
		this.amsStoragePrgRelManager = (IAmsStoragePrgRelManager) ApplicationContextHolder.webApplicationContext.getBean("amsstorageprgrelManager");
		this.progPackageManager = (IProgPackageManager) ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
		this.fileServerManager = (IAmsFileServerManager) ApplicationContextHolder.webApplicationContext.getBean("amsfileserverManager");
		this.productInfoManager = (IProductInfoManager) ApplicationContextHolder.webApplicationContext.getBean("productinfoManager");
		this.programInfoManager = (IProgramInfoManager) ApplicationContextHolder.webApplicationContext.getBean("programinfoManager");
	}
	
	public List<Object[]> getDBdata(String storageClassCode, 
			String startTime, String endTime, Long styleId, 
			String packageName, String siteCode) {
		delFileLog.info("用户[ " + FlexProxy.getOperator("user").getOperatorname() + " ]查询存储!");
		logger.debug(FileManagerService.class + "---->getDBdata");
		logger.debug("参数: 存储等级Code    storageClassCode ==> " + storageClassCode);
		logger.debug("参数: 上线日期起始            startTime ==> " + startTime);
		logger.debug("参数: 上线日期结束            endTime ==> " + endTime);
		logger.debug("参数: 样式ID          styleId ==> " + styleId);
		logger.debug("参数: 节目包名称                 packageName ==> " + packageName);
		logger.debug("参数: 品牌Code        siteCode ==> " + siteCode);
		
		if (null == styleId) {
			styleId = 0L;
		}
		
		if (null == packageName) {
			packageName = "";
		}
		
		packageName = "%" + packageName.trim() + "%";
		
		String startScheduleDate = -1 != startTime.indexOf("-") ? startTime.replaceAll("-", "") + "000000" : startTime;
		String endScheduleDate =  -1 != endTime.indexOf("-") ? endTime.replaceAll("-", "") + "000000" : endTime;
		String safeScheduleDate = DateUtil.getAfterScheduleDate(0 - Integer.valueOf(new CmsConfig().getPropertyByName("SafeAutoDelFileDays")));
		
		List<Object[]> list = this.fileServerManager.queryFilesByScheduleDateAndStorage(startScheduleDate, endScheduleDate, storageClassCode, safeScheduleDate, styleId, packageName, siteCode);
		Set<String> progPackageIdSet = new HashSet<String>();
		List<Object[]> results = new ArrayList<Object[]>();
		for (Object[] objects : list) {
			if (!progPackageIdSet.contains(objects[1])) {
				progPackageIdSet.add(String.valueOf(objects[1]));
				long packageSize = Long.valueOf(null == objects[7] ? "0" : String.valueOf(objects[7]));
				objects[7] = SmbFileUtils.byteCountToDisplaySize(packageSize);
				if (null == objects[10]) {
					objects[10] = "全";
				}
				results.add(objects);
			}
		}
		logger.debug("根据上线日期区间查询可删除的节目包数量为: [ " + list.size() + " ], 去除重复后剩: [ " + results.size() + " ]");
		return results;
	}
	
	/**
	 * 查询存储信息
	 * @return
	 */
	public List<Object[]> getStorage() {
		return this.fileServerManager.getStorages();
	}
	
	/**
	 * 删除文件
	 * @param progListIds 编单细表ID
	 * @param storageClassCode 存储等级Code
	 * @param inputManId 操作人员编号
	 * @return 删除成功返回删除磁盘空间大小, 失败返回null
	 */
	public Object deleteFiles(List<String> progListIds, String storageClassCode, String inputManId) {
		logger.debug(FileManagerService.class + "---->deleteFiles");
		logger.debug("参数: 编单细表ID    progListIds ==> " + progListIds.size() + "   " + ArrayUtils.toString(progListIds.toArray()));
		logger.debug("参数: 存储等级Code  storageClassCode ==> " + storageClassCode);
		logger.debug("参数: 操作人员编号      inputManId ==> " + inputManId);
		try {
			delFileLog.info("~~~~~~~~~用户[ " + FlexProxy.getOperator("user").getOperatorname() + " ]开始删除文件~~~~~~~~~~");
			Object result =  this.delete(progListIds, storageClassCode, inputManId);
			delFileLog.info("~~~~~~~~~用户[ " + FlexProxy.getOperator("user").getOperatorname() + " ]删除文件结束~~~~~~~~~~");
			return result;
		} catch (Exception e) {	
			logger.error("文件删除失败: " + e);
			return null;
		}
	} 
	
	private Object delete(List<String> progListIds, String storageClassCode, String inputManId) {
		String safeScheduleDate = DateUtil.getAfterScheduleDate(0 - Integer.valueOf(new CmsConfig().getPropertyByName("SafeAutoDelFileDays")));
		List<Object[]> list = this.fileServerManager.queryCanDelPackageFiles(progListIds, storageClassCode, safeScheduleDate);
		logger.debug("根据编单细表集合查询可删除的节目包文件数量为: " + list.size());
		
		long delResult = -1;
		long totalDeletFileSize = 0;
		for (Object[] objects : list) {
			if ("Clip".equals(objects[8])) {
				/**
				 * 如果该节目包的片花被其它节目包引用, 并且节目包状态为播发库, 则只修改该节目包状态, 不删文件和位置表
				 * 思路: 根据文件名和节目包ID, 去搜索节目包表和节目包文件表, 如果存在状态为一级库的节目包并且ID不为参数节目包ID
				 */
				if (this.fileServerManager.isContainerOfOther(String.valueOf(objects[13]))) {
					logger.debug("节目包[ " +  objects[13] + "]片花被其它节目包使用中, 不删除文件, 修改当前节目包附件状态");
					ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(String.valueOf(objects[13]));
					if (3 == progPackage.getState()) {
						progPackage.setState(1L);
						this.progPackageManager.update(progPackage);
					}
					continue;
				}
			}
			if ("Key".equals(objects[8]) && "Online".equals(storageClassCode)) {
				logger.debug("删除一级库记录时, 不删一级库Key文件! 位置表ID: " + objects[9]);
				continue;
			}
			
			String sambaFilePath = AmsStoragePrgRelService.getPath(
					objects[0].toString(), objects[1].toString(), 
					objects[2].toString(), objects[3].toString(), 
					objects[4].toString(), objects[5].toString(), 
					objects[6].toString(), objects[7].toString());
			
			if ("CaOnline".equals(storageClassCode)) {
				/**
				 * 修改产品信息表主文件状态 加扰库[9] -> 缓存库[0]
				 */
				TProductInfo productInfo = this.productInfoManager.getTProductInfoById(String.valueOf(objects[14]));
				if (9 == productInfo.getEncryptState()) {
					productInfo.setEncryptState(0L);
					this.productInfoManager.update(productInfo);
				}
			} else if ("NearOnline".equals(storageClassCode)
					&& "1".equals(String.valueOf(objects[17]))) {
				/**
				 * 修改节目包状态 缓存库[1] -> 未导入[-1]
				 */
				ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(String.valueOf(objects[13]));
				if (1 == progPackage.getState()) {
					progPackage.setState(-1L);
					this.progPackageManager.update(progPackage);
				}
				
				/**
				 * 修改节目状态已入缓存库[2] -> 未导入[-1]
				 */
				ProgramInfo programInfo = (ProgramInfo) this.programInfoManager.getById(String.valueOf(objects[18]));
				if (1 < programInfo.getDsflag()) {
					programInfo.setDsflag(-1L);
					this.productInfoManager.update(programInfo);
				}
			} else if ("Online".equals(storageClassCode)
					&& "1".equals(String.valueOf(objects[17]))) {
				/**
				 * 修改产品信息表主文件状态 加扰库[19] -> 缓存库[9]
				 */
				TProductInfo productInfo = this.productInfoManager.getTProductInfoById(String.valueOf(objects[14]));
				if (19 == productInfo.getEncryptState()) {
					productInfo.setEncryptState(9L);
					this.productInfoManager.update(productInfo);
				}
			} else if ("Online".equals(storageClassCode)) {
				/**
				 * 修改节目包附件状态 一级库[3] -> 缓存库[1]
				 */
				ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(String.valueOf(objects[13]));
				if (3 == progPackage.getState()) {
					progPackage.setState(1L);
					this.progPackageManager.update(progPackage);
				}
			} else {
				logger.debug("缓存库附属文件[ " + objects[5] + objects[6] + "/" + objects[7] + " ]不删除! 跳过!");
				continue;
			}
			SmbFile smbFile = null;
			try {
				smbFile = new SmbFile(sambaFilePath);
				if (smbFile.exists()) {
					totalDeletFileSize += SmbFileUtils.sizeOf(smbFile);
					smbFile.delete();
					logger.debug("成功删除文件: " + sambaFilePath);
					logger.debug("成功删除文件, 所属节目包[ " + String.valueOf(objects[13]) + " ], 产品[ " + String.valueOf(objects[14]) + " ], 节目[ " + String.valueOf(objects[18]) + " ]");
					delFileLog.info("成功删除文件, 所属节目包[ " + String.valueOf(objects[13]) + " ], 产品[ " + String.valueOf(objects[14]) + " ], 节目[ " + String.valueOf(objects[18]) + " ], 路径: \\\\" + sambaFilePath.substring(sambaFilePath.indexOf("@") + 1).replace("/", "\\"));
				} else {
					logger.debug("文件不存在: " + sambaFilePath);
					logger.debug("节目包[ " + String.valueOf(objects[13]) + " ], 产品[ " + String.valueOf(objects[14]) + " ], 节目[ " + String.valueOf(objects[18]) + " ]实体文件不存在~~~~");
					delFileLog.info("文件不存在, 所属节目包[ " + String.valueOf(objects[13]) + " ], 产品[ " + String.valueOf(objects[14]) + " ], 节目[ " + String.valueOf(objects[18]) + " ], 路径: \\\\" + sambaFilePath.substring(sambaFilePath.indexOf("@") + 1).replace("/", "\\"));
				}
				delResult = 0;  //删除成功标志
			} catch (SmbException e) {
				logger.warn("文件删除, 远程文件访问失败: " + e);
				logger.warn("延时5秒重试....");
				try {
					Thread.sleep(5000);
					smbFile.delete();
					logger.debug("成功删除文件: " + sambaFilePath);
					logger.debug("成功删除文件, 所属节目包[ " + String.valueOf(objects[13]) + " ], 产品[ " + String.valueOf(objects[14]) + " ], 节目[ " + String.valueOf(objects[18]) + " ]");
					delFileLog.info("成功删除文件, 所属节目包[ " + String.valueOf(objects[13]) + " ], 产品[ " + String.valueOf(objects[14]) + " ], 节目[ " + String.valueOf(objects[18]) + " ], 路径: \\\\" + sambaFilePath.substring(sambaFilePath.indexOf("@") + 1).replace("/", "\\"));
				} catch (InterruptedException e1) {
					logger.error(e1);
				} catch (SmbException e2) {
					logger.error("节目包[ " + String.valueOf(objects[13]) + " ], 产品[ " + String.valueOf(objects[14]) + " ], 节目[ " + String.valueOf(objects[18]) + " ], 文件[ " + sambaFilePath + " ] 删除失败!");
					logger.error(e2);
				}
			} catch (MalformedURLException e) {
				logger.error("节目包[ " + String.valueOf(objects[13]) + " ], 产品[ " + String.valueOf(objects[14]) + " ], 节目[ " + String.valueOf(objects[18]) + " ], 文件[ " + sambaFilePath + " ] 删除失败!");
				logger.error("文件删除, 远程路径解析失败: " + e);
			}
				
			this.addDelFileLog(String.valueOf(objects[7]), String.valueOf(objects[12]), 
					String.valueOf(objects[11]), String.valueOf(objects[10]), 
					String.valueOf(objects[15]), String.valueOf(objects[13]), 
					sambaFilePath, String.valueOf(objects[9]), 
					delResult, inputManId);
			
		}
		String totalDeletFileDisplaySize = SmbFileUtils.byteCountToDisplaySize(totalDeletFileSize);
		logger.debug("文件数: [ " + list.size() + " ] 空间大小为: [ " + totalDeletFileDisplaySize + " ]");
		delFileLog.info("此次操作共删除文件记录数: [ " + list.size() + " ], 释放空间大小为: [ " + totalDeletFileDisplaySize + " ]");
		return new Object[] {totalDeletFileDisplaySize};
	}
	
	private void addDelFileLog(String fileName, String storageId, String storageClassId, 
			String storageDirId, String progFileId, String progPackageId, 
			String filePath, String relId, long resultCode, String inputManId) {
		logger.debug(FileManagerService.class + "---->addDelFileLog");
		String currMaxID = null;
		try {
			currMaxID = this.amsStorageHistoryManager.getCurrMaxID();
		} catch (Exception e) {
			logger.error("获取当前最大主键ID值失败: " + e);
		}
		GetAmsPkImpl getAmsPkImpl = new GetAmsPkImpl();
//		logger.debug("历史记录表当前最大主键ID值: " + currMaxID);
		
		AmsStorageHistory amsStorageHistory = new AmsStorageHistory();
		amsStorageHistory.setId(getAmsPkImpl.GetTablePK("AMSSTORAGEHISTORY", currMaxID));
		amsStorageHistory.setFileName(fileName);
		amsStorageHistory.setInputManId(inputManId);
		amsStorageHistory.setInputTime(new Date());
		amsStorageHistory.setOptType(30l);
		amsStorageHistory.setSgId(storageId);
		amsStorageHistory.setScgId(storageClassId);
		amsStorageHistory.setSdgId(storageDirId);
		amsStorageHistory.setProgFileId(progFileId);
		amsStorageHistory.setProgId(progPackageId);
		amsStorageHistory.setOptObjectId(relId);
		amsStorageHistory.setOptResult(0l);
		amsStorageHistory.setRemark(filePath);
		
		AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) 
				this.amsStoragePrgRelManager.getById(relId);
		if (null != amsStoragePrgRel) {
			AmsStoragePrgRelBack amsStoragePrgRelBack = new AmsStoragePrgRelBack();
			amsStoragePrgRelBack.setFileName(amsStoragePrgRel.getFilename());
			amsStoragePrgRelBack.setFilePath(amsStoragePrgRel.getFilepath());
			amsStoragePrgRelBack.setFileTypeGlobalId(amsStoragePrgRel.getFtypeglobalid());
			amsStoragePrgRelBack.setFileUpdTime(amsStoragePrgRel.getFiledate());
			amsStoragePrgRelBack.setFileUploadTime(amsStoragePrgRel.getUploadtime());
			amsStoragePrgRelBack.setInputManId(amsStoragePrgRel.getInputmanid());
			amsStoragePrgRelBack.setInputTime(amsStoragePrgRel.getInputtime());
			amsStoragePrgRelBack.setPgId(amsStoragePrgRel.getPrglobalid());
			amsStoragePrgRelBack.setProgFileId(amsStoragePrgRel.getProgfileid());
			amsStoragePrgRelBack.setRemak(amsStoragePrgRel.getRemark());
			amsStoragePrgRelBack.setSdgId(amsStoragePrgRel.getStdirglobalid());
			amsStoragePrgRelBack.setSgId(amsStoragePrgRel.getStglobalid());
			amsStoragePrgRelBack.setRelId(amsStoragePrgRel.getRelid());
			this.amsStoragePrgRelBackManager.save(amsStoragePrgRelBack);
		}
		
		this.amsStorageHistoryManager.save(amsStorageHistory);
		amsStoragePrgRel.setIsDel("是");
		this.amsStoragePrgRelManager.update(amsStoragePrgRel);
	}
	
	
	public static void main(String[] args) throws IOException {
//		SELECT DISTINCT PK.PRODUCTID, PK.PRODUCTNAME, PK.STATE, PK.DEALSTATE, REL.FILENAME, REL.FILEPATH, S.STGLOBALID,
//		S.STORAGEACCSTYPE, S.STORAGEIP, S.LOGINNAME, S.LOGINPWD, S.MAPPATH, DIR.STDIRGLOBALID, DIR.STORAGEDIRNAME,
//		C.STCLASSGLOBALID, C.STCLASSNAME, REL.RELID, PD.PROGLISTDETAILID
//		FROM 
//			CMS.TPROGLISTDETAIL PD LEFT JOIN CMS.TCMSPROGPACKAGE PK ON PD.PRODUCTID = PK.PRODUCTID 
//			LEFT JOIN CMS.TSOAAMSSTORAGEPRGREL REL ON PK.PRODUCTID = REL.PRGLOBALID
//			LEFT JOIN CMS.TSOAAMSSTORAGEDIR DIR ON REL.STDIRGLOBALID = DIR.STDIRGLOBALID
//			LEFT JOIN CMS.TSOAAMSSTORAGE S ON REL.STGLOBALID = S.STGLOBALID
//			LEFT JOIN CMS.TSOAAMSSTORAGECLASS C ON C.STCLASSGLOBALID = S.STCLASSGLOBALID
//			LEFT JOIN CMS.TPRODUCTINFO PI ON PD.PRODUCTINFOID = PI.PRODUCTINFOID
//
//		WHERE PD.SCHEDULEDATE BETWEEN '20110823000000' AND '20110824000000' AND C.STCLASSCODE = 'Online'--'CaOnline' 
//		AND PK.PRODUCTID NOT IN
//		(
//			SELECT DISTINCT PD.PRODUCTID FROM CMS.TPROGLISTDETAIL PD WHERE PD.SCHEDULEDATE NOT BETWEEN '20110824000000' AND '20110824000000'
//		) ORDER BY PK.PRODUCTID
		
//		SELECT 
//		   DISTINCT PK.PRODUCTID AS 节目包编号, PK.PRODUCTNAME AS 节目包名称, PI.PRODUCTINFONAME AS 产品名称, C.STCLASSNAME AS 存储库, "TO_CHAR"(PK.SUBSCRIBERETIME, 'yyyy-MM-dd') AS 版权期终, "TO_CHAR"(PK.INPUTTIME, 'yyyy-MM-dd') AS 节目包录入时间, 
//		   "SUM"(PF.CONTENTFILESIZE) AS 节目包大小,
//		   (CASE WHEN PK.PRODUCTID IN (SELECT PLD.PRODUCTID FROM CMS.TPROGLISTDETAIL PLD WHERE PLD.SCHEDULEDATE > '20110825000000') THEN '否' ELSE '是' END) AS 是否可删除
//		FROM 
//		   CMS.TPROGLISTDETAIL PD INNER JOIN CMS.TCMSPROGPACKAGE PK ON PD.PRODUCTID = PK.PRODUCTID 
//		   INNER JOIN CMS.TSOAAMSSTORAGEPRGREL REL ON PK.PRODUCTID = REL.PRGLOBALID
//		   INNER JOIN CMS.TSOAAMSSTORAGEDIR DIR ON REL.STDIRGLOBALID = DIR.STDIRGLOBALID
//		   INNER JOIN CMS.TSOAAMSSTORAGE S ON REL.STGLOBALID = S.STGLOBALID
//		   INNER JOIN CMS.TSOAAMSSTORAGECLASS C ON C.STCLASSGLOBALID = S.STCLASSGLOBALID
//		   INNER JOIN CMS.TPRODUCTINFO PI ON PD.PRODUCTINFOID = PI.PRODUCTINFOID
//		   LEFT JOIN CMS.TCMSPROGRAMFILES PF ON PF.FILENAME = REL.FILENAME
//		WHERE 
//		   PD.SCHEDULEDATE BETWEEN '20110823000000' AND '20110824000000' AND C.STCLASSCODE = 'Online'--'CaOnline' 
//		GROUP BY 
//		   PK.PRODUCTID, PK.PRODUCTNAME, PK.STATE, PK.DEALSTATE, PI.PRODUCTINFONAME, C.STCLASSNAME, PK.SUBSCRIBERETIME, "TO_CHAR"(PK.INPUTTIME, 'yyyy-MM-dd')
//		ORDER BY PK.PRODUCTID
		
		ApplicationContext app = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-amsmgrdaobeans.xml",
				"applicationContext-cmsmgrdaobeans.xml",
				"applicationContext-common.xml",
				"applicationContext-contentmgrmanagerbeans.xml",
				"applicationContext-amsmgrmanager.xml",
				"applicationContext-cmsmgrmanager.xml"
		});
		IFileManager fileManager = (IFileManager) app.getBean("fileManagerService");
//		IAmsFileServerManager fileServerManager = (IAmsFileServerManager) app.getBean("amsfileserverManager");
//		String safeScheduleDate = DateUtil.getAfterScheduleDate(0 - Integer.valueOf(new CmsConfig().getPropertyByName("SafeAutoDelFileDays")));
//		System.out.println(safeScheduleDate);
		List<String> list = new ArrayList<String>(1);
		list.add("PD00005118");
		fileManager.deleteFiles(list, "Online", "");
		
		
		
//		List<Object[]> list = fileServerManager.queryFilesByScheduleDateAndStorage("20110701000000", "20110824000000", "Online", safeScheduleDate);
//		List<Object[]> list = fileServerManager.getStorages();
//		System.out.println(list.size());
//		Map<String, List<Object>> map = ListUtil.toMap(list.toArray(), 0);
//		System.out.println(map.size());
//		for (Object o : map.keySet()) {
//			System.out.println(o.toString() + " " + map.get(o));
//			
//		}
		
//		for (Object[] objects : list) {
//			System.out.println(objects[0] + " " + objects[1]);
//		}
	}
	
	public void setProgramInfoManager(IProgramInfoManager programInfoManager) {
		this.programInfoManager = programInfoManager;
	}
	
	public void setFileServerManager(IAmsFileServerManager fileServerManager) {
		this.fileServerManager = fileServerManager;
	}
	
	public void setProgPackageManager(IProgPackageManager progPackageManager) {
		this.progPackageManager = progPackageManager;
	}
	
	public void setProductInfoManager(IProductInfoManager productInfoManager) {
		this.productInfoManager = productInfoManager;
	}
	
	public void setAmsStoragePrgRelManager(
			IAmsStoragePrgRelManager amsStoragePrgRelManager) {
		this.amsStoragePrgRelManager = amsStoragePrgRelManager;
	}
	
	public void setAmsStorageHistoryManager(
			IAmsStorageHistoryManager amsStorageHistoryManager) {
		this.amsStorageHistoryManager = amsStorageHistoryManager;
	}
	
	public void setAmsStoragePrgRelBackManager(
			IAmsStoragePrgRelBackManager amsStoragePrgRelBackManager) {
		this.amsStoragePrgRelBackManager = amsStoragePrgRelBackManager;
	}
}
/**
 * 查一级库
 */
//SELECT 
//DISTINCT PD.PROGLISTDETAILID AS 编单号, PK.PRODUCTID AS 节目包编号, PK.PRODUCTNAME AS 节目包名称, PI.PRODUCTINFONAME AS 产品名称, C.STCLASSNAME AS 存储库, "TO_CHAR"(PK.SUBSCRIBERETIME, 'yyyy-MM-dd') AS 版权期终, "TO_CHAR"(PK.INPUTTIME, 'yyyy-MM-dd') AS 节目包录入时间, 
//"SUM"(PF.CONTENTFILESIZE) AS 节目包大小,
//(CASE WHEN PK.PRODUCTID IN (SELECT PLD.PRODUCTID FROM CMS.TPROGLISTDETAIL PLD WHERE PLD.SCHEDULEDATE > '20110824000000') THEN '否' ELSE '是' END) AS 是否可删除, C.STCLASSCODE
//FROM 
//CMS.TPROGLISTDETAIL PD INNER JOIN CMS.TCMSPROGPACKAGE PK ON PD.PRODUCTID = PK.PRODUCTID 
//INNER JOIN CMS.TSOAAMSSTORAGEPRGREL REL ON PK.PRODUCTID = REL.PRGLOBALID
//INNER JOIN CMS.TSOAAMSSTORAGEDIR DIR ON REL.STDIRGLOBALID = DIR.STDIRGLOBALID
//INNER JOIN CMS.TSOAAMSSTORAGE S ON REL.STGLOBALID = S.STGLOBALID
//INNER JOIN CMS.TSOAAMSSTORAGECLASS C ON C.STCLASSGLOBALID = S.STCLASSGLOBALID
//INNER JOIN CMS.TPRODUCTINFO PI ON PD.PRODUCTINFOID = PI.PRODUCTINFOID
//LEFT JOIN CMS.TCMSPROGRAMFILES PF ON PF.FILENAME = REL.FILENAME
//WHERE 
//PD.SCHEDULEDATE BETWEEN '20110522000000' AND '20110827000000' AND C.STCLASSCODE = 'Online' AND DIR.FILECODE <> 'KEY'
//GROUP BY 
//PD.PROGLISTDETAILID, PK.PRODUCTID, PK.PRODUCTNAME, PK.STATE, PK.DEALSTATE, PI.PRODUCTINFONAME, C.STCLASSNAME, PK.SUBSCRIBERETIME, "TO_CHAR"(PK.INPUTTIME, 'yyyy-MM-dd'), C.STCLASSCODE
//ORDER BY PK.PRODUCTID
/**
 * 查加扰库
 */
//SELECT 
//DISTINCT PD.PROGLISTDETAILID AS 编单号, PK.PRODUCTID AS 节目包编号, PK.PRODUCTNAME AS 节目包名称, PI.PRODUCTINFONAME AS 产品名称, C.STCLASSNAME AS 存储库, "TO_CHAR"(PK.SUBSCRIBERETIME, 'yyyy-MM-dd') AS 版权期终, "TO_CHAR"(PK.INPUTTIME, 'yyyy-MM-dd') AS 节目包录入时间, 
//"SUM"(PF.CONTENTFILESIZE) AS 节目包大小,
//(CASE WHEN PK.PRODUCTID IN (SELECT PLD.PRODUCTID FROM CMS.TPROGLISTDETAIL PLD WHERE PLD.SCHEDULEDATE > :safeScheduleDate) THEN '否' 
//WHEN 1 < (SELECT "COUNT"(*) FROM CMS.TSOAAMSSTORAGEPRGREL WHERE CMS.TSOAAMSSTORAGEPRGREL.PRGLOBALID = PK.PRODUCTID 
//AND CMS.TSOAAMSSTORAGEPRGREL.STGLOBALID IN ('20090903143323000954')) THEN '否' ELSE '是' END) AS 是否可删除, C.STCLASSCODE
//FROM 
//CMS.TPROGLISTDETAIL PD INNER JOIN CMS.TCMSPROGPACKAGE PK ON PD.PRODUCTID = PK.PRODUCTID 
//INNER JOIN CMS.TSOAAMSSTORAGEPRGREL REL ON PK.PRODUCTID = REL.PRGLOBALID
//INNER JOIN CMS.TSOAAMSSTORAGEDIR DIR ON REL.STDIRGLOBALID = DIR.STDIRGLOBALID
//INNER JOIN CMS.TSOAAMSSTORAGE S ON REL.STGLOBALID = S.STGLOBALID
//INNER JOIN CMS.TSOAAMSSTORAGECLASS C ON C.STCLASSGLOBALID = S.STCLASSGLOBALID
//INNER JOIN CMS.TPRODUCTINFO PI ON PD.PRODUCTINFOID = PI.PRODUCTINFOID
//LEFT JOIN CMS.TCMSPROGRAMFILES PF ON PF.FILENAME = REL.FILENAME
//WHERE 
//PD.SCHEDULEDATE BETWEEN :startScheduleDate AND :endScheduleDate AND C.STCLASSCODE = :storageClassCode
//GROUP BY 
//PD.PROGLISTDETAILID, PK.PRODUCTID, PK.PRODUCTNAME, PK.STATE, PK.DEALSTATE, PI.PRODUCTINFONAME, C.STCLASSNAME, PK.SUBSCRIBERETIME, "TO_CHAR"(PK.INPUTTIME, 'yyyy-MM-dd'), C.STCLASSCODE
//ORDER BY PK.PRODUCTID
/**
 * 查缓存库
 */
//SELECT 
//DISTINCT PD.PROGLISTDETAILID AS 编单号, PK.PRODUCTID AS 节目包编号, PK.PRODUCTNAME AS 节目包名称, PI.PRODUCTINFONAME AS 产品名称, C.STCLASSNAME AS 存储库, "TO_CHAR"(PK.SUBSCRIBERETIME, 'yyyy-MM-dd') AS 版权期终, "TO_CHAR"(PK.INPUTTIME, 'yyyy-MM-dd') AS 节目包录入时间, 
//"SUM"(PF.CONTENTFILESIZE) AS 节目包大小,
//(CASE WHEN PK.PRODUCTID IN (SELECT PLD.PRODUCTID FROM CMS.TPROGLISTDETAIL PLD WHERE PLD.SCHEDULEDATE > :safeScheduleDate) THEN '否' 
//WHEN 0 < (SELECT "COUNT"(*) FROM CMS.TSOAAMSSTORAGEPRGREL WHERE CMS.TSOAAMSSTORAGEPRGREL.PRGLOBALID = PK.PRODUCTID 
//AND CMS.TSOAAMSSTORAGEPRGREL.STGLOBALID IN ('20090903143323000954', '20090903143323000955')) THEN '否' ELSE '是' END) AS 是否可删除, C.STCLASSCODE
//FROM 
//CMS.TPROGLISTDETAIL PD INNER JOIN CMS.TCMSPROGPACKAGE PK ON PD.PRODUCTID = PK.PRODUCTID 
//INNER JOIN CMS.TSOAAMSSTORAGEPRGREL REL ON PK.PRODUCTID = REL.PRGLOBALID
//INNER JOIN CMS.TSOAAMSSTORAGEDIR DIR ON REL.STDIRGLOBALID = DIR.STDIRGLOBALID
//INNER JOIN CMS.TSOAAMSSTORAGE S ON REL.STGLOBALID = S.STGLOBALID
//INNER JOIN CMS.TSOAAMSSTORAGECLASS C ON C.STCLASSGLOBALID = S.STCLASSGLOBALID
//INNER JOIN CMS.TPRODUCTINFO PI ON PD.PRODUCTINFOID = PI.PRODUCTINFOID
//LEFT JOIN CMS.TCMSPROGRAMFILES PF ON PF.FILENAME = REL.FILENAME
//WHERE 
//PD.SCHEDULEDATE BETWEEN :startScheduleDate AND :endScheduleDate AND C.STCLASSCODE = :storageClassCode
//GROUP BY 
//PD.PROGLISTDETAILID, PK.PRODUCTID, PK.PRODUCTNAME, PK.STATE, PK.DEALSTATE, PI.PRODUCTINFONAME, C.STCLASSNAME, PK.SUBSCRIBERETIME, "TO_CHAR"(PK.INPUTTIME, 'yyyy-MM-dd'), C.STCLASSCODE
//ORDER BY PK.PRODUCTID
