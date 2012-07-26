package com.soaplat.cmsmgr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cbs.cbsmgr.bean.ProductCategory;
import com.cbs.cbsmgr.dto.ProductCategoryDisplayDTO;
import com.soaplat.cmsmgr.bean.CmsService;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgSrvRel;
import com.soaplat.cmsmgr.bean.SrvColumn;
import com.soaplat.cmsmgr.bean.SrvProduct;
import com.soaplat.cmsmgr.bean.SrvProgClass;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.dto.CmsServiceDto;
import com.soaplat.cmsmgr.dto.ProgramCategoryDto;
import com.soaplat.cmsmgr.manageriface.ICmsServiceManager;
import com.soaplat.cmsmgr.manageriface.ICmsTransactionManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgSrvRelManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.soaplat.cmsmgr.manageriface.ISrvColumnManager;
import com.soaplat.cmsmgr.manageriface.ISrvProductManager;
import com.soaplat.cmsmgr.manageriface.ISrvProgClassManager;
import com.soaplat.sysmgr.bean.Dict;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.manageriface.IDictManager;

public class CmsServiceServiceImpl implements CmsServiceServiceIface {

	public ICmsServiceManager cmsServiceManager = null;
	public IProgSrvRelManager progSrvRelManager = null;
	public IProgPackageManager progPackageManager = null;
	public ISrvProgClassManager srvProgClassManager = null;
	public ISrvColumnManager srvColumnManager = null;
	public ISrvProductManager srvProductManager = null;
	public IProgramInfoManager programinfoManager = null;
	public IDictManager dictManager = null;
	
	public ICmsTransactionManager cmsTransactionManager = null;
	public static final Logger cmsLog = Logger.getLogger("Cms");
	
	public CmsServiceServiceImpl()
	{
		cmsServiceManager = (ICmsServiceManager)ApplicationContextHolder.webApplicationContext.getBean("cmsServiceManager");
		progSrvRelManager = (IProgSrvRelManager)ApplicationContextHolder.webApplicationContext.getBean("progSrvRelManager");
		progPackageManager = (IProgPackageManager)ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");	
		srvProgClassManager = (ISrvProgClassManager)ApplicationContextHolder.webApplicationContext.getBean("srvProgClassManager");	
		srvColumnManager = (ISrvColumnManager)ApplicationContextHolder.webApplicationContext.getBean("srvColumnManager");	
		srvProductManager = (ISrvProductManager)ApplicationContextHolder.webApplicationContext.getBean("srvProductManager");
		programinfoManager = (IProgramInfoManager)ApplicationContextHolder.webApplicationContext.getBean("programinfoManager");
		dictManager = (IDictManager)ApplicationContextHolder.webApplicationContext.getBean("dictManager");
		
		cmsTransactionManager = (ICmsTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("cmsTransactionManager");
	}
	
	
	public void transactionTest()
	{
//		cmsServiceManager.savetransactionTest(progPackageManager);
//		cmsServiceManager.savetest1();
//		cmsServiceManager.savetest2();
	}
	
	// 查询所有服务
	public CmsResultDto getAllCmsServices() 
	{
		cmsLog.info("getAllCmsServices...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		List cmsServiceDtos = new ArrayList();
		List cmsServices = cmsServiceManager.findAll();
		for(int i = 0; i < cmsServices.size(); i++)
		{
			CmsService cmsService = (CmsService)cmsServices.get(i);
			CmsServiceDto cmsServiceDto = new CmsServiceDto(cmsService);
			cmsServiceDtos.add(cmsServiceDto);
		}
		cmsResultDto.setResultObject(cmsServiceDtos);
		cmsLog.info("getAllCmsServices returns.");
		return cmsResultDto;
	}
	
	private void deleteSrvProgClassBySrvId(String srvId)
	{
		List srvProgClasses = srvProgClassManager.findByProperty("srvid", srvId);
		if(srvProgClasses.size() > 0)
		{
			cmsLog.info("删除服务与节目分类的配置关系记录。");
			SrvProgClass[] srvProgClassObjects = (SrvProgClass[])srvProgClasses.toArray(new SrvProgClass[srvProgClasses.size()]);
			srvProgClassManager.delete(srvProgClassObjects);
		}
	}
	
	private void deleteSrvColumnBySrvId(String srvId)
	{
		List srvColumns = srvColumnManager.findByProperty("srvid", srvId);
		if(srvColumns.size() > 0)
		{
			cmsLog.info("删除服务与栏目的配置关系记录。");
			SrvColumn[] srvColumnObjects = (SrvColumn[])srvColumns.toArray(new SrvColumn[srvColumns.size()]);
			srvColumnManager.delete(srvColumnObjects);
		}
	}
	
	private void deleteSrvProductBySrvId(String srvId)
	{
		List srvProducts = srvProductManager.findByProperty("srvid", srvId);
		if(srvProducts.size() > 0)
		{
			cmsLog.info("删除服务与产品的配置关系记录。");
			SrvProduct[] srvProductObjects = (SrvProduct[])srvProducts.toArray(new SrvProduct[srvProducts.size()]);
			srvProductManager.delete(srvProductObjects);
		}
	}
	
	// 删除服务
	public CmsResultDto deleteCmsService(String srvId)
	{
		cmsLog.info("Cms -> CmsServiceServiceImpl -> deleteCmsService...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsTransactionManager.deleteCmsService(
				cmsServiceManager, 
				srvProgClassManager, 
				srvColumnManager, 
				srvProductManager, 
				progSrvRelManager, 
				srvId
				);
		
//		// 1 - 检查该节点是否是叶子节点
//		// 2 - 如果不是，直接返回失败
//		// 3 - 如果是，
//		// 3.1 - 删除与节目分类、栏目、产品的配置关系记录
//		// 3.2 - 删除与节目包的关系
//		// 3.3 - 删除该服务节点
//		// 3.4 - 判断父节点下是否有节点
//		// 3.4.1 - 如果有，无操作
//		// 3.4.2 - 如果没有，将父节点改成叶子节点
//		
//		// 1 - 检查该节点是否是叶子节点
//		CmsService cmsService = (CmsService)cmsServiceManager.getById(srvId);
//		if(cmsService == null)
//		{
//			cmsResultDto.setResultCode((long)1);
//			cmsResultDto.setErrorMessage("该服务节点不存在。");
//			cmsLog.info("该服务节点不存在。" + srvId);
//		}
//		else if("N".equalsIgnoreCase(cmsService.getIsleaf()))
//		{
//			// 2 - 如果不是，直接返回失败
//			cmsResultDto.setResultCode((long)1);
//			cmsResultDto.setErrorMessage("该服务节点不是叶子节点，不能删除。");
//			cmsLog.info("该服务节点不是叶子节点，不能删除。" + srvId);
//		}
//		else
//		{
//			// 3 - 如果是，
//			// 3.1 - 删除与节目分类、栏目、产品的配置关系记录
//			// SrvProgClass,SrvColumn,SrvProduct
//			deleteSrvProgClassBySrvId(cmsService.getSrvid());
//			deleteSrvColumnBySrvId(cmsService.getSrvid());
//			deleteSrvProductBySrvId(cmsService.getSrvid());
////			List srvProgClasses = srvProgClassManager.findByProperty("srvid", cmsService.getSrvid());
////			if(srvProgClasses.size() > 0)
////			{
////				cmsLog.info("删除服务与节目分类的配置关系记录。");
////				SrvProgClass[] srvProgClassObjects = (SrvProgClass[])srvProgClasses.toArray(new SrvProgClass[srvProgClasses.size()]);
////				srvProgClassManager.delete(srvProgClassObjects);
////			}
////			List srvColumns = srvColumnManager.findByProperty("srvid", cmsService.getSrvid());
////			if(srvColumns.size() > 0)
////			{
////				cmsLog.info("删除服务与栏目的配置关系记录。");
////				SrvColumn[] srvColumnObjects = (SrvColumn[])srvColumns.toArray(new SrvColumn[srvColumns.size()]);
////				srvColumnManager.delete(srvColumnObjects);
////			}
////			List srvProducts = srvProductManager.findByProperty("srvid", cmsService.getSrvid());
////			if(srvProducts.size() > 0)
////			{
////				cmsLog.info("删除服务与产品的配置关系记录。");
////				SrvProduct[] srvProductObjects = (SrvProduct[])srvProducts.toArray(new SrvProduct[srvProducts.size()]);
////				srvProductManager.delete(srvProductObjects);
////			}
//			
//			
//			// 3.2 - 删除与节目包的关系
//			// ProgSrvRel
//			List progSrvRels = progSrvRelManager.findByProperty("srvid", cmsService.getSrvid());
//			if(progSrvRels.size() > 0)
//			{
//				cmsLog.info("删除服务与节目包的关系记录。");
//				ProgSrvRel[] progSrvRelObjects = (ProgSrvRel[])progSrvRels.toArray(new ProgSrvRel[progSrvRels.size()]);
//				progSrvRelManager.delete(progSrvRelObjects);
//			}
//			
//			// 3.3 - 删除该服务节点
//			// CmsService
//			List parents = cmsServiceManager.findByProperty("defcatcode", cmsService.getParentsid());
//			cmsLog.info("删除服务记录。");
//			cmsServiceManager.deleteById(cmsService.getSrvid());
//			
//			
//			if(parents.size() > 0)
//			{
//				for(int i = 0; i < parents.size(); i++)
//				{
//					CmsService parent = (CmsService)parents.get(i);
//					// 3.4 - 判断父节点下是否有节点
//					List children = cmsServiceManager.findByProperty("parentsid", parent.getDefcatcode());
//					if(children.size() > 0)
//					{
//						// 3.4.1 - 如果有，无操作
//					}
//					else
//					{
//						// 3.4.2 - 如果没有，将父节点改成叶子节点
//						cmsLog.info("将父节点改成叶子节点。");
//						parent.setIsleaf("Y");
//						cmsServiceManager.save(parent);
//					}
//				}
//			}
//		}
		cmsLog.info("Cms -> CmsServiceServiceImpl -> deleteCmsService returns.");
		return cmsResultDto;
	}
	
	// 修改服务
	public CmsResultDto modifyCmsService(CmsService cmsService)
	{
		cmsLog.info("Cms -> CmsServiceServiceImpl -> modifyCmsService...");
		CmsResultDto cmsResultDto = new CmsResultDto();
//		CmsService newCmsService = new CmsService();
		CmsService curCmsService = (CmsService)cmsServiceManager.getById(cmsService.getSrvid());
		
		if(curCmsService != null)
		{
//			cmsService.setSrvid(curCmsService.getSrvid());
			cmsService.setDefcatcode(curCmsService.getDefcatcode());
			cmsService.setIsleaf(curCmsService.getIsleaf());
			cmsService.setDefcatlevel(curCmsService.getDefcatlevel());
			cmsService.setRootid(curCmsService.getRootid());
			cmsService.setParentsid(curCmsService.getParentsid());
			cmsService.setDefcatseq(curCmsService.getDefcatseq());
			cmsService.setInputmanid(curCmsService.getInputmanid());
			cmsService.setInputtime(curCmsService.getInputtime());
			
			// 只允许改变这3项
//			newCmsService.setSrvname(cmsService.getSrvname());
//			newCmsService.setDisplayorder(cmsService.getDisplayorder());
//			newCmsService.setRemark(cmsService.getRemark());
	
			cmsLog.info("修改服务节点。");
			cmsServiceManager.update(cmsService);
		}
		else
		{
			cmsLog.info("未查询到该服务节点。");
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage("未查询到该服务节点。srvid:" + curCmsService.getSrvid());
		}
		cmsLog.info("Cms -> CmsServiceServiceImpl -> modifyCmsService returns.");
		return cmsResultDto;
	}
	
	// 创建服务
	public CmsResultDto createCmsService(CmsService cmsService)
	{
		cmsLog.info("Cms -> CmsServiceServiceImpl -> createCmsService...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsService = cmsTransactionManager.saveCmsService(
				cmsServiceManager, 
				srvProgClassManager, 
				srvColumnManager, 
				srvProductManager, 
				progSrvRelManager, 
				cmsService
				);

		cmsResultDto.setResultObject(cmsService);
		
//		// 0 - 查询判断新加节点的defcatcode，是否已经存在
//		// 1 - 查询判断父节点，是否是叶子节点
//		// 1.1 - 不是叶子节点，
//		// 1.1.1 - 保存该服务节点
//		// 1.2 - 是叶子节点，
//		// 1.2.1 - 删除父节点与节目分类、栏目、产品的配置关系记录
//		// SrvProgClass,SrvColumn,SrvProduct
//		// 1.2.2 - 创建该服务节点
//		// 1.2.3 - 修改父节点下所有的节目包的srvid为新建节点srvid
//		// ProgSrvRel
//		// 1.2.4 - 修改服务父节点为非叶子节点
//		// CmsService
//		
//		// 0 - 查询判断新加节点的defcatcode，是否已经存在
//		if(cmsService.getDefcatcode() != null && !"".equalsIgnoreCase(cmsService.getDefcatcode()))
//		{
//			List curCmsServices = cmsServiceManager.findByProperty("defcatcode", cmsService.getDefcatcode());
//			if(curCmsServices.size() > 0)
//			{
//				cmsResultDto.setResultCode((long)1);
//				cmsResultDto.setErrorMessage("服务代码已经存在。");
//				cmsLog.info("服务代码已经存在。");
//			}
//			else
//			{
//				// 1 - 查询判断父节点，是否是叶子节点
//				List parents = cmsServiceManager.findByProperty("defcatcode", cmsService.getParentsid());
//				if(parents.size() > 0)
//				{
//					CmsService parent = (CmsService)parents.get(0);
//					cmsLog.info("Parent SrvId:" + parent.getSrvid());
//					cmsService.setDefcatlevel(parent.getDefcatlevel() + 1);
//					cmsService.setInputtime(new Date());
//					cmsService.setIsleaf("Y");
//					cmsService.setRootid(parent.getRootid());
//					
//					if("N".equalsIgnoreCase(parent.getIsleaf()))
//					{
//						// 1.1 - 不是叶子节点，
//						// 1.1.1 - 保存该服务节点
//						cmsLog.info("服务父节点不是叶子节点，创建该服务。");
//						cmsService = (CmsService)cmsServiceManager.save(cmsService);
//						cmsLog.info("New SrvId:" + cmsService.getSrvid());
//						cmsResultDto.setResultObject(cmsService);
//					}
//					else
//					{
//						// 1.2 - 是叶子节点，
//						// 1.2.1 - 删除父节点与节目分类、栏目、产品的配置关系记录
//						// SrvProgClass,SrvColumn,SrvProduct
//						cmsLog.info("服务父节点是叶子节点。");
//						deleteSrvProgClassBySrvId(parent.getSrvid());
//						deleteSrvColumnBySrvId(parent.getSrvid());
//						deleteSrvProductBySrvId(parent.getSrvid());
//						
//						// 1.2.2 - 创建该服务节点
//						cmsLog.info("创建该服务。");
//						cmsService = (CmsService)cmsServiceManager.save(cmsService);
//						cmsLog.info("New SrvId:" + cmsService.getSrvid());
//						cmsResultDto.setResultObject(cmsService);
//						
//						// 1.2.3 - 修改父节点下所有的节目包的srvid为新建节点srvid
//						// ProgSrvRel
//						List parentProgSrvRels = progSrvRelManager.findByProperty("srvid", parent.getSrvid());
//						for(int i = 0; i < parentProgSrvRels.size(); i++)
//						{
//							ProgSrvRel progSrvRel = (ProgSrvRel)parentProgSrvRels.get(i);
//							cmsLog.info("修改父节点的节目包为新建节点的节目包。ProgSrvRel.srvid:" + progSrvRel.getSrvid());
//							progSrvRel.setSrvid(cmsService.getSrvid());
//							progSrvRelManager.update(progSrvRel);
//						}
//						
//						// 1.2.4 - 修改服务父节点为非叶子节点
//						// CmsService
//						cmsLog.info("修改父节点为非叶子节点。");
//						parent.setIsleaf("N");
//						progSrvRelManager.update(parent);
//					}
//				}
//				else
//				{
//					cmsResultDto.setResultCode((long)1);
//					cmsResultDto.setErrorMessage("未查询到服务父节点。");
//					cmsLog.info("未查询到服务父节点。");
//				}
//			}
//		}
//		else
//		{
//			cmsResultDto.setResultCode((long)1);
//			cmsResultDto.setErrorMessage("服务代码为空。");
//			cmsLog.info("服务代码为空。");
//		}
		
		cmsLog.info("Cms -> CmsServiceServiceImpl -> createCmsService returns.");
		return cmsResultDto;
	}

	
	// 
	// 查询服务与节目分类的配置关系记录 SrvProgClass ProgramCategoryDto 20091105
	public CmsResultDto getProgramCategoryDtosBySrvid(String srvid)
	{
		cmsLog.info("Cms -> CmsServiceServiceImpl -> getProgramCategoryDtosBySrvid...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		List ProgramCategoryDtos = new ArrayList();
		List srvProgClasses = srvProgClassManager.findByProperty("srvid", srvid);
		List programCategories = dictManager.findByProperty("dictid", "JBFL");
		
		for(int i = 0; i < srvProgClasses.size(); i++)
		{
			SrvProgClass srvProgClass = (SrvProgClass)srvProgClasses.get(i);
			for(int j = 0; j < programCategories.size(); j++)
			{
				Dict dict = (Dict)programCategories.get(j);
				if(dict.getDictcode().equalsIgnoreCase(srvProgClass.getGenre()))
				{
					ProgramCategoryDto programCategoryDto = new ProgramCategoryDto();
					programCategoryDto.setDicGlobalId(dict.getDicglobalid());
					programCategoryDto.setDictCode(dict.getDictcode());
					programCategoryDto.setDictName(dict.getDictname());
					programCategoryDto.setCmsServices(null);
					ProgramCategoryDtos.add(programCategoryDto);
					break;
				}
			}
		}
		
		cmsResultDto.setResultObject(ProgramCategoryDtos);
		cmsLog.info("Cms -> CmsServiceServiceImpl -> getProgramCategoryDtosBySrvid returns.");
		return cmsResultDto;
	}
	
	// 
	// 查询服务与栏目的配置关系记录 SrvColumn PortalColumn 20091105
	public CmsResultDto getPortalColumnsBySrvid(String srvid)
	{
		cmsLog.info("Cms -> CmsServiceServiceImpl -> getPortalColumnsBySrvid...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		List portalColumns = srvColumnManager.getPortalColumnsBySrvid(srvid);
		cmsResultDto.setResultObject(portalColumns);
		cmsLog.info("Cms -> CmsServiceServiceImpl -> getPortalColumnsBySrvid returns.");
		return cmsResultDto;
	}
	
	// 
	// 查询服务与产品的配置关系记录 SrvProduct ProductCategory 20091105
	public CmsResultDto getProductCategoriesBySrvid(String srvid)
	{
		cmsLog.info("Cms -> CmsServiceServiceImpl -> getProductCategoriesBySrvid...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		//???????????????????????????????????????????????????????????????????????????????????????????
		//???????????????????????????????????????????????????????????????????????????????????????????
		//???????????????????????????????????????????????????????????????????????????????????????????
		//???????????????????????????????????????????????????????????????????????????????????????????
		List productCategories = srvProductManager.getProductCategoriesBySrvid(srvid);
		cmsResultDto.setResultObject(productCategories);
		cmsLog.info("Cms -> CmsServiceServiceImpl -> getProductCategoriesBySrvid returns.");
		return cmsResultDto;
	}
	
	// 查询服务与产品的配置关系记录 SrvProduct ProductCategory ProductCategoryDisplayDTO 20091110
	public CmsResultDto getProductCategoryDisplayDTOsBySrvid(String srvid)
	{
		cmsLog.info("Cms -> CmsServiceServiceImpl -> getProductCategoryDtosBySrvid...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// hql:
//		select pc.productCategoryId,pc.description,pc.productTypeId,pc.priceId,pc.introduction,
//		pc.billingTypeId,pc.contentCategoryId,pc.days,pc.counts,pc.seconds,pc.validFrom,
//		pc.validTo,pc.createUser,pc.createDate,p.description,p.price,sa.smsAccountId,
//		sa.description,sa.debitCredit,pt.description,pt.hardwareTag,
//		cc.description,bt.description,bt.introduction 
//		from com.cbs.cbsmgr.bean.ProductCategory pc, com.cbs.cbsmgr.bean.Price p, 
//		com.cbs.cbsmgr.bean.SmsAccount sa, com.cbs.cbsmgr.bean.ProductType pt, 
//		com.cbs.cbsmgr.bean.ContentCategory cc, com.cbs.cbsmgr.bean.BillingType bt, 
//		SrvProduct sp 
//		where pc.productCategoryId = sp.productCategoryId 
//		and sp.srvid = :srvid 
//		and pc.priceId = p.priceId 
//		and p.smsAccountId = sa.smsAccountId 
//		and pc.productTypeId = pt.productTypeId 
//		and pc.contentCategoryId = cc.contentCategoryId 
//		and pc.billingTypeId = bt.billingTypeId 
		
//		select pc.productCategoryId,pc.description,pc.productTypeId,pc.priceId,pc.introduction,
//		pc.billingTypeId,pc.contentCategoryId,pc.days,pc.counts,pc.seconds,pc.validFrom,
//		pc.validTo,pc.createUser,pc.createDate,p.description,p.price,sa.smsAccountId,
//		sa.description,sa.debitCredit,pt.description,pt.hardwareTag,
//		cc.description,bt.description,bt.introduction 
		
		List productCategoryDisplayDtos = new ArrayList();
		List productCategories = srvProductManager.getProductCategoriesBySrvid(srvid);
		for(int i = 0; i < productCategories.size(); i++)
		{
			ProductCategoryDisplayDTO productCategoryDisplayDTO = new ProductCategoryDisplayDTO();
			Object[] rows = (Object[])productCategories.get(i);
			
			productCategoryDisplayDTO.setProductCategoryId((Long)rows[0]);
			productCategoryDisplayDTO.setDescription((String)rows[1]);
			productCategoryDisplayDTO.setProductTypeId((Long)rows[2]);
			productCategoryDisplayDTO.setPriceId((Long)rows[3]);
			productCategoryDisplayDTO.setIntroduction((String)rows[4]);
			productCategoryDisplayDTO.setBillingTypeId((String)rows[5]);
			productCategoryDisplayDTO.setContentCategoryId((String)rows[6]);
			productCategoryDisplayDTO.setDays((Long)rows[7]);
			productCategoryDisplayDTO.setCounts((Long)rows[8]);
			productCategoryDisplayDTO.setSeconds((Long)rows[9]);
			productCategoryDisplayDTO.setValidFrom((Date)rows[10]);
			productCategoryDisplayDTO.setValidTo((Date)rows[11]);
			productCategoryDisplayDTO.setCreateUser((String)rows[12]);
			productCategoryDisplayDTO.setCreateDate((Date)rows[13]);
			productCategoryDisplayDTO.setPriceDescription((String)rows[14]);
			productCategoryDisplayDTO.setPrice((Double)rows[15]);
			productCategoryDisplayDTO.setSmsAccountId((Long)rows[16]);
			productCategoryDisplayDTO.setSmsAccountDescription((String)rows[17]);
			productCategoryDisplayDTO.setDebitCredit((String)rows[18]);
			productCategoryDisplayDTO.setProductTypeDes((String)rows[19]);
			productCategoryDisplayDTO.setHardwareTag((String)rows[20]);
			productCategoryDisplayDTO.setContentCategoryDes((String)rows[21]);
			productCategoryDisplayDTO.setBillingTypeDes((String)rows[22]);
			productCategoryDisplayDTO.setBillingTypeIntro((String)rows[23]);
			
			productCategoryDisplayDtos.add(productCategoryDisplayDTO);
		}
		
		cmsResultDto.setResultObject(productCategoryDisplayDtos);
		cmsLog.info("Cms -> CmsServiceServiceImpl -> getProductCategoryDtosBySrvid returns.");
		return cmsResultDto;
	}
	
//	// 
//	// 保存服务与节目分类的配置关系记录 SrvProgClass 20091105
//	public CmsResultDto saveSrvProgClassesBySrvid(List srvProgClasses, String srvid)
//	{
//		cmsLog.info("saveSrvProgClassesBySrvid...");
//		CmsResultDto cmsResultDto = new CmsResultDto();
//		
//		cmsTransactionManager.saveSrvProgClassesBySrvid(srvProgClassManager, cmsServiceManager, srvProgClasses, srvid);
//		
//		cmsLog.info("saveSrvProgClassesBySrvid returns.");
//		return cmsResultDto;
//	}
//	
//	// 
//	// 保存服务与栏目的配置关系记录 SrvColumn 20091105
//	public CmsResultDto saveSrvColumnsClassesBySrvid(List srvColumns, String srvid)
//	{
//		cmsLog.info("saveSrvColumnsClassesBySrvid...");
//		CmsResultDto cmsResultDto = new CmsResultDto();
//		
//		cmsTransactionManager.saveSrvColumnsBySrvid(srvColumnManager, cmsServiceManager, srvColumns, srvid);
//		
//		cmsLog.info("saveSrvColumnsClassesBySrvid returns.");
//		return cmsResultDto;
//	}
//	
//	// 
//	// 保存服务与产品的配置关系记录 SrvProduct 20091105
//	public CmsResultDto saveSrvProductsClassesBySrvid(List srvProducts, String srvid)
//	{
//		cmsLog.info("saveSrvProductsClassesBySrvid...");
//		CmsResultDto cmsResultDto = new CmsResultDto();
//		
//		cmsTransactionManager.saveSrvProductsBySrvid(srvProductManager, cmsServiceManager, srvProducts, srvid);
//		
//		cmsLog.info("saveSrvProductsClassesBySrvid returns.");
//		return cmsResultDto;
//	}
	
	// 保存服务与节目分类的配置关系记录 SrvProgClass Dict ProgramCategoryDto 20091107
	// 保存服务与栏目的配置关系记录 SrvColumn PortalColumn 20091107
	// 保存服务与产品的配置关系记录 SrvProduct ProductCategory 20091107
	public CmsResultDto saveAllSrvConfigBySrvid(List dicts, List portalColumns, List productCategories, String srvid)
	{
		cmsLog.info("saveAllSrvConfigBySrvid...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsTransactionManager.saveAllSrvConfigBySrvid(
				srvProgClassManager, 
				srvColumnManager, 
				srvProductManager, 
				cmsServiceManager, 
				dicts, 
				portalColumns, 
				productCategories, 
				srvid
				);
		
		cmsLog.info("saveAllSrvConfigBySrvid returns.");
		return cmsResultDto;
	}
	
	
	//
	// 根据srvid，查询PpSrvPdtRel，得到PackageFiles列表，20091104
	public CmsResultDto getProgPackagesBySrvId(String srvId)
	{
		cmsLog.info("getProgPackagesBySrvId...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		cmsResultDto.setResultObject(progPackageManager.getProgPackagesBySrvId(srvId));
		cmsLog.info("getProgPackagesBySrvId returns.");
		return cmsResultDto;
	}
	
	//
	// 加入节目包，到srvid，20091104
	public CmsResultDto addProgPackagesToSrvId(List progPackages, String srvId)
	{
		cmsLog.info("addProgPackagesToSrvId...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsTransactionManager.saveProgPackagesToSrvId(
				cmsServiceManager, 
				progSrvRelManager, 
				progPackages, 
				srvId
				);
		
//		// 判断服务节点是否是叶子节点
//		// 是，继续
//		// 不是，返回失败
//		CmsService cmsService = (CmsService)cmsServiceManager.getById(srvId);
//		if("Y".equalsIgnoreCase(cmsService.getIsleaf()))
//		{
//			if(progPackages.size() > 0)
//			{
////				ProgSrvRel[] progSrvRelObjects = new ProgSrvRel[progPackages.size()];
//				for(int i = 0; i < progPackages.size(); i++)
//				{
//					// 判断节目包是否属于服务根节点
//					// 属于根节点，删除根节点关系，添加服务关系
//					// 不属于根节点，添加服务关系
//					ProgPackage progPackage = (ProgPackage)progPackages.get(i);
//					List progSrvRels = progSrvRelManager.getProgSrvRelsByProductidAndSrvid(
//																progPackage.getProductid(), 
//																"SV00000001"
//																);
//					if(progSrvRels.size() > 0)
//					{
//						ProgSrvRel[] progSrvRelRootObjects = new ProgSrvRel[progSrvRels.size()];
//						for(int j = 0; j < progSrvRels.size(); j++)
//						{
//							progSrvRelRootObjects[j] = (ProgSrvRel)progSrvRels.get(j);
//							cmsLog.info("Old relid:" + ((ProgSrvRel)progSrvRels.get(j)).getRelid());
//							cmsLog.info("Old Productid:" + ((ProgSrvRel)progSrvRels.get(j)).getProductid());
//						}
//						cmsLog.info("删除节目包与服务根节点的关系记录。");
//						progSrvRelManager.delete(progSrvRelRootObjects);
//					}
//					
//					// 查询节目包与服务是否已经存在关系记录
//					// 如果存在，不操作
//					// 如果不存在，添加
//					List curProgSrvRels = progSrvRelManager.getProgSrvRelsByProductidAndSrvid(progPackage.getProductid(), srvId);
//					if(curProgSrvRels.size() > 0)
//					{
//						
//					}
//					else
//					{
//						ProgSrvRel progSrvRel = new ProgSrvRel();
//						progSrvRel.setProductid(progPackage.getProductid());
//						progSrvRel.setSrvid(srvId);
//						progSrvRel.setInputtime(new Date());
////						progSrvRelObjects[i] = progSrvRel;
//						cmsLog.info("New ProductId:" + progPackage.getProductid());
//						cmsLog.info("New SrvId:" + srvId);
//						cmsLog.info("创建节目包与服务的关系记录。");
//						progSrvRelManager.save(progSrvRel);
//					}
//				}
////				cmsLog.info("创建节目包与服务的关系记录。");
////				progSrvRelManager.save(progSrvRelObjects);
//			}
//			else
//			{
//				cmsResultDto.setResultCode((long)1);
//				cmsResultDto.setErrorMessage("节目包列表为空。");
//				cmsLog.info("节目包列表为空。");
//			}
//		}
//		else
//		{
//			cmsResultDto.setResultCode((long)1);
//			cmsResultDto.setErrorMessage("所选择的服务节点不是叶子节点，不能添加节目包。");
//			cmsLog.info("所选择的服务节点不是叶子节点，不能添加节目包。");
//		}
		cmsLog.info("addProgPackagesToSrvId returns.");
		return cmsResultDto;
	}
	
	//
	// 删除节目包，从srvid，20091104
	public CmsResultDto deleteProgPackagesFromSrvId(List progPackages, String srvId)
	{
		cmsLog.info("deleteProgPackagesFromSrvId...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsTransactionManager.deleteProgPackagesFromSrvId(
				progSrvRelManager, 
				progPackages, 
				srvId
				);
		
//		if(progPackages.size() > 0)
//		{
//			// 1 - 查询删除服务节目包关系
//			// 2 - 查询节目包和服务关系
//			// 3 - 没有，添加节目包和服务根节点关系
//			// 4 - 有，判断关系数量
//			// 5 - 如果唯一，不处理
//			// 6 - 大于1，判断是否存在与服务根节点关系，如果有，删除与根节点的关系
//			
//			for(int i = 0; i < progPackages.size(); i++)
//			{
//				ProgPackage progPackage = (ProgPackage)progPackages.get(i);
//				
//				// 1 - 查询删除服务节目包关系
//				List progSrvRels = progSrvRelManager.getProgSrvRelsByProductidAndSrvid(
//						progPackage.getProductid(), 
//						srvId
//						);
//				if(progSrvRels.size() > 0)
//				{
//					ProgSrvRel[] progSrvRelObjects = new ProgSrvRel[progSrvRels.size()];
//					for(int j = 0; j < progSrvRels.size(); j++)
//					{
//						progSrvRelObjects[j] = (ProgSrvRel)progSrvRels.get(j);
//						cmsLog.info("Old relid:" + ((ProgSrvRel)progSrvRels.get(j)).getRelid());
//						cmsLog.info("Old ProductId:" + ((ProgSrvRel)progSrvRels.get(j)).getProductid());
//					}
//					cmsLog.info("删除节目包与服务节点关系记录。");
//					progSrvRelManager.delete(progSrvRelObjects);
//				}
//				
//				// 2 - 查询节目包和服务关系
//				List curProgSrvRels = progSrvRelManager.findByProperty("productid", progPackage.getProductid());
//				if(curProgSrvRels.size() > 0)
//				{
//					// 4 - 有，判断关系数量
//					if(curProgSrvRels.size() == 1)
//					{
//						// 5 - 如果唯一，不处理
//						cmsLog.info("节目包与服务节点关系唯一，无操作。");
//					}
//					else
//					{
//						// 6 - 大于1，判断是否存在与服务根节点关系，如果有，删除与根节点的关系
//						cmsLog.info("节目包与服务节点关系不唯一。" + curProgSrvRels.size());
////						ProgSrvRel[] progSrvRelRootObjects = new ProgSrvRel[curProgSrvRels.size()];
//						for(int j = 0; j < curProgSrvRels.size(); j++)
//						{
//							ProgSrvRel progSrvRel = (ProgSrvRel)curProgSrvRels.get(j);
//							if(progSrvRel.getSrvid() == "SV00000001")
//							{
////								progSrvRelRootObjects[j] = progSrvRel;
//								cmsLog.info("Old relid:" + progSrvRel.getRelid());
//								cmsLog.info("Old ProductId:" + progSrvRel.getProductid());
//								cmsLog.info("删除节目包与服务根节点的关系记录。");
//								progSrvRelManager.deleteById(progSrvRel.getRelid());
//							}
//						}
//					}
//				}
//				else
//				{
//					// 3 - 没有，添加节目包和服务根节点关系
//					ProgSrvRel progSrvRel = new ProgSrvRel();
//					progSrvRel.setProductid(progPackage.getProductid());
//					progSrvRel.setSrvid("SV00000001");
//					progSrvRel.setInputtime(new Date());
//					cmsLog.info("创建节目包与根节点的关系记录。");
//					cmsLog.info("new ProductId:" + progPackage.getProductid());
//					progSrvRelManager.save(progSrvRel);
//				}
//			}
//		}
//		else
//		{
//			cmsResultDto.setResultCode((long)1);
//			cmsResultDto.setErrorMessage("节目包列表为空。");
//			cmsLog.info("节目包列表为空。");
//		}
		
		cmsLog.info("deleteProgPackagesFromSrvId returns.");
		return cmsResultDto;
	}
	
	// 根据节目包查询节目
	public CmsResultDto getProgramInfosByProductId(String productid)
	{
		cmsLog.info("getProgramInfosByProductId...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		List programInfos = programinfoManager.getProgramInfosByProductid(productid);
		cmsResultDto.setResultObject(programInfos);
		cmsLog.info("getProgramInfosByProductId returns.");
		return cmsResultDto;
	}
	
	// 根据节目包查询服务
	public CmsResultDto getCmsServicesByProductId(String productid)
	{
		cmsLog.info("getCmsServicesByProductId...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		List cmsServices = cmsServiceManager.getCmsServicesByProductid(productid);
		cmsResultDto.setResultObject(cmsServices);
		cmsLog.info("getCmsServicesByProductId returns.");
		return cmsResultDto;
	}
}
