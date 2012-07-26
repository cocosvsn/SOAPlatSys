/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;

/**
 * Title 		: 节目类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 11时29分
 */
public class ProgramInfo  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields    
	private String programid;				// 主键ID
	private String prglobalid;				// ---暂未使用
	private String title;					// 节目名称(标题)
	private String titlebrief;				// ---暂未使用
	private String epicodename;				// 电视剧或系列片的名称
	private String epicodenumber;			// ---暂未使用
	private String epicodeid;				// ---暂未使用
	private String rating;					// ---暂未使用
	private String description;				// 节目描述
	private Date lengthrecord;				// ---暂未使用
	private String starttc;					// ---暂未使用
	private String endtc;					// ---暂未使用
	private String lengthtc;				// ---暂未使用
	private String closedcapt;				// ---暂未使用
	private String issueyear;				// ---暂未使用
	private String origincountry;			// ---暂未使用
	private String studio;					// ---暂未使用
	private String genre;					// 节目基本分类(Dict.dictcode)
	private String provider;				// ---暂未使用
	private String productype;				// ---暂未使用
	private String progproperty;			// 节目性质
	private String progtype;				// 节目类型
	private String licensingWindowStart;	// ---暂未使用
	private String licensingWindowEnd;		// ---暂未使用
	private Long dsflag;					// 节目状态[-1: 未导入; 0:迁移; 1:迁移失败; 2:新入库; 3:已包装成节目包]
	private String proginfoxml;				// 节目信息XML
	private String inputmanid;				// 操作人员ID
	private Date inputtime;					// 信息录入时间
	private String remark;					// 备注
	private Long styleid;					// 样式(PackStyle)ID


    // Constructors

    /** default constructor */
    public ProgramInfo() {
    }

	/** minimal constructor */
    public ProgramInfo(String programid) {
        this.programid = programid;
    }
    
    public ProgramInfo(String programid, String title, String epicodename, 
    		String description, String genre, String progproperty, 
    		String progtype, Long dsflag, String proginfoxml, String inputmanid, 
    		Date inputtime, String remark, long styleID) {
		this.programid = programid;
		this.title = title;
		this.epicodename = epicodename;
		this.description = description;
		this.genre = genre;
		this.progproperty = progproperty;
		this.progtype = progtype;
		this.dsflag = dsflag;
		this.proginfoxml = proginfoxml;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
		this.styleid = styleID;
	}
    
	/** full constructor */
	public ProgramInfo(String programid, String prglobalid, String title,
			String titlebrief, String epicodename, String epicodenumber,
			String epicodeid, String rating, String description,
			Date lengthrecord, String starttc, String endtc, String lengthtc,
			String closedcapt, String issueyear, String origincountry,
			String studio, String genre, String provider, String productype,
			String progproperty, String progtype, String licensingWindowStart,
			String licensingWindowEnd, Long dsflag, String proginfoxml,
			String inputmanid, Date inputtime, String remark, long styleID) {
		this.programid = programid;
		this.prglobalid = prglobalid;
		this.title = title;
		this.titlebrief = titlebrief;
		this.epicodename = epicodename;
		this.epicodenumber = epicodenumber;
		this.epicodeid = epicodeid;
		this.rating = rating;
		this.description = description;
		this.lengthrecord = lengthrecord;
		this.starttc = starttc;
		this.endtc = endtc;
		this.lengthtc = lengthtc;
		this.closedcapt = closedcapt;
		this.issueyear = issueyear;
		this.origincountry = origincountry;
		this.studio = studio;
		this.genre = genre;
		this.provider = provider;
		this.productype = productype;
		this.progproperty = progproperty;
		this.progtype = progtype;
		this.licensingWindowStart = licensingWindowStart;
		this.licensingWindowEnd = licensingWindowEnd;
		this.dsflag = dsflag;
		this.proginfoxml = proginfoxml;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
		this.styleid = styleID;
	}

   
    // Property accessors
	/**
	 * 主键ID
	 * @return
	 */
    public String getProgramid() {
        return this.programid;
    }
    
    /**
     * 主键ID
     * @param programid
     */
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getPrglobalid() {
        return this.prglobalid;
    }
    
    public void setPrglobalid(String prglobalid) {
        this.prglobalid = prglobalid;
    }

    /**
     * 节目名称(标题)
     * @return
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * 节目名称(正标题名)
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 电影标题的简写
     * @return
     */
    public String getTitlebrief() {
        return this.titlebrief;
    }
    
    public void setTitlebrief(String titlebrief) {
        this.titlebrief = titlebrief;
    }

    public String getEpicodename() {
        return this.epicodename;
    }
    
    public void setEpicodename(String epicodename) {
        this.epicodename = epicodename;
    }

    public String getEpicodenumber() {
        return this.epicodenumber;
    }
    
    public void setEpicodenumber(String epicodenumber) {
        this.epicodenumber = epicodenumber;
    }

    public String getEpicodeid() {
        return this.epicodeid;
    }
    
    public void setEpicodeid(String epicodeid) {
        this.epicodeid = epicodeid;
    }

    public String getRating() {
        return this.rating;
    }
    
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * 节目描述
     * @return
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * 节目描述
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLengthrecord() {
        return this.lengthrecord;
    }
    
    public void setLengthrecord(Date lengthrecord) {
        this.lengthrecord = lengthrecord;
    }

    public String getStarttc() {
        return this.starttc;
    }
    
    public void setStarttc(String starttc) {
        this.starttc = starttc;
    }

    public String getEndtc() {
        return this.endtc;
    }
    
    public void setEndtc(String endtc) {
        this.endtc = endtc;
    }

    public String getLengthtc() {
        return this.lengthtc;
    }
    
    public void setLengthtc(String lengthtc) {
        this.lengthtc = lengthtc;
    }

    public String getClosedcapt() {
        return this.closedcapt;
    }
    
    public void setClosedcapt(String closedcapt) {
        this.closedcapt = closedcapt;
    }

    public String getIssueyear() {
        return this.issueyear;
    }
    
    public void setIssueyear(String issueyear) {
        this.issueyear = issueyear;
    }

    public String getOrigincountry() {
        return this.origincountry;
    }
    
    public void setOrigincountry(String origincountry) {
        this.origincountry = origincountry;
    }

    public String getStudio() {
        return this.studio;
    }
    
    public void setStudio(String studio) {
        this.studio = studio;
    }

    /**
     * 节目基本分类(Dict.dictcode)
     * @return
     */
    public String getGenre() {
        return this.genre;
    }
    
    /**
     * 节目基本分类(Dict.dictcode)
     * @param genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getProvider() {
        return this.provider;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProductype() {
        return this.productype;
    }
    
    public void setProductype(String productype) {
        this.productype = productype;
    }

    /**
     * 节目性质
     * @return
     */
    public String getProgproperty() {
        return this.progproperty;
    }
    
    /**
     * 节目性质
     * @param progproperty
     */
    public void setProgproperty(String progproperty) {
        this.progproperty = progproperty;
    }

    /**
     * 节目类型
     * @return
     */
    public String getProgtype() {
        return this.progtype;
    }
    
    /**
     * 节目类型
     * @param progtype
     */
    public void setProgtype(String progtype) {
        this.progtype = progtype;
    }

    public String getLicensingWindowStart() {
        return this.licensingWindowStart;
    }
    
    public void setLicensingWindowStart(String licensingWindowStart) {
        this.licensingWindowStart = licensingWindowStart;
    }

    public String getLicensingWindowEnd() {
        return this.licensingWindowEnd;
    }
    
    public void setLicensingWindowEnd(String licensingWindowEnd) {
        this.licensingWindowEnd = licensingWindowEnd;
    }

    /**
     * 节目状态[-1: 未导入; 0:迁移; 1:迁移失败; 2:新入库; 3:已包装成节目包]
     * @return
     */
    public Long getDsflag() {
        return this.dsflag;
    }
    
    /**
     * 节目状态[-1: 未导入; 0:迁移; 1:迁移失败; 2:新入库; 3:已包装成节目包]
     * @param dsflag
     */
    public void setDsflag(Long dsflag) {
        this.dsflag = dsflag;
    }

    /**
     * 节目信息XML
     * @return
     */
    public String getProginfoxml() {
        return this.proginfoxml;
    }
    
    /**
     * 节目信息XML
     * @param proginfoxml
     */
    public void setProginfoxml(String proginfoxml) {
        this.proginfoxml = proginfoxml;
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
     * 样式()ID
     * @return
     */
	public Long getStyleid() {
		return styleid;
	}

	/**
	 * 样式()ID
	 * @param styleid
	 */
	public void setStyleid(Long styleid) {
		this.styleid = styleid;
	}
}