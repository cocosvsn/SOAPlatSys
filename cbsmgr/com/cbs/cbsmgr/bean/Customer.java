package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * Customer entity. @author MyEclipse Persistence Tools
 */

public class Customer  implements java.io.Serializable {


    // Fields    

     private Long customerId;
     private Long customerTypeId;		// 数据字典
     private Long customerStatusId;		// 数据字典
     private Date captureDate;
//     private Long loyalty;				// 不使用
     private Long defaultAddressId;
     private String districtId;
//     private Long postalAddressId;		// 不使用
//     private String depotTag;			// 不使用
//     private String dealerTag;			// 不使用
//     private String productProviderTag;	// 不使用


    // Constructors

    /** default constructor */
    public Customer() {
    }

    
    /** full constructor */
    public Customer(Long customerId, Long customerTypeId, Long customerStatusId, Date captureDate, Long loyalty, Long defaultAddressId, Long postalAddressId, String depotTag, String dealerTag, String productProviderTag) {
        this.customerId = customerId;
        this.customerTypeId = customerTypeId;
        this.customerStatusId = customerStatusId;
        this.captureDate = captureDate;
//        this.loyalty = loyalty;
        this.defaultAddressId = defaultAddressId;
//        this.postalAddressId = postalAddressId;
//        this.depotTag = depotTag;
//        this.dealerTag = dealerTag;
//        this.productProviderTag = productProviderTag;
    }

   
    // Property accessors

    public Long getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerTypeId() {
        return this.customerTypeId;
    }
    
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public Long getCustomerStatusId() {
        return this.customerStatusId;
    }
    
    public void setCustomerStatusId(Long customerStatusId) {
        this.customerStatusId = customerStatusId;
    }

    public Date getCaptureDate() {
        return this.captureDate;
    }
    
    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }



    public Long getDefaultAddressId() {
        return this.defaultAddressId;
    }
    
    public void setDefaultAddressId(Long defaultAddressId) {
        this.defaultAddressId = defaultAddressId;
    }


	public String getDistrictId() {
		return districtId;
	}


	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}


}