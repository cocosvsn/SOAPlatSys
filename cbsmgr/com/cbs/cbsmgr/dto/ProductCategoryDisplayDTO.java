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
	private Long productCategoryId;			// �ײ�Ŀ¼Id
    private String description;				// �ײ����������ƣ�
    private Long productTypeId;				// ��Ʒ���ͣ�20091026
    private Long priceId;					// �۸�Id
    private String introduction;			// ˵����20091026
    private String billingTypeId;			// �Ʒѷ�ʽId��20091026
    private String contentCategoryId;		// ���ݷ���Id��20091026
    private Long days;						// �������Ʒѵ�������20091026
    private Long counts;					// �������ƷѵĴ�����20091026
    private Long seconds;					// ��ʱ���Ʒѵ�������20091026
    private Date validFrom;					// ��Ʒ��Ч��ʼ��20091026
    private Date validTo;					// ��Ʒ��Ч���գ�20091026
    private String createUser;				// �����û���20091026
    private Date createDate;				// �������ڣ�20091026
    private String code;					// code��20091118
    
    // Price
    private String priceDescription;		// �۸����������ƣ�
    private Double price;					// �۸�
    
    // SmsAccount
    private Long smsAccountId;				// �����˻�Id
    private String smsAccountDescription;	// �����˻����������ƣ�
    private String debitCredit;				// �� or �� 
    
    // PRODUCT_TYPE
    private String productTypeDes;			// ��Ʒ���ͣ�20091026
    private String hardwareTag;				// Ӳ����־��20091026
    
    // CONTENT_CATEGORY
    private String contentCategoryDes;		// ���ݷ��࣬20091026
    
    // BILLING_TYPE
    private String billingTypeDes;			// �Ʒ����ͣ�20091026
    private String billingTypeIntro;		// �Ʒ�����˵����20091026
    
    
    
    
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