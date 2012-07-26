package com.cbs.cbsmgr.dto;
import java.io.Serializable;
//import com.rtb.cbs.core.utils.TimeConvertor;
import java.math.BigDecimal;
import java.util.Date;

public class FtDisplayDTO implements Serializable {

    private Long ftId;
    private Long customerId;
    private Long accountId;
    private Date createDate;
    private Double amount;
    private Date periodFrom;
    private Date periodTo;
    private Long productId;
    private Long smsAccountId;
    private Long invoiceId;
    private Long ftStatusId;				// Êý¾Ý×Öµä
    private String smsAccount;
    
    public FtDisplayDTO() {

	}

	public Long getFtId() {
		return ftId;
	}

	public void setFtId(Long ftId) {
		this.ftId = ftId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Date getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Date periodTo) {
		this.periodTo = periodTo;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSmsAccountId() {
		return smsAccountId;
	}

	public void setSmsAccountId(Long smsAccountId) {
		this.smsAccountId = smsAccountId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getFtStatusId() {
		return ftStatusId;
	}

	public void setFtStatusId(Long ftStatusId) {
		this.ftStatusId = ftStatusId;
	}

	public String getSmsAccount() {
		return smsAccount;
	}

	public void setSmsAccount(String smsAccount) {
		this.smsAccount = smsAccount;
	}


}