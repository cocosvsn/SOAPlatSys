package com.srmmgr.managerimpl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;
import com.srmmgr.bean.IpQamChannel;
import com.srmmgr.manageriface.IIpQamChannelManager;



// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class IpQamChannelManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class IpQamChannelManagerImpl implements IIpQamChannelManager {
	
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

		IpQamChannel ipqamchannel=(IpQamChannel)baseDAO.getById(IpQamChannel.class, pkid);
		if(ipqamchannel!=null){
			baseDAO.delete(ipqamchannel);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List ipqamchannellist=baseDAO.findAll("IpQamChannel","ipqamchannelid");
		return ipqamchannellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List ipqamchannellist=baseDAO.findByProperty("IpQamChannel", propertyName, value);
		return ipqamchannellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		IpQamChannel ipqamchannel=(IpQamChannel)baseDAO.getById(IpQamChannel.class, pkid);
		return ipqamchannel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		IpQamChannel ipqamchannel=(IpQamChannel)baseDAO.loadById(IpQamChannel.class, pkid);
		return ipqamchannel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		String ipqamid="";
		String strMaxPK="";
		String preStr="";
		Map map=new HashMap(0);
		
		IpQamChannel ipqamchannel=new IpQamChannel();
		ipqamchannel=(IpQamChannel)object;
		ipqamid=ipqamchannel.getIpqamid();
		preStr=ipqamid+"%";
		
		//
		map.put("id", preStr);
		List list=baseDAO.queryByNamedQuery("select_maxipqamchannelid",map);
		
		//List list=baseDAO.executeHQL("select max(i.ipqamchannelid)from IpQamChannel s where i.ipqamchannelid like '"+ipqamid+"%'");
		if (list.get(0)==null)
		{
			strMaxPK=getsrmpk.GetTablePK("IpQamChannel", ipqamid);
			
		}else
		{
			strMaxPK=list.get(0).toString();
			strMaxPK=getsrmpk.GetTablePK("IpQamChannel", strMaxPK);
		}
		
		
		ipqamchannel.setInputtime(new Date());
		ipqamchannel.setIpqamchannelid(strMaxPK);
		baseDAO.save(ipqamchannel);
		//
		return baseDAO.getById(IpQamChannel.class, strMaxPK);

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
