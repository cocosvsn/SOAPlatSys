package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgCreator entity. @author MyEclipse Persistence Tools
 */

public class ProgCreator  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String programid;
     private String creatername;
     private String createrenname;
     private String createmode;
     private String otherinfo;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ProgCreator() {
    }

	/** minimal constructor */
    public ProgCreator(String relid, String programid) {
        this.relid = relid;
        this.programid = programid;
    }
    
    /** full constructor */
    public ProgCreator(String relid, String programid, String creatername, String createrenname, String createmode, String otherinfo, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.programid = programid;
        this.creatername = creatername;
        this.createrenname = createrenname;
        this.createmode = createmode;
        this.otherinfo = otherinfo;
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

    public String getCreatername() {
        return this.creatername;
    }
    
    public void setCreatername(String creatername) {
        this.creatername = creatername;
    }

    public String getCreaterenname() {
        return this.createrenname;
    }
    
    public void setCreaterenname(String createrenname) {
        this.createrenname = createrenname;
    }

    public String getCreatemode() {
        return this.createmode;
    }
    
    public void setCreatemode(String createmode) {
        this.createmode = createmode;
    }

    public String getOtherinfo() {
        return this.otherinfo;
    }
    
    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
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