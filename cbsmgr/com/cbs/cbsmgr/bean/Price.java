package com.cbs.cbsmgr.bean;
// default package



/**
 * Price entity. @author MyEclipse Persistence Tools
 */

public class Price  implements java.io.Serializable {


    // Fields    

     private Long priceId;
     private String description;
     private Double price;
     private Long smsAccountId;


    // Constructors

    /** default constructor */
    public Price() {
    }

	/** minimal constructor */
    public Price(Long priceId, Double price, Long smsAccountId) {
        this.priceId = priceId;
        this.price = price;
        this.smsAccountId = smsAccountId;
    }
    
    /** full constructor */
    public Price(Long priceId, String description, Double price, Long smsAccountId) {
        this.priceId = priceId;
        this.description = description;
        this.price = price;
        this.smsAccountId = smsAccountId;
    }

   
    // Property accessors

    public Long getPriceId() {
        return this.priceId;
    }
    
    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getSmsAccountId() {
        return this.smsAccountId;
    }
    
    public void setSmsAccountId(Long smsAccountId) {
        this.smsAccountId = smsAccountId;
    }
   








}