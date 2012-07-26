package com.soaplat.cmsmgr.service;

import java.util.List;

import com.soaplat.cmsmgr.dto.CmsResultDto;

public interface EncryptServiceIface {

	
	
	// Edited by Andy at 20100109 21:35
	// 检查节目包是否需要加扰，如果需要，则加扰
	public CmsResultDto sendEncryptTaskByProgPackage(
			List<String> productid, 						// 节目包ID
			String operatorid						// 操作人员ID
			);
	
	// 20100112 15:54
	// 加扰任务完成处理接口，
	public CmsResultDto finishEncrypt(
			String productid,			// 节目包id
			String remark,				// 备注信息，包含：目标存储ID、目标目录ID、目标文件路径、文件位置表主键LIST
			Long encryptResult,			// 加扰完成结果，处理状态（0未处理1下发任务5加扰成功6反馈失败8成功9失败）
			String errorMsg,			// 失败的信息
			String operatorid			// 操作人员
			);

	/**
	 * 批量发送节目包加扰任务, 支持视频文件和富媒体文件的加扰任务
	 * @param progPackageID 节目包ID列表
	 * @param operatorID 操作员ID
	 * @return 返回生成加扰任务信息
	 */
	public String sendEncryptTask(List<String> progPackageID, String operatorID);
}
