package com.cbs.cbsmgr.serviceBackground;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.bean.CampaignCategory;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.bean.Price;
import com.cbs.cbsmgr.bean.Product;
import com.cbs.cbsmgr.bean.ProductCategory;
import com.cbs.cbsmgr.bean.VodCampaignCategory;
import com.cbs.cbsmgr.bean.VodCbsProductRel;
import com.cbs.cbsmgr.bean.VodFlow;
import com.cbs.cbsmgr.bean.VodHistory;
import com.cbs.cbsmgr.bean.VodHistoryTemp;
import com.cbs.cbsmgr.bean.VodProduct;
import com.cbs.cbsmgr.dto.CbsResultDto;
import com.cbs.cbsmgr.dto.VodFlowDTO;
import com.cbs.cbsmgr.manageriface.IAccountManager;
import com.cbs.cbsmgr.manageriface.ICampaignCategoryManager;
import com.cbs.cbsmgr.manageriface.ICbsTransactionManager;
import com.cbs.cbsmgr.manageriface.ICustomerManager;
import com.cbs.cbsmgr.manageriface.IPriceManager;
import com.cbs.cbsmgr.manageriface.IProductCategoryManager;
import com.cbs.cbsmgr.manageriface.IProductManager;
import com.cbs.cbsmgr.manageriface.IVodCampaignCategoryManager;
import com.cbs.cbsmgr.manageriface.IVodCbsProductRelManager;
import com.cbs.cbsmgr.manageriface.IVodFlowManager;
import com.cbs.cbsmgr.manageriface.IVodHistoryManager;
import com.cbs.cbsmgr.manageriface.IVodHistoryTempManager;
import com.cbs.cbsmgr.manageriface.IVodProductManager;
import com.cbs.cbsmgr.manageriface.IVodProgramPackageRelManager;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.srmmgr.bean.ConsumeList;


/**
 * Title 		:the Class VodBillingImpl.
 * Description	:读取点播流水接口xml文件，分析点播流水，生成点播费用
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-09-08
 * 
 * @author		:cbsmgr Group (Andy Han)
 * @version		:1.0
 */

public class VodBillingImpl implements VodBillingIface {

	// 点播流水导入
	// 点播流水筛选
	// 点播费用计算
	// 点播历史生成
	
	public List xmlFileList;
	public List txtFileList;
	private static FileOperationImpl fileoper = null;
	
	public IVodHistoryManager vodHistoryManager = null;
	public IVodHistoryTempManager vodHistoryTempManager = null;
	public IAccountManager accountManager = null;
	public IVodProgramPackageRelManager vodProgramPackageRelManager = null;
	public IVodProductManager vodProductManager = null;
	public IProductManager productManager = null;
	public IVodCbsProductRelManager vodCbsProductRelManager = null;
	public IVodCampaignCategoryManager vodCampaignCategoryManager = null;
	public IVodFlowManager vodFlowManager = null;
	public ICustomerManager customerManager = null;
	public IProductCategoryManager productCategoryManager = null;
	public IPriceManager priceManager = null;
	public ICampaignCategoryManager campaignCategoryManager = null;
	
	public ICbsTransactionManager cbsTransactionManager = null;
	
	public static final Logger cmsLog = Logger.getLogger("Cms");
	private static Date lastDate = null;
	
	public VodBillingImpl()
	{
		xmlFileList = new ArrayList();
		fileoper = new FileOperationImpl();
		vodHistoryManager = (IVodHistoryManager)ApplicationContextHolder.webApplicationContext.getBean("vodHistoryManager");
		vodHistoryTempManager = (IVodHistoryTempManager)ApplicationContextHolder.webApplicationContext.getBean("vodHistoryTempManager");
		accountManager = (IAccountManager)ApplicationContextHolder.webApplicationContext.getBean("accountManager");
		vodProgramPackageRelManager = (IVodProgramPackageRelManager)ApplicationContextHolder.webApplicationContext.getBean("vodProgramPackageRelManager");
		vodProductManager = (IVodProductManager)ApplicationContextHolder.webApplicationContext.getBean("vodProductManager");
		productManager = (IProductManager)ApplicationContextHolder.webApplicationContext.getBean("productManager");
		vodCbsProductRelManager = (IVodCbsProductRelManager)ApplicationContextHolder.webApplicationContext.getBean("vodCbsProductRelManager");
		vodCampaignCategoryManager = (IVodCampaignCategoryManager)ApplicationContextHolder.webApplicationContext.getBean("vodCampaignCategoryManager");
		vodFlowManager = (IVodFlowManager)ApplicationContextHolder.webApplicationContext.getBean("vodFlowManager");
		customerManager = (ICustomerManager)ApplicationContextHolder.webApplicationContext.getBean("customerManager");
		productCategoryManager = (IProductCategoryManager)ApplicationContextHolder.webApplicationContext.getBean("productCategoryManager");
		priceManager = (IPriceManager)ApplicationContextHolder.webApplicationContext.getBean("priceManager");
		campaignCategoryManager = (ICampaignCategoryManager)ApplicationContextHolder.webApplicationContext.getBean("campaignCategoryManager");
		
		cbsTransactionManager = (ICbsTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("cbsTransactionManager");
	}

	
	// ---------------------------------------------------------------------------------------------------------------
	// 经过讨论，新一轮的修改开始，如下：	20091222
	// 点播流水导入
	// 点播流水筛选
	// 点播费用计算
	// 点播历史生成
	
	// 遍历目录下所有文件
	public static void listFile(File dataDir, List fileList)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> listFile...");
		if (!dataDir.exists())
        {
            cmsLog.info("文件或文件夹不存在。");
        }
        else
        {
            if (dataDir.isFile())
            {
            	if(dataDir.getName().endsWith(".log"))
            	{
	                cmsLog.info(dataDir);
	                cmsLog.info(dataDir.getName());
	                fileList.add(dataDir);
            	}
            }
            else
            {
            	File[] files = dataDir.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                	listFile(files[i], fileList);
                }
            }
        }
		cmsLog.info("Cbs -> VodBillingImpl -> listFile returns.");
	}
	
	// 文件处理，遍历指定目录下所有txt文件。
	private void getAllTxtFromDir(String dir)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> getAllTxtFromDir...");
		File dataDir = new File(dir);	// 是存放原始文件的文件夹

        cmsLog.info("文件夹" + dir + "的文件列表:");
        listFile(dataDir, txtFileList);
        cmsLog.info("Cbs -> VodBillingImpl -> getAllTxtFromDir returns.");
	}
	
	// 文件处理，读取Txt中所有点播流水
	private List<VodFlow> getVodFlowByVodFlowTxt(String vodFlowTxt) throws Exception
	{
		cmsLog.info("Cbs -> VodBillingImpl -> getVodFlowByVodFlowTxt...");
		List<VodFlow> vodFlows = new ArrayList<VodFlow>();
	
		BufferedReader br;
//		private BufferedWriter bw;
		Vector ipVector = new Vector();   
			           
		try   
        {   
            br = new BufferedReader(new InputStreamReader(new FileInputStream(vodFlowTxt)));
            String str = br.readLine();
            while(str != null)
            {
            	cmsLog.info(str);
            	ipVector.add(str);
            	str = br.readLine();
            }
            br.close();
        }
        catch(Exception e)
        {
       		e.printStackTrace();
        }
        
        List userNames = new ArrayList(); 		// 已经查询过的userName
        List customerids = new ArrayList();		// 已经查询过的userName对应的customerid
        Iterator it = ipVector.iterator();
        for(int i = 0; i < ipVector.size(); i++)
        {
        	// 0 - [2009-11-20
        	// 1 - 14:48:26:156]
        	// 2 - annfan			Hardware.username
        	// 3 - 
        	// 4 - 1				progfileid
        	// 5 - 
        	// 6 - 1				servicetype
        	// 7 - 
        	// 8 - 2009-11-20		starttime
        	// 9 - 14:48:24
        	// 10 - 
        	// 11 - 2009-11-20		endtime
        	// 12 - 14:48:26
        	
        	String str = (String)it.next();
        	String[] strl = str.split(" ");
        	
        	if(strl.length == 13)
        	{
        		String userName = strl[2];
        		String progfileId = strl[4];
        		String serviceType = strl[6];
        		String startTime = strl[8] + " " + strl[9];
        		String stopTime = strl[11] + " " + strl[12];
        		Long customerId = Long.valueOf(0);

        		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			Date dateStart = format.parse(startTime);
    			Date dateStop = format.parse(stopTime);
        		
    			for(int j = 0; j < userNames.size(); j++)
    			{
    				String un = (String)userNames.get(j);
    				if(un.equalsIgnoreCase(userName))
    				{
    					customerId = (Long)customerids.get(j);
    					break;
    				}
    			}
    			if(customerId == null || customerId == 0)
    			{
	        		// 根据username，查询Hardware、Product，得到customerid
	        		List products = productManager.getProductsByUsername(userName);
	        		customerId = ((Product)products.get(0)).getCustomerId();
	        		userNames.add(userName);
	        		customerids.add(customerId);
    			}

        		if(customerId != null && customerId > 0)
        		{
        			VodFlow vodFlow = new VodFlow();
        			vodFlow.setCustomerId(customerId);
        			vodFlow.setProgfileId(progfileId);
        			vodFlow.setServiceType(Long.valueOf(serviceType));
        			vodFlow.setStartTime(dateStart);
        			vodFlow.setStopTime(dateStop);
        			vodFlow.setImportDate(new Date());
        			vodFlow.setDealDate(null);
        			vodFlow.setDealState(Long.valueOf(0));
        			
        			vodFlows.add(vodFlow);
        		}
        	}
        }

		cmsLog.info("Cbs -> VodBillingImpl -> getVodFlowByVodFlowTxt returns.");
		return vodFlows;
	}
	
	// 复制文件，本地 --> 本地
	private int copyFile(String strFileFrom, String strFileTo)
	{
		cmsLog.debug("Cms -> VodBillingImpl -> copyFile...");
		cmsLog.info("From : " + strFileFrom);
		cmsLog.info("To : " + strFileTo);
		int ret = -1;
		
		try
		{
			int last = 0;
			Long localreadbytes = Long.valueOf(0);
			byte[] bytes = new byte[1024];
			File fileTo = new File(strFileTo);
			File fileFrom = new File(strFileFrom);
			FileOutputStream fileStreamOut = new FileOutputStream(fileTo, true);
			FileInputStream fileStreamIn = new FileInputStream(fileFrom);
			
			while ((last = fileStreamIn.read(bytes)) != -1) 
			{
				fileStreamOut.write(bytes, 0, last);
				localreadbytes += last; 					// 已传输字节数
//				long nowProcess = localreadbytes / step; 	// 计算当前进度
//				if (nowProcess > process) 
//				{
//					process = nowProcess;
//					transObj.setFileProcess(process);
//					instance.syncCtrl.start();
//					logger.debug("传输进度：" + process);
//				}
			}
			fileStreamOut.flush();

			ret = 0;
			cmsLog.info("Copy file successfully : " + strFileFrom + " --> " + strFileTo);
		}
		catch(IOException ex)
		{
			ret = 1;
			cmsLog.info("Copy file unsuccessfully : " + strFileFrom + " --> " + strFileTo);
			cmsLog.info(ex.getMessage());
		}
		
		cmsLog.debug("Cms -> VodBillingImpl -> copyFile returns.");
		return ret;
	}

	// 删除文件，本地
	private int deleteFile(String strFile)
	{
		// 删除文件
		cmsLog.debug("Cms -> VodBillingImpl -> deleteFile...");
		int ireturn = -1;
		try
		{
			File file = new File(strFile);
			file.delete();

			ireturn = 0;
			cmsLog.info("Delete file successfully: " + strFile);
		}
		catch(Exception ex)
		{
			ireturn = 1;
			cmsLog.info("Delete file unsuccessfully: " + strFile);
			cmsLog.info(ex.getMessage());
		}
		
		cmsLog.debug("Cms -> VodBillingImpl -> deleteFile returns.");
		return ireturn;
	}
	
	// 文件处理，迁移处理完的txt文件
	public void moveTxtFile(String pathFrom, String pathTo)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> moveTxtFile...");
		// 暂时不使用
		
		cmsLog.info("Cbs -> VodBillingImpl -> moveTxtFile returns.");
	}
	
	// 查询所有需要分析的点播流水
	private List<VodFlow> getNotDealedVodFlows(int firstResult, int maxResults)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> getNotDealedVodFlows...");
		List<VodFlow> vodFlows = new ArrayList<VodFlow>();
		
		vodFlows = vodFlowManager.getNotDealedVodFlowsWithCount(firstResult, maxResults);
		
		cmsLog.info("Cbs -> VodBillingImpl -> getNotDealedVodFlows returns.");
		return vodFlows;
	}
	
	// 筛选点播流水
	private List<VodFlow> getNeedDealingVodFlows(List<VodFlow> allVodFlows)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> getNeedDealingVodFlows...");
		List<VodFlow> vodFlows = new ArrayList<VodFlow>();
		
		vodFlows = allVodFlows;
		
		cmsLog.info("Cbs -> VodBillingImpl -> getNeedDealingVodFlows returns.");
		return vodFlows;
	}

	// 根据文件ID，查询得到产品列表
	private List<ProductCategory> getProductCategoriesByProgfileid(String progfileid)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> getProductCategoriesByProgfileid...");
		List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
		
		productCategories = productCategoryManager.getProductCategoriesByProgfileid(progfileid);
		
		cmsLog.info("Cbs -> VodBillingImpl -> getProductCategoriesByProgfileid returns.");
		return productCategories;
	}
	
	// 根据客户ID，查询得到客户订购产品列表
	private List getProductsByCustomerid(Long customerid)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> getProductsByProgfileId...");
		List list = new ArrayList();
		
		list = productManager.getNormalStatusProductsByCustomerId(customerid);
		
		cmsLog.info("Cbs -> VodBillingImpl -> getProductsByProgfileId returns.");
		return list;
	}
	
	// 比较产品列表
	private List getSameProducts(
			List<ProductCategory> progProductCategoryList, 		// 文件所属产品列表
			List customerProductList							// 客户订购产品列表，包含Product和ProductCategory
			)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> getSameProductCategories...");
		List retList = new ArrayList();
		List sameProducts = new ArrayList();
		List sameProductCategories = new ArrayList();
		
//		List customerProducts = (List)customerProductList.get(0);
//		List customerProductCategories = (List)customerProductList.get(1);
		
		for(int i = 0; i < progProductCategoryList.size(); i++)
		{
			ProductCategory progPc = progProductCategoryList.get(i);
			
			for(int j = 0; j < customerProductList.size(); j++)
			{
				Product customerPt = (Product)customerProductList.get(j);
//				ProductCategory customerPc = (ProductCategory)customerProductCategories.get(j);
				
				if((long)progPc.getProductCategoryId() == (long)customerPt.getProductCategoryId())
				{
					sameProducts.add(customerPt);
					sameProductCategories.add(progPc);
//					break;
				}
			}
		}
		retList.add(sameProducts);
		retList.add(sameProductCategories);
		
		cmsLog.info("Cbs -> VodBillingImpl -> getSameProductCategories returns.");
		return retList;
	}
	
	// 分析得到优先级最高的产品
	private List getBillingProduct(List products, List productCategories)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> getSameProductCategories...");
		List retList = new ArrayList();
		Product billingProduct = new Product();
		ProductCategory billingProductCategory = new ProductCategory();
		
		if(products.size() > 0)
		{
			billingProduct = (Product)products.get(0);
			billingProductCategory = (ProductCategory)productCategories.get(0);
		}
		
		retList.add(billingProduct);
		retList.add(billingProductCategory);
		cmsLog.info("Cbs -> VodBillingImpl -> getSameProductCategories returns.");
		return retList;
	}
	
	// 计算优惠
	private VodHistory calculateCampaign(
			VodHistory vodHistory, 
			Customer customer,
			Product billingProduct,
			ProductCategory billingProductCategory
			)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> calculateCampaign...");

		// 查询产品的价格
		Price price = (Price)priceManager.getById(billingProductCategory.getPriceId().toString());
		Double fee = 0.0;
		
		// 查询账户信息
		Account account = (Account)accountManager.getById(billingProduct.getAccountId().toString());
		
		// BT00000001	基本单点计费
		// BT00000002	一次性购买
		// BT00000003	按天数计费
		// BT00000004	按次数计费		暂时不使用
		// BT00000005	按点播时长计费 	暂时不使用
		if(billingProductCategory.getBillingTypeId().equalsIgnoreCase("BT00000001"))
		{
			cmsLog.info("计费产品的计费方式为基本单点计费...");
			fee = price.getPrice();
			
			// 查询产品的优惠
			// 条件：
			// PRODUCT_CATEGORY_ID
			// SERVICE_TYPE
			// CUSTOMER_TYPE_COLLECTION
			// CUSTOMER_STATUS_COLLECTION
			// DISTRICT_COLLECTION
			// ACCOUNT_TYPE_COLLECTION
			// ACCOUNT_STATUS_COLLECTION
			// MOP_COLLECTION
			// VALID_FROM
			// VALID_TO
			// ACTIVE_TAG
			// 排序：
			// PRIORITY
			CampaignCategory campaignCategory = null;
			List campaignCategories = campaignCategoryManager.getCampaignCategories(vodHistory, customer, account);
			if(campaignCategories.size() > 0)
			{
				campaignCategory = (CampaignCategory)campaignCategories.get(0);
			}

			// 计算费用
			Double discount = Double.valueOf(1);
			if(campaignCategory != null)
			{
				discount = campaignCategory.getDiscount();
				fee = fee * discount;
				
				vodHistory.setCampaignCategoryId(campaignCategory.getCampaignCategoryId());
				
				cmsLog.info("计费优惠ID：" + campaignCategory.getCampaignCategoryId());
				cmsLog.info("计费优惠折扣：" + discount);
				cmsLog.info("计费优惠后费用：" + fee);
			}
			else
			{
				vodHistory.setCampaignCategoryId("0");
			}
			
			vodHistory.setAmount(fee);
		}
		else if(billingProductCategory.getBillingTypeId().equalsIgnoreCase("BT00000002"))
		{
			cmsLog.info("计费产品的计费方式为一次性购买...");
			fee = 0.0;
			vodHistory.setCampaignCategoryId("0");
			vodHistory.setAmount(fee);
		}
		else if(billingProductCategory.getBillingTypeId().equalsIgnoreCase("BT00000003"))
		{
			cmsLog.info("计费产品的计费方式为按天数计费...");
			fee = 0.0;
			vodHistory.setCampaignCategoryId("0");
			vodHistory.setAmount(fee);
		}
		else if(billingProductCategory.getBillingTypeId().equalsIgnoreCase("BT00000004"))
		{
			cmsLog.info("计费产品的计费方式为按次数计费...");
			fee = 0.0;
			vodHistory.setCampaignCategoryId("0");
			vodHistory.setAmount(fee);
		}
		else if(billingProductCategory.getBillingTypeId().equalsIgnoreCase("BT00000005"))
		{
			cmsLog.info("计费产品的计费方式为按点播时长计费...");
			fee = 0.0;
			vodHistory.setCampaignCategoryId("0");
			vodHistory.setAmount(fee);
		}

		cmsLog.info("Cbs -> VodBillingImpl -> calculateCampaign returns.");
		return vodHistory;
	}
	
	// 计算点播流水费用
	private VodHistory calculateVodFee(
			VodFlow vodFlow, 				// 点播流水
			Customer customer, 				// 客户信息
			List customerProductList,		// 客户订购产品列表，包含Product和ProductCategory
			List progProductCategoryList	// 文件ID所属的产品列表
			)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> calculateVodFee...");
		VodHistory vodHistory = new VodHistory();
		
		
		// 比较产品列表
		cmsLog.info("比较产品列表...");
		List sameList = getSameProducts(
				progProductCategoryList, 			// 文件所属产品列表
				customerProductList					// 客户订购产品列表，包含Product和ProductCategory
				);
		
		if(sameList == null || sameList.size() <= 0)
		{
			cmsLog.warn("产品列表没有交集。");
		}
		else
		{
			cmsLog.info("得到计费产品...");
			List sameProducts = (List)sameList.get(0);
			List sameProductCategories = (List)sameList.get(1);
			
			// 分析得到优先级最高的产品
			cmsLog.info("分析产品优先级...");
			List billingList = getBillingProduct(sameProducts, sameProductCategories);
			Product billingProduct = (Product)billingList.get(0);
			ProductCategory billingProductCategory = (ProductCategory)billingList.get(1);
			
			cmsLog.info("得到计费产品...");
			cmsLog.info("客户产品ID：" + billingProduct.getProductId());
			cmsLog.info("产品ID：" + billingProductCategory.getProductCategoryId());
			cmsLog.info("产品名称：" + billingProductCategory.getDescription());
			
			// 点播历史
			vodHistory.setCustomerId(vodFlow.getCustomerId());
			vodHistory.setAccountId(billingProduct.getAccountId());
			vodHistory.setImportDate(vodFlow.getImportDate());
			vodHistory.setProductCategoryId(billingProduct.getProductCategoryId());
			vodHistory.setProductId(billingProduct.getProductId());
			vodHistory.setProgfileId(vodFlow.getProgfileId());
			vodHistory.setServiceType(vodFlow.getServiceType());
			vodHistory.setStartTime(vodFlow.getStartTime());
			vodHistory.setStopTime(vodFlow.getStopTime());
			vodHistory.setDealState((long)0);
			vodHistory.setFtId((long)0);
	//		vodHistory.setCampaignCategoryId(null);
	//		vodHistory.setAmount(amount);
			
			// 计算优惠，得到点播历史，包含点播费用
			cmsLog.info("计算优惠，得到点播历史...");
			vodHistory = calculateCampaign(
					vodHistory, 
					customer, 
					billingProduct, 
					billingProductCategory
					);
		}
		cmsLog.info("Cbs -> VodBillingImpl -> calculateVodFee returns.");
		return vodHistory;
	}
	
	// 计算点播流水费用，按客户ID、文件ID排序，返回点播历史
	private List<VodHistory> calculateVodFees(List<VodFlow> vodFlows)
	{
		cmsLog.info("Cbs -> VodBillingImpl -> calculateVodFees...");
		List<VodHistory> vodHistories = new ArrayList<VodHistory>();
		
		// 为了提高查询效率，需要对相同客户进行一次性客户信息查询
		int exist = 0;
		List<Customer> customers = new ArrayList<Customer>();
		List customerProducts = new ArrayList();
		List<String> progfileids = new ArrayList<String>();
		List progProductCategories = new ArrayList();
		
		cmsLog.info("共有" + vodFlows.size() + "条点播流水...");
		for(int i = 0; i < vodFlows.size(); i++)
		{
			VodFlow vodFlow = vodFlows.get(i);
			Customer customer = new Customer();
			List customerProductList = new ArrayList();
			List<ProductCategory> progProductCategoryList = new ArrayList<ProductCategory>();
			
			cmsLog.info("处理第" + (i+1) + "条点播流水...");
			
			// 客户是否已经查询过
			exist = 0;
			for(int j = 0; j < customers.size(); j++)
			{
				Customer existCustomer = (Customer)customers.get(j);
				if(existCustomer.getCustomerId().toString().equalsIgnoreCase(vodFlow.getCustomerId().toString()))
				{
					customer = existCustomer;
					customerProductList = (List)customerProducts.get(j);
					exist = 1;
					break;
				}
			}
			if(exist == 0)
			{
				// 不存在，查询
				// 查询客户
				cmsLog.info("客户没有处理过，查询客户，客户ID：" + vodFlow.getCustomerId());
				customer = (Customer)customerManager.getById(vodFlow.getCustomerId().toString());
				
				// 查询客户订购产品列表，包含Product
				customerProductList = (List)getProductsByCustomerid(customer.getCustomerId());//.get(0);
				
				customers.add(customer);
				customerProducts.add(customerProductList);
			}
			
			// 查询文件ID对应的产品列表
			exist = 0;
			for(int j = 0; j < progfileids.size(); j++)
			{
				String progfileid = progfileids.get(j);
				if(progfileid.equalsIgnoreCase(vodFlow.getProgfileId()))
				{
					progProductCategoryList = (List)progProductCategories.get(j);
					exist = 1;
					break;
				}
			}
			if(exist == 0)
			{
				// 不存在，查询
				cmsLog.info("点播文件不在已处理列表，加入...文件ID：" + vodFlow.getProgfileId());
				
				// 查询文件ID所属产品列表
				progProductCategoryList = getProductCategoriesByProgfileid(vodFlow.getProgfileId());
				
				progfileids.add(vodFlow.getProgfileId());
				progProductCategories.add(progProductCategoryList);
			}
			
			// 计算点播费用，得到点播历史
			cmsLog.info("计算点播费用，得到点播历史...");
			VodHistory vodHistory = calculateVodFee(
					vodFlow, 					// 点播流水
					customer, 					// 客户信息
					customerProductList,		// 客户订购产品列表
					progProductCategoryList		// 文件ID所属的产品列表
					);
			vodHistories.add(vodHistory);
		}
		
		cmsLog.info("Cbs -> VodBillingImpl -> calculateVodFees returns.");
		return vodHistories;
	}

	
	
	// 导入点播流水，文件 --> 数据库记录
	public CbsResultDto importVodFlowsFromFilesToDB() throws Exception
	{
		cmsLog.info("Cbs -> VodBillingImpl -> importVodFlowsFromFilesToDB...");
		CbsResultDto cbsResultDto = new CbsResultDto();

		List<VodFlow> vodFlows = new ArrayList<VodFlow>();
		String newDir = "C:\\";
		
		// 遍历指定目录下所有txt文件。
		String dir = "C:/temp/consume/";
		
		cmsLog.info("获取路径下所有内容，" + dir);
//		getAllTxtFromDir(dir);
		File[] files = fileoper.listLocalFiles(dir);
		cmsLog.info("共有" + files.length + "个内容。");
		
		for(int i = 0; i < files.length; i++)
		{
			File txtFile = (File)files[i];
			String filename = txtFile.getName();
			
			cmsLog.info("处理第" + (i+1) + "个内容...");
			cmsLog.info("内容名称：" + filename);
			
			// 判断文件是否需要解析
			if(filename.charAt(filename.length() - 1) == '/')
			{
				cmsLog.info("文件或文件夹名称：" + filename);
				cmsLog.info("不是文件，不处理，跳过...");
				continue; 
			}
			String rearpart = filename.substring(filename.lastIndexOf('.') + 1, filename.length());
			if(rearpart == null || rearpart.equalsIgnoreCase("") || !rearpart.equalsIgnoreCase("log"))
			{
				cmsLog.info("文件名称：" + filename);
				cmsLog.info("不符合规定格式，不处理，跳过...");
				continue; 
			}
			
			// 查询Txt中所有点播流水
			List<VodFlow> vodFlowsInFile = getVodFlowByVodFlowTxt(txtFile.getPath());
			
			for(int j = 0; j < vodFlowsInFile.size(); j++)
			{
				VodFlow vodFlow = vodFlowsInFile.get(j);
				vodFlows.add(vodFlow);
			}
			
			// 复制接口文件
			//copyFile(txtFile.getPath(), newDir + txtFile.getName());
			String source = txtFile.getPath();
			String newname = txtFile.getName() + "__";
			source = source.replace('\\', '/');
			cmsLog.info("准备重命名文件...");
			cmsLog.info("源 - " + source);
			cmsLog.info("目标 - " + newname);
			int ret = fileoper.renameLocalFile(source, newname);
			
			if(ret == 0)
			{
				cmsLog.info("重命名文件成功。");
				// 生成点播流水记录
				CbsResultDto c = cbsTransactionManager.saveVodFlows(vodFlowManager, vodFlows);
				
				if(c.getResultCode() == 0)
				{
					cmsLog.info("点播流水已经成功保存到数据库。");
					// 删除接口文件
	//				deleteFile(txtFile.getPath() + "__");
					
				}
				else
				{
					cmsLog.warn("点播流水已经保存到数据库失败，准备重命名点播流水日志文件...");
					source += "__";
					newname += "error";
					cmsLog.info("准备重命名文件...");
					cmsLog.info("源 - " + source);
					cmsLog.info("目标 - " + newname);
					ret = fileoper.renameLocalFile(source, newname);
					if(ret == 0)
					{
						cmsLog.info("点播流水日志文件重命名成功。");
					}
					else
					{
						cmsLog.warn("点播流水日志文件重命名失败。");
					}
				}
			}
			else
			{
				cmsLog.info("重命名文件失败。");
			}
		}
		
		cmsLog.info("Cbs -> VodBillingImpl -> importVodFlowsFromFilesToDB returns.");
		return cbsResultDto;
	}
	
	// 点播流水筛选、费用计算，最后生成点播历史
	public CbsResultDto calculateVodFeeByVodFlow()
	{
		cmsLog.info("Cbs -> VodBillingImpl -> calculateVodFeeByVodFlow...");
		CbsResultDto cbsResultDto = new CbsResultDto();
		
		// 流程：
		// 分页查询所有需要分析的点播流水，条件：未处理状态，排序：客户ID、文件ID
		// 筛选点播流水，按客户ID、文件ID排序
		// 计算点播流水费用，按客户ID、文件ID排序
		// 生成点播历史记录，修改点播流水的状态
		// 每页最后一批记录（同一客户同一文件），需要结合下一页记录一起处理
		
		int firstResult = 0;
		int maxResults = 200;
		Long curCustomerId = Long.valueOf(0);		// 当前准备点播历史的客户ID
		String curProgfileid = "";					// 当前准备点播历史的文件ID
		List vodFlows = new ArrayList();			// 分页查询得到的点播流水列表
		List sameVfs = new ArrayList();				// 准备生成点播历史的点播流水列表
		
		do
		{
			// 分页查询所有需要分析的点播流水，按客户ID、文件ID排序
			cmsLog.info("分页查询未处理的点播流水...从" + firstResult + "条开始，共" + maxResults + "条");
			vodFlows = getNotDealedVodFlows(firstResult, maxResults);
			cmsLog.info("得到未处理的点播流水，共" + vodFlows.size() + "条记录。");
			
			firstResult = firstResult + maxResults;
			if(vodFlows.size() > 0)
			{
				for(int i = 0; i < vodFlows.size(); i++)
				{
					VodFlow vodFlow = (VodFlow)vodFlows.get(i);
					cmsLog.info("处理第" + (i+1) + "条记录...");
					cmsLog.info("客户ID：" + vodFlow.getCustomerId());
					cmsLog.info("点播时间：" + vodFlow.getStartTime());
					
					
					if(((long)curCustomerId == (long)vodFlow.getCustomerId() && curProgfileid.equalsIgnoreCase(vodFlow.getProgfileId()))
							|| (curCustomerId == 0 || curProgfileid.equalsIgnoreCase(""))
							)
					{
						// 同一客户同一文件的点播流水
						// 汇总点播历史，准备生成财务交易
						if(curCustomerId == 0 || curProgfileid.equalsIgnoreCase(""))
						{
							// 首条记录
							cmsLog.info("找到首条记录。");
							curCustomerId = vodFlow.getCustomerId();
							curProgfileid = vodFlow.getProgfileId();
						}
						
						// 加入准备生成财务交易的点播历史列表
						sameVfs.add(vodFlow);
					}
					else
					{
						// 新客户或新账户的点播流水
						// 此时，根据上一批点播流水sameVfs，筛选，计算，生成点播历史，修改点播流水状态
						// 清空准备生成点播历史的点播流水列表
						// 最后修改curCustomerId和curProgfileid为新的
						
						
						if(sameVfs.size() > 0)
						{
							cmsLog.info("准备保存点播流水记录...");
							cmsLog.info("客户ID：" + curCustomerId);
							
							// 筛选点播流水，按客户ID、文件ID排序
							cmsLog.info("筛选点播流水记录...");
							List<VodFlow> newVodFlows = getNeedDealingVodFlows(vodFlows);
							
							// 计算点播流水费用，按客户ID、文件ID排序
							cmsLog.info("计算点播费用...");
							List<VodHistory> vodHistories = calculateVodFees(newVodFlows);
							
							// 生成点播历史记录，修改点播流水的状态
							cmsLog.info("生成点播历史记录，修改点播流水的状态...");
							CbsResultDto c = cbsTransactionManager.saveVodHistories(
									vodHistoryManager, 
									vodHistoryTempManager, 
									vodFlowManager, 
									vodHistories, 
									newVodFlows
									);
							if(c.getResultCode() == (long)0)
							{
								cmsLog.info("生成点播历史记录，修改点播流水的状态成功。");
							}
							else
							{
								cmsLog.warn("生成点播历史记录，修改点播流水的状态失败。");
							}
							
							cmsLog.info("清空准备生成点播历史的点播流水列表...");
							sameVfs.clear();
						}
						
						curCustomerId = vodFlow.getCustomerId();
						curProgfileid = vodFlow.getProgfileId();
					}
				}
			}
			else
			{
				// 本次查询返回记录为0，为最后一次查询，
				// 根据上一批点播流水sameVfs，筛选，计算，生成点播历史，修改点播流水状态
				// 清空准备生成点播历史的点播流水列表
				// 最后修改curCustomerId和curProgfileid为0
				
				cmsLog.info("本次查询返回结果数量为0...");
				if(sameVfs.size() > 0)
				{
					cmsLog.info("准备保存点播流水记录...");
					cmsLog.info("客户ID：" + curCustomerId);
					
					// 筛选点播流水，按客户ID、文件ID排序
					cmsLog.info("筛选点播流水记录...");
					List<VodFlow> newVodFlows = getNeedDealingVodFlows(vodFlows);
					
					// 计算点播流水费用，按客户ID、文件ID排序
					cmsLog.info("计算点播费用...");
					List<VodHistory> vodHistories = calculateVodFees(newVodFlows);
					
					// 生成点播历史记录，修改点播流水的状态
					cmsLog.info("生成点播历史记录，修改点播流水的状态...");
					CbsResultDto c = cbsTransactionManager.saveVodHistories(
							vodHistoryManager, 
							vodHistoryTempManager, 
							vodFlowManager, 
							vodHistories, 
							newVodFlows
							);
					
					if(c.getResultCode() == (long)0)
					{
						cmsLog.info("生成点播历史记录，修改点播流水的状态成功。");
					}
					else
					{
						cmsLog.warn("生成点播历史记录，修改点播流水的状态失败。");
					}
					
					cmsLog.info("清空准备生成点播历史的点播流水列表...");
					sameVfs.clear();
				}
				
				curCustomerId = Long.valueOf(0);
				curProgfileid = "";
			}
		} while(vodFlows != null && vodFlows.size() > 0);

		cmsLog.info("Cbs -> VodBillingImpl -> calculateVodFeeByVodFlow returns.");
		return cbsResultDto;
	}

	
	// 20100330
	// 服务
	public CbsResultDto executePeriodly()
	{
		CbsResultDto cbsResultDto = new CbsResultDto();
		Date date = new Date();
		
		String strCurDay = fileoper.convertDateToString(date, "dd");
		
		// 每日执行，且当日只能执行一次
		if(lastDate == null)
		{
			try {
				cmsLog.info("执行导入点播流水到数据库...");
				importVodFlowsFromFilesToDB();
			} catch (Exception e) {
				cmsLog.info("异常：" + e.getMessage());
			}
			
			cmsLog.info("执行计算点播流水费用（生成点播历史）...");
			calculateVodFeeByVodFlow();
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
				try {
					cmsLog.info("执行导入点播流水到数据库...");
					importVodFlowsFromFilesToDB();
				} catch (Exception e) {
					cmsLog.info("异常：" + e.getMessage());
				}
				
				cmsLog.info("执行计算点播流水费用（生成点播历史）...");
				calculateVodFeeByVodFlow();
			}
		}
		
		
		lastDate = date;
		
		return cbsResultDto;
	}

	public void test()
	{
		try
		{
			getVodFlowByVodFlowTxt("D:\\ConsumeList.log");
		}
		catch(Exception ex)
		{
			
		}
	}
}
