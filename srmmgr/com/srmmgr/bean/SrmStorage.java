package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * SrmStorage entity. @author MyEclipse Persistence Tools
 */

public class SrmStorage  implements java.io.Serializable {


    // Fields    

     private String storageid;
     private String stglobalid;
     private String storagename;
     private String storageip;
     private String storagetype;
     private String storageaccstype;
     private String loginname;
     private String loginpwd;
     private String mappath;
     private String maploginuid;
     private String maploginpwd;
     private String maplogindisk;
     private String totalcap;
     private String storagevalid;
     private String districtid;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public SrmStorage() {
    }

	/** minimal constructor */
    public SrmStorage(String storageid) {
        this.storageid = storageid;
    }
    
    /** full constructor */
    public SrmStorage(String storageid, String stglobalid, String storagename, String storageip, String storagetype, String storageaccstype, String loginname, String loginpwd, String mappath, String maploginuid, String maploginpwd, String maplogindisk, String totalcap, String storagevalid, String districtid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.storageid = storageid;
        this.stglobalid = stglobalid;
        this.storagename = storagename;
        this.storageip = storageip;
        this.storagetype = storagetype;
        this.storageaccstype = storageaccstype;
        this.loginname = loginname;
        this.loginpwd = loginpwd;
        this.mappath = mappath;
        this.maploginuid = maploginuid;
        this.maploginpwd = maploginpwd;
        this.maplogindisk = maplogindisk;
        this.totalcap = totalcap;
        this.storagevalid = storagevalid;
        this.districtid = districtid;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getStorageid() {
        return this.storageid;
    }
    
    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getStglobalid() {
        return this.stglobalid;
    }
    
    public void setStglobalid(String stglobalid) {
        this.stglobalid = stglobalid;
    }

    public String getStoragename() {
        return this.storagename;
    }
    
    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    public String getStorageip() {
        return this.storageip;
    }
    
    public void setStorageip(String storageip) {
        this.storageip = storageip;
    }

    public String getStoragetype() {
        return this.storagetype;
    }
    
    public void setStoragetype(String storagetype) {
        this.storagetype = storagetype;
    }

    public String getStorageaccstype() {
        return this.storageaccstype;
    }
    
    public void setStorageaccstype(String storageaccstype) {
        this.storageaccstype = storageaccstype;
    }

    public String getLoginname() {
        return this.loginname;
    }
    
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getLoginpwd() {
        return this.loginpwd;
    }
    
    public void setLoginpwd(String loginpwd) {
        this.loginpwd = loginpwd;
    }

    public String getMappath() {
        return this.mappath;
    }
    
    public void setMappath(String mappath) {
        this.mappath = mappath;
    }

    public String getMaploginuid() {
        return this.maploginuid;
    }
    
    public void setMaploginuid(String maploginuid) {
        this.maploginuid = maploginuid;
    }

    public String getMaploginpwd() {
        return this.maploginpwd;
    }
    
    public void setMaploginpwd(String maploginpwd) {
        this.maploginpwd = maploginpwd;
    }

    public String getMaplogindisk() {
        return this.maplogindisk;
    }
    
    public void setMaplogindisk(String maplogindisk) {
        this.maplogindisk = maplogindisk;
    }

    public String getTotalcap() {
        return this.totalcap;
    }
    
    public void setTotalcap(String totalcap) {
        this.totalcap = totalcap;
    }

    public String getStoragevalid() {
        return this.storagevalid;
    }
    
    public void setStoragevalid(String storagevalid) {
        this.storagevalid = storagevalid;
    }

    public String getDistrictid() {
        return this.districtid;
    }
    
    public void setDistrictid(String districtid) {
        this.districtid = districtid;
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