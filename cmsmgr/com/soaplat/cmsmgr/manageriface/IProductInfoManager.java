package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProductInfoManager extends IBaseManager {
	public TProductInfo getTProductInfoById(String productInfoId);

	/**
	 * 根据编单日期, 节目包ID查询栏目单明细中节目包应用产品信息的KeyId
	 * @param scheduleDateStr 编单日期, 格式: 20100909000000
	 * @param progPackageId 节目包ID
	 * @return 返回应用产品信息的KeyId集合字符串
	 */
	public String queryProductInfoKeyIdByScheduleDateAndProgPackageId(
			String scheduleDateStr, String progPackageId);

	/**
	 * 根据产品信息ID查询产品描述XML
	 * @param productInfoId 产品信息ID
	 * @return 返回产品描述XML
	 */
	public String queryProductInfoXMLById(String productInfoId);
	
	/**
	 * 根据编单日期ID, 查询该日期下所有编单明细已应用的产品信息是否已全部加密
	 * @param scheduleDate 编单日期ID
	 * @return 返回是否已全部加密完成
	 */
	public boolean existNoEncryptProductInfo(String scheduleDate);
	
	/**
	 * 根据节目包编号, 查询该节目包有效的最新历史订价信息
	 * @param progPackageId 节目包编号
	 * @return 返回最新的有效历史订价信息
	 */
	public TProductInfo queryProductInfoByProgPackageId(String progPackageId);
	
	/**
	 * 根据节目包名称查询产品订价信息
	 * @param progPackageName 节目包名称
	 * @return List<TProductInfo>
	 */
	public List<TProductInfo> queryProductInfosByProgPackageName(String progPackageName);
	
	/**
	 * 根据编单日期, 查询当天编单节目包可用于加密的产品信息
	 * @param scheduleDate 编单日期
	 * @return List<TProductInfo>
	 */
	public List<TProductInfo> queryProductInfosByScheduleDateStr(String scheduleDate);
}
