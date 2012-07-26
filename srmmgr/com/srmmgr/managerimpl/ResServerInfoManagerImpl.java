package com.srmmgr.managerimpl;


import java.util.Date;
import java.util.List;

import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;
import com.srmmgr.bean.ResServerInfo;
import com.srmmgr.manageriface.IResServerInfoManager;



// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ResServerInfoManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ResServerInfoManagerImpl implements IResServerInfoManager {
	
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

		ResServerInfo resserverinfo=(ResServerInfo)baseDAO.getById(ResServerInfo.class, pkid);
		if(resserverinfo!=null){
			baseDAO.delete(resserverinfo);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List resserverinfolist=baseDAO.findAll("ResServerInfo","resserverid");
		return resserverinfolist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List resserverinfolist=baseDAO.findByProperty("ResServerInfo", propertyName, value);
		return resserverinfolist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ResServerInfo resserverinfo=(ResServerInfo)baseDAO.getById(ResServerInfo.class, pkid);
		return resserverinfo;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ResServerInfo resserverinfo=(ResServerInfo)baseDAO.loadById(ResServerInfo.class, pkid);
		return resserverinfo;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		ResServerInfo resserverinfo=new ResServerInfo();
		resserverinfo=(ResServerInfo)object;
		resserverinfo.setInputtime(new Date());
		String strMaxPK=baseDAO.getMaxPropertyValue("ResServerInfo", "resserverid");
		strMaxPK=getsrmpk.GetTablePK("ResServerInfo",strMaxPK);
		resserverinfo.setResserverid(strMaxPK);
		baseDAO.save(resserverinfo);
		//
		return baseDAO.getById(ResServerInfo.class, strMaxPK);

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
