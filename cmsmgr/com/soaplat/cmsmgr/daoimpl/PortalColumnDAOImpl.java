package com.soaplat.cmsmgr.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.daoiface.IPortalColumnDAO;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;

public class PortalColumnDAOImpl implements IPortalColumnDAO {


	/** The PortalColumndao. */
	IBaseDAO baseDAO;

	/** The getcmspk. */
	IGetPK getcmspk;




	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {

		PortalColumn PortalColumn = (PortalColumn) baseDAO.getById(
				PortalColumn.class, pkid);
		if (PortalColumn != null) {
			baseDAO.delete(PortalColumn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List list = baseDAO.findAll("PortalColumn", "displayorder");
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang
	 * .String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List list = baseDAO.findByProperty("PortalColumn",
				propertyName, value);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public PortalColumn getById(String pkid) {
		PortalColumn portalColumn = (PortalColumn) baseDAO.getById(
				PortalColumn.class, pkid);
		return portalColumn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public PortalColumn loadById(String pkid) {
		PortalColumn portalColumn = (PortalColumn) baseDAO.loadById(
				PortalColumn.class, pkid);
		return portalColumn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public PortalColumn save(PortalColumn portalColumn) {


		String strCurMaxPK = baseDAO.getMaxPropertyValue("PortalColumn",
				"columnclassid");
		String strMaxPK = getcmspk.GetTablePK("PortalColumn", strCurMaxPK);
		portalColumn.setColumnclassid(strMaxPK);
		baseDAO.save(portalColumn);

		return portalColumn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(PortalColumn portalColumn) {
		baseDAO.update(portalColumn);

	}



	public List findbyExample(Object exampleentity) {
		List list = baseDAO.findbyExample(exampleentity);
		return list;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}

	public List getPortalColumnsByDefcatseq(String defcatseq) {
		defcatseq = defcatseq + "%";
		Map map = new HashMap();
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery(
				"select_PortalColumnsByDefcatseq", map);
		return list;
	}

	// 根据序列查询所有栏目叶子节点
	public List getLeafPortalColumnsByDefcatseq(String defcatseq) {
		defcatseq = defcatseq + "%";
		Map map = new HashMap();
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery(
				"select_LeafPortalColumnsByDefcatseq", map);
		return list;
	}

	// 20100114 16:52
	// 根据序列查询所有栏目叶子节点(有效的)
	public List getValidLeafPortalColumnsByDefcatseq(String defcatseq) {
		defcatseq = defcatseq + "%";
		Map map = new HashMap();
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery(
				"select_ValidLeafPortalColumnsByDefcatseq", map);
		return list;
	}

	// 20100114 16:10
	// 根据栏目code序列，查询，得到所有有效的需要生成发布文件的栏目
	public List getAllPublishPortalColumnsByDefcatseq(String defcatseq) {
		defcatseq = defcatseq + "%";
		Map map = new HashMap();
		map.put("defcatseq", defcatseq);
		List list = baseDAO.queryByNamedQuery(
				"select_AllPublishPortalColumnsByDefcatseq", map);
		return list;
	}

	/**
	 * 查询报纸的栏目
	 * @return List<PortalColumn>
	 */
	@SuppressWarnings("unchecked")
	public List<PortalColumn> getPaperPortalColumn() {
		return this.baseDAO.queryByNamedQuery(
						"query.PortalColumn.by.portalColumnName");
	}
	
	
}
