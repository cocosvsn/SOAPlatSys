package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * CmsService entity. @author MyEclipse Persistence Tools
 */

public class CmsService  implements java.io.Serializable {


    // Fields    

     private String srvid;
     private String srvname;
     private String defcatcode;
     private String isleaf;
     private Long defcatlevel;
     private String rootid;
     private String parentsid;
     private Long displayorder;
     private String defcatseq;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public CmsService() {
    }

	/** minimal constructor */
    public CmsService(String srvid, String srvname) {
        this.srvid = srvid;
        this.srvname = srvname;
    }
    
    /** full constructor */
    public CmsService(String srvid, String srvname, String defcatcode, String isleaf, Long defcatlevel, String rootid, String parentsid, Long displayorder, String defcatseq, String inputmanid, Date inputtime, String remark) {
        this.srvid = srvid;
        this.srvname = srvname;
        this.defcatcode = defcatcode;
        this.isleaf = isleaf;
        this.defcatlevel = defcatlevel;
        this.rootid = rootid;
        this.parentsid = parentsid;
        this.displayorder = displayorder;
        this.defcatseq = defcatseq;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getSrvid() {
        return this.srvid;
    }
    
    public void setSrvid(String srvid) {
        this.srvid = srvid;
    }

    public String getSrvname() {
        return this.srvname;
    }
    
    public void setSrvname(String srvname) {
        this.srvname = srvname;
    }

    public String getDefcatcode() {
        return this.defcatcode;
    }
    
    public void setDefcatcode(String defcatcode) {
        this.defcatcode = defcatcode;
    }

    public String getIsleaf() {
        return this.isleaf;
    }
    
    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

    public Long getDefcatlevel() {
        return this.defcatlevel;
    }
    
    public void setDefcatlevel(Long defcatlevel) {
        this.defcatlevel = defcatlevel;
    }

    public String getRootid() {
        return this.rootid;
    }
    
    public void setRootid(String rootid) {
        this.rootid = rootid;
    }

    public String getParentsid() {
        return this.parentsid;
    }
    
    public void setParentsid(String parentsid) {
        this.parentsid = parentsid;
    }

    public Long getDisplayorder() {
        return this.displayorder;
    }
    
    public void setDisplayorder(Long displayorder) {
        this.displayorder = displayorder;
    }

    public String getDefcatseq() {
        return this.defcatseq;
    }
    
    public void setDefcatseq(String defcatseq) {
        this.defcatseq = defcatseq;
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