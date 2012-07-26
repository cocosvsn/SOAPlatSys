package com.cbs.cbsmgr.bean;
// default package



/**
 * PackageItem entity. @author MyEclipse Persistence Tools
 */

public class PackageItem  implements java.io.Serializable {


    // Fields    

     private Long packageItemId;
     private Long packageId;
     private Long sequence;
     private Long productCategoryId;
     private Long nextOption;
     private Long nextProductCategoryId;



    // Constructors

    /** default constructor */
    public PackageItem() {
    }

	/** minimal constructor */
    public PackageItem(Long packageItemId, Long packageId, Long sequence, Long productCategoryId, String hardwareModelCollection, String hardwareStatusCollection, String skipHardwareSelectTag, String mudSelectableTag, String financeOptionParkTag, String financeOptionMustTag, String marketSegmentParkTag, String marketSegmentMustTag, String dealerParkTag, String dealerMustTag, Long linkedItemSequence, Long guaranteeDays) {
        this.packageItemId = packageItemId;
        this.packageId = packageId;
        this.sequence = sequence;
        this.productCategoryId = productCategoryId;

    }
    
    /** full constructor */
    public PackageItem(Long packageItemId, Long packageId, Long sequence, Long productCategoryId, String hardwareModelCollection, String hardwareStatusCollection, String skipHardwareSelectTag, String mudSelectableTag, String financeOptionParkTag, String financeOptionMustTag, Long financeOptionDefaultId, String marketSegmentParkTag, String marketSegmentMustTag, Long marketSegmentDefaultId, String dealerParkTag, String dealerMustTag, Long dealerDefaultId, Long linkedItemSequence, Long guaranteeDays) {
        this.packageItemId = packageItemId;
        this.packageId = packageId;
        this.sequence = sequence;
        this.productCategoryId = productCategoryId;

    }

   
    // Property accessors

    public Long getPackageItemId() {
        return this.packageItemId;
    }
    
    public void setPackageItemId(Long packageItemId) {
        this.packageItemId = packageItemId;
    }

    public Long getPackageId() {
        return this.packageId;
    }
    
    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Long getSequence() {
        return this.sequence;
    }
    
    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Long getProductCategoryId() {
        return this.productCategoryId;
    }
    
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
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

}