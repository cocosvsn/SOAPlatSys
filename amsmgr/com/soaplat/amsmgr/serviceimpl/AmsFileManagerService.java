/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.amsmgr.serviceimpl;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.log4j.Logger;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageClass;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStorageHistory;
import com.soaplat.amsmgr.bean.AmsStoragePolicy;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.bean.AmsStoragePrgRelBack;
import com.soaplat.amsmgr.common.GetAmsPkImpl;
import com.soaplat.amsmgr.manageriface.IAmsStorageClassManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageHistoryManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePolicyManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelBackManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.amsmgr.vo.FileManageVo;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.common.PropertyUtil;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

/**
 * Title 		: 文件删除业务类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-18 上午11:02:37
 */
public class AmsFileManagerService {
	private static Logger logger = Logger.getLogger("Cms");
	private IAmsStorageManager amsStorageManager;
	private IAmsStorageClassManager amsStorageClassManager;
	private IAmsStoragePrgRelManager amsStoragePrgRelManager;
	private IAmsStorageHistoryManager amsStorageHistoryManager;
	private IAmsStoragePolicyManager amsStoragePolicyManager;
	private IAmsStoragePrgRelBackManager amsStoragePrgRelBackManager;
	private IProgPackageManager progPackageManager;
	private IProgListDetailManager progListDetailManager;
	private IProgramFilesManager programFilesManager;
	
	public AmsFileManagerService() {
		this.amsStorageClassManager = (IAmsStorageClassManager) ApplicationContextHolder.webApplicationContext.getBean("amsstorageclassManager");
		this.amsStorageHistoryManager = (IAmsStorageHistoryManager) ApplicationContextHolder.webApplicationContext.getBean("amsStorageHistoryManager");
		this.amsStorageManager = (IAmsStorageManager) ApplicationContextHolder.webApplicationContext.getBean("amsstorageManager");
		this.amsStoragePolicyManager = (IAmsStoragePolicyManager) ApplicationContextHolder.webApplicationContext.getBean("amsStoragePolicyManager");
		this.amsStoragePrgRelBackManager = (IAmsStoragePrgRelBackManager) ApplicationContextHolder.webApplicationContext.getBean("amsStoragePrgRelBackManager");
		this.amsStoragePrgRelManager = (IAmsStoragePrgRelManager) ApplicationContextHolder.webApplicationContext.getBean("amsstorageprgrelManager");
		this.progPackageManager = (IProgPackageManager) ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
		this.progListDetailManager = (IProgListDetailManager) ApplicationContextHolder.webApplicationContext.getBean("progListDetailManager");
		this.programFilesManager = (IProgramFilesManager) ApplicationContextHolder.webApplicationContext.getBean("programFilesManager");
	}
	
	/**
	 * 查看存储的文件信息
	 * @param storageClass 存储体等级
	 * @param startTime	开始日期 格式:2010-01-01
	 * @param endTime 结束日期 格式同上
	 * @param viewType 显示类别(正常, 冗余文件, 冗余记录)
	 * @param filtType 列表筛选 (暂不使用, 由前台控制)
	 * @return CmsResultDto
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto queryAllFiles(
				String storageClassId, 
				String startTime, 
				String endTime,
				String viewType 
				//String filtType
	) {
		logger.debug("获取需要显示的文件列表,Ams -> AmsFileManagerService -> queryAllFiles");
		List<FileManageVo> list = new ArrayList<FileManageVo>(0);
		
		if ("0".equals(viewType)) { // 冗余文件
			Object[] objects = this.getStorageFileListByStorageClass(storageClassId);
			AmsStorage amsStorage = (AmsStorage) objects[1];
			AmsStorageClass amsStorageClass  = (AmsStorageClass) objects[2];
			String storageClassPath = (String) objects[3];
			String storageClassName = amsStorageClass.getStclassname();

			List<SmbFile> fileList = (List<SmbFile>) objects[0];
			logger.debug("存储等级上文件记录数: " + fileList.size());
			
			list = this.getRedundancyFiles(fileList, amsStorage, storageClassId, 
					storageClassName, storageClassPath);
			logger.debug("存储等级上冗余文件记录数: " + list.size());
		} else {
			List<FileManageVo> fileManageVos = this.getDBdata(storageClassId, startTime, endTime);
			logger.debug("查询的数据库文件记录数: " + fileManageVos.size());
			
			list = this.getFileManageVosByViewType(fileManageVos, viewType);
			logger.debug("根据显示类型取得的记录数: " + list.size());
		}
		
		/**
		 * 标记每一条记录的可删除状态
		 */
		list = this.isCanDelete(list, viewType);
		
		CmsResultDto cmsResultDto = new CmsResultDto();
		if (list == null) {
			cmsResultDto.setResultCode(1l);
			cmsResultDto.setErrorMessage("显示状态有误!");
		} else {
			cmsResultDto.setResultCode(0l);
			cmsResultDto.setResultObject(list);
		}
		
		logger.debug("查看存储的文件信息返回");
		return cmsResultDto;
	}
	
	/**
	 * 查询所有存储体等级, 以及显示类别信息
	 * @return CmsResultDto
	 * CmsResultDto.resultCode: 0=成功, 1=失败
	 * CmsResultDto.resultObject: Object[2]
	 * Object[0]: List<AmsStorageClass>
	 * Object[1]: List<Object[2] {String, String}>
	 */
	public CmsResultDto queryAllStorage() {
		CmsResultDto cmsResultDto = new CmsResultDto();
		try {
			List<?> amsStorageClassList = this.amsStorageClassManager.queryAllValidateStorage();
			List<Object[]> viewCategorysList = new ArrayList<Object[]>(3);
			Map<String, String> viewCategoryMap = PropertyUtil.getProperties("./viewCategory.properties");
			List<String> list = new ArrayList<String>(viewCategoryMap.keySet());
			Collections.sort(list);
			Collections.reverse(list);
			for (Object string : list) {
				viewCategorysList.add(new Object[] {string, viewCategoryMap.get(string)});
			}
			
			//Collections.sort(viewCategorysList, null);
			
			cmsResultDto.setResultCode(0l);
			cmsResultDto.setResultObject(new Object[] {amsStorageClassList, viewCategorysList});
		} catch (Exception e) {
			cmsResultDto.setResultCode(1l);
			cmsResultDto.setErrorMessage("存储等级查询失败!");
		}
		return cmsResultDto;
	}
	
	/**
	 * 根据存储体等级ID, 获取该存储体等级位置上的所有文件列表
	 * @param storageClassId 存储体等级ID
	 * @return 返回共享文件列表Object[]
	 * object[0]: List<SmbFile>,
	 * object[1]: AmsStorage,
	 * object[2]: AmsStorageClass
	 * object[3]: String storageClassPath
	 */
	@SuppressWarnings("unchecked")
	private Object[] getStorageFileListByStorageClass(String storageClassId) {
		logger.debug("Ams -> AmsFileManagerService -> getStorageFileListByStorageClass");
		List<Object[]> storagelist = (List<Object[]>) this.amsStorageManager
				.queryByStorageClassId(storageClassId);
		Object[] objects = storagelist.get(0);
		AmsStorage amsStorage = (AmsStorage) objects[0];
		AmsStorageClass amsStorageClass = (AmsStorageClass) objects[1];
		
		logger.debug("获取存储体等级目录路径.");
		// 获取存储体等级目录
		StringBuffer storageClassPathBuffer = new StringBuffer(amsStorage.getStorageaccstype());
		storageClassPathBuffer.append("://");
		storageClassPathBuffer.append(amsStorage.getLoginname());
		storageClassPathBuffer.append(":");
		storageClassPathBuffer.append(null == amsStorage.getLoginpwd() ? "" : amsStorage.getLoginpwd());
		storageClassPathBuffer.append("@");
		storageClassPathBuffer.append(amsStorage.getStorageip());
		storageClassPathBuffer.append(amsStorage.getMappath().startsWith("/") 
				? amsStorage.getMappath() : "/" + amsStorage.getMappath());
		logger.debug("存储体等级路径" + storageClassPathBuffer.toString());
		
		FileOperationImpl fileOperationImpl = new FileOperationImpl();
		fileOperationImpl.getSmbFiles(storageClassPathBuffer.toString());
		
		logger.debug("返回存储等级上所有共享文件列表!");
		return new Object[] {fileOperationImpl.getSmbFileList(), amsStorage, 
				amsStorageClass, storageClassPathBuffer.toString()};
	}
	
	/**
	 * 从数据库中获取文件记录信息, 根据存储等级ID, 日期区间获取 
	 * @param storageClassId 存储体等级ID
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 返回该存储等级库中文件列表List<FileManageVo>
	 */
	@SuppressWarnings("unchecked")
	private List<FileManageVo> getDBdata(String storageClassId, String startTime, String endTime) {
		logger.debug("查看存储体等级上的所有文件信息.  getDBdata");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Object[]> storagelist = (List<Object[]>) this.amsStorageManager
				.queryByStorageClassId(storageClassId);
		Object[] objects = storagelist.get(0);
		AmsStorage amsStorage = (AmsStorage) objects[0];
		AmsStorageClass amsStorageClass = (AmsStorageClass) objects[1];

		List<Object[]> fileList = null;
		try {
			fileList = (List<Object[]>) this.amsStoragePrgRelManager
					.queryAllFileDetail(amsStorage.getStglobalid(), 
							dateFormat.parse(startTime), dateFormat.parse(endTime));
		} catch (ParseException e) {
			logger.error("日期数据转换出错! " + e);
		}
		
		logger.debug("获取存储体等级目录路径.");
		// 获取存储体等级目录
		StringBuffer storageClassPathBuffer = new StringBuffer(amsStorage.getStorageaccstype());
		storageClassPathBuffer.append("://");
		storageClassPathBuffer.append(amsStorage.getLoginname());
		storageClassPathBuffer.append(":");
		storageClassPathBuffer.append(null == amsStorage.getLoginpwd() 
				? "" : amsStorage.getLoginpwd());
		storageClassPathBuffer.append("@");
		storageClassPathBuffer.append(amsStorage.getStorageip().endsWith("/")
				? amsStorage.getStorageip() : amsStorage.getStorageip() + "/");
		storageClassPathBuffer.append(amsStorage.getMappath().endsWith("/")
				? amsStorage.getMappath() : amsStorage.getMappath() + "/");
		
		List<FileManageVo> list = new ArrayList<FileManageVo>();
		for (Object[] objects2 : fileList) {
			AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) objects2[0];
			ProgramFiles programFiles = (ProgramFiles) objects2[1];
			AmsStorageDir amsStorageDir = (AmsStorageDir) objects2[2];
			
			/**
			 * 获取节目包信息
			 */
			List<Object[]> progPackageList = (List<Object[]>) this.progPackageManager
					.queryByProgramFileId(programFiles.getProgfileid());
			String filePath = amsStoragePrgRel.getFilepath().replaceAll("\\\\", "/");
			String dirPath = amsStorageDir.getStoragedirname().replaceAll("\\\\", "/");
			FileOperationImpl fileOperationImpl = new FileOperationImpl();
			FileManageVo fileManageVo = new FileManageVo();
			fileManageVo.setFileCode(programFiles.getFilecode());
			fileManageVo.setFileCreateTime(amsStoragePrgRel.getInputtime());
			fileManageVo.setFileName(programFiles.getFilename());
			fileManageVo.setFilePath(fileOperationImpl.checkPathFormatRear(dirPath, '/') +
					fileOperationImpl.checkPathFormatRear(filePath, '/'));
			fileManageVo.setFileSize(programFiles.getContentfilesize());
			fileManageVo.setSmbFilePref(storageClassPathBuffer.toString());
			
			fileManageVo.setRelId(amsStoragePrgRel.getRelid());
			fileManageVo.setStorageClassId(storageClassId);
			fileManageVo.setStorageClassName(amsStorageClass.getStclassname());
			fileManageVo.setStorageId(amsStorage.getStglobalid());
			fileManageVo.setStorageDirId(amsStorageDir.getStdirglobalid());
			
			/**
			 * 判断节目文件是否属于某节目包
			 */
			if (progPackageList.size() > 0) {
				Object[] packages = progPackageList.get(0);
				
				logger.debug("节目文件:" + programFiles.getFilename() + " 属于节目包:" + packages[1]);
				
				fileManageVo.setPkgId((String) packages[0]);
				fileManageVo.setPkgName((String) packages[1]);
				fileManageVo.setPkgStateCode((Long) packages[2]);
				fileManageVo.setPkgDealStateCode((Long) packages[3]);
				fileManageVo.setPkgStateName(FileOperationImpl
						.getState((Long) packages[2]));
				fileManageVo.setPkgDealStatename(FileOperationImpl
						.getDealState((Long) packages[3]));
				
				/**
				 * 获取节目包上下线日期信息
				 */
				Object[] progListDetails = this.progListDetailManager
						.queryProgListDetailByProgramFileId((String) packages[0]);
				if (progListDetails != null) {
					logger.debug("取得节目包上线日期为:" + progListDetails[1] + " ,下线日期为:" + progListDetails[2]);
					
					fileManageVo.setProgListDetailId((String) progListDetails[0]);
					fileManageVo.setUpLineDate((Date)  progListDetails[1]);
					fileManageVo.setDownLineDate((Date) progListDetails[2]);
				}
			}
			list.add(fileManageVo);
		}
		logger.debug("获取节目文件信息返回!");
		return list;
	}
	
	/**
	 * 根据显示类型返回, 数据库记录的方件是否存在
	 * @param fileManageVos 
	 * @param viewType
	 * @return 根据显示类型返回.  1: 返回文件和记录同时存在, 2: 返回记录存在而文件不存在
	 */
	private List<FileManageVo> getFileManageVosByViewType(
			List<FileManageVo> fileManageVos, String viewType) {
		List<FileManageVo> normalFileManageVos = new ArrayList<FileManageVo>();
		List<FileManageVo> redundancyFileManageVos = new ArrayList<FileManageVo>();
		
		for (FileManageVo fileManageVo : fileManageVos) {
			String file = fileManageVo.getSmbFilePref() 
					+ (fileManageVo.getFilePath().endsWith("/")
							? fileManageVo.getFilePath() : fileManageVo.getFilePath() + "/") 
					+ fileManageVo.getFileName();
			String viewPath = file.substring(file.indexOf("@") + 1);
			fileManageVo.setViewPath(viewPath);			// 设置冗余数据, 正常数据的显示路径
			
			logger.info("存储上文件路径:" + file + "  ~~~  显示路径: " + viewPath);
			SmbFile smbFile;
			try {
				smbFile = new SmbFile(file);
				if (smbFile.exists()) {
					normalFileManageVos.add(fileManageVo);
				} else {
					redundancyFileManageVos.add(fileManageVo);
				}
			} catch (MalformedURLException e) {
				logger.error("getFileManageVosByViewType ->共享盘连接错误! " + e);
			} catch (SmbException e) {
				logger.error("getFileManageVosByViewType ->共享文件读取错误! " + e);
			}
		}
		
		if ("1".equals(viewType)) {
			return normalFileManageVos;
		} else if ("2".equals(viewType)) {
			return redundancyFileManageVos;
		} else {
			return null;
		}
	}

	/**
	 * 从文件列表中获取冗余文件列表
	 * @param fileList 文件列表
	 * @param amsStorage 存储
	 * @param storageClassId 存储等级ID
	 * @return 返回冗余文件列表
	 */
	@SuppressWarnings("unchecked")
	private List<FileManageVo> getRedundancyFiles(List<SmbFile> fileList, 
			AmsStorage amsStorage, String storageClassId, 
			String storageClassName, String storageClassPath) {
		List<FileManageVo> list = new ArrayList<FileManageVo>();
		FileOperationImpl fileOperationImpl = new FileOperationImpl();
		
		List<Object[]> relList = (List<Object[]>) this.amsStoragePrgRelManager
				.queryAllFileDetail(amsStorage.getStglobalid());
		logger.debug("存储上文件数" + relList.size());
		
		for (SmbFile smbFile : fileList) {
			boolean flag = true;
			String fileName = -1 == smbFile.getName().indexOf(".") 
					? smbFile.getName() : smbFile.getName().substring(0, 
							smbFile.getName().lastIndexOf("."));
			logger.debug("存储上文件名: " + smbFile.getName() + " ---->  去掉扩展名后的主键: " + fileName);
			
			
			/**
			 * 循环判断文件是否存在于该等级存储上
			 */
			for (Object[] objects : relList) {
				AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) objects[0];
				AmsStorageDir amsStorageDir = (AmsStorageDir) objects[2];
				String relPath = amsStoragePrgRel.getFilepath().replaceAll("\\\\", "/");
				String dirPath = amsStorageDir.getStoragedirname().replace("\\\\", "/");
				String path = storageClassPath 
						+ fileOperationImpl.checkPathFormatRear(dirPath, '/')
						+ fileOperationImpl .checkPathFormatRear(relPath, '/') 
						+ amsStoragePrgRel.getFilename();
//				logger.debug("存储上文件路径: " + smbFile.getPath() + 
//						"-------\n--~~> 数据库文件路径: " + path);
				
				if (smbFile.getName().equals(amsStoragePrgRel.getFilename()) 
//						|| fileName.equals(amsStoragePrgRel.getProgfileid())
						&& smbFile.getPath().equals(path)) {
					logger.debug("位置表记录--文件名: " + amsStoragePrgRel.getFilename() 
							+ "\n\t文件路径: " + path + "\n~~~~~~~~存在, 不是于冗余文件.");
					flag = false;
					break;
				}
			}
			logger.debug("冗余文件: ~~~~~~~~~~~~~~~----------------------------------------");
			
			
			// 不存在于该等级存储上则为冗余文件
			if (flag) {
//				AmsStorageClass amsStorageClass = (AmsStorageClass) 
//						this.amsStorageClassManager.getById(storageClassId);
				FileManageVo fileManageVo = new FileManageVo();
				fileManageVo.setFileName(smbFile.getName());
				fileManageVo.setFilePath(smbFile.getPath());
				fileManageVo.setStorageClassId(storageClassId);
				fileManageVo.setStorageClassName(storageClassName);
				fileManageVo.setStorageId(amsStorage.getStglobalid());
				fileManageVo.setViewPath(
						smbFile.getPath().substring(
								smbFile.getPath().indexOf("@") + 1));		// 显示路径
				fileManageVo.setCanDelete(true);
				try {
					fileManageVo.setFileSize(String.valueOf(smbFile.length()));
					fileManageVo.setFileCreateTime(new Date(smbFile.createTime()));
				} catch (SmbException e) {
					logger.error("读取共享文件大小出错!");
				}
				
				ProgramFiles programFiles = (ProgramFiles) this.programFilesManager.getById(fileName);
				/**
				 * 判断该文件数据库记录是否存在 
				 */
				if (programFiles != null) {
					// 存在于数据库记录中
					List<Object[]> progPackages = (List<Object[]>) 
							this.progPackageManager.queryByProgramFileId(fileName);
					// 判断该记录是否被节目包包含
					if (progPackages.size() > 0) {
						//待确认.. 节目包与节目文件关系是否是1对1
//						boolean progPackageFlag = false;
//						for (Object[] objects : progPackages) {
//							long dealState = Long.parseLong((String) objects[3]);		// 节目包处理状态
//							long state = Long.parseLong((String) objects[2]);			// 节目包状态
//							Object[] progListDetail = this.progListDetailManager
//									.queryProgListDetailByProgramFileId((String) objects[0]);
//						}
						Object[] objects = progPackages.get(0);
						Object[] progListDetail = this.progListDetailManager
								.queryProgListDetailByProgramFileId((String) objects[0]);
						
						fileManageVo.setPkgId((String) objects[0]);
						fileManageVo.setPkgName((String) objects[1]);
						fileManageVo.setPkgStateCode((Long) objects[2]);
						fileManageVo.setPkgStateName(FileOperationImpl
								.getState((Long) objects[2]));
						fileManageVo.setPkgDealStateCode((Long) objects[3]);
						fileManageVo.setPkgDealStatename(FileOperationImpl
								.getDealState((Long) objects[3]));
						fileManageVo.setDownLineDate((Date) progListDetail[2]);
						fileManageVo.setUpLineDate((Date) progListDetail[1]);
					}
				}
				
				list.add(fileManageVo);
			}
		}
		return list;
	}
	
	/**
	 * 判断存储等级上的文件是否可删除
	 * @param fileManangeList 存储等级文件列表
	 * @param viewType 显示类别
	 * @return 
	 */
	private List<FileManageVo> isCanDelete(List<FileManageVo> fileManangeList, String viewType) {
		logger.debug("判断数据库存储等级上文件是否可删除! 总记录数(包含正常和冗余数据): " + fileManangeList.size());
		if (1 > fileManangeList.size()) {
			return fileManangeList;
		}
		
		String storageClassID = fileManangeList.get(0).getStorageClassId();
		List<?> list = null;
		try {
			list = this.amsStoragePolicyManager.queryByStorageClassId(storageClassID);
			logger.debug("该等级的文件删除策略记录数: " + list.size());
		} catch (Exception e) {
			logger.error("查询该等级的文件删除策略失败: " + e);
		}
		
		AmsStoragePolicy amsStoragePolicy = (AmsStoragePolicy) (0 < list.size() ? list.get(0) : null);
		int day = (int) (null == amsStoragePolicy ? 0 : amsStoragePolicy.getFollowing());
		logger.debug("获得策略时间偏移天数");
		
		Calendar calendar = Calendar.getInstance();
		
		if("1".equals(viewType)) { // 正常文件记录
			for (FileManageVo fileManageVo : fileManangeList) {
				if (null == fileManageVo.getPkgId()) {
					logger.debug("isCanDelete  --> 文件未被节目包包含!");
					fileManageVo.setCanDelete(true);
				} else {
					logger.debug("isCanDelete  --> 文件: " + fileManageVo.getViewPath() 
							+ "\n\t被节目包: " + fileManageVo.getPkgName() + " 包含!");
					
					calendar.setTime(fileManageVo.getDownLineDate());
					calendar.add(Calendar.DAY_OF_MONTH, day);
					
					if ((0 == fileManageVo.getPkgDealStateCode() 
									|| -2 == fileManageVo.getPkgDealStateCode())// 当前节目包处理状态为"未处理"或"待更新"状态
							&& (!this.isGreatStorageClass(fileManageVo.getPkgStateCode(), 
									fileManageVo.getStorageClassName()))		// 节目包状态 <= 当前选择存储等级
							&& (null != fileManageVo.getDownLineDate())
							&& (new Date().getTime() >= calendar.getTimeInMillis())				// 现在时间 >= 节目包下线时间加上策略偏移时间
							&& (!this.existGreatStorageClass(					// 判断文件存放位置表，在>当前选择存储等级中没有该节目包的文件记录
									fileManageVo.getStorageClassName(), 
									fileManageVo.getFileName()))) {
						//缺少策略表条件待确认..........
						logger.debug("isCanDelete  --> 正常文件记录, 文件路径: " + fileManageVo.getViewPath());
						fileManageVo.setCanDelete(true);
					}
				}
			}
		} else if ("2".equals(viewType)) { // 冗余记录
			for (FileManageVo fileManageVo : fileManangeList) {
				if (null == fileManageVo.getPkgId()) {
					logger.debug("isCanDelete  ~~> 文件未被节目包包含!");
					fileManageVo.setCanDelete(true);
				} else {
					logger.debug("isCanDelete  ~~> 文件: " + fileManageVo.getViewPath() 
							+ "\n\t被节目包: " + fileManageVo.getPkgName() + " 包含!");
					
					calendar.setTime(fileManageVo.getDownLineDate());
					calendar.add(Calendar.DAY_OF_MONTH, day);
					
					if ((0 == fileManageVo.getPkgDealStateCode()
									|| -2 == fileManageVo.getPkgDealStateCode())// 当前节目包处理状态为"未处理"状态
							&& (!this.isGreatStorageClass(fileManageVo.getPkgStateCode(), 
									fileManageVo.getStorageClassName()))		// 节目包状态 <= 当前选择存储等级
							&& (null != fileManageVo.getDownLineDate())
							&& (new Date().getTime() >= calendar.getTimeInMillis())				// 现在时间 >= 节目包下线时间加上策略偏移时间
							&& (!this.existGreatStorageClass(					// 判断文件存放位置表，在>当前选择存储等级中没有该节目包的文件记录
									fileManageVo.getStorageClassName(), 
									fileManageVo.getFileName()))) {
						
						//缺少策略表条件待确认..........
						logger.debug("isCanDelete  ~~> 冗余文件记录, 文件路径: " + fileManageVo.getViewPath());
						fileManageVo.setCanDelete(true);
					}
				}
			}
		//} else if ("0".equals(viewType)) {
			
		}
		return fileManangeList;
	}
	
	/**
	 * 删除文件
	 * @param list List<FileManageVo>
	 * @param inputManId 操作人员ID
	 * @param remark 备注
	 * @return 返回删除是否成功结果
	 */
	public CmsResultDto deleteFiles(List<FileManageVo> list, String viewType,
			String inputManId, String remark) {
		logger.debug("删除文件 Ams -> AmsFileManagerService -> deleteFiles");
		CmsResultDto cmsResultDto = new CmsResultDto();
		List<FileManageVo> delFailure = new ArrayList<FileManageVo>();
		
		logger.debug("删除前再做一次有效数据验证!");
		List<FileManageVo> validateList = this.isCanDelete(list, viewType);
		for (FileManageVo validateFileManageVo : validateList) {
			if (!validateFileManageVo.isCanDelete()) {
				cmsResultDto.setResultCode(1l);
				cmsResultDto.setErrorMessage("当前数据过期, 请刷新界面再进行删除操作!");
				logger.debug("当前数据过期, 请刷新界面再进行删除操作!");
				return cmsResultDto;
			}
		}
		
		for (FileManageVo fileManageVo : list) {
			if (null != fileManageVo.getRelId()) {
				AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) 
						this.amsStoragePrgRelManager.getById(fileManageVo.getRelId());
				amsStoragePrgRel.setIsDel("是");
				this.amsStoragePrgRelManager.save(amsStoragePrgRel);
			}
			
			String file = null;		// 文件绝对路径
			if (-1 == fileManageVo.getFilePath().indexOf("smb://")) {
				file = fileManageVo.getSmbFilePref() + fileManageVo.getFilePath() 
						+ fileManageVo.getFileName();
			} else {
				file = fileManageVo.getFilePath();
			}
			logger.debug("SmbFile Path :" + file);
			
			try {
				SmbFile smbFile = new SmbFile(file);
				if (smbFile.exists()) {
					logger.debug("---------------------------------------------------------");
					logger.debug("\t\t操作者: " + inputManId);
					logger.debug("\t\t节目包ID: " + fileManageVo.getPkgId());
					logger.debug("\t\t节目包名称: " + fileManageVo.getPkgName());
					logger.debug("\t\t删除文件:" + file);
					smbFile.delete();
				}
				
				this.delFileAddLog(true, fileManageVo, inputManId, remark, viewType);
				
				logger.debug("deleteFiles -> 删除文件成功:" + file);
			} catch (MalformedURLException e) {
				logger.error("deleteFiles ->共享盘连接错误! " + e);
				this.delFileAddLog(false, fileManageVo, inputManId, remark, viewType);
				delFailure.add(fileManageVo);
			} catch (SmbException e) {
				logger.error("deleteFiles ->共享文件读取错误! " + e);
				this.delFileAddLog(false, fileManageVo, inputManId, remark, viewType);
				delFailure.add(fileManageVo);
			}
		}
		
		if (0 < delFailure.size()) {
			cmsResultDto.setResultCode(1l);
			cmsResultDto.setResultObject(delFailure);
		} else {
			cmsResultDto.setResultCode(0l);
			cmsResultDto.setErrorMessage("删除全部成功!");
		}
		return cmsResultDto;
	}
	
	/**
	 * 判断当前节目包状态是否大于当前选择存储等级
	 * @param state 当前节目包状态
	 * @param storageClassName 当前选择存储等级
	 * @return 如果节目包状态是否大于当前选择存储等级
	 */
	private boolean isGreatStorageClass(long state, String storageClassName) {
			logger.debug("北京系统:当前节目包状态是否大于当前选择存储等级");
			return state > this.getStorageClassCode(storageClassName);
	}
	
	/**
	 * 文件删除是否成功后调用的接口
	 * @param isDeleted 文件删除是否成功
	 * @return 
	 */
	public void delFileAddLog(boolean isDeleted, FileManageVo fileManageVo,
			String inputManId, String remark, String viewType) {
		logger.debug("删除文件添加日志 Ams -> AmsFileManagerService -> delFileAddLog");
		String currMaxID = null;
		try {
			currMaxID = this.amsStorageHistoryManager.getCurrMaxID();
		} catch (Exception e) {
			logger.error("获取当前最大主键ID值失败: " + e);
		}
		GetAmsPkImpl getAmsPkImpl = new GetAmsPkImpl();
		logger.debug("历史记录表当前最大主键ID值: " + currMaxID);
		
		AmsStorageHistory amsStorageHistory = new AmsStorageHistory();
		amsStorageHistory.setId(getAmsPkImpl.GetTablePK("AMSSTORAGEHISTORY", currMaxID));
		amsStorageHistory.setFileName(fileManageVo.getFileName());
		amsStorageHistory.setInputManId(inputManId);
		amsStorageHistory.setInputTime(new Date());
		amsStorageHistory.setOptType(30l);
		amsStorageHistory.setSgId(fileManageVo.getStorageId());
		amsStorageHistory.setScgId(fileManageVo.getStorageClassId());
		amsStorageHistory.setSdgId(fileManageVo.getStorageDirId());
		amsStorageHistory.setProgFileId(-1 == fileManageVo.getFileName().indexOf(".") 
				? fileManageVo.getFileName() : fileManageVo.getFileName().substring(0, 
						fileManageVo.getFileName().lastIndexOf(".")));
		amsStorageHistory.setProgId(fileManageVo.getPkgId());
		amsStorageHistory.setOptObjectId(fileManageVo.getRelId());
		
		if (isDeleted) {
			// 成功删除
			logger.debug("实体文件删除成功~");
			amsStorageHistory.setOptResult(0l);
			if (null == fileManageVo.getRelId()) {
				logger.debug("删除成功-->实体文件不被位置表包含, 直接添加删除历史记录~~");
				this.amsStorageHistoryManager.save(amsStorageHistory);
			} else {
				logger.debug("删除成功-->实体文件被位置表包含, 添加位置表备份, 删除原位置表记录, 添加文件删除历史记录~~");
				AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) 
						this.amsStoragePrgRelManager.getById(fileManageVo.getRelId());
				AmsStoragePrgRelBack amsStoragePrgRelBack = new AmsStoragePrgRelBack();
				amsStoragePrgRelBack.setFileName(amsStoragePrgRel.getFilename());
				amsStoragePrgRelBack.setFilePath(amsStoragePrgRel.getFilepath());
				amsStoragePrgRelBack.setFileTypeGlobalId(amsStoragePrgRel.getFtypeglobalid());
				amsStoragePrgRelBack.setFileUpdTime(amsStoragePrgRel.getFiledate());
				amsStoragePrgRelBack.setFileUploadTime(amsStoragePrgRel.getUploadtime());
				amsStoragePrgRelBack.setInputManId(amsStoragePrgRel.getInputmanid());
				amsStoragePrgRelBack.setInputTime(amsStoragePrgRel.getInputtime());
				amsStoragePrgRelBack.setPgId(amsStoragePrgRel.getPrglobalid());
				amsStoragePrgRelBack.setProgFileId(amsStoragePrgRel.getProgfileid());
				amsStoragePrgRelBack.setRemak(amsStoragePrgRel.getRemark());
				amsStoragePrgRelBack.setSdgId(amsStoragePrgRel.getStdirglobalid());
				amsStoragePrgRelBack.setSgId(amsStoragePrgRel.getStglobalid());
				amsStoragePrgRelBack.setRelId(amsStoragePrgRel.getRelid());
				
				try {
					logger.debug("删除成功-->实体文件被位置表包含, 添加位置表备份记录..");
					this.amsStoragePrgRelBackManager.save(amsStoragePrgRelBack);
				} catch (Exception e) {
					logger.error(" 添加位置表备份记录失败: " + e);
				}
				
				try {
					logger.debug("删除成功-->实体文件被位置表包含, 删除原位置表记录..");
					this.amsStoragePrgRelManager.deleteById(fileManageVo.getRelId());
				} catch (Exception e) {
					logger.error("删除原位置表记录失败: " + e);
				}
				
				try {
					logger.debug("删除成功-->实体文件被位置表包含, 添加文件删除历史记录~~");
					this.amsStorageHistoryManager.save(amsStorageHistory);
				} catch (Exception e) {
					logger.error("添加文件删除历史记录失败: " + e);
				}
			}
			
			if (null != fileManageVo.getPkgId()) {
				logger.debug("删除成功 --> 文件被节目包包含, 修改节目包状态, 降一级! 节目包ID: " + fileManageVo.getPkgId());
				ProgPackage progPackage = (ProgPackage) this.progPackageManager
						.getById(fileManageVo.getPkgId());
				if (this.getStorageClassCode(fileManageVo.getStorageClassName()) 
						== progPackage.getState() && !"0".equals(viewType)) {
					logger.debug("当前存储等级与当前文件所在节目包状态相同!");
					
					CmsConfig cmsConfig = new CmsConfig();
					String systemArea = cmsConfig.getPropertyByName("SystemArea");
					if ("021".equals(systemArea)
							&& 9 == progPackage.getState()) {
						progPackage.setState(1l);
					} else {
						progPackage.setState(progPackage.getState() - 1);
					}
					this.progPackageManager.update(progPackage);
				}
			}
			
		} else {
			// 删除失败
			logger.debug("实体文件删除失败~~");
			amsStorageHistory.setOptResult(1l);
			if (null != fileManageVo.getRelId()) {
				logger.debug("删除失败-->实体文件不被位置表包含~~");
				AmsStoragePrgRel amsStoragePrgRel = (AmsStoragePrgRel) 
						this.amsStoragePrgRelManager.getById(fileManageVo.getRelId());
				amsStoragePrgRel.setIsDel("否");
				this.amsStoragePrgRelManager.save(amsStoragePrgRel);
			}
			
			logger.debug("添加删除失败历史记录~~");
			this.amsStorageHistoryManager.save(amsStorageHistory);
		}
	}
	
	/**
	 * 获取数据库中大于当前选择存储等级的存储体ID列表
	 * @param storageClassName 当前选择存储等级
	 * @return 返回数据库中大于当前选择的存储等级的存储体ID列表 List<String>
	 */
	@SuppressWarnings("unchecked")
	private List<String> getGreatStorageClassList(String storageClassName) {
		logger.debug("获取大于当前存储等级的所有存储等级的ID! getGreatStorageClassList");
		List<String> storageIdList = new ArrayList<String>(0);
		List<AmsStorageClass> storageList = this.amsStorageClassManager.findAll();
		
		long currStorageClassCode = this.getStorageClassCode(storageClassName);
		logger.debug("当前存储等级Code: " + currStorageClassCode + "------------------------------->");
		
		for (AmsStorageClass amsStorageClass : storageList) {
			long dbStorageClassCode = this.getStorageClassCode(
					amsStorageClass.getStclassname());
			logger.debug("数据存储等级Code: " + dbStorageClassCode);
			if (dbStorageClassCode > currStorageClassCode) {
				logger.debug("数据库当前存储等级 大于 当前选择存储等级 !");
				//storageClassIdList.add(amsStorageClass.getStclassglobalid());
				List<Object[]> list = (List<Object[]>) this.amsStorageManager
						.queryByStorageClassId(amsStorageClass.getStclassglobalid());
				for (Object[] objects : list) {
					AmsStorage amsStorage = (AmsStorage) objects[0];
					storageIdList.add(amsStorage.getStglobalid());
				}
			}
		}
		
		return storageIdList;
	} 
	
	/**
	 * 根据存储体等级名称, 取得该存储体等级代号 <br />
	 * @param storageClassName 存储体等级名称
	 * @return 返回该存储体代号
	 */
	public long getStorageClassCode(String storageClassName) {
		CmsConfig cmsConfig = new CmsConfig();
		String systemArea = cmsConfig.getPropertyByName("SystemArea");
		long currStgClassState = Long.MAX_VALUE;
		
		if ("010".equals(systemArea)) {
			//待确认..........
			if ("播出库".equals(storageClassName)) {
				currStgClassState = 3;
			} else if ("加扰库".equals(storageClassName)) {
				currStgClassState = 2;
			} else if ("缓存库".equals(storageClassName)) {
				currStgClassState = 1;
			} else if ("导入区".equals(storageClassName)) {
				currStgClassState = 0;
			}
		} else if ("021".equals(systemArea)) {
			if("北京缓存库".equals(storageClassName)) {
				currStgClassState = 9;
			} else if("缓存库".equals(storageClassName)) {
				currStgClassState = 1;
			}
		}
		
		return currStgClassState;
	}

	/**
	 * 查看比当前存储体等级更高级的存储体上是否存在指定文件的记录
	 * @param storageClassName 当前选择存储体等级名称
	 * @param fileName 文件名
	 * @return 返回更高等级存储体上是否存在指定文件
	 */
	private boolean existGreatStorageClass(String storageClassName, String fileName) {
		List<String> storageIdList = this.getGreatStorageClassList(storageClassName);
		logger.debug("取得大于当前选择存储等级的存储体ID数: " + storageIdList.size());
		for (String string : storageIdList) {
			List<?> prgReList = this.amsStoragePrgRelManager
					.queryOtherStorageFileByStorageIdAndFileName(string, fileName);
			logger.debug("大于当前存储等级的存储体: " + string + " 上的位置表文件记录数: " + prgReList.size());
			if (prgReList.size() > 0) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public void test() {
//		CmsResultDto cmsResultDto = this.queryAllStorage();
//		Object[] objects = (Object[]) cmsResultDto.getResultObject();
//		List<AmsStorageClass> storageList = (List<AmsStorageClass>) objects[0];
//		for (AmsStorageClass amsStorage : storageList) {
//			System.out.println(amsStorage.getStclassglobalid() + ": " + amsStorage.getStclassname());
//		}
		
		
		//this.queryAllStorage();
//		Map<String, String> viewCategoryMap = PropertyUtil.getProperties("./viewCategory.properties");
//		Set<String> set = viewCategoryMap.keySet();
//		for (String string : set) {
//			System.out.println(string + ": " + viewCategoryMap.get(string));
//		}
		
		CmsResultDto cmsResultDto = this.queryAllFiles("20090903143135000381", "2010-01-01", "2010-09-01", "1");
		List<FileManageVo> list = (List<FileManageVo>) cmsResultDto.getResultObject();
		System.out.println("============================" + list.size());
		
		int i = 0;
		for (FileManageVo fileManageVo : list) {
			System.out.println(i ++ + " ~~~~~" + fileManageVo.getFileName() + ":" + fileManageVo.isCanDelete() + "\t" + fileManageVo.getSmbFilePref() + "\t" + fileManageVo.getPkgStateName() + "\n\t--" + fileManageVo.getFilePath());
		}
////		System.out.println(list.size());
//		List<FileManageVo> delList = new ArrayList<FileManageVo>();
//		delList.add(list.get(142));
//		//delList.add(list.get(137));
//		this.deleteFiles(delList, "222", "冗余数据, 就是要删!");
		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		List<?> list2 = null;
//		try {
//			list2 = this.amsStoragePrgRelManager.queryAllFileDetail("20090903143323000953", 
//					dateFormat.parse("2010-01-01"), dateFormat.parse("2010-06-01"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		System.out.println(list2.size());
		
//		String smbFilePath = "smb://administrator:1@172.23.19.117/Test/PPRP201001PPRP20100126180544000015.xml";
//		System.out.println(smbFilePath.substring(smbFilePath.indexOf("@") + 1));
//		System.out.println(this.getState(1));
		
//		String currMaxID = this.amsStorageHistoryManager.getCurrMaxID();
//		System.out.println(currMaxID);
//		System.out.println(new GetAmsPkImpl().GetTablePK("AMSSTORAGEHISTORY", currMaxID));
	}
}
