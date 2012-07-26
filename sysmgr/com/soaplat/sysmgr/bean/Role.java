package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * Role entity. @author MyEclipse Persistence Tools
 */

public class Role  implements java.io.Serializable {


    // Fields    

     private String roleid;
     private String rolename;
     private String roletype;
     private String roledesc;
     private String rolecode;
     private String orgid;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public Role() {
    }

	/** minimal constructor */
    public Role(String roleid, String orgid) {
        this.roleid = roleid;
        this.orgid = orgid;
    }
    
    /** full constructor */
    public Role(String roleid, String rolename, String roletype, String roledesc, String rolecode, String orgid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.roleid = roleid;
        this.rolename = rolename;
        this.roletype = roletype;
        this.roledesc = roledesc;
        this.rolecode = rolecode;
        this.orgid = orgid;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getRoleid() {
        return this.roleid;
    }
    
    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return this.rolename;
    }
    
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoletype() {
        return this.roletype;
    }
    
    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

    public String getRoledesc() {
        return this.roledesc;
    }
    
    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }

    public String getRolecode() {
        return this.rolecode;
    }
    
    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public String getOrgid() {
        return this.orgid;
    }
    
    public void setOrgid(String orgid) {
        this.orgid = orgid;
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