package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgLicences entity. @author MyEclipse Persistence Tools
 */

public class ProgLicences  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String programid;
     private String licencename;
     private String licenceno;
     private String licenceunit;
     private Date licencestarttime;
     private Date licenceendtime;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ProgLicences() {
    }

	/** minimal constructor */
    public ProgLicences(String relid, String programid) {
        this.relid = relid;
        this.programid = programid;
    }
    
    /** full constructor */
    public ProgLicences(String relid, String programid, String licencename, String licenceno, String licenceunit, Date licencestarttime, Date licenceendtime, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.programid = programid;
        this.licencename = licencename;
        this.licenceno = licenceno;
        this.licenceunit = licenceunit;
        this.licencestarttime = licencestarttime;
        this.licenceendtime = licenceendtime;
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

    public String getLicencename() {
        return this.licencename;
    }
    
    public void setLicencename(String licencename) {
        this.licencename = licencename;
    }

    public String getLicenceno() {
        return this.licenceno;
    }
    
    public void setLicenceno(String licenceno) {
        this.licenceno = licenceno;
    }

    public String getLicenceunit() {
        return this.licenceunit;
    }
    
    public void setLicenceunit(String licenceunit) {
        this.licenceunit = licenceunit;
    }

    public Date getLicencestarttime() {
        return this.licencestarttime;
    }
    
    public void setLicencestarttime(Date licencestarttime) {
        this.licencestarttime = licencestarttime;
    }

    public Date getLicenceendtime() {
        return this.licenceendtime;
    }
    
    public void setLicenceendtime(Date licenceendtime) {
        this.licenceendtime = licenceendtime;
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