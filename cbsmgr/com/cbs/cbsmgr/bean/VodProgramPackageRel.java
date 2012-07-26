package com.cbs.cbsmgr.bean;
// default package



/**
 * VodProgramPackageRel entity. @author MyEclipse Persistence Tools
 */

public class VodProgramPackageRel  implements java.io.Serializable {


    // Fields    

     private String relId;
     private String vodPackageId;
     private String programid;
     private String remark;


    // Constructors

    /** default constructor */
    public VodProgramPackageRel() {
    }

	/** minimal constructor */
    public VodProgramPackageRel(String relId) {
        this.relId = relId;
    }
    
    /** full constructor */
    public VodProgramPackageRel(String relId, String vodPackageId, String programid, String remark) {
        this.relId = relId;
        this.vodPackageId = vodPackageId;
        this.programid = programid;
        this.remark = remark;
    }

   
    // Property accessors

    public String getRelId() {
        return this.relId;
    }
    
    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getVodPackageId() {
        return this.vodPackageId;
    }
    
    public void setVodPackageId(String vodPackageId) {
        this.vodPackageId = vodPackageId;
    }

    public String getProgramid() {
        return this.programid;
    }
    
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}