/**
* copyright (c) by A-one 2010
*/
package com.soaplat.amsmgr.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Title 		: 文件存放策略类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-10 上午11:48:52
 */
public class AmsStoragePolicy implements Serializable {
	private String policyId;				// 策略ID	POLICYID	VARCHAR2(30)	TRUE	FALSE	TRUE
	private String policyName;				// 策略名	POLICYNAME	VARCHAR2(256)	FALSE	FALSE	FALSE
	private String scgId;					// 存储等级ID	STCLASSGLOBALID	VARCHAR2(256)	FALSE	FALSE	FALSE
	private String policy;					// 策略公式	POLICYSTRING	VARCHAR2(256)	FALSE	FALSE	FALSE
	private long previous;					// 向前偏移（日）	PREVIOUS	NUMBER	FALSE	FALSE	FALSE
	private long following;					// 向后偏移（日）	FOLLOWING	NUMBER	FALSE	FALSE	FALSE
	private long optType;					// 操作类型	OPERATION	NUMBER	FALSE	FALSE	FALSE
	private String validTag;				// 有效标识	VALIDTAG	VARCHAR2(2)	FALSE	FALSE	FALSE
	private String lastUpdManId;			// 上次修改人员	LASTMANID	VARCHAR2(60)	FALSE	FALSE	FALSE
	private Date lastUpdTime;				// 上次修改日期	LASTTIME	DATE	FALSE	FALSE	FALSE
	private String inputManId;				// 信息录入人	INPUTMANID	VARCHAR2(60)	FALSE	FALSE	FALSE
	private Date inputTime;					// 信息录入时间	INPUTTIME	DATE	FALSE	FALSE	FALSE
	private String remark;					// 备注	REMARK	VARCHAR2(400)	FALSE	FALSE	FALSE
	
	/**
	 * default constructor
	 */
	public AmsStoragePolicy() {}
	
	/**
	 * 全参构造
	 * @param policyId		策略ID, 主键
	 * @param policyName	策略名称
	 * @param scgId			存储等级ID
	 * @param policy		策略公式
	 * @param previous		向前偏移
	 * @param following		向后偏移
	 * @param optType		操作类型
	 * @param validTag		有效标识
	 * @param lastUpdManId	最后一次修改的修改者编号
	 * @param lastUpdTime	最后一次修改时间
	 * @param inputManId	信息录入人编号
	 * @param inputTime		信息录入时间
	 * @param remark		备注
	 */
	public AmsStoragePolicy(String policyId, String policyName, String scgId,
			String policy, long previous, long following, long optType,
			String validTag, String lastUpdManId, Date lastUpdTime,
			String inputManId, Date inputTime, String remark) {
		super();
		this.policyId = policyId;
		this.policyName = policyName;
		this.scgId = scgId;
		this.policy = policy;
		this.previous = previous;
		this.following = following;
		this.optType = optType;
		this.validTag = validTag;
		this.lastUpdManId = lastUpdManId;
		this.lastUpdTime = lastUpdTime;
		this.inputManId = inputManId;
		this.inputTime = inputTime;
		this.remark = remark;
	}

	/**
	 * 获取策略ID
	 * @return policyId
	 */
	public String getPolicyId() {
		return policyId;
	}

	/**
	 * 设置策略ID
	 * @param policyId
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	/**
	 * 获取策略名称
	 * @return policyName
	 */
	public String getPolicyName() {
		return policyName;
	}

	/**
	 * 设置策略名称
	 * @param policyName
	 */
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	/**
	 * 获取存储等级
	 * @return scgId
	 */
	public String getScgId() {
		return scgId;
	}

	/**
	 * 设置 存储等级
	 * @param scgId
	 */
	public void setScgId(String scgId) {
		this.scgId = scgId;
	}

	/**
	 * 获取策略公式
	 * @return policy
	 */
	public String getPolicy() {
		return policy;
	}

	/**
	 * 设置策略公式
	 * @param policy
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}

	/**
	 * 获取向前偏移
	 * @return previous
	 */
	public long getPrevious() {
		return previous;
	}

	/**
	 * 设置向前偏移
	 * @param previous
	 */
	public void setPrevious(long previous) {
		this.previous = previous;
	}

	/**
	 * 获取向后偏移
	 * @return following
	 */
	public long getFollowing() {
		return following;
	}

	/**
	 * 设置向后偏移
	 * @param following
	 */
	public void setFollowing(long following) {
		this.following = following;
	}

	/**
	 * 获取操作类型
	 * @return optType
	 */
	public long getOptType() {
		return optType;
	}

	/**
	 * 设置操作类型
	 * @param optType
	 */
	public void setOptType(long optType) {
		this.optType = optType;
	}

	/**
	 * 获取有效标识
	 * @return validTag
	 */
	public String getValidTag() {
		return validTag;
	}

	/**
	 * 设置有效标识
	 * @param validTag
	 */
	public void setValidTag(String validTag) {
		this.validTag = validTag;
	}

	/**
	 * 获取最后一次修改者编号
	 * @return lastUpdManId
	 */
	public String getLastUpdManId() {
		return lastUpdManId;
	}

	/**
	 * 设置最后一次修改者编号
	 * @param lastUpdManId
	 */
	public void setLastUpdManId(String lastUpdManId) {
		this.lastUpdManId = lastUpdManId;
	}

	/**
	 * 获取最后一次修改时间
	 * @return lastUpdTime
	 */
	public Date getLastUpdTime() {
		return lastUpdTime;
	}

	/**
	 * 设置最后一次修改时间
	 * @param lastUpdTime
	 */
	public void setLastUpdTime(Date lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	/**
	 * 获取信息录入者编号
	 * @return inputManId
	 */
	public String getInputManId() {
		return inputManId;
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
		return inputTime;
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
		return remark;
	}

	/**
	 * 设置备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
