package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * ProgList entity. @author MyEclipse Persistence Tools
 */

public class ProgList  implements java.io.Serializable {


    // Fields    

     private String pdate;
     private Date pacceptdate;
     private Date validFrom;
     private Date validTo;
     private String pagepath;
     private String idProcess;
     private String idAct;
     private String pop;
     private Date createDate;
     private Long dealState;
     private Date dealDate;


    // Constructors

    /** default constructor */
    public ProgList() {
    }

	/** minimal constructor */
    public ProgList(String pdate) {
        this.pdate = pdate;
    }
    
    /** full constructor */
    public ProgList(String pdate, Date pacceptdate, Date validFrom, Date validTo, String pagepath, String idProcess, String idAct, String pop, Date createDate, Long dealState, Date dealDate) {
        this.pdate = pdate;
        this.pacceptdate = pacceptdate;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.pagepath = pagepath;
        this.idProcess = idProcess;
        this.idAct = idAct;
        this.pop = pop;
        this.createDate = createDate;
        this.dealState = dealState;
        this.dealDate = dealDate;
    }

   
    // Property accessors

    public String getPdate() {
        return this.pdate;
    }
    
    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public Date getPacceptdate() {
        return this.pacceptdate;
    }
    
    public void setPacceptdate(Date pacceptdate) {
        this.pacceptdate = pacceptdate;
    }

    public Date getValidFrom() {
        return this.validFrom;
    }
    
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return this.validTo;
    }
    
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public String getPagepath() {
        return this.pagepath;
    }
    
    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }

    public String getIdProcess() {
        return this.idProcess;
    }
    
    public void setIdProcess(String idProcess) {
        this.idProcess = idProcess;
    }

    public String getIdAct() {
        return this.idAct;
    }
    
    public void setIdAct(String idAct) {
        this.idAct = idAct;
    }

    public String getPop() {
        return this.pop;
    }
    
    public void setPop(String pop) {
        this.pop = pop;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getDealState() {
        return this.dealState;
    }
    
    public void setDealState(Long dealState) {
        this.dealState = dealState;
    }

    public Date getDealDate() {
        return this.dealDate;
    }
    
    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }
   








}