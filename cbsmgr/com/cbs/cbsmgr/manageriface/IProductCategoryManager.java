package com.cbs.cbsmgr.manageriface;

import java.util.List;

import com.cbs.cbsmgr.bean.ProductCategory;

public interface IProductCategoryManager extends IBaseManager {

	public List getProductCategoriesByDescription(String description);
	
	// �����ļ�ID��ѯ�õ���Ʒ
	public List getProductCategoriesByProgfileid(String progfileid);
}
