package com.soaplat.cmsmgr.manageriface;

import java.util.Date;
import java.util.List;

import com.cbs.cbsmgr.manageriface.IPpSrvPdtRelManager;
import com.cbs.cbsmgr.manageriface.IProductCategoryManager;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.manageriface.IAmsStorageClassManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.cmsmgr.bean.CmsService;
import com.soaplat.cmsmgr.bean.EncryptList;
import com.soaplat.cmsmgr.bean.PPColumnRel;
import com.soaplat.cmsmgr.bean.PackageFiles;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.PortalPackage;
import com.soaplat.cmsmgr.bean.PortalRoleOperRel;
import com.soaplat.cmsmgr.bean.ProgList;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.sysmgr.manageriface.IRoleManager;


public interface ICmsTransactionManager 
{
	//------------------------------------- CmsService ---------------------------------------------------
	

	// 保存服务与节目分类的配置关系记录 SrvProgClass dicts ProgramCategoryDto 20091107
	// 保存服务与栏目的配置关系记录 SrvColumn PortalColumn 20091107
	// 保存服务与产品的配置关系记录 SrvProduct ProductCategory 20091107
	public void saveAllSrvConfigBySrvid(
			ISrvProgClassManager srvProgClassManager, 
			ISrvColumnManager srvColumnManager,
			ISrvProductManager srvProductManager,
			ICmsServiceManager cmsServiceManager,
			List dicts, 
			List portalColumns, 
			List productCategories, 
			String srvid
			);
	
	// 删除服务
	public void deleteCmsService(
			ICmsServiceManager cmsServiceManager,
			ISrvProgClassManager srvProgClassManager,
			ISrvColumnManager srvColumnManager,
			ISrvProductManager srvProductManager,
			IProgSrvRelManager progSrvRelManager,
			String srvId
			);
	
	// 创建服务
	public CmsService saveCmsService(
			ICmsServiceManager cmsServiceManager,
			ISrvProgClassManager srvProgClassManager,
			ISrvColumnManager srvColumnManager,
			ISrvProductManager srvProductManager,
			IProgSrvRelManager progSrvRelManager,
			CmsService cmsService
			);
	
	// 加入节目包，到srvid，20091104
	public void saveProgPackagesToSrvId(
			ICmsServiceManager cmsServiceManager,
			IProgSrvRelManager progSrvRelManager,
			List progPackages, 
			String srvId
			);
	
	// 删除节目包，从srvid，20091104
	public void deleteProgPackagesFromSrvId(
			IProgSrvRelManager progSrvRelManager,
			List progPackages, 
			String srvId
			);
	
	
	//------------------------------------- ProgPackage ---------------------------------------------------
	
	// 根据节目包编号 productId ，删除节目包，
	// 同时删除 产品节目包关系：PpSrvPdtRel
	// 删除 栏目节目包关系：PPColumnRel
	// 删除 服务节目包关系：ProgSrvRel
	// 删除 节目包文件关系：PackageFiles
	// 删除 节目包节目关系：ProgPPRel
	public CmsResultDto deleteProgPackage(
			IProgPackageManager progPackageManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			IPPColumnRelManager pPColumnRelManager,
			IProgSrvRelManager progSrvRelManager,
			IPackageFilesManager packageFilesManager,
			IProgPPRelManager progPPRelManager,
			String productId
			);
	
	// 修改节目包所有信息，ProgPackage ProgSrvRel ProgPPRel PackageFiles
	public void updateProgPackage(
			IProgPackageManager progPackageManager,
			IProgPPRelManager progPPRelManager,
			IPackageFilesManager packageFilesManager,
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager,
			IProgSrvRelManager progSrvRelManager,
			IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager,
			ProgPackage progPackage, 
			List cmsServices, 
			List programs, 
			Long styleId
			);
	
	// 修改节目包节目、节目包文件关系：ProgPPRel、PackageFiles
	public void updateProgPPRelAndPackageFiles(
			IProgPPRelManager progPPRelManager,
			IPackageFilesManager packageFilesManager,
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager,
			List programs, 
			ProgPackage progPackage, 
			Long styleId
			);
	
	// save 服务节目包关系：ProgSrvRel
	public void saveProgSrvRel(
			IProgSrvRelManager progSrvRelManager,
			List cmsServices, 
			ProgPackage progPackage
			);
	
	// 20100120 14:10
	// save 服务节目包关系：ProgSrvRel，同时修改节目包与栏目 节目包与产品的关系
	public void saveProgSrvRelColumnRelProductCategoryRel(
			IProgPackageManager progPackageManager,
			IProgSrvRelManager progSrvRelManager,
			IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager,
			List cmsServices, 
			ProgPackage progPackage
			);
	
	// 创建（定义）节目包，输入节目包描述信息、节目类型、样式、节目分类、服务列表、节目列表、文件列表
	public ProgPackage saveProgPackage(
			IProgPackageManager progPackageManager,
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager,
			IPackageFilesManager packageFilesManager,
			IProgPPRelManager progPPRelManager,
			IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager,
			IProgSrvRelManager progSrvRelManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager,
			ProgPackage progPackage, 
			Long styleId, 
			List cmsServices, 
			List programs,
			List progFiles
			);
	
	// 保存xml文件记录，到文件表、ASM节目位置表、节目包文件表
	public AmsStoragePrgRel saveUploadFile(
			IProgramFilesManager programFilesManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IPackageFilesManager packageFilesManager,
			ProgramFiles programFiles,
			ProgPackage progPackage,
			AmsStorage amsStorage,
			AmsStorageDir amsStorageDir,
			String filepath,
			boolean changeFilename			// 是否需要修改文件名为文件记录id，用于xml和png
			);
	
	// 20100304 10:26
	// 修改节目包，包括节目包描述、节目包与服务产品栏目的关系、节目包与文件的关系、海报更新
	public CmsResultDto updateProgPackageAllInfo(
			IProgPackageManager progPackageManager,
			IProgSrvRelManager progSrvRelManager,
			IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager,
			IProgPPRelManager progPPRelManager,
			IPackageFilesManager packageFilesManager,
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgListDetailManager progListDetailManager,
			Boolean updateprogpackage,		// 修改节目包信息标识
			ProgPackage progPackage, 		// 节目包信息，原方法 modifyProgPPRelAndPackageFiles、modifyProgSrvRel、modifyProgPackage
			Boolean updatepackagefiles,		// 修改节目包节目文件关系标识
			List programs, 					// 节目列表，原方法 modifyProgPPRelAndPackageFiles
			Long styleId,					// 样式ID，原方法 modifyProgPPRelAndPackageFiles
			Boolean updatecmsservice,		// 修改节目包服务关系标识
			List cmsServices,				// 服务列表，原方法 modifyProgSrvRel
			Boolean updatepng,				// 修改海报标识
			List programFileses,			// 海报列表，包含了修改和未修改的海报信息
			List fileFroms,					// 海报源路径列表，在服务器上
			List storageclasses,			// 海报目标存储体等级code列表
			String filecode,				// 节目包xml的filecode
			String stclasscode				// 节目包xml文件存放存储体等级
			);
	
	// 20100608 16:09
	// 修改节目包，包括节目包描述、节目包与服务产品栏目的关系、节目包与文件的关系、海报更新
	public CmsResultDto updateProgPackageWithoutPrograminfoCmsservice(
			IProgPackageManager progPackageManager,
			IProgSrvRelManager progSrvRelManager,
			IPPColumnRelManager pPColumnRelManager,
			ISrvColumnManager srvColumnManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			ISrvProductManager srvProductManager,
			IProgPPRelManager progPPRelManager,
			IPackageFilesManager packageFilesManager,
			IPackStyleFileTypeManager packStyleFileTypeManager,
			IProgramFilesManager programFilesManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgListDetailManager progListDetailManager,
			Boolean updateprogpackage,		// 修改节目包信息标识
			ProgPackage progPackage, 		// 节目包信息，原方法 modifyProgPPRelAndPackageFiles、modifyProgSrvRel、modifyProgPackage
			Boolean updatepackagefiles,		// 修改节目包节目文件关系标识
			List programfiles, 				// 主文件列表，原方法 modifyProgPPRelAndPackageFiles
			Boolean updatecmsservice,		// 修改节目包服务关系标识
			List cmsServices,				// 服务列表，原方法 modifyProgSrvRel
			Boolean updatepng,				// 修改海报标识
			List programFileses,			// 海报列表，包含了修改和未修改的海报信息
			List fileFroms,					// 海报源路径列表，在服务器上
			List storageclasses,			// 海报目标存储体等级code列表
			String filecode,				// 节目包xml的filecode
			String stclasscode				// 节目包xml文件存放存储体等级
			);
			
	// 转移文件，上传海报
	public CmsResultDto savePngOfProgPackage(
			IProgPackageManager progPackageManager,
			IPackageFilesManager packageFilesManager,
			IProgramFilesManager programFilesManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			ProgPackage progPackage, 
			ProgramFiles programFiles, 
			String strFileFrom, 
			String storageclass
			);
	
	// 20100322 13:37
	// 保存节目信息和节目文件信息
	public CmsResultDto saveProgramInfoProgramFiles(
			IProgramInfoManager programinfoManager,
			IProgramFilesManager programFilesManager,
			ProgramInfo programinfo,
			ProgramFiles programfiles
			);
	
	
	
//	// 转移文件
//	public CmsResultDto moveFile(
//			IProgramFilesManager programFilesManager,
//			IAmsStoragePrgRelManager amsstorageprgrelManager,
//			IPackageFilesManager packageFilesManager,
//			IAmsStorageDirManager amsstoragedirManager,
//			ProgPackage progPackage, 
//			ProgramFiles programFiles, 
//			String strFileFrom, 
//			String storageclass
//			);
	
	//------------------------------------- ProgListDetail ---------------------------------------------------
	// 保存（新建）栏目单
	public ProgList saveProgList(
			IProgListManager progListManager,
			IBpmcManager bpmcManager,
			ProgList progList
			);
	
	// 添加栏目单详细 ProgListDetail
	public void saveProgListDetailsToProgList(
			IProgListManager progListManager,
			IProgListDetailManager progListDetailManager,
			IPortalColumnManager portalColumnManager,
			List progPackages, 
			String pdate, 
			String columnclassid,
			List lnums
			);
	
	// 删除栏目单详细 ProgListDetail
	public void deleteProgListDetailsFromProgList(
			IProgListDetailManager progListDetailManager,
			List progListDetails
			);
	
	// 发送栏目单到下一活动idProcess，下一活动人，
	// FU00000077 编单定义
	public void saveSendProgListDetails(
			IProgListDetailManager progListDetailManager,
			IBpmcManager bpmcManager,
			List<ProgListDetail> progListDetails, 
			String nextIdAct, 
			String nextOperator, 
			String nextState2,
			String operator,
			String sendremark
			);
	
	// --------- 栏目编单修改 20091202 -----------
	// 添加节目包progPackage，到栏目单（详细）ProgListDetail，20091202 21:16
	public CmsResultDto saveProgListDetails(
			IProgListDetailManager progListDetailManager,
			IPortalColumnManager portalColumnManager,
			IProgPackageManager progPackageManager,
			IPackStylePortalColumnManager packStylePortalColumnManager,
			IPackageFilesManager packageFilesManager,
			IProgramFilesManager programFilesManager,
			List<ProgPackage> progPackages, 
			String date, 
			Date offlineDate,
			String columnclassid,
			List<Long> lnums,
			String userId
			);
	
	// 删除节目包progListDetail，从栏目单（详细）ProgListDetail，20091202 21:16
	// 只能删除上线日期==当前栏目单的日期的
	public CmsResultDto deleteProgListDetails(
			IProgListDetailManager progListDetailManager,
			List<ProgListDetail> progListDetails,
			String date
			);
	

	
	// 更新栏目单详细 ProgListDetail 20091208 12:40
	public CmsResultDto updateProgListDetails(
			IProgListDetailManager progListDetailManager,
			IPortalColumnManager portalColumnManager,
			IProgPackageManager progPackageManager,
			List<ProgListDetail> progListDetails, 
			Date offlineDate
			);
	

	
	// 20091226
	// ------------- 新一轮的修改开始了，来吧 -----------------------

	// 发送栏目单（详细），到下一活动idProcess，下一活动人，
	public CmsResultDto updateProgListMangDetails(
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			IBpmcManager bpmcManager, 
			IFlowActivityOrderManager flowActivityOrderManager,
			String scheduleDate,						// 主表的主键
//			List<PortalColumn> portalColumns,			// 需要发送的栏目单详细
			String defcatseq,							// 栏目的代码序列
			String nextIdAct, 							// 下一活动编号
			String nextOperator, 						// 下一操作人员
			String nextState2, 							// 下一活动的性质：（新建N   回退R  顺序P  完成C）
			String currentIdAct, 						// 当前活动编号
			String operator,							// 当前操作人员
			String sendremark							// 备注
			);
	
	// 这就是传说中的方法13
	// 20100121 14:03
	// 发送本地栏目单（详细），到下一活动idProcess，下一活动人，
	public CmsResultDto updateLocalProgListMangDetails(
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			IBpmcManager bpmcManager, 
			IFlowActivityOrderManager flowActivityOrderManager,
			String scheduleDate,						// 主表的主键
//			List<PortalColumn> portalColumns,			// 需要发送的栏目单详细
			String defcatseq,							// 栏目的代码序列
			String nextIdAct, 							// 下一活动编号
			String nextOperator, 						// 下一操作人员
			String nextState2, 							// 下一活动的性质：（新建N   回退R  顺序P  完成C）
			String currentIdAct, 						// 当前活动编号
			String operator,							// 当前操作人员
			String sendremark							// 备注
			);

	// 20100130
	// 刷新栏目单详细中节目包的状态state1， 节目包状态，0-导入 1-缓存库 2-加扰库 3-播控库
	public CmsResultDto updateRefreshState1OfProgPackage(
			IPackageFilesManager packageFilesManager,
			IProgListDetailManager progListDetailManager,
			IProgPackageManager progPackageManager,
			String stclasscodeNearOnline,		// 缓存库存储体等级code
			String stclasscodeCaOnline,			// 加扰库存储体等级code
			String stclasscodeOnline,			// 播控库存储体等级code
			String stclasscodeBjOnline,			// 在上海的北京缓存库存储体等级code
			ProgPackage progPackage,
			int type							// 0 - 上海； 1 - 北京
			);
	
	// 20100223 15:18
	// 在查询栏目单的时候，判断栏目单总表分表里是否有记录，如果没有记录，就添加；如果有，就不添加
	public CmsResultDto saveCheckProgListMang(
			IPortalColumnManager portalColumnManager,
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			String date, 
			String defcatseq, 
			String operator
			);
	
	// 20100221 13:51
	// 数据导入，把上海导出的节目包和节目包文件信息，导入到北京的cms
	public CmsResultDto saveImportProgPackageFilesToBjCms(
			IProgPackageManager progPackageManager,
			IProgramFilesManager programFilesManager,
			IPackageFilesManager packageFilesManager,
			IAmsStorageDirManager amsstoragedirManager,
			IAmsStorageManager amsstorageManager,
			IAmsStorageClassManager amsstorageclassManager,
			String importDataStclasscode,				// 数据导入的存储体等级code
			String importDataFilecode,					// 数据导入的源路径
			String destStclasscode,						// 目标存储体等级code
			String filecodeMigrationImport,				// 迁移单xml的filecode
			String stclasscodeMigrationImport,			// 迁移单xml的存储体等级code
			String progPackagePath,						// 节目包目录名称
			String progListPath,						// 栏目单目录名称
			String operator								// 当前操作人员
			);
	
	// 20100223 13:45
	// 数据导入，分析上海导出的xml，把节目包和编单信息导入到北京的cms
	public CmsResultDto saveImportPortalXmlToBjCms(
			IProgPackageManager progPackageManager,
			IAmsStorageDirManager amsstoragedirManager,
			IPortalColumnManager portalColumnManager,
			IProgListMangManager progListMangManager, 
			IProgListMangDetailManager progListMangDetailManager, 
			IProgListDetailManager progListDetailManager, 
			IPPColumnRelManager pPColumnRelManager,
			String importDataStclasscode,				// 数据导入的存储体等级code
			String importDataFilecode,					// 数据导入的源路径
			String progPackagePath,						// 节目包目录名称
			String progListPath,						// 栏目单目录名称
			String operator								// 当前操作人员
			);
	
	
	
	
	
	
	
	
	//------------------------------------- PortalColumn ---------------------------------------------------
	// 添加节目包到栏目 ProgPackage PortalColumn 
	public CmsResultDto saveProgPackagesToPortalColumn(
			IPortalColumnManager portalColumnManager,
			IPPColumnRelManager pPColumnRelManager,
			List progPackages, 
			String columnclassid
			);
	
	// 删除节目包从栏目 ProgPackage PortalColumn
	public CmsResultDto deleteProgPackagesFromPortalColumn(
			IPortalColumnManager portalColumnManager,
			IPPColumnRelManager pPColumnRelManager,
			List progPackages, 
			String columnclassid
			);
	
	// 保存（新建）栏目 PortalColumn
	public CmsResultDto savePortalColumn(
			IPortalColumnManager portalColumnManager,
			IPPColumnRelManager pPColumnRelManager,
			PortalColumn portalColumn
			);
	
	// 删除栏目 PortalColumn
	public CmsResultDto deletePortalColumn(
			IPortalColumnManager portalColumnManager,
			ISrvColumnManager srvColumnManager,
			IPPColumnRelManager pPColumnRelManager,
			IProgListDetailManager progListDetailManager,
			IProgPackageManager progPackageManager,
			PortalColumn portalColumn
			);
	
	// 保存栏目与服务的配置关系
	public CmsResultDto saveSrvColumnByColumnclassid(
			IPortalColumnManager portalColumnManager,
			ISrvColumnManager srvColumnManager,
			List cmsServices, 
			String columnclassid
			);
	
	// 保存栏目角色关系表
	public CmsResultDto savePortalRoleOperRel(
			IPortalRoleOperRelManager portalRoleOperRelManager,
			IRoleManager roleManager,
			IPortalColumnManager portalColumnManager,
			String roleid, 
			PortalColumn portalColumn, 
			String inputmanid
			);
	
	// 删除栏目角色关系表
	public CmsResultDto deletePortalRoleOperRel(
			IPortalRoleOperRelManager portalRoleOperRelManager,
			IRoleManager roleManager,
			IPortalColumnManager portalColumnManager,
			String roleid, 
			PortalColumn portalColumn, 
			String inputmanid
			);
	
	// 20100131 14:41
	// 生成本地栏目的发布文件
	public CmsResultDto saveGeneratePortalXmlForLocal(
			IPortalColumnManager portalColumnManager,
			IProgListDetailManager progListDetailManager,
			IPackageFilesManager packageFilesManager,
			IProgListMangManager progListMangManager,
			IProgListFileManager progListFileManager,
			IPpSrvPdtRelManager ppSrvPdtRelManager,
			IProductCategoryManager productCategoryManager,
			IProgListMangDetailManager progListMangDetailManager,
			IFlowActivityOrderManager flowActivityOrderManager,
			IBpmcManager bpmcManager,
			String filecodePortalXml,
			String stclasscode,
			String date,								// 日期，格式：yyyy-MM-dd
			String progPackagePath,						// 节目包目录名称
			String progListPath,						// 栏目单目录名称
			String operator								// 当前操作人员
			);
	
	

	
	//------------------------------------- Encrypt 加扰 ---------------------------------------------------
	// 20100111 15:47
	// 生成加扰任务，并且修改栏目单详细中节目包状态
	public CmsResultDto saveEncryptTask(
			IEncryptListManager encryptListManager,
			IProgListDetailManager progListDetailManager,
			IProgPackageManager progPackageManager,
			EncryptList encryptList, 				// 加扰任务
//			List progListDetails					// 栏目单详细
			ProgPackage progPackage
			);
	
	// 20100112 20:09
	// 加扰任务完成处理接口，
//	public CmsResultDto saveFinishEncrypt(
//			IProgListDetailManager progListDetailManager,
//			IAmsStoragePrgRelManager amsstorageprgrelManager,
//			String productid,			// 节目包id
//			String stglobalid,			// 目标存储ID
//			String stdirglobalid,		// 目标存储ID
//			String filepath,			// 目标文件路径
//			List storagePrgRels,		// 文件存放位置表记录
//			Long encryptResult,			// 加扰完成结果，预期结果：5/6，处理状态（0未处理1下发任务5加扰成功6反馈失败8成功9失败）
//			String errorMsg,			// 失败的信息
//			String operatorid			// 操作人员
//			);
	
	// 20100131 11:50
	// 加扰任务完成处理接口，
	public CmsResultDto saveFinishEncrypt(
			IProgListDetailManager progListDetailManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgramFilesManager programFilesManager,
			IPackageFilesManager packageFilesManager,
			IProgPackageManager progPackageManager,
			String productid,			// 节目包id
			String stGlobalid,			// 目标存储体ID
			String stDirGlobalid,		// 目标存储体目录ID
			List storagePrgRels,
			String destStStorageClassCode,
			String stclasscodeNearOnline, 
			String stclasscodeCaOnline, 
			String stclasscodeOnline, 
			String filecode,			// 目标文件filecode
			String filePath,			// 目标文件路径
			String remark,				// 备注信息，包含：目标存储ID、目标目录ID、目标文件路径、文件位置表主键LIST
			String operatorid			// 操作人员
			);
	
	
	
	
	
	
	//------------------------------------- Portal ---------------------------------------------------
	// 这就是传说中的方法12
	// 20100119 22:13
	// 保存ProgListFile和AmsStoragePrgRel
	public CmsResultDto saveProgListFileAmsStoragePrgRel(
			IProgListFileManager progListFileManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			ProgListFile progListFile,
			AmsStoragePrgRel amsStoragePrgRel
			);
	
	// 这就是传说中的方法8
	// 20100119 22:41
	// 保存ProgListFile，如果已经存在记录，把状态都改为无效，再生成有效的新记录
	public CmsResultDto saveProgListFile(
			IProgListFileManager progListFileManager,
			ProgListFile progListFile,
			String date,					// 栏目单日期，格式：yyyy-MM-dd
			String defcatseq				// 栏目的code序列
			);
	
	
	
	
	
	//------------------------------------- Migration ---------------------------------------------------
	// 20100130 16:14
	// 完成迁移到一级库，迁移模块完成迁移后，通过webservice调用这个接口
	public CmsResultDto saveFinishMigrationToOnline(
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgListDetailManager progListDetailManager,
			IPackageFilesManager packageFilesManager,
			IProgPackageManager progPackageManager,
			String stclasscodeNearOnline,		// 缓存库存储体等级code
			String stclasscodeCaOnline,			// 加扰库存储体等级code
			String stclasscodeOnline,			// 播控库存储体等级code
			AmsStoragePrgRel amsStoragePrgRel,
			String proglistId,				// 栏目单日期
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes				// 失败原因
			);
	
	// 20100208 16:34
	// 完成迁移到 在上海的北京缓存库，迁移模块完成迁移后，通过webservice调用这个接口，新增文件位置表记录
	public CmsResultDto saveFinishMigrationToBjOnline(
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgListDetailManager progListDetailManager,
			IPackageFilesManager packageFilesManager,
			IProgPackageManager progPackageManager,
			String stclasscodeNearOnline,		// 缓存库存储体等级code
			String stclasscodeCaOnline,			// 加扰库存储体等级code
			String stclasscodeOnline,			// 播控库存储体等级code
			String stclasscodeBjOnline,			// 在上海的北京缓存库存储体等级code
			AmsStoragePrgRel amsStoragePrgRel
//			String proglistId,				// 栏目单日期
//			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
//			String resultDes				// 失败原因
			);
	
	// 20100130 16:14
	// 完成迁移，从导入区到北京缓存库，迁移模块完成迁移后，通过webservice调用这个接口
	public CmsResultDto saveFinishMigrationImportDataToBjNearOnline(
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			IProgListDetailManager progListDetailManager,
			IPackageFilesManager packageFilesManager,
			IProgPackageManager progPackageManager,
			String stclasscodeNearOnline,		// 缓存库存储体等级code
			String stclasscodeCaOnline,			// 加扰库存储体等级code
			String stclasscodeOnline,			// 播控库存储体等级code
			AmsStoragePrgRel amsStoragePrgRel
//			String proglistId,				// 栏目单日期
//			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
//			String resultDes				// 失败原因
			);
	
	
	
	
	//------------------------------------- broadcast 播发单 ---------------------------------------------------
	// 20100202 12:18
	// 播发单下发完成，保存发布文件表记录progListfile，并且发送活动
	public CmsResultDto saveProgListFileAndUpdateProgListMangDetail(
			IProgListFileManager progListFileManager,
			IProgListMangManager progListMangManager,
			IProgListMangDetailManager progListMangDetailManager,
			IBpmcManager bpmcManager, 
			IFlowActivityOrderManager flowActivityOrderManager,
			IPortalColumnManager portalColumnManager,
			IProgListDetailManager progListDetailManager,
			ProgListFile progListFile,
			String date,					// 栏目单日期，格式：yyyy-MM-dd
			String defcatseq,				// 栏目的code序列
			String operatorId
			);
	
	
	
	
	// ----------------------------------- PortalPackage ------------------------------------
	// 20100414 19:42
	// 保存（新建）图文记录（TPORTALPACKAGE），同时保存PORTAL页面包装和节目包关系表（TPTPPGPREL）记录
	public CmsResultDto savePortalPackage(
			IPortalPackageManager portalPackageManager,
			IPtpPgpRelManager ptpPgpRelManager,
			PortalPackage portalPackage,
			List ptpPgpRels,
			String operatorId
			);
	
	// 20100414 19:49
	// 修改图文记录（TPORTALPACKAGE），同时保存PORTAL页面包装和节目包关系表（TPTPPGPREL）记录
	public CmsResultDto updatePortalPackage(
			IPortalPackageManager portalPackageManager,
			IPtpPgpRelManager ptpPgpRelManager,
			PortalPackage portalPackage,
			List ptpPgpRels,
			String operatorId
			);
	
	// 20100419 11:35
	// 删除页面包装记录（TPORTALPACKAGE），同时删除PORTAL页面包装和节目包关系表（TPTPPGPREL）记录
	public CmsResultDto deletePortalPackage(
			IPortalPackageManager portalPackageManager,
			IPtpPgpRelManager ptpPgpRelManager,
			String ptpid,
			String operatorId
			);
	
	// 20100419 11:37
	// 新增页面包装与节目包关系记录
	public CmsResultDto savePtpPgpRels(
			IPortalPackageManager portalPackageManager,
			IPtpPgpRelManager ptpPgpRelManager,
			IProgPackageManager progPackageManager,
			PortalPackage portalPackage,		// 页面包装对象
			List ptpPgpRels,					// 页面包装与节目包关系对象
			String operatorId					// 操作人员ID
			);
	
	// 20100419 11:37
	// 删除页面包装与节目包关系记录
	public CmsResultDto deletePtpPgpRels(
			IPortalPackageManager portalPackageManager,
			IPtpPgpRelManager ptpPgpRelManager,
			PortalPackage portalPackage,		// 页面包装对象
			List ptpPgpRels,					// 页面包装与节目包关系对象
			String operatorId					// 操作人员ID
			);
	
	
	/**
	 * 流程回退. 所有流程只要回退. 统一回退到总单审校
	 * 总单审校活动编号: FU00000081
	 * @param progListMangManager
	 * @param progListMangDetailManager
	 * @param bpmcManager
	 * @param date 日期主键. 格式: 2010-01-01
	 * @param inputid 操作者编号
	 * @param remark 回退备注
	 */
	public void updateRollBack(IProgListMangManager progListMangManager, 
			IProgListMangDetailManager progListMangDetailManager, 
			IBpmcManager bpmcManager,
			String date, String inputid, String remark);
	
	/**
	 * 数据导入，把上海导出的节目包和节目包文件信息，导入到北京的cms 2010年7月14日 11时18分
	 * 
	 * @param progPackageManager
	 * @param programFilesManager
	 * @param packageFilesManager
	 * @param amsstoragedirManager
	 * @param amsstorageManager
	 * @param amsstorageclassManager
	 * @param importDataStclasscode
	 * @param importDataFilecode
	 * @param destStclasscode
	 * @param filecodeMigrationImport
	 * @param stclasscodeMigrationImport
	 * @param progPackagePath
	 * @param progListPath
	 * @param operator
	 * @return
	 */
	public CmsResultDto saveImportProgPackageFilesToBjCms2(
			IProgPackageManager progPackageManager,
			IProgramFilesManager programFilesManager,
			IPackageFilesManager packageFilesManager,
			IAmsStorageDirManager amsstoragedirManager,
			IAmsStorageManager amsstorageManager,
			IAmsStorageClassManager amsstorageclassManager,
			String importDataStclasscode, // 数据导入的存储体等级code
			String importDataFilecode, // 数据导入的源路径
			String destStclasscode, // 目标存储体等级code
			String filecodeMigrationImport, // 迁移单xml的filecode
			String stclasscodeMigrationImport, // 迁移单xml的存储体等级code
			String progPackagePath, // 节目包目录名称
			String progListPath, // 栏目单目录名称
			String operator // 当前操作人员
	);
	
	/**
	 * 报纸导入的事务管理
	 * @param programFilesManager 节目文件Manager
	 * @param programFile 
	 * @param packageManager 节目包Manager
	 * @param progPackage
	 * @param packageFilesManager 节目包文件关系Manager
	 * @param packageFile
	 * @param amsStoragePrgRelManager 文件位置表Manager
	 * @param amsStoragePrgRel
	 * @param pColumnRelManager 节目包栏目关系Manager
	 * @param ppColumnRel
	 * @param bpmcManager 操作记录Manager
	 * @return
	 */
	public void savePager(
			IProgramFilesManager programFilesManager, ProgramFiles programFile, 
			IProgPackageManager packageManager, ProgPackage progPackage,
			IPackageFilesManager packageFilesManager, PackageFiles packageFile,
			IAmsStoragePrgRelManager amsStoragePrgRelManager, AmsStoragePrgRel amsStoragePrgRel,
			IPPColumnRelManager pColumnRelManager, PPColumnRel ppColumnRel,
			IBpmcManager bpmcManager);
}
