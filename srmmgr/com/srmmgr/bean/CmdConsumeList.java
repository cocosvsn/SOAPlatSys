package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * CmdConsumeList entity. @author MyEclipse Persistence Tools
 */

public class CmdConsumeList  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String programid;
     private String servicetype;
     private String productid;
     private String stbid;
     private String stbmac;
     private String stbip;
     private String smartcardid;
     private String sessionid;
     private String userid;
     private Date starttime;
     private Date endtime;
     private String endstatus;
     private Integer sendflag;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public CmdConsumeList() {
    }

	/** minimal constructor */
    public CmdConsumeList(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public CmdConsumeList(String relid, String programid, String servicetype, String productid, String stbid, String stbmac, String stbip, String smartcardid, String sessionid, String userid, Date starttime, Date endtime, String endstatus, Integer sendflag, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.programid = programid;
        this.servicetype = servicetype;
        this.productid = productid;
        this.stbid = stbid;
        this.stbmac = stbmac;
        this.stbip = stbip;
        this.smartcardid = smartcardid;
        this.sessionid = sessionid;
        this.userid = userid;
        this.starttime = starttime;
        this.endtime = endtime;
        this.endstatus = endstatus;
        this.sendflag = sendflag;
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

    public String getServicetype() {
        return this.servicetype;
    }
    
    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getProductid() {
        return this.productid;
    }
    
    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getStbid() {
        return this.stbid;
    }
    
    public void setStbid(String stbid) {
        this.stbid = stbid;
    }

    public String getStbmac() {
        return this.stbmac;
    }
    
    public void setStbmac(String stbmac) {
        this.stbmac = stbmac;
    }

    public String getStbip() {
        return this.stbip;
    }
    
    public void setStbip(String stbip) {
        this.stbip = stbip;
    }

    public String getSmartcardid() {
        return this.smartcardid;
    }
    
    public void setSmartcardid(String smartcardid) {
        this.smartcardid = smartcardid;
    }

    public String getSessionid() {
        return this.sessionid;
    }
    
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getStarttime() {
        return this.starttime;
    }
    
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return this.endtime;
    }
    
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getEndstatus() {
        return this.endstatus;
    }
    
    public void setEndstatus(String endstatus) {
        this.endstatus = endstatus;
    }

    public Integer getSendflag() {
        return this.sendflag;
    }
    
    public void setSendflag(Integer sendflag) {
        this.sendflag = sendflag;
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