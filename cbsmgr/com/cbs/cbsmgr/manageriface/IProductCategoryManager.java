package com.cbs.cbsmgr.manageriface;

import java.util.List;

import com.cbs.cbsmgr.bean.ProductCategory;

public interface IProductCategoryManager extends IBaseManager {

	public List getProductCategoriesByDescription(String description);
	
	// 根据文件ID查询得到产品
	public List getProductCategoriesByProgfileid(String progfileid);
}
