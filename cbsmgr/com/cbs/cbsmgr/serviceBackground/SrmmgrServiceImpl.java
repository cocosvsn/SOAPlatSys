package com.cbs.cbsmgr.serviceBackground;

import java.util.ArrayList;
import java.util.List;

//import com.cbs.cbsmgr.bean.ConsumeList;
//import com.cbs.cbsmgr.bean.StbCustomer;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.bean.Hardware;
import com.srmmgr.bean.ConsumeList;
import com.srmmgr.bean.StbCustomer;
import com.srmmgr.serviceimpl.ConsumeListService;
import com.srmmgr.serviceimpl.StbCustomerService;

public class SrmmgrServiceImpl implements SrmmgrServiceIface 
{

	public SrmmgrServiceImpl()
	{
		
	}
	
	
	public List exportCustomerInfoFromCbsmgrToSrmmgr() {
		// TODO Auto-generated method stub

		ConsumeListService service = new ConsumeListService();
		List consumes = service.ExportConsumeList();

		return consumes;
	}

	public void getConsumeListFromSrmmgr() {
		// TODO Auto-generated method stub

		StbCustomerService service = new StbCustomerService();
		
		Object[] stbCustomers = new Object[1];
		StbCustomer stbCustomer = new StbCustomer();
		
		stbCustomer.setSmscstid("91");
		stbCustomer.setUserid("91");
		stbCustomer.setPwd("91");
		stbCustomer.setDistrictid("1");
		
		stbCustomers[0] = stbCustomer;
//		service.importStbCustomer(stbCustomers);
	}
	
	public void setCustomerInfoFromCbsToSrm(Customer customer, Hardware hardware)
	{
		StbCustomerService service = new StbCustomerService();
		
		Object[] stbCustomers = new Object[1];
		StbCustomer stbCustomer = new StbCustomer();
		
		stbCustomer.setSmscstid(customer.getCustomerId().toString());
		stbCustomer.setDistrictid(customer.getDistrictId());
		
		if(hardware != null)
		{
			stbCustomer.setUserid(hardware.getUserName());
			stbCustomer.setPwd(hardware.getPassword());
			stbCustomer.setStbid(hardware.getSerialNo());
			stbCustomer.setStbip(hardware.getIp());
			stbCustomer.setStbmac(hardware.getMac());
			stbCustomer.setSmartcardid(hardware.getCardNo());
		}

		stbCustomers[0] = stbCustomer;
//		service.importStbCustomer(stbCustomers);
	}

}
