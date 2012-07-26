package com.cbs.cbsmgr.serviceSimple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cbs.cbsmgr.bean.HardwareModel;
import com.cbs.cbsmgr.bean.PackageItem;
import com.cbs.cbsmgr.bean.ProductCategory;
import com.cbs.cbsmgr.bean.ProductPackage;
import com.cbs.cbsmgr.bean.VodCbsProductRel;
import com.cbs.cbsmgr.dto.PackageItemDisplayDTO;
import com.cbs.cbsmgr.dto.ProductCategoryDisplayDTO;
import com.cbs.cbsmgr.manageriface.ICbsTransactionManager;
import com.cbs.cbsmgr.manageriface.IHardwareModelManager;
import com.cbs.cbsmgr.manageriface.IPackageItemManager;
import com.cbs.cbsmgr.manageriface.IPackageManager;
import com.cbs.cbsmgr.manageriface.IPpSrvPdtRelManager;
import com.cbs.cbsmgr.manageriface.IPriceManager;
import com.cbs.cbsmgr.manageriface.IProductCategoryManager;
import com.cbs.cbsmgr.manageriface.IVodCbsProductRelManager;
import com.cbs.cbsmgr.manageriface.IVodProductManager;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.manageriface.ISrvProductManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class ConfigProductImpl implements ConfigProductIface {

	public IProductCategoryManager productCategoryManager = null;
	public IPriceManager priceManager = null;
	public IPackageItemManager packageItemManager = null;
	public IPackageManager packageManager = null;
	public IVodCbsProductRelManager vodCbsProductRelManager = null;
	public IVodProductManager vodProductManager = null;
	public IHardwareModelManager hardwareModelManager = null;
	public IPpSrvPdtRelManager ppSrvPdtRelManager = null;
	public ISrvProductManager srvProductManager = null;
	
	public ICbsTransactionManager cbsTransactionManager = null;
	
	public ConfigProductImpl()
	{
		packageItemManager = (IPackageItemManager)ApplicationContextHolder.webApplicationContext.getBean("packageItemManager");
		packageManager = (IPackageManager)ApplicationContextHolder.webApplicationContext.getBean("packageManager");
		productCategoryManager = (IProductCategoryManager)ApplicationContextHolder.webApplicationContext.getBean("productCategoryManager");
		priceManager = (IPriceManager)ApplicationContextHolder.webApplicationContext.getBean("priceManager");
		vodCbsProductRelManager = (IVodCbsProductRelManager)ApplicationContextHolder.webApplicationContext.getBean("vodCbsProductRelManager");
		vodProductManager = (IVodProductManager)ApplicationContextHolder.webApplicationContext.getBean("vodProductManager");
		hardwareModelManager = (IHardwareModelManager)ApplicationContextHolder.webApplicationContext.getBean("hardwareModelManager");
		ppSrvPdtRelManager = (IPpSrvPdtRelManager)ApplicationContextHolder.webApplicationContext.getBean("ppSrvPdtRelManager");
		srvProductManager = (ISrvProductManager)ApplicationContextHolder.webApplicationContext.getBean("srvProductManager");
		
		cbsTransactionManager = (ICbsTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("cbsTransactionManager");
	}
	
	
	// 创建产品（ProductCategory）
	public void createProductCategory(ProductCategory productCategory)
	{
		// 
		productCategory.setCreateDate(new Date());
		productCategory = (ProductCategory)productCategoryManager.save(productCategory);
		System.out.println("createProductCategory:" + productCategory.getProductCategoryId().toString());
	}
	
	// 修改产品（ProductCategory）
	public void modifyProductCategory(ProductCategory productCategory)
	{
		ProductCategory curProductCategory = new ProductCategory();
		curProductCategory = (ProductCategory)productCategoryManager.getById(productCategory.getProductCategoryId().toString());

		if(curProductCategory != null)
		{
			// 以下字段不允许修改
			productCategory.setCreateDate(curProductCategory.getCreateDate());
			productCategory.setCreateUser(curProductCategory.getCreateUser());
			
			System.out.println("modifyProductCategory:" + productCategory.getProductCategoryId().toString());
		}
		else
		{
			System.out.println("产品不存在，没有修改产品。");
		}
		productCategoryManager.update(productCategory);
	}
	
	// 查询产品（ProductCategory）
	public List getProductCategoryDisplayDTOsByDescription(String description)
	{
		// 返回类型：ProductCategoryDisplayDTO
		// 条件：ProductCategory.description
		// 查询表：ProductCategory,Price,SmsAccount
		
		// hql:
//		select pc.productCategoryId,pc.description,pc.productTypeId,pc.priceId,p.description,p.price,sa.smsAccountId,sa.description
//		from ProductCategory pc,Price p,SmsAccount sa 
//		where pc.description like :description and pc.priceId = p.priceId and p.smsAccountId = sa.smsAccountId
		
		// hql:select_productCategoriesByDescription,select_allProductCategories , 20091026
		
//		select pc.productCategoryId,pc.description,pc.productTypeId,pc.priceId,p.description,
//		p.price,pc.introduction,pc.billingTypeId,pc.contentCategoryId,pc.days,pc.counts,
//		pc.seconds,pc.validFrom,pc.validTo,pc.createUser,pc.createDate,
//		sa.smsAccountId,sa.description,
//		pt.description,pt.hardwareTag,cc.description,bt.description
//		from ProductCategory pc,Price p,SmsAccount sa,ProductType pt,ContentCategory cc,BillingType bt 
//		where pc.description like :description 
//		and pc.priceId = p.priceId 
//		and p.smsAccountId = sa.smsAccountId
//		and pc.productTypeId = pt.productTypeId
//		and pc.contentCategoryId = cc.contentCategoryId
//		and pc.billingTypeId = bt.billingTypeId
		
		List productCategoryDisplayDTOs = new ArrayList();
		List productCategories = productCategoryManager.getProductCategoriesByDescription(description);
		for(int i = 0; i < productCategories.size(); i++)
		{
			ProductCategoryDisplayDTO productCategoryDisplayDTO = new ProductCategoryDisplayDTO();
			Object[] rows = (Object[])productCategories.get(i);
			
			productCategoryDisplayDTO.setProductCategoryId((Long)rows[0]);
			productCategoryDisplayDTO.setDescription((String)rows[1]);
			productCategoryDisplayDTO.setProductTypeId((Long)rows[2]);
			productCategoryDisplayDTO.setPriceId((Long)rows[3]);
			productCategoryDisplayDTO.setPriceDescription((String)rows[4]);
			productCategoryDisplayDTO.setPrice((Double)rows[5]);
			productCategoryDisplayDTO.setIntroduction((String)rows[6]);
			productCategoryDisplayDTO.setBillingTypeId((String)rows[7]);
			productCategoryDisplayDTO.setContentCategoryId((String)rows[8]);
			productCategoryDisplayDTO.setDays((Long)rows[9]);
			productCategoryDisplayDTO.setCounts((Long)rows[10]);
			productCategoryDisplayDTO.setSeconds((Long)rows[11]);
			productCategoryDisplayDTO.setValidFrom((Date)rows[12]);
			productCategoryDisplayDTO.setValidTo((Date)rows[13]);
			productCategoryDisplayDTO.setCreateUser((String)rows[14]);
			productCategoryDisplayDTO.setCreateDate((Date)rows[15]);
			productCategoryDisplayDTO.setSmsAccountId((Long)rows[16]);
			productCategoryDisplayDTO.setSmsAccountDescription((String)rows[17]);
			productCategoryDisplayDTO.setProductTypeDes((String)rows[18]);
			productCategoryDisplayDTO.setHardwareTag((String)rows[19]);
			productCategoryDisplayDTO.setContentCategoryDes((String)rows[20]);
			productCategoryDisplayDTO.setBillingTypeDes((String)rows[21]);
			productCategoryDisplayDTO.setCode((String)rows[22]);

			productCategoryDisplayDTOs.add(productCategoryDisplayDTO);
		}

		return productCategoryDisplayDTOs;
	}

	// 创建产品包
	public void createProductPackage(ProductPackage productPackage)
	{
		productPackage = (ProductPackage)packageManager.save(productPackage);
		System.out.println("createProductPackage:" + productPackage.getPackageId().toString());
	}
	
	// 修改产品包
	public void modifyProductPackage(ProductPackage productPackage)
	{
		packageManager.update(productPackage);
		System.out.println("modifyProductPackage:" + productPackage.getPackageId().toString());
	}
	
	// 查询产品包
	public List getProductPackagesByDescription(String description)
	{
		// 返回类型：ProductPackage
		// 条件：模糊Package.description
		// 查询表：Package
		// hql:
//		select ditinct(p)
//		from Package p
//		where p.description like :description
//		order by p.packageId
		
	 	List packages = packageManager.getPackagesByDescription(description);
		
		return packages;
	}
	
	// 创建产品包单项
	public void createPackageItem(PackageItem packageItem)
	{
		packageItem = (PackageItem)packageItemManager.save(packageItem);
		System.out.println("createPackageItem:" + packageItem.getPackageItemId().toString());
	}
	
	// 修改产品包单项
	public void modifyPackageItem(PackageItem packageItem)
	{
		packageItemManager.update(packageItem);
		System.out.println("modifyPackageItem:" + packageItem.getPackageItemId().toString());
	}
	
	// 删除产品包单项
	public void deletePackageItem(Long packageItemId)
	{
		packageItemManager.deleteById(packageItemId.toString());
		System.out.println("deletePackageItem:" + packageItemId.toString());
	}
	
	// 查询产品包单项
	public List getPackageItems(Long packageId)
	{
		// 返回类型：PackageItemDisplayDTO
		// 条件：packageId
		// 查询表：PackageItem,productcategory,price
		// hql:
//		select pi.packageItemId,pi.sequence,pc.productCategoryId,pc.description,p.priceId,p.description,p.price
//		from PackageItem pi, ProductCategory pc, Price p
//		where pi.packageId = :packageId and pi.productCategoryId = pc.productCategoryId and pc.priceId = p.priceId
//		order by pi.packageItemId
		
		// hql:select_packageItemsByPackageId , 20091026
		
//		select pi.packageItemId,pi.sequence,pc.productCategoryId,pc.description,
//		pc.nextOption,pc.nextProductCategoryId,
//		p.priceId,p.description,p.price 
//		from PackageItem pi, ProductCategory pc, Price p 
//		where pi.packageId = :packageId 
//		and pi.productCategoryId = pc.productCategoryId 
//		and pc.priceId = p.priceId 
//		order by pi.packageItemId
		
		List packageItemDisplayDTOs = new ArrayList();
		List packageItems = packageItemManager.getPackageItemsByPackageId(packageId);
		for(int i = 0; i < packageItems.size(); i++)
		{
			PackageItemDisplayDTO packageItemDisplayDTO = new PackageItemDisplayDTO();
			Object[] rows = (Object[])packageItems.get(i);
			
			packageItemDisplayDTO.setPackageItemId((Long)rows[0]);
			packageItemDisplayDTO.setSequence((Long)rows[1]);
			packageItemDisplayDTO.setProductCategoryId((Long)rows[2]);
			packageItemDisplayDTO.setProductCategoryDes((String)rows[3]);
			packageItemDisplayDTO.setNextOption((Long)rows[4]);
			packageItemDisplayDTO.setNextProductCategoryId((Long)rows[5]);
			packageItemDisplayDTO.setPriceId((Long)rows[6]);
			packageItemDisplayDTO.setPriceDescription((String)rows[7]);
			packageItemDisplayDTO.setPrice((Double)rows[8]);
			packageItemDisplayDTO.setPackageId(packageId);
			
			if(packageItemDisplayDTO.getNextProductCategoryId() != null)
			{
				ProductCategory nextProductCategory = (ProductCategory)productCategoryManager.getById(packageItemDisplayDTO.getNextProductCategoryId().toString());
						
				if(nextProductCategory != null)
				{
					packageItemDisplayDTO.setNextProductCategoryDes(nextProductCategory.getDescription());
				}
			}
			
			packageItemDisplayDTOs.add(packageItemDisplayDTO);
		}
		
		return packageItemDisplayDTOs;
	}

//	// 2009-10-25
//	// 修改：该方法取消
//	// 查询套餐下的点播产品
//	public List getVodProductsByProductCategoryId(Long productCategoryId)
//	{
//		// 返回类型：VodProductDisplayDTO
//		// 条件：productCategoryId
//		// 查询表：vodCbsProductRel，VodProduct,VodPackage,VodDisplayCategory
//		// hql:
////		select vpt.vodProductId,vpt.type,vpt.billingType,vpt.price,vpt.validFrom,vpt.validTo,
////		vpt.sendTag,vpt.packagePrice,vpg.vodPackageId,vpg.ptglobalid,vpg.description,vpg.subtitlelanguage,
////		vpg.audiolanguage,vpg.director,vpg.productname,vpg.actors,vpg.shootdate,vpg.issuedate,
////		vpg.countrydist,vpg.inputmanid,vpg.inputtime,vpg.remark,vdc.vodDisplayCategoryId,vdc.title
////		from vodCbsProductRel vcp, VodProduct vpt, VodPackage vpg, VodDisplayCategory vdc
////		where vcp.productCategoryId = :productCategoryId and vcp.vodProductId = vpt.vodProductId and vpt.vodPackageId = vpg.vodPackageId and vpg.vodDisplayCategoryId = vdc.vodDisplayCategoryId
//
//		List vodProductDisplayDTOs = new ArrayList();
//		List vodProducts = vodProductManager.getVodProductsByProductCategoryId(productCategoryId);
//		for(int i = 0; i < vodProducts.size(); i++)
//		{
//			VodProductDisplayDTO vodProductDisplayDTO = new VodProductDisplayDTO();
//			Object[] rows = (Object[])vodProducts.get(i);
//			
//			vodProductDisplayDTO.setVodProductId((String)rows[0]);
//			vodProductDisplayDTO.setType((Long)rows[1]);
//			vodProductDisplayDTO.setBillingType((Long)rows[2]);
//			vodProductDisplayDTO.setPrice((Double)rows[3]);
//			vodProductDisplayDTO.setValidFrom((Date)rows[4]);
//			vodProductDisplayDTO.setValidTo((Date)rows[5]);
//			vodProductDisplayDTO.setSendTag((Long)rows[6]);
//			vodProductDisplayDTO.setPackagePrice((Double)rows[7]);
//			vodProductDisplayDTO.setVodPackageId((String)rows[8]);
//			vodProductDisplayDTO.setPtglobalid((String)rows[9]);
//			vodProductDisplayDTO.setDescription((String)rows[10]);
//			vodProductDisplayDTO.setSubtitlelanguage((String)rows[11]);
//			vodProductDisplayDTO.setAudiolanguage((String)rows[12]);
//			vodProductDisplayDTO.setDirector((String)rows[13]);
//			vodProductDisplayDTO.setProductname((String)rows[14]);
//			vodProductDisplayDTO.setActors((String)rows[15]);
//			vodProductDisplayDTO.setShootdate((String)rows[16]);
//			vodProductDisplayDTO.setIssuedate((String)rows[17]);
//			vodProductDisplayDTO.setCountrydist((String)rows[18]);
//			vodProductDisplayDTO.setInputmanid((String)rows[19]);
//			vodProductDisplayDTO.setInputtime((Date)rows[20]);
//			vodProductDisplayDTO.setRemark((String)rows[21]);
//			vodProductDisplayDTO.setVodDisplayCategoryId((String)rows[22]);
//			vodProductDisplayDTO.setVodDisplayCategory((String)rows[23]);
//			
//			vodProductDisplayDTOs.add(vodProductDisplayDTO);
//		}
//
//		return vodProductDisplayDTOs;
//	}
//	
//	// 2009-10-25
//	// 修改：该方法取消
//	// 保存套餐下的点播产品
//	public void saveVodProductsToProductCategory(ArrayCollection vodProducts, Long productCategoryId)
//	{
//		VodCbsProductRel[] vodCbsProductRels = new VodCbsProductRel[vodProducts.size()];
////		List vodCbsProductRels = new ArrayList();
//		
//		for(int i = 0; i < vodProducts.size(); i++)
//		{
//			VodCbsProductRel vodCbsProductRel = new VodCbsProductRel();
//			VodProduct vodProduct = (VodProduct)vodProducts.get(i);
//			
//			vodCbsProductRel.setProductCategoryId(productCategoryId);
//			vodCbsProductRel.setVodProductId(vodProduct.getVodProductId());
//			
//			vodCbsProductRels[i] = vodCbsProductRel;
//		}
//		vodCbsProductRelManager.save(vodCbsProductRels);
//	}
//	
//	// 2009-10-25
//	// 修改：该方法取消
//	// 保存套餐下的点播产品(单个点播产品)
//	public String saveVodProductToProductCategory(String vodProductId, Long productCategoryId)
//	{
//		VodCbsProductRel vodCbsProductRel = new VodCbsProductRel();
//		vodCbsProductRel.setProductCategoryId(productCategoryId);
//		vodCbsProductRel.setVodProductId(vodProductId);
//		vodCbsProductRelManager.save(vodCbsProductRel);
//		
//		System.out.println("saveVodProductToProductCategory:" + vodCbsProductRel.getRelId().toString());
//		
//		return vodCbsProductRel.getRelId();
//	}
//	
//	// 2009-10-25
//	// 修改：该方法取消
//	// 删除套餐下的点播产品
//	public void deleteVodProductsByVodProductIds(ArrayCollection vodProductIds, Long productCategoryId)
//	{
//		for(int i = 0; i< vodProductIds.size(); i++)
//		{
//			String vodProductId = (String)vodProductIds.get(i);
////			VodCbsProductRel vodCbsProductRel = (VodCbsProductRel)vodCbsProductRelManager.findByProperty("vodProductId", vodProductId);
//			VodCbsProductRel vodCbsProductRel = (VodCbsProductRel)vodCbsProductRelManager.getVodCbsProductRelByVodProductIdAndProductCategoryId(vodProductId, productCategoryId);
//			vodCbsProductRelManager.deleteById(vodCbsProductRel.getRelId());
//		}
//	}
//	
	// 2009-10-25
	// 修改：该方法取消
	// 删除套餐下的点播产品
	public void deleteVodProductByVodProductId(String vodProductId, Long productCategoryId)
	{
		VodCbsProductRel vodCbsProductRel = (VodCbsProductRel)vodCbsProductRelManager.getVodCbsProductRelByVodProductIdAndProductCategoryId(vodProductId, productCategoryId);
		vodCbsProductRelManager.deleteById(vodCbsProductRel.getRelId());
		System.out.println("deleteVodProductByVodProductId:" + vodCbsProductRel.getRelId().toString());
	}
	
	// 2009-10-25
	// 修改：该方法取消
	// 创建硬件型号
	public void createHardwareModel(HardwareModel hardwareModel)
	{
		hardwareModelManager.save(hardwareModel);
	}
	
	// 2009-10-25
	// 修改：该方法取消
	// 修改硬件型号
	public void modifyHardwareModel(HardwareModel hardwareModel)
	{
		hardwareModelManager.update(hardwareModel);
	}
	
	// 2009-10-25
	// 修改：该方法取消
	// 查询硬件型号
	public List getHardwareModels(String hardwareModelName)
	{
		List hardwareModels = hardwareModelManager.getHardwareModelsByHardwareModelName(hardwareModelName);
		return hardwareModels;
	}
	
	
	// ----------------------- 直播频道 ---------------------------------------------------------------
	// 1 - VOD
	// 2 - 直播
	// 3 - 硬件
	// 得到Vod类型的产品
	public CmsResultDto getVodTypeProductCategories()
	{
		System.out.println("cbs -> ConfigProductImpl -> getVodTypeProductCategories...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		List productCategories = productCategoryManager.findByProperty("productTypeId", (long)1);
		cmsResultDto.setResultObject(productCategories);
		System.out.println("cbs -> ConfigProductImpl -> getVodTypeProductCategories returns.");
		return cmsResultDto;
	}
	
	// 得到直播类型的产品
	public CmsResultDto getLiveTypeProductCategories()
	{
		System.out.println("cbs -> ConfigProductImpl -> getLiveTypeProductCategories...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		List productCategories = productCategoryManager.findByProperty("productTypeId", (long)2);
		cmsResultDto.setResultObject(productCategories);
		System.out.println("cbs -> ConfigProductImpl -> getLiveTypeProductCategories returns.");
		return cmsResultDto;
	}
	
	
	
	//----------------------- 节目包(ProgPackage) / 服务(CmsService) ------------------------------------------
	// 产品与节目包关系 ProgPackage
	// 查询
	// 根据产品ID，查询得到节目包，节目包记录很多，需要添加搜索条件，由flex来处理
	
	// 添加
	// 添加节目包到产品
	public CmsResultDto addProgPackagesToProductCategory(List progPackages, Long productCategoryId)
	{
		System.out.println("cbs -> addProgPackagesToProductCategory...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cbsTransactionManager.saveProgPackagesToProductCategory(
				productCategoryManager, 
				ppSrvPdtRelManager, 
				progPackages, 
				productCategoryId
				);
		
		System.out.println("cbs -> addProgPackagesToProductCategory returns.");
		return cmsResultDto;
	}
	
	// 删除
	// 删除节目包从产品
	public CmsResultDto deleteProgPackagesFromProductCategory(List progPackages, Long productCategoryId)
	{
		System.out.println("cbs -> deleteProgPackagesFromProductCategory...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cbsTransactionManager.deleteProgPackagesFromProductCategory(
				productCategoryManager, 
				ppSrvPdtRelManager, 
				progPackages, 
				productCategoryId
				);
		
		System.out.println("cbs -> deleteProgPackagesFromProductCategory returns.");
		return cmsResultDto;
	}
	
	
	// 产品与服务配置关系 CmsService
	// 查询
	// 根据产品id，查询服务
	public CmsResultDto getCmsServicesByProductCategoryId(Long productCategoryId)
	{
		System.out.println("cbs -> getCmsServicesByProductCategoryId...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		List cmsServices = srvProductManager.getCmsServicesByProductCategoryId(productCategoryId);
		cmsResultDto.setResultObject(cmsServices);
		System.out.println("cbs -> getCmsServicesByProductCategoryId returns.");
		return cmsResultDto;
	}
	
	// 保存（先删除，后添加）
	// 保存服务与产品的配置关系记录 SrvProduct 20091105
	public CmsResultDto saveSrvProductsByProductCategoryId(List cmsServices, Long productCategoryId)
	{
		System.out.println("cbs -> saveSrvProductsByProductCategoryId...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cbsTransactionManager.saveSrvProductsByProductCategoryId(
				productCategoryManager, 
				srvProductManager, 
				cmsServices, 
				productCategoryId
				);
		
		System.out.println("cbs -> saveSrvProductsByProductCategoryId returns.");
		return cmsResultDto;
	}
}
