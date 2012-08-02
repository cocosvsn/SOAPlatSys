package com.soaplat.cmsmgr.service;

import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.apache.log4j.Logger;

import cn.sh.sbl.cms.listener.ServerConfigListener;

import com.cbs.cbsmgr.manageriface.IPpSrvPdtRelManager;
import com.cbs.cbsmgr.manageriface.IProductCategoryManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageClassManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.cmsmgr.bean.Bpmc;
import com.soaplat.cmsmgr.bean.FlowActivityOrder;
import com.soaplat.cmsmgr.bean.PackStyle;
import com.soaplat.cmsmgr.bean.PackStylePortalColumn;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.PortalRoleOperRel;
import com.soaplat.cmsmgr.bean.ProgList;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.bean.ProgListMangDetail;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgProduct;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.dto.FunResourceDto;
import com.soaplat.cmsmgr.dto.PortalColumnDto;
import com.soaplat.cmsmgr.manageriface.IBpmcManager;
import com.soaplat.cmsmgr.manageriface.ICmsSiteProductRelManager;
import com.soaplat.cmsmgr.manageriface.ICmsTransactionManager;
import com.soaplat.cmsmgr.manageriface.IFlowActivityOrderManager;
import com.soaplat.cmsmgr.manageriface.IPPColumnRelManager;
import com.soaplat.cmsmgr.manageriface.IPackStyleManager;
import com.soaplat.cmsmgr.manageriface.IPackStylePortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IPortalModManager;
import com.soaplat.cmsmgr.manageriface.IPortalRoleOperRelManager;
import com.soaplat.cmsmgr.manageriface.IPricingManager;
import com.soaplat.cmsmgr.manageriface.IProductManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListFileManager;
import com.soaplat.cmsmgr.manageriface.IProgListManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.cmsmgr.manageriface.ISrvColumnManager;
import com.soaplat.cmsmgr.util.ParamCheck;
import com.soaplat.sysmgr.bean.FunResource;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.manageriface.IRoleManager;

public class PortalColumnServiceImpl implements PortalColumnServiceIface {
	private final String ATTRIBUTE = "{0}:''{1}''";
	private final String ATTRIBUTENUMBER = "{0}:{1}";
    private final String ATTRIBUTEOBJECT = "{0}:'{'{1}'}'";
    private final String ARRAYOBJECT = "{0}:[{1}]";
    private final String STRING = "''{0}''";
    
    private final String PROGACTION = "FU00000077";

	private static FileOperationImpl fileopr = new FileOperationImpl();

	private IPortalColumnManager portalColumnManager = null;
	private IPPColumnRelManager pPColumnRelManager = null;
	private IProgListManager progListManager = null;
	private IProgListDetailManager progListDetailManager = null;
	private ISrvColumnManager srvColumnManager = null;
	private IPortalModManager portalModManager = null;
	private IFlowActivityOrderManager flowActivityOrderManager = null;
	private IBpmcManager bpmcManager = null;
	private IProgPackageManager progPackageManager = null;
	private IPackStyleManager packStyleManager = null;
	private IPortalRoleOperRelManager portalRoleOperRelManager = null;
	private IRoleManager roleManager = null;
	private IProgListMangDetailManager progListMangDetailManager = null;
	private IProgListMangManager progListMangManager = null;
	private IProgListFileManager progListFileManager = null;
	private IAmsStorageManager amsstorageManager = null;
	private IAmsStorageClassManager amsstorageclassManager = null;
	private IAmsStorageDirManager amsstoragedirManager = null;
	private IPackageFilesManager packageFilesManager = null;
	private IPpSrvPdtRelManager ppSrvPdtRelManager = null;
	private IProductCategoryManager productCategoryManager = null;
	private IProgramFilesManager programFilesManager = null;
	private IPackStylePortalColumnManager packStylePortalColumnManager = null;
	private ICmsSiteProductRelManager cmsSiteProductRelManager = null;
	private IPricingManager pricingManager = null;
	private IProductManager productManager = null;

	private ICmsTransactionManager cmsTransactionManager = null;

	public static final Logger cmsLog = Logger.getLogger("Cms");
	private String onlineJsPath;

	public PortalColumnServiceImpl() {
		portalColumnManager = (IPortalColumnManager) ApplicationContextHolder.webApplicationContext
				.getBean("portalColumnManager");
		pPColumnRelManager = (IPPColumnRelManager) ApplicationContextHolder.webApplicationContext
				.getBean("pPColumnRelManager");
		progListManager = (IProgListManager) ApplicationContextHolder.webApplicationContext
				.getBean("progListManager");
		progListDetailManager = (IProgListDetailManager) ApplicationContextHolder.webApplicationContext
				.getBean("progListDetailManager");
		srvColumnManager = (ISrvColumnManager) ApplicationContextHolder.webApplicationContext
				.getBean("srvColumnManager");
		portalModManager = (IPortalModManager) ApplicationContextHolder.webApplicationContext
				.getBean("portalModManager");
		flowActivityOrderManager = (IFlowActivityOrderManager) ApplicationContextHolder.webApplicationContext
				.getBean("flowActivityOrderManager");
		bpmcManager = (IBpmcManager) ApplicationContextHolder.webApplicationContext
				.getBean("bpmcManager");
		progPackageManager = (IProgPackageManager) ApplicationContextHolder.webApplicationContext
				.getBean("progPackageManager");
		packStyleManager = (IPackStyleManager) ApplicationContextHolder.webApplicationContext
				.getBean("packStyleManager");
		portalRoleOperRelManager = (IPortalRoleOperRelManager) ApplicationContextHolder.webApplicationContext
				.getBean("portalRoleOperRelManager");
		roleManager = (IRoleManager) ApplicationContextHolder.webApplicationContext
				.getBean("roleManager");
		progListMangDetailManager = (IProgListMangDetailManager) ApplicationContextHolder.webApplicationContext
				.getBean("progListMangDetailManager");
		progListMangManager = (IProgListMangManager) ApplicationContextHolder.webApplicationContext
				.getBean("progListMangManager");
		progListFileManager = (IProgListFileManager) ApplicationContextHolder.webApplicationContext
				.getBean("progListFileManager");
		amsstorageManager = (IAmsStorageManager) ApplicationContextHolder.webApplicationContext
				.getBean("amsstorageManager");
		amsstorageclassManager = (IAmsStorageClassManager) ApplicationContextHolder.webApplicationContext
				.getBean("amsstorageclassManager");
		amsstoragedirManager = (IAmsStorageDirManager) ApplicationContextHolder.webApplicationContext
				.getBean("amsstoragedirManager");
		packageFilesManager = (IPackageFilesManager) ApplicationContextHolder.webApplicationContext
				.getBean("packageFilesManager");
		ppSrvPdtRelManager = (IPpSrvPdtRelManager) ApplicationContextHolder.webApplicationContext
				.getBean("ppSrvPdtRelManager");
		productCategoryManager = (IProductCategoryManager) ApplicationContextHolder.webApplicationContext
				.getBean("productCategoryManager");
		programFilesManager = (IProgramFilesManager) ApplicationContextHolder.webApplicationContext
				.getBean("programFilesManager");
		packStylePortalColumnManager = (IPackStylePortalColumnManager) ApplicationContextHolder.webApplicationContext
				.getBean("packStylePortalColumnManager");
		this.cmsSiteProductRelManager = (ICmsSiteProductRelManager) ApplicationContextHolder
				.webApplicationContext.getBean("cmsSiteProductRelManager");
		cmsTransactionManager = (ICmsTransactionManager) ApplicationContextHolder.webApplicationContext
				.getBean("cmsTransactionManager");
		this.pricingManager = (IPricingManager) ApplicationContextHolder
				.webApplicationContext.getBean("pricingManager");
		this.productManager = (IProductManager) ApplicationContextHolder
				.webApplicationContext.getBean("cmsProductManager");
	}

	// ------------------------------------- ProgList
	// ------------------------------------------------------
	// 得到栏目单
	public CmsResultDto getAllProgLists() {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> getAllProgLists...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		List progLists = progListManager.findAll();
		cmsResultDto.setResultObject(progLists);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getAllProgLists returns.");
		return cmsResultDto;
	}

	// 得到栏目单
	public CmsResultDto getProgListsByIdact(String idact, String pop) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> getProgListsByIdact...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		List progLists = progListManager.getProgListsByIdActAndPop(idact, pop);
		cmsResultDto.setResultObject(progLists);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListsByIdact returns.");
		return cmsResultDto;
	}

	// 保存（新建）栏目单
	public CmsResultDto saveProgList(ProgList progList) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> saveProgList...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsTransactionManager.saveProgList(progListManager, bpmcManager,
				progList);

		cmsResultDto.setResultObject(progList);
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> saveProgList returns.");
		return cmsResultDto;
	}

	// 根据栏目路径序列和栏目单编号，查询得到栏目单的栏目下的节目包
	public CmsResultDto getProgListDetailsByPdateAndDefcatseq(String pdate,
			String defcatseq) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListDetailsByPdateAndColumnclassid...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		//		
		cmsLog.info("代码空。");

		// // List progListDetails =
		// progListDetailManager.getProgListDetailsByPdateAndDefcatseq(pdate,
		// defcatseq);
		//		
		// List progListDetails = new ArrayList();
		//		
		// // 根据defcatseq,查询所有的栏目
		// List portalColumns =
		// portalColumnManager.getPortalColumnsByDefcatseq(defcatseq);
		//		
		// // 根据栏目和pdate,查询所有栏目单细表里的记录
		// for(int i = 0; i < portalColumns.size(); i++)
		// {
		// PortalColumn portalColumn = (PortalColumn)portalColumns.get(i);
		// List details =
		// progListDetailManager.getProgListDetailsByPdateAndColumnclassid(pdate,
		// portalColumn.getColumnclassid());
		// for(int j = 0; j < details.size(); j++)
		// {
		// ProgListDetail progListDetail = (ProgListDetail)details.get(j);
		// progListDetails.add(progListDetail);
		// }
		// }
		// cmsResultDto.setResultObject(progListDetails);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListDetailsByPdateAndColumnclassid returns.");
		return cmsResultDto;
	}

	// 添加栏目单详细 ProgListDetail
	public CmsResultDto addProgListDetailsToProgList(List progPackages,
			String pdate, String columnclassid, List lnums) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> addProgListDetailsToProgList...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsTransactionManager.saveProgListDetailsToProgList(progListManager,
				progListDetailManager, portalColumnManager, progPackages,
				pdate, columnclassid, lnums);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> addProgListDetailsToProgList returns.");
		return cmsResultDto;
	}

	// 删除栏目单详细 ProgListDetail
	public CmsResultDto deleteProgListDetailsFromProgList(List progListDetails) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> deleteProgListDetailsFromProgList...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsTransactionManager.deleteProgListDetailsFromProgList(
				progListDetailManager, progListDetails);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> deleteProgListDetailsFromProgList returns.");
		return cmsResultDto;
	}

	// 更新栏目单详细 ProgListDetail 20091208 12:40
	// progListDetails : 需要更新的栏目单详细
	// offlineDate : 需要更新的下线日期，包含日期时间
	public CmsResultDto updateProgListDetails(
			List<ProgListDetail> progListDetails, Date offlineDate) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> updateProgListDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsResultDto = cmsTransactionManager.updateProgListDetails(
				progListDetailManager, portalColumnManager, progPackageManager,
				progListDetails, offlineDate);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> updateProgListDetails returns.");
		return cmsResultDto;
	}

	// -------- 流程控制 --------------
	// 20100201 10:36
	// 根据实体ID，查询实体name
	protected String getNameById(String EntityName, String id) {
		String name = "";
		if (EntityName.equalsIgnoreCase("ProgPackageState")) {
			// 节目包状态
			// 0 - 导入
			// 1 - 缓存库
			// 2 - 加扰库
			// 3 - 播控库
			// 9 - 北京缓存库
			if (id.equalsIgnoreCase("0")) {
				name = "导入区";
			} else if (id.equalsIgnoreCase("1")) {
				name = "缓存库";
			} else if (id.equalsIgnoreCase("2")) {
				name = "加扰库";
			} else if (id.equalsIgnoreCase("3")) {
				name = "播控库";
			} else if (id.equalsIgnoreCase("9")) {
				name = "北京缓存库";
			} else if (id.equalsIgnoreCase("-1")) {
				name = "未导入";
			} else {
				name = "未知(" + id + ")";
			}
		} else if (EntityName.equalsIgnoreCase("ProgPackageDealState")) {
			// 节目包处理状态（0未处理1处理8失败9成功）
			if (id.equalsIgnoreCase("0")) {
				name = "未处理";
			} else if (id.equalsIgnoreCase("1")) {
				name = "处理中";
			} else if (id.equalsIgnoreCase("8")) {
				name = "失败";
			} else if (id.equalsIgnoreCase("9")) {
				name = "成功";
			} else if (id.equalsIgnoreCase("-1")) {
				name = "未导入";
			} else if (id.equalsIgnoreCase("-2")) {
				name = "待更新";
			} else {
				name = "未知(" + id + ")";
			}
		} else {
			name = "未知";
		}
		return name;
	}

	// 根据当前活动，查询下一活动和操作人员
	public CmsResultDto getNextOperationByCurOperation(String curIdProcess) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getNextOperationByCurOperation...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		List funResourceDtos = new ArrayList();
		List funResources = flowActivityOrderManager
				.getFunResourcesByFlowactivityidparent(curIdProcess);
		for (int i = 0; i < funResources.size(); i++) {
			Object[] rows = (Object[]) funResources.get(i);

			FunResourceDto funResourceDto = new FunResourceDto();

			funResourceDto.setFunResource((FunResource) rows[0]);
			funResourceDto.setState2((String) rows[1]);
			List operators = flowActivityOrderManager
					.getOperatorsByFuncid(funResourceDto.getFunResource()
							.getFuncid());
			funResourceDto.setOperators(operators);

			funResourceDtos.add(funResourceDto);
		}

		cmsResultDto.setResultObject(funResourceDtos);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getNextOperationByCurOperation returns.");
		return cmsResultDto;
	}

	// 发送 栏目单到下一活动idProcess，下一活动人，
	// FU00000077 编单定义
	public CmsResultDto sendProgList(ProgList progList, String nextIdAct,
			String nextOperator, String nextState2, String operator,
			String sendremark) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> sendProgList...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsLog.info("代码空");
		// cmsTransactionManager.saveSendProgList(
		// progListManager,
		// bpmcManager,
		// progList,
		// nextIdAct,
		// nextOperator,
		// nextState2,
		// operator,
		// sendremark
		// );

		cmsLog.debug("Cms -> PortalColumnServiceImpl -> sendProgList returns.");
		return cmsResultDto;
	}

	// --------- 栏目编单修改 20091202 -----------

	// 添加节目包progPackage，到栏目单（详细）ProgListDetail，20091202 21:16，20091226 更新
	public CmsResultDto saveProgListDetails(List<ProgPackage> progPackages, // 节目包
			String date, // 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			Date offlineDate, // 这批节目包的下线日期，包括时间
			String columnclassid, // 栏目编号
			List<String> lnums, // 节目包的序号
			String userId // 操作人员的id
	) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> saveProgListDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		List<Long> newLnums = new ArrayList<Long>();
		for (int i = 0; i < lnums.size(); i++) {
			Long lnum = Long.valueOf(lnums.get(i));
			newLnums.add(lnum);
		}

		// 判断，如果栏目是富媒体类型，date的栏目单已经存在记录，则不允许添加新节目包到编单
		// 查询
		PortalColumn pc = (PortalColumn) portalColumnManager
				.getById(columnclassid);
		if (pc == null) {
			// 
			String str = "栏目不存在。栏目ID：" + columnclassid;
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.info(str);
		} else {
			cmsResultDto = cmsTransactionManager.saveProgListDetails(
					progListDetailManager, portalColumnManager,
					progPackageManager, packStylePortalColumnManager,
					packageFilesManager, programFilesManager, progPackages,
					date, offlineDate, columnclassid, newLnums, userId);
		}
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> saveProgListDetails returns.");
		return cmsResultDto;
	}

	// 删除节目包progListDetail，从栏目单（详细）ProgListDetail，20091202 21:16
	// 只能删除上线日期 == 当前栏目单的日期的
	public CmsResultDto deleteProgListDetails(
			List<ProgListDetail> progListDetails, String date) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> deleteProgListDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		if (progListDetails == null) {
			cmsLog.info("progListDetails == null");
			cmsResultDto.setResultCode((long) 1);
			return cmsResultDto;
		}
		cmsResultDto = cmsTransactionManager.deleteProgListDetails(
				progListDetailManager, progListDetails, date);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> deleteProgListDetails returns.");
		return cmsResultDto;
	}

	// 报警查看，date格式：yyyy-MM-dd
	public CmsResultDto viewProgListDetailAlarm(String date) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> viewProgListDetailAlarm...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 返回：
		// 1 - 文件总大小MB
		// 2 - 在近线库数量 NEARSTATUS
		// 3 - 不在近线库数量
		// 4 - 在离线库数量 OFFLINESTATUS
		// 5 - 不在离线库数量
		// 6 - 在播出库数量 ONAIRSTATUS
		// 7 - 不在播出库数量
		// 8 - List<ProgramFiles>
		// 9 - List<ProgPackage>
		// 10- List<ProgListDetail>
		List retList = new ArrayList(); // 返回数组
		List<String> files = new ArrayList<String>(); // 已计算过文件列表
		Double total = Double.valueOf(0); // 总的文件大小，单位：MB
		Long atNearCount = Long.valueOf(0);
		Long notAtNearCount = Long.valueOf(0);
		Long atOfflineCount = Long.valueOf(0);
		Long notAtOfflineCount = Long.valueOf(0);
		Long atOnAirCount = Long.valueOf(0);
		Long notAtOnAirCount = Long.valueOf(0);
		List<ProgramFiles> programFileses = new ArrayList<ProgramFiles>();
		List<ProgPackage> progPackages = new ArrayList<ProgPackage>();
		List<ProgListDetail> progListDetails = new ArrayList<ProgListDetail>();

		// 查询栏目单的节目文件列表
		List list = progListDetailManager.getProgramFilesesByDate(date);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				int iExist = 0;
				Object[] rows = (Object[]) list.get(i);
				ProgramFiles programFiles = (ProgramFiles) rows[0];
				ProgPackage progPackage = (ProgPackage) rows[1];
				ProgListDetail progListDetail = (ProgListDetail) rows[2];

				// 计算文件总大小
				Long hi = progListDetail.getFilesizehi();
				Long lo = progListDetail.getFilesizehi();
				if (hi == null)
					hi = (long) 0;
				if (lo == null)
					lo = (long) 0;

				for (int j = 0; j < files.size(); j++) {
					String progfileid = files.get(j);

					if (progfileid.equalsIgnoreCase(programFiles
							.getProgfileid())) {
						iExist = 1;
						break;
					}
				}
				if (iExist == 0) {
					// 不存在
					total += Double.valueOf(((hi << 32) + lo) / (1024 * 1024)); // 单位MB
					files.add(programFiles.getProgfileid());
				} else {
					// 文件已计算过
				}

				// 记录其他数量
				if (programFiles.getNearstatus() == null
						|| programFiles.getNearstatus() == 0)
					notAtNearCount++;
				else
					atNearCount++;

				if (programFiles.getOfflinestatus() == null
						|| programFiles.getOfflinestatus() == 0)
					notAtOfflineCount++;
				else
					atOfflineCount++;

				if (programFiles.getOnairstatus() == null
						|| programFiles.getOnairstatus() == 0)
					notAtOnAirCount++;
				else
					atOnAirCount++;

				programFileses.add(programFiles);
				progPackages.add(progPackage);
				progListDetails.add(progListDetail);
			}
		}

		retList.add(total);
		retList.add(atNearCount);
		retList.add(notAtNearCount);
		retList.add(atOfflineCount);
		retList.add(notAtOfflineCount);
		retList.add(atOnAirCount);
		retList.add(notAtOnAirCount);
		retList.add(programFileses);
		retList.add(progPackages);
		retList.add(progListDetails);

		cmsResultDto.setResultObject(retList);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> viewProgListDetailAlarm returns.");
		return cmsResultDto;
	}

	// 发送栏目单（详细），到下一活动idProcess，下一活动人，
	public CmsResultDto sendProgListDetails(
			List<ProgListDetail> progListDetails, // 需要发送的栏目单详细
			String nextIdAct, // 下一活动编号
			String nextOperator, // 下一操作人员
			String nextState2, // 下一活动的性质：（新建N 回退R 顺序P 完成C）
			String operator, // 当前操作人员
			String sendremark // 备注
	) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> sendProgListDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsTransactionManager.saveSendProgListDetails(progListDetailManager,
				bpmcManager, progListDetails, nextIdAct, nextOperator,
				nextState2, operator, sendremark);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> sendProgListDetails returns.");
		return cmsResultDto;
	}

	// 未完成
	// 发布栏目单（详细）
	public CmsResultDto publishProgListDetails() {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> publishProgListDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> publishProgListDetails returns.");
		return cmsResultDto;
	}

	// 根据日期和栏目，查询栏目单详细，得到栏目单详细列表。
	// date: 栏目单的日期，格式：yyyy-MM-dd
	// defcatseq : 栏目的code
	public CmsResultDto getProgListDetailsByDateAndDefcatseq(String date,
			String defcatseq, String operator) {
		// 返回：List
		// 1 - List<ProgListDetail> progListDetails
		// 2 - List<String> states 节目包状态（0导入1缓存库2加扰库3播控库）
		// 3 - List<String> dealStates 处理状态（0未处理1处理8失败9成功）
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListDetailsByDateAndDefcatseq...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		List retlist = new ArrayList();

		try {
			// List list =
			// progListDetailManager.getProgListDetailsByDateAndDefcatseq(date,
			// defcatseq);
			// // 返回：
			// // List<Object[]>
			// // (ProgListDetail)Object[0]
			// // (ProgPackage)Object[1]
			// List list =
			// progListDetailManager.getProgListDetailsByDateAndDefcatseqAndOper(
			// date,
			// defcatseq,
			// operator
			// );
			List list = progListDetailManager
					.getProgListDetailsByDateAndDefcatseqAndOperWithoutProgPackage(
							date, defcatseq, operator);
			if (list != null) {
				List<ProgListDetail> progListDetails = new ArrayList<ProgListDetail>();
				List<String> actName = new ArrayList<String>();
				List<String> states = new ArrayList<String>(); // 节目包状态（0导入1缓存库2加扰库3播控库）
				List<String> dealStates = new ArrayList<String>(); // 处理状态（0未处理1处理8失败9成功）

				for (int i = 0; i < list.size(); i++) {
					ProgListDetail progListDetail = (ProgListDetail) list
							.get(i);
					// ProgListDetail progListDetail = (ProgListDetail)rows[0];
					// ProgPackage progPackage = (ProgPackage)rows[1];
					// FlowAction flowAction = (FlowAction)rows[2];

					Long state = (long) -1;
					Long dealState = (long) -1;

					// 查询节目包
					ProgPackage progPackage = (ProgPackage) progPackageManager
							.getById(progListDetail.getProductid());
					if (progPackage != null) {
						state = progPackage.getState();
						dealState = progPackage.getDealstate();
					}

					progListDetails.add(progListDetail);
					// actName.add(flowAction.getActionname());
					states
							.add(getNameById("ProgPackageState", state
									.toString()));
					dealStates.add(getNameById("ProgPackageDealState",
							dealState.toString()));
				}

				retlist.add(progListDetails);
				// retlist.add(actName);
				retlist.add(states);
				retlist.add(dealStates);

				// 在查询栏目单的时候，判断栏目单总表分表里是否有记录，如果没有记录，就添加；如果有，就不添加
				// checkProgListMang(date, defcatseq, operator);
				checkProgListMang(date, "", operator);
			} else {
				String str = "返回为空。";
				cmsLog.warn(str);
				cmsResultDto.setResultCode(Long.valueOf(0));
				cmsResultDto.setErrorMessage(str);
			}
			cmsResultDto.setResultObject(retlist);
		} catch (Exception e) {
			String str = "异常：" + e.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListDetailsByDateAndDefcatseq returns.");
		return cmsResultDto;
	}

	// 20091226
	// ------------- 新一轮的修改开始了，来吧 -----------------------
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

	// 在查询栏目单的时候，判断栏目单总表分表里是否有记录，如果没有记录，就添加；如果有，就不添加
	private int checkProgListMang(String date, String defcatseq, String operator) {
		cmsTransactionManager.saveCheckProgListMang(portalColumnManager,
				progListMangManager, progListMangDetailManager, date,
				defcatseq, operator);
		// // 查询总表是否有记录
		// String scheduledate = convertDateToScheduleDate(date);
		// ProgListMang progListMang =
		// (ProgListMang)progListMangManager.getById(scheduledate);
		// if(progListMang == null)
		// {
		// // 无记录
		// progListMang = new ProgListMang();
		// progListMang.setScheduledate(scheduledate);
		// progListMang.setIdAct("FU00000077");
		// progListMang.setInputmanid(operator);
		// progListMang.setInputtime(new Date());
		// progListMangManager.save(progListMang);
		// }
		//			
		// // 查询栏目
		// List portalColumns =
		// portalColumnManager.getLeafPortalColumnsByDefcatseq(defcatseq);
		// if(portalColumns != null && portalColumns.size() > 0)
		// {
		// for(int j = 0; j < portalColumns.size(); j++)
		// {
		// PortalColumn portalColumn = (PortalColumn)portalColumns.get(j);
		//				
		// // 判断栏目是否有效
		// if(portalColumn.getValidFlag() != 0)
		// {
		// // 不是有效的栏目，忽略
		// continue;
		// }
		//				
		// // 根据scheduleDate和Columnclassid，查询得到progListMangDetail
		// List progListMangDetails =
		// progListMangDetailManager.getProgListMangDetailsByScheduleDateAndColumnclassid(scheduledate,
		// portalColumn.getColumnclassid());
		//				
		// if(progListMangDetails == null || progListMangDetails.size() <= 0)
		// {
		// ProgListMangDetail progListMangDetail = new ProgListMangDetail();
		// progListMangDetail.setScheduledate(scheduledate);
		// progListMangDetail.setColumnclassid(portalColumn.getColumnclassid());
		//					
		// progListMangDetail.setInputmanid(operator);
		// progListMangDetail.setInputtime(new Date());
		// if(portalColumn.getCountnumb() == 0)
		// {
		// // 本地
		// progListMangDetail.setState2(Long.valueOf(1));
		// progListMangDetail.setIdAct("FU00000077");
		// }
		// else
		// {
		// progListMangDetail.setState2(Long.valueOf(0));
		// progListMangDetail.setIdAct("FU00000080");
		// }
		// progListMangDetailManager.save(progListMangDetail);
		// }
		// else
		// {
		// // 如果已经存在，检查本地字段，
		// ProgListMangDetail progListMangDetail =
		// (ProgListMangDetail)progListMangDetails.get(0);
		//					
		// if(portalColumn.getCountnumb() == 0)
		// {
		// // 本地
		// if(progListMangDetail.getState2() == null ||
		// progListMangDetail.getState2() != 1)
		// {
		// progListMangDetail.setState2((long)1);
		// progListMangDetailManager.update(progListMangDetail);
		// }
		// }
		// else
		// {
		// // 非本地
		// if(progListMangDetail.getState2() == null ||
		// progListMangDetail.getState2() != 0)
		// {
		// progListMangDetail.setState2((long)0);
		// progListMangDetailManager.update(progListMangDetail);
		// }
		// }
		// }
		// }
		// }
		return 0;
	}

	// 20100201 13:31
	// 判断总单管理中的发送，是否允许发送
	private boolean checkSendProgListMangDetails(String date, // 日期，格式：yyyy-MM-dd
			String currentIdAct, // 当前活动编号
			String defcatseq // 栏目的代码序列
	) {
		// 说明：
		// FU00000080 数据导入
		// FU00000081 文件加扰
		// FU00000082 生成页面
		// FU00000083 PORTAL完成
		// FU00000084 预览
		// FU00000085 迁移
		// FU00000086 播发
		// FU00000087 完成
		// 总单的发送，是针对所有栏目的（不仅仅是本地栏目）
		// 界面 总单审校：当前活动（“文件加扰”81），以分表为单位发送。
		// 80-->82 判断栏目（栏目单详细）下所有节目包的state>=2加扰库，才能发送
		// 81-->82 判断栏目（栏目单详细）下所有节目包的state>=2加扰库，才能发送
		// 界面 Portal预览：当前活动（“生成页面”82
		// “PORTAL完成”83），82-->83以分表为单位发送，83-->85压缩portal并以date为单位发送所有栏目分表
		// 82-->83 以栏目分表为单位，生成portal后，发送
		// 83-->85 以date总表为单位，判断是否所有分表活动都是83，是才能压缩并发送所有栏目分表到85
		// 界面 编单迁移：当前活动（“迁移”85），以date栏目单总表为单位发送，
		// 85-->86 判断是否所有栏目单分表对应所有栏目单详细对应所有节目包的state==3，是才能发送
		// 界面 编单播发：当前活动（“播发”86），以date栏目单总表为单位发送，
		// 86-->87 播放单生成成功，发送

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> checkSendProgListMangDetails...");

		boolean allowSend = true;

		// 首先判断数据一致性，当前活动和分表的当前活动是否一致

		// 判断是否可以发送到下一活动
		if (currentIdAct.equalsIgnoreCase("FU00000081")
				|| currentIdAct.equalsIgnoreCase("FU00000080")) {
			// 界面 总单审校：当前活动（“文件加扰”81），以分表为单位发送。
			// 80-->82 判断栏目（栏目单详细）下所有节目包的state>=2加扰库，才能发送
			// 81-->82 判断栏目（栏目单详细）下所有节目包的state>=2加扰库，才能发送
			List pcs = portalColumnManager
					.getValidLeafPortalColumnsByDefcatseq(defcatseq);
			for (int i = 0; i < pcs.size(); i++) {
				PortalColumn portalColumn = (PortalColumn) pcs.get(i);
				// 返回：List<Object[]>
				// (ProgListDetail)Object[0]
				// (ProgPackage)Object[1]
				List plds = progListDetailManager
						.getProgListDetailsByScheduledateAndColumnclassid(date,
								portalColumn.getColumnclassid());

				// 判断所有节目包的state是否都 >= 2
				for (int j = 0; j < plds.size(); j++) {
					Object[] rows = (Object[]) plds.get(j);
					ProgListDetail progListDetail = (ProgListDetail) rows[0];
					ProgPackage progPackage = (ProgPackage) rows[1];

					if (!(progPackage.getState() >= 2)) {
						allowSend = false;
						cmsLog.info("栏目分表对应的节目包中，存在节目包状态<2的，不能发送到下一活动。节目包ID："
								+ progPackage.getProductid());
						break;
					}
				}

				if (allowSend == false) {
					break;
				}
			}
		} else if (currentIdAct.equalsIgnoreCase("FU00000082")) {
			// 界面 Portal预览：当前活动（“生成页面”82
			// “PORTAL完成”83），82-->83以分表为单位发送，83-->85压缩portal并以date为单位发送所有栏目分表
			// 82-->83 以栏目分表为单位，生成portal后，发送
			// 判断栏目是否已经生成portal，如果是才能发送

		} else if (currentIdAct.equalsIgnoreCase("FU00000083")) {
			// 界面 Portal预览：当前活动（“生成页面”82
			// “PORTAL完成”83），82-->83以分表为单位发送，83-->85压缩portal并以date为单位发送所有栏目分表
			// 83-->85 以date总表为单位，判断是否所有分表活动都是83，是才能压缩并发送所有栏目分表到85
			String scheduledate = convertDateToScheduleDate(date);
			// 根据栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
			// 返回：List<Object[]>
			// (ProgListMangDetail)Object[0]
			// (PortalColumn)Object[1]
			// (FunResource)Object[2]
			List list = progListMangDetailManager
					.getProgListMangDetailsByScheduledate(scheduledate);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] rows = (Object[]) list.get(i);
					ProgListMangDetail plmd = (ProgListMangDetail) rows[0];
					if (!plmd.getIdAct().equalsIgnoreCase(currentIdAct)) // “PORTAL完成”
					{
						allowSend = false;
						cmsLog
								.info("不是所有栏目单分表都是“PORTAL完成”FU00000083活动，不能发送到下一活动。日期："
										+ date);
						break;
					}
				}
			} else {
				allowSend = false;
				cmsLog.info("未查询到栏目单分表记录，不能发送到下一活动。日期：" + date);
			}
		} else if (currentIdAct.equalsIgnoreCase("FU00000085")) {
			// 界面 编单迁移：当前活动（“迁移”85），以date栏目单总表为单位发送，
			// 85-->86 判断是否所有栏目单分表对应所有栏目单详细对应所有节目包的state==3，是才能发送
			List pcs = portalColumnManager
					.getValidLeafPortalColumnsByDefcatseq(defcatseq);
			for (int i = 0; i < pcs.size(); i++) {
				PortalColumn portalColumn = (PortalColumn) pcs.get(i);
				// 返回：List<Object[]>
				// (ProgListDetail)Object[0]
				// (ProgPackage)Object[1]
				List plds = progListDetailManager
						.getProgListDetailsByScheduledateAndColumnclassid(date,
								portalColumn.getColumnclassid());

				// 判断所有节目包的state是否都 == 3
				for (int j = 0; j < plds.size(); j++) {
					Object[] rows = (Object[]) plds.get(j);
					ProgListDetail progListDetail = (ProgListDetail) rows[0];
					ProgPackage progPackage = (ProgPackage) rows[1];

					if (progPackage.getProgtype().equalsIgnoreCase("V")) {
						if (!(progPackage.getState() == 3)) {
							allowSend = false;
							cmsLog
									.info("栏目分表对应的节目包中，存在节目包状态不为3的，不能发送到下一活动。节目包ID："
											+ progPackage.getProductid());
							break;
						}
					}
				}

				if (allowSend == false) {
					break;
				}
			}
		} else if (currentIdAct.equalsIgnoreCase("FU00000086")) {
			// 界面 编单播发：当前活动（“播发”86），以date栏目单总表为单位发送，
			// 86-->87 播放单生成成功，发送
			String scheduledate = convertDateToScheduleDate(date);
			// 根据栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
			// 返回：List<Object[]>
			// (ProgListMangDetail)Object[0]
			// (PortalColumn)Object[1]
			// (FunResource)Object[2]
			List list = progListMangDetailManager
					.getProgListMangDetailsByScheduledate(scheduledate);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] rows = (Object[]) list.get(i);
					ProgListMangDetail plmd = (ProgListMangDetail) rows[0];
					if (!plmd.getIdAct().equalsIgnoreCase(currentIdAct)) // “PORTAL完成”
					{
						allowSend = false;
						cmsLog
								.info("不是所有栏目单分表都是“PORTAL完成”FU00000083活动，不能发送到下一活动。日期："
										+ date);
						break;
					}
				}
			} else {
				allowSend = false;
				cmsLog.info("未查询到栏目单分表记录，不能发送到下一活动。日期：" + date);
			}
		} else if (currentIdAct.equalsIgnoreCase("FU00000087")) {
			// 不需要发送
			allowSend = false;
			cmsLog.info("当前活动是“完成”，不能发送到一下活动。当前活动：" + currentIdAct);
		}

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> checkSendProgListMangDetails returns.");
		return allowSend;
	}

	// 在发送的时候，不修改栏目单详细里的活动，修改栏目单分表的活动，同时需要判断总表是否需要修改活动
	// 发送栏目单（详细），到下一活动idProcess，下一活动人，
	// 目前，界面不选择下一活动和下一活动人
	public CmsResultDto sendProgListMangDetails(String date, // 日期，格式：yyyy-MM-dd
			// List<PortalColumn> portalColumns, // 需要发送的栏目单详细
			String defcatseq, // 栏目的代码序列
			// String nextIdAct, // 不使用，下一活动编号，
			// String nextOperator, // 不使用，下一操作人员，不使用
			// String nextState2, // 不使用，下一活动的性质：（新建N 回退R 顺序P 完成C）
			String currentIdAct, // 当前活动编号
			String operator, // 当前操作人员
			String sendremark // 备注
	) {
		// 说明：
		// FU00000081 文件加扰
		// FU00000082 生成页面
		// FU00000083 PORTAL完成
		// FU00000084 预览
		// FU00000085 迁移
		// FU00000086 播发
		// FU00000087 完成
		// 总单的发送，是针对所有栏目的（不仅仅是本地栏目）
		// 界面 总单审校：当前活动（“文件加扰”81），以分表为单位发送。
		// 81-->82 判断栏目（栏目单详细）下所有节目包的state>=2加扰库，才能发送
		// 界面 Portal预览：当前活动（“生成页面”82
		// “PORTAL完成”83），82-->83以分表为单位发送，83-->85压缩portal并以date为单位发送所有栏目分表
		// 82-->83 以栏目分表为单位，生成portal后，发送
		// 83-->85 以date总表为单位，判断是否所有分表活动都是83，是才能压缩并发送所有栏目分表到85
		// 界面 编单迁移：当前活动（“迁移”85），以date栏目单总表为单位发送，
		// 85-->86 判断是否所有栏目单分表对应所有栏目单详细对应所有节目包的state==3，是才能发送
		// 界面 编单播发：当前活动（“播发”86），以date栏目单总表为单位发送，
		// 86-->87 播放单生成成功，发送

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> sendProgListMangDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
		String scheduleDate = convertDateToScheduleDate(date);

		boolean allowSend = true;

		// 判断是否可以发送到下一活动
		allowSend = checkSendProgListMangDetails(date, // 日期，格式：yyyy-MM-dd
				currentIdAct, // 当前活动编号
				defcatseq // 栏目的代码序列
		);

		cmsLog.info("判断是否可以发送到下一活动: " + allowSend);

		// 查询下一活动nextIdAct，条件：State2=P，FLOWACTIVITYIDPARENT=currentIdAct
		List flowActivityOrders = flowActivityOrderManager
				.getNextIdActsByCurrentIdActAndState2(currentIdAct, "P");

		if (allowSend == true && flowActivityOrders.size() > 0) {
			FlowActivityOrder flowActivityOrder = (FlowActivityOrder) flowActivityOrders
					.get(0);
			cmsLog.info("查询下一活动, 查询数据库得出下一个活动编号: "
					+ flowActivityOrder.getFlowactivityidchild());

			cmsResultDto = cmsTransactionManager.updateProgListMangDetails(
					progListMangManager, progListMangDetailManager,
					bpmcManager, flowActivityOrderManager, scheduleDate, // 主表的主键
					defcatseq, // 栏目的代码序列
					flowActivityOrder.getFlowactivityidchild(), // 下一活动编号
					"", // 下一操作人员
					"P", // 下一活动的性质：（新建N 回退R 顺序P 完成C）
					currentIdAct, // 当前活动编号
					operator, // 当前操作人员
					sendremark // 备注
					);

			cmsLog.info("修改编单活动结果: " + cmsResultDto.getResultCode());

			/**
			 * 这是传说中的, 将下一个活动编号. 纯粹为了返回当前活动的下一个活动. 没啥用处
			 */
			cmsResultDto.setErrorDetail(flowActivityOrder
					.getFlowactivityidchild());

			cmsLog.info("活动发送成功, 保存操作记录!");
			Bpmc bpmc = new Bpmc();
			Date d = new Date();
			bpmc.setObjectid(convertDateToScheduleDate(date));
			bpmc.setCreatedate(d);
			bpmc.setSends(currentIdAct);
			bpmc.setReceives(cmsResultDto.getErrorDetail());
			bpmc.setSendremark(sendremark);
			bpmc.setState("P");
			bpmc.setCreater(operator);
			bpmc.setSender(operator);
			bpmc.setSenddate(d);

			bpmcManager.save(bpmc);
		} else {
			// 返回失败
			String str = "不符合发送规则，不能发送到下一活动。";
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		}

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> sendProgListMangDetails returns.");
		return cmsResultDto;
	}

	// 在回退的时候，修改栏目单分表的活动为上一活动，同时需要判断总表是否需要修改活动
	// 回退栏目单（详细），到上一活动idProcess，上一活动人，
	// 目前，界面不选择下一活动和下一活动人
	public CmsResultDto backProgListMangDetails(String date, // 日期，格式：yyyy-MM-dd
			// List<PortalColumn> portalColumns, // 需要发送的栏目单详细
			String defcatseq, // 栏目的代码序列
			// String nextIdAct, // 不使用，下一活动编号
			// String nextOperator, // 不使用，下一操作人员
			// String nextState2, // 不使用，下一活动的性质：（新建N 回退R 顺序P 完成C）
			String currentIdAct, // 当前活动编号
			String operator, // 当前操作人员
			String sendremark // 备注
	) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> backProgListMangDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
		String scheduleDate = convertDateToScheduleDate(date);

		// 查询下一活动nextIdAct，条件：State2=P，FLOWACTIVITYIDPARENT=currentIdAct
		List flowActivityOrders = flowActivityOrderManager
				.getNextIdActsByCurrentIdActAndState2(currentIdAct, "R");

		if (flowActivityOrders.size() > 0) {
			FlowActivityOrder flowActivityOrder = (FlowActivityOrder) flowActivityOrders
					.get(0);

			cmsResultDto = cmsTransactionManager.updateProgListMangDetails(
					progListMangManager, progListMangDetailManager,
					bpmcManager, flowActivityOrderManager, scheduleDate, // 主表的主键
					defcatseq, // 栏目的代码序列
					flowActivityOrder.getFlowactivityidchild(), // 下一活动编号
					"", // 下一操作人员
					"R", // 下一活动的性质：（新建N 回退R 顺序P 完成C）
					currentIdAct, // 当前活动编号
					operator, // 当前操作人员
					sendremark // 备注
					);
		}

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> backProgListMangDetails returns.");
		return cmsResultDto;
	}

	// 20100121 14:20
	// 在发送的时候，不修改栏目单详细里的活动，修改栏目单分表的活动，同时需要判断总表是否需要修改活动
	// 发送本地栏目单（详细），到下一活动idProcess，下一活动人，
	// 目前，界面不选择下一活动和下一活动人
	public CmsResultDto sendLocalProgListMangDetails(String date, // 日期，格式：yyyy-MM-dd
			// List<PortalColumn> portalColumns, // 需要发送的栏目单详细
			String defcatseq, // 栏目的代码序列
			// String nextIdAct, // 不使用，下一活动编号，
			// String nextOperator, // 不使用，下一操作人员，不使用
			// String nextState2, // 不使用，下一活动的性质：（新建N 回退R 顺序P 完成C）
			String currentIdAct, // 当前活动编号
			String operator, // 当前操作人员
			String sendremark // 备注
	) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> sendProgListMangDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
		String scheduleDate = convertDateToScheduleDate(date);

		// 查询下一活动nextIdAct，条件：State2=P，FLOWACTIVITYIDPARENT=currentIdAct
		List flowActivityOrders = flowActivityOrderManager
				.getNextIdActsByCurrentIdActAndState2(currentIdAct, "P");

		if (flowActivityOrders.size() > 0) {
			FlowActivityOrder flowActivityOrder = (FlowActivityOrder) flowActivityOrders
					.get(0);

			if (currentIdAct.equalsIgnoreCase("FU00000079")) {
				cmsLog.info("编单完成(FU00000079)-->完成(FU00000087)");
				flowActivityOrder.setFlowactivityidchild("FU00000087");
			}

			cmsLog.info("发送编单分表活动：" + currentIdAct + "-->"
					+ flowActivityOrder.getFlowactivityidchild());

			cmsResultDto = cmsTransactionManager
					.updateLocalProgListMangDetails(progListMangManager,
							progListMangDetailManager, bpmcManager,
							flowActivityOrderManager, scheduleDate, // 主表的主键
							defcatseq, // 栏目的代码序列
							flowActivityOrder.getFlowactivityidchild(), // 下一活动编号
							"", // 下一操作人员
							"P", // 下一活动的性质：（新建N 回退R 顺序P 完成C）
							currentIdAct, // 当前活动编号
							operator, // 当前操作人员
							sendremark // 备注
					);
			
			cmsLog.info("活动发送成功, 保存操作记录!");
			Bpmc bpmc = new Bpmc();
			Date d = new Date();
			bpmc.setObjectid(scheduleDate);
			bpmc.setCreatedate(d);
			bpmc.setSends(currentIdAct);
			bpmc.setReceives(flowActivityOrder.getFlowactivityidchild());
			bpmc.setSendremark(sendremark);
			bpmc.setState("P");
			bpmc.setCreater(operator);
			bpmc.setSender(operator);
			bpmc.setSenddate(d);

			bpmcManager.save(bpmc);
		}

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> sendProgListMangDetails returns.");
		return cmsResultDto;
	}

	// 20100121 14:20
	// 在回退的时候，修改栏目单分表的活动为上一活动，同时需要判断总表是否需要修改活动
	// 回退本地栏目单（详细），到上一活动idProcess，上一活动人，
	// 目前，界面不选择下一活动和下一活动人
	public CmsResultDto backLocalProgListMangDetails(String date, // 日期，格式：yyyy-MM-dd
			// List<PortalColumn> portalColumns, // 需要发送的栏目单详细
			String defcatseq, // 栏目的代码序列
			// String nextIdAct, // 不使用，下一活动编号
			// String nextOperator, // 不使用，下一操作人员
			// String nextState2, // 不使用，下一活动的性质：（新建N 回退R 顺序P 完成C）
			String currentIdAct, // 当前活动编号
			String operator, // 当前操作人员
			String sendremark // 备注
	) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> backLocalProgListMangDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
		String scheduleDate = convertDateToScheduleDate(date);

		// 查询下一活动nextIdAct，条件：State2=P，FLOWACTIVITYIDPARENT=currentIdAct
		List flowActivityOrders = flowActivityOrderManager
				.getNextIdActsByCurrentIdActAndState2(currentIdAct, "R");

		if (flowActivityOrders.size() > 0) {
			FlowActivityOrder flowActivityOrder = (FlowActivityOrder) flowActivityOrders
					.get(0);

			cmsResultDto = cmsTransactionManager
					.updateLocalProgListMangDetails(progListMangManager,
							progListMangDetailManager, bpmcManager,
							flowActivityOrderManager, scheduleDate, // 主表的主键
							defcatseq, // 栏目的代码序列
							flowActivityOrder.getFlowactivityidchild(), // 下一活动编号
							"", // 下一操作人员
							"R", // 下一活动的性质：（新建N 回退R 顺序P 完成C）
							currentIdAct, // 当前活动编号
							operator, // 当前操作人员
							sendremark // 备注
					);
			
			cmsLog.info("活动发送成功, 保存操作记录!");
			Bpmc bpmc = new Bpmc();
			Date d = new Date();
			bpmc.setObjectid(scheduleDate);
			bpmc.setCreatedate(d);
			bpmc.setSends(currentIdAct);
			bpmc.setReceives(flowActivityOrder.getFlowactivityidchild());
			bpmc.setSendremark(sendremark);
			bpmc.setState("R");
			bpmc.setCreater(operator);
			bpmc.setSender(operator);
			bpmc.setSenddate(d);

			bpmcManager.save(bpmc);
		}

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> backLocalProgListMangDetails returns.");
		return cmsResultDto;
	}

	// ProgPackageServiceImpl也有此方法，以后做调整
	private int createXMLFile(String strFileName, String strXml) {
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> createXMLFile...");
		int ireturn = -1;

		// FileOperationImpl fileopr = new FileOperationImpl();

		ireturn = fileopr.createSmbFile(strFileName, strXml);
		// try
		// {
		// SmbFile file = new SmbFile(strFileName);
		// SmbFileOutputStream fileStream = new SmbFileOutputStream(file, true);
		// byte[] bytes = new byte[strXml.length()];
		// bytes = strXml.getBytes("utf-8");
		// fileStream.write(bytes, 0, bytes.length);
		// fileStream.flush();
		//
		// ireturn = 0;
		// cmsLog.info("Create xml file successfully:" + strFileName);
		// }
		// catch(IOException ex)
		// {
		// ireturn = 1;
		// cmsLog.info("Create xml file unsuccessfully:" + strFileName);
		// cmsLog.info(ex.getMessage());
		// }

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> createXMLFile returns.");
		return ireturn;
	}

	// 查询XML需要上传的目标地址
	private String getXmlPath(String storageclass, String fileType) {
		String strXmlPath = "";
		String storageIP;
		String loginName;
		String loginPwd;
		String mapPath;
		String mapLoginUid;
		String mapLoginPwd;
		String mapLoginDisk;

		Object[] maxdir = amsstoragedirManager
				.findstorageanddirlistbystorageclass(storageclass, fileType);
		if (maxdir != null && maxdir.length >= 10) {
			storageIP = (String) (maxdir[2]);
			loginName = (String) (maxdir[4]);
			loginPwd = (String) (maxdir[5]);
			mapPath = (String) (maxdir[6]);
			mapLoginUid = (String) (maxdir[7]);
			mapLoginPwd = (String) (maxdir[8]);
			mapLoginDisk = (String) (maxdir[9]);

			strXmlPath = "smb://";
			strXmlPath += loginName;
			strXmlPath += ":";
			strXmlPath += loginPwd;
			strXmlPath += "@";
			strXmlPath += storageIP;
			strXmlPath += "/";
			strXmlPath += mapPath;
			strXmlPath += "/";
		} else {
			// 未查询到
			cmsLog.info("查询maxdir失败。");
			strXmlPath = "";
		}
		return strXmlPath;
	}

	// 删除smb文件
	private int deleteSmbFile(String strFile) {
		// 删除文件
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> deleteSmbFile...");
		int ireturn = -1;
		// FileOperationImpl fileopr = new FileOperationImpl();

		ireturn = fileopr.deleteSmbFile(strFile);
		// try
		// {
		// SmbFile file = new SmbFile(strFile);
		// file.delete();
		//
		// ireturn = 0;
		// cmsLog.info("Delete file successfully: " + strFile);
		// }
		// catch(IOException ex)
		// {
		// ireturn = 1;
		// cmsLog.info("Delete file unsuccessfully: " + strFile);
		// cmsLog.info(ex.getMessage());
		// }

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> deleteSmbFile returns.");
		return ireturn;
	}

	// 20100125 18:03
	// 发布用生成xml(本地有效的栏目)，
	public CmsResultDto generatePortalXmlForLocal(String date, // 日期，格式：yyyy-MM-dd
			String operator // 当前操作人员
	) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> generatePortalXmlForLocal...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 配置文件，获取
		String filecodePortalXml = "CLXML";
		String stclasscode = "BjOnline";
		String progPackagePath = "progpackage";
		String progListPath = "proglist";

		cmsResultDto = cmsTransactionManager.saveGeneratePortalXmlForLocal(
				portalColumnManager, progListDetailManager,
				packageFilesManager, progListMangManager, progListFileManager,
				ppSrvPdtRelManager, productCategoryManager,
				progListMangDetailManager, flowActivityOrderManager,
				bpmcManager, filecodePortalXml, stclasscode, date,
				progPackagePath, progListPath, operator);

		// // 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
		// String scheduledate = convertDateToScheduleDate(date);
		//		
		// // 根据scheduledate，查询TPROGLISTMANG，得到proglistmang是否是“发布”活动
		// ProgListMang progListMang =
		// (ProgListMang)progListMangManager.getById(scheduledate);
		//		
		// if(progListMang.getIdAct().equalsIgnoreCase("FU00000079"))
		// {
		// // 如果是发布活动，继续
		// // 查询PortalColumn表，得到发布portalcolumn
		// List pcs = portalColumnManager.findByProperty("ispublish", (long)0);
		// if(pcs != null && pcs.size() > 0)
		// {
		// for(int i = 0; i < pcs.size(); i++)
		// {
		// // 需要发布的栏目，每个需要发布的栏目生成一个xml文件
		// PortalColumn pc = (PortalColumn)pcs.get(i);
		//					
		// if(pc.getValidFlag() != 0 || pc.getCountnumb() != 0)
		// {
		// // 不是有效的本地栏目，忽略
		// continue;
		// }
		//
		// // 如果发布
		// String portalXml;
		// portalXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		// portalXml += "<root>";
		//					
		// // 根据defcatseq，查询PortalColumn表，得到所有叶子节点portalcolumn
		// List leafPcs =
		// portalColumnManager.getLeafPortalColumnsByDefcatseq(pc.getDefcatseq());
		//					
		// for(int j = 0; j < leafPcs.size(); j++)
		// {
		// // 需要发布的栏目下的叶子节点栏目
		// PortalColumn leafPc = (PortalColumn)leafPcs.get(j);
		//						
		// if(leafPc.getValidFlag() != 0 || leafPc.getCountnumb() != 0)
		// {
		// // 不是有效的本地栏目，忽略
		// continue;
		// }
		//						
		// //
		// 根据scheduledate和portalcolumn，查询TPROGLISTDETAIL，得到proglistdetail和progpackage
		// List retlist =
		// progListDetailManager.getProgListDetailsByScheduledateAndColumnclassid(
		// date,
		// leafPc.getColumnclassid() // 怀疑这里参数有误，pc.getColumnclassid()
		// );
		//							
		// // 根据proglistdetail和progpackage，生成xml
		// portalXml += "<column DEFCATCODE=\"";
		// portalXml += leafPc.getDefcatcode();
		// portalXml += "\" name=\"";
		// portalXml += leafPc.getColumnname();
		// portalXml += "\" COUNTNUMB=\"";
		// portalXml += retlist.size();
		// portalXml += "\" COUNTONEPAGE=\"";
		// portalXml += leafPc.getCountonepage();
		// portalXml += "\">";
		//						
		// for(int k = 0; k < retlist.size(); k++)
		// {
		// Object[] rows = (Object[])retlist.get(k);
		// ProgListDetail progListDetail = (ProgListDetail)rows[0];
		// ProgPackage progPackage = (ProgPackage)rows[1];
		//							
		// String ppXml = progPackage.getPpxml();
		// ppXml = ppXml.replaceFirst("\\<\\?xml version=\"1.0\"
		// encoding=\"UTF-8\" \\?\\>", "");
		// ppXml = ppXml.replaceFirst("\\<\\?xml version=\"1.0\"
		// encoding=\"UTF-8\"\\?\\>", "");
		//							
		// portalXml += ppXml;
		// }
		//						
		// portalXml += "</column>";
		// }
		// portalXml += "</root>";
		//
		// // 调用方法2，得到xml目前存储路径
		// // 返回：List
		// // 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
		// // 2 - List<Object[]>
		// // (AmsStorage)Object[0]
		// // (AmsStorageDir)Object[1]
		// // (AmsStorageClass)Object[2]
		// String strXmlFullPath = ""; // xml目标存放全路径，含文件名和后缀
		// String strXmlPath = ""; // xml目标存放路径
		// String strXmlName = ""; // xml名字
		// String filepath = ""; // 节目包ID前10位
		// List destpaths =
		// packageFilesManager.getDestPathByFilecodeStclasscode(
		// filecodePortalXml,
		// stclasscodeNearOnline
		// );
		// if(destpaths == null || destpaths.size() < 2)
		// {
		// cmsLog.info("获取节目包xml目标存放路径失败。");
		// return null;
		// }
		// strXmlPath = (String)destpaths.get(0);
		// List rowlist = (List)destpaths.get(1);
		// Object[] rows1 = (Object[])rowlist.get(0);
		// AmsStorage amsst = (AmsStorage)rows1[0];
		// AmsStorageDir amsstdir = (AmsStorageDir)rows1[1];
		// AmsStorageClass amsstc = (AmsStorageClass)rows1[2];
		//					
		// // // 查询XML需要上传的目标地址
		// // String strXmlPath = "";
		// // String strXmlName = "";
		// // String strXmlFullPath = "";
		// // strXmlPath = getXmlPath("NearOnline", "xml");
		// strXmlName = pc.getDefcatcode() + ".xml"; // 栏目code.xml
		// strXmlFullPath = strXmlPath + strXmlName;
		// cmsLog.info("New xml file:" + strXmlFullPath);
		//					
		// //????????????????????????????????????????????????????????????????????????????????????????
		// // 找到原来文件的位置
		// // 判断strXmlFullPath文件是否存在，
		// // 如果存在，删除原来文件strXmlFullPath
		// // deleteSmbFile(strXmlFullPath);
		//					
		// // 生成XML文件，以节目包ID命名
		// // 上传XML文件，？？是否上一步直接生成XML文件到上传的目标地址
		// if(createXMLFile(strXmlFullPath, portalXml) != 0)
		// {
		// // 写xml文件失败
		// cmsLog.info("写xml文件失败。");
		// return null;
		// }
		// else
		// {
		// cmsLog.info("写xml文件成功，保存progListFile。");
		// // 保存portalXml到TPROGLISTFILE
		// ProgListFile progListFile = new ProgListFile();
		// progListFile.setColumnclassid(pc.getColumnclassid());
		// progListFile.setScheduledate(scheduledate);
		// progListFile.setColumnxml(portalXml);
		// progListFile.setFiletype(Long.valueOf(0));
		// progListFile.setState1(Long.valueOf(0)); // 状态(0有效1无效)
		// progListFile.setInputtime(new Date());
		// progListFile.setInputmanid(operator);
		// progListFile.setFilename(strXmlName);
		// progListFileManager.save(progListFile);
		//						
		// // 发送栏目单分表记录
		// for(int j = 0; j < leafPcs.size(); j++)
		// {
		// // 需要发布的栏目下的叶子节点栏目
		// PortalColumn leafPc = (PortalColumn)leafPcs.get(j);
		//							
		// if(leafPc.getValidFlag() != 0 || leafPc.getCountnumb() != 0)
		// {
		// // 不是有效的本地栏目，忽略
		// continue;
		// }
		//							
		// // 发送栏目单分表到下一活动 FU00000081
		// sendLocalProgListMangDetails(date, leafPc.getDefcatseq(),
		// "FU00000079", "", "");
		//							
		// //
		// 根据scheduledate和portalcolumn，查询TPROGLISTDETAIL，得到proglistdetail和progpackage
		// List retlist =
		// progListDetailManager.getProgListDetailsByScheduledateAndColumnclassid(
		// date,
		// leafPc.getColumnclassid()
		// );
		// for(int k = 0; k < retlist.size(); k++)
		// {
		// Object[] rows = (Object[])retlist.get(k);
		// ProgListDetail progListDetail = (ProgListDetail)rows[0];
		// ProgPackage progPackage = (ProgPackage)rows[1];
		//								
		// // 查询节目包对应的产品列表（CA产品）
		// String keyids =
		// getCaProductListOfProgPackage(progPackage.getProductid());
		// progListDetail.setETitle(keyids);
		// // progListDetail.setState1((long)1);
		// progListDetailManager.update(progListDetail);
		// }
		// cmsLog.info("这里没有使用事务处理，有待优化。");
		// }
		// }
		// }
		// }
		// }
		// else
		// {
		// // 如果不是发布活动，返回
		// String str = "栏目单当前活动不是“发布”。";
		// cmsLog.info(str);
		// cmsResultDto.setResultCode(Long.valueOf(1));
		// cmsResultDto.setErrorMessage(str);
		// }

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> generatePortalXmlForLocal returns.");
		return cmsResultDto;
	}

	// 发布用生成xml，20091230 13:20
	public CmsResultDto generatePortalXml(String date, // 编单的日期，格式：yyyy-MM-dd
			String operator // 当前操作人员
	) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> generatePortalXml...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsResultDto = generatePortalXmlForLocal(date, operator);

		// // 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
		// String scheduledate = convertDateToScheduleDate(date);
		//		
		// // 根据scheduledate，查询TPROGLISTMANG，得到proglistmang是否是“发布”活动
		// ProgListMang progListMang =
		// (ProgListMang)progListMangManager.getById(scheduledate);
		//		
		// if(progListMang.getIdAct().equalsIgnoreCase("FU00000079"))
		// {
		// // 如果是发布活动，继续
		// // 查询PortalColumn表，得到发布portalcolumn
		// List pcs = portalColumnManager.findByProperty("ispublish",
		// Long.valueOf(1));
		// if(pcs != null && pcs.size() > 0)
		// {
		// for(int i = 0; i < pcs.size(); i++)
		// {
		// // 需要发布的栏目，每个需要发布的栏目生成一个xml文件
		// PortalColumn pc = (PortalColumn)pcs.get(i);
		//
		// // 如果发布
		// String portalXml;
		// portalXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		// portalXml += "<root>";
		//					
		// // 根据defcatseq，查询PortalColumn表，得到所有叶子节点portalcolumn
		// List leafPcs =
		// portalColumnManager.getLeafPortalColumnsByDefcatseq(pc.getDefcatseq());
		//					
		// for(int j = 0; j < leafPcs.size(); j++)
		// {
		// // 需要发布的栏目下的叶子节点栏目
		// PortalColumn leafPc = (PortalColumn)leafPcs.get(j);
		//						
		// //
		// 根据scheduledate和portalcolumn，查询TPROGLISTDETAIL，得到proglistdetail和progpackage
		// List retlist =
		// progListDetailManager.getProgListDetailsByScheduledateAndColumnclassid(date,
		// pc.getColumnclassid());
		//							
		// // 根据proglistdetail和progpackage，生成xml
		// portalXml += "<column DEFCATCODE=\"";
		// portalXml += leafPc.getDefcatcode();
		// portalXml += "\" name=\"";
		// portalXml += leafPc.getColumnname();
		// portalXml += "\" COUNTNUMB=\"";
		// portalXml += retlist.size();
		// portalXml += "\" COUNTONEPAGE=\"";
		// portalXml += leafPc.getCountonepage();
		// portalXml += "\">";
		//						
		// for(int k = 0; k < retlist.size(); k++)
		// {
		// Object[] rows = (Object[])retlist.get(k);
		// ProgListDetail progListDetail = (ProgListDetail)rows[0];
		// ProgPackage progPackage = (ProgPackage)rows[1];
		//							
		// String ppXml = progPackage.getPpxml();
		// ppXml = ppXml.replaceFirst("\\<\\?xml version=\"1.0\"
		// encoding=\"UTF-8\" \\?\\>", "");
		// ppXml = ppXml.replaceFirst("\\<\\?xml version=\"1.0\"
		// encoding=\"UTF-8\"\\?\\>", "");
		//							
		// portalXml += ppXml;
		// }
		//						
		// portalXml += "</column>";
		// }
		// portalXml += "</root>";
		//
		// // 查询XML需要上传的目标地址
		// String strXmlPath = "";
		// String strXmlName = "";
		// String strXmlFullPath = "";
		// strXmlPath = getXmlPath("NearOnline", "xml");
		// strXmlName = pc.getDefcatcode() + ".xml"; // 栏目code.xml
		// strXmlFullPath = strXmlPath + strXmlName;
		// cmsLog.info("New xml file:" + strXmlFullPath);
		//					
		// //????????????????????????????????????????????????????????????????????????????????????????
		// // 找到原来文件的位置
		// // 判断strXmlFullPath文件是否存在，
		// // 如果存在，删除原来文件strXmlFullPath
		// deleteSmbFile(strXmlFullPath);
		//					
		// // 生成XML文件，以节目包ID命名
		// // 上传XML文件，？？是否上一步直接生成XML文件到上传的目标地址
		// if(createXMLFile(strXmlFullPath, portalXml) != 0)
		// {
		// // 写xml文件失败
		// cmsLog.info("写xml文件失败。");
		// return null;
		// }
		// else
		// {
		// cmsLog.info("写xml文件成功，保存progListFile。");
		// // 保存portalXml到TPROGLISTFILE
		// ProgListFile progListFile = new ProgListFile();
		// progListFile.setColumnclassid(pc.getColumnclassid());
		// progListFile.setScheduledate(scheduledate);
		// progListFile.setColumnxml(portalXml);
		// progListFile.setFiletype(Long.valueOf(0));
		// progListFile.setState1(Long.valueOf(0)); // 状态(0有效1无效)
		// progListFileManager.save(progListFile);
		// }
		// }
		// }
		// }
		// else
		// {
		// // 如果不是发布活动，返回
		// String str = "栏目单当前活动不是“发布”。";
		// cmsLog.info(str);
		// cmsResultDto.setResultCode(Long.valueOf(1));
		// cmsResultDto.setErrorMessage(str);
		// }

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> generatePortalXml returns.");
		return cmsResultDto;
	}

	private int getPosInList(String object, List objects) {
		int ret = -1;
		if (objects != null && objects.size() > 0) {
			for (int i = 0; i < objects.size(); i++) {
				String o = (String) objects.get(i);
				if (object.equalsIgnoreCase(o)) {
					ret = i;
					break;
				}
			}
		}

		return ret;
	}

	private List getIdActsFromFirst(String firstIdAct, String nextState2) {
		List acts = new ArrayList();
		List list = null;
		String curIdAct;
		if (nextState2.equalsIgnoreCase("P")) {
			acts.add(firstIdAct);
			curIdAct = firstIdAct;
			do {
				// 根据state2、IdAct，查询得到idact，条件：state2、FLOWACTIVITYIDCHILD=IdAct
				list = new ArrayList();
				list = flowActivityOrderManager
						.getNextIdActsByCurrentIdActAndState2(curIdAct, "P");
				if (list.size() > 0) {
					FlowActivityOrder flowActivityOrder = (FlowActivityOrder) list
							.get(0);
					curIdAct = flowActivityOrder.getFlowactivityidchild();
					acts.add(curIdAct);
				}

			} while (list != null && list.size() > 0);
		} else if (nextState2.equalsIgnoreCase("R")) {
			acts.add(firstIdAct);
			curIdAct = firstIdAct;
			do {
				// 根据state2、IdAct，查询得到idact，条件：state2、FLOWACTIVITYIDCHILD=IdAct
				list = new ArrayList();
				list = flowActivityOrderManager
						.getNextIdActsByCurrentIdActAndState2(curIdAct, "P");
				if (list.size() > 0) {
					FlowActivityOrder flowActivityOrder = (FlowActivityOrder) list
							.get(0);
					curIdAct = flowActivityOrder.getFlowactivityidchild();
					acts.add(curIdAct);
				}

			} while (list != null && list.size() > 0);
		}
		return acts;
	}

	// 根据日期、栏目、当前活动，查询得到栏目单状态，20091231 10:18
	// 返回retStates
	// 0 - 栏目活动 早于 当前活动
	// 1 - 栏目活动 等于 当前活动
	// 2 - 栏目活动 晚于 当前活动
	// 3 - 栏目未初始化
	public CmsResultDto getProgListMangDetails(List<String> dates, // 日期，格式：yyyy-MM-dd
			String defcatseq, // 栏目的代码序列
			String currentIdAct, // 当前活动编号
			String operator // 当前操作人员
	) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListMangDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		List<Integer> retStates = new ArrayList<Integer>();

		// 根据defcatseq，判断栏目是否是叶子节点
		List pcs = portalColumnManager.findByProperty("defcatseq", defcatseq);
		if (pcs != null && pcs.size() > 0) {
			PortalColumn portalColumn = (PortalColumn) pcs.get(0);
			if (portalColumn.getIsleaf().equalsIgnoreCase("Y")) {
				// 如果是叶子节点，继续
				// 查询得到活动序列
				List idActs = getIdActsFromFirst("FU00000077", "P");
				for (int i = 0; i < dates.size(); i++) {
					String date = dates.get(i);

					// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
					String scheduledate = convertDateToScheduleDate(date);

					// 根据scheduleDate、defcatseq，查询TPROGLISTMANGDETAIL，得到TPROGLISTMANGDETAIL
					// 因为栏目节点是叶子节点，所以一下查询没问题。like对效率有所影响
					List progListMangDetails = progListMangDetailManager
							.getProgListMangDetailsByScheduleDateAndDefcatseq(
									scheduledate, defcatseq);

					if (progListMangDetails.size() > 0) {
						// 判断栏目当前活动与currentIdAct
						ProgListMangDetail progListMangDetail = (ProgListMangDetail) progListMangDetails
								.get(0);
						int posCur = getPosInList(currentIdAct, idActs);
						int posPc = getPosInList(progListMangDetail.getIdAct(),
								idActs);
						int state;
						if (posCur > posPc) {
							state = 0;
						} else if (posCur == posPc) {
							state = 1;
						} else {
							state = 2;
						}

						retStates.add(state);
					} else {
						int state = 3;
						retStates.add(3);
					}
				}
			} else {
				// 如果不是叶子节点，返回
				cmsResultDto.setResultCode(Long.valueOf(1));
				cmsResultDto.setErrorMessage("栏目不是叶子节点栏目。");
			}
		} else {
			// 如果不存在，返回
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("栏目不存在。");
		}

		cmsResultDto.setResultObject(retStates);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListMangDetails returns.");
		return cmsResultDto;
	}

	// 20100121 16:27
	// 根据日期、栏目、当前活动，查询得到栏目单状态，
	// 返回retStates
	// 0 - 栏目活动 早于 当前活动
	// 1 - 栏目活动 等于 当前活动
	// 2 - 栏目活动 晚于 当前活动
	// 3 - 栏目未初始化
	public CmsResultDto getLocalProgListMangDetails(List<String> dates, // 日期，格式：yyyy-MM-dd
			String defcatseq, // 栏目的代码序列
			String currentIdAct, // 当前活动编号
			String operator // 当前操作人员
	) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getLocalProgListMangDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		List<Integer> retStates = new ArrayList<Integer>();

		// 根据defcatseq，判断栏目是否是叶子节点
		List pcs = portalColumnManager.findByProperty("defcatseq", defcatseq);
		if (pcs != null && pcs.size() > 0) {
			PortalColumn portalColumn = (PortalColumn) pcs.get(0);
			if (portalColumn.getIsleaf().equalsIgnoreCase("Y")) {
				// 如果是叶子节点，继续
				// 查询得到活动序列
				List idActs = getIdActsFromFirst("FU00000077", "P");
				for (int i = 0; i < dates.size(); i++) {
					String date = dates.get(i);

					// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
					String scheduledate = convertDateToScheduleDate(date);

					// 根据scheduleDate、defcatseq，查询TPROGLISTMANGDETAIL，得到TPROGLISTMANGDETAIL
					// 因为栏目节点是叶子节点，所以一下查询没问题。like对效率有所影响
					// List progListMangDetails =
					// progListMangDetailManager.getProgListMangDetailsByScheduleDateAndDefcatseq(scheduledate,
					// defcatseq);
					// 返回：List<ProgListMangDetail>
					List progListMangDetails = progListMangDetailManager
							.getLocalProgListMangDetailsByScheduleDateAndDefcatseq(
									scheduledate, defcatseq);

					if (progListMangDetails.size() > 0) {
						// 判断栏目当前活动与currentIdAct
						ProgListMangDetail progListMangDetail = (ProgListMangDetail) progListMangDetails
								.get(0);
						int posCur = getPosInList(currentIdAct, idActs);
						int posPc = getPosInList(progListMangDetail.getIdAct(),
								idActs);
						int state;
						if (posCur > posPc) {
							state = 0;
						} else if (posCur == posPc) {
							state = 1;
						} else {
							state = 2;
						}

						retStates.add(state);
					} else {
						int state = 3;
						retStates.add(3);
					}
				}
			} else {
				// 如果不是叶子节点，返回
				cmsResultDto.setResultCode(Long.valueOf(1));
				cmsResultDto.setErrorMessage("栏目不是叶子节点栏目。");
			}
		} else {
			// 如果不存在，返回
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("栏目不存在。");
		}

		cmsResultDto.setResultObject(retStates);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getLocalProgListMangDetails returns.");
		return cmsResultDto;
	}

	// 20100108 10:24
	// 根据日期，查询栏目单总表或者主表管理表TPROGLISTMANG，
	// 返回：
	public CmsResultDto getProgListMangsByDate(String dateFrom, // 日期起始，格式：yyyy-MM-dd
			String dateTo // 日期终止，格式：yyyy-MM-dd
	) {
		// 返回：
		// List<Object[]>
		// (ProgListMang)Object[]
		// (FlowAction)Object[]
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListMangsByDate...");
		cmsLog.info("dateFrom : " + dateFrom);
		cmsLog.info("dateTo : " + dateTo);
		CmsResultDto cmsResultDto = new CmsResultDto();

		dateFrom = convertDateToScheduleDate(dateFrom);
		dateTo = convertDateToScheduleDate(dateTo);
		// 返回：
		// List<Object[]>
		// (ProgListMang)Object[]
		// (FlowAction)Object[]
		List list = progListMangManager
				.getProgListMangsByDate(dateFrom, dateTo);
		cmsLog.info("返回记录数 : " + list.size());
		cmsResultDto.setResultObject(list);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListMangsByDate returns.");
		return cmsResultDto;
	}

	// 20100108 10:24
	// 根据栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
	// 返回：
	public CmsResultDto getProgListMangDetailsByScheduledate(String scheduledate // 栏目单总表的主键
	) {
		// 返回：List<Object[]>
		// (ProgListMangDetail)Object[0]
		// (PortalColumn)Object[1]
		// (FlowAction)Object[2]

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListMangDetailsByScheduledate...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 返回：List<Object[]>
		// (ProgListMangDetail)Object[0]
		// (PortalColumn)Object[1]
		// (FlowAction)Object[2]
		List list = progListMangDetailManager
				.getProgListMangDetailsByScheduledate(scheduledate);
		cmsResultDto.setResultObject(list);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getProgListMangDetailsByScheduledate returns.");
		return cmsResultDto;
	}

	// 20100121 13:45
	// 根据本地栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
	// 返回：
	public CmsResultDto getLocalProgListMangDetailsByScheduledate(
			String scheduledate // 栏目单总表的主键
	) {
		// 返回：List<Object[]>
		// (ProgListMangDetail)Object[0]
		// (PortalColumn)Object[1]
		// (FlowAction)Object[2]

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getLocalProgListMangDetailsByScheduledate...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 返回：List<Object[]>
		// (ProgListMangDetail)Object[0]
		// (PortalColumn)Object[1]
		// (FlowAction)Object[2]
		List list = progListMangDetailManager
				.getLocalProgListMangDetailsByScheduledate(scheduledate);
		cmsResultDto.setResultObject(list);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getLocalProgListMangDetailsByScheduledate returns.");
		return cmsResultDto;
	}

	// 20100130
	// 刷新栏目单详细中节目包的状态state1， 节目包状态，0-导入 1-缓存库 2-加扰库 3-播控库
	public CmsResultDto refreshState1OfProgPackage(
			String stclasscodeNearOnline, // 缓存库存储体等级code
			String stclasscodeCaOnline, // 加扰库存储体等级code
			String stclasscodeOnline, // 播控库存储体等级code
			ProgListDetail progListDetail) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> refreshState1OfProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		ProgPackage progPackage = (ProgPackage) progPackageManager
				.getById(progListDetail.getProductid());
		if (progPackage != null) {
			cmsResultDto = cmsTransactionManager
					.updateRefreshState1OfProgPackage(packageFilesManager,
							progListDetailManager, progPackageManager,
							stclasscodeNearOnline, stclasscodeCaOnline,
							stclasscodeOnline, "", progPackage, 1);
		} else {
			String str = "节目包不存在。节目包ID：" + progListDetail.getProductid();
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> refreshState1OfProgPackage returns.");
		return cmsResultDto;
	}

	// 20100126 21:40
	// 刷新date当天栏目单详细的节目包状态，0导入1缓存库2加扰库3播控库
	public CmsResultDto refreshState1sInProgListDetail(String date // 日期，格式：yyyy-MM-dd
	) {
		// 疑问：是否本地

		// 流程：
		// 1 - 根据date，查询总表，判断记录是否存在
		// 2 - 根据date，查询分表，得到分表记录
		// 3 - 根据分表的栏目和date，查询，得到栏目单详细
		// 4 - 根据栏目单详细的节目包ID和节目包状态，查询文件存放位置表，得到是否节目包所有主文件都在下一存储位置上
		// 4.1 - 如果是，改变栏目单详细中的节目包状态
		// 4.2 - 如果不是，不处理，break

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> refreshState1sInProgListDetail...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 配置文件，获取
		// 节目包状态，0-导入 1-缓存库 2-加扰库 3-播控库
		String stclasscodeNearOnline = "NearOnline"; // 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline"; // 加扰库存储体等级code
		String stclasscodeOnline = "Online"; // 播控库存储体等级code

		String scheduledate = convertDateToScheduleDate(date);

		// 1 - 根据date，查询总表，判断记录是否存在
		ProgListMang progListMang = (ProgListMang) progListMangManager
				.getById(scheduledate);
		if (progListMang == null) {
			// 返回失败
			String str = "未查询到栏目单总表记录，返回失败。日期：" + date;
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}

		// 2 - 根据date，查询分表，得到分表记录
		List progListMangDetails = progListMangDetailManager.findByProperty(
				"scheduledate", scheduledate);
		for (int i = 0; i < progListMangDetails.size(); i++) {
			ProgListMangDetail progListMangDetail = (ProgListMangDetail) progListMangDetails
					.get(i);

			// 3 - 根据分表的栏目和date，查询，得到栏目单详细
			List list = progListDetailManager
					.getProgListDetailsByScheduledateAndColumnclassid(date,
							progListMangDetail.getColumnclassid());

			for (int j = 0; j < list.size(); j++) {
				Object[] rows = (Object[]) list.get(j);
				ProgListDetail progListDetail = (ProgListDetail) rows[0];
				ProgPackage progPackage = (ProgPackage) rows[1];

				cmsResultDto = refreshState1OfProgPackage(
						stclasscodeNearOnline, // 缓存库存储体等级code
						stclasscodeCaOnline, // 加扰库存储体等级code
						stclasscodeOnline, // 播控库存储体等级code
						progListDetail);

				if (cmsResultDto.getResultCode() != 0) {
					break;
				}
			}
		}

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> refreshState1sInProgListDetail returns.");
		return cmsResultDto;
	}

	// 20100221 14:15
	// 数据导入，分析上海导出的xml，把节目包和编单信息导入到北京的cms
	public CmsResultDto importPortalXmlToBjCms(String operator // 当前操作人员
	) {
		// 说明：
		// 每个xml对应一个栏目，
		// xml是以栏目code命名的，以日期为文件夹区分不同日期的栏目单，格式：yyyyMMdd000000

		// smb://administrator:1@10.0.2.253/20100223000000/proglist/dvd.xml

		// 流程：
		// 1 - 从配置文件，获得数据导出导入的栏目单xml的源路径，处理成功后的目标路径，处理失败后的目标路径
		// 2 - 从xml的源路径获取xml，循环
		// 3 - 分析xml
		// 4 - 判断栏目是否存在，栏目是否是非本地栏目(上海的栏目)，
		// 5 - 判断栏目单(以日期为单位)，判断流程、初始化编单分表、总表
		// 6 - 判断栏目单详细记录(栏目、节目包)是否已经存在，不存在则导入栏目单详细记录到数据库
		// 7 - 修改xml文件名字

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> importPortalXmlToBjCms...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 读取配置文件
		String progPackagePath = "progpackage";
		String progListPath = "proglist";
		String importDataStclasscode = "ImportArea"; // 数据导入源存储体等级code
		String importDataFilecode = "IMPORT"; // 数据导入源filecode

		cmsResultDto = cmsTransactionManager.saveImportPortalXmlToBjCms(
				progPackageManager, amsstoragedirManager, portalColumnManager,
				progListMangManager, progListMangDetailManager,
				progListDetailManager, pPColumnRelManager,
				importDataStclasscode, importDataFilecode, progPackagePath,
				progListPath, operator);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> importPortalXmlToBjCms returns.");
		return cmsResultDto;
	}

	// 20100221 17:12
	// 数据导入，把上海导出的节目包和节目包文件信息，导入到北京的cms
	/**
	 * 2010年7月14日 10时33分
	 */
	public CmsResultDto importProgPackageFilesToBjCms(String operator // 当前操作人员
	) {
		// 流程：
		// 1 - 从配置文件，获得数据导出导入的节目包的源路径
		// 2 - 获得源路径下所有目录，期待结果格式:20100222000000
		// 3 - 获得编单日期目录(20100222000000)下的所有目录，期待结果：progpackage和proglist
		// 4 - 获得progpackage下的所有目录，期待结果格式：PPVP20100209110638000078
		// 5 - 获得PPVP20100209110638000078目录下的所有文件，期待结果：*.png,*.ts,*.xml
		// 6 - 读取xml文件内容，获得节目包ID、栏目单详细记录信息
		// 7 - 查询数据库，判断节目包是否已经存在
		// 7.1 - 存在，暂时不做处理
		// 7.2 - 不存在，需要新增节目包记录到数据库，继续
		// 7.2.1 - 根据xml，获得节目包下所有的文件信息，判断对应的文件是否都在目录(PPVP20100209110638000078)下
		// 7.2.1.1 - 不存在，不处理
		// 7.2.1.2 - 存在，继续
		// 7.2.1.2.1 -
		// 添加记录到：ProgPackage,ProgramFiles,PackageFiles,(节目包栏目关系表，节目包产品关系表)
		// 7.2.1.2.2 - 下迁移单，把节目包下的文件迁移到缓存库

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> importProgPackageFilesToBjCms...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 1 - 从配置文件，获得数据导出导入的节目包的源路径
		// 读取配置文件
		// String importDataPath =
		// "smb://administrator:1@127.0.0.1/importdata/";
		String progPackagePath = "progpackage";
		String progListPath = "proglist";
		String importDataStclasscode = "ImportArea"; // 数据导入源存储体等级code
		String importDataFilecode = "IMPORT"; // 数据导入源filecode
		String destStclasscode = "NearOnline"; // 数据导入目标存储体等级code
		String filecodeMigrationImport = "MIGXML"; // 迁移任务xml文件filecode
		String stclasscodeMigrationImport = "Migration";// 迁移任务xml目标存储体等级code

		cmsResultDto = cmsTransactionManager.saveImportProgPackageFilesToBjCms2(
				progPackageManager, programFilesManager, packageFilesManager,
				amsstoragedirManager, amsstorageManager,
				amsstorageclassManager, importDataStclasscode,
				importDataFilecode, destStclasscode, filecodeMigrationImport,
				stclasscodeMigrationImport, progPackagePath, progListPath,
				operator);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> importProgPackageFilesToBjCms returns.");
		return cmsResultDto;
	}

	// 20100223 17:35
	// 数据导入，包括节目包和编单, 被前台调用
	public CmsResultDto importDataToBjCms(String operator // 当前操作人员
	) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> importDataToBjCms...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		String str = "";
		// 分析上海导出的xml, 把节目包和编单信息导入到北京的CMS的结果
		CmsResultDto cmsResultDto1 = importPortalXmlToBjCms(operator);
		
		// 把上海导出的节目包和节目包文件信息，导入到北京的cms的结果
		CmsResultDto cmsResultDto2 = importProgPackageFilesToBjCms(operator);

		if (cmsResultDto1.getResultCode() == 0) {
			str += "编单数据导入成功。";
		} else {
			str += "编单数据导入失败。\r\n错误消息：" + cmsResultDto1.getErrorMessage();
			str += "\r\n错误详细：" + cmsResultDto1.getErrorDetail();
			cmsResultDto.setResultCode((long) 1);
		}
		if (cmsResultDto2.getResultCode() == 0) {
			str += "节目包数据导入成功。";
		} else {
			str += "节目包数据导入失败。\r\n错误消息：" + cmsResultDto2.getErrorMessage();
			str += "\r\n错误详细：" + cmsResultDto2.getErrorDetail();
			cmsResultDto.setResultCode((long) 1);
		}

		String errorMsg = "";
		if ((cmsResultDto1.getErrorMessage() == null || cmsResultDto1
				.getErrorMessage().equalsIgnoreCase(""))
				&& (cmsResultDto2.getErrorMessage() == null || cmsResultDto2
						.getErrorMessage().equalsIgnoreCase(""))) {
			errorMsg = "";
		}

		String strErrorMessage = cmsResultDto1.getErrorMessage() + ";"
				+ cmsResultDto2.getErrorMessage();
		if (strErrorMessage.equalsIgnoreCase(";")) {
			strErrorMessage = "";
		}
		cmsResultDto.setErrorMessage(strErrorMessage);
		cmsResultDto.setErrorDetail(str);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> importDataToBjCms returns.");
		return cmsResultDto;
	}

	// ------------------------------------- PortalColumn
	// ------------------------------------------------------

	// 得到所有的栏目
	@SuppressWarnings("unchecked")
	public CmsResultDto getAllPortalColumns() {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> getAllPortalColumns...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 获利节目包js文件在一级库的存放路径
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"d_column", "Online");
		if (null == descPath || 2 > descPath.size()) {
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		this.onlineJsPath = (String) descPath.get(0);
		this.onlineJsPath = fileopr.checkPathFormatRear(onlineJsPath, '/');	
		
		List portalColumnDtos = new ArrayList();
		List portalColumns = portalColumnManager.queryPortalColumnsByValid();
		for (int i = 0; i < portalColumns.size(); i++) {
			PortalColumn portalColumn = (PortalColumn) portalColumns.get(i);
			PortalColumnDto portalColumnDto = new PortalColumnDto(portalColumn);
			portalColumnDtos.add(portalColumnDto);
			
			if (0 != fileopr.copyFileFromSmbToLocal(
					this.onlineJsPath + portalColumn.getParentdir() + 
					"/" + portalColumn.getFocusImgName(), 
					ServerConfigListener.TEMP_PATH + portalColumn.getFocusImgName())) {
				cmsLog.error(portalColumn.getColumnname() + " 复制默认皮肤焦点图片失败: ");
				cmsResultDto.setResultCode(1L);
				cmsResultDto.setErrorMessage("查询栏目失败!");
				return cmsResultDto;
			}
			if (0 != fileopr.copyFileFromSmbToLocal(
					this.onlineJsPath + portalColumn.getParentdir() + 
					"/" + portalColumn.getBlurImgName(), 
					ServerConfigListener.TEMP_PATH + portalColumn.getBlurImgName())) {
				cmsLog.error("复制默认皮肤非焦点图片失败: ");
				cmsResultDto.setResultCode(1L);
				cmsResultDto.setErrorMessage("查询栏目失败!");
				return cmsResultDto;
			}
		}
		cmsResultDto.setResultObject(portalColumnDtos);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getAllPortalColumns returns.");
		return cmsResultDto;
	}

	// 添加节目包到栏目 ProgPackage PortalColumn
	public CmsResultDto addProgPackagesToPortalColumn(List progPackages,
			String columnclassid) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> addProgPackagesToPortalColumn...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsTransactionManager.saveProgPackagesToPortalColumn(
				portalColumnManager, pPColumnRelManager, progPackages,
				columnclassid);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> addProgPackagesToPortalColumn returns.");
		return cmsResultDto;
	}

	// 删除节目包从栏目 ProgPackage PortalColumn
	public CmsResultDto deleteProgPackagesFromPortalColumn(List progPackages,
			String columnclassid) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> deleteProgPackagesFromPortalColumn...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsTransactionManager.deleteProgPackagesFromPortalColumn(
				portalColumnManager, pPColumnRelManager, progPackages,
				columnclassid);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> deleteProgPackagesFromPortalColumn returns.");
		return cmsResultDto;
	}

	// 保存（新建）栏目 PortalColumn
	public String savePortalColumn(PortalColumn portalColumn) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> savePortalColumn...");
		if (cmsLog.isDebugEnabled()) {
			cmsLog.debug("\tPortalColumn.columnclassid" + portalColumn.getColumnclassid());
			cmsLog.debug("\tPortalColumn.columnname" + portalColumn.getColumnname());
			cmsLog.debug("\tPortalColumn.defcatcode" + portalColumn.getDefcatcode());
			cmsLog.debug("\tPortalColumn.siteCode" + portalColumn.getSiteCode());
			cmsLog.debug("\tPortalColumn.isleaf" + portalColumn.getIsleaf());
			cmsLog.debug("\tPortalColumn.defcatlevel" + portalColumn.getDefcatlevel());
			cmsLog.debug("\tPortalColumn.rootid" + portalColumn.getRootid());
			cmsLog.debug("\tPortalColumn.parentsid" + portalColumn.getParentsid());
			cmsLog.debug("\tPortalColumn.displayorder" + portalColumn.getDisplayorder());
			cmsLog.debug("\tPortalColumn.defcatseq" + portalColumn.getDefcatseq());
			cmsLog.debug("\tPortalColumn.scheduleTag" + portalColumn.getScheduleTag());
			cmsLog.debug("\tPortalColumn.validFlag" + portalColumn.getValidFlag());
			cmsLog.debug("\tPortalColumn.remark" + portalColumn.getRemark());
			cmsLog.debug("\tPortalColumn.ispublish" + portalColumn.getIspublish());
			cmsLog.debug("\tPortalColumn.updatedate" + portalColumn.getUpdatedate());
			cmsLog.debug("\tPortalColumn.archivedays" + portalColumn.getArchivedays());
			cmsLog.debug("\tPortalColumn.inputmanid" + portalColumn.getInputmanid());
			cmsLog.debug("\tPortalColumn.inputtime" + portalColumn.getInputtime());
			cmsLog.debug("\tPortalColumn.validFrom" + portalColumn.getValidFrom());
			cmsLog.debug("\tPortalColumn.countonepage" + portalColumn.getCountonepage());
			cmsLog.debug("\tPortalColumn.isdomainsub" + portalColumn.getIsdomainsub());
			cmsLog.debug("\tPortalColumn.isallchange" + portalColumn.getIsallchange());

			cmsLog.debug("\tPortalColumn.siteid" + portalColumn.getSiteid());
			cmsLog.debug("\tPortalColumn.validTo" + portalColumn.getValidTo());
			cmsLog.debug("\tPortalColumn.publishfilename" + portalColumn.getPublishfilename());
			cmsLog.debug("\tPortalColumn.parentdir" + portalColumn.getParentdir());
			cmsLog.debug("\tPortalColumn.domainname" + portalColumn.getDomainname());
			cmsLog.debug("\tPortalColumn.countnumb" + portalColumn.getCountnumb());
			cmsLog.debug("\tPortalColumn.styleid" + portalColumn.getStyleid());

			cmsLog.debug("\tPortalColumn.focusImgName" + portalColumn.getFocusImgName());
			cmsLog.debug("\tPortalColumn.blurImgName" + portalColumn.getBlurImgName());
			cmsLog.debug("\tPortalColumn.currentdir" + portalColumn.getCurrentdir());
			cmsLog.debug("\tPortalColumn.contentmodeid" + portalColumn.getCovermodeid());
			cmsLog.debug("\tPortalColumn.covermodeid" + portalColumn.getCovermodeid());
			cmsLog.debug("\tPortalColumn.listmodeid" + portalColumn.getListmodeid());
		}
		portalColumn.setSiteCode(null);
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		/**
		 * 参数检查
		 */
		// 检查栏目名称是否合法
		if (!ParamCheck.hasText(portalColumn.getColumnname(), "栏目名称不能为空, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(
						portalColumn.getColumnname(), 40, "栏目名称长度超出40字符!")) {
			cmsLog.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		// 检查栏目Code是否合法
		if (!ParamCheck.hasText(portalColumn.getDefcatcode(), "栏目Code不能为空, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(
						portalColumn.getDefcatcode(), 40, "栏目Code长度超出40字符!")) {
			cmsLog.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		// 检查栏目Code是否存在
		if (this.portalColumnManager.isExistsColumnCode(portalColumn.getDefcatcode())) {
			if (this.progListMangDetailManager.existProgListDetail(portalColumn.getDefcatcode())) {
				cmsLog.info("栏目Code已使用: " + portalColumn.getDefcatcode());
				return "栏目Code已使用, 请重新输入!";
			} else {
				PortalColumn example = new PortalColumn();
				example.setDefcatcode(portalColumn.getDefcatcode());
				example.setValidFlag(1L);
				List<PortalColumn> existColumns = this.portalColumnManager.findbyExample(example);
				for (PortalColumn p : existColumns) {
					cmsLog.debug("删除已存在相同栏目Code[ " + portalColumn.getDefcatcode() + 
							" ]且未初始化编单的栏目: " + p.getColumnname());
					this.portalColumnManager.deleteById(p.getColumnclassid());
				}
			}
		}
		// 检查终端显示栏目名
		if (!ParamCheck.hasText(portalColumn.getPublishfilename(), "终端显示栏目名不能为空, 将会导致终端无栏目名显示, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(
						portalColumn.getDefcatcode(), 40, "终端显示栏目名长度超出40字符!")) {
			cmsLog.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		
		cmsLog.debug((1 == portalColumn.getArchivedays()) + " : " + (1 != portalColumn.getStyleid()));
		cmsLog.debug((0 == portalColumn.getArchivedays()) + " : " + (1 == portalColumn.getStyleid()));
		if ((1 == portalColumn.getArchivedays()	&& 1 == portalColumn.getStyleid())
				|| (0 == portalColumn.getArchivedays() && 1 != portalColumn.getStyleid())) {
			cmsLog.debug("富媒体或视频节目[视频]只能选择[视频类]栏目样式编号, 富媒体或视频节目[富媒体]不能选择[视频类]栏目样式编号!");
			return "富媒体或视频节目[视频]只能选择[视频类]栏目样式编号, \n\t富媒体或视频节目[富媒体]不能选择[视频类]栏目样式编号!";
		}
		
		CmsConfig config = new CmsConfig();
		String defaultSkinName = config.getPropertyByName("DefaultSkinName");
		String secondkinName = config.getPropertyByName("SecondSkinName");
		String thirdSkinName = config.getPropertyByName("ThirdSkinName");
		// 获利节目包js文件在一级库的存放路径
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"d_column", "Online");
		if (null == descPath || 2 > descPath.size()) {
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		this.onlineJsPath = (String) descPath.get(0);
		this.onlineJsPath = fileopr.checkPathFormatRear(onlineJsPath, '/');		
		
		portalColumn.setParentdir("png");
		cmsLog.debug("设置当前新建栏目的子栏目级数由[ " + portalColumn.getIsdomainsub() + 
				" ]减1 ---> [ " + (portalColumn.getIsdomainsub() - 1) + " ]");
		portalColumn.setIsdomainsub(portalColumn.getIsdomainsub() - 1);

		try {
			if (0 != fileopr.copyFileFromLocalToSmb(
					ServerConfigListener.TEMP_PATH + portalColumn.getFocusImgName(), 
					this.onlineJsPath + portalColumn.getParentdir() + "/" + 
					defaultSkinName + portalColumn.getDefcatcode() + "_focus.png")) {
				throw new Exception(portalColumn.getColumnname() + " [默认皮肤]焦点图片复制失败!");
			}
			
			if (0 != fileopr.copyFileFromLocalToSmb(
					ServerConfigListener.TEMP_PATH + portalColumn.getBlurImgName(), 
					this.onlineJsPath + portalColumn.getParentdir() + "/" + 
					defaultSkinName + portalColumn.getDefcatcode() + "_blur.png")) {
				throw new Exception(portalColumn.getColumnname() + " [默认皮肤]非焦点图片复制失败!");
			}
			
//			if (0 != fileopr.copyFileFromLocalToSmb(
//					ServerConfigListener.TEMP_PATH + portalColumn.getCurrentdir(), 
//					this.onlineJsPath + portalColumn.getParentdir() + "/" + 
//					secondkinName + portalColumn.getDefcatcode() + "_focus.png")) {
//				throw new Exception(portalColumn.getColumnname() + " [第二套皮肤]焦点图片复制失败!");
//			}
//			
//			if (0 != fileopr.copyFileFromLocalToSmb(
//					ServerConfigListener.TEMP_PATH + portalColumn.getContentmodeid(), 
//					this.onlineJsPath + portalColumn.getParentdir() + "/" + 
//					secondkinName + portalColumn.getDefcatcode() + "_blur.png")) {
//				throw new Exception(portalColumn.getColumnname() + " [第二套皮肤]非焦点图片复制失败!");
//			}
//			
//			if (0 != fileopr.copyFileFromLocalToSmb(
//					ServerConfigListener.TEMP_PATH + portalColumn.getCovermodeid(), 
//					this.onlineJsPath + portalColumn.getParentdir() + "/" + 
//					thirdSkinName + portalColumn.getDefcatcode() + "_focus.png")) {
//				throw new Exception(portalColumn.getColumnname() + " [第三套皮肤]焦点图片复制失败!");
//			}
//			
//			if (0 != fileopr.copyFileFromLocalToSmb(
//					ServerConfigListener.TEMP_PATH + portalColumn.getListmodeid(), 
//					this.onlineJsPath + portalColumn.getParentdir() + "/" + 
//					thirdSkinName + portalColumn.getDefcatcode() + "_blur.png")) {
//				throw new Exception(portalColumn.getColumnname() + " [第三套皮肤]非焦点图片复制失败!");
//			}
		} catch (Exception e) {
			cmsLog.error(e);
			return "栏目添加失败!";
		}
		
		portalColumn.setIsallchange(2L);					// 为了不让维护完的栏目显示为黑色字体
		portalColumn.setFocusImgName(defaultSkinName + portalColumn.getDefcatcode() + "_focus.png");
		portalColumn.setBlurImgName(defaultSkinName + portalColumn.getDefcatcode() + "_blur.png");
//		portalColumn.setContentmodeid(secondkinName + portalColumn.getDefcatcode() + "_focus.png");
//		portalColumn.setCurrentdir(secondkinName + portalColumn.getDefcatcode() + "_blur.png");
//		portalColumn.setListmodeid(thirdSkinName + portalColumn.getDefcatcode() + "_focus.png");
//		portalColumn.setCovermodeid(thirdSkinName + portalColumn.getDefcatcode() + "_blur.png");
		
		cmsResultDto = cmsTransactionManager.savePortalColumn(
				portalColumnManager, pPColumnRelManager, portalColumn);

		
		try {
			if (0 == cmsResultDto.getResultCode()) {
				PackStylePortalColumn packStylePortalColumn = new PackStylePortalColumn();
				packStylePortalColumn.setDefcatcode(portalColumn.getDefcatcode());
				packStylePortalColumn.setInputmanid(portalColumn.getInputmanid());
				packStylePortalColumn.setInputtime(new Date());
				packStylePortalColumn.setRemark("默认");
				if (1 == portalColumn.getStyleid()) {
					packStylePortalColumn.setStyleid(101L);
					
					PackStylePortalColumn packStylePortalColumnDSJ = new PackStylePortalColumn();
					packStylePortalColumnDSJ.setDefcatcode(portalColumn.getDefcatcode());
					packStylePortalColumnDSJ.setInputmanid(portalColumn.getInputmanid());
					packStylePortalColumnDSJ.setInputtime(new Date());
					packStylePortalColumnDSJ.setRemark("默认");
					packStylePortalColumnDSJ.setStyleid(7L);
					
					PackStylePortalColumn packStylePortalColumnAD = new PackStylePortalColumn();
					packStylePortalColumnAD.setDefcatcode(portalColumn.getDefcatcode());
					packStylePortalColumnAD.setInputmanid(portalColumn.getInputmanid());
					packStylePortalColumnAD.setInputtime(new Date());
					packStylePortalColumnAD.setRemark("默认");
					packStylePortalColumnAD.setStyleid(20L);
					
					this.packStylePortalColumnManager.save(packStylePortalColumnDSJ);
					this.packStylePortalColumnManager.save(packStylePortalColumnAD);
				} else if (2 == portalColumn.getStyleid()) {
					packStylePortalColumn.setStyleid(12L);
				} else if (3 == portalColumn.getStyleid()) {
					packStylePortalColumn.setStyleid(10L);
				} else if (4 == portalColumn.getStyleid()) {
					packStylePortalColumn.setStyleid(8L);
				} else {
					packStylePortalColumn.setStyleid(101L);
				}
				this.packStylePortalColumnManager.save(packStylePortalColumn);
				this.generateColumnJs();
			} else {
				return cmsResultDto.getErrorMessage();
			}
		} catch (Exception e) {
			this.portalColumnManager.deleteById(portalColumn.getColumnclassid());
			cmsLog.error(e);
			return "栏目添加失败!";
		}
		
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> savePortalColumn returns.");
		return null;
	}

	// 修改（更新）栏目 PortalColumn
	public String updatePortalColumn(PortalColumn portalColumn) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> updatePortalColumn...");
		if (cmsLog.isDebugEnabled()) {
			cmsLog.debug("\tPortalColumn.columnclassid" + portalColumn.getColumnclassid());
			cmsLog.debug("\tPortalColumn.columnname" + portalColumn.getColumnname());
			cmsLog.debug("\tPortalColumn.defcatcode" + portalColumn.getDefcatcode());
			cmsLog.debug("\tPortalColumn.siteCode" + portalColumn.getSiteCode());
			cmsLog.debug("\tPortalColumn.isleaf" + portalColumn.getIsleaf());
			cmsLog.debug("\tPortalColumn.defcatlevel" + portalColumn.getDefcatlevel());
			cmsLog.debug("\tPortalColumn.rootid" + portalColumn.getRootid());
			cmsLog.debug("\tPortalColumn.parentsid" + portalColumn.getParentsid());
			cmsLog.debug("\tPortalColumn.displayorder" + portalColumn.getDisplayorder());
			cmsLog.debug("\tPortalColumn.defcatseq" + portalColumn.getDefcatseq());
			cmsLog.debug("\tPortalColumn.scheduleTag" + portalColumn.getScheduleTag());
			cmsLog.debug("\tPortalColumn.validFlag" + portalColumn.getValidFlag());
			cmsLog.debug("\tPortalColumn.remark" + portalColumn.getRemark());
			cmsLog.debug("\tPortalColumn.ispublish" + portalColumn.getIspublish());
			cmsLog.debug("\tPortalColumn.updatedate" + portalColumn.getUpdatedate());
			cmsLog.debug("\tPortalColumn.archivedays" + portalColumn.getArchivedays());
			cmsLog.debug("\tPortalColumn.inputmanid" + portalColumn.getInputmanid());
			cmsLog.debug("\tPortalColumn.inputtime" + portalColumn.getInputtime());
			cmsLog.debug("\tPortalColumn.validFrom" + portalColumn.getValidFrom());
			cmsLog.debug("\tPortalColumn.countonepage" + portalColumn.getCountonepage());
			cmsLog.debug("\tPortalColumn.isdomainsub" + portalColumn.getIsdomainsub());
			cmsLog.debug("\tPortalColumn.isallchange" + portalColumn.getIsallchange());

			cmsLog.debug("\tPortalColumn.siteid" + portalColumn.getSiteid());
			cmsLog.debug("\tPortalColumn.validTo" + portalColumn.getValidTo());
			cmsLog.debug("\tPortalColumn.publishfilename" + portalColumn.getPublishfilename());
			cmsLog.debug("\tPortalColumn.parentdir" + portalColumn.getParentdir());
			cmsLog.debug("\tPortalColumn.domainname" + portalColumn.getDomainname());
			cmsLog.debug("\tPortalColumn.countnumb" + portalColumn.getCountnumb());
			cmsLog.debug("\tPortalColumn.styleid" + portalColumn.getStyleid());

			cmsLog.debug("\tPortalColumn.focusImgName" + portalColumn.getFocusImgName());
			cmsLog.debug("\tPortalColumn.blurImgName" + portalColumn.getBlurImgName());
			cmsLog.debug("\tPortalColumn.currentdir" + portalColumn.getCurrentdir());
			cmsLog.debug("\tPortalColumn.contentmodeid" + portalColumn.getCovermodeid());
			cmsLog.debug("\tPortalColumn.covermodeid" + portalColumn.getCovermodeid());
			cmsLog.debug("\tPortalColumn.listmodeid" + portalColumn.getListmodeid());
		}
		
		// 检查栏目名称是否合法
		if (!ParamCheck.hasText(portalColumn.getColumnname(), "栏目名称不能为空!")
				|| !ParamCheck.lessThenOrEqualToLength(
						portalColumn.getColumnname(), 40, "栏目名称长度超出40字符!")) {
			cmsLog.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		// 检查栏目Code是否合法
		if (!ParamCheck.hasText(portalColumn.getDefcatcode(), "栏目Code不能为空!")
				|| !ParamCheck.lessThenOrEqualToLength(
						portalColumn.getDefcatcode(), 40, "栏目Code长度超出40字符!")) {
			cmsLog.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		// 检查终端显示栏目名
		if (!ParamCheck.hasText(portalColumn.getPublishfilename(), "终端显示栏目名不能为空!")
				|| !ParamCheck.lessThenOrEqualToLength(
						portalColumn.getDefcatcode(), 40, "终端显示栏目名长度超出40字符!")) {
			cmsLog.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		
		cmsLog.debug((1 == portalColumn.getArchivedays()) + " : " + (1 != portalColumn.getStyleid()));
		cmsLog.debug((0 == portalColumn.getArchivedays()) + " : " + (1 == portalColumn.getStyleid()));
		if ((1 == portalColumn.getArchivedays()	&& 1 == portalColumn.getStyleid())
				|| (0 == portalColumn.getArchivedays() && 1 != portalColumn.getStyleid())) {
			cmsLog.debug("富媒体或视频节目[视频]只能选择[视频类]栏目样式编号, 富媒体或视频节目[富媒体]不能选择[视频类]栏目样式编号!");
			return "富媒体或视频节目[视频]只能选择[视频类]栏目样式编号, \n\t富媒体或视频节目[富媒体]不能选择[视频类]栏目样式编号!";
		}
		
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 获利节目包js文件在一级库的存放路径
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"d_column", "Online");
		if (null == descPath || 2 > descPath.size()) {
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		this.onlineJsPath = (String) descPath.get(0);
		this.onlineJsPath = fileopr.checkPathFormatRear(onlineJsPath, '/');	
		
		PortalColumn curPortalColumn = (PortalColumn) portalColumnManager
				.getById(portalColumn.getColumnclassid().toString());
		List<ProgListDetail> progListDetails = this.progListDetailManager.findByProperty(
				"columnclassid", curPortalColumn.getColumnclassid());
		
		if (curPortalColumn.getArchivedays() != portalColumn.getArchivedays()) {
			// 如果修改了栏目类型 判断当前下是否编有节目
			List<Object[]> list = progListDetailManager.queryScheduleDateAndActionName(
					curPortalColumn.getColumnclassid());
			if (0 < list.size()) {
				if (cmsLog.isDebugEnabled()) {
					for (Object[] objects : list) {
						cmsLog.debug("当前栏目编单日期[" + objects[0] + "]下编有节目, 所处流程[" + 
								objects[0] + "], 不允许修改栏目[富媒体或视频节目类型]");
					}
				}
				
				return "当前栏目下编有节目, 不允许修改栏目[富媒体或视频节目类型]";
			}
		} 
		
		// 未改栏目, 判断是否改了栏目样式编号
		// 如果改了, 删除原来的样式编号, 添加新的栏目编号
		if (curPortalColumn.getStyleid() != portalColumn.getStyleid()) {
			List<PackStylePortalColumn> packStylePortalColumns = 
					this.packStylePortalColumnManager.getPackStylePortalColumnsByStyleidDefcatcode(
							convertColumnStyleId2ProgType(curPortalColumn.getStyleid()), 
							curPortalColumn.getDefcatcode());
			for (PackStylePortalColumn packStylePortalColumn : packStylePortalColumns) {
				cmsLog.debug("修改前栏目样式编号: [ " + packStylePortalColumn.getDefcatcode() + 
						" : " + packStylePortalColumn.getStyleid() + " ], id: " + packStylePortalColumn.getRelid());
				packStylePortalColumn.setStyleid(convertColumnStyleId2ProgType(portalColumn.getStyleid()));
				this.packStylePortalColumnManager.update(packStylePortalColumn);
				cmsLog.debug("修改后栏目样式编号: [ " + packStylePortalColumn.getDefcatcode() + 
						" : " + packStylePortalColumn.getStyleid() + " ], id: " + packStylePortalColumn.getRelid());
			}
			
			// 视频类的改为图书,杂志或报纸类
			if (1 == curPortalColumn.getStyleid()) {
				// 删除多的
				this.packStylePortalColumnManager.deleteByStyleIdDefcatcode(
						curPortalColumn.getDefcatcode(), 7L);
				this.packStylePortalColumnManager.deleteByStyleIdDefcatcode(
						curPortalColumn.getDefcatcode(), 20L);
			}
			
			// 图书,杂志或报纸类改为视频类
			if (1 == portalColumn.getStyleid()) {
				// 添加少的
				PackStylePortalColumn packStylePortalColumnDSJ = new PackStylePortalColumn();
				packStylePortalColumnDSJ.setDefcatcode(portalColumn.getDefcatcode());
				packStylePortalColumnDSJ.setInputmanid(portalColumn.getInputmanid());
				packStylePortalColumnDSJ.setInputtime(new Date());
				packStylePortalColumnDSJ.setRemark("修改时默认");
				packStylePortalColumnDSJ.setStyleid(7L);
				
				PackStylePortalColumn packStylePortalColumnAD = new PackStylePortalColumn();
				packStylePortalColumnAD.setDefcatcode(portalColumn.getDefcatcode());
				packStylePortalColumnAD.setInputmanid(portalColumn.getInputmanid());
				packStylePortalColumnAD.setInputtime(new Date());
				packStylePortalColumnAD.setRemark("修改时默认");
				packStylePortalColumnAD.setStyleid(20L);
				
				this.packStylePortalColumnManager.save(packStylePortalColumnDSJ);
				this.packStylePortalColumnManager.save(packStylePortalColumnAD);
			}
		}
	
		try {
			if (!curPortalColumn.getFocusImgName().equals(portalColumn.getFocusImgName())
					&& 0 != fileopr.copyFileFromLocalToSmb(
							ServerConfigListener.TEMP_PATH + portalColumn.getFocusImgName(), 
							this.onlineJsPath + portalColumn.getParentdir() + "/" + 
							curPortalColumn.getFocusImgName())) {
				throw new Exception(portalColumn.getColumnname() + " 默认皮肤焦点图片复制失败!");
			}
			if (!curPortalColumn.getBlurImgName().equals(portalColumn.getBlurImgName())
					&& 0 != fileopr.copyFileFromLocalToSmb(
							ServerConfigListener.TEMP_PATH + portalColumn.getBlurImgName(), 
							this.onlineJsPath + portalColumn.getParentdir() + "/" + 
							curPortalColumn.getBlurImgName())) {
				throw new Exception(portalColumn.getColumnname() + " 默认皮肤非焦点图片复制失败!");
			}
			
//			if (!curPortalColumn.getCurrentdir().equals(portalColumn.getCurrentdir())
//					&& 0 != fileopr.copyFileFromLocalToSmb(
//							ServerConfigListener.TEMP_PATH + portalColumn.getCurrentdir(), 
//							this.onlineJsPath + portalColumn.getParentdir() + "/" + 
//							curPortalColumn.getCurrentdir())) {
//				throw new Exception(portalColumn.getColumnname() + " 第二套皮肤焦点图片复制失败!");
//			}
//			if (!curPortalColumn.getContentmodeid().equals(portalColumn.getContentmodeid())
//					&& 0 != fileopr.copyFileFromLocalToSmb(
//							ServerConfigListener.TEMP_PATH + portalColumn.getContentmodeid(), 
//							this.onlineJsPath + portalColumn.getParentdir() + "/" + 
//							curPortalColumn.getContentmodeid())) {
//				throw new Exception(portalColumn.getColumnname() + " 第二套皮肤非焦点图片复制失败!");
//			}
//			
//			if (!curPortalColumn.getCovermodeid().equals(portalColumn.getCovermodeid())
//					&& 0 != fileopr.copyFileFromLocalToSmb(
//							ServerConfigListener.TEMP_PATH + portalColumn.getCovermodeid(), 
//							this.onlineJsPath + portalColumn.getParentdir() + "/" + 
//							curPortalColumn.getCovermodeid())) {
//				throw new Exception(portalColumn.getColumnname() + " 第三套皮肤焦点图片复制失败!");
//			}
//			if (!curPortalColumn.getListmodeid().equals(portalColumn.getListmodeid())
//					&& 0 != fileopr.copyFileFromLocalToSmb(
//							ServerConfigListener.TEMP_PATH + portalColumn.getListmodeid(), 
//							this.onlineJsPath + portalColumn.getParentdir() + "/" + 
//							curPortalColumn.getListmodeid())) {
//				throw new Exception(portalColumn.getColumnname() + " 第三套皮肤非焦点图片复制失败!");
//			}
		} catch (Exception e) {
			cmsLog.error("焦点或非焦点图片复制失败!");
			cmsLog.error(e);
			return "栏目修改失败!";
		}

		if (curPortalColumn != null) {
			// 不允许改变以下项目
			portalColumn.setBlurImgName(curPortalColumn.getBlurImgName());
			portalColumn.setFocusImgName(curPortalColumn.getFocusImgName());
//			portalColumn.setCurrentdir(curPortalColumn.getCurrentdir());
//			portalColumn.setContentmodeid(curPortalColumn.getContentmodeid());
//			portalColumn.setCovermodeid(curPortalColumn.getCovermodeid());
//			portalColumn.setListmodeid(curPortalColumn.getListmodeid());
			portalColumn.setIsallchange(2L);					// 为了不让维护完的栏目显示为黑色字体
			portalColumn.setDefcatcode(curPortalColumn.getDefcatcode());
			portalColumn.setIsleaf(curPortalColumn.getIsleaf());
			portalColumn.setDefcatlevel(curPortalColumn.getDefcatlevel());
			portalColumn.setRootid(curPortalColumn.getRootid());
			portalColumn.setParentsid(curPortalColumn.getParentsid());
			portalColumn.setDefcatseq(curPortalColumn.getDefcatseq());
			portalColumn.setInputmanid(curPortalColumn.getInputmanid());
			portalColumn.setInputtime(curPortalColumn.getInputtime());
			portalColumn.setParentdir(curPortalColumn.getParentdir());
			portalColumn.setUpdatedate(new Date());

			if (portalColumn.getIspublish() == null) {
				portalColumn.setIspublish((long) 0);
			}
			if (portalColumn.getIsdomainsub() == null) {
				portalColumn.setIsdomainsub((long) 0);
			}
			cmsLog.debug("修改栏目节点。");
//		} else {
//			cmsLog.debug("未查询到该栏目节点。");
//			cmsResultDto.setResultCode((long) 1);
//			cmsResultDto.setErrorMessage("未查询到该栏目节点。Columnclassid:"
//					+ portalColumn.getColumnclassid());
		}
		
		int childUpdateFlag = 0;
		List<PortalColumn> childPortalColumns = new ArrayList<PortalColumn>();
		if ("N".equals(portalColumn.getIsleaf())) {
			childPortalColumns = this.portalColumnManager.queryChilds(portalColumn.getDefcatseq() + "\\%");
		}
		
		cmsLog.debug("[ " + curPortalColumn.getValidFlag() + " ]-------->[ " + curPortalColumn.getValidFlag() + " ]  ========> " + (!curPortalColumn.getValidFlag().equals(portalColumn.getValidFlag())));
		
		/**
		 * 如果只停播/启用栏目, 则子栏目修改状态为: 1
		 * 如果只修改栏目品牌, 则子栏目修改状态为: 2
		 * 如果即停播/启用栏目, 又修改栏目品牌状态, 则子栏目修改状态为: 3
		 */
		if (!curPortalColumn.getValidFlag().equals(portalColumn.getValidFlag())) {
			cmsLog.debug("停播/启用栏目");
			childUpdateFlag += 1;
		}
//		if (!curPortalColumn.getSiteCode().equals(portalColumn.getSiteCode())) {
//			cmsLog.debug("修改栏目品牌");
//			List<Object[]> list = progListDetailManager.queryScheduleDateAndActionName(
//					curPortalColumn.getColumnclassid());
//			for (PortalColumn p : childPortalColumns) {
//				list.addAll(progListDetailManager.queryScheduleDateAndActionName(
//						p.getColumnclassid()));
//			}
//			cmsLog.debug("当前栏目所属品牌未播发节目包数量为: " + list.size());
//			if (0 < list.size()) {
//				return "当前栏目所属品牌未播发节目包数量为: " + list.size() + " 不允许修改栏目品牌! ";	
//			}
//			childUpdateFlag += 2;
//		}
		
		// 如果启用, 则需要找出停用的父栏目, 一并启用(停用则不需要修改父栏目)
		if (!curPortalColumn.getValidFlag().equals(portalColumn.getValidFlag())
				&& 0 == portalColumn.getValidFlag()) {
			childPortalColumns.addAll(this.portalColumnManager.queryDiffParents(
					Arrays.asList(curPortalColumn.getDefcatseq().split("\\\\")), 2L));
		}
		
		
		try {
			portalColumnManager.update(portalColumn);
			for (PortalColumn childColumn : childPortalColumns) {
				switch (childUpdateFlag) {
					case 1: // 只停播/启用栏目
						childColumn.setValidFlag(portalColumn.getValidFlag());
						break;
					case 2: // 只修改栏目品牌
						childColumn.setSiteCode(portalColumn.getSiteCode());
						break;
					case 3: // 即停播/启用栏目, 又修改栏目品牌状态
						childColumn.setValidFlag(portalColumn.getValidFlag());
						childColumn.setSiteCode(portalColumn.getSiteCode());
						break;
					default: // 什么也不做
						break;
				}
				childColumn.setUpdatedate(new Date());
				portalColumnManager.update(childColumn);
			}
			
			int updateCount = portalColumnManager.updateRootTime();
			cmsLog.debug("刷新ROOT栏目修改时间: " + updateCount);
			this.generateColumnJs();
		} catch (Exception e) {
			return "栏目修改失败!";
		}

		cmsLog.debug("Cms -> PortalColumnServiceImpl -> updatePortalColumn returns.");
		return null;
	}

	// 删除栏目 PortalColumn
	public String deletePortalColumn(String columnclassid) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> deletePortalColumn...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		// 检查栏目ID是否合法
		if (!ParamCheck.hasText(columnclassid, "栏目ID不能为空!")) {
			cmsLog.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}

		// 获利节目包js文件在一级库的存放路径
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"d_column", "Online");
		if (null == descPath || 2 > descPath.size()) {
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		this.onlineJsPath = (String) descPath.get(0);
		this.onlineJsPath = fileopr.checkPathFormatRear(onlineJsPath, '/');
//		
		PortalColumn portalColumn = (PortalColumn) this.portalColumnManager.getById(columnclassid);
		String defaultFocusImg = this.onlineJsPath + portalColumn.getParentdir() 
				+ "/" + portalColumn.getFocusImgName();
		String defaultBlurImg = this.onlineJsPath + portalColumn.getParentdir() 
				+ "/" + portalColumn.getBlurImgName();
//		String secondFocusImg = this.onlineJsPath + portalColumn.getParentdir()
//				+ "/" + portalColumn.getCurrentdir();
//		String secondBlurImg = this.onlineJsPath + portalColumn.getParentdir()
//				+ "/" + portalColumn.getContentmodeid();
//		String thirdFocusImg = this.onlineJsPath + portalColumn.getParentdir()
//				+ "/" + portalColumn.getCovermodeid();
//		String thirdBlurImg = this.onlineJsPath + portalColumn.getParentdir()
//				+ "/" + portalColumn.getListmodeid();
		
		cmsResultDto = cmsTransactionManager.deletePortalColumn(
				portalColumnManager, srvColumnManager, pPColumnRelManager,
				progListDetailManager, progPackageManager, portalColumn);
		
		// 栏目删除成功, 删除栏目默认图标
		if (0 == cmsResultDto.getResultCode()) {
			if (0 != fileopr.deleteSmbFile(defaultFocusImg)) {
				cmsLog.warn("栏目删除成功, 默认皮肤焦点图标[ " + defaultFocusImg + " ]删除失败!");
			}
			if (0 != fileopr.deleteSmbFile(defaultBlurImg)) {
				cmsLog.warn("栏目删除成功, 默认皮肤非焦点图标[ " + defaultBlurImg + " ]删除失败!");
			}
//			if (0 != fileopr.deleteSmbFile(secondFocusImg)) {
//				cmsLog.warn("栏目删除成功, 第二套皮肤焦点图标[ " + secondFocusImg + " ]删除失败!");
//			}
//			if (0 != fileopr.deleteSmbFile(secondBlurImg)) {
//				cmsLog.warn("栏目删除成功, 第二套皮肤非焦点图标[ " + secondBlurImg + " ]删除失败!");
//			}
//			if (0 != fileopr.deleteSmbFile(thirdFocusImg)) {
//				cmsLog.warn("栏目删除成功, 第三套皮肤焦点图标[ " + thirdFocusImg + " ]删除失败!");
//			}
//			if (0 != fileopr.deleteSmbFile(thirdBlurImg)) {
//				cmsLog.warn("栏目删除成功, 第三套皮肤非焦点图标[ " + thirdBlurImg + " ]删除失败!");
//			}
			
			try {
				this.generateColumnJs();
			} catch (Exception e) {
				cmsLog.error(e);
				return "栏目删除失败!";
			}
		} else {
			return cmsResultDto.getErrorMessage();
		}
		
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> deletePortalColumn returns.");
		return null;
	}

	// 保存栏目与服务的配置关系
	public CmsResultDto saveSrvColumnByColumnclassid(List cmsServices,
			String columnclassid) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> saveSrvColumnByColumnclassid...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsResultDto = cmsTransactionManager.saveSrvColumnByColumnclassid(
				portalColumnManager, srvColumnManager, cmsServices,
				columnclassid);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> saveSrvColumnByColumnclassid returns.");
		return cmsResultDto;
	}

	// 根据栏目id，得到栏目与服务的配置关系 SrvColumn
	public CmsResultDto getCmsServicesByColumnclassid(String columnclassid) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getCmsServicesByColumnclassid...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		List cmsServices = srvColumnManager
				.getCmsServicesByColumnclassid(columnclassid);
		cmsResultDto.setResultObject(cmsServices);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getCmsServicesByColumnclassid returns.");
		return cmsResultDto;
	}

	// 得到所有模板 PortalMod 20091109 19:18
	public CmsResultDto getAllPortalMods() {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> getAllPortalMods...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		List portalMods = portalModManager.findAll();
		cmsResultDto.setResultObject(portalMods);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getAllPortalMods returns.");
		return cmsResultDto;
	}

	// 根据栏目，查询得到节目包
	public CmsResultDto getProgPackagesByColumnclassid(String columnclassid) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> getAllPortalMods...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		List progPackages = pPColumnRelManager
				.getProgPackagesByColumnclassid(columnclassid);
		cmsResultDto.setResultObject(progPackages);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getAllPortalMods returns.");
		return cmsResultDto;
	}

	// 得到栏目样式列表
	public CmsResultDto getPortalColumnStyles() {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> getAllPortalMods...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		List<PackStyle> packStyles = packStyleManager.findByProperty(
				"styletype", Long.valueOf(1));
		cmsResultDto.setResultObject(packStyles);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getAllPortalMods returns.");
		return cmsResultDto;
	}

	// 得到栏目角色关系表
	public CmsResultDto getPortalColumnsByRoleids(List<String> roleids) {
		// String roleid : 角色id
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getPortalColumnsByRoleid...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		if (roleids == null) {
			cmsLog.info("roleids == null");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("roleids == null");
			return cmsResultDto;
		}

		List<PortalColumnDto> retlist = new ArrayList<PortalColumnDto>();
		List<PortalColumnDto> noOrderlist = new ArrayList<PortalColumnDto>();

		for (int r = 0; r < roleids.size(); r++) {
			String roleid = (String) roleids.get(r);
			List<PortalRoleOperRel> portalRoleOperRels = portalRoleOperRelManager
					.findByProperty("roleid", roleid);
			for (int i = 0; i < portalRoleOperRels.size(); i++) {
				PortalRoleOperRel portalRoleOperRel = (PortalRoleOperRel) portalRoleOperRels
						.get(i);

				// 判断关系记录对应的栏目是否存在且为叶子节点
				PortalColumnDto portalColumnDto = new PortalColumnDto();
				PortalColumn portalColumn = (PortalColumn) portalColumnManager
						.getById(portalRoleOperRel.getColumnclassid());
				portalColumnDto.setPortalColumn(portalColumn);
				portalColumnDto.setColumnNameOrder(portalColumn.getColumnname()
						+ "[" + portalColumn.getDisplayorder() + "]");

				if (portalColumn == null
						|| !portalColumn.getIsleaf().equalsIgnoreCase("Y")) {
					// 如果不存在，或者不是叶子节点，则删除关系记录
					portalRoleOperRelManager.deleteById(portalRoleOperRel
							.getRelid());
					cmsLog.info("已删除栏目角色关系。Relid:"
							+ portalRoleOperRel.getRelid());
				} else {
					// 判断portalcolumn是否已经再返回数组里
					boolean bExist = false;
					for (int p = 0; p < noOrderlist.size(); p++) {
						PortalColumnDto pc = (PortalColumnDto) noOrderlist
								.get(p);
						if (pc.getPortalColumn().getColumnclassid()
								.equalsIgnoreCase(
										portalColumn.getColumnclassid())) {
							bExist = true;
						}
					}
					if (bExist == false) {
						// 是叶子节点，添加到返回数组里
						noOrderlist.add(portalColumnDto);
					} else {
						// 是叶子节点，但是已经添加到返回数组，不添加到返回数组里
						cmsLog.info("是叶子节点，但是已经添加到返回数组，不添加到返回数组里");
					}
				}
			}
		}

		// 排序noOrderlist，并赋值retlist
		Long curMin = null;
		int size = noOrderlist.size();
		for (int i = 0; i < size; i++) {
			curMin = Long.valueOf(0);
			PortalColumnDto pcdMin = new PortalColumnDto();

			for (int j = 0; j < noOrderlist.size(); j++) {
				PortalColumnDto pcd = (PortalColumnDto) noOrderlist.get(j);
				if (curMin == Long.valueOf(0)
						|| curMin > pcd.getPortalColumn().getDisplayorder()) {
					curMin = pcd.getPortalColumn().getDisplayorder();
					pcdMin = pcd;
				}
			}

			retlist.add(pcdMin);
			noOrderlist.remove(pcdMin);
		}

		cmsResultDto.setResultObject(retlist);
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> getPortalColumnsByRoleid returns.");
		return cmsResultDto;
	}

	// 保存栏目角色关系表
	public CmsResultDto savePortalRoleOperRel(String roleid,
			PortalColumn portalColumn, String inputmanid) {
		// String roleid : 角色id
		// PortalColumn portalColumn : 栏目
		// String inputmanid : 当前操作人员的id
		// 所有叶子节点与角色的关系要保存
		// 作废，当钩选非叶子节点时，该节点下所有的节点自动钩选（包括非叶子和叶子）
		// 作废，当钩选非叶子或叶子节点时，该节点所属的父节点自动钩选。递归：自动钩选父节点时，也需要同样的操作。
		// 作废，当取消钩选非叶子节点时，该节点下所有的节点自动取消钩选（包括非叶子和叶子）
		// 作废，当取消钩选非叶子或叶子节点时，如果该节点所属的父节点下没有任何钩选的节点，则自动取消钩选。递归：自动取消钩选父节点时，也需要同样的操作。

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> savePortalRoleOperRel...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		if (roleid == null || portalColumn == null) {
			cmsLog.info("roleid == null || portalColumn == null");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto
					.setErrorMessage("roleid == null || portalColumn == null");
			return cmsResultDto;
		}

		cmsResultDto = cmsTransactionManager.savePortalRoleOperRel(
				portalRoleOperRelManager, roleManager, portalColumnManager,
				roleid, portalColumn, inputmanid);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> savePortalRoleOperRel returns.");
		return cmsResultDto;
	}

	// 删除栏目角色关系表
	public CmsResultDto deletePortalRoleOperRel(String roleid,
			PortalColumn portalColumn, String inputmanid) {
		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> deletePortalRoleOperRel...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		if (roleid == null || portalColumn == null) {
			cmsLog.info("roleid == null || portalColumn == null");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto
					.setErrorMessage("roleid == null || portalColumn == null");
			return cmsResultDto;
		}

		cmsResultDto = cmsTransactionManager.deletePortalRoleOperRel(
				portalRoleOperRelManager, roleManager, portalColumnManager,
				roleid, portalColumn, inputmanid);

		cmsLog
				.debug("Cms -> PortalColumnServiceImpl -> deletePortalRoleOperRel returns.");
		return cmsResultDto;
	}

	/**
	 * 操作回退
	 * 
	 * @param date
	 *            节目单日期
	 * @param inputid
	 *            操作者ID
	 * @param remark
	 *            回退备注
	 * @return 回退操作结果
	 */
	public CmsResultDto updateRollBack(String date, String inputid,
			String remark) {
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> updateRollBack... ");

		CmsResultDto cmsResultDto = new CmsResultDto();

		try {
			cmsTransactionManager.updateRollBack(progListMangManager,
					progListMangDetailManager, bpmcManager, date, inputid,
					remark);
			cmsResultDto.setResultCode(0l);
			cmsResultDto.setErrorMessage("回退成功!");
		} catch (Exception e) {
			cmsResultDto.setResultCode(1l);
			cmsResultDto.setErrorMessage("操作失败!");
		}

		return cmsResultDto;
	}
	
	/**
	 * HuangBo addition by 2010年8月26日 16时54分
	 * 为报纸查询出报纸的栏目和样式
	 * @return ReturnVo.ArrayList<Object[2]>
	 * object[0]: PortalColumn
	 * object[1]: PackStyle
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList queryPortalColumnAndStyle() {
		return (ArrayList) this.portalColumnManager.getPaperPortalColumn();
	}

	/**
	 * HuangBo addition by 2011年4月21日 13时9分
	 * 根据栏目Code查询该栏目所能应用的样式ID列表
	 * @param defcatcode 栏目Code
	 * @return 样式ID集合
	 */
	public List<Long> queryPackStyleIDByDefcatcode(String defcatcode) {
		List<Long> list = this.packStylePortalColumnManager.getPackStyleIdByDefcatcode(defcatcode);
		return list;
	}
	
	public void generateColumnJs() throws Exception {
		CmsConfig config = new CmsConfig();
		String defaultSkinName = config.getPropertyByName("DefaultSkinName");
//		String secondkinName = config.getPropertyByName("SecondSkinName");
//		String thirdSkinName = config.getPropertyByName("ThirdSkinName");
		
		List<PortalColumn> portalColumns = this.portalColumnManager.queryPortalColumnsByValid();
		
		StringBuilder columnBuilder = new StringBuilder("[");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		for (PortalColumn p : portalColumns) {
			if (cmsLog.isDebugEnabled()) {
				cmsLog.debug("PortalColumn.getDefcatcode       : " + p.getDefcatcode());
				cmsLog.debug("PortalColumn.getStyleid          : " + p.getStyleid());
				cmsLog.debug("PortalColumn.getPublishfilename  : " + p.getPublishfilename());
				cmsLog.debug("PortalColumn.getFocusImgName     : " + p.getFocusImgName());
				cmsLog.debug("PortalColumn.getBlurImgName      : " + p.getBlurImgName());
				cmsLog.debug("PortalColumn.getRemark           : " + p.getRemark());
				cmsLog.debug("PortalColumn.getParentsid        : " + p.getParentsid());
				cmsLog.debug("PortalColumn.getCountnumb        : " + p.getCountnumb());
				cmsLog.debug("PortalColumn.getIsleaf           : " + p.getIsleaf());
				cmsLog.debug("PortalColumn.getValidFlag        : " + p.getValidFlag());
				cmsLog.debug("PortalColumn.getCovermodeid      : " + p.getCovermodeid());
				cmsLog.debug("PortalColumn.getUpdatedate       : " + p.getUpdatedate());
			}
			columnBuilder.append("{" +
					MessageFormat.format(this.ATTRIBUTE, "id", p.getDefcatcode()) + "," +
					MessageFormat.format(this.ATTRIBUTE, "type", this.getColumnType(p.getStyleid())) + "," +
					MessageFormat.format(this.ATTRIBUTE, "name", p.getPublishfilename().trim()) + "," +
//					MessageFormat.format(this.ATTRIBUTEOBJECT, "poster", 
					MessageFormat.format(this.ARRAYOBJECT, "poster", 
									"'" + p.getFocusImgName() + "','" + p.getBlurImgName() + "'") + "," +
//							MessageFormat.format(this.ARRAYOBJECT, secondkinName,
//									"'" + p.getCurrentdir() + "','" + p.getContentmodeid() + "'") + "," +
//							MessageFormat.format(this.ARRAYOBJECT, thirdSkinName,
//									"'" + p.getCovermodeid() + "','" + p.getListmodeid() + "'")) +
					MessageFormat.format(this.ATTRIBUTE, "desc", null == p.getRemark() ? "" : p.getRemark().trim()) +  "," +
					MessageFormat.format(this.ATTRIBUTE, "parent", p.getParentsid().trim()) + "," +
					MessageFormat.format(this.ATTRIBUTENUMBER, "priority", String.valueOf(p.getCountnumb())) + "," +
//					MessageFormat.format(this.ATTRIBUTENUMBER, "brand", "gqpp".equals(p.getSiteCode()) ? 1 : 2) + "," +
					MessageFormat.format(this.ATTRIBUTENUMBER, "ISLEAF", "Y".equals(p.getIsleaf()) ? true : false) + "," +
					MessageFormat.format(this.ATTRIBUTENUMBER, "states", p.getValidFlag()) + "," +
					MessageFormat.format(this.ATTRIBUTENUMBER, "display", p.getCovermodeid()) + "," +
					MessageFormat.format(this.ATTRIBUTE, "timestamp", simpleDateFormat.format(p.getUpdatedate())) + "},");
		}
		
		columnBuilder.setCharAt(columnBuilder.length() - 1, ']');
		columnBuilder.append(';');
		
//		System.out.println(columnBuilder.toString());
		/**
		 * 根据技术部姜磊要求, 去掉js文件中的变量名部分
		 * HuangBo update by 2012年4月16日 10时28分
		 */
//		if (0 != fileopr.createSmbFileGb2312(onlineJsPath + "/column.js", "var columns = " + columnBuilder.toString())) {
		if (0 != fileopr.createSmbFileGb2312(onlineJsPath + "/column.js", columnBuilder.toString())) {
			cmsLog.error("栏目JS文件创建失败!");
			throw new Exception("栏目JS文件创建失败!");
		}
	}
	
	public String getColumnType(Long type) {
//		if (0 == type) {
//			return "目录";
//		} else 
		if (1 == type) {
			return "视频类";
		} else if(2 == type) {
			return "图书类";
		} else if (3 == type) {
			return "杂志类";
		} else if (4 == type) {
			return "报纸类";
		} else {
			return "混合类";
		}
	}
	
	/**
	 * 根据指定日期, 编单定义活动编号, 角色编号, 操作人员. 初始化当天编单, 并获取天编单栏目列表和状态
	 * @param dateStr 编单日期
	 * @param currAct 活动编号
	 * @param roleIds 角色集合
	 * @param operator 操作人员
	 * @return
	 */
	public List<PortalColumn> getProgDetailsState(
			String dateStr, String currAct, List<String> roleIds, String operator) {
		String scheduleDate = dateStr.replaceAll("[-|/| |\\.]", "") + "000000";
		try {
			if (this.PROGACTION.equals(currAct)) {
				this.progListMangManager.saveProgMangAndDetail(scheduleDate, currAct, operator);
			}
			return this.portalColumnManager.queryPortalColumnsByRolesAndValid(scheduleDate, roleIds, currAct);
		} catch (Exception e) {
			cmsLog.error("", e);
			cmsLog.error("初始化日期 [" + dateStr + "] 号的编单失败!");
			return null;
		}
	}
	
	public long convertColumnStyleId2ProgType(long columnStyleId) {
		if (1 == columnStyleId) {
			return 101L;
		} else if (2 == columnStyleId) {
			return 12L;
		} else if (3 == columnStyleId) {
			return 10L;
		} else if (4 == columnStyleId) {
			return 8L;
		} else {
			return 20L;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String initPortalColumnImg() throws MalformedURLException, SmbException {
		/**
		 * 首先初始化数据库栏目表多套皮肤图标数据
		 */
		CmsConfig config = new CmsConfig();
		String defaultSkinName = config.getPropertyByName("DefaultSkinName").trim();
//		String secondSkinName = config.getPropertyByName("SecondSkinName").trim();
//		String thirdSkinName = config.getPropertyByName("ThirdSkinName").trim();
		
		List<PortalColumn> proColumns = this.portalColumnManager.findAll();
		for (PortalColumn portalColumn : proColumns) {
			portalColumn.setFocusImgName(defaultSkinName + portalColumn.getDefcatcode() + "_focus.png");
			portalColumn.setBlurImgName(defaultSkinName + portalColumn.getDefcatcode() + "_blur.png");
//			portalColumn.setCurrentdir(secondSkinName + portalColumn.getDefcatcode() + "_focus.png");
//			portalColumn.setContentmodeid(secondSkinName + portalColumn.getDefcatcode() + "_blur.png");
//			portalColumn.setCovermodeid(thirdSkinName + portalColumn.getDefcatcode() + "_focus.png");
//			portalColumn.setListmodeid(thirdSkinName + portalColumn.getDefcatcode() + "_blur.png");
			
			this.portalColumnManager.update(portalColumn);
		}
		
		
		/**
		 * 在指定路径初始化默认图标
		 */
		List<?> descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"d_column", "Online");
		if (null == descPath || 2 > descPath.size()) {
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		this.onlineJsPath = (String) descPath.get(0);
		this.onlineJsPath = fileopr.checkPathFormatRear(onlineJsPath, '/');
		
		List<PortalColumn> portalColumns = this.portalColumnManager.findAll();
		for (PortalColumn portalColumn : portalColumns) {
			String defaultSkinFocusImgName = this.onlineJsPath + portalColumn.getParentdir() + "/" + portalColumn.getFocusImgName();
			String defaultSkinBlurImgName = this.onlineJsPath + portalColumn.getParentdir() + "/" + portalColumn.getBlurImgName();
//			String secondSkinFocusImgName = this.onlineJsPath + portalColumn.getParentdir() + "/" + portalColumn.getCurrentdir();
//			String secondSkinBlurImgName = this.onlineJsPath + portalColumn.getParentdir() + "/" + portalColumn.getContentmodeid();
//			String thirdSkinFocusImgName = this.onlineJsPath + portalColumn.getParentdir() + "/" + portalColumn.getCovermodeid();
//			String thirdSkinBlurImgName = this.onlineJsPath + portalColumn.getParentdir() + "/" + portalColumn.getListmodeid();
			
			SmbFile defaultSkinFocusImg = new SmbFile(defaultSkinFocusImgName);
			SmbFile defaultSkinBlurImg = new SmbFile(defaultSkinBlurImgName);
//			SmbFile secondSkinFocusImg = new SmbFile(secondSkinFocusImgName);
//			SmbFile secondSkinBlurImg = new SmbFile(secondSkinBlurImgName);
//			SmbFile thirdSkinFocusImg = new SmbFile(thirdSkinFocusImgName);
//			SmbFile thirdSkinBlurImg = new SmbFile(thirdSkinBlurImgName);
			
			if (!defaultSkinFocusImg.exists()
					&& !defaultSkinBlurImg.exists()) {
				fileopr.copyFileFromLocalToSmb(ServerConfigListener.REAL_PATH + "images/init_focus.png", defaultSkinFocusImgName);
				fileopr.copyFileFromLocalToSmb(ServerConfigListener.REAL_PATH + "images/init_blur.png", defaultSkinBlurImgName);
			} else {
				return "初始化栏目图标失败!";
			}
//			if (!secondSkinFocusImg.exists()) {
//				fileopr.copyFileFromLocalToSmb(ServerConfigListener.REAL_PATH + "../../SOAPlatSys/images/init.png", secondSkinFocusImgName);
//			}
//			if (!secondSkinBlurImg.exists()) {
//				fileopr.copyFileFromLocalToSmb(ServerConfigListener.REAL_PATH + "../../SOAPlatSys/images/init.png", secondSkinBlurImgName);
//			}
//			if (!thirdSkinFocusImg.exists()) {
//				fileopr.copyFileFromLocalToSmb(ServerConfigListener.REAL_PATH + "../../SOAPlatSys/images/init.png", thirdSkinFocusImgName);
//			}
//			if (!thirdSkinBlurImg.exists()) {
//				fileopr.copyFileFromLocalToSmb(ServerConfigListener.REAL_PATH + "../../SOAPlatSys/images/init.png", thirdSkinBlurImgName);
//			}
		}
		return "初始化栏目图标成功!";
	}

	/**
	 * 添加栏目产品关系
	 * @param columnId 栏目编号
	 * @param productIds 产品列表
	 * @param inputManId 操作人员ID
	 * @return 
	 */
	public String addColumnProductRel(String columnId, List<String> productIds, String inputManId) {
		try {
			this.cmsSiteProductRelManager.save(columnId, productIds, inputManId);
			this.portalColumnManager.updateRootTime();
			this.generateColumnJs();
			return null;
		} catch (Exception e) {
			cmsLog.debug("栏目产品关系添加失败: " + e.getMessage());
			return " 栏目产品关系添加失败! ";
		}
	}
	
	/**
	 * 删除栏目产品关系
	 * @param siteId 栏目ID
	 * @param ids 产品列表
	 * @return
	 */
	public String delColumnProductRel(String columnId, List<String> ids) {
		try {
			List<String> keyIds = this.productManager.queryByids(ids);
			List<String> progPackageIds = this.pricingManager.queryPackageIdByKeyIdsColumnId(keyIds, columnId);
			cmsLog.debug("绑定该栏目的节目包数量: " + progPackageIds.size());
			if (0 < progPackageIds.size()) {
				return "[" + progPackageIds.size() + "]个节目包已绑定该栏目, 不允许删除!";
			}
			this.cmsSiteProductRelManager.deleteById(columnId, ids);
			this.portalColumnManager.updateRootTime();
			this.generateColumnJs();
			return null;
		} catch (Exception e) {
			cmsLog.debug("品牌产品关系删除失败: " + e.getMessage());
			return " 品牌产品关系删除失败! ";
		}
	}
	
	/**
	 * 根据栏目编号,查询产品信息
	 * @param columnId 栏目编号
	 * @return
	 */
	public List<ProgProduct> getProgProductsByColumnId(String columnId) {
		return this.productManager.queryProducts(columnId);
	}
}
