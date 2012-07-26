package com.soaplat.cmsmgr.service;

import org.apache.log4j.Logger;
import com.soaplat.cmsmgr.EncryptService.EncryptServiceServiceImpl;

public class BackgroundEncryptService implements Runnable {

	private static Thread circleThread = null;
	private static EncryptServiceImpl encryptServiceImpl = new EncryptServiceImpl();
	private static EncryptServiceServiceImpl es = new EncryptServiceServiceImpl();
	private static boolean encryptTaskScanRunFlag = false;
	public static final Logger cmsLog = Logger.getLogger("Cms");
	private static int timer = 20000;

	public static void start() {
		if (circleThread == null) {
			circleThread = new Thread(new BackgroundEncryptService());
			encryptTaskScanRunFlag = true;
			circleThread.start();
			cmsLog.info("启动加扰任务扫描模块...");
			cmsLog.info("任务扫描间隔：" + timer);
		} else {
			cmsLog.info("加扰任务扫描模块已经启动，不操作。");
			cmsLog.info("任务扫描间隔：" + timer);
		}
	}

	public void run() {
		while(encryptTaskScanRunFlag) {
			try {
				encryptServiceImpl.autoAddEncryptTask();
				es.addTaskHB1_11();
				es.getTaskStatusHB1_11();
				Thread.sleep(timer);
				// Thread.currentThread().sleep(10000);

			} catch (Exception e) {
				cmsLog.error("加扰任务扫描模块异常。信息：" + e.getMessage());
			}
		}
	}

	public static void stop() {
//		if (circleThread != null) {
//			circleThread.yield();
//			circleThread = null;
//			// encryptService = null;
			encryptTaskScanRunFlag = false;
			cmsLog.info("停止加扰任务扫描模块。");
			circleThread = null;
//		} else {
//			cmsLog.info("加扰任务扫描模块未启动，不操作。");
//		}
	}
}
