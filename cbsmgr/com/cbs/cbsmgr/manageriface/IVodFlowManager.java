package com.cbs.cbsmgr.manageriface;

import java.util.List;

import com.cbs.cbsmgr.bean.VodFlow;

public interface IVodFlowManager extends IBaseManager {

	// ��ѯ������Ҫ�����ĵ㲥��ˮ
	public List getNotDealedVodFlowsWithCount(int firstResult, int maxResults);
}
