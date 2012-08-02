/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import cn.sh.sbl.cms.dao.IConfigDao;

import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoModuleManager;
import com.soaplat.cmsmgr.manageriface.ISequenceManager;
import com.soaplat.cmsmgr.util.DateUtil;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-11-12 上午11:30:20
 */
public class ProgramInfoService {
	private static Logger log = Logger.getLogger("Cms");
	private IConfigDao configDao;
	private ISequenceManager sequenceManager;
	private IProgramInfoManager programInfoManager;
	private IAmsStorageDirManager amsStorageDirManager;
	private IProgramInfoModuleManager programInfoModuleManager;
	
	public ProgramInfoService() {
		this.configDao = ApplicationContextHolder.webApplicationContext.getBean("configDaoImpl", IConfigDao.class);
		this.sequenceManager = (ISequenceManager) ApplicationContextHolder.webApplicationContext.getBean("sequenceManager");
		this.programInfoManager = (IProgramInfoManager) ApplicationContextHolder.webApplicationContext.getBean("programinfoManager");
		this.amsStorageDirManager = (IAmsStorageDirManager) ApplicationContextHolder.webApplicationContext.getBean("amsstoragedirManager");
		this.programInfoModuleManager = (IProgramInfoModuleManager) ApplicationContextHolder.webApplicationContext.getBean("programInfoModuleManager");
	}
	
	/**
	 * 查询节目信息和文件信息
	 * 根据传进来的参数拼写HQL语句进行查询
	 * @param id				节目ID
	 * @param title				节目名称
	 * @param style				样式编号
	 * @param inputTimeStart	录入时间开始, 格式: 2010-09-09
	 * @param inputTimeEnd		录入时间结束, 格式: 2010-10-10
	 * @param sourceFileName	原文件名, 包含扩展名长度限制60字符
	 * @return 返回List<Object[]>
	 * object[0]: ProgramInfo					节目对象
	 * object[1]: ProgramFiles					文件对象
	 * object[2]: ProgramInfo.programid			节目ID
	 * object[3]: ProgramInfo.title				节目名称
	 * object[4]: PackStyle.stylename			节目样式名称
	 * object[5]: ProgramInfo.inputtime			节目录入时间
	 * object[6]: ProgramFiles.processinstid	源文件名
	 * object[7]: ProgramInfo.dsflag			节目状态编号
	 * object[8]: ProgramInfo.remark			节目状态名称
	 * object[9]: Operator.operatorname			录入人员名称
	 * @throws Exception 
	 */
	public List<Object[]> queryProgramInfos(String id, String title, String style,
			String inputTimeStart, String inputTimeEnd, String sourceFileName) throws Exception {
		// ProgramInfo.dsflag : 节目状态[-1: 未导入; 0:迁移中; 1:迁移失败; 2:已入库; 3:已包装成节目包]
		String hql = "select pi, pf, pi.programid, pi.title, pt.classname, ps.stylename, pi.inputtime, pf.processinstid, pi.dsflag, pi.remark, o.operatorname from ProgramInfo as pi, ProgramFiles as pf, ProgType as pt, PackStyle as ps, Operator as o where pf.programid = pi.programid and pt.progtypeid = pi.progtype and pi.styleid = ps.styleid and o.operatorid = pi.inputmanid ";
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (null != id && 0 != id.trim().length()) {
			if (hql.contains("where")) {
				hql += " and";
			} else {
				hql += " where";
			}
			hql += " pi.programid like :id";
			map.put("id", "%" + id + "%");
		}
		if (null != title && 0 != title.trim().length()) {
			if (hql.contains("where")) {
				hql += " and";
			} else {
				hql += " where";
			}
			hql += " pi.title like :title";
			map.put("title", "%" + title + "%");
		} 
		if (null != style && 0 != style.trim().length()) {
			if (hql.contains("where")) {
				hql += " and";
			} else {
				hql += " where";
			}
			hql += " pi.styleid = :style";
			map.put("style", Long.valueOf(style));
		}
		if ((null != inputTimeStart && 0 != inputTimeStart.trim().length())
				&& (null != inputTimeEnd && 0 != inputTimeEnd.trim().length())) {
			if (hql.contains("where")) {
				hql += " and";
			} else {
				hql += " where";
			}
			hql += " pi.inputtime between :startTime and :endTime";
			map.put("startTime", DateUtil.parseDate("yyyy-MM-dd", inputTimeStart));
			map.put("endTime", DateUtil.parseDate("yyyy-MM-dd", inputTimeEnd));
		} else {
			if (null != inputTimeStart && 0 != inputTimeStart.trim().length()) {
				if (hql.contains("where")) {
					hql += " and";
				} else {
					hql += " where";
				}
				hql += " pi.inputtime >= :startTime";
				map.put("startTime", DateUtil.parseDate("yyyy-MM-dd", inputTimeStart));
			}
			if (null != inputTimeEnd && 0 != inputTimeEnd.trim().length()) {
				if (hql.contains("where")) {
					hql += " and";
				} else {
					hql += " where";
				}
				hql += " pi.inputtime <= :endTime";
				map.put("endTime", DateUtil.parseDate("yyyy-MM-dd", inputTimeEnd));
			}
		}
		if (null != sourceFileName && 0 != sourceFileName.trim().length()) {
			if (hql.contains("where")) {
				hql += " and";
			} else {
				hql += " where";
			}
			hql += " pf.processinstid like :processinstid";
			map.put("processinstid", "%" + sourceFileName + "%");
		}
		
		log.debug(hql + map.size());
		
		List<Object[]> list = this.programInfoModuleManager.queryProgramInfo(hql, map);
		for (Object[] objects : list) {
			int dsflag = Integer.parseInt(String.valueOf(objects[8]));
			objects[9] = getDsflagName(dsflag);
		}
		return list;
	}
//	public List<Object[]> queryProgramInfos(String id, String title, 
//			String inputTimeStart, String inputTimeEnd, 
//			String dsflag, String sourceFileName) throws Exception {
//		String hql = "select pi.programid, pi.title, pi.progtype, pi.titlebrief, pi.dsflag, pi.remark, pi.inputtime, pi.styleid, pf.processinstid, ps.stylename from ProgramInfo as pi, ProgramFiles as pf, PackStyle as ps where pf.programid = pi.programid and pi.styleid = ps.styleid";
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		if (null != id && 0 != id.trim().length()) {
//			if (hql.contains("where")) {
//				hql += " and";
//			} else {
//				hql += " where";
//			}
//			hql += " pi.programid like :id";
//			map.put("id", "%" + id + "%");
//		}
//		if (null != title && 0 != title.trim().length()) {
//			if (hql.contains("where")) {
//				hql += " and";
//			} else {
//				hql += " where";
//			}
//			hql += " pi.title like :title";
//			map.put("title", "%" + title + "%");
//		} 
//		if ((null != inputTimeStart && 0 != inputTimeStart.trim().length())
//				&& (null != inputTimeEnd && 0 != inputTimeEnd.trim().length())) {
//			if (hql.contains("where")) {
//				hql += " and";
//			} else {
//				hql += " where";
//			}
//			hql += " pi.inputtime between :startTime and :endTime";
//			map.put("startTime", DateUtil.parseDate("yyyy-MM-dd", inputTimeStart));
//			map.put("endTime", DateUtil.parseDate("yyyy-MM-dd", inputTimeEnd));
//		} else {
//			if (null != inputTimeStart && 0 != inputTimeStart.trim().length()) {
//				if (hql.contains("where")) {
//					hql += " and";
//				} else {
//					hql += " where";
//				}
//				hql += " pi.inputtime >= :startTime";
//				map.put("startTime", DateUtil.parseDate("yyyy-MM-dd", inputTimeStart));
//			}
//			if (null != inputTimeEnd && 0 != inputTimeEnd.trim().length()) {
//				if (hql.contains("where")) {
//					hql += " and";
//				} else {
//					hql += " where";
//				}
//				hql += " pi.inputtime <= :endTime";
//				map.put("endTime", DateUtil.parseDate("yyyy-MM-dd", inputTimeEnd));
//			}
//		}
//		if (null != dsflag && 0 != dsflag.trim().length()) {
//			if (hql.contains("where")) {
//				hql += " and";
//			} else {
//				hql += " where";
//			}
//			hql += " pi.dsflag = :dsflag";
//			map.put("dsflag", Long.valueOf(dsflag));
//		}
//		if (null != sourceFileName && 0 != sourceFileName.trim().length()) {
//			if (hql.contains("where")) {
//				hql += " and";
//			} else {
//				hql += " where";
//			}
//			hql += " pf.processinstid like :processinstid";
//			map.put("processinstid", "%" + sourceFileName + "%");
//		}
//		
//		log.debug(hql + map.size());
//		
//		List<Object[]> list = this.programInfoModuleManager.queryProgramInfo(hql, map);
//		log.debug("查询得出节目数量： " + list.size());
//		for (Object[] objects : list) {
//			long state = Long.valueOf(objects[4].toString());
//			if (-1 == state) {
//				objects[5] = "未导入缓存库";
//			} else if (0 == state) {
//				objects[5] = "迁移中";
//			} else if (1 == state) {
//				objects[5] = "迁移失败";
//			} else if (2 == state) {
//				objects[5] = "已入缓存库";
//			} else {
//				objects[5] = "已包装成节目包";
//			}
//			
//			if ("V".equals(objects[2])) {
//				objects[3] = "视频节目";
//			} else if ("R".equals(objects[2])) {
//				objects[3] = "富媒体节目";
//			} else {
//				objects[3] = "其它类型节目";
//			}
//		}
//		return list;
//	}
	
	/**
	 * 保存节目信息和文件信息
	 * 根据用户选择节目录入类型, 对节目表和文件表进行操作.
	 * 1. 正常: 保存节目表信息, 文件表信息, 生成迁移任务. 完成
	 * 2. 无实体文件: 保存节目表信息, 文件表信息, 节目表(注明节目状态为未导入)和<br>
	 *    文件表(注明无实体文件标识), 不生成迁移任务. 完成
	 * 3. 补充实体文件: 修改节目表和文件表标识, 补充原文件名信息, 生成迁移任务. 完成
	 * @param operatType	前台界面选择的节目录入类型[1: 正常, 2: 无实体文件, 3:补充实体文件]
	 * @param programInfo	节目表对象
	 * @param programFiles	文件表对象
	 * @param inputManId	操作人员ID
	 * @return Object[]
	 * object[0]: ProgramInfo
	 * object[1]: ProgramFiles
	 * @throws Exception	如果节目录入不成功, 抛出节目录入不成功异常, 异常信息为: 节目录入不成功!
	 * 如果节目录入成功, 则不返回信息.
	 */
	public Object[] addProgFile(String operatType, ProgramInfo programInfo, 
			ProgramFiles programFiles, String inputManId) throws Exception {
		programInfo.setDsflag(-1L);
		
		if (null == programInfo.getInputtime()) {
			programInfo.setInputtime(new Date());
		}
		
		if (null == programFiles.getInputtime()) {
			programFiles.setInputtime(new Date());
		}
		
		if (null == programInfo.getInputmanid()) {
			programInfo.setInputmanid(inputManId);
		}
		
		if (null == programFiles.getInputmanid()) {
			programFiles.setInputmanid(inputManId);
		}
		
		if ("Clip".equalsIgnoreCase(programFiles.getFilecode())
				&& 1 == programFiles.getProgrank()) {
			programFiles.setProgrank(0L);
		}
		
		if ((!"2".equals(operatType)) && (null == programFiles.getProcessinstid() 
				|| 0 == programFiles.getProcessinstid().trim().length())) {
			throw new NullPointerException(" 原文件名不能为空! ");
		}
		
		if (null != programFiles.getProgfileid()) {
			if ("H264".equals(programFiles.getFilecode())) {
				/**
				 * 修改文件名的扩展名由固定.ts为自适应, 录入时为.ts则修改后仍然为.ts
				 * HuangBo update by 2012年2月19日 20时23分
				 */
				programFiles.setFilename(programFiles.getProgfileid() 
						+ programFiles.getProcessinstid().substring(
								programFiles.getProcessinstid().lastIndexOf(".")));
			} else if ("RMZIP".equals(programFiles.getFilecode())) {
				programFiles.setFilename(programFiles.getProgfileid() + "/");
			}
		}
		
		/**
		 * 读取序列设置ContentId的值
		 */
		if (null == programFiles.getContentId()) {
			programFiles.setContentId(this.sequenceManager.getContentId());
		}
		
		CmsResultDto cmsResultDto = this.programInfoModuleManager.savePrograminfoProgramfiles(
				operatType, programInfo, programFiles, inputManId);
		if (0 == cmsResultDto.getResultCode()) {
			return (Object[]) cmsResultDto.getResultObject();
		} else {
			throw new Exception(cmsResultDto.getErrorMessage());
		}
	}
	
	/**
	 * 节目导入判断文件是否存在, 支持ts文件和文件夹
	 * @param path 
	 * @return
	 */
	public CmsResultDto isExist(String path) throws Exception {
		CmsResultDto cmsResultDto = new CmsResultDto();
		int progInputPathMaxLen  = Integer.valueOf(
				new CmsConfig().getPropertyByName("ProgInputPathMaxLen"));
		String fileCode;
		/**
		 * 修改节目录入最大长度为可配置
		 * HuangBo update by 2012年2月19日 20时29分
		 */
		if(progInputPathMaxLen < path.length()) {
			cmsResultDto.setResultCode(1L);
			cmsResultDto.setErrorMessage(" 系统不支持文件名长度超过[ " + progInputPathMaxLen + " ]! ");
			return cmsResultDto;
		}
		
		path = path.startsWith("/") ? path.substring(1) : path;
		if (path.endsWith("/")) {
			fileCode = "RMZIP";
		} else {
			fileCode = "H264";
		}
		List<String> supportExtensions = Arrays.asList(
				this.configDao.getValueById("SupportExtensions").toLowerCase().split("[,;|]"));
		return this.programInfoModuleManager.isExist(path, fileCode, supportExtensions);
	}
	
	public void upload(String operatType, ProgramInfo programInfo, 
			ProgramFiles programFiles, String sourcePartPath, 
			String filePath, String inputManId) throws Exception {
		if ("2".equals(operatType)) {
			return;
		}
		
		CmsResultDto cmsResultDto = this.programInfoModuleManager.saveMigrationTaskForPrograminfo(
				programInfo, programFiles, sourcePartPath, 
				"", filePath, "");
		
		if (0 != cmsResultDto.getResultCode()) {
			throw new Exception(cmsResultDto.getErrorMessage());
		}
	}
	
	public List<Object[]> getStorageDirAndProgId(Object prog, 
			String storageClassCode, String fileCode) {
		// 返回：Object[]
		// AmsStorage.stglobalid
		// AmsStorage.storagename
		// AmsStorage.storageip
		// AmsStorage.storageaccstype
		// AmsStorage.loginname
		// AmsStorage.loginpwd
		// AmsStorage.mappath
		// AmsStorage.maploginuid
		// AmsStorage.maploginpwd
		// AmsStorage.maplogindisk
		// AmsStorage.totalcap
		// AmsStorage.districtid
		// AmsStorage.storagevalid
		// AmsStorageDir.storagedirname
		// AmsStorageDir.dirtotalcap
		// AmsStorageDir.diralarmcap
		// AmsStorageDir.dirfreecap
		Object[] storage = this.amsStorageDirManager
				.findstorageanddirlistbystorageclass(storageClassCode, fileCode);

		// 返回：Object[]
		// programid
		// strMaxPK
		Object[] idlist = this.programInfoManager.getProgAndFilesId(prog);

		List<Object[]> filelist = new ArrayList<Object[]>();
		filelist.add(storage);
		filelist.add(idlist);
		return filelist;
	}
	
	/**
	 * 修改原始文件名, 以及文件大小
	 * @param operatType
	 * @param programInfo
	 * @param programFiles
	 * @param inputManId
	 * @return
	 * @throws Exception
	 */
	public Object[] complementarityFile(String operatType, ProgramInfo programInfo, 
			ProgramFiles programFiles, String inputManId) throws Exception {
//		CmsResultDto cmsResultDto = this.programInfoModuleManager.isExist(
//				programFiles.getProcessinstid(), programFiles.getFilecode());
//		if (0 == cmsResultDto.getResultCode()) {
//			Object[] objects = (Object[]) cmsResultDto.getResultObject();
//			if (!(Boolean) objects[0]) {
//				throw new Exception(" 文件不存在, 请检查路径是否正确! ");
//			}
			
		CmsResultDto cmsResultDto = this.programInfoModuleManager.updateProgramFileName(
				programFiles.getProgfileid(), programFiles.getProcessinstid(), 
				programFiles.getContentfilesize());
		if (0 == cmsResultDto.getResultCode()) {
			return new Object[] {programInfo, cmsResultDto.getResultObject()};
		}
//		} 
		throw new Exception(cmsResultDto.getErrorMessage());
	} 
	
	/**
	 * 获取IPPV的值
	 * @return
	 */
	public String getIppv() {
		return this.sequenceManager.getIppvId();
	}
	
	/**
	 * 根据节目状态编号返回节目状态名称
	 * @param dsflag 节目状态编号
	 * @return 节目状态名称
	 */
	public static String getDsflagName(int dsflag) {
		String dsflagName;
		switch (dsflag) {
		case -1:
			dsflagName = "未导入";
			break;
		case 0:
			dsflagName = "迁移中";
			break;
		case 1:
			dsflagName = "迁移失败";
			break;
		case 2:
			dsflagName = "已入缓存库";
			break;
		default:
			dsflagName = "未知";
			break;
		}
		return dsflagName;
	}
	
	public void test(String path) throws Exception {
//		CmsResultDto cmsResultDto = this.isExist(path);
//		Object[] objects = (Object[]) cmsResultDto.getResultObject();
//		System.out.println(objects[0]);
//		System.out.println(objects[1]);
//		System.out.println(objects[2]);
		
		List<Object[]> list = this.queryProgramInfos("", "", "", "", "", "");
		for (Object[] objects : list) {
			System.out.println(objects[0] + " : " + objects[1]);
		}
	}
}
