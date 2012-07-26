package com.cbs.cbsmgr.serviceSimple;

import java.util.List;

import com.cbs.cbsmgr.bean.HardwareModel;
import com.cbs.cbsmgr.bean.InvoicePeriod;
import com.cbs.cbsmgr.bean.Price;
import com.cbs.cbsmgr.bean.SmsAccount;
import com.cbs.cbsmgr.bean.SmsDistrict;

public interface ConfigBillingIface {

	
	
	// 创建价格
	public Long createPrice(Price price);
	
	// 修改价格
	public void modifyPrice(Price price);
	
	// 查询所有价格
	public List getAllPrices();
	
	
	// 创建开帐周期
	public Long createInvoicePeriod(InvoicePeriod invoicePeriod);
	
	// 修改开帐周期
	public void modifyInvoicePeriod(InvoicePeriod invoicePeriod);
	
	// 查询开账周期
	public List getAllInvoicePeriods();
	
	
	// 创建分类帐户
	public void createSmsAccount(SmsAccount smsAccount1, SmsAccount smsAccount2);
	
	// 修改分类帐户
	public void modifySmsAccount(SmsAccount smsAccount);
	
	// 查询分类账户
	public List getAllSmsAccount();
	
	
	// 创建硬件型号
	public String createHardwareModel(HardwareModel hardwareModel);
	
	// 修改硬件型号
	public void modifyHardwareModel(HardwareModel hardwareModel);
	
	// 查询硬件型号
	public List getAllHardwareModels();
	
	
	// 创建区域
	public String createDistrict(SmsDistrict smsDistrict);
	
	// 修改区域
	public void modifyDistrict(SmsDistrict smsDistrict);
	
	// 查询区域
	public List getAllDistrict();
	
	
	
	
	
	// 得到所有产品类型
	public List getAllProductTypes();
	
	// 得到所有产品内容分类
	public List getAllContentCategories();
	
	// 得到所有计费方式
	public List getAllBillingTypes();
	
	// 得到所有计费有效期到期后操作
	public List getAllOptions();
}
