/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.managerimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.soaplat.cmsmgr.bean.FileStyle;
import com.soaplat.cmsmgr.manageriface.IFileStyleManager;
import com.soaplat.sysmgr.common.IBaseDAO;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-10-11 下午02:30:19
 */
public class FileStyleManagerImpl implements IFileStyleManager {
	private IBaseDAO baseDAO;
	
	/**
	 * 根据文件样式Code和样式ID查询文件样式信息
	 * @param styleCode 文件样式Code
	 * @param sytleID 样式ID
	 * @return List<FileStyle> 返回文件样式信息
	 */
	@SuppressWarnings("unchecked")
	public List<FileStyle> queryFileStylesByStyleCodeAndStyleID(
			long styleCode, long sytleID) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("styleCode", styleCode);
		map.put("styleId", sytleID);
		return this.baseDAO.queryByNamedQuery(
				"query.FileStyle.by.styleCode.styleId", map);
	}
	
	/**
	 * 获取所有有效文件样式ID
	 * @param styleCode 文件样式Code
	 * @param sytleID 样式ID
	 * @return List<String> 返回有效文件样式ID
	 */
	public List<String> getValidateFileStyleId(long styleCode, long sytleID) {
		List<FileStyle> fileStyles = this.queryFileStylesByStyleCodeAndStyleID(
				styleCode, sytleID);
		List<String> fileStyleIds = new ArrayList<String>();
		for (FileStyle fileStyle : fileStyles) {
			fileStyleIds.add(fileStyle.getFileTypeId());
		}
		return fileStyleIds;
	}
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}
