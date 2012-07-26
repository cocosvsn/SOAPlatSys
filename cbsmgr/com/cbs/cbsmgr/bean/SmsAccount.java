package com.cbs.cbsmgr.bean;
// default package



/**
 * SmsAccount entity. @author MyEclipse Persistence Tools
 */

public class SmsAccount  implements java.io.Serializable {


    // Fields    

     private Long smsAccountId;
     private String description;
     private String invoiceLineDescription;
     private String debitCredit;
     private Long reverseId;


    // Constructors

    /** default constructor */
    public SmsAccount() {
    }

	/** minimal constructor */
    public SmsAccount(String debitCredit, Long reverseId) {
        this.debitCredit = debitCredit;
        this.reverseId = reverseId;
    }
    
    /** full constructor */
    public SmsAccount(String description, String invoiceLineDescription, String debitCredit, Long reverseId) {
        this.description = description;
        this.invoiceLineDescription = invoiceLineDescription;
        this.debitCredit = debitCredit;
        this.reverseId = reverseId;
    }

   
    // Property accessors

    public Long getSmsAccountId() {
        return this.smsAccountId;
    }
    
    public void setSmsAccountId(Long smsAccountId) {
        this.smsAccountId = smsAccountId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoiceLineDescription() {
        return this.invoiceLineDescription;
    }
    
    public void setInvoiceLineDescription(String invoiceLineDescription) {
        this.invoiceLineDescription = invoiceLineDescription;
    }

    public String getDebitCredit() {
        return this.debitCredit;
    }
    
    public void setDebitCredit(String debitCredit) {
        this.debitCredit = debitCredit;
    }

    public Long getReverseId() {
        return this.reverseId;
    }
    
    public void setReverseId(Long reverseId) {
        this.reverseId = reverseId;
    }
   








}