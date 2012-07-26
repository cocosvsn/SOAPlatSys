package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * SrvColumn entity. @author MyEclipse Persistence Tools
 */

public class SrvColumn  implements java.io.Serializable {


    // Fields    

     private String srvprogid;
     private String srvid;
     private String columnclassid;
     private Long selectTag;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public SrvColumn() {
    }

	/** minimal constructor */
    public SrvColumn(String srvprogid) {
        this.srvprogid = srvprogid;
    }
    
    /** full constructor */
    public SrvColumn(String srvprogid, String srvid, String columnclassid, Long selectTag, String inputmanid, Date inputtime, String remark) {
        this.srvprogid = srvprogid;
        this.srvid = srvid;
        this.columnclassid = columnclassid;
        this.selectTag = selectTag;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getSrvprogid() {
        return this.srvprogid;
    }
    
    public void setSrvprogid(String srvprogid) {
        this.srvprogid = srvprogid;
    }

    public String getSrvid() {
        return this.srvid;
    }
    
    public void setSrvid(String srvid) {
        this.srvid = srvid;
    }

    public String getColumnclassid() {
        return this.columnclassid;
    }
    
    public void setColumnclassid(String columnclassid) {
        this.columnclassid = columnclassid;
    }

    public Long getSelectTag() {
        return this.selectTag;
    }
    
    public void setSelectTag(Long selectTag) {
        this.selectTag = selectTag;
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