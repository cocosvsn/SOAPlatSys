package com.cbs.cbsmgr.dto;

/**
 * <p>Title: CONVERGENCE BUSINESS SYSTEM</p>
 * <p>Module: CBS CORE MODULE</p>
 * <p>Description: </p>
 * <p>Company: Road To Broadband Co., Ltd.</p>
 * @author not attributable
 *
 */
import java.io.Serializable;
public class AddressDTO implements Serializable {
  private String name;
  private String gender;
  private java.util.Date birthday;
  private String room;
  private String postalCode;
  private String homeTelephone;
  private String officeTelephone;
  private String fax;
  private String pager;
  private String cell;
  private String email;
  private String referenceNo;
  private Long referenceTypeId;
//  private String districtId;		// 
  
  public AddressDTO() {

    //Initialize the NOT-NULL field when instancing.
    this.setReferenceTypeId(new Long(0));

  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getGender() {
    return gender;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }
  public java.util.Date getBirthday() {
    return birthday;
  }
  public void setBirthday(java.util.Date birthday) {
    this.birthday = birthday;
  }
  public String getRoom() {
    return room;
  }
  public void setRoom(String room) {
    this.room = room;
  }
  public String getPostalCode() {
    return postalCode;
  }
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }
  public String getHomeTelephone() {
    return homeTelephone;
  }
  public void setHomeTelephone(String homeTelephone) {
    this.homeTelephone = homeTelephone;
  }
  public String getOfficeTelephone() {
    return officeTelephone;
  }
  public void setOfficeTelephone(String officeTelephone) {
    this.officeTelephone = officeTelephone;
  }
  public String getFax() {
    return fax;
  }
  public void setFax(String fax) {
    this.fax = fax;
  }
  public String getPager() {
    return pager;
  }
  public void setPager(String pager) {
    this.pager = pager;
  }
  public String getCell() {
    return cell;
  }
  public void setCell(String cell) {
    this.cell = cell;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public Long getReferenceTypeId() {
    return referenceTypeId;
  }
  public void setReferenceTypeId(Long referenceTypeId) {
    this.referenceTypeId = referenceTypeId;
  }
  public String getReferenceNo() {
    return referenceNo;
  }
  public void setReferenceNo(String referenceNo) {
    this.referenceNo = referenceNo;
  }


}