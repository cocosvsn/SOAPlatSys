package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgAward entity. @author MyEclipse Persistence Tools
 */

public class ProgAward  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String programid;
     private String awardtime;
     private String awardname;
     private String awarditem;
     private String awardwinner;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ProgAward() {
    }

	/** minimal constructor */
    public ProgAward(String relid, String programid) {
        this.relid = relid;
        this.programid = programid;
    }
    
    /** full constructor */
    public ProgAward(String relid, String programid, String awardtime, String awardname, String awarditem, String awardwinner, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.programid = programid;
        this.awardtime = awardtime;
        this.awardname = awardname;
        this.awarditem = awarditem;
        this.awardwinner = awardwinner;
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

    public String getAwardtime() {
        return this.awardtime;
    }
    
    public void setAwardtime(String awardtime) {
        this.awardtime = awardtime;
    }

    public String getAwardname() {
        return this.awardname;
    }
    
    public void setAwardname(String awardname) {
        this.awardname = awardname;
    }

    public String getAwarditem() {
        return this.awarditem;
    }
    
    public void setAwarditem(String awarditem) {
        this.awarditem = awarditem;
    }

    public String getAwardwinner() {
        return this.awardwinner;
    }
    
    public void setAwardwinner(String awardwinner) {
        this.awardwinner = awardwinner;
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