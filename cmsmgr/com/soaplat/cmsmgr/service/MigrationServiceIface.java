package com.soaplat.cmsmgr.service;

import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.dto.CmsResultDto;

public interface MigrationServiceIface {

	
	// 20100123 11:05
	// 生成一级库的迁移单
	public CmsResultDto generateMigrationToOnline(
			String date,					// 编单日期，格式：yyyy-MM-dd
			String operatorId				// 操作人员ID
			);
	
	// 20100125 22:34
	// 完成迁移到一级库，迁移模块完成迁移后，通过webservice调用这个接口
	public CmsResultDto finishMigrationToOnline(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			);
	
	// 20100208 13:23
	// 生成数据导出对应的迁移单
	public CmsResultDto generateMigrationForExportData(
			String date,					// 编单日期，格式：yyyy-MM-dd
			String operatorId				// 操作人员ID
			);
	
	// 20100208 16:22
	// 数据导出迁移反馈
	public CmsResultDto finishMigrationToBjOnline(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			);
	
	// 20100223 11:20
	// 数据导入迁移反馈
	public CmsResultDto finishMigrationImportDataToBjNearOnline(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			);
	
	// 20100312 13:17
	// 节目录入时，下迁移单
	public CmsResultDto generateMigrationForProgram(
			ProgramInfo programInfo,
			ProgramFiles programFiles,
			String sourcePath,				// 源文件(相对)路径，
			String sourceStclasscode,		// 源文件的存储体等级code
			String filePath,				// 目标文件的filePath
			String destStclasscode			// 目标文件的存储体等级code
			);
	
	// 20100312 13:17
	// 节目录入时，迁移反馈
	public CmsResultDto finishMigrationForProgram(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			);
	
	
}
