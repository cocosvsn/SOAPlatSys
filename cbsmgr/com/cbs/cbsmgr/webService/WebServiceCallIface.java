package com.cbs.cbsmgr.webService;

import java.util.List;

import javax.jws.WebService;

import com.srmmgr.webservice.ProgProductRel;
import com.srmmgr.webservice.StbCustomer;


public interface WebServiceCallIface {

	// 调用webService
	
	// 保存（创建）客户
	public int saveStbCustomers(List<StbCustomer> stbCustomers);
	
	// 修改客户
	public int updateStbCustomers(List<StbCustomer> stbCustomers);
	
	// 删除客户
	public int deleteStbCustomers(List<StbCustomer> stbCustomers);
	
	// 查询客户
	public List<StbCustomer> getAllStbCustomer();
	
	
	// 保存（创建）节目产品关系
	public int saveProgProductRels(List<ProgProductRel> progProductRels);
	
	// 修改节目产品关系
	public int updateProgProductRels(List<ProgProductRel> progProductRels);
	
	// 删除节目产品关系
	public int deleteProgProductRels(List<ProgProductRel> progProductRels);
	
	// 查询节目产品关系
	public List<ProgProductRel> getAllProgProductRels();
}
