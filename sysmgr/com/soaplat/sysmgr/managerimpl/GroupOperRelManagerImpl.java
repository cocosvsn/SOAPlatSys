package com.soaplat.sysmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.soaplat.sysmgr.bean.GroupOperRel;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.manageriface.IGroupOperRelManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class GroupOperRelManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class GroupOperRelManagerImpl implements IGroupOperRelManager {

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
			GroupOperRel groupoperrel=(GroupOperRel)baseDAO.getById(GroupOperRel.class, pkid);
			if(groupoperrel!=null){
				baseDAO.delete(groupoperrel);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List groupoperrellist=baseDAO.findAll("GroupOperRel","relid");
		return groupoperrellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {

		List groupoperrellist=baseDAO.findByProperty("GroupOperRel", propertyName, value);
		return groupoperrellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		GroupOperRel groupoperrel=(GroupOperRel)baseDAO.getById(GroupOperRel.class, pkid);
		return groupoperrel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		GroupOperRel groupoperrel=(GroupOperRel)baseDAO.loadById(GroupOperRel.class, pkid);
		return groupoperrel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		String strMaxPK="";
		GroupOperRel groupoperrel=new GroupOperRel();
		groupoperrel=(GroupOperRel)object;
		groupoperrel.setInputtime(new Date());

		
		strMaxPK=getsyspk.GetTablePK("GroupOperRel");
		
		groupoperrel.setRelid(strMaxPK);
		baseDAO.save(groupoperrel);
		
		return baseDAO.getById(GroupOperRel.class, strMaxPK);
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

	public List getGroupOperators(String groupid) {
		
		Map map=new HashMap();
		map.put("id", groupid);
		List operatorlist=baseDAO.queryByNamedQuery("select_groupoperator", map);
		return operatorlist;
	}

	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}

	public List getGroupRoleOperators(String groupid, String roleid) {
		Map map=new HashMap();
		map.put("groupid", groupid);
		map.put("roleid", roleid);
		List operatorlist=baseDAO.queryByNamedQuery("select_grouproleoperator", map);
		return operatorlist;
	}

	

	

}
