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

public class AccountDTO
    implements Serializable {

  private Long invoicePeriodId;
  private Long mopId;
  private String description;

  public AccountDTO() {
    //Initialize the NOT-NULL field when instancing.
    this.setInvoicePeriodId(new Long(0));
    this.setMopId(new Long(0));
    this.setDescription(null);
  }

  public Long getInvoicePeriodId() {
    return invoicePeriodId;
  }

  public void setInvoicePeriodId(Long invoicePeriodId) {
    this.invoicePeriodId = invoicePeriodId;
  }

  public Long getMopId() {
    return mopId;
  }

  public void setMopId(Long mopId) {
    this.mopId = mopId;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}