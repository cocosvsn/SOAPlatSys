package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IVodCampaignCategoryManager extends IBaseManager {

	// 得到所有有效的点播优惠
	public List getValidVodCampaignCategories();
}
