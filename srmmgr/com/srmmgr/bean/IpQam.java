package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * IpQam entity. @author MyEclipse Persistence Tools
 */

public class IpQam  implements java.io.Serializable {


    // Fields    

     private String ipqamid;
     private String ipqamname;
     private String ipqammanip;
     private String ipqamdataip;
     private String rfport;
     private String shareable;
     private String districtid;
     private String clusterid;
     private String ipqamvalid;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public IpQam() {
    }

	/** minimal constructor */
    public IpQam(String ipqamid) {
        this.ipqamid = ipqamid;
    }
    
    /** full constructor */
    public IpQam(String ipqamid, String ipqamname, String ipqammanip, String ipqamdataip, String rfport, String shareable, String districtid, String clusterid, String ipqamvalid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.ipqamid = ipqamid;
        this.ipqamname = ipqamname;
        this.ipqammanip = ipqammanip;
        this.ipqamdataip = ipqamdataip;
        this.rfport = rfport;
        this.shareable = shareable;
        this.districtid = districtid;
        this.clusterid = clusterid;
        this.ipqamvalid = ipqamvalid;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getIpqamid() {
        return this.ipqamid;
    }
    
    public void setIpqamid(String ipqamid) {
        this.ipqamid = ipqamid;
    }

    public String getIpqamname() {
        return this.ipqamname;
    }
    
    public void setIpqamname(String ipqamname) {
        this.ipqamname = ipqamname;
    }

    public String getIpqammanip() {
        return this.ipqammanip;
    }
    
    public void setIpqammanip(String ipqammanip) {
        this.ipqammanip = ipqammanip;
    }

    public String getIpqamdataip() {
        return this.ipqamdataip;
    }
    
    public void setIpqamdataip(String ipqamdataip) {
        this.ipqamdataip = ipqamdataip;
    }

    public String getRfport() {
        return this.rfport;
    }
    
    public void setRfport(String rfport) {
        this.rfport = rfport;
    }

    public String getShareable() {
        return this.shareable;
    }
    
    public void setShareable(String shareable) {
        this.shareable = shareable;
    }

    public String getDistrictid() {
        return this.districtid;
    }
    
    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public String getClusterid() {
        return this.clusterid;
    }
    
    public void setClusterid(String clusterid) {
        this.clusterid = clusterid;
    }

    public String getIpqamvalid() {
        return this.ipqamvalid;
    }
    
    public void setIpqamvalid(String ipqamvalid) {
        this.ipqamvalid = ipqamvalid;
    }

    public String getDefaulticonpath() {
        return this.defaulticonpath;
    }
    
    public void setDefaulticonpath(String defaulticonpath) {
        this.defaulticonpath = defaulticonpath;
    }

    public String getActiveiconpath() {
        return this.activeiconpath;
    }
    
    public void setActiveiconpath(String activeiconpath) {
        this.activeiconpath = activeiconpath;
    }

    public String getInactiveiconpath() {
        return this.inactiveiconpath;
    }
    
    public void setInactiveiconpath(String inactiveiconpath) {
        this.inactiveiconpath = inactiveiconpath;
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