package com.soaplat.cmsmgr.service;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageClass;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.cmsmgr.bean.PackageFiles;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.manageriface.IBpmcManager;
import com.soaplat.cmsmgr.manageriface.ICmsTransactionManager;
import com.soaplat.cmsmgr.manageriface.IFlowActivityOrderManager;
import com.soaplat.cmsmgr.manageriface.IMigrationModuleManager;
import com.soaplat.cmsmgr.manageriface.IPPColumnRelManager;
import com.soaplat.cmsmgr.manageriface.IPackStyleManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IPortalModManager;
import com.soaplat.cmsmgr.manageriface.IPortalRoleOperRelManager;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListFileManager;
import com.soaplat.cmsmgr.manageriface.IProgListManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.soaplat.cmsmgr.manageriface.ISrvColumnManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.manageriface.IRoleManager;

public class MigrationServiceImpl implements MigrationServiceIface {

	private static FileOperationImpl fileoper = null;
	public static final Logger cmsLog = Logger.getLogger("Cms");

	private IProgListDetailManager progListDetailManager = null;
	private IProgPackageManager progPackageManager = null;
	private IProgListMangDetailManager progListMangDetailManager = null;
	private IProgListMangManager progListMangManager = null;
	private IProgListFileManager progListFileManager = null;
	private IPackageFilesManager packageFilesManager = null;
	private IAmsStorageManager amsstorageManager = null;
	private IAmsStorageDirManager amsstoragedirManager = null;
	private IAmsStoragePrgRelManager amsstorageprgrelManager = null;
	private IProgramFilesManager programFilesManager = null;
	private IProgramInfoManager programinfoManager = null;
	private ICmsTransactionManager cmsTransactionManager = null;
	private IPortalColumnManager portalColumnManager;
	private IMigrationModuleManager migrationModuleManager;
	private IProductInfoManager productInfoManager;
	
	public MigrationServiceImpl() {
		fileoper = new FileOperationImpl();
		progListDetailManager = (IProgListDetailManager)ApplicationContextHolder.webApplicationContext.getBean("progListDetailManager");
		progPackageManager = (IProgPackageManager)ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
		progListMangDetailManager = (IProgListMangDetailManager)ApplicationContextHolder.webApplicationContext.getBean("progListMangDetailManager");
		progListMangManager = (IProgListMangManager)ApplicationContextHolder.webApplicationContext.getBean("progListMangManager");
		progListFileManager = (IProgListFileManager)ApplicationContextHolder.webApplicationContext.getBean("progListFileManager");
		packageFilesManager = (IPackageFilesManager)ApplicationContextHolder.webApplicationContext.getBean("packageFilesManager");
		amsstorageManager = (IAmsStorageManager)ApplicationContextHolder.webApplicationContext.getBean("amsstorageManager");
		amsstoragedirManager = (IAmsStorageDirManager)ApplicationContextHolder.webApplicationContext.getBean("amsstoragedirManager");
		amsstorageprgrelManager = (IAmsStoragePrgRelManager)ApplicationContextHolder.webApplicationContext.getBean("amsstorageprgrelManager");
		programFilesManager = (IProgramFilesManager)ApplicationContextHolder.webApplicationContext.getBean("programFilesManager");
		programinfoManager = (IProgramInfoManager)ApplicationContextHolder.webApplicationContext.getBean("programinfoManager");
		cmsTransactionManager = (ICmsTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("cmsTransactionManager");
		this.portalColumnManager = (IPortalColumnManager) ApplicationContextHolder.webApplicationContext.getBean("portalColumnManager");
		this.migrationModuleManager = (IMigrationModuleManager) ApplicationContextHolder.webApplicationContext.getBean("migrationModuleManager");
		this.productInfoManager = (IProductInfoManager) ApplicationContextHolder.webApplicationContext.getBean("productinfoManager");
	}
	
	// 20100119 17:11
	// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
	private String convertDateToScheduleDate(String date) {
		String[] strl = date.split("-");
		String newDate = "";
		for (int i = 0; i < strl.length; i++) {
			if (strl[i] != null && strl[i] != "") {
				newDate += strl[i];
			}
		}
		return newDate + "000000";
	}
	
	
	// 20100208 16:22
	// 数据导出迁移反馈
	public CmsResultDto finishMigrationToBjOnline(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
	) {
		cmsLog.info("Cms -> MigrationServiceImpl -> finishMigrationToBjOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件，获取
		// 节目包状态，0-导入 1-缓存库 2-加扰库 3-播控库
		String stclasscodeNearOnline = "NearOnline";		// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline";			// 加扰库存储体等级code
		String stclasscodeOnline = "Online";				// 播控库存储体等级code
		String stclasscodeBjOnline = "BjOnline";			// 在上海的北京缓存库存储体等级code

		if (result.equalsIgnoreCase("Y")) {
			// 迁移成功
			// 新增记录，到文件存放位置表
			// 解析xml
			cmsLog.info("迁移成功...");
			List list = getAmsStoragePrgRelFromXml(transferEntity);
			if (list != null && list.size() >= 2) {
				AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) list
						.get(0);
				String proglistId = (String) list.get(1); // 栏目单日期

				// 新增文件位置表记录
				cmsResultDto = cmsTransactionManager
						.saveFinishMigrationToBjOnline(amsstorageprgrelManager,
								progListDetailManager, packageFilesManager,
								progPackageManager, stclasscodeNearOnline,
								stclasscodeCaOnline, stclasscodeOnline,
								stclasscodeBjOnline, amsStoragePrgRel);
			} else {
				String str = "解析返回xml失败。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		} else if (result.equalsIgnoreCase("N")) {
			// 迁移失败
			// 查询节目包的栏目单详细，修改处理状态为“失败”，处理状态(0未处理1处理8失败9成功)
			cmsLog.info("迁移失败...");
			List list = getAmsStoragePrgRelFromXml(transferEntity);
			if (list != null && list.size() >= 2) {
				AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) list
						.get(0);
				String proglistId = (String) list.get(1); // 栏目单日期

				ProgPackage pp = (ProgPackage) progPackageManager
						.getById(amsStoragePrgRel.getPrglobalid());
				if (pp != null) {
					pp.setDealstate((long) 8);
					progPackageManager.update(pp);
				}
			} else {
				String str = "解析返回xml失败。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		} else {
			String str = "未知输入结果。result：" + result;
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}
		cmsLog.info("Cms -> MigrationServiceImpl -> finishMigrationToBjOnline returns.");
		return cmsResultDto;
	}
	
	// 20100208 13:23
	// 生成数据导出对应的迁移单
	public CmsResultDto generateMigrationForExportData(
			String date,					// 编单日期，格式：yyyy-MM-dd
			String operatorId				// 操作人员ID
	) {
		// 上海数据导出流程：
		// 1 - 根据date查询栏目单详细记录，
		// 2 - 根据节目包ID查询节目包下所有文件
		// 3 - 根据progfileid、节目包ID和存储体等级BjOnline，查询文件的源路径，判断文件是否已经存在于北京的缓存库
		// 4 - 如果存在于北京的缓存库，
		// 4.1 - 判断文件存放位置表的filedate是否是最新的，
		// 4.2 - 如果不是最新的，需要迁移，如果是不需要迁移这个文件
		// 5 - 如果不存在，需要下迁移单，迁移文件到北京的缓存库

		// 0 - 数据导出@上海； 
		// 1 - 迁移至播发库@北京； 
		// 2 - 数据导入@北京
		// 3 - 节目录入@上海
		
		cmsLog.info("Cms -> MigrationServiceImpl -> generateMigrationForExportData...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件，获取
		String stclasscodeOnline = "Online";			// 一级库存储体等级code
		String stclasscodeNearOnline = "NearOnline";	// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline";		// 缓存库存储体等级code
		String stclasscodeBjOnline = "BjOnline";		// 上海的北京缓存库存储体等级code
		String stclasscodeMigration = "Migration";		// 迁移单的目标存储体等级code
		String filecodeMigration = "MIGXML";			// 迁移单xml的filecode
		String progPackagePath = "progpackage";
		String progListPath = "proglist";
		String importDataFilecode = "IMPORT"; 		// 数据导入源filecode
		
		String scheduledate = convertDateToScheduleDate(date);
		
		int filecount = 0;
		String strXml = "";								// 迁移单内容
		String destpathMigration = "";					// 迁移单的目标路径
		String xmlFilename = "";						// 迁移单的文件名字
		// xml内容字段
		String proglistId = scheduledate;				// 编单日期，总表主键
		String requestId = "";				// 当前时间，不重复
		String createDate = "";
		String priorityDate = "";
		// 0 - 数据导出@上海； 
		// 1 - 迁移至播发库@北京； 
		// 2 - 数据导入@北京
		// 3 - 节目录入@上海
		String type = "0";		
		
		List needUpdatePps = new ArrayList();		// 更新状态和处理状态的节目包列表

		try {
			// 赋值
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
			requestId = df.format(new Date());
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			createDate = df.format(new Date());
			priorityDate = date + " 00:00:00";
			xmlFilename = proglistId + requestId + ".xml";

			// 调用方法2，根据配置文件stclasscode、配置文件filecode，获得迁移单xml目标路径
			// 返回：List
			// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
			// 2 - List<Object[]>
			// (AmsStorage)Object[0]
			// (AmsStorageDir)Object[1]
			// (AmsStorageClass)Object[2]
			List xmldestpaths = packageFilesManager
					.getDestPathByFilecodeStclasscode(filecodeMigration, // 迁移单xml的filecode
							stclasscodeMigration // 迁移单的目标存储体等级code
					);
			if (xmldestpaths != null && xmldestpaths.size() >= 2) {
				destpathMigration = (String) xmldestpaths.get(0);
				if (destpathMigration == null
						|| destpathMigration.equalsIgnoreCase("")) {
					String str = "迁移单目标路径不存在。";
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.warn(str);
				} else {
					destpathMigration = fileoper.checkPathFormatRear(
							destpathMigration, '/');
					cmsLog.info("得到迁移单的目标路径。" + destpathMigration);
				}
			} else {
				String str = "迁移单目标路径不存在。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}

			strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
			strXml += "<Migration>\r\n";
			strXml += "<Distribution ProglistId=\"";
			strXml += proglistId;
			strXml += "\" RequestId=\"";
			strXml += requestId;
			strXml += "\" CreateDate=\"";
			strXml += createDate;
			strXml += "\" Type=\"";
			strXml += type;
			strXml += "\">\r\n";

			// 1 - 根据date查询栏目单详细记录，
			// 1 - 调用方法6根据日期和栏目单code序列，查询，得到栏目单详细和节目包
			// 返回：List<Object[]>
			// (ProgListDetail)Object[0]
			// (ProgPackage)Object[1]
			// (FunResource)Object[2]
			cmsLog.info("查询栏目单详细记录...");
			cmsLog.info("日期：" + date);
			List plds = progListDetailManager
					.getProgListDetailsProgPackagesByDateAndDefcatseq(date, "");
			cmsLog.info("共有" + plds.size() + "个栏目单详细记录。");
			for (int i = 0; i < plds.size(); i++) {
				Object[] rows = (Object[]) plds.get(i);
				ProgListDetail progListDetail = (ProgListDetail) rows[0];
				ProgPackage progPackage = (ProgPackage) rows[1];

				cmsLog.info("处理第" + (i + 1) + "个栏目单详细记录...");
				cmsLog.info("节目包ID：" + progPackage.getProductid());
				cmsLog.info("节目包名称：" + progPackage.getProductname());
				cmsLog.info("节目包状态：" + progPackage.getState());
				cmsLog.info("节目包处理状态：" + progPackage.getDealstate());

				try {
					// 节目包状态
					// 0 - 导入
					// 1 - 缓存库
					// 2 - 加扰库
					// 3 - 播控库
					// 9 - 北京缓存库
					// 判断栏目单详细，节目包的（迁移）处理状态，处理状态(0未处理1处理8失败9成功)
					cmsLog.info("判断节目包状态...");
					if (progPackage.getState() != (long) 1) {
						cmsLog.info("节目包状态不是“缓存库”，不加入迁移单，跳过...");
						continue;
					}
					cmsLog.info("判断节目包的处理状态...");
					Long dealState = progPackage.getDealstate();
					if (dealState == null || (dealState != 0 && dealState != 8)) {
						cmsLog.info("节目包的处理状态不是“未处理”或“失败”，不加入迁移单，跳过...");
						continue;
					}

					// // 2 - 判断节目包的ProgType是否="V"
					// if(progPackage.getProgtype().equalsIgnoreCase("V"))
					// {
					// 视频节目包
					// 查询节目包下所有文件
					String ppstr = "";
					cmsLog.info("查询节目包的所有文件...");
					List packfiles = packageFilesManager.findByProperty(
							"productid", progPackage.getProductid());
					cmsLog.info("节目包共有" + packfiles.size() + "个文件。");
					for (int j = 0; j < packfiles.size(); j++) {
						PackageFiles packfile = (PackageFiles) packfiles.get(j);
						ProgramFiles programFiles = (ProgramFiles) programFilesManager
								.getById(packfile.getProgfileid());

						cmsLog.info("处理第" + (j + 1) + "个文件记录...");
						cmsLog.info("文件ID：" + programFiles.getProgfileid());
						cmsLog.info("文件名：" + programFiles.getFilename());

						// try
						// {
						// 调用方法11，根据节目包ID、文件ID、缓存库classcode，获得每个文件的在缓存库的路径
						// 返回：List
						// 1 - String
						// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
						// 2 - List<Object[]>
						// (AmsStorage)Object[0]
						// (AmsStoragePrgRel)Object[1]
						// (AmsStorageDir)Object[2]
						// (AmsStorageClass)Object[3]
						String sourcepath = ""; // 节目包文件的源路径
						String destpath = "";
						String strfiledate = ""; // 源文件的filedate
						Date dfiledate = null; // 源文件的filedate
						boolean needmigration = false;
						AmsStorage sourceamsst = null;
						AmsStoragePrgRel sourceamsstpr = null;
						AmsStorageDir sourceamsstd = null;
						AmsStorageClass sourceamsstc = null;
						AmsStorage destamsst = null;
						AmsStorageDir destamsstd = null;
						AmsStorageClass destamsstc = null;
						String sourcePort = "0";
						String destPort = "0";

						cmsLog.info("查询文件的源路径...");
						List sourcepaths = packageFilesManager
								.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
										packfile.getProgfileid(),
										stclasscodeNearOnline, // 缓存库存储体等级code，从配置文件获取
										"" // progPackage.getProductid()
								);
						if (sourcepaths != null && sourcepaths.size() >= 2) {
							sourcepath = (String) sourcepaths.get(0);
							List list = (List) sourcepaths.get(1);
							Object[] sourcerows = (Object[]) list.get(0);
							sourceamsst = (AmsStorage) sourcerows[0];
							sourceamsstpr = (AmsStoragePrgRel) sourcerows[1];
							sourceamsstd = (AmsStorageDir) sourcerows[2];
							sourceamsstc = (AmsStorageClass) sourcerows[3];

							dfiledate = sourceamsstpr.getFiledate();
							strfiledate = fileoper.convertDateToString(
									dfiledate, "yyyy-MM-dd HH:mm:ss");

							if (sourceamsst.getPort() != null) {
								sourcePort = String.valueOf(sourceamsst
										.getPort());
							}
						} else {
							ppstr = "";
							cmsLog.warn("查询节目包文件源路径为空，不生成该节目包的迁移任务，不继续处理该节目包文件。");
							break;
						}
						// 3 -
						// 根据progfileid、节目包ID和存储体等级BjOnline，查询文件的源路径，判断文件是否已经存在于北京的缓存库
						// 2.2.2 -
						// 调用方法11，根据节目包ID、文件ID、上海的北京缓存库classcode，获得每个文件的在上海的北京缓存库的路径
						// 返回：List
						// 1 - String
						// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
						// 2 - List<Object[]>
						// (AmsStorage)Object[0]
						// (AmsStoragePrgRel)Object[1]
						// (AmsStorageDir)Object[2]
						// (AmsStorageClass)Object[3]
						cmsLog.info("判断文件是否已经存在于在上海的北京缓存库了...");
						// 节目包状态
						// 0 - 导入
						// 1 - 缓存库
						// 2 - 加扰库
						// 3 - 播控库
						// 9 - 北京缓存库
						if (progPackage.getState() == (long) 9) {
							List bjonlinepaths = packageFilesManager
									.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
											packfile.getProgfileid(),
											stclasscodeBjOnline, // 在上海的北京缓存库存储体等级code，从配置文件获取
											progPackage.getProductid());
							// 2.3 - 判断文件是否已经存在于在上海的北京缓存库了，
							if (bjonlinepaths != null
									&& bjonlinepaths.size() >= 2) {
								String bjonlinepath = (String) bjonlinepaths
										.get(0);
								List list = (List) bjonlinepaths.get(1);
								Object[] rows1 = (Object[]) list.get(0);
								AmsStoragePrgRel aspr = (AmsStoragePrgRel) rows1[1];

								if (bjonlinepath != null
										&& !bjonlinepath.equalsIgnoreCase("")) {
									// 4 - 如果存在于北京的缓存库，
									// 4.1 - 判断文件存放位置表的filedate是否是最新的，
									if (dfiledate.compareTo(aspr.getFiledate()) != 0) {
										// 文件有更新，需要下迁移单
										// 4.2 - 如果不是最新的，需要迁移，如果是不需要迁移这个文件
										cmsLog.info("节目包状态为“北京的缓存库”，文件有更新，需要迁移...");
										needmigration = true;
									} else {
										// 文件没有更新
										cmsLog.info("节目包状态为“北京的缓存库”，文件没有更新，不需要迁移...");
										needmigration = false;
									}
								} else {
									// 5 - 如果不存在，需要下迁移单，迁移文件到北京的缓存库
									cmsLog.info("节目包状态为“北京的缓存库”，但是文件位置表查询到的记录异常，需要迁移...");
									needmigration = true;
								}
							} else {
								// 5 - 如果不存在，需要下迁移单，迁移文件到北京的缓存库
								cmsLog.info("节目包状态为“北京的缓存库”，但是文件位置表未查询到记录，需要迁移...");
								needmigration = true;
							}
						} else if (progPackage.getState() == (long) 1) {
							cmsLog.info("节目包状态为“缓存库”，需要迁移...");
							needmigration = true;
						}
						if (needmigration == true) {
							cmsLog.info("文件不存在于在上海的北京缓存库了，需要迁移...");
							cmsLog.info("查询文件的目标路径...");
							// 调用方法2，根据stdir.filecode、配置文件在上海的北京缓存库stclasscode，获取节目包主文件迁移的目标路径
							// 返回：List
							// 1 - String 目标路径()
							// 格式："smb://hc:hc@172.23.19.66/公用/"
							// 2 - List<Object[]>
							// (AmsStorage)Object[0]
							// (AmsStorageDir)Object[1]
							// (AmsStorageClass)Object[2]
							List destpaths = packageFilesManager
									.getDestPathByFilecodeStclasscode(
											importDataFilecode, // sourceamsstd.getFilecode(),
																// //
																// 节目包主文件的filecode
											stclasscodeBjOnline // 在上海的北京缓存库存储体等级code，从配置文件获取
									);
							cmsLog.info("判断查询返回值的合法性...");
							if (destpaths != null && destpaths.size() >= 2) {
								destpath = (String) destpaths.get(0);
								List list1 = (List) destpaths.get(1);

								cmsLog.info("destpath - " + destpath);
								if (destpath == null) {
									cmsLog.warn("destpath == null。");
								}

								if (list1 != null && list1.size() > 0) {
									Object[] destrows = (Object[]) list1.get(0);
									if (destrows == null) {
										cmsLog.warn("destrows == null。");
									} else {
										destamsst = (AmsStorage) destrows[0];
										destamsstd = (AmsStorageDir) destrows[1];
										destamsstc = (AmsStorageClass) destrows[2];

										if (destamsst.getPort() != null) {
											destPort = String.valueOf(destamsst
													.getPort());
										}
									}
									if (destamsst == null) {
										cmsLog.warn("destamsst == null。");
									} else {
										cmsLog.info("destamsst:"
												+ destamsst.getStglobalid());
									}
									if (destamsstd == null) {
										cmsLog.warn("destamsstd == null。");
									} else {
										cmsLog.info("destamsstd:"
												+ destamsstd.getStdirglobalid());
									}
									if (destamsstc == null) {
										cmsLog.warn("destamsstc == null。");
									} else {
										cmsLog.info("destamsstc:"
												+ destamsstc
														.getStclassglobalid());
									}

									if (destpath == null
											|| destpath.equalsIgnoreCase("")) {
										cmsLog.warn("节目包下的主文件迁移到在上海的北京缓存库的目标路径不存在。文件ID："
												+ programFiles.getProgfileid());
										// continue;
									} else {
										cmsLog.info("获取文件的路径...");
										// 处理destpath，加上filepath和文件名
										destpath = destpath.replace('\\', '/');
										destpath = fileoper
												.checkPathFormatRear(destpath,
														'/');
										destpath += scheduledate;
										destpath += "/";
										destpath += progPackagePath;
										destpath += "/";
										destpath += progPackage.getProductid();
										destpath += "/";

										destpath += sourceamsstpr.getFilename();

										cmsLog.info("文件的源路径 - " + sourcepath);
										cmsLog.info("文件的目标路径 - " + destpath);

										// 处理路径格式，去掉头
										// smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
										// /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
										sourcepath = sourcepath.substring(
												sourcepath.indexOf("/", 6),
												sourcepath.length());
										destpath = destpath.substring(
												destpath.indexOf("/", 6),
												destpath.length());

										cmsLog.info("加入迁移任务的文件的源路径 - "
												+ sourcepath);
										cmsLog.info("加入迁移任务的文件的目标路径 - "
												+ destpath);

									}
								} else {
									cmsLog.warn("查询节目文件的目标路径为空。");
									if (list1 == null) {
										cmsLog.warn("list1 == null。");
									}
								}
							}
							if (sourcepath == null || destpath == null
									|| sourcepath.equalsIgnoreCase("")
									|| destpath.equalsIgnoreCase("")
									|| sourceamsst == null
									|| sourceamsstpr == null
									|| sourceamsstd == null
									|| sourceamsstc == null
									|| destamsst == null || destamsstd == null
									|| destamsstc == null) {
								String str = "符合迁移条件，但是输入参数为空，不生成该文件的迁移任务，继续...文件ID："
										+ programFiles.getProgfileid();
								cmsLog.warn(str);
							} else {
								// 生成迁移单
								cmsLog.info("加入迁移任务单字符串...");

								ppstr += "<Transfer-Entity SourceStorageClass=\"";
								ppstr += sourceamsstc.getStclasscode();
								ppstr += "\" SourceFileType=\"";
								ppstr += programFiles.getFiletypeid();
								ppstr += "\" DesStorageClass=\"";
								ppstr += destamsstc.getStclasscode();
								ppstr += "\" PriorityDate=\"";
								ppstr += priorityDate;
								ppstr += "\" SourceProgTitle=\"";
								ppstr += progPackage.getProductname();
								ppstr += "\" SourceFileName=\"";
								ppstr += sourceamsstpr.getFilename();
								ppstr += "\" SourceFileId=\"";
								ppstr += sourceamsstpr.getProgfileid();
								ppstr += "\" SourceProgid=\"";
								ppstr += progPackage.getProductid();
								ppstr += "\" IsCover=\"";
								ppstr += "N";
								ppstr += "\">\r\n";

								// 源文件
								ppstr += "<Source Type=\"";
								ppstr += sourceamsst.getStorageaccstype();
								ppstr += "\" SourceDirId=\"";
								ppstr += sourceamsstd.getStdirglobalid();
								ppstr += "\" Hostname=\"";
								ppstr += sourceamsst.getStorageip();
								ppstr += "\" Port=\"";
								ppstr += sourcePort;
								ppstr += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									ppstr += sourceamsst.getLoginname();
								ppstr += "\" SourcestorageId=\"";
								ppstr += sourceamsst.getStglobalid();
								ppstr += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									ppstr += sourceamsst.getLoginpwd();
								ppstr += "\" FileDate=\"";
								ppstr += strfiledate;
								ppstr += "\" VariableFilePath=\"";
								ppstr += sourceamsstpr.getFilepath();
								ppstr += "\">\r\n";
								ppstr += "<File>";
								ppstr += sourcepath;
								ppstr += "</File>\r\n";
								ppstr += "</Source>\r\n";

								// 目标文件
								ppstr += "<Destination Type=\"";
								ppstr += destamsst.getStorageaccstype();
								ppstr += "\" DesStorageDirId=\"";
								ppstr += destamsstd.getStdirglobalid();
								ppstr += "\" Hostname=\"";
								ppstr += destamsst.getStorageip();
								ppstr += "\" Port=\"";
								ppstr += destPort;
								ppstr += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									ppstr += destamsst.getLoginname();
								ppstr += "\" DesStorageId=\"";
								ppstr += destamsst.getStglobalid();
								ppstr += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									ppstr += destamsst.getLoginpwd();
								ppstr += "\" FileDate=\"";
								ppstr += strfiledate;
								ppstr += "\" VariableFilePath=\"";
								ppstr += sourceamsstpr.getFilepath();
								ppstr += "\">\r\n";
								ppstr += "<File>";
								ppstr += destpath; // 没有文件名，需要处理
								ppstr += "</File>\r\n";
								ppstr += "</Destination>\r\n";

								ppstr += "</Transfer-Entity>\r\n";

								filecount++;
								cmsLog.info("迁移任务单字符串已经生成。");

								boolean exist = false;
								for (int k = 0; k < needUpdatePps.size(); k++) {
									ProgPackage pp = (ProgPackage) needUpdatePps
											.get(k);
									if (pp.getProductid().equalsIgnoreCase(
											progPackage.getProductid())) {
										exist = true;
									}
								}
								if (exist == false) {
									needUpdatePps.add(progPackage);
								}
							}
						} else {
							cmsLog.info("文件已经存在于在上海的北京缓存库了，不需要迁移...");
						}
					}
					cmsLog.info("将节目包的迁移任务字符串加入迁移任务字符串中...");
					strXml += ppstr;
					// }
					// else
					// {
					// // 富媒体节目包
					// // 需要迁移zip文件
					// // 查询节目包下所有文件
					// // 3 -
					// 根据progfileid、节目包ID和存储体等级BjOnline，查询文件的源路径，判断文件是否已经存在于北京的缓存库
					// // 4 - 如果存在于北京的缓存库，
					// // 4.1 - 判断文件存放位置表的filedate是否是最新的，
					// // 4.2 - 如果不是最新的，需要迁移，如果是不需要迁移这个文件
					// // 5 - 如果不存在，需要下迁移单，迁移文件到北京的缓存库
					// }
				} catch (Exception e) {
					cmsLog.error("处理节目包文件发生异常，" + e.getMessage());
				}
			}
			strXml += "</Distribution>\r\n";
			strXml += "</Migration>\r\n";

			if (filecount > 0) {
				cmsLog.info("有" + filecount + "个文件需要迁移，准备生成迁移任务...");

				// 生成迁移单文件
				if (fileoper.createSmbFile(destpathMigration + xmlFilename,
						strXml) == 0) {
					// 生成迁移单文件成功
					cmsLog.info("生成迁移单文件成功。" + destpathMigration);

					cmsLog.info("准备修改节目包状态和处理状态...");
					cmsLog.warn("这里没有使用事务处理，有待优化。");
					cmsLog.info("共有" + needUpdatePps.size() + "个节目包需要修改。");
					for (int i = 0; i < needUpdatePps.size(); i++) {
						ProgPackage progPackage = (ProgPackage) needUpdatePps
								.get(i);
						// 如果节目包状态为缓存库，处理状态为未处理/失败，修改
						Long state = progPackage.getState();
						Long dealState = progPackage.getDealstate();
						if (state == 1) {
							if (dealState == 0 || dealState == 8) {
								progPackage.setDealstate((long) 1); // 处理状态(0未处理1处理8失败9成功)
							}
						}

						progPackageManager.update(progPackage);
						cmsLog.info("节目包ID：" + progPackage.getProductid());
						cmsLog.info("节目包名称：" + progPackage.getProductname());
						cmsLog.info("节目包的处理状态修改为：" + progPackage.getDealstate());
					}
				} else {
					// 生成迁移单文件失败
					String str = "生成迁移单文件失败。" + destpathMigration;
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.error(str);
				}
			} else {
				String str = "需要迁移的文件数为0，不生成迁移单，修改节目包的状态和处理状态。";
				cmsResultDto.setResultCode((long) 0);
				cmsResultDto.setErrorMessage(str);
				cmsLog.info(str);

				cmsLog.info("准备修改节目包状态和处理状态...");
				cmsLog.warn("这里没有使用事务处理，有待优化。");
				cmsLog.info("共有" + needUpdatePps.size() + "个节目包需要修改。");
				for (int i = 0; i < needUpdatePps.size(); i++) {
					ProgPackage progPackage = (ProgPackage) needUpdatePps
							.get(i);
					// 如果节目包状态为缓存库，处理状态为未处理/失败，修改
					Long state = progPackage.getState();
					Long dealState = progPackage.getDealstate();
					if (state == 1) {
						if (dealState == 0 || dealState == 8) {
							progPackage.setDealstate((long) 1); // 处理状态(0未处理1处理8失败9成功)
						}
					}

					progPackageManager.update(progPackage);
					cmsLog.info("节目包ID：" + progPackage.getProductid());
					cmsLog.info("节目包名称：" + progPackage.getProductname());
					cmsLog.info("节目包的处理状态修改为：" + progPackage.getDealstate());
				}
			}
		} catch (Exception e) {
			String str = "生成迁移单发生异常。" + e.getMessage();
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.error(str);
		}

		cmsLog.info("Cms -> MigrationServiceImpl -> generateMigrationForExportData returns.");
		return cmsResultDto;
	}
	
	// 20100123 12:23
	// 生成迁移单
	@SuppressWarnings("unchecked")
	private int generateMigration(
			String date,					// 编单日期，格式：yyyy-MM-dd
			String operatorId				// 操作人员ID
	) {
		// -----------------------------------------------------------------------------------------
		// 流程：
		// 1 - 调用方法6根据日期和栏目单code序列，查询，得到栏目单详细和节目包
		// 2 - 判断节目包的ProgType是否="V"
		// 2.1 - 如果不="V"，不处理
		// 2.2 - 如果="V"，继续
		// 2.2.1 - 调用方法5，根据节目包id 和 主文件表示(progrank)，查询packagefiles、programfiles表，得到节目包的文件（主文件）
		// 2.2.2 - 调用方法11，根据节目包ID、文件ID、一级库classcode，获得每个文件的源路径
		// 2.3 - 判断文件是否已经存在于一级库了，
		// 2.3.1 - 如果存在，不处理
		// 2.3.2 - 如果不存在，继续
		// 2.4 - 调用方法2，根据stdir.filecode、配置文件一级库stclasscode，获取节目包主文件迁移的目标路径
		// 2.5 - 把节目文件加入迁移单中
		// 3 - 调用方法2，根据配置文件stclasscode、配置文件filecode，获得迁移单xml目标路径
		// 4 - 调用方法9，生成xml到指定位置
		// 5 - 调用方法8，根据编单日期、文件类型8，生成xml对应的发布文件表记录
		// -----------------------------------------------------------------------------------------
		
		// 0 - 数据导出@上海； 
		// 1 - 迁移至播发库@北京； 
		// 2 - 数据导入@北京
		// 3 - 节目录入@上海
		
		cmsLog.info("Cms -> MigrationServiceImpl -> generateMigration...");
		String scheduledate = convertDateToScheduleDate(date);
		
		// 配置文件，获取
		String stclasscodeOnline = "Online";			// 一级库存储体等级code
		String stclasscodeNearOnline = "NearOnline";	// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline";		// 缓存库存储体等级code
		String stclasscodeMigration = "Migration";		// 迁移单的目标存储体等级code
		String filecodeMigration = "MIGXML";			// 迁移单xml的filecode
		
		String strXml = "";								// 迁移单内容
		String destpathMigration = "";					// 迁移单的目标路径
		String xmlFilename = "";						// 迁移单的文件名字
		
		// xml内容字段
		String proglistId = scheduledate;				// 编单日期，总表主键
		String requestId = "";				// 当前时间，不重复
		String createDate = "";
		String priorityDate = "";
		// 0 - 数据导出@上海； 
		// 1 - 迁移至播发库@北京； 
		// 2 - 数据导入@北京
		// 3 - 节目录入@上海
		String type = "1";					
		
		// 赋值
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		requestId = df.format(new Date());
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		createDate = df.format(new Date());
		priorityDate = date + " 00:00:00";
		xmlFilename = proglistId + requestId + ".xml";
		
		// 3 - 调用方法2，根据配置文件stclasscode、配置文件filecode，获得迁移单xml目标路径
		// 返回：List
		// 1 - String 目标路径()  格式："smb://hc:hc@172.23.19.66/公用/"
		// 2 - List<Object[]>
		//			(AmsStorage)Object[0]
		//			(AmsStorageDir)Object[1]
		//			(AmsStorageClass)Object[2]
		List xmldestpaths = packageFilesManager.getDestPathByFilecodeStclasscode(
				filecodeMigration, 					// 迁移单xml的filecode
				stclasscodeMigration				// 迁移单的目标存储体等级code
				);
		// FileOperationImpl fileoper = new FileOperationImpl();
		if (xmldestpaths != null && xmldestpaths.size() >= 2) {
			destpathMigration = (String) xmldestpaths.get(0);
			if (destpathMigration == null
					|| destpathMigration.equalsIgnoreCase("")) {
				cmsLog.info("迁移单目标路径不存在。");
				return 1;
			} else {
				destpathMigration = fileoper.checkPathFormatRear(
						destpathMigration, '/');
				cmsLog.info("得到迁移单的目标路径。" + destpathMigration);
			}
		} else {
			cmsLog.info("迁移单目标路径不存在。");
			return 1;
		}

		strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		strXml += "<Migration>";
		strXml += "<Distribution ProglistId=\"";
		strXml += proglistId;
		strXml += "\" RequestId=\"";
		strXml += requestId;
		strXml += "\" CreateDate=\"";
		strXml += createDate;
		strXml += "\" Type=\"";
		strXml += type;
		strXml += "\">";

		// 1 - 调用方法6根据日期和栏目单code序列，查询，得到栏目单详细和节目包
		// 返回：List<Object[]>
		// (ProgListDetail)Object[0]
		// (ProgPackage)Object[1]
		// (FunResource)Object[2]
		List plds = progListDetailManager
				.getProgListDetailsProgPackagesByDateAndDefcatseq(date, "");
		for (int i = 0; i < plds.size(); i++) {
			Object[] rows = (Object[]) plds.get(i);
			ProgListDetail progListDetail = (ProgListDetail) rows[0];
			ProgPackage progPackage = (ProgPackage) rows[1];

			// 判断栏目单详细，节目包的（迁移）处理状态，处理状态(0未处理1处理8失败9成功)
			Long dealState = progPackage.getDealstate();
			if (dealState == null || (dealState != 0 && dealState != 8)) {
				cmsLog.info("栏目单详细的处理状态不是“未处理”或“失败”，不加入迁移单。节目包ID："
						+ progPackage.getProductid());
				continue;
			}

			// 2 - 判断节目包的ProgType是否="V"
			if (progPackage.getProgtype().equalsIgnoreCase("V")) {
				// 2.2 - 如果="V"，继续
				// 2.2.1 - 调用方法5，根据节目包id 和
				// 主文件表示(progrank)，查询packagefiles、programfiles表，得到节目包的文件（主文件）
				// 返回： List<ProgramFiles>
				List programFileses = packageFilesManager
						.getProgramFilesesByProductidProgrank(
								progPackage.getProductid(), (long) 1 // 主文件标识，0
																		// - 不是;
																		// 1 - 是
						);
				if (programFileses == null || programFileses.size() == 0) {
					cmsLog.info("节目包下无文件。节目包ID：" + progPackage.getProductid());
					continue;
				}
				for (int j = 0; j < programFileses.size(); j++) {
					ProgramFiles programFiles = (ProgramFiles) programFileses
							.get(j);

					// 2.2.2 - 调用方法11，根据节目包ID、文件ID、一级库classcode，获得每个文件的在一级库的路径
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStoragePrgRel)Object[1]
					// (AmsStorageDir)Object[2]
					// (AmsStorageClass)Object[3]
					List onlinepaths = packageFilesManager
							.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									programFiles.getProgfileid(),
									stclasscodeOnline, // 一级库存储体等级code，从配置文件获取
									progPackage.getProductid());
					// 2.3 - 判断文件是否已经存在于一级库了，
					if (onlinepaths != null && onlinepaths.size() >= 2) {
						String destpath = (String) onlinepaths.get(0);
						if (destpath != null && !destpath.equalsIgnoreCase("")) {
							// 2.3.1 - 如果存在，
							// 判断，不处理
							cmsLog.info("节目包下的文件已经存在于一级库。文件ID："
									+ programFiles.getProgfileid());
							cmsLog.info("节目包下的文件在一级库的位置：" + destpath);
							cmsTransactionManager
									.updateRefreshState1OfProgPackage(
											packageFilesManager,
											progListDetailManager,
											progPackageManager,
											stclasscodeNearOnline,
											stclasscodeCaOnline,
											stclasscodeOnline, "", progPackage,
											1);
							continue;
						}
					}
					// 2.3.2 - 如果不存在，继续
					// 调用方法11，根据节目包ID、文件ID、缓存库classcode，获得每个文件的在缓存库的路径
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStoragePrgRel)Object[1]
					// (AmsStorageDir)Object[2]
					// (AmsStorageClass)Object[3]
					String sourcepath = ""; // 节目包主文件的源路径
					List sourcepaths = packageFilesManager
							.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									programFiles.getProgfileid(),
									stclasscodeCaOnline, // 缓存库存储体等级code，从配置文件获取
									progPackage.getProductid());
					if (sourcepaths != null && sourcepaths.size() >= 2) {
						sourcepath = (String) sourcepaths.get(0);
						List list = (List) sourcepaths.get(1);
						Object[] sourcerows = (Object[]) list.get(0);
						AmsStorage sourceamsst = (AmsStorage) sourcerows[0];
						AmsStoragePrgRel sourceamsstpr = (AmsStoragePrgRel) sourcerows[1];
						AmsStorageDir sourceamsstd = (AmsStorageDir) sourcerows[2];
						AmsStorageClass sourceamsstc = (AmsStorageClass) sourcerows[3];

						Date dfiledate = sourceamsstpr.getFiledate();
						String filedate = fileoper.convertDateToString(
								dfiledate, "yyyy-MM-dd HH:mm:ss");

						// 2.4 -
						// 调用方法2，根据stdir.filecode、配置文件一级库stclasscode，获取节目包主文件迁移的目标路径
						// 返回：List
						// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
						// 2 - List<Object[]>
						// (AmsStorage)Object[0]
						// (AmsStorageDir)Object[1]
						// (AmsStorageClass)Object[2]
						List destpaths = packageFilesManager
								.getDestPathByFilecodeStclasscode(
										sourceamsstd.getFilecode(), // 节目包主文件的filecode
										stclasscodeOnline // 一级库存储体等级code，从配置文件获取
								);
						if (destpaths != null && destpaths.size() >= 2) {
							String destpath = (String) destpaths.get(0);
							List list1 = (List) destpaths.get(1);
							Object[] destrows = (Object[]) list1.get(0);
							AmsStorage destamsst = (AmsStorage) destrows[0];
							AmsStorageDir destamsstd = (AmsStorageDir) destrows[1];
							AmsStorageClass destamsstc = (AmsStorageClass) destrows[2];

							if (destpath == null
									|| destpath.equalsIgnoreCase("")) {
								cmsLog.info("节目包下的主文件迁移到一级库的目标路径不存在。文件ID："
										+ programFiles.getProgfileid());
								continue;
							} else {
								// 处理destpath，加上filepath和文件名
								destpath = destpath.replace('\\', '/');
								destpath = fileoper.checkPathFormatRear(
										destpath, '/');
								if (sourceamsstpr.getFilepath() != null
										&& !sourceamsstpr.getFilepath()
												.equalsIgnoreCase("")) {
									destpath += sourceamsstpr.getFilepath();
								}
								destpath = destpath.replace('\\', '/');
								destpath = fileoper.checkPathFormatRear(
										destpath, '/');
								destpath += sourceamsstpr.getFilename();

								// 处理路径格式，去掉头
								// smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								// /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								sourcepath = sourcepath.substring(
										sourcepath.indexOf("/", 6),
										sourcepath.length());
								destpath = destpath.substring(
										destpath.indexOf("/", 6),
										destpath.length());

								// 2.5 - 把节目文件加入迁移单中
								strXml += "<Transfer-Entity SourceStorageClass=\"";
								strXml += sourceamsstc.getStclasscode();
								strXml += "\" SourceFileType=\"";
								strXml += programFiles.getFiletypeid();
								strXml += "\" DesStorageClass=\"";
								strXml += destamsstc.getStclasscode();
								strXml += "\" PriorityDate=\"";
								strXml += priorityDate;
								strXml += "\" SourceProgTitle=\"";
								strXml += progPackage.getProductname();
								strXml += "\" SourceFileName=\"";
								strXml += sourceamsstpr.getFilename();
								strXml += "\" SourceFileId=\"";
								strXml += sourceamsstpr.getProgfileid();
								strXml += "\" SourceProgid=\"";
								strXml += progPackage.getProductid();
								strXml += "\" IsCover=\"";
								strXml += "N";
								strXml += "\">";

								// 源文件
								strXml += "<Source Type=\"";
								strXml += sourceamsst.getStorageaccstype();
								strXml += "\" SourceDirId=\"";
								strXml += sourceamsstd.getStdirglobalid();
								strXml += "\" Hostname=\"";
								strXml += sourceamsst.getStorageip();
								strXml += "\" Port=\"";
								strXml += "";
								strXml += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									strXml += sourceamsst.getLoginname();
								strXml += "\" SourcestorageId=\"";
								strXml += sourceamsst.getStglobalid();
								strXml += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									strXml += sourceamsst.getLoginpwd();
								strXml += "\" FileDate=\"";
								strXml += filedate;
								strXml += "\" VariableFilePath=\"";
								strXml += sourceamsstpr.getFilepath();
								strXml += "\">";
								strXml += "<File>";
								strXml += sourcepath;
								strXml += "</File>";
								strXml += "</Source>";

								// 目标文件
								strXml += "<Destination Type=\"";
								strXml += destamsst.getStorageaccstype();
								strXml += "\" DesStorageDirId=\"";
								strXml += destamsstd.getStdirglobalid();
								strXml += "\" Hostname=\"";
								strXml += destamsst.getStorageip();
								strXml += "\" Port=\"";
								strXml += "";
								strXml += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									strXml += destamsst.getLoginname();
								strXml += "\" DesStorageId=\"";
								strXml += destamsst.getStglobalid();
								strXml += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									strXml += destamsst.getLoginpwd();
								strXml += "\" FileDate=\"";
								strXml += filedate;
								strXml += "\" VariableFilePath=\"";
								strXml += sourceamsstpr.getFilepath();
								strXml += "\">";
								strXml += "<File>";
								strXml += destpath; // 没有文件名，需要处理
								strXml += "</File>";
								strXml += "</Destination>";

								strXml += "</Transfer-Entity>";
							}
						} else {
							cmsLog.info("节目包下的主文件迁移到一级库的目标路径不存在。文件ID："
									+ programFiles.getProgfileid());
						}
					} else {
						cmsLog.info("节目包下的主文件在缓存库的源路径不存在。文件ID："
								+ programFiles.getProgfileid());
						continue;
					}
				}
			} else {
				// 2.1 - 如果不="V"，不处理
				// 20100205 01:24 北京修改
				// 如果是富媒体节目包，把节目包的state改为一级库
				cmsLog.info("节目包是富媒体。节目包ID：" + progPackage.getProductid());
//				progPackage.setState((long) 3);
//				progPackage.setDealstate((long) 0);
//				progPackageManager.update(progPackage);
//				cmsLog.info("节目包的状态修改为“一级库”(“播发库”)。");
//				cmsLog.info("节目包的处理状态修改为“未处理”。");
				// continue;
				/**
				 * HuangBo update by 2010年8月25日 13时37分
				 * 为富媒体文件目录生成迁移单
				 */
				List<ProgramFiles> programFiles = packageFilesManager
						.getProgramFilesesByProductidProgrank(
						progPackage.getProductid(), 1l // 主文件标识，0 - 不是; 1 - 是
				);
				
				for (ProgramFiles programFile : programFiles) {
					// 2.2.2 - 调用方法11，根据节目包ID、文件ID、一级库classcode，获得每个文件的在一级库的路径
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStoragePrgRel)Object[1]
					// (AmsStorageDir)Object[2]
					// (AmsStorageClass)Object[3]
					List onlinepaths = packageFilesManager
							.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									programFile.getProgfileid(),
									stclasscodeOnline, // 一级库存储体等级code，从配置文件获取
									progPackage.getProductid());
					// 2.3 - 判断文件是否已经存在于一级库了，
					if (onlinepaths != null && onlinepaths.size() >= 2) {
						String destpath = (String) onlinepaths.get(0);
						if (destpath != null && !destpath.equalsIgnoreCase("")) {
							// 2.3.1 - 如果存在，
							// 判断，不处理
							cmsLog.info("节目包下的文件已经存在于一级库。文件ID："
									+ programFile.getProgfileid());
							cmsLog.info("节目包下的文件在一级库的位置：" + destpath);
							cmsTransactionManager
									.updateRefreshState1OfProgPackage(
											packageFilesManager,
											progListDetailManager,
											progPackageManager,
											stclasscodeNearOnline,
											stclasscodeCaOnline,
											stclasscodeOnline, "", progPackage,
											1);
							continue;
						}
					}
					
					// 2.3.2 - 如果不存在，继续
					// 调用方法11，根据节目包ID、文件ID、缓存库classcode，获得每个文件的在缓存库的路径
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStoragePrgRel)Object[1]
					// (AmsStorageDir)Object[2]
					// (AmsStorageClass)Object[3]
					String sourcepath = ""; // 节目包主文件的源路径
					List sourcepaths = packageFilesManager
							.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									programFile.getProgfileid(),
									stclasscodeCaOnline, // 缓存库存储体等级code，从配置文件获取
									progPackage.getProductid());
					
					if (sourcepaths != null && sourcepaths.size() >= 2) {
						sourcepath = (String) sourcepaths.get(0);
						List list = (List) sourcepaths.get(1);
						Object[] sourcerows = (Object[]) list.get(0);
						AmsStorage sourceamsst = (AmsStorage) sourcerows[0];
						AmsStoragePrgRel sourceamsstpr = (AmsStoragePrgRel) sourcerows[1];
						AmsStorageDir sourceamsstd = (AmsStorageDir) sourcerows[2];
						AmsStorageClass sourceamsstc = (AmsStorageClass) sourcerows[3];

						Date dfiledate = sourceamsstpr.getFiledate();
						String filedate = fileoper.convertDateToString(
								dfiledate, "yyyy-MM-dd HH:mm:ss");

						// 2.4 -
						// 调用方法2，根据stdir.filecode、配置文件一级库stclasscode，获取节目包主文件迁移的目标路径
						// 返回：List
						// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
						// 2 - List<Object[]>
						// (AmsStorage)Object[0]
						// (AmsStorageDir)Object[1]
						// (AmsStorageClass)Object[2]
						/**
						 * HuangBo update by 2010年8月25日 14时53分
						 * 原来是通过源的等级code, 由于之前未涉及到富媒体. 故fileCode参数可能不正确.
						 * 现在为了简单, 直接写死在这. 为了查出播出库, 富媒体的迁移存放路径
						 */
						List destpaths = packageFilesManager
								.getDestPathByFilecodeStclasscode(
										"RMZIP", // 节目包主文件的filecode
										stclasscodeOnline // 一级库存储体等级code，从配置文件获取
								);
						if (destpaths != null && destpaths.size() >= 2) {
							String destpath = (String) destpaths.get(0);
							List list1 = (List) destpaths.get(1);
							Object[] destrows = (Object[]) list1.get(0);
							AmsStorage destamsst = (AmsStorage) destrows[0];
							AmsStorageDir destamsstd = (AmsStorageDir) destrows[1];
							AmsStorageClass destamsstc = (AmsStorageClass) destrows[2];

							if (destpath == null
									|| destpath.equalsIgnoreCase("")) {
								cmsLog.info("节目包下的主文件迁移到一级库的目标路径不存在。文件ID："
										+ programFile.getProgfileid());
								continue;
							} else {
								// 处理destpath，加上filepath和文件名
								destpath = destpath.replace('\\', '/');
								destpath = fileoper.checkPathFormatRear(
										destpath, '/');
								if (sourceamsstpr.getFilepath() != null
										&& !sourceamsstpr.getFilepath()
												.equalsIgnoreCase("")) {
									destpath += sourceamsstpr.getFilepath();
								}
								destpath = destpath.replace('\\', '/');
								destpath = fileoper.checkPathFormatRear(
										destpath, '/');
								destpath += sourceamsstpr.getFilename();

								// 处理路径格式，去掉头
								// smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								// /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								sourcepath = sourcepath.substring(
										sourcepath.indexOf("/", 6),
										sourcepath.length());
								destpath = destpath.substring(
										destpath.indexOf("/", 6),
										destpath.length());

								// 2.5 - 把节目文件加入迁移单中
								strXml += "<Transfer-Entity SourceStorageClass=\"";
								strXml += sourceamsstc.getStclasscode();
								strXml += "\" SourceFileType=\"";
								strXml += programFile.getFiletypeid();
								strXml += "\" DesStorageClass=\"";
								strXml += destamsstc.getStclasscode();
								strXml += "\" PriorityDate=\"";
								strXml += priorityDate;
								strXml += "\" SourceProgTitle=\"";
								strXml += progPackage.getProductname();
								strXml += "\" SourceFileName=\"";
								strXml += sourceamsstpr.getFilename();
								strXml += "\" SourceFileId=\"";
								strXml += sourceamsstpr.getProgfileid();
								strXml += "\" SourceProgid=\"";
								strXml += progPackage.getProductid();
								strXml += "\" IsCover=\"";
								strXml += "N";
								strXml += "\">";

								// 源文件
								strXml += "<Source Type=\"";
								strXml += sourceamsst.getStorageaccstype();
								strXml += "\" SourceDirId=\"";
								strXml += sourceamsstd.getStdirglobalid();
								strXml += "\" Hostname=\"";
								strXml += sourceamsst.getStorageip();
								strXml += "\" Port=\"";
								strXml += "";
								strXml += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									strXml += sourceamsst.getLoginname();
								strXml += "\" SourcestorageId=\"";
								strXml += sourceamsst.getStglobalid();
								strXml += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									strXml += sourceamsst.getLoginpwd();
								strXml += "\" FileDate=\"";
								strXml += filedate;
								strXml += "\" VariableFilePath=\"";
								strXml += sourceamsstpr.getFilepath();
								strXml += "\">";
								strXml += "<Folder>";
								strXml += sourcepath;
								strXml += "</Folder>";
								strXml += "</Source>";

								// 目标文件
								strXml += "<Destination Type=\"";
								strXml += destamsst.getStorageaccstype();
								strXml += "\" DesStorageDirId=\"";
								strXml += destamsstd.getStdirglobalid();
								strXml += "\" Hostname=\"";
								strXml += destamsst.getStorageip();
								strXml += "\" Port=\"";
								strXml += "";
								strXml += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									strXml += destamsst.getLoginname();
								strXml += "\" DesStorageId=\"";
								strXml += destamsst.getStglobalid();
								strXml += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									strXml += destamsst.getLoginpwd();
								strXml += "\" FileDate=\"";
								strXml += filedate;
								strXml += "\" VariableFilePath=\"";
								strXml += sourceamsstpr.getFilepath();
								strXml += "\">";
								strXml += "<Folder>";
								strXml += destpath; // 没有文件名，需要处理
								strXml += "</Folder>";
								strXml += "</Destination>";

								strXml += "</Transfer-Entity>";
							}
						} else {
							cmsLog.info("节目包下的主文件迁移到一级库的目标路径不存在。文件ID："
									+ programFile.getProgfileid());
						}
					} else {
						cmsLog.info("节目包下的主文件在缓存库的源路径不存在。文件ID："
								+ programFile.getProgfileid());
						continue;
					}
				}
			}
		}
		strXml += "</Distribution>";
		strXml += "</Migration>";

		// 3 - 调用方法2，根据配置文件stclasscode、配置文件filecode，获得迁移单xml目标路径
		// 见上

		// 4 - 调用方法9，生成xml到指定位置
		if (fileoper.createSmbFile(destpathMigration + xmlFilename, strXml) == 0) {
			// 5 - 调用方法8(还是方法12??)，根据编单日期、文件类型8，生成xml对应的发布文件表记录
			// 同时需要修改
			ProgListFile progListFile = new ProgListFile();
			progListFile.setScheduledate(scheduledate);
			progListFile.setFilename(xmlFilename);
			progListFile.setFiletype((long) 8); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
			progListFile.setInputtime(new Date());
			progListFile.setInputmanid(operatorId);
			progListFile.setState1((long) 0);
			progListFile.setState2((long) 0);
			progListFile.setDate1(new Date());
			progListFile.setColumnxml(strXml);

			CmsResultDto cmsResultDto = cmsTransactionManager.saveProgListFile(
					progListFileManager, progListFile, date, null);

			if (cmsResultDto.getResultCode() != 0) {
				fileoper.deleteSmbFile(destpathMigration + xmlFilename);
				return 1;
			}

			cmsLog.info("这里没有使用事务处理，有待优化。");
			for (int i = 0; i < plds.size(); i++) {
				Object[] rows = (Object[]) plds.get(i);
				ProgListDetail progListDetail = (ProgListDetail) rows[0];
				ProgPackage progPackage = (ProgPackage) rows[1];

				// 如果节目包状态为加扰，处理状态为未处理，修改
				Long state = progPackage.getState();
				Long dealState = progPackage.getDealstate();
				if (state == 2) {
					/**
					 * HuangBo update by 2010年8月25日 15时2分
					 * 迁移单生成成功后, 富媒体也需要修改节目包状态. 
					 * 而不是只有视频类节目包才需要修改节目包状态为处理中..
					 * 之前由于富媒体文件由CMS直接COPY到一级库, 现在改为由迁移模块迁移富媒体目录
					 */
//					if (progPackage.getProgtype().equalsIgnoreCase("V")) {
						if (dealState == 0 || dealState == 8) {
							progPackage.setDealstate((long) 1); // 处理状态(0未处理1处理8失败9成功)
						}
//					} else if (progPackage.getProgtype().equalsIgnoreCase("R")) {
//						progPackage.setDealstate((long) 0); // 处理状态(0未处理1处理8失败9成功)
						
//					}
				}

				progPackageManager.update(progPackage);
				cmsLog.info("节目包ID为：" + progPackage.getProductid());
				cmsLog.info("节目包的处理状态修改为：" + progPackage.getDealstate());
			}
		} else {
			cmsLog.info("生成迁移单xml文件失败。目标路径：" + destpathMigration + xmlFilename);
			return 1;
		}
		cmsLog.info("Cms -> MigrationServiceImpl -> generateMigration returns.");
		return 0;
	}
	
	// 20100204 北京修改
	// 修改date当天所有的富媒体栏目下的节目包状态为一级库
	private int refreshRichMediaProgPackageStates(
			String date,					// 编单日期，格式：yyyy-MM-dd
			String operatorId				// 操作人员ID
	) {
		cmsLog.info("Cms -> MigrationServiceImpl -> refreshRichMediaProgPackageStates...");
		
		List plds = progListDetailManager.getProgListDetailsProgPackagesByDateAndDefcatseq(
				date, "");
		for (int i = 0; i < plds.size(); i++) {
			Object[] rows = (Object[]) plds.get(i);
			ProgListDetail progListDetail = (ProgListDetail) rows[0];
			ProgPackage progPackage = (ProgPackage) rows[1];

			// 判断栏目单详细，节目包的（迁移）处理状态，处理状态(0未处理1处理8失败9成功)
			Long dealState = progPackage.getDealstate();
			if (dealState == null || (dealState != 0 && dealState != 8)) {
				cmsLog.info("栏目单详细的处理状态不是“未处理”或“失败”，不加入迁移单。节目包ID："
						+ progPackage.getProductid());
				continue;
			}

			// 2 - 判断节目包的ProgType是否="V"
			if (progPackage.getProgtype().equalsIgnoreCase("R")) {
				// 修改富媒体节目包state = 3
				// 节目包状态（0导入1缓存库2加扰库3播控库）
				progPackage.setState((long) 3);
				progPackage.setDealstate((long) 0);
				progPackageManager.update(progPackage);
				cmsLog.info("富媒体节目包的状态已改为播控库。节目包ID："
						+ progPackage.getProductid());
			}
		}

		cmsLog.info("Cms -> MigrationServiceImpl -> refreshRichMediaProgPackageStates returns.");
		return 0;
	}
	
	// 20100123 11:05
	// 生成一级库的迁移单，只有总表活动为“迁移”的栏目单，才能生成迁移单
	public CmsResultDto generateMigrationToOnline(
			String date,					// 编单日期，格式：yyyy-MM-dd
			String operatorId				// 操作人员ID
	) {
		// 0 - 数据导出@上海； 
		// 1 - 迁移至播发库@北京； 
		// 2 - 数据导入@北京
		// 3 - 节目录入@上海
		cmsLog.info("Cms -> MigrationServiceImpl -> generateMigrationToOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 北京修改
//		refreshRichMediaProgPackageStates(date, operatorId);
		
		// 判断总表活动是否是"迁移"FU00000085
		String scheduledate = convertDateToScheduleDate(date);
		List progListMangs = progListMangManager.findByProperty("scheduledate", scheduledate);
		if (progListMangs != null && progListMangs.size() > 0) {
			ProgListMang progListMang = (ProgListMang) progListMangs.get(0);

			if (progListMang.getIdAct().equalsIgnoreCase("FU00000085")) // 只有"迁移"状态的栏目单才能迁移
			{
				cmsLog.info("这里有注释了的代码，用于测试，正式使用需要做修改。");
				// 生成迁移单
				if (generateMigration(date, operatorId) == 0) {
					String str = "生成迁移单成功。编单日期：" + date;
					cmsLog.info(str);
				} else {
					String str = "生成迁移单失败。编单日期：" + date;
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.info(str);
				}
			} else {
				String str = "栏目单目前状态不是“迁移”状态，不生成迁移单。目前状态ID："
						+ progListMang.getIdAct();
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.info(str);
				return cmsResultDto;
			}
		} else {
			String str = "栏目单的总表记录不存在，不生成迁移单。编单日期：" + date;
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.info(str);
		}

		cmsLog.info("Cms -> MigrationServiceImpl -> generateMigrationToOnline returns.");
		return cmsResultDto;
	}
	
	// 20100126 9:19
	// 解析xml获得AmsStoragePrgRel
	private List getAmsStoragePrgRelFromXml(String strXml) {
		// 返回：
		// List
		// 		1 - AmsStoragePrgRel
		// 		2 - String ProglistId
		
		List retlist = new ArrayList();
		
		String stglobalid = "";					// DesStorageId
		String stdirglobalid = "";				// DesStorageDirId
		String prglobalid = "";					// SourceProgid--
		String progfileid = "";					// SourceFileId--
		String ftypeglobalid = "";				// SourceFileType--
		String filename = "";					// SourceFileName--
		String filepath = "";					// VariableFilePath
		String filedate = "";					// FileDate
		Date dFiledate = new Date();
		String proglistId = "";					// ProglistId
		
		FileOperationImpl fileOpr = new FileOperationImpl();
		
		// 为strXml补充头和根节点
		strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <Distribution>" + strXml;
		strXml += "</Distribution>";
		
		// 解析xml
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// 将String转成parse可以识别的InputSource
			StringReader sr = new StringReader(strXml);
			InputSource is = new InputSource(sr);
			Document doc = builder.parse(is);
			doc.normalize();

			// 查找cell节点
			NodeList cells = doc.getElementsByTagName("Transfer-Entity");
			if (cells.getLength() > 0) {
				Node cell = cells.item(0);
				Element cellattr = (Element) cells.item(0);

				// 取节点Transfer-Entity的属性
				if (cellattr.hasAttribute("SourceFileType")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("SourceFileType").getNodeValue());
					ftypeglobalid = cell.getAttributes()
							.getNamedItem("SourceFileType").getNodeValue();
				}
				if (cellattr.hasAttribute("SourceFileName")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("SourceFileName").getNodeValue());
					filename = cell.getAttributes()
							.getNamedItem("SourceFileName").getNodeValue();
				}
				if (cellattr.hasAttribute("SourceFileId")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("SourceFileId").getNodeValue());
					progfileid = cell.getAttributes()
							.getNamedItem("SourceFileId").getNodeValue();
				}
				if (cellattr.hasAttribute("SourceProgid")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("SourceProgid").getNodeValue());
					prglobalid = cell.getAttributes()
							.getNamedItem("SourceProgid").getNodeValue();
				}
				if (cellattr.hasAttribute("PriorityDate")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("PriorityDate").getNodeValue());
					proglistId = cell.getAttributes()
							.getNamedItem("PriorityDate").getNodeValue();
				}

				// 取节点Transfer-Entity的子节点Source Destination
				if (cell.hasChildNodes()) {
					NodeList secNodes = cell.getChildNodes();
					for (int j = 0; j < secNodes.getLength(); j++) {
						Node secNode = secNodes.item(j);
						Element nodeattr = null;
						if (secNode.getNodeType() == Element.ELEMENT_NODE) {
							// Element nodeattr = (Element)secNodes.item(j);
							nodeattr = (Element) secNode;
						}
						if (secNode.getNodeName().equalsIgnoreCase("Source")) {
							if (nodeattr.hasAttribute("FileDate")) // 判断节点有tag属性
							{
								// cmsLog.info(secNode.getAttributes().getNamedItem("FileDate").getNodeValue());
								filedate = secNode.getAttributes()
										.getNamedItem("FileDate")
										.getNodeValue();
							}
							if (nodeattr.hasAttribute("VariableFilePath")) // 判断节点有tag属性
							{
								// cmsLog.info(secNode.getAttributes().getNamedItem("VariableFilePath").getNodeValue());
								filepath = secNode.getAttributes()
										.getNamedItem("VariableFilePath")
										.getNodeValue();
							}
						} else if (secNode.getNodeName().equalsIgnoreCase(
								"Destination")) {
							if (nodeattr.hasAttribute("DesStorageId")) // 判断节点有tag属性
							{
								// cmsLog.info(secNode.getAttributes().getNamedItem("DesStorageId").getNodeValue());
								stglobalid = secNode.getAttributes()
										.getNamedItem("DesStorageId")
										.getNodeValue();
							}
							if (nodeattr.hasAttribute("DesStorageDirId")) // 判断节点有tag属性
							{
								// cmsLog.info(secNode.getAttributes().getNamedItem("DesStorageDirId").getNodeValue());
								stdirglobalid = secNode.getAttributes()
										.getNamedItem("DesStorageDirId")
										.getNodeValue();
							}
						}
					}
				}
			}

			if (filedate != null && !filedate.equalsIgnoreCase("")) {
				dFiledate = fileOpr.convertStringToDate(filedate,
						"yyyy-MM-dd HH:mm:ss");
			}

			AmsStoragePrgRel aspr = new AmsStoragePrgRel();
			aspr.setStglobalid(stglobalid); // DesStorageId
			aspr.setStdirglobalid(stdirglobalid); // DesStorageDirId
			aspr.setPrglobalid(prglobalid); // SourceProgid
			aspr.setProgfileid(progfileid); // SourceFileId
			aspr.setFtypeglobalid(ftypeglobalid); // SourceFileType
			aspr.setFilename(filename); // SourceFileName
			aspr.setFilepath(filepath); // VariableFilePath
			aspr.setFiledate(dFiledate); // FileDate
			aspr.setUploadtime(new Date()); //
			aspr.setInputmanid("");
			aspr.setInputtime(new Date());
			aspr.setRemark("");

			retlist.add(aspr);
			retlist.add(proglistId);
		} catch (Exception ex) {
			cmsLog.error("解析迁移模块反馈xml异常。");
			cmsLog.error(ex.toString());
			retlist = null;
		}

		return retlist;
	}

	// 20100125 22:34
	// 完成迁移到一级库，迁移模块完成迁移后，通过webservice调用这个接口
	public CmsResultDto finishMigrationToOnline(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
	) {
		cmsLog.info("Cms -> MigrationServiceImpl -> finishMigrationToOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsResultDto = this.migrationModuleManager.updateFinishMigrationFromCaonlineToOnline_123(
				transferEntity, result, resultDes);
		
		cmsLog.info("Cms -> MigrationServiceImpl -> finishMigrationToOnline returns.");
		return cmsResultDto;
	}

	// 20100223 11:20
	// 数据导入迁移反馈
	public CmsResultDto finishMigrationImportDataToBjNearOnline(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
	) {
		cmsLog.info("Cms -> MigrationServiceImpl -> finishMigrationImportDataToBjNearOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件，获取
		// 节目包状态，0-导入 1-缓存库 2-加扰库 3-播控库
		String stclasscodeNearOnline = "NearOnline";		// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline";			// 加扰库存储体等级code
		String stclasscodeOnline = "Online";				// 播控库存储体等级code
		
		if (result.equalsIgnoreCase("Y")) {
			cmsLog.info("迁移反馈为Y. 迁移成功! ");

			// 迁移成功
			// 新增记录，到文件存放位置表
			// 解析xml
			List list = getAmsStoragePrgRelFromXml(transferEntity);
			if (list != null && list.size() >= 2) {
				AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) list
						.get(0);
				String proglistId = (String) list.get(1); // 栏目单日期

				// 新增文件位置表记录
				// 作废：transferEntity中的文件所在的栏目单详细中的处理状态改为“成功”，处理状态(0未处理1处理8失败9成功)
				// 查询transferEntity中的文件所在的节目包状态（ProgListDetail中）是否需要改变，
				// 调用refreshState1sInProgListDetail，需要date从ProglistId获得，需要栏目单详细从SourceProgid获得节目包ID
				// 作废：如果需要改变，则改变，同时判断栏目单分表总表状态是否需要修改
				// 作废：如需要，则修改栏目单分表总表状态
				cmsResultDto = cmsTransactionManager
						.saveFinishMigrationImportDataToBjNearOnline(
								amsstorageprgrelManager, progListDetailManager,
								packageFilesManager, progPackageManager,
								stclasscodeNearOnline, stclasscodeCaOnline,
								stclasscodeOnline, amsStoragePrgRel);
			} else {
				String str = "解析返回xml失败。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		} else if (result.equalsIgnoreCase("N")) {
			// 迁移失败
			// 查询节目包的栏目单详细，修改处理状态为“失败”，处理状态(0未处理1处理8失败9成功)
			List list = getAmsStoragePrgRelFromXml(transferEntity);
			if (list != null && list.size() >= 2) {
				AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) list
						.get(0);
				String proglistId = (String) list.get(1); // 栏目单日期

				ProgPackage pp = (ProgPackage) progPackageManager
						.getById(amsStoragePrgRel.getPrglobalid());
				if (pp != null) {
					pp.setDealstate((long) 8);
					progPackageManager.update(pp);
				}
			} else {
				String str = "解析返回xml失败。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		} else {
			String str = "未知输入结果。result：" + result;
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.info(str);
		}
		cmsLog.info("Cms -> MigrationServiceImpl -> finishMigrationImportDataToBjNearOnline returns.");
		return cmsResultDto;
	}

	// 20100312 13:17
	// 节目录入时，下迁移单
	public CmsResultDto generateMigrationForProgram(
			ProgramInfo programInfo,
			ProgramFiles programFiles,
			String sourcePartPath,			// 源文件(相对)路径，
			String sourceStclasscode,		// 源文件的存储体等级code
			String filePath,				// 目标文件的filePath
			String destStclasscode			// 目标文件的存储体等级code
	) {
		cmsLog.info("Cms -> MigrationServiceImpl -> generateMigrationForProgram...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 0 - 数据导出@上海； 
		// 1 - 迁移至播发库@北京； 
		// 2 - 数据导入@北京
		// 3 - 节目录入@上海
		
		// 配置文件，获取 
		String stclasscodePrepared = "Prepared";		// 节目录入存储体等级code
		String stclasscodeOnline = "Online";			// 一级库存储体等级code
		String stclasscodeNearOnline = "NearOnline";	// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline";		// 缓存库存储体等级code
		String stclasscodeBjOnline = "BjOnline";		// 上海的北京缓存库存储体等级code
		String stclasscodeMigration = "Migration";		// 迁移单的目标存储体等级code
		String filecodeMigration = "MIGXML";			// 迁移单xml的filecode


		Date ddate = new Date();
		int filecount = 0;
		String strXml = "";								// 迁移单内容
		String destpathMigration = "";					// 迁移单的目标路径
		String xmlFilename = "";						// 迁移单的文件名字
		String filename = "";
		
		// xml内容字段
		String proglistId = fileoper.convertDateToString(ddate, "yyyyMMdd000000");				
		String requestId = "";				// 当前时间，不重复
		String createDate = "";
		String priorityDate = "";
		// 0 - 数据导出@上海
		// 1 - 迁移至播发库@北京
		// 2 - 数据导入@北京
		// 3 - 节目录入@上海
		String type = "3";

		try {
			requestId = fileoper.convertDateToString(ddate,
					"yyyyMMddHHmmssSSSSSS");
			createDate = fileoper.convertDateToString(ddate,
					"yyyy-MM-dd HH:mm:ss");
			priorityDate = fileoper.convertDateToString(ddate, "yyyy-MM-dd")
					+ " 00:00:00";
			xmlFilename = proglistId + requestId + ".xml";

			programInfo = (ProgramInfo) programinfoManager.getById(programInfo
					.getProgramid());
			if (programInfo == null) {
				String str = "输入节目不存在，不生成迁移任务单。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			} else {
				// 0 - 迁移
				// 1 - 迁移失败
				// 2 - 新入库
				// 3 - 已包装
				cmsLog.info("判断节目文件是否需要迁移...");
				if (programInfo.getDsflag() == 0
						|| programInfo.getDsflag() == 1) {
					// 调用方法2，根据配置文件stclasscode、配置文件filecode，获得迁移单xml目标路径
					// 返回：List
					// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStorageDir)Object[1]
					// (AmsStorageClass)Object[2]
					cmsLog.info("准备获得迁移单的目标路径...");
					List xmldestpaths = packageFilesManager
							.getDestPathByFilecodeStclasscode(
									filecodeMigration, // 迁移单xml的filecode
									stclasscodeMigration // 迁移单的目标存储体等级code
							);
					if (xmldestpaths != null && xmldestpaths.size() >= 2) {
						destpathMigration = (String) xmldestpaths.get(0);
						if (destpathMigration == null
								|| destpathMigration.equalsIgnoreCase("")) {
							String str = "迁移单目标路径不存在，不生成迁移任务单。";
							cmsResultDto.setResultCode((long) 1);
							cmsResultDto.setErrorMessage(str);
							cmsLog.warn(str);
						} else {
							destpathMigration = fileoper.checkPathFormatRear(
									destpathMigration, '/');
							cmsLog.info("迁移单的目标路径 - " + destpathMigration);

							String sourcepath = "";
							AmsStorage sourceamsst = null;
							// AmsStoragePrgRel sourceamsstpr = null;
							AmsStorageDir sourceamsstd = null;
							AmsStorageClass sourceamsstc = null;

							String destpath = "";
							AmsStorage destamsst = null;
							AmsStorageDir destamsstd = null;
							AmsStorageClass destamsstc = null;

							strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
							strXml += "<Migration>\r\n";
							strXml += "<Distribution ProglistId=\"";
							strXml += proglistId;
							strXml += "\" RequestId=\"";
							strXml += requestId;
							strXml += "\" CreateDate=\"";
							strXml += createDate;
							strXml += "\" Type=\"";
							strXml += type;
							strXml += "\">\r\n";

							cmsLog.info("准备获得迁移源文件路径...");
							// 返回：List<Object[]>
							// (AmsStorage)Object[0]
							// (AmsStorageDir)Object[1]
							// (AmsStorageClass)Object[2]
							List sources = amsstoragedirManager
									.getStorageStoragedirsByStclasscodeFilecode(
											stclasscodePrepared, // 节目录入区存储体等级code
											programFiles.getFilecode() // 节目文件的filecode
									);
							if (sources != null && sources.size() > 0) {
								Object[] sourcerows = (Object[]) sources.get(0);
								sourceamsst = (AmsStorage) sourcerows[0];
								sourceamsstd = (AmsStorageDir) sourcerows[1];
								sourceamsstc = (AmsStorageClass) sourcerows[2];

								sourcepath = "smb://";
								if (sourceamsst.getLoginname() != null)
									sourcepath += sourceamsst.getLoginname();
								sourcepath += ":";
								if (sourceamsst.getLoginpwd() != null)
									sourcepath += sourceamsst.getLoginpwd();
								sourcepath += "@";
								sourcepath += sourceamsst.getStorageip();
								// sourcepath += "/";
								String mappath = sourceamsst.getMappath()
										.replace('\\', '/');
								if (mappath != null
										&& !mappath.equalsIgnoreCase("")
										&& !mappath.equalsIgnoreCase("/")) {
									mappath = fileoper.checkPathFormatFirst(
											mappath, '/');
									sourcepath += mappath;
								}
								sourcepath = fileoper.checkPathFormatRear(
										sourcepath, '/');
								String dirname = sourceamsstd
										.getStoragedirname().replace('\\', '/');
								if (dirname != null
										&& !dirname.equalsIgnoreCase("")
										&& !dirname.equalsIgnoreCase("/")) {
									sourcepath += dirname;
								}
								sourcepath = fileoper.checkPathFormatRear(
										sourcepath, '/');

								if (sourcePartPath != null
										&& !sourcePartPath.equalsIgnoreCase("")) {
									sourcePartPath = sourcePartPath.replace(
											'\\', '/');
									sourcepath += sourcePartPath;

									// 获得filename
									int begin = sourcePartPath.lastIndexOf('/') + 1;
									int end = sourcePartPath.length();
									if (begin >= 0 && end >= 0) {
										filename = sourcePartPath.substring(
												begin, end);
									} else {
										filename = "";
									}
								} else {
									cmsLog.warn("输入源文件相对路径为空，不生成迁移任务单。");
									sourcepath = "";
								}
							}

							// 调用方法2，根据stdir.filecode、配置文件在上海的北京缓存库stclasscode，获取节目包主文件迁移的目标路径
							// 返回：List
							// 1 - String 目标路径()
							// 格式："smb://hc:hc@172.23.19.66/公用/"
							// 2 - List<Object[]>
							// (AmsStorage)Object[0]
							// (AmsStorageDir)Object[1]
							// (AmsStorageClass)Object[2]
							cmsLog.info("准备获得迁移目标文件路径...");
							List destpaths = packageFilesManager
									.getDestPathByFilecodeStclasscode(
											programFiles.getFilecode(), // 节目文件的filecode
											stclasscodeNearOnline // 在上海的缓存库存储体等级code，从配置文件获取
									);
							if (destpaths != null && destpaths.size() >= 2) {
								destpath = (String) destpaths.get(0);
								List list1 = (List) destpaths.get(1);
								Object[] destrows = (Object[]) list1.get(0);
								destamsst = (AmsStorage) destrows[0];
								destamsstd = (AmsStorageDir) destrows[1];
								destamsstc = (AmsStorageClass) destrows[2];

								if (destpath == null
										|| destpath.equalsIgnoreCase("")) {
									cmsLog.warn("目标路径不存在。文件ID："
											+ programFiles.getProgfileid());
								} else {
									// 处理destpath，加上filepath和文件名
									destpath = destpath.replace('\\', '/');
									destpath = fileoper.checkPathFormatRear(
											destpath, '/');
									if (filePath != null
											&& !filePath.equalsIgnoreCase("")) {
										destpath += filePath;
										destpath = destpath.replace('\\', '/');
										destpath = fileoper
												.checkPathFormatRear(destpath,
														'/');
									}
									destpath += programFiles.getFilename();
								}
							}

							cmsLog.info("源文件路径 - " + sourcepath);
							cmsLog.info("目标文件路径 - " + destpath);

							if (sourcepath != null
									&& !sourcepath.equalsIgnoreCase("")) {
								// 处理路径格式，去掉头
								// smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								// /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								sourcepath = sourcepath.substring(
										sourcepath.indexOf("/", 6),
										sourcepath.length());
							}
							if (destpath != null
									&& !destpath.equalsIgnoreCase("")) {
								// 处理路径格式，去掉头
								// smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								// /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								destpath = destpath.substring(
										destpath.indexOf("/", 6),
										destpath.length());
							}

							if (sourcepath == null
									|| destpath == null
									|| sourcepath.equalsIgnoreCase("")
									|| destpath.equalsIgnoreCase("")
									|| sourceamsst == null
									// || sourceamsstpr == null
									|| sourceamsstd == null
									|| sourceamsstc == null
									|| destamsst == null || destamsstd == null
									|| destamsstc == null) {
								String str = "输入参数为空，不生成迁移任务单...文件ID："
										+ programFiles.getProgfileid();
								cmsLog.warn(str);
							} else {
								// 生成迁移单
								strXml += "<Transfer-Entity SourceStorageClass=\"";
								strXml += sourceamsstc.getStclasscode();
								strXml += "\" SourceFileType=\"";
								strXml += programFiles.getFiletypeid();
								strXml += "\" DesStorageClass=\"";
								strXml += destamsstc.getStclasscode();
								strXml += "\" PriorityDate=\"";
								strXml += priorityDate;
								strXml += "\" SourceProgTitle=\"";
								strXml += programInfo.getTitle();
								strXml += "\" SourceFileName=\"";
								strXml += filename;
								strXml += "\" SourceFileId=\"";
								strXml += programFiles.getProgfileid();
								strXml += "\" SourceProgid=\"";
								strXml += programInfo.getProgramid();
								strXml += "\" IsCover=\"";
								strXml += "N";
								strXml += "\">\r\n";

								// 源文件
								strXml += "<Source Type=\"";
								strXml += sourceamsst.getStorageaccstype();
								strXml += "\" SourceDirId=\"";
								strXml += sourceamsstd.getStdirglobalid();
								strXml += "\" Hostname=\"";
								strXml += sourceamsst.getStorageip();
								strXml += "\" Port=\"";
								strXml += "";
								strXml += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									strXml += sourceamsst.getLoginname();
								strXml += "\" SourcestorageId=\"";
								strXml += sourceamsst.getStglobalid();
								strXml += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									strXml += sourceamsst.getLoginpwd();
								strXml += "\" FileDate=\"";
								// strXml += strfiledate;
								strXml += "\" VariableFilePath=\"";
								strXml += filePath;// sourceamsstpr.getFilepath();
								strXml += "\">\r\n";
								strXml += "<File>";
								strXml += sourcepath;
								strXml += "</File>\r\n";
								strXml += "</Source>\r\n";

								// 目标文件
								strXml += "<Destination Type=\"";
								strXml += destamsst.getStorageaccstype();
								strXml += "\" DesStorageDirId=\"";
								strXml += destamsstd.getStdirglobalid();
								strXml += "\" Hostname=\"";
								strXml += destamsst.getStorageip();
								strXml += "\" Port=\"";
								strXml += "";
								strXml += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									strXml += destamsst.getLoginname();
								strXml += "\" DesStorageId=\"";
								strXml += destamsst.getStglobalid();
								strXml += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									strXml += destamsst.getLoginpwd();
								strXml += "\" FileDate=\"";
								strXml += createDate;
								strXml += "\" VariableFilePath=\"";
								strXml += filePath;// sourceamsstpr.getFilepath();
								strXml += "\">\r\n";
								strXml += "<File>";
								strXml += destpath; // 没有文件名，需要处理
								strXml += "</File>\r\n";
								strXml += "</Destination>\r\n";

								strXml += "</Transfer-Entity>\r\n";

								filecount++;
							}
							strXml += "</Distribution>\r\n";
							strXml += "</Migration>\r\n";

							if (filecount > 0 && strXml != null
									&& !strXml.equalsIgnoreCase("")) {
								cmsLog.info("有" + filecount
										+ "个文件需要迁移，准备生成迁移任务单文件...");
								cmsLog.info("目标 - " + destpathMigration
										+ xmlFilename);
								// 生成迁移单文件
								if (fileoper.createSmbFile(destpathMigration
										+ xmlFilename, strXml) == 0) {
									// 生成迁移单文件成功
									cmsLog.info("生成迁移任务单文件成功。");
									// cmsLog.info("准备修改节目状态...");
									// cmsLog.warn("这里没有使用事务处理，有待优化。");

									// 0 - 迁移
									// 1 - 迁移失败
									// 2 - 新入库
									// 3 - 已包装
									// programInfo.setDsflag((long)0);
									// programinfoManager.update(programInfo);
								} else {
									// 生成迁移单文件失败
									String str = "生成迁移单文件失败。";
									cmsResultDto.setResultCode((long) 1);
									cmsResultDto.setErrorMessage(str);
									cmsLog.warn(str);

									// 0 - 迁移
									// 1 - 迁移失败
									// 2 - 新入库
									// 3 - 已包装
									// programInfo.setDsflag((long)1);
									// programinfoManager.update(programInfo);
								}
							} else {
								String str = "需要迁移的文件数为0，不生成迁移单，修改节目包的状态和处理状态。";
								cmsResultDto.setResultCode((long) 0);
								cmsResultDto.setErrorMessage(str);
								cmsLog.info(str);

								// cmsLog.info("准备修改节目状态...");
								// cmsLog.warn("这里没有使用事务处理，有待优化。");

								// 0 - 迁移
								// 1 - 迁移失败
								// 2 - 新入库
								// 3 - 已包装
								// programInfo.setDsflag((long)1);
								// programinfoManager.update(programInfo);
							}
						}
					} else {
						String str = "迁移单目标路径不存在，不生成迁移任务单。";
						cmsResultDto.setResultCode((long) 1);
						cmsResultDto.setErrorMessage(str);
						cmsLog.warn(str);

						// 0 - 迁移
						// 1 - 迁移失败
						// 2 - 新入库
						// 3 - 已包装
						// programInfo.setDsflag((long)1);
						// programinfoManager.update(programInfo);
					}
				} else {
					String str = "节目状态不符合规则，不能生成迁移任务单。";
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.warn(str);

					// 0 - 迁移
					// 1 - 迁移失败
					// 2 - 新入库
					// 3 - 已包装
					// programInfo.setDsflag((long)1);
					// programinfoManager.update(programInfo);
				}
			}
		} catch (Exception e) {
			String str = "Cms -> MigrationServiceImpl -> generateMigrationForProgram - 异常："
					+ e.getMessage();
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.error(str);
		}

		cmsLog.info("Cms -> MigrationServiceImpl -> generateMigrationForProgram returns.");
		return cmsResultDto;
	}
	
	// 20100312 13:17
	// 节目录入时，迁移反馈
	public CmsResultDto finishMigrationForProgram(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
	) {
		cmsLog.info("Cms -> MigrationServiceImpl -> finishMigrationForProgram...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件，获取
		// 节目包状态，0-导入 1-缓存库 2-加扰库 3-播控库
		String stclasscodeNearOnline = "NearOnline";		// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline";			// 加扰库存储体等级code
		String stclasscodeOnline = "Online";				// 播控库存储体等级code
		
		if (result.equalsIgnoreCase("Y")) {
			// 迁移成功
			// 新增记录，到文件存放位置表
			// 解析xml
			cmsLog.info("迁移成功，准备修改节目状态和新增文件位置表记录...");
			cmsLog.info("解析迁移反馈xml...");
			List list = getAmsStoragePrgRelFromXml(transferEntity);
			if (list != null && list.size() >= 2) {
				cmsLog.info("解析迁移反馈xml成功，准备新增文件位置表记录...");
				AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) list
						.get(0);
				// String proglistId = (String)list.get(1); // 栏目单日期

				cmsLog.info("节目文件的文件位置表记录的Filename字段需要修改...");
				ProgramFiles programFiles = (ProgramFiles) programFilesManager
						.getById(amsStoragePrgRel.getProgfileid());
				amsStoragePrgRel.setFilename(programFiles.getFilename());

				ProgramInfo programinfo = (ProgramInfo) programinfoManager
						.getById(amsStoragePrgRel.getPrglobalid());

				if (programinfo != null) {
					cmsLog.info("节目ID：" + programinfo.getProgramid());

					// 修改节目文件表的filename

					// 新增文件位置表记录
					amsstorageprgrelManager.save(amsStoragePrgRel);
					cmsLog.info("文件位置表记录已经添加，文件位置ID："
							+ amsStoragePrgRel.getRelid());

					// 0 - 迁移
					// 1 - 迁移失败
					// 2 - 新入库
					// 3 - 已包装
					programinfo.setDsflag((long) 2);
					programinfoManager.update(programinfo);
					cmsLog.info("节目状态已经更新，状态：新入库(2)。");
				} else {
					String str = "节目记录不存在，节目ID："
							+ amsStoragePrgRel.getPrglobalid();
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.warn(str);
				}
			} else {
				String str = "解析返回xml失败。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		} else if (result.equalsIgnoreCase("N")) {
			// 迁移失败
			// 查询节目包的栏目单详细，修改处理状态为“失败”，处理状态(0未处理1处理8失败9成功)
			cmsLog.info("迁移失败，准备修改节目状态...");
			List list = getAmsStoragePrgRelFromXml(transferEntity);
			if (list != null && list.size() >= 2) {
				AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) list
						.get(0);
				// String proglistId = (String)list.get(1); // 栏目单日期

				ProgramInfo programinfo = (ProgramInfo) programinfoManager
						.getById(amsStoragePrgRel.getPrglobalid());
				if (programinfo != null) {
					cmsLog.info("节目ID：" + programinfo.getProgramid());

					// 0 - 迁移
					// 1 - 迁移失败
					// 2 - 新入库
					// 3 - 已包装
					programinfo.setDsflag((long) 1);
					programinfoManager.update(programinfo);
					cmsLog.info("节目状态已经更新，状态：迁移失败(1)。");
				} else {
					String str = "节目记录不存在，节目ID："
							+ amsStoragePrgRel.getPrglobalid();
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.warn(str);
				}
			} else {
				String str = "解析返回xml失败。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		} else {
			String str = "未知输入结果。result：" + result;
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}

		cmsLog.info("Cms -> MigrationServiceImpl -> finishMigrationForProgram returns.");
		return cmsResultDto;
	}

	/**
	 * HuangBo addition by 2010年8月30日 16时34分
	 * @param progPackageId 节目包ID集合
	 * @param inputManId 操作人员ID
	 * @throws MalformedURLException 
	 * @throws SmbException 
	 */
	public void migration2Online(List<String> progPackageId, String inputManId)
			throws MalformedURLException, SmbException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 配置文件，获取
		String scheduledate = convertDateToScheduleDate(simpleDateFormat.format(new Date()));
		String stclasscodeOnline = "Online";			// 一级库存储体等级code
		String stclasscodeNearOnline = "NearOnline";	// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline";		// 缓存库存储体等级code
		String stclasscodeMigration = "Migration";		// 迁移单的目标存储体等级code
		String filecodeMigration = "MIGXML";			// 迁移单xml的filecode
		
		String strXml = "";								// 迁移单内容
		String destpathMigration = "";					// 迁移单的目标路径
		String xmlFilename = "";						// 迁移单的文件名字
		
		// xml内容字段
		String proglistId = scheduledate;				// 编单日期，总表主键
		String requestId = "";							// 当前时间，不重复
		String createDate = "";
		String priorityDate = "";
		/**
		 * 0 - 数据导出@上海
		 * 1 - 迁移至播发库@北京
		 * 2 - 数据导入@北京
		 * 3 - 节目录入@上海
		 */
		String type = "1";					
		
		// 赋值
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		requestId = df.format(new Date());
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		createDate = df.format(new Date());
		priorityDate = createDate;
		xmlFilename = proglistId + requestId + ".xml";
		
		// 3 - 调用方法2，根据配置文件stclasscode、配置文件filecode，获得迁移单xml目标路径
		// 返回：List
		// 1 - String 目标路径()  格式："smb://hc:hc@172.23.19.66/公用/"
		// 2 - List<Object[]>
		//			(AmsStorage)Object[0]
		//			(AmsStorageDir)Object[1]
		//			(AmsStorageClass)Object[2]
		List xmldestpaths = packageFilesManager.getDestPathByFilecodeStclasscode(
				filecodeMigration, 					// 迁移单xml的filecode
				stclasscodeMigration				// 迁移单的目标存储体等级code
				);
		// FileOperationImpl fileoper = new FileOperationImpl();
		if (xmldestpaths != null && xmldestpaths.size() >= 2) {
			destpathMigration = (String) xmldestpaths.get(0);
			if (destpathMigration == null
					|| destpathMigration.equalsIgnoreCase("")) {
				cmsLog.debug("迁移单目标路径不存在。");
			} else {
				destpathMigration = fileoper.checkPathFormatRear(
						destpathMigration, '/');
				cmsLog.debug("得到迁移单的目标路径。" + destpathMigration);
			}
		} else {
			cmsLog.debug("迁移单目标路径不存在。");
		}

		strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		strXml += "<Migration>";
		strXml += "<Distribution ProglistId=\"";
		strXml += proglistId;
		strXml += "\" RequestId=\"";
		strXml += requestId;
		strXml += "\" CreateDate=\"";
		strXml += createDate;
		strXml += "\" Type=\"";
		strXml += type;
		strXml += "\">";

		/**
		 * 获得所有节目包
		 */
		List<ProgPackage> progPackages = 
				this.progPackageManager.queryByIDs(progPackageId);
		
		for (ProgPackage progPackage : progPackages) {
			// 判断栏目单详细，节目包的（迁移）处理状态，处理状态(0未处理1处理8失败9成功)
			Long dealState = progPackage.getDealstate();
			if (dealState == null || (dealState != 0 && dealState != 8)) {
				cmsLog.debug("栏目单详细的处理状态不是“未处理”或“失败”，不加入迁移单。节目包ID："
						+ progPackage.getProductid());
				continue;
			}

			// 2 - 判断节目包的ProgType是否="V"
			if (progPackage.getProgtype().equalsIgnoreCase("V")) {
				// 2.2 - 如果="V"，继续
				// 2.2.1 - 调用方法5，根据节目包id 和
				// 主文件表示(progrank)，查询packagefiles、programfiles表，得到节目包的文件（主文件）
				// 返回： List<ProgramFiles>
				List programFileses = packageFilesManager
						.getProgramFilesesByProductidProgrank(
								progPackage.getProductid(), (long) 1 // 主文件标识，0
																		// - 不是;
																		// 1 - 是
						);
				if (programFileses == null || programFileses.size() == 0) {
					cmsLog.debug("节目包下无文件。节目包ID：" + progPackage.getProductid());
					continue;
				}
				for (int j = 0; j < programFileses.size(); j++) {
					ProgramFiles programFiles = (ProgramFiles) programFileses
							.get(j);

					// 2.2.2 - 调用方法11，根据节目包ID、文件ID、一级库classcode，获得每个文件的在一级库的路径
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStoragePrgRel)Object[1]
					// (AmsStorageDir)Object[2]
					// (AmsStorageClass)Object[3]
					List onlinepaths = packageFilesManager
							.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									programFiles.getProgfileid(),
									stclasscodeOnline, // 一级库存储体等级code，从配置文件获取
									progPackage.getProductid());
					// 2.3 - 判断文件是否已经存在于一级库了，
					if (onlinepaths != null && onlinepaths.size() >= 2) {
						String destpath = (String) onlinepaths.get(0);
						if (destpath != null && !destpath.equalsIgnoreCase("")) {
							// 2.3.1 - 如果存在，
							// 判断，不处理
							cmsLog.debug("节目包下的文件已经存在于一级库。文件ID："
									+ programFiles.getProgfileid());
							cmsLog.debug("节目包下的文件在一级库的位置：" + destpath);
							cmsTransactionManager
									.updateRefreshState1OfProgPackage(
											packageFilesManager,
											progListDetailManager,
											progPackageManager,
											stclasscodeNearOnline,
											stclasscodeCaOnline,
											stclasscodeOnline, "", progPackage,
											1);
							continue;
						}
					}
					// 2.3.2 - 如果不存在，继续
					// 调用方法11，根据节目包ID、文件ID、缓存库classcode，获得每个文件的在缓存库的路径
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStoragePrgRel)Object[1]
					// (AmsStorageDir)Object[2]
					// (AmsStorageClass)Object[3]
					String sourcepath = ""; // 节目包主文件的源路径
					List sourcepaths = packageFilesManager
							.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									programFiles.getProgfileid(),
									stclasscodeCaOnline, // 缓存库存储体等级code，从配置文件获取
									progPackage.getProductid());
					if (sourcepaths != null && sourcepaths.size() >= 2) {
						sourcepath = (String) sourcepaths.get(0);
						List list = (List) sourcepaths.get(1);
						Object[] sourcerows = (Object[]) list.get(0);
						AmsStorage sourceamsst = (AmsStorage) sourcerows[0];
						AmsStoragePrgRel sourceamsstpr = (AmsStoragePrgRel) sourcerows[1];
						AmsStorageDir sourceamsstd = (AmsStorageDir) sourcerows[2];
						AmsStorageClass sourceamsstc = (AmsStorageClass) sourcerows[3];

						Date dfiledate = sourceamsstpr.getFiledate();
						String filedate = fileoper.convertDateToString(
								dfiledate, "yyyy-MM-dd HH:mm:ss");

						// 2.4 -
						// 调用方法2，根据stdir.filecode、配置文件一级库stclasscode，获取节目包主文件迁移的目标路径
						// 返回：List
						// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
						// 2 - List<Object[]>
						// (AmsStorage)Object[0]
						// (AmsStorageDir)Object[1]
						// (AmsStorageClass)Object[2]
						List destpaths = packageFilesManager
								.getDestPathByFilecodeStclasscode(
										sourceamsstd.getFilecode(), // 节目包主文件的filecode
										stclasscodeOnline // 一级库存储体等级code，从配置文件获取
								);
						if (destpaths != null && destpaths.size() >= 2) {
							String destpath = (String) destpaths.get(0);
							List list1 = (List) destpaths.get(1);
							Object[] destrows = (Object[]) list1.get(0);
							AmsStorage destamsst = (AmsStorage) destrows[0];
							AmsStorageDir destamsstd = (AmsStorageDir) destrows[1];
							AmsStorageClass destamsstc = (AmsStorageClass) destrows[2];

							if (destpath == null
									|| destpath.equalsIgnoreCase("")) {
								cmsLog.debug("节目包下的主文件迁移到一级库的目标路径不存在。文件ID："
										+ programFiles.getProgfileid());
								continue;
							} else {
								// 处理destpath，加上filepath和文件名
								destpath = destpath.replace('\\', '/');
								destpath = fileoper.checkPathFormatRear(
										destpath, '/');
								if (sourceamsstpr.getFilepath() != null
										&& !sourceamsstpr.getFilepath()
												.equalsIgnoreCase("")) {
									destpath += sourceamsstpr.getFilepath();
								}
								destpath = destpath.replace('\\', '/');
								destpath = fileoper.checkPathFormatRear(
										destpath, '/');
								destpath += sourceamsstpr.getFilename();

								// 处理路径格式，去掉头
								// smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								// /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								sourcepath = sourcepath.substring(
										sourcepath.indexOf("/", 6),
										sourcepath.length());
								destpath = destpath.substring(
										destpath.indexOf("/", 6),
										destpath.length());

								// 2.5 - 把节目文件加入迁移单中
								strXml += "<Transfer-Entity SourceStorageClass=\"";
								strXml += sourceamsstc.getStclasscode();
								strXml += "\" SourceFileType=\"";
								strXml += programFiles.getFiletypeid();
								strXml += "\" DesStorageClass=\"";
								strXml += destamsstc.getStclasscode();
								strXml += "\" PriorityDate=\"";
								strXml += priorityDate;
								strXml += "\" SourceProgTitle=\"";
								strXml += progPackage.getProductname();
								strXml += "\" SourceFileName=\"";
								strXml += sourceamsstpr.getFilename();
								strXml += "\" SourceFileId=\"";
								strXml += sourceamsstpr.getProgfileid();
								strXml += "\" SourceProgid=\"";
								strXml += progPackage.getProductid();
								strXml += "\" IsCover=\"";
								strXml += "N";
								strXml += "\">";

								// 源文件
								strXml += "<Source Type=\"";
								strXml += sourceamsst.getStorageaccstype();
								strXml += "\" SourceDirId=\"";
								strXml += sourceamsstd.getStdirglobalid();
								strXml += "\" Hostname=\"";
								strXml += sourceamsst.getStorageip();
								strXml += "\" Port=\"";
								strXml += "";
								strXml += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									strXml += sourceamsst.getLoginname();
								strXml += "\" SourcestorageId=\"";
								strXml += sourceamsst.getStglobalid();
								strXml += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									strXml += sourceamsst.getLoginpwd();
								strXml += "\" FileDate=\"";
								strXml += filedate;
								strXml += "\" VariableFilePath=\"";
								strXml += sourceamsstpr.getFilepath();
								strXml += "\">";
								strXml += "<File>";
								strXml += sourcepath;
								strXml += "</File>";
								strXml += "</Source>";

								// 目标文件
								strXml += "<Destination Type=\"";
								strXml += destamsst.getStorageaccstype();
								strXml += "\" DesStorageDirId=\"";
								strXml += destamsstd.getStdirglobalid();
								strXml += "\" Hostname=\"";
								strXml += destamsst.getStorageip();
								strXml += "\" Port=\"";
								strXml += "";
								strXml += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									strXml += destamsst.getLoginname();
								strXml += "\" DesStorageId=\"";
								strXml += destamsst.getStglobalid();
								strXml += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									strXml += destamsst.getLoginpwd();
								strXml += "\" FileDate=\"";
								strXml += filedate;
								strXml += "\" VariableFilePath=\"";
								strXml += sourceamsstpr.getFilepath();
								strXml += "\">";
								strXml += "<File>";
								strXml += destpath; // 没有文件名，需要处理
								strXml += "</File>";
								strXml += "</Destination>";

								strXml += "</Transfer-Entity>";
							}
						} else {
							cmsLog.debug("节目包下的主文件迁移到一级库的目标路径不存在。文件ID："
									+ programFiles.getProgfileid());
						}
					} else {
						cmsLog.debug("节目包下的主文件在缓存库的源路径不存在。文件ID："
								+ programFiles.getProgfileid());
						continue;
					}
				}
			} else {
				// 2.1 - 如果不="V"，不处理
				// 20100205 01:24 北京修改
				// 如果是富媒体节目包，把节目包的state改为一级库
				cmsLog.debug("节目包是富媒体。节目包ID：" + progPackage.getProductid());
				/**
				 * HuangBo update by 2010年8月25日 13时37分
				 * 为富媒体文件目录生成迁移单
				 */
				List<ProgramFiles> programFiles = packageFilesManager
						.getProgramFilesesByProductidProgrank(
						progPackage.getProductid(), 1l // 主文件标识，0 - 不是; 1 - 是
				);
				
				for (ProgramFiles programFile : programFiles) {
					// 2.2.2 - 调用方法11，根据节目包ID、文件ID、一级库classcode，获得每个文件的在一级库的路径
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStoragePrgRel)Object[1]
					// (AmsStorageDir)Object[2]
					// (AmsStorageClass)Object[3]
					List onlinepaths = packageFilesManager
							.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									programFile.getProgfileid(),
									stclasscodeOnline, // 一级库存储体等级code，从配置文件获取
									progPackage.getProductid());
					// 2.3 - 判断文件是否已经存在于一级库了，
					if (onlinepaths != null && onlinepaths.size() >= 2) {
						String destpath = (String) onlinepaths.get(0);
						if (destpath != null && !destpath.equalsIgnoreCase("")) {
							// 2.3.1 - 如果存在，
							// 判断，不处理
							cmsLog.debug("节目包下的文件已经存在于一级库。文件ID："
									+ programFile.getProgfileid());
							cmsLog.debug("节目包下的文件在一级库的位置：" + destpath);
							cmsTransactionManager
									.updateRefreshState1OfProgPackage(
											packageFilesManager,
											progListDetailManager,
											progPackageManager,
											stclasscodeNearOnline,
											stclasscodeCaOnline,
											stclasscodeOnline, "", progPackage,
											1);
							continue;
						}
					}
					
					// 2.3.2 - 如果不存在，继续
					// 调用方法11，根据节目包ID、文件ID、缓存库classcode，获得每个文件的在缓存库的路径
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStoragePrgRel)Object[1]
					// (AmsStorageDir)Object[2]
					// (AmsStorageClass)Object[3]
					String sourcepath = ""; // 节目包主文件的源路径
					List sourcepaths = packageFilesManager
							.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									programFile.getProgfileid(),
									stclasscodeCaOnline, // 缓存库存储体等级code，从配置文件获取
									progPackage.getProductid());
					
					if (sourcepaths != null && sourcepaths.size() >= 2) {
						sourcepath = (String) sourcepaths.get(0);
						List list = (List) sourcepaths.get(1);
						Object[] sourcerows = (Object[]) list.get(0);
						AmsStorage sourceamsst = (AmsStorage) sourcerows[0];
						AmsStoragePrgRel sourceamsstpr = (AmsStoragePrgRel) sourcerows[1];
						AmsStorageDir sourceamsstd = (AmsStorageDir) sourcerows[2];
						AmsStorageClass sourceamsstc = (AmsStorageClass) sourcerows[3];

						Date dfiledate = sourceamsstpr.getFiledate();
						String filedate = fileoper.convertDateToString(
								dfiledate, "yyyy-MM-dd HH:mm:ss");

						// 2.4 -
						// 调用方法2，根据stdir.filecode、配置文件一级库stclasscode，获取节目包主文件迁移的目标路径
						// 返回：List
						// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
						// 2 - List<Object[]>
						// (AmsStorage)Object[0]
						// (AmsStorageDir)Object[1]
						// (AmsStorageClass)Object[2]
						/**
						 * HuangBo update by 2010年8月25日 14时53分
						 * 原来是通过源的等级code, 由于之前未涉及到富媒体. 故fileCode参数可能不正确.
						 * 现在为了简单, 直接写死在这. 为了查出播出库, 富媒体的迁移存放路径
						 */
						List destpaths = packageFilesManager
								.getDestPathByFilecodeStclasscode(
										"RMZIP", // 节目包主文件的filecode
										stclasscodeOnline // 一级库存储体等级code，从配置文件获取
								);
						if (destpaths != null && destpaths.size() >= 2) {
							String destpath = (String) destpaths.get(0);
							List list1 = (List) destpaths.get(1);
							Object[] destrows = (Object[]) list1.get(0);
							AmsStorage destamsst = (AmsStorage) destrows[0];
							AmsStorageDir destamsstd = (AmsStorageDir) destrows[1];
							AmsStorageClass destamsstc = (AmsStorageClass) destrows[2];

							if (destpath == null
									|| destpath.equalsIgnoreCase("")) {
								cmsLog.debug("节目包下的主文件迁移到一级库的目标路径不存在。文件ID："
										+ programFile.getProgfileid());
								continue;
							} else {
								// 处理destpath，加上filepath和文件名
								destpath = destpath.replace('\\', '/');
								destpath = fileoper.checkPathFormatRear(
										destpath, '/');
								
								//TODO 清空指定栏目的报纸
								if(13 == progPackage.getStyleid()) {
									destpath += new CmsConfig().getPropertyByName("njswPaperPath");
								} else if(8 == progPackage.getStyleid()) {
									destpath += new CmsConfig().getPropertyByName("yxPaperPath");
								}
								
								/**
								 * HuangBo addition by 2010年8月31日 15时49分
								 * 生成迁移单前, 清空一库下的报纸目录...
								 */
								SmbFile smbFile = new SmbFile(destpath);
								SmbFile[] childs = smbFile.listFiles();
								for (SmbFile smbFile2 : childs) {
									smbFile2.delete();
								}

								destpath += sourceamsstpr.getFilename();

								// 处理路径格式，去掉头
								// smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								// /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
								sourcepath = sourcepath.substring(
										sourcepath.indexOf("/", 6),
										sourcepath.length());
								destpath = destpath.substring(
										destpath.indexOf("/", 6),
										destpath.length());

								// 2.5 - 把节目文件加入迁移单中
								strXml += "<Transfer-Entity SourceStorageClass=\"";
								strXml += sourceamsstc.getStclasscode();
								strXml += "\" SourceFileType=\"";
								strXml += programFile.getFiletypeid();
								strXml += "\" DesStorageClass=\"";
								strXml += destamsstc.getStclasscode();
								strXml += "\" PriorityDate=\"";
								strXml += priorityDate;
								strXml += "\" SourceProgTitle=\"";
								strXml += progPackage.getProductname();
								strXml += "\" SourceFileName=\"";
								strXml += sourceamsstpr.getFilename();
								strXml += "\" SourceFileId=\"";
								strXml += sourceamsstpr.getProgfileid();
								strXml += "\" SourceProgid=\"";
								strXml += progPackage.getProductid();
								strXml += "\" IsCover=\"";
								strXml += "N";
								strXml += "\">";

								// 源文件
								strXml += "<Source Type=\"";
								strXml += sourceamsst.getStorageaccstype();
								strXml += "\" SourceDirId=\"";
								strXml += sourceamsstd.getStdirglobalid();
								strXml += "\" Hostname=\"";
								strXml += sourceamsst.getStorageip();
								strXml += "\" Port=\"";
								strXml += "";
								strXml += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									strXml += sourceamsst.getLoginname();
								strXml += "\" SourcestorageId=\"";
								strXml += sourceamsst.getStglobalid();
								strXml += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									strXml += sourceamsst.getLoginpwd();
								strXml += "\" FileDate=\"";
								strXml += filedate;
								strXml += "\" VariableFilePath=\"";
								strXml += sourceamsstpr.getFilepath();
								strXml += "\">";
								strXml += "<Folder>";
								strXml += sourcepath;
								strXml += "</Folder>";
								strXml += "</Source>";

								// 目标文件
								strXml += "<Destination Type=\"";
								strXml += destamsst.getStorageaccstype();
								strXml += "\" DesStorageDirId=\"";
								strXml += destamsstd.getStdirglobalid();
								strXml += "\" Hostname=\"";
								strXml += destamsst.getStorageip();
								strXml += "\" Port=\"";
								strXml += "";
								strXml += "\" Username=\"";
								if (sourceamsst.getLoginname() != null)
									strXml += destamsst.getLoginname();
								strXml += "\" DesStorageId=\"";
								strXml += destamsst.getStglobalid();
								strXml += "\" Password=\"";
								if (sourceamsst.getLoginpwd() != null)
									strXml += destamsst.getLoginpwd();
								strXml += "\" FileDate=\"";
								strXml += filedate;
								strXml += "\" VariableFilePath=\"";
//								strXml += sourceamsstpr.getFilepath();
								strXml += "\">";
								strXml += "<Folder>";
								strXml += destpath; // 没有文件名，需要处理
								strXml += "</Folder>";
								strXml += "</Destination>";

								strXml += "</Transfer-Entity>";
							}
						} else {
							cmsLog.debug("节目包下的主文件迁移到一级库的目标路径不存在。文件ID："
									+ programFile.getProgfileid());
						}
					} else {
						cmsLog.debug("节目包下的主文件在缓存库的源路径不存在。文件ID："
								+ programFile.getProgfileid());
						continue;
					}
				}
			}
		}
		
		strXml += "</Distribution>";
		strXml += "</Migration>";

		// 3 - 调用方法2，根据配置文件stclasscode、配置文件filecode，获得迁移单xml目标路径
		// 见上

		// 4 - 调用方法9，生成xml到指定位置
		if (fileoper.createSmbFile(destpathMigration + xmlFilename, strXml) == 0) {
			// 5 - 调用方法8(还是方法12??)，根据编单日期、文件类型8，生成xml对应的发布文件表记录
			// 同时需要修改
			ProgListFile progListFile = new ProgListFile();
			progListFile.setScheduledate(scheduledate);
			progListFile.setFilename(xmlFilename);
			progListFile.setFiletype((long) 8); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
			progListFile.setInputtime(new Date());
			progListFile.setInputmanid(inputManId);
			progListFile.setState1((long) 0);
			progListFile.setState2((long) 0);
			progListFile.setDate1(new Date());
			progListFile.setColumnxml(strXml);

			CmsResultDto cmsResultDto = cmsTransactionManager.saveProgListFile(
					progListFileManager, progListFile, null, null);

			if (cmsResultDto.getResultCode() != 0) {
				fileoper.deleteSmbFile(destpathMigration + xmlFilename);
			}

			cmsLog.debug("这里没有使用事务处理，有待优化。");
			for (ProgPackage progPackage : progPackages) {
				// 如果节目包状态为加扰，处理状态为未处理，修改
				Long state = progPackage.getState();
				Long dealState = progPackage.getDealstate();
				if (state == 2) {
					/**
					 * HuangBo update by 2010年8月25日 15时2分
					 * 迁移单生成成功后, 富媒体也需要修改节目包状态. 
					 * 而不是只有视频类节目包才需要修改节目包状态为处理中..
					 * 之前由于富媒体文件由CMS直接COPY到一级库, 现在改为由迁移模块迁移富媒体目录
					 */
					if (dealState == 0 || dealState == 8) {
						progPackage.setDealstate((long) 1); // 处理状态(0未处理1处理8失败9成功)
					}
				}

				progPackageManager.update(progPackage);
				cmsLog.debug("节目包ID为：" + progPackage.getProductid());
				cmsLog.debug("节目包的处理状态修改为：" + progPackage.getDealstate());
			}
		} else {
			cmsLog.debug("生成迁移单xml文件失败。目标路径：" + destpathMigration + xmlFilename);
		}
		cmsLog.debug("Cms -> MigrationServiceImpl -> generateMigration returns.");
	}

	/**
	 * 新写的迁移, 从导入区->缓存库
	 * @param amsstorageprgrelManager
	 * @param programinfoManager
	 * @param programFilesManager
	 * @param packageFilesManager
	 * @param progPackageManager
	 * @param transferEntity
	 * @param result
	 * @param resultDes
	 * @return
	 */
	public CmsResultDto updateFinishMigrationForProgramInfo(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
	) {
		return this.migrationModuleManager.updateFinishMigrationForProgramInfo(
				transferEntity, result, resultDes);
	}

	/**
	 * 批量下迁移任务到一级库, 根据编单日期下迁移任务
	 * @param dateStr 编单日期
	 * @param inputManId 操作人员ID
	 * @return 如果没有异常返回null, 如果有异常则返回异常信息
	 */
	public String migrationToOnline(String dateStr, String inputManId) {
		String scheduleDate = dateStr.replaceAll("-", "") + "000000";
		List<PortalColumn> portalColumns = this.portalColumnManager
				.queryPortalColumnsByScheduleDate(scheduleDate);
		CmsResultDto cmsResultDto = this.migrationModuleManager
				.saveMigrationFromCaonlineToOnline_123(dateStr, portalColumns, inputManId);
		if (0 == cmsResultDto.getResultCode()) {
			return null;
		} else {
			return cmsResultDto.getErrorMessage();
		}
	}
	
	/**
	 * 以节目包为单位下迁移任务到一级库
	 * @param dateStr 编单日期
	 * @param inputManId 操作人员ID
	 * @return 如果没有异常返回null, 如果有异常则返回异常信息
	 */
	public String migrationToOnlineUnit(List<String> productInfoIds, String inputManId) {
		List<ProgPackage> progPackages = new ArrayList<ProgPackage>();
		if (productInfoIds.contains(null)) {
			return "您所选的节目包中存在未绑定包月产品的节目包!";
		}
		
		for (String string : productInfoIds) {
			TProductInfo productInfo = this.productInfoManager.getTProductInfoById(string);
			ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(
					productInfo.getProgPackageId());
			String errMessage = "节目包 [ " + progPackage.getProductname() + " ] ";
			if (1 != progPackage.getState()) {
				errMessage += "附属文件不是缓存库, 不允许迁移!";
				cmsLog.info(errMessage);
				return errMessage;
			} else if (9 > productInfo.getEncryptState()) {
				errMessage += "主文件未加扰完成, 不允许迁移!";
				cmsLog.info(errMessage);
				return errMessage;
			} else if (18 < productInfo.getEncryptState()
					&& 3 == progPackage.getState()) {
				errMessage += "主文件和附属文件已存在于播发库, 无需迁移!";
				cmsLog.info(errMessage);
				return errMessage;
			}
			
			progPackages.add(progPackage);
		}
		
		CmsResultDto cmsResultDto = this.migrationModuleManager
				.saveMigrationFromCaonlineToOnlineByProgpackages_123(
						null, progPackages, productInfoIds, inputManId);
		if (0 == cmsResultDto.getResultCode()) {
			return "生成迁移任务成功!";
		} else {
			return cmsResultDto.getErrorMessage();
		}
	}
	
	public void test(String dateStr, String progPackageId, String inputManId) {
		List<String> progPackageIds = new ArrayList<String>();
		progPackageIds.add(progPackageId);
		System.out.println(this.migrationToOnlineUnit(progPackageIds, inputManId));
	}
}
