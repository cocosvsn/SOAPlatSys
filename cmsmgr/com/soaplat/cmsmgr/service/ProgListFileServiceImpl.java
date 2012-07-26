/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.manageriface.IProgListFileDao;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-1 下午02:00:44
 */
public class ProgListFileServiceImpl implements IProgListFileService {
	private IProgListFileDao progListFileDao;

	public ProgListFileServiceImpl() {
		this.progListFileDao = (IProgListFileDao) ApplicationContextHolder.webApplicationContext.getBean("progListFileDao");
	}
	/**
	 * 根据日期区间查询播发单
	 * @param before 开始日期, 必须是日期格式. 如: 2010-06-01
	 * @param end 结果日期, 格式同开始日期.
	 * @return 播发单集合
	 */
	public CmsResultDto queryPlfByDate(String before, String end) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
		String parseBefore = null;
		String parseEnd = null;
		try {
			Date beforedDate = df.parse(before);
			Date endDate = df.parse(end);
			
			parseBefore = df2.format(beforedDate);
			parseEnd = df2.format(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		CmsResultDto crd = new CmsResultDto();

		try {
			List<ProgListFile> list = this.progListFileDao.queryPlfByDate(parseBefore, parseEnd, 9l);
			crd.setResultCode(0l);
			crd.setResultObject(list);
		} catch (Exception e) {
			crd.setResultCode(1l);
			crd.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
//		for (ProgListFile progListFile : list) {
//			try {
//				Document doc = DocumentHelper.parseText(progListFile.getColumnxml());
//				List<?> date = doc.selectNodes("/PushVod/@BroadcastTime");
//				if(date.size() > 0) {
//					DefaultAttribute attribute = (DefaultAttribute) date.get(0);
//					progListFile.setRemark(attribute.getValue());
//				}
//			} catch (DocumentException e) {
//				System.out.println("页面XML文件格式不正确.. ProgListFileServiceImpl");
//				e.printStackTrace();
//			}
//		}
		
		
		return crd;
	}
	
	@SuppressWarnings("unchecked")
	public void test() {
		CmsResultDto crd = this.queryPlfByDate("2010-05-01", "2010-07-01");
		List<ProgListFile> list = (List<ProgListFile>) crd.getResultObject();
		for (ProgListFile progListFile : list) {
			System.out.println(progListFile.getColumnxml());
		}
	}

	/*---------------- setter and getter -----------------*/
	
	public void setProgListFileDao(IProgListFileDao progListFileDao) {
		this.progListFileDao = progListFileDao;
	}
}
