package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * PortalRoleOperRel entity. @author MyEclipse Persistence Tools
 */

public class PortalRoleOperRel  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String roleid;
     private String columnclassid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public PortalRoleOperRel() {
    }

	/** minimal constructor */
    public PortalRoleOperRel(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public PortalRoleOperRel(String relid, String roleid, String columnclassid, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.roleid = roleid;
        this.columnclassid = columnclassid;
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

    public String getColumnclassid() {
        return this.columnclassid;
    }
    
    public void setColumnclassid(String columnclassid) {
        this.columnclassid = columnclassid;
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