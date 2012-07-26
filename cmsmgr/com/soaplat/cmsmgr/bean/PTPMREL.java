package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * PTPMREL entity. @author MyEclipse Persistence Tools
 */

public class PTPMREL  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String productid;
     private String programid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public PTPMREL() {
    }

	/** minimal constructor */
    public PTPMREL(String relid, String productid, String programid) {
        this.relid = relid;
        this.productid = productid;
        this.programid = programid;
    }
    
    /** full constructor */
    public PTPMREL(String relid, String productid, String programid, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.productid = productid;
        this.programid = programid;
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

    public String getProductid() {
        return this.productid;
    }
    
    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProgramid() {
        return this.programid;
    }
    
    public void setProgramid(String programid) {
        this.programid = programid;
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