package com.srmmgr.managerimpl;


import java.util.Date;
import java.util.List;

import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;
import com.srmmgr.bean.IpQamChannelProgNo;
import com.srmmgr.manageriface.IIpQamChannelProgNoManager;



// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class IpQamChannelProgNoManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class IpQamChannelProgNoManagerImpl implements IIpQamChannelProgNoManager {
	
	/** The base dao. */
	IBaseDAO baseDAO;
	
	/** The getsrmpk. */
	IGetPK getsrmpk;
	
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

		IpQamChannelProgNo ipqamchannelprogno=(IpQamChannelProgNo)baseDAO.getById(IpQamChannelProgNo.class, pkid);
		if(ipqamchannelprogno!=null){
			baseDAO.delete(ipqamchannelprogno);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List ipqamchannelprognolist=baseDAO.findAll("IpQamChannelProgNo","ipqamchannelprogid");
		return ipqamchannelprognolist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List ipqamchannelprognolist=baseDAO.findByProperty("IpQamChannelProgNo", propertyName, value);
		return ipqamchannelprognolist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		IpQamChannelProgNo ipqamchannelprogno=(IpQamChannelProgNo)baseDAO.getById(IpQamChannelProgNo.class, pkid);
		return ipqamchannelprogno;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		IpQamChannelProgNo ipqamchannelprogno=(IpQamChannelProgNo)baseDAO.loadById(IpQamChannelProgNo.class, pkid);
		return ipqamchannelprogno;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		IpQamChannelProgNo ipqamchannelprogno=new IpQamChannelProgNo();
		ipqamchannelprogno=(IpQamChannelProgNo)object;
		ipqamchannelprogno.setInputtime(new Date());
		String strMaxPK=baseDAO.getMaxPropertyValue("IpQamChannelProgNo", "ipqamchannelprogid");
		strMaxPK=getsrmpk.GetTablePK("IpQamChannelProgNo",strMaxPK);
		ipqamchannelprogno.setIpqamchannelprogid(strMaxPK);
		baseDAO.save(ipqamchannelprogno);
		//
		return baseDAO.getById(IpQamChannelProgNo.class, strMaxPK);

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
	 * Sets the getsrmpk.
	 * 
	 * @param getsrmpk the new getsrmpk
	 */
	public void setGetsrmpk(IGetPK getsrmpk) {
		this.getsrmpk = getsrmpk;
	}

}
