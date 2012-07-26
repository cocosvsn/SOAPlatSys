/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;

/**
 * Title 		: 节目包加扰任务类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 17时0分
 */
public class EncryptList  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields    
	private String encryptid;		// 主键ID
    private String productid;		// 节目包(ProgPackage)ID
    private String productname;		// 节目包名称
    private String progfileid;		// 节目文件(ProgramFiles)ID
    private String porductlist;		// 服务编号
    private String taskprio;		// 
    private Long encrypttype;		// 加扰类型
    private String location;		// 映射的本地文件路径
    private String name;			// 文件名
    private String groupname;		// 文件组名
    private String keyfiledestpath;	// 生成Key文件存放路径
    private String contentxml;		// 加扰单XML文件内容
    private Date date4;
    private Date date5;
    private Long dealstate;			// 处理状态[0:未处理; 1:下发任务; 8:成功; 9:失败;]
    private String dealinfo;
    private String scip;
    private Long state2;			// 加扰状态[0:未处理; 1:已分配; 2:处理中; 3:成功; 4:失败;]
    private String inputmanid;		// 操作人员(Operator)ID
    private Date inputtime;			// 信息录入时间
    private String remark;			// 备注


    // Constructors

    /** default constructor */
    public EncryptList() {
    }

	/** minimal constructor */
    public EncryptList(String encryptid) {
        this.encryptid = encryptid;
    }

	/** full constructor */
	public EncryptList(String encryptid, String productid, String productname,
			String progfileid, String porductlist, String taskprio,
			Long encrypttype, String location, String name, String groupname,
			String keyfiledestpath, String contentxml, Date date4, Date date5,
			Long dealstate, String dealinfo, String scip, Long state2,
			String inputmanid, Date inputtime, String remark) {
		this.encryptid = encryptid;
		this.productid = productid;
		this.productname = productname;
		this.progfileid = progfileid;
		this.porductlist = porductlist;
		this.taskprio = taskprio;
		this.encrypttype = encrypttype;
		this.location = location;
		this.name = name;
		this.groupname = groupname;
		this.keyfiledestpath = keyfiledestpath;
		this.contentxml = contentxml;
		this.date4 = date4;
		this.date5 = date5;
		this.dealstate = dealstate;
		this.dealinfo = dealinfo;
		this.scip = scip;
		this.state2 = state2;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
	}

   
	// Property accessors
	/**
	 * 主键ID
	 * @return
	 */
    public String getEncryptid() {
        return this.encryptid;
    }
    
    /**
     * 主键ID
     * @param encryptid
     */
    public void setEncryptid(String encryptid) {
        this.encryptid = encryptid;
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

    /**
     * 节目包名称
     * @return
     */
    public String getProductname() {
        return this.productname;
    }
    
    /**
     * 节目包名称
     * @param productname
     */
    public void setProductname(String productname) {
        this.productname = productname;
    }

    /**
     * 节目文件(ProgramFiles)ID
     * @return
     */
    public String getProgfileid() {
        return this.progfileid;
    }
    
    /**
     * 节目文件(ProgramFiles)ID
     * @param progfileid
     */
    public void setProgfileid(String progfileid) {
        this.progfileid = progfileid;
    }

    /**
     * 服务编号
     * @return
     */
    public String getPorductlist() {
        return this.porductlist;
    }
    /**
     * 服务编号
     * @param porductlist
     */
    public void setPorductlist(String porductlist) {
        this.porductlist = porductlist;
    }

    public String getTaskprio() {
        return this.taskprio;
    }
    
    public void setTaskprio(String taskprio) {
        this.taskprio = taskprio;
    }

    /**
     * 加扰类型
     * @return
     */
    public Long getEncrypttype() {
        return this.encrypttype;
    }
    
    /**
     * 加扰类型
     * @param encrypttype
     */
    public void setEncrypttype(Long encrypttype) {
        this.encrypttype = encrypttype;
    }

    /**
     * 映射的本地文件路径
     * @return
     */
    public String getLocation() {
        return this.location;
    }
    
    /**
     * 映射的本地文件路径
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 文件名
     * @return
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * 文件名
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 文件组名
     * @return
     */
    public String getGroupname() {
        return this.groupname;
    }
    
    /**
     * 文件组名
     * @param groupname
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    /**
     * 生成Key文件存放路径
     * @return
     */
    public String getKeyfiledestpath() {
        return this.keyfiledestpath;
    }
    
    /**
     * 生成Key文件存放路径
     * @param keyfiledestpath
     */
    public void setKeyfiledestpath(String keyfiledestpath) {
        this.keyfiledestpath = keyfiledestpath;
    }

    /**
     * 加扰单XML文件内容
     * @return
     */
    public String getContentxml() {
        return this.contentxml;
    }
    
    /**
     * 加扰单XML文件内容
     * @param contentxml
     */
    public void setContentxml(String contentxml) {
        this.contentxml = contentxml;
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

    /**
     * 处理状态[0:未处理; 1:下发任务; 8:成功; 9:失败;]
     * @return
     */
    public Long getDealstate() {
        return this.dealstate;
    }
    
    /**
     * 处理状态[0:未处理; 1:下发任务; 8:成功; 9:失败;]
     * @param dealstate
     */
    public void setDealstate(Long dealstate) {
        this.dealstate = dealstate;
    }

    public String getDealinfo() {
        return this.dealinfo;
    }
    
    public void setDealinfo(String dealinfo) {
        this.dealinfo = dealinfo;
    }

    public String getScip() {
        return this.scip;
    }
    
    public void setScip(String scip) {
        this.scip = scip;
    }

    /**
     * 加扰状态[0:未处理; 1:已分配; 2:处理中; 3:成功; 4:失败;]
     * @return
     */
    public Long getState2() {
        return this.state2;
    }
    
    /**
     * 加扰状态[0:未处理; 1:已分配; 2:处理中; 3:成功; 4:失败;]
     * @param state2
     */
    public void setState2(Long state2) {
        this.state2 = state2;
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