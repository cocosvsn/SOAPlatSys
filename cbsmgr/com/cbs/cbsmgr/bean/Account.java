package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;

import com.cbs.cbsmgr.dto.AccountDTO;


/**
 * Account entity. @author MyEclipse Persistence Tools
 */

public class Account  implements java.io.Serializable {


    // Fields    

     private Long accountId;
     private String description;
     private Long customerId;
     private Long mopId;
     private Long invoicePeriodId;
     private Date nextInvoiceDay;
//     private Long bankNameId;			// 不使用
//     private String bankAccountName;	// 不使用
//     private String bankAccountNo;		// 不使用
//     private Date expiry;				// 不使用
     private Double bbf;
     private Long accountTypeId;
     private Long accountStatusId;
//     private Long billingAddressId;		// 不使用
//     private Double age130;				// 不使用
//     private Double age3160;			// 不使用
//     private Double age6190;			// 不使用
//     private Double age91120;			// 不使用
//     private Double age121150;			// 不使用
//     private Double age151180;			// 不使用
//     private Double ageBeyond180;		// 不使用
     private Date createDate;


    // Constructors

    /** default constructor */
     public Account()
     {
     }
     
    public Account(AccountDTO accountDTO) {
    	this.description = accountDTO.getDescription();
        this.mopId = accountDTO.getMopId();
        this.invoicePeriodId = accountDTO.getInvoicePeriodId();
        this.bbf = Double.valueOf(0.0);
        
//        this.bankNameId = (long)0;
//        this.bankAccountName = "";
//        this.bankAccountNo = "";
//        this.expiry = null;
//        this.billingAddressId = (long)0;
//        this.age130 = Double.valueOf(0.0);
//        this.age3160 = Double.valueOf(0.0);
//        this.age6190 = Double.valueOf(0.0);
//        this.age91120 = Double.valueOf(0.0);
//        this.age121150 = Double.valueOf(0.0);
//        this.age151180 = Double.valueOf(0.0);
//        this.ageBeyond180 = Double.valueOf(0.0);
    }

	/** minimal constructor */
    public Account(String description, Long customerId, Long mopId, Long invoicePeriodId, Date nextInvoiceDay, Long bankNameId, Double bbf, Long accountTypeId, Long accountStatusId, Long billingAddressId, Double age130, Double age3160, Double age6190, Double age91120, Double age121150, Double age151180, Double ageBeyond180, Date createDate) {
        this.description = description;
        this.customerId = customerId;
        this.mopId = mopId;
        this.invoicePeriodId = invoicePeriodId;
        this.nextInvoiceDay = nextInvoiceDay;
//        this.bankNameId = bankNameId;
        this.bbf = bbf;
        this.accountTypeId = accountTypeId;
        this.accountStatusId = accountStatusId;
//        this.billingAddressId = billingAddressId;
//        this.age130 = age130;
//        this.age3160 = age3160;
//        this.age6190 = age6190;
//        this.age91120 = age91120;
//        this.age121150 = age121150;
//        this.age151180 = age151180;
//        this.ageBeyond180 = ageBeyond180;
        this.createDate = createDate;
    }
    
    /** full constructor */
    public Account(String description, Long customerId, Long mopId, Long invoicePeriodId, Date nextInvoiceDay, Long bankNameId, String bankAccountName, String bankAccountNo, Date expiry, Double bbf, Long accountTypeId, Long accountStatusId, Long billingAddressId, Double age130, Double age3160, Double age6190, Double age91120, Double age121150, Double age151180, Double ageBeyond180, Date createDate) {
        this.description = description;
        this.customerId = customerId;
        this.mopId = mopId;
        this.invoicePeriodId = invoicePeriodId;
        this.nextInvoiceDay = nextInvoiceDay;
//        this.bankNameId = bankNameId;
//        this.bankAccountName = bankAccountName;
//        this.bankAccountNo = bankAccountNo;
//        this.expiry = expiry;
        this.bbf = bbf;
        this.accountTypeId = accountTypeId;
        this.accountStatusId = accountStatusId;
//        this.billingAddressId = billingAddressId;
//        this.age130 = age130;
//        this.age3160 = age3160;
//        this.age6190 = age6190;
//        this.age91120 = age91120;
//        this.age121150 = age121150;
//        this.age151180 = age151180;
//        this.ageBeyond180 = ageBeyond180;
        this.createDate = createDate;
    }

   
    // Property accessors

    public Long getAccountId() {
        return this.accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getMopId() {
        return this.mopId;
    }
    
    public void setMopId(Long mopId) {
        this.mopId = mopId;
    }

    public Long getInvoicePeriodId() {
        return this.invoicePeriodId;
    }
    
    public void setInvoicePeriodId(Long invoicePeriodId) {
        this.invoicePeriodId = invoicePeriodId;
    }

    public Date getNextInvoiceDay() {
        return this.nextInvoiceDay;
    }
    
    public void setNextInvoiceDay(Date nextInvoiceDay) {
        this.nextInvoiceDay = nextInvoiceDay;
    }

    public Double getBbf() {
        return this.bbf;
    }
    
    public void setBbf(Double bbf) {
        this.bbf = bbf;
    }

    public Long getAccountTypeId() {
        return this.accountTypeId;
    }
    
    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public Long getAccountStatusId() {
        return this.accountStatusId;
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

}