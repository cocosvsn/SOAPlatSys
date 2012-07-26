package com.cbs.cbsmgr.serviceBackground;

import java.applet.*; 
import java.awt.*;

import org.apache.log4j.Logger;


public class BackgroundBillingService implements Runnable 
{

	static Thread circleThread;
	private static MonthlyBillingImpl monthlyBilling = new MonthlyBillingImpl();
	private static VodBillingImpl vodBillingImpl = new VodBillingImpl();
	private static final Logger cmsLog = Logger.getLogger("Cms");
	private static int timer = 60 * 60 * 1000;
	
	public void start()
	{
		if (circleThread == null)
		{
			circleThread = new Thread(this);
			circleThread.start();
			cmsLog.info("启动计费后台模块...");
			cmsLog.info("任务扫描间隔：" + timer);
		}
		else
		{
			cmsLog.info("计费后台模块已经启动，不操作。");
			cmsLog.info("任务扫描间隔：" + timer);
		}
	}
	
	public void run()
	{
		while(circleThread != null)
		{
			try
			{
				vodBillingImpl.executePeriodly();
				monthlyBilling.executePeriodly();
				circleThread.sleep(timer);
//				Thread.currentThread().sleep(10000);

			}
			catch(InterruptedException e)
			{
				cmsLog.error("计费后台模块异常。信息：" + e.getMessage());
			}
		}
	}
	
	public void stop()
	{
		if(circleThread != null)
		{
			circleThread.yield();
			circleThread = null;
	//		encryptService = null;
			cmsLog.info("停止计费后台模块。");
		}
		else
		{
			cmsLog.info("计费后台模块未启动，不操作。");
		}
	}

}
