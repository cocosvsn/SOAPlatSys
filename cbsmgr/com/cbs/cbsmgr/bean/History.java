package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * History entity. @author MyEclipse Persistence Tools
 */

public class History  implements java.io.Serializable {


    // Fields    

     private Long historyId;
     private Date createDate;
     private Long createUserId;
     private Long eventId;
     private Long reasonId;
     private Long customerId;
     private Long accountId;
     private Long productId;
     private Long hardwareId;
     private Long invoiceId;
     private Long exportTag;


    // Constructors

    /** default constructor */
    public History() {
    }

	/** minimal constructor */
    public History(Long historyId, Date createDate, Long createUserId, Long eventId, Long reasonId, Long customerId, Long accountId, Long productId, Long hardwareId, Long invoiceId) {
        this.historyId = historyId;
        this.createDate = createDate;
        this.createUserId = createUserId;
        this.eventId = eventId;
        this.reasonId = reasonId;
        this.customerId = customerId;
        this.accountId = accountId;
        this.productId = productId;
        this.hardwareId = hardwareId;
        this.invoiceId = invoiceId;
    }
    
    /** full constructor */
    public History(Long historyId, Date createDate, Long createUserId, Long eventId, Long reasonId, Long customerId, Long accountId, Long productId, Long hardwareId, Long invoiceId, Long exportTag) {
        this.historyId = historyId;
        this.createDate = createDate;
        this.createUserId = createUserId;
        this.eventId = eventId;
        this.reasonId = reasonId;
        this.customerId = customerId;
        this.accountId = accountId;
        this.productId = productId;
        this.hardwareId = hardwareId;
        this.invoiceId = invoiceId;
        this.exportTag = exportTag;
    }

   
    // Property accessors

    public Long getHistoryId() {
        return this.historyId;
    }
    
    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUserId() {
        return this.createUserId;
    }
    
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getEventId() {
        return this.eventId;
    }
    
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getReasonId() {
        return this.reasonId;
    }
    
    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
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

    public Long getProductId() {
        return this.productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getHardwareId() {
        return this.hardwareId;
    }
    
    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public Long getInvoiceId() {
        return this.invoiceId;
    }
    
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getExportTag() {
        return this.exportTag;
    }
    
    public void setExportTag(Long exportTag) {
        this.exportTag = exportTag;
    }
   








}