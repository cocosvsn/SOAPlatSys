package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * StorageVsRel entity. @author MyEclipse Persistence Tools
 */

public class StorageVsRel  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String storageid;
     private String vodsid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public StorageVsRel() {
    }

	/** minimal constructor */
    public StorageVsRel(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public StorageVsRel(String relid, String storageid, String vodsid, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.storageid = storageid;
        this.vodsid = vodsid;
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

    public String getStorageid() {
        return this.storageid;
    }
    
    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    public String getVodsid() {
        return this.vodsid;
    }
    
    public void setVodsid(String vodsid) {
        this.vodsid = vodsid;
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