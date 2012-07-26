package com.cbs.cbsmgr.serviceSimple;

import java.util.List;

import com.cbs.cbsmgr.bean.PackageItem;
import com.cbs.cbsmgr.bean.ProductCategory;
import com.cbs.cbsmgr.bean.ProductPackage;
import com.soaplat.cmsmgr.dto.CmsResultDto;

public interface ConfigProductIface {

	// 2009-10-26
	// 修改：
	// 1 - 原来“套餐”更名为“产品”
	// 2 - 原来“套餐包”更名为“产品包”
	// 3 - 原来“点播产品”概念取消
	
	
	// 2009-10-26
	// 修改：该方法取消
	// 测试通过
	// 查询套餐下的点播产品
//	public List getVodProductsByProductCategoryId(Long productCategoryId);
	
	// 2009-10-26
	// 修改：该方法取消
	// 测试通过
	// 保存套餐下的点播产品
//	public void saveVodProductsToProductCategory(ArrayCollection vodProducts, Long productCategoryId);
	
	// 2009-10-26
	// 修改：该方法取消
	// 保存套餐下的点播产品(单个点播产品)
//	public String saveVodProductToProductCategory(String vodProductId, Long productCategoryId);
	
	// 2009-10-26
	// 修改：该方法取消
	// 测试通过
	// 删除套餐下的点播产品
//	public void deleteVodProductsByVodProductIds(ArrayCollection vodProductIds, Long productCategoryId);
	
	// 2009-10-26
	// 修改：该方法取消
	// 删除套餐下的点播产品(单个点播产品)
//	public void deleteVodProductByVodProductId(String vodProductId, Long productCategoryId);
	
	// 2009-10-26
	// 修改：该方法取消
//	// 测试通过
//	// 创建硬件型号
//	public void createHardwareModel(HardwareModel hardwareModel);
//	
	// 2009-10-26
	// 修改：该方法取消
//	// 测试通过
//	// 修改硬件型号
//	public void modifyHardwareModel(HardwareModel hardwareModel);
//	
	// 2009-10-26
	// 修改：该方法取消
//	// 测试通过
//	// 查询硬件型号
//	public List getHardwareModels(String hardwareModelName);
	
	
	// 测试通过
	// 创建产品（ProductCategory）
	public void createProductCategory(ProductCategory productCategory);
	
	// 测试通过
	// 修改产品（ProductCategory）
	public void modifyProductCategory(ProductCategory productCategory);
	
	// 测试通过，20091027
	// 查询产品（ProductCategory）
	public List getProductCategoryDisplayDTOsByDescription(String description);

	
	// 测试通过
	// 创建产品包
	public void createProductPackage(ProductPackage productPackage);
	
	// 测试通过
	// 修改产品包
	public void modifyProductPackage(ProductPackage productPackage);
	
	// 测试通过
	// 查询套餐包
	public List getProductPackagesByDescription(String description);
	
	
	// 测试通过
	// 创建产品包单项
	public void createPackageItem(PackageItem packageItem);
	
	// 测试通过
	// 修改产品包单项
	public void modifyPackageItem(PackageItem packageItem);
	
	// 测试通过
	// 删除产品包单项
	public void deletePackageItem(Long packageItemId);
	
	// 测试通过，20091027
	// 查询产品包单项
	public List getPackageItems(Long packageId);
	
	
	
	// ----------------------- 直播频道 ---------------------------------------------------------------
	// 得到Vod类型的产品
	public CmsResultDto getVodTypeProductCategories();
	
	// 得到直播类型的产品
	public CmsResultDto getLiveTypeProductCategories();
	
	
	
	
	
	//----------------------- 节目包(ProgPackage) / 服务(CmsService) ------------------------------------------
	// 产品与节目包关系 ProgPackage
	// 查询
	// 根据产品ID，查询得到节目包，节目包记录很多，需要添加搜索条件，由flex来处理
	
	// 添加
	// 添加节目包到产品
	public CmsResultDto addProgPackagesToProductCategory(List progPackages, Long productCategoryId);
	
	// 删除
	// 删除节目包从产品
	public CmsResultDto deleteProgPackagesFromProductCategory(List progPackages, Long productCategoryId);
	
	
	// 产品与服务配置关系 CmsService
	// 查询
	// 根据产品id，查询服务
	public CmsResultDto getCmsServicesByProductCategoryId(Long productCategoryId);
	
	// 保存（先删除，后添加）
	// 保存服务与产品的配置关系记录 SrvProduct 20091105
	public CmsResultDto saveSrvProductsByProductCategoryId(List cmsServices, Long productCategoryId);
	
}
