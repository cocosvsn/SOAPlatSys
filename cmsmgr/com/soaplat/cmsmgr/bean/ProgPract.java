package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgPract entity. @author MyEclipse Persistence Tools
 */

public class ProgPract  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String catid;
     private String entryid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ProgPract() {
    }

	/** minimal constructor */
    public ProgPract(String relid, String catid, String entryid) {
        this.relid = relid;
        this.catid = catid;
        this.entryid = entryid;
    }
    
    /** full constructor */
    public ProgPract(String relid, String catid, String entryid, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.catid = catid;
        this.entryid = entryid;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getRelid() {
        return this.relid;
    }
    
    public void setRelid(String relid) {
        this.relid = relid;
    }

    public String getCatid() {
        return this.catid;
    }
    
    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getEntryid() {
        return this.entryid;
    }
    
    public void setEntryid(String entryid) {
        this.entryid = entryid;
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