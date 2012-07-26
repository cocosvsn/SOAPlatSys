package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * DictType entity. @author MyEclipse Persistence Tools
 */

public class DictType  implements java.io.Serializable {


    // Fields    

     private String dtglobalid;
     private String dictid;
     private String dicttypename;
     private String parentid;
     private Long rank;
     private Long diclevel;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;
     private String seqno;


    // Constructors

    /** default constructor */
    public DictType() {
    }

	/** minimal constructor */
    public DictType(String dtglobalid, String dictid) {
        this.dtglobalid = dtglobalid;
        this.dictid = dictid;
    }
    
    /** full constructor */
    public DictType(String dtglobalid, String dictid, String dicttypename, String parentid, Long rank, Long diclevel, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark, String seqno) {
        this.dtglobalid = dtglobalid;
        this.dictid = dictid;
        this.dicttypename = dicttypename;
        this.parentid = parentid;
        this.rank = rank;
        this.diclevel = diclevel;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
        this.seqno = seqno;
    }

   
    // Property accessors

    public String getDtglobalid() {
        return this.dtglobalid;
    }
    
    public void setDtglobalid(String dtglobalid) {
        this.dtglobalid = dtglobalid;
    }

    public String getDictid() {
        return this.dictid;
    }
    
    public void setDictid(String dictid) {
        this.dictid = dictid;
    }

    public String getDicttypename() {
        return this.dicttypename;
    }
    
    public void setDicttypename(String dicttypename) {
        this.dicttypename = dicttypename;
    }

    public String getParentid() {
        return this.parentid;
    }
    
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Long getRank() {
        return this.rank;
    }
    
    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Long getDiclevel() {
        return this.diclevel;
    }
    
    public void setDiclevel(Long diclevel) {
        this.diclevel = diclevel;
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

    public String getSeqno() {
        return this.seqno;
    }
    
    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }
   








}