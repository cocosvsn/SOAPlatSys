package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * StbCustomerAuth entity. @author MyEclipse Persistence Tools
 */

public class StbCustomerAuth  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String customerid;
     private String userid;
     private String encrytoken;
     private Date encrytokencreattime;
     private Date encrytokenexpiretime;
     private String authstatus;
     private String encryinfo;
     private String usertoken;
     private Date usertokencreattime;
     private Date usertokenexpiretime;
     private String preusertoken;
     private Date preusertokenexpiretime;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public StbCustomerAuth() {
    }

	/** minimal constructor */
    public StbCustomerAuth(String relid) {
        this.relid = relid;
    }
    
    /** full constructor */
    public StbCustomerAuth(String relid, String customerid, String userid, String encrytoken, Date encrytokencreattime, Date encrytokenexpiretime, String authstatus, String encryinfo, String usertoken, Date usertokencreattime, Date usertokenexpiretime, String preusertoken, Date preusertokenexpiretime, String inputmanid, Date inputtime, String remark) {
        this.relid = relid;
        this.customerid = customerid;
        this.userid = userid;
        this.encrytoken = encrytoken;
        this.encrytokencreattime = encrytokencreattime;
        this.encrytokenexpiretime = encrytokenexpiretime;
        this.authstatus = authstatus;
        this.encryinfo = encryinfo;
        this.usertoken = usertoken;
        this.usertokencreattime = usertokencreattime;
        this.usertokenexpiretime = usertokenexpiretime;
        this.preusertoken = preusertoken;
        this.preusertokenexpiretime = preusertokenexpiretime;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getRelid() {
        return this.relid;
    }
    
    public void setRelid(String relid) {
        this.relid = relid;
    }

    public String getCustomerid() {
        return this.customerid;
    }
    
    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEncrytoken() {
        return this.encrytoken;
    }
    
    public void setEncrytoken(String encrytoken) {
        this.encrytoken = encrytoken;
    }

    public Date getEncrytokencreattime() {
        return this.encrytokencreattime;
    }
    
    public void setEncrytokencreattime(Date encrytokencreattime) {
        this.encrytokencreattime = encrytokencreattime;
    }

    public Date getEncrytokenexpiretime() {
        return this.encrytokenexpiretime;
    }
    
    public void setEncrytokenexpiretime(Date encrytokenexpiretime) {
        this.encrytokenexpiretime = encrytokenexpiretime;
    }

    public String getAuthstatus() {
        return this.authstatus;
    }
    
    public void setAuthstatus(String authstatus) {
        this.authstatus = authstatus;
    }

    public String getEncryinfo() {
        return this.encryinfo;
    }
    
    public void setEncryinfo(String encryinfo) {
        this.encryinfo = encryinfo;
    }

    public String getUsertoken() {
        return this.usertoken;
    }
    
    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }

    public Date getUsertokencreattime() {
        return this.usertokencreattime;
    }
    
    public void setUsertokencreattime(Date usertokencreattime) {
        this.usertokencreattime = usertokencreattime;
    }

    public Date getUsertokenexpiretime() {
        return this.usertokenexpiretime;
    }
    
    public void setUsertokenexpiretime(Date usertokenexpiretime) {
        this.usertokenexpiretime = usertokenexpiretime;
    }

    public String getPreusertoken() {
        return this.preusertoken;
    }
    
    public void setPreusertoken(String preusertoken) {
        this.preusertoken = preusertoken;
    }

    public Date getPreusertokenexpiretime() {
        return this.preusertokenexpiretime;
    }
    
    public void setPreusertokenexpiretime(Date preusertokenexpiretime) {
        this.preusertokenexpiretime = preusertokenexpiretime;
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