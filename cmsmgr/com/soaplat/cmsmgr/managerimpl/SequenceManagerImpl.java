/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.managerimpl;

import java.util.List;

import com.soaplat.cmsmgr.manageriface.ISequenceManager;
import com.soaplat.sysmgr.common.IBaseDAO;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-11-26 下午04:26:15
 */
public class SequenceManagerImpl implements ISequenceManager {
	private IBaseDAO baseDAO;
	
	/**
	 * 获取文件表ContentId的值
	 * @return
	 */
	public String getContentId() {
		String sql = "select CMS.SEQCONTENTID.nextval from dual";
		List<?> list = this.baseDAO.queryBySQL(sql, null);
		if (0 < list.size()) {
			return list.get(0).toString();
		}
		throw new RuntimeException(" 获取数据库Sequence失败! ");
	}
	
	/**
	 * 获取产品信息表IPPV的值
	 * @return
	 */
	public String getIppvId() {
		String sql = "select CMS.SEQIPPVID.nextval from dual";
		List<?> list = this.baseDAO.queryBySQL(sql, null);
		if (0 < list.size()) {
			return list.get(0).toString();
		}
		throw new RuntimeException(" 获取数据库Sequence失败! ");
	}

	/**--------- setter and getter ---------**/
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
}
