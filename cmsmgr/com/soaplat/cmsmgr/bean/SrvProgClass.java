package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * SrvProgClass entity. @author MyEclipse Persistence Tools
 */

public class SrvProgClass  implements java.io.Serializable {


    // Fields    

     private String srvprogid;
     private String srvid;
     private Long selecttag;
     private String inputmanid;
     private Date inputtime;
     private String remark;
     private String genre;


    // Constructors

    /** default constructor */
    public SrvProgClass() {
    }

	/** minimal constructor */
    public SrvProgClass(String srvprogid, String srvid) {
        this.srvprogid = srvprogid;
        this.srvid = srvid;
    }
    
    /** full constructor */
    public SrvProgClass(String srvprogid, String classId, String srvid, Long selecttag, String inputmanid, Date inputtime, String remark) {
        this.srvprogid = srvprogid;
        this.srvid = srvid;
        this.selecttag = selecttag;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getSrvprogid() {
        return this.srvprogid;
    }
    
    public void setSrvprogid(String srvprogid) {
        this.srvprogid = srvprogid;
    }

    public String getCatid() {
        return this.srvid;
    }
    
    public void setCatid(String srvid) {
        this.srvid = srvid;
    }

    public Long getDefault_() {
        return this.selecttag;
    }
    
    public void setDefault_(Long selecttag) {
        this.selecttag = selecttag;
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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getSrvid() {
		return srvid;
	}

	public void setSrvid(String srvid) {
		this.srvid = srvid;
	}

	public Long getSelecttag() {
		return selecttag;
	}

	public void setSelecttag(Long selecttag) {
		this.selecttag = selecttag;
	}
   








}