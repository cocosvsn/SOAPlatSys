package com.soaplat.cmsmgr.service;

import org.apache.log4j.Logger;

import com.soaplat.cmsmgr.EncryptService.EncryptServiceServiceImpl;
import com.soaplat.cmsmgr.dto.CmsResultDto;

public class BackgroundServiceImpl implements BackgroundServiceIface {

	public static boolean getEncryptTask = false;
	public static boolean encryptTaskMonitor = false;
	public static final Logger cmsLog = Logger.getLogger("Cms");
	
	
	public BackgroundServiceImpl()
	{
		
	}
	
	
	// 20100202
	// 24小时运行，查询加扰任务表中记录，发现任务则发送加扰任务到加扰服务器
	private static void getEncryptTask()
	{
		cmsLog.debug("Cms -> BackgroundServiceImpl -> getEncryptTask...");
		try 
		{
			EncryptServiceServiceImpl encryptService = new EncryptServiceServiceImpl();
			do 
			{
				encryptService.addTask();
				Thread.sleep(10000);
			} while (getEncryptTask);
			
			cmsLog.info("终止加扰任务查询模块。");
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		cmsLog.debug("Cms -> BackgroundServiceImpl -> getEncryptTask returns.");
	}
	
	private static void encryptTaskMonitor()
	{
		cmsLog.debug("Cms -> BackgroundServiceImpl -> encryptTaskMonitor...");
		try 
		{
			EncryptServiceServiceImpl encryptService = new EncryptServiceServiceImpl();
			do 
			{
				encryptService.GetTaskStatus();
				Thread.sleep(10000);
			} while (encryptTaskMonitor);
			
			cmsLog.info("终止加扰任务监控模块。");
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		cmsLog.debug("Cms -> BackgroundServiceImpl -> encryptTaskMonitor returns.");
	}
	
	// 20100202 13:11
	// 启动任务扫描
	public CmsResultDto setEncryptTaskRun()
	{
		cmsLog.debug("Cms -> BackgroundServiceImpl -> setEncryptTaskRun...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		if(getEncryptTask == false)
		{
			getEncryptTask = true;
			getEncryptTask();
		}
		else
		{
			String str = "当前状态是正在运行，不操作。";
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.info(str);
		}
		cmsLog.debug("Cms -> BackgroundServiceImpl -> setEncryptTaskRun returns.");
		return cmsResultDto;
	}
	
	// 20100202 13:11
	// 中止任务扫描
	public CmsResultDto setEncryptTaskStandby()
	{
		cmsLog.debug("Cms -> BackgroundServiceImpl -> setEncryptTaskStandby...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		if(getEncryptTask == true)
		{
			getEncryptTask = false;
		}
		else
		{
			String str = "当前状态是停止，不操作。";
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.info(str);
		}
		cmsLog.debug("Cms -> BackgroundServiceImpl -> setEncryptTaskStandby returns.");
		return cmsResultDto;
	}
	
	// 20100202 15:18
	// 启动任务监控
	public CmsResultDto setEncryptTaskMonitorRun()
	{
		cmsLog.debug("Cms -> BackgroundServiceImpl -> setEncryptTaskMonitorRun...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		if(encryptTaskMonitor == false)
		{
			encryptTaskMonitor = true;
			encryptTaskMonitor();
		}
		else
		{
			String str = "当前状态是正在运行，不操作。";
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.info(str);
		}
		cmsLog.debug("Cms -> BackgroundServiceImpl -> setEncryptTaskMonitorRun returns.");
		return cmsResultDto;
	}
	
	// 20100202 15:18
	// 中止任务监控
	public CmsResultDto setEncryptTaskMonitorStandby()
	{
		cmsLog.debug("Cms -> BackgroundServiceImpl -> setEncryptTaskMonitorStandby...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		if(encryptTaskMonitor == true)
		{
			encryptTaskMonitor = false;
		}
		else
		{
			String str = "当前状态是停止，不操作。";
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.info(str);
		}
		cmsLog.debug("Cms -> BackgroundServiceImpl -> setEncryptTaskMonitorStandby returns.");
		return cmsResultDto;
	}
	
	// 20100202 13:11
	// 查询任务扫描工作状态
	public CmsResultDto getEncryptTaskWorkStatus()
	{
		cmsLog.debug("Cms -> BackgroundServiceImpl -> getEncryptTaskStatus...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		cmsResultDto.setResultObject(getEncryptTask);
		cmsLog.debug("Cms -> BackgroundServiceImpl -> getEncryptTaskStatus returns.");
		return cmsResultDto;
	} 
	
}
