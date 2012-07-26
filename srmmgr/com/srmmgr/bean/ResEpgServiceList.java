package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * ResEpgServiceList entity. @author MyEclipse Persistence Tools
 */

public class ResEpgServiceList  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String resserverid;
     private String servicename;
     private String serviceurl;
     private String servicecode;
     private String servicehotkey;
     private String servicehotkeycode;
     private String servicetype;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ResEpgServiceList() {
    }

	/** minimal constructor */
    public ResEpgServiceList(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public ResEpgServiceList(String relid, String resserverid, String servicename, String serviceurl, String servicecode, String servicehotkey, String servicehotkeycode, String servicetype, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.resserverid = resserverid;
        this.servicename = servicename;
        this.serviceurl = serviceurl;
        this.servicecode = servicecode;
        this.servicehotkey = servicehotkey;
        this.servicehotkeycode = servicehotkeycode;
        this.servicetype = servicetype;
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

    public String getResserverid() {
        return this.resserverid;
    }
    
    public void setResserverid(String resserverid) {
        this.resserverid = resserverid;
    }

    public String getServicename() {
        return this.servicename;
    }
    
    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServiceurl() {
        return this.serviceurl;
    }
    
    public void setServiceurl(String serviceurl) {
        this.serviceurl = serviceurl;
    }

    public String getServicecode() {
        return this.servicecode;
    }
    
    public void setServicecode(String servicecode) {
        this.servicecode = servicecode;
    }

    public String getServicehotkey() {
        return this.servicehotkey;
    }
    
    public void setServicehotkey(String servicehotkey) {
        this.servicehotkey = servicehotkey;
    }

    public String getServicehotkeycode() {
        return this.servicehotkeycode;
    }
    
    public void setServicehotkeycode(String servicehotkeycode) {
        this.servicehotkeycode = servicehotkeycode;
    }

    public String getServicetype() {
        return this.servicetype;
    }
    
    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
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