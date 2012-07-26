package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.ProgListDeleteDetail;
import com.soaplat.cmsmgr.dto.ProgListDeleteDetailVo;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgListDeleteDetailManager extends IBaseManager {
	public void save(List<ProgListDeleteDetail> progListDeleteDetails, 
			String scheduleDate);
	
	/**
	 * HuangBo addition by 2011年1月21日 15时15分
	 * 根据编单日期ID, 查询该编单日期下所需强制删除的节目列表
	 * @param scheduleDate 编单日期ID
	 * @return List<ProgListDeleteDetailVo>
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListDeleteDetailVo> getProgListDeleteDetailVOsByScheduledate(
			String scheduleDate);
	
	public List<ProgListDeleteDetail> getProgListDeleteDetailsBySitecodeScheduledate(
			String siteCode,
			String scheduleDate
			);
	
	public List<ProgListDeleteDetail> getProgListDeleteDetailsByScheduledate(
			String scheduleDate
			);
}
