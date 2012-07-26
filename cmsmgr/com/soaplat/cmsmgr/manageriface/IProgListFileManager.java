package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgListFileManager extends IBaseManager {
	public void saveProgListFile(ProgListFile progListFile);
	
	/**
	 * 获取ProgListFile表当前最大主键
	 * @return 返回当前最大主键
	 */
	public String getProgListFileCurrMaxPK();
	
	/**
	 * 根据编单日期和[文件名|文件类型|节目包ID]查询ProgListFile信息
	 * @param scheduleDate 编单日期, 格式: 20100909000000
	 * @param value [文件名 [*.*]|文件类型 [6|7]|节目包ID[PPVP20100526160127000156]]
	 * @return 如果存在返回ProgListFile对象, 反之返回null
	 */
	@SuppressWarnings("unchecked")
	public ProgListFile existPackageOrNoticeJs(String scheduleDate, 
			String value);

	// 这就是传说中的方法7
	// 20100119 10:38
	// 根据date、栏目的defcatseq、filetype，查询proglistfile，得到ProgListFile列表
	// 如果栏目defcatseq为空，则忽略该条件
	public List getProgListFilesByDateFiletypeDefcatseq(
			String date,						// 栏目单日期，格式：yyyy-MM-dd
			Long filetype,						// 文件类型，0-PAGEXML,1-JS,2-HTML,9-BROADCASTXML
			String defcatseq					// 栏目code序列
			);

	public void save() throws Exception;
	
	
	
	/**
	 * 20110124 12:26 1.23 根据日期、filetype、有效标识、文件名，查询ProgListFile
	 */
	public List<ProgListFile> getProgListFilesByScheduledateFiletypeState1Filename(
			String scheduledate,
			long filetype,
			long state1,
			String filename
			);
}
