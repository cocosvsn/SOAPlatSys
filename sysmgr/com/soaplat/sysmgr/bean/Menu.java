package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * Menu entity. @author MyEclipse Persistence Tools
 */

public class Menu  implements java.io.Serializable {


    // Fields    

     private String menuid;
     private String menuname;
     private String menulabel;
     private String menucode;
     private String isleaf;
     private String menuaction;
     private String parameter;
     private String uientry;
     private Long menulevel;
     private String rootid;
     private String parentsid;
     private Long displayorder;
     private String imagepath;
     private String expandpath;
     private String menuseq;
     private String openmode;
     private Long subcount;
     private String funcid;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public Menu() {
    }

	/** minimal constructor */
    public Menu(String menuid) {
        this.menuid = menuid;
    }
    
    /** full constructor */
    public Menu(String menuid, String menuname, String menulabel, String menucode, String isleaf, String menuaction, String parameter, String uientry, Long menulevel, String rootid, String parentsid, Long displayorder, String imagepath, String expandpath, String menuseq, String openmode, Long subcount, String funcid, String inputmanid, Date inputtime, String remark) {
        this.menuid = menuid;
        this.menuname = menuname;
        this.menulabel = menulabel;
        this.menucode = menucode;
        this.isleaf = isleaf;
        this.menuaction = menuaction;
        this.parameter = parameter;
        this.uientry = uientry;
        this.menulevel = menulevel;
        this.rootid = rootid;
        this.parentsid = parentsid;
        this.displayorder = displayorder;
        this.imagepath = imagepath;
        this.expandpath = expandpath;
        this.menuseq = menuseq;
        this.openmode = openmode;
        this.subcount = subcount;
        this.funcid = funcid;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getMenuid() {
        return this.menuid;
    }
    
    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMenuname() {
        return this.menuname;
    }
    
    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getMenulabel() {
        return this.menulabel;
    }
    
    public void setMenulabel(String menulabel) {
        this.menulabel = menulabel;
    }

    public String getMenucode() {
        return this.menucode;
    }
    
    public void setMenucode(String menucode) {
        this.menucode = menucode;
    }

    public String getIsleaf() {
        return this.isleaf;
    }
    
    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

    public String getMenuaction() {
        return this.menuaction;
    }
    
    public void setMenuaction(String menuaction) {
        this.menuaction = menuaction;
    }

    public String getParameter() {
        return this.parameter;
    }
    
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getUientry() {
        return this.uientry;
    }
    
    public void setUientry(String uientry) {
        this.uientry = uientry;
    }

    public Long getMenulevel() {
        return this.menulevel;
    }
    
    public void setMenulevel(Long menulevel) {
        this.menulevel = menulevel;
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

    public String getImagepath() {
        return this.imagepath;
    }
    
    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getExpandpath() {
        return this.expandpath;
    }
    
    public void setExpandpath(String expandpath) {
        this.expandpath = expandpath;
    }

    public String getMenuseq() {
        return this.menuseq;
    }
    
    public void setMenuseq(String menuseq) {
        this.menuseq = menuseq;
    }

    public String getOpenmode() {
        return this.openmode;
    }
    
    public void setOpenmode(String openmode) {
        this.openmode = openmode;
    }

    public Long getSubcount() {
        return this.subcount;
    }
    
    public void setSubcount(Long subcount) {
        this.subcount = subcount;
    }

    public String getFuncid() {
        return this.funcid;
    }
    
    public void setFuncid(String funcid) {
        this.funcid = funcid;
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