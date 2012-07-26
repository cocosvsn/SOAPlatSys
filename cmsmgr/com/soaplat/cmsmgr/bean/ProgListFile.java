package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgListFile entity. @author MyEclipse Persistence Tools
 */

public class ProgListFile  implements java.io.Serializable {


    // Fields    

     private String columnfileid;	// 主键ID
     private String scheduledate;	// 编单日期
     private String columnclassid;	// 栏目编号
     private String filename;		// 文件名称
     private Long filetype;			// 文件类型[0:PAGEXML, 1:JS, 2:PTL, 5:Key文件, 6:预告JS, 7:节目包JS, 8:迁移单, 9:播发单XML]
     private Date date1;			// 生成日期
     private Date date2;			// 生成JS日期
     private Date date3;			// 生成静态页面日期
     private Date date4;			// 计划播发日期
     private Long state1;			// 状态(0有效1无效)
     private Long state2;			// 状态2
     private String columnxml;		// 页面XML文件
     private String columnjs;		// 页面JS文件
     private String columnhtml;		// 静态页面文件
     private String idProcess;		// 流程编号
     private String idAct;			// 活动编号
     private String pop;			// 操作员编号
     private String inputmanid;		// 信息录入人
     private Date inputtime;		// 信息录入时间
     private String remark;			// 备注

    // Constructors

    /** default constructor */
    public ProgListFile() {
    }

	/** minimal constructor */
    public ProgListFile(String columnfileid) {
        this.columnfileid = columnfileid;
    }
    
    /** full constructor */
    public ProgListFile(String columnfileid, String scheduledate, String columnclassid, String filename, Long filetype, Date date1, Date date2, Date date3, Date date4, Long state1, Long state2, String columnxml, String columnjs, String columnhtml, String idProcess, String idAct, String pop, String inputmanid, Date inputtime, String remark) {
        this.columnfileid = columnfileid;
        this.scheduledate = scheduledate;
        this.columnclassid = columnclassid;
        this.filename = filename;
        this.filetype = filetype;
        this.date1 = date1;
        this.date2 = date2;
        this.date3 = date3;
        this.date4 = date4;
        this.state1 = state1;
        this.state2 = state2;
        this.columnxml = columnxml;
        this.columnjs = columnjs;
        this.columnhtml = columnhtml;
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
    public String getColumnfileid() {
        return this.columnfileid;
    }
    
    /**
     * 主键ID
     * @param columnfileid
     */
    public void setColumnfileid(String columnfileid) {
        this.columnfileid = columnfileid;
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
     * 栏目ID
     * @return
     */
    public String getColumnclassid() {
        return this.columnclassid;
    }
    
    /**
     * 栏目编号
     * @param columnclassid
     */
    public void setColumnclassid(String columnclassid) {
        this.columnclassid = columnclassid;
    }

    /**
     * 文件名称
     * @return
     */
    public String getFilename() {
        return this.filename;
    }
    
    /**
     * 文件名称
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 文件类型[0:PAGEXML, 1:JS, 2:PTL, 5:Key文件, 6:预告JS, 7:节目包JS, 8:迁移单, 9:播发单XML]
     * @return
     */
    public Long getFiletype() {
        return this.filetype;
    }
    
    /**
     * 文件类型[0:PAGEXML, 1:JS, 2:PTL, 5:Key文件, 6:预告JS, 7:节目包JS, 8:迁移单, 9:播发单XML]
     * @param filetype
     */
    public void setFiletype(Long filetype) {
        this.filetype = filetype;
    }

    /**
     * 生成日期
     * @return
     */
    public Date getDate1() {
        return this.date1;
    }
    
    /**
     * 生成日期
     * @param date1
     */
    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    /**
     * 生成JS日期
     * @return
     */
    public Date getDate2() {
        return this.date2;
    }
    
    /**
     * 生成JS日期
     * @param date2
     */
    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate3() {
        return this.date3;
    }
    
    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    /**
     * 计划播发日期
     * @return
     */
    public Date getDate4() {
        return this.date4;
    }
    
    /**
     * 计划播发日期
     * @param date4
     */
    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    /**
     * 状态[0:有效, 1:无效]
     * @return
     */
    public Long getState1() {
        return this.state1;
    }
    
    /**
     * 状态[0:有效, 1:无效]
     * @param state1
     */
    public void setState1(Long state1) {
        this.state1 = state1;
    }

    public Long getState2() {
        return this.state2;
    }
    
    public void setState2(Long state2) {
        this.state2 = state2;
    }

    public String getColumnxml() {
        return this.columnxml;
    }
    
    public void setColumnxml(String columnxml) {
        this.columnxml = columnxml;
    }

    public String getColumnjs() {
        return this.columnjs;
    }
    
    public void setColumnjs(String columnjs) {
        this.columnjs = columnjs;
    }

    public String getColumnhtml() {
        return this.columnhtml;
    }
    
    public void setColumnhtml(String columnhtml) {
        this.columnhtml = columnhtml;
    }

    /**
     * 流程编号
     * @return
     */
    public String getIdProcess() {
        return this.idProcess;
    }
    
    /**
     * 流程编号
     * @param idProcess
     */
    public void setIdProcess(String idProcess) {
        this.idProcess = idProcess;
    }

    /**
     * 活动编号
     * @return
     */
    public String getIdAct() {
        return this.idAct;
    }
    
    /**
     * 活动编号
     * @param idAct
     */
    public void setIdAct(String idAct) {
        this.idAct = idAct;
    }

    /**
     * 操作员编号
     * @return
     */
    public String getPop() {
        return this.pop;
    }
    
    /**
     * 操作员编号
     * @param pop
     */
    public void setPop(String pop) {
        this.pop = pop;
    }

    /**
     * 操作人员编号
     * @return
     */
    public String getInputmanid() {
        return this.inputmanid;
    }
    
    /**
     * 操作人员编号
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