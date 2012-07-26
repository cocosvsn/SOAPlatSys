package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * VodFlow entity. @author MyEclipse Persistence Tools
 */

public class VodFlow  implements java.io.Serializable {


    // Fields    

     private String vodFlowId;
     private Long customerId;
     private String progfileId;
     private Long serviceType;
     private Date startTime;
     private Date stopTime;
     private Date importDate;
     private Long dealState;
     private Date dealDate;


    // Constructors

    /** default constructor */
    public VodFlow() {
    }

	/** minimal constructor */
    public VodFlow(String vodFlowId) {
        this.vodFlowId = vodFlowId;
    }
    
    /** full constructor */
    public VodFlow(String vodFlowId, Long customerId, String progfileId, Long serviceType, Date startTime, Date stopTime, Date importDate, Long dealState, Date dealDate) {
        this.vodFlowId = vodFlowId;
        this.customerId = customerId;
        this.progfileId = progfileId;
        this.serviceType = serviceType;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.importDate = importDate;
        this.dealState = dealState;
        this.dealDate = dealDate;
    }

   
    // Property accessors

    public String getVodFlowId() {
        return this.vodFlowId;
    }
    
    public void setVodFlowId(String vodFlowId) {
        this.vodFlowId = vodFlowId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getProgfileId() {
        return this.progfileId;
    }
    
    public void setProgfileId(String progfileId) {
        this.progfileId = progfileId;
    }

    public Long getServiceType() {
        return this.serviceType;
    }
    
    public void setServiceType(Long serviceType) {
        this.serviceType = serviceType;
    }

    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return this.stopTime;
    }
    
    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Date getImportDate() {
        return this.importDate;
    }
    
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
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
   








}