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
	private Long packageItemId;					// ���
    private Long packageId;						// ��Ʒ��ID
    private Long sequence;						// �Ʒ����ȼ�
    private Long productCategoryId;				// ��ƷID
    private Long nextOption;					// �Ʒѵ��ں������20091026
    private Long nextProductCategoryId;			// �Ʒѵ��ں��л���ƷID��20091026
    
    // ProductCategory
    private String productCategoryDes;			// ��Ʒ����
    private String nextProductCategoryDes;		// �Ʒѵ��ں��л���Ʒ����
    
    // Price
    private Long priceId;						// �۸�ID
    private Double price;						// �۸�
    private String priceDescription;			// �۸�����
    
    
    
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