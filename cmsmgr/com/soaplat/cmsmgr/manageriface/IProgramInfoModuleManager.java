package com.soaplat.cmsmgr.manageriface;

import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.dto.CmsResultDto;

public interface IProgramInfoModuleManager {

	/**
	 * 条件查询节目
	 */
	public List<Object[]> queryProgramInfo(String hql, Map<String, Object> map);
	
	/**
	 * 判断文件是否存在
	 */
	public CmsResultDto isExist(
			String path,
			String fileCode
			);
	
	/**
	 * 保存节目和文件信息
	 */
	public CmsResultDto savePrograminfoProgramfiles(
			String operationType,
			ProgramInfo programInfo,
			ProgramFiles programFiles,
			String operatorId
			);
	
	/**
	 * 为节目文件生成迁移任务单
	 */
	public CmsResultDto saveMigrationTaskForPrograminfo(
			ProgramInfo programInfo,
			ProgramFiles programFiles,
			String sourcePartPath,			// 源文件(相对)路径，
			String sourceStclasscode,		// 源文件的存储体等级code
			String filePath,				// 目标文件的filePath
			String destStclasscode			// 目标文件的存储体等级code
			);

	/**
	 * 补充实体文件时, 修改原始文件名
	 * @param programFileId 文件表ID
	 * @param path 用户输入的文件路径
	 * @param fileSize 文件大小
	 * @return
	 */
	public CmsResultDto updateProgramFileName(String programFileId, 
			String path, String fileSize);
}
