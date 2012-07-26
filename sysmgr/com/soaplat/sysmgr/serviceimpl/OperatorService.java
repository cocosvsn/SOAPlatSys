package com.soaplat.sysmgr.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.soaplat.sysmgr.bean.Operator;
import com.soaplat.sysmgr.bean.OperatorInfo;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.manageriface.IOperatorManager;


// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class OperatorService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class OperatorService implements IOperatorManager {
	private static Logger operatLogger = Logger.getLogger("operation");
	private static Logger cmsLogger = Logger.getLogger("Cms");
	
	/** The operator manager. */
	public IOperatorManager operatorManager=null;
	
	/**
	 * Instantiates a new operator service.
	 */
	public OperatorService()
	{
		operatorManager=(IOperatorManager)ApplicationContextHolder.webApplicationContext.getBean("operatorManager");
	}
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IOperatorManager#Login(java.lang.String, java.lang.String)
	 */
	public Object Login(String userid, String password) throws Exception {
		
		List operlist=new ArrayList();
		List menulist=new ArrayList();
		operlist=(List)operatorManager.Login(userid, password);
		
		//get query list
		if(operlist.size()<=0){
			cmsLogger.debug("*****no userid*****"+userid+" &&&&&&&"+password);
			operatLogger.warn("用户名[ " + userid + " ]登陆系统失败!");
			return null;
		} else {
//			if(true){
//				
//				throw new Exception("登录成功！");
//			}
			Operator operator=new Operator();
			OperatorInfo operatorinfo=new OperatorInfo();
			//get operator
			operator=(Operator)operlist.get(0);
			//get operator's menulist
			menulist=this.getOperMenu(operator.getOperatorid());
			//set operatorinfo 
			operatorinfo.setOperator(operator);
			
			operatorinfo.setMenulist(menulist);
			operatLogger.info("用户[ " + operator.getOperatorname() + " ]登陆系统成功!");
			
			return operatorinfo;
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IOperatorManager#getOperMenu(java.lang.String)
	 */
	public List getOperMenu(String operatorid) {
		List list=operatorManager.getOperMenu(operatorid);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		System.out.println("*****before saveobjects*******");
//		operatorManager=ApplicationContextHolder.getOperatorService();
		operatorManager.save(object);
		System.out.println("*****after saveobjects*******");
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		operatorManager.update(object);
	
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		operatorManager.delete(object);
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {
		operatorManager.deleteById(pkid);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List operlist=operatorManager.findAll();
		return operlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List operlist=operatorManager.findByProperty(propertyName, value);
		return operlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		Object object=operatorManager.getById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		Object object=operatorManager.loadById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		Object saveobject=operatorManager.save(object);
		return saveobject;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		operatorManager.update(object);

	}
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteByIDRetAll(java.lang.String)
	 */
	/**
	 * Delete by id ret all.
	 * 
	 * @param pkid the pkid
	 * 
	 * @return the list
	 */
	public List deleteByIDRetAll(String pkid) {
		this.deleteById(pkid);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteRetAll(java.lang.Object[])
	 */
	/**
	 * Delete ret all.
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List deleteRetAll(Object[] object) {
		this.delete(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object)
	 */
	/**
	 * Save ret all.
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List saveRetAll(Object object) {
		this.save(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object[])
	 */
	/**
	 * Save ret all.
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List saveRetAll(Object[] object) {
		this.save(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object)
	 */
	/**
	 * Update ret all.
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List updateRetAll(Object object) {
		this.update(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object[])
	 */
	/**
	 * Update ret all.
	 * 
	 * @param object the object
	 * 
	 * @return the list
	 */
	public List updateRetAll(Object[] object) {
		this.update(object);
		List list=this.findAll();
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findbyExample(java.lang.Object)
	 */
	public List findbyExample(Object exampleentity) {
		List list=operatorManager.findbyExample(exampleentity);
		return list;
	}

	@SuppressWarnings("unchecked")
	public Operator getOperator(Operator operatorExample) {
		List<Operator> operators = this.operatorManager.findbyExample(operatorExample);
		cmsLogger.debug("根据用户名[ " + operatorExample.getUserid() + " ]和密码取得用户数: " + operators);
		if(0 < operators.size())
			return operators.get(0);
		return null;
	}
}
