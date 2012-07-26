package com.soaplat.cmsmgr.service;

import java.util.List;

import com.soaplat.cmsmgr.bean.PortalPackage;
import com.soaplat.cmsmgr.dto.CmsResultDto;

public interface PortalServiceIface {

	// 20100114 10:50
	// 生成Portal。界面选择一天栏目单分表记录，点击“生成Portal”。
	public CmsResultDto generatePortalByDate(
			String date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			String defcatseq,					// 栏目code序列
			String currentIdAct, 				// 当前活动编号
			String operatorId					// 操作人员id
			);
	
	// 这就是传说中的方法14
	// 20100121 11:20
	// 根据，得到Portal模板文件zip上传的路径
	public CmsResultDto getPortalModelDestpathByFilepath(
			String filepath						// filepath，期待传入模板ID
			);
	
	// 20100130
	// 压缩portal目录，判断所有（总单）的栏目单分表的活动是否“PORTAL完成”FU00000083，都是才能压缩，然后改分表和总表活动为“预览”FU00000084
	public CmsResultDto zipPortal(
			String date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			String operatorId					// 操作人员id
			);
	
	
	// 20100408 15:05
	// 发送总表分表活动，不管理portal
	public CmsResultDto sendProgListMang(
			String date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			String operatorId					// 操作人员id
			);
	
	// 20100330
	// 生成Portal，根据最新讨论结果修改
	// 复制小文件（海报、xml），生成js，到播发库（一级库），不管理Portal模板和Portal。
	public CmsResultDto generatePortalWithoutModel(
			String date,						// 编单日期，不是编单的日期，格式 "yyyy-MM-dd"
			String defcatseq,					// 栏目code序列
			String currentIdAct, 				// 当前活动编号
			String operatorId					// 操作人员id
			);
	
	// 20100414 15:11
	// 查询图文记录（TPORTALPACKAGE），根据
	public CmsResultDto getPortalPackages(
			String ptpname,				// 页面包装名称
			String columnclassid,		// 栏目ID
			Long onlinetag				// 上线标识，0 - 下线，1 - 上线
			);
	
	// 20100414 19:42
	// 保存（新建）图文记录（TPORTALPACKAGE），同时保存PORTAL页面包装和节目包关系表（TPTPPGPREL）记录
	public CmsResultDto savePortalPackage(
			PortalPackage portalPackage,		// 页面包装对象
//			List ptpPgpRels,					// 页面包装与节目包关系对象
			String operatorId					// 操作人员ID
			);
	
	// 20100414 19:49
	// 修改图文记录（TPORTALPACKAGE），同时保存PORTAL页面包装和节目包关系表（TPTPPGPREL）记录
	public CmsResultDto updatePortalPackage(
			PortalPackage portalPackage,		// 页面包装对象
//			List ptpPgpRels,					// 页面包装与节目包关系对象
			String operatorId					// 操作人员ID
			);
	
	// 20100419 11:21
	// 删除页面包装
	public CmsResultDto deletePortalPackage(
			String ptpid,
			String operatorId
			);
	
}
