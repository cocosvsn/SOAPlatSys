/**
* copyright (c) by A-one 2010
*/
package com.soaplat.amsmgr.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Title 		: 文件存放位置备份类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-10 下午12:44:48
 */
public class AmsStoragePrgRelBack implements Serializable {
	private String relId;				// 序号(20位）	RELID	VARCHAR2(30)	TRUE	FALSE	TRUE
	private String sgId;				// 存储体GLOBALID	STGLOBALID	VARCHAR2(256)	FALSE	FALSE	FALSE
	private String sdgId;				// 存储体目录GLOBALID	STDIRGLOBALID	VARCHAR2(256)	FALSE	FALSE	TRUE
	private String pgId;				// 节目GLOBALID	PRGLOBALID	VARCHAR2(256)	FALSE	FALSE	FALSE
	private String progFileId;			// *文件编号GLOBALID	PROGFILEID	VARCHAR2(30)	FALSE	FALSE	FALSE
	private String fileTypeGlobalId;	// 文件类型GLOBALID	FTYPEGLOBALID	VARCHAR2(256)	FALSE	FALSE	FALSE
	private String fileName;			// 文件名称	FILENAME	VARCHAR2(100)	FALSE	FALSE	FALSE
	private Date fileUpdTime;			// *文件更新日期	FILEDATE	DATE	FALSE	FALSE	FALSE
	private String filePath;			// *文件路径	FILEPATH	VARCHAR2(200)	FALSE	FALSE	FALSE
	private Date fileUploadTime;		// 上传日期	UPLOADTIME	DATE	FALSE	FALSE	FALSE
	private String inputManId;			// 信息录入人	INPUTMANID	VARCHAR2(60)	FALSE	FALSE	FALSE
	private Date inputTime;				// 信息录入时间	INPUTTIME	DATE	FALSE	FALSE	FALSE
	private String remak;				// 备注	REMARK	VARCHAR2(400)	FALSE	FALSE	FALSE
	
	/**
	 * default constructor
	 */
	public AmsStoragePrgRelBack() {}

	/**
	 * 全参构造
	 * @param relId 				备份文件存储ID, 主键
	 * @param sgId					存储体ID
	 * @param sdgId					存储体目录
	 * @param pgId					节目编号
	 * @param progFileId			文件编号
	 * @param fileTypeGlobalId		文件类型
	 * @param fileName				文件名称
	 * @param fileUpdTime			文件更新日期
	 * @param filePath				文件路径
	 * @param fileUploadTime		上传日期
	 * @param inputManId			信息录入者编号
	 * @param inputTime				信息录入时间
	 * @param remak					备注
	 */
	public AmsStoragePrgRelBack(String relId, String sgId, String sdgId,
			String pgId, String progFileId, String fileTypeGlobalId,
			String fileName, Date fileUpdTime, String filePath,
			Date fileUploadTime, String inputManId, Date inputTime, String remak) {
		super();
		this.relId = relId;
		this.sgId = sgId;
		this.sdgId = sdgId;
		this.pgId = pgId;
		this.progFileId = progFileId;
		this.fileTypeGlobalId = fileTypeGlobalId;
		this.fileName = fileName;
		this.fileUpdTime = fileUpdTime;
		this.filePath = filePath;
		this.fileUploadTime = fileUploadTime;
		this.inputManId = inputManId;
		this.inputTime = inputTime;
		this.remak = remak;
	}

	/**
	 * 获取主键
	 * @return relId
	 */
	public String getRelId() {
		return this.relId;
	}

	/**
	 * 设置主键
	 * @param relId
	 */
	public void setRelId(String relId) {
		this.relId = relId;
	}

	/**
	 * 获取存储体编号
	 * @return sgId
	 */
	public String getSgId() {
		return this.sgId;
	}

	/**
	 * 设置存储体编号
	 * @param sgId
	 */
	public void setSgId(String sgId) {
		this.sgId = sgId;
	}

	/**
	 * 获取存储体目录
	 * @return sdgId
	 */
	public String getSdgId() {
		return this.sdgId;
	}

	/**
	 * 设置存储体目录
	 * @param sdgId
	 */
	public void setSdgId(String sdgId) {
		this.sdgId = sdgId;
	}

	/**
	 * 获取节目编号
	 * @return pgId
	 */
	public String getPgId() {
		return this.pgId;
	}

	/**
	 * 设置节目编号
	 * @param pgId
	 */
	public void setPgId(String pgId) {
		this.pgId = pgId;
	}

	/**
	 * 获取文件编号
	 * @return progFileId
	 */
	public String getProgFileId() {
		return this.progFileId;
	}

	/**
	 * 设置文件编号
	 * @param progFileId
	 */
	public void setProgFileId(String progFileId) {
		this.progFileId = progFileId;
	}

	/**
	 * 获取文件类型
	 * @return fileTypeGlobalId
	 */
	public String getFileTypeGlobalId() {
		return this.fileTypeGlobalId;
	}

	/**
	 * 设置文件类型
	 * @param fileTypeGlobalId
	 */
	public void setFileTypeGlobalId(String fileTypeGlobalId) {
		this.fileTypeGlobalId = fileTypeGlobalId;
	}

	/**
	 * 获取文件名
	 * @return fileName
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * 设置文件名
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 获取文件更新时间
	 * @return fileUpdTime
	 */
	public Date getFileUpdTime() {
		return this.fileUpdTime;
	}

	/**
	 * 设置文件更新时间
	 * @param fileUpdTime
	 */
	public void setFileUpdTime(Date fileUpdTime) {
		this.fileUpdTime = fileUpdTime;
	}

	/**
	 * 获取文件路径
	 * @return filePath
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 设置文件路径
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 获取文件上传时间
	 * @return fileUploadTime
	 */
	public Date getFileUploadTime() {
		return this.fileUploadTime;
	}

	/**
	 * 设置文件上传时间
	 * @param fileUploadTime
	 */
	public void setFileUploadTime(Date fileUploadTime) {
		this.fileUploadTime = fileUploadTime;
	}

	/**
	 * 获取信息录入者编号
	 * @return inputManId
	 */
	public String getInputManId() {
		return this.inputManId;
	}

	/**
	 * 设置信息录入者编号
	 * @param inputManId
	 */
	public void setInputManId(String inputManId) {
		this.inputManId = inputManId;
	}

	/**
	 * 获取信息录入时间
	 * @return inputTime
	 */
	public Date getInputTime() {
		return this.inputTime;
	}

	/**
	 * 设置信息录入时间
	 * @param inputTime
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	/**
	 * 获取备注
	 * @return remak
	 */
	public String getRemak() {
		return this.remak;
	}

	/**
	 * 设置备注
	 * @param remak
	 */
	public void setRemak(String remak) {
		this.remak = remak;
	}
}
