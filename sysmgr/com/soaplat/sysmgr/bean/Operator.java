package com.soaplat.sysmgr.bean;
// default package

import java.util.Date;


/**
 * Operator entity. @author MyEclipse Persistence Tools
 */

public class Operator  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3725059675402492570L;
	
	// Fields    
     private String operatorid;		// 用户编号 主键
     private String userid;			// 用户名
     private String password;		// 密码
     private Date invaldate;
     private String operatorname;	// 呢称
     private String authmode;
     private String status;
     private Date unlocktime;
     private String menutype;
     private Date lastlogin;
     private Long errcount;
     private Date startdate;
     private Date enddate;
     private String validtime;
     private String maccode;
     private String ipaddress;
     private String orgid;
     private String duty;
     private String operatorclass;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public Operator() {
    }

	/** minimal constructor */
    public Operator(String operatorid, String orgid) {
        this.operatorid = operatorid;
        this.orgid = orgid;
    }
    
    /** full constructor */
    public Operator(String operatorid, String userid, String password, Date invaldate, String operatorname, String authmode, String status, Date unlocktime, String menutype, Date lastlogin, Long errcount, Date startdate, Date enddate, String validtime, String maccode, String ipaddress, String orgid, String duty, String operatorclass, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.operatorid = operatorid;
        this.userid = userid;
        this.password = password;
        this.invaldate = invaldate;
        this.operatorname = operatorname;
        this.authmode = authmode;
        this.status = status;
        this.unlocktime = unlocktime;
        this.menutype = menutype;
        this.lastlogin = lastlogin;
        this.errcount = errcount;
        this.startdate = startdate;
        this.enddate = enddate;
        this.validtime = validtime;
        this.maccode = maccode;
        this.ipaddress = ipaddress;
        this.orgid = orgid;
        this.duty = duty;
        this.operatorclass = operatorclass;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

    // Property accessors
    /**
     * 用户编号 主键
     * @return
     */
    public String getOperatorid() {
        return this.operatorid;
    }
    
    /**
     * 用户编号 主键
     * @param operatorid
     */
    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getInvaldate() {
        return this.invaldate;
    }
    
    public void setInvaldate(Date invaldate) {
        this.invaldate = invaldate;
    }

    public String getOperatorname() {
        return this.operatorname;
    }
    
    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public String getAuthmode() {
        return this.authmode;
    }
    
    public void setAuthmode(String authmode) {
        this.authmode = authmode;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUnlocktime() {
        return this.unlocktime;
    }
    
    public void setUnlocktime(Date unlocktime) {
        this.unlocktime = unlocktime;
    }

    public String getMenutype() {
        return this.menutype;
    }
    
    public void setMenutype(String menutype) {
        this.menutype = menutype;
    }

    public Date getLastlogin() {
        return this.lastlogin;
    }
    
    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public Long getErrcount() {
        return this.errcount;
    }
    
    public void setErrcount(Long errcount) {
        this.errcount = errcount;
    }

    public Date getStartdate() {
        return this.startdate;
    }
    
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return this.enddate;
    }
    
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getValidtime() {
        return this.validtime;
    }
    
    public void setValidtime(String validtime) {
        this.validtime = validtime;
    }

    public String getMaccode() {
        return this.maccode;
    }
    
    public void setMaccode(String maccode) {
        this.maccode = maccode;
    }

    public String getIpaddress() {
        return this.ipaddress;
    }
    
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getOrgid() {
        return this.orgid;
    }
    
    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getDuty() {
        return this.duty;
    }
    
    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getOperatorclass() {
        return this.operatorclass;
    }
    
    public void setOperatorclass(String operatorclass) {
        this.operatorclass = operatorclass;
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