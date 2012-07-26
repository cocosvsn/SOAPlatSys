package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * PortalPackage entity. @author MyEclipse Persistence Tools
 */

public class PortalPackage  implements java.io.Serializable {
    // Fields    
     private String ptpid;				// 主键ID
     private String ptpname;			// 页面包装名称
     private String ptpxml;				// -- 暂未使用
     private String columnclassid;		// 栏目 (PortalColumn) ID
     private Long epicodecount;			// 总集数
     private String productname;		// -- 暂未使用
     private String description;		// -- 暂未使用
     private String category;			// -- 暂未使用
     private String titlebrief;			// -- 暂未使用
     private String epicodename;		// -- 暂未使用
     private String progtype;			// -- 暂未使用
     private String subtitlelanguage;	// -- 暂未使用
     private String audiolanguage;		// -- 暂未使用
     private String director;			// -- 暂未使用
     private String actors;				// -- 暂未使用
     private String shootdate;			// -- 暂未使用
     private String issuedate;			// -- 暂未使用
     private String countrydist;		// -- 暂未使用
     private String inputmanid;			// 录入人员ID
     private Date inputtime;			// 创建日期
     private String remark;				// 备注
     private Long onlinetag;			// 上线标识 (1: 上线, 0: 未上线)


    // Constructors

    public Long getOnlinetag() {
		return onlinetag;
	}

	public void setOnlinetag(Long onlinetag) {
		this.onlinetag = onlinetag;
	}

	/** default constructor */
    public PortalPackage() {
    }

	/** minimal constructor */
    public PortalPackage(String ptpid) {
        this.ptpid = ptpid;
    }
    
    /** full constructor */
    public PortalPackage(String ptpid, String ptpname, String ptpxml, String columnclassid, Long epicodecount, String productname, String description, String category, String titlebrief, String epicodename, String progtype, String subtitlelanguage, String audiolanguage, String director, String actors, String shootdate, String issuedate, String countrydist, String inputmanid, Date inputtime, String remark) {
        this.ptpid = ptpid;
        this.ptpname = ptpname;
        this.ptpxml = ptpxml;
        this.columnclassid = columnclassid;
        this.epicodecount = epicodecount;
        this.productname = productname;
        this.description = description;
        this.category = category;
        this.titlebrief = titlebrief;
        this.epicodename = epicodename;
        this.progtype = progtype;
        this.subtitlelanguage = subtitlelanguage;
        this.audiolanguage = audiolanguage;
        this.director = director;
        this.actors = actors;
        this.shootdate = shootdate;
        this.issuedate = issuedate;
        this.countrydist = countrydist;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getPtpid() {
        return this.ptpid;
    }
    
    public void setPtpid(String ptpid) {
        this.ptpid = ptpid;
    }

    public String getPtpname() {
        return this.ptpname;
    }
    
    public void setPtpname(String ptpname) {
        this.ptpname = ptpname;
    }

    public String getPtpxml() {
        return this.ptpxml;
    }
    
    public void setPtpxml(String ptpxml) {
        this.ptpxml = ptpxml;
    }

    public String getColumnclassid() {
        return this.columnclassid;
    }
    
    public void setColumnclassid(String columnclassid) {
        this.columnclassid = columnclassid;
    }

    public Long getEpicodecount() {
        return this.epicodecount;
    }
    
    public void setEpicodecount(Long epicodecount) {
        this.epicodecount = epicodecount;
    }

    public String getProductname() {
        return this.productname;
    }
    
    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitlebrief() {
        return this.titlebrief;
    }
    
    public void setTitlebrief(String titlebrief) {
        this.titlebrief = titlebrief;
    }

    public String getEpicodename() {
        return this.epicodename;
    }
    
    public void setEpicodename(String epicodename) {
        this.epicodename = epicodename;
    }

    public String getProgtype() {
        return this.progtype;
    }
    
    public void setProgtype(String progtype) {
        this.progtype = progtype;
    }

    public String getSubtitlelanguage() {
        return this.subtitlelanguage;
    }
    
    public void setSubtitlelanguage(String subtitlelanguage) {
        this.subtitlelanguage = subtitlelanguage;
    }

    public String getAudiolanguage() {
        return this.audiolanguage;
    }
    
    public void setAudiolanguage(String audiolanguage) {
        this.audiolanguage = audiolanguage;
    }

    public String getDirector() {
        return this.director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return this.actors;
    }
    
    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getShootdate() {
        return this.shootdate;
    }
    
    public void setShootdate(String shootdate) {
        this.shootdate = shootdate;
    }

    public String getIssuedate() {
        return this.issuedate;
    }
    
    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getCountrydist() {
        return this.countrydist;
    }
    
    public void setCountrydist(String countrydist) {
        this.countrydist = countrydist;
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