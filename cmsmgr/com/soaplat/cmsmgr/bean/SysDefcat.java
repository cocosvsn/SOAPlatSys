package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * SysDefcat entity. @author MyEclipse Persistence Tools
 */

public class SysDefcat  implements java.io.Serializable {


    // Fields    

     private String defcatid;
     private String catglobalid;
     private String defcatname;
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
    public SysDefcat() {
    }

	/** minimal constructor */
    public SysDefcat(String defcatid) {
        this.defcatid = defcatid;
    }
    
    /** full constructor */
    public SysDefcat(String defcatid, String catglobalid, String defcatname, String defcatcode, String isleaf, Long defcatlevel, String rootid, String parentsid, Long displayorder, String defcatseq, String inputmanid, Date inputtime, String remark) {
        this.defcatid = defcatid;
        this.catglobalid = catglobalid;
        this.defcatname = defcatname;
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

    public String getDefcatid() {
        return this.defcatid;
    }
    
    public void setDefcatid(String defcatid) {
        this.defcatid = defcatid;
    }

    public String getCatglobalid() {
        return this.catglobalid;
    }
    
    public void setCatglobalid(String catglobalid) {
        this.catglobalid = catglobalid;
    }

    public String getDefcatname() {
        return this.defcatname;
    }
    
    public void setDefcatname(String defcatname) {
        this.defcatname = defcatname;
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