package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * ChannelList entity. @author MyEclipse Persistence Tools
 */

public class ChannelList  implements java.io.Serializable {


    // Fields    

     private String channelid;
     private String channelgid;
     private String channelsmsid;
     private String channelname;
     private String channelnameen;
     private Integer userchannelid;
     private String channelurl;
     private String timeshift;
     private String channelsdp;
     private String timeshifturl;
     private String channeltype;
     private String channellogurl;
     private String multicasturl;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ChannelList() {
    }

	/** minimal constructor */
    public ChannelList(String channelid) {
        this.channelid = channelid;
    }
    
    /** full constructor */
    public ChannelList(String channelid, String channelgid, String channelsmsid, String channelname, String channelnameen, Integer userchannelid, String channelurl, String timeshift, String channelsdp, String timeshifturl, String channeltype, String channellogurl, String multicasturl, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.channelid = channelid;
        this.channelgid = channelgid;
        this.channelsmsid = channelsmsid;
        this.channelname = channelname;
        this.channelnameen = channelnameen;
        this.userchannelid = userchannelid;
        this.channelurl = channelurl;
        this.timeshift = timeshift;
        this.channelsdp = channelsdp;
        this.timeshifturl = timeshifturl;
        this.channeltype = channeltype;
        this.channellogurl = channellogurl;
        this.multicasturl = multicasturl;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getChannelid() {
        return this.channelid;
    }
    
    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getChannelgid() {
        return this.channelgid;
    }
    
    public void setChannelgid(String channelgid) {
        this.channelgid = channelgid;
    }

    public String getChannelsmsid() {
        return this.channelsmsid;
    }
    
    public void setChannelsmsid(String channelsmsid) {
        this.channelsmsid = channelsmsid;
    }

    public String getChannelname() {
        return this.channelname;
    }
    
    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getChannelnameen() {
        return this.channelnameen;
    }
    
    public void setChannelnameen(String channelnameen) {
        this.channelnameen = channelnameen;
    }

    public Integer getUserchannelid() {
        return this.userchannelid;
    }
    
    public void setUserchannelid(Integer userchannelid) {
        this.userchannelid = userchannelid;
    }

    public String getChannelurl() {
        return this.channelurl;
    }
    
    public void setChannelurl(String channelurl) {
        this.channelurl = channelurl;
    }

    public String getTimeshift() {
        return this.timeshift;
    }
    
    public void setTimeshift(String timeshift) {
        this.timeshift = timeshift;
    }

    public String getChannelsdp() {
        return this.channelsdp;
    }
    
    public void setChannelsdp(String channelsdp) {
        this.channelsdp = channelsdp;
    }

    public String getTimeshifturl() {
        return this.timeshifturl;
    }
    
    public void setTimeshifturl(String timeshifturl) {
        this.timeshifturl = timeshifturl;
    }

    public String getChanneltype() {
        return this.channeltype;
    }
    
    public void setChanneltype(String channeltype) {
        this.channeltype = channeltype;
    }

    public String getChannellogurl() {
        return this.channellogurl;
    }
    
    public void setChannellogurl(String channellogurl) {
        this.channellogurl = channellogurl;
    }

    public String getMulticasturl() {
        return this.multicasturl;
    }
    
    public void setMulticasturl(String multicasturl) {
        this.multicasturl = multicasturl;
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