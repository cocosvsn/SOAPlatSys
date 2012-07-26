package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * OfflineProg entity. @author MyEclipse Persistence Tools
 */

public class OfflineProg  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String barcodeid;
     private String programid;
     private String filetypeid;
     private Date copydate;
     private String copyoperatorid;


    // Constructors

    /** default constructor */
    public OfflineProg() {
    }

	/** minimal constructor */
    public OfflineProg(String relid, String barcodeid, String programid) {
        this.relid = relid;
        this.barcodeid = barcodeid;
        this.programid = programid;
    }
    
    /** full constructor */
    public OfflineProg(String relid, String barcodeid, String programid, String filetypeid, Date copydate, String copyoperatorid) {
        this.relid = relid;
        this.barcodeid = barcodeid;
        this.programid = programid;
        this.filetypeid = filetypeid;
        this.copydate = copydate;
        this.copyoperatorid = copyoperatorid;
    }

   
    // Property accessors

    public String getRelid() {
        return this.relid;
    }
    
    public void setRelid(String relid) {
        this.relid = relid;
    }

    public String getBarcodeid() {
        return this.barcodeid;
    }
    
    public void setBarcodeid(String barcodeid) {
        this.barcodeid = barcodeid;
    }

    public String getProgramid() {
        return this.programid;
    }
    
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getFiletypeid() {
        return this.filetypeid;
    }
    
    public void setFiletypeid(String filetypeid) {
        this.filetypeid = filetypeid;
    }

    public Date getCopydate() {
        return this.copydate;
    }
    
    public void setCopydate(Date copydate) {
        this.copydate = copydate;
    }

    public String getCopyoperatorid() {
        return this.copyoperatorid;
    }
    
    public void setCopyoperatorid(String copyoperatorid) {
        this.copyoperatorid = copyoperatorid;
    }
   








}