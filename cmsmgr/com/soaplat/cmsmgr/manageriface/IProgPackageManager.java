package com.soaplat.cmsmgr.manageriface;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.dto.EncryptProgVo;
import com.soaplat.cmsmgr.dto.PackageProductVo;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgPackageManager extends IBaseManager {

	@SuppressWarnings("rawtypes")
	public List getProgPackagesBySrvId(String srvId);
	
	/**
	 * 根据节目文件id查询节目包信息
	 * @param programFileId
	 * @return List<Object[4]>
	 * object[0]: ProgPackage.productid
	 * object[1]: ProgPackage.productname
	 * object[2]: ProgPackage.state
	 * object[3]: ProgPackage.dealstate
	 */
	public List<?> queryByProgramFileId(String programFileId);
	
	/**
	 * 查询报纸节目包
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<ProgPackage> queryPaper(String hql, Map<String, Object> params);
	
	/**
	 * 根据ID列表, 查询所有节目包
	 * @param progPackageIDs
	 * @return
	 */
	public List<ProgPackage> queryByIDs(List<String> progPackageIDs);

	/**
	 * 根据编单日期, 栏目ID, 查询该栏目编单下所有生成节目预告的节目包信息
	 * @param scheduleDateStr 编单日期, 格式: 20100909000000
	 * @param columnId 栏目ID
	 * @return 返回需要生成节目预告的节目包信息
	 */
	public List<ProgPackage> queryByScheduleDateAndColumn(String scheduleDateStr, 
			String columnId);

	/**
	 * 根据编单日期查询查询当天所有的编单节目包信息.
	 * @param scheduleDateStr 编单日期, 格式: 20100909000000
	 * @return 返回指定编单日所有编单节目包信息
	 */
	public List<ProgPackage> queryByScheduleDate(String scheduleDateStr);
	
	/**
	 * 根据节目包ID查询待加扰节目包的节目及文件信息
	 * @param progPakcageId 节目包ID
	 * @return
	 */
	public EncryptProgVo queryProgramFilesVoByProgPackageId(String progPackageId);

	/**
	 * 20101110 10:00 1.2 修改节目包的状态和处理状态
	 * @param progPackageManager
	 * @param packageFilesManager
	 * @param programFilesManager
	 * @param fileStyleManager
	 * @param amsstorageprgrelManager
	 * @param progPackage
	 * @param fileStyleCode
	 * @return
	 */
	public CmsResultDto updateRefreshStateOfProgPackage(
			IProgPackageManager progPackageManager,
			IPackageFilesManager packageFilesManager,
			IProgramFilesManager programFilesManager,
			IFileStyleManager fileStyleManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			ProgPackage progPackage,	// 节目包
			long fileStyleCode,			// 文件样式应用code
			String result				// 此次操作结果： "Y" - 成功； "N" - 失败
			);

	/**
	 * 根据编单日期, 检查当前编单的节目包状态是否都为播发库[3], 未处理[0]状态 
	 * @param scheduleDate 编单日期ID
	 * @return 返回是否全部为播发库[3], 未处理[0]状态
	 */
	public boolean isCanBroadcast(String scheduleDate);
	
	/**
	 * 查询产品加密和文件迁移节目包信息(包含产品信息)
	 * @param hql 用于查询的HQL语句
	 * @param params 查询HQL语句的参数列表
	 * @return 返回List<PackageProductVo>
	 */
	public List<PackageProductVo> queryPackageProductVos(String hql, 
			Map<String, Object> params);

	/**
	 * 根据节目包ID, 查询该节目包主文件ContentID
	 * @param progPackageId 节目包ID
	 * @return
	 */
	public List<Long> queryPackageCoutentID(List<String> progPackageIds);
	
	/**
	 * 根据节目包名称模糊查询节目包信息
	 * @param progPackageName 节目包名称
	 * @return List<ProgPackage>
	 */
	public List<ProgPackage> queryProgPackagesByName(String progPackageName);
	
	/**
	 * 按产品统计节目包强制保留大小
	 * @return List<Object[]> <br/>
	 * object[0]: ProgProduct.KeyName <br/>
	 * object[1]: ProgProduct.KeyId <br/>
	 * object[2]: sum of package size group by ProgProduct.KeyName
	 */
	public List<Object[]> countOfForcedReservesPackageByProduct();
}
