package com.cbs.cbsmgr.bean;
// default package



/**
 * HardwareModel entity. @author MyEclipse Persistence Tools
 */

public class HardwareModel  implements java.io.Serializable {


    // Fields    

     private String hardwareModelId;
     private String hardwareModelName;
     private Long hardwareType;
     private String serialNoTag;
     private String cardNoTag;
     private String otherNoTag;
     private String macTag;
     private String ipTag;


    // Constructors

    /** default constructor */
    public HardwareModel() {
    }

	/** minimal constructor */
    public HardwareModel(String hardwareModelId, Long hardwareType, String serialNoTag, String cardNoTag, String otherNoTag) {
        this.hardwareModelId = hardwareModelId;
        this.hardwareType = hardwareType;
        this.serialNoTag = serialNoTag;
        this.cardNoTag = cardNoTag;
        this.otherNoTag = otherNoTag;
    }
    
    /** full constructor */
    public HardwareModel(String hardwareModelId, String hardwareModelName, Long hardwareType, String serialNoTag, String cardNoTag, String otherNoTag) {
        this.hardwareModelId = hardwareModelId;
        this.hardwareModelName = hardwareModelName;
        this.hardwareType = hardwareType;
        this.serialNoTag = serialNoTag;
        this.cardNoTag = cardNoTag;
        this.otherNoTag = otherNoTag;
    }

   
    // Property accessors

    public String getHardwareModelId() {
        return this.hardwareModelId;
    }
    
    public void setHardwareModelId(String hardwareModelId) {
        this.hardwareModelId = hardwareModelId;
    }

    public String getHardwareModelName() {
        return this.hardwareModelName;
    }
    
    public void setHardwareModelName(String hardwareModelName) {
        this.hardwareModelName = hardwareModelName;
    }

    public Long getHardwareType() {
        return this.hardwareType;
    }
    
    public void setHardwareType(Long hardwareType) {
        this.hardwareType = hardwareType;
    }

    public String getSerialNoTag() {
        return this.serialNoTag;
    }
    
    public void setSerialNoTag(String serialNoTag) {
        this.serialNoTag = serialNoTag;
    }

    public String getCardNoTag() {
        return this.cardNoTag;
    }
    
    public void setCardNoTag(String cardNoTag) {
        this.cardNoTag = cardNoTag;
    }

    public String getOtherNoTag() {
        return this.otherNoTag;
    }
    
    public void setOtherNoTag(String otherNoTag) {
        this.otherNoTag = otherNoTag;
    }

	public String getMacTag() {
		return macTag;
	}

	public void setMacTag(String macTag) {
		this.macTag = macTag;
	}

	public String getIpTag() {
		return ipTag;
	}

	public void setIpTag(String ipTag) {
		this.ipTag = ipTag;
	}
   








}