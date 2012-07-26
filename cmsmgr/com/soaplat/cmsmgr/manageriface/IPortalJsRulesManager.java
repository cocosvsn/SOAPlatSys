package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IPortalJsRulesManager extends IBaseManager {

	// 20100331 21:29
	// 根据栏目code，查询Js规则记录，按序号排序
	public List getJsRulesByDefcatcode(String defcatcode);
	
	// 20100331 21:29
	// 根据栏目code，查询Js规则记录，按序号排序
	public List getJsRulesByDatatypeDefcatcode(
			Long datatype,
			String defcatcode
			);
}
