package com.soaplat.amsmgr.bean;
// default package

import java.util.Date;


/**
 * Title 		: 存储体等级表
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */

public class AmsStorageClass  implements java.io.Serializable {


    // Fields    

     /** 存储体等级GLOBALID */
    private String stclassglobalid;
     
     /** 存储体等级名称 */
     private String stclassname;
     
     /** 存储体等级代码 */
     private String stclasscode;
     
     /** 存储体等级优先级 */
     private String stclasspri;
     
     /** 数据库对象名称 */
     private String dbobjname;
     
     /** 数据库中的状态标识 */
     private String statuscode;
     
     /** 数据库中的的名称 */
     private String statusname;
     
     /** 数据库中的的路径标识 */
     private String pathcode;
     
     /** 是否启用数据库中标识 */
     private String codevalid;
     
     /** 是否可用 */
     private String storagevalid;
     
     /** 默认状态图标路径 */
     private String defaulticonpath;
     
     /** 激活状态图标路径 */
     private String activeiconpath;
     
     /** 未激活状态图标路径 */
     private String inactiveiconpath;
     
     /** 信息录入人 */
     private String inputmanid;
     
     /** 信息录入时间 */
     private Date inputtime;
     
     /** 备注 */
     private String remark;


    // Constructors

    /**
     * Instantiates a new ams storage class.
     */
    public AmsStorageClass() {
    }

	/**
	 * Instantiates a new ams storage class.
	 * 
	 * @param stclassglobalid the stclassglobalid
	 * @param stclasscode the stclasscode
	 * @param stclasspri the stclasspri
	 */
    public AmsStorageClass(String stclassglobalid, String stclasscode, String stclasspri) {
        this.stclassglobalid = stclassglobalid;
        this.stclasscode = stclasscode;
        this.stclasspri = stclasspri;
    }
    
    /**
     * Instantiates a new ams storage class.
     * 
     * @param stclassglobalid the stclassglobalid
     * @param stclassname the stclassname
     * @param stclasscode the stclasscode
     * @param stclasspri the stclasspri
     * @param dbobjname the dbobjname
     * @param statuscode the statuscode
     * @param statusname the statusname
     * @param pathcode the pathcode
     * @param codevalid the codevalid
     * @param storagevalid the storagevalid
     * @param defaulticonpath the defaulticonpath
     * @param activeiconpath the activeiconpath
     * @param inactiveiconpath the inactiveiconpath
     * @param inputmanid the inputmanid
     * @param inputtime the inputtime
     * @param remark the remark
     */
    public AmsStorageClass(String stclassglobalid, String stclassname, String stclasscode, String stclasspri, String dbobjname, String statuscode, String statusname, String pathcode, String codevalid, String storagevalid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.stclassglobalid = stclassglobalid;
        this.stclassname = stclassname;
        this.stclasscode = stclasscode;
        this.stclasspri = stclasspri;
        this.dbobjname = dbobjname;
        this.statuscode = statuscode;
        this.statusname = statusname;
        this.pathcode = pathcode;
        this.codevalid = codevalid;
        this.storagevalid = storagevalid;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    /**
     * Gets the stclassglobalid.
     * 
     * @return the stclassglobalid
     */
    public String getStclassglobalid() {
        return this.stclassglobalid;
    }
    
    /**
     * Sets the stclassglobalid.
     * 
     * @param stclassglobalid the new stclassglobalid
     */
    public void setStclassglobalid(String stclassglobalid) {
        this.stclassglobalid = stclassglobalid;
    }

    /**
     * Gets the stclassname.
     * 
     * @return the stclassname
     */
    public String getStclassname() {
        return this.stclassname;
    }
    
    /**
     * Sets the stclassname.
     * 
     * @param stclassname the new stclassname
     */
    public void setStclassname(String stclassname) {
        this.stclassname = stclassname;
    }

    /**
     * Gets the stclasscode.
     * 
     * @return the stclasscode
     */
    public String getStclasscode() {
        return this.stclasscode;
    }
    
    /**
     * Sets the stclasscode.
     * 
     * @param stclasscode the new stclasscode
     */
    public void setStclasscode(String stclasscode) {
        this.stclasscode = stclasscode;
    }

    /**
     * Gets the stclasspri.
     * 
     * @return the stclasspri
     */
    public String getStclasspri() {
        return this.stclasspri;
    }
    
    /**
     * Sets the stclasspri.
     * 
     * @param stclasspri the new stclasspri
     */
    public void setStclasspri(String stclasspri) {
        this.stclasspri = stclasspri;
    }

    /**
     * Gets the dbobjname.
     * 
     * @return the dbobjname
     */
    public String getDbobjname() {
        return this.dbobjname;
    }
    
    /**
     * Sets the dbobjname.
     * 
     * @param dbobjname the new dbobjname
     */
    public void setDbobjname(String dbobjname) {
        this.dbobjname = dbobjname;
    }

    /**
     * Gets the statuscode.
     * 
     * @return the statuscode
     */
    public String getStatuscode() {
        return this.statuscode;
    }
    
    /**
     * Sets the statuscode.
     * 
     * @param statuscode the new statuscode
     */
    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    /**
     * Gets the statusname.
     * 
     * @return the statusname
     */
    public String getStatusname() {
        return this.statusname;
    }
    
    /**
     * Sets the statusname.
     * 
     * @param statusname the new statusname
     */
    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    /**
     * Gets the pathcode.
     * 
     * @return the pathcode
     */
    public String getPathcode() {
        return this.pathcode;
    }
    
    /**
     * Sets the pathcode.
     * 
     * @param pathcode the new pathcode
     */
    public void setPathcode(String pathcode) {
        this.pathcode = pathcode;
    }

    /**
     * Gets the codevalid.
     * 
     * @return the codevalid
     */
    public String getCodevalid() {
        return this.codevalid;
    }
    
    /**
     * Sets the codevalid.
     * 
     * @param codevalid the new codevalid
     */
    public void setCodevalid(String codevalid) {
        this.codevalid = codevalid;
    }

    /**
     * Gets the storagevalid.
     * 
     * @return the storagevalid
     */
    public String getStoragevalid() {
        return this.storagevalid;
    }
    
    /**
     * Sets the storagevalid.
     * 
     * @param storagevalid the new storagevalid
     */
    public void setStoragevalid(String storagevalid) {
        this.storagevalid = storagevalid;
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