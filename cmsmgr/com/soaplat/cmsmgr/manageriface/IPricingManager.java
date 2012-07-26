/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.manageriface;

import java.util.Date;
import java.util.List;

import com.soaplat.cmsmgr.bean.ProductProgRel;
import com.soaplat.cmsmgr.bean.ProgProduct;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.dto.ProductProgRelVo;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-10-8 下午02:35:37
 */
public interface IPricingManager {
	public List<TProductInfo> queryProductInfos(String progPackageId
			, Date valiDate);
	
	/**
	 * 根据包月产品ID集合查询包月产品列表
	 * @param ids 包月产品ID集合
	 * @return 返回包月产品集合
	 */
	public List<ProgProduct> queryProductsByIds(List<String> ids);
	
	/**
	 * 查询所有包月产品信息
	 * @return List<ProgProduct> 返回包月产品集合
	 */
	public List<ProgProduct> queryAllProgProducts(List<String> keyIds, String siteCode);
	
	/**
	 * 查询所有节目包订价关系表信息
	 * @param progPackageId 节目包ID
	 * @return List<ProductProgRel> 返回指定节目包订价关系表信息
	 */
	public List<ProductProgRel> queryAllProductProgRels(String progPackageId,
			Date valiDate);
	
	/**
	 * 查询所有节目包订价关系表信息, 用于前台显示
	 * @param progPackageId 节目包 (ProgPackage) ID
	 * @param valiDate 有效日期
	 * @return List<ProductProgRelVo> 返回用于前台显示的节目包订价关系信息集合
	 */
	public List<ProductProgRelVo> queryAllProductProgRelVos(String progPackageId,
			Date valiDate);
	
	/**
	 * 保存节目包包月产品关系记录
	 * @param productProgRel 节目包包月产品关系
	 */
	public void saveProductProgRel(ProductProgRel productProgRel);
	
	/**
	 * 添加节目包产品信息
	 * @param productInfo 节目包产品信息
	 */
	public void saveProductInfo(TProductInfo productInfo, 
			ProductProgRel... productProgRels) throws Exception;

	/**
	 * 根据产品ID删除节目包产品信息
	 * @param productInfoId 产品ID
	 */
	public void delProductInfo(String productInfoId);
	
	/**
	 * 根据产品ID获取产品信息
	 * @param productInfoId 产品ID
	 * @return TProductInfo 返回产品信息
	 */
	public TProductInfo getProductInfoById(String productInfoId);
	
	/**
	 * 根据keyId查询该产品号绑定的节目包ID集合
	 * @param keyIds 产品编号集合
	 * @param siteId 品牌编号
	 * @return 返回绑定此产品号的节目包ID集合List<String>
	 */
	public List<String> queryProgPackageIdByKeyIdsSiteId(
			List<String> keyIds, String siteId);
	
	/**
	 * 根据keyId 栏目编号, 查询指定栏目包含该产品号的节目包ID集合
	 * @param keyIds 产品编号集合
	 * @param columnId 栏目编号
	 * @return List<String> 返回绑定此产品号的节目包ID集合
	 */
	public List<String> queryPackageIdByKeyIdsColumnId(
			List<String> keyIds, String columnId);

	/**
	 * 根据节目包ID, 产品ID, 删除节目包绑定的产品
	 * @param progPackageId 节目包ID
	 * @param progProductIds 产品ID集合
	 */
	public void deleteProductProgRelByProgPackageIdAndProgProductIds(
			String progPackageId, List<String> progProductIds);

	public void deleteProductProgRelById(String... ids);
	
	public void deleteProductProgRelById(List<String> ids);
}
