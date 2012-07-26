/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.dto;

import java.io.Serializable;
import java.util.Date;

import com.soaplat.cmsmgr.common.FileOperationImpl;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2011-1-26 上午07:42:44
 */
public class PackageProductVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String	packageId;					// 节目包ID
	private String	packageName;				// 节目包名称
	private long	packageStateCode;			// 节目包状态Code
	private String	packageStateName;			// 节目包状态名称
	private String	packageActors;				// 节目包主演
	private String	packageCategory;			// 节目包影片类型
	private String	pacakgeDirector;			// 节目包导演
	private Date	packageInputTime;			// 节目包创建时间
	private String	productId;					// 产品信息ID
	private String	productName;				// 产品信息名称
	private String	productKeyIds;				// 包月产品ID集合
	private long	productEncryptStateCode;	// 产品加密状态Code
	private String	productEncryptStateName;	// 产品加密状态名称
	private String	brandCode;					// 品牌Code
	private String	brandName;					// 品牌名称
	
	private boolean isCanEncryptProduct;		// 是否能下产品加扰状态
	private boolean isCanMigration;				// 是否能下迁移任务状态
	
	public PackageProductVo() { }
	
	/**
	 * @param packageId					节目包ID
	 * @param packageName				节目包名称
	 * @param packageStateCode			节目包状态Code
	 * @param packageInputTime			节目包创建时间
	 * @param productId					产品信息ID
	 * @param productName				产品信息名称
	 * @param productKeyIds				包月产品ID集合
	 * @param productEncryptStateCode	产品加密状态Code
	 * @param brandCode					品牌Code
	 * @param brandName					品牌名称
	 */
	public PackageProductVo(String packageId, String packageName,
			long packageStateCode, Date packageInputTime, String productId, 
			String productName, String productKeyIds, 
			long productEncryptStateCode, String brandCode, String brandName) {
		super();
		this.packageId = packageId;
		this.packageName = packageName;
		this.packageStateCode = packageStateCode;
		this.packageStateName = FileOperationImpl.getState(
				packageStateCode);
		this.packageInputTime = packageInputTime;
		this.productId = productId;
		this.productName = productName;
		this.productKeyIds = productKeyIds;
		this.productEncryptStateCode = productEncryptStateCode;
		this.productEncryptStateName = (0 == productEncryptStateCode && -1 ==  packageStateCode 
				? "未导入" 
				: FileOperationImpl.getMainFileState(
						productEncryptStateCode));
		this.brandCode = brandCode;
		this.brandName = brandName;
	}
	
	/**
	 * 
	 * @param packageId
	 * @param packageName
	 * @param packageStateCode
	 * @param packageActors
	 * @param packageCategory
	 * @param pacakgeDirector
	 * @param packageInputTime
	 * @param brandCode
	 * @param brandName
	 */
	public PackageProductVo(String packageId, String packageName,
			long packageStateCode, String packageActors, String packageCategory,
			String pacakgeDirector, Date packageInputTime,
			String brandCode, String brandName) {
		super();
		this.packageId = packageId;
		this.packageName = packageName;
		this.packageStateCode = packageStateCode;
		this.packageStateName = FileOperationImpl.getState(
				packageStateCode);
		this.packageActors = packageActors;
		this.packageCategory = packageCategory;
		this.pacakgeDirector = pacakgeDirector;
		this.packageInputTime = packageInputTime;
		this.brandCode = brandCode;
		this.brandName = brandName;
	}
	
	/**
	 * @param packageId					节目包ID
	 * @param packageName				节目包名称
	 * @param packageStateCode			节目包状态Code
	 * @param packageActors				节目包主演
	 * @param packageCategory			节目包影片类型
	 * @param pacakgeDirector			节目包导演
	 * @param packageInputTime			节目包创建时间
	 * @param productId					产品信息ID
	 * @param productName				产品信息名称
	 * @param productKeyIds				包月产品ID集合
	 * @param productEncryptStateCode	产品加密状态Code
	 * @param brandCode					品牌Code
	 * @param brandName					品牌名称
	 */
	public PackageProductVo(String packageId, String packageName,
			long packageStateCode, String packageActors, String packageCategory,
			String pacakgeDirector, Date packageInputTime, String productId,
			String productName, String productKeyIds,
			long productEncryptStateCode, String brandCode, String brandName) {
		super();
		this.packageId = packageId;
		this.packageName = packageName;
		this.packageStateCode = packageStateCode;
		this.packageStateName = FileOperationImpl.getState(
				packageStateCode);
		this.packageActors = packageActors;
		this.packageCategory = packageCategory;
		this.pacakgeDirector = pacakgeDirector;
		this.packageInputTime = packageInputTime;
		this.productId = productId;
		this.productName = productName;
		this.productKeyIds = productKeyIds;
		this.productEncryptStateCode = productEncryptStateCode;
		this.productEncryptStateName = (0 == productEncryptStateCode && -1 ==  packageStateCode 
				? "未导入" 
				: FileOperationImpl.getMainFileState(
				productEncryptStateCode));
		this.brandCode = brandCode;
		this.brandName = brandName;
	}

	/**
	 * 节目包ID
	 * @return
	 */
	public String getPackageId() {
		return packageId;
	}

	/**
	 * 节目包ID
	 * @param packageId
	 */
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	/**
	 * 节目包名称
	 * @return
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * 节目包名称
	 * @param packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * 节目包状态Code
	 * @return
	 */
	public long getPackageStateCode() {
		return packageStateCode;
	}

	/**
	 * 节目包状态Code
	 * @param packageStateCode
	 */
	public void setPackageStateCode(long packageStateCode) {
		this.packageStateCode = packageStateCode;
		this.packageStateName = FileOperationImpl.getState(
				packageStateCode);
	}

	/**
	 * 节目包状态名称
	 * @return
	 */
	public String getPackageStateName() {
		return packageStateName;
	}

	/**
	 * 节目包状态名称
	 * @param packageStateName
	 */
	public void setPackageStateName(String packageStateName) {
		this.packageStateName = packageStateName;
	}

	/**
	 * 节目包主演
	 * @return
	 */
	public String getPackageActors() {
		return packageActors;
	}

	/**
	 * 节目包主演
	 * @param packageActors
	 */
	public void setPackageActors(String packageActors) {
		this.packageActors = packageActors;
	}

	/**
	 * 节目包影片类型
	 * @return
	 */
	public String getPackageCategory() {
		return packageCategory;
	}

	/**
	 * 节目包影片类型
	 * @param packageCategory
	 */
	public void setPackageCategory(String packageCategory) {
		this.packageCategory = packageCategory;
	}

	/**
	 * 节目包导演
	 * @return
	 */
	public String getPacakgeDirector() {
		return pacakgeDirector;
	}

	/**
	 * 节目包导演
	 * @param pacakgeDirector
	 */
	public void setPacakgeDirector(String pacakgeDirector) {
		this.pacakgeDirector = pacakgeDirector;
	}

	/**
	 * 节目包创建时间
	 * @return
	 */
	public Date getPackageInputTime() {
		return packageInputTime;
	}

	/**
	 * 节目包创建时间
	 * @param packageInputTime
	 */
	public void setPackageInputTime(Date packageInputTime) {
		this.packageInputTime = packageInputTime;
	}

	/**
	 * 产品信息ID
	 * @return
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * 产品信息ID
	 * @param productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * 产品信息名称
	 * @return
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 产品信息名称
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 包月产品ID集合
	 * @return
	 */
	public String getProductKeyIds() {
		return productKeyIds;
	}

	/**
	 * 包月产品ID集合
	 * @param productKeyIds
	 */
	public void setProductKeyIds(String productKeyIds) {
		this.productKeyIds = productKeyIds;
	}

	/**
	 * 产品加密状态Code
	 * @return
	 */
	public long getProductEncryptStateCode() {
		return productEncryptStateCode;
	}

	/**
	 * 产品加密状态Code
	 * @param productEncryptStateCode
	 */
	public void setProductEncryptStateCode(long productEncryptStateCode) {
		this.productEncryptStateCode = productEncryptStateCode;
		this.productEncryptStateName = (0 == productEncryptStateCode && -1 ==  packageStateCode 
				? "未导入" 
				: FileOperationImpl.getMainFileState(
						productEncryptStateCode));
	}

	/**
	 * 产品加密状态名称
	 * @return
	 */
	public String getProductEncryptStateName() {
		return productEncryptStateName;
	}

	/**
	 * 产品加密状态名称
	 * @param productEncryptStateName
	 */
	public void setProductEncryptStateName(String productEncryptStateName) {
		this.productEncryptStateName = productEncryptStateName;
	}

	/**
	 * 品牌Code
	 * @return
	 */
	public String getBrandCode() {
		return brandCode;
	}

	/**
	 * 品牌Code
	 * @param brandCode
	 */
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	/**
	 * 品牌名称
	 * @return
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * 品牌名称
	 * @param brandName
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * 是否能下产品加扰状态
	 * @return
	 */
	public boolean isCanEncryptProduct() {
		return isCanEncryptProduct;
	}

	/**
	 * 是否能下产品加扰状态
	 * @param isCanEncryptProduct
	 */
	public void setCanEncryptProduct(boolean isCanEncryptProduct) {
		this.isCanEncryptProduct = isCanEncryptProduct;
	}

	/**
	 * 是否能下迁移任务状态
	 * @return
	 */
	public boolean isCanMigration() {
		return isCanMigration;
	}

	/**
	 * 是否能下迁移任务状态
	 * @param isCanMigration
	 */
	public void setCanMigration(boolean isCanMigration) {
		this.isCanMigration = isCanMigration;
	}
}
