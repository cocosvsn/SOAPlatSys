/**
* copyright (c) by A-one 2010
*/
package com.soaplat.amsmgr.managerimpl;

import java.util.List;

import com.soaplat.amsmgr.bean.AmsStorageHistory;
import com.soaplat.amsmgr.manageriface.IAmsStorageHistoryManager;
import com.soaplat.sysmgr.common.IBaseDAO;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-11 下午12:45:06
 */
public class AmsStorageHistoryManagerImpl implements IAmsStorageHistoryManager {
	private IBaseDAO baseDAO;
	
	/**
	 * 保存
	 * @param amsStorageHistories
	 */
	public void save(AmsStorageHistory... amsStorageHistories) {
		for (AmsStorageHistory amsStorageHistory : amsStorageHistories) {
			this.baseDAO.save(amsStorageHistory);
		}
	}
	
	/**
	 * 修改
	 * @param amsStorageHistory
	 */
	public void update(AmsStorageHistory amsStorageHistory) {
		this.baseDAO.update(amsStorageHistory);
	}
	
	/**
	 * 删除
	 * @param pkid
	 */
	public void delete(String pkid) {
		this.baseDAO.delete(this.baseDAO.getById(AmsStorageHistory.class, pkid));
	}
	
	/**
	 * 根据主键ID查询
	 * @param pkid
	 * @return
	 */
	public AmsStorageHistory getAmsStorageHistoryById(String pkid) {
		return (AmsStorageHistory) this.baseDAO.getById(AmsStorageHistory.class, pkid);
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<?> queryAll() {
		return this.baseDAO.queryByNamedQuery("");
	}
	
	/**
	 * 获取当前操作历史表中ID最大值
	 * @return
	 */
	public String getCurrMaxID() {
		List<?> list = this.baseDAO.queryByNamedQuery("query.AmsStorageHistory.max.ID");
		String currMaxID = (String) list.get(0);
		return null == currMaxID ? "0000000000" : currMaxID;
	}
	
	/** ---------------- setter and getter ----------------- **/
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}
