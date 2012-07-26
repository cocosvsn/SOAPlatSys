package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * CampaignCategory entity. @author MyEclipse Persistence Tools
 */

public class CampaignCategory  implements java.io.Serializable {


    // Fields    

     private String campaignCategoryId;
     private String description;
     private String introduction;
     private Long productCategoryId;
     private Long serviceType;
     private String customerTypeCollection;
     private String customerStatusCollection;
     private String districtCollection;
     private String accountTypeCollection;
     private String accountStatusCollection;
     private String mopCollection;
     private Long campaignType;
     private Date validFrom;
     private Date validTo;
     private String timeFrom;
     private String timeTo;
     private String activeTag;
     private Double discount;
     private Long priority;


    // Constructors

    /** default constructor */
    public CampaignCategory() {
    }

	/** minimal constructor */
    public CampaignCategory(String campaignCategoryId) {
        this.campaignCategoryId = campaignCategoryId;
    }
    
    /** full constructor */
    public CampaignCategory(String campaignCategoryId, String description, String introduction, Long productCategoryId, Long serviceType, String customerTypeCollection, String customerStatusCollection, String districtCollection, String accountTypeCollection, String accountStatusCollection, String mopCollection, Long campaignType, Date validFrom, Date validTo, String timeFrom, String timeTo, String activeTag, Double discount, Long priority) {
        this.campaignCategoryId = campaignCategoryId;
        this.description = description;
        this.introduction = introduction;
        this.productCategoryId = productCategoryId;
        this.serviceType = serviceType;
        this.customerTypeCollection = customerTypeCollection;
        this.customerStatusCollection = customerStatusCollection;
        this.districtCollection = districtCollection;
        this.accountTypeCollection = accountTypeCollection;
        this.accountStatusCollection = accountStatusCollection;
        this.mopCollection = mopCollection;
        this.campaignType = campaignType;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.activeTag = activeTag;
        this.discount = discount;
        this.priority = priority;
    }

   
    // Property accessors

    public String getCampaignCategoryId() {
        return this.campaignCategoryId;
    }
    
    public void setCampaignCategoryId(String campaignCategoryId) {
        this.campaignCategoryId = campaignCategoryId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getIntroduction() {
        return this.introduction;
    }
    
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Long getProductCategoryId() {
        return this.productCategoryId;
    }
    
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getServiceType() {
        return this.serviceType;
    }
    
    public void setServiceType(Long serviceType) {
        this.serviceType = serviceType;
    }

    public String getCustomerTypeCollection() {
        return this.customerTypeCollection;
    }
    
    public void setCustomerTypeCollection(String customerTypeCollection) {
        this.customerTypeCollection = customerTypeCollection;
    }

    public String getCustomerStatusCollection() {
        return this.customerStatusCollection;
    }
    
    public void setCustomerStatusCollection(String customerStatusCollection) {
        this.customerStatusCollection = customerStatusCollection;
    }

    public String getDistrictCollection() {
        return this.districtCollection;
    }
    
    public void setDistrictCollection(String districtCollection) {
        this.districtCollection = districtCollection;
    }

    public String getAccountTypeCollection() {
        return this.accountTypeCollection;
    }
    
    public void setAccountTypeCollection(String accountTypeCollection) {
        this.accountTypeCollection = accountTypeCollection;
    }

    public String getAccountStatusCollection() {
        return this.accountStatusCollection;
    }
    
    public void setAccountStatusCollection(String accountStatusCollection) {
        this.accountStatusCollection = accountStatusCollection;
    }

    public String getMopCollection() {
        return this.mopCollection;
    }
    
    public void setMopCollection(String mopCollection) {
        this.mopCollection = mopCollection;
    }

    public Long getCampaignType() {
        return this.campaignType;
    }
    
    public void setCampaignType(Long campaignType) {
        this.campaignType = campaignType;
    }

    public Date getValidFrom() {
        return this.validFrom;
    }
    
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return this.validTo;
    }
    
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public String getTimeFrom() {
        return this.timeFrom;
    }
    
    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return this.timeTo;
    }
    
    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getActiveTag() {
        return this.activeTag;
    }
    
    public void setActiveTag(String activeTag) {
        this.activeTag = activeTag;
    }

    public Double getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Long getPriority() {
        return this.priority;
    }
    
    public void setPriority(Long priority) {
        this.priority = priority;
    }
   








}