package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * Title 		:the Class IconSet.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */

public class IconSet  implements java.io.Serializable {


    // Fields    

     /** The iconsetid. */
    private String iconsetid;
     
     /** The iconsetname. */
     private String iconsetname;
     
     /** The iconsetrelpath. */
     private String iconsetrelpath;
     
     /** The iconsetcode. */
     private String iconsetcode;
     
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
     * Instantiates a new icon set.
     */
    public IconSet() {
    }

	/**
	 * Instantiates a new icon set.
	 * 
	 * @param iconsetid the iconsetid
	 */
    public IconSet(String iconsetid) {
        this.iconsetid = iconsetid;
    }
    
    /**
     * Instantiates a new icon set.
     * 
     * @param iconsetid the iconsetid
     * @param iconsetname the iconsetname
     * @param iconsetrelpath the iconsetrelpath
     * @param iconsetcode the iconsetcode
     * @param defaulticonpath the defaulticonpath
     * @param activeiconpath the activeiconpath
     * @param inactiveiconpath the inactiveiconpath
     * @param inputmanid the inputmanid
     * @param inputtime the inputtime
     * @param remark the remark
     */
    public IconSet(String iconsetid, String iconsetname, String iconsetrelpath, String iconsetcode, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.iconsetid = iconsetid;
        this.iconsetname = iconsetname;
        this.iconsetrelpath = iconsetrelpath;
        this.iconsetcode = iconsetcode;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    /**
     * Gets the iconsetid.
     * 
     * @return the iconsetid
     */
    public String getIconsetid() {
        return this.iconsetid;
    }
    
    /**
     * Sets the iconsetid.
     * 
     * @param iconsetid the new iconsetid
     */
    public void setIconsetid(String iconsetid) {
        this.iconsetid = iconsetid;
    }

    /**
     * Gets the iconsetname.
     * 
     * @return the iconsetname
     */
    public String getIconsetname() {
        return this.iconsetname;
    }
    
    /**
     * Sets the iconsetname.
     * 
     * @param iconsetname the new iconsetname
     */
    public void setIconsetname(String iconsetname) {
        this.iconsetname = iconsetname;
    }

    /**
     * Gets the iconsetrelpath.
     * 
     * @return the iconsetrelpath
     */
    public String getIconsetrelpath() {
        return this.iconsetrelpath;
    }
    
    /**
     * Sets the iconsetrelpath.
     * 
     * @param iconsetrelpath the new iconsetrelpath
     */
    public void setIconsetrelpath(String iconsetrelpath) {
        this.iconsetrelpath = iconsetrelpath;
    }

    /**
     * Gets the iconsetcode.
     * 
     * @return the iconsetcode
     */
    public String getIconsetcode() {
        return this.iconsetcode;
    }
    
    /**
     * Sets the iconsetcode.
     * 
     * @param iconsetcode the new iconsetcode
     */
    public void setIconsetcode(String iconsetcode) {
        this.iconsetcode = iconsetcode;
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