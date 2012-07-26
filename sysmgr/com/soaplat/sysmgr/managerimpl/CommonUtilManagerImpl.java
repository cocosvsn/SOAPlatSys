package com.soaplat.sysmgr.managerimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.soaplat.sysmgr.common.CriteriaUtil;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.manageriface.ICommonUtilManager;

import flex.messaging.io.amf.ASObject;

public class CommonUtilManagerImpl implements ICommonUtilManager{
	IBaseDAO baseDAO;
	public List queryByCriteria(ASObject asobj) {
		// TODO Auto-generated method stub
		ASObject obj = ASObject.class.cast(asobj.get("criteria"));
		Criteria crit =baseDAO.returnCriteria((String)obj.get("_entity"));
		//添加查询字段
		Object selectObj = obj.get("_select");
		if (selectObj !=null)
		{
			ProjectionList prolist = Projections.projectionList();
			for (Object objtemp : (Object[])selectObj)
			{
				ASObject asobjtemp = ASObject.class.cast(objtemp);
				prolist.add(CriteriaUtil.addToPro(asobjtemp));
			}
			crit.setProjection(prolist);
		}
		

		
		//添加查询条件
		Object exprObj = obj.get("_expr");
		if (exprObj !=null)
		{
			for (Object objtemp : (Object[])exprObj)
			{
				ASObject asobjtemp = ASObject.class.cast(objtemp);
				crit.add(CriteriaUtil.addToRes(asobjtemp));
			}
		}
			
		//添加排序条件
		Object orderObj = obj.get("_order");
		if (orderObj !=null)
		{
			for (Object objtemp : (Object[])orderObj)
			{
				ASObject asobjtemp = ASObject.class.cast(objtemp);
				if (asobjtemp.get("_asc")!=null)
				{
					crit.addOrder(Order.asc((String)asobjtemp.get("_asc")));
				}
				else if (asobjtemp.get("_desc")!=null)
				{
					crit.addOrder(Order.asc((String)asobjtemp.get("_desc")));
				}
			}			
		}
		List list = baseDAO.queryByCriteria(crit);
		return list;
	}
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	public List queryByHQL(String strSQL) {
		// TODO Auto-generated method stub
		List list = null;
		try
		{
			list = baseDAO.executeHQL(strSQL);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

}
