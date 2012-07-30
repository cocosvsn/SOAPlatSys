 package com.soaplat.cmsmgr.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sbl.cms.patch.JSPatch;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageClass;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.cmsmgr.bean.FlowAction;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.PortalJsRules;
import com.soaplat.cmsmgr.bean.PortalMod;
import com.soaplat.cmsmgr.bean.PortalPackage;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.bean.ProgListMangDetail;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgProduct;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.PtpPgpRel;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.dto.ProgListState;
import com.soaplat.cmsmgr.manageriface.IBpmcManager;
import com.soaplat.cmsmgr.manageriface.ICmsSiteManager;
import com.soaplat.cmsmgr.manageriface.ICmsTransactionManager;
import com.soaplat.cmsmgr.manageriface.IFileStyleManager;
import com.soaplat.cmsmgr.manageriface.IFlowActionManager;
import com.soaplat.cmsmgr.manageriface.IFlowActivityOrderManager;
import com.soaplat.cmsmgr.manageriface.IMigrationModuleManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IPortalJsRulesManager;
import com.soaplat.cmsmgr.manageriface.IPortalModManager;
import com.soaplat.cmsmgr.manageriface.IPortalPackageManager;
import com.soaplat.cmsmgr.manageriface.IPricingManager;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;
import com.soaplat.cmsmgr.manageriface.IProductManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListFileManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.cmsmgr.manageriface.IPtpPgpRelManager;
import com.soaplat.cmsmgr.util.DateUtil;
import com.soaplat.cmsmgr.util.ListUtil;
import com.soaplat.cmsmgr.util.SmbFileUtils;
import com.soaplat.cmsmgr.util.Xml2Json;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.common.IGetPK;
import flex.messaging.io.ArrayCollection;

public class PortalServiceImpl implements PortalServiceIface {
	private final String PRICINGACTION			= "FU00000107";		// 定价活动编号
	private final String PROGCHECK				= "FU00000078";		// 编单审校活动编号
	private final String MIGRATIONACTION		= "FU00000085";		// 迁移活动编号
	private final String ENCRYPTPRODUCTACTION	= "FU00000081";		// 产品加密活动编号
	private final String GENERATEJS				= "FU00000082";		// 生成节目预告JS和节目包JS
	private final String FINISHGENERATEJS		= "FU00000083";		// 生成JS完成
	private final String FINISHBROADCAST		= "FU00000087";
	private final String ONLINESTORAGEID = "20090903143323000954";
	private static final String ATTRIBUTE = "\"{0}\":\"{1}\"";
    private static final String ATTRIBUTEOBJECT = "\"{0}\":'{'{1}'}'";
    private static final String ARRAYOBJECT = "\"{0}\":[{1}]";
    private static final String STRING = "\"{0}\"";
	
	private static FileOperationImpl fileopr = null;
	private IGetPK getPK;
	private IPortalColumnManager portalColumnManager = null;
	private IPortalModManager portalModManager = null;
	private IPackageFilesManager packageFilesManager = null;
	private IProgListDetailManager progListDetailManager = null;
	private IProgPackageManager progPackageManager = null;
	private IProgListFileManager progListFileManager = null;
	private IAmsStoragePrgRelManager amsstorageprgrelManager = null;
	private IProgListMangDetailManager progListMangDetailManager = null;
	private IProgListMangManager progListMangManager = null;
	private IPortalJsRulesManager portalJsRulesManager = null;
	private IPortalPackageManager portalPackageManager = null;
	private IPtpPgpRelManager ptpPgpRelManager = null;
	private IProgramFilesManager programFilesManager = null;
	private ICmsSiteManager cmsSiteManager = null;
	private IBpmcManager bpmcManager;
	private IFileStyleManager fileStyleManager;
	private IPricingManager pricingManager;
	private IProductInfoManager productInfoManager;
	private IFlowActivityOrderManager flowActivityOrderManager;
	private IMigrationModuleManager migrationModuleManager;
	private IFlowActionManager flowActionManager;
	private ICmsTransactionManager cmsTransactionManager = null;
	private static final Logger cmsLog = Logger.getLogger("Cms");
	private static List dealedPortalPackages = null;
	private static List dealedProgPackages = null;
	private IProductManager productManager = null;
	
	public PortalServiceImpl() {
		dealedPortalPackages = null;
		dealedProgPackages = null;
		fileopr = new FileOperationImpl();
		this.getPK = (IGetPK) ApplicationContextHolder.webApplicationContext.getBean("getcmspk");
		portalColumnManager = (IPortalColumnManager)ApplicationContextHolder.webApplicationContext.getBean("portalColumnManager");
		portalModManager = (IPortalModManager)ApplicationContextHolder.webApplicationContext.getBean("portalModManager");
		packageFilesManager = (IPackageFilesManager)ApplicationContextHolder.webApplicationContext.getBean("packageFilesManager");
		progListDetailManager = (IProgListDetailManager)ApplicationContextHolder.webApplicationContext.getBean("progListDetailManager");
		progListFileManager = (IProgListFileManager)ApplicationContextHolder.webApplicationContext.getBean("progListFileManager");
		amsstorageprgrelManager = (IAmsStoragePrgRelManager)ApplicationContextHolder.webApplicationContext.getBean("amsstorageprgrelManager");
		progListMangDetailManager = (IProgListMangDetailManager)ApplicationContextHolder.webApplicationContext.getBean("progListMangDetailManager");
		progListMangManager = (IProgListMangManager)ApplicationContextHolder.webApplicationContext.getBean("progListMangManager");
		portalJsRulesManager = (IPortalJsRulesManager)ApplicationContextHolder.webApplicationContext.getBean("portalJsRulesManager");
		portalPackageManager = (IPortalPackageManager)ApplicationContextHolder.webApplicationContext.getBean("portalPackageManager");
		ptpPgpRelManager = (IPtpPgpRelManager)ApplicationContextHolder.webApplicationContext.getBean("ptpPgpRelManager");
		progPackageManager = (IProgPackageManager)ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
		bpmcManager = (IBpmcManager) ApplicationContextHolder.webApplicationContext.getBean("bpmcManager");
		cmsTransactionManager = (ICmsTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("cmsTransactionManager");
		programFilesManager = (IProgramFilesManager) ApplicationContextHolder.webApplicationContext.getBean("programfilesManager");
		cmsSiteManager = (ICmsSiteManager) ApplicationContextHolder.webApplicationContext.getBean("cmsSiteManager");
		this.fileStyleManager = (IFileStyleManager) ApplicationContextHolder.webApplicationContext.getBean("fileStyleManager");
		this.pricingManager = (IPricingManager) ApplicationContextHolder.webApplicationContext.getBean("pricingManager");
		this.productInfoManager = (IProductInfoManager) ApplicationContextHolder.webApplicationContext.getBean("productinfoManager");
		this.flowActionManager = (IFlowActionManager) ApplicationContextHolder.webApplicationContext.getBean("flowActionManager");
		this.flowActivityOrderManager = (IFlowActivityOrderManager) ApplicationContextHolder.webApplicationContext.getBean("flowActivityOrderManager");
		this.migrationModuleManager = (IMigrationModuleManager) ApplicationContextHolder.webApplicationContext.getBean("migrationModuleManager");
		this.productManager = (IProductManager) ApplicationContextHolder.webApplicationContext.getBean("cmsProductManager");
	}
	
	// 20100119 17:11
	// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
	private String convertDateToScheduleDate(String date)
	{
		String[] strl = date.split("-");
		String newDate = "";
		for(int i = 0; i < strl.length; i++)
		{
			if(strl[i] != null && strl[i] != "")
			{
				newDate += strl[i];
			}
		}
		return newDate + "000000";
	}
	
	// 20100119 16:42
	// 1 - 判断模板是否已经获取，目前不使用
	private String checkPortalModInProgListFile(
			String date,						// 栏目单日期，格式：yyyy-MM-dd
			String defcatseq,					// 栏目code序列
			String operatorId					// 操作人员id
			)
	{
		cmsLog.info("Cms -> PortalServiceImpl -> checkPortalModInProgListFile...");
		
		// 返回：
		// String - Portal模板的目标路径，不包含Portal目录的名字
		
		// 流程：
		// 1 - 判断模板是否已经获取
		// 1.1 - 如果没有，
		// 1.1.1 - 取固定模板
		// 1.1.2 - 写入proglistfile、amsstprg
		// 1.1.3 - 获取Portal模板的目标路径
		// 1.2 - 如果已经获取，继续
		// 1.2.1 - 获取Portal模板的目标路径
		// 2 - 返回Portal模板的目标路径
		
		String pathModelDest = "";	// Portal模板的目标路径：存储IP + 存储目录 + 编单日期ID(主键)
		
		// 1 - 判断模板是否已经获取
		// 判断栏目的合法性
		List pcs = portalColumnManager.findByProperty("defcatseq", defcatseq);
		if(pcs == null || pcs.size() <= 0)
		{
			cmsLog.info("栏目不存在，返回失败。");
			return "";
		}
		// 根据date、栏目，查询proglistfile，得到proglistfile
		// List<Object[]>
		//		(ProgListFile)Object[0]
		//		(PortalColumn)Object[1]
		List plfs = progListFileManager.getProgListFilesByDateFiletypeDefcatseq(
				date, 
				Long.valueOf(2), 
				defcatseq
				);
		if(plfs == null || plfs.size() <= 0)
		{
			// 1.1 - 如果没有，
			// 1.1.1 - 取固定模板
			// 1.1.3 - 获取Portal模板的目标路径
			cmsLog.info("模板没有到位，准备获取模板zip文件...");
			pathModelDest = getPortalModelZip(defcatseq, date, operatorId);
			cmsLog.info("模板已经到位，模板 - " + pathModelDest);
		}
		else
		{
			cmsLog.info("模板已经到位，准备获取模板位置...");
			
			Object[] rows = (Object[])plfs.get(0);
			ProgListFile plf = (ProgListFile)rows[0];
			
			// 1.2 - 如果已经获取，继续
			// 1.2.1 - 获取Portal模板的目标路径
			// 配置文件，获取以下数据
			String stclasscodeModelDest = "CaOnline";			// 模板目标存储等级预览区
//			String filecodeModel = "MODZIP";					// 模板文件CODE
//			String filepath = date;								// 文件的filepath
			
//			List pathModelDests = getDestPath(filecodeModel, stclasscodeModelDest, filepath);
//			if(pathModelDests != null && pathModelDests.size() >= 4)
//			{
//				pathModelDest = (String)pathModelDests.get(0);
//			}
			
			// 查询Portal的源路径（zip解压缩后的路径）
			// 根据portalzip的文件id和portal所在存储体等级code，查询，得到portalzip的源路径
			// 返回：List
			// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
			// 2 - List<Object[]>
			// 		(ProgramFiles)Object[0]
			// 		(AmsStorage)Object[1]
			// 		(AmsStoragePrgRel)Object[2]
			// 		(AmsStorageDir)Object[3]
			// 		(AmsStorageClass)Object[4]
			List sourcepaths = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
					plf.getColumnfileid(), 
					stclasscodeModelDest, 
					""
					);
			if(sourcepaths != null && sourcepaths.size() >= 2)
			{
				pathModelDest = (String)sourcepaths.get(0);
				pathModelDest = pathModelDest.substring(0, pathModelDest.lastIndexOf('/'));
				pathModelDest = fileopr.checkPathFormatRear(pathModelDest, '/');
			}
			else
			{
				cmsLog.info("查询模板位置失败。");
				pathModelDest = "";
			}
			cmsLog.info("模板 - " + pathModelDest);
		}
		
		// 2 - 返回
		cmsLog.info("Cms -> PortalServiceImpl -> checkPortalModInProgListFile returns.");
		return pathModelDest;
	}

	// 20100119 22:24，目前不使用
	// 根据，查询，得到目标路径
	private List getDestPath(
			String filecode,			// 目标文件filecode
			String stclasscode,			// 目标目录存储体等级code
			String filepath				// 文件的filepath
			)
	{
		// 返回：List
		// 1 - String 目标路径，以'/'结尾，不包含文件名，格式："smb://hc:hc@172.23.19.66/公用/"
		// 2 - AmsStorage
		// 3 - AmsStorageDir
		// 4 - AmsStorageClass
		
		cmsLog.info("Cms -> PortalServiceImpl -> getDestPath ...");
		List retlist = new ArrayList();
		
		// (配置文件中读取参数：模板目标存储等级、模板文件CODE("MODZIP")，调用获取文件存放位置接口得到值。)
		// 路径：存储IP + 存储目录 + 编单日期ID(主键)
		String pathModelDest = "";	//getModelDestPathByFilecodeStclasscode(filecodeModel, stclasscodeModelDest);
		// 返回：List
		// 1 - String 目标路径()  格式："smb://hc:hc@172.23.19.66/公用/"
		// 2 - List<Object[]>
		//			(AmsStorage)Object[0]
		//			(AmsStorageDir)Object[1]
		//			(AmsStorageClass)Object[2]
		List destPaths = packageFilesManager.getDestPathByFilecodeStclasscode(
				filecode, 
				stclasscode
				);
		if(destPaths != null && destPaths.size() > 0)
		{
			pathModelDest = (String)destPaths.get(0);
			List rowlist = (List)destPaths.get(1);
			Object[] rows = (Object[])rowlist.get(0);
			AmsStorage amsst = (AmsStorage)rows[0];
			AmsStorageDir amsstdir = (AmsStorageDir)rows[1];
			AmsStorageClass amsstclass = (AmsStorageClass)rows[2];
			
//			stglobalid = amsst.getStglobalid();						// 模板文件zip的目标存储体id
//			stdirglobalid = amsstdir.getStdirglobalid();			// 模板文件zip的目标存储体目录id

			// 需要判断路径格式，必须以"/"结尾
			pathModelDest = fileopr.checkPathFormatRear(pathModelDest, '/');
			if(filepath != null)
			{
				pathModelDest += filepath;
				pathModelDest = fileopr.checkPathFormatRear(pathModelDest, '/');
			}
			
			retlist.add(pathModelDest);
			retlist.add(amsst);
			retlist.add(amsstdir);
			retlist.add(amsstclass);
		}
		else
		{
			// 返回失败
			cmsLog.info("目标路径为空。");
//			return "";
		}
		cmsLog.info("Cms -> PortalServiceImpl -> getDestPath returns.");
		return retlist;
	}
	
	// 20100114 10:50，目前不使用
	// 取固定模板，返回目录存放的目录，格式：路径：存储IP + 存储目录 + 编单日期ID(主键)"smb://hc:hc@172.23.19.66/公用/2010-01-19"
	private String getPortalModelZip(
			String defcatseq,					// 栏目代码序列
			String date,						// 栏目单日期
			String operatorId					// 操作人员id
			)
	{
		// 流程：
		// 1 - 取栏目表中栏目树节点的封面模板ID(COVERMODEID)，从模板表中取“是否压缩文件标志”如果是“是”，则2，如果不是则报错。
		// 2 - 根据模板ID从文件存放位置表取文件存放位置。调用已有的方法，参数是模板ID，模板源存储等级，配置文件取。
		// 3 - 调用已有的方法获取解包存放位置。(配置文件中读取参数：模板目标存储等级、模板文件CODE，MODZIP，调用获取文件存放位置接口得到值。)
		// 4 - 解包，取编单ID为文件夹名，将解包中的内容放入其中。
		
		cmsLog.info("Cms -> PortalServiceImpl -> getPortalModelZip...");
//		String ret = 0;
		
		// 配置文件参数，读取配置文件
		String stclasscodeModelSource = "NearOnline";		// 模板源存储等级
		String stclasscodeModelDest = "CaOnline";			// 模板目标存储等级预览区
		String filecodeModel = "MODZIP";					// 模板文件CODE
		String filetypePortalZip = "PTLZIP";				// portalzip文件的type
		
		String filepath = date + "/";						// 模板文件zip的filepath
		String filename = "";								// 模板文件zip的filename
		String stglobalid = "";								// 模板文件zip的目标存储体id
		String stdirglobalid = "";							// 模板文件zip的目标存储体目录id
//		String ftypeglobalid = "";							// 模板文件zip的文件类型
		
		// 1 - 取栏目表中栏目树节点的封面模板ID(COVERMODEID)，从模板表中取“是否压缩文件标志”如果是“是”，则2，如果不是则报错。
		List portalColumns = portalColumnManager.findByProperty("defcatseq", defcatseq);
		if(portalColumns.size() <= 0)
		{
			// 返回失败
			cmsLog.info("没有需要生成portal的栏目，返回失败。");
			return "";
		}
		PortalColumn portalColumn = (PortalColumn)portalColumns.get(0);
		String portalModId = portalColumn.getCovermodeid();
		PortalMod portalMod = (PortalMod)portalModManager.getById(portalModId);
		if(portalMod == null || portalMod.getIsused() == null || portalMod.getIsused() != 1)
		{
			// 返回失败
			cmsLog.info("PortalMod为空，返回失败。");
			return "";
		}
		
		// 2 - 根据模板ID从文件存放位置表取文件存放位置（存储体IP + 存储体目录 + filepath + filename）。
		// 参数是模板ID(文件ID)，模板源存储等级，配置文件取。
		String fullPathModelSource = "";
		// 返回：List
		// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
		// 2 - List<Object[]>
		// 		(AmsStorage)Object[0]
		// 		(AmsStoragePrgRel)Object[1]
		// 		(AmsStorageDir)Object[2]
		// 		(AmsStorageClass)Object[3]
//		List sourcePaths = packageFilesManager.getSourcePathByProgfileidStclasscode(portalMod.getModeid(), stclasscodeModelSource);
		List sourcePaths = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
				portalMod.getModeid(), 
				stclasscodeModelSource,
				null
				);
		if(sourcePaths != null && sourcePaths.size() > 0)
		{
			fullPathModelSource = (String)sourcePaths.get(0);		// zip模板文件源路径
			List rowlist = (List)sourcePaths.get(1);
			Object[] rows = (Object[])rowlist.get(0);
			AmsStoragePrgRel aspr = (AmsStoragePrgRel)rows[1];
			filename = aspr.getFilename();
//			ftypeglobalid = aspr.getFtypeglobalid();				// 模板文件zip的文件类型
		}
		else
		{
			// 返回失败
			cmsLog.info("PortalMod的源路径为空，返回失败。");
			return "";
		}
		
		// 3 - 获取解包存放位置(目标路径)。
		// (配置文件中读取参数：模板目标存储等级、模板文件CODE("MODZIP")，调用获取文件存放位置接口得到值。)
		// 路径：存储IP + 存储目录 + 编单日期ID(主键)
		String pathModelDest = "";	//getModelDestPathByFilecodeStclasscode(filecodeModel, stclasscodeModelDest);
		// 返回：List
		// 1 - String 目标路径()  格式："smb://hc:hc@172.23.19.66/公用/"
		// 2 - List<Object[]>
		//			(AmsStorage)Object[0]
		//			(AmsStorageDir)Object[1]
		//			(AmsStorageClass)Object[2]
		List destPaths = packageFilesManager.getDestPathByFilecodeStclasscode(
				filetypePortalZip, 
				stclasscodeModelDest
				);
		if(destPaths != null && destPaths.size() > 0)
		{
			pathModelDest = (String)destPaths.get(0);
			List rowlist = (List)destPaths.get(1);
			Object[] rows = (Object[])rowlist.get(0);
			AmsStorage amsst = (AmsStorage)rows[0];
			AmsStorageDir amsstdir = (AmsStorageDir)rows[1];
			
			stglobalid = amsst.getStglobalid();						// 模板文件zip的目标存储体id
			stdirglobalid = amsstdir.getStdirglobalid();			// 模板文件zip的目标存储体目录id

			// 需要判断路径格式，必须以"/"结尾
			pathModelDest = fileopr.checkPathFormatRear(pathModelDest, '/');
			pathModelDest += filepath;
			pathModelDest = fileopr.checkPathFormatRear(pathModelDest, '/');
		}
		else
		{
			// 返回失败
			cmsLog.info("PortalMod的目标路径为空，返回失败。");
			return "";
		}
		
		// 复制模板zip文件到目标路径
		if(fileopr.copyFileFromSmbToSmb(fullPathModelSource, pathModelDest + filename) != 0)
		{
			// 返回失败
			cmsLog.info("复制模板zip文件失败，返回失败。");
			return "";
		}
		
		// 4 - 解压缩包，取编单ID为文件夹名，将解包中的内容放入pathModelDest。
		int ret = fileopr.unZipSmb(fullPathModelSource, pathModelDest);
		if(ret == 0)
		{
			// 压缩成功
			cmsLog.info("解压缩Portal模板成功。");
			
			// 写入proglistfile、amsstprg
			ProgListFile progListFile = new ProgListFile();
			AmsStoragePrgRel amsStoragePrgRel = new AmsStoragePrgRel();
			progListFile.setScheduledate(convertDateToScheduleDate(date));
			progListFile.setColumnclassid(portalColumn.getColumnclassid());
			progListFile.setFilename(filename);
			progListFile.setFiletype(Long.valueOf(2));
			progListFile.setDate1(new Date());
			progListFile.setState1(Long.valueOf(0));
			progListFile.setIdAct("");
			progListFile.setInputmanid(operatorId);
			progListFile.setInputtime(new Date());
			amsStoragePrgRel.setStglobalid(stglobalid);
			amsStoragePrgRel.setStdirglobalid(stdirglobalid);
//			amsStoragePrgRel.setProgfileid(progfileid);
			amsStoragePrgRel.setFtypeglobalid(filetypePortalZip);
			amsStoragePrgRel.setFilename(filename);
			amsStoragePrgRel.setFiledate(new Date());
			amsStoragePrgRel.setFilepath(filepath);
			amsStoragePrgRel.setUploadtime(new Date());
			amsStoragePrgRel.setInputmanid(operatorId);
			amsStoragePrgRel.setInputtime(new Date());
			amsStoragePrgRel.setFiledate(new Date());
			cmsTransactionManager.saveProgListFileAmsStoragePrgRel(
					progListFileManager, 
					amsstorageprgrelManager, 
					progListFile, 
					amsStoragePrgRel
					);
		}
		else
		{
			// 返回失败
			cmsLog.info("解压缩Portal模板失败。");
			return "";
		}
		
		cmsLog.info("Cms -> PortalServiceImpl -> getPortalModelZip returns.");
		return pathModelDest;
	}
	
	// 20100116 21:57，目前不使用
	// 把源文件名添加到目标路径中
	private String addFileFromSourcePathToDestPath(String sourcePath, String destPath)
	{
		cmsLog.info("Cms -> PortalServiceImpl -> addFileFromSourcePathToDestPath...");
		String newDestPath = "";
		String fileName = "";
		
		cmsLog.info("sourcePath - " + sourcePath);
		cmsLog.info("destPath - " + destPath);
		
		// "\\" 转成 "/"
		sourcePath = sourcePath.replaceAll("\\\\", "/");
		cmsLog.info("sourcePath - " + sourcePath);
		
		String[] strl = sourcePath.split("/");
		for(int i = strl.length - 1; i >= 0; i--)
		{
			if(strl[i] != null && !strl[i].equalsIgnoreCase(""))
			{
				fileName = strl[i];
				break;
			}
		}
		if(!fileName.equalsIgnoreCase(""))
		{
			if(destPath.charAt(destPath.length() - 1) == '/')
				newDestPath = destPath + fileName;
			else
				newDestPath = destPath + "/" + fileName;
		}
		else
		{
			newDestPath = destPath;
		}
		
		cmsLog.info("Cms -> PortalServiceImpl -> addFileFromSourcePathToDestPath returns.");
		return newDestPath;
	}
	

	// 20100114 15:44，目前不使用
	// 生成Portal
	private int generatePortal(
			String rootPortalColumnDefcatseq, 		// 
			String date,							// 栏目单日期，格式：2010-01-19
			String pathModelDest,					// 栏目单模板的目标路径
			String operatorId						// 操作人员id
			)
	{
		cmsLog.info("Cms -> PortalServiceImpl -> generatePortal...");
		
		// 流程：
		// 1 - 取栏目表中需要生成PORTAL的节点：
		// 条件：有效、是否生成发布文件（0生成）、Defcatseq
		
		// 从配置文件获取 
		String mediaFilecode = "H264";					// 加密区，ts文件filecode
//		String mediaFileSourceStclasscode = "NearOnline";// 加密区，ts文件源文件路径存储体等级
		String portalJsFilecode = "JS";					// portal，JS文件filecode
		String portalPicFilecode1 = "PNG3";				// portal，海报filecode
		String portalPicFilecode2 = "PNG2";				// portal，海报filecode
		String portalPicFilecode3 = "PNG1";				// portal，海报filecode
		String portalDestStclasscode = "CaOnline";			// portal，海报、key、zip目标路径存储体等级code
		String portalPicSourceStclasscode = "CaOnline";			// portal，海报和ts源文件路径存储体等级code
		String portalJsDestStclasscode = "CaOnline";			// portal，JS目标路径存储体等级
		String portalUrlHead = "\"file://\"+iPanel.getSavePath(1)+";		// portal，url头
		String portalMediaKeyPathMiddle = "ocwebs/videoCA/videoCA/";			// portal，ts对应key文件目标路径头
		String portalZipFilecode = "RMZIP";				// portal，富媒体zip的filecode
		String portalZipSourceStclasscode = "CaOnline";		// portal，富媒体zip的源文件存储等级code
		
		String currentIdAct = "FU00000082";
		
		String errorDetail = "";
		
		cmsLog.info("得到所有允许发布的栏目...");
		List portalColumns = portalColumnManager.getAllPublishPortalColumnsByDefcatseq(rootPortalColumnDefcatseq);
		if(portalColumns.size() <= 0)
		{
			String str = "未找到需要生成Portal的栏目，返回失败。";
			cmsLog.warn(str);
			errorDetail += str;
			return 1;
		}
		
		cmsLog.info("共有" + portalColumns.size() + "个栏目允许发布。");
		for(int i = 0; i < portalColumns.size(); i++)
		{
			// 每个portalColumn需要生成一个js
			PortalColumn portalColumn = (PortalColumn)portalColumns.get(i);
			String pcDir = portalColumn.getParentdir();
			String strJs = "";
			
			cmsLog.info("处理第" + (i+1) + "个栏目...");
			cmsLog.info("准备为栏目-" + portalColumn.getColumnname() + "(" + portalColumn.getColumnclassid() + ")生成Portal...");
					
			// 查询需要生成Portal的栏目(portalColumn)下所有的叶子节点栏目leafPcs
			cmsLog.info("查询得到栏目下有效的叶子节点栏目...");
			List leafPcs = portalColumnManager.getValidLeafPortalColumnsByDefcatseq(portalColumn.getDefcatseq());
			cmsLog.info("共有" + leafPcs.size() + "个叶子节点栏目。");
			// 判断需要生成Portal的栏目（portalColumn）是 富媒体 还是 点播栏目
			cmsLog.info("判断需要生成Portal的栏目类型...");
			if(portalColumn.getArchivedays() != null && portalColumn.getArchivedays() == 0)
			{
				// 需要生成Portal的栏目是 点播栏目(视频)
				cmsLog.info("栏目是视频类型...");
				strJs = "var movies = [\r\n";
			
				// 查栏目单详细，
				for(int j = 0; j < leafPcs.size(); j++)
				{
					// 为每个栏目的节目包添加海报文件（png），根据filecode、日期，查询
					// 目标路径：存储体ip＋存储体目录＋编单日期＋栏目目录（portalColumn的，不是leafPc的）

					PortalColumn leafPc = (PortalColumn)leafPcs.get(j);
					
					cmsLog.info("处理第" + (j+1) + "个叶子节点栏目...");
					cmsLog.info("栏目ID：" + leafPc.getColumnclassid());
					cmsLog.info("栏目名称：" + leafPc.getColumnname());
					
					// 查询海报(PNG)文件的目标路径
					// 这里使用portal模板的filecode，查询portal模板的目标路径，之后拼上date和pcdir，得到加密后海报的放在portal目录中的目标路径
					String destPath1 = "";
					String destPath2 = "";
					String destPath3 = "";

					destPath1 = pathModelDest + pcDir;
					destPath2 = pathModelDest + pcDir;
					destPath3 = pathModelDest + pcDir;
					destPath1 = destPath1.replace('\\', '/');
					destPath2 = destPath2.replace('\\', '/');
					destPath3 = destPath3.replace('\\', '/');
					cmsLog.info("海报1的目标路径 - " + destPath1);
					cmsLog.info("海报2的目标路径 - " + destPath2);
					cmsLog.info("海报3的目标路径 - " + destPath3);
					
					// 得到当前栏目（leafPc）的上线的节目包列表
					// 根据日期、栏目，查询栏目单详细，得到节目包列表
					// 返回：List<Object[]>
					//			(ProgListDetail)Object[0]
					//			(ProgPackage)Object[1]
					//			(FunResource)Object[2]
					cmsLog.info("查询得到当前栏目的上线的节目包列表...");
					List progListDetails = progListDetailManager.getProgListDetailsProgPackagesByDateAndDefcatseq(
							date, 
							leafPc.getDefcatseq()
							);
					
					cmsLog.info("共有" + progListDetails.size() + "个上线节目包。");
					for(int k = 0; k < progListDetails.size(); k++)
					{
						// 得到当前栏目（leafPc）的上线的节目包（pp）
						Object[] rows = (Object[])progListDetails.get(k);
						ProgListDetail pld = (ProgListDetail)rows[0];
						ProgPackage pp = (ProgPackage)rows[1];
						
						cmsLog.info("处理第" + (k+1) + "个节目包...");
						cmsLog.info("栏目单详细ID：" + pld.getProglistdetailid());
						cmsLog.info("节目包ID：" + pp.getProductid());
						cmsLog.info("节目包名称：" + pp.getProductname());
						
						String img1 = "";			// 海报1的文件名，含后缀
						String img2 = "";			// 海报2的文件名，含后缀
						String img3 = "";			// 海报3的文件名，含后缀
						String name = "";			// 节目包的名称
						String url = portalUrlHead;		// url 头 ＋ ts文件filepath＋ts文件filename
						String intro = "";			// 节目包的描述，推送日期、导演、演员、简介
						String ca = "";				// js文件的ca字段：0 / 
						String validFrom = "";		// 栏目上线日期
						
						validFrom = fileopr.convertDateToString(pld.getValidFrom(), "yyyy-MM-dd HH:mm:ss");

						name = pp.getProductname();
						intro = "推送日期：";
						if(validFrom != null)
							intro += validFrom;
						intro += "|导演：";
						if(pp.getDirector() != null)
							intro += pp.getDirector();
						intro += "|演员：";
						if(pp.getActors() != null)
							intro += pp.getActors();
						intro += "|简介：";
						if(pp.getDescription() != null)
							intro += pp.getDescription();
						
						if(pld.getETitle() == null || pld.getETitle().equalsIgnoreCase(""))
						{
							// 节目包的产品列表为空，文件不加扰
							cmsLog.info("节目包产品列表为空，标识节目包文件不加扰。");
							ca = "0";
						}
						else
						{
							// 节目包的产品列表不为空，文件加扰
							cmsLog.info("节目包的产品列表不为空，标识节目包文件加扰。");
							ca = "1";
						}
						
						// 根据节目包ID、filecode，查询栏目单详细和文件表，得到栏目下的媒体（视频）文件
						// 返回： List<ProgramFiles>
						cmsLog.info("查询得到节目包的视频文件...");
						List list = packageFilesManager.getProgramFilesesByProductidFilecode(
								pld.getProductid(), 
								mediaFilecode
								);
						cmsLog.info("共有" + list.size() + "个视频文件。");
						
						for(int m = 0; m < list.size(); m++)
						{
							// 得到节目包下每个媒体（视频）文件
							String tsSourcePath = "";			// ts文件的源路径，含文件名后缀
							String tsFilename = "";				// ts文件名，含后缀
							String keySourcePath = "";
							String keyDestpath = "";
							
							ProgramFiles pf = (ProgramFiles)list.get(m);
							
							cmsLog.info("处理第" + (m+1) + "个视频文件...");
							cmsLog.info("文件ID：" + pf.getProgfileid());
							cmsLog.info("文件名：" + pf.getFilename());
							
							tsFilename = pf.getFilename();				// ts文件名，含后缀

							// 返回：List
							// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
							// 2 - List<Object[]>
							// 		(AmsStorage)Object[0]
							// 		(AmsStoragePrgRel)Object[1]
							// 		(AmsStorageDir)Object[2]
							// 		(AmsStorageClass)Object[3]
							cmsLog.info("查询文件源路径...");
							List sourcePaths = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									pf.getProgfileid(),
									portalPicSourceStclasscode,
									pld.getProductid()
									);
							if(sourcePaths == null || sourcePaths.size() <= 0)
							{
								cmsLog.warn("媒体文件的源路径未空，返回失败。");
								errorDetail += "媒体文件的源路径未空，返回失败。";
								return 1;
							}
							tsSourcePath = (String)sourcePaths.get(0);
							List pflist = (List)sourcePaths.get(1);
							
							cmsLog.info("文件源路径 - " + tsSourcePath);
							
							AmsStoragePrgRel amspr = null;
							if(pflist.size() > 0)
							{
								Object[] pfrows = (Object[])pflist.get(0);
								amspr = (AmsStoragePrgRel)pfrows[1];
							}
							else
							{
								cmsLog.warn("媒体文件的源路径未空，返回失败。");
								errorDetail += "媒体文件的源路径未空，返回失败。";
								return 1;
							}

							if(m == 0)
							{
								// 注意：
								// 这个if语句，用来处理一个节目包下多个节目文件的情况
								// 当一个节目包下存在多个节目文件时，
								// 只取第一个节目文件，写入到js文件中，作为url的一部分
								// 这种做法是限于当前Portal的js格式，以后会做相应的修改
								cmsLog.warn("这个if语句，用来处理一个节目包下多个节目文件的情况。当一个节目包下存在多个节目文件时，只取第一个节目文件，写入到js文件中，作为url的一部分，这种做法是限于当前Portal的js格式，以后会做相应的修改。");
								
								String urlRear = "\"";
								if(amspr.getFilepath() != null && !amspr.getFilepath().equalsIgnoreCase(""))
								{
									urlRear += amspr.getFilepath();
									urlRear += "/";
								}
								urlRear += amspr.getFilename();
								urlRear += "|";
								if(pp.getProductname() != null)
								{
									urlRear += pp.getProductname();
								}
								urlRear += "\"";
//								cmsLog.info("urlRear - " + urlRear);
								
								// "\\" 转成 "/"
								urlRear = urlRear.replaceAll("\\\\", "/");
//								cmsLog.info("urlRear - " + urlRear);
	
								// 去掉多余的"/"或"\\"
								urlRear = urlRear.replaceAll("//", "/");
//								cmsLog.info("urlRear - " + urlRear);
								
								url += urlRear;
//								cmsLog.info("url - " + url);
							}
							// 查询ts对应key文件的源路径
							cmsLog.info("判断节目包是否加扰过，是否需要复制key文件到Portal...");
							if(ca.equalsIgnoreCase("1"))
							{
								cmsLog.info("节目包加扰过，需要复制key文件到Portal...");
								cmsLog.info("查询得到key文件源路径...");
								keySourcePath = tsSourcePath.replaceAll(tsFilename, "");
								
								
								// 查询ts对应key文件的源路径
								if(keySourcePath != null && !keySourcePath.equalsIgnoreCase(""))
								{
									if(keySourcePath.charAt(keySourcePath.length() - 1) != '/')
									{
										keySourcePath += "/";
									}
									keySourcePath += "key";
									cmsLog.info("key文件源路径 - " + keySourcePath);
								}
								else
								{
									cmsLog.warn("媒体文件对应的Key的源路径未空，返回失败。");
									errorDetail += "媒体文件对应的Key的源路径未空，返回失败。";
									return 1;
								}
								
								cmsLog.info("查询得到key文件目标路径...");
								// 查询ts对应key文件的目标路径
								// 根据filecode Stclasscode，查询，得到目标路径
								// 格式："smb://hc:hc@172.23.19.66/公用/"
								// 返回：List
								// 1 - String 目标路径()
								// 2 - List<Object[]>
								//			(AmsStorage)Object[0]
								//			(AmsStorageDir)Object[1]
								//			(AmsStorageClass)Object[2]
	//							List destpaths = packageFilesManager.getDestPathByFilecodeStclasscode(
	//									mediaFilecode, 
	//									portalDestStclasscode
	//									);
	//							if(destpaths != null && destpaths.size() >= 2)
	//							{
	//								keyDestpath = (String)destpaths.get(0);
									keyDestpath = pathModelDest;		// smb://administrator:1@172.23.19.213/CA/Portal/2010-01-27/
									if(keyDestpath != null && !keyDestpath.equalsIgnoreCase(""))
									{
	//									if(keyDestpath.charAt(keyDestpath.length() - 1) != '/')
	//										keyDestpath += "/";
	//									keyDestpath += date;
	//									keyDestpath += "/";
										if(portalMediaKeyPathMiddle != null && !portalMediaKeyPathMiddle.equalsIgnoreCase(""))
										{
											portalMediaKeyPathMiddle = portalMediaKeyPathMiddle.replace('\\', '/');
											portalMediaKeyPathMiddle = fileopr.checkPathFormatRear(portalMediaKeyPathMiddle, '/');
											keyDestpath += portalMediaKeyPathMiddle;	//"ocwebs/videoCA/"
										}
										String keydestfilepath = amspr.getFilepath();
										if(keydestfilepath != null && !keydestfilepath.equalsIgnoreCase(""))
										{
											keydestfilepath = keydestfilepath.replace('\\', '/');
											keydestfilepath = fileopr.checkPathFormatRear(keydestfilepath, '/');
											keyDestpath += keydestfilepath;	//"ocwebs/videoCA/"
										}
										keyDestpath += "key";
										cmsLog.info("key文件目标路径 - " + keyDestpath);
									}
									else
									{
										cmsLog.warn("媒体文件对应的Key的目标路径未空，返回失败。");
										errorDetail += "媒体文件对应的Key的目标路径未空，返回失败。";
										return 1;
									}
	//							}
	//							else
	//							{
	//								cmsLog.info("媒体文件对应的Key的目标路径未空，返回失败。");
	//								return 1;
	//							}
								
								
								
								// 复制key文件到目标路径，从源路径
								cmsLog.info("准备复制key文件到Portal...");
								if(fileopr.copyFileFromSmbToSmb(keySourcePath, keyDestpath) == 0)
								{
									cmsLog.info("复制key文件到Portal成功。");
								}
								else
								{
									cmsLog.warn("复制Key文件失败，返回失败。");
									errorDetail += "复制Key文件失败，返回失败。";
									return 1;
								}
							}
							else if(ca.equalsIgnoreCase("0"))
							{
								cmsLog.info("节目包没有加扰过，不需要复制key文件到Portal...");
							}
							else
							{
								cmsLog.warn("加扰标识未知：" + ca);
							}
						}
						
						cmsLog.info("查询节目包3张海报源路径...");
						// 根据节目包ID、filecode，查询栏目单详细和文件表，得到栏目下的海报文件（海报文件的源路径）
						// 返回： List<ProgramFiles>
						List list1 = packageFilesManager.getProgramFilesesByProductidFilecode(
								pld.getProductid(), 
								portalPicFilecode1
								);
						List list2 = packageFilesManager.getProgramFilesesByProductidFilecode(
								pld.getProductid(), 
								portalPicFilecode2
								);
						List list3 = packageFilesManager.getProgramFilesesByProductidFilecode(
								pld.getProductid(), 
								portalPicFilecode3
								);
						if(list1.size() > 0)	//for(int m = 0; m < list1.size(); m++)
						{
							ProgramFiles pf = (ProgramFiles)list1.get(0);
							img1 = pf.getFilename();
							// 查询海报1文件的源路径
							// 返回：List
							// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
							// 2 - List<Object[]>
							// 		(ProgramFiles)Object[0]
							// 		(AmsStorage)Object[1]
							// 		(AmsStoragePrgRel)Object[2]
							// 		(AmsStorageDir)Object[3]
							// 		(AmsStorageClass)Object[3]
							List sourcePaths = packageFilesManager.getSourcePathByProgfileidStclasscode(
									pf.getProgfileid(), 
									portalPicSourceStclasscode
									);
							String sourcePath = (String)sourcePaths.get(0);
							
							// 复制文件，到目标路径 destPath1 
							cmsLog.info("准备复制海报1到Portal...");
							cmsLog.info("源 - " + sourcePath);
							cmsLog.info("目标 - " + destPath1 + img1);
							if(fileopr.copyFileFromSmbToSmb(sourcePath, destPath1 + img1) == 0)
							{
								cmsLog.info("复制海报1到Portal成功。");
							}
							else
							{
								cmsLog.warn("复制海报1到Portal失败。");
								errorDetail += "复制海报1到Portal失败。";
							}
						}
						if(list2.size() > 0)	//for(int m = 0; m < list2.size(); m++)
						{
							ProgramFiles pf = (ProgramFiles)list2.get(0);
							img2 = pf.getFilename();
							// 查询海报2文件的源路径
							// 返回：List
							// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
							// 2 - List<Object[]>
							// 		(ProgramFiles)Object[0]
							// 		(AmsStorage)Object[1]
							// 		(AmsStoragePrgRel)Object[2]
							// 		(AmsStorageDir)Object[3]
							// 		(AmsStorageClass)Object[3]
							List sourcePaths = packageFilesManager.getSourcePathByProgfileidStclasscode(
									pf.getProgfileid(), 
									portalPicSourceStclasscode
									);
							String sourcePath = (String)sourcePaths.get(0);
							
							// 复制文件，到目标路径 destPath1 
							cmsLog.info("准备复制海报2到Portal...");
							cmsLog.info("源 - " + sourcePath);
							cmsLog.info("目标 - " + destPath2 + img2);
							if(fileopr.copyFileFromSmbToSmb(sourcePath, destPath2 + img2) == 0)
							{
								cmsLog.info("复制海报2到Portal成功。");
							}
							else
							{
								cmsLog.warn("复制海报2到Portal失败。");
								errorDetail += "复制海报2到Portal失败。";
							}
						}
						if(list3.size() > 0)	//for(int m = 0; m < list3.size(); m++)
						{
							ProgramFiles pf = (ProgramFiles)list3.get(0);
							img3 = pf.getFilename();
							// 查询海报3文件的源路径
							// 返回：List
							// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
							// 2 - List<Object[]>
							// 		(ProgramFiles)Object[0]
							// 		(AmsStorage)Object[1]
							// 		(AmsStoragePrgRel)Object[2]
							// 		(AmsStorageDir)Object[3]
							// 		(AmsStorageClass)Object[3]
							List sourcePaths = packageFilesManager.getSourcePathByProgfileidStclasscode(
									pf.getProgfileid(), 
									portalPicSourceStclasscode
									);
							String sourcePath = (String)sourcePaths.get(0);
							
							// 复制海报png文件，到目标路径 destPath1 
							cmsLog.info("准备复制海报3到Portal...");
							cmsLog.info("源 - " + sourcePath);
							cmsLog.info("目标 - " + destPath3 + img3);
							if(fileopr.copyFileFromSmbToSmb(sourcePath, destPath3 + img3) == 0)
							{
								cmsLog.info("复制海报3到Portal成功。");
							}
							else
							{
								cmsLog.warn("复制海报3到Portal失败。");
								errorDetail += "复制海报3到Portal失败。";
							}
						}
						
						// 20100205 16:24 北京修改
						// 根据茁壮提供的portal js文件格式，进行修改
						cmsLog.info("将节目包加入js字符串...");
						if(strJs.charAt(strJs.length() - 1) != '[')
						{
							strJs += ",";
						}
						strJs += "{";

						strJs += "\"img\": [\"";
						strJs += img1;
						strJs += "\", \"";
						strJs += img2;
						strJs += "\", \"";
						strJs += img3;
						strJs += "\"],";
						
						strJs += "\"name\": \"";
						strJs += name;
						strJs += "\",";
						
						strJs += "\"url\": ";
						strJs += url;
						strJs += ",";
						
						strJs += "\"intro\": \"";
						strJs += intro;
						strJs += "\"";
						
						// 20100205 11:38 北京修改
						strJs += ",";
						strJs += "\"ca\": ";
						strJs += ca;
						// 修改完毕
						
						strJs += "}\r\n";
					}
				}
				
				// strJs视频类栏目需要生成js文件
				// 生成js文件，js文件如何命名
				// 目标路径：存储体ip＋存储体目录＋编单日期＋栏目目录（portalColumn的）
				// 返回：List
				// 1 - String 目标路径()
				// 2 - List<Object[]>
				//			(AmsStorage)Object[0]
				//			(AmsStorageDir)Object[1]
				//			(AmsStorageClass)Object[2]
				String jsDestPath = "";
				String jsFileName = portalColumn.getPublishfilename();
				// 这里使用portal模板的filecode，查询portal模板的目标路径，之后拼上date和pcdir，得到JS文件放在portal目录中的目标路径

				
				jsDestPath = pathModelDest + pcDir;
				jsDestPath = jsDestPath.replace('\\', '/');
				jsDestPath = fileopr.checkPathFormatRear(jsDestPath, '/');
				jsDestPath += jsFileName;
				jsDestPath = jsDestPath.replace('\\', '/');
				cmsLog.info("JS文件的目标路径 - " + jsDestPath);
				
				strJs += "];\r\n";
				
				cmsLog.warn("此处没有使用事务处理，有待优化。");
				// 生成js文件，目标路径jsDestPath（含文件名后缀），内容strJs
				cmsLog.info("准备生成js文件...");
				cmsLog.info("目标 - " + jsDestPath);
				if(fileopr.createSmbFileGb2312(jsDestPath, strJs) == 0)
				{
					// 生成新记录到progListFile
					cmsLog.info("生成js文件成功，准备生成数据库记录(ProgListFile)...");
					ProgListFile progListFile = new ProgListFile();
					progListFile.setScheduledate(convertDateToScheduleDate(date));
					progListFile.setColumnclassid(portalColumn.getColumnclassid());
					progListFile.setFilename(jsFileName);
					progListFile.setFiletype(Long.valueOf(1));	// 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
					progListFile.setDate1(new Date());
					progListFile.setState1(Long.valueOf(0));
					progListFile.setIdAct("");
					progListFile.setInputmanid(operatorId);
					progListFile.setInputtime(new Date());
					cmsTransactionManager.saveProgListFile(
							progListFileManager, 
							progListFile, 
							date, 
							portalColumn.getDefcatseq()
							);
					
					cmsLog.info("生成数据库记录(ProgListFile)成功，ID：" + progListFile.getColumnfileid());
					// 压缩portal目录，到zip文件
//					cmsLog.info("缺少代码，压缩portal到zip。");

					cmsLog.info("准备发送栏目单分表活动(" + currentIdAct + ")，到一下活动...");
					PortalColumnServiceImpl pcservice = new PortalColumnServiceImpl();
					CmsResultDto crd = pcservice.sendProgListMangDetails(
							date, 
							portalColumn.getDefcatseq(), 
							currentIdAct, 
							operatorId, 
							""
							);
					if(crd.getResultCode() != 0)
					{
						cmsLog.warn("发送栏目单分表活动失败， 返回失败。");
						errorDetail += "发送栏目单分表活动失败， 返回失败。";
						return 1;
					}
					else
					{
						cmsLog.info("媒体栏目Portal已生成。");
					}
				}
				else
				{
					// 返回失败。
					cmsLog.warn("生成js文件失败， 返回失败。");
					cmsLog.warn("缺少代码， 返回失败。");
					errorDetail += "生成js文件失败， 返回失败。";
					return 1;
				}
			}
			else if(portalColumn.getArchivedays() != null && portalColumn.getArchivedays() == 1)
			{
				// 需要生成Portal的栏目是 富媒体栏目
				cmsLog.info("栏目是富媒体类型...");
				for(int j = 0; j < leafPcs.size(); j++)
				{
					// 把每个栏目的ZIP文件，解压缩到栏目目录
					// 目标路径：存储体ip＋存储体目录＋编单日期＋栏目目录（portalColumn的，不是leafPc的）
					PortalColumn leafPc = (PortalColumn)leafPcs.get(j);
					
					cmsLog.info("处理第" + (j+1) + "个叶子节点栏目...");
					cmsLog.info("栏目ID：" + leafPc.getColumnclassid());
					cmsLog.info("栏目名称：" + leafPc.getColumnname());
					
					String publishfilename = leafPc.getPublishfilename();
					
					// 根据日期、栏目，查询栏目单详细，得到节目包列表
					// 返回：List<Object[]>
					//			(ProgListDetail)Object[0]
					//			(ProgPackage)Object[1]
					cmsLog.info("查询得到当前栏目的上线的节目包列表...");
					List progListDetails = progListDetailManager.getProgListDetailsProgPackagesByDateAndDefcatseq(
							date, 
							leafPc.getDefcatseq()
							);
					cmsLog.info("共有" + progListDetails.size() + "个上线的节目包。");
					
					for(int k = 0; k < progListDetails.size(); k++)
					{
						Object[] rows = (Object[])progListDetails.get(k);
						ProgListDetail pld = (ProgListDetail)rows[0];
						ProgPackage pp = (ProgPackage)rows[1];
						
						cmsLog.info("处理第" + (k+1) + "个节目包...");
						cmsLog.info("栏目单详细ID：" + pld.getProglistdetailid());
						cmsLog.info("节目包ID：" + pp.getProductid());
						cmsLog.info("节目包名称：" + pp.getProductname());
						
						String zipSourcePath = "";			// zip文件的源路径，含文件名后缀
						String zipDestPath = "";			// zip文件的目标路径，含文件名后缀
						String progfileid = "";
						
						// 查询栏目的zip文件源路径
						// 根据节目包ID、filecode，查询栏目单详细和文件表，得到栏目下的媒体（视频）文件
						// 返回： List<ProgramFiles>
						cmsLog.info("查询节目包的zip文件...");
						List list = packageFilesManager.getProgramFilesesByProductidFilecode(
								pld.getProductid(), 
								portalZipFilecode
								);
						cmsLog.info("共有" + list.size() + "个结果。");
						if(list.size() > 0)	//for(int m = 0; m < list.size(); m++)
						{
							ProgramFiles pf = (ProgramFiles)list.get(0);
							progfileid = pf.getProgfileid();
							cmsLog.info("取第一个结果...");
							cmsLog.info("富媒体ZIP文件ID： " + progfileid);
							cmsLog.info("富媒体ZIP文件名： " + pf.getFilename());
							
							// 查询加密后的zip文件的源路径
							// 返回：List
							// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
							// 2 - List<Object[]>
							// 		(ProgramFiles)Object[0]
							// 		(AmsStorage)Object[1]
							// 		(AmsStoragePrgRel)Object[2]
							// 		(AmsStorageDir)Object[3]
							// 		(AmsStorageClass)Object[3]
							cmsLog.info("查询加密后的zip文件的源路径...");
							List sourcePaths = packageFilesManager.getSourcePathByProgfileidStclasscode(
									pf.getProgfileid(), 
									portalZipSourceStclasscode
									);
							if(sourcePaths != null && sourcePaths.size() >= 2)
							{
								zipSourcePath = (String)sourcePaths.get(0);
								cmsLog.info("ZIP文件的源路径是 - " + zipSourcePath);
							}
							else
							{
								cmsLog.warn("查询加密后的zip文件的源路径为空，返回失败。");
								return 1;
							}
						}
						else
						{
							cmsLog.warn("没有查询到加密后的zip文件的源路径，返回失败。");
							return 1;
						}
						
						// 查询加密后的zip文件解压缩目标路径
						cmsLog.info("查询加密后的zip文件解压缩目标路径...");
						zipDestPath = pathModelDest;
						zipDestPath = zipDestPath.replace('\\', '/');
						zipDestPath = fileopr.checkPathFormatRear(zipDestPath, '/');
						zipDestPath += pcDir;
						zipDestPath = zipDestPath.replace('\\', '/');
						zipDestPath = fileopr.checkPathFormatRear(zipDestPath, '/');
//						zipDestPath = zipDestPath.substring(0, zipDestPath.lastIndexOf("/"));
						cmsLog.info("ZIP文件的解压缩目标路径是 - " + zipDestPath);
						
						// 解压缩加密后的zip文件到目标路径
						if(zipSourcePath == null 
							|| zipSourcePath.equalsIgnoreCase("")
							|| zipDestPath == null
							|| zipDestPath.equalsIgnoreCase("")
							)
						{
							cmsLog.warn("富媒体解压缩源路径或目标路径为空，返回失败。");
							return 1;
						}
						else
						{
							cmsLog.info("准备解压缩zip文件...");
							cmsLog.info("源 - " + zipSourcePath);
							cmsLog.info("目标 - " + zipDestPath);
							if(fileopr.unZipSmb(zipSourcePath, zipDestPath) == 0)
							{
								// 20100205 13:22 北京修改
								// 修改富媒体目录名字，流程：
								// 解压缩好富媒体的zip文件到加扰库的Portal模板目标目录
								// 需要修改解压缩生成的文件夹名字
								// 修改前，文件夹名字是zip文件的名字，即文件ID，zipDestPath
								// 修改后，文件夹名字是富媒体栏目的publishfilename，
								cmsLog.info("解压缩富媒体到Portal目录成功，准备修改富媒体目录名字。");
								if(publishfilename != null && !publishfilename.equalsIgnoreCase(""))
								{
									publishfilename = fileopr.checkPathFormatRear(publishfilename, '/');
									
									// 修改解压缩后的富媒体目录
									cmsLog.info("准备修改富媒体目录名字...");
									String oldFolder = zipDestPath;
									oldFolder = fileopr.checkPathFormatRear(oldFolder, '/');
									oldFolder += progfileid;
									oldFolder = fileopr.checkPathFormatRear(oldFolder, '/');

									cmsLog.info("修改前富媒体目录路径名字 - " + oldFolder);
									cmsLog.info("修改后富媒体目录名字 - " + publishfilename);
									if(fileopr.renameSmbFile(oldFolder, publishfilename) == 0)
									{						
										// 发送栏目分表活动到84"预览"
										cmsLog.info("修改解压缩后的富媒体目录成功，准备发送栏目单分表活动到“预览”FU00000084。");
										PortalColumnServiceImpl pcservice = new PortalColumnServiceImpl();
										CmsResultDto crd = pcservice.sendProgListMangDetails(
												date, 
												portalColumn.getDefcatseq(), 
												currentIdAct, 
												operatorId, 
												""
												);
										if(crd.getResultCode() != 0)
										{
											cmsLog.warn("发送栏目单分表活动失败， 返回失败。");
											return 1;
										}
										else
										{
											cmsLog.info("发送栏目单分表活动成功。");
										}
									}
									else
									{
										cmsLog.warn("修改解压缩后的富媒体目录失败，返回失败。");
										return 1;
									}
								}
								else
								{
									// 栏目的发布文件名称为空
									cmsLog.warn("栏目的发布文件名称为空。栏目ID：" + leafPc.getColumnclassid());
									cmsLog.warn("修改解压缩后的富媒体目录失败，返回失败。");
									return 1;
								}
								// 修改完毕
							}
							else
							{
								cmsLog.warn("解压缩富媒体失败，返回失败。");
								return 1;
							}
						}
					}
				}
				
				// 富媒体栏目不需要生成js文件
				cmsLog.info("富媒体栏目Portal已生成。");
			}
			else
			{
				cmsLog.warn("未知栏目类型(" + portalColumn.getArchivedays() + ")，不处理，跳过...");
				continue;
			}
		}
		
		cmsLog.info("Cms -> PortalServiceImpl -> generatePortal returns.");
		return 0;
	}
	
	// 20100114 10:50
	// 生成Portal。界面选择一天栏目单分表记录，点击“生成Portal”。
	public CmsResultDto generatePortalByDate(
			String date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			String defcatseq,					// 栏目code序列
			String currentIdAct, 				// 当前活动编号
			String operatorId					// 操作人员id
			)
	{
		// 流程：
		// 0 - 判断date日期栏目单分表记录是否都是“生成页面”FU00000082或“Portal完成”FU00000083
		// 1 - 判断模板是否已经获取，返回目标的目标路径
		// 2 - 生成Portal
		
		cmsLog.info("Cms -> PortalServiceImpl -> generatePortalByDate...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 修改为新接口，20100408 14:15
		cmsResultDto = generatePortalWithoutModel(
				date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
				defcatseq,					// 栏目code序列
				currentIdAct, 				// 当前活动编号
				operatorId					// 操作人员id
				);
//
//		// 配置文件，获取，需要生成portal的栏目根节点，用于查询
//		String rootPortalColumnDefcatseq = "ROOT\\LANMUSHU";
//		
//		String pathModelDest = "";	// Portal模板的目标路径：存储IP + 存储目录 + 编单日期ID(主键)
//		
//		// 0 - 判断date日期栏目单分表记录是否都是“生成页面”FU00000082或“Portal完成”FU00000083
//		boolean allowed = true;
//		cmsLog.info("查询栏目，栏目defcatseq：" + defcatseq);
//		List pcs = portalColumnManager.findByProperty("defcatseq", defcatseq);
//		if(pcs == null || pcs.size() <= 0)
//		{
//			String str = "栏目不存在。栏目code序列：" + defcatseq;
//			cmsResultDto.setResultCode(Long.valueOf(1));
//			cmsResultDto.setErrorMessage(str);
//			cmsLog.info(str);
//			return cmsResultDto;
//		}
//		PortalColumn pc = (PortalColumn)pcs.get(0);
//		cmsLog.info("栏目ID：" + pc.getColumnclassid());
//		cmsLog.info("栏目名称：" + pc.getColumnname());
//		cmsLog.info("查询栏目下的栏目单分表记录，判断栏目单分表活动是否合法...");
//		List plmds = progListMangDetailManager.getProgListMangDetailsByScheduleDateAndColumnclassid(
//				convertDateToScheduleDate(date), 
//				pc.getColumnclassid()
//				);
//		for(int i = 0; i < plmds.size(); i++)
//		{
//			ProgListMangDetail progListMangDetail = (ProgListMangDetail)plmds.get(i);
//			if(!(progListMangDetail.getIdAct().equalsIgnoreCase("FU00000082") 
//				|| progListMangDetail.getIdAct().equalsIgnoreCase("FU00000083"))
//				)
//			{
//				allowed = false;
//				String str = "栏目单分表活动不合法，不能生成portal。活动：" + progListMangDetail.getIdAct();
//				break;
//			}
//		}
//		
//		if(allowed == false)
//		{
//			String str = "栏目单分表活动不合法，不能生成portal。栏目code序列：" + defcatseq;
//			cmsResultDto.setResultCode(Long.valueOf(1));
//			cmsResultDto.setErrorMessage(str);
//			cmsLog.warn(str);
//		}
//		else
//		{
//			// 1 - 判断模板是否已经获取
//			// 根据date、栏目，查询proglistfile，得到proglistfile
//			cmsLog.info("栏目单分表活动合法，允许生成Portal...");
//			cmsLog.info("检查并查询模板目录...");
//			pathModelDest = checkPortalModInProgListFile(date, rootPortalColumnDefcatseq, operatorId);
//	
//			if(pathModelDest != null && !pathModelDest.equalsIgnoreCase(""))
//			{
//				// 2 - 生成Portal
//				cmsLog.info("准备生成Portal...");
//				if(generatePortal(defcatseq, date, pathModelDest, operatorId) == 0)
//				{
//					cmsLog.info("生成Portal成功。");
//					cmsResultDto.setResultCode(Long.valueOf(0));
//				}
//				else
//				{
//					String str = "生成Portal失败。";
//					cmsResultDto.setResultCode(Long.valueOf(1));
//					cmsResultDto.setErrorMessage(str);
//					cmsLog.warn(str);
//				}
//			}
//			else
//			{
//				String str = "查询Portal模板失败。";
//				cmsResultDto.setResultCode(Long.valueOf(1));
//				cmsResultDto.setErrorMessage(str);
//				cmsLog.warn(str);
//			}
//		}
		cmsLog.info("Cms -> PortalServiceImpl -> generatePortalByDate returns.");
		return cmsResultDto;
	}

	// 这就是传说中的方法14
	// 20100121 11:20，目前不使用
	// 根据，得到Portal模板文件zip上传的路径，路径：存储IP + 存储目录 + 编单日期ID(主键)
	public CmsResultDto getPortalModelDestpathByFilepath(
			String filepath						// filepath，期待传入模板ID
			)
	{
		// 返回：List
		// 1 - String 目标路径，以'/'结尾，不包含文件名，格式："smb://hc:hc@172.23.19.66/公用/"
		// 2 - AmsStorage
		// 3 - AmsStorageDir
		// 4 - AmsStorageClass
		
		cmsLog.info("Cms -> PortalServiceImpl -> getPortalModelDestpathByFilepath...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件，获取
		String filecode = "MODZIP";
		String stclasscode = "NearOnline";
		
//		String portalModelDestpath = "";
		List portalModelDestpaths = getDestPath(filecode,stclasscode,filepath);
//		if(portalModelDestpaths != null && portalModelDestpaths.size() >= 4)
//		{
//			portalModelDestpath = (String)portalModelDestpaths.get(0);
//		}
		
		cmsResultDto.setResultObject(portalModelDestpaths);
		cmsLog.info("Cms -> PortalServiceImpl -> getPortalModelDestpathByFilepath returns.");
		return cmsResultDto;
	}
	
	// 20100130，目前不使用
	// 压缩portal目录，判断所有（总单）的栏目单分表的活动是否“PORTAL完成”FU00000083，都是才能压缩，然后改分表和总表活动为“预览”FU00000084
	public CmsResultDto zipPortal(
			String date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			String operatorId					// 操作人员id
			)
	{
		// 流程：
		// 1 - 判断所有（总单）的栏目单分表的活动是否“PORTAL完成”FU00000083
		// 1.1 - 如果不是，返回失败
		// 1.2 - 如果是，继续（生成portal）
		// 2 - 查找portal的源路径，在加扰库
		// 3 - 查询portal压缩的目标路径，在加扰库
		// 4 - 压缩，不需要生成文件位置记录，因为这条记录已经存在，删除原来的zip文件，生成新的zip文件替代原来的
		// 5 - 修改所有分表和总表活动为“预览”FU00000084
		
		cmsLog.info("Cms -> PortalServiceImpl -> zipPortal...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsResultDto = sendProgListMang(
				date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
				operatorId					// 操作人员id
				);
		
	
		
//		// 配置文件，获取
//		String stclasscodeCaOnline = "CaOnline";			// 加扰库存储体等级code
//		String stclasscodeOnline = "Online";				// 一级库存储体等级code
//		String columnclasscodeRoot = "ROOT\\LANMUSHU";
//		
//		String currentIdAct = "FU00000083";
//		String sourcePathDir = "";							// 用于压缩的目录的源路径
//		String destPathZipFile = "";						// 压缩成zip文件的目标路径
//		
//		
//		// 1 - 判断所有（总单）的栏目单分表的活动是否“PORTAL完成”FU00000083
//		boolean allowed = true;
//		String scheduledate = convertDateToScheduleDate(date);
//		// 根据栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
//		// 返回：List<Object[]>
//		// 			(ProgListMangDetail)Object[0]
//		//			(PortalColumn)Object[1]
//		//			(FunResource)Object[2]
//		List list = progListMangDetailManager.getProgListMangDetailsByScheduledate(scheduledate);
//		if(list != null && list.size() > 0)
//		{
//			for(int i = 0; i < list.size(); i++)
//			{
//				Object[] rows = (Object[])list.get(i);
//				ProgListMangDetail plmd = (ProgListMangDetail)rows[0];
//				if(!plmd.getIdAct().equalsIgnoreCase(currentIdAct))		// “PORTAL完成”
//				{
//					allowed = false;
//					break;
//				}
//			}                                                 
//		}
//		else
//		{
//			// 未查询到栏目单分表记录，返回失败
//			String str = "未查询到栏目单分表记录，返回失败。date:" + date;
//			cmsResultDto.setResultCode(Long.valueOf(1));
//			cmsResultDto.setErrorMessage(str);
//			cmsLog.warn(str);
//			return cmsResultDto;
//		}
//		
//		// 1.1 - 如果不是，返回失败
//		if(allowed == false)
//		{
//			// 返回失败
//			String str = "不是所有栏目单分表都是“PORTAL完成”活动，Portal生成未完成，不能压缩。";
//			cmsResultDto.setResultCode(Long.valueOf(1));
//			cmsResultDto.setErrorMessage(str);
//			cmsLog.warn(str);
//			return cmsResultDto;
//		}
//		
//		// 1.2 - 如果是，继续（生成portal）
//		// 2 - 查找portal的源路径，在加扰库
//		// 这就是传说中的方法7
//		// 根据date、栏目的defcatseq、filetype，查询proglistfile，得到ProgListFile列表
//		// 如果栏目defcatseq为空，则忽略该条件
//		// 返回：
//		// List<Object[]>
//		//		(ProgListFile)Object[0]
//		//		(PortalColumn)Object[1]
//		List progListFiles = progListFileManager.getProgListFilesByDateFiletypeDefcatseq(
//				date, 					// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
//				(long)2, 				// 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
//				"");					// 栏目code序列
//		if(progListFiles != null && progListFiles.size() > 0)
//		{
//			Object[] rows = (Object[])progListFiles.get(0);
//			ProgListFile progListFile = (ProgListFile)rows[0];
//			
//			// 3 - 查询portal压缩的目标路径，在加扰库
//			// 这就是传说中的方法11
//			// 根据文件ID 和 存储体等级，查询，得到文件存放(源)路径（不返回ProgramFiles），
//			// 当progfileid传入内容是模板的ID时，在programfiles中无记录
//			// 当节目包ID为null或者""时，忽略节目包ID的过滤条件
//			// 返回：List
//			// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
//			// 2 - List<Object[]>
//			// 		(AmsStorage)Object[0]
//			// 		(AmsStoragePrgRel)Object[1]
//			// 		(AmsStorageDir)Object[2]
//			// 		(AmsStorageClass)Object[3]
//			List pfs = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
//					progListFile.getColumnfileid(), 
//					stclasscodeCaOnline, 
//					null
//					);
//			if(pfs != null && pfs.size() >= 2)
//			{
//				String sourcePath = (String)pfs.get(0);
//				List rows0 = (List)pfs.get(1);
//				Object[] rows1 = (Object[])rows0.get(0);
//				AmsStoragePrgRel aspr = (AmsStoragePrgRel)rows1[1];
//				AmsStorageDir asd = (AmsStorageDir)rows1[2];
//				
//				
//				if(sourcePath == null || sourcePath.equalsIgnoreCase(""))
//				{
//					// 返回失败
//					String str = "在加扰库中，没有找到Portal文件，返回失败。";
//					cmsResultDto.setResultCode(Long.valueOf(1));
//					cmsResultDto.setErrorMessage(str);
//					cmsLog.warn(str);
//					return cmsResultDto;
//				}
//				// 查询得到的portal的源路径，去掉后缀，就是此次用于压缩的目录的源路径
//				sourcePathDir = sourcePath;
//				sourcePathDir = sourcePathDir.substring(0, sourcePathDir.lastIndexOf("."));
//				sourcePathDir = fileopr.checkPathFormatRear(sourcePathDir, '/');
//				// 查询得到的portal的源路径，也是此次压缩成zip文件的目标路径
//				destPathZipFile = sourcePath;
//				
//				// 压缩Portal
//				// 4 - 压缩，不需要生成文件位置记录，因为这条记录已经存在，删除原来的zip文件，生成新的zip文件替代原来的
//				cmsLog.info("准备压缩Portalzip文件...");
//				cmsLog.info("源 - " + sourcePathDir);
//				cmsLog.info("目标 - " + destPathZipFile);
//				int ret = fileopr.zipSmbSinglePath(
//						sourcePathDir, 		// 存放需要压缩的文件或目录的路径，可以是绝对或相对路径，文件要包含扩展名
//						destPathZipFile		// 压缩后的zip文件的路径，可以是绝对或相对路径，包含文件的扩展名
//						);
//				
//				
//				if(ret == 0)
//				{
//					// 压缩完成后，把压缩文件zip，copy到一级库，
//					String zipSourcePath = destPathZipFile;
//					String zipDestPath = "";
//					String zipDestPathFile = "";
//					
//					// 返回：List
//					// 1 - String 目标路径()  格式："smb://hc:hc@172.23.19.66/公用/"
//					// 2 - List<Object[]>
//					//			(AmsStorage)Object[0]
//					//			(AmsStorageDir)Object[1]
//					//			(AmsStorageClass)Object[2]
//
//					List pfs1 = packageFilesManager.getDestPathByFilecodeStclasscode(
//							asd.getFilecode(), 
//							stclasscodeOnline
//							);
//					if(pfs1 == null || pfs1.size() < 2)
//					{
//						// 返回失败
//						String str = "获取Portalzip文件在一级库的目标路径为空。";
//						cmsResultDto.setResultCode(Long.valueOf(1));
//						cmsResultDto.setErrorMessage(str);
//						cmsLog.warn(str);
//						return cmsResultDto;
//					}
//					zipDestPath = (String)pfs1.get(0);
//					List list1 = (List)pfs1.get(1);
//					Object[] rows2 = (Object[])list1.get(0);
//					AmsStorage amst = (AmsStorage)rows2[0];
//					AmsStorageDir amsd = (AmsStorageDir)rows2[1];
//					AmsStorageClass amsc = (AmsStorageClass)rows2[2];
//					
//					// 为目标路径zipDestPath添加filepath，从aspr.getFilepath()获取
//					zipDestPath.replace('\\', '/');
//					zipDestPath = fileopr.checkPathFormatRear(zipDestPath, '/');
//					if(aspr.getFilepath() != null && !aspr.getFilepath().equalsIgnoreCase(""))
//					{
//						zipDestPath += aspr.getFilepath();
//						zipDestPath.replace('\\', '/');
//						zipDestPath = fileopr.checkPathFormatRear(zipDestPath, '/');
//					}
//					
//					// 添加文件名
//					zipDestPathFile = addFileFromSourcePathToDestPath(zipSourcePath, zipDestPath);
//					cmsLog.info("准备复制Portalzip文件...");
//					cmsLog.info("源 - " + zipSourcePath);
//					cmsLog.info("目标 - " + zipDestPathFile);
//					ret = fileopr.copyFileFromSmbToSmb(zipSourcePath, zipDestPathFile);
//					
//					if(ret == 0)
//					{
//						// 压缩文件zip到一级库之后，解压缩zip文件，
//						cmsLog.info("复制Portalzip文件成功。");
//						cmsLog.info("准备解压缩Portalzip文件...");
//						cmsLog.info("源 - " + zipDestPathFile);
//						cmsLog.info("目标 - " + zipDestPath);
//						ret = fileopr.unZipSmb(zipDestPathFile, zipDestPath);
//
//						if(ret == 0)
//						{
//							cmsLog.info("Portalzip文件解压缩成功。");
//							cmsLog.info("此处没有使用事务处理，有待优化。");
//							
//							// 文件位置表，添加记录
//							cmsLog.info("准备添加文件位置表记录...");
//							AmsStoragePrgRel amsStoragePrgRel = new AmsStoragePrgRel();
//							amsStoragePrgRel.setStglobalid(amst.getStglobalid());
//							amsStoragePrgRel.setStdirglobalid(amsd.getStdirglobalid());
//							amsStoragePrgRel.setFtypeglobalid(aspr.getFtypeglobalid());
//							amsStoragePrgRel.setFilepath(aspr.getFilepath());
//							amsStoragePrgRel.setFilename(aspr.getFilename());
//							amsStoragePrgRel.setFiledate(aspr.getFiledate());
//							amsStoragePrgRel.setPrglobalid(aspr.getPrglobalid());
//							amsStoragePrgRel.setProgfileid(aspr.getProgfileid());
//							amsStoragePrgRel.setUploadtime(new Date());
//							amsStoragePrgRel.setInputtime(new Date());
//							amsStoragePrgRel.setInputmanid(operatorId);
//							amsstorageprgrelManager.save(amsStoragePrgRel);
//							cmsLog.info("文件位置表记录添加成功，文件位置ID：" + amsStoragePrgRel.getRelid());
//							
//							// 然后，发送栏目单分表活动
//							// 5 - 修改所有分表和总表活动为“迁移”FU00000085
//							cmsLog.info("准备发送栏目单分表和总表到下一活动...");
//							cmsLog.info("当前活动：" + currentIdAct);
//							PortalColumnServiceImpl portalColumnService = new PortalColumnServiceImpl();
//							cmsResultDto = portalColumnService.sendProgListMangDetails(
//									date, 
//									columnclasscodeRoot,		// 栏目code序列 
//									currentIdAct, 				// 当前活动 “PORTAL完成”
//									operatorId, 
//									"Portal压缩成功"
//									);
//						}
//						else
//						{
//							// 返回失败
//							String str = "解压缩Portalzip文件在一级库失败。";
//							cmsResultDto.setResultCode(Long.valueOf(1));
//							cmsResultDto.setErrorMessage(str);
//							cmsLog.warn(str);
//							return cmsResultDto;
//						}
//					}
//					else
//					{
//						// 返回失败
//						String str = "复制Portalzip文件到一级库失败。";
//						cmsResultDto.setResultCode(Long.valueOf(1));
//						cmsResultDto.setErrorMessage(str);
//						cmsLog.info(str);
//						return cmsResultDto;
//					}
//				}
//				else
//				{
//					// 返回失败
//					String str = "压缩Portal失败。";
//					cmsResultDto.setResultCode(Long.valueOf(1));
//					cmsResultDto.setErrorMessage(str);
//					cmsLog.info(str);
////					return cmsResultDto;
//				}
//			}
//			else
//			{
//				// 返回失败
//				String str = "在加扰库中，没有找到Portal文件，返回失败。";
//				cmsResultDto.setResultCode(Long.valueOf(1));
//				cmsResultDto.setErrorMessage(str);
//				cmsLog.info(str);
//				return cmsResultDto;
//			}
//		}
//		else
//		{
//			// 返回失败
//			String str = "没有找到Portal文件，返回失败。";
//			cmsResultDto.setResultCode(Long.valueOf(1));
//			cmsResultDto.setErrorMessage(str);
//			cmsLog.info(str);
//			return cmsResultDto;
//		}

		cmsLog.info("Cms -> PortalServiceImpl -> zipPortal returns.");
		return cmsResultDto;
	}
	
	// 20100408 15:05
	// 发送总表分表活动，不管理portal
	public CmsResultDto sendProgListMang(
			String date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			String operatorId					// 操作人员id
			)
	{
		cmsLog.info("Cms -> PortalServiceImpl -> sendProgListMang...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件，获取
		String columnclasscodeRoot = "ROOT\\LANMUSHU";
		
		String currentIdAct = "FU00000083";
		
		PortalColumnServiceImpl portalColumnService = new PortalColumnServiceImpl();
		cmsResultDto = portalColumnService.sendProgListMangDetails(
				date, 
				columnclasscodeRoot,		// 栏目code序列 
				currentIdAct, 				// 当前活动 “PORTAL完成”
				operatorId, 
				"Portal发送"
				);
		
		
		
		cmsLog.info("Cms -> PortalServiceImpl -> sendProgListMang returns.");
		return cmsResultDto;
	}
	
	/**
	 * 文件加扰新增功能.. 根据日期发送
	 * @param date
	 * @param operatorId
	 * @return
	 */
	public CmsResultDto sendProgListMang2(
			String date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			String operatorId,					// 操作人员id
			String sendremark
			)
	{
		cmsLog.info("Cms -> PortalServiceImpl -> sendProgListMang2...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件，获取
		String columnclasscodeRoot = "ROOT\\LANMUSHU";
		
		String currentIdAct = "FU00000080";
		
		PortalColumnServiceImpl portalColumnService = new PortalColumnServiceImpl();
		cmsResultDto = portalColumnService.sendProgListMangDetails(
				date, 
				columnclasscodeRoot,		// 栏目code序列 
				currentIdAct, 				// 当前活动 “PORTAL完成”
				operatorId, 
				sendremark
				);
		
		
		cmsLog.info("Cms -> PortalServiceImpl -> sendProgListMang2 returns.");
		return cmsResultDto;
	}
	
	// 20100330
	// 生成Portal，根据最新讨论结果修改
	// 复制小文件（海报、xml、字幕文件），生成js，到播发库（一级库），不管理Portal模板和Portal。
	public CmsResultDto generatePortalWithoutModel(
			String date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			String defcatseq,					// 栏目code序列
			String currentIdAct, 				// 当前活动编号
			String operatorId					// 操作人员id
			)
	{
		cmsLog.info("Cms -> PortalServiceImpl -> generatePortalWithoutModel...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 说明：
		// 1 - 复制海报文件和媒体key文件到播发库（一级库）
		// 2 - 生成js文件到播发库（一级库）
		// 3 - 发送栏目的活动到下一活动
		
		
		// 配置文件，获取，需要生成portal的栏目根节点，用于查询
		String rootPortalColumnDefcatseq = "ROOT\\LANMUSHU";
		String mediaFilecode = "H264";					// 加密区，ts文件filecode
		String filecodeOnline = "ENCRYPTFILE";
		String portalJsFilecode = "JS";					// portal，JS文件filecode
		String stclasscodeOnline = "Online";
		String stclasscodeCaOnline = "CaOnline";
		String portalPicSourceStclasscode = "CaOnline";			// portal，海报和ts源文件路径存储体等级code
		String portalJsDestStclasscode = "CaOnline";			// portal，JS目标路径存储体等级
		String portalZipSourceStclasscode = "CaOnline";		// portal，富媒体zip的源文件存储等级code
		String portalPicFilecode = "PNG1";		// 目前，只有富媒体使用
//		String filecodeSrtChs = "WGZMCHS";
//		String filecodeSrtCht = "WGZMCHT";
//		String filecodeSrtEng = "WGZMENG";
		String filetypePng = "PNG";
		String filetypeSrt = "WGZM";

		
		
		currentIdAct = "FU00000082";
		
		String errorDetail = "";
		int	errorcount = 0;				// 错误计数
		
		//String pathModelDest = "";	// Portal模板的目标路径：存储IP + 存储目录 + 编单日期ID(主键)
		
		// 0 - 判断date日期栏目单分表记录是否都是“生成页面”FU00000082或“Portal完成”FU00000083
		boolean allowed = true;
		
		try 
		{
			cmsLog.info("查询栏目，栏目defcatseq：" + defcatseq);
			List pcs = portalColumnManager.findByProperty("defcatseq",
					defcatseq);
			if (pcs == null || pcs.size() <= 0) 
			{
				String str = "栏目不存在。栏目code序列：" + defcatseq;
				cmsResultDto.setResultCode(Long.valueOf(1));
				cmsResultDto.setErrorMessage(str);
				cmsLog.info(str);
				return cmsResultDto;
			}
			PortalColumn pc = (PortalColumn) pcs.get(0);
			cmsLog.info("栏目ID：" + pc.getColumnclassid());
			cmsLog.info("栏目名称：" + pc.getColumnname());
			cmsLog.info("查询栏目下的栏目单分表记录，判断栏目单分表活动是否合法...");
			List plmds = progListMangDetailManager
					.getProgListMangDetailsByScheduleDateAndColumnclassid(
							convertDateToScheduleDate(date), 
							pc.getColumnclassid()
							);
			if (plmds != null && plmds.size() > 0) 
			{
				for (int i = 0; i < plmds.size(); i++) 
				{
					ProgListMangDetail progListMangDetail = (ProgListMangDetail) plmds.get(i);
					if (!(progListMangDetail.getIdAct().equalsIgnoreCase("FU00000082") 
						|| progListMangDetail.getIdAct().equalsIgnoreCase("FU00000083"))
						) 
					{
						allowed = false;
						String str = "栏目单分表活动不合法，不能生成portal。活动："
								+ progListMangDetail.getIdAct();
						break;
					}
				}
			} 
			else 
			{
				allowed = false;
				cmsLog.warn("栏目单分表记录未查询到。");
				errorDetail += "栏目单分表记录未查询到。\r\n";
				errorcount++;
			}
			if (allowed == false) 
			{
				String str = "栏目单分表活动不合法，不能生成portal。栏目code序列：" + defcatseq;
				cmsResultDto.setResultCode(Long.valueOf(1));
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			} 
			else 
			{
				cmsLog.info("栏目单分表活动合法，可以生成Portal...");

				// --- 20100518 --- 
				List ppOfNoPortalPackage = new ArrayList();
				cmsLog.info("判断电视剧栏目的所有节目包是否都有对应的页面包装...");
				cmsLog.info("查询电视剧栏目的所有节目包...");
				List ps = progListDetailManager.getProgListDetailsProgPackagesByDateAndDefcatseq(
						date,
						"ROOT\\LANMUSHU\\yuanxian\\yx_dsj"
						);
				if (ps == null) 
				{
					cmsLog.warn("查询电视剧栏目的所有节目包结果为空。");
				} 
				else if (ps.size() > 0) 
				{
					cmsLog.info("电视剧栏目的节目包数量为 " + ps.size() + "。");
					for (int i = 0; i < ps.size(); i++) 
					{
						cmsLog.info("处理第" + (i + 1) + "个节目包...");
						Object[] rs = (Object[]) ps.get(i);
						if (rs != null) 
						{
							ProgListDetail p = (ProgListDetail) rs[0];
							ProgPackage pp = (ProgPackage) rs[1];
							cmsLog.info("节目包ID：" + pp.getProductid());
							cmsLog.info("节目包名称：" + pp.getProductname());

							cmsLog.info("查询节目包所属的页面包装...");
							List portalPackages = portalPackageManager
									.getPortalPackagesByProductidColumnclassid(
											pp.getProductid(), 
											p.getColumnclassid()
											);
							if (portalPackages != null
								&& portalPackages.size() > 0) 
							{
								cmsLog.info("节目包所属的页面包装数量：" + portalPackages.size());
								for (int j = 0; j < portalPackages.size(); j++) 
								{
									Object[] ptprows = (Object[]) portalPackages.get(j);
									PortalPackage portalPackage = (PortalPackage) ptprows[0];
									cmsLog.info("页面包装ID：" + portalPackage.getPtpid());
									cmsLog.info("页面包装名称：" + portalPackage.getPtpname());
								}
							} 
							else 
							{
								// 节目包没有安排页面包装
								cmsLog.info("节目包没有所属的页面包装，加入返回列表。");
								ppOfNoPortalPackage.add(pp);
							}
						} 
						else 
						{
							cmsLog.warn("得到结果为空。");
						}
					}
				} 
				else 
				{
					cmsLog.info("电视剧栏目的节目包数量为 0。");
				}
				// --- 20100518 end ---

				if (ppOfNoPortalPackage != null
					&& ppOfNoPortalPackage.size() > 0) 
				{
					errorDetail += "存在节目包没有所属的页面包装的情况。节目包：\r\n";
					for (int i = 0; i < ppOfNoPortalPackage.size(); i++) 
					{
						ProgPackage p1 = (ProgPackage) ppOfNoPortalPackage.get(i);
						errorDetail += p1.getProductid() + " - " + p1.getProductname() + ";\r\n";
					}
					errorcount++;

					String str = "生成Portal失败。";
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
					cmsResultDto.setErrorDetail(errorDetail);
					cmsLog.warn(str);
				} 
				else 
				{
					cmsLog.info("得到所有允许发布的栏目...");
					List portalColumns = portalColumnManager
							.getAllPublishPortalColumnsByDefcatseq(rootPortalColumnDefcatseq);
					if (portalColumns == null || portalColumns.size() <= 0) 
					{
						String str = "未找到需要生成Portal的栏目，返回失败。";
						cmsLog.warn(str);
						errorDetail += str;
						errorcount++;
					} 
					else 
					{
						cmsLog.info("共有" + portalColumns.size() + "个栏目允许发布。");
						for (int i = 0; i < portalColumns.size(); i++) 
						{
							// 每个portalColumn需要生成一个js
							PortalColumn portalColumn = (PortalColumn) portalColumns.get(i);
							String pcDir = portalColumn.getParentdir();

							String strJs = ""; // JS字符串
							int pcErrorCount = 0; // 当前栏目错误计数

							// 1 - 复制海报文件和媒体key文件到播发库（一级库）
							cmsLog.info("处理第" + (i + 1) + "个栏目...");
							cmsLog.info("栏目code：" + portalColumn.getDefcatcode());
							cmsLog.info("栏目名称：" + portalColumn.getColumnname());

							// 0 头
							// 1 元数据
							// 2 附件
							// 3 子项头
							// 4 子项
							List headpjrs = portalJsRulesManager
									.getJsRulesByDatatypeDefcatcode((long) 0,
											portalColumn.getDefcatcode());
							if (headpjrs != null && headpjrs.size() > 0) 
							{
								PortalJsRules pjr = (PortalJsRules) headpjrs.get(0);

								strJs = pjr.getJsheadtag();
								strJs += "[\r\n";
							} 
							else 
							{
								cmsLog.warn("未查询到Js文件规则，跳过...");
								errorcount++;
								pcErrorCount++;
								strJs = "";
								continue;
							}
							List metpjrs = portalJsRulesManager
									.getJsRulesByDatatypeDefcatcode((long) 1,
											portalColumn.getDefcatcode());
							List accpjrs = portalJsRulesManager
									.getJsRulesByDatatypeDefcatcode((long) 2,
											portalColumn.getDefcatcode());
							List itemheadpjrs = portalJsRulesManager
									.getJsRulesByDatatypeDefcatcode((long) 3,
											portalColumn.getDefcatcode());
							List itempjrs = portalJsRulesManager
									.getJsRulesByDatatypeDefcatcode((long) 4,
											portalColumn.getDefcatcode());

							// 查询需要生成Portal的栏目(portalColumn)下所有的叶子节点栏目leafPcs
							cmsLog.info("查询得到栏目下有效的叶子节点栏目...");
							List leafPcs = portalColumnManager
									.getValidLeafPortalColumnsByDefcatseq(portalColumn
											.getDefcatseq());
							cmsLog.info("共有" + leafPcs.size() + "个叶子节点栏目。");

							// 判断需要生成Portal的栏目（portalColumn）是 富媒体 还是 点播栏目
							// --------------------- 视频类型 ---------------------------------------------
							if (portalColumn.getArchivedays() != null
								&& portalColumn.getArchivedays() == 0) 
							{
								// 视频类型
								// 复制海报、key文件到播发库
								cmsLog.info("栏目为视频类型，准备复制海报和key文件...");

								dealedPortalPackages = new ArrayList();
								dealedProgPackages = new ArrayList();

								for (int j = 0; j < leafPcs.size(); j++) 
								{
									// 为每个栏目的节目包添加海报文件（png），根据filecode、日期，查询
									// 目标路径：存储体ip＋存储体目录＋编单日期＋栏目目录（portalColumn的，不是leafPc的）

									PortalColumn leafPc = (PortalColumn) leafPcs.get(j);

									cmsLog.info("处理第" + (j + 1) + "个叶子节点栏目...");
									cmsLog.info("栏目ID：" + leafPc.getColumnclassid());
									cmsLog.info("栏目名称：" + leafPc.getColumnname());

									// 查询海报(PNG)文件的目标路径						
									// 得到当前栏目（leafPc）的上线的节目包列表
									// 根据日期、栏目，查询栏目单详细，得到节目包列表
									// 返回：List<Object[]>
									//			(ProgListDetail)Object[0]
									//			(ProgPackage)Object[1]
									//			(FunResource)Object[2]
									cmsLog.info("查询得到当前栏目的上线的节目包列表...");
									List progListDetails = progListDetailManager
											.getProgListDetailsProgPackagesByDateAndDefcatseq(
													date, leafPc.getDefcatseq());

									cmsLog.info("共有" + progListDetails.size()
											+ "个上线节目包。");
									for (int k = 0; k < progListDetails.size(); k++) 
									{
										// 得到当前栏目（leafPc）的上线的节目包（pp）
										Object[] rows = (Object[]) progListDetails.get(k);
										ProgListDetail pld = (ProgListDetail) rows[0];
										ProgPackage pp = (ProgPackage) rows[1];

										int copyPng = 1; // 复制海报文件返回结果
										int copySrt = 1; // 复制字幕文件返回结果
										int copyKey = 1; // 复制key文件返回结果
										String filenameTs = "";
										String filepathTs = "";
										String sourcePathTs = "";
										String destPath = "";
										String sourcePathPng = "";
										String destPathPng = "";
										String filenamePng = "";
										String sourcePathSrt = "";
										String destPathSrt = "";
										String filenameSrt = "";
										String sourcePathKey = "";
										String destPathKey = "";
										AmsStorage amsstdest = null;
										AmsStorageDir amsstddest = null;

										cmsLog.info("处理第" + (k + 1) + "个节目包...");
										cmsLog.info("栏目单详细ID：" + pld.getProglistdetailid());
										cmsLog.info("节目包ID：" + pp.getProductid());
										cmsLog.info("节目包名称：" + pp.getProductname());

										cmsLog.info("查询节目包主文件目标路径...");
										// 返回： List<ProgramFiles>
										List pfts = packageFilesManager
												.getProgramFilesesByProductidProgrank(
														pp.getProductid(),
														(long) 1);
										if (pfts != null && pfts.size() > 0) 
										{
											ProgramFiles pf = (ProgramFiles) pfts.get(0);

											filenameTs = pf.getFilename();

											// 返回：List
											// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
											// 2 - List<Object[]>
											// 		(AmsStorage)Object[0]
											// 		(AmsStoragePrgRel)Object[1]
											// 		(AmsStorageDir)Object[2]
											// 		(AmsStorageClass)Object[3]
											List sourcepaths = packageFilesManager
													.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
															pf.getProgfileid(),
															stclasscodeCaOnline,
															pp.getProductid());
											if (sourcepaths != null
												&& sourcepaths.size() >= 2) 
											{
												sourcePathTs = (String) sourcepaths.get(0);
												List list = (List) sourcepaths.get(1);
												if (list != null
													&& list.size() > 0) 
												{
													Object[] rows1 = (Object[]) list.get(0);
													AmsStoragePrgRel amsstpr = (AmsStoragePrgRel) rows1[1];
													filepathTs = amsstpr.getFilepath();
												}
											}

											// 返回：List
											// 1 - String 目标路径()  格式："smb://hc:hc@172.23.19.66/公用/"
											// 2 - List<Object[]>
											//			(AmsStorage)Object[0]
											//			(AmsStorageDir)Object[1]
											//			(AmsStorageClass)Object[2]
											List destpaths = packageFilesManager
													.getDestPathByFilecodeStclasscode(
															filecodeOnline,
															stclasscodeOnline);
											if (destpaths != null
												&& destpaths.size() >= 2) 
											{
												destPath = (String) destpaths.get(0);
												List rowdests = (List) destpaths.get(1);
												if (rowdests != null
													&& rowdests.size() > 0) 
												{
													Object[] rowdest = (Object[]) rowdests.get(0);
													amsstdest = (AmsStorage) rowdest[0];
													amsstddest = (AmsStorageDir) rowdest[1];
												}
											}
										}

										// 处理海报文件
										cmsLog.info("处理节目包的海报文件...");
										cmsLog.info("查询节目包的所有海报文件...");
										List pfPnglist = packageFilesManager.getProgramFilesesByProductidFiletype(
												pld.getProductid(), 
												filetypePng
												);
										cmsLog.info("节目包海报文件数量：" + pfPnglist.size());
										if(pfPnglist.size() > 0)
										{
											for(int m = 0; m < pfPnglist.size(); m++)
											{
												ProgramFiles pfPng = (ProgramFiles)pfPnglist.get(m);
												
												cmsLog.info("处理第" + (m+1) + "个海报文件...");
												cmsLog.info("文件ID：" + pfPng.getProgfileid());
												cmsLog.info("文件名：" + pfPng.getFilename());
												cmsLog.info("filecode：" + pfPng.getFilecode());
												
												cmsLog.info("查询节目包海报源路径...");
												// 根据节目包ID、filecode，查询栏目单详细和文件表，得到栏目下的海报文件（海报文件的源路径）
												// 返回： List<ProgramFiles>
												List listPng = packageFilesManager
														.getProgramFilesesByProductidFilecode(
																pld.getProductid(),
																pfPng.getFilecode()	// portalPicFilecode
																);
		
												if (listPng.size() > 0) //for(int m = 0; m < list1.size(); m++)
												{
													ProgramFiles pf = (ProgramFiles) listPng.get(0);
													filenamePng = pf.getFilename();
		
													if (destPath != null
														&& !destPath.equalsIgnoreCase("")) 
													{
														destPathPng = destPath;
														destPathPng = destPathPng.replace('\\', '/');
														destPathPng = fileopr.checkPathFormatRear(
																		destPathPng,
																		'/');
														destPathPng += filepathTs;
														destPathPng = destPathPng.replace('\\', '/');
														destPathPng = fileopr.checkPathFormatRear(
																		destPathPng,
																		'/');
														destPathPng += filenamePng;
													} 
													else 
													{
														cmsLog.warn("获得海报文件目标路径失败。");
														errorDetail += "获得海报文件目标路径失败。\r\n";
														destPathPng = "";
													}
		
													cmsLog.info("判断节目包海报是否已经迁移到播发库(一级库)...");
													cmsLog.info("查询节目包海报在播发库(一级库)的位置...");
													boolean pngOnlineExist = false;
													List sourcePathPngOnlineList = packageFilesManager
															.getSourcePathByProgfileidStclasscode(
																	pf.getProgfileid(),
																	stclasscodeOnline);
													if (sourcePathPngOnlineList != null
														&& sourcePathPngOnlineList.size() >= 2) 
													{
														String sourcePathPngOnline = (String) sourcePathPngOnlineList.get(0);
														if (sourcePathPngOnline != null
															&& !sourcePathPngOnline.equalsIgnoreCase("")) 
														{
															pngOnlineExist = true;
														}
													}
													if (pngOnlineExist == false) 
													{
														cmsLog.info("节目包海报不存在于播发库(一级库)，准备迁移海报文件...");
		
														// 查询海报1文件的源路径
														// 返回：List
														// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
														// 2 - List<Object[]>
														// 		(ProgramFiles)Object[0]
														// 		(AmsStorage)Object[1]
														// 		(AmsStoragePrgRel)Object[2]
														// 		(AmsStorageDir)Object[3]
														// 		(AmsStorageClass)Object[3]
														ProgramFiles programfilepng = null;
														AmsStorage amsstpng = null;
														AmsStoragePrgRel amsstprpng = null;
														List sourcePaths = packageFilesManager
																.getSourcePathByProgfileidStclasscode(
																		pf.getProgfileid(),
																		portalPicSourceStclasscode);
														if (sourcePaths != null
															&& sourcePaths.size() >= 2) 
														{
															sourcePathPng = (String) sourcePaths.get(0);
															List rowpngs = (List) sourcePaths.get(1);
															if (rowpngs != null
																&& rowpngs.size() > 0) 
															{
																Object[] rowpng = (Object[]) rowpngs.get(0);
																programfilepng = (ProgramFiles) rowpng[0];
																amsstpng = (AmsStorage) rowpng[1];
																amsstprpng = (AmsStoragePrgRel) rowpng[2];
															}
														} 
														else 
														{
															cmsLog.warn("获得海报文件源路径失败。");
															errorDetail += "获得海报文件源路径失败。\r\n";
															sourcePathPng = "";
														}
		
														if (sourcePathPng == null
															|| sourcePathPng.equalsIgnoreCase("")
															|| destPathPng == null
															|| destPathPng.equalsIgnoreCase("")) 
														{
															cmsLog.warn("海报文件源路径和目标路径查询失败，跳过...");
															errorcount++;
															pcErrorCount++;
														} 
														else 
														{
															// 复制海报文件 
															cmsLog.info("准备复制海报到播发库(一级库)...");
															cmsLog.info("源 - " + sourcePathPng);
															cmsLog.info("目标 - " + destPathPng);
															copyPng = fileopr.copyFileFromSmbToSmb(
																			sourcePathPng,
																			destPathPng);
															if (copyPng == 0) 
															{
																cmsLog.info("复制海报到播发库(一级库)成功。");
																cmsLog.info("准备为海报插入文件位置表记录...");
																if (amsstdest == null
																	|| amsstddest == null
																	|| programfilepng == null
																	|| amsstprpng == null) 
																{
																	cmsLog.warn("必要的参数未空，海报的文件位置表记录未保存。");
																	errorDetail += "必要的参数未空，海报的文件位置表记录未保存。\r\n";
																	errorcount++;
																	pcErrorCount++;
																} 
																else 
																{
																	AmsStoragePrgRel amsStoragePrgRel = new AmsStoragePrgRel();
																	amsStoragePrgRel.setStglobalid(
																			amsstdest.getStglobalid());
																	amsStoragePrgRel.setStdirglobalid(
																			amsstddest.getStdirglobalid());
																	amsStoragePrgRel.setFilepath(filepathTs);
																	amsStoragePrgRel.setFilename(
																			programfilepng.getFilename());
																	amsStoragePrgRel.setFtypeglobalid(
																			programfilepng.getFiletypeid());
																	amsStoragePrgRel.setPrglobalid(
																			pld.getProductid());
																	amsStoragePrgRel.setProgfileid(
																			pf.getProgfileid());
																	amsStoragePrgRel.setUploadtime(
																			amsstprpng.getUploadtime());
																	amsStoragePrgRel.setRemark("");
																	amsStoragePrgRel.setFiledate(
																			amsstprpng.getFiledate());
																	amsStoragePrgRel.setInputmanid(operatorId);
																	amsStoragePrgRel.setInputtime(new Date());
																	amsstorageprgrelManager.save(amsStoragePrgRel);
																	cmsLog.info("海报的文件位置表记录已经保存。文件位置ID："
																					+ amsStoragePrgRel.getRelid());
																}
															} 
															else 
															{
																cmsLog.warn("复制海报到播发库(一级库)失败。");
																errorDetail += "复制海报到播发库(一级库)失败。\r\n";
																errorcount++;
																pcErrorCount++;
															}
														}
													} 
													else 
													{
														cmsLog.info("节目包海报已经存在于播发库(一级库)，不操作...");
														copyPng = 0;
													}
												}
											}
										}
										else
										{
											cmsLog.info("节目包海报文件数量为0，不操作...");
											copyPng = 0;
										}
										
										// 处理字幕文件
										cmsLog.info("处理节目包的字幕文件...");
										cmsLog.info("查询节目包的所有字幕文件...");
										List pfSrtlist = packageFilesManager.getProgramFilesesByProductidFiletype(
												pld.getProductid(), 
												filetypeSrt
												);
										cmsLog.info("节目包字幕文件数量：" + pfSrtlist.size());
										if(pfSrtlist.size() > 0)
										{
											for(int m = 0; m < pfSrtlist.size(); m++)
											{
												ProgramFiles pfSrt = (ProgramFiles)pfSrtlist.get(m);
												
												cmsLog.info("处理第" + (m+1) + "个字幕文件...");
												cmsLog.info("文件ID：" + pfSrt.getProgfileid());
												cmsLog.info("文件名：" + pfSrt.getFilename());
												cmsLog.info("filecode：" + pfSrt.getFilecode());
												
												cmsLog.info("查询节目包字幕源路径...");
												// 根据节目包ID、filecode，查询栏目单详细和文件表，得到栏目下的海报文件（海报文件的源路径）
												// 返回： List<ProgramFiles>
												List listSrt = packageFilesManager
														.getProgramFilesesByProductidFilecode(
																pld.getProductid(),
																pfSrt.getFilecode()
																);
		
												if (listSrt.size() > 0) //for(int m = 0; m < list1.size(); m++)
												{
													ProgramFiles pf = (ProgramFiles) listSrt.get(0);
													filenameSrt = pf.getFilename();
		
													if (destPath != null
														&& !destPath.equalsIgnoreCase("")) 
													{
														destPathSrt = destPath;
														destPathSrt = destPathSrt.replace('\\', '/');
														destPathSrt = fileopr.checkPathFormatRear(
																destPathSrt,
																		'/');
														destPathSrt += filepathTs;
														destPathSrt = destPathSrt.replace('\\', '/');
														destPathSrt = fileopr.checkPathFormatRear(
																destPathSrt,
																		'/');
														destPathSrt += filenameSrt;
													} 
													else 
													{
														cmsLog.warn("获得字幕文件目标路径失败。");
														errorDetail += "获得字幕文件目标路径失败。\r\n";
														destPathSrt = "";
													}
		
													cmsLog.info("判断节目包字幕是否已经迁移到播发库(一级库)...");
													cmsLog.info("查询节目包字幕在播发库(一级库)的位置...");
													boolean srtOnlineExist = false;
													List sourcePathSrtOnlineList = packageFilesManager
															.getSourcePathByProgfileidStclasscode(
																	pf.getProgfileid(),
																	stclasscodeOnline);
													if (sourcePathSrtOnlineList != null
														&& sourcePathSrtOnlineList.size() >= 2) 
													{
														String sourcePathSrtOnline = (String) sourcePathSrtOnlineList.get(0);
														if (sourcePathSrtOnline != null
															&& !sourcePathSrtOnline.equalsIgnoreCase("")) 
														{
															srtOnlineExist = true;
														}
													}
													if (srtOnlineExist == false) 
													{
														cmsLog.info("节目包字幕不存在于播发库(一级库)，准备迁移字幕文件...");
		
														// 查询海报1文件的源路径
														// 返回：List
														// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
														// 2 - List<Object[]>
														// 		(ProgramFiles)Object[0]
														// 		(AmsStorage)Object[1]
														// 		(AmsStoragePrgRel)Object[2]
														// 		(AmsStorageDir)Object[3]
														// 		(AmsStorageClass)Object[3]
														ProgramFiles programfilesrt = null;
														AmsStorage amsstsrt = null;
														AmsStoragePrgRel amsstprsrt = null;
														List sourcePaths = packageFilesManager
																.getSourcePathByProgfileidStclasscode(
																		pf.getProgfileid(),
																		portalPicSourceStclasscode);
														if (sourcePaths != null
															&& sourcePaths.size() >= 2) 
														{
															sourcePathSrt = (String) sourcePaths.get(0);
															List rowpngs = (List) sourcePaths.get(1);
															if (rowpngs != null
																&& rowpngs.size() > 0) 
															{
																Object[] rowpng = (Object[]) rowpngs.get(0);
																programfilesrt = (ProgramFiles) rowpng[0];
																amsstsrt = (AmsStorage) rowpng[1];
																amsstprsrt = (AmsStoragePrgRel) rowpng[2];
															}
														} 
														else 
														{
															cmsLog.warn("获得字幕文件源路径失败。");
															errorDetail += "获得字幕文件源路径失败。\r\n";
															sourcePathSrt = "";
														}
		
														if (sourcePathSrt == null
															|| sourcePathSrt.equalsIgnoreCase("")
															|| destPathSrt == null
															|| destPathSrt.equalsIgnoreCase("")) 
														{
															cmsLog.warn("字幕文件源路径和目标路径查询失败，跳过...");
															errorcount++;
															pcErrorCount++;
														} 
														else 
														{
															// 复制海报文件 
															cmsLog.info("准备复制字幕到播发库(一级库)...");
															cmsLog.info("源 - " + sourcePathPng);
															cmsLog.info("目标 - " + destPathPng);
															copySrt = fileopr.copyFileFromSmbToSmb(
																			sourcePathSrt,
																			destPathSrt);
															if (copySrt == 0) 
															{
																cmsLog.info("复制字幕到播发库(一级库)成功。");
																cmsLog.info("准备为字幕插入文件位置表记录...");
																if (amsstdest == null
																	|| amsstddest == null
																	|| programfilesrt == null
																	|| amsstprsrt == null) 
																{
																	cmsLog.warn("必要的参数未空，字幕的文件位置表记录未保存。");
																	errorDetail += "必要的参数未空，字幕的文件位置表记录未保存。\r\n";
																	errorcount++;
																	pcErrorCount++;
																} 
																else 
																{
																	AmsStoragePrgRel amsStoragePrgRel = new AmsStoragePrgRel();
																	amsStoragePrgRel.setStglobalid(
																			amsstdest.getStglobalid());
																	amsStoragePrgRel.setStdirglobalid(
																			amsstddest.getStdirglobalid());
																	amsStoragePrgRel.setFilepath(filepathTs);
																	amsStoragePrgRel.setFilename(
																			programfilesrt.getFilename());
																	amsStoragePrgRel.setFtypeglobalid(
																			programfilesrt.getFiletypeid());
																	amsStoragePrgRel.setPrglobalid(
																			pld.getProductid());
																	amsStoragePrgRel.setProgfileid(
																			pf.getProgfileid());
																	amsStoragePrgRel.setUploadtime(
																			amsstprsrt.getUploadtime());
																	amsStoragePrgRel.setRemark("");
																	amsStoragePrgRel.setFiledate(
																			amsstprsrt.getFiledate());
																	amsStoragePrgRel.setInputmanid(operatorId);
																	amsStoragePrgRel.setInputtime(new Date());
																	amsstorageprgrelManager.save(amsStoragePrgRel);
																	cmsLog.info("字幕的文件位置表记录已经保存。文件位置ID："
																					+ amsStoragePrgRel.getRelid());
																}
															} 
															else 
															{
																cmsLog.warn("复制字幕到播发库(一级库)失败。");
																errorDetail += "复制字幕到播发库(一级库)失败。\r\n";
																errorcount++;
																pcErrorCount++;
															}
														}
													} 
													else 
													{
														cmsLog.info("节目包字幕已经存在于播发库(一级库)，不操作...");
														copySrt = 0;
													}
												}
											}
										}
										else
										{
											cmsLog.info("节目包字幕文件数量为0，不操作...");
											copySrt = 0;
										}
										
										// 处理key文件
										if (copyPng == 0 && copySrt == 0) 
										{
											cmsLog.info("查询Key文件源路径...");

											cmsLog.info("判断节目包是否加扰过，是否需要复制key文件到Portal...");
											if (pld.getETitle() == null
												|| pld.getETitle().equalsIgnoreCase("")) 
											{
												copyKey = 0;
												cmsLog.info("节目包未加扰过，不需要复制key文件到播发库(一级库)，继续...");
											} 
											else 
											{
												cmsLog.info("节目包加扰过，需要复制key文件到播发库(一级库)...");
												cmsLog.info("查询key文件源路径...");
												sourcePathKey = sourcePathTs.replaceAll(filenameTs,
																"");

												// 查询ts对应key文件的源路径
												if (sourcePathKey != null
													&& !sourcePathKey.equalsIgnoreCase("")) 
												{
													sourcePathKey = fileopr.checkPathFormatRear(
																	sourcePathKey,
																	'/');
													sourcePathKey += "key";
													cmsLog.info("key文件源路径 - " + sourcePathKey);
												} 
												else 
												{
													cmsLog.warn("媒体文件对应的Key的源路径为空。");
													errorDetail += "媒体文件对应的Key的源路径为空。\r\n";
													sourcePathKey = "";
												}

												cmsLog.info("查询key文件目标路径...");
												if (destPath != null
													&& !destPath.equalsIgnoreCase("")) 
												{
													destPathKey = destPath; // smb://administrator:1@172.23.19.213/CA/Portal/2010-01-27/
													destPathKey = destPathKey.replace('\\', '/');
													destPathKey = fileopr.checkPathFormatRear(
																	destPathKey,
																	'/');
													destPathKey += filepathTs;
													destPathKey = destPathKey.replace('\\', '/');
													destPathKey = fileopr.checkPathFormatRear(
																	destPathKey,
																	'/');
													destPathKey += "key";

													cmsLog.info("key文件目标路径 - " + destPathKey);
												} 
												else 
												{
													cmsLog.warn("媒体文件对应的Key的目标路径未空，返回失败。");
													errorDetail += "媒体文件对应的Key的目标路径未空，返回失败。\r\n";
													destPathKey = "";
												}

												if (sourcePathKey == null
													|| sourcePathKey.equalsIgnoreCase("")
													|| destPathKey == null
													|| destPathKey.equalsIgnoreCase("")) 
												{
													cmsLog.warn("海报文件源路径和目标路径查询失败，跳过...");
													//											errorDetail += "海报文件源路径和目标路径查询失败。\r\n";
													errorcount++;
													pcErrorCount++;
												} 
												else 
												{
													// 复制key文件到目标路径，从源路径
													cmsLog.info("准备复制Key文件到播发库(一级库)...");
													cmsLog.info("源 - " + sourcePathKey);
													cmsLog.info("目标 - " + destPathKey);
													copyKey = fileopr.copyFileFromSmbToSmb(
																	sourcePathKey,
																	destPathKey);
													if (copyKey == 0) 
													{
														cmsLog.info("复制Key文件到播发库(一级库)成功。");
													} 
													else 
													{
														cmsLog.warn("复制Key文件到播发库(一级库)失败。");
														errorDetail += "复制Key文件到播发库(一级库)失败。\r\n";
														errorcount++;
														pcErrorCount++;
													}
												}
											}
										}

										// 处理js
										if (copyPng == 0 
												&& copySrt == 0
												&& copyKey == 0) 
										{
											// 为单个节目包生成js字符串，
											// 如果是电视剧，则是为整部电视剧生成js字符串，包含有若干个节目包

											if (portalColumn.getDefcatcode()
													.equalsIgnoreCase("yx_dsj")
													|| leafPc.getDefcatcode()
															.equalsIgnoreCase(
																	"yx_dsj")) {
												// 查询页面包装，根据节目包ID、栏目ID
												// 返回：List<Object[]>
												// (PortalPackage)Object[0]
												// (PtpPgpRel)Object[1]
												cmsLog.info("查询节目包所属的页面包装...");
												List portalPackages = portalPackageManager
														.getPortalPackagesByProductidColumnclassid(
																pp.getProductid(),
																leafPc.getColumnclassid());
												if (portalPackages != null
														&& portalPackages
																.size() > 0) {
													Object[] ptprows = (Object[]) portalPackages
															.get(0);
													PortalPackage portalPackage = (PortalPackage) ptprows[0];
													// PortalPackage
													// portalPackage =
													// (PortalPackage)portalPackages.get(0);
													cmsLog.info("节目包属于页面包装，ID："
															+ portalPackage
																	.getPtpid());

													// 判断该节目包（页面包装）是否已经生成过js字符串
													boolean dealed = false;
													cmsLog.info("判断节目包是否被处理过...");
													if (dealedProgPackages
															.size() > 0) {
														for (int m = 0; m < dealedProgPackages
																.size(); m++) {
															ProgPackage dealedpp = (ProgPackage) dealedProgPackages
																	.get(m);

															if (pp.getProductid()
																	.equalsIgnoreCase(
																			dealedpp.getProductid())) {
																cmsLog.info("节目包已经被处理过。");
																dealed = true;
																break;
															}
														}
													}

													if (dealed == false) {
														cmsLog.info("节目包没有被处理过...");

														// 查询上线的同一个页面包装下的节目包
														// 查询节目包，根据页面包装ID、栏目ID
														// 返回：List<Object[]>
														// (ProgPackage)Object[0]
														// (PtpPgpRel)Object[1]
														cmsLog.info("查询页面包装下所有的节目包...");
														List ppackages = portalPackageManager
																.getProgPackagesByPtpidColumnclassid(
																		portalPackage
																				.getPtpid(),
																		leafPc.getColumnclassid());
														cmsLog.info("页面包装下共有"
																+ ppackages
																		.size()
																+ "个节目包...");
														if (ppackages != null
																&& ppackages
																		.size() > 0) {
															List readyPps = new ArrayList(); // 准备生成js的progpackages
															List readyPlds = new ArrayList(); // 准备生成js的progListDetails
															List readyPprs = new ArrayList(); // 准备生成js的PtpPgpRels

															cmsLog.info("得到第一集节目包，作为整个页面包装的信息来源（描述、海报）...");
															Object[] firstpprows = (Object[]) ppackages
																	.get(0);
															ProgPackage firstpp = (ProgPackage) firstpprows[0];
															cmsLog.info("节目包ID："
																	+ firstpp
																			.getProductid());
															cmsLog.info("节目包名称："
																	+ firstpp
																			.getProductname());

															cmsLog.info("为整个页面包装生成js字符串...");
															cmsLog.info("获取页面包装中上线的节目包...");
															for (int m = 0; m < ppackages
																	.size(); m++) {
																Object[] notdealedpprows = (Object[]) ppackages
																		.get(m);
																ProgPackage notdealedpp = (ProgPackage) notdealedpprows[0];
																PtpPgpRel notdealedPpr = (PtpPgpRel) notdealedpprows[1];

																List plds = progListDetailManager
																		.getProgListDetailsByProductidColumnclassid(
																				notdealedpp
																						.getProductid(),
																				leafPc.getColumnclassid());
																if (plds != null
																		&& plds.size() > 0) {
																	for (int h = 0; h < plds
																			.size(); h++) {
																		ProgListDetail notdealedpld = (ProgListDetail) plds
																				.get(h);

																		// 比较上线日期、下线日期
																		Date portalDate = fileopr
																				.convertStringToDate(
																						date
																								+ " 00:00:00",
																						"yyyy-MM-dd HH:mm:ss");
																		if (notdealedpld
																				.getValidFrom()
																				.compareTo(
																						portalDate) <= 0
																				&& notdealedpld
																						.getValidTo()
																						.compareTo(
																								portalDate) > 0) {
																			cmsLog.info("将上线节目包加入准备生成js字符串列表...");
																			cmsLog.info("节目包ID："
																					+ notdealedpp
																							.getProductid());
																			cmsLog.info("节目包名称："
																					+ notdealedpp
																							.getProductname());

																			readyPps.add(notdealedpp);
																			readyPlds
																					.add(notdealedpld);
																			readyPprs
																					.add(notdealedPpr);
																			break;
																		} else {
																			cmsLog.info("判断栏目单详细，非上线记录，继续...");
																		}
																	}
																}
															}
															// 生成js字符串，根据firstpp、readyPps、readyPlds
															cmsLog.info("生成js字符串...");
															strJs += getPackageJsByPortalPackage(
																	leafPc.getDefcatcode(),
																	firstpp.getPpxml(), // 电视剧第一集的ppxml，取海报和描述
																	readyPps, // 准备生成js的progpackages，包含ppxml，取文件目录信息
																	readyPlds, // 准备生成js的progListDetails，包含加扰产品列表
																	readyPprs // 准备生成js的PtpPgpRels，包含分集数);
															);
															strJs += ",";

															cmsLog.info("将页面包装下处理过的节目包加入已处理列表...");
															for (int m = 0; m < readyPps
																	.size(); m++) {
																ProgPackage dealedpp = (ProgPackage) readyPps
																		.get(m);
																dealedProgPackages
																		.add(dealedpp);
															}
														}
													} else {
														cmsLog.info("节目包已经被处理过了，不重复操作，跳过...");
													}
												} else {
													cmsLog.info("节目包不属于页面包装...");
													cmsLog.info("为节目包生成js字符串...");

													strJs += getPackageJs(
															pld,
															pld.getETitle(),
															portalColumn
																	.getDefcatcode(),
															pp.getPpxml(),
															metpjrs, accpjrs,
															itemheadpjrs,
															itempjrs);

													strJs += ",";
												}
											} else {
												strJs += getPackageJs(
														pld,
														pld.getETitle(),
														portalColumn
																.getDefcatcode(),
														pp.getPpxml(), metpjrs,
														accpjrs, itemheadpjrs,
														itempjrs);

												strJs += ",";
											}
										}
										else
										{
											cmsLog.warn("复制节目包的文件失败，不生成js文件。");
											cmsLog.info("copyPng:" + copyPng);
											cmsLog.info("copySrt:" + copySrt);
											cmsLog.info("copyKey:" + copyKey);
										}
									}
								}
								dealedPortalPackages = null;
								dealedProgPackages = null;
							}

							// --------------------- 富媒体类型 ---------------------------------------------
							else 
							{
								
								//TODO 富媒体类型
								// 富媒体类型
								// 复制海报到播发库
								// 复制主文件（zip）到播发库，并解压缩
								cmsLog.info("栏目为富媒体类型，准备复制海报和主文件（ZIP）...");

								for (int j = 0; j < leafPcs.size(); j++) 
								{
									// 为每个栏目的节目包添加海报文件（png），根据filecode、日期，查询
									// 目标路径：存储体ip＋存储体目录＋编单日期＋栏目目录（portalColumn的，不是leafPc的）

									PortalColumn leafPc = (PortalColumn) leafPcs.get(j);

									cmsLog.info("处理第" + (j + 1) + "个叶子节点栏目...");
									cmsLog.info("栏目ID：" + leafPc.getColumnclassid());
									cmsLog.info("栏目名称：" + leafPc.getColumnname());

									// 查询海报(PNG)文件的目标路径						
									// 得到当前栏目（leafPc）的上线的节目包列表
									// 根据日期、栏目，查询栏目单详细，得到节目包列表
									// 返回：List<Object[]>
									//			(ProgListDetail)Object[0]
									//			(ProgPackage)Object[1]
									//			(FunResource)Object[2]
									cmsLog.info("查询得到当前栏目的上线的节目包列表...");
									List progListDetails = progListDetailManager
											.getProgListDetailsProgPackagesByDateAndDefcatseq(
													date, leafPc.getDefcatseq());

									cmsLog.info("共有" + progListDetails.size()
											+ "个上线节目包。");
									for (int k = 0; k < progListDetails.size(); k++) 
									{
										// 得到当前栏目（leafPc）的上线的节目包（pp）
										Object[] rows = (Object[]) progListDetails.get(k);
										ProgListDetail pld = (ProgListDetail) rows[0];
										ProgPackage pp = (ProgPackage) rows[1];

										int retCopyPng = 1; // 复制海报返回结果
										int retCopyZip = 1; // 复制Zip文件返回结果
										int retUnzip = 1; // 解压缩返回结果
										String filenameZip = "";
										String filepathZip = "";
										String sourcePathZip = "";
										String destPathZip = "";
										String destPath = "";
										String sourcePathPng = "";
										String destPathPng = "";
										String filenamePng = "";
										String filecodeZip = "";
										AmsStorage amsstdest = null;
										AmsStorage amsStorageDesc = null;	// 一级库目标存储体
										AmsStorageDir amsstddest = null;
										ProgramFiles programFilesZip = null;
										AmsStoragePrgRel amsstprZip = null;

										cmsLog.info("处理第" + (k + 1) + "个节目包...");
										cmsLog.info("栏目单详细ID：" + pld.getProglistdetailid());
										cmsLog.info("节目包ID：" + pp.getProductid());
										cmsLog.info("节目包名称：" + pp.getProductname());

										cmsLog.info("查询节目包主文件目标路径...");
										// 返回： List<ProgramFiles>
										List pfts = packageFilesManager
												.getProgramFilesesByProductidProgrank(
														pp.getProductid(),
														(long) 1);
										if (pfts != null && pfts.size() > 0) 
										{
											ProgramFiles pf = (ProgramFiles) pfts.get(0);
											programFilesZip = pf;
											filecodeZip = pf.getFilecode();
											filenameZip = pf.getFilename();

											// 返回：List
											// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
											// 2 - List<Object[]>
											// 		(AmsStorage)Object[0]
											// 		(AmsStoragePrgRel)Object[1]
											// 		(AmsStorageDir)Object[2]
											// 		(AmsStorageClass)Object[3]
											List sourcepaths = packageFilesManager
													.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
															pf.getProgfileid(),
															stclasscodeCaOnline,
															pp.getProductid());
											if (sourcepaths != null
												&& sourcepaths.size() >= 2) 
											{
												sourcePathZip = (String) sourcepaths.get(0);
												List list = (List) sourcepaths.get(1);
												if (list != null
													&& list.size() > 0) 
												{
													Object[] rows1 = (Object[]) list.get(0);
													amsstdest = (AmsStorage) rows1[0];
													amsstddest = (AmsStorageDir) rows1[2];
													amsstprZip = (AmsStoragePrgRel) rows1[1];
													filepathZip = amsstprZip.getFilepath();
												}
											} 
											else 
											{
												cmsLog.warn("获得Zip文件源路径失败。");
												errorDetail += "获得Zip文件源路径失败。\r\n";
												sourcePathZip = "";
											}

											// 返回：List
											// 1 - String 目标路径()  格式："smb://hc:hc@172.23.19.66/公用/"
											// 2 - List<Object[]>
											//			(AmsStorage)Object[0]
											//			(AmsStorageDir)Object[1]
											//			(AmsStorageClass)Object[2]
											List destpaths = packageFilesManager
													.getDestPathByFilecodeStclasscode(
															pf.getFilecode(),
															stclasscodeOnline);
											if (destpaths != null
													&& destpaths.size() >= 2) 
											{
												destPath = (String) destpaths.get(0);
												List<Object[]> objectList = (List<Object[]>) destpaths.get(1);
												amsStorageDesc = (AmsStorage) objectList.get(0)[0];
											} 
											else 
											{
												cmsLog.warn("获得目标路径失败。");
												errorDetail += "获得目标路径失败。\r\n";
												destPath = "";
											}
										}

										// 处理海报文件
										cmsLog.info("判断富媒体zip文件是否已经迁移到播发库(一级库)...");
										cmsLog.info("查询富媒体zip文件在播发库(一级库)的位置...");
										boolean pngOnlineExist = false;
										List sourcepathPngOnlineList = packageFilesManager
												.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
														portalPicFilecode,
														stclasscodeOnline, 
														pp.getProductid());
										if (sourcepathPngOnlineList != null
												&& sourcepathPngOnlineList.size() >= 2) 
										{
											String sourcepathPngOnline = (String) sourcepathPngOnlineList.get(0);
											if (sourcepathPngOnline != null
												&& !sourcepathPngOnline.equalsIgnoreCase("")) 
											{
												pngOnlineExist = true;
											}
										}
										if (pngOnlineExist == false) 
										{
											cmsLog.info("节目包海报不存在于播发库(一级库)...");
											ProgramFiles programfilepng = null;
											AmsStorage amsstpng = null;
											AmsStoragePrgRel amsstprpng = null;

											cmsLog.info("查询节目包海报源路径...");
											// 根据节目包ID、filecode，查询栏目单详细和文件表，得到栏目下的海报文件（海报文件的源路径）
											// 返回： List<ProgramFiles>
											List listPng = packageFilesManager
													.getProgramFilesesByProductidFilecode(
															pld.getProductid(),
															portalPicFilecode);

											if (listPng.size() > 0) //for(int m = 0; m < list1.size(); m++)
											{
												ProgramFiles pf = (ProgramFiles) listPng.get(0);
												programfilepng = pf;
												filenamePng = pf.getFilename();

												if (destPath != null
													&& !destPath.equalsIgnoreCase("")) 
												{
													destPathPng = destPath;
													destPathPng = destPathPng.replace('\\', '/');
													destPathPng = fileopr.checkPathFormatRear(
																	destPathPng,
																	'/');
													destPathPng += filepathZip;
													destPathPng = destPathPng
															.replace('\\', '/');
													destPathPng = fileopr.checkPathFormatRear(
																	destPathPng,
																	'/');
													destPathPng += filenamePng;
												} 
												else 
												{
													cmsLog.warn("获得海报文件目标路径失败。");
													errorDetail += "获得海报文件目标路径失败。\r\n";
													destPathPng = "";
												}

												// 查询海报1文件的源路径
												// 返回：List
												// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
												// 2 - List<Object[]>
												// 		(ProgramFiles)Object[0]
												// 		(AmsStorage)Object[1]
												// 		(AmsStoragePrgRel)Object[2]
												// 		(AmsStorageDir)Object[3]
												// 		(AmsStorageClass)Object[3]
												List sourcePaths = packageFilesManager
														.getSourcePathByProgfileidStclasscode(
																pf.getProgfileid(),
																portalPicSourceStclasscode);
												if (sourcePaths != null
													&& sourcePaths.size() >= 2) 
												{
													sourcePathPng = (String) sourcePaths.get(0);
													List sourcepathpngrows = (List) sourcePaths
															.get(1);
													if (sourcepathpngrows != null
														&& sourcepathpngrows.size() > 0) 
													{
														Object[] sourcepathpngrow = (Object[]) sourcepathpngrows
																.get(0);
														amsstprpng = (AmsStoragePrgRel) sourcepathpngrow[2];
													}
												} 
												else 
												{
													cmsLog.warn("获得海报文件源路径失败。");
													errorDetail += "获得海报文件源路径失败。\r\n";
													sourcePathPng = "";
												}

												if (sourcePathPng == null
													|| sourcePathPng.equalsIgnoreCase("")
													|| destPathPng == null
													|| destPathPng.equalsIgnoreCase("")) 
												{
													cmsLog.warn("海报文件源路径和目标路径查询失败，跳过...");
													errorcount++;
													pcErrorCount++;
												} 
												else 
												{
													// 复制海报文件 
													cmsLog.info("准备复制海报到播发库(一级库)...");
													cmsLog.info("源 - "
															+ sourcePathPng);
													cmsLog.info("目标 - "
															+ destPathPng);
													retCopyPng = fileopr.copyFileFromSmbToSmb(
																	sourcePathPng,
																	destPathPng);
													if (retCopyPng == 0) 
													{
														if (amsstdest == null
															|| amsstddest == null
															|| programfilepng == null
															|| amsstprpng == null) 
														{
															cmsLog.warn("必要的参数为空，海报的文件位置表记录未保存。");
															errorDetail += "必要的参数为空，海报的文件位置表记录未保存。\r\n";
															errorcount++;
															pcErrorCount++;
														} 
														else 
														{
															AmsStoragePrgRel amsStoragePrgRel = new AmsStoragePrgRel();
															amsStoragePrgRel
																	.setStglobalid(amsStorageDesc
																			.getStglobalid());
															amsStoragePrgRel
																	.setStdirglobalid(amsstddest
																			.getStdirglobalid());
															amsStoragePrgRel
																	.setFilepath(filepathZip);
															amsStoragePrgRel
																	.setFilename(programfilepng
																			.getFilename());
															amsStoragePrgRel
																	.setFtypeglobalid(programfilepng
																			.getFiletypeid());
															amsStoragePrgRel
																	.setPrglobalid(pld
																			.getProductid());
															amsStoragePrgRel
																	.setProgfileid(pf
																			.getProgfileid());
															amsStoragePrgRel
																	.setUploadtime(amsstprpng
																			.getUploadtime());
															amsStoragePrgRel
																	.setRemark("生成Portal");
															amsStoragePrgRel
																	.setFiledate(amsstprpng
																			.getFiledate());
															amsStoragePrgRel
																	.setInputmanid(operatorId);
															amsStoragePrgRel
																	.setInputtime(new Date());
															amsstorageprgrelManager
																	.save(amsStoragePrgRel);
															cmsLog.info("海报的文件位置表记录已经保存。文件位置ID："
																			+ amsStoragePrgRel
																					.getRelid());
														}
													} 
													else 
													{
														cmsLog.warn("复制海报到播发库(一级库)失败。");
														errorDetail += "复制海报到播发库(一级库)失败。\r\n";
														errorcount++;
														pcErrorCount++;
													}
												}
											} else {	// HuangBo addition by 2010年8月25日 10时30分
												retCopyPng = 0;
											}
										} 
										else 
										{
											cmsLog.info("节目包海报已经存在于播发库(一级库)，不操作...");
											retCopyPng = 0;
										}

										// 处理富媒体zip文件，先复制，后解压
										if (retCopyPng == 0) 
										{
											cmsLog.info("复制节目包的海报成功，准备复制富媒体Zip文件到播发库(一级库)...");

											cmsLog.info("判断富媒体zip文件是否已经迁移到播发库(一级库)...");
											cmsLog.info("查询富媒体zip文件在播发库(一级库)的位置...");
											boolean zipOnlineExist = false;
											List sourcepathZipOnlineList = packageFilesManager
													.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
															programFilesZip.getProgfileid(),
															stclasscodeOnline,
															pp.getProductid());
											if (sourcepathZipOnlineList != null
													&& sourcepathZipOnlineList
															.size() >= 2) 
											{
												String sourcepathZipOnline = (String) sourcepathZipOnlineList
														.get(0);
												if (sourcepathZipOnline != null
													&& !sourcepathZipOnline.equalsIgnoreCase("")) 
												{
													zipOnlineExist = true;
												}
											}
											if (zipOnlineExist == false) 
											{
												cmsLog.info("富媒体的zip文件不存在于播发库(一级库)...");
												cmsLog.info("查询Zip文件目标路径...");
												if (destPath != null
													&& !destPath.equalsIgnoreCase("")) 
												{
													destPathZip = destPath; // smb://administrator:1@172.23.19.213/CA/Portal/2010-01-27/
													destPathZip = destPathZip
															.replace('\\', '/');
													destPathZip = fileopr
															.checkPathFormatRear(
																	destPathZip,
																	'/');
													destPathZip += filepathZip;
													destPathZip = destPathZip
															.replace('\\', '/');
													destPathZip = fileopr
															.checkPathFormatRear(
																	destPathZip,
																	'/');
													destPathZip += filenameZip;
												} 
												else 
												{
													cmsLog.warn("富媒体zip文件的目标路径未空，返回失败。");
													errorDetail += "富媒体zip文件的目标路径未空，返回失败。\r\n";
													destPathZip = "";
												}

												if (sourcePathZip == null
													|| sourcePathZip.equalsIgnoreCase("")
													|| destPathZip == null
													|| destPathZip.equalsIgnoreCase("")) 
												{
													cmsLog.warn("富媒体zip文件源路径和目标路径查询失败，跳过...");
													errorcount++;
													pcErrorCount++;
												} 
												else 
												{
													// 复制key文件到目标路径，从源路径
													cmsLog.info("准备复制富媒体Zip文件到播发库(一级库)...");
													cmsLog.info("源 - "
															+ sourcePathZip);
													cmsLog.info("目标 - "
															+ destPathZip);
//													retCopyZip = fileopr
//															.copyFileFromSmbToSmb(
//																	sourcePathZip,
//																	destPathZip);
													/**
													 * HuangBo update by 2010年8月25日 10时50分
													 * 此处富媒体不由cms操作, 改由视频文件同样的方式, 采用迁移模块来操作.
													 */
													retCopyZip = 0;
													
													if (retCopyZip == 0) 
													{
														cmsLog.info("复制富媒体Zip文件到播发库(一级库)成功。");

														String dirPathSmb = "";
														dirPathSmb = destPathZip.substring(
																		0,
																		destPathZip.lastIndexOf("/"));
														cmsLog.info("准备解压缩富媒体Zip文件在播发库(一级库)...");
														cmsLog.info("源 - "
																+ destPathZip);
														cmsLog.info("目标 - "
																+ dirPathSmb);
//														retUnzip = fileopr.unZipSmb(
//																		destPathZip,
//																		dirPathSmb);
														/**
														 * HuangBo update by 2010年8月25日 11时19分
														 * 此处不复制压缩文件, 也无需进行解压缩..
														 */
														retUnzip = 0;
														
														if (retUnzip == 0) 
														{
															cmsLog.info("解压缩富媒体Zip文件在播发库(一级库)成功。");
															cmsLog.info("准备为富媒体zip文件插入文件位置表记录...");
															if (amsstdest == null
																|| amsstddest == null
																|| programFilesZip == null
																|| amsstprZip == null) 
															{
																cmsLog.warn("必要的参数未空，海报的文件位置表记录未保存。");
																errorDetail += "必要的参数未空，海报的文件位置表记录未保存。\r\n";
																errorcount++;
																pcErrorCount++;
															} 
															else 
															{
																AmsStoragePrgRel amsStoragePrgRel = new AmsStoragePrgRel();
																amsStoragePrgRel
																		.setStglobalid(amsstdest
																				.getStglobalid());
																amsStoragePrgRel
																		.setStdirglobalid(amsstddest
																				.getStdirglobalid());
																amsStoragePrgRel
																		.setFilepath(filepathZip);
																/**
																 * HuangBo update by 2010年8月25日 13时59分
																 * 富媒体文件名.zip修改为目录形式, 不采用压缩和解压缩
																 */
																amsStoragePrgRel
																		.setFilename(programFilesZip
																				.getFilename().substring(0, programFilesZip
																				.getFilename().lastIndexOf(".")) + "/");
																amsStoragePrgRel
																		.setFtypeglobalid(programFilesZip
																				.getFiletypeid());
																amsStoragePrgRel
																		.setPrglobalid(pld
																				.getProductid());
																amsStoragePrgRel
																		.setProgfileid(programFilesZip
																				.getProgfileid());
																amsStoragePrgRel
																		.setUploadtime(amsstprZip
																				.getUploadtime());
																amsStoragePrgRel
																		.setRemark("生成Portal");
																amsStoragePrgRel
																		.setFiledate(amsstprZip
																				.getFiledate());
																amsStoragePrgRel
																		.setInputmanid(operatorId);
																amsStoragePrgRel
																		.setInputtime(new Date());
																/**
																 * HuangBo update by 2010年8月25日 14时1分
																 * 此处不需要再保存位置表, 该记录在加扰完成后就已经生成.. 因此此处不需要再进行文件位置记录
																 */
//																amsstorageprgrelManager
//																		.save(amsStoragePrgRel);
																cmsLog.info("富媒体zip文件位置表记录已经保存。文件位置ID："
																				+ amsStoragePrgRel
																						.getRelid());
															}
														} 
														else 
														{
															cmsLog.warn("解压缩富媒体Zip文件在播发库(一级库)失败。");
															errorDetail += "解压缩富媒体Zip文件在播发库(一级库)失败。\r\n";
															errorcount++;
															pcErrorCount++;
														}
													} 
													else 
													{
														cmsLog.warn("复制富媒体Zip文件到播发库(一级库)失败。");
														errorDetail += "复制富媒体Zip文件到播发库(一级库)失败。\r\n";
														errorcount++;
														pcErrorCount++;
													}
												}
											} 
											else 
											{
												cmsLog.info("富媒体的zip文件已经存在于播发库(一级库)，不操作...");
												retCopyZip = 0;
												retUnzip = 0;
											}
										}

										// 处理js
										if (retCopyPng == 0 && retCopyZip == 0
												&& retUnzip == 0) {
											cmsLog.info("富媒体节目包生成js文件。");
											/**
											 * HuangBo update by 2010年8月25日 11时30分
											 * 增加富媒体需要生成js
											 */
											// 为单个节目包生成js字符串，
											// 如果是电视剧，则是为整部电视剧生成js字符串，包含有若干个节目包

											if (portalColumn.getDefcatcode()
													.equalsIgnoreCase("yx_dsj")
													|| leafPc.getDefcatcode()
															.equalsIgnoreCase(
																	"yx_dsj")) {
												// 查询页面包装，根据节目包ID、栏目ID
												// 返回：List<Object[]>
												// (PortalPackage)Object[0]
												// (PtpPgpRel)Object[1]
												cmsLog.info("查询节目包所属的页面包装...");
												List portalPackages = portalPackageManager
														.getPortalPackagesByProductidColumnclassid(
																pp.getProductid(),
																leafPc.getColumnclassid());
												if (portalPackages != null
														&& portalPackages
																.size() > 0) {
													Object[] ptprows = (Object[]) portalPackages
															.get(0);
													PortalPackage portalPackage = (PortalPackage) ptprows[0];
													// PortalPackage
													// portalPackage =
													// (PortalPackage)portalPackages.get(0);
													cmsLog.info("节目包属于页面包装，ID："
															+ portalPackage
																	.getPtpid());

													// 判断该节目包（页面包装）是否已经生成过js字符串
													boolean dealed = false;
													cmsLog.info("判断节目包是否被处理过...");
													if (dealedProgPackages
															.size() > 0) {
														for (int m = 0; m < dealedProgPackages
																.size(); m++) {
															ProgPackage dealedpp = (ProgPackage) dealedProgPackages
																	.get(m);

															if (pp.getProductid()
																	.equalsIgnoreCase(
																			dealedpp.getProductid())) {
																cmsLog.info("节目包已经被处理过。");
																dealed = true;
																break;
															}
														}
													}

													if (dealed == false) {
														cmsLog.info("节目包没有被处理过...");

														// 查询上线的同一个页面包装下的节目包
														// 查询节目包，根据页面包装ID、栏目ID
														// 返回：List<Object[]>
														// (ProgPackage)Object[0]
														// (PtpPgpRel)Object[1]
														cmsLog.info("查询页面包装下所有的节目包...");
														List ppackages = portalPackageManager
																.getProgPackagesByPtpidColumnclassid(
																		portalPackage
																				.getPtpid(),
																		leafPc.getColumnclassid());
														cmsLog.info("页面包装下共有"
																+ ppackages
																		.size()
																+ "个节目包...");
														if (ppackages != null
																&& ppackages
																		.size() > 0) {
															List readyPps = new ArrayList(); // 准备生成js的progpackages
															List readyPlds = new ArrayList(); // 准备生成js的progListDetails
															List readyPprs = new ArrayList(); // 准备生成js的PtpPgpRels

															cmsLog.info("得到第一集节目包，作为整个页面包装的信息来源（描述、海报）...");
															Object[] firstpprows = (Object[]) ppackages
																	.get(0);
															ProgPackage firstpp = (ProgPackage) firstpprows[0];
															cmsLog.info("节目包ID："
																	+ firstpp
																			.getProductid());
															cmsLog.info("节目包名称："
																	+ firstpp
																			.getProductname());

															cmsLog.info("为整个页面包装生成js字符串...");
															cmsLog.info("获取页面包装中上线的节目包...");
															for (int m = 0; m < ppackages
																	.size(); m++) {
																Object[] notdealedpprows = (Object[]) ppackages
																		.get(m);
																ProgPackage notdealedpp = (ProgPackage) notdealedpprows[0];
																PtpPgpRel notdealedPpr = (PtpPgpRel) notdealedpprows[1];

																List plds = progListDetailManager
																		.getProgListDetailsByProductidColumnclassid(
																				notdealedpp
																						.getProductid(),
																				leafPc.getColumnclassid());
																if (plds != null
																		&& plds.size() > 0) {
																	for (int h = 0; h < plds
																			.size(); h++) {
																		ProgListDetail notdealedpld = (ProgListDetail) plds
																				.get(h);

																		// 比较上线日期、下线日期
																		Date portalDate = fileopr
																				.convertStringToDate(
																						date
																								+ " 00:00:00",
																						"yyyy-MM-dd HH:mm:ss");
																		if (notdealedpld
																				.getValidFrom()
																				.compareTo(
																						portalDate) <= 0
																				&& notdealedpld
																						.getValidTo()
																						.compareTo(
																								portalDate) > 0) {
																			cmsLog.info("将上线节目包加入准备生成js字符串列表...");
																			cmsLog.info("节目包ID："
																					+ notdealedpp
																							.getProductid());
																			cmsLog.info("节目包名称："
																					+ notdealedpp
																							.getProductname());

																			readyPps.add(notdealedpp);
																			readyPlds
																					.add(notdealedpld);
																			readyPprs
																					.add(notdealedPpr);
																			break;
																		} else {
																			cmsLog.info("判断栏目单详细，非上线记录，继续...");
																		}
																	}
																}
															}
															// 生成js字符串，根据firstpp、readyPps、readyPlds
															cmsLog.info("生成js字符串...");
															strJs += getPackageJsByPortalPackage(
																	leafPc.getDefcatcode(),
																	firstpp.getPpxml(), // 电视剧第一集的ppxml，取海报和描述
																	readyPps, // 准备生成js的progpackages，包含ppxml，取文件目录信息
																	readyPlds, // 准备生成js的progListDetails，包含加扰产品列表
																	readyPprs // 准备生成js的PtpPgpRels，包含分集数);
															);
															strJs += ",";

															cmsLog.info("将页面包装下处理过的节目包加入已处理列表...");
															for (int m = 0; m < readyPps
																	.size(); m++) {
																ProgPackage dealedpp = (ProgPackage) readyPps
																		.get(m);
																dealedProgPackages
																		.add(dealedpp);
															}
														}
													} else {
														cmsLog.info("节目包已经被处理过了，不重复操作，跳过...");
													}
												} else {
													cmsLog.info("节目包不属于页面包装...");
													cmsLog.info("为节目包生成js字符串...");

													strJs += getPackageJs(
															pld,
															pld.getETitle(),
															portalColumn
																	.getDefcatcode(),
															pp.getPpxml(),
															metpjrs, accpjrs,
															itemheadpjrs,
															itempjrs);

													strJs += ",";
												}
											} else {
												strJs += getPackageJs(
														pld,
														pld.getETitle(),
														portalColumn
																.getDefcatcode(),
														pp.getPpxml(), metpjrs,
														accpjrs, itemheadpjrs,
														itempjrs);

												strJs += ",";
											}
										}
									}
								}
							}

							// -------------------- 生成js文件、保存数据库、发送栏目单状态 -----------------
							if (strJs != null && !strJs.equalsIgnoreCase("")) 
							{
								// Js结束字符串
								if (portalColumn.getArchivedays() != null
										&& (portalColumn.getArchivedays() == 0
										|| 1 == portalColumn.getArchivedays())) {
									if (strJs.charAt(strJs.length() - 1) == ',') {
										strJs = strJs.substring(0, strJs
												.length() - 1);
									}
									strJs += "];";
								} else {
									strJs = "";
								}
							}

							// ------------ 为了300GB预装内容 ----------------------------------
							if (strJs != null) {
								strJs = JSPatch.patch(strJs, portalColumn
										.getDefcatcode());
							}
							// ------------- end ---------------------------------------------

							// 2 - 生成js文件到播发库（一级库），数据都是根据JS文件生成规则表（TPORTALJSRULES）从节目包xml中获得，
							if (pcErrorCount == 0 && strJs != null
								&& !strJs.equalsIgnoreCase("")) 
							{
								int retCreateJs = 1;

								// strJs视频类栏目需要生成js文件
								// 生成js文件，js文件如何命名
								// 目标路径：存储体ip＋存储体目录＋编单日期＋栏目目录（portalColumn的）
								// 返回：List
								// 1 - String 目标路径()
								// 2 - List<Object[]>
								//			(AmsStorage)Object[0]
								//			(AmsStorageDir)Object[1]
								//			(AmsStorageClass)Object[2]
								String jsDestPath = "";
								String jsFileName = portalColumn
										.getPublishfilename();
								List destpaths = packageFilesManager
										.getDestPathByFilecodeStclasscode(
												portalJsFilecode,
												stclasscodeOnline);
								if (destpaths != null && destpaths.size() >= 2) 
								{
									jsDestPath = (String) destpaths.get(0);
									jsDestPath = jsDestPath.replace('\\', '/');
									jsDestPath = fileopr.checkPathFormatRear(
											jsDestPath, '/');
									jsDestPath += date;
									jsDestPath += "/";
									jsDestPath += jsFileName;
								}

								// 生成js文件，目标路径jsDestPath（含文件名后缀），内容strJs
								cmsLog.warn("此处没有使用事务处理，有空优化。");
								cmsLog.info("准备生成js文件...");
								cmsLog.info("目标 - " + jsDestPath);
								retCreateJs = fileopr.createSmbFileGb2312(
										jsDestPath, strJs);
								if (retCreateJs == 0) 
								{
									// 生成新记录到progListFile
									cmsLog.info("生成js文件成功，准备生成数据库记录(ProgListFile)...");
									ProgListFile progListFile = new ProgListFile();
									progListFile
											.setScheduledate(convertDateToScheduleDate(date));
									progListFile.setColumnclassid(portalColumn
											.getColumnclassid());
									progListFile.setFilename(jsFileName);
									progListFile.setFiletype(Long.valueOf(1)); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
									progListFile.setDate1(new Date());
									progListFile.setState1(Long.valueOf(0));
									progListFile.setIdAct("");
									progListFile.setColumnjs(strJs);
									progListFile.setInputmanid(operatorId);
									progListFile.setInputtime(new Date());

									CmsResultDto c = cmsTransactionManager
											.saveProgListFile(
													progListFileManager,
													progListFile, date,
													portalColumn.getDefcatseq());

									if ((long) c.getResultCode() == (long) 0) 
									{
										cmsLog.info("生成数据库记录(ProgListFile)成功，ID："
														+ progListFile
																.getColumnfileid());

										cmsLog.info("准备发送栏目单分表活动("
												+ currentIdAct + ")，到一下活动...");
										PortalColumnServiceImpl pcservice = new PortalColumnServiceImpl();
										CmsResultDto crd = pcservice
												.sendProgListMangDetails(
														date,
														portalColumn
																.getDefcatseq(),
														currentIdAct,
														operatorId, "");
										if ((long) crd.getResultCode() == (long) 0) 
										{
											cmsLog.info("媒体栏目Portal已生成。");
										} 
										else 
										{
											String str = "发送栏目单分表活动失败。";
											cmsLog.warn(str);
											errorDetail += str + "\r\n";
											errorcount++;
										}
									}
									else 
									{
										String str = "生成数据库记录(ProgListFile)失败。";
										cmsLog.warn(str);
										errorDetail += str + "\r\n";
										errorcount++;
									}
								} 
								else 
								{
									// 返回失败。
									String str = "生成js文件失败。";
									cmsLog.warn(str);
									cmsLog.warn("缺少代码， 返回失败。");
									errorDetail += str + "\r\n";
									errorcount++;
								}
							} 
							else 
							{
								cmsLog.info("不生成JS文件。");
							}
						}

						String[] ygJsFileName = {"gq_yg.js", "njsw_yg.js"}; 
						String[] columnSeq = {"ROOT\\LANMUSHU\\yuanxian", "ROOT\\LANMUSHU\\njsw"};
						
						for (int i = 0; i < ygJsFileName.length; ++ i) {
							// ------------------ 准备生成节目预告js -----------------------
							// 生成js文件，js文件如何命名
							// 目标路径：存储体ip＋存储体目录＋编单日期＋栏目目录（portalColumn的）
							// 返回：List
							// 1 - String 目标路径()
							// 2 - List<Object[]>
							//			(AmsStorage)Object[0]
							//			(AmsStorageDir)Object[1]
							//			(AmsStorageClass)Object[2]
							String jsDestPath = "";
							List destpaths = packageFilesManager
							.getDestPathByFilecodeStclasscode(
									portalJsFilecode, stclasscodeOnline);
							if (destpaths != null && destpaths.size() >= 2) {
								jsDestPath = (String) destpaths.get(0);
								jsDestPath = jsDestPath.replace('\\', '/');
								jsDestPath = fileopr.checkPathFormatRear(
										jsDestPath, '/');
								jsDestPath += date;
								jsDestPath += "/";
								jsDestPath += ygJsFileName[i];
							}
							
							String strJs = generateYgJsString(date, columnSeq[i]);
							
							// 生成js文件，目标路径jsDestPath（含文件名后缀），内容strJs
							cmsLog.info("准备生成节目预告js文件...");
							cmsLog.info("目标 - " + jsDestPath);
							int retCreateJs = fileopr.createSmbFileGb2312(
									jsDestPath, strJs);
							if (retCreateJs == 0) {
								// 生成新记录到progListFile
								cmsLog
										.info("生成节目预告js文件成功，准备生成数据库记录(ProgListFile)...");
								ProgListFile progListFile = new ProgListFile();
								progListFile
										.setScheduledate(convertDateToScheduleDate(date));
								progListFile.setColumnclassid("");
								progListFile.setFilename(ygJsFileName[i]);
								progListFile.setFiletype(Long.valueOf(1)); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
								progListFile.setDate1(new Date());
								progListFile.setState1(Long.valueOf(0));
								progListFile.setIdAct("");
								progListFile.setColumnjs(strJs);
								progListFile.setInputmanid(operatorId);
								progListFile.setInputtime(new Date());

								CmsResultDto c = cmsTransactionManager
										.saveProgListFile(progListFileManager,
												progListFile, date, "null");

								if ((long) c.getResultCode() == (long) 0) {
									cmsLog.info("生成数据库记录(ProgListFile)成功，ID："
											+ progListFile.getColumnfileid());
								} else {
									String str = "生成数据库记录(ProgListFile)失败。";
									cmsLog.warn(str);
									errorDetail += str + "\r\n";
									errorcount++;
								}
							} else {
								// 返回失败。
								String str = "生成节目预告js文件失败。";
								cmsLog.warn(str);
								cmsLog.warn("缺少代码， 返回失败。");
								errorDetail += str + "\r\n";
								errorcount++;
							}
						}

					}

					if (errorcount == 0) {
						cmsLog.info("生成Portal成功。");
					} else {
						String str = "生成Portal失败。";
						cmsResultDto.setResultCode((long) 1);
						cmsResultDto.setErrorMessage(str);
						cmsResultDto.setErrorDetail(errorDetail);
						cmsLog.warn(str);
					}
				}
			}
		} catch (Exception e) {
			String str = "生成Portal发生异常。";
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);
			cmsResultDto.setErrorDetail(errorDetail);
			cmsLog.error("Cms -> PortalServiceImpl -> generatePortalWithoutModel，异常：" + str);
			cmsLog.error(errorDetail);
		}
		cmsLog.info("Cms -> PortalServiceImpl -> generatePortalWithoutModel returns.");
		return cmsResultDto;
	}
	
	// 20100416 13:34
	// 获得节目包js字符串，电视剧栏目，多个节目包和一个页面包装，生成一段js字符串
	public String getPackageJsByPortalPackage(
			String defcatcode,
			String firstPpxml,		// 电视剧第一集的ppxml，取海报和描述
			List readyPps,			// 准备生成js的progpackages，包含ppxml，取文件目录信息
			List readyPlds,			// 准备生成js的progListDetails，包含加扰产品列表
			List readyPprs			// 准备生成js的PtpPgpRels，包含分集数
			)
	{
		// 生成js字符串，根据firstpp、readyPps、readyPlds
		String strJs = "";
		String str = "";
		
		try
		{
			// 去掉utf-8 bom头
			firstPpxml = firstPpxml.substring(firstPpxml.indexOf("<?xml"), firstPpxml.length());
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader sr = new StringReader(firstPpxml);
			InputSource is = new InputSource(sr);
			Document doc = builder.parse(is);
			doc.normalize();
			
			strJs += "{\r\n";
			
			// 取第一集节目包的信息作为电视剧总的描述
//			NodeList cells = doc.getElementsByTagName("APP");
//			if(cells != null && cells.getLength() > 0)
//			{
//				Node cell = cells.item(0);
//			    Element cellattr = (Element)cells.item(0);
			
//				strJs += "'PROGTYPE':'";
//			    strJs += getCellAttribute(cell, cellattr, "AUDIOLANGUAGE");
//			    strJs += "',\r\n'PRODUCTNAME':'";
//			    strJs += getCellAttribute(cell, cellattr, "PRODUCTNAME");
//			    strJs += "',\r\n'TITLEBRIEF':'";
//			    strJs += getCellAttribute(cell, cellattr, "TITLEBRIEF");
//			    strJs += "',\r\n'COUNTRYDIST':'";
//			    strJs += getCellAttribute(cell, cellattr, "COUNTRYDIST");
//			    strJs += "',\r\n'ISSUEDATE':'";
//			    strJs += getCellAttribute(cell, cellattr, "ISSUEDATE");
//			    strJs += "',\r\n'CopyrightNUMB':'";
//			    strJs += getCellAttribute(cell, cellattr, "");
//			    strJs += "',\r\n'CopyrightOwner':'";
//			    strJs += getCellAttribute(cell, cellattr, "");
//			    strJs += "',\r\n'SUBSCRIBERETIME':'";
//			    strJs += getCellAttribute(cell, cellattr, "SUBSCRIBERETIME");
//			    strJs += "',\r\n'DIRECTOR':'";
//			    strJs += getCellAttribute(cell, cellattr, "DIRECTOR");
//			    strJs += "',\r\n'ACTORS':'";
//			    strJs += getCellAttribute(cell, cellattr, "ACTORS");
//			    strJs += "',\r\n'DESCRIPTION':'";
//			    strJs += getCellAttribute(cell, cellattr, "DESCRIPTION");
//			    strJs += "',\r\n'LANGUAGE':'";
//			    strJs += getCellAttribute(cell, cellattr, "");
//			    strJs += "',\r\n'SUBTITLELANGUAGE':'";
//			    strJs += getCellAttribute(cell, cellattr, "SUBTITLELANGUAGE");
//			    strJs += "',\r\n'LENGTHTC':'";
//			    strJs += getCellAttribute(cell, cellattr, "LENGTHTC");
//			    strJs += "',\r\n'SUMFILESIZE':'";
//			    strJs += getCellAttribute(cell, cellattr, "SUMFILESIZE");
//			    strJs += "',\r\n'VideoBitrate':'";
//			    strJs += getCellAttribute(cell, cellattr, "");
//			    strJs += "',\r\n'QualityGrade':'";
//			    strJs += getCellAttribute(cell, cellattr, "");
//			    strJs += "',\r\n'StarGrade':'";
//			    strJs += getCellAttribute(cell, cellattr, "");
			
			String name = getCellTextContent(doc, "epicodename");
		    if(name == null || name.equalsIgnoreCase(""))
		    {
		    	name = getCellTextContent(doc, "titlebrief");
		    }
		    
			    strJs += "'PROGTYPE':'";
			    strJs += getCellTextContent(doc, "audiolanguage");
			    strJs += "',\r\n'PRODUCTNAME':'";
			    strJs += getCellTextContent(doc, "productname");
			    strJs += "',\r\n'TITLEBRIEF':'";
			    strJs += name;
			    strJs += "',\r\n'COUNTRYDIST':'";
			    strJs += getCellTextContent(doc, "countrydist");
			    strJs += "',\r\n'ISSUEDATE':'";
			    strJs += getCellTextContent(doc, "ISSUEDATE");
			    strJs += "',\r\n'CopyrightNUMB':'";
			    strJs += getCellTextContent(doc, "CopyrightNUMB");
			    strJs += "',\r\n'CopyrightOwner':'";
			    strJs += getCellTextContent(doc, "CopyrightOwner");
			    strJs += "',\r\n'SUBSCRIBERETIME':'";
			    strJs += getCellTextContent(doc, "subscriberetime");
			    strJs += "',\r\n'DIRECTOR':'";
			    strJs += getCellTextContent(doc, "director");
			    strJs += "',\r\n'ACTORS':'";
			    strJs += getCellTextContent(doc, "actors");
			    strJs += "',\r\n'DESCRIPTION':'";
			    strJs += getCellTextContent(doc, "description");
			    strJs += "',\r\n'LANGUAGE':'";
			    strJs += getCellTextContent(doc, "LANGUAGE");
			    strJs += "',\r\n'SUBTITLELANGUAGE':'";
			    strJs += getCellTextContent(doc, "SUBTITLELANGUAGE");
			    strJs += "',\r\n'LENGTHTC':'";
			    strJs += getCellTextContent(doc, "LENGTHTC");
			    strJs += "',\r\n'SUMFILESIZE':'";
			    strJs += getCellTextContent(doc, "SUMFILESIZE");
			    strJs += "',\r\n'VideoBitrate':'";
			    strJs += getCellTextContent(doc, "VideoBitrate");
			    strJs += "',\r\n'QualityGrade':'";
			    strJs += getCellTextContent(doc, "QualityGrade");
			    strJs += "',\r\n'StarGrade':'";
			    strJs += getCellTextContent(doc, "StarGrade");
			    strJs += "',\r\n'CA':'";
			    strJs += "0";
			    strJs += "',\r\n'POSTER':'";
			    NodeList itemcells = doc.getElementsByTagName("FILE");
				for(int i = 0; i < itemcells.getLength(); i++)
				{
					Node itemcell = itemcells.item(i);
				    Element itemcellattr = (Element)itemcells.item(i);
				    str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
				    if(str.equalsIgnoreCase("PNG1"))
				    {
				    	strJs += getCellAttribute(itemcell, itemcellattr, "FILENAME");
				    	break;
				    }
				}
				strJs += "',\r\n";
				
				strJs += "items : [\r\n";
				for(int i = 0; i < readyPps.size(); i++)
				{
//					List readyPps,			// 准备生成js的progpackages，包含ppxml，取文件目录信息
//					List readyPlds,			// 准备生成js的progListDetails，包含加扰产品列表
//					List readyPprs			// 准备生成js的PtpPgpRels，包含分集数
					
					ProgPackage pp = (ProgPackage)readyPps.get(i);
					ProgListDetail pld = (ProgListDetail)readyPlds.get(i);
					PtpPgpRel ppr = (PtpPgpRel)readyPprs.get(i);
					
					sr = new StringReader(pp.getPpxml());
					is = new InputSource(sr);
					doc = builder.parse(is);
					doc.normalize();
					
					strJs += "{\r\n'TITLEBRIEF':'";		// readyPprs
					strJs += ppr.getEpicodeid();
					strJs += "', \r\n'PRODUCTNAME':'";
					strJs += ppr.getEpicodeid();
					strJs += "', \r\n'ACTORS':'";
					strJs += getCellTextContent(doc, "actors");
					strJs += "', \r\n'COUNTRYDIST':'";
					strJs += getCellTextContent(doc, "countrydist");
					strJs += "', \r\n'CopyrightNUMB':'";
					strJs += getCellTextContent(doc, "CopyrightNUMB");
					strJs += "', \r\n'CopyrightOwner':'";
					strJs += getCellTextContent(doc, "CopyrightOwner");
					strJs += "', \r\n'DESCRIPTION':'";
					strJs += getCellTextContent(doc, "description");
					strJs += "', \r\n'DIRECTOR':'";
					strJs += getCellTextContent(doc, "director");
					strJs += "', \r\n'ISSUEDATE':'";
					strJs += getCellTextContent(doc, "ISSUEDATE");
					strJs += "', \r\n'LANGUAGE':'";
					strJs += getCellTextContent(doc, "LANGUAGE");
					strJs += "', \r\n'LENGTHTC':'";
					strJs += getCellTextContent(doc, "LENGTHTC");
					strJs += "', \r\n'PROGTYPE':'";
					strJs += getCellTextContent(doc, "audiolanguage");
					strJs += "', \r\n'QualityGrade':'";
					strJs += getCellTextContent(doc, "QualityGrade");
					strJs += "', \r\n'SUBSCRIBERETIME':'";
					 strJs += getCellTextContent(doc, "subscriberetime");
					strJs += "', \r\n'SUBTITLELANGUAGE':'";
					strJs += getCellTextContent(doc, "SUBTITLELANGUAGE");
					strJs += "', \r\n'SUMFILESIZE':'";
					strJs += getCellTextContent(doc, "SUMFILESIZE");
					strJs += "', \r\n'StarGrade':'";
					strJs += getCellTextContent(doc, "StarGrade");
					strJs += "', \r\n'VideoBitrate':'";
					strJs += getCellTextContent(doc, "VideoBitrate");
					strJs += "', \r\n'PRICE':'";		// 暂时不使用
					strJs += "', \r\n'DAYS':'";			// 下线日期
					strJs += fileopr.convertDateToString(pld.getValidTo(), "yyyy-MM-dd");
					strJs += "', \r\n'FILENAME':'";		// PROGFILE -> FILE.FILENAME
					NodeList filecells = doc.getElementsByTagName("FILE");
					for(int j = 0; j < filecells.getLength(); j++)
					{
						Node itemcell = filecells.item(j);
					    Element itemcellattr = (Element)filecells.item(j);
					    str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
					    if("H264".equalsIgnoreCase(str))
					    {
					    	strJs += getCellAttribute(itemcell, itemcellattr, "FILENAME");
					    	break;
					    }
					}
					strJs += "', \r\n'POSTER':'";		// PROGFILE -> FILE.FILENAME
					for(int j = 0; j < filecells.getLength(); j++)
					{
						Node itemcell = filecells.item(j);
					    Element itemcellattr = (Element)filecells.item(j);
					    str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
					    if("PNG1".equalsIgnoreCase(str))
					    {
					    	strJs += getCellAttribute(itemcell, itemcellattr, "FILENAME");
					    	break;
					    }
					}
					strJs += "', \r\n'CA':'";
					if(pld.getETitle() != null && !pld.getETitle().equalsIgnoreCase(""))
					{
						strJs += "1";
					}
					else
					{
						strJs += "0";
					}
					strJs += "'\r\n}\r\n";
				}
				strJs += "]\r\n";
//			}
			
			strJs += "}\r\n";
		}
		catch(Exception ex)
		{
			cmsLog.error("Cms -> PortalServiceImpl -> getPackageJsByPortalPackage，异常：" + ex.getMessage());
			strJs = "";
		}
		return strJs;
	}
	
	// 20100402 夜里
	// 获得节目包js字符串
	public String getPackageJs(
			ProgListDetail proglistdetail,
			String eTitle,
			String defcatcode,
			String strxml,
			List metpjrs,			// 目前未使用
			List accpjrs,			// 目前未使用
			List itemheadpjrs,		// 目前未使用
			List itempjrs			// 目前未使用
			)
	{
		String strJs = "";
		String str = "";
		try
		{
			//strxml = getStrFromLocalFile("D:/项目程序_SBL/31.Vod_Java/xml/ppxml/复件 PPVP20100209110638000078节目包.xml");
			// 去掉utf-8 bom头
			strxml = strxml.substring(strxml.indexOf("<?xml"), strxml.length());
			// 将String转成parse可以识别的InputSource
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader sr = new StringReader(strxml);
			InputSource is = new InputSource(sr);
			Document doc = builder.parse(is);
			doc.normalize();

			strJs += "{\r\n";
			
			if(defcatcode.equalsIgnoreCase("dy")
				|| defcatcode.equalsIgnoreCase("gq_yx_dy")
				|| defcatcode.equalsIgnoreCase("yx_jlp")
				|| defcatcode.equalsIgnoreCase("gq_yx_jlp")
				|| defcatcode.equalsIgnoreCase("gq_yx_zh")
				|| defcatcode.equalsIgnoreCase("njsw_yx_ny")
				|| defcatcode.equalsIgnoreCase("yx_zy")
				|| defcatcode.equalsIgnoreCase("njsw_yx_jy")
				|| defcatcode.equalsIgnoreCase("njsw_yx_ys")
				|| defcatcode.equalsIgnoreCase("yx_dy")
				|| defcatcode.equalsIgnoreCase("yx_se")
				|| defcatcode.equalsIgnoreCase("yx_jybk")
				|| defcatcode.equalsIgnoreCase("yx_nykj")
			) {
				strJs += "'PROGTYPE':'";
			    strJs += getCellTextContent(doc, "audiolanguage");
			    strJs += "',\r\n'PRODUCTNAME':'";
			    strJs += getCellTextContent(doc, "productname");
			    strJs += "',\r\n'TITLEBRIEF':'";
			    strJs += getCellTextContent(doc, "titlebrief");
			    strJs += "',\r\n'COUNTRYDIST':'";
			    strJs += getCellTextContent(doc, "countrydist");
			    strJs += "',\r\n'ISSUEDATE':'";
			    strJs += getCellTextContent(doc, "ISSUEDATE");
			    strJs += "',\r\n'CopyrightNUMB':'";
			    strJs += getCellTextContent(doc, "CopyrightNUMB");
			    strJs += "',\r\n'CopyrightOwner':'";
			    strJs += getCellTextContent(doc, "CopyrightOwner");
			    strJs += "',\r\n'SUBSCRIBERETIME':'";
			    strJs += getCellTextContent(doc, "subscriberetime");
			    strJs += "',\r\n'DAYS':'";// 下线日期
		    	strJs += fileopr.convertDateToString(proglistdetail.getValidTo(), "yyyy-MM-dd");
			    strJs += "',\r\n'DIRECTOR':'";
			    strJs += getCellTextContent(doc, "director");
			    strJs += "',\r\n'ACTORS':'";
			    strJs += getCellTextContent(doc, "actors");
			    strJs += "',\r\n'DESCRIPTION':'";
			    strJs += getCellTextContent(doc, "description");
			    strJs += "',\r\n'LANGUAGE':'";
			    strJs += getCellTextContent(doc, "LANGUAGE");
			    strJs += "',\r\n'SUBTITLELANGUAGE':'";
			    strJs += getCellTextContent(doc, "SUBTITLELANGUAGE");
			    strJs += "',\r\n'LENGTHTC':'";
			    strJs += getCellTextContent(doc, "LENGTHTC");
			    strJs += "',\r\n'SUMFILESIZE':'";
			    strJs += getCellTextContent(doc, "SUMFILESIZE");
			    strJs += "',\r\n'VideoBitrate':'";
			    strJs += getCellTextContent(doc, "VideoBitrate");
			    strJs += "',\r\n'QualityGrade':'";
			    strJs += getCellTextContent(doc, "QualityGrade");
			    strJs += "',\r\n'StarGrade':'";
			    strJs += getCellTextContent(doc, "StarGrade");
				strJs += "',\r\n'CA':'";
				if (eTitle != null && !eTitle.equalsIgnoreCase("")) {
					strJs += "1";
				} else {
					strJs += "0";
				}

				NodeList itemcells = doc.getElementsByTagName("FILE");
				for (int i = 0; i < itemcells.getLength(); i++) {
					Node itemcell = itemcells.item(i);
					Element itemcellattr = (Element) itemcells.item(i);
					str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
					if (str.equalsIgnoreCase("H264")) {
						strJs += "',\r\n'FILENAME':'";
						strJs += getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
					} else if (str.equalsIgnoreCase("PNG1")) {
						strJs += "',\r\n'POSTER':'";
						strJs += getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
					}
				}
				strJs += "'";	
			}
			// ---------- 电视剧 --------------------------------------------------------------
			else if(defcatcode.equalsIgnoreCase("dsj")
					|| defcatcode.equalsIgnoreCase("dianshiju")
					|| defcatcode.equalsIgnoreCase("yx_dsj")
					|| defcatcode.equalsIgnoreCase("gq_yx_dsj")
			) {
				// 流程（电视剧）：多个节目包组合成一个电视剧，每个节目包是一集
				// 1 - 得到节目包的ppxml后，根据节目包ID查询数据库，得到该节目包所属的页面包装记录
				// 2 - 如果有，
				// 3 - 如果没有，
				// 
				strJs += "'PROGTYPE':'";
			    strJs += getCellTextContent(doc, "audiolanguage");
			    strJs += "',\r\n'PRODUCTNAME':'";
			    strJs += getCellTextContent(doc, "productname");
				    
				
				
				NodeList itemcells = doc.getElementsByTagName("FILE");
				for (int i = 0; i < itemcells.getLength(); i++) {
					Node itemcell = itemcells.item(i);
					Element itemcellattr = (Element) itemcells.item(i);
					str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
					if (str.equalsIgnoreCase("PNG1")) {
						strJs += "',\r\n'POSTER':'";
						strJs += getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
						break;
					}
				}

				String titlebrief = "";
				strJs += "',\r\n'TITLEBRIEF':'";
				titlebrief = getCellTextContent(doc, "titlebrief");
				strJs += titlebrief;
				strJs += "',\r\n'COUNTRYDIST':'";
				strJs += getCellTextContent(doc, "countrydist");
				strJs += "',\r\n'ISSUEDATE':'";
				strJs += getCellTextContent(doc, "ISSUEDATE");
				strJs += "',\r\n'CopyrightNUMB':'";
				strJs += getCellTextContent(doc, "CopyrightNUMB");
				strJs += "',\r\n'CopyrightOwner':'";
				strJs += getCellTextContent(doc, "CopyrightOwner");
				strJs += "',\r\n'SUBSCRIBERETIME':'";
				strJs += getCellTextContent(doc, "subscriberetime");
				strJs += "',\r\n'DIRECTOR':'";
				strJs += getCellTextContent(doc, "director");
				strJs += "',\r\n'ACTORS':'";
				strJs += getCellTextContent(doc, "actors");
				strJs += "',\r\n'DESCRIPTION':'";
				strJs += getCellTextContent(doc, "description");
				strJs += "',\r\n'LANGUAGE':'";
				strJs += getCellTextContent(doc, "LANGUAGE");
				strJs += "',\r\n'SUBTITLELANGUAGE':'";
				strJs += getCellTextContent(doc, "SUBTITLELANGUAGE");
				strJs += "',\r\n'LENGTHTC':'";
				strJs += getCellTextContent(doc, "LENGTHTC");
				strJs += "',\r\n'SUMFILESIZE':'";
				strJs += getCellTextContent(doc, "SUMFILESIZE");
				strJs += "',\r\n'VideoBitrate':'";
				strJs += getCellTextContent(doc, "VideoBitrate");
				strJs += "',\r\n'QualityGrade':'";
				strJs += getCellTextContent(doc, "QualityGrade");
				strJs += "',\r\n'StarGrade':'";
				strJs += getCellTextContent(doc, "StarGrade");
				strJs += "',\r\n'CA':'";
				strJs += "0";
				strJs += "',\r\n'items':[\r\n";
				itemcells = doc.getElementsByTagName("FILE");
				for (int i = 0; i < itemcells.getLength(); i++) {
					Node itemcell = itemcells.item(i);
					Element itemcellattr = (Element) itemcells.item(i);

					str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
					// str = getCellAttribute(itemcell, itemcellattr,
					// "PROGRANK");
					if (str.equalsIgnoreCase("H264")) {
						strJs += "{'TITLEBRIEF':'";
						strJs += titlebrief;
						strJs += "', 'PRICE':'";// 暂时不使用
						strJs += "', 'DAYS':'";// 下线日期
						strJs += fileopr.convertDateToString(
								proglistdetail.getValidTo(), "yyyy-MM-dd");
						strJs += "', 'FILENAME':'";
						// strJs += "{'FILENAME':'";
						strJs += getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
						strJs += "','CA':'";
						if (eTitle != null && !eTitle.equalsIgnoreCase("")) {
							strJs += "1";
						} else {
							strJs += "0";
						}
						strJs += "'}\r\n,";
					}
				}
				if (strJs.charAt(strJs.length() - 1) == ',') {
					strJs = strJs.substring(0, strJs.length() - 1);
				}
				strJs += "]";
			} else if("njsw_dzbz".equalsIgnoreCase(defcatcode) 
					|| "gq_zx_bz".equalsIgnoreCase(defcatcode)
					|| "yx_dzbz".equalsIgnoreCase(defcatcode)
					|| "njsw_bk_bz".equalsIgnoreCase(defcatcode)) {
				/**
				 * 电子报纸
				 */
				strJs += "'issn':'";									// ISSN
			    strJs += getCellTextContent(doc, "issn");
			    strJs += "',\r\n'updatetime':'";						// 更新日期
			    strJs += getCellTextContent(doc, "UPDATETIME");
			    strJs += "',\r\n'productid':'";							// 产品编号
			    strJs += getCellTextContent(doc, "productid");
			    strJs += "',\r\n'syqx':'";								// 使用期限
			    strJs += getCellTextContent(doc, "syqx");
			    strJs += "',\r\n'nrtgf':'";								// 内容提供方
			    strJs += getCellTextContent(doc, "nrtgf");
			    strJs += "',\r\n'nrtgfbh':'";							// 内容提供方编号
			    strJs += getCellTextContent(doc, "nrtgfbh");
			    strJs += "',\r\n'issuedate':'";							// 出版日期
			    strJs += getCellTextContent(doc, "issuedate");
			    strJs += "',\r\n'gntykh':'";							// 国内统一刊号
			    strJs += getCellTextContent(doc, "gntykh");
			    strJs += "',\r\n'scyj':'";								// 审查意见
		    	strJs += getCellTextContent(doc, "scyj");
			    strJs += "',\r\n'toudihao':'";							// 投递号
			    strJs += getCellTextContent(doc, "toudihao");
			    strJs += "',\r\n'toudiriqi':'";							// 投递日期
			    strJs += getCellTextContent(doc, "toudiriqi");
			    strJs += "',\r\n'toudicishu':'";						// 投递次数
			    strJs += getCellTextContent(doc, "toudicishu");
			    strJs += "',\r\n'audiolanguage':'";						// 报纸分类
			    strJs += getCellTextContent(doc, "audiolanguage");
			    strJs += "',\r\n'shuliang':'";							// 数量
			    strJs += getCellTextContent(doc, "shuliang");
			    strJs += "',\r\n'audiolanguage':'";						// 类别
			    strJs += getCellTextContent(doc, "audiolanguage");
			    
//			    strJs += "',\r\n'CA':'";
//				if (eTitle != null && !eTitle.equalsIgnoreCase("")) {
//					strJs += "1";
//				} else {
//					strJs += "0";
//				}

				NodeList itemcells = doc.getElementsByTagName("FILE");
				for (int i = 0; i < itemcells.getLength(); i++) {
					Node itemcell = itemcells.item(i);
					Element itemcellattr = (Element) itemcells.item(i);
					str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
					if (str.equalsIgnoreCase("RMZIP")) {
						strJs += "',\r\n'FILENAME':'";
						String fileName = getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
						strJs += fileName.substring(0, fileName.lastIndexOf("."));
					} else if (str.equalsIgnoreCase("PNG1")) {
						strJs += "',\r\n'POSTER':'";
						strJs += getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
					}
				}
				strJs += "'";	
			} else if("njsw_dzzz".equalsIgnoreCase(defcatcode)
					 || "yx_dzzz".equalsIgnoreCase(defcatcode)
					 || "njsw_bk_zz".equalsIgnoreCase(defcatcode)
					 || "gq_zx_zz".equalsIgnoreCase(defcatcode)) {
				/**
				 * 电子杂志
				 */
				strJs += "'isbn':'";									// ISBN
			    strJs += getCellTextContent(doc, "issn");
			    strJs += "',\r\n'updatetime':'";						// 更新日期
			    strJs += getCellTextContent(doc, "UPDATETIME");
			    strJs += "',\r\n'zgdw':'";								// 主管单位
			    strJs += getCellTextContent(doc, "zgdw");
			    strJs += "',\r\n'productid':'";							// 产品编号
			    strJs += getCellTextContent(doc, "productid");
			    strJs += "',\r\n'syqx':'";								// 使用期限
			    strJs += getCellTextContent(doc, "syqx");
			    strJs += "',\r\n'nrtgf':'";								// 内容提供方
		    	strJs += getCellTextContent(doc, "nrtgf");
			    strJs += "',\r\n'nrtgfbh':'";							// 内容提供方编号
			    strJs += getCellTextContent(doc, "nrtgfbh");
			    strJs += "',\r\n'description':'";						// 内容简介
			    strJs += getCellTextContent(doc, "description");
			    strJs += "',\r\n'chubanshe':'";							// 出版社
			    strJs += getCellTextContent(doc, "chubanshe");
			    strJs += "',\r\n'cbsszd':'";							// 出版社所在地
			    strJs += getCellTextContent(doc, "cbsszd");
			    strJs += "',\r\n'cbdw':'";								// 出版单位
			    strJs += getCellTextContent(doc, "cbdw");
			    strJs += "',\r\n'issuedate':'";							// 出版时间
			    strJs += getCellTextContent(doc, "issuedate");
			    strJs += "',\r\n'gntykh':'";							// 国内统一刊号
			    strJs += getCellTextContent(doc, "gntykh");
			    strJs += "',\r\n'audiolanguage':'";						// 杂志分类
			    strJs += getCellTextContent(doc, "audiolanguage");
			    strJs += "',\r\n'sywjm':'";								// 首页文件名
			    strJs += getCellTextContent(doc, "sywjm");
			    strJs += "',\r\n'tpmc':'";								// 图片名称
			    
			    NodeList itemcells = doc.getElementsByTagName("FILE");
				for(int i = 0; i < itemcells.getLength(); i++)
				{
					Node itemcell = itemcells.item(i);
				    Element itemcellattr = (Element)itemcells.item(i);
				    str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
				    if(str.equalsIgnoreCase("PNG1"))
				    {
//				    	strJs += "',\r\n'tpmc':'";						// 图片名称
				    	strJs += getCellAttribute(itemcell, itemcellattr, "FILENAME");
				    	break;
				    }
				}
				
			    strJs += "',\r\n'tpgs':'";								// 图片格式
//			    strJs += getCellTextContent(doc, "productname");
			    strJs += "',\r\n'ccwz':'";								// 存储位置
//			    strJs += getCellTextContent(doc, "countrydist");
			    strJs += "',\r\n'scyj':'";								// 审查意见
			    strJs += getCellTextContent(doc, "scyj");
			    strJs += "',\r\n'xcxx':'";								// 宣传信息
			    strJs += getCellTextContent(doc, "xcxx");
			    strJs += "',\r\n'kaiben':'";							// 开本
			    strJs += getCellTextContent(doc, "kaiben");
			    strJs += "',\r\n'epicodenumber':'";						// 总期号
			    strJs += getCellTextContent(doc, "epicodenumber");
			    strJs += "',\r\n'toudihao':'";							// 投递号
			    strJs += getCellTextContent(doc, "toudihao");
			    strJs += "',\r\n'toudiriqi':'";							// 投递日期
			    strJs += getCellTextContent(doc, "toudiriqi");
			    strJs += "',\r\n'toudicishu':'";						// 投递次数
			    strJs += getCellTextContent(doc, "toudicishu");
			    strJs += "',\r\n'xingji':'";							// 星级
			    strJs += getCellTextContent(doc, "xingji");
			    strJs += "',\r\n'sfydyysj':'";							// 是否有对应影视剧
			    strJs += getCellTextContent(doc, "sfydyysj");
			    strJs += "',\r\n'epicodeid':'";							// 期号
			    strJs += getCellTextContent(doc, "epicodeid");
			    strJs += "',\r\n'bqs':'";								// 版权商
			    strJs += getCellTextContent(doc, "bqs");
			    strJs += "',\r\n'subscriberetime':'";					// 版权截止日期
			    strJs += getCellTextContent(doc, "subscriberetime");
			    strJs += "',\r\n'bqlx':'";								// 版权类型
			    strJs += getCellTextContent(doc, "bqlx");
			    strJs += "',\r\n'dzbjg':'";								// 电子版价格
			    strJs += getCellTextContent(doc, "dzbjg");
			    strJs += "',\r\n'dzbys':'";								// 电子版页数
			    strJs += getCellTextContent(doc, "dzbys");
			    strJs += "',\r\n'audiolanguage':'";						// 类别
			    strJs += getCellTextContent(doc, "audiolanguage");
			    strJs += "',\r\n'zzzzjg':'";							// 纸质图书价格
			    strJs += getCellTextContent(doc, "zzzzjg");
			    strJs += "',\r\n'zzys':'";								// 纸质图书页数
			    strJs += getCellTextContent(doc, "zzys");
			    strJs += "',\r\n'yuyan':'";								// 语言
			    strJs += getCellTextContent(doc, "yuyan");
			    strJs += "',\r\n'xsfs':'";								// 销售方式
			    strJs += getCellTextContent(doc, "xsfs");

				for (int i = 0; i < itemcells.getLength(); i++) {
					Node itemcell = itemcells.item(i);
					Element itemcellattr = (Element) itemcells.item(i);
					str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
					if (str.equalsIgnoreCase("RMZIP")) {
						strJs += "',\r\n'FILENAME':'";
						String fileName = getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
						strJs += fileName.substring(0, fileName.lastIndexOf("."));
					} else if (str.equalsIgnoreCase("PNG1")) {
						strJs += "',\r\n'POSTER':'";
						strJs += getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
					}
				}
				strJs += "'";	
			} else if("njsw_dzts".equalsIgnoreCase(defcatcode)
					 || "yx_dzts".equalsIgnoreCase(defcatcode)
					 || "gq_zx_ts".equalsIgnoreCase(defcatcode)
					 || "njsw_ts_kj".equalsIgnoreCase(defcatcode)
					 || "njsw_ts_bx".equalsIgnoreCase(defcatcode)
					 || "njsw_ts_dj".equalsIgnoreCase(defcatcode)
					 || "njsw_ts_wh".equalsIgnoreCase(defcatcode)) {
				/**
				 * 电子图书
				 */
				strJs += "'isbn':'";									// ISBN
			    strJs += getCellTextContent(doc, "isbn");
			    strJs += "',\r\n'updatetime':'";						// 更新日期
			    strJs += getCellTextContent(doc, "UPDATETIME");
			    strJs += "',\r\n'productname':'";						// 书名
			    strJs += getCellTextContent(doc, "productname");
			    strJs += "',\r\n'productid':'";							// 产品编号
			    strJs += getCellTextContent(doc, "productid");
			    strJs += "',\r\n'zuozhe1':'";							// 作者1
			    strJs += getCellTextContent(doc, "zuozhe1");
			    strJs += "',\r\n'zuozhe2':'";							// 作者2
			    strJs += getCellTextContent(doc, "zuozhe2");
			    strJs += "',\r\n'zuozhe3':'";							// 作者3
			    strJs += getCellTextContent(doc, "zuozhe3");
			    strJs += "',\r\n'syqx':'";								// 使用期限
			    strJs += getCellTextContent(doc, "syqx");
			    strJs += "',\r\n'nrtgf':'";								// 内容提供方
		    	strJs += getCellTextContent(doc, "nrtgf");
			    strJs += "',\r\n'nrtgfbh':'";							// 内容提供方编号
			    strJs += getCellTextContent(doc, "nrtgfbh");
			    strJs += "',\r\n'description':'";						// 内容简介
			    strJs += getCellTextContent(doc, "description");
			    strJs += "',\r\n'issuedate':'";							// 出版时间
			    strJs += getCellTextContent(doc, "issuedate");
			    strJs += "',\r\n'chubanshe':'";							// 出版社
			    strJs += getCellTextContent(doc, "chubanshe");
			    strJs += "',\r\n'cbsszd':'";							// 出版社所在地
			    strJs += getCellTextContent(doc, "cbsszd");
			    strJs += "',\r\n'audiolanguage':'";						// 图书分类
			    strJs += getCellTextContent(doc, "audiolanguage");
			    strJs += "',\r\n'sywjm':'";								// 首页文件名
			    strJs += getCellTextContent(doc, "sywjm");
			    strJs += "',\r\n'tpmc':'";								// 图片名称

			    NodeList itemcells = doc.getElementsByTagName("FILE");
				for (int i = 0; i < itemcells.getLength(); i++) {
					Node itemcell = itemcells.item(i);
					Element itemcellattr = (Element) itemcells.item(i);
					str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
					if (str.equalsIgnoreCase("PNG1")) {
						// strJs += "',\r\n'tpmc':'"; // 图片名称
						strJs += getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
						break;
					}
				}
				
			    strJs += "',\r\n'tpgs':'";								// 图片格式
//			    strJs += getCellTextContent(doc, "productname");
			    strJs += "',\r\n'zishu':'";								// 字数
			    strJs += getCellTextContent(doc, "zishu");
			    strJs += "',\r\n'ccwz':'";								// 存储位置
			    strJs += getCellTextContent(doc, "ccwz");
			    strJs += "',\r\n'scyj':'";								// 审查意见
			    strJs += getCellTextContent(doc, "scyj");
			    strJs += "',\r\n'xcxx':'";								// 宣传信息
			    strJs += getCellTextContent(doc, "xcxx");
			    strJs += "',\r\n'kaiben':'";							// 开本
			    strJs += getCellTextContent(doc, "kaiben");
			    strJs += "',\r\n'toudihao':'";							// 投递号
			    strJs += getCellTextContent(doc, "toudihao");
			    strJs += "',\r\n'toudiriqi':'";							// 投递日期
		    	strJs += getCellTextContent(doc, "toudiriqi");
			    strJs += "',\r\n'toudicishu':'";						// 投递次数
			    strJs += getCellTextContent(doc, "toudicishu");
			    strJs += "',\r\n'xingji':'";							// 星级
			    strJs += getCellTextContent(doc, "xingji");
			    strJs += "',\r\n'sfydyysj':'";							// 是否有对应影视剧
			    strJs += getCellTextContent(doc, "sfydyysj");
			    strJs += "',\r\n'bqs':'";								// 版权商
			    strJs += getCellTextContent(doc, "bqs");
			    strJs += "',\r\n'subscriberetime':'";					// 版权截止日期
			    strJs += getCellTextContent(doc, "subscriberetime");
			    strJs += "',\r\n'bqlx':'";								// 版权类型
			    strJs += getCellTextContent(doc, "bqlx");
			    strJs += "',\r\n'banci':'";								// 版次
			    strJs += getCellTextContent(doc, "banci");
			    strJs += "',\r\n'dzbjg':'";								// 电子版价格
			    strJs += getCellTextContent(doc, "dzbjg");
			    strJs += "',\r\n'dzbys':'";								// 电子版页数
		    	strJs += getCellTextContent(doc, "dzbys");
			    strJs += "',\r\n'audiolanguage':'";						// 类别
			    strJs += getCellTextContent(doc, "audiolanguage");
			    strJs += "',\r\n'epicodename':'";						// 系列书名
			    strJs += getCellTextContent(doc, "epicodename");
			    strJs += "',\r\n'zztsjg':'";							// 纸质图书价格
			    strJs += getCellTextContent(doc, "zztsjg");
			    strJs += "',\r\n'zzys':'";								// 纸质图书页数
			    strJs += getCellTextContent(doc, "zzys");
			    strJs += "',\r\n'yuyan':'";								// 语言
			    strJs += getCellTextContent(doc, "yuyan");
			    strJs += "',\r\n'xsfs':'";								// 销售方式
			    strJs += getCellTextContent(doc, "xsfs");
			    
				for (int i = 0; i < itemcells.getLength(); i++) {
					Node itemcell = itemcells.item(i);
					Element itemcellattr = (Element) itemcells.item(i);
					str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
					if (str.equalsIgnoreCase("RMZIP")) {
						strJs += "',\r\n'FILENAME':'";
						String fileName = getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
						strJs += fileName.substring(0, fileName.lastIndexOf("."));
					} else if (str.equalsIgnoreCase("PNG1")) {
						strJs += "',\r\n'POSTER':'";
						strJs += getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
					}
				}
				strJs += "'";
			} else {
				cmsLog.warn("栏目code没有对应的处理，使用默认处理。" + defcatcode);
				
				strJs += "'PROGTYPE':'";
			    strJs += getCellTextContent(doc, "audiolanguage");
			    strJs += "',\r\n'PRODUCTNAME':'";
			    strJs += getCellTextContent(doc, "productname");
			    strJs += "',\r\n'TITLEBRIEF':'";
			    strJs += getCellTextContent(doc, "titlebrief");
			    strJs += "',\r\n'COUNTRYDIST':'";
			    strJs += getCellTextContent(doc, "countrydist");
			    strJs += "',\r\n'ISSUEDATE':'";
			    strJs += getCellTextContent(doc, "ISSUEDATE");
			    strJs += "',\r\n'CopyrightNUMB':'";
			    strJs += getCellTextContent(doc, "CopyrightNUMB");
			    strJs += "',\r\n'CopyrightOwner':'";
			    strJs += getCellTextContent(doc, "CopyrightOwner");
			    strJs += "',\r\n'SUBSCRIBERETIME':'";
			    strJs += getCellTextContent(doc, "subscriberetime");
			    strJs += "',\r\n'DAYS':'";// 下线日期
		    	strJs += fileopr.convertDateToString(proglistdetail.getValidTo(), "yyyy-MM-dd");
			    strJs += "',\r\n'DIRECTOR':'";
			    strJs += getCellTextContent(doc, "director");
			    strJs += "',\r\n'ACTORS':'";
			    strJs += getCellTextContent(doc, "actors");
			    strJs += "',\r\n'DESCRIPTION':'";
			    strJs += getCellTextContent(doc, "description");
			    strJs += "',\r\n'LANGUAGE':'";
			    strJs += getCellTextContent(doc, "LANGUAGE");
			    strJs += "',\r\n'SUBTITLELANGUAGE':'";
			    strJs += getCellTextContent(doc, "SUBTITLELANGUAGE");
			    strJs += "',\r\n'LENGTHTC':'";
			    strJs += getCellTextContent(doc, "LENGTHTC");
			    strJs += "',\r\n'SUMFILESIZE':'";
			    strJs += getCellTextContent(doc, "SUMFILESIZE");
			    strJs += "',\r\n'VideoBitrate':'";
			    strJs += getCellTextContent(doc, "VideoBitrate");
			    strJs += "',\r\n'QualityGrade':'";
			    strJs += getCellTextContent(doc, "QualityGrade");
			    strJs += "',\r\n'StarGrade':'";
			    strJs += getCellTextContent(doc, "StarGrade");
				strJs += "',\r\n'CA':'";
				if (eTitle != null && !eTitle.equalsIgnoreCase("")) {
					strJs += "1";
				} else {
					strJs += "0";
				}

				NodeList itemcells = doc.getElementsByTagName("FILE");
				for (int i = 0; i < itemcells.getLength(); i++) {
					Node itemcell = itemcells.item(i);
					Element itemcellattr = (Element) itemcells.item(i);
					str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
					if (str.equalsIgnoreCase("H264")) {
						strJs += "',\r\n'FILENAME':'";
						strJs += getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
					} else if (str.equalsIgnoreCase("PNG1")) {
						strJs += "',\r\n'POSTER':'";
						strJs += getCellAttribute(itemcell, itemcellattr,
								"FILENAME");
					}
				}
				strJs += "'";	
			}
	
			strJs += "}\r\n";
		}
		catch (Exception e) 
		{
			cmsLog.error("Cms -> PortalServiceImpl -> getPackageJs，异常：" + e.getMessage());
			strJs = "";
		}
		return strJs;
	}
	
	// 20100425 17:45
	// 获取xml节点内容
	private String getCellTextContent(
			Document doc,
			String tagName
			)
	{
		String str = "";
		try 
		{
			NodeList cells = doc.getElementsByTagName(tagName);
			if (cells != null && cells.getLength() > 0) 
			{
				Node cell = cells.item(0);
//				Element cellattr = (Element) cells.item(0);
				str = cell.getTextContent();
			} 
			else 
			{
				str = "";
			}
		} 
		catch (Exception e) 
		{
			str = "";
			cmsLog.error("Cms -> PortalServiceImpl -> getCellTextContent，异常：" + e.getMessage());
			cmsLog.info("tagName:" + tagName);
		}
		return str;
	}
	
	// 20100401 14:17
	// 获取xml节点属性值
	private String getCellAttribute(Node cell, Element cellattr, String attributeName)
	{
		String str = "";
	    
	    try {
			if (cellattr.hasAttribute(attributeName)) // 判断节点有tag属性 
			{
				str = cell.getAttributes().getNamedItem(attributeName)
						.getNodeValue();
			} else {
				str = "";
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> PortalServiceImpl -> getCellAttribute，异常：" + e.getMessage());
			str = "";
		}
		return str;
	}
	
	// 20100222 15:59
	// 测试用
	private String getStrFromLocalFile(
			String xmlFilePath						// 节目包xml文件路径
			)
	{
		String strxml = "";
		
		try 
		{
			File xmlfile = new File(xmlFilePath);
			
			int last = 0;
			Long localreadbytes = Long.valueOf(0);
			byte[] bytes = new byte[1024];
			
			FileInputStream fileStreamIn = new FileInputStream(xmlfile);
			while ((last = fileStreamIn.read(bytes)) != -1) 
			{
				byte[] bs = new byte[last];
				for(int i = 0; i < last; i++)
				{
					bs[i] = bytes[i];
				}
				String str = new String(bs, "UTF-8");
				strxml += str;
			}
		} 
		catch (Exception e) 
		{
			cmsLog.error(e.getMessage());
		}
		return strxml;
	}
	
	// 20100427 15:29
	// 生成节目预告js字符串
	public String generateYgJsString(
			String date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			String seq
	) {
		// 说明：获取date开始7天的每一天的新上线节目包，生成js字符串
		// 		把新上线的节目包的海报文件复制到/home/下
		
		
		cmsLog.info("Cms -> PortalServiceImpl -> generateYgJsString...");
		String strJs = "";
		
		try {
			// 配置文件
			String filecodeOnline = "ENCRYPTFILE";
			String stclasscodeOnline = "Online";
			String stclasscodeCaOnline = "CaOnline";
			String stclasscodeNearOnline = "NearOnline";
			String portalPicFilecode = "PNG1";
			
			String pngFileId = "";
			String pngFileName = "";
			
			// 将String转成parse可以识别的InputSource
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader sr = null;
			InputSource is = null;
			Document doc = null;
//			doc.normalize();
			
			Date newddate = new Date();
			String newdate = "";
			Date ddate = fileopr.convertStringToDate(date, "yyyy-MM-dd");
			
			strJs = "var data_yg = [\r\n";
			
			
			for (int i = 0; i < 7; i++) {
				newddate.setTime(ddate.getTime() + (long)(1000 * 60 * 60 * 24 * i));
				newdate = fileopr.convertDateToString(newddate, "yyyy-MM-dd");

				cmsLog.info("查询日期(" + newdate + ")上线的节目包...");

				strJs += "{ date: '";
				strJs += newdate;
				strJs += "', items: [\r\n";
				
				// 获取newdate当日上线的节目包
				List list = progListDetailManager.getTodayOnlineProgListDetailsByDateAndDefcatseq(
								newdate, 
								seq);
				cmsLog.info("共有" + list.size() + "个节目包。");
				if (list.size() > 0) {
					String strxml = "";
					for (int j = 0; j < list.size(); j++) {
						Object[] rows = (Object[]) list.get(j);
						ProgListDetail pld = (ProgListDetail) rows[0];
						ProgPackage pp = (ProgPackage) rows[1];

						cmsLog.info("处理第" + (j + 1) + "个节目包...");
						cmsLog.info("节目包ID：" + pp.getProductid());
						cmsLog.info("节目包名称：" + pp.getProductname());

						
						cmsLog.info("生成节目预告js字符串...");
						// 去掉utf-8 bom头
						strxml = pp.getPpxml();
						strxml = strxml.substring(strxml.indexOf("<?xml"), strxml.length());
						sr = new StringReader(strxml);
						is = new InputSource(sr);
						doc = builder.parse(is);
						doc.normalize();
						
						strJs += "{ TITLEBRIEF : '";
						strJs += getCellTextContent(doc, "titlebrief");
						strJs += "', PROGTYPE : '";
						strJs += getCellTextContent(doc, "audiolanguage");
						strJs += "', DESCRIPTION : '";
						strJs += getCellTextContent(doc, "description");
						strJs += "', POSTER : '";
						NodeList itemcells = doc.getElementsByTagName("FILE");
						for(int k = 0; k < itemcells.getLength(); k++) {
							Node itemcell = itemcells.item(k);
						    Element itemcellattr = (Element)itemcells.item(k);
						    String str = getCellAttribute(itemcell, itemcellattr, "FILECODE");
						    if(str.equalsIgnoreCase("PNG1")) {
						    	pngFileName = getCellAttribute(itemcell, itemcellattr, "FILENAME");
						    	pngFileId = getCellAttribute(itemcell, itemcellattr, "PROGFILEID");
						    	
						    	strJs += pngFileName;
						    	break;
						    }
						}
						strJs += "'}\r\n,";
						
						
						
						cmsLog.info("复制节目包的海报文件到/home/下...");
						String pngSource = "";
						String pngDest = "";
	
						// 返回：List
						// 1 - String 目标路径()  格式："smb://hc:hc@172.23.19.66/公用/"
						// 2 - List<Object[]>
						//			(AmsStorage)Object[0]
						//			(AmsStorageDir)Object[1]
						//			(AmsStorageClass)Object[2]
						List destpaths = packageFilesManager.getDestPathByFilecodeStclasscode(
								filecodeOnline, 
								stclasscodeOnline
								);
						if(destpaths != null && destpaths.size() >= 2) {
							AmsStorage amsstdest = null;
							AmsStorageDir amsstddest = null;
//							destPath = (String)destpaths.get(0);
							List rowdests = (List)destpaths.get(1);
							if(rowdests != null && rowdests.size() > 0) {
								Object[] rowdest = (Object[])rowdests.get(0);
								amsstdest = (AmsStorage)rowdest[0];
								amsstddest = (AmsStorageDir)rowdest[1];
								
								pngDest = "smb://";
								pngDest += amsstdest.getLoginname();
								pngDest += ":";
								pngDest += amsstdest.getLoginpwd();
								pngDest += "@";
								pngDest += amsstdest.getStorageip();
								pngDest += fileopr.checkPathFormatFirst(amsstdest.getMappath(), '/');
								pngDest = fileopr.checkPathFormatRear(pngDest, '/');
								pngDest += "other/home/";
								pngDest += pngFileName;
							}
						}
						
						// 处理海报文件
						cmsLog.info("查询节目包海报源路径...");
						// 查询海报1文件的源路径
						// 返回：List
						// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
						// 2 - List<Object[]>
						// 		(ProgramFiles)Object[0]
						// 		(AmsStorage)Object[1]
						// 		(AmsStoragePrgRel)Object[2]
						// 		(AmsStorageDir)Object[3]
						// 		(AmsStorageClass)Object[3]
						ProgramFiles programfilepng = null;
						AmsStorage amsstpng = null;
						AmsStoragePrgRel amsstprpng = null;
						List sourcePaths = packageFilesManager.getSourcePathByProgfileidStclasscode(
								pngFileId, 
								stclasscodeNearOnline
								);
						if(sourcePaths != null && sourcePaths.size() >= 2) {
							pngSource = (String)sourcePaths.get(0);
							List rowpngs = (List)sourcePaths.get(1);
							if(rowpngs != null && rowpngs.size() > 0) {
								Object[] rowpng = (Object[])rowpngs.get(0);
								programfilepng = (ProgramFiles)rowpng[0];
								amsstpng = (AmsStorage)rowpng[1];
								amsstprpng = (AmsStoragePrgRel)rowpng[2];
							}
						} else {
							cmsLog.warn("获得海报文件源路径失败。");
							pngSource = "";
						}
						
						cmsLog.info("检查海报路径...");
						cmsLog.info("源 - " + pngSource);
						cmsLog.info("目标 - " + pngDest);
						if(pngSource != null
							&& !pngSource.equalsIgnoreCase("")
							&& pngDest != null
							&& !pngDest.equalsIgnoreCase("")
						) {
							int copyret = fileopr.copyFileFromSmbToSmb(pngSource, pngDest);
							if(copyret == 0) {
								cmsLog.info("复制海报成功。");
							} else {
								cmsLog.warn("复制海报失败。");
							}
						} else {
							cmsLog.warn("海报路径有误，不复制海报。");
						}
						
						pngFileName = "";
				    	pngFileId = "";
					}
				}
				if(strJs != null && !strJs.equalsIgnoreCase("")) {
					// Js结束字符串
					if(strJs.charAt(strJs.length() - 1) == ',') {
				    	strJs = strJs.substring(0, strJs.length() - 1);
				    }
				}
				strJs += "]}\r\n,";
			}
			
			if(strJs != null && !strJs.equalsIgnoreCase("")) {
				// Js结束字符串
				if(strJs.charAt(strJs.length() - 1) == ',') {
			    	strJs = strJs.substring(0, strJs.length() - 1);
			    }
			}
			strJs += "];";
		} catch (Exception e) {
			strJs = "";
			cmsLog.error("Cms -> PortalServiceImpl -> generateYgJsString，异常：" + e.getMessage());
		}
		cmsLog.info("Cms -> PortalServiceImpl -> generateYgJsString returns.");
		return strJs;
	}
	
	/**
	 * 生成节目预告JS文件, 并写ProgListFile记录
	 * @param dateStr 格式:2010-09-12
	 * @param cmsSite
	 * @param days
	 * @param inputManId
	 * @throws ParseException
	 * @throws DocumentException
	 */
	private void generateNoticeXml(String dateStr, CmsSite cmsSite,
			int days, String inputManId) throws ParseException, DocumentException {
		cmsLog.info("Cms -> PortalServiceImpl -> generateNoticeJs...  " 
				+ cmsSite.getSitename() + "品牌生成节目预告xml");
		String schedule = dateStr.replaceAll("-", "") + "000000";
		
		// 创建xml文档对象
		org.dom4j.Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		org.dom4j.Element rootProgPackageSchedule, siteElement, proglistElement, 
				columnElement, progPackageElement, fileElement, progPackageInfoElement,
				productInfoElement,	casystemElement, cdcadescElement, rightdescElement, 
				portalInfoElement;
		
		// 创建根节点
		rootProgPackageSchedule = doc.addElement("ProgPackageSchedule");
		
		// 创建品牌节点
		siteElement = rootProgPackageSchedule.addElement("Site");
		// 设置品牌节点属性
 		siteElement.addAttribute("ScheduleDate", dateStr);
		siteElement.addAttribute("BroadcastTime", "");
		siteElement.addAttribute("SiteCode", cmsSite.getSiteCode());
		siteElement.addAttribute("SiteName", cmsSite.getSitename());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.parseDate("yyyy-MM-dd", dateStr));
		// 循环创建N天的编单
		for (int i = 0; i < days; i++) {
			// 创建编单节点
			proglistElement = siteElement.addElement("Proglist");
			//设置编单节点属性
			calendar.add(Calendar.DAY_OF_MONTH, 0 == i ? 0 : 1);
			String currScheduleDate = DateUtil.getDateStr("yyyy-MM-dd", calendar.getTime());
			// 取得当前编单ID
			String currScheduleID = currScheduleDate.replaceAll("-", "") + "000000";
			
			cmsLog.debug("当前编单日期为: " + currScheduleDate);
			proglistElement.addAttribute("ScheduleDate", currScheduleDate);
			
			/**
			 *  该死的需求, 现要求合并品牌预告JS. 
			 *  实现思路, 以前分品牌生成预告, 改为一个品牌查询出所有栏目, 将所有栏目存放到一个品牌中生成预告JS
			 *  HuangBo update by 2012年07月23日 13时58分
			 */
			List<PortalColumn> portalColumns = this.portalColumnManager
					.queryPortalColumnsByScheduleDate(currScheduleID, cmsSite.getSiteCode());
			
			cmsLog.debug("当前日期编单[ " + currScheduleDate + " ]栏目数量为: " + portalColumns.size());
			
			// 循环每天编单的栏目编单
			for (PortalColumn portalColumn : portalColumns) {
				// 创建编单下栏目节点
				columnElement = proglistElement.addElement("Column");
				// 设置编单下栏目节点属性
				columnElement.addAttribute("DefCatCode", portalColumn.getDefcatcode());
				columnElement.addAttribute("Name", portalColumn.getPublishfilename());
				columnElement.addAttribute("Type", portalColumn.getArchivedays().toString());
				
				/**
				 * 获取当前栏目编单下所有节目包信息, 是否生成节目预告判断标识存入节目包lengthtc属性中..
				 * 产品信息存入节目包updatemanid属性之中
				 */
				List<ProgPackage> progPackages = this.progPackageManager
						.queryByScheduleDateAndColumn(currScheduleID, 
								portalColumn.getColumnclassid());
				
				cmsLog.debug("取得当前栏目[ " + portalColumn.getColumnname() + " ]编单下生成节目预告节目包数量: " + progPackages.size());
				
				// 循环栏目编单下的节目包
				org.dom4j.Node node = null;
				for (ProgPackage progPackage : progPackages) {
//					packageDocument = DocumentHelper.parseText(progPackage.getPpxml());
					/**
					 * 判断节目包lengthtc属性的值, 是否生成节目预告. 为Y则生成 节目预告.
					 */
					if ("Y".equalsIgnoreCase(progPackage.getLengthtc())) {
						// 创建节目包节点
						progPackageElement = columnElement.addElement("ProgPackage");
						// 设置节目包节点属性
						progPackageElement.addAttribute("PPID", progPackage.getProductid());
						progPackageElement.addAttribute("STBTITLE", progPackage.getProductname());
						progPackageElement.addAttribute("KEYIDS", this.productInfoManager.
								queryProductInfoKeyIdByScheduleDateAndProgPackageId(
										currScheduleID, progPackage.getProductid()));
						
						List<ProgramFiles> programFiles = null;
						List<String> fileStyleIds = null;
						/**
						 * HuangBo update by 2010年11月4日 14时57分
						 * 该死的计划有变, 修改每天都按文件样式规则取节目包文件.
						 * 当天取Code = 2的文件样式规则, 其它日取Code = 1的文件样式规则
						 */
						if (currScheduleDate.equalsIgnoreCase(dateStr)) {
							// 1. 创建FILE节点
							fileStyleIds = this.fileStyleManager.getValidateFileStyleId(
									2, progPackage.getStyleid());
							
							if ("R".equals(progPackage.getProgtype())) {
								fileStyleIds.add("RMZIP");
							}
							
							programFiles = this.programFilesManager.queryProgramFilesByProgPackageID(
									progPackage.getProductid(), fileStyleIds);
						} else {
							// 1. 创建FILE节点
							fileStyleIds = this.fileStyleManager.getValidateFileStyleId(
									1, progPackage.getStyleid());
							
							List<ProgramFiles> tempProgramFiles = this.programFilesManager.queryProgramFilesByProgPackageID(
									progPackage.getProductid(), fileStyleIds);
							
							/**
							 * HuangBo update by 2011年3月24日 9时55分
							 * 如果不是当前日期, 则不需要主文件
							 */
							programFiles = new ArrayList<ProgramFiles>();
							for (ProgramFiles programFile : tempProgramFiles) {
								if (1 != programFile.getProgrank()) {
									programFiles.add(programFile);
								}
							}
						}
						cmsLog.debug("取得当前节目包[ " + progPackage.getProductid() + " : " 
								+ progPackage.getProductname() + " ]下文件数量: " + programFiles.size());
						// 取得节目包主文件contentID
						String contentID = this.programFilesManager.queryContentIdByProgPackage(
								progPackage.getProductid());
						
						for (ProgramFiles programFile : programFiles) {
							fileElement = progPackageElement.addElement("FILE");
							fileElement.addAttribute("PROGFILEID", programFile.getProgfileid());
							fileElement.addAttribute("FILENAME", programFile.getFilename());
							fileElement.addAttribute("CONTENTID", contentID);
							fileElement.addAttribute("PROGRANK", programFile.getProgrank().toString());
							fileElement.addAttribute("FILECODE", programFile.getFilecode());
							fileElement.addAttribute("FILETYPEID", programFile.getFiletypeid());
							fileElement.addAttribute("SRC", "");
							//TODO 以下两个属性值待定
							fileElement.addAttribute("UPDATEFLAG", "");	
							fileElement.addAttribute("UPDATETIME", "");
						}
					}
					
					/**
					 * HuangBo update by 2010年11月5日 9时30分
					 * 需求修改, 生成节目包xml的时候, 在一天的编单中, 如果节目包属于两个或两个以上栏目.
					 * 则节目包xml只生成一份, 但其中的PortalInfo节点, 则对应每个栏目生成一个节点
					 */
					// 生成节目包xml
//					this.generateProgPackageXml(progPackage, portalColumn);
				}
			}
		}
		
		// 获取一级库节目预告目标路径
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"YGJS", "Online");
		if (null == descPath || 2 > descPath.size()) {
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		
		String onlineNoticeJsPath = (String) descPath.get(0);
		onlineNoticeJsPath = fileopr.checkPathFormatRear(onlineNoticeJsPath, '/');
		onlineNoticeJsPath += dateStr.replaceAll("-", "") + "000000/";
		cmsLog.debug("取得一级库节目预告JS存放目录: " + onlineNoticeJsPath);
		
		Xml2Json xml2Json = new Xml2Json(doc);
		
		if (0 == fileopr.createSmbFileGb2312(
				onlineNoticeJsPath + cmsSite.getEpgpath(), 
						cmsSite.getEpgip() + xml2Json.getJson() + ";")) {
			// 判断当天是否已生成节目包JS记录, 如果已生成将其有效状态改为无效, 再重新生成文件记录
			ProgListFile oldFile = this.progListFileManager.existPackageOrNoticeJs(
					dateStr.replaceAll("-", "") + "000000", cmsSite.getEpgpath());
			if (null != oldFile) {
//				oldFile = (ProgListFile) this.progListFileManager.getById(
//						oldFile.getColumnfileid());
				oldFile.setState1(1l);
				this.progListFileManager.update(oldFile);
			}
			
			ProgListFile progListFile = new ProgListFile();
			progListFile.setScheduledate(schedule);
			progListFile.setFilename(cmsSite.getEpgpath());
			progListFile.setFiletype(6l); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
			progListFile.setDate1(new Date());
			progListFile.setState1(0l);
			progListFile.setIdAct("");
			progListFile.setColumnxml(doc.asXML());
			progListFile.setColumnjs(xml2Json.getJson());
			progListFile.setInputmanid(inputManId);
			progListFile.setInputtime(new Date());
			
			this.progListFileManager.save(progListFile);
			
			AmsStoragePrgRel amsStoragePrgRel = this.amsstorageprgrelManager
					.queryByFilePathAndFileName(
							schedule + "/", cmsSite.getEpgpath());
			if (null == amsStoragePrgRel) {		
				// 如果位置表记录为空, 则表示该节目预告以前没有生成过. 生成新的位置表记录
				List<Object[]> list = (List<Object[]>) descPath.get(1);
				Object[] objects = list.get(0);
				AmsStorage storage = (AmsStorage) objects[0];
				AmsStorageDir storageDir = (AmsStorageDir) objects[1];
				
				amsStoragePrgRel = new AmsStoragePrgRel();
				amsStoragePrgRel.setStglobalid(storage.getStglobalid());
				amsStoragePrgRel.setStdirglobalid(storageDir.getStdirglobalid());
				amsStoragePrgRel.setProgfileid(progListFile.getColumnfileid());
				amsStoragePrgRel.setFtypeglobalid("YGJS");
				amsStoragePrgRel.setFilename(cmsSite.getEpgpath());
				amsStoragePrgRel.setUploadtime(new Date());
				amsStoragePrgRel.setInputtime(new Date());
				amsStoragePrgRel.setInputmanid(inputManId);
				amsStoragePrgRel.setFiledate(new Date());
				amsStoragePrgRel.setFilepath(schedule + "/");
				this.amsstorageprgrelManager.save(amsStoragePrgRel);
			} else {
				amsStoragePrgRel.setProgfileid(progListFile.getColumnfileid());
				amsStoragePrgRel.setUploadtime(new Date());
				amsStoragePrgRel.setFiledate(new Date());
				amsStoragePrgRel.setInputmanid(inputManId);
				this.amsstorageprgrelManager.update(amsStoragePrgRel);
			}
		}
	}
	
	/**
	 * 按照编单日期, 生成当天编单的所有节目包XML字符串
	 * @param dateStr 编单日期, 格式:2010-09-09
	 * @throws DocumentException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws FileUploadException 
	 */
	private synchronized void generateProgPackageXml(String dateStr, String inputManId) 
			throws DocumentException, SecurityException, 
					IllegalArgumentException, NoSuchMethodException, 
							IllegalAccessException, InvocationTargetException, 
									FileUploadException {
		// 取得当前编单ID
		String currSchedule = dateStr.replaceAll("-", "");
		
		// 获利节目包js文件在一级库的存放路径
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"PPJS", "Online");
		if (null == descPath || 2 > descPath.size()) {
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		
		String onlineJsPath = (String) descPath.get(0);
		onlineJsPath = fileopr.checkPathFormatRear(onlineJsPath, '/');
		onlineJsPath += currSchedule + "000000/";
		cmsLog.debug("取得一级库JS存放目录: " + onlineJsPath);
		
		// 获取ProgListFile表当前最大主键
//		String currMaxPK = this.progListFileManager.getProgListFileCurrMaxPK();
		
		// 查询出指定编单日下所有节目包信息
		List<ProgPackage> progPackages = this.progPackageManager.queryByScheduleDate(
				currSchedule + "000000");
		// 将节目包进行分组
		Map<String, List<ProgPackage>> groupMap = ListUtil.groupByProperty(
				progPackages, "productid");
		
		// 遍历分组后的第一个节目包
		for (String string : groupMap.keySet()) {
			
			List<ProgPackage> groupPackages = groupMap.get(string);
			org.dom4j.Document packageDocument = DocumentHelper.parseText(
					groupPackages.get(0).getPpxml().replaceAll("\"", "'").replaceAll("&quot;", "&apos;"));
			org.dom4j.Element packageRoot = packageDocument.getRootElement();
			// 查询产品信息, 添加到节目包ppXml根节点下
			org.dom4j.Document productInfoXmlDoc = DocumentHelper.parseText(
					this.productInfoManager.queryProductInfoXMLById(
							groupPackages.get(0).getIssuedate()));
			
			// 将产品信息描述XML添加到节目包根节点中
			packageRoot.add(productInfoXmlDoc.getRootElement());
			
			//TODO 此处可以优化, 目前是为每个节目包查询页面包装信息, 可以改进为只为连续剧查询页面包装信息
			PtpPgpRel ptpPgpRel = this.ptpPgpRelManager.queryPortalInfoByProgPackageId(
					string);
			// 一个节目包如果被编单进多个栏目, 则一个节目包对应多个栏目关系, 因此需要生成多个PortalInfo节点
			for (ProgPackage progPackage : groupPackages) {
				
				// 创建PORTALINFO节点
				org.dom4j.Element portalInfoElement = packageRoot.addElement("PORTALINFO");
				// 设置PORTALINFO节点属性
				portalInfoElement.addAttribute("PortalColumnName", progPackage.getLicensingWindowEnd());
				portalInfoElement.addAttribute("PortalColumnCode", progPackage.getShootdate());
				portalInfoElement.addAttribute("Priority", "");
				portalInfoElement.addAttribute("OnlineDate", "");
				portalInfoElement.addAttribute("OfflineDate", "");
				portalInfoElement.addAttribute("GroupTag", null == ptpPgpRel ? ""
						: ptpPgpRel.getPtpid());
				portalInfoElement.addAttribute("GroupName", null == ptpPgpRel ? ""
						: ptpPgpRel.getRemark());
				portalInfoElement.addAttribute("GroupDescription",
						StringUtils.isBlank(progPackage.getDescription()) 
								? progPackage.getDescription() 
										: progPackage.getDescription().replaceAll("\"", "'"));
				portalInfoElement.addAttribute("ItemCount", null == ptpPgpRel ? ""
						: ptpPgpRel.getSequence().toString());
				portalInfoElement.addAttribute("ItemNo", null == ptpPgpRel ? "" 
						: ptpPgpRel.getEpicodeid().toString());
			}
			
			/**
			 * 获取当前即将生成ProgListFile记录主键
			 */
//			String strMaxPK = this.getPK.GetTablePK("ProgListFile", currMaxPK);
			Xml2Json xml2Json = new Xml2Json(packageDocument);
			CmsConfig cmsConfig = new CmsConfig();
			String varName = cmsConfig.getPropertyByName("PackageJsVariableName");
			// 创建文件, 并生成ProgListFile记录
			if (0 == fileopr.createSmbFileGb2312(
					onlineJsPath + string + ".js", 
							varName + xml2Json.getJson() + ";")) {
				// 判断当天是否已生成节目包JS记录, 如果已生成将其有效状态改为无效, 再重新生成文件记录
				ProgListFile oldFile = this.progListFileManager.existPackageOrNoticeJs(
						currSchedule + "000000", string);
				if (null != oldFile) {
//					oldFile = (ProgListFile) this.progListFileManager.getById(
//							oldFile.getColumnfileid());
					oldFile.setState1(1l);
					this.progListFileManager.update(oldFile);
				}
				
				ProgListFile progListFile = new ProgListFile();
				progListFile.setScheduledate(currSchedule + "000000");
				progListFile.setFilename(string + ".js");
				progListFile.setFiletype(7L); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
				progListFile.setDate1(new Date());
				progListFile.setState1(0L);
				progListFile.setIdAct("");
				progListFile.setPop(string); //TODO 待确定, 将节目包ID存入ProgListFile中, 及是否需检查文件已生成即不重复生成
				progListFile.setColumnxml(packageDocument.asXML());
				progListFile.setColumnjs(xml2Json.getJson());
				progListFile.setInputmanid(inputManId);
				progListFile.setInputtime(new Date());
				
				this.progListFileManager.save(progListFile);
				
				AmsStoragePrgRel amsStoragePrgRel = this.amsstorageprgrelManager
						.queryByFilePathAndFileName(
								currSchedule + "000000/", string + ".js");
				if (null == amsStoragePrgRel) {
					// 如果位置表记录为空, 则表示该节目预告以前没有生成过. 生成新的位置表记录
					List<Object[]> list = (List<Object[]>) descPath.get(1);
					Object[] objects = list.get(0);
					AmsStorage storage = (AmsStorage) objects[0];
					AmsStorageDir storageDir = (AmsStorageDir) objects[1];
					
					amsStoragePrgRel = new AmsStoragePrgRel();
					amsStoragePrgRel.setStglobalid(storage.getStglobalid());
					amsStoragePrgRel.setStdirglobalid(storageDir.getStdirglobalid());
					amsStoragePrgRel.setPrglobalid(string);
					amsStoragePrgRel.setProgfileid(progListFile.getColumnfileid());
					amsStoragePrgRel.setFtypeglobalid("PPJS");
					amsStoragePrgRel.setFilename(string + ".js");
					amsStoragePrgRel.setUploadtime(new Date());
					amsStoragePrgRel.setInputtime(new Date());
					amsStoragePrgRel.setInputmanid(inputManId);
					amsStoragePrgRel.setFiledate(new Date());
					amsStoragePrgRel.setFilepath(currSchedule + "000000/");
					this.amsstorageprgrelManager.save(amsStoragePrgRel);
				} else {
					amsStoragePrgRel.setProgfileid(progListFile.getColumnfileid());
					amsStoragePrgRel.setPrglobalid(string);
					amsStoragePrgRel.setUploadtime(new Date());
					amsStoragePrgRel.setFiledate(new Date());
					amsStoragePrgRel.setInputmanid(inputManId);
					this.amsstorageprgrelManager.update(amsStoragePrgRel);
				}
				
				
				this.progListDetailManager.updateJsFileId(currSchedule + "000000", 
						string, progListFile.getColumnfileid());
			} else {
				throw new FileUploadException( " 节目包JS文件创建失败! " );
			}
//			/**
//			 * 此处将最新的最大的主键值赋于当前最大主键的引用, 如果不写会报错
//			 */
//			currMaxPK = strMaxPK;
		}
	}

	private synchronized void generatePackageXml(String dateStr,
			String inputManId) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			DocumentException, NoSuchFieldException, FileUploadException {
		
		// 取得当前编单ID
		String currSchedule = dateStr.replaceAll("-", "");
		
		// 获利节目包js文件在一级库的存放路径
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"PPJS", "Online");
		if (null == descPath || 2 > descPath.size()) {
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}

		String onlineJsPath = (String) descPath.get(0);
		onlineJsPath = fileopr.checkPathFormatRear(onlineJsPath, '/');
		onlineJsPath += currSchedule + "000000/";
		cmsLog.debug("取得一级库JS存放目录: " + onlineJsPath);
		
		// 查询出指定编单日下所有节目包信息
		List<ProgPackage> progPackages = this.progPackageManager.queryByScheduleDate(
				currSchedule + "000000");
		// 将节目包进行分组
		Map<String, List<ProgPackage>> groupMap = ListUtil.groupByProperty(
				progPackages, "productid");
		
		//TODO .replaceAll("\"", "'").replaceAll("&quot;", "&apos;")
		for (String progPackageId : groupMap.keySet()) {
			List<ProgPackage> groupPackages = groupMap.get(progPackageId);
			org.dom4j.Document packageDocument = DocumentHelper.parseText(
					groupPackages.get(0).getPpxml());
			TProductInfo productInfo = this.productInfoManager.getTProductInfoById(groupPackages.get(0).getIssuedate());
			
			List<org.dom4j.Node> nodes = packageDocument.selectNodes("//GP/*");

			StringBuilder matadataAttributeBuilder = new StringBuilder();
	        for (org.dom4j.Node node : nodes) {
	        	matadataAttributeBuilder.append(MessageFormat.format(this.ATTRIBUTE, 
	            		StringUtils.trim(node.selectSingleNode("@CHNAME").getStringValue().replaceAll("　", "")), 
	            		StringUtils.abbreviate(node.getText().replaceAll("\"", "'"), 240)));
	        	matadataAttributeBuilder.append(",");
	        }
	        // METADATA
	        List<String> encryptCode = Arrays.asList(
	        		new CmsConfig().getPropertyByName("UnEncryptCode").split(","));
	        matadataAttributeBuilder.append(MessageFormat.format(this.ATTRIBUTE, "加密", 
	        		encryptCode.containsAll(
	        				Arrays.asList(productInfo.getKeyId().split(","))) ? "否" : "是"));
	        String metadata = MessageFormat.format(this.ATTRIBUTEOBJECT, "METADATA", 
	        		matadataAttributeBuilder.toString());
	        
	        // PROGFILE 
	        List<AmsStoragePrgRel> storagePrgRels = 
	        		this.amsstorageprgrelManager.queryAmsStoragePrgRels(progPackageId, this.ONLINESTORAGEID);
	        
	        Map<String, List<AmsStoragePrgRel>> fileGroupMap = ListUtil.groupByProperty(
	        		storagePrgRels, "ftypeglobalid");
	        StringBuilder progFileBuilder = new StringBuilder();
	        for (String fileTypeId : fileGroupMap.keySet()) {
	        	List<AmsStoragePrgRel> groupStoragePrgRels = fileGroupMap.get(fileTypeId); 
	        	if ("H264".equals(fileTypeId)) {
	        		for (AmsStoragePrgRel amsStoragePrgRel : groupStoragePrgRels) {
						progFileBuilder.append(MessageFormat.format(this.ATTRIBUTE, 
								30 == amsStoragePrgRel.getProgfileid().length() 
								? "CLIP" : "H264", amsStoragePrgRel.getFilename()));
						progFileBuilder.append(",");
	        		}
				} else if ("PNG".equals(fileTypeId)
						|| "WGZM".equals(fileTypeId)) {
					StringBuilder tempFileNameBuilder = new StringBuilder();
					for (AmsStoragePrgRel amsStoragePrgRel : groupStoragePrgRels) {
						tempFileNameBuilder.append(MessageFormat.format(
								this.STRING, amsStoragePrgRel.getFilename()));
						tempFileNameBuilder.append(",");
					}
					progFileBuilder.append(MessageFormat.format(this.ARRAYOBJECT, fileTypeId,
							StringUtils.chomp(tempFileNameBuilder.toString(), ",")));
					progFileBuilder.append(",");
				} else if ("PPJS".equals(fileTypeId)) {
					AmsStoragePrgRel amsStoragePrgRel = groupStoragePrgRels.get(0);
					progFileBuilder.append(MessageFormat.format(this.ATTRIBUTE, 
							fileTypeId, amsStoragePrgRel.getFilename()));
					progFileBuilder.append(",");
				} else {
					for (AmsStoragePrgRel amsStoragePrgRel : groupStoragePrgRels) {
						progFileBuilder.append(MessageFormat.format(this.ATTRIBUTE, 
								fileTypeId, amsStoragePrgRel.getFilename()));
						progFileBuilder.append(",");
					}
				}
			}
	        String progfile = MessageFormat.format(this.ATTRIBUTEOBJECT, "PROGFILE", 
	        		StringUtils.chomp(progFileBuilder.toString(), ","));
	        
	        // PORTALINFO
	        List<PortalColumn> portalColumns = this.portalColumnManager.queryPortalColumns(progPackageId);
//	        List<String> portalColumnStrings = ListUtil.getPropertiesList(portalColumns, "publishfilename");
	        List<String> portalColumnStrings = ListUtil.getPropertiesList(portalColumns, "defcatcode");
	        Set<String> set = new HashSet<String>(portalColumnStrings);
	        StringBuilder portalColumnNameBuilder = new StringBuilder();
	        for (String string : set) {
	        	portalColumnNameBuilder.append(
	        			MessageFormat.format(this.STRING, string));
	        	portalColumnNameBuilder.append(",");
			}
	        String portalinfo = MessageFormat.format(this.ARRAYOBJECT, "PORTALINFO", 
	        		StringUtils.chomp(portalColumnNameBuilder.toString(), ","));
	        
	        // APP
	        List<ProgramFiles> programFiles = this.programFilesManager.queryProgramFilesByProgPackageID(progPackageId);
	        String size = null;
	        for (ProgramFiles programFile : programFiles) {
	        	cmsLog.debug("ProgramFiles.progrank: " + programFile.getProgrank());
				if (1 == programFile.getProgrank()) {
					size = programFile.getContentfilesize();
				}
			}
	        String app = MessageFormat.format(this.ATTRIBUTEOBJECT, "APP",
	        		MessageFormat.format(this.ATTRIBUTE, "PROGPACKAGEID", progPackageId) + "," + 
	        		MessageFormat.format(this.ATTRIBUTE, "SUMFILESIZE", size));
	        
	        // PRODUCTINFO
	        org.dom4j.Document productInfoDocument = DocumentHelper.parseText(
	        		productInfo.getProductXml()
	        );
	        Xml2Json xml2Json =  new Xml2Json(productInfoDocument);
	        String productinfo = MessageFormat.format(this.ARRAYOBJECT, "PRODUCTINFO", xml2Json.getJson());
	        
	        StringBuilder ppjsBuilder = new StringBuilder(
	        		new CmsConfig().getPropertyByName("PackageJsVariableName"));
	        ppjsBuilder.append("{");
	        ppjsBuilder.append(metadata);
	        ppjsBuilder.append(",");
	        ppjsBuilder.append(progfile);
	        ppjsBuilder.append(",");
	        ppjsBuilder.append(portalinfo);
	        ppjsBuilder.append(",");
	        ppjsBuilder.append(app);
	        ppjsBuilder.append(",");
	        ppjsBuilder.append(productinfo);
	        ppjsBuilder.append("};");
	        
//	        fileopr.createSmbFile(onlineJsPath + progPackageId + ".xml", groupPackages.get(0).getPpxml());
	        
	     // 创建文件, 并生成ProgListFile记录
			if (0 == fileopr.createSmbFileGb2312(
					onlineJsPath + progPackageId + ".js", 
					ppjsBuilder.toString())) {
				// 判断当天是否已生成节目包JS记录, 如果已生成将其有效状态改为无效, 再重新生成文件记录
				ProgListFile oldFile = this.progListFileManager.existPackageOrNoticeJs(
						currSchedule + "000000", progPackageId);
				if (null != oldFile) {
//					oldFile = (ProgListFile) this.progListFileManager.getById(
//							oldFile.getColumnfileid());
					oldFile.setState1(1l);
					this.progListFileManager.update(oldFile);
				}
				
				ProgListFile progListFile = new ProgListFile();
				progListFile.setScheduledate(currSchedule + "000000");
				progListFile.setFilename(progPackageId + ".js");
				progListFile.setFiletype(7L); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
				progListFile.setDate1(new Date());
				progListFile.setState1(0L);
				progListFile.setIdAct("");
				progListFile.setPop(progPackageId); //TODO 待确定, 将节目包ID存入ProgListFile中, 及是否需检查文件已生成即不重复生成
				progListFile.setColumnxml("新JS规则, 不生成XML");
				progListFile.setColumnjs(ppjsBuilder.toString());
				progListFile.setInputmanid(inputManId);
				progListFile.setInputtime(new Date());
				
				this.progListFileManager.save(progListFile);
				
				AmsStoragePrgRel amsStoragePrgRel = this.amsstorageprgrelManager
						.queryByFilePathAndFileName(
								currSchedule + "000000/", progPackageId + ".js");
				if (null == amsStoragePrgRel) {
					// 如果位置表记录为空, 则表示该节目预告以前没有生成过. 生成新的位置表记录
					List<Object[]> list = (List<Object[]>) descPath.get(1);
					Object[] objects = list.get(0);
					AmsStorage storage = (AmsStorage) objects[0];
					AmsStorageDir storageDir = (AmsStorageDir) objects[1];
					
					amsStoragePrgRel = new AmsStoragePrgRel();
					amsStoragePrgRel.setStglobalid(storage.getStglobalid());
					amsStoragePrgRel.setStdirglobalid(storageDir.getStdirglobalid());
					amsStoragePrgRel.setPrglobalid(progPackageId);
					amsStoragePrgRel.setProgfileid(progListFile.getColumnfileid());
					amsStoragePrgRel.setFtypeglobalid("PPJS");
					amsStoragePrgRel.setFilename(progPackageId + ".js");
					amsStoragePrgRel.setUploadtime(new Date());
					amsStoragePrgRel.setInputtime(new Date());
					amsStoragePrgRel.setInputmanid(inputManId);
					amsStoragePrgRel.setFiledate(new Date());
					amsStoragePrgRel.setFilepath(currSchedule + "000000/");
					this.amsstorageprgrelManager.save(amsStoragePrgRel);
				} else {
					amsStoragePrgRel.setProgfileid(progListFile.getColumnfileid());
					amsStoragePrgRel.setPrglobalid(progPackageId);
					amsStoragePrgRel.setUploadtime(new Date());
					amsStoragePrgRel.setFiledate(new Date());
					amsStoragePrgRel.setInputmanid(inputManId);
					this.amsstorageprgrelManager.update(amsStoragePrgRel);
				}
				
				
				this.progListDetailManager.updateJsFileId(currSchedule + "000000", 
						progPackageId, progListFile.getColumnfileid());
			} else {
				throw new FileUploadException( " 节目包JS文件创建失败! " );
			}
		}
	}
	
	/**
	 * 终端审校调用接口, 生成节目包JS和节目预告JS
	 * @param dateStr 编单日期,  格式: 2010-09-09
	 * @param inputManId 操作人员ID
	 * @throws Exception 如果生成失败则抛出生成js失败异常!
	 */
	public String checkByTerminal(String dateStr, Integer days, String inputManId)
			throws Exception {
		days = Integer.valueOf(new CmsConfig().getPropertyByName("ScheduleDays"));
		
		/**
		 * 新增加判断, 判断编单日往后的几天的活动是否正确, 否则不允许生JS
		 */
		/*-------------------- start ---------------------*/
		List<String> greaterProgCheckActions = this.flowActionManager.getGreaterOrLessAction(this.PROGCHECK, false);
		List<String> afterScheduleDates = DateUtil.getAfterDaysStrList(dateStr, days - 1);
		for (String string : afterScheduleDates) {
			ProgListMang mang = (ProgListMang) this.progListMangManager.getById(string);
			/**
			 * 增加编单空检测, 防止未初始化编单的情况发生
			 * HuangBo upeate by 2012年3月31日 15时1分
			 */
			if (null == mang || !greaterProgCheckActions.contains(mang.getIdAct())) {
				return "后 " + (days - 1) + " 日的编单未初始化或通过编单审校, \r\n\t\t初始化并通过审校后才能生成JS!";
			}
		}
		/*-------------------- en ---------------------*/
		
		String scheduleDate = dateStr.replaceAll("-", "") + "000000";
		ProgListMang progListMang = (ProgListMang) this.progListMangManager.getById(scheduleDate);
		if (!this.GENERATEJS.equals(progListMang.getIdAct()) 
				&& !this.FINISHGENERATEJS.equals(progListMang.getIdAct())) {
			return " 当前流程活动不正确! 请尝试刷新当前页面或检查流程活动后再进行操作! ";
		}
		
		// 1. 生成 节目包JS
		try {
			this.generatePackageXml(dateStr, inputManId);
		} catch (Exception e) {
			return " 生成节目包JS失败! ";
		}
		
		// 2. 生成节目预告js文件及节目预告ProgListFile记录
		List<CmsSite> cmsSites = this.cmsSiteManager.findAll();
		for (CmsSite cmsSite : cmsSites) {
			try {
				this.generateNoticeXml(dateStr, cmsSite, days, inputManId);
				/**
				 * 如果当前活动是生成js完成, 则不发送流程活动, 必须通过界面的发送按钮, 来进行流程活动的发送
				 */
				if (!this.FINISHGENERATEJS.equals(progListMang)) {
					this.sendProcessAction(dateStr, this.GENERATEJS, inputManId);
				}
				/**
				 *  该死的需求, 现要求合并品牌预告JS. 
				 *  实现思路, 以前分品牌生成预告, 改为一个品牌查询出所有栏目, 将所有栏目存放到一个品牌中生成预告JS
				 *  HuangBo update by 2012年07月23日 13时58分
				 */
				break;
			} catch (Exception e) {
				return " 节目预告生成失败! ";
			}
		}
		return null;
	}
	
	/**
	 * 检查终端审校是否已执行
	 * @param dateStr 编单日期, 格式: 2010-09-09
	 * @return 返回是否已生成节目预告JS和节目包JS
	 */
	public boolean isGeneratedNoticeOrPackageJs(String dateStr) {
		String scheduleDate = dateStr.replaceAll("-", "") + "000000";
		ProgListFile progListFile = this.progListFileManager.existPackageOrNoticeJs(
				scheduleDate, "6");
		return !(null == progListFile);
	}
	
	// ---------------------- 页面包装 ---------------------------------------------------
	
	// 20100414 15:11
	// 查询页面包装记录（TPORTALPACKAGE），根据名称、栏目ID、上下线标识
	public CmsResultDto getPortalPackages(
			String ptpname,				// 页面包装名称
			String columnclassid,		// 栏目ID
			Long onlinetag				// 上线标识，0 - 下线，1 - 上线
	) {
		cmsLog.info("Cms -> PortalServiceImpl -> getPortalPackages...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		try {
			cmsLog.info("输入，页面包装名称：" + ptpname);
			cmsLog.info("输入，栏目ID：" + columnclassid);
			cmsLog.info("输入，上线标识：" + onlinetag);
			onlinetag = (long) 1;
			List ptps = portalPackageManager
					.getPortalPackagesByNameColumnclassidOnlinetag(ptpname,
							columnclassid, onlinetag);
			cmsLog.info("返回" + ptps.size() + "条记录。");
			cmsResultDto.setResultObject(ptps);
		} catch (Exception e) {
			String str = "Cms -> PortalServiceImpl -> getPortalPackages，异常：" + e.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.info("Cms -> PortalServiceImpl -> getPortalPackages returns.");
		return cmsResultDto;
	}
	
	// 20100419 11:54
	// 查询页面包装下的节目包，根据页面包装、栏目id
	
	public CmsResultDto getProgPackagesByPortalPackage(
			String ptpid
	) {
		cmsLog.info("Cms -> PortalServiceImpl -> getProgPackagesByPortalPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		try 
		{
			cmsLog.info("输入，页面包装ID：" + ptpid);
//			List pps = new ArrayList();
			List retlist = new ArrayList();
			
			List pprs = ptpPgpRelManager.getPtpPgpRelByPortalPackage(ptpid);
			for (int i = 0; i < pprs.size(); i++) 
			{
				PtpPgpRel ptpPgpRel = (PtpPgpRel) pprs.get(i);

				Object[] ob = new Object[2];
				
				ProgPackage progPackage = (ProgPackage) progPackageManager.getById(ptpPgpRel.getProductid());
				if(progPackage == null)
				{
					cmsLog.warn("根据节目包ID，没有查询到节目包记录。");
					cmsLog.warn("relid：" + ptpPgpRel.getRelid());
					cmsLog.warn("节目包ID：" + ptpPgpRel.getProductid());
				}
				else
				{
					ob[0] = progPackage;
					ob[1] = ptpPgpRel;
					retlist.add(ob);
				}
			}
			cmsLog.info("返回" + pprs.size() + "条记录。");

			cmsResultDto.setResultObject(retlist);
		} 
		catch (Exception e) 
		{
			String str = "Cms -> PortalServiceImpl -> getProgPackagesByPortalPackage，异常：" + e.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.info("Cms -> PortalServiceImpl -> getProgPackagesByPortalPackage returns.");
		return cmsResultDto;
	}
	
	// 20100419 11:54
	// 查询节目包，根据节目包名称、栏目id
	
	public CmsResultDto getProgPackagesByProductnameColumnclassid(
			String productname,
			String columnclassid
	) {
		// 返回：List<Object[]>
		//			(ProgPackage)Object[0]
		//			(ProgListDetail)Object[1]
		cmsLog.info("Cms -> PortalServiceImpl -> getProgPackagesByProductnameColumnclassid...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		try {
			cmsLog.info("输入，节目包名称：" + productname);
			cmsLog.info("输入，栏目ID：" + columnclassid);
			List retlist = progListDetailManager.getProgPackagesByProductnameColumnclassid(
					productname,
					columnclassid
					);
			ArrayCollection adsf = new ArrayCollection();
			
			cmsLog.info("返回" + retlist.size() + "条记录。");
			cmsResultDto.setResultObject(retlist);
		} catch (Exception e) {
			String str = "Cms -> PortalServiceImpl -> getProgPackagesByProductnameColumnclassid，异常：" + e.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.info("Cms -> PortalServiceImpl -> getProgPackagesByProductnameColumnclassid returns.");
		return cmsResultDto;
	}
	
	// 20100414 19:42
	// 保存（新建）页面包装记录（TPORTALPACKAGE），同时保存PORTAL页面包装和节目包关系表（TPTPPGPREL）记录
	public CmsResultDto savePortalPackage(
			PortalPackage portalPackage,		// 页面包装对象
//			List ptpPgpRels,					// 页面包装与节目包关系对象
			String operatorId					// 操作人员ID
	) {
		cmsLog.info("Cms -> PortalServiceImpl -> savePortalPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		try
		{
			cmsLog.info("判断栏目是否可以保存页面包装，栏目ID：" + portalPackage.getColumnclassid());
			if(portalPackage.getColumnclassid() == null || portalPackage.getColumnclassid().equalsIgnoreCase(""))
			{
				String str = "输入参数为空，不能保存页面包装。";
				cmsLog.warn(str);
				cmsResultDto.setResultCode((long)1);
				cmsResultDto.setErrorMessage(str);
			}
			else
			{
				PortalColumn portalColumn = (PortalColumn)portalColumnManager.getById(portalPackage.getColumnclassid());
				if(portalColumn.getDefcatcode() == null || !portalColumn.getDefcatcode().equalsIgnoreCase("yx_dsj"))
				{
					String str = "栏目不是“电视剧”，不能保存页面包装。";
					cmsLog.warn(str);
					cmsResultDto.setResultCode((long)1);
					cmsResultDto.setErrorMessage(str);
				}
				else
				{
					Date date = new Date();
					portalPackage.setInputmanid(operatorId);
					portalPackage.setInputtime(date);
					portalPackage.setOnlinetag((long)1);
					portalPackageManager.save(portalPackage);
					cmsLog.info("页面包装已经保存。ID：" + portalPackage.getPtpid());
					
			//		cmsResultDto = cmsTransactionManager.savePortalPackage(
			//				portalPackageManager, 
			//				ptpPgpRelManager, 
			//				portalPackage, 
			//				ptpPgpRels, 
			//				operatorId
			//				);
				}
			}
		}
		catch (Exception e) 
		{
			String str = "Cms -> PortalServiceImpl -> savePortalPackage，异常：" + e.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.info("Cms -> PortalServiceImpl -> savePortalPackage returns.");
		return cmsResultDto;
	}
	
	// 20100414 19:49
	// 修改页面包装记录（TPORTALPACKAGE），同时保存PORTAL页面包装和节目包关系表（TPTPPGPREL）记录
	public CmsResultDto updatePortalPackage(
			PortalPackage portalPackage,		// 页面包装对象
//			List ptpPgpRels,					// 页面包装与节目包关系对象
			String operatorId					// 操作人员ID
	) {
		cmsLog.info("Cms -> PortalServiceImpl -> updatePortalPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		try {
			cmsLog.info("判断栏目是否可以更新页面包装，栏目ID："
					+ portalPackage.getColumnclassid());
			if (portalPackage.getColumnclassid() == null
					|| portalPackage.getColumnclassid().equalsIgnoreCase("")) {
				String str = "输入参数为空，不能保存页面包装。";
				cmsLog.warn(str);
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
			} else {
				PortalColumn portalColumn = (PortalColumn) portalColumnManager
						.getById(portalPackage.getColumnclassid());
				if (portalColumn.getDefcatcode() == null
						|| !portalColumn.getDefcatcode()
								.equalsIgnoreCase("yx_dsj")) {
					String str = "栏目不是“电视剧”，不能更新页面包装。";
					cmsLog.warn(str);
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
				} else {
					portalPackageManager.update(portalPackage);
					cmsLog.info("页面包装已经更新。ID：" + portalPackage.getPtpid());

					//		cmsResultDto = cmsTransactionManager.updatePortalPackage(
					//				portalPackageManager, 
					//				ptpPgpRelManager, 
					//				portalPackage, 
					//				ptpPgpRels, 
					//				operatorId
					//				);
				}
			}
		} catch (Exception e) {
			String str = "Cms -> PortalServiceImpl -> updatePortalPackage，异常：" + e.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.info("Cms -> PortalServiceImpl -> updatePortalPackage returns.");
		return cmsResultDto;
	}

	// 20100419 11:21
	// 删除页面包装
	public CmsResultDto deletePortalPackage(
			String ptpid,
			String operatorId
	) {
		cmsLog.info("Cms -> PortalServiceImpl -> deletePortalPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsResultDto = cmsTransactionManager.deletePortalPackage(
				portalPackageManager, 
				ptpPgpRelManager, 
				ptpid, 
				operatorId
				);
		
		cmsLog.info("Cms -> PortalServiceImpl -> deletePortalPackage returns.");
		return cmsResultDto;
	}

	// 20100419 11:37
	// 新增页面包装与节目包关系记录
	
	public CmsResultDto savePtpPgpRels(
			PortalPackage portalPackage,		// 页面包装对象
			List ptpPgpRels,					// 页面包装与节目包关系对象
			String operatorId					// 操作人员ID
	) {
		cmsLog.info("Cms -> PortalServiceImpl -> savePtpPgpRels...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		try {
			cmsResultDto = cmsTransactionManager.savePtpPgpRels(
					portalPackageManager, 
					ptpPgpRelManager, 
					progPackageManager,
					portalPackage,
					ptpPgpRels, 
					operatorId
					);
		} catch (Exception e) {
			String str = "Cms -> PortalServiceImpl -> savePtpPgpRels，异常：" + e.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.info("Cms -> PortalServiceImpl -> savePtpPgpRels returns.");
		return cmsResultDto;
	}
	
	// 20100419 11:37
	// 删除页面包装与节目包关系记录
	public CmsResultDto deletePtpPgpRels(
			PortalPackage portalPackage,		// 页面包装对象
			List ptpPgpRels,					// 页面包装与节目包关系对象
			String operatorId					// 操作人员ID
	) {
		cmsLog.info("Cms -> PortalServiceImpl -> deletePtpPgpRels...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		try {
			cmsResultDto = cmsTransactionManager.deletePtpPgpRels(
					portalPackageManager, ptpPgpRelManager, portalPackage,
					ptpPgpRels, operatorId);
		} catch (Exception e) {
			String str = "Cms -> PortalServiceImpl -> deletePtpPgpRels，异常：" + e.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.info("Cms -> PortalServiceImpl -> deletePtpPgpRels returns.");
		return cmsResultDto;
	}

	/**
	 * 根据编单日期字符串获取当月每一天的编单状态
	 * @param dateStr 编单日期
	 * @param currAct 当前活动编号
	 * @param operator 操作人员ID
	 * @return 返回当月每一天的编单状态是否为可编辑, 状态为0表示为可编单状态, 为-1表示不可编单状态..
	 * @throws ParseException 日期字符串格式错误解析异常
	 */
	public List<ProgListState> getState(String dateStr, String currAct, String operator) throws ParseException {
		return this.progListMangManager.getState(dateStr, currAct, operator);
	}
	
	/**
	 * 根据日期和栏目ID查询编单明细
	 * @param dateStr 编单日期
	 * @param columnId 栏目ID
	 * @return 
	 * object[0]: List<ProgListDetail>
	 * object[1]: int 状态[0:可编单状态, -1:不可编单状态]
	 */
	public Object[] queryProgList(String dateStr, String columnId, String currAct) {
		String date = dateStr.replaceAll("-", "") + "000000";
		List<ProgListDetail> list = this.progListMangManager.queryProgList(date, columnId);
		TProductInfo productInfo = null;
		for (ProgListDetail progListDetail : list) {
			if (null != progListDetail.getProductInfoID() 
					&& 12 == progListDetail.getProductInfoID().trim().length()) {
				productInfo = this.pricingManager.getProductInfoById(
						progListDetail.getProductInfoID());
				progListDetail.setProductName(productInfo.getKeyId());
				progListDetail.setEDescription((0 == productInfo.getEncryptState() && -1 ==  progListDetail.getFilesizehi() 
						? "未导入" 
								: FileOperationImpl.getMainFileState(
										productInfo.getEncryptState())));
			}
			
			progListDetail.setBystr(FileOperationImpl.getState(progListDetail.getFilesizehi()));
		}
		int state = -1;
		try {
			state = this.progListMangManager.getCulumnState(date, columnId, currAct);
		} catch (Exception e) {
			cmsLog.warn("查询栏目状态出错: " + e);
		}
		return new Object[] {list, state};
	}
	
	/**
	 * 修改编单细表的排序, 用于编单定义拖动排序
	 * @param progListDetails
	 */
	public synchronized void updateProgListDetail(List<ProgListDetail> progListDetails) {
		try {
//			this.progListDetailManager.update(progListDetails.toArray());
			for (ProgListDetail p : progListDetails) {
				ProgListDetail progListDetail = (ProgListDetail) this.progListDetailManager.getById(p.getProglistdetailid());
				progListDetail.setLnum(p.getLnum());
				this.progListDetailManager.update(progListDetail);
			}
		} catch (Exception e) {
			cmsLog.warn("修改编单细表排序失败: " + e);
		}
	}
	
	/**
	 * 添加编单明细记录
	 * @param dateStr 编单日期
	 * @param progPackageIds 
	 * List<List<String>> 
	 * List[0]: 节目包ID
	 * List[1]: 是否加入节目预告
	 * @param columnId 栏目ID
	 * @param operator 操作人员ID
	 */
	public String addProgListDetail(String dateStr, List<List<String>> progPackageIds, 
			String columnId, String operator) {
		try {
			Assert.notNull(columnId, "栏目编号不能为空!");
			Assert.notEmpty(progPackageIds, "栏目编号不能为空!");
		} catch (IllegalArgumentException e) {
			cmsLog.warn(e.getMessage());
			return e.getMessage();
		}
		PortalColumn portalColumn = (PortalColumn) this.portalColumnManager.getById(columnId);
		if (!"Y".equalsIgnoreCase(portalColumn.getIsleaf())) {
			return "当前栏目不为叶子节点, 不允许编排节目包!";
		}
		
		List<ProgProduct> products = this.productManager.queryProducts(columnId);
		if (null == products || 1 > products.size()) {
			return "栏目[" + portalColumn.getColumnname() + "]未绑定任何产品, 不允许往该栏目编排节目包!";
		}
		
		List<Long> existContentIdSet = null;
		String date = dateStr.replaceAll("-", "") + "000000";
		List<String> existProgs = this.progListDetailManager.queryProgPackageIdByScheduleDate(date);
		if (0 < existProgs.size()) {
			existContentIdSet = this.progPackageManager.queryPackageCoutentID(existProgs);
		}

		boolean isContainKeyId = false;
		for (List<String> progPackageId : progPackageIds) {
			TProductInfo productInfo = this.productInfoManager.queryProductInfoByProgPackageId(progPackageId.get(0));
			if (null == productInfo) {
				return "编号为[" + progPackageId.get(0) + "]的节目包未产品归属, 请先进行产品归属再编单!";
			}
			
			for (ProgProduct progProduct : products) {
				if (-1 != productInfo.getKeyId().indexOf(progProduct.getKeyId())) {
					isContainKeyId = true;
					break;
				}
			}
			if (!isContainKeyId) {
				return "栏目[" + portalColumn.getColumnname() + "]未绑定节目包[ " + progPackageId.get(0) + " ]的产品KeyID, 不允许将节目包编入该栏目!";
			}
			
			List<String> tempProgPackageIds = new ArrayList<String>();
			tempProgPackageIds.add(progPackageId.get(0));
			List<Long> currContentIds = this.progPackageManager.queryPackageCoutentID(tempProgPackageIds);
			if (null != existContentIdSet
					&& existContentIdSet.containsAll(currContentIds)) {
				if (!existProgs.contains(progPackageId.get(0))) {
					return "节目包[ " + progPackageId.get(0) + " ] 不允许在当天编单!";
				}
			}
			
			this.progListMangManager.saveAddProgListDetail(date, 
					progPackageId.get(0), columnId, progPackageId.get(1), operator);
		}
		return null;
	}
	
	/**
	 * 删除编单明细记录
	 * @param progListDetailID 编单明细ID
	 * @param operator 操作人员ID
	 */
	public void deleteProgListDetail(List<String> progListDetailIDs, String operator) {
		for (String progListDetailID : progListDetailIDs) {
			this.progListMangManager.deleteProgListDetail(progListDetailID, operator);
		}
	}
	
	/**
	 * 修改编单活动
	 * @param currentAction 当前页面活动编号
	 * @param dateStr 编单日期, 格式: 2010-09-09
	 * @param columnID 栏目ID
	 * @param actionOrder 活动顺序[R:回退; P:发送; C:完成; N:新建;]
	 * @param operator 操作人员ID
	 * @throws Exception
	 */
	public String updateProgAct(String currentAction, String dateStr, String columnID, 
			String actionOrder, String operator) throws Exception {
		String date = dateStr.replaceAll("-", "") + "000000";
		cmsLog.debug(" 当前" + ("P".equals(actionOrder) ? "发送" : "回退") + "栏目[ "+ columnID +" ] ");
		
//		if (this.PRICINGACTION.equals(currentAction) && "P".equals(actionOrder)
//				|| !"P".equals(actionOrder)) {
//			if (!this.isFinishPricing(date, columnID)) {
//				return "当前栏目存在未订价的节目包! 流程发送失败!";
//			}
//			try {
//				this.progListMangManager.updateProgAct(
//						currentAction, date, columnID, actionOrder, operator);
//			} catch (Exception e) {
//				cmsLog.debug(e);
//			}
//			return null;
//		} else {
		List<String> scheduleDateStr = new ArrayList<String>(1);
		scheduleDateStr.add(date);
		List<ProgListMangDetail> progListMangDetails = 
				this.progListMangDetailManager.queryDetailsByScheduleDate(
						scheduleDateStr);
		if (this.PRICINGACTION.equals(currentAction)
				&& "P".equals(actionOrder)) {
			for (ProgListMangDetail progListMangDetail : progListMangDetails) {
				if (!this.isFinishPricing(date, progListMangDetail.getColumnclassid())) {
					PortalColumn portalColumn = (PortalColumn) this.portalColumnManager.getById(
							progListMangDetail.getColumnclassid());
					return "栏目[ " + portalColumn.getColumnname() + " ]存在未订价或与订价不符的节目包, 请重新订价或应用订价, 流程发送失败!";
				}
			}
		}
		
		
		for (ProgListMangDetail progListMangDetail : progListMangDetails) {
			try {
				this.progListMangManager.updateProgAct(
						currentAction, date, progListMangDetail.getColumnclassid(), actionOrder, operator);
			} catch (Exception e) {
				String errMessage = " 栏目[ " + progListMangDetail.getColumnclassid() + 
				" : " + progListMangDetail.getAssessorid() + " ]发送失败! ";
				cmsLog.debug(errMessage);
				cmsLog.debug(e);
				return errMessage;
			}
		}
		return null;
//		}
	}
	
	/**
	 * 判断某天编单, 某个栏目下的编单明细是否全部应用上了产品订价
	 * @param scheduleDate 编单日期ID, 格式: 20100909000000
	 * @param columnId 栏目ID
	 * @return 如果全部应用上了产品订价则返回[true], 否则返回[false]
	 */
	private boolean isFinishPricing(String scheduleDate, String columnId) {
		List<ProgListDetail> progListDetails = this.progListDetailManager
				.queryDetailsByScheduleAndColumnId(scheduleDate, columnId);
		if (0 < progListDetails.size()) {
			for (ProgListDetail progListDetail : progListDetails) {
				if (null == progListDetail.getScheduledate() 
						|| null == progListDetail.getProductInfoID()
						|| 12 != progListDetail.getProductInfoID().trim().length()) {
					return false;
				} else {
					TProductInfo productInfo = this.productInfoManager.queryProductInfoByProgPackageId(
							progListDetail.getProductid());
					if (null == productInfo.getKeyId()) {
						return false;
					} else if (!progListDetail.getProductInfoID().equals(productInfo.getId())) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 修改明细明细是否生成发布文件状态
	 * @param progListDetailID 编单明细ID
	 * @param scheduleTag 是否生成发布文件[Y:生成, N:不生成]
	 */
	public void updateScheduleTag(String progListDetailID, 
			String scheduleTag) {
		this.progListMangManager.updateProgListDetailScheduleTag(
				progListDetailID, scheduleTag);
	}
	
	/**
	 * 按编单日期发送流程活动
	 * @param dateStr 编单日期, 格式: 2010-09-09
	 * @param currentAction 流程活动ID
	 * @param inputManId 操作人员ID
	 * @return 如果没有异常返回Null, 如果有错误则返回错误信息!
	 */
	public String sendProcessAction(String dateStr, String currentAction, String inputManId) {
		String scheduleDate = dateStr.replaceAll("-", "") + "000000";
		List<String> scheduleDates = new ArrayList<String>();
		scheduleDates.add(scheduleDate);
		
		List<ProgListMangDetail> progListMangDetails = this.progListMangDetailManager
				.queryDetailsByScheduleDate(scheduleDates);
		//boolean checkActionOk = true;
		for (ProgListMangDetail progListMangDetail : progListMangDetails) {
			if (!currentAction.equals(progListMangDetail.getIdAct())) {
				//checkActionOk = false;
				return progListMangDetail.getAssessorid() + " 流程活动不是[ " 
						+ progListMangDetail.getIdProcess() + " ], 请检查流程活动正确后再发送!";
			}
		}
		
		if (this.MIGRATIONACTION.equals(currentAction)) {
			CmsResultDto cmsResultDto = this.migrationModuleManager.checkMigrationCanSend(dateStr);
			if (1 == cmsResultDto.getResultCode()) {
				return cmsResultDto.getErrorMessage();
			}
		} else if (this.ENCRYPTPRODUCTACTION.equals(currentAction)) {
			if (this.productInfoManager.existNoEncryptProductInfo(scheduleDate)) {
				return "存在未进行产品加密的节目!";
			}
		} else if (this.FINISHGENERATEJS.equals(currentAction)) {
			if (!this.progPackageManager.isCanBroadcast(scheduleDate)) {
				return " 节目包未完全导入到播发库! ";
			}
			
			if (this.progListDetailManager.existsNullJsFileId(scheduleDate)) {
				return " 节目包JS未完全生成! ";
			}
		}
		

		String nextAction = this.flowActivityOrderManager.getNextAction(currentAction, "P");
		if (null != nextAction) {
			try {
				this.progListMangDetailManager.updateAction(scheduleDate, nextAction);
				return null;
			} catch (Exception e) {
				cmsLog.debug(e);
			}
		}
		return "流程活动发送失败!";
	}
	
	/**
	 * 按编单日期回退流程活动
	 * @param dateStr 编单日期, 格式: 2010-09-09
	 * @param currentAction 流程活动ID
	 * @param inputManId 操作人员ID
	 * @return 如果没有异常返回Null, 如果有错误则返回错误信息!
	 * @throws ParseException 
	 */
	public String backProcessAction(String dateStr, String currentAction, 
			String inputManId) throws ParseException {
		String scheduleDate = dateStr.replaceAll("-", "") + "000000";
		List<String> scheduleDates = new ArrayList<String>();
		scheduleDates.add(scheduleDate);
		
		List<ProgListMangDetail> progListMangDetails = this.progListMangDetailManager
				.queryDetailsByScheduleDate(scheduleDates);
		//boolean checkActionOk = true;
		for (ProgListMangDetail progListMangDetail : progListMangDetails) {
			if (!currentAction.equals(progListMangDetail.getIdAct())) {
				//checkActionOk = false;
				return progListMangDetail.getAssessorid() + " 流程活动是[ " 
						+ progListMangDetail.getIdProcess() + " ], 请检查流程活动正确后再回退!";
			}
		}
		
		if (this.GENERATEJS.equals(currentAction)
				|| this.FINISHGENERATEJS.equals(currentAction)) {
			List<String> beforeGenerateJsActs = this.flowActionManager.getGreaterOrLessAction(
					this.GENERATEJS, true);
			int days = Integer.valueOf(new CmsConfig().getPropertyByName("ScheduleDays"));
			List<String> beforeScheduleDates = new ArrayList<String>();
			List<String> tempBeforeScheduleDates = DateUtil.getBeforeDaysStrList(
					scheduleDate, days - 1);
			
			long standard = Long.valueOf(DateUtil.getCurrentDateStr("yyyyMMddHHmmss"));
			
			for (String string : tempBeforeScheduleDates) {
				if (standard <= Long.valueOf(string)) {
					beforeScheduleDates.add(string);
				}
			}
			
			if (0 < beforeScheduleDates.size()
					&& !beforeGenerateJsActs.containsAll(
					this.progListMangManager.queryActionsByScheduleDates(
							beforeScheduleDates))) {
				return "前 " + (days - 1) + " 天的编单必须存在于[生成JS完成]之前!";
			}
		}
		
		String nextAction = this.flowActivityOrderManager.getNextAction(currentAction, "R");
		if (null != nextAction) {
			try {
				this.progListMangDetailManager.updateAction(scheduleDate, nextAction);
			} catch (Exception e) {
				cmsLog.debug(e);
				return "流程活动回退失败!";
			}
		}
		
		if (this.FINISHBROADCAST.equals(currentAction)) {
			return "[ " + dateStr + " ]播发单回退成功!";
		}
		
		return null;
	}

	/**
	 * 根据当前活动编号, 日期区间查询编单总表记录
	 * @param currentAction 当前活动编号
	 * @param timeStart 日期区间起始, 格式: 2010-09-09
	 * @param timeEnd 日期区间结束, 格式: 2010-10-10
	 * @return
	 */
	public List<ProgListMang> queryProgListMangs(String currentAction, 
			String timeStart, String timeEnd) {
		List<String> currentActions = new ArrayList<String>();
		currentActions.add(currentAction);
		if (this.GENERATEJS.equals(currentAction)) {
			currentActions.add(this.FINISHGENERATEJS);
		}
		String scheduleDateStart = timeStart.replaceAll("-", "") + "000000";
		String scheduleDateEnd = timeEnd.replaceAll("-", "") + "000000";
		return this.progListMangManager.queryProgListMangs(currentActions, 
				scheduleDateStart, scheduleDateEnd);
	}
	
	/**
	 * 根据编单日期, 查询该编单日期下的栏目信息和编单明细信息
	 * @param dateStr 编单日期, 格式: 2010-09-09
	 * @return Object[]
	 * objects[0]: List<PortalColumn>
	 * objects[1]: List<ProgListDetail>
	 */
	public Object[] queryProgListDetails(String dateStr) {
		String scheduleDate = dateStr.replaceAll("-", "") + "000000";
		List<PortalColumn> portalColumns = this.portalColumnManager
				.queryPortalColumnsByScheduleDate(scheduleDate);
		List<Object[]> list1 = new ArrayList<Object[]>();
		for (PortalColumn portalColumn : portalColumns) {
			FlowAction currentFlowAction = 
					this.flowActionManager.getByScheduleDateAndColumnID(
								scheduleDate, portalColumn.getColumnclassid());
			Object[] objects = new Object[] {portalColumn, currentFlowAction};
			list1.add(objects);
		}
		
		List<?> list2 = this.progListDetailManager.queryByScheduleDate(scheduleDate);
		return new Object[] {list1, list2};
	}
	
	/**
	 * 检测某天编单播发量大小, 统计
	 * @param dateStr 编单日期,格式: yyyy-MM-dd
	 * @return 返回播发量大小
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws Exception 
	 */
	public String checkProgSize(String dateStr) throws Exception {
		if (cmsLog.isDebugEnabled()) {
			cmsLog.debug("checkProgSize.param.dateStr: " + dateStr);
		}
		List<Object> list = this.progListDetailManager.checkProgSize(dateStr.replaceAll("-", "") + "000000");
		if (cmsLog.isDebugEnabled()) {
			cmsLog.debug("ProgSize.list.size(): " + list.size());
		}
		if (0 < list.size()) {
//			List<String> strings = new ArrayList<String>();
//			for (Object[] objects : list) {
//				if (cmsLog.isDebugEnabled()) {
//					cmsLog.debug("节目大小: " + ArrayUtils.toString(objects));
//				}
//				strings.add(String.valueOf(objects[0]) + ": " 
//						+ SmbFileUtils.byteCountToDisplaySize(
//								Long.valueOf(String.valueOf(objects[1]))));
//			}
//			return ListUtil.toString(strings);
			return "当前编单节目包大小总和: " + SmbFileUtils.byteCountToDisplaySize(
					Long.valueOf(String.valueOf(list.get(0))));
		} else {
			return "您 " + dateStr + "编单未编入任何节目包";
		}
	}
	
	public void test(String dateStr, String currAct) throws Exception {
//		this.sendProgListMang2("2010-06-12", "OP00000034", "试试批量发送行不行!");
//		CmsSite cmsSite = (CmsSite) this.cmsSiteManager.getById("CS00000001");
//		System.out.println(this.generateNoticeXml("2010-12-08", cmsSite, 7));
//		this.generateProgPackageXml("2010-12-08", "");
//		this.checkByTerminal("2010-12-08", 7, "");
//		this.isGeneratedNoticeOrPackageJs("2010-09-12");
//		System.out.println(this.sendProcessAction(dateStr, currAct, ""));
	}
}
