package com.cbs.cbsmgr.bean;
// default package



/**
 * VodDisplayCategory entity. @author MyEclipse Persistence Tools
 */

public class VodDisplayCategory  implements java.io.Serializable {


    // Fields    

     private String vodDisplayCategoryId;
     private String description;
     private String title;


    // Constructors

    /** default constructor */
    public VodDisplayCategory() {
    }

	/** minimal constructor */
    public VodDisplayCategory(String vodDisplayCategoryId) {
        this.vodDisplayCategoryId = vodDisplayCategoryId;
    }
    
    /** full constructor */
    public VodDisplayCategory(String vodDisplayCategoryId, String description, String title) {
        this.vodDisplayCategoryId = vodDisplayCategoryId;
        this.description = description;
        this.title = title;
    }

   
    // Property accessors

    public String getVodDisplayCategoryId() {
        return this.vodDisplayCategoryId;
    }
    
    public void setVodDisplayCategoryId(String vodDisplayCategoryId) {
        this.vodDisplayCategoryId = vodDisplayCategoryId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
   








}