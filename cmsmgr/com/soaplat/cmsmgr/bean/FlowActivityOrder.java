/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

/**
 * Title 		: 活动顺序类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 15时34分
 */
public class FlowActivityOrder  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // Fields    
	private Long flowactivityorderid;		// 主键ID
    private String flowactivityidparent;	// 当前活动(FlowAction)ID
    private String flowactivityidchild;		// 相邻活动(FlowAction)ID
    private String controlinfo2;
    private String controlinfo;
    private Long state;
    private String remark;					// 备注
    private String state2;					// 相邻活动性质[R:回退; P:顺序; C:完成; N:新建;]


    // Constructors

    /** default constructor */
    public FlowActivityOrder() {
    }

	/** full constructor */
	public FlowActivityOrder(String flowactivityidparent,
			String flowactivityidchild, String controlinfo2,
			String controlinfo, Long state, String remark, String state2) {
		this.flowactivityidparent = flowactivityidparent;
		this.flowactivityidchild = flowactivityidchild;
		this.controlinfo2 = controlinfo2;
		this.controlinfo = controlinfo;
		this.state = state;
		this.remark = remark;
		this.state2 = state2;
	}

    // Property accessors
	/**
	 * 主键ID
	 * @return flowactivityorderid
	 */
    public Long getFlowactivityorderid() {
        return this.flowactivityorderid;
    }
    
    /**
     * flowactivityorderid
     * @param flowactivityorderid
     */
    public void setFlowactivityorderid(Long flowactivityorderid) {
        this.flowactivityorderid = flowactivityorderid;
    }

    /**
     * 当前活动(FlowAction)ID
     * @return
     */
    public String getFlowactivityidparent() {
        return this.flowactivityidparent;
    }
    
    /**
     * 当前活动(FlowAction)ID
     * @param flowactivityidparent
     */
    public void setFlowactivityidparent(String flowactivityidparent) {
        this.flowactivityidparent = flowactivityidparent;
    }

    /**
     * 相邻活动(FlowAction)ID
     * @return
     */
    public String getFlowactivityidchild() {
        return this.flowactivityidchild;
    }
    
    /**
     * 相邻活动(FlowAction)ID
     * @param flowactivityidchild
     */
    public void setFlowactivityidchild(String flowactivityidchild) {
        this.flowactivityidchild = flowactivityidchild;
    }

    public String getControlinfo2() {
        return this.controlinfo2;
    }
    
    public void setControlinfo2(String controlinfo2) {
        this.controlinfo2 = controlinfo2;
    }

    public String getControlinfo() {
        return this.controlinfo;
    }
    
    public void setControlinfo(String controlinfo) {
        this.controlinfo = controlinfo;
    }

    public Long getState() {
        return this.state;
    }
    
    public void setState(Long state) {
        this.state = state;
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

    /**
     * 相邻活动性质[R:回退; P:顺序; C:完成; N:新建;]
     * @return
     */
    public String getState2() {
        return this.state2;
    }
    
    /**
     * 相邻活动性质[R:回退; P:顺序; C:完成; N:新建;]
     * @param state2
     */
    public void setState2(String state2) {
        this.state2 = state2;
    }
}