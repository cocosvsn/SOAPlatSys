package com.cbs.cbsmgr.manageriface;

import java.util.Date;
import java.util.List;

public interface IVodHistoryManager extends IBaseManager {

	// 根据customerId、dateFrom、dateTo，查询表VodHistory，得到点播流水列表
	public List getVodFlowDTOsByCustomerIdAndDate(Long customerId, Date dateFrom, Date dateTo);
	
	// 20100322 21:25
	// 点播历史报表
	public List reportVodHistories(
			Long customerId,				// 
			String vodDateFrom,				// 格式：yyyy-MM-dd
			String vodDateTo				// 格式：yyyy-MM-dd
			);
	
}
