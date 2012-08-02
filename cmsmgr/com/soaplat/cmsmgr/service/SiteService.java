package com.soaplat.cmsmgr.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import cn.sh.sbl.cms.listener.ServerConfigListener;

import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgProduct;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.manageriface.ICmsSiteManager;
import com.soaplat.cmsmgr.manageriface.ICmsSiteProductRelManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IPricingManager;
import com.soaplat.cmsmgr.manageriface.IProductManager;
import com.soaplat.cmsmgr.util.ParamCheck;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class SiteService {
	private final String ATTRIBUTE = "{0}:''{1}''";
	private final String ATTRIBUTENUMBER = "{0}:{1}";
//    private final String ATTRIBUTEOBJECT = "{0}:'{'{1}'}'";
    private final String ARRAYOBJECT = "{0}:[{1}]";
//    private final String STRING = "''{0}''";
    
	private static final Logger logger = Logger.getLogger("Cms");
	private FileOperationImpl fileOperationImpl;
	private IPricingManager pricingManager;
	private ICmsSiteManager cmsSiteManager;
	private IProductManager productManager;
	private IPortalColumnManager portalColumnManager;
	private IPackageFilesManager packageFilesManager;
	private ICmsSiteProductRelManager cmsSiteProductRelManager;
	
	
	public SiteService() {
		this.fileOperationImpl = new FileOperationImpl();
		this.pricingManager = (IPricingManager) ApplicationContextHolder
				.webApplicationContext.getBean("pricingManager");
		this.cmsSiteManager = (ICmsSiteManager) ApplicationContextHolder
				.webApplicationContext.getBean("cmsSiteManager");
		this.productManager = (IProductManager) ApplicationContextHolder
				.webApplicationContext.getBean("cmsProductManager");
		this.portalColumnManager = (IPortalColumnManager) ApplicationContextHolder
				.webApplicationContext.getBean("portalColumnManager");
		this.packageFilesManager = (IPackageFilesManager) ApplicationContextHolder
				.webApplicationContext.getBean("packageFilesManager");
		this.cmsSiteProductRelManager = (ICmsSiteProductRelManager) ApplicationContextHolder
				.webApplicationContext.getBean("cmsSiteProductRelManager");
	}
	
	public String addSite(CmsSite cmsSite) {
		if (logger.isDebugEnabled()) {
			logger.debug("\tCmsSite.siteid: " + cmsSite.getSiteid());
			logger.debug("\tCmsSite.sitename: " + cmsSite.getSitename());
			logger.debug("\tCmsSite.siteCode: " + cmsSite.getSiteCode());
			logger.debug("\tCmsSite.epgpath: " + cmsSite.getEpgpath());
			logger.debug("\tCmsSite.epgip: " + cmsSite.getEpgip());
			logger.debug("\tCmsSite.updateTime: " + cmsSite.getUpdateTime());
			logger.debug("\tCmsSite.remark: " + cmsSite.getRemark());
		}
		
		/**
		 * 参数检测
		 */
		// 检测品牌名称
		if (!ParamCheck.hasText(cmsSite.getSitename(), "用户分组名称不能为空, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(cmsSite.getSitename(), 255, "用户分组名称长度超出255字符!")) {
			logger.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		// 检测品牌Code
		if (!ParamCheck.hasText(cmsSite.getSiteCode(), "用户分组Code不能为空, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(cmsSite.getSiteCode(), 255, "用户分组Code长度超出255字符!")) {
			logger.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		// 检测品牌节目预告文件名
//		if (!ParamCheck.hasText(cmsSite.getEpgpath(), "节目预告文件名不能为空, 至少包含一个非空字符!")
//				|| !ParamCheck.lessThenOrEqualToLength(cmsSite.getEpgpath(), 255, "节目预告文件名长度超出255字符!")) {
//			logger.info(ParamCheck.getErrMsg());
//			return ParamCheck.getErrMsg();
//		}
		// 检测品牌Code或节目包预告文件名是否存在
		if (this.cmsSiteManager.isExistsSite(cmsSite)) {
			logger.warn("用户分组Code已存在!");
			return "用户分组Code已存在, 请重新输入!";
		}
		
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"d_column", "Online");
		if (null == descPath || 2 > descPath.size()) {
			logger.warn(" 查询一级库JS目录失败! ");
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		String onlineJsPath = (String) descPath.get(0);
		onlineJsPath = onlineJsPath.substring(0, onlineJsPath.indexOf("D_COLUMN"));         //fileOperationImpl.checkPathFormatRear(onlineJsPath, '/');
		if (null == cmsSite.getFilepath() || 1 > cmsSite.getFilepath().trim().length()) {
			fileOperationImpl.checkSmbDir(onlineJsPath + cmsSite.getSiteCode() + "/");
		} else {
			String[] fileNames = cmsSite.getFilepath().split(";");
			for (String string : fileNames) {
				if(0 != fileOperationImpl.copyFileFromLocalToSmb(
						ServerConfigListener.TEMP_PATH + string, 
						onlineJsPath + cmsSite.getSiteCode() + "/" + string)) {
					return "添加分组失败, 文件拷贝错误!";
				}
			}
		}
		
		try {
			cmsSite.setUpdateTime(new Date());
			this.cmsSiteManager.saveCmsSite(cmsSite);
//			this.portalColumnManager.updateRootTime();
//			this.generateSiteJS();
//			if (null == cmsSite.getFilepath() 
//					|| 1 > cmsSite.getFilepath().trim().length()) {
//				return "用户分组添加成功, 但未上传用户分组文件!";
//			}
			return null;
		} catch (Exception e) {
			logger.info("用户分组添加失败: " + e);
			return " 用户分组添加失败! ";
		}
	}
	
	public String delSite(String id) {
		if (logger.isDebugEnabled()) {
			logger.debug("\tParams.id: " + id);
		}
		CmsSite cmsSite = null;
		try {
//			List<ProgProduct> products = this.getProgProducts(id);
//			logger.debug("products.size(): " + products.size());
//			if (0 < products.size()) {
//				return "用户分组正在被产品包使用中, 不允许删除!";
//			} else {
				cmsSite = (CmsSite) this.cmsSiteManager.getById(id);
//				List<PortalColumn> columns = this.portalColumnManager.getPortalColumnsBySiteCode(
//						cmsSite.getSiteCode());
//				if (0 < columns.size()) {
//					return "用户分组正在被栏目使用中, 不允许删除!";
//				}
//			}
			
			this.cmsSiteManager.deleteCmsSite(cmsSite);
			List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
					"d_column", "Online");
			if (null == descPath || 2 > descPath.size()) {
				logger.warn(" 查询一级库JS目录失败! ");
				throw new NullPointerException(" 查询一级库JS目录失败! ");
			}
			String onlineJsPath = (String) descPath.get(0);
			onlineJsPath = onlineJsPath.substring(0, onlineJsPath.indexOf("D_COLUMN"));
			fileOperationImpl.deleteSmbFile(onlineJsPath + cmsSite.getSiteCode() + "/");
//			this.portalColumnManager.updateRootTime();
//			this.generateSiteJS();
			return null;
		} catch (Exception e) {
			logger.info("用户分组删除失败: ", e);
			return " 用户分组删除失败! ";
		}
	}
	
	public String updateSite(CmsSite cmsSite) {
		if (logger.isDebugEnabled()) {
			logger.debug("\tCmsSite.siteid: " + cmsSite.getSiteid());
			logger.debug("\tCmsSite.sitename: " + cmsSite.getSitename());
			logger.debug("\tCmsSite.siteCode: " + cmsSite.getSiteCode());
			logger.debug("\tCmsSite.epgpath: " + cmsSite.getEpgpath());
			logger.debug("\tCmsSite.epgip: " + cmsSite.getEpgip());
			logger.debug("\tCmsSite.updateTime: " + cmsSite.getUpdateTime());
			logger.debug("\tCmsSite.remark: " + cmsSite.getRemark());
		}
		
		/**
		 * 参数检测
		 */
		// 检测品牌名称
		if (!ParamCheck.hasText(cmsSite.getSitename(), "用户分组名称不能为空, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(cmsSite.getSitename(), 255, "用户分组名称长度超出255字符!")) {
			logger.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		// 检测品牌Code
		if (!ParamCheck.hasText(cmsSite.getSiteCode(), "用户分组Code不能为空, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(cmsSite.getSiteCode(), 255, "用户分组Code长度超出255字符!")) {
			logger.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		// 检测品牌节目预告文件名
//		if (!ParamCheck.hasText(cmsSite.getEpgpath(), "节目预告文件名不能为空, 至少包含一个非空字符!")
//				|| !ParamCheck.lessThenOrEqualToLength(cmsSite.getEpgpath(), 255, "节目预告文件名长度超出255字符!")) {
//			logger.info(ParamCheck.getErrMsg());
//			return ParamCheck.getErrMsg();
//		}
		
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"d_column", "Online");
		if (null == descPath || 2 > descPath.size()) {
			logger.warn(" 查询一级库JS目录失败! ");
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		String onlineJsPath = (String) descPath.get(0);
		onlineJsPath = onlineJsPath.substring(0, onlineJsPath.indexOf("D_COLUMN"));         //fileOperationImpl.checkPathFormatRear(onlineJsPath, '/');
		String[] fileNames = null == cmsSite.getFilepath() || 1 > cmsSite.getFilepath().trim().length()
				? new String[] {} : cmsSite.getFilepath().split(";");
		for (String string : fileNames) {
			if(0 != fileOperationImpl.copyFileFromLocalToSmb(
					ServerConfigListener.TEMP_PATH + string, 
					onlineJsPath + cmsSite.getSiteCode() + "/" + string)) {
				return "修改分组失败, 文件拷贝错误!";
			}
		}
		
		try {
//			List<ProgProduct> products = this.getProgProducts(cmsSite
//					.getSiteid());
			CmsSite oldCmsSite = (CmsSite) this.cmsSiteManager.getById(cmsSite
					.getSiteid());
			if (!oldCmsSite.getSiteCode().equals(cmsSite.getSiteCode())) {
				// 检测品牌Code或节目包预告文件名是否存在
				if (this.cmsSiteManager.isExistsSite(cmsSite)) {
					logger.warn("用户分组Code已存在!");
					return "用户分组Code已存在, 请重新输入!";
				}
			}
//			List<PortalColumn> columns = this.portalColumnManager
//					.getPortalColumnsBySiteCode(cmsSite.getSiteCode());
//			if (0 < columns.size()
//					&& !oldCmsSite.getSiteCode().equals(cmsSite.getSiteCode())) {
//				return "用户分组正在使用中, 不允许修改用户分组Code";
//			} else {
			if (null == cmsSite.getFilepath()
					|| 1 > cmsSite.getFilepath().trim().length()) {
				logger.debug("修改用户分组时未上传文件, 不修改filepath, 原filepath=" + oldCmsSite.getFilepath());
				cmsSite.setFilepath(oldCmsSite.getFilepath());
			} else {
				Set<String> fileNameSet = new HashSet<String>();
				fileNameSet.addAll(Arrays.asList(fileNames));
				if (null != oldCmsSite.getFilepath()) {
					fileNameSet.addAll(Arrays.asList(oldCmsSite.getFilepath().split(";")));
				}
				String filepath = "";
				for (String string : fileNameSet) {
					filepath += string + ";";
				}
				cmsSite.setFilepath(filepath);
			}
			cmsSite.setUpdateTime(new Date());
			this.cmsSiteManager.updateCmsSite(cmsSite);
//				this.portalColumnManager.updateRootTime();
//			}

//			if (null != oldCmsSite.getFilepath()
//					&& !oldCmsSite.getFilepath().equalsIgnoreCase(cmsSite.getFilepath())) {
//				
//			}
//			if (null == cmsSite.getFilepath()
//					|| 1 > cmsSite.getFilepath().trim().length()) {
//				return "用户分组修改成功, 未上传新的用户分组文件!";
//			}
//			this.generateSiteJS();
			return null;
		} catch (Exception e) {
			logger.info("用户分组修改失败: " + e);
			return " 用户分组修改失败! ";
		}
	}

	/**
	 * 添加品牌产品关系
	 * @param siteId 品牌ID
	 * @param productIds 产品列表
	 * @param inputManId 操作人员ID
	 * @return
	 */
	public String addSiteProductRel(String siteId, List<String> productIds, String inputManId) {
		try {
			this.cmsSiteProductRelManager.save(siteId, productIds, inputManId);
			this.portalColumnManager.updateRootTime();
			this.generateSiteJS();
			return null;
		} catch (Exception e) {
			logger.info("品牌产品关系添加失败: " + e);
			return " 品牌产品关系添加失败! ";
		}
	}
	
	/**
	 * 删除品牌产品关系
	 * @param siteId 品牌ID
	 * @param ids 产品列表
	 * @return
	 */
	public String delSiteProductRel(String siteId, List<String> ids) {
		try {
			List<String> keyIds = this.productManager.queryByids(ids);
			List<String> progPackageIds = this.pricingManager.queryProgPackageIdByKeyIdsSiteId(keyIds, siteId);
			logger.debug("绑定该产品的节目包数量: " + progPackageIds.size());
			if (0 < progPackageIds.size()) {
				return "[" + progPackageIds.size() + "]个节目包已绑定该产品, 不允许删除!";
			}
			this.cmsSiteProductRelManager.deleteById(siteId, ids);
			this.portalColumnManager.updateRootTime();
			this.generateSiteJS();
			return null;
		} catch (Exception e) {
			logger.debug("品牌产品关系删除失败: " + e.getMessage());
			return " 品牌产品关系删除失败! ";
		}
	}
	
	/**
	 * 根据品牌ID查询产品信息
	 * @param siteId
	 * @return
	 */
	public List<ProgProduct> getProgProducts(String siteId) {
		return this.productManager.queryProductsBySiteId(siteId);
	}
	
	/**
	 * 生成品牌JS
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public void generateSiteJS() throws Exception {
		logger.debug("生成品牌JS!");
		// 获利节目包js文件在一级库的存放路径
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"d_column", "Online");
		if (null == descPath || 2 > descPath.size()) {
			logger.warn(" 查询一级库JS目录失败! ");
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		String onlineJsPath = (String) descPath.get(0);
		onlineJsPath = fileOperationImpl.checkPathFormatRear(onlineJsPath, '/');
		
		List<CmsSite> cmsSites = this.cmsSiteManager.queryAllCmsSites();
		StringBuilder siteBuilder = new StringBuilder("[");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		for (CmsSite cmsSite : cmsSites) {
			List<ProgProduct> products = this.getProgProducts(cmsSite.getSiteid());
			StringBuilder productsBuilder = new StringBuilder();
			for (ProgProduct progProduct : products) {
				productsBuilder.append(progProduct.getKeyId());
				productsBuilder.append(",");
			}
			if (0 < productsBuilder.length()) {
				productsBuilder = productsBuilder.deleteCharAt(productsBuilder.length() - 1);
			}
			siteBuilder.append("{" +
					MessageFormat.format(this.ATTRIBUTENUMBER, "id", "gqpp".equals(cmsSite.getSiteCode()) ? 1 : 2) + "," +
					MessageFormat.format(this.ATTRIBUTE, "name", cmsSite.getSitename()) + "," +
					MessageFormat.format(this.ARRAYOBJECT, "products", productsBuilder.toString()) + "," +
					MessageFormat.format(this.ATTRIBUTE, "timestamp", simpleDateFormat.format(cmsSite.getUpdateTime())) + "},");
		}
		siteBuilder.setCharAt(siteBuilder.length() - 1, ']');
		siteBuilder.append(';');
		System.out.println(siteBuilder.toString());
		
		/**
		 * 根据技术部姜磊要求, 去掉js文件中的变量名部分
		 * HuangBo update by 2012年4月16日 10时28分
		 */
//		if (0 != fileOperationImpl.createSmbFileGb2312(onlineJsPath + "/brand.js", "var brands = " + siteBuilder.toString())) {
		if (0 != fileOperationImpl.createSmbFileGb2312(onlineJsPath + "/brand.js", siteBuilder.toString())) {
			throw new Exception("品牌JS生成失败!");
		}
	}
}
