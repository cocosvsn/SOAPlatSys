package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.FlowActivityOrder;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IFlowActivityOrderManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class FlowActivityOrderManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class FlowActivityOrderManagerImpl implements IFlowActivityOrderManager {
	
	/** The flowActivityOrderdao. */
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

		FlowActivityOrder flowActivityOrder=(FlowActivityOrder)baseDAO.getById(FlowActivityOrder.class, Long.valueOf(pkid));
		if(flowActivityOrder!=null){
			baseDAO.delete(flowActivityOrder);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List flowActivityOrderlist=baseDAO.findAll("FlowActivityOrder","flowactivityorderid");
		return flowActivityOrderlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List flowActivityOrderlist=baseDAO.findByProperty("FlowActivityOrder", propertyName, value);
		return flowActivityOrderlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		FlowActivityOrder flowActivityOrder=(FlowActivityOrder)baseDAO.getById(FlowActivityOrder.class, Long.valueOf(pkid));
		return flowActivityOrder;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		FlowActivityOrder flowActivityOrder=(FlowActivityOrder)baseDAO.loadById(FlowActivityOrder.class, Long.valueOf(pkid));
		return flowActivityOrder;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		FlowActivityOrder flowActivityOrder=new FlowActivityOrder();
		flowActivityOrder=(FlowActivityOrder)object;

		baseDAO.save(flowActivityOrder);
		Long lMaxPk = flowActivityOrder.getFlowactivityorderid();
		return baseDAO.getById(FlowActivityOrder.class, lMaxPk);

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

	public List getFunResourcesByFlowactivityidparent(String flowactivityidparent)
	{
		Map map = new HashMap();
		map.put("flowactivityidparent", flowactivityidparent);
		List list = baseDAO.queryByNamedQuery("select_FunResourcesByFlowactivityidparent", map);
		return list;
	}
	
	public List getOperatorsByFuncid(String funcid)
	{
		Map map = new HashMap();
		map.put("funcid", funcid);
		List list = baseDAO.queryByNamedQuery("select_OperatorsByFuncid", map);
		return list;
	}
	
	// Edited by Andy at 20091228 16:19
	// 查询下一活动nextIdAct，条件：State2，FLOWACTIVITYIDPARENT=currentIdAct
	public List getNextIdActsByCurrentIdActAndState2(String currentIdAct, String state2)
	{
		Map map = new HashMap();
		map.put("flowactivityidparent", currentIdAct);
		map.put("state2", state2);
		List list = baseDAO.queryByNamedQuery("select_NextIdActsByCurrentIdActAndState2", map);
		return list;
	}
	
	/**
	 * 通过当前活动编号, 以及流程活动顺序查询下一个流程活动编号
	 * @param currentAction 当前流程活动编号
	 * @param state 流程活动顺序 [R:回退; P:顺序; C:完成; N:新建;]
	 * @return 反回下一个流程活动编号
	 */
	@SuppressWarnings("unchecked")
	public String getNextAction(String currentAction, String state) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("currAct", currentAction);
		map.put("state", state);
		List<String> actions = this.baseDAO.queryByNamedQuery(
				"query.FlowActivityOrder.by.flowactivityidparent", map);
		if (0 < actions.size()) {
			return actions.get(0);
		}
		return null;
	}
	
	// Edited by Andy at 20091229 11:10
	// 根据state2、IdAct，查询得到idact，条件：state2、FLOWACTIVITYIDCHILD=IdAct
	public List getLastIdActsByCurrentIdActAndState2(String currentIdAct, String state2)
	{
		Map map = new HashMap();
		map.put("flowactivityidchild", currentIdAct);
		map.put("state2", state2);
		List list = baseDAO.queryByNamedQuery("select_LastIdActsByCurrentIdActAndState2", map);
		return list;
	}
}
