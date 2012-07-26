/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;

/**
 * Title 		: 节目节目包关系类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 16时26分
 */
public class ProgPPRel  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // Fields    
	private String relid;		// 主键ID
    private String productid;	// 节目包ID
    private String programid;	// 节目ID
    private String inputmanid;	// 信息录入人编号
    private Date inputtime;		// 信息录入时间
    private String remark;		// 备注


    // Constructors

    /** default constructor */
    public ProgPPRel() {
    }

	/**
	 * minimal constructor 
	 * @param relid			主键ID
	 * @param productid		节目包ID
	 * @param programid		节目ID
	 */
    public ProgPPRel(String relid, String productid, String programid) {
        this.relid = relid;
        this.productid = productid;
        this.programid = programid;
    }
    
	/**
	 * full constructor
	 * @param relid			主键ID
	 * @param productid		节目包ID
	 * @param programid		节目ID
	 * @param inputmanid	信息录入人编号
	 * @param inputtime		信息录入时间
	 * @param remark		备注
	 */
	public ProgPPRel(String relid, String productid, String programid,
			String inputmanid, Date inputtime, String remark) {
		this.relid = relid;
		this.productid = productid;
		this.programid = programid;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
	}

   
	/**
	 * 主键ID
	 * @return
	 */
    public String getRelid() {
        return this.relid;
    }
    
    /**
     * 主键ID
     * @param relid
     */
    public void setRelid(String relid) {
        this.relid = relid;
    }

    /**
     * 节目包ID
     * @return
     */
    public String getProductid() {
        return this.productid;
    }
    
    /**
     * 节目包ID
     * @param productid
     */
    public void setProductid(String productid) {
        this.productid = productid;
    }

    /**
     * 节目ID
     * @return
     */
    public String getProgramid() {
        return this.programid;
    }
    
    /**
     * 节目ID
     * @param programid
     */
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    /**
     * 信息录入人编号
     * @return
     */
    public String getInputmanid() {
        return this.inputmanid;
    }
    
    /**
     * 信息录入人编号
     * @param inputmanid
     */
    public void setInputmanid(String inputmanid) {
        this.inputmanid = inputmanid;
    }

    /**
     * 信息录入时间
     * @return
     */
    public Date getInputtime() {
        return this.inputtime;
    }
    
    /**
     * 信息录入时间
     * @param inputtime
     */
    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    /**
     * 备注
     * @return
     */
    public String getRemark() {
        return this.remark;
    }
    
    /**
     * 备注
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}