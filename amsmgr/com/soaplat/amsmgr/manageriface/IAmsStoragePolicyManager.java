/**
* copyright (c) by A-one 2010
*/
package com.soaplat.amsmgr.manageriface;

import java.util.List;

import com.soaplat.amsmgr.bean.AmsStoragePolicy;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-11 下午12:43:46
 */
public interface IAmsStoragePolicyManager {
	/**
	 * 保存
	 * @param amsStoragePolicies
	 */
	public void save(AmsStoragePolicy... amsStoragePolicies);
	
	/**
	 * 修改
	 * @param amsStoragePolicy
	 */
	public void update(AmsStoragePolicy amsStoragePolicy);
	
	/**
	 * 删除
	 * @param pkid
	 */
	public void delete(String pkid);
	
	/**
	 * 根据主键ID查询
	 * @param pkid
	 * @return
	 */
	public AmsStoragePolicy getAmsStoragePolicyById(String pkid);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<?> queryAll();
	
	/**
	 * 根据存储等级ID查询该存储体等级的文件删除策略
	 * @param storageClassID 存储体等级ID
	 * @return 返回指定存储等级的文件删除策略
	 */
	public List<?> queryByStorageClassId(String storageClassID);
}
