package com.soaplat.cmsmgr.managerimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.apache.log4j.Logger;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageClass;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.cmsmgr.bean.FileStyle;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgListDeleteDetail;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.dto.BroadcastColumn;
import com.soaplat.cmsmgr.dto.BroadcastDelete;
import com.soaplat.cmsmgr.dto.BroadcastFile;
import com.soaplat.cmsmgr.dto.BroadcastJs;
import com.soaplat.cmsmgr.dto.BroadcastProgPackage;
import com.soaplat.cmsmgr.dto.BroadcastProglist;
import com.soaplat.cmsmgr.dto.BroadcastPushCondition;
import com.soaplat.cmsmgr.dto.BroadcastPushVod;
import com.soaplat.cmsmgr.dto.BroadcastSite;
import com.soaplat.cmsmgr.dto.BroadcastYgJs;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.manageriface.IBpmcManager;
import com.soaplat.cmsmgr.manageriface.IBroadcastModuleManager;
import com.soaplat.cmsmgr.manageriface.ICmsSiteManager;
import com.soaplat.cmsmgr.manageriface.ICmsTransactionManager;
import com.soaplat.cmsmgr.manageriface.IFileStyleManager;
import com.soaplat.cmsmgr.manageriface.IFlowActivityOrderManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;
import com.soaplat.cmsmgr.manageriface.IProgListDeleteDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListFileManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.soaplat.cmsmgr.util.DateUtil;

/**
 * 
 * @author Andy
 * @version cms 1.21
 */


public class BroadcastModuleManagerImpl implements IBroadcastModuleManager {
	
	private ICmsTransactionManager cmsTransactionManager;
	private ICmsSiteManager cmsSiteManager;
	private IPortalColumnManager portalColumnManager;
	private IProgListDetailManager progListDetailManager;
	private IProgPackageManager progPackageManager;
	private IFileStyleManager fileStyleManager;
	private IPackageFilesManager packageFilesManager;
	private IProgListMangManager progListMangManager;
	private IProgListFileManager progListFileManager;
	private IProgListMangDetailManager progListMangDetailManager; 
	private IBpmcManager bpmcManager;
	private IFlowActivityOrderManager flowActivityOrderManager;
	private IProductInfoManager productinfoManager;
	private IProgramFilesManager programFilesManager;
	private IProgramInfoManager programinfoManager;
	private IProgListDeleteDetailManager progListDeleteDetailManager;
	
	private static final Logger cmsLog = Logger.getLogger("Cms");
	private static FileOperationImpl fileopr = new FileOperationImpl();
	public static CmsConfig cc = new CmsConfig();
	
	/**
	 * 节目状态（dsflag）
	 * -1 - 未导入
	 * 0 - 迁移中
	 * 1 - 迁移失败
	 * 2 - 新录入
	 * 3 - 已包装
	 *
	 * 节目包状态（ProgPackage --> state）
	 * -1 - 未导入
	 * 0 - 导入区（Import），1.2不使用
	 * 1 - 缓存库(NearOnline)
	 * 2 - 加扰库(CaOnline)
	 * 3 - 播发库(Online)
	 * 9 - 在上海的北京缓存库(BjOnline)，1.2不使用
	 * 
	 * 文件是否存在（ProgramFiles --> progstatus）
	 * 0 - 实体文件存在
	 * 1 - 实体文件不存在
	 */
	
	
	//---------------------- 重用方法 --------------------------------------------------------
	/**
	 * 20101126 10:10 1.2 获得播发单xml文件生成的目标路径
	 * （返回：目标路径、本地临时路径，都不包含文件名）
	 */
	private List<String> getBroadcastXmlFilepath(
			String filecodeBroadcast, 
			String stclasscode,
			String date,
			String migrationXmlTemp,
			String serverOS
			)
	{
		List<String> filepaths = new ArrayList<String>();
		String localfilepath = null;
		String destfilepath = null;
		
		/**
		 * 查询播发单的目标路径，不包含文件名
		 */
		List dest = packageFilesManager.getDestPathByFilecodeStclasscode(
				filecodeBroadcast, stclasscode);
		if (dest != null && dest.size() >= 2)
		{
			String destpath = (String) dest.get(0);

			if(destpath != null && !"".equalsIgnoreCase(destpath))
			{
				destfilepath = destpath;
				destfilepath = destfilepath.replace('\\', '/');
				destfilepath = fileopr.checkPathFormatRear(
						destfilepath, '/');
				destfilepath += date;
				destfilepath += "/";
				
				cmsLog.info("得到播发单的目标路径 - " + destfilepath);
				fileopr.checkSmbDir(destfilepath);
			}
			else
			{
				cmsLog.warn("播发单目标路径不存在。");
				destfilepath = null;
			}
		}
		else
		{
			cmsLog.warn("播发单目标路径不存在。");
			destfilepath = null;
		}
		
		/**
		 * 查询播发单的本地临时路径，不包含文件名
		 */
		if("LINUX".equalsIgnoreCase(serverOS.toUpperCase()))
		{
			localfilepath = "/";
		}
		else if("WINDOWS".equalsIgnoreCase(serverOS.toUpperCase()))
		{
			localfilepath = "C:/";
		}
		else
		{
			if(fileopr.checkFileExist("/"))
			{
				localfilepath = "/";
			}
			else if(fileopr.checkFileExist("C:/"))
			{
				localfilepath = "C:/";
			}
			else
			{
				localfilepath = null;
			}
		}
		if(localfilepath != null)
		{
			localfilepath = fileopr.checkPathFormatRear(localfilepath, '/');
			migrationXmlTemp = migrationXmlTemp.replace('\\', '/');
			localfilepath = localfilepath + migrationXmlTemp;
			localfilepath = fileopr.checkPathFormatRear(localfilepath, '/');
			localfilepath = localfilepath + date + "/";
			fileopr.checkLocalDir(localfilepath);
		}
		else
		{
			cmsLog.warn("播发单本地临时目录不存在。");
		}
		
		/**
		 * 返回
		 */
		if(localfilepath != null
			&& destfilepath != null
			)
		{
			filepaths.add(destfilepath);
			filepaths.add(localfilepath);
		}
		else
		{
			filepaths = null;
		}

		return filepaths;
	}
	
	/**
	 * 20101126 11:16 1.2 生成xml Document
	 * @param broadcastPushVod
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Document generateXmlDoc(BroadcastPushVod broadcastPushVod)
	{
		Document document = null;
		if(broadcastPushVod != null)
		{
			document = DocumentHelper.createDocument();
			Element rootelement = document.addElement("PushVod");		// 根节点
			rootelement.addComment("播发单，生成日期：" + fileopr.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));		
			rootelement.addAttribute("ID", broadcastPushVod.getId());
			
			List<BroadcastSite> broadcastSites = broadcastPushVod.getBroadcastSites();
			for(int i = 0; i < broadcastSites.size(); i++)
			{
				BroadcastSite broadcastSite = broadcastSites.get(i);
				List<BroadcastProglist> broadcastProglists = broadcastSite.getBroadcastProglists();
				BroadcastJs broadcastJs = broadcastSite.getBroadcastJs();
				BroadcastDelete broadcastDelete = broadcastSite.getBroadcastDelete();
				
				Element siteElement = rootelement.addElement("Site");		// 品牌
				siteElement.addAttribute("BroadcastTime", broadcastSite.getBroadcastTime());
				siteElement.addAttribute("SiteCode", broadcastSite.getSiteCode());
				siteElement.addAttribute("SiteName", broadcastSite.getSiteName());
				siteElement.addAttribute("ScheduleDate", broadcastSite.getScheduleDate());
				
				for(int j = 0; j < broadcastProglists.size(); j++)
				{
					BroadcastProglist broadcastProglist = broadcastProglists.get(j);
					List<BroadcastColumn> broadcastColumns = broadcastProglist.getBroadcastColumns();
					
					Element proglistElement = siteElement.addElement("Proglist");
					proglistElement.addAttribute("ScheduleDate", broadcastProglist.getScheduleDate());
					
					for(int k = 0; k < broadcastColumns.size(); k++)
					{
						BroadcastColumn broadcastColumn = broadcastColumns.get(k);
						List<BroadcastProgPackage> broadcastProgPackages = broadcastColumn.getBroadcastProgPackages();
						
						Element columnElement = proglistElement.addElement("Column");
						columnElement.addAttribute("DefCatCode", broadcastColumn.getDefCatCode());
						columnElement.addAttribute("Name", broadcastColumn.getName());
						columnElement.addAttribute("Type", broadcastColumn.getType());
						columnElement.addAttribute("SiteCode", broadcastColumn.getSiteCode());
						
						for(int m = 0; m < broadcastProgPackages.size(); m++)
						{
							BroadcastProgPackage broadcastProgPackage = broadcastProgPackages.get(m);
							List<BroadcastFile> broadcastFiles = broadcastProgPackage.getBroadcastFiles();
							List<BroadcastPushCondition> broadcastPushConditions = broadcastProgPackage.getBroadcastPushConditions();
							
							Element progPackageElement = columnElement.addElement("ProgPackage");
							progPackageElement.addAttribute("PPID", broadcastProgPackage.getPpid());
							progPackageElement.addAttribute("STBTITLE", broadcastProgPackage.getStbTitle());
							progPackageElement.addAttribute("KEYIDS", broadcastProgPackage.getKeyids());
							
							for(int n = 0; n < broadcastFiles.size(); n++)
							{
								BroadcastFile broadcastFile = broadcastFiles.get(n);
								
								if(broadcastFile.getIsFolder() == true)
								{
									Element fileElement = progPackageElement.addElement("FOLDER");
									fileElement.addAttribute("PROGFILEID", broadcastFile.getProgfileid());
									fileElement.addAttribute("FILENAME", broadcastFile.getFilename());
									fileElement.addAttribute("CONTENTID", broadcastFile.getContentid());
									fileElement.addAttribute("PROGRANK", broadcastFile.getProgrank());
									fileElement.addAttribute("FILECODE", broadcastFile.getFilecode());
									fileElement.addAttribute("FILETYPEID", broadcastFile.getFiletypeid());
									fileElement.addAttribute("SRC", broadcastFile.getSrc());
									/**
									 * HuangBo update by 2011年3月17日 15时56分
									 * UPDATEFLAG 节点的值由(Y, N) 修改为(1, 0)
									 * UPDATETIME 节点保证有值
									 */
									fileElement.addAttribute("UPDATEFLAG", "Y".equals(broadcastFile.getUpdateflag()) 
											? "1" : "0");
//									fileElement.addAttribute("UPDATEFLAG", "0");
									fileElement.addAttribute("UPDATETIME", 
											(null == broadcastFile.getUpdatetime() 
													|| "".equals(broadcastFile.getUpdatetime().trim()))
															? "2012-12-31 00:00:00" : broadcastFile.getUpdatetime());
								}
								else
								{
									Element fileElement = progPackageElement.addElement("FILE");
									fileElement.addAttribute("PROGFILEID", broadcastFile.getProgfileid());
									fileElement.addAttribute("FILENAME", broadcastFile.getFilename());
									fileElement.addAttribute("CONTENTID", broadcastFile.getContentid());
									fileElement.addAttribute("PROGRANK", broadcastFile.getProgrank());
									fileElement.addAttribute("FILECODE", broadcastFile.getFilecode());
									fileElement.addAttribute("FILETYPEID", broadcastFile.getFiletypeid());
									fileElement.addAttribute("SRC", broadcastFile.getSrc());
									/**
									 * HuangBo update by 2011年3月17日 15时56分
									 * UPDATEFLAG 节点的值由(Y, N) 修改为(1, 0)
									 */
									fileElement.addAttribute("UPDATEFLAG", "Y".equals(broadcastFile.getUpdateflag()) 
											? "1" : "0");
//									fileElement.addAttribute("UPDATEFLAG", "0");
									fileElement.addAttribute("UPDATETIME", 
											(null == broadcastFile.getUpdatetime() 
													|| "".equals(broadcastFile.getUpdatetime().trim()))
															? "2012-12-31 00:00:00" : broadcastFile.getUpdatetime());
								}
							}
							
							for(int n = 0; n < broadcastPushConditions.size(); n++)
							{
								BroadcastPushCondition broadcastPushCondition = broadcastPushConditions.get(n);
								
								Element pushConditionElement = progPackageElement.addElement("PushCondition");
//								pushConditionElement.setText(broadcastPushCondition.getValue());
								/**
								 * HuangBo update by 2011年3月17日 15时56分
								 * PushCondition 节点的值由修改为push约定好的固定值(A::3) A固定, 后面的数值是指轮播次数
								 */
								pushConditionElement.setText("A::0");
							}
						}
					}
				}
				
//				for(int j = 0; j < broadcastJses.size(); j++)
//				{
//					BroadcastJs broadcastJs = broadcastJses.get(j);
//					Element jsElement = siteElement.addElement("Js");
//					List<BroadcastYgJs> broadcastYgJses = broadcastJs.getBroadcastYgJses();
//					
//					for(int k = 0; k < broadcastYgJses.size(); k++)
//					{
//						BroadcastYgJs broadcastYgJs = broadcastYgJses.get(k);
//						Element ygjsElement = jsElement.addElement("YgJs");
//						ygjsElement.addAttribute("ScheduleDate", broadcastYgJs.getScheduleDate());
//						ygjsElement.addAttribute("FILENAME", broadcastYgJs.getFileName());
//						ygjsElement.addAttribute("SRC", broadcastYgJs.getSrc());
//					}
//				}
//				
//				for(int j = 0; j < broadcastDeletes.size(); j++)
//				{
//					BroadcastDelete broadcastDelete = broadcastDeletes.get(j);
//					Element deleteElement = siteElement.addElement("Delete");
//				}


				Element jsElement = siteElement.addElement("Js");
				List<BroadcastYgJs> broadcastYgJses = broadcastJs.getBroadcastYgJses();
				
				for(int k = 0; k < broadcastYgJses.size(); k++)
				{
					BroadcastYgJs broadcastYgJs = broadcastYgJses.get(k);
					Element ygjsElement = jsElement.addElement("YgJs");
					ygjsElement.addAttribute("ScheduleDate", broadcastYgJs.getScheduleDate());
					ygjsElement.addAttribute("FILENAME", broadcastYgJs.getFileName());
					ygjsElement.addAttribute("SRC", broadcastYgJs.getSrc());
				}

			

				Element deleteElement = siteElement.addElement("Delete");
				List<BroadcastProgPackage> broadcastProgPackages = broadcastDelete.getBroadcastProgPackages();
				for(int k = 0; k < broadcastProgPackages.size(); k++)
				{
					BroadcastProgPackage broadcastProgPackage = broadcastProgPackages.get(k);

					List<BroadcastFile> broadcastFiles = broadcastProgPackage.getBroadcastFiles();
					List<BroadcastPushCondition> broadcastPushConditions = broadcastProgPackage.getBroadcastPushConditions();
					
					Element progPackageElement = deleteElement.addElement("ProgPackage");
					progPackageElement.addAttribute("PPID", broadcastProgPackage.getPpid());
					progPackageElement.addAttribute("STBTITLE", broadcastProgPackage.getStbTitle());
					progPackageElement.addAttribute("KEYIDS", broadcastProgPackage.getKeyids());
					
					for(int n = 0; n < broadcastFiles.size(); n++)
					{
						BroadcastFile broadcastFile = broadcastFiles.get(n);
						
						Element fileElement = null;
						if (broadcastFile.getIsFolder()) {
							fileElement = progPackageElement.addElement("FOLDER");
						} else {
							fileElement = progPackageElement.addElement("FILE");
						}
						fileElement.addAttribute("PROGFILEID", broadcastFile.getProgfileid());
						fileElement.addAttribute("FILENAME", broadcastFile.getFilename());
						fileElement.addAttribute("CONTENTID", broadcastFile.getContentid());
						fileElement.addAttribute("PROGRANK", broadcastFile.getProgrank());
						fileElement.addAttribute("FILECODE", broadcastFile.getFilecode());
						fileElement.addAttribute("FILETYPEID", broadcastFile.getFiletypeid());
						fileElement.addAttribute("SRC", broadcastFile.getSrc());
						fileElement.addAttribute("UPDATEFLAG", "Y".equals(broadcastFile.getUpdateflag()) 
								? "1" : "0");
						fileElement.addAttribute("UPDATETIME", broadcastFile.getUpdatetime());
					}
				}
			}
			
			List<PortalColumn> portalColumns = this.portalColumnManager.findByProperty("defcatlevel", 0L);
			PortalColumn portalColumn = portalColumns.get(0);
			
			Element foldElement = rootelement.addElement("Fold");
			Element egFoldElement = foldElement.addElement("EgFold");
			egFoldElement.addAttribute("FOLDNAME", "D_COLUMN");
			egFoldElement.addAttribute("SRC", "/D_COLUMN/");
			egFoldElement.addAttribute("UPDATEFLAG", "1");
			egFoldElement.addAttribute("UPDATETIME", DateUtil.getDateStr("yyyy-MM-dd HH:mm:ss:SSS", portalColumn.getUpdatedate()));
			egFoldElement.addAttribute("FOLDID", "1");
			/**
			 * 加入用户分组信息
			 * HuangBo addition by 2012年8月1日 11时04分
			 */
			List<CmsSite> sites = this.cmsSiteManager.findAll();
			for (CmsSite cmsSite : sites) {
				Element siteEgFoldElement = foldElement.addElement("EgFold");
				siteEgFoldElement.addAttribute("FOLDNAME", "D_GROUP");
				siteEgFoldElement.addAttribute("ID", cmsSite.getSiteCode());
				siteEgFoldElement.addAttribute("SRC", "/" + cmsSite.getSiteCode() + "/");
				siteEgFoldElement.addAttribute("UPDATEFLAG", "1");
				siteEgFoldElement.addAttribute("UPDATETIME", DateUtil.getDateStr("yyyy-MM-dd HH:mm:ss:SSS", cmsSite.getUpdateTime()));
				siteEgFoldElement.addAttribute("FOLDID", "1");
			}
		}
		
		return document;
	}
	
	/**
	 * 20101126 16:29 1.2 保存播发单xml文件，proglistfile记录
	 * @param broadcastPushVod
	 * @param document
	 * @param broadcastXmlFullPath
	 * @param localfile
	 * @param progListFile
	 * @param date
	 * @param operatorId
	 * @return
	 */
	private int saveBroadcastXmlFile(
			BroadcastPushVod broadcastPushVod,
			Document document,
			String broadcastXmlFullPath,
			String localfile,
			ProgListFile progListFile,
			String date,
			String operatorId
			)
	{
		int ret = 1;	// 返回：0 - 成功； 1 - 失败
		
		/**
		 * 1 - 把xml字符串增加到ProgListfile记录，获取新增记录的id，设置新增记录为无效状态(state1 = 1)
		 * 2 - 把新增id设置为xml的pushvod id
		 * 3 - 生成播发单的xml文件在本地
		 * 4 - 复制本地播发单xml文件到网络路径（一级库）
		 * 5 - 设置新增ProgListfile记录为有效状态(state1 = 0)
		 */
		String strxml = document.asXML();
		int errorcount = 0;
		String errordetails = "";
		
		cmsLog.info("生成播发单字符串成功，准备生成播发单文件...");
		cmsLog.info("播发单文件目标路径 - " + broadcastXmlFullPath);

		/**
		 * 增加300G预装内容
		 * 暂时不使用
		 * strxml = XMLPatch.patch(strxml);
		 */ 

		cmsLog.info("生成播发单xml字符串成功，准备新增发布文件记录...");

//		ProgListFile progListFile = new ProgListFile();
//
//		progListFile.setScheduledate(scheduledate);
//		progListFile.setFilename(broadcastXmlName);
//		progListFile.setFiletype((long) 9);
//		progListFile.setDate1(nowDate);
//		progListFile.setDate4(fileopr.convertStringToDate(plandate, "yyyy-MM-dd HH:mm:ss"));
//		progListFile.setState1((long) 1);		// 设置为无效
//		progListFile.setState2((long) 0);
//		progListFile.setInputmanid(operatorId);
		progListFile.setColumnxml(strxml);
//		progListFile.setInputtime(nowDate);

		// 播发单下发完成，保存发布文件表记录progListfile，并且发送活动 86-->87
		// 播放单生成成功，发送
		CmsResultDto crd = cmsTransactionManager.saveProgListFileAndUpdateProgListMangDetail(
						progListFileManager,
						progListMangManager,
						progListMangDetailManager, 
						bpmcManager,
						flowActivityOrderManager,
						portalColumnManager,
						progListDetailManager, 
						progListFile,
						date, 
						"", 
						operatorId
						);
		if(crd.getResultCode() == (long)0)
		{
			Long idLong = Long.parseLong(progListFile.getColumnfileid().replaceAll("PL", ""));
			String strnewid = idLong.toString();
			cmsLog.info("更新数据库成功，获得ProgListFile ID：" + strnewid);
			
			broadcastPushVod.setId(strnewid);
			document = generateXmlDoc(broadcastPushVod);
		}
		else
		{
			String str = "更新数据库失败，" + crd.getErrorDetail() + "\r\n";
			errorcount++;
			errordetails += str;
			cmsLog.warn(str);
			ret = 1;
		}
		
		strxml = null;
		XMLWriter fileout = null;
		try {
			cmsLog.info("在本地磁盘生成播发单...");
			cmsLog.info("文件路径 - " + localfile);
			
			// 输出格式化器
			OutputFormat format = OutputFormat.createPrettyPrint();
			
			// 设置编码 - gb2312 / GBK / UTF-8
			/**
			 * HuangBo update by 2011年3月17日 15时50分
			 * 编码格式由UTF-8 改为GBK
			 */
			format.setEncoding("GBK");
			
//			// xml输出器
//			StringWriter stringwriter = new StringWriter();
//			XMLWriter stringout = new XMLWriter(stringwriter, format);
//			stringout.write(document);
//			stringout.flush();
//			strxml = stringwriter.toString();
			
//			XMLWriter fileout = new XMLWriter(new FileWriter(new File(localfile)), format);
//			fileout.write(document);
//			fileout.close();

            FileOutputStream fos = new FileOutputStream(localfile);       
            //writer = new XMLWriter(new FileWriter(xmlFile), format);       
            fileout = new XMLWriter(fos, format);       
            fileout.write(document);
            fileout.flush();
		} catch(IOException e) {
			String str = "在本地磁盘生成播发单异常：" + e.getMessage() + "\r\n";
			errorcount++;
			errordetails += str;
			cmsLog.error(str);
			ret = 1;
		} finally {
			 try {
				 if (null != fileout) {
					 fileout.close();
				 }
			} catch (IOException e) {
				cmsLog.error(e.getMessage());
				ret = 1;
			}  
		}
		
		try {
			fileopr.checkSmbDir(broadcastXmlFullPath);
			if(fileopr.checkFileExist(localfile))
			{
				cmsLog.info("准备复制播发单...");
				int ret1 = fileopr.copyFileFromLocalToSmb(localfile, broadcastXmlFullPath);
				if(ret1 == 0)
				{
					cmsLog.info("复制播发单成功。");
					ret = 0;
				}
				else
				{
					String str = "复制播发单失败！\r\n";
					errorcount++;
					errordetails += str;
					cmsLog.warn(str);
					ret = 1;
				}
			}
			else
			{
				String str = "本地播发单不存在..." + localfile + "\r\n";
				errorcount++;
				errordetails += str;
				cmsLog.warn(str);
				ret = 1;
			}
		} catch(Exception e) {
			String str = "复制本地磁盘播发单至一级库异常：" + e.getMessage() + "\r\n";
			errorcount++;
			errordetails += str;
			cmsLog.error(str);
			ret = 1;
		}
		
		if(errorcount <= 0 && ret == 0)
		{
			progListFile.setColumnxml(strxml);
			progListFile.setState1(0l);				// 设置为有效状态
			progListFileManager.update(progListFile);
			cmsLog.info("已经更新xml。PushVod ID:" + progListFile.getColumnfileid());
		}
		else
		{
			String str = "错误计数大于0，生成播发单失败。";
			cmsLog.warn(str);
			ret = 1;
		}
		
		return ret;
	}
	
	/**
	 * 20101129 9:39 1.2 检查节目包的文件是否到位（一级库），检查节目包状态是否到位。
	 * 根据文件样式，ts、key、png、字幕，
	 * 文件位置表记录是否存在
	 * 实体文件是否存在
	 * @param progPackageManager
	 * @param packageFilesManager
	 * @param programFilesManager
	 * @param programInfoManager
	 * @param portalColumnManager
	 * @param progListDetailManager
	 * @param fileStyleManager
	 * @param stclassOnline
	 * @param fileStyleCode
	 * @param scheduledate
	 * @param siteCode
	 * @return
	 */
	public boolean checkProgPackageFilesReady(
			String stclassOnline,
			long fileStyleCode,		// 1 - 节目预告 ; 2 - 播发单
			String scheduledate,
			String siteCode
			)
	{
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> checkProgPackageFilesReady...");
		
		boolean ready = true;

		/**
		 * 节目状态（dsflag）
		 * -1 - 未导入
		 * 0 - 迁移中
		 * 1 - 迁移失败
		 * 2 - 新录入
		 * 3 - 已包装
		 *
		 * 节目包状态（ProgPackage --> state）
		 * -1 - 未导入
		 * 0 - 导入区（Import），1.2不使用
		 * 1 - 缓存库(NearOnline)
		 * 2 - 加扰库(CaOnline)
		 * 3 - 播发库(Online)
		 * 9 - 在上海的北京缓存库(BjOnline)，1.2不使用
		 * 
		 * 文件是否存在（ProgramFiles --> progstatus）
		 * 0 - 实体文件存在
		 * 1 - 实体文件不存在
		 */
		
		/**
		 * 节目包状态：一级库
		 * 节目包的主文件PROGSTATUS：
		 */
		List pcs = portalColumnManager.getPortalColumnsBySitecodeValidtagIsleaf(
				siteCode, 
				(long)0, 		// validflag
				"Y"				// isleaf
				);
		if(pcs != null && pcs.size() > 0)
		{
			cmsLog.info("栏目数量：" + pcs.size());
			for(int i = 0; i < pcs.size(); i++)
			{
				PortalColumn pc = (PortalColumn)pcs.get(i);
				
				List plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
						scheduledate, 
						pc.getColumnclassid()
						);
				
				for(int k = 0; k < plds.size(); k++)
				{
					ProgListDetail pld = (ProgListDetail)plds.get(k);
					// 获得节目包
					ProgPackage pp = (ProgPackage)progPackageManager.getById(pld.getProductid());
					
					List<ProgramFiles> pfs = new ArrayList<ProgramFiles>();	// 需要加入播发单的节目包文件
					String contentid = "";		// 目前为主文件的progfileid
					
					cmsLog.info("处理第" + (k+1) + "个栏目单详细...");
					cmsLog.info("节目包id：" + pld.getProductid());
					cmsLog.info("节目包名称：" + pld.getProductname());
					cmsLog.info("产品信息id：" + pld.getProductname());
					cmsLog.info("文件样式应用代码：" + fileStyleCode);
					cmsLog.info("节目包的样式ID：" + pp.getStyleid());
					
					/**
					 * 判断节目包状态，是否是3 - 播发库(Online)
					 * 判断节目状态dsflag，是否是3 - 已包装
					 * 判断文件表记录，文件是否到位
					 */
					if(pp.getState() != (long)3)
					{
						// 节目包状态未到位
						cmsLog.info("节目包状态未到位，中止");
						ready = false;
						break;
					}
					
					cmsLog.info("查询文件样式...");
					List<FileStyle> fileStyles = fileStyleManager.queryFileStylesByStyleCodeAndStyleID(fileStyleCode, pp.getStyleid());

					
					if(fileStyles == null || fileStyles.size() <= 0)
					{
						cmsLog.info("节目包的文件样式为空，不处理，跳过...");
						
					}
					else
					{
						cmsLog.info("节目包的文件样式size：" + fileStyles.size());
						for(int m = 0; m < fileStyles.size(); m++)
						{
							FileStyle fileStyle = fileStyles.get(m);
							cmsLog.info("filetypeid：" + fileStyle.getFileTypeId());
							List<ProgramFiles> programFiles = packageFilesManager.getProgramFilesesByProductidFiletype(
									pp.getProductid(), fileStyle.getFileTypeId());
							if(programFiles != null && programFiles.size() > 0)
							{
								ProgramFiles pf = programFiles.get(0);
								
								if(pf.getProgstatus() != null && pf.getProgstatus() == (long)0)
								{
									// 文件到位
									
								}
								else
								{
									// 文件未到位
									cmsLog.info("文件未到位，中止。");
									ready = false;
									break;
								}
								
								pfs.add(pf);
								// 主文件
								if(pf.getProgrank() == (long)1)
								{
									contentid = pf.getContentId();
								}
							}
						}	// for(int m = 0; m < fileStyles.size(); m++)
						
						if(pfs.size() > 0)
						{
							/**
							 * 节目包文件，根据文件样式
							 */
							for(int m = 0; m < pfs.size(); m++)
							{
								ProgramFiles pf = pfs.get(m);
								
								/** 返回：List
								* 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
								* 2 - List<Object[]>
								* 		(AmsStorage)Object[0]
								* 		(AmsStoragePrgRel)Object[1]
								* 		(AmsStorageDir)Object[2]
								* 		(AmsStorageClass)Object[3]
								*/
								List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
										pf.getProgfileid(), 
										stclassOnline, 
										""
										);
								if(list != null && list.size() >= 2)
								{
									String srcPath = (String)list.get(0);
	
									if(fileopr.checkSmbFileExist(srcPath))
									{
										// 实体文件存在，继续
									}
									else
									{
										// 实体文件不存在，中止
										cmsLog.info("实体文件不存在，中止。");
										ready = false;
										break;
									}
								}
								else
								{
									// 文件没有到位
									cmsLog.info("文件没有到位，中止。");
									ready = false;
									break;
								}
							}
							
							/**
							 * key文件，根据栏目单详细记录、文件位置表找
							 */
//							String keyfileid = pld.getProductInfoID();
							String productinfoid = pld.getProductInfoID();
							TProductInfo productInfo = productinfoManager.getTProductInfoById(productinfoid);
							String keyfileid = productInfo.getKeyFileId();
								
							if(keyfileid != null && !"".equalsIgnoreCase(keyfileid))
							{
								List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
										keyfileid, 
										stclassOnline, 
										""
										);
								if(list != null && list.size() >= 2)
								{
									String srcPath = (String)list.get(0);

									if(fileopr.checkSmbFileExist(srcPath))
									{
										// 实体文件存在，继续
									}
									else
									{
										// 实体文件不存在，中止
										cmsLog.info("key文件实体文件不存在，中止。");
										ready = false;
										break;
									}
								}
								else
								{
									// key文件没有到位
									cmsLog.info("key文件没有到位，中止。");
									ready = false;
									break;
								}
							}
							else
							{
								// key文件未指定
								cmsLog.info("key文件实体文件不存在。");
//								ready = false;
//								break;
							}
							
							/**
							 * js文件，根据栏目单详细记录、文件位置表找
							 */
							String jsfileid = pld.getJsFileID();
							if(jsfileid != null && !"".equalsIgnoreCase(jsfileid))
							{
								List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
										jsfileid, 
										stclassOnline, 
										""
										);
								if(list != null && list.size() >= 2)
								{
									String srcPath = (String)list.get(0);

									if(fileopr.checkSmbFileExist(srcPath))
									{
										// 实体文件存在，继续
									}
									else
									{
										// 实体文件不存在，中止
										cmsLog.info("js文件实体文件不存在，中止。");
										ready = false;
										break;
									}
								}
								else
								{
									// js文件没有到位
									cmsLog.info("js文件没有到位，中止。");
									ready = false;
									break;
								}
							}
							else
							{
								// js文件未指定
								cmsLog.info("js文件未指定，中止。");
								ready = false;
								break;
							}
						}
						else
						{
							/**
							 * 符合文件样式的节目包文件没有查询到，节目包不加入播发单
							 */
							cmsLog.info("符合文件样式的节目包文件没有查询到");
						}
						
					}	// if(fileStyles == null || fileStyles.size() <= 0) else
				}	// for(int k = 0; k < plds.size(); k++)
				
				if(ready != true)
				{
					cmsLog.info("中止。");
					break;
				}
			}	// for(int i = 0; i < pcs.size(); i++)
		}
		else
		{
			/**
			 * 栏目数量为0，应该不允许生成播发单，或者生成空的播发单
			 */ 
		}
		

		cmsLog.info("检查文件结果：" + ready);
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> checkProgPackageFilesReady returns.");
		return ready;
	}
	
	/**
	 * 20101129 9:39 1.2 检查节目包的文件是否到位（一级库），检查节目包状态是否到位。
	 * 根据文件样式，ts、key、png、字幕，
	 * 文件位置表记录是否存在
	 * 实体文件是否存在
	 * @param progPackageManager
	 * @param packageFilesManager
	 * @param programFilesManager
	 * @param programInfoManager
	 * @param portalColumnManager
	 * @param progListDetailManager
	 * @param fileStyleManager
	 * @param stclassOnline
	 * @param fileStyleCode
	 * @param scheduledate
	 * @param siteCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkProgPackageFilesReady_123(
			String stclassOnline,
			long fileStyleCode,		// 1 - 节目预告 ; 2 - 播发单
			String scheduledate,
			String siteCode,
			List<String> unEncryptCode
			)
	{
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> checkProgPackageFilesReady_123...");
		
		boolean ready = true;

		/**
		 * 节目状态（dsflag）
		 * -1 - 未导入
		 * 0 - 迁移中
		 * 1 - 迁移失败
		 * 2 - 新录入
		 * 3 - 已包装
		 *
		 * 节目包状态（ProgPackage --> state）
		 * -1 - 未导入
		 * 0 - 导入区（Import），1.2不使用
		 * 1 - 缓存库(NearOnline)
		 * 2 - 加扰库(CaOnline)
		 * 3 - 播发库(Online)
		 * 9 - 在上海的北京缓存库(BjOnline)，1.2不使用
		 * 
		 * 文件是否存在（ProgramFiles --> progstatus）
		 * 0 - 实体文件存在
		 * 1 - 实体文件不存在
		 */
		
		/**
		 * 节目包状态：一级库
		 * 节目包的主文件PROGSTATUS：
		 */
		List pcs = portalColumnManager.getPortalColumnsBySitecodeValidtagIsleaf(
				siteCode, 
				(long)0, 		// validflag
				"Y"				// isleaf
				);
		if(pcs != null && pcs.size() > 0)
		{
			cmsLog.info("栏目数量：" + pcs.size());
			for(int i = 0; i < pcs.size(); i++)
			{
				PortalColumn pc = (PortalColumn)pcs.get(i);
				
				List plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
						scheduledate, 
						pc.getColumnclassid()
						);
				
				for(int k = 0; k < plds.size(); k++)
				{
					ProgListDetail pld = (ProgListDetail)plds.get(k);
					// 获得节目包
					ProgPackage pp = (ProgPackage)progPackageManager.getById(pld.getProductid());
					
//					List<ProgramFiles> pfs = new ArrayList<ProgramFiles>();	// 需要加入播发单的节目包文件
					ProgramFiles mainpf = null;			// 节目包主文件
					List<ProgramFiles> accPfs = new ArrayList<ProgramFiles>();	// 需要播发的节目包附属文件
					String contentid = "";		// 目前为主文件的progfileid
					String keyfileid = "";
					String productinfoid = pld.getProductInfoID();
					TProductInfo productInfo = productinfoManager.getTProductInfoById(productinfoid);
					
					cmsLog.info("处理第" + (k+1) + "个栏目单详细...");
					cmsLog.info("节目包id：" + pld.getProductid());
					cmsLog.info("节目包名称：" + pld.getProductname());
					cmsLog.info("产品信息id：" + pld.getProductname());
					cmsLog.info("文件样式应用代码：" + fileStyleCode);
					cmsLog.info("节目包的样式ID：" + pp.getStyleid());
					
					/**
					 * 判断节目包状态，是否是3 - 播发库(Online)
					 * 判断节目状态dsflag，是否是3 - 已包装
					 * 判断文件表记录，文件是否到位
					 */
					if(pp == null 
						|| pp.getState() != (long)3)
					{
						// 节目包状态未到位
						cmsLog.warn("节目包状态未到位，中止");
						ready = false;
						break;
					}
					if(productInfo == null
						|| productInfo.getEncryptState() != (long)19
						)
					{
						cmsLog.warn("节目包的产品信息状态未到位，中止");
						ready = false;
						break;
					}
					
					keyfileid = productInfo.getKeyFileId();
					
					cmsLog.info("查询主文件...");
					List mainpfs = packageFilesManager.getProgramFilesesByProductidProgrank(
							pp.getProductid(), 
							(long)1				// 主文件标识，0 - 不是;  1 - 是
							);
					if(mainpfs != null && mainpfs.size() > 0)
					{
						mainpf = (ProgramFiles)mainpfs.get(0);
						contentid = mainpf.getContentId();
					}
					
					cmsLog.info("根据文件样式，查询需要播发的文件列表...");
					accPfs = programFilesManager.getProgramFilesesByProgPackageFilestylecode(
							fileStyleManager, 
							packageFilesManager, 
							pp, 
							fileStyleCode
							);
					int accPfsCount = accPfs.size();
					cmsLog.info("需要播发的节目包附属文件数量：" + accPfsCount);
					
					/**
					 * 处理附属文件，根据节目包样式
					 */
					if(accPfsCount > 0)
					{
						/**
						 * 节目包附属文件，根据文件样式
						 */
						for(int m = 0; m < accPfs.size(); m++)
						{
							ProgramFiles accPf = accPfs.get(m);
							
							/** 返回：List
							* 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
							* 2 - List<Object[]>
							* 		(AmsStorage)Object[0]
							* 		(AmsStoragePrgRel)Object[1]
							* 		(AmsStorageDir)Object[2]
							* 		(AmsStorageClass)Object[3]
							*/
							List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									accPf.getProgfileid(), 
									stclassOnline, 
									""
									);
							if(list != null && list.size() >= 2)
							{
								String srcPath = (String)list.get(0);

								if(fileopr.checkSmbFileExist(srcPath))
								{
									// 实体文件存在，继续
									cmsLog.info("附属实体文件存在，继续...  fileCode: " + accPf.getFilecode());
								}
								else
								{
									// 实体文件不存在，中止
									cmsLog.info("附属实体文件不存在，中止。fileCode: " + accPf.getFilecode());
									ready = false;
									break;
								}
							}
							else
							{
								// 文件没有到位
								cmsLog.info("文件没有到位，中止。");
								ready = false;
								break;
							}
						}
						
						/**
						 * 主文件，根据栏目单详细记录、文件位置表找
						 */
						if(productinfoid != null && !"".equalsIgnoreCase(productinfoid))
						{	
							/**
							 * 查询主文件的源路径
							 */
							List list = packageFilesManager.getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
									productinfoid, 
									stclassOnline, 
									"",
									mainpf.getFiletypeid()
									);
							if(list != null && list.size() >= 2)
							{
								String srcPath = (String)list.get(0);

								if(fileopr.checkSmbFileExist(srcPath))
								{
									// 实体文件存在，继续
									cmsLog.info("主文件实体文件存在，继续...");
								}
								else
								{
									// 实体文件不存在，中止
									cmsLog.info("主文件实体文件不存在，中止。");
									ready = false;
									break;
								}
							}
						}
						
						/**
						 * HuangBo update by 2011年3月17日 16时16分
						 * 增加KEY文件判断条件, 只判断视频节目的KEY文件是否存在, 不判断富媒体节目的KEY文件是否存在 
						 */
						/**
						 * key文件，根据栏目单详细记录、文件位置表找
						 */
						if ("V".equals(pp.getProgtype())) {
							if(keyfileid != null && !"".equalsIgnoreCase(keyfileid)) {
								List list = packageFilesManager.getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
										keyfileid, 
										stclassOnline, 
										pld.getProductid(),
										"KEY"
								);
//							List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
//									keyfileid, 
//									stclassOnline, 
//									""
//									);
								/**
								 * HuangBo update by 2011年7月1日 16时44分
								 * 增加清流产品号判断
								 */
//								List<String> encryptCode = Arrays.asList(
//						        		new CmsConfig().getPropertyByName("UnEncryptCode").split(","));
								if (!unEncryptCode.containsAll(
				        				Arrays.asList(productInfo.getKeyId().split(",")))) {
									if(list != null && list.size() >= 2)
									{
										String srcPath = (String)list.get(0);
										
										if(fileopr.checkSmbFileExist(srcPath))
										{
											// 实体文件存在，继续
											cmsLog.info("key文件实体文件存在，继续...");
										}
										else
										{
											// 实体文件不存在，中止
											cmsLog.info("key文件实体文件不存在，中止。");
											ready = false;
											break;
										}
									}
									else
									{
										// key文件没有到位
										cmsLog.info("key文件没有到位，中止。");
										ready = false;
										break;
									}
								} else {
									cmsLog.info("清流产品, 不检查key文件...");
								}
							}
							else
							{
								// key文件未指定
								cmsLog.info("key文件实体文件不存在。");
//							ready = false;
//							break;
							}
						}
						
						/**
						 * js文件，根据栏目单详细记录、文件位置表找
						 */
						String jsfileid = pld.getJsFileID();
						if(jsfileid != null && !"".equalsIgnoreCase(jsfileid))
						{
							List list = packageFilesManager.getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
									jsfileid, 
									stclassOnline, 
									"",
									"PPJS"
									);
//							List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
//									jsfileid, 
//									stclassOnline, 
//									""
//									);
							if(list != null && list.size() >= 2)
							{
								String srcPath = (String)list.get(0);

								if(fileopr.checkSmbFileExist(srcPath))
								{
									// 实体文件存在，继续
									cmsLog.info("js文件实体文件存在，继续...");
								}
								else
								{
									// 实体文件不存在，中止
									cmsLog.info("js文件实体文件不存在，中止。");
									ready = false;
									break;
								}
							}
							else
							{
								// js文件没有到位
								cmsLog.info("js文件没有到位，中止。");
								ready = false;
								break;
							}
						}
						else
						{
							// js文件未指定
							cmsLog.info("js文件未指定，中止。");
							ready = false;
							break;
						}
					}
				}	// for(int k = 0; k < plds.size(); k++)
				
				if(ready != true)
				{
					cmsLog.info("中止。");
					break;
				}
			}	// for(int i = 0; i < pcs.size(); i++)
		}
		else
		{
			/**
			 * 栏目数量为0，应该不允许生成播发单，或者生成空的播发单
			 */ 
		}
		

		cmsLog.info("检查文件结果：" + ready);
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> checkProgPackageFilesReady_123 returns.");
		return ready;
	}
	
	public BroadcastProgPackage getBroadcastProgPackage(
			ProgListDetail progListDetail,
			ProgPackage progPackage,
			TProductInfo productInfo,
			Long fileStyleCode,
			String stclassOnline
			)
	{
		BroadcastProgPackage broadcastProgPackage = new BroadcastProgPackage();
		List<BroadcastFile> broadcastFiles = new ArrayList<BroadcastFile>();
		List<BroadcastPushCondition> broadcastPushConditions = new ArrayList<BroadcastPushCondition>();
		List<ProgramFiles> accPfs = new ArrayList<ProgramFiles>();	// 需要播发的节目包附属文件
		ProgramFiles mainpf = null;
		String contentid = "";
		String productinfoid = "";
		String keyfileid = "";
		String keyids = "";
		String jsfileid = "";
		
		/**
		 * 判断输入参数合法性
		 */
		if(progListDetail != null)
		{
			jsfileid = progListDetail.getJsFileID();
		}
		else
		{
			broadcastProgPackage = null;
		}
		if(progPackage != null)
		{
			
		}
		else
		{
			broadcastProgPackage = null;
		}
		if(productInfo != null)
		{
			productinfoid = productInfo.getId();
			keyfileid = productInfo.getKeyFileId();
			keyids = productInfo.getKeyId();
		}
		else
		{
			broadcastProgPackage = null;
		}
		
		cmsLog.info("查询主文件...");
		List mainpfs = packageFilesManager.getProgramFilesesByProductidProgrank(
				progPackage.getProductid(), 
				(long)1				// 主文件标识，0 - 不是;  1 - 是
				);
		if(mainpfs != null && mainpfs.size() > 0)
		{
			mainpf = (ProgramFiles)mainpfs.get(0);
			contentid = mainpf.getContentId();
			cmsLog.info("主文件ID：" + mainpf.getProgfileid());
			cmsLog.info("contentid：" + contentid);
			cmsLog.info("主文件filecode：" + mainpf.getFilecode());
		}
		
		cmsLog.info("根据文件样式，查询需要播发的附属文件列表...");
		accPfs = programFilesManager.getProgramFilesesByProgPackageFilestylecode(
				fileStyleManager, 
				packageFilesManager, 
				progPackage, 
				fileStyleCode
				);
		int accPfsCount = accPfs.size();
		cmsLog.info("需要播发的节目包附属文件数量：" + accPfsCount);
		
		/**
		 * 处理附属文件，根据节目包样式
		 */
		if(accPfsCount > 0)
		{
			for(int m = 0; m < accPfs.size(); m++)
			{
				ProgramFiles pf = accPfs.get(m);
				
				/**
				 * 查询附属文件的源路径
				 */
				List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
						pf.getProgfileid(), 
						stclassOnline, 
						""
						);
				if(list != null && list.size() >= 2)
				{
					List<Object[]> list1 = (List<Object[]>)list.get(1);
					Object[] rows = (Object[]) list1.get(0);
					AmsStorage ams = (AmsStorage) rows[0];
					AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
					AmsStorageDir amsd = (AmsStorageDir) rows[2];
					AmsStorageClass amsc = (AmsStorageClass) rows[3];
					
					String src = amsd.getStoragedirname();
					src = fileopr.checkPathFormatRear(src, '/');
					src += amspr.getFilepath();
					src = src.replace('\\', '/');
					src = fileopr.checkPathFormatFirst(src, '/');
					src = fileopr.checkPathFormatRear(src, '/');
					src += amspr.getFilename();
					src = src.replaceAll("//", "/");
					
					String updateflag = "N";
					String updatetime = "";
					try {
						if (pf.getUpdateTime() == null) {
							updateflag = "N";
						} else {
							if (pf.getInputtime().compareTo(pf.getUpdateTime()) == 0) {
								updateflag = "N";
							} else {
								updateflag = "Y";
							}
						}
						updatetime = fileopr.convertDateToString(progPackage
								.getUpdatetime(), "yyyy-MM-dd HH:mm:ss");
					} catch (Exception ex) {
						updateflag = "N";
						updatetime = "";
						cmsLog.error("获得节目包附件updatetime异常：" + ex.getMessage());
					}
					
					BroadcastFile broadcastFile = new BroadcastFile();
					broadcastFile.setProgfileid(pf.getProgfileid());
					broadcastFile.setFilename(pf.getFilename());
					broadcastFile.setContentid(contentid);	// CONTENTID（全部为主文件值）
					broadcastFile.setProgrank(pf.getProgrank().toString());	// 主体文件标识,1-主文件;0-非主文件
					broadcastFile.setFilecode(pf.getFilecode());
					broadcastFile.setFiletypeid(pf.getFiletypeid());
					broadcastFile.setSrc(src);						// 源文件路径
					broadcastFile.setUpdateflag(updateflag);		// 更新标识
					broadcastFile.setUpdatetime(updatetime);		// 更新版本
					
					broadcastFiles.add(broadcastFile);
				}
				else
				{
					cmsLog.warn("文件在一级库的位置没有查询到。");
					cmsLog.warn("文件ID：" + pf.getProgfileid());
//					errorcount++;
//					errordetails += "文件("
//									+ pf.getFilename() 
//									+ ")在一级库的位置没有查询到，节目包(" 
//									+ pp.getProductname() 
//									+ ")加入播发单，文件未加入播发单。\r\n";
				}
			}
		}

		
		/**
		 * 主文件，根据栏目单详细记录、文件位置表找
		 */
		/**
		 * HuangBo update by 2011年3月23日  12时51分
		 * 增加判断条件, 如果是节目预告, 则不加入主文件
		 */
		if(productinfoid != null && !"".equalsIgnoreCase(productinfoid)
				&& 2 == fileStyleCode)
		{	
			/**
			 * 查询主文件的源路径
			 */
			List list = packageFilesManager.getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
					productinfoid, 
					stclassOnline, 
					"",
					mainpf.getFiletypeid()
					);
			if(list != null && list.size() >= 2)
			{
				List<Object[]> list1 = (List<Object[]>)list.get(1);
				Object[] rows = (Object[]) list1.get(0);
				AmsStorage ams = (AmsStorage) rows[0];
				AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
				AmsStorageDir amsd = (AmsStorageDir) rows[2];
				AmsStorageClass amsc = (AmsStorageClass) rows[3];
				
//				String src = amspr.getFilepath();
//				src = src.replace('\\', '/');
//				src = fileopr.checkPathFormatFirst(src, '/');
//				src = fileopr.checkPathFormatRear(src, '/');
//				src += amspr.getFilename();
				
				String updateflag = "N";
				String updatetime = "";
				String mainfilename = "";
				String src = null;
				
				try
				{
					mainfilename = amspr.getFilename();
					mainfilename = mainfilename.endsWith("/") ? mainfilename.substring(0, mainfilename.length() -1) : mainfilename;
				}
				catch(Exception ex)
				{
					cmsLog.error("获得节目包主文件名异常：" + ex.getMessage());
					mainfilename = amspr.getFilename();
				}
				try
				{
					if(mainpf.getUpdateTime() == null)
					{
						updateflag = "N";
						updatetime = fileopr.convertDateToString(mainpf.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
					}
					else
					{
						if(mainpf.getInputtime().compareTo(mainpf.getUpdateTime()) == 0)
						{
							updateflag = "N";
							updatetime = fileopr.convertDateToString(mainpf.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
						}
						else
						{
							updateflag = "Y";
							updatetime = fileopr.convertDateToString(mainpf.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
						}
					}
				}
				catch(Exception ex)
				{
					updateflag = "N";
					updatetime = "";
					cmsLog.error("获得节目包主文件updatetime异常：" + ex.getMessage());
				}
				
				src = amsd.getStoragedirname();
				src = fileopr.checkPathFormatRear(src, '/');
				src += amspr.getFilepath();
				src = src.replace('\\', '/');
				src = fileopr.checkPathFormatFirst(src, '/');
				src = fileopr.checkPathFormatRear(src, '/');
				src += mainfilename;
				src = src.replaceAll("//", "/");
				
				
				BroadcastFile broadcastFile = new BroadcastFile();
				broadcastFile.setProgfileid(amspr.getProgfileid());
				broadcastFile.setFilename(mainfilename);
				broadcastFile.setContentid(contentid);	// CONTENTID（全部为主文件值）
				broadcastFile.setProgrank(String.valueOf(mainpf.getProgrank()));	// 主体文件标识,1-主文件;0-非主文件
				broadcastFile.setFilecode(mainpf.getFilecode());
				broadcastFile.setFiletypeid(amspr.getFtypeglobalid());
				broadcastFile.setSrc(src);						// 源文件路径
				broadcastFile.setUpdateflag(updateflag);		// 更新标识
				broadcastFile.setUpdatetime(updatetime);		// 更新版本
				
				if("RMZIP".equalsIgnoreCase(mainpf.getFilecode()))
				{
					broadcastFile.setIsFolder(true);
				}
				else
				{
					broadcastFile.setIsFolder(false);
				}
				
				broadcastFiles.add(broadcastFile);
			}
		}
		
		
		/**
		 * key文件，根据栏目单详细记录、文件位置表找
		 * 如果节目包是富媒体，则不需要加入key文件节点到播发单xml中
		 */
//		String keyfileid = pld.getProductInfoID();
		if(mainpf != null && !"RMZIP".equalsIgnoreCase(mainpf.getFilecode()))
		{
			if(keyfileid != null && !"".equalsIgnoreCase(keyfileid))
			{
				/**
				 * 查询Key文件的源路径
				 */
				List list = packageFilesManager.getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
						keyfileid, 
						stclassOnline, 
						"",
						"KEY"
						);
				if(list != null && list.size() >= 2)
				{
					List<Object[]> list1 = (List<Object[]>)list.get(1);
					Object[] rows = (Object[]) list1.get(0);
					AmsStorage ams = (AmsStorage) rows[0];
					AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
					AmsStorageDir amsd = (AmsStorageDir) rows[2];
					AmsStorageClass amsc = (AmsStorageClass) rows[3];
					
//					String src = amspr.getFilepath();
//					src = src.replace('\\', '/');
//					src = fileopr.checkPathFormatFirst(src, '/');
//					src = fileopr.checkPathFormatRear(src, '/');
//					src += amspr.getFilename();
					
					String src = amsd.getStoragedirname();
					src = fileopr.checkPathFormatRear(src, '/');
					src += amspr.getFilepath();
					src = src.replace('\\', '/');
					src = fileopr.checkPathFormatFirst(src, '/');
					src = fileopr.checkPathFormatRear(src, '/');
					src += amspr.getFilename();
					src = src.replaceAll("//", "/");
					
					String updateflag = "Y";
					String updatetime = fileopr.convertDateToString(productInfo.getInputTime(), "yyyy-MM-dd HH:mm:ss");
					
					BroadcastFile broadcastFile = new BroadcastFile();
					broadcastFile.setProgfileid(keyfileid);
					broadcastFile.setFilename(amspr.getFilename());
					broadcastFile.setContentid(contentid);	// CONTENTID（全部为主文件值）
					broadcastFile.setProgrank("0");	// 主体文件标识,1-主文件;0-非主文件
					broadcastFile.setFilecode("KEY");
					broadcastFile.setFiletypeid(amspr.getFtypeglobalid());
					broadcastFile.setSrc(src);						// 源文件路径
					broadcastFile.setUpdateflag(updateflag);		// 更新标识
					broadcastFile.setUpdatetime(updatetime);		// 更新版本
					
					broadcastFiles.add(broadcastFile);
				}
			}
		}
		
		/**
		 * js文件，根据栏目单详细记录、文件位置表找
		 */
//		String jsfileid = pld.getJsFileID();
		if(jsfileid != null && !"".equalsIgnoreCase(jsfileid))
		{
			/**
			 * 查询js文件的源路径
			 */
			List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
					jsfileid, 
					stclassOnline, 
					""
					);
			if(list != null && list.size() >= 2)
			{
				List<Object[]> list1 = (List<Object[]>)list.get(1);
				Object[] rows = (Object[]) list1.get(0);
				AmsStorage ams = (AmsStorage) rows[0];
				AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
				AmsStorageDir amsd = (AmsStorageDir) rows[2];
				AmsStorageClass amsc = (AmsStorageClass) rows[3];
				
//				String src = amspr.getFilepath();
//				src = src.replace('\\', '/');
//				src = fileopr.checkPathFormatFirst(src, '/');
//				src = fileopr.checkPathFormatRear(src, '/');
//				src += amspr.getFilename();
				
				String src = amsd.getStoragedirname();
				src = fileopr.checkPathFormatRear(src, '/');
				src += amspr.getFilepath();
				src = src.replace('\\', '/');
				src = fileopr.checkPathFormatFirst(src, '/');
				src = fileopr.checkPathFormatRear(src, '/');
				src += amspr.getFilename();
				src = src.replaceAll("//", "/");
				
				String updateflag = "Y";
				String updatetime = fileopr.convertDateToString(amspr.getUploadtime(), "yyyy-MM-dd HH:mm:ss");
				
				BroadcastFile broadcastFile = new BroadcastFile();
				broadcastFile.setProgfileid(jsfileid);
				broadcastFile.setFilename(amspr.getFilename());
				broadcastFile.setContentid(contentid);	// CONTENTID（全部为主文件值）
				broadcastFile.setProgrank("0");	// 主体文件标识,1-主文件;0-非主文件
				broadcastFile.setFilecode("PPJS");
				broadcastFile.setFiletypeid(amspr.getFtypeglobalid());
				broadcastFile.setSrc(src);						// 源文件路径
				broadcastFile.setUpdateflag(updateflag);		// 更新标识
				broadcastFile.setUpdatetime(updatetime);		// 更新版本
				
				broadcastFiles.add(broadcastFile);
			}
		}


		BroadcastPushCondition broadcastPushCondition = new BroadcastPushCondition();
		broadcastPushCondition.setValue("投递策略代码...");
		broadcastPushConditions.add(broadcastPushCondition);

		
		broadcastProgPackage.setPpid(progPackage.getProductid());
		broadcastProgPackage.setStbTitle(progPackage.getProductname());
		broadcastProgPackage.setKeyids(keyids);
		broadcastProgPackage.setBroadcastFiles(broadcastFiles);

		broadcastProgPackage.setBroadcastPushConditions(broadcastPushConditions);
		
		
		return broadcastProgPackage;
	}
	
	public BroadcastProgPackage getDeleteBroadcastProgPackage(
			ProgPackage progPackage,
			Long fileStyleCode,
			String stclassOnline
			)
	{
		BroadcastProgPackage broadcastProgPackage = new BroadcastProgPackage();
		List<BroadcastFile> broadcastFiles = new ArrayList<BroadcastFile>();
		List<BroadcastPushCondition> broadcastPushConditions = new ArrayList<BroadcastPushCondition>();
		List<ProgramFiles> accPfs = new ArrayList<ProgramFiles>();	// 需要迁移的节目包文件
		ProgramFiles mainpf = null;
		String contentid = "";
		
		
		/**
		 * 判断输入参数合法性
		 */
		if(progPackage != null)
		{
			
		}
		else
		{
			broadcastProgPackage = null;
		}

		
		
		cmsLog.info("查询主文件...");
		List mainpfs = packageFilesManager.getProgramFilesesByProductidProgrank(
				progPackage.getProductid(), 
				(long)1				// 主文件标识，0 - 不是;  1 - 是
				);
		if(mainpfs != null && mainpfs.size() > 0)
		{
			mainpf = (ProgramFiles)mainpfs.get(0);
			contentid = mainpf.getContentId();
			cmsLog.info("主文件ID：" + mainpf.getProgfileid());
			cmsLog.info("contentid：" + contentid);
			cmsLog.info("主文件filecode：" + mainpf.getFilecode());
		}
		
		cmsLog.info("根据文件样式，查询需要迁移的文件列表...");
		accPfs = programFilesManager.getProgramFilesesByProgPackageFilestylecode(
				fileStyleManager, 
				packageFilesManager, 
				progPackage, 
				fileStyleCode
				);
		int accPfsCount = accPfs.size();
		cmsLog.info("需要播发的节目包附属文件数量：" + accPfsCount);
		
		/**
		 * 处理附属文件，根据节目包样式
		 */
		if(accPfsCount > 0)
		{
			for(int m = 0; m < accPfs.size(); m++)
			{
				ProgramFiles pf = accPfs.get(m);
				
				/**
				 * 查询附属文件的源路径
				 */
				List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
						pf.getProgfileid(), 
						stclassOnline, 
						""
						);
				if(list != null && list.size() >= 2)
				{
					List<Object[]> list1 = (List<Object[]>)list.get(1);
					Object[] rows = (Object[]) list1.get(0);
					AmsStorage ams = (AmsStorage) rows[0];
					AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
					AmsStorageDir amsd = (AmsStorageDir) rows[2];
					AmsStorageClass amsc = (AmsStorageClass) rows[3];
					
//					String src = amspr.getFilepath();
//					src = src.replace('\\', '/');
//					src = fileopr.checkPathFormatFirst(src, '/');
//					src = fileopr.checkPathFormatRear(src, '/');
//					src += amspr.getFilename();
					
					String src = amsd.getStoragedirname();
					src = fileopr.checkPathFormatRear(src, '/');
					src += amspr.getFilepath();
					src = src.replace('\\', '/');
					src = fileopr.checkPathFormatFirst(src, '/');
					src = fileopr.checkPathFormatRear(src, '/');
					src += amspr.getFilename();
					src = src.replaceAll("//", "/");
					
					String updateflag = "N";
					String updatetime = "";
					try
					{
						if(pf.getUpdateTime() == null)
						{
							updateflag = "N";
							updatetime = "";
						}
						else
						{
							if(pf.getInputtime().compareTo(pf.getUpdateTime()) == 0)
							{
								updateflag = "N";
								updatetime = "";
							}
							else
							{
								updateflag = "Y";
								updatetime = fileopr.convertDateToString(pf.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
							}
						}
					}
					catch(Exception ex)
					{
						updateflag = "N";
						updatetime = "";
					}
					
					BroadcastFile broadcastFile = new BroadcastFile();
					broadcastFile.setProgfileid(pf.getProgfileid());
					broadcastFile.setFilename(pf.getFilename());
					broadcastFile.setContentid(contentid);	// CONTENTID（全部为主文件值）
					broadcastFile.setProgrank(pf.getProgrank().toString());	// 主体文件标识,1-主文件;0-非主文件
					broadcastFile.setFilecode(pf.getFilecode());
					broadcastFile.setFiletypeid(pf.getFiletypeid());
					broadcastFile.setSrc(src);						// 源文件路径
					broadcastFile.setUpdateflag(updateflag);		// 更新标识
					broadcastFile.setUpdatetime(updatetime);		// 更新版本
					
					broadcastFiles.add(broadcastFile);
				}
				else
				{
					cmsLog.warn("文件在一级库的位置没有查询到。");
					cmsLog.warn("文件ID：" + pf.getProgfileid());
//					errorcount++;
//					errordetails += "文件("
//									+ pf.getFilename() 
//									+ ")在一级库的位置没有查询到，节目包(" 
//									+ pp.getProductname() 
//									+ ")加入播发单，文件未加入播发单。\r\n";
				}
			}
		}

		/**
		 * 查询与节目包相关的所有栏目单详细记录
		 */
		List<ProgListDetail> plds = (List<ProgListDetail>)progListDetailManager.findByProperty("productid", progPackage.getProductid());
		String keyids = "";
		List<String> dealedProductInfoIds = new ArrayList<String>();
		for(int i = 0; i < plds.size(); i++)
		{
			/**
			 * 下面一行代码是，获取节目包相关的所有ts、js、key文件，并加入delete节点
			 */
//			ProgListDetail progListDetail = plds.get(i);
			
			/**
			 * 下面一段代码是，只获取一份节目包相关的ts、js、key文件，并加入delete节点
			 * 因为机顶盒只存储一份ts、js、key文件
			 */
			ProgListDetail progListDetail = plds.get(0);
			if(i > 0)
			{
				break;
			}
			
			String productinfoid = progListDetail.getProductInfoID();
			String keyfileid = "";
			
			String jsfileid = progListDetail.getJsFileID();
			
			TProductInfo productInfo = productinfoManager.getTProductInfoById(productinfoid);
			if(productInfo != null)
			{
				keyfileid = productInfo.getKeyFileId();
				keyids = productInfo.getKeyId();
			}
			else
			{
				continue;
			}
			if(!dealedProductInfoIds.contains(productinfoid))
			{
				dealedProductInfoIds.add(productinfoid);
			}
			else
			{
				continue;
			}
			
			/**
			 * 主文件，根据栏目单详细记录、文件位置表找
			 */
			if(productinfoid != null && !"".equalsIgnoreCase(productinfoid))
			{	
				/**
				 * 查询主文件的源路径
				 */
				List list = packageFilesManager.getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
						productinfoid, 
						stclassOnline, 
						"",
						mainpf.getFiletypeid()
						);
				if(list != null && list.size() >= 2)
				{
					List<Object[]> list1 = (List<Object[]>)list.get(1);
					Object[] rows = (Object[]) list1.get(0);
					AmsStorage ams = (AmsStorage) rows[0];
					AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
					AmsStorageDir amsd = (AmsStorageDir) rows[2];
					AmsStorageClass amsc = (AmsStorageClass) rows[3];
					
//					String src = amspr.getFilepath();
//					src = src.replace('\\', '/');
//					src = fileopr.checkPathFormatFirst(src, '/');
//					src = fileopr.checkPathFormatRear(src, '/');
//					src += amspr.getFilename();
					String updateflag = "N";
					String updatetime = "";
					String mainfilename = "";
					String src = null;
					
					try
					{
						mainfilename = amspr.getFilename();
						mainfilename = mainfilename.endsWith("/") ? mainfilename.substring(0, mainfilename.length() -1) : mainfilename;
					}
					catch(Exception ex)
					{
						cmsLog.error("获得节目包主文件名异常：" + ex.getMessage());
						mainfilename = amspr.getFilename();
					}
					try
					{
						if(mainpf.getUpdateTime() == null)
						{
							updateflag = "N";
							updatetime = "";
						}
						else
						{
							if(mainpf.getInputtime().compareTo(mainpf.getUpdateTime()) == 0)
							{
								updateflag = "N";
								updatetime = "";
							}
							else
							{
								updateflag = "Y";
								updatetime = fileopr.convertDateToString(mainpf.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
							}
						}
					}
					catch(Exception ex)
					{
						updateflag = "N";
						updatetime = "";
					}
					
					src = amsd.getStoragedirname();
					src = fileopr.checkPathFormatRear(src, '/');
					src += amspr.getFilepath();
					src = src.replace('\\', '/');
					src = fileopr.checkPathFormatFirst(src, '/');
					src = fileopr.checkPathFormatRear(src, '/');
					src += mainfilename;
					src = src.replaceAll("//", "/");
					
					BroadcastFile broadcastFile = new BroadcastFile();
					broadcastFile.setProgfileid(amspr.getProgfileid());
					broadcastFile.setFilename(mainfilename);
					broadcastFile.setContentid(contentid);	// CONTENTID（全部为主文件值）
					broadcastFile.setProgrank(String.valueOf(mainpf.getProgrank()));	// 主体文件标识,1-主文件;0-非主文件
					broadcastFile.setFilecode(mainpf.getFilecode());
					broadcastFile.setFiletypeid(amspr.getFtypeglobalid());
					broadcastFile.setSrc(src);						// 源文件路径
					broadcastFile.setUpdateflag(updateflag);		// 更新标识
					broadcastFile.setUpdatetime(updatetime);		// 更新版本
					if("RMZIP".equalsIgnoreCase(mainpf.getFilecode()))
					{
						broadcastFile.setIsFolder(true);
					}
					else
					{
						broadcastFile.setIsFolder(false);
					}
					
					broadcastFiles.add(broadcastFile);
				}
			}
			
			
			/**
			 * key文件，根据栏目单详细记录、文件位置表找
			 * 如果节目包是富媒体，则不需要加入key文件节点到播发单xml中
			 */
	//		String keyfileid = pld.getProductInfoID();
			if(mainpf != null && !"RMZIP".equalsIgnoreCase(mainpf.getFilecode()))
			{
				if(keyfileid != null && !"".equalsIgnoreCase(keyfileid))
				{
					/**
					 * 查询Key文件的源路径
					 */
					List list = packageFilesManager.getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
							keyfileid, 
							stclassOnline, 
							"",
							"KEY"
							);
					if(list != null && list.size() >= 2)
					{
						List<Object[]> list1 = (List<Object[]>)list.get(1);
						Object[] rows = (Object[]) list1.get(0);
						AmsStorage ams = (AmsStorage) rows[0];
						AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
						AmsStorageDir amsd = (AmsStorageDir) rows[2];
						AmsStorageClass amsc = (AmsStorageClass) rows[3];
						
//						String src = amspr.getFilepath();
//						src = src.replace('\\', '/');
//						src = fileopr.checkPathFormatFirst(src, '/');
//						src = fileopr.checkPathFormatRear(src, '/');
//						src += amspr.getFilename();
						
						String src = amsd.getStoragedirname();
						src = fileopr.checkPathFormatRear(src, '/');
						src += amspr.getFilepath();
						src = src.replace('\\', '/');
						src = fileopr.checkPathFormatFirst(src, '/');
						src = fileopr.checkPathFormatRear(src, '/');
						src += amspr.getFilename();
						src = src.replaceAll("//", "/");
						
						String updateflag = "Y";
						String updatetime = fileopr.convertDateToString(productInfo.getInputTime(), "yyyy-MM-dd HH:mm:ss");
						
						BroadcastFile broadcastFile = new BroadcastFile();
						broadcastFile.setProgfileid(keyfileid);
						broadcastFile.setFilename(amspr.getFilename());
						broadcastFile.setContentid(contentid);	// CONTENTID（全部为主文件值）
						broadcastFile.setProgrank("0");	// 主体文件标识,1-主文件;0-非主文件
						broadcastFile.setFilecode("KEY");
						broadcastFile.setFiletypeid(amspr.getFtypeglobalid());
						broadcastFile.setSrc(src);						// 源文件路径
						broadcastFile.setUpdateflag(updateflag);		// 更新标识
						broadcastFile.setUpdatetime(updatetime);		// 更新版本
						
						broadcastFiles.add(broadcastFile);
					}
				}
			}
			
			/**
			 * js文件，根据栏目单详细记录、文件位置表找
			 */
	//		String jsfileid = pld.getJsFileID();
			if(jsfileid != null && !"".equalsIgnoreCase(jsfileid))
			{
				/**
				 * 查询js文件的源路径
				 */
				List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
						jsfileid, 
						stclassOnline, 
						""
						);
				if(list != null && list.size() >= 2)
				{
					List<Object[]> list1 = (List<Object[]>)list.get(1);
					Object[] rows = (Object[]) list1.get(0);
					AmsStorage ams = (AmsStorage) rows[0];
					AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
					AmsStorageDir amsd = (AmsStorageDir) rows[2];
					AmsStorageClass amsc = (AmsStorageClass) rows[3];
					
//					String src = amspr.getFilepath();
//					src = src.replace('\\', '/');
//					src = fileopr.checkPathFormatFirst(src, '/');
//					src = fileopr.checkPathFormatRear(src, '/');
//					src += amspr.getFilename();
					
					String src = amsd.getStoragedirname();
					src = fileopr.checkPathFormatRear(src, '/');
					src += amspr.getFilepath();
					src = src.replace('\\', '/');
					src = fileopr.checkPathFormatFirst(src, '/');
					src = fileopr.checkPathFormatRear(src, '/');
					src += amspr.getFilename();
					src = src.replaceAll("//", "/");
					
					String updateflag = "Y";
					String updatetime = fileopr.convertDateToString(amspr.getUploadtime(), "yyyy-MM-dd HH:mm:ss");
					
					BroadcastFile broadcastFile = new BroadcastFile();
					broadcastFile.setProgfileid(jsfileid);
					broadcastFile.setFilename(amspr.getFilename());
					broadcastFile.setContentid(contentid);	// CONTENTID（全部为主文件值）
					broadcastFile.setProgrank("0");	// 主体文件标识,1-主文件;0-非主文件
					broadcastFile.setFilecode("PPJS");
					broadcastFile.setFiletypeid(amspr.getFtypeglobalid());
					broadcastFile.setSrc(src);						// 源文件路径
					broadcastFile.setUpdateflag(updateflag);		// 更新标识
					broadcastFile.setUpdatetime(updatetime);		// 更新版本
					
					broadcastFiles.add(broadcastFile);
				}
			}
		}

		BroadcastPushCondition broadcastPushCondition = new BroadcastPushCondition();
		broadcastPushCondition.setValue("投递策略代码...");
		broadcastPushConditions.add(broadcastPushCondition);

		
		broadcastProgPackage.setPpid(progPackage.getProductid());
		broadcastProgPackage.setStbTitle(progPackage.getProductname());
		broadcastProgPackage.setKeyids(keyids);
		broadcastProgPackage.setBroadcastFiles(broadcastFiles);

		broadcastProgPackage.setBroadcastPushConditions(broadcastPushConditions);
		
		
		return broadcastProgPackage;
	}
	
	//=============================================================================================
	
	//---------------------- 1.23 接口方法 ----------------------------------------------------------
	/**
	 * 20110111 16:10 1.23 生成播发单xml文件
	 * @param date，格式：yyyy-MM-dd
	 * @param operatorId
	 * @param plandate
	 * @return CmsResultDto.errordetails不为空时，播发单文件生成，但是有节目包未加入到播发单中的情况。
	 */
	public CmsResultDto saveGenerateBroadcastxml_123(
			String date, 			// yyyy-MM-dd
			String operatorId, 		// 操作人员id
			String plandate, 		// 计划播发日期，格式："yyyy-MM-dd HH:mm:ss"
			boolean forceToGenerate,	// 强制生成播发单，如果为true，当某个品牌或节目包出现错误的情况，也继续生成播发单xml文件。
			List<String> unEncryptCode, String scheduleDays
			)
	{
		// cms 1.21 版本，生成播发单，
		// 由于媒资不能上线，节目来源仍然是cms导入，contentid用节目包主文件的文件id来代替。
		// 1 - 查询date的栏目单详细记录
		// 2 - 根据栏目分类节目包
		// 播发单下发完成，保存发布文件表记录progListfile，并且发送活动 86-->87 播放单生成成功，发送
		
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> saveGenerateBroadcastxml_123...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		int errorcount = 0;
		String errordetails = "";
		
		// 配置文件库中
		String serverOS = cc.getPropertyByName("ServerOS");		// 服务器操作系统
//		String scheduleDays = cc.getPropertyByName("ScheduleDays");		// 天数
		String broadcastXmlTemp = cc.getPropertyByName("BroadcastXmlTemp"); 	// 播发单xml本地临时目录
		
		String stclassOnline = "Online"; 		// 一级等级
		String filecodeBroadcast = "BRDXML";
//		String rootPortalColumnDefcatseq = "";
		String localfile = "";					// 播发单本地临时路径
		String broadcastXmlFullPath = "";		// 播发单路径
//		String errorDetail = "";
		long fileStyleCode = 0L;		// 1 - 节目预告 ; 2 - 播发单
		Date nowDate = new Date();
		String scheduledate = fileopr.convertDateToScheduleDate(date);
		Date ddate = fileopr.convertStringToDate(date, "yyyy-MM-dd");
		
		
		
		
		long days = 7L;
		try {
			days = Long.valueOf(scheduleDays);
		} catch (Exception e) {
			cmsLog.warn("预告天数类型转换异常：" + e.getMessage());
		}

		// 播发单文件名
		String broadcastXmlName = "broadcast_" + date +"_";
		broadcastXmlName += fileopr.convertDateToString(nowDate,"yyyyMMddHHmmss");
		broadcastXmlName += ".xml";
		
		/**
		 * 查询播发单的路径
		 */
		List<String> filepaths = getBroadcastXmlFilepath(
				filecodeBroadcast, 
				stclassOnline, 
				date, 
				broadcastXmlTemp, 
				serverOS
				);
		if(filepaths != null && filepaths.size() >= 2)
		{
			broadcastXmlFullPath = filepaths.get(0);
			localfile = filepaths.get(1);
			
			localfile = localfile + broadcastXmlName;
			broadcastXmlFullPath = broadcastXmlFullPath + broadcastXmlName;
			
			cmsLog.info("播发单本地临时路径 - " + localfile);
			cmsLog.info("播发单目标路径 - " + broadcastXmlFullPath);
			
			/**
			 * 判断当前活动是否允许生成播发单
			 */
			cmsLog.info("判断编单总表活动是否是“播发”(FU00000086)...");
			cmsLog.info("编单总表日期：" + scheduledate);
			ProgListMang progListMang = (ProgListMang)progListMangManager.getById(scheduledate);

			// 判断编单总表活动是否是“播发”(FU00000086)
			if ("FU00000086".equalsIgnoreCase(progListMang.getIdAct()))
			{
				cmsLog.info("编单总表活动是“播发”(FU00000086)，允许生成播发单...");
				cmsLog.info("获得播发单xml文件生成的目标路径...");

				/**
				 * 查询品牌
				 * 查询品牌下所有栏目
				 * 查询栏目下所有节目包
				 * 查询节目包下所有文件
				 */
				cmsLog.info("查询所有品牌...");
				List cmsSites = cmsSiteManager.findAll();
				if(cmsSites != null && cmsSites.size() > 0)
				{
					cmsLog.info("共有" + cmsSites.size() + "个品牌。");

					BroadcastPushVod broadcastPushVod = new BroadcastPushVod();
					List<BroadcastSite> broadcastSites = new ArrayList<BroadcastSite>();
					for (int i = 0; i < cmsSites.size(); i++)
					{
						CmsSite cmsSite = (CmsSite) cmsSites.get(i);
						
						cmsLog.info("处理第" + (i + 1) + "个品牌...");
						cmsLog.info("品牌code：" + cmsSite.getSiteCode());
						cmsLog.info("品牌名称：" + cmsSite.getSitename());
						
						/**
						 * 判断当前编单中是否有文件未到位的节目包，如果有不能生成播发单
						 */
						cmsLog.info(" 判断当前编单中是否有文件未到位的节目包..." );
						if(checkProgPackageFilesReady_123(stclassOnline, 
								2L, scheduledate, cmsSite.getSiteCode(), unEncryptCode)){
							// 节目包文件都到位
							cmsLog.info("该品牌即将播发的节目包文件，已经完全到位（一级库），该品牌加入播发单，继续...");
						}
						else
						{
							// 节目包文件未完全到位
							cmsLog.info("该品牌即将播发的节目包文件，尚未完全到位（一级库），该品牌不加入播发单，跳过...");
							errorcount++;
							errordetails += "品牌(" + cmsSite.getSitename() + ")即将播发的节目包文件，尚未完全到位（一级库），该品牌不加入播发单。\r\n";
							continue;
						}
						
						
						cmsLog.info("得到品牌下所有有效的、叶子节点栏目...");
						List pcs = portalColumnManager.queryPortalColumnsByScheduleDate(
								date.replaceAll("-", "") + "000000",
								cmsSite.getSiteCode()
								);
						cmsLog.info("品牌下共有" + pcs.size() + "个有效的、叶子节点栏目...");
						
						BroadcastSite broadcastSite = new BroadcastSite();// 品牌
						List<BroadcastProglist> broadcastProglists = new ArrayList<BroadcastProglist>();
//						List<BroadcastJs> broadcastJses = new ArrayList<BroadcastJs>();
//						List<BroadcastDelete> broadcastDeletes = new ArrayList<BroadcastDelete>();
						BroadcastJs broadcastJs = new BroadcastJs();
						BroadcastDelete broadcastDelete = new BroadcastDelete();

						for(long d = 0; d < days; d++)
						{
							Date dscheduledate = new Date();
							dscheduledate.setTime(ddate.getTime() + 24*60*60*1000*d);
							String strscheduledate = fileopr.convertDateToString(dscheduledate, "yyyyMMdd000000");
							
							if(d == 0)
							{
								cmsLog.info("处理当日编单...");
								cmsLog.info("编单日期：" + strscheduledate);
								fileStyleCode = 2L;
								
							}
							else
							{
								cmsLog.info("处理节目预告编单...");
								cmsLog.info("编单日期：" + strscheduledate);
								fileStyleCode = 1L;
							}
							
							// 编单节点
							BroadcastProglist broadcastProglist = new BroadcastProglist();
							List<BroadcastColumn> broadcastColumns = new ArrayList<BroadcastColumn>();

							for(int j = 0; j < pcs.size(); j++)
							{
								PortalColumn tempPortalColumn = (PortalColumn) pcs.get(j);
								PortalColumn portalColumn = (PortalColumn) this.portalColumnManager.getById(tempPortalColumn.getColumnclassid());
								
								cmsLog.info("处理第" + (i+1) + "个栏目...");
								cmsLog.info("栏目code：" + portalColumn.getDefcatcode());
								cmsLog.info("栏目名称：" + portalColumn.getColumnname());
								cmsLog.info("栏目id：" + portalColumn.getColumnclassid());
								
								BroadcastColumn broadcastColumn = new BroadcastColumn();
								List<BroadcastProgPackage> broadcastProgPackages = new ArrayList<BroadcastProgPackage>();			
								
								cmsLog.info("编单日期：" + strscheduledate);
								cmsLog.info("查询栏目下的栏目单详细(ProgListDetail)...");
								List plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
										strscheduledate, 
										portalColumn.getColumnclassid()
										);
								cmsLog.info("栏目单详细(ProgListDetail)记录数量：" + plds.size());
								for(int k = 0; k < plds.size(); k++)
								{
									ProgListDetail pld = (ProgListDetail)plds.get(k);
									// 获得节目包
									ProgPackage pp = (ProgPackage)progPackageManager.getById(pld.getProductid());
									
									
									List<ProgramFiles> pfs = new ArrayList<ProgramFiles>();	// 需要加入播发单的节目包文件
									String contentid = "";		// 目前为主文件的progfileid
									String productinfoid = pld.getProductInfoID();
									String jsfileid = pld.getJsFileID();
									String keyfileid = null;
									String keyids = "";
									
									cmsLog.info("处理第" + (k+1) + "个栏目单详细...");
									cmsLog.info("节目包ID：" + pld.getProductid());
									cmsLog.info("节目包名称：" + pld.getProductname());
									cmsLog.info("文件样式应用代码：" + fileStyleCode);
									cmsLog.info("节目包的样式ID：" + pp.getStyleid());
									cmsLog.info("产品信息ID：" + productinfoid);
									cmsLog.info("节目包JS文件ID：" + jsfileid);
									
									TProductInfo productInfo = productinfoManager.getTProductInfoById(productinfoid);
									if(productInfo != null)
									{
										keyfileid = productInfo.getKeyFileId();
										keyids = productInfo.getKeyId();
									}
									cmsLog.info("节目包Key文件ID：" + keyfileid);
									
									if(d != 0)		// 是节目预告，不是当日编单
									{
										/**
										 * 如果是节目预告（不是当日节目）
										 * 并且，栏目单详细不加入节目预告
										 * 则，不处理，跳过
										 */
										if(!"Y".equalsIgnoreCase(pld.getScheduleTag()))
										{
											cmsLog.info("栏目单详细记录属于节目预告编单，且不加入节目预告，不处理，跳过...");
											continue;
										}
									}
									else			// 不是节目预告，是当日编单
									{
										
									}
									if(productinfoid == null || "".equalsIgnoreCase(productinfoid))
									{
										cmsLog.info("栏目单详细的产品信息ID为空，不处理，跳过...");
										errorcount++;
										errordetails += "栏目单详细(" + pld.getProductname() + ")的产品信息ID为空，不加入播发单。\r\n";
										continue;
									}
									if(jsfileid == null || "".equalsIgnoreCase(jsfileid))
									{
										cmsLog.info("栏目单详细的JS文件ID为空，不处理，跳过...");
										errordetails += "栏目单详细(" + pld.getProductname() + ")的JS文件ID为空，不加入播发单。\r\n";
										errorcount++;
										continue;
									}
									if(keyfileid == null || "".equalsIgnoreCase(keyfileid))
									{
										cmsLog.info("栏目单详细的Key文件ID为空，不处理，跳过...");
										errorcount++;
										errordetails += "栏目单详细(" + pld.getProductname() + ")的Key文件ID为空，不加入播发单。\r\n";
										continue;
									}
									
									/** 
									 * 获得节目包下文件列表（根据文件样式）
									 * 编单当日的节目包文件，不适用于文件样式，取节目包所有文件
									 * 编单日后的编单，根据文件样式，取节目包的文件用于生成播发单
									 */ 
									
									/**
									 * 根据应用（生成节目预告），
									 * 查询文件样式表（FileStyle），
									 * 得到允许加入播发单预告部分的节目包文件的filetype列表
									 * 另外，需要判断栏目单详细(proglistdetail)的节目预告标志(scheduletag)
									 */

									BroadcastProgPackage broadcastProgPackage = null;
									broadcastProgPackage = getBroadcastProgPackage(
											pld, 
											pp, 
											productInfo, 
											fileStyleCode, 
											stclassOnline
											);
									if(broadcastProgPackage != null)
									{
										broadcastProgPackages.add(broadcastProgPackage);
									}
									else
									{
										/**
										 * 处理栏目单详细（节目包）失败
										 */
										errorcount++;
										errordetails += "处理栏目单详细(" + pp.getProductname() + ")失败，不加入播发单。\r\n";
									}

								}	// for(int k = 0; k < plds.size(); k++)
								
								broadcastColumn.setDefCatCode(portalColumn.getDefcatcode());
								broadcastColumn.setName(portalColumn.getColumnname());
								broadcastColumn.setType(portalColumn.getArchivedays().toString());
								broadcastColumn.setSiteCode(cmsSite.getSiteCode());
								broadcastColumn.setBroadcastProgPackages(broadcastProgPackages);
								broadcastColumns.add(broadcastColumn);
							}	// for(int j = 0; j < pcs.size(); j++)
							
							broadcastProglist.setScheduleDate(strscheduledate);
							broadcastProglist.setBroadcastColumns(broadcastColumns);
							broadcastProglists.add(broadcastProglist);
						}	// for(long d = 0; d < days; d++)
						
						/**
						 * 节目预告JS文件
						 * 在proglistfile表，根据日期、有效标识、type(6)、文件名(CmsSite表)，找到当日有效的节目预告记录
						 * 根据proglistfile的主键，在文件位置表查询
						 * 
						 */
						String ygjsSrc = null;
						List<ProgListFile> proglistfiles = progListFileManager.getProgListFilesByScheduledateFiletypeState1Filename(
								scheduledate, 
								(long)6, 
								(long)0, 
								cmsSite.getEpgpath()
								);
						if(proglistfiles != null && proglistfiles.size() > 0)
						{
							ProgListFile progListFile = proglistfiles.get(0);
							cmsLog.info("节目预告proglistfileid：" + progListFile.getColumnfileid());
							cmsLog.info("节目预告名称：" + progListFile.getFilename());
							List list = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									progListFile.getColumnfileid(),
									stclassOnline, 
									""
									);
							if(list != null && list.size() >= 2)
							{
								List<Object[]> list1 = (List<Object[]>)list.get(1);
								Object[] rows = (Object[]) list1.get(0);
								AmsStorage ams = (AmsStorage) rows[0];
								AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
								AmsStorageDir amsd = (AmsStorageDir) rows[2];
								AmsStorageClass amsc = (AmsStorageClass) rows[3];
	
								ygjsSrc = amsd.getStoragedirname();
								ygjsSrc = fileopr.checkPathFormatRear(ygjsSrc, '/');
								ygjsSrc += amspr.getFilepath();
								ygjsSrc = ygjsSrc.replace('\\', '/');
								ygjsSrc = fileopr.checkPathFormatFirst(ygjsSrc, '/');
								ygjsSrc = fileopr.checkPathFormatRear(ygjsSrc, '/');
								ygjsSrc += amspr.getFilename();
								ygjsSrc = ygjsSrc.replaceAll("//", "/");
	
							}
							else
							{
								cmsLog.warn("节目预告js文件在一级库的位置没有查询到。");
							}
						}
						else
						{
							ygjsSrc = "";
						}
						List<BroadcastYgJs> broadcastYgJses = new ArrayList<BroadcastYgJs>();
						BroadcastYgJs broadcastYgJs = new BroadcastYgJs();
						broadcastYgJs.setFileName(cmsSite.getEpgpath());
						broadcastYgJs.setScheduleDate(scheduledate);
						broadcastYgJs.setSrc(ygjsSrc);
						broadcastYgJses.add(broadcastYgJs);
						broadcastJs.setBroadcastYgJses(broadcastYgJses);
						
						/**
						 * 查询栏目单删除详细（强制删除）
						 */
						List<BroadcastProgPackage> broadcastDeleteProgPackages = new ArrayList<BroadcastProgPackage>();
						/**
						 * 根据编单日期，查询编单删除详细
						 */
						List<ProgListDeleteDetail> pldds = (List<ProgListDeleteDetail>)progListDeleteDetailManager.getProgListDeleteDetailsBySitecodeScheduledate(
								cmsSite.getSiteCode(), scheduledate
								);
						for(int k = 0; k < pldds.size(); k++)
						{
							ProgListDeleteDetail pldd = (ProgListDeleteDetail)pldds.get(k);
							// 获得节目包
							ProgPackage deletePp = (ProgPackage)progPackageManager.getById(pldd.getProgPackageId());
							
							
							List<ProgramFiles> pfs = new ArrayList<ProgramFiles>();	// 需要加入播发单的节目包文件
							String contentid = "";		// 目前为主文件的progfileid

							cmsLog.info("处理第" + (k+1) + "个栏目单删除详细...");
							cmsLog.info("节目包ID：" + deletePp.getProductid());
							cmsLog.info("节目包名称：" + deletePp.getProductname());
							cmsLog.info("文件样式应用代码：" + fileStyleCode);
							cmsLog.info("节目包的样式ID：" + deletePp.getStyleid());
							
							BroadcastProgPackage deleteProadcastProgPackage = getDeleteBroadcastProgPackage(
									deletePp, 
									fileStyleCode, 
									stclassOnline
									);
							
							/**
							 * 增加强制删除节目包下的文件验证, 如果文件数量为0, 则返回错误信息, 该节目包文件已经被删除, 不允许加入强制删除!
							 * HuangBo addition by 2011年11月28日 15时7分
							 */
							if (1 > deleteProadcastProgPackage.getBroadcastFiles().size()) {
								errordetails += "强制删除节目包[ " + deletePp.getProductid() + " : " + deletePp.getProductname() 
										+ " ]一级库文件不存在, 不允许加入强制删除列表!"; 
								cmsLog.error(errordetails);
								cmsResultDto.setResultCode(1L);
								cmsResultDto.setErrorMessage(errordetails);
								return cmsResultDto;
							}
							
							if (deleteProadcastProgPackage != null) {
								broadcastDeleteProgPackages
										.add(deleteProadcastProgPackage);
							}
						}
						broadcastDelete.setBroadcastProgPackages(broadcastDeleteProgPackages);
						
						broadcastSite.setBroadcastTime(plandate);// 播发时间
						broadcastSite.setSiteCode(cmsSite.getSiteCode());// 品牌code
						broadcastSite.setSiteName(cmsSite.getSitename());// 品牌名称
						broadcastSite.setScheduleDate(scheduledate);// 栏目单日期
						broadcastSite.setBroadcastProglists(broadcastProglists);
						broadcastSite.setBroadcastJs(broadcastJs);
						broadcastSite.setBroadcastDelete(broadcastDelete);
						broadcastSites.add(broadcastSite);
						
						/**
						 * 该死的1.3要求品牌合并
						 * 本着改动最小原则, 采用一个品牌查询出所有栏目, 得出所有品牌之后只需要循环一次就OK
						 * HuangBo update by 2012年07月24日 15时02分
						 */
						break;
					}	// for (int i = 0; i < cmsSites.size(); i++)
					
					broadcastPushVod.setId("");
					broadcastPushVod.setBroadcastSites(broadcastSites);
					
					boolean generateXmlFile = false;
					if(errorcount > 0)
					{
						if(forceToGenerate == true)
						{
							generateXmlFile = true;
						}
						else
						{
							generateXmlFile = false;
						}
					}	
					else
					{
						generateXmlFile = true;
					}
					/**
					 * 生成播发单xml Document
					 */
					if(generateXmlFile == true)
					{
						Document document = generateXmlDoc(broadcastPushVod);
						
						/**
						 * HuangBo addition by 2011年11月7日16时48分
						 * 增加生成播发单的品牌完整性验证
						 */
						if(2 != document.selectNodes("//Site").size()) {
							String str = "生成播发单xml文件不完整,请检查。";
							cmsLog.warn(str);
							cmsResultDto.setErrorMessage(str);
							cmsResultDto.setResultCode((long)1);
							return cmsResultDto;
						}
						
						/**
						 * 1 - 把xml字符串增加到ProgListfile记录，获取新增记录的id，设置新增记录为无效状态(state1 = 1)
						 * 2 - 把新增id设置为xml的pushvod id
						 * 3 - 生成播发单的xml文件在本地
						 * 4 - 复制本地播发单xml文件到网络路径（一级库）
						 * 5 - 设置新增ProgListfile记录为有效状态(state1 = 0)
						 */
						ProgListFile progListFile = new ProgListFile();
						progListFile.setScheduledate(scheduledate);
						progListFile.setFilename(broadcastXmlName);
						progListFile.setFiletype((long) 9);
						progListFile.setDate1(nowDate);
						progListFile.setDate4(fileopr.convertStringToDate(plandate, "yyyy-MM-dd HH:mm:ss"));
						progListFile.setState1((long) 1);		// 设置为无效
						progListFile.setState2((long) 0);
						progListFile.setInputmanid(operatorId);
	//					progListFile.setColumnxml(strxml);
						progListFile.setInputtime(nowDate);
	
						int ret = saveBroadcastXmlFile(
								broadcastPushVod, 
								document, broadcastXmlFullPath, localfile, 
								progListFile, date, operatorId);
						if(ret == 0)
						{
							cmsLog.info("生成播发单xml文件成功。");
							cmsLog.info("目标路径 - " + broadcastXmlFullPath);
						}
						else
						{
							String str = "生成播发单xml文件失败。";
							cmsLog.warn(str);
							cmsResultDto.setErrorMessage(str);
							cmsResultDto.setResultCode((long)1);
						}
					}
					else
					{
						String str = "生成播发单过程中发生错误，不强制生成播发单xml文件。";
						cmsLog.warn(str);
						cmsResultDto.setErrorMessage(str);
						cmsResultDto.setResultCode((long)1);
					}
				}
				else
				{
					String str = "品牌数量为0，不生成播发单。";
					cmsLog.warn(str);
					cmsResultDto.setErrorMessage(str);
				}
			}
			else
			{
				String str = "编单总表活动不是“播发”(FU00000086)，不生成播发单。";
				cmsLog.warn(str);
				cmsLog.info("编单总表活动：" + progListMang.getIdAct());
				cmsResultDto.setErrorMessage(str);
				cmsResultDto.setResultCode((long)1);
			}
		}
		else
		{
			String str = "查询播发单路径失败，不生成播发单。";
			cmsLog.warn(str);
			cmsResultDto.setErrorMessage(str);
			cmsResultDto.setResultCode((long)1);
		}
		cmsResultDto.setErrorDetail(errordetails);

		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> saveGenerateBroadcastxml_123 returns.");
		return cmsResultDto;
	}
	
	/**
	 * 20110114 10:52 1.23 查询编单删除详细记录
	 * @param date	Format:"2011-01-14"
	 * @return
	 */
	public CmsResultDto getProgListDeleteDetails(String date, String operatorId)
	{
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> getProgListDeleteDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsLog.info("输入参数：");
		cmsLog.info("date - " + date);
		cmsLog.info("operatorId - " + operatorId);
		
		String scheduleDate = fileopr.convertDateToScheduleDate(date);
		List<ProgListDeleteDetail> pldds = progListDeleteDetailManager.getProgListDeleteDetailsByScheduledate(scheduleDate);
		cmsResultDto.setResultObject(pldds);
		
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> getProgListDeleteDetails returns.");
		return cmsResultDto;
	}
	
	/**
	 * 20110114 10:52 1.23 保存（创建）编单删除详细记录
	 * @param date	Format:"2011-01-14"
	 * @param progPackages	List<ProgPackage>
	 * @param operatorId
	 * @return
	 */
	public CmsResultDto saveProgListDeleteDetails(String date, List<ProgPackage> progPackages, String operatorId)
	{
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> saveProgListDeleteDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsLog.info("输入参数：");
		cmsLog.info("date - " + date);
		cmsLog.info("operatorId - " + operatorId);
		
		Date ddate = new Date();
		String scheduleDate = fileopr.convertDateToScheduleDate(date);
		
		if(progPackages != null && progPackages.size() > 0)
		{
			for(int i = 0; i < progPackages.size(); i++)
			{
				ProgPackage pp = progPackages.get(i);
				
				ProgListDeleteDetail pldd = new ProgListDeleteDetail();
				pldd.setProgPackageId(pp.getProductid());
				pldd.setProgPackageName(pp.getProductname());
				pldd.setScheduleDate(scheduleDate);
				pldd.setSiteCode(pp.getSiteCode());
				pldd.setInputManId(operatorId);
				pldd.setInputTime(ddate);
				
				progListDeleteDetailManager.save(pldd);
				
				cmsLog.info("编单删除详细已保存。");
				cmsLog.info("编单删除详细ID：" + pldd.getId());
				cmsLog.info("节目包ID：" + pldd.getProgPackageId());
				cmsLog.info("节目包名称：" + pldd.getProgPackageName());
			}
		}
		else
		{
			String str = "节目包列表为空。";
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}
		
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> saveProgListDeleteDetails returns.");
		return cmsResultDto;
	}
	
	/**
	 * 20110114 10:52 1.23 删除编单删除详细记录
	 * @param progListDeleteDetailids	List<String>
	 * @param operatorId
	 * @return
	 */
	public CmsResultDto deleteProgListDeleteDetails(List<String> progListDeleteDetailids, String operatorId)
	{
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> deleteProgListDeleteDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsLog.info("输入参数：");
		cmsLog.info("operatorId - " + operatorId);
		
		if(progListDeleteDetailids != null && progListDeleteDetailids.size() > 0)
		{
			for(int i = 0; i < progListDeleteDetailids.size(); i++)
			{
				String plddids = progListDeleteDetailids.get(i);
				
				ProgListDeleteDetail pldd = (ProgListDeleteDetail)progListDeleteDetailManager.getById(plddids);
				cmsLog.info("准备删除编单删除详细记录...");
				cmsLog.info("编单删除详细ID：" + pldd.getId());
				cmsLog.info("节目包ID：" + pldd.getProgPackageId());
				cmsLog.info("节目包名称：" + pldd.getProgPackageName());
				cmsLog.info("品牌code：" + pldd.getSiteCode());
				cmsLog.info("创建人员：" + pldd.getInputManId());
				cmsLog.info("创建日期：" + fileopr.convertDateToString(pldd.getInputTime(), "yyyy-MM-dd HH:mm:ss"));
				
				progListDeleteDetailManager.deleteById(pldd.getId());
				cmsLog.info("编单删除详细记录已经删除。");
			}
		}
		else
		{
			String str = "编单删除详细列表为空。";
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}
		
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> deleteProgListDeleteDetails returns.");
		return cmsResultDto;
	}
	
	
	//=============================================================================================
	
	//------------------ 旧代码，不使用 -------------------------------------------------------------
	/**
	 * 20101126 9:52 1.2 不使用，生成播发单xml文件
	 * @param date
	 * @param operatorId
	 * @param plandate
	 * 
	 */
	public CmsResultDto saveGenerateBroadcastxmlNotUse(
//			ICmsTransactionManager cmsTransactionManager,
//			ICmsSiteManager cmsSiteManager,
//			IPortalColumnManager portalColumnManager,
//			IProgListDetailManager progListDetailManager,
//			IProgPackageManager progPackageManager,
//			IFileStyleManager fileStyleManager,
//			IPackageFilesManager packageFilesManager,
//			IProgListMangManager progListMangManager,
//			IProgListFileManager progListFileManager,
//			IProgListMangDetailManager progListMangDetailManager, 
//			IBpmcManager bpmcManager,
//			IFlowActivityOrderManager flowActivityOrderManager,
			String date, 			// yyyy-MM-dd
			String operatorId, 		// 操作人员id
			String plandate, 		// 计划播发日期，格式："yyyy-MM-dd HH:mm:ss"
			String scheduleDays
			)
	{
		// cms 1.21 版本，生成播发单，
		// 由于媒资不能上线，节目来源仍然是cms导入，contentid用节目包主文件的文件id来代替。
		// 1 - 查询date的栏目单详细记录
		// 2 - 根据栏目分类节目包
		// 播发单下发完成，保存发布文件表记录progListfile，并且发送活动 86-->87 播放单生成成功，发送
		
		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> generateBroadcastXml...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		int errorcount = 0;
		String errordetails = "";
		
		// 配置文件库中
		String serverOS = cc.getPropertyByName("ServerOS");		// 服务器操作系统
//		String scheduleDays = cc.getPropertyByName("ScheduleDays");		// 天数
		String broadcastXmlTemp = cc.getPropertyByName("BroadcastXmlTemp"); 	// 播发单xml本地临时目录
		
		String stclassOnline = "Online"; 		// 一级等级
		String filecodeBroadcast = "BRDXML";
		String rootPortalColumnDefcatseq = "";
		String localfile = "";					// 播发单本地临时路径
		String broadcastXmlFullPath = "";		// 播发单路径
		String errorDetail = "";
		
		
		long days = 7L;
		try
		{
			days = Long.valueOf(scheduleDays);
		}
		catch(Exception e)
		{
			cmsLog.warn("预告天数类型转换异常：" + e.getMessage());
		}
		long fileStyleCode = 0L;		// 1 - 节目预告 ; 2 - 播发单
		
		// 播发单文件名
		Date nowDate = new Date();
		String broadcastXmlName = "broadcast_";
		broadcastXmlName += date;
		broadcastXmlName += "_";
		broadcastXmlName += fileopr.convertDateToString(
				nowDate,
				"yyyyMMddHHmmss"
				);
		broadcastXmlName += ".xml";
		
		String scheduledate = fileopr.convertDateToScheduleDate(date);
		Date ddate = fileopr.convertStringToDate(date, "yyyy-MM-dd");
		
		/**
		 * 查询播发单的路径
		 */
		List<String> filepaths = getBroadcastXmlFilepath(
				filecodeBroadcast, 
				stclassOnline, 
				date, 
				broadcastXmlTemp, 
				serverOS
				);
		if(filepaths != null && filepaths.size() >= 2)
		{
			broadcastXmlFullPath = filepaths.get(0);
			localfile = filepaths.get(1);
			
			localfile = localfile + broadcastXmlName;
			broadcastXmlFullPath = broadcastXmlFullPath + broadcastXmlName;
			
			cmsLog.info("播发单本地临时路径 - " + localfile);
			cmsLog.info("播发单目标路径 - " + broadcastXmlFullPath);
			
			/**
			 * 判断当前活动是否允许生成播发单
			 */
			cmsLog.info("判断编单总表活动是否是“播发”(FU00000086)...");
			cmsLog.info("编单总表日期：" + scheduledate);
			ProgListMang progListMang = (ProgListMang)progListMangManager.getById(scheduledate);
			
			/**
			 * 判断当前编单中是否有文件未到位的节目包，如果有不能生成播发单
			 */
			

			// 判断编单总表活动是否是“播发”(FU00000086)
			if ("FU00000086".equalsIgnoreCase(progListMang.getIdAct()))
			{
				cmsLog.info("编单总表活动是“播发”(FU00000086)，允许生成播发单...");
				cmsLog.info("获得播发单xml文件生成的目标路径...");

				/**
				 * 查询品牌
				 * 查询品牌下所有栏目
				 * 查询栏目下所有节目包
				 * 查询节目包下所有文件
				 */
				cmsLog.info("查询所有品牌...");
				List cmsSites = cmsSiteManager.findAll();
				if(cmsSites != null && cmsSites.size() > 0)
				{
					cmsLog.info("共有" + cmsSites.size() + "个品牌。");
					
					Document document = DocumentHelper.createDocument();
					Element rootelement = document.addElement("PushVod");		// 根节点
					rootelement.addComment("播发单 " + date);					// 注释
					rootelement.addAttribute("ID", "");			// 属性，pushvod id = proglistfileid
					
					for (int i = 0; i < cmsSites.size(); i++)
					{
						CmsSite cmsSite = (CmsSite) cmsSites.get(i);
						
						cmsLog.info("处理第" + (i + 1) + "个品牌...");
						cmsLog.info("品牌code：" + cmsSite.getSiteCode());
						cmsLog.info("品牌名称：" + cmsSite.getSitename());
						cmsLog.info("得到品牌下所有有效的、叶子节点栏目...");
						List pcs = portalColumnManager.getPortalColumnsBySitecodeValidtagIsleaf(
								cmsSite.getSiteCode(), 
								(long)0, 		// validflag
								"Y"				// isleaf
								);
						cmsLog.info("品牌下共有" + pcs.size() + "个有效的、叶子节点栏目...");
						
						Element siteElement = rootelement.addElement("Site");		// 品牌
						siteElement.addAttribute("BroadcastTime", plandate);		// 播发时间
						siteElement.addAttribute("SiteCode", cmsSite.getSiteCode());// 品牌code
						siteElement.addAttribute("SiteName", cmsSite.getSitename());// 品牌名称
						siteElement.addAttribute("ScheduleDate", scheduledate);		// 栏目单日期

						for(long d = 0; d < days; d++)
						{
							Date dscheduledate = new Date();
							dscheduledate.setTime(ddate.getTime() + 24*60*60*1000*d);
							String strscheduledate = fileopr.convertDateToString(dscheduledate, "yyyyMMdd000000");
							
							if(d == 0)
							{
								cmsLog.info("处理当日编单...");
								cmsLog.info("编单日期：" + strscheduledate);
								fileStyleCode = 2L;
								
							}
							else
							{
								cmsLog.info("处理节目预告编单...");
								cmsLog.info("编单日期：" + strscheduledate);
								fileStyleCode = 1L;
							}
							
							// 编单节点
							Element proglistElement = siteElement.addElement("Proglist");
							proglistElement.addAttribute("ScheduleDate", strscheduledate);
							for(int j = 0; j < pcs.size(); j++)
							{
								PortalColumn portalColumn = (PortalColumn) pcs.get(j);
								
								cmsLog.info("处理第" + (i+1) + "个栏目...");
								cmsLog.info("栏目code：" + portalColumn.getDefcatcode());
								cmsLog.info("栏目名称：" + portalColumn.getColumnname());
								cmsLog.info("栏目id：" + portalColumn.getColumnclassid());
								
								Element columnElement = proglistElement.addElement("Column");
								columnElement.addAttribute("DefCatCode", portalColumn.getDefcatcode());
								columnElement.addAttribute("Name", portalColumn.getColumnname());
								columnElement.addAttribute("Type", portalColumn.getArchivedays().toString());	// 0 - 视频类；1 - 富媒体类
							
								
								cmsLog.info("编单日期：" + strscheduledate);
								cmsLog.info("查询栏目下的栏目单详细(ProgListDetail)...");
								List plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
										strscheduledate, 
										portalColumn.getColumnclassid()
										);
								cmsLog.info("栏目单详细(ProgListDetail)记录数量：" + plds.size());
								for(int k = 0; k < plds.size(); k++)
								{
									ProgListDetail pld = (ProgListDetail)plds.get(k);
									// 获得节目包
									ProgPackage pp = (ProgPackage)progPackageManager.getById(pld.getProductid());
									
									List<ProgramFiles> pfs = new ArrayList<ProgramFiles>();	// 需要加入播发单的节目包文件
									String contentid = "";		// 目前为主文件的progfileid
									
									cmsLog.info("处理第" + (k+1) + "个栏目单详细...");
									cmsLog.info("节目包id：" + pld.getProductid());
									cmsLog.info("节目包名称：" + pld.getProductname());
									cmsLog.info("产品信息id：" + pld.getProductname());
									cmsLog.info("文件样式应用代码：" + fileStyleCode);
									cmsLog.info("节目包的样式ID：" + pp.getStyleid());
									
									if(d != 0 && !"Y".equalsIgnoreCase(pld.getScheduleTag()))
									{
										/**
										 * 如果是节目预告（不是当日节目）
										 * 并且，栏目单详细不加入节目预告
										 * 则，不处理，跳过
										 */
										cmsLog.info("栏目单详细记录属于节目预告编单，且不加入节目预告，不处理，跳过...");
										continue;
									}
									
									/** 
									 * 获得节目包下文件列表（根据文件样式）
									 * 编单当日的节目包文件，不适用于文件样式，取节目包所有文件
									 * 编单日后的编单，根据文件样式，取节目包的文件用于生成播发单
									 */ 
									
									/**
									 * 根据应用（生成节目预告），
									 * 查询文件样式表（FileStyle），
									 * 得到允许加入播发单预告部分的节目包文件的filetype列表
									 * 另外，需要判断栏目单详细(proglistdetail)的节目预告标志(scheduletag)
									 */
									cmsLog.info("查询文件样式...");
									List<FileStyle> fileStyles = fileStyleManager.queryFileStylesByStyleCodeAndStyleID(fileStyleCode, pp.getStyleid());

									
									if(fileStyles == null || fileStyles.size() <= 0)
									{
										cmsLog.info("节目包的文件样式为空，不处理，跳过...");
										
									}
									else
									{
										cmsLog.info("节目包的文件样式size：" + fileStyles.size());
										for(int m = 0; m < fileStyles.size(); m++)
										{
											FileStyle fileStyle = fileStyles.get(m);
											cmsLog.info("filetypeid：" + fileStyle.getFileTypeId());
											List<ProgramFiles> programFiles = packageFilesManager.getProgramFilesesByProductidFiletype(
													pp.getProductid(), fileStyle.getFileTypeId());
											if(programFiles != null && programFiles.size() > 0)
											{
												ProgramFiles pf = programFiles.get(0);
												pfs.add(pf);
												if(pf.getProgrank() == (long)1)
												{
													contentid = pf.getProgfileid();
												}
											}
										}
										
										/**
										 * 如果主文件的文件id（contentid）未找到，需要先找到
										 */
										
										if(pfs.size() > 0)
										{
											Element progpackageElement = columnElement.addElement("ProgPackage");
											
											progpackageElement.addAttribute("PPID", pp.getProductid());
											progpackageElement.addAttribute("STBTITLE", pp.getProductname());
											progpackageElement.addAttribute("KEYIDS", "产品信息列表...是否需要？");
											
											for(int m = 0; m < pfs.size(); m++)
											{
												ProgramFiles pf = pfs.get(m);
												
												Element fileElement = progpackageElement.addElement("FILE");
												fileElement.addAttribute("PROGFILEID", pf.getProgfileid());
												fileElement.addAttribute("FILENAME", pf.getFilename());
												fileElement.addAttribute("CONTENTID", contentid);		// CONTENTID（全部为主文件值）
												fileElement.addAttribute("PROGRANK", pf.getProgrank().toString());		// 主体文件标识,1-主文件;0-非主文件
												fileElement.addAttribute("FILECODE", pf.getFilecode());
												fileElement.addAttribute("FILETYPEID", pf.getFiletypeid());
												
												/**
												 * 以下如何填写？？
												 */
												fileElement.addAttribute("SRC", "");			// 源文件路径
												fileElement.addAttribute("UPDATEFLAG", "");		// 更新标识
												fileElement.addAttribute("UPDATETIME", "");		// 更新版本
											}
											Element pushConditionElement = progpackageElement.addElement("PushCondition");
											pushConditionElement.setText("投递策略代码...");
											
										}
										else
										{
											/**
											 * 符合文件样式的节目包文件没有查询到，节目包不加入播发单
											 */
										}
									}
								}
							}
						}
						
						/**
						 * 节目预告js文件名字，
						 * CmsSite表
						 */
						// 节目预告js节点
						Element jsElement = siteElement.addElement("Js");
						Element ygjsElement = jsElement.addElement("YgJs");
						ygjsElement.addAttribute("ScheduleDate", scheduledate);
						ygjsElement.addAttribute("FILENAME", cmsSite.getEpgpath());
						ygjsElement.addAttribute("SRC", "");
						
						// 删除节目包节点
						Element deleteElement = siteElement.addElement("Delete");

					}
					
					
					/**
					 * 1 - 把xml字符串增加到ProgListfile记录，获取新增记录的id，设置新增记录为无效状态(state1 = 1)
					 * 2 - 把新增id设置为xml的pushvod id
					 * 3 - 生成播发单的xml文件在本地
					 * 4 - 复制本地播发单xml文件到网络路径（一级库）
					 * 5 - 设置新增ProgListfile记录为有效状态(state1 = 0)
					 */
					String strxml = null;

					try
					{
						cmsLog.info("生成播发单xml字符串...");
	
						// 输出格式化器
						OutputFormat format = OutputFormat.createPrettyPrint();
						
						// 设置编码 - gb2312 / GBK / 
						format.setEncoding("GBK");
						
						// xml输出器
						StringWriter stringwriter = new StringWriter();
						XMLWriter stringout = new XMLWriter(stringwriter, format);
						stringout.write(document);
						stringout.flush();
						strxml = stringwriter.toString();
						
//							XMLWriter fileout = new XMLWriter(new FileWriter(new File(localfile)), format);
//							fileout.write(document);
//							fileout.close();
					}
					catch(IOException e)
					{
						String str = "生成播发单xml字符串异常：" + e.getMessage() + "\r\n";
						errorcount++;
						errordetails += str;
						cmsLog.error(str);
					}
					

					cmsLog.info("生成播发单字符串成功，准备生成播发单文件...");
					cmsLog.info("播发单文件目标路径 - " + broadcastXmlFullPath);

					/**
					 * 增加300G预装内容
					 * 暂时不使用
					 * strxml = XMLPatch.patch(strxml);
					 */ 

					cmsLog.info("生成播发单xml字符串成功，准备新增发布文件记录...");

					ProgListFile progListFile = new ProgListFile();

					progListFile.setScheduledate(scheduledate);
					progListFile.setFilename(broadcastXmlName);
					progListFile.setFiletype((long) 9);
					progListFile.setDate1(nowDate);
					progListFile.setDate4(fileopr.convertStringToDate(plandate, "yyyy-MM-dd HH:mm:ss"));
					progListFile.setState1((long) 1);		// 设置为无效
					progListFile.setState2((long) 0);
					progListFile.setInputmanid(operatorId);
					progListFile.setColumnxml(strxml);
					progListFile.setInputtime(nowDate);

					// 播发单下发完成，保存发布文件表记录progListfile，并且发送活动 86-->87
					// 播放单生成成功，发送
					CmsResultDto crd = cmsTransactionManager.saveProgListFileAndUpdateProgListMangDetail(
									progListFileManager,
									progListMangManager,
									progListMangDetailManager, 
									bpmcManager,
									flowActivityOrderManager,
									portalColumnManager,
									progListDetailManager, 
									progListFile,
									date, 
									"", 
									operatorId
									);
					if(crd.getResultCode() == (long)0)
					{
						Long idLong = Long.parseLong(progListFile.getColumnfileid());
						String strnewid = idLong.toString();
						cmsLog.info("更新数据库成功，获得ProgListFile ID：" + strnewid);
						
						Attribute idattr = rootelement.attribute("ID");
						idattr.setValue(strnewid);
					}
					else
					{
						String str = "更新数据库失败，" + crd.getErrorDetail() + "\r\n";
						errorcount++;
						errordetails += str;
						cmsLog.warn(str);
					}
					
					strxml = null;
					try
					{
						cmsLog.info("在本地磁盘生成播发单...");
						cmsLog.info("文件路径 - " + localfile);
						
						// 输出格式化器
						OutputFormat format = OutputFormat.createPrettyPrint();
						
						// 设置编码 - gb2312 / GBK / 
						format.setEncoding("GBK");
						
						// xml输出器
						StringWriter stringwriter = new StringWriter();
						XMLWriter stringout = new XMLWriter(stringwriter, format);
						stringout.write(document);
						stringout.flush();
						strxml = stringwriter.toString();
						
						XMLWriter fileout = new XMLWriter(new FileWriter(new File(localfile)), format);
						fileout.write(document);
						fileout.close();
					}
					catch(IOException e)
					{
						String str = "在本地磁盘生成播发单异常：" + e.getMessage() + "\r\n";
						errorcount++;
						errordetails += str;
						cmsLog.error(str);
					}
					
					try
					{
						fileopr.checkSmbDir(broadcastXmlFullPath);
						if(fileopr.checkFileExist(localfile))
						{
							cmsLog.info("准备复制播发单...");
							int ret = fileopr.copyFileFromLocalToSmb(localfile, broadcastXmlFullPath);
							if(ret == 0)
							{
								cmsLog.info("复制播发单成功。");
							}
							else
							{
								String str = "复制播发单失败！\r\n";
								errorcount++;
								errordetails += str;
								cmsLog.warn(str);
							}
						}
						else
						{
							String str = "本地播发单不存在..." + localfile + "\r\n";
							errorcount++;
							errordetails += str;
							cmsLog.warn(str);
						}
					}
					catch(Exception e)
					{
						String str = "复制本地磁盘播发单至一级库异常：" + e.getMessage() + "\r\n";
						errorcount++;
						errordetails += str;
						cmsLog.error(str);
					}
					
					if(errorcount <= 0)
					{
						progListFile.setColumnxml(strxml);
						progListFile.setState1(0l);
						progListFileManager.update(progListFile);
						cmsLog.info("已经更新xml。PushVod ID:" + progListFile.getColumnfileid());
					}
					else
					{
						String str = "错误计数大于0，生成播发单失败。";
						cmsLog.warn(str);
						cmsResultDto.setResultCode((long)1);
						cmsResultDto.setErrorMessage("生成播发单失败");
						cmsResultDto.setErrorDetail(errordetails);
					}
				}
				else
				{
					String str = "品牌数量为0，不生成播发单。";
					cmsLog.warn(str);
					cmsResultDto.setErrorMessage(str);
				}
			}
			else
			{
				String str = "编单总表活动不是“播发”(FU00000086)，不生成播发单。";
				cmsLog.warn(str);
				cmsLog.info("编单总表活动：" + progListMang.getIdAct());
				cmsResultDto.setErrorMessage(str);
				cmsResultDto.setResultCode((long)1);
			}
		}
		else
		{
			cmsLog.warn("查询播发单路径失败，不生成播发单。");
		}

		cmsLog.debug("Cms -> BroadcastModuleManagerImpl -> generateBroadcastXml returns.");
		return cmsResultDto;
	}

	//=============================================================================================
	
	
	//------------------ SET 方法 -------------------------------------------------------------
	
	public void setCmsTransactionManager(
			ICmsTransactionManager cmsTransactionManager) {
		this.cmsTransactionManager = cmsTransactionManager;
	}

	public void setCmsSiteManager(ICmsSiteManager cmsSiteManager) {
		this.cmsSiteManager = cmsSiteManager;
	}

	public void setPortalColumnManager(IPortalColumnManager portalColumnManager) {
		this.portalColumnManager = portalColumnManager;
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

	public void setPackageFilesManager(IPackageFilesManager packageFilesManager) {
		this.packageFilesManager = packageFilesManager;
	}

	public void setProgListMangManager(IProgListMangManager progListMangManager) {
		this.progListMangManager = progListMangManager;
	}

	public void setProgListFileManager(IProgListFileManager progListFileManager) {
		this.progListFileManager = progListFileManager;
	}

	public void setProgListMangDetailManager(
			IProgListMangDetailManager progListMangDetailManager) {
		this.progListMangDetailManager = progListMangDetailManager;
	}

	public void setBpmcManager(IBpmcManager bpmcManager) {
		this.bpmcManager = bpmcManager;
	}

	public void setFlowActivityOrderManager(
			IFlowActivityOrderManager flowActivityOrderManager) {
		this.flowActivityOrderManager = flowActivityOrderManager;
	}

	public void setProductinfoManager(IProductInfoManager productinfoManager) {
		this.productinfoManager = productinfoManager;
	}

	public void setProgramFilesManager(IProgramFilesManager programFilesManager) {
		this.programFilesManager = programFilesManager;
	}

	public void setPrograminfoManager(IProgramInfoManager programinfoManager) {
		this.programinfoManager = programinfoManager;
	}
	//=============================================================================================

	public void setProgListDeleteDetailManager(
			IProgListDeleteDetailManager progListDeleteDetailManager) {
		this.progListDeleteDetailManager = progListDeleteDetailManager;
	}

	public static void setFileopr(FileOperationImpl fileopr) {
		BroadcastModuleManagerImpl.fileopr = fileopr;
	}

	public static void setCc(CmsConfig cc) {
		BroadcastModuleManagerImpl.cc = cc;
	}
}
