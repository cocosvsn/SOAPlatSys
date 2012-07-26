package com.soaplat.cmsmgr.managerimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageClass;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.manageriface.IAmsStorageClassManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.cmsmgr.bean.PackageFiles;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.dto.*;
import com.soaplat.cmsmgr.manageriface.IFileStyleManager;
import com.soaplat.cmsmgr.manageriface.IMigrationModuleManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListFileManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;


/**
 * 
 * @author Andy
 * 1.2版，根据直播星2010年下半年确定的1.2版需求开发的，使用的drm接口、push接口、终端接口都是1.2版
 * 1.23版，根据直播星2010年底确定的1.2版需求开发的，使用drm1.1版接口、push和终端1.2版接口
 * 1.2版，宣布不按计划上线使用，重新开发1.23版，2011年3月31日上线
 */

public class MigrationModuleManagerImpl implements IMigrationModuleManager {
	
	
	private IPackageFilesManager packageFilesManager;
	private IProgListDetailManager progListDetailManager;
	private IProgPackageManager progPackageManager;
	private IFileStyleManager fileStyleManager;
	private IAmsStorageManager amsstorageManager;
	private IAmsStorageClassManager amsstorageclassManager;
	private IAmsStoragePrgRelManager amsstorageprgrelManager;
	private IProgramFilesManager programFilesManager;
	private IAmsStorageDirManager amsstoragedirManager;
	private IPortalColumnManager portalColumnManager;
	private IProductInfoManager productinfoManager;
	
	private IProgramInfoManager programInfoManager;
	private IProgListFileManager progListFileManager;
	
	private static final Logger cmsLog = Logger.getLogger("Cms");
	private static FileOperationImpl fileopr = new FileOperationImpl();
	public static CmsConfig cc = new CmsConfig();
	
	
	/**
	 * 节目状态（dsflag）
	 * 	-1 - 未导入
	 * 	0 - 迁移中
	 * 	1 - 迁移失败
	 * 	2 - 新录入
	 * 	3 - 已包装
	 * 
	 * 节目包状态（ProgPackage --> state）
	 * 	-1 - 未导入
	 * 	0 - ** 导入区（Import），1.2不使用
	 * 	1 - 缓存库(NearOnline)
	 * 	2 - 加扰库(CaOnline)
	 * 	3 - 播发库(Online)
	 * 	9 - ** 在上海的北京缓存库(BjOnline)，1.2不使用
	 * 
	 * 节目包处理状态（ProgPackage --> dealstate）
	 *  -1 - 未导入
	 *  0 - 未处理
	 *  1 - 处理中
	 *  8 - 失败
	 *  9 - ** 成功，1.2不使用
	 * 
	 * 迁移类型（Type）
	 * 	0 - ** 数据导出@上海，1.2不使用
	 * 	1 - 迁移至播发库@北京
	 * 	2 - ** 数据导入@北京，1.2不使用
	 * 	3 - 节目录入@上海
	 */

	
	//---------------------- 重用方法 --------------------------------------------------------
	/**
	 * 20101112 15:00 获得迁移任务xml文件生成的目标路径，XML PATH(NO FILE NAME)
	 * （返回：目标路径、本地临时路径，都不包含文件名）
	 */
	@SuppressWarnings("unchecked")
	private List<String> getMigrationXmlFilepath(
			String filecodeMigration,
			String stclasscodeMigration,
			String migrationXmlTemp,
			String serverOS
			)
	{
		List<String> filepaths = new ArrayList<String>();
		String localfile = null;
		String destpathMigration = null;
		String dateDir = fileopr.convertDateToString(new Date(), "yyyy-MM");
		
		/**
		 * 查询迁移单xml文件目标路径
		 */
		List xmldestpaths = packageFilesManager.getDestPathByFilecodeStclasscode(
				filecodeMigration, 					// 迁移单xml的filecode
				stclasscodeMigration				// 迁移单的目标存储体等级code
				);
		if (xmldestpaths != null && xmldestpaths.size() >= 2) 
		{
			destpathMigration = (String) xmldestpaths.get(0);
			if (destpathMigration == null
				|| destpathMigration.equalsIgnoreCase("")) 
			{
				cmsLog.warn("迁移单目标路径不存在。");
				destpathMigration = null;
			} 
			else 
			{
				destpathMigration = fileopr.checkPathFormatRear(destpathMigration, '/');
				cmsLog.info("得到迁移单的目标路径 - " + destpathMigration);
				fileopr.checkSmbDir(destpathMigration);
			}
		} 
		else 
		{
			cmsLog.warn("迁移单目标路径不存在。");
			destpathMigration = null;
		}
		/**
		 * 查询迁移单xml文件本地临时目录
		 */
		if("LINUX".equalsIgnoreCase(serverOS.toUpperCase()))
		{
			localfile = "/";
		}
		else if("WINDOWS".equalsIgnoreCase(serverOS.toUpperCase()))
		{
			localfile = "C:/";
		}
		else
		{
			if(fileopr.checkFileExist("/"))
			{
				localfile = "/";
			}
			else if(fileopr.checkFileExist("C:/"))
			{
				localfile = "C:/";
			}
			else
			{
				localfile = null;
			}
		}
		if(localfile != null)
		{
			localfile = fileopr.checkPathFormatRear(localfile, '/');
			migrationXmlTemp = migrationXmlTemp.replace('\\', '/');
			localfile = localfile + migrationXmlTemp;
			localfile = fileopr.checkPathFormatRear(localfile, '/');
			localfile = localfile + dateDir + "/";
			fileopr.checkLocalDir(localfile);
		}
		else
		{
			cmsLog.warn("迁移单本地临时目录不存在。");
		}
		
		if(localfile != null
			&& destpathMigration != null
			)
		{
			filepaths.add(destpathMigration);
			filepaths.add(localfile);
		}
		else
		{
			filepaths = null;
		}
		
		return filepaths;
	}
	
	/**
	 * 20101115 10:18 生成迁移任务xml文件和数据库记录（Proglistfile）， XML Document --> XML File
	 * @param progListFileManager
	 * @param document
	 * @param destpathMigration
	 * @param localfile
	 * @param progListFile
	 * @return
	 */
	private int saveMigrationXmlFile(
			Document document,
			String destpathMigration,
			String localfile,
			ProgListFile progListFile
			)
	{
		/**
		 * 在本地临时目录生成迁移任务xml文件
		 * 在数据库（proglistfile）生成记录，状态无效（state1=1）
		 * 复制迁移单，从本地到目标
		 * 修改数据库状态，proglistfile和progpackage
		 */
		int ret = 1;	// 返回：0 - 成功； 1 - 失败
		String strxml = null;
		try
		{
			cmsLog.info("生成迁移任务单xml字符串...");
			cmsLog.info("本地临时目录：" + localfile);

			// 输出格式化器
			OutputFormat format = OutputFormat.createPrettyPrint();
			
			// 设置编码 - gb2312 / GBK / 
			format.setEncoding("UTF-8");
			
			// xml输出器
			StringWriter stringwriter = new StringWriter();
			XMLWriter stringout = new XMLWriter(stringwriter, format);
			stringout.write(document);
			stringout.flush();
			strxml = stringwriter.toString();
			
			XMLWriter fileout = new XMLWriter(new FileOutputStream(new File(localfile)), format);
			fileout.write(document);
			fileout.close();
		}
		catch(IOException e)
		{
			String str = "在本地临时目录生成播发单xml字符串异常：" + e.getMessage() + "\r\n";
			cmsLog.error(str);
			ret = 1;
			return 1;
		}
		
		/**
		 * 生成迁移任务xml，在 本地临时目录
		 */

		progListFile.setFiletype((long) 8); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
		progListFile.setState1((long) 1);
		progListFile.setState2((long) 0);
		progListFile.setColumnxml(strxml);
		progListFileManager.save(progListFile);
		cmsLog.info("ProgListFile记录已经保存，ID：" + progListFile.getColumnfileid());
		
		/**
		 * 复制迁移任务xml文件，
		 * 从 本地临时目录 
		 * 到 迁移接口目录
		 */
		
		fileopr.checkSmbDir(destpathMigration);
		if(fileopr.checkFileExist(localfile))
		{
			cmsLog.info("准备复制迁移任务单...");
			ret = fileopr.copyFileFromLocalToSmb(localfile, destpathMigration);
			if(ret == 0)
			{
				cmsLog.info("复制迁移任务单成功。");
				
				progListFile.setState1((long) 0);
				progListFileManager.update(progListFile);
				cmsLog.info("ProgListFile记录已经更新，state1 = 0。");
				ret = 0;
			}
			else
			{
				String str = "复制迁移任务单失败！\r\n";
				cmsLog.warn(str);
				ret = 1;
			}
		}
		else
		{
			String str = "本地迁移任务单不存在..." + localfile + "\r\n";
			cmsLog.warn(str);
			ret = 1;
		}
		
		return ret;
	}
	
	/**
	 * 20101109 14:43 解析迁移模块反馈xml，XML String --> TransferDistribution
	 */
	@SuppressWarnings("unchecked")
	private TransferDistribution dealXmlFromMigrationModule(String strXml)
	{
		cmsLog.info("Cms -> MigrationModuleManagerImpl -> dealXmlFromMigrationModule...");
		TransferDistribution transferDistribution = new TransferDistribution();
		
			
		// 为strXml补充头和根节点
		strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <Distribution>" + strXml;
		strXml += "</Distribution>";
		
		try 
		{
			Document document = DocumentHelper.parseText(strXml);

			List<TransferTask> transferTasks = new ArrayList<TransferTask>();
			TransferTask transferTask = new TransferTask();
			
			Element rootElement = document.getRootElement();
//			List<Element> elementList = rootElement.elements();
			Iterator iter = rootElement.elementIterator();
			if(iter != null)
			{
				while(iter.hasNext())
				{
					Element transferentityElement = (Element)iter.next();
					String eleName = transferentityElement.getName();
					/**
					 * HuangBo update by 2011年4月22日 19时5分
					 * 未使用, 注释
					 */
//					String eleValue = transferentityElement.getText();
					
					if("Transfer-Entity".equalsIgnoreCase(eleName))
					{
						Element sourceElement = transferentityElement.element("Source");
						Element destinationElement = transferentityElement.element("Destination");
						
						
						TransferEntity transferEntity = new TransferEntity();
						TransferSource transferSource = new TransferSource();
						TransferDestination transferDestination = new TransferDestination();
						String sourceProgid = transferentityElement.attributeValue("SourceProgid");
						String sourceProgTitle = transferentityElement.attributeValue("SourceProgTitle");
						String sourceFileId = transferentityElement.attributeValue("SourceFileId");
						String sourceFileName = transferentityElement.attributeValue("SourceFileName");
						String sourceStorageClass = transferentityElement.attributeValue("SourceStorageClass");
						String desStorageClass = transferentityElement.attributeValue("DesStorageClass");
						String sourceFileType = transferentityElement.attributeValue("SourceFileType");
						String sourceFileCode = transferentityElement.attributeValue("SourceFileCode");
						String priorityDate = transferentityElement.attributeValue("PriorityDate");
						String isCover = transferentityElement.attributeValue("IsCover");
						String mainFileTag = transferentityElement.attributeValue("MainFileTag");
						String productInfoId = transferentityElement.attributeValue("ProductInfoId");
						String progFileId = transferentityElement.attributeValue("ProgFileId");
						
						if(sourceElement != null)
						{
							String type = sourceElement.attributeValue("Type");
							String hostname = sourceElement.attributeValue("Hostname");
							String port = sourceElement.attributeValue("Port");
							String username = sourceElement.attributeValue("Username");
							String password = sourceElement.attributeValue("Password");
							String sourcestorageId = sourceElement.attributeValue("SourcestorageId");
							String sourceDirId = sourceElement.attributeValue("SourceDirId");
							String variableFilePath = sourceElement.attributeValue("VariableFilePath");
							String fileDate = sourceElement.attributeValue("FileDate");
							String file = sourceElement.elementText("File");
							String folder = sourceElement.elementText("Folder");
							
							transferSource.setType(type);
							transferSource.setHostname(hostname);
							transferSource.setPort(port);
							transferSource.setUsername(username);
							transferSource.setPassword(password);
							transferSource.setSourcestorageId(sourcestorageId);
							transferSource.setSourceDirId(sourceDirId);
							transferSource.setVariableFilePath(variableFilePath);
							transferSource.setFileDate(fileDate);
							transferSource.setFile(file);
							transferSource.setFolder(folder);
						}
						if(destinationElement != null)
						{
							String type = destinationElement.attributeValue("Type");
							String hostname = destinationElement.attributeValue("Hostname");
							String port = destinationElement.attributeValue("Port");
							String username = destinationElement.attributeValue("Username");
							String password = destinationElement.attributeValue("Password");
							String desStorageId = destinationElement.attributeValue("DesStorageId");
							String desStorageDirId = destinationElement.attributeValue("DesStorageDirId");
							String variableFilePath = destinationElement.attributeValue("VariableFilePath");
							String fileDate = destinationElement.attributeValue("FileDate");
							String file = destinationElement.elementText("File");
							String folder = destinationElement.elementText("Folder");
							
							transferDestination.setType(type);
							transferDestination.setHostname(hostname);
							transferDestination.setPort(port);
							transferDestination.setUsername(username);
							transferDestination.setPassword(password);
							transferDestination.setDesStorageId(desStorageId);
							transferDestination.setDesStorageDirId(desStorageDirId);
							transferDestination.setVariableFilePath(variableFilePath);
							transferDestination.setFileDate(fileDate);
							transferDestination.setFile(file);
							transferDestination.setFolder(folder);
						}
						
						
						transferEntity.setSourceProgid(sourceProgid);
						transferEntity.setSourceProgTitle(sourceProgTitle);
						transferEntity.setSourceFileId(sourceFileId);
						transferEntity.setSourceFileName(sourceFileName);
						transferEntity.setSourceStorageClass(sourceStorageClass);
						transferEntity.setDesStorageClass(desStorageClass);
						transferEntity.setSourceFileType(sourceFileType);
						transferEntity.setSourceFileCode(sourceFileCode);
						transferEntity.setPriorityDate(priorityDate);
						transferEntity.setIsCover(isCover);
						transferEntity.setTransferSource(transferSource);
						transferEntity.setTransferDestination(transferDestination);
						transferEntity.setMainFileTag(mainFileTag);
						transferEntity.setProductInfoId(productInfoId);
						transferEntity.setProgFileId(progFileId);
						
						transferTask.getTransferEntities().add(transferEntity);
					}	// if("Transfer-Entity".equalsIgnoreCase(eleName))
				}	// while(iter.hasNext())
			}	// if(iter != null)
			
			transferTasks.add(transferTask);
			transferDistribution.setTransferTasks(transferTasks);
		} 
		catch (Exception ex) 
		{
			cmsLog.error("解析迁移模块反馈xml异常：" + ex.getMessage());
			cmsLog.error(ex.toString());
			transferDistribution = null;
		}

		cmsLog.info("Cms -> MigrationModuleManagerImpl -> dealXmlFromMigrationModule returns.");
		return transferDistribution;
	}
	
	/**
	 * 20101112 14:19 生成迁移任务单(Document)，TransferDistribution -- > XML Document，不生成迁移xml文件
	 * @param transferDistribution
	 * @return
	 */
	public static Document generateXmlDoc(TransferDistribution transferDistribution)
	{
		Document document = null;
		if(transferDistribution != null)
		{
			document = DocumentHelper.createDocument();
			
			Element rootelement = document.addElement("Migration");						// 根节点
			rootelement.addComment(transferDistribution.getComment());					// 注释
			Element distributionElement = rootelement.addElement("Distribution");		// 品牌
			distributionElement.addAttribute("ProglistId", transferDistribution.getProglistId());
			distributionElement.addAttribute("RequestId", transferDistribution.getRequestId());
			distributionElement.addAttribute("CreateDate", transferDistribution.getCreateDate());
			distributionElement.addAttribute("Type", transferDistribution.getType());
			
			List<TransferTask> transferTasks = transferDistribution.getTransferTasks();
					
			for(int i = 0; i < transferTasks.size(); i++)
			{
				TransferTask transferTask = transferTasks.get(i);
				List<TransferEntity> transferEntities = transferTask.getTransferEntities();
				for(int j = 0; j < transferEntities.size(); j++)
				{
					TransferEntity transferEntity = transferEntities.get(j);
					TransferSource transferSource = transferEntity.getTransferSource();
					TransferDestination transferDestination = transferEntity.getTransferDestination();
					
					Element transferEntityElement = distributionElement.addElement("Transfer-Entity");
					transferEntityElement.addAttribute("SourceProgid", transferEntity.getSourceProgid());			// 节目包id
					transferEntityElement.addAttribute("SourceProgTitle", transferEntity.getSourceProgTitle());		// 节目包名称	
					transferEntityElement.addAttribute("SourceFileId", transferEntity.getSourceFileId());			// 文件id
					transferEntityElement.addAttribute("SourceFileName", transferEntity.getSourceFileName());			// 文件名称
					transferEntityElement.addAttribute("SourceFileType", transferEntity.getSourceFileType());		// filetype
					transferEntityElement.addAttribute("SourceFileCode", transferEntity.getSourceFileCode());			// filecode
					transferEntityElement.addAttribute("SourceStorageClass", transferEntity.getSourceStorageClass());	// 源存储等级code			
					transferEntityElement.addAttribute("DesStorageClass", transferEntity.getDesStorageClass());		// 目标存储等级code	
					transferEntityElement.addAttribute("PriorityDate", transferEntity.getPriorityDate());
					transferEntityElement.addAttribute("MainFileTag", transferEntity.getMainFileTag());
					transferEntityElement.addAttribute("ProductInfoId", transferEntity.getProductInfoId());
					transferEntityElement.addAttribute("ProgFileId", transferEntity.getProgFileId());
					transferEntityElement.addAttribute("IsCover", transferEntity.getIsCover());
					/**
					 * HuangBo addition by 2011年11月8日 16时30分
					 * 为升级迁移增加的属性无实际作用
					 */
					transferEntityElement.addAttribute("PriorityLevel", "0");
					
					Element SourceElement = transferEntityElement.addElement("Source");
					SourceElement.addAttribute("Type", transferSource.getType());
					SourceElement.addAttribute("Hostname", transferSource.getHostname());
					SourceElement.addAttribute("Port", transferSource.getPort());	
					SourceElement.addAttribute("Username", transferSource.getUsername());
					SourceElement.addAttribute("Password", transferSource.getPassword());
					SourceElement.addAttribute("SourcestorageId", transferSource.getSourcestorageId());
					SourceElement.addAttribute("SourceDirId", transferSource.getSourceDirId());
					SourceElement.addAttribute("VariableFilePath", transferSource.getVariableFilePath());
					SourceElement.addAttribute("FileDate", transferSource.getFileDate());
					if(transferSource.getFile() != null 
						&& !"".equalsIgnoreCase(transferSource.getFile())
						)
					{
						// 视频类节目包，主文件为文件
						Element sfileElement = SourceElement.addElement("File");
						sfileElement.setText(transferSource.getFile());
					}
					else
					{
						// 富媒体类节目包，主文件为文件夹
						Element sfolderElement = SourceElement.addElement("Folder");
						sfolderElement.setText(transferSource.getFolder());
					}
					
					Element destinationElement = transferEntityElement.addElement("Destination");
					destinationElement.addAttribute("Type", transferDestination.getType());
					destinationElement.addAttribute("Hostname", transferDestination.getHostname());
					destinationElement.addAttribute("Port", transferDestination.getPort());
					destinationElement.addAttribute("Username", transferDestination.getUsername());
					destinationElement.addAttribute("Password", transferDestination.getPassword());
					destinationElement.addAttribute("DesStorageId", transferDestination.getDesStorageId());
					destinationElement.addAttribute("DesStorageDirId", transferDestination.getDesStorageDirId());
					destinationElement.addAttribute("VariableFilePath", transferDestination.getVariableFilePath());
					destinationElement.addAttribute("FileDate", transferDestination.getFileDate());
					if(transferDestination.getFile() != null
						&& !"".equalsIgnoreCase(transferDestination.getFile())
						)
					{
						// 视频类节目包，主文件为文件
						Element dfileElement = destinationElement.addElement("File");
						dfileElement.setText(transferDestination.getFile());
					}
					else
					{
						// 富媒体类节目包，主文件为文件夹
						Element dfolderElement = destinationElement.addElement("Folder");
						dfolderElement.setText(transferDestination.getFolder());
					}
				}
				/**
				 * 目前每个文件作为一个transferTask，所以只循环一次
				 */
				break;
			}
		}
		return document;
	}

	/**
	 * 20101110 10:00 1.2 修改节目包的状态和处理状态
	 * @param progPackage
	 * @param fileStyleCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto updateRefreshStateOfProgPackage(
			ProgPackage progPackage,	// 节目包
			long fileStyleCode,			// 文件样式应用code
			String result				// 此次操作结果： "Y" - 成功； "N" - 失败
			)
	{
		cmsLog.info("Cms -> MigrationModuleManagerImpl -> updateRefreshStateOfProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		/**
		 * 		存储等级
		 * 1 - NearOnline
		 * 2 - CaOnline
		 * 3 - Online
		 * 
		 * 		节目包状态
		 * -1 - 未导入
		 * 0 - 导入 （不使用）
		 * 1 - 缓存库 ，所有文件
		 * 2 - 加扰库 ，主文件
		 * 3 - 播控库 ，根据文件样式，应用code2
		 * 9 - 在上海的北京缓存库（不使用）
		 * 
		 * 		处理状态
		 * 0 - 未处理
		 * 1 - 处理中
		 * 8 - 失败
		 * 9 - 成功（不使用）
		 */
		
		/**
		 * ***** 应用场景 *****
		 * 1 - 加扰完成(内容加密)
		 * 		缓存库、处理中 --> 加扰库、未处理
		 * 
		 * 2 - 迁移完成(迁移至播发库)
		 * 		加扰库、处理中 --> 播发库、未处理
		 * 
		 * 3 - 文件删除
		 * 		播发库、未处理 --> 加扰库、未处理
		 * 		加扰库、未处理 --> 缓存库、未处理
		 * 		缓存库、未处理 --> 未导入、未处理
		 * 
		 * 4 - 无主文件节目包的文件导入
		 * 		未导入、未处理 --> 缓存库、未处理
		 * 		
		 */
		
		Long state = (long)-1;				// 节目包状态
		Long dealState = (long)-1;			// 处理状态
		Long newState = (long)-1;			// 新的节目包状态
		Long newDealState = (long)0;		// 新的处理状态
		String queryStclasscode = null;		// 需要判断的存储等级code
//		Long queryState = (long)-1;			// 需要判断的节目包状态
		
		String stclasscodeNearOnline = "NearOnline"; 	// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline"; 		// 加扰库存储体等级code
		String stclasscodeOnline = "Online"; 			// 播控库存储体等级code
		/**
		 * HuangBo update by 2011年4月22日 19时5分
		 * 未使用, 注释
		 */
//		String stclasscodeBjOnline = "BjOnline"; 		// 在上海的北京缓存库存储体等级code，不使用
		
		if(progPackage != null)
		{
			state = progPackage.getState();
			dealState = progPackage.getDealstate();
			
			cmsLog.info("节目包状态：" + state);
			cmsLog.info("节目包处理状态：" + dealState);
			cmsLog.info("此次操作结果：" + result);
			
			if("Y".equalsIgnoreCase(result))
			{
				if(dealState == (long)0
					|| dealState == (long)8
					)
				{
					/**
					 * 未处理、失败
					 * 节目包状态回退（文件删除）
					 */
					if(state == (long)0)
					{
						cmsLog.info("此版本不处理。");
					}
					else if(state == (long)1)
					{
						newState = (long)-1;
						queryStclasscode = stclasscodeNearOnline;
					}
					else if(state == (long)2)
					{
						newState = (long)1;
						queryStclasscode = stclasscodeCaOnline;
					}
					else if(state == (long)3)
					{
						newState = (long)2;
						queryStclasscode = stclasscodeOnline;
					}
					else if(state == (long)9)
					{
						cmsLog.info("此版本不处理。");
					}
					else
					{
						cmsLog.info("此版本不处理。");
					}
				}
				else if(dealState == (long)1)
				{
					/**
					 * 处理中
					 * 节目包状态前进（加扰完成、迁移完成）
					 */
					if(state == (long)0)
					{
						cmsLog.info("此版本不处理。");
					}
					else if(state == (long)1)
					{
						newState = (long)2;
						queryStclasscode = stclasscodeCaOnline;
					}
					else if(state == (long)2)
					{
						newState = (long)3;
						queryStclasscode = stclasscodeOnline;
					}
					else if(state == (long)3)
					{
						cmsLog.info("此版本不处理。");
					}
					else if(state == (long)9)
					{
						cmsLog.info("此版本不处理。");
					}
					else if(state == (long)-1)
					{
						cmsLog.info("此版本不处理。");
					}
					else
					{
						cmsLog.info("此版本不处理。");
					}
				}
				else
				{
					cmsLog.info("此版本不处理。");
				}
				
				if (queryStclasscode == null || "".equalsIgnoreCase(queryStclasscode))
				{
					String str = "存储等级code为空，不处理。";
					cmsResultDto.setResultCode((long)1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.warn(str);
				}
				else
				{
					List<ProgramFiles> programFileses = new ArrayList<ProgramFiles>();	// 需要查询判断的文件列表
					
					if(stclasscodeNearOnline.equalsIgnoreCase(queryStclasscode))
					{
						/**
						 * 查询缓存库，需要查询节目包所有文件
						 */
						List<PackageFiles> queryPackageFileses = packageFilesManager.findByProperty(
								"productid", 
								progPackage.getProductid()
								);
						for(int i = 0; i < queryPackageFileses.size(); i++)
						{
							PackageFiles pkf = queryPackageFileses.get(i);
							ProgramFiles pf = (ProgramFiles)programFilesManager.getById(pkf.getProgfileid());
							programFileses.add(pf);
						}
					}
					else if(stclasscodeCaOnline.equalsIgnoreCase(queryStclasscode))
					{
						/**
						 * 查询加扰库，需要查询主文件
						 */
						programFileses = packageFilesManager.getProgramFilesesByProductidProgrank(
								progPackage.getProductid(), 	// 节目包ID
								(long) 1 						// 主文件标识，0 - 不是; 1 - 是
								);
//						for(int i = 0; i < queryProgramFileses.size(); i++)
//						{
//							PackageFiles pkf = queryProgramFileses.get(i);
//							ProgramFiles pf = (ProgramFiles)programFilesManager.getById(pkf.getProgfileid());
//							programFileses.add(pf);
//						}
					}
					else if(stclasscodeOnline.equalsIgnoreCase(queryStclasscode))
					{
						/**
						 * 查询播发库，需要根据文件样式（应用代码），查询节目包文件
						 */
						programFileses = programFilesManager.getProgramFilesesByProgPackageFilestylecode(
								fileStyleManager, 
								packageFilesManager, 
								progPackage, 
								fileStyleCode
								);
					}
					
					/**
					 * 只要有一个文件不存在（文件位置表 或 实体文件）
					 * 那么节目包状态就不能修改
					 * 注意：未导入状态的节目包，主文件在播发库不存在，所以判断结果是不能修改节目包状态
					 */
					boolean needUpdate = true;
					cmsLog.info("判断节目包的文件是否已经到位...");
					cmsLog.info("节目包的文件数量：" + programFileses.size());
					for (int k = 0; k < programFileses.size(); k++) 
					{
						ProgramFiles pf = programFileses.get(k);
						
						cmsLog.info("处理第" + (k + 1) + "个文件...");
						cmsLog.info("文件ID：" + pf.getProgfileid());
						cmsLog.info("文件名：" + pf.getFilename());
						
						/**
						 * 根据文件id、存储等级code，查询文件位置表记录
						 */
						cmsLog.info("查询存储等级code：" + queryStclasscode);
						AmsStorageClass asc = null;
						AmsStorage amsStorage = null;
						AmsStoragePrgRel aspr = null;
						List<AmsStorageClass> ascs = amsstorageclassManager.findByProperty("stclasscode", queryStclasscode);
						if(ascs != null && ascs.size() > 0)
						{
							asc = ascs.get(0);
						}
						if(asc != null)
						{
							List<AmsStorage> amsStorages = amsstorageManager.findByProperty("stclassglobalid", asc.getStclassglobalid());
							if(amsStorages != null && amsStorages.size() > 0)
							{
								amsStorage = amsStorages.get(0);
							}
						}
						if(amsStorage != null)
						{
							List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
									pf.getProgfileid(), 
									amsStorage.getStglobalid()
									);
							if(asprs != null && asprs.size() > 0)
							{
								aspr = asprs.get(0);
							}
						}
						
						if(aspr != null)
						{
							cmsLog.info("文件位置表记录已经存在，需要判断实体文件是否存在。");
							cmsLog.info("文件位置表ID：" + aspr.getRelid());
							
							/**
							 * 根据，查询实体文件
							 */
							List sourcepaths = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									pf.getProgfileid(),
									queryStclasscode, 
									progPackage.getProductid()
									);
							if (sourcepaths != null && sourcepaths.size() >= 2)
							{
								String sourcepath = (String) sourcepaths.get(0);
								if(fileopr.checkSmbFileExist(sourcepath))
								{
									cmsLog.info("实体文件存在，该文件迁移完成，继续...");
								}
								else
								{
									cmsLog.info("实体文件不存在，文件尚未迁移完毕。");
									cmsLog.info("节目包文件尚未全部迁移完成，不修改节目包状态，停止循环判断。");
									needUpdate = false;
									break;
								}
							}
							else
							{
								/**
								 * 如何处理
								 */
								cmsLog.warn("查询文件路径为空。");
								needUpdate = false;
							}
						}
						else
						{
							cmsLog.info("文件位置表记录不存在，文件尚未迁移完毕。");
							cmsLog.info("节目包文件尚未全部迁移完成，不修改节目包状态，停止循环判断。");
							needUpdate = false;
							break;
						}
					}
					
					if(needUpdate == true)
					{
						cmsLog.info("节目包的所有文件都已经到位...");
						cmsLog.info("准备修改节目包状态和处理状态，节目包ID：" + progPackage.getProductid());
	
						progPackage.setState(newState);
						progPackage.setDealstate(newDealState);
						progPackageManager.update(progPackage);
						cmsLog.info("节目包的状态修改为：" + newState);
						cmsLog.info("节目包的处理状态修改为：" + newDealState);
						
						String str = "节目包的状态和处理状态修改为：" + newState + "，" + newDealState;
						cmsResultDto.setErrorMessage(str);
					}
					else
					{				
						String str = "节目包状态不需要修改，不处理。";
						cmsLog.info(str);
						cmsResultDto.setErrorMessage(str);
					}
				}
			}	// if("Y".equalsIgnoreCase(result))
			else if("N".equalsIgnoreCase(result))
			{
				/**
				 * 如果此次操作结果为：失败
				 * 并且节目包的处理状态为：处理中
				 * 那么不修改节目包的状态，只修改处理状态为：失败
				 */
				if(dealState == (long)1)
				{
					progPackage.setDealstate((long)8);
//					progPackageManager.
					progPackageManager.update(progPackage);
					String str = "节目包的处理状态修改为：失败(8)";
					cmsLog.info(str);
					cmsResultDto.setErrorMessage(str);
				}
				else
				{
					String str = "此次操作结果为“失败”，但是节目包的处理状态不是“处理中”，不处理。";
					cmsResultDto.setResultCode((long)1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.warn(str);
				}
			}
			else
			{
				String str = "此次操作结果未能识别，不处理。";
				cmsResultDto.setResultCode((long)1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		}	// if(progPackage == null)
		else
		{
			String str = "节目包为空。";
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}	
		
		cmsLog.info("Cms -> MigrationModuleManagerImpl -> updateRefreshStateOfProgPackage returns.");
		return cmsResultDto;
	}
	
	/**
	 * 20110110 17:08 1.23 修改节目包的状态和处理状态
	 * @param progPackage
	 * @param fileStyleCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto updateRefreshStateOfProgPackage_123(
			ProgPackage progPackage,	// 节目包
			long fileStyleCode,			// 文件样式应用code
			String result,				// 此次操作结果： "Y" - 成功； "N" - 失败
			String mainFileTag,
			String productInfoId
			)
	{
		cmsLog.info("Cms -> MigrationModuleManagerImpl -> updateRefreshStateOfProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		/**
		 * 		存储等级
		 * 1 - NearOnline
		 * 2 - CaOnline
		 * 3 - Online
		 * 
		 * 		节目包状态
		 * -1 - 未导入
		 * 0 - 导入 （不使用）
		 * 1 - 缓存库 ，所有文件
		 * 2 - 加扰库 ，主文件
		 * 3 - 播控库 ，根据文件样式，应用code2
		 * 9 - 在上海的北京缓存库（不使用）
		 * 
		 * 		处理状态
		 * 0 - 未处理
		 * 1 - 处理中
		 * 8 - 失败
		 * 9 - 成功（不使用）
		 */
		
		/**
		 * ***** 应用场景 *****
		 * 1 - 加扰完成(内容加密)
		 * 		缓存库、处理中 --> 加扰库、未处理
		 * 
		 * 2 - 迁移完成(迁移至播发库)
		 * 		加扰库、处理中 --> 播发库、未处理
		 * 
		 * 3 - 文件删除
		 * 		播发库、未处理 --> 加扰库、未处理
		 * 		加扰库、未处理 --> 缓存库、未处理
		 * 		缓存库、未处理 --> 未导入、未处理
		 * 
		 * 4 - 无主文件节目包的文件导入
		 * 		未导入、未处理 --> 缓存库、未处理
		 * 		
		 */
		
		cmsLog.info("Productid - " + progPackage.getProductid());
		cmsLog.info("fileStyleCode - " + String.valueOf(fileStyleCode));
		cmsLog.info("result - " + result);
		cmsLog.info("mainFileTag - " + mainFileTag);
		cmsLog.info("productInfoId - " + productInfoId);
		
		Long state = (long)-1;				// 节目包状态
		Long dealState = (long)-1;			// 处理状态
		Long newState = (long)-1;			// 新的节目包状态
		Long newDealState = (long)0;		// 新的处理状态
		Long encryptState = (long)-1;
		String queryStclasscode = null;		// 需要判断的存储等级code
//		Long queryState = (long)-1;			// 需要判断的节目包状态
		
		String stclasscodeNearOnline = "NearOnline"; 	// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline"; 		// 加扰库存储体等级code
		String stclasscodeOnline = "Online"; 			// 播控库存储体等级code
		/**
		 * HuangBo update by 2011年4月22日 19时5分
		 * 未使用, 注释
		 */
//		String stclasscodeBjOnline = "BjOnline"; 		// 在上海的北京缓存库存储体等级code，不使用
		
		if(progPackage != null)
		{
			if("Y".equalsIgnoreCase(mainFileTag))
			{
				TProductInfo productInfo = productinfoManager.getTProductInfoById(productInfoId);
				if(productInfo != null)
				{
					encryptState = productInfo.getEncryptState();
					if("Y".equalsIgnoreCase(result))
					{
						if(encryptState == (long)11)
						{
							newState = (long)19;
							queryStclasscode = stclasscodeOnline;
							
							if (queryStclasscode == null || "".equalsIgnoreCase(queryStclasscode))
							{
								String str = "存储等级code为空，不处理。";
								cmsResultDto.setResultCode((long)1);
								cmsResultDto.setErrorMessage(str);
								cmsLog.warn(str);
							}
							else
							{
								boolean needUpdate = true;
								
								List mainpfs = packageFilesManager.getProgramFilesesByProductidProgrank(
										progPackage.getProductid(), 
										(long)1				// 主文件标识，0 - 不是;  1 - 是
										);
								if(mainpfs != null && mainpfs.size() > 0)
								{
									ProgramFiles mainPf = (ProgramFiles)mainpfs.get(0);
									
									cmsLog.info("文件ID：" + mainPf.getProgfileid());
									cmsLog.info("文件名：" + mainPf.getFilename());
									
									/**
									 * 根据文件id、存储等级code，查询文件位置表记录
									 */
									cmsLog.info("查询存储等级code：" + queryStclasscode);
									AmsStorageClass asc = null;
									AmsStorage amsStorage = null;
									AmsStoragePrgRel aspr = null;
									List<AmsStorageClass> ascs = amsstorageclassManager.findByProperty("stclasscode", queryStclasscode);
									if(ascs != null && ascs.size() > 0)
									{
										asc = ascs.get(0);
									}
									if(asc != null)
									{
										List<AmsStorage> amsStorages = amsstorageManager.findByProperty("stclassglobalid", asc.getStclassglobalid());
										if(amsStorages != null && amsStorages.size() > 0)
										{
											amsStorage = amsStorages.get(0);
										}
									}
									if(amsStorage != null)
									{
										List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
												productInfoId, 
												amsStorage.getStglobalid()
												);
										if(asprs != null && asprs.size() > 0)
										{
											for(int i = 0; i < asprs.size(); i++)
											{
												AmsStoragePrgRel asprtemp = asprs.get(i);
												if(asprtemp.getFtypeglobalid().equalsIgnoreCase(mainPf.getFiletypeid()))
												{
													aspr = asprtemp;
													break;
												}
											}
										}
									}
									
									if(aspr != null)
									{
										cmsLog.info("文件位置表记录已经存在，需要判断实体文件是否存在。");
										cmsLog.info("文件位置表ID：" + aspr.getRelid());
										
										List sourcepaths = packageFilesManager
															.getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
																	productInfoId,
																	queryStclasscode, 	// 存储体等级code
																	progPackage.getProductid(),
																	mainPf.getFiletypeid()
																	);
										if (sourcepaths != null && sourcepaths.size() >= 2)
										{
											String sourcepath = (String) sourcepaths.get(0);
											if(fileopr.checkSmbFileExist(sourcepath))
											{
												cmsLog.info("实体文件存在，该文件迁移完成，继续...");
											}
											else
											{
												cmsLog.info("实体文件不存在，文件尚未迁移完毕。");
												cmsLog.info("节目包文件尚未全部迁移完成，不修改节目包状态，停止循环判断。");
												needUpdate = false;
											}
										}
										else
										{
											/**
											 * 如何处理
											 */
											cmsLog.warn("查询文件路径为空。");
											needUpdate = false;
										}
									}
									else
									{
										cmsLog.info("文件位置表记录不存在，文件尚未迁移完毕。");
										cmsLog.info("节目包文件尚未全部迁移完成，不修改节目包状态，停止循环判断。");
										needUpdate = false;
									}
								}

								if(needUpdate == true)
								{
									cmsLog.info("节目包的主文件已经到位...");
									cmsLog.info("准备修改节目包的产品信息状态，节目包ID：" + progPackage.getProductid());
									cmsLog.info("产品信息ID：" + productInfoId);
									
									productInfo.setEncryptState(newState);
									productinfoManager.update(productInfo);
									cmsLog.info("节目包的产品信息状态修改为：" + newState);
									
									String str = "节目包的产品信息状态状态修改为：" + newState;
									cmsResultDto.setErrorMessage(str);
								}
								else
								{				
									String str = "节目包状态不需要修改，不处理。";
									cmsLog.info(str);
									cmsResultDto.setErrorMessage(str);
								}
							}
						}
						else
						{
							cmsLog.warn("此版本不处理。");
						}
					}
					else if("N".equalsIgnoreCase(result))
					{
						if(encryptState == (long)11)
						{
							newState = (long)18;

							cmsLog.info("节目包的主文件迁移失败...");
							cmsLog.info("准备修改节目包的产品信息状态，节目包ID：" + progPackage.getProductid());
							cmsLog.info("产品信息ID：" + productInfoId);
							
							productInfo.setEncryptState(newState);
							productinfoManager.update(productInfo);
							cmsLog.info("节目包的产品信息状态修改为：" + newState);
						}
						else
						{
							cmsLog.warn("此版本不处理。");
						}
					}
					else
					{
						String str = "此次操作结果未能识别，不处理。";
						cmsResultDto.setResultCode((long)1);
						cmsResultDto.setErrorMessage(str);
						cmsLog.warn(str);
					}
				}
				else
				{
					cmsLog.warn("产品信息为空。");
				}
			}
			else if("N".equalsIgnoreCase(mainFileTag))
			{
				state = progPackage.getState();
				dealState = progPackage.getDealstate();
				
				cmsLog.info("节目包状态：" + state);
				cmsLog.info("节目包处理状态：" + dealState);
				cmsLog.info("此次操作结果：" + result);
				
				if("Y".equalsIgnoreCase(result))
				{
					/**
					 * 文件删除
					 */
					if(dealState == (long)0
						|| dealState == (long)8
						)
					{
						/**
						 * 未处理、失败
						 * 节目包状态回退（文件删除）
						 * 查询目标目录
						 * 这里代码需要修改，目前代码是按照的删除顺序： 一级库-->加扰库-->缓存库
						 */
						if(state == (long)0)
						{
							cmsLog.info("此版本不处理。");
						}
						else if(state == (long)1)
						{
							newState = (long)-1;
							queryStclasscode = stclasscodeNearOnline;
						}
						else if(state == (long)2)
						{
							newState = (long)1;
							queryStclasscode = stclasscodeCaOnline;
						}
						else if(state == (long)3)
						{
							newState = (long)3;
							queryStclasscode = stclasscodeOnline;
						}
						else if(state == (long)9)
						{
							cmsLog.info("此版本不处理。");
						}
						else
						{
							cmsLog.info("此版本不处理。");
						}
					}
					/**
					 * 文件迁移
					 */
					else if(dealState == (long)1)
					{
						/**
						 * 处理中
						 * 节目包状态前进（加扰完成、迁移完成）
						 */
						if(state == (long)0)
						{
							cmsLog.info("此版本不处理。");
						}
						else if(state == (long)1)
						{
							newState = (long)3;
							queryStclasscode = stclasscodeOnline;
						}
						else if(state == (long)2)
						{
//							newState = (long)3;
//							queryStclasscode = stclasscodeOnline;
							cmsLog.info("此版本不处理。");
						}
						else if(state == (long)3)
						{
							cmsLog.info("此版本不处理。");
						}
						else if(state == (long)9)
						{
							cmsLog.info("此版本不处理。");
						}
						else if(state == (long)-1)
						{
							cmsLog.info("此版本不处理。");
						}
						else
						{
							cmsLog.info("此版本不处理。");
						}
					}
					else
					{
						cmsLog.info("此版本不处理。");
					}
					
					if (queryStclasscode == null || "".equalsIgnoreCase(queryStclasscode))
					{
						String str = "存储等级code为空，不处理。";
						cmsResultDto.setResultCode((long)1);
						cmsResultDto.setErrorMessage(str);
						cmsLog.warn(str);
					}
					else
					{
						List<ProgramFiles> programFileses = new ArrayList<ProgramFiles>();	// 需要查询判断的文件列表
						
						if(stclasscodeNearOnline.equalsIgnoreCase(queryStclasscode))
						{
							/**
							 * 查询缓存库，需要查询节目包所有文件
							 */
							List<PackageFiles> queryPackageFileses = packageFilesManager.findByProperty(
									"productid", 
									progPackage.getProductid()
									);
							for(int i = 0; i < queryPackageFileses.size(); i++)
							{
								PackageFiles pkf = queryPackageFileses.get(i);
								ProgramFiles pf = (ProgramFiles)programFilesManager.getById(pkf.getProgfileid());
								programFileses.add(pf);
							}
						}
						else if(stclasscodeCaOnline.equalsIgnoreCase(queryStclasscode))
						{
							/**
							 * 查询加扰库，需要查询主文件
							 */
							programFileses = packageFilesManager.getProgramFilesesByProductidProgrank(
									progPackage.getProductid(), 	// 节目包ID
									(long) 1 						// 主文件标识，0 - 不是; 1 - 是
									);
	//						for(int i = 0; i < queryProgramFileses.size(); i++)
	//						{
	//							PackageFiles pkf = queryProgramFileses.get(i);
	//							ProgramFiles pf = (ProgramFiles)programFilesManager.getById(pkf.getProgfileid());
	//							programFileses.add(pf);
	//						}
						}
						else if(stclasscodeOnline.equalsIgnoreCase(queryStclasscode))
						{
							/**
							 * 查询播发库，需要根据文件样式（应用代码），查询节目包文件
							 */
							programFileses = programFilesManager.getProgramFilesesByProgPackageFilestylecode(
									fileStyleManager, 
									packageFilesManager, 
									progPackage, 
									fileStyleCode
									);
						}
						
						/**
						 * 只要有一个文件不存在（文件位置表 或 实体文件）
						 * 那么节目包状态就不能修改
						 * 注意：未导入状态的节目包，主文件在播发库不存在，所以判断结果是不能修改节目包状态
						 */
						boolean needUpdate = true;
						cmsLog.info("判断节目包的文件是否已经到位...");
						cmsLog.info("节目包的文件数量：" + programFileses.size());
						for (int k = 0; k < programFileses.size(); k++) 
						{
							ProgramFiles pf = programFileses.get(k);
							
							cmsLog.info("处理第" + (k + 1) + "个文件...");
							cmsLog.info("文件ID：" + pf.getProgfileid());
							cmsLog.info("文件名：" + pf.getFilename());
							
							/**
							 * 根据文件id、存储等级code，查询文件位置表记录
							 */
							cmsLog.info("查询存储等级code：" + queryStclasscode);
							AmsStorageClass asc = null;
							AmsStorage amsStorage = null;
							AmsStoragePrgRel aspr = null;
							List<AmsStorageClass> ascs = amsstorageclassManager.findByProperty("stclasscode", queryStclasscode);
							if(ascs != null && ascs.size() > 0)
							{
								asc = ascs.get(0);
							}
							if(asc != null)
							{
								List<AmsStorage> amsStorages = amsstorageManager.findByProperty("stclassglobalid", asc.getStclassglobalid());
								if(amsStorages != null && amsStorages.size() > 0)
								{
									amsStorage = amsStorages.get(0);
								}
							}
							if(amsStorage != null)
							{
								List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
										pf.getProgfileid(), 
										amsStorage.getStglobalid()
										);
								if(asprs != null && asprs.size() > 0)
								{
									aspr = asprs.get(0);
								}
							}
							//TODO 位置表片花无记录, 显示迁移不完成
							if(aspr != null)
							{
								cmsLog.info("文件位置表记录已经存在，需要判断实体文件是否存在。");
								cmsLog.info("文件位置表ID：" + aspr.getRelid());
								
								/**
								 * 根据，查询实体文件
								 */
								List sourcepaths = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
										pf.getProgfileid(),
										queryStclasscode,
										/**
										 * HuangBo update by 2011年4月22日 19时5分
										 * 片花重复使用, 只建立第一次迁移的位置表信息, 不能根据节目包ID查询位置表信息, 修改为不传入节目包ID参数
										 */
										""
//										progPackage.getProductid()
										);
								if (sourcepaths != null && sourcepaths.size() >= 2)
								{
									String sourcepath = (String) sourcepaths.get(0);
									if(fileopr.checkSmbFileExist(sourcepath))
									{
										cmsLog.info("实体文件存在，该文件迁移完成，继续...");
									}
									else
									{
										cmsLog.info("实体文件不存在，文件尚未迁移完毕。");
										cmsLog.info("节目包文件尚未全部迁移完成，不修改节目包状态，停止循环判断。");
										needUpdate = false;
										break;
									}
								}
								else
								{
									/**
									 * 如何处理
									 */
									cmsLog.warn("查询文件路径为空。");
									needUpdate = false;
								}
							}
							else
							{
								cmsLog.info("文件位置表记录不存在，文件尚未迁移完毕。");
								cmsLog.info("节目包文件尚未全部迁移完成，不修改节目包状态，停止循环判断。");
								needUpdate = false;
								break;
							}
						}
						
						if(needUpdate == true)
						{
							cmsLog.info("节目包的所有文件都已经到位...");
							cmsLog.info("准备修改节目包状态和处理状态，节目包ID：" + progPackage.getProductid());
		
							progPackage.setState(newState);
							progPackage.setDealstate(newDealState);
							progPackageManager.update(progPackage);
							cmsLog.info("节目包的状态修改为：" + newState);
							cmsLog.info("节目包的处理状态修改为：" + newDealState);
							
							String str = "节目包的状态和处理状态修改为：" + newState + "，" + newDealState;
							cmsResultDto.setErrorMessage(str);
						}
						else
						{				
							String str = "节目包状态不需要修改，不处理。";
							cmsLog.info(str);
							cmsResultDto.setErrorMessage(str);
						}
					}
				}	// if("Y".equalsIgnoreCase(result))
				else if("N".equalsIgnoreCase(result))
				{
					/**
					 * 如果此次操作结果为：失败
					 * 并且节目包的处理状态为：处理中
					 * 那么不修改节目包的状态，只修改处理状态为：失败
					 */
					if(dealState == (long)1)
					{
						progPackage.setDealstate((long)8);
	//					progPackageManager.
						progPackageManager.update(progPackage);
						String str = "节目包的处理状态修改为：失败(8)";
						cmsLog.info(str);
						cmsResultDto.setErrorMessage(str);
					}
					else
					{
						String str = "此次操作结果为“失败”，但是节目包的处理状态不是“处理中”，不处理。";
						cmsResultDto.setResultCode((long)1);
						cmsResultDto.setErrorMessage(str);
						cmsLog.warn(str);
					}
				}
				else
				{
					String str = "此次操作结果未能识别，不处理。";
					cmsResultDto.setResultCode((long)1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.warn(str);
				}
			}
			else
			{
				
			}
		}	// if(progPackage == null)
		else
		{
			String str = "节目包为空。";
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}	
		
		cmsLog.info("Cms -> MigrationModuleManagerImpl -> updateRefreshStateOfProgPackage returns.");
		return cmsResultDto;
	}

	public boolean checkStateOkForAccessaryfilesFromNearonlineToOnline_123(
			long ppState,
			long ppDealState,
			long ppMainFileFlag,
			long encrypState
			)
	{
		boolean stateOk = false;
		if(ppDealState == (long)-1)
		{
			if(ppState == (long)-1)
			{
				if(ppMainFileFlag == (long)-1)
				{
					stateOk = true;
				}
				else
				{
					stateOk = false;
				}
			}
			else
			{
				stateOk = false;
			}
		}
		else if(ppDealState == (long)0
				|| ppDealState == (long)8
				)
		{
			if(ppState == (long)-1)
			{
				if(ppMainFileFlag == (long)-1)
				{
					stateOk = true;
				}
				else
				{
					stateOk = false;
				}
			}
			else if(ppState == (long)1)
			{
				stateOk = true;
			}
			else 
			{
				stateOk = false;
			}
		}
		else
		{
			stateOk = false;
		}
		return stateOk;
	}
	
	public boolean checkStateOkForMainfilesFromCaonlineToOnline_123(
			long ppState,
			long ppDealState,
			long ppMainFileFlag,
			long encrypState
			)
	{
//		/**
//		 * state = 1
//		 */
		boolean stateOk = false;
//		if(ppState == (long)1 
//			&& ppDealState == (long)0
//			)
//		{
//			if(encrypState == (long)9 
//				|| encrypState == (long)18
//				)
//			{
//				stateOk = true;
//			}
//		}
//		else if(ppState == (long)-1)
//		{
//			if(ppMainFileFlag == (long)-1)
//			{
//				stateOk = true;
//			}
//		}
//		else
//		{
//			
//		}
		if(encrypState == (long)9 
			|| encrypState == (long)18
			)
		{
			stateOk = true;
		}

		return stateOk;
	}
	//============================================================================================
	
	
	
	//---------------------- 1.23 接口方法 ----------------------------------------------------------
	/**
	 * 20110107 10:17 1.23 加扰库 --> 一级库（播发库），生成迁移任务（以节目包为单位）
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto saveMigrationFromCaonlineToOnlineByProgpackages_123(
			String date,
			List<ProgPackage> progPackages,
			List<String> productinfoids,
			String operatorId
			)
	{
		/**
		 * 1 - 循环判断每个节目包的state和dealstate，state为-1未导入或2加扰库，dealstate为-1未导入、0未处理或8失败，可以生成迁移任务。
		 * 			1缓存库和3播发库的节目包不能生成迁移任务。（节目包state为-1未导入，MAINFILEFLAG必须为-1 - 无主体文件节目包。）
		 * 2 - 修改state为2加扰库的节目包的dealstate修改为：1处理中；state为-1未导入的节目包的dealstate不修改。
		 * 3 - 查询节目包需要迁移的文件list（根据文件样式查询）。
		 * 4 - 根据上述文件list查询每个文件在播发库是否已经存在（文件位置表记录），并且判断实体文件是否已经存在。
		 * 6 - 如果文件已经存在于播发库（文件位置表记录、实体文件都存在），则该文件不加入迁移任务，跳过；
		 * 			如果文件不存在于播发库（文件位置表记录或实体文件不存在），则继续下面操作。
		 * 7 - 附加判断，如果该节目包下所有的文件都存在于播发库，将节目包的state修改为：3播发库，dealstate修改为：0未处理。
		 * 8 - 查询该文件的源路径和目标路径。
		 * 9 - 如果文件的源路径、目标路径或源实体文件有误，则该文件不加入迁移任务，并且如果所属节目包的state为-1未导入，则不处理；
		 * 			如果所属节目包的state是2加扰库，则节目包的dealstate修改为8失败。
		 * 10 - 源路径、目标路径和实体文件都无误的文件，加入迁移任务。
		 * 11 - 判断迁移任务是否为空，如果不为空，在本地生成迁移任务xml文件，复制xml文件至迁移模块接口目录。
		 * 12 - 操作数据库，proglistfile。
		 */
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationFromCaonlineToOnlineByProgpackages_123...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件
		String serverOS = cc.getPropertyByName("ServerOS");		// 服务器操作系统
		String migrationXmlTemp = cc.getPropertyByName("MigrationXmlTemp"); 	// 迁移单xml本地临时目录
		
		cmsLog.debug("日期：" + date);
		cmsLog.debug("操作员：" + operatorId);
		
		String stclasscodeNearOnline = "NearOnline";	// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline";		// 加扰库存储体等级code
		String stclasscodeOnline = "Online";			// 一级库存储体等级code
		String stclasscodeMigration = "Migration";		// 迁移单的目标存储体等级code
		String filecodeMigration = "MIGXML";			// 迁移单xml的filecode
		String destpathMigration = "";					// 迁移单的目标路径
		String xmlFilename = "";						// 迁移单的文件名字
		Date nowdate = new Date();
		String localfile = null;
		String scheduledate = null;
		if(date == null || "".equalsIgnoreCase(date))
		{
			scheduledate = fileopr.convertDateToString(nowdate, "yyyyMMddHHmmss");
		}
		else
		{
			scheduledate = fileopr.convertDateToScheduleDate(date);
		}
		String createDate = fileopr.convertDateToString(nowdate, "yyyy-MM-dd HH:mm:ss");
		String requestId = fileopr.convertDateToString(nowdate, "yyyyMMddHHmmssSSSSSS");
		String priorityDate = createDate;
		long fileStyleCode = 2L;		// 1 - 节目预告 ; 2 - 播发单
		int migrationType = 1;			// 迁移至播发库@北京
		int taskcount = 0;
		List<ProgPackage> dealedPps = new ArrayList<ProgPackage>();			// 处理过的节目包
		List<ProgPackage> needUpdatePps = new ArrayList<ProgPackage>();		// 需要更新的节目包
		List<TProductInfo> needUpdatePtis = new ArrayList<TProductInfo>();	// 需要更新的产品信息
		List<ProgPackage> needFixPps = new ArrayList<ProgPackage>();		// 需要修正的节目包
		List<TProductInfo> needFixPtis = new ArrayList<TProductInfo>();		// 需要修正的产品信息
		
		xmlFilename = scheduledate + requestId + ".xml";

		/**
		 * 查询迁移单xml文件目标路径
		 */
		List<String> filepaths = getMigrationXmlFilepath(
				filecodeMigration, 
				stclasscodeMigration, 
				migrationXmlTemp, 
				serverOS
				);
		
		if(filepaths != null && filepaths.size() >= 2)
		{
			destpathMigration = filepaths.get(0);
			localfile = filepaths.get(1);
		}
		
		if(destpathMigration == null 
			|| "".equalsIgnoreCase(destpathMigration)
			|| localfile == null
			|| "".equalsIgnoreCase(localfile)
			)
		{
			String str = "迁移单目标路径或本地临时目录不存在，不生成迁移任务。";
			cmsLog.warn(str);
			cmsResultDto.setErrorMessage(str);
			cmsResultDto.setResultCode((long)1);
		}
		else
		{
			// 迁移xml文件目标路径查询成功。
			localfile = localfile + xmlFilename;
			destpathMigration = destpathMigration + xmlFilename;
			cmsLog.debug("迁移xml文件本地临时路径：" + localfile);
			cmsLog.debug("迁移xml文件目标路径：" + destpathMigration);
			
			cmsLog.debug("编单日期：" + scheduledate);

			if(progPackages == null || progPackages.size() <= 0)
			{
				cmsLog.debug("节目包列表为空，不操作，跳过...");
			}
			else if(productinfoids == null || productinfoids.size() <= 0)
			{
				cmsLog.debug("产品信息列表为空，不操作，跳过...");
			}
			else
			{
				/**
				 * 处理 xml
				 */
				TransferDistribution transferDistribution = new TransferDistribution();
				transferDistribution.setComment("迁移任务单（加扰库 至 播发库），生成日期：" + createDate);
				transferDistribution.setProglistId(scheduledate);
				transferDistribution.setRequestId(requestId);
				transferDistribution.setType(String.valueOf(migrationType));
				transferDistribution.setCreateDate(createDate);
				
				List<TransferTask> transferTasks = new ArrayList<TransferTask>();
				TransferTask transferTask = new TransferTask();
				List<TransferEntity> transferEntities = new ArrayList<TransferEntity>();
				
				cmsLog.debug("节目包数量：" + progPackages.size());
				for(int j = 0; j < progPackages.size(); j++)
				{
					ProgPackage progPackage = (ProgPackage)progPackages.get(j);
					String productInfoId = productinfoids.get(j);
					TProductInfo productInfo = null;
					if(productInfoId != null && !"".equalsIgnoreCase(productInfoId))
					{
						productInfo = productinfoManager.getTProductInfoById(productInfoId);
					}
					List<ProgramFiles> programFileses = new ArrayList<ProgramFiles>();	// 需要迁移的节目包文件
//					String contentid = "";		// 目前为主文件的progfileid
					long ppState = -2;
					long ppDealState = -2;
					long ppMainFileFlag = -2;
					long encryptState = -1;
					try
					{
						ppState = progPackage.getState();
						ppDealState = progPackage.getDealstate();
						ppMainFileFlag = (progPackage.getMainFileFlag() == null) ? (long)1 : progPackage.getMainFileFlag();
						if(productInfo != null)
						{
							encryptState = productInfo.getEncryptState();
						}
					}
					catch(Exception e)
					{
						cmsLog.warn(e.getMessage());
					}
					/**
					 * 附件判断用，如果该节目包下所有的文件都存在于播发库，将节目包的state修改为：3播发库，dealstate修改为：0未处理。
					 */ 
					int allNeedFileCount = 0;
					int existOnlineFileCount = 0;
					
					cmsLog.debug("处理第" + (j+1) + "个节目包...");
					cmsLog.debug("节目包id：" + progPackage.getProductid());
					cmsLog.debug("节目包名称：" + progPackage.getProductname());
					cmsLog.debug("文件样式应用代码：" + fileStyleCode);
					cmsLog.debug("节目包的样式ID：" + progPackage.getStyleid());
					cmsLog.debug("节目包的品牌：" + progPackage.getSiteCode());
					cmsLog.debug("节目包的State：" + ppState);
					cmsLog.debug("节目包的DealState：" + ppDealState);
					cmsLog.debug("节目包的MainFileFlag：" + ppMainFileFlag);
					cmsLog.debug("产品信息id：" + productInfoId);
					cmsLog.debug("产品信息encrypState：" + encryptState);

					/**
					 * dealedPpids
					 * 判断节目包是否已经在本次操作中处理过，
					 * 如果处理过，不处理，跳过
					 * 如果没处理过，继续
					 */
					boolean dealed = false;
					for(int k = 0; k < dealedPps.size(); k++)
					{
						ProgPackage dealedpp = dealedPps.get(k);
						if(dealedpp.getProductid().equalsIgnoreCase(progPackage.getProductid()))
						{
							dealed = true;
							break;
						}
					}
					if(dealed == true)
					{
						cmsLog.debug("节目包已经处理过了，不处理，跳过...");
						continue;
					}
					else
					{
						dealedPps.add(progPackage);
					}
					
					/**
					 * 循环判断每个节目包的state和dealstate
					 * state为-1未导入或2加扰库，dealstate为-1未导入、0未处理或8失败，可以生成迁移任务
					 * 1缓存库和3播发库的节目包不能生成迁移任务
					 * 注：节目包state为-1未导入，MAINFILEFLAG必须为-1 - 无主体文件节目包
					 */
					boolean stateOkForAccessary = false;
					boolean stateOkForMain = false;
					
					stateOkForAccessary = checkStateOkForAccessaryfilesFromNearonlineToOnline_123(ppState,ppDealState,ppMainFileFlag,encryptState);
					if(productInfo != null)
					{
						stateOkForMain = checkStateOkForMainfilesFromCaonlineToOnline_123(ppState,ppDealState,ppMainFileFlag,encryptState);
					}
//					if(stateOk == false)
//					{
//						cmsLog.debug("节目包的状态和处理状态不符合迁移条件，不处理，跳过...");
//						continue;
//					}
					
					cmsLog.debug("附属文件状态检查结果：" + stateOkForAccessary);
					cmsLog.debug("主文件状态检查结果：" + stateOkForMain);
					
					/**
					 * ************* 处理附属文件 **********************************************
					 */
					if(stateOkForAccessary == true)
					{
						/**
						 * 修改state为2加扰库的节目包的dealstate修改为：1处理中；
						 * state为-1未导入的节目包的dealstate不修改。
						 */
						cmsLog.debug("节目包的状态和处理状态符合迁移条件，继续...");
						
						if(ppState == (long)-1)
						{
							cmsLog.debug("节目包状态为：-1 - 未导入，节目包的处理状态不修改。");
						}
						else if(ppState == (long)1)
						{
//							cmsLog.debug("设置节目包的处理状态为：1 - 处理中...");
//							progPackage.setDealstate((long)1);
//							if(!needUpdatePps.contains(progPackage))
//							{
//								needUpdatePps.add(progPackage);
//							}
						}
						else
						{
							cmsLog.warn("节目包状态不符合迁移条件，跳过...");
							continue;
						}
	
						/**
						 * 根据文件样式，查询需要迁移的文件列表
						 */
						cmsLog.debug("根据文件样式，查询需要迁移的文件列表...");
						programFileses = programFilesManager.getProgramFilesesByProgPackageFilestylecode(
								fileStyleManager, 
								packageFilesManager, 
								progPackage, 
								fileStyleCode
								);
						
						if (null == programFileses) {
							cmsLog.debug("文件样式中配置的附属文件为空, 没有附属文件需要迁移，跳过...");
							cmsLog.debug("修改节目包附属文件状态为3(播发库)!");
							progPackage.setState(3L);
							progPackage.setDealstate(0L);
							if(!needUpdatePps.contains(progPackage))
							{
								needUpdatePps.add(progPackage);
							}
						} else {
							
							allNeedFileCount = programFileses.size();
							cmsLog.debug("需要迁移的节目包文件数量：" + allNeedFileCount);
							if(allNeedFileCount > 0)
							{
								for(int m = 0; m < allNeedFileCount; m++)
								{
									ProgramFiles accPf = programFileses.get(m);
									cmsLog.debug("处理第" + (m+1) + "个节目包文件...");
									cmsLog.debug("文件id：" + accPf.getProgfileid());
									cmsLog.debug("文件名称：" + accPf.getFilename());
									cmsLog.debug("主文件标志：" + accPf.getProgrank());
									cmsLog.debug("filecode：" + accPf.getFilecode());
									cmsLog.debug("filetype：" + accPf.getFiletypeid());
									
									boolean needMigration = false;
									int fileorfolder = 0;	// 0 - file ; 1 - folder
									String sourcepath = null; 		// 节目包主文件的源路径
									String destpath = null; 		// 节目包主文件的目标路径
									String filepathinxml = null;			
									Date dfiledate = null;
									String filedateinxml = null;
									String sourcefileinxml = null;
									String desfileinxml = null;
									String srcstclasscode = null;			// 源文件的存储等级code			
									AmsStorage sourceamsst = null;
									AmsStoragePrgRel sourceamsstpr = null;
									AmsStorageDir sourceamsstd = null;
									AmsStorageClass sourceamsstc = null;
									AmsStorage destamsst = null;
									AmsStorageDir destamsstd = null;
									AmsStorageClass destamsstc = null;
									
									/**
									 * 主文件，从加扰库获取
									 * 非主文件，从缓存库获取
									 */
									if(accPf.getProgrank() == (long)1)
									{
										if("R".equalsIgnoreCase(progPackage.getProgtype()))
										{
											// 富媒体的主文件，是文件夹
											fileorfolder = 1;
										}
										if(ppState == (long)-1)
										{
											/**
											 * 如果节目包状态是未导入，节目包的主文件不处理。
											 */
											cmsLog.debug("该文件所属节目包状态是“未导入”，且该文件是节目包的主文件，不处理该文件，跳过...");
											continue;
										}
										srcstclasscode = stclasscodeCaOnline;
									}
									else
									{
										srcstclasscode = stclasscodeNearOnline;
									}
									cmsLog.debug("源文件所在存储等级code：" + srcstclasscode);
									
									/**
									 * 生成filepath，文件id的前10位
									 */
									filepathinxml = accPf.getProgfileid();
									filepathinxml = filepathinxml.substring(0, 10);
									
									/**
									 * 查询文件的源路径(CaOnline)...
									 * 主文件：CaOnline
									 * 附属文件：NearOnline
									 * Key：CaOnline，不处理
									 */
									/**
									 * HuangBo update by 2011年3月23日 16时14分
									 */
									cmsLog.debug("查询文件的源路径...");
									List sourcepaths = this.
									packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
											accPf.getProgfileid(),
											srcstclasscode, 	// 存储体等级code
											""
									);
									
									if (sourcepaths != null && sourcepaths.size() >= 2)
									{
										sourcepath = (String) sourcepaths.get(0);
										List list = (List) sourcepaths.get(1);
										Object[] sourcerows = (Object[]) list.get(0);
										sourceamsst = (AmsStorage) sourcerows[0];
										sourceamsstpr = (AmsStoragePrgRel) sourcerows[1];
										sourceamsstd = (AmsStorageDir) sourcerows[2];
										sourceamsstc = (AmsStorageClass) sourcerows[3];
										
										/**
										 * 文件的filedate，从源文件位置表记录获取
										 */
										dfiledate = sourceamsstpr.getFiledate();
										filedateinxml = fileopr.convertDateToString(dfiledate, "yyyy-MM-dd HH:mm:ss");
									}
									else
									{
										cmsLog.warn("查询文件源路径失败。");
										sourcepath = null;
									}
									
									/**
									 * 查询文件的目标路径(Online)...
									 */
									cmsLog.debug("查询文件的目标路径(Online)...");
									List destpaths = packageFilesManager
									.getDestPathByFilecodeStclasscode(
											accPf.getFilecode(), // 节目包文件的filecode
											stclasscodeOnline // 一级库存储体等级code，从配置文件获取
									);
									if (destpaths != null && destpaths.size() >= 2)
									{
										destpath = (String) destpaths.get(0);
										List list1 = (List) destpaths.get(1);
										Object[] destrows = (Object[]) list1.get(0);
										destamsst = (AmsStorage) destrows[0];
										destamsstd = (AmsStorageDir) destrows[1];
										destamsstc = (AmsStorageClass) destrows[2];
										
										// 处理destpath，加上filepath和文件名
										destpath = destpath.replace('\\', '/');
										destpath = fileopr.checkPathFormatRear(destpath, '/');
										if (filepathinxml != null && !"".equalsIgnoreCase(filepathinxml)) 
										{
											destpath += filepathinxml;
										}
										destpath = destpath.replace('\\', '/');
										destpath = fileopr.checkPathFormatRear(destpath, '/');
										destpath += accPf.getFilename();
									}
									else
									{
										cmsLog.warn("查询文件目标路径失败。");
										destpath = null;
									}
									
									cmsLog.debug("源路径 - " + sourcepath);
									cmsLog.debug("目标路径 - " + destpath);
									
									/**
									 * 判断文件是否存在于一级库（文件位置表、存储）
									 * 判断文件的源路径、目标路径和源文件
									 */
									boolean pathfileOk = false;
									if(sourcepath == null
											|| destpath == null
											|| "".equalsIgnoreCase(sourcepath)
											|| "".equalsIgnoreCase(destpath)
									)
									{
										cmsLog.warn("节目包文件的源路径或目标路径不合法...");
										pathfileOk = false;
										if(ppState == (long)-1)
										{
											// 不处理
											cmsLog.debug("节目包状态是-1未导入，节目包的处理状态不修改。");
										}
										if(ppState == (long)2)
										{
											cmsLog.debug("节目包的处理状态修改为：8 - 失败");
											progPackage.setDealstate((long)8);
											if(!needUpdatePps.contains(progPackage))
											{
												needUpdatePps.add(progPackage);
											}
										}
									}
									else
									{
										cmsLog.debug("节目包文件的源路径和目标路径合法...");
										cmsLog.debug("判断源文件是否存在...");
										
										if(fileopr.checkSmbFileExist(sourcepath))
										{
											cmsLog.debug("源文件存在，继续...");
											pathfileOk = true;
										}
										else
										{
											cmsLog.warn("节目包文件的源路径存在，但是源文件不存在...");
											pathfileOk = false;
											
											cmsLog.debug("节目包的处理状态修改为：8 - 失败");
											progPackage.setDealstate((long)8);
											if(!needUpdatePps.contains(progPackage))
											{
												needUpdatePps.add(progPackage);
											}
										}
									}
									if(pathfileOk == false)
									{
//									if(ppState == (long)-1)
//									{
//										// 不处理
//										cmsLog.debug("节目包状态是-1未导入，节目包的处理状态不修改。");
//									}
//									if(ppState == (long)2)
//									{
//										cmsLog.debug("节目包的处理状态修改为：8 - 失败");
//										progPackage.setDealstate((long)8);
//										if(!needUpdatePps.contains(progPackage))
//										{
//											needUpdatePps.add(progPackage);
//										}
//									}
										cmsLog.debug("不处理该文件，跳过...");
										continue;
									}
									
									/**
									 * 处理路径格式，去掉头
									 * smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
									 * /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
									 */ 
									sourcefileinxml = sourcepath.substring(sourcepath.indexOf("/", 6),sourcepath.length());
									desfileinxml = destpath.substring(destpath.indexOf("/", 6),destpath.length());
									if(fileorfolder == 1)
									{
										sourcefileinxml = sourcefileinxml.replace('\\', '/');
										sourcefileinxml = fileopr.checkPathFormatRear(sourcefileinxml, '/');
										desfileinxml = desfileinxml.replace('\\', '/');
										desfileinxml = fileopr.checkPathFormatRear(desfileinxml, '/');
									}
									
									/**
									 * 根据文件id、存储等级code，查询文件是否存在于文件位置表
									 */
									cmsLog.debug("节目包文件的源路径、目标路径和源文件正确...");
									cmsLog.debug("判断该文件是否已经存在于一级库...");
									cmsLog.debug("查询该文件的存储位置表记录（一级库）...");
									boolean existOnline = false;
									AmsStorageClass asc = null;
									AmsStorage amsStorage = null;
									AmsStoragePrgRel aspr = null;
									List<AmsStorageClass> ascs = amsstorageclassManager.findByProperty("stclasscode", stclasscodeOnline);
									if(ascs != null && ascs.size() > 0)
									{
										asc = ascs.get(0);
									}
									if(asc != null)
									{
										List<AmsStorage> amsStorages = amsstorageManager.findByProperty("stclassglobalid", asc.getStclassglobalid());
										if(amsStorages != null && amsStorages.size() > 0)
										{
											amsStorage = amsStorages.get(0);
										}
									}
									if(amsStorage != null)
									{
										List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
												accPf.getProgfileid(), 
												amsStorage.getStglobalid()
										);
										if(asprs != null && asprs.size() > 0)
										{
											aspr = asprs.get(0);
										}
									}
									if(aspr == null)
									{
										cmsLog.debug("文件不存在于文件位置表。");
										existOnline = false;
									}
									else
									{
										cmsLog.debug("文件已经存在于文件位置表，需要检查实体文件...");
										cmsLog.debug("查询该文件的实体文件（一级库）...");
										if(fileopr.checkSmbFileExist(destpath))
										{
											cmsLog.debug("该文件的实体文件存在于一级库.");
											existOnline = true;
											existOnlineFileCount++;
										}
										else
										{
											cmsLog.debug("该文件的实体文件不存在于一级库.");
											existOnline = false;
										}
									}
									if(pathfileOk == true
											&& existOnline == false
									)
									{
										needMigration = true;
									}
									else
									{
										needMigration = false;
									}
									
									if(needMigration == true)
									{
										cmsLog.debug("该文件加入迁移任务...");
										taskcount++;
										
										/**
										 * 处理 xml
										 */
										String isCover = "Y";
										TransferEntity transferEntity = new TransferEntity();
										TransferSource transferSource = new TransferSource();
										TransferDestination transferDestination = new TransferDestination();
										
										transferSource.setType(sourceamsst.getStorageaccstype());
										transferSource.setHostname(sourceamsst.getStorageip());
										transferSource.setPort((sourceamsst.getPort() == null) ? "" : sourceamsst.getPort().toString());
										transferSource.setUsername((sourceamsst.getLoginname() == null) ? "" : sourceamsst.getLoginname());
										transferSource.setPassword((sourceamsst.getLoginpwd() == null) ? "" : sourceamsst.getLoginpwd());
										transferSource.setSourcestorageId(sourceamsst.getStglobalid());
										transferSource.setSourceDirId(sourceamsstd.getStdirglobalid());
										transferSource.setVariableFilePath(sourceamsstpr.getFilepath());
										transferSource.setFileDate((filedateinxml == null) ? "" : filedateinxml);
										if(fileorfolder == 0)
										{
											transferSource.setFolder(sourcefileinxml);
										}
										else
										{
											transferSource.setFile(sourcefileinxml);
										}
										
										transferDestination.setType(destamsst.getStorageaccstype());
										transferDestination.setHostname(destamsst.getStorageip());
										transferDestination.setPort((destamsst.getPort() == null) ? "" : destamsst.getPort().toString());
										transferDestination.setUsername((destamsst.getLoginname() == null) ? "" : destamsst.getLoginname());
										transferDestination.setPassword((destamsst.getLoginpwd() == null) ? "" : destamsst.getLoginpwd());
										transferDestination.setDesStorageId(destamsst.getStglobalid());
										transferDestination.setDesStorageDirId(destamsstd.getStdirglobalid());
										transferDestination.setVariableFilePath((filepathinxml == null) ? "" : filepathinxml);
										transferDestination.setFileDate((filedateinxml == null) ? "" : filedateinxml);
										if(fileorfolder == 0)
										{
											transferDestination.setFolder(desfileinxml);
										}
										else
										{
											transferDestination.setFile(desfileinxml);
										}
										
										transferEntity.setSourceFileId(accPf.getProgfileid());
										transferEntity.setSourceFileName(accPf.getFilename());
										transferEntity.setSourceFileCode(accPf.getFilecode());
										transferEntity.setSourceFileType(accPf.getFiletypeid());
										transferEntity.setSourceProgid(progPackage.getProductid());
										transferEntity.setSourceProgTitle(progPackage.getProductname());
										transferEntity.setSourceStorageClass(sourceamsstc.getStclasscode());
										transferEntity.setDesStorageClass(destamsstc.getStclasscode());
										transferEntity.setPriorityDate(priorityDate);
										transferEntity.setIsCover(isCover);
										
										transferEntity.setMainFileTag("N");
										transferEntity.setProgFileId(accPf.getProgfileid());
										transferEntity.setProductInfoId("");
										
										transferEntity.setTransferSource(transferSource);
										transferEntity.setTransferDestination(transferDestination);
										
										/**
										 * 加入一个文件的迁移任务
										 */
										transferEntities.add(transferEntity);
										
										cmsLog.debug("设置节目包的处理状态为：1 - 处理中...");
										progPackage.setDealstate((long)1);
										if(!needUpdatePps.contains(progPackage))
										{
											needUpdatePps.add(progPackage);
										}
									}	// if(needMigration == true)
									else
									{
										cmsLog.debug("该文件不需要加入迁移任务，跳过...");
									}
								}	// for(int m = 0; m < programFileses.size(); m++)
								
								/**
								 * 附件判断用，如果该节目包下所有的文件都存在于播发库，将节目包的state修改为：3播发库，dealstate修改为：0未处理。
								 */ 
								if(allNeedFileCount == existOnlineFileCount)
								{
									if(progPackage.getState() != (long)3
											|| progPackage.getDealstate() != (long)0
									)
									{
										cmsLog.warn("节目包的文件都存在于播发库（一级库），但是节目包状态不是“播发库，未处理”。");
										cmsLog.debug("修正节目包状态为：3 - 播发库");
										cmsLog.debug("修正节目包处理状态为：0 - 未处理");
										progPackage.setState((long)3);
										progPackage.setDealstate((long)0);
//									if(!needUpdatePps.contains(progPackage))
//									{
//										needUpdatePps.add(progPackage);
//									}
										if(!needFixPps.contains(progPackage))
										{
											needFixPps.add(progPackage);
										}
									}
								}
								
							}	// if(programFileses.size() > 0)
							else
							{
								cmsLog.warn("符合文件样式的节目包文件没有查询到，不加入迁移任务，跳过...");
								cmsLog.warn("不允许节目包的附属文件数量为0。");
								cmsLog.debug("节目包的处理状态修改为：8 - 失败");
								progPackage.setDealstate((long)8);
								if(!needUpdatePps.contains(progPackage))
								{
									needUpdatePps.add(progPackage);
								}
							}
						}
					}
					else
					{
						cmsLog.debug("节目包附属文件不符合迁移状态规则，或不需要迁移，不处理。");
					}
					
					/**
					 * ************* 处理主文件 **********************************************
					 */
					if(stateOkForMain == true && productInfo != null)
					{
						if(encryptState == (long)9 || encryptState == (long)18)
						{
							productInfo.setEncryptState((long)11);
							if(!needUpdatePtis.contains(productInfo))
							{
								needUpdatePtis.add(productInfo);
							}
						}
						
						/**
						 * 查询主文件，迁移主文件。H264 / RMZIP
						 * 需要区分ts文件和key文件的文件位置表记录，
						 */
						cmsLog.debug("查询主文件，迁移主文件...");
						List pfs = packageFilesManager.getProgramFilesesByProductidProgrank(
								progPackage.getProductid(), 
								(long)1				// 主文件标识，0 - 不是;  1 - 是
								);
						if(pfs != null && pfs.size() > 0)
						{
							ProgramFiles mainPf = (ProgramFiles)pfs.get(0);
							
							/**
							 * programFiles是主文件
							 */
							
							
							boolean needMigration = false;
							int fileorfolder = 0;	// 0 - file ; 1 - folder
							String sourcepath = null; 		// 节目包主文件的源路径
							String destpath = null; 		// 节目包主文件的目标路径
							String filepathinxml = null;			
							Date dfiledate = null;
							String filedateinxml = null;
							String sourcefileinxml = null;
							String desfileinxml = null;
							String srcstclasscode = null;			// 源文件的存储等级code			
							AmsStorage sourceamsst = null;
							AmsStoragePrgRel sourceamsstpr = null;
							AmsStorageDir sourceamsstd = null;
							AmsStorageClass sourceamsstc = null;
							AmsStorage destamsst = null;
							AmsStorageDir destamsstd = null;
							AmsStorageClass destamsstc = null;
							
							srcstclasscode = stclasscodeCaOnline;
							
							
							cmsLog.debug("查询文件的源路径...");
							/**
							 * 通过文件的filetype，来区分ts文件和key文件
							 */ 
							List sourcepaths = packageFilesManager
												.getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
														productInfo.getId(),
														srcstclasscode, 	// 存储体等级code
														progPackage.getProductid(),
														mainPf.getFiletypeid()
														);
							if (sourcepaths != null && sourcepaths.size() >= 2)
							{
								sourcepath = (String) sourcepaths.get(0);
								List list = (List) sourcepaths.get(1);
								Object[] sourcerows = (Object[]) list.get(0);
								sourceamsst = (AmsStorage) sourcerows[0];
								sourceamsstpr = (AmsStoragePrgRel) sourcerows[1];
								sourceamsstd = (AmsStorageDir) sourcerows[2];
								sourceamsstc = (AmsStorageClass) sourcerows[3];
								
								/**
								 * 文件的filedate，从源文件位置表记录获取
								 */
								dfiledate = sourceamsstpr.getFiledate();
								filedateinxml = fileopr.convertDateToString(dfiledate, "yyyy-MM-dd HH:mm:ss");
								
								/**
								 * 生成filepath
								 */
								filepathinxml = sourceamsstpr.getFilepath();
							}
							else
							{
								cmsLog.warn("查询文件源路径失败。");
								sourcepath = null;
							}
			
							/**
							 * 查询文件的目标路径(Online)...
							 */
							cmsLog.debug("查询文件的目标路径(Online)...");
							List destpaths = packageFilesManager
												.getDestPathByFilecodeStclasscode(
														mainPf.getFilecode(), // 节目包文件的filecode
														stclasscodeOnline // 一级库存储体等级code，从配置文件获取
												);
							if (destpaths != null && destpaths.size() >= 2)
							{
								destpath = (String) destpaths.get(0);
								List list1 = (List) destpaths.get(1);
								Object[] destrows = (Object[]) list1.get(0);
								destamsst = (AmsStorage) destrows[0];
								destamsstd = (AmsStorageDir) destrows[1];
								destamsstc = (AmsStorageClass) destrows[2];
								
								// 处理destpath，加上filepath和文件名
								destpath = destpath.replace('\\', '/');
								destpath = fileopr.checkPathFormatRear(destpath, '/');
								if (filepathinxml != null && !"".equalsIgnoreCase(filepathinxml)) 
								{
									destpath += filepathinxml;
								}
								destpath = destpath.replace('\\', '/');
								destpath = fileopr.checkPathFormatRear(destpath, '/');
								destpath += mainPf.getFilename();
							}
							else
							{
								cmsLog.warn("查询文件目标路径失败。");
								destpath = null;
							}
							
							cmsLog.debug("源路径 - " + sourcepath);
							cmsLog.debug("目标路径 - " + destpath);
							
							/**
							 * 判断主文件是否存在于一级库（文件位置表、存储）
							 * 判断主文件的源路径、目标路径和源文件
							 */
							boolean pathfileOk = false;
							if(sourcepath == null
								|| destpath == null
								|| "".equalsIgnoreCase(sourcepath)
								|| "".equalsIgnoreCase(destpath)
							)
							{
								cmsLog.warn("节目包文件的源路径或目标路径不合法...");
								pathfileOk = false;
							}
							else
							{
								cmsLog.debug("判断源文件是否存在...");

								if(fileopr.checkSmbFileExist(sourcepath))
								{
									cmsLog.debug("源文件存在，继续...");
									pathfileOk = true;
								}
								else
								{
									cmsLog.warn("源文件不存在...");
									pathfileOk = false;
								}
							}
							if(pathfileOk == false)
							{
//								if(ppState == (long)-1)
//								{
//									// 不处理
//									cmsLog.debug("节目包状态是-1未导入，节目包的处理状态不修改。");
//								}
//								if(ppState == (long)2)
//								{
//									cmsLog.debug("节目包的处理状态修改为：8 - 失败");
//									progPackage.setDealstate((long)8);
//									if(!needUpdatePps.contains(progPackage))
//									{
//										needUpdatePps.add(progPackage);
//									}
//								}
								
								productInfo.setEncryptState(encryptState);
								if(!needUpdatePtis.contains(productInfo))
								{
									needUpdatePtis.add(productInfo);
								}
								cmsLog.debug("不处理该文件，跳过...");
								continue;
							}

							/**
							 * 处理路径格式，去掉头
							 * smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
							 * /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
							 */ 
							sourcefileinxml = sourcepath.substring(sourcepath.indexOf("/", 6),sourcepath.length());
							desfileinxml = destpath.substring(destpath.indexOf("/", 6),destpath.length());
							if(fileorfolder == 1)
							{
								sourcefileinxml = sourcefileinxml.replace('\\', '/');
								sourcefileinxml = fileopr.checkPathFormatRear(sourcefileinxml, '/');
								desfileinxml = desfileinxml.replace('\\', '/');
								desfileinxml = fileopr.checkPathFormatRear(desfileinxml, '/');
							}

							/**
							 * 根据文件id、存储等级code，查询文件是否存在于文件位置表
							 */
							cmsLog.debug("节目包文件的源路径、目标路径和源文件正确...");
							cmsLog.debug("判断该文件是否已经存在于一级库...");
							cmsLog.debug("查询该文件的存储位置表记录（一级库）...");
							boolean existOnline = false;
							AmsStorageClass asc = null;
							AmsStorage amsStorage = null;
							AmsStoragePrgRel aspr = null;
							List<AmsStorageClass> ascs = amsstorageclassManager.findByProperty("stclasscode", stclasscodeOnline);
							if(ascs != null && ascs.size() > 0)
							{
								asc = ascs.get(0);
							}
							if(asc != null)
							{
								List<AmsStorage> amsStorages = amsstorageManager.findByProperty("stclassglobalid", asc.getStclassglobalid());
								if(amsStorages != null && amsStorages.size() > 0)
								{
									amsStorage = amsStorages.get(0);
								}
							}
							if(amsStorage != null)
							{
								/**
								 * 需要区分ts文件和key文件
								 */ 
								List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
										productInfoId,				//programFiles.getProgfileid(), 
										amsStorage.getStglobalid()
										);
								if(asprs != null && asprs.size() > 0)
								{
									for(int i = 0; i < asprs.size(); i++)
									{
										AmsStoragePrgRel asprtemp = asprs.get(i);
										if(asprtemp.getFtypeglobalid().equalsIgnoreCase(mainPf.getFiletypeid()))
										{
											aspr = asprtemp;
											break;
										}
									}
								}
							}
							if(aspr == null)
							{
								cmsLog.debug("文件不存在于文件位置表。");
								existOnline = false;
							}
							else
							{
								cmsLog.debug("文件已经存在于文件位置表，需要检查实体文件...");
								cmsLog.debug("查询该文件的实体文件（一级库）...");
								if(fileopr.checkSmbFileExist(destpath))
								{
									cmsLog.debug("该文件的实体文件存在于一级库.");
									existOnline = true;
//									existOnlineFileCount++;
									
									
								}
								else
								{
									cmsLog.debug("该文件的实体文件不存在于一级库.");
									existOnline = false;
								}
							}
							if(pathfileOk == true
								&& existOnline == false
								)
							{
								needMigration = true;
							}
							else
							{
								needMigration = false;
							}
							
							if(needMigration == true)
							{
								cmsLog.debug("该文件加入迁移任务...");
								taskcount++;
								
								/**
								 * 处理 xml
								 */
								String isCover = "Y";
								TransferEntity transferEntity = new TransferEntity();
								TransferSource transferSource = new TransferSource();
								TransferDestination transferDestination = new TransferDestination();
								
								transferSource.setType(sourceamsst.getStorageaccstype());
								transferSource.setHostname(sourceamsst.getStorageip());
								transferSource.setPort((sourceamsst.getPort() == null) ? "" : sourceamsst.getPort().toString());
								transferSource.setUsername((sourceamsst.getLoginname() == null) ? "" : sourceamsst.getLoginname());
								transferSource.setPassword((sourceamsst.getLoginpwd() == null) ? "" : sourceamsst.getLoginpwd());
								transferSource.setSourcestorageId(sourceamsst.getStglobalid());
								transferSource.setSourceDirId(sourceamsstd.getStdirglobalid());
								transferSource.setVariableFilePath(sourceamsstpr.getFilepath());
								transferSource.setFileDate((filedateinxml == null) ? "" : filedateinxml);
								if(fileorfolder == 0)
								{
									transferSource.setFolder(sourcefileinxml);
								}
								else
								{
									transferSource.setFile(sourcefileinxml);
								}
								
								transferDestination.setType(destamsst.getStorageaccstype());
								transferDestination.setHostname(destamsst.getStorageip());
								transferDestination.setPort((destamsst.getPort() == null) ? "" : destamsst.getPort().toString());
								transferDestination.setUsername((destamsst.getLoginname() == null) ? "" : destamsst.getLoginname());
								transferDestination.setPassword((destamsst.getLoginpwd() == null) ? "" : destamsst.getLoginpwd());
								transferDestination.setDesStorageId(destamsst.getStglobalid());
								transferDestination.setDesStorageDirId(destamsstd.getStdirglobalid());
								transferDestination.setVariableFilePath((filepathinxml == null) ? "" : filepathinxml);
								transferDestination.setFileDate((filedateinxml == null) ? "" : filedateinxml);
								if(fileorfolder == 0)
								{
									transferDestination.setFolder(desfileinxml);
								}
								else
								{
									transferDestination.setFile(desfileinxml);
								}
								
								transferEntity.setSourceFileId(sourceamsstpr.getProgfileid());
								transferEntity.setSourceFileName(mainPf.getFilename());
								transferEntity.setSourceFileCode(mainPf.getFilecode());
								transferEntity.setSourceFileType(mainPf.getFiletypeid());
								transferEntity.setSourceProgid(progPackage.getProductid());
								transferEntity.setSourceProgTitle(progPackage.getProductname());
								transferEntity.setSourceStorageClass(sourceamsstc.getStclasscode());
								transferEntity.setDesStorageClass(destamsstc.getStclasscode());
								transferEntity.setPriorityDate(priorityDate);
								transferEntity.setIsCover(isCover);
								transferEntity.setMainFileTag("Y");
								transferEntity.setProgFileId(mainPf.getProgfileid());
								transferEntity.setProductInfoId(productInfoId);
								transferEntity.setTransferSource(transferSource);
								transferEntity.setTransferDestination(transferDestination);

								/**
								 * 加入一个文件的迁移任务
								 */
								transferEntities.add(transferEntity);
							}	// if(needMigration == true)
							else
							{
								cmsLog.debug("该文件不需要加入迁移任务，跳过...");
							}
							
							/**
							 * 附件判断用，如果该节目包的主文件存在于播发库，将节目包的产品信息状态修改为：19 - 播发库。
							 */ 
							if(existOnline == true)
							{
								if(productInfo != null && productInfo.getEncryptState() != (long)19)
								{
									/**
									 * 主文件已经迁移至一级库，修改产品信息的状态为19
									 */
									cmsLog.warn("节目包的主文件存在于播发库（一级库），但是节目包的产品信息状态不是“播发库”。");
									cmsLog.debug("修正产品信息状态为：19 - 播发库");
									productInfo.setEncryptState((long)19);
									if(!needFixPtis.contains(productInfo))
									{
										needFixPtis.add(productInfo);
									}
								}
							}
						}
					}
					else
					{
						cmsLog.debug("节目包主文件不符合迁移状态规则，或不需要迁移，不处理。");
					}
					
				}	// for(int j = 0; j < progPackages.size(); j++)
				
				transferTask.setTransferEntities(transferEntities);
				transferTasks.add(transferTask);	// 一个文件一个迁移任务，暂时是这样
				transferDistribution.setTransferTasks(transferTasks);
				
				if(taskcount <= 0)
				{
					String str = "需要迁移的文件数量为0，不生成迁移任务单。";
					cmsLog.debug(str);
					cmsResultDto.setErrorMessage(str);
				}
				else
				{
					Document document = generateXmlDoc(transferDistribution);
					
					/**
					 * 在本地临时目录生成迁移任务xml文件
					 * 在数据库（proglistfile）生成记录，状态无效（state1=1）
					 * 复制迁移单，从本地到目标
					 * 修改数据库状态，proglistfile和progpackage
					 */
					ProgListFile progListFile = new ProgListFile();
					progListFile.setScheduledate(scheduledate);
					progListFile.setFilename(xmlFilename);
					progListFile.setFiletype((long) 8); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
					progListFile.setInputtime(nowdate);
					progListFile.setInputmanid(operatorId);
					progListFile.setDate1(nowdate);
//					progListFile.setState1((long) 1);
//					progListFile.setState2((long) 0);
//					progListFile.setColumnxml(strxml);
					int ret = saveMigrationXmlFile(
							document, 
							destpathMigration, 
							localfile, 
							progListFile
							);
					
					if(ret == 0)
					{
						cmsLog.debug("生成迁移任务xml文件成功，准备修改节目包的处理状态...");
						for(int i = 0; i < needUpdatePps.size(); i++)
						{
							ProgPackage updatePp = needUpdatePps.get(i);
							progPackageManager.update(updatePp);
							cmsLog.debug("节目包ID：" + updatePp.getProductid());
							cmsLog.debug("节目包的处理状态为：" + updatePp.getDealstate());
						}
						
						cmsLog.debug("生成迁移任务xml文件成功，准备修改节目包的产品信息状态...");
						for(int i = 0; i < needUpdatePtis.size(); i++)
						{
							TProductInfo productInfo = needUpdatePtis.get(i);
							productinfoManager.update(productInfo);
							cmsLog.debug("产品信息ID：" + productInfo.getId());
							cmsLog.debug("产品信息状态：" + productInfo.getEncryptState());
						}
					}
					else
					{
						String str = "生成迁移任务单失败。";
						cmsLog.warn(str);
						cmsResultDto.setResultCode((long)1);
						cmsResultDto.setErrorMessage(str);
					}
				}
				
				cmsLog.debug("准备修正节目包的状态和处理状态...");
				for(int i = 0; i < needFixPps.size(); i++)
				{
					ProgPackage updatePp = needFixPps.get(i);
					progPackageManager.update(updatePp);
					cmsLog.debug("节目包ID：" + updatePp.getProductid());
					cmsLog.debug("节目包的处理状态为：" + updatePp.getDealstate());
				}
				cmsLog.debug("准备修正节目包的产品信息状态...");
				for(int i = 0; i < needFixPtis.size(); i++)
				{
					TProductInfo productInfo = needFixPtis.get(i);
					productinfoManager.update(productInfo);
					cmsLog.debug("产品信息ID：" + productInfo.getId());
					cmsLog.debug("产品信息状态：" + productInfo.getEncryptState());
				}
			}	// if(progPackages == null || progPackages.size() <= 0) else
		}	// if(destpathMigration == null || "".equalsIgnoreCase(destpathMigration)|| localfile == null|| "".equalsIgnoreCase(localfile)) else
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationFromCaonlineToOnlineByProgpackages_123 returns.");
		return cmsResultDto;
	}

	/**
	 * 20110107 10:17 1.23 加扰库 --> 一级库（播发库），生成迁移任务（以编单日期为单位）
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto saveMigrationFromCaonlineToOnline_123(
		String date,
		List<PortalColumn> portalColumns,
		String operatorId
		) 
	{
		/**
		 * 流程：
		 * 0 - 查询得到播发单xml的目标路径（迁移接口、本地临时目录）。
		 * 1 - 根据编单日期（date），查询当日栏目单详细（proglistdetail），并查询节目包信息（progpackage）
		 */

		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationFromCaonlineToOnline_123...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		String scheduledate = fileopr.convertDateToScheduleDate(date);
		List<ProgPackage> progPackages = new ArrayList();
		List<String> productinfoids = new ArrayList<String>();
		
		if(portalColumns == null || portalColumns.size() <= 0)
		{
			String str = "栏目列表为空，不生成迁移任务。";
			cmsLog.warn(str);
			cmsResultDto.setErrorMessage(str);
			cmsResultDto.setResultCode((long)1);
		}
		else
		{
			cmsLog.debug("编单日期：" + scheduledate);
			cmsLog.debug("栏目数量：" + portalColumns.size());
			for(int i = 0; i < portalColumns.size(); i++)
			{
				PortalColumn pc = portalColumns.get(i);
				
				cmsLog.debug("处理第" + (i+1) + "个栏目...");
				cmsLog.debug("栏目id：" + pc.getColumnclassid());
				cmsLog.debug("栏目名称：" + pc.getColumnname());
				cmsLog.debug("栏目code：" + pc.getDefcatcode());
				cmsLog.debug("品牌code：" + pc.getSiteCode());
				
				cmsLog.debug("查询栏目下的栏目单详细(ProgListDetail)...");
				List plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
						scheduledate, 
						pc.getColumnclassid()
						);
				if(plds == null || plds.size() <= 0)
				{
					cmsLog.debug("栏目下无栏目单详细(ProgListDetail)记录，不操作，跳过...");
				}
				else
				{
					cmsLog.debug("栏目单详细(ProgListDetail)记录数量：" + plds.size());
					for(int j = 0; j < plds.size(); j++)
					{
						ProgListDetail pld = (ProgListDetail)plds.get(j);
						// 获得节目包
						ProgPackage pp = (ProgPackage)progPackageManager.getById(pld.getProductid());

						progPackages.add(pp);
						productinfoids.add(pld.getProductInfoID());
					}	// for(int j = 0; j < plds.size(); j++)
				}	// if(plds == null || plds.size() <= 0)
			}	// for(int i = 0; i < portalColumns.size(); i++)
			
			if(progPackages != null && progPackages.size() > 0)
			{
				cmsResultDto = saveMigrationFromCaonlineToOnlineByProgpackages_123(date, progPackages,productinfoids, operatorId);
			}
			else
			{
				String str = "查询到节目包数量为0，不生成迁移任务单。";
				cmsLog.debug(str);
				cmsResultDto.setErrorMessage(str);
			}
		}
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationFromCaonlineToOnline_123 returns.");
		return cmsResultDto;
	}
	
	
	/**
	 * 20110107 10:17 1.23 加扰库 --> 一级库（播发库），完成迁移（迁移反馈）
	 */
	public synchronized CmsResultDto updateFinishMigrationFromCaonlineToOnline_123(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			)
	{
		/**
		 * 流程：
		 * 1 - 解析反馈xml
		 * 2 - 迁移成功，获取节目包下所有文件
		 * 3 - 根据节目包样式、应用代码，获取节目包的文件样式
		 * 4 - 对比节目包所有文件和节目包文件样式，获得需要迁移文件列表
		 * 5 - 判断节目包是否迁移完毕
		 * 6 - 如果迁移完毕，修改节目包状态，
		 * 7 - 如果没有迁移完毕，不修改节目包状态
		 * 8 - 增加文件位置表记录
		 * 
		 * 需要区分主文件与附属文件
		 * 主文件：修改产品信息的EncryptState
		 * 附属文件：根据文件样式判断，修改节目包的State和DealState
		 */
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> updateFinishMigrationFromCaonlineToOnline_123...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		long fileStyleCode = 2L;		// 1 - 节目预告 ; 2 - 播发单
		Date nowDate = new Date();

		if("Y".equalsIgnoreCase(result))
		{
			TransferDistribution transferDistribution = dealXmlFromMigrationModule(transferEntity);
			if(transferDistribution != null)
			{
				List<TransferTask> transferTasks = transferDistribution.getTransferTasks();
				
				for(int i = 0; i < transferTasks.size(); i++)
				{
					TransferTask transferTask = transferTasks.get(i);
					List<TransferEntity> tes = transferTask.getTransferEntities();
					
					
					cmsLog.debug("迁移结果：成功");
					if(tes != null && tes.size() > 0)
					{
						TransferEntity te = tes.get(0);
						
						String progfileid = te.getProgFileId();
						
						cmsLog.debug("TransferEntity节点数量：" + tes.size());
						cmsLog.debug("取第1个节点（从迁移反馈xml获取）...");
						cmsLog.debug("节目包ID：" + te.getSourceProgid());
						cmsLog.debug("节目包名称：" + te.getSourceProgTitle());
						cmsLog.debug("文件ID(位置表)：" + te.getSourceFileId());
						cmsLog.debug("文件ID(文件表)：" + te.getProgFileId());
						cmsLog.debug("文件名称：" + te.getSourceFileName());
						cmsLog.debug("主文件标识：" + te.getMainFileTag());
						cmsLog.debug("产品信息ID：" + te.getProductInfoId());
						
						ProgPackage progPackage = (ProgPackage) progPackageManager.getById(te.getSourceProgid());
						ProgramFiles programFiles = (ProgramFiles)programFilesManager.getById(progfileid);
						if(progPackage != null && programFiles != null)
						{
							/**
							 * 生成本地迁移文件的文件位置表记录
							 */
							/**
							 * HuangBo update by 2011年4月22日 19时5分
							 * 未使用, 注释
							 */
//							TransferSource transferSource = te.getTransferSource();
							TransferDestination transferDestination = te.getTransferDestination();
							
							
							String progFileId = te.getSourceFileId();
							String desStclasscode = te.getDesStorageClass();
							
							Date newfiledate = null;
							String sfileDate = transferDestination.getFileDate();
							if(sfileDate == null || "".equalsIgnoreCase(sfileDate))
							{
								newfiledate = null;
							}
							else
							{
								newfiledate = fileopr.convertStringToDate(sfileDate, "yyyy-MM-dd HH:mm:ss");
							}
							
							/**
							 * 根据文件id、存储等级code，查询文件位置表记录
							 */
							AmsStoragePrgRel aspr = null;
							List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
									progFileId, 
									desStclasscode
									);
							if(asprs != null && asprs.size() > 0)
							{
								/**
								 * 文件位置表记录已经存在，需要判断filedate是否与迁移文件的filedate相同
								 * 如果不同，说明文件更新，需要更新文件位置表记录
								 * 如果相同，不处理。
								 */
								
								for(int j = 0; j < asprs.size(); j++)
								{
									AmsStoragePrgRel asprtemp = asprs.get(j);
									if(asprtemp.getFtypeglobalid().equalsIgnoreCase(programFiles.getFiletypeid()))
									{
										aspr = asprtemp;
										break;
									}
								}
							}
							if(aspr != null)
							{
								cmsLog.debug("文件位置表记录已经存在，判断filedate...");
								cmsLog.debug("文件位置表ID：" + aspr.getRelid());
								Date oldfiledate = aspr.getFiledate();
								cmsLog.debug("原filedate：" + fileopr.convertDateToString(oldfiledate, "yyyy-MM-dd HH:mm:ss"));
								cmsLog.debug("新filedate：" + fileopr.convertDateToString(newfiledate, "yyyy-MM-dd HH:mm:ss"));
								aspr.setStglobalid(transferDestination.getDesStorageId()); // DesStorageId
								aspr.setStdirglobalid(transferDestination.getDesStorageDirId()); // DesStorageDirId
								aspr.setPrglobalid(te.getSourceProgid()); // SourceProgid
								aspr.setProgfileid(te.getSourceFileId()); // SourceFileId
								aspr.setFtypeglobalid(te.getSourceFileType()); // SourceFileType
								aspr.setFilename(te.getSourceFileName()); // SourceFileName
								aspr.setFilepath(transferDestination.getVariableFilePath()); // VariableFilePath
								aspr.setFiledate(newfiledate); // FileDate
								aspr.setUploadtime(nowDate); //
								aspr.setRemark("文件迁移至播发库（一级库），迁移成功，更新记录。");
								amsstorageprgrelManager.update(aspr);
								cmsLog.debug("文件位置表记录已经更新。");
							}
							else
							{
								aspr = new AmsStoragePrgRel();
								
								aspr.setStglobalid(transferDestination.getDesStorageId()); // DesStorageId
								aspr.setStdirglobalid(transferDestination.getDesStorageDirId()); // DesStorageDirId
								aspr.setPrglobalid(te.getSourceProgid()); // SourceProgid
								aspr.setProgfileid(te.getSourceFileId()); // SourceFileId
								aspr.setFtypeglobalid(te.getSourceFileType()); // SourceFileType
								aspr.setFilename(te.getSourceFileName()); // SourceFileName
								aspr.setFilepath(transferDestination.getVariableFilePath()); // VariableFilePath
								aspr.setFiledate(newfiledate); // FileDate
								aspr.setUploadtime(nowDate); //
								aspr.setInputtime(nowDate);
								aspr.setInputmanid("MigrationModule");
								aspr.setRemark("文件迁移至播发库（一级库），迁移成功，新建记录。");
								amsstorageprgrelManager.save(aspr);
								cmsLog.debug("文件位置表记录已经保存。");
								cmsLog.debug("文件位置表ID：" + aspr.getRelid());
							}
							/**
							 * 处理节目包状态
							 * 如果节目包需要迁移的所有文件都迁移完成，
							 * 	并且节目包当前状态不是“迁移失败”，
							 * 则修改节目包状态：下一状态、未处理
							 */
							cmsResultDto = updateRefreshStateOfProgPackage_123(
									progPackage, 
									fileStyleCode,
									result,
									te.getMainFileTag(),
									te.getProductInfoId()
									);
						}
						else
						{
							String str = "节目包不存在，节目包ID：" + te.getSourceProgid();
							cmsResultDto.setResultCode((long) 1);
							cmsResultDto.setErrorMessage(str);
							cmsLog.warn(str);
						}
					}
				}
			}
			else
			{
				String str = "解析迁移反馈xml失败";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		}
		else if("N".equalsIgnoreCase(result))
		{
			/**
			 * 修改节目包的处理状态为：迁移失败
			 */
			TransferDistribution transferDistribution = dealXmlFromMigrationModule(transferEntity);
			
			if(transferDistribution != null)
			{
				List<TransferTask> transferTasks = transferDistribution.getTransferTasks();
				for(int i = 0; i < transferTasks.size(); i++)
				{
					TransferTask transferTask = transferTasks.get(i);
					List<TransferEntity> tes = transferTask.getTransferEntities();
					
					cmsLog.debug("迁移结果：失败");
					if(tes != null && tes.size() > 0)
					{	
						TransferEntity te = tes.get(0);
						
						cmsLog.debug("TransferEntity节点数量：" + tes.size());
						cmsLog.debug("取第1个节点...");
						cmsLog.debug("节目包ID：" + te.getSourceProgid());
						cmsLog.debug("节目包名称：" + te.getSourceProgTitle());
						cmsLog.debug("文件ID：" + te.getSourceFileId());
						cmsLog.debug("文件名称：" + te.getSourceFileName());
						
						ProgPackage progPackage = (ProgPackage) progPackageManager.getById(te.getSourceProgid());
						if(progPackage != null)
						{
							/**
							 * 处理节目包状态
							 * 如果节目包需要迁移的所有文件都迁移完成，
							 * 	并且节目包当前状态不是“迁移失败”，
							 * 则修改节目包状态：下一状态、未处理
							 */
							cmsResultDto = updateRefreshStateOfProgPackage_123(
									progPackage, 
									fileStyleCode,
									result,
									te.getMainFileTag(),
									te.getProductInfoId()
									);
						}
						else
						{
							String str = "节目包不存在，节目包ID：" + te.getSourceProgid();
							cmsResultDto.setResultCode((long) 1);
							cmsResultDto.setErrorMessage(str);
							cmsLog.warn(str);
						}
					}
					else
					{
						String str = "迁移模块反馈xml内容有误，TransferEntity节点数量为0。";
						cmsResultDto.setResultCode((long) 1);
						cmsResultDto.setErrorMessage(str);
						cmsLog.warn(str);
					}
				}
			}
			else
			{
				String str = "解析迁移反馈xml失败";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		}
		else
		{
			String str = "未知输入结果。result：" + result;
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> updateFinishMigrationFromCaonlineToOnline_123 returns.");
		return cmsResultDto;
	}
	
	
	/**
	 * 20110107 10:17 1.23 加扰库 --> 一级库（播发库），检验编单中节目包是否符合发送规则
	 * @param date，编单日期，格式："yyyy-MM-dd"
	 * @return 0 - 检验通过；1 - 检验不通过
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto checkMigrationCanSend_123(
			String date						// yyyy-MM-dd
			)
	{
		/**
		 * 1 - 根据date，查询得到当日编单的节目包list
		 * 2 - 循环判断每个节目包的state和dealstate，是否是3播发库0未处理
		 * 3 - 如果是，通过
		 * 4 - 如果不是，继续判断是否是-1未导入，
		 * 5 - 如果是，通过
		 * 6 - 如果不是，不通过，说明节目包尚未迁移完成。
		 */
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> checkMigrationCanSend_123...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsLog.debug("编单日期：" + date);
		
		boolean checkOk = true;
		String errorMessage = "";
		String scheduledate = fileopr.convertDateToScheduleDate(date);
		
		List<PortalColumn> PortalColumns = portalColumnManager.findByProperty("validFlag", 0L);
		if(PortalColumns != null && PortalColumns.size() > 0)
		{
			cmsLog.debug("查询到栏目数量：" + PortalColumns.size());
			for(int i = 0; i < PortalColumns.size(); i++)
			{
				PortalColumn portalColumn = PortalColumns.get(i);
				cmsLog.debug("处理栏目记录：" + (i+1));
				cmsLog.debug("栏目ID：" + portalColumn.getColumnclassid());
				cmsLog.debug("栏目名称：" + portalColumn.getColumnname());
				cmsLog.debug("栏目code：" + portalColumn.getDefcatcode());
				cmsLog.debug("所属品牌：" + portalColumn.getSiteCode());
				
				if("Y".equalsIgnoreCase(portalColumn.getIsleaf()))
				{
					cmsLog.debug("栏目是叶子节点栏目，查询栏目当日的栏目单详细...");
					List<ProgListDetail> plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
							scheduledate, portalColumn.getColumnclassid());
					if(plds != null && plds.size() > 0)
					{
						cmsLog.debug("查询到栏目单详细数量：" + plds.size());
						for(int j = 0; j < plds.size(); j++)
						{
							ProgListDetail pld = plds.get(j);
							ProgPackage pp = (ProgPackage)progPackageManager.getById(pld.getProductid());
							TProductInfo productInfo = productinfoManager.getTProductInfoById(pld.getProductInfoID());
							cmsLog.debug("处理栏目单详细记录：" + (j+1));
							if(pp != null && productInfo != null)
							{
								boolean checkAccOk = true;
								boolean checkMainOk = true;
								
								cmsLog.debug("节目包ID：" + pp.getProductid());
								cmsLog.debug("节目包名称：" + pp.getProductname());
								cmsLog.debug("节目包state：" + pp.getState());
								cmsLog.debug("节目包dealstate：" + pp.getDealstate());
								cmsLog.debug("节目包mainfileflag：" + pp.getMainFileFlag());
								
								/**
								 * 检查附属文件状态（节目包state和dealstate）
								 */
								try
								{
									Long state = pp.getState();
									Long dealstate = pp.getDealstate();
									if(state == (long)3 && dealstate == (long)0)
									{
										checkAccOk = true;
										cmsLog.debug("节目包状态和处理状态，符合迁移发送规则，继续...");
									}
									else if(state == (long)-1)
									{
										if(pp.getMainFileFlag() != null && pp.getMainFileFlag() == (long)-1)
										{
											checkAccOk = true;
											cmsLog.debug("节目包状态和处理状态，符合迁移发送规则，继续...");
										}
										else
										{
											checkAccOk = false;
											String str = "节目包状态或处理状态不符合迁移发送规则，检验不通过。";
											errorMessage += str;
											cmsLog.warn(str);
//											cmsLog.debug("中止栏目单详细循环。");
//											break;
										}
									}
									else
									{
										checkAccOk = false;
										String str = "节目包状态或处理状态不符合迁移发送规则，检验不通过。";
										errorMessage += str;
										cmsLog.warn(str);
//										cmsLog.debug("中止栏目单详细循环。");
//										break;
									}
								}
								catch(Exception ex)
								{
									checkAccOk = false;
									cmsLog.error("异常：" + ex.getMessage());
									String str = "节目包状态或处理状态类型转换异常，检验不通过。";
									errorMessage += str;
									cmsLog.warn(str);
//									cmsLog.debug("中止栏目单详细循环。");
//									break;
								}
								
								/**
								 * 检查主文件状态（产品信息的encryptstate）
								 */
								try
								{
									Long encryptState = productInfo.getEncryptState();
									if(encryptState == (long)19)
									{
										checkMainOk = true;
										cmsLog.debug("产品信息状态，符合迁移发送规则，继续...");
									}
									else if(encryptState == (long)0)
									{
										if(pp.getMainFileFlag() != null && pp.getMainFileFlag() == (long)-1)
										{
											checkMainOk = true;
											cmsLog.debug("产品信息状态，符合迁移发送规则，继续...");
										}
										else
										{
											checkMainOk = false;
											String str = "产品信息状态不符合迁移发送规则，检验不通过。";
											errorMessage += str;
											cmsLog.warn(str);
										}
									}
									else
									{
										checkMainOk = false;
										String str = "产品信息状态不符合迁移发送规则，检验不通过。";
										errorMessage += str + "。";
										cmsLog.warn(str);
									}
								}
								catch(Exception ex)
								{
									checkMainOk = false;
									cmsLog.error("异常：" + ex.getMessage());
									String str = "产品信息状态类型转换异常，检验不通过。";
									errorMessage += str;
									cmsLog.warn(str);
								}
								
								/**
								 * 汇总判断结果
								 */
								if(checkAccOk == false
									|| checkMainOk == false
									)
								{
									checkOk = false;
									cmsLog.debug("中止栏目单详细循环。");
									break;
								}
							}
							else
							{
								checkOk = false;
								String str = "节目包或产品信息记录不存在，检验不通过。" + pld.getProductid();
								errorMessage += str;
								cmsLog.warn(str);
								cmsLog.debug("中止栏目单详细循环。");
								break;
							}
						}
						if(checkOk == false)
						{
							cmsLog.debug("中止栏目循环。");
							break;
						}
					}
					else
					{
						cmsLog.debug("栏目单详细记录为空。");
					}
				}
				else
				{
					cmsLog.debug("栏目不是叶子节点栏目，不处理，跳过...");
				}
			}
		}
		else
		{
			checkOk = false;
			errorMessage += "未查询到有效的叶子节点栏目，检验不通过。";
		}
		
		if(checkOk == true)
		{
			String str = "检验通过。";
			cmsLog.debug(str);
			cmsResultDto.setResultCode((long)0);
			cmsResultDto.setErrorMessage(str);
		}
		else
		{
			cmsLog.warn("检验未通过。");
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(errorMessage);
		}

		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> checkMigrationCanSend_123 returns.");
		return cmsResultDto;
	}
	
	
	//=============================================================================================
	
	
	
	//---------------------- 1.2 接口方法 -----------------------------------------------------------
	
	/**
	 * 20101203 16:33 1.2 加扰库 --> 一级库（播发库），生成迁移任务（以节目包为单位）
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto saveMigrationFromCaonlineToOnlineByProgpackages(
			String date,
			List<ProgPackage> progPackages,
			String operatorId
			)
	{
		/**
		 * 1 - 循环判断每个节目包的state和dealstate，state为-1未导入或2加扰库，dealstate为-1未导入、0未处理或8失败，可以生成迁移任务。
		 * 			1缓存库和3播发库的节目包不能生成迁移任务。（节目包state为-1未导入，MAINFILEFLAG必须为-1 - 无主体文件节目包。）
		 * 2 - 修改state为2加扰库的节目包的dealstate修改为：1处理中；state为-1未导入的节目包的dealstate不修改。
		 * 3 - 查询节目包需要迁移的文件list（根据文件样式查询）。
		 * 4 - 根据上述文件list查询每个文件在播发库是否已经存在（文件位置表记录），并且判断实体文件是否已经存在。
		 * 6 - 如果文件已经存在于播发库（文件位置表记录、实体文件都存在），则该文件不加入迁移任务，跳过；
		 * 			如果文件不存在于播发库（文件位置表记录或实体文件不存在），则继续下面操作。
		 * 7 - 附加判断，如果该节目包下所有的文件都存在于播发库，将节目包的state修改为：3播发库，dealstate修改为：0未处理。
		 * 8 - 查询该文件的源路径和目标路径。
		 * 9 - 如果文件的源路径、目标路径或源实体文件有误，则该文件不加入迁移任务，并且如果所属节目包的state为-1未导入，则不处理；
		 * 			如果所属节目包的state是2加扰库，则节目包的dealstate修改为8失败。
		 * 10 - 源路径、目标路径和实体文件都无误的文件，加入迁移任务。
		 * 11 - 判断迁移任务是否为空，如果不为空，在本地生成迁移任务xml文件，复制xml文件至迁移模块接口目录。
		 * 12 - 操作数据库，proglistfile。
		 */
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationFromCaonlineToOnlineByProgpackages...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件
		String serverOS = cc.getPropertyByName("ServerOS");		// 服务器操作系统
		String migrationXmlTemp = cc.getPropertyByName("MigrationXmlTemp"); 	// 迁移单xml本地临时目录
		
		cmsLog.debug("日期：" + date);
		cmsLog.debug("操作员：" + operatorId);
		
		String stclasscodeNearOnline = "NearOnline";	// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline";		// 加扰库存储体等级code
		String stclasscodeOnline = "Online";			// 一级库存储体等级code
		String stclasscodeMigration = "Migration";		// 迁移单的目标存储体等级code
		String filecodeMigration = "MIGXML";			// 迁移单xml的filecode
		String destpathMigration = "";					// 迁移单的目标路径
		String xmlFilename = "";						// 迁移单的文件名字
		Date nowdate = new Date();
		String localfile = null;
		String scheduledate = null;
		if(date == null || "".equalsIgnoreCase(date))
		{
			scheduledate = fileopr.convertDateToString(nowdate, "yyyyMMddHHmmss");
		}
		else
		{
			scheduledate = fileopr.convertDateToScheduleDate(date);
		}
		String createDate = fileopr.convertDateToString(nowdate, "yyyy-MM-dd HH:mm:ss");
		String requestId = fileopr.convertDateToString(nowdate, "yyyyMMddHHmmssSSSSSS");
		String priorityDate = createDate;
		long fileStyleCode = 2L;		// 1 - 节目预告 ; 2 - 播发单
		int migrationType = 1;			// 迁移至播发库@北京
		int taskcount = 0;
		List<ProgPackage> dealedPps = new ArrayList();
		List<ProgPackage> needUpdatePps = new ArrayList();
		
		xmlFilename = scheduledate + requestId + ".xml";
		
		/**
		 * 查询迁移单xml文件目标路径
		 */
		List<String> filepaths = getMigrationXmlFilepath(
				filecodeMigration, 
				stclasscodeMigration, 
				migrationXmlTemp, 
				serverOS
				);
		
		if(filepaths != null && filepaths.size() >= 2)
		{
			destpathMigration = filepaths.get(0);
			localfile = filepaths.get(1);
		}
		
		if(destpathMigration == null 
			|| "".equalsIgnoreCase(destpathMigration)
			|| localfile == null
			|| "".equalsIgnoreCase(localfile)
			)
		{
			String str = "迁移单目标路径或本地临时目录不存在，不生成迁移任务。";
			cmsLog.warn(str);
			cmsResultDto.setErrorMessage(str);
			cmsResultDto.setResultCode((long)1);
		}
		else
		{
			// 迁移xml文件目标路径查询成功。
			localfile = localfile + xmlFilename;
			destpathMigration = destpathMigration + xmlFilename;
			cmsLog.debug("迁移xml文件本地临时路径：" + localfile);
			cmsLog.debug("迁移xml文件目标路径：" + destpathMigration);
			
			cmsLog.debug("编单日期：" + scheduledate);

			if(progPackages == null || progPackages.size() <= 0)
			{
				cmsLog.debug("节目包列表为空，不操作，跳过...");
			}
			else
			{
				/**
				 * 处理 xml
				 */
				TransferDistribution transferDistribution = new TransferDistribution();
				transferDistribution.setComment("迁移任务单（加扰库 至 播发库），生成日期：" + createDate);
				transferDistribution.setProglistId(scheduledate);
				transferDistribution.setRequestId(requestId);
				transferDistribution.setType(String.valueOf(migrationType));
				transferDistribution.setCreateDate(createDate);
				
				List<TransferTask> transferTasks = new ArrayList<TransferTask>();
				TransferTask transferTask = new TransferTask();
				List<TransferEntity> transferEntities = new ArrayList<TransferEntity>();
				
				cmsLog.debug("节目包数量：" + progPackages.size());
				for(int j = 0; j < progPackages.size(); j++)
				{
					ProgPackage progPackage = (ProgPackage)progPackages.get(j);

					List<ProgramFiles> programFileses = new ArrayList<ProgramFiles>();	// 需要迁移的节目包文件
					/**
					 * HuangBo update by 2011年4月22日 19时5分
					 * 未使用, 注释
					 */
//					String contentid = "";		// 目前为主文件的progfileid
					long ppState = -2;
					long ppDealState = -2;
					long ppMainFileFlag = -2;
					try
					{
						ppState = progPackage.getState();
						ppDealState = progPackage.getDealstate();
						ppMainFileFlag = (progPackage.getMainFileFlag() == null) ? (long)1 : progPackage.getMainFileFlag();
					}
					catch(Exception e)
					{
						cmsLog.warn(e.getMessage());
					}
					/**
					 * 附件判断用，如果该节目包下所有的文件都存在于播发库，将节目包的state修改为：3播发库，dealstate修改为：0未处理。
					 */ 
					int allNeedFileCount = 0;
					int existOnlineFileCount = 0;
					
					cmsLog.debug("处理第" + (j+1) + "个节目包...");
					cmsLog.debug("节目包id：" + progPackage.getProductid());
					cmsLog.debug("节目包名称：" + progPackage.getProductname());
//					cmsLog.debug("产品信息id：" + pld.getProductname());
					cmsLog.debug("文件样式应用代码：" + fileStyleCode);
					cmsLog.debug("节目包的样式ID：" + progPackage.getStyleid());
					cmsLog.debug("节目包的品牌：" + progPackage.getSiteCode());
					cmsLog.debug("节目包的State：" + ppState);
					cmsLog.debug("节目包的DealState：" + ppDealState);
					cmsLog.debug("节目包的MainFileFlag：" + ppMainFileFlag);

					/**
					 * dealedPpids
					 * 判断节目包是否已经在本次操作中处理过，
					 * 如果处理过，不处理，跳过
					 * 如果没处理过，继续
					 */
					boolean dealed = false;
					for(int k = 0; k < dealedPps.size(); k++)
					{
						ProgPackage dealedpp = dealedPps.get(k);
						if(dealedpp.getProductid().equalsIgnoreCase(progPackage.getProductid()))
						{
							dealed = true;
							break;
						}
					}
					if(dealed == true)
					{
						cmsLog.debug("节目包已经处理过了，不处理，跳过...");
						continue;
					}
					else
					{
						dealedPps.add(progPackage);
					}
					
					/**
					 * 循环判断每个节目包的state和dealstate
					 * state为-1未导入或2加扰库，dealstate为-1未导入、0未处理或8失败，可以生成迁移任务
					 * 1缓存库和3播发库的节目包不能生成迁移任务
					 * 注：节目包state为-1未导入，MAINFILEFLAG必须为-1 - 无主体文件节目包
					 */
					boolean stateOk = false;
					if(ppDealState == (long)-1)
					{
						if(ppState == (long)-1)
						{
							if(ppMainFileFlag == (long)-1)
							{
								stateOk = true;
							}
							else
							{
								stateOk = false;
							}
						}
						else
						{
							stateOk = false;
						}
					}
					else if(ppDealState == (long)0
							|| ppDealState == (long)8
							)
					{
						if(ppState == (long)-1)
						{
							if(ppMainFileFlag == (long)-1)
							{
								stateOk = true;
							}
							else
							{
								stateOk = false;
							}
						}
						else if(ppState == (long)2)
						{
							stateOk = true;
						}
						else 
						{
							stateOk = false;
						}
					}
					else
					{
						stateOk = false;
					}
					
					if(stateOk == false)
					{
						cmsLog.debug("节目包的状态和处理状态不符合迁移条件，不处理，跳过...");
						continue;
					}
					
					/**
					 * 修改state为2加扰库的节目包的dealstate修改为：1处理中；
					 * state为-1未导入的节目包的dealstate不修改。
					 */
					cmsLog.debug("节目包的状态和处理状态符合迁移条件，继续...");
					if(ppState == (long)-1)
					{
						cmsLog.debug("节目包状态为：-1 - 未导入，节目包的处理状态不修改。");
					}
					else if(ppState == (long)2)
					{
						cmsLog.debug("设置节目包的处理状态为：1 - 处理中...");
						progPackage.setDealstate((long)1);
						needUpdatePps.add(progPackage);
					}
					else
					{
						cmsLog.warn("节目包状态不符合迁移条件，跳过...");
						continue;
					}

					/**
					 * 根据文件样式，查询需要迁移的文件列表
					 */
					cmsLog.debug("根据文件样式，查询需要迁移的文件列表...");
					programFileses = programFilesManager.getProgramFilesesByProgPackageFilestylecode(
							fileStyleManager, 
							packageFilesManager, 
							progPackage, 
							fileStyleCode
							);
					allNeedFileCount = programFileses.size();
					cmsLog.debug("需要迁移的节目包文件数量：" + allNeedFileCount);
					if(allNeedFileCount > 0)
					{
						for(int m = 0; m < allNeedFileCount; m++)
						{
							ProgramFiles pf = programFileses.get(m);
							cmsLog.debug("处理第" + (m+1) + "个节目包文件...");
							cmsLog.debug("文件id：" + pf.getProgfileid());
							cmsLog.debug("文件名称：" + pf.getFilename());
							cmsLog.debug("主文件标志：" + pf.getProgrank());
							cmsLog.debug("filecode：" + pf.getFilecode());
							cmsLog.debug("filetype：" + pf.getFiletypeid());

							boolean needMigration = false;
							int fileorfolder = 0;	// 0 - file ; 1 - folder
							String sourcepath = null; 		// 节目包主文件的源路径
							String destpath = null; 		// 节目包主文件的目标路径
							String filepathinxml = null;			
							Date dfiledate = null;
							String filedateinxml = null;
							String sourcefileinxml = null;
							String desfileinxml = null;
							String srcstclasscode = null;			// 源文件的存储等级code			
							AmsStorage sourceamsst = null;
							AmsStoragePrgRel sourceamsstpr = null;
							AmsStorageDir sourceamsstd = null;
							AmsStorageClass sourceamsstc = null;
							AmsStorage destamsst = null;
							AmsStorageDir destamsstd = null;
							AmsStorageClass destamsstc = null;
							
							/**
							 * 主文件，从加扰库获取
							 * 非主文件，从缓存库获取
							 */
							if(pf.getProgrank() == (long)1)
							{
								if("R".equalsIgnoreCase(progPackage.getProgtype()))
								{
									// 富媒体的主文件，是文件夹
									fileorfolder = 1;
								}
								if(ppState == (long)-1)
								{
									/**
									 * 如果节目包状态是未导入，节目包的主文件不处理。
									 */
									cmsLog.debug("该文件所属节目包状态是“未导入”，且该文件是节目包的主文件，不处理该文件，跳过...");
									continue;
								}
								srcstclasscode = stclasscodeCaOnline;
							}
							else
							{
								srcstclasscode = stclasscodeNearOnline;
							}
							cmsLog.debug("源文件所在存储等级code：" + srcstclasscode);

							/**
							 * 生成filepath，文件id的前10位
							 */
							filepathinxml = pf.getProgfileid();
							filepathinxml = filepathinxml.substring(0, 10);
							
							/**
							 * 查询文件的源路径(CaOnline)...
							 * 主文件：CaOnline
							 * 附属文件：NearOnline
							 * Key：CaOnline，不处理
							 */
							cmsLog.debug("查询文件的源路径...");
							List sourcepaths = packageFilesManager
												.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
														pf.getProgfileid(),
														srcstclasscode, 	// 存储体等级code
														progPackage.getProductid()
														);
							if (sourcepaths != null && sourcepaths.size() >= 2)
							{
								sourcepath = (String) sourcepaths.get(0);
								List list = (List) sourcepaths.get(1);
								Object[] sourcerows = (Object[]) list.get(0);
								sourceamsst = (AmsStorage) sourcerows[0];
								sourceamsstpr = (AmsStoragePrgRel) sourcerows[1];
								sourceamsstd = (AmsStorageDir) sourcerows[2];
								sourceamsstc = (AmsStorageClass) sourcerows[3];
								
								/**
								 * 文件的filedate，从源文件位置表记录获取
								 */
								dfiledate = sourceamsstpr.getFiledate();
								filedateinxml = fileopr.convertDateToString(dfiledate, "yyyy-MM-dd HH:mm:ss");
							}
							else
							{
								cmsLog.warn("查询文件源路径失败。");
								sourcepath = null;
							}
							
							/**
							 * 查询文件的目标路径(Online)...
							 */
							cmsLog.debug("查询文件的目标路径(Online)...");
							List destpaths = packageFilesManager
												.getDestPathByFilecodeStclasscode(
														pf.getFilecode(), // 节目包文件的filecode
														stclasscodeOnline // 一级库存储体等级code，从配置文件获取
												);
							if (destpaths != null && destpaths.size() >= 2)
							{
								destpath = (String) destpaths.get(0);
								List list1 = (List) destpaths.get(1);
								Object[] destrows = (Object[]) list1.get(0);
								destamsst = (AmsStorage) destrows[0];
								destamsstd = (AmsStorageDir) destrows[1];
								destamsstc = (AmsStorageClass) destrows[2];
								
								// 处理destpath，加上filepath和文件名
								destpath = destpath.replace('\\', '/');
								destpath = fileopr.checkPathFormatRear(destpath, '/');
								if (filepathinxml != null && !"".equalsIgnoreCase(filepathinxml)) 
								{
									destpath += filepathinxml;
								}
								destpath = destpath.replace('\\', '/');
								destpath = fileopr.checkPathFormatRear(destpath, '/');
								destpath += pf.getFilename();
							}
							else
							{
								cmsLog.warn("查询文件目标路径失败。");
								destpath = null;
							}
							
							cmsLog.debug("源路径 - " + sourcepath);
							cmsLog.debug("目标路径 - " + destpath);
							
							/**
							 * 判断文件是否存在于一级库（文件位置表、存储）
							 * 判断文件的源路径、目标路径和源文件
							 */
							boolean pathfileOk = false;
							if(sourcepath == null
								|| destpath == null
								|| "".equalsIgnoreCase(sourcepath)
								|| "".equalsIgnoreCase(destpath)
							)
							{
								cmsLog.warn("节目包文件的源路径或目标路径不合法...");
								pathfileOk = false;
							}
							else
							{
								cmsLog.debug("判断源文件是否存在...");

								if(fileopr.checkSmbFileExist(sourcepath))
								{
									cmsLog.debug("源文件存在，继续...");
									pathfileOk = true;
								}
								else
								{
									cmsLog.warn("源文件不存在...");
									pathfileOk = false;
								}
							}
							if(pathfileOk == false)
							{
								if(ppState == (long)-1)
								{
									// 不处理
									cmsLog.debug("节目包状态是-1未导入，节目包的处理状态不修改。");
								}
								if(ppState == (long)2)
								{
									cmsLog.debug("节目包的处理状态修改为：8 - 失败");
									progPackage.setDealstate((long)8);
									if(!needUpdatePps.contains(progPackage))
									{
										needUpdatePps.add(progPackage);
									}
								}
								cmsLog.debug("不处理该文件，跳过...");
								continue;
							}

							/**
							 * 处理路径格式，去掉头
							 * smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
							 * /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
							 */ 
							sourcefileinxml = sourcepath.substring(sourcepath.indexOf("/", 6),sourcepath.length());
							desfileinxml = destpath.substring(destpath.indexOf("/", 6),destpath.length());
							if(fileorfolder == 1)
							{
								sourcefileinxml = sourcefileinxml.replace('\\', '/');
								sourcefileinxml = fileopr.checkPathFormatRear(sourcefileinxml, '/');
								desfileinxml = desfileinxml.replace('\\', '/');
								desfileinxml = fileopr.checkPathFormatRear(desfileinxml, '/');
							}

							/**
							 * 根据文件id、存储等级code，查询文件是否存在于文件位置表
							 */
							cmsLog.debug("节目包文件的源路径、目标路径和源文件正确...");
							cmsLog.debug("判断该文件是否已经存在于一级库...");
							cmsLog.debug("查询该文件的存储位置表记录（一级库）...");
							boolean existOnline = false;
							AmsStorageClass asc = null;
							AmsStorage amsStorage = null;
							AmsStoragePrgRel aspr = null;
							List<AmsStorageClass> ascs = amsstorageclassManager.findByProperty("stclasscode", stclasscodeOnline);
							if(ascs != null && ascs.size() > 0)
							{
								asc = ascs.get(0);
							}
							if(asc != null)
							{
								List<AmsStorage> amsStorages = amsstorageManager.findByProperty("stclassglobalid", asc.getStclassglobalid());
								if(amsStorages != null && amsStorages.size() > 0)
								{
									amsStorage = amsStorages.get(0);
								}
							}
							if(amsStorage != null)
							{
								List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
										pf.getProgfileid(), 
										amsStorage.getStglobalid()
										);
								if(asprs != null && asprs.size() > 0)
								{
									aspr = asprs.get(0);
								}
							}
							if(aspr == null)
							{
								cmsLog.debug("文件不存在于文件位置表。");
								existOnline = false;
							}
							else
							{
								cmsLog.debug("文件已经存在于文件位置表，需要检查实体文件...");
								cmsLog.debug("查询该文件的实体文件（一级库）...");
								if(fileopr.checkSmbFileExist(destpath))
								{
									cmsLog.debug("该文件的实体文件存在于一级库.");
									existOnline = true;
									existOnlineFileCount++;
								}
								else
								{
									cmsLog.debug("该文件的实体文件不存在于一级库.");
									existOnline = false;
								}
							}
							if(pathfileOk == true
								&& existOnline == false
								)
							{
								needMigration = true;
							}
							else
							{
								needMigration = false;
							}
							
							if(needMigration == true)
							{
								cmsLog.debug("该文件加入迁移任务...");
								taskcount++;
								
								/**
								 * 处理 xml
								 */
								String isCover = "Y";
								TransferEntity transferEntity = new TransferEntity();
								TransferSource transferSource = new TransferSource();
								TransferDestination transferDestination = new TransferDestination();
								
								transferSource.setType(sourceamsst.getStorageaccstype());
								transferSource.setHostname(sourceamsst.getStorageip());
								transferSource.setPort((sourceamsst.getPort() == null) ? "" : sourceamsst.getPort().toString());
								transferSource.setUsername((sourceamsst.getLoginname() == null) ? "" : sourceamsst.getLoginname());
								transferSource.setPassword((sourceamsst.getLoginpwd() == null) ? "" : sourceamsst.getLoginpwd());
								transferSource.setSourcestorageId(sourceamsst.getStglobalid());
								transferSource.setSourceDirId(sourceamsstd.getStdirglobalid());
								transferSource.setVariableFilePath(sourceamsstpr.getFilepath());
								transferSource.setFileDate((filedateinxml == null) ? "" : filedateinxml);
								if(fileorfolder == 0)
								{
									transferSource.setFolder(sourcefileinxml);
								}
								else
								{
									transferSource.setFile(sourcefileinxml);
								}
								
								transferDestination.setType(destamsst.getStorageaccstype());
								transferDestination.setHostname(destamsst.getStorageip());
								transferDestination.setPort((destamsst.getPort() == null) ? "" : destamsst.getPort().toString());
								transferDestination.setUsername((destamsst.getLoginname() == null) ? "" : destamsst.getLoginname());
								transferDestination.setPassword((destamsst.getLoginpwd() == null) ? "" : destamsst.getLoginpwd());
								transferDestination.setDesStorageId(destamsst.getStglobalid());
								transferDestination.setDesStorageDirId(destamsstd.getStdirglobalid());
								transferDestination.setVariableFilePath((filepathinxml == null) ? "" : filepathinxml);
								transferDestination.setFileDate((filedateinxml == null) ? "" : filedateinxml);
								if(fileorfolder == 0)
								{
									transferDestination.setFolder(desfileinxml);
								}
								else
								{
									transferDestination.setFile(desfileinxml);
								}
								
								transferEntity.setSourceFileId(pf.getProgfileid());
								transferEntity.setSourceFileName(pf.getFilename());
								transferEntity.setSourceFileCode(pf.getFilecode());
								transferEntity.setSourceFileType(pf.getFiletypeid());
								transferEntity.setSourceProgid(progPackage.getProductid());
								transferEntity.setSourceProgTitle(progPackage.getProductname());
								transferEntity.setSourceStorageClass(sourceamsstc.getStclasscode());
								transferEntity.setDesStorageClass(destamsstc.getStclasscode());
								transferEntity.setPriorityDate(priorityDate);
								transferEntity.setIsCover(isCover);
								transferEntity.setTransferSource(transferSource);
								transferEntity.setTransferDestination(transferDestination);

								/**
								 * 加入一个文件的迁移任务
								 */
								transferEntities.add(transferEntity);
							}	// if(needMigration == true)
							else
							{
								cmsLog.debug("该文件不需要加入迁移任务，跳过...");
							}
						}	// for(int m = 0; m < programFileses.size(); m++)
						
						/**
						 * 附件判断用，如果该节目包下所有的文件都存在于播发库，将节目包的state修改为：3播发库，dealstate修改为：0未处理。
						 */ 
						if(allNeedFileCount == existOnlineFileCount)
						{
							if(progPackage.getState() != (long)3
								|| progPackage.getDealstate() != (long)0
								)
							{
								cmsLog.warn("节目包的文件都存在于播发库（一级库），但是节目包状态不是“播发库，未处理”。");
								cmsLog.debug("修正节目包状态为：3 - 播发库");
								cmsLog.debug("修正节目包处理状态为：0 - 未处理");
								progPackage.setState((long)3);
								progPackage.setDealstate((long)0);
								if(!needUpdatePps.contains(progPackage))
								{
									needUpdatePps.add(progPackage);
								}
							}
						}
						
					}	// if(programFileses.size() > 0)
					else
					{
						cmsLog.warn("符合文件样式的节目包文件没有查询到，不加入迁移任务，跳过...");
					}
				}	// for(int j = 0; j < progPackages.size(); j++)
				
				transferTask.setTransferEntities(transferEntities);
				transferTasks.add(transferTask);	// 一个文件一个迁移任务，暂时是这样
				transferDistribution.setTransferTasks(transferTasks);
				
				if(taskcount <= 0)
				{
					String str = "需要迁移的文件数量为0，不生成迁移任务单。";
					cmsLog.debug(str);
					cmsResultDto.setErrorMessage(str);
				}
				else
				{
					Document document = generateXmlDoc(transferDistribution);
					
					/**
					 * 在本地临时目录生成迁移任务xml文件
					 * 在数据库（proglistfile）生成记录，状态无效（state1=1）
					 * 复制迁移单，从本地到目标
					 * 修改数据库状态，proglistfile和progpackage
					 */
					ProgListFile progListFile = new ProgListFile();
					progListFile.setScheduledate(scheduledate);
					progListFile.setFilename(xmlFilename);
					progListFile.setFiletype((long) 8); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
					progListFile.setInputtime(nowdate);
					progListFile.setInputmanid(operatorId);
					progListFile.setDate1(nowdate);
//					progListFile.setState1((long) 1);
//					progListFile.setState2((long) 0);
//					progListFile.setColumnxml(strxml);
					int ret = saveMigrationXmlFile(
							document, 
							destpathMigration, 
							localfile, 
							progListFile
							);
					
					if(ret == 0)
					{
						cmsLog.debug("生成迁移任务xml文件成功，准备修改节目包的处理状态...");
						for(int i = 0; i < needUpdatePps.size(); i++)
						{
							ProgPackage updatePp = needUpdatePps.get(i);
							progPackageManager.update(updatePp);
							cmsLog.debug("节目包ID：" + updatePp.getProductid());
							cmsLog.debug("节目包的处理状态修改为：" + updatePp.getDealstate());
						}
					}
					else
					{
						String str = "生成迁移任务单失败。";
						cmsLog.warn(str);
						cmsResultDto.setResultCode((long)1);
						cmsResultDto.setErrorMessage(str);
					}
				}
			}	// if(progPackages == null || progPackages.size() <= 0) else
		}	// if(destpathMigration == null || "".equalsIgnoreCase(destpathMigration)|| localfile == null|| "".equalsIgnoreCase(localfile)) else
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationFromCaonlineToOnlineByProgpackages returns.");
		return cmsResultDto;
	}

	/**
	 * 20101203 10:26 1.2 加扰库 --> 一级库（播发库），生成迁移任务（以编单日期为单位）
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto saveMigrationFromCaonlineToOnline(
		String date,
		List<PortalColumn> portalColumns,
		String operatorId
		) 
	{
		/**
		 * 流程：
		 * 0 - 查询得到播发单xml的目标路径（迁移接口、本地临时目录）。
		 * 1 - 根据编单日期（date），查询当日栏目单详细（proglistdetail），并查询节目包信息（progpackage）
		 */

		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationFromCaonlineToOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		String scheduledate = fileopr.convertDateToScheduleDate(date);
		List<ProgPackage> progPackages = new ArrayList();
		
		if(portalColumns == null || portalColumns.size() <= 0)
		{
			String str = "栏目列表为空，不生成迁移任务。";
			cmsLog.warn(str);
			cmsResultDto.setErrorMessage(str);
			cmsResultDto.setResultCode((long)1);
		}
		else
		{
			cmsLog.debug("编单日期：" + scheduledate);
			cmsLog.debug("栏目数量：" + portalColumns.size());
			for(int i = 0; i < portalColumns.size(); i++)
			{
				PortalColumn pc = portalColumns.get(i);
				
				cmsLog.debug("处理第" + (i+1) + "个栏目...");
				cmsLog.debug("栏目id：" + pc.getColumnclassid());
				cmsLog.debug("栏目名称：" + pc.getColumnname());
				cmsLog.debug("栏目code：" + pc.getDefcatcode());
				cmsLog.debug("品牌code：" + pc.getSiteCode());
				
				cmsLog.debug("查询栏目下的栏目单详细(ProgListDetail)...");
				List plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
						scheduledate, 
						pc.getColumnclassid()
						);
				if(plds == null || plds.size() <= 0)
				{
					cmsLog.debug("栏目下无栏目单详细(ProgListDetail)记录，不操作，跳过...");
				}
				else
				{
					cmsLog.debug("栏目单详细(ProgListDetail)记录数量：" + plds.size());
					for(int j = 0; j < plds.size(); j++)
					{
						ProgListDetail pld = (ProgListDetail)plds.get(j);
						// 获得节目包
						ProgPackage pp = (ProgPackage)progPackageManager.getById(pld.getProductid());

						progPackages.add(pp);
					}	// for(int j = 0; j < plds.size(); j++)
				}	// if(plds == null || plds.size() <= 0)
			}	// for(int i = 0; i < portalColumns.size(); i++)
			
			if(progPackages != null && progPackages.size() > 0)
			{
				cmsResultDto = saveMigrationFromCaonlineToOnlineByProgpackages(date, progPackages, operatorId);
			}
			else
			{
				String str = "查询到节目包数量为0，不生成迁移任务单。";
				cmsLog.debug(str);
				cmsResultDto.setErrorMessage(str);
			}
		}
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationFromCaonlineToOnline returns.");
		return cmsResultDto;
	}
	
	
	/**
	 * 20101203 16:33 1.2 加扰库 --> 一级库（播发库），完成迁移（迁移反馈）
	 */
	public CmsResultDto updateFinishMigrationFromCaonlineToOnline(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			)
	{
		/**
		 * 流程：
		 * 1 - 解析反馈xml
		 * 2 - 迁移成功，获取节目包下所有文件
		 * 3 - 根据节目包样式、应用代码，获取节目包的文件样式
		 * 4 - 对比节目包所有文件和节目包文件样式，获得需要迁移文件列表
		 * 5 - 判断节目包是否迁移完毕
		 * 6 - 如果迁移完毕，修改节目包状态，
		 * 7 - 如果没有迁移完毕，不修改节目包状态
		 * 8 - 增加文件位置表记录
		 * 
		 */
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> updateFinishMigrationFromCaonlineToOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		long fileStyleCode = 2L;		// 1 - 节目预告 ; 2 - 播发单
		Date nowDate = new Date();

		if("Y".equalsIgnoreCase(result))
		{
			TransferDistribution transferDistribution = dealXmlFromMigrationModule(transferEntity);
			if(transferDistribution != null)
			{
				List<TransferTask> transferTasks = transferDistribution.getTransferTasks();
				
				for(int i = 0; i < transferTasks.size(); i++)
				{
					TransferTask transferTask = transferTasks.get(i);
					List<TransferEntity> tes = transferTask.getTransferEntities();
					
					
					cmsLog.debug("迁移结果：成功");
					if(tes != null && tes.size() > 0)
					{
						TransferEntity te = tes.get(0);
						
						cmsLog.debug("TransferEntity节点数量：" + tes.size());
						cmsLog.debug("取第1个节点（从迁移反馈xml获取）...");
						cmsLog.debug("节目包ID：" + te.getSourceProgid());
						cmsLog.debug("节目包名称：" + te.getSourceProgTitle());
						cmsLog.debug("文件ID：" + te.getSourceFileId());
						cmsLog.debug("文件名称：" + te.getSourceFileName());
						
						ProgPackage progPackage = (ProgPackage) progPackageManager.getById(te.getSourceProgid());
						if(progPackage != null)
						{
							/**
							 * 生成本地迁移文件的文件位置表记录
							 */
							/**
							 * HuangBo update by 2011年4月22日 19时5分
							 * 未使用, 注释
							 */
//							TransferSource transferSource = te.getTransferSource();
							TransferDestination transferDestination = te.getTransferDestination();
							
							
							String progFileId = te.getSourceFileId();
							String desStclasscode = te.getDesStorageClass();
							
							Date newfiledate = null;
							String sfileDate = transferDestination.getFileDate();
							if(sfileDate == null || "".equalsIgnoreCase(sfileDate))
							{
								newfiledate = null;
							}
							else
							{
								newfiledate = fileopr.convertStringToDate(sfileDate, "yyyy-MM-dd HH:mm:ss");
							}
							
							/**
							 * 根据文件id、存储等级code，查询文件位置表记录
							 */
							List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
									progFileId, 
									desStclasscode
									);
							if(asprs != null && asprs.size() > 0)
							{
								/**
								 * 文件位置表记录已经存在，需要判断filedate是否与迁移文件的filedate相同
								 * 如果不同，说明文件更新，需要更新文件位置表记录
								 * 如果相同，不处理。
								 */
								AmsStoragePrgRel aspr = asprs.get(0);
								cmsLog.debug("文件位置表记录已经存在，判断filedate...");
								cmsLog.debug("文件位置表ID：" + aspr.getRelid());
								Date oldfiledate = aspr.getFiledate();
								cmsLog.debug("原filedate：" + fileopr.convertDateToString(oldfiledate, "yyyy-MM-dd HH:mm:ss"));
								cmsLog.debug("新filedate：" + fileopr.convertDateToString(newfiledate, "yyyy-MM-dd HH:mm:ss"));
								aspr.setStglobalid(transferDestination.getDesStorageId()); // DesStorageId
								aspr.setStdirglobalid(transferDestination.getDesStorageDirId()); // DesStorageDirId
								aspr.setPrglobalid(te.getSourceProgid()); // SourceProgid
								aspr.setProgfileid(te.getSourceFileId()); // SourceFileId
								aspr.setFtypeglobalid(te.getSourceFileType()); // SourceFileType
								aspr.setFilename(te.getSourceFileName()); // SourceFileName
								aspr.setFilepath(transferDestination.getVariableFilePath()); // VariableFilePath
								aspr.setFiledate(newfiledate); // FileDate
								aspr.setUploadtime(nowDate); //
								aspr.setRemark("文件迁移至播发库（一级库），迁移成功，更新记录。");
								amsstorageprgrelManager.update(aspr);
								cmsLog.debug("文件位置表记录已经更新。");
							}
							else
							{
								AmsStoragePrgRel aspr = new AmsStoragePrgRel();
								
								aspr.setStglobalid(transferDestination.getDesStorageId()); // DesStorageId
								aspr.setStdirglobalid(transferDestination.getDesStorageDirId()); // DesStorageDirId
								aspr.setPrglobalid(te.getSourceProgid()); // SourceProgid
								aspr.setProgfileid(te.getSourceFileId()); // SourceFileId
								aspr.setFtypeglobalid(te.getSourceFileType()); // SourceFileType
								aspr.setFilename(te.getSourceFileName()); // SourceFileName
								aspr.setFilepath(transferDestination.getVariableFilePath()); // VariableFilePath
								aspr.setFiledate(newfiledate); // FileDate
								aspr.setUploadtime(nowDate); //
								aspr.setInputtime(nowDate);
								aspr.setInputmanid("MigrationModule");
								aspr.setRemark("文件迁移至播发库（一级库），迁移成功，新建记录。");
								amsstorageprgrelManager.save(aspr);
								cmsLog.debug("文件位置表记录已经保存。");
								cmsLog.debug("文件位置表ID：" + aspr.getRelid());
							}
							/**
							 * 处理节目包状态
							 * 如果节目包需要迁移的所有文件都迁移完成，
							 * 	并且节目包当前状态不是“迁移失败”，
							 * 则修改节目包状态：下一状态、未处理
							 */
							cmsResultDto = updateRefreshStateOfProgPackage(
									progPackage, 
									fileStyleCode,
									result
									);
						}
						else
						{
							String str = "节目包不存在，节目包ID：" + te.getSourceProgid();
							cmsResultDto.setResultCode((long) 1);
							cmsResultDto.setErrorMessage(str);
							cmsLog.warn(str);
						}
					}
				}
			}
			else
			{
				String str = "解析迁移反馈xml失败";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		}
		else if("N".equalsIgnoreCase(result))
		{
			/**
			 * 修改节目包的处理状态为：迁移失败
			 */
			TransferDistribution transferDistribution = dealXmlFromMigrationModule(transferEntity);
			
			if(transferDistribution != null)
			{
				List<TransferTask> transferTasks = transferDistribution.getTransferTasks();
				for(int i = 0; i < transferTasks.size(); i++)
				{
					TransferTask transferTask = transferTasks.get(i);
					List<TransferEntity> tes = transferTask.getTransferEntities();
					
					cmsLog.debug("迁移结果：失败");
					if(tes != null && tes.size() > 0)
					{	
						TransferEntity te = tes.get(0);
						
						cmsLog.debug("TransferEntity节点数量：" + tes.size());
						cmsLog.debug("取第1个节点...");
						cmsLog.debug("节目包ID：" + te.getSourceProgid());
						cmsLog.debug("节目包名称：" + te.getSourceProgTitle());
						cmsLog.debug("文件ID：" + te.getSourceFileId());
						cmsLog.debug("文件名称：" + te.getSourceFileName());
						
						ProgPackage progPackage = (ProgPackage) progPackageManager.getById(te.getSourceProgid());
						if(progPackage != null)
						{
							/**
							 * 处理节目包状态
							 * 如果节目包需要迁移的所有文件都迁移完成，
							 * 	并且节目包当前状态不是“迁移失败”，
							 * 则修改节目包状态：下一状态、未处理
							 */
							cmsResultDto = updateRefreshStateOfProgPackage(
									progPackage, 
									fileStyleCode,
									result
									);
						}
						else
						{
							String str = "节目包不存在，节目包ID：" + te.getSourceProgid();
							cmsResultDto.setResultCode((long) 1);
							cmsResultDto.setErrorMessage(str);
							cmsLog.warn(str);
						}
					}
					else
					{
						String str = "迁移模块反馈xml内容有误，TransferEntity节点数量为0。";
						cmsResultDto.setResultCode((long) 1);
						cmsResultDto.setErrorMessage(str);
						cmsLog.warn(str);
					}
				}
			}
			else
			{
				String str = "解析迁移反馈xml失败";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		}
		else
		{
			String str = "未知输入结果。result：" + result;
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> updateFinishMigrationFromCaonlineToOnline returns.");
		return cmsResultDto;
	}
	
	

	/**
	 * 20101115 12:35 1.2 预备区 --> 缓存库，生成迁移任务
	 * @param programInfo
	 * @param programFiles
	 * @param sourcePartPath
	 * @param sourceStclasscode
	 * @param filePath
	 * @param destStclasscode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto saveMigrationForProgramInfo(
			ProgramInfo programInfo,
			ProgramFiles programFiles,
			String sourcePartPath,			// 源文件(相对)路径，由界面输入，以"/"或"\"结尾，认为是文件夹，其他认为是文件
			String sourceStclasscode,		// 源文件的存储体等级code
			String filePath,				// 目标文件的filePath
			String destStclasscode			// 目标文件的存储体等级code
			)
	{
		/**
		 * 判断的节目状态（-1 - 未导入；1 - 迁移失败），是否合法
		 * 生成迁移任务成功，修改
		 * 		节目表节目状态（dsflag）为：0 - 迁移中
		 * 生成迁移任务失败，修改
		 * 		节目表节目状态（dsflag）为：1 - 迁移失败
		 */
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationForProgramInfo...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件
		String serverOS = cc.getPropertyByName("ServerOS");		// 服务器操作系统
		String migrationXmlTemp = cc.getPropertyByName("MigrationXmlTemp"); 	// 迁移单xml本地临时目录
		
		String stclasscodePrepared = "Prepared";		// 不使用，节目录入存储体等级code
		/**
		 * HuangBo update by 2011年4月22日 19时5分
		 * 未使用, 注释
		 */
//		String stclasscodeOnline = "Online";			// 一级库存储体等级code
		String stclasscodeNearOnline = "NearOnline";	// 缓存库存储体等级code
		/**
		 * HuangBo update by 2011年4月22日 19时5分
		 * 未使用, 注释
		 */
//		String stclasscodeCaOnline = "CaOnline";		// 缓存库存储体等级code
//		String stclasscodeBjOnline = "BjOnline";		// 不使用，上海的北京缓存库存储体等级code
		String stclasscodeMigration = "Migration";		// 迁移单的目标存储体等级code
		String filecodeMigration = "MIGXML";			// 迁移单xml的filecode
		
		Date ddate = new Date();
		/**
		 * HuangBo update by 2011年4月22日 19时5分
		 * 未使用, 注释
		 */
//		int filecount = 0;
//		String strXml = "";								// 迁移单内容
		String localfile = null;
		String destpathMigration = "";					// 迁移单的目标路径
		String xmlFilename = "";						// 迁移单的文件名字
		String filename = "";
		String type = "3";
		
		// xml内容字段
		String proglistId = fileopr.convertDateToString(ddate, "yyyyMMdd000000");				
		String requestId = fileopr.convertDateToString(ddate, "yyyyMMddHHmmssSSSSSS");				// 当前时间，不重复
		String createDate = fileopr.convertDateToString(ddate, "yyyy-MM-dd HH:mm:ss");
		String priorityDate = fileopr.convertDateToString(ddate, "yyyy-MM-dd") + " 00:00:00";
		xmlFilename = proglistId + requestId + ".xml";
		
		/**
		 * 节目状态（dsflag）
		 * 	-1 - 未导入
		 * 	0 - 迁移中
		 * 	1 - 迁移失败
		 * 	2 - 新录入
		 * 	3 - 已包装
		 */
		if(programInfo.getDsflag() == -1		
			|| programInfo.getDsflag() == 1
			)
		{
			cmsLog.debug("准备获得迁移单的目标路径...");
			/**
			 * 查询迁移单xml文件目标路径
			 */
			List<String> filepaths = getMigrationXmlFilepath(
					filecodeMigration, 
					stclasscodeMigration, 
					migrationXmlTemp, 
					serverOS
					);
			if(filepaths != null && filepaths.size() >= 2)
			{
				destpathMigration = filepaths.get(0);
				localfile = filepaths.get(1);

				String sourcepath = "";
				AmsStorage sourceamsst = null;
				AmsStorageDir sourceamsstd = null;
				AmsStorageClass sourceamsstc = null;

				String destpath = "";
				AmsStorage destamsst = null;
				AmsStorageDir destamsstd = null;
				AmsStorageClass destamsstc = null;
				
				/**
				 * 处理xml
				 */
				TransferDistribution transferDistribution = new TransferDistribution();
				transferDistribution.setComment("迁移任务单（预备区 至 缓存库），生成日期：" + createDate);
				transferDistribution.setProglistId(proglistId);
				transferDistribution.setRequestId(requestId);
				transferDistribution.setType(type);
				transferDistribution.setCreateDate(createDate);
				
				List<TransferTask> transferTasks = new ArrayList<TransferTask>();
				TransferTask transferTask = new TransferTask();
				List<TransferEntity> transferEntities = new ArrayList<TransferEntity>();
				
				cmsLog.debug("准备获取源文件路径...");
				cmsLog.debug("参数1：" + stclasscodePrepared);
				cmsLog.debug("参数2：" + programFiles.getFilecode());
				List sources = amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
								stclasscodePrepared, // 节目录入区存储体等级code
								programFiles.getFilecode() // 节目文件的filecode
						);
				if (sources != null && sources.size() > 0) 
				{
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
					String mappath = sourceamsst.getMappath().replace('\\', '/');
					if (mappath != null
							&& !mappath.equalsIgnoreCase("")
							&& !mappath.equalsIgnoreCase("/")) {
						mappath = fileopr.checkPathFormatFirst(mappath, '/');
						sourcepath += mappath;
					}
					sourcepath = fileopr.checkPathFormatRear(sourcepath, '/');
					String dirname = sourceamsstd.getStoragedirname().replace('\\', '/');
					if (dirname != null
							&& !dirname.equalsIgnoreCase("")
							&& !dirname.equalsIgnoreCase("/")) {
						sourcepath += dirname;
					}
					sourcepath = fileopr.checkPathFormatRear(sourcepath, '/');
		
					if (sourcePartPath != null
							&& !sourcePartPath.equalsIgnoreCase("")) {
						sourcePartPath = sourcePartPath.replace('\\', '/');
						sourcepath += sourcePartPath;
		
						// 获得filename
						filename = programFiles.getProcessinstid();		// 文件记录的原始文件名称
//						int begin = sourcePartPath.lastIndexOf('/') + 1;
//						int end = sourcePartPath.length();
//						if (begin >= 0 && end >= 0) {
//							filename = sourcePartPath.substring(begin, end);
//						} else {
//							filename = "";
//						}
					} else {
						cmsLog.warn("输入源文件相对路径为空，不生成迁移任务单。");
						sourcepath = "";
					}
				}
				else
				{
					cmsLog.warn("获取文件源路径失败。");
					sourcepath = "";
				}
		
				// 调用方法2，根据stdir.filecode、配置文件在上海的北京缓存库stclasscode，获取节目包主文件迁移的目标路径
				// 返回：List
				// 1 - String 目标路径()
				// 格式："smb://hc:hc@172.23.19.66/公用/"
				// 2 - List<Object[]>
				// (AmsStorage)Object[0]
				// (AmsStorageDir)Object[1]
				// (AmsStorageClass)Object[2]
				cmsLog.debug("准备获得迁移目标文件路径...");
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
						destpath = fileopr.checkPathFormatRear(destpath, '/');
						if (filePath != null
							&& !filePath.equalsIgnoreCase(""))
						{
							destpath += filePath;
							destpath = destpath.replace('\\', '/');
							destpath = fileopr.checkPathFormatRear(destpath, '/');
						}
						destpath += programFiles.getFilename();
					}
				}
		
				cmsLog.debug("源文件路径 - " + sourcepath);
				cmsLog.debug("目标文件路径 - " + destpath);
				
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
						|| "".equalsIgnoreCase(sourcepath)
						|| "".equalsIgnoreCase(destpath)
						|| sourceamsst == null
						// || sourceamsstpr == null
						|| sourceamsstd == null
						|| sourceamsstc == null
						|| destamsst == null || destamsstd == null
						|| destamsstc == null) {
					String str = "输入参数为空，不生成迁移任务单...文件ID："
							+ programFiles.getProgfileid();
					cmsLog.warn(str);
				}
				else
				{
					TransferEntity transferEntity = new TransferEntity();
					TransferSource transferSource = new TransferSource();
					TransferDestination transferDestination = new TransferDestination();
					String isCover = "Y";
					String lastchar = sourcepath.substring(sourcepath.length() - 1);
					transferSource.setType(sourceamsst.getStorageaccstype());
					transferSource.setHostname(sourceamsst.getStorageip());
					transferSource.setPort((sourceamsst.getPort() == null) ? "" : sourceamsst.getPort().toString());
					transferSource.setUsername((sourceamsst.getLoginname() == null) ? "" : sourceamsst.getLoginname());
					transferSource.setPassword((sourceamsst.getLoginpwd() == null) ? "" : sourceamsst.getLoginpwd());
					transferSource.setSourcestorageId(sourceamsst.getStglobalid());
					transferSource.setSourceDirId(sourceamsstd.getStdirglobalid());
					transferSource.setVariableFilePath("");
					transferSource.setFileDate(createDate);
					if("/".equalsIgnoreCase(lastchar))
					{
						transferSource.setFolder(sourcepath);
					}
					else
					{
						transferSource.setFile(sourcepath);
					}
					
					transferDestination.setType(destamsst.getStorageaccstype());
					transferDestination.setHostname(destamsst.getStorageip());
					transferDestination.setPort((destamsst.getPort() == null) ? "" : destamsst.getPort().toString());
					transferDestination.setUsername((destamsst.getLoginname() == null) ? "" : destamsst.getLoginname());
					transferDestination.setPassword((destamsst.getLoginpwd() == null) ? "" : destamsst.getLoginpwd());
					transferDestination.setDesStorageId(destamsst.getStglobalid());
					transferDestination.setDesStorageDirId(destamsstd.getStdirglobalid());
					transferDestination.setVariableFilePath(filePath);
					transferDestination.setFileDate(createDate);
					if("/".equalsIgnoreCase(lastchar))
					{
						transferDestination.setFolder(destpath);
					}
					else
					{
						transferDestination.setFile(destpath);
					}

					transferEntity.setSourceFileId(programFiles.getProgfileid());
					transferEntity.setSourceFileName(filename);
					transferEntity.setSourceFileCode(programFiles.getFilecode());
					transferEntity.setSourceFileType(programFiles.getFiletypeid());
					transferEntity.setSourceProgid(programInfo.getProgramid());
					transferEntity.setSourceProgTitle(programInfo.getTitle());
					transferEntity.setSourceStorageClass(sourceamsstc.getStclasscode());
					transferEntity.setDesStorageClass(destamsstc.getStclasscode());
					transferEntity.setPriorityDate(priorityDate);
					transferEntity.setIsCover(isCover);
					transferEntity.setTransferSource(transferSource);
					transferEntity.setTransferDestination(transferDestination);
					
					transferEntities.add(transferEntity);
					
					
					transferTask.setTransferEntities(transferEntities);
					transferTasks.add(transferTask);	// 一个文件一个迁移任务，暂时是这样
					transferDistribution.setTransferTasks(transferTasks);
					
					ProgListFile progListFile = new ProgListFile();
					progListFile.setFilename(xmlFilename);
					progListFile.setDate1(new Date());
					progListFile.setInputtime(new Date());
					progListFile.setState1(0L);
					
					int tmp = saveMigrationXmlFile( 
							generateXmlDoc(transferDistribution), 
									destpathMigration + "/" + xmlFilename, 
									localfile + "/" + xmlFilename, progListFile);
					if (0 == tmp) {
						
						programInfo.setDsflag(0L); // 文件生成成功, 设置节目状态为迁移中状态
						this.programInfoManager.update(programInfo);
						cmsLog.debug("节目状态(dsflag)修改为：0");
						
						cmsResultDto.setResultCode(0L);
						cmsLog.debug("生成迁移任务XML文件成功。");
					} else {
						cmsResultDto.setResultCode(1L);
						cmsResultDto.setErrorMessage(" 生成迁移任务XML文件失败! ");
						cmsLog.warn("生成迁移任务XML文件失败! ");
					}
				}
			}
			else {
				cmsLog.warn(" 查询迁移xml目标路径失败! ");
			}
		}
		else	// if(programInfo.getDsflag() == -1 || programInfo.getDsflag() == 1)
		{
			String str = "节目的状态不是“未导入”或“迁移失败”，不生成迁移任务。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);			
		}
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveFinishMigrationFromCaonlineToOnline returns.");
		return cmsResultDto;
	}

	/**
	 * 20101115 13:10 1.2 预备区 --> 缓存库，完成迁移（迁移反馈）
	 * @param transferEntity
	 * @param result
	 * @param resultDes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto updateFinishMigrationForProgramInfo(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			)
	{
		/**
		 * 迁移反馈处理：
		 * 迁移成功，修改：
		 * 		节目表节目状态（dsflag）为：2 - 新录入
		 * 		文件表实体文件存在标识（progstatus）为：0 - 实体文件存在
		 * 		与文件相关的节目包状态为：1 - 缓存库(NearOnline)，原来状态为：-1 - 未导入
		 * 迁移失败，修改：
		 * 		节目表节目状态（dsflag）为：1 - 迁移失败
		 */
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> updateFinishMigrationForProgramInfo...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		Date nowDate = new Date();
		/**
		 * HuangBo update by 2011年4月22日 19时5分
		 * 未使用, 注释
		 */
//		String stclasscodeNearOnline = "NearOnline";		// 缓存库存储体等级code
//		String stclasscodeCaOnline = "CaOnline";			// 加扰库存储体等级code
//		String stclasscodeOnline = "Online";				// 播控库存储体等级code
		
		if ("Y".equalsIgnoreCase(result)) 
		{
			// 迁移成功
			// 新增记录，到文件存放位置表
			// 解析xml
			cmsLog.debug("迁移成功，准备修改节目状态和新增文件位置表记录...");
			cmsLog.debug("解析迁移反馈xml...");
			
			TransferDistribution transferDistribution = dealXmlFromMigrationModule(transferEntity);
			if(transferDistribution != null)
			{
				List<TransferTask> transferTasks = transferDistribution.getTransferTasks();
				for(int i = 0; i < transferTasks.size(); i++)
				{
					TransferTask transferTask = transferTasks.get(i);
					List<TransferEntity> tes = transferTask.getTransferEntities();
					
					cmsLog.debug("迁移结果：成功");
					if(tes != null && tes.size() > 0)
					{
						TransferEntity te = tes.get(0);
						/**
						 * HuangBo update by 2011年4月22日 19时5分
						 * 未使用, 注释
						 */
//						TransferSource transferSource = te.getTransferSource();
						TransferDestination transferDestination = te.getTransferDestination();
						
						cmsLog.debug("TransferEntity节点数量：" + tes.size());
						cmsLog.debug("取第1个节点（从迁移反馈xml获取）...");
						cmsLog.debug("节目ID：" + te.getSourceProgid());
						cmsLog.debug("节目名称：" + te.getSourceProgTitle());
						cmsLog.debug("文件ID：" + te.getSourceFileId());
						cmsLog.debug("文件名称：" + te.getSourceFileName());
						
						Date dfileDate = null;
						String sfileDate = transferDestination.getFileDate();
						if(sfileDate == null || "".equalsIgnoreCase(sfileDate))
						{
							dfileDate = null;
						}
						else
						{
							dfileDate = fileopr.convertStringToDate(sfileDate, "yyyy-MM-dd HH:mm:ss");
						}
						
						AmsStoragePrgRel aspr = new AmsStoragePrgRel();
						aspr.setStglobalid(transferDestination.getDesStorageId()); // DesStorageId
						aspr.setStdirglobalid(transferDestination.getDesStorageDirId()); // DesStorageDirId
						aspr.setPrglobalid(te.getSourceProgid()); // SourceProgid
						aspr.setProgfileid(te.getSourceFileId()); // SourceFileId
						aspr.setFtypeglobalid(te.getSourceFileType()); // SourceFileType
						aspr.setFilename(te.getSourceFileName()); // SourceFileName
						aspr.setFilepath(transferDestination.getVariableFilePath()); // VariableFilePath
						aspr.setFiledate(dfileDate); // FileDate
						aspr.setUploadtime(nowDate); //
						aspr.setInputmanid("MigrationModule");
						aspr.setInputtime(nowDate);
						aspr.setRemark("节目录入，迁移成功。");
						
						cmsLog.debug("节目文件的文件位置表记录的Filename字段需要修改...");
						ProgramInfo programinfo = (ProgramInfo) programInfoManager.getById(aspr.getPrglobalid());
						ProgramFiles programFiles = (ProgramFiles) programFilesManager.getById(aspr.getProgfileid());
						aspr.setFilename(programFiles.getFilename());
						
						if (programinfo != null) 
						{
							cmsLog.debug("节目ID：" + programinfo.getProgramid());
							/**
							 * 	新增，文件位置表记录
							 * 	节目表节目状态（dsflag）为：2 - 新录入
							 * 	文件表实体文件存在标识（progstatus）为：0 - 实体文件存在
							 * 	与文件相关的节目包状态为：1 - 缓存库(NearOnline)，原来状态为：-1 - 未导入
							 */
							
							amsstorageprgrelManager.save(aspr);
							cmsLog.debug("文件位置表记录已经保存。");
							cmsLog.debug("文件位置表ID：" + aspr.getRelid());
		
							programinfo.setDsflag((long) 2);
							programInfoManager.update(programinfo);
							cmsLog.debug("节目状态（dsflag）已经更新，状态：新入库(2)。");
							
							programFiles.setProgstatus((long)0);
							programFilesManager.update(programFiles);
							cmsLog.debug("文件的实体文件存在标识（progstatus）已经更新，状态：0 - 实体文件存在。");
							
							/**
							 * 根据文件，查询相关的节目包
							 */
							List<PackageFiles> pkfs = packageFilesManager.findByProperty(
									"progfileid", 
									programFiles.getProgfileid()
									);
							for(int j = 0; j < pkfs.size(); j++)
							{
								PackageFiles pkf = pkfs.get(j);
								ProgPackage pp = (ProgPackage)progPackageManager.getById(pkf.getProductid());
								if(pp.getState() == (long)-1)
								{
									pp.setState((long)1);
									progPackageManager.update(pp);
									cmsLog.debug("节目包状态（state）已经更新，状态：缓存库(1)。");
								}
								else
								{
									cmsLog.warn("节目包状态（state）不是“未导入”，不处理。");
								}
							}
						} 
						else 
						{
							String str = "节目记录不存在，节目ID：" + aspr.getPrglobalid();
							cmsResultDto.setResultCode((long) 1);
							cmsResultDto.setErrorMessage(str);
							cmsLog.warn(str);
						}
					}	// if(tes != null && tes.size() > 0)
				}	// for(int i = 0; i < transferTasks.size(); i++)
			}
			else	// if(transferDistribution != null)
			{
				String str = "解析迁移反馈xml字符串失败。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		}
		else if ("N".equalsIgnoreCase(result)) 
		{
			/**
			 * 修改节目包的处理状态为：迁移失败
			 */
			TransferDistribution transferDistribution = dealXmlFromMigrationModule(transferEntity);
			
			if(transferDistribution != null)
			{
				List<TransferTask> transferTasks = transferDistribution.getTransferTasks();
				for(int i = 0; i < transferTasks.size(); i++)
				{
					TransferTask transferTask = transferTasks.get(i);
					List<TransferEntity> tes = transferTask.getTransferEntities();
					
					cmsLog.debug("迁移结果：失败");
					
					if(tes != null && tes.size() > 0)
					{
						TransferEntity te = tes.get(0);
						
						cmsLog.debug("TransferEntity节点数量：" + tes.size());
						cmsLog.debug("取第1个节点...");
						cmsLog.debug("节目ID：" + te.getSourceProgid());
						cmsLog.debug("节目名称：" + te.getSourceProgTitle());
						cmsLog.debug("文件ID：" + te.getSourceFileId());
						cmsLog.debug("文件名称：" + te.getSourceFileName());
						
						
						ProgramInfo programinfo = (ProgramInfo) programInfoManager
														.getById(te.getSourceProgid());
						
						if (programinfo != null) 
						{
							cmsLog.debug("节目ID：" + programinfo.getProgramid());
							programinfo.setDsflag((long) 1);
							programInfoManager.update(programinfo);
							cmsLog.debug("节目状态已经更新，状态：迁移失败(1)。");
						} 
						else 
						{
							String str = "节目记录不存在，节目ID："	+ te.getSourceProgid();
							cmsResultDto.setResultCode((long) 1);
							cmsResultDto.setErrorMessage(str);
							cmsLog.warn(str);
						}
					}
				}
			}
			else	// if(transferDistribution != null)
			{
				String str = "解析迁移反馈xml字符串失败。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		} 
		else 
		{
			String str = "未知输入结果。result：" + result;
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}

		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> updateFinishMigrationForProgramInfo returns.");
		return cmsResultDto;
	}
	
	
	/**
	 * 20101209 13:52 1.2 加扰库 --> 一级库（播发库），检验编单中节目包是否符合发送规则
	 * @param date，编单日期，格式："yyyy-MM-dd"
	 * @return 0 - 检验通过；1 - 检验不通过
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto checkMigrationCanSend(
			String date						// yyyy-MM-dd
			)
	{
		/**
		 * 1 - 根据date，查询得到当日编单的节目包list
		 * 2 - 循环判断每个节目包的state和dealstate，是否是3播发库0未处理
		 * 3 - 如果是，通过
		 * 4 - 如果不是，继续判断是否是-1未导入，
		 * 5 - 如果是，通过
		 * 6 - 如果不是，不通过，说明节目包尚未迁移完成。
		 */
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> checkMigrationCanSend...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsLog.debug("编单日期：" + date);
		
		boolean checkOk = true;
		String errorMessage = "";
		String scheduledate = fileopr.convertDateToScheduleDate(date);
		
		List<PortalColumn> PortalColumns = portalColumnManager.findByProperty("validFlag", 0L);
		if(PortalColumns != null && PortalColumns.size() > 0)
		{
			cmsLog.debug("查询到栏目数量：" + PortalColumns.size());
			for(int i = 0; i < PortalColumns.size(); i++)
			{
				PortalColumn portalColumn = PortalColumns.get(i);
				cmsLog.debug("处理栏目记录：" + (i+1));
				cmsLog.debug("栏目ID：" + portalColumn.getColumnclassid());
				cmsLog.debug("栏目名称：" + portalColumn.getColumnname());
				cmsLog.debug("栏目code：" + portalColumn.getDefcatcode());
				cmsLog.debug("所属品牌：" + portalColumn.getSiteCode());
				
				if("Y".equalsIgnoreCase(portalColumn.getIsleaf()))
				{
					cmsLog.debug("栏目是叶子节点栏目，查询栏目当日的栏目单详细...");
					List<ProgListDetail> plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
							scheduledate, portalColumn.getColumnclassid());
					if(plds != null && plds.size() > 0)
					{
						cmsLog.debug("查询到栏目单详细数量：" + plds.size());
						for(int j = 0; j < plds.size(); j++)
						{
							ProgListDetail pld = plds.get(j);
							ProgPackage pp = (ProgPackage)progPackageManager.getById(pld.getProductid());
							cmsLog.debug("处理栏目单详细记录：" + (j+1));
							if(pp != null)
							{
								cmsLog.debug("节目包ID：" + pp.getProductid());
								cmsLog.debug("节目包名称：" + pp.getProductname());
								cmsLog.debug("节目包state：" + pp.getState());
								cmsLog.debug("节目包dealstate：" + pp.getDealstate());
								cmsLog.debug("节目包mainfileflag：" + pp.getMainFileFlag());
								try
								{
									Long state = pp.getState();
									Long dealstate = pp.getDealstate();
									if(state == (long)3 && dealstate == (long)0)
									{
										checkOk = true;
										cmsLog.debug("节目包状态和处理状态，符合迁移发送规则，继续...");
									}
									else if(state == (long)-1)
									{
										if(pp.getMainFileFlag() != null && pp.getMainFileFlag() == (long)-1)
										{
											checkOk = true;
											cmsLog.debug("节目包状态和处理状态，符合迁移发送规则，继续...");
										}
										else
										{
											checkOk = false;
											String str = "节目包状态或处理状态不符合迁移发送规则，检验不通过。";
											errorMessage += str + "。";
											cmsLog.warn(str);
											cmsLog.debug("中止栏目单详细循环。");
											break;
										}
									}
									else
									{
										checkOk = false;
										String str = "节目包状态或处理状态不符合迁移发送规则，检验不通过。";
										errorMessage += str + "。";
										cmsLog.warn(str);
										cmsLog.debug("中止栏目单详细循环。");
										break;
									}
								}
								catch(Exception ex)
								{
									checkOk = false;
									cmsLog.error("异常：" + ex.getMessage());
									String str = "节目包状态或处理状态类型转换异常，检验不通过。";
									errorMessage += str + "。";
									cmsLog.warn(str);
									cmsLog.debug("中止栏目单详细循环。");
									break;
								}
							}
							else
							{
								checkOk = false;
								String str = "节目包记录不存在，检验不通过。" + pld.getProductid();
								errorMessage += str + "。";
								cmsLog.warn(str);
								cmsLog.debug("中止栏目单详细循环。");
								break;
							}
						}
						if(checkOk == false)
						{
							cmsLog.debug("中止栏目循环。");
							break;
						}
					}
					else
					{
						cmsLog.debug("栏目单详细记录为空。");
					}
				}
				else
				{
					cmsLog.debug("栏目不是叶子节点栏目，不处理，跳过...");
				}
			}
		}
		else
		{
			checkOk = false;
			errorMessage += "未查询到有效的叶子节点栏目，检验不通过。";
		}
		
		if(checkOk == true)
		{
			String str = "检验通过。";
			cmsLog.debug(str);
			cmsResultDto.setResultCode((long)0);
			cmsResultDto.setErrorMessage(str);
		}
		else
		{
			cmsLog.warn("检验未通过。");
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(errorMessage);
		}

		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> checkMigrationCanSend returns.");
		return cmsResultDto;
	}
	
	
	//============================================================================================
	
	
	
	
	
	
	//------------------ 旧代码，不使用 -------------------------------------------------------------
	/**
	 * 20101109 13:23 1.21 不使用，生成迁移任务，加扰库 --> 一级库（播发库）
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto saveMigrationFromCaonlineToOnlineNOTUSE(
		String date,
		List<PortalColumn> portalColumns,
		String operatorId
		) 
	{
		/**
		 * 流程：
		 * 0 - 查询得到播发单xml的目标路径（迁移接口、本地临时目录）。
		 * 1 - 根据编单日期（date），查询当日栏目单详细（proglistdetail），并查询节目包信息（progpackage）
		 * 2 - 根据节目包的样式和样式应用code，查询文件样式（不包含key文件）
		 * 3 - 根据节目包id，查询所有的节目包的文件
		 * 4 - 对比文件样式和节目包文件列表，得到需要迁移的文件列表
		 * 5 - 根据栏目单详细，得到对应的key文件（暂时不实现）
		 * 6 - 逐一判断需要迁移的文件列表，
		 * 		查询文件的源路径（加扰库），
		 * 		查询文件的目标路径（一级库），
		 * 		是否已经存在于一级库，（文件位置记录、实体文件）
		 * 6.1 - 如果存在，不处理
		 * 6.2 - 如果不存在，继续
		 * 7 - 将文件加入迁移单（xml）
		 * 8 - 判断迁移任务是否为空
		 * 8.1 - 如果为空，不生成播发单xml
		 * 8.2 - 如果不为空，继续
		 * 9 - 生成播发单xml文件到临时目录，并且复制xml文件到目标目录
		 * 10 - 操作数据库
		 * 10.1 - 生成发布文件表记录（proglistfile），类型为8，编单日期
		 * 10.2 - 修改节目包状态
		 */
		
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationFromCaonlineToOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 配置文件
		String serverOS = cc.getPropertyByName("ServerOS");		// 服务器操作系统
		String migrationXmlTemp = cc.getPropertyByName("MigrationXmlTemp"); 	// 迁移单xml本地临时目录
		
		String stclasscodeNearOnline = "NearOnline";	// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline";		// 加扰库存储体等级code
		String stclasscodeOnline = "Online";			// 一级库存储体等级code
		String stclasscodeMigration = "Migration";		// 迁移单的目标存储体等级code
		String filecodeMigration = "MIGXML";			// 迁移单xml的filecode
		String destpathMigration = "";					// 迁移单的目标路径
		String xmlFilename = "";						// 迁移单的文件名字
		Date nowdate = new Date();
		String localfile = null;
		String scheduledate = fileopr.convertDateToScheduleDate(date);
		String createDate = fileopr.convertDateToString(nowdate, "yyyy-MM-dd HH:mm:ss");
		String requestId = fileopr.convertDateToString(nowdate, "yyyyMMddHHmmssSSSSSS");
		String priorityDate = date + " 00:00:00";
		long fileStyleCode = 2L;		// 1 - 节目预告 ; 2 - 播发单
		int migrationType = 0;
		int taskcount = 0;
		List<ProgPackage> dealedPps = new ArrayList();
		
		xmlFilename = scheduledate + requestId + ".xml";
		
		/**
		 * 查询迁移单xml文件目标路径
		 */
		List<String> filepaths = getMigrationXmlFilepath(
				filecodeMigration, 
				stclasscodeMigration, 
				migrationXmlTemp, 
				serverOS
				);
		
		if(filepaths != null && filepaths.size() >= 2)
		{
			destpathMigration = filepaths.get(0);
			localfile = filepaths.get(1);
		}
		
		if(destpathMigration == null 
			|| "".equalsIgnoreCase(destpathMigration)
			|| localfile == null
			|| "".equalsIgnoreCase(localfile)
			)
		{
			String str = "迁移单目标路径或本地临时目录不存在，不生成迁移任务。";
			cmsLog.warn(str);
			cmsResultDto.setErrorMessage(str);
			cmsResultDto.setResultCode((long)1);
		}
		else
		{
			localfile = localfile + xmlFilename;
			destpathMigration = destpathMigration + xmlFilename;
			
			if(portalColumns == null || portalColumns.size() <= 0)
			{
				String str = "栏目列表为空，不生成迁移任务。";
				cmsLog.warn(str);
				cmsResultDto.setErrorMessage(str);
				cmsResultDto.setResultCode((long)1);
			}
			else
			{
				/**
				 * 处理 xml
				 */
				Document document = DocumentHelper.createDocument();
				Element rootelement = document.addElement("Migration");		// 根节点
				rootelement.addComment("迁移任务单");						// 注释
				Element distributionElement = rootelement.addElement("Distribution");		// 品牌
				distributionElement.addAttribute("ProglistId", scheduledate);
				distributionElement.addAttribute("RequestId", requestId);
				distributionElement.addAttribute("CreateDate", createDate);
				distributionElement.addAttribute("Type", String.valueOf(migrationType));
				
				
				cmsLog.debug("编单日期：" + scheduledate);
				cmsLog.debug("栏目数量：" + portalColumns.size());
				for(int i = 0; i < portalColumns.size(); i++)
				{
					PortalColumn pc = portalColumns.get(i);
					
					cmsLog.debug("处理第" + (i+1) + "个栏目...");
					cmsLog.debug("栏目id：" + pc.getColumnclassid());
					cmsLog.debug("栏目名称：" + pc.getColumnname());
					cmsLog.debug("栏目code：" + pc.getDefcatcode());
					cmsLog.debug("品牌code：" + pc.getSiteCode());
					
					cmsLog.debug("查询栏目下的栏目单详细(ProgListDetail)...");
					List plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
							scheduledate, 
							pc.getColumnclassid()
							);
					if(plds == null || plds.size() <= 0)
					{
						cmsLog.debug("栏目下无栏目单详细(ProgListDetail)记录，不操作，跳过...");
					}
					else
					{
						cmsLog.debug("栏目单详细(ProgListDetail)记录数量：" + plds.size());
						for(int j = 0; j < plds.size(); j++)
						{
							ProgListDetail pld = (ProgListDetail)plds.get(j);
							// 获得节目包
							ProgPackage pp = (ProgPackage)progPackageManager.getById(pld.getProductid());
							
							List<ProgramFiles> programFileses = new ArrayList<ProgramFiles>();	// 需要迁移的节目包文件
							/**
							 * HuangBo update by 2011年4月22日 19时5分
							 * 未使用, 注释
							 */
//							String contentid = "";		// 目前为主文件的progfileid
							
							cmsLog.debug("处理第" + (j+1) + "个栏目单详细...");
							cmsLog.debug("节目包id：" + pld.getProductid());
							cmsLog.debug("节目包名称：" + pld.getProductname());
							cmsLog.debug("产品信息id：" + pld.getProductname());
							cmsLog.debug("文件样式应用代码：" + fileStyleCode);
							cmsLog.debug("节目包的样式ID：" + pp.getStyleid());
							
							
							/**
							 * dealedPpids
							 * 判断节目包是否已经在本次操作中处理过，
							 * 如果处理过，不处理，跳过
							 * 如果没处理过，继续
							 */
							boolean dealed = false;
							for(int k = 0; k < dealedPps.size(); k++)
							{
								ProgPackage dealedpp = dealedPps.get(k);
								if(dealedpp.getProductid().equalsIgnoreCase(pp.getProductid()))
								{
									dealed = true;
									break;
								}
							}
							if(dealed == true)
							{
								cmsLog.debug("节目包已经处理过了，不处理，跳过...");
								continue;
							}
							else
							{
								dealedPps.add(pp);
							}
							
							
							cmsLog.debug("查询文件样式...");
//							List<FileStyle> fileStyles = fileStyleManager.queryFileStylesByStyleCodeAndStyleID(fileStyleCode, pp.getStyleid());
//							if(fileStyles == null || fileStyles.size() <= 0)
//							{
//								cmsLog.warn("节目包的文件样式为空，不处理，跳过...");
//							}
//							else
//							{
//								cmsLog.debug("节目包的文件样式size：" + fileStyles.size());
//								cmsLog.debug("根据文件样式查询节目包的文件...");		// programFiles
//								for(int m = 0; m < fileStyles.size(); m++)
//								{
//									FileStyle fileStyle = fileStyles.get(m);
//									
//									cmsLog.debug("filetypeid：" + fileStyle.getFileTypeId());
//									
//									List<ProgramFiles> pfs = packageFilesManager.getProgramFilesesByProductidFiletype(
//											pp.getProductid(), 
//											fileStyle.getFileTypeId()
//											);
//									if(pfs != null && pfs.size() > 0)
//									{
//										// 取查询到的第一个
//										ProgramFiles pf = pfs.get(0);
//										programFileses.add(pf);
//										
//										// 把主文件的文件id记录下，作为contentid
//										if(pf.getProgrank() == (long)1)
//										{
//											contentid = pf.getProgfileid();
//										}
//									}
//								}
							
							/**
							 * ******************************************
							 * 5 - 根据栏目单详细，得到对应的key文件（缺少代码）
							 * ******************************************
							 */ 
							
							
							/**
							 * 根据文件样式，查询需要迁移的文件列表
							 */
							programFileses = programFilesManager.getProgramFilesesByProgPackageFilestylecode(
									fileStyleManager, 
									packageFilesManager, 
									pp, 
									fileStyleCode
									);
							cmsLog.debug("需要迁移的节目包文件数量：" + programFileses.size());
							if(programFileses.size() > 0)
							{
								for(int m = 0; m < programFileses.size(); m++)
								{
									ProgramFiles pf = programFileses.get(m);
									cmsLog.debug("处理第" + (m+1) + "个节目包文件...");
									cmsLog.debug("文件id：" + pf.getProgfileid());
									cmsLog.debug("文件名称：" + pf.getProgfileid());
									cmsLog.debug("主文件标志：" + pf.getProgrank());
									cmsLog.debug("filecode：" + pf.getFilecode());
									cmsLog.debug("filetype：" + pf.getFiletypeid());

									
									boolean needMigration = false;
									int fileorfolder = 0;	// 0 - file ; 1 - folder
									String sourcepath = null; 		// 节目包主文件的源路径
									String destpath = null; 		// 节目包主文件的目标路径
									String filepathinxml = null;			
									Date dfiledate = null;
									String filedateinxml = null;
									String sourcefileinxml = null;
									String desfileinxml = null;
									String srcstclasscode = null;			// 源文件的存储等级code			
									AmsStorage sourceamsst = null;
									AmsStoragePrgRel sourceamsstpr = null;
									AmsStorageDir sourceamsstd = null;
									/**
									 * HuangBo update by 2011年4月22日 19时5分
									 * 未使用, 注释
									 */
//									AmsStorageClass sourceamsstc = null;
									AmsStorage destamsst = null;
									AmsStorageDir destamsstd = null;
									/**
									 * HuangBo update by 2011年4月22日 19时5分
									 * 未使用, 注释
									 */
//									AmsStorageClass destamsstc = null;
									
									
									if(pf.getProgrank() == (long)1)
									{
										if("R".equalsIgnoreCase(pp.getProgtype()))
										{
											// 富媒体的主文件，是文件夹
											fileorfolder = 1;
										}
										srcstclasscode = stclasscodeCaOnline;
									}
									else
									{
										srcstclasscode = stclasscodeNearOnline;
									}
									
									
									/**
									 * 生成filepath，文件id的前10位
									 */
									filepathinxml = pf.getProgfileid();
									filepathinxml = filepathinxml.substring(0, 10);
									
									/**
									 * 查询文件的源路径(CaOnline)...
									 * 主文件：CaOnline
									 * 附属文件：NearOnline
									 * Key：CaOnline
									 */
									cmsLog.debug("查询文件的源路径(CaOnline)...");
									List sourcepaths = packageFilesManager
														.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
																pf.getProgfileid(),
																srcstclasscode, 	// 存储体等级code
																pp.getProductid()
																);
									if (sourcepaths != null && sourcepaths.size() >= 2)
									{
										sourcepath = (String) sourcepaths.get(0);
										List list = (List) sourcepaths.get(1);
										Object[] sourcerows = (Object[]) list.get(0);
										sourceamsst = (AmsStorage) sourcerows[0];
										sourceamsstpr = (AmsStoragePrgRel) sourcerows[1];
										sourceamsstd = (AmsStorageDir) sourcerows[2];
										/**
										 * HuangBo update by 2011年4月22日 19时5分
										 * 未使用, 注释
										 */
//										sourceamsstc = (AmsStorageClass) sourcerows[3];
										
										/**
										 * 文件的filedate，从源文件位置表记录获取
										 */
										dfiledate = sourceamsstpr.getFiledate();
										filedateinxml = fileopr.convertDateToString(dfiledate, "yyyy-MM-dd HH:mm:ss");
									}
									else
									{
										cmsLog.warn("查询文件源路径失败。");
										sourcepath = null;
									}
									
									/**
									 * 查询文件的目标路径(Online)...
									 */
									cmsLog.debug("查询文件的目标路径(Online)...");
									List destpaths = packageFilesManager
														.getDestPathByFilecodeStclasscode(
																pf.getFilecode(), // 节目包文件的filecode
																stclasscodeOnline // 一级库存储体等级code，从配置文件获取
														);
									if (destpaths != null && destpaths.size() >= 2)
									{
										destpath = (String) destpaths.get(0);
										List list1 = (List) destpaths.get(1);
										Object[] destrows = (Object[]) list1.get(0);
										destamsst = (AmsStorage) destrows[0];
										destamsstd = (AmsStorageDir) destrows[1];
										/**
										 * HuangBo update by 2011年4月22日 19时5分
										 * 未使用, 注释
										 */
//										destamsstc = (AmsStorageClass) destrows[2];
										
										// 处理destpath，加上filepath和文件名
										destpath = destpath.replace('\\', '/');
										destpath = fileopr.checkPathFormatRear(destpath, '/');
										if (filepathinxml != null && !"".equalsIgnoreCase(filepathinxml)) 
										{
											destpath += filepathinxml;
										}
										destpath = destpath.replace('\\', '/');
										destpath = fileopr.checkPathFormatRear(destpath, '/');
										destpath += pf.getFilename();
									}
									else
									{
										cmsLog.warn("查询文件目标路径失败。");
										destpath = null;
									}
									
									/**
									 * 判断文件是否存在（文件位置表、存储）
									 */
									if(sourcepath == null
										|| destpath == null
										|| "".equalsIgnoreCase(sourcepath)
										|| "".equalsIgnoreCase(destpath)
									)
									{
										cmsLog.warn("节目包文件的源路径或目标路径不合法，不处理该文件，跳过...");
										continue;
									}
									else
									{
										cmsLog.debug("判断该文件是否已经存在于一级库...");
										
										
										/**
										 * 处理路径格式，去掉头
										 * smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
										 * /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
										 */ 
										sourcefileinxml = sourcepath.substring(
												sourcepath.indexOf("/", 6),
												sourcepath.length());
										desfileinxml = destpath.substring(
												destpath.indexOf("/", 6),
												destpath.length());
										if(fileorfolder == 1)
										{
											sourcefileinxml = sourcefileinxml.replace('\\', '/');
											sourcefileinxml = fileopr.checkPathFormatRear(sourcefileinxml, '/');
											desfileinxml = desfileinxml.replace('\\', '/');
											desfileinxml = fileopr.checkPathFormatRear(desfileinxml, '/');
										}
										
										
										
										/**
										 * 根据文件id、存储等级code，查询文件是否存在于文件位置表
										 */
										cmsLog.debug("查询该文件的存储位置表记录（一级库）...");
										AmsStorageClass asc = null;
										AmsStorage amsStorage = null;
										AmsStoragePrgRel aspr = null;
										List<AmsStorageClass> ascs = amsstorageclassManager.findByProperty("stclasscode", stclasscodeOnline);
										if(ascs != null && ascs.size() > 0)
										{
											asc = ascs.get(0);
										}
										if(asc != null)
										{
											List<AmsStorage> amsStorages = amsstorageManager.findByProperty("stclassglobalid", asc.getStclassglobalid());
											if(amsStorages != null && amsStorages.size() > 0)
											{
												amsStorage = amsStorages.get(0);
											}
										}
										if(amsStorage != null)
										{
											List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
													pf.getProgfileid(), 
													amsStorage.getStglobalid()
													);
											if(asprs != null && asprs.size() > 0)
											{
												aspr = asprs.get(0);
											}
										}
										if(aspr == null)
										{
											cmsLog.debug("文件不存在于文件位置表，需要加入迁移任务。");
											needMigration = true;
										}
										else
										{
											cmsLog.debug("文件已经存在于文件位置表，需要检查实体文件。");
										}
										
										if(needMigration == true)
										{
											// 继续
										}
										else
										{
											/**
											 * 查询实体文件是否存在于一级库
											 */
											cmsLog.debug("查询该文件的实体文件（一级库）...");
											if(fileopr.checkSmbFileExist(destpath))
											{
												cmsLog.debug("该文件的实体文件存在于一级库），不需要加入迁移任务，跳过...");
												needMigration = false;
											}
											else
											{
												cmsLog.debug("该文件的实体文件不存在于一级库，需要加入迁移任务...");
												needMigration = true;
											}
										}
									}
									
									if(needMigration == true)
									{
										cmsLog.debug("该文件加入迁移任务...");
										taskcount++;
										
										/**
										 * 处理 xml
										 */
										Element transferEntityElement = distributionElement.addElement("Transfer-Entity");
										transferEntityElement.addAttribute("SourceProgid", pp.getProductid());			// 节目包id
										transferEntityElement.addAttribute("SourceProgTitle", pp.getProductname());		// 节目包名称	
										transferEntityElement.addAttribute("SourceFileId", pf.getProgfileid());			// 文件id
										transferEntityElement.addAttribute("SourceFileName", pf.getFilename());			// 文件名称
										transferEntityElement.addAttribute("SourceFileType", pf.getFiletypeid());		// filetype
										transferEntityElement.addAttribute("SourceFileCode", pf.getFilecode());			// filecode
										transferEntityElement.addAttribute("SourceStorageClass", stclasscodeCaOnline);	// 源存储等级code			
										transferEntityElement.addAttribute("DesStorageClass", stclasscodeOnline);		// 目标存储等级code	
										transferEntityElement.addAttribute("PriorityDate", priorityDate);
										transferEntityElement.addAttribute("IsCover", "N");
										
										Element SourceElement = transferEntityElement.addElement("Source");
										SourceElement.addAttribute("Type", sourceamsst.getStorageaccstype());
										SourceElement.addAttribute("Hostname", sourceamsst.getStorageip());
										SourceElement.addAttribute("Port", (sourceamsst.getPort() == null) ? "" : String.valueOf((long)sourceamsst.getPort()));	
										SourceElement.addAttribute("Username", (sourceamsst.getLoginname() == null) ? "" : sourceamsst.getLoginname());
										SourceElement.addAttribute("Password", (sourceamsst.getLoginpwd() == null) ? "" : sourceamsst.getLoginpwd());
										SourceElement.addAttribute("SourcestorageId", sourceamsst.getStglobalid());
										SourceElement.addAttribute("SourceDirId", sourceamsstd.getStdirglobalid());
										SourceElement.addAttribute("VariableFilePath", sourceamsstpr.getFilepath());
										SourceElement.addAttribute("FileDate", (filedateinxml == null) ? "" : filedateinxml);
										if(fileorfolder == 0)
										{
											// 视频类节目包，主文件为文件
											Element sfileElement = SourceElement.addElement("File");
											sfileElement.setText(sourcefileinxml);
										}
										else
										{
											// 富媒体类节目包，主文件为文件夹
											Element sfolderElement = SourceElement.addElement("Folder");
											sfolderElement.setText(sourcefileinxml);
										}
										
										Element destinationElement = transferEntityElement.addElement("Destination");
										destinationElement.addAttribute("Type", destamsst.getStorageaccstype());
										destinationElement.addAttribute("Hostname", destamsst.getStorageip());
										destinationElement.addAttribute("Port", (destamsst.getPort() == null) ? "" : String.valueOf((long)destamsst.getPort()));
										destinationElement.addAttribute("Username", (destamsst.getLoginname() == null) ? "" : destamsst.getLoginname());
										destinationElement.addAttribute("Password", (destamsst.getLoginpwd() == null) ? "" : destamsst.getLoginpwd());
										destinationElement.addAttribute("DesStorageId", destamsst.getStglobalid());
										destinationElement.addAttribute("DesStorageDirId", destamsstd.getStdirglobalid());
										destinationElement.addAttribute("VariableFilePath", (filepathinxml == null) ? "" : filepathinxml);
										destinationElement.addAttribute("FileDate", (filedateinxml == null) ? "" : filedateinxml);
										if(fileorfolder == 0)
										{
											// 视频类节目包，主文件为文件
											Element dfileElement = destinationElement.addElement("File");
											dfileElement.setText(desfileinxml);
										}
										else
										{
											// 富媒体类节目包，主文件为文件夹
											Element dfolderElement = destinationElement.addElement("Folder");
											dfolderElement.setText(desfileinxml);
										}
									}
									else
									{
										cmsLog.debug("该文件不需要加入迁移任务，跳过...");
									}
								}	// for(int m = 0; m < programFileses.size(); m++)
							}	// if(programFileses.size() > 0)
							else
							{
								cmsLog.warn("符合文件样式的节目包文件没有查询到，不加入迁移任务，跳过...");
							}
//							}	// if(fileStyles == null || fileStyles.size() <= 0)
						}	// for(int j = 0; j < plds.size(); j++)
					}	// if(plds == null || plds.size() <= 0)
				}	// for(int i = 0; i < portalColumns.size(); i++)
				
				
				/**
				 * 在本地临时目录生成迁移任务xml文件
				 * 在数据库（proglistfile）生成记录，状态无效（state1=1）
				 * 复制迁移单，从本地到目标
				 * 修改数据库状态，proglistfile和progpackage
				 */
				ProgListFile progListFile = new ProgListFile();
				progListFile.setScheduledate(scheduledate);
				progListFile.setFilename(xmlFilename);
				progListFile.setFiletype((long) 8); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
				progListFile.setInputtime(nowdate);
				progListFile.setInputmanid(operatorId);
				progListFile.setDate1(nowdate);
//				progListFile.setState1((long) 1);
//				progListFile.setState2((long) 0);
//				progListFile.setColumnxml(strxml);
				int ret = saveMigrationXmlFile(
						document, 
						destpathMigration, 
						localfile, 
						progListFile
						);
				
				if(ret == 0)
				{
					cmsLog.debug("生成迁移任务xml文件成功，准备修改节目包的处理状态...");
					for(int i = 0; i < dealedPps.size(); i++)
					{
						ProgPackage dealedPp = dealedPps.get(i);
						Long state = dealedPp.getState();
						Long dealState = dealedPp.getDealstate();
						if (state == 2)
						{
							if (dealState == 0 || dealState == 8) 
							{
								dealedPp.setDealstate((long) 1); // 处理状态(0未处理1处理8失败9成功)
								progPackageManager.update(dealedPp);
								cmsLog.debug("节目包ID：" + dealedPp.getProductid());
								cmsLog.debug("节目包的处理状态修改为：" + dealedPp.getDealstate());
							}
							else
							{
								cmsLog.debug("节目包ID：" + dealedPp.getProductid());
								cmsLog.debug("节目包的处理状态不修改。" + dealedPp.getDealstate());
							}
						}
						else
						{
							cmsLog.debug("节目包ID：" + dealedPp.getProductid());
							cmsLog.debug("节目包的处理状态不修改。" + dealedPp.getDealstate());
						}
					}
				}
				else
				{
					String str = "生成迁移任务单失败。";
					cmsLog.warn(str);
					cmsResultDto.setResultCode((long)1);
					cmsResultDto.setErrorMessage(str);
				}
//				String strxml = null;
//				try
//				{
//					cmsLog.debug("生成播发单xml字符串...");
//
//					// 输出格式化器
//					OutputFormat format = OutputFormat.createPrettyPrint();
//					
//					// 设置编码 - gb2312 / GBK / 
//					format.setEncoding("GBK");
//					
//					// xml输出器
//					StringWriter stringwriter = new StringWriter();
//					XMLWriter stringout = new XMLWriter(stringwriter, format);
//					stringout.write(document);
//					stringout.flush();
//					strxml = stringwriter.toString();
//					
//					XMLWriter fileout = new XMLWriter(new FileWriter(new File(localfile)), format);
//					fileout.write(document);
//					fileout.close();
//				}
//				catch(IOException e)
//				{
//					String str = "生成播发单xml字符串异常：" + e.getMessage() + "\r\n";
////					errorcount++;
////					errordetails += str;
//					cmsLog.error(str);
//				}
//				
//				/**
//				 * 生成迁移任务xml，在 本地临时目录
//				 */
//				ProgListFile progListFile = new ProgListFile();
//				progListFile.setScheduledate(scheduledate);
//				progListFile.setFilename(xmlFilename);
//				progListFile.setFiletype((long) 8); // 文件类型（0PAGEXML,1JS,2PTL,8迁移单,9BROADCASTXML）
//				progListFile.setInputtime(nowdate);
//				progListFile.setInputmanid(operatorId);
//				progListFile.setState1((long) 1);
//				progListFile.setState2((long) 0);
//				progListFile.setDate1(nowdate);
//				progListFile.setColumnxml(strxml);
//				progListFileManager.save(progListFile);
//				cmsLog.debug("ProgListFile记录已经保存，ID：" + progListFile.getColumnfileid());
//				
////				CmsResultDto crd = cmsTransactionManager.saveProgListFile(
////						progListFileManager, 
////						progListFile, 
////						date, 
////						null
////						);
////
////				if (crd.getResultCode() != 0) 
////				{
////					fileopr.deleteSmbFile(destpathMigration);
////				}
//				
//				/**
//				 * 复制迁移任务xml文件，
//				 * 从 本地临时目录 
//				 * 到 迁移接口目录
//				 */
//				
//				fileopr.checkSmbDir(destpathMigration);
//				if(fileopr.checkFileExist(localfile))
//				{
//					cmsLog.debug("准备复制迁移任务单...");
//					int ret = fileopr.copyFileFromLocalToSmb(localfile, destpathMigration);
//					if(ret == 0)
//					{
//						cmsLog.debug("复制迁移任务单成功。");
//						
//						for(int i = 0; i < dealedPps.size(); i++)
//						{
//							ProgPackage dealedPp = dealedPps.get(i);
//							Long state = dealedPp.getState();
//							Long dealState = dealedPp.getDealstate();
//							if (state == 2)
//							{
//								if (dealState == 0 || dealState == 8) 
//								{
//									dealedPp.setDealstate((long) 1); // 处理状态(0未处理1处理8失败9成功)
//									progPackageManager.update(dealedPp);
//									cmsLog.debug("节目包ID：" + dealedPp.getProductid());
//									cmsLog.debug("节目包的处理状态修改为：" + dealedPp.getDealstate());
//								}
//								else
//								{
//									cmsLog.debug("节目包ID：" + dealedPp.getProductid());
//									cmsLog.debug("节目包的处理状态不修改。" + dealedPp.getDealstate());
//								}
//							}
//							else
//							{
//								cmsLog.debug("节目包ID：" + dealedPp.getProductid());
//								cmsLog.debug("节目包的处理状态不修改。" + dealedPp.getDealstate());
//							}
//						}
//						progListFile.setState1((long) 0);
//						progListFileManager.update(progListFile);
//						cmsLog.debug("ProgListFile记录已经更新，state1 = 0。");
//					}
//					else
//					{
//						String str = "复制迁移任务单失败！\r\n";
////						errorcount++;
////						errordetails += str;
//						cmsLog.warn(str);
//					}
//				}
//				else
//				{
//					String str = "本地迁移任务单不存在..." + localfile + "\r\n";
////					errorcount++;
////					errordetails += str;
//					cmsLog.warn(str);
//				}
			}	// if(portalColumns == null || portalColumns.size() <= 0)
		}
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> saveMigrationFromCaonlineToOnline returns.");
		return cmsResultDto;
	}
	
	/**
	 * 20101108 13:30 1.21 不使用，完成迁移，加扰库 --> 一级库（播发库）
	 * @param transferEntity
	 * @param result
	 * @param resultDes
	 * @return
	 */
	public CmsResultDto updateFinishMigrationFromCaonlineToOnlineNOTUSE(

			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			)
	{
		/**
		 * 流程：
		 * 1 - 解析反馈xml
		 * 2 - 迁移成功，获取节目包下所有文件
		 * 3 - 根据节目包样式、应用代码，获取节目包的文件样式
		 * 4 - 对比节目包所有文件和节目包文件样式，获得需要迁移文件列表
		 * 5 - 判断节目包是否迁移完毕
		 * 6 - 如果迁移完毕，修改节目包状态，
		 * 7 - 如果没有迁移完毕，不修改节目包状态
		 * 8 - 增加文件位置表记录
		 * 
		 */
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> updateFinishMigrationFromCaonlineToOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		long fileStyleCode = 2L;		// 1 - 节目预告 ; 2 - 播发单
		Date nowDate = new Date();

		if("Y".equalsIgnoreCase(result))
		{
			TransferDistribution transferDistribution = dealXmlFromMigrationModule(transferEntity);
			if(transferDistribution != null)
			{
				List<TransferTask> transferTasks = transferDistribution.getTransferTasks();
				
				for(int i = 0; i < transferTasks.size(); i++)
				{
					TransferTask transferTask = transferTasks.get(i);
					List<TransferEntity> tes = transferTask.getTransferEntities();
					
					
					cmsLog.debug("迁移结果：成功");
					if(tes != null && tes.size() > 0)
					{
						TransferEntity te = tes.get(0);
						
						cmsLog.debug("TransferEntity节点数量：" + tes.size());
						cmsLog.debug("取第1个节点（从迁移反馈xml获取）...");
						cmsLog.debug("节目包ID：" + te.getSourceProgid());
						cmsLog.debug("节目包名称：" + te.getSourceProgTitle());
						cmsLog.debug("文件ID：" + te.getSourceFileId());
						cmsLog.debug("文件名称：" + te.getSourceFileName());
						
						ProgPackage progPackage = (ProgPackage) progPackageManager.getById(te.getSourceProgid());
						if(progPackage != null)
						{
							/**
							 * 生成本地迁移文件的文件位置表记录
							 */
							/**
							 * HuangBo update by 2011年4月22日 19时5分
							 * 未使用, 注释
							 */
//							TransferSource transferSource = te.getTransferSource();
							TransferDestination transferDestination = te.getTransferDestination();
							
							
							String progFileId = te.getSourceFileId();
							String desStclasscode = te.getDesStorageClass();
							
							/**
							 * 根据文件id、存储等级code，查询文件位置表记录
							 */
							List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
									progFileId, 
									desStclasscode
									);
							if(asprs != null && asprs.size() > 0)
							{
								AmsStoragePrgRel aspr = asprs.get(0);
								cmsLog.debug("文件位置表记录已经存在，不处理。");
								cmsLog.debug("文件位置表ID：" + aspr.getRelid());
							}
							else
							{
								AmsStoragePrgRel aspr = new AmsStoragePrgRel();
								Date dfileDate = null;
								String sfileDate = transferDestination.getFileDate();
								if(sfileDate == null || "".equalsIgnoreCase(sfileDate))
								{
									dfileDate = null;
								}
								else
								{
									dfileDate = fileopr.convertStringToDate(sfileDate, "yyyy-MM-dd HH:mm:ss");
								}
								aspr.setStglobalid(transferDestination.getDesStorageId()); // DesStorageId
								aspr.setStdirglobalid(transferDestination.getDesStorageDirId()); // DesStorageDirId
								aspr.setPrglobalid(te.getSourceProgid()); // SourceProgid
								aspr.setProgfileid(te.getSourceFileId()); // SourceFileId
								aspr.setFtypeglobalid(te.getSourceFileType()); // SourceFileType
								aspr.setFilename(te.getSourceFileName()); // SourceFileName
								aspr.setFilepath(transferDestination.getVariableFilePath()); // VariableFilePath
								aspr.setFiledate(dfileDate); // FileDate
								aspr.setUploadtime(nowDate); //
								aspr.setInputmanid("MigrationModule");
								aspr.setInputtime(nowDate);
								aspr.setRemark("文件迁移至一级库成功。");
								amsstorageprgrelManager.save(aspr);
								cmsLog.debug("文件位置表记录已经保存。");
								cmsLog.debug("文件位置表ID：" + aspr.getRelid());
							}
							/**
							 * 处理节目包状态
							 * 如果节目包需要迁移的所有文件都迁移完成，
							 * 	并且节目包当前状态不是“迁移失败”，
							 * 则修改节目包状态：下一状态、未处理
							 */
							cmsResultDto = updateRefreshStateOfProgPackage(
									progPackage, 
									fileStyleCode,
									result
									);
		//					boolean needUpdate = true;		// 需要修改节目包状态
		//					List<ProgramFiles> programFileses = new ArrayList<ProgramFiles>();	// 需要迁移的节目包文件
		//					
		//					programFileses = programFilesManager.getProgramFilesesByProgPackageFilestylecode(
		//							fileStyleManager, 
		//							packageFilesManager, 
		//							pp, 
		//							fileStyleCode
		//							);
		//					
		//					if(programFileses != null && programFileses.size() > 0)
		//					{
		//						cmsLog.debug("需要迁移的节目包文件数量：" + programFileses.size());
		//						for(int i = 0; i < programFileses.size(); i++)
		//						{
		//							ProgramFiles pf = programFileses.get(i);
		//							
		//							cmsLog.debug("处理第" + (i+1) + "个需要迁移节目包文件，判断文件是否迁移完成（成功）...");
		//							cmsLog.debug("文件ID：" + pf.getProgfileid());
		//							cmsLog.debug("文件名称：" + pf.getFilename());
		//							
		//							/**
		//							 * 根据文件id、存储等级code，查询文件位置表记录
		//							 */
		//							asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
		//									progFileId, 
		//									desStclasscode
		//									);
		//							if(asprs != null && asprs.size() > 0)
		//							{
		//								AmsStoragePrgRel aspr = asprs.get(0);
		//								cmsLog.debug("文件位置表记录已经存在，需要判断实体文件是否存在。");
		//								cmsLog.debug("文件位置表ID：" + aspr.getRelid());
		//								
		//								/**
		//								 * 根据，查询实体文件
		//								 */
		//								// 返回：List
		//								// 1 - String
		//								// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
		//								// 2 - List<Object[]>
		//								// (AmsStorage)Object[0]
		//								// (AmsStoragePrgRel)Object[1]
		//								// (AmsStorageDir)Object[2]
		//								// (AmsStorageClass)Object[3]
		//								List sourcepaths = packageFilesManager
		//										.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
		//												progFileId,
		//												desStclasscode, // 一级库存储体等级code，从配置文件获取
		//												pp.getProductid()
		//												);
		//								if (sourcepaths != null && sourcepaths.size() >= 2)
		//								{
		//									String sourcepath = (String) sourcepaths.get(0);
		//									if(fileopr.checkSmbFileExist(sourcepath))
		//									{
		//										cmsLog.debug("实体文件存在，该文件迁移完成，继续...");
		//									}
		//									else
		//									{
		//										cmsLog.debug("实体文件不存在，文件尚未迁移完毕。");
		//										cmsLog.debug("节目包文件尚未全部迁移完成，不修改节目包状态，停止循环判断。");
		//										needUpdate = false;
		//										break;
		//									}
		//								}
		//							}
		//							else
		//							{
		//								cmsLog.debug("文件位置表记录不存在，文件尚未迁移完毕。");
		//								cmsLog.debug("节目包文件尚未全部迁移完成，不修改节目包状态，停止循环判断。");
		//								needUpdate = false;
		//								break;
		//							}
		//						}
		//					}
		//					else
		//					{
		//						cmsLog.warn("需要迁移的文件列表为空。");
		//						needUpdate = false;
		//					}
		//					if(needUpdate == true)
		//					{
		//						/**
		//						 * 节目包状态，
		//						 * 0 - 导入 
		//						 * 1 - 缓存库 
		//						 * 2 - 加扰库 
		//						 * 3 - 播控库
		//						 */ 
		//						/**
		//						 * 处理状态
		//						 * 0 - 未处理
		//						 * 1 - 处理中
		//						 * 8 - 失败
		//						 * 9 - 成功，不使用
		//						 */
		//						pp.setDealstate((long)0);
		//						progPackageManager.update(pp);
		//					}
		//					else
		//					{
		//						cmsLog.debug("节目包状态不需要修改。");
		//					}
						}
						else
						{
							String str = "节目包不存在，节目包ID：" + te.getSourceProgid();
							cmsResultDto.setResultCode((long) 1);
							cmsResultDto.setErrorMessage(str);
							cmsLog.warn(str);
						}
					}
				}
			}
			else
			{
				String str = "解析迁移反馈xml失败";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		}
		else if("N".equalsIgnoreCase(result))
		{
			/**
			 * 修改节目包的处理状态为：迁移失败
			 */
			TransferDistribution transferDistribution = dealXmlFromMigrationModule(transferEntity);
			
			if(transferDistribution != null)
			{
				List<TransferTask> transferTasks = transferDistribution.getTransferTasks();
				for(int i = 0; i < transferTasks.size(); i++)
				{
					TransferTask transferTask = transferTasks.get(i);
					List<TransferEntity> tes = transferTask.getTransferEntities();
					
					cmsLog.debug("迁移结果：失败");
					if(tes != null && tes.size() > 0)
					{	
						TransferEntity te = tes.get(0);
						
						cmsLog.debug("TransferEntity节点数量：" + tes.size());
						cmsLog.debug("取第1个节点...");
						cmsLog.debug("节目包ID：" + te.getSourceProgid());
						cmsLog.debug("节目包名称：" + te.getSourceProgTitle());
						cmsLog.debug("文件ID：" + te.getSourceFileId());
						cmsLog.debug("文件名称：" + te.getSourceFileName());
						
						ProgPackage progPackage = (ProgPackage) progPackageManager.getById(te.getSourceProgid());
						if(progPackage != null)
						{
							/**
							 * 处理节目包状态
							 * 如果节目包需要迁移的所有文件都迁移完成，
							 * 	并且节目包当前状态不是“迁移失败”，
							 * 则修改节目包状态：下一状态、未处理
							 */
							cmsResultDto = updateRefreshStateOfProgPackage(
									progPackage, 
									fileStyleCode,
									result
									);
		//					progPackage.setDealstate((long) 8);
		//					progPackageManager.update(progPackage);
		//					
		//					cmsLog.debug("节目包的处理状态修改为：失败(8)");
						}
						else
						{
							String str = "节目包不存在，节目包ID：" + te.getSourceProgid();
							cmsResultDto.setResultCode((long) 1);
							cmsResultDto.setErrorMessage(str);
							cmsLog.warn(str);
						}
					}
					else
					{
						String str = "迁移模块反馈xml内容有误，TransferEntity节点数量为0。";
						cmsResultDto.setResultCode((long) 1);
						cmsResultDto.setErrorMessage(str);
						cmsLog.warn(str);
					}
				}
			}
			else
			{
				String str = "解析迁移反馈xml失败";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		}
		else
		{
			String str = "未知输入结果。result：" + result;
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}
		
		cmsLog.debug("Cms -> MigrationModuleManagerImpl -> updateFinishMigrationFromCaonlineToOnline returns.");
		return cmsResultDto;
	}
	
	//=============================================================================================
	
	
	
	
	
	
	//------------------ SET 方法 -------------------------------------------------------------
	public void setProgramInfoManager(IProgramInfoManager programInfoManager) {
		this.programInfoManager = programInfoManager;
	}
	
	public void setProgListFileManager(IProgListFileManager progListFileManager) {
		this.progListFileManager = progListFileManager;
	}

	public void setPackageFilesManager(IPackageFilesManager packageFilesManager) {
		this.packageFilesManager = packageFilesManager;
	}

	public void setProgListDetailManager(
			IProgListDetailManager progListDetailManager) {
		this.progListDetailManager = progListDetailManager;
	}

	public void setProgPackageManager(IProgPackageManager progPackageManager) {
		this.progPackageManager = progPackageManager;
	}

	public void setFileStyleManager(IFileStyleManager fileStyleManager) {
		this.fileStyleManager = fileStyleManager;
	}



	public void setProgramFilesManager(IProgramFilesManager programFilesManager) {
		this.programFilesManager = programFilesManager;
	}

	public void setAmsstorageManager(IAmsStorageManager amsstorageManager) {
		this.amsstorageManager = amsstorageManager;
	}

	public void setAmsstorageclassManager(
			IAmsStorageClassManager amsstorageclassManager) {
		this.amsstorageclassManager = amsstorageclassManager;
	}

	public void setAmsstorageprgrelManager(
			IAmsStoragePrgRelManager amsstorageprgrelManager) {
		this.amsstorageprgrelManager = amsstorageprgrelManager;
	}

	public void setAmsstoragedirManager(IAmsStorageDirManager amsstoragedirManager) {
		this.amsstoragedirManager = amsstoragedirManager;
	}
	//=============================================================================================

	public void setPortalColumnManager(IPortalColumnManager portalColumnManager) {
		this.portalColumnManager = portalColumnManager;
	}

	public static void setFileopr(FileOperationImpl fileopr) {
		MigrationModuleManagerImpl.fileopr = fileopr;
	}

	public static void setCc(CmsConfig cc) {
		MigrationModuleManagerImpl.cc = cc;
	}

	public void setProductinfoManager(IProductInfoManager productinfoManager) {
		this.productinfoManager = productinfoManager;
	}
}
