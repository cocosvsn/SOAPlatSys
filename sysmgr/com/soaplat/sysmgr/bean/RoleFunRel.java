package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * RoleFunRel entity. @author MyEclipse Persistence Tools
 */

public class RoleFunRel  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String roleid;
     private String funcid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public RoleFunRel() {
    }

	/** minimal constructor */
    public RoleFunRel(String relid, String roleid, String funcid) {
        this.relid = relid;
        this.roleid = roleid;
        this.funcid = funcid;
    }
    
    /** full constructor */
    public RoleFunRel(String relid, String roleid, String funcid, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.roleid = roleid;
        this.funcid = funcid;
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

    public String getFuncid() {
        return this.funcid;
    }
    
    public void setFuncid(String funcid) {
        this.funcid = funcid;
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