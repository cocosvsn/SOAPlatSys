package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * FunResource entity. @author MyEclipse Persistence Tools
 */

public class FunResource  implements java.io.Serializable {


    // Fields    

     private String funcid;
     private String funcname;
     private String funcdesc;
     private String funcaction;
     private String parainfo;
     private String ischeck;
     private String functype;
     private String restype;
     private String respath;
     private String compackname;
     private String resname;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public FunResource() {
    }

	/** minimal constructor */
    public FunResource(String funcid) {
        this.funcid = funcid;
    }
    
    /** full constructor */
    public FunResource(String funcid, String funcname, String funcdesc, String funcaction, String parainfo, String ischeck, String functype, String restype, String respath, String compackname, String resname, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.funcid = funcid;
        this.funcname = funcname;
        this.funcdesc = funcdesc;
        this.funcaction = funcaction;
        this.parainfo = parainfo;
        this.ischeck = ischeck;
        this.functype = functype;
        this.restype = restype;
        this.respath = respath;
        this.compackname = compackname;
        this.resname = resname;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getFuncid() {
        return this.funcid;
    }
    
    public void setFuncid(String funcid) {
        this.funcid = funcid;
    }

    public String getFuncname() {
        return this.funcname;
    }
    
    public void setFuncname(String funcname) {
        this.funcname = funcname;
    }

    public String getFuncdesc() {
        return this.funcdesc;
    }
    
    public void setFuncdesc(String funcdesc) {
        this.funcdesc = funcdesc;
    }

    public String getFuncaction() {
        return this.funcaction;
    }
    
    public void setFuncaction(String funcaction) {
        this.funcaction = funcaction;
    }

    public String getParainfo() {
        return this.parainfo;
    }
    
    public void setParainfo(String parainfo) {
        this.parainfo = parainfo;
    }

    public String getIscheck() {
        return this.ischeck;
    }
    
    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getFunctype() {
        return this.functype;
    }
    
    public void setFunctype(String functype) {
        this.functype = functype;
    }

    public String getRestype() {
        return this.restype;
    }
    
    public void setRestype(String restype) {
        this.restype = restype;
    }

    public String getRespath() {
        return this.respath;
    }
    
    public void setRespath(String respath) {
        this.respath = respath;
    }

    public String getCompackname() {
        return this.compackname;
    }
    
    public void setCompackname(String compackname) {
        this.compackname = compackname;
    }

    public String getResname() {
        return this.resname;
    }
    
    public void setResname(String resname) {
        this.resname = resname;
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