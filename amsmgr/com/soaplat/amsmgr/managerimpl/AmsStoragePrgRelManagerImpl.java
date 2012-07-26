/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.amsmgr.managerimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jcifs.smb.SmbFile;
import org.apache.log4j.Logger;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-11 下午07:07:56
 */
public class AmsStoragePrgRelManagerImpl implements IAmsStoragePrgRelManager {
	private IBaseDAO baseDAO;
	private IGetPK getamspk;
	public static final Logger cmsLog = Logger.getLogger("Cms");
	
	/**
	 * 删除AmsStoragePrgRel对象
	 * @param object AmsStoragePrgRel对象数组
	 */
	public void delete(Object[] object) {
		if(object.length > 0) {
			for(int i = 0; i < object.length; i ++) {
				this.baseDAO.delete(object[i]);
			}
		}	
	}

	/**
	 * 根据AmsStoragePrgRel对象主键ID删除对象
	 * @param pkid AmsStoragePrgRel主键ID
	 */
	public void deleteById(String pkid) {
		this.baseDAO.delete(this.baseDAO.getById(AmsStoragePrgRel.class, pkid));
	}

	/**
	 * 查询所有AmsStoragePrgRel对象
	 * @return 返回AmsStoragePrgRel对象集合
	 */
	public List<?> findAll() {
		return this.baseDAO.findAll("AmsStoragePrgRel","relid");
	}

	/**
	 * 根据对象的某个指定属性, 和属性值, 查询符合条件的对象集合
	 * @param propertyName AmsStoragePrgRel对象属性名
	 * @param value AmsStoragePrgRel对象属性值
	 * @return 返回符合条件的AmsStoragePrgRel对象集合
	 */
	public List<?> findByProperty(String propertyName, Object value) {
		return this.baseDAO.findByProperty("AmsStoragePrgRel", propertyName, value);
	}

	/**
	 * 根据AmsStoragePrgRel主键ID查询AmsStoragePrgRel对象
	 * @param pkid AmsStoragePrgRel主键ID
	 * @return AmsStoragePrgRel对象
	 */
	public Object getById(String pkid) {
		AmsStoragePrgRel storageprgrel = (AmsStoragePrgRel) this.baseDAO.getById(AmsStoragePrgRel.class, pkid);
		return storageprgrel;
	}

	/**
	 * 根据AmsStoragePrgRel主键ID查询AmsStoragePrgRel对象, 支持延时加载
	 * @param pkid AmsStoragePrgRel主键ID
	 * @return 返回AmsStoragePrgRel对象
	 */
	public Object loadById(String pkid) {
		AmsStoragePrgRel storageprgrel = (AmsStoragePrgRel) this.baseDAO.loadById(AmsStoragePrgRel.class, pkid);
		return storageprgrel;
	}

	/**
	 * 保存AmsStoragePrgRel对象
	 * @param object AmsStoragePrgRel对象
	 * @return 返回持久化后的AmsStoragePrgRel对象
	 */
	public Object save(Object object) 
	{
		AmsStoragePrgRel storageprgrel = null;
		try {
			storageprgrel = (AmsStoragePrgRel) object;
			
			List<?> exists = getAmsStoragePrgRels(
					storageprgrel.getStglobalid(),
					storageprgrel.getStdirglobalid(),
					storageprgrel.getPrglobalid(),
					storageprgrel.getFtypeglobalid(),
					storageprgrel.getFilename(),
					storageprgrel.getProgfileid(),
					storageprgrel.getFilepath()
					);
			if(exists == null || exists.size() <= 0) {
				storageprgrel.setInputtime(new Date());
				String strMaxPK = getamspk.GetTablePK("AmsStoragePrgRel");
				AmsStoragePrgRel amsstpr = (AmsStoragePrgRel) getById(strMaxPK);
				while (amsstpr != null) {
					cmsLog.warn("AmsStoragePrgRelManagerImpl-->save，文件位置表ID已经存在，睡眠1000毫秒后，继续生成。ID：" + strMaxPK);
					Thread.sleep(1000);
					strMaxPK = getamspk.GetTablePK("AmsStoragePrgRel");
					amsstpr = (AmsStoragePrgRel) getById(strMaxPK);
				}
				cmsLog.info("AmsStoragePrgRelManagerImpl-->save，得到文件位置表ID：" + strMaxPK);
				storageprgrel.setRelid(strMaxPK);
				baseDAO.save(storageprgrel);
			} else {
				storageprgrel = (AmsStoragePrgRel)exists.get(0);
			}
		} catch (Exception e) {
			cmsLog.error("AmsStoragePrgRelManagerImpl-->save，异常：" + e.getMessage());
		}
		return storageprgrel;
	}

	/**
	 * 修改AmsStoragePrgRel对象属性, 单个对象
	 * @param object 修改后的AmsStoragePrgRel对象
	 */
	public void update(Object object) {
		this.baseDAO.update(object);
	}

	/**
	 * 保存AmsStoragePrgRel对象
	 * @param object 待持久化的AmsStoragePrgRel对象数组
	 */
	public void save(Object[] object) {
		if (object.length > 0) {
			for(int i = 0; i < object.length; i ++) {
				this.save(object[i]);
			}
		}
	}

	/**
	 * 修改AmsStoragePrgRel对象属性, 多个对象
	 * @param object 修改后的AmsStoragePrgRel对象数组
	 */
	public void update(Object[] object) {
		if (object.length > 0) {
			for(int i = 0; i < object.length; i ++) {
				this.update(object[i]);
			}
		}
	}

	/**
	 * 把已经设置某些属性的对象作为模板，查询跟这个对象已有属性值相同的列表
	 * @param exampleentity 模板对象
	 * @return 属性相同的对象集合
	 */
	public List<?> findbyExample(Object exampleentity) {
		return this.baseDAO.findbyExample(exampleentity);
	}
	
	/**
	 * 判断文件位置表记录是否已经存在
	 * @param stglobalid
	 * @param stdirglobalid
	 * @param prglobalid
	 * @param ftypeglobalid
	 * @param filename
	 * @param progfileid
	 * @param filepath
	 * @return
	 */
	public List<?> getAmsStoragePrgRels(
				String stglobalid,
				String stdirglobalid,
				String prglobalid,
				String ftypeglobalid,
				String filename,
				String progfileid,
				String filepath
	) {
		List<AmsStoragePrgRel> ret = new ArrayList<AmsStoragePrgRel>();
		
		try {
			if(stglobalid == null)
				stglobalid = "";
			if(stdirglobalid == null)
				stdirglobalid = "";
			if(prglobalid == null)
				prglobalid = "";
			if(ftypeglobalid == null)
				ftypeglobalid = "";
			if(filename == null)
				filename = "";
			if(progfileid == null)
				progfileid = "";
			if(filepath == null)
				filepath = "";
			
			// 根据stglobalid stdirglobalid prglobalid ftypeglobalid filename progfileid
			// 查询记录
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put("stglobalid", stglobalid);
			map.put("stdirglobalid", stdirglobalid);
			map.put("prglobalid", prglobalid);
			map.put("ftypeglobalid", ftypeglobalid);
			map.put("filename", filename);
			map.put("progfileid", progfileid);
			map.put("filepath", filepath);
			
			List<?> list = this.baseDAO.queryByNamedQuery("getAmsStoragePrgRels", map);
			
			// 判断查询的记录的filepath
			if(list != null && list.size() > 0) {
				for(int i = 0; i < list.size(); i++) {
					AmsStoragePrgRel aspr = (AmsStoragePrgRel)list.get(i);
					
					String qfilepath = aspr.getFilepath();
					
					qfilepath = qfilepath.replace('\\', '/');
					filepath = filepath.replace('\\', '/');
					if(qfilepath.equalsIgnoreCase(filepath)) {
						ret.add(aspr);
					}
				}
			}
			
		} catch(Exception ex) {
			ret = null;
		}
		
		return ret;
	}
	
	/**
	 * 根据存储等级查询所有文件及文件详细信息
	 * @param storageId 存储体等级ID
	 * @return List<Object[3]>
	 * object[0]: AmsStoragePrgRel
	 * object[1]: ProgramFiles
	 * object[2]: AmsStorageDir
	 */
	public List<?> queryAllFileDetail(String storageId, 
			Date startInputTime, Date endInputTime) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("storageId", storageId);
		map.put("startInputTime", startInputTime);
		map.put("endInputTime", endInputTime);
		return this.baseDAO.queryByNamedQuery("query.ProgramFiles.by.storageId", map);
	}
	
	/**
	 * 根据存储等级查询所有文件及文件详细信息
	 * @param storageId 存储体等级ID
	 * @return List<Object[3]>
	 * object[0]: AmsStoragePrgRel
	 * object[1]: ProgramFiles
	 * object[2]: AmsStorageDir
	 */
	public List<?> queryAllFileDetail(String storageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("storageId", storageId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgramFiles.by.storageId2", map);
	}
	
	/**
	 * 根据存储体和位置表中文件名, 查询该存储体中文件位置记录
	 * @param storageId 存储体ID
	 * @param fileName 文件名
	 * @return 返回该存储体中文件位置记录
	 */
	public List<?> queryOtherStorageFileByStorageIdAndFileName(
			String storageId, String fileName) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("storageId", storageId);
		map.put("fileName", fileName);
		return this.baseDAO.queryByNamedQuery(
				"query.AmsStoragePrgRel.by.storageId.fileName", map);
	}
	
	/**
	 * 根据节目ID查询节目文件所在的路径
	 * @param programInfoID
	 * @return List<Object[4]>
	 * object[0]: AmsStorage
	 * object[1]: AmsStorageDir
	 * object[2]: AmsStoragePrgRel
	 * object[3]: ProgramFiles
	 */
	public List<?> queryFilePathByProgramInfoID(String programInfoID) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("programInfoID", programInfoID);
		return this.baseDAO.queryByNamedQuery(
				"query.AmsStorage.AmsStorageDir.AmsStoragePrgRel.ProgramFiles.by.programInfoID", 
				map);
	}
	
	/**
	 * 根据节目包ID和存储等级Code查询节目包存储位置信息
	 * @param progPackageId 节目包ID
	 * @param storageId 存储体ID
	 * @return List<Object[3]>
	 * object[0]: AmsStorage
	 * object[1]: AmsStorageDir
	 * object[2]: AmsStoragePrgRel
	 */
	public List<?> queryProgPackageStorageInfo(String progPackageId, 
			String storageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageId", progPackageId);
		map.put("storageId", storageId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgPackage.Storage.info.by.progPackageId.storageId", 
				map);
	}
	
	/**
	 * 根据文件ID, 文件Code和存储等级Code查询指定存储等级上的节目包主文件位置信息
	 * @param programFileId 文件ID
	 * @param fileCode 文件Code
	 * @param classCode 存储等级Code
	 * @return Object[]
	 * object[0]: AmsStorage
	 * object[1]: AmsStorageDir
	 * object[2]: AmsStoragePrgRel
	 */
	@SuppressWarnings("unchecked")
	public Object[] queryByProgPackageAndClassCode(String programFileId, 
			String fileCode, String classCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("programFileId", programFileId);
		map.put("fileCode", fileCode);
		map.put("classCode", classCode);
		List<Object[]> list = this.baseDAO.queryByNamedQuery(
				"query.positionInfo.by.programFileId.fileCode.classCode", map);
		if (0 < list.size()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据文件路径和文件名, 查询位置表信息, 用于生成预告JS时判断位置表记录是否已生成
	 * @param filePath 文件路径
	 * @param fileName 文件名
	 * @return 返回文件的位置信息
	 */
	@SuppressWarnings("unchecked")
	public AmsStoragePrgRel queryByFilePathAndFileName(String filePath, String fileName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("filePath", filePath);
		map.put("fileName", fileName);
		List<AmsStoragePrgRel> list = this.baseDAO.queryByNamedQuery(
				"query.AmsStoragePrgRel.by.filePath.fileName", map);
		if (0 < list.size()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据节目包ID, 存储等级Code, 文件ID查询文件路径和文件位置表ID
	 * 并将文件和位置表记录删除, 同时修改节目包状态为缓存库
	 * @param progPackageId 节目包ID
	 * @param classCode 存储等级Code
	 * @param programFileIds 文件ID集合
	 * @return 删除是否成功
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteOnlineFile(String progPackageId, String classCode,
			List<String> programFileIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("progPackageId", progPackageId);
		map.put("classCode", classCode);
		map.put("programFileIds", programFileIds);
		List<Object[]> list = (List<Object[]>) this.baseDAO.queryNamed(
				"query.AmsStoragePrgRel.by.progPackageId.classCode.fileCode", map);
		cmsLog.debug("节目包维护, 查询得出一级库附件文件记录数: " + list.size());
		for (Object[] objects : list) {
			String mapPath = null == objects[16] ? "" 
					: objects[16].toString().replaceAll("\\\\", "/");
			mapPath = !"".equals(mapPath) && mapPath.startsWith("/") 
					? mapPath.substring(1, mapPath.length()) : mapPath;
			mapPath = !"".equals(mapPath) && mapPath.endsWith("/") 
					? mapPath : mapPath + "/";
			
			String storageClassDir = null == objects[10] ? "" 
					: objects[10].toString().replaceAll("\\\\", "/");
			storageClassDir = !"".equals(storageClassDir) && storageClassDir.startsWith("/") 
					? storageClassDir.substring(1, storageClassDir.length()) 
							: storageClassDir;
			storageClassDir = !"".equals(storageClassDir) && storageClassDir.endsWith("/") 
					? storageClassDir : storageClassDir + "/";
			
			String storageIp = null == objects[4] ? "" : objects[4].toString();
			storageIp = !"".equals(storageIp) && storageIp.endsWith("/") 
					? storageIp : storageIp + "/";
			
			String relFilePath = null == objects[15] ? "" : objects[15].toString();
			relFilePath = !"".equals(relFilePath) && relFilePath.startsWith("/")
					? relFilePath.substring(1, relFilePath.length()) : relFilePath;
			relFilePath = !"".equals(relFilePath) && relFilePath.endsWith("/")
					? relFilePath : relFilePath + "/";
			
			StringBuffer storageClassPathBuffer = new StringBuffer(
					null == objects[7] ? "smb" : objects[7].toString());
			storageClassPathBuffer.append("://");
			storageClassPathBuffer.append(
					null == objects[5] ? "Administrator" : objects[5].toString());
			storageClassPathBuffer.append(":");
			storageClassPathBuffer.append(null == objects[6] ? "" 
					: objects[6].toString());
			storageClassPathBuffer.append("@");
			storageClassPathBuffer.append(storageIp);
			storageClassPathBuffer.append(mapPath);
			storageClassPathBuffer.append(storageClassDir);
			storageClassPathBuffer.append(relFilePath);
			storageClassPathBuffer.append(objects[13]);
			
			try {
				cmsLog.debug("节目包维护, 查询得出一级库小文件路径: " + storageClassPathBuffer.toString());
				SmbFile smbFile = new SmbFile(storageClassPathBuffer.toString());
				smbFile.delete();
				cmsLog.debug("节目包维护, 删除一级库附件实体文件成功!");
				this.baseDAO.deleteById(AmsStoragePrgRel.class, (String)objects[11]);
				cmsLog.debug("节目包维护, 删除一级库附件位置表文件记录成功!");
				ProgPackage progPackage = (ProgPackage) this.baseDAO.getById(
						ProgPackage.class, progPackageId);
				progPackage.setState(1L);
				progPackage.setDealstate(0L);
				this.baseDAO.update(progPackage);
				cmsLog.debug("节目包维护, 重置节目包附件状态为缓存库成功!");
			} catch (Exception e) {
				cmsLog.error(e);
				return false;
			}
		}
		cmsLog.debug("节目包维护, 删除一级库小文件成功, 返回删除结果!");
		return true;
	}
	
	/**
	 * 根据节目包ID, 存储体ID查询位置表记录
	 * @param progPackageId 节目包ID
	 * @param storageId 存储体ID
	 * @return 返回位置表集合
	 */
	@SuppressWarnings("unchecked")
	public List<AmsStoragePrgRel> queryAmsStoragePrgRels(String progPackageId, String storageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageId", progPackageId);
		map.put("storageId", storageId);
		return this.baseDAO.queryByNamedQuery(
				"query.AmsStoragePrgRel.by.progPackageId.storageId", map);
	}
	
	/**
	 * andy
	 * 1.2	20101105 9:52	根据文件id、存储等级id，查询文件位置表记录
	 * @param progfileid
	 * @param stclasscode
	 * @return
	 */
	public List<AmsStoragePrgRel> getAmsStoragePrgRelsByProgfileidStclasscode(
			String progfileid, String stglobalid) {
		Map map = new HashMap();
		map.put("progfileid", progfileid);
		map.put("stglobalid", stglobalid);
		List<AmsStoragePrgRel> list = baseDAO.queryByNamedQuery("getAmsStoragePrgRelsByProgfileidStclasscode", map);
		return list;
	}
	/** ---------------- setter and getter ----------------- **/
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetamspk(IGetPK getamspk) {
		this.getamspk = getamspk;
	}
}
