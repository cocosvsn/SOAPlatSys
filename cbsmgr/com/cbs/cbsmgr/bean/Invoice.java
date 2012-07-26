package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * Invoice entity. @author MyEclipse Persistence Tools
 */

public class Invoice  implements java.io.Serializable {


    // Fields    

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


    // Constructors

    /** default constructor */
    public Invoice() {
    }

	/** minimal constructor */
    public Invoice(Long invoiceId, Long accountId, Long customerId, Date createDate, Date dueDate, Double totalAmount, Double bbf, Double paidAmount, String printTag, Long invoiceStatusId) {
        this.invoiceId = invoiceId;
        this.accountId = accountId;
        this.customerId = customerId;
        this.createDate = createDate;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.bbf = bbf;
        this.paidAmount = paidAmount;
        this.printTag = printTag;
        this.invoiceStatusId = invoiceStatusId;
    }
    
    /** full constructor */
    public Invoice(Long invoiceId, Long accountId, Long customerId, Date createDate, Date dueDate, Double totalAmount, Double bbf, Double paidAmount, Date paidDate, String printTag, Long invoiceStatusId) {
        this.invoiceId = invoiceId;
        this.accountId = accountId;
        this.customerId = customerId;
        this.createDate = createDate;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.bbf = bbf;
        this.paidAmount = paidAmount;
        this.paidDate = paidDate;
        this.printTag = printTag;
        this.invoiceStatusId = invoiceStatusId;
    }

   
    // Property accessors

    public Long getInvoiceId() {
        return this.invoiceId;
    }
    
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getAccountId() {
        return this.accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDueDate() {
        return this.dueDate;
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }
    
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getBbf() {
        return this.bbf;
    }
    
    public void setBbf(Double bbf) {
        this.bbf = bbf;
    }

    public Double getPaidAmount() {
        return this.paidAmount;
    }
    
    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Date getPaidDate() {
        return this.paidDate;
    }
    
    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public String getPrintTag() {
        return this.printTag;
    }
    
    public void setPrintTag(String printTag) {
        this.printTag = printTag;
    }

    public Long getInvoiceStatusId() {
        return this.invoiceStatusId;
    }
    
    public void setInvoiceStatusId(Long invoiceStatusId) {
        this.invoiceStatusId = invoiceStatusId;
    }
   








}