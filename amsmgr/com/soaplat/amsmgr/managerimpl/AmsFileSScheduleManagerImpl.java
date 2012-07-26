package com.soaplat.amsmgr.managerimpl;

import java.util.Date;
import java.util.List;

import com.soaplat.amsmgr.bean.AmsFileSSchedule;
import com.soaplat.amsmgr.manageriface.IAmsFileSScheduleManager;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;


/**
 * Title 		:the Class AmsFileSScheduleManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class AmsFileSScheduleManagerImpl implements IAmsFileSScheduleManager {

	/** The base dao. */
	IBaseDAO baseDAO;
	
	/** The getamspk. */
	IGetPK getamspk;
	
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

		AmsFileSSchedule filesschedule=(AmsFileSSchedule)baseDAO.getById(AmsFileSSchedule.class, pkid);
		if(filesschedule!=null){
			baseDAO.delete(filesschedule);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List filesschedulelist=baseDAO.findAll("AmsFileSSchedule","relid");
		return filesschedulelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List filesschedulelist=baseDAO.findByProperty("AmsFileSSchedule", propertyName, value);
		return filesschedulelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		AmsFileSSchedule filesschedule=(AmsFileSSchedule)baseDAO.getById(AmsFileSSchedule.class, pkid);
		return filesschedule;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		AmsFileSSchedule filesschedule=(AmsFileSSchedule)baseDAO.loadById(AmsFileSSchedule.class, pkid);
		return filesschedule;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		AmsFileSSchedule filesschedule=new AmsFileSSchedule();
		filesschedule=(AmsFileSSchedule)object;
		filesschedule.setInputtime(new Date());

		String strMaxPK=getamspk.GetTablePK("AmsFileSSchedule");
		filesschedule.setRelid(strMaxPK);
		baseDAO.save(filesschedule);
		//
		return baseDAO.getById(AmsFileSSchedule.class, strMaxPK);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);
		

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
	 * Sets the base dao.
	 * 
	 * @param baseDAO the new base dao
	 */
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * Sets the getamspk.
	 * 
	 * @param getamspk the new getamspk
	 */
	public void setGetamspk(IGetPK getamspk) {
		this.getamspk = getamspk;
	}

	

}
