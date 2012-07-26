package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * PtpPgpRel entity. @author MyEclipse Persistence Tools
 */

public class PtpPgpRel  implements java.io.Serializable {
    // Fields    
     private String relid;			// 主键ID
     private String ptpid;			// 页面包装ID
     private String productid;		// 节目包ID
     private Long sequence;			// 序列ID(分集数)
     private Long epicodeid;		// 分集数
     private String inputmanid;		// 录入人员编号
     private Date inputtime;		// 录入时间
     private String remark;			// 备注


    // Constructors
    /** default constructor */
    public PtpPgpRel() {
    }

    /**
     * minimal constructor
     * @param relid 主键ID
     */
    public PtpPgpRel(String relid) {
        this.relid = relid;
    }
    
    /**
     * full constructor
     * @param relid 		主键ID
     * @param ptpid			页面包装ID
     * @param productid		节目包ID
     * @param sequence		序列ID(分集数)
     * @param epicodeid		分集数
     * @param inputmanid	录入人员编号
     * @param inputtime		录入时间
     * @param remark		备注
     */
    public PtpPgpRel(String relid, String ptpid, String productid, 
    		Long sequence, Long epicodeid, String inputmanid, 
    		Date inputtime, String remark) {
        this.relid = relid;
        this.ptpid = ptpid;
        this.productid = productid;
        this.sequence = sequence;
        this.epicodeid = epicodeid;
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
     * 页面包装ID
     * @return
     */
    public String getPtpid() {
        return this.ptpid;
    }
    
    /**
     * 页面包装ID
     * @param ptpid
     */
    public void setPtpid(String ptpid) {
        this.ptpid = ptpid;
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
     * 序列ID(分集数)
     * @return
     */
    public Long getSequence() {
        return this.sequence;
    }
    
    /**
     * 序列ID(分集数)
     * @param sequence
     */
    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    /**
     * 分集数
     * @return
     */
    public Long getEpicodeid() {
        return this.epicodeid;
    }
    
    /**
     * 分集数
     * @param epicodeid
     */
    public void setEpicodeid(Long epicodeid) {
        this.epicodeid = epicodeid;
    }

    /**
     * 录入人员编号
     * @return
     */
    public String getInputmanid() {
        return this.inputmanid;
    }
    
    /**
     * 录入人员编号
     * @param inputmanid
     */
    public void setInputmanid(String inputmanid) {
        this.inputmanid = inputmanid;
    }

    /**
     * 录入时间
     * @return
     */
    public Date getInputtime() {
        return this.inputtime;
    }
    
    /**
     * 录入时间
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