/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;

/**
 * Title 		: 节目包栏目关系类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 16时26分
 */
public class PPColumnRel  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Fields    
    private String relid;			// 主键ID
    private String columnclassid;	// 栏目(PortalColumn)ID
    private String productid;		// 节目包(ProgPackage)ID
    private String srvid;			
    private Long progstate;
    private String inputmanid;
    private Date inputtime;
    private String remark;


    // Constructors

    /** default constructor */
    public PPColumnRel() {
    }

	/** minimal constructor */
    public PPColumnRel(String relid) {
        this.relid = relid;
    }

	/** full constructor */
	public PPColumnRel(String relid, String columnclassid, String productid,
			String srvid, Long progstate, String inputmanid, Date inputtime,
			String remark) {
		this.relid = relid;
		this.columnclassid = columnclassid;
		this.productid = productid;
		this.srvid = srvid;
		this.progstate = progstate;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
	}

	// Property accessors
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
     * 栏目(PortalColumn)ID
     * @return
     */
    public String getColumnclassid() {
        return this.columnclassid;
    }
    
    /**
     * 栏目(PortalColumn)ID
     * @param columnclassid
     */
    public void setColumnclassid(String columnclassid) {
        this.columnclassid = columnclassid;
    }

    /**
     * 节目包(ProgPackage)ID
     * @return
     */
    public String getProductid() {
        return this.productid;
    }
    
    /**
     * 节目包(ProgPackage)ID
     * @param productid
     */
    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getSrvid() {
        return this.srvid;
    }
    
    public void setSrvid(String srvid) {
        this.srvid = srvid;
    }

    public Long getProgstate() {
        return this.progstate;
    }
    
    public void setProgstate(Long progstate) {
        this.progstate = progstate;
    }

    public String getInputmanid() {
        return this.inputmanid;
    }
    
    public void setInputmanid(String inputmanid) {
        this.inputmanid = inputmanid;
    }

    public Date getInputtime() {
        return this.inputtime;
    }
    
    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}