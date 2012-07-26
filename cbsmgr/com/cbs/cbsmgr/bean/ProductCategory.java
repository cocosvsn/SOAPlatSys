package com.cbs.cbsmgr.bean;

import java.util.Date;

// default package



/**
 * ProductCategory entity. @author MyEclipse Persistence Tools
 */

public class ProductCategory  implements java.io.Serializable {


    // Fields    

     private Long productCategoryId;
     private String description;
     private Long productTypeId;		// Êý¾Ý×Öµä
     private Long priceId;
     private String introduction;
     private String billingTypeId;
     private String contentCategoryId;
     private Long days;
     private Long counts;
     private Long seconds;
     private Date validFrom;
     private Date validTo;
     private String createUser;
     private Date createDate;
     private String code;


    // Constructors

    /** default constructor */
    public ProductCategory() {

    }

	/** minimal constructor */
    public ProductCategory(Long productCategoryId, Long productTypeId, Long productProviderId, String hardwareTag, String subscriptionTag, String stbTag, String smartcardTag, String cablemodemTag, String othersTag, Long othersId) {
        this.productCategoryId = productCategoryId;
        this.productTypeId = productTypeId;

    }
    
    /** full constructor */
    public ProductCategory(Long productCategoryId, String description, Long productTypeId, Long productProviderId, String hardwareTag, String subscriptionTag, String stbTag, String smartcardTag, String cablemodemTag, String othersTag, Long othersId) {
        this.productCategoryId = productCategoryId;
        this.description = description;
        this.productTypeId = productTypeId;

    }

   
    // Property accessors

    public Long getProductCategoryId() {
        return this.productCategoryId;
    }
    
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductTypeId() {
        return this.productTypeId;
    }
    
    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

	public Long getPriceId() {
		return priceId;
	}

	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getBillingTypeId() {
		return billingTypeId;
	}

	public void setBillingTypeId(String billingTypeId) {
		this.billingTypeId = billingTypeId;
	}

	public String getContentCategoryId() {
		return contentCategoryId;
	}

	public void setContentCategoryId(String contentCategoryId) {
		this.contentCategoryId = contentCategoryId;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public Long getCounts() {
		return counts;
	}

	public void setCounts(Long counts) {
		this.counts = counts;
	}

	public Long getSeconds() {
		return seconds;
	}

	public void setSeconds(Long seconds) {
		this.seconds = seconds;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}