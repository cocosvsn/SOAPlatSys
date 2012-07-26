/**
* copyright (c) by A-one 2010
*/
package com.soaplat.amsmgr.manageriface;

import java.util.List;

import com.soaplat.amsmgr.bean.AmsStoragePrgRelBack;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-11 下午12:13:11
 */
public interface IAmsStoragePrgRelBackManager {
	/**
	 * 保存
	 * @param amsStoragePrgRelBacks
	 */
	public void save(AmsStoragePrgRelBack... amsStoragePrgRelBacks);
	
	/**
	 * 修改
	 * @param amsStoragePrgRelBacks
	 */
	public void update(AmsStoragePrgRelBack amsStoragePrgRelBacks);
	
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
	public AmsStoragePrgRelBack getAmsStoragePrgRelBackById(String pkid);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<?> queryAll();
}
