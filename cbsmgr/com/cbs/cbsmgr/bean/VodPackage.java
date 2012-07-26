package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * VodPackage entity. @author MyEclipse Persistence Tools
 */

public class VodPackage  implements java.io.Serializable {


    // Fields    

     private String vodPackageId;
     private String vodDisplayCategoryId;
     private String ptglobalid;
     private String description;
     private String category;
     private String epicodenumber;
     private String subtitlelanguage;
     private String audiolanguage;
     private String director;
     private String productname;
     private String actors;
     private String shootdate;
     private String issuedate;
     private String countrydist;
     private Date subscriberstime;
     private Date subscriberetime;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public VodPackage() {
    }

	/** minimal constructor */
    public VodPackage(String vodPackageId) {
        this.vodPackageId = vodPackageId;
    }
    
    /** full constructor */
    public VodPackage(String vodPackageId, String vodDisplayCategoryId, String ptglobalid, String description, String category, String epicodenumber, String subtitlelanguage, String audiolanguage, String director, String productname, String actors, String shootdate, String issuedate, String countrydist, Date subscriberstime, Date subscriberetime, String inputmanid, Date inputtime, String remark) {
        this.vodPackageId = vodPackageId;
        this.vodDisplayCategoryId = vodDisplayCategoryId;
        this.ptglobalid = ptglobalid;
        this.description = description;
        this.category = category;
        this.epicodenumber = epicodenumber;
        this.subtitlelanguage = subtitlelanguage;
        this.audiolanguage = audiolanguage;
        this.director = director;
        this.productname = productname;
        this.actors = actors;
        this.shootdate = shootdate;
        this.issuedate = issuedate;
        this.countrydist = countrydist;
        this.subscriberstime = subscriberstime;
        this.subscriberetime = subscriberetime;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getVodPackageId() {
        return this.vodPackageId;
    }
    
    public void setVodPackageId(String vodPackageId) {
        this.vodPackageId = vodPackageId;
    }

    public String getVodDisplayCategoryId() {
        return this.vodDisplayCategoryId;
    }
    
    public void setVodDisplayCategoryId(String vodDisplayCategoryId) {
        this.vodDisplayCategoryId = vodDisplayCategoryId;
    }

    public String getPtglobalid() {
        return this.ptglobalid;
    }
    
    public void setPtglobalid(String ptglobalid) {
        this.ptglobalid = ptglobalid;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public String getEpicodenumber() {
        return this.epicodenumber;
    }
    
    public void setEpicodenumber(String epicodenumber) {
        this.epicodenumber = epicodenumber;
    }

    public String getSubtitlelanguage() {
        return this.subtitlelanguage;
    }
    
    public void setSubtitlelanguage(String subtitlelanguage) {
        this.subtitlelanguage = subtitlelanguage;
    }

    public String getAudiolanguage() {
        return this.audiolanguage;
    }
    
    public void setAudiolanguage(String audiolanguage) {
        this.audiolanguage = audiolanguage;
    }

    public String getDirector() {
        return this.director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }

    public String getProductname() {
        return this.productname;
    }
    
    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getActors() {
        return this.actors;
    }
    
    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getShootdate() {
        return this.shootdate;
    }
    
    public void setShootdate(String shootdate) {
        this.shootdate = shootdate;
    }

    public String getIssuedate() {
        return this.issuedate;
    }
    
    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getCountrydist() {
        return this.countrydist;
    }
    
    public void setCountrydist(String countrydist) {
        this.countrydist = countrydist;
    }

    public Date getSubscriberstime() {
        return this.subscriberstime;
    }
    
    public void setSubscriberstime(Date subscriberstime) {
        this.subscriberstime = subscriberstime;
    }

    public Date getSubscriberetime() {
        return this.subscriberetime;
    }
    
    public void setSubscriberetime(Date subscriberetime) {
        this.subscriberetime = subscriberetime;
    }

    public String getInputmanid() {
        return this.inputmanid;
    }
    
    public void setInputmanid(String inputmanid) {
        this.inputmanid = inputmanid;
    }

    public Date getInputtime() {
        return this.inputtime;
    }
    
    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}