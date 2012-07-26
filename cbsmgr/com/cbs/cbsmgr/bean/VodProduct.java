package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * VodProduct entity. @author MyEclipse Persistence Tools
 */

public class VodProduct  implements java.io.Serializable {


    // Fields    

     private String vodProductId;
     private Long type;
     private String vodPackageId;
     private String vodDisplayCategoryId;
     private Long billingType;
     private Double price;
     private Date validFrom;
     private Date validTo;
     private Long sendTag;
     private Double packagePrice;

    // Constructors

    /** default constructor */
    public VodProduct() {
    }

	/** minimal constructor */
    public VodProduct(String vodProductId) {
        this.vodProductId = vodProductId;
    }
    
    /** full constructor */
    public VodProduct(String vodProductId, Long type, String vodPackageId, String vodDisplayCategoryId, Long billingType, Double price, Date validFrom, Date validTo, Long sendTag) {
        this.vodProductId = vodProductId;
        this.type = type;
        this.vodPackageId = vodPackageId;
        this.vodDisplayCategoryId = vodDisplayCategoryId;
        this.billingType = billingType;
        this.price = price;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.sendTag = sendTag;
    }

   
    // Property accessors

    public String getVodProductId() {
        return this.vodProductId;
    }
    
    public void setVodProductId(String vodProductId) {
        this.vodProductId = vodProductId;
    }

    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
    }

    public String getVodPackageId() {
        return this.vodPackageId;
    }
    
    public void setVodPackageId(String vodPackageId) {
        this.vodPackageId = vodPackageId;
    }

    public String getVodDisplayCategoryId() {
        return this.vodDisplayCategoryId;
    }
    
    public void setVodDisplayCategoryId(String vodDisplayCategoryId) {
        this.vodDisplayCategoryId = vodDisplayCategoryId;
    }

    public Long getBillingType() {
        return this.billingType;
    }
    
    public void setBillingType(Long billingType) {
        this.billingType = billingType;
    }

    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
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

    public Long getSendTag() {
        return this.sendTag;
    }
    
    public void setSendTag(Long sendTag) {
        this.sendTag = sendTag;
    }

	public Double getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(Double packagePrice) {
		this.packagePrice = packagePrice;
	}
}