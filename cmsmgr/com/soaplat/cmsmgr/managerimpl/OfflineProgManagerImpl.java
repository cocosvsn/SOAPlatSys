package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.List;

import com.soaplat.cmsmgr.bean.OfflineProg;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.cmsmgr.manageriface.IOfflineProgManager;
import com.soaplat.sysmgr.common.IBaseDAO;
// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class OfflineProgManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class OfflineProgManagerImpl implements IOfflineProgManager {
	
	/** The offlineprogdao. */
	IBaseDAO baseDAO;
	
	/** The getpk. */
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

		OfflineProg offlineprog=(OfflineProg)baseDAO.getById(OfflineProg.class, pkid);
		if(offlineprog!=null){
			baseDAO.delete(offlineprog);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List offlineproglist=baseDAO.findAll("OfflineProg","relid");
		return offlineproglist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List offlineproglist=baseDAO.findByProperty("OfflineProg", propertyName, value);
		return offlineproglist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		OfflineProg offlineprog=(OfflineProg)baseDAO.getById(OfflineProg.class, pkid);
		return offlineprog;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		OfflineProg offlineprog=(OfflineProg)baseDAO.loadById(OfflineProg.class, pkid);
		return offlineprog;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		OfflineProg offlineprog=new OfflineProg();
		offlineprog=(OfflineProg)object;
		String strMaxPK=getcmspk.GetTablePK("OfflineProg");
		offlineprog.setRelid(strMaxPK);
		baseDAO.save(offlineprog);
		//
		return baseDAO.getById(OfflineProg.class, strMaxPK);

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
	public void setOfflineProgdao(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
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

	public void setOfflineprogdao(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

}
