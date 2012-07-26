/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 产品信息类
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-9-26 下午04:58:17
 */
public class TProductInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String	id;							// 主键ID, 长度12位, 格式:000000000001
	private String	progPackageId;				// 节目包(ProgPackage)ID
	private String	productName;				// 产品名称
	private String	ippvId;						// IPPVID
	private String	slotId;						// 电子钱包ID
	private String	price;						// 价格
	private Date	expired;					// 版权日期
	private String	watchDuration;				// 观看次数限制
	private String	watchTimes;					// 观看时长限制
	private String	watchTraffice;				// 观看流量限制
	private String	keyId;						// 包月产品集合
	private Date	validDate;					// 生效日期
	private Date	invalidDate;				// 失效日期
	private String	productStyleId;				// 产品样式ID
	private String	productXml;					// 产品描述XML
	private String	keyFileId;					// key文件ID
	private Long	encryptState;				// 产品加密状态[0:未处理; 1:处理中; 7:复制Key文件到一级库失败; 8:失败; 9:成功;]
	private Date	keyUpdateTime;				// key文件更新日期(key文件版本)
	private String	operator;					// 操作人员ID
	private Date	inputTime;					// 信息录入时间
	private String	remark;						// 备注

	// 不使用
	private String	priceId;					// 价格ID
	private List<ProgProduct> progProducts;		// 包月产品集合

	public TProductInfo() { }
	
	/**
	 * @param progPackageId		节目包(ProgPackage)ID
	 * @param productName		产品名称
	 * @param ippvId			IPPVID
	 * @param slotId			电子钱包ID
	 * @param price				价格
	 * @param operator			操作人员ID
	 * @param remark			备注
	 */
	public TProductInfo(String progPackageId, String productName, String ippvId,
			String slotId, String price, String operator, String remark) {
		this.progPackageId = progPackageId;
		this.productName = productName;
		this.ippvId = ippvId;
		this.slotId = slotId;
		this.price = price;
		this.operator = operator;
		this.inputTime = new Date();
		this.remark = remark;
	}

	/**
	 * @param id				主键ID, 长度12位, 格式:000000000001
	 * @param progPackageId		节目包(ProgPackage)ID
	 * @param productName		产品名称
	 * @param ippvId			IPPVID
	 * @param slotId			电子钱包ID
	 * @param price				价格
	 * @param expired			版权日期
	 * @param watchDuration		观看次数限制
	 * @param watchTimes		观看时长限制
	 * @param watchTraffice		观看流量限制
	 * @param keyId				包月产品集合
	 * @param validDate			生效日期
	 * @param invalidDate		失效日期
	 * @param productStyleId	产品样式ID
	 * @param productXml		产品描述XML
	 * @param keyFileId			key文件ID
	 * @param keyUpdateTime		key文件更新日期(key文件版本)
	 * @param operator			操作人员ID
	 * @param inputTime			信息录入时间
	 * @param remark			备注
	 */
	public TProductInfo(String id, String progPackageId, String productName,
			String ippvId, String slotId, String price, Date expired,
			String watchDuration, String watchTimes, String watchTraffice,
			String keyId, Date validDate, Date invalidDate,
			String productStyleId, String productXml, String keyFileId,
			Date keyUpdateTime, String operator, Date inputTime, String remark) {
		super();
		this.id = id;
		this.progPackageId = progPackageId;
		this.productName = productName;
		this.ippvId = ippvId;
		this.slotId = slotId;
		this.price = price;
		this.expired = expired;
		this.watchDuration = watchDuration;
		this.watchTimes = watchTimes;
		this.watchTraffice = watchTraffice;
		this.keyId = keyId;
		this.validDate = validDate;
		this.invalidDate = invalidDate;
		this.productStyleId = productStyleId;
		this.productXml = productXml;
		this.keyFileId = keyFileId;
		this.keyUpdateTime = keyUpdateTime;
		this.operator = operator;
		this.inputTime = inputTime;
		this.remark = remark;
	}

	/**
	 * 主键ID, 长度12位, 格式:000000000001
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 主键ID, 长度12位, 格式:000000000001
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 节目包(ProgPackage)ID
	 * @return
	 */
	public String getProgPackageId() {
		return progPackageId;
	}

	/**
	 * 节目包(ProgPackage)ID
	 * @param progPackageId
	 */
	public void setProgPackageId(String progPackageId) {
		this.progPackageId = progPackageId;
	}

	/**
	 * 产品名称
	 * @return
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 产品名称
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * IPPVID
	 * @return
	 */
	public String getIppvId() {
		return ippvId;
	}

	/**
	 * IPPVID
	 * @param ippvId
	 */
	public void setIppvId(String ippvId) {
		this.ippvId = ippvId;
	}

	/**
	 * 电子钱包ID
	 * @return
	 */
	public String getSlotId() {
		return slotId;
	}

	/**
	 * 电子钱包ID
	 * @param slotId
	 */
	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}

	/**
	 * 价格
	 * @return
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * 价格
	 * @param price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * 版权日期
	 * @return
	 */
	public Date getExpired() {
		return expired;
	}

	/**
	 * 版权日期
	 * @param expired
	 */
	public void setExpired(Date expired) {
		this.expired = expired;
	}

	/**
	 * 观看次数限制
	 * @return
	 */
	public String getWatchDuration() {
		return watchDuration;
	}

	/**
	 * 观看次数限制
	 * @param watchDuration
	 */
	public void setWatchDuration(String watchDuration) {
		this.watchDuration = watchDuration;
	}

	/**
	 * 观看时长限制
	 * @return
	 */
	public String getWatchTimes() {
		return watchTimes;
	}

	/**
	 * 观看时长限制
	 * @param watchTimes
	 */
	public void setWatchTimes(String watchTimes) {
		this.watchTimes = watchTimes;
	}

	/**
	 * 观看流量限制
	 * @return
	 */
	public String getWatchTraffice() {
		return watchTraffice;
	}

	/**
	 * 观看流量限制
	 * @param watchTraffice
	 */
	public void setWatchTraffice(String watchTraffice) {
		this.watchTraffice = watchTraffice;
	}

	/**
	 * 包月产品集合
	 * @return
	 */
	public String getKeyId() {
		return keyId;
	}

	/**
	 * 包月产品集合
	 * @param keyId
	 */
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	/**
	 * 生效日期
	 * @return
	 */
	public Date getValidDate() {
		return validDate;
	}

	/**
	 * 生效日期
	 * @param validDate
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * 失效日期
	 * @return
	 */
	public Date getInvalidDate() {
		return invalidDate;
	}

	/**
	 * 失效日期
	 * @param invalidDate
	 */
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**
	 * 产品样式ID
	 * @return
	 */
	public String getProductStyleId() {
		return productStyleId;
	}

	/**
	 * 产品样式ID
	 * @param productStyleId
	 */
	public void setProductStyleId(String productStyleId) {
		this.productStyleId = productStyleId;
	}

	/**
	 * 产品描述XML
	 * @return
	 */
	public String getProductXml() {
		return productXml;
	}

	/**
	 * 产品描述XML
	 * @param productXml
	 */
	public void setProductXml(String productXml) {
		this.productXml = productXml;
	}

	/**
	 * key文件ID
	 * @return
	 */
	public String getKeyFileId() {
		return keyFileId;
	}

	/**
	 * key文件ID
	 * @param keyFileId
	 */
	public void setKeyFileId(String keyFileId) {
		this.keyFileId = keyFileId;
	}

	/**
	 * 产品加密状态[0:未处理; 1:处理中; 8:失败; 9:成功;]
	 * @return
	 */
	public Long getEncryptState() {
		return encryptState;
	}
	
	/**
	 * 产品加密状态[0:未处理; 1:处理中; 8:失败; 9:成功;]
	 * @param encryptState
	 */
	public void setEncryptState(Long encryptState) {
		this.encryptState = encryptState;
	}
	
	/**
	 * key文件更新日期(key文件版本)
	 * @return
	 */
	public Date getKeyUpdateTime() {
		return keyUpdateTime;
	}

	/**
	 * key文件更新日期(key文件版本)
	 * @param keyUpdateTime
	 */
	public void setKeyUpdateTime(Date keyUpdateTime) {
		this.keyUpdateTime = keyUpdateTime;
	}

	/**
	 * 操作人员ID
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 操作人员ID
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 信息录入时间
	 * @return
	 */
	public Date getInputTime() {
		return inputTime;
	}

	/**
	 * 信息录入时间
	 * @param inputTime
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	/**
	 * 备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 价格ID
	 * @return
	 */
	public String getPriceId() {
		return priceId;
	}

	/**
	 * 价格ID
	 * @param priceId
	 */
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}
	
	/**
	 * 包月产品集合
	 * @param progProducts
	 */
	public void setProgProducts(List<ProgProduct> progProducts) {
		this.progProducts = progProducts;
	}
	
	/**
	 * 包月产品集合
	 * @return
	 */
	public List<ProgProduct> getProgProducts() {
		return progProducts;
	}
}
