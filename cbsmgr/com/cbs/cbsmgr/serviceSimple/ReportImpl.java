package com.cbs.cbsmgr.serviceSimple;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cbs.cbsmgr.bean.Address;
import com.cbs.cbsmgr.bean.BillingType;
import com.cbs.cbsmgr.bean.FinancialTransaction;
import com.cbs.cbsmgr.bean.Product;
import com.cbs.cbsmgr.bean.ProductCategory;
import com.cbs.cbsmgr.bean.SmsAccount;
import com.cbs.cbsmgr.bean.VodHistory;
import com.cbs.cbsmgr.dto.CbsResultDto;
import com.cbs.cbsmgr.manageriface.IBillingTypeManager;
import com.cbs.cbsmgr.manageriface.IFinancialTransactionManager;
import com.cbs.cbsmgr.manageriface.IProductCategoryManager;
import com.cbs.cbsmgr.manageriface.IProductManager;
import com.cbs.cbsmgr.manageriface.ISmsAccountManager;
import com.cbs.cbsmgr.manageriface.IVodHistoryManager;
import com.cbs.cbsmgr.manageriface.IVodHistoryTempManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class ReportImpl implements ReportIface {

	private static final Logger cmsLog = Logger.getLogger("Cms");
	
	private IFinancialTransactionManager financialTransactionManager = null;
	private IVodHistoryManager vodHistoryManager = null;
	private IVodHistoryTempManager vodHistoryTempManager = null;
	private IProductCategoryManager productCategoryManager = null;
	private IProductManager productManager = null;
	private ISmsAccountManager smsAccountManager = null;
	private IBillingTypeManager billingTypeManager = null;

	public ReportImpl()
	{
		financialTransactionManager = (IFinancialTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("financialTransactionManager");
		vodHistoryManager = (IVodHistoryManager)ApplicationContextHolder.webApplicationContext.getBean("vodHistoryManager");
		vodHistoryTempManager = (IVodHistoryTempManager)ApplicationContextHolder.webApplicationContext.getBean("vodHistoryTempManager");
		productCategoryManager = (IProductCategoryManager)ApplicationContextHolder.webApplicationContext.getBean("productCategoryManager");
		productManager = (IProductManager)ApplicationContextHolder.webApplicationContext.getBean("productManager");
		smsAccountManager = (ISmsAccountManager)ApplicationContextHolder.webApplicationContext.getBean("smsAccountManager");
		billingTypeManager = (IBillingTypeManager)ApplicationContextHolder.webApplicationContext.getBean("billingTypeManager");

	}
	
	// 20100322 19:49
	// 财务报表
	public CbsResultDto reportFTs(
			Long customerId,			
			String createDateFrom,			// 格式：yyyy-MM-dd
			String createDateTo				// 格式：yyyy-MM-dd
			)
	{
		cmsLog.info("Cbs -> ReportImpl -> reportFTs...");
		CbsResultDto cbsResultDto = new CbsResultDto();
		
		List retlist = new ArrayList();
		List resultlist = new ArrayList();
		List summarylist = new ArrayList();
		
		
		// 返回：
		// List<Object[]> list
		//		(FinancialTransaction)Object[0]
		//		(Address)Object[1]
		List list = financialTransactionManager.reportFTs(
				customerId, 
				createDateFrom, 
				createDateTo
				);
		
		if(list != null && list.size() > 0)
		{
			for(int i = 0; i < list.size(); i++)
			{
				Object[] rows = (Object[])list.get(i);
				FinancialTransaction ft = (FinancialTransaction)rows[0];
				Address ad = (Address)rows[1];
				Product pt = null;
				ProductCategory pc = null;
				SmsAccount sa = null;
				
				if(ft.getProductId() > 0)
				{
					pt = (Product)productManager.getById(ft.getProductId().toString());
					pc = (ProductCategory)productCategoryManager.getById(pt.getProductCategoryId().toString());
				}
				else
				{
//					pt = new Product();
//					pt.setProductId((long)0);
				}
				sa = (SmsAccount)smsAccountManager.getById(ft.getSmsAccountId().toString());
				
				Object[] newrows = new Object[5];
				newrows[0] = ft;
				newrows[1] = ad;
				newrows[2] = pt;
				newrows[3] = pc;
				newrows[4] = sa;
				
				resultlist.add(newrows);
				
				// 计算统计，费用总计
				// 判断返回中，是否已经存在改smsaccount
				Double amount = ft.getAmount();
				if(summarylist.size() <= 0)
				{					
					Object[] summaryrows = new Object[4];
					summaryrows[0] = sa;
					summaryrows[1] = amount;
					
					summarylist.add(summaryrows);
				}
				else
				{
					boolean exist = false;
					for(int j = 0; j < summarylist.size(); j++)
					{
						Object[] oldrows = (Object[])summarylist.get(j);
						SmsAccount oldsa = (SmsAccount)oldrows[0];
						Double oldamount = (Double)oldrows[1];
						
						if((long)oldsa.getSmsAccountId() == (long)sa.getSmsAccountId())
						{
							oldamount += amount;
							
							Object[] summaryrows = new Object[4];
							summaryrows[0] = oldsa;
							summaryrows[1] = oldamount;
							
							summarylist.set(j, summaryrows);
							exist = true;
							break;
						}
					}
					if(exist == false)
					{
						Object[] summaryrows = new Object[4];
						summaryrows[0] = sa;
						summaryrows[1] = amount;
						
						summarylist.add(summaryrows);
					}
				}
			}
		}
		
		retlist.add(resultlist);
		retlist.add(summarylist);
		cbsResultDto.setResultObject(retlist);
		
		cmsLog.info("Cbs -> ReportImpl -> reportFTs returns.");
		return cbsResultDto;
	}
	
	// 20100322 19:49
	// 财务报表
	public CbsResultDto reportSummaryFTs(
			Long customerId,			
			String createDateFrom,			// 格式：yyyy-MM-dd
			String createDateTo				// 格式：yyyy-MM-dd
			)
	{
		cmsLog.info("Cbs -> ReportImpl -> reportFTs...");
		CbsResultDto cbsResultDto = new CbsResultDto();
		
		List retlist = new ArrayList();
		List resultlist = new ArrayList();
		List summarylist = new ArrayList();
		
		// 返回：
		// List<Object[]> list
		//		(FinancialTransaction)Object[0]
		//		(Address)Object[1]
		List list = financialTransactionManager.reportFTs(
				customerId, 
				createDateFrom, 
				createDateTo
				);
		
		
		if(list != null && list.size() > 0)
		{
			for(int i = 0; i < list.size(); i++)
			{
				Object[] rows = (Object[])list.get(i);
				FinancialTransaction ft = (FinancialTransaction)rows[0];
				Address ad = (Address)rows[1];
				
				if(resultlist.size() <= 0)
				{
					Object[] newrows = new Object[2];
					newrows[0] = ft;
					newrows[1] = ad;
					
					resultlist.add(newrows);
				}
				else
				{
					Object[] oldrows = (Object[])resultlist.get(resultlist.size() - 1);
					FinancialTransaction oldft = (FinancialTransaction)oldrows[0];
					Address oldad = (Address)oldrows[1];
					
					if((long)ft.getCustomerId() == (long)oldft.getCustomerId())
					{
						Double amount = oldft.getAmount() + ft.getAmount();
						oldft.setAmount(amount);
						
						Object[] newrows = new Object[2];
						newrows[0] = oldft;
						newrows[1] = oldad;
						
						resultlist.set(resultlist.size() - 1, newrows);
					}
					else
					{
						Object[] newrows = new Object[2];
						newrows[0] = ft;
						newrows[1] = ad;
						
						resultlist.add(newrows);
					}
				}
			}
		}
		
		retlist.add(resultlist);
		retlist.add(summarylist);
		cbsResultDto.setResultObject(retlist);
		
		cmsLog.info("Cbs -> ReportImpl -> reportFTs returns.");
		return cbsResultDto;
	}
	
	// 20100322 20:16
	// 点播历史报表
	public CbsResultDto reportVodHistories(
			Long customerId,				// 
			String vodDateFrom,				// 格式：yyyy-MM-dd
			String vodDateTo				// 格式：yyyy-MM-dd
			)
	{
		cmsLog.info("Cbs -> ReportImpl -> reportVodHistories...");
		CbsResultDto cbsResultDto = new CbsResultDto();
		
		List retlist = new ArrayList();
		List resultlist = new ArrayList();
		List summarylist = new ArrayList();
		// 返回：
		// List<Object> list
		//		(VodHistory)Object[0]
		//		(Address)Object[1]
		//		(ProductCategory)Object[2]
		List list = vodHistoryManager.reportVodHistories(
				customerId, 
				vodDateFrom, 
				vodDateTo
				);
		
		if(list != null && list.size() > 0)
		{
			for(int i = 0; i < list.size(); i++)
			{
				Object[] rows = (Object[])list.get(i);
				VodHistory vh = (VodHistory)rows[0];
				Address ad = (Address)rows[1];
				ProductCategory pc = (ProductCategory)rows[2];
				BillingType billingType = (BillingType)billingTypeManager.getById(pc.getBillingTypeId());
				
				Object[] newrows = new Object[4];
				newrows[0] = vh;
				newrows[1] = ad;
				newrows[2] = pc;
				newrows[3] = billingType;
				resultlist.add(newrows);
				
				// 计算统计，费用总计
				// 判断返回中，是否已经存在改smsaccount
				int count = 0;
				if(summarylist.size() <= 0)
				{					
					count++;
					
					Object[] summaryrows = new Object[4];
					summaryrows[0] = billingType;
					summaryrows[1] = count;
					
					summarylist.add(summaryrows);
				}
				else
				{
					boolean exist = false;
					for(int j = 0; j < summarylist.size(); j++)
					{
						Object[] oldrows = (Object[])summarylist.get(j);
						BillingType oldBillingType = (BillingType)oldrows[0];
						int oldcount = (Integer)oldrows[1];
						
						if(oldBillingType.getBillingTypeId().equalsIgnoreCase(billingType.getBillingTypeId()))
						{
							oldcount++;
							
							Object[] summaryrows = new Object[4];
							summaryrows[0] = oldBillingType;
							summaryrows[1] = oldcount;
							
							summarylist.set(j, summaryrows);
							exist = true;
							break;
						}
					}
					if(exist == false)
					{
						count++;
						
						Object[] summaryrows = new Object[4];
						summaryrows[0] = billingType;
						summaryrows[1] = count;
						
						summarylist.add(summaryrows);
					}
				}
			}
		}
		
		retlist.add(resultlist);
		retlist.add(summarylist);
		cbsResultDto.setResultObject(retlist);
		
		cmsLog.info("Cbs -> ReportImpl -> reportVodHistories returns.");
		return cbsResultDto;
	}

}
