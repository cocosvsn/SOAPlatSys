package com.cbs.cbsmgr.serviceiface;

import java.util.Collection;

import com.cbs.cbsmgr.bean.Address;
import com.cbs.cbsmgr.dto.AddressDTO;

public interface IAddressService extends IBaseService {

//	public Collection findByCustomer(Long customerId)/* throws FinderException*/;
	public Address findCustomerDefaultAddress(Long customerId);
}
