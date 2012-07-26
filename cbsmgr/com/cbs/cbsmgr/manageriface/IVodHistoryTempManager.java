package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IVodHistoryTempManager extends IBaseManager {

	// 根据客户Id、vodProductId，查询表vodHistoryTemp，得到所有点播记录的amount总和
	public Double getTotalAmountByCustomerIdAndVodProductId(Long customerId, String vodProductId);
	
	// 20091224 13:12
	// 分页查询VodHistory表中记录，条件：未处理状态，排序：客户，点播日期
	public List getNotDealedVodHistoryTempsWithCount(int firstResult, int maxResults);
}
