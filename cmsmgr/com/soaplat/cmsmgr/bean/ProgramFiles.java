/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;

/**
 * Title 		: 节目文件类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月23日 11时1分
 */
public class ProgramFiles  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields    
	private String	progfileid;			// 主键ID
    private String	figlobalid;			// ---暂未使用
    private String	programid;			// 节目(ProgramInfo)ID
    private String	filetypeid;			// 文件类型编号[H264:视频H264文件; RMZIP:富媒体压缩文件; XML:XML文档; PNG:海报; MODZIP:模版文件; PTLZIP:PORTAL文件; WGZM:外挂字幕;]
    private String	filecode;			// 文件Code [H264:视频; PPXML:节目包XML文件; PROGXML:节目XML文件; PNG1:海报1; WGZMCHS:外挂字幕中文(简体); WGZMCHT:外挂字幕中文(繁体) WGZMENG:外挂字幕英文; RMZIP:富媒体压缩包文件; MODZIP:模版文件; PTLZIP:PORTAL文件; MIGXML:迁移单文件; BRDXML:播发单文件; CLXML:编单XML文件; JS:Portal JS文件; IMPORT:数据导入;]
    private Date	runtime;			// ---暂未使用	节目时长
    private String	filedes;			// ---暂未使用	文件描述
    private String	filename;			// 文件名	
    private String	audiotype;			// ---暂未使用	音频格式
    private String	screeformat;		// ---暂未使用	屏幕显示格式
    private String	hdcontent;			// ---暂未使用	是否为高清
    private String	subtitlelanguage;	// ---暂未使用	字幕语言
    private String	dubbedlanguage;		// ---暂未使用	配音主意
    private Long	filesizehi;			// ---暂未使用	文件大小高位
    private Long	filesizelow;		// ---暂未使用	文件大小低们
    private String	contentfilesize;	// 文件大小
    private String	contentchecksum;	// ---暂未使用	MD5字符
    private String	imageratio;			// ---暂未使用	分辨率
    private String	encryption;			// ---暂未使用	是否加密[Y:是, N:否]
    private String	copyprotection;		// ---暂未使用	是否拷贝保护 
    private String	delmanid;			// ---暂未使用	删除人
    private Date	deldate;			// ---暂未使用	删除日期
    private String	progmanager;		// ---暂未使用	节目责编
    private String	progbak;			// ---暂未使用	节目是否备份[Y:是, N:否]
    private Long	progbkmonth;		// ---暂未使用	节目保留天数
    private Long	progrank;			// 主体文件标识 [0: 不是; 1: 是]
    private String	instoragemanid;		// ---暂未使用	节目入库人
	private Date	instoragedate;		// ---暂未使用	入库时间
	private String	nearfilepath;		// ---暂未使用	缓存库路径
	private Long	nearstatus;			// ---暂未使用	是否在线库状态[0: 不在, 1: 在]
	private Long	offlinestatus;		// ---暂未使用	是否在离线库状态[0: 不在, 1: 在]
	private Long	onairstatus;		// ---暂未使用	是否在播出库状态[0: 不在, 1: 在]
	private Long	ext1;				// ---暂未使用	EXT1扩展状态
	private Long	ext2;				// ---暂未使用	EXT2扩展状态
	private String	assesssugt;			// ---暂未使用	下一活动处理要求
	private Date	plantime;			// ---暂未使用	当前计划完成时间
	private String	opmanid;			// ---暂未使用	当前操作人
	private Long	progstatus;			// 实体文件存在标识 [1: 存在, 0: 不存在]
	private Long	encodestatus;		// 内容加密标识[0: 未加密, 1: 已加密]
	private String	contentId;			// ContentId
	private Long	indexstatus;		// ---暂未使用	编目状态[0: 不在, 1: 在]
	private String	processinstid;		// 原始文件名 注: 长度包含扩展名必须限制60字符长度
	private String	activityinstid;		// ---暂未使用	活动实例ID
	private String	activityinstname;	// ---暂未使用	活动实例名称
	private String	workitemid;			// ---暂未使用	工作项ID
	private String	inputmanid;			// 操作人员ID
	private Date	inputtime;			// 录入时间
	private String	remark;				// 备注
	private String	storageid;			// 存储体(AmsStorage)ID
	private String	storagedirid;		// 存储体目录(AmsStorageDir)ID
	private Date	fileDate;			// 文件版本(文件日期)
	private String	updateManId;		// 修改人员ID
	private Date	updateTime;			// 最后一次修改日期

    // Constructors

    /** default constructor */
    public ProgramFiles() {
    	this.progrank = (long)0;
    }

	/** minimal constructor */
    public ProgramFiles(String progfileid) {
        this.progfileid = progfileid;
    }
    
    /**
     * 自定义构造函数, 由于该对象主键生成策略为手动, 故构造函数中加入主键ID参数
     * @param progfileid 主键ID
     * @param programid	节目(ProgramInfo)ID
     * @param filetypeid 文件类型编号[H264:视频H264文件; RMZIP:富媒体压缩文件; XML:XML文档; PNG:海报; MODZIP:模版文件; PTLZIP:PORTAL文件; WGZM:外挂字幕;]
     * @param filename 文件名
     * @param contentfilesize 文件大小
     * @param progrank 是否是主要(视频)文件 [1:是; 0:不是] 
     * @param inputmanid 操作人员()ID
     * @param inputtime 录入时间
     * @param remark 备注
     * @param fileCode 文件Code [H264:视频; PPXML:节目包XML文件; PROGXML:节目XML文件; PNG1:海报1; WGZMCHS:外挂字幕中文(简体); WGZMCHT:外挂字幕中文(繁体) WGZMENG:外挂字幕英文; RMZIP:富媒体压缩包文件; MODZIP:模版文件; PTLZIP:PORTAL文件; MIGXML:迁移单文件; BRDXML:播发单文件; CLXML:编单XML文件; JS:Portal JS文件; IMPORT:数据导入;]
     */
	public ProgramFiles(String progfileid, String programid, String filetypeid, String filename,
			String contentfilesize, long progrank, String inputmanid,
			Date inputtime, String remark, String fileCode) {
		this.progfileid = progfileid;
		this.programid = programid;
		this.filetypeid = filetypeid;
		this.filename = filename;
		this.contentfilesize = contentfilesize;
		this.progrank = progrank;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
		this.filecode = fileCode;
	}

	/** full constructor */
	public ProgramFiles(String progfileid, String figlobalid, String programid,
			String filetypeid, Date runtime, String filedes, String filename,
			String audiotype, String screeformat, String hdcontent,
			String subtitlelanguage, String dubbedlanguage, Long filesizehi,
			Long filesizelow, String contentfilesize, String contentchecksum,
			String imageratio, String encryption, String copyprotection,
			String delmanid, Date deldate, String progmanager, String progbak,
			Long progbkmonth, Long progrank, String instoragemanid,
			Date instoragedate, String nearfilepath, Long nearstatus,
			Long offlinestatus, Long onairstatus, Long ext1, Long ext2,
			String assesssugt, Date plantime, String opmanid, Long progstatus,
			Long encodestatus, Long indexstatus, String processinstid,
			String activityinstid, String activityinstname, String workitemid,
			String inputmanid, Date inputtime, String remark, String storageid,
			String storagedirid, String filecode) {
		this.progfileid = progfileid;
		this.figlobalid = figlobalid;
		this.programid = programid;
		this.filetypeid = filetypeid;
		this.runtime = runtime;
		this.filedes = filedes;
		this.filename = filename;
		this.audiotype = audiotype;
		this.screeformat = screeformat;
		this.hdcontent = hdcontent;
		this.subtitlelanguage = subtitlelanguage;
		this.dubbedlanguage = dubbedlanguage;
		this.filesizehi = filesizehi;
		this.filesizelow = filesizelow;
		this.contentfilesize = contentfilesize;
		this.contentchecksum = contentchecksum;
		this.imageratio = imageratio;
		this.encryption = encryption;
		this.copyprotection = copyprotection;
		this.delmanid = delmanid;
		this.deldate = deldate;
		this.progmanager = progmanager;
		this.progbak = progbak;
		this.progbkmonth = progbkmonth;
		this.progrank = progrank;
		this.instoragemanid = instoragemanid;
		this.instoragedate = instoragedate;
		this.nearfilepath = nearfilepath;
		this.nearstatus = nearstatus;
		this.offlinestatus = offlinestatus;
		this.onairstatus = onairstatus;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.assesssugt = assesssugt;
		this.plantime = plantime;
		this.opmanid = opmanid;
		this.progstatus = progstatus;
		this.encodestatus = encodestatus;
		this.indexstatus = indexstatus;
		this.processinstid = processinstid;
		this.activityinstid = activityinstid;
		this.activityinstname = activityinstname;
		this.workitemid = workitemid;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
		this.storageid = storageid;
		this.storagedirid = storagedirid;
		this.filecode = filecode;
	}
   
    // Property accessors

	/**
	 * 主键ID
	 */
    public String getProgfileid() {
        return this.progfileid;
    }
    
    /**
     * 主键ID
     * @param progfileid
     */
    public void setProgfileid(String progfileid) {
        this.progfileid = progfileid;
    }

    public String getFiglobalid() {
        return this.figlobalid;
    }
    
    public void setFiglobalid(String figlobalid) {
        this.figlobalid = figlobalid;
    }

    /**
     * 节目(ProgramInfo)ID
     * @return
     */
    public String getProgramid() {
        return this.programid;
    }
    
    /**
     * 节目(ProgramInfo)ID
     * @param programid
     */
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    /**
     * 文件类型编号[H264:视频H264文件; RMZIP:富媒体压缩文件; XML:XML文档; PNG:海报; MODZIP:模版文件; PTLZIP:PORTAL文件; WGZM:外挂字幕;]
     * @return
     */
    public String getFiletypeid() {
        return this.filetypeid;
    }
    
    /**
     * 文件类型编号[H264:视频H264文件; RMZIP:富媒体压缩文件; XML:XML文档; PNG:海报; MODZIP:模版文件; PTLZIP:PORTAL文件; WGZM:外挂字幕;]
     * @param filetypeid
     */
    public void setFiletypeid(String filetypeid) {
        this.filetypeid = filetypeid;
    }

    public Date getRuntime() {
        return this.runtime;
    }
    
    public void setRuntime(Date runtime) {
        this.runtime = runtime;
    }

    public String getFiledes() {
        return this.filedes;
    }
    
    public void setFiledes(String filedes) {
        this.filedes = filedes;
    }

    /**
     * 文件名
     * @return
     */
    public String getFilename() {
        return this.filename;
    }
    
    /**
     * 文件名
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAudiotype() {
        return this.audiotype;
    }
    
    public void setAudiotype(String audiotype) {
        this.audiotype = audiotype;
    }

    public String getScreeformat() {
        return this.screeformat;
    }
    
    public void setScreeformat(String screeformat) {
        this.screeformat = screeformat;
    }

    public String getHdcontent() {
        return this.hdcontent;
    }
    
    public void setHdcontent(String hdcontent) {
        this.hdcontent = hdcontent;
    }

    public String getSubtitlelanguage() {
        return this.subtitlelanguage;
    }
    
    public void setSubtitlelanguage(String subtitlelanguage) {
        this.subtitlelanguage = subtitlelanguage;
    }

    public String getDubbedlanguage() {
        return this.dubbedlanguage;
    }
    
    public void setDubbedlanguage(String dubbedlanguage) {
        this.dubbedlanguage = dubbedlanguage;
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

    /**
     * 文件大小
     * @return
     */
    public String getContentfilesize() {
        return this.contentfilesize;
    }
    
    /**
     * 文件大小
     * @param contentfilesize
     */
    public void setContentfilesize(String contentfilesize) {
        this.contentfilesize = contentfilesize;
    }

    public String getContentchecksum() {
        return this.contentchecksum;
    }
    
    public void setContentchecksum(String contentchecksum) {
        this.contentchecksum = contentchecksum;
    }

    public String getImageratio() {
        return this.imageratio;
    }
    
    public void setImageratio(String imageratio) {
        this.imageratio = imageratio;
    }

    public String getEncryption() {
        return this.encryption;
    }
    
    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getCopyprotection() {
        return this.copyprotection;
    }
    
    public void setCopyprotection(String copyprotection) {
        this.copyprotection = copyprotection;
    }

    public String getDelmanid() {
        return this.delmanid;
    }
    
    public void setDelmanid(String delmanid) {
        this.delmanid = delmanid;
    }

    public Date getDeldate() {
        return this.deldate;
    }
    
    public void setDeldate(Date deldate) {
        this.deldate = deldate;
    }

    public String getProgmanager() {
        return this.progmanager;
    }
    
    public void setProgmanager(String progmanager) {
        this.progmanager = progmanager;
    }

    public String getProgbak() {
        return this.progbak;
    }
    
    public void setProgbak(String progbak) {
        this.progbak = progbak;
    }

    public Long getProgbkmonth() {
        return this.progbkmonth;
    }
    
    public void setProgbkmonth(Long progbkmonth) {
        this.progbkmonth = progbkmonth;
    }

    /**
     * 是否为主要(视频)文件 [0: 不是; 1: 是]
     * @return
     */
    public Long getProgrank() {
        return this.progrank;
    }
    
    /**
     * 是否为主要(视频)文件 [0: 不是; 1: 是]
     * @param progrank
     */
    public void setProgrank(Long progrank) {
        this.progrank = progrank;
    }

    public String getInstoragemanid() {
        return this.instoragemanid;
    }
    
    public void setInstoragemanid(String instoragemanid) {
        this.instoragemanid = instoragemanid;
    }

    public Date getInstoragedate() {
        return this.instoragedate;
    }
    
    public void setInstoragedate(Date instoragedate) {
        this.instoragedate = instoragedate;
    }

    public String getNearfilepath() {
        return this.nearfilepath;
    }
    
    public void setNearfilepath(String nearfilepath) {
        this.nearfilepath = nearfilepath;
    }

    public Long getNearstatus() {
        return this.nearstatus;
    }
    
    public void setNearstatus(Long nearstatus) {
        this.nearstatus = nearstatus;
    }

    public Long getOfflinestatus() {
        return this.offlinestatus;
    }
    
    public void setOfflinestatus(Long offlinestatus) {
        this.offlinestatus = offlinestatus;
    }

    public Long getOnairstatus() {
        return this.onairstatus;
    }
    
    public void setOnairstatus(Long onairstatus) {
        this.onairstatus = onairstatus;
    }

    public Long getExt1() {
        return this.ext1;
    }
    
    public void setExt1(Long ext1) {
        this.ext1 = ext1;
    }

    public Long getExt2() {
        return this.ext2;
    }
    
    public void setExt2(Long ext2) {
        this.ext2 = ext2;
    }

    public String getAssesssugt() {
        return this.assesssugt;
    }
    
    public void setAssesssugt(String assesssugt) {
        this.assesssugt = assesssugt;
    }

    public Date getPlantime() {
        return this.plantime;
    }
    
    public void setPlantime(Date plantime) {
        this.plantime = plantime;
    }

    public String getOpmanid() {
        return this.opmanid;
    }
    
    public void setOpmanid(String opmanid) {
        this.opmanid = opmanid;
    }

    /**
     * 实体文件存在标识 [1: 存在, 0: 不存在]
     * @return
     */
    public Long getProgstatus() {
        return this.progstatus;
    }
    
    /**
     * 实体文件存在标识 [1: 存在, 0: 不存在]
     * @param progstatus
     */
    public void setProgstatus(Long progstatus) {
        this.progstatus = progstatus;
    }

    /**
     * 内容加密标识[0: 未加密, 1: 已加密]
     * @return
     */
    public Long getEncodestatus() {
        return this.encodestatus;
    }
    
    /**
     * 内容加密标识[0: 未加密, 1: 已加密]
     * @param encodestatus
     */
    public void setEncodestatus(Long encodestatus) {
        this.encodestatus = encodestatus;
    }

    public Long getIndexstatus() {
        return this.indexstatus;
    }
    
    public void setIndexstatus(Long indexstatus) {
        this.indexstatus = indexstatus;
    }

    /**
     * 原始文件名 注: 长度包含扩展名必须限制60字符长度
     * @return
     */
    public String getProcessinstid() {
        return this.processinstid;
    }
    
    /**
     * 原始文件名 注: 长度包含扩展名必须限制60字符长度
     * @param processinstid
     */
    public void setProcessinstid(String processinstid) {
        this.processinstid = processinstid;
    }

    public String getActivityinstid() {
        return this.activityinstid;
    }
    
    public void setActivityinstid(String activityinstid) {
        this.activityinstid = activityinstid;
    }

    public String getActivityinstname() {
        return this.activityinstname;
    }
    
    public void setActivityinstname(String activityinstname) {
        this.activityinstname = activityinstname;
    }

    public String getWorkitemid() {
        return this.workitemid;
    }
    
    public void setWorkitemid(String workitemid) {
        this.workitemid = workitemid;
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

    /**
     * 存储体(AmsStorage)ID
     * @return
     */
    public String getStorageid() {
        return this.storageid;
    }
    
    /**
     * 存储体(AmsStorage)ID
     * @param storageid
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid;
    }

    /**
     * 存储体目录(AmsStorageDir)ID
     * @return
     */
    public String getStoragedirid() {
        return this.storagedirid;
    }
    
    /**
     * 存储体目录(AmsStorageDir)ID
     * @param storagedirid
     */
    public void setStoragedirid(String storagedirid) {
        this.storagedirid = storagedirid;
    }

    /**
     * 文件Code [H264:视频; PPXML:节目包XML文件; PROGXML:节目XML文件; PNG1:海报1; WGZMCHS:外挂字幕中文(简体); WGZMCHT:外挂字幕中文(繁体) WGZMENG:外挂字幕英文; RMZIP:富媒体压缩包文件; MODZIP:模版文件; PTLZIP:PORTAL文件; MIGXML:迁移单文件; BRDXML:播发单文件; CLXML:编单XML文件; JS:Portal JS文件; IMPORT:数据导入;]
     * @return
     */
    public String getFilecode() {
        return this.filecode;
    }
    
    /**
     * 文件Code [H264:视频; PPXML:节目包XML文件; PROGXML:节目XML文件; PNG1:海报1; WGZMCHS:外挂字幕中文(简体); WGZMCHT:外挂字幕中文(繁体) WGZMENG:外挂字幕英文; RMZIP:富媒体压缩包文件; MODZIP:模版文件; PTLZIP:PORTAL文件; MIGXML:迁移单文件; BRDXML:播发单文件; CLXML:编单XML文件; JS:Portal JS文件; IMPORT:数据导入;]
     * @param filecode
     */
    public void setFilecode(String filecode) {
        this.filecode = filecode;
    }
   
    /**
     * ContentId
     * @return
     */
    public String getContentId() {
		return contentId;
	}
    
    /**
     * ContentId
     * @param contentId
     */
    public void setContentId(String contentId) {
		this.contentId = contentId;
	}

    /**
     * 文件版本(文件日期)
     * @return
     */
    public Date getFileDate() {
		return fileDate;
	}

    /**
     * 文件版本(文件日期)
     * @param fileDate
     */
    public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

    /**
     * 修改人员ID
     * @return
     */
    public String getUpdateManId() {
		return updateManId;
	}
    
    /**
     * 修改人员ID
     * @param updateManId
     */
    public void setUpdateManId(String updateManId) {
		this.updateManId = updateManId;
	}
    
    /**
     * 最后一次修改日期
     * @return
     */
    public Date getUpdateTime() {
		return updateTime;
	}
    
    /**
     * 最后一次修改日期
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}