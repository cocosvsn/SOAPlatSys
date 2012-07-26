package com.srmmgr.managerimpl;


import java.util.Date;
import java.util.List;

import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;
import com.srmmgr.bean.SrmStoragePrgRel;
import com.srmmgr.manageriface.ISrmStoragePrgRelManager;



// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class SrmStoragePrgRelManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class SrmStoragePrgRelManagerImpl implements ISrmStoragePrgRelManager {
	
	/** The base dao. */
	IBaseDAO baseDAO;
	
	/** The getsrmpk. */
	IGetPK getsrmpk;
	
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

		SrmStoragePrgRel srmstorageprgrel=(SrmStoragePrgRel)baseDAO.getById(SrmStoragePrgRel.class, pkid);
		if(srmstorageprgrel!=null){
			baseDAO.delete(srmstorageprgrel);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List srmstorageprgrellist=baseDAO.findAll("SrmStoragePrgRel","srmstorageprgrelid");
		return srmstorageprgrellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List srmstorageprgrellist=baseDAO.findByProperty("SrmStoragePrgRel", propertyName, value);
		return srmstorageprgrellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		SrmStoragePrgRel srmstorageprgrel=(SrmStoragePrgRel)baseDAO.getById(SrmStoragePrgRel.class, pkid);
		return srmstorageprgrel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		SrmStoragePrgRel srmstorageprgrel=(SrmStoragePrgRel)baseDAO.loadById(SrmStoragePrgRel.class, pkid);
		return srmstorageprgrel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		SrmStoragePrgRel srmstorageprgrel=new SrmStoragePrgRel();
		srmstorageprgrel=(SrmStoragePrgRel)object;
		srmstorageprgrel.setInputtime(new Date());
		String strMaxPK=getsrmpk.GetTablePK("SrmStoragePrgRel");
		srmstorageprgrel.setRelid(strMaxPK);
		baseDAO.save(srmstorageprgrel);
		//
		return baseDAO.getById(SrmStoragePrgRel.class, strMaxPK);

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
	 * Sets the base dao.
	 * 
	 * @param baseDAO the new base dao
	 */
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * Sets the getsrmpk.
	 * 
	 * @param getsrmpk the new getsrmpk
	 */
	public void setGetsrmpk(IGetPK getsrmpk) {
		this.getsrmpk = getsrmpk;
	}

}
