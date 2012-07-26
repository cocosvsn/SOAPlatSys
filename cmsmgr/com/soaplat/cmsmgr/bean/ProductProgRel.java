/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 节目包与包月产品关系
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-9-27 上午10:12:03
 */
public class ProductProgRel implements Serializable {
	private String	id;				// 主键ID, 长度12位, 格式: 000000000001
	private String	progPackageId;	// 节目包 (ProgPackage) ID
	private String	keyName;		// 产品名称
	private String	keyId;			// Key ID
	private Date	valiDate;		// 生效日期
	private String	operator;		// 操作人员ID
	private Date	inputDate;		// 信息录入时间
	private String	remark;			// 备注

	public ProductProgRel() { }

	/**
	 * @param progPackageId	节目包 (ProgPackage) ID
	 * @param keyName		产品名称
	 * @param keyId			Key ID
	 * @param valiDate		生效日期
	 * @param operator		操作人员ID
	 */
	public ProductProgRel(String progPackageId, String keyName, String keyId,
			Date valiDate, String operator) {
		this.progPackageId = progPackageId;
		this.keyName = keyName;
		this.keyId = keyId;
		this.valiDate = valiDate;
		this.operator = operator;
		this.inputDate = new Date();
	}

	/**
	 * @param id			主键ID, 长度12位, 格式: 000000000001
	 * @param progPackageId	节目包 (ProgPackage) ID
	 * @param keyName		产品名称
	 * @param keyId			Key ID
	 * @param valiDate		生效日期
	 * @param operator		操作人员ID
	 * @param inputDate		信息录入时间
	 * @param remark		备注
	 */
	public ProductProgRel(String id, String progPackageId, String keyName,
			String keyId, Date valiDate, String operator, Date inputDate,
			String remark) {
		super();
		this.id = id;
		this.progPackageId = progPackageId;
		this.keyName = keyName;
		this.keyId = keyId;
		this.valiDate = valiDate;
		this.operator = operator;
		this.inputDate = inputDate;
		this.remark = remark;
	}

	/**
	 * 主键ID, 长度12位, 格式: 000000000001
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 主键ID, 长度12位, 格式: 000000000001
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 节目包 (ProgPackage) ID
	 * @return
	 */
	public String getProgPackageId() {
		return progPackageId;
	}
	
	/**
	 * 节目包 (ProgPackage) ID
	 * @param progPackageId
	 */
	public void setProgPackageId(String progPackageId) {
		this.progPackageId = progPackageId;
	}
	
	/**
	 * 产品名称
	 * @return
	 */
	public String getKeyName() {
		return keyName;
	}
	
	/**
	 * 产品名称
	 * @param keyName
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	/**
	 * Key ID
	 * @return
	 */
	public String getKeyId() {
		return keyId;
	}
	
	/**
	 * Key ID
	 * @param keyId
	 */
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	
	/**
	 * 生效日期
	 * @return
	 */
	public Date getValiDate() {
		return valiDate;
	}
	
	/**
	 * 生效日期
	 * @param valiDate
	 */
	public void setValiDate(Date valiDate) {
		this.valiDate = valiDate;
	}
	
	/**
	 * 操作人员ID
	 * @return
	 */
	public String getOperator() {
		return operator;
	}
	
	/**
	 * 操作人员
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	/**
	 * 信息录入时间
	 * @return
	 */
	public Date getInputDate() {
		return inputDate;
	}
	
	/**
	 * 信息录入时间
	 * @param inputDate
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
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
}
