/**
* copyright (c) by A-one 2010
*/
package com.soaplat.amsmgr.manageriface;

import java.util.List;

import com.soaplat.amsmgr.bean.AmsStorageHistory;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-11 下午12:49:35
 */
public interface IAmsStorageHistoryManager {
	/**
	 * 保存
	 * @param amsStorageHistories
	 */
	public void save(AmsStorageHistory... amsStorageHistories);
	
	/**
	 * 修改
	 * @param amsStorageHistory
	 */
	public void update(AmsStorageHistory amsStorageHistory);
	
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
	public AmsStorageHistory getAmsStorageHistoryById(String pkid);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<?> queryAll();
	
	/**
	 * 获取当前操作历史表中ID最大值
	 * @return
	 */
	public String getCurrMaxID();
}
