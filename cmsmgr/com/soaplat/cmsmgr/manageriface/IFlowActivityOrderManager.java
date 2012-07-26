package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IFlowActivityOrderManager extends IBaseManager {

	public List getFunResourcesByFlowactivityidparent(String flowactivityidparent);
	
	public List getOperatorsByFuncid(String funcid);
	
	// Edited by Andy at 20091228 16:19
	// 查询下一活动nextIdAct，条件：State2，FLOWACTIVITYIDPARENT=currentIdAct
	public List getNextIdActsByCurrentIdActAndState2(String currentIdAct, String state2);
	
	// Edited by Andy at 20091229 11:10
	// 根据state2、IdAct，查询得到idact，条件：state2、FLOWACTIVITYIDCHILD=IdAct
	public List getLastIdActsByCurrentIdActAndState2(String currentIdAct, String state2);
	
	/**
	 * 通过当前活动编号, 以及流程活动顺序查询下一个流程活动编号
	 * @param currentAction 当前流程活动编号
	 * @param state 流程活动顺序 [R:回退; P:顺序; C:完成; N:新建;]
	 * @return 反回下一个流程活动编号
	 */
	public String getNextAction(String currentAction, String state);
}
