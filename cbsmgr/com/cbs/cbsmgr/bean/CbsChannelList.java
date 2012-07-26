package com.cbs.cbsmgr.bean;
// default package



/**
 * ChannelList entity. @author MyEclipse Persistence Tools
 */

public class CbsChannelList  implements java.io.Serializable {


    // Fields    

     private String channelid;
     private String channelname;
     private Long productCategoryId;
     private String channelurl;
     private Long updateState;


    // Constructors

    /** default constructor */
    public CbsChannelList() {
    }

	/** minimal constructor */
    public CbsChannelList(String channelid) {
        this.channelid = channelid;
    }
    
    /** full constructor */
    public CbsChannelList(String channelid, String channelname, Long productCategoryId, String channelurl, Long updateState) {
        this.channelid = channelid;
        this.channelname = channelname;
        this.productCategoryId = productCategoryId;
        this.channelurl = channelurl;
        this.updateState = updateState;
    }

   
    // Property accessors

    public String getChannelid() {
        return this.channelid;
    }
    
    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getChannelname() {
        return this.channelname;
    }
    
    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public Long getProductCategoryId() {
        return this.productCategoryId;
    }
    
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getChannelurl() {
        return this.channelurl;
    }
    
    public void setChannelurl(String channelurl) {
        this.channelurl = channelurl;
    }

    public Long getUpdateState() {
        return this.updateState;
    }
    
    public void setUpdateState(Long updateState) {
        this.updateState = updateState;
    }
   








}