package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * VodCampaignCategory entity. @author MyEclipse Persistence Tools
 */

public class VodCampaignCategory  implements java.io.Serializable {


    // Fields    

     private String vodCampaignCategoryId;
     private String title;
     private String description;
     private Date validFromDate;			// �Ż���Чʱ��
     private Date validToDate;				// �Ż�ʧЧʱ��
     private Double priceDiscount;			// �Ż��ۿ���
     private Date createDate;	
     private String activeTag;				// ��Щ��־��Y/N
     private Long type;						// �Ż����ͣ�24Сʱ ��ÿ�չ̶�ʱ��� 
     private Date periodFrom;				// ÿ�չ̶�ʱ��Ŀ�ʼ
     private Date periodTo;					// ÿ�չ̶�ʱ��ν���


    // Constructors

    /** default constructor */
    public VodCampaignCategory() {
    }

	/** minimal constructor */
    public VodCampaignCategory(String vodCampaignCategoryId, Long type) {
        this.vodCampaignCategoryId = vodCampaignCategoryId;
        this.type = type;
    }
    
    /** full constructor */
    public VodCampaignCategory(String vodCampaignCategoryId, String title, String description, Date validFromDate, Date validToDate, Double priceDiscount, Date createDate, String activeTag, Long type, Date periodFrom, Date periodTo) {
        this.vodCampaignCategoryId = vodCampaignCategoryId;
        this.title = title;
        this.description = description;
        this.validFromDate = validFromDate;
        this.validToDate = validToDate;
        this.priceDiscount = priceDiscount;
        this.createDate = createDate;
        this.activeTag = activeTag;
        this.type = type;
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
    }

   
    // Property accessors

    public String getVodCampaignCategoryId() {
        return this.vodCampaignCategoryId;
    }
    
    public void setVodCampaignCategoryId(String vodCampaignCategoryId) {
        this.vodCampaignCategoryId = vodCampaignCategoryId;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidFromDate() {
        return this.validFromDate;
    }
    
    public void setValidFromDate(Date validFromDate) {
        this.validFromDate = validFromDate;
    }

    public Date getValidToDate() {
        return this.validToDate;
    }
    
    public void setValidToDate(Date validToDate) {
        this.validToDate = validToDate;
    }

    public Double getPriceDiscount() {
        return this.priceDiscount;
    }
    
    public void setPriceDiscount(Double priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getActiveTag() {
        return this.activeTag;
    }
    
    public void setActiveTag(String activeTag) {
        this.activeTag = activeTag;
    }

    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
    }

    public Date getPeriodFrom() {
        return this.periodFrom;
    }
    
    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return this.periodTo;
    }
    
    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }
   








}