/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;

/**
 * Title 		: 编单明细类
 * Description	: 用于编单流程控制
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 15时50分
 */
public class ProgListMangDetail  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // Fields    

	private String mangdetailid;		// 主键ID
    private String scheduledate;		// 编单日期
    private String columnclassid;		// 栏目(PortalColumn)ID
    private Date pacceptdate;			
    private String assessorid;
    private Date passessdate;
    private Date psentdate;
    private Long state1;
    private Long state2;				
    private String idProcess;
    private String idAct;				// 活动(FlowAction)ID
    private String pop;
    private String inputmanid;			// 操作人员(Operator)ID
    private Date inputtime;				// 信息录入时间
    private String remark;				// 备注
    

    // Constructors

    /** default constructor */
    public ProgListMangDetail() {
    }

	/** minimal constructor */
    public ProgListMangDetail(String mangdetailid) {
        this.mangdetailid = mangdetailid;
    }
    
    public ProgListMangDetail(String mangdetailid, 
    		String scheduledate, String columnclassid) {
    	this.mangdetailid = mangdetailid;
    	this.scheduledate = scheduledate;
    	this.columnclassid = columnclassid;
    }
    
    public ProgListMangDetail(String mangdetailid, String scheduledate,
			String columnclassid, String assessorid, String idProcess, 
			String idAct, String pop, String inputmanid,
			Date inputtime, String remark) {
		this.mangdetailid = mangdetailid;
		this.scheduledate = scheduledate;
		this.columnclassid = columnclassid;
		this.assessorid = assessorid;
		this.idProcess = idProcess;
		this.idAct = idAct;
		this.pop = pop;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
	}
    
	/** full constructor */
	public ProgListMangDetail(String mangdetailid, String scheduledate,
			String columnclassid, Date pacceptdate, String assessorid,
			Date passessdate, Date psentdate, Long state1, Long state2,
			String idProcess, String idAct, String pop, String inputmanid,
			Date inputtime, String remark) {
		this.mangdetailid = mangdetailid;
		this.scheduledate = scheduledate;
		this.columnclassid = columnclassid;
		this.pacceptdate = pacceptdate;
		this.assessorid = assessorid;
		this.passessdate = passessdate;
		this.psentdate = psentdate;
		this.state1 = state1;
		this.state2 = state2;
		this.idProcess = idProcess;
		this.idAct = idAct;
		this.pop = pop;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
	}

   
    // Property accessors
	/**
	 * 主键ID
	 * @return mangdetailid
	 */
    public String getMangdetailid() {
        return this.mangdetailid;
    }
    
    /**
     * 主键ID
     * @param mangdetailid
     */
    public void setMangdetailid(String mangdetailid) {
        this.mangdetailid = mangdetailid;
    }

    /**
     * 编单日期
     * @return
     */
    public String getScheduledate() {
        return this.scheduledate;
    }
    
    /**
     * 编单日期
     * @param scheduledate
     */
    public void setScheduledate(String scheduledate) {
        this.scheduledate = scheduledate;
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

    public Date getPacceptdate() {
        return this.pacceptdate;
    }
    
    public void setPacceptdate(Date pacceptdate) {
        this.pacceptdate = pacceptdate;
    }

    public String getAssessorid() {
        return this.assessorid;
    }
    
    public void setAssessorid(String assessorid) {
        this.assessorid = assessorid;
    }

    public Date getPassessdate() {
        return this.passessdate;
    }
    
    public void setPassessdate(Date passessdate) {
        this.passessdate = passessdate;
    }

    public Date getPsentdate() {
        return this.psentdate;
    }
    
    public void setPsentdate(Date psentdate) {
        this.psentdate = psentdate;
    }

    public Long getState1() {
        return this.state1;
    }
    
    public void setState1(Long state1) {
        this.state1 = state1;
    }

    public Long getState2() {
        return this.state2;
    }
    
    public void setState2(Long state2) {
        this.state2 = state2;
    }

    public String getIdProcess() {
        return this.idProcess;
    }
    
    public void setIdProcess(String idProcess) {
        this.idProcess = idProcess;
    }

    /**
     * 活动(FlowAction)ID
     * @return
     */
    public String getIdAct() {
        return this.idAct;
    }
    
    /**
     * 活动(FlowAction)ID
     * @param idAct
     */
    public void setIdAct(String idAct) {
        this.idAct = idAct;
    }

    public String getPop() {
        return this.pop;
    }
    
    public void setPop(String pop) {
        this.pop = pop;
    }

    /**
     * 操作人员(Operator)ID
     * @return
     */
    public String getInputmanid() {
        return this.inputmanid;
    }
    
    /**
     * 操作人员(Operator)ID
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