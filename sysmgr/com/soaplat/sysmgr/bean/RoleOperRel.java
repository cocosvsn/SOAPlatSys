package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * RoleOperRel entity. @author MyEclipse Persistence Tools
 */

public class RoleOperRel  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String roleid;
     private String operatorid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public RoleOperRel() {
    }

	/** minimal constructor */
    public RoleOperRel(String relid, String roleid, String operatorid) {
        this.relid = relid;
        this.roleid = roleid;
        this.operatorid = operatorid;
    }
    
    /** full constructor */
    public RoleOperRel(String relid, String roleid, String operatorid, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.roleid = roleid;
        this.operatorid = operatorid;
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

    public String getRoleid() {
        return this.roleid;
    }
    
    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getOperatorid() {
        return this.operatorid;
    }
    
    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
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