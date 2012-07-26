package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * VCustomerAddressQueryId entity. @author MyEclipse Persistence Tools
 */

public class VCustomerAddressQueryId  implements java.io.Serializable {


    // Fields    

     private Long customerId;
     private Long customerTypeId;
     private Long customerStatusId;
     private Date captureDate;
     private Long defaultAddressId;
     private Long addressId;
     private String name;
     private String gender;
     private Date birthday;
     private String room;
     private String postalCode;
     private String homeTelephone;
     private String officeTelephone;
     private String fax;
     private String pager;
     private String cell;
     private String email;
     private Long referenceTypeId;
     private String referenceNo;


    // Constructors

    /** default constructor */
    public VCustomerAddressQueryId() {
    }

	/** minimal constructor */
    public VCustomerAddressQueryId(Long customerId, Long customerTypeId, Long customerStatusId, Date captureDate, Long defaultAddressId, Long addressId, Long referenceTypeId) {
        this.customerId = customerId;
        this.customerTypeId = customerTypeId;
        this.customerStatusId = customerStatusId;
        this.captureDate = captureDate;
        this.defaultAddressId = defaultAddressId;
        this.addressId = addressId;
        this.referenceTypeId = referenceTypeId;
    }
    
    /** full constructor */
    public VCustomerAddressQueryId(Long customerId, Long customerTypeId, Long customerStatusId, Date captureDate, Long defaultAddressId, Long addressId, String name, String gender, Date birthday, String room, String postalCode, String homeTelephone, String officeTelephone, String fax, String pager, String cell, String email, Long referenceTypeId, String referenceNo) {
        this.customerId = customerId;
        this.customerTypeId = customerTypeId;
        this.customerStatusId = customerStatusId;
        this.captureDate = captureDate;
        this.defaultAddressId = defaultAddressId;
        this.addressId = addressId;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
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

    public Long getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerTypeId() {
        return this.customerTypeId;
    }
    
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public Long getCustomerStatusId() {
        return this.customerStatusId;
    }
    
    public void setCustomerStatusId(Long customerStatusId) {
        this.customerStatusId = customerStatusId;
    }

    public Date getCaptureDate() {
        return this.captureDate;
    }
    
    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }

    public Long getDefaultAddressId() {
        return this.defaultAddressId;
    }
    
    public void setDefaultAddressId(Long defaultAddressId) {
        this.defaultAddressId = defaultAddressId;
    }

    public Long getAddressId() {
        return this.addressId;
    }
    
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof VCustomerAddressQueryId) ) return false;
		 VCustomerAddressQueryId castOther = ( VCustomerAddressQueryId ) other; 
         
		 return ( (this.getCustomerId()==castOther.getCustomerId()) || ( this.getCustomerId()!=null && castOther.getCustomerId()!=null && this.getCustomerId().equals(castOther.getCustomerId()) ) )
 && ( (this.getCustomerTypeId()==castOther.getCustomerTypeId()) || ( this.getCustomerTypeId()!=null && castOther.getCustomerTypeId()!=null && this.getCustomerTypeId().equals(castOther.getCustomerTypeId()) ) )
 && ( (this.getCustomerStatusId()==castOther.getCustomerStatusId()) || ( this.getCustomerStatusId()!=null && castOther.getCustomerStatusId()!=null && this.getCustomerStatusId().equals(castOther.getCustomerStatusId()) ) )
 && ( (this.getCaptureDate()==castOther.getCaptureDate()) || ( this.getCaptureDate()!=null && castOther.getCaptureDate()!=null && this.getCaptureDate().equals(castOther.getCaptureDate()) ) )
 && ( (this.getDefaultAddressId()==castOther.getDefaultAddressId()) || ( this.getDefaultAddressId()!=null && castOther.getDefaultAddressId()!=null && this.getDefaultAddressId().equals(castOther.getDefaultAddressId()) ) )
 && ( (this.getAddressId()==castOther.getAddressId()) || ( this.getAddressId()!=null && castOther.getAddressId()!=null && this.getAddressId().equals(castOther.getAddressId()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getGender()==castOther.getGender()) || ( this.getGender()!=null && castOther.getGender()!=null && this.getGender().equals(castOther.getGender()) ) )
 && ( (this.getBirthday()==castOther.getBirthday()) || ( this.getBirthday()!=null && castOther.getBirthday()!=null && this.getBirthday().equals(castOther.getBirthday()) ) )
 && ( (this.getRoom()==castOther.getRoom()) || ( this.getRoom()!=null && castOther.getRoom()!=null && this.getRoom().equals(castOther.getRoom()) ) )
 && ( (this.getPostalCode()==castOther.getPostalCode()) || ( this.getPostalCode()!=null && castOther.getPostalCode()!=null && this.getPostalCode().equals(castOther.getPostalCode()) ) )
 && ( (this.getHomeTelephone()==castOther.getHomeTelephone()) || ( this.getHomeTelephone()!=null && castOther.getHomeTelephone()!=null && this.getHomeTelephone().equals(castOther.getHomeTelephone()) ) )
 && ( (this.getOfficeTelephone()==castOther.getOfficeTelephone()) || ( this.getOfficeTelephone()!=null && castOther.getOfficeTelephone()!=null && this.getOfficeTelephone().equals(castOther.getOfficeTelephone()) ) )
 && ( (this.getFax()==castOther.getFax()) || ( this.getFax()!=null && castOther.getFax()!=null && this.getFax().equals(castOther.getFax()) ) )
 && ( (this.getPager()==castOther.getPager()) || ( this.getPager()!=null && castOther.getPager()!=null && this.getPager().equals(castOther.getPager()) ) )
 && ( (this.getCell()==castOther.getCell()) || ( this.getCell()!=null && castOther.getCell()!=null && this.getCell().equals(castOther.getCell()) ) )
 && ( (this.getEmail()==castOther.getEmail()) || ( this.getEmail()!=null && castOther.getEmail()!=null && this.getEmail().equals(castOther.getEmail()) ) )
 && ( (this.getReferenceTypeId()==castOther.getReferenceTypeId()) || ( this.getReferenceTypeId()!=null && castOther.getReferenceTypeId()!=null && this.getReferenceTypeId().equals(castOther.getReferenceTypeId()) ) )
 && ( (this.getReferenceNo()==castOther.getReferenceNo()) || ( this.getReferenceNo()!=null && castOther.getReferenceNo()!=null && this.getReferenceNo().equals(castOther.getReferenceNo()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCustomerId() == null ? 0 : this.getCustomerId().hashCode() );
         result = 37 * result + ( getCustomerTypeId() == null ? 0 : this.getCustomerTypeId().hashCode() );
         result = 37 * result + ( getCustomerStatusId() == null ? 0 : this.getCustomerStatusId().hashCode() );
         result = 37 * result + ( getCaptureDate() == null ? 0 : this.getCaptureDate().hashCode() );
         result = 37 * result + ( getDefaultAddressId() == null ? 0 : this.getDefaultAddressId().hashCode() );
         result = 37 * result + ( getAddressId() == null ? 0 : this.getAddressId().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getGender() == null ? 0 : this.getGender().hashCode() );
         result = 37 * result + ( getBirthday() == null ? 0 : this.getBirthday().hashCode() );
         result = 37 * result + ( getRoom() == null ? 0 : this.getRoom().hashCode() );
         result = 37 * result + ( getPostalCode() == null ? 0 : this.getPostalCode().hashCode() );
         result = 37 * result + ( getHomeTelephone() == null ? 0 : this.getHomeTelephone().hashCode() );
         result = 37 * result + ( getOfficeTelephone() == null ? 0 : this.getOfficeTelephone().hashCode() );
         result = 37 * result + ( getFax() == null ? 0 : this.getFax().hashCode() );
         result = 37 * result + ( getPager() == null ? 0 : this.getPager().hashCode() );
         result = 37 * result + ( getCell() == null ? 0 : this.getCell().hashCode() );
         result = 37 * result + ( getEmail() == null ? 0 : this.getEmail().hashCode() );
         result = 37 * result + ( getReferenceTypeId() == null ? 0 : this.getReferenceTypeId().hashCode() );
         result = 37 * result + ( getReferenceNo() == null ? 0 : this.getReferenceNo().hashCode() );
         return result;
   }   





}