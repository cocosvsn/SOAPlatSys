package com.cbs.cbsmgr.bean;
// default package



/**
 * VodCbsProductRel entity. @author MyEclipse Persistence Tools
 */

public class VodCbsProductRel  implements java.io.Serializable {


    // Fields    

     private String relId;
     private String vodProductId;
     private Long productCategoryId;


    // Constructors

    /** default constructor */
    public VodCbsProductRel() {
    }

    
    /** full constructor */
    public VodCbsProductRel(String relId, String vodProductId, Long productCategoryId) {
        this.relId = relId;
        this.vodProductId = vodProductId;
        this.productCategoryId = productCategoryId;
    }

   
    // Property accessors

    public String getRelId() {
        return this.relId;
    }
    
    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getVodProductId() {
        return this.vodProductId;
    }
    
    public void setVodProductId(String vodProductId) {
        this.vodProductId = vodProductId;
    }

    public Long getProductCategoryId() {
        return this.productCategoryId;
    }
    
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
   








}