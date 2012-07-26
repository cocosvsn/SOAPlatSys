/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.cmsmgr.manageriface.IProgListFileDao;
import com.soaplat.sysmgr.common.IBaseDAO;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-1 上午11:40:47
 */
public class ProgListFileDaoImpl implements IProgListFileDao {
	//private ICommon<ProgListFile> common;
	private IBaseDAO baseDAO;

	/**
	 * 根据日期区间和文件类型查询播发单
	 * @param before 播发单起始时间
	 * @param end 播发单结束时间
	 * @param fileType 文件类型. 播发单=9
	 * @return 日期区间所有播发单
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListFile> queryPlfByDate(String before, String end, long fileType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("before", before);
		params.put("end", end);
		params.put("fileType", fileType);
		return this.baseDAO.queryByNamedQuery(
				"query.proglistfile.by.date.and.filetype", params);
	}
	
	/**
	 * 查询播发单的播发日期
	 * @param scheduledate
	 * @param fileType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Date queryPlfByScheduledateAndFileType(String scheduledate, long fileType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schedule", scheduledate);
		map.put("fileType", fileType);
		List list = this.baseDAO.queryByNamedQuery(
				"query.proglistfile.by.scheduledate.and.filetype", map);
		return (Date) (list.size() > 0 ? list.get(0) : null);
	}

	/*---------------- setter and getter -----------------*/
//	public void setCommon(ICommon<ProgListFile> common) {
//		this.common = common;
//	}
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}
