package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.PtpPgpRel;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IPtpPgpRelManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class PtpPgpRelManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class PtpPgpRelManagerImpl implements IPtpPgpRelManager {
	
	/** The PtpPgpReldao. */
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

		PtpPgpRel ptpPgpRel=(PtpPgpRel)baseDAO.getById(PtpPgpRel.class, pkid);
		if(ptpPgpRel!=null){
			baseDAO.delete(ptpPgpRel);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List ptpPgpRellist=baseDAO.findAll("PtpPgpRel","ptpPgpRelid");
		return ptpPgpRellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List ptpPgpRellist=baseDAO.findByProperty("PtpPgpRel", propertyName, value);
		return ptpPgpRellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		PtpPgpRel ptpPgpRel=(PtpPgpRel)baseDAO.getById(PtpPgpRel.class, pkid);
		return ptpPgpRel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		PtpPgpRel ptpPgpRel=(PtpPgpRel)baseDAO.loadById(PtpPgpRel.class, pkid);
		return ptpPgpRel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) 
	{
		PtpPgpRel ptpPgpRel = new PtpPgpRel();
		ptpPgpRel = (PtpPgpRel)object;
		
		String strCurMaxPK = baseDAO.getMaxPropertyValue("PtpPgpRel", "relid");
		String strMaxPK = getcmspk.GetTablePK("PtpPgpRel", strCurMaxPK);
		ptpPgpRel.setRelid(strMaxPK);
		
		baseDAO.save(ptpPgpRel);
		
		return ptpPgpRel;
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

	
	// 20100422 22:14
	// 查询页面包装和节目包关系记录，根据页面包装
	public List getPtpPgpRelByPortalPackage(
			String ptpid
			)
	{
		Map map = new HashMap();
		map.put("ptpid", ptpid);
		List list = baseDAO.queryByNamedQuery("select_PtpPgpRelsByPortalPackage", map);
		return list;
	}
	
	
	/**------------------ HuangBo addition by 2010年11月4日 14时14分 ---------------------**/
	
	/**
	 * 根据节目包ID查询该节目包页面包装信息
	 * @param progPackageId 节目包ID
	 * @return 返回该节目包的页面包装关系信息, 其中包含页面包装信息
	 * PtpPgpRel.sequence 页面包装总集数
	 * PtpPgpRel.inputmanid 栏目ID
	 * PtpPgpRel.remark 页面包装名称
	 */
	@SuppressWarnings("unchecked")
	public PtpPgpRel queryPortalInfoByProgPackageId(String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageId", progPackageId);
		List<PtpPgpRel> list = this.baseDAO.queryByNamedQuery(
				"query.PtpPgpRel.by.progPackageId", map);
		if (0 < list.size()) {
			return list.get(0);
		}
		return null;
	}
}
