package com.cbs.cbsmgr.bean;
// default package



/**
 * ContentCategory entity. @author MyEclipse Persistence Tools
 */

public class ContentCategory  implements java.io.Serializable {


    // Fields    

     private String contentCategoryId;
     private String description;


    // Constructors

    /** default constructor */
    public ContentCategory() {
    }

	/** minimal constructor */
    public ContentCategory(String contentCategoryId) {
        this.contentCategoryId = contentCategoryId;
    }
    
    /** full constructor */
    public ContentCategory(String contentCategoryId, String description) {
        this.contentCategoryId = contentCategoryId;
        this.description = description;
    }

   
    // Property accessors

    public String getContentCategoryId() {
        return this.contentCategoryId;
    }
    
    public void setContentCategoryId(String contentCategoryId) {
        this.contentCategoryId = contentCategoryId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
   








}