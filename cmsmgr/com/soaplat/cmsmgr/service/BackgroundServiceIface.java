package com.soaplat.cmsmgr.service;

import com.soaplat.cmsmgr.dto.CmsResultDto;

public interface BackgroundServiceIface {

	// 20100202 13:11
	// 启动任务扫描
	public CmsResultDto setEncryptTaskRun();
	
	// 20100202 13:11
	// 中止任务扫描
	public CmsResultDto setEncryptTaskStandby();
	
	// 20100202 13:11
	// 查询任务扫描工作状态
	public CmsResultDto getEncryptTaskWorkStatus();
}
