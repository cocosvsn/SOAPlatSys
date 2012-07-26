package com.cbs.cbsmgr.serviceBackground;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.bean.FinancialTransaction;
import com.cbs.cbsmgr.bean.Invoice;
import com.cbs.cbsmgr.bean.PackageItem;
import com.cbs.cbsmgr.bean.Price;
import com.cbs.cbsmgr.bean.Product;
import com.cbs.cbsmgr.bean.ProductCategory;
import com.cbs.cbsmgr.bean.VodFlow;
import com.cbs.cbsmgr.bean.VodHistory;
import com.cbs.cbsmgr.bean.VodHistoryTemp;
import com.cbs.cbsmgr.dto.CbsResultDto;
import com.cbs.cbsmgr.dto.ProductDTO;
import com.cbs.cbsmgr.manageriface.IAccountManager;
import com.cbs.cbsmgr.manageriface.ICbsTransactionManager;
import com.cbs.cbsmgr.manageriface.ICustomerManager;
import com.cbs.cbsmgr.manageriface.IFinancialTransactionManager;
import com.cbs.cbsmgr.manageriface.IInvoiceManager;
import com.cbs.cbsmgr.manageriface.IPriceManager;
import com.cbs.cbsmgr.manageriface.IProductCategoryManager;
import com.cbs.cbsmgr.manageriface.IProductManager;
import com.cbs.cbsmgr.manageriface.IVodHistoryManager;
import com.cbs.cbsmgr.manageriface.IVodHistoryTempManager;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.sysmgr.common.ApplicationContextHolder;



/**
 * Title 		:the Class MonthlyBillingImpl.
 * Description	:生成每月固定费用
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-09-08
 * 
 * @author		:cbsmgr Group (Andy Han)
 * @version		:1.0
 */


public class MonthlyBillingImpl implements MonthlyBillingIface {

	public ICustomerManager customerManager = null;
	public IAccountManager accountManager = null;
	public IFinancialTransactionManager financialTransactionManager = null;
	public IProductManager productManager = null;
	public IProductCategoryManager productCategoryManager = null;
	public IPriceManager priceManager = null;
	public IVodHistoryManager vodHistoryManager = null;
	public IVodHistoryTempManager vodHistoryTempManager = null;
	public IInvoiceManager invoiceManager = null;
	
	public ICbsTransactionManager cbsTransactionManager = null;
	
	public static final Logger cmsLog = Logger.getLogger("Cms");
	private static FileOperationImpl fileoper = null;
	private static Date lastDate = null;
	private static String monthlyDay = "25";		// 每月执行服务的日期（日）
	private static String invoiceDueDay = "15";		// 生成账单的付费日期（日）
	
	public MonthlyBillingImpl()
	{
		fileoper = new FileOperationImpl();
		
		
		customerManager = (ICustomerManager)ApplicationContextHolder.webApplicationContext.getBean("customerManager");
		accountManager = (IAccountManager)ApplicationContextHolder.webApplicationContext.getBean("accountManager");
		financialTransactionManager = (IFinancialTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("financialTransactionManager");
		productManager = (IProductManager)ApplicationContextHolder.webApplicationContext.getBean("productManager");
		productCategoryManager = (IProductCategoryManager)ApplicationContextHolder.webApplicationContext.getBean("productCategoryManager");
		priceManager = (IPriceManager)ApplicationContextHolder.webApplicationContext.getBean("priceManager");
		vodHistoryManager = (IVodHistoryManager)ApplicationContextHolder.webApplicationContext.getBean("vodHistoryManager");
		vodHistoryTempManager = (IVodHistoryTempManager)ApplicationContextHolder.webApplicationContext.getBean("vodHistoryTempManager");
		invoiceManager = (IInvoiceManager)ApplicationContextHolder.webApplicationContext.getBean("invoiceManager");
		
		cbsTransactionManager = (ICbsTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("cbsTransactionManager");
	}
	
	// 生成每月套餐费用
	public void generateMonthlyFeeByAccountId(Long accountId) 
	{
		// 查询Account，判断Account是否是正常状态。如果是销户状态，不生成费用
		Account account = (Account)accountManager.getById(accountId.toString());
		if(account.getAccountStatusId() != 1)
		{
			// 帐户状态不是“正常”，返回，不生成费用
			return;
		}
		
		
		// 查询客户，判断客户是否是正常状态。如果是销户状态，不生成费用
		Customer customer = (Customer)customerManager.getById(account.getCustomerId().toString());
		if(customer.getCustomerStatusId() != 2)
		{
			// 客户状态不是“正常”，返回，不生成费用
			return;
		}
		
		
		// 查询Account下的Products，判断Product是否是正常状态，如果是其他状态，不生成费用
		List products = (List)productManager.findByProperty("accountId", accountId);
		
		Iterator iProducts = products.iterator();
		for (int i = 0; i < products.size(); i++)
		{
			// 查询每个正常状态Product的Price，生成财务交易，分类帐户类型为Price对应的
			Product product = (Product) iProducts.next();
			ProductCategory productCategory = (ProductCategory)productCategoryManager.getById(product.getProductCategoryId().toString());
			Price price = (Price)priceManager.getById(productCategory.getPriceId().toString());
			
			
			if(product.getProductStatusId() == 1)
			{
				// 判断改产品的费用是否已经生成
				
				// 如果尚未生成，则生成费用到财务交易
				FinancialTransaction ft = new FinancialTransaction();
				ft.setAccountId(accountId);
				ft.setAmount(price.getPrice());
				ft.setCreateDate(new Date());
				ft.setCustomerId(customer.getCustomerId());
				ft.setFtStatusId((long)1);
				ft.setInvoiceId((long)0);
				ft.setPeriodFrom(null);
				ft.setPeriodTo(null);
				ft.setProductId(product.getProductId());
				ft.setSmsAccountId(price.getSmsAccountId());
				
				ft = (FinancialTransaction)financialTransactionManager.save(ft);
			}
		}
	}

	
	
	
	
	
	// 新需求，新开发
	// -------------------------------------- 20091223 ---------------------------------------------------------------
	// 关于oracle分页查询的说明：
	// oracle的分页查询是3层嵌套的：先排序、后取指定数量的记录
	// 例如：select * from (select * from (select * from user order by userName) where rownum <= 20) where rownum >=10
	// 而非：select * from user where rownum <= 20 and rownum > 10 order by userName
	
	// 汇总状态为未处理的点播历史到财务交易，每月固定时间，执行一次
	public CbsResultDto collectVodHistoriesToFt(
//			Date date
			)
	{
		cmsLog.info("Cbs -> MonthlyBillingImpl -> collectVodHistoriesToFt...");
		CbsResultDto cbsResultDto = new CbsResultDto();

		// 流程：
		// 分页查询VodHistory表中记录，条件：未处理状态，排序：客户，点播日期
		// 每个客户在同一个月内的点播历史，生成一个财务交易记录
		// 每页最后一批记录（同一客户同一月份），需要结合下一页记录一起处理
		
		int firstResult = 0;
		int maxResults = 200;
		Long curCustomerId = Long.valueOf(0);		// 当前准备生成财务交易的客户ID
		Long curAccountId = Long.valueOf(0);		// 当前准备生成财务交易的账户ID
		List vodHistoryTemps = new ArrayList();		// 分页查询得到的点播历史
		List sameFtVhf = new ArrayList();			// 准备生成财务交易的点播历史列表
		do
		{
			// 分页查询得到点播历史
			cmsLog.info("分页查询未处理的点播历史（临时）...从" + firstResult + "条开始，共" + maxResults + "条");
			vodHistoryTemps = vodHistoryTempManager.getNotDealedVodHistoryTempsWithCount(firstResult, maxResults);
			cmsLog.info("得到未处理的点播历史（临时），共" + vodHistoryTemps.size() + "条记录。");
			
			firstResult = firstResult + maxResults;
			
			if(vodHistoryTemps.size() > 0)
			{
				for(int i = 0; i < vodHistoryTemps.size(); i++)
				{
					VodHistoryTemp vodHistoryTemp = (VodHistoryTemp)vodHistoryTemps.get(i);
					
					cmsLog.info("处理第" + (i+1) + "条记录...");
					cmsLog.info("客户ID：" + vodHistoryTemp.getCustomerId());
					cmsLog.info("点播时间：" + vodHistoryTemp.getStartTime());
					
					if(((long)curCustomerId == (long)vodHistoryTemp.getCustomerId() && (long)curAccountId == (long)vodHistoryTemp.getAccountId())
						|| (curCustomerId == 0 || curAccountId == 0)
						)
					{
						// 同一客户同一账户的点播历史记录
						// 汇总点播历史，准备生成财务交易
						if(curCustomerId == 0 || curAccountId == 0)
						{
							// 首条记录
							cmsLog.info("找到首条记录。");
							curCustomerId = vodHistoryTemp.getCustomerId();
							curAccountId = vodHistoryTemp.getAccountId();
						}
						
						// 加入准备生成财务交易的点播历史列表
						sameFtVhf.add(vodHistoryTemp);
					}
					else 
					{
						// 新客户或新账户的点播历史
						// 此时，根据上一批点播历史sameFtVhf汇总，生成财务交易，修改点播历史的状态和ftid
						// 清空准备生成财务交易的点播历史列表
						// 最后修改curCustomerId和curAccountId为新的

						if(sameFtVhf.size() > 0)
						{
							cmsLog.info("准备生成财务交易记录...");
							cmsLog.info("客户ID：" + curCustomerId);
							cmsLog.info("账户ID：" + curAccountId);
							
							CbsResultDto c = cbsTransactionManager.saveFtFromVodHistoryTemps(
									vodHistoryManager, 
									vodHistoryTempManager, 
									financialTransactionManager, 
									sameFtVhf
									);
							
							if(c.getResultCode() == (long)0)
							{
								cmsLog.info("生成财务交易记录成功。");
							}
							else
							{
								cmsLog.warn("生成财务交易记录失败。");
							}
							
							cmsLog.info("清空准备生成财务交易的点播历史（临时）列表...");
							sameFtVhf.clear();
						}
						
						curCustomerId = vodHistoryTemp.getCustomerId();
						curAccountId = vodHistoryTemp.getAccountId();
					}
				}
				// 不能执行下面代码，因为每次分页查询后，这批记录的状态都改变过了，如果执行下面代码，会跳过某些记录
//				if(vodHistoryTemps != null)
//				{
//					firstResult += vodHistoryTemps.size();
//				}
			}
			else
			{
				// 本次查询返回记录为0，为最后一次查询，
				// 此时，根据上一批点播历史sameFtVhf汇总，生成财务交易，修改点播历史的状态和ftid
				// 清空准备生成财务交易的点播历史列表
				// 最后修改curCustomerId和curAccountId为0
				
				if(sameFtVhf.size() > 0)
				{
					cmsLog.info("准备生成财务交易记录...");
					cmsLog.info("客户ID：" + curCustomerId);
					cmsLog.info("账户ID：" + curAccountId);
					
					CbsResultDto c = cbsTransactionManager.saveFtFromVodHistoryTemps(
							vodHistoryManager, 
							vodHistoryTempManager, 
							financialTransactionManager, 
							sameFtVhf
							);
					
					if(c.getResultCode() == (long)0)
					{
						cmsLog.info("生成财务交易记录成功。");
					}
					else
					{
						cmsLog.warn("生成财务交易记录失败。");
					}
					
					cmsLog.info("清空准备生成财务交易的点播历史（临时）列表...");
					sameFtVhf.clear();
				}
				
				curCustomerId = Long.valueOf(0);
				curAccountId = Long.valueOf(0);
			}
			
		} while(vodHistoryTemps != null && vodHistoryTemps.size() > 0);

		cmsLog.info("Cbs -> MonthlyBillingImpl -> collectVodHistoriesToFt returns.");
		return cbsResultDto;
	}
	
	// 生成周期性费用，每客户的计费到期时间，每天只执行一次
	public CbsResultDto generatePeriodFeeToFt(Date date)
	{
		cmsLog.info("Cbs -> MonthlyBillingImpl -> generatePeriodFeeToFt...");
		CbsResultDto cbsResultDto = new CbsResultDto();

		// 流程：
		// 分页查询Product表中记录，条件：正常状态、周期性产品、计费有效期=date，排序：客户，创建日期
		// 后付费：如果账户状态正常，每个客户的每个有效的周期性产品生成一个财务交易（需要分析优惠）FinancialTransaction，同时更新该产品的计费有效期
		// 		  如果账户状态不正常，取消该产品
		// 预付费暂时不使用
		// 预付费：如果账户状态正常，比较账户余额和有效的周期性产品的价格（需要分析优惠），
		//		  如果余额充足，生成财务交易，扣除账户余额，同时更新该产品的计费有效期
		//		  如果余额不足，取消该产品
		// 		  如果账户状态不正常，取消该产品


		int firstResult = 0;
		int maxResults = 200;
		List products = new ArrayList();			// 分页查询得到的产品列表
		List productCategories = new ArrayList();	// 分页查询得到的产品对应的产品目录
		List prices = new ArrayList();				// 分页查询得到产品对应的价格
		List accounts = new ArrayList();			// 分页查询得到产品对应的账户

		do
		{
			// 分页查询符合条件的产品
			cmsLog.info("分页查询未处理的周期性计费产品...从" + firstResult + "条开始，共" + maxResults + "条");
			List list = productManager.getProductsForGeneratingPeriodFee(date, firstResult, maxResults);
			cmsLog.info("得到未处理的周期性计费产品，共" + list.size() + "条记录。");
			
			firstResult = firstResult + maxResults;
			
			if(list != null && list.size() > 0)
			{
//				products = (List)list.get(0);
//				productCategories = (List)list.get(1);
//				prices = (List)list.get(2);
//				accounts = (List)list.get(3);
				
				for(int i = 0; i < list.size(); i++)
				{
//					Product product = (Product)products.get(i);
//					ProductCategory productCategory = (ProductCategory)productCategories.get(i);
//					Price price = (Price)prices.get(i);
//					Account account = (Account)accounts.get(i);
					
					Object[] rows = (Object[])list.get(i);
					Product product = (Product)rows[0];
					ProductCategory productCategory = (ProductCategory)rows[1];
					Price price = (Price)rows[2];
					Account account = (Account)rows[3];
					
					cmsLog.info("处理第" + (i+1) + "条记录...");
					cmsLog.info("客户ID：" + account.getCustomerId());
					cmsLog.info("账户ID：" + account.getAccountId());
					cmsLog.info("客户产品ID：" + product.getProductId());
					cmsLog.info("产品名称：" + productCategory.getDescription());
					
					// 疑问：是否需要判断客户状态
					// 暂时不判断账户是否是预付费或后付费
					// 下面代码只是针对后付费
					// 判断账户状态
					if(account.getAccountStatusId() == 1)
					{
						// 账户状态正常
						// 查询优惠
						cmsLog.info("账户状态正常，准备生成周期性费用...");
						
						// 生成财务交易，修改产品计费有效期
						CbsResultDto c = cbsTransactionManager.saveFtFromPeriodProduct(
								financialTransactionManager, 
								productManager, 
								product, 
								productCategory, 
								price
								);
						
						if(c.getResultCode() == (long)0)
						{
							cmsLog.info("生成周期性费用成功。");
						}
						else
						{
							cmsLog.warn("生成周期性费用失败。");
						}
					}
					else
					{
						// 账户状态不正常
						// 取消该产品
						// ????????????????????????????????????????????????????????
						cmsLog.info("账户状态不正常，不生成周期性费用。");
					}
				}
			}
		} while(products != null && products.size() > 0);
		
		cmsLog.info("Cbs -> MonthlyBillingImpl -> generatePeriodFeeToFt returns.");
		return cbsResultDto;
	}
	
	// 生成账单，每月固定时间，执行一次
	public CbsResultDto createInvoices(Date date, String dueDate)
	{
		cmsLog.info("Cbs -> MonthlyBillingImpl -> createInvoices...");
		CbsResultDto cbsResultDto = new CbsResultDto();

		// 流程：
		// 分页查询FinancialTransaction表中记录，条件：正常状态、未入帐，排序：账户，创建日期
		// 同一账户的财务交易，汇总，生成一张账单（一条账单记录）Invoice
		// 每页最后一批记录（同一账户），需要结合下一页记录一起处理

		int firstResult = 0;
		int maxResults = 200;
		Long curCustomerId = Long.valueOf(0);		// 当前准备生成账单的客户ID
		Long curAccountId = Long.valueOf(0);		// 当前准备生成账单的账户ID
		List fts = new ArrayList();					// 分页查询得到的财务交易列表
		List sameInvoiceFts = new ArrayList();		// 准备生成账单的财务交易列表
		
		do
		{
			// 分页查询FinancialTransaction表中记录，条件：正常状态、未入帐，排序：账户，创建日期
			cmsLog.info("分页查询未入账的财务交易...从" + firstResult + "条开始，共" + maxResults + "条");
			fts = financialTransactionManager.getNotInInvoiceFts(firstResult, maxResults);
			cmsLog.info("得到未入账的财务交易，共" + fts.size() + "条记录。");
			
			firstResult = firstResult + maxResults;
			
			if(fts != null && fts.size() > 0)
			{
				for(int i = 0; i < fts.size(); i++)
				{
					FinancialTransaction ft = (FinancialTransaction)fts.get(i);
					
					cmsLog.info("处理第" + (i+1) + "条记录...");
					cmsLog.info("客户ID：" + ft.getCustomerId());
					cmsLog.info("账户ID：" + ft.getAccountId());
					cmsLog.info("财务交易ID：" + ft.getFtId());
					
					if(((long)curCustomerId == (long)ft.getCustomerId() && (long)curAccountId == (long)ft.getAccountId())
						|| (curCustomerId == 0 || curAccountId == 0)
						)
					{
						// 同一客户同一账户的财务交易
						// 汇总财务交易，准备生成账单
						if(curCustomerId == 0 || curAccountId == 0)
						{
							// 首条记录
							cmsLog.info("找到首条记录。");
							curCustomerId = ft.getCustomerId();
							curAccountId = ft.getAccountId();
						}
						
						// 加入准备生成账单的财务交易列表
						sameInvoiceFts.add(ft);
					}
					else 
					{
						// 新客户或新账户的财务交易
						// 此时，根据上一批财务交易sameInvoiceProducts汇总，生成账单，修改财务交易invoiceid
						// 清空准备生成账单的财务交易列表
						// 最后修改curCustomerId和curAccountId为新的

						if(sameInvoiceFts.size() > 0)
						{
							cmsLog.info("准备生成账单...");
							cmsLog.info("客户ID：" + curCustomerId);
							cmsLog.info("账户ID：" + curAccountId);
							
							boolean createInvoice = true;
							
							// 根据上一批财务交易sameInvoiceProducts汇总，生成账单，修改财务交易invoiceid
							Account account = (Account)accountManager.getById(((FinancialTransaction)sameInvoiceFts.get(0)).getAccountId().toString());
							
							// 查询账户的当月账单是否已经生成，如果未生成，才新生成账单
							List invoices = invoiceManager.findByProperty("accountId", account.getAccountId());
							if(invoices != null && invoices.size() > 0)
							{
								for(int inv = 0; inv < invoices.size(); inv++)
								{
									Invoice invoice = (Invoice)invoices.get(inv);
									
									Date createDate = invoice.getCreateDate();
									String strCreateDate = fileoper.convertDateToString(
											createDate, 
											"yyyy-MM-dd"
											);
									String expectDate = fileoper.convertDateToString(
											date, 
											"yyyy-MM-"
											);
									expectDate += monthlyDay;
									
									if(strCreateDate.equalsIgnoreCase(expectDate))
									{
										createInvoice = false;
										break;
									}
								}
							}
							
							if(createInvoice == true)
							{
								CbsResultDto c = cbsTransactionManager.saveInvoiceFromFts(
										financialTransactionManager, 
										invoiceManager, 
										accountManager, 
										sameInvoiceFts, 
										account, 
										date, 
										dueDate
										);
								if(c.getResultCode() == (long)0)
								{
									cmsLog.info("生成账单成功。");
								}
								else
								{
									cmsLog.warn("生成账单失败。");
								}
							}
							
							cmsLog.info("清空准备生成账单的财务交易列表...");
							sameInvoiceFts.clear();
						}
						
						curCustomerId = ft.getCustomerId();
						curAccountId = ft.getAccountId();
					}
				}
			}
			else
			{
				// 本次查询返回记录为0，为最后一次查询，
				// 此时，根据上一批财务交易sameInvoiceProducts汇总，生成账单，修改财务交易invoiceid
				// 清空准备生账单的财务交易列表
				// 最后修改curCustomerId和curAccountId为0
				
				if(sameInvoiceFts.size() > 0)
				{
					cmsLog.info("准备生成账单...");
					cmsLog.info("客户ID：" + curCustomerId);
					cmsLog.info("账户ID：" + curAccountId);
					
					boolean createInvoice = true;
					
					// 根据上一批财务交易sameInvoiceProducts汇总，生成账单，修改财务交易invoiceid
					Account account = (Account)accountManager.getById(((FinancialTransaction)sameInvoiceFts.get(0)).getAccountId().toString());
					
					// 查询账户的当月账单是否已经生成，如果未生成，才新生成账单
					List invoices = invoiceManager.findByProperty("accountId", account.getAccountId());
					if(invoices != null && invoices.size() > 0)
					{
						for(int inv = 0; inv < invoices.size(); inv++)
						{
							Invoice invoice = (Invoice)invoices.get(inv);
							
							Date createDate = invoice.getCreateDate();
							String strCreateDate = fileoper.convertDateToString(
									createDate, 
									"yyyy-MM-dd"
									);
							String expectDate = fileoper.convertDateToString(
									date, 
									"yyyy-MM-"
									);
							expectDate += monthlyDay;
							
							if(strCreateDate.equalsIgnoreCase(expectDate))
							{
								createInvoice = false;
								break;
							}
						}
					}
					
					if(createInvoice == true)
					{
						CbsResultDto c = cbsTransactionManager.saveInvoiceFromFts(
								financialTransactionManager, 
								invoiceManager, 
								accountManager, 
								sameInvoiceFts, 
								account, 
								date, 
								dueDate
								);
						if(c.getResultCode() == (long)0)
						{
							cmsLog.info("生成账单成功。");
						}
						else
						{
							cmsLog.warn("生成账单失败。");
						}
					}
					
					cmsLog.info("清空准备生成账单的财务交易列表...");
					sameInvoiceFts.clear();
				}
				
				curCustomerId = Long.valueOf(0);
				curAccountId = Long.valueOf(0);
			}
			
		} while(fts != null && fts.size() > 0);
		
		cmsLog.info("Cbs -> MonthlyBillingImpl -> createInvoices returns.");
		return cbsResultDto;
	}


	// 20100330
	// 服务
	public CbsResultDto executePeriodly()
	{
		CbsResultDto cbsResultDto = new CbsResultDto();
		Date date = new Date();
		
		String strCurDay = fileoper.convertDateToString(date, "dd");
		
		// 生成账单的最后付费日期
		String dueDate = fileoper.convertDateToString(date, "yyyy-MM-");
		dueDate += invoiceDueDay;
		
		// 每天执行，且每日只能执行一次
		// 生成周期性费用到财务交易
		if(lastDate == null)
		{
			cmsLog.info("执行生成周期性费用到财务交易...");
			generatePeriodFeeToFt(date);
		}
		else
		{
			String strLastDay = fileoper.convertDateToString(lastDate, "dd");
			if(strLastDay.equalsIgnoreCase(strCurDay))
			{
				
			}
			else
			{
				// 日期切换
				cmsLog.info("执行生成周期性费用到财务交易...");
				generatePeriodFeeToFt(date);
			}
		}
		
		// 每月25日执行，且当日只能执行一次
		// 生成当月点播费用到财务交易
		// 汇总当月财务交易生成账单
		if(strCurDay.equalsIgnoreCase(monthlyDay))
		{
			if(lastDate == null)
			{
				cmsLog.info("执行生成当月点播费用到财务交易...");
				collectVodHistoriesToFt();
				
				cmsLog.info("执行汇总当月财务交易生成账单...");
				createInvoices(date, dueDate);
			}
			else
			{
				String strLastDay = fileoper.convertDateToString(lastDate, "dd");
				if(strLastDay.equalsIgnoreCase(strCurDay))
				{
					
				}
				else
				{
					// 日期切换
					cmsLog.info("执行生成当月点播费用到财务交易...");
					collectVodHistoriesToFt();
					
					cmsLog.info("执行汇总当月财务交易生成账单...");
					createInvoices(date, dueDate);
				}
			}
		}
		
		lastDate = date;
		
		return cbsResultDto;
	}
	
}
