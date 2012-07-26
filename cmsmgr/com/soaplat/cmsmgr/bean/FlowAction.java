/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

/**
 * Title 		: 活动类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 15时34分
 */
public class FlowAction  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    // Fields    
	private String actionid;		// 主键ID
    private String actionname;		// 活动名称
    private String descript;		// 
    private String funcid;
    private String controlinfo;
    private String remark;			// 备注


    // Constructors

    /** default constructor */
    public FlowAction() {
    }

	/** minimal constructor */
    public FlowAction(String actionid) {
        this.actionid = actionid;
    }
    
	/** full constructor */
	public FlowAction(String actionid, String actionname, String descript,
			String funcid, String controlinfo, String remark) {
		this.actionid = actionid;
		this.actionname = actionname;
		this.descript = descript;
		this.funcid = funcid;
		this.controlinfo = controlinfo;
		this.remark = remark;
	}
   
    // Property accessors

	/**
	 * 主键ID
	 * @return actionid
	 */
    public String getActionid() {
        return this.actionid;
    }
    
    /**
     * 主键ID
     * @param actionid
     */
    public void setActionid(String actionid) {
        this.actionid = actionid;
    }

    /**
     * 活动名称
     * @return
     */
    public String getActionname() {
        return this.actionname;
    }
    
    /**
     * 活动名称
     * @param actionname
     */
    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public String getDescript() {
        return this.descript;
    }
    
    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getFuncid() {
        return this.funcid;
    }
    
    public void setFuncid(String funcid) {
        this.funcid = funcid;
    }

    public String getControlinfo() {
        return this.controlinfo;
    }
    
    public void setControlinfo(String controlinfo) {
        this.controlinfo = controlinfo;
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