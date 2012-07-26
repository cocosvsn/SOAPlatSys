package com.soaplat.cmsmgr.EncryptService;

import com.soaplat.cmsmgr.bean.EncryptList;

public interface EncryptServiceServiceIface {

	// 添加加扰任务
//	int  AddTask ( uint  taskId, byte  taskPrio, byte[]  taskContent, out string errMsg )
	public void addTask();
	
	
	// 查询指定任务的状态
//	int  GetTaskStatus ( uint taskId, out string addTime, out byte prio, 
//			int state ,out string sc,out string errMsg )
	public void GetTaskStatus();
}
