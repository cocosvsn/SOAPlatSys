package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * Group entity. @author MyEclipse Persistence Tools
 */

public class Group  implements java.io.Serializable {


    // Fields    

     private String groupid;
     private String groupname;
     private String groupdesc;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public Group() {
    }

	/** minimal constructor */
    public Group(String groupid) {
        this.groupid = groupid;
    }
    
    /** full constructor */
    public Group(String groupid, String groupname, String groupdesc, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.groupid = groupid;
        this.groupname = groupname;
        this.groupdesc = groupdesc;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getGroupid() {
        return this.groupid;
    }
    
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return this.groupname;
    }
    
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupdesc() {
        return this.groupdesc;
    }
    
    public void setGroupdesc(String groupdesc) {
        this.groupdesc = groupdesc;
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