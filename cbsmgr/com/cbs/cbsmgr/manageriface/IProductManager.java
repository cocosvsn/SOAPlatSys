package com.cbs.cbsmgr.manageriface;

import java.util.Date;
import java.util.List;

public interface IProductManager extends IBaseManager {

	// 得到Product，根据accountId productCategoryId
	public List getProductByAccountIdAndProductCategoryId(Long accountId, Long productCategoryId);
	
	// 得到正常状态 的product
	public List getNormalStatusProductsByCustomerId(Long customerId);
	
	// 得到客户id下所有的硬件product
	public List getHardwareProductsByCustomerId(Long customerId);
	
	// 得到正常状态 的product
	public List getNormalProductsByCustomerId(Long customerId);
	
	// 20091224 15:36
	// 分页查询Product表中记录，条件：正常状态、周期性产品、计费有效期=date，排序：客户，创建日期
	public List getProductsForGeneratingPeriodFee(Date date, int firstResult, int maxResults);
	
	// 20091225 14:55
	// 根据username，查询Hardware、Product，得到Product
	public List getProductsByUsername(String userName);
}
