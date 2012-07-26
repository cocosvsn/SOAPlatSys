package com.soaplat.amsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;


/**
 * Title 		:the Class AmsStorageManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class AmsStorageManagerImpl implements IAmsStorageManager {

	/** The base dao. */
	IBaseDAO baseDAO;
	
	/** The getamspk. */
	IGetPK getamspk;
	
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

		AmsStorage storage=(AmsStorage)baseDAO.getById(AmsStorage.class, pkid);
		if(storage!=null){
			baseDAO.delete(storage);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List storagelist=baseDAO.findAll("AmsStorage","stglobalid");
		return storagelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List storagelist=baseDAO.findByProperty("AmsStorage", propertyName, value);
		return storagelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		AmsStorage storage=(AmsStorage)baseDAO.getById(AmsStorage.class, pkid);
		return storage;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		AmsStorage storage=(AmsStorage)baseDAO.loadById(AmsStorage.class, pkid);
		return storage;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	// 这就是传说中的方法1
	public Object save(Object object) {
		AmsStorage storage=new AmsStorage();
		storage=(AmsStorage)object;
		storage.setInputtime(new Date());
		String strMaxPK=baseDAO.getMaxPropertyValue("AmsStorage", "stglobalid");
		strMaxPK=getamspk.GetTablePK("AmsStorage",strMaxPK);
		storage.setStglobalid(strMaxPK);
		baseDAO.save(storage);
		//
		return baseDAO.getById(AmsStorage.class, strMaxPK);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);
		

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

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findbyExample(java.lang.Object)
	 */
	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}
	
	/**
	 * 根据存储体等级ID查询存储体和存储体等级信息
	 * @param storageClassId 存储体等级ID
	 * @return 返回List<Object[2]> <br />
	 * object[0]: AmsStorage 
	 * object[1]: AmsStorageClass
	 */
	public List<?> queryByStorageClassId(String storageClassId) {
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("storageClassId", storageClassId);
		return this.baseDAO.queryByNamedQuery(
				"query.AmsStorage.AmsStorageClass.by.storageClassId", params);
	}
	
	/**
	 * 根据存储体等级Code查询该存储等级的存储体ID
	 * @param storageClassCode 存储体等级Code
	 * @return 返回指定等级的存储体ID
	 * @throws Exception 如果根据存储等级Code查询不到存储体信息, 则抛出异常
	 */
	public String queryStorageIdByStorageClassCode(String storageClassCode)
			throws Exception {
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("storageClassCode", storageClassCode);
		List<?> list = this.baseDAO.queryByNamedQuery(
				"query.StorageID.by.storageClassCode", params);
		if (0 < list.size()) {
			return (String) list.get(0);
		} else {
			throw new Exception("存储体或存储体等级不存在!");
		}
	}
	
	/**
	 * Sets the base dao.
	 * 
	 * @param baseDAO the new base dao
	 */
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * Sets the getamspk.
	 * 
	 * @param getamspk the new getamspk
	 */
	public void setGetamspk(IGetPK getamspk) {
		this.getamspk = getamspk;
	}
	
//	// Edited by Andy at 20100111 15:10
//	// ��ݴ洢��ȼ�����ѯ�洢��ȼ��?�洢��?�õ��洢��
//	public List getAmsStorageDirsByStclasscode(String stclasscode)
//	{
//		Map map = new HashMap();
//		map.put("stclasscode", stclasscode);
//		List list = baseDAO.queryByNamedQuery("select_AmsStorageDirsByStclasscode", map);
//		return list;
//	}

}
