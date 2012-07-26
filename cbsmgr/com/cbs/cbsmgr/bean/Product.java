package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;

import com.cbs.cbsmgr.dto.ProductDTO;


/**
 * Product entity. @author MyEclipse Persistence Tools
 */

public class Product  implements java.io.Serializable {


    // Fields    

     private Long productId;
     private Long customerId;
     private Long accountId;
     private Long productCategoryId;
     private String hardwareId;						// 
     private Long productStatusId;					// 数据字典
     private Long oldStatusId;						// 数据字典
     private Date statusChangeDate;
     private Date captureDate;
     private Date validFrom;
     private Date validTo;
     private Long packageId;
     private Long packagePriority;
     private String billingTypeId;	
     private Date billingValidDate;
     private Long billingPriority;
     private Long nextOption;
     private Long nextProductCategoryId;
     private Long lastProductCategoryId;
     private Long priceId;


    // Constructors

    /** default constructor */
     public Product()
     {
     
     }
    public Product(ProductDTO productDTO) {
    	//this.productId = productId;

        this.productCategoryId = productDTO.getProductCategoryId();
        this.productStatusId = productDTO.getProductStatusId();
        this.oldStatusId = productDTO.getOldStatusId();
        this.statusChangeDate = productDTO.getStatusChangeDate();
        this.captureDate = productDTO.getCaptureDate();
    }

	/** minimal constructor */
    public Product(Long productId, Long customerId, Long accountId, Long productCategoryId, Long hardwareId, Long productStatusId, Long productAddressId, Long linkProductId, Long financeOptionId, Long marketSegmentId, Long dealerId, String mudTag, Long mudUnit, Double mudDay, Double mudPrice, String hardwareModelCollection, String hardwareStatusCollectioin, Long oldStatusId, Date captureDate) {
        this.productId = productId;
        this.customerId = customerId;
        this.accountId = accountId;
        this.productCategoryId = productCategoryId;
        this.productStatusId = productStatusId;
        this.oldStatusId = oldStatusId;
        this.captureDate = captureDate;
    }
    
    /** full constructor */
    public Product(Long productId, Long customerId, Long accountId, Long productCategoryId, Long hardwareId, Long productStatusId, Long productAddressId, Long linkProductId, Long financeOptionId, Long marketSegmentId, Long dealerId, Date providerGuaranteeDate, String mudTag, Long mudUnit, Double mudDay, Double mudPrice, String hardwareModelCollection, String hardwareStatusCollectioin, Long oldStatusId, Date statusChangeDate, Date captureDate) {
        this.productId = productId;
        this.customerId = customerId;
        this.accountId = accountId;
        this.productCategoryId = productCategoryId;
        this.productStatusId = productStatusId;
        this.oldStatusId = oldStatusId;
        this.statusChangeDate = statusChangeDate;
        this.captureDate = captureDate;
    }

   
    // Property accessors

    public Long getProductId() {
        return this.productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Long getProductCategoryId() {
        return this.productCategoryId;
    }
    
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getProductStatusId() {
        return this.productStatusId;
    }
    
    public void setProductStatusId(Long productStatusId) {
        this.productStatusId = productStatusId;
    }

    public Long getOldStatusId() {
        return this.oldStatusId;
    }
    
    public void setOldStatusId(Long oldStatusId) {
        this.oldStatusId = oldStatusId;
    }

    public Date getStatusChangeDate() {
        return this.statusChangeDate;
    }
    
    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public Date getCaptureDate() {
        return this.captureDate;
    }
    
    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }
	public String getHardwareId() {
		return hardwareId;
	}
	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
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
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public Long getPackagePriority() {
		return packagePriority;
	}
	public void setPackagePriority(Long packagePriority) {
		this.packagePriority = packagePriority;
	}
	public String getBillingTypeId() {
		return billingTypeId;
	}
	public void setBillingTypeId(String billingTypeId) {
		this.billingTypeId = billingTypeId;
	}
	public Date getBillingValidDate() {
		return billingValidDate;
	}
	public void setBillingValidDate(Date billingValidDate) {
		this.billingValidDate = billingValidDate;
	}
	public Long getBillingPriority() {
		return billingPriority;
	}
	public void setBillingPriority(Long billingPriority) {
		this.billingPriority = billingPriority;
	}
	public Long getNextOption() {
		return nextOption;
	}
	public void setNextOption(Long nextOption) {
		this.nextOption = nextOption;
	}
	public Long getNextProductCategoryId() {
		return nextProductCategoryId;
	}
	public void setNextProductCategoryId(Long nextProductCategoryId) {
		this.nextProductCategoryId = nextProductCategoryId;
	}
	public Long getLastProductCategoryId() {
		return lastProductCategoryId;
	}
	public void setLastProductCategoryId(Long lastProductCategoryId) {
		this.lastProductCategoryId = lastProductCategoryId;
	}
	public Long getPriceId() {
		return priceId;
	}
	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}

}