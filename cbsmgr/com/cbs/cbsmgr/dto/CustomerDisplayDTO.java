package com.cbs.cbsmgr.dto;

/**
 * <p>Title: CONVERGENCE BUSINESS SYSTEM</p>
 * <p>Module: CBS CORE MODULE</p>
 * <p>Description: </p>
 * <p>Company: Road To Broadband Co., Ltd.</p>
 * @author Hu Jin
 * <p>2004/1/14 Revised by Hu Jin
 */
import java.io.Serializable;
import java.util.Date;

public class CustomerDisplayDTO
    implements Serializable {
	
    private Long customerId;
    private Long customerTypeId;		// 数据字典
    private Long customerStatusId;		// 数据字典
    private Date captureDate;
    private Long defaultAddressId;
    private String districtId;
    
    private String district;

	public CustomerDisplayDTO() 
	{
	    
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public Long getCustomerStatusId() {
		return customerStatusId;
	}

	public void setCustomerStatusId(Long customerStatusId) {
		this.customerStatusId = customerStatusId;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}

	public Long getDefaultAddressId() {
		return defaultAddressId;
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
}