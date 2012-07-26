package com.srmmgr.managerimpl;


import java.util.Date;
import java.util.List;

import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;
import com.srmmgr.bean.VodServer;
import com.srmmgr.manageriface.IVodServerManager;



// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class VodServerManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class VodServerManagerImpl implements IVodServerManager {
	
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

		VodServer vodserver=(VodServer)baseDAO.getById(VodServer.class, pkid);
		if(vodserver!=null){
			baseDAO.delete(vodserver);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List vodserverlist=baseDAO.findAll("VodServer","vodsid");
		return vodserverlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List vodserverlist=baseDAO.findByProperty("VodServer", propertyName, value);
		return vodserverlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		VodServer vodserver=(VodServer)baseDAO.getById(VodServer.class, pkid);
		return vodserver;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		VodServer vodserver=(VodServer)baseDAO.loadById(VodServer.class, pkid);
		return vodserver;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		VodServer vodserver=new VodServer();
		vodserver=(VodServer)object;
		vodserver.setInputtime(new Date());
		String strMaxPK=baseDAO.getMaxPropertyValue("VodServer", "vodsid");
		strMaxPK=getsrmpk.GetTablePK("VodServer",strMaxPK);
		vodserver.setVodsid(strMaxPK);
		baseDAO.save(vodserver);
		//
		return baseDAO.getById(VodServer.class, strMaxPK);

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
