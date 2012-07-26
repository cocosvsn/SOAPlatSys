package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IFinancialTransactionManager extends IBaseManager {

	// 得到accountid的未入帐单的借的财务交易列表
	public List getDebitNotInInvoiceFts(Long accountId);
	
	// 得到accountid的未入帐单的贷的财务交易列表
	public List getCreditNotInInvoiceFts(Long accountId);
	
	// 得到accountid的未入帐单的所有的财务交易列表
	public List getAllNotInInvoiceFts(Long accountId);
	
	
	// 查询未入账单的财务交易 Edited by Andy 20091223 17:10
	public List getNotInInvoiceFts(int firstResult, int maxResults);
	
	// 20100322 20:45
	// 财务报表
	public List reportFTs(
			Long customerId,			
			String createDateFrom,			// 格式：yyyy-MM-dd
			String createDateTo				// 格式：yyyy-MM-dd
			);
}
