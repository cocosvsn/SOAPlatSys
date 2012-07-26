package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.PPColumnRel;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IPPColumnRelManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class PPColumnRelManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class PPColumnRelManagerImpl implements IPPColumnRelManager {
	
	/** The pPColumnReldao. */
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

		PPColumnRel pPColumnRel=(PPColumnRel)baseDAO.getById(PPColumnRel.class, pkid);
		if(pPColumnRel!=null){
			baseDAO.delete(pPColumnRel);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List pPColumnRellist=baseDAO.findAll("PPColumnRel","relid");
		return pPColumnRellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List pPColumnRellist=baseDAO.findByProperty("PPColumnRel", propertyName, value);
		return pPColumnRellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		PPColumnRel pPColumnRel=(PPColumnRel)baseDAO.getById(PPColumnRel.class, pkid);
		return pPColumnRel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		PPColumnRel pPColumnRel=(PPColumnRel)baseDAO.loadById(PPColumnRel.class, pkid);
		return pPColumnRel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		PPColumnRel pPColumnRel = new PPColumnRel();
		pPColumnRel = (PPColumnRel)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("PPColumnRel", "relid");
		String strMaxPK = getcmspk.GetTablePK("PPColumnRel", strCurMaxPK);
		pPColumnRel.setRelid(strMaxPK);
		baseDAO.save(pPColumnRel);
		//
		return baseDAO.getById(PPColumnRel.class, strMaxPK);

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
	
	public List getPpColumnRelsByProductIdAndColumnclassid(String productId, String columnclassid)
	{
		Map map = new HashMap();
		map.put("productid", productId);
		map.put("columnclassid", columnclassid);
		List list = baseDAO.queryByNamedQuery("select_ppColumnRelsByProductIdAndColumnclassid", map);
		return list;
	}
	
	
	public List getProgPackagesByColumnclassid(String columnclassid)
	{
		Map map = new HashMap();
		map.put("columnclassid", columnclassid);
		List list = baseDAO.queryByNamedQuery("select_ProgPackagesByColumnclassid", map);
		return list;
	}

}
