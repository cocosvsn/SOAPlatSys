package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * VodServer entity. @author MyEclipse Persistence Tools
 */

public class VodServer  implements java.io.Serializable {


    // Fields    

     private String vodsid;
     private String vodsname;
     private String vscode;
     private String vodsip;
     private String vodsport;
     private String vodsdataip;
     private String vodsdataport;
     private Integer vodschannelnum;
     private String bandwidth;
     private String districtid;
     private String vosstype;
     private String clusterid;
     private String vodsvalid;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public VodServer() {
    }

	/** minimal constructor */
    public VodServer(String vodsid) {
        this.vodsid = vodsid;
    }
    
    /** full constructor */
    public VodServer(String vodsid, String vodsname, String vscode, String vodsip, String vodsport, String vodsdataip, String vodsdataport, Integer vodschannelnum, String bandwidth, String districtid, String vosstype, String clusterid, String vodsvalid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.vodsid = vodsid;
        this.vodsname = vodsname;
        this.vscode = vscode;
        this.vodsip = vodsip;
        this.vodsport = vodsport;
        this.vodsdataip = vodsdataip;
        this.vodsdataport = vodsdataport;
        this.vodschannelnum = vodschannelnum;
        this.bandwidth = bandwidth;
        this.districtid = districtid;
        this.vosstype = vosstype;
        this.clusterid = clusterid;
        this.vodsvalid = vodsvalid;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getVodsid() {
        return this.vodsid;
    }
    
    public void setVodsid(String vodsid) {
        this.vodsid = vodsid;
    }

    public String getVodsname() {
        return this.vodsname;
    }
    
    public void setVodsname(String vodsname) {
        this.vodsname = vodsname;
    }

    public String getVscode() {
        return this.vscode;
    }
    
    public void setVscode(String vscode) {
        this.vscode = vscode;
    }

    public String getVodsip() {
        return this.vodsip;
    }
    
    public void setVodsip(String vodsip) {
        this.vodsip = vodsip;
    }

    public String getVodsport() {
        return this.vodsport;
    }
    
    public void setVodsport(String vodsport) {
        this.vodsport = vodsport;
    }

    public String getVodsdataip() {
        return this.vodsdataip;
    }
    
    public void setVodsdataip(String vodsdataip) {
        this.vodsdataip = vodsdataip;
    }

    public String getVodsdataport() {
        return this.vodsdataport;
    }
    
    public void setVodsdataport(String vodsdataport) {
        this.vodsdataport = vodsdataport;
    }

    public Integer getVodschannelnum() {
        return this.vodschannelnum;
    }
    
    public void setVodschannelnum(Integer vodschannelnum) {
        this.vodschannelnum = vodschannelnum;
    }

    public String getBandwidth() {
        return this.bandwidth;
    }
    
    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getDistrictid() {
        return this.districtid;
    }
    
    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public String getVosstype() {
        return this.vosstype;
    }
    
    public void setVosstype(String vosstype) {
        this.vosstype = vosstype;
    }

    public String getClusterid() {
        return this.clusterid;
    }
    
    public void setClusterid(String clusterid) {
        this.clusterid = clusterid;
    }

    public String getVodsvalid() {
        return this.vodsvalid;
    }
    
    public void setVodsvalid(String vodsvalid) {
        this.vodsvalid = vodsvalid;
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