package com.cbs.cbsmgr.serviceimpl;

import java.util.List;

import com.cbs.cbsmgr.bean.Address;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.dto.AddressDTO;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.ICustomerManager;
import com.cbs.cbsmgr.serviceiface.IAddressService;
import com.cbs.cbsmgr.serviceiface.ICustomerService;

public class CustomerService implements ICustomerService {

	public ICustomerManager customerManager = null;
	
	public CustomerService()
	{
		customerManager = (ICustomerManager)ApplicationContextHolder.webApplicationContext.getBean("customerManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		customerManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		customerManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = customerManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = customerManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = customerManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = customerManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = customerManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		customerManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		customerManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		customerManager.update(object);
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteByIDRetAll(java.lang.String)
	 */
	public List deleteByIDRetAll(String pkid) {
		this.deleteById(pkid);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteRetAll(java.lang.Object[])
	 */
	public List deleteRetAll(Object[] object) {
		this.delete(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object)
	 */
	public List saveRetAll(Object object) {
		this.save(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object[])
	 */
	public List saveRetAll(Object[] object) {
		this.save(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object)
	 */
	public List updateRetAll(Object object) {
		this.update(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object[])
	 */
	public List updateRetAll(Object[] object) {
		this.update(object);
		List list = this.findAll();
		return list;
	}

	public List findbyExample(Object exampleentity) {
		List list = customerManager.findbyExample(exampleentity);
		return list;
	}

	public Customer createCustomer(AddressDTO defaultAddressDTO, Long customerType)
	{
//	    if (defaultAddressDTO == null)
//	        throw new CBSException(ErrorNumber.CUSTOMER_DEFAULTADDRESSDTO_IS_NULL);
//	      if (!Validator.isIdValid(customerType))
//	        throw new CBSException(ErrorNumber.CUSTOMER_TYPE_IS_INVALID);
//
//	      AddressService addressService = new AddressService();
//	      Address defaultAddress =
//	          addressService.createAddress(defaultAddressDTO,Address.ADDRESS_TYPE_DEFAULT);
//	      CustomerHome customerHome = null ;
//	      try {
//	        customerHome = (CustomerHome) BusinessObjectFactory.getBusinessObjectHome(
//	            "Customer");
//	      }
//	      catch (CBSException e) {
//	        throw new EJBException(e);
//	      }
//	      try {
//	        Customer customer = customerHome.create(defaultAddress, customerType);
//	        defaultAddress.setCustomerId(customer.getId());
//	        return customer;
//	      }
//	      catch (CreateException ex) {
//	        throw new CBSException(ErrorNumber.CUSTOMER_CREATE_ERROR);
//	      }
	    
		// Edited by Andy at 20090810
		// ����Ĭ�ϵ�ַ
		AddressService addressService = new AddressService();
		Address address = new Address(defaultAddressDTO);
		Address newDefaultAddress = (Address)addressService.save(address);
		
		// �����ͻ�
		Customer customer = new Customer();
		customer.setCustomerStatusId((long)0);			// ���¼��������ı�
		customer.setCustomerTypeId(customerType);
		customer.setDefaultAddressId(newDefaultAddress.getAddressId());
		Customer newCustomer = (Customer)customerManager.save(customer);
		
		// ����Ĭ�ϵ�ַ�Ŀͻ����
		newDefaultAddress.setCustomerId(newCustomer.getCustomerId());
		newDefaultAddress = (Address)addressService.save(address);
		
		return newCustomer;
	}
}
