package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * PortalJsRules entity. @author MyEclipse Persistence Tools
 */

public class PortalJsRules  implements java.io.Serializable {


    // Fields    

     private String stylerelid;
     private Long bstyleid;
     private String defcatcode;
     private Long ismetdata;
     private String jsheadtag;
     private String atag;
     private String btag;
     private String attribute;
     private Long datatype;
     private String filter;
     private String filtervalue;
     private String itemheadtag;
     private Long sequence;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public PortalJsRules() {
    }

	/** minimal constructor */
    public PortalJsRules(String stylerelid) {
        this.stylerelid = stylerelid;
    }
    
    /** full constructor */
    public PortalJsRules(String stylerelid, Long bstyleid, String defcatcode, Long ismetdata, String jsheadtag, String atag, String btag, String attribute, Long datatype, String filter, String filtervalue, String itemheadtag, Long sequence, String inputmanid, Date inputtime, String remark) {
        this.stylerelid = stylerelid;
        this.bstyleid = bstyleid;
        this.defcatcode = defcatcode;
        this.ismetdata = ismetdata;
        this.jsheadtag = jsheadtag;
        this.atag = atag;
        this.btag = btag;
        this.attribute = attribute;
        this.datatype = datatype;
        this.filter = filter;
        this.filtervalue = filtervalue;
        this.itemheadtag = itemheadtag;
        this.sequence = sequence;
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

    public Long getBstyleid() {
        return this.bstyleid;
    }
    
    public void setBstyleid(Long bstyleid) {
        this.bstyleid = bstyleid;
    }

    public String getDefcatcode() {
        return this.defcatcode;
    }
    
    public void setDefcatcode(String defcatcode) {
        this.defcatcode = defcatcode;
    }

    public Long getIsmetdata() {
        return this.ismetdata;
    }
    
    public void setIsmetdata(Long ismetdata) {
        this.ismetdata = ismetdata;
    }

    public String getJsheadtag() {
        return this.jsheadtag;
    }
    
    public void setJsheadtag(String jsheadtag) {
        this.jsheadtag = jsheadtag;
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

    public String getAttribute() {
        return this.attribute;
    }
    
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Long getDatatype() {
        return this.datatype;
    }
    
    public void setDatatype(Long datatype) {
        this.datatype = datatype;
    }

    public String getFilter() {
        return this.filter;
    }
    
    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFiltervalue() {
        return this.filtervalue;
    }
    
    public void setFiltervalue(String filtervalue) {
        this.filtervalue = filtervalue;
    }

    public String getItemheadtag() {
        return this.itemheadtag;
    }
    
    public void setItemheadtag(String itemheadtag) {
        this.itemheadtag = itemheadtag;
    }

    public Long getSequence() {
        return this.sequence;
    }
    
    public void setSequence(Long sequence) {
        this.sequence = sequence;
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