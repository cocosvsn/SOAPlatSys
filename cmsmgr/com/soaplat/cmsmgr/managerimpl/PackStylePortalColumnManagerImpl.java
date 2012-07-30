package com.soaplat.cmsmgr.managerimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.PackStylePortalColumn;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IPackStylePortalColumnManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class PackStylePortalColumnManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class PackStylePortalColumnManagerImpl implements IPackStylePortalColumnManager {
	
	/** The PackStylePortalColumndao. */
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

		PackStylePortalColumn packStylePortalColumn=(PackStylePortalColumn)baseDAO.getById(PackStylePortalColumn.class, pkid);
		if(packStylePortalColumn!=null){
			baseDAO.delete(packStylePortalColumn);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List packStylePortalColumnlist=baseDAO.findAll("PackStylePortalColumn","packStylePortalColumnid");
		return packStylePortalColumnlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List packStylePortalColumnlist=baseDAO.findByProperty("PackStylePortalColumn", propertyName, value);
		return packStylePortalColumnlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		PackStylePortalColumn packStylePortalColumn=(PackStylePortalColumn)baseDAO.getById(PackStylePortalColumn.class, pkid);
		return packStylePortalColumn;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		PackStylePortalColumn packStylePortalColumn=(PackStylePortalColumn)baseDAO.loadById(PackStylePortalColumn.class, pkid);
		return packStylePortalColumn;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) 
	{
		PackStylePortalColumn packStylePortalColumn = new PackStylePortalColumn();
		packStylePortalColumn = (PackStylePortalColumn)object;
		
		String strCurMaxPK = baseDAO.getMaxPropertyValue("PackStylePortalColumn", "relid");
		String strMaxPK = getcmspk.GetTablePK("PackStylePortalColumn", strCurMaxPK);
		packStylePortalColumn.setRelid(strMaxPK);
		baseDAO.save(packStylePortalColumn);

		return packStylePortalColumn;
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

	// 20100522 17:30
	// 查询关系，根据样式id和栏目code
	public List getPackStylePortalColumnsByStyleidDefcatcode(Long styleid,
			String defcatcode) {
		try {
			List list = null;

			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put("styleid", styleid);
			map.put("defcatcode", defcatcode);
			list = baseDAO.queryByNamedQuery(
					"getPackStylePortalColumnsByStyleidDefcatcode", map);

			return list;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	/**
	 * 根据栏目Code查询该栏目所能应用的样式ID列表
	 * @param defcatcode 栏目Code
	 * @return 返回样式ID集合
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getPackStyleIdByDefcatcode(String defcatcode) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("defcatcode", defcatcode);
		return this.baseDAO.queryByNamedQuery("query.PackStylePortalColumn.PackStyleID.by.defcatcode", map);
	}
	
	/**
	 * 根据栏目Code和栏目样式编号删除
	 * @param defcatcode
	 * @param styleId
	 */
	public void deleteByStyleIdDefcatcode(String defcatcode, Long styleId) {
		String sql = "DELETE FROM CMS.TCMSPACKSTYLEPORTALCOLUMN PC WHERE PC.DEFCATCODE = :defcatcode AND PC.STYLEID = :styleId";
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("defcatcode", defcatcode);
		map.put("styleId", styleId);
		this.baseDAO.executeSQL(sql, map);
	}
}
