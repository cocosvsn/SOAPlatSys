package com.cbs.cbsmgr.manageriface;

import java.util.List;

import com.cbs.cbsmgr.bean.VodCbsProductRel;

public interface IVodCbsProductRelManager extends IBaseManager {

	// 根据vodProductId,productCategoryId，查询关系记录
	public VodCbsProductRel getVodCbsProductRelByVodProductIdAndProductCategoryId(String vodProductId, Long productCategoryId);
}
