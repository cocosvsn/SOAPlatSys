package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * Package entity. @author MyEclipse Persistence Tools
 */

public class ProductPackage  implements java.io.Serializable {


    // Fields    

     private Long packageId;
     private String description;
     private String allowCaptureTag;
     private Date allowCaptureDateFrom;
     private Date allowCaptureDateTo;
     private String introduction;
     private Long packageBillingPriority;



    // Constructors

    /** default constructor */
    public ProductPackage() {

    }

	/** minimal constructor */
    public ProductPackage(Long packageId, String forGroupCollection, String allowCaptureTag, String allowTelesalesTag, String accountTypeCollection, String accountStatusCollection, String accountMopCollection, String invoicePeriodCollection, String externalLinkTag, String linkableCategoryCollection) {
        this.packageId = packageId;
        this.allowCaptureTag = allowCaptureTag;
    }
    
    /** full constructor */
    public ProductPackage(Long packageId, String description, String forGroupCollection, String allowCaptureTag, String allowTelesalesTag, String accountTypeCollection, String accountStatusCollection, String accountMopCollection, String invoicePeriodCollection, Date allowCaptureDateFrom, Date allowCaptureDateTo, String externalLinkTag, String linkableCategoryCollection) {
        this.packageId = packageId;
        this.description = description;
        this.allowCaptureTag = allowCaptureTag;
        this.allowCaptureDateFrom = allowCaptureDateFrom;
        this.allowCaptureDateTo = allowCaptureDateTo;

    }

   
    // Property accessors

    public Long getPackageId() {
        return this.packageId;
    }
    
    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAllowCaptureTag() {
        return this.allowCaptureTag;
    }
    
    public void setAllowCaptureTag(String allowCaptureTag) {
        this.allowCaptureTag = allowCaptureTag;
    }

    public Date getAllowCaptureDateFrom() {
        return this.allowCaptureDateFrom;
    }
    
    public void setAllowCaptureDateFrom(Date allowCaptureDateFrom) {
        this.allowCaptureDateFrom = allowCaptureDateFrom;
    }

    public Date getAllowCaptureDateTo() {
        return this.allowCaptureDateTo;
    }
    
    public void setAllowCaptureDateTo(Date allowCaptureDateTo) {
        this.allowCaptureDateTo = allowCaptureDateTo;
    }

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Long getPackageBillingPriority() {
		return packageBillingPriority;
	}

	public void setPackageBillingPriority(Long packageBillingPriority) {
		this.packageBillingPriority = packageBillingPriority;
	}

}