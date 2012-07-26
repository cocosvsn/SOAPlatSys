package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * VodHistory entity. @author MyEclipse Persistence Tools
 */

public class VodHistory  implements java.io.Serializable {


    // Fields    

     private String vodHistoryId;
     private Long customerId;
     private Long accountId;
     private String progfileId;
     private Long productCategoryId;
     private Long productId;
     private Long serviceType;
     private Date startTime;
     private Date stopTime;
     private Long dealState;
     private Date dealDate;
     private Date importDate;
     private Double amount;
     private String campaignCategoryId;
     private Long ftId;


    // Constructors

    /** default constructor */
    public VodHistory() {
    }

	/** minimal constructor */
    public VodHistory(String vodHistoryId) {
        this.vodHistoryId = vodHistoryId;
    }
    
    /** full constructor */
    public VodHistory(String vodHistoryId, Long customerId, Long accountId, String progfileId, Long productCategoryId, Long productId, Long serviceType, Date startTime, Date stopTime, Long dealState, Date dealDate, Date importDate, Double amount, String campaignCategoryId, Long ftId) {
        this.vodHistoryId = vodHistoryId;
        this.customerId = customerId;
        this.accountId = accountId;
        this.progfileId = progfileId;
        this.productCategoryId = productCategoryId;
        this.productId = productId;
        this.serviceType = serviceType;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.dealState = dealState;
        this.dealDate = dealDate;
        this.importDate = importDate;
        this.amount = amount;
        this.campaignCategoryId = campaignCategoryId;
        this.ftId = ftId;
    }

   
    // Property accessors

    public String getVodHistoryId() {
        return this.vodHistoryId;
    }
    
    public void setVodHistoryId(String vodHistoryId) {
        this.vodHistoryId = vodHistoryId;
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

    public String getProgfileId() {
        return this.progfileId;
    }
    
    public void setProgfileId(String progfileId) {
        this.progfileId = progfileId;
    }

    public Long getProductCategoryId() {
        return this.productCategoryId;
    }
    
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getProductId() {
        return this.productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getServiceType() {
        return this.serviceType;
    }
    
    public void setServiceType(Long serviceType) {
        this.serviceType = serviceType;
    }

    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return this.stopTime;
    }
    
    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Long getDealState() {
        return this.dealState;
    }
    
    public void setDealState(Long dealState) {
        this.dealState = dealState;
    }

    public Date getDealDate() {
        return this.dealDate;
    }
    
    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public Date getImportDate() {
        return this.importDate;
    }
    
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Double getAmount() {
        return this.amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCampaignCategoryId() {
        return this.campaignCategoryId;
    }
    
    public void setCampaignCategoryId(String campaignCategoryId) {
        this.campaignCategoryId = campaignCategoryId;
    }

    public Long getFtId() {
        return this.ftId;
    }
    
    public void setFtId(Long ftId) {
        this.ftId = ftId;
    }
   








}