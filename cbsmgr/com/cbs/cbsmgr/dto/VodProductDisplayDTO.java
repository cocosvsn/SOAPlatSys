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
    private Double price;					// 单点价格
    private Date validFrom;					// 有效期
    private Date validTo;					// 有效期
    private Long sendTag;					// 分发标志位
    private Double packagePrice;			// 封顶价格
    
    private String vodDisplayCategoryId;	// 显示分类
    private String ptglobalid;				// globalid
    private String description;				// 简介
//    private String category;				// 
//    private String epicodenumber;			// 
    private String subtitlelanguage;		// 字幕语种(数据字典)
    private String audiolanguage;			// 音频语种(数据字典)
    private String director;				// 导演
    private String productname;				// 名称
    private String actors;					// 演员
    private String shootdate;				// 拍摄年代
    private String issuedate;				// 发行日期
    private String countrydist;				// 国家/地区(数据字典)
//    private Date subscriberstime;
//    private Date subscriberetime;
    private String inputmanid;				// 信息录入人
    private Date inputtime;					// 信息录入时间
    private String remark;					// 备注
    
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