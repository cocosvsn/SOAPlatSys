/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;

/**
 * Title : 栏目类 Description : Company : A-one E-mail : cocosvsn@yahoo.com.cn,
 * b_huang@sbl.sh.cn
 * 
 * @author : Bunco
 * @version : 1.0
 * @date ：2010年8月27日 9时46分
 */
public class PortalColumn implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Fields

	private String columnclassid;	// 主键ID
	private String columnname;		// 栏目名称
	private String defcatcode;		// 栏目Code
	private String siteCode;		// 品牌Code
	private String isleaf;			// 是否为叶子栏目
	private Long defcatlevel;		// 栏目层次
	private String rootid;			// 根栏目Code
	private String parentsid;		// 父级栏目Code
	private Long displayorder;		// 显示排序
	private String defcatseq;		// 栏目路径序列
	private String scheduleTag;		// 是否生成节目预告
	private Long validFlag;			// 有效标识[0:启用; 1:无效(删除); 2:停播;]
	private String remark;			// 备注
	private Long ispublish;			// 是否生成发布文件
	private Date updatedate;		// 更新日期
	private Long archivedays;		// 富媒体或视频节目[1: 富媒体, 0: 视频]
	private String inputmanid;		// 操作人员(Operator)ID
	private Date inputtime;			// 信息录入时间
	private Date validFrom;			// 栏目生效日期, 必填
	private Long countonepage;		// 子栏目个数限制
	private Long isdomainsub;		// 子栏目级数限制
	private Long isallchange;		// 栏目维护显示颜色(2:黑色)

	private String siteid;			// --- 暂未使用 // 站点(CmsSite)ID
	private Date validTo;			// (不使用)下线日期
	private String publishfilename;	// 栏目生成文件文件名
	private String parentdir;		// 栏目存放目录
	private String domainname;
	private Long countnumb;			// 终端生成栏目顺序
	private Long styleid;			// 栏目样式编号[1: 视频类, 2: 图书类, 3: 杂志类, 4: 报纸类]

	private String focusImgName;	// 默认皮肤 焦点图片名称
	private String blurImgName;		// 默认皮肤 非焦点图片名称
	private String currentdir;		// V1.3 不使用 第二套皮肤 焦点图片名称
	private String contentmodeid;	// V1.3 不使用 第二套皮肤 非焦点图片名称
	/**
	 * V1.3修改为 强显标识  
	 * 正常【0】：portal正常规则显示的栏目；	
	 * 强显【1】：portal强制显示的栏目，不论栏目下是否有节目都显示；	
	 * 不显【-1】：portal不显示的栏目，仅用于传递数据的栏目，如首页片花广告栏目。 
	 */
	private String covermodeid;		// V1.3 不使用 第三套皮肤 焦点图片名称
	private String listmodeid;		// V1.3 不使用 第三套皮肤 非焦点图片名称

	// Constructors

	/** default constructor */
	public PortalColumn() {
	}

	/** minimal constructor */
	public PortalColumn(String columnclassid) {
		this.columnclassid = columnclassid;
	}

	public PortalColumn(String columnclassid, String columnname,
			String siteCode, String remark) {
		super();
		this.columnclassid = columnclassid;
		this.columnname = columnname;
		this.siteCode = siteCode;
		this.remark = remark;
	}

	public PortalColumn(String columnclassid, String columnname,
			String siteCode, String remark, String pubName, Long archiveDays) {
		super();
		this.columnclassid = columnclassid;
		this.columnname = columnname;
		this.siteCode = siteCode;
		this.remark = remark;
		this.publishfilename = pubName;
		this.archivedays = archiveDays;
	}

	/**
	 * 
	 * @param columnclassid
	 * @param columnname
	 * @param defcatcode
	 * @param siteCode
	 * @param isleaf
	 * @param defcatlevel
	 * @param rootid
	 * @param parentsid
	 * @param displayorder
	 * @param defcatseq
	 * @param scheduleTag
	 * @param validFlag			有效标识[0:启用; 1:无效(删除); 2:停播;]
	 * @param remark
	 * @param ispublish
	 * @param updatedate
	 * @param archivedays
	 * @param inputmanid
	 * @param inputtime
	 */
	public PortalColumn(String columnclassid, String columnname,
			String defcatcode, String siteCode, String isleaf,
			Long defcatlevel, String rootid, String parentsid,
			Long displayorder, String defcatseq, String scheduleTag,
			Long validFlag, String remark, Long ispublish, Date updatedate,
			Long archivedays, String inputmanid, Date inputtime) {
		super();
		this.columnclassid = columnclassid;
		this.columnname = columnname;
		this.defcatcode = defcatcode;
		this.siteCode = siteCode;
		this.isleaf = isleaf;
		this.defcatlevel = defcatlevel;
		this.rootid = rootid;
		this.parentsid = parentsid;
		this.displayorder = displayorder;
		this.defcatseq = defcatseq;
		this.scheduleTag = scheduleTag;
		this.validFlag = validFlag;
		this.remark = remark;
		this.ispublish = ispublish;
		this.updatedate = updatedate;
		this.archivedays = archivedays;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
	}

	/**
	 * 
	 * @param columnclassid
	 * @param columnname
	 * @param defcatcode
	 * @param siteCode
	 * @param isleaf
	 * @param defcatlevel
	 * @param rootid
	 * @param parentsid
	 * @param displayorder
	 * @param defcatseq
	 * @param scheduleTag
	 * @param validFlag			有效标识[0:启用; 1:无效(删除); 2:停播;]
	 * @param remark
	 * @param ispublish
	 * @param updatedate
	 * @param archivedays
	 * @param inputmanid
	 * @param inputtime
	 * @param focusImgName
	 * @param blurImgName
	 * @param isSend
	 */
	public PortalColumn(String columnclassid, String columnname,
			String defcatcode, String siteCode, String isleaf,
			Long defcatlevel, String rootid, String parentsid,
			Long displayorder, String defcatseq, String scheduleTag,
			Long validFlag, String remark, Long ispublish, Date updatedate,
			Long archivedays, String inputmanid, Date inputtime,
			String focusImgName, String blurImgName, Long isSend) {
		super();
		this.columnclassid = columnclassid;
		this.columnname = columnname;
		this.defcatcode = defcatcode;
		this.siteCode = siteCode;
		this.isleaf = isleaf;
		this.defcatlevel = defcatlevel;
		this.rootid = rootid;
		this.parentsid = parentsid;
		this.displayorder = displayorder;
		this.defcatseq = defcatseq;
		this.scheduleTag = scheduleTag;
		this.validFlag = validFlag;
		this.remark = remark;
		this.ispublish = ispublish;
		this.updatedate = updatedate;
		this.archivedays = archivedays;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.focusImgName = focusImgName;
		this.blurImgName = blurImgName;
		this.isallchange = isSend;
	}

	// Property accessors
	/**
	 * 主键ID
	 * @return
	 */
	public String getColumnclassid() {
		return this.columnclassid;
	}

	/**
	 * 主键ID
	 * @param columnclassid
	 */
	public void setColumnclassid(String columnclassid) {
		this.columnclassid = columnclassid;
	}

	public String getSiteid() {
		return this.siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	/**
	 * 栏目名称
	 * @return
	 */
	public String getColumnname() {
		return this.columnname;
	}

	/**
	 * 栏目名称
	 * @param columnname
	 */
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	/**
	 * 栏目Code
	 * @return
	 */
	public String getDefcatcode() {
		return this.defcatcode;
	}

	/**
	 * 栏目Code
	 * @param defcatcode
	 */
	public void setDefcatcode(String defcatcode) {
		this.defcatcode = defcatcode;
	}

	/**
	 * 是否为叶子栏目
	 * @return
	 */
	public String getIsleaf() {
		return this.isleaf;
	}

	/**
	 * 是否为叶子栏目
	 * @param isleaf
	 */
	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	/**
	 * 栏目层次
	 * @return
	 */
	public Long getDefcatlevel() {
		return this.defcatlevel;
	}

	/**
	 * 栏目层次
	 * @param defcatlevel
	 */
	public void setDefcatlevel(Long defcatlevel) {
		this.defcatlevel = defcatlevel;
	}

	/**
	 * 根栏目Code
	 * @return
	 */
	public String getRootid() {
		return this.rootid;
	}

	/**
	 * 根栏目Code
	 * @param rootid
	 */
	public void setRootid(String rootid) {
		this.rootid = rootid;
	}

	/**
	 * 父级栏目Code
	 * @return
	 */
	public String getParentsid() {
		return this.parentsid;
	}

	/**
	 * 父级栏目Code
	 * @param parentsid
	 */
	public void setParentsid(String parentsid) {
		this.parentsid = parentsid;
	}

	/**
	 * 显示排序
	 * @return
	 */
	public Long getDisplayorder() {
		return this.displayorder;
	}

	/**
	 * 显示排序
	 * @param displayorder
	 */
	public void setDisplayorder(Long displayorder) {
		this.displayorder = displayorder;
	}

	/**
	 * 栏目路径序列
	 * @return
	 */
	public String getDefcatseq() {
		return this.defcatseq;
	}

	/**
	 * 栏目路径序列
	 * @param defcatseq
	 */
	public void setDefcatseq(String defcatseq) {
		this.defcatseq = defcatseq;
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
	 * 栏目生效日期, 必填
	 * @return
	 */
	public Date getValidFrom() {
		return this.validFrom;
	}

	/**
	 * 栏目生效日期, 必填
	 * @param validFrom
	 */
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * (不使用)下线日期
	 * @return
	 */
	public Date getValidTo() {
		return this.validTo;
	}

	/**
	 * (不使用)下线日期
	 * @param validTo
	 */
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	/**
	 * 有效标识[0:启用; 1:无效(删除); 2:停播;]
	 * @return
	 */
	public Long getValidFlag() {
		return this.validFlag;
	}

	/**
	 * 有效标识[0:启用; 1:无效(删除); 2:停播;]
	 * @param validFlag
	 */
	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
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
	 * 是否生成发布文件
	 * @return
	 */
	public Long getIspublish() {
		return this.ispublish;
	}

	/**
	 * 是否生成发布文件
	 * @param ispublish
	 */
	public void setIspublish(Long ispublish) {
		this.ispublish = ispublish;
	}

	/**
	 * 栏目生成文件文件名
	 * @return
	 */
	public String getPublishfilename() {
		return this.publishfilename;
	}

	/**
	 * 栏目生成文件文件名
	 * @param publishfilename
	 */
	public void setPublishfilename(String publishfilename) {
		this.publishfilename = publishfilename;
	}

	/**
	 * 栏目存放目录
	 * @return
	 */
	public String getParentdir() {
		return this.parentdir;
	}

	/**
	 * 栏目存放目录
	 * @param parentdir
	 */
	public void setParentdir(String parentdir) {
		this.parentdir = parentdir;
	}

	public String getDomainname() {
		return this.domainname;
	}

	public void setDomainname(String domainname) {
		this.domainname = domainname;
	}

	/**
	 * 子栏目级数限制
	 * @return
	 */
	public Long getIsdomainsub() {
		return this.isdomainsub;
	}

	/**
	 * 子栏目级数限制
	 * @param isdomainsub
	 */
	public void setIsdomainsub(Long isdomainsub) {
		this.isdomainsub = isdomainsub;
	}

	/**
	 * 终端生成栏目顺序
	 * @return
	 */
	public Long getCountnumb() {
		return this.countnumb;
	}

	/**
	 * 终端生成栏目顺序
	 * @param countnumb
	 */
	public void setCountnumb(Long countnumb) {
		this.countnumb = countnumb;
	}

	/**
	 * 子栏目个数限制
	 * @return
	 */
	public Long getCountonepage() {
		return this.countonepage;
	}

	/**
	 * 子栏目个数限制
	 * @param countonepage
	 */
	public void setCountonepage(Long countonepage) {
		this.countonepage = countonepage;
	}

	/**
	 * 栏目维护显示颜色(2:黑色)
	 * @return
	 */
	public Long getIsallchange() {
		return this.isallchange;
	}

	/**
	 * 栏目维护显示颜色(2:黑色)
	 * @param isallchange
	 */
	public void setIsallchange(Long isallchange) {
		this.isallchange = isallchange;
	}

	/**
	 * 更新日期
	 * @return
	 */
	public Date getUpdatedate() {
		return this.updatedate;
	}

	/**
	 * 更新日期
	 * @param updatedate
	 */
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	/**
	 * 富媒体或视频节目[1: 富媒体, 0: 视频]
	 * @return
	 */
	public Long getArchivedays() {
		return this.archivedays;
	}

	/**
	 * 富媒体或视频节目[1: 富媒体, 0: 视频]
	 * @param archivedays
	 */
	public void setArchivedays(Long archivedays) {
		this.archivedays = archivedays;
	}

	/**
	 * 栏目样式编号 [1: 视频类, 2: 图书类, 3: 杂志类, 4: 报纸类]
	 * @return
	 */
	public Long getStyleid() {
		return this.styleid;
	}

	/**
	 * 栏目样式编号 [1: 视频类, 2: 图书类, 3: 杂志类, 4: 报纸类]
	 * @param styleid
	 */
	public void setStyleid(Long styleid) {
		this.styleid = styleid;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getScheduleTag() {
		return scheduleTag;
	}

	public void setScheduleTag(String scheduleTag) {
		this.scheduleTag = scheduleTag;
	}
	

	/**
	 * 默认皮肤 焦点图片名称
	 * @return
	 */
	public String getFocusImgName() {
		return focusImgName;
	}

	/**
	 * 默认皮肤 焦点图片名称
	 * @param focusImgName
	 */
	public void setFocusImgName(String focusImgName) {
		this.focusImgName = focusImgName;
	}

	/**
	 * 默认皮肤 非焦点图片名称
	 * @return
	 */
	public String getBlurImgName() {
		return blurImgName;
	}

	/**
	 * 默认皮肤 非焦点图片名称
	 * @param blurImgName
	 */
	public void setBlurImgName(String blurImgName) {
		this.blurImgName = blurImgName;
	}
	
	/**
	 * 第二套皮肤 焦点图片名称
	 * @return
	 */
	public String getCurrentdir() {
		return this.currentdir;
	}

	/**
	 * 第二套皮肤 焦点图片名称
	 * @param currentdir
	 */
	public void setCurrentdir(String currentdir) {
		this.currentdir = currentdir;
	}
	
	/**
	 * V1.3 不使用 第二套皮肤 非焦点图片名称
	 * @return
	 */
	public String getContentmodeid() {
		return this.contentmodeid;
	}

	/**
	 * V1.3 不使用 第二套皮肤 非焦点图片名称
	 * @param contentmodeid
	 */
	public void setContentmodeid(String contentmodeid) {
		this.contentmodeid = contentmodeid;
	}
	
	/**
	 * V1.2 第三套皮肤 焦点图片名称
	 * V1.3 修改为 强显标识  
	 * 正常【0】：portal正常规则显示的栏目；	
	 * 强显【1】：portal强制显示的栏目，不论栏目下是否有节目都显示；	
	 * 不显【-1】：portal不显示的栏目，仅用于传递数据的栏目，如首页片花广告栏目。
	 * @return
	 */
	public String getCovermodeid() {
		return this.covermodeid;
	}

	/**
	 * V1.2 第三套皮肤 焦点图片名称
	 * V1.3 修改为 强显标识  
	 * 正常【0】：portal正常规则显示的栏目；	
	 * 强显【1】：portal强制显示的栏目，不论栏目下是否有节目都显示；	
	 * 不显【-1】：portal不显示的栏目，仅用于传递数据的栏目，如首页片花广告栏目。
	 * @param covermodeid
	 */
	public void setCovermodeid(String covermodeid) {
		this.covermodeid = covermodeid;
	}
	
	/**
	 * V1.3 不使用 第三套皮肤 非焦点图片名称
	 * @return
	 */
	public String getListmodeid() {
		return this.listmodeid;
	}

	/**
	 * V1.3 不使用 第三套皮肤 非焦点图片名称
	 * @param listmodeid
	 */
	public void setListmodeid(String listmodeid) {
		this.listmodeid = listmodeid;
	}
}