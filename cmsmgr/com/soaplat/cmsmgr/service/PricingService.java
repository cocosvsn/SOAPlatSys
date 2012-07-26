/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProductProgRel;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListMangDetail;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgProduct;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.dto.ProductProgRelVo;
import com.soaplat.cmsmgr.manageriface.ICmsSiteProductRelManager;
import com.soaplat.cmsmgr.manageriface.IPricingManager;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.util.DateUtil;
import com.soaplat.cmsmgr.util.ListUtil;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-10-8 上午09:41:07
 */
public class PricingService {
	private static final Logger logger = Logger.getLogger("Cms");
	private final String currPricingActionId = "FU00000107";	// 定价流程活动ID
	private IPricingManager pricingManager;
	private IProgPackageManager progPackageManager;
	private IProductInfoManager productInfoManager;
	private IProgListMangManager progListMangManager;
	private IProgListDetailManager progListDetailManager;
	private ICmsSiteProductRelManager cmsSiteProductRelManager;
	private IProgListMangDetailManager progListMangDetailManager;
	
	public PricingService() {
		this.pricingManager = (IPricingManager) ApplicationContextHolder
				.webApplicationContext.getBean("pricingManager");
		this.progPackageManager = (IProgPackageManager) ApplicationContextHolder
				.webApplicationContext.getBean("progPackageManager");
		this.productInfoManager = (IProductInfoManager) ApplicationContextHolder
				.webApplicationContext.getBean("productinfoManager");
		this.progListMangManager = (IProgListMangManager) ApplicationContextHolder
				.webApplicationContext.getBean("progListMangManager");
		this.progListDetailManager = (IProgListDetailManager) ApplicationContextHolder
				.webApplicationContext.getBean("progListDetailManager");
		this.cmsSiteProductRelManager = (ICmsSiteProductRelManager) ApplicationContextHolder
				.webApplicationContext.getBean("cmsSiteProductRelManager");
		this.progListMangDetailManager = (IProgListMangDetailManager) ApplicationContextHolder
				.webApplicationContext.getBean("progListMangDetailManager");
	}
	
	/**
	 * 根据节目包ID 查询所有的包月产品关系信息, 及该节目包的产品信息
	 * @param progPackageId 节目包( ProgPackage )ID
	 * @param progDate 编单日期
	 * @return Object[]
	 * object[0]: List<TProductInfo> 节目包产品信息集合
	 * object[1]: List<ProductProgRelVo> 用于前台显示的订价关系信息集合
	 * @throws ParseException 
	 */
	public Object[] queryProductInfos(String progPackageId, Date progDate) 
			throws ParseException {
		List<TProductInfo> productInfos = this.pricingManager
				.queryProductInfos(progPackageId, progDate);
		List<ProductProgRelVo> productProgRels = this.pricingManager
				.queryAllProductProgRelVos(progPackageId, 
						DateUtil.parseDate("yyyy", "2099"));
		return new Object[] {productInfos, productProgRels};
	}
	
	/**
	 * 查询所有有效的包月产品信息
	 * @return List<ProgProduct> 返回有效包月产品信息集合
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws ParseException 
	 */
	public List<ProgProduct> queryAllProgProducts(String progPackageId) 
			throws SecurityException, IllegalArgumentException, 
					NoSuchFieldException, NoSuchMethodException, 
							IllegalAccessException, InvocationTargetException, 
									ParseException {
		ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(progPackageId);
		List<ProductProgRel> productProgRels = this.pricingManager
				.queryAllProductProgRels(progPackageId, DateUtil.parseDate("yyyy", "2099"));
		List<String> keyIds = ListUtil.getPropertiesList(productProgRels, "keyId");
		logger.debug("根据节目包ID[ " + progPackageId + " ]查询得出节目包[ " + progPackage.getProductname() + " ]已绑定的产品数量: " + keyIds.size() + ArrayUtils.toString(keyIds.toArray()));
		if (1 > keyIds.size()) {
			keyIds.add("*");
		}
		List<ProgProduct> products = this.pricingManager.queryAllProgProducts(
				keyIds, progPackage.getSiteCode());
		logger.debug("查询得出包月产品数量: " + products.size());
		return products;
	}
	
	/**
	 * 添加节目包产品信息
	 * @param productInfo 节目包产品信息
	 * @return Object[]
	 * object[0]: List<TProductInfo> 节目包产品信息集合
	 * object[1]: List<ProductProgRelVo> 用于前台显示的订价关系信息集合
	 * @throws Exception 
	 */
	public Object[] addProductInfo(List<ProductProgRelVo> productProgRels,
			TProductInfo productInfo) throws Exception {
		
		/**
		 * 转换VO为节目包包月产品关系信息
		 */
		List<ProductProgRel> progRels = new ArrayList<ProductProgRel>();
		for (ProductProgRelVo productProgRelVo : productProgRels) {
			ProductProgRel progRel = new ProductProgRel();
			progRel.setProgPackageId(productProgRelVo.getProgPackageId());
			progRel.setKeyId(productProgRelVo.getKeyId());
			progRel.setKeyName(productProgRelVo.getKeyName());
			progRel.setValiDate(productProgRelVo.getValiDate());
			progRel.setOperator(productProgRelVo.getOperator());
			progRel.setInputDate(new Date());
			progRel.setRemark(productProgRelVo.getRemark());
			
			progRels.add(progRel);
		}
		
		productInfo.setEncryptState(0L);
		ListUtil.sortByProperty(progRels, "valiDate");
		this.pricingManager.saveProductInfo(productInfo, 
				progRels.toArray(new ProductProgRel[] {}));
		
		return this.queryProductInfos(productInfo.getProgPackageId(), 
				productInfo.getValidDate());
	}
	
	/**
	 * 应用产品信息到编单明细
	 * @param progListDetailId 编单明细ID
	 * @param productInfoId 产品信息ID
	 */
	public String applyPricing(String progListDetailId, String productInfoId) {
		ProgListDetail progListDetail = (ProgListDetail) 
				this.progListDetailManager.getById(progListDetailId);
		ProgPackage progPackage = (ProgPackage) this.progPackageManager.getById(
				progListDetail.getProductid());
		List<ProgListDetail> progListDetails = this.progListDetailManager.queryProgListDetailsByScheduleDateAndPackageId(
				progListDetail.getScheduledate(), progListDetail.getProductid());

		TProductInfo productInfo = this.productInfoManager.getTProductInfoById(productInfoId);
		if (null == productInfo.getKeyId()) {
			return " 该订价未应用到任何产品 KeyID 不符合订价要求, 请重新订价! ";
		}
		if (null == progPackage.getSiteCode()) {
			List<String> productInfoKeyIds = Arrays.asList(productInfo.getKeyId().split(","));
			List<String> keyIdsSiteCodes = this.cmsSiteProductRelManager.getSiteCodeByKeyIds(productInfoKeyIds);
			List<String> progListSiteCode = this.progListDetailManager.querySiteCodeByScheduleDateAndPackageId(
					progListDetail.getScheduledate(), progListDetail.getProductid());
			if (keyIdsSiteCodes.containsAll(progListSiteCode)) {
				for (ProgListDetail detail : progListDetails) {
					detail.setProductInfoID(productInfoId);
					this.progListDetailManager.update(detail);
				}
				return null;
			} else {
				return "该节目包产品信息只存在于单一品牌中, 请重新订价!";
			}
		}
		
		
		
		for (ProgListDetail detail : progListDetails) {
			detail.setProductInfoID(productInfoId);
			this.progListDetailManager.update(detail);
		}
		return null;
	}
	
	/**
	 * 删除产品订价信息
	 * @param pricingId 产品 (TProductInfo) id 
	 * @param progPackageId 节目包ID
	 * @param progDate 编单日期
	 * @return Object[]
	 * object[0]: List<TProductInfo> 节目包产品信息集合
	 * object[1]: List<ProductProgRelVo> 用于前台显示的订价关系信息集合
	 * @throws Exception 
	 */
	public Object[] delProductInfo(List<String> pricingIds, 
			String progPackageId, Date progDate) 
					throws Exception {
		for (String string : pricingIds) {
			List<ProgListDetail> progListDetails = this.progListDetailManager
					.queryProgListDetailByProductInfoID(string);
			if (0 < progListDetails.size()) {
				List<String> scheduleDateStrList = ListUtil.getPropertiesList(
						progListDetails, "scheduledate");
				List<ProgListMangDetail> progListMangDetails = this.progListMangDetailManager
						.queryDetailsByScheduleDate(scheduleDateStrList);
				
				for (ProgListMangDetail progListMangDetail : progListMangDetails) {
					/**
					 * 产品信息被使用, 并且流程活动不是产品订价, 则不允许删除
					 */
					if (!"FU00000076".equalsIgnoreCase(progListMangDetail.getIdAct())) {
						throw new Exception(
								"产品信息被使用, 并且流程活动不是产品订价, 不允许删除, 产品ID: " 
										+ string);
					}
				}
			} else {
				this.pricingManager.delProductInfo(string);
			}
		}
		
		return this.queryProductInfos(progPackageId, progDate);
	}
	
	/**
	 * 节目包订价完成, 发送栏目编单到下一个流程活动
	 * @param dateStr 编单日期, 格式: 2010-09-09
	 * @param columnID
	 * @param operator
	 * @throws Exception 
	 */
	public String finishPricing(String dateStr, String columnID, String operator) 
			throws Exception {
		String date = dateStr.replaceAll("-", "") + "000000";
		List<ProgListDetail> progListDetails = this.progListDetailManager
				.queryDetailsByScheduleAndColumnId(date, columnID);
		if (0 < progListDetails.size()) {
			for (ProgListDetail progListDetail : progListDetails) {
				if (null == progListDetail.getScheduledate() 
						|| 12 != progListDetail.getProductInfoID().trim().length()) {
					return "节目包: " + progListDetail.getProductname() 
							+ "未应用到订价信息, 不能发送流程活动!";
				}
			}
		}
		this.progListMangManager.updateProgAct(this.currPricingActionId, 
					date, columnID, "P", operator);
		return null;
	}
	
	public void backPricingProcess(String dateStr, String columnID, 
			String operator) throws Exception {
		String date = dateStr.replaceAll("-", "") + "000000";
		this.progListMangManager.updateProgAct(this.currPricingActionId, 
				date, columnID, "P", operator);
	}
	
	/**
	 * 批量订价
	 * @param progPackageId 批量订价的节目包
	 * @param productId 批量订价的包月产品ID
	 * @param inputManId 操作人员编号
	 * @return
	 * @throws ParseException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public String batchPricing(List<String> progPackageIds, 
			List<String> productIds, String operator) throws ParseException, 
			SecurityException, IllegalArgumentException, NoSuchFieldException, 
			NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		CmsConfig cmsConfig = new CmsConfig();
		String defaultPricingProductName = cmsConfig.getPropertyByName("DefaultPricingProductName").trim();
		String defaultValidateStr = cmsConfig.getPropertyByName("DefaultPricingValidate");
		String defaultInvalidateStr = cmsConfig.getPropertyByName("DefaultPricingInvalidate");
		Date defaultValiDate = DateUtil.parseDate("yyyy-MM-dd", defaultValidateStr);
		Date defaultInvaliDate = DateUtil.parseDate("yyyy-MM-dd", defaultInvalidateStr);
		Date currDate = new Date();
		List<ProgProduct> progProducts = this.pricingManager.queryProductsByIds(productIds);
		List<String> keyIds = ListUtil.getPropertiesList(progProducts, "keyId");
		// 1. 循环所有的节目包
		// 2. 为每个节目包应用包月产品
		for (String progPackageId : progPackageIds) {
			TProductInfo productInfo = new TProductInfo();
			productInfo.setProgPackageId(progPackageId);
			productInfo.setEncryptState(0L);
			productInfo.setInputTime(currDate);
			productInfo.setOperator(operator);
			productInfo.setProductName(0 == defaultPricingProductName.length() 
					? null : defaultPricingProductName);
			productInfo.setValidDate(defaultValiDate);
			productInfo.setInvalidDate(defaultInvaliDate);
			
			List<ProductProgRel> progRels = new ArrayList<ProductProgRel>();
			for (ProgProduct product : progProducts) {
				ProductProgRel progRel = new ProductProgRel();
				progRel.setProgPackageId(progPackageId);
				progRel.setKeyId(product.getKeyId());
				progRel.setKeyName(product.getKeyName());
				progRel.setValiDate(defaultValiDate);
				progRel.setOperator(operator);
				progRel.setInputDate(currDate);
				progRels.add(progRel);
			}
			
			try {
				this.pricingManager.saveProductInfo(productInfo, progRels.toArray(new ProductProgRel[] {}));
			} catch (Exception e) {
				logger.error(e);
				return "节目包[ " + progPackageId + " ]订价失败!";
			}
		}
		return null;
	}
	
	/**
	 * 批量应用订价
	 * @param scheduleStr 编单日期
	 * @param columnId 栏目编号
	 * @return
	 */
	public String batchApplyPricing(String scheduleStr, String columnId) {
		logger.debug("批量应用订价: ");
		String scheduleDate = scheduleStr.replaceAll("[-/]", "") + "000000";
		List<ProgListDetail> exitProgListDetails = this.progListDetailManager.queryDetailsByScheduleAndColumnId(
				scheduleDate, columnId);
		List<ProgListDetail> progListDetails = new ArrayList<ProgListDetail>();
		
		TProductInfo productInfo = null;
		for (ProgListDetail progListDetail : exitProgListDetails) {
			productInfo = this.productInfoManager.queryProductInfoByProgPackageId(
					progListDetail.getProductid());
			if (null == productInfo) {
				continue;
			} else {
				if (null == productInfo.getKeyId()) {
					logger.debug("节目包[ " + progListDetail.getProductname() + " ]的订价[ " + productInfo.getId() + " ]未应用到任何产品 KeyID 不符合订价要求, 编单细表ID[ " + progListDetail.getProglistdetailid() + " ]");
					return " 节目包[ " + progListDetail.getProductname() + " ]的订价未应用到任何产品 KeyID 不符合订价要求, 请重新订价! ";
				}
				logger.debug("应用订价==节目包: " + progListDetail.getProductid() + " \t产品ID: " + productInfo.getId());
				progListDetail.setProductInfoID(productInfo.getId());
				progListDetails.add(progListDetail);
			} 
		}
		
		try {
			this.progListDetailManager.update(progListDetails.toArray());
		} catch (Exception e) {
			logger.error(e);
			return "栏目[ " + columnId + " ]批量应用订价失败!";
		}
		return null;
	}
	
	/**
	 * 取消订价
	 * @param progListDetailIds
	 * @param operator
	 * @return
	 */
	public String cancelPricing(List<String> progListDetailIds, String operator) {
		if (0 >= progListDetailIds.size()) {
			return "操作错误:\n\t未选择取消订价的节目包!";
		}
		
		for (String progListDetailId : progListDetailIds) {
			ProgListDetail progListDetail = (ProgListDetail) this.progListDetailManager.getById(progListDetailId);
			if (null != progListDetail.getProductInfoID()
					&& 12 == progListDetail.getProductInfoID().trim().length()) {
				logger.debug("取消编单[ " + progListDetail.getProglistdetailid() + " : " 
						+ progListDetail.getProductInfoID() + " ]中节目包[ " 
						+ progListDetail.getProductid() + " : " 
						+ progListDetail.getProductName() + " ]应用订价! ");
				progListDetail.setProductInfoID(null);
				try {
					this.progListDetailManager.update(progListDetail);					
				} catch (Exception e) {
					logger.error("取消应用订价失败: " + e);
					return "节目包[ " + progListDetail.getProductName() + " ]取消应用订价失败!";
				}
			} else {
				logger.debug("编单[ " + progListDetail.getProglistdetailid() + " : " 
						+ progListDetail.getProductInfoID() + " ]中节目包[ " 
						+ progListDetail.getProductid() + " : " 
						+ progListDetail.getProductName() + " ]产品ID不是有效产品编号!");
			}
		}
		return null;
	}
	
	/**
	 * 删除包月产品
	 * @param progProductId 产品ID集合
	 * @param progPackageId 节目包ID
	 * @param operator 操作人员编号
	 * @return
	 * @throws ParseException
	 */
	public String deleteProgProduct(List<String> progProductIds, 
			String progPackageId, String operator) throws ParseException {
		String returnStr = null;
		String scheduleDate = DateUtil.getAfterScheduleDate(0);
		List<String> existScheduleDates = 
				this.progListDetailManager.queryScheduleDateByScheduleDateAndProgPackage(
						scheduleDate, progPackageId);
		if (0 < existScheduleDates.size()) {
			List<String> existScheduleDateStrings = new ArrayList<String>();
			for (String string : existScheduleDates) {
				existScheduleDateStrings.add(
						DateUtil.getDateStr("yyyy-MM-dd", 
						DateUtil.parseDate("yyyyMMddHHmmss", string)));
			}
			returnStr = "节目包存在于编单[ " + ListUtil.toString(existScheduleDateStrings) 
					+ " ]中, 不允许删除!";
			logger.debug(returnStr);
			return returnStr;
		} else {
			try {
//				this.pricingManager.deleteProductProgRelByProgPackageIdAndProgProductIds(
//					progPackageId, progProductIds);
				this.pricingManager.deleteProductProgRelById(progProductIds);
				logger.debug("当前用户[ " + operator + " ]删除节目包[ " 
						+ progPackageId + " ]产品[ " + ListUtil.toString(progProductIds) + " ]成功!");
			} catch (Exception e) {
				logger.error("当前用户[ " + operator + " ]删除节目包[ " 
						+ progPackageId + " ]产品[ " + ListUtil.toString(progProductIds) + " ]失败!");
				logger.error(e);
			}
		}
		
		CmsConfig cmsConfig = new CmsConfig();
		String defaultPricingProductName = cmsConfig.getPropertyByName("DefaultPricingProductName").trim();
		String defaultValidateStr = cmsConfig.getPropertyByName("DefaultPricingValidate");
		String defaultInvalidateStr = cmsConfig.getPropertyByName("DefaultPricingInvalidate");
		Date defaultValiDate = DateUtil.parseDate("yyyy-MM-dd", defaultValidateStr);
		Date defaultInvaliDate = DateUtil.parseDate("yyyy-MM-dd", defaultInvalidateStr);
		Date currDate = new Date();
		// 1. 为节目包应用包月产品
		TProductInfo productInfo = new TProductInfo();
		productInfo.setProgPackageId(progPackageId);
		productInfo.setEncryptState(0L);
		productInfo.setInputTime(currDate);
		productInfo.setOperator(operator);
		productInfo.setProductName(0 == defaultPricingProductName.length() 
				? null : defaultPricingProductName);
		productInfo.setValidDate(defaultValiDate);
		productInfo.setInvalidDate(defaultInvaliDate);
		
		List<ProductProgRel> progRels = this.pricingManager.queryAllProductProgRels(
				progPackageId, defaultInvaliDate);
		
		try {
			this.pricingManager.saveProductInfo(productInfo, progRels.toArray(new ProductProgRel[] {}));
		} catch (Exception e) {
			returnStr = "节目包[ " + progPackageId + " ]订价失败!";
			logger.error(e);
			return returnStr;
		}
		return returnStr;
	}
	
	public void test() throws Exception {
//		List<ProductProgRelVo> productProgRels = new ArrayList<ProductProgRelVo>();
//		ProductProgRelVo productProgRel1 = new ProductProgRelVo("PPVP20100526114651000265", "101包月产品", "101", "10", DateUtil.parseDate("yyyy-MM-dd", "2010-10-13"), "OP0000000024");
//		ProductProgRelVo productProgRel2 = new ProductProgRelVo("PPVP20100526114651000265", "102包月产品", "102", "10", DateUtil.parseDate("yyyy-MM-dd", "2010-10-13"), "OP0000000024");
//		ProductProgRelVo productProgRel3 = new ProductProgRelVo("PPVP20100526114651000265", "103包月产品", "103", new Date(), "OP0000000024");
//		productProgRels.add(productProgRel1);
//		productProgRels.add(productProgRel2);
//		
//		TProductInfo productInfo = new TProductInfo();
//		productInfo.setExpired(new Date());
//		productInfo.setInvalidDate(new Date());
//		productInfo.setIppvId("1111");
//		productInfo.setKeyId("101,102");
//		productInfo.setKeyUpdateTime(new Date());
//		productInfo.setOperator("OP000034");
//		productInfo.setPrice("10");
//		productInfo.setPriceId("000000000001");
//		productInfo.setProductName("精武风云");
//		productInfo.setProductStyleId("101");
//		productInfo.setProgPackageId("PPVP20100526114651000265");
//		productInfo.setValidDate(new Date());
//		productInfo.setSlotId("");
//		productInfo.setWatchDuration("");
//		productInfo.setWatchTimes("");
//		productInfo.setWatchTraffice("");
//		
//		this.addProductInfo(productProgRels, productInfo);
	
//		List<String> progPackageIds = new ArrayList<String>();
//		progPackageIds.add("PPVP20110720171824000281");
//		progPackageIds.add("PPVP20110720170919000121");
//		progPackageIds.add("PPVP20110720170247000067");
//		List<String> productIds = new ArrayList<String>();
//		productIds.add("000000000001");
//		this.batchPricing(progPackageIds, productIds, "");
		
		this.batchApplyPricing("2011-07-20", "PC00000003");
		
//		Object[] objects = this.queryProductInfos("PPVP20100526114651000265", DateUtil.parseDate("yyyy", "2099"));
//		System.out.println(objects[0]);
//		System.out.println(objects[1]);
		
//		
//		List<ProgProduct> progProducts = this.queryAllProgProducts("PPVP20100526133255000312");
//		System.out.println(progProducts.size());
	}
}
