package com.cbs.cbsmgr.serviceBackground;

import java.util.List;

import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.bean.Hardware;

public interface SrmmgrServiceIface {

	// 导入客户信息，到srmmgr
	public List exportCustomerInfoFromCbsmgrToSrmmgr();
	
	
	// 导入客户信息，从cbs到srm
	public void setCustomerInfoFromCbsToSrm(Customer customer, Hardware hardware);
	
	 
}
