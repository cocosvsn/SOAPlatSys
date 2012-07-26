package com.soaplat.cmsmgr.daoiface;

import java.util.List;

import com.soaplat.cmsmgr.bean.PackageFiles;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgListMang;

public interface IPortalColumnDAO {

	
	public void deleteById(String pkid);
	public List findAll();
	public List findByProperty(String propertyName, Object value);
	public PortalColumn getById(String pkid);
	public PortalColumn loadById(String pkid);
	public PortalColumn save(PortalColumn portalColumn);
	public void update(PortalColumn portalColumn);
	
	// 根据序列查询所有栏目节点
	public List getPortalColumnsByDefcatseq(String defcatseq);
	
	// 根据序列查询所有栏目叶子节点
	public List getLeafPortalColumnsByDefcatseq(String defcatseq);
	
	// 20100114 16:52
	// 根据序列查询所有栏目叶子节点(有效的)
	public List getValidLeafPortalColumnsByDefcatseq(String defcatseq);
	
	// 20100114 16:10
	// 根据栏目code序列，查询，得到所有有效的需要生成发布文件的栏目
	public List getAllPublishPortalColumnsByDefcatseq(String defcatseq);
	
	/**
	 * 查询报纸的栏目
	 * @return List<PortalColumn>
	 */
	public List<PortalColumn> getPaperPortalColumn();
}
