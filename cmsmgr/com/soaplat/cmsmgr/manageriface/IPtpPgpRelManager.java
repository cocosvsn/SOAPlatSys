package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.PtpPgpRel;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IPtpPgpRelManager extends IBaseManager {

	// 20100422 22:14
	// 查询页面包装和节目包关系记录，根据页面包装
	public List getPtpPgpRelByPortalPackage(
			String ptpid
			);

	/**
	 * 根据节目包ID查询该节目包页面包装信息
	 * @param progPackageId 节目包ID
	 * @return 返回该节目包的页面包装关系信息, 其中包含页面包装信息
	 * PtpPgpRel.sequence 页面包装总集数
	 * PtpPgpRel.inputmanid 栏目ID
	 * PtpPgpRel.remark 页面包装名称
	 */
	public PtpPgpRel queryPortalInfoByProgPackageId(String progPackageId);
}
