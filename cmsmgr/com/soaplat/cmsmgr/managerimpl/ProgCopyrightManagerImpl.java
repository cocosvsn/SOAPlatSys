package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.List;

import com.soaplat.cmsmgr.bean.ProgCopyright;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IProgCopyrightManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ProgCopyrightManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgCopyrightManagerImpl implements IProgCopyrightManager {
	
	/** The progcopyrightdao. */
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

		ProgCopyright progcopyright=(ProgCopyright)baseDAO.getById(ProgCopyright.class, pkid);
		if(progcopyright!=null){
			baseDAO.delete(progcopyright);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List progcopyrightlist=baseDAO.findAll("ProgCopyright","relid");
		return progcopyrightlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List progcopyrightlist=baseDAO.findByProperty("ProgCopyright", propertyName, value);
		return progcopyrightlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgCopyright progcopyright=(ProgCopyright)baseDAO.getById(ProgCopyright.class, pkid);
		return progcopyright;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgCopyright progcopyright=(ProgCopyright)baseDAO.loadById(ProgCopyright.class, pkid);
		return progcopyright;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		ProgCopyright progcopyright=new ProgCopyright();
		progcopyright=(ProgCopyright)object;
		String strMaxPK=getcmspk.GetTablePK("ProgCopyright");
		progcopyright.setRelid(strMaxPK);
		baseDAO.save(progcopyright);
		//
		return baseDAO.getById(ProgCopyright.class, strMaxPK);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);
		

	}


	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
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

}
