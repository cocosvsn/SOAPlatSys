package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * IpQamChannel entity. @author MyEclipse Persistence Tools
 */

public class IpQamChannel  implements java.io.Serializable {


    // Fields    

     private String ipqamchannelid;
     private String ipqamid;
     private String freqname;
     private String moduleid;
     private String unitid;
     private String channelno;
     private String ipaddr;
     private String modulation;
     private String frequency;
     private String symbolrate;
     private String bandwidth;
     private String channelvalid;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public IpQamChannel() {
    }

	/** minimal constructor */
    public IpQamChannel(String ipqamchannelid) {
        this.ipqamchannelid = ipqamchannelid;
    }
    
    /** full constructor */
    public IpQamChannel(String ipqamchannelid, String ipqamid, String freqname, String moduleid, String unitid, String channelno, String ipaddr, String modulation, String frequency, String symbolrate, String bandwidth, String channelvalid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.ipqamchannelid = ipqamchannelid;
        this.ipqamid = ipqamid;
        this.freqname = freqname;
        this.moduleid = moduleid;
        this.unitid = unitid;
        this.channelno = channelno;
        this.ipaddr = ipaddr;
        this.modulation = modulation;
        this.frequency = frequency;
        this.symbolrate = symbolrate;
        this.bandwidth = bandwidth;
        this.channelvalid = channelvalid;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getIpqamchannelid() {
        return this.ipqamchannelid;
    }
    
    public void setIpqamchannelid(String ipqamchannelid) {
        this.ipqamchannelid = ipqamchannelid;
    }

    public String getIpqamid() {
        return this.ipqamid;
    }
    
    public void setIpqamid(String ipqamid) {
        this.ipqamid = ipqamid;
    }

    public String getFreqname() {
        return this.freqname;
    }
    
    public void setFreqname(String freqname) {
        this.freqname = freqname;
    }

    public String getModuleid() {
        return this.moduleid;
    }
    
    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    public String getUnitid() {
        return this.unitid;
    }
    
    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getChannelno() {
        return this.channelno;
    }
    
    public void setChannelno(String channelno) {
        this.channelno = channelno;
    }

    public String getIpaddr() {
        return this.ipaddr;
    }
    
    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getModulation() {
        return this.modulation;
    }
    
    public void setModulation(String modulation) {
        this.modulation = modulation;
    }

    public String getFrequency() {
        return this.frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSymbolrate() {
        return this.symbolrate;
    }
    
    public void setSymbolrate(String symbolrate) {
        this.symbolrate = symbolrate;
    }

    public String getBandwidth() {
        return this.bandwidth;
    }
    
    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getChannelvalid() {
        return this.channelvalid;
    }
    
    public void setChannelvalid(String channelvalid) {
        this.channelvalid = channelvalid;
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