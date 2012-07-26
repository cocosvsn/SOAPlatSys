/**
* copyright (c) by A-one 2010
*/
/**
 * 
 */
package com.soaplat.amsmgr.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Title 		: 文件操作历史类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-10 下午01:11:07
 */
public class AmsStorageHistory implements Serializable {
	private String id;				// 序号	RELID	VARCHAR2(30)	TRUE	FALSE	TRUE
	private String sgId;			// 存储体GLOBALID	STGLOBALID	VARCHAR2(256)	FALSE	FALSE	FALSE
	private String scgId;			// 存储体等级GLOBALID	STCLASSGLOBALID	VARCHAR2(256)	FALSE	FALSE	FALSE
	private String sdgId;			// 存储体目录GLOBALID	STDIRGLOBALID	VARCHAR2(256)	FALSE	FALSE	FALSE
	private String progFileId;		// 文件编号GLOBALID	PROGFILEID	VARCHAR2(30)	FALSE	FALSE	FALSE
	private String fileName;		// 文件名称	FILENAME	VARCHAR2(100)	FALSE	FALSE	FALSE
	private String progId;			// 节目GLOBALID	PRGLOBALID	VARCHAR2(256)	FALSE	FALSE	FALSE
	private long optType;			// 操作类型	OPERATION	NUMBER	FALSE	FALSE	FALSE
	private String policyId;		// 策略ID	POLICYID	VARCHAR2(30)	FALSE	FALSE	FALSE
	private String inputManId;		// 信息录入人	INPUTMANID	VARCHAR2(60)	FALSE	FALSE	FALSE
	private Date inputTime;			// 信息录入时间	INPUTTIME	DATE	FALSE	FALSE	FALSE
	private long optResult;			// 操作返回结果，0-成功，1-失败
	private String optObjectId;		// 删除的原位置表记录ID
	private String remark;			// 备注	REMARK	VARCHAR2(400)	FALSE	FALSE	FALSE
	
	/**
	 * default constructor
	 */
	public AmsStorageHistory() {}

	/**
	 * @param id 			编号, 主键
	 * @param sgId			存储体编号
	 * @param scgId			存储体等级编号
	 * @param sdgId			存储体目录编号
	 * @param progFileId	文件编号
	 * @param fileName		文件名
	 * @param progId		节目编号
	 * @param optType		操作类型
	 * @param policyId		策略编号
	 * @param inputManId	信息录入者编号
	 * @param inputTime		信息录入时间
	 * @param remark		备注
	 */
	public AmsStorageHistory(String id, String sgId, String scgId,
			String sdgId, String progFileId, String fileName, String progId,
			long optType, String policyId, String inputManId, Date inputTime,
			String remark) {
		super();
		this.id = id;
		this.sgId = sgId;
		this.scgId = scgId;
		this.sdgId = sdgId;
		this.progFileId = progFileId;
		this.fileName = fileName;
		this.progId = progId;
		this.optType = optType;
		this.policyId = policyId;
		this.inputManId = inputManId;
		this.inputTime = inputTime;
		this.remark = remark;
	}

	/** -------------------- setter and getter ---------------------- **/
	
	/**
	 * 获取  id
	 * @return id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置 id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取存储体编号
	 * @return sgId
	 */
	public String getSgId() {
		return this.sgId;
	}

	/**
	 * 设置存储体编号
	 * @param sgId
	 */
	public void setSgId(String sgId) {
		this.sgId = sgId;
	}

	/**
	 * 获取存储体等级编号
	 * @return scgId
	 */
	public String getScgId() {
		return this.scgId;
	}

	/**
	 * 设置存储体等级编号
	 * @param scgId
	 */
	public void setScgId(String scgId) {
		this.scgId = scgId;
	}

	/**
	 * 获取存储体目录编号
	 * @return sdgId
	 */
	public String getSdgId() {
		return this.sdgId;
	}

	/**
	 * 设置存储体目录编号
	 * @param sdgId
	 */
	public void setSdgId(String sdgId) {
		this.sdgId = sdgId;
	}

	/**
	 * 获取文件编号
	 * @return progFileId
	 */
	public String getProgFileId() {
		return this.progFileId;
	}

	/**
	 * 设置文件编号
	 * @param progFileId
	 */
	public void setProgFileId(String progFileId) {
		this.progFileId = progFileId;
	}

	/**
	 * 获取文件名
	 * @return fileName
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * 设置文件名
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 获取节目编号
	 * @return progId
	 */
	public String getProgId() {
		return this.progId;
	}

	/**
	 * 设置节目编号
	 * @param progId
	 */
	public void setProgId(String progId) {
		this.progId = progId;
	}

	/**
	 * 获取操作类型
	 * @return optType
	 */
	public long getOptType() {
		return this.optType;
	}

	/**
	 * 设置操作类型
	 * @param optType
	 */
	public void setOptType(long optType) {
		this.optType = optType;
	}

	/**
	 * 获取策略编号
	 * @return policyId
	 */
	public String getPolicyId() {
		return this.policyId;
	}

	/**
	 * 设置策略编号
	 * @param policyId
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	/**
	 * 获取信息录入者编号
	 * @return inputManId
	 */
	public String getInputManId() {
		return this.inputManId;
	}

	/**
	 * 设置信息录入者编号
	 * @param inputManId
	 */
	public void setInputManId(String inputManId) {
		this.inputManId = inputManId;
	}

	/**
	 * 获取信息录入时间
	 * @return inputTime
	 */
	public Date getInputTime() {
		return this.inputTime;
	}

	/**
	 * 设置信息录入时间
	 * @param inputTime
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	/**
	 * 获取备注
	 * @return remark
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 设置备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 设置操作结果
	 * @param optResult
	 */
	public void setOptResult(long optResult) {
		this.optResult = optResult;
	}
	
	/**
	 * 获取操作结果
	 * @return
	 */
	public long getOptResult() {
		return optResult;
	}
	
	/**
	 * 设置原位置表主键ID
	 * @param optObjectId
	 */
	public void setOptObjectId(String optObjectId) {
		this.optObjectId = optObjectId;
	}
	
	/**
	 * 获取原位置表主键ID
	 * @return
	 */
	public String getOptObjectId() {
		return optObjectId;
	}
}
