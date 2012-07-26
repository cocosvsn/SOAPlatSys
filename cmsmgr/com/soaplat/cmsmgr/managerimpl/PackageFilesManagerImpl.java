package com.soaplat.cmsmgr.managerimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageClass;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.cmsmgr.bean.PackageFiles;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class PackageFilesManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class PackageFilesManagerImpl implements IPackageFilesManager {
	private static Logger cmsLogger = Logger.getLogger("Cms");
	/** The productinfodao. */
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

		PackageFiles productinfo=(PackageFiles)baseDAO.getById(PackageFiles.class, Long.valueOf(pkid));
		if(productinfo!=null){
			baseDAO.delete(productinfo);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List productinfolist=baseDAO.findAll("PackageFiles","cmspackageFilesid");
		return productinfolist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List productinfolist=baseDAO.findByProperty("PackageFiles", propertyName, value);
		return productinfolist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		PackageFiles productinfo=(PackageFiles)baseDAO.getById(PackageFiles.class, Long.valueOf(pkid));
		return productinfo;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		PackageFiles productinfo=(PackageFiles)baseDAO.loadById(PackageFiles.class, Long.valueOf(pkid));
		return productinfo;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		PackageFiles packageFiles = new PackageFiles();
		packageFiles = (PackageFiles)object;
//		String strMaxPK = getcmspk.GetTablePK("PackageFiles");
//		packageFiles.setCmspackageFilesid(cmspackageFilesid);
		baseDAO.save(packageFiles);
		Long lMaxPk = packageFiles.getCmspackageFilesid();
		return baseDAO.getById(PackageFiles.class, lMaxPk);

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

	public List getPackageFilesesByProductidProgfileidAndProgramid(String productId, String progFileId, String programId)
	{
		Map map = new HashMap(0);
		map.put("productid", productId);
		map.put("progfileid", progFileId);
		map.put("programid", programId);
		List list = baseDAO.queryByNamedQuery("select_packageFilesesByProductidProgfileidAndProgramid",map);
		return list;
	}
	
	// 20100106 16:11
	// 根据节目包id、filecode、filetype，查询packagefiles、programfiles表，得到file
	public List getProgramFilesesByProductidFilecodeFiletypeid(String productid, String filecode, String filetypeid)
	{
		Map map = new HashMap(0);
		map.put("productid", productid);
		map.put("filecode", filecode);
		map.put("filetypeid", filetypeid);
		List list = baseDAO.queryByNamedQuery("select_ProgramFilesesByProductidFilecodeFiletype",map);
		return list;
	}
	
	// 20100111 12:32
	// 根据 节目包id和存储体等级，查询得到文件列表
	public List getProgramfilesesByProductidAndStclasscode(String productid, String storageclassid)
	{
		Map map = new HashMap(0);
		map.put("productid", productid);
		map.put("stclasscode", storageclassid);
		List list = baseDAO.queryByNamedQuery("select_programfilesesByProductidAndStclasscode",map);
		return list;
	}
	
	// 这就是传说中的方法2
	// 20100115 9:58
	// 根据 文件code 和 存储体等级，查询得到文件存放目标路径
	public List getDestPathByFilecodeStclasscode(
			String filecode, 						// 文件code
			String stclasscode						// 存储体等级code
			)
	{
		// 返回：List
		// 1 - String 目标路径()  格式："smb://hc:hc@172.23.19.66/公用/"
		// 2 - List<Object[]>
		//			(AmsStorage)Object[0]
		//			(AmsStorageDir)Object[1]
		//			(AmsStorageClass)Object[2]
		List retlist = new ArrayList();
		String strHead = "";
		String strPath = "";				// 格式："smb://hc:hc@172.23.19.66/公用/"
		
		Map map = new HashMap(0);
		map.put("filecode", filecode);
		map.put("stclasscode", stclasscode);
		List list = baseDAO.queryByNamedQuery("select_DestPathByFilecodeStclasscode",map);
		
		// 计算目标路径
		if(list.size() > 0)
		{
			Object[] rows = (Object[])list.get(0);
			AmsStorage ams = (AmsStorage)rows[0];
			AmsStorageDir amsd = (AmsStorageDir)rows[1];
			
			String stIP = ams.getStorageip();						// 存储体IP
			String stMapPath = ams.getMappath();					// 映射存储体路径
			String storagedirname = amsd.getStoragedirname();		// 存储体目录
			
			if(stIP == null || stIP.equalsIgnoreCase(""))
			{
				
			}
			else
			{
				// 先不判断"/"或"\\"的格式
				// 最后判断多余的"/"或"\\"，然后在前面补"/"或"\\"
				// 格式："smb://hc:hc@172.23.19.66/公用/"
				if(!"smb".equalsIgnoreCase(ams.getStorageaccstype()) 
						&& !"ftp".equalsIgnoreCase(ams.getStorageaccstype()))
				{
					return null;
				}
				if(ams.getLoginname() == null || ams.getLoginname().equalsIgnoreCase(""))
				{
					strHead = "smb://";
				}
				else
				{
					strHead = "smb://";
					strHead += ams.getLoginname();
					strHead += ":";
					if(ams.getLoginpwd() != null)
					{
						strHead += ams.getLoginpwd();
					}
					strHead += "@";
				}
				strPath = stIP;
				try 
				{
					if(stMapPath != null && !stMapPath.equalsIgnoreCase(""))
					{
						//System.out.println("stMapPath - " + stMapPath);
						stMapPath = stMapPath.replaceAll("\\\\", "/");
						//System.out.println("stMapPath - " + stMapPath);
						if(strPath.charAt(strPath.length() - 1) != '/')
						{
							strPath += "/";
						}
						strPath += stMapPath;
						//System.out.println("strPath - " + strPath);
					}
					if(storagedirname != null && !storagedirname.equalsIgnoreCase(""))
					{
						storagedirname = storagedirname.replaceAll("\\\\", "/");
						if(strPath.charAt(strPath.length() - 1) != '/')
						{
							strPath += "/";
						}
						strPath += storagedirname;
						//System.out.println("strPath - " + strPath);
					}
					
					// 去掉多余的"/"或"\\"
					strPath = strPath.replaceAll("//", "/");
					//System.out.println("strPath - " + strPath);
					
					// 生成完整路径
					strPath = strHead + strPath;
					//System.out.println("strPath - " + strPath);
				} 
				catch (RuntimeException e) 
				{
					// TODO Auto-generated catch block
//					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
			retlist.add(strPath);
			retlist.add(list);
		}
		else
		{
			retlist = null;
		}
		return retlist;
	}
	
	// 这就是传说中的方法3
	// 20100115 14:10
	// 根据文件ID 和 存储体等级，查询，得到源文件存放目标路径
	public List getSourcePathByProgfileidStclasscode(
			String progfileid, 						// 文件code
			String stclasscode						// 存储体等级code
			)
	{
		// 返回：List
		// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
		// 2 - List<Object[]>
		// 		(ProgramFiles)Object[0]
		// 		(AmsStorage)Object[1]
		// 		(AmsStoragePrgRel)Object[2]
		// 		(AmsStorageDir)Object[3]
		// 		(AmsStorageClass)Object[4]
		
		List retlist = new ArrayList();
		String strHead = "";
		String strPath = "";				// 格式："smb://hc:hc@172.23.19.66/公用/test.xml"
		
		Map map = new HashMap(0);
		map.put("progfileid", progfileid);
		map.put("stclasscode", stclasscode);
		List list = baseDAO.queryByNamedQuery("select_SourcePathByProgfileidStclasscode",map);
		
		// 计算目标路径
		if(list.size() > 0)
		{
			Object[] rows = (Object[])list.get(0);
			ProgramFiles pf = (ProgramFiles)rows[0];
			AmsStorage ams = (AmsStorage)rows[1];
			AmsStoragePrgRel amspr = (AmsStoragePrgRel)rows[2];
			AmsStorageDir amsd = (AmsStorageDir)rows[3];
			AmsStorageClass amsc = (AmsStorageClass)rows[4];
			
			String stIP = ams.getStorageip();						// 存储体IP
			String stMapPath = ams.getMappath();					// 映射存储体路径
			String stdirname = amsd.getStoragedirname();			// 存储体目录
			String filepath = amspr.getFilepath();					// filepath
			String filename = amspr.getFilename();					// filename(含后缀)
			
			if(stIP == null || stIP.equalsIgnoreCase(""))
			{
				System.out.println("stIP，返回null。");
				return null;
			}
			else
			{
				// 先不判断"/"或"\\"的格式
				// 最后判断多余的"/"或"\\"，然后在前面补"/"或"\\"
				// 格式："smb://hc:hc@172.23.19.66/公用/test.xml"
				if(!ams.getStorageaccstype().equalsIgnoreCase("smb"))
				{
					if(ams.getLoginname() == null || ams.getLoginname().equalsIgnoreCase(""))
					{
						strHead = "smb://";
					}
					else
					{
						strHead = "smb://";
						strHead += ams.getLoginname();
						strHead += ":";
						if(ams.getLoginpwd() != null)
						{
							strHead += ams.getLoginpwd();
						}
						strHead += "@";
					}
				}
				else
				{
					if(ams.getLoginname() == null || ams.getLoginname().equalsIgnoreCase(""))
					{
						strHead = "smb://";
					}
					else
					{
						strHead = "smb://";
						strHead += ams.getLoginname();
						strHead += ":";
						if(ams.getLoginpwd() != null)
						{
							strHead += ams.getLoginpwd();
						}
						strHead += "@";
					}
				}
				strPath = stIP;
				
				try 
				{
					if(stMapPath != null && !stMapPath.equalsIgnoreCase(""))
					{
						//System.out.println("stMapPath - " + stMapPath);
						stMapPath = stMapPath.replaceAll("\\\\", "/");
						//System.out.println("stMapPath - " + stMapPath);
						if(strPath.charAt(strPath.length() - 1) != '/')
						{
							strPath += "/";
						}
						strPath += stMapPath;
						//System.out.println("strPath - " + strPath);
					}
					if(stdirname != null && !stdirname.equalsIgnoreCase(""))
					{
						//System.out.println("stdirname - " + stdirname);
						stdirname = stdirname.replaceAll("\\\\", "/");
						//System.out.println("stdirname - " + stdirname);
						if(strPath.charAt(strPath.length() - 1) != '/')
						{
							strPath += "/";
						}
						strPath += stdirname;
						//System.out.println("strPath - " + strPath);
					}
					if(filepath != null && !filepath.equalsIgnoreCase(""))
					{
						//System.out.println("filepath - " + filepath);
						filepath = filepath.replaceAll("\\\\", "/");
						//System.out.println("filepath - " + filepath);
						if(strPath.charAt(strPath.length() - 1) != '/')
						{
							strPath += "/";
						}
						strPath += filepath;
						//System.out.println("strPath - " + strPath);
					}
					if(filename != null && !filename.equalsIgnoreCase(""))
					{
						//System.out.println("filename - " + filename);
						if(strPath.charAt(strPath.length() - 1) != '/')
						{
							strPath += "/";
						}
						strPath += filename;
						//System.out.println("strPath - " + strPath);
					}
					else
					{
						//System.out.println("filename为空，返回null。");
						return null;
					}
					
					// 去掉多余的"/"或"\\"
					strPath = strPath.replaceAll("//", "/");
					//System.out.println("strPath - " + strPath);
					
					// 生成完整路径
					strPath = strHead + strPath;
					//System.out.println("strPath - " + strPath);
				} 
				catch (RuntimeException e) 
				{
//					e.printStackTrace();
					System.out.println("getSourcePathByProgfileidStclasscode，异常：" + e.getMessage());
				}
			}
			retlist.add(strPath);
			retlist.add(list);
		}
		else
		{
			System.out.println("返回无记录，返回null。");
			retlist = null;
		}
		return retlist;
	}

	// 这就是传说中的方法11
	// 20100118 10:51
	// 根据文件ID 和 存储体等级，查询，得到文件存放(源)路径（不返回ProgramFiles），
	// 当progfileid传入内容是模板的ID时，在programfiles中无记录
	// 当节目包ID为null或者""时，忽略节目包ID的过滤条件
	public List getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
			String progfileid, 						// 文件code
			String stclasscode,						// 存储体等级code
			String productid						// 节目包ID
	) {
		cmsLogger.debug("progfileid:" + progfileid);
		cmsLogger.debug("stclasscode:" + stclasscode);
		cmsLogger.debug("productid:" + productid);
		// 返回：List
		// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
		// 2 - List<Object[]>
		// 		(AmsStorage)Object[0]
		// 		(AmsStoragePrgRel)Object[1]
		// 		(AmsStorageDir)Object[2]
		// 		(AmsStorageClass)Object[3]
		
		List retlist = new ArrayList();
		String strHead = "";
		String strPath = ""; // 格式："smb://hc:hc@172.23.19.66/公用/test.xml"

		List list = null;
		if (productid != null && !productid.equalsIgnoreCase("")) {
			Map map = new HashMap(0);
			map.put("progfileid", progfileid);
			map.put("stclasscode", stclasscode);
			map.put("productid", productid);
			list = baseDAO
					.queryByNamedQuery(
							"select_SourcePathByProgfileidStclasscodeProductidWithoutProgramFiles",
							map);
			if (cmsLogger.isDebugEnabled()) {
				cmsLogger.debug("select_SourcePathByProgfileidStclasscodeProductidWithoutProgramFiles  Results");
				for (Object object : list) {
					cmsLogger.debug("\t" + object);
				}
				cmsLogger.debug("select_SourcePathByProgfileidStclasscodeProductidWithoutProgramFiles Results End...");
			}
		} else {
			Map map = new HashMap(0);
			map.put("progfileid", progfileid);
			map.put("stclasscode", stclasscode);
			list = baseDAO
					.queryByNamedQuery(
							"select_SourcePathByProgfileidStclasscodeWithoutProgramFiles",
							map);
			
			if (cmsLogger.isDebugEnabled()) {
				cmsLogger.debug("select_SourcePathByProgfileidStclasscodeWithoutProgramFiles  Results");
				for (Object object : list) {
					cmsLogger.debug("\t" + object);
				}
				cmsLogger.debug("select_SourcePathByProgfileidStclasscodeWithoutProgramFiles Results End...");
			}
		}
		// 计算目标路径
		if (list != null && list.size() > 0) {
			Object[] rows = (Object[]) list.get(0);
			AmsStorage ams = (AmsStorage) rows[0];
			AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
			AmsStorageDir amsd = (AmsStorageDir) rows[2];
			AmsStorageClass amsc = (AmsStorageClass) rows[3];

			String stIP = ams.getStorageip(); // 存储体IP
			String stMapPath = ams.getMappath(); // 映射存储体路径
			String stdirname = amsd.getStoragedirname(); // 存储体目录
			String filepath = amspr.getFilepath(); // filepath
			String filename = amspr.getFilename(); // filename(含后缀)

			if (stIP == null || stIP.equalsIgnoreCase("")) {
				cmsLogger.warn("存储体IP为空。");
				return null;
			} else {
				// 先不判断"/"或"\\"的格式
				// 最后判断多余的"/"或"\\"，然后在前面补"/"或"\\"
				// 格式："smb://hc:hc@172.23.19.66/公用/test.xml"
				if (!ams.getStorageaccstype().equalsIgnoreCase("smb")) {
					if (ams.getLoginname() == null
							|| ams.getLoginname().equalsIgnoreCase("")) {
						strHead = "smb://";
					} else {
						strHead = "smb://";
						strHead += ams.getLoginname();
						strHead += ":";
						if (ams.getLoginpwd() != null) {
							strHead += ams.getLoginpwd();
						}
						strHead += "@";
					}
				} else {
					if (ams.getLoginname() == null
							|| ams.getLoginname().equalsIgnoreCase("")) {
						strHead = "smb://";
					} else {
						strHead = "smb://";
						strHead += ams.getLoginname();
						strHead += ":";
						if (ams.getLoginpwd() != null) {
							strHead += ams.getLoginpwd();
						}
						strHead += "@";
					}
				}
				strPath = stIP;

				try {
					if (stMapPath != null && !stMapPath.equalsIgnoreCase("")) {
						stMapPath = stMapPath.replaceAll("\\\\", "/");
						if (strPath.charAt(strPath.length() - 1) != '/') {
							strPath += "/";
						}
						strPath += stMapPath;
					}
					if (stdirname != null && !stdirname.equalsIgnoreCase("")) {
						stdirname = stdirname.replaceAll("\\\\", "/");
						if (strPath.charAt(strPath.length() - 1) != '/') {
							strPath += "/";
						}
						strPath += stdirname;
					}
					if (filepath != null && !filepath.equalsIgnoreCase("")) {
						filepath = filepath.replaceAll("\\\\", "/");
						if (strPath.charAt(strPath.length() - 1) != '/') {
							strPath += "/";
						}
						strPath += filepath;
					}
					if (filename != null && !filename.equalsIgnoreCase("")) {
						if (strPath.charAt(strPath.length() - 1) != '/') {
							strPath += "/";
						}
						strPath += filename;
					} else {
						cmsLogger.warn("filename为空，返回null。");
						return null;
					}

					// 去掉多余的"/"或"\\"
					strPath = strPath.replaceAll("//", "/");

					// 生成完整路径
					strPath = strHead + strPath;
				} catch (RuntimeException e) {
					cmsLogger.warn(e.getMessage());
				}
			}
			cmsLogger.debug(strPath);
			retlist.add(strPath);
			retlist.add(list);
		} else {
			cmsLogger.warn("getSourcePathByProgfileidStclasscodeWithoutProgramFiles，未查询到记录。");
			retlist = null;
		}
		return retlist;
	}
	
	/**
	 * 20110107 17:16 1.23 
	 */
	public List getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
			String progfileid, 						// 文件code
			String stclasscode,						// 存储体等级code
			String productid,						// 节目包ID
			String filetype
	) {
		// 返回：List
		// 1 - String 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
		// 2 - List<Object[]>
		// 		(AmsStorage)Object[0]
		// 		(AmsStoragePrgRel)Object[1]
		// 		(AmsStorageDir)Object[2]
		// 		(AmsStorageClass)Object[3]
		
		List retlist = new ArrayList();
		String strHead = "";
		String strPath = ""; // 格式："smb://hc:hc@172.23.19.66/公用/test.xml"

		List list = null;
		if (productid != null && !productid.equalsIgnoreCase("")) {
			Map map = new HashMap(0);
			map.put("progfileid", progfileid);
			map.put("stclasscode", stclasscode);
			map.put("productid", productid);
			map.put("ftypeglobalid", filetype);
			list = baseDAO
					.queryByNamedQuery(
							"select_SourcePathByProgfileidStclasscodeProductidFiletypeWithoutProgramFiles",
							map);
		} else {
			Map map = new HashMap(0);
			map.put("progfileid", progfileid);
			map.put("stclasscode", stclasscode);
			map.put("ftypeglobalid", filetype);
			list = baseDAO
					.queryByNamedQuery(
							"select_SourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles",
							map);
		}
		// 计算目标路径
		if (list != null && list.size() > 0) {
			Object[] rows = (Object[]) list.get(0);
			AmsStorage ams = (AmsStorage) rows[0];
			AmsStoragePrgRel amspr = (AmsStoragePrgRel) rows[1];
			AmsStorageDir amsd = (AmsStorageDir) rows[2];
			AmsStorageClass amsc = (AmsStorageClass) rows[3];

			String stIP = ams.getStorageip(); // 存储体IP
			String stMapPath = ams.getMappath(); // 映射存储体路径
			String stdirname = amsd.getStoragedirname(); // 存储体目录
			String filepath = amspr.getFilepath(); // filepath
			String filename = amspr.getFilename(); // filename(含后缀)

			if (stIP == null || stIP.equalsIgnoreCase("")) {
				System.out.println("存储体IP为空。");
				return null;
			} else {
				// 先不判断"/"或"\\"的格式
				// 最后判断多余的"/"或"\\"，然后在前面补"/"或"\\"
				// 格式："smb://hc:hc@172.23.19.66/公用/test.xml"
				if (!ams.getStorageaccstype().equalsIgnoreCase("smb")) {
					if (ams.getLoginname() == null
							|| ams.getLoginname().equalsIgnoreCase("")) {
						strHead = "smb://";
					} else {
						strHead = "smb://";
						strHead += ams.getLoginname();
						strHead += ":";
						if (ams.getLoginpwd() != null) {
							strHead += ams.getLoginpwd();
						}
						strHead += "@";
					}
				} else {
					if (ams.getLoginname() == null
							|| ams.getLoginname().equalsIgnoreCase("")) {
						strHead = "smb://";
					} else {
						strHead = "smb://";
						strHead += ams.getLoginname();
						strHead += ":";
						if (ams.getLoginpwd() != null) {
							strHead += ams.getLoginpwd();
						}
						strHead += "@";
					}
				}
				strPath = stIP;

				try {
					if (stMapPath != null && !stMapPath.equalsIgnoreCase("")) {
						stMapPath = stMapPath.replaceAll("\\\\", "/");
						if (strPath.charAt(strPath.length() - 1) != '/') {
							strPath += "/";
						}
						strPath += stMapPath;
					}
					if (stdirname != null && !stdirname.equalsIgnoreCase("")) {
						stdirname = stdirname.replaceAll("\\\\", "/");
						if (strPath.charAt(strPath.length() - 1) != '/') {
							strPath += "/";
						}
						strPath += stdirname;
					}
					if (filepath != null && !filepath.equalsIgnoreCase("")) {
						filepath = filepath.replaceAll("\\\\", "/");
						if (strPath.charAt(strPath.length() - 1) != '/') {
							strPath += "/";
						}
						strPath += filepath;
					}
					if (filename != null && !filename.equalsIgnoreCase("")) {
						if (strPath.charAt(strPath.length() - 1) != '/') {
							strPath += "/";
						}
						strPath += filename;
					} else {
						System.out.println("filename为空，返回null。");
						return null;
					}

					// 去掉多余的"/"或"\\"
					strPath = strPath.replaceAll("//", "/");

					// 生成完整路径
					strPath = strHead + strPath;
				} catch (RuntimeException e) {
					System.out.println(e.getMessage());
				}
			}
			retlist.add(strPath);
			retlist.add(list);
		} else {
			System.out
					.println("getSourcePathByProgfileidStclasscodeWithoutProgramFiles，未查询到记录。");
			retlist = null;
		}
		return retlist;
	}
	
	// 这就是传说中的方法4
	// 20100115 16:01
	// 根据节目包id、filecode，查询packagefiles、programfiles表，得到file
	public List getProgramFilesesByProductidFilecode(
			String productid, 						// 节目包ID
			String filecode 						// filecode
			)
	{
		// 返回： List<ProgramFiles>
		
		Map map = new HashMap(0);
		map.put("productid", productid);
		map.put("filecode", filecode);
		List list = baseDAO.queryByNamedQuery("select_ProgramFilesesByProductidFilecode",map);
		return list;
	}
	
	// 这就是传说中的方法5
	// 20100115 16:04
	// 根据节目包id 和 主文件表示(progrank)，查询packagefiles、programfiles表，得到节目包的文件
	public List getProgramFilesesByProductidProgrank(
			String productid, 						// 节目包ID
			Long progrank 						// 主文件标识，0 - 不是;  1 - 是
			)
	{
		// 返回： List<ProgramFiles>
		
		Map map = new HashMap(0);
		map.put("productid", productid);
		map.put("progrank", progrank);
		List list = baseDAO.queryByNamedQuery("select_ProgramFilesesByProductidProgrank",map);
		return list;
	}

	// 20100521 13:16
	// 根据节目包id和filetype
	public List getProgramFilesesByProductidFiletype(
			String productid,
			String filetypeid
			)
	{
		Map map = new HashMap(0);
		map.put("productid", productid);
		map.put("filetypeid", filetypeid);
		List list = baseDAO.queryByNamedQuery("getProgramFilesesByProductidFiletype",map);
		return list;
	}

	// 20100608 15:28
	// 根据节目包ID，查询节目包的文件列表
	public List getProgramfilesByProductid(
			String productid				// 节目包ID
			)
	{
		Map map = new HashMap(0);
		map.put("productid", productid);
		List list = baseDAO.queryByNamedQuery("getProgramfilesByProductid",map);
		return list;
	}

	/**
	 * 根据节目包ID 查询节目包文件关系记录
	 * @param progPackageID
	 * @return List<PackageFiles>
	 */
	public List<PackageFiles> queryPackageFilesByProgPackageID(String progPackageID) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageID", progPackageID);
		return this.baseDAO.queryByNamedQuery(
				"query.PackageFiles.by.ProgPackage.id", map);
	}
	
	/**
	 * 根据节目包ID统计节目包所有文件大小
	 * @param progPackageId 节目包编号
	 * @return 节目包所有文件大小总和
	 */
	public Long sumOfPackageFileSize(String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageId", progPackageId);
		List<BigDecimal> list = this.baseDAO.queryByNamedQuery(
				"query.ProgPackage.size.progPackageId", map);
		if (0 < list.size()) {
			return list.get(0).longValue();
		}
		return 0L;
	}
}
