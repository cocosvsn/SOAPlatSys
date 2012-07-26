package com.cbs.cbsmgr.dto;

/**
 * <p>Title: CONVERGENCE BUSINESS SYSTEM</p>
 * <p>Module: CBS CORE MODULE</p>
 * <p>Description: </p>
 * <p>Company: Road To Broadband Co., Ltd.</p>
 * @author Hu Jin
 * <p>2004/1/14 Revised by Hu Jin
 */
import java.io.Serializable;

public class PackageItemDisplayDTO
    implements Serializable {

	// PackageItem
	private Long packageItemId;					// 编号
    private Long packageId;						// 产品包ID
    private Long sequence;						// 计费优先级
    private Long productCategoryId;				// 产品ID
    private Long nextOption;					// 计费到期后操作，20091026
    private Long nextProductCategoryId;			// 计费到期后切换产品ID，20091026
    
    // ProductCategory
    private String productCategoryDes;			// 产品名称
    private String nextProductCategoryDes;		// 计费到期后切换产品名称
    
    // Price
    private Long priceId;						// 价格ID
    private Double price;						// 价格
    private String priceDescription;			// 价格名称
    
    
    
	public String getPriceDescription() {
		return priceDescription;
	}
	public void setPriceDescription(String priceDescription) {
		this.priceDescription = priceDescription;
	}
	public Long getPackageItemId() {
		return packageItemId;
	}
	public void setPackageItemId(Long packageItemId) {
		this.packageItemId = packageItemId;
	}
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public Long getSequence() {
		return sequence;
	}
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}
	public Long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getProductCategoryDes() {
		return productCategoryDes;
	}
	public void setProductCategoryDes(String productCategoryDes) {
		this.productCategoryDes = productCategoryDes;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getPriceId() {
		return priceId;
	}
	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}
	public Long getNextOption() {
		return nextOption;
	}
	public void setNextOption(Long nextOption) {
		this.nextOption = nextOption;
	}
	public Long getNextProductCategoryId() {
		return nextProductCategoryId;
	}
	public void setNextProductCategoryId(Long nextProductCategoryId) {
		this.nextProductCategoryId = nextProductCategoryId;
	}
	public String getNextProductCategoryDes() {
		return nextProductCategoryDes;
	}
	public void setNextProductCategoryDes(String nextProductCategoryDes) {
		this.nextProductCategoryDes = nextProductCategoryDes;
	}

}