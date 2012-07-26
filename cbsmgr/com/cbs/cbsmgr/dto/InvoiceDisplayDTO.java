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

public class InvoiceDisplayDTO
    implements Serializable {


    private Long invoiceId;
    private Long accountId;
    private Long customerId;
    private Date createDate;
    private Date dueDate;
    private Double totalAmount;
    private Double bbf;
    private Double paidAmount;
    private Date paidDate;
    private String printTag;
    private Long invoiceStatusId;			// Êý¾Ý×Öµä
    
    private Double curAmount;
    private String invoiceStatus;
    
	public InvoiceDisplayDTO() 
	{
	    
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getBbf() {
		return bbf;
	}

	public void setBbf(Double bbf) {
		this.bbf = bbf;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public String getPrintTag() {
		return printTag;
	}

	public void setPrintTag(String printTag) {
		this.printTag = printTag;
	}

	public Long getInvoiceStatusId() {
		return invoiceStatusId;
	}

	public void setInvoiceStatusId(Long invoiceStatusId) {
		this.invoiceStatusId = invoiceStatusId;
	}

	public Double getCurAmount() {
		return curAmount;
	}

	public void setCurAmount(Double curAmount) {
		this.curAmount = curAmount;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

}