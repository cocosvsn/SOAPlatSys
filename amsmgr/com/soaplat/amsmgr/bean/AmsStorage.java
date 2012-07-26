package com.soaplat.amsmgr.bean;
// default package

import java.util.Date;


/**
 * Title 		: 存储体表
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */

public class AmsStorage  implements java.io.Serializable {


    // Fields    

     /** 存储体GLOBALID */
    private String stglobalid;
     
     /** 存储体名称 */
     private String storagename;
     
     /** 存储体IP */
     private String storageip;
     
     /** 存储体等级GLOBALID */
     private String stclassglobalid;
     
     /** 存储体访问类型（数据字典） */
     private String storageaccstype;
     
     /** 登陆用户名 */
     private String loginname;
     
     /** 登陆密码 */
     private String loginpwd;
     
     /** 映射存储体路径 */
     private String mappath;
     
     /** 映射登陆名 */
     private String maploginuid;
     
     /** 映射登陆密码 */
     private String maploginpwd;
     
     /** 影射盘符 */
     private String maplogindisk;
     
     /** 总容量(M) */
     private String totalcap;
     
     /** 存储体所属区域 */
     private String districtid;
     
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

	private Long port;

    // Constructors

    /**
     * Instantiates a new ams storage.
     */
    public AmsStorage() {
    }

	/**
	 * Instantiates a new ams storage.
	 * 
	 * @param stglobalid the stglobalid
	 * @param storagename the storagename
	 * @param stclassglobalid the stclassglobalid
	 */
    public AmsStorage(String stglobalid, String storagename, String stclassglobalid) {
        this.stglobalid = stglobalid;
        this.storagename = storagename;
        this.stclassglobalid = stclassglobalid;
    }
    
    /**
     * Instantiates a new ams storage.
     * 
     * @param stglobalid the stglobalid
     * @param storagename the storagename
     * @param storageip the storageip
     * @param stclassglobalid the stclassglobalid
     * @param storageaccstype the storageaccstype
     * @param loginname the loginname
     * @param loginpwd the loginpwd
     * @param mappath the mappath
     * @param maploginuid the maploginuid
     * @param maploginpwd the maploginpwd
     * @param maplogindisk the maplogindisk
     * @param totalcap the totalcap
     * @param districtid the districtid
     * @param storagevalid the storagevalid
     * @param defaulticonpath the defaulticonpath
     * @param activeiconpath the activeiconpath
     * @param inactiveiconpath the inactiveiconpath
     * @param inputmanid the inputmanid
     * @param inputtime the inputtime
     * @param remark the remark
     */
    public AmsStorage(String stglobalid, String storagename, String storageip, String stclassglobalid, String storageaccstype, String loginname, String loginpwd, String mappath, String maploginuid, String maploginpwd, String maplogindisk, String totalcap, String districtid, String storagevalid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.stglobalid = stglobalid;
        this.storagename = storagename;
        this.storageip = storageip;
        this.stclassglobalid = stclassglobalid;
        this.storageaccstype = storageaccstype;
        this.loginname = loginname;
        this.loginpwd = loginpwd;
        this.mappath = mappath;
        this.maploginuid = maploginuid;
        this.maploginpwd = maploginpwd;
        this.maplogindisk = maplogindisk;
        this.totalcap = totalcap;
        this.districtid = districtid;
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
     * Gets the stglobalid.
     * 
     * @return the stglobalid
     */
    public String getStglobalid() {
        return this.stglobalid;
    }
    
    /**
     * Sets the stglobalid.
     * 
     * @param stglobalid the new stglobalid
     */
    public void setStglobalid(String stglobalid) {
        this.stglobalid = stglobalid;
    }

    /**
     * Gets the storagename.
     * 
     * @return the storagename
     */
    public String getStoragename() {
        return this.storagename;
    }
    
    /**
     * Sets the storagename.
     * 
     * @param storagename the new storagename
     */
    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }

    /**
     * Gets the storageip.
     * 
     * @return the storageip
     */
    public String getStorageip() {
        return this.storageip;
    }
    
    /**
     * Sets the storageip.
     * 
     * @param storageip the new storageip
     */
    public void setStorageip(String storageip) {
        this.storageip = storageip;
    }

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
     * Gets the storageaccstype.
     * 
     * @return the storageaccstype
     */
    public String getStorageaccstype() {
        return this.storageaccstype;
    }
    
    /**
     * Sets the storageaccstype.
     * 
     * @param storageaccstype the new storageaccstype
     */
    public void setStorageaccstype(String storageaccstype) {
        this.storageaccstype = storageaccstype;
    }

    /**
     * Gets the loginname.
     * 
     * @return the loginname
     */
    public String getLoginname() {
        return this.loginname;
    }
    
    /**
     * Sets the loginname.
     * 
     * @param loginname the new loginname
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    /**
     * Gets the loginpwd.
     * 
     * @return the loginpwd
     */
    public String getLoginpwd() {
        return this.loginpwd;
    }
    
    /**
     * Sets the loginpwd.
     * 
     * @param loginpwd the new loginpwd
     */
    public void setLoginpwd(String loginpwd) {
        this.loginpwd = loginpwd;
    }

    /**
     * Gets the mappath.
     * 
     * @return the mappath
     */
    public String getMappath() {
        return this.mappath;
    }
    
    /**
     * Sets the mappath.
     * 
     * @param mappath the new mappath
     */
    public void setMappath(String mappath) {
        this.mappath = mappath;
    }

    /**
     * Gets the maploginuid.
     * 
     * @return the maploginuid
     */
    public String getMaploginuid() {
        return this.maploginuid;
    }
    
    /**
     * Sets the maploginuid.
     * 
     * @param maploginuid the new maploginuid
     */
    public void setMaploginuid(String maploginuid) {
        this.maploginuid = maploginuid;
    }

    /**
     * Gets the maploginpwd.
     * 
     * @return the maploginpwd
     */
    public String getMaploginpwd() {
        return this.maploginpwd;
    }
    
    /**
     * Sets the maploginpwd.
     * 
     * @param maploginpwd the new maploginpwd
     */
    public void setMaploginpwd(String maploginpwd) {
        this.maploginpwd = maploginpwd;
    }

    /**
     * Gets the maplogindisk.
     * 
     * @return the maplogindisk
     */
    public String getMaplogindisk() {
        return this.maplogindisk;
    }
    
    /**
     * Sets the maplogindisk.
     * 
     * @param maplogindisk the new maplogindisk
     */
    public void setMaplogindisk(String maplogindisk) {
        this.maplogindisk = maplogindisk;
    }

    /**
     * Gets the totalcap.
     * 
     * @return the totalcap
     */
    public String getTotalcap() {
        return this.totalcap;
    }
    
    /**
     * Sets the totalcap.
     * 
     * @param totalcap the new totalcap
     */
    public void setTotalcap(String totalcap) {
        this.totalcap = totalcap;
    }

    /**
     * Gets the districtid.
     * 
     * @return the districtid
     */
    public String getDistrictid() {
        return this.districtid;
    }
    
    /**
     * Sets the districtid.
     * 
     * @param districtid the new districtid
     */
    public void setDistrictid(String districtid) {
        this.districtid = districtid;
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
     * 
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
   
	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
	}







}
