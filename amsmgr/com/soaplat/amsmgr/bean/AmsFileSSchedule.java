package com.soaplat.amsmgr.bean;
// default package

import java.util.Date;


/**
 * Title 		:the Class AmsFileSSchedule.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */

public class AmsFileSSchedule  implements java.io.Serializable {


    // Fields    

     /** The relid. */
    private String relid;
     
     /** The filesgloabalid. */
     private String filesgloabalid;
     
     /** The pglobalid. */
     private String pglobalid;
     
     /** The ftypeglobalid. */
     private String ftypeglobalid;
     
     /** The filename. */
     private String filename;
     
     /** The sourcepath. */
     private String sourcepath;
     
     /** The destpath. */
     private String destpath;
     
     /** The migstarttime. */
     private Date migstarttime;
     
     /** The migendtime. */
     private Date migendtime;
     
     /** The migstatus. */
     private Long migstatus;
     
     /** The inputmanid. */
     private String inputmanid;
     
     /** The inputtime. */
     private Date inputtime;
     
     /** The remark. */
     private String remark;


    // Constructors

    /**
     * Instantiates a new ams file s schedule.
     */
    public AmsFileSSchedule() {
    }

	/**
	 * Instantiates a new ams file s schedule.
	 * 
	 * @param relid the relid
	 * @param filesgloabalid the filesgloabalid
	 */
    public AmsFileSSchedule(String relid, String filesgloabalid) {
        this.relid = relid;
        this.filesgloabalid = filesgloabalid;
    }
    
    /**
     * Instantiates a new ams file s schedule.
     * 
     * @param relid the relid
     * @param filesgloabalid the filesgloabalid
     * @param pglobalid the pglobalid
     * @param ftypeglobalid the ftypeglobalid
     * @param filename the filename
     * @param sourcepath the sourcepath
     * @param destpath the destpath
     * @param migstarttime the migstarttime
     * @param migendtime the migendtime
     * @param migstatus the migstatus
     * @param inputmanid the inputmanid
     * @param inputtime the inputtime
     * @param remark the remark
     */
    public AmsFileSSchedule(String relid, String filesgloabalid, String pglobalid, String ftypeglobalid, String filename, String sourcepath, String destpath, Date migstarttime, Date migendtime, Long migstatus, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.filesgloabalid = filesgloabalid;
        this.pglobalid = pglobalid;
        this.ftypeglobalid = ftypeglobalid;
        this.filename = filename;
        this.sourcepath = sourcepath;
        this.destpath = destpath;
        this.migstarttime = migstarttime;
        this.migendtime = migendtime;
        this.migstatus = migstatus;
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
     * Gets the pglobalid.
     * 
     * @return the pglobalid
     */
    public String getPglobalid() {
        return this.pglobalid;
    }
    
    /**
     * Sets the pglobalid.
     * 
     * @param pglobalid the new pglobalid
     */
    public void setPglobalid(String pglobalid) {
        this.pglobalid = pglobalid;
    }

    /**
     * Gets the ftypeglobalid.
     * 
     * @return the ftypeglobalid
     */
    public String getFtypeglobalid() {
        return this.ftypeglobalid;
    }
    
    /**
     * Sets the ftypeglobalid.
     * 
     * @param ftypeglobalid the new ftypeglobalid
     */
    public void setFtypeglobalid(String ftypeglobalid) {
        this.ftypeglobalid = ftypeglobalid;
    }

    /**
     * Gets the filename.
     * 
     * @return the filename
     */
    public String getFilename() {
        return this.filename;
    }
    
    /**
     * Sets the filename.
     * 
     * @param filename the new filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Gets the sourcepath.
     * 
     * @return the sourcepath
     */
    public String getSourcepath() {
        return this.sourcepath;
    }
    
    /**
     * Sets the sourcepath.
     * 
     * @param sourcepath the new sourcepath
     */
    public void setSourcepath(String sourcepath) {
        this.sourcepath = sourcepath;
    }

    /**
     * Gets the destpath.
     * 
     * @return the destpath
     */
    public String getDestpath() {
        return this.destpath;
    }
    
    /**
     * Sets the destpath.
     * 
     * @param destpath the new destpath
     */
    public void setDestpath(String destpath) {
        this.destpath = destpath;
    }

    /**
     * Gets the migstarttime.
     * 
     * @return the migstarttime
     */
    public Date getMigstarttime() {
        return this.migstarttime;
    }
    
    /**
     * Sets the migstarttime.
     * 
     * @param migstarttime the new migstarttime
     */
    public void setMigstarttime(Date migstarttime) {
        this.migstarttime = migstarttime;
    }

    /**
     * Gets the migendtime.
     * 
     * @return the migendtime
     */
    public Date getMigendtime() {
        return this.migendtime;
    }
    
    /**
     * Sets the migendtime.
     * 
     * @param migendtime the new migendtime
     */
    public void setMigendtime(Date migendtime) {
        this.migendtime = migendtime;
    }

    /**
     * Gets the migstatus.
     * 
     * @return the migstatus
     */
    public Long getMigstatus() {
        return this.migstatus;
    }
    
    /**
     * Sets the migstatus.
     * 
     * @param migstatus the new migstatus
     */
    public void setMigstatus(Long migstatus) {
        this.migstatus = migstatus;
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