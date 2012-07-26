package com.cbs.cbsmgr.serviceSimple;

import java.util.ArrayList;
import java.util.List;

import com.cbs.cbsmgr.bean.HardwareModel;
import com.cbs.cbsmgr.bean.InvoicePeriod;
import com.cbs.cbsmgr.bean.Price;
import com.cbs.cbsmgr.bean.SmsAccount;
import com.cbs.cbsmgr.bean.SmsDistrict;
import com.cbs.cbsmgr.dto.OptionDTO;
import com.cbs.cbsmgr.dto.PriceDisplayDTO;
import com.cbs.cbsmgr.dto.SmsAccountDisplayDTO;
import com.cbs.cbsmgr.manageriface.IBillingTypeManager;
import com.cbs.cbsmgr.manageriface.IContentCategoryManager;
import com.cbs.cbsmgr.manageriface.IHardwareModelManager;
import com.cbs.cbsmgr.manageriface.IInvoicePeriodManager;
import com.cbs.cbsmgr.manageriface.IPriceManager;
import com.cbs.cbsmgr.manageriface.IProductTypeManager;
import com.cbs.cbsmgr.manageriface.ISmsAccountManager;
import com.cbs.cbsmgr.manageriface.ISmsDistrictManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.manageriface.IDictManager;

public class ConfigBillingImpl implements ConfigBillingIface {

	
	public IPriceManager priceManager = null;
	public ISmsAccountManager smsAccountManager = null;
	public IInvoicePeriodManager invoicePeriodManager = null;
	public IHardwareModelManager hardwareModelManager = null;
	public ISmsDistrictManager smsDistrictManager = null;
	public IDictManager dictManager = null;
	
	public IBillingTypeManager billingTypeManager = null;
	public IContentCategoryManager contentCategoryManager = null;
	public IProductTypeManager productTypeManager = null;
	
	public ConfigBillingImpl()
	{
		priceManager = (IPriceManager)ApplicationContextHolder.webApplicationContext.getBean("priceManager");
		smsAccountManager = (ISmsAccountManager)ApplicationContextHolder.webApplicationContext.getBean("smsAccountManager");
		invoicePeriodManager = (IInvoicePeriodManager)ApplicationContextHolder.webApplicationContext.getBean("invoicePeriodManager");
		hardwareModelManager = (IHardwareModelManager)ApplicationContextHolder.webApplicationContext.getBean("hardwareModelManager");
		smsDistrictManager = (ISmsDistrictManager)ApplicationContextHolder.webApplicationContext.getBean("smsDistrictManager");
		dictManager = (IDictManager)ApplicationContextHolder.webApplicationContext.getBean("dictManager");
		
		billingTypeManager = (IBillingTypeManager)ApplicationContextHolder.webApplicationContext.getBean("billingTypeManager");
		contentCategoryManager = (IContentCategoryManager)ApplicationContextHolder.webApplicationContext.getBean("contentCategoryManager");
		productTypeManager = (IProductTypeManager)ApplicationContextHolder.webApplicationContext.getBean("productTypeManager");
	}
	
	
	public String createHardwareModel(HardwareModel hardwareModel) {

		hardwareModel = (HardwareModel)hardwareModelManager.save(hardwareModel);
		return hardwareModel.getHardwareModelId();
	}

	public Long createInvoicePeriod(InvoicePeriod invoicePeriod) {

		invoicePeriod = (InvoicePeriod)invoicePeriodManager.save(invoicePeriod);
		return invoicePeriod.getInvoicePeriodId();
	}

	public Long createPrice(Price price) {

		price = (Price)priceManager.save(price);
		return price.getPriceId();
	}

	public void createSmsAccount(SmsAccount smsAccount1, SmsAccount smsAccount2) {

		smsAccount1.setReverseId((long)0);
		smsAccount2.setReverseId((long)0);
		smsAccount1 = (SmsAccount)smsAccountManager.save(smsAccount1);
		smsAccount2 = (SmsAccount)smsAccountManager.save(smsAccount2);
		
		smsAccount1.setReverseId(smsAccount2.getSmsAccountId());
		smsAccount2.setReverseId(smsAccount1.getSmsAccountId());
		smsAccountManager.update(smsAccount1);
		smsAccountManager.update(smsAccount2);
	}

	public List getAllHardwareModels() {

		return hardwareModelManager.findAll();
	}

	public List getAllInvoicePeriods() {

		return invoicePeriodManager.findAll();
	}

	public List getAllSmsAccount() {

		List smsAccounts = smsAccountManager.findAll();
		List smsAccountDisplayDTOs = new ArrayList();
		
		for(int i = 0; i < smsAccounts.size(); i++)
		{
			SmsAccountDisplayDTO smsAccountDisplayDTO = new SmsAccountDisplayDTO();
			SmsAccount smsAccount = (SmsAccount)smsAccounts.get(i);
			
			smsAccountDisplayDTO.setSmsAccountId(smsAccount.getSmsAccountId());
			smsAccountDisplayDTO.setDescription(smsAccount.getDescription());
			smsAccountDisplayDTO.setDebitCredit(smsAccount.getDebitCredit());
			smsAccountDisplayDTO.setInvoiceLineDescription(smsAccount.getInvoiceLineDescription());
			smsAccountDisplayDTO.setReverseId(smsAccount.getReverseId());
			smsAccountDisplayDTO.setReverseDes(getSmsAccountBySmsAccountId(smsAccount.getSmsAccountId()).getDescription());
			
			smsAccountDisplayDTOs.add(smsAccountDisplayDTO);
		}
		
		return smsAccountDisplayDTOs;
	}

	public List getAllPrices() {

		List priceDisplayDTOs = new ArrayList();
		List prices = priceManager.getAllPrices();
		for(int i = 0; i < prices.size(); i++)
		{
			PriceDisplayDTO priceDisplayDTO = new PriceDisplayDTO();
			Object[] rows = (Object[])prices.get(i);
			
			priceDisplayDTO.setPriceId((Long)rows[0]);
			priceDisplayDTO.setDescription((String)rows[1]);
			priceDisplayDTO.setPrice((Double)rows[2]);
			priceDisplayDTO.setSmsAccountId((Long)rows[3]);
			priceDisplayDTO.setSmsAccount((String)rows[4]);
			
			priceDisplayDTOs.add(priceDisplayDTO);
		}
		return priceDisplayDTOs;
	}

	public void modifyHardwareModel(HardwareModel hardwareModel) {

		hardwareModelManager.update(hardwareModel);
	}

	public void modifyInvoicePeriod(InvoicePeriod invoicePeriod) {

		invoicePeriodManager.update(invoicePeriod);
	}

	public void modifyPrice(Price price) {

		priceManager.update(price);
	}

	public void modifySmsAccount(SmsAccount smsAccount) {

		smsAccountManager.update(smsAccount);
	}
	
	// 创建区域
	public String createDistrict(SmsDistrict smsDistrict)
	{
		smsDistrict = (SmsDistrict)smsDistrictManager.save(smsDistrict);
		return smsDistrict.getDistrictId();
	}
	
	// 修改区域
	public void modifyDistrict(SmsDistrict smsDistrict)
	{
		smsDistrictManager.update(smsDistrict);
	}
	
	// 查询区域
	public List getAllDistrict()
	{
		List districts = smsDistrictManager.findAll();
		return districts;
	}
	
	
	// 根据分类帐户ID得到分类帐户
	public SmsAccount getSmsAccountBySmsAccountId(Long smsAccountId)
	{
		SmsAccount smsAccount = (SmsAccount)smsAccountManager.getById(smsAccountId.toString());
		return smsAccount;
	}
	
	
	
	
	
	
	// 得到所有产品类型
	public List getAllProductTypes()
	{
		return productTypeManager.findAll();
	}
	
	// 得到所有产品内容分类
	public List getAllContentCategories()
	{
		return contentCategoryManager.findAll();
	}
	
	// 得到所有计费方式
	public List getAllBillingTypes()
	{
		return billingTypeManager.findAll();
	}
	
	// 得到所有计费有效期到期后操作
	public List getAllOptions()
	{
		// 计费有效期到期后操作
		// 1 - 继续原有产品
		// 2 - 停止原有产品
		// 3 - 切换新产品
		
		List options = new ArrayList();
		OptionDTO optionDTO1 = new OptionDTO();
		optionDTO1.setOptionId((long)1);
		optionDTO1.setOption("继续原有产品");
		OptionDTO optionDTO2 = new OptionDTO();
		optionDTO2.setOptionId((long)2);
		optionDTO2.setOption("停止原有产品");
		OptionDTO optionDTO3 = new OptionDTO();
		optionDTO3.setOptionId((long)3);
		optionDTO3.setOption("切换新产品");
		
		options.add(optionDTO1);
		options.add(optionDTO2);
		options.add(optionDTO3);
		return options;
	}
}
