/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2011-1-12 下午02:10:13
 */
public class ProgListDeleteDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String	id;					// 主键ID
	private String	scheduleDate;		// 编单日期ID
	private String	columnClassId;		// 栏目ID
	private String	defcatSeq;			// 栏目路径序列
	private String	progPackageId;		// 节目包ID
	private String	progPackageName;	// 节目包名称
	private Date	inputTime;			// 信息录入时间
	private String	inputManId;			// 操作人员ID
	private String	remark;				// 备注
	private String  siteCode;			// 品牌code
	
	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public ProgListDeleteDetail() { }
	
	/**
	 * full constructor
	 * @param id				主键ID
	 * @param scheduleDate		编单日期ID
	 * @param columnClassId		栏目ID
	 * @param defcatSeq			栏目路径序列
	 * @param progPackageId		节目包ID
	 * @param progPackageName	节目包名称
	 * @param inputTime			信息录入时间
	 * @param inputManId		操作人员ID
	 * @param remark			备注
	 */
	public ProgListDeleteDetail(String id, String scheduleDate,
			String columnClassId, String defcatSeq, String progPackageId,
			String progPackageName, Date inputTime, String inputManId,
			String remark) {
		super();
		this.id = id;
		this.scheduleDate = scheduleDate;
		this.columnClassId = columnClassId;
		this.defcatSeq = defcatSeq;
		this.progPackageId = progPackageId;
		this.progPackageName = progPackageName;
		this.inputTime = inputTime;
		this.inputManId = inputManId;
		this.remark = remark;
	}

	/**
	 * 主键ID
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 主键ID
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 编单日期ID
	 * @return
	 */
	public String getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * 编单日期ID
	 * @param scheduleDate
	 */
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	/**
	 * 栏目ID
	 * @return
	 */
	public String getColumnClassId() {
		return columnClassId;
	}

	/**
	 * 栏目ID
	 * @param columnClassId
	 */
	public void setColumnClassId(String columnClassId) {
		this.columnClassId = columnClassId;
	}

	/**
	 * 栏目路径序列
	 * @return
	 */
	public String getDefcatSeq() {
		return defcatSeq;
	}

	/**
	 * 栏目路径序列
	 * @param defcatSeq
	 */
	public void setDefcatSeq(String defcatSeq) {
		this.defcatSeq = defcatSeq;
	}

	/**
	 * 节目包ID
	 * @return
	 */
	public String getProgPackageId() {
		return progPackageId;
	}

	/**
	 * 节目包ID
	 * @param progPackageId
	 */
	public void setProgPackageId(String progPackageId) {
		this.progPackageId = progPackageId;
	}

	/**
	 * 节目包名称
	 * @return
	 */
	public String getProgPackageName() {
		return progPackageName;
	}

	/**
	 * 节目包名称
	 * @param progPackageName
	 */
	public void setProgPackageName(String progPackageName) {
		this.progPackageName = progPackageName;
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
	 * 操作人员ID
	 * @return
	 */
	public String getInputManId() {
		return inputManId;
	}

	/**
	 * 操作人员ID
	 * @param inputManId
	 */
	public void setInputManId(String inputManId) {
		this.inputManId = inputManId;
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
