package com.soaplat.amsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

/**
 * Title 		:the Interface IAmsStorageManager.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public interface IAmsStorageManager  extends IBaseManager{

//	// Edited by Andy at 20100111 15:10
//	// ��ݴ洢��ȼ�����ѯ�洢��ȼ��?�洢��?�õ��洢��
//	public List getAmsStorageDirsByStclasscode(String stclasscode);
	
	/**
	 * 根据存储体等级ID查询存储体和存储体等级信息
	 * @param storageClassId 存储体等级ID
	 * @return 返回List<Object[2]>  <br />
	 * object[0]: AmsStorage 
	 * object[1]: AmsStorageClass
	 */
	public List<?> queryByStorageClassId(String storageClassId);
	
	/**
	 * 根据存储体等级Code查询该存储等级的存储体ID
	 * @param storageClassCode 存储体等级Code
	 * @return 返回指定等级的存储体ID
	 * @throws Exception 如果根据存储等级Code查询不到存储体信息, 则抛出异常
	 */
	public String queryStorageIdByStorageClassCode(String storageClassCode)
			throws Exception;
}
