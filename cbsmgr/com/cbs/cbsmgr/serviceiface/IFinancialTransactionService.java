package com.cbs.cbsmgr.serviceiface;

import java.util.List;

public interface IFinancialTransactionService extends IBaseService {
	
	// �õ�accountid��δ���ʵ��Ľ�Ĳ������б�
	public List getDebitNotInInvoiceFts(Long accountId);
	
	// �õ�accountid��δ���ʵ��Ĵ��Ĳ������б�
	public List getCreditNotInInvoiceFts(Long accountId);
	
	// �õ�accountid��δ���ʵ������еĲ������б�
	public List getAllNotInInvoiceFts(Long accountId);
}
