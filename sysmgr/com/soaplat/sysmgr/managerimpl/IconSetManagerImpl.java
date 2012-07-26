package com.soaplat.sysmgr.managerimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.soaplat.sysmgr.bean.FunResourceTypeList;
import com.soaplat.sysmgr.bean.IconSet;
import com.soaplat.sysmgr.bean.IconSetNameRelList;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.manageriface.IIconSetManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class IconSetManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class IconSetManagerImpl implements IIconSetManager {
	
	/** The baseDAO. */
	
	IBaseDAO baseDAO;
	
	/** The getsyspk. */
	IGetPK getsyspk;
	
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

		IconSet iconset=(IconSet)baseDAO.getById(IconSet.class, pkid);
		if(iconset!=null){
			baseDAO.delete(iconset);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List iconsetlist=baseDAO.findAll("IconSet","iconsetid");
		return iconsetlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List iconsetlist=baseDAO.findByProperty("IconSet", propertyName, value);
		return iconsetlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		IconSet iconset=(IconSet)baseDAO.getById(IconSet.class, pkid);
		return iconset;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		IconSet iconset=(IconSet)baseDAO.loadById(IconSet.class, pkid);
		return iconset;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		IconSet iconset=new IconSet();
		iconset=(IconSet)object;
		iconset.setInputtime(new Date());
		String strMaxPK=getsyspk.GetTablePK("IconSet");
		iconset.setIconsetid(strMaxPK);
		baseDAO.save(iconset);
		//
		return baseDAO.getById(IconSet.class, strMaxPK);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);
		

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

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findbyExample(java.lang.Object)
	 */
	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}

	/**
	 * Sets the baseDAO.
	 * 
	 * @param baseDAO the new baseDAO
	 */
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IIconSetManager#getIconSetName()
	 */
	public List getIconSetName() {
//		String querystring="select distinct(i.iconsetname) from IconSet i order by iconsetname";
//		List iconsetnamelist=baseDAO.executeHQL(querystring);
//		return iconsetnamelist;
		List list=baseDAO.queryByNamedQuery("select_distincticonsetname");
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IIconSetManager#getIconSetNameList()
	 */
	public List getIconSetNameList() {
		List iconsetnamelist=this.getIconSetName();
		List iconsetnamerellists=new ArrayList();
		Map map=new HashMap();
		String iconsetname="";
		//如果存在不同的图标集名称，逐个构造IconSetNameRelList对象
		if (iconsetnamelist.size()>0){
			for(int i=0;i<iconsetnamelist.size();i++)
			{	
				iconsetname=iconsetnamelist.get(i).toString();
				//复合对象
				IconSetNameRelList iconsetnamerellist=new IconSetNameRelList();
				//
				iconsetnamerellist.setIconsetname(iconsetname);

				
				map.put("id", iconsetname);
				List iconsetlist=baseDAO.queryByNamedQuery("select_iconsetlistbyname", map);
//				
				//List iconsetlist=baseDAO.executeHQL("from IconSet i where i.iconsetname='"+iconsetname+"'");
				iconsetnamerellist.setIconsetnamelist(iconsetlist);
				iconsetnamerellists.add(iconsetnamerellist);
				
			}
		}
		//如果不存在图标集名称，则返回所有的图标集记录
		else
		{
			IconSetNameRelList iconsetnamerellist=new IconSetNameRelList();
			List iconsetlist=baseDAO.findAll("IconSet","iconsetid");
			iconsetnamerellist.setIconsetname("");
			iconsetnamerellist.setIconsetnamelist(iconsetlist);
			iconsetnamerellists.add(iconsetnamerellist);
			
		}
		return iconsetnamerellists;
	}

}
