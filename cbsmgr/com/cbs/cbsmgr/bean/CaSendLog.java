package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * CaSendLog entity. @author MyEclipse Persistence Tools
 */

public class CaSendLog  implements java.io.Serializable {


    // Fields    

     private Long caSendLogId;
     private Long customerId;
     private String scSerial;
     private Long eventId;
     private String sendTaskBody;
     private String sendBody;
     private Date generateDate;
     private Date sendDate;
     private String successTag;
     private String ackString;


    // Constructors

    /** default constructor */
    public CaSendLog() {
    }

	/** minimal constructor */
    public CaSendLog(Long caSendLogId) {
        this.caSendLogId = caSendLogId;
    }
    
    /** full constructor */
    public CaSendLog(Long caSendLogId, Long customerId, String scSerial, Long eventId, String sendTaskBody, String sendBody, Date generateDate, Date sendDate, String successTag, String ackString) {
        this.caSendLogId = caSendLogId;
        this.customerId = customerId;
        this.scSerial = scSerial;
        this.eventId = eventId;
        this.sendTaskBody = sendTaskBody;
        this.sendBody = sendBody;
        this.generateDate = generateDate;
        this.sendDate = sendDate;
        this.successTag = successTag;
        this.ackString = ackString;
    }

   
    // Property accessors

    public Long getCaSendLogId() {
        return this.caSendLogId;
    }
    
    public void setCaSendLogId(Long caSendLogId) {
        this.caSendLogId = caSendLogId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getScSerial() {
        return this.scSerial;
    }
    
    public void setScSerial(String scSerial) {
        this.scSerial = scSerial;
    }

    public Long getEventId() {
        return this.eventId;
    }
    
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getSendTaskBody() {
        return this.sendTaskBody;
    }
    
    public void setSendTaskBody(String sendTaskBody) {
        this.sendTaskBody = sendTaskBody;
    }

    public String getSendBody() {
        return this.sendBody;
    }
    
    public void setSendBody(String sendBody) {
        this.sendBody = sendBody;
    }

    public Date getGenerateDate() {
        return this.generateDate;
    }
    
    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    public Date getSendDate() {
        return this.sendDate;
    }
    
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSuccessTag() {
        return this.successTag;
    }
    
    public void setSuccessTag(String successTag) {
        this.successTag = successTag;
    }

    public String getAckString() {
        return this.ackString;
    }
    
    public void setAckString(String ackString) {
        this.ackString = ackString;
    }
   








}