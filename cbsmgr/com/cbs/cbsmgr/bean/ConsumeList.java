package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * ConsumeList entity. @author MyEclipse Persistence Tools
 */

public class ConsumeList  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String vodsid;
     private String ipqamchannelid;
     private String programid;
     private String stbid;
     private String stbmac;
     private String stbip;
     private String smartcardid;
     private String sessionid;
     private Date starttime;
     private Date endtime;
     private String desresouce;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ConsumeList() {
    }

	/** minimal constructor */
    public ConsumeList(String relid, String vodsid, String ipqamchannelid) {
        this.relid = relid;
        this.vodsid = vodsid;
        this.ipqamchannelid = ipqamchannelid;
    }
    
    /** full constructor */
    public ConsumeList(String relid, String vodsid, String ipqamchannelid, String programid, String stbid, String stbmac, String stbip, String smartcardid, String sessionid, Date starttime, Date endtime, String desresouce, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.vodsid = vodsid;
        this.ipqamchannelid = ipqamchannelid;
        this.programid = programid;
        this.stbid = stbid;
        this.stbmac = stbmac;
        this.stbip = stbip;
        this.smartcardid = smartcardid;
        this.sessionid = sessionid;
        this.starttime = starttime;
        this.endtime = endtime;
        this.desresouce = desresouce;
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

    public String getVodsid() {
        return this.vodsid;
    }
    
    public void setVodsid(String vodsid) {
        this.vodsid = vodsid;
    }

    public String getIpqamchannelid() {
        return this.ipqamchannelid;
    }
    
    public void setIpqamchannelid(String ipqamchannelid) {
        this.ipqamchannelid = ipqamchannelid;
    }

    public String getProgramid() {
        return this.programid;
    }
    
    public void setProgramid(String programid) {
        this.programid = programid;
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

    public String getDesresouce() {
        return this.desresouce;
    }
    
    public void setDesresouce(String desresouce) {
        this.desresouce = desresouce;
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