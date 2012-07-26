package com.cbs.cbsmgr.bean;
// default package

import java.util.Date;


/**
 * Hardware entity. @author MyEclipse Persistence Tools
 */

public class Hardware  implements java.io.Serializable {


    // Fields    

     private String hardwareId;
     private String hardwareModelId;
     private String serialNo;
     private String cardNo;
     private String otherNo;
     private Long hardwareStatusId;
     private Date guaranteeDate;
     private String mac;
     private String ip;
     private String userName;
     private String password;


    // Constructors

    /** default constructor */
    public Hardware() {
    }

	/** minimal constructor */
    public Hardware(String hardwareId, String hardwareModelId, Long hardwareStatusId) {
        this.hardwareId = hardwareId;
        this.hardwareModelId = hardwareModelId;
        this.hardwareStatusId = hardwareStatusId;
    }
    
    /** full constructor */
    public Hardware(String hardwareId, String hardwareModelId, String serialNo, String cardNo, String otherNo, Long hardwareStatusId, Date guaranteeDate) {
        this.hardwareId = hardwareId;
        this.hardwareModelId = hardwareModelId;
        this.serialNo = serialNo;
        this.cardNo = cardNo;
        this.otherNo = otherNo;
        this.hardwareStatusId = hardwareStatusId;
        this.guaranteeDate = guaranteeDate;
    }

   
    // Property accessors

    public String getHardwareId() {
        return this.hardwareId;
    }
    
    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getHardwareModelId() {
        return this.hardwareModelId;
    }
    
    public void setHardwareModelId(String hardwareModelId) {
        this.hardwareModelId = hardwareModelId;
    }

    public String getSerialNo() {
        return this.serialNo;
    }
    
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getCardNo() {
        return this.cardNo;
    }
    
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOtherNo() {
        return this.otherNo;
    }
    
    public void setOtherNo(String otherNo) {
        this.otherNo = otherNo;
    }

    public Long getHardwareStatusId() {
        return this.hardwareStatusId;
    }
    
    public void setHardwareStatusId(Long hardwareStatusId) {
        this.hardwareStatusId = hardwareStatusId;
    }

    public Date getGuaranteeDate() {
        return this.guaranteeDate;
    }
    
    public void setGuaranteeDate(Date guaranteeDate) {
        this.guaranteeDate = guaranteeDate;
    }

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
   








}