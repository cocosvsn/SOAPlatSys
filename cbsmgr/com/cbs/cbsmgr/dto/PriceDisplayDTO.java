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

public class PriceDisplayDTO
    implements Serializable {
	
	private Long priceId;
    private String description;
    private Double price;
    private Long smsAccountId;
    
    private String smsAccount;
    
    

	public Long getPriceId() {
		return priceId;
	}

	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getSmsAccount() {
		return smsAccount;
	}

	public void setSmsAccount(String smsAccount) {
		this.smsAccount = smsAccount;
	}
    
}