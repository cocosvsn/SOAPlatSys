package com.soaplat.cmsmgr.daoimpl;

import java.util.List;

import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.daoiface.IProgListMangDAO;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;

public class ProgListMangDAOImpl implements IProgListMangDAO {
	
	IBaseDAO baseDAO;
	IGetPK getcmspk;
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}
	

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {

		ProgListMang progListMang = (ProgListMang)baseDAO.getById(ProgListMang.class, pkid);
		if(progListMang!=null){
			baseDAO.delete(progListMang);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List programfileslist=baseDAO.findAll("ProgListMang","scheduledate");
		return programfileslist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List programfileslist=baseDAO.findByProperty("ProgListMang", propertyName, value);
		return programfileslist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public ProgListMang getById(String pkid) {
		ProgListMang progListMang=(ProgListMang)baseDAO.getById(ProgListMang.class, pkid);
		return progListMang;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public ProgListMang loadById(String pkid) {
		ProgListMang progListMang=(ProgListMang)baseDAO.loadById(ProgListMang.class, pkid);
		return progListMang;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public ProgListMang save(ProgListMang progListMang) {

		baseDAO.save(progListMang);
		return progListMang;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(ProgListMang progListMang) {
		baseDAO.update(progListMang);
	}

}
