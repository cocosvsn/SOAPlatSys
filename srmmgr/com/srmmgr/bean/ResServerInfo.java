package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * ResServerInfo entity. @author MyEclipse Persistence Tools
 */

public class ResServerInfo  implements java.io.Serializable {


    // Fields    

     private String resserverid;
     private String resservername;
     private String resserverurl;
     private String resservercode;
     private String resservertype;
     private String districtid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ResServerInfo() {
    }

	/** minimal constructor */
    public ResServerInfo(String resserverid) {
        this.resserverid = resserverid;
    }
    
    /** full constructor */
    public ResServerInfo(String resserverid, String resservername, String resserverurl, String resservercode, String resservertype, String districtid, String inputmanid, Date inputtime, String remark) {
        this.resserverid = resserverid;
        this.resservername = resservername;
        this.resserverurl = resserverurl;
        this.resservercode = resservercode;
        this.resservertype = resservertype;
        this.districtid = districtid;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getResserverid() {
        return this.resserverid;
    }
    
    public void setResserverid(String resserverid) {
        this.resserverid = resserverid;
    }

    public String getResservername() {
        return this.resservername;
    }
    
    public void setResservername(String resservername) {
        this.resservername = resservername;
    }

    public String getResserverurl() {
        return this.resserverurl;
    }
    
    public void setResserverurl(String resserverurl) {
        this.resserverurl = resserverurl;
    }

    public String getResservercode() {
        return this.resservercode;
    }
    
    public void setResservercode(String resservercode) {
        this.resservercode = resservercode;
    }

    public String getResservertype() {
        return this.resservertype;
    }
    
    public void setResservertype(String resservertype) {
        this.resservertype = resservertype;
    }

    public String getDistrictid() {
        return this.districtid;
    }
    
    public void setDistrictid(String districtid) {
        this.districtid = districtid;
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