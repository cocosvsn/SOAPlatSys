package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.PackStyle;
import com.soaplat.cmsmgr.bean.PortalJsRules;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.cmsmgr.manageriface.IPortalJsRulesManager;
import com.soaplat.sysmgr.common.IBaseDAO;
// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class PortalJsRulesManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class PortalJsRulesManagerImpl implements IPortalJsRulesManager {
	
	/** The portalJsRulesdao. */
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

		PortalJsRules portalJsRules=(PortalJsRules)baseDAO.getById(PortalJsRules.class, pkid);
		if(portalJsRules!=null){
			baseDAO.delete(portalJsRules);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List portalJsRuleslist=baseDAO.findAll("PortalJsRules","stylerelid");
		return portalJsRuleslist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List portalJsRuleslist=baseDAO.findByProperty("PortalJsRules", propertyName, value);
		return portalJsRuleslist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		PortalJsRules portalJsRules=(PortalJsRules)baseDAO.getById(PortalJsRules.class, pkid);
		return portalJsRules;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		PortalJsRules portalJsRules=(PortalJsRules)baseDAO.loadById(PortalJsRules.class, pkid);
		return portalJsRules;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		PortalJsRules portalJsRules=new PortalJsRules();
		portalJsRules = (PortalJsRules)object;
		
		String strCurMaxPK = baseDAO.getMaxPropertyValue("PortalJsRules", "stylerelid");
		String strMaxPK = getcmspk.GetTablePK("PortalJsRules", strCurMaxPK);
		portalJsRules.setStylerelid(strMaxPK);
		baseDAO.save(portalJsRules);

		return portalJsRules;
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
	public void setPortalJsRulesdao(IBaseDAO baseDAO) {
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
	
	
	// 20100331 21:29
	// 根据栏目code，查询Js规则记录，按序号排序
	public List getJsRulesByDefcatcode(String defcatcode)
	{
		Map map = new HashMap();
		map.put("defcatcode", defcatcode);
		List list = baseDAO.queryByNamedQuery("select_JsRulesByDefcatcode", map);
		return list;
	}
	
	// 20100331 21:29
	// 根据栏目code，查询Js规则记录，按序号排序
	public List getJsRulesByDatatypeDefcatcode(
			Long datatype,
			String defcatcode
			)
	{
		Map map = new HashMap();
		map.put("datatype", datatype);
		map.put("defcatcode", defcatcode);
		List list = baseDAO.queryByNamedQuery("select_JsRulesByDatatypeDefcatcode", map);
		return list;
	}

}
