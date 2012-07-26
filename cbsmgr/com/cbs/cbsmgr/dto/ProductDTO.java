package com.cbs.cbsmgr.dto;
import java.io.Serializable;
//import com.rtb.cbs.core.utils.TimeConvertor;
import java.math.BigDecimal;
import java.util.Date;

public class ProductDTO implements Serializable {

  private Long productCategoryId;
  private Long productStatusId;
  private Long oldStatusId;
  private Date statusChangeDate;
  private Date captureDate;
  private Long packageItemId;

  
  public ProductDTO() {
    this.setProductCategoryId(new Long(0));
    this.setProductStatusId(new Long(0));

    this.setOldStatusId(new Long(0));
    this.setStatusChangeDate(null);
//    this.setCaptureDate(TimeConvertor.getCurrentDate());
    this.setCaptureDate(new Date());
  }

  public Long getProductCategoryId() {
    return productCategoryId;
  }
  public void setProductCategoryId(Long productCategoryId) {
    this.productCategoryId = productCategoryId;
  }
  public Long getProductStatusId() {
    return productStatusId;
  }
  public void setProductStatusId(Long productStatusId) {
    this.productStatusId = productStatusId;
  }
 
  public Long getOldStatusId() {
    return oldStatusId;
  }
  public void setOldStatusId(Long oldStatusId) {
    this.oldStatusId = oldStatusId;
  }
  public Date getStatusChangeDate() {
    return statusChangeDate;
  }
  public void setStatusChangeDate(Date statusChangeDate) {
    this.statusChangeDate = statusChangeDate;
  }
  public Date getCaptureDate() {
    return captureDate;
  }
  public void setCaptureDate(Date captureDate) {
    this.captureDate = captureDate;
  }
  public Long getPackageItemId() {
    return packageItemId;
  }
  public void setPackageItemId(Long packageItemId) {
    this.packageItemId = packageItemId;
  }

}