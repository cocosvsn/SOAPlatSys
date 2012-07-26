/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.service;

import com.soaplat.cmsmgr.dto.CmsResultDto;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-1 下午01:54:16
 */
public interface IProgListFileService {

	/**
	 * 根据日期区间查询播发单
	 * @param before 开始日期, 必须是日期格式. 如: 2010-06-01
	 * @param end 结果日期, 格式同开始日期.
	 * @return 播发单集合
	 */
	public CmsResultDto queryPlfByDate(String before, String end);
}
