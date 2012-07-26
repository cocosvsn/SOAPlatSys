package com.soaplat.cmsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.soaplat.cmsmgr.bean.ProgListFile;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IProgListFileManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ProgListFileManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgListFileManagerImpl implements IProgListFileManager {
	
	/** The ProgListFiledao. */
	IBaseDAO baseDAO;
	
	/** The getcmspk. */
	IGetPK getcmspk;
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		//��ɾ��
		if(object.length>0){
			for(int i=0;i<object.length;i++){
				baseDAO.delete(object[i]);
			}
		}else{
			return ;
		}	
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {

		ProgListFile progListFile=(ProgListFile)baseDAO.getById(ProgListFile.class, pkid);
		if(progListFile!=null){
			baseDAO.delete(progListFile);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List progListFilelist=baseDAO.findAll("ProgListFile","progListFileid");
		return progListFilelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List progListFilelist=baseDAO.findByProperty("ProgListFile", propertyName, value);
		return progListFilelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgListFile progListFile=(ProgListFile)baseDAO.getById(ProgListFile.class, pkid);
		return progListFile;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgListFile progListFile=(ProgListFile)baseDAO.loadById(ProgListFile.class, pkid);
		return progListFile;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) 
	{
		ProgListFile progListFile = new ProgListFile();
		progListFile = (ProgListFile)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("ProgListFile", "columnfileid");
		String strMaxPK = getcmspk.GetTablePK("ProgListFile", strCurMaxPK);
		progListFile.setColumnfileid(strMaxPK);
		baseDAO.save(progListFile);

		return progListFile;

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);
		

	}

	/**
	 * Sets the baseDAO.
	 * 
	 * @param baseDAO the new baseDAO
	 */


	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		if (object.length>0){
			for(int i=0;i<object.length;i++){
				this.save(object[i]);
			}
			
		}
		
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		if (object.length>0){
			for(int i=0;i<object.length;i++){
				this.update(object[i]);
			}
			
		}
		
	}

	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}

	// 转换日期date(yyyy-MM-dd)为主键格式：yyyyMMdd000000
	private String convertDateToScheduleDate(String date)
	{
		String[] strl = date.split("-");
		String newDate = "";
		for(int i = 0; i < strl.length; i++)
		{
			if(strl[i] != null && strl[i] != "")
			{
				newDate += strl[i];
			}
		}
		return newDate + "000000";
	}
	
	// 这就是传说中的方法7
	// 20100119 10:38
	// 根据date、栏目的defcatseq、filetype，查询proglistfile，得到ProgListFile列表
	// 如果栏目defcatseq为空，则忽略该条件
	public List getProgListFilesByDateFiletypeDefcatseq(
			String date,						// 栏目单日期，格式：yyyy-MM-dd
			Long filetype,						// 文件类型，0-PAGEXML,1-JS,2-HTML,9-BROADCASTXML
			String defcatseq					// 栏目code序列
			)
	{
		// 返回：
		// List<Object[]>
		//		(ProgListFile)Object[0]
		//		(PortalColumn)Object[1]
		
		if(defcatseq == null)
			defcatseq = "";
		defcatseq = defcatseq + "%";
		
		String scheduledate = convertDateToScheduleDate(date);
		
		Map map = new HashMap(0);
		map.put("scheduledate", scheduledate);			// 
		map.put("filetype", filetype);			// 
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery("select_ProgListFilesByDateFiletypeDefcatseq", map);
		return list;
	}

	public void saveProgListFile(ProgListFile progListFile) {
		this.baseDAO.save(progListFile);
	}

	/**
	 * 获取ProgListFile表当前最大主键
	 * @return 返回当前最大主键
	 */
	public String getProgListFileCurrMaxPK() {
		return this.baseDAO.getMaxPropertyValue("ProgListFile", "columnfileid");
	}

	/**
	 * 根据编单日期和[文件名|文件类型|节目包ID]查询ProgListFile信息
	 * @param scheduleDate 编单日期, 格式: 20100909000000
	 * @param value [文件名 [*.*]|文件类型 [6|7]|节目包ID[PPVP20100526160127000156]]
	 * @return 如果存在返回ProgListFile对象, 反之返回null
	 */
	@SuppressWarnings("unchecked")
	public ProgListFile existPackageOrNoticeJs(String scheduleDate, 
			String value) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("scheduleDate", scheduleDate);
		String hql = "from ProgListFile as p where p.scheduledate = :scheduleDate and p.state1 = 0 ";
		if (-1 != value.indexOf(".")) {
			hql += "and p.filename = :value";
			map.put("value", value);
		} else if(1 == value.length()) {
			hql += "and p.filetype = :value";
			map.put("value", Long.valueOf(value));
		} else {
			hql += "and p.pop = :value";
			map.put("value", value);
		}
		List<ProgListFile> list = (List<ProgListFile>) this.baseDAO.query(hql, map);
		if (0 < list.size()) {
			return list.get(0);
		}
		return null;
	}

	public void save() throws Exception {
		ProgListFile progListFile = new ProgListFile();
		String strCurMaxPK = baseDAO.getMaxPropertyValue("ProgListFile", "columnfileid");
		String strMaxPK = getcmspk.GetTablePK("ProgListFile", strCurMaxPK);
		progListFile.setColumnfileid(strMaxPK);
		baseDAO.save(progListFile);
		throw new RuntimeException("就是要出错!");
	}
	
	
	/**
	 * 20110124 12:26 1.23 根据日期、filetype、有效标识、文件名，查询ProgListFile
	 */
	public List<ProgListFile> getProgListFilesByScheduledateFiletypeState1Filename(
			String scheduledate,
			long filetype,
			long state1,
			String filename
			)
	{
		List<ProgListFile> progListFiles = null;
		Map map = new HashMap(0);
		map.put("scheduledate", scheduledate);
		map.put("filetype", filetype);
		map.put("state1", state1);
		map.put("filename", filename);
		progListFiles = (List<ProgListFile>)baseDAO.queryByNamedQuery("getProgListFilesByScheduledateFiletypeState1Filename", map);
		return progListFiles;
	}
	
	
	
}
