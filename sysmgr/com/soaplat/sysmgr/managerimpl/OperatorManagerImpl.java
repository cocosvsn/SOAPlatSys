package com.soaplat.sysmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;




import com.soaplat.sysmgr.bean.Operator;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.manageriface.IOperatorManager;


// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class OperatorManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class OperatorManagerImpl implements IOperatorManager {
	
	/** The baseDAO. */
	IBaseDAO baseDAO;
	
	/** The getsyspk. */
	IGetPK getsyspk;
	
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
		Operator operator=(Operator)baseDAO.getById(Operator.class, pkid);
		if(operator!=null){
			baseDAO.delete(operator);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List operatorlist=baseDAO.findAll("Operator","operatorid");
		return operatorlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List operatorlist=baseDAO.findByProperty("Operator", propertyName, value);
		return operatorlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		Operator operator=(Operator)baseDAO.getById(Operator.class, pkid);
		return operator;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		Operator operator=(Operator)baseDAO.loadById(Operator.class, pkid);
		return operator;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		String strMaxPK="";
		Operator operator=new Operator();
		operator=(Operator)object;
		
		operator.setInputtime(new Date());
		strMaxPK=baseDAO.getMaxPropertyValue("Operator", "operatorid");
		strMaxPK=getsyspk.GetTablePK("Operator", strMaxPK);
		operator.setOperatorid(strMaxPK);
		baseDAO.save(operator);
		//
		System.out.println("OperatorID:::"+strMaxPK);
		
		return baseDAO.getById(Operator.class, strMaxPK);
		
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);

	}


	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IOperatorManager#getOperMenu(java.lang.String)
	 */
	public List getOperMenu(java.lang.String  operatorid) {
		
		Map map=new HashMap();
		map.put("id", operatorid);
		List menulist=baseDAO.queryByNamedQuery("select_rolemenu", map);
		return menulist;
		
		
		
	}
	

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IOperatorManager#Login(java.lang.String, java.lang.String)
	 */
	public Object Login(String userid, String password) {
		Operator operator=new Operator();
		operator.setUserid(userid);
		operator.setPassword(password);
		List list=baseDAO.findbyExample(operator);	
		return list;		
		}
	
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		//Operator oper=null;
		
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
	
	/**
	 * Sets the baseDAO.
	 * 
	 * @param baseDAO the new baseDAO
	 */
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * Sets the getsyspk.
	 * 
	 * @param getsyspk the new getsyspk
	 */
	public void setGetsyspk(IGetPK getsyspk) {
		this.getsyspk = getsyspk;
	}

	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}

}
