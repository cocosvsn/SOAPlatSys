package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.FlowAction;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IFlowActionManager extends IBaseManager {

	/**
	 * 根据编单日期ID, 栏目ID, 查询栏目编单活动名称
	 * @param scheduleDate 编单日期ID
	 * @param columnId 栏目ID
	 * @return 返回活动名称
	 */
	public String getActionNameByScheduleDateAndColumnID(
			String scheduleDate, String columnId);

	/**
	 * 根据编单日期ID, 栏目ID, 查询栏目编单活动名称
	 * @param scheduleDate 编单日期ID
	 * @param columnId 栏目ID
	 * @return 
	 */
	public FlowAction getByScheduleDateAndColumnID(
			String scheduleDate, String columnId);
	
	/**
	 * 根据活动编号, 查询大于或小于等于该活动的所有活动集合
	 * @param actionId 活动ID
	 * @param lessEqual 是否查询小于等于, 如果是则返回小于等于的结果, 否则返回大于的结果
	 */
	public List<String> getGreaterOrLessAction(String actionId, boolean lessEqual);
}
