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

public class PackageItemDTO
    implements Serializable {

  private Long packageItemId;
  private Long productCategoryId;
  private String description;
  private Double price;

  public PackageItemDTO() {

  }

public Long getPackageItemId() {
	return packageItemId;
}

public void setPackageItemId(Long packageItemId) {
	this.packageItemId = packageItemId;
}

public Long getProductCategoryId() {
	return productCategoryId;
}

public void setProductCategoryId(Long productCategoryId) {
	this.productCategoryId = productCategoryId;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public Double getPrice() {
	return price;
}

public void setPrice(Double price) {
	this.price = price;
}


}