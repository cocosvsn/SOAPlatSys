/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.dto;

/**
 * @author：Bunco E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-11-23 上午11:05:01
 */
public class EncryptProgVo {
	private String	progPackageId;				// 节目包ID
	private String	progPackageName;			// 节目包名称
	private Long	progPackageState;			// 节目包状态[-1:未导入; 0:导入区; 1:缓存库; 2:加扰库; 3:播发库;]
	private Long	progPackageDealState;		// 节目包处理状态[0:未处理; 1:处理中; 8:失败; 9:成功;]
	private String	programInfoId;				// 节目ID
	private String	programInfoProgType;		// 节目类型[V: 视频节目; R: 富媒体节目]
	private Long	programInfoDsFlag;			// 节目状态[-1: 未导入; 0:迁移; 1:迁移失败; 2:新入库; 3:已包装成节目包]
	private String	programFileId;				// 文件ID
	private String	programFileTypeId;			// 文件类型ID
	private String	programFileCode;			// 文件Code
	private String	programFileName;			// 文件名
	private Long	programFileProgRank;		// 主文件标识[1: 主文件; 2: 非主文件]
	private String	programFileContentId;		// 文件ContentID
	private Long	programFileProgStatus;		// 实体文件存在标识 [1: 存在, 0: 不存在]
	private Long	programFileEncodeStatus;	// 内容加密标识[0: 未加密, 1: 已加密]
	
	public EncryptProgVo() {}

	/**
	 * @param progPackageId				节目包ID
	 * @param progPackageName			节目包名称
	 * @param progPackageState			节目包状态[-1:未导入; 0:导入区; 1:缓存库; 2:加扰库; 3:播发库;]
	 * @param progPackageDealState		节目包处理状态[0:未处理; 1:处理中; 8:失败; 9:成功;]
	 * @param programInfoId				节目ID
	 * @param programInfoProgType		节目类型[V: 视频节目; R: 富媒体节目]
	 * @param programInfoDsFlag			节目状态[-1: 未导入; 0:迁移; 1:迁移失败; 2:新入库; 3:已包装成节目包]
	 * @param programFileId				文件ID
	 * @param programFileTypeId			文件类型ID
	 * @param programFileCode			文件Code
	 * @param programFileName			文件名
	 * @param programFileProgRank		主文件标识[1: 主文件; 2: 非主文件]
	 * @param programFileContentId		文件ContentID
	 * @param programFileProgStatus		实体文件存在标识 [1: 存在, 0: 不存在]
	 * @param programFileEncodeStatus	内容加密标识[0: 未加密, 1: 已加密]
	 */
	public EncryptProgVo(String progPackageId, String progPackageName,
			Long progPackageState, Long progPackageDealState, String programInfoId,
			String programInfoProgType, Long programInfoDsFlag,
			String programFileId, String programFileTypeId, 
			String programFileCode, String programFileName, 
			Long programFileProgRank, String programFileContentId, 
			Long programFileProgStatus, Long programFileEncodeStatus) {
		super();
		this.progPackageId = progPackageId;
		this.progPackageName = progPackageName;
		this.progPackageState = progPackageState;
		this.progPackageDealState = progPackageDealState;
		this.programInfoId = programInfoId;
		this.programInfoProgType = programInfoProgType;
		this.programInfoDsFlag = programInfoDsFlag;
		this.programFileId = programFileId;
		this.programFileTypeId = programFileTypeId;
		this.programFileCode = programFileCode;
		this.programFileName = programFileName;
		this.programFileProgRank = programFileProgRank;
		this.programFileContentId = programFileContentId;
		this.programFileProgStatus = programFileProgStatus;
		this.programFileEncodeStatus = programFileEncodeStatus;
	}

	/**
	 * 节目包ID
	 * @return
	 */
	public String getProgPackageId() {
		return progPackageId;
	}

	/**
	 * 节目包ID
	 * @param progPackageId
	 */
	public void setProgPackageId(String progPackageId) {
		this.progPackageId = progPackageId;
	}

	/**
	 * 节目包名称
	 * @return
	 */
	public String getProgPackageName() {
		return progPackageName;
	}

	/**
	 * 节目包名称
	 * @param progPackageName
	 */
	public void setProgPackageName(String progPackageName) {
		this.progPackageName = progPackageName;
	}

	/**
	 * 节目包状态[-1:未导入; 0:导入区; 1:缓存库; 2:加扰库; 3:播发库;]
	 * @return
	 */
	public Long getProgPackageState() {
		return progPackageState;
	}

	/**
	 * 节目包状态[-1:未导入; 0:导入区; 1:缓存库; 2:加扰库; 3:播发库;]
	 * @param progPackageState
	 */
	public void setProgPackageState(Long progPackageState) {
		this.progPackageState = progPackageState;
	}

	/**
	 * 节目包处理状态[0:未处理; 1:处理中; 8:失败; 9:成功;]
	 * @return
	 */
	public Long getProgPackageDealState() {
		return progPackageDealState;
	}
	
	/**
	 * 节目包处理状态[0:未处理; 1:处理中; 8:失败; 9:成功;]
	 * @param progPackageDealState
	 */
	public void setProgPackageDealState(Long progPackageDealState) {
		this.progPackageDealState = progPackageDealState;
	}
	
	/**
	 * 节目ID
	 * @return
	 */
	public String getProgramInfoId() {
		return programInfoId;
	}

	/**
	 * 节目ID
	 * @param programInfoId
	 */
	public void setProgramInfoId(String programInfoId) {
		this.programInfoId = programInfoId;
	}

	/**
	 * 节目类型[V: 视频节目; R: 富媒体节目]
	 * @return
	 */
	public String getProgramInfoProgType() {
		return programInfoProgType;
	}

	/**
	 * 节目类型[V: 视频节目; R: 富媒体节目]
	 * @param programInfoProgType
	 */
	public void setProgramInfoProgType(String programInfoProgType) {
		this.programInfoProgType = programInfoProgType;
	}

	/**
	 * 节目状态[-1: 未导入; 0:迁移; 1:迁移失败; 2:新入库; 3:已包装成节目包]
	 * @return
	 */
	public Long getProgramInfoDsFlag() {
		return programInfoDsFlag;
	}

	/**
	 * 节目状态[-1: 未导入; 0:迁移; 1:迁移失败; 2:新入库; 3:已包装成节目包]
	 * @param programInfoDsFlag
	 */
	public void setProgramInfoDsFlag(Long programInfoDsFlag) {
		this.programInfoDsFlag = programInfoDsFlag;
	}

	/**
	 * 文件ID
	 * @return
	 */
	public String getProgramFileId() {
		return programFileId;
	}

	/**
	 * 文件ID
	 * @param programFileId
	 */
	public void setProgramFileId(String programFileId) {
		this.programFileId = programFileId;
	}

	/**
	 * 文件类型ID
	 * @return
	 */
	public String getProgramFileTypeId() {
		return programFileTypeId;
	}

	/**
	 * 文件类型ID
	 * @param programFileTypeId
	 */
	public void setProgramFileTypeId(String programFileTypeId) {
		this.programFileTypeId = programFileTypeId;
	}

	/**
	 * 文件Code
	 * @return
	 */
	public String getProgramFileCode() {
		return programFileCode;
	}
	
	/**
	 * 文件Code
	 * @param programFileCode
	 */
	public void setProgramFileCode(String programFileCode) {
		this.programFileCode = programFileCode;
	}
	
	/**
	 * 文件名
	 * @return
	 */
	public String getProgramFileName() {
		return programFileName;
	}

	/**
	 * 文件名
	 * @param programFileName
	 */
	public void setProgramFileName(String programFileName) {
		this.programFileName = programFileName;
	}

	/**
	 * 主文件标识[1: 主文件; 2: 非主文件]
	 * @return
	 */
	public Long getProgramFileProgRank() {
		return programFileProgRank;
	}

	/**
	 * 主文件标识[1: 主文件; 2: 非主文件]
	 * @param programFileProgRank
	 */
	public void setProgramFileProgRank(Long programFileProgRank) {
		this.programFileProgRank = programFileProgRank;
	}

	/**
	 * 文件ContentID
	 * @return
	 */
	public String getProgramFileContentId() {
		return programFileContentId;
	}

	/**
	 * 文件ContentID
	 * @param programFileContentId
	 */
	public void setProgramFileContentId(String programFileContentId) {
		this.programFileContentId = programFileContentId;
	}

	/**
	 * 实体文件存在标识 [1: 存在, 0: 不存在]
	 * @return
	 */
	public Long getProgramFileProgStatus() {
		return programFileProgStatus;
	}

	/**
	 * 实体文件存在标识 [1: 存在, 0: 不存在]
	 * @param programFileProgStatus
	 */
	public void setProgramFileProgStatus(Long programFileProgStatus) {
		this.programFileProgStatus = programFileProgStatus;
	}

	/**
	 * 内容加密标识[0: 未加密, 1: 已加密]
	 * @return
	 */
	public Long getProgramFileEncodeStatus() {
		return programFileEncodeStatus;
	}

	/**
	 * 内容加密标识[0: 未加密, 1: 已加密]
	 * @param programFileEncodeStatus
	 */
	public void setProgramFileEncodeStatus(Long programFileEncodeStatus) {
		this.programFileEncodeStatus = programFileEncodeStatus;
	}
}
