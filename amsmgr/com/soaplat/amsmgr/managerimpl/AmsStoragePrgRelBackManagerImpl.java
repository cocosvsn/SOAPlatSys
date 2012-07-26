/**
* copyright (c) by A-one 2010
*/
package com.soaplat.amsmgr.managerimpl;

import java.util.List;
import com.soaplat.amsmgr.bean.AmsStoragePrgRelBack;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelBackManager;
import com.soaplat.sysmgr.common.IBaseDAO;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-11 上午11:53:17
 */
public class AmsStoragePrgRelBackManagerImpl implements IAmsStoragePrgRelBackManager {
	private IBaseDAO baseDAO;
	
	/**
	 * 保存
	 * @param amsStoragePrgRelBacks
	 */
	public void save(AmsStoragePrgRelBack... amsStoragePrgRelBacks) {
		for (AmsStoragePrgRelBack amsStoragePrgRelBack : amsStoragePrgRelBacks) {
			this.baseDAO.save(amsStoragePrgRelBack);
		}
	}
	
	/**
	 * 修改
	 * @param amsStoragePrgRelBacks
	 */
	public void update(AmsStoragePrgRelBack amsStoragePrgRelBacks) {
		this.baseDAO.update(amsStoragePrgRelBacks);
	}
	
	/**
	 * 删除
	 * @param pkid
	 */
	public void delete(String pkid) {
		this.baseDAO.delete(this.baseDAO.getById(AmsStoragePrgRelBack.class, pkid));
	}
	
	/**
	 * 根据主键ID查询
	 * @param pkid
	 * @return
	 */
	public AmsStoragePrgRelBack getAmsStoragePrgRelBackById(String pkid)  {
		return (AmsStoragePrgRelBack) this.baseDAO.getById(AmsStoragePrgRelBack.class, pkid);
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<?> queryAll() {
		return this.baseDAO.queryByNamedQuery("");
	}
	
	/** ---------------- setter and getter ----------------- **/
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}
