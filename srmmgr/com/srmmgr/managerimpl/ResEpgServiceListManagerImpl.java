package com.srmmgr.managerimpl;


import java.util.Date;
import java.util.List;

import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;
import com.srmmgr.bean.ResEpgServiceList;
import com.srmmgr.manageriface.IResEpgServiceListManager;



// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ResEpgServiceListManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ResEpgServiceListManagerImpl implements IResEpgServiceListManager {
	
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

		ResEpgServiceList resepgservicelist=(ResEpgServiceList)baseDAO.getById(ResEpgServiceList.class, pkid);
		if(resepgservicelist!=null){
			baseDAO.delete(resepgservicelist);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List resepgservicelistlist=baseDAO.findAll("ResEpgServiceList","relid");
		return resepgservicelistlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List resepgservicelistlist=baseDAO.findByProperty("ResEpgServiceList", propertyName, value);
		return resepgservicelistlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ResEpgServiceList resepgservicelist=(ResEpgServiceList)baseDAO.getById(ResEpgServiceList.class, pkid);
		return resepgservicelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ResEpgServiceList resepgservicelist=(ResEpgServiceList)baseDAO.loadById(ResEpgServiceList.class, pkid);
		return resepgservicelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		ResEpgServiceList resepgservicelist=new ResEpgServiceList();
		resepgservicelist=(ResEpgServiceList)object;
		resepgservicelist.setInputtime(new Date());
		String strMaxPK=getsrmpk.GetTablePK("ResEpgServiceList");
		resepgservicelist.setRelid(strMaxPK);
		baseDAO.save(resepgservicelist);
		//
		return baseDAO.getById(ResEpgServiceList.class, strMaxPK);

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
