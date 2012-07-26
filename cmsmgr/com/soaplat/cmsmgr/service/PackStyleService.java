/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.service;

import java.util.List;

import com.soaplat.cmsmgr.bean.PackStyle;
import com.soaplat.cmsmgr.manageriface.IPackStyleManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-8-26 下午05:52:06
 */
public class PackStyleService {
	private IPackStyleManager packStyleManager;
	
	public PackStyleService() {
		this.packStyleManager = (IPackStyleManager) ApplicationContextHolder
				.webApplicationContext .getBean("packStyleManager");
	}
	
	/**
	 * 根据报纸栏目名称, 查询报纸样式
	 * @param packStyleName
	 * @return
	 */
	public PackStyle getPackStyleByName(String packStyleName) {
		return this.packStyleManager.getPackStyleByName(packStyleName + "节目包样式");
	}
	
	/**
	 * 查询所有节目样式
	 * @return List<Object[]>
	 * objects[0]:  PackStyle.styleid
	 * objects[1]:  PackStyle.stylename
	 */
	public List<Object[]> getProgramStyle() {
		return this.packStyleManager.getPackaStyleByStyleType(2L);
	}
	
	public void setPackStyleManager(IPackStyleManager packStyleManager) {
		this.packStyleManager = packStyleManager;
	}
}
