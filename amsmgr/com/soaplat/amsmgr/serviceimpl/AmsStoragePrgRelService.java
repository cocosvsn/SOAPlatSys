package com.soaplat.amsmgr.serviceimpl;

import java.net.MalformedURLException;
import java.util.List;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.amsmgr.serviceiface.IAmsStoragePrgRelService;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Title 		:the Class AmsStoragePrgRelService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class AmsStoragePrgRelService implements IAmsStoragePrgRelService {
	private static Logger logger = Logger.getLogger("Cms");

	/** The storageprgrel manager. */
	private IAmsStorageManager amsStorageManager;
	private IAmsStoragePrgRelManager storageprgrelManager=null;
	
	/**
	 * Instantiates a new ams storage prg rel service.
	 */
	public AmsStoragePrgRelService() {
		storageprgrelManager = (IAmsStoragePrgRelManager) ApplicationContextHolder.webApplicationContext
				.getBean("amsstorageprgrelManager");
		this.amsStorageManager = (IAmsStorageManager) ApplicationContextHolder.webApplicationContext
				.getBean("amsstorageManager");
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		storageprgrelManager.delete(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {
		storageprgrelManager.deleteById(pkid);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findAll()
	 */
	public List findAll() {
		List list=storageprgrelManager.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List list=storageprgrelManager.findByProperty(propertyName, value);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		Object object=storageprgrelManager.getById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		Object object=storageprgrelManager.loadById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object)
	 */
	public Object save(Object object) {
		Object saveobject=storageprgrelManager.save(object);
		return saveobject;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		storageprgrelManager.save(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object)
	 */
	public void update(Object object) {
		storageprgrelManager.update(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		storageprgrelManager.update(object);

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
		List list=storageprgrelManager.findbyExample(exampleentity);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public CmsResultDto getFilePath(String programInfoID) {
		logger.debug("取得文件路径 AmsStoragePrgRelService -> getFilePath(节目ID:" + programInfoID + ")");
		List<Object[]> list = (List<Object[]>) this.storageprgrelManager
				.queryFilePathByProgramInfoID(programInfoID);
		CmsResultDto cmsResultDto = new CmsResultDto();
		cmsResultDto.setResultCode(1l);
		cmsResultDto.setErrorMessage("节目文件不存在!");
		
		if (0 < list.size()) {
			Object[] objects = list.get(0);
			AmsStorage storage = (AmsStorage) objects[0];
			AmsStorageDir storageDir = (AmsStorageDir) objects[1];
			AmsStoragePrgRel storagePrgRel = (AmsStoragePrgRel) objects[2];
			
			
			String smbFilePath = getPath(storage, storageDir, storagePrgRel);
			logger.debug("获得节目录入的视频文件路径: " + smbFilePath);
			
			try {
				SmbFile smbFile = new SmbFile(smbFilePath);
				boolean flag = smbFile.isDirectory() ? false : smbFile.exists();
				
				String tmpPath = smbFilePath.substring(
						smbFilePath.lastIndexOf("@") + 1, smbFilePath.length());
				cmsResultDto.setResultCode(0l);
				cmsResultDto.setErrorMessage("");
				cmsResultDto.setResultObject(new Object[] {flag, "\\\\" 
						+ tmpPath.replaceAll("/", "\\\\")});
			} catch (MalformedURLException e) {
				logger.error("节目视频文件录入路径错误! " + e);
			} catch (SmbException e) {
				logger.error("节目视频文件访问失败!" + e);
			}
		} else {
			logger.debug("出错, 节目文件位置记录不存在!" + list.size());
		}
		
		return cmsResultDto;
	}
	
	/**
	 * 根据节目包ID, 存储等级Code获取文件是否存在信息, 如果文件存在还将获得文件路径信息. 格式: \\ip\文件路径\文件名
	 * @param progPackageId
	 * @param storageClassCode
	 * @return
	 * @throws Exception
	 */
	public CmsResultDto getFilePath(String progPackageId, String storageClassCode) {
		CmsResultDto cmsResultDto = new CmsResultDto();
		cmsResultDto.setResultCode(1l);
		cmsResultDto.setErrorMessage("节目文件不存在!");
		
		String storageId = null;
		try {
			storageId = this.amsStorageManager.queryStorageIdByStorageClassCode(
					storageClassCode);
		} catch (Exception e1) {
			logger.error("出错, 节目文件位置记录不存在!" + e1);
		}
		List<?> list = this.storageprgrelManager.queryProgPackageStorageInfo(
				progPackageId, storageId);
		
		if (0 < list.size()) {
			Object[] objects = (Object[]) list.get(0);
			AmsStorage storage = (AmsStorage) objects[0];
			AmsStorageDir storageDir = (AmsStorageDir) objects[1];
			AmsStoragePrgRel storagePrgRel = (AmsStoragePrgRel) objects[2];
			
			String smbFilePath = getPath(storage, storageDir, storagePrgRel);
			logger.debug("获得smb文件路径: " + smbFilePath);
			
			SmbFile smbFile;
			try {
				smbFile = new SmbFile(smbFilePath);
				boolean flag = smbFile.isDirectory() ? false : smbFile.exists();
				String tmpPath = smbFilePath.substring(
						smbFilePath.lastIndexOf("@") + 1, smbFilePath.length());
				cmsResultDto.setResultCode(0l);
				cmsResultDto.setErrorMessage("");
				cmsResultDto.setResultObject(new Object[] {flag, "\\\\" 
						+ tmpPath.replaceAll("/", "\\\\")});
			} catch (MalformedURLException e) {
				logger.error("节目视频文件录入路径错误! " + e);
			} catch (SmbException e) {
				logger.error("节目视频文件访问失败!" + e);
			}
			
		} else {
			logger.debug("出错, 节目文件位置记录不存在!" + list.size());
		}
		return cmsResultDto;
	}
	
	/**
	 * 根据存储体, 存储体目录, 文件位置表信息拼装出文件路径
	 * @param storage
	 * @param storageDir
	 * @param storagePrgRel
	 * @return
	 */
	public static String getPath(AmsStorage storage, AmsStorageDir storageDir, 
			AmsStoragePrgRel storagePrgRel) {
		
//		String mapPath = storage.getMappath().replaceAll("\\\\", "/");
//		mapPath = mapPath.startsWith("/") ? mapPath.substring(1, mapPath.length()) : mapPath;
//		mapPath = mapPath.endsWith("/") ? mapPath : mapPath + "/";
//		
//		String storageClassDir = storageDir.getStoragedirname().replaceAll("\\\\", "/");
//		storageClassDir = storageClassDir.startsWith("/") 
//				? storageClassDir.substring(1, storageClassDir.length()) : storageClassDir;
//		storageClassDir = storageClassDir.endsWith("/") ? storageClassDir : storageClassDir + "/";
//		
//		
//		StringBuffer storageClassPathBuffer = new StringBuffer(storage.getStorageaccstype());
//		storageClassPathBuffer.append("://");
//		storageClassPathBuffer.append(storage.getLoginname());
//		storageClassPathBuffer.append(":");
//		storageClassPathBuffer.append(null == storage.getLoginpwd() ? "" : storage.getLoginpwd());
//		storageClassPathBuffer.append("@");
//		storageClassPathBuffer.append(storage.getStorageip().endsWith("/") 
//				? storage.getStorageip() : storage.getStorageip() + "/");
//		storageClassPathBuffer.append(mapPath);
//		storageClassPathBuffer.append(storageClassDir);
		String smbFilePath = null;
		if (null != storagePrgRel) {
			smbFilePath = getPath(storage.getStorageaccstype(), storage.getLoginname(), 
					storage.getLoginpwd(), storage.getStorageip(), 
					storage.getMappath(), storageDir.getStoragedirname(), 
					storagePrgRel.getFilepath(), storagePrgRel.getFilename());
		} else {
			smbFilePath = getPath(storage.getStorageaccstype(), storage.getLoginname(), 
					storage.getLoginpwd(), storage.getStorageip(), 
					storage.getMappath(), storageDir.getStoragedirname(), null, null);
		}
		
		logger.debug("获得节目录入的视频文件路径: " + smbFilePath);
		return smbFilePath;
	}
	
	/**
	 * 拼装出文件路径
	 * @param storage
	 * @param storageDir
	 * @param storagePrgRel
	 * @return
	 */
	public static String getPath(String accessType, String loginName, String loginPwd, String ip,
			String mapPath, String storageClassDir, String filePath, String fileName) {
		if (null == accessType) {
			throw new NullPointerException("访问远程文件类型为空!");
		}
		
		if (null == loginName) {
			throw new NullPointerException("访问远程文件用户名为空!");
		}
		
		if (null == ip) {
			throw new NullPointerException("访问远程文件IP为空!");
		}
		
		if (null == mapPath) {
			throw new NullPointerException("访问远程文件映射路径为空!");
		}
		
		if (null == storageClassDir) {
			throw new NullPointerException("访问远程文件文件类型目录为空!");
		}
		
		mapPath = mapPath.replaceAll("\\\\", "/");
		mapPath = mapPath.startsWith("/") ? mapPath.substring(1, mapPath.length()) : mapPath;
		mapPath = mapPath.endsWith("/") ? mapPath : mapPath + "/";
		
		storageClassDir = storageClassDir.replaceAll("\\\\", "/");
		storageClassDir = storageClassDir.startsWith("/") 
				? storageClassDir.substring(1, storageClassDir.length()) : storageClassDir;
		storageClassDir = storageClassDir.endsWith("/") ? storageClassDir : storageClassDir + "/";
		
		
		StringBuffer storageClassPathBuffer = new StringBuffer(accessType);
		storageClassPathBuffer.append("://");
		storageClassPathBuffer.append(loginName);
		storageClassPathBuffer.append(":");
		storageClassPathBuffer.append(null == loginPwd ? "" : loginPwd);
		storageClassPathBuffer.append("@");
		storageClassPathBuffer.append(ip.endsWith("/") ? ip : ip + "/");
		storageClassPathBuffer.append(mapPath);
		storageClassPathBuffer.append(storageClassDir);
		if (null != filePath) {
			filePath = filePath.replace("\\\\", "/");
			filePath = filePath.startsWith("/") ? filePath.substring(1, filePath.length()) : filePath;
			filePath = filePath.endsWith("/") ? filePath : filePath + "/";
			storageClassPathBuffer.append(filePath);
			storageClassPathBuffer.append(fileName);
		}
		
		String smbFilePath = storageClassPathBuffer.toString();
		logger.debug("拼装得到的Samba文件路径: " + smbFilePath);
		return smbFilePath;
	}
	
	public void test() {
		String programInfoId = "PRVN20100909200032000390";
		String programFileId = "PRVN20100802154535000453001001";
		CmsResultDto cmsResultDto = this.getFilePath("PRVN20100909200032000390", "NearOnline");
		System.out.println(cmsResultDto.getResultObject());
	}
	
//	public static void main(String[] args) {PPVP20100726100854000625
//		ApplicationContext app = new ClassPathXmlApplicationContext(new String[] {
//				"applicationContext-amsmgrdaobeans.xml",
//				"applicationContext-cmsmgrdaobeans.xml",
//				"applicationContext-common.xml",
//				"applicationContext-contentmgrmanagerbeans.xml",
//				"applicationContext-amsmgrmanager.xml",
//				"applicationContext-cmsmgrmanager.xml"
//		});
//	}
}
