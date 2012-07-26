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
import java.util.Date;

public class HardwareProductDisplayDTO
    implements Serializable {
	
	private Long productId;
    private Long customerId;
    private Long accountId;
//    private Long productCategoryId;
    private String hardwareId;
    private Long productStatusId;					// 数据字典
    private Long oldStatusId;						// 数据字典
    private Date statusChangeDate;
    private Date captureDate;
    
    private String hardwareModelId;
    private String serialNo;
    private String cardNo;
    private String otherNo;
    private Long hardwareStatusId;					// 暂时不使用
    private Date guaranteeDate;
    private String mac;
    private String ip;
    private String userName;
    private String password;
    
    private String hardwareModelName;
    private Long hardwareType;
    
    

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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

	public String getHardwareModelId() {
		return hardwareModelId;
	}

	public void setHardwareModelId(String hardwareModelId) {
		this.hardwareModelId = hardwareModelId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOtherNo() {
		return otherNo;
	}

	public void setOtherNo(String otherNo) {
		this.otherNo = otherNo;
	}

	public Long getHardwareStatusId() {
		return hardwareStatusId;
	}

	public void setHardwareStatusId(Long hardwareStatusId) {
		this.hardwareStatusId = hardwareStatusId;
	}

	public Date getGuaranteeDate() {
		return guaranteeDate;
	}

	public void setGuaranteeDate(Date guaranteeDate) {
		this.guaranteeDate = guaranteeDate;
	}

	public String getHardwareModelName() {
		return hardwareModelName;
	}

	public void setHardwareModelName(String hardwareModelName) {
		this.hardwareModelName = hardwareModelName;
	}

	public String getHardwareId() {
		return hardwareId;
	}

	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	public Long getHardwareType() {
		return hardwareType;
	}

	public void setHardwareType(Long hardwareType) {
		this.hardwareType = hardwareType;
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