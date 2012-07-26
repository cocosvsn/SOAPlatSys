package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * GroupOperRel entity. @author MyEclipse Persistence Tools
 */

public class GroupOperRel  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String groupid;
     private String operatorid;
     private String operatorclass;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public GroupOperRel() {
    }

	/** minimal constructor */
    public GroupOperRel(String relid, String groupid, String operatorid) {
        this.relid = relid;
        this.groupid = groupid;
        this.operatorid = operatorid;
    }
    
    /** full constructor */
    public GroupOperRel(String relid, String groupid, String operatorid, String operatorclass, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.groupid = groupid;
        this.operatorid = operatorid;
        this.operatorclass = operatorclass;
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

    public String getOperatorid() {
        return this.operatorid;
    }
    
    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public String getOperatorclass() {
        return this.operatorclass;
    }
    
    public void setOperatorclass(String operatorclass) {
        this.operatorclass = operatorclass;
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