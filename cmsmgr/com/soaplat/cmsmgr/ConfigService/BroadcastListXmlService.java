package com.soaplat.cmsmgr.ConfigService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.sbl.cms.patch.XMLPatch;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageClass;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.cmsmgr.bean.PackageFiles;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.manageriface.IBpmcManager;
import com.soaplat.cmsmgr.manageriface.ICmsTransactionManager;
import com.soaplat.cmsmgr.manageriface.IFlowActivityOrderManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListFileManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class BroadcastListXmlService implements IBroadcastListXmlService {

	private class BroadcastXml {
		String videoProgListId = "";
		String videoBroadcastTime = "";
		String webProgListId = "";
		String webBroadcastTime = "";

		// 说明：
		// videoColumns.size() = 5
		// (String)videoColumns.get(0) : DEFCATCODE
		// (String)videoColumns.get(1) : name
		// (List)videoColumns.get(2) : TodayProg
		// TodayProg.size() = ?
		// (List)TodayProg.get(?) : ProgPackage
		// ProgPackage.size() = 3
		// (String)ProgPackage.get(0) : PPID
		// (String)ProgPackage.get(1) : STBTITLE
		// (List)ProgPackage.get(2) : FILE
		// FILE.size() = ?
		// (List)FILE.get(?) : FILE
		// FILE.size() = 6
		// (String)FILE.get(0) : SRC
		// (String)FILE.get(1) : PROGRANK
		// (String)FILE.get(2) : FILETYPEID
		// (String)FILE.get(3) : FILECODE
		// (String)FILE.get(4) : PROGFILEID
		// (String)FILE.get(5) : FILENAME
		// (List)videoColumns.get(3) : ReserveProg
		// (List)videoColumns.get(4) : ColumnJs
		@SuppressWarnings("unchecked")
		List videoColumns = new ArrayList();

		// 说明：
		// webColumns.size() = 5
		// (String)webColumns.get(0) : DEFCATCODE
		// (String)webColumns.get(1) : name
		// (List)webColumns.get(2) : TodayProg
		// TodayProg.size() = ?
		// (List)TodayProg.get(?) : ProgPackage
		// ProgPackage.size() = 3
		// (String)ProgPackage.get(0) : PPID
		// (String)ProgPackage.get(1) : STBTITLE
		// (List)ProgPackage.get(2) : FILE
		// FILE.size() = ?
		// (List)FILE.get(?) : FILE
		// FILE.size() = 6
		// (String)FILE.get(0) : SRC
		// (String)FILE.get(1) : PROGRANK
		// (String)FILE.get(2) : FILETYPEID
		// (String)FILE.get(3) : FILECODE
		// (String)FILE.get(4) : PROGFILEID
		// (String)FILE.get(5) : FILENAME
		// (List)webColumns.get(3) : ReserveProg
		// (List)webColumns.get(4) : ColumnJs
		@SuppressWarnings("unchecked")
		List webColumns = new ArrayList();

		public String getVideoProgListId() {
			return videoProgListId;
		}

		public void setVideoProgListId(String videoProgListId) {
			this.videoProgListId = videoProgListId;
		}

		public String getVideoBroadcastTime() {
			return videoBroadcastTime;
		}

		public void setVideoBroadcastTime(String videoBroadcastTime) {
			this.videoBroadcastTime = videoBroadcastTime;
		}

		public String getWebProgListId() {
			return webProgListId;
		}

		public void setWebProgListId(String webProgListId) {
			this.webProgListId = webProgListId;
		}

		public String getWebBroadcastTime() {
			return webBroadcastTime;
		}

		public void setWebBroadcastTime(String webBroadcastTime) {
			this.webBroadcastTime = webBroadcastTime;
		}

		public List getVideoColumns() {
			return videoColumns;
		}

		public void setVideoColumns(List videoColumns) {
			this.videoColumns = videoColumns;
		}

		public List getWebColumns() {
			return webColumns;
		}

		public void setWebColumns(List webColumns) {
			this.webColumns = webColumns;
		}
	}

	// 构造注入
	private FileOperationImpl fileopr = null;
	private IProgListDetailManager progListDetailManager = null;
	private IPackageFilesManager packageFilesManager = null;
	private IProgListFileManager progListFileManager = null;
	private ICmsTransactionManager cmsTransactionManager = null;
	private IPortalColumnManager portalColumnManager = null;
	private IFlowActivityOrderManager flowActivityOrderManager = null;
	private IBpmcManager bpmcManager = null;
	private IProgListMangDetailManager progListMangDetailManager = null;
	private IProgListMangManager progListMangManager = null;
	private IProgramFilesManager programFilesManager = null;

	public static final Logger cmsLog = Logger.getLogger("Cms");

	public BroadcastListXmlService() {
		fileopr = new FileOperationImpl();

		progListDetailManager = (IProgListDetailManager) ApplicationContextHolder.webApplicationContext
				.getBean("progListDetailManager");
		packageFilesManager = (IPackageFilesManager) ApplicationContextHolder.webApplicationContext
				.getBean("packageFilesManager");
		progListFileManager = (IProgListFileManager) ApplicationContextHolder.webApplicationContext
				.getBean("progListFileManager");
		cmsTransactionManager = (ICmsTransactionManager) ApplicationContextHolder.webApplicationContext
				.getBean("cmsTransactionManager");
		portalColumnManager = (IPortalColumnManager) ApplicationContextHolder.webApplicationContext
				.getBean("portalColumnManager");
		flowActivityOrderManager = (IFlowActivityOrderManager) ApplicationContextHolder.webApplicationContext
				.getBean("flowActivityOrderManager");
		bpmcManager = (IBpmcManager) ApplicationContextHolder.webApplicationContext
				.getBean("bpmcManager");
		progListMangDetailManager = (IProgListMangDetailManager) ApplicationContextHolder.webApplicationContext
				.getBean("progListMangDetailManager");
		progListMangManager = (IProgListMangManager) ApplicationContextHolder.webApplicationContext
				.getBean("progListMangManager");
		programFilesManager = (IProgramFilesManager) ApplicationContextHolder.webApplicationContext
				.getBean("programFilesManager");
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

	// 张亚
	// 生成播发单
	// 暂未使用
	public CmsResultDto BroadcastListXml(String date, // yyyy-MM-dd
			// String defcatseq, // 栏目code序列
			String operatorId, // 操作人员id
			String plandate // 计划播发日期，格式："yyyy-MM-dd HH:mm:ss"
	) {
		cmsLog.debug("Cms -> BroadcastListXmlService -> BroadcastListXml...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 修改新接口，20100408 14:16
		cmsResultDto = generateBroadcastXml(date, // yyyy-MM-dd
				operatorId, // 操作人员id
				plandate // 计划播发日期，格式："yyyy-MM-dd HH:mm:ss"
		);

		/***********************************************************************
		 * 用到配置文件的地方 都有数字表明 按先后顺序排 如: // ******1 ******2
		 */


		cmsLog
				.debug("Cms -> BroadcastListXmlService -> BroadcastListXml returns.");
		return cmsResultDto;
	}

	// 20100329 14:24
	// 生成播发单
	// 生成xml文件, 会调用 generateBroadcastStr
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CmsResultDto generateBroadcastXml(String date, // yyyy-MM-dd
			String operatorId, // 操作人员id
			String plandate // 计划播发日期，格式："yyyy-MM-dd HH:mm:ss"
	) {
		// 生成播发单，根据讨论后新需求修改，在公元2010年3月29日
		// 1 - 查询date的栏目单详细记录
		// 2 - 根据栏目分类节目包

		// 播发单下发完成，保存发布文件表记录progListfile，并且发送活动 86-->87 播放单生成成功，发送

		cmsLog
				.debug("Cms -> BroadcastListXmlService -> generateBroadcastXml...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 配置文件库中
		String stclassOnline = "Online"; // 一级等级
		String stclassBroadcastxml = "Online"; // 一级等级
		String filecodeBroadcast = "BRDXML";

		String errorDetail = "";
		String rootPortalColumnDefcatseq = "";
		Date nowDate = new Date();

		/**
		 * 生成文件名
		 */
		String broadcastXmlName = "broadcast_";
		broadcastXmlName += date;
		broadcastXmlName += "_";
		broadcastXmlName += fileopr.convertDateToString(nowDate,
				"yyyyMMddHHmmss");
		broadcastXmlName += ".xml";
		String broadcastXmlFullPath = "";
		// cmsLog.info("播发单文件名：" + broadcastXmlName);
		System.out.println("播发单文件名：" + broadcastXmlName);

		String videosid = String.valueOf(nowDate.getTime() / 1000);
		String websid = String.valueOf((nowDate.getTime() / 1000) + 1);
		String broadcastTime = plandate;
		Date dplan = fileopr.convertStringToDate(plandate,
				"yyyy-MM-dd HH:mm:ss");
		Long ltmp = dplan.getTime();
		// ltmp += plandateoffset;
		dplan.setTime(ltmp);
		String websbroadcastTime = fileopr.convertDateToString(dplan,
				"yyyy-MM-dd HH:mm:ss");

		videosid = videosid
				.substring(videosid.length() - 10, videosid.length());
		websid = websid.substring(websid.length() - 10, websid.length());

		BroadcastXml broadcastXml = new BroadcastXml();

		String scheduledate = fileopr.convertDateToScheduleDate(date);
		cmsLog.info("判断编单总表活动是否是“播发”(FU00000086)...");
		cmsLog.info("编单总表日期：" + scheduledate);
		ProgListMang progListMang = (ProgListMang) progListMangManager
				.getById(scheduledate);
		if (progListMang.getIdAct().equalsIgnoreCase("FU00000086")) {
			cmsLog.info("编单总表活动是“播发”(FU00000086)，允许生成发布文件...");
			// 获得播发单xml文件生成的目标路径
			// 返回：List
			// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
			// 2 - List<Object[]>
			// (AmsStorage)Object[0]
			// (AmsStorageDir)Object[1]
			// (AmsStorageClass)Object[2]
			cmsLog.info("获得播发单xml文件生成的目标路径...");
			List dest = packageFilesManager.getDestPathByFilecodeStclasscode(
					filecodeBroadcast, stclassBroadcastxml);
			if (dest != null && dest.size() >= 2) {
				String destpath = (String) dest.get(0);

				broadcastXmlFullPath = destpath;
				broadcastXmlFullPath = broadcastXmlFullPath.replace('\\', '/');
				broadcastXmlFullPath = fileopr.checkPathFormatRear(
						broadcastXmlFullPath, '/');
				broadcastXmlFullPath += date;
				broadcastXmlFullPath += "/";
				broadcastXmlFullPath += broadcastXmlName;

				cmsLog.info("查询所有允许发布的栏目...");
				List portalColumns = portalColumnManager
						.getAllPublishPortalColumnsByDefcatseq(rootPortalColumnDefcatseq);
				if (portalColumns.size() <= 0) {
					String str = "未找到需要生成Portal的栏目，返回失败。";
					cmsLog.warn(str);
					errorDetail += str;
				} else {
					List listVideoColumn = new ArrayList();
					List listWebColumn = new ArrayList();

					cmsLog.info("共有" + portalColumns.size() + "个栏目允许发布。");
					for (int i = 0; i < portalColumns.size(); i++) {
						// 每个portalColumn需要生成一个js
						PortalColumn portalColumn = (PortalColumn) portalColumns
								.get(i);
						String pcDir = portalColumn.getParentdir();
						String strJs = "";

						List column = new ArrayList();
						List listTodayProg = new ArrayList();
						List listReserveProg = new ArrayList();
						List listColumnJs = new ArrayList();

						cmsLog.info("处理第" + (i + 1) + "个栏目...");
						cmsLog.info("栏目code：" + portalColumn.getDefcatcode());
						cmsLog.info("栏目名称：" + portalColumn.getColumnname());

						cmsLog.info("栏目是视频类型，查询栏目下所有栏目单详细记录...");
						// 根据日期和栏目单code序列，查询，得到栏目单详细和节目包
						List progListDetails = progListDetailManager
								.getProgListDetailsProgPackagesByDateAndDefcatseq(
										date, portalColumn.getDefcatseq());

						if (progListDetails != null) {
							cmsLog.info("共有" + portalColumns.size()
									+ "个栏目栏目单详细记录。");
							for (int j = 0; j < progListDetails.size(); j++) {
								Object[] rows = (Object[]) progListDetails
										.get(j);
								ProgListDetail progListDetail = (ProgListDetail) rows[0];
								ProgPackage progPackage = (ProgPackage) rows[1];

								List newProgPackage = new ArrayList();
								List fileses = new ArrayList();

								cmsLog.info("处理第" + (j + 1) + "个栏目单详细...");
								cmsLog.info("节目包ID: "
										+ progPackage.getProductid());
								cmsLog.info("节目包名称: "
										+ progPackage.getProductname());
								cmsLog.info("上线日期: "
										+ fileopr.convertDateToString(
												progListDetail.getValidFrom(),
												"yyyy-MM-dd HH:mm:ss"));

								// 返回： List<ProgramFiles>
								cmsLog.info("查询节目包下所有文件...");
								List packfs = packageFilesManager
										.findByProperty("productid",
												progPackage.getProductid());
								cmsLog.info("节目包下共有" + packfs.size() + "个文件。");
								for (int k = 0; k < packfs.size(); k++) {
									// ProgramFiles programFiles =
									// (ProgramFiles) packageFilesList.get(k);
									PackageFiles packageFiles = (PackageFiles) packfs
											.get(k);
									ProgramFiles programFiles = (ProgramFiles) programFilesManager
											.getById(packageFiles
													.getProgfileid());
									List files = new ArrayList();

									cmsLog.info("处理第" + (k + 1) + "个文件...");
									if (programFiles == null) {
										cmsLog.info("文件未查询到，跳过...");
										continue;
									}
									cmsLog.info("文件ID："
											+ programFiles.getProgfileid());
									cmsLog.info("文件名："
											+ programFiles.getFilename());
									if (programFiles.getFiletypeid()
											.equalsIgnoreCase("XML")) {
										cmsLog.info("文件的类型是“XML”，跳过...");
										continue;
									}

									// 调用方法三 获得原路径 参数Id , 配置文件中一级库的存储等级
									// 返回：List
									// 1 - String
									// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
									// 2 - List<Object[]>
									// (AmsStorage)Object[0]
									// (AmsStoragePrgRel)Object[1]
									// (AmsStorageDir)Object[2]
									// (AmsStorageClass)Object[3]
									List fileList = packageFilesManager
											.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
													programFiles
															.getProgfileid(),
													stclassOnline, progPackage
															.getProductid());

									if (fileList != null
											&& fileList.size() >= 2) {
										String sourcePath = (String) fileList
												.get(0);
										List list = (List) fileList.get(1);
										if (list != null && list.size() > 0) {
											Object[] rows2 = (Object[]) list
													.get(0);
											AmsStorage ams = (AmsStorage) rows2[0];
											AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows2[1];
											AmsStorageDir amsd = (AmsStorageDir) rows2[2];
											AmsStorageClass amsc = (AmsStorageClass) rows2[3];

											/*
											 * (String)FILE.get(0) : SRC
											 * (String)FILE.get(1) : PROGRANK
											 * (String)FILE.get(2): FILETYPEID
											 * (String)FILE.get(3) : FILECODE
											 * (String)FILE.get(4): PROGFILEID
											 * (String)FILE.get(5) : FILENAME
											 */
											String src = amspr.getFilepath();
											src = src.replace('\\', '/');
											src = fileopr.checkPathFormatFirst(
													src, '/');
											src = fileopr.checkPathFormatRear(
													src, '/');
											if ((long) portalColumn
													.getArchivedays() == (long) 0) {
												src += programFiles
														.getFilename();
											} else {
												src += programFiles
														.getProgfileid();
											}
											files.add(src);
											files.add(programFiles
													.getProgrank());
											files.add(programFiles
													.getFiletypeid());
											files.add(programFiles
													.getFilecode());
											files.add(programFiles
													.getProgfileid());
											files.add(programFiles
													.getFilename());
										} else {
											cmsLog.warn("查询文件源路径错误，不操作，跳过...");
										}
									} else {
										cmsLog.warn("查询文件源路径为空，不操作，跳过...");
									}
									if (files.size() >= 6) {
										fileses.add(files);
									}
								}

								newProgPackage.add(progPackage.getProductid());
								newProgPackage
										.add(progPackage.getProductname());
								newProgPackage.add(fileses);

								// 判断节目包是否今日上线
								String progValidFrom = fileopr
										.convertDateToString(progListDetail
												.getValidFrom(), "yyyy-MM-dd");
								if (date.equalsIgnoreCase(progValidFrom)) {
									// 今日上线节目包
									listTodayProg.add(newProgPackage);
								} else {
									// 已经上线节目包
									listReserveProg.add(newProgPackage);
								}
							}
						}

						String jssrc = date + "/"
								+ portalColumn.getPublishfilename();
						listColumnJs.add(jssrc);
						listColumnJs.add(portalColumn.getPublishfilename());

						column.add(portalColumn.getDefcatcode());
						column.add(portalColumn.getColumnname());
						column.add(listTodayProg);
						column.add(listReserveProg);
						column.add(listColumnJs);

						if ((long) portalColumn.getArchivedays() == (long) 0) {
							// 视频栏目
							listVideoColumn.add(column);
						} else {
							// 富媒体栏目
							listWebColumn.add(column);
						}
					}

					broadcastXml.setVideoProgListId(videosid);
					broadcastXml.setVideoBroadcastTime(broadcastTime);
					broadcastXml.setVideoColumns(listVideoColumn);
					broadcastXml.setWebProgListId(websid);
					broadcastXml.setWebBroadcastTime(websbroadcastTime);
					broadcastXml.setWebColumns(listWebColumn);
				}

				CmsResultDto c = generateBroadcastStr(broadcastXml, date);

				if ((long) c.getResultCode() == (long) 0) {
					String strxml = (String) c.getResultObject();
					// cmsLog.info(strxml);
					cmsLog.info("生成播发单字符串成功，准备生成播发单文件...");
					cmsLog.info("播发单文件目标路径 - " + broadcastXmlFullPath);

					strxml = XMLPatch.patch(strxml);

					cmsLog.info("生成播发单xml字符串成功，准备新增发布文件记录...");

					ProgListFile progListFile = new ProgListFile();

					String scheduledate1 = convertDateToScheduleDate(date);
					progListFile.setScheduledate(scheduledate1);
					progListFile.setFilename(broadcastXmlName);
					progListFile.setFiletype((long) 9);
					progListFile.setDate1(nowDate);
					progListFile.setDate4(fileopr.convertStringToDate(
							plandate, "yyyy-MM-dd HH:mm:ss"));
					progListFile.setState1((long) 1);
					progListFile.setState2((long) 0);
					progListFile.setInputmanid(operatorId);
					progListFile.setColumnxml(strxml);
					progListFile.setInputtime(nowDate);

					// 播发单下发完成，保存发布文件表记录progListfile，并且发送活动 86-->87
					// 播放单生成成功，发送
					CmsResultDto cms = cmsTransactionManager
							.saveProgListFileAndUpdateProgListMangDetail(
									progListFileManager,
									progListMangManager,
									progListMangDetailManager, bpmcManager,
									flowActivityOrderManager,
									portalColumnManager,
									progListDetailManager, progListFile,
									date, "", operatorId);

					// updateBroadcastXml(progListFile.getColumnfileid());

					if (cms.resultCode == 0) {
						/**
						 * 数据库记录插入成功后将xml文件中的id值改为当前ID值
						 */
						try {
							cmsLog.info("PushVod ID在字符串中的位置："
									+ strxml.indexOf("PushVod ID=\""));
							String strid = strxml.substring(
									strxml.indexOf("PushVod ID=\""), strxml
											.indexOf("PushVod ID=\"") + 23);
							cmsLog.info("原：" + strid);
							// Long lid = Long.valueOf(progListFile.getColumnfileid());
							Long idLong = Long.parseLong(progListFile.getColumnfileid());
							String cfid = idLong.toString();
							cfid = cfid.replaceAll("PF", "00");
							String strnewid = "PushVod ID=\"" + cfid + "\"";
							cmsLog.info("新：" + strnewid);
							strxml = strxml.replaceFirst(strid, strnewid);
							cmsLog.info(strxml);
						} catch (Exception e) {
							cmsLog.error("更新播发单xml异常：" + e.getMessage());
							cmsLog.info("不修改PushVod ID。");
							strxml = "";
						}
						
						if (!strxml.equalsIgnoreCase("")) {
							strxml = strxml.replaceAll("null</ReserveProg>", "</ReserveProg>");
							progListFile.setColumnxml(strxml);
							progListFileManager.update(progListFile);
							cmsLog.info("已经更新xml。PushVod ID:" + progListFile.getColumnfileid());
							
							//TODO 测试播发单内容
//							cmsLog.info(strxml.replaceAll("null</ReserveProg>", "</ReserveProg>"));
							
							/**
							 * 更新成功创建文件
							 */
							int ret = fileopr.createSmbFileGb2312(broadcastXmlFullPath, strxml);
							
							if (ret == 0) {
								/**
								 * 文件创建成功, 修改数据库记录状态
								 */
								progListFile.setState1(0l);
								progListFileManager.update(progListFile);
								cmsLog.info("已经更新播发单状态, 播发单 ID:" + progListFile.getColumnfileid());
							} else {
								String str = "生成播发单xml文件失败，返回失败。";
								cmsLog.warn(str);
								cmsResultDto.setResultCode((long) 1);
								cmsResultDto.setErrorMessage(str);
							}
							// 发送活动
							cmsLog.info("保存播发单文件记录并把栏目单分表发送到“完成”成功，生成播发单成功。");
						}
						
					} else {
						String str1 = "保存播发单文件记录并把栏目单分表发送到“完成”失败，返回失败。";
						cmsLog.warn(str1);
						cmsResultDto.setResultCode((long) 1);
						cmsResultDto.setErrorMessage(str1);
//
//						cmsLog.info("删除播发单文件...");
//						ret = fileopr.deleteSmbFile(broadcastXmlFullPath);
//						if (ret == 0) {
//							cmsLog.info("删除播发单文件成功，生成播发单文件失败。");
//						} else {
//							cmsLog.warn("删除播发单文件失败，生成播发单文件失败。");
//						}
					}
					
				} else {
					String str = "生成播发单字符串失败，返回失败。";
					cmsLog.warn(str);
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
					cmsResultDto.setErrorDetail(c.getErrorDetail());
				}
			} else {
				String str = "获得播发单目标路径失败，返回失败。";
				cmsLog.warn(str);
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
			}
		} else {
			cmsLog.info("编单总表活动：" + progListMang.getIdAct());
			String str = "编单总表活动不是“播发”(FU00000086)，不允许生成发布文件。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}

		cmsLog
				.debug("Cms -> BroadcastListXmlService -> generateBroadcastXml returns.");
		return cmsResultDto;
	}

	// 20100329 14:24
	// 生成播发单字符串
	@SuppressWarnings("rawtypes")
	private CmsResultDto generateBroadcastStr(BroadcastXml broadcastXml, String date) {
		cmsLog
				.debug("Cms -> BroadcastListXmlService -> generateBroadcastStr...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		String strxml = "";
		try {
			String columnJsPath = "";

			strxml = "<?xml version=\"1.0\" encoding=\"gb2312\" ?> \r\n";
			strxml += "<PushVod ID=\"";
			strxml += broadcastXml.getVideoProgListId();
			strxml += "\" BroadcastTime=\"";
			strxml += broadcastXml.getVideoBroadcastTime();
			strxml += "\">\r\n";

			strxml += "<VideosProgList>\r\n";

			List listColumn = (List) broadcastXml.getVideoColumns();
			if (listColumn != null && listColumn.size() > 0) {
				for (int i = 0; i < listColumn.size(); i++) {
					List column = (List) listColumn.get(i);
					String defcatcode = (String) column.get(0);
					String name = (String) column.get(1);
					List listTodayProg = (List) column.get(2);
					List listReserveProg = (List) column.get(3);
					List listColumnJs = (List) column.get(4);

					strxml += "<Column DEFCATCODE=\"";
					strxml += defcatcode;
					strxml += "\" name=\"";
					strxml += name;
					strxml += "\">\r\n";

					strxml += "<TodayProg>\r\n";
					for (int j = 0; j < listTodayProg.size(); j++) {
						List newProgPackage = (List) listTodayProg.get(j);

						String progid = (String) newProgPackage.get(0);
						String progname = (String) newProgPackage.get(1);
						List listfile = (List) newProgPackage.get(2);

						strxml += "<ProgPackage PPID=\"";
						strxml += progid;
						strxml += "\" STBTITLE=\"";
						strxml += progname;
						strxml += "\">\r\n";

						for (int k = 0; k < listfile.size(); k++) {
							List files = (List) listfile.get(k);

							if (files.size() >= 6) {
								String src = (String) files.get(0);
								Long progrank = (Long) files.get(1);
								String filetypeid = (String) files.get(2);
								String filecode = (String) files.get(3);
								String progfileid = (String) files.get(4);
								String filename = (String) files.get(5);

								strxml += "<FILE TITLE=\"";
								strxml += progname;
								strxml += "\" SRC=\"";
								strxml += src;
								strxml += "\" PROGRANK=\"";
								strxml += progrank;
								strxml += "\" FILETYPEID=\"";
								strxml += filetypeid;
								strxml += "\" FILECODE=\"";
								strxml += filecode;
								strxml += "\" PROGFILEID=\"";
								strxml += progfileid;
								strxml += "\" FILENAME=\"";
								strxml += filename;
								strxml += "\" />\r\n";
							}
						}
						strxml += "</ProgPackage>\r\n";
					}
					strxml += "</TodayProg>\r\n";

					strxml += "<ReserveProg>\r\n";
					if (null != listReserveProg) {
						for (int j = 0; j < listReserveProg.size(); j++) {
							List newProgPackage = (List) listReserveProg.get(j);
							String progid = (String) newProgPackage.get(0);
							String progname = (String) newProgPackage.get(1);
							List listfile = (List) newProgPackage.get(2);

							strxml += "<ProgPackage PPID=\"";
							strxml += progid;
							strxml += "\" STBTITLE=\"";
							strxml += progname;
							strxml += "\">\r\n";

							for (int k = 0; k < listfile.size(); k++) {
								List files = (List) listfile.get(k);

								if (files.size() >= 6) {
									String src = (String) files.get(0);
									Long progrank = (Long) files.get(1);
									String filetypeid = (String) files.get(2);
									String filecode = (String) files.get(3);
									String progfileid = (String) files.get(4);
									String filename = (String) files.get(5);

									strxml += "<FILE SRC=\"";
									strxml += src;
									strxml += "\" PROGRANK=\"";
									strxml += progrank;
									strxml += "\" FILETYPEID=\"";
									strxml += filetypeid;
									strxml += "\" FILECODE=\"";
									strxml += filecode;
									strxml += "\" PROGFILEID=\"";
									strxml += progfileid;
									strxml += "\" FILENAME=\"";
									strxml += filename;
									strxml += "\" />\r\n";
								}
							}
							strxml += "</ProgPackage>\r\n";
						}
					}
					strxml += "</ReserveProg>\r\n";

					strxml += "<ColumnJs SRC=\"";
					strxml += (String) listColumnJs.get(0);
					strxml += "\" FILENAME=\"";
					strxml += (String) listColumnJs.get(1);
					strxml += "\" />\r\n";

					strxml += "</Column>\r\n";

					columnJsPath = (String) listColumnJs.get(0);
				}
			}
			strxml += "</VideosProgList>\r\n";

			strxml += "<WebsProgList>\r\n";

			listColumn = (List) broadcastXml.getWebColumns();
			if (listColumn != null && listColumn.size() > 0) {
				for (int i = 0; i < listColumn.size(); i++) {
					List column = (List) listColumn.get(i);
					String defcatcode = (String) column.get(0);
					String name = (String) column.get(1);
					List listTodayProg = (List) column.get(2);
					List listReserveProg = (List) column.get(3);
					List listColumnJs = (List) column.get(4);
					
					if ("njsw_dzbz".equalsIgnoreCase(defcatcode) 
							|| "yx_dzbz".equalsIgnoreCase(defcatcode)
							|| "农家书屋电子报纸".equalsIgnoreCase(name)
							|| "高清品牌电子报纸".equalsIgnoreCase(name)) {
						continue;
					}

					strxml += "<Column DEFCATCODE=\"";
					strxml += defcatcode;
					strxml += "\" name=\"";
					strxml += name;
					strxml += "\">\r\n";

					strxml += "<TodayProg>\r\n";
					for (int j = 0; j < listTodayProg.size(); j++) {
						List newProgPackage = (List) listTodayProg.get(j);

						String progid = (String) newProgPackage.get(0);
						String progname = (String) newProgPackage.get(1);
						List listfile = (List) newProgPackage.get(2);

						strxml += "<ProgPackage PPID=\"";
						strxml += progid;
						strxml += "\" STBTITLE=\"";
						strxml += progname;
						strxml += "\">\r\n";

						for (int k = 0; k < listfile.size(); k++) {
							List files = (List) listfile.get(k);

							String src = (String) files.get(0);
							Long progrank = (Long) files.get(1);
							String filetypeid = (String) files.get(2);
							String filecode = (String) files.get(3);
							String progfileid = (String) files.get(4);
							String filename = (String) files.get(5);;
							
							/**
							 * HuangBo update by 2010年8月25日 16时6分
							 * 此部分播发单内容为富媒体部分,
							 * 如果是主文件, 则子节点为Folder, 否则子节点为File, 属性不变
							 */
							
							if (1 == progrank) {
								src = -1 != src.indexOf(".") 
										? src.substring(0, src.lastIndexOf(".")) + "/"
										: src.endsWith("/") ? src : src + "/";
								filename = -1 != filename.indexOf(".") 
										? filename.substring(0, filename.lastIndexOf("."))
										: filename;
								strxml += "<FOLDER TITLE=\"";
							} else {
								strxml += "<FILE TITLE=\"";
							}
							strxml += progname;
							strxml += "\" SRC=\"";
							strxml += src;
							strxml += "\" PROGRANK=\"";
							strxml += progrank;
							strxml += "\" FILETYPEID=\"";
							strxml += filetypeid;
							strxml += "\" FILECODE=\"";
							strxml += filecode;
							strxml += "\" PROGFILEID=\"";
							strxml += progfileid;
							strxml += "\" FILENAME=\"";
							strxml += filename;
							strxml += "\" />\r\n";
						}
						strxml += "</ProgPackage>\r\n";
					}
					strxml += "</TodayProg>\r\n";

					strxml += "<ReserveProg>\r\n";
					for (int j = 0; j < listReserveProg.size(); j++) {
						List newProgPackage = (List) listReserveProg.get(j);
						String progid = (String) newProgPackage.get(0);
						String progname = (String) newProgPackage.get(1);
						List listfile = (List) newProgPackage.get(2);

						strxml += "<ProgPackage PPID=\"";
						strxml += progid;
						strxml += "\" STBTITLE=\"";
						strxml += progname;
						strxml += "\">\r\n";

						for (int k = 0; k < listfile.size(); k++) {
							List files = (List) listfile.get(k);

							String src = (String) files.get(0);
							Long progrank = (Long) files.get(1);
							String filetypeid = (String) files.get(2);
							String filecode = (String) files.get(3);
							String progfileid = (String) files.get(4);
							String filename = (String) files.get(5);

							strxml += "<FILE SRC=\"";
							strxml += src;
							strxml += "\" PROGRANK=\"";
							strxml += progrank;
							strxml += "\" FILETYPEID=\"";
							strxml += filetypeid;
							strxml += "\" FILECODE=\"";
							strxml += filecode;
							strxml += "\" PROGFILEID=\"";
							strxml += progfileid;
							strxml += "\" FILENAME=\"";
							strxml += filename;
							strxml += "\" />\r\n";
						}
						strxml += "</ProgPackage>\r\n";
					}
					strxml += "</ReserveProg>\r\n";

					strxml += "<ColumnJs SRC=\"";
					strxml += (String) listColumnJs.get(0);
					strxml += "\" FILENAME=\"";
					strxml += (String) listColumnJs.get(1);
					strxml += "\" />\r\n";

					strxml += "</Column>\r\n";
				}
			}
			//TODO 添加报纸部分的XML内容
			String progDate = date.replaceAll("-", "");
			String paper = new CmsConfig().getPropertyByName("paperJS");
			String paperStr = MessageFormat.format(paper, progDate, progDate, progDate, progDate);
			
			strxml += paperStr;
			strxml += "</WebsProgList>\r\n";

			if (columnJsPath != null || !columnJsPath.equalsIgnoreCase("")) {
				columnJsPath = columnJsPath.substring(0, columnJsPath
						.indexOf('/') + 1);

				strxml += "<HomePage>\r\n";
				strxml += "<FOLDER SRC=\"";
				strxml += "/home/";
				strxml += "\" FILENAME=\"";
				strxml += "home";
				strxml += "\" />\r\n";
				strxml += "<HomeJs SRC=\"";
				strxml += "data_home.js";
				strxml += "\" FILENAME=\"";
				strxml += "data_home.js";
				strxml += "\" />\r\n";
				strxml += "<YgJs SRC=\"";
				strxml += columnJsPath + "gq_yg.js";
				strxml += "\" FILENAME=\"";
				strxml += "gq_yg.js";
				strxml += "\" />\r\n";
				/**
				 * HuangBo addition by 2010年10月18日 9时40分
				 */
				strxml += "<YgJs SRC=\"";
				strxml += columnJsPath + "njsw_yg.js";
				strxml += "\" FILENAME=\"";
				strxml += "njsw_yg.js";
				strxml += "\" />\r\n";
				
				strxml += "</HomePage>\r\n";
			}
			strxml += "</PushVod>";
			
			cmsResultDto.setResultObject(strxml);
		} catch (Exception ex) {
			String str = "Cms -> BroadcastListXmlService -> generateBroadcastStr，异常："
					+ ex.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage("生成播发单字符串异常。");
			cmsResultDto.setErrorDetail(str);
		}

		cmsLog
				.debug("Cms -> PortalServiceImpl -> generateBroadcastStr returns.");
		return cmsResultDto;
	}

	// 20100524 10:31
	// 修改加扰任务单的PushVod ID为proglistfile数据库记录id
	// 生成xml文件内容字符串之后..
	private CmsResultDto updateBroadcastXml(String columnfileid) {
		cmsLog.debug("Cms -> BroadcastListXmlService -> updateBroadcastXml...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		try {
			cmsLog.info("columnfileid:" + columnfileid);
			ProgListFile progListFile = (ProgListFile) progListFileManager
					.getById(columnfileid);
			String strxml = progListFile.getColumnxml();

			try {
				cmsLog.info("PushVod ID在字符串中的位置："
						+ strxml.indexOf("PushVod ID=\""));
				String strid = strxml.substring(
						strxml.indexOf("PushVod ID=\""), strxml
								.indexOf("PushVod ID=\"") + 23);
				cmsLog.info("原：" + strid);
				// Long lid = Long.valueOf(progListFile.getColumnfileid());
				String cfid = progListFile.getColumnfileid();
				cfid = cfid.replaceAll("PF", "00");
				String strnewid = "PushVod ID=\"" + cfid + "\"";
				cmsLog.info("新：" + strnewid);
				strxml = strxml.replaceFirst(strid, strnewid);
				cmsLog.info(strxml);
			} catch (Exception e) {
				cmsLog.error("更新播发单xml异常：" + e.getMessage());
				cmsLog.info("不修改PushVod ID。");
				strxml = "";
			}
			if (!strxml.equalsIgnoreCase("")) {
				progListFile.setColumnxml(strxml);
				progListFileManager.update(progListFile);
				cmsLog.info("已经更新xml。PushVod ID:" + columnfileid);
			}
		} catch (Exception e) {
			cmsLog
					.error("Cms -> BroadcastListXmlService -> updateBroadcastXml，异常："
							+ e.getMessage());
		}
		cmsLog
				.debug("Cms -> BroadcastListXmlService -> updateBroadcastXml returns.");
		return cmsResultDto;
	}

	public void test() {
		this.generateBroadcastXml("2010-06-13", "OP00000034",
				"2010-06-22 00:00:00");
	}
}
