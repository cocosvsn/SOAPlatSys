package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * District entity. @author MyEclipse Persistence Tools
 */

public class District  implements java.io.Serializable {


    // Fields    

     private String districtid;
     private String smsdstid;
     private String districtname;
     private String districtcode;
     private String districttype;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public District() {
    }

	/** minimal constructor */
    public District(String districtid) {
        this.districtid = districtid;
    }
    
    /** full constructor */
    public District(String districtid, String smsdstid, String districtname, String districtcode, String districttype, String inputmanid, Date inputtime, String remark) {
        this.districtid = districtid;
        this.smsdstid = smsdstid;
        this.districtname = districtname;
        this.districtcode = districtcode;
        this.districttype = districttype;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getDistrictid() {
        return this.districtid;
    }
    
    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public String getSmsdstid() {
        return this.smsdstid;
    }
    
    public void setSmsdstid(String smsdstid) {
        this.smsdstid = smsdstid;
    }

    public String getDistrictname() {
        return this.districtname;
    }
    
    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public String getDistrictcode() {
        return this.districtcode;
    }
    
    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getDistricttype() {
        return this.districttype;
    }
    
    public void setDistricttype(String districttype) {
        this.districttype = districttype;
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