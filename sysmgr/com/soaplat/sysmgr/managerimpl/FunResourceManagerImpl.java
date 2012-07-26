package com.soaplat.sysmgr.managerimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.soaplat.sysmgr.bean.DictType;
import com.soaplat.sysmgr.bean.FunResource;
import com.soaplat.sysmgr.bean.FunResourceTypeList;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;

import com.soaplat.sysmgr.manageriface.IFunResourceManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class FunResourceManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class FunResourceManagerImpl implements IFunResourceManager {
	
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
			FunResource funresource=(FunResource)baseDAO.getById(FunResource.class, pkid);
			if(funresource!=null){
				baseDAO.delete(funresource);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List funresourcelist=baseDAO.findAll("FunResource","funcid");
		return funresourcelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List funresourcelist=baseDAO.findByProperty("FunResource", propertyName, value);
		return funresourcelist;

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		FunResource funresource=(FunResource)baseDAO.getById(FunResource.class, pkid);
		return funresource;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		FunResource funresource=(FunResource)baseDAO.loadById(FunResource.class, pkid);
		return funresource;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		String strMaxPK="";
		FunResource funresource=new FunResource();
		funresource=(FunResource)object;
		
		funresource.setInputtime(new Date());
		strMaxPK=baseDAO.getMaxPropertyValue("FunResource", "funcid");
		strMaxPK=getsyspk.GetTablePK("FunResource",strMaxPK);
		funresource.setFuncid(strMaxPK);
		baseDAO.save(funresource);
		
		return baseDAO.getById(FunResource.class, strMaxPK);
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

	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}

	public List getFunResourceTypeList() {
		
		List functypelist=this.getFuncType();
		List funresourcelist=new ArrayList();
		Map map=new HashMap();
		String functype="";
		//如果存在功能类别，逐个构造FunResourceTypeList对象
		if (functypelist.size()>0){
			for(int i=0;i<functypelist.size();i++)
			{	
				functype=functypelist.get(i).toString();
				FunResourceTypeList funresourcetypelist=new FunResourceTypeList();
				//
				map.put("id", functype);
				List funrlist=baseDAO.queryByNamedQuery("select_funrlistbyfunctype", map);
				
				//List funrlist=baseDAO.executeHQL("from FunResource f where f.functype='"+functype+"'");
				funresourcetypelist.setFunctype(functype);
				funresourcetypelist.setFunresourcelist(funrlist);
				//System.out.println(functype+"====="+funrset.size());
				funresourcelist.add(funresourcetypelist);
				
			}
		}
		//如果不存在功能类型，则返回所有的功能资源
		else
		{
			FunResourceTypeList funresourcetypelist=new FunResourceTypeList();
			funresourcetypelist.setFunctype("");
			List funrlist=baseDAO.findAll("FunResource", "funcid");
			funresourcetypelist.setFunresourcelist(funrlist);
			funresourcelist.set(0, funresourcetypelist);
		}
		return funresourcelist;
	}

	public List getFuncType() {
		//String querystring="select distinct(f.functype) from FunResource f";
		//List functypelist=baseDAO.executeHQL(querystring);
		//return functypelist;
		//
		List list=baseDAO.queryByNamedQuery("select_distinctfunctype");
		return list;
	}

}
