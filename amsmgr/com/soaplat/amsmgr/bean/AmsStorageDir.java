package com.soaplat.amsmgr.bean;
// default package

import java.util.Date;


/**
 * 存储体目录表
 */

public class AmsStorageDir  implements java.io.Serializable {


    // Fields    

     private String stdirglobalid;			// 存储体目录GLOBALID
     private String stglobalid;				// 存储体GLOBALID
     private String storagedirname;			// 存储体目录名称
     private String storagedirdesc;			// 存储体目录描述
     private String dirtotalcap;			// 总容量(M)
     private String diralarmcap;			// 报警容量（M）
     private String dirfreecap;				// 剩余容量（M）
     private String dirvalid;				// 是否可用
     private String defaulticonpath;		// 默认状态图标路径
     private String activeiconpath;			// 激活状态图标路径
     private String inactiveiconpath;		// 未激活状态图标路径
     private String inputmanid;				// 信息录入人
     private Date inputtime;				// 信息录入时间
     private String remark;					// 备注
     private String filecode;				// *文件代码


    // Constructors

    /** default constructor */
    public AmsStorageDir() {
    }

	/** minimal constructor */
    public AmsStorageDir(String stdirglobalid, String storagedirname) {
        this.stdirglobalid = stdirglobalid;
        this.storagedirname = storagedirname;
    }
    
    /** full constructor */
    public AmsStorageDir(String stdirglobalid, String stglobalid, String storagedirname, String storagedirdesc, String dirtotalcap, String diralarmcap, String dirfreecap, String dirvalid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark, String filecode) {
        this.stdirglobalid = stdirglobalid;
        this.stglobalid = stglobalid;
        this.storagedirname = storagedirname;
        this.storagedirdesc = storagedirdesc;
        this.dirtotalcap = dirtotalcap;
        this.diralarmcap = diralarmcap;
        this.dirfreecap = dirfreecap;
        this.dirvalid = dirvalid;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
        this.filecode = filecode;
    }

   
    // Property accessors

    public String getStdirglobalid() {
        return this.stdirglobalid;
    }
    
    public void setStdirglobalid(String stdirglobalid) {
        this.stdirglobalid = stdirglobalid;
    }

    public String getStglobalid() {
        return this.stglobalid;
    }
    
    public void setStglobalid(String stglobalid) {
        this.stglobalid = stglobalid;
    }

    public String getStoragedirname() {
        return this.storagedirname;
    }
    
    public void setStoragedirname(String storagedirname) {
        this.storagedirname = storagedirname;
    }

    public String getStoragedirdesc() {
        return this.storagedirdesc;
    }
    
    public void setStoragedirdesc(String storagedirdesc) {
        this.storagedirdesc = storagedirdesc;
    }

    public String getDirtotalcap() {
        return this.dirtotalcap;
    }
    
    public void setDirtotalcap(String dirtotalcap) {
        this.dirtotalcap = dirtotalcap;
    }

    public String getDiralarmcap() {
        return this.diralarmcap;
    }
    
    public void setDiralarmcap(String diralarmcap) {
        this.diralarmcap = diralarmcap;
    }

    public String getDirfreecap() {
        return this.dirfreecap;
    }
    
    public void setDirfreecap(String dirfreecap) {
        this.dirfreecap = dirfreecap;
    }

    public String getDirvalid() {
        return this.dirvalid;
    }
    
    public void setDirvalid(String dirvalid) {
        this.dirvalid = dirvalid;
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

    public String getFilecode() {
        return this.filecode;
    }
    
    public void setFilecode(String filecode) {
        this.filecode = filecode;
    }
   








}