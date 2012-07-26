package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.List;

import com.soaplat.cmsmgr.bean.OfflineStorage;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.cmsmgr.manageriface.IOfflineStorageManager;
import com.soaplat.sysmgr.common.IBaseDAO;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class OfflineStorageManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class OfflineStorageManagerImpl implements IOfflineStorageManager {
	
	/** The baseDAO. */
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

		OfflineStorage offlinestorage=(OfflineStorage)baseDAO.getById(OfflineStorage.class, pkid);
		if(offlinestorage!=null){
			baseDAO.delete(offlinestorage);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List offlinestoragelist=baseDAO.findAll("OfflineStorage","barcodeid");
		return offlinestoragelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List offlinestoragelist=baseDAO.findByProperty("OfflineStorage", propertyName, value);
		return offlinestoragelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		OfflineStorage offlinestorage=(OfflineStorage)baseDAO.getById(OfflineStorage.class, pkid);
		return offlinestorage;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		OfflineStorage offlinestorage=(OfflineStorage)baseDAO.loadById(OfflineStorage.class, pkid);
		return offlinestorage;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		String strMaxPK="";
		OfflineStorage offlinestorage=new OfflineStorage();
		offlinestorage=(OfflineStorage)object;
		strMaxPK=baseDAO.getMaxPropertyValue("OfflineStorage", "barcodeid");
		strMaxPK=getcmspk.GetTablePK("OfflineStorage", strMaxPK);
//		String strMaxPK=getcmspk.GetTablePK("OfflineStorage");
		offlinestorage.setBarcodeid(strMaxPK);
		baseDAO.save(offlinestorage);
		//
		return baseDAO.getById(OfflineStorage.class, strMaxPK);

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



	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}



}
