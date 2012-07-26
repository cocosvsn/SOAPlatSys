package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.PortalRoleOperRel;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IPortalRoleOperRelManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class PortalRoleOperRelManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class PortalRoleOperRelManagerImpl implements IPortalRoleOperRelManager {
	
	/** The PortalRoleOperReldao. */
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

		PortalRoleOperRel portalRoleOperRel=(PortalRoleOperRel)baseDAO.getById(PortalRoleOperRel.class, pkid);
		if(portalRoleOperRel!=null){
			baseDAO.delete(portalRoleOperRel);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List portalRoleOperRellist=baseDAO.findAll("PortalRoleOperRel","portalRoleOperRelid");
		return portalRoleOperRellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List portalRoleOperRellist=baseDAO.findByProperty("PortalRoleOperRel", propertyName, value);
		return portalRoleOperRellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		PortalRoleOperRel portalRoleOperRel=(PortalRoleOperRel)baseDAO.getById(PortalRoleOperRel.class, pkid);
		return portalRoleOperRel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		PortalRoleOperRel portalRoleOperRel=(PortalRoleOperRel)baseDAO.loadById(PortalRoleOperRel.class, pkid);
		return portalRoleOperRel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		PortalRoleOperRel portalRoleOperRel = new PortalRoleOperRel();
		portalRoleOperRel = (PortalRoleOperRel)object;

		String strCurMaxPK = baseDAO.getMaxPropertyValue("PortalRoleOperRel", "relid");
		String strMaxPK = getcmspk.GetTablePK("PortalRoleOperRel", strCurMaxPK);
		portalRoleOperRel.setRelid(strMaxPK);
		baseDAO.save(portalRoleOperRel);

		return baseDAO.getById(PortalRoleOperRel.class, strMaxPK);
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

	public List<PortalRoleOperRel> getPortalRoleOperRelsByRoleidAndColumnclassid(String roleid, String columnclassid)
	{
		Map map = new HashMap();
		map.put("roleid", roleid);
		map.put("columnclassid", columnclassid);
		List<PortalRoleOperRel> list = baseDAO.queryByNamedQuery("select_PortalRoleOperRelsByRoleidAndColumnclassid", map);
		return list;
	}
	
	public List<PortalColumn> getPortalColumnsByRoleid(String roleid)
	{
		Map map = new HashMap();
		map.put("roleid", roleid);
		List<PortalColumn> list = baseDAO.queryByNamedQuery("select_PortalColumnsByRoleid", map);
		return list;
	}
}
