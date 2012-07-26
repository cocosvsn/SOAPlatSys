package com.cbs.cbsmgr.manageriface;

import java.util.List;

import com.cbs.cbsmgr.dto.CustomerDisplayDTO;

public interface ICustomerManager extends IBaseManager {

	public List getCustomerDisplayDTOByCustomerId(Long customerId);
}
