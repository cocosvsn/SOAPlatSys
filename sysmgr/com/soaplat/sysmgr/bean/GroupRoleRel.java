package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * GroupRoleRel entity. @author MyEclipse Persistence Tools
 */

public class GroupRoleRel  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String groupid;
     private String roleid;
     private String roleclass;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public GroupRoleRel() {
    }

	/** minimal constructor */
    public GroupRoleRel(String relid, String groupid, String roleid) {
        this.relid = relid;
        this.groupid = groupid;
        this.roleid = roleid;
    }
    
    /** full constructor */
    public GroupRoleRel(String relid, String groupid, String roleid, String roleclass, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.groupid = groupid;
        this.roleid = roleid;
        this.roleclass = roleclass;
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

    public String getGroupid() {
        return this.groupid;
    }
    
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getRoleid() {
        return this.roleid;
    }
    
    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRoleclass() {
        return this.roleclass;
    }
    
    public void setRoleclass(String roleclass) {
        this.roleclass = roleclass;
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