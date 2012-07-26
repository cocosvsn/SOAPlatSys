package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgramFilesManager extends IBaseManager {
	public List findbyprogramidfiletype(String programid,String filetypeid);
	public List findstoragelistbyfileid(String programid,String filetypeid);
	public String findmaxprogramfileidbyprogramidandfiletype(String programid,String filetypeid);
	
	/**
	 * 获取节目文件ID
	 * @param programInfoId
	 * @return
	 */
	public String getProgramFilesID(String programInfoId);
	
	/**
	 * 根据节目包ID, 查询该节目包所有文件信息
	 * @param progPackageID 节目包(ProgPackage)ID
	 * @return 返回节目包下所有文件信息
	 */
	public List<ProgramFiles> queryProgramFilesByProgPackageID(
			String progPackageID, List<String> fileTypeIds);

	/**
	 * 根据节目包ID查询节目包中主文件ID
	 * @param progPackageId 节目包ID
	 * @return 返回节目包中主文件ID
	 */
	public String queryProgPackageMainFileId(String progPackageId);
	
	/**
	 * 根据节目包ID查询节目包中主文件contentID
	 * @param progPackageId 节目包ID
	 * @return 返回节目包中主文件contentID
	 */
	public String queryContentIdByProgPackage(String progPackageId);
	
	/**
	 * 根据节目包ID, 文件Code查询文件ID
	 * @param progPackageId 节目包ID
	 * @param fileCodes 文件Code
	 * @return 返回文件ID集合
	 */
	public List<String> queryProgramFileIdByProgPackageIdFileCode(String progPackageId,
			List<String> fileCodes);
	
	/**
	 * 根据节目包ID查询该节目包下所有文件
	 * @param progPackageId 节目包ID
	 * @return 返回节目包所有文件信息
	 */
	public List<ProgramFiles> queryProgramFilesByProgPackageID(
			String progPackageId);

	/**
	 * 根据节目包和应用code，获得文件列表
	 * @param fileStyleManager
	 * @param packageFilesManager
	 * @param progPackage
	 * @param fileStyleCode
	 * @return
	 */
	public List<ProgramFiles> getProgramFilesesByProgPackageFilestylecode(
			IFileStyleManager fileStyleManager,
			IPackageFilesManager packageFilesManager,
			ProgPackage progPackage,
			long fileStyleCode 
			);
}
