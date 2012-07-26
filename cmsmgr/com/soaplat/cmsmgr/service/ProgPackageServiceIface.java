package com.soaplat.cmsmgr.service;

import java.util.List;

import com.soaplat.cmsmgr.bean.PackStyle;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.dto.ProgramTypeDto;

public interface ProgPackageServiceIface {

	// 测试通过，20091102
	// 获取节目类型列表，包含样式列表
	public List<ProgramTypeDto> getAllProgramTypes(Long styletype);
	
	// 测试通过，20091102
	// 获取节目分类列表，包含服务列表
	public List getAllProgramCategories();
	
	// 测试通过，20091102
	// 获取文件列表，根据节目列表
	public List getProgramFilesByProgramInfos(List programInfos, PackStyle packStyle);
	
	// 
	// 创建（定义）节目包，输入节目包描述信息、节目类型、样式、节目分类、服务列表、节目列表、文件列表
	public ProgPackage createProgPackage(ProgPackage progPackage, 
			Long styleId, 
			List cmsServices, 
			List programs,
			List progFiles,
			String ppXml
			);
	
//	// 修改节目包
//	public void modifyProgPackage(ProgPackage progPackage, 
//			Long styleId, 
//			List cmsServices, 
//			List programs,
//			List progFiles
//			);
	
	// 修改节目包所有信息，ProgPackage ProgSrvRel ProgPPRel PackageFiles
	public void updateProgPackage(ProgPackage progPackage, List cmsServices, List programs, Long styleId) throws Exception;
	
	// 测试通过，20091103，9:55
	// 修改节目包信息：ProgPackage
	public void modifyProgPackage(ProgPackage progPackage, String ppXml);
	
	// 测试通过，20091103，10:11
	// 修改节目包服务关系：ProgSrvRel
	public void modifyProgSrvRel(List cmsServices, ProgPackage progPackage);
	
	// 测试通过，20091103，10:59
	// 修改节目包节目、节目包文件关系：ProgPPRel、PackageFiles
	public void modifyProgPPRelAndPackageFiles(List programs, ProgPackage progPackage, Long styleId);
	
	// 测试通过，20091103，11:22
	// 删除节目包
	public CmsResultDto deleteProgPackage(String productId);
	
	// 查询节目包
	public CmsResultDto getProgPackage(String productId);
	
	// 转移文件，上传海报
	public CmsResultDto moveFile(ProgPackage progPackage, ProgramFiles programFiles, String strFileFrom, String storageclass);
	
	// 20100108 14:44
	// 删除海报
	public CmsResultDto deletePackageFile(
			ProgPackage progPackage,				// 节目包
			ProgramFiles programFiles,				// 文件
			String storageclass						// 存储体等级
			);
	
	// 从存储体复制文件到服务器，20100106 15:34
	// 返回所有文件的全路径
	public CmsResultDto moveFileFromStorageToServer(
			String storageclass,				// 存储体等级
			ProgPackage progPackage,			// 节目包
			String filecode,					// filecode
			String fileType						// 后缀
			);
	
	// 20100115 13:04
	// 根据 文件code 和 存储体等级，查询得到文件存放目标路径
	public CmsResultDto getDestPathByFilecodeStclasscode(
			String filecode, 						// 文件code
			String stclasscode						// 存储体等级code
			);
	
	// 20100304 10:01
	// 修改节目包，包括节目包描述、节目包与服务产品栏目的关系、节目包与文件的关系、海报更新
	public CmsResultDto updateProgPackageAllInfo(
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
			List storageclasses				// 海报目标存储体等级code列表
			);
	
	// 20100322 13:20
	public CmsResultDto saveProgramInfoProgramFiles(
			Object progobject,
			Object progfilesobject,
			String filepath,
			String sourcePartPath
			);
	
	// 20100608 15:28
	// 根据节目包ID，查询节目包的文件列表
	public CmsResultDto getProgramfilesByProductid(
			String productid				// 节目包ID
			);
	
	// 20100608 16:01
	// 修改节目包，包括节目包描述、节目包与服务产品栏目的关系、节目包与文件的关系、海报更新
	public CmsResultDto updateProgPackageWithoutPrograminfoCmsservice(
			Boolean updateprogpackage,		// 修改节目包信息标识
			ProgPackage progPackage, 		// 节目包信息，原方法 modifyProgPPRelAndPackageFiles、modifyProgSrvRel、modifyProgPackage
			Boolean updatepackagefiles,		// 修改节目包节目文件关系标识
			List programfiles, 				// 节目列表，原方法 modifyProgPPRelAndPackageFiles
			Boolean updatecmsservice,		// 修改节目包服务关系标识，目标为false
			List cmsServices,				// 服务列表，原方法 modifyProgSrvRel
			Boolean updatepng,				// 修改海报标识
			List programFileses,			// 海报列表，包含了修改和未修改的海报信息
			List fileFroms,					// 海报源路径列表，在服务器上
			List storageclasses				// 海报目标存储体等级code列表
			);
}
