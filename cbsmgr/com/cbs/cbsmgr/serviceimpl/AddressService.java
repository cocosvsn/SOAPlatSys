package com.cbs.cbsmgr.serviceimpl;

import java.util.Collection;
import java.util.List;

import com.cbs.cbsmgr.bean.Address;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IAddressManager;
import com.cbs.cbsmgr.serviceiface.IAddressService;
import com.cbs.cbsmgr.dto.AddressDTO;

public class AddressService implements IAddressService {

	public IAddressManager addressManager = null;
	
	public AddressService()
	{
		addressManager = (IAddressManager)ApplicationContextHolder.webApplicationContext.getBean("addressManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		addressManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		addressManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = addressManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = addressManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = addressManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = addressManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = addressManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		addressManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		addressManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		addressManager.update(object);
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
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object[])
	 */
	public List saveRetAll(Object[] object) {
		this.save(object);
		List list=this.findAll();
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
		List list = addressManager.findbyExample(exampleentity);
		return list;
	}
	
	public Collection findByCustomer(Long customerId)
	{
		return null;
	}
	
	public Address getCustomerDefaultAddress(Long customerId)/* throws CBSException*/ {
		
		// �õ��ͻ���Ĭ�ϵ�ַ
		
//		if (!Validator.isIdValid(customerId))
//		      throw new CBSException(ErrorNumber.ADDRESS_CUSTOMER_ID_IS_INVALID);
//
//		    Customer customer = null;
//		    try {
//		      customer = (Customer) BusinessObjectFactory.getBusinessObjectById(
//		          "Customer", customerId);
//		    }
//		    catch (CBSException ex) {
//		      throw new CBSException(ErrorNumber.CUSTOMER_FINDBYPK_ERROR);
//		    }
//		    Long addressId = customer.getDefaultAddressId();
//		    Address defaultAddress = null;
//		    try {
//		      defaultAddress = (Address) BusinessObjectFactory.getBusinessObjectById(
//		          "Address", addressId);
//		    }
//		    catch (CBSException e) {
//		      throw new CBSException(ErrorNumber.ADDRESS_FINDBYPK_ERROR);
//		    }
//		    return defaultAddress;
		
		// �жϲ���Ϸ���
		
		// ��ѯ�õ��ͻ���Ĭ�ϵ�ַ
		Address defaultAddress = (Address)addressManager.findByProperty("customerId", customerId);
		
		return defaultAddress;
	}
	public Address findCustomerDefaultAddress(Long customerId)
	{
		// ��ѯ�ͻ���Ĭ�ϵ�ַ
		return getCustomerDefaultAddress(customerId);
	}
}
