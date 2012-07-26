package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.SrvColumn;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.ISrvColumnManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class SrvColumnManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class SrvColumnManagerImpl implements ISrvColumnManager {
	
	/** The srvColumndao. */
	IBaseDAO baseDAO;
	
	/** The getcmspk. */
	IGetPK getcmspk;
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		//锟斤拷删锟斤拷
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

		SrvColumn srvColumn=(SrvColumn)baseDAO.getById(SrvColumn.class, pkid);
		if(srvColumn!=null){
			baseDAO.delete(srvColumn);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List srvColumnlist=baseDAO.findAll("SrvColumn","srvprogid");
		return srvColumnlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List srvColumnlist=baseDAO.findByProperty("SrvColumn", propertyName, value);
		return srvColumnlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		SrvColumn srvColumn=(SrvColumn)baseDAO.getById(SrvColumn.class, pkid);
		return srvColumn;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		SrvColumn srvColumn=(SrvColumn)baseDAO.loadById(SrvColumn.class, pkid);
		return srvColumn;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		SrvColumn srvColumn = new SrvColumn();
		srvColumn = (SrvColumn)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("SrvColumn", "srvprogid");
		String strMaxPK = getcmspk.GetTablePK("SrvColumn", strCurMaxPK);
		srvColumn.setSrvprogid(strMaxPK);
		baseDAO.save(srvColumn);
		return baseDAO.getById(SrvColumn.class, strMaxPK);
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

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}

	public List getPortalColumnsBySrvid(String srvid)
	{
		Map map = new HashMap();
		map.put("srvid", srvid);
		List list = baseDAO.queryByNamedQuery("select_PortalColumnsBySrvid", map);
		return list;
	}
	
	// 根据栏目id，得到栏目与服务的配置关系 SrvColumn
	public List getCmsServicesByColumnclassid(String columnclassid)
	{
		Map map = new HashMap();
		map.put("columnclassid", columnclassid);
		List list = baseDAO.queryByNamedQuery("select_CmsServicesByColumnclassid", map);
		return list;
	}
}
