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

public class ProductCategoryDisplayDTO
    implements Serializable {

	// ProductCategory
	private Long productCategoryId;			// 套餐目录Id
    private String description;				// 套餐描述（名称）
    private Long productTypeId;				// 产品类型，20091026
    private Long priceId;					// 价格Id
    private String introduction;			// 说明，20091026
    private String billingTypeId;			// 计费方式Id，20091026
    private String contentCategoryId;		// 内容分类Id，20091026
    private Long days;						// 按天数计费的天数，20091026
    private Long counts;					// 按次数计费的次数，20091026
    private Long seconds;					// 按时长计费的秒数，20091026
    private Date validFrom;					// 产品有效期始，20091026
    private Date validTo;					// 产品有效期终，20091026
    private String createUser;				// 创建用户，20091026
    private Date createDate;				// 创建日期，20091026
    private String code;					// code，20091118
    
    // Price
    private String priceDescription;		// 价格描述（名称）
    private Double price;					// 价格
    
    // SmsAccount
    private Long smsAccountId;				// 分类账户Id
    private String smsAccountDescription;	// 分类账户描述（名称）
    private String debitCredit;				// 借 or 贷 
    
    // PRODUCT_TYPE
    private String productTypeDes;			// 产品类型，20091026
    private String hardwareTag;				// 硬件标志，20091026
    
    // CONTENT_CATEGORY
    private String contentCategoryDes;		// 内容分类，20091026
    
    // BILLING_TYPE
    private String billingTypeDes;			// 计费类型，20091026
    private String billingTypeIntro;		// 计费类型说明，20091026
    
    
    
    
	public Long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getProductTypeId() {
		return productTypeId;
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
	public String getPriceDescription() {
		return priceDescription;
	}
	public void setPriceDescription(String priceDescription) {
		this.priceDescription = priceDescription;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getSmsAccountId() {
		return smsAccountId;
	}
	public void setSmsAccountId(Long smsAccountId) {
		this.smsAccountId = smsAccountId;
	}
	public String getSmsAccountDescription() {
		return smsAccountDescription;
	}
	public void setSmsAccountDescription(String smsAccountDescription) {
		this.smsAccountDescription = smsAccountDescription;
	}
	public String getDebitCredit() {
		return debitCredit;
	}
	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
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
	public String getProductTypeDes() {
		return productTypeDes;
	}
	public void setProductTypeDes(String productTypeDes) {
		this.productTypeDes = productTypeDes;
	}
	public String getHardwareTag() {
		return hardwareTag;
	}
	public void setHardwareTag(String hardwareTag) {
		this.hardwareTag = hardwareTag;
	}
	public String getContentCategoryDes() {
		return contentCategoryDes;
	}
	public void setContentCategoryDes(String contentCategoryDes) {
		this.contentCategoryDes = contentCategoryDes;
	}
	public String getBillingTypeDes() {
		return billingTypeDes;
	}
	public void setBillingTypeDes(String billingTypeDes) {
		this.billingTypeDes = billingTypeDes;
	}
	public String getBillingTypeIntro() {
		return billingTypeIntro;
	}
	public void setBillingTypeIntro(String billingTypeIntro) {
		this.billingTypeIntro = billingTypeIntro;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}