package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgCopyright entity. @author MyEclipse Persistence Tools
 */

public class ProgCopyright  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String programid;
     private String accname;
     private String accmode;
     private Date accstarttime;
     private Long acctimelimited;
     private Date accendtime;
     private String acczone;
     private Long acccount;
     private String otherinfo;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public ProgCopyright() {
    }

	/** minimal constructor */
    public ProgCopyright(String relid, String programid) {
        this.relid = relid;
        this.programid = programid;
    }
    
    /** full constructor */
    public ProgCopyright(String relid, String programid, String accname, String accmode, Date accstarttime, Long acctimelimited, Date accendtime, String acczone, Long acccount, String otherinfo, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.programid = programid;
        this.accname = accname;
        this.accmode = accmode;
        this.accstarttime = accstarttime;
        this.acctimelimited = acctimelimited;
        this.accendtime = accendtime;
        this.acczone = acczone;
        this.acccount = acccount;
        this.otherinfo = otherinfo;
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

    public String getProgramid() {
        return this.programid;
    }
    
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getAccname() {
        return this.accname;
    }
    
    public void setAccname(String accname) {
        this.accname = accname;
    }

    public String getAccmode() {
        return this.accmode;
    }
    
    public void setAccmode(String accmode) {
        this.accmode = accmode;
    }

    public Date getAccstarttime() {
        return this.accstarttime;
    }
    
    public void setAccstarttime(Date accstarttime) {
        this.accstarttime = accstarttime;
    }

    public Long getAcctimelimited() {
        return this.acctimelimited;
    }
    
    public void setAcctimelimited(Long acctimelimited) {
        this.acctimelimited = acctimelimited;
    }

    public Date getAccendtime() {
        return this.accendtime;
    }
    
    public void setAccendtime(Date accendtime) {
        this.accendtime = accendtime;
    }

    public String getAcczone() {
        return this.acczone;
    }
    
    public void setAcczone(String acczone) {
        this.acczone = acczone;
    }

    public Long getAcccount() {
        return this.acccount;
    }
    
    public void setAcccount(Long acccount) {
        this.acccount = acccount;
    }

    public String getOtherinfo() {
        return this.otherinfo;
    }
    
    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
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