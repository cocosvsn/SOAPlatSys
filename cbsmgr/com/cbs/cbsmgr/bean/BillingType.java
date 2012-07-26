package com.cbs.cbsmgr.bean;
// default package



/**
 * BillingType entity. @author MyEclipse Persistence Tools
 */

public class BillingType  implements java.io.Serializable {


    // Fields    

     private String billingTypeId;
     private String description;
     private String introduction;


    // Constructors

    /** default constructor */
    public BillingType() {
    }

	/** minimal constructor */
    public BillingType(String billingTypeId) {
        this.billingTypeId = billingTypeId;
    }
    
    /** full constructor */
    public BillingType(String billingTypeId, String description, String introduction) {
        this.billingTypeId = billingTypeId;
        this.description = description;
        this.introduction = introduction;
    }

   
    // Property accessors

    public String getBillingTypeId() {
        return this.billingTypeId;
    }
    
    public void setBillingTypeId(String billingTypeId) {
        this.billingTypeId = billingTypeId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getIntroduction() {
        return this.introduction;
    }
    
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
   








}