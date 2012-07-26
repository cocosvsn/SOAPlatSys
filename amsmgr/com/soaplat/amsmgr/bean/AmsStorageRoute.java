package com.soaplat.amsmgr.bean;
// default package

import java.util.Date;


/**
 * Title 		:the Class AmsStorageRoute.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */

public class AmsStorageRoute  implements java.io.Serializable {


    // Fields    

     /** The relid. */
    private String relid;
     
     /** The appname. */
     private String appname;
     
     /** The appcode. */
     private String appcode;
     
     /** The stclassglobalidsou. */
     private String stclassglobalidsou;
     
     /** The stclassglobaliddes. */
     private String stclassglobaliddes;
     
     /** The stclassvalid. */
     private String stclassvalid;
     
     /** The soustartpnt. */
     private String soustartpnt;
     
     /** The desendpnt. */
     private String desendpnt;
     
     /** The inputmanid. */
     private String inputmanid;
     
     /** The inputtime. */
     private Date inputtime;
     
     /** The remark. */
     private String remark;


    // Constructors

    /**
     * Instantiates a new ams storage route.
     */
    public AmsStorageRoute() {
    }

	/**
	 * Instantiates a new ams storage route.
	 * 
	 * @param relid the relid
	 * @param stclassglobalidsou the stclassglobalidsou
	 * @param stclassglobaliddes the stclassglobaliddes
	 */
    public AmsStorageRoute(String relid, String stclassglobalidsou, String stclassglobaliddes) {
        this.relid = relid;
        this.stclassglobalidsou = stclassglobalidsou;
        this.stclassglobaliddes = stclassglobaliddes;
    }
    
    /**
     * Instantiates a new ams storage route.
     * 
     * @param relid the relid
     * @param appname the appname
     * @param appcode the appcode
     * @param stclassglobalidsou the stclassglobalidsou
     * @param stclassglobaliddes the stclassglobaliddes
     * @param stclassvalid the stclassvalid
     * @param soustartpnt the soustartpnt
     * @param desendpnt the desendpnt
     * @param inputmanid the inputmanid
     * @param inputtime the inputtime
     * @param remark the remark
     */
    public AmsStorageRoute(String relid, String appname, String appcode, String stclassglobalidsou, String stclassglobaliddes, String stclassvalid, String soustartpnt, String desendpnt, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.appname = appname;
        this.appcode = appcode;
        this.stclassglobalidsou = stclassglobalidsou;
        this.stclassglobaliddes = stclassglobaliddes;
        this.stclassvalid = stclassvalid;
        this.soustartpnt = soustartpnt;
        this.desendpnt = desendpnt;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    /**
     * Gets the relid.
     * 
     * @return the relid
     */
    public String getRelid() {
        return this.relid;
    }
    
    /**
     * Sets the relid.
     * 
     * @param relid the new relid
     */
    public void setRelid(String relid) {
        this.relid = relid;
    }

    /**
     * Gets the appname.
     * 
     * @return the appname
     */
    public String getAppname() {
        return this.appname;
    }
    
    /**
     * Sets the appname.
     * 
     * @param appname the new appname
     */
    public void setAppname(String appname) {
        this.appname = appname;
    }

    /**
     * Gets the appcode.
     * 
     * @return the appcode
     */
    public String getAppcode() {
        return this.appcode;
    }
    
    /**
     * Sets the appcode.
     * 
     * @param appcode the new appcode
     */
    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    /**
     * Gets the stclassglobalidsou.
     * 
     * @return the stclassglobalidsou
     */
    public String getStclassglobalidsou() {
        return this.stclassglobalidsou;
    }
    
    /**
     * Sets the stclassglobalidsou.
     * 
     * @param stclassglobalidsou the new stclassglobalidsou
     */
    public void setStclassglobalidsou(String stclassglobalidsou) {
        this.stclassglobalidsou = stclassglobalidsou;
    }

    /**
     * Gets the stclassglobaliddes.
     * 
     * @return the stclassglobaliddes
     */
    public String getStclassglobaliddes() {
        return this.stclassglobaliddes;
    }
    
    /**
     * Sets the stclassglobaliddes.
     * 
     * @param stclassglobaliddes the new stclassglobaliddes
     */
    public void setStclassglobaliddes(String stclassglobaliddes) {
        this.stclassglobaliddes = stclassglobaliddes;
    }

    /**
     * Gets the stclassvalid.
     * 
     * @return the stclassvalid
     */
    public String getStclassvalid() {
        return this.stclassvalid;
    }
    
    /**
     * Sets the stclassvalid.
     * 
     * @param stclassvalid the new stclassvalid
     */
    public void setStclassvalid(String stclassvalid) {
        this.stclassvalid = stclassvalid;
    }

    /**
     * Gets the soustartpnt.
     * 
     * @return the soustartpnt
     */
    public String getSoustartpnt() {
        return this.soustartpnt;
    }
    
    /**
     * Sets the soustartpnt.
     * 
     * @param soustartpnt the new soustartpnt
     */
    public void setSoustartpnt(String soustartpnt) {
        this.soustartpnt = soustartpnt;
    }

    /**
     * Gets the desendpnt.
     * 
     * @return the desendpnt
     */
    public String getDesendpnt() {
        return this.desendpnt;
    }
    
    /**
     * Sets the desendpnt.
     * 
     * @param desendpnt the new desendpnt
     */
    public void setDesendpnt(String desendpnt) {
        this.desendpnt = desendpnt;
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