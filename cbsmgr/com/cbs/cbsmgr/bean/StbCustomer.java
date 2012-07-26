package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * StbCustomer entity. @author MyEclipse Persistence Tools
 */

public class StbCustomer  implements java.io.Serializable {


    // Fields    

     private String customerid;
     private String smscstid;
     private String uid;
     private String pwd;
     private Date cstcrttime;
     private Date cstdsttime;
     private String paymode;
     private String creditinfo;
     private String districtid;
     private Double cstfree;
     private String stbid;
     private String stbmac;
     private String stbip;
     private String smartcardid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public StbCustomer() {
    }

	/** minimal constructor */
    public StbCustomer(String customerid) {
        this.customerid = customerid;
    }
    
    /** full constructor */
    public StbCustomer(String customerid, String smscstid, String uid, String pwd, Date cstcrttime, Date cstdsttime, String paymode, String creditinfo, String districtid, Double cstfree, String stbid, String stbmac, String stbip, String smartcardid, String inputmanid, Date inputtime, String remark) {
        this.customerid = customerid;
        this.smscstid = smscstid;
        this.uid = uid;
        this.pwd = pwd;
        this.cstcrttime = cstcrttime;
        this.cstdsttime = cstdsttime;
        this.paymode = paymode;
        this.creditinfo = creditinfo;
        this.districtid = districtid;
        this.cstfree = cstfree;
        this.stbid = stbid;
        this.stbmac = stbmac;
        this.stbip = stbip;
        this.smartcardid = smartcardid;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getCustomerid() {
        return this.customerid;
    }
    
    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getSmscstid() {
        return this.smscstid;
    }
    
    public void setSmscstid(String smscstid) {
        this.smscstid = smscstid;
    }

    public String getUid() {
        return this.uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return this.pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getCstcrttime() {
        return this.cstcrttime;
    }
    
    public void setCstcrttime(Date cstcrttime) {
        this.cstcrttime = cstcrttime;
    }

    public Date getCstdsttime() {
        return this.cstdsttime;
    }
    
    public void setCstdsttime(Date cstdsttime) {
        this.cstdsttime = cstdsttime;
    }

    public String getPaymode() {
        return this.paymode;
    }
    
    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getCreditinfo() {
        return this.creditinfo;
    }
    
    public void setCreditinfo(String creditinfo) {
        this.creditinfo = creditinfo;
    }

    public String getDistrictid() {
        return this.districtid;
    }
    
    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public Double getCstfree() {
        return this.cstfree;
    }
    
    public void setCstfree(Double cstfree) {
        this.cstfree = cstfree;
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