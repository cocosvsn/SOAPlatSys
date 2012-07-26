package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;

import com.cbs.cbsmgr.dto.AddressDTO;


/**
 * Address entity. @author MyEclipse Persistence Tools
 */

public class Address implements java.io.Serializable {


    // Fields    

     private Long addressId;
     private Long customerId;
//     private Long addressType;		// 不使用
     private String name;
     private String gender;
     private Date birthday;
//     private Long countryId;		// 不使用
//     private Long provinceId;		// 不使用
//     private Long cityId;			// 不使用
//     private Long districtId;		// 不使用
//     private String road;			// 不使用
//     private String path;			// 不使用
//     private String no;				// 不使用
     private String room;
     private String postalCode;
     private String homeTelephone;
     private String officeTelephone;
     private String fax;
     private String pager;
     private String cell;
     private String email;
     private Long referenceTypeId;		// 数据字典
     private String referenceNo;


    // Constructors

    /** default constructor */
     public Address() {


     }
    public Address(AddressDTO addressDTO) {

        this.name = addressDTO.getName();
        this.gender = addressDTO.getGender();
        this.birthday = addressDTO.getBirthday();
//        this.countryId = (long)0;
//        this.provinceId = (long)0;
//        this.cityId = (long)0;
//        this.districtId = (long)0;
//        this.road = "";
//        this.path = "";
//        this.no = "";
        this.room = addressDTO.getRoom();
        this.postalCode = addressDTO.getPostalCode();
        this.homeTelephone = addressDTO.getHomeTelephone();
        this.officeTelephone = addressDTO.getOfficeTelephone();
        this.fax = addressDTO.getFax();
        this.pager = addressDTO.getPager();
        this.cell = addressDTO.getCell();
        this.email = addressDTO.getEmail();
        this.referenceTypeId = addressDTO.getReferenceTypeId();
        this.referenceNo = addressDTO.getReferenceNo();
    }

	/** minimal constructor */
    public Address(Long addressId, Long customerId, Long addressType, Long countryId, Long provinceId, Long cityId, Long districtId, Long referenceTypeId) {
        this.addressId = addressId;
        this.customerId = customerId;
//        this.addressType = addressType;
//        this.countryId = countryId;
//        this.provinceId = provinceId;
//        this.cityId = cityId;
//        this.districtId = districtId;
        this.referenceTypeId = referenceTypeId;
    }
    
    /** full constructor */
    public Address(Long addressId, Long customerId, Long addressType, String name, String gender, Date birthday, Long countryId, Long provinceId, Long cityId, Long districtId, String road, String path, String no, String room, String postalCode, String homeTelephone, String officeTelephone, String fax, String pager, String cell, String email, Long referenceTypeId, String referenceNo) {
        this.addressId = addressId;
        this.customerId = customerId;
//        this.addressType = addressType;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
//        this.countryId = countryId;
//        this.provinceId = provinceId;
//        this.cityId = cityId;
//        this.districtId = districtId;
//        this.road = road;
//        this.path = path;
//        this.no = no;
        this.room = room;
        this.postalCode = postalCode;
        this.homeTelephone = homeTelephone;
        this.officeTelephone = officeTelephone;
        this.fax = fax;
        this.pager = pager;
        this.cell = cell;
        this.email = email;
        this.referenceTypeId = referenceTypeId;
        this.referenceNo = referenceNo;
    }

   
    // Property accessors

    public Long getAddressId() {
        return this.addressId;
    }
    
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

//    public Long getAddressType() {
//        return this.addressType;
//    }
//    
//    public void setAddressType(Long addressType) {
//        this.addressType = addressType;
//    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRoom() {
        return this.room;
    }
    
    public void setRoom(String room) {
        this.room = room;
    }

    public String getPostalCode() {
        return this.postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHomeTelephone() {
        return this.homeTelephone;
    }
    
    public void setHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
    }

    public String getOfficeTelephone() {
        return this.officeTelephone;
    }
    
    public void setOfficeTelephone(String officeTelephone) {
        this.officeTelephone = officeTelephone;
    }

    public String getFax() {
        return this.fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPager() {
        return this.pager;
    }
    
    public void setPager(String pager) {
        this.pager = pager;
    }

    public String getCell() {
        return this.cell;
    }
    
    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public Long getReferenceTypeId() {
        return this.referenceTypeId;
    }
    
    public void setReferenceTypeId(Long referenceTypeId) {
        this.referenceTypeId = referenceTypeId;
    }

    public String getReferenceNo() {
        return this.referenceNo;
    }
    
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

   








}