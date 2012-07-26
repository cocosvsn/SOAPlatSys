package com.srmmgr.serviceiface;

import java.util.List;

import com.soaplat.sysmgr.common.IBaseService;

/**
 * Title 		:the Interface IStbCustomerService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IStbCustomerService extends IBaseService {
	public int saveStbCustomer(Object[] customlist);
	public int updateStbCustomer(Object[] cutomlist);
	public int deleteStbCustomer(Object[] customlist);
	public Object updateObject(Object object);
}
