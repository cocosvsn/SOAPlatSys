package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.PortalPackage;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IPortalPackageManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class PortalPackageManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class PortalPackageManagerImpl implements IPortalPackageManager {
	
	/** The PortalPackagedao. */
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

		PortalPackage portalPackage=(PortalPackage)baseDAO.getById(PortalPackage.class, pkid);
		if(portalPackage!=null){
			baseDAO.delete(portalPackage);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List portalPackagelist=baseDAO.findAll("PortalPackage","portalPackageid");
		return portalPackagelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List portalPackagelist=baseDAO.findByProperty("PortalPackage", propertyName, value);
		return portalPackagelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		PortalPackage portalPackage=(PortalPackage)baseDAO.getById(PortalPackage.class, pkid);
		return portalPackage;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		PortalPackage portalPackage=(PortalPackage)baseDAO.loadById(PortalPackage.class, pkid);
		return portalPackage;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) 
	{
		PortalPackage portalPackage = new PortalPackage();
		portalPackage = (PortalPackage)object;
		
		String strCurMaxPK = baseDAO.getMaxPropertyValue("PortalPackage", "ptpid");
		String strMaxPK = getcmspk.GetTablePK("PortalPackage", strCurMaxPK);
		portalPackage.setPtpid(strMaxPK);
		
		baseDAO.save(portalPackage);
		
		return portalPackage;
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

	
	
	// 20100414 18:10
	// 查询图文记录（TPORTALPACKAGE），根据名称、栏目ID、上下线标识
	public List getPortalPackagesByNameColumnclassidOnlinetag(
			String ptpname,
			String columnclassid,
			Long onlinetag
			)
	{
		List list = null;
		Map map = new HashMap();
		
		if(ptpname == null || ptpname.equalsIgnoreCase(""))
		{
			map.put("columnclassid", columnclassid);
			map.put("onlinetag", onlinetag);
			list = baseDAO.queryByNamedQuery("select_PortalPackagesByColumnclassidOnlinetag", map);
		}
		else
		{
			ptpname = "%" + ptpname + "%";
			
			map.put("ptpname", ptpname);
			map.put("columnclassid", columnclassid);
			map.put("onlinetag", onlinetag);
			list = baseDAO.queryByNamedQuery("select_PortalPackagesByNameColumnclassidOnlinetag", map);
		}
		return list;
	}
	
	// 20100415 11:57
	// 查询图文记录（PtpPgpRel），根据ID、节目包ID
	public List getPtpPgpRelsByPtpidProductid(
			String ptpid,
			String productid
			)
	{
		Map map = new HashMap();
		map.put("ptpid", ptpid);
		map.put("productid", productid);
		List list = baseDAO.queryByNamedQuery("select_PtpPgpRelsByPtpidProductid", map);
		return list;
	}
	
	// 20100416 11:29
	// 查询页面包装，根据节目包ID、栏目ID
	public List getPortalPackagesByProductidColumnclassid(
			String productid,
			String columnclassid
			)
	{
		// 返回：List<Object[]>
		//		(PortalPackage)Object[0]
		//		(PtpPgpRel)Object[1]
		Map map = new HashMap();
		map.put("productid", productid);
		map.put("columnclassid", columnclassid);
		List list = baseDAO.queryByNamedQuery("select_PortalPackagesByProductidColumnclassid", map);
		return list;
	}
	
	// 20100416 11:29
	// 查询节目包，根据页面包装ID、栏目ID
	public List getProgPackagesByPtpidColumnclassid(
			String ptpid,
			String columnclassid
			)
	{
		// 返回：List<Object[]>
		// 	(ProgPackage)Object[0]
		//	(PtpPgpRel)Object[1]
		Map map = new HashMap();
		map.put("ptpid", ptpid);
		map.put("columnclassid", columnclassid);
		List list = baseDAO.queryByNamedQuery("select_ProgPackagesByPtpidColumnclassid", map);
		return list;
	}
	
	// 20100419 12:58
	// 查询页面包装下的节目包，根据页面包装、栏目id
//	public List getProgPackagesByPortalPackage(
//			String ptpid,
//			String productname
//			)
//	{
//		Map map = new HashMap();
//		map.put("ptpid", ptpid);
//		map.put("productname", productname);
//		List list = baseDAO.queryByNamedQuery("select_ProgPackagesByPtpidProductname", map);
//		return list;
//	}
	
}
