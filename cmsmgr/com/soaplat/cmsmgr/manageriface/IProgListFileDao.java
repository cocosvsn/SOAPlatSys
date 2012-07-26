/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.manageriface;

import java.util.Date;
import java.util.List;
import com.soaplat.cmsmgr.bean.ProgListFile;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-1 上午11:33:17
 */
public interface IProgListFileDao {
	
	/**
	 * 根据日期区间和文件类型查询播发单
	 * @param before 播发单起始时间
	 * @param end 播发单结束时间
	 * @param fileType 文件类型. 播发单=9
	 * @return 日期区间所有播发单
	 */
	public List<ProgListFile> queryPlfByDate(String before, String end, long fileType);
	
	/**
	 * 查询播发单的播发日期
	 * @param scheduledate
	 * @param fileType
	 * @return
	 */
	public Date queryPlfByScheduledateAndFileType(String scheduledate, long fileType);
}
