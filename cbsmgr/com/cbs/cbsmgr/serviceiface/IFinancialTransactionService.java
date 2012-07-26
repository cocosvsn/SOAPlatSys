package com.cbs.cbsmgr.serviceiface;

import java.util.List;

public interface IFinancialTransactionService extends IBaseService {
	
	// 得到accountid的未入帐单的借的财务交易列表
	public List getDebitNotInInvoiceFts(Long accountId);
	
	// 得到accountid的未入帐单的贷的财务交易列表
	public List getCreditNotInInvoiceFts(Long accountId);
	
	// 得到accountid的未入帐单的所有的财务交易列表
	public List getAllNotInInvoiceFts(Long accountId);
}
