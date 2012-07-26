package com.cbs.cbsmgr.serviceSimple;

import java.util.Date;
import java.util.List;

import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.bean.Invoice;
import com.cbs.cbsmgr.bean.SmsAccount;
import com.cbs.cbsmgr.dto.AccountDTO;
import com.cbs.cbsmgr.dto.AccountDisplayDTO;
import com.cbs.cbsmgr.dto.InvoiceDisplayDTO;

public interface SessionAccountIface {

	// 捕获帐户
	public Long CaptureAccount(Long customerId, AccountDTO accountDTO, Long accountType, Long accountStatus);
	
	// 修改帐户其他信息
	public Long modifyAccountInfo(Long accountId, AccountDTO accountDTO);
	
	// 修改帐户类型
	public Long changeAccountType(Long accountId, Long accountType);
	
	// 修改帐户状态
	public Long changeAccountStatus(Long accountId, Long accountStatus);
	
	// 创建帐单
	public Invoice createInvoice(Long accountId, Date dueDate);
	
	// 根据客户编号得到帐户列表
	public List getAccountsByCustomerId(Long customerId);
	
	// 根据客户ID得到帐户列表
	public List getAccountDisplayDTOsByCustomerId(Long customerId);
	
	// 根据帐户ID，得到AccountDisplayDTO
	public AccountDisplayDTO getAccountDisplayDTOByAccountId(Long accountId);
	
	// 根据帐户编号得到帐户
	public Account getAccountByAccountId(Long accountId);
	
	// 根据客户ID得到财务交易FtDisplayDTO列表
	public List getFtDisplayDTOsByCustomerId(Long customerId);
	
	// 根据帐户ID得到财务交易列表
	public List getFinancialTransactionsByAccountId(Long accountId); 
	
	// 根据帐户ID得到财务交易FtDisplayDTO列表
	public List getFtDisplayDTOsByAccountId(Long accountId);
	
	// 根据客户ID得到帐单列表
	public List getInvoicesByCustomerId(Long customerId);
	
	// 根据帐户ID得到帐单列表
	public List getInvoicesByAccountId(Long accountId);
	
	// 根据帐单ID得到财务交易列表
	public List getFinancialTransactionsByInvoiceId(Long invoiceId);
	
	// 根据帐单ID得到财务交易FtDisplayDTO列表
	public List getFtDisplayDTOsByInvoiceId(Long invoiceId);
	

	// 根据帐户ID得到未入帐金额-借
	public Double getNotInInvoiceAmountDebitByAccountId(Long accountId);
	
	// 根据帐户ID得到未入帐金额-贷
	public Double getNotInInvoiceAmountCreditByAccountId(Long accountId);
	
	// 帐单付费
	public Long payInvoice(Long invoiceId, Double amount);
	
	// 手工添加财务交易
	public Long manualAddFinancialTransaction(Long accountId, Long productId, Double amount, Long smsAccountId);
	
	// 删除财务交易
	public Long deleteFinancialTransaction(Long ftId);
	
	// 恢复删除财务交易
	public Long cancelDeleteFinancialTransaction(Long ftId);
	
	// 退还款项

	
	
	
	//************************************************************************************************
	
//	// 创建开帐周期
//	public Long createInvoicePeriod(String description, Long invoicePeriod);
//	
//	// 修改开帐周期
//	public void modifyInvoicePeriod(Long invoicePeriodId, String description, Long invoicePeriod);
//	
//	// 创建分类帐户
//	public void createSmsAccount(String description1, String invoiceLineDes1, String debitCredit1, 
//			String description2, String invoiceLineDes2, String debitCredit2);
//	
//	// 修改分类帐户
//	public void modifySmsAccount(Long smsAccountId, String description, String invoiceLineDes);
	
	
	// 根据客户ID，得到帐单列表InvoiceDisplayDTO
	public List getInvoiceDisplayDTOsByCustomerId(Long customerId);
	
	
	// 得到所有分类帐户
	public List getAllSmsAccounts();
	
	// 根据分类帐户ID得到分类帐户
	public SmsAccount getSmsAccountBySmsAccountId(Long smsAccountId);
	
	// 获得所有开帐周期 
	public List getAllInvoicePeriods();
}
