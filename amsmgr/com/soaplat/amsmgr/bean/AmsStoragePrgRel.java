/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.amsmgr.bean;

import java.util.Date;

/**
 * Title 		: 文件存储位置表
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月27日 10时6分
 */
public class AmsStoragePrgRel  implements java.io.Serializable {
    // Fields    
	private static final long serialVersionUID = 1L;
	private String relid;				// 主键ID, 序号(20位）
    private String stglobalid;			// 存储体 (AmsStorage) ID
    private String stdirglobalid;		// 存储体目录 (AmsStorageDir) ID
    private String prglobalid;			// 节目包 (ProgPackage) ID
    private String ftypeglobalid;		// 文件类型编号
    private String filename;			// 文件名称
    private Date uploadtime;			// 上传日期
    private String inputmanid;			// 信息录入人
    private Date inputtime;				// 信息录入时间
    private String remark;				// 备注
    private Date filedate;				// *文件更新日期
    private String progfileid;			// *文件编号 (ProgramFiles) ID
    private String filepath;			// *文件路径
    private String isDel;				// 是否为删除状态

    // Constructors
    /** default constructor */
    public AmsStoragePrgRel() { }

	/** minimal constructor */
    public AmsStoragePrgRel(String relid) {
        this.relid = relid;
    }
    
	/** full constructor */
	public AmsStoragePrgRel(String relid, String stglobalid,
			String stdirglobalid, String prglobalid, String ftypeglobalid,
			String filename, Date uploadtime, String inputmanid,
			Date inputtime, String remark, Date filedate, String progfileid,
			String filepath) {
		this.relid = relid;
		this.stglobalid = stglobalid;
		this.stdirglobalid = stdirglobalid;
		this.prglobalid = prglobalid;
		this.ftypeglobalid = ftypeglobalid;
		this.filename = filename;
		this.uploadtime = uploadtime;
		this.inputmanid = inputmanid;
		this.inputtime = inputtime;
		this.remark = remark;
		this.filedate = filedate;
		this.progfileid = progfileid;
		this.filepath = filepath;
	}
   
	// Property accessors
	/**
	 * 主键ID, 序号(20位）
	 * @return
	 */
    public String getRelid() {
        return this.relid;
    }
    
    /**
     * 主键ID, 序号(20位）
     * @param relid
     */
    public void setRelid(String relid) {
        this.relid = relid;
    }

    /**
     * 存储体 (AmsStorage) ID
     * @return
     */
    public String getStglobalid() {
        return this.stglobalid;
    }
    
    /**
     * 存储体 (AmsStorage) ID
     * @param stglobalid
     */
    public void setStglobalid(String stglobalid) {
        this.stglobalid = stglobalid;
    }

    /**
     * 存储体目录 (AmsStorageDir) ID
     * @return
     */
    public String getStdirglobalid() {
        return this.stdirglobalid;
    }
    
    /**
     * 存储体目录 (AmsStorageDir) ID
     * @param stdirglobalid
     */
    public void setStdirglobalid(String stdirglobalid) {
        this.stdirglobalid = stdirglobalid;
    }

    /**
     * 节目包 (ProgPackage) ID
     * @return
     */
    public String getPrglobalid() {
        return this.prglobalid;
    }
    
    /**
     * 节目包 (ProgPackage) ID
     * @param prglobalid
     */
    public void setPrglobalid(String prglobalid) {
        this.prglobalid = prglobalid;
    }

    /**
     * 文件类型编号
     * @return
     */
    public String getFtypeglobalid() {
        return this.ftypeglobalid;
    }
    
    /**
     * 文件类型编号
     * @param ftypeglobalid
     */
    public void setFtypeglobalid(String ftypeglobalid) {
        this.ftypeglobalid = ftypeglobalid;
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
     * 上传日期
     * @return
     */
    public Date getUploadtime() {
        return this.uploadtime;
    }
    
    /**
     * 上传日期
     * @param uploadtime
     */
    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    /**
     * 信息录入人
     * @return
     */
    public String getInputmanid() {
        return this.inputmanid;
    }
    
    /**
     * 信息录入人
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
     * *文件更新日期
     * @return
     */
    public Date getFiledate() {
        return this.filedate;
    }
    
    /**
     * *文件更新日期
     * @param filedate
     */
    public void setFiledate(Date filedate) {
        this.filedate = filedate;
    }

    /**
     * *文件编号 (ProgramFiles) ID
     * @return
     */
    public String getProgfileid() {
        return this.progfileid;
    }
    
    /**
     * *文件编号 (ProgramFiles) ID
     * @param progfileid
     */
    public void setProgfileid(String progfileid) {
        this.progfileid = progfileid;
    }

    /**
     * *文件路径
     * @return
     */
    public String getFilepath() {
        return this.filepath;
    }
    
    /**
     * *文件路径
     * @param filepath
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
   
    /**
     * 是否为删除状态
     * @param isDel
     */
    public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
    
    /**
     * 是否为删除状态
     * @return
     */
    public String getIsDel() {
		return isDel;
	}
}