package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * SrmStorageDir entity. @author MyEclipse Persistence Tools
 */

public class SrmStorageDir  implements java.io.Serializable {


    // Fields    

     private String storagedirid;
     private String stdirglobalid;
     private String storageid;
     private String storagedirname;
     private String storagedirdesc;
     private String dirtotalcap;
     private String diralarmcap;
     private String dirfreecap;
     private String dirvalid;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public SrmStorageDir() {
    }

	/** minimal constructor */
    public SrmStorageDir(String storagedirid, String storageid) {
        this.storagedirid = storagedirid;
        this.storageid = storageid;
    }
    
    /** full constructor */
    public SrmStorageDir(String storagedirid, String stdirglobalid, String storageid, String storagedirname, String storagedirdesc, String dirtotalcap, String diralarmcap, String dirfreecap, String dirvalid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.storagedirid = storagedirid;
        this.stdirglobalid = stdirglobalid;
        this.storageid = storageid;
        this.storagedirname = storagedirname;
        this.storagedirdesc = storagedirdesc;
        this.dirtotalcap = dirtotalcap;
        this.diralarmcap = diralarmcap;
        this.dirfreecap = dirfreecap;
        this.dirvalid = dirvalid;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getStoragedirid() {
        return this.storagedirid;
    }
    
    public void setStoragedirid(String storagedirid) {
        this.storagedirid = storagedirid;
    }

    public String getStdirglobalid() {
        return this.stdirglobalid;
    }
    
    public void setStdirglobalid(String stdirglobalid) {
        this.stdirglobalid = stdirglobalid;
    }

    public String getStorageid() {
        return this.storageid;
    }
    
    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getStoragedirname() {
        return this.storagedirname;
    }
    
    public void setStoragedirname(String storagedirname) {
        this.storagedirname = storagedirname;
    }

    public String getStoragedirdesc() {
        return this.storagedirdesc;
    }
    
    public void setStoragedirdesc(String storagedirdesc) {
        this.storagedirdesc = storagedirdesc;
    }

    public String getDirtotalcap() {
        return this.dirtotalcap;
    }
    
    public void setDirtotalcap(String dirtotalcap) {
        this.dirtotalcap = dirtotalcap;
    }

    public String getDiralarmcap() {
        return this.diralarmcap;
    }
    
    public void setDiralarmcap(String diralarmcap) {
        this.diralarmcap = diralarmcap;
    }

    public String getDirfreecap() {
        return this.dirfreecap;
    }
    
    public void setDirfreecap(String dirfreecap) {
        this.dirfreecap = dirfreecap;
    }

    public String getDirvalid() {
        return this.dirvalid;
    }
    
    public void setDirvalid(String dirvalid) {
        this.dirvalid = dirvalid;
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