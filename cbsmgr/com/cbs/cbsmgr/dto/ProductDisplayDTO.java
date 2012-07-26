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
    private Long productStatusId;					// �����ֵ�
    private Long oldStatusId;						// �����ֵ�
    private Date statusChangeDate;
    private Date captureDate;
    private Date validFrom;							// ��Ʒ��Ч��ʼ��20091026
    private Date validTo;							// ��Ʒ��Ч���գ�20091026
    private Long packageId;							// ��Ʒ��ID��20091026
    private Long packagePriority;					// ��Ʒ�����ȼ���20091026
    private String billingTypeId;					// �Ʒѷ�ʽID��20091026
    private Date billingValidDate;					// �Ʒ���Ч�ڣ�20091026
    private Long billingPriority;					// �Ʒ����ȼ���20091026
    private Long nextOption;						// �Ʒ���Ч�ڵ��ں������20091026
    private Long nextProductCategoryId;				// �Ʒ���Ч�ڵ��ں��л���ƷID��20091026
    private Long lastProductCategoryId;				// ��һ��ƷID��20091026
    private Long priceId;							// �۸�ID��20091026
    
    // ProductCategory
    private String productCategory;
    private String nextProductCategory;				// �Ʒ���Ч�ڵ��ں��л���Ʒ���ƣ�20091026
    
    // BillingType
    private String billingTypeDes;					// �Ʒѷ�ʽ��20091026
    
    // Price
    private String price;							// �۸�20091026
    
    // Package
    private String packageDes;						// ��Ʒ�����ƣ�20091026
    
    
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