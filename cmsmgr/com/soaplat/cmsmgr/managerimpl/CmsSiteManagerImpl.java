package com.soaplat.cmsmgr.managerimpl;

import java.util.List;

import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.ICmsSiteManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class CmsSiteManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class CmsSiteManagerImpl implements ICmsSiteManager {
	
	/** The CmsSitedao. */
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

		CmsSite cmsSite=(CmsSite)baseDAO.getById(CmsSite.class, pkid);
		if(cmsSite!=null){
			baseDAO.delete(cmsSite);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List cmsSitelist=baseDAO.findAll("CmsSite","siteid");
		return cmsSitelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List cmsSitelist=baseDAO.findByProperty("CmsSite", propertyName, value);
		return cmsSitelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		CmsSite cmsSite=(CmsSite)baseDAO.getById(CmsSite.class, pkid);
		return cmsSite;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		CmsSite cmsSite=(CmsSite)baseDAO.loadById(CmsSite.class, pkid);
		return cmsSite;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		CmsSite cmsSite=new CmsSite();
		cmsSite=(CmsSite)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("CmsSite", "siteid");
		String strMaxPK = getcmspk.GetTablePK("CmsSite", strCurMaxPK);
		cmsSite.setSiteid(strMaxPK);
		baseDAO.save(cmsSite);

		return baseDAO.getById(CmsSite.class, strMaxPK);

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
	
	/**
	 * 添加品牌
	 * @param cmsSites
	 */
	public synchronized void saveCmsSite(CmsSite... cmsSites) {
		String tempCurrMaxPk = this.baseDAO.getMaxPropertyValue("CmsSite", "id");
		Long currMaxPk = "".equals(tempCurrMaxPk) ? 0 : Long.valueOf(tempCurrMaxPk.substring(2));
		for (CmsSite cmsSite : cmsSites) {
			cmsSite.setSiteid("CS" + String.format("%08d", ++currMaxPk));
			this.baseDAO.save(cmsSite);
		}
	}
	
	/**
	 * 删除品牌
	 * @param ids
	 */
	public void deleteCmsSite(String... ids) {
		for (String id : ids) {
			this.baseDAO.deleteById(CmsSite.class, id);
		}
	}
	
	/**
	 * 删除品牌
	 * @param ids
	 */
	public void deleteCmsSite(CmsSite... cmsSites) {
		for (CmsSite cmsSite : cmsSites) {
			this.baseDAO.delete(cmsSite);
		}
	}
	
	/**
	 * 修改品牌
	 * @param cmsSites
	 */
	public void updateCmsSite(CmsSite... cmsSites) {
		for (CmsSite cmsSite : cmsSites) {
			this.baseDAO.update(cmsSite);
		}
	}
	
	/**
	 * 查询所有有效品牌信息
	 * @return 返回品牌信息集合 List<CmsSite>
	 */
	@SuppressWarnings("unchecked")
	public List<CmsSite> queryAllCmsSites() {
		return this.baseDAO.queryByNamedQuery("query.all.validate.CmsSite");
	}

	/**
	 * 根据品牌Code取得品牌名称
	 * @param siteCode 品牌Code
	 * @return 品牌名称
	 */
	@SuppressWarnings("unchecked")
	public String getSiteNameBySiteCode(String siteCode) {
		List<CmsSite> cmsSites = this.baseDAO.findByProperty("CmsSite", "siteCode", siteCode);
		if (0 < cmsSites.size()) {
			return cmsSites.get(0).getSitename();
		}
		return null;
	}
	
	/**
	 * 是否存在指定品牌属性(品牌Code, 节目预告文件名)
	 * @param cmsSite
	 * @return
	 */
	public boolean isExistsSite(CmsSite cmsSite) {
		if (this.baseDAO.isExist("CmsSite", "siteCode", cmsSite.getSiteCode())) {
			return true;
		}
//		if (this.baseDAO.isExist("CmsSite", "epgpath", cmsSite.getEpgpath())) {
//			return true;
//		}
		return false;
	} 
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}

}
