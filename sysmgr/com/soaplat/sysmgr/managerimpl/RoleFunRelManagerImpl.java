package com.soaplat.sysmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.soaplat.sysmgr.bean.RoleFunRel;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.manageriface.IRoleFunRelManager;


// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class RoleFunRelManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class RoleFunRelManagerImpl implements IRoleFunRelManager {

	/** The baseDAO. */
	IBaseDAO baseDAO;
	
	/** The getsyspk. */
	IGetPK getsyspk;
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
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
		try{
			RoleFunRel rolefunrel=(RoleFunRel)baseDAO.getById(RoleFunRel.class, pkid);
			if(rolefunrel!=null){
				baseDAO.delete(rolefunrel);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List rolefunrellist=baseDAO.findAll("RoleFunRel","relid");
		return rolefunrellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List rolefunrellist=baseDAO.findByProperty("RoleFunRel", propertyName, value);
		return rolefunrellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		RoleFunRel rolefunrel=(RoleFunRel)baseDAO.getById(RoleFunRel.class, pkid);
		return rolefunrel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		RoleFunRel rolefunrel=(RoleFunRel)baseDAO.loadById(RoleFunRel.class, pkid);
		return rolefunrel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		String strMaxPK="";
		RoleFunRel rolefunrel=new RoleFunRel();
		rolefunrel=(RoleFunRel)object;
		rolefunrel.setInputtime(new Date());

		
		strMaxPK=getsyspk.GetTablePK("RoleFunRel");
		
		rolefunrel.setRelid(strMaxPK);
		baseDAO.save(rolefunrel);
		
		return baseDAO.getById(RoleFunRel.class, strMaxPK);
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
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * Sets the getsyspk.
	 * 
	 * @param getsyspk the new getsyspk
	 */
	public void setGetsyspk(IGetPK getsyspk) {
		this.getsyspk = getsyspk;
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

	public List getRoleFunResources(String roleid) {
		Map map=new HashMap();
		map.put("id", roleid);
		List funresourcelist=baseDAO.queryByNamedQuery("select_rolefunresource", map);
		return funresourcelist;
	}

	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}
}
