package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * WebSVodSRel entity. @author MyEclipse Persistence Tools
 */

public class WebSVodSRel  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String websid;
     private String vodsid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public WebSVodSRel() {
    }

	/** minimal constructor */
    public WebSVodSRel(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public WebSVodSRel(String relid, String websid, String vodsid, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.websid = websid;
        this.vodsid = vodsid;
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

    public String getWebsid() {
        return this.websid;
    }
    
    public void setWebsid(String websid) {
        this.websid = websid;
    }

    public String getVodsid() {
        return this.vodsid;
    }
    
    public void setVodsid(String vodsid) {
        this.vodsid = vodsid;
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