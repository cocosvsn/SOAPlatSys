package com.soaplat.cmsmgr.service;

import java.applet.*; 
import java.awt.*;

import org.apache.log4j.Logger;

import com.soaplat.cmsmgr.EncryptService.EncryptServiceServiceImpl;

public class BackgroundEncrypt2Service
{

//	static Thread circleThread;
	static MyThread mt = new MyThread();

//	private static EncryptServiceServiceImpl es = new EncryptServiceServiceImpl();
	public static final Logger cmsLog = Logger.getLogger("Cms");
//	
	public void start()
	{
		try {
			mt.start();
			cmsLog.info("开启加扰任务扫描模块异常。");
		} catch (Exception e) {
			cmsLog.info("加扰任务扫描模块异常。信息：" + e.getMessage());
		}
	}

	public void stop()
	{
		try {
			mt.stop();
			cmsLog.info("停止加扰任务扫描模块异常。");
		} catch (Exception e) {
			cmsLog.info("加扰任务扫描模块异常。信息：" + e.getMessage());
		}
	}

}

class MyThread extends Thread
{
	private static EncryptServiceServiceImpl es = new EncryptServiceServiceImpl();
	
	public void run()
	{
		try 
		{
			while (true) 
			{
				es.addTask();
				es.GetTaskStatus();
				this.sleep(10000);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
 	}
}
