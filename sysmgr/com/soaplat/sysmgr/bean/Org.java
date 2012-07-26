package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * Org entity. @author MyEclipse Persistence Tools
 */

public class Org  implements java.io.Serializable {


    // Fields    

     private String orgid;
     private String orgname;
     private String orgcode;
     private String isleaf;
     private Long orglevel;
     private String rootid;
     private String parentsid;
     private String orgseq;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public Org() {
    }

	/** minimal constructor */
    public Org(String orgid) {
        this.orgid = orgid;
    }
    
    /** full constructor */
    public Org(String orgid, String orgname, String orgcode, String isleaf, Long orglevel, String rootid, String parentsid, String orgseq, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.orgid = orgid;
        this.orgname = orgname;
        this.orgcode = orgcode;
        this.isleaf = isleaf;
        this.orglevel = orglevel;
        this.rootid = rootid;
        this.parentsid = parentsid;
        this.orgseq = orgseq;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getOrgid() {
        return this.orgid;
    }
    
    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgname() {
        return this.orgname;
    }
    
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOrgcode() {
        return this.orgcode;
    }
    
    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getIsleaf() {
        return this.isleaf;
    }
    
    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

    public Long getOrglevel() {
        return this.orglevel;
    }
    
    public void setOrglevel(Long orglevel) {
        this.orglevel = orglevel;
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

    public String getOrgseq() {
        return this.orgseq;
    }
    
    public void setOrgseq(String orgseq) {
        this.orgseq = orgseq;
    }

    public String getDefaulticonpath() {
        return this.defaulticonpath;
    }
    
    public void setDefaulticonpath(String defaulticonpath) {
        this.defaulticonpath = defaulticonpath;
    }

    public String getActiveiconpath() {
        return this.activeiconpath;
    }
    
    public void setActiveiconpath(String activeiconpath) {
        this.activeiconpath = activeiconpath;
    }

    public String getInactiveiconpath() {
        return this.inactiveiconpath;
    }
    
    public void setInactiveiconpath(String inactiveiconpath) {
        this.inactiveiconpath = inactiveiconpath;
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