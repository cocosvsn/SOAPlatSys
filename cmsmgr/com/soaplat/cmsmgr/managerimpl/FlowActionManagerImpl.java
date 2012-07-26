package com.soaplat.cmsmgr.managerimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.FlowAction;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IFlowActionManager;
import com.sun.org.apache.bcel.internal.generic.Select;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class FlowActionManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class FlowActionManagerImpl implements IFlowActionManager {
	
	/** The FlowActiondao. */
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

		FlowAction flowAction=(FlowAction)baseDAO.getById(FlowAction.class, pkid);
		if(flowAction!=null){
			baseDAO.delete(flowAction);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List flowActionlist=baseDAO.findAll("FlowAction","flowActionid");
		return flowActionlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List flowActionlist=baseDAO.findByProperty("FlowAction", propertyName, value);
		return flowActionlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		FlowAction flowAction=(FlowAction)baseDAO.getById(FlowAction.class, pkid);
		return flowAction;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		FlowAction flowAction=(FlowAction)baseDAO.loadById(FlowAction.class, pkid);
		return flowAction;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		FlowAction flowAction = new FlowAction();
		flowAction = (FlowAction)object;
		
		baseDAO.save(flowAction);
//		Long lMaxPk = flowAction.getFlowActionid();
		return flowAction;
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

	/**
	 * 根据编单日期ID, 栏目ID, 查询栏目编单活动名称
	 * @param scheduleDate 编单日期ID
	 * @param columnId 栏目ID
	 * @return 返回活动名称
	 */
	@SuppressWarnings("unchecked")
	public String getActionNameByScheduleDateAndColumnID(
			String scheduleDate, String columnId) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("scheduleDate", scheduleDate);
		params.put("columnId", columnId);
		List<String> list = this.baseDAO.queryByNamedQuery(
				"query.FlowAction.by.scheduleDate.ColumnId", params);
		if (0 < list.size()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据编单日期ID, 栏目ID, 查询栏目编单活动名称
	 * @param scheduleDate 编单日期ID
	 * @param columnId 栏目ID
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public FlowAction getByScheduleDateAndColumnID(
			String scheduleDate, String columnId) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("scheduleDate", scheduleDate);
		params.put("columnId", columnId);
		List<FlowAction> list = this.baseDAO.queryByNamedQuery(
				"query.FlowAction.by.scheduleDate.ColumnId", params);
		if (0 < list.size()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询活动表中指定属性值, 查询所有
	 * @param propertiesName
	 * @return
	 */
	public List<?> getFlowActionProperties(String... properties) {
		StringBuilder hqlBuilder = new StringBuilder("select");
		for (String string : properties) {
			hqlBuilder.append(" f.");
			hqlBuilder.append(string);
		}
		
		if (-1 == hqlBuilder.indexOf("f.")) {
			hqlBuilder.append(" f");
		}
		
		hqlBuilder.append(" from FlowAction as f");
		
		return this.baseDAO.executeHQL(hqlBuilder.toString());
	}
	
	/**
	 * 根据ID和属性名, 查询属性值
	 * @param id 主键ID
	 * @param properties 属性名称
	 * @return 返回属性值
	 */
	@SuppressWarnings("unchecked")
	public String getPropertiesById(String id, String properties) {
		String hql = "select f." + properties + " from FlowAction as f where f.actionid = :id";
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("id", id);
		List<String> list = (List<String>) this.baseDAO.query(hql, params);
		if (0 < list.size()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据活动编号, 查询大于或小于等于该活动的所有活动集合
	 * @param actionId 活动ID
	 * @param lessEqual 是否查询小于等于, 如果是则返回小于等于的结果, 否则返回大于的结果
	 */
	@SuppressWarnings("unchecked")
	public List<String> getGreaterOrLessAction(String actionId, boolean lessEqual) {
		List<String> greaterActionIdList = new ArrayList<String>();
		List<String> lessActionIdList = new ArrayList<String>();
		long currActionOrder = Long.valueOf(this.getPropertiesById(actionId, "remark"));
		List<FlowAction> actions = (List<FlowAction>) this.getFlowActionProperties();
		for (FlowAction flowAction : actions) {
			if (currActionOrder < Long.valueOf(flowAction.getRemark())) {
				greaterActionIdList.add(flowAction.getActionid());
			} else {
				lessActionIdList.add(flowAction.getActionid());
			}
		}
		if (lessEqual) {
			return lessActionIdList;
		} else {
			return greaterActionIdList;
		}
	}
}
