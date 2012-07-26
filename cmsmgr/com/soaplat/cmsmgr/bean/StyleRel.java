package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * StyleRel entity. @author MyEclipse Persistence Tools
 */

public class StyleRel  implements java.io.Serializable {


    // Fields    

     private String stylerelid;
     private Long astyleid;
     private Long bstyleid;
     private Long ismetdata;
     private String atag;
     private String btag;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public StyleRel() {
    }

	/** minimal constructor */
    public StyleRel(String stylerelid) {
        this.stylerelid = stylerelid;
    }
    
    /** full constructor */
    public StyleRel(String stylerelid, Long astyleid, Long bstyleid, Long ismetdata, String atag, String btag, String inputmanid, Date inputtime, String remark) {
        this.stylerelid = stylerelid;
        this.astyleid = astyleid;
        this.bstyleid = bstyleid;
        this.ismetdata = ismetdata;
        this.atag = atag;
        this.btag = btag;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getStylerelid() {
        return this.stylerelid;
    }
    
    public void setStylerelid(String stylerelid) {
        this.stylerelid = stylerelid;
    }

    public Long getAstyleid() {
        return this.astyleid;
    }
    
    public void setAstyleid(Long astyleid) {
        this.astyleid = astyleid;
    }

    public Long getBstyleid() {
        return this.bstyleid;
    }
    
    public void setBstyleid(Long bstyleid) {
        this.bstyleid = bstyleid;
    }

    public Long getIsmetdata() {
        return this.ismetdata;
    }
    
    public void setIsmetdata(Long ismetdata) {
        this.ismetdata = ismetdata;
    }

    public String getAtag() {
        return this.atag;
    }
    
    public void setAtag(String atag) {
        this.atag = atag;
    }

    public String getBtag() {
        return this.btag;
    }
    
    public void setBtag(String btag) {
        this.btag = btag;
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