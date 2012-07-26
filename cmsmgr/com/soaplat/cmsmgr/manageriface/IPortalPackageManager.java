package com.soaplat.cmsmgr.manageriface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IPortalPackageManager extends IBaseManager {

	// 20100414 18:10
	// 查询图文记录（TPORTALPACKAGE），根据名称、栏目ID、上下线标识
	public List getPortalPackagesByNameColumnclassidOnlinetag(
			String ptpname,
			String columnclassid,
			Long onlinetag
			);
	
	// 20100415 11:57
	// 查询图文记录（PtpPgpRel），根据ID、节目包ID
	public List getPtpPgpRelsByPtpidProductid(
			String ptpid,
			String productid
			);
	
	// 20100416 11:29
	// 查询页面包装，根据节目包ID、栏目ID
	public List getPortalPackagesByProductidColumnclassid(
			String productid,
			String columnclassid
			);
	
	// 20100416 11:29
	// 查询节目包，根据页面包装ID、栏目ID
	public List getProgPackagesByPtpidColumnclassid(
			String ptpid,
			String columnclassid
			);
	
	// 20100419 12:58
	// 查询页面包装下的节目包，根据页面包装、栏目id
//	public List getProgPackagesByPortalPackage(
//			String ptpid,
//			String productname
//			);
}
