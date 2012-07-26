package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.amsmgr.manageriface.IAmsStorageClassManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.dto.CmsResultDto;



public interface IMigrationModuleManager	
{
	//---------------------- 1.2 接口方法 -----------------------------------------------------------
	/**
	 * 20110107 10:17 1.23 加扰库 --> 一级库（播发库），生成迁移任务（以节目包为单位）
	 */
	public CmsResultDto saveMigrationFromCaonlineToOnlineByProgpackages_123(
			String date,
			List<ProgPackage> progPackages,
			List<String> productinfoids,
			String operatorId
			);
	
	/**
	 * 20110107 10:17 1.23 加扰库 --> 一级库（播发库），生成迁移任务（以编单日期为单位）
	 */
	public CmsResultDto saveMigrationFromCaonlineToOnline_123(
			String date,
			List<PortalColumn> portalColumns,
			String operatorId
			);
	
	/**
	 * 20110107 10:17 1.23 加扰库 --> 一级库（播发库），完成迁移（迁移反馈）
	 */
	public CmsResultDto updateFinishMigrationFromCaonlineToOnline_123(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			);
	
	/**
	 * 20110107 10:17 1.23 加扰库 --> 一级库（播发库），检验编单中节目包是否符合发送规则
	 * @param date，编单日期，格式："yyyy-MM-dd"
	 * @return 0 - 检验通过；1 - 检验不通过
	 */
	public CmsResultDto checkMigrationCanSend_123(
			String date						// yyyy-MM-dd
			);
	
	//=============================================================================================
	
	//---------------------- 1.2 接口方法 -----------------------------------------------------------
	/**
	 * 20101110 10:00 1.2 修改节目包的状态和处理状态
	 * @param progPackage
	 * @param fileStyleCode
	 * @return
	 */
	public CmsResultDto updateRefreshStateOfProgPackage(
			ProgPackage progPackage,	// 节目包
			long fileStyleCode,			// 文件样式应用code
			String result				// 此次操作结果： "Y" - 成功； "N" - 失败
			);
	
	/**
	 * 20110110 17:08 1.23 修改节目包的状态和处理状态
	 * @param progPackage
	 * @param fileStyleCode
	 * @return
	 */
	public CmsResultDto updateRefreshStateOfProgPackage_123(
			ProgPackage progPackage,	// 节目包
			long fileStyleCode,			// 文件样式应用code
			String result,				// 此次操作结果： "Y" - 成功； "N" - 失败
			String mainFileTag,
			String productInfoId
			);
	
	/**
	 * 20101203 16:33 1.2 加扰库 --> 一级库（播发库），生成迁移任务（以节目包为单位）
	 */
	public CmsResultDto saveMigrationFromCaonlineToOnlineByProgpackages(
			String date,
			List<ProgPackage> progPackages,
			String operatorId
			);
	
	/**
	 * 20101103 13:23 1.21 迁移，加扰库 --> 一级库（播发库）
	 * @param date
	 * @param operatorId
	 * @return
	 */
	
	public CmsResultDto saveMigrationFromCaonlineToOnline(
			String date,					// 编单日期，格式：yyyy-MM-dd
			List<PortalColumn> portalColumns,
			String operatorId				// 操作人员ID
			);
	
	/**
	 * 20101108 13:30 完成迁移，加扰库 --> 一级库（播发库）
	 * @param transferEntity
	 * @param result
	 * @param resultDes
	 * @return
	 */
	public CmsResultDto updateFinishMigrationFromCaonlineToOnline(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			);
	
	/**
	 * 20101115 12:35 1.21 生成迁移任务，节目录入（至缓存库）
	 * @param programInfo
	 * @param programFiles
	 * @param sourcePartPath
	 * @param sourceStclasscode
	 * @param filePath
	 * @param destStclasscode
	 * @return
	 */
	public CmsResultDto saveMigrationForProgramInfo(
			ProgramInfo programInfo,
			ProgramFiles programFiles,
			String sourcePartPath,			// 源文件(相对)路径，由界面输入，以"/"或"\"结尾，认为是文件夹，其他认为是文件
			String sourceStclasscode,		// 源文件的存储体等级code
			String filePath,				// 目标文件的filePath
			String destStclasscode			// 目标文件的存储体等级code
			);
	
	/**
	 * 20101115 13:10 1.21 完成迁移，节目录入（至缓存库）
	 * @param transferEntity
	 * @param result
	 * @param resultDes
	 * @return
	 */
	public CmsResultDto updateFinishMigrationForProgramInfo(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			);
	
	/**
	 * 20101209 13:52 1.2 加扰库 --> 一级库（播发库），检验编单中节目包是否符合发送规则
	 * @param date，编单日期，格式："yyyy-MM-dd"
	 * @return 0 - 检验通过；1 - 检验不通过
	 */
	public CmsResultDto checkMigrationCanSend(
			String date						// yyyy-MM-dd
			);
	//=============================================================================================
}
