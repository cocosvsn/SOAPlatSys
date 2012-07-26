/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.soaplat.cmsmgr.bean.ProductProgRel;
import com.soaplat.cmsmgr.bean.ProgProduct;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.dto.ProductProgRelVo;
import com.soaplat.cmsmgr.manageriface.IPricingManager;
import com.soaplat.cmsmgr.util.DateUtil;
import com.soaplat.cmsmgr.util.ListUtil;
import com.soaplat.sysmgr.common.IBaseDAO;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-10-8 上午10:16:36
 */
public class PricingManagerImpl implements IPricingManager {
	private IBaseDAO baseDAO;
	
	@SuppressWarnings("unchecked")
	public List<TProductInfo> queryProductInfos(String progPackageId, Date valiDate) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("progPackageId", progPackageId);
		map.put("validDate", valiDate);
		List<TProductInfo> productInfos = this.baseDAO.queryByNamedQuery(
				"query.TProductInfo.by.progPackageId", map);
//		for (TProductInfo tProductInfo : productInfos) {
//			String[] keys = tProductInfo.getKeyId().split(",");
//			List<String> keyIdList = Arrays.asList(keys);
//			map.clear();
//			map.put("keyId", keyIdList);
//			List<ProgProduct> progProducts = (List<ProgProduct>) 
//					this.baseDAO.queryNamed("query.ProgProduct.by.keyId", map);
//			tProductInfo.setProgProducts(progProducts);
//		}
		return productInfos;
	}
	
	/**
	 * 根据包月产品ID集合查询包月产品列表
	 * @param ids 包月产品ID集合
	 * @return 返回包月产品集合
	 */
	@SuppressWarnings("unchecked")
	public List<ProgProduct> queryProductsByIds(List<String> ids) {
		String hql = "from ProgProduct as p where p.id in (:ids)";
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("ids", ids);
//		if (null != siteCode 
//				&& 0 != siteCode.trim().length()) {
//			if (hql.contains("where")) {
//				hql += "and ";
//			} else {
//				hql += "where ";
//			}
//			hql += "p.id in (select r.productId from CmsSiteProductRel as r where r.cmsSiteId in (select c.siteid from CmsSite as c where c.siteCode = :siteCode)) ";
//			map.put("siteCode", siteCode);
//		}
		hql += " order by p.valiDate"; 
		
//		return (List<ProgProduct>) this.baseDAO.queryNamed(
//				"query.all.validate.ProgProduct", map);
		return (List<ProgProduct>) this.baseDAO.query(hql, map);
	}
	
	/**
	 * 查询所有包月产品信息
	 * @return List<ProgProduct> 返回包月产品集合
	 */
	@SuppressWarnings("unchecked")
	public List<ProgProduct> queryAllProgProducts(List<String> keyIds, String siteCode) {
		String hql = "from ProgProduct as p where p.keyId not in (:keyIds) and p.valiDate <= :valiDate ";
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("keyIds", keyIds);
		map.put("valiDate", new Date());
		
		if (null != siteCode 
				&& 0 != siteCode.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.id in (select r.productId from CmsSiteProductRel as r where r.cmsSiteId in (select c.siteid from CmsSite as c where c.siteCode = :siteCode)) ";
			map.put("siteCode", siteCode);
		}
		hql += " order by p.keyId"; 
		
//		return (List<ProgProduct>) this.baseDAO.queryNamed(
//				"query.all.validate.ProgProduct", map);
		return (List<ProgProduct>) this.baseDAO.query(hql, map);
	}
	
	/**
	 * 查询所有节目包订价关系表信息
	 * @param progPackageId 节目包ID
	 * @param valiDate 有效日期
	 * @return List<ProductProgRel> 返回指定节目包订价关系表信息
	 */
	@SuppressWarnings("unchecked")
	public List<ProductProgRel> queryAllProductProgRels(String progPackageId,
			Date valiDate) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("progPackageId", progPackageId);
		map.put("valiDate", valiDate);
		return this.baseDAO.queryByNamedQuery(
				"query.all.validate.ProductProgRel.progPackageId.valiDate", map);
	}
	
	/**
	 * 查询所有节目包订价关系表信息, 用于前台显示
	 * @param progPackageId 节目包 (ProgPackage) ID
	 * @param valiDate 有效日期
	 * @return List<ProductProgRelVo> 返回用于前台显示的节目包订价关系信息集合
	 */
	@SuppressWarnings("unchecked")
	public List<ProductProgRelVo> queryAllProductProgRelVos(String progPackageId,
			Date valiDate) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("progPackageId", progPackageId);
		map.put("valiDate", valiDate);
		return this.baseDAO.queryByNamedQuery(
				"query.all.validate.ProductProgRelVo.progPackageId.valiDate", map);
	}
	
	/**
	 * 保存节目包包月产品关系记录
	 * @param productProgRel 节目包包月产品关系
	 */
	public synchronized void saveProductProgRel(ProductProgRel productProgRel) {
		String currMaxPk = this.baseDAO.getMaxPropertyValue("ProductProgRel", "id");
		productProgRel.setId("".equals(currMaxPk) ? String.format("%012d", 1) 
				: String.format("%012d", Long.valueOf(currMaxPk) + 1));
		this.baseDAO.save(productProgRel);
	}
	
	/**
	 * 添加节目包产品信息
	 * @param productInfo 节目包产品信息
	 * @throws Exception 
	 */
	public synchronized void saveProductInfo(TProductInfo productInfo, 
			ProductProgRel... productProgRels) throws Exception {
		List<ProductProgRel> existProductProgRels = this.queryAllProductProgRels(
				productInfo.getProgPackageId(), DateUtil.parseDate("yyyy-MM-dd", 
						new CmsConfig().getPropertyByName("DefaultPricingInvalidate")));
		
		List<String> existKeyIds = ListUtil.getPropertiesList(
				existProductProgRels, "keyId");
		if (0 < existProductProgRels.size()) {
			ListUtil.sortByProperty(existProductProgRels, "valiDate");
		}
		
//		if (productProgRels.length < existProductProgRels.size()) {
//			throw new Exception("节目包包月产品少于 " + existProductProgRels.size() + " 个!");
//		} else 
		if(0 < existProductProgRels.size() && 0 < productProgRels.length
				&& productProgRels[0].getValiDate().getTime() < 
						existProductProgRels.get(existProductProgRels.size() - 1)
										.getValiDate().getTime()) {
			throw new Exception("节目包包月产品生效日期不能小于已有包月产品的生效日期!");
		}
		
		String currMaxPk = null;
		for (ProductProgRel productProgRel : productProgRels) {
			if (existKeyIds.contains(productProgRel.getKeyId())) {
				continue;
			}
			
			productProgRel.setInputDate(new Date());
			currMaxPk = this.baseDAO.getMaxPropertyValue("ProductProgRel", "id");
			productProgRel.setId("".equals(currMaxPk) ? String.format("%012d", 1) 
					: String.format("%012d", Long.valueOf(currMaxPk) + 1));
			this.baseDAO.save(productProgRel);
		}
		
		List<ProductProgRel> existValidateProductProgRels = 
			this.queryAllProductProgRels(
					productInfo.getProgPackageId(), productInfo.getValidDate());
	
		String keyIds = "";
		for (ProductProgRel productProgRel : existValidateProductProgRels) {
			keyIds += productProgRel.getKeyId() + ",";
		}
		
		productInfo.setKeyId(keyIds.endsWith(",") 
				? keyIds.substring(0, keyIds.length() - 1) : keyIds);
		
		Document document = DocumentHelper.createDocument();
		Element root, casystem, cdcadesc, rightdesc;
		
		root = document.addElement("PRODUCTINFO");
		root.addAttribute("PRODUCTINFONAME", productInfo.getProductName());
		root.addAttribute("VALIDDATE", DateUtil.getDateStr(
				"yyyy-MM-dd HH:mm:ss", productInfo.getValidDate()));
		root.addAttribute("INVALIDDATE", DateUtil.getDateStr(
				"yyyy-MM-dd HH:mm:ss", productInfo.getInvalidDate()));
		
		casystem = root.addElement("casystem");
		rightdesc = root.addElement("rightdesc");
		
		casystem.addAttribute("systemid", "1");
		
		cdcadesc = casystem.addElement("cdcadesc");
		//TODO securityflag属性值待定
		cdcadesc.addAttribute("securityflag", "");
		cdcadesc.addAttribute("KEYID", productInfo.getKeyId());
		cdcadesc.addAttribute("IPPVID", productInfo.getIppvId());
		cdcadesc.addAttribute("SLOTID", productInfo.getSlotId());
		cdcadesc.addAttribute("PRICE", productInfo.getPrice());
//		cdcadesc.addAttribute("isEncrypt", "1000".equals(productInfo.getKeyId()) ? "0" : "1");
		
		rightdesc.addAttribute("EXPIREDDATE", DateUtil.getDateStr(
				"yyyy-MM-dd HH:mm:ss", productInfo.getExpired()));
		rightdesc.addAttribute("WATCHTIMES", productInfo.getWatchTimes());
		rightdesc.addAttribute("WATCHDURATION", productInfo.getWatchDuration());
		rightdesc.addAttribute("WATCHTRAFFICE", productInfo.getWatchTraffice());
		
		productInfo.setProductXml(document.asXML());
		productInfo.setInputTime(new Date());
		if (null == productInfo.getProductName()) {
			productInfo.setProductName("[" + productInfo.getKeyId().trim() + "]");
		}
//		productInfo.setInvalidDate(DateUtil.parseDate("yyyy-MM-dd", defaultInvalidateStr));
		
		currMaxPk = this.baseDAO.getMaxPropertyValue("TProductInfo", "id");
		productInfo.setId("".equals(currMaxPk) ? String.format("%012d", 1) 
				: String.format("%012d", Long.valueOf(currMaxPk) + 1));
		
		this.baseDAO.save(productInfo);
	}
	
	/**
	 * 根据产品ID删除节目包产品信息
	 * @param productInfoId 产品ID
	 */
	public void delProductInfo(String productInfoId) {
		this.baseDAO.deleteById(TProductInfo.class, productInfoId);
	}
	
	/**
	 * 根据产品ID获取产品信息
	 * @param productInfoId 产品ID
	 * @return TProductInfo 返回产品信息
	 */
	public TProductInfo getProductInfoById(String productInfoId) {
		return (TProductInfo) this.baseDAO.getById(
				TProductInfo.class, productInfoId);
	}
	
	public void updateProgductInfo() {}
	
	/**
	 * 根据keyId查询该产品号绑定的节目包ID集合
	 * @param keyIds 产品编号集合
	 * @param siteId 品牌编号
	 * @return 返回绑定此产品号的节目包ID集合List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryProgPackageIdByKeyIdsSiteId(
			List<String> keyIds, String siteId) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("keyIds", keyIds);
		map.put("siteId", siteId);
		return (List<String>) this.baseDAO.queryNamed(
				"query.ProgPackage.progPackageId.by.keyIds.siteId", map);
	}

	/**
	 * 根据keyId 栏目编号, 查询指定栏目包含该产品号的节目包ID集合
	 * @param keyIds 产品编号集合
	 * @param columnId 栏目编号
	 * @return List<String> 返回绑定此产品号的节目包ID集合
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryPackageIdByKeyIdsColumnId(
			List<String> keyIds, String columnId) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("keyIds", keyIds);
		map.put("columnId", columnId);
		return (List<String>) this.baseDAO.queryNamed(
				"query.ProgPackage.progPackageId.by.keyIds.columnId", map);
		
	}
	
	/**
	 * 根据节目包ID, 产品ID, 删除节目包绑定的产品
	 * @param progPackageId 节目包ID
	 * @param progProductIds 产品ID集合
	 */
	public void deleteProductProgRelByProgPackageIdAndProgProductIds(
			String progPackageId, List<String> progProductIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("progPackageId", progPackageId);
		map.put("progProductIds", progProductIds);
		this.baseDAO.executeSQL(
				"DELETE FROM CMSADMIN.TPRODUCTPROGREL WHERE CMSADMIN.TPRODUCTPROGREL.PRODUCTID = :progPackageId AND CMSADMIN.TPRODUCTPROGREL.KEYID IN (SELECT CMSADMIN.TPROGPRODUCT.KEYID FROM CMSADMIN.TPROGPRODUCT WHERE CMSADMIN.TPROGPRODUCT.PRODUCTPROGREL IN (:progProductIds))", 
				map);
	}
	
	
	public void deleteProductProgRelById(String... ids) {
		for (String id : ids) {
			this.baseDAO.deleteById(ProductProgRel.class, id);
		}
	}
	
	public void deleteProductProgRelById(List<String> ids) {
		for (String id : ids) {
			this.baseDAO.deleteById(ProductProgRel.class, id);
		}
	}
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}
