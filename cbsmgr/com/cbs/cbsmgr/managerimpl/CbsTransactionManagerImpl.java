package com.cbs.cbsmgr.managerimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.bean.FinancialTransaction;
import com.cbs.cbsmgr.bean.Invoice;
import com.cbs.cbsmgr.bean.PpSrvPdtRel;
import com.cbs.cbsmgr.bean.Price;
import com.cbs.cbsmgr.bean.Product;
import com.cbs.cbsmgr.bean.ProductCategory;
import com.cbs.cbsmgr.bean.VodFlow;
import com.cbs.cbsmgr.bean.VodHistory;
import com.cbs.cbsmgr.bean.VodHistoryTemp;
import com.cbs.cbsmgr.common.FileOperationImpl;
import com.cbs.cbsmgr.dto.CbsResultDto;
import com.cbs.cbsmgr.manageriface.IAccountManager;
import com.cbs.cbsmgr.manageriface.ICbsTransactionManager;
import com.cbs.cbsmgr.manageriface.IFinancialTransactionManager;
import com.cbs.cbsmgr.manageriface.IInvoiceManager;
import com.cbs.cbsmgr.manageriface.IPpSrvPdtRelManager;
import com.cbs.cbsmgr.manageriface.IProductCategoryManager;
import com.cbs.cbsmgr.manageriface.IProductManager;
import com.cbs.cbsmgr.manageriface.IVodFlowManager;
import com.cbs.cbsmgr.manageriface.IVodHistoryManager;
import com.cbs.cbsmgr.manageriface.IVodHistoryTempManager;
import com.soaplat.cmsmgr.bean.CmsService;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgSrvRel;
import com.soaplat.cmsmgr.bean.SrvColumn;
import com.soaplat.cmsmgr.bean.SrvProduct;
import com.soaplat.cmsmgr.manageriface.ISrvProductManager;


public class CbsTransactionManagerImpl implements ICbsTransactionManager {

	public static final Logger cmsLog = Logger.getLogger("Cms");
	private static FileOperationImpl fileopr = null;
	private static Long vodSmsAccountId = (long)1;
	
	public CbsTransactionManagerImpl()
	{
		fileopr = new FileOperationImpl();
	}
	

	
	//----------------------- 节目包(ProgPackage) / 服务(CmsService) ------------------------------------------
	// 产品与节目包关系 ProgPackage PpSrvPdtRel
	// 添加
	// 添加节目包到产品
	public void saveProgPackagesToProductCategory(
			IProductCategoryManager productCategoryManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			List progPackages, 
			Long productCategoryId
			)
	{
		cmsLog.info("cbs -> CbsTransaction -> saveProgPackagesToProductCategory...");
		
		// 1 - 判断产品是否存在
		// 2 - 查询判断节目包是否属于“其他”产品
		// 2.1 - 如果是，删除节目包与“其他”产品的关系
		// 2.2 - 如果不是，继续
		// 2.2.1 - 查询判断节目包与产品的关系，是否已经存在
		// 2.2.1.1 - 如果是，保存（添加）节目包与产品的关系
		
		if(productCategoryId == 1)
		{
			cmsLog.info("该产品是“其他”，不能添加节目包。");
			return;
		}
		if(progPackages == null || progPackages.size() <= 0)
		{
			cmsLog.info("节目包列表为空。");
			return;
		}
		// 1 - 判断产品是否存在
		ProductCategory productCategory = (ProductCategory)productCategoryManager.getById(productCategoryId.toString());
		if(productCategory == null)
		{
			cmsLog.info("该产品不存在。");
			return;
		}
		for(int i = 0; i < progPackages.size(); i++)
		{
			// 2 - 查询判断节目包是否属于“其他”产品
			ProgPackage progPackage = (ProgPackage)progPackages.get(i);
			List ppSrvPdtRels = ppSrvPdtRelManager.getPpSrvPdtRelsByProductIdAndProductCategoryId(
					progPackage.getProductid(), 
					(long)1
					);
			if(ppSrvPdtRels.size() > 0)
			{
				// 2.1 - 如果是，删除节目包与“其他”产品的关系
				PpSrvPdtRel[] ppSrvPdtRelObjects = new PpSrvPdtRel[ppSrvPdtRels.size()];
				for(int j = 0; j < ppSrvPdtRels.size(); j++)
				{
					ppSrvPdtRelObjects[j] = (PpSrvPdtRel)ppSrvPdtRels.get(j);
					cmsLog.info("Old relid:" + ((PpSrvPdtRel)ppSrvPdtRels.get(j)).getRelid());
					cmsLog.info("Old Productid:" + ((PpSrvPdtRel)ppSrvPdtRels.get(j)).getProductid());
				}
				cmsLog.info("删除节目包与“其他”产品的关系记录。");
				ppSrvPdtRelManager.delete(ppSrvPdtRelObjects);
			}
			// 2.2 - 如果不是，继续
			// 2.2.1 - 查询判断节目包与产品的关系，是否已经存在
			List curPpSrvPdtRels = ppSrvPdtRelManager.getPpSrvPdtRelsByProductIdAndProductCategoryId(
					progPackage.getProductid(), 
					productCategoryId
					);
			if(curPpSrvPdtRels.size() > 0)
			{
				// 不操作
			}
			else
			{
				// 2.2.1.1 - 如果是，保存（添加）节目包与产品的关系
				PpSrvPdtRel ppSrvPdtRel = new PpSrvPdtRel();
				ppSrvPdtRel.setProductCategoryId(productCategoryId);
				ppSrvPdtRel.setProductid(progPackage.getProductid());
//				ppSrvPdtRel.setSrvid(srvid);
				ppSrvPdtRel.setInputtime(new Date());
				cmsLog.info("New ProductId:" + progPackage.getProductid());
				cmsLog.info("New productCategoryId:" + productCategoryId);
				cmsLog.info("创建节目包与产品的关系记录。");
				ppSrvPdtRelManager.save(ppSrvPdtRel);
			}
		}
		
		cmsLog.info("cbs -> CbsTransaction -> saveProgPackagesToProductCategory returns.");
	}
	
	// 删除
	// 删除节目包从产品
	public void deleteProgPackagesFromProductCategory(
			IProductCategoryManager productCategoryManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			List progPackages, 
			Long productCategoryId
			)
	{
		cmsLog.info("cbs -> CbsTransaction -> deleteProgPackagesFromProductCategory...");
		
		if(productCategoryId == 1)
		{
			cmsLog.info("该产品是“其他”，不能删除节目包。");
			return;
		}
		if(progPackages == null || progPackages.size() <= 0)
		{
			cmsLog.info("节目包列表为空。");
			return;
		}
		// 判断产品是否存在
		ProductCategory productCategory = (ProductCategory)productCategoryManager.getById(productCategoryId.toString());
		if(productCategory == null)
		{
			cmsLog.info("该产品不存在。");
			return;
		}
		for(int i = 0; i < progPackages.size(); i++)
		{
			ProgPackage progPackage = (ProgPackage)progPackages.get(i);
			
			// 1 - 查询删除产品节目包关系
			List ppSrvPdtRels = ppSrvPdtRelManager.getPpSrvPdtRelsByProductIdAndProductCategoryId(
					progPackage.getProductid(), 
					productCategoryId
					);
			if(ppSrvPdtRels.size() > 0)
			{
				PpSrvPdtRel[] ppSrvPdtRelObjects = new PpSrvPdtRel[ppSrvPdtRels.size()];
				for(int j = 0; j < ppSrvPdtRels.size(); j++)
				{
					ppSrvPdtRelObjects[j] = (PpSrvPdtRel)ppSrvPdtRels.get(j);
					cmsLog.info("Old relid:" + ((PpSrvPdtRel)ppSrvPdtRels.get(j)).getRelid());
					cmsLog.info("Old ProductId:" + ((PpSrvPdtRel)ppSrvPdtRels.get(j)).getProductid());
				}
				cmsLog.info("删除产品与节目包的关系记录。");
				ppSrvPdtRelManager.delete(ppSrvPdtRelObjects);
			}
			
			// 2 - 查询产品与节目包关系
			List curPpSrvPdtRels = ppSrvPdtRelManager.findByProperty("productid", progPackage.getProductid());
			if(curPpSrvPdtRels.size() > 0)
			{
				
				// 4 - 有，判断关系数量
				if(curPpSrvPdtRels.size() == 1)
				{
					// 5 - 如果唯一，不处理
					cmsLog.info("节目包与产品关系唯一，无操作。");
				}
				else
				{
					// 6 - 大于1，判断是否存在与“其他”产品的关系，如果有，删除与“其他”产品的关系
					cmsLog.info("节目包与产品关系不唯一。" + curPpSrvPdtRels.size());
					for(int j = 0; j < curPpSrvPdtRels.size(); j++)
					{
						PpSrvPdtRel ppSrvPdtRel = (PpSrvPdtRel)curPpSrvPdtRels.get(j);
						if(ppSrvPdtRel.getProductCategoryId() == 1)
						{
							cmsLog.info("Old relid:" + ppSrvPdtRel.getRelid());
							cmsLog.info("Old ProductId:" + ppSrvPdtRel.getProductid());
							cmsLog.info("删除节目包与“其他”产品的关系记录。");
							ppSrvPdtRelManager.deleteById(ppSrvPdtRel.getRelid());
						}
					}
				}
			}
			else
			{
				// 3 - 没有，添加“其他”产品与节目包的关系
				PpSrvPdtRel ppSrvPdtRel = new PpSrvPdtRel();
				ppSrvPdtRel.setProductCategoryId((long)1);
				ppSrvPdtRel.setProductid(progPackage.getProductid());
//				ppSrvPdtRel.setSrvid(srvid);
				ppSrvPdtRel.setInputtime(new Date());
				cmsLog.info("创建节目包与“其他”产品的关系记录。");
				cmsLog.info("new ProductId:" + progPackage.getProductid());
				ppSrvPdtRelManager.save(ppSrvPdtRel);
			}
		}
		cmsLog.info("cbs -> CbsTransaction -> deleteProgPackagesFromProductCategory returns.");
	}
	
	
	// 产品与服务配置关系 CmsService SrvProduct
	// 保存（先删除，后添加）
	// 保存服务与产品的配置关系记录 SrvProduct 20091105
	public void saveSrvProductsByProductCategoryId(
			IProductCategoryManager productCategoryManager,
			ISrvProductManager srvProductManager,
			List cmsServices, 
			Long productCategoryId
			)
	{
		cmsLog.info("cbs -> CbsTransaction -> saveSrvProductsByProductCategoryId...");
		
		// 1 - 判断productCategoryId是否存在
		// 2 - 删除productCategoryId下所有的SrvProduct
		// 3 - 保存SrvProduct
		
		// 1 - 判断productCategoryId是否存在
		ProductCategory productCategory = (ProductCategory)productCategoryManager.getById(productCategoryId.toString());
		if(productCategory != null)
		{
			// 2 - 删除productCategoryId下所有的SrvProduct
			List oldSrvProducts = srvProductManager.findByProperty("productCategoryId", productCategoryId);
			if(oldSrvProducts.size() > 0)
			{
				SrvProduct[] oldSrvProductObjects = (SrvProduct[])oldSrvProducts.toArray(new SrvProduct[oldSrvProducts.size()]);
				cmsLog.info("删除原有产品与服务的配置关系记录。");
				srvProductManager.delete(oldSrvProductObjects);
			}
			// 3 - 保存SrvProduct
			if(cmsServices.size() > 0)
			{
				// 保存SrvProduct
				SrvProduct[] newSrvProductObjects = new SrvProduct[cmsServices.size()];
				
				for(int i = 0; i < cmsServices.size(); i++)
				{
					CmsService cmsService = (CmsService)cmsServices.get(i);
					SrvProduct srvProduct = new SrvProduct();
					srvProduct.setProductCategoryId(productCategoryId);
					srvProduct.setSrvid(cmsService.getSrvid());
					srvProduct.setSelecttag((long)0);
					srvProduct.setInputtime(new Date());
					newSrvProductObjects[i] = srvProduct;
				}
				
				cmsLog.info("保存产品与服务的配置关系记录。");
				srvProductManager.save(newSrvProductObjects);
			}
		}
		else
		{
			cmsLog.info("该产品不存在。");
		}
		
		cmsLog.info("cbs -> CbsTransaction -> saveSrvProductsByProductCategoryId returns.");
	}

	
	
	
	
	
	
	//----------------------- 后台计费（点播流水） ------------------------------------------
	// 生成点播流水记录VodFlow
	public CbsResultDto saveVodFlows(
			IVodFlowManager vodFlowManager,
			List<VodFlow> vodFlows
			)
	{
		cmsLog.info("Cbs -> CbsTransactionManagerImpl -> saveVodFlows...");
		CbsResultDto cbsResultDto = new CbsResultDto();
		
		try {
			if(vodFlows != null)
			{
				for(int i = 0; i < vodFlows.size(); i++)
				{
					VodFlow vodFlow = (VodFlow)vodFlows.get(i);
					vodFlowManager.save(vodFlow);
				}
			}
			else
			{
				
			}
//			VodFlow[] vodFlowObjects = (VodFlow[]) vodFlows
//					.toArray(new VodFlow[vodFlows.size()]);
//			vodFlowManager.save(vodFlowObjects);
		} catch (Exception e) {
			cbsResultDto.setResultCode((long)1);
			cbsResultDto.setErrorMessage("保存点播流水异常。");
			cbsResultDto.setErrorDetail(e.getMessage());
		}
		cmsLog.info("Cbs -> CbsTransactionManagerImpl -> saveVodFlows returns.");
		return cbsResultDto;
	}
	
	// 生成点播历史记录，修改点播流水的状态
	public CbsResultDto saveVodHistories(
			IVodHistoryManager vodHistoryManager,
			IVodHistoryTempManager vodHistoryTempManager,
			IVodFlowManager vodFlowManager,
			List<VodHistory> vodHistories,			// 点播历史，用于生成
			List<VodFlow> vodFlows					// 点播流水，用于修改状态
			)
	{
		cmsLog.info("Cbs -> CbsTransactionManagerImpl -> saveVodHistories...");
		CbsResultDto cbsResultDto = new CbsResultDto();
		
		for(int i = 0; i < vodHistories.size(); i++)
		{
			VodHistory vodHistory = (VodHistory)vodHistories.get(i);
			VodFlow vodFlow = (VodFlow)vodFlows.get(i);
			
			// 生成点播历史
			vodHistoryManager.save(vodHistory);
			
			VodHistoryTemp vodHistoryTemp = new VodHistoryTemp(vodHistory);
			vodHistoryTempManager.save(vodHistoryTemp);
			
			// 修改点播流水处理状态
			vodFlow.setDealState((long)1);
			vodFlow.setDealDate(new Date());
			vodFlowManager.update(vodFlow);
		}
		
		cmsLog.info("Cbs -> CbsTransactionManagerImpl -> saveVodHistories returns.");
		return cbsResultDto;
	}
	
	
	
	
	// ---------------------- 周期性费用生成 ------------------------------------------------
	// 生成财务交易，修改点播历史的状态和ftid
	public CbsResultDto saveFtFromVodHistoryTemps(
			IVodHistoryManager vodHistoryManager,
			IVodHistoryTempManager vodHistoryTempManager,
			IFinancialTransactionManager financialTransactionManager,
			List vodHistoryTemps
			)
	{
		cmsLog.info("Cbs -> CbsTransactionManagerImpl -> saveFtFromVodHistoryTemps...");
		CbsResultDto cbsResultDto = new CbsResultDto();
		
		// 生成财务交易的费用
		Double totalFee = Double.valueOf(0);
		Long customerId = Long.valueOf(0);
		Long accountId = Long.valueOf(0);
		Long smsAccountId = vodSmsAccountId;
		
		for(int i = 0; i < vodHistoryTemps.size(); i++)
		{
			VodHistoryTemp vodHistoryTemp = (VodHistoryTemp)vodHistoryTemps.get(i);
			
			totalFee += vodHistoryTemp.getAmount();
			customerId = vodHistoryTemp.getCustomerId();
			accountId = vodHistoryTemp.getAccountId();
		}
		
		// 生成财务交易
		FinancialTransaction ft = new FinancialTransaction();
		ft.setCustomerId(customerId);
		ft.setAccountId(accountId);
		ft.setAmount(totalFee);
		ft.setFtStatusId((long)1);
		ft.setInvoiceId(Long.valueOf(0));
		ft.setPeriodFrom(null);
		ft.setPeriodTo(null);
		ft.setProductId(Long.valueOf(0));
		ft.setSmsAccountId(smsAccountId);
		ft.setCreateDate(new Date());
		financialTransactionManager.save(ft);
		cmsLog.info("财务交易记录已经保存，ftid：" + ft.getFtId());
		
		// 修改点播历史的状态
		for(int i = 0; i < vodHistoryTemps.size(); i++)
		{
			VodHistoryTemp vodHistoryTemp = (VodHistoryTemp)vodHistoryTemps.get(i);
			VodHistory vodHistory = (VodHistory)vodHistoryManager.getById(vodHistoryTemp.getVodHistoryId());
			
			vodHistoryTemp.setDealState(Long.valueOf(1));
			vodHistoryTemp.setDealDate(new Date());
			vodHistoryTemp.setFtId(ft.getFtId());
			
			vodHistory.setDealState(Long.valueOf(1));
			vodHistory.setDealDate(new Date());
			vodHistory.setFtId(ft.getFtId());
			
			vodHistoryTempManager.update(vodHistoryTemp);
			vodHistoryManager.update(vodHistory);
		}
		cmsLog.info("点播历史和点播历史（临时）已经修改。共" + vodHistoryTemps.size() + "条记录。");
		
		cmsLog.info("Cbs -> CbsTransactionManagerImpl -> saveFtFromVodHistoryTemps returns.");
		return cbsResultDto;
	}
	
	// 计算产品的计费有效期
	private Date getNewValidDate(Date oldValidDate, Long offset)
	{
		Date newValidDate;
		Calendar c = Calendar.getInstance();
		c.setTime(oldValidDate);
		c.add(Calendar.DATE, offset.intValue());
		newValidDate = c.getTime();
		return newValidDate;
	}

	// 生成财务交易，修改产品计费有效期
	public CbsResultDto saveFtFromPeriodProduct(
			IFinancialTransactionManager financialTransactionManager,
			IProductManager productManager,
			Product product,
			ProductCategory productCategory,
			Price price
			)
	{
		cmsLog.info("Cbs -> CbsTransactionManagerImpl -> saveFtFromPeriodProduct ...");
		CbsResultDto cbsResultDto = new CbsResultDto();
		
		// 生成财务交易
		FinancialTransaction ft = new FinancialTransaction();
		ft.setCustomerId(product.getCustomerId());
		ft.setAccountId(product.getAccountId());
		ft.setAmount(price.getPrice());
		ft.setFtStatusId((long)1);
		ft.setInvoiceId(Long.valueOf(0));
		ft.setPeriodFrom(null);
		ft.setPeriodTo(null);
		ft.setProductId(Long.valueOf(0));
		ft.setSmsAccountId(price.getSmsAccountId());
		ft.setCreateDate(new Date());
		financialTransactionManager.save(ft);
		cmsLog.info("财务交易记录已经保存，ftid：" + ft.getFtId());
		
		// 计算产品的计费有效期
		Date oldValidDate = product.getBillingValidDate();
		Long offset = productCategory.getDays();
		Date newValidDate = getNewValidDate(oldValidDate, offset);
		
		// 修改产品计费有效期
		product.setBillingValidDate(newValidDate);
		productManager.update(product);
		cmsLog.info("产品的计费有效期已经更新，新有效期：" + newValidDate);
		
		cmsLog.info("Cbs -> CbsTransactionManagerImpl -> saveFtFromPeriodProduct returns.");
		return cbsResultDto;
	}
	
	// 根据上一批财务交易fts汇总，生成账单，修改财务交易invoiceid
	public CbsResultDto saveInvoiceFromFts(
			IFinancialTransactionManager financialTransactionManager,
			IInvoiceManager invoiceManager,
			IAccountManager accountManager,
			List fts,
			Account account,
			Date date,
			String dueDate
			)
	{
		cmsLog.info("Cbs -> CbsTransactionManagerImpl -> saveInvoiceFromFts...");
		CbsResultDto cbsResultDto = new CbsResultDto();
		
		// 生成财务交易的费用
		Double bbf = account.getBbf();
		Double totalFee = Double.valueOf(0);
		Long customerId = Long.valueOf(0);
		Long accountId = Long.valueOf(0);
		Long smsAccountId = Long.valueOf(0);
		
		for(int i = 0; i < fts.size(); i++)
		{
			FinancialTransaction ft = (FinancialTransaction)fts.get(i);
			
			totalFee += ft.getAmount();
			customerId = ft.getCustomerId();
			accountId = ft.getAccountId();
		}
		
		// 生成账单
		Invoice invoice = new Invoice();
		invoice.setCustomerId(customerId);
		invoice.setAccountId(accountId);
		invoice.setBbf(bbf);
		invoice.setCreateDate(date);
		invoice.setDueDate(fileopr.convertStringToDate(dueDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		invoice.setInvoiceStatusId(Long.valueOf(1));
		invoice.setPaidAmount(Double.valueOf(0));
		invoice.setPaidDate(null);
		invoice.setPrintTag("N");
		invoice.setTotalAmount(totalFee);
		invoiceManager.save(invoice);
		cmsLog.info("账单已经保存，账单ID：" + invoice.getInvoiceId());
		
		// 修改财务交易的invoiceid
		for(int i = 0; i < fts.size(); i++)
		{
			FinancialTransaction ft = (FinancialTransaction)fts.get(i);
			ft.setInvoiceId(invoice.getInvoiceId());
			financialTransactionManager.update(ft);
		}
		
		// 修改账户的bbf
		account.setBbf(bbf + totalFee);
		accountManager.update(account);
		cmsLog.info("账户已经更新，账户ID：" + account.getAccountId());
		
		cmsLog.info("Cbs -> CbsTransactionManagerImpl -> saveInvoiceFromFts returns.");
		return cbsResultDto;
	}
	
	
}
