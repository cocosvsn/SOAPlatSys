package com.cbs.cbsmgr.serviceSimple;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.bean.FinancialTransaction;
import com.cbs.cbsmgr.bean.Invoice;
import com.cbs.cbsmgr.bean.InvoicePeriod;
import com.cbs.cbsmgr.bean.PackageItem;
import com.cbs.cbsmgr.bean.SmsAccount;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.dto.AccountDTO;
import com.cbs.cbsmgr.dto.AccountDisplayDTO;
import com.cbs.cbsmgr.dto.FtDisplayDTO;
import com.cbs.cbsmgr.dto.InvoiceDisplayDTO;
import com.cbs.cbsmgr.dto.ProductDTO;
import com.cbs.cbsmgr.manageriface.IAccountManager;
import com.cbs.cbsmgr.manageriface.IFinancialTransactionManager;
import com.cbs.cbsmgr.manageriface.IInvoiceManager;
import com.cbs.cbsmgr.manageriface.IInvoicePeriodManager;
import com.cbs.cbsmgr.manageriface.ISmsAccountManager;
import com.cbs.cbsmgr.serviceimpl.AccountService;
import com.cbs.cbsmgr.serviceimpl.FinancialTransactionService;
import com.cbs.cbsmgr.serviceimpl.InvoiceService;
import com.cbs.cbsmgr.serviceimpl.SmsAccountService;

public class SessionAccountImpl implements SessionAccountIface {

	public IAccountManager accountManager = null;
	public IFinancialTransactionManager financialTransactionManager = null;
	public IInvoiceManager invoiceManager = null;
	public ISmsAccountManager smsAccountManager = null;
	public IInvoicePeriodManager invoicePeriodManager = null;
	
	public SessionAccountImpl()
	{
		accountManager = (IAccountManager)ApplicationContextHolder.webApplicationContext.getBean("accountManager");
		financialTransactionManager = (IFinancialTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("financialTransactionManager");
		invoiceManager = (IInvoiceManager)ApplicationContextHolder.webApplicationContext.getBean("invoiceManager");
		smsAccountManager = (ISmsAccountManager)ApplicationContextHolder.webApplicationContext.getBean("smsAccountManager");
		invoicePeriodManager = (IInvoicePeriodManager)ApplicationContextHolder.webApplicationContext.getBean("invoicePeriodManager");
	}
	
	
	public Long CaptureAccount(Long customerId, AccountDTO accountDTO,
			Long accountType, Long accountStatus) {
		// 捕获帐户
		Account account = new Account(accountDTO);
		account.setCustomerId(customerId);
		account.setAccountTypeId(accountType);
		account.setCreateDate(new Date());
		account.setAccountStatusId(accountStatus);
		account.setBbf(Double.valueOf(0.0));
		account = (Account)accountManager.save(account);
		return account.getAccountId();
	}

	public Long modifyAccountInfo(Long accountId, AccountDTO accountDTO) {
		// 修改帐户信息
		Account account = (Account)accountManager.getById(accountId.toString());
		account.setDescription(accountDTO.getDescription());
		account.setMopId(accountDTO.getMopId());
		account.setInvoicePeriodId(accountDTO.getInvoicePeriodId());
		accountManager.update(account);
		return null;
	}

	public Long changeAccountStatus(Long accountId, Long accountStatus) {
		// 修改帐户状态
		Account account = (Account)accountManager.getById(accountId.toString());
		account.setAccountStatusId(accountStatus);
		accountManager.update(account);
		return null;
	}

	public Long changeAccountType(Long accountId, Long accountType) {
		// 修改帐户类型
		Account account = (Account)accountManager.getById(accountId.toString());
		account.setAccountTypeId(accountType);
		accountManager.update(account);
		return null;
	}
	
	public List getAccountsByCustomerId(Long customerId)
	{
		List accounts = (List)accountManager.findByProperty("customerId", customerId);
		return accounts;
	}
	
	// 根据客户ID得到帐户AccountDisplayDTO列表
	public List getAccountDisplayDTOsByCustomerId(Long customerId)
	{
		List accounts = (List)accountManager.findByProperty("customerId", customerId);
		List accountDisplayDTOs = new ArrayList();
		Iterator iAccounts = accounts.iterator();
		for (int i = 0; i < accounts.size(); i++)
		{
			Double notInvDebit = 0.0;
			Double notInvCredit = 0.0;
			Account account = (Account)iAccounts.next();
			AccountDisplayDTO accountDisplayDTO = new AccountDisplayDTO();
			
			notInvDebit = getNotInInvoiceAmountDebitByAccountId((long)account.getAccountId());
			notInvCredit = getNotInInvoiceAmountCreditByAccountId((long)account.getAccountId());
			
			accountDisplayDTO.setAccountId(account.getAccountId());
			accountDisplayDTO.setAccountStatusId(account.getAccountStatusId());
			accountDisplayDTO.setAccountTypeId(account.getAccountTypeId());
			accountDisplayDTO.setBbf(account.getBbf());
			accountDisplayDTO.setCreateDate(account.getCreateDate());
			accountDisplayDTO.setCustomerId(account.getCustomerId());
			accountDisplayDTO.setDescription(account.getDescription());
			accountDisplayDTO.setInvoicePeriodId(account.getInvoicePeriodId());
			accountDisplayDTO.setMopId(account.getMopId());
			accountDisplayDTO.setNextInvoiceDay(account.getNextInvoiceDay());
			accountDisplayDTO.setNotInvCredit(notInvCredit);
			accountDisplayDTO.setNotInvDebit(notInvDebit);
			accountDisplayDTO.setInvoicePeriod(
					getInvoicePeriodByInvoicePeriodId(account.getInvoicePeriodId()));
			
			accountDisplayDTOs.add(accountDisplayDTO);
		}
		return accountDisplayDTOs;
	}
	
	// 根据帐户ID，得到AccountDisplayDTO
	public AccountDisplayDTO getAccountDisplayDTOByAccountId(Long accountId)
	{
		Double notInvDebit = 0.0;
		Double notInvCredit = 0.0;
		Account account = (Account)accountManager.getById(accountId.toString());
		AccountDisplayDTO accountDisplayDTO = new AccountDisplayDTO();
		
		notInvDebit = getNotInInvoiceAmountDebitByAccountId((long)account.getAccountId());
		notInvCredit = getNotInInvoiceAmountCreditByAccountId((long)account.getAccountId());
		
		accountDisplayDTO.setAccountId(account.getAccountId());
		accountDisplayDTO.setAccountStatusId(account.getAccountStatusId());
		accountDisplayDTO.setAccountTypeId(account.getAccountTypeId());
		accountDisplayDTO.setBbf(account.getBbf());
		accountDisplayDTO.setCreateDate(account.getCreateDate());
		accountDisplayDTO.setCustomerId(account.getCustomerId());
		accountDisplayDTO.setDescription(account.getDescription());
		accountDisplayDTO.setInvoicePeriodId(account.getInvoicePeriodId());
		accountDisplayDTO.setMopId(account.getMopId());
		accountDisplayDTO.setNextInvoiceDay(account.getNextInvoiceDay());
		accountDisplayDTO.setNotInvCredit(notInvCredit);
		accountDisplayDTO.setNotInvDebit(notInvDebit);
		accountDisplayDTO.setInvoicePeriod(
				getInvoicePeriodByInvoicePeriodId(account.getInvoicePeriodId()));
		
		return accountDisplayDTO;
	}
	
	public String getInvoicePeriodByInvoicePeriodId(Long invoicePeriodId)
	{
		InvoicePeriod invoicePeriod = (InvoicePeriod)invoicePeriodManager.getById(invoicePeriodId.toString());
		return invoicePeriod.getDescription();
	}
	
	// 根据帐户编号得到帐户
	public Account getAccountByAccountId(Long accountId)
	{
		Account account = (Account)accountManager.getById(accountId.toString());
		return account;
	}

	
	public List getFinancialTransactionsByAccountId(Long accountId)
	{
		List financialTransactions = (List)financialTransactionManager.findByProperty("accountId", accountId);	
		return financialTransactions;
	}
	
	// 根据客户ID得到财务交易列表
	public List getFtDisplayDTOsByCustomerId(Long customerId)
	{
		List financialTransactions = (List)financialTransactionManager.findByProperty("customerId", customerId);
		
		List ftDisplayDTOs = new ArrayList();
		Iterator iFts = financialTransactions.iterator();
		for (int i = 0; i < financialTransactions.size(); i++)
		{
			FinancialTransaction ft = (FinancialTransaction)iFts.next();
			FtDisplayDTO ftDisplayDTO = new FtDisplayDTO();
			ftDisplayDTO.setAccountId(ft.getAccountId());
			ftDisplayDTO.setAmount(ft.getAmount());
			ftDisplayDTO.setCreateDate(ft.getCreateDate());
			ftDisplayDTO.setCustomerId(ft.getCustomerId());
			ftDisplayDTO.setFtId(ft.getFtId());
			ftDisplayDTO.setFtStatusId(ft.getFtStatusId());
			ftDisplayDTO.setInvoiceId(ft.getInvoiceId());
			ftDisplayDTO.setPeriodFrom(ft.getPeriodFrom());
			ftDisplayDTO.setPeriodTo(ft.getPeriodTo());
			ftDisplayDTO.setProductId(ft.getProductId());
			ftDisplayDTO.setSmsAccountId(ft.getSmsAccountId());
			ftDisplayDTO.setSmsAccount(
					getSmsAccountBySmsAccountId(ft.getSmsAccountId()).getDescription());
			
			ftDisplayDTOs.add(ftDisplayDTO);
		}
		return ftDisplayDTOs;
	}
	
	// 根据帐单ID得到财务交易FtDisplayDTO列表
	public List getFtDisplayDTOsByAccountId(Long accountId)
	{
		List financialTransactions = (List)financialTransactionManager.findByProperty("accountId", accountId);
		
		List ftDisplayDTOs = new ArrayList();
		Iterator iFts = financialTransactions.iterator();
		for (int i = 0; i < financialTransactions.size(); i++)
		{
			FinancialTransaction ft = (FinancialTransaction)iFts.next();
			FtDisplayDTO ftDisplayDTO = new FtDisplayDTO();
			ftDisplayDTO.setAccountId(ft.getAccountId());
			ftDisplayDTO.setAmount(ft.getAmount());
			ftDisplayDTO.setCreateDate(ft.getCreateDate());
			ftDisplayDTO.setCustomerId(ft.getCustomerId());
			ftDisplayDTO.setFtId(ft.getFtId());
			ftDisplayDTO.setFtStatusId(ft.getFtStatusId());
			ftDisplayDTO.setInvoiceId(ft.getInvoiceId());
			ftDisplayDTO.setPeriodFrom(ft.getPeriodFrom());
			ftDisplayDTO.setPeriodTo(ft.getPeriodTo());
			ftDisplayDTO.setProductId(ft.getProductId());
			ftDisplayDTO.setSmsAccountId(ft.getSmsAccountId());
			ftDisplayDTO.setSmsAccount(
					getSmsAccountBySmsAccountId(ft.getSmsAccountId()).getDescription());
			
			ftDisplayDTOs.add(ftDisplayDTO);
		}
		return ftDisplayDTOs;
	}
	
	// 根据客户ID得到帐单列表
	public List getInvoicesByCustomerId(Long customerId)
	{
		List invoices = (List)invoiceManager.findByProperty("customerId", customerId);
		return invoices;
	}
	
	// 根据客户ID，得到帐单列表InvoiceDisplayDTO
	public List getInvoiceDisplayDTOsByCustomerId(Long customerId)
	{
		List invoices = (List)invoiceManager.findByProperty("customerId", customerId);
		
		List invoiceDisplayDTOs = new ArrayList();
		Iterator iInvoices = invoices.iterator();
		for (int i = 0; i < invoices.size(); i++)
		{
			Invoice invoice = (Invoice)iInvoices.next();
			InvoiceDisplayDTO invoiceDisplayDTO = new InvoiceDisplayDTO();
			
			invoiceDisplayDTO.setAccountId(invoice.getAccountId());
			invoiceDisplayDTO.setBbf(invoice.getBbf());
			invoiceDisplayDTO.setCreateDate(invoice.getCreateDate());
			invoiceDisplayDTO.setCustomerId(invoice.getCustomerId());
			invoiceDisplayDTO.setDueDate(invoice.getDueDate());
			invoiceDisplayDTO.setInvoiceId(invoice.getInvoiceId());
			invoiceDisplayDTO.setInvoiceStatusId(invoice.getInvoiceStatusId());
			invoiceDisplayDTO.setPaidAmount(invoice.getPaidAmount());
			invoiceDisplayDTO.setPaidDate(invoice.getPaidDate());
			invoiceDisplayDTO.setPrintTag(invoice.getPrintTag());
			invoiceDisplayDTO.setTotalAmount(invoice.getTotalAmount());
			
			invoiceDisplayDTO.setCurAmount(invoice.getTotalAmount() - invoice.getBbf());

			
			invoiceDisplayDTOs.add(invoiceDisplayDTO);
		}
		return invoiceDisplayDTOs;
	}
	
	public List getInvoicesByAccountId(Long accountId)
	{
		List invoices = (List)invoiceManager.findByProperty("accountId", accountId);
		return invoices;
	}
	
	public List getFinancialTransactionsByInvoiceId(Long invoiceId)
	{
		List financialTransactions = (List)financialTransactionManager.findByProperty("invoiceId", invoiceId);
		return financialTransactions;
	}
	
	// 根据帐单ID得到财务交易FtDisplayDTO列表
	public List getFtDisplayDTOsByInvoiceId(Long invoiceId)
	{
		List financialTransactions = (List)financialTransactionManager.findByProperty("invoiceId", invoiceId);
		
		List ftDisplayDTOs = new ArrayList();
		Iterator iFts = financialTransactions.iterator();
		for (int i = 0; i < financialTransactions.size(); i++)
		{
			FinancialTransaction ft = (FinancialTransaction)iFts.next();
			FtDisplayDTO ftDisplayDTO = new FtDisplayDTO();
			ftDisplayDTO.setAccountId(ft.getAccountId());
			ftDisplayDTO.setAmount(ft.getAmount());
			ftDisplayDTO.setCreateDate(ft.getCreateDate());
			ftDisplayDTO.setCustomerId(ft.getCustomerId());
			ftDisplayDTO.setFtId(ft.getFtId());
			ftDisplayDTO.setFtStatusId(ft.getFtStatusId());
			ftDisplayDTO.setInvoiceId(ft.getInvoiceId());
			ftDisplayDTO.setPeriodFrom(ft.getPeriodFrom());
			ftDisplayDTO.setPeriodTo(ft.getPeriodTo());
			ftDisplayDTO.setProductId(ft.getProductId());
			ftDisplayDTO.setSmsAccountId(ft.getSmsAccountId());
			ftDisplayDTO.setSmsAccount(
					getSmsAccountBySmsAccountId(ft.getSmsAccountId()).getDescription());
			
			ftDisplayDTOs.add(ftDisplayDTO);
		}
		return ftDisplayDTOs;
	}
	
	// 根据分类帐户ID得到分类帐户
	public SmsAccount getSmsAccountBySmsAccountId(Long smsAccountId)
	{
		SmsAccount smsAccount = (SmsAccount)smsAccountManager.getById(smsAccountId.toString());
		return smsAccount;
	}
	
	// 得到所有分类帐户
	public List getAllSmsAccounts()
	{
		List smsAccounts = (List)smsAccountManager.findAll();
		return smsAccounts;
	}
	
	// 根据帐户ID得到未入帐金额-借Debit
	public Double getNotInInvoiceAmountDebitByAccountId(Long accountId)
	{
		// 条件：ft.accountId,ft.invoiceId=0,ft.smsaccountid="D",ftStatusId=1 or ftStatusId=3
		double debitNivFtAmount = 0.0;

		List fts = (List)financialTransactionManager.getDebitNotInInvoiceFts(accountId);
		
		Iterator iFts = fts.iterator();
		for (int i = 0; i < fts.size(); i++)
		{
			FinancialTransaction ft = (FinancialTransaction) iFts.next();
			debitNivFtAmount += ft.getAmount();
		}
		
		return debitNivFtAmount;
	}
	
	// 根据帐户ID得到未入帐金额-贷Credit
	public Double getNotInInvoiceAmountCreditByAccountId(Long accountId)
	{
		// 条件：ft.accountId,ft.invoiceId=0,ft.smsaccountid="C",ftStatusId=1 or ftStatusId=3
		double creditNivFtAmount = 0.0;

		List fts = (List)financialTransactionManager.getCreditNotInInvoiceFts(accountId);
		
		Iterator iFts = fts.iterator();
		for (int i = 0; i < fts.size(); i++)
		{
			FinancialTransaction ft = (FinancialTransaction) iFts.next();
			creditNivFtAmount += ft.getAmount();
		}
		
		return creditNivFtAmount;
	}

	// 手工添加财务交易
	public Long manualAddFinancialTransaction(Long accountId, Long productId, Double amount, Long smsAccountId)
	{
		return AddFinancialTransaction(accountId, productId, amount, smsAccountId);
	}
	
	// 生成财务交易
	public Long AddFinancialTransaction(Long accountId, Long productId, Double amount, Long smsAccountId/*, Long ftStatusId*/)
	{
		Account account = (Account)accountManager.getById(accountId.toString());
		Long customerId = account.getCustomerId();
		FinancialTransaction financialTransaction = new FinancialTransaction();
		financialTransaction.setAccountId(accountId);
		financialTransaction.setCreateDate(new Date());
		financialTransaction.setCustomerId(customerId);
		financialTransaction.setInvoiceId((long)0);
		financialTransaction.setProductId(productId);
		financialTransaction.setSmsAccountId(smsAccountId);
		financialTransaction.setFtStatusId((long)1);
		
		// 判断分类帐户类型
		SmsAccount smsAccount = getSmsAccountBySmsAccountId(smsAccountId);
		if(smsAccount.getDebitCredit().equalsIgnoreCase("D"))
		{
			// 借
			financialTransaction.setAmount(amount);
		}
		else
		{
			// 贷
			financialTransaction.setAmount(amount * (-1));
		}
		
		financialTransaction = (FinancialTransaction)financialTransactionManager.save(financialTransaction);
		
		// 修改财务交易状态:正常
//		changeFinancialTransactionStatus(financialTransaction.getFtId(), (long)1);
		
		return financialTransaction.getFtId();
	}
	
	public Long changeFinancialTransactionStatus(Long ftId, Long newStatus)
	{
		//FinancialTransactionService financialTransactionService = new FinancialTransactionService();
		FinancialTransaction financialTransaction = (FinancialTransaction)financialTransactionManager.getById(ftId.toString());
		financialTransaction.setFtStatusId(newStatus);
		financialTransactionManager.update(financialTransaction);
		return null;
	}

	// *********** 创建帐单 ******************
	public Invoice createInvoice(Long accountId, Date dueDate) {
		
		double totalNivFtAmount = 0.0;
		double lastBbf = 0.0;
		double totalInvoiceAmount = 0.0;
		
		// 查询未入帐单的财务交易列表，查询条件：accountId、invoiceId=0、ftStatusId=1 or ftStatusId=3
		List fts = financialTransactionManager.getAllNotInInvoiceFts(accountId);
		
		Iterator iFts = fts.iterator();
		for (int i = 0; i < fts.size(); i++)
		{
			FinancialTransaction ft = (FinancialTransaction) iFts.next();
			totalNivFtAmount += ft.getAmount();
		}
		
		// 查询未付费的帐单金额（帐户的bbf），查询条件：accountId
		Account account = (Account)accountManager.getById(accountId.toString());
		lastBbf = account.getBbf();
		
		// 汇总财务交易 + bbf = 帐单金额
		totalInvoiceAmount = lastBbf + totalNivFtAmount;
		
		// 生成新帐单
		InvoiceService invoiceService = new InvoiceService();
		Invoice invoice = new Invoice();
		invoice.setAccountId(accountId);
		invoice.setBbf(lastBbf);
		invoice.setCreateDate(new Date());
		invoice.setCustomerId(account.getCustomerId());
		invoice.setDueDate(dueDate);
		invoice.setInvoiceStatusId((long)1);
		invoice.setPaidAmount(0.0);
		invoice.setPaidDate(null);
		invoice.setPrintTag("N");
		invoice.setTotalAmount(totalInvoiceAmount);
		invoice = (Invoice)invoiceService.save(invoice);
		
		// 修改财务交易的invoiceId
		Iterator iIntoInvFts = fts.iterator();
		for (int i = 0; i < fts.size(); i++)
		{
			FinancialTransaction ft = (FinancialTransaction) iIntoInvFts.next();
			ft.setInvoiceId(invoice.getInvoiceId());
			financialTransactionManager.update(ft);
		}
		
		// 修改account的bbf = 帐单金额
		account.setBbf(totalInvoiceAmount);
		accountManager.update(account);
		
		return invoice;
	}

	// 帐单付费，也是一条财务交易
	public Long payInvoice(Long invoiceId, Double amount)
	{
		// 修改帐单信息:PaidAmount,paiddate,invoicestatusId
		//InvoiceService invoiceService = new InvoiceService();
		Invoice invoice = (Invoice)invoiceManager.getById(invoiceId.toString());
		invoice.setPaidAmount(amount);
		invoice.setPaidDate(new Date());
		invoice.setInvoiceStatusId((long)2);	// 设置帐单状态为已付费
		
		// 生成一条财务交易,帐单付费分类帐户
		AddFinancialTransaction(invoice.getAccountId(), (long)0, amount, (long)4);
		
		invoiceManager.update(invoice);
		
		// 不修改帐户bbf

		return null;
	}
	
	// 删除财务交易
	public Long deleteFinancialTransaction(Long ftId)
	{
		// 修改财务交易状态：删除
		return changeFinancialTransactionStatus(ftId, (long)2);
	}
	
	// 恢复删除财务交易
	public Long cancelDeleteFinancialTransaction(Long ftId)
	{
		// 修改财务交易状态：恢复删除
		return changeFinancialTransactionStatus(ftId, (long)1);
	}
	
	// 获得所有开帐周期 
	public List getAllInvoicePeriods()
	{
		return invoicePeriodManager.findAll();
	}
	
	
	//*****************************************************************************************
	
	
	// 创建开帐周期
	public Long createInvoicePeriod(String description, Long invoicePeriod)
	{
		InvoicePeriod invPeriod = new InvoicePeriod();
		invPeriod.setDescription(description);
		invPeriod.setInvoicePeriod(invoicePeriod);
		invoicePeriodManager.save(invPeriod);
		
		return invPeriod.getInvoicePeriodId();
	}
	
	// 修改开帐周期
	public void modifyInvoicePeriod(Long invoicePeriodId, String description, Long invoicePeriod)
	{
		InvoicePeriod invPeriod = (InvoicePeriod)invoicePeriodManager.getById(invoicePeriodId.toString());
		invPeriod.setDescription(description);
		invPeriod.setInvoicePeriod(invoicePeriod);
		invoicePeriodManager.update(invPeriod);
	}
	
	// 创建分类帐户
	public void createSmsAccount(String description1, String invoiceLineDes1, String debitCredit1, 
			String description2, String invoiceLineDes2, String debitCredit2)
	{
		SmsAccount smsAccount1 = new SmsAccount();
		SmsAccount smsAccount2 = new SmsAccount();
		
		smsAccount1.setDescription(description1);
		smsAccount1.setInvoiceLineDescription(invoiceLineDes1);
		smsAccount1.setDebitCredit(debitCredit1);
		smsAccount1.setReverseId((long)0);
		smsAccount2.setDescription(description2);
		smsAccount2.setInvoiceLineDescription(invoiceLineDes2);
		smsAccount2.setDebitCredit(debitCredit2);
		smsAccount2.setReverseId((long)0);
		
		smsAccount1 = (SmsAccount)smsAccountManager.save(smsAccount1);
		smsAccount2 = (SmsAccount)smsAccountManager.save(smsAccount2);
		
		smsAccount1.setReverseId(smsAccount2.getSmsAccountId());
		smsAccount2.setReverseId(smsAccount1.getSmsAccountId());
		
		smsAccountManager.update(smsAccount1);
		smsAccountManager.update(smsAccount2);
	}
	
	// 修改分类帐户
	public void modifySmsAccount(Long smsAccountId, String description, String invoiceLineDes)
	{
		SmsAccount smsAccount = (SmsAccount)smsAccountManager.getById(smsAccountId.toString());
		smsAccount.setDescription(description);
		smsAccount.setInvoiceLineDescription(invoiceLineDes);
		
		smsAccountManager.update(smsAccount);
	}
	
	
}
