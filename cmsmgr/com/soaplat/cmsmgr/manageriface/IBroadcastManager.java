package com.soaplat.cmsmgr.manageriface;

//import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IBroadcastManager	// extends IBaseManager 
{
	/**
	 * 
	 * @param date
	 * @param operatorId
	 * @param plandate
	 * 
	 */
	public int generateBroadcastxml(
			ICmsSiteManager cmsSiteManager,
			IPortalColumnManager portalColumnManager,
			IProgListDetailManager progListDetailManager,
			IProgPackageManager progPackageManager,
			IFileStyleManager fileStyleManager,
			IPackageFilesManager packageFilesManager,
			String date, 			// yyyy-MM-dd
			String operatorId, 		// 操作人员id
			String plandate 		// 计划播发日期，格式："yyyy-MM-dd HH:mm:ss"
			);
}
