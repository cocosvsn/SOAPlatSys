package com.soaplat.cmsmgr.managerimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.cmsmgr.bean.PackageFiles;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgProduct;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.dto.EncryptProgVo;
import com.soaplat.cmsmgr.dto.PackageProductVo;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IFileStyleManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;

/**
 * Title 		:the Class ProgPackageManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgPackageManagerImpl implements IProgPackageManager {
	private static final Logger cmsLog = Logger.getLogger("Cms");
	private static FileOperationImpl fileopr = new FileOperationImpl();
	
	/** The progPackagedao. */
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

		ProgPackage progPackage=(ProgPackage)baseDAO.getById(ProgPackage.class, pkid);
		if(progPackage!=null){
			baseDAO.delete(progPackage);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List progPackagelist=baseDAO.findAll("ProgPackage","productid");
		return progPackagelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List progPackagelist=baseDAO.findByProperty("ProgPackage", propertyName, value);
		return progPackagelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgPackage progPackage=(ProgPackage)baseDAO.getById(ProgPackage.class, pkid);
		return progPackage;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgPackage progPackage=(ProgPackage)baseDAO.loadById(ProgPackage.class, pkid);
		return progPackage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		// 说明：
		// 如果节目包的主键已经存在，则不需要新生成主键

		ProgPackage progPackage = new ProgPackage();
		progPackage = (ProgPackage) object;
		// String strMaxPK = getcmspk.GetTablePK("ProgPackage");

		if (progPackage.getProductid() == null
				|| progPackage.getProductid().equalsIgnoreCase("")) {
			String progtype = progPackage.getProgtype();
			String progproperty = "P";
			String strMaxPK = progtype + "|" + progproperty;
			strMaxPK = getcmspk.GetTablePK("ProgPackage", strMaxPK);
			progPackage.setProductid(strMaxPK);
		}
		baseDAO.save(progPackage);

		return progPackage;

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

	public List getProgPackagesBySrvId(String srvId)
	{
		Map map = new HashMap(0);
		map.put("srvid", srvId);
		List list = baseDAO.queryByNamedQuery("select_progPackagesBySrvid",map);
		return list;
	}
	
	/*------------------ Huang Bo ---------------------*/
	
	/**
	 * 根据节目文件id查询节目包信息
	 * @param programFileId
	 * @return List<Object[4]>
	 * object[0]: ProgPackage.productid
	 * object[1]: ProgPackage.productname
	 * object[2]: ProgPackage.state
	 * object[3]: ProgPackage.dealstate
	 */
	public List<?> queryByProgramFileId(String programFileId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("programFileId", programFileId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgPackage.by.programFileId", map);
	}
	
	/**
	 * 查询报纸节目包
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProgPackage> queryPaper(String hql, Map<String, Object> params) {
		return (List<ProgPackage>) this.baseDAO.query(hql, params);
	}
	
	/**
	 * 根据ID列表, 查询所有节目包
	 * @param progPackageIDs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProgPackage> queryByIDs(List<String> progPackageIDs) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("progPackageIDs", progPackageIDs);
		return (List<ProgPackage>) 
				this.baseDAO.queryNamed("query.ProgPackage.by.IDs", map);
	}
	
	/**
	 * 根据编单日期, 栏目ID, 查询该栏目编单下所有生成节目预告的节目包信息
	 * @param scheduleDateStr 编单日期, 格式: 20100909000000
	 * @param columnId 栏目ID
	 * @return 返回需要生成节目预告的节目包信息
	 */
	@SuppressWarnings("unchecked")
	public List<ProgPackage> queryByScheduleDateAndColumn(String scheduleDateStr, 
			String columnId) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("scheduleDate", scheduleDateStr);
		map.put("columnID", columnId);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgPackage.by.scheduleDate.columnID", map);
	}
	
	/**
	 * 根据编单日期查询查询当天所有的编单节目包信息.
	 * @param scheduleDateStr 编单日期, 格式: 20100909000000
	 * @return 返回指定编单日所有编单节目包信息
	 */
	@SuppressWarnings("unchecked")
	public List<ProgPackage> queryByScheduleDate(String scheduleDateStr) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("scheduleDate", scheduleDateStr);
		return this.baseDAO.queryByNamedQuery(
				"query.ProgPackage.by.scheduleDate", map);
	}
	
	/**
	 * 根据节目包ID查询待加扰节目包的节目及文件信息
	 * @param progPakcageId 节目包ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public EncryptProgVo queryProgramFilesVoByProgPackageId(String progPackageId) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("progPackageId", progPackageId);
		List<EncryptProgVo> list = this.baseDAO.queryByNamedQuery(
				"query.ProgramFilesVo.by.progPackageId", map);
		if (0 < list.size()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据编单日期, 检查当前编单的节目包状态是否都为播发库[3], 未处理[0]状态 
	 * @param scheduleDate 编单日期ID
	 * @return 返回是否全部为播发库[3], 未处理[0]状态
	 */
	public boolean isCanBroadcast(String scheduleDate) {
		List<ProgPackage> progPackages = this.queryByScheduleDate(scheduleDate);
		for (ProgPackage progPackage : progPackages) {
			if (3 != progPackage.getState() || 19 != progPackage.getDealstate()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 查询产品加密和文件迁移节目包信息(包含产品信息)
	 * @param hql 用于查询的HQL语句
	 * @param params 查询HQL语句的参数列表
	 * @return 返回List<PackageProductVo>
	 */
	@SuppressWarnings("unchecked")
	public List<PackageProductVo> queryPackageProductVos(String hql, 
			Map<String, Object> params) {
		return (List<PackageProductVo>) this.baseDAO.query(hql, params);
	}
	
	/**
	 * 根据节目包ID, 查询该节目包主文件ContentID
	 * @param progPackageId 节目包ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> queryPackageCoutentID(List<String> progPackageIds) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("progPackageIds", progPackageIds);
		return (List<Long>) this.baseDAO.queryNamed(
				"query.ProgramFiles.ContentID.by.progPackageIds", map);
	}
	
	/**
	 * 根据节目包名称模糊查询节目包信息
	 * @param progPackageName 节目包名称
	 * @return List<ProgPackage>
	 */
	@SuppressWarnings("unchecked")
	public List<ProgPackage> queryProgPackagesByName(String progPackageName) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("progPackageName", progPackageName);
		return (List<ProgPackage>) this.baseDAO.queryNamed(
				"query.ProgPackage.by.progPackageName", map);
	} 
	
//	public String queryPackageSize(String progPackageId) {
//		Map<String, String> map = new HashMap<String, String>(1);
//		map.put("progPackageId", progPackageId);
//		return this.baseDAO.queryByNamedQuery(
//				"query.ProgPackage.by.scheduleDate", map);
//	}
	
	public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-common.xml",
				"applicationContext-cmsmgrdaobeans.xml",
				"applicationContext-amsmgrmanager.xml",
				"applicationContext-cmsmgrmanager.xml",
				"applicationContext-contentmgrmanagerbeans.xml",
				"applicationContext-contentmgrdaobeans.xml",
				"applicationContext-srmmgrmanager.xml",
				"applicationContext-srmmgrdaobeans.xml",
				"applicationContext-cmsmgrdaobeans.xml",
				"applicationContext-sysmgrmanager.xml",
				"applicationContext-sysmgrdao.xml",
				"applicationContext-amsmgrdaobeans.xml"
		});
		
		IProgPackageManager progPackageManager = 
				(IProgPackageManager) app.getBean("progPackageManager");
//		List<String> progPackageIDs = new ArrayList<String>(2);
//		progPackageIDs.add("PPRN20100830161418000109");
//		progPackageIDs.add("klsadfjlasfd");
//		List<ProgPackage> progPackages = progPackageManager.queryByScheduleDateAndColumn("20100912000000", "PC00000004");
//		for (ProgPackage progPackage : progPackages) {
//			System.out.println(progPackage);
//		}
//		EncryptProgVo encryptProgVo = progPackageManager.queryProgramFilesVoByProgPackageId("PPVP20101123100745000062");
		List<String> list = new ArrayList<String>();
		list.add("PPVP20110506201024000030");
		System.out.println(progPackageManager.queryPackageCoutentID(list).size());
	}

	/**
	 * 20101110 10:00 1.2 修改节目包的状态和处理状态
	 * @param progPackageManager
	 * @param packageFilesManager
	 * @param programFilesManager
	 * @param fileStyleManager
	 * @param amsstorageprgrelManager
	 * @param progPackage
	 * @param fileStyleCode
	 * @return
	 */
	public CmsResultDto updateRefreshStateOfProgPackage(
			IProgPackageManager progPackageManager,
			IPackageFilesManager packageFilesManager,
			IProgramFilesManager programFilesManager,
			IFileStyleManager fileStyleManager,
			IAmsStoragePrgRelManager amsstorageprgrelManager,
			ProgPackage progPackage,	// 节目包
			long fileStyleCode,			// 文件样式应用code
			String result				// 此次操作结果： "Y" - 成功； "N" - 失败
			)
	{
		cmsLog.info("Cms -> ProgPackageManagerImpl -> updateRefreshStateOfProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		/**
		 * 		存储等级
		 * 1 - NearOnline
		 * 2 - CaOnline
		 * 3 - Online
		 * 
		 * 		节目包状态
		 * 0 - 导入 （不使用）
		 * 1 - 缓存库 ，所有文件
		 * 2 - 加扰库 ，主文件
		 * 3 - 播控库 ，根据文件样式，应用code2
		 * 9 - 在上海的北京缓存库（不使用）
		 * 
		 * 		处理状态
		 * 0 - 未处理
		 * 1 - 处理中
		 * 8 - 失败
		 * 9 - 成功（不使用）
		 */
		
		/**
		 * ***** 应用场景 *****
		 * 1 - 加扰完成(内容加密)
		 * 		缓存库、处理中 --> 加扰库、未处理
		 * 
		 * 2 - 迁移完成(迁移至播发库)
		 * 		加扰库、处理中 --> 播发库、未处理
		 * 
		 * 3 - 文件删除
		 * 		播发库、未处理 --> 加扰库、未处理
		 * 		加扰库、未处理 --> 缓存库、未处理
		 * 		缓存库、未处理 --> 未导入、未处理
		 * 
		 * 4 - 无主文件节目包的文件导入
		 * 		未导入、未处理 --> 缓存库、未处理
		 * 		
		 */
		
		Long state = (long)-1;				// 节目包状态
		Long dealState = (long)-1;			// 处理状态
		Long newState = (long)-1;			// 新的节目包状态
		Long newDealState = (long)0;		// 新的处理状态
		String queryStclasscode = null;		// 需要判断的存储等级code
//		Long queryState = (long)-1;			// 需要判断的节目包状态
		
		String stclasscodeNearOnline = "NearOnline"; 	// 缓存库存储体等级code
		String stclasscodeCaOnline = "CaOnline"; 		// 加扰库存储体等级code
		String stclasscodeOnline = "Online"; 			// 播控库存储体等级code
		String stclasscodeBjOnline = "BjOnline"; 		// 在上海的北京缓存库存储体等级code，不使用
		
		if(progPackage != null)
		{
			state = progPackage.getState();
			dealState = progPackage.getDealstate();
			
			cmsLog.info("节目包状态：" + state);
			cmsLog.info("节目包处理状态：" + dealState);
			cmsLog.info("此次操作结果：" + result);
			
			if("Y".equalsIgnoreCase(result))
			{
				if(dealState == (long)0
					|| dealState == (long)8
					)
				{
					/**
					 * 未处理、失败
					 * 节目包状态回退（文件删除）
					 */
					if(state == (long)0)
					{
						cmsLog.info("此版本不处理。");
					}
					else if(state == (long)1)
					{
						newState = (long)-1;
						queryStclasscode = stclasscodeNearOnline;
					}
					else if(state == (long)2)
					{
						newState = (long)1;
						queryStclasscode = stclasscodeCaOnline;
					}
					else if(state == (long)3)
					{
						newState = (long)2;
						queryStclasscode = stclasscodeOnline;
					}
					else if(state == (long)9)
					{
						cmsLog.info("此版本不处理。");
					}
					else
					{
						cmsLog.info("此版本不处理。");
					}
				}
				else if(dealState == (long)1)
				{
					/**
					 * 处理中
					 * 节目包状态前进（加扰完成、迁移完成）
					 */
					if(state == (long)0)
					{
						cmsLog.info("此版本不处理。");
					}
					else if(state == (long)1)
					{
						newState = (long)2;
						queryStclasscode = stclasscodeCaOnline;
					}
					else if(state == (long)2)
					{
						newState = (long)3;
						queryStclasscode = stclasscodeOnline;
					}
					else if(state == (long)3)
					{
						cmsLog.info("此版本不处理。");
					}
					else if(state == (long)9)
					{
						cmsLog.info("此版本不处理。");
					}
					else if(state == (long)-1)
					{
						cmsLog.info("此版本不处理。");
					}
					else
					{
						cmsLog.info("此版本不处理。");
					}
				}
				else
				{
					cmsLog.info("此版本不处理。");
				}
				
				if (queryStclasscode == null || "".equalsIgnoreCase(queryStclasscode))
				{
					String str = "存储等级code为空，不处理。";
					cmsResultDto.setResultCode((long)1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.warn(str);
				}
				else
				{
					List<ProgramFiles> programFileses = new ArrayList<ProgramFiles>();	// 需要查询判断的文件列表
					if(stclasscodeNearOnline.equalsIgnoreCase(queryStclasscode))
					{
						/**
						 * 需要查询节目包所有文件
						 */
						List<PackageFiles> queryPackageFileses = packageFilesManager.findByProperty(
								"productid", 
								progPackage.getProductid()
								);
						for(int i = 0; i < queryPackageFileses.size(); i++)
						{
							PackageFiles pkf = queryPackageFileses.get(i);
							ProgramFiles pf = (ProgramFiles)programFilesManager.getById(pkf.getProgfileid());
							programFileses.add(pf);
						}
					}
					else if(stclasscodeCaOnline.equalsIgnoreCase(queryStclasscode))
					{
						/**
						 * 需要查询主文件
						 */
						List<PackageFiles> queryPackageFileses = packageFilesManager.getProgramFilesesByProductidProgrank(
								progPackage.getProductid(), 	// 节目包ID
								(long) 1 						// 主文件标识，0 - 不是; 1 - 是
								);
						for(int i = 0; i < queryPackageFileses.size(); i++)
						{
							PackageFiles pkf = queryPackageFileses.get(i);
							ProgramFiles pf = (ProgramFiles)programFilesManager.getById(pkf.getProgfileid());
							programFileses.add(pf);
						}
					}
					else if(stclasscodeOnline.equalsIgnoreCase(queryStclasscode))
					{
						/**
						 * 需要根据文件样式（应用代码），查询节目包文件
						 */
						programFileses = programFilesManager.getProgramFilesesByProgPackageFilestylecode(
								fileStyleManager, 
								packageFilesManager, 
								progPackage, 
								fileStyleCode
								);
					}
					
					/**
					 * 只要有一个文件不存在（文件位置表 或 实体文件）
					 * 那么节目包状态就需要改变
					 */
					boolean needUpdate = true;
					cmsLog.info("判断节目包的文件是否已经到位...");
					cmsLog.info("节目包的文件数量：" + programFileses.size());
					for (int k = 0; k < programFileses.size(); k++) 
					{
						ProgramFiles pf = programFileses.get(k);
						
						cmsLog.info("处理第" + (k + 1) + "个文件...");
						cmsLog.info("文件ID：" + pf.getProgfileid());
						
						/**
						 * 根据文件id、存储等级code，查询文件位置表记录
						 */
						List<AmsStoragePrgRel> asprs = amsstorageprgrelManager.getAmsStoragePrgRelsByProgfileidStclasscode(
								pf.getProgfileid(), 
								queryStclasscode
								);
						if(asprs != null && asprs.size() > 0)
						{
							AmsStoragePrgRel aspr = asprs.get(0);
							cmsLog.info("文件位置表记录已经存在，需要判断实体文件是否存在。");
							cmsLog.info("文件位置表ID：" + aspr.getRelid());
							
							/**
							 * 根据，查询实体文件
							 */
							List sourcepaths = packageFilesManager.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
									pf.getProgfileid(),
									queryStclasscode, 
									progPackage.getProductid()
									);
							if (sourcepaths != null && sourcepaths.size() >= 2)
							{
								String sourcepath = (String) sourcepaths.get(0);
								if(fileopr.checkSmbFileExist(sourcepath))
								{
									cmsLog.info("实体文件存在，该文件迁移完成，继续...");
								}
								else
								{
									cmsLog.info("实体文件不存在，文件尚未迁移完毕。");
									cmsLog.info("节目包文件尚未全部迁移完成，不修改节目包状态，停止循环判断。");
									needUpdate = false;
									break;
								}
							}
							else
							{
								/**
								 * 如何处理
								 */
								cmsLog.warn("查询文件路径为空。");
							}
						}
						else
						{
							cmsLog.info("文件位置表记录不存在，文件尚未迁移完毕。");
							cmsLog.info("节目包文件尚未全部迁移完成，不修改节目包状态，停止循环判断。");
							needUpdate = false;
							break;
						}
					}
					
					if(needUpdate == true)
					{
						cmsLog.info("节目包的所有文件都已经到位...");
						cmsLog.info("准备修改节目包状态和处理状态，节目包ID：" + progPackage.getProductid());
	
						progPackage.setState(newState);
						progPackage.setDealstate(newDealState);
						progPackageManager.update(progPackage);
						cmsLog.info("节目包的状态修改为：" + newState);
						cmsLog.info("节目包的处理状态修改为：" + newDealState);
						
						String str = "节目包的状态和处理状态修改为：" + newState + "，" + newDealState;
						cmsResultDto.setErrorMessage(str);
					}
					else
					{				
						String str = "节目包状态不需要修改，不处理。";
						cmsLog.info(str);
						cmsResultDto.setErrorMessage(str);
					}
				}
			}	// if("Y".equalsIgnoreCase(result))
			else if("N".equalsIgnoreCase(result))
			{
				/**
				 * 如果此次操作结果为：失败
				 * 并且节目包的处理状态为：处理中
				 * 那么不修改节目包的状态，只修改处理状态为：失败
				 */
				if(dealState == (long)1)
				{
					progPackage.setDealstate((long)8);
					progPackageManager.update(progPackage);
					String str = "节目包的处理状态修改为：失败(8)";
					cmsLog.info(str);
					cmsResultDto.setErrorMessage(str);
				}
				else
				{
					String str = "此次操作结果为“失败”，但是节目包的处理状态不是“处理中”，不处理。";
					cmsResultDto.setResultCode((long)1);
					cmsResultDto.setErrorMessage(str);
					cmsLog.warn(str);
				}
			}
			else
			{
				String str = "此次操作结果未能识别，不处理。";
				cmsResultDto.setResultCode((long)1);
				cmsResultDto.setErrorMessage(str);
				cmsLog.warn(str);
			}
		}	// if(progPackage == null)
		else
		{
			String str = "节目包为空。";
			cmsResultDto.setResultCode((long)1);
			cmsResultDto.setErrorMessage(str);
			cmsLog.warn(str);
		}	
		
		cmsLog.info("Cms -> ProgPackageManagerImpl -> updateRefreshStateOfProgPackage returns.");
		return cmsResultDto;
	}

	/**
	 * 1.20 添加节目包
	 */
	
	public void saveProgPackage(ProgPackage progPackage, List<String> programInfoIds, 
			String inputMainId) {
		// 1. 保存节目包信息
		progPackage.setState(1L);
		progPackage.setDealstate(0L);
		this.baseDAO.save(progPackage);
		//TODO 保存节目包-节目关系表信息, 以及节目包-文件关系表信息
	}

	/**
	 * 按产品统计节目包强制保留大小
	 * @return List<Object[]> <br/>
	 * object[0]: ProgProduct.KeyName <br/>
	 * object[1]: ProgProduct.KeyId <br/>
	 * object[2]: sum of package size group by ProgProduct.KeyName
	 */
	public List<Object[]> countOfForcedReservesPackageByProduct() {
		cmsLog.debug("count of forced reserves package by product");
		try {
			@SuppressWarnings("unchecked")
			List<Object[]> list = (List<Object[]>) this.baseDAO.queryByNamedQuery(
					"query.count.of.forced.reserves.package.by.product");
			cmsLog.debug("count of forced reserves package by product size: " + list.size());
			return list;
		} catch (RuntimeException re) {
			cmsLog.error("count of forced reserves package by product failed", re);
			throw re;
		}
	}
}
