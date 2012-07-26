package com.soaplat.sysmgr.managerimpl;

import java.util.Date;
import java.util.List;

import com.soaplat.sysmgr.bean.Operator;
import com.soaplat.sysmgr.bean.Org;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.manageriface.IOrgManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class OrgManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class OrgManagerImpl implements IOrgManager {
	
	/** The BaseDAO. */
	IBaseDAO BaseDAO;
	
	/** The getsyspk. */
	IGetPK  getsyspk;

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		if(object.length>0){
			for(int i=0;i<object.length;i++){
				BaseDAO.delete(object[i]);
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
			Org org=(Org)BaseDAO.getById(Org.class, pkid);
			if(org!=null){
				BaseDAO.delete(org);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List orglist=BaseDAO.findAll("Org","orgid");
		return orglist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List orglist=BaseDAO.findByProperty("Org", propertyName, value);
		return orglist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		Org org=(Org)BaseDAO.getById(Org.class, pkid);
		return org;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		Org org=(Org)BaseDAO.loadById(Org.class, pkid);
		return org;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		String strMaxPK="";
		Org org=new Org();
		org=(Org)object;
		org.setInputtime(new Date());

		strMaxPK=BaseDAO.getMaxPropertyValue("Org", "orgid");
		strMaxPK=getsyspk.GetTablePK("Org", strMaxPK);
		
		org.setOrgid(strMaxPK);
		BaseDAO.save(org);
		return BaseDAO.getById(Org.class, strMaxPK);
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		BaseDAO.update(object);

	}

	/**
	 * Sets the BaseDAO.
	 * 
	 * @param BaseDAO the new BaseDAO
	 */
	public void setBaseDAO(IBaseDAO BaseDAO) {
		this.BaseDAO = BaseDAO;
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

	public List findbyExample(Object exampleentity) {
		List list=BaseDAO.findbyExample(exampleentity);
		return list;
	}

}
