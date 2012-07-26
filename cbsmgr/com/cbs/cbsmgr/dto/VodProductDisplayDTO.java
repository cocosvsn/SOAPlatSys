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

public class VodProductDisplayDTO
    implements Serializable {
	
	private String vodProductId;
    private Long type;
    private String vodPackageId;
//    private String vodDisplayCategoryId;
    private Long billingType;
    private Double price;					// ����۸�
    private Date validFrom;					// ��Ч��
    private Date validTo;					// ��Ч��
    private Long sendTag;					// �ַ���־λ
    private Double packagePrice;			// �ⶥ�۸�
    
    private String vodDisplayCategoryId;	// ��ʾ����
    private String ptglobalid;				// globalid
    private String description;				// ���
//    private String category;				// 
//    private String epicodenumber;			// 
    private String subtitlelanguage;		// ��Ļ����(�����ֵ�)
    private String audiolanguage;			// ��Ƶ����(�����ֵ�)
    private String director;				// ����
    private String productname;				// ����
    private String actors;					// ��Ա
    private String shootdate;				// �������
    private String issuedate;				// ��������
    private String countrydist;				// ����/����(�����ֵ�)
//    private Date subscriberstime;
//    private Date subscriberetime;
    private String inputmanid;				// ��Ϣ¼����
    private Date inputtime;					// ��Ϣ¼��ʱ��
    private String remark;					// ��ע
    
    private String vodDisplayCategory;
    
    

	public String getVodProductId() {
		return vodProductId;
	}

	public void setVodProductId(String vodProductId) {
		this.vodProductId = vodProductId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getVodPackageId() {
		return vodPackageId;
	}

	public void setVodPackageId(String vodPackageId) {
		this.vodPackageId = vodPackageId;
	}

	public Long getBillingType() {
		return billingType;
	}

	public void setBillingType(Long billingType) {
		this.billingType = billingType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public Long getSendTag() {
		return sendTag;
	}

	public void setSendTag(Long sendTag) {
		this.sendTag = sendTag;
	}

	public Double getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(Double packagePrice) {
		this.packagePrice = packagePrice;
	}

	public String getVodDisplayCategoryId() {
		return vodDisplayCategoryId;
	}

	public void setVodDisplayCategoryId(String vodDisplayCategoryId) {
		this.vodDisplayCategoryId = vodDisplayCategoryId;
	}

	public String getPtglobalid() {
		return ptglobalid;
	}

	public void setPtglobalid(String ptglobalid) {
		this.ptglobalid = ptglobalid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubtitlelanguage() {
		return subtitlelanguage;
	}

	public void setSubtitlelanguage(String subtitlelanguage) {
		this.subtitlelanguage = subtitlelanguage;
	}

	public String getAudiolanguage() {
		return audiolanguage;
	}

	public void setAudiolanguage(String audiolanguage) {
		this.audiolanguage = audiolanguage;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getShootdate() {
		return shootdate;
	}

	public void setShootdate(String shootdate) {
		this.shootdate = shootdate;
	}

	public String getIssuedate() {
		return issuedate;
	}

	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}

	public String getCountrydist() {
		return countrydist;
	}

	public void setCountrydist(String countrydist) {
		this.countrydist = countrydist;
	}

	public String getInputmanid() {
		return inputmanid;
	}

	public void setInputmanid(String inputmanid) {
		this.inputmanid = inputmanid;
	}

	public Date getInputtime() {
		return inputtime;
	}

	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVodDisplayCategory() {
		return vodDisplayCategory;
	}

	public void setVodDisplayCategory(String vodDisplayCategory) {
		this.vodDisplayCategory = vodDisplayCategory;
	}		

}