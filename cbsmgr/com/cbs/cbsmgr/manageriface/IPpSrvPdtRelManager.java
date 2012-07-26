package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IPpSrvPdtRelManager extends IBaseManager {

	public List getPpSrvPdtRelsByProductIdAndProductCategoryId(String productId, Long productCategoryId);
}
