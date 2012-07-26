package com.soaplat.cmsmgr.manageriface;

import java.util.List;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IPortalColumnManager extends IBaseManager {

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
	
	/**
	 * 根据品牌code、validtag、叶子节点，查询栏目
	 * @param siteCode 品牌code
	 * @param validFlag 有效标识
	 * @param isleaf 叶子节点标识
	 * @since 2010-09-21
	 * @调用hql 
	 */
	public List getPortalColumnsBySitecodeValidtagIsleaf(
			String siteCode,
			Long validFlag,
			String isleaf
	);
	
	/**
	 * 根据品牌Code查询该品牌下所有栏目
	 * @param siteCode 品牌Code
	 * @return 返回栏目列表
	 */
	public List<PortalColumn> getPortalColumnsBySiteCode(String siteCode);

	/**
	 * 根据编单日期ID, 查询该日期下所有栏目
	 * @param scheduleDate 编单日期ID, 格式: 20100909000000
	 * @return List<PortalColumn>
	 */
	public List<PortalColumn> queryPortalColumnsByScheduleDate(String scheduleDate);
	
	/**
	 * 根据编单日期ID, 品牌Code, 查询该日期下所有栏目
	 * @param scheduleDate 编单日期ID, 格式: 20100909000000
	 * @param siteCode 品牌Code
	 * @return List<PortalColumn>
	 */
	public List<PortalColumn> queryPortalColumnsByScheduleDate(
			String scheduleDate, String siteCode);
	
	/**
	 * 根据编单细表的节目包ID查询该节目包曾经编单过的所有栏目
	 * @param progPackageId 节目包ID
	 * @return 返回节目包编单的所有栏目集合
	 */
	public List<PortalColumn> queryPortalColumns(String progPackageId);
	
	/**
	 * 查询终端使用的有效栏目, 用于生成栏目JS
	 * @return
	 */
	public List<PortalColumn> queryPortalColumnsByValid();
	
	public List<PortalColumn> queryPortalColumnsByRolesAndValid(
			String scheduleDate, List<String> roles, String currAction);

	/**
	 * 根据栏目序列,查询该栏目的所有子节点
	 * @param seq 栏目序列
	 * @return 返回子栏目集合
	 */
	public List<PortalColumn> queryChilds(String seq);

	/**
	 * 查询停用的父栏目
	 * @param parentCodes 父栏目Code集合
	 * @param validFlag 启用之前的栏目状态(停用: 2)
	 * @return 
	 */
	public List<PortalColumn> queryDiffParents(List<String> parentCodes, Long validFlag);
	
	/**
	 * 删除栏目时判断该栏目下是否存在有效子栏目
	 * @param columnCode 栏目Code
	 * @return true:存在有效子栏目, false:不存在有效子栏目
	 */
	public boolean isExistValidChild(String columnCode);
	
	/**
	 * 刷新Root栏目的修改时间
	 * @return
	 */
	public int updateRootTime();
	
	/**
	 * 查询指定栏目Code是否已经使用
	 * @param columnCode 栏目Code
	 * @return 返回栏目Code是否存在
	 */
	public boolean isExistsColumnCode(String columnCode);
}
