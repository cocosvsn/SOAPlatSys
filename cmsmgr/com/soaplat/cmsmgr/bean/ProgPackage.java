/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;

/**
 * Title 		: 节目包类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 11时45分
 */
public class ProgPackage  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields    
	private String	productid;				// 主键ID
	private Long	styleid;				// 样式(PackStyle)ID
	private String	ptglobalid;				// V1.3强制保留标识[Y: 强制保留, N: 不强制保留] ---暂未使用
	private String	productname;			// 节目包名称
	private String	description;			// 节目包描述
	private String	category;				// 节目包类型(Dict.dictcode)
	private String	titlebrief;				// 节目包标题的缩写
	private String	epicodename;			// 电视剧或系列片的名称
	private String	epicodeid;				// 电视剧或系列片的序号(分集号)
	private String	lengthtc;				// ---暂未使用 --生成节目预告时存放是否生成节目预告标识 ( ProgListDetail.scheduleTag )
	private String	progtype;				// 节目包类型[V:视频节目包; R:富媒体节目包;]
	private String	epicodenumber;			// 电视剧或系列片的总集数
	private String	subtitlelanguage;		// ---暂未使用
	private String	audiolanguage;			// 影片类型
	private String	director;				// 导演
	private String	actors;					// 主演
	private String	shootdate;				// ---暂未使用 --生成节目包JS时存放栏目Code ( PortalColumn.defcatcode )
	private String	issuedate;				// ---暂未使用 --生成节目包JS时存放产品信息ID ( ProgListDetail.productInfoID )
	private String	countrydist;			// 国家/地区
	private Date	subscriberstime;		// 版权开始日期
	private Date	subscriberetime;		// 版权结束日期
	private String	licensingWindowStart;	// ---暂未使用 --生成节目包JS时存放栏目ID ( PortalColumn.columnclassid )
	private String	licensingWindowEnd;		// ---暂未使用 --生成节目包JS时存放栏目名称 ( PortalColumn.columnname )
	private String	ppxml;					// 节目包描述XML
	private String	inputmanid;				// 操作人员ID
	private Date	inputtime;				// 信息录入时间
	private String	updatemanid;			// ---暂未使用 --生成节目预告时存放产品信息ID ( ProgListDetail.productInfoID )
	private Date	updatetime;				// 节目包最后修改时间
	private String	remark;					// 备注
	private Long	state;					// 节目包存储等级状态 {北京:[-1:未导入; 0:导入区; 1:缓存库; 2:加扰库; 3:播发库;], 上海:[1:缓存库; 9:北京缓存库;]}
	private Long	dealstate;				// 节目包使用状态[0:未处理; 1:处理中; 8:失败; 9:成功;]
	private Long	importFlag;				// 导入标识[-1: 未导入; 0: 迁移中; 1: 已导入]
	private Long	mainFileFlag;			// 主文件标识[-1: 未导入; 1: 已导入; 2: 内容加密中; 3: 已内容加密]
	private String	siteCode;				// 品牌Code
	private Long	filesizehi;				// 用于统计节目包文件大小
	private Long	filesizelow;			// V1.3强制保留有效期 存放Timestamp ---暂未使用
	private String	sumfilesize;			// ---暂未使用 --节目包维护和查询, 显示品牌名称

    // Constructors

    /** default constructor */
    public ProgPackage() {
    	this.setState(1L);			// 节目包状态（0导入1缓存库2加扰库3播控库）
    	this.setDealstate(0L);		// 处理状态(0未处理1处理8失败9成功)
    }

	/** minimal constructor */
    public ProgPackage(String productid) {
        this.productid = productid;
    }
    
    /**
     * 编单定义,强制删除查询
     * @param progPackageId		节目包ID
     * @param progPackageName	节目包名称
     * @param style				样式名称
     * @param type				类型
     * @param site				品牌名称
     * @param inputTime			录入时间
     */
    public ProgPackage(String progPackageId, String progPackageName, 
    		String type, String style, String site, Long styleId, Date inputTime) {
    	this.productid = progPackageId;
    	this.productname = progPackageName;
    	this.subtitlelanguage = type;
    	this.remark = style;
    	this.styleid = styleId;
    	this.siteCode = site;
    	
    	this.inputtime = inputTime;
    }
    
    /**
     * 节目包维护
     * @param progPackageId		节目包ID
     * @param sytleId			样式ID
     * @param progPackageName	节目包名称
     * @param type				影片分类
     * @param count				分集数
     * @param director			导演
     * @param actors			主演
     * @param country			国家
     * @param style				样式名称
     * @param site				品牌名称
     * @param inputTime			录入时间
     */
    public ProgPackage(String progPackageId, Long sytleId, String progPackageName, 
    		String type, String count, String director, String actors, 
    		String country, String style, String site, Date inputTime,
    		String xml, String progType) {
    	this.productid = progPackageId;
    	this.styleid = sytleId;
    	this.productname = progPackageName;
    	this.audiolanguage = type;
    	this.epicodeid = count;
    	this.director = director;
    	this.actors = actors;
    	this.countrydist = country;
    	this.remark = style;
    	this.siteCode = site;
    	this.inputtime = inputTime;
    	this.ppxml = xml;
    	this.progtype = progType;
    }
    
    /**
     * 用于节目预告
     * @param productid			主键ID
     * @param styleid			样式(PackStyle)ID
     * @param productname		节目包名称
     * @param description		节目包描述
     * @param category			节目包类型(Dict.dictcode)
     * @param titlebrief		节目包标题的缩写
     * @param epicodename		电视剧或系列片的名称
     * @param epicodeid			电视剧或系列片的序号(分集号)
     * @param lengthtc			是否生成节目预告标识[Y: 生成, N: 不生成]
     * @param progtype			节目包类型[V:视频节目包; R:富媒体节目包;]
     * @param epicodenumber		电视剧或系列片的总集数
     * @param audiolanguage		影片类型
     * @param director			导演
     * @param actors			主演
     * @param countrydist		国家/地区
     * @param ppxml				节目包描述XML
     * @param inputmanid		操作人员ID
     * @param inputtime			信息录入时间	
     * @param remark			备注
     * @param productInfoId		产品( TProductInfo )ID
     */
    public ProgPackage(String productid, Long styleid, String productname, 
    		String description, String category, String titlebrief, 
    		String epicodename, String epicodeid, String lengthtc, 
    		String progtype, String epicodenumber, String audiolanguage, 
    		String director, String actors, String countrydist, String ppxml, 
    		String inputmanid, Date inputtime, String remark, String productInfoId) {
		this.productid = productid;
		this.styleid = styleid;
		this.productname = productname;
		this.description = description;
		this.category = category;
		this.titlebrief = titlebrief;
		this.epicodename = epicodename;
		this.epicodeid = epicodeid;
		this.lengthtc = lengthtc;
		this.progtype = progtype;
		this.epicodenumber = epicodenumber;
		this.audiolanguage = audiolanguage;
		this.director = director;
		this.actors = actors;
		this.countrydist = countrydist;
		this.ppxml = ppxml;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
		this.updatemanid = productInfoId;
	}

    /**
     * 用于生成节目包JS
     * @param productid			主键ID
     * @param productname		节目包名称
     * @param description		节目包描述
     * @param ppxml				节目包描述XML
     * @param portalColumnId	栏目( PortalColumn )ID
     * @param portalColumnName	栏目名称
     * @param portalColumnCode	栏目Code
     * @param productInfoId		产品信息( TProductInfo )ID
     */
    public ProgPackage(String productid, String productname, String description,
    		String ppxml, String portalColumnId, String portalColumnName, 
    		String portalColumnCode, String productInfoId, Long state, Long dealState) {
		this.productid = productid;
		this.productname = productname;
		this.description = description;
		this.ppxml = ppxml;
		this.licensingWindowStart = portalColumnId;
		this.licensingWindowEnd = portalColumnName;
		this.shootdate = portalColumnCode;
		this.issuedate = productInfoId;
		this.state = state;
		this.dealstate = dealState;
	}
    
	/** full constructor */
	public ProgPackage(String productid, Long styleid, String ptglobalid,
			String productname, String description, String category,
			String titlebrief, String epicodename, String epicodeid,
			String lengthtc, String licensingWindowStart,
			String licensingWindowEnd, String progtype, String epicodenumber,
			String subtitlelanguage, String audiolanguage, String director,
			String actors, String shootdate, String issuedate,
			String countrydist, Date subscriberstime, Date subscriberetime,
			String ppxml, String inputmanid, Date inputtime, String remark,
			Long filesizehi, Long filesizelow, String sumfilesize,
			String updatemanid, Date updatetime, String siteCode) {
		this.productid = productid;
		this.styleid = styleid;
		this.ptglobalid = ptglobalid;
		this.productname = productname;
		this.description = description;
		this.category = category;
		this.titlebrief = titlebrief;
		this.epicodename = epicodename;
		this.epicodeid = epicodeid;
		this.lengthtc = lengthtc;
		this.licensingWindowStart = licensingWindowStart;
		this.licensingWindowEnd = licensingWindowEnd;
		this.progtype = progtype;
		this.epicodenumber = epicodenumber;
		this.subtitlelanguage = subtitlelanguage;
		this.audiolanguage = audiolanguage;
		this.director = director;
		this.actors = actors;
		this.shootdate = shootdate;
		this.issuedate = issuedate;
		this.countrydist = countrydist;
		this.subscriberstime = subscriberstime;
		this.subscriberetime = subscriberetime;
		this.ppxml = ppxml;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
		this.filesizehi = filesizehi;
		this.filesizelow = filesizelow;
		this.sumfilesize = sumfilesize;
		this.updatemanid = updatemanid;
		this.updatetime = updatetime;
		this.siteCode = siteCode;
	}

	public ProgPackage(String productid, String productname, String siteCode, 
			Date subscriberstime, Date subscriberetime, Long state, 
			Long dealstate, Date inputtime, String stylename) {
		this.productid = productid;
		this.productname = productname;
		this.siteCode = siteCode;
		this.subscriberstime = subscriberstime;
		this.subscriberetime = subscriberetime;
		this.state = state;
		this.dealstate = dealstate;
		this.inputtime = inputtime;
		this.audiolanguage = stylename;
		
	}
   
    // Property accessors
	/**
	 * 主键ID
	 * @return
	 */
    public String getProductid() {
        return this.productid;
    }
    
    /**
     * 主键ID
     * @param productid
     */
    public void setProductid(String productid) {
        this.productid = productid;
    }

    /**
     * 样式()ID
     * @return
     */
    public Long getStyleid() {
        return this.styleid;
    }
    
    /**
     * 样式()ID
     * @param styleid
     */
    public void setStyleid(Long styleid) {
        this.styleid = styleid;
    }

    /**
     * V1.3强制保留标识[Y: 强制保留, N: 不强制保留]
     * @return
     */
    public String getPtglobalid() {
        return this.ptglobalid;
    }
    
    /**
     * V1.3强制保留标识[Y: 强制保留, N: 不强制保留]
     * @param ptglobalid
     */
    public void setPtglobalid(String ptglobalid) {
        this.ptglobalid = ptglobalid;
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
     * 节目包类型(Dict.dictcode)
     * @return
     */
    public String getCategory() {
        return this.category;
    }
    
    /**
     * 节目包类型(Dict.dictcode)
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 节目包标题的缩写
     * @return
     */
    public String getTitlebrief() {
        return this.titlebrief;
    }
    
    /**
     * 节目包标题的缩写
     * @param titlebrief
     */
    public void setTitlebrief(String titlebrief) {
        this.titlebrief = titlebrief;
    }

    /**
     * 电视剧或系列片的名称
     * @return
     */
    public String getEpicodename() {
        return this.epicodename;
    }
    
    /**
     * 电视剧或系列片的名称
     * @param epicodename
     */
    public void setEpicodename(String epicodename) {
        this.epicodename = epicodename;
    }

    /**
     * 电视剧或系列片的序号(分集号)
     * @return
     */
    public String getEpicodeid() {
        return this.epicodeid;
    }
    
    /**
     * 电视剧或系列片的序号(分集号)
     * @param epicodeid
     */
    public void setEpicodeid(String epicodeid) {
        this.epicodeid = epicodeid;
    }

    /**
     * 生成节目预告时存放是否生成节目预告标识 ( ProgListDetail.scheduleTag )
     * @return
     */
    public String getLengthtc() {
        return this.lengthtc;
    }
    
    /**
     * 生成节目预告时存放是否生成节目预告标识 ( ProgListDetail.scheduleTag )
     * @param lengthtc
     */
    public void setLengthtc(String lengthtc) {
        this.lengthtc = lengthtc;
    }

    /**
     * 生成节目包JS时存放栏目ID ( PortalColumn.columnclassid )
     * @return
     */
    public String getLicensingWindowStart() {
        return this.licensingWindowStart;
    }
    
    /**
     * 生成节目包JS时存放栏目ID ( PortalColumn.columnclassid )
     * @param licensingWindowStart
     */
    public void setLicensingWindowStart(String licensingWindowStart) {
        this.licensingWindowStart = licensingWindowStart;
    }

    /**
     * 生成节目包JS时存放栏目名称 ( PortalColumn.columnname )
     * @return
     */
    public String getLicensingWindowEnd() {
        return this.licensingWindowEnd;
    }
    
    /**
     * 生成节目包JS时存放栏目名称 ( PortalColumn.columnname )
     * @param licensingWindowEnd
     */
    public void setLicensingWindowEnd(String licensingWindowEnd) {
        this.licensingWindowEnd = licensingWindowEnd;
    }

    /**
     * 节目包类型[V:视频节目包; R:富媒体节目包;]
     * @return
     */
    public String getProgtype() {
        return this.progtype;
    }
    
    /**
     * 节目包类型[V:视频节目包; R:富媒体节目包;]
     * @param progtype
     */
    public void setProgtype(String progtype) {
        this.progtype = progtype;
    }

    /**
     * 电视剧或系列片的总集数
     * @return
     */
    public String getEpicodenumber() {
        return this.epicodenumber;
    }
    
    /**
     * 电视剧或系列片的总集数
     * @param epicodenumber
     */
    public void setEpicodenumber(String epicodenumber) {
        this.epicodenumber = epicodenumber;
    }

    public String getSubtitlelanguage() {
        return this.subtitlelanguage;
    }
    
    public void setSubtitlelanguage(String subtitlelanguage) {
        this.subtitlelanguage = subtitlelanguage;
    }

    /**
     * 影片类型
     * @return
     */
    public String getAudiolanguage() {
        return this.audiolanguage;
    }
    
    /**
     * 影片类型
     * @param audiolanguage
     */
    public void setAudiolanguage(String audiolanguage) {
        this.audiolanguage = audiolanguage;
    }

    /**
     * 导演
     * @return
     */
    public String getDirector() {
        return this.director;
    }
    
    /**
     * 导演
     * @param director
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * 主演
     * @return
     */
    public String getActors() {
        return this.actors;
    }
    
    /**
     * 主演
     * @param actors
     */
    public void setActors(String actors) {
        this.actors = actors;
    }

    /**
     * 生成节目包JS时存放栏目Code ( PortalColumn.defcatcode )
     * @return
     */
    public String getShootdate() {
        return this.shootdate;
    }
    
    /**
     * 生成节目包JS时存放栏目Code ( PortalColumn.defcatcode )
     * @param shootdate
     */
    public void setShootdate(String shootdate) {
        this.shootdate = shootdate;
    }

    /**
     * 生成节目包JS时存放产品信息ID ( ProgListDetail.productInfoID )
     * @return
     */
    public String getIssuedate() {
        return this.issuedate;
    }
    
    /**
     * 生成节目包JS时存放产品信息ID ( ProgListDetail.productInfoID )
     * @param issuedate
     */
    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    /**
     * 国家/地区
     * @return
     */
    public String getCountrydist() {
        return this.countrydist;
    }
    
    /**
     * 国家/地区
     * @param countrydist
     */
    public void setCountrydist(String countrydist) {
        this.countrydist = countrydist;
    }

    /**
     * 版权开始日期
     * @return
     */
    public Date getSubscriberstime() {
        return this.subscriberstime;
    }
    
    /**
     * 版权开始日期
     * @param subscriberstime
     */
    public void setSubscriberstime(Date subscriberstime) {
        this.subscriberstime = subscriberstime;
    }

    /**
     * 版权结束日期
     * @return
     */
    public Date getSubscriberetime() {
        return this.subscriberetime;
    }
    
    /**
     * 版权结束日期
     * @param subscriberetime
     */
    public void setSubscriberetime(Date subscriberetime) {
        this.subscriberetime = subscriberetime;
    }

    /**
     * 节目包描述XML
     * @return
     */
    public String getPpxml() {
        return this.ppxml;
    }
    
    /**
     * 节目包描述XML
     * @param ppxml
     */
    public void setPpxml(String ppxml) {
        this.ppxml = ppxml;
    }

    /**
     * 操作人员ID
     * @return
     */
    public String getInputmanid() {
        return this.inputmanid;
    }
    
    /**
     * 操作人员ID
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

    /**
     * 用于统计节目包文件大小
     * @return
     */
    public Long getFilesizehi() {
        return this.filesizehi;
    }
    
    /**
     * 用于统计节目包文件大小
     * @param filesizehi
     */
    public void setFilesizehi(Long filesizehi) {
        this.filesizehi = filesizehi;
    }

    /**
     * V1.3强制保留有效期 存放Timestamp
     * @return
     */
    public Long getFilesizelow() {
        return this.filesizelow;
    }
    
    /**
     * V1.3强制保留有效期 存放Timestamp
     * @param filesizelow
     */
    public void setFilesizelow(Long filesizelow) {
        this.filesizelow = filesizelow;
    }

    public String getSumfilesize() {
        return this.sumfilesize;
    }
    
    public void setSumfilesize(String sumfilesize) {
        this.sumfilesize = sumfilesize;
    }

    /**
     * 生成节目预告时存放产品信息ID ( ProgListDetail.productInfoID )
     * @return
     */
    public String getUpdatemanid() {
        return this.updatemanid;
    }
    
    /**
     * 生成节目预告时存放产品信息ID ( ProgListDetail.productInfoID )
     * @param updatemanid
     */
    public void setUpdatemanid(String updatemanid) {
        this.updatemanid = updatemanid;
    }

    /**
     * 节目包最后修改时间
     * @return
     */
    public Date getUpdatetime() {
        return this.updatetime;
    }
    
    /**
     * 节目包最后修改时间
     * @param updatetime
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 节目包存储等级状态 {北京:[-1:未导入; 0:导入区; 1:缓存库; 2:加扰库; 3:播发库;], 上海:[1:缓存库; 9:北京缓存库;]}
     * @return
     */
	public Long getState() {
		return state;
	}

	/**
	 * 节目包存储等级状态 {北京:[-1:未导入; 0:导入区; 1:缓存库; 2:加扰库; 3:播发库;], 上海:[1:缓存库; 9:北京缓存库;]}
	 * @param state
	 */
	public void setState(Long state) {
		this.state = state;
	}

	/**
	 * 节目包使用状态[0:未处理; 1:处理中; 8:失败; 9:成功;]
	 * @return
	 */
	public Long getDealstate() {
		return dealstate;
	}

	/**
	 * 节目包使用状态[0:未处理; 1:处理中; 8:失败; 9:成功;]
	 * @param dealstate
	 */
	public void setDealstate(Long dealstate) {
		this.dealstate = dealstate;
	}
   
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProgPackage) {
			ProgPackage progPackage = (ProgPackage) obj;
			return progPackage.getProductid().equals(this.productid);
		} else {
			return false;
		}
	}

	/**
	 * 导入标识[-1: 未导入; 0: 迁移中; 1: 已导入]
	 * @return
	 */
	public Long getImportFlag() {
		return importFlag;
	}
	
	/**
	 * 导入标识[-1: 未导入; 0: 迁移中; 1: 已导入]
	 * @param importFlag
	 */
	public void setImportFlag(Long importFlag) {
		this.importFlag = importFlag;
	}
	
	/**
	 * 主文件标识[-1: 未导入; 1: 已导入; 2: 内容加密中; 3: 已内容加密]
	 * @return
	 */
	public Long getMainFileFlag() {
		return mainFileFlag;
	}
	
	/**
	 * 主文件标识[-1: 未导入; 1: 已导入; 2: 内容加密中; 3: 已内容加密]
	 * @param mainFIleFlag
	 */
	public void setMainFileFlag(Long mainFileFlag) {
		this.mainFileFlag = mainFileFlag;
	}
	
	/**
	 * 品牌Code
	 * @return
	 */
	public String getSiteCode() {
		return siteCode;
	}
	
	/**
	 * 品牌Code
	 * @param siteCode
	 */
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
}