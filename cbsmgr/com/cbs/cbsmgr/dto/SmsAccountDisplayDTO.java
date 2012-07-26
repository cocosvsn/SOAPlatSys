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

public class SmsAccountDisplayDTO
    implements Serializable {
	
    private Long smsAccountId;
    private String description;
    private String invoiceLineDescription;
    private String debitCredit;
    private Long reverseId;
    
    private String reverseDes;
    

	public Long getSmsAccountId() {
		return smsAccountId;
	}

	public void setSmsAccountId(Long smsAccountId) {
		this.smsAccountId = smsAccountId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInvoiceLineDescription() {
		return invoiceLineDescription;
	}

	public void setInvoiceLineDescription(String invoiceLineDescription) {
		this.invoiceLineDescription = invoiceLineDescription;
	}

	public String getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}

	public Long getReverseId() {
		return reverseId;
	}

	public void setReverseId(Long reverseId) {
		this.reverseId = reverseId;
	}

	public String getReverseDes() {
		return reverseDes;
	}

	public void setReverseDes(String reverseDes) {
		this.reverseDes = reverseDes;
	}

}