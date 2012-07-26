package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.ProgPPRel;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IProgPPRelManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ProgPPRelManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgPPRelManagerImpl implements IProgPPRelManager {
	
	/** The progPPReldao. */
	IBaseDAO baseDAO;
	
	/** The getcmspk. */
	IGetPK getcmspk;
	
	String lastPK;
	
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

		ProgPPRel progPPRel=(ProgPPRel)baseDAO.getById(ProgPPRel.class, pkid);
		if(progPPRel!=null){
			baseDAO.delete(progPPRel);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List progPPRellist=baseDAO.findAll("ProgPPRel","relid");
		return progPPRellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List progPPRellist=baseDAO.findByProperty("ProgPPRel", propertyName, value);
		return progPPRellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgPPRel progPPRel=(ProgPPRel)baseDAO.getById(ProgPPRel.class, pkid);
		return progPPRel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgPPRel progPPRel=(ProgPPRel)baseDAO.loadById(ProgPPRel.class, pkid);
		return progPPRel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) 
	{
		ProgPPRel progPPRel = new ProgPPRel();
		progPPRel = (ProgPPRel)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("ProgPPRel", "relid");
		String strMaxPK = getcmspk.GetTablePK("ProgPPRel", strCurMaxPK);
		System.out.println("Last PK:" + lastPK);
		System.out.println("Get PK:" + strMaxPK);
//		if(lastPK == null || lastPK == "")
//		{
//		}
//		else
//		{
//			if(strMaxPK == lastPK)
//			{
//				System.out.println("duplicate PK.");
//				for(int i = 0; i < 1000; i++)
//				{
//					try
//					{
//						System.out.println("Sleep...");
//						Thread.sleep((long)100);
//					}
//					catch (InterruptedException e) 
//					{
//			        }
//					strMaxPK = getcmspk.GetTablePK("ProgPPRel");
//					if(strMaxPK != lastPK)
//					{
//						System.out.println("New PK found.");
//						break;
//					}
//				}
//			}
//		}
		lastPK = strMaxPK;
		System.out.println("New PK:" + strMaxPK);
		progPPRel.setRelid(strMaxPK);
		baseDAO.save(progPPRel);

		return baseDAO.getById(ProgPPRel.class, strMaxPK);
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
	public void save(Object[] object) 
	{
		lastPK = "";
		if (object.length > 0)
		{
			for(int i=0;i<object.length;i++)
			{
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
	
	public List getProgPPRelsByProductidAndProgramid(String productId, String programId)
	{
		Map map = new HashMap(0);
		map.put("productid", productId);
		map.put("programid", programId);
		List list = baseDAO.queryByNamedQuery("select_progPPRelsByProductidAndProgramid",map);
		return list;
	}

}
