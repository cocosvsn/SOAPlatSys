package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * PackStylePortalColumn entity. @author MyEclipse Persistence Tools
 */

public class PackStylePortalColumn  implements java.io.Serializable {


    // Fields    

     private String relid;
     private Long styleid;
     private String defcatcode;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public PackStylePortalColumn() {
    }

	/** minimal constructor */
    public PackStylePortalColumn(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public PackStylePortalColumn(String relid, Long styleid, String defcatcode, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.styleid = styleid;
        this.defcatcode = defcatcode;
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

    public Long getStyleid() {
        return this.styleid;
    }
    
    public void setStyleid(Long styleid) {
        this.styleid = styleid;
    }

    public String getDefcatcode() {
        return this.defcatcode;
    }
    
    public void setDefcatcode(String defcatcode) {
        this.defcatcode = defcatcode;
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