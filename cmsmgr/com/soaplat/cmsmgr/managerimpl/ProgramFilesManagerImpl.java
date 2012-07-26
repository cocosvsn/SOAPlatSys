package com.soaplat.cmsmgr.managerimpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.soaplat.cmsmgr.bean.FileStyle;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IFileStyleManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ProgramFilesManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgramFilesManagerImpl implements IProgramFilesManager {
	
	/** The programfilesdao. */
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

		ProgramFiles programfiles=(ProgramFiles)baseDAO.getById(ProgramFiles.class, pkid);
		if(programfiles!=null){
			baseDAO.delete(programfiles);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List programfileslist=baseDAO.findAll("ProgramFiles","progfileid");
		return programfileslist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List programfileslist=baseDAO.findByProperty("ProgramFiles", propertyName, value);
		return programfileslist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgramFiles programfiles=(ProgramFiles)baseDAO.getById(ProgramFiles.class, pkid);
		return programfiles;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgramFiles programfiles=(ProgramFiles)baseDAO.loadById(ProgramFiles.class, pkid);
		return programfiles;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		//String strMaxPK = "";
//		String preStr = "";
//		String filetype = "";
		ProgramFiles programfiles = new ProgramFiles();
		programfiles = (ProgramFiles)object;
//		String programid = programfiles.getProgramid();
//		filetype = programfiles.getFiletypeid();
//		Map map = new HashMap(0);
//		preStr = programid + "00" + filetype + "%";
//		map.put("id", preStr);
//		List list = baseDAO.queryByNamedQuery("select_maxprogfileid",map);
		//if (list.get(0)==null)
		//{
		//	strMaxPK=getcmspk.GetTablePK("ProgramFiles", programid+"00"+filetype);
			
	//	}else
		//{
		//	strMaxPK=list.get(0).toString();
	//		strMaxPK=getcmspk.GetTablePK("ProgramFiles", strMaxPK);
	//	}
		//programfiles.setProgfileid(strMaxPK);

//		String strCurMaxPK = baseDAO.getMaxPropertyValue("ProgramFiles", "progfileid");
//		String strMaxPK = getcmspk.GetTablePK("ProgramFiles", strCurMaxPK);
//		programfiles.setProgfileid(strMaxPK);
		baseDAO.save(programfiles);
		//
		return baseDAO.getById(ProgramFiles.class, programfiles.getProgfileid());
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);
		

	}



	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}

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
	public List findbyprogramidfiletype(String programid,String filetypeid)
	{
		Map map = new HashMap(0);
		map.put("programid", programid);
		map.put("filetypeid", filetypeid);
		List list=baseDAO.queryByNamedQuery("select_programidfiletypeid",map);
		return list;
	}
	public List findstoragelistbyfileid(String programid,String filetypeid)
	{
		Map map = new HashMap(0);
		map.put("programid", programid);
		map.put("filetypeid", filetypeid);
		List list=baseDAO.queryByNamedQuery("select_storagelistbyfileid",map);
		return list;
	}
	public String findmaxprogramfileidbyprogramidandfiletype(String programid, String filetypeid)
	{
		String preStr = "";
		String strMaxPK = "";
		Map map = new HashMap(0);
		preStr = programid + filetypeid + "%";
		map.put("id", preStr);
		List list = baseDAO.queryByNamedQuery("select_maxprogfileid", map);
		if (list.get(0) == null)
		{
			strMaxPK = getcmspk.GetTablePK("ProgramFiles", programid + filetypeid);
			
		}else
		{
			strMaxPK = list.get(0).toString();
			strMaxPK = getcmspk.GetTablePK("ProgramFiles", strMaxPK);
		}
		return strMaxPK;
	}
	
	/**
	 * 获取节目文件ID
	 * @param programInfoId
	 * @return
	 */
	public String getProgramFilesID(String programInfoId) {
		String strMaxPK;
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("id", programInfoId + "001" + "%");
		List list = baseDAO.queryByNamedQuery("select_maxprogfileid", map);
		if (list.get(0) == null) {
			strMaxPK = getcmspk.GetTablePK("ProgramFiles", programInfoId + "001");

		} else {
			strMaxPK = list.get(0).toString();
			strMaxPK = getcmspk.GetTablePK("ProgramFiles", strMaxPK);
		}
		return strMaxPK;
	}

	/**
	 * 根据节目包ID, 查询该节目包所有文件信息
	 * @param progPackageID 节目包(ProgPackage)ID
	 * @return 返回节目包下所有文件信息
	 */
	@SuppressWarnings("unchecked")
	public List<ProgramFiles> queryProgramFilesByProgPackageID(
			String progPackageID, List<String> fileTypeIds) {
		if (1 > fileTypeIds.size()) {
			return new ArrayList<ProgramFiles>(0);
		}
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("progPackageID", progPackageID);
		map.put("fileTypeIds", fileTypeIds);
		return (List<ProgramFiles>) this.baseDAO.queryNamed(
				"query.ProgramFiles.by.progPackageID.fileTypeIds", map);
	}
	
	/**
	 * 根据节目包ID查询节目包中主文件ID
	 * @param progPackageId 节目包ID
	 * @return 返回节目包中主文件ID
	 */
	@SuppressWarnings("unchecked")
	public String queryProgPackageMainFileId(String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageId", progPackageId);
		List<String> programFileIds = this.baseDAO.queryByNamedQuery(
				"query.ProgramFiles.progfileid.by.progPackageId.progrank", map);
		return programFileIds.get(0);
	}
	
	/**
	 * 根据节目包ID查询节目包中主文件contentID
	 * @param progPackageId 节目包ID
	 * @return 返回节目包中主文件contentID
	 */
	@SuppressWarnings("unchecked")
	public String queryContentIdByProgPackage(String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageId", progPackageId);
		List<String> programFileIds = this.baseDAO.queryByNamedQuery(
				"query.ProgramFiles.contentId.by.progPackageId.progrank", map);
		return programFileIds.get(0);
	}
	
	/**
	 * 根据节目包ID, 文件Code查询文件ID
	 * @param progPackageId 节目包ID
	 * @param fileCodes 文件Code
	 * @return 返回文件ID集合
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryProgramFileIdByProgPackageIdFileCode(String progPackageId,
			List<String> fileCodes) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("progPackageId", progPackageId);
		map.put("fileCodes", fileCodes);
		return (List<String>) this.baseDAO.queryNamed(
				"query.ProgramFiles.progfileid.by.progPackageId.fileCode", map);
	}
	
	/**
	 * 根据节目包ID查询该节目包下所有文件
	 * @param progPackageId 节目包ID
	 * @return 返回节目包所有文件信息
	 */
	@SuppressWarnings("unchecked")
	public List<ProgramFiles> queryProgramFilesByProgPackageID(
			String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageID", progPackageId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgramFiles.by.progPackageID", map);
	}
	
	/**
	 * 根据节目ID列表查询文件信息
	 * @param proggramInfoIds 节目ID列表
	 * @return 返回文件信息
	 */
	@SuppressWarnings("unchecked")
	public List<ProgramFiles> queryProgramFilesByProgramInfoId(List<String> proggramInfoIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("proggramInfoIds", proggramInfoIds);
		return this.baseDAO.queryByNamedQuery("", map);
	}
	
	public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-common.xml",
				"applicationContext-cmsmgrdaobeans.xml",
				"applicationContext-amsmgrmanager.xml",
				"applicationContext-cmsmgrmanager.xml",
				"applicationContext-contentmgrmanagerbeans.xml",
				"applicationContext-contentmgrdaobeans.xml",
				"applicationContext-srmmgrmanager.xml",
				"applicationContext-srmmgrdaobeans.xml",
				"applicationContext-cmsmgrdaobeans.xml",
				"applicationContext-sysmgrmanager.xml",
				"applicationContext-sysmgrdao.xml",
				"applicationContext-amsmgrdaobeans.xml"
		});
		
		IProgramFilesManager programFilesManager = 
				(IProgramFilesManager) app.getBean("programfilesManager");
		String[] fileStyleIds = {"PNG", "H264"};
		List<ProgramFiles> programFiles = programFilesManager
				.queryProgramFilesByProgPackageID("PPVP20100601105724000593", Arrays.asList(fileStyleIds));
		System.out.println(programFiles.size());
	}
	
	
	
	
	/**
	 * 根据节目包和应用code，获得文件列表
	 * @param fileStyleManager
	 * @param packageFilesManager
	 * @param progPackage
	 * @param fileStyleCode
	 * @return
	 */
	public List<ProgramFiles> getProgramFilesesByProgPackageFilestylecode(
			IFileStyleManager fileStyleManager,
			IPackageFilesManager packageFilesManager,
			ProgPackage progPackage,
			long fileStyleCode 
			)
	{
		List<ProgramFiles> programFileses = null;//new ArrayList<ProgramFiles>();	// 需要的节目包文件
		
		List<FileStyle> fileStyles = fileStyleManager.queryFileStylesByStyleCodeAndStyleID(fileStyleCode, progPackage.getStyleid());
		if(fileStyles == null || fileStyles.size() <= 0)
		{
			return programFileses;
		}
		else
		{
			programFileses = new ArrayList<ProgramFiles>();
			for(int m = 0; m < fileStyles.size(); m++)
			{
				FileStyle fileStyle = fileStyles.get(m);
				List<ProgramFiles> pfs = packageFilesManager.getProgramFilesesByProductidFiletype(
						progPackage.getProductid(), 
						fileStyle.getFileTypeId()
						);
				
				/**
				 * HuangBo update by 2011年3月25日 17时52分
				 */
				// TODO 暂时先这么写, 如果发现有主文件未迁移则修改此处
				for (ProgramFiles programFiles : pfs) {
					if (0 == programFiles.getProgrank()) {
						
						programFileses.add(programFiles);
					}
				}
			}
		}
		return programFileses;
	}
}
