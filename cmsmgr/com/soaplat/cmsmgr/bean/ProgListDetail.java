/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;

/**
 * Title 		: 编单类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 11时45分
 */
public class ProgListDetail  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    // Fields    
	private String	proglistdetailid;	// 主键ID
	private String	scheduledate;		// 编单日期
	private String	columnclassid;		// 栏目(PortalColumn)ID
	private String	defcatseq;			// 栏目路径序列
	private String	productid;			// 节目包(ProgPackage)ID(22位)
	private String	productName;		// 节目包名称
	private String	broadcastpolicy;	// 节目包轮播次数
	private String	ETitle;				// *产品列表(编单完成)
	private String	sumfilesize;		// 文件总大小
	private String	lengthPlay;			// 时间长度
	private String	pop;				// 操作人员(Operator)ID
	private String	dealInfo;			// 处理信息
	private Date	dealDate;			// 处理日期
	private Long	byint;				// ---暂未使用	// 备用int
	private String	bystr;				// ---暂未使用	// 备用String
	private Date	bydate;				// ---暂未使用	// 备用Date
	private Long	productCategoryId;	// 备用
	private String	scheduleTag;		// 是否加入节目预告[Y:是, N:否]
	private String	jsFileID;			// js文件ID
	private String	productInfoID;		// 产品信息ID
	
	private String srvid;
	private Long lnum;					// 序号(用于编单中节目包的排序)
	private Date validFrom;				// *上线日期
	private Date validTo;				// *下线日期
	private Long validFlag;				// ---暂未使用	 // *下线标识(0上线1下线)
	private Date startPlay;				// ---暂未使用	// 播出时间
	private Date endPlay;				// ---暂未使用	// 结束时间
	private String productname;			// 节目包名称
	private Date updatetime;			// 节目包更新日期
	private String description;			// 节目描述
	private String EDescription;		// 节目描述(英文)
	private Long filesizehi;			// ---暂未使用	// 文件总大小高位
	private Long filesizelow;			// ---暂未使用	// 文件总大小低位
//	private String defcatseq;			// 栏目路径序列
	//private Long state1;				//
	//private Long dealstate;			// ---暂未使用	// 处理状态(0未处理1处理8失败9成功)
	private String dealstateinfo;		// ---暂未使用	// 处理状态描述
	private Long overlapflag;			// ---暂未使用	// 是否覆盖标志
	private Long uploaduser;			// ---暂未使用	// 上传人
	private Date uploaddate;			// 上传日期
	private String idProcess;			// ---暂未使用// 流程编号
	private String idAct;				// 活动(FlowAction)ID

    // Constructors

    /** default constructor */
    public ProgListDetail() {
    }

	/** minimal constructor */
    public ProgListDetail(String proglistdetailid) {
        this.proglistdetailid = proglistdetailid;
    }
    
    /**
     * 
     * @param scheduledate 编单日期
     * @param columnclassid 栏目ID
     * @param productid 节目包ID
     */
    public ProgListDetail(String scheduledate, String columnclassid, 
    		String productid) {
    	this.scheduledate = scheduledate;
    	this.columnclassid = columnclassid;
    	this.productid = productid;
    }
    
	public ProgListDetail(String proglistdetailid, String scheduledate,
			String columnclassid, String productid, String broadcastpolicy,
			String eTitle, String sumfilesize, String lengthPlay, String pop,
			String dealInfo, Date dealDate, Long byint, String bystr,
			Date bydate, Long productCategoryId, String scheduleTag,
			String jsFileID, String productInfoID) {
		super();
		this.proglistdetailid = proglistdetailid;
		this.scheduledate = scheduledate;
		this.columnclassid = columnclassid;
		this.productid = productid;
		this.broadcastpolicy = broadcastpolicy;
		this.ETitle = eTitle;
		this.sumfilesize = sumfilesize;
		this.lengthPlay = lengthPlay;
		this.pop = pop;
		this.dealInfo = dealInfo;
		this.dealDate = dealDate;
		this.byint = byint;
		this.bystr = bystr;
		this.bydate = bydate;
		this.productCategoryId = productCategoryId;
		this.scheduleTag = scheduleTag;
		this.jsFileID = jsFileID;
		this.productInfoID = productInfoID;
	}
	
	public ProgListDetail(String proglistdetailid, String scheduledate,
			String columnclassid, String productid, String broadcastpolicy,
			String eTitle, String sumfilesize, String lengthPlay, String pop,
			String dealInfo, Date dealDate, Long byint, String bystr,
			Date bydate, Long productCategoryId, String scheduleTag,
			String jsFileID, String productInfoID, long state, long dealstaate,
			String productName) {
		super();
		this.proglistdetailid = proglistdetailid;
		this.scheduledate = scheduledate;
		this.columnclassid = columnclassid;
		this.productid = productid;
		this.broadcastpolicy = broadcastpolicy;
		this.ETitle = eTitle;
		this.sumfilesize = sumfilesize;
		this.lengthPlay = lengthPlay;
		this.pop = pop;
		this.dealInfo = dealInfo;
		this.dealDate = dealDate;
		this.byint = byint;
		this.bystr = bystr;
		this.bydate = bydate;
		this.productCategoryId = productCategoryId;
		this.scheduleTag = scheduleTag;
		this.jsFileID = jsFileID;
		this.productInfoID = productInfoID;
		this.filesizehi = state;
		this.filesizelow = dealstaate;
		this.productname = productName;
	}
	
	public ProgListDetail(String proglistdetailid, String scheduledate,
			String columnclassid, String productid, String broadcastpolicy,
			String eTitle, String sumfilesize, String lengthPlay, String pop,
			String dealInfo, Date dealDate, Long byint, String bystr,
			Date bydate, Long productCategoryId, String scheduleTag,
			String jsFileID, String productInfoID, long state, long dealstaate,
			String productName, Long lnum) {
		super();
		this.proglistdetailid = proglistdetailid;
		this.scheduledate = scheduledate;
		this.columnclassid = columnclassid;
		this.productid = productid;
		this.broadcastpolicy = broadcastpolicy;
		this.ETitle = eTitle;
		this.sumfilesize = sumfilesize;
		this.lengthPlay = lengthPlay;
		this.pop = pop;
		this.dealInfo = dealInfo;
		this.dealDate = dealDate;
		this.byint = byint;
		this.bystr = bystr;
		this.bydate = bydate;
		this.productCategoryId = productCategoryId;
		this.scheduleTag = scheduleTag;
		this.jsFileID = jsFileID;
		this.productInfoID = productInfoID;
		this.filesizehi = state;
		this.filesizelow = dealstaate;
		this.productname = productName;
		this.lnum = lnum;
	}

	// Property accessors
	/**
	 * 主键ID
	 * @return
	 */
    public String getProglistdetailid() {
        return this.proglistdetailid;
    }
    
    /**
     * 主键ID
     * @param proglistdetailid
     */
    public void setProglistdetailid(String proglistdetailid) {
        this.proglistdetailid = proglistdetailid;
    }

    /**
     * 序号(用于编单中节目包的排序)
     * @return
     */
    public Long getLnum() {
        return this.lnum;
    }
    
    /**
     * 序号(用于编单中节目包的排序)
     * @param lnum
     */
    public void setLnum(Long lnum) {
        this.lnum = lnum;
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
     * 节目包(ProgPackage)ID(22位)
     * @return
     */
    public String getProductid() {
        return this.productid;
    }
    
    /**
     * 节目包(ProgPackage)ID(22位)
     * @param productid
     */
    public void setProductid(String productid) {
        this.productid = productid;
    }


    public Long getProductCategoryId() {
        return this.productCategoryId;
    }
    
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    /**
     * 上线日期
     * @return
     */
    public Date getValidFrom() {
        return this.validFrom;
    }
    
    /**
     * 上线日期
     * @param validFrom
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * 下线日期
     * @return
     */
    public Date getValidTo() {
        return this.validTo;
    }
    
    /**
     * 下线日期
     * @param validTo
     */
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Long getValidFlag() {
        return this.validFlag;
    }
    
    public void setValidFlag(Long validFlag) {
        this.validFlag = validFlag;
    }

    public Date getStartPlay() {
        return this.startPlay;
    }
    
    public void setStartPlay(Date startPlay) {
        this.startPlay = startPlay;
    }

    public Date getEndPlay() {
        return this.endPlay;
    }
    
    public void setEndPlay(Date endPlay) {
        this.endPlay = endPlay;
    }

    public String getLengthPlay() {
        return this.lengthPlay;
    }
    
    public void setLengthPlay(String lengthPlay) {
        this.lengthPlay = lengthPlay;
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
     * 节目包更新日期
     * @return
     */
    public Date getUpdatetime() {
        return this.updatetime;
    }
    
    /**
     * 节目包更新日期
     * @param updatetime
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * *产品列表(编单完成)
     * @return
     */
    public String getETitle() {
        return this.ETitle;
    }
    
    /**
     * *产品列表(编单完成)
     * @param ETitle
     */
    public void setETitle(String ETitle) {
        this.ETitle = ETitle;
    }

    /**
     * 节目包描述
     * @return
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * 节目包描述
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 节目描述(英文)
     * @return
     */
    public String getEDescription() {
        return this.EDescription;
    }
    
    /**
     * 节目描述(英文)
     * @param EDescription
     */
    public void setEDescription(String EDescription) {
        this.EDescription = EDescription;
    }

    public Long getFilesizehi() {
        return this.filesizehi;
    }
    
    public void setFilesizehi(Long filesizehi) {
        this.filesizehi = filesizehi;
    }

    public Long getFilesizelow() {
        return this.filesizelow;
    }
    
    public void setFilesizelow(Long filesizelow) {
        this.filesizelow = filesizelow;
    }

    public String getSumfilesize() {
        return this.sumfilesize;
    }
    
    public void setSumfilesize(String sumfilesize) {
        this.sumfilesize = sumfilesize;
    }

//    /**
//     * 栏目路径序列
//     * @return
//     */
//    public String getDefcatseq() {
//        return this.defcatseq;
//    }
//    
//    /**
//     * 栏目路径序列
//     * @param defcatseq
//     */
//    public void setDefcatseq(String defcatseq) {
//        this.defcatseq = defcatseq;
//    }

    public String getDealstateinfo() {
        return this.dealstateinfo;
    }
    
    public void setDealstateinfo(String dealstateinfo) {
        this.dealstateinfo = dealstateinfo;
    }

    public Long getOverlapflag() {
        return this.overlapflag;
    }
    
    public void setOverlapflag(Long overlapflag) {
        this.overlapflag = overlapflag;
    }

    public Long getUploaduser() {
        return this.uploaduser;
    }
    
    public void setUploaduser(Long uploaduser) {
        this.uploaduser = uploaduser;
    }

    /**
     * 上传日期
     * @return
     */
    public Date getUploaddate() {
        return this.uploaddate;
    }
    
    /**
     * 上传日期
     * @param uploaddate
     */
    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
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

    /**
     * 操作人员(Operator)ID
     * @return
     */
    public String getPop() {
        return this.pop;
    }
    
    /**
     * 操作人员(Operator)ID
     * @param pop
     */
    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getDealInfo() {
        return this.dealInfo;
    }
    
    public void setDealInfo(String dealInfo) {
        this.dealInfo = dealInfo;
    }

    public Date getDealDate() {
        return this.dealDate;
    }
    
    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public Long getByint() {
        return this.byint;
    }
    
    public void setByint(Long byint) {
        this.byint = byint;
    }

    public String getBystr() {
        return this.bystr;
    }
    
    public void setBystr(String bystr) {
        this.bystr = bystr;
    }

    public Date getBydate() {
        return this.bydate;
    }
    
    public void setBydate(Date bydate) {
        this.bydate = bydate;
    }

	
    public String getScheduledate() {
		return scheduledate;
	}
	

	public void setScheduledate(String scheduledate) {
		this.scheduledate = scheduledate;
	}
	

	public String getBroadcastpolicy() {
		return broadcastpolicy;
	}
	

	public void setBroadcastpolicy(String broadcastpolicy) {
		this.broadcastpolicy = broadcastpolicy;
	}
	

	public String getScheduleTag() {
		return scheduleTag;
	}
	

	public void setScheduleTag(String scheduleTag) {
		this.scheduleTag = scheduleTag;
	}
	

	public String getJsFileID() {
		return jsFileID;
	}
	

	public void setJsFileID(String jsFileID) {
		this.jsFileID = jsFileID;
	}
	

	public String getProductInfoID() {
		return productInfoID;
	}
	

	public void setProductInfoID(String productInfoID) {
		this.productInfoID = productInfoID;
	}
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDefcatseq() {
		return defcatseq;
	}

	public void setDefcatseq(String defcatseq) {
		this.defcatseq = defcatseq;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void setSrvid(String srvid) {
		this.srvid = srvid;
	}
	
	public String getSrvid() {
		return srvid;
	}
}