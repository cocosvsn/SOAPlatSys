package com.soaplat.cmsmgr.managerimpl;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.log4j.Logger;

import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.amsmgr.serviceimpl.AmsStoragePrgRelService;
import com.soaplat.cmsmgr.bean.Bpmc;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.manageriface.IMigrationModuleManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoModuleManager;
import com.soaplat.cmsmgr.util.SmbFileUtils;
import com.soaplat.sysmgr.common.IBaseDAO;

public class ProgramInfoModuleManagerImpl implements IProgramInfoModuleManager {
	private final String storageClassCodePrepared = "Prepared";		// 上海节目录入存储体等级code
	private IBaseDAO baseDAO;
	private IProgramInfoManager programInfoManager;
	private IPackageFilesManager packageFilesManager;
	private IAmsStorageDirManager amsStorageDirManager;
	private IMigrationModuleManager migrationModuleManager;

	private static final Logger cmsLog = Logger.getLogger("Cms");
	
	/**
	 * 节目状态（dsflag）
	 * -1 - 未导入
	 * 0 - 迁移中
	 * 1 - 迁移失败
	 * 2 - 新录入
	 * 3 - 已包装
	 * 
	 * 操作类型（operationType）
	 * 1 - 正常节目录入
	 * 2 - 无实体文件节目录入
	 * 3 - 补充实体文件
	 * 
	 * 节目包状态（ProgPackage --> state）
	 * -1 - 未导入
	 * 0 - 导入区（Import），1.2不使用
	 * 1 - 缓存库(NearOnline)
	 * 2 - 加扰库(CaOnline)
	 * 3 - 播发库(Online)
	 * 9 - 在上海的北京缓存库(BjOnline)，1.2不使用
	 */
	
	/**
	 * 条件查询节目
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryProgramInfo(String hql, Map<String, Object> map) {
		return (List<Object[]>) this.baseDAO.query(hql, map);
	}
	
	/**
	 * 判断文件是否存在
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto isExist(String path, String fileCode, List<String> supportExtensions) {
		cmsLog.debug("Cms -> ProgramInfoModuleManagerImpl -> isExist...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		List<ProgramFiles> programFiles = this.baseDAO.findByProperty(
				"ProgramFiles", "processinstid", path);
		
		List<Object[]> list = this.amsStorageDirManager
				.getStorageStoragedirsByStclasscodeFilecode(
							storageClassCodePrepared, fileCode);
		Object[] objects = list.get(0);
		AmsStorage storage = (AmsStorage) objects[0];
		AmsStorageDir storageDir = (AmsStorageDir) objects[1];
		String smbFilePath = AmsStoragePrgRelService.getPath(storage, storageDir, null)
				 + path;
		
		try {
			String tmpPath = smbFilePath.substring(smbFilePath.lastIndexOf("@") + 1, 
					smbFilePath.length());
			SmbFile smbFile = new SmbFile(smbFilePath);
			cmsResultDto.setResultCode(0L);
			cmsResultDto.setResultObject(new Object[] {
					"H264".equals(fileCode) 
							/**
							 * 修改判断文件是否存在由固定只判断ts文件改为可支持多视频类型
							 * HuangBo update by 2012年2月19日 20时49分
							 */
							? supportExtensions.contains(smbFilePath.substring(
									smbFilePath.lastIndexOf(".") + 1).toLowerCase()) 
									? smbFile.exists() : false 
									: smbFile.isDirectory() ? smbFile.exists() : false, 
					smbFile.exists() ? SmbFileUtils.sizeOf(smbFile)	: 0, 
					"\\\\" + tmpPath.replaceAll("/", "\\\\"), 
					0 < programFiles.size()});
		} catch (MalformedURLException e) {
			cmsLog.error("SmbFile文件路径错误: " + e);
			cmsResultDto.setResultCode(1L);
			cmsResultDto.setErrorMessage("SmbFile文件访问失败, 请检查网络!");
		} catch (SmbException e) {
			cmsLog.error("SmbFile文件访问失败, 请检查网络 : " + e);
			cmsResultDto.setResultCode(1L);
			cmsResultDto.setErrorMessage("SmbFile文件访问失败, 请检查网络!");
		}
		
		cmsLog.debug("Cms -> ProgramInfoModuleManagerImpl -> isExist returns.");
		return cmsResultDto;
	}
	
	/**
	 * 保存节目和文件信息
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto savePrograminfoProgramfiles(
			String operationType,
			ProgramInfo programInfo,
			ProgramFiles programFiles,
			String operatorId) {
		/**
		 * 判断操作类型（operationType）
		 * 如果是 1 - 正常节目录入 或 2 - 无实体文件节目录入 ，
		 * 		节目表节目状态（dsflag）为：-1 - 未导入，
		 * 		文件表实体文件存在标识（progstatus）为：1 - 实体文件不存在
		 * 如果是 3 - 补充实体文件，不处理
		 */
		cmsLog.debug("Cms -> ProgramInfoModuleManagerImpl -> savePrograminfoProgramfiles...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		Bpmc bpmc = null;
		
		try {
			ProgramInfo persistedProgramInfo = (ProgramInfo) this.programInfoManager.save(programInfo);
			cmsLog.debug("节目信息表已经保存，节目ID：" + persistedProgramInfo.getProgramid());
			
			programFiles.setProgramid(persistedProgramInfo.getProgramid());
			cmsLog.debug("节目[ " + persistedProgramInfo.getTitle() + " ]源文件大小为: " + programFiles.getContentfilesize());
			if ("NaN".equals(programFiles.getContentfilesize())) {
				cmsLog.debug("节目[ " + persistedProgramInfo.getTitle() + " ] 大小为 [ NaN ]重新计算大小!");
				List<Object[]> list = this.amsStorageDirManager
						.getStorageStoragedirsByStclasscodeFilecode(
									storageClassCodePrepared, programFiles.getFilecode());
				Object[] objects = list.get(0);
				AmsStorage storage = (AmsStorage) objects[0];
				AmsStorageDir storageDir = (AmsStorageDir) objects[1];
				String smbFilePath = AmsStoragePrgRelService.getPath(storage, storageDir, null)
						 + programFiles.getProcessinstid();
				SmbFile file = new SmbFile(smbFilePath);
				if (file.exists()) {
					cmsLog.debug("节目[ " + persistedProgramInfo.getTitle() + " ]源文件存在, 重新计算大小为: " + file.length());
					programFiles.setContentfilesize(String.valueOf(file.length()));
				} else {
					cmsLog.debug("节目[ " + persistedProgramInfo.getTitle() + " ]源文件不存在, 设置大小为: 0");
					programFiles.setContentfilesize("0");
				}
			}
			this.baseDAO.save(programFiles);
			
			cmsResultDto.setResultCode(0L);
			cmsResultDto.setResultObject(new Object[] {persistedProgramInfo, programFiles});
			
			bpmc = new Bpmc(programInfo.getProgramid(), operatorId, null, null, 
					null, null, programInfo.getTitle() + " ----> 创建成功!", "C");
		} catch (Exception e) {
			cmsLog.error("保存节目或文件信息出错: " + e);
			bpmc = new Bpmc(programInfo.getProgramid(), operatorId, null, null, 
					null, null, programInfo.getTitle() + " ----> 创建失败!", "C");
			cmsResultDto.setResultCode(1L);
			cmsResultDto.setErrorMessage(" 保存节目或文件信息失败! ");
		} finally {
			this.baseDAO.save(bpmc);
		}
		
		cmsLog.debug("Cms -> ProgramInfoModuleManagerImpl -> savePrograminfoProgramfiles returns.");
		return cmsResultDto;
	}
	
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
			)
	{
		/**
		 * 判断的节目状态（-1 - 未导入；1 - 迁移失败），是否合法
		 * 生成迁移任务成功，修改
		 * 		节目表节目状态（dsflag）为：0 - 迁移中
		 * 生成迁移任务失败，修改
		 * 		节目表节目状态（dsflag）为：1 - 迁移失败
		 */
		
		/**
		 * 迁移反馈处理：
		 * 迁移成功，修改：
		 * 		节目表节目状态（dsflag）为：2 - 新录入
		 * 		文件表实体文件存在标识（progstatus）为：0 - 实体文件存在
		 * 		与文件相关的节目包状态为：1 - 缓存库(NearOnline)，原来状态为：-1 - 未导入
		 * 迁移失败，修改：
		 * 		节目表节目状态（dsflag）为：1 - 迁移失败
		 */
		
		cmsLog.debug("Cms -> ProgramInfoModuleManagerImpl -> saveMigrationTaskForPrograminfo...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsResultDto = migrationModuleManager.saveMigrationForProgramInfo(
				programInfo, 
				programFiles, 
				sourcePartPath, 
				sourceStclasscode, 
				filePath, 
				destStclasscode
				);
		
		cmsLog.debug("Cms -> ProgramInfoModuleManagerImpl -> saveMigrationTaskForPrograminfo returns.");
		return cmsResultDto;
	}

	/**
	 * 补充实体文件时, 修改原始文件名
	 * @param programFileId 文件表ID
	 * @param path 用户输入的文件路径
	 * @param fileSize 文件大小
	 * @return
	 */
	public CmsResultDto updateProgramFileName(String programFileId, 
			String path, String fileSize) {
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		try {
			ProgramFiles programFiles = (ProgramFiles) this.baseDAO.getById(
					ProgramFiles.class, programFileId);
			programFiles.setProcessinstid(path);
			programFiles.setContentfilesize(fileSize);
			this.baseDAO.update(programFiles);
			cmsResultDto.setResultCode(0L);
			cmsResultDto.setResultObject(programFiles);
		} catch (Exception e) {
			cmsLog.error(" 修改原始文件名和文件大小出错: " + e);
			cmsResultDto.setResultCode(1L);
			cmsResultDto.setErrorMessage(" 修改原始文件名出错! ");
		}
		return cmsResultDto;
	}
	
	/**------------ setter and getter --------------**/
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void setProgramInfoManager(IProgramInfoManager programInfoManager) {
		this.programInfoManager = programInfoManager;
	}
	
	public void setPackageFilesManager(IPackageFilesManager packageFilesManager) {
		this.packageFilesManager = packageFilesManager;
	}
	
	public void setAmsStorageDirManager(
			IAmsStorageDirManager amsStorageDirManager) {
		this.amsStorageDirManager = amsStorageDirManager;
	}

	public void setMigrationModuleManager( 
			IMigrationModuleManager migrationModuleManager) {
		this.migrationModuleManager = migrationModuleManager;
	}
}
//http://140.112.2.5/Downloads/MySQL-5.5/mysql-5.5.20-linux2.6-x86_64.tar.gz
//ftp://140.110.123.9/Unix/Database/MySQL/Downloads/MySQL-5.5/mysql-5.5.20-linux2.6-x86_64.tar.gz