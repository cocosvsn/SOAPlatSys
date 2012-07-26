package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * PackStyle entity. @author MyEclipse Persistence Tools
 */

public class PackStyle  implements java.io.Serializable {


    // Fields    

     private Long styleid;
     private String stylename;
     private Date sytlecreatedate;
     private String operatorid;
     private Long bcstatus;
     private String stylexml;
     private String progtype;
     private Long styletype;


    // Constructors

    public Long getStyletype() {
		return styletype;
	}

	public void setStyletype(Long styletype) {
		this.styletype = styletype;
	}

	/** default constructor */
    public PackStyle() {
    }

	/** minimal constructor */
    public PackStyle(Long styleid) {
        this.styleid = styleid;
    }
    
    /** full constructor */
    public PackStyle(Long styleid, String stylename, Date sytlecreatedate, String operatorid, Long bcstatus, String stylexml, String progtype) {
        this.styleid = styleid;
        this.stylename = stylename;
        this.sytlecreatedate = sytlecreatedate;
        this.operatorid = operatorid;
        this.bcstatus = bcstatus;
        this.stylexml = stylexml;
        this.progtype = progtype;
    }

   
    // Property accessors

    public Long getStyleid() {
        return this.styleid;
    }
    
    public void setStyleid(Long styleid) {
        this.styleid = styleid;
    }

    public String getStylename() {
        return this.stylename;
    }
    
    public void setStylename(String stylename) {
        this.stylename = stylename;
    }

    public Date getSytlecreatedate() {
        return this.sytlecreatedate;
    }
    
    public void setSytlecreatedate(Date sytlecreatedate) {
        this.sytlecreatedate = sytlecreatedate;
    }

    public String getOperatorid() {
        return this.operatorid;
    }
    
    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public Long getBcstatus() {
        return this.bcstatus;
    }
    
    public void setBcstatus(Long bcstatus) {
        this.bcstatus = bcstatus;
    }

    public String getStylexml() {
        return this.stylexml;
    }
    
    public void setStylexml(String stylexml) {
        this.stylexml = stylexml;
    }

    public String getProgtype() {
        return this.progtype;
    }
    
    public void setProgtype(String progtype) {
        this.progtype = progtype;
    }
   








}