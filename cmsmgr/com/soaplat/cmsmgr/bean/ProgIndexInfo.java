package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgIndexInfo entity. @author MyEclipse Persistence Tools
 */

public class ProgIndexInfo  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String programid;
     private String subject;
     private String caption;
     private String ownername;
     private String captionlanguage;
     private String progtype;
     private String audiolanguage;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ProgIndexInfo() {
    }

	/** minimal constructor */
    public ProgIndexInfo(String relid, String programid) {
        this.relid = relid;
        this.programid = programid;
    }
    
    /** full constructor */
    public ProgIndexInfo(String relid, String programid, String subject, String caption, String ownername, String captionlanguage, String progtype, String audiolanguage, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.programid = programid;
        this.subject = subject;
        this.caption = caption;
        this.ownername = ownername;
        this.captionlanguage = captionlanguage;
        this.progtype = progtype;
        this.audiolanguage = audiolanguage;
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

    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCaption() {
        return this.caption;
    }
    
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getOwnername() {
        return this.ownername;
    }
    
    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getCaptionlanguage() {
        return this.captionlanguage;
    }
    
    public void setCaptionlanguage(String captionlanguage) {
        this.captionlanguage = captionlanguage;
    }

    public String getProgtype() {
        return this.progtype;
    }
    
    public void setProgtype(String progtype) {
        this.progtype = progtype;
    }

    public String getAudiolanguage() {
        return this.audiolanguage;
    }
    
    public void setAudiolanguage(String audiolanguage) {
        this.audiolanguage = audiolanguage;
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