package com.soaplat.amsmgr.serviceimpl;

import java.net.MalformedURLException;
import java.util.List;
import org.apache.log4j.Logger;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.amsmgr.serviceiface.IAmsStorageDirService;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.sysmgr.common.ApplicationContextHolder;


/**
 * Title 		:the Class AmsStorageDirService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class AmsStorageDirService implements IAmsStorageDirService {
	private static Logger logger = Logger.getLogger(AmsStorageDirService.class);
	
	/** The storagedir manager. */
	public IAmsStorageDirManager storagedirManager=null;
	
	/**
	 * Instantiates a new ams storage dir service.
	 */
	public AmsStorageDirService() {
		storagedirManager=(IAmsStorageDirManager)ApplicationContextHolder.webApplicationContext.getBean("amsstoragedirManager");
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		storagedirManager.delete(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {
		storagedirManager.deleteById(pkid);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findAll()
	 */
	public List findAll() {
		List list=storagedirManager.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List list=storagedirManager.findByProperty(propertyName, value);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		Object object=storagedirManager.getById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		Object object=storagedirManager.loadById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object)
	 */
	public Object save(Object object) {
		Object saveobject=storagedirManager.save(object);
		return saveobject;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		storagedirManager.save(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object)
	 */
	public void update(Object object) {
		storagedirManager.update(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		storagedirManager.update(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteByIDRetAll(java.lang.String)
	 */
	public List deleteByIDRetAll(String pkid) {
		this.deleteById(pkid);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteRetAll(java.lang.Object[])
	 */
	public List deleteRetAll(Object[] object) {
		this.delete(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object)
	 */
	public List saveRetAll(Object object) {
		this.save(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object[])
	 */
	public List saveRetAll(Object[] object) {
		this.save(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object)
	 */
	public List updateRetAll(Object object) {
		this.update(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object[])
	 */
	public List updateRetAll(Object[] object) {
		this.update(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findbyExample(java.lang.Object)
	 */
	public List findbyExample(Object exampleentity) {
		List list=storagedirManager.findbyExample(exampleentity);
		return list;
	}
	
	public Object[] findstorageanddirlistbystorageclass(String stclasscode,String filecode) {
		Object[] maxdir=storagedirManager.findstorageanddirlistbystorageclass(stclasscode,filecode);
		return maxdir;
	}
	
	/**
	 * 节目导入判断文件是否存在
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CmsResultDto isExist(String path) {
		String storageClassCodePrepared = "Prepared";		// 上海节目录入存储体等级code
		String fileCode = "H264";	// 视频文件Code: H264; 富媒体文件Code: RMZIP
		String extraName = path.substring(path.lastIndexOf(".") + 1, path.length());
		
		if("zip".equalsIgnoreCase(extraName)) {
			fileCode = "RMZIP";
		}
		
		CmsResultDto cmsResultDto = new CmsResultDto();
		cmsResultDto.setResultCode(0l);
		boolean flag = false;
		long fileSize = 0;
		
		List<Object[]> list = this.storagedirManager
				.getStorageStoragedirsByStclasscodeFilecode(
						storageClassCodePrepared, fileCode);
		if (0 < list.size()) {
			Object[] objects = list.get(0);
			AmsStorage amsStorage = (AmsStorage) objects[0];
			AmsStorageDir amsStorageDir = (AmsStorageDir) objects[1];
			
			String mapPath = amsStorage.getMappath().replaceAll("\\\\", "/");
			mapPath = mapPath.startsWith("/") ? mapPath.substring(1, mapPath.length()) : mapPath;
			mapPath = mapPath.endsWith("/") ? mapPath : mapPath + "/";
			
			String storageClassDir = amsStorageDir.getStoragedirname().replaceAll("\\\\", "/");
			storageClassDir = storageClassDir.startsWith("/") 
					? storageClassDir.substring(1, storageClassDir.length()) : storageClassDir;
			storageClassDir = storageClassDir.endsWith("/") ? storageClassDir : storageClassDir + "/";
			
			StringBuffer storageClassPathBuffer = new StringBuffer(amsStorage.getStorageaccstype());
			storageClassPathBuffer.append("://");
			storageClassPathBuffer.append(amsStorage.getLoginname());
			storageClassPathBuffer.append(":");
			storageClassPathBuffer.append(null == amsStorage.getLoginpwd() ? "" : amsStorage.getLoginpwd());
			storageClassPathBuffer.append("@");
			storageClassPathBuffer.append(amsStorage.getStorageip().endsWith("/") 
					? amsStorage.getStorageip() : amsStorage.getStorageip() + "/");
			storageClassPathBuffer.append(mapPath);
			storageClassPathBuffer.append(storageClassDir);
			storageClassPathBuffer.append(path.replace("\\\\", "/"));
			
			String smbFilePath = storageClassPathBuffer.toString();
			logger.debug("获得节目录入的视频文件路径: " + smbFilePath);
			
			try {
				SmbFile smbFile = new SmbFile(smbFilePath);
				flag = smbFile.isDirectory() ? false : smbFile.exists();
				if (flag) {
					fileSize = smbFile.length();
				}
			} catch (MalformedURLException e) {
				logger.error("节目视频文件录入路径错误! " + e);
			} catch (SmbException e) {
				logger.error("节目视频文件访问失败!" + e);
			}
		}
		
		cmsResultDto.setResultObject(new Object[] {flag, fileSize});
		return cmsResultDto;
	}
	
	public void test() {
//		AmsStorageDirService amsStorageDirService = new AmsStorageDirService();
//		CmsResultDto cmsResultDto = amsStorageDirService.isExist("District.9.2009.Blu-ray.REMUX.H264.1080P.DTSHDMA.MySilu.disk1.ts");
//		Object[] objects = (Object[]) cmsResultDto.getResultObject();
//		System.out.println(objects[0]);
//		System.out.println(objects[1]);
	}
}
