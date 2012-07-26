package com.cbs.cbsmgr.serviceSimple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.bean.Hardware;
import com.cbs.cbsmgr.bean.PackageItem;
import com.cbs.cbsmgr.bean.Price;
import com.cbs.cbsmgr.bean.Product;
import com.cbs.cbsmgr.bean.ProductCategory;
import com.cbs.cbsmgr.bean.ProductPackage;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.dto.HardwareProductDisplayDTO;
import com.cbs.cbsmgr.dto.PackageItemDTO;
import com.cbs.cbsmgr.dto.ProductDTO;
import com.cbs.cbsmgr.dto.ProductDisplayDTO;
import com.cbs.cbsmgr.manageriface.IAccountManager;
import com.cbs.cbsmgr.manageriface.ICustomerManager;
import com.cbs.cbsmgr.manageriface.IHardwareManager;
import com.cbs.cbsmgr.manageriface.IHardwareModelManager;
import com.cbs.cbsmgr.manageriface.IPackageItemManager;
import com.cbs.cbsmgr.manageriface.IPackageManager;
import com.cbs.cbsmgr.manageriface.IPriceManager;
import com.cbs.cbsmgr.manageriface.IProductCategoryManager;
import com.cbs.cbsmgr.manageriface.IProductManager;
import com.cbs.cbsmgr.serviceBackground.SrmmgrServiceImpl;
import com.cbs.cbsmgr.serviceimpl.AccountService;
import com.cbs.cbsmgr.serviceimpl.PackageItemService;
import com.cbs.cbsmgr.serviceimpl.ProductPackageService;
import com.cbs.cbsmgr.serviceimpl.ProductService;

public class SessionProductImpl implements SessionProductIface {

	public IAccountManager accountManager = null;
	public IProductManager productManager = null;
	public IPackageItemManager packageItemManager = null;
	public IPackageManager packageManager = null;
	public IProductCategoryManager productCategoryManager = null;
	public IPriceManager priceManager = null;
	public IHardwareManager hardwareManager = null;
	public ICustomerManager customerManager = null;
	
	public SessionProductImpl()
	{
		productManager = (IProductManager)ApplicationContextHolder.webApplicationContext.getBean("productManager");
		packageItemManager = (IPackageItemManager)ApplicationContextHolder.webApplicationContext.getBean("packageItemManager");
		packageManager = (IPackageManager)ApplicationContextHolder.webApplicationContext.getBean("packageManager");
		accountManager = (IAccountManager)ApplicationContextHolder.webApplicationContext.getBean("accountManager");
		productCategoryManager = (IProductCategoryManager)ApplicationContextHolder.webApplicationContext.getBean("productCategoryManager");
		priceManager = (IPriceManager)ApplicationContextHolder.webApplicationContext.getBean("priceManager");
		hardwareManager = (IHardwareManager)ApplicationContextHolder.webApplicationContext.getBean("hardwareManager");
		customerManager = (ICustomerManager)ApplicationContextHolder.webApplicationContext.getBean("customerManager");
	}
	


	public Long capturePackage(Long accountId, Long packageId) {
		// 捕获产品包
		
		// 获取Package_Item
//		Collection collection = null;
		List packageItems = packageItemManager.findByProperty("packageId",packageId);
		
		Iterator iPackageItem = packageItems.iterator();
		
		// 生成product记录
		for (int i = 0; i < packageItems.size(); i++)
		{
			PackageItem packageItem = (PackageItem) iPackageItem.next();
			
			ProductDTO productDTO = composeProductDTO(packageItem);
			Long lProductId = captureProduct(accountId, productDTO);
			
			// 修改产品状态
//			changeProductStatus(lProductId, (long)1);
		}

		return null;
	}
	
	public Long captureProduct(Long accountId, ProductDTO productDTO)
	{
		Long retProductId = (long)0;
		// 判断accountId，是否已经有ProductCategoryId
		List curProducts = productManager.getProductByAccountIdAndProductCategoryId(
				accountId, 
				productDTO.getProductCategoryId()
				);
		
		if(curProducts.size() != 0)
		{
			// 如果有，得到productId，修改产品状态为“正常”
			Product curProduct = (Product)curProducts.get(0);
			reconnectProduct(curProduct.getProductId());
			retProductId = curProduct.getProductId();
		}
		else
		{
			// 捕获单个产品
			Account account = (Account)accountManager.getById(accountId.toString());
			Product product = new Product();
			product.setAccountId(accountId);
			product.setCaptureDate(new Date());
			product.setCustomerId(account.getCustomerId());
	//		product.setOldStatusId(productDTO.getOldStatusId());
			product.setOldStatusId((long)0);
			product.setProductCategoryId(productDTO.getProductCategoryId());
			product.setProductStatusId((long)1);
	//		product.setStatusChangeDate(productDTO.getStatusChangeDate());
			product.setStatusChangeDate(null);
			
			product = (Product)productManager.save(product);
			
			retProductId = product.getProductId();
		}
		
		return retProductId;
	}
	
	public ProductDTO composeProductDTO(PackageItem packageItem)
	{
		ProductDTO productDTO = new ProductDTO();
		productDTO.setCaptureDate(new Date());
		productDTO.setOldStatusId((long)0);
		productDTO.setPackageItemId(packageItem.getPackageItemId());
		productDTO.setProductCategoryId(packageItem.getProductCategoryId());
		productDTO.setProductStatusId((long)0);
		productDTO.setStatusChangeDate(null);
		
		return productDTO;
	}

	// 系统暂停产品
	public Long disconnectProduct(Long productId) {
		changeProductStatus(productId, (long)3);
		return null;
	}

	// 恢复产品
	public Long reconnectProduct(Long productId) {
		changeProductStatus(productId, (long)1);
		return null;
	}

	// 用户暂停产品
	public Long suspendProduct(Long productId) {
		changeProductStatus(productId, (long)2);
		return null;
	}

	// 取消产品
	public Long cancelProduct(Long productId) {
		changeProductStatus(productId, (long)4);
		return null;
	}
	
	// 修改产品状态
	public Long changeProductStatus(Long productId, Long newStatus)
	{
		//ProductService productService = new ProductService();
		Product product = (Product)productManager.getById(productId.toString());
		product.setOldStatusId(product.getProductStatusId());
		product.setStatusChangeDate(new Date());
		product.setProductStatusId(newStatus);
		
		productManager.update(product);
		return product.getProductId();
	}
	
	// 根据客户ID得到(套餐)产品列表
	public List getProductsByCustomerId(Long customerId)
	{
		List products = (List)productManager.findByProperty("customerId", customerId);
		return products;
	}
	
	// 根据customerId，得到ProductDisplayDTO列表
	public List getProductDisplayDTOsByPackageId(Long customerId)
	{
		List products = (List)productManager.findByProperty("customerId", customerId);
		List productDisplayDTOs = new ArrayList();
		Iterator iProducts = products.iterator();
		for (int i = 0; i < products.size(); i++)
		{
			ProductDisplayDTO productDisplayDTO = new ProductDisplayDTO();
			Product product = (Product)iProducts.next();
			productDisplayDTO.setAccountId(product.getAccountId());
			productDisplayDTO.setCaptureDate(product.getCaptureDate());
			productDisplayDTO.setCustomerId(product.getCustomerId());
			productDisplayDTO.setOldStatusId(product.getOldStatusId());
			productDisplayDTO.setProductCategoryId(product.getProductCategoryId());
			productDisplayDTO.setProductId(product.getProductId());
			productDisplayDTO.setProductStatusId(product.getProductStatusId());
			productDisplayDTO.setStatusChangeDate(product.getStatusChangeDate());
			if(product.getProductCategoryId() > 0)
			{
				productDisplayDTO.setProductCategory(
						getProductCategoryByProductCategoryId(product.getProductCategoryId()).getDescription());
			}
			else
			{
				productDisplayDTO.setProductCategory("");
			}
			
			if(product.getProductCategoryId() > 0)
			{
				productDisplayDTOs.add(productDisplayDTO);
			}
		}
		return productDisplayDTOs;
	}
	
	// 根据ProductCategoryId，得到ProductCategory
	public ProductCategory getProductCategoryByProductCategoryId(Long productCategoryId)
	{
		ProductCategory productCategory = null;
		if(productCategoryId > 0)
		{
			productCategory = (ProductCategory)productCategoryManager.getById(productCategoryId.toString());
		}
		else
		{
			productCategory = null;
		}
		return productCategory;
	}
	
	// 根据ProductStatusId，得到ProductStatus
	public String getProductStatusByProductStatusId(Long productStatusId)
	{
		String ProductStatus = "";
		if(productStatusId == 1)
		{
			ProductStatus = "正常";
		}
		else if(productStatusId == 2)
		{
			ProductStatus = "销户";
		}
		return ProductStatus;
	}
	
	// 根据ProductID得到(套餐)产品
	public Product getProductByProductId(Long productId)
	{
		Product product = (Product)productManager.getById(productId.toString());
		return product;
	}
	
	// 得到所有产品包列表
	public List getAllPackages()
	{
		List productPackages = (List)packageManager.findAll();
		return productPackages;
	}
	
	// 得到允许捕获的产品包列表
	public List getAllowCapturePackages()
	{
		// 另外需要加上允许捕获时间
		List productPackages = (List)packageManager.findByProperty("allowCaptureTag", "Y");
		return productPackages;
	}
	
	// 根据Package_Id，得到packageItemDTOs列表
	public List getPackageItemByPackageId(Long packageId)
	{
		List packageItems = (List)packageItemManager.findByProperty("packageId", packageId);
		Iterator iPackageItem = packageItems.iterator();
		
		List packageItemDTOs = new ArrayList();
		// 生成product记录
		for (int i = 0; i < packageItems.size(); i++)
		{
			PackageItem packageItem = (PackageItem) iPackageItem.next();
			ProductCategory productCategory = (ProductCategory)productCategoryManager.getById(
					packageItem.getProductCategoryId().toString());

			Price price = (Price)priceManager.getById(
					productCategory.getPriceId().toString());
			
			PackageItemDTO packageItemDTO = new PackageItemDTO();
			packageItemDTO.setPackageItemId(packageItem.getPackageItemId());
			packageItemDTO.setProductCategoryId(packageItem.getProductCategoryId());
			packageItemDTO.setDescription(productCategory.getDescription());
			packageItemDTO.setPrice(price.getPrice());
			
			packageItemDTOs.add(packageItemDTO);
		}
		
		return packageItemDTOs;
	}
	
	
	
	//*****************************************************************************************
	
	//-------------------------------------------------------------------------------------------------
	// 创建产品包
	public Long createPackage(String description, String allowCaptureTag, 
			Date allowCaptureDateFrom, Date allowCaptureDateTo)
	{
		ProductPackage productPackage = new ProductPackage();
		
		productPackage.setDescription(description);
		productPackage.setAllowCaptureTag(allowCaptureTag);
		productPackage.setAllowCaptureDateFrom(allowCaptureDateFrom);
		productPackage.setAllowCaptureDateTo(allowCaptureDateTo);
	
		productPackage = (ProductPackage)packageManager.save(productPackage);
		
		return productPackage.getPackageId();
	}
	
	// 修改产品
	public void modifyPackage(Long packageId, String description, String allowCaptureTag, 
			Date allowCaptureDateFrom, Date allowCaptureDateTo)
	{
		ProductPackage productPackage = (ProductPackage)packageManager.getById(packageId.toString());
		productPackage.setDescription(description);
		productPackage.setAllowCaptureTag(allowCaptureTag);
		productPackage.setAllowCaptureDateFrom(allowCaptureDateFrom);
		productPackage.setAllowCaptureDateTo(allowCaptureDateTo);
	
		packageManager.update(productPackage);
	}
	
	// 创建产品包单项
	public Long createPackageItem(Long packageId, Long sequence, Long productCategoryId)
	{
		PackageItem packageItem = new PackageItem();
		
		packageItem.setPackageId(packageId);
		packageItem.setSequence(sequence);
		packageItem.setProductCategoryId(productCategoryId);
		
		packageItem = (PackageItem)packageItemManager.save(packageItem);
		
		return packageItem.getPackageItemId();
	}
	
	// 修改产品包单项
	//public void modifyPackageItem(Long packageItemId);
	
	// 删除产品包单项
	public void deletePackageItem(Long packageItemId)
	{
		packageItemManager.deleteById(packageItemId.toString());
	}
	//-------------------------------------------------------------------------------------------------
	
	// 根据客户Id查询硬件product
	public List getHardwareProductsByCustomerId(Long customerId)
	{
		// 返回类型：HardwareProductDisplayDTO
		// 查询条件：customerId
		// 查询表：product,hardware,hardwareModel
		// hql:
//		select distinct(pt.productId),pt.accountId,pt.hardwareId,pt.productStatusId,pt.oldStatusId,pt.statusChangeDate,pt.captureDate,hr.hardwareModelId,hr.serialNo,hr.cardNo,hr.otherNo,hr.hardwareStatusId,hr.guaranteeDate,hm.hardwareModelName
//		from Product pt, Hardware hr, HardwareModel hm
//		where pt.customerId = :customerId and pt.hardwareId = hr.hardwareId and hr.hardwareModelId = hm.hardwareModelId
//		order by pt.productId
		
		List hardwareProductDisplayDTOs = new ArrayList();
		List hardwareProducts = productManager.getHardwareProductsByCustomerId(customerId);
		for(int i = 0; i < hardwareProducts.size(); i++)
		{
			HardwareProductDisplayDTO hardwareProductDisplayDTO = new HardwareProductDisplayDTO();
			Object[] rows = (Object[])hardwareProducts.get(i);
			
			hardwareProductDisplayDTO.setProductId((Long)rows[0]);
			hardwareProductDisplayDTO.setCustomerId(customerId);
			hardwareProductDisplayDTO.setAccountId((Long)rows[1]);
			hardwareProductDisplayDTO.setHardwareId((String)rows[2]);
			hardwareProductDisplayDTO.setProductStatusId((Long)rows[3]);
			hardwareProductDisplayDTO.setOldStatusId((Long)rows[4]);
			hardwareProductDisplayDTO.setStatusChangeDate((Date)rows[5]);
			hardwareProductDisplayDTO.setCaptureDate((Date)rows[6]);
			hardwareProductDisplayDTO.setHardwareModelId((String)rows[7]);
			hardwareProductDisplayDTO.setSerialNo((String)rows[8]);
			hardwareProductDisplayDTO.setCardNo((String)rows[9]);
			hardwareProductDisplayDTO.setOtherNo((String)rows[10]);
			hardwareProductDisplayDTO.setHardwareStatusId((Long)rows[11]);
			hardwareProductDisplayDTO.setGuaranteeDate((Date)rows[12]);
			hardwareProductDisplayDTO.setHardwareModelName((String)rows[13]);
			hardwareProductDisplayDTO.setHardwareType((Long)rows[14]);
			hardwareProductDisplayDTO.setMac((String)rows[15]);
			hardwareProductDisplayDTO.setIp((String)rows[16]);
			hardwareProductDisplayDTO.setUserName((String)rows[17]);
			hardwareProductDisplayDTO.setPassword((String)rows[18]);
			
			hardwareProductDisplayDTOs.add(hardwareProductDisplayDTO);
		}
		
		return hardwareProductDisplayDTOs;
	}
	
	// 添加硬件到客户下
	public void captureHardwareProductToAccountId(Long accountId, Hardware hardware)
	{
		// 首先判断username是否已经存在
		// 判断Hardware表中，是否有该硬件的记录
		// 如果没有，可以捕获
		// 如果有，判断硬件的硬件状态
		// 如果是“使用”或“返修”状态，则不能捕获
		// 如果是“库存”或“修好”状态，可以捕获。
		
		boolean bAllowCapture = false;
		String serialNo = hardware.getSerialNo();
		String cardNo = hardware.getCardNo();
		String otherNo = hardware.getOtherNo();
		List hardwares = null;
		
		if(hardware.getUserName() != null && !hardware.getUserName().equalsIgnoreCase(""))
		{
			hardwares = hardwareManager.findByProperty("userName", hardware.getUserName());
		}
		if(hardwares == null || hardwares.size() == 0)
		{
			bAllowCapture = true;
		}
		else
		{
			// username有重复
		}
		
		if(serialNo != null && !serialNo.equalsIgnoreCase(""))
		{
			hardwares = hardwareManager.getHardwaresByHardwareModelIdAndSerialNo(hardware.getHardwareModelId(), serialNo);
		}
		else if(cardNo != null && !cardNo.equalsIgnoreCase(""))
		{
			hardwares = hardwareManager.getHardwaresByHardwareModelIdAndCardNo(hardware.getHardwareModelId(), cardNo);
		}
		else if(otherNo != null && !otherNo.equalsIgnoreCase(""))
		{
			hardwares = hardwareManager.getHardwaresByHardwareModelIdAndOtherNo(hardware.getHardwareModelId(), otherNo);
		}
		
		if(hardwares == null || hardwares.size() == 0)
		{
			bAllowCapture = true;
		}
		else
		{
			// 库存、使用、返修、修好
			Hardware existHardware = (Hardware)hardwares.get(0);
			if(existHardware.getHardwareStatusId() == 2 || existHardware.getHardwareStatusId() == 3)
			{
				bAllowCapture = false;
			}
			else if(existHardware.getHardwareStatusId() == 1 || existHardware.getHardwareStatusId() == 4)
			{
				bAllowCapture = true;
			}
		}
		
		if(bAllowCapture == true)
		{
			Account account = (Account)accountManager.getById(accountId.toString());
			Customer customer = (Customer)customerManager.getById(account.getCustomerId().toString());
			
			hardware.setHardwareStatusId((long)2);
			hardware = (Hardware)hardwareManager.save(hardware);
			
			Product product = new Product();
			product.setAccountId(accountId);
			product.setCaptureDate(new Date());
			product.setCustomerId(account.getCustomerId());
			product.setHardwareId(hardware.getHardwareId());
			product.setOldStatusId((long)0);
			product.setProductCategoryId((long)0);
			product.setProductStatusId((long)1);
			product.setStatusChangeDate(null);
			
			product = (Product)productManager.save(product);
			
			// 把客户信息导入到srm
			SrmmgrServiceImpl service = new SrmmgrServiceImpl();
			service.setCustomerInfoFromCbsToSrm(customer, hardware);
			System.out.println("已保存到Srm。");
		}
	}
	
	// 添加硬件到客户下
	public void captureHardwareProductToCustomerId(Long customerId, Hardware hardware)
	{
		// 判断Hardware表中，是否有该硬件的记录
		// 如果没有，可以捕获
		// 如果有，判断硬件的硬件状态
		// 如果是“使用”或“返修”状态，则不能捕获
		// 如果是“库存”或“修好”状态，可以捕获。
		
		boolean bAllowCapture = false;
		String serialNo = hardware.getSerialNo();
		String cardNo = hardware.getCardNo();
		String otherNo = hardware.getOtherNo();
		List hardwares = null;
		if(serialNo != null && !serialNo.equalsIgnoreCase(""))
		{
			hardwares = hardwareManager.getHardwaresByHardwareModelIdAndSerialNo(hardware.getHardwareModelId(), serialNo);
		}
		else if(cardNo != null && !cardNo.equalsIgnoreCase(""))
		{
			hardwares = hardwareManager.getHardwaresByHardwareModelIdAndCardNo(hardware.getHardwareModelId(), cardNo);
		}
		else if(otherNo != null && !otherNo.equalsIgnoreCase(""))
		{
			hardwares = hardwareManager.getHardwaresByHardwareModelIdAndOtherNo(hardware.getHardwareModelId(), otherNo);
		}
		
		if(hardwares == null || hardwares.size() == 0)
		{
			bAllowCapture = true;
		}
		else
		{
			// 库存、使用、返修、修好
			Hardware existHardware = (Hardware)hardwares.get(0);
			if(existHardware.getHardwareStatusId() == 2 || existHardware.getHardwareStatusId() == 3)
			{
				bAllowCapture = false;
			}
			else if(existHardware.getHardwareStatusId() == 1 || existHardware.getHardwareStatusId() == 4)
			{
				bAllowCapture = true;
			}
		}
		
		if(bAllowCapture == true)
		{
			Customer customer = (Customer)customerManager.getById(customerId.toString());
			
			hardware.setHardwareStatusId((long)2);
			hardware = (Hardware)hardwareManager.save(hardware);
			
			Product product = new Product();
			product.setAccountId((long)0);
			product.setCaptureDate(new Date());
			product.setCustomerId(customerId);
			product.setHardwareId(hardware.getHardwareId());
			product.setOldStatusId((long)0);
			product.setProductCategoryId((long)0);
			product.setProductStatusId((long)1);
			product.setStatusChangeDate(null);
			
			product = (Product)productManager.save(product);
			
			// 把客户信息导入到srm
			SrmmgrServiceImpl service = new SrmmgrServiceImpl();
			service.setCustomerInfoFromCbsToSrm(customer, hardware);
			System.out.println("已保存到Srm。");
		}
	}
}
