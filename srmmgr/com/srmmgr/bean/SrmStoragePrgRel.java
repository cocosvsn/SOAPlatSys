package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * SrmStoragePrgRel entity. @author MyEclipse Persistence Tools
 */

public class SrmStoragePrgRel  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String stdirglobalid;
     private String ptglobalid;
     private String fglobalid;
     private String pglobalid;
     private String ftypeglobalid;
     private String filename;
     private Date uploadtime;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public SrmStoragePrgRel() {
    }

	/** minimal constructor */
    public SrmStoragePrgRel(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public SrmStoragePrgRel(String relid, String stdirglobalid, String ptglobalid, String fglobalid, String pglobalid, String ftypeglobalid, String filename, Date uploadtime, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.stdirglobalid = stdirglobalid;
        this.ptglobalid = ptglobalid;
        this.fglobalid = fglobalid;
        this.pglobalid = pglobalid;
        this.ftypeglobalid = ftypeglobalid;
        this.filename = filename;
        this.uploadtime = uploadtime;
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

    public String getStdirglobalid() {
        return this.stdirglobalid;
    }
    
    public void setStdirglobalid(String stdirglobalid) {
        this.stdirglobalid = stdirglobalid;
    }

    public String getPtglobalid() {
        return this.ptglobalid;
    }
    
    public void setPtglobalid(String ptglobalid) {
        this.ptglobalid = ptglobalid;
    }

    public String getFglobalid() {
        return this.fglobalid;
    }
    
    public void setFglobalid(String fglobalid) {
        this.fglobalid = fglobalid;
    }

    public String getPglobalid() {
        return this.pglobalid;
    }
    
    public void setPglobalid(String pglobalid) {
        this.pglobalid = pglobalid;
    }

    public String getFtypeglobalid() {
        return this.ftypeglobalid;
    }
    
    public void setFtypeglobalid(String ftypeglobalid) {
        this.ftypeglobalid = ftypeglobalid;
    }

    public String getFilename() {
        return this.filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getUploadtime() {
        return this.uploadtime;
    }
    
    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
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