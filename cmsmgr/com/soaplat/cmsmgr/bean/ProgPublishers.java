package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgPublishers entity. @author MyEclipse Persistence Tools
 */

public class ProgPublishers  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String programid;
     private String publishername;
     private String publisherenname;
     private String publishernation;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ProgPublishers() {
    }

	/** minimal constructor */
    public ProgPublishers(String relid, String programid) {
        this.relid = relid;
        this.programid = programid;
    }
    
    /** full constructor */
    public ProgPublishers(String relid, String programid, String publishername, String publisherenname, String publishernation, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.programid = programid;
        this.publishername = publishername;
        this.publisherenname = publisherenname;
        this.publishernation = publishernation;
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

    public String getProgramid() {
        return this.programid;
    }
    
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getPublishername() {
        return this.publishername;
    }
    
    public void setPublishername(String publishername) {
        this.publishername = publishername;
    }

    public String getPublisherenname() {
        return this.publisherenname;
    }
    
    public void setPublisherenname(String publisherenname) {
        this.publisherenname = publisherenname;
    }

    public String getPublishernation() {
        return this.publishernation;
    }
    
    public void setPublishernation(String publishernation) {
        this.publishernation = publishernation;
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