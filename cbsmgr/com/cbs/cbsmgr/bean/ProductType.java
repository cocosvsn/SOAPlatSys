package com.cbs.cbsmgr.bean;
// default package



/**
 * ProductType entity. @author MyEclipse Persistence Tools
 */

public class ProductType  implements java.io.Serializable {


    // Fields    

     private Long productTypeId;
     private String description;
     private String hardwareTag;


    // Constructors

    /** default constructor */
    public ProductType() {
    }

	/** minimal constructor */
    public ProductType(Long productTypeId) {
        this.productTypeId = productTypeId;
    }
    
    /** full constructor */
    public ProductType(Long productTypeId, String description, String hardwareTag) {
        this.productTypeId = productTypeId;
        this.description = description;
        this.hardwareTag = hardwareTag;
    }

   
    // Property accessors

    public Long getProductTypeId() {
        return this.productTypeId;
    }
    
    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getHardwareTag() {
        return this.hardwareTag;
    }
    
    public void setHardwareTag(String hardwareTag) {
        this.hardwareTag = hardwareTag;
    }
   








}