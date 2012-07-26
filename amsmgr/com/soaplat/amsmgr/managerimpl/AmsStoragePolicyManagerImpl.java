/**
* copyright (c) by A-one 2010
*/
package com.soaplat.amsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.amsmgr.bean.AmsStoragePolicy;
import com.soaplat.amsmgr.manageriface.IAmsStoragePolicyManager;
import com.soaplat.sysmgr.common.IBaseDAO;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-11 下午12:14:17
 */
public class AmsStoragePolicyManagerImpl implements IAmsStoragePolicyManager {
	private IBaseDAO baseDAO;
	
	/**
	 * 保存
	 * @param amsStoragePolicies
	 */
	public void save(AmsStoragePolicy... amsStoragePolicies) {
		for (AmsStoragePolicy amsStoragePolicy : amsStoragePolicies) {
			this.baseDAO.save(amsStoragePolicy);
		}
	}
	
	/**
	 * 修改
	 * @param amsStoragePolicy
	 */
	public void update(AmsStoragePolicy amsStoragePolicy) {
		this.baseDAO.update(amsStoragePolicy);
	}
	
	/**
	 * 删除
	 * @param pkid
	 */
	public void delete(String pkid) {
		this.baseDAO.delete(this.baseDAO.getById(AmsStoragePolicy.class, pkid));
	}
	
	/**
	 * 根据主键ID查询
	 * @param pkid
	 * @return
	 */
	public AmsStoragePolicy getAmsStoragePolicyById(String pkid) {
		return (AmsStoragePolicy) this.baseDAO.getById(AmsStoragePolicy.class, pkid);
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<?> queryAll() {
		return this.baseDAO.queryByNamedQuery("");
	}
	
	/**
	 * 根据存储等级ID查询该存储体等级的文件删除策略
	 * @param storageClassID 存储体等级ID
	 * @return 返回指定存储等级的文件删除策略
	 */
	public List<?> queryByStorageClassId(String storageClassID) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("storageClassID", storageClassID);
		return this.baseDAO.queryByNamedQuery(
				"query.AmsStoragePolicy.by.storageClassID", map);
	}
	
	/** ---------------- setter and getter ----------------- **/
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}
