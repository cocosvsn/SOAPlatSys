package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * Bpmc entity. @author MyEclipse Persistence Tools
 */

public class Bpmc  implements java.io.Serializable {


    // Fields    

     private Long bpmcid;			// 主键ID
     private String objectid;		// 对象ID
     private String creater;		// 对象创建者
     private Date createdate;		// 对象创建日期
     private String sender;			// 发送者
     private String sends;			// 发送活动
     private String receiver;		// 接收者
     private String receives;		// 接收活动
     private Date senddate;			// 发送日期
     private Date receivedate;		// 接收日期
     private String sendremark;		// 备注
     private String state;			// 状态（新建N   回退R  顺序P  完成C 修改U）


    // Constructors

    /** default constructor */
    public Bpmc() {
    }

    public Bpmc(String objectid, String creater, String sender, String sends, 
    		String receiver, String receives,
			String sendremark, String state) {
    	Date currentDate = new Date();
		this.objectid = objectid;
		this.creater = creater;
		this.createdate = currentDate;
		this.sender = sender;
		this.sends = sends;
		this.receiver = receiver;
		this.receives = receives;
		this.senddate = currentDate;
		this.receivedate = currentDate;
		this.sendremark = sendremark;
		this.state = state;
	}
    
    /** full constructor */
	public Bpmc(String objectid, String creater, Date createdate,
			String sender, String sends, String receiver, String receives,
			Date senddate, Date receivedate, String sendremark, String state) {
		this.objectid = objectid;
		this.creater = creater;
		this.createdate = createdate;
		this.sender = sender;
		this.sends = sends;
		this.receiver = receiver;
		this.receives = receives;
		this.senddate = senddate;
		this.receivedate = receivedate;
		this.sendremark = sendremark;
		this.state = state;
	}

   
    // Property accessors
	/**
	 * 主键ID
	 * @return
	 */
    public Long getBpmcid() {
        return this.bpmcid;
    }
    
    /**
     * 主键ID
     * @param bpmcid
     */
    public void setBpmcid(Long bpmcid) {
        this.bpmcid = bpmcid;
    }

    /**
     * 对象ID
     * @return
     */
    public String getObjectid() {
        return this.objectid;
    }
    
    /**
     * 对象ID
     * @param objectid
     */
    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    /**
     * 对象创建者
     * @return
     */
    public String getCreater() {
        return this.creater;
    }
    
    /**
     * 对象创建者
     * @param creater
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * 对象创建日期
     * @return
     */
    public Date getCreatedate() {
        return this.createdate;
    }
    
    /**
     * 对象创建日期
     * @param createdate
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * 发送者
     * @return
     */
    public String getSender() {
        return this.sender;
    }
    
    /**
     * 发送者
     * @param sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * 发送活动
     * @return
     */
    public String getSends() {
        return this.sends;
    }
    
    /**
     * 发送活动
     * @param sends
     */
    public void setSends(String sends) {
        this.sends = sends;
    }

    /**
     * 接收者
     * @return
     */
    public String getReceiver() {
        return this.receiver;
    }
    
    /**
     * 接收者
     * @param receiver
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * 接收活动
     * @return
     */
    public String getReceives() {
        return this.receives;
    }
    
    /**
     * 接收活动
     * @param receives
     */
    public void setReceives(String receives) {
        this.receives = receives;
    }

    /**
     * 发送日期
     * @return
     */
    public Date getSenddate() {
        return this.senddate;
    }
    
    /**
     * 发送日期
     * @param senddate
     */
    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }

    /**
     * 接收日期
     * @return
     */
    public Date getReceivedate() {
        return this.receivedate;
    }
    
    /**
     * 接收日期
     * @param receivedate
     */
    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }

    /**
     * 备注
     * @return
     */
    public String getSendremark() {
        return this.sendremark;
    }
    
    /**
     * 备注
     * @param sendremark
     */
    public void setSendremark(String sendremark) {
        this.sendremark = sendremark;
    }

    /**
     * 状态（新建N   回退R  顺序P  完成C）
     * @return
     */
    public String getState() {
        return this.state;
    }
    
    /**
     * 状态（新建N   回退R  顺序P  完成C）
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }
}