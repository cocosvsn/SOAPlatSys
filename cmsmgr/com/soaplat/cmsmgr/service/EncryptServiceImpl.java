package com.soaplat.cmsmgr.service;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.manageriface.IAmsStorageClassManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.amsmgr.serviceimpl.AmsStoragePrgRelService;
import com.soaplat.cmsmgr.bean.EncryptList;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.common.UnZip1Impl;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.dto.EncryptProgVo;
import com.soaplat.cmsmgr.manageriface.ICmsTransactionManager;
import com.soaplat.cmsmgr.manageriface.IEncryptListManager;
import com.soaplat.cmsmgr.manageriface.IMigrationModuleManager;
import com.soaplat.cmsmgr.manageriface.IPackStyleFileTypeManager;
import com.soaplat.cmsmgr.manageriface.IPackStyleManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListFileManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.cmsmgr.util.DateUtil;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class EncryptServiceImpl implements EncryptServiceIface {
	private final String KEYFILECODE = "KEY";
	private final String ENCRYPTSTORAGECLASSCODE = "CaOnline";
	private final String NEARONLINESTORAGECLASSCODE = "NearOnline";
	private final String ONLINESTORAGECLASSCODE = "Online";
	private final List<String> encryptCode;
	private CmsConfig cmsConfig;
	// 生成预加扰任务
	private static FileOperationImpl fileopr = null;
	
	private IEncryptListManager encryptListManager = null;
	private IPackStyleManager packStyleManager = null;
	private IProgPackageManager progPackageManager = null;
	private IPackageFilesManager packageFilesManager = null;
	private IProgramFilesManager programFilesManager = null;
	private IProgListDetailManager progListDetailManager = null;
	private IPackStyleFileTypeManager packStyleFileTypeManager = null;
	private IAmsStoragePrgRelManager amsstorageprgrelManager = null;
	private IAmsStorageDirManager amsstoragedirManager = null;
	private IAmsStorageManager amsstorageManager = null;
	private IProductInfoManager productInfoManager;
	private IProgListFileManager progListFileManager;
	private IMigrationModuleManager migrationModuleManager;
	private IAmsStorageClassManager amsstorageclassManager = null;
	
	private ICmsTransactionManager cmsTransactionManager = null;		// 事务管理器
	public static final Logger cmsLog = Logger.getLogger("Cms");

	public EncryptServiceImpl()
	{
		this.cmsConfig = new CmsConfig();
		fileopr = new FileOperationImpl();
		
		encryptListManager = (IEncryptListManager)ApplicationContextHolder.webApplicationContext.getBean("encryptListManager");
		packStyleManager = (IPackStyleManager)ApplicationContextHolder.webApplicationContext.getBean("packStyleManager");
		progPackageManager = (IProgPackageManager)ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
		packageFilesManager = (IPackageFilesManager)ApplicationContextHolder.webApplicationContext.getBean("packageFilesManager");
		programFilesManager = (IProgramFilesManager)ApplicationContextHolder.webApplicationContext.getBean("programFilesManager");
		progListDetailManager = (IProgListDetailManager)ApplicationContextHolder.webApplicationContext.getBean("progListDetailManager");
		packStyleFileTypeManager = (IPackStyleFileTypeManager)ApplicationContextHolder.webApplicationContext.getBean("packStyleFileTypeManager");
		amsstorageprgrelManager = (IAmsStoragePrgRelManager)ApplicationContextHolder.webApplicationContext.getBean("amsstorageprgrelManager");
		amsstoragedirManager = (IAmsStorageDirManager)ApplicationContextHolder.webApplicationContext.getBean("amsstoragedirManager");
		amsstorageManager = (IAmsStorageManager)ApplicationContextHolder.webApplicationContext.getBean("amsstorageManager");
		amsstorageclassManager = (IAmsStorageClassManager)ApplicationContextHolder.webApplicationContext.getBean("amsstorageclassManager");
		this.productInfoManager = (IProductInfoManager)ApplicationContextHolder.webApplicationContext.getBean("productinfoManager");
		this.progListFileManager = (IProgListFileManager)ApplicationContextHolder.webApplicationContext.getBean("progListFileManager");
		cmsTransactionManager = (ICmsTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("cmsTransactionManager");
		this.migrationModuleManager = (IMigrationModuleManager) ApplicationContextHolder.webApplicationContext.getBean("migrationModuleManager");
		encryptCode = Arrays.asList(
				this.cmsConfig.getPropertyByName("UnEncryptCode").split(","));
	}
	
	
	// 生成加扰任务，根据永新视博的加扰任务格式
	private String generateEncryptTaskXml(
			ProgPackage progPackage, 				// 节目包，包含：ID、名称
			String destroot,						// 根据配置文件的目标存储ID，去查询存储体的映射盘符
			String keyids, 							// 在CA中的产品ID列表
			String expireddate,						// 到期日期，格式2009-12-31，从配置文件获得
			Long encrypttype,						// 加扰类别，富媒体/视频，folder/file
			String groupName,						// group的路径，默认规则，根据节目包ID前10位\节目包ID
			List programfileses, 					// 节目包的文件列表，包含：文件名、
			List programfileNames,					// 节目包文件名，包含后缀
			List encrypts,							// 是否加扰，如果文件是主文件且产品ID列表不为空，则加扰
			List locations							// folder/file的源路径，映射盘符由存储体表中映射盘符取值。"G:\PPVP201001\PPVP20100106162355000906节目包ID\PPVP20100106162355000906,ZIP文件ID"
			)
	{
		// 使用字符串生成
		String strxml;

		// 配置文件
		String ippvid = "0";
		String watchtimes = "0";
		String watchduration = "0";
		String watchtraffic = "0";
		// expireddate = "2010-05-01";

		strxml = "<?xml version=\"1.0\" encoding=\"gb2312\" standalone=\"yes\"?>\r\n";
		strxml += "<namespace name=\"PushVOD\" version=\"1.0\" type=\"content\">\r\n";

		strxml += "<content id=\"";
		// 北京修改，20100204 15:13
		// 节目ID，取值范围1-0xffffffff, 节目编排系统必须保证其唯一性。
		// 取当前时间的秒数的后8位，作为节目id
		// strxml += progPackage.getProductid();
		Date nowDate = new Date();
		String contentid = String.valueOf(nowDate.getTime() / 1000);
		contentid = contentid.substring(contentid.length() - 8,
				contentid.length());
		strxml += contentid;
		// 修改完毕
		strxml += "\" name=\"";
		// 北京修改，20100204 15:07
		// 把name内容去掉，赋值为""，因为加扰时会创建一个以name命名的目录
		// strxml += progPackage.getProductname();
		// 修改完毕
		strxml += "\" destroot=\"";
		strxml += destroot;
		strxml += "\" >\r\n";

		strxml += "<casystem systemid=\"0x4A02\" >\r\n";
		strxml += "<cdcadesc securityflag=\"0\" keyid =\"";
		if (keyids == null || keyids.equalsIgnoreCase("")
				|| keyids.equalsIgnoreCase("0")) {
			strxml += "1";
			cmsLog.warn("keyid为空，赋值“1”。");
		} else {
			strxml += keyids;
		}
		strxml += "\" ippvid=\"";
		strxml += ippvid;
		strxml += "\" slotid=\"1\" price=\"50\"/>\r\n";
		strxml += "</casystem>\r\n";

		strxml += "<rightdesc expireddate=\"";
		strxml += expireddate;
		strxml += "\" watchtimes=\"";
		strxml += watchtimes;
		strxml += "\" watchduration=\"";
		strxml += watchduration;
		strxml += "\" watchtraffic=\"";
		strxml += watchtraffic;
		strxml += "\"/>\r\n";

		if (encrypttype == 1) {
			// 富媒体节目包
			strxml += "<group name=\"";
			strxml += groupName;
			strxml += "\" >\r\n";
			for (int i = 0; i < programfileses.size(); i++) {
				ProgramFiles pf = (ProgramFiles) programfileses.get(i);
				if (pf.getProgrank() == 1) {
					strxml += "<folder name=\"";
					strxml += pf.getProgfileid(); // 应该是ZIP文件的ID
					strxml += "\" encrypt=\"";
					strxml += (String) encrypts.get(i);
					strxml += "\" location=\"";
					strxml += (String) locations.get(i);
					strxml += "\" />\r\n";
				} else {
					strxml += "<file name=\"";
					strxml += (String) programfileNames.get(i);
					strxml += "\" encrypt=\"";
					strxml += (String) encrypts.get(i);
					strxml += "\" location=\"";
					strxml += (String) locations.get(i);
					strxml += "\" />\r\n";
				}
			}
			strxml += "</group>\r\n";
		} else if (encrypttype == 0) {
			// 视频节目包
			strxml += "<group name=\"";
			strxml += groupName;
			strxml += "\" keyfiledestpath=\"";
			strxml += groupName;
			strxml += "\">\r\n";
			for (int i = 0; i < programfileses.size(); i++) {
				ProgramFiles pf = (ProgramFiles) programfileses.get(i);

				strxml += "<file name=\"";
				strxml += (String) programfileNames.get(i);
				strxml += "\" encrypt=\"";
				strxml += (String) encrypts.get(i);
				strxml += "\" location=\"";
				strxml += (String) locations.get(i);
				strxml += "\" />\r\n";
			}
			strxml += "</group>\r\n";
		} else {
			return "";
		}

		strxml += "</content>\r\n";
		strxml += "</namespace>\r\n";

		return strxml;
	}

	// 根据porductlist产品列表，查询，得到CA中对应的产品id列表
	private String getCaProductids(String productlist)
	{
		// 1;2;3;4;
		String keyids;
		if(productlist == null)
		{
			keyids = "";
		}
		else
		{
			keyids = productlist;
		}
//		String str;
//		String[] strl = productlist.split(";");
//		for(int i = 0; i < strl.length; i++)
//		{
//			if(strl[i] != null && strl[i] != "")
//			{
//				str = strl[i];
//			}
//		}
		return keyids;
	}
	
	// 根据存储体等级和filecode，查询存储体等级表、存储体表和存储体目录表，得到路径。目标存储ID查映射盘符，查询存储体表
	@SuppressWarnings("unchecked")
	private List getDestrootByStorageid(String stclasscode, String filecode) {
		String path = ""; // 目标路径
		String remark = ""; // 格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;

		List list = new ArrayList();

		// 根据存储体等级，查询存储体等级表、存储体表，得到存储体
		// 返回：List<Object[]>
		// (AmsStorage)Object[0]
		// (AmsStorageDir)Object[1]
		// (AmsStorageClass)Object[2]
		List<Object[]> stlist = amsstoragedirManager
				.getStorageStoragedirsByStclasscodeFilecode(stclasscode,
						filecode);
		if (stlist != null && stlist.size() > 0) {
			Object[] rows = (Object[]) stlist.get(0);
			AmsStorage amsStorage = (AmsStorage) rows[0];
			AmsStorageDir amsStorageDir = (AmsStorageDir) rows[1];

			String loginDisk = amsStorage.getMaplogindisk(); // 映射盘符
			String storagedirname = amsStorageDir.getStoragedirname(); // 目录
			if (loginDisk != null && !loginDisk.equalsIgnoreCase("")) {
				if (storagedirname == null)
					storagedirname = "";

				path = loginDisk; // 映射盘符
				path += ":\\";
				storagedirname = storagedirname.replace('/', '\\');
				storagedirname = fileopr.checkPathFormatRear(storagedirname,
						'\\');
				path += storagedirname; // 目录

				remark = amsStorage.getStglobalid();
				remark += "|";
				remark += amsStorageDir.getStdirglobalid();
				remark += "|";
			}
		}
		list.add(path);
		list.add(remark);
		return list;
	}
	
	// group的路径，默认规则，根据节目包ID前10位\节目包ID
	private String getGroupName(String productid)
	{
		String str = productid.substring(0, 10);
		str += "\\" + productid;
		return str;
 	}

	// 根据节目包ID，查询，得到节目包下所有文件
	private List getProgramFilesesByProductid(
			String productid, 				// 节目包id
			String keyids, 					// 节目包在CA中的产品id列表
			String stclasscode, 			// 存储等级code
			Long encrypttype,				// 加扰类型，0 - 视频节目包，1 - 富媒体节目包
			String remark					// 备注，格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;
			)
	{
		// 返回：List
		// List programfileses				// 文件记录
		// List programfileName				// 文件名（含后缀），文件存放位置表（TSOASTORAGEPRGREL）中的FILENAME
		// List encrypts 					// 加扰标志，"1" - 需要加扰；"0" - 不加扰，根据文件表中的PROGRANK字段
		// List locations					// 文件存放位置表（TSOASTORAGEPRGREL）中的FILEPATH
		// String remark					// 备注，格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;
		
		// PackageFiles ProgramFiles 
		
		if(encrypttype != Long.valueOf(0) && encrypttype != Long.valueOf(1))
		{
			return null;
		}
		
		List list = new ArrayList();
		List programfileses = new ArrayList();
		List programfileName = new ArrayList();		// 文件存放位置表（TSOASTORAGEPRGREL）中的FILENAME
		List encrypts = new ArrayList();			// 加扰标志，"1" - 需要加扰；"0" - 不加扰，根据文件表中的PROGRANK字段
		List locations = new ArrayList(); 			// 文件存放位置表（TSOASTORAGEPRGREL）中的FILEPATH
		
		List pfs = packageFilesManager.getProgramfilesesByProductidAndStclasscode(productid, stclasscode);
		for(int i = 0; i < pfs.size(); i++)
		{
			Object[] rows = (Object[])pfs.get(i);
			
			ProgramFiles pf = (ProgramFiles)rows[0];
			AmsStoragePrgRel aspr = (AmsStoragePrgRel)rows[1];
			AmsStorage as = (AmsStorage)rows[2];
			AmsStorageDir asd = (AmsStorageDir)rows[3];
			
			String encrypt;
			Long progrank = pf.getProgrank();
			if(progrank != null && progrank == 1 && !"".equalsIgnoreCase(keyids))
			{
				// 需要加扰
				encrypt = "1";
			}
			else
			{
				encrypt = "0";
			}
			String location = "";
			String loginDisk;
			String storageDir;
			String filePath;
			String fileName;
			loginDisk = as.getMaplogindisk();
			storageDir = asd.getStoragedirname();
			filePath = aspr.getFilepath();
			fileName = aspr.getFilename();
			if(storageDir == null)
				storageDir = "";
			if(filePath == null)
				filePath = "";
			if(loginDisk != null)
			{
				if(encrypttype == 1)
				{
					// 富媒体节目包
					location = loginDisk + ":\\";
					if(!storageDir.equalsIgnoreCase(""))
					{
						storageDir = storageDir.replace('/', '\\');
						storageDir = fileopr.checkPathFormatRear(storageDir, '\\');
						location += storageDir;
					}
					if(!filePath.equalsIgnoreCase(""))
					{
						filePath = filePath.replace('/', '\\');
						filePath = fileopr.checkPathFormatRear(filePath, '\\');
						location += filePath;
					}
					if(pf.getProgrank() == 1)
					{
						// 主文件
						location += pf.getProgfileid();
					}
					else
					{
						location += fileName;
					}
					
					location = location.replace('/', '\\');
					remark += aspr.getRelid() + ";";
				}
				else if(encrypttype == 0)
				{
					// 视频节目包
					if(fileName != null)
					{
						location = loginDisk + ":\\";
						if(!storageDir.equalsIgnoreCase(""))
						{
							storageDir = storageDir.replace('/', '\\');
							storageDir = fileopr.checkPathFormatRear(storageDir, '\\');
							location += storageDir;
						}
						if(!filePath.equalsIgnoreCase(""))
						{
							filePath = filePath.replace('/', '\\');
							filePath = fileopr.checkPathFormatRear(filePath, '\\');
							location += filePath;
						}
						location += fileName;
						
						location = location.replace('/', '\\');
						remark += aspr.getRelid() + ";";
					}
				}
			}
			else
			{
				return null;
			}
			
			
			programfileses.add(pf);
			programfileName.add(aspr.getFilename());
			encrypts.add(encrypt);
			locations.add(location);		// 文件的源路径
		}
		
		list.add(programfileses);
		list.add(programfileName);
		list.add(encrypts);
		list.add(locations);
		list.add(remark);
		return list;
	}
	
	// 根据storagePrgRelids，查询得到storagePrgRels
	private List getStoragePrgRels(List storagePrgRelids)
	{
		List storagePrgRels = new ArrayList();
		for(int i = 0; i < storagePrgRelids.size(); i++)
		{
			AmsStoragePrgRel storagePrgRel = (AmsStoragePrgRel)amsstorageprgrelManager.getById((String)storagePrgRelids.get(i));
			storagePrgRels.add(storagePrgRel);
		}
		return storagePrgRels;
	}
	
	// 20100116 21:32
	// 解压缩zip文件到目标路径
	private int unZipSmbFileToSmbPath(String zipSourcePath, String zipDestPath)
	{
		cmsLog.info("Cms -> EncryptServiceImpl -> unZipFileToPath...");
		
		if(zipSourcePath == null || zipSourcePath.equalsIgnoreCase("")
			|| zipDestPath == null || zipDestPath.equalsIgnoreCase("")
			)
		{
			cmsLog.info("输入参数为空，返回。");
			return 1;
		}
		
		fileopr.checkSmbDir(fileopr.checkPathFormatRear(zipDestPath, '/'));
//		checkSmbDir(checkPathFormatRear(zipDestPath, '/'));
		
		UnZip1Impl unzip = new UnZip1Impl();
		unzip.unZipSmb(zipSourcePath, zipDestPath);
		cmsLog.info("Cms -> EncryptServiceImpl -> unZipFileToPath returns.");
		return 0;
	}
	
	// 20100112 15:54
	// 加扰任务完成处理接口，
	public CmsResultDto finishEncrypt(
			String productid,			// 节目包id
			String remark,				// 备注信息，包含：目标存储ID、目标目录ID、目标文件路径、文件位置表主键LIST
			Long encryptResult,			// 加扰完成结果，预期结果：8/9，处理状态（0未处理1下发任务5加扰成功6反馈失败8成功9失败）
			String errorMsg,			// 失败的信息
			String operatorid			// 操作人员
	) {
		// 流程：
		// 修改状态
		//		根据节目ID,修改编单细表中查出所有的上线的编单记录，修改状态为加扰完成或者失败。
		// 		查节目包所在栏目节点下所有的节目包状态是否是加扰完成，如果是则修改分表和总表的活动为下一个活动
		// 创建文件位置表记录
		// 压缩富媒体目录到zip文件
		
		cmsLog.info("Cms -> EncryptServiceImpl -> finishEncrypt...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 配置文件内容
		String destStStorageClassCode = ""; // 加扰目标路径的存储体等级
		String stclasscodeNearOnline = "NearOnline"; // 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline"; // 加扰库存储体等级code
		String stclasscodeOnline = "Online"; // 播控库存储体等级code
		String destFileCode = "ENCRYPTFILE"; // 加扰后的目标文件filecode

		// 读取配置文件
		destStStorageClassCode = "CaOnline";

		// 解析remark，格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;
		List storagePrgRelids = new ArrayList();
		String stGlobalid = ""; // 目标存储体ID
		String stDirGlobalid = ""; // 目标存储体目录ID
		String filePath = ""; // 目标文件路径
		String prgrelids = ""; // 文件位置表主键LIST
		String[] strs = remark.split("\\|"); // 格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;

		ProgPackage progPackage = (ProgPackage) progPackageManager
				.getById(productid);
		if (progPackage == null) {
			// 返回失败
			String str = "节目包不存在，返回失败。节目包ID：" + productid;
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}

		// 处理状态（0未处理1下发任务5加扰成功6反馈失败8成功9失败）
		if (encryptResult == 8) {
			if (strs.length >= 4) {
				stGlobalid = strs[0];
				stDirGlobalid = strs[1];
				filePath = strs[2];
				prgrelids = strs[3];
			} else {
				// 返回失败
				String str = "备注格式有误，返回失败。备注：" + remark;
				cmsLog.info(str);
				cmsResultDto.setResultCode(Long.valueOf(1));
				cmsResultDto.setErrorMessage(str);
				return cmsResultDto;
			}
			
			if (prgrelids != null && !prgrelids.equalsIgnoreCase("")) {
				strs = prgrelids.split(";");
				for (int i = 0; i < strs.length; i++) {
					if (strs[i] != null && !strs[i].equalsIgnoreCase("")) {
						storagePrgRelids.add(strs[i]);
					}
				}
			}

			if (filePath != null && filePath.equalsIgnoreCase("")) {
				filePath = filePath.replace('\\', '/');
				filePath = fileopr.checkPathFormatRear(filePath, '/');
			}
			
			// 根据storagePrgRelids，查询得到storagePrgRels
			List storagePrgRels = getStoragePrgRels(storagePrgRelids);

			// 加扰任务完成处理接口，
			// 说明：
			// 如果是视频，save文件存放位置表记录
			// 如果是富媒体，压缩加扰后的文件夹，并save文件存放位置表记录
			// 最后更新节目包的状态和处理状态
			cmsResultDto = cmsTransactionManager.saveFinishEncrypt(
					progListDetailManager, amsstorageprgrelManager,
					programFilesManager, packageFilesManager,
					progPackageManager, productid, stGlobalid, stDirGlobalid,
					storagePrgRels, destStStorageClassCode,
					stclasscodeNearOnline, stclasscodeCaOnline,
					stclasscodeOnline, destFileCode, filePath, remark,
					operatorid);
			if (cmsResultDto.getResultCode() != 0) {
				// 写数据库失败，修改节目包状态为“失败”
				// 处理状态(0未处理1处理8失败9成功)
				String str = "加扰成功后，写数据库记录失败，准备修改节目包的处理状态为“失败”。";
				cmsLog.info(str);
				progPackage.setDealstate((long) 8);
				progPackageManager.update(progPackage);
				cmsLog.info("节目包状态已修改为“失败”。节目包ID：" + productid);
			}
		} else if (encryptResult == 9) {
			// 返回失败
			// 修改节目包的处理状态为“失败”，
			// 但是，不修改节目包的状态
			// 处理状态(0未处理1处理8失败9成功)
			String str = "加扰结果是“失败”，准备修改节目包的处理状态为“失败”。";
			cmsLog.info(str);
			progPackage.setDealstate((long) 8);
			progPackageManager.update(progPackage);
			cmsLog.info("节目包状态已修改为“失败”。节目包ID：" + productid);
		} else {
			// 返回失败
			String str = "加扰结果未知，返回失败。加扰结果代码：" + encryptResult;
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			progPackage.setDealstate((long) 8);
			progPackageManager.update(progPackage);
			cmsLog.info("节目包状态已修改为“失败”。节目包ID：" + productid);
		}

		cmsLog.info("Cms -> EncryptServiceImpl -> finishEncrypt returns.");
		return cmsResultDto;
	}

	// Edited by Andy at 20100109 21:35
	// 检查节目包是否需要加扰，如果需要，则加扰
	/**
	 * HuangBo Update 2010年8月23日 21时26分
	 */
	public CmsResultDto sendEncryptTaskByProgPackage(
			List<String> productid, 						// 节目包ID
			String operatorid								// 操作人员ID
	) {
		int flag = 0;
		String resultString = "成功{0}个, 失败{1}个. {2}";
		String tmpString = "失败节目包: ";
			
		cmsLog.info("Cms -> EncryptServiceImpl -> sendEncryptTaskByProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		for (int index = 0; index < productid.size(); ++ index) {
				// 说明：	加扰任务是以节目包为单位的，不是以文件为单位的
				//		节目包状态（是否加扰），是记录在栏目单详细里的
				//		当节目包和栏目单详细都到位（在数据库）时，才可以生成加扰任务
				//		当存在的栏目单详细记录中的节目包状态是已经加扰的，则表示节目包已经加扰过，不需要再加扰了。
				//		当节目包的产品列表不为空、且是主文件，为该文件加扰。
				// 流程：
				// 判断节目包是否存在；
				// 判断栏目单详细是否存在；如果存在，判断已经存在的节目包状态，如果没有加扰，则生成加扰任务，改变所有栏目单详细的节目包状态
				// 如果需要生成加扰任务
				// 则，根据节目包，查询，得到节目包（）、产品列表、文件列表（包含文件路径、主文件标志），文件所在存储体（包含映射盘符）
				// 判断节目包是视频还是富媒体
				// 如果富媒体，unzip压缩包
				// 如果视频，继续
			
			cmsLog.info("为节目包生成加扰任务，节目包ID：" + productid.get(index));
			
			ProgPackage progPackage = (ProgPackage)progPackageManager.getById(productid.get(index));
			if (progPackage == null) {
				String str = "节目包不存在，节目包ID：" + productid.get(index);
				cmsLog.info(str);
				flag++;
				tmpString += "节目包 " + productid.get(index) + " 不存在; ";
				continue;
			}
			List progListDetails = progListDetailManager.findByProperty(
					"productid", productid.get(index)); // 查节目包的栏目单详细
			if (progListDetails.size() <= 0) {
				String str = "节目包的栏目单详细不存在，节目包ID：" + productid.get(index);
				cmsLog.warn(str);
				flag++;
				tmpString += progPackage.getProductname() + "; ";
				continue;
			}

			// 查询节目包的文件列表，文件的filetype=节目包的主文件类型filetype
			// 查询节目包的产品列表，需要查询产品与CA产品映射表，得到CA产品ID
			cmsLog.info("准备查询节目包: " + productid.get(index) + " 的产品列表...");
			String productlist = "";
			boolean bEncrypt = true;
			for (int i = 0; i < progListDetails.size(); i++) {
				ProgListDetail progListDetail = (ProgListDetail) progListDetails
						.get(i);
				productlist = progListDetail.getETitle(); // 产品列表
			}

			cmsLog.info("判断节目包: " + productid.get(index)
					+ " 的状态和处理状态，是否允许下加扰任务...");
			Long state = progPackage.getState();
			Long dealState = progPackage.getDealstate();
			// 节目包加扰状态，（0导入1存在2加扰3一级库）
			// dealState 0未处理1处理8失败9成功
			if (state == null || dealState == null) {
				bEncrypt = false;
			} else if (state == 1) {
				if (dealState == 0 || dealState == 8) {
					bEncrypt = true;
				} else {
					bEncrypt = false;
				}
			} else {
				bEncrypt = false;
			}
			
			if (bEncrypt == false) {
				String str = "节目包的栏目单详细的节目包状态不符合规则，不生成加扰任务。节目包ID："
						+ progPackage.getProductid();
				cmsLog.warn(str);
				// return cmsResultDto;
				flag++;
				tmpString += progPackage.getProductname() + "; ";
				continue;
			}

			cmsLog.info("节目包: " + productid.get(index) + ", 准备生成加扰任务...");
			// 根据porductlist产品列表，查询，得到CA中对应的产品id列表
			String keyids = getCaProductids(productlist);

			// 配置文件内容
			String destStStorageClassCode = ""; // 加扰目标路径的存储体等级
			String destFileCode = ""; // 加扰目标路径的文件代码
			String stclasscode = ""; // 源文件所在存储体等级，从配置文件得到
			// 读取配置文件
			destStStorageClassCode = "CaOnline";
			destFileCode = "ENCRYPTFILE";
			stclasscode = "NearOnline";

			// 生成加扰任务，根据永新视博的加扰任务格式
			List destrootRemark = getDestrootByStorageid(
					destStStorageClassCode, destFileCode); // 从配置文件读取目标存储ID。z:\
			String destroot = (String) destrootRemark.get(0); // 从配置文件读取目标存储ID。z:\
			String remark = (String) destrootRemark.get(1); // 格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;
			String expireddate = getSubscriberetimeByProgPackageXml(progPackage
					.getPpxml());
			String groupName = getGroupName(progPackage.getProductid()); // group的路径，默认规则，根据节目包ID前10位\节目包ID。"PPVP201001\PPVP20100106162355000906"
			List programfileses = new ArrayList();
			List programfileNames = new ArrayList();
			List encrypts = new ArrayList();
			List locations = new ArrayList();
			String taskprio = "1";
			Long encrypttype = Long.valueOf(1); // 加扰类别，根据节目包类型判断
			String keyfiledestpath = ""; // key文件所在位置

			remark += groupName;
			remark += "|";

			if (destroot == null || destroot.equalsIgnoreCase("")) {
				String str = "查询的目标路径为空，不生成加扰任务。";
				cmsLog.warn(str);
				// cmsResultDto.setResultCode(Long.valueOf(1));
				// cmsResultDto.setErrorMessage(str);
				// return cmsResultDto;
				flag++;
				tmpString += progPackage.getProductname() + "; ";
				continue;
			}

			if (progPackage.getProgtype().equalsIgnoreCase("V")) {
				// 视频节目包
				cmsLog.info("节目包: " + productid.get(index) + " 是视频类型...");
				encrypttype = Long.valueOf(0);
			} else if (progPackage.getProgtype().equalsIgnoreCase("R")) {
				// 富媒体节目包
				cmsLog.info("节目包: " + productid.get(index) + " 是富媒体类型...");
				encrypttype = Long.valueOf(1);
			} else {
				cmsLog.warn("节目包: " + productid.get(index) + " 类型未知："
						+ progPackage.getProgtype());
				encrypttype = Long.valueOf(-1);
			}

			// 根据节目包ID，查询，得到节目包下所有文件
			List list = getProgramFilesesByProductid(productid.get(index),
					keyids, stclasscode, encrypttype, remark);
			programfileses = (List) list.get(0);
			programfileNames = (List) list.get(1);
			encrypts = (List) list.get(2);
			locations = (List) list.get(3);
			remark = (String) list.get(4);

			if (encrypttype == 0) {
				// 视频节目包
				keyfiledestpath = groupName;
			} else if (encrypttype == 1) {
				// 富媒体节目包
				if (programfileses.size() > 0) {
					String progfileid = ((ProgramFiles) programfileses.get(0))
							.getProgfileid();
					keyfiledestpath += groupName + "\\" + progfileid;
				}
			}

			if (progPackage.getSubscriberetime() != null) {
				expireddate = fileopr.convertDateToString(
						progPackage.getSubscriberetime(), "yyyy-MM-dd");
			}
			else
			{
				expireddate = "2100-01-01";
			}
			if(expireddate == null || expireddate.equalsIgnoreCase(""))
			{
				expireddate = "2100-01-01";
			}

			cmsLog.info("节目包: " + productid.get(index) + " 准备生成加扰任务字符串...");
			String taskXml = generateEncryptTaskXml(progPackage, destroot,
					keyids, expireddate, encrypttype, groupName,
					programfileses, programfileNames, encrypts, locations);
			cmsLog.info("节目包: " + productid.get(index) + " 加扰任务字符串生成完毕。");

			if (taskXml == null || taskXml.equalsIgnoreCase("")) {
				String str = "节目包: " + productid.get(index)
						+ " 生成的加扰任务字符串为空，不生成加扰任务。";
				cmsLog.warn(str);
				// cmsResultDto.setResultCode(Long.valueOf(1));
				// cmsResultDto.setErrorMessage(str);
				// return cmsResultDto;
				flag++;
				tmpString += progPackage.getProductname() + "; ";
				continue;
			}

			// --------------------- 富媒体
			// --------------------------------------------
			// 如果是富媒体节目包，unzip压缩包
			if (encrypttype == 1) {
				cmsLog.info("节目包: " + productid.get(index)
						+ " 是富媒体类型，准备解压缩zip文件...");
				String richPpSourcepath = "";
				String unzipPath = "";
				// 查询zip源路径
				cmsLog.info("查询节目包: " + productid.get(index) + " zip源路径...");
				if (programfileses.size() > 0) {
					cmsLog.info("获得节目包: " + productid.get(index) + " 主文件记录...");
					String progfileid = "";
					for (int i = 0; i < programfileses.size(); i++) {
						ProgramFiles progf = (ProgramFiles) programfileses
								.get(i);
						if (progf.getProgrank() == 1) {
							progfileid = progf.getProgfileid();
							cmsLog.info("找到节目包: " + productid.get(index)
									+ " 主文件记录，文件ID：" + progf.getProgfileid());
							break;
						}
					}
					if (progfileid == null || progfileid.equalsIgnoreCase("")) {
						String str = "节目包: " + productid.get(index)
								+ " 是富媒体类型，但是没有找到主文件，不能解压缩，不生成加扰任务，返回失败。";
						cmsLog.warn(str);
						flag++;
						tmpString += progPackage.getProductname() + "; ";
						continue;
					}
					// String progfileid =
					// ((ProgramFiles)programfileses.get(0)).getProgfileid();
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (ProgramFiles)Object[0]
					// (AmsStorage)Object[1]
					// (AmsStoragePrgRel)Object[2]
					// (AmsStorageDir)Object[3]
					// (AmsStorageClass)Object[4]
					List sourcepaths = packageFilesManager
							.getSourcePathByProgfileidStclasscode(progfileid,
									stclasscode);
					if (sourcepaths != null && sourcepaths.size() >= 2) {
						richPpSourcepath = (String) sourcepaths.get(0);
						cmsLog.info("节目包: " + productid.get(index)
								+ " zip源路径 - " + richPpSourcepath);
					} else {
						String str = "节目包: " + productid.get(index)
								+ " 获得解压缩源文件路径为空，返回失败。";
						cmsLog.warn(str);
						// cmsResultDto.setResultCode(Long.valueOf(1));
						// cmsResultDto.setErrorMessage(str);
						// return cmsResultDto;
						flag++;
						tmpString += progPackage.getProductname() + "; ";
						continue;
					}
				}

				// 查询zip目标路径，就是源路径去掉文件名

				// 北京修改，20100204 15:06
				// 把"/"改成"."
				unzipPath = richPpSourcepath.substring(0,
						richPpSourcepath.lastIndexOf("."));
				// 修改完毕
				unzipPath = fileopr.checkPathFormatRear(unzipPath, '/');

				if (richPpSourcepath == null
						|| richPpSourcepath.equalsIgnoreCase("")
						|| unzipPath == null || unzipPath.equalsIgnoreCase("")) {
					String str = "节目包: " + productid.get(index)
							+ " 获得解压缩的源文件路径或解压缩目标路径为空，返回失败。";
					cmsLog.warn(str);
					// cmsResultDto.setResultCode(Long.valueOf(1));
					// cmsResultDto.setErrorMessage(str);
					// return cmsResultDto;
					flag++;
					tmpString += progPackage.getProductname() + "; ";
					continue;
				} else {
					cmsLog.info("节目包: " + productid.get(index)
							+ " 开始解压缩zip文件...");
					cmsLog.info("源 - " + richPpSourcepath);
					cmsLog.info("目标 - " + unzipPath);
					unZipSmbFileToSmbPath(richPpSourcepath, unzipPath);
					cmsLog.info("节目包: " + productid.get(index) + " 解压缩zip文件完毕。");
				}
			} else {
				cmsLog.info("节目包: " + productid.get(index)
						+ " 不是富媒体类型，不需要解压缩zip文件，继续...");
			}

			cmsLog.info("节目包: " + productid.get(index) + " 准备生成加扰任务数据库记录...");
			EncryptList encryptList = new EncryptList();
			encryptList.setProductid(productid.get(index));
			encryptList.setProductname(progPackage.getProductname());
			if (programfileses.size() > 0)
				encryptList
						.setProgfileid(((ProgramFiles) programfileses.get(0))
								.getProgfileid()); // "???????????????????多文件"
			encryptList.setPorductlist(keyids);
			encryptList.setTaskprio(taskprio); // 优先级
			encryptList.setEncrypttype(encrypttype); // 加扰类别（0文件1文件夹）
			if (locations.size() > 0)
				encryptList.setLocation((String) locations.get(0)); // "???????????????????????多文件"
			if (programfileNames.size() > 0)
				encryptList.setName((String) programfileNames.get(0)); // "???????????????????????多文件"
			encryptList.setKeyfiledestpath(keyfiledestpath);
			encryptList.setGroupname(groupName);
			encryptList.setContentxml(taskXml);
			encryptList.setDate4(new Date());
			encryptList.setDate5(null); // 由加扰模块修改，加扰结束时间
			encryptList.setDealstate(Long.valueOf(0)); // 处理状态（0未处理1下发任务8成功9失败）
			encryptList.setDealinfo(""); // 由加扰模块修改
			encryptList.setScip(""); // 由加扰模块修改
			encryptList.setState2(Long.valueOf(0)); // 状态2（=0：未处理；=1：已分配；=2：处理中；=3：成功；=4：失败
													// ）
			encryptList.setInputmanid(operatorid);
			encryptList.setInputtime(new Date());
			encryptList.setRemark(remark); // 格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;
			cmsTransactionManager.saveEncryptTask(encryptListManager,
					progListDetailManager, progPackageManager, encryptList,
					progPackage);

			cmsLog.info("节目包: " + productid.get(index)
					+ " 加扰任务数据库记录已经生成。加扰任务ID：" + encryptList.getEncryptid());

			updateEncryptXml(encryptList.getEncryptid());

			cmsResultDto.setResultObject(encryptList);
		}

		String result = null;
		if (flag == 0) {
			result = "加扰成功!";
			cmsResultDto.setResultCode(0l);
			cmsResultDto.setErrorMessage(result);
		} else if (flag > 0 && flag < productid.size()) {
			result = MessageFormat.format(resultString,
					productid.size() - flag, flag, tmpString);
			cmsResultDto.setResultCode(1l);
			cmsResultDto.setErrorMessage(result);
		} else {
			result = "加扰失败!";
			cmsResultDto.setResultCode(1l);
			cmsResultDto.setErrorMessage(result);
		}
		cmsLog.info("Cms -> EncryptServiceImpl -> sendEncryptTaskByProgPackage returns.");
		return cmsResultDto;
	}

	// 20100520 
	// 修改加扰任务单的content id为加扰任务数据库记录id
	private CmsResultDto updateEncryptXml(String encryptid) {
		cmsLog.info("Cms -> EncryptServiceImpl -> updateEncryptXml...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		try  {
			EncryptList encryptList = (EncryptList)encryptListManager.getById(encryptid);
			String strxml = encryptList.getContentxml();
			
			try {
				cmsLog.info("id在字符串中的位置：" + strxml.indexOf("id=\""));
				String strid = strxml.substring(strxml.indexOf("id=\""),
						strxml.indexOf("id=\"") + 13);
				cmsLog.info("原：" + strid);
				Long lid = Long.valueOf(encryptList.getEncryptid());
				String strnewid = "id=\"" + lid + "\"";
				cmsLog.info("新：" + strnewid);
				strxml = strxml.replaceFirst(strid, strnewid);
			} catch (Exception e) {
				cmsLog.error("更新加扰任务xml异常：" + e.getMessage());
				cmsLog.info("不修改id。");
			}
			
			if (!strxml.equalsIgnoreCase("")) {
				encryptList.setContentxml(strxml);
				encryptListManager.update(encryptList);
				cmsLog.info("已经更新xml。content id:" + encryptid);
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> EncryptServiceImpl -> updateEncryptXml，异常："
					+ e.getMessage());
		}
		cmsLog.info("Cms -> EncryptServiceImpl -> updateEncryptXml returns.");
		return cmsResultDto;
	}

	// 20100520 16:58
	// 根据节目包的xml，获取节目包的版权期终subscriberetime
	private String getSubscriberetimeByProgPackageXml(String ppxml) {
		cmsLog.info("Cms -> EncryptServiceImpl -> getSubscriberetimeByProgPackageXml...");
		String subscriberetime = "2050-12-31";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// 将String转成parse可以识别的InputSource
			StringReader sr = new StringReader(ppxml);
			InputSource is = new InputSource(sr);
			Document doc = builder.parse(is);
			doc.normalize();

			// cmsLog.info("修改节目包节点(APP)属性...");
			NodeList cells = doc.getElementsByTagName("subscriberetime");
			for (int i = 0; i < cells.getLength(); i++) {
				Node cell = cells.item(i);
				Element cellattr = (Element) cells.item(i);
				subscriberetime = cell.getTextContent();
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> EncryptServiceImpl -> getSubscriberetimeByProgPackageXml，异常："
					+ e.getMessage());
		}
		if (subscriberetime == null || subscriberetime.equalsIgnoreCase("")) {
			subscriberetime = "2050-12-31";
			cmsLog.info("获取subscriberetime为空，设置为：" + subscriberetime);
		}
		cmsLog.info("Cms -> EncryptServiceImpl -> getSubscriberetimeByProgPackageXml returns.");
		return subscriberetime;
	}
	
	// 20100610 10:07
	// 查询加扰任务
	public CmsResultDto getEncryptTasks(
			String productid,			// 节目包ID，=
			String productname,			// 节目包名称，like
			String datefrom,			// 日期起始，格式：yyyy-MM-dd
			String dateto				// 日期终止，格式：yyyy-MM-dd
			)
	{
		cmsLog.info("Cms -> EncryptServiceImpl -> getEncryptTasks...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		cmsLog.info("productid:" + productid);
		cmsLog.info("productname:" + productname);
		cmsLog.info("datefrom:" + datefrom);
		cmsLog.info("dateto:" + dateto);
		
		try
		{
			List list = null;
			if (datefrom == null 
				|| "".equalsIgnoreCase(datefrom)
				|| dateto == null
				|| "".equalsIgnoreCase(dateto)
				)
			{
				String str = "输入日期不能为空。";
				cmsLog.warn(str);
				cmsResultDto.setResultObject((long)1);
				cmsResultDto.setErrorMessage(str);
			}
			else if((productid == null || "".equalsIgnoreCase(productid))
					&& (productname == null || "".equalsIgnoreCase(productname))
					)
			{
				list = encryptListManager.getEncryptListByDate(
						datefrom, 
						dateto
						);
			}
			else if(productid == null || "".equalsIgnoreCase(productid))
			{
				list = encryptListManager.getEncryptListByProductnameDate(
						productname, 
						datefrom, 
						dateto
						);
			}
			else if(productname == null || "".equalsIgnoreCase(productname))
			{
				list = encryptListManager.getEncryptListByProductidDate(
						productid, 
						datefrom, 
						dateto
						);
			}
			else
			{
				list = encryptListManager.getEncryptListByProductidProductnameDate(
						productid, 
						productname, 
						datefrom, 
						dateto
						);
			}
			
			if(list == null)
			{
				cmsLog.warn("返回加扰任务记录为空。");
			}
			else
			{
				cmsLog.info("返回加扰任务记录数：" + list.size());
				/**
				 * HuangBo update by 2011年4月13日 14时59分
				 * 增加前台显示用的加扰状态, 由原来的两个分离的状态改为统一文字显示, 从字段remark中取
				 */
				for (Object object : list) {
					EncryptList encryptList = (EncryptList) object;
					if (0 == encryptList.getDealstate() 
							&& 0 == encryptList.getState2()) {
						encryptList.setRemark("未处理");
					} else if (1 == encryptList.getDealstate() 
							&& 2 > encryptList.getState2()) {
						encryptList.setRemark("等待加扰");
					} else if (1 == encryptList.getDealstate() 
							&& 2 == encryptList.getState2()) {
						encryptList.setRemark("加扰中");
					} else if (8 == encryptList.getDealstate() 
							&& 3 == encryptList.getState2()) {
						encryptList.setRemark("加扰成功");
					} else {
						encryptList.setRemark("加扰失败");
					}
				}
				cmsResultDto.setResultObject(list);
			}
		}
		catch(Exception e)
		{
			String str = "查询加扰单，异常：" + e.getMessage();
			cmsLog.warn(str);
			cmsResultDto.setResultObject((long)1);
			cmsResultDto.setErrorMessage(str);
		}
		
		cmsLog.info("Cms -> EncryptServiceImpl -> getEncryptTasks returns.");
		return cmsResultDto;
	}
	
	/**
	 * 批量发送节目包加扰任务, 支持视频文件和富媒体文件的加扰任务
	 * @param progPackageID 节目包ID列表
	 * @param operatorID 操作员ID
	 * @return 返回生成加扰任务信息
	 */
	@SuppressWarnings("rawtypes")
	public String sendEncryptTask(List<String> progPackageID, String operatorID) {
		cmsLog.debug("Cms -> EncryptServiceImpl -> sendEncryptTask start");
		int count = 0;
		String resultString = "成功{0}个, 失败{1}个. {2}";
		String tmpString = "失败节目包: ";
		String result = null;
		
		cmsLog.debug("根据节目包ID取得所有节目包, 待加加扰的节目包数量: " + progPackageID.size() + " 个!");
		List<ProgPackage> progPackages = this.progPackageManager.queryByIDs(progPackageID);
		
		cmsLog.debug("循环所有存在的节目包, 为每一个节目包生成加扰任务...");
		for (ProgPackage progPackage : progPackages) {
			cmsLog.debug("为节目包生成加扰任务，节目包ID：" + progPackage.getProductid());
			
			List<?> programfileses = new ArrayList();
			List<?> programfileNames = new ArrayList();
			List<?> encrypts = new ArrayList();
			List<?> locations = new ArrayList();
			
			String serviceID = progPackage.getRemark();
			cmsLog.debug("当前节目包的加扰产品ID为: " + serviceID);

			/**
			 * 判断节目包状态, 及使用状态是否为, 缓存库, (未使用 或失败状态) 节目包状态:state，（0:导入, 1:缓存,
			 * 2:加扰, 3:一级库） 节目包使用状态:dealState (0:未处理, 1:处理中, 8:失败, 9:成功)
			 */
			if (1 == progPackage.getState()
					&& (0 == progPackage.getDealstate() || 8 == progPackage
							.getDealstate())) {
				// 节目包状态及使用状态符合要求
				// 配置文件内容
				String destStStorageClassCode = "CaOnline"; // 加扰目标路径的存储体等级
				String destFileCode = "ENCRYPTFILE"; // 加扰目标路径的文件代码
				String stclasscode = "NearOnline"; // 源文件所在存储体等级，从配置文件得到

				// 生成加扰任务，根据永新视博的加扰任务格式
				List destrootRemark = getDestrootByStorageid(
						destStStorageClassCode, destFileCode); // 从配置文件读取目标存储ID。z:\
				String destroot = (String) destrootRemark.get(0); // 从配置文件读取目标存储ID。z:\
				String remark = (String) destrootRemark.get(1); // 格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;
				String expireddate = getSubscriberetimeByProgPackageXml(progPackage
						.getPpxml());
				String groupName = getGroupName(progPackage.getProductid()); // group的路径，默认规则，根据节目包ID前10位\节目包ID。"PPVP201001\PPVP20100106162355000906"

				String taskprio = "1";
				Long encrypttype = Long.valueOf(1); // 加扰类别，根据节目包类型判断
				String keyfiledestpath = ""; // key文件所在位置
				remark += groupName;
				remark += "|";

				if (null == destroot || "".equals(destroot)) {
					cmsLog.debug("查询的目标路径为空，不生成加扰任务。");
					count++;
					tmpString += progPackage.getProductname() + "; ";
					continue;
				}

				if (progPackage.getProgtype().equalsIgnoreCase("V")) {
					// 视频节目包
					cmsLog.info("节目包: " + progPackage.getProductid()
							+ " 是视频类型...");
					encrypttype = 0l;
				} else if (progPackage.getProgtype().equalsIgnoreCase("R")) {
					// 富媒体节目包
					cmsLog.info("节目包: " + progPackage.getProductid()
							+ " 是富媒体类型...");
					encrypttype = 1l;
				} else {
					cmsLog.warn("节目包: " + progPackage.getProductid() + " 类型未知："
							+ progPackage.getProgtype());
					encrypttype = -1l;
				}

				// 根据节目包ID，查询，得到节目包下所有文件
				List list = getProgramFilesesByProductid(
						progPackage.getProductid(), serviceID, stclasscode,
						encrypttype, remark);
				programfileses = (List) list.get(0);
				programfileNames = (List) list.get(1);
				encrypts = (List) list.get(2);
				locations = (List) list.get(3);
				remark = (String) list.get(4);

				if (encrypttype == 0) {
					// 视频节目包
					keyfiledestpath = groupName;
				} else if (encrypttype == 1) {
					// 富媒体节目包
					if (programfileses.size() > 0) {
						String progfileid = ((ProgramFiles) programfileses
								.get(0)).getProgfileid();
						keyfiledestpath += groupName + "\\" + progfileid;
					}
				}

				if (progPackage.getSubscriberetime() != null) {
					expireddate = fileopr.convertDateToString(
							progPackage.getSubscriberetime(), "yyyy-MM-dd");
				}

				cmsLog.info("节目包: " + progPackage.getProductid()
						+ " 准备生成加扰任务字符串...");
				String taskXml = generateEncryptTaskXml(progPackage, destroot,
						serviceID, expireddate, encrypttype, groupName,
						programfileses, programfileNames, encrypts, locations);
				cmsLog.info("节目包: " + progPackage.getProductid()
						+ " 加扰任务字符串生成完毕。");

				if (taskXml == null || taskXml.equalsIgnoreCase("")) {
					cmsLog.warn("节目包: " + progPackage.getProductid()
							+ " 生成的加扰任务字符串为空，不生成加扰任务。");
					count++;
					tmpString += progPackage.getProductname() + "; ";
					continue;
				}

				// --------------------- 富媒体
				// --------------------------------------------
				// 如果是富媒体节目包，unzip压缩包
				if (encrypttype == 1) {
					cmsLog.info("节目包: " + progPackage.getProductid()
							+ " 是富媒体类型，准备解压缩zip文件...");
					String richPpSourcepath = "";
					String unzipPath = "";
					// 查询zip源路径
					cmsLog.info("查询节目包: " + progPackage.getProductid()
							+ " zip源路径...");
					if (programfileses.size() > 0) {
						cmsLog.info("获得节目包: " + progPackage.getProductid()
								+ " 主文件记录...");
						String progfileid = "";
						for (int i = 0; i < programfileses.size(); i++) {
							ProgramFiles progf = (ProgramFiles) programfileses
									.get(i);
							if (progf.getProgrank() == 1) {
								progfileid = progf.getProgfileid();
								cmsLog.info("找到节目包: "
										+ progPackage.getProductid()
										+ " 主文件记录，文件ID："
										+ progf.getProgfileid());
								break;
							}
						}
						if (progfileid == null
								|| progfileid.equalsIgnoreCase("")) {
							String str = "节目包: " + progPackage.getProductid()
									+ " 是富媒体类型，但是没有找到主文件，不能解压缩，不生成加扰任务，返回失败。";
							cmsLog.warn(str);
							count++;
							tmpString += progPackage.getProductname() + "; ";
							continue;
						}
						// String progfileid =
						// ((ProgramFiles)programfileses.get(0)).getProgfileid();
						// 返回：List
						// 1 - String
						// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
						// 2 - List<Object[]>
						// (ProgramFiles)Object[0]
						// (AmsStorage)Object[1]
						// (AmsStoragePrgRel)Object[2]
						// (AmsStorageDir)Object[3]
						// (AmsStorageClass)Object[4]
						List sourcepaths = packageFilesManager
								.getSourcePathByProgfileidStclasscode(
										progfileid, stclasscode);
						if (sourcepaths != null && sourcepaths.size() >= 2) {
							richPpSourcepath = (String) sourcepaths.get(0);
							cmsLog.info("节目包: " + progPackage.getProductid()
									+ " zip源路径 - " + richPpSourcepath);
						} else {
							String str = "节目包: " + progPackage.getProductid()
									+ " 获得解压缩源文件路径为空，返回失败。";
							cmsLog.warn(str);
							// cmsResultDto.setResultCode(Long.valueOf(1));
							// cmsResultDto.setErrorMessage(str);
							// return cmsResultDto;
							count++;
							tmpString += progPackage.getProductname() + "; ";
							continue;
						}
					}

					// 查询zip目标路径，就是源路径去掉文件名

					// 北京修改，20100204 15:06
					// 把"/"改成"."
					unzipPath = richPpSourcepath.substring(0,
							richPpSourcepath.lastIndexOf("."));
					// 修改完毕
					unzipPath = fileopr.checkPathFormatRear(unzipPath, '/');

					if (richPpSourcepath == null
							|| richPpSourcepath.equalsIgnoreCase("")
							|| unzipPath == null
							|| unzipPath.equalsIgnoreCase("")) {
						String str = "节目包: " + progPackage.getProductid()
								+ " 获得解压缩的源文件路径或解压缩目标路径为空，返回失败。";
						cmsLog.warn(str);
						count++;
						tmpString += progPackage.getProductname() + "; ";
						continue;
					} else {
						cmsLog.info("节目包: " + progPackage.getProductid()
								+ " 开始解压缩zip文件...");
						cmsLog.info("源 - " + richPpSourcepath);
						cmsLog.info("目标 - " + unzipPath);
						unZipSmbFileToSmbPath(richPpSourcepath, unzipPath);
						cmsLog.info("节目包: " + progPackage.getProductid()
								+ " 解压缩zip文件完毕。");
					}
				} else {
					cmsLog.info("节目包: " + progPackage.getProductid()
							+ " 不是富媒体类型，不需要解压缩zip文件，继续...");
				}

				cmsLog.info("节目包: " + progPackage.getProductid()
						+ " 准备生成加扰任务数据库记录...");
				EncryptList encryptList = new EncryptList();
				encryptList.setProductid(progPackage.getProductid());
				encryptList.setProductname(progPackage.getProductname());
				if (programfileses.size() > 0)
					encryptList.setProgfileid(((ProgramFiles) programfileses
							.get(0)).getProgfileid()); // "???????????????????多文件"
				encryptList.setPorductlist(serviceID);
				encryptList.setTaskprio(taskprio); // 优先级
				encryptList.setEncrypttype(encrypttype); // 加扰类别（0文件1文件夹）
				if (locations.size() > 0)
					encryptList.setLocation((String) locations.get(0)); // "???????????????????????多文件"
				if (programfileNames.size() > 0)
					encryptList.setName((String) programfileNames.get(0)); // "???????????????????????多文件"
				encryptList.setKeyfiledestpath(keyfiledestpath);
				encryptList.setGroupname(groupName);
				encryptList.setContentxml(taskXml);
				encryptList.setDate4(new Date());
				encryptList.setDate5(null); // 由加扰模块修改，加扰结束时间
				encryptList.setDealstate(Long.valueOf(0)); // 处理状态（0未处理1下发任务8成功9失败）
				encryptList.setDealinfo(""); // 由加扰模块修改
				encryptList.setScip(""); // 由加扰模块修改
				encryptList.setState2(0l); // 状态2（=0：未处理；=1：已分配；=2：处理中；=3：成功；=4：失败
														// ）
				encryptList.setInputmanid(operatorID);
				encryptList.setInputtime(new Date());
				encryptList.setRemark(remark); // 格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;
				cmsTransactionManager.saveEncryptTask(encryptListManager,
						progListDetailManager, progPackageManager, encryptList,
						progPackage);

				cmsLog.info("节目包: " + progPackage.getProductid()
						+ " 加扰任务数据库记录已经生成。加扰任务ID：" + encryptList.getEncryptid());

				updateEncryptXml(encryptList.getEncryptid());

				if (count == 0) {
					result = "加扰成功!";
				} else if (count > 0 && count < progPackageID.size()) {
					result = MessageFormat.format(resultString,
							progPackageID.size() - count, count, tmpString);
				} else {
					result = "加扰失败!";
				}
			} else {
				cmsLog.warn("节目包的状态不符合规则，不生成加扰任务。节目包ID："
						+ progPackage.getProductid());
				count++;
				tmpString += progPackage.getProductname() + "; ";
				continue;
			}
		}
		
		cmsLog.info("Cms -> EncryptServiceImpl -> sendEncryptTask returns.");
		return result;
	}

	/**
	 * 1.2  生成内容加扰XML
	 * @param encryptProgVo
	 * @param encryptPath
	 * @param sourcePath
	 * @return
	 */
	private org.dom4j.Document generateContentEncryptXmlDocument(
			EncryptProgVo encryptProgVo, String encryptPath, String sourcePath) {
		org.dom4j.Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("gb2312");
		org.dom4j.Element rootElement = document.addElement("namespace");
		rootElement.addAttribute("name", "preScrambling");
		rootElement.addAttribute("version", "1.0");
		
		// 一个节目包表示为一个content
		org.dom4j.Element contentElement = rootElement.addElement("content");
		contentElement.addAttribute("id", "0x" + Long.toHexString(Long.valueOf(encryptProgVo.getProgramFileContentId())));			// 文件信息的ContentID
		contentElement.addAttribute("destroot", encryptPath);	// 存放文件的根目录
		
		org.dom4j.Element groupElement = contentElement.addElement("group");
		groupElement.addAttribute("name", encryptProgVo.getProgramFileId().substring(0, 10) + "\\" 
				+ encryptProgVo.getProgramFileId());
		
		org.dom4j.Element fileOrFolderElement = null;
		if ("V".equals(encryptProgVo.getProgramInfoProgType())) {
			fileOrFolderElement = groupElement.addElement("file");
		} else {
			fileOrFolderElement = groupElement.addElement("folder");
		}
		
		fileOrFolderElement.addAttribute("name", encryptProgVo.getProgramFileName());
		fileOrFolderElement.addAttribute("encrypt", encryptProgVo.getProgramFileProgRank().toString());
		fileOrFolderElement.addAttribute("location", sourcePath);
		return document;
	}
	
	/**
	 * 1.2 生成产品加密XML
	 * @param encryptProgVo	
	 * @param productInfo
	 * @param keyPath
	 * @return
	 */
	private org.dom4j.Document generateProductEncryptXmlDocument(
			EncryptProgVo encryptProgVo, TProductInfo productInfo,
			String keyPath) {
		org.dom4j.Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("gb2312");
		org.dom4j.Element rootElement = document.addElement("namespace");
		rootElement.addAttribute("name", "PushVOD");
		rootElement.addAttribute("version", "1.0");
		
		org.dom4j.Element drmElement = rootElement.addElement("drm");
		drmElement.addAttribute("destfilename", keyPath);			// key文件路径 
		
		org.dom4j.Element issueElement = drmElement.addElement("issue");
		issueElement.addAttribute("contentids", encryptProgVo.getProgramFileContentId());
		issueElement.addAttribute("expiredtime", 
				DateUtil.getDateStr("yyyy-MM-dd", productInfo.getExpired()));
		if (null != productInfo.getKeyId()) {
			for (String string : productInfo.getKeyId().split(",")) {
				org.dom4j.Element productElement = issueElement.addElement("product");
				long productId = Long.valueOf(string);
				productElement.addAttribute("id", "0x" + Long.toHexString(productId));
			}
		}
		
		org.dom4j.Element ippvElement = issueElement.addElement("ippv");
		long ippvId = Long.valueOf(productInfo.getIppvId());
		ippvElement.addAttribute("id", "0x" + Long.toHexString(ippvId));
		ippvElement.addAttribute("price", productInfo.getPrice());
		cmsLog.debug(document.asXML());
		
		/**
		 * 不需要迁移, contents节目不需要
		 */
//		org.dom4j.Element contentElement = drmElement.addElement("contents");
//		org.dom4j.Element groupElement = contentElement.addElement("group");
//		groupElement.addAttribute("destpath", encryptPath);	// 目标文件夹
//		
//		org.dom4j.Element fileOrFolderElement = null;
//		if ("V".equals(encryptProgVo.getProgramInfoProgType())) {
//			fileOrFolderElement = groupElement.addElement("file");
//		} else {
//			fileOrFolderElement = groupElement.addElement("folder");
//		}
//		
//		fileOrFolderElement.addAttribute("name", encryptProgVo.getProgramFileName());
//		fileOrFolderElement.addAttribute("location", sourcePath);	// 源文件路径
		return document;
	}

	/**
	 * 内容加密, 为节目包下内容加密任务
	 * @param progPackageId 节目包ID
	 * @param inputMainId 操作人员ID
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String encryptContent(String progPackageId, String inputManId) throws Exception {
		EncryptProgVo encryptProgVo = this.progPackageManager.
				queryProgramFilesVoByProgPackageId(progPackageId);
		if (null == encryptProgVo) {
			return " 节目或节目包不存在! ";
		} else if (2 > encryptProgVo.getProgramInfoDsFlag()) {
			return " 节目包实体文件未导入到缓存库, 不能进行内容加密! ";
		} else if (1 == encryptProgVo.getProgPackageDealState()) {
			return " 节目包是处理中状态, 不能进行内容加密! ";
		} else if (1 == encryptProgVo.getProgramFileEncodeStatus()) {
			List<Object[]> progPackages = (List<Object[]>) 
					this.progPackageManager.queryByProgramFileId(
							encryptProgVo.getProgramFileId());
			for (Object[] objects : progPackages) {
				if (1 > Long.valueOf(objects[2].toString())) {
					cmsLog.info(" 主文件已加密, 节目包状态仍然小于缓存库: " + objects[2] 
							+ "\n\t 修改节目包[" + objects[0] + "]的状态为缓存库!");
					ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(
							(String) objects[0]);
					progPackage.setState(1L);
					this.progPackageManager.update(progPackage);
				}
			}
			return null;
		} else if((0 == encryptProgVo.getProgPackageDealState() 
						|| 8 == encryptProgVo.getProgPackageDealState())
				&& 1 == encryptProgVo.getProgPackageState()) {
			List<Object[]> encryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
					this.ENCRYPTSTORAGECLASSCODE, encryptProgVo.getProgramFileCode());
			Object[] encryptObjects = encryptList.get(0);
			AmsStorage encryptStorage = (AmsStorage) encryptObjects[0];
			AmsStorageDir encryptStorageDir = (AmsStorageDir) encryptObjects[1];
			String encryptPath = AmsStoragePrgRelService.getPath(
					encryptStorage, encryptStorageDir, null);
			cmsLog.info(("H264".equals(encryptProgVo.getProgramFileCode()) 
					? "视频文件加扰库路径: " : "富媒体文件加扰库路径: ") + encryptPath);
			
			Object[] sourceObjects = this.amsstorageprgrelManager.queryByProgPackageAndClassCode(
					encryptProgVo.getProgramFileId(), encryptProgVo.getProgramFileCode(), 
					this.NEARONLINESTORAGECLASSCODE);
			AmsStorage sourceStorage = (AmsStorage) sourceObjects[0];
			AmsStorageDir sourceStorageDir = (AmsStorageDir) sourceObjects[1];
			AmsStoragePrgRel sourceStoragePrgRel = (AmsStoragePrgRel) sourceObjects[2];
			String sourcePath = AmsStoragePrgRelService.getPath(sourceStorage, 
					sourceStorageDir, sourceStoragePrgRel);
			cmsLog.info("节目包[" + encryptProgVo.getProgPackageId() + "]缓存库路径: " + sourcePath);
			
			encryptPath = encryptPath.substring(encryptPath.indexOf("@") + 1).replaceAll("/", "\\\\");
			sourcePath = sourcePath.substring(sourcePath.indexOf("@") + 1).replaceAll("/", "\\\\");
			org.dom4j.Document document = this.generateContentEncryptXmlDocument(encryptProgVo, 
					"\\\\" + encryptPath, "\\\\" + sourcePath);
			
			EncryptList encryptTask = new EncryptList();
			encryptTask.setProductid(encryptProgVo.getProgPackageId());
			encryptTask.setProductname(encryptProgVo.getProgPackageName());
			encryptTask.setProgfileid(encryptProgVo.getProgramFileId());
			encryptTask.setTaskprio("1"); // 优先级
			encryptTask.setEncrypttype("H264".equals(encryptProgVo.getProgramFileCode()) ? 0L : 1L); // 加扰类别（0文件1文件夹）
			encryptTask.setName(encryptProgVo.getProgramFileName());
			encryptTask.setKeyfiledestpath("");
			encryptTask.setGroupname("");
			encryptTask.setContentxml(document.asXML().replaceFirst(
					"\\?>", " standalone=\"yes\"?>"));
			encryptTask.setDate4(new Date());
			encryptTask.setDate5(null); // 由加扰模块修改，加扰结束时间
			encryptTask.setDealstate(0L); // 处理状态（0未处理1下发任务8成功9失败）
			encryptTask.setDealinfo(""); // 由加扰模块修改
			encryptTask.setScip(""); // 由加扰模块修改
			encryptTask.setState2(0L); // 状态2[0: 未处理; 1: 已分配; 2: 处理中; 3:成功; 4: 失败]
			encryptTask.setInputmanid(inputManId);
			encryptTask.setInputtime(new Date());
			encryptTask.setRemark("0"); // 格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;
			this.encryptListManager.save(encryptTask);
			
			ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(
					encryptProgVo.getProgPackageId());
			progPackage.setState(1L);
			progPackage.setDealstate(1L);
			this.progPackageManager.update(progPackage);
			return null;
		} else {
			return "节目包加密失败!";
		}
	}
	
	/**
	 * 产品加密, 为节目包下产品加密任务
	 * @param progPackageId 节目包ID
	 * @param productInfo 产品信息表对象
	 * @param inputMainId 操作人员ID
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String encryptProduct(EncryptProgVo encryptProgVo, TProductInfo productInfo, 
			String inputMainId) throws Exception {
//		if (0 != encryptProgVo.getProgPackageDealState()) {
//			return " 节目包不是未处理状态! ";
//		}
		
//		// 生成key文件记录, 默认设为无效, 目的是要先生成出key文件ID
//		ProgListFile progListFile = new ProgListFile();
//		progListFile.setDate1(new Date());
//		progListFile.setFiletype(5L);
//		progListFile.setState1(1L);
//		progListFile.setInputmanid(inputMainId);
//		progListFile.setInputtime(new Date());
//		progListFile = (ProgListFile) this.progListFileManager.save(progListFile);
		
		List<Object[]> encryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
				this.ENCRYPTSTORAGECLASSCODE, this.KEYFILECODE);
		Object[] encryptObjects = encryptList.get(0);
		AmsStorage encryptStorage = (AmsStorage) encryptObjects[0];
		AmsStorageDir encryptStorageDir = (AmsStorageDir) encryptObjects[1];
		String keyPath = AmsStoragePrgRelService.getPath(
				encryptStorage, encryptStorageDir, null);
		keyPath = keyPath.substring(keyPath.indexOf("@") + 1).replaceAll("/", "\\\\");
		keyPath = "\\\\" + keyPath + encryptProgVo.getProgPackageId().substring(0, 10) 
				+ "\\" + encryptProgVo.getProgPackageId()
				+ "\\" + productInfo.getId() + "\\key";
		
		/**
		 * 生成产品加密Xml
		 */
		org.dom4j.Document document = this.generateProductEncryptXmlDocument(
				encryptProgVo, productInfo, keyPath);
		
		EncryptList encryptTask = new EncryptList();
		encryptTask.setProductid(encryptProgVo.getProgPackageId());
		encryptTask.setProductname(encryptProgVo.getProgPackageName());
		encryptTask.setProgfileid(encryptProgVo.getProgramFileId());
		encryptTask.setTaskprio("1"); // 优先级
		encryptTask.setEncrypttype("H264".equals(encryptProgVo.getProgramFileCode()) ? 0L : 1L); // 加扰类别（0文件1文件夹）
		encryptTask.setName(productInfo.getId());		// 产品信息表ID
		encryptTask.setKeyfiledestpath(keyPath);
		encryptTask.setGroupname(productInfo.getId());
		encryptTask.setContentxml(document.asXML().replaceFirst(
				"\\?>", " standalone=\"yes\"?>"));
		encryptTask.setDate4(new Date());
		encryptTask.setDate5(null); // 由加扰模块修改，加扰结束时间
		encryptTask.setDealstate(0L); // 处理状态（0未处理1下发任务8成功9失败）
		encryptTask.setDealinfo(""); // 由加扰模块修改
		encryptTask.setScip(""); // 由加扰模块修改
		encryptTask.setState2(0L); // 状态2[0: 未处理; 1: 已分配; 2: 处理中; 3:成功; 4: 失败]
		encryptTask.setInputmanid(inputMainId);
		encryptTask.setInputtime(new Date());
		encryptTask.setRemark("1"); // 格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;
		this.encryptListManager.save(encryptTask);
		
		// 此处加扰任务记录保存后修改产品信息表的key文件ID值. 
		productInfo.setEncryptState(1L);
		this.productInfoManager.update(productInfo);
		return null;
	}
	
	/**
	 * 用于前台调用, 为节目包下内容加扰任务
	 * @param progPackageId 节目包ID列表
	 * @param inputMainId 操作人员ID
	 * @throws Exception 如果下加扰任务失败则抛出异常
	 */
	public String encryptContent(List<String> progPackageIds, String inputMainId) throws Exception {
		int count = 0;
		String resultString = "生成内容加扰任务成功{0}个, 失败{1}个. {2}";
		String tmpString = "失败节目包: ";
		String result = null;
		for (String string : progPackageIds) {
			try {
				result = this.encryptContent(string, inputMainId);
				if (null != result) {
					++ count;
					tmpString += string + ";\n";
				}
			} catch (Exception e) {
				++ count;
				tmpString += string + ";\n";
				cmsLog.error(" 为节目包下内容加扰任务失败: " + e);
			}
		}
		
		if (count == 0) {
			result = "生成内容加扰任务成功!";
			return result;
		} else if (count > 0 && count < progPackageIds.size()) {
			result = MessageFormat.format(resultString,
					progPackageIds.size() - count, count, tmpString);
			throw new Exception(result);
		} else {
			result = "生成内容加扰任务失败!";
			throw new Exception(result);
		}
	}
	
	/**
	 * 为节目包下产品加密任务
	 * @param progPackageId
	 * @param productInfoId
	 * @param inputMainId
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private String encryptProduct(String progPackageId, String productInfoId, 
			String inputManId) {
		TProductInfo productInfo = this.productInfoManager.getTProductInfoById(productInfoId);
		EncryptProgVo encryptProgVo = null;
		if (null == productInfo) {
			return " 产品( " + productInfoId + " )信息不存在, 请刷新页面重新选择产品后再进行产品加密! \n";
		} else {
			encryptProgVo = this.progPackageManager.queryProgramFilesVoByProgPackageId(
					productInfo.getProgPackageId());
			if(null == encryptProgVo) {
				return " 产品( " + productInfoId + " )的节目或节目包不存在! \n";
			} else if(null != productInfo.getKeyFileId()) {
				return " 产品( " + productInfoId + " )已加密, 不需重复加密! \n";
			} else if(1 == productInfo.getEncryptState()) {
				return " 产品( " + productInfoId 
						+ " )正在进行加密中, 请稍后进行操作! \n";
			} else if(9 == productInfo.getEncryptState()) {
				return " 产品( " + productInfoId
						+ " )已成功加密, 请勿重复操作! \n";
			} else if(7 == productInfo.getEncryptState()) {
				// 如果是key复制失败则只需要复制key文件到一级库, 
				// 并判断位置表(包含加扰库和一级库)记录是否存在. 
				// 如果存在则不操作. 如果不存在则需要写位置表记录
				/**
				 * 产品加密完成后, 需要复制key文件到一级库, 并且写key文件所在一级库的位置表记录
				 */
				List<Object[]> caOnlineEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
						this.ENCRYPTSTORAGECLASSCODE, this.KEYFILECODE);
				Object[] caOnlineEncryptObjects = caOnlineEncryptList.get(0);
				AmsStorage caOnlineEncryptStorage = (AmsStorage) caOnlineEncryptObjects[0];
				AmsStorageDir caOnlineEncryptStorageDir = (AmsStorageDir) caOnlineEncryptObjects[1];
				String caOnlineKeyFileName = AmsStoragePrgRelService.getPath(
						caOnlineEncryptStorage, caOnlineEncryptStorageDir, null) 
						+ encryptProgVo.getProgPackageId() + "/"
						+ productInfoId + "/key";
				
				List<Object[]> onlineEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
						this.ENCRYPTSTORAGECLASSCODE, this.KEYFILECODE);
				Object[] onlineEncryptObjects = onlineEncryptList.get(0);
				AmsStorage onlineEncryptStorage = (AmsStorage) onlineEncryptObjects[0];
				AmsStorageDir onlineEncryptStorageDir = (AmsStorageDir) onlineEncryptObjects[1];
				
				String onlineKeyFileName = AmsStoragePrgRelService.getPath(
						onlineEncryptStorage, onlineEncryptStorageDir, null)
						+ encryptProgVo.getProgPackageId() + "/"
						+ productInfoId + "/key";
				
				int copyFileSucessful = fileopr.copyFileFromSmbToSmb(caOnlineKeyFileName, onlineKeyFileName);
				if (0 == copyFileSucessful) {
					/**
					 * 1. 复制key文件成功后写一级库key文件的位置表记录
					 */
					AmsStoragePrgRel onlineAmsStoragePrgRel = new AmsStoragePrgRel();
					onlineAmsStoragePrgRel.setStglobalid(onlineEncryptStorage.getStglobalid());
					onlineAmsStoragePrgRel.setStdirglobalid(onlineEncryptStorageDir.getStdirglobalid());
					onlineAmsStoragePrgRel.setPrglobalid(progPackageId);
					onlineAmsStoragePrgRel.setUploadtime(new Date());
					onlineAmsStoragePrgRel.setInputmanid(inputManId);
					onlineAmsStoragePrgRel.setInputtime(new Date());
					onlineAmsStoragePrgRel.setFtypeglobalid("KEY");
					onlineAmsStoragePrgRel.setFilename("key");
					onlineAmsStoragePrgRel.setProgfileid(productInfoId);
					onlineAmsStoragePrgRel.setFilepath(encryptProgVo.getProgPackageId().substring(0, 10)
							+ "/" + encryptProgVo.getProgPackageId() + "/"
							+ productInfoId + "/");
					onlineAmsStoragePrgRel.setRemark("产品加密成功");
					this.amsstorageprgrelManager.save(onlineAmsStoragePrgRel);
					/**
					 * 2. 修改产品信息的加密状态为9[成功]
					 */
					productInfo.setEncryptState(9L);
					this.productInfoManager.update(productInfo);
					return null;
				} else {
					return "产品( " + productInfoId + " )下加扰任务失败!";
				}
			} else  {
				try {
					return this.encryptProduct(encryptProgVo, productInfo, inputManId);
				} catch (Exception e) {
					cmsLog.error(" 为节目包下产品加扰任务失败, 产品ID[" + productInfoId + "]: " + e);
					return " 产品( " + productInfoId + " )下加扰任务失败! \n";
				}
			}
		}
	}
	
	/**
	 * HuangBo update by 2011年1月10日 17时19分 修改为1.11版加扰
	 * 用于前台调用, 为节目包下产品加密任务
	 * @param productInfos 产品ID集合
	 * @param inputManId 操作人员ID
	 * @return 返回产品加扰结果信息
	 * @throws Exception
	 */
	public String encryptProduct(List<String> productInfos, String inputManId)
			throws Exception {
		if (productInfos.contains(null)) {
			return "您所选的节目包中存在未绑定包月产品的节目包!";
		}
		int count = 0;
		String tmp = "";
		String resultString = "生成产品加扰任务成功{0}个, 失败{1}个. \n{2}";
		for (String string : productInfos) {
			/**
			 * HuangBo update by 2011年1月12日 10时22分
			 * String result = this.encryptProduct("", string, inputManId);
			 */
			String result = this.checkEncrypt(string, inputManId);
			if(null != result) {
				++ count;
				tmp += result;
			}
		}
		
		if (count == 0) {
			resultString = "生成产品加扰任务成功!";
			return resultString;
		} else if (count > 0 && count <= productInfos.size()) {
			return MessageFormat.format(resultString,
					productInfos.size() - count, count, tmp);
		} else {
			resultString = "生成产品加扰任务失败!";
			throw new Exception(resultString);
		}
	}
	
	/**
	 * 内容加扰完成时调用的接口
	 * @param progPackageId 节目包ID
	 * @param encryptResult 加扰结果
	 * @param encryptType 加扰类型[0: 内容加密, 1: 产品加密]
	 * @param productInfoId 产品信息ID
	 * @param inputMainId 操作人员ID
	 */
	@SuppressWarnings("unchecked")
	public void finishContentEncrypt(String progPackageId, int encryptResult,
			String encryptType, String productInfoId, String inputManId) {
		EncryptProgVo encryptProgVo = this.progPackageManager.
				queryProgramFilesVoByProgPackageId(progPackageId);
		Date fileDate = new Date();
		/**
		 * 判断加密类型, 写加扰库位置表记录
		 * 1: 如果是内容加密写主文件的位置表记录.
		 * 2: 如果是产品加密写KEY文件的位置表记录
		 */
		boolean isContentEncrypt = "0".equals(encryptType);	

		if (encryptResult == 8) {
			
			List<Object[]> caOnlineEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
					this.ENCRYPTSTORAGECLASSCODE, isContentEncrypt 
							? encryptProgVo.getProgramFileCode() : this.KEYFILECODE);
			Object[] caOnlineEncryptObjects = caOnlineEncryptList.get(0);
			AmsStorage caOnlineEncryptStorage = (AmsStorage) caOnlineEncryptObjects[0];
			AmsStorageDir caOnlineEncryptStorageDir = (AmsStorageDir) caOnlineEncryptObjects[1];
			
			// 加扰库
			AmsStoragePrgRel caonlineAmsStoragePrgRel = new AmsStoragePrgRel();
			caonlineAmsStoragePrgRel.setStglobalid(caOnlineEncryptStorage.getStglobalid());
			caonlineAmsStoragePrgRel.setStdirglobalid(caOnlineEncryptStorageDir.getStdirglobalid());
			caonlineAmsStoragePrgRel.setPrglobalid(progPackageId);
			caonlineAmsStoragePrgRel.setUploadtime(fileDate);
			caonlineAmsStoragePrgRel.setInputmanid(inputManId);
			caonlineAmsStoragePrgRel.setInputtime(fileDate);
			caonlineAmsStoragePrgRel.setFiledate(fileDate);
			
			/**
			 * 产品加密完成后, 需要复制key文件到一级库, 并且写key文件所在一级库的位置表记录
			 */
			List<Object[]> onlineEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
					this.ONLINESTORAGECLASSCODE, this.KEYFILECODE);
			Object[] onlineEncryptObjects = onlineEncryptList.get(0);
			AmsStorage onlineEncryptStorage = (AmsStorage) onlineEncryptObjects[0];
			AmsStorageDir onlineEncryptStorageDir = (AmsStorageDir) onlineEncryptObjects[1];
			// 一级库
			AmsStoragePrgRel onlineAmsStoragePrgRel = new AmsStoragePrgRel();
			
			if (isContentEncrypt) {
				ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(
						progPackageId);
				/**
				 * 如果节目包状态不是缓存库处理中状态, 则不做内容加扰完成反馈. 过滤脏数据.
				 */
				if (1 != progPackage.getDealstate()
						|| 1 != progPackage.getState()) {
					cmsLog.info("节目包状态不是缓存库处理中状态[1, 1], 则不做内容加扰完成反馈. 有可能数据不正确.");
					return;
				}
				
				String caOnlineMainFileName = AmsStoragePrgRelService.getPath(
						caOnlineEncryptStorage, caOnlineEncryptStorageDir, null) 
						+ encryptProgVo.getProgramFileId().substring(0, 10) + "/" 
						+ encryptProgVo.getProgramFileId() + "/"
						+ encryptProgVo.getProgramFileName();
				
				try {
					SmbFile caonlineMainSmbFile = new SmbFile(caOnlineMainFileName);
					if (caonlineMainSmbFile.exists()) {

						// 内容加密
						caonlineAmsStoragePrgRel.setFtypeglobalid(encryptProgVo.getProgramFileTypeId());
						caonlineAmsStoragePrgRel.setFilename(encryptProgVo.getProgramFileName());
						caonlineAmsStoragePrgRel.setProgfileid(encryptProgVo.getProgramFileId());
						caonlineAmsStoragePrgRel.setFilepath(encryptProgVo.getProgramFileId().substring(0, 10) + "/" 
								+ encryptProgVo.getProgramFileId());
						caonlineAmsStoragePrgRel.setRemark("内容加密已成功!");
						
						/**
						 * 1. 内容加密成功, 修改节目包状态为加扰库
						 */
						progPackage.setState(2L);
						progPackage.setDealstate(0L);
						this.progPackageManager.update(progPackage);
						
						this.amsstorageprgrelManager.save(caonlineAmsStoragePrgRel);
						
						/**
						 * 2. 加扰成功后, 修改文件表的已加密标识
						 */
						String packageMainFileId = this.programFilesManager.queryProgPackageMainFileId(
								progPackageId);
						ProgramFiles programFiles = (ProgramFiles) this.programFilesManager.getById(
								packageMainFileId);
						programFiles.setEncodestatus(1L);
						this.programFilesManager.update(programFiles);
					} else {
						String str = "加扰反馈成功, 但文件不存在!";
						progPackage.setState(1L);
						progPackage.setDealstate(8L);
						progPackage.setRemark(str);
						progPackageManager.update(progPackage);
						cmsLog.info(str + " 节目包状态已修改为“失败”。节目包ID：" + progPackageId);
					}
				} catch (MalformedURLException e) {
					cmsLog.error(e);
				} catch (SmbException e) {
					cmsLog.error(e);
				}
			} else {
				// 产品加密
				caonlineAmsStoragePrgRel.setFtypeglobalid("KEY");
				caonlineAmsStoragePrgRel.setFilename("key");
				caonlineAmsStoragePrgRel.setProgfileid(productInfoId);
				caonlineAmsStoragePrgRel.setFilepath(encryptProgVo.getProgPackageId().substring(0, 10)
						+ "/" + encryptProgVo.getProgPackageId() + "/"
						+ productInfoId + "/");
				caonlineAmsStoragePrgRel.setRemark("产品加密成功");
				
				onlineAmsStoragePrgRel.setStglobalid(onlineEncryptStorage.getStglobalid());
				onlineAmsStoragePrgRel.setStdirglobalid(onlineEncryptStorageDir.getStdirglobalid());
				onlineAmsStoragePrgRel.setPrglobalid(progPackageId);
				onlineAmsStoragePrgRel.setUploadtime(new Date());
				onlineAmsStoragePrgRel.setInputmanid(inputManId);
				onlineAmsStoragePrgRel.setInputtime(new Date());
				onlineAmsStoragePrgRel.setFtypeglobalid("KEY");
				onlineAmsStoragePrgRel.setFilename("key");
				onlineAmsStoragePrgRel.setProgfileid(productInfoId);
				onlineAmsStoragePrgRel.setFilepath(encryptProgVo.getProgPackageId().substring(0, 10)
						+ "/" + encryptProgVo.getProgPackageId() + "/"
						+ productInfoId + "/");
				onlineAmsStoragePrgRel.setRemark("产品加密成功");
				onlineAmsStoragePrgRel.setFiledate(fileDate);
				
				
				String caOnlineKeyFileName = AmsStoragePrgRelService.getPath(
						caOnlineEncryptStorage, caOnlineEncryptStorageDir, null) 
						+ caonlineAmsStoragePrgRel.getFilepath() + "/key";
				String onlineKeyFileName = AmsStoragePrgRelService.getPath(
						onlineEncryptStorage, onlineEncryptStorageDir, null)
						+ onlineAmsStoragePrgRel.getFilepath() + "/key";
				
				TProductInfo productInfo = this.productInfoManager.getTProductInfoById(productInfoId);
				
				try {
					SmbFile caOnlineKeySmbFile = new SmbFile(caOnlineKeyFileName);
					if (caOnlineKeySmbFile.exists()) {
						this.amsstorageprgrelManager.save(caonlineAmsStoragePrgRel);

						int copyFileState = fileopr.copyFileFromSmbToSmb(caOnlineKeyFileName, onlineKeyFileName);
						
						if (0 == copyFileState) {
							/**
							 * Key文件复制成功, 写一级库的key文件位置表记录
							 */
							this.amsstorageprgrelManager.save(onlineAmsStoragePrgRel);
							cmsLog.info("复制Key文件成功: " + caOnlineKeyFileName + " -=> " + onlineKeyFileName);
							productInfo.setEncryptState(9L);
							productInfo.setKeyFileId(productInfoId);
							productInfo.setKeyUpdateTime(new Date());
						} else {
							/**
							 * 复制失败, 修改产品信息的加密状态为[7], 产品加密复制key文件到一级库失败.
							 */
							productInfo.setEncryptState(7L);
							productInfo.setKeyFileId(productInfoId);
							productInfo.setKeyUpdateTime(fileDate);
							productInfo.setRemark("复制Key文件到一级库失败!");
							cmsLog.info("复制Key文件失败: " + caOnlineKeyFileName + " -=> " + onlineKeyFileName);
						}
						
						this.productInfoManager.update(productInfo);
					} else {
						// 加扰反馈成功, 但key文件不存在. 修改加密状态为8失败
						productInfo.setEncryptState(8L);
						productInfo.setRemark("加扰库Key文件不存在!");
						this.productInfoManager.update(productInfo);
					}
				} catch (MalformedURLException e) {
					cmsLog.error(e);
				} catch (SmbException e) {
					cmsLog.error(e);
				}
			}
		} else if (encryptResult == 9) {
			// 返回失败
			if (isContentEncrypt) {
				// 修改节目包的处理状态为“失败”，
				// 但是，不修改节目包的状态
				// 处理状态(0未处理1处理8失败9成功)
				ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(
						progPackageId);
				String str = "加扰结果是“失败”，准备修改节目包的处理状态为“失败”。";
				progPackage.setDealstate(8L);
				progPackageManager.update(progPackage);
				cmsLog.info("节目包状态已修改为“失败”。节目包ID：" + progPackageId);
			} else {
				TProductInfo productInfo = this.productInfoManager.getTProductInfoById(productInfoId);
				productInfo.setEncryptState(8L);
				this.productInfoManager.update(productInfo);
			}
		} else {
			// 返回失败
			if (isContentEncrypt) {
				ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(
						progPackageId);
				String str = "加扰结果未知，返回失败。加扰结果代码：" + encryptResult;
				cmsLog.info(str);
				progPackage.setDealstate(8L);
				progPackageManager.update(progPackage);
				cmsLog.info("节目包状态已修改为“失败”。节目包ID：" + progPackageId);
			} else {
				TProductInfo productInfo = this.productInfoManager.getTProductInfoById(productInfoId);
				productInfo.setEncryptState(8L);
				this.productInfoManager.update(productInfo);
			}
		}
	}
	
	/*------- 1.11 修改 2011年1月10日 17时21分 -------*/
	@SuppressWarnings("unchecked")
	private String checkEncrypt(String productInfoId, String inputManId) {
		TProductInfo productInfo = this.productInfoManager.getTProductInfoById(productInfoId);
		EncryptProgVo encryptProgVo = null;
		if (null == productInfo) {
			return " 产品( " + productInfoId + " )信息不存在, 请刷新页面重新选择产品后再进行产品加密! \n";
		} else {
			encryptProgVo = this.progPackageManager.queryProgramFilesVoByProgPackageId(
					productInfo.getProgPackageId());
			if(null == encryptProgVo) {
				return " 产品( " + productInfoId + " )的节目或节目包不存在! \n";
			} else if (2 > encryptProgVo.getProgramInfoDsFlag()) {
				return " 节目包实体文件未导入到缓存库, 不能进行内容加密! ";
			} else if (1 == encryptProgVo.getProgPackageDealState()) {
				return " 节目包是处理中状态, 不能进行内容加密! ";
//			} else if (1 == encryptProgVo.getProgramFileEncodeStatus()) {
//				List<Object[]> progPackages = (List<Object[]>) 
//						this.progPackageManager.queryByProgramFileId(
//									encryptProgVo.getProgramFileId());
//				for (Object[] objects : progPackages) {
//					if (1 > Long.valueOf(objects[2].toString())) {
//						cmsLog.info(" 主文件已加密, 节目包状态仍然小于缓存库: " + objects[2] 
//								+ "\n\t 修改节目包[" + objects[0] + "]的状态为缓存库!");
//						ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(
//								(String) objects[0]);
//						progPackage.setState(1L);
//						this.progPackageManager.update(progPackage);
//					}
//				}
//				return null;
//			} else if() {
//				return " 产品( " + productInfoId + " )已加密, 不需重复加密! \n";
			} else if(1 == productInfo.getEncryptState()) {
				return " 产品( " + productInfoId 
						+ " )正在进行加密中, 请稍后进行操作! \n";
			} else if(9 <= productInfo.getEncryptState() && null != productInfo.getKeyFileId()) {
				return " 产品( " + productInfoId
						+ " )已成功加密, 请勿重复操作! \n";
			} else if(7 == productInfo.getEncryptState()) {
				// 如果是key复制失败则只需要复制key文件到一级库, 
				// 并判断位置表(包含加扰库和一级库)记录是否存在. 
				// 如果存在则不操作. 如果不存在则需要写位置表记录
				/**
				 * 产品加密完成后, 需要复制key文件到一级库, 并且写key文件所在一级库的位置表记录
				 */
				List<Object[]> caOnlineEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
						this.ENCRYPTSTORAGECLASSCODE, this.KEYFILECODE);
				Object[] caOnlineEncryptObjects = caOnlineEncryptList.get(0);
				AmsStorage caOnlineEncryptStorage = (AmsStorage) caOnlineEncryptObjects[0];
				AmsStorageDir caOnlineEncryptStorageDir = (AmsStorageDir) caOnlineEncryptObjects[1];
				String caOnlineKeyFileName = AmsStoragePrgRelService.getPath(
						caOnlineEncryptStorage, caOnlineEncryptStorageDir, null) 
						+ encryptProgVo.getProgPackageId().substring(0, 10) 
						+ "\\" + encryptProgVo.getProgPackageId() + "/"
						+ productInfoId + "/key";
				
				List<Object[]> onlineEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
						this.ENCRYPTSTORAGECLASSCODE, this.KEYFILECODE);
				Object[] onlineEncryptObjects = onlineEncryptList.get(0);
				AmsStorage onlineEncryptStorage = (AmsStorage) onlineEncryptObjects[0];
				AmsStorageDir onlineEncryptStorageDir = (AmsStorageDir) onlineEncryptObjects[1];
				
				String onlineKeyFileName = AmsStoragePrgRelService.getPath(
						onlineEncryptStorage, onlineEncryptStorageDir, null)
						+ encryptProgVo.getProgPackageId().substring(0, 10) 
						+ "\\" + encryptProgVo.getProgPackageId() + "/"
						+ productInfoId + "/key";
				
				int copyFileSucessful = fileopr.copyFileFromSmbToSmb(caOnlineKeyFileName, onlineKeyFileName);
				if (0 == copyFileSucessful) {
					/**
					 * 1. 复制key文件成功后写一级库key文件的位置表记录
					 */
					AmsStoragePrgRel onlineAmsStoragePrgRel = new AmsStoragePrgRel();
					onlineAmsStoragePrgRel.setStglobalid(onlineEncryptStorage.getStglobalid());
					onlineAmsStoragePrgRel.setStdirglobalid(onlineEncryptStorageDir.getStdirglobalid());
					onlineAmsStoragePrgRel.setPrglobalid(encryptProgVo.getProgPackageId());
					onlineAmsStoragePrgRel.setUploadtime(new Date());
					onlineAmsStoragePrgRel.setInputmanid(inputManId);
					onlineAmsStoragePrgRel.setInputtime(new Date());
					onlineAmsStoragePrgRel.setFtypeglobalid("KEY");
					onlineAmsStoragePrgRel.setFilename("key");
					onlineAmsStoragePrgRel.setProgfileid(productInfoId);
					onlineAmsStoragePrgRel.setFilepath(encryptProgVo.getProgPackageId().substring(0, 10)
							+ "/" + encryptProgVo.getProgPackageId() + "/"
							+ productInfoId + "/");
					onlineAmsStoragePrgRel.setRemark("产品加密成功");
					this.amsstorageprgrelManager.save(onlineAmsStoragePrgRel);
					/**
					 * 2. 修改产品信息的加密状态为9[成功]
					 */
					productInfo.setEncryptState(9L);
					productInfo.setKeyFileId(productInfoId);
					productInfo.setKeyUpdateTime(new Date());
					this.productInfoManager.update(productInfo);
					return null;
				} else {
					this.encrypt(encryptProgVo, productInfo, inputManId);
					return null;
				}
			} else  {
//				try {
//					return this.encryptProduct(encryptProgVo, productInfo, inputManId);
//				} catch (Exception e) {
//					cmsLog.error(" 为节目包下产品加扰任务失败, 产品ID[" + productInfoId + "]: " + e);
//					return " 产品( " + productInfoId + " )下加扰任务失败! \n";
//				}
				this.encrypt(encryptProgVo, productInfo, inputManId);
				return null;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void encrypt(EncryptProgVo encryptProgVo, TProductInfo productInfo, String inputManId){
		List<Object[]> sourceEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
				this.ENCRYPTSTORAGECLASSCODE, encryptProgVo.getProgramFileCode());
		Object[] sourceEncryptObjects = sourceEncryptList.get(0);
		AmsStorage sourceEncryptStorage = (AmsStorage) sourceEncryptObjects[0];
		AmsStorageDir sourceEncryptStorageDir = (AmsStorageDir) sourceEncryptObjects[1];
		String encryptPath = AmsStoragePrgRelService.getPath(
				sourceEncryptStorage, sourceEncryptStorageDir, null);
		cmsLog.info(("H264".equals(encryptProgVo.getProgramFileCode()) 
				? "视频文件加扰库路径: " : "富媒体文件加扰库路径: ") + encryptPath);
		
		Object[] sourceObjects = this.amsstorageprgrelManager.queryByProgPackageAndClassCode(
				encryptProgVo.getProgramFileId(), encryptProgVo.getProgramFileCode(), 
						this.NEARONLINESTORAGECLASSCODE);
		AmsStorage sourceStorage = (AmsStorage) sourceObjects[0];
		AmsStorageDir sourceStorageDir = (AmsStorageDir) sourceObjects[1];
		AmsStoragePrgRel sourceStoragePrgRel = (AmsStoragePrgRel) sourceObjects[2];
		String sourcePath = AmsStoragePrgRelService.getPath(sourceStorage, 
				sourceStorageDir, sourceStoragePrgRel);
		cmsLog.info("节目包[" + encryptProgVo.getProgPackageId() + "]缓存库路径: " + sourcePath);
		
		List<Object[]> keyEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
				this.ENCRYPTSTORAGECLASSCODE, this.KEYFILECODE);
		Object[] keyEncryptObjects = keyEncryptList.get(0);
		AmsStorage keyEncryptStorage = (AmsStorage) keyEncryptObjects[0];
		AmsStorageDir keyEncryptStorageDir = (AmsStorageDir) keyEncryptObjects[1];
		String keyPath = AmsStoragePrgRelService.getPath(
				keyEncryptStorage, keyEncryptStorageDir, null);
		keyPath = keyPath.substring(keyPath.indexOf("@") + 1).replaceAll("/", "\\\\");
		keyPath = "\\\\" + keyPath + encryptProgVo.getProgPackageId().substring(0, 10) 
				+ "\\" + encryptProgVo.getProgPackageId()
				+ "\\" + productInfo.getId() + "\\key";
		encryptPath = encryptPath.substring(encryptPath.indexOf("@") 
				+ 1).replaceAll("/", "\\\\");
		encryptPath = encryptPath.substring(0, encryptPath.lastIndexOf("\\", 
				encryptPath.length() - 2) + 1);
		// TODO 1.1 path
		CmsConfig cmsConfig = new CmsConfig();
		encryptPath = cmsConfig.getPropertyByName("MappingCaonline");
		sourcePath = cmsConfig.getPropertyByName("MappingNearonline") + 
				sourcePath.substring(sourcePath.indexOf(
						encryptProgVo.getProgramFileCode())).replaceAll("/", "\\\\");
		
		EncryptList encryptTask = new EncryptList();
		encryptTask.setProductid(encryptProgVo.getProgPackageId());
		encryptTask.setProductname(encryptProgVo.getProgPackageName());
		encryptTask.setProgfileid(encryptProgVo.getProgramFileId());
		encryptTask.setPorductlist(productInfo.getKeyId());
		encryptTask.setTaskprio(encryptProgVo.getProgPackageName().contains("报纸") 
				? "3" : "R".equals(encryptProgVo.getProgramInfoProgType()) 
						? "2" : "1"); // 优先级, 富媒体优先, 报纸更优先
		encryptTask.setEncrypttype("H264".equals(encryptProgVo.getProgramFileCode()) ? 0L : 1L); // 加扰类别（0文件1文件夹）
		encryptTask.setName(productInfo.getId());		// 产品信息表ID
		encryptTask.setKeyfiledestpath(keyPath);
		encryptTask.setGroupname(productInfo.getId());
		encryptTask.setDate4(new Date());
		encryptTask.setDate5(null); // 由加扰模块修改，加扰结束时间
		encryptTask.setDealstate(0L); // 处理状态（0未处理1下发任务8成功9失败）
		encryptTask.setDealinfo(""); // 由加扰模块修改
		encryptTask.setScip(""); // 由加扰模块修改
		encryptTask.setState2(0L); // 状态2[0: 未处理; 1: 已分配; 2: 处理中; 3:成功; 4: 失败]
		encryptTask.setInputmanid(inputManId);
		encryptTask.setInputtime(new Date());
		encryptTask.setRemark("1.11产品加密"); // 格式：目标存储ID|目标目录ID|目标文件路径|文件位置表主键LIST;
		this.encryptListManager.save(encryptTask);
		
		/**
		 * 生成产品加密Xml
		 */
		org.dom4j.Document document = this.generateEncrypt1_11XmlDocument(
				encryptProgVo, productInfo, encryptPath, sourcePath, 
				encryptTask.getEncryptid());
		
		encryptTask.setContentxml(document.asXML().replaceFirst(
				"\\?>", " standalone=\"yes\"?>"));
		this.encryptListManager.update(encryptTask);
		
		// 此处加扰任务记录保存后修改产品信息表的key文件ID值. 
		productInfo.setEncryptState(1L);
		this.productInfoManager.update(productInfo);
	}
	
	private org.dom4j.Document generateEncrypt1_11XmlDocument(
			EncryptProgVo encryptProgVo, TProductInfo productInfo,
			 String encryptPath, String sourcePath, String contentId) {
		ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(
				encryptProgVo.getProgPackageId());
		
		org.dom4j.Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("gb2312");
		org.dom4j.Element rootElement = document.addElement("namespace");
		rootElement.addAttribute("name", "PushVOD");
		rootElement.addAttribute("version", "1.0");
		rootElement.addAttribute("type", "content");
		
		org.dom4j.Element contentElement = rootElement.addElement("content");
		contentElement.addAttribute("id", Long.valueOf(contentId).toString());
		contentElement.addAttribute("name", "");//encryptProgVo.getProgPackageName());// 节目包名称
		contentElement.addAttribute("destroot", encryptPath);
		
		org.dom4j.Element casystemElement = contentElement.addElement("casystem");
		casystemElement.addAttribute("systemid", "0x4A02");
		org.dom4j.Element cdcadescElement = casystemElement.addElement("cdcadesc");
		cdcadescElement.addAttribute("securityflag", "0");
		cdcadescElement.addAttribute("keyid", productInfo.getKeyId());
		cdcadescElement.addAttribute("ippvid", "0");
		cdcadescElement.addAttribute("slotid", "0");
		cdcadescElement.addAttribute("price", "0");
		
		org.dom4j.Element rightdescElement = contentElement.addElement("rightdesc");
		rightdescElement.addAttribute("expireddate", null == progPackage.getSubscriberetime()
				? "2012-12-31" : DateUtil.getDateStr("yyyy-MM-dd", 
						progPackage.getSubscriberetime()));
		rightdescElement.addAttribute("watchtimes", "0");
		rightdescElement.addAttribute("watchduration", "0");
		rightdescElement.addAttribute("watchtraffic", "0");
		
		org.dom4j.Element groupElement = contentElement.addElement("group");
		groupElement.addAttribute("name", encryptProgVo.getProgramFileCode()
				+ "\\" +encryptProgVo.getProgramFileId().substring(0, 10) 
				+ "\\" + encryptProgVo.getProgramFileId() + "\\" 
				+ productInfo.getId());
		
		org.dom4j.Element fileOrFolderElement = null;
		if ("V".equals(encryptProgVo.getProgramInfoProgType())) {
			groupElement.addAttribute("keyfiledestpath", "KEY\\" 
					+ encryptProgVo.getProgPackageId().substring(0, 10)
					+ "\\" + encryptProgVo.getProgPackageId() + "\\"
					+ productInfo.getId());
			fileOrFolderElement = groupElement.addElement("file");
		} else {
			fileOrFolderElement = groupElement.addElement("folder");
		}
		
		fileOrFolderElement.addAttribute("name", encryptProgVo.getProgramFileName());
		fileOrFolderElement.addAttribute("encrypt", encryptCode.containsAll(
				Arrays.asList(productInfo.getKeyId().split(","))) ? "0" : encryptProgVo.getProgramFileProgRank().toString());
		fileOrFolderElement.addAttribute("location", sourcePath);
		
		return document;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void finishContentEncrypt1_11(String progPackageId, int encryptResult,
			String productInfoId, String inputManId) {
		boolean isCanMigration = false;
		ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(
				progPackageId);
		EncryptProgVo encryptProgVo = this.progPackageManager.
				queryProgramFilesVoByProgPackageId(progPackageId);
		Date fileDate = new Date();
		
		if (encryptResult == 8) {
			List<Object[]> mainFileCaonlineEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
					this.ENCRYPTSTORAGECLASSCODE, encryptProgVo.getProgramFileCode());
			List<Object[]> keyFileCaonlineEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
					this.ENCRYPTSTORAGECLASSCODE, this.KEYFILECODE);
			
			Object[] mainFileCaonlineEncryptObjects = mainFileCaonlineEncryptList.get(0);
			AmsStorage mainFileCaonlineEncryptStorage = 
					(AmsStorage) mainFileCaonlineEncryptObjects[0];
			AmsStorageDir mainFileCaonlineEncryptStorageDir = 
					(AmsStorageDir) mainFileCaonlineEncryptObjects[1];
			
			Object[] keyFileCaonlineEncryptObjects = keyFileCaonlineEncryptList.get(0);
			AmsStorage keyFileCaonlineEncryptStorage = 
					(AmsStorage) keyFileCaonlineEncryptObjects[0];
			AmsStorageDir keyFileCaonlineEncryptStorageDir = 
					(AmsStorageDir) keyFileCaonlineEncryptObjects[1];
			
			String mainFileCaonlineMainFileName = AmsStoragePrgRelService.getPath(
					mainFileCaonlineEncryptStorage, mainFileCaonlineEncryptStorageDir, null) 
					+ encryptProgVo.getProgramFileId().substring(0, 10) + "/" 
					+ encryptProgVo.getProgramFileId() + "/"
					+ productInfoId + "/"
					+ encryptProgVo.getProgramFileName();
			cmsLog.debug("加扰反馈查询主文件加扰库文件路径 : " + mainFileCaonlineMainFileName);
			
			String keyFileCaonlineKeyFileName = AmsStoragePrgRelService.getPath(
					keyFileCaonlineEncryptStorage, keyFileCaonlineEncryptStorageDir, null) 
					+ encryptProgVo.getProgPackageId().substring(0, 10)
					+ "/" + encryptProgVo.getProgPackageId() + "/"
					+ productInfoId + "/key";
			cmsLog.debug("加扰反馈查询Key文件加扰库文件路径 : " + keyFileCaonlineKeyFileName);
			
			try {
				TProductInfo productInfo = this.productInfoManager.getTProductInfoById(productInfoId);
				
				SmbFile mainCaonlineSmbFile = new SmbFile(mainFileCaonlineMainFileName);
				SmbFile keyCaonlineSmbFile = new SmbFile(keyFileCaonlineKeyFileName);
				if (mainCaonlineSmbFile.exists()) {
					cmsLog.debug("加扰成功, 主文件存在!");
					/**
					 * 如果节目包状态不是缓存库处理中状态, 则不做内容加扰完成反馈. 过滤脏数据.
					 */
					if (1 != productInfo.getEncryptState()
							|| 1 > progPackage.getState()) {
						cmsLog.info("节目包状态不是缓存库处理中状态[1, 1], 则不做内容加扰完成反馈. 有可能数据不正确.");
						return;
					}
					// 内容加密
					AmsStoragePrgRel mainFileCaonlineAmsStoragePrgRel = new AmsStoragePrgRel();
					mainFileCaonlineAmsStoragePrgRel.setStglobalid(
							mainFileCaonlineEncryptStorage.getStglobalid());
					mainFileCaonlineAmsStoragePrgRel.setStdirglobalid(
							mainFileCaonlineEncryptStorageDir.getStdirglobalid());
					mainFileCaonlineAmsStoragePrgRel.setPrglobalid(progPackageId);
					mainFileCaonlineAmsStoragePrgRel.setUploadtime(fileDate);
					mainFileCaonlineAmsStoragePrgRel.setInputmanid(inputManId);
					mainFileCaonlineAmsStoragePrgRel.setInputtime(fileDate);
					mainFileCaonlineAmsStoragePrgRel.setFiledate(fileDate);
					mainFileCaonlineAmsStoragePrgRel.setFtypeglobalid(
							encryptProgVo.getProgramFileTypeId());
					mainFileCaonlineAmsStoragePrgRel.setFilename(
							encryptProgVo.getProgramFileName());
					mainFileCaonlineAmsStoragePrgRel.setProgfileid(
							productInfoId);
					mainFileCaonlineAmsStoragePrgRel.setFilepath(
							encryptProgVo.getProgramFileId().substring(0, 10) + "/" 
							+ encryptProgVo.getProgramFileId()
							+ "/" + productInfoId + "/");
					mainFileCaonlineAmsStoragePrgRel.setRemark("内容加密已成功!");
					
					/**
					 * 1. 内容加密成功, 修改节目包状态为加扰库
					 */
//					progPackage.setState(2L);
					progPackage.setDealstate(0L);
					this.progPackageManager.update(progPackage);
					
					this.amsstorageprgrelManager.save(mainFileCaonlineAmsStoragePrgRel);
					
					/**
					 * 2. 加扰成功后, 修改文件表的已加密标识
					 */
					String packageMainFileId = this.programFilesManager.queryProgPackageMainFileId(
							progPackageId);
					ProgramFiles programFiles = (ProgramFiles) this.programFilesManager.getById(
							packageMainFileId);
					programFiles.setEncodestatus(1L);
					this.programFilesManager.update(programFiles);
					
					if ("V".equalsIgnoreCase(progPackage.getProgtype())
							&& !encryptCode.containsAll(
			        				Arrays.asList(productInfo.getKeyId().split(",")))
							&& keyCaonlineSmbFile.exists()) {
						AmsStoragePrgRel keyFileCaonlineAmsStoragePrgRel = new AmsStoragePrgRel();
						keyFileCaonlineAmsStoragePrgRel.setStglobalid(
								keyFileCaonlineEncryptStorage.getStglobalid());
						keyFileCaonlineAmsStoragePrgRel.setStdirglobalid(
								keyFileCaonlineEncryptStorageDir.getStdirglobalid());
						keyFileCaonlineAmsStoragePrgRel.setPrglobalid(progPackageId);
						keyFileCaonlineAmsStoragePrgRel.setUploadtime(fileDate);
						keyFileCaonlineAmsStoragePrgRel.setInputmanid(inputManId);
						keyFileCaonlineAmsStoragePrgRel.setInputtime(fileDate);
						keyFileCaonlineAmsStoragePrgRel.setFiledate(fileDate);
						keyFileCaonlineAmsStoragePrgRel.setFtypeglobalid("KEY");
						keyFileCaonlineAmsStoragePrgRel.setFilename("key");
						keyFileCaonlineAmsStoragePrgRel.setProgfileid(productInfoId);
						keyFileCaonlineAmsStoragePrgRel.setFilepath(
								encryptProgVo.getProgPackageId().substring(0, 10)
								+ "/" + encryptProgVo.getProgPackageId() + "/"
								+ productInfoId + "/");
						keyFileCaonlineAmsStoragePrgRel.setRemark("产品加密成功");
						
						/**
						 * 产品加密完成后, 需要复制key文件到一级库, 并且写key文件所在一级库的位置表记录
						 */
						List<Object[]> onlineEncryptList = this.amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
								this.ONLINESTORAGECLASSCODE, this.KEYFILECODE);
						Object[] onlineEncryptObjects = onlineEncryptList.get(0);
						AmsStorage onlineEncryptStorage = (AmsStorage) onlineEncryptObjects[0];
						AmsStorageDir onlineEncryptStorageDir = (AmsStorageDir) onlineEncryptObjects[1];
						
						String onlineKeyFileName = AmsStoragePrgRelService.getPath(
								onlineEncryptStorage, onlineEncryptStorageDir, null)
								+ encryptProgVo.getProgPackageId().substring(0, 10)
								+ "/" + encryptProgVo.getProgPackageId() + "/"
								+ productInfoId + "/key";
								
						AmsStoragePrgRel keyFileOnlineAmsStoragePrgRel = new AmsStoragePrgRel();
						keyFileOnlineAmsStoragePrgRel.setStglobalid(
								onlineEncryptStorage.getStglobalid());
						keyFileOnlineAmsStoragePrgRel.setStdirglobalid(
								onlineEncryptStorageDir.getStdirglobalid());
						keyFileOnlineAmsStoragePrgRel.setPrglobalid(progPackageId);
						keyFileOnlineAmsStoragePrgRel.setUploadtime(fileDate);
						keyFileOnlineAmsStoragePrgRel.setInputmanid(inputManId);
						keyFileOnlineAmsStoragePrgRel.setInputtime(fileDate);
						keyFileOnlineAmsStoragePrgRel.setFtypeglobalid("KEY");
						keyFileOnlineAmsStoragePrgRel.setFilename("key");
						keyFileOnlineAmsStoragePrgRel.setProgfileid(productInfoId);
						keyFileOnlineAmsStoragePrgRel.setFilepath(
								encryptProgVo.getProgPackageId().substring(0, 10)
								+ "/" + encryptProgVo.getProgPackageId() + "/"
								+ productInfoId + "/");
						keyFileOnlineAmsStoragePrgRel.setRemark("产品加密成功: " + fileDate.getTime());
						keyFileOnlineAmsStoragePrgRel.setFiledate(fileDate);
						this.amsstorageprgrelManager.save(keyFileCaonlineAmsStoragePrgRel);
		
						int copyFileState = fileopr.copyFileFromSmbToSmb(
								keyFileCaonlineKeyFileName, onlineKeyFileName);
						
						if (0 == copyFileState) {
							/**
							 * Key文件复制成功, 写一级库的key文件位置表记录
							 */
							this.amsstorageprgrelManager.save(keyFileOnlineAmsStoragePrgRel);
							cmsLog.debug("复制Key文件成功: " + keyFileCaonlineKeyFileName 
									+ " -=> " + onlineKeyFileName);
							productInfo.setEncryptState(9L);
							productInfo.setKeyFileId(productInfoId);
							productInfo.setKeyUpdateTime(new Date());
							/**
							 * 加扰成功, 可以自动迁移
							 */
							isCanMigration = true;
							
//							cmsLog.debug("加扰成功, 自动为该节目包[" + progPackage.getProductid() 
//									+ "" + progPackage.getProductname() + "]下迁移任务!");
//							List<ProgPackage> progPackages = new ArrayList<ProgPackage>();
//							progPackages.add(progPackage);
//							List<String> productInfoIds = new ArrayList<String>();
//							productInfoIds.add(productInfoId);
//							
//							this.migrationModuleManager.saveMigrationFromCaonlineToOnlineByProgpackages_123(
//									null, progPackages, productInfoIds, "自动生成任务");
						} else {
							/**
							 * 复制失败, 修改产品信息的加密状态为[7], 产品加密复制key文件到一级库失败.
							 */
							productInfo.setEncryptState(7L);
//							productInfo.setKeyFileId(productInfoId);
//							productInfo.setKeyUpdateTime(fileDate);
							productInfo.setRemark("复制Key文件到一级库失败!");
							cmsLog.info("复制Key文件失败: " + keyFileCaonlineKeyFileName 
									+ " -=> " + onlineKeyFileName);
						}
					} else {
						productInfo.setEncryptState(9L);
						productInfo.setKeyFileId(productInfoId);
						productInfo.setKeyUpdateTime(new Date());
						/**
						 * 加扰成功, 可以自动迁移
						 */
						isCanMigration = true;
					}
					
					this.productInfoManager.update(productInfo);
				} else {
					String str = "加扰反馈成功, 但文件不存在!";
					progPackage.setState(1L);
					progPackage.setDealstate(8L);
					progPackage.setRemark(str);
					progPackageManager.update(progPackage);
					cmsLog.info(str + " 节目包状态已修改为“失败”。节目包ID：" + progPackageId);
					// 加扰反馈成功, 但key文件不存在. 修改加密状态为8失败
					productInfo.setEncryptState(8L);
					productInfo.setRemark("加扰库Key文件不存在!");
					this.productInfoManager.update(productInfo);
				}
			} catch (MalformedURLException e) {
				cmsLog.error(e);
			} catch (SmbException e) {
				cmsLog.error(e);
			}
		} else if (encryptResult == 9) {
			// 返回失败
			// 修改节目包的处理状态为“失败”，
			// 但是，不修改节目包的状态
			// 处理状态(0未处理1处理8失败9成功)
			String str = "加扰结果是“失败”，准备修改节目包的处理状态为“失败”。";
			progPackage.setDealstate(8L);
			progPackageManager.update(progPackage);
			cmsLog.info("节目包状态已修改为“失败”。节目包ID：" + progPackageId);
			TProductInfo productInfo = this.productInfoManager.getTProductInfoById(productInfoId);
			productInfo.setEncryptState(8L);
			this.productInfoManager.update(productInfo);
		} else {
			// 返回失败
			String str = "加扰结果未知，返回失败。加扰结果代码：" + encryptResult;
			cmsLog.info(str);
			progPackage.setDealstate(8L);
			progPackageManager.update(progPackage);
			cmsLog.info("节目包状态已修改为“失败”。节目包ID：" + progPackageId);
			
			TProductInfo productInfo = this.productInfoManager.getTProductInfoById(productInfoId);
			productInfo.setEncryptState(8L);
			this.productInfoManager.update(productInfo);
		}
		
		if (isCanMigration) {
			/**
			 * 增加加扰完成延时两秒进行迁移
			 * HuangBo update by 2011年12月12日 11时15分
			 */
			long autoMigrationDelayTime = Long.valueOf(this.cmsConfig.getPropertyByName("AutoMigrationDelayTime"));
			try {
				Thread.sleep(autoMigrationDelayTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
				cmsLog.error("自动迁移延时失败: " + e);
			}
			
			cmsLog.debug("加扰成功, 自动为该节目包[" + progPackage.getProductid() 
					+ "" + progPackage.getProductname() + "]下迁移任务!");
			List<ProgPackage> progPackages = new ArrayList<ProgPackage>();
			progPackages.add(progPackage);
			List<String> productInfoIds = new ArrayList<String>();
			productInfoIds.add(productInfoId);
			this.migrationModuleManager.saveMigrationFromCaonlineToOnlineByProgpackages_123(
					null, progPackages, productInfoIds, "自动生成任务");
		}
	}
	
	public void autoAddEncryptTask() {
		int scheduleDays = Integer.valueOf(this.cmsConfig.getPropertyByName("ScheduleDays"));
		int autoEncryptDays = Integer.valueOf(this.cmsConfig.getPropertyByName("AutoEncryptDays"));
		String scheduleDate = DateUtil.getAfterScheduleDate(scheduleDays + autoEncryptDays);
		List<TProductInfo> productInfos = this.productInfoManager.queryProductInfosByScheduleDateStr(scheduleDate);
		cmsLog.debug("查询符合自动生成加扰任务数量: " + productInfos.size());
		try {
			for (TProductInfo productInfo : productInfos) {
				this.checkEncrypt(productInfo.getId(), "自动生成加扰任务");
			}
		} catch (Exception e) {
			cmsLog.error("自动生成加扰任务失败: " + e);
		}
	}
	
	/**-------- setter and getter -------**/
	public void setEncryptListManager(IEncryptListManager encryptListManager) {
		this.encryptListManager = encryptListManager;
	}

	public void setProgPackageManager(IProgPackageManager progPackageManager) {
		this.progPackageManager = progPackageManager;
	}

	public void setPackageFilesManager(IPackageFilesManager packageFilesManager) {
		this.packageFilesManager = packageFilesManager;
	}

	public void setProgramFilesManager(IProgramFilesManager programFilesManager) {
		this.programFilesManager = programFilesManager;
	}

	public void setProgListDetailManager(
			IProgListDetailManager progListDetailManager) {
		this.progListDetailManager = progListDetailManager;
	}

	public void setAmsstorageprgrelManager(
			IAmsStoragePrgRelManager amsstorageprgrelManager) {
		this.amsstorageprgrelManager = amsstorageprgrelManager;
	}

	public void setAmsstoragedirManager(IAmsStorageDirManager amsstoragedirManager) {
		this.amsstoragedirManager = amsstoragedirManager;
	}

	public void setCmsTransactionManager(
			ICmsTransactionManager cmsTransactionManager) {
		this.cmsTransactionManager = cmsTransactionManager;
	}

	public void test(String productInfoId, String inputManId) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(productInfoId);
		System.out.println(this.encryptProduct(list, inputManId));
	}
	
	public void testFinishEncrypt(String progPackageId, int encryptResult, String encryptType, 
			String productInfoId, String inputManId) throws Exception {
		//this.finishContentEncrypt(progPackageId, encryptResult, encryptType, productInfoId, inputMainId);
		//System.out.println(this.encryptContent(progPackageId, inputMainId));
		finishContentEncrypt1_11(progPackageId, encryptResult, productInfoId, inputManId);
		
	}
	
	public static void main(String[] args) throws DocumentException, MalformedURLException, SmbException {
//		String tmp = "\\tmo\\tmp\\";
//		System.out.println(tmp.length() + " " + tmp.lastIndexOf("\\", tmp.length() - 2));
//		
//		org.dom4j.Document document = DocumentHelper.parseText(
//					"<?xml version=\"1.0\" encoding=\"gb2312\" standalone=\"yes\"?><namespace name=\"PushVOD\" version=\"1.0\" type=\"content\"><content id=\"113\" name=\"不朽的园丁\" destroot=\"\\\\172.23.19.117\\cms\\CaOnline\\\"><casystem systemid=\"0x4A02\"><cdcadesc securityflag=\"0\" keyid=\"101\" ippvid=\"0\" slotid=\"0\" price=\"0\"/></casystem><rightdesc expireddate=\"2029-12-31\" watchtimes=\"0\" watchduration=\"0\" watchtraffic=\"0\"/><group name=\"H264\\PRVN201012\\PRVN20101207151759000781001001\\000000000001\" keyfiledestpath=\"KEY\\PPVP201012\\PPVP20101207154343000031\\000000000001\"><file name=\"PRVN20101207151759000781001001.ts\" encrypt=\"1\" location=\"\\\\172.23.19.117\\cms\\NearOnline\\H264\\PRVN201012\\PRVN20101207151759000781001001.ts\"/></group></content></namespace>"
//				);
//		org.dom4j.tree.DefaultAttribute attribute = 
//				(DefaultAttribute) document.selectSingleNode("//content/@destroot");
//		System.out.println(attribute.getText());
//		SmbFile smbFile = new SmbFile("smb://administrator:1@10.0.3.30/CaOnline/H264/PRVN201103/PRVN20110301132542000281001001/000000000005/PRVN20110301132542000281001001.ts");
//		System.out.println(smbFile.exists());
	}
}
