package com.srmmgr.bean;
// default package

import java.util.Date;

//
/**
 * ConsumeDetailHis entity. @author MyEclipse Persistence Tools
 */

public class ConsumeDetailHis  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String sessionid;
     private String commandname;
     private String programid;
     private String productid;
     private String stbid;
     private String stbmac;
     private String stbip;
     private String smartcardid;
     private Date starttime;
     private Integer sendflag;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ConsumeDetailHis() {
    }

	/** minimal constructor */
    public ConsumeDetailHis(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public ConsumeDetailHis(String relid, String sessionid, String commandname, String programid, String productid, String stbid, String stbmac, String stbip, String smartcardid, Date starttime, Integer sendflag, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.sessionid = sessionid;
        this.commandname = commandname;
        this.programid = programid;
        this.productid = productid;
        this.stbid = stbid;
        this.stbmac = stbmac;
        this.stbip = stbip;
        this.smartcardid = smartcardid;
        this.starttime = starttime;
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

    public String getSessionid() {
        return this.sessionid;
    }
    
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getCommandname() {
        return this.commandname;
    }
    
    public void setCommandname(String commandname) {
        this.commandname = commandname;
    }

    public String getProgramid() {
        return this.programid;
    }
    
    public void setProgramid(String programid) {
        this.programid = programid;
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

    public Date getStarttime() {
        return this.starttime;
    }
    
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
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