package com.cbs.cbsmgr.dto;

import java.util.Date;

public class CustomerProfile {

	Long customerIdBegin;
	Long customerIdEnd;
	Long customerTypeId;
	Long customerStatusId;
	
	String name;
	String gender;
	Date birthday;
	Long refTypeId;
	String refNo;
	String homeTel;
	String fax;
	String pager;
	String officeTel;
	String cell;
	String email;
	String address;
	String postCode;
	
	CustomerProfile()
	{
		customerIdBegin = null;
		customerIdEnd = null;
		customerTypeId = null;
		customerStatusId = null;
		
		name = null;
		gender = null;
		birthday = null;
		refTypeId = null;
		refNo = null;
		homeTel = null;
		fax = null;
		pager = null;
		officeTel = null;
		cell = null;
		email = null;
		address = null;
		postCode = null;
	}
	
	public Long getCustomerIdBegin() {
		return customerIdBegin;
	}
	public void setCustomerIdBegin(Long customerIdBegin) {
		this.customerIdBegin = customerIdBegin;
	}
	public Long getCustomerIdEnd() {
		return customerIdEnd;
	}
	public void setCustomerIdEnd(Long customerIdEnd) {
		this.customerIdEnd = customerIdEnd;
	}
	public Long getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public Long getCustomerStatusId() {
		return customerStatusId;
	}
	public void setCustomerStatusId(Long customerStatusId) {
		this.customerStatusId = customerStatusId;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Long getRefTypeId() {
		return refTypeId;
	}
	public void setRefTypeId(Long refTypeId) {
		this.refTypeId = refTypeId;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getHomeTel() {
		return homeTel;
	}
	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
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
	public String getOfficeTel() {
		return officeTel;
	}
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
}
