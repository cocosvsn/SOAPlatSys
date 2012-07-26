package com.soaplat.amsmgr.manageriface;

import java.util.Date;
import java.util.List;

import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.sysmgr.manageriface.IBaseManager;

/**
 * Title 		:the Interface IAmsStoragePrgRelManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IAmsStoragePrgRelManager extends IBaseManager{

	
	// 20100520 10:39
	// 判断文件位置表记录是否已经存在
	public List getAmsStoragePrgRels(
			String stglobalid,
			String stdirglobalid,
			String prglobalid,
			String ftypeglobalid,
			String filename,
			String progfileid,
			String filepath
			);
	
	/**
	 * 根据存储等级查询所有文件及文件详细信息
	 * @param storageClassId 存储体等级ID
	 * @return List<Object[3]>
	 * object[0]: AmsStoragePrgRel
	 * object[1]: ProgramFiles
	 * object[2]: AmsStorageDir
	 */
	public List<?> queryAllFileDetail(String storageId, 
			Date startInputTime, Date endInputTime);
	
	public List<?> queryAllFileDetail(String storageId);
	
	/**
	 * 根据存储体和位置表中文件名, 查询该存储体中文件位置记录
	 * @param storageId 存储体ID
	 * @param fileName 文件名
	 * @return 返回该存储体中文件位置记录
	 */
	public List<?> queryOtherStorageFileByStorageIdAndFileName(
			String storageId, String fileName);
	
	/**
	 * 根据节目ID查询节目文件所在的路径
	 * @param programInfoID
	 * @return List<Object[4]>
	 * object[0]: AmsStorage
	 * object[1]: AmsStorageDir
	 * object[2]: AmsStoragePrgRel
	 * object[3]: ProgramFiles
	 */
	public List<?> queryFilePathByProgramInfoID(String programFileID);
	
	/**
	 * 根据节目包ID和存储等级Code查询节目包存储位置信息
	 * @param progPackageId 节目包ID
	 * @param storageId 存储体ID
	 * @return List<Object[3]>
	 * object[0]: AmsStorage
	 * object[1]: AmsStorageDir
	 * object[2]: AmsStoragePrgRel
	 */
	public List<?> queryProgPackageStorageInfo(String progPackageId, 
			String storageId);

	/**
	 * 根据文件ID, 文件Code和存储等级Code查询指定存储等级上的节目包主文件位置信息
	 * @param programFileId 文件ID
	 * @param fileCode 文件Code
	 * @param classCode 存储等级Code
	 * @return Object[]
	 * object[0]: AmsStorage
	 * object[1]: AmsStorageDir
	 * object[2]: AmsStoragePrgRel
	 */
	public Object[] queryByProgPackageAndClassCode(String programFileId, 
			String fileCode, String classCode);
	
	/**
	 * 根据文件路径和文件名, 查询位置表信息, 用于生成预告JS时判断位置表记录是否已生成
	 * @param filePath 文件路径
	 * @param fileName 文件名
	 * @return 返回文件的位置信息
	 */
	public AmsStoragePrgRel queryByFilePathAndFileName(String filePath, String fileName);
	
	/**
	 * 根据节目包ID, 存储等级Code, 文件ID查询文件路径和文件位置表ID
	 * 并将文件和位置表记录删除, 同时修改节目包状态为缓存库
	 * @param progPackageId 节目包ID
	 * @param classCode 存储等级Code
	 * @param programFileIds 文件ID集合
	 * @return 删除是否成功
	 */
	public boolean deleteOnlineFile(String progPackageId, String classCode,
			List<String> programFileIds);
	
	/**
	 * 根据节目包ID, 存储体ID查询位置表记录
	 * @param progPackageId 节目包ID
	 * @param storageId 存储体ID
	 * @return 返回位置表集合
	 */
	public List<AmsStoragePrgRel> queryAmsStoragePrgRels(String progPackageId, String storageId);
	
	/**
	 * andy
	 * 1.2	20101105 9:52	根据文件id、存储等级id，查询文件位置表记录
	 * @param progfileid
	 * @param stclasscode
	 * @return
	 */
	public List<AmsStoragePrgRel> getAmsStoragePrgRelsByProgfileidStclasscode(
			String progfileid, String stglobalid);
}
