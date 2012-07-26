package com.soaplat.cmsmgr.managerimpl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;

import jcifs.smb.SmbFile;

import org.apache.log4j.Logger;


import com.sbl.cms.patch.ReadFile;
import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.cmsmgr.bean.FileStyle;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.daoiface.IPackageFilesDAO;
import com.soaplat.cmsmgr.daoiface.IPortalColumnDAO;
import com.soaplat.cmsmgr.daoiface.IProgListMangDAO;
import com.soaplat.cmsmgr.manageriface.IBroadcastManager;
import com.soaplat.cmsmgr.manageriface.ICmsSiteManager;
import com.soaplat.cmsmgr.manageriface.IFileStyleManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;

/**
 * 
 * @author Andy
 * @version cms 1.21
 */


public class BroadcastManagerImpl implements IBroadcastManager {
	
	IBaseDAO baseDAO;
	IGetPK getcmspk;
	private IProgListMangDAO progListMangDAO;
	private IPackageFilesDAO packageFilesDAO;
	private IPortalColumnDAO portalColumnDAO;
	
	private static final Logger cmsLog = Logger.getLogger("Cms");
	private static FileOperationImpl fileopr = new FileOperationImpl();
	public static CmsConfig cc = new CmsConfig();
	
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}
	
	public void setPackageFilesDAO(IPackageFilesDAO packageFilesDAO) {
		this.packageFilesDAO = packageFilesDAO;
	}

	public void setProgListMangDAO(IProgListMangDAO progListMangDAO) {
		this.progListMangDAO = progListMangDAO;
	}

	public void setPortalColumnDAO(IPortalColumnDAO portalColumnDAO) {
		this.portalColumnDAO = portalColumnDAO;
	}
	
	/**
	 * 
	 * @param date
	 * @param operatorId
	 * @param plandate
	 * 
	 */
	public int generateBroadcastxml(
			ICmsSiteManager cmsSiteManager,
			IPortalColumnManager portalColumnManager,
			IProgListDetailManager progListDetailManager,
			IProgPackageManager progPackageManager,
			IFileStyleManager fileStyleManager,
			IPackageFilesManager packageFilesManager,
			String date, 			// yyyy-MM-dd
			String operatorId, 		// 操作人员id
			String plandate 		// 计划播发日期，格式："yyyy-MM-dd HH:mm:ss"
			)
	{
		// cms 1.21 版本，生成播发单，
		// 由于媒资不能上线，节目来源仍然是cms导入，contentid用节目包主文件的文件id来代替。
		// 1 - 查询date的栏目单详细记录
		// 2 - 根据栏目分类节目包
		// 播发单下发完成，保存发布文件表记录progListfile，并且发送活动 86-->87 播放单生成成功，发送
		
		cmsLog.debug("Cms -> BroadcastManagerImpl -> generateBroadcastXml...");
		
		
		// 配置文件库中
		String serverOS = cc.getPropertyByName("ServerOS");		// 服务器操作系统
		String stclassOnline = "Online"; 		// 一级等级
		String filecodeBroadcast = "BRDXML";
		String rootPortalColumnDefcatseq = "";
		String localfile = "";
		
		if("LINUX".equalsIgnoreCase(serverOS.toUpperCase()))
		{
			localfile = "/broadcast/";
		}
		else if("WINDOWS".equalsIgnoreCase(serverOS.toUpperCase()))
		{
			localfile = "C:/broadcast/";
		}
		else
		{
			if(fileopr.checkFileExist("/"))
			{
				localfile = "/broadcast/";
			}
			else if(fileopr.checkFileExist("C:/"))
			{
				localfile = "C:/broadcast/";
			}
		}
		fileopr.checkLocalDir(localfile);
		
		String errorDetail = "";
		String broadcastXmlFullPath = "";
		long days = 7L;
		long fileStyleCode = 1L;		// 1 - 节目预告
		
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
		
		// 本地生成播发单文件路径和文件名
		localfile = localfile + date + "/" + broadcastXmlName;
		
		// 一级库生成播发单文件路径和文件名

		
		String scheduledate = fileopr.convertDateToScheduleDate(date);
		Date ddate = fileopr.convertStringToDate(date, "yyyy-MM-dd");
		
		cmsLog.debug("判断编单总表活动是否是“播发”(FU00000086)...");
		cmsLog.debug("编单总表日期：" + scheduledate);
		ProgListMang progListMang = (ProgListMang) progListMangDAO.getById(scheduledate);
		
		// 判断编单总表活动是否是“播发”(FU00000086)
		if ("FU00000086".equalsIgnoreCase(progListMang.getIdAct()))
		{
			cmsLog.debug("编单总表活动是“播发”(FU00000086)，允许生成播发单...");
			cmsLog.debug("获得播发单xml文件生成的目标路径...");
			List dest = packageFilesDAO.getDestPathByFilecodeStclasscode(
					filecodeBroadcast, stclassOnline);
			if (dest != null && dest.size() >= 2)
			{
				String destpath = (String) dest.get(0);

				broadcastXmlFullPath = destpath;
				broadcastXmlFullPath = broadcastXmlFullPath.replace('\\', '/');
				broadcastXmlFullPath = fileopr.checkPathFormatRear(
						broadcastXmlFullPath, '/');
				broadcastXmlFullPath += date;
				broadcastXmlFullPath += "/";
				broadcastXmlFullPath += broadcastXmlName;
				

				/**
				 * 查询品牌
				 * 查询品牌下所有栏目
				 * 查询栏目下所有节目包
				 * 查询节目包下所有文件
				 */
				cmsLog.debug("查询所有品牌...");
				List cmsSites = cmsSiteManager.findAll();
				if(cmsSites != null && cmsSites.size() > 0)
				{
					cmsLog.debug("共有" + cmsSites.size() + "个品牌。");
					
					Document document = DocumentHelper.createDocument();
					Element rootelement = document.addElement("PushVod");		// 根节点
					rootelement.addComment("播发单 " + date);					// 注释
					rootelement.addAttribute("ID", "");			// 属性，pushvod id = proglistfileid
					
		
			
					for (int i = 0; i < cmsSites.size(); i++)
					{
						CmsSite cmsSite = (CmsSite) cmsSites.get(i);
						
						cmsLog.debug("处理第" + (i + 1) + "个品牌...");
						cmsLog.debug("品牌code：" + cmsSite.getSiteCode());
						cmsLog.debug("品牌名称：" + cmsSite.getSitename());
						cmsLog.debug("得到品牌下所有有效的、叶子节点栏目...");
						List pcs = portalColumnManager.getPortalColumnsBySitecodeValidtagIsleaf(
								cmsSite.getSiteCode(), 
								(long)0, 		// validflag
								"Y"				// isleaf
								);
						cmsLog.debug("品牌下共有" + pcs.size() + "个有效的、叶子节点栏目...");
						
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
								cmsLog.debug("处理当日编单...");
								cmsLog.debug("编单日期：" + strscheduledate);
								
							}
							else
							{
								cmsLog.debug("处理节目预告编单...");
								cmsLog.debug("编单日期：" + strscheduledate);
							}
							
							// 编单节点
							Element proglistElement = siteElement.addElement("Proglist");
							proglistElement.addAttribute("ScheduleDate", strscheduledate);
							for(int j = 0; j < pcs.size(); j++)
							{
								PortalColumn portalColumn = (PortalColumn) pcs.get(j);
								
								cmsLog.debug("处理第" + (i+1) + "个栏目...");
								cmsLog.debug("栏目code：" + portalColumn.getDefcatcode());
								cmsLog.debug("栏目名称：" + portalColumn.getColumnname());
								cmsLog.debug("栏目id：" + portalColumn.getColumnclassid());
								
								Element columnElement = proglistElement.addElement("Column");
								columnElement.addAttribute("DefCatCode", portalColumn.getDefcatcode());
								columnElement.addAttribute("Name", portalColumn.getColumnname());
								columnElement.addAttribute("Type", portalColumn.getArchivedays().toString());	// 0 - 视频类；1 - 富媒体类
							
								
								cmsLog.debug("编单日期：" + strscheduledate);
								cmsLog.debug("查询栏目下的栏目单详细(ProgListDetail)...");
								List plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
										strscheduledate, 
										portalColumn.getColumnclassid()
										);
								cmsLog.debug("栏目单详细(ProgListDetail)记录数量：" + plds.size());
								for(int k = 0; k < plds.size(); k++)
								{
									ProgListDetail pld = (ProgListDetail)plds.get(k);
									// 获得节目包
									ProgPackage pp = (ProgPackage)progPackageManager.getById(pld.getProductid());
									
									List<ProgramFiles> pfs = new ArrayList<ProgramFiles>();	// 需要加入播发单的节目包文件
									String contentid = "";		// 目前为主文件的progfileid
									
									cmsLog.debug("节目包id：" + pld.getProductid());
									cmsLog.debug("节目包名称：" + pld.getProductname());
									cmsLog.debug("产品信息id：" + pld.getProductname());
									cmsLog.debug("文件样式应用代码：" + fileStyleCode);
									cmsLog.debug("节目包的样式ID：" + pp.getStyleid());
									
									
									/** 
									 * 获得节目包下文件列表（根据文件样式）
									 * 编单当日的节目包文件，不适用于文件样式，取节目包所有文件
									 * 编单日后的编单，根据文件样式，取节目包的文件用于生成播发单
									 */ 
									
									/**
									 * 根据应用（生成节目预告），
									 * 查询文件样式表（FileStyle），
									 * 得到允许加入播发单预告部分的节目包文件的filetype列表
									 */
									cmsLog.debug("查询文件样式...");
									List<FileStyle> fileStyles = fileStyleManager.queryFileStylesByStyleCodeAndStyleID(fileStyleCode, pp.getStyleid());
									
									
									
									if(fileStyles == null || fileStyles.size() <= 0)
									{
										cmsLog.debug("节目包的文件样式为空，不处理，跳过...");
										
									}
									else
									{
										for(int m = 0; m < fileStyles.size(); m++)
										{
											FileStyle fileStyle = fileStyles.get(m);
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
						
						

						
						
						
						
//						//------------------------------------------------------------
//						
//						cmsLog.debug("查询品牌下的所有的栏目...");
//						List pcs = portalColumnManager.findByProperty("siteCode", cmsSite.getSiteCode());
//						cmsLog.debug("共有" + pcs.size() + "个栏目。");
//						
//						for (int j = 0; j < pcs.size(); j++)
//						{
//							PortalColumn portalColumn = (PortalColumn) pcs.get(i);
//							
//							cmsLog.debug("处理第" + (i + 1) + "个栏目...");
//							cmsLog.debug("栏目code：" + portalColumn.getDefcatcode());
//							cmsLog.debug("栏目名称：" + portalColumn.getColumnname());
//							
//							cmsLog.debug("判断栏目是否有效的叶子节点...");
//							if(portalColumn.getValidFlag() != null 
//									&& portalColumn.getValidFlag() == (long)0
//									&& "Y".equalsIgnoreCase(portalColumn.getIsleaf())
//									)
//							{
//								cmsLog.debug("栏目设置为有效的叶子节点...");
//								cmsLog.debug("查询栏目下的栏目单详细(ProgListDetail)...");
//								cmsLog.debug("栏目名称：" + portalColumn.getColumnname());
//								cmsLog.debug("栏目id：" + portalColumn.getColumnclassid());
//								cmsLog.debug("栏目code：" + portalColumn.getDefcatcode());
//								cmsLog.debug("编单日期：" + scheduledate);
//								List plds = progListDetailManager.getProgListDetailsByScheduledateColumnclassid(
//										scheduledate, 
//										portalColumn.getColumnclassid()
//										);
//								
//								cmsLog.debug("栏目下共有" + plds.size() + "个栏目单详细。");
//								for(int k = 0; k < plds.size(); k++)
//								{
//									ProgListDetail pld = (ProgListDetail)plds.get(k);
//									cmsLog.debug("处理第" + (k + 1) + "个栏目单详细...");
//									cmsLog.debug("节目包id：" + pld.getProductid());
//									cmsLog.debug("节目包名称：" + pld.getProductname());
//									
//									
//								}
//							}
//							else
//							{
//								cmsLog.debug("栏目设置为无效或非叶子节点，不加入播发单，跳过...");
//							}
//							
//						}
					}
					
					
					/**
					 * 先，生成播发单的xml文件在本地
					 * 后，复制本地播发单xml文件到网络路径（一级库）
					 */
					try
					{
						cmsLog.debug("在本地磁盘生成播发单...");
						cmsLog.debug("文件路径 - " + localfile);
						OutputFormat format = OutputFormat.createPrettyPrint();
						format.setEncoding("GBK");
						XMLWriter output = new XMLWriter(new FileWriter(new File(localfile)),format);
						output.write(document);
						output.close();
					}
					catch(IOException e)
					{
						cmsLog.debug("在本地磁盘生成播发单异常：" + e.getMessage());
					}
					
					try
					{
						if(fileopr.checkFileExist(localfile))
						{
							cmsLog.debug("准备复制播发单...");
							int ret = fileopr.copyFileFromLocalToSmb(localfile, broadcastXmlFullPath);
							if(ret == 0)
							{
								cmsLog.info("复制播发单成功。");
							}
							else
							{
								cmsLog.warn("复制播发单失败！");
							}
						}
						else
						{
							cmsLog.warn("本地播发单不存在..." + localfile);
						}
					}
					catch(Exception e)
					{
						cmsLog.debug("复制本地磁盘播发单至一级库异常：" + e.getMessage());
					}
				}
				else
				{
					cmsLog.debug("品牌数量为0。");
				}
				
				
				cmsLog.debug("查询所有允许发布的栏目...");
				List portalColumns = portalColumnDAO.getAllPublishPortalColumnsByDefcatseq(
						rootPortalColumnDefcatseq
						);
				if (portalColumns.size() <= 0)
				{
					String str = "未找到需要生成Portal的栏目，返回失败。";
					cmsLog.warn(str);
					errorDetail += str;
				}
				else
				{
					cmsLog.debug("共有" + portalColumns.size() + "个栏目允许发布。");
					for (int i = 0; i < portalColumns.size(); i++)
					{
						PortalColumn portalColumn = (PortalColumn) portalColumns.get(i);
						
						cmsLog.debug("处理第" + (i + 1) + "个栏目...");
						cmsLog.debug("栏目code：" + portalColumn.getDefcatcode());
						cmsLog.debug("栏目名称：" + portalColumn.getColumnname());
						
						String xmlType = "0";			// 0 - 视频类型栏目; 1 - 富媒体类型栏目

						cmsLog.debug("栏目是视频类型，查询栏目下所有栏目单详细记录...");
					}
				}
			}
		}
		
		cmsLog.debug("Cms -> BroadcastManagerImpl -> generateBroadcastXml returns.");
		return 0;
	}
	
	public static void main(String[] args) {
		//System.out.println(patch(ReadFile.read("d:/dy.js"),"asdf"));
	}
}
