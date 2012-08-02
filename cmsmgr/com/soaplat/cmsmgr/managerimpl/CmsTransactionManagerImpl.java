package com.soaplat.cmsmgr.managerimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.cbs.cbsmgr.bean.PpSrvPdtRel;
import com.cbs.cbsmgr.bean.ProductCategory;
import com.cbs.cbsmgr.manageriface.IPpSrvPdtRelManager;
import com.cbs.cbsmgr.manageriface.IProductCategoryManager;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageClass;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.manageriface.IAmsStorageClassManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.cmsmgr.bean.Bpmc;
import com.soaplat.cmsmgr.bean.CmsService;
import com.soaplat.cmsmgr.bean.EncryptList;
import com.soaplat.cmsmgr.bean.FlowActivityOrder;
import com.soaplat.cmsmgr.bean.PPColumnRel;
import com.soaplat.cmsmgr.bean.PackStyleFileType;
import com.soaplat.cmsmgr.bean.PackageFiles;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.PortalPackage;
import com.soaplat.cmsmgr.bean.PortalRoleOperRel;
import com.soaplat.cmsmgr.bean.ProgList;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.bean.ProgListMangDetail;
import com.soaplat.cmsmgr.bean.ProgPPRel;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgSrvRel;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.bean.PtpPgpRel;
import com.soaplat.cmsmgr.bean.SrvColumn;
import com.soaplat.cmsmgr.bean.SrvProduct;
import com.soaplat.cmsmgr.bean.SrvProgClass;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.manageriface.IBpmcManager;
import com.soaplat.cmsmgr.manageriface.ICmsServiceManager;
import com.soaplat.cmsmgr.manageriface.ICmsTransactionManager;
import com.soaplat.cmsmgr.manageriface.IEncryptListManager;
import com.soaplat.cmsmgr.manageriface.IFlowActivityOrderManager;
import com.soaplat.cmsmgr.manageriface.IPPColumnRelManager;
import com.soaplat.cmsmgr.manageriface.IPackStyleFileTypeManager;
import com.soaplat.cmsmgr.manageriface.IPackStylePortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IPortalPackageManager;
import com.soaplat.cmsmgr.manageriface.IPortalRoleOperRelManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListFileManager;
import com.soaplat.cmsmgr.manageriface.IProgListManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangManager;
import com.soaplat.cmsmgr.manageriface.IProgPPRelManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgSrvRelManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.soaplat.cmsmgr.manageriface.IPtpPgpRelManager;
import com.soaplat.cmsmgr.manageriface.ISrvColumnManager;
import com.soaplat.cmsmgr.manageriface.ISrvProductManager;
import com.soaplat.cmsmgr.manageriface.ISrvProgClassManager;
import com.soaplat.sysmgr.bean.Dict;
import com.soaplat.sysmgr.bean.Role;
import com.soaplat.sysmgr.manageriface.IRoleManager;

public class CmsTransactionManagerImpl implements ICmsTransactionManager {
	private static FileOperationImpl fileoper = null;
	public static final Logger cmsLog = Logger.getLogger("Cms");
	private static String proglistAdditional = "__";
	private static String progPackageAdditional = "";
	private static String pngAdditional = "__";

	public CmsTransactionManagerImpl() {
		fileoper = new FileOperationImpl();
		
	}

	// ------------------------------------- CmsService
	// ---------------------------------------------------

	// 删除服务与节目分类的关系记录
	private void deleteSrvProgClassBySrvId(
			ISrvProgClassManager srvProgClassManager, String srvId) {
		cmsLog.debug("Cms -> CmsTransaction -> deleteSrvProgClassBySrvId ...");
		List srvProgClasses = srvProgClassManager
				.findByProperty("srvid", srvId);
		if (srvProgClasses.size() > 0) {
			cmsLog.info("删除服务与节目分类的配置关系记录。");
			SrvProgClass[] srvProgClassObjects = (SrvProgClass[]) srvProgClasses
					.toArray(new SrvProgClass[srvProgClasses.size()]);
			srvProgClassManager.delete(srvProgClassObjects);
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> deleteSrvProgClassBySrvId returns.");
	}

	// 删除服务与栏目的关系记录
	private void deleteSrvColumnBySrvId(ISrvColumnManager srvColumnManager,
			String srvId) {
		cmsLog.debug("Cms -> CmsTransaction -> deleteSrvColumnBySrvId ...");
		List srvColumns = srvColumnManager.findByProperty("srvid", srvId);
		if (srvColumns.size() > 0) {
			cmsLog.info("删除服务与栏目的配置关系记录。");
			SrvColumn[] srvColumnObjects = (SrvColumn[]) srvColumns
					.toArray(new SrvColumn[srvColumns.size()]);
			srvColumnManager.delete(srvColumnObjects);
		}
		cmsLog.debug("Cms -> CmsTransaction -> deleteSrvColumnBySrvId returns.");
	}

	// 删除服务与产品的关系记录
	private void deleteSrvProductBySrvId(ISrvProductManager srvProductManager,
			String srvId) {
		cmsLog.debug("Cms -> CmsTransaction -> deleteSrvProductBySrvId ...");
		List srvProducts = srvProductManager.findByProperty("srvid", srvId);
		if (srvProducts.size() > 0) {
			cmsLog.info("删除服务与产品的配置关系记录。");
			SrvProduct[] srvProductObjects = (SrvProduct[]) srvProducts
					.toArray(new SrvProduct[srvProducts.size()]);
			srvProductManager.delete(srvProductObjects);
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> deleteSrvProductBySrvId returns.");
	}

	// 保存服务与节目分类的配置关系记录 SrvProgClass 20091105
	private void saveSrvProgClassesBySrvid(
			ISrvProgClassManager srvProgClassManager,
			ICmsServiceManager cmsServiceManager, List dicts, String srvid) {
		cmsLog.debug("Cms -> CmsTransaction -> saveSrvProgClassesBySrvid...");

		// 判断srvid是否存在
		CmsService cmsService = (CmsService) cmsServiceManager.getById(srvid);

		if (cmsService != null) {
			// 删除srvid下所有的srvProgClass
			List oldSrvProgClasses = srvProgClassManager.findByProperty(
					"srvid", srvid);
			if (oldSrvProgClasses.size() > 0) {
				SrvProgClass[] oldSrvProgClassObjects = (SrvProgClass[]) oldSrvProgClasses
						.toArray(new SrvProgClass[oldSrvProgClasses.size()]);
				cmsLog.info("删除原有服务与节目分类的配置关系记录。");
				srvProgClassManager.delete(oldSrvProgClassObjects);
			}

			if (dicts.size() > 0) {
				// 保存srvProgClasses
				SrvProgClass[] newSrvProgClassObjects = new SrvProgClass[dicts
						.size()];

				for (int i = 0; i < dicts.size(); i++) {
					Dict dict = (Dict) dicts.get(i);
					SrvProgClass srvProgClass = new SrvProgClass();
					srvProgClass.setSrvid(srvid);
					srvProgClass.setGenre(dict.getDictcode());
					srvProgClass.setInputtime(new Date());
					srvProgClass.setSelecttag((long) 0);
					newSrvProgClassObjects[i] = srvProgClass;
				}

				cmsLog.info("保存服务与节目分类的配置关系记录。");
				srvProgClassManager.save(newSrvProgClassObjects);
			}
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> saveSrvProgClassesBySrvid returns.");
	}

	// 保存服务与栏目的配置关系记录 SrvColumn 20091105
	private void saveSrvColumnsBySrvid(ISrvColumnManager srvColumnManager,
			ICmsServiceManager cmsServiceManager, List portalColumns,
			String srvid) {
		cmsLog.debug("Cms -> CmsTransaction -> saveSrvColumnsBySrvid...");

		// 判断srvid是否存在
		CmsService cmsService = (CmsService) cmsServiceManager.getById(srvid);

		if (cmsService != null) {
			// 删除srvid下所有的SrvColumn
			List oldSrvColumns = srvColumnManager
					.findByProperty("srvid", srvid);
			if (oldSrvColumns.size() > 0) {
				SrvColumn[] oldSrvColumnObjects = (SrvColumn[]) oldSrvColumns
						.toArray(new SrvColumn[oldSrvColumns.size()]);
				cmsLog.info("删除原有服务与栏目的配置关系记录。");
				srvColumnManager.delete(oldSrvColumnObjects);
			}

			if (portalColumns.size() > 0) {
				// 保存SrvColumn
				SrvColumn[] newSrvColumnObjects = new SrvColumn[portalColumns
						.size()];

				for (int i = 0; i < portalColumns.size(); i++) {
					PortalColumn portalColumn = (PortalColumn) portalColumns
							.get(i);
					SrvColumn srvColumn = new SrvColumn();
					srvColumn.setSrvid(srvid);
					srvColumn.setColumnclassid(portalColumn.getColumnclassid());
					srvColumn.setInputtime(new Date());
					srvColumn.setSelectTag((long) 0);
					newSrvColumnObjects[i] = srvColumn;
				}

				cmsLog.info("保存服务与栏目的配置关系记录。");
				srvColumnManager.save(newSrvColumnObjects);
			}
		}
		cmsLog.debug("Cms -> CmsTransaction -> saveSrvColumnsBySrvid returns.");
	}

	// 保存服务与产品的配置关系记录 SrvProduct 20091105
	private void saveSrvProductsBySrvid(ISrvProductManager srvProductManager,
			ICmsServiceManager cmsServiceManager, List productCategories,
			String srvid) {
		cmsLog.debug("Cms -> CmsTransaction -> saveSrvProductsBySrvid...");

		// 判断srvid是否存在
		CmsService cmsService = (CmsService) cmsServiceManager.getById(srvid);

		if (cmsService != null) {
			// 删除srvid下所有的SrvProduct
			List oldSrvProducts = srvProductManager.findByProperty("srvid",
					srvid);
			if (oldSrvProducts.size() > 0) {
				SrvProduct[] oldSrvProductObjects = (SrvProduct[]) oldSrvProducts
						.toArray(new SrvProduct[oldSrvProducts.size()]);
				cmsLog.info("删除原有服务与产品的配置关系记录。");
				srvProductManager.delete(oldSrvProductObjects);
			}

			if (productCategories.size() > 0) {
				// 保存SrvProduct
				SrvProduct[] newSrvProductObjects = new SrvProduct[productCategories
						.size()];

				for (int i = 0; i < productCategories.size(); i++) {
					ProductCategory productCategory = (ProductCategory) productCategories
							.get(i);
					SrvProduct srvProduct = new SrvProduct();
					srvProduct.setProductCategoryId(productCategory
							.getProductCategoryId());
					srvProduct.setSrvid(srvid);
					srvProduct.setSelecttag((long) 0);
					srvProduct.setInputtime(new Date());
					newSrvProductObjects[i] = srvProduct;
				}

				cmsLog.info("保存服务与产品的配置关系记录。");
				srvProductManager.save(newSrvProductObjects);
			}
		}
		cmsLog.debug("Cms -> CmsTransaction -> saveSrvProductsBySrvid returns.");
	}

	// 保存服务与节目分类的配置关系记录 SrvProgClass dicts ProgramCategoryDto 20091107
	// 保存服务与栏目的配置关系记录 SrvColumn PortalColumn 20091107
	// 保存服务与产品的配置关系记录 SrvProduct ProductCategory 20091107
	public void saveAllSrvConfigBySrvid(
			ISrvProgClassManager srvProgClassManager,
			ISrvColumnManager srvColumnManager,
			ISrvProductManager srvProductManager,
			ICmsServiceManager cmsServiceManager, List dicts,
			List portalColumns, List productCategories, String srvid) {
		cmsLog.debug("Cms -> CmsTransaction -> saveAllSrvConfigBySrvid...");
		saveSrvProgClassesBySrvid(srvProgClassManager, cmsServiceManager,
				dicts, srvid);
		saveSrvColumnsBySrvid(srvColumnManager, cmsServiceManager,
				portalColumns, srvid);
		saveSrvProductsBySrvid(srvProductManager, cmsServiceManager,
				productCategories, srvid);
		cmsLog
				.debug("Cms -> CmsTransaction -> saveAllSrvConfigBySrvid returns.");
	}

	// 删除服务（叶子节点） CmsService
	public void deleteCmsService(ICmsServiceManager cmsServiceManager,
			ISrvProgClassManager srvProgClassManager,
			ISrvColumnManager srvColumnManager,
			ISrvProductManager srvProductManager,
			IProgSrvRelManager progSrvRelManager, String srvId) {
		cmsLog.debug("Cms -> CmsTransaction -> deleteCmsService...");

		// 1 - 检查该节点是否是叶子节点
		// 2 - 如果不是，直接返回失败
		// 3 - 如果是，
		// 3.1 - 删除与节目分类、栏目、产品的配置关系记录
		// 3.2 - 删除与节目包的关系
		// 3.3 - 删除该服务节点
		// 3.4 - 判断父节点下是否有节点
		// 3.4.1 - 如果有，无操作
		// 3.4.2 - 如果没有，将父节点改成叶子节点

		// 1 - 检查该节点是否是叶子节点
		CmsService cmsService = (CmsService) cmsServiceManager.getById(srvId);
		if (cmsService == null) {
			// cmsResultDto.setResultCode((long)1);
			// cmsResultDto.setErrorMessage("该服务节点不存在。");
			cmsLog.info("该服务节点不存在。" + srvId);
		} else if ("N".equalsIgnoreCase(cmsService.getIsleaf())) {
			// 2 - 如果不是，直接返回失败
			// cmsResultDto.setResultCode((long)1);
			// cmsResultDto.setErrorMessage("该服务节点不是叶子节点，不能删除。");
			cmsLog.info("该服务节点不是叶子节点，不能删除。" + srvId);
		} else {
			// 3 - 如果是，
			// 3.1 - 删除与节目分类、栏目、产品的配置关系记录
			// SrvProgClass,SrvColumn,SrvProduct
			deleteSrvProgClassBySrvId(srvProgClassManager, cmsService
					.getSrvid());
			deleteSrvColumnBySrvId(srvColumnManager, cmsService.getSrvid());
			deleteSrvProductBySrvId(srvProductManager, cmsService.getSrvid());

			// 3.2 - 删除与节目包的关系
			// ProgSrvRel
			List progSrvRels = progSrvRelManager.findByProperty("srvid",
					cmsService.getSrvid());
			if (progSrvRels.size() > 0) {
				cmsLog.info("删除服务与节目包的关系记录。");
				ProgSrvRel[] progSrvRelObjects = (ProgSrvRel[]) progSrvRels
						.toArray(new ProgSrvRel[progSrvRels.size()]);
				progSrvRelManager.delete(progSrvRelObjects);
			}

			// 3.3 - 删除该服务节点
			// CmsService
			List parents = cmsServiceManager.findByProperty("defcatcode",
					cmsService.getParentsid());
			if (parents.size() > 0) {
				for (int i = 0; i < parents.size(); i++) {
					CmsService parent = (CmsService) parents.get(i);
					// 3.4 - 判断父节点下是否有其他节点
					List children = cmsServiceManager.findByProperty(
							"parentsid", parent.getDefcatcode());
					if (children.size() > 1) {
						// 3.4.1 - 如果有，无操作
					} else {
						// 3.4.2 - 如果没有，将父节点改成叶子节点
						cmsLog.info("将父节点改成叶子节点。");
						parent.setIsleaf("Y");
						cmsServiceManager.update(parent);
					}
				}
			}
			cmsLog.info("删除服务记录。");
			cmsServiceManager.deleteById(cmsService.getSrvid());
		}
		cmsLog.debug("Cms -> CmsTransaction -> deleteCmsService returns.");
	}

	// 保存（创建）服务（叶子节点） CmsService
	public CmsService saveCmsService(ICmsServiceManager cmsServiceManager,
			ISrvProgClassManager srvProgClassManager,
			ISrvColumnManager srvColumnManager,
			ISrvProductManager srvProductManager,
			IProgSrvRelManager progSrvRelManager, CmsService cmsService) {
		cmsLog.debug("Cms -> CmsTransaction -> saveCmsService...");

		// 0 - 查询判断新加节点的defcatcode，是否已经存在
		// 1 - 查询判断父节点，是否是叶子节点
		// 1.1 - 不是叶子节点，
		// 1.1.1 - 保存该服务节点
		// 1.2 - 是叶子节点，
		// 1.2.1 - 删除父节点与节目分类、栏目、产品的配置关系记录
		// SrvProgClass,SrvColumn,SrvProduct
		// 1.2.2 - 创建该服务节点
		// 1.2.3 - 修改父节点下所有的节目包的srvid为新建节点srvid
		// ProgSrvRel
		// 1.2.4 - 修改服务父节点为非叶子节点
		// CmsService

		// 0 - 查询判断新加节点的defcatcode，是否已经存在
		if (cmsService.getDefcatcode() != null
				&& !"".equalsIgnoreCase(cmsService.getDefcatcode())) {
			List curCmsServices = cmsServiceManager.findByProperty(
					"defcatcode", cmsService.getDefcatcode());
			if (curCmsServices.size() > 0) {
				// cmsResultDto.setResultCode((long)1);
				// cmsResultDto.setErrorMessage("服务代码已经存在。");
				cmsLog.info("服务代码已经存在。");
			} else {
				// 1 - 查询判断父节点，是否是叶子节点
				List parents = cmsServiceManager.findByProperty("defcatcode",
						cmsService.getParentsid());
				if (parents.size() > 0) {
					CmsService parent = (CmsService) parents.get(0);
					cmsLog.info("Parent SrvId:" + parent.getSrvid());
					cmsService.setDefcatlevel(parent.getDefcatlevel() + 1);
					cmsService.setInputtime(new Date());
					cmsService.setIsleaf("Y");
					cmsService.setRootid(parent.getRootid());
					cmsService.setDefcatseq(parent + "\\"
							+ cmsService.getDefcatcode());

					if ("N".equalsIgnoreCase(parent.getIsleaf())) {
						// 1.1 - 不是叶子节点，
						// 1.1.1 - 保存该服务节点
						cmsLog.info("服务父节点不是叶子节点，创建该服务。");
						cmsService = (CmsService) cmsServiceManager
								.save(cmsService);
						cmsLog.info("New SrvId:" + cmsService.getSrvid());
						// cmsResultDto.setResultObject(cmsService);
					} else {
						// 1.2 - 是叶子节点，
						// 1.2.1 - 删除父节点与节目分类、栏目、产品的配置关系记录
						// SrvProgClass,SrvColumn,SrvProduct
						cmsLog.info("服务父节点是叶子节点。");
						deleteSrvProgClassBySrvId(srvProgClassManager, parent
								.getSrvid());
						deleteSrvColumnBySrvId(srvColumnManager, parent
								.getSrvid());
						deleteSrvProductBySrvId(srvProductManager, parent
								.getSrvid());

						// 1.2.2 - 创建该服务节点
						cmsLog.info("创建该服务。");
						cmsService = (CmsService) cmsServiceManager
								.save(cmsService);
						cmsLog.info("New SrvId:" + cmsService.getSrvid());
						// cmsResultDto.setResultObject(cmsService);

						// 1.2.3 - 修改父节点下所有的节目包的srvid为新建节点srvid
						// ProgSrvRel
						List parentProgSrvRels = progSrvRelManager
								.findByProperty("srvid", parent.getSrvid());
						for (int i = 0; i < parentProgSrvRels.size(); i++) {
							ProgSrvRel progSrvRel = (ProgSrvRel) parentProgSrvRels
									.get(i);
							cmsLog.info("修改父节点的节目包为新建节点的节目包。ProgSrvRel.srvid:"
									+ progSrvRel.getSrvid());
							progSrvRel.setSrvid(cmsService.getSrvid());
							progSrvRelManager.update(progSrvRel);
						}

						// 1.2.4 - 修改服务父节点为非叶子节点
						// CmsService
						cmsLog.info("修改父节点为非叶子节点。");
						parent.setIsleaf("N");
						progSrvRelManager.update(parent);
					}
				} else {
					// cmsResultDto.setResultCode((long)1);
					// cmsResultDto.setErrorMessage("未查询到服务父节点。");
					cmsLog.info("未查询到服务父节点。");
				}
			}
		} else {
			// cmsResultDto.setResultCode((long)1);
			// cmsResultDto.setErrorMessage("服务代码为空。");
			cmsLog.info("服务代码为空。");
		}
		cmsLog.debug("Cms -> CmsTransaction -> saveCmsService returns.");

		return cmsService;
	}

	// 保存（加入）节目包，到srvid，ProgPackage CmsService 20091104
	public void saveProgPackagesToSrvId(ICmsServiceManager cmsServiceManager,
			IProgSrvRelManager progSrvRelManager, List progPackages,
			String srvId) {
		cmsLog.debug("Cms -> CmsTransaction -> saveProgPackagesToSrvId...");

		// 判断服务节点是否是叶子节点
		// 是，继续
		// 不是，返回失败
		CmsService cmsService = (CmsService) cmsServiceManager.getById(srvId);
		if (cmsService == null) {
			cmsLog.info("该服务不存在。");
			return;
		}
		if ("Y".equalsIgnoreCase(cmsService.getIsleaf())) {
			if (progPackages.size() > 0) {
				// ProgSrvRel[] progSrvRelObjects = new
				// ProgSrvRel[progPackages.size()];
				for (int i = 0; i < progPackages.size(); i++) {
					// 判断节目包是否属于服务根节点
					// 属于根节点，删除根节点关系，添加服务关系
					// 不属于根节点，添加服务关系
					ProgPackage progPackage = (ProgPackage) progPackages.get(i);
					List progSrvRels = progSrvRelManager
							.getProgSrvRelsByProductidAndSrvid(progPackage
									.getProductid(), "SV00000001");
					if (progSrvRels.size() > 0) {
						ProgSrvRel[] progSrvRelRootObjects = new ProgSrvRel[progSrvRels
								.size()];
						for (int j = 0; j < progSrvRels.size(); j++) {
							progSrvRelRootObjects[j] = (ProgSrvRel) progSrvRels
									.get(j);
							cmsLog.info("Old relid:"
									+ ((ProgSrvRel) progSrvRels.get(j))
											.getRelid());
							cmsLog.info("Old Productid:"
									+ ((ProgSrvRel) progSrvRels.get(j))
											.getProductid());
						}
						cmsLog.info("删除节目包与服务根节点的关系记录。");
						progSrvRelManager.delete(progSrvRelRootObjects);
					}

					// 查询节目包与服务是否已经存在关系记录
					// 如果存在，不操作
					// 如果不存在，添加
					List curProgSrvRels = progSrvRelManager
							.getProgSrvRelsByProductidAndSrvid(progPackage
									.getProductid(), srvId);
					if (curProgSrvRels.size() > 0) {

					} else {
						ProgSrvRel progSrvRel = new ProgSrvRel();
						progSrvRel.setProductid(progPackage.getProductid());
						progSrvRel.setSrvid(srvId);
						progSrvRel.setInputtime(new Date());
						// progSrvRelObjects[i] = progSrvRel;
						cmsLog.info("New ProductId:"
								+ progPackage.getProductid());
						cmsLog.info("New SrvId:" + srvId);
						cmsLog.info("创建节目包与服务的关系记录。");
						progSrvRelManager.save(progSrvRel);
					}
				}
				// cmsLog.info("创建节目包与服务的关系记录。");
				// progSrvRelManager.save(progSrvRelObjects);
			} else {
				// cmsResultDto.setResultCode((long)1);
				// cmsResultDto.setErrorMessage("节目包列表为空。");
				cmsLog.info("节目包列表为空。");
			}
		} else {
			// cmsResultDto.setResultCode((long)1);
			// cmsResultDto.setErrorMessage("所选择的服务节点不是叶子节点，不能添加节目包。");
			cmsLog.info("所选择的服务节点不是叶子节点，不能添加节目包。");
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgPackagesToSrvId returns.");
	}

	// 删除节目包，从srvid，ProgPackage CmsService 20091104
	public void deleteProgPackagesFromSrvId(
			IProgSrvRelManager progSrvRelManager, List progPackages,
			String srvId) {
		cmsLog.debug("Cms -> CmsTransaction -> deleteProgPackagesFromSrvId...");
		if (progPackages.size() > 0) {
			// 1 - 查询删除服务节目包关系
			// 2 - 查询节目包和服务关系
			// 3 - 没有，添加节目包和服务根节点关系
			// 4 - 有，判断关系数量
			// 5 - 如果唯一，不处理
			// 6 - 大于1，判断是否存在与服务根节点关系，如果有，删除与根节点的关系

			for (int i = 0; i < progPackages.size(); i++) {
				ProgPackage progPackage = (ProgPackage) progPackages.get(i);

				// 1 - 查询删除服务节目包关系
				List progSrvRels = progSrvRelManager
						.getProgSrvRelsByProductidAndSrvid(progPackage
								.getProductid(), srvId);
				if (progSrvRels.size() > 0) {
					ProgSrvRel[] progSrvRelObjects = new ProgSrvRel[progSrvRels
							.size()];
					for (int j = 0; j < progSrvRels.size(); j++) {
						progSrvRelObjects[j] = (ProgSrvRel) progSrvRels.get(j);
						cmsLog.info("Old relid:"
								+ ((ProgSrvRel) progSrvRels.get(j)).getRelid());
						cmsLog.info("Old ProductId:"
								+ ((ProgSrvRel) progSrvRels.get(j))
										.getProductid());
					}
					cmsLog.info("删除节目包与服务节点关系记录。");
					progSrvRelManager.delete(progSrvRelObjects);
				}

				// 2 - 查询节目包和服务关系
				List curProgSrvRels = progSrvRelManager.findByProperty(
						"productid", progPackage.getProductid());
				if (curProgSrvRels.size() > 0) {
					// 4 - 有，判断关系数量
					if (curProgSrvRels.size() == 1) {
						// 5 - 如果唯一，不处理
						cmsLog.info("节目包与服务节点关系唯一，无操作。");
					} else {
						// 6 - 大于1，判断是否存在与服务根节点关系，如果有，删除与根节点的关系
						cmsLog.info("节目包与服务节点关系不唯一。" + curProgSrvRels.size());
						// ProgSrvRel[] progSrvRelRootObjects = new
						// ProgSrvRel[curProgSrvRels.size()];
						for (int j = 0; j < curProgSrvRels.size(); j++) {
							ProgSrvRel progSrvRel = (ProgSrvRel) curProgSrvRels
									.get(j);
							if (progSrvRel.getSrvid() == "SV00000001") {
								// progSrvRelRootObjects[j] = progSrvRel;
								cmsLog.info("Old relid:"
										+ progSrvRel.getRelid());
								cmsLog.info("Old ProductId:"
										+ progSrvRel.getProductid());
								cmsLog.info("删除节目包与服务根节点的关系记录。");
								progSrvRelManager.deleteById(progSrvRel
										.getRelid());
							}
						}
					}
				} else {
					// 3 - 没有，添加节目包和服务根节点关系
					ProgSrvRel progSrvRel = new ProgSrvRel();
					progSrvRel.setProductid(progPackage.getProductid());
					progSrvRel.setSrvid("SV00000001");
					progSrvRel.setInputtime(new Date());
					cmsLog.info("创建节目包与根节点的关系记录。");
					cmsLog.info("new ProductId:" + progPackage.getProductid());
					progSrvRelManager.save(progSrvRel);
				}
			}
		} else {
			// cmsResultDto.setResultCode((long)1);
			// cmsResultDto.setErrorMessage("节目包列表为空。");
			cmsLog.info("节目包列表为空。");
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> deleteProgPackagesFromSrvId returns.");
	}

	// ------------------------------------- ProgPackage
	// ---------------------------------------------------

	// 复制文件
	// file --> smbfile
	private int copyFile(String strFileFrom, String strFileTo) {
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> copyFile...");
		cmsLog.info("From : " + strFileFrom);
		cmsLog.info("To : " + strFileTo);
		int ret = -1;

		try {
			int last = 0;
			Long localreadbytes = Long.valueOf(0);
			byte[] bytes = new byte[1024];
			SmbFile fileTo = new SmbFile(strFileTo);
			File fileFrom = new File(strFileFrom);
			SmbFileOutputStream fileStreamOut = new SmbFileOutputStream(fileTo,
					true);
			FileInputStream fileStreamIn = new FileInputStream(fileFrom);

			while ((last = fileStreamIn.read(bytes)) != -1) {
				fileStreamOut.write(bytes, 0, last);
				localreadbytes += last; // 已传输字节数
				// long nowProcess = localreadbytes / step; // 计算当前进度
				// if (nowProcess > process)
				// {
				// process = nowProcess;
				// transObj.setFileProcess(process);
				// instance.syncCtrl.start();
				// logger.debug("传输进度：" + process);
				// }
			}
			fileStreamOut.flush();

			ret = 0;
			cmsLog.info("Copy file successfully : " + strFileFrom + " --> "
					+ strFileTo);
		} catch (IOException ex) {
			ret = 1;
			cmsLog.info("Copy file unsuccessfully : " + strFileFrom + " --> "
					+ strFileTo);
			cmsLog.info(ex.getMessage());
		}

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> copyFile returns.");
		return ret;
	}

	// 删除文件
	// file
	private int deleteFile(String strFile) {
		// 删除文件
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> deleteFile...");
		int ireturn = -1;
		try {
			File file = new File(strFile);
			file.delete();

			ireturn = 0;
			cmsLog.info("Delete file successfully: " + strFile);
		} catch (Exception ex) {
			ireturn = 1;
			cmsLog.info("Delete file unsuccessfully: " + strFile);
			cmsLog.info(ex.getMessage());
		}

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> deleteFile returns.");
		return ireturn;
	}

	// 根据配置：SrvProduct，save 产品节目包关系：PpSrvPdtRel
	private void savePPSrvPdtRel(IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager, List cmsServices,
			ProgPackage progPackage) {
		cmsLog.debug("Cms -> CmsTransaction -> savePPSrvPdtRel ...");
		if (cmsServices == null) {
			cmsLog.debug("cmsServices == null");
			return;
		}
		Long ppSrvPdtRelCount = (long) 0;

		// 删除原有节目包与产品的关系记录
		List ppSrvPdtRels = ppSrvPdtRelManager.findByProperty("productid",
				progPackage.getProductid());
		for (int i = 0; i < ppSrvPdtRels.size(); i++) {
			PpSrvPdtRel ppSrvPdtRel = (PpSrvPdtRel) ppSrvPdtRels.get(i);
			ppSrvPdtRelManager.deleteById(ppSrvPdtRel.getRelid());
			cmsLog.debug("删除产品与节目包的关系记录。");
		}

		// 测试通过，20091102，16:28

		// 根据配置：SrvProduct，save 产品节目包关系：PPSrvPdtRel
		// List distinctColumns = new ArrayList();
		if (cmsServices.size() > 0) {
			for (int i = 0; i < cmsServices.size(); i++) {
				CmsService cmsService = (CmsService) cmsServices.get(i);
				List srvProducts = srvProductManager.findByProperty("srvid",
						cmsService.getSrvid());
				for (int j = 0; j < srvProducts.size(); j++) {
					SrvProduct srvProduct = (SrvProduct) srvProducts.get(j);

					// 根据progPackage.getProductid()和srvProduct.getProductCategoryId()，查询表PpSrvPdtRel，是否有记录
					// 如果有记录，则无操作
					// 如果无记录，则添加记录到PpSrvPdtRel
					List curPpSrvPdtRels = ppSrvPdtRelManager
							.getPpSrvPdtRelsByProductIdAndProductCategoryId(
									progPackage.getProductid(), srvProduct
											.getProductCategoryId());
					if (curPpSrvPdtRels.size() <= 0) {
						// 添加记录到PpSrvPdtRel
						PpSrvPdtRel ppSrvPdtRel = new PpSrvPdtRel();
						ppSrvPdtRel.setProductid(progPackage.getProductid());
						ppSrvPdtRel.setProductCategoryId(srvProduct
								.getProductCategoryId());
						ppSrvPdtRel.setSrvid(cmsService.getSrvid());
						ppSrvPdtRel.setInputtime(new Date());
						ppSrvPdtRelManager.save(ppSrvPdtRel);

						cmsLog.debug("保存产品与节目包的关系记录。"
								+ srvProduct.getProductCategoryId());
					} else {
						cmsLog.debug("产品与节目包的关系记录已经存在，不新加记录。");
					}
					ppSrvPdtRelCount++;
				}
			}
		}
		if (ppSrvPdtRelCount <= 0) {
			// PpSrvPdtRel ppSrvPdtRel = new PpSrvPdtRel();
			// ppSrvPdtRel.setProductid(progPackage.getProductid());
			// ppSrvPdtRel.setSrvid("SV00000001");
			// ppSrvPdtRel.setProductCategoryId((long)1);
			// ppSrvPdtRel.setInputtime(new Date());
			// ppSrvPdtRelManager.save(ppSrvPdtRel);
			//			
			// cmsLog.info("保存“其他”产品与节目包的关系记录。");
		}
		cmsLog.debug("Cms -> CmsTransaction -> savePPSrvPdtRel returns.");
	}

	// 根据配置：SrvColumun，save 栏目节目包关系：PPColumnRel
	private void savePPColumnRel(IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager, List cmsServices,
			ProgPackage progPackage) {
		cmsLog.debug("Cms -> CmsTransaction -> savePPColumnRel ...");

		if (cmsServices == null) {
			cmsLog.debug("cmsServices == null");
			return;
		}

		// 测试通过，20091102，16:15
		// 根据配置：SrvColumun，save 栏目节目包关系：PPColumnRel

		Long ppColumnRelCount = (long) 0;

		// 删除原有节目包与栏目的关系记录
		List pPColumnRels = pPColumnRelManager.findByProperty("productid",
				progPackage.getProductid());
		for (int i = 0; i < pPColumnRels.size(); i++) {
			PPColumnRel pPColumnRel = (PPColumnRel) pPColumnRels.get(i);
			pPColumnRelManager.deleteById(pPColumnRel.getRelid());
			cmsLog.debug("删除栏目与节目包的关系记录。");
		}

		if (cmsServices.size() > 0) {
			for (int i = 0; i < cmsServices.size(); i++) {
				CmsService cmsService = (CmsService) cmsServices.get(i);
				List srvColumns = srvColumnManager.findByProperty("srvid",
						cmsService.getSrvid());
				for (int j = 0; j < srvColumns.size(); j++) {
					SrvColumn srvColumn = (SrvColumn) srvColumns.get(j);

					// 根据progPackage.getProductid()和srvColumn.getColumnclassid()，查询表PPColumnRel，是否有记录
					// 如果有记录，则无操作
					// 如果无记录，则添加记录到PPColumnRel
					List curPPColumnRels = pPColumnRelManager
							.getPpColumnRelsByProductIdAndColumnclassid(
									progPackage.getProductid(), srvColumn
											.getColumnclassid());
					if (curPPColumnRels.size() <= 0) {
						// 添加记录到PPColumnRel
						PPColumnRel ppColumnRel = new PPColumnRel();
						ppColumnRel.setProductid(progPackage.getProductid());
						ppColumnRel.setColumnclassid(srvColumn
								.getColumnclassid());
						ppColumnRel.setSrvid(cmsService.getSrvid());
						// ppColumnRel.setProgstate(progstate);
						ppColumnRel.setInputtime(new Date());
						pPColumnRelManager.save(ppColumnRel);
						cmsLog.debug("保存栏目与节目包的关系记录。"
								+ srvColumn.getColumnclassid());
					} else {
						cmsLog.debug("栏目与节目包的关系记录已经存在，不新加记录。");
					}
					ppColumnRelCount++;
				}
			}
		}
		if (ppColumnRelCount <= 0) {
			PPColumnRel ppColumnRel = new PPColumnRel();
			ppColumnRel.setProductid(progPackage.getProductid());
			ppColumnRel.setSrvid("SV00000001");
			ppColumnRel.setColumnclassid("PC00000001");
			ppColumnRel.setInputtime(new Date());
			pPColumnRelManager.save(ppColumnRel);
			cmsLog.debug("保存“其他”栏目与节目包的关系记录。");
		}
		cmsLog.debug("Cms -> CmsTransaction -> savePPColumnRel returns.");
	}

	// save 节目包文件关系：PackageFiles
	private void savePackageFiles(
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager,
			IPackageFilesManager packageFilesManager, List programs,
			ProgPackage progPackage, Long styleId) {
		cmsLog.debug("Cms -> CmsTransaction -> savePackageFiles ...");

		if (programs == null) {
			cmsLog.debug("programs == null");
			return;
		}

		// 获取文件列表，根据节目列表
		List progFilesQuery = getProgramFilesByProgramInfosAndStyleId(
				packStyleFileTypeManager, programFilesManager, programs,
				styleId);

		// 测试通过，20091102，14:50
		// save 节目包文件关系：PackageFiles
		for (int i = 0; i < progFilesQuery.size(); i++) {
			List files = (List) progFilesQuery.get(i);
			for (int j = 0; j < files.size(); j++) {
				ProgramFiles programFiles = (ProgramFiles) files.get(j);

				// 根据progPackage.getProductid()、programFiles.getProgfileid()和programFiles.getProgramid()，查询表PackageFiles，是否有记录
				// 如果有记录，则无操作
				// 如果无记录，则添加记录到PackageFiles
				List packageFileses = packageFilesManager
						.getPackageFilesesByProductidProgfileidAndProgramid(
								progPackage.getProductid(), programFiles
										.getProgfileid(), programFiles
										.getProgramid());
				if (packageFileses.size() <= 0) {
					PackageFiles packageFiles = new PackageFiles();
					packageFiles.setProductid(progPackage.getProductid());
					packageFiles.setProgramid(programFiles.getProgramid());
					packageFiles.setProgfileid(programFiles.getProgfileid());
					packageFiles.setFiglobalid(programFiles.getFiglobalid());
					packageFilesManager.save(packageFiles);
				}
			}
		}
		cmsLog.debug("Cms -> CmsTransaction -> savePackageFiles returns.");
	}

	// save 节目包节目关系：ProgPPRel
	private void saveProgPPRel(IProgPPRelManager progPPRelManager,
			List programs, ProgPackage progPackage) {
		cmsLog.debug("Cms -> CmsTransaction -> saveProgPPRel ...");

		// 删除原有的关系记录
		List progPPRels = progPPRelManager.findByProperty("productid",
				progPackage.getProductid());
		for (int i = 0; i < progPPRels.size(); i++) {
			ProgPPRel progPPRel = (ProgPPRel) progPPRels.get(i);
			progPPRelManager.deleteById(progPPRel.getRelid());
		}

		// 测试通过，20091102，14:25
		// save 节目包节目关系：ProgPPRel
		if (programs != null && programs.size() > 0) {
			for (int i = 0; i < programs.size(); i++) {
				ProgramInfo programInfo = (ProgramInfo) programs.get(i);

				ProgPPRel progPPRel = new ProgPPRel();
				progPPRel.setProductid(progPackage.getProductid());
				progPPRel.setProgramid(programInfo.getProgramid());
				progPPRel.setInputtime(new Date());
				progPPRel = (ProgPPRel) progPPRelManager.save(progPPRel);
				cmsLog.debug(progPPRel.getRelid());
			}
		}

		cmsLog.debug("Cms -> CmsTransaction -> saveProgPPRel returns.");
	}

	// 根据旧的文件名（含后缀），得到新的文件名(含后缀)
	private String getNewFileName(String oldFileName, String str) {
		String newFileName = null;
		String[] name = oldFileName.split("\\.");

		if (name.length >= 2) {
			newFileName = str + "." + name[name.length - 1];
		} else {
			newFileName = str;
		}

		return newFileName;
	}

	// 根据节目包编号 productId ，删除节目包，
	// 同时删除 产品节目包关系：PpSrvPdtRel
	// 删除 栏目节目包关系：PPColumnRel
	// 删除 服务节目包关系：ProgSrvRel
	// 删除 节目包文件关系：PackageFiles
	// 删除 节目包节目关系：ProgPPRel
	public CmsResultDto deleteProgPackage(
			IProgPackageManager progPackageManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			IPPColumnRelManager pPColumnRelManager,
			IProgSrvRelManager progSrvRelManager,
			IPackageFilesManager packageFilesManager,
			IProgPPRelManager progPPRelManager, String productId) {
		cmsLog.debug("Cms -> CmsTransaction -> deleteProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		ProgPackage progPackage = (ProgPackage) progPackageManager
				.getById(productId);

		if (progPackage != null) {
			// 删除 产品节目包关系：PpSrvPdtRel
			cmsLog.info("删除 产品节目包关系：PpSrvPdtRel");
			List ppSrvPdtRels = ppSrvPdtRelManager.findByProperty("productid",
					productId);
			if (ppSrvPdtRels.size() > 0) {
				PpSrvPdtRel[] ppSrvPdtRelObjects = new PpSrvPdtRel[ppSrvPdtRels
						.size()];
				for (int i = 0; i < ppSrvPdtRels.size(); i++) {
					PpSrvPdtRel ppSrvPdtRel = (PpSrvPdtRel) ppSrvPdtRels.get(i);
					ppSrvPdtRelObjects[i] = ppSrvPdtRel;
				}
				ppSrvPdtRelManager.delete(ppSrvPdtRelObjects);
			}

			// 删除 栏目节目包关系：PPColumnRel
			cmsLog.info("删除 栏目节目包关系：PPColumnRel");
			List pPColumnRels = pPColumnRelManager.findByProperty("productid",
					productId);
			if (pPColumnRels.size() > 0) {
				PPColumnRel[] pPColumnRelObjects = new PPColumnRel[pPColumnRels
						.size()];
				for (int i = 0; i < pPColumnRels.size(); i++) {
					PPColumnRel pPColumnRel = (PPColumnRel) pPColumnRels.get(i);
					pPColumnRelObjects[i] = pPColumnRel;
				}
				pPColumnRelManager.delete(pPColumnRelObjects);
			}

			// 删除 服务节目包关系：ProgSrvRel
			cmsLog.info("删除 服务节目包关系：ProgSrvRel");
			List progSrvRels = progSrvRelManager.findByProperty("productid",
					productId);
			if (progSrvRels.size() > 0) {
				ProgSrvRel[] progSrvRelObjects = new ProgSrvRel[progSrvRels
						.size()];
				for (int i = 0; i < progSrvRels.size(); i++) {
					ProgSrvRel progSrvRel = (ProgSrvRel) progSrvRels.get(i);
					progSrvRelObjects[i] = progSrvRel;
				}
				progSrvRelManager.delete(progSrvRelObjects);
			}

			// 删除 节目包文件关系：PackageFiles
			cmsLog.info("删除 节目包文件关系：PackageFiles");
			List packageFileses = packageFilesManager.findByProperty(
					"productid", productId);
			if (packageFileses.size() > 0) {
				PackageFiles[] packageFilesObjects = new PackageFiles[packageFileses
						.size()];
				for (int i = 0; i < packageFileses.size(); i++) {
					PackageFiles packageFiles = (PackageFiles) packageFileses
							.get(i);
					packageFilesObjects[i] = packageFiles;
				}
				packageFilesManager.delete(packageFilesObjects);
			}

			// 删除 节目包节目关系：ProgPPRel
			cmsLog.info("删除 节目包节目关系：ProgPPRel");
			List progPPRels = progPPRelManager.findByProperty("productid",
					productId);
			if (progPPRels.size() > 0) {
				ProgPPRel[] progPPRelObjects = new ProgPPRel[progPPRels.size()];
				for (int i = 0; i < progPPRels.size(); i++) {
					ProgPPRel progPPRel = (ProgPPRel) progPPRels.get(i);
					progPPRelObjects[i] = progPPRel;
				}
				progPPRelManager.delete(progPPRelObjects);
			}

			progPackageManager.deleteById(progPackage.getProductid());
		} else {
			String str = "ProgPackage is null.";
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.debug("Cms -> CmsTransaction -> deleteProgPackage returns.");
		return cmsResultDto;
	}

	// 获取文件列表，根据节目列表
	public List getProgramFilesByProgramInfosAndStyleId(
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager, List programInfos,
			Long styleId) {
		cmsLog
				.debug("Cms -> CmsTransaction -> getProgramFilesByProgramInfosAndStyleId...");

		// if(packStyleFileTypeManager == null
		// || programFilesManager == null
		// || programInfos == null
		// || styleId == null
		// )
		// {
		// cmsLog.info("输入参数为null。");
		// return null;
		// }

		if (programInfos == null) {
			cmsLog.info("programInfos为null。");
			return null;
		}

		List programFiles = new ArrayList();

		// 获取样式对应的文件类型列表
		List fileTypes = packStyleFileTypeManager.findByProperty("styleid",
				styleId);

		for (int i = 0; i < programInfos.size(); i++) {
			ProgramInfo programInfo = (ProgramInfo) programInfos.get(i);
			List sameProgFiles = new ArrayList();

			// 根据样式和节目编号，查询节目文件
			for (int j = 0; j < fileTypes.size(); j++) {
				PackStyleFileType packStyleFileType = (PackStyleFileType) fileTypes
						.get(j);
				List files = programFilesManager.findbyprogramidfiletype(
						programInfo.getProgramid(), packStyleFileType
								.getFiletypeid());

				for (int k = 0; k < files.size(); k++) {
					sameProgFiles.add(files.get(k));
				}
			}
			programFiles.add(sameProgFiles);
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> getProgramFilesByProgramInfosAndStyleId returns.");
		return programFiles;
	}

	// 修改节目包所有信息，ProgPackage ProgSrvRel ProgPPRel PackageFiles
	public void updateProgPackage(IProgPackageManager progPackageManager,
			IProgPPRelManager progPPRelManager,
			IPackageFilesManager packageFilesManager,
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager,
			IProgSrvRelManager progSrvRelManager,
			IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager, ProgPackage progPackage,
			List cmsServices, List programs, Long styleId) {
		cmsLog.debug("Cms -> CmsTransaction -> updateProgPackage...");
		if (progPackage.getUpdatetime() == null)
			progPackage.setUpdatetime(new Date());
		progPackageManager.update(progPackage);

		if (cmsServices != null) {
			// saveProgSrvRel(
			// progSrvRelManager,
			// cmsServices,
			// progPackage
			// );

			// 测试通过，20091102，15:03
			// save 服务节目包关系：ProgSvrRel
			cmsLog.info("save 服务节目包关系：ProgSvrRel");
			saveProgSrvRel(progSrvRelManager, cmsServices, progPackage);

			// 根据配置：SrvColumun，save 栏目节目包关系：PPColumnRel
			cmsLog.info("根据配置：SrvColumun，save 栏目节目包关系：PPColumnRel");
			savePPColumnRel(pPColumnRelManager, srvColumnManager, cmsServices,
					progPackage);

			// 根据配置：SrvProduct，save 产品节目包关系：PPSrvPdtRel
			cmsLog.info("根据配置：SrvProduct，save 产品节目包关系：PPSrvPdtRel");
			savePPSrvPdtRel(ppSrvPdtRelManager, srvProductManager, cmsServices,
					progPackage);
		}

		if (programs != null) {
			updateProgPPRelAndPackageFiles(progPPRelManager,
					packageFilesManager, packStyleFileTypeManager,
					programFilesManager, programs, progPackage, styleId);
		}
		cmsLog.debug("Cms -> CmsTransaction -> updateProgPackage returns.");
	}

	// 修改节目包节目、节目包文件关系：ProgPPRel、PackageFiles
	public void updateProgPPRelAndPackageFiles(
			IProgPPRelManager progPPRelManager,
			IPackageFilesManager packageFilesManager,
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager, List programs,
			ProgPackage progPackage, Long styleId) {
		cmsLog
				.debug("Cms -> CmsTransaction -> updateProgPPRelAndPackageFiles...");

		// 删除原有关系记录
		List progPPRels = progPPRelManager.findByProperty("productid",
				progPackage.getProductid());
		List packageFileses = packageFilesManager.findByProperty("productid",
				progPackage.getProductid());

		if (progPPRels.size() > 0) {
			ProgPPRel[] progPPRelObjects = new ProgPPRel[progPPRels.size()];
			for (int i = 0; i < progPPRels.size(); i++) {
				ProgPPRel progPPRel = (ProgPPRel) progPPRels.get(i);
				progPPRelObjects[i] = progPPRel;
			}
			progPPRelManager.delete(progPPRelObjects);
			cmsLog.info("节目包与节目关系已经删除。");
		}
		if (packageFileses.size() > 0) {
			// PackageFiles[] packageFilesObjects = new
			// PackageFiles[packageFileses.size()];
			for (int i = 0; i < packageFileses.size(); i++) {
				PackageFiles packageFiles = (PackageFiles) packageFileses
						.get(i);
				ProgramFiles progf = (ProgramFiles) programFilesManager
						.getById(packageFiles.getProgfileid());
				if ("H264".equals(progf.getFiletypeid())
						|| 1L == progf.getProgrank()) {
					cmsLog.warn("文件ID：" + progf.getProgfileid()
							+ "是主文件，删除节目包与之关系记录。");
					packageFilesManager.deleteById(packageFiles
							.getCmspackageFilesid().toString());
				}
				// packageFilesObjects[i] = packageFiles;
			}
			// cmsLog.warn("这里需要判断，只删除节目包与节目文件的关系。");
			// packageFilesManager.delete(packageFilesObjects);

			cmsLog.info("节目包与文件关系已经删除。");
		}
		
		// 删除原有关系记录
		List progPPRels2 = progPPRelManager.findByProperty("productid",
				progPackage.getProductid());
		List packageFileses2 = packageFilesManager.findByProperty("productid",
				progPackage.getProductid());

		// 测试通过，20091102，14:25
		// save 节目包节目关系：ProgPPRel
		for (int i = 0; i < programs.size(); i++) {

			ProgramInfo programInfo = (ProgramInfo) programs.get(i);

			ProgPPRel progPPRel = new ProgPPRel();
			progPPRel.setProductid(progPackage.getProductid());
			progPPRel.setProgramid(programInfo.getProgramid());
			progPPRel.setInputtime(new Date());
			progPPRelManager.save(progPPRel);
			cmsLog.info("保存节目包与节目关系。" + programInfo.getProgramid());
		}

		// 获取文件列表，根据节目列表
		List progFilesQuery = getProgramFilesByProgramInfosAndStyleId(
				packStyleFileTypeManager, programFilesManager, programs,
				(long) styleId);

		// 测试通过，20091102，14:50
		// save 节目包文件关系：PackageFiles
		for (int i = 0; i < progFilesQuery.size(); i++) {
			List files = (List) progFilesQuery.get(i);
			for (int j = 0; j < files.size(); j++) {
				ProgramFiles programFiles = (ProgramFiles) files.get(j);

				PackageFiles packageFiles = new PackageFiles();
				packageFiles.setProductid(progPackage.getProductid());
				packageFiles.setProgramid(programFiles.getProgramid());
				packageFiles.setProgfileid(programFiles.getProgfileid());
				packageFiles.setFiglobalid(programFiles.getFiglobalid());
				packageFilesManager.save(packageFiles);
				cmsLog.info("保存节目包与文件关系。" + programFiles.getProgfileid());
			}
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> updateProgPPRelAndPackageFiles returns.");
	}

	// 20100608 16:16
	// 修改节目包主文件关系：PackageFiles
	private int updatePackageFiles(IPackageFilesManager packageFilesManager,
			IProgramFilesManager programFilesManager, List programfiles,
			ProgPackage progPackage) {
		cmsLog.debug("Cms -> CmsTransaction -> updatePackageFiles...");

		if (programfiles != null && programfiles.size() > 0) {
			// 删除原有关系记录
			List packageFileses = packageFilesManager.findByProperty(
					"productid", progPackage.getProductid());

			if (packageFileses.size() > 0) {
				for (int i = 0; i < packageFileses.size(); i++) {
					PackageFiles packageFiles = (PackageFiles) packageFileses
							.get(i);
					ProgramFiles progf = (ProgramFiles) programFilesManager
							.getById(packageFiles.getProgfileid());
					if (progf.getProgrank() == 1) {
						cmsLog.warn("文件ID：" + progf.getProgfileid()
								+ "是主文件，删除节目包与之关系记录。");
						packageFilesManager.deleteById(packageFiles
								.getCmspackageFilesid().toString());
					}
				}

				cmsLog.info("节目包与主文件关系已经删除。");
			}

			// 测试通过，20091102，14:50
			// save 节目包文件关系：PackageFiles

			for (int j = 0; j < programfiles.size(); j++) {
				ProgramFiles programFiles = (ProgramFiles) programfiles.get(j);

				PackageFiles packageFiles = new PackageFiles();
				packageFiles.setProductid(progPackage.getProductid());
				packageFiles.setProgramid(programFiles.getProgramid());
				packageFiles.setProgfileid(programFiles.getProgfileid());
				packageFiles.setFiglobalid(programFiles.getFiglobalid());
				packageFilesManager.save(packageFiles);
				cmsLog.info("保存节目包与主文件关系。" + programFiles.getProgfileid());
			}
		} else {
			cmsLog.info("主文件列表为空，不保存。");
		}

		cmsLog.debug("Cms -> CmsTransaction -> updatePackageFiles returns.");
		return 0;
	}

	// save 服务节目包关系：ProgSrvRel
	public void saveProgSrvRel(IProgSrvRelManager progSrvRelManager,
			List cmsServices, ProgPackage progPackage) {
		cmsLog.debug("Cms -> CmsTransaction -> saveProgSrvRel...");

		if (cmsServices == null) {
			cmsLog.debug("cmsServices == null");
			return;
		}

		Long progSrvRelCount = (long) 0;

		// 删除原有关系记录
		List progSrvRels = progSrvRelManager.findByProperty("productid",
				progPackage.getProductid());
		for (int i = 0; i < progSrvRels.size(); i++) {
			ProgSrvRel progSrvRel = (ProgSrvRel) progSrvRels.get(i);
			progSrvRelManager.deleteById(progSrvRel.getRelid());
		}

		if (cmsServices.size() > 0) {
			// 保存节目包服务关系 ProgSrvRel
			for (int i = 0; i < cmsServices.size(); i++) {
				CmsService cmsService = (CmsService) cmsServices.get(i);

				ProgSrvRel progSrvRel = new ProgSrvRel();
				progSrvRel.setProductid(progPackage.getProductid());
				progSrvRel.setSrvid(cmsService.getSrvid());
				progSrvRel.setInputtime(new Date());
				progSrvRelManager.save(progSrvRel);

				cmsLog.debug("保存服务与节目包的关系记录。" + cmsService.getSrvid());

				progSrvRelCount++;
			}
		}
		if (progSrvRelCount <= 0) {
			// 保存节目包与服务根节点(其他)的关系
			ProgSrvRel progSrvRel = new ProgSrvRel();
			progSrvRel.setProductid(progPackage.getProductid());
			progSrvRel.setSrvid("SV00000001");
			progSrvRel.setInputtime(new Date());
			progSrvRelManager.save(progSrvRel);
			cmsLog.info("保存“其他”服务与节目包的关系记录。");
		}
		cmsLog.debug("Cms -> CmsTransaction -> saveProgSrvRel returns.");
	}

	// 20100120 14:10
	// save 服务节目包关系：ProgSrvRel，同时修改节目包与栏目 节目包与产品的关系
	public void saveProgSrvRelColumnRelProductCategoryRel(
			IProgPackageManager progPackageManager,
			IProgSrvRelManager progSrvRelManager,
			IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager, List cmsServices,
			ProgPackage progPackage) {
		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgSrvRelColumnRelProductCategoryRel...");
		if (progPackage.getUpdatetime() == null)
			progPackage.setUpdatetime(new Date());
		progPackageManager.update(progPackage);

		if (cmsServices != null) {
			// 测试通过，20091102，15:03
			// save 服务节目包关系：ProgSvrRel
			cmsLog.info("save 服务节目包关系：ProgSvrRel");
			saveProgSrvRel(progSrvRelManager, cmsServices, progPackage);

			// 根据配置：SrvColumun，save 栏目节目包关系：PPColumnRel
			cmsLog.info("根据配置：SrvColumun，save 栏目节目包关系：PPColumnRel");
			savePPColumnRel(pPColumnRelManager, srvColumnManager, cmsServices,
					progPackage);

			// 根据配置：SrvProduct，save 产品节目包关系：PPSrvPdtRel
			cmsLog.info("根据配置：SrvProduct，save 产品节目包关系：PPSrvPdtRel");
			savePPSrvPdtRel(ppSrvPdtRelManager, srvProductManager, cmsServices,
					progPackage);
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgSrvRelColumnRelProductCategoryRel returns.");
	}

	// 创建（定义）节目包，输入节目包描述信息、节目类型、样式、节目分类、服务列表、节目列表、文件列表
	public ProgPackage saveProgPackage(IProgPackageManager progPackageManager,
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager,
			IPackageFilesManager packageFilesManager,
			IProgPPRelManager progPPRelManager,
			IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager,
			IProgSrvRelManager progSrvRelManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager, ProgPackage progPackage,
			Long styleId, List cmsServices, List programs, List progFiles) {
		cmsLog.debug("Cms -> CmsTransaction -> saveProgPackage...");

		if (cmsServices == null)
			cmsServices = new ArrayList();

		// save 节目包
		cmsLog.debug("save 节目包");
		if (progPackage.getUpdatetime() == null
				|| progPackage.getInputtime() == null) {
			Date now = new Date();
			progPackage.setInputtime(now);
			progPackage.setUpdatetime(now);
			/**
			 * HuangBo addition by 2010年11月23日 11时40分
			 * 创建节目包时, 如果选择为创建无实体文件节目包, 则节目包状态为未导入.
			 */
			if (-1 == progPackage.getMainFileFlag()) {
				progPackage.setState(-1L);
			}
			progPackage = (ProgPackage) progPackageManager.save(progPackage);
		}

		// 测试通过，20091102，14:25
		// save 节目包节目关系：ProgPPRel
		cmsLog.debug("save 节目包节目关系：ProgPPRel");
		saveProgPPRel(progPPRelManager, programs, progPackage);

		// 测试通过，20091102，14:50
		// save 节目包文件关系：PackageFiles
		cmsLog.debug("save 节目包文件关系：PackageFiles");
		savePackageFiles(packStyleFileTypeManager, programFilesManager,
				packageFilesManager, programs, progPackage, styleId);

		// 测试通过，20091102，15:03
		// save 服务节目包关系：ProgSvrRel
		cmsLog.debug("save 服务节目包关系：ProgSvrRel");
		saveProgSrvRel(progSrvRelManager, cmsServices, progPackage);

		// 根据配置：SrvColumun，save 栏目节目包关系：PPColumnRel
		cmsLog.debug("根据配置：SrvColumun，save 栏目节目包关系：PPColumnRel");
		savePPColumnRel(pPColumnRelManager, srvColumnManager, cmsServices,
				progPackage);

		// 根据配置：SrvProduct，save 产品节目包关系：PPSrvPdtRel
		cmsLog.debug("根据配置：SrvProduct，save 产品节目包关系：PPSrvPdtRel");
		savePPSrvPdtRel(ppSrvPdtRelManager, srvProductManager, cmsServices,
				progPackage);
		cmsLog.debug("Cms -> CmsTransaction -> saveProgPackage returns.");
		return progPackage;
	}

	// 保存xml文件记录，到文件表、ASM节目位置表、节目包文件表
	public AmsStoragePrgRel saveUploadFile(
			IProgramFilesManager programFilesManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IPackageFilesManager packageFilesManager,
			ProgramFiles programFiles, ProgPackage progPackage,
			AmsStorage amsStorage, AmsStorageDir amsStorageDir,
			String filepath, boolean changeFilename // 是否需要修改文件名为文件记录id，用于xml和png
	) {
		cmsLog.debug("Cms -> CmsTransaction -> saveUploadFile...");

		// save到文件表
		programFilesManager.save(programFiles);
		cmsLog.debug("已保存到节目文件表。节目文件ID：" + programFiles.getProgfileid());

		// 20100204 16:56
		// 北京调试，这里有问题，如果是xml文件，是不需要修改文件名字的
		if (changeFilename == true) {
			if (programFiles.getFilename() != null) {
				programFiles.setFilename(getNewFileName(programFiles
						.getFilename(), programFiles.getProgfileid()));
			} else {
				cmsLog.warn("输入文件名为null。");
				programFiles.setFilename(programFiles.getProgfileid());
			}
		}
		programFiles.setStorageid(amsStorage.getStglobalid());
		programFiles.setStoragedirid(amsStorageDir.getStdirglobalid());
		programFilesManager.update(programFiles);
		cmsLog.debug("已更新节目文件表。节目文件ID：" + programFiles.getProgfileid());

		// save到节目存放位置表
		AmsStoragePrgRel amsStoragePrgRel = new AmsStoragePrgRel();
		amsStoragePrgRel.setStglobalid(amsStorage.getStglobalid()); // 存储体GLOBALID
		amsStoragePrgRel.setStdirglobalid(amsStorageDir.getStdirglobalid()); // 存储体目录GLOBALID
		amsStoragePrgRel.setProgfileid(programFiles.getProgfileid());
		amsStoragePrgRel.setFtypeglobalid(programFiles.getFiletypeid()); // 文件类型GLOBALID
		amsStoragePrgRel.setFilename(programFiles.getFilename()); // 文件名称
		amsStoragePrgRel.setUploadtime(programFiles.getInputtime()); // 上传日期
		amsStoragePrgRel.setInputmanid(programFiles.getInputmanid());
		amsStoragePrgRel.setInputtime(new Date());
		amsStoragePrgRel.setFilepath(filepath);
		amsStoragePrgRel.setFiledate(new Date());
		amsStoragePrgRel.setPrglobalid(progPackage.getProductid());
		amsstorageprgrelManager.save(amsStoragePrgRel);
		cmsLog.debug("已保存到文件存放位置表。文件位置ID：" + amsStoragePrgRel.getRelid());

		// save到节目包文件表
		PackageFiles packageFiles = new PackageFiles();
		packageFiles.setProductid(progPackage.getProductid());
		packageFiles.setProgfileid(programFiles.getProgfileid());
		packageFilesManager.save(packageFiles);
		cmsLog.debug("已保存到节目包文件表。节目包文件ID："
						+ packageFiles.getCmspackageFilesid());

		cmsLog.debug("Cms -> CmsTransaction -> saveUploadFile returns.");
		return amsStoragePrgRel;
	}

	// 20100304 10:26
	// 修改节目包，包括节目包描述、节目包与服务产品栏目的关系、节目包与文件的关系、海报更新
	public CmsResultDto updateProgPackageAllInfo(
			IProgPackageManager progPackageManager,
			IProgSrvRelManager progSrvRelManager,
			IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager,
			IProgPPRelManager progPPRelManager,
			IPackageFilesManager packageFilesManager,
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgListDetailManager progListDetailManager,
			Boolean updateprogpackage, // 修改节目包信息标识
			ProgPackage progPackage, // 节目包信息，原方法
			// modifyProgPPRelAndPackageFiles、modifyProgSrvRel、modifyProgPackage
			Boolean updatepackagefiles, // 修改节目包节目文件关系标识
			List programs, // 节目列表，原方法 modifyProgPPRelAndPackageFiles
			Long styleId, // 样式ID，原方法 modifyProgPPRelAndPackageFiles
			Boolean updatecmsservice, // 修改节目包服务关系标识
			List cmsServices, // 服务列表，原方法 modifyProgSrvRel
			Boolean updatepng, // 修改海报标识
			List programFileses, // 海报列表，包含了修改和未修改的海报信息
			List fileFroms, // 海报源路径列表，在服务器上
			List storageclasses, // 海报目标存储体等级code列表
			String filecode, // 节目包xml的filecode
			String stclasscode // 节目包xml文件存放存储体等级
	) {
		cmsLog.debug("Cms -> CmsTransaction -> updateProgPackageAllInfo...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		Date date = new Date();
		String info = "";

		String strdate = fileoper.convertDateToString(date,
				"yyyy-MM-dd HH:mm:ss");
		cmsLog.debug("更新日期：" + strdate);

		if (updateprogpackage == true) {
			cmsLog.debug("准备修改节目包信息...");
			progPackageManager.update(progPackage);
			info += "节目包信息已经修改；\r\n";
		}
		if (updatepackagefiles == true) {
			cmsLog.debug("准备修改节目包与节目文件关系...");
			updateProgPPRelAndPackageFiles(progPPRelManager,
					packageFilesManager, packStyleFileTypeManager,
					programFilesManager, programs, progPackage, styleId);
			info += "节目包与节目文件关系已经修改；\r\n";
		}
		if (updatecmsservice == true) {
			cmsLog.debug("准备修改节目包与服务(栏目、产品)关系...");
			saveProgSrvRelColumnRelProductCategoryRel(progPackageManager,
					progSrvRelManager, pPColumnRelManager, srvColumnManager,
					ppSrvPdtRelManager, srvProductManager, cmsServices,
					progPackage);
//			info += "节目包与服务(栏目、产品)关系已经修改；\r\n";
		}
		if (updatepng == true) {
			cmsLog.debug("准备修改节目包的海报...");
			CmsResultDto c1 = updateProgPackagePngs(progPackageManager,
					packageFilesManager, programFilesManager,
					amsstorageprgrelManager, progPackage, programFileses, // 海报列表，包含了修改和未修改的海报信息
					fileFroms, // 海报源路径列表，在服务器上
					storageclasses, // 海报目标存储体等级code列表
					date // 更新日期
			);
			info += "节目包的海报已经修改；\r\n";
			info += "详细信息：\r\n";
			info += c1.getErrorDetail();
		}

		// 修改节目包的ppxml、更新日期字段
		if (updatepackagefiles == true || updatecmsservice == true
				|| updatepng == true) {
			cmsLog.debug("准备修改节目包的ppxml和更新日期...");
			progPackage.setUpdatetime(date);
			progPackage = updateProgPackagePpxml(packageFilesManager,
					programFilesManager, progPackage);
			progPackage.setFilesizehi(packageFilesManager.sumOfPackageFileSize(progPackage.getProductid()));
			progPackageManager.update(progPackage);
			cmsLog.debug("节目包的ppxml和更新日期已修改。");
			info += "节目包的ppxml和更新日期已修改；\r\n";

			cmsLog.debug("准备修改节目包相关的栏目单详细的更新日期...");
			List plds = progListDetailManager.findByProperty("productid",
					progPackage.getProductid());
			if (plds != null && plds.size() > 0) {
				for (int i = 0; i < plds.size(); i++) {
					ProgListDetail progListDetail = (ProgListDetail) plds
							.get(i);
					progListDetail.setUpdatetime(date);
					progListDetailManager.update(progListDetail);
				}
			}
			cmsLog.debug("节目包相关的栏目单详细的更新日期已经修改。");
			info += "节目包相关的栏目单详细的更新日期已经修改；\r\n";

			List progfs = packageFilesManager
					.getProgramFilesesByProductidFilecode(progPackage
							.getProductid(), filecode);
			if (progfs != null && progfs.size() > 0) {
				ProgramFiles progf = (ProgramFiles) progfs.get(0);
				/**
				 * HuangBo addition by 2010年12月3日 10时49分
				 * 增加修改文件记录的修改日期
				 */
				progf.setUpdateTime(date);
				progf.setContentfilesize("0");
				programFilesManager.update(progf);

				// 返回：List
				// 1 - Stringgq
				// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
				// 2 - List<Object[]>
				// (AmsStorage)Object[0]
				// (AmsStoragePrgRel)Object[1]
				// (AmsStorageDir)Object[2]
				// (AmsStorageClass)Object[3]
				List paths = packageFilesManager
						.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
								progf.getProgfileid(), stclasscode, progPackage
										.getProductid());
				if (paths != null && paths.size() >= 2) {
					AmsStorage amsst = null;
					AmsStoragePrgRel amsstpr = null;
					AmsStorageDir amsstd = null;
					AmsStorageClass amsstc = null;
					String sourcepath = (String) paths.get(0);
					List list = (List) paths.get(1);
					if (list != null && list.size() > 0) {
						Object[] rows = (Object[]) list.get(0);
						amsst = (AmsStorage) rows[0];
						amsstpr = (AmsStoragePrgRel) rows[1];
						amsstd = (AmsStorageDir) rows[2];
						amsstc = (AmsStorageClass) rows[3];
					}

					if (fileoper.checkSmbFileExist(sourcepath)) {
						int ret = fileoper.createSmbFile(sourcepath,
								progPackage.getPpxml());
						if (ret == 0) {
							if (amsstpr != null) {
								amsstpr.setFiledate(date);
								amsstorageprgrelManager.update(amsstpr);
								cmsLog.debug("节目包的xml文件已经修改。" + sourcepath);
								info += "节目包的xml文件已经修改；\r\n";
							}
						} else {
							cmsLog.debug("更新节目包的xml文件失败。" + sourcepath);
							info += "更新节目包的xml文件失败；\r\n";
						}
					} else {
						cmsLog.debug("节目包的xml文件不存在，不修改xml文件。" + sourcepath);
						info += "节目包的xml文件不存在，不修改xml文件；\r\n";
					}
				} else {
					cmsLog.debug("节目包的xml文件路径不存在，不修改xml文件。");
					info += "节目包的xml文件路径不存在，不修改xml文件；\r\n";
				}
			} else {
				cmsLog.debug("节目包的xml文件记录不存在，不修改xml文件。");
				info += "节目包的xml文件记录不存在，不修改xml文件；\r\n";
			}
		} else {
			info = "没有修改请求，不修改节目包。";
		}
		cmsResultDto.setErrorDetail(info);

		cmsLog.debug("Cms -> CmsTransaction -> updateProgPackageAllInfo returns.");
		return cmsResultDto;
	}

	// 20100608 16:09
	// 修改节目包，包括节目包描述、节目包与服务产品栏目的关系、节目包与文件的关系、海报更新
	public CmsResultDto updateProgPackageWithoutPrograminfoCmsservice(
			IProgPackageManager progPackageManager,
			IProgSrvRelManager progSrvRelManager,
			IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager,
			IProgPPRelManager progPPRelManager,
			IPackageFilesManager packageFilesManager,
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgListDetailManager progListDetailManager,
			Boolean updateprogpackage, // 修改节目包信息标识
			ProgPackage progPackage, // 节目包信息，原方法
			// modifyProgPPRelAndPackageFiles、modifyProgSrvRel、modifyProgPackage
			Boolean updatepackagefiles, // 修改节目包节目文件关系标识
			List programfiles, // 主文件列表，原方法 modifyProgPPRelAndPackageFiles
			Boolean updatecmsservice, // 修改节目包服务关系标识
			List cmsServices, // 服务列表，原方法 modifyProgSrvRel
			Boolean updatepng, // 修改海报标识
			List programFileses, // 海报列表，包含了修改和未修改的海报信息
			List fileFroms, // 海报源路径列表，在服务器上
			List storageclasses, // 海报目标存储体等级code列表
			String filecode, // 节目包xml的filecode
			String stclasscode // 节目包xml文件存放存储体等级
	) {
		cmsLog.debug("Cms -> CmsTransaction -> updateProgPackageWithoutPrograminfoCmsservice...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		Date date = new Date();
		String info = "";

		String strdate = fileoper.convertDateToString(date,
				"yyyy-MM-dd HH:mm:ss");
		cmsLog.debug("更新日期：" + strdate);

		if (updateprogpackage == true) {
			cmsLog.debug("准备修改节目包信息...");
			progPackageManager.update(progPackage);
			info += "节目包信息已经修改；\r\n";
		}
		if (updatepackagefiles == true) {
			cmsLog.debug("准备修改节目包与主文件关系...");
			updatePackageFiles(packageFilesManager, programFilesManager,
					programfiles, progPackage);

			info += "节目包与主文件关系已经修改；\r\n";
		}
		if (updatecmsservice == true) {
			cmsLog.debug("准备修改节目包与服务(栏目、产品)关系...");
			saveProgSrvRelColumnRelProductCategoryRel(progPackageManager,
					progSrvRelManager, pPColumnRelManager, srvColumnManager,
					ppSrvPdtRelManager, srvProductManager, cmsServices,
					progPackage);
			info += "节目包与服务(栏目、产品)关系已经修改；\r\n";
		}
		if (updatepng == true) {
			cmsLog.debug("准备修改节目包的海报...");
			CmsResultDto c1 = updateProgPackagePngs(progPackageManager,
					packageFilesManager, programFilesManager,
					amsstorageprgrelManager, progPackage, programFileses, // 海报列表，包含了修改和未修改的海报信息
					fileFroms, // 海报源路径列表，在服务器上
					storageclasses, // 海报目标存储体等级code列表
					date // 更新日期
			);
			info += "节目包的海报已经修改；\r\n";
			info += "详细信息：\r\n";
			info += c1.getErrorDetail();
		}

		// 修改节目包的ppxml、更新日期字段
		if (updatepackagefiles == true || updatecmsservice == true
				|| updatepng == true) {
			cmsLog.debug("准备修改节目包的ppxml和更新日期...");
			progPackage.setUpdatetime(date);
			progPackage = updateProgPackagePpxml(packageFilesManager,
					programFilesManager, progPackage);
			progPackageManager.update(progPackage);
			cmsLog.debug("节目包的ppxml和更新日期已修改。");
			info += "节目包的ppxml和更新日期已修改；\r\n";

			cmsLog.debug("准备修改节目包相关的栏目单详细的更新日期...");
			List plds = progListDetailManager.findByProperty("productid",
					progPackage.getProductid());
			if (plds != null && plds.size() > 0) {
				for (int i = 0; i < plds.size(); i++) {
					ProgListDetail progListDetail = (ProgListDetail) plds
							.get(i);
					progListDetail.setUpdatetime(date);
					progListDetailManager.update(progListDetail);
				}
			}
			cmsLog.debug("节目包相关的栏目单详细的更新日期已经修改。");
			info += "节目包相关的栏目单详细的更新日期已经修改；\r\n";

			List progfs = packageFilesManager
					.getProgramFilesesByProductidFilecode(progPackage
							.getProductid(), filecode);
			if (progfs != null && progfs.size() > 0) {
				ProgramFiles progf = (ProgramFiles) progfs.get(0);

				// 返回：List
				// 1 - String
				// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
				// 2 - List<Object[]>
				// (AmsStorage)Object[0]
				// (AmsStoragePrgRel)Object[1]
				// (AmsStorageDir)Object[2]
				// (AmsStorageClass)Object[3]
				List paths = packageFilesManager
						.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
								progf.getProgfileid(), stclasscode, progPackage
										.getProductid());
				if (paths != null && paths.size() >= 2) {
					AmsStorage amsst = null;
					AmsStoragePrgRel amsstpr = null;
					AmsStorageDir amsstd = null;
					AmsStorageClass amsstc = null;
					String sourcepath = (String) paths.get(0);
					List list = (List) paths.get(1);
					if (list != null && list.size() > 0) {
						Object[] rows = (Object[]) list.get(0);
						amsst = (AmsStorage) rows[0];
						amsstpr = (AmsStoragePrgRel) rows[1];
						amsstd = (AmsStorageDir) rows[2];
						amsstc = (AmsStorageClass) rows[3];
					}

					if (fileoper.checkSmbFileExist(sourcepath)) {
						int ret = fileoper.createSmbFile(sourcepath,
								progPackage.getPpxml());
						if (ret == 0) {
							if (amsstpr != null) {
								amsstpr.setFiledate(date);
								amsstorageprgrelManager.update(amsstpr);
								cmsLog.debug("节目包的xml文件已经修改。" + sourcepath);
								info += "节目包的xml文件已经修改；\r\n";
							}
						} else {
							cmsLog.debug("更新节目包的xml文件失败。" + sourcepath);
							info += "更新节目包的xml文件失败；\r\n";
						}
					} else {
						cmsLog.debug("节目包的xml文件不存在，不修改xml文件。" + sourcepath);
						info += "节目包的xml文件不存在，不修改xml文件；\r\n";
					}
				} else {
					cmsLog.debug("节目包的xml文件路径不存在，不修改xml文件。");
					info += "节目包的xml文件路径不存在，不修改xml文件；\r\n";
				}
			} else {
				cmsLog.debug("节目包的xml文件记录不存在，不修改xml文件。");
				info += "节目包的xml文件记录不存在，不修改xml文件；\r\n";
			}
		} else {
			info = "没有修改请求，不修改节目包。";
		}
		cmsResultDto.setErrorDetail(info);

		cmsLog.debug("Cms -> CmsTransaction -> updateProgPackageWithoutPrograminfoCmsservice returns.");
		return cmsResultDto;
	}

	// 20100304 10:37
	// 修改节目包的海报
	private CmsResultDto updateProgPackagePngs(
			IProgPackageManager progPackageManager,
			IPackageFilesManager packageFilesManager,
			IProgramFilesManager programFilesManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			ProgPackage progPackage, // 节目包信息
			List programFileses, // 海报列表，包含了修改和未修改的海报信息
			List fileFroms, // 海报源路径列表，在服务器上
			List storageclasses, // 海报目标存储体等级code列表
			Date date // 更新日期
	) {
		// 1、修改原海报名字，
		// 2、上传新海报，以原文件ID命名
		// 3、删除原海报。
		// 4、如果2报错，恢复原海报名字，如果恢复出错，则复制原海报。
		// 5、修改数据信息（事务处理）
		// 修改文件位置表FILEDATE,
		// 修改节目包表，更新XML字段和更新日期字段。

		cmsLog.debug("Cms -> CmsTransaction -> updateProgPackagePngs...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		String info = "";
		int totalret = -1;

		if (programFileses == null || fileFroms == null
				|| storageclasses == null) {
			String str = "输入参数为空，不操作。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		} else {
			try {
				cmsLog.debug("准备为节目包上传或更新附件文件...");
				cmsLog.debug("节目包ID：" + progPackage.getProductid());
				cmsLog.debug("节目包名称：" + progPackage.getProductname());

				for (int i = 0; i < fileFroms.size(); i++) {
					ProgramFiles progf = (ProgramFiles) programFileses.get(i);
					if (cmsLog.isDebugEnabled() && null != progf) {
						cmsLog.debug("------- ProgramFile Info start -------");
						cmsLog.debug("\tProgramFilesID: " + progf.getProgfileid());
						cmsLog.debug("\tProgramFilesFileName: " + progf.getFilename());
						cmsLog.debug("\tProgramFilesFileSize: " + progf.getContentfilesize());
						cmsLog.debug("\tProgramFilesFileCode: " + progf.getFilecode());
						cmsLog.debug("\tProgramFilesFileType: " + progf.getFiletypeid());
						cmsLog.debug("------- ProgramFile Info   end -------");
					}
					String filefrom = (String) fileFroms.get(i);
					String stclasscode = (String) storageclasses.get(i);

					if (progf == null || filefrom == null
							|| stclasscode == null
							|| filefrom.equalsIgnoreCase("")
							|| stclasscode.equalsIgnoreCase("")) {
						cmsLog.debug("附件" + (i + 1) + "不修改。");
						info += "附件";
						info += (i + 1);
						info += "不修改；\r\n";
					} else {
						// 查询海报文件记录
						cmsLog.debug("查询附件文件记录...");
						ProgramFiles oldprogf = null;
						if (null != progf.getProgfileid() &&
								30 != progf.getProgfileid().trim().length()) {
							List<ProgramFiles> list = packageFilesManager.getProgramFilesesByProductidFilecodeFiletypeid(
									progPackage.getProductid(), progf.getFilecode(), 
									progf.getFiletypeid());
							cmsLog.debug("附件[ " + progf.getFiletypeid() + " ]个数为: " + list.size());
							if (0 < list.size()) {
								oldprogf = list.get(0);
							}
						} else {
							oldprogf = (ProgramFiles) programFilesManager.getById(
									progf.getProgfileid());
							cmsLog.debug("根据文件ID [ " + progf.getProgfileid() + " ] 查询到文件记录: " + oldprogf);
						}
						if (oldprogf != null) {
							// 1、修改原海报名字，
							// 查询原来海报
							// 返回：List
							// 1 - String
							// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
							// 2 - List<Object[]>
							// (AmsStorage)Object[0]
							// (AmsStoragePrgRel)Object[1]
							// (AmsStorageDir)Object[2]
							// (AmsStorageClass)Object[3]
							cmsLog.debug("原附件文件记录已经存在，更新附件...");
							List list = packageFilesManager
									.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
											oldprogf.getProgfileid(), stclasscode,
											progPackage.getProductid());
							if (list != null && list.size() >= 2) {
								int ret = -1;
								AmsStorage amsst = null;
								AmsStoragePrgRel amsstpr = null;
								AmsStorageDir amsstd = null;
								AmsStorageClass amsstc = null;
								String sourcepath = (String) list.get(0);
								List list1 = (List) list.get(1);
								if (list1 != null && list1.size() > 0) {
									Object[] rows = (Object[]) list1.get(0);
									amsst = (AmsStorage) rows[0];
									amsstpr = (AmsStoragePrgRel) rows[1];
									amsstd = (AmsStorageDir) rows[2];
									amsstc = (AmsStorageClass) rows[3];
								}
								if (fileoper.checkSmbFileExist(sourcepath)) {
									cmsLog.debug("原附件文件存在，" + sourcepath);
									cmsLog.debug("准备重命名原来附件文件...");
									String newName = amsstpr.getFilename()
											+ pngAdditional;
									ret = fileoper.renameSmbFile(sourcepath,
											newName);

									if (ret == 0) {
										// 2、上传新海报，以原文件ID命名
										cmsLog.debug("重命名原附件文件成功，准备复制新附件文件...");
										ret = fileoper.copyFileFromLocalToSmb(
												filefrom, sourcepath);
										if (ret == 0) {
											// 3、删除原海报。
											cmsLog
													.debug("复制附件文件成功，准备删除原附件文件...");
											ret = fileoper
													.deleteSmbFile(sourcepath
															+ pngAdditional);
											amsstpr.setFiledate(date);
											amsstorageprgrelManager
													.update(amsstpr);
											cmsLog.debug("附件文件的filedate已经更新。");
											cmsLog.debug("上传新附件成功。");

											info += "上传附件";
											info += (i + 1);
											info += "成功；\r\n";
										} else {
											// 4、如果2报错，恢复原海报名字，如果恢复出错，则复制原海报。
											cmsLog
													.debug("复制附件文件失败，准备重命名原附件为原来文件名...");
											ret = fileoper.renameSmbFile(
													sourcepath + pngAdditional,
													amsstpr.getFilename());
											if (ret == 0) {
												cmsLog.debug("重命名成功，上传新附件失败。");
												info += "上传附件";
												info += (i + 1);
												info += "失败；\r\n";
											} else {
												cmsLog
														.debug("重命名失败，准备复制原附件文件...");
												fileoper
														.deleteSmbFile(sourcepath);
												ret = fileoper
														.copyFileFromSmbToSmb(
																sourcepath
																		+ pngAdditional,
																sourcepath);
												if (ret == 0) {
													cmsLog
															.debug("复制原附件文件成功，准备删除重命名后的原附件文件...");
													ret = fileoper
															.deleteSmbFile(sourcepath
																	+ pngAdditional);
													cmsLog.debug("上传新附件失败。");
													info += "上传附件";
													info += (i + 1);
													info += "失败；\r\n";
												} else {
													cmsLog
															.warn("复制原附件文件失败，上传新附件失败。");
													info += "上传海报";
													info += (i + 1);
													info += "失败；\r\n";
												}
											}
										}
									} else {
										cmsLog.warn("重命名原附件文件失败。");
										info += "重命名原附件";
										info += (i + 1);
										info += "失败；\r\n";
									}
								} else {
									cmsLog.warn("原附件文件不存在，上传新附件。文件ID："
											+ oldprogf.getProgfileid());
									ret = fileoper.copyFileFromLocalToSmb(
											filefrom, sourcepath);
									if (ret == 0) {
										amsstpr.setFiledate(date);
										amsstorageprgrelManager
												.update(amsstpr);
										cmsLog.debug("附件文件的filedate已经更新。");
										cmsLog.debug("上传新附件成功。");

										info += "上传附件";
										info += (i + 1);
										info += "成功；\r\n";
									}
								}
							} else {
								cmsLog.warn("原海报源存储路径不存在，不上传新附件。文件ID："
										+ progf.getProgfileid());
								info += "附件";
								info += (i + 1);
								info += "源存储路径不存在，不上传新附件；\r\n";
							}
						} else {
							cmsLog.debug("原附件文件记录不存在，上传新附件...");

							CmsResultDto c2 = savePngOfProgPackage(
									progPackageManager, packageFilesManager,
									programFilesManager,
									amsstorageprgrelManager, progPackage,
									progf, filefrom, stclasscode);
							if (c2.getResultCode() == (long) 0) {
								info += "附件";
								info += (i + 1);
								info += "记录不存在，上传新附件成功；\r\n";
								cmsLog.info("附件" + (i + 1) + "记录不存在，上传新附件成功。");
							} else {
								info += "附件";
								info += (i + 1);
								info += "记录不存在，上传新附件失败，" + c2.getErrorMessage()
										+ "；\r\n";
								cmsLog.info("附件" + (i + 1)
										+ "记录不存在，上传附件报失败。ErrorMessage："
										+ c2.getErrorMessage());
							}
						}
					}
				}
				cmsResultDto.setErrorDetail(info);
			} catch (Exception e) {
				String str = "Cms -> CmsTransaction -> updateProgPackagePngs - 异常："
						+ e.getMessage();
				cmsLog.error(str);
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				throw new RuntimeException(str);
			}
		}

		cmsLog.debug("Cms -> CmsTransaction -> updateProgPackagePngs returns.");
		return cmsResultDto;
	}

	// 转移文件，上传海报
	public CmsResultDto savePngOfProgPackage(
			IProgPackageManager progPackageManager,
			IPackageFilesManager packageFilesManager,
			IProgramFilesManager programFilesManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			ProgPackage progPackage, ProgramFiles programFiles,
			String strFileFrom, String storageclass) {
		cmsLog.debug("Cms -> CmsTransaction -> savePngOfProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		int iCommit = 0;

		// try
		// {
		cmsLog.debug("准备为节目包上传海报文件...");
		cmsLog.debug("节目包ID：" + progPackage.getProductid());
		cmsLog.debug("节目包名称：" + progPackage.getProductname());
		cmsLog.debug("海报源路径：" + strFileFrom);
		
//		File pngFile = new File(strFileFrom);
//		if (100 * 1024 < pngFile.length()) {
//			cmsResultDto.setErrorCode(1L);
//			cmsResultDto.setErrorMessage(" 海报文件过大, 请更换海报重新上传! ");
//			return cmsResultDto;
//		}

		if (strFileFrom.charAt(strFileFrom.length() - 1) == '/'
				|| strFileFrom.charAt(strFileFrom.length() - 1) == '\\') {
			String str = "输入的海报源路径格式有误，返回失败。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		} else {
			// 调用方法2，得到xml目前存储路径
			// 返回：List
			// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
			// 2 - List<Object[]>
			// (AmsStorage)Object[0]
			// (AmsStorageDir)Object[1]
			// (AmsStorageClass)Object[2]
			String strFileFullPath = ""; // 海报目标存放全路径，含文件名和后缀
			String strFilePath = ""; // 海报目标存放路径
			String strFileName = ""; // 海报名字
			String filepath = "";
			List destpaths = packageFilesManager
					.getDestPathByFilecodeStclasscode(programFiles
							.getFilecode(), storageclass);
			if (destpaths == null || destpaths.size() < 2) {
				String str = "获取节目包海报文件目标存放路径失败。";
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
				// return cmsResultDto;
			} else {
				strFilePath = (String) destpaths.get(0);
				List rowlist = (List) destpaths.get(1);
				Object[] rows = (Object[]) rowlist.get(0);
				AmsStorage amsst = (AmsStorage) rows[0];
				AmsStorageDir amsstdir = (AmsStorageDir) rows[1];
				AmsStorageClass amsstc = (AmsStorageClass) rows[2];
				filepath = progPackage.getProductid().substring(0, 10);

				// 写数据库，到文件表、AMS节目位置表、节目包文件表
				String strMaxPk = programFilesManager
						.findmaxprogramfileidbyprogramidandfiletype(progPackage
								.getProductid(), "010");
				programFiles.setProgfileid(strMaxPk);
				programFiles.setInputtime(new Date());
				programFiles.setProgramid(progPackage.getProductid());
				AmsStoragePrgRel amsStoragePrgRel = saveUploadFile(
						programFilesManager, amsstorageprgrelManager,
						packageFilesManager, programFiles, progPackage, amsst,
						amsstdir, filepath, true);
				if (amsStoragePrgRel == null) {
					// 写数据库 失败
					String str = "写数据库，到文件表、AMS节目位置表、节目包文件表失败。";
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.warn(str);
					// return cmsResultDto;
				} else {
					// 写数据库 成功
					cmsLog.debug("写文件位置表成功。文件位置ID："
							+ amsStoragePrgRel.getRelid());

					// 获得文件名，含后缀
					String fileType = ""; // 后缀
					String[] sFile = strFileFrom.split("\\.");
					if (sFile.length >= 2) {
						fileType = "." + sFile[sFile.length - 1];
						cmsLog.debug("得到文件后缀：" + fileType);
					} else {
						fileType = "";
						cmsLog.debug("得到文件后缀为空。");
					}
					if (programFiles.getProgfileid() == null
							|| programFiles.getProgfileid()
									.equalsIgnoreCase("")) {
						String str = "获取文件名为空，上传海报失败。";
						cmsResultDto.setResultCode((long) 1);
						cmsResultDto.setErrorMessage(str);
						cmsLog.warn(str);
						// return cmsResultDto;
					} else {
						strFileName = programFiles.getProgfileid() + fileType;

						// 北京修改，20100204 14:03
						// xml的源文件缺少filepath，但是文件位置表不缺少
						strFilePath = strFilePath.replace('\\', '/');
						strFilePath = fileoper.checkPathFormatRear(strFilePath,
								'/');
						strFilePath += filepath;
						strFilePath = strFilePath.replace('\\', '/');
						strFilePath = fileoper.checkPathFormatRear(strFilePath,
								'/');
						// 修改完毕

						strFileFullPath = strFilePath + strFileName;

						cmsLog.debug("准备复制海报文件从服务器本地到缓存库...");
						cmsLog.debug("源 - " + strFileFrom);
						cmsLog.debug("目标 - " + strFileFullPath);
						int copyret = fileoper.copyFileFromLocalToSmb(
								strFileFrom, strFileFullPath); // 上传文件
						if (copyret == 0) {
							cmsLog.debug("复制文件成功，准备删除源文件...");

							if (deleteFile(strFileFrom) == 0) {
								cmsLog.debug("删除文件成功。");
							} else {
								String str = "删除文件失败。";
								cmsLog.warn(str);
							}

							cmsLog.debug("迁移文件完毕，准备更新节目包的xml字段(ppxml)...");

							progPackage = updateProgPackagePpxml(
									packageFilesManager, programFilesManager,
									progPackage);
							progPackageManager.update(progPackage);
							cmsLog.debug("已经更新数据库中节目包的xml字段(ppxml)。");

							// ProgPackage pp =
							// (ProgPackage)progPackageManager.getById(progPackage.getProductid());
							// cmsLog.debug("查询节目包：" +
							// progPackage.getProductid());
							// pp = updateProgPackagePpxml(
							// packageFilesManager,
							// programFilesManager,
							// pp
							// );
							// progPackageManager.update(pp);
							// cmsLog.debug("节目包信息已经修改。");
						} else {
							cmsLog.warn("迁移文件失败。");

							// 删除数据库记录
							cmsLog.warn("！！缺少代码：失败时，删除原有数据库操作。");

							String str = "迁移文件失败，回滚数据库操作。";
							cmsResultDto.setResultCode((long) 1);
							cmsResultDto.setErrorMessage(str);
							cmsLog.warn(str);
							throw new RuntimeException(str);
							// return cmsResultDto;
						}
					}
				}
			}
		}
		// }
		// catch(Exception ex)
		// {
		// // String str = "上传文件失败。详细信息：" + ex.getMessage();
		// String str = "Cms -> CmsTransaction -> savePngOfProgPackage - 异常：" +
		// ex.getMessage();
		// cmsResultDto.setResultCode((long)1);
		// cmsResultDto.setErrorMessage(str);
		// cmsResultDto.setErrorDetail(ex.getMessage());
		// cmsLog.error(str);
		// cmsLog.warn("回滚数据库操作。");
		// throw new RuntimeException(str);
		//			
		// // return cmsResultDto;
		// }

		cmsLog.debug("Cms -> CmsTransaction -> savePngOfProgPackage returns.");
		return cmsResultDto;
	}

	// 20100304 10:55
	// 修改节目包的xml
	private ProgPackage updateProgPackagePpxml(
			IPackageFilesManager packageFilesManager,
			IProgramFilesManager programFilesManager, ProgPackage progPackage) {
		// 删除文件
		cmsLog.debug("Cms -> CmsTransaction -> updateProgPackagePpxml...");

		List packageFileses = packageFilesManager.findByProperty("productid",
				progPackage.getProductid());
		String xml = progPackage.getPpxml();

		try {
			// 读配置文件
			String sourceFileStclasscode = "NearOnline";

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// 将String转成parse可以识别的InputSource
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			Document doc = builder.parse(is);
			doc.normalize();

			// 查找cell节点
			cmsLog.debug("修改节目包属性...");
			NodeList cells = doc.getElementsByTagName("APP");
			for (int i = 0; i < cells.getLength(); i++) {
				Node cell = cells.item(i);
				Element cellattr = (Element) cells.item(i);
				if (cellattr.hasAttribute("PROGPACKAGEID")) // 判断节点有tag属性
				{
					// cmsLog.debug(cell.getAttributes().getNamedItem("PROGPACKAGEID").getNodeValue());
					cell.getAttributes().getNamedItem("PROGPACKAGEID")
							.setNodeValue(progPackage.getProductid());
				}
				if (cellattr.hasAttribute("PROGPACKAGENAME")) // 判断节点有tag属性
				{
					// cmsLog.debug(cell.getAttributes().getNamedItem("PROGPACKAGENAME").getNodeValue());
					cell.getAttributes().getNamedItem("PROGPACKAGENAME")
							.setNodeValue(progPackage.getProductname());
				}
				if (cellattr.hasAttribute("PROGTYPE")) // 判断节点有tag属性
				{
					// cmsLog.debug(cell.getAttributes().getNamedItem("PROGTYPE").getNodeValue());
					cell.getAttributes().getNamedItem("PROGTYPE").setNodeValue(
							progPackage.getProgtype());
				}
				if (cellattr.hasAttribute("STYLEID")) // 判断节点有tag属性
				{
					// cmsLog.debug(cell.getAttributes().getNamedItem("STYLEID").getNodeValue());
					cell.getAttributes().getNamedItem("STYLEID").setNodeValue(
							progPackage.getStyleid().toString());
				}
				if (cellattr.hasAttribute("SUMFILESIZE")) // 判断节点有tag属性
				{
					// cmsLog.debug(cell.getAttributes().getNamedItem("SUMFILESIZE").getNodeValue());
					cell.getAttributes().getNamedItem("SUMFILESIZE")
							.setNodeValue(progPackage.getSumfilesize());
				}
				if (cellattr.hasAttribute("UPDATEMANID")) // 判断节点有tag属性
				{
					// cmsLog.debug(cell.getAttributes().getNamedItem("UPDATEMANID").getNodeValue());
					cell.getAttributes().getNamedItem("UPDATEMANID")
							.setNodeValue(progPackage.getUpdatemanid());
				}
				if (cellattr.hasAttribute("UPDATETIME")) // 判断节点有tag属性
				{
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					// cmsLog.debug(cell.getAttributes().getNamedItem("UPDATETIME").getNodeValue());
					cell.getAttributes().getNamedItem("UPDATETIME")
							.setNodeValue(null == progPackage.getUpdatetime()
									? "" : sdf.format(progPackage.getUpdatetime()));
				}
				if (cellattr.hasAttribute("EPICODENUMBER")) // 判断节点有tag属性
				{
					// cmsLog.debug(cell.getAttributes().getNamedItem("EPICODENUMBER").getNodeValue());
					cell.getAttributes().getNamedItem("EPICODENUMBER")
							.setNodeValue(progPackage.getEpicodenumber());
				}
				cellattr.setAttribute("PROGPACKAGEID", progPackage
						.getProductid());
				cellattr.setAttribute("PROGPACKAGENAME", progPackage
						.getProductname());
				cellattr.setAttribute("PROGTYPE", progPackage.getProgtype());
				cellattr.setAttribute("STYLEID", progPackage.getStyleid()
						.toString());
				cellattr.setAttribute("SUMFILESIZE", progPackage
						.getSumfilesize());
				cellattr.setAttribute("UPDATEMANID", progPackage
						.getUpdatemanid());
				cellattr.setAttribute("UPDATETIME", null == progPackage.getUpdatetime()
						? "" : fileoper.convertDateToString(
								progPackage.getUpdatetime(), "yyyy-MM-dd HH:mm:ss"));
				cellattr.setAttribute("EPICODENUMBER", progPackage
						.getEpicodenumber());

				cellattr
						.setAttribute("PTGLOBALID", progPackage.getPtglobalid());
				cellattr.setAttribute("PRODUCTNAME", progPackage
						.getProductname());
				cellattr.setAttribute("DESCRIPTION", progPackage
						.getDescription());
				cellattr.setAttribute("CATEGORY", progPackage.getCategory());
				cellattr
						.setAttribute("TITLEBRIEF", progPackage.getTitlebrief());
				cellattr.setAttribute("EPICODEID", progPackage.getEpicodeid());
				cellattr.setAttribute("LENGTHTC", progPackage.getLengthtc());
				cellattr.setAttribute("EPICODENAME", progPackage
						.getEpicodename());
				cellattr.setAttribute("SUBTITLELANGUAGE", progPackage
						.getSubtitlelanguage());
				cellattr.setAttribute("AUDIOLANGUAGE", progPackage
						.getAudiolanguage());
				cellattr.setAttribute("DIRECTOR", progPackage.getDirector());
				cellattr.setAttribute("ACTORS", progPackage.getActors());
				cellattr.setAttribute("SHOOTDATE", progPackage.getShootdate());
				cellattr.setAttribute("ISSUEDATE", progPackage.getIssuedate());
				cellattr.setAttribute("COUNTRYDIST", progPackage.getCountrydist());
				cellattr.setAttribute("SUBSCRIBERSTIME", null == progPackage.getSubscriberstime()
						? "" : fileoper.convertDateToString(
								progPackage.getSubscriberstime(), "yyyy-MM-dd HH:mm:ss"));
				cellattr.setAttribute("SUBSCRIBERETIME", null == progPackage.getSubscriberetime()
						? "" : fileoper.convertDateToString(
								progPackage.getSubscriberetime(), "yyyy-MM-dd HH:mm:ss"));
				cellattr
						.setAttribute("INPUTMANID", progPackage.getInputmanid());
				cellattr.setAttribute("INPUTTIME", null == progPackage.getInputtime()
						? "" : fileoper.convertDateToString(
								progPackage.getInputtime(), "yyyy-MM-dd HH:mm:ss"));
				cellattr.setAttribute("FILESIZEHI", progPackage.getFilesizehi()
						.toString());
				cellattr.setAttribute("FILESIZELOW", progPackage
						.getFilesizelow().toString());
				cellattr.setAttribute("SUMFILESIZE", progPackage
						.getSumfilesize());
				cellattr.setAttribute("REMARK", progPackage.getRemark());
				cellattr.setAttribute("STATE", progPackage.getState()
						.toString());
				cellattr.setAttribute("DEALSTATE", progPackage.getDealstate()
						.toString());
			}

			cmsLog.debug("修改节目包的文件信息...");
			cells = doc.getElementsByTagName("PROGFILE");
			for (int i = 0; i < cells.getLength(); i++) {
				Node cell = cells.item(i);
				Element cellattr = (Element) cells.item(i);

				// 先删除原有节点
				cmsLog.debug("为节目包删除原有文件节点...");
				if (cell.hasChildNodes()) {
					NodeList oleNodes = cell.getChildNodes();
					for (int j = 0; j < oleNodes.getLength(); j++) {
						Node oldNode = oleNodes.item(j);

						if (oldNode.getNodeName().equalsIgnoreCase("FILE")) {
							cell.removeChild(oldNode);
						}
					}
				}
				cmsLog.debug("为节目包添加文件节点...");
				for (int j = 0; j < packageFileses.size(); j++) {
					PackageFiles pf = (PackageFiles) packageFileses.get(j);
					ProgramFiles programFiles = (ProgramFiles) programFilesManager
							.getById(pf.getProgfileid());

					// 创建一个值
					Element newe = doc.createElement("FILE");

					newe.setAttribute("PROGFILEID", pf.getProgfileid());
					newe.setAttribute("FILETYPEID", programFiles
							.getFiletypeid());
					newe.setAttribute("FILECODE", programFiles.getFilecode());
					newe.setAttribute("FILENAME", programFiles.getFilename());
					newe.setAttribute("SUBTITLELANGUAGE", programFiles
							.getSubtitlelanguage());
					newe.setAttribute("DUBBEDLANGUAGE", programFiles
							.getDubbedlanguage());
					/**
					 * HuangBo update by 2011年4月13日 14时28分
					 * 增加文件属性的ContentID属性
					 */
					newe.setAttribute("CONTENTID", programFiles
							.getContentId());
					newe.setAttribute("FILESIZEHI", programFiles
							.getFilesizehi().toString());
					newe.setAttribute("FILESIZELOW", programFiles
							.getFilesizelow().toString());
					newe.setAttribute("CONTENTFILESIZE", programFiles
							.getContentfilesize());
					newe.setAttribute("CONTENTCHECKSUM", programFiles
							.getContentchecksum());

					if (programFiles.getProgrank() != null)
						newe.setAttribute("PROGRANK", programFiles
								.getProgrank().toString());
					else
						newe.setAttribute("PROGRANK", "0");

					newe.setAttribute("INPUTMANID", programFiles
							.getInputmanid());
					newe.setAttribute("INPUTTIME", null == programFiles.getInputtime()
							? "" : fileoper.convertDateToString(
									programFiles.getInputtime(), "yyyy-MM-dd HH:mm:ss"));
					newe.setAttribute("ISFLAG", "Y");

					// 从文件位置表获取
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (ProgramFiles)Object[0]
					// (AmsStorage)Object[1]
					// (AmsStoragePrgRel)Object[2]
					// (AmsStorageDir)Object[3]
					// (AmsStorageClass)Object[3]
					cmsLog.debug("查询文件的filedate...");
					String filedate = "";
					List sourcePaths = packageFilesManager
							.getSourcePathByProgfileidStclasscode(programFiles
									.getProgfileid(), sourceFileStclasscode);
					if (sourcePaths != null && sourcePaths.size() >= 2) {
						List list = (List) sourcePaths.get(1);
						if (list.size() > 0) {
							Object[] rows = (Object[]) list.get(0);
							AmsStoragePrgRel amsstpr = (AmsStoragePrgRel) rows[2];

							if (amsstpr.getFiledate() != null) {
								filedate = fileoper.convertDateToString(amsstpr
										.getFiledate(), "yyyy-MM-dd HH:mm:ss");
							}
						}
					}
					newe.setAttribute("FILEDATE", filedate);

					// 添加在cell后
					cell.appendChild(newe);
					cmsLog.debug("新的文件节点已经添加。");
				}
			}
			String ppxml = fileoper.XMLtoStr(doc);
			if (!ppxml.equalsIgnoreCase("")) {
				progPackage.setPpxml(ppxml);
				cmsLog.debug("已经更新节目包的xml，尚未保存到数据库。");
			}
		} catch (Exception ex) {
			cmsLog.error("Cms -> CmsTransaction -> updateProgPackagePpxml - 异常："
							+ ex.getMessage());
		}

		cmsLog.debug("Cms -> CmsTransaction -> updateProgPackagePpxml returns.");
		return progPackage;
	}

	// 20100322 13:37
	// 保存节目信息和节目文件信息
	public CmsResultDto saveProgramInfoProgramFiles(
			IProgramInfoManager programinfoManager,
			IProgramFilesManager programFilesManager, ProgramInfo programinfo,
			ProgramFiles programfiles) {
		// 返回：
		// List list
		// (ProgramInfo)list.get(0);
		// (ProgramFiles)list.get(1);

		cmsLog.debug("Cms -> CmsTransaction -> saveProgramInfoProgramFiles...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		String strMaxPK = "";

		if (programfiles.getInputtime() == null)
			programfiles.setInputtime(new Date());
		programinfoManager.save(programinfo);
		cmsLog.debug("节目信息表已经保存，节目ID：" + programinfo.getProgramid());

		programfiles.setProgramid(programinfo.getProgramid());
		programFilesManager.save(programfiles);
		cmsLog.debug("节目文件表已经保存，节目文件ID：" + programfiles.getProgfileid());

		// 修改节目文件的filename
		if (programfiles.getFilename() != null) {
			programfiles.setFilename(getNewFileName(programfiles.getFilename(),
					programfiles.getProgfileid()));
		} else {
			cmsLog.warn("输入文件名为null。");
			programfiles.setFilename(programfiles.getProgfileid());
		}
		programFilesManager.update(programfiles);
		cmsLog.debug("节目文件表filename已经更新，节目文件filename："
				+ programfiles.getFilename());

		List list = new ArrayList();
		list.add(programinfo);
		list.add(programfiles);
		cmsResultDto.setResultObject(list);

		cmsLog.debug("Cms -> CmsTransaction -> saveProgramInfoProgramFiles returns.");
		return cmsResultDto;
	}

	// ------------------------------------- ProgListDetail
	// ---------------------------------------------------
	// 保存（创建）流程持久化 Bpmc
	private void saveBpmc(IBpmcManager bpmcManager, Bpmc bpmc) {
		cmsLog.debug("Cms -> CmsTransaction -> saveBpmc...");

		bpmc.setSenddate(new Date());
		bpmcManager.save(bpmc);

		cmsLog.debug("Cms -> CmsTransaction -> saveBpmc returns.");
	}

	// 不使用
	// 保存（新建）栏目单 FU00000077 编单定义
	public ProgList saveProgList(IProgListManager progListManager,
			IBpmcManager bpmcManager, ProgList progList) {
		cmsLog.debug("Cms -> CmsTransaction -> saveProgList...");

		// 保存（新建）栏目单
		progList.setCreateDate(new Date());
		// progList.setIdAct("FU00000077");
		progListManager.save(progList);

		// 保存（创建）流程持久化
		Bpmc bpmc = new Bpmc();
		bpmc.setCreater(progList.getPop());
		bpmc.setObjectid(progList.getPdate());
		bpmc.setCreatedate(new Date());
		bpmc.setSender(progList.getPop());
		bpmc.setSends("FU00000077");
		bpmc.setReceiver(progList.getPop());
		bpmc.setReceives("FU00000077");
		// bpmc.setReceivedate(new Date());
		// bpmc.setSendremark(sendremark);
		bpmc.setState("N");
		saveBpmc(bpmcManager, bpmc);

		cmsLog.debug("Cms -> CmsTransaction -> saveProgList...");
		return progList;
	}

	// 不使用
	// 添加栏目单详细 ProgListDetail
	public void saveProgListDetailsToProgList(IProgListManager progListManager,
			IProgListDetailManager progListDetailManager,
			IPortalColumnManager portalColumnManager, List progPackages,
			String pdate, String columnclassid, List lnums) {
		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgListDetailsToProgList...");

		cmsLog.info("代码空。");

		// if(progPackages.size() > 0)
		// {
		// // 判断栏目单的合法性
		// ProgList progList = (ProgList)progListManager.getById(pdate);
		// if(progList == null)
		// {
		// cmsLog.info("pdate invalid.");
		// return;
		// }
		//				
		// // 判断栏目的合法性
		// PortalColumn portalColumn =
		// (PortalColumn)portalColumnManager.getById(columnclassid.toString());
		// if(portalColumn == null)
		// {
		// cmsLog.info("columnclassid invalid.");
		// return;
		// }
		// else if(!"Y".equalsIgnoreCase(portalColumn.getIsleaf()))
		// {
		// cmsLog.info("The portalColumn is not a leaf.");
		// return;
		// }
		//
		// for(int i = 0; i < progPackages.size(); i++)
		// {
		// ProgListDetail progListDetail = new ProgListDetail();
		// ProgPackage progPackage = (ProgPackage)progPackages.get(i);
		// Long lnum = Long.valueOf(lnums.get(i).toString());
		//				
		// //???????????????????????????????????????????????????????????????????????????????????
		// //???????????????????????????????????????????????????????????????????????????????????
		// //???????????????????????????????????????????????????????????????????????????????????
		// //???????????????????????????????????????????????????????????????????????????????????
		//				
		// // 根据栏目单编号和节目包编号，查询栏目单详细是否已经存在
		// // 如果存在，不操作
		// // 如果不存在，添加到栏目单详细
		// List progListDetails =
		// progListDetailManager.getProgListDetailsByPdateAndProductidAndColumnclassid(
		// pdate,
		// progPackage.getProductid(),
		// columnclassid
		// );
		// if(progListDetails.size() <= 0)
		// {
		// progListDetail.setProductid(progPackage.getProductid());
		// progListDetail.setLnum(lnum);
		// progListDetail.setPdate(pdate);
		// progListDetail.setColumnclassid(columnclassid);
		// progListDetail.setValidFrom(progList.getValidFrom());
		// progListDetail.setValidTo(progList.getValidTo());
		// progListDetail.setLengthPlay(progPackage.getLengthtc());
		// progListDetail.setProductname(progPackage.getProductname());
		// progListDetail.setFilesizehi(progPackage.getFilesizehi());
		// progListDetail.setFilesizelow(progPackage.getFilesizelow());
		// progListDetail.setSumfilesize(progPackage.getSumfilesize());
		// // progListDetail.setState1(state1);?????????????????????????????????
		// progListDetail.setDealstate((long)0);
		// progListDetail.setOverlapflag((long)0);
		// // progListDetail.setUploaduser();???????????????????????????????????
		// // progListDetail.setUploaddate(new Date());
		// // progListDetail.setDealdate(dealdate);
		//					
		// progListDetailManager.save(progListDetail);
		// }
		// }
		// }

		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgListDetailsToProgList returns.");
	}

	// 不使用
	// 删除栏目单详细 ProgListDetail
	public void deleteProgListDetailsFromProgList(
			IProgListDetailManager progListDetailManager, List progListDetails) {
		cmsLog
				.debug("Cms -> CmsTransaction -> deleteProgListDetailsFromProgList...");

		if (progListDetails.size() > 0) {
			ProgListDetail[] progListDetailObjects = (ProgListDetail[]) progListDetails
					.toArray(new ProgListDetail[progListDetails.size()]);
			progListDetailManager.delete(progListDetailObjects);
		}

		cmsLog
				.debug("Cms -> CmsTransaction -> deleteProgListDetailsFromProgList returns.");
	}

	// 发送栏目单到下一活动idProcess，下一活动人，

	// 编单 发送
	public void saveSendProgListDetails(
			IProgListDetailManager progListDetailManager,
			IBpmcManager bpmcManager, List<ProgListDetail> progListDetails,
			String nextIdAct, String nextOperator, String nextState2,
			String operator, String sendremark) {
		cmsLog.debug("Cms -> CmsTransaction -> saveSendProgList...");

		if (progListDetails == null) {
			cmsLog.info("progListDetails == null");
			return;
		}

		for (int i = 0; i < progListDetails.size(); i++) {
			ProgListDetail progListDetail = (ProgListDetail) progListDetails
					.get(i);
			progListDetail.setPop(nextOperator);
			progListDetail.setIdAct(nextIdAct);
			progListDetailManager.update(progListDetail);
			cmsLog.info("已保存栏目单详细。");

			Bpmc bpmc = new Bpmc();
			bpmc.setObjectid(progListDetail.getProglistdetailid());
			bpmc.setSender(operator);
			bpmc.setSends(progListDetail.getIdAct());
			bpmc.setReceiver(nextOperator);
			bpmc.setReceives(nextIdAct);
			bpmc.setSendremark(sendremark);
			bpmc.setState(nextState2);
			saveBpmc(bpmcManager, bpmc);
			cmsLog.info("已保存持久化。");
		}

		// // 修改progList的当前活动
		// progList = (ProgList)progListManager.getById(progList.getPdate());
		// progList.setPop(nextOperator);
		// progList.setIdAct(nextIdAct);
		// progListManager.update(progList);

		// // 保存（创建）流程持久化
		// Bpmc bpmc = new Bpmc();
		// // bpmc.setCreater(operator);
		// bpmc.setObjectid(progList.getPdate());
		// bpmc.setSender(operator);
		// bpmc.setSends(progList.getIdAct());
		// bpmc.setReceiver(nextOperator);
		// bpmc.setReceives(nextIdAct);
		// // bpmc.setReceivedate(receivedate);
		// bpmc.setSendremark(sendremark);
		// bpmc.setState(nextState2);
		// saveBpmc(bpmcManager, bpmc);

		cmsLog.debug("Cms -> CmsTransaction -> saveSendProgList returns.");
	}

	// --------- 栏目编单修改 20091202 -----------
	private String convertDateToString(Date date, String format) {
		// 转换Date 到 Stirng
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> convertDateToString...");
		DateFormat format1 = new SimpleDateFormat(format);
		String str = format1.format(date);

		// String转Date
		// str = "2007-1-18";
		// try {
		// date = format1.parse(str);
		// data = format2.parse(str);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }

		cmsLog.info("Date : " + str);
		cmsLog.debug("Cms -> PortalColumnServiceImpl -> convertDateToString...");
		return str;
	}

	private Date getOnlineDate(String date) {
		// DateFormat format = new SimpleDateFormat("yyyy-mm-dd 00:00:00");
		// String strB = format.format(date);
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");

			Date dB = format.parse(date + " 00:00:00");

			// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd
			// 00:00:00");
			// Date d = formatter.parse(date);
			return dB;
		} catch (Exception ex) {
			cmsLog.info("ParseException : " + ex.getMessage());
			return null;
		}
	}

	private int compareDate(Date from1, Date to1, Date from2, Date to2) {
		if ((from1.compareTo(from2) > 0 && from1.compareTo(to2) <= 0)
				|| (to1.compareTo(from2) >= 0 && to1.compareTo(to2) < 0)
				|| (from2.compareTo(from1) > 0 && from2.compareTo(to1) <= 0)
				|| (to2.compareTo(from1) >= 0 && to2.compareTo(to1) < 0)) {
			// 有重叠
			return 1;
		} else {
			// 无重叠
			return 0;
		}
	}

	// 添加节目包progPackage，到栏目单（详细）ProgListDetail，20091202 21:16
	public CmsResultDto saveProgListDetails(
			IProgListDetailManager progListDetailManager,
			IPortalColumnManager portalColumnManager,
			IProgPackageManager progPackageManager,
			IPackStylePortalColumnManager packStylePortalColumnManager,
			IPackageFilesManager packageFilesManager,
			IProgramFilesManager programFilesManager,
			List<ProgPackage> progPackages, String date, Date offlineDate,
			String columnclassid, List<Long> lnums, String userId) {
		cmsLog.debug("Cms -> CmsTransaction -> saveProgListDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		int errorcount = 0;
		String errordetails = "";

		if (progPackages == null || lnums == null || columnclassid == null) {
			String str = "progPackages == null || lnums == null || columnclassid == null";
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}

		// 查询栏目
		cmsLog.info("栏目ID：" + columnclassid);
		PortalColumn portalColumn = (PortalColumn) portalColumnManager
				.getById(columnclassid);
		if (portalColumn == null) {
			String str = "portalColumn == null";
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		} else {
			cmsLog.info("栏目名称：" + portalColumn.getColumnname());
			cmsLog.info("栏目code：" + portalColumn.getDefcatcode());
		}

		// 修改该栏目下现有的栏目单详细记录的序号
		if (progPackages.size() > 0) {
			cmsLog.info("修改该栏目下现有的栏目单详细记录的序号。");
			List<ProgListDetail> progListDetails = progListDetailManager
					.findByProperty("columnclassid", columnclassid);
			for (int i = 0; i < progListDetails.size(); i++) {
				Long offset = Long.valueOf(progPackages.size() * 10);
				ProgListDetail progListDetail = (ProgListDetail) progListDetails
						.get(i);
				progListDetail.setLnum(progListDetail.getLnum() + offset);
				progListDetailManager.update(progListDetail);
			}

			cmsLog.info("准备保存栏目单详细记录...");
			cmsLog.info("栏目单详细(节目包)的数量：" + progPackages.size());

			for (int i = 0; i < progPackages.size(); i++) {
				Date dateOnline;
				ProgListDetail progListDetail = new ProgListDetail();
				ProgPackage progPackage = (ProgPackage) progPackages.get(i);

				// 生成上线日期
				dateOnline = getOnlineDate(date);

				cmsLog.info("处理第" + (i + 1) + "个节目包...");
				cmsLog.info("节目包ID：" + progPackage.getProductid());
				cmsLog.info("节目包名称：" + progPackage.getProductname());
				cmsLog.info("节目包样式ID：" + progPackage.getStyleid());
				cmsLog.info("上线日期："
						+ fileoper.convertDateToString(dateOnline,
								"yyyy-MM-dd HH:mm:ss"));

				// 判断节目包的样式和栏目是否有关系
				boolean deal = false;
				boolean completeness = false;
				cmsLog.info("判断节目包样式与栏目是否匹配...");
				List pspcs = packStylePortalColumnManager
						.getPackStylePortalColumnsByStyleidDefcatcode(
								progPackage.getStyleid(), portalColumn
										.getDefcatcode());
				if (pspcs != null && pspcs.size() > 0) {
					cmsLog.info("栏目与节目包的样式的关系记录数量为：" + pspcs.size());
					deal = true;
				} else {
					cmsLog.warn("未查询到栏目与节目包的样式的关系记录。");
					deal = false;
				}

				if (deal == true) {
					// ts文件，png，外挂字幕，xml
					cmsLog.info("判断节目包文件的完整性...");
					if (progPackage.getProgtype().equalsIgnoreCase("V")) {
						cmsLog.info("节目包类型是视频类型。");

						boolean existXml = false;
						boolean existTs = false;
						boolean existPng = false;
						boolean existSrt = false;
						int srtcount = 0;
						int srtcountinxml = 0;

						try {
							DocumentBuilderFactory factory = DocumentBuilderFactory
									.newInstance();
							DocumentBuilder builder = factory
									.newDocumentBuilder();
							StringReader sr = new StringReader(progPackage
									.getPpxml());
							InputSource is = new InputSource(sr);
							Document doc = builder.parse(is);
							doc.normalize();

							cmsLog.info("查询节目包节点(WGZMCOUNT)属性...");
							NodeList cells = doc
									.getElementsByTagName("WGZMCOUNT");
							for (int j = 0; j < cells.getLength(); j++) {
								Node cell = cells.item(j);
								String str = cell.getTextContent();

								if (str.equalsIgnoreCase("无")) {
									srtcountinxml = 0;
								} else if (str.equalsIgnoreCase("1个")) {
									srtcountinxml = 1;
								} else if (str.equalsIgnoreCase("2个")) {
									srtcountinxml = 2;
								} else if (str.equalsIgnoreCase("3个")) {
									srtcountinxml = 3;
								} else {
									srtcountinxml = 0;
								}
							}
						} catch (Exception ex) {
							cmsLog.error("读取节目包xml异常：" + ex.getMessage());
							cmsLog.info("继续...");
							srtcountinxml = 0;
						}

						cmsLog.info("查询节目包的所有文件...");
						List pkfs = packageFilesManager.findByProperty(
								"productid", progPackage.getProductid());
						List pfs = new ArrayList();
						cmsLog.info("节目包的文件数量：" + pkfs.size());
						for (int j = 0; j < pkfs.size(); j++) {
							PackageFiles pkf = (PackageFiles) pkfs.get(j);
							ProgramFiles pf = (ProgramFiles) programFilesManager
									.getById(pkf.getProgfileid());
							pfs.add(pf);

							String filecode = pf.getFilecode();
							String filetype = pf.getFiletypeid();

							cmsLog.info("处理第" + (j + 1) + "个文件...");
							cmsLog.info("文件ID：" + pf.getProgfileid());
							cmsLog.info("文件名：" + pf.getFilename());
							cmsLog.info("filecode：" + filecode);
							cmsLog.info("filetype：" + filetype);

							if (filetype.equalsIgnoreCase("H264")) {
								cmsLog.info("视频文件存在。");
								existTs = true;
							} else if (filetype.equalsIgnoreCase("XML")) {
								cmsLog.info("Xml文件存在。");
								existXml = true;
							} else if (filetype.equalsIgnoreCase("PNG")) {
								cmsLog.info("海报文件存在。");
								existPng = true;
							} else if (filetype.equalsIgnoreCase("WGZM")) {
								cmsLog.info("字幕文件存在。");
								srtcount++;
							}
						}

						cmsLog.info("节目包的外挂字幕数量(xml)：" + srtcountinxml);
						cmsLog.info("节目包的外挂字幕数量(实际)：" + srtcount);
						if (srtcount >= srtcountinxml) {
							cmsLog.info("字幕文件完整。");
							existSrt = true;
						} else {
							cmsLog.info("字幕文件不完整。");
							existSrt = false;
						}

						if (existXml == true && existTs == true
								&& existPng == true && existSrt == true) {
							cmsLog.info("节目包文件完整。");
							completeness = true;
						} else {
							cmsLog.info("节目包文件不完整。");
							completeness = false;
						}
					} else {
						cmsLog.warn("节目包类型是富媒体，暂时不处理。");
						completeness = true;
					}
				} else {
					cmsLog.warn("节目包样式与栏目不匹配，不继续判断节目包文件完整性。");
				}

				if (deal == true && completeness == true) {
					cmsLog.info("节目包样式与栏目匹配，且节目包文件完整，准备保存栏目单详细记录...");

					// 判断生成下线日期
					if (portalColumn.getValidTo() == null) {
						cmsLog.info("取节目包的允许使用日期终。");
						offlineDate = progPackage.getSubscriberetime();
					} else if (progPackage.getSubscriberetime() == null) {
						cmsLog.info("取栏目的下线日期。");
						offlineDate = portalColumn.getValidTo();
					}
					if (portalColumn.getValidTo() != null
							&& progPackage.getSubscriberetime() != null) {
						if (portalColumn.getValidTo().compareTo(
								progPackage.getSubscriberetime()) < 0) {
							cmsLog.info("取栏目的下线日期。");
							offlineDate = portalColumn.getValidTo();
						} else {
							cmsLog.info("取节目包的允许使用日期终。");
							offlineDate = progPackage.getSubscriberetime();
						}
					}
					cmsLog.info("下线日期："
							+ fileoper.convertDateToString(offlineDate,
									"yyyy-MM-dd HH:mm:ss"));
					// 比较新的上线下线日期是否合法
					if (dateOnline != null && offlineDate != null) {
						if (0 <= dateOnline.compareTo(offlineDate)) {
							String str = "新的上线不早于新的下线日期，不合法。";
							cmsLog.info(str);
							cmsResultDto.setResultCode(Long.valueOf(1));
							cmsResultDto.setErrorMessage(str);
							throw new RuntimeException(str);
						}
					}

					// 查询该节目包对应的栏目单详细记录，判断新的上线和下线日期是否合法
					progListDetails = progListDetailManager.findByProperty(
							"productid", progPackage.getProductid());
					for (int j = 0; j < progListDetails.size(); j++) {
						ProgListDetail existProgListDetail = (ProgListDetail) progListDetails
								.get(j);
						Date existOnlineDate = existProgListDetail
								.getValidFrom();
						Date existOfflineDate = existProgListDetail
								.getValidTo();

						if (compareDate(existOnlineDate, existOfflineDate,
								dateOnline, offlineDate) != 0) {
							// 新的上线和下线日期不合法
							String date1 = fileoper.convertDateToString(
									existOnlineDate, "yyyy-MM-dd HH:mm:ss");
							String date2 = fileoper.convertDateToString(
									existOfflineDate, "yyyy-MM-dd HH:mm:ss");
							String date3 = fileoper.convertDateToString(
									dateOnline, "yyyy-MM-dd HH:mm:ss");
							String date4 = fileoper.convertDateToString(
									offlineDate, "yyyy-MM-dd HH:mm:ss");

							String str = "新的上线和下线日期与已经存在的上下线日期有重叠，不合法。\r\n";
							str += "已经存在的日期：" + date1 + " - " + date2 + "\r\n";
							str += "新的日期：" + date3 + " - " + date4;
							cmsLog.warn(str);
							cmsResultDto.setResultCode(Long.valueOf(1));
							cmsResultDto.setErrorMessage(str);
							throw new RuntimeException(str);
						}
					}

					// 保存栏目单详细
					progListDetail.setLnum(lnums.get(i));
					progListDetail.setColumnclassid(columnclassid);
					progListDetail.setProductid(progPackage.getProductid());
					progListDetail.setValidFrom(dateOnline);
					progListDetail.setValidTo(offlineDate);
					progListDetail.setValidFlag((long) 0);
					progListDetail.setProductname(progPackage.getProductname());
					progListDetail.setFilesizehi(progPackage.getFilesizehi());
					progListDetail.setFilesizelow(progPackage.getFilesizelow());
					progListDetail.setSumfilesize(progPackage.getSumfilesize());
					progListDetail.setLengthPlay(progPackage.getLengthtc());
					progListDetail.setUpdatetime(progPackage.getUpdatetime());
					progListDetail.setDefcatseq(portalColumn.getDefcatseq());
					// progListDetail.setState1((long)0);
					// progListDetail.setDealstate((long)0);
					progListDetail.setOverlapflag((long) 0);
					progListDetail.setUploaduser((long) 0);
					if (progListDetail.getUploaddate() == null)
						progListDetail.setUploaddate(new Date());
					progListDetail.setIdProcess("");
					progListDetail.setIdAct("FU00000077");
					progListDetail.setPop(userId);

					progListDetailManager.save(progListDetail);
					cmsLog.info("已保存栏目单详细 : "
							+ progListDetail.getProglistdetailid());
				} else {
					if (deal == false) {
						String str;
						str = "节目包 " + progPackage.getProductid();
						str += "(" + progPackage.getProductname();
						str += ")样式与栏目(";
						str += portalColumn.getColumnname();
						str += ")不匹配，不操作。\r\n";
						cmsLog.warn(str);
						errorcount++;
						errordetails += str;
					} else if (completeness == false) {
						String str;
						str = "节目包 " + progPackage.getProductid();
						str += "(" + progPackage.getProductname();
						str += ")的文件不完整，不操作。\r\n";
						cmsLog.warn(str);
						errorcount++;
						errordetails += str;
					} else {
						String str;
						str = "保存栏目单详细 " + progPackage.getProductid();
						str += "(" + progPackage.getProductname();
						str += ")发生未知错误，不操作。\r\n";
						cmsLog.warn(str);
						errorcount++;
						errordetails += str;
					}
				}
			}
		} else {
			String str = "节目包列表为空。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		}
		if (errorcount > 0) {
			String str = "保存栏目单详细发生错误。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
			cmsResultDto.setErrorDetail(errordetails);
		}

		cmsLog.debug("Cms -> CmsTransaction -> saveProgListDetails returns.");
		return cmsResultDto;
	}

	// 删除栏目单详细
	// 删除节目包progListDetail，从栏目单（详细）ProgListDetail，20091202 21:16
	// 只能删除上线日期 == 当前栏目单的日期的
	// date : yyyy-MM-dd
	public CmsResultDto deleteProgListDetails(
			IProgListDetailManager progListDetailManager,
			List<ProgListDetail> progListDetails, String date) {
		cmsLog.debug("Cms -> CmsTransaction -> deleteProgListDetails ...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		if (progListDetails == null) {
			String str = "progListDetails == null";
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		} else {
			for (int i = 0; i < progListDetails.size(); i++) {
				ProgListDetail progListDetail = (ProgListDetail) progListDetails
						.get(i);
				// 比较上线日期 和 当前栏目单日期
				String onlineDate = convertDateToString(progListDetail
						.getValidFrom(), "yyyy-MM-dd");
				if (onlineDate.equalsIgnoreCase(date)) {
					cmsLog.info("Delete proglistdetail : "
							+ progListDetail.getProglistdetailid());
					progListDetailManager.deleteById(progListDetail
							.getProglistdetailid());
				} else {
					String str = "Not delete proglistdetail : "
							+ progListDetail.getProglistdetailid();
					cmsLog.info(str);
					cmsResultDto.setResultCode(Long.valueOf(1));
					cmsResultDto.setErrorMessage(str);
					throw new RuntimeException(str);
				}
			}
		}

		cmsLog.debug("Cms -> CmsTransaction -> deleteProgListDetails returns.");
		return cmsResultDto;
	}

	// 更新栏目单详细 ProgListDetail 20091208 12:40
	public CmsResultDto updateProgListDetails(
			IProgListDetailManager progListDetailManager,
			IPortalColumnManager portalColumnManager,
			IProgPackageManager progPackageManager,
			List<ProgListDetail> progListDetails, Date offlineDate) {
		cmsLog.debug("Cms -> CmsTransaction -> updateProgListDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		if (progListDetails == null) {
			String str = "progListDetails == null";
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		} else if (progListDetails.size() > 0) {
			if (offlineDate == null) {
				cmsLog.info("修改栏目单详细...");
				for (int i = 0; i < progListDetails.size(); i++) {
					ProgListDetail progListDetail = (ProgListDetail) progListDetails
							.get(i);

					progListDetail.setUpdatetime(new Date());
					progListDetailManager.update(progListDetail);
					cmsLog.info("栏目单详细已更新。");
				}
			} else {
				cmsLog.info("修改栏目单详细的下线日期...");
				for (int i = 0; i < progListDetails.size(); i++) {
					ProgListDetail progListDetail = (ProgListDetail) progListDetails
							.get(i);

					// 查询得到节目包和栏目
					PortalColumn portalColumn = (PortalColumn) portalColumnManager
							.getById(progListDetail.getColumnclassid());
					ProgPackage progPackage = (ProgPackage) progPackageManager
							.getById(progListDetail.getProductid());

					// 比较下线日期
					// 如果下线日期 <= 栏目的下线日期 && 下线日期 <= 节目包的下线日期
					// 可以修改
					try {
						if (portalColumn.getValidTo() != null
								&& progPackage.getSubscriberetime() != null) {
							if (offlineDate
									.compareTo(portalColumn.getValidTo()) <= 0
									&& offlineDate.compareTo(progPackage
											.getSubscriberetime()) <= 0) {
								progListDetail.setValidTo(offlineDate);
								progListDetail.setUpdatetime(new Date());
								progListDetailManager.update(progListDetail);
								cmsLog.info("栏目单详细已更新。");
							} else {
								String str = "下线日期晚于节目包的下线日期，或者晚于栏目的下线日期，不修改。";
								cmsLog.warn(str);
								cmsResultDto.setResultCode(Long.valueOf(1));
								cmsResultDto.setErrorMessage(str);
								// throw new RuntimeException(str);
							}
						} else {
							if (portalColumn.getValidTo() != null) {
								if (offlineDate.compareTo(portalColumn
										.getValidTo()) <= 0) {
									progListDetail.setValidTo(offlineDate);
									progListDetail.setUpdatetime(new Date());
									progListDetailManager
											.update(progListDetail);
									cmsLog.info("栏目单详细已更新。");
								} else {
									String str = "下线日期晚于栏目的下线日期，不修改。";
									cmsLog.warn(str);
									cmsResultDto.setResultCode(Long.valueOf(1));
									cmsResultDto.setErrorMessage(str);
									// throw new RuntimeException(str);
								}
							} else if (progPackage.getSubscriberetime() != null) {
								if (offlineDate.compareTo(progPackage
										.getSubscriberetime()) <= 0) {
									progListDetail.setValidTo(offlineDate);
									progListDetail.setUpdatetime(new Date());
									progListDetailManager
											.update(progListDetail);
									cmsLog.info("栏目单详细已更新。");
								} else {
									String str = "下线日期晚于节目包的下线日期，不修改。";
									cmsLog.warn(str);
									cmsResultDto.setResultCode(Long.valueOf(1));
									cmsResultDto.setErrorMessage(str);
									// throw new RuntimeException(str);
								}
							} else {
								progListDetail.setValidTo(offlineDate);
								progListDetail.setUpdatetime(new Date());
								progListDetailManager.update(progListDetail);
								cmsLog.info("栏目单详细已更新。");
							}
						}
					} catch (Exception ex) {
						String str = "更新栏目单详细，异常：" + ex.getMessage();
						cmsLog.error(str);
						cmsResultDto.setResultCode(Long.valueOf(1));
						cmsResultDto.setErrorMessage(str);
					}
				}
			}
		}

		cmsLog.debug("Cms -> CmsTransaction -> updateProgListDetails returns.");
		return cmsResultDto;
	}

	// 20091226
	// ------------- 新一轮的修改开始了，来吧 -----------------------

	// 根据scheduleDate查询所有progListMangDetails，判断活动，修改progListMang的活动
	private int updateCheckProgListMangAct(
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			IFlowActivityOrderManager flowActivityOrderManager,
			String scheduleDate, // 主表的主键
			String currentIdAct, // 当前活动编号
			String nextIdAct, // 下一活动编号
			String nextState2, // 下一活动的性质：（新建N 回退R 顺序P 完成C）
			String remark) {
		cmsLog.debug("Cms -> CmsTransaction -> updateCheckProgListMangAct...");

		// 查询活动的顺序
		List acts = new ArrayList();
		List list = null;

		if (nextState2.equalsIgnoreCase("P")) {
			acts.add(nextIdAct);
			acts.add(currentIdAct);

			do {
				// 根据state2、IdAct，查询得到idact，条件：state2、FLOWACTIVITYIDCHILD=IdAct
				// 得到，倒序的活动list
				list = new ArrayList();
				list = flowActivityOrderManager
						.getLastIdActsByCurrentIdActAndState2(currentIdAct, "P");
				if (list.size() > 0) {
					FlowActivityOrder flowActivityOrder = (FlowActivityOrder) list
							.get(0);
					acts.add(flowActivityOrder.getFlowactivityidparent());
					currentIdAct = flowActivityOrder.getFlowactivityidparent();
				}

			} while (list != null && list.size() > 0);
		} else if (nextState2.equalsIgnoreCase("R")) {
			acts.add(currentIdAct);
			acts.add(nextIdAct);

			do {
				// 根据state2、IdAct，查询得到idact，条件：state2、FLOWACTIVITYIDCHILD=IdAct
				// 得到，倒序的活动list
				list = new ArrayList();
				list = flowActivityOrderManager
						.getLastIdActsByCurrentIdActAndState2(nextIdAct, "P");
				if (list.size() > 0) {
					FlowActivityOrder flowActivityOrder = (FlowActivityOrder) list
							.get(0);
					acts.add(flowActivityOrder.getFlowactivityidparent());
					nextIdAct = flowActivityOrder.getFlowactivityidparent();
				}

			} while (list != null && list.size() > 0);
		}

		// 根据scheduledate，查询总表下所有分表记录，
		List allProgListMangDetails = progListMangDetailManager.findByProperty(
				"scheduledate", scheduleDate);

		// 判断是否有活动的ProgListMangDetail记录
		String newIdAct = "";
		if (acts.size() > 0 && allProgListMangDetails.size() > 0) {
			for (int i = acts.size() - 1; i >= 0; i--) {
				String idAct = (String) acts.get(i);
				for (int j = 0; j < allProgListMangDetails.size(); j++) {
					ProgListMangDetail plmd = (ProgListMangDetail) allProgListMangDetails
							.get(j);

					if (idAct.equalsIgnoreCase(plmd.getIdAct())) {
						newIdAct = idAct;
						break;
					}
				}
				if (!newIdAct.equalsIgnoreCase("")) {
					break;
				}
			}
		}

		// 修改总表的活动为newIdAct
		if (!newIdAct.equalsIgnoreCase("")) {
			ProgListMang progListMang = (ProgListMang) progListMangManager
					.getById(scheduleDate);
			if (progListMang != null) {
				progListMang.setIdAct(newIdAct);
				progListMang.setRemark(remark);
				progListMangManager.update(progListMang);
			}
		}

		cmsLog
				.debug("Cms -> CmsTransaction -> updateCheckProgListMangAct returns.");
		return 0;
	}

	// 20100121 14:05
	// 根据scheduleDate查询本地progListMangDetails，判断活动，修改progListMang的活动
	private int updateCheckLocalProgListMangAct(
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			IFlowActivityOrderManager flowActivityOrderManager,
			String scheduleDate, // 主表的主键
			String currentIdAct, // 当前活动编号
			String nextIdAct, // 下一活动编号
			String nextState2 // 下一活动的性质：（新建N 回退R 顺序P 完成C）
	) {
		cmsLog
				.debug("Cms -> CmsTransaction -> updateCheckLocalProgListMangAct...");

		// 查询活动的顺序
		List acts = new ArrayList();
		List list = null;

		if (nextState2.equalsIgnoreCase("P")) {
			acts.add(nextIdAct);
			acts.add(currentIdAct);

			do {
				// 根据state2、IdAct，查询得到idact，条件：state2、FLOWACTIVITYIDCHILD=IdAct
				// 得到，倒序的活动list
				list = new ArrayList();
				list = flowActivityOrderManager
						.getLastIdActsByCurrentIdActAndState2(currentIdAct, "P");
				if (list.size() > 0) {
					FlowActivityOrder flowActivityOrder = (FlowActivityOrder) list
							.get(0);
					acts.add(flowActivityOrder.getFlowactivityidparent());
					currentIdAct = flowActivityOrder.getFlowactivityidparent();
				}

			} while (list != null && list.size() > 0);
		} else if (nextState2.equalsIgnoreCase("R")) {
			acts.add(currentIdAct);
			acts.add(nextIdAct);

			do {
				// 根据state2、IdAct，查询得到idact，条件：state2、FLOWACTIVITYIDCHILD=IdAct
				// 得到，倒序的活动list
				list = new ArrayList();
				list = flowActivityOrderManager
						.getLastIdActsByCurrentIdActAndState2(nextIdAct, "P");
				if (list.size() > 0) {
					FlowActivityOrder flowActivityOrder = (FlowActivityOrder) list
							.get(0);
					acts.add(flowActivityOrder.getFlowactivityidparent());
					nextIdAct = flowActivityOrder.getFlowactivityidparent();
				}

			} while (list != null && list.size() > 0);
		}

		// 根据scheduledate，查询总表下本地分表记录，
		List allLocalProgListMangDetails = progListMangDetailManager
				.getLocalProgListMangDetailsByScheduleDateAndDefcatseq(
						scheduleDate, "");

		// 判断是否有活动的ProgListMangDetail记录
		String newIdAct = "";
		if (acts.size() > 0 && allLocalProgListMangDetails.size() > 0) {
			for (int i = acts.size() - 1; i >= 0; i--) {
				String idAct = (String) acts.get(i);
				for (int j = 0; j < allLocalProgListMangDetails.size(); j++) {
					ProgListMangDetail plmd = (ProgListMangDetail) allLocalProgListMangDetails
							.get(j);

					if (idAct.equalsIgnoreCase(plmd.getIdAct())) {
						newIdAct = idAct;
						break;
					}
				}
				if (!newIdAct.equalsIgnoreCase("")) {
					break;
				}
			}
		}

		// 修改总表的活动为newIdAct
		if (!newIdAct.equalsIgnoreCase("")) {
			ProgListMang progListMang = (ProgListMang) progListMangManager
					.getById(scheduleDate);
			if (progListMang != null) {
				progListMang.setIdAct(newIdAct);
				progListMangManager.update(progListMang);
			}
		}

		cmsLog
				.debug("Cms -> CmsTransaction -> updateCheckLocalProgListMangAct returns.");
		return 0;
	}

	// 发送栏目单（详细），到下一活动idProcess，下一活动人，
	// 目前，界面不选择下一活动和下一活动人
	public CmsResultDto updateProgListMangDetails(
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			IBpmcManager bpmcManager,
			IFlowActivityOrderManager flowActivityOrderManager,
			String scheduleDate, // 主表的主键
			// List<PortalColumn> portalColumns, // 需要发送的栏目单详细
			String defcatseq, // 栏目的代码序列
			String nextIdAct, // 下一活动编号
			String nextOperator, // 下一操作人员
			String nextState2, // 下一活动的性质：（新建N 回退R 顺序P 完成C）
			String currentIdAct, // 当前活动编号
			String operator, // 当前操作人员
			String sendremark // 备注
	) {
		// 说明：
		// FU00000081 文件加扰
		// FU00000082 生成页面
		// FU00000083 PORTAL完成
		// FU00000084 预览
		// FU00000085 迁移
		// FU00000086 播发
		// FU00000087 完成
		// 总单的发送，是针对所有栏目的（不仅仅是本地栏目）
		// 界面 总单审校：当前活动（“文件加扰”81），以分表为单位发送。
		// 81-->82 判断栏目（栏目单详细）下所有节目包的state>=2加扰库，才能发送
		// 界面 Portal预览：当前活动（“生成页面”82
		// “PORTAL完成”83），82-->83以分表为单位发送，83-->85压缩portal并以date为单位发送所有栏目分表
		// 82-->83 以栏目分表为单位，生成portal后，发送
		// 83-->85 以date总表为单位，判断是否所有分表活动都是83，是才能压缩并发送所有栏目分表到85
		// 界面 编单迁移：当前活动（“迁移”85），以date栏目单总表为单位发送，
		// 85-->86 判断是否所有栏目单分表对应所有栏目单详细对应所有节目包的state==3，是才能发送
		// 界面 编单播发：当前活动（“播发”86），以date栏目单总表为单位发送，
		// 86-->87 播放单生成成功，发送

		cmsLog.debug("Cms -> CmsTransaction -> updateProgListMangDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 没有发送的记录，用于返回
		List<ProgListMangDetail> notSaveList = new ArrayList<ProgListMangDetail>();

		// 根据scheduleDate、defcatseq查询，得到progListMangDetails
		// 返回：List<ProgListMangDetail>
		cmsLog.info("参数 scheduleDate: " + scheduleDate + "\tdefcatseq: "
				+ defcatseq);
		List progListMangDetails = progListMangDetailManager
				.getProgListMangDetailsByScheduleDateAndDefcatseq(scheduleDate,
						defcatseq);

		cmsLog.info("根据栏目序列查询出栏目数: " + progListMangDetails.size());

		for (int i = 0; i < progListMangDetails.size(); i++) {
			// 
			ProgListMangDetail progListMangDetail = (ProgListMangDetail) progListMangDetails
					.get(i);

			// 判断当前活动与progListMangDetail的活动是否一致，
			if (progListMangDetail.getIdAct().equalsIgnoreCase(currentIdAct)) {
				// 如果一致，修改
				// 修改progListMangDetails的nextIdAct、nextOperator
				progListMangDetail.setIdAct(nextIdAct);
				progListMangDetail.setPop(nextOperator);
				progListMangDetail.setRemark(sendremark);
				progListMangDetailManager.update(progListMangDetail);
				cmsLog.info("栏目编号: " + progListMangDetail.getColumnclassid()
						+ " 日志已修改!");
			} else {
				// 如果不一致，记录，准备返回。
				notSaveList.add(progListMangDetail);
				cmsLog.info("栏目编号: " + progListMangDetail.getColumnclassid()
						+ " 日志未修改!");
			}
		}

		// 根据scheduleDate查询所有progListMangDetails，判断活动，修改progListMang的活动
		updateCheckProgListMangAct(progListMangManager,
				progListMangDetailManager, flowActivityOrderManager,
				scheduleDate, // 主表的主键
				currentIdAct, // 当前活动编号
				nextIdAct, // 下一活动编号
				nextState2, // 下一活动的性质：（新建N 回退R 顺序P 完成C）
				sendremark);

		cmsResultDto.setResultObject(notSaveList);
		cmsLog
				.debug("Cms -> CmsTransaction -> updateProgListMangDetails returns.");
		return cmsResultDto;
	}

	// 这就是传说中的方法13
	// 20100121 14:03
	// 发送本地栏目单（详细），到下一活动idProcess，下一活动人，
	public CmsResultDto updateLocalProgListMangDetails(
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			IBpmcManager bpmcManager,
			IFlowActivityOrderManager flowActivityOrderManager,
			String scheduleDate, // 主表的主键
			// List<PortalColumn> portalColumns, // 需要发送的栏目单详细
			String defcatseq, // 栏目的代码序列
			String nextIdAct, // 下一活动编号
			String nextOperator, // 下一操作人员
			String nextState2, // 下一活动的性质：（新建N 回退R 顺序P 完成C）
			String currentIdAct, // 当前活动编号
			String operator, // 当前操作人员
			String sendremark // 备注
	) {
		cmsLog
				.debug("Cms -> CmsTransaction -> updateLocalProgListMangDetails...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 没有发送的记录，用于返回
		List<ProgListMangDetail> notSaveList = new ArrayList<ProgListMangDetail>();

		// 根据scheduleDate、defcatseq查询，得到progListMangDetails
		List progListMangDetails = progListMangDetailManager
				.getLocalProgListMangDetailsByScheduleDateAndDefcatseq(
						scheduleDate, defcatseq);

		if (progListMangDetails.size() > 0) {
			for (int i = 0; i < progListMangDetails.size(); i++) {
				// 
				ProgListMangDetail progListMangDetail = (ProgListMangDetail) progListMangDetails
						.get(i);

				// 判断当前活动与progListMangDetail的活动是否一致，
				if (progListMangDetail.getIdAct()
						.equalsIgnoreCase(currentIdAct)) {
					// 如果一致，修改
					// 修改progListMangDetails的nextIdAct、nextOperator
					progListMangDetail.setIdAct(nextIdAct);
					progListMangDetail.setPop(nextOperator);
					progListMangDetail.setRemark(sendremark);
					progListMangDetailManager.update(progListMangDetail);
				} else {
					// 如果不一致，记录，准备返回。
					notSaveList.add(progListMangDetail);
				}
			}

			// 根据scheduleDate查询所有progListMangDetails，判断活动，修改progListMang的活动
			updateCheckLocalProgListMangAct(progListMangManager,
					progListMangDetailManager, flowActivityOrderManager,
					scheduleDate, // 主表的主键
					currentIdAct, // 当前活动编号
					nextIdAct, // 下一活动编号
					nextState2 // 下一活动的性质：（新建N 回退R 顺序P 完成C）
			);
		} else {
			String str = "没有查询到本地的栏目单。";
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		}
		cmsResultDto.setResultObject(notSaveList);
		cmsLog
				.debug("Cms -> CmsTransaction -> updateLocalProgListMangDetails returns.");
		return cmsResultDto;
	}

	// 20100130
	// 刷新栏目单详细中节目包的状态state1， 节目包状态，0-导入 1-缓存库 2-加扰库 3-播控库
	public CmsResultDto updateRefreshState1OfProgPackage(
			IPackageFilesManager packageFilesManager,
			IProgListDetailManager progListDetailManager,
			IProgPackageManager progPackageManager,
			String stclasscodeNearOnline, // 缓存库存储体等级code
			String stclasscodeCaOnline, // 加扰库存储体等级code
			String stclasscodeOnline, // 播控库存储体等级code
			String stclasscodeBjOnline, // 在上海的北京缓存库存储体等级code
			ProgPackage progPackage, int type // 0 - 上海； 1 - 北京
	) {
		cmsLog.debug("Cms -> CmsTransaction -> refreshState1OfProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		String stclasscode = "";
		Long nextState1 = (long) 0;
		// 根据节目包状态，查询下一级存储体上节目包主文件存在情况
		// 节目包状态，0-导入 1-缓存库 2-加扰库 3-播控库 9-在上海的北京缓存库
		// 0 --> 1 所有文件
		// 1 --> 2 所有文件
		// 2 --> 3 主文件
		Long state = (long) 0;
		// ProgPackage progPackage =
		// (ProgPackage)progPackageManager.getById(progListDetail.getProductid());
		if (progPackage == null) {
			String str = "节目包不存在。";
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}
		if (progPackage.getState() == null) {
			String str = "节目包状态为空。节目包ID：" + progPackage.getProductid();
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}
		state = progPackage.getState();
		if (state == 0) {
			stclasscode = stclasscodeNearOnline;
			nextState1 = (long) 1;
		} else if (state == 1) {
			// 0 - 上海； 1 - 北京
			if (type == 0) {
				stclasscode = stclasscodeBjOnline;
				nextState1 = (long) 9;
			} else if (type == 1) {
				stclasscode = stclasscodeCaOnline;
				nextState1 = (long) 2;
			}
		} else if (state == 2) {
			stclasscode = stclasscodeOnline;
			nextState1 = (long) 3;
		} else if (state == 3) {
			stclasscode = "";
			nextState1 = state;
		} else {
			stclasscode = "";
			nextState1 = state;
		}

		if (stclasscode == null || stclasscode.equalsIgnoreCase("")) {
			// 返回失败
			String str = "节目包的状态是播控库或非法，不需要修改状态。节目包ID："
					+ progPackage.getProductid();
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}

		List pmfs = new ArrayList();
		if (state == 0 || state == 1) {
			pmfs = packageFilesManager.findByProperty("productid", progPackage
					.getProductid());
		} else if (state == 2) {
			// 根据节目包id 和 主文件表示(progrank)，查询packagefiles、programfiles表，得到节目包的文件
			pmfs = packageFilesManager.getProgramFilesesByProductidProgrank(
					progPackage.getProductid(), // 节目包ID
					(long) 1 // 主文件标识，0 - 不是; 1 - 是
					);
		} else {
			// 返回失败
			String str = "节目包的状态是播控库或非法，不需要修改状态。节目包ID："
					+ progPackage.getProductid();
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}
		if (pmfs == null || pmfs.size() <= 0) {
			// 返回失败
			String str = "节目包下没有查询到文件。节目包ID：" + progPackage.getProductid();
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			// return cmsResultDto;
		} else {
			// 4 - 根据栏目单详细的节目包ID和节目包状态，查询文件存放位置表，得到是否节目包所有主文件都在下一存储位置上
			boolean allexist = true;
			cmsLog.info("判断节目包的所有文件是否已经到位...");
			cmsLog.info("节目包的文件数量：" + pmfs.size());
			for (int k = 0; k < pmfs.size(); k++) {
				String progfileid = "";
				if (state == 0 || state == 1) {
					PackageFiles pmf = (PackageFiles) pmfs.get(k);
					progfileid = pmf.getProgfileid();
				} else if (state == 2) {
					ProgramFiles pmf = (ProgramFiles) pmfs.get(k);
					progfileid = pmf.getProgfileid();
				}

				cmsLog.info("处理第" + (k + 1) + "个文件...");
				cmsLog.info("文件ID：" + progfileid);

				// 根据progfileid和存储体等级code，查文件存放位置表
				List sourcepaths = packageFilesManager
						.getSourcePathByProgfileidStclasscode(progfileid,
								stclasscode);
				if (sourcepaths == null || sourcepaths.size() <= 0) {
					cmsLog.info("文件没有到位，中止循环判断。");
					allexist = false;
					break;
				} else {
					cmsLog.info("文件已经到位。");
				}
			}
			if (allexist == true && nextState1 != null) {
				// 4.1 - 如果是，改变栏目单详细中的节目包状态
				// progListDetail.setState1(nextState1);
				// progListDetail.setDealstate((long)0);
				// progListDetailManager.update(progListDetail);
				cmsLog.info("节目包的所有文件都已经到位...");
				cmsLog.info("准备修改节目包状态和处理状态，节目包ID："
						+ progPackage.getProductid());

				progPackage.setState(nextState1);
				progPackage.setDealstate((long) 0);
				progPackageManager.update(progPackage);
				cmsLog.info("节目包的状态修改为 " + nextState1.toString());
				cmsLog.info("节目包的处理状态修改为“未处理”。");
			} else {
				// 4.2 - 如果不是，不处理
				cmsLog.info("节目包的文件没有都到位，不修改节目包状态和处理状态。");
			}
		}

		cmsLog
				.debug("Cms -> CmsTransaction -> refreshState1OfProgPackage returns.");
		return cmsResultDto;
	}

	// 20100201 13:31
	// 判断总单管理中的发送，是否允许发送
	private boolean checkSendProgListMangDetails(
			IPortalColumnManager portalColumnManager,
			IProgListDetailManager progListDetailManager,
			IProgListMangDetailManager progListMangDetailManager, String date, // 日期，格式：yyyy-MM-dd
			String currentIdAct, // 当前活动编号
			String defcatseq // 栏目的代码序列
	) {
		// 说明：
		// FU00000081 文件加扰
		// FU00000082 生成页面
		// FU00000083 PORTAL完成
		// FU00000084 预览
		// FU00000085 迁移
		// FU00000086 播发
		// FU00000087 完成
		// 总单的发送，是针对所有栏目的（不仅仅是本地栏目）
		// 界面 总单审校：当前活动（“文件加扰”81），以分表为单位发送。
		// 81-->82 判断栏目（栏目单详细）下所有节目包的state>=2加扰库，才能发送
		// 界面 Portal预览：当前活动（“生成页面”82
		// “PORTAL完成”83），82-->83以分表为单位发送，83-->85压缩portal并以date为单位发送所有栏目分表
		// 82-->83 以栏目分表为单位，生成portal后，发送
		// 83-->85 以date总表为单位，判断是否所有分表活动都是83，是才能压缩并发送所有栏目分表到85
		// 界面 编单迁移：当前活动（“迁移”85），以date栏目单总表为单位发送，
		// 85-->86 判断是否所有栏目单分表对应所有栏目单详细对应所有节目包的state==3，是才能发送
		// 界面 编单播发：当前活动（“播发”86），以date栏目单总表为单位发送，
		// 86-->87 播放单生成成功，发送

		cmsLog.debug("Cms -> CmsTransaction -> checkSendProgListMangDetails...");

		boolean allowSend = true;

		// 首先判断数据一致性，当前活动和分表的当前活动是否一致

		// 判断是否可以发送到下一活动
		if (currentIdAct.equalsIgnoreCase("FU00000081")) {
			// 界面 总单审校：当前活动（“文件加扰”81），以分表为单位发送。
			// 81-->82 判断栏目（栏目单详细）下所有节目包的state>=2加扰库，才能发送
			List pcs = portalColumnManager
					.getValidLeafPortalColumnsByDefcatseq(defcatseq);
			for (int i = 0; i < pcs.size(); i++) {
				PortalColumn portalColumn = (PortalColumn) pcs.get(i);
				// 返回：List<Object[]>
				// (ProgListDetail)Object[0]
				// (ProgPackage)Object[1]
				List plds = progListDetailManager
						.getProgListDetailsByScheduledateAndColumnclassid(date,
								portalColumn.getColumnclassid());

				// 判断所有节目包的state是否都 >= 2
				for (int j = 0; j < plds.size(); j++) {
					Object[] rows = (Object[]) plds.get(j);
					ProgListDetail progListDetail = (ProgListDetail) rows[0];
					ProgPackage progPackage = (ProgPackage) rows[1];

					if (!(progPackage.getState() >= 2)) {
						allowSend = false;
						cmsLog.info("栏目分表对应的节目包中，存在节目包状态<2的，不能发送到下一活动。节目包ID："
								+ progPackage.getProductid());
						break;
					}
				}

				if (allowSend == false) {
					break;
				}
			}
		} else if (currentIdAct.equalsIgnoreCase("FU00000082")) {
			// 界面 Portal预览：当前活动（“生成页面”82
			// “PORTAL完成”83），82-->83以分表为单位发送，83-->85压缩portal并以date为单位发送所有栏目分表
			// 82-->83 以栏目分表为单位，生成portal后，发送
			// 判断栏目是否已经生成portal，如果是才能发送

		} else if (currentIdAct.equalsIgnoreCase("FU00000083")) {
			// 界面 Portal预览：当前活动（“生成页面”82
			// “PORTAL完成”83），82-->83以分表为单位发送，83-->85压缩portal并以date为单位发送所有栏目分表
			// 83-->85 以date总表为单位，判断是否所有分表活动都是83，是才能压缩并发送所有栏目分表到85
			String scheduledate = convertDateToScheduleDate(date);
			// 根据栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
			// 返回：List<Object[]>
			// (ProgListMangDetail)Object[0]
			// (PortalColumn)Object[1]
			// (FunResource)Object[2]
			List list = progListMangDetailManager
					.getProgListMangDetailsByScheduledate(scheduledate);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] rows = (Object[]) list.get(i);
					ProgListMangDetail plmd = (ProgListMangDetail) rows[0];
					if (!plmd.getIdAct().equalsIgnoreCase(currentIdAct)) // “PORTAL完成”
					{
						allowSend = false;
						cmsLog
								.info("不是所有栏目单分表都是“PORTAL完成”FU00000083活动，不能发送到下一活动。日期："
										+ date);
						break;
					}
				}
			} else {
				allowSend = false;
				cmsLog.info("未查询到栏目单分表记录，不能发送到下一活动。日期：" + date);
			}
		} else if (currentIdAct.equalsIgnoreCase("FU00000085")) {
			// 界面 编单迁移：当前活动（“迁移”85），以date栏目单总表为单位发送，
			// 85-->86 判断是否所有栏目单分表对应所有栏目单详细对应所有节目包的state==3，是才能发送
			List pcs = portalColumnManager
					.getValidLeafPortalColumnsByDefcatseq(defcatseq);
			for (int i = 0; i < pcs.size(); i++) {
				PortalColumn portalColumn = (PortalColumn) pcs.get(i);
				// 返回：List<Object[]>
				// (ProgListDetail)Object[0]
				// (ProgPackage)Object[1]
				List plds = progListDetailManager
						.getProgListDetailsByScheduledateAndColumnclassid(date,
								portalColumn.getColumnclassid());

				// 判断所有节目包的state是否都 == 3
				for (int j = 0; j < plds.size(); j++) {
					Object[] rows = (Object[]) plds.get(j);
					ProgListDetail progListDetail = (ProgListDetail) rows[0];
					ProgPackage progPackage = (ProgPackage) rows[1];

					if (progPackage.getProgtype().equalsIgnoreCase("V")) {
						if (!(progPackage.getState() == 3)) {
							allowSend = false;
							cmsLog
									.info("栏目分表对应的节目包中，存在节目包状态不为3的，不能发送到下一活动。节目包ID："
											+ progPackage.getProductid());
							break;
						}
					}
				}

				if (allowSend == false) {
					break;
				}
			}
		} else if (currentIdAct.equalsIgnoreCase("FU00000086")) {
			// 界面 编单播发：当前活动（“播发”86），以date栏目单总表为单位发送，
			// 86-->87 播放单生成成功，发送
			String scheduledate = convertDateToScheduleDate(date);
			// 根据栏目单总表或者主表管理表TPROGLISTMANG，查询栏目单分表管理表(TPROGLISTMANGDETAIL)
			// 返回：List<Object[]>
			// (ProgListMangDetail)Object[0]
			// (PortalColumn)Object[1]
			// (FunResource)Object[2]
			List list = progListMangDetailManager
					.getProgListMangDetailsByScheduledate(scheduledate);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] rows = (Object[]) list.get(i);
					ProgListMangDetail plmd = (ProgListMangDetail) rows[0];
					if (!plmd.getIdAct().equalsIgnoreCase(currentIdAct)) // “PORTAL完成”
					{
						allowSend = false;
						cmsLog
								.info("不是所有栏目单分表都是“PORTAL完成”FU00000083活动，不能发送到下一活动。日期："
										+ date);
						break;
					}
				}
			} else {
				allowSend = false;
				cmsLog.info("未查询到栏目单分表记录，不能发送到下一活动。日期：" + date);
			}
		} else if (currentIdAct.equalsIgnoreCase("FU00000087")) {
			// 不需要发送
			allowSend = false;
			cmsLog.info("当前活动是“完成”，不能发送到一下活动。当前活动：" + currentIdAct);
		}

		cmsLog
				.debug("Cms -> CmsTransaction -> checkSendProgListMangDetails returns.");
		return allowSend;
	}

	// 20100223 15:18
	// 在查询栏目单的时候，判断栏目单总表分表里是否有记录，如果没有记录，就添加；如果有，就不添加
	public CmsResultDto saveCheckProgListMang(
			IPortalColumnManager portalColumnManager,
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager, String date,
			String defcatseq, String operator) {
		// 说明：
		// 在编单或者数据导入的时候初始化这两张表的记录
		// 总表，没有则添加
		// 分表，本地栏目初始化活动为"FU00000077"编单定义
		// 分表，非本地栏目初始化活动为""导入

		cmsLog.debug("Cms -> CmsTransaction -> checkProgListMang...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 查询总表是否有记录
		String scheduledate = convertDateToScheduleDate(date);
		ProgListMang progListMang = (ProgListMang) progListMangManager
				.getById(scheduledate);
		if (progListMang == null) {
			// 无记录
			progListMang = new ProgListMang();
			progListMang.setScheduledate(scheduledate);
			progListMang.setIdAct("FU00000077");
			progListMang.setInputmanid(operator);
			progListMang.setInputtime(new Date());
			progListMangManager.save(progListMang);
		}

		int count1 = 0;
		int count2 = 0;

		// 查询栏目
		List portalColumns = portalColumnManager
				.getLeafPortalColumnsByDefcatseq(defcatseq);
		if (portalColumns != null && portalColumns.size() > 0) {
			for (int j = 0; j < portalColumns.size(); j++) {
				PortalColumn portalColumn = (PortalColumn) portalColumns.get(j);

				// 判断栏目是否有效
				if (portalColumn.getValidFlag() != 0) {
					// 不是有效的栏目，忽略
					continue;
				}

				// 根据scheduleDate和Columnclassid，查询得到progListMangDetail
				List progListMangDetails = progListMangDetailManager
						.getProgListMangDetailsByScheduleDateAndColumnclassid(
								scheduledate, portalColumn.getColumnclassid());

				if (progListMangDetails == null
						|| progListMangDetails.size() <= 0) {
					ProgListMangDetail progListMangDetail = new ProgListMangDetail();
					progListMangDetail.setScheduledate(scheduledate);
					progListMangDetail.setColumnclassid(portalColumn
							.getColumnclassid());

					progListMangDetail.setInputmanid(operator);
					progListMangDetail.setInputtime(new Date());
					if (portalColumn.getCountnumb() == 0) {
						// 本地
						progListMangDetail.setState2(Long.valueOf(1));
						progListMangDetail.setIdAct("FU00000077");
						count1++;
					} else {
						progListMangDetail.setState2(Long.valueOf(0));
						progListMangDetail.setIdAct("FU00000080");
						count2++;
					}
					progListMangDetailManager.save(progListMangDetail);
				} else {
					// 如果已经存在，检查本地字段，
					ProgListMangDetail progListMangDetail = (ProgListMangDetail) progListMangDetails
							.get(0);

					if (portalColumn.getCountnumb() == 0) {
						// 本地
						if (progListMangDetail.getState2() == null
								|| progListMangDetail.getState2() != 1) {
							progListMangDetail.setState2((long) 1);
							progListMangDetailManager
									.update(progListMangDetail);
						}
					} else {
						// 非本地
						if (progListMangDetail.getState2() == null
								|| progListMangDetail.getState2() != 0) {
							progListMangDetail.setState2((long) 0);
							progListMangDetailManager
									.update(progListMangDetail);
						}
					}
				}
			}
		}
		if (count1 <= 0 && count2 > 0) {
			progListMang.setIdAct("FU00000080");
			progListMangManager.update(progListMang);
		}

		cmsLog.debug("Cms -> CmsTransaction -> checkProgListMang returns.");
		return cmsResultDto;
	}


	// 20100222 15:59
	private String getStrFromSmbFile(String xmlSmbFilePath // 节目包xml文件路径
	) {
		cmsLog.debug("Cms -> CmsTransaction -> getStrFromSmbFile...");
		String strxml = "";

		try {
			SmbFile xmlfile = new SmbFile(xmlSmbFilePath);

			int last = 0;
			Long localreadbytes = Long.valueOf(0);
			// byte[] bytes = new byte[1024];
			//
			// // BufferedReader br = null;
			// // InputStreamReader read = new InputStreamReader(new
			// FileInputStream(f),"utf-8");
			// // br = new BufferedReader(read);
			//			
			// SmbFileInputStream fileStreamIn = new
			// SmbFileInputStream(xmlfile);
			// while ((last = fileStreamIn.read(bytes)) != -1)
			// {
			// byte[] bs = new byte[last];
			// for(int i = 0; i < last; i++)
			// {
			// bs[i] = bytes[i];
			// }
			// String str = new String(bs, "UTF-8");
			// strxml += str;
			// }

			byte[] bytes = new byte[(int) (xmlfile.length())];
			SmbFileInputStream fileStreamIn = new SmbFileInputStream(xmlfile);
			while ((last = fileStreamIn.read(bytes)) != -1) {
				String str = new String(bytes, "UTF-8");
				strxml += str;
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> CmsTransaction -> getStrFromSmbFile，异常："
					+ e.getMessage());
		}
		cmsLog.debug("Cms -> CmsTransaction -> getStrFromSmbFile returns.");
		return strxml.replaceAll("\u00A0", "\u0020");
	}

	// 20100222 15:43
	// 分析节目包的xml，获得节目包和文件信息
	private List getProgPackageFilesByXml(String xmlSmbFilePath // 节目包xml文件路径
	) {
		// 返回：List
		// 1 - ProgPackage
		// 2 - List<ProgramFiles>
		// 3 - List<PackageFiles>
		// 4 - List<Integer> // 文件导出标识，标识此次数据导出是否同时导出该文件，0 - no ; 1 - yes
		// 5 - List<String> // 文件的filedate

		cmsLog.debug("Cms -> CmsTransaction -> getProgPackageFilesByXml...");
		List list = new ArrayList();

		try {
			if (xmlSmbFilePath == null || xmlSmbFilePath.equalsIgnoreCase("")) {
				String str = "输入参数为空。";
				cmsLog.warn(str);
			} else {
				String strxml = getStrFromSmbFile(xmlSmbFilePath);

				// 去掉utf-8 bom头
				strxml = strxml.substring(strxml.indexOf("<?xml"), strxml
						.length());

				// 将String转成parse可以识别的InputSource
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				StringReader sr = new StringReader(strxml);
				InputSource is = new InputSource(sr);
				Document doc = builder.parse(is);
				doc.normalize();

				// 获取节目包信息
				ProgPackage progPackage = new ProgPackage();
				progPackage.setPpxml(strxml);
				NodeList cells = doc.getElementsByTagName("APP");
				for (int i = 0; i < cells.getLength(); i++) {
					Node cell = cells.item(i);
					Element cellattr = (Element) cells.item(i);

					String str = "";
					if (cellattr.hasAttribute("PROGPACKAGEID")) // 判断节点有tag属性
					{
						str = cell.getAttributes()
								.getNamedItem("PROGPACKAGEID").getNodeValue();
						progPackage.setProductid(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("ACTORS")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("ACTORS")
								.getNodeValue();
						progPackage.setActors(str
								.replaceAll("\u00A0", "\u0020"));
					}
					if (cellattr.hasAttribute("AUDIOLANGUAGE")) // 判断节点有tag属性
					{
						str = cell.getAttributes()
								.getNamedItem("AUDIOLANGUAGE").getNodeValue();
						progPackage.setAudiolanguage(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("CATEGORY")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("CATEGORY")
								.getNodeValue();
						progPackage.setCategory(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("COUNTRYDIST")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("COUNTRYDIST")
								.getNodeValue();
						progPackage.setCountrydist(str.replaceAll("\u00A0",
								"\u0020"));
					}
					// if(cellattr.hasAttribute("DEALSTATE")) // 判断节点有tag属性
					// {
					// str =
					// cell.getAttributes().getNamedItem("DEALSTATE").getNodeValue();
					progPackage.setDealstate((long) 0);
					// }
					if (cellattr.hasAttribute("DESCRIPTION")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("DESCRIPTION")
								.getNodeValue();
						progPackage.setDescription(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("DIRECTOR")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("DIRECTOR")
								.getNodeValue();
						progPackage.setDirector(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("EPICODEID")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("EPICODEID")
								.getNodeValue();
						progPackage.setEpicodeid(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("EPICODENAME")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("EPICODENAME")
								.getNodeValue();
						progPackage.setEpicodename(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("EPICODENUMBER")) // 判断节点有tag属性
					{
						str = cell.getAttributes()
								.getNamedItem("EPICODENUMBER").getNodeValue();
						progPackage.setEpicodenumber(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("FILESIZEHI")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("FILESIZEHI")
								.getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							progPackage.setFilesizehi(Long.valueOf(str));
					}
					if (cellattr.hasAttribute("FILESIZELOW")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("FILESIZELOW")
								.getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							progPackage.setFilesizelow(Long.valueOf(str));
					}
					if (cellattr.hasAttribute("INPUTMANID")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("INPUTMANID")
								.getNodeValue();
						progPackage.setInputmanid(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("INPUTTIME")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("INPUTTIME")
								.getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							progPackage.setInputtime(fileoper
									.convertStringToDate(str,
											"yyyy-MM-dd HH:mm:ss"));
					}
					if (cellattr.hasAttribute("ISSUEDATE")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("ISSUEDATE")
								.getNodeValue();
						progPackage.setIssuedate(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("LENGTHTC")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("LENGTHTC")
								.getNodeValue();
						progPackage.setLengthtc(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("PRODUCTNAME")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("PRODUCTNAME")
								.getNodeValue();
						progPackage.setProductname(str.replaceAll("\u00A0",
								"\u0020"));
					}
					// if(cellattr.hasAttribute("PROGPACKAGENAME")) //
					// 判断节点有tag属性
					// {
					// str =
					// cell.getAttributes().getNamedItem("PROGPACKAGENAME").getNodeValue();
					// progPackage.setProductname(str);
					// }
					if (cellattr.hasAttribute("PROGTYPE")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("PROGTYPE")
								.getNodeValue();
						progPackage.setProgtype(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("PTGLOBALID")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("PTGLOBALID")
								.getNodeValue();
						progPackage.setPtglobalid(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("REMARK")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("REMARK")
								.getNodeValue();
						progPackage.setRemark(str
								.replaceAll("\u00A0", "\u0020"));
					}
					if (cellattr.hasAttribute("SHOOTDATE")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("SHOOTDATE")
								.getNodeValue();
						progPackage.setShootdate(str.replaceAll("\u00A0",
								"\u0020"));
					}
					// if(cellattr.hasAttribute("STATE")) // 判断节点有tag属性
					// {
					// str =
					// cell.getAttributes().getNamedItem("STATE").getNodeValue();
					progPackage.setState((long) 0);
					// }
					if (cellattr.hasAttribute("STYLEID")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("STYLEID")
								.getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							progPackage.setStyleid(Long.valueOf(str));
					}
					if (cellattr.hasAttribute("SUBSCRIBERETIME")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem(
								"SUBSCRIBERETIME").getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							progPackage.setSubscriberetime(fileoper
									.convertStringToDate(str,
											"yyyy-MM-dd HH:mm:ss"));
					}
					if (cellattr.hasAttribute("SUBSCRIBERSTIME")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem(
								"SUBSCRIBERSTIME").getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							progPackage.setSubscriberstime(fileoper
									.convertStringToDate(str,
											"yyyy-MM-dd HH:mm:ss"));
					}
					if (cellattr.hasAttribute("SUBTITLELANGUAGE")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem(
								"SUBTITLELANGUAGE").getNodeValue();
						progPackage.setSubtitlelanguage(str.replaceAll(
								"\u00A0", "\u0020"));
					}
					if (cellattr.hasAttribute("SUMFILESIZE")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("SUMFILESIZE")
								.getNodeValue();
						progPackage.setSumfilesize(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("TITLEBRIEF")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("TITLEBRIEF")
								.getNodeValue();
						progPackage.setTitlebrief(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("UPDATEMANID")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("UPDATEMANID")
								.getNodeValue();
						progPackage.setUpdatemanid(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("UPDATETIME")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("UPDATETIME")
								.getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							progPackage.setUpdatetime(fileoper
									.convertStringToDate(str,
											"yyyy-MM-dd HH:mm:ss"));
					}
				}

				// 获取节目包文件信息
				List<ProgramFiles> programfileses = new ArrayList<ProgramFiles>();
				List<PackageFiles> packageFileses = new ArrayList<PackageFiles>();
				List<Integer> fileexists = new ArrayList<Integer>();
				List<String> filedates = new ArrayList<String>();
				cells = doc.getElementsByTagName("FILE");
				for (int i = 0; i < cells.getLength(); i++) {
					Node cell = cells.item(i);
					Element cellattr = (Element) cells.item(i);
					ProgramFiles programFiles = new ProgramFiles();
					PackageFiles packageFiles = new PackageFiles();
					int fileexist = 1; // 默认要迁移文件
					String filedate = "";

					String str = "";
					if (cellattr.hasAttribute("PROGFILEID")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("PROGFILEID")
								.getNodeValue();
						programFiles.setProgfileid(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("FILENAME")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("FILENAME")
								.getNodeValue();
						programFiles.setFilename(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("FILECODE")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("FILECODE")
								.getNodeValue();
						programFiles.setFilecode(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("FILETYPEID")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("FILETYPEID")
								.getNodeValue();
						programFiles.setFiletypeid(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("PROGRANK")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("PROGRANK")
								.getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							programFiles.setProgrank(Long.valueOf(str));
					}
					if (cellattr.hasAttribute("INPUTMANID")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("INPUTMANID")
								.getNodeValue();
						programFiles.setInputmanid(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("INPUTTIME")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("INPUTTIME")
								.getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							programFiles.setInputtime(fileoper
									.convertStringToDate(str,
											"yyyy-MM-dd HH:mm:ss"));
					}
					if (cellattr.hasAttribute("FILESIZEHI")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("FILESIZEHI")
								.getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							programFiles.setFilesizehi(Long.valueOf(str));
					}
					if (cellattr.hasAttribute("FILESIZELOW")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("FILESIZELOW")
								.getNodeValue();
						if (str != null && !str.equalsIgnoreCase(""))
							programFiles.setFilesizelow(Long.valueOf(str));
					}
					if (cellattr.hasAttribute("CONTENTFILESIZE")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem(
								"CONTENTFILESIZE").getNodeValue();
						programFiles.setContentfilesize(str.replaceAll(
								"\u00A0", "\u0020"));
					}
					if (cellattr.hasAttribute("CONTENTCHECKSUM")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem(
								"CONTENTCHECKSUM").getNodeValue();
						programFiles.setContentchecksum(str.replaceAll(
								"\u00A0", "\u0020"));
					}
					if (cellattr.hasAttribute("DUBBEDLANGUAGE")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem(
								"DUBBEDLANGUAGE").getNodeValue();
						programFiles.setDubbedlanguage(str.replaceAll("\u00A0",
								"\u0020"));
					}
					if (cellattr.hasAttribute("SUBTITLELANGUAGE")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem(
								"SUBTITLELANGUAGE").getNodeValue();
						programFiles.setSubtitlelanguage(str.replaceAll(
								"\u00A0", "\u0020"));
					}
					if (cellattr.hasAttribute("FILEDATE")) // 判断节点有tag属性
					{
						filedate = cell.getAttributes()
								.getNamedItem("FILEDATE").getNodeValue();
					}
					if (cellattr.hasAttribute("ISFLAG")) // 判断节点有tag属性
					{
						str = cell.getAttributes().getNamedItem("ISFLAG")
								.getNodeValue();
						if (str != null && !str.equalsIgnoreCase("")) {
							if (str.equalsIgnoreCase("Y")) {
								fileexist = 1;
							} else {
								fileexist = 0;
							}
						} else {
							fileexist = 1;
						}
					}

					packageFiles.setProductid(progPackage.getProductid());
					packageFiles.setProgfileid(programFiles.getProgfileid());

					programfileses.add(programFiles);
					packageFileses.add(packageFiles);
					fileexists.add(fileexist);
					filedates.add(filedate);
				}

				list.add(progPackage);
				list.add(programfileses);
				list.add(packageFileses);
				list.add(fileexists);
				list.add(filedates);
			}
		} catch (Exception e) {
			cmsLog.error(e.getMessage());
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> getProgPackageFilesByXml returns.");
		return list;
	}

	// 20100223 14:29
	// 分析栏目单的xml，获得栏目单详细
	private List getProgListDetailsByXml(String xmlSmbFilePath // 节目包xml文件路径
	) {
		// 返回：
		// List
		// 0 - String
		// 1 - List<ProgListDetail>

		cmsLog.debug("Cms -> CmsTransaction -> getProgListDetailsByXml...");
		List list = new ArrayList();

		try {
			String strxml = getStrFromSmbFile(xmlSmbFilePath);

			// 去掉utf-8 bom头
			cmsLog.info(strxml);
			strxml = strxml.substring(strxml.indexOf("<?xml"), strxml.length());
			cmsLog.info(strxml);
			String defcatcode = "";
			List<ProgListDetail> plds = new ArrayList<ProgListDetail>();

			// 将String转成parse可以识别的InputSource
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader sr = new StringReader(strxml);
			InputSource is = new InputSource(sr);
			Document doc = builder.parse(is);
			// Document doc = DocumentHelper.parseText(strxml);

			doc.normalize();

			NodeList cells = doc.getElementsByTagName("column");
			for (int i = 0; i < cells.getLength(); i++) {
				Node cell = cells.item(i);
				Element cellattr = (Element) cells.item(i);

				String str = "";
				if (cellattr.hasAttribute("DEFCATCODE")) // 判断节点有tag属性
				{
					defcatcode = cell.getAttributes()
							.getNamedItem("DEFCATCODE").getNodeValue();
				}
			}

			cells = doc.getElementsByTagName("ProgListDetail");
			for (int i = 0; i < cells.getLength(); i++) {
				Node cell = cells.item(i);
				Element cellattr = (Element) cells.item(i);

				ProgListDetail pld = new ProgListDetail();

				String str = "";
				if (cellattr.hasAttribute("VALID_FROM")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("VALID_FROM")
							.getNodeValue();
					if (str != null && !str.equalsIgnoreCase(""))
						pld.setValidFrom(fileoper.convertStringToDate(str,
								"yyyy-MM-dd HH:mm:ss"));
				}
				if (cellattr.hasAttribute("VALID_TO")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("VALID_TO")
							.getNodeValue();
					if (str != null && !str.equalsIgnoreCase(""))
						pld.setValidTo(fileoper.convertStringToDate(str,
								"yyyy-MM-dd HH:mm:ss"));
				}
				if (cellattr.hasAttribute("VALID_FLAG")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("VALID_FLAG")
							.getNodeValue();
					if (str != null && !str.equalsIgnoreCase(""))
						pld.setValidFlag(Long.valueOf(str));
				}
				// if(cellattr.hasAttribute("UPLOADUSER")) // 判断节点有tag属性
				// {
				// str =
				// cell.getAttributes().getNamedItem("UPLOADUSER").getNodeValue();
				// pld.setUploaduser(str.replaceAll("\u00A0", "\u0020"));
				// }
				if (cellattr.hasAttribute("UPLOADDATE")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("UPLOADDATE")
							.getNodeValue();
					if (str != null && !str.equalsIgnoreCase(""))
						pld.setUploaddate(fileoper.convertStringToDate(str,
								"yyyy-MM-dd HH:mm:ss"));
				}
				if (cellattr.hasAttribute("PROGPACKAGEID")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("PROGPACKAGEID")
							.getNodeValue();
					pld.setProductid(str.replaceAll("\u00A0", "\u0020"));
				}
				if (cellattr.hasAttribute("PRODUCTID")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("PRODUCTID")
							.getNodeValue();
					pld.setProductid(str.replaceAll("\u00A0", "\u0020"));
				}
				if (cellattr.hasAttribute("PRODUCTNAME")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("PRODUCTNAME")
							.getNodeValue();
					pld.setProductname(str.replaceAll("\u00A0", "\u0020"));
				}
				if (cellattr.hasAttribute("LNUM")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("LNUM")
							.getNodeValue();
					if (str != null && !str.equalsIgnoreCase(""))
						pld.setLnum(Long.valueOf(str));
				}
				if (cellattr.hasAttribute("SRVID")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("SRVID")
							.getNodeValue();
					pld.setSrvid(str.replaceAll("\u00A0", "\u0020"));
				}
				if (cellattr.hasAttribute("UPDATETIME")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("UPDATETIME")
							.getNodeValue();
					if (str != null && !str.equalsIgnoreCase(""))
						pld.setUpdatetime(fileoper.convertStringToDate(str,
								"yyyy-MM-dd HH:mm:ss"));
				}
				if (cellattr.hasAttribute("E_TITLE")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("E_TITLE")
							.getNodeValue();
					if (str == null || str.equalsIgnoreCase("")) {
						pld.setETitle("");
					} else {
						pld.setETitle(str.replaceAll("\u00A0", "\u0020"));
					}
				}
				if (cellattr.hasAttribute("DEFCATSEQ")) // 判断节点有tag属性
				{
					str = cell.getAttributes().getNamedItem("DEFCATSEQ")
							.getNodeValue();
					pld.setDefcatseq(str.replaceAll("\u00A0", "\u0020"));
				}

				plds.add(pld);
			}

			list.add(defcatcode);
			list.add(plds);
		} catch (Exception e) {
			cmsLog.error(e.getMessage());
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> getProgListDetailsByXml returns.");
		return list;
	}

	// 20100225 14:41
	// 获得数据导入迁移单目标路径
	private String getImportMigrationDest(
			IPackageFilesManager packageFilesManager,
			IAmsStorageDirManager amsstoragedirManager, AmsStorage sourceamsst,
			AmsStorageDir sourceamsstd, AmsStorageClass sourceamsstc,
			ProgPackage progPackage, String importDataPath, // 数据导入的源路径
			String progPackagePath, // 节目包目录名称
			String progListPath, // 栏目单目录名称
			String progListDate, // 编单日期，格式：yyyyMMdd000000
			String filecodeMigrationImport, // 迁移单xml的filecode
			String stclasscodeMigrationImport // 迁移单xml的存储体等级code
	) {
		cmsLog.debug("Cms -> CmsTransaction -> getImportMigrationDest...");

		String destPath = ""; // 迁移单的文件名字
		try {
			String destpathMigration = ""; // 迁移单的目标路径
			String xmlFilename = ""; // 迁移单的文件名字

			// xml内容字段
			String proglistId = progListDate; // 编单日期，总表主键20100222000000
			String requestId = ""; // 当前时间，不重复

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
			requestId = df.format(new Date());

			xmlFilename = proglistId + requestId + ".xml";

			String path = importDataPath;
			path = fileoper.checkPathFormatRear(path, '/');
			path += progListDate;
			path = fileoper.checkPathFormatRear(path, '/');
			path += progPackagePath;
			path = fileoper.checkPathFormatRear(path, '/');
			path += progPackage.getProductid();
			path = fileoper.checkPathFormatRear(path, '/');
			path += progPackage.getProductid() + ".xml";

			if (fileoper.checkSmbFileExist(path)) {
				// 获得迁移任务xml文件目标路径
				// 调用方法2，根据配置文件stclasscode、配置文件filecode，获得迁移单xml目标路径
				// 返回：List
				// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
				// 2 - List<Object[]>
				// (AmsStorage)Object[0]
				// (AmsStorageDir)Object[1]
				// (AmsStorageClass)Object[2]
				List xmldestpaths = packageFilesManager
						.getDestPathByFilecodeStclasscode(
								filecodeMigrationImport, // 迁移单xml的filecode
								stclasscodeMigrationImport // 迁移单的目标存储体等级code
						);

				if (xmldestpaths != null && xmldestpaths.size() >= 2) {
					destpathMigration = (String) xmldestpaths.get(0);
					if (destpathMigration == null
							|| destpathMigration.equalsIgnoreCase("")) {
						cmsLog.info("迁移单目标路径不存在。");
						destPath = "";
					} else {
						destpathMigration = fileoper.checkPathFormatRear(
								destpathMigration, '/');
						destPath = destpathMigration + xmlFilename;
						cmsLog.info("得到迁移单的目标路径。" + destPath);
					}
				} else {
					cmsLog.info("迁移单目标路径不存在。");
					destPath = "";
				}
			} else {
				cmsLog.info("节目包的xml文件不存在，返回失败。" + path);
				destPath = "";
			}
		} catch (Exception e) {
			cmsLog.error(e.getMessage());
			destPath = "";
		}

		cmsLog.debug("Cms -> CmsTransaction -> getImportMigrationDest returns.");
		return destPath;
	}

	// 20100225 14:50
	// 生成数据导入迁移单xml字符串
	private String getImportMigrationXml(
			IPackageFilesManager packageFilesManager,
			IAmsStorageDirManager amsstoragedirManager, AmsStorage sourceamsst,
			AmsStorageDir sourceamsstd, AmsStorageClass sourceamsstc,
			ProgPackage progPackage, String importDataPath, // 数据导入的源路径
			String progPackagePath, // 节目包目录名称
			String progListPath, // 栏目单目录名称
			String progListDate, // 编单日期，格式：yyyyMMdd000000
			String filecodeMigrationImport, // 迁移单xml的filecode
			String stclasscodeMigrationImport, // 迁移单xml的存储体等级code
			String destStclasscode, // 目标存储体等级code
			List<ProgramFiles> progfs, // 节目包的文件
			List<Integer> importfiles, List<String> filedates, String operator // 当前操作人员
	) {
		cmsLog.debug("Cms -> CmsTransaction -> getImportMigrationXml...");

		// 源文件路径：
		// smb://administrator:1@10.0.2.253/20100223000000/progpackage/PPVP20100209145124000062/PPVP20100209145124000062.xml
		// smb://administrator:1@10.0.2.253/20100223000000/progpackage/PPVP20100209145124000062/PRVN20100209091021000671001001.ts
		// smb://administrator:1@10.0.2.253/20100223000000/progpackage/PPVP20100209145124000062/PPVP20100209145124000062.xml

		int ret = -1;
		String strXml = ""; // 迁移单内容

		try {
			// xml内容字段
			String proglistId = progListDate; // 编单日期，总表主键20100222000000
			String requestId = ""; // 当前时间，不重复
			String createDate = "";
			String priorityDate = "";
			String type = "2"; // 0 - 上海； 1 - 北京； 2 - 数据导入@北京

			Date date = fileoper.convertStringToDate(progListDate,
					"yyyyMMddHHmmss");

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
			requestId = df.format(new Date());
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			createDate = df.format(new Date());
			priorityDate = df.format(date);

			String path = importDataPath;
			path = fileoper.checkPathFormatRear(path, '/');
			path += progListDate;
			path = fileoper.checkPathFormatRear(path, '/');
			path += progPackagePath;
			path = fileoper.checkPathFormatRear(path, '/');
			path += progPackage.getProductid();
			path = fileoper.checkPathFormatRear(path, '/');
			path += progPackage.getProductid() + ".xml";

			if (fileoper.checkSmbFileExist(path)) {
				int count = 0;

				strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
				strXml += "<Migration>\r\n";
				strXml += "<Distribution ProglistId=\"";
				strXml += proglistId;
				strXml += "\" RequestId=\"";
				strXml += requestId;
				strXml += "\" CreateDate=\"";
				strXml += createDate;
				strXml += "\" Type=\"";
				strXml += type;
				strXml += "\">\r\n";
				for (int i = 0; i < progfs.size(); i++) {
					ProgramFiles programFiles = (ProgramFiles) progfs.get(i);
					int importfile = importfiles.get(i);

					cmsLog.info("准备处理节目包的文件...");
					cmsLog.info("文件ID：" + programFiles.getProgfileid());
					cmsLog.info("文件名：" + programFiles.getFilename());

					if (importfile != 1) {
						cmsLog.info("文件迁移标识表示文件不需要迁移，不操作，跳过...");
						continue;
					}

					cmsLog.info("文件迁移标识表示文件需要迁移，准备加入迁移任务字符串...");

					AmsStorage destamsst = null;
					AmsStorageDir destamsstd = null;
					AmsStorageClass destamsstc = null;

					String filedate = filedates.get(i);
					String filepath = "";
					String sourcepath = "";
					String destpath = "";

					filepath = progPackage.getProductid().substring(0, 10);
					filepath += "/" + progPackage.getProductid();

					// 获得迁移文件的源路径
					// smb://administrator:1@10.0.2.253/20100223000000/progpackage/PPVP20100209145124000062/PRVN20100209091021000671001001.ts
					cmsLog.info("获得迁移文件的源路径...");
					sourcepath = "/";
					if (sourceamsst.getMappath() != null
							&& !sourceamsst.getMappath().equalsIgnoreCase("")
							&& !sourceamsst.getMappath().equalsIgnoreCase("/")) {
						sourcepath += sourceamsst.getMappath();
						sourcepath = fileoper.checkPathFormatRear(sourcepath,
								'/');
					}
					if (sourceamsstd.getStoragedirname() != null
							&& !sourceamsstd.getStoragedirname()
									.equalsIgnoreCase("")
							&& !sourceamsstd.getStoragedirname()
									.equalsIgnoreCase("/")) {
						sourcepath += sourceamsstd.getStoragedirname();
						sourcepath = fileoper.checkPathFormatRear(sourcepath,
								'/');
					}
					sourcepath += progListDate;
					sourcepath = fileoper.checkPathFormatRear(sourcepath, '/');
					sourcepath += progPackagePath;
					sourcepath = fileoper.checkPathFormatRear(sourcepath, '/');
					sourcepath += progPackageAdditional
							+ progPackage.getProductid();
					sourcepath = fileoper.checkPathFormatRear(sourcepath, '/');
					sourcepath += programFiles.getFilename();
					sourcepath = sourcepath.replaceFirst("//", "/");

					// 获得迁移文件的目标路径。根据filecode和stclasscode，查询
					// 调用方法2，根据stdir.filecode、配置文件在上海的北京缓存库stclasscode，获取节目包主文件迁移的目标路径
					// 返回：List
					// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStorageDir)Object[1]
					// (AmsStorageClass)Object[2]
					cmsLog.info("获得迁移文件的目标路径...");
					List destpaths = packageFilesManager
							.getDestPathByFilecodeStclasscode(programFiles
									.getFilecode(), // 节目包主文件的filecode
									destStclasscode // 在上海的北京缓存库存储体等级code，从配置文件获取
							);
					if (destpaths != null && destpaths.size() >= 2) {
						destpath = (String) destpaths.get(0);
						List list1 = (List) destpaths.get(1);
						Object[] destrows = (Object[]) list1.get(0);
						destamsst = (AmsStorage) destrows[0];
						destamsstd = (AmsStorageDir) destrows[1];
						destamsstc = (AmsStorageClass) destrows[2];

						if (destpath == null || destpath.equalsIgnoreCase("")) {
							cmsLog.warn("节目包下的主文件迁移到在上海的北京缓存库的目标路径不存在。文件ID："
									+ programFiles.getProgfileid());
							count = 0;
							cmsLog.warn("中止操作，不生成迁移任务。");
							break;
						} else {
							// 处理destpath，加上filepath和文件名
							destpath = destpath.replace('\\', '/');
							destpath = fileoper.checkPathFormatRear(destpath,
									'/');
							if (filepath != null
									&& !filepath.equalsIgnoreCase("")) {
								destpath += filepath;
							}
							destpath = destpath.replace('\\', '/');
							destpath = fileoper.checkPathFormatRear(destpath,
									'/');
							destpath += programFiles.getFilename();

							// 处理路径格式，去掉头
							// smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
							// /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
							// sourcepath =
							// sourcepath.substring(sourcepath.indexOf("/",
							// 6),sourcepath.length());
							destpath = destpath.substring(destpath.indexOf("/",
									6), destpath.length());

							// 2.5 - 把节目文件加入迁移单中
							strXml += "<Transfer-Entity SourceStorageClass=\"";
							strXml += sourceamsstc.getStclasscode();
							strXml += "\" SourceFileType=\"";
							strXml += programFiles.getFiletypeid();
							strXml += "\" DesStorageClass=\"";
							strXml += destStclasscode;// destamsstc.getStclasscode();
							strXml += "\" PriorityDate=\"";
							strXml += priorityDate;
							strXml += "\" SourceProgTitle=\"";
							strXml += progPackage.getProductname();
							strXml += "\" SourceFileName=\"";
							strXml += programFiles.getFilename();
							strXml += "\" SourceFileId=\"";
							strXml += programFiles.getProgfileid();
							strXml += "\" SourceProgid=\"";
							strXml += progPackage.getProductid();
							strXml += "\" IsCover=\"";
							strXml += "N";
							strXml += "\">\r\n";

							// 源文件
							strXml += "<Source Type=\"";
							strXml += sourceamsst.getStorageaccstype();
							strXml += "\" SourceDirId=\"";
							strXml += sourceamsstd.getStdirglobalid();
							strXml += "\" Hostname=\"";
							strXml += sourceamsst.getStorageip();
							strXml += "\" Port=\"";
							strXml += "";
							strXml += "\" Username=\"";
							if (sourceamsst.getLoginname() != null)
								strXml += sourceamsst.getLoginname();
							strXml += "\" SourcestorageId=\"";
							strXml += sourceamsst.getStglobalid();
							strXml += "\" Password=\"";
							if (sourceamsst.getLoginpwd() != null)
								strXml += sourceamsst.getLoginpwd();
							strXml += "\" FileDate=\"";
							strXml += filedate;
							strXml += "\" VariableFilePath=\"";
							strXml += filepath;
							strXml += "\">\r\n";
							strXml += "<File>";
							strXml += sourcepath;
							strXml += "</File>\r\n";
							strXml += "</Source>\r\n";

							// 目标文件
							strXml += "<Destination Type=\"";
							strXml += destamsst.getStorageaccstype();
							strXml += "\" DesStorageDirId=\"";
							strXml += destamsstd.getStdirglobalid();
							strXml += "\" Hostname=\"";
							strXml += destamsst.getStorageip();
							strXml += "\" Port=\"";
							strXml += "";
							strXml += "\" Username=\"";
							if (sourceamsst.getLoginname() != null)
								strXml += destamsst.getLoginname();
							strXml += "\" DesStorageId=\"";
							strXml += destamsst.getStglobalid();
							strXml += "\" Password=\"";
							if (sourceamsst.getLoginpwd() != null)
								strXml += destamsst.getLoginpwd();
							strXml += "\" FileDate=\"";
							strXml += filedate;
							strXml += "\" VariableFilePath=\"";
							strXml += filepath;
							strXml += "\">\r\n";
							strXml += "<File>";
							strXml += destpath; // 
							strXml += "</File>\r\n";
							strXml += "</Destination>\r\n";

							strXml += "</Transfer-Entity>\r\n";

							count++;

							cmsLog.info("源Hostname - "
									+ sourceamsst.getStorageip());
							cmsLog.info("源File - " + sourcepath);
							cmsLog.info("目标Hostname - "
									+ destamsst.getStorageip());
							cmsLog.info("目标File - " + destpath);
							cmsLog.info("文件已加入到迁移任务字符串中。");
						}
					} else {
						cmsLog.warn("查找文件目标路径，返回为空。文件不加入到迁移任务字符串。");
						cmsLog.info("输入参数：" + programFiles.getFilecode() + ";"
								+ destStclasscode);
						count = 0;
						cmsLog.warn("中止操作，不生成迁移任务。");
						break;
					}
				}
				strXml += "</Distribution>\r\n";
				strXml += "</Migration>\r\n";

				if (count > 0) {
					cmsLog.info("迁移任务单中有" + count + "个文件需要迁移。");
				} else {
					cmsLog.info("迁移任务单没有需要迁移的文件，不需要生成迁移任务单。");
					strXml = "";
				}
			} else {
				cmsLog.warn("节目包的xml文件不存在，返回失败。" + path);
				strXml = "";
			}
		} catch (Exception e) {
			cmsLog.error("异常：" + e.getMessage());
			strXml = "";
		}
		cmsLog.debug("Cms -> CmsTransaction -> getImportMigrationXml returns.");
		return strXml;
	}

	// 20100222 21:34
	// 为数据导入的节目包下迁移单
	private int generateMigrationForImport(
			IPackageFilesManager packageFilesManager,
			IAmsStorageDirManager amsstoragedirManager, AmsStorage sourceamsst,
			AmsStorageDir sourceamsstd, AmsStorageClass sourceamsstc,
			ProgPackage progPackage, String importDataPath, // 数据导入的源路径
			String progPackagePath, // 节目包目录名称
			String progListPath, // 栏目单目录名称
			String progListDate, // 编单日期，格式：yyyyMMdd000000
			String filecodeMigrationImport, // 迁移单xml的filecode
			String stclasscodeMigrationImport, // 迁移单xml的存储体等级code
			String destStclasscode, // 目标存储体等级code
			List<ProgramFiles> progfs, // 节目包的文件
			List<Integer> importfiles, List<String> filedates, String operator // 当前操作人员
	) {
		cmsLog.debug("Cms -> CmsTransaction -> generateMigrationForImport...");

		// 源文件路径：
		// smb://administrator:1@10.0.2.253/20100223000000/progpackage/PPVP20100209145124000062/PPVP20100209145124000062.xml
		// smb://administrator:1@10.0.2.253/20100223000000/progpackage/PPVP20100209145124000062/PRVN20100209091021000671001001.ts
		// smb://administrator:1@10.0.2.253/20100223000000/progpackage/PPVP20100209145124000062/PPVP20100209145124000062.xml

		int ret = -1;

		try {
			String strXml = ""; // 迁移单内容
			String destpathMigration = ""; // 迁移单的目标路径
			String xmlFilename = ""; // 迁移单的文件名字

			// xml内容字段
			String proglistId = progListDate; // 编单日期，总表主键20100222000000
			String requestId = ""; // 当前时间，不重复
			String createDate = "";
			String priorityDate = "";
			String type = "2"; // 0 - 上海； 1 - 北京； 2 - 数据导入@北京

			Date date = fileoper.convertStringToDate(progListDate,
					"yyyyMMddHHmmss");

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
			requestId = df.format(new Date());
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			createDate = df.format(new Date());
			priorityDate = df.format(date);
			xmlFilename = proglistId + requestId + ".xml";

			String path = importDataPath;
			path = fileoper.checkPathFormatRear(path, '/');
			path += progListDate;
			path = fileoper.checkPathFormatRear(path, '/');
			path += progPackagePath;
			path = fileoper.checkPathFormatRear(path, '/');
			path += progPackage.getProductid();
			path = fileoper.checkPathFormatRear(path, '/');
			path += progPackage.getProductid() + ".xml";

			if (fileoper.checkSmbFileExist(path)) {
				// 获得迁移任务xml文件目标路径
				// 调用方法2，根据配置文件stclasscode、配置文件filecode，获得迁移单xml目标路径
				// 返回：List
				// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
				// 2 - List<Object[]>
				// (AmsStorage)Object[0]
				// (AmsStorageDir)Object[1]
				// (AmsStorageClass)Object[2]
				List xmldestpaths = packageFilesManager
						.getDestPathByFilecodeStclasscode(
								filecodeMigrationImport, // 迁移单xml的filecode
								stclasscodeMigrationImport // 迁移单的目标存储体等级code
						);

				if (xmldestpaths != null && xmldestpaths.size() >= 2) {
					destpathMigration = (String) xmldestpaths.get(0);
					if (destpathMigration == null
							|| destpathMigration.equalsIgnoreCase("")) {
						cmsLog.info("迁移单目标路径不存在。");
						return 1;
					} else {
						destpathMigration = fileoper.checkPathFormatRear(
								destpathMigration, '/');
						cmsLog.info("得到迁移单的目标路径。" + destpathMigration);
					}
				} else {
					cmsLog.info("迁移单目标路径不存在。");
					return 1;
				}

				strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
				strXml += "<Migration>\r\n";
				strXml += "<Distribution ProglistId=\"";
				strXml += proglistId;
				strXml += "\" RequestId=\"";
				strXml += requestId;
				strXml += "\" CreateDate=\"";
				strXml += createDate;
				strXml += "\" Type=\"";
				strXml += type;
				strXml += "\">\r\n";
				for (int i = 0; i < progfs.size(); i++) {
					ProgramFiles programFiles = (ProgramFiles) progfs.get(i);
					int importfile = importfiles.get(i);

					cmsLog.error("处理节目包的文件，文件ID："
							+ programFiles.getProgfileid());

					if (importfile != 1) {
						cmsLog.error("节目包的文件不需要迁移，不操作，跳过...");
						continue;
					}

					AmsStorage destamsst = null;
					AmsStorageDir destamsstd = null;
					AmsStorageClass destamsstc = null;

					String filedate = filedates.get(i);
					String filepath = "";
					String sourcepath = "";
					String destpath = "";

					filepath = progPackage.getProductid().substring(0, 10);
					filepath += "/" + progPackage.getProductid();

					// smb://administrator:1@10.0.2.253/20100223000000/progpackage/PPVP20100209145124000062/PRVN20100209091021000671001001.ts
					sourcepath = "/";
					if (sourceamsst.getMappath() != null
							&& !sourceamsst.getMappath().equalsIgnoreCase("")
							&& !sourceamsst.getMappath().equalsIgnoreCase("/")) {
						sourcepath += sourceamsst.getMappath();
						sourcepath = fileoper.checkPathFormatRear(sourcepath,
								'/');
					}
					if (sourceamsstd.getStoragedirname() != null
							&& !sourceamsstd.getStoragedirname()
									.equalsIgnoreCase("")
							&& !sourceamsstd.getStoragedirname()
									.equalsIgnoreCase("/")) {
						sourcepath += sourceamsstd.getStoragedirname();
						sourcepath = fileoper.checkPathFormatRear(sourcepath,
								'/');
					}
					sourcepath += progListDate;
					sourcepath = fileoper.checkPathFormatRear(sourcepath, '/');
					sourcepath += progPackagePath;
					sourcepath = fileoper.checkPathFormatRear(sourcepath, '/');
					sourcepath += progPackage.getProductid();
					sourcepath = fileoper.checkPathFormatRear(sourcepath, '/');
					sourcepath += programFiles.getFilename();

					// 获得迁移文件的源路径。importDataPath + progListDate +
					// progPackagePath + progPackageId + 文件名
					String sourcePath = importDataPath;
					sourcePath = fileoper.checkPathFormatRear(sourcePath, '/');
					sourcePath += progListDate;
					sourcePath = fileoper.checkPathFormatRear(sourcePath, '/');
					sourcePath += progPackagePath;
					sourcePath = fileoper.checkPathFormatRear(sourcePath, '/');
					sourcePath += progPackage.getProductid();
					sourcePath = fileoper.checkPathFormatRear(sourcePath, '/');
					sourcePath += programFiles.getFilename();

					// 获得迁移文件的目标路径。根据filecode和stclasscode，查询
					// // 返回：List<Object[]>
					// // (AmsStorage)Object[0]
					// // (AmsStorageDir)Object[1]
					// // (AmsStorageClass)Object[2]
					// List dests =
					// amsstoragedirManager.getStorageStoragedirsByStclasscodeFilecode(
					// destStclasscode,
					// programFiles.getFilecode()
					// );
					// 调用方法2，根据stdir.filecode、配置文件在上海的北京缓存库stclasscode，获取节目包主文件迁移的目标路径
					// 返回：List
					// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStorageDir)Object[1]
					// (AmsStorageClass)Object[2]
					List destpaths = packageFilesManager
							.getDestPathByFilecodeStclasscode(programFiles
									.getFilecode(), // 节目包主文件的filecode
									destStclasscode // 在上海的北京缓存库存储体等级code，从配置文件获取
							);
					if (destpaths != null && destpaths.size() >= 2) {
						destpath = (String) destpaths.get(0);
						List list1 = (List) destpaths.get(1);
						Object[] destrows = (Object[]) list1.get(0);
						destamsst = (AmsStorage) destrows[0];
						destamsstd = (AmsStorageDir) destrows[1];
						destamsstc = (AmsStorageClass) destrows[2];

						if (destpath == null || destpath.equalsIgnoreCase("")) {
							cmsLog.error("节目包下的主文件迁移到在上海的北京缓存库的目标路径不存在。文件ID："
									+ programFiles.getProgfileid());
							// continue;
						} else {
							// 处理destpath，加上filepath和文件名
							destpath = destpath.replace('\\', '/');
							destpath = fileoper.checkPathFormatRear(destpath,
									'/');
							if (filepath != null
									&& !filepath.equalsIgnoreCase("")) {
								destpath += filepath;
							}
							destpath = destpath.replace('\\', '/');
							destpath = fileoper.checkPathFormatRear(destpath,
									'/');
							destpath += programFiles.getFilename();

							// 处理路径格式，去掉头
							// smb://administrator:1@172.23.19.213/broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
							// /broadcast/Video/PPVP201001/PPVP20100126180339000640/PRVE20100126180000000906001001.ts
							// sourcepath =
							// sourcepath.substring(sourcepath.indexOf("/",
							// 6),sourcepath.length());
							destpath = destpath.substring(destpath.indexOf("/",
									6), destpath.length());

							// 2.5 - 把节目文件加入迁移单中
							strXml += "<Transfer-Entity SourceStorageClass=\"";
							strXml += sourceamsstc.getStclasscode();
							strXml += "\" SourceFileType=\"";
							strXml += programFiles.getFiletypeid();
							strXml += "\" DesStorageClass=\"";
							strXml += destStclasscode;// destamsstc.getStclasscode();
							strXml += "\" PriorityDate=\"";
							strXml += priorityDate;
							strXml += "\" SourceProgTitle=\"";
							strXml += progPackage.getProductname();
							strXml += "\" SourceFileName=\"";
							strXml += programFiles.getFilename();
							strXml += "\" SourceFileId=\"";
							strXml += programFiles.getProgfileid();
							strXml += "\" SourceProgid=\"";
							strXml += progPackage.getProductid();
							strXml += "\" IsCover=\"";
							strXml += "N";
							strXml += "\">\r\n";

							// 源文件
							strXml += "<Source Type=\"";
							strXml += sourceamsst.getStorageaccstype();
							strXml += "\" SourceDirId=\"";
							strXml += sourceamsstd.getStdirglobalid();
							strXml += "\" Hostname=\"";
							strXml += sourceamsst.getStorageip();
							strXml += "\" Port=\"";
							strXml += "";
							strXml += "\" Username=\"";
							if (sourceamsst.getLoginname() != null)
								strXml += sourceamsst.getLoginname();
							strXml += "\" SourcestorageId=\"";
							strXml += sourceamsst.getStglobalid();
							strXml += "\" Password=\"";
							if (sourceamsst.getLoginpwd() != null)
								strXml += sourceamsst.getLoginpwd();
							strXml += "\" FileDate=\"";
							strXml += filedate;
							strXml += "\" VariableFilePath=\"";
							strXml += filepath;
							strXml += "\">\r\n";
							strXml += "<File>";
							strXml += sourcepath;
							strXml += "</File>\r\n";
							strXml += "</Source>\r\n";

							// 目标文件
							strXml += "<Destination Type=\"";
							strXml += destamsst.getStorageaccstype();
							strXml += "\" DesStorageDirId=\"";
							strXml += destamsstd.getStdirglobalid();
							strXml += "\" Hostname=\"";
							strXml += destamsst.getStorageip();
							strXml += "\" Port=\"";
							strXml += "";
							strXml += "\" Username=\"";
							if (sourceamsst.getLoginname() != null)
								strXml += destamsst.getLoginname();
							strXml += "\" DesStorageId=\"";
							strXml += destamsst.getStglobalid();
							strXml += "\" Password=\"";
							if (sourceamsst.getLoginpwd() != null)
								strXml += destamsst.getLoginpwd();
							strXml += "\" FileDate=\"";
							strXml += filedate;
							strXml += "\" VariableFilePath=\"";
							strXml += filepath;
							strXml += "\">\r\n";
							strXml += "<File>";
							strXml += destpath; // 
							strXml += "</File>\r\n";
							strXml += "</Destination>\r\n";

							strXml += "</Transfer-Entity>\r\n";
						}
					}
				}
				strXml += "</Distribution>\r\n";
				strXml += "</Migration>\r\n";

				// 生成迁移任务xml文件
				ret = fileoper.createSmbFile(destpathMigration + xmlFilename,
						strXml);
			} else {
				cmsLog.info("节目包的xml文件不存在，返回失败。" + path);
			}
		} catch (Exception e) {
			cmsLog.error(e.getMessage());
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> generateMigrationForImport returns.");
		return ret;
	}

	// 20100221 13:51
	// 数据导入，把上海导出的节目包和节目包文件信息，导入到北京的cms
	public CmsResultDto saveImportProgPackageFilesToBjCms(
			IProgPackageManager progPackageManager,
			IProgramFilesManager programFilesManager,
			IPackageFilesManager packageFilesManager,
			IAmsStorageDirManager amsstoragedirManager,
			IAmsStorageManager amsstorageManager,
			IAmsStorageClassManager amsstorageclassManager,
			String importDataStclasscode, // 数据导入的存储体等级code
			String importDataFilecode, // 数据导入的源路径
			String destStclasscode, // 目标存储体等级code
			String filecodeMigrationImport, // 迁移单xml的filecode
			String stclasscodeMigrationImport, // 迁移单xml的存储体等级code
			String progPackagePath, // 节目包目录名称
			String progListPath, // 栏目单目录名称
			String operator // 当前操作人员
	) {
		// 流程：
		// 1 - 从配置文件，获得数据导出导入的节目包的源路径
		// 2 - 获得源路径下所有目录，期待结果格式:20100222000000
		// 3 - 获得编单日期目录(20100222000000)下的所有目录，期待结果：progpackage和proglist
		// 4 - 获得progpackage下的所有目录，期待结果格式：PPVP20100209110638000078
		// 5 - 获得PPVP20100209110638000078目录下的所有文件，期待结果：*.png,*.ts,*.xml
		// 6 - 读取xml文件内容，获得节目包ID、栏目单详细记录信息
		// 7 - 查询数据库，判断节目包是否已经存在
		// 7.1 - 存在，暂时不做处理
		// 7.2 - 不存在，需要新增节目包记录到数据库，继续
		// 7.2.1 - 根据xml，获得节目包下所有的文件信息，判断对应的文件是否都在目录(PPVP20100209110638000078)下
		// 7.2.1.1 - 不存在，不处理
		// 7.2.1.2 - 存在，继续
		// 7.2.1.2.1 -
		// 添加记录到：ProgPackage,ProgramFiles,PackageFiles,(节目包栏目关系表，节目包产品关系表)
		// 7.2.1.2.2 - 下迁移单，把节目包下的文件迁移到缓存库

		cmsLog
				.debug("Cms -> CmsTransaction -> saveImportProgPackageFilesToBjCms...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		List renames = new ArrayList();
		// try
		// {
		cmsLog.info("查询获得数据导入源目录路径...");
		String importDataPath = "";
		AmsStorage sourceamsst = null;
		AmsStorageDir sourceamsstd = null;
		AmsStorageClass sourceamsstc = null;
		// 返回：List<Object[]>
		// (AmsStorage)Object[0]
		// (AmsStorageDir)Object[1]
		// (AmsStorageClass)Object[2]
		List amsstdirs = amsstoragedirManager
				.getStorageStoragedirsByStclasscodeFilecode(
						importDataStclasscode, importDataFilecode);
		if (amsstdirs.size() > 0) {
			Object[] rows = (Object[]) amsstdirs.get(0);
			sourceamsst = (AmsStorage) rows[0];
			sourceamsstd = (AmsStorageDir) rows[1];
			sourceamsstc = (AmsStorageClass) rows[2];
		}
		importDataPath = sourceamsst.getStorageaccstype();
		importDataPath += "://";
		importDataPath += sourceamsst.getLoginname();
		importDataPath += ":";
		if (sourceamsst.getLoginpwd() != null)
			importDataPath += sourceamsst.getLoginpwd();
		importDataPath += "@";
		importDataPath += sourceamsst.getStorageip();
		// importDataPath = fileoper.checkPathFormatRear(importDataPath, '/');
		if (sourceamsst.getMappath() != null
				&& !sourceamsst.getMappath().equalsIgnoreCase("")
				&& !sourceamsst.getMappath().equalsIgnoreCase("/")) {
			importDataPath += sourceamsst.getMappath();
			importDataPath = importDataPath.replace('\\', '/');
			importDataPath = fileoper.checkPathFormatRear(importDataPath, '/');
		}
		if (sourceamsstd.getStoragedirname() != null
				&& !sourceamsstd.getStoragedirname().equalsIgnoreCase("")
				&& !sourceamsstd.getStoragedirname().equalsIgnoreCase("/")) {
			importDataPath += sourceamsstd.getStoragedirname();
			importDataPath = importDataPath.replace('\\', '/');
			importDataPath = fileoper.checkPathFormatRear(importDataPath, '/');
		}
		importDataPath = fileoper.checkPathFormatRear(importDataPath, '/');

		// 1 - 从配置文件，获得数据导出导入的节目包的源路径

		// 2 - 获得源路径下所有目录，期待结果格式:20100222000000
		cmsLog.info("获取路径下所有内容，" + importDataPath);
		SmbFile[] files = fileoper.listSmbFiles(importDataPath);
		cmsLog.info("共有" + files.length + "个内容。");
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				SmbFile file = files[i];
				String filename = file.getName(); // 目录名称，格式："20100222000000/"
				// "PAL.ts"
				String proglistPath = importDataPath + filename;
				proglistPath = fileoper.checkPathFormatRear(proglistPath, '/');

				cmsLog.info("处理第" + (i + 1) + "个内容...");
				cmsLog.info("内容名称：" + filename);

				if (filename.charAt(filename.length() - 1) != '/') {
					cmsLog.info("文件或文件夹名称：" + filename);
					cmsLog.info("不是文件夹，不处理，跳过...");
					continue;
				}

				filename = filename.substring(0, filename.lastIndexOf("/"));
				String progListDate = filename;
				cmsLog.info("文件夹名称：" + filename);
				if (filename.length() != 14) {
					cmsLog.info("文件夹名称格式不符合规则，不处理，跳过...");
					continue;
				}

				// 3 - 获得编单日期目录(20100222000000)下的所有目录，期待结果：progpackage和proglist
				cmsLog.info("获得该文件夹下所有内容...");
				// proglistPath = fileoper.checkPathFormatRear(proglistPath,
				// '/');
				SmbFile[] progfiles = fileoper.listSmbFiles(proglistPath);
				cmsLog.info("共有" + progfiles.length + "个内容。");
				for (int j = 0; j < progfiles.length; j++) {
					SmbFile progfile = progfiles[j];
					String progfilename = progfile.getName(); // 目录名称，格式："20100222000000/"
					// "PAL.ts"
					String progpackagePath = proglistPath + progfilename;
					progpackagePath = fileoper.checkPathFormatRear(
							progpackagePath, '/');

					cmsLog.info("处理第" + (j + 1) + "个内容...");
					cmsLog.info("内容名称：" + progfilename);

					if (progfilename.charAt(progfilename.length() - 1) != '/') {
						cmsLog.info("文件或文件夹名称：" + progfilename);
						cmsLog.info("不是文件夹，不处理，跳过...");
						continue;
					}
					progfilename = progfilename.substring(0, progfilename
							.lastIndexOf("/"));
					cmsLog.info("文件夹名称：" + progfilename);
					if (!progfilename.equalsIgnoreCase(progPackagePath)) {
						cmsLog.info("文件夹名称格式不符合规则，不处理，跳过..." + progPackagePath);
						continue;
					}

					// 4 - 获得progpackage下的所有目录，期待结果格式：PPVP20100209110638000078
					cmsLog.info("获得该文件夹下所有内容...");
					// progpackagePath =
					// fileoper.checkPathFormatRear(progpackagePath, '/');
					SmbFile[] progpackagefiles = fileoper
							.listSmbFiles(progpackagePath);
					cmsLog.info("共有" + progpackagefiles.length + "个内容。");
					for (int k = 0; k < progpackagefiles.length; k++) {
						SmbFile progpackagefile = progpackagefiles[k];
						String progpackagefilename = progpackagefile.getName(); // 目录名称，格式："20100222000000/"
						// "PAL.ts"
						String progpackagefilePath = progpackagePath
								+ progpackagefilename;
						progpackagefilePath = fileoper.checkPathFormatRear(
								progpackagefilePath, '/');

						cmsLog.info("处理第" + (k + 1) + "个内容...");
						cmsLog.info("内容名称：" + progpackagefilename);

						if (progpackagefilename.charAt(progpackagefilename
								.length() - 1) != '/') {
							cmsLog.info("文件或文件夹名称：" + progpackagefilename);
							cmsLog.info("不是文件夹，不处理，跳过...");
							continue;
						}

						progpackagefilename = progpackagefilename.substring(0,
								progpackagefilename.lastIndexOf("/"));
						cmsLog.info("文件夹名称：" + progpackagefilename);
						if (progpackagefilename.length() != 24) {
							cmsLog.info("文件夹名称格式不符合规则，不处理，跳过...");
							continue;
						}
						{
							// 5 -
							// 获得PPVP20100209110638000078目录下的所有文件，期待结果：*.png,*.ts,*.xml
							cmsLog.info("获得该文件夹下所有内容...");
							SmbFile[] pprealfiles = fileoper
									.listSmbFiles(progpackagefilePath);
							cmsLog.info("共有" + pprealfiles.length + "个内容。");
							String xmlFilePath = "";
							for (int l = 0; l < pprealfiles.length; l++) {
								SmbFile pprealfile = pprealfiles[l];
								String pprealfilename = pprealfile.getName(); // 目录名称，格式："20100222000000/"
								// "PAL.ts"

								cmsLog.info("处理第" + (l + 1) + "个内容...");
								cmsLog.info("内容名称：" + pprealfilename);

								String[] strlist = pprealfilename.split("\\.");
								if (strlist.length < 2) {
									cmsLog.info("文件或文件夹名称：" + pprealfilename);
									cmsLog.info("文件名称格式不符合规则，不处理，跳过...");
									continue;
								}
								if (strlist[1].equalsIgnoreCase("xml")) {
									xmlFilePath = progpackagefilePath
											+ pprealfilename;
									cmsLog.info("得到节目包的xml文件，" + xmlFilePath);
									break;
								}
							}
							if (xmlFilePath == null
									|| xmlFilePath.equalsIgnoreCase("")) {
								cmsLog.info("未找到节目包的xml文件，不处理，跳过...");
								continue;
							}

							// 6 - 读取xml文件内容，获得节目包记录和文件记录信息
							cmsLog.info("得到节目包的xml文件，" + xmlFilePath);
							cmsLog.info("读取xml文件内容...");
							// 返回：List
							// 1 - ProgPackage
							// 2 - List<ProgramFiles>
							// 3 - List<PackageFiles>
							// 4 - List<Integer> // 文件导出标识，标识此次数据导出是否同时导出该文件，0 -
							// no ; 1 - yes
							// 5 - List<String> // 文件的filedate
							List list = getProgPackageFilesByXml(xmlFilePath);
							if (list.size() < 5) {
								cmsLog.info("xml内容不符合规则，不处理，跳过...");
								continue;
							}
							ProgPackage progPackage = (ProgPackage) list.get(0);
							List<ProgramFiles> progfs = (List<ProgramFiles>) list
									.get(1);
							List<PackageFiles> packfs = (List<PackageFiles>) list
									.get(2);
							List<Integer> importfiles = (List<Integer>) list
									.get(3);
							List<String> filedates = (List<String>) list.get(4);

							// 7 - 查询数据库，判断节目包是否已经存在
							ProgPackage oldpp = (ProgPackage) progPackageManager
									.getById(progPackage.getProductid());
							if (oldpp != null) {
								// 7.1 - 存在，暂时不做处理
								cmsLog.info("节目包已经存在，暂时不做处理。节目包ID："
										+ oldpp.getProductid());
							} else {
								// 7.2 - 不存在，需要新增节目包记录到数据库，继续
								cmsLog.info("节目包不存在，需要添加节目包记录到数据库...节目包ID："
										+ progPackage.getProductid());
								// 7.2.1 -
								// 根据xml，获得节目包下所有的文件信息，判断对应的文件是否都在目录(PPVP20100209110638000078)下
								// 文件路径：progpackagefilePath + 文件名
								cmsLog.info("判断节目包文件是否全部到位...");
								boolean allfileexist = true;
								cmsLog.info("共有" + progfs.size() + "个节目包文件。");
								for (int pf = 0; pf < progfs.size(); pf++) {
									ProgramFiles progf = progfs.get(pf);
									int importfile = importfiles.get(pf);
									String progfilePath = progpackagefilePath
											+ progf.getFilename();

									cmsLog.info("处理第" + (pf + 1) + "个节目包文件...");
									cmsLog
											.info("文件ID："
													+ progf.getProgfileid());
									cmsLog.info("文件名：" + progf.getFilename());

									if (importfile == 1) {
										cmsLog
												.info("节目包文件导入标识为”需要导入“，判断文件是否存在...");
										if (!fileoper
												.checkSmbFileExist(progfilePath)) {
											allfileexist = false;
											cmsLog.info("节目包文件缺失。");
											cmsLog.info("节目包文件名："
													+ progfilePath);
											break;
										}
									}
								}
								if (allfileexist == false) {
									// 7.2.1.1 - 不存在，不处理
									cmsLog.info("节目包文件缺失，不处理该节目包，跳过...");
									continue;
								} else {
									// 7.2.1.2 - 存在，继续
									String strOldFile = "";
									String newName = "";
									cmsLog.info("节目包文件全部到位，准备添加节目包记录到数据库...");

									// 7.2.1.2.1 -
									// 添加记录到：ProgPackage,ProgramFiles,PackageFiles,(节目包栏目关系表，节目包产品关系表)
									// cmsLog.warn("测试代码，需要修改...");
									progPackageManager.save(progPackage);
									cmsLog.info("节目包记录已经添加。节目包ID："
											+ progPackage.getProductid());
									cmsLog.info("准备添加节目包下的节目文件记录和节目包文件记录...");
									for (int pf = 0; pf < progfs.size(); pf++) {
										ProgramFiles progf = (ProgramFiles) progfs
												.get(pf);
										PackageFiles packf = (PackageFiles) packfs
												.get(pf);
										int importfile = (int) importfiles
												.get(pf);

										cmsLog
												.info("准备添加节目文件(ProgramFiles)记录...");
										cmsLog.info("节目包ID："
												+ progPackage.getProductid());
										cmsLog.info("节目文件ID："
												+ progf.getProgfileid());
										cmsLog.info("节目文件名："
												+ progf.getFilename());
										programFilesManager.save(progf);
										cmsLog.info("节目文件记录已经添加。返回节目文件ID："
												+ progf.getProgfileid());

										cmsLog
												.info("准备添加节目包文件(PackageFiles)记录...");
										packf.setProductid(progPackage
												.getProductid());
										packf.setProgfileid(progf
												.getProgfileid());
										packageFilesManager.save(packf);
										cmsLog.info("节目包文件记录已经添加。返回节目包文件ID："
												+ packf.getCmspackageFilesid());
									}

									// 7.2.1.2.2 - 下迁移单，把节目包下的文件迁移到缓存库
									String migXmlFile = "";
									String strMigXml = "";
									int ret = -1;
									cmsLog.info("获取迁移任务单目标路径...");
									migXmlFile = getImportMigrationDest(
											packageFilesManager,
											amsstoragedirManager, sourceamsst,
											sourceamsstd, sourceamsstc,
											progPackage, importDataPath, // 数据导入的源路径
											progPackagePath, // 节目包目录名称
											progListPath, // 栏目单目录名称
											progListDate, // 编单日期，格式：yyyyMMdd000000
											filecodeMigrationImport, // 迁移单xml的filecode
											stclasscodeMigrationImport // 迁移单xml的存储体等级code
									);
									cmsLog.info("迁移任务单目标路径 - " + migXmlFile);

									cmsLog.info("生成迁移任务单字符串...");
									strMigXml = getImportMigrationXml(
											packageFilesManager,
											amsstoragedirManager, sourceamsst,
											sourceamsstd, sourceamsstc,
											progPackage, importDataPath, // 数据导入的源路径
											progPackagePath, // 节目包目录名称
											progListPath, // 栏目单目录名称
											progListDate, // 编单日期，格式：yyyyMMdd000000
											filecodeMigrationImport, // 迁移单xml的filecode
											stclasscodeMigrationImport, // 迁移单xml的存储体等级code
											destStclasscode, // 目标存储体等级code
											progfs, // 节目包的文件
											importfiles, filedates, operator // 当前操作人员
									);
									cmsLog.info("生成迁移任务单字符串完毕。");

									if (migXmlFile != null
											&& !migXmlFile.equalsIgnoreCase("")) {
										// cmsLog.info("生成迁移任务单成功。");
										// progPackage.setDealstate((long)1);
										// cmsLog.warn("测试代码，需要修改...");
										// //
										// progPackageManager.update(progPackage);
										// cmsLog.info("更新节目包的处理状态为1。");

										// 修改节目包文件夹名字progpackagefile
										// progpackagefilename，表示已经处理过该节目包了
										cmsLog.info("准备修改节目包文件夹名称...");

										// progpackagefilename =
										// "__PPVP20100209110638000078";
										strOldFile = progpackagePath
												+ progpackagefilename;
										strOldFile = fileoper
												.checkPathFormatRear(
														strOldFile, '/');
										newName = progPackageAdditional
												+ progpackagefilename;
										newName = fileoper.checkPathFormatRear(
												newName, '/');
										cmsLog.info("原：" + strOldFile);
										cmsLog.info("新：" + newName);
										List<String> rename = new ArrayList<String>();
										rename.add(strOldFile);
										rename.add(newName);
										renames.add(rename);
										// ret =
										// fileoper.renameSmbFile(strOldFile,
										// newName);
										ret = 0;
										cmsLog.warn("这里暂时不修改节目包的文件夹名字。");
										if (ret == 0) {
											// cmsLog.info("节目包文件夹改名成功。");

											if (migXmlFile != null
													&& !migXmlFile
															.equalsIgnoreCase("")) {
												if (strMigXml != null
														&& !strMigXml
																.equalsIgnoreCase("")) {
													cmsLog
															.info("准备生成迁移任务单xml文件...");
													ret = fileoper
															.createSmbFile(
																	migXmlFile,
																	strMigXml);
													if (ret == 0) {
														cmsLog
																.info("生成迁移任务单xml文件成功。");

														// 0 - 未处理,1 - 处理,8 -
														// 失败,9 - 成功
														cmsLog
																.info("生成迁移任务单成功。");
														progPackage
																.setDealstate((long) 1);
														// cmsLog.warn("测试代码，需要修改...");
														progPackageManager
																.update(progPackage);
														cmsLog
																.info("节目包的处理状态更新为1。");
													} else {
														String str = "生成迁移任务单xml文件失败。";
														cmsLog.warn(str);
														progPackage
																.setDealstate((long) 8);
														// cmsLog.warn("测试代码，需要修改...");
														progPackageManager
																.update(progPackage);
														cmsLog
																.info("节目包的处理状态更新为8。");
														// throw new
														// RuntimeException(str);
													}

												} else {
													cmsLog
															.info("迁移任务单字符串为空，不需要生成迁移任务单xml文件。");
												}
											} else {
												String str = "迁移单目标目录为空，不生成迁移单xml文件。";
												cmsLog.warn(str);
												// throw new
												// RuntimeException(str);
											}
										} else {
											// String str =
											// "节目包文件夹改名失败，回滚数据库操作。";
											// cmsLog.info("不生成迁移任务单xml文件。");
											// cmsLog.warn(str);
											// throw new RuntimeException(str);
										}
									} else {
										String str = "迁移任务单目标目录为空，不生成迁移任务单。";
										cmsLog.warn(str);
										progPackage.setDealstate((long) 8);
										// cmsLog.warn("测试代码，需要修改...");
										progPackageManager.update(progPackage);
										cmsLog.info("更新节目包的处理状态为8。");

										// throw new RuntimeException(str);
									}
								}
							}
						}
					}
				}
			}
		} else {
			String str = "数据导入源目录中没有找到文件或文件夹，不操作。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}
		cmsResultDto.setResultObject(renames);
		// }
		// catch(Exception e)
		// {
		// cmsLog.error("异常：");
		// String str = e.getMessage();
		// cmsLog.error(str);
		// cmsResultDto.setResultCode(Long.valueOf(1));
		// cmsResultDto.setErrorMessage(str);
		// // return cmsResultDto;
		// }

		cmsLog
				.debug("Cms -> CmsTransaction -> saveImportProgPackageFilesToBjCms returns.");
		return cmsResultDto;
	}

	// 20100223 13:45
	// 数据导入，分析上海导出的xml，把节目包和编单信息导入到北京的cms
	public CmsResultDto saveImportPortalXmlToBjCms(
			IProgPackageManager progPackageManager,
			IAmsStorageDirManager amsstoragedirManager,
			IPortalColumnManager portalColumnManager,
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			IProgListDetailManager progListDetailManager,
			IPPColumnRelManager pPColumnRelManager,
			String importDataStclasscode, // 数据导入的存储体等级code
			String importDataFilecode, // 数据导入的源路径
			String progPackagePath, // 节目包目录名称
			String progListPath, // 栏目单目录名称
			String operator // 当前操作人员
	) {
		// 说明：
		// 每个xml对应一个栏目，
		// xml是以栏目code命名的，以日期为文件夹区分不同日期的栏目单，格式：yyyyMMdd000000

		// smb://administrator:1@10.0.2.253/20100223000000/proglist/dvd.xml

		// 流程：
		// 1 - 从配置文件，获得数据导出导入的栏目单xml的源路径，处理成功后的目标路径，处理失败后的目标路径
		// 2 - 从xml的源路径获取xml，循环
		// 3 - 分析xml
		// 4 - 判断栏目是否存在，栏目是否是非本地栏目(上海的栏目)，
		// 5 - 判断栏目单(以日期为单位)，判断流程、初始化编单分表、总表
		// 6 - 判断栏目单详细记录(栏目、节目包)是否已经存在，不存在则导入栏目单详细记录到数据库
		// 7 - 修改xml文件名字

		cmsLog.debug("Cms -> CmsTransaction -> saveImportPortalXmlToBjCms...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		try {
			String retword = "";

			// 1 - 从配置文件，获得数据导出导入的栏目单xml的源路径，处理成功后的目标路径，处理失败后的目标路径
			cmsLog.info("查询获得数据导入源目录路径...");

			String importDataPath = "";
			AmsStorage sourceamsst = null;
			AmsStorageDir sourceamsstd = null;
			AmsStorageClass sourceamsstc = null;
			// 返回：List<Object[]>
			// (AmsStorage)Object[0]
			// (AmsStorageDir)Object[1]
			// (AmsStorageClass)Object[2]
			List amsstdirs = amsstoragedirManager
					.getStorageStoragedirsByStclasscodeFilecode(
							importDataStclasscode, importDataFilecode);
			if (amsstdirs.size() > 0) {
				Object[] rows = (Object[]) amsstdirs.get(0);
				sourceamsst = (AmsStorage) rows[0];
				sourceamsstd = (AmsStorageDir) rows[1];
				sourceamsstc = (AmsStorageClass) rows[2];
			}
			importDataPath = sourceamsst.getStorageaccstype();
			importDataPath += "://";
			importDataPath += sourceamsst.getLoginname();
			importDataPath += ":";
			if (sourceamsst.getLoginpwd() != null)
				importDataPath += sourceamsst.getLoginpwd();
			importDataPath += "@";
			importDataPath += sourceamsst.getStorageip();
			importDataPath = fileoper.checkPathFormatRear(importDataPath, '/');
			String mappath = sourceamsst.getMappath();
			if (mappath != null && !mappath.equalsIgnoreCase("")
					&& !mappath.equalsIgnoreCase("/")) {
				importDataPath += mappath;
				importDataPath = importDataPath.replace('\\', '/');
				importDataPath = fileoper.checkPathFormatRear(importDataPath,
						'/');
			}
			if (sourceamsstd.getStoragedirname() != null
					&& !sourceamsstd.getStoragedirname().equalsIgnoreCase("")
					&& !sourceamsstd.getStoragedirname().equalsIgnoreCase("/")) {
				importDataPath += sourceamsstd.getStoragedirname();
				importDataPath = importDataPath.replace('\\', '/');
				importDataPath = fileoper.checkPathFormatRear(importDataPath,
						'/');
			}

			// 2 - 从xml的源路径获取xml，循环
			// 2 - 获得源路径下所有目录，期待结果格式:20100222000000
			cmsLog.info("获取路径下所有内容，" + importDataPath);
			SmbFile[] files = fileoper.listSmbFiles(importDataPath);
			if (files != null) {
				cmsLog.info("共有" + files.length + "个内容。");
				for (int i = 0; i < files.length; i++) {
					SmbFile file = files[i];
					String filename = file.getName(); // 目录名称，格式："20100222000000/"
					// "PAL.ts"
					String proglistPath = importDataPath + filename;
					proglistPath = fileoper.checkPathFormatRear(proglistPath,
							'/');

					cmsLog.info("处理第" + (i + 1) + "个内容...");
					cmsLog.info("名称：" + filename);

					String date = "";

					if (filename.charAt(filename.length() - 1) != '/') {
						cmsLog.info("文件或文件夹名称：" + filename);
						cmsLog.info("不是文件夹，不处理，跳过...");
						continue;
					}

					filename = filename.substring(0, filename.lastIndexOf("/"));
					String progListDate = filename;
					cmsLog.info("文件夹名称：" + filename);
					if (filename.length() != 14) {
						cmsLog.info("文件夹名称格式不符合规则，不处理，跳过...");
						continue;
					}

					Date d = fileoper.convertStringToDate(filename,
							"yyyyMMddHHmmss");
					date = fileoper.convertDateToString(d, "yyyy-MM-dd");

					// 3 -
					// 获得编单日期目录(20100222000000)下的所有目录，期待结果：progpackage和proglist
					cmsLog.info("获得该文件夹下所有内容...");
					SmbFile[] progfiles = fileoper.listSmbFiles(proglistPath);
					cmsLog.info("共有" + progfiles.length + "个内容。");
					for (int j = 0; j < progfiles.length; j++) {
						SmbFile progfile = progfiles[j];
						String progfilename = progfile.getName(); // 目录名称，格式："20100222000000/"
						// "PAL.ts"
						String progpackagePath = proglistPath + progfilename;
						progpackagePath = fileoper.checkPathFormatRear(
								progpackagePath, '/');

						cmsLog.info("处理第" + (j + 1) + "个内容...");
						cmsLog.info("名称：" + progfilename);

						if (progfilename.charAt(progfilename.length() - 1) != '/') {
							cmsLog.info("文件或文件夹名称：" + progfilename);
							cmsLog.info("不是文件夹，不处理，跳过...");
							continue;
						}
						progfilename = progfilename.substring(0, progfilename
								.lastIndexOf("/"));
						cmsLog.info("文件夹名称：" + progfilename);
						if (!progfilename.equalsIgnoreCase(progListPath)) {
							cmsLog.info("文件夹名称格式不符合规则，不处理，跳过...");
							continue;
						}

						// 4 - 获得proglist下的所有目录，期待结果格式：dvd.xml sejy.xml
						cmsLog.info("获得该文件夹下所有内容...");
						SmbFile[] proglistfiles = fileoper
								.listSmbFiles(progpackagePath);
						cmsLog.info("共有" + proglistfiles.length + "个内容。");
						for (int k = 0; k < proglistfiles.length; k++) {
							SmbFile proglistfile = proglistfiles[k];
							String proglistfilename = proglistfile.getName(); // 文件名称，格式："dvd.xml"
							// "sejy.xml"
							String progpackagefilePath = progpackagePath
									+ proglistfilename;
							progpackagefilePath = fileoper.checkPathFormatRear(
									progpackagefilePath, '/');

							cmsLog.info("处理第" + (k + 1) + "个内容...");
							cmsLog.info("名称：" + proglistfilename);

							String front = proglistfilename.substring(0,
									proglistAdditional.length());
							String rear = proglistfilename.substring(
									proglistfilename.lastIndexOf("."),
									proglistfilename.length());
							if (rear == null || !rear.equalsIgnoreCase(".xml")) {
								cmsLog.info("文件或文件夹名称：" + proglistfilename);
								cmsLog.info("不是xml文件，不处理，跳过...");
								continue;
							}

							cmsLog.info("文件名称：" + proglistfilename);
							if (front == null
							// || front.equalsIgnoreCase("")
							// || front.equalsIgnoreCase(additionalFile)
							) {
								cmsLog.info("文件名称格式不符合规则，不处理，跳过...");
								continue;
							} else {
								if (proglistAdditional != null
										&& !proglistAdditional
												.equalsIgnoreCase("")) {
									if (front
											.equalsIgnoreCase(proglistAdditional)) {
										cmsLog.info("文件名称格式不符合规则，不处理，跳过...");
										continue;
									}
								}
							}

							// 3 - 分析xml
							// 返回：
							// List
							// 0 - String
							// 1 - List<ProgListDetail>
							String defcatcode = "";
							PortalColumn portalColumn = null;
							List<ProgListDetail> plds = new ArrayList<ProgListDetail>();

							// 分析xml内容
							cmsLog.info("分析xml内容...");
							List list = getProgListDetailsByXml(progpackagefilePath);

							if (list != null && list.size() >= 2) {
								cmsLog.info("分析xml内容完毕。");
								defcatcode = (String) list.get(0);
								plds = (List<ProgListDetail>) list.get(1);
							} else {
								cmsLog.warn("分析xml失败，不处理，跳过...");
								continue;
							}

							// 判断分析的xml结果是否完整
							// ret = checkXmlContent(plds);

							// 4 - 判断栏目是否存在，栏目是否是非本地栏目(上海的栏目)，
							cmsLog.info("判断栏目是否存在，栏目code：" + defcatcode);
							List pcs = portalColumnManager.findByProperty(
									"defcatcode", defcatcode);
							cmsLog.info("共有" + pcs.size() + "个符合条件的栏目。");
							if (pcs.size() > 0) {
								portalColumn = (PortalColumn) pcs.get(0);
								cmsLog.info("取第1个栏目...");
								cmsLog.info("栏目ID："
										+ portalColumn.getColumnclassid());
								cmsLog.info("栏目名称："
										+ portalColumn.getColumnname());

								if (portalColumn.getValidFlag() == 0
										&& portalColumn.getCountnumb() == 1) {
									// 是本地且有效的栏目
									cmsLog.info("是有效的非本地栏目，准备添加栏目单详细记录到数据库...");
								} else {
									cmsLog.warn("不是有效的非本地栏目，不处理，跳过...");
									continue;
								}
							} else {
								cmsLog.warn("栏目不存在，不处理，跳过...");
								continue;
							}

							// 5 - 判断栏目单(以日期为单位)，判断流程、初始化编单分表、总表
							cmsLog.info("判断流程、初始化编单分表、总表...");
							saveCheckProgListMang(portalColumnManager,
									progListMangManager,
									progListMangDetailManager, date, "",
									operator);

							cmsLog.info("准备循环添加栏目单详细记录...");
							cmsLog.info("共有" + plds.size() + "个栏目单详细。");
							for (int p = 0; p < plds.size(); p++) {
								// 6 -
								// 判断栏目单详细记录(栏目、节目包、上线日期)是否已经存在，不存在则导入栏目单详细记录到数据库
								ProgListDetail pld = plds.get(p);

								cmsLog.info("判断栏目单详细记录完整性...");
								if (portalColumn.getColumnclassid() == null) {
									cmsLog.warn("栏目id为空，不操作，跳过...");
									continue;
								}
								if (pld.getProductid() == null
										|| pld.getProductid().equalsIgnoreCase(
												"")) {
									cmsLog.warn("栏目单详细的节目包id为空，不操作，跳过...");
									continue;
								}

								cmsLog.info("准备添加栏目单详细记录...");
								cmsLog.info("栏目ID："
										+ portalColumn.getColumnclassid());
								cmsLog.info("节目包ID：" + pld.getProductid());
								cmsLog.info("节目包名称：" + pld.getProductname());
								pld.setColumnclassid(portalColumn
										.getColumnclassid());
								pld.setDefcatseq(portalColumn.getDefcatseq());

								cmsLog.info("查询栏目单详细记录是否已经存在...");
								List oldplds = progListDetailManager
										.getProgListDetailsByProductidColumnclassid(
												pld.getProductid(), pld
														.getColumnclassid());
								boolean exist = false;
								for (int p1 = 0; p1 < oldplds.size(); p1++) {
									ProgListDetail oldpld = (ProgListDetail) oldplds
											.get(p1);

									if (oldpld.getValidFrom().compareTo(
											pld.getValidFrom()) == 0) {
										// 找到相同的记录
										cmsLog.info("栏目单详细已经存在，栏目单详细ID："
												+ oldpld.getProglistdetailid());
										exist = true;
										break;
									}
								}
								if (exist == false) {
									cmsLog.info("栏目单详细不存在，添加记录到数据库...");
									
									
									/**
									 * HuangBo update 2010年7月30日 19时49分
									 */
//									
//									ProgPackage progPackage = (ProgPackage) progPackageManager
//											.getById(pld.getProductid());
//									if (null != progPackage && -1 != progPackage.getState()) {
//										Calendar calendar = Calendar.getInstance();
//										calendar.setTime(pld.getValidFrom());
//										calendar.add(Calendar.MONTH, -1);
//										pld.setValidFrom(calendar.getTime());
//										
//										calendar.setTime(pld.getValidTo());
//										calendar.add(Calendar.MONTH, -1);
//										pld.setValidTo(calendar.getTime());
//									}
									progListDetailManager.save(pld);
									
									
									cmsLog.info("栏目单详细已经添加记录到数据库，栏目单详细ID："
											+ pld.getProglistdetailid());

									cmsLog
											.info("准备添加节目包与栏目关系(PPColumnRel)记录到数据库...");
									cmsLog
											.info("判断节目包与栏目关系(PPColumnRel)记录是否已经存在...");
									List pcrs = pPColumnRelManager
											.getPpColumnRelsByProductIdAndColumnclassid(
													pld.getProductid(), pld
															.getColumnclassid());
									if (pcrs != null && pcrs.size() > 0) {
										cmsLog
												.info("节目包与栏目关系(PPColumnRel)记录已经存在，不操作。");
									} else {
										PPColumnRel pcr = new PPColumnRel();
										pcr.setColumnclassid(pld
												.getColumnclassid());
										pcr.setProductid(pld.getProductid());
										pPColumnRelManager.save(pcr);
										cmsLog
												.info("节目包与栏目关系(PPColumnRel)记录已经保存，relid："
														+ pcr.getRelid());
									}
								} else {
									cmsLog.info("栏目单详细已经存在，不操作。");
								}
							}
							// 7 - 修改xml文件名字progpackagePath proglistfilename
							String oldname = progpackagePath + proglistfilename;
							String newname = proglistAdditional
									+ proglistfilename;
							cmsLog.info("准备重命名数据导入文件...");
							cmsLog.info("旧 - " + oldname);
							cmsLog.info("新 - " + newname);
							int ret = fileoper.renameSmbFile(oldname, newname);
							if (ret == 0) {
								// 成功
								cmsLog.info("处理导出编单xml文件成功，xml文件名字已经修改。"
										+ newname);
							} else {
								// 失败
								String str = "修改xml文件名字失败，回滚数据库操作。";
								cmsLog.warn(str);
								cmsResultDto.setResultCode(Long.valueOf(1));
								cmsResultDto.setErrorMessage(str);
								throw new RuntimeException(str);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			String str = e.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			throw new RuntimeException(str);
		}

		cmsLog
				.debug("Cms -> CmsTransaction -> saveImportPortalXmlToBjCms returns.");
		return cmsResultDto;
	}

	private int checkXmlContent(List<ProgListDetail> plds) {
		cmsLog.debug("Cms -> CmsTransaction -> checkXmlContent returns.");
		int ret = -1;

		if (plds == null) {
			ret = 1;
		} else {

		}

		cmsLog.debug("Cms -> CmsTransaction -> checkXmlContent returns.");
		return ret;
	}

	// ------------------------------------- PortalColumn
	// ---------------------------------------------------
	// 20100131 14:54
	// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
	private String convertDateToScheduleDate(String date) {
		String[] strl = date.split("-");
		String newDate = "";
		for (int i = 0; i < strl.length; i++) {
			if (strl[i] != null && strl[i] != "") {
				newDate += strl[i];
			}
		}
		return newDate + "000000";
	}

	// 20100131 14:56
	// 查询节目包对应的产品列表（CA产品）
	private String getCaProductListOfProgPackage(
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			IProductCategoryManager productCategoryManager, String productid) {
		String keyids = "";
		try {
			List ppSrvPdtRels = ppSrvPdtRelManager.findByProperty("productid",
					productid);
			for (int i = 0; i < ppSrvPdtRels.size(); i++) {
				PpSrvPdtRel ppSrvPdtRel = (PpSrvPdtRel) ppSrvPdtRels.get(i);
				ProductCategory productCategory = (ProductCategory) productCategoryManager
						.getById(ppSrvPdtRel.getProductCategoryId().toString());
				if (productCategory.getCode() != null) {
					keyids += productCategory.getCode();
					keyids += ",";
				}
			}
			// 处理字符串，把keyids最后的","去掉
			if (keyids != null && !keyids.equalsIgnoreCase("")) {
				if (keyids.charAt(keyids.length() - 1) == ',') {
					keyids = keyids.substring(0, keyids.lastIndexOf(","));
				}
			}
		} catch (Exception e) {
			cmsLog
					.error("Cms -> CmsTransaction -> getCaProductListOfProgPackage，异常："
							+ e.getMessage());
			keyids = "0";
		}
		if (keyids == null) {
			keyids = "";
		}
		return keyids;
	}

	// 添加节目包到栏目 ProgPackage PortalColumn PPColumnRel
	public CmsResultDto saveProgPackagesToPortalColumn(
			IPortalColumnManager portalColumnManager,
			IPPColumnRelManager pPColumnRelManager, List progPackages,
			String columnclassid) {
		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgPackagesToPortalColumn...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		// 1 - 判断栏目节点是否是叶子节点
		// 1.1 - 是，继续
		// 1.1.1 - 判断节目包是否属于栏目根节点
		// 1.1.1.1 - 属于根节点，删除根节点关系
		// 1.1.1.2 - 不属于根节点
		// 1.1.1.3 - 添加栏目关系，查询节目包与栏目是否已经存在关系记录
		// 1.1.1.3.1 - 如果存在，不操作
		// 1.1.1.3.2 - 如果不存在，添加
		// 1.2 - 不是，返回失败

		PortalColumn portalColumn = (PortalColumn) portalColumnManager
				.getById(columnclassid);
		// 1 - 判断栏目节点是否是叶子节点
		if ("Y".equalsIgnoreCase(portalColumn.getIsleaf())) {
			// 1.1 - 是，继续
			if (progPackages.size() > 0) {
				for (int i = 0; i < progPackages.size(); i++) {
					// 1.1.1 - 判断节目包是否属于栏目"其他"节点
					ProgPackage progPackage = (ProgPackage) progPackages.get(i);
					List pPColumnRels = pPColumnRelManager
							.getPpColumnRelsByProductIdAndColumnclassid(
									progPackage.getProductid(), "PC00000002");
					if (pPColumnRels.size() > 0) {
						// 1.1.1.1 - 属于根节点，删除节目包与栏目根节点关系
						PPColumnRel[] pPColumnRelRootObjects = new PPColumnRel[pPColumnRels
								.size()];
						for (int j = 0; j < pPColumnRels.size(); j++) {
							pPColumnRelRootObjects[j] = (PPColumnRel) pPColumnRels
									.get(j);
							cmsLog.info("Old relid:"
									+ ((PPColumnRel) pPColumnRels.get(j))
											.getRelid());
							cmsLog.info("Old Productid:"
									+ ((PPColumnRel) pPColumnRels.get(j))
											.getProductid());
						}
						cmsLog.info("删除节目包与服务根节点的关系记录。");
						pPColumnRelManager.delete(pPColumnRelRootObjects);
					}

					// 1.1.1.2 - 不属于根节点
					// 1.1.1.3 - 添加栏目关系，查询节目包与栏目是否已经存在关系记录
					List curPPColumnRels = pPColumnRelManager
							.getPpColumnRelsByProductIdAndColumnclassid(
									progPackage.getProductid(), columnclassid);
					if (curPPColumnRels.size() > 0) {
						// 1.1.1.3.1 - 如果存在，不操作
					} else {
						// 1.1.1.3.2 - 如果不存在，添加
						PPColumnRel pPColumnRel = new PPColumnRel();
						pPColumnRel.setProductid(progPackage.getProductid());
						pPColumnRel.setColumnclassid(columnclassid);
						pPColumnRel.setSrvid("");
						pPColumnRel.setInputtime(new Date());

						cmsLog.info("New ProductId:"
								+ progPackage.getProductid());
						cmsLog.info("New columnclassid:" + columnclassid);
						cmsLog.info("创建节目包与栏目的关系记录。");
						pPColumnRelManager.save(pPColumnRel);
					}
				}
				cmsResultDto.setResultCode(Long.valueOf(0));
			} else {
				String str = "节目包列表为空。";
				cmsLog.info(str);
				cmsResultDto.setResultCode(Long.valueOf(1));
				cmsResultDto.setErrorMessage(str);
			}
		} else {
			// 1.2 - 不是，返回失败
			String str = "所选择的栏目节点不是叶子节点，不能添加节目包。";
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgPackagesToPortalColumn returns.");
		return cmsResultDto;
	}

	// 删除节目包从栏目 ProgPackage PortalColumn
	public CmsResultDto deleteProgPackagesFromPortalColumn(
			IPortalColumnManager portalColumnManager,
			IPPColumnRelManager pPColumnRelManager, List progPackages,
			String columnclassid) {
		cmsLog
				.debug("Cms -> CmsTransaction -> deleteProgPackagesFromPortalColumn...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		if (progPackages.size() > 0) {
			// 1 - 查询删除栏目节目包关系
			// 2 - 查询节目包和栏目关系
			// 3 - 没有，添加节目包和栏目根节点关系
			// 4 - 有，判断关系数量
			// 5 - 如果唯一，不处理
			// 6 - 大于1，判断是否存在与栏目根节点关系，如果有，删除与根节点的关系

			for (int i = 0; i < progPackages.size(); i++) {
				ProgPackage progPackage = (ProgPackage) progPackages.get(i);

				// 1 - 查询删除栏目节目包关系
				List pPColumnRels = pPColumnRelManager
						.getPpColumnRelsByProductIdAndColumnclassid(progPackage
								.getProductid(), columnclassid);
				if (pPColumnRels.size() > 0) {
					PPColumnRel[] pPColumnRelObjects = new PPColumnRel[pPColumnRels
							.size()];
					for (int j = 0; j < pPColumnRels.size(); j++) {
						pPColumnRelObjects[j] = (PPColumnRel) pPColumnRels
								.get(j);
						cmsLog.info("Old relid:"
								+ ((PPColumnRel) pPColumnRels.get(j))
										.getRelid());
						cmsLog.info("Old ProductId:"
								+ ((PPColumnRel) pPColumnRels.get(j))
										.getProductid());
					}
					cmsLog.info("删除节目包与栏目节点关系记录。");
					pPColumnRelManager.delete(pPColumnRelObjects);
				}

				// 2 - 查询节目包和栏目关系
				List curPPColumnRels = pPColumnRelManager.findByProperty(
						"productid", progPackage.getProductid());
				if (curPPColumnRels.size() > 0) {
					// 4 - 有，判断关系数量
					if (curPPColumnRels.size() == 1) {
						// 5 - 如果唯一，不处理
						cmsLog.info("节目包与栏目节点关系唯一，无操作。");
					} else {
						// 6 - 大于1，判断是否存在与栏目根节点关系，如果有，删除与根节点的关系
						cmsLog.info("节目包与栏目节点关系不唯一。" + curPPColumnRels.size());
						for (int j = 0; j < curPPColumnRels.size(); j++) {
							PPColumnRel pPColumnRel = (PPColumnRel) curPPColumnRels
									.get(j);
							if (pPColumnRel.getColumnclassid()
									.equalsIgnoreCase("PC00000002")) {
								cmsLog.info("Old relid:"
										+ pPColumnRel.getRelid());
								cmsLog.info("Old ProductId:"
										+ pPColumnRel.getProductid());
								cmsLog.info("删除节目包与栏目根节点的关系记录。");
								pPColumnRelManager.deleteById(pPColumnRel
										.getRelid());
							}
						}
					}
				} else {
					// 3 - 没有，添加节目包和栏目根节点关系
					PPColumnRel pPColumnRel = new PPColumnRel();
					pPColumnRel.setProductid(progPackage.getProductid());
					pPColumnRel.setColumnclassid("PC00000002");
					pPColumnRel.setInputtime(new Date());
					cmsLog.info("创建节目包与栏目根节点的关系记录。");
					cmsLog.info("new ProductId:" + progPackage.getProductid());
					pPColumnRelManager.save(pPColumnRel);
				}
			}
		} else {
			cmsLog.info("节目包列表为空。");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("节目包列表为空。");
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> deleteProgPackagesFromPortalColumn returns.");
		return cmsResultDto;
	}

	// 保存（新建）栏目 PortalColumn
	public CmsResultDto savePortalColumn(
			IPortalColumnManager portalColumnManager,
			IPPColumnRelManager pPColumnRelManager, PortalColumn portalColumn) {
		// 0 - 查询判断新加节点的defcatcode，是否已经存在
		// 1 - 查询判断父节点，是否是叶子节点
		// 1.1 - 不是叶子节点，
		// 1.1.1 - 保存该栏目节点
		// 1.2 - 是叶子节点，
		// 1.2.1 - 删除栏目父节点与服务的配置关系记录
		// SrvColumn
		// 1.2.2 - 创建该栏目节点
		// 1.2.3 - 修改父节点下所有的节目包的columnclassid为新建节点columnclassid
		// PPColumnRel
		// 1.2.4 - 修改服务父节点为非叶子节点
		// PortalColumn

		cmsLog.debug("Cms -> CmsTransaction -> savePortalColumn...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		// 1 - 查询判断父节点，是否是叶子节点
		List parents = portalColumnManager.findByProperty("defcatcode",
				portalColumn.getParentsid());
		if (parents.size() > 0) {
			PortalColumn parent = (PortalColumn) parents.get(0);
			cmsLog.debug("父节点栏目ID：" + parent.getColumnclassid());
			portalColumn.setDefcatlevel(parent.getDefcatlevel() + 1);
			if (portalColumn.getInputtime() == null)
				portalColumn.setInputtime(new Date());
			portalColumn.setUpdatedate(new Date());
			portalColumn.setIsleaf("Y");
			portalColumn.setRootid(parent.getRootid());
			portalColumn.setDefcatseq(parent.getDefcatseq() + "\\"
					+ portalColumn.getDefcatcode());
			portalColumn.setSiteid("CS00000001");

			/**
			 * 不再多管闲事, 让她愿意顺序输入多少就多少. 不输就界面上默认为1
			 * HuangBo update by 2012年5月9日 16时41分
			 */
//			if (1 == portalColumn.getCountnumb()) {
//				cmsLog.debug("修改默认终端栏目显示顺序: 父栏目显示顺序*10 + 1");
//				String parentCountNumb = String.valueOf(parent.getCountnumb());
//				portalColumn.setCountnumb(Long.parseLong(parentCountNumb + 1));
//			}

			if ("N".equalsIgnoreCase(parent.getIsleaf())) {
				// 1.1 - 不是叶子节点，
				// 1.1.1 - 保存该服务节点
				cmsLog.info("栏目父节点不是叶子节点，创建该栏目。");
				portalColumn = (PortalColumn) portalColumnManager
						.save(portalColumn);
				cmsLog.info("新建栏目ID：" + portalColumn.getColumnclassid());
			} else {
				// 1.2 - 是叶子节点，
				// 1.2.1 - 删除父节点与服务的配置关系记录
				// SrvProgClass,SrvColumn,SrvProduct
				cmsLog.info("栏目父节点是叶子节点, 创建该栏目。");
				// deleteSrvColumnBySrvId(srvColumnManager,parent.getSrvid());
				// 1.2.2 - 创建该服务节点
				// try {
				portalColumn = (PortalColumn) portalColumnManager
						.save(portalColumn);
				// } catch (Exception e) {
				// cmsLog.error(e);
				// cmsResultDto.setResultCode(1L);
				// cmsResultDto.setErrorMessage("栏目添加失败!");
				// return cmsResultDto;
				// }
				cmsLog.debug("新建栏目ID：" + portalColumn.getColumnclassid());

				// 1.2.3 - 修改父节点下所有的节目包的srvid为新建节点srvid
				// ProgSrvRel
				List parentProgSrvRels = pPColumnRelManager.findByProperty(
						"columnclassid", parent.getColumnclassid());
				for (int i = 0; i < parentProgSrvRels.size(); i++) {
					PPColumnRel pPColumnRel = (PPColumnRel) parentProgSrvRels
							.get(i);
					cmsLog.info("修改父节点的节目包为新建节点的节目包。PPColumnRel.columnclassid:"
							+ pPColumnRel.getColumnclassid());
					pPColumnRel.setColumnclassid(portalColumn
							.getColumnclassid());
					pPColumnRelManager.update(pPColumnRel);
				}

				// 1.2.4 - 修改服务父节点为非叶子节点
				// CmsService
				cmsLog.info("修改父节点为非叶子节点。");
				parent.setIsleaf("N");
				// parent.setStyleid(0L);
				pPColumnRelManager.update(parent);
			}
			portalColumnManager.updateRootTime();
		} else {
			String str = "未查询到栏目父节点，不能创建栏目。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.debug("Cms -> CmsTransaction -> savePortalColumn returns.");
		return cmsResultDto;
	}

	// 删除栏目 PortalColumn

	public CmsResultDto deletePortalColumn(
			IPortalColumnManager portalColumnManager,
			ISrvColumnManager srvColumnManager,
			IPPColumnRelManager pPColumnRelManager,
			IProgListDetailManager progListDetailManager,
			IProgPackageManager progPackageManager, PortalColumn portalColumn) {
		cmsLog.debug("Cms -> CmsTransaction -> deletePortalColumn...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		// 1 - 检查该节点是否是叶子节点
		// 2 - 如果不是，直接返回失败
		// 3 - 如果是，
		// 3.1 - 判断该栏目节点在编单新表中是否有未完成的节目包，
		// 3.1.1 - 如果有，不能删除，返回
		// 3.1.2 - 如果没有，继续
		// 3.1.2.1 - 删除与服务的配置关系记录
		// 3.1.2.2 - 删除与节目包的关系
		// 3.1.2.3 - 删除该栏目节点
		// 3.1.2.4 - 判断父节点下是否有节点
		// 3.1.2.4.1 - 如果有，无操作
		// 3.1.2.4.2 - 如果没有，将父节点改成叶子节点

		// 1 - 检查该节点是否是叶子节点
		if (portalColumn == null) {
			cmsLog.info("该栏目节点不存在。" + portalColumn.getColumnclassid());
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("该栏目节点不存在。" + portalColumn.getColumnclassid());
		} else if ("N".equalsIgnoreCase(portalColumn.getIsleaf())) {
			// 2 - 如果不是，直接返回失败
			cmsLog.info("该栏目节点不是叶子节点，不能删除。" + portalColumn.getColumnclassid());
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("该栏目节点不是叶子节点，不能删除。" + portalColumn.getColumnclassid());
		} else {
			// 3 - 如果是，
			// 3.1 - 判断该栏目节点在编单细表中是否有未完成的节目包，
			// 3.1.1 - 如果有，不能删除，返回
			// 3.1.2 - 如果没有，继续
			// proglistdetail.STATE1 <> 9 and proglistdetail.columnclassid =
			// columnclassid
			// List progListDetails =
			// progListDetailManager.getProgListDetailsByColumnclassidAndState1NotEqual(columnclassid,
			// (long)9);
			List<Object[]> list = progListDetailManager.queryScheduleDateAndActionName(
					portalColumn.getColumnclassid());
			cmsLog.debug("栏目[ " + portalColumn.getColumnname() 
					+ " ]下编单未生成播发单节目包数量: " + list.size());
//			for (int i = 0; i < progListDetails.size(); i++) {
//				ProgListDetail progListDetail = (ProgListDetail) progListDetails
//						.get(i);
//				ProgPackage progPackage = (ProgPackage) progPackageManager
//						.getById(progListDetail.getProductid());
//				if (progPackage.getState() != 3) {
//					existNotFinished = true;
//					break;
//				}
//			}
//			if (0 < list.size()) {
//				
//			}
			
			if (0 < list.size() && 0 == portalColumn.getValidFlag()) {
				cmsLog.warn("栏目未停播并且栏目下有在栏目单里有未完成的节目。");
				cmsResultDto.setResultCode(Long.valueOf(1));
				cmsResultDto.setErrorMessage("栏目[ " + portalColumn.getColumnname() 
						+ " ]有在栏目单里有未完成的节目, \n\t如果确定要删除该栏目, 请先停播再删除该栏目!");
				
				if (cmsLog.isDebugEnabled()) {
					for (Object[] objects : list) {
						cmsLog.debug("栏目[ " + portalColumn.getColumnname() 
								+ " ]下编单[ " + String.valueOf(objects[0]) + " ]所处流程====> " 
								+ String.valueOf(objects[1]));
					}
				}
			} else {
				// 3.1.2.1 - 删除与服务的配置关系记录
				// SrvColumn,
				List cmsServices = new ArrayList();
				saveSrvColumnByColumnclassid(portalColumnManager,
						srvColumnManager, cmsServices, portalColumn.getColumnclassid());

				// 3.1.2.2 - 删除与节目包的关系
				// PPColumnRel
				List pPColumnRels = pPColumnRelManager.findByProperty(
						"columnclassid", portalColumn.getColumnclassid());
				if (pPColumnRels.size() > 0) {
					cmsLog.debug("删除栏目与节目包的关系记录。");
					PPColumnRel[] pPColumnRelObjects = (PPColumnRel[]) pPColumnRels
							.toArray(new PPColumnRel[pPColumnRels.size()]);
					pPColumnRelManager.delete(pPColumnRelObjects);
				}

				// 3.1.2.3 - 删除该栏目节点
				// PortalColumn
				List parents = portalColumnManager.findByProperty("defcatcode",
						portalColumn.getParentsid());
				if (parents.size() > 0) {
					for (int i = 0; i < parents.size(); i++) {
						PortalColumn parent = (PortalColumn) parents.get(i);
						// 3.1.2.4 - 判断父节点下是否有节点
						/**
						 * 修改是否存在子栏目判断方法, 增加子栏目是否有效
						 * HuangBo update by 2012年2月22日 22时40分
						 */
//						List children = portalColumnManager.findByProperty(
//								"parentsid", parent.getDefcatcode());
//						if (children.size() > 1) {
						if (portalColumnManager.isExistValidChild(parent.getDefcatcode())) {
							// 3.4.1 - 如果有，无操作
						} else {
							// 3.4.2 - 如果没有，将父节点改成叶子节点
							cmsLog.debug("将父节点改成叶子节点。");
							parent.setIsleaf("Y");
							portalColumnManager.update(parent);
						}
					}
				}
				cmsLog.debug("删除栏目记录。修改有效标识为删除");
				portalColumn = (PortalColumn) portalColumnManager.getById(portalColumn.getColumnclassid());
				portalColumn.setValidFlag(1L);
				portalColumnManager.update(portalColumn);
				portalColumnManager.updateRootTime();
				cmsResultDto.setResultCode(0L);
			}
		}
		cmsLog.debug("Cms -> CmsTransaction -> deletePortalColumn returns.");
		return cmsResultDto;
	}

	// 保存栏目与服务的配置关系 PortalColumn SrvColumn
	public CmsResultDto saveSrvColumnByColumnclassid(
			IPortalColumnManager portalColumnManager,
			ISrvColumnManager srvColumnManager, List cmsServices,
			String columnclassid) {
		cmsLog.debug("Cms -> CmsTransaction -> saveSrvColumnByColumnclassid...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		// 判断srvid是否存在
		PortalColumn portalColumn = (PortalColumn) portalColumnManager
				.getById(columnclassid.toString());

		if (portalColumn != null) {
			// 删除srvid下所有的oldSrvColumns
			List oldSrvColumns = srvColumnManager.findByProperty(
					"columnclassid", columnclassid);
			if (oldSrvColumns.size() > 0) {
				SrvColumn[] oldSrvColumnObjects = (SrvColumn[]) oldSrvColumns
						.toArray(new SrvColumn[oldSrvColumns.size()]);
				cmsLog.debug("删除原有栏目与服务的配置关系记录。");
				srvColumnManager.delete(oldSrvColumnObjects);
			}

			if (cmsServices.size() > 0) {
				// 保存SrvColumn
				SrvColumn[] newSrvColumnObjects = new SrvColumn[cmsServices
						.size()];

				for (int i = 0; i < cmsServices.size(); i++) {
					CmsService cmsService = (CmsService) cmsServices.get(i);
					SrvColumn srvColumn = new SrvColumn();

					srvColumn.setColumnclassid(columnclassid);
					srvColumn.setSrvid(cmsService.getSrvid());
					srvColumn.setSelectTag((long) 0);
					srvColumn.setInputtime(new Date());

					newSrvColumnObjects[i] = srvColumn;
				}

				cmsLog.debug("保存栏目与服务的配置关系记录。");
				srvColumnManager.save(newSrvColumnObjects);
			}
		} else {
			cmsLog.debug("栏目为空。。");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("栏目为空。");
		}
		cmsLog.debug("Cms -> CmsTransaction -> saveSrvColumnByColumnclassid returns.");
		return cmsResultDto;
	}

	// 保存栏目角色关系表
	public CmsResultDto savePortalRoleOperRel(
			IPortalRoleOperRelManager portalRoleOperRelManager,
			IRoleManager roleManager, IPortalColumnManager portalColumnManager,
			String roleid, PortalColumn portalColumn, String inputmanid) {
		// 所有叶子节点与角色的关系要保存

		cmsLog.debug("Cms -> CmsTransaction -> savePortalRoleOperRel...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		if (roleid == null || portalColumn == null) {
			cmsLog.info("roleid == null || portalRoleOperRels == null");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto
					.setErrorMessage("roleid == null || portalRoleOperRels == null");
			return cmsResultDto;
		}

		// 判断roleid和portalRoleOperRels是否合法
		Role role = (Role) roleManager.getById(roleid);
		if (role == null) {
			cmsLog.info("role == null");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("role == null");
			return cmsResultDto;
		}

		// 根据角色id和栏目，查询关系记录
		PortalColumn curPortalColumn = (PortalColumn) portalColumnManager
				.getById(portalColumn.getColumnclassid());
		if (curPortalColumn == null) {
			cmsLog.info("curPortalColumn == null");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("curPortalColumn == null");
		} else {
			// 判断该栏目是否是叶子节点
			if (curPortalColumn.getIsleaf().equalsIgnoreCase("Y")) {
				// 如果是叶子节点，
				// 判断关系是否已经存在
				List<PortalRoleOperRel> curPortalRoleOperRels = portalRoleOperRelManager
						.getPortalRoleOperRelsByRoleidAndColumnclassid(roleid,
								curPortalColumn.getColumnclassid());

				if (curPortalRoleOperRels.size() > 0) {
					// 如果存在，更新操作人员的id
					for (int i = 0; i < curPortalRoleOperRels.size(); i++) {
						PortalRoleOperRel portalRoleOperRel = (PortalRoleOperRel) curPortalRoleOperRels
								.get(i);
						portalRoleOperRel.setInputmanid(inputmanid);
						portalRoleOperRel.setInputtime(new Date());
						portalRoleOperRelManager.update(portalRoleOperRel);
						cmsLog.info("已更新角色新的关系。Relid:"
								+ portalRoleOperRel.getRelid());
					}
				} else {
					// 如果不存在，添加
					PortalRoleOperRel portalRoleOperRel = new PortalRoleOperRel();
					portalRoleOperRel.setColumnclassid(portalColumn
							.getColumnclassid());
					portalRoleOperRel.setRoleid(roleid);
					portalRoleOperRel.setInputmanid(inputmanid);
					portalRoleOperRel.setInputtime(new Date());
					portalRoleOperRelManager.save(portalRoleOperRel);
					cmsLog.info("已添加角色新的关系。Relid:"
							+ portalRoleOperRel.getRelid());
				}
			} else {
				// 如果是非叶子节点
				// 查询该节点下所有叶子节点，根据栏目的序列
				List leafs = portalColumnManager
						.getLeafPortalColumnsByDefcatseq(curPortalColumn
								.getDefcatseq());
				for (int i = 0; i < leafs.size(); i++) {
					PortalColumn leaf = (PortalColumn) leafs.get(i);

					// 添加叶子节点与角色的关系记录
					// 判断关系是否已经存在
					List<PortalRoleOperRel> curPortalRoleOperRels = portalRoleOperRelManager
							.getPortalRoleOperRelsByRoleidAndColumnclassid(
									roleid, leaf.getColumnclassid());

					if (curPortalRoleOperRels.size() > 0) {
						// 如果存在，更新操作人员的id
						for (int j = 0; j < curPortalRoleOperRels.size(); j++) {
							PortalRoleOperRel portalRoleOperRel = (PortalRoleOperRel) curPortalRoleOperRels
									.get(j);
							portalRoleOperRel.setInputmanid(inputmanid);
							portalRoleOperRel.setInputtime(new Date());
							portalRoleOperRelManager.update(portalRoleOperRel);
							cmsLog.info("已更新角色新的关系。Relid:"
									+ portalRoleOperRel.getRelid());
						}
					} else {
						// 如果不存在，添加
						PortalRoleOperRel portalRoleOperRel = new PortalRoleOperRel();
						portalRoleOperRel.setColumnclassid(leaf
								.getColumnclassid());
						portalRoleOperRel.setRoleid(roleid);
						portalRoleOperRel.setInputmanid(inputmanid);
						portalRoleOperRel.setInputtime(new Date());
						portalRoleOperRelManager.save(portalRoleOperRel);
						cmsLog.info("已添加角色新的关系。Relid:"
								+ portalRoleOperRel.getRelid());
					}
				}
			}
		}
		cmsLog.debug("Cms -> CmsTransaction -> savePortalRoleOperRel returns.");
		return cmsResultDto;
	}

	// 删除栏目角色关系表
	public CmsResultDto deletePortalRoleOperRel(
			IPortalRoleOperRelManager portalRoleOperRelManager,
			IRoleManager roleManager, IPortalColumnManager portalColumnManager,
			String roleid, PortalColumn portalColumn, String inputmanid) {
		cmsLog.debug("Cms -> CmsTransaction -> deletePortalRoleOperRel...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		if (roleid == null || portalColumn == null) {
			cmsLog.info("roleid == null || portalRoleOperRels == null");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto
					.setErrorMessage("roleid == null || portalRoleOperRels == null");
			return cmsResultDto;
		}

		// 判断roleid和portalRoleOperRels是否合法
		Role role = (Role) roleManager.getById(roleid);
		if (role == null) {
			cmsLog.info("role == null");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("roleid == null");
			return cmsResultDto;
		}

		// 根据角色id和栏目，查询关系记录
		PortalColumn curPortalColumn = (PortalColumn) portalColumnManager
				.getById(portalColumn.getColumnclassid());
		if (curPortalColumn == null) {
			cmsLog.info("curPortalColumn == null");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("curPortalColumn == null");
			return cmsResultDto;
		}
		// 删除角色已有的所有关系
		List<PortalRoleOperRel> oldPortalRoleOperRels = portalRoleOperRelManager
				.getPortalRoleOperRelsByRoleidAndColumnclassid(roleid,
						portalColumn.getColumnclassid());
		if (oldPortalRoleOperRels.size() > 0) {
			PortalRoleOperRel[] oldPortalRoleOperRelObjects = (PortalRoleOperRel[]) oldPortalRoleOperRels
					.toArray(new PortalRoleOperRel[oldPortalRoleOperRels.size()]);
			portalRoleOperRelManager.delete(oldPortalRoleOperRelObjects);
			cmsLog.info("已删除角色已有的关系。");
		} else {
			cmsLog.info("角色栏目的关系不存在。");
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage("角色栏目的关系不存在。");
		}
		cmsLog
				.debug("Cms -> CmsTransaction -> deletePortalRoleOperRel returns.");
		return cmsResultDto;
	}

	// 20100131 14:41
	// 生成本地栏目的发布文件
	public CmsResultDto saveGeneratePortalXmlForLocal(
			IPortalColumnManager portalColumnManager,
			IProgListDetailManager progListDetailManager,
			IPackageFilesManager packageFilesManager,
			IProgListMangManager progListMangManager,
			IProgListFileManager progListFileManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			IProductCategoryManager productCategoryManager,
			IProgListMangDetailManager progListMangDetailManager,
			IFlowActivityOrderManager flowActivityOrderManager,
			IBpmcManager bpmcManager, String filecodePortalXml,
			String stclasscode, String date, // 日期，格式：yyyy-MM-dd
			String progPackagePath, // 节目包目录名称
			String progListPath, // 栏目单目录名称
			String operator // 当前操作人员
	) {
		cmsLog
				.debug("Cms -> CmsTransaction -> saveGeneratePortalXmlForLocal...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
		String scheduleDate = convertDateToScheduleDate(date);

		// 根据scheduledate，查询TPROGLISTMANG，得到proglistmang是否是“发布”活动
		ProgListMang progListMang = (ProgListMang) progListMangManager
				.getById(scheduleDate);

		cmsLog.info("生成导出文件的编单日期：" + scheduleDate);

		if (progListMang.getIdAct().equalsIgnoreCase("FU00000079")) {
			cmsLog.info("编单活动为“发布”(FU00000079)，准备生成导出文件...");
			// 如果是发布活动，继续
			// 查询PortalColumn表，得到发布portalcolumn
			cmsLog.info("查询允许发布的栏目...");
			List pcs = portalColumnManager
					.findByProperty("ispublish", (long) 0);
			if (pcs != null && pcs.size() > 0) {
				cmsLog.info("允许发布的栏目数量：" + pcs.size());
				for (int i = 0; i < pcs.size(); i++) {
					// 需要发布的栏目，每个需要发布的栏目生成一个xml文件
					PortalColumn pc = (PortalColumn) pcs.get(i);

					cmsLog.info("处理第" + (i + 1) + "个栏目...");
					cmsLog.info("栏目ID：" + pc.getColumnclassid());
					cmsLog.info("栏目名称：" + pc.getColumnname());
					cmsLog.info("栏目code：" + pc.getDefcatcode());

					if (pc.getValidFlag() != 0 || pc.getCountnumb() != 0) {
						// 不是有效的本地栏目，忽略
						cmsLog.info("栏目不是有效的本地栏目，不操作，跳过...");
						continue;
					}

					cmsLog.info("栏目是有效的本地栏目...");

					// 如果发布
					cmsLog.info("准备生成导出xml字符串...");
					String portalXml;
					portalXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n";
					portalXml += "<root>\r\n";

					// 根据defcatseq，查询PortalColumn表，得到所有叶子节点portalcolumn
					cmsLog.info("查询栏目下所有的叶子节点栏目...");
					List leafPcs = portalColumnManager
							.getLeafPortalColumnsByDefcatseq(pc.getDefcatseq());
					cmsLog.info("叶子节点栏目数量：" + leafPcs.size());
					for (int j = 0; j < leafPcs.size(); j++) {
						// 需要发布的栏目下的叶子节点栏目
						PortalColumn leafPc = (PortalColumn) leafPcs.get(j);

						cmsLog.info("处理第" + (j + 1) + "个叶子节点栏目...");
						cmsLog.info("叶子节点栏目ID：" + leafPc.getColumnclassid());
						cmsLog.info("叶子节点栏目名称：" + leafPc.getColumnname());
						cmsLog.info("叶子节点栏目code：" + leafPc.getDefcatcode());

						if (leafPc.getValidFlag() != 0
								|| leafPc.getCountnumb() != 0) {
							// 不是有效的本地栏目，忽略
							cmsLog.info("叶子节点栏目不是有效的本地栏目，不操作，跳过...");
							continue;
						}

						// 根据scheduledate和portalcolumn，查询TPROGLISTDETAIL，得到proglistdetail和progpackage
						cmsLog.info("查询叶子节点栏目的栏目单详细(节目包)记录...");
						List retlist = progListDetailManager
								.getProgListDetailsByScheduledateAndColumnclassid(
										date, leafPc.getColumnclassid() // 怀疑这里参数有误，pc.getColumnclassid()
								);
						cmsLog.info("叶子节点栏目的栏目单详细(节目包)记录数量：" + retlist.size());
						for (int k = 0; k < retlist.size(); k++) {
							Object[] rows = (Object[]) retlist.get(k);
							ProgListDetail progListDetail = (ProgListDetail) rows[0];
							ProgPackage progPackage = (ProgPackage) rows[1];

							cmsLog.info("处理第" + (k + 1) + "个栏目单详细(节目包)...");
							cmsLog.info("栏目单详细ID："
									+ progListDetail.getProglistdetailid());
							cmsLog.info("节目包ID：" + progPackage.getProductid());
							cmsLog
									.info("节目包名称："
											+ progPackage.getProductname());

							// 查询节目包对应的产品列表（CA产品）
							String keyids = getCaProductListOfProgPackage(
									ppSrvPdtRelManager, productCategoryManager,
									progPackage.getProductid());
							progListDetail.setETitle(keyids);
							progListDetailManager.update(progListDetail);
							cmsLog.info("更新栏目单详细的keyid：" + keyids);
						}

						// 根据proglistdetail和progpackage，生成xml
						portalXml += "<column DEFCATCODE=\"";
						portalXml += leafPc.getDefcatcode();
						portalXml += "\" name=\"";
						portalXml += leafPc.getColumnname();
						portalXml += "\" COUNTNUMB=\"";
						portalXml += retlist.size();
						portalXml += "\" COUNTONEPAGE=\"";
						portalXml += leafPc.getCountonepage();
						portalXml += "\">\r\n";

						for (int k = 0; k < retlist.size(); k++) {
							Object[] rows = (Object[]) retlist.get(k);
							ProgListDetail progListDetail = (ProgListDetail) rows[0];
							ProgPackage progPackage = (ProgPackage) rows[1];

							cmsLog.info("处理第" + (k + 1) + "个栏目单详细(节目包)...");
							cmsLog.info("栏目单详细ID："
									+ progListDetail.getProglistdetailid());
							cmsLog.info("节目包ID：" + progPackage.getProductid());
							cmsLog
									.info("节目包名称："
											+ progPackage.getProductname());

							cmsLog.info("将节目包信息加入导出xml字符串...");

							portalXml += "<ProgPackage>\r\n";

							portalXml += "<ProgListDetail";
							portalXml += " PRODUCTID=\"";
							portalXml += progListDetail.getProductid();
							portalXml += "\" PRODUCTNAME=\"";
							portalXml += progListDetail.getProductname();
							portalXml += "\" E_TITLE=\"";
							if (progListDetail.getETitle() != null)
								portalXml += progListDetail.getETitle();
							portalXml += "\" DEFCATSEQ=\"";
							portalXml += progListDetail.getDefcatseq();
							portalXml += "\" VALID_FROM=\"";
							portalXml += fileoper.convertDateToString(
									progListDetail.getValidFrom(),
									"yyyy-MM-dd HH:mm:ss");
							portalXml += "\" VALID_TO=\"";
							portalXml += fileoper.convertDateToString(
									progListDetail.getValidTo(),
									"yyyy-MM-dd HH:mm:ss");
							portalXml += "\" VALID_FLAG=\"";
							portalXml += progListDetail.getValidFlag()
									.toString();
							portalXml += "\" UPLOADUSER=\"";
							portalXml += progListDetail.getUploaduser();
							portalXml += "\" UPLOADDATE=\"";
							portalXml += fileoper.convertDateToString(
									progListDetail.getUploaddate(),
									"yyyy-MM-dd HH:mm:ss");
							portalXml += "\"/>\r\n";

							String ppXml = progPackage.getPpxml();
							ppXml = ppXml
									.replaceFirst(
											"\\<\\?xml version=\"1.0\" encoding=\"UTF-8\" \\?\\>",
											"");
							ppXml = ppXml
									.replaceFirst(
											"\\<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?\\>",
											"");

							portalXml += ppXml;

							portalXml += "</ProgPackage>\r\n";

							cmsLog.info("节目包信息已经加入导出xml字符串。");
						}

						portalXml += "</column>\r\n";
					}
					portalXml += "</root>\r\n";
					cmsLog.info("导出xml字符串已经生成。");

					// 调用方法2，得到xml目前存储路径
					// 返回：List
					// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
					// 2 - List<Object[]>
					// (AmsStorage)Object[0]
					// (AmsStorageDir)Object[1]
					// (AmsStorageClass)Object[2]
					String strXmlFullPath = ""; // xml目标存放全路径，含文件名和后缀
					String strXmlPath = ""; // xml目标存放路径
					String strXmlName = ""; // xml名字
					String filepath = ""; // 节目包ID前10位
					cmsLog.info("查询导出xml文件存放目标路径...");
					List destpaths = packageFilesManager
							.getDestPathByFilecodeStclasscode(
									filecodePortalXml, stclasscode);
					if (destpaths == null || destpaths.size() < 2) {
						cmsLog.warn("获取导出xml文件(发布文件)目标存放路径失败。");
						return null;
					}
					strXmlPath = (String) destpaths.get(0);
					List rowlist = (List) destpaths.get(1);
					Object[] rows1 = (Object[]) rowlist.get(0);
					AmsStorage amsst = (AmsStorage) rows1[0];
					AmsStorageDir amsstdir = (AmsStorageDir) rows1[1];
					AmsStorageClass amsstc = (AmsStorageClass) rows1[2];

					// // 查询XML需要上传的目标地址
					// String strXmlPath = "";
					// String strXmlName = "";
					// String strXmlFullPath = "";
					// strXmlPath = getXmlPath("NearOnline", "xml");

					// /20100303000000/proglist/
					strXmlPath += scheduleDate;
					strXmlPath += "/";
					strXmlPath += progListPath;
					strXmlPath += "/";

					strXmlName = pc.getDefcatcode() + ".xml"; // 栏目code.xml
					strXmlFullPath = strXmlPath + strXmlName;
					cmsLog.info("导出xml文件(发布文件) - " + strXmlFullPath);

					// ????????????????????????????????????????????????????????????????????????????????????????
					// 找到原来文件的位置
					// 判断strXmlFullPath文件是否存在，
					// 如果存在，删除原来文件strXmlFullPath
					// deleteSmbFile(strXmlFullPath);

					// 生成XML文件，以节目包ID命名
					// 上传XML文件，？？是否上一步直接生成XML文件到上传的目标地址
					// if(fileoper.createSmbFile(strXmlFullPath, portalXml) !=
					// 0)
					// {
					// // 写xml文件失败
					// cmsLog.info("写xml文件失败。");
					// return null;
					// }
					// else
					// {
					// cmsLog.info("写xml文件成功，保存progListFile。");
					// cmsLog.info("这里没有把发布xml写到文件，而是记录到progListFile。");

					// 把旧记录改为无效状态
					// 返回：
					// List<Object[]>
					// (ProgListFile)Object[0]
					// (PortalColumn)Object[1]
					cmsLog.info("把发布文件旧记录改为无效状态...");
					List plfs = progListFileManager
							.getProgListFilesByDateFiletypeDefcatseq(date,
									(long) 0, pc.getDefcatseq());
					if (plfs != null && plfs.size() > 0) {
						cmsLog.info("发布文件旧记录数量：" + plfs.size());
						for (int k = 0; k < plfs.size(); k++) {
							Object[] rows = (Object[]) plfs.get(k);
							ProgListFile plf = (ProgListFile) rows[0];

							cmsLog.info("处理第" + (k + 1) + "个发布文件旧记录...");
							cmsLog.info("发布文件记录ID：" + plf.getColumnfileid());

							plf.setState1(Long.valueOf(1));
							progListFileManager.update(plf);
							cmsLog.info("发布文件记录状态已经更新为无效(1)。");
						}
					}

					// 保存portalXml到TPROGLISTFILE
					ProgListFile progListFile = new ProgListFile();
					progListFile.setColumnclassid(pc.getColumnclassid());
					progListFile.setScheduledate(scheduleDate);
					progListFile.setColumnxml(portalXml);
					progListFile.setFiletype(Long.valueOf(0));
					progListFile.setState1(Long.valueOf(0)); // 状态(0有效1无效)
					progListFile.setInputtime(new Date());
					progListFile.setInputmanid(operator);
					progListFile.setFilename(strXmlName);
					progListFileManager.save(progListFile);
					cmsLog.info("发布文件记录已经生成，发布文件记录ID："
							+ progListFile.getColumnfileid());

					// 发送栏目单分表记录
					for (int j = 0; j < leafPcs.size(); j++) {
						// 需要发布的栏目下的叶子节点栏目
						PortalColumn leafPc = (PortalColumn) leafPcs.get(j);

						if (leafPc.getValidFlag() != 0
								|| leafPc.getCountnumb() != 0) {
							// 不是有效的本地栏目，忽略
							continue;
						}

						// 发送栏目单分表到下一活动 FU00000081
						// sendLocalProgListMangDetails(date,
						// leafPc.getDefcatseq(), "FU00000079", "", "");

						// 20100209 15:24
						// 这里不修改栏目分表的活动，由“发送”按钮发送活动
						// String currentIdAct = "FU00000079";
						// //
						// 查询下一活动nextIdAct，条件：State2=P，FLOWACTIVITYIDPARENT=currentIdAct
						// List flowActivityOrders =
						// flowActivityOrderManager.getNextIdActsByCurrentIdActAndState2(
						// currentIdAct,
						// "P"
						// );
						//							
						// if(flowActivityOrders.size() > 0)
						// {
						// FlowActivityOrder flowActivityOrder =
						// (FlowActivityOrder)flowActivityOrders.get(0);
						//								
						// cmsResultDto = updateLocalProgListMangDetails(
						// progListMangManager,
						// progListMangDetailManager,
						// bpmcManager,
						// flowActivityOrderManager,
						// scheduleDate, // 主表的主键
						// leafPc.getDefcatseq(), // 栏目的代码序列
						// flowActivityOrder.getFlowactivityidchild(), // 下一活动编号
						// "", // 下一操作人员
						// "P", // 下一活动的性质：（新建N 回退R 顺序P 完成C）
						// currentIdAct, // 当前活动编号
						// operator, // 当前操作人员
						// "" // 备注
						// );
						// }

						// 根据scheduledate和portalcolumn，查询TPROGLISTDETAIL，得到proglistdetail和progpackage
						List retlist = progListDetailManager
								.getProgListDetailsByScheduledateAndColumnclassid(
										date, leafPc.getColumnclassid());
						for (int k = 0; k < retlist.size(); k++) {
							Object[] rows = (Object[]) retlist.get(k);
							ProgListDetail progListDetail = (ProgListDetail) rows[0];
							ProgPackage progPackage = (ProgPackage) rows[1];

							// 查询节目包对应的产品列表（CA产品）
							String keyids = getCaProductListOfProgPackage(
									ppSrvPdtRelManager, productCategoryManager,
									progPackage.getProductid());
							progListDetail.setETitle(keyids);
							// progListDetail.setState1((long)1);
							progListDetailManager.update(progListDetail);
						}
					}

					// 生成xml文件到目标目录
					cmsLog.info("准备生成导出文件...");
					cmsLog.info("目标路径 - " + strXmlFullPath);
					int ret = fileoper.createSmbFile(strXmlFullPath, portalXml);
					if (ret == 0) {
						String str = "生成数据导出xml文件成功。";
						cmsLog.info(str);
						cmsResultDto.setErrorMessage(str);
					} else {
						String str = "生成数据导出xml文件失败，回滚数据库操作。" + strXmlFullPath;
						cmsLog.warn(str);
						cmsResultDto.setResultCode(Long.valueOf(1));
						cmsResultDto.setErrorMessage(str);
						throw new RuntimeException(str);
					}
					// }
				}
			} else {
				if (pcs == null) {
					cmsLog.warn("查询结果为空。");
				} else if (pcs.size() <= 0) {
					cmsLog.info("允许发布的栏目数量：" + pcs.size());
				} else {
					cmsLog.warn("其他错误。");
				}
			}
		} else {
			// 如果不是发布活动，返回
			String str = "栏目单当前活动不是“发布”。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		}

		cmsLog
				.debug("Cms -> CmsTransaction -> saveGeneratePortalXmlForLocal returns.");
		return cmsResultDto;
	}

	// ------------------------------------- Encrypt 加扰
	// ---------------------------------------------------
	// 20100111 15:47
	// 生成加扰任务，并且修改栏目单详细中节目包状态
	public CmsResultDto saveEncryptTask(IEncryptListManager encryptListManager,
			IProgListDetailManager progListDetailManager,
			IProgPackageManager progPackageManager, EncryptList encryptList, // 加扰任务
			// List progListDetails // 栏目单详细
			ProgPackage progPackage) {
		cmsLog.debug("Cms -> CmsTransaction -> saveEncryptTask...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		if (encryptList == null || progPackage == null) {
			String str = "输入参数为空。";
			cmsLog.info(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}

		encryptListManager.save(encryptList);

		progPackage.setDealstate((long) 1); // 处理状态(0未处理1处理8失败9成功)
		progPackageManager.update(progPackage);
		cmsLog.info("节目包的处理状态修改为1，“处理”。节目包ID：" + progPackage.getProductid());

		// // 修改栏目单详细的节目包状态：加扰
		// for(int i = 0; i < progListDetails.size(); i++)
		// {
		// ProgListDetail progListDetail =
		// (ProgListDetail)progListDetails.get(i);
		// //// progListDetail.setState1((long)1); // 节目包状态（0导入1缓存库2加扰库3播控库）
		// // progListDetail.setDealstate((long)1); // 处理状态(0未处理1处理8失败9成功)
		// // progListDetailManager.update(progListDetail);
		//			
		// ProgPackage progPackage =
		// (ProgPackage)progPackageManager.getById(progListDetail.getProductid());
		// progPackage.setDealstate((long)1); // 处理状态(0未处理1处理8失败9成功)
		// progPackageManager.update(progPackage);
		// }
		cmsLog.info("Encryptid:" + encryptList.getEncryptid());
		cmsResultDto.setResultObject(encryptList);
		cmsLog.debug("Cms -> CmsTransaction -> saveEncryptTask returns.");
		return cmsResultDto;
	}

	// 20100131 11:50
	// 加扰任务完成处理接口，
	/**
	 * HuangBo Update 2010年8月24日 14时43分 
	 */
	public CmsResultDto saveFinishEncrypt(
			IProgListDetailManager progListDetailManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgramFilesManager programFilesManager,
			IPackageFilesManager packageFilesManager,
			IProgPackageManager progPackageManager,
			String productid, // 节目包id
			String stGlobalid, // 目标存储体ID
			String stDirGlobalid, // 目标存储体目录ID
			List storagePrgRels, String destStStorageClassCode,
			String stclasscodeNearOnline, String stclasscodeCaOnline,
			String stclasscodeOnline, String filecode, // 目标文件filecode
			String filePath, // 目标文件路径
			String remark, // 备注信息，包含：目标存储ID、目标目录ID、目标文件路径、文件位置表主键LIST
			String operatorid // 操作人员
	) {
		// 说明：
		// 如果是视频，save文件存放位置表记录
		// 如果是富媒体，压缩加扰后的文件夹，并save文件存放位置表记录
		// 最后更新节目包的状态和处理状态
		cmsLog.debug("Cms -> CmsTransaction -> saveFinishEncrypt...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		try {
			cmsLog.info("查询节目包，节目包ID：" + productid);
			ProgPackage progPackage = (ProgPackage) progPackageManager
					.getById(productid);
			if (progPackage == null) {
				// 返回失败
				String str = "节目包不存在，返回失败。节目包ID：" + productid;
				cmsLog.info(str);
				cmsResultDto.setResultCode(Long.valueOf(1));
				cmsResultDto.setErrorMessage(str);
				return cmsResultDto;
			}
			if (progPackage.getProgtype().equalsIgnoreCase("V")) {
				// 视频节目包
				cmsLog.info("节目包是视频类型...");
				for (int i = 0; i < storagePrgRels.size(); i++) {
					AmsStoragePrgRel aspr = (AmsStoragePrgRel) storagePrgRels
							.get(i);

					// 查询
					// 压缩源文件夹路径：加扰文件夹目标路径(存储体IP+存储体目录) + filePath + 文件ID
					ProgramFiles pf = (ProgramFiles) programFilesManager
							.getById(aspr.getProgfileid());
					if (pf != null) {
						// 添加记录，到文件存放位置表
						cmsLog.info("准备添加节目包文件的文件位置表记录...");
						AmsStoragePrgRel amsstprgrel = new AmsStoragePrgRel();
						amsstprgrel.setStglobalid(stGlobalid);
						amsstprgrel.setStdirglobalid(stDirGlobalid);
						amsstprgrel.setPrglobalid(productid); // 节目包ID
						amsstprgrel.setProgfileid(aspr.getProgfileid());
						amsstprgrel.setFtypeglobalid(aspr.getFtypeglobalid()); // filetype
						amsstprgrel.setFilename(aspr.getFilename());
						amsstprgrel.setFiledate(aspr.getFiledate());
						amsstprgrel.setFilepath(filePath);
						amsstprgrel.setUploadtime(new Date());
						amsstprgrel.setInputmanid(operatorid);
						amsstprgrel.setInputtime(new Date());
						amsstprgrel.setRemark(remark);

						amsstorageprgrelManager.save(amsstprgrel);
						cmsLog.info("节目包文件的文件位置表记录已经添加。文件位置ID："
								+ aspr.getRelid());

					} else {
						// 返回失败
						String str = "节目文件不存在，返回失败。文件：" + aspr.getProgfileid();
						cmsLog.warn(str);
						cmsResultDto.setResultCode(Long.valueOf(1));
						cmsResultDto.setErrorMessage(str);
						return cmsResultDto;
					}
				}
			} else if (progPackage.getProgtype().equalsIgnoreCase("R")) {
				// 富媒体，需要压缩
				cmsLog.info("节目包是富媒体类型， 不进行压缩...");
				for (int i = 0; i < storagePrgRels.size(); i++) {
					AmsStoragePrgRel aspr = (AmsStoragePrgRel) storagePrgRels
							.get(i);

					// 查询
					// 压缩源文件夹路径：加扰文件夹目标路径(存储体IP+存储体目录) + filePath + 文件ID
					cmsLog.info("查询压缩源文件夹的路径...");
					ProgramFiles pf = (ProgramFiles) programFilesManager
							.getById(aspr.getProgfileid());
					if (pf != null) {
						boolean success = true;
						// 如果是富媒体的主文件，需要压缩
						cmsLog.info("判断节目包下文件是否是主文件...文件ID："
								+ pf.getProgfileid());
//						if (1 == pf.getProgrank()) {
							// 返回：List
							// 1 - String 目标路径()
							// 格式："smb://hc:hc@172.23.19.66/公用/"
							// 2 - List<Object[]>
							// (AmsStorage)Object[0]
							// (AmsStorageDir)Object[1]
							// (AmsStorageClass)Object[2]
							cmsLog.info("是主文件，查询文件需要压缩的目标路径...");
//							List dests = packageFilesManager
//									.getDestPathByFilecodeStclasscode(filecode,
//											destStStorageClassCode);
//							if (dests != null && dests.size() >= 2) {
//								String zipdirSourcepath = ""; // (String)dests.get(0)
//								String zipDestpath = ""; // zipdirSourcepath
//
//								zipdirSourcepath = (String) dests.get(0);
//								zipdirSourcepath = zipdirSourcepath.replace(
//										'\\', '/');
//								zipdirSourcepath = fileoper
//										.checkPathFormatRear(zipdirSourcepath,
//												'/');
//								if (filePath != null
//										&& !filePath.equalsIgnoreCase("")) {
//									zipdirSourcepath += filePath;
//								}
//								zipdirSourcepath = zipdirSourcepath.replace(
//										'\\', '/');
//								zipdirSourcepath = fileoper
//										.checkPathFormatRear(zipdirSourcepath,
//												'/');
//								zipdirSourcepath += pf.getProgfileid();
//								zipdirSourcepath = zipdirSourcepath.replace(
//										'\\', '/');
//								zipdirSourcepath = fileoper
//										.checkPathFormatRear(zipdirSourcepath,
//												'/');
//
//								zipDestpath = (String) dests.get(0);
//								zipDestpath = zipDestpath.replace('\\', '/');
//								zipDestpath = fileoper.checkPathFormatRear(
//										zipDestpath, '/');
//								if (filePath != null
//										&& !filePath.equalsIgnoreCase("")) {
//									zipDestpath += filePath;
//								}
//								zipDestpath = zipDestpath.replace('\\', '/');
//								zipDestpath = fileoper.checkPathFormatRear(
//										zipDestpath, '/');
//								zipDestpath += pf.getProgfileid() + ".zip";
//
//								cmsLog.info("压缩源 - " + zipdirSourcepath);
//								cmsLog.info("压缩目标 - " + zipDestpath);
//
//								if (fileoper.zipSmbSinglePath(zipdirSourcepath,
//										zipDestpath) == 0) {
//									// 压缩富媒体成功
//									cmsLog.info("压缩节目包(富媒体)目录成功。");
//									success = true;
//								} else {
//									// 返回失败
//									String str = "压缩富媒体失败。";
//									cmsLog.warn(str);
//									cmsResultDto.setResultCode(Long.valueOf(1));
//									cmsResultDto.setErrorMessage(str);
//									// return cmsResultDto;
//									success = false;
//								}
//							} else {
//								// 返回失败
//								String str = "压缩富媒体目标路径为空。";
//								cmsLog.warn(str);
//								cmsResultDto.setResultCode(Long.valueOf(1));
//								cmsResultDto.setErrorMessage(str);
//								// return cmsResultDto;
//								success = false;
//							}
//						} else {
//							// 不是主文件，不需要压缩
//							cmsLog.info("节目文件不是主文件，不需要压缩，继续...");
//							success = true;
//						}
						
						if (success) {
							// 添加记录，到文件存放位置表
							cmsLog.info("准备添加节目包(富媒体)文件的文件位置表记录...");
							AmsStoragePrgRel amsstprgrel = new AmsStoragePrgRel();
							amsstprgrel.setStglobalid(stGlobalid);
							amsstprgrel.setStdirglobalid(stDirGlobalid);
							amsstprgrel.setPrglobalid(productid); // 节目包ID
							amsstprgrel.setProgfileid(aspr.getProgfileid());
							amsstprgrel.setFtypeglobalid(aspr
									.getFtypeglobalid()); // filetype
							if (1 == pf.getProgrank()) {
								amsstprgrel.setFilename(
										aspr.getFilename().substring(0, 
												aspr.getFilename().indexOf(".")) + "/");
							} else {
								amsstprgrel.setFilename(aspr.getFilename());
							}
							amsstprgrel.setFiledate(aspr.getFiledate());
							amsstprgrel.setFilepath(filePath);
							amsstprgrel.setUploadtime(new Date());
							amsstprgrel.setInputmanid(operatorid);
							amsstprgrel.setInputtime(new Date());
							amsstprgrel.setRemark(remark);

							amsstorageprgrelManager.save(amsstprgrel);
							cmsLog.info("文件位置表记录已经添加，文件位置ID："
									+ amsstprgrel.getRelid());
						} else {
							String str = "压缩节目包(富媒体)文件夹失败，不生成文件位置表记录。";
							cmsLog.warn(str);
							cmsResultDto.setResultCode(Long.valueOf(1));
							cmsResultDto.setErrorMessage(str);
							throw new RuntimeException(str);
						}
					} else {
						// 返回失败
						String str = "节目文件不存在，返回失败。文件：" + aspr.getProgfileid();
						cmsLog.warn(str);
						cmsResultDto.setResultCode(Long.valueOf(1));
						cmsResultDto.setErrorMessage(str);
						return cmsResultDto;
					}
				}
			}

			cmsLog.info("准备更新节目包的状态和处理状态...");
			updateRefreshState1OfProgPackage(packageFilesManager,
					progListDetailManager, progPackageManager,
					stclasscodeNearOnline, // 缓存库存储体等级code
					stclasscodeCaOnline, // 加扰库存储体等级code
					stclasscodeOnline, // 播控库存储体等级code
					"", progPackage, 1);
			cmsLog.info("节目包的状态和处理状态更新完毕。");
		} catch (Exception e) {
			// 返回失败
			String str = e.getMessage();
			cmsLog.error("异常：" + str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.debug("Cms -> CmsTransaction -> saveFinishEncrypt returns.");
		return cmsResultDto;
	}

	// ------------------------------------- Portal
	// ---------------------------------------------------
	// 这就是传说中的方法12
	// 20100119 22:13
	// 保存ProgListFile和AmsStoragePrgRel
	public CmsResultDto saveProgListFileAmsStoragePrgRel(
			IProgListFileManager progListFileManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			ProgListFile progListFile, AmsStoragePrgRel amsStoragePrgRel) {
		// 返回：ProgListFile progListFile

		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgListFileAmsStoragePrgRel...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		progListFileManager.save(progListFile);
		amsStoragePrgRel.setProgfileid(progListFile.getColumnfileid());
		amsStoragePrgRel.setFiledate(new Date());
		amsstorageprgrelManager.save(amsStoragePrgRel);
		cmsResultDto.setResultObject(progListFile);
		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgListFileAmsStoragePrgRel returns.");
		return cmsResultDto;
	}

	// 这就是传说中的方法8
	// 20100119 22:41
	// 保存ProgListFile，如果已经存在记录，把状态都改为无效，再生成有效的新记录
	public CmsResultDto saveProgListFile(
			IProgListFileManager progListFileManager,
			ProgListFile progListFile, String date, // 栏目单日期，格式：yyyy-MM-dd
			String defcatseq // 栏目的code序列
	) {
		// 返回：ProgListFile progListFile

		cmsLog.debug("Cms -> CmsTransaction -> saveProgListFile...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		if (defcatseq != null) {
			// 把旧记录改为无效状态
			// 返回：
			// List<Object[]>
			// (ProgListFile)Object[0]
			// (PortalColumn)Object[1]
			cmsLog.info("查询现有progListFile记录...");
			List plfs = progListFileManager
					.getProgListFilesByDateFiletypeDefcatseq(date, progListFile
							.getFiletype(), defcatseq);
			cmsLog.info("数量：" + plfs.size());
			if (plfs != null && plfs.size() > 0) {
				for (int i = 0; i < plfs.size(); i++) {
					Object[] rows = (Object[]) plfs.get(i);
					ProgListFile plf = (ProgListFile) rows[0];

					cmsLog.info("处理第" + (i + 1) + "个记录...");
					cmsLog.info("ID:" + plf.getColumnfileid());

					if (plf.getState1() != (long) 1) {
						plf.setState1(Long.valueOf(1));
						progListFileManager.update(plf);
						cmsLog.info("state1已经更新为1。");
					}
				}
			}
		}

		// 生成新记录
		progListFileManager.save(progListFile);
		cmsLog.info("progListFile已经保存，ID：" + progListFile.getColumnfileid());

		cmsResultDto.setResultObject(progListFile);
		cmsLog.debug("Cms -> CmsTransaction -> saveProgListFile returns.");
		return cmsResultDto;
	}

	// ------------------------------------- Migration
	// ---------------------------------------------------
	// 20100130 16:14
	// 完成迁移到一级库，迁移模块完成迁移后，通过webservice调用这个接口，新增文件位置表记录，修改栏目单详细的节目包状态
	public CmsResultDto saveFinishMigrationToOnline(
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgListDetailManager progListDetailManager,
			IPackageFilesManager packageFilesManager,
			IProgPackageManager progPackageManager,
			String stclasscodeNearOnline, // 缓存库存储体等级code
			String stclasscodeCaOnline, // 加扰库存储体等级code
			String stclasscodeOnline, // 播控库存储体等级code
			AmsStoragePrgRel amsStoragePrgRel, String proglistId, // 栏目单日期
			String result, // 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes // 失败原因
	) {
		cmsLog.debug("Cms -> CmsTransaction -> saveFinishMigrationToOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		amsstorageprgrelManager.save(amsStoragePrgRel);

		// 作废：transferEntity中的文件所在的栏目单详细中的处理状态改为“成功”，处理状态(0未处理1处理8失败9成功)
		// 查询transferEntity中的文件所在的节目包状态（ProgListDetail中）是否需要改变，
		// 调用refreshState1sInProgListDetail，需要date从ProglistId获得，需要栏目单详细从SourceProgid获得节目包ID
		// 作废：如果需要改变，则改变，同时判断栏目单分表总表状态是否需要修改
		// 作废：如需要，则修改栏目单分表总表状态
		// List plds =
		// progListDetailManager.getOnlineProgListDetailsByProductid(amsStoragePrgRel.getPrglobalid());
		// for(int i = 0; i < plds.size(); i++)
		// {
		// ProgListDetail progListDetail = (ProgListDetail)plds.get(i);
		//			
		// // 判断栏目单详细的上线下线日期
		// Date date = fileoper.convertStringToDate(proglistId, "yyyy-MM-dd
		// HH:mm:ss");
		// if(date.compareTo(progListDetail.getValidFrom()) >= 0
		// && date.compareTo(progListDetail.getValidTo()) <= 0)
		// {
		// // 有效且上线
		// updateRefreshState1OfProgPackage(
		// packageFilesManager,
		// progListDetailManager,
		// progPackageManager,
		// stclasscodeNearOnline,
		// stclasscodeCaOnline,
		// stclasscodeOnline,
		// progListDetail
		// );
		// }
		// }

		ProgPackage progPackage = (ProgPackage) progPackageManager
				.getById(amsStoragePrgRel.getPrglobalid());
		updateRefreshState1OfProgPackage(packageFilesManager,
				progListDetailManager, progPackageManager,
				stclasscodeNearOnline, // 缓存库存储体等级code
				stclasscodeCaOnline, // 加扰库存储体等级code
				stclasscodeOnline, // 播控库存储体等级code
				"", progPackage, 1);

		cmsLog
				.debug("Cms -> CmsTransaction -> saveFinishMigrationToOnline returns.");
		return cmsResultDto;
	}

	// 20100208 16:34
	// 完成迁移到 在上海的北京缓存库，迁移模块完成迁移后，通过webservice调用这个接口，新增文件位置表记录
	public CmsResultDto saveFinishMigrationToBjOnline(
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgListDetailManager progListDetailManager,
			IPackageFilesManager packageFilesManager,
			IProgPackageManager progPackageManager,
			String stclasscodeNearOnline, // 缓存库存储体等级code
			String stclasscodeCaOnline, // 加扰库存储体等级code
			String stclasscodeOnline, // 播控库存储体等级code
			String stclasscodeBjOnline, // 在上海的北京缓存库存储体等级code
			AmsStoragePrgRel amsStoragePrgRel
	// String proglistId, // 栏目单日期
	// String result, // 迁移结果，"Y" - 成功；"N" - 失败
	// String resultDes // 失败原因
	) {
		cmsLog
				.debug("Cms -> CmsTransaction -> saveFinishMigrationToBjOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		amsstorageprgrelManager.save(amsStoragePrgRel);

		ProgPackage progPackage = (ProgPackage) progPackageManager
				.getById(amsStoragePrgRel.getPrglobalid());
		updateRefreshState1OfProgPackage(packageFilesManager,
				progListDetailManager, progPackageManager,
				stclasscodeNearOnline, // 缓存库存储体等级code
				stclasscodeCaOnline, // 加扰库存储体等级code
				stclasscodeOnline, // 播控库存储体等级code
				stclasscodeBjOnline, // 在上海的北京缓存库存储体等级code
				progPackage, 0);

		cmsLog
				.debug("Cms -> CmsTransaction -> saveFinishMigrationToBjOnline returns.");
		return cmsResultDto;
	}

	// 20100130 16:14
	// 完成迁移，从导入区到北京缓存库，迁移模块完成迁移后，通过webservice调用这个接口
	public CmsResultDto saveFinishMigrationImportDataToBjNearOnline(
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgListDetailManager progListDetailManager,
			IPackageFilesManager packageFilesManager,
			IProgPackageManager progPackageManager,
			String stclasscodeNearOnline, // 缓存库存储体等级code
			String stclasscodeCaOnline, // 加扰库存储体等级code
			String stclasscodeOnline, // 播控库存储体等级code
			AmsStoragePrgRel amsStoragePrgRel
	// String proglistId, // 栏目单日期
	// String result, // 迁移结果，"Y" - 成功；"N" - 失败
	// String resultDes // 失败原因
	) {
		cmsLog
				.debug("Cms -> CmsTransaction -> saveFinishMigrationImportDataToBjNearOnline...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		amsstorageprgrelManager.save(amsStoragePrgRel);

		ProgPackage progPackage = (ProgPackage) progPackageManager
				.getById(amsStoragePrgRel.getPrglobalid());
		progPackage.setState(0l);
		
		updateRefreshState1OfProgPackage(packageFilesManager,
				progListDetailManager, progPackageManager,
				stclasscodeNearOnline, // 缓存库存储体等级code
				stclasscodeCaOnline, // 加扰库存储体等级code
				stclasscodeOnline, // 播控库存储体等级code
				"", progPackage, 1);

		// 还需要修改分表总表的活动

		cmsLog
				.debug("Cms -> CmsTransaction -> saveFinishMigrationImportDataToBjNearOnline returns.");
		return cmsResultDto;
	}

	// ------------------------------------- broadcast 播发单
	// ---------------------------------------------------
	// 20100202 12:18
	// 播发单下发完成，保存发布文件表记录progListfile，并且发送活动 86-->87 播放单生成成功，发送
	public CmsResultDto saveProgListFileAndUpdateProgListMangDetail(
			IProgListFileManager progListFileManager,
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			IBpmcManager bpmcManager,
			IFlowActivityOrderManager flowActivityOrderManager,
			IPortalColumnManager portalColumnManager,
			IProgListDetailManager progListDetailManager,
			ProgListFile progListFile, String date, // 栏目单日期，格式：yyyy-MM-dd
			String defcatseq, // 栏目的code序列
			String operatorId) {
		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgListFileAndUpdateProgListMangDetail...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsResultDto = saveProgListFile(progListFileManager, progListFile,
				date, "");
		if (cmsResultDto.getResultCode() == 0) {
			// 说明：
			// FU00000081 文件加扰
			// FU00000082 生成页面
			// FU00000083 PORTAL完成
			// FU00000084 预览
			// FU00000085 迁移
			// FU00000086 播发
			// FU00000087 完成
			// 总单的发送，是针对所有栏目的（不仅仅是本地栏目）
			// 界面 总单审校：当前活动（“文件加扰”81），以分表为单位发送。
			// 81-->82 判断栏目（栏目单详细）下所有节目包的state>=2加扰库，才能发送
			// 界面 Portal预览：当前活动（“生成页面”82
			// “PORTAL完成”83），82-->83以分表为单位发送，83-->85压缩portal并以date为单位发送所有栏目分表
			// 82-->83 以栏目分表为单位，生成portal后，发送
			// 83-->85 以date总表为单位，判断是否所有分表活动都是83，是才能压缩并发送所有栏目分表到85
			// 界面 编单迁移：当前活动（“迁移”85），以date栏目单总表为单位发送，
			// 85-->86 判断是否所有栏目单分表对应所有栏目单详细对应所有节目包的state==3，是才能发送
			// 界面 编单播发：当前活动（“播发”86），以date栏目单总表为单位发送，
			// 86-->87 播放单生成成功，发送

			String currentIdAct = "FU00000086";
			// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
			String scheduleDate = convertDateToScheduleDate(date);

			boolean allowSend = true;

			// 判断是否可以发送到下一活动
			allowSend = checkSendProgListMangDetails(portalColumnManager,
					progListDetailManager, progListMangDetailManager, date, // 日期，格式：yyyy-MM-dd
					currentIdAct, // 当前活动编号
					defcatseq // 栏目的代码序列
			);

			// 查询下一活动nextIdAct，条件：State2=P，FLOWACTIVITYIDPARENT=currentIdAct
			List flowActivityOrders = flowActivityOrderManager
					.getNextIdActsByCurrentIdActAndState2(currentIdAct, "P");

			if (allowSend == true && flowActivityOrders.size() > 0) {
				FlowActivityOrder flowActivityOrder = (FlowActivityOrder) flowActivityOrders
						.get(0);

				cmsResultDto = updateProgListMangDetails(progListMangManager,
						progListMangDetailManager, bpmcManager,
						flowActivityOrderManager, scheduleDate, // 主表的主键
						defcatseq, // 栏目的代码序列
						flowActivityOrder.getFlowactivityidchild(), // 下一活动编号
						"", // 下一操作人员
						"P", // 下一活动的性质：（新建N 回退R 顺序P 完成C）
						currentIdAct, // 当前活动编号
						operatorId, // 当前操作人员
						"生成播发单成功" // 备注
				);

				/**
				 * 这是传说中的, 将下一个活动编号. 纯粹为了返回当前活动的下一个活动. 没啥用处
				 */
				cmsResultDto.setErrorDetail(flowActivityOrder
						.getFlowactivityidchild());
			}

			if (cmsResultDto.getResultCode() != 0) {
				String str = "保存发布文件成功，发送栏目单分表活动失败，回滚操作。";
				cmsLog.warn(str);
				cmsResultDto.setResultCode(Long.valueOf(1));
				cmsResultDto.setErrorMessage(str);
				throw new RuntimeException(str);
			} else {
				Bpmc bpmc = new Bpmc();
				Date d = new Date();
				bpmc.setObjectid(convertDateToScheduleDate(date));
				bpmc.setCreatedate(d);
				bpmc.setSends(currentIdAct);
				bpmc.setReceives(cmsResultDto.getErrorDetail());
				bpmc.setSendremark("生成播发单");
				bpmc.setState("P");
				bpmc.setCreater(operatorId);
				bpmc.setSender(operatorId);
				bpmc.setSenddate(d);

				bpmcManager.save(bpmc);

				String str = "保存发布文件成功，发送栏目单分表活动成功。";
				cmsLog.info(str);
			}
		} else {
			String str = "保存发布文件失败。";
			cmsLog.warn(str);
		}

		cmsLog
				.debug("Cms -> CmsTransaction -> saveProgListFileAndUpdateProgListMangDetail returns.");
		return cmsResultDto;
	}

	// ----------------------------------- PortalPackage
	// ------------------------------------
	// 20100414 19:42
	// 保存（新建）图文记录（TPORTALPACKAGE），同时保存PORTAL页面包装和节目包关系表（TPTPPGPREL）记录
	public CmsResultDto savePortalPackage(
			IPortalPackageManager portalPackageManager,
			IPtpPgpRelManager ptpPgpRelManager, PortalPackage portalPackage,
			List ptpPgpRels, String operatorId) {
		cmsLog.debug("Cms -> CmsTransaction -> savePortalPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		Date date = new Date();
		portalPackage.setInputmanid(operatorId);
		portalPackage.setInputtime(date);
		portalPackageManager.save(portalPackage);
		cmsLog.info("页面包装已经保存。ID：" + portalPackage.getPtpid());

		if (ptpPgpRels != null && ptpPgpRels.size() > 0) {
			for (int i = 0; i < ptpPgpRels.size(); i++) {
				PtpPgpRel ptpPgpRel = (PtpPgpRel) ptpPgpRels.get(i);
				ptpPgpRel.setInputmanid(operatorId);
				ptpPgpRel.setInputtime(date);
				ptpPgpRelManager.save(ptpPgpRel);
				cmsLog.info("保存记录，节目包ID：" + ptpPgpRel.getProductid());
			}
			cmsLog.info("页面包装与节目包关系已经保存。共" + ptpPgpRels.size() + "条记录。");
		}

		cmsLog.debug("Cms -> CmsTransaction -> savePortalPackage returns.");
		return cmsResultDto;
	}

	// 20100414 19:49
	// 修改图文记录（TPORTALPACKAGE），同时保存PORTAL页面包装和节目包关系表（TPTPPGPREL）记录
	public CmsResultDto updatePortalPackage(
			IPortalPackageManager portalPackageManager,
			IPtpPgpRelManager ptpPgpRelManager, PortalPackage portalPackage,
			List ptpPgpRels, String operatorId) {
		cmsLog.debug("Cms -> CmsTransaction -> updatePortalPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		Date date = new Date();

		portalPackageManager.update(portalPackage);
		cmsLog.info("页面包装已经更新。ID：" + portalPackage.getPtpid());

		List list = portalPackageManager.findByProperty("ptpid", portalPackage
				.getPtpid());
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				PtpPgpRel ptpPgpRel = (PtpPgpRel) list.get(i);
				ptpPgpRelManager.deleteById(ptpPgpRel.getRelid());
				cmsLog.info("删除记录，节目包ID：" + ptpPgpRel.getProductid());
			}
			cmsLog.info("页面包装与节目包关系已经删除。共" + list.size() + "条记录。");
		}

		if (ptpPgpRels != null && ptpPgpRels.size() > 0) {
			for (int i = 0; i < ptpPgpRels.size(); i++) {
				PtpPgpRel ptpPgpRel = (PtpPgpRel) ptpPgpRels.get(i);
				ptpPgpRel.setInputmanid(operatorId);
				ptpPgpRel.setInputtime(date);
				ptpPgpRelManager.save(ptpPgpRel);
				cmsLog.info("保存记录，节目包ID：" + ptpPgpRel.getProductid());
			}
			cmsLog.info("页面包装与节目包关系已经保存。共" + ptpPgpRels.size() + "条记录。");
		}

		cmsLog.debug("Cms -> CmsTransaction -> updatePortalPackage returns.");
		return cmsResultDto;
	}

	// 20100419 11:35
	// 删除页面包装记录（TPORTALPACKAGE），同时删除PORTAL页面包装和节目包关系表（TPTPPGPREL）记录
	public CmsResultDto deletePortalPackage(
			IPortalPackageManager portalPackageManager,
			IPtpPgpRelManager ptpPgpRelManager, String ptpid, String operatorId) {
		cmsLog.debug("Cms -> CmsTransaction -> deletePortalPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsLog.info("准备删除页面包装...页面包装ID：" + ptpid);
		// 判断页面包装下是否还有节目包
		List pprs = ptpPgpRelManager.findByProperty("ptpid", ptpid);
		cmsLog.info("页面包装下共有" + pprs.size() + "个节目包");
		for (int i = 0; i < pprs.size(); i++) {
			PtpPgpRel ptpPgpRel = (PtpPgpRel) pprs.get(i);
			ptpPgpRelManager.deleteById(ptpPgpRel.getRelid());
		}
		cmsLog.info("页面包装下的节目包已经全部删除。");
		portalPackageManager.deleteById(ptpid);
		cmsLog.info("页面包装记录已经删除。");

		cmsLog.debug("Cms -> CmsTransaction -> deletePortalPackage returns.");
		return cmsResultDto;
	}

	// 20100419 11:37
	// 新增页面包装与节目包关系记录
	public CmsResultDto savePtpPgpRels(
			IPortalPackageManager portalPackageManager,
			IPtpPgpRelManager ptpPgpRelManager,
			IProgPackageManager progPackageManager,
			PortalPackage portalPackage, // 页面包装对象
			List ptpPgpRels, // 页面包装与节目包关系对象
			String operatorId // 操作人员ID
	) {
		cmsLog.debug("Cms -> CmsTransaction -> savePtpPgpRels...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		String str = "";
		Date date = new Date();

		if (ptpPgpRels != null && ptpPgpRels.size() > 0) {
			// 判断系列名称与页面包装名称是否相同，如果有一个不同，则不能保存
			boolean flag = true;
			String str1 = "";
			String str2 = "";
			for (int i = 0; i < ptpPgpRels.size(); i++) {
				PtpPgpRel ptpPgpRel = (PtpPgpRel) ptpPgpRels.get(i);
				ProgPackage pp = (ProgPackage) progPackageManager
						.getById(ptpPgpRel.getProductid());

				if (!portalPackage.getPtpname().equalsIgnoreCase(
						pp.getEpicodename()))
				// if(i == 0)
				// {
				// str1 = pp.getEpicodename();
				// }
				// str2 = pp.getEpicodename();
				// if(!str1.equalsIgnoreCase(str2))
				{
					flag = false;
					break;
				}
			}

			if (flag == true) {
				for (int i = 0; i < ptpPgpRels.size(); i++) {
					PtpPgpRel ptpPgpRel = (PtpPgpRel) ptpPgpRels.get(i);

					cmsLog.warn("ptpPgpRel：" + ptpPgpRel);
					cmsLog.warn("ptpPgpRel.getProductid()"
							+ ptpPgpRel.getProductid());
					if (ptpPgpRel == null || ptpPgpRel.getProductid() == null) {
						cmsLog.warn("关系记录内容有误，不保存。");
					} else {
						if (ptpPgpRel.getEpicodeid() == null) {
							cmsLog.warn("分集数为空，赋值0。");
							ptpPgpRel.setEpicodeid((long) 0);
						}
						// 判断关系是否已经存在
						cmsLog.info("判断关系记录是否已经存在...");
						List oldpprs = portalPackageManager
								.getPtpPgpRelsByPtpidProductid(portalPackage
										.getPtpid(), ptpPgpRel.getProductid());

						if (oldpprs == null || oldpprs.size() <= 0) {
							ptpPgpRel.setInputmanid(operatorId);
							ptpPgpRel.setInputtime(date);
							ptpPgpRelManager.save(ptpPgpRel);
							cmsLog.info("保存记录，节目包ID："
									+ ptpPgpRel.getProductid());
						} else {
							cmsLog.warn("关系已经存在，不保存。关系ID："
									+ ptpPgpRel.getRelid());
						}
					}
				}

				str = "页面包装与节目包关系已经保存。共" + ptpPgpRels.size() + "条记录。";
				cmsLog.info(str);
				cmsResultDto.setErrorMessage(str);
			} else {
				str = "所选择的节目包的系列名称与页面包装名称不一致，不保存。";
				cmsLog.warn(str);
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
			}
		} else {
			str = "输入参数为空。";
			cmsLog.info(str);
			cmsResultDto.setErrorMessage(str);
		}

		cmsLog.debug("Cms -> CmsTransaction -> savePtpPgpRels returns.");
		return cmsResultDto;
	}

	// 20100419 11:37
	// 删除页面包装与节目包关系记录
	public CmsResultDto deletePtpPgpRels(
			IPortalPackageManager portalPackageManager,
			IPtpPgpRelManager ptpPgpRelManager, PortalPackage portalPackage, // 页面包装对象
			List ptpPgpRels, // 页面包装与节目包关系对象
			String operatorId // 操作人员ID
	) {
		cmsLog.debug("Cms -> CmsTransaction -> deletePtpPgpRels...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		String str = "";

		if (ptpPgpRels != null && ptpPgpRels.size() > 0) {
			for (int i = 0; i < ptpPgpRels.size(); i++) {
				PtpPgpRel ptpPgpRel = (PtpPgpRel) ptpPgpRels.get(i);

				cmsLog.warn("ptpPgpRel" + ptpPgpRel);
				cmsLog.warn("ptpPgpRel.getProductid()"
						+ ptpPgpRel.getProductid());
				cmsLog.warn("ptpPgpRel.getRelid()" + ptpPgpRel.getRelid());
				if (ptpPgpRel == null || ptpPgpRel.getProductid() == null
						|| ptpPgpRel.getRelid() == null) {
					cmsLog.warn("关系记录内容有误，不删除。");
				} else {
					// 判断关系是否已经存在
					List oldpprs = portalPackageManager
							.getPtpPgpRelsByPtpidProductid(portalPackage
									.getPtpid(), ptpPgpRel.getProductid());

					if (oldpprs == null || oldpprs.size() <= 0) {
						cmsLog.warn("关系已经不存在，不删除。");
					} else {
						cmsLog.info("准备删除记录，节目包ID：" + ptpPgpRel.getProductid());
						ptpPgpRelManager.deleteById(ptpPgpRel.getRelid());
						cmsLog.info("记录已删除。");
					}
				}
			}
			str = "页面包装与节目包关系已经删除。共" + ptpPgpRels.size() + "条记录。";
			cmsLog.info(str);
			cmsResultDto.setErrorMessage(str);
		} else {
			str = "输入参数为空。";
			cmsLog.info(str);
			cmsResultDto.setErrorMessage(str);
		}

		cmsLog.debug("Cms -> CmsTransaction -> deletePtpPgpRels returns.");
		return cmsResultDto;
	}

	/**
	 * 流程回退. 所有流程只要回退. 统一回退到总单审校 总单审校, 数据导入活动编号: FU00000080
	 * 
	 * @param progListMangManager
	 * @param progListMangDetailManager
	 * @param bpmcManager
	 * @param date
	 *            日期主键. 格式: 2010-01-01
	 * @param inputid
	 *            操作者编号
	 * @param remark
	 *            回退备注
	 */
	public void updateRollBack(IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			IBpmcManager bpmcManager, String date, String inputid, String remark) {
		cmsLog.debug("Cms -> CmsTransaction -> updateRollBack. 流程回退");

		String pkid = convertDateToScheduleDate(date);

		ProgListMang progListMang = (ProgListMang) progListMangManager
				.getById(pkid);
		List<ProgListMangDetail> progListMangDetails = progListMangDetailManager
				.findByProperty("scheduledate", pkid);

		if (progListMang != null && progListMangDetails.size() > 0) {
			cmsLog.info("开始修改流程属性...");
			String oldIdAct = progListMang.getIdAct();

			progListMang.setIdAct("FU00000080");
			progListMang.setRemark(remark);

			for (ProgListMangDetail progListMangDetail : progListMangDetails) {
				progListMangDetail.setIdAct("FU00000080");
				progListMangDetail.setRemark(remark);
			}

			progListMangManager.update(progListMang);
			progListMangDetailManager.update(progListMangDetails.toArray());

			Bpmc bpmc = new Bpmc();
			Date d = new Date();
			bpmc.setObjectid(progListMang.getScheduledate());
			bpmc.setCreatedate(d);
			bpmc.setSends(oldIdAct);
			bpmc.setReceives("FU00000080");
			bpmc.setSendremark(remark);
			bpmc.setState("R");
			bpmc.setCreater(inputid);
			bpmc.setSender(inputid);
			bpmc.setSenddate(d);

			bpmcManager.save(bpmc);

			cmsLog.info("修改流程属性成功...");
		} else {
			cmsLog.error("流程对象为空!.. 出错... ");
		}
	}

	/**
	 * 数据导入，把上海导出的节目包和节目包文件信息，导入到北京的cms 2010年7月14日 11时18分
	 * 
	 * @param progPackageManager
	 * @param programFilesManager
	 * @param packageFilesManager
	 * @param amsstoragedirManager
	 * @param amsstorageManager
	 * @param amsstorageclassManager
	 * @param importDataStclasscode
	 * @param importDataFilecode
	 * @param destStclasscode
	 * @param filecodeMigrationImport
	 * @param stclasscodeMigrationImport
	 * @param progPackagePath
	 * @param progListPath
	 * @param operator
	 * @return
	 */
	public CmsResultDto saveImportProgPackageFilesToBjCms2(
			IProgPackageManager progPackageManager,
			IProgramFilesManager programFilesManager,
			IPackageFilesManager packageFilesManager,
			IAmsStorageDirManager amsstoragedirManager,
			IAmsStorageManager amsstorageManager,
			IAmsStorageClassManager amsstorageclassManager,
			String importDataStclasscode, // 数据导入的存储体等级code
			String importDataFilecode, // 数据导入的源路径
			String destStclasscode, // 目标存储体等级code
			String filecodeMigrationImport, // 迁移单xml的filecode
			String stclasscodeMigrationImport, // 迁移单xml的存储体等级code
			String progPackagePath, // 节目包目录名称
			String progListPath, // 栏目单目录名称
			String operator // 当前操作人员
	) {
		// 流程：
		// 1 - 从配置文件，获得数据导出导入的节目包的源路径
		// 2 - 获得源路径下所有目录，期待结果格式:20100222000000
		// 3 - 获得编单日期目录(20100222000000)下的所有目录，期待结果：progpackage和proglist
		// 4 - 获得progpackage下的所有目录，期待结果格式：PPVP20100209110638000078
		// 5 - 获得PPVP20100209110638000078目录下的所有文件，期待结果：*.png,*.ts,*.xml
		// 6 - 读取xml文件内容，获得节目包ID、栏目单详细记录信息
		// 7 - 查询数据库，判断节目包是否已经存在
		// 7.1 - 存在，暂时不做处理
		// 7.2 - 不存在，需要新增节目包记录到数据库，继续
		// 7.2.1 - 根据xml，获得节目包下所有的文件信息，判断对应的文件是否都在目录(PPVP20100209110638000078)下
		// 7.2.1.1 - 不存在，不处理
		// 7.2.1.2 - 存在，继续
		// 7.2.1.2.1 -
		// 添加记录到：ProgPackage,ProgramFiles,PackageFiles,(节目包栏目关系表，节目包产品关系表)
		// 7.2.1.2.2 - 下迁移单，把节目包下的文件迁移到缓存库

		CmsResultDto cmsResultDto = new CmsResultDto();

		List renames = new ArrayList();
		List<String> importFailureList = new ArrayList<String>(0);
		String importDataPath = "";
		AmsStorage sourceAmsStorage = null;
		AmsStorageDir sourceAmsStorageDir = null;
		AmsStorageClass sourceAmsStorageClass = null;
		// 返回：List<Object[]>
		// (AmsStorage)Object[0]
		// (AmsStorageDir)Object[1]
		// (AmsStorageClass)Object[2]
		List amsstdirs = amsstoragedirManager
				.getStorageStoragedirsByStclasscodeFilecode(
						importDataStclasscode, importDataFilecode); // 查询导入区存储信息
		if (amsstdirs.size() > 0) {
			Object[] rows = (Object[]) amsstdirs.get(0);
			sourceAmsStorage = (AmsStorage) rows[0];
			sourceAmsStorageDir = (AmsStorageDir) rows[1];
			sourceAmsStorageClass = (AmsStorageClass) rows[2];
		}
		importDataPath = sourceAmsStorage.getStorageaccstype();
		importDataPath += "://";
		importDataPath += sourceAmsStorage.getLoginname();
		importDataPath += ":";
		if (sourceAmsStorage.getLoginpwd() != null)
			importDataPath += sourceAmsStorage.getLoginpwd();
		importDataPath += "@";
		importDataPath += sourceAmsStorage.getStorageip();
		if (sourceAmsStorage.getMappath() != null
				&& !sourceAmsStorage.getMappath().equalsIgnoreCase("")
				&& !sourceAmsStorage.getMappath().equalsIgnoreCase("/")) {
			importDataPath += sourceAmsStorage.getMappath();
			importDataPath = importDataPath.replace('\\', '/');
			importDataPath = fileoper.checkPathFormatRear(importDataPath, '/');
		}
		if (sourceAmsStorageDir.getStoragedirname() != null
				&& !sourceAmsStorageDir.getStoragedirname()
						.equalsIgnoreCase("")
				&& !sourceAmsStorageDir.getStoragedirname().equalsIgnoreCase(
						"/")) {
			importDataPath += sourceAmsStorageDir.getStoragedirname();
			importDataPath = importDataPath.replace('\\', '/');
			importDataPath = fileoper.checkPathFormatRear(importDataPath, '/');
		}
		importDataPath = fileoper.checkPathFormatRear(importDataPath, '/');

		cmsLog.info("得到北京导入区的源路径: " + importDataPath);

		// 1 - 从配置文件，获得数据导出导入的节目包的源路径
		// 2 - 获得源路径下所有目录，期待结果格式:20100222000000
		SmbFile[] files = fileoper.listSmbFiles(importDataPath);
		cmsLog.info("获取路径下所有内容，共有" + files.length + "个内容。");

		if (files != null) {
			// 导入区根目录级循环, 目录格式: 20100222000000
			for (int i = 0; i < files.length; i++) {
				SmbFile file = files[i];
				String filename = file.getName(); // 目录名称，格式："20100222000000/"
				String progListDate = -1 != filename.lastIndexOf("/") 
						? filename.substring(0, filename.length() - 1) 
								: filename; // "PAL.ts"
				String proglistPath = importDataPath + filename;
				proglistPath = fileoper.checkPathFormatRear(proglistPath, '/');

				cmsLog.info("处理第" + (i + 1) + "个文件夹: " + filename);

				try {
					int fileNameLength = -1 != filename.lastIndexOf("/") 
							? filename.length() - 1 : filename.length();
					if (!file.isDirectory() || 14 != fileNameLength) {
						cmsLog.info("不是文件夹或文件夹名称: " + filename
								+ " 不符合规则, 跳过不处理...");
						continue;
					}
				} catch (SmbException e) {
					cmsLog.error("SmbFile: " + file.getPath() + " 读取错误! " + e);
				}

				// 3 - 获得编单日期目录(20100222000000)下的所有目录，期待结果：progpackage和proglist
				cmsLog.info("获得编单日期: " + filename + " 目录文件夹下所有内容...");
				SmbFile[] progfiles = fileoper.listSmbFiles(proglistPath);
				cmsLog.info("共有" + progfiles.length + "个内容。");

				// 编单日期目录级循环, 目录格式: progpackage, proglist
				for (int j = 0; j < progfiles.length; j++) {
					SmbFile progfile = progfiles[j];
					String progfilename = progfile.getName(); // 目录名称，格式："20100222000000/"
					String progpackagePath = proglistPath + progfilename;
					progpackagePath = fileoper.checkPathFormatRear(
							progpackagePath, '/');

					cmsLog.info("处理第" + (j + 1) + "个文件夹: " + progfilename);

					try {
						progfilename = -1 == progfilename.lastIndexOf("/") 
								? progfilename : progfilename.substring(0, progfilename.length() - 1);
						if (!progfile.isDirectory()
								|| !progfilename.equalsIgnoreCase(progPackagePath)) {
							cmsLog.info("不是文件夹或文件夹名称: " + progfilename
									+ " 不符合规则, 跳过不处理...");
							continue;
						}
					} catch (SmbException e) {
						cmsLog.error("SmbFile: " + progfile.getPath()
								+ " 读取错误! " + e);
					}

					// 4 - 获得progpackage下的所有目录，期待结果格式：PPVP20100209110638000078
					cmsLog.info("获得progpackage文件夹下所有节目包内容...");
					SmbFile[] progpackagefiles = fileoper
							.listSmbFiles(progpackagePath);
					cmsLog.info("共有" + progpackagefiles.length + "个内容。");

					// progpackage 级循环所有节目包目录, 目录格式: PPVP20100209110638000078
					for (int k = 0; k < progpackagefiles.length; k++) {
						SmbFile progpackagefile = progpackagefiles[k];
						String progpackagefilename = progpackagefile.getName(); // 目录名称，格式："20100222000000/"
						String progpackagefilePath = progpackagePath
								+ progpackagefilename;
						progpackagefilePath = fileoper.checkPathFormatRear(
								progpackagefilePath, '/');

						cmsLog.info("处理第" + (k + 1) + "个文件夹: " + progpackagefilename);

						try {
							int progPackageFileNameLength = -1 != progpackagefilename.lastIndexOf("/")
									? progpackagefilename.length() - 1 : progpackagefilename.length();
							if (!progpackagefile.isDirectory() || 24 != progPackageFileNameLength) {
								cmsLog.info("不是文件夹或文件夹名称: " + progpackagefilename 
										+ " 不符合规则, 跳过不处理...");
								continue;
							}
						} catch (SmbException e) {
							cmsLog.error("SmbFile: " + progpackagefile.getPath()
									+ " 读取错误! " + e);
						}
						
						// 5 -
						// 获得PPVP20100209110638000078目录下的所有文件，期待结果：*.png,*.ts,*.xml
						cmsLog.info("获得文件夹: " + progpackagefilename + " 下所有内容...");
						SmbFile[] pprealfiles = fileoper
								.listSmbFiles(progpackagefilePath);
						cmsLog.info("共有" + pprealfiles.length + "个内容。");
						
						String xmlFilePath = "";
						for (int l = 0; l < pprealfiles.length; l++) {
							SmbFile pprealfile = pprealfiles[l];
							String pprealfilename = pprealfile.getName(); 

							cmsLog.info("处理第" + (l + 1) + "个内容...");
							cmsLog.info("内容名称：" + pprealfilename);

							String[] strlist = pprealfilename.split("\\.");
							if (strlist.length < 2) {
								cmsLog.info("文件或文件夹名称：" + pprealfilename);
								cmsLog.info("文件名称格式不符合规则，不处理，跳过...");
								continue;
							}
							if (strlist[1].equalsIgnoreCase("xml")) {
								xmlFilePath = progpackagefilePath
										+ pprealfilename;
								cmsLog.info("得到节目包的xml文件，" + xmlFilePath);
								break;
							}
						}
						
						if (xmlFilePath == null
								|| xmlFilePath.equalsIgnoreCase("")) {
							cmsLog.info("未找到节目包的xml文件，不处理，跳过...");
							continue;
						}

						// 6 - 读取xml文件内容，获得节目包记录和文件记录信息
						cmsLog.info("得到节目包的xml文件，" + xmlFilePath);
						cmsLog.info("读取xml文件内容...");
						// 返回：List
						// 1 - ProgPackage
						// 2 - List<ProgramFiles>
						// 3 - List<PackageFiles>
						// 4 - List<Integer> // 文件导出标识，标识此次数据导出是否同时导出该文件，0 -
						// no ; 1 - yes
						// 5 - List<String> // 文件的filedate
						List list = getProgPackageFilesByXml(xmlFilePath);
						if (list.size() < 5) {
							cmsLog.info("xml内容不符合规则，不处理，跳过...");
							continue;
						}
						
						ProgPackage progPackage = (ProgPackage) list.get(0);
						
						cmsLog.info("------------------------------------\n" 
								+ progPackage.getState() 
								+ "\n------------------------------------");
						
						
						List<ProgramFiles> progfs = (List<ProgramFiles>) list
								.get(1);
						List<PackageFiles> packfs = (List<PackageFiles>) list
								.get(2);
						List<Integer> importfiles = (List<Integer>) list.get(3);
						List<String> filedates = (List<String>) list.get(4);

						// 7 - 查询数据库，判断节目包是否已经存在
						ProgPackage oldpp = (ProgPackage) progPackageManager
								.getById(progPackage.getProductid());
						if (oldpp != null) {
							
							
							/**--------------------- add 2010年7月14日 14时40分 --------------------------*/
							// 节目包存在, 比较导入区节目包的修改时间和数据库里节目包的修改时间
							// 如果当前节目包修改时间 大于 数据库已存在节目包的修改时间, 表示该节目包已作修改, 需要更新
							if (oldpp.getUpdatetime().getTime() 
									< progPackage.getUpdatetime().getTime()) {
								
								// 1. 当前节目包修改时间大于, 数据库已存在节目修改时间. 则认为节目包更新过.
								// 2. 数据库已存在节目状态等于 -1 ( 未导入 ) 或 0 ( 导入区 ) 状态, 则更新数据库节目包信息. 
								
								if (0 < oldpp.getState()) {
									oldpp.setDealstate(-2l);
									progPackageManager.update(oldpp);
									
									// 节目包状态不等于 -1 跳过不作导入处理...
									cmsLog.info("节目包: " + progPackage.getProductid() 
											+ " 状态不等于 -1 或 0 跳过不作导入处理...");
									importFailureList.add(progPackage.getProductid());
									continue;
								} else {
									boolean allfileexist = true;
									cmsLog.info("共有" + progfs.size() + "个节目包文件。");
									for (int pf = 0; pf < progfs.size(); pf++) {
										ProgramFiles progf = progfs.get(pf);
										int importfile = importfiles.get(pf);
										String progfilePath = progpackagefilePath
												+ progf.getFilename();

										cmsLog.info("处理第" + (pf + 1) + "个节目包文件...");
										cmsLog.info("文件ID：" + progf.getProgfileid());
										cmsLog.info("文件名：" + progf.getFilename());

										if (importfile == 1) {
											cmsLog.info("节目包文件导入标识为”需要导入“，判断文件是否存在...");
											if (!fileoper
													.checkSmbFileExist(progfilePath)) {
												allfileexist = false;
												cmsLog.info("节目包文件缺失。");
												cmsLog.info("节目包文件名：" + progfilePath);
												break;
											}
										}
									}
									
									if (allfileexist == false) {
										// 7.2.1.1 - 不存在，不处理
										cmsLog.info("节目包文件缺失，不处理该节目包，跳过...");
										continue;
									} else {
										
										// 7.2.1.2 - 存在，继续
										String strOldFile = "";
										String newName = "";
										cmsLog.info("节目包文件全部到位，准备更新数据库节目包记录...");
										
										try {
											// 修改节目包
											oldpp.setActors(progPackage.getActors());
											oldpp.setAudiolanguage(progPackage.getAudiolanguage());
											oldpp.setCategory(progPackage.getCategory());
											oldpp.setCountrydist(progPackage.getCountrydist());
											oldpp.setDescription(progPackage.getDescription());
											oldpp.setDirector(progPackage.getDirector());
											oldpp.setEpicodeid(progPackage.getEpicodeid());
											oldpp.setEpicodename(progPackage.getEpicodename());
											oldpp.setEpicodenumber(progPackage.getEpicodenumber());
											oldpp.setFilesizehi(progPackage.getFilesizehi());
											oldpp.setFilesizelow(progPackage.getFilesizelow());
											oldpp.setInputmanid(progPackage.getInputmanid());
											oldpp.setIssuedate(progPackage.getIssuedate());
											oldpp.setLengthtc(progPackage.getLengthtc());
											oldpp.setLicensingWindowEnd(progPackage.getLicensingWindowEnd());
											oldpp.setLicensingWindowStart(progPackage.getLicensingWindowStart());
											oldpp.setPpxml(progPackage.getPpxml());
											oldpp.setProductname(progPackage.getProductname());
											oldpp.setProgtype(progPackage.getProgtype());
											oldpp.setPtglobalid(progPackage.getPtglobalid());
											oldpp.setRemark(progPackage.getRemark());
											oldpp.setShootdate(progPackage.getShootdate());
											oldpp.setStyleid(progPackage.getStyleid());
											oldpp.setSubscriberetime(progPackage.getSubscriberetime());
											oldpp.setSubscriberstime(progPackage.getSubscriberstime());
											oldpp.setSubtitlelanguage(progPackage.getSubtitlelanguage());
											oldpp.setSumfilesize(progPackage.getSumfilesize());
											oldpp.setTitlebrief(progPackage.getTitlebrief());
											oldpp.setUpdatemanid(progPackage.getUpdatemanid());
											oldpp.setUpdatetime(progPackage.getUpdatetime());
											
											progPackageManager.update(oldpp);
										} catch (Exception e) {
											cmsLog.error("节目包更新失败! " + e);
										}
										
										List<PackageFiles> packageFilesList = packageFilesManager
												.queryPackageFilesByProgPackageID(progPackage
														.getProductid());
										cmsLog.info("旧节目包文件数: " + packageFilesList.size());
										packageFilesManager.delete(packageFilesList.toArray());
										cmsLog.info("删除旧节目包文件...");
										
										cmsLog.info("准备添加节目包下的节目文件记录和节目包文件记录...");
										for (int pf = 0; pf < progfs.size(); pf++) {
											ProgramFiles progf = (ProgramFiles) progfs
													.get(pf);
											PackageFiles packf = (PackageFiles) packfs
													.get(pf);
											int importfile = (int) importfiles.get(pf);

											cmsLog.info("准备添加节目文件(ProgramFiles)记录...");
											cmsLog.info("节目包ID："
													+ progPackage.getProductid());
											cmsLog.info("节目文件ID："
													+ progf.getProgfileid());
											cmsLog.info("节目文件名：" + progf.getFilename());
											programFilesManager.deleteById(progf.getProgfileid());
											
											if (null == programFilesManager.getById(progf.getProgfileid())) {
												programFilesManager.save(progf);
											}
											cmsLog.info("节目文件记录已经添加。返回节目文件ID："
													+ progf.getProgfileid());

											cmsLog.info("准备添加节目包文件(PackageFiles)记录...");
											packf.setProductid(progPackage
													.getProductid());
											packf.setProgfileid(progf.getProgfileid());
											packageFilesManager.save(packf);
											cmsLog.info("节目包文件记录已经添加。返回节目包文件ID："
													+ packf.getCmspackageFilesid());
										}

										// 7.2.1.2.2 - 下迁移单，把节目包下的文件迁移到缓存库
										String migXmlFile = "";
										String strMigXml = "";
										int ret = -1;
										cmsLog.info("获取迁移任务单目标路径...");
										migXmlFile = getImportMigrationDest(
												packageFilesManager,
												amsstoragedirManager, sourceAmsStorage,
												sourceAmsStorageDir,
												sourceAmsStorageClass, oldpp,
												importDataPath, // 数据导入的源路径
												progPackagePath, // 节目包目录名称
												progListPath, // 栏目单目录名称
												progListDate, // 编单日期，格式：yyyyMMdd000000
												filecodeMigrationImport, // 迁移单xml的filecode
												stclasscodeMigrationImport // 迁移单xml的存储体等级code
										);
										cmsLog.info("迁移任务单目标路径 - " + migXmlFile);

										cmsLog.info("生成迁移任务单字符串...");
										strMigXml = getImportMigrationXml(
												packageFilesManager,
												amsstoragedirManager, sourceAmsStorage,
												sourceAmsStorageDir,
												sourceAmsStorageClass, oldpp,
												importDataPath, // 数据导入的源路径
												progPackagePath, // 节目包目录名称
												progListPath, // 栏目单目录名称
												progListDate, // 编单日期，格式：yyyyMMdd000000
												filecodeMigrationImport, // 迁移单xml的filecode
												stclasscodeMigrationImport, // 迁移单xml的存储体等级code
												destStclasscode, // 目标存储体等级code
												progfs, // 节目包的文件
												importfiles, filedates, operator // 当前操作人员
										);
										cmsLog.info("生成迁移任务单字符串完毕。");

										if (migXmlFile != null
												&& !migXmlFile.equalsIgnoreCase("")) {
											cmsLog.info("准备修改节目包文件夹名称...");

											strOldFile = progpackagePath
													+ progpackagefilename;
											strOldFile = fileoper.checkPathFormatRear(
													strOldFile, '/');
											newName = progPackageAdditional
													+ progpackagefilename;
											newName = fileoper.checkPathFormatRear(
													newName, '/');
											cmsLog.info("原：" + strOldFile);
											cmsLog.info("新：" + newName);
											List<String> rename = new ArrayList<String>();
											rename.add(strOldFile);
											rename.add(newName);
											renames.add(rename);
											ret = 0;
											cmsLog.warn("这里暂时不修改节目包的文件夹名字。");
											if (ret == 0) {
												// cmsLog.info("节目包文件夹改名成功。");

												if (migXmlFile != null
														&& !migXmlFile
																.equalsIgnoreCase("")) {
													if (strMigXml != null
															&& !strMigXml
																	.equalsIgnoreCase("")) {
														cmsLog
																.info("准备生成迁移任务单xml文件...");
														ret = fileoper.createSmbFile(
																migXmlFile, strMigXml);
														if (ret == 0) {
															cmsLog
																	.info("生成迁移任务单xml文件成功。");

															// 0 - 未处理,1 - 处理,8 -
															// 失败,9 - 成功
															cmsLog.info("生成迁移任务单成功。");
															oldpp.setDealstate((long) 1);
															// cmsLog.warn("测试代码，需要修改...");
															progPackageManager
																	.update(oldpp);
															cmsLog
																	.info("节目包的处理状态更新为1。");
															
															// 导入成功
															cmsResultDto.setResultCode(0l);
														} else {
															String str = "生成迁移任务单xml文件失败。";
															cmsLog.warn(str);
															oldpp.setDealstate((long) 8);
															// cmsLog.warn("测试代码，需要修改...");
															progPackageManager.update(oldpp);
															cmsLog
																	.info("节目包的处理状态更新为8。");
															// throw new
															// RuntimeException(str);
														}

													} else {
														cmsLog
																.info("迁移任务单字符串为空，不需要生成迁移任务单xml文件。");
													}
												} else {
													String str = "迁移单目标目录为空，不生成迁移单xml文件。";
													cmsLog.warn(str);
													// throw new
													// RuntimeException(str);
												}
											} else {
												// String str =
												// "节目包文件夹改名失败，回滚数据库操作。";
												// cmsLog.info("不生成迁移任务单xml文件。");
												// cmsLog.warn(str);
												// throw new RuntimeException(str);
											}
										} else {
											String str = "迁移任务单目标目录为空，不生成迁移任务单。";
											cmsLog.warn(str);
											progPackage.setDealstate((long) 8);
											// cmsLog.warn("测试代码，需要修改...");
											progPackageManager.update(progPackage);
											cmsLog.info("更新节目包的处理状态为8。");

											// throw new RuntimeException(str);
										}
									}
								}
							} else {
								// 节目包存在且未修改，暂时不做处理
								cmsLog.info("节目包已经存在且未修改，暂时不做处理。节目包ID："
										+ oldpp.getProductid());
							}
							
							/**--------------------- add 2010年7月14日 14时40分 --------------------------*/
							
						} else {
							// 7.2 - 不存在，需要新增节目包记录到数据库，继续
							cmsLog.info("节目包不存在，需要添加节目包记录到数据库...节目包ID："
									+ progPackage.getProductid());
							// 7.2.1 -
							// 根据xml，获得节目包下所有的文件信息，判断对应的文件是否都在目录(PPVP20100209110638000078)下
							// 文件路径：progpackagefilePath + 文件名
							cmsLog.info("判断节目包文件是否全部到位...");
							boolean allfileexist = true;
							cmsLog.info("共有" + progfs.size() + "个节目包文件。");
							for (int pf = 0; pf < progfs.size(); pf++) {
								ProgramFiles progf = progfs.get(pf);
								int importfile = importfiles.get(pf);
								String progfilePath = progpackagefilePath
										+ progf.getFilename();

								cmsLog.info("处理第" + (pf + 1) + "个节目包文件...");
								cmsLog.info("文件ID：" + progf.getProgfileid());
								cmsLog.info("文件名：" + progf.getFilename());

								if (importfile == 1) {
									cmsLog.info("节目包文件导入标识为”需要导入“，判断文件是否存在...");
									if (!fileoper
											.checkSmbFileExist(progfilePath)) {
										allfileexist = false;
										cmsLog.info("节目包文件缺失。");
										cmsLog.info("节目包文件名：" + progfilePath);
										break;
									}
								}
							}
							if (allfileexist == false) {
								// 7.2.1.1 - 不存在，不处理
								cmsLog.info("节目包文件缺失，不处理该节目包，跳过...");
								continue;
							} else {
								// 7.2.1.2 - 存在，继续
								String strOldFile = "";
								String newName = "";
								cmsLog.info("节目包文件全部到位，准备添加节目包记录到数据库...");

								// 7.2.1.2.1 -
								// 添加记录到：ProgPackage,ProgramFiles,PackageFiles,(节目包栏目关系表，节目包产品关系表)
								// cmsLog.warn("测试代码，需要修改...");
								progPackageManager.save(progPackage);
								cmsLog.info("节目包记录已经添加。节目包ID："
										+ progPackage.getProductid());
								cmsLog.info("准备添加节目包下的节目文件记录和节目包文件记录...");
								for (int pf = 0; pf < progfs.size(); pf++) {
									ProgramFiles progf = (ProgramFiles) progfs
											.get(pf);
									PackageFiles packf = (PackageFiles) packfs
											.get(pf);
									int importfile = (int) importfiles.get(pf);

									cmsLog.info("准备添加节目文件(ProgramFiles)记录...");
									cmsLog.info("节目包ID："
											+ progPackage.getProductid());
									cmsLog.info("节目文件ID："
											+ progf.getProgfileid());
									cmsLog.info("节目文件名：" + progf.getFilename());
									programFilesManager.save(progf);
									cmsLog.info("节目文件记录已经添加。返回节目文件ID："
											+ progf.getProgfileid());

									cmsLog.info("准备添加节目包文件(PackageFiles)记录...");
									packf.setProductid(progPackage
											.getProductid());
									packf.setProgfileid(progf.getProgfileid());
									packageFilesManager.save(packf);
									cmsLog.info("节目包文件记录已经添加。返回节目包文件ID："
											+ packf.getCmspackageFilesid());
								}

								// 7.2.1.2.2 - 下迁移单，把节目包下的文件迁移到缓存库
								String migXmlFile = "";
								String strMigXml = "";
								int ret = -1;
								cmsLog.info("获取迁移任务单目标路径...");
								migXmlFile = getImportMigrationDest(
										packageFilesManager,
										amsstoragedirManager, sourceAmsStorage,
										sourceAmsStorageDir,
										sourceAmsStorageClass, progPackage,
										importDataPath, // 数据导入的源路径
										progPackagePath, // 节目包目录名称
										progListPath, // 栏目单目录名称
										progListDate, // 编单日期，格式：yyyyMMdd000000
										filecodeMigrationImport, // 迁移单xml的filecode
										stclasscodeMigrationImport // 迁移单xml的存储体等级code
								);
								cmsLog.info("迁移任务单目标路径 - " + migXmlFile);

								cmsLog.info("生成迁移任务单字符串...");
								strMigXml = getImportMigrationXml(
										packageFilesManager,
										amsstoragedirManager, sourceAmsStorage,
										sourceAmsStorageDir,
										sourceAmsStorageClass, progPackage,
										importDataPath, // 数据导入的源路径
										progPackagePath, // 节目包目录名称
										progListPath, // 栏目单目录名称
										progListDate, // 编单日期，格式：yyyyMMdd000000
										filecodeMigrationImport, // 迁移单xml的filecode
										stclasscodeMigrationImport, // 迁移单xml的存储体等级code
										destStclasscode, // 目标存储体等级code
										progfs, // 节目包的文件
										importfiles, filedates, operator // 当前操作人员
								);
								cmsLog.info("生成迁移任务单字符串完毕。");

								if (migXmlFile != null
										&& !migXmlFile.equalsIgnoreCase("")) {
									cmsLog.info("准备修改节目包文件夹名称...");

									strOldFile = progpackagePath
											+ progpackagefilename;
									strOldFile = fileoper.checkPathFormatRear(
											strOldFile, '/');
									newName = progPackageAdditional
											+ progpackagefilename;
									newName = fileoper.checkPathFormatRear(
											newName, '/');
									cmsLog.info("原：" + strOldFile);
									cmsLog.info("新：" + newName);
									List<String> rename = new ArrayList<String>();
									rename.add(strOldFile);
									rename.add(newName);
									renames.add(rename);
									ret = 0;
									cmsLog.warn("这里暂时不修改节目包的文件夹名字。");
									if (ret == 0) {
										// cmsLog.info("节目包文件夹改名成功。");

										if (migXmlFile != null
												&& !migXmlFile
														.equalsIgnoreCase("")) {
											if (strMigXml != null
													&& !strMigXml
															.equalsIgnoreCase("")) {
												cmsLog
														.info("准备生成迁移任务单xml文件...");
												ret = fileoper.createSmbFile(
														migXmlFile, strMigXml);
												if (ret == 0) {
													cmsLog
															.info("生成迁移任务单xml文件成功。");

													// 0 - 未处理,1 - 处理,8 -
													// 失败,9 - 成功
													cmsLog.info("生成迁移任务单成功。");
													progPackage
															.setDealstate((long) 1);
													// cmsLog.warn("测试代码，需要修改...");
													progPackageManager
															.update(progPackage);
													cmsLog
															.info("节目包的处理状态更新为1。");
												} else {
													String str = "生成迁移任务单xml文件失败。";
													cmsLog.warn(str);
													progPackage
															.setDealstate((long) 8);
													// cmsLog.warn("测试代码，需要修改...");
													progPackageManager
															.update(progPackage);
													cmsLog
															.info("节目包的处理状态更新为8。");
													// throw new
													// RuntimeException(str);
												}

											} else {
												cmsLog
														.info("迁移任务单字符串为空，不需要生成迁移任务单xml文件。");
											}
										} else {
											String str = "迁移单目标目录为空，不生成迁移单xml文件。";
											cmsLog.warn(str);
											// throw new
											// RuntimeException(str);
										}
									} else {
										// String str =
										// "节目包文件夹改名失败，回滚数据库操作。";
										// cmsLog.info("不生成迁移任务单xml文件。");
										// cmsLog.warn(str);
										// throw new RuntimeException(str);
									}
								} else {
									String str = "迁移任务单目标目录为空，不生成迁移任务单。";
									cmsLog.warn(str);
									progPackage.setDealstate((long) 8);
									// cmsLog.warn("测试代码，需要修改...");
									progPackageManager.update(progPackage);
									cmsLog.info("更新节目包的处理状态为8。");

									// throw new RuntimeException(str);
								}
							}
						}
					}
				}
			}
		} else {
			String str = "数据导入源目录中没有找到文件或文件夹，不操作。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode(Long.valueOf(1));
			cmsResultDto.setErrorMessage(str);
			return cmsResultDto;
		}
		cmsResultDto.setResultObject(renames);

		cmsLog
				.debug("Cms -> CmsTransaction -> saveImportProgPackageFilesToBjCms2 returns.");
		return cmsResultDto;
	}
	
	/**
	 * 报纸导入的事务管理
	 * @param programFilesManager 节目文件Manager
	 * @param programFile 
	 * @param packageManager 节目包Manager
	 * @param progPackage
	 * @param packageFilesManager 节目包文件关系Manager
	 * @param packageFile
	 * @param amsStoragePrgRelManager 文件位置表Manager
	 * @param amsStoragePrgRel
	 * @param pColumnRelManager 节目包栏目关系Manager
	 * @param ppColumnRel
	 * @param bpmcManager 操作记录Manager
	 * @return
	 */
	public void savePager(
			IProgramFilesManager programFilesManager, ProgramFiles programFile, 
			IProgPackageManager packageManager, ProgPackage progPackage,
			IPackageFilesManager packageFilesManager, PackageFiles packageFile,
			IAmsStoragePrgRelManager amsStoragePrgRelManager, AmsStoragePrgRel amsStoragePrgRel,
			IPPColumnRelManager pColumnRelManager, PPColumnRel ppColumnRel,
			IBpmcManager bpmcManager) {
		programFilesManager.save(programFile);
		progPackage = (ProgPackage) packageManager.save(progPackage);
		packageFilesManager.save(packageFile);
		amsStoragePrgRelManager.save(amsStoragePrgRel);
		pColumnRelManager.save(ppColumnRel);
		
		Bpmc bpmc = new Bpmc();
		bpmc.setObjectid(progPackage.getProductid());
		bpmc.setSender(progPackage.getInputmanid());
		bpmc.setSends("");
		bpmc.setReceiver("");
		bpmc.setReceives("");
		bpmc.setSenddate(new Date());
		bpmc.setSendremark("报纸导入");
		bpmc.setState("N");
		
		bpmcManager.save(bpmc);
	}

	public static void main(String[] args) {
		List list = new CmsTransactionManagerImpl()
				.getProgListDetailsByXml("smb://bunco:fmango@127.0.0.1/Downloads/yx_dsj.xml");
		List l = (List) list.get(1);
		for (Object o : l) {
			ProgListDetail progListDetail = (ProgListDetail) o;
			System.out.println(progListDetail.getProductname());
		}
	}
}
