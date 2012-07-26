package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * VodSend entity. @author MyEclipse Persistence Tools
 */

public class VodSend  implements java.io.Serializable {


    // Fields    

     private String vodSendId;
     private String vodProductId;
     private Date createDate;
     private Long dealState;
     private Date dealDate;
     private Date validFrom;
     private Date validTo;


    // Constructors

    /** default constructor */
    public VodSend() {
    }

	/** minimal constructor */
    public VodSend(String vodSendId) {
        this.vodSendId = vodSendId;
    }
    
    /** full constructor */
    public VodSend(String vodSendId, String vodProductId, Date createDate, Long dealState, Date dealDate, Date validFrom, Date validTo) {
        this.vodSendId = vodSendId;
        this.vodProductId = vodProductId;
        this.createDate = createDate;
        this.dealState = dealState;
        this.dealDate = dealDate;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

   
    // Property accessors

    public String getVodSendId() {
        return this.vodSendId;
    }
    
    public void setVodSendId(String vodSendId) {
        this.vodSendId = vodSendId;
    }

    public String getVodProductId() {
        return this.vodProductId;
    }
    
    public void setVodProductId(String vodProductId) {
        this.vodProductId = vodProductId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getDealState() {
        return this.dealState;
    }
    
    public void setDealState(Long dealState) {
        this.dealState = dealState;
    }

    public Date getDealDate() {
        return this.dealDate;
    }
    
    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public Date getValidFrom() {
        return this.validFrom;
    }
    
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return this.validTo;
    }
    
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
   








}