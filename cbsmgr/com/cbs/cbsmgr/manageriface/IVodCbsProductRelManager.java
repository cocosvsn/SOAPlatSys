package com.cbs.cbsmgr.manageriface;

import java.util.List;

import com.cbs.cbsmgr.bean.VodCbsProductRel;

public interface IVodCbsProductRelManager extends IBaseManager {

	// ����vodProductId,productCategoryId����ѯ��ϵ��¼
	public VodCbsProductRel getVodCbsProductRelByVodProductIdAndProductCategoryId(String vodProductId, Long productCategoryId);
}
