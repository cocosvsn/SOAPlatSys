package com.cbs.cbsmgr.bean;
// default package



/**
 * District entity. @author MyEclipse Persistence Tools
 */

public class SmsDistrict  implements java.io.Serializable {


    // Fields    

     private String districtId;
     private String district;
     private Long type;
     private String remark;


    // Constructors

    /** default constructor */
    public SmsDistrict() {
    }

	/** minimal constructor */
    public SmsDistrict(String districtId) {
        this.districtId = districtId;
    }
    
    /** full constructor */
    public SmsDistrict(String districtId, String district, Long type, String remark) {
        this.districtId = districtId;
        this.district = district;
        this.type = type;
        this.remark = remark;
    }

   
    // Property accessors

    public String getDistrictId() {
        return this.districtId;
    }
    
    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrict() {
        return this.district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}