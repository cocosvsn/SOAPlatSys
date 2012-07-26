package com.cbs.cbsmgr.dto;
import java.io.Serializable;
//import com.rtb.cbs.core.utils.TimeConvertor;
import java.math.BigDecimal;
import java.util.Date;

public class ProductDisplayDTO implements Serializable {

	// Product
    private Long productId;
    private Long customerId;
    private Long accountId;
    private Long productCategoryId;
    private Long productStatusId;					// 数据字典
    private Long oldStatusId;						// 数据字典
    private Date statusChangeDate;
    private Date captureDate;
    private Date validFrom;							// 产品有效期始，20091026
    private Date validTo;							// 产品有效期终，20091026
    private Long packageId;							// 产品包ID，20091026
    private Long packagePriority;					// 产品包优先级，20091026
    private String billingTypeId;					// 计费方式ID，20091026
    private Date billingValidDate;					// 计费有效期，20091026
    private Long billingPriority;					// 计费优先级，20091026
    private Long nextOption;						// 计费有效期到期后操作，20091026
    private Long nextProductCategoryId;				// 计费有效期到期后切换产品ID，20091026
    private Long lastProductCategoryId;				// 上一产品ID，20091026
    private Long priceId;							// 价格ID，20091026
    
    // ProductCategory
    private String productCategory;
    private String nextProductCategory;				// 计费有效期到期后切换产品名称，20091026
    
    // BillingType
    private String billingTypeDes;					// 计费方式，20091026
    
    // Price
    private String price;							// 价格，20091026
    
    // Package
    private String packageDes;						// 产品包名称，20091026
    
    
    public ProductDisplayDTO() {

	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Long getProductStatusId() {
		return productStatusId;
	}

	public void setProductStatusId(Long productStatusId) {
		this.productStatusId = productStatusId;
	}

	public Long getOldStatusId() {
		return oldStatusId;
	}

	public void setOldStatusId(Long oldStatusId) {
		this.oldStatusId = oldStatusId;
	}

	public Date getStatusChangeDate() {
		return statusChangeDate;
	}

	public void setStatusChangeDate(Date statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
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

	public String getNextProductCategory() {
		return nextProductCategory;
	}

	public void setNextProductCategory(String nextProductCategory) {
		this.nextProductCategory = nextProductCategory;
	}

	public String getBillingTypeDes() {
		return billingTypeDes;
	}

	public void setBillingTypeDes(String billingTypeDes) {
		this.billingTypeDes = billingTypeDes;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPackageDes() {
		return packageDes;
	}

	public void setPackageDes(String packageDes) {
		this.packageDes = packageDes;
	}

}