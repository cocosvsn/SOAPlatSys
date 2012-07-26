/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.soaplat.cmsmgr.bean.ProgListDeleteDetail;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.dto.ProgListDeleteDetailVo;
//import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IProgListDeleteDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2011-1-20 下午05:02:58
 */
public class ProgListDeteleDetailService {
	private IProgPackageManager progPackageManager;
//	private IPortalColumnManager portalColumnManager;
	private IProgListDeleteDetailManager progListDeleteDetailManager;
	
	public ProgListDeteleDetailService() {
		this.progPackageManager = (IProgPackageManager) ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
//		this.portalColumnManager = (IPortalColumnManager) ApplicationContextHolder.webApplicationContext.getBean("portalColumnManager");
		this.progListDeleteDetailManager = (IProgListDeleteDetailManager) ApplicationContextHolder.webApplicationContext.getBean("progListDeleteDetailManager");
	}
	
	/**
	 * 获取所有删除节目列表
	 * @param dateStr 编单日期字符串, 格式: 2010-09-09
	 * @return
	 */
	public List<ProgListDeleteDetailVo> getProgListDeleteDetails(String dateStr) {
		String scheduleDate = dateStr.replace("-", "") + "000000";
		return this.progListDeleteDetailManager.getProgListDeleteDetailVOsByScheduledate(
				scheduleDate);
	}
	
	/**
	 * 添加强制删除节目包转动
	 * @param dateStr 编单日期
	 * @param columnId 栏目ID
	 * @param defcatSeq 栏目序列
	 * @param progPackageId 节目包ID
	 * @param inputManId 操作人员ID
	 * @param remark 备注
	 */
	public void addProgListDeleteDetail(String dateStr, List<String> progPackageIds, 
			String inputManId, String remark) {
		String scheduleDate = dateStr.replace("-", "") + "000000";
		List<ProgListDeleteDetail> progListDeleteDetails = new ArrayList<ProgListDeleteDetail>();
		ProgPackage progPackage = null;
		for (String string : progPackageIds) {
			progPackage = (ProgPackage) this.progPackageManager.getById(string);
			ProgListDeleteDetail progListDeleteDetail = new ProgListDeleteDetail();
			progListDeleteDetail.setScheduleDate(scheduleDate);
			progListDeleteDetail.setProgPackageId(string);
			progListDeleteDetail.setProgPackageName(progPackage.getProductname());
			progListDeleteDetail.setInputManId(inputManId);
			progListDeleteDetail.setInputTime(new Date());
			progListDeleteDetail.setSiteCode(progPackage.getSiteCode());
			progListDeleteDetail.setRemark(remark);
			progListDeleteDetails.add(progListDeleteDetail);
		}
		this.progListDeleteDetailManager.save(progListDeleteDetails, scheduleDate);
	}
	
	public void setProgPackageManager(IProgPackageManager progPackageManager) {
		this.progPackageManager = progPackageManager;
	}
	
//	public void setPortalColumnManager(IPortalColumnManager portalColumnManager) {
//		this.portalColumnManager = portalColumnManager;
//	}
	
	public void setProgListDeleteDetailManager(
			IProgListDeleteDetailManager progListDeleteDetailManager) {
		this.progListDeleteDetailManager = progListDeleteDetailManager;
	}
}
