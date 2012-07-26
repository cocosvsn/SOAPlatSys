package com.cbs.cbsmgr.serviceSimple;

//import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.cbs.cbsmgr.bean.Address;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.bean.Hardware;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.dto.AddressDTO;
import com.cbs.cbsmgr.dto.CustomerDisplayDTO;
import com.cbs.cbsmgr.dto.CustomerProfile;
import com.cbs.cbsmgr.manageriface.IAddressManager;
import com.cbs.cbsmgr.manageriface.ICustomerManager;
//import com.cbs.cbsmgr.manageriface.ISmsDistrictManager;
import com.cbs.cbsmgr.serviceBackground.SrmmgrServiceImpl;
import com.cbs.cbsmgr.serviceimpl.AddressService;
import com.cbs.cbsmgr.serviceimpl.CustomerService;

public class SessionCustomerImpl implements SessionCustomerIface {

	public ICustomerManager customerManager = null;
	public IAddressManager addressManager = null;
//	public ISmsDistrictManager smsDistrictManager = null;
	
	public SessionCustomerImpl()
	{
		customerManager = (ICustomerManager)ApplicationContextHolder.webApplicationContext.getBean("customerManager");
		addressManager = (IAddressManager)ApplicationContextHolder.webApplicationContext.getBean("addressManager");
//		smsDistrictManager = (ISmsDistrictManager)ApplicationContextHolder.webApplicationContext.getBean("smsDistrictManager");
	}
	
	public Long captureCustomer(AddressDTO defaultAddressDTO, Long customerType, String districtId) {
		// 捕获客户
		
		// 获取districtId
//		String districtId = defaultAddressDTO.getDistrictId();
		
		// 创建默认地址
		//AddressService addressService = new AddressService();
		Address address = new Address(defaultAddressDTO);
		address.setCustomerId((long)0);
		Address newDefaultAddress = (Address)addressManager.save(address);
		System.out.println("已保存到Address。");
		
		// 创建客户
		Customer customer = new Customer();
		customer.setCustomerStatusId((long)2);			// 由事件触发，改变
		customer.setCustomerTypeId(customerType);
		customer.setDefaultAddressId(newDefaultAddress.getAddressId());
		customer.setDistrictId(districtId);
		Customer newCustomer = (Customer)customerManager.save(customer);
		System.out.println("已保存到Customer。");
		
		// 设置默认地址的客户编号
		newDefaultAddress.setCustomerId(newCustomer.getCustomerId());
		addressManager.update(address);
		System.out.println("已更新Address。");
		
		// 把客户信息导入到srm
		SrmmgrServiceImpl service = new SrmmgrServiceImpl();
		service.setCustomerInfoFromCbsToSrm(newCustomer, null);
		System.out.println("已保存到Srm。");
		
		return newCustomer.getCustomerId();
	}

	public Long changeCustomerStatus(Long customerId, Long newStatus) {
		// 修改客户状态
		//CustomerService customerService = new CustomerService();
		Customer customer = (Customer)customerManager.getById(customerId.toString());
		customer.setCustomerStatusId(newStatus);
		customerManager.update(customer);
		return customer.getCustomerId();
	}

	public Long changeCustomerType(Long customerId, Long newType) {
		// 修改客户类型
		//CustomerService customerService = new CustomerService();
		Customer customer = (Customer)customerManager.getById(customerId.toString());
		customer.setCustomerTypeId(newType);
		customerManager.update(customer);
		return customer.getCustomerId();
	}
	
	// 修改地址信息
	public Address modifyDefaultAddress(Long addressId, AddressDTO defaultAddressDTO)
	{
		//AddressService addressService = new AddressService();
		Address defaultAddress = (Address)addressManager.getById(addressId.toString());
		defaultAddress.setBirthday(defaultAddressDTO.getBirthday());
		defaultAddress.setName(defaultAddressDTO.getName());
		defaultAddress.setGender(defaultAddressDTO.getGender());
		defaultAddress.setReferenceTypeId(defaultAddressDTO.getReferenceTypeId());
		defaultAddress.setReferenceNo(defaultAddressDTO.getReferenceNo());
		defaultAddress.setCell(defaultAddressDTO.getCell());
		defaultAddress.setHomeTelephone(defaultAddressDTO.getHomeTelephone());
		defaultAddress.setOfficeTelephone(defaultAddressDTO.getOfficeTelephone());
		defaultAddress.setFax(defaultAddressDTO.getFax());
		defaultAddress.setEmail(defaultAddressDTO.getEmail());
		defaultAddress.setPager(defaultAddressDTO.getPager());
		defaultAddress.setBirthday(defaultAddressDTO.getBirthday());
		defaultAddress.setRoom(defaultAddressDTO.getRoom());
		defaultAddress.setPostalCode(defaultAddressDTO.getPostalCode());
		
		addressManager.update(defaultAddress);

		return defaultAddress;
	}
	
	// 查询客户、地址信息
	public CustomerDisplayDTO getCustomerInfoById(Long customerId)
	{
		//CustomerService customerService = new CustomerService();
		CustomerDisplayDTO customerDisplayDTO = null;
		List customers = (List)customerManager.getCustomerDisplayDTOByCustomerId(customerId);
		
		if(customers.size() > 0)
		{
			customerDisplayDTO = new CustomerDisplayDTO();
			Object[] rows = (Object[])customers.get(0);
		
			customerDisplayDTO.setCustomerId(customerId);
			customerDisplayDTO.setCustomerTypeId((Long)rows[0]);
			customerDisplayDTO.setCustomerStatusId((Long)rows[1]);
			customerDisplayDTO.setCaptureDate((Date)rows[2]);
			customerDisplayDTO.setDefaultAddressId((Long)rows[3]);
			customerDisplayDTO.setDistrictId((String)rows[4]);
			customerDisplayDTO.setDistrict((String)rows[5]);
		}
		else
		{
			customerDisplayDTO = null;
		}
		
		return customerDisplayDTO;
	}
	
	public List getAddressesByCustomerId(Long customerId)
	{
		//AddressService addressService = new AddressService();
		List addresses = (List)addressManager.findByProperty("customerId", customerId);
		
		return addresses;
	}
	
	// 根据条件查询客户
	public List getCustomersByCustomerProfile(CustomerProfile customerProfile)
	{
		return null;
	}
	
	// 修改客户的区域
	public void modifySmsDistrictOfCustomerId(Long customerId, String smsDistrictId)
	{
		Customer customer = (Customer)customerManager.getById(customerId.toString());
		customer.setDistrictId(smsDistrictId);
		customerManager.update(customer);
	}

}
