package com.cbs.cbsmgr.serviceiface;

import com.cbs.cbsmgr.bean.Address;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.dto.AddressDTO;

public interface ICustomerService extends IBaseService {

	public Customer createCustomer(AddressDTO defaultAddressDTO, Long customerType);
}
