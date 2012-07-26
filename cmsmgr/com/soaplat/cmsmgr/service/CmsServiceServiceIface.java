package com.soaplat.cmsmgr.service;

import java.util.List;

import com.soaplat.cmsmgr.bean.CmsService;
import com.soaplat.cmsmgr.dto.CmsResultDto;

public interface CmsServiceServiceIface {

	// 测试通过
	// 查询所有服务
	public CmsResultDto getAllCmsServices();
	
	// 测试通过，20091105,12:56
	// 删除服务
	public CmsResultDto deleteCmsService(String srvId);
	
	// 测试通过，20091105，14:59
	// 修改服务
	public CmsResultDto modifyCmsService(CmsService cmsService);
	
	// 测试通过，20091105，15:46
	// 创建服务
	public CmsResultDto createCmsService(CmsService cmsService);
	
	
	// 
	// 查询服务与节目分类的配置关系记录 SrvProgClass ProgramCategoryDto 20091105
	public CmsResultDto getProgramCategoryDtosBySrvid(String srvid);
	
	// 
	// 查询服务与栏目的配置关系记录 SrvColumn PortalColumn 20091105
	public CmsResultDto getPortalColumnsBySrvid(String srvid);
	
	// 
	// 查询服务与产品的配置关系记录 SrvProduct ProductCategory 20091105
	public CmsResultDto getProductCategoriesBySrvid(String srvid);
	
	// 查询服务与产品的配置关系记录 SrvProduct ProductCategory ProductCategoryDto 20091110
	public CmsResultDto getProductCategoryDisplayDTOsBySrvid(String srvid);
	
	// 
//	// 保存服务与节目分类的配置关系记录 SrvProgClass 20091105
//	public CmsResultDto saveSrvProgClassesBySrvid(List srvProgClasses, String srvid);
//	
//	// 
//	// 保存服务与栏目的配置关系记录 SrvColumn 20091105
//	public CmsResultDto saveSrvColumnsClassesBySrvid(List srvColumns, String srvid);
//	
//	// 
//	// 保存服务与产品的配置关系记录 SrvProduct 20091105
//	public CmsResultDto saveSrvProductsClassesBySrvid(List srvProducts, String srvid);
	
	// 测试通过，20091111 13:56
	// 保存服务与节目分类的配置关系记录 SrvProgClass ProgramCategoryDto 20091107
	// 保存服务与栏目的配置关系记录 SrvColumn PortalColumn 20091107
	// 保存服务与产品的配置关系记录 SrvProduct ProductCategory 20091107
	public CmsResultDto saveAllSrvConfigBySrvid(List programCategoryDtos, List portalColumns, List productCategories, String srvid);
//	public CmsResultDto saveAllSrvConfigBySrvid(List srvProgClasses, List srvColumns, List srvProducts, String srvid);
	
	// 测试通过
	// 根据srvid，查询PpSrvPdtRel，得到ProgPackage列表，20091104
	public CmsResultDto getProgPackagesBySrvId(String srvId);
	
	// 测试通过，20091105,13:25
	// 加入节目包，到srvid，20091104
	public CmsResultDto addProgPackagesToSrvId(List progPackages, String srvId);
	
	// 测试通过，20091105,13:25
	// 删除节目包，从srvid，20091104
	public CmsResultDto deleteProgPackagesFromSrvId(List progPackages, String srvId);
	
	// 根据节目包查询节目
	public CmsResultDto getProgramInfosByProductId(String productid);
	
	// 根据节目包查询服务
	public CmsResultDto getCmsServicesByProductId(String productid);
}
