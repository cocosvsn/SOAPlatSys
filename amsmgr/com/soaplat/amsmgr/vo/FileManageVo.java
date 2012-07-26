/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.amsmgr.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-22 上午05:50:28
 */
public class FileManageVo implements Serializable {
	/*------------- 显示信息 --------------*/
	private String pkgId;						// 节目包ID;
	private String pkgName;						// 节目包名称;
	private Date upLineDate;					// 上线日期;
	private Date downLineDate;					// 下线日期;
	private long pkgStateCode;					// 节目包状态码;
	private String pkgStateName;				// 节目包状态;
	private long pkgDealStateCode;				// 节目包处理状态码;
	private String pkgDealStatename;			// 节目包处理状态;
	private String fileName;					// 文件名;
	private String fileCode;					// 文件代码;
	private String filePath;					// 文件路径;
	private String fileSize;					// 文件大小;
	private Date fileCreateTime;				// 文件创建时间;
	private String viewPath;					// 显示文件路径
	private boolean isCanDelete = false;		// 是否可删除;
	/*------------- 其它信息 --------------*/
	private String storageId;					// 存储体ID
	private String storageClassId;				// 存储体等级ID
	private String storageClassName;			// 存储体等级名称
	private String storageDirId;				// 存储体目录ID
	private String relId;						// 存储体, 文件, 目录关系ID
	private String progListDetailId;			// 节目包详细ID
	private String messageState;				// 信息状态
	private String smbFilePref;					// smb文件前辍
	
	/** constructor **/
	public FileManageVo() {}
	
	/**
	 * @param pkgId
	 * @param pkgName
	 * @param upLineDate
	 * @param downLineDate
	 * @param pkgStateCode
	 * @param pkgStateName
	 * @param pkgDealStateCode
	 * @param pkgDealStatename
	 * @param fileName
	 * @param fileCode
	 * @param filePath
	 * @param fileSize
	 * @param fileCreateTime
	 * @param isCanDelete
	 * @param storageId
	 * @param storageClassId
	 * @param storageDirId
	 * @param relId
	 * @param progListDetailId
	 * @param messageState
	 */
	public FileManageVo(String pkgId, String pkgName, Date upLineDate,
			Date downLineDate, long pkgStateCode, String pkgStateName,
			long pkgDealStateCode, String pkgDealStatename, String fileName,
			String fileCode, String filePath, String fileSize,
			Date fileCreateTime, boolean isCanDelete, String storageId,
			String storageClassId, String storageDirId, String relId,
			String progListDetailId, String messageState) {
		super();
		this.pkgId = pkgId;
		this.pkgName = pkgName;
		this.upLineDate = upLineDate;
		this.downLineDate = downLineDate;
		this.pkgStateCode = pkgStateCode;
		this.pkgStateName = pkgStateName;
		this.pkgDealStateCode = pkgDealStateCode;
		this.pkgDealStatename = pkgDealStatename;
		this.fileName = fileName;
		this.fileCode = fileCode;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.fileCreateTime = fileCreateTime;
		this.isCanDelete = isCanDelete;
		this.storageId = storageId;
		this.storageClassId = storageClassId;
		this.storageDirId = storageDirId;
		this.relId = relId;
		this.progListDetailId = progListDetailId;
		this.messageState = messageState;
	}

	/** setter and getter **/
	
	/**
	 * 获取  pkgId
	 * @return pkgId
	 */
	public String getPkgId() {
		return this.pkgId;
	}

	/**
	 * 设置 pkgId
	 * @param pkgId
	 */
	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}

	/**
	 * 获取  pkgName
	 * @return pkgName
	 */
	public String getPkgName() {
		return this.pkgName;
	}

	/**
	 * 设置 pkgName
	 * @param pkgName
	 */
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	/**
	 * 获取  upLineDate
	 * @return upLineDate
	 */
	public Date getUpLineDate() {
		return this.upLineDate;
	}

	/**
	 * 设置 upLineDate
	 * @param upLineDate
	 */
	public void setUpLineDate(Date upLineDate) {
		this.upLineDate = upLineDate;
	}

	/**
	 * 获取  downLineDate
	 * @return downLineDate
	 */
	public Date getDownLineDate() {
		return this.downLineDate;
	}

	/**
	 * 设置 downLineDate
	 * @param downLineDate
	 */
	public void setDownLineDate(Date downLineDate) {
		this.downLineDate = downLineDate;
	}

	/**
	 * 获取  pkgStateCode
	 * @return pkgStateCode
	 */
	public long getPkgStateCode() {
		return this.pkgStateCode;
	}

	/**
	 * 设置 pkgStateCode
	 * @param pkgStateCode
	 */
	public void setPkgStateCode(long pkgStateCode) {
		this.pkgStateCode = pkgStateCode;
	}

	/**
	 * 获取  pkgStateName
	 * @return pkgStateName
	 */
	public String getPkgStateName() {
		return this.pkgStateName;
	}

	/**
	 * 设置 pkgStateName
	 * @param pkgStateName
	 */
	public void setPkgStateName(String pkgStateName) {
		this.pkgStateName = pkgStateName;
	}

	/**
	 * 获取  pkgDealStateCode
	 * @return pkgDealStateCode
	 */
	public long getPkgDealStateCode() {
		return this.pkgDealStateCode;
	}

	/**
	 * 设置 pkgDealStateCode
	 * @param pkgDealStateCode
	 */
	public void setPkgDealStateCode(long pkgDealStateCode) {
		this.pkgDealStateCode = pkgDealStateCode;
	}

	/**
	 * 获取  pkgDealStatename
	 * @return pkgDealStatename
	 */
	public String getPkgDealStatename() {
		return this.pkgDealStatename;
	}

	/**
	 * 设置 pkgDealStatename
	 * @param pkgDealStatename
	 */
	public void setPkgDealStatename(String pkgDealStatename) {
		this.pkgDealStatename = pkgDealStatename;
	}

	/**
	 * 获取  fileName
	 * @return fileName
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * 设置 fileName
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 获取  fileCode
	 * @return fileCode
	 */
	public String getFileCode() {
		return this.fileCode;
	}

	/**
	 * 设置 fileCode
	 * @param fileCode
	 */
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	/**
	 * 获取  filePath
	 * @return filePath
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 设置 filePath
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 获取  fileSize
	 * @return fileSize
	 */
	public String getFileSize() {
		return this.fileSize;
	}

	/**
	 * 设置 fileSize
	 * @param fileSize
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 获取  fileCreateTime
	 * @return fileCreateTime
	 */
	public Date getFileCreateTime() {
		return this.fileCreateTime;
	}

	/**
	 * 设置 fileCreateTime
	 * @param fileCreateTime
	 */
	public void setFileCreateTime(Date fileCreateTime) {
		this.fileCreateTime = fileCreateTime;
	}

	/**
	 * 获取  isCanDelete
	 * @return isCanDelete
	 */
	public boolean isCanDelete() {
		return this.isCanDelete;
	}

	/**
	 * 设置 isCanDelete
	 * @param isCanDelete
	 */
	public void setCanDelete(boolean isCanDelete) {
		this.isCanDelete = isCanDelete;
	}

	/**
	 * 获取  storageId
	 * @return storageId
	 */
	public String getStorageId() {
		return this.storageId;
	}

	/**
	 * 设置 storageId
	 * @param storageId
	 */
	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	/**
	 * 获取  storageClassId
	 * @return storageClassId
	 */
	public String getStorageClassId() {
		return this.storageClassId;
	}

	/**
	 * 设置 storageClassId
	 * @param storageClassId
	 */
	public void setStorageClassId(String storageClassId) {
		this.storageClassId = storageClassId;
	}

	/**
	 * 获取  storageDirId
	 * @return storageDirId
	 */
	public String getStorageDirId() {
		return this.storageDirId;
	}

	/**
	 * 设置 storageDirId
	 * @param storageDirId
	 */
	public void setStorageDirId(String storageDirId) {
		this.storageDirId = storageDirId;
	}

	/**
	 * 获取  relId
	 * @return relId
	 */
	public String getRelId() {
		return this.relId;
	}

	/**
	 * 设置 relId
	 * @param relId
	 */
	public void setRelId(String relId) {
		this.relId = relId;
	}

	/**
	 * 获取  progListDetailId
	 * @return progListDetailId
	 */
	public String getProgListDetailId() {
		return this.progListDetailId;
	}

	/**
	 * 设置 progListDetailId
	 * @param progListDetailId
	 */
	public void setProgListDetailId(String progListDetailId) {
		this.progListDetailId = progListDetailId;
	}

	/**
	 * 获取  messageState
	 * @return messageState
	 */
	public String getMessageState() {
		return this.messageState;
	}

	/**
	 * 设置 messageState
	 * @param messageState
	 */
	public void setMessageState(String messageState) {
		this.messageState = messageState;
	}
	
	/**
	 * 设置smb文件前辍
	 * @param smbFilePref
	 */
	public void setSmbFilePref(String smbFilePref) {
		this.smbFilePref = smbFilePref;
	}
	
	/**
	 * 获取smb文件前辍
	 * @return
	 */
	public String getSmbFilePref() {
		return this.smbFilePref;
	}
	
	/**
	 * 设置存储体等级名称
	 * @param storageClassName
	 */
	public void setStorageClassName(String storageClassName) {
		this.storageClassName = storageClassName;
	}
	
	/**
	 * 获取存储体等级名称
	 * @return
	 */
	public String getStorageClassName() {
		return storageClassName;
	}
	
	/**
	 * 获取显示路径
	 * @return
	 */
	public String getViewPath() {
		return viewPath;
	}
	
	/**
	 * 设置显示路径
	 * @param viewPath
	 */
	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}
}
