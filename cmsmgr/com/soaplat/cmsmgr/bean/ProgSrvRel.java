package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgSvrRel entity. @author MyEclipse Persistence Tools
 */

public class ProgSrvRel  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String productid;
     private String srvid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ProgSrvRel() {
    }

	/** minimal constructor */
    public ProgSrvRel(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public ProgSrvRel(String relid, String productid, String srvid, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.productid = productid;
        this.srvid = srvid;
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

    public String getSrvid() {
        return this.srvid;
    }
    
    public void setSrvid(String srvid) {
        this.srvid = srvid;
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