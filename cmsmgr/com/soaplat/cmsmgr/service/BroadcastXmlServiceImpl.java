package com.soaplat.cmsmgr.service;

import java.text.ParseException;
import java.util.List;

import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.manageriface.IBpmcManager;
import com.soaplat.cmsmgr.manageriface.IBroadcastModuleManager;
import com.soaplat.cmsmgr.manageriface.ICmsSiteManager;
import com.soaplat.cmsmgr.manageriface.ICmsTransactionManager;
import com.soaplat.cmsmgr.manageriface.IFileStyleManager;
import com.soaplat.cmsmgr.manageriface.IFlowActionManager;
import com.soaplat.cmsmgr.manageriface.IFlowActivityOrderManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IPortalColumnManager;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListFileManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.soaplat.cmsmgr.util.DateUtil;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class BroadcastXmlServiceImpl {
	private final String GENERATEJS				= "FU00000082";		// 生成节目预告JS和节目包JS
	/**
	 * 界面：生成播发单
	 * 按钮：生成播发单
	 */
//	private ICmsTransactionManager cmsTransactionManager = null;
	private IFlowActionManager flowActionManager;
	private IProgListMangManager progListMangManager;
	private IBroadcastModuleManager broadcastModuleManager = null;
//	private ICmsSiteManager cmsSiteManager = null;
//	private IPortalColumnManager portalColumnManager = null;
//	private IProgListDetailManager progListDetailManager = null;
//	private IProgPackageManager progPackageManager = null;
//	private IFileStyleManager fileStyleManager = null;
//	private IPackageFilesManager packageFilesManager = null;
//	private IProgListMangManager progListMangManager = null;
//	private IProgListFileManager progListFileManager = null;
//	private IProgListMangDetailManager progListMangDetailManager = null;
//	private IBpmcManager bpmcManager = null;
//	private IFlowActivityOrderManager flowActivityOrderManager = null;
//	private IProductInfoManager productinfoManager = null;
//	private IProgramFilesManager programFilesManager = null;
//	private IProgramInfoManager programInfoManager = null;
	
	
	public BroadcastXmlServiceImpl()
	{
//		cmsTransactionManager = (ICmsTransactionManager) ApplicationContextHolder.webApplicationContext.getBean("cmsTransactionManager");
		this.flowActionManager = (IFlowActionManager) ApplicationContextHolder.webApplicationContext.getBean("flowActionManager");
		this.progListMangManager = (IProgListMangManager)ApplicationContextHolder.webApplicationContext.getBean("progListMangManager");
		broadcastModuleManager = (IBroadcastModuleManager)ApplicationContextHolder.webApplicationContext.getBean("broadcastModuleManager");
//		cmsSiteManager = (ICmsSiteManager)ApplicationContextHolder.webApplicationContext.getBean("cmsSiteManager");
//		portalColumnManager = (IPortalColumnManager)ApplicationContextHolder.webApplicationContext.getBean("portalColumnManager");
//		progListDetailManager = (IProgListDetailManager)ApplicationContextHolder.webApplicationContext.getBean("progListDetailManager");
//		progPackageManager = (IProgPackageManager)ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
//		fileStyleManager = (IFileStyleManager)ApplicationContextHolder.webApplicationContext.getBean("fileStyleManager");
//		packageFilesManager = (IPackageFilesManager)ApplicationContextHolder.webApplicationContext.getBean("packageFilesManager");
//		progListMangManager = (IProgListMangManager)ApplicationContextHolder.webApplicationContext.getBean("progListMangManager");
//		progListFileManager = (IProgListFileManager) ApplicationContextHolder.webApplicationContext.getBean("progListFileManager");
//		progListMangDetailManager = (IProgListMangDetailManager) ApplicationContextHolder.webApplicationContext.getBean("progListMangDetailManager");
//		flowActivityOrderManager = (IFlowActivityOrderManager) ApplicationContextHolder.webApplicationContext.getBean("flowActivityOrderManager");
//		bpmcManager = (IBpmcManager) ApplicationContextHolder.webApplicationContext.getBean("bpmcManager");
//		productinfoManager = (IProductInfoManager) ApplicationContextHolder.webApplicationContext.getBean("productinfoManager");
//		programFilesManager = (IProgramFilesManager) ApplicationContextHolder.webApplicationContext.getBean("programFilesManager");
//		programInfoManager = (IProgramInfoManager) ApplicationContextHolder.webApplicationContext.getBean("programinfoManager");
	}
	
	/**
	 * @version 1.21 
	 * 按钮：生成播发单
	 * @throws ParseException 
	 */
	public String generateBroadcastxml(
			String date, 
			String plandate,		// "yyyy-MM-dd HH:mm:ss"
			String operatorId
			) throws ParseException
	{
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		/**
		 * 新增加判断, 判断编单日往后的几天的活动是否正确, 否则不允许生JS
		 */
		/*-------------------- start ---------------------*/
		int days = Integer.valueOf(new CmsConfig().getPropertyByName("ScheduleDays"));
		List<String> greaterProgCheckActions = this.flowActionManager.getGreaterOrLessAction(this.GENERATEJS, false);
		List<String> afterScheduleDates = DateUtil.getAfterDaysStrList(date, days - 1);
		for (String string : afterScheduleDates) {
			ProgListMang mang = (ProgListMang) this.progListMangManager.getById(string);
			if (!greaterProgCheckActions.contains(mang.getIdAct())) {
				return "后 " + (days - 1) + " 日的编单未通过生成JS, \r\n\t\t通过后才能生成播发单!";
			}
		}
		/*-------------------- en ---------------------*/
		
		cmsResultDto = broadcastModuleManager.saveGenerateBroadcastxml_123(
//				cmsTransactionManager,
//				cmsSiteManager, 
//				portalColumnManager, 
//				progListDetailManager, 
//				progPackageManager, 
//				fileStyleManager, 
//				packageFilesManager,
//				progListMangManager,
//				progListFileManager,
//				progListMangDetailManager, 
//				bpmcManager,
//				flowActivityOrderManager,
//				productinfoManager,
//				programFilesManager,
//				programInfoManager,
				date, 
				operatorId, 
				plandate,
				true
				);
		
		if (0 == cmsResultDto.getResultCode()) {
			return null;
		} else {
			return cmsResultDto.getErrorMessage();
		}
	}
	
	public void test(String date, String plandate, String operatorId) {
		try {
			System.out.println(this.generateBroadcastxml(date, plandate, operatorId));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
