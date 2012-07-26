package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * PortalMod entity. @author MyEclipse Persistence Tools
 */

public class PortalMod  implements java.io.Serializable {


    // Fields    

     private String modeid;
     private String idFiletype;
     private String modename;
     private String modecontent;
     private Long isused;
     private Date usedate;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public PortalMod() {
    }

	/** minimal constructor */
    public PortalMod(String modeid) {
        this.modeid = modeid;
    }
    
    /** full constructor */
    public PortalMod(String modeid, String idFiletype, String modename, String modecontent, Long isused, Date usedate, String inputmanid, Date inputtime, String remark) {
        this.modeid = modeid;
        this.idFiletype = idFiletype;
        this.modename = modename;
        this.modecontent = modecontent;
        this.isused = isused;
        this.usedate = usedate;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getModeid() {
        return this.modeid;
    }
    
    public void setModeid(String modeid) {
        this.modeid = modeid;
    }

    public String getIdFiletype() {
        return this.idFiletype;
    }
    
    public void setIdFiletype(String idFiletype) {
        this.idFiletype = idFiletype;
    }

    public String getModename() {
        return this.modename;
    }
    
    public void setModename(String modename) {
        this.modename = modename;
    }

    public String getModecontent() {
        return this.modecontent;
    }
    
    public void setModecontent(String modecontent) {
        this.modecontent = modecontent;
    }

    public Long getIsused() {
        return this.isused;
    }
    
    public void setIsused(Long isused) {
        this.isused = isused;
    }

    public Date getUsedate() {
        return this.usedate;
    }
    
    public void setUsedate(Date usedate) {
        this.usedate = usedate;
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