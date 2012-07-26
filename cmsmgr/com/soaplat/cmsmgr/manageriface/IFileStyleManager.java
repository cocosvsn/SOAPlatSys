/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.manageriface;

import java.util.List;
import com.soaplat.cmsmgr.bean.FileStyle;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-10-11 下午03:04:55
 */
public interface IFileStyleManager {
	/**
	 * 根据文件样式Code和样式ID查询文件样式信息
	 * @param styleCode 文件样式Code
	 * @param sytleID 样式ID
	 * @return List<FileStyle> 返回文件样式信息
	 */
	public List<FileStyle> queryFileStylesByStyleCodeAndStyleID(
			long styleCode, long sytleID);
	
	/**
	 * 获取所有有效文件样式ID
	 * @param styleCode 文件样式Code
	 * @param sytleID 样式ID
	 * @return List<String> 返回有效文件样式ID
	 */
	public List<String> getValidateFileStyleId(long styleCode, long sytleID);
}
