package com.cbs.cbsmgr.bean;
// default package



/**
 * VodSendDetail entity. @author MyEclipse Persistence Tools
 */

public class VodSendDetail  implements java.io.Serializable {


    // Fields    

     private String vodSendDetailId;
     private String vodSendId;
     private String vodProductId;
     private Long sendstate;


    // Constructors

    /** default constructor */
    public VodSendDetail() {
    }

	/** minimal constructor */
    public VodSendDetail(String vodSendDetailId, String vodSendId, String vodProductId) {
        this.vodSendDetailId = vodSendDetailId;
        this.vodSendId = vodSendId;
        this.vodProductId = vodProductId;
    }
    
    /** full constructor */
    public VodSendDetail(String vodSendDetailId, String vodSendId, String vodProductId, Long sendstate) {
        this.vodSendDetailId = vodSendDetailId;
        this.vodSendId = vodSendId;
        this.vodProductId = vodProductId;
        this.sendstate = sendstate;
    }

   
    // Property accessors

    public String getVodSendDetailId() {
        return this.vodSendDetailId;
    }
    
    public void setVodSendDetailId(String vodSendDetailId) {
        this.vodSendDetailId = vodSendDetailId;
    }

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

    public Long getSendstate() {
        return this.sendstate;
    }
    
    public void setSendstate(Long sendstate) {
        this.sendstate = sendstate;
    }
   








}