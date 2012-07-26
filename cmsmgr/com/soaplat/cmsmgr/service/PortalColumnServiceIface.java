package com.soaplat.cmsmgr.service;

import java.util.Date;
import java.util.List;

import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.PortalRoleOperRel;
import com.soaplat.cmsmgr.bean.ProgList;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.dto.CmsResultDto;

public interface PortalColumnServiceIface {

	
	//------------------------------------- ProgList ------------------------------------------------------
	// test passed 20091109 19:28
	// 得到栏目单
	public CmsResultDto getAllProgLists();
	
	// 得到栏目单
	public CmsResultDto getProgListsByIdact(String idact, String pop);
	
	// 保存（新建）栏目单
	public CmsResultDto saveProgList(ProgList progList);
	
	// 根据栏目路径序列和栏目单编号，查询得到栏目单的栏目下的节目包
//	public CmsResultDto getProgListDetailsByPdateAndDefcatseq(String pdate, String defcatseq);
	
	// 添加栏目单详细 ProgListDetail
	public CmsResultDto addProgListDetailsToProgList(List progPackages, String pdate, String columnclassid, List lnums);
	
	// 删除栏目单详细 ProgListDetail
	public CmsResultDto deleteProgListDetailsFromProgList(List progListDetails);
	
	// test passed 20091111 10:56
	// 更新栏目单详细 ProgListDetail 20091111 10:39
	// progListDetails : 需要更新的栏目单详细
	// offlineDate : 需要更新的下线日期，包含日期时间
	public CmsResultDto updateProgListDetails(List<ProgListDetail> progListDetails, Date offlineDate);
	

	//-------- 流程控制 --------------
	// test passed 20091112 15:02
	// 根据当前活动，查询下一活动和操作人员
	public CmsResultDto getNextOperationByCurOperation(String curIdProcess);
	
	// test passed 20091112 17:27
	// 发送栏目单到下一活动idProcess，下一活动人，
	public CmsResultDto sendProgList(
			ProgList progList, 
			String nextIdAct, 
			String nextOperator, 
			String nextState2, 
			String operator,
			String sendremark
			);
	
	// --------- 栏目编单修改 20091202 -----------
	// 添加节目包progPackage，到栏目单（详细）ProgListDetail，20091202 21:16
	public CmsResultDto saveProgListDetails(
			List<ProgPackage> progPackages, 		// 节目包
			String date,							// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			Date offlineDate,						// 这批节目包的下线日期，包括时间
			String columnclassid,					// 栏目编号
			List<String> lnums,						// 节目包的序号
			String userId							// 操作人员的id
			);
	
	// 删除节目包progListDetail，从栏目单（详细）ProgListDetail，20091202 21:16
	// 只能删除上线日期==当前栏目单的日期的
	public CmsResultDto deleteProgListDetails(List<ProgListDetail> progListDetails, String date);
	
	// 修改栏目单（详细)ProgListDetail，20091202 21:16 
//	public CmsResultDto updateProgListDetails(List<ProgListDetail> progListDetails);
	
	// 报警查看
	public CmsResultDto viewProgListDetailAlarm(String date);
	
	// 发送栏目单（详细），到下一活动idProcess，下一活动人，
	public CmsResultDto sendProgListDetails(
			List<ProgListDetail> progListDetails,
			String nextIdAct, 
			String nextOperator, 
			String nextState2, 
			String operator,
			String sendremark
			);
	
	// 发布栏目单（详细）
	public CmsResultDto publishProgListDetails();
	
	// 根据日期和栏目，查询栏目单详细，得到栏目单详细列表。date格式：yyyy-MM-dd
	// date: 栏目单的日期，格式：yyyy-MM-dd
	// defcatseq : 栏目的code
	public CmsResultDto getProgListDetailsByDateAndDefcatseq(String date, String defcatseq, String operator);
	
	
	// 20091226
	// ------------- 新一轮的修改开始了，来吧 -----------------------
	// 在发送的时候，不修改栏目单详细里的活动，修改栏目单分表的活动，同时需要判断总表是否需要修改活动
	// 发送栏目单（详细），到下一活动idProcess，下一活动人，
	// 目前，界面不选择下一活动和下一活动人
	public CmsResultDto sendProgListMangDetails(
			String date,								// 日期，格式：yyyy-MM-dd
//			List<PortalColumn> portalColumns,			// 需要发送的栏目单详细
			String defcatseq,							// 栏目的代码序列
//			String nextIdAct, 							// 不使用，下一活动编号，
//			String nextOperator, 						// 不使用，下一操作人员，不使用
//			String nextState2, 							// 不使用，下一活动的性质：（新建N   回退R  顺序P  完成C）
			String currentIdAct, 						// 当前活动编号
			String operator,							// 当前操作人员
			String sendremark							// 备注
			);
	
	// 在回退的时候，修改栏目单分表的活动为上一活动，同时需要判断总表是否需要修改活动
	// 回退栏目单（详细），到上一活动idProcess，上一活动人，
	// 目前，界面不选择下一活动和下一活动人
	public CmsResultDto backProgListMangDetails(
			String date,								// 日期，格式：yyyy-MM-dd
//			List<PortalColumn> portalColumns,			// 需要发送的栏目单详细
			String defcatseq,							// 栏目的代码序列
//			String nextIdAct, 							// 不使用，下一活动编号
//			String nextOperator, 						// 不使用，下一操作人员
//			String nextState2, 							// 不使用，下一活动的性质：（新建N   回退R  顺序P  完成C）
			String currentIdAct, 						// 当前活动编号
			String operator,							// 当前操作人员
			String sendremark							// 备注
			);
	
	// 20100121 14:20
	// 在发送的时候，不修改栏目单详细里的活动，修改栏目单分表的活动，同时需要判断总表是否需要修改活动
	// 发送本地栏目单（详细），到下一活动idProcess，下一活动人，
	// 目前，界面不选择下一活动和下一活动人
	public CmsResultDto sendLocalProgListMangDetails(
			String date,								// 日期，格式：yyyy-MM-dd
//			List<PortalColumn> portalColumns,			// 需要发送的栏目单详细
			String defcatseq,							// 栏目的代码序列
//			String nextIdAct, 							// 不使用，下一活动编号，
//			String nextOperator, 						// 不使用，下一操作人员，不使用
//			String nextState2, 							// 不使用，下一活动的性质：（新建N   回退R  顺序P  完成C）
			String currentIdAct, 						// 当前活动编号
			String operator,							// 当前操作人员
			String sendremark							// 备注
			);
	
	// 20100121 14:20
	// 在回退的时候，修改栏目单分表的活动为上一活动，同时需要判断总表是否需要修改活动
	// 回退本地栏目单（详细），到上一活动idProcess，上一活动人，
	// 目前，界面不选择下一活动和下一活动人
	public CmsResultDto backLocalProgListMangDetails(
			String date,								// 日期，格式：yyyy-MM-dd
//			List<PortalColumn> portalColumns,			// 需要发送的栏目单详细
			String defcatseq,							// 栏目的代码序列
//			String nextIdAct, 							// 不使用，下一活动编号
//			String nextOperator, 						// 不使用，下一操作人员
//			String nextState2, 							// 不使用，下一活动的性质：（新建N   回退R  顺序P  完成C）
			String currentIdAct, 						// 当前活动编号
			String operator,							// 当前操作人员
			String sendremark							// 备注
			);
	
	// 发布用生成xml(本地有效的栏目)，20100125 18:03
	public CmsResultDto generatePortalXmlForLocal(
			String date,								// 日期，格式：yyyy-MM-dd
			String operator								// 当前操作人员
			);
	
	// 发布用生成xml，20091230 13:20
	public CmsResultDto generatePortalXml(
			String date,								// 日期，格式：yyyy-MM-dd
			String operator								// 当前操作人员
			);
	
	// 根据日期、栏目、当前活动，查询得到栏目单状态，20091231 10:18
	public CmsResultDto getProgListMangDetails(
			List<String> dates,							// 日期，格式：yyyy-MM-dd
			String defcatseq,							// 栏目的代码序列
			String currentIdAct, 						// 当前活动编号
			String operator								// 当前操作人员
			);
	
	// 20100121 16:27
	// 根据日期、栏目、当前活动，查询得到栏目单状态，20091231 10:18
	public CmsResultDto getLocalProgListMangDetails(
			List<String> dates,							// 日期，格式：yyyy-MM-dd
			String defcatseq,							// 栏目的代码序列
			String currentIdAct, 						// 当前活动编号
			String operator								// 当前操作人员
			);
	
	// 20100108 10:24
	// 根据日期，查询栏目单总表或者主表管理表TPROGLISTMANG，
	// 返回：
	public CmsResultDto getProgListMangsByDate(
			String dateFrom,							// 日期起始，格式：yyyy-MM-dd
			String dateTo								// 日期终止，格式：yyyy-MM-dd
			);
	
	// 20100108 10:24
	// 根据栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
	// 返回：
	public CmsResultDto getProgListMangDetailsByScheduledate(
			String scheduledate							// 栏目单总表的主键
			);
	
	// 20100121 13:45
	// 根据本地栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
	// 返回：
	public CmsResultDto getLocalProgListMangDetailsByScheduledate(
			String scheduledate							// 栏目单总表的主键
			);
	
	// 20100130
	// 刷新栏目单详细中节目包的状态state1， 节目包状态，0-导入 1-缓存库 2-加扰库 3-播控库
	public CmsResultDto refreshState1OfProgPackage(
			String stclasscodeNearOnline,		// 缓存库存储体等级code
			String stclasscodeCaOnline,			// 加扰库存储体等级code
			String stclasscodeOnline,			// 播控库存储体等级code
			ProgListDetail progListDetail
			);
	
	// 20100126 21:40
	// 刷新date当天栏目单详细的节目包状态
	public CmsResultDto refreshState1sInProgListDetail(
			String date						// 日期，格式：yyyy-MM-dd
			);
	
	// 20100221 14:15
	// 数据导入，分析上海导出的xml，把节目包和编单信息导入到北京的cms
	public CmsResultDto importPortalXmlToBjCms(
			String operator								// 当前操作人员
			);
	
	// 20100221 17:12
	// 数据导入，把上海导出的节目包和节目包文件信息，导入到北京的cms
	public CmsResultDto importProgPackageFilesToBjCms(
			String operator								// 当前操作人员
			);
	
	// 20100223 17:35
	// 数据导入，包括节目包和编单
	public CmsResultDto importDataToBjCms(
			String operator								// 当前操作人员
			);
	
	
	
	
	
	
	
	
	
	//------------------------------------- PortalColumn ------------------------------------------------------
	// test passed 20091109 16:17
	// 得到所有的栏目 PortalColumn
	public CmsResultDto getAllPortalColumns();
	
	// test passed 20091109 16:17
	// 添加节目包到栏目 ProgPackage PortalColumn 
	public CmsResultDto addProgPackagesToPortalColumn(List progPackages,String columnclassid);
	
	// test passed 20091109 16:17
	// 删除节目包从栏目 ProgPackage PortalColumn
	public CmsResultDto deleteProgPackagesFromPortalColumn(List progPackages,String columnclassid);
	
	// test passed 20091109 16:17
	// 保存（新建）栏目 PortalColumn
	// portalColumn中，需要指定父节点的code
	public String savePortalColumn(PortalColumn portalColumn);
	
	// 修改（更新）栏目 PortalColumn
	public String updatePortalColumn(PortalColumn portalColumn);
	
	// test passed 20091109 16:17
	// 删除栏目 PortalColumn
	public String deletePortalColumn(String columnclassid);
	
	// test passed 20091109 16:17
	// 保存栏目与服务的配置关系
	public CmsResultDto saveSrvColumnByColumnclassid(List cmsServices, String columnclassid);
	
	// test passed 20091109 17:20
	// 根据栏目id，得到栏目与服务的配置关系 SrvColumn
	public CmsResultDto getCmsServicesByColumnclassid(String columnclassid);
	
	// test passed 20091109 19:25
	// 得到所有模板 PortalMod 20091109 19:18
	public CmsResultDto getAllPortalMods();
	
	// 根据栏目，查询得到节目包
	public CmsResultDto getProgPackagesByColumnclassid(String columnclassid);
	
	// 得到栏目样式列表
	public CmsResultDto getPortalColumnStyles();
	
	// 得到栏目角色关系表
	public CmsResultDto getPortalColumnsByRoleids(List<String> roleids);
	
	// 保存栏目角色关系表
	public CmsResultDto savePortalRoleOperRel(String roleid, PortalColumn portalColumn, String inputmanid);
	
	// 删除栏目角色关系表
	public CmsResultDto deletePortalRoleOperRel(String roleid, PortalColumn portalColumn, String inputmanid);
	
	// 保存栏目角色关系表，不使用
//	public CmsResultDto savePortalRoleOperRels(String roleid, List<PortalColumn>portalColumns, String inputmanid);
}
