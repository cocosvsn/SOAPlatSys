/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;
import java.util.List;

/**
 * Title 		: 编单日期类
 * Description	: 用于编单流程控制
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 15时34分
 */
public class ProgListMang  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields    
	private String scheduledate;		// 主键ID
	private Date date1;
	private Date date2;
	private Date date3;
	private Date date4;
	private Date date5;
	private Date date6;
	private Long state1;
	private Long state2;
	private String idProcess;			
	private String idAct;				// 编单活动(FlowAction)ID
	private String pop;					
	private String inputmanid;			// 操作人员(Operator)ID
	private Date inputtime;				// 信息录入时间
	private String remark;				// 备注
	private List<ProgListMangDetail> progListMangDetails;


    // Constructors

    /** default constructor */
    public ProgListMang() {
    }

	/** minimal constructor */
    public ProgListMang(String scheduledate) {
        this.scheduledate = scheduledate;
    }
    
    /**
     * @param scheduledate 主键ID(编单日期)
     * @param idAct	编单活动ID
     * @param operator 操作人员ID
     * @param inputTime 添加时间
     */
    public ProgListMang(String scheduledate, String idAct, 
    		String operator, Date inputTime) {
    	this.scheduledate = scheduledate;
    	this.idAct = idAct;
    	this.inputmanid = operator;
    	this.inputtime = inputTime;
    }
    
    /**
     * 
     */
    public ProgListMang(String scheduledate, String idAct, 
    		String operator, Date inputTime, String remark) {
    	this.scheduledate = scheduledate;
    	this.idAct = idAct;
    	this.inputmanid = operator;
    	this.inputtime = inputTime;
    	this.remark = remark;
    }
    
	/** full constructor */
	public ProgListMang(String scheduledate, Date date1, Date date2,
			Date date3, Date date4, Date date5, Date date6, Long state1,
			Long state2, String idProcess, String idAct, String pop,
			String inputmanid, Date inputtime, String remark) {
		this.scheduledate = scheduledate;
		this.date1 = date1;
		this.date2 = date2;
		this.date3 = date3;
		this.date4 = date4;
		this.date5 = date5;
		this.date6 = date6;
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
	 * @return
	 */
    public String getScheduledate() {
        return this.scheduledate;
    }
    /**
     * 主键ID
     * @param scheduledate
     */
    public void setScheduledate(String scheduledate) {
        this.scheduledate = scheduledate;
    }

    public Date getDate1() {
        return this.date1;
    }
    
    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return this.date2;
    }
    
    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate3() {
        return this.date3;
    }
    
    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public Date getDate4() {
        return this.date4;
    }
    
    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    public Date getDate5() {
        return this.date5;
    }
    
    public void setDate5(Date date5) {
        this.date5 = date5;
    }

    public Date getDate6() {
        return this.date6;
    }
    
    public void setDate6(Date date6) {
        this.date6 = date6;
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
     * 编单活动(FlowAction)ID
     * @return
     */
    public String getIdAct() {
        return this.idAct;
    }
    
    /**
     * 编单活动(FlowAction)ID
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

	public void setProgListMangDetails(
			List<ProgListMangDetail> progListMangDetails) {
		this.progListMangDetails = progListMangDetails;
	}
	
	public List<ProgListMangDetail> getProgListMangDetails() {
		return progListMangDetails;
	}
//    @Override
//	public boolean equals(Object obj) {
//		return this.scheduledate.equals(obj);
//	}
}