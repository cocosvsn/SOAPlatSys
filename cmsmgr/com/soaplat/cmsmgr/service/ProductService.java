package com.soaplat.cmsmgr.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgProduct;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IProductManager;
import com.soaplat.cmsmgr.util.ParamCheck;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class ProductService {
	private final String ATTRIBUTE = "{0}:''{1}''";
	private final String ATTRIBUTE2 = "{0}:{1}";
//    private final String ATTRIBUTEOBJECT = "{0}:'{'{1}'}'";
//    private final String ARRAYOBJECT = "{0}:[{1}]";
//    private final String STRING = "''{0}''";
    
	private static final Logger logger = Logger.getLogger("Cms");
	private FileOperationImpl fileOperationImpl;
	private IProductManager productManager;
//	private IPricingManager pricingManager;
	private IPackageFilesManager packageFilesManager;
	private IPortalColumnManager portalColumnManager;
	
	public ProductService() {
		this.fileOperationImpl = new FileOperationImpl();
		this.productManager = (IProductManager) ApplicationContextHolder
				.webApplicationContext.getBean("cmsProductManager");
//		this.pricingManager = (IPricingManager) ApplicationContextHolder
//				.webApplicationContext.getBean("pricingManager");
		this.packageFilesManager = (IPackageFilesManager) ApplicationContextHolder
				.webApplicationContext.getBean("packageFilesManager");
		this.portalColumnManager = (IPortalColumnManager) ApplicationContextHolder
				.webApplicationContext.getBean("portalColumnManager");
	}
	
	/**
	 * 
	 * @param products
	 * @return
	 */
	public String addProduct(ProgProduct product) {
		if (logger.isDebugEnabled()) {
			logger.debug("\tProgProduct.id: " + product.getId());
			logger.debug("\tProgProduct.keyName: " + product.getKeyName());
			logger.debug("\tProgProduct.keyId: " + product.getKeyId());
			logger.debug("\tProgProduct.price: " + product.getPrice());
			logger.debug("\tProgProduct.valiDate: " + product.getValiDate());
			logger.debug("\tProgProduct.operator: " + product.getOperator());
			logger.debug("\tProgProduct.inputDate: " + product.getInputDate());
			logger.debug("\tProgProduct.remark: " + product.getRemark());
		}
		
		/**
		 * 参数检查
		 */
		// 检查产品名称是否合法
		if (!ParamCheck.hasText(product.getKeyName(), "产品名称不能为空, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(
						product.getKeyName(), 100, "产品名称长度超出100字符!")) {
			logger.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		// 检查产品KeyId是否合法
		if (!ParamCheck.hasText(product.getKeyId(), "产品KeyId不能为空, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(
						product.getKeyId(), 30, "产品KeyId长度超出30字符!")) {
			logger.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		
		try {
			this.productManager.saveProduct(product);
			this.portalColumnManager.updateRootTime();
			this.generateProductJS();
			return "产品添加成功, 未绑定栏目产品的关系!";
		} catch (Exception e) {
			logger.error("产品添加失败: " + e.getMessage());
			return " 产品添加失败! ";
		}
	}
	
	public String delProduct(String id) {
		if (logger.isDebugEnabled()) {
			logger.debug("\tProgProduct.id: " + id);
		}
		
		try {
			List<PortalColumn> portalColumns = this.productManager.queryColumnByProdcutId(id);
			logger.debug("产品绑定的栏目数量: " + portalColumns.size());
			if (0 < portalColumns.size()) {
				return "已绑定栏目产品关系, 不允许删除!";
			} 
//			else {
//				ProgProduct product = this.productManager.getById(id);
//				List<String> progPackageIds = this.pricingManager.queryProgPackageIdByKeyId(product.getKeyId());
//				logger.debug("绑定该产品的节目包数量: " + progPackageIds.size());
//				if (0 < progPackageIds.size()) {
//					return "[" + progPackageIds.size() + "]个节目包已绑定该产品, 不允许删除!";
//				}
//			}
//			return "暂时不允许删除!";
			this.productManager.delProduct(id);
			this.portalColumnManager.updateRootTime();
			this.generateProductJS();
			return null;
		} catch (Exception e) {
			logger.error("产品删除失败: " + e.getMessage());
			return " 产品删除失败! ";
		}
	}
	
	public String updateProduct(ProgProduct product) {
		if (logger.isDebugEnabled()) {
			logger.debug("\tProgProduct.id: " + product.getId());
			logger.debug("\tProgProduct.keyName: " + product.getKeyName());
			logger.debug("\tProgProduct.keyId: " + product.getKeyId());
			logger.debug("\tProgProduct.price: " + product.getPrice());
			logger.debug("\tProgProduct.valiDate: " + product.getValiDate());
			logger.debug("\tProgProduct.operator: " + product.getOperator());
			logger.debug("\tProgProduct.inputDate: " + product.getInputDate());
			logger.debug("\tProgProduct.remark: " + product.getRemark());
		}
		
		/**
		 * 参数检查
		 */
		// 检查产品名称是否合法
		if (!ParamCheck.hasText(product.getKeyName(), "产品名称不能为空, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(
						product.getKeyName(), 100, "产品名称长度超出100字符!")) {
			logger.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		// 检查产品KeyId是否合法
		if (!ParamCheck.hasText(product.getKeyId(), "产品KeyId不能为空, 至少包含一个非空字符!")
				|| !ParamCheck.lessThenOrEqualToLength(
						product.getKeyId(), 30, "产品KeyId长度超出30字符!")) {
			logger.info(ParamCheck.getErrMsg());
			return ParamCheck.getErrMsg();
		}
		
		try {
			ProgProduct progProduct = this.productManager.getById(product.getId());
			
			if (!progProduct.getKeyId().equals(product.getKeyId())) {
				return "产品的 KeyId 不允许修改!";
			}
			
			this.productManager.updateProduct(product);
			this.portalColumnManager.updateRootTime();
			this.generateProductJS();
			return null;
		} catch (Exception e) {
			logger.info("产品修改失败: " + e.getMessage());
			return " 产品修改失败! ";
		}
	}
	
	public List<ProgProduct> queryAllProduct() {
		return this.productManager.queryAllProducts();
	}
	
	/**
	 * 生成产品JS
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void generateProductJS() throws Exception {
		logger.debug("生成产品JS!");
		
		// 获利节目包js文件在一级库的存放路径
		List descPath = this.packageFilesManager.getDestPathByFilecodeStclasscode(
				"d_column", "Online");
		if (null == descPath || 2 > descPath.size()) {
			throw new NullPointerException(" 查询一级库JS目录失败! ");
		}
		String onlineJsPath = (String) descPath.get(0);
		onlineJsPath = fileOperationImpl.checkPathFormatRear(onlineJsPath, '/');
		
		List<ProgProduct> progProducts = this.productManager.queryAllProducts();
		StringBuilder productBuilder = new StringBuilder("[");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		for (ProgProduct progProduct : progProducts) {
			productBuilder.append("{" +
					MessageFormat.format(this.ATTRIBUTE2, "id", progProduct.getKeyId()) + "," +
					MessageFormat.format(this.ATTRIBUTE, "name", progProduct.getKeyName()) + "," +
					MessageFormat.format(this.ATTRIBUTE, "desc", null == progProduct.getRemark() ? "" : progProduct.getRemark()) + "," +
					MessageFormat.format(this.ATTRIBUTE, "timestamp", simpleDateFormat.format(progProduct.getInputDate())) + "},");
		}
		productBuilder.setCharAt(productBuilder.length() - 1, ']');
		productBuilder.append(';');
		
		/**
		 * 根据技术部姜磊要求, 去掉js文件中的变量名部分
		 * HuangBo update by 2012年4月16日 10时28分
		 */
//		if (0 != fileOperationImpl.createSmbFileGb2312(onlineJsPath + "/product.js", "var products = " + productBuilder.toString())) {
		if (0 != fileOperationImpl.createSmbFileGb2312(onlineJsPath + "/product.js", productBuilder.toString())) {
			throw new Exception("产品JS生成, 写入文件失败!");
		}
	}
	
	public void test() {
		System.out.println(queryAllProduct().size());
	}
}
