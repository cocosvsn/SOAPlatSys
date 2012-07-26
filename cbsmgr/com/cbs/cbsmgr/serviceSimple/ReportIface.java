package com.cbs.cbsmgr.serviceSimple;

import java.util.Date;

import com.cbs.cbsmgr.dto.CbsResultDto;

public interface ReportIface {

	
	// 20100322 19:49
	// 财务报表
	public CbsResultDto reportFTs(
			Long customerId,			
			String createDateFrom,			// 格式：yyyy-MM-dd
			String createDateTo				// 格式：yyyy-MM-dd
			);
	
	// 20100322 20:16
	// 点播历史报表
	public CbsResultDto reportVodHistories(
			Long customerId,				// 
			String vodDateFrom,				// 格式：yyyy-MM-dd
			String vodDateTo				// 格式：yyyy-MM-dd
			);
}
