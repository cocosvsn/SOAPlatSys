package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * Dict entity. @author MyEclipse Persistence Tools
 */

public class Dict  implements java.io.Serializable {


    // Fields    

     private String dicglobalid;
     private String dictcode;
     private String dtglobalid;
     private String dictid;
     private String dictname;
     private Long status;
     private Long sortno;
     private Long rank;
     private String seqno;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public Dict() {
    }

	/** minimal constructor */
    public Dict(String dicglobalid, String dictcode, String dtglobalid, String dictid) {
        this.dicglobalid = dicglobalid;
        this.dictcode = dictcode;
        this.dtglobalid = dtglobalid;
        this.dictid = dictid;
    }
    
    /** full constructor */
    public Dict(String dicglobalid, String dictcode, String dtglobalid, String dictid, String dictname, Long status, Long sortno, Long rank, String seqno, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.dicglobalid = dicglobalid;
        this.dictcode = dictcode;
        this.dtglobalid = dtglobalid;
        this.dictid = dictid;
        this.dictname = dictname;
        this.status = status;
        this.sortno = sortno;
        this.rank = rank;
        this.seqno = seqno;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getDicglobalid() {
        return this.dicglobalid;
    }
    
    public void setDicglobalid(String dicglobalid) {
        this.dicglobalid = dicglobalid;
    }

    public String getDictcode() {
        return this.dictcode;
    }
    
    public void setDictcode(String dictcode) {
        this.dictcode = dictcode;
    }

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

    public String getDictname() {
        return this.dictname;
    }
    
    public void setDictname(String dictname) {
        this.dictname = dictname;
    }

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getSortno() {
        return this.sortno;
    }
    
    public void setSortno(Long sortno) {
        this.sortno = sortno;
    }

    public Long getRank() {
        return this.rank;
    }
    
    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getSeqno() {
        return this.seqno;
    }
    
    public void setSeqno(String seqno) {
        this.seqno = seqno;
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