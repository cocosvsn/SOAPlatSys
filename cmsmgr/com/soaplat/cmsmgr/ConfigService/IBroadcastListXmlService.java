package com.soaplat.cmsmgr.ConfigService;

import java.util.List;

import com.soaplat.cmsmgr.dto.CmsResultDto;

public interface IBroadcastListXmlService {

	// 根据日期和栏目单code序列，查询，得到栏目单详细和节目包

	public CmsResultDto BroadcastListXml(String date, // yyyy-MM-dd
//			String defcatseq, // 栏目code序列
			String operatorId, // 操作人员id
			String plandate) ;
	
	// 20100329 14:24
	// 生成播发单
	public CmsResultDto generateBroadcastXml(
			String date, 			// yyyy-MM-dd
			String operatorId, 		// 操作人员id
			String plandate			// 计划播发日期，格式："yyyy-MM-dd HH:mm:ss"
			);
}
