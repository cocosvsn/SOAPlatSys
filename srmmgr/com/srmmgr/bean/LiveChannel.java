package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * LiveChannel entity. @author MyEclipse Persistence Tools
 */

public class LiveChannel  implements java.io.Serializable {


    // Fields    

     private String livechannelid;
     private String lcglobalid;
     private String smslivechannelid;
     private String livechannelname;
     private String livechannelenname;
     private String livechannellogpath;
     private String livechanneladdr;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public LiveChannel() {
    }

	/** minimal constructor */
    public LiveChannel(String livechannelid) {
        this.livechannelid = livechannelid;
    }
    
    /** full constructor */
    public LiveChannel(String livechannelid, String lcglobalid, String smslivechannelid, String livechannelname, String livechannelenname, String livechannellogpath, String livechanneladdr, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.livechannelid = livechannelid;
        this.lcglobalid = lcglobalid;
        this.smslivechannelid = smslivechannelid;
        this.livechannelname = livechannelname;
        this.livechannelenname = livechannelenname;
        this.livechannellogpath = livechannellogpath;
        this.livechanneladdr = livechanneladdr;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getLivechannelid() {
        return this.livechannelid;
    }
    
    public void setLivechannelid(String livechannelid) {
        this.livechannelid = livechannelid;
    }

    public String getLcglobalid() {
        return this.lcglobalid;
    }
    
    public void setLcglobalid(String lcglobalid) {
        this.lcglobalid = lcglobalid;
    }

    public String getSmslivechannelid() {
        return this.smslivechannelid;
    }
    
    public void setSmslivechannelid(String smslivechannelid) {
        this.smslivechannelid = smslivechannelid;
    }

    public String getLivechannelname() {
        return this.livechannelname;
    }
    
    public void setLivechannelname(String livechannelname) {
        this.livechannelname = livechannelname;
    }

    public String getLivechannelenname() {
        return this.livechannelenname;
    }
    
    public void setLivechannelenname(String livechannelenname) {
        this.livechannelenname = livechannelenname;
    }

    public String getLivechannellogpath() {
        return this.livechannellogpath;
    }
    
    public void setLivechannellogpath(String livechannellogpath) {
        this.livechannellogpath = livechannellogpath;
    }

    public String getLivechanneladdr() {
        return this.livechanneladdr;
    }
    
    public void setLivechanneladdr(String livechanneladdr) {
        this.livechanneladdr = livechanneladdr;
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