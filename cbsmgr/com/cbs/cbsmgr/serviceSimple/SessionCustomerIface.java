package com.cbs.cbsmgr.serviceSimple;

import java.util.List;

import com.cbs.cbsmgr.bean.Address;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.dto.AddressDTO;
import com.cbs.cbsmgr.dto.CustomerDisplayDTO;
import com.cbs.cbsmgr.dto.CustomerProfile;

public interface SessionCustomerIface {
	//public void modifyCustomerInfo();
	
	// 捕获客户
	public Long captureCustomer(AddressDTO defaultAddressDTO, Long customerType, String districtId);
	
	// 修改客户类型
	public Long changeCustomerType(Long customerId, Long newType);
	
	// 修改客户状态
	public Long changeCustomerStatus(Long customerId, Long newStatus);
	
	// 修改默认地址信息
	public Address modifyDefaultAddress(Long addressId, AddressDTO defaultAddressDTO);
	
	// 查询客户信息
	public CustomerDisplayDTO getCustomerInfoById(Long customerId);
	
	// 根据客户Id得到地址列表
	public List getAddressesByCustomerId(Long customerId);
	
	// 根据条件查询客户
	public List getCustomersByCustomerProfile(CustomerProfile customerProfile);
	
	// 修改客户的区域
	public void modifySmsDistrictOfCustomerId(Long customerId, String smsDistrictId);
}
