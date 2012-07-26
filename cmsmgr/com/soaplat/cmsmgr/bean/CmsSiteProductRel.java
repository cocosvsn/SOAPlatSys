package com.soaplat.cmsmgr.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Title 		: 栏目产品关系
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2011年4月23日 17时5分
 */
public class CmsSiteProductRel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String	id;				// 主键ID
	private String	cmsSiteId;		// 栏目编号
	private String	productId;		// 产品编号
	private String	inputManId;		// 操作人员编号
	private Date	inputDate;		// 录入时间
	private String	remark;			// 备注
	
	public CmsSiteProductRel() {}
	
	/**
	 * 全参构造
	 * @param id			主键ID
	 * @param cmsSiteId		栏目编号
	 * @param productId		产品编号
	 * @param inputManId	操作人员编号
	 * @param inputDate		录入时间
	 * @param remark		备注
	 */
	public CmsSiteProductRel(String id, String cmsSiteId, String productId,
			String inputManId, Date inputDate, String remark) {
		super();
		this.id = id;
		this.cmsSiteId = cmsSiteId;
		this.productId = productId;
		this.inputManId = inputManId;
		this.inputDate = inputDate;
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
	 * 栏目编号
	 * @return
	 */
	public String getCmsSiteId() {
		return cmsSiteId;
	}

	/**
	 * 栏目编号
	 * @param cmsSiteId
	 */
	public void setCmsSiteId(String cmsSiteId) {
		this.cmsSiteId = cmsSiteId;
	}

	/**
	 * 产品编号
	 * @return
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * 产品编号
	 * @param productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * 操作人员编号
	 * @return
	 */
	public String getInputManId() {
		return inputManId;
	}

	/**
	 * 操作人员编号
	 * @param inputManId
	 */
	public void setInputManId(String inputManId) {
		this.inputManId = inputManId;
	}

	/**
	 * 录入时间
	 * @return
	 */
	public Date getInputDate() {
		return inputDate;
	}

	/**
	 * 录入时间
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
