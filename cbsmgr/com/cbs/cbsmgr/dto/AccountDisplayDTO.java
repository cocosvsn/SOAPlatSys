package com.cbs.cbsmgr.dto;

/**
 * <p>Title: CONVERGENCE BUSINESS SYSTEM</p>
 * <p>Module: CBS CORE MODULE</p>
 * <p>Description: </p>
 * <p>Company: Road To Broadband Co., Ltd.</p>
 * @author Hu Jin
 * <p>2004/1/14 Revised by Hu Jin
 */
import java.io.Serializable;
import java.util.Date;

public class AccountDisplayDTO
    implements Serializable {
	
	private Long accountId;
    private String description;
    private Long customerId;
    private Long mopId;
    private Long invoicePeriodId;
    private Date nextInvoiceDay;
    private Double bbf;
    private Long accountTypeId;
    private Long accountStatusId;
    private Date createDate;
    private Double notInvDebit;
    private Double notInvCredit;
    private String invoicePeriod;

	public AccountDisplayDTO() 
	{
	    
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getMopId() {
		return mopId;
	}

	public void setMopId(Long mopId) {
		this.mopId = mopId;
	}

	public Long getInvoicePeriodId() {
		return invoicePeriodId;
	}

	public void setInvoicePeriodId(Long invoicePeriodId) {
		this.invoicePeriodId = invoicePeriodId;
	}

	public Date getNextInvoiceDay() {
		return nextInvoiceDay;
	}

	public void setNextInvoiceDay(Date nextInvoiceDay) {
		this.nextInvoiceDay = nextInvoiceDay;
	}

	public Double getBbf() {
		return bbf;
	}

	public void setBbf(Double bbf) {
		this.bbf = bbf;
	}

	public Long getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public Long getAccountStatusId() {
		return accountStatusId;
	}

	public void setAccountStatusId(Long accountStatusId) {
		this.accountStatusId = accountStatusId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getNotInvDebit() {
		return notInvDebit;
	}

	public void setNotInvDebit(Double notInvDebit) {
		this.notInvDebit = notInvDebit;
	}

	public Double getNotInvCredit() {
		return notInvCredit;
	}

	public void setNotInvCredit(Double notInvCredit) {
		this.notInvCredit = notInvCredit;
	}

	public String getInvoicePeriod() {
		return invoicePeriod;
	}

	public void setInvoicePeriod(String invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
}