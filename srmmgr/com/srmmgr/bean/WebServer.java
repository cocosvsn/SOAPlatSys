package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * WebServer entity. @author MyEclipse Persistence Tools
 */

public class WebServer  implements java.io.Serializable {


    // Fields    

     private String websid;
     private String websglobalid;
     private String websname;
     private String websip;
     private String websdir;
     private String districtid;
     private String customertype;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public WebServer() {
    }

	/** minimal constructor */
    public WebServer(String websid) {
        this.websid = websid;
    }
    
    /** full constructor */
    public WebServer(String websid, String websglobalid, String websname, String websip, String websdir, String districtid, String customertype, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.websid = websid;
        this.websglobalid = websglobalid;
        this.websname = websname;
        this.websip = websip;
        this.websdir = websdir;
        this.districtid = districtid;
        this.customertype = customertype;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getWebsid() {
        return this.websid;
    }
    
    public void setWebsid(String websid) {
        this.websid = websid;
    }

    public String getWebsglobalid() {
        return this.websglobalid;
    }
    
    public void setWebsglobalid(String websglobalid) {
        this.websglobalid = websglobalid;
    }

    public String getWebsname() {
        return this.websname;
    }
    
    public void setWebsname(String websname) {
        this.websname = websname;
    }

    public String getWebsip() {
        return this.websip;
    }
    
    public void setWebsip(String websip) {
        this.websip = websip;
    }

    public String getWebsdir() {
        return this.websdir;
    }
    
    public void setWebsdir(String websdir) {
        this.websdir = websdir;
    }

    public String getDistrictid() {
        return this.districtid;
    }
    
    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public String getCustomertype() {
        return this.customertype;
    }
    
    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public String getDefaulticonpath() {
        return this.defaulticonpath;
    }
    
    public void setDefaulticonpath(String defaulticonpath) {
        this.defaulticonpath = defaulticonpath;
    }

    public String getActiveiconpath() {
        return this.activeiconpath;
    }
    
    public void setActiveiconpath(String activeiconpath) {
        this.activeiconpath = activeiconpath;
    }

    public String getInactiveiconpath() {
        return this.inactiveiconpath;
    }
    
    public void setInactiveiconpath(String inactiveiconpath) {
        this.inactiveiconpath = inactiveiconpath;
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