package com.cbs.cbsmgr.manageriface;

import java.util.Date;
import java.util.List;

import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.bean.Price;
import com.cbs.cbsmgr.bean.Product;
import com.cbs.cbsmgr.bean.ProductCategory;
import com.cbs.cbsmgr.bean.VodFlow;
import com.cbs.cbsmgr.bean.VodHistory;
import com.cbs.cbsmgr.dto.CbsResultDto;
import com.soaplat.cmsmgr.manageriface.ISrvProductManager;



public interface ICbsTransactionManager 
{
	//----------------------- 节目包(ProgPackage) / 服务(CmsService) ------------------------------------------
	// 产品与节目包关系 ProgPackage
	// 添加
	// 添加节目包到产品
	public void saveProgPackagesToProductCategory(
			IProductCategoryManager productCategoryManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			List progPackages, 
			Long productCategoryId
			);
	
	// 删除
	// 删除节目包从产品
	public void deleteProgPackagesFromProductCategory(
			IProductCategoryManager productCategoryManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			List progPackages, 
			Long productCategoryId
			);
	
	
	// 产品与服务配置关系 CmsService
	// 保存（先删除，后添加）
	// 保存服务与产品的配置关系记录 SrvProduct 20091105
	public void saveSrvProductsByProductCategoryId(
			IProductCategoryManager productCategoryManager,
			ISrvProductManager srvProductManager,
			List cmsServices, 
			Long productCategoryId
			);
	
	
	
	
	
	
	//----------------------- 后台计费（点播流水） ------------------------------------------
	// 生成点播流水记录VodFlow
	public CbsResultDto saveVodFlows(
			IVodFlowManager vodFlowManager,
			List<VodFlow> vodFlows
			);
	
	// 生成点播历史记录，修改点播流水的状态
	public CbsResultDto saveVodHistories(
			IVodHistoryManager vodHistoryManager,
			IVodHistoryTempManager vodHistoryTempManager,
			IVodFlowManager vodFlowManager,
			List<VodHistory> vodHistories,
			List<VodFlow> vodFlows
			);
	
	// ---------------------- 周期性费用生成 ------------------------------------------------
	// 生成财务交易，修改点播历史的状态和ftid
	public CbsResultDto saveFtFromVodHistoryTemps(
			IVodHistoryManager vodHistoryManager,
			IVodHistoryTempManager vodHistoryTempManager,
			IFinancialTransactionManager financialTransactionManager,
			List vodHistoryTemps
			);
	
	// 生成财务交易，修改产品计费有效期
	public CbsResultDto saveFtFromPeriodProduct(
			IFinancialTransactionManager financialTransactionManager,
			IProductManager productManager,
			Product product,
			ProductCategory productCategory,
			Price price
			);
	
	// 根据上一批财务交易fts汇总，生成账单，修改财务交易invoiceid
	public CbsResultDto saveInvoiceFromFts(
			IFinancialTransactionManager financialTransactionManager,
			IInvoiceManager invoiceManager,
			IAccountManager accountManager,
			List fts,
			Account account,
			Date date,
			String dueDate
			);
	
}
