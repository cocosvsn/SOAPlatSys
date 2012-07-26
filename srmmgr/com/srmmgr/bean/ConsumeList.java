package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * ConsumeList entity. @author MyEclipse Persistence Tools
 */

public class ConsumeList  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String vodsid;
     private String vodsip;
     private String ipqamchannelprogid;
     private String casturl;
     private String ipqamchannelid;
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
     private String desresouce;
     private Integer sendflag;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ConsumeList() {
    }

	/** minimal constructor */
    public ConsumeList(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public ConsumeList(String relid, String vodsid, String vodsip, String ipqamchannelprogid, String casturl, String ipqamchannelid, String programid, String servicetype, String productid, String stbid, String stbmac, String stbip, String smartcardid, String sessionid, String userid, Date starttime, Date endtime, String desresouce, Integer sendflag, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.vodsid = vodsid;
        this.vodsip = vodsip;
        this.ipqamchannelprogid = ipqamchannelprogid;
        this.casturl = casturl;
        this.ipqamchannelid = ipqamchannelid;
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
        this.desresouce = desresouce;
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

    public String getVodsid() {
        return this.vodsid;
    }
    
    public void setVodsid(String vodsid) {
        this.vodsid = vodsid;
    }

    public String getVodsip() {
        return this.vodsip;
    }
    
    public void setVodsip(String vodsip) {
        this.vodsip = vodsip;
    }

    public String getIpqamchannelprogid() {
        return this.ipqamchannelprogid;
    }
    
    public void setIpqamchannelprogid(String ipqamchannelprogid) {
        this.ipqamchannelprogid = ipqamchannelprogid;
    }

    public String getCasturl() {
        return this.casturl;
    }
    
    public void setCasturl(String casturl) {
        this.casturl = casturl;
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

    public String getDesresouce() {
        return this.desresouce;
    }
    
    public void setDesresouce(String desresouce) {
        this.desresouce = desresouce;
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