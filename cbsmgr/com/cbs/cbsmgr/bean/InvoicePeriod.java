package com.cbs.cbsmgr.bean;
// default package



/**
 * InvoicePeriod entity. @author MyEclipse Persistence Tools
 */

public class InvoicePeriod  implements java.io.Serializable {


    // Fields    

     private Long invoicePeriodId;
     private Long invoicePeriod;
     private String description;


    // Constructors

    /** default constructor */
    public InvoicePeriod() {
    }

	/** minimal constructor */
    public InvoicePeriod(Long invoicePeriod) {
        this.invoicePeriod = invoicePeriod;
    }
    
    /** full constructor */
    public InvoicePeriod(Long invoicePeriod, String description) {
        this.invoicePeriod = invoicePeriod;
        this.description = description;
    }

   
    // Property accessors

    public Long getInvoicePeriodId() {
        return this.invoicePeriodId;
    }
    
    public void setInvoicePeriodId(Long invoicePeriodId) {
        this.invoicePeriodId = invoicePeriodId;
    }

    public Long getInvoicePeriod() {
        return this.invoicePeriod;
    }
    
    public void setInvoicePeriod(Long invoicePeriod) {
        this.invoicePeriod = invoicePeriod;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
   








}