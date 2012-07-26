package com.cbs.cbsmgr.manageriface;

import java.util.List;

import com.cbs.cbsmgr.bean.VodFlow;

public interface IVodFlowManager extends IBaseManager {

	// 查询所有需要分析的点播流水
	public List getNotDealedVodFlowsWithCount(int firstResult, int maxResults);
}
