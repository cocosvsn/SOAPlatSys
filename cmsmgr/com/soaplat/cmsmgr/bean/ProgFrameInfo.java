package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgFrameInfo entity. @author MyEclipse Persistence Tools
 */

public class ProgFrameInfo  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String programid;
     private String framedir;
     private String frmaepath;
     private String timecode;
     private Long framestatus;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ProgFrameInfo() {
    }

	/** minimal constructor */
    public ProgFrameInfo(String relid, String programid) {
        this.relid = relid;
        this.programid = programid;
    }
    
    /** full constructor */
    public ProgFrameInfo(String relid, String programid, String framedir, String frmaepath, String timecode, Long framestatus, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.programid = programid;
        this.framedir = framedir;
        this.frmaepath = frmaepath;
        this.timecode = timecode;
        this.framestatus = framestatus;
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

    public String getFramedir() {
        return this.framedir;
    }
    
    public void setFramedir(String framedir) {
        this.framedir = framedir;
    }

    public String getFrmaepath() {
        return this.frmaepath;
    }
    
    public void setFrmaepath(String frmaepath) {
        this.frmaepath = frmaepath;
    }

    public String getTimecode() {
        return this.timecode;
    }
    
    public void setTimecode(String timecode) {
        this.timecode = timecode;
    }

    public Long getFramestatus() {
        return this.framestatus;
    }
    
    public void setFramestatus(Long framestatus) {
        this.framestatus = framestatus;
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