package com.soaplat.cmsmgr.managerimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.cmsmgr.bean.ProgListDeleteDetail;
import com.soaplat.cmsmgr.dto.ProgListDeleteDetailVo;
import com.soaplat.cmsmgr.manageriface.IProgListDeleteDetailManager;
import com.soaplat.cmsmgr.util.ListUtil;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ProgListDeleteDetailManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgListDeleteDetailManagerImpl implements IProgListDeleteDetailManager {
	
	/** The ProgListDeleteDetaildao. */
	IBaseDAO baseDAO;
	
	/** The getpk. */
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

		ProgListDeleteDetail ProgListDeleteDetail=(ProgListDeleteDetail)baseDAO.getById(ProgListDeleteDetail.class, pkid);
		if(ProgListDeleteDetail!=null){
			baseDAO.delete(ProgListDeleteDetail);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List ProgListDeleteDetaillist=baseDAO.findAll("ProgListDeleteDetail","id");
		return ProgListDeleteDetaillist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List ProgListDeleteDetaillist=baseDAO.findByProperty("ProgListDeleteDetail", propertyName, value);
		return ProgListDeleteDetaillist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgListDeleteDetail ProgListDeleteDetail=(ProgListDeleteDetail)baseDAO.getById(ProgListDeleteDetail.class, pkid);
		return ProgListDeleteDetail;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgListDeleteDetail ProgListDeleteDetail=(ProgListDeleteDetail)baseDAO.loadById(ProgListDeleteDetail.class, pkid);
		return ProgListDeleteDetail;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		ProgListDeleteDetail ProgListDeleteDetail = (ProgListDeleteDetail) object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("ProgListDeleteDetail", "id");
		String strMaxPK=getcmspk.GetTablePK("ProgListDeleteDetail", strCurMaxPK);
		ProgListDeleteDetail.setId(strMaxPK);
		this.baseDAO.save(ProgListDeleteDetail);
		return ProgListDeleteDetail;
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
	public void setProgListDeleteDetaildao(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
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
	
	public void save(List<ProgListDeleteDetail> progListDeleteDetails, 
			String scheduleDate) {
		List<ProgListDeleteDetailVo> oldProgListDeleteDetailVOs = 
				this.getProgListDeleteDetailVOsByScheduledate(scheduleDate);
		try {
			List<String> oldProgListDeleteDetailIds = ListUtil.getPropertiesList(
					oldProgListDeleteDetailVOs, "id");
			for (String string : oldProgListDeleteDetailIds) {
				this.deleteById(string);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		for (ProgListDeleteDetail progListDeleteDetail : progListDeleteDetails) {
			this.save(progListDeleteDetail);
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

//	public void setProgListDeleteDetaildao(IBaseDAO baseDAO) {
//		this.baseDAO = baseDAO;
//	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * 根据编单日期ID, 查询该编单日期下所需强制删除的节目列表
	 * @param scheduleDate 编单日期ID
	 * @return List<ProgListDeleteDetailVo>
	 */
	@SuppressWarnings("unchecked")
	public List<ProgListDeleteDetailVo> getProgListDeleteDetailVOsByScheduledate(
			String scheduleDate) {
		List<ProgListDeleteDetailVo> progListDeleteDetailVos = new ArrayList<ProgListDeleteDetailVo>();
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("scheduleDate", scheduleDate);
		List<ProgListDeleteDetail> list = this.baseDAO.queryByNamedQuery(
				"query.ProgListDeleteDetailVo.by.scheduleDate", map);
		for (ProgListDeleteDetail progListDeleteDetail : list) {
			ProgListDeleteDetailVo progListDeleteDetailVo = new ProgListDeleteDetailVo();
			progListDeleteDetailVo.setId(progListDeleteDetail.getId());
			progListDeleteDetailVo.setProgPackageId(progListDeleteDetail.getProgPackageId());
			progListDeleteDetailVo.setProgPackageName(progListDeleteDetail.getProgPackageName());
			progListDeleteDetailVo.setInputTime(progListDeleteDetail.getInputTime());
			if (null == progListDeleteDetail.getSiteCode()) {
				progListDeleteDetailVo.setSiteName("全");
			} else {
				List<CmsSite> cmsSites = this.baseDAO.findByProperty(
						"CmsSite", "siteCode", progListDeleteDetail.getSiteCode());
				if (0 < cmsSites.size()) {
					CmsSite cmsSite = cmsSites.get(0);
					progListDeleteDetailVo.setSiteCode(cmsSite.getSiteCode());
					progListDeleteDetailVo.setSiteName(cmsSite.getSitename());
				}
			}
			progListDeleteDetailVos.add(progListDeleteDetailVo);
		}
		return progListDeleteDetailVos;
	}
	
	public List<ProgListDeleteDetail> getProgListDeleteDetailsBySitecodeScheduledate(
			String siteCode,
			String scheduleDate
			)
	{
		List<ProgListDeleteDetail> pldds = null;
		
		Map map = new HashMap(0);
		map.put("siteCode", siteCode);
		map.put("scheduleDate", scheduleDate);
		pldds = (List<ProgListDeleteDetail>)baseDAO.queryByNamedQuery(
				"getProgListDeleteDetailsBySitecodeScheduledate", map);
		
		return pldds;
	}
	
	public List<ProgListDeleteDetail> getProgListDeleteDetailsByScheduledate(
			String scheduleDate
			)
	{
		List<ProgListDeleteDetail> pldds = null;
		
		Map map = new HashMap(0);
		map.put("scheduleDate", scheduleDate);
		pldds = (List<ProgListDeleteDetail>)baseDAO.queryByNamedQuery(
				"getProgListDeleteDetailsByScheduledate", map);
		
		return pldds;
	}
}
