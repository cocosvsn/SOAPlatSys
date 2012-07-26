package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * FinancialTransaction entity. @author MyEclipse Persistence Tools
 */

public class FinancialTransaction  implements java.io.Serializable {


    // Fields    

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
     private Long ftStatusId;				// 数据字典
     
//     private Long billingRuleId;			// 不使用
//     private Long commissionRuleId;			// 不使用
//     private String commissionRuleName;		// 不使用
//     private Long eventId;					// 不使用
//     private Long refundFtId;				// 不使用
//     private String lateFeeTag;				// 不使用
//     private Long ageId;					// 不使用


    // Constructors

    /** default constructor */
    public FinancialTransaction() {

        this.periodFrom = null;
        this.periodTo = null;
//        this.billingRuleId = (long)0;
//        this.commissionRuleId = (long)0;
//        this.commissionRuleName = "";
//        this.eventId = (long)0;
//        this.refundFtId = (long)0;
//        this.lateFeeTag = "N";
//        this.ageId = (long)0;
    }

	/** minimal constructor */
    public FinancialTransaction(Long ftId, Long customerId, Long accountId, Date createDate, Double amount, Long productId, Long billingRuleId, Long eventId, Long smsAccountId, Long invoiceId, Long ftStatusId, Long refundFtId, String lateFeeTag, Long ageId) {
        this.ftId = ftId;
        this.customerId = customerId;
        this.accountId = accountId;
        this.createDate = createDate;
        this.amount = amount;
        this.productId = productId;
//        this.billingRuleId = billingRuleId;
//        this.eventId = eventId;
        this.smsAccountId = smsAccountId;
        this.invoiceId = invoiceId;
        this.ftStatusId = ftStatusId;
//        this.refundFtId = refundFtId;
//        this.lateFeeTag = lateFeeTag;
//        this.ageId = ageId;
    }
    
    /** full constructor */
    public FinancialTransaction(Long ftId, Long customerId, Long accountId, Date createDate, Double amount, Date periodFrom, Date periodTo, Long productId, Long billingRuleId, Long commissionRuleId, String commissionRuleName, Long eventId, Long smsAccountId, Long invoiceId, Long ftStatusId, Long refundFtId, String lateFeeTag, Long ageId) {
        this.ftId = ftId;
        this.customerId = customerId;
        this.accountId = accountId;
        this.createDate = createDate;
        this.amount = amount;
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
        this.productId = productId;
//        this.billingRuleId = billingRuleId;
//        this.commissionRuleId = commissionRuleId;
//        this.commissionRuleName = commissionRuleName;
//        this.eventId = eventId;
        this.smsAccountId = smsAccountId;
        this.invoiceId = invoiceId;
        this.ftStatusId = ftStatusId;
//        this.refundFtId = refundFtId;
//        this.lateFeeTag = lateFeeTag;
//        this.ageId = ageId;
    }

   
    // Property accessors

    public Long getFtId() {
        return this.ftId;
    }
    
    public void setFtId(Long ftId) {
        this.ftId = ftId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAccountId() {
        return this.accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getAmount() {
        return this.amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getProductId() {
        return this.productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSmsAccountId() {
        return this.smsAccountId;
    }
    
    public void setSmsAccountId(Long smsAccountId) {
        this.smsAccountId = smsAccountId;
    }

    public Long getInvoiceId() {
        return this.invoiceId;
    }
    
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getFtStatusId() {
        return this.ftStatusId;
    }
    
    public void setFtStatusId(Long ftStatusId) {
        this.ftStatusId = ftStatusId;
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

}