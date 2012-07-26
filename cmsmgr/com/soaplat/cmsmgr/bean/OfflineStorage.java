package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * OfflineStorage entity. @author MyEclipse Persistence Tools
 */

public class OfflineStorage  implements java.io.Serializable {


    // Fields    

     private String barcodeid;
     private String filetypeid;
     private Date bccreatedate;
     private String operatorid;
     private Long caphi;
     private Long caplow;
     private Long tapetypeid;
     private Long bcstatus;


    // Constructors

    /** default constructor */
    public OfflineStorage() {
    }

	/** minimal constructor */
    public OfflineStorage(String barcodeid) {
        this.barcodeid = barcodeid;
    }
    
    /** full constructor */
    public OfflineStorage(String barcodeid, String filetypeid, Date bccreatedate, String operatorid, Long caphi, Long caplow, Long tapetypeid, Long bcstatus) {
        this.barcodeid = barcodeid;
        this.filetypeid = filetypeid;
        this.bccreatedate = bccreatedate;
        this.operatorid = operatorid;
        this.caphi = caphi;
        this.caplow = caplow;
        this.tapetypeid = tapetypeid;
        this.bcstatus = bcstatus;
    }

   
    // Property accessors

    public String getBarcodeid() {
        return this.barcodeid;
    }
    
    public void setBarcodeid(String barcodeid) {
        this.barcodeid = barcodeid;
    }

    public String getFiletypeid() {
        return this.filetypeid;
    }
    
    public void setFiletypeid(String filetypeid) {
        this.filetypeid = filetypeid;
    }

    public Date getBccreatedate() {
        return this.bccreatedate;
    }
    
    public void setBccreatedate(Date bccreatedate) {
        this.bccreatedate = bccreatedate;
    }

    public String getOperatorid() {
        return this.operatorid;
    }
    
    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public Long getCaphi() {
        return this.caphi;
    }
    
    public void setCaphi(Long caphi) {
        this.caphi = caphi;
    }

    public Long getCaplow() {
        return this.caplow;
    }
    
    public void setCaplow(Long caplow) {
        this.caplow = caplow;
    }

    public Long getTapetypeid() {
        return this.tapetypeid;
    }
    
    public void setTapetypeid(Long tapetypeid) {
        this.tapetypeid = tapetypeid;
    }

    public Long getBcstatus() {
        return this.bcstatus;
    }
    
    public void setBcstatus(Long bcstatus) {
        this.bcstatus = bcstatus;
    }
   








}