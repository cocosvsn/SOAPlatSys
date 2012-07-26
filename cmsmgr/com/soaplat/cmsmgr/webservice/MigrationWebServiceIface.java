package com.soaplat.cmsmgr.webservice;

import javax.jws.WebService;

@WebService
public interface MigrationWebServiceIface {

	// 20100130 16:28
	// 完成迁移到一级库，迁移模块完成迁移后，通过webservice调用这个接口
	// 返回：0 - 成功 ； 1 - 失败
	public int finishMigration(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes,				// 失败原因
			String type						// 0 - 上海； 1 - 北京
			);
	
	

}
