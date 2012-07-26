package com.soaplat.amsmgr.bean;
// default package

import java.util.Date;


/**
 * Title 		:the Class AmsFileServer.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */

public class AmsFileServer  implements java.io.Serializable {


    // Fields    

     /** The filesgloabalid. */
    private String filesgloabalid;
     
     /** The filesname. */
     private String filesname;
     
     /** The filesaddr. */
     private String filesaddr;
     
     /** The filestype. */
     private String filestype;
     
     /** The filesstatus. */
     private Long filesstatus;
     
     /** The filesgid. */
     private String filesgid;
     
     /** The filesvalid. */
     private String filesvalid;
     
     /** The defaulticonpath. */
     private String defaulticonpath;
     
     /** The activeiconpath. */
     private String activeiconpath;
     
     /** The inactiveiconpath. */
     private String inactiveiconpath;
     
     /** The inputmanid. */
     private String inputmanid;
     
     /** The inputtime. */
     private Date inputtime;
     
     /** The remark. */
     private String remark;


    // Constructors

    /**
     * Instantiates a new ams file server.
     */
    public AmsFileServer() {
    }

	/**
	 * Instantiates a new ams file server.
	 * 
	 * @param filesgloabalid the filesgloabalid
	 * @param filesname the filesname
	 * @param filesaddr the filesaddr
	 */
    public AmsFileServer(String filesgloabalid, String filesname, String filesaddr) {
        this.filesgloabalid = filesgloabalid;
        this.filesname = filesname;
        this.filesaddr = filesaddr;
    }
    
    /**
     * Instantiates a new ams file server.
     * 
     * @param filesgloabalid the filesgloabalid
     * @param filesname the filesname
     * @param filesaddr the filesaddr
     * @param filestype the filestype
     * @param filesstatus the filesstatus
     * @param filesgid the filesgid
     * @param filesvalid the filesvalid
     * @param defaulticonpath the defaulticonpath
     * @param activeiconpath the activeiconpath
     * @param inactiveiconpath the inactiveiconpath
     * @param inputmanid the inputmanid
     * @param inputtime the inputtime
     * @param remark the remark
     */
    public AmsFileServer(String filesgloabalid, String filesname, String filesaddr, String filestype, Long filesstatus, String filesgid, String filesvalid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.filesgloabalid = filesgloabalid;
        this.filesname = filesname;
        this.filesaddr = filesaddr;
        this.filestype = filestype;
        this.filesstatus = filesstatus;
        this.filesgid = filesgid;
        this.filesvalid = filesvalid;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    /**
     * Gets the filesgloabalid.
     * 
     * @return the filesgloabalid
     */
    public String getFilesgloabalid() {
        return this.filesgloabalid;
    }
    
    /**
     * Sets the filesgloabalid.
     * 
     * @param filesgloabalid the new filesgloabalid
     */
    public void setFilesgloabalid(String filesgloabalid) {
        this.filesgloabalid = filesgloabalid;
    }

    /**
     * Gets the filesname.
     * 
     * @return the filesname
     */
    public String getFilesname() {
        return this.filesname;
    }
    
    /**
     * Sets the filesname.
     * 
     * @param filesname the new filesname
     */
    public void setFilesname(String filesname) {
        this.filesname = filesname;
    }

    /**
     * Gets the filesaddr.
     * 
     * @return the filesaddr
     */
    public String getFilesaddr() {
        return this.filesaddr;
    }
    
    /**
     * Sets the filesaddr.
     * 
     * @param filesaddr the new filesaddr
     */
    public void setFilesaddr(String filesaddr) {
        this.filesaddr = filesaddr;
    }

    /**
     * Gets the filestype.
     * 
     * @return the filestype
     */
    public String getFilestype() {
        return this.filestype;
    }
    
    /**
     * Sets the filestype.
     * 
     * @param filestype the new filestype
     */
    public void setFilestype(String filestype) {
        this.filestype = filestype;
    }

    /**
     * Gets the filesstatus.
     * 
     * @return the filesstatus
     */
    public Long getFilesstatus() {
        return this.filesstatus;
    }
    
    /**
     * Sets the filesstatus.
     * 
     * @param filesstatus the new filesstatus
     */
    public void setFilesstatus(Long filesstatus) {
        this.filesstatus = filesstatus;
    }

    /**
     * Gets the filesgid.
     * 
     * @return the filesgid
     */
    public String getFilesgid() {
        return this.filesgid;
    }
    
    /**
     * Sets the filesgid.
     * 
     * @param filesgid the new filesgid
     */
    public void setFilesgid(String filesgid) {
        this.filesgid = filesgid;
    }

    /**
     * Gets the filesvalid.
     * 
     * @return the filesvalid
     */
    public String getFilesvalid() {
        return this.filesvalid;
    }
    
    /**
     * Sets the filesvalid.
     * 
     * @param filesvalid the new filesvalid
     */
    public void setFilesvalid(String filesvalid) {
        this.filesvalid = filesvalid;
    }

    /**
     * Gets the defaulticonpath.
     * 
     * @return the defaulticonpath
     */
    public String getDefaulticonpath() {
        return this.defaulticonpath;
    }
    
    /**
     * Sets the defaulticonpath.
     * 
     * @param defaulticonpath the new defaulticonpath
     */
    public void setDefaulticonpath(String defaulticonpath) {
        this.defaulticonpath = defaulticonpath;
    }

    /**
     * Gets the activeiconpath.
     * 
     * @return the activeiconpath
     */
    public String getActiveiconpath() {
        return this.activeiconpath;
    }
    
    /**
     * Sets the activeiconpath.
     * 
     * @param activeiconpath the new activeiconpath
     */
    public void setActiveiconpath(String activeiconpath) {
        this.activeiconpath = activeiconpath;
    }

    /**
     * Gets the inactiveiconpath.
     * 
     * @return the inactiveiconpath
     */
    public String getInactiveiconpath() {
        return this.inactiveiconpath;
    }
    
    /**
     * Sets the inactiveiconpath.
     * 
     * @param inactiveiconpath the new inactiveiconpath
     */
    public void setInactiveiconpath(String inactiveiconpath) {
        this.inactiveiconpath = inactiveiconpath;
    }

    /**
     * Gets the inputmanid.
     * 
     * @return the inputmanid
     */
    public String getInputmanid() {
        return this.inputmanid;
    }
    
    /**
     * Sets the inputmanid.
     * 
     * @param inputmanid the new inputmanid
     */
    public void setInputmanid(String inputmanid) {
        this.inputmanid = inputmanid;
    }

    /**
     * Gets the inputtime.
     * 
     * @return the inputtime
     */
    public Date getInputtime() {
        return this.inputtime;
    }
    
    /**
     * Sets the inputtime.
     * 
     * @param inputtime the new inputtime
     */
    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    /**
     * Gets the remark.
     * 
     * @return the remark
     */
    public String getRemark() {
        return this.remark;
    }
    
    /**
     * Sets the remark.
     * 
     * @param remark the new remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}