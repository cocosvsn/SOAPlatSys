package com.soaplat.cmsmgr.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.InputSource;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import cn.sh.sbl.cms.listener.ServerConfigListener;

import com.cbs.cbsmgr.manageriface.IPpSrvPdtRelManager;
import com.soaplat.amsmgr.bean.AmsStorage;
import com.soaplat.amsmgr.bean.AmsStorageClass;
import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.amsmgr.manageriface.IAmsStoragePrgRelManager;
import com.soaplat.cmsmgr.bean.Bpmc;
import com.soaplat.cmsmgr.bean.CmsSite;
import com.soaplat.cmsmgr.bean.PPColumnRel;
import com.soaplat.cmsmgr.bean.PackStyle;
import com.soaplat.cmsmgr.bean.PackStyleFileType;
import com.soaplat.cmsmgr.bean.PackageFiles;
import com.soaplat.cmsmgr.bean.PortalColumn;
import com.soaplat.cmsmgr.bean.ProgListDetail;
import com.soaplat.cmsmgr.bean.ProgListMang;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.cmsmgr.bean.ProgType;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.cmsmgr.common.CmsConfig;
import com.soaplat.cmsmgr.common.FileOperationImpl;
import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.dto.PackageProductVo;
import com.soaplat.cmsmgr.dto.ProgramCategoryDto;
import com.soaplat.cmsmgr.dto.ProgramTypeDto;
import com.soaplat.cmsmgr.manageriface.IBpmcManager;
import com.soaplat.cmsmgr.manageriface.ICmsServiceManager;
import com.soaplat.cmsmgr.manageriface.ICmsSiteManager;
import com.soaplat.cmsmgr.manageriface.ICmsTransactionManager;
import com.soaplat.cmsmgr.manageriface.IFlowActionManager;
import com.soaplat.cmsmgr.manageriface.IPPColumnRelManager;
import com.soaplat.cmsmgr.manageriface.IPackStyleFileTypeManager;
import com.soaplat.cmsmgr.manageriface.IPackStyleManager;
import com.soaplat.cmsmgr.manageriface.IPackageFilesManager;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;
import com.soaplat.cmsmgr.manageriface.IProgListDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListFileDao;
import com.soaplat.cmsmgr.manageriface.IProgListMangDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgListMangManager;
import com.soaplat.cmsmgr.manageriface.IProgPPRelManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;
import com.soaplat.cmsmgr.manageriface.IProgSrvRelManager;
import com.soaplat.cmsmgr.manageriface.IProgTypeManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.soaplat.cmsmgr.manageriface.ISrvColumnManager;
import com.soaplat.cmsmgr.manageriface.ISrvProductManager;
import com.soaplat.cmsmgr.util.DateUtil;
import com.soaplat.cmsmgr.util.ListUtil;
import com.soaplat.sysmgr.bean.Dict;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.manageriface.IDictManager;
import org.apache.log4j.Logger;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;

/**
 * 
 * @author Bunco
 *
 */
public class ProgPackageServiceImpl implements ProgPackageServiceIface {
	private static final String FINISHPROG = "FU00000078";
	private static final String ONLINECLASSCODE = "Online";
	
	private static FileOperationImpl fileopr = null;
	private IGetPK getcmspk;
	private CmsConfig cmsConfig = null;
	private IProgTypeManager progTypeManager = null;
	private IPackStyleManager packStyleManager = null;
	private ICmsServiceManager cmsServiceManager = null;
	private IDictManager dictManager = null;
	private IProgPackageManager progPackageManager = null;
	private IProgPPRelManager progPPRelManager = null;
	private IPackageFilesManager packageFilesManager = null;
	private IProgSrvRelManager progSrvRelManager = null;
	private ISrvColumnManager srvColumnManager = null;
	private ISrvProductManager srvProductManager = null;
	private IPpSrvPdtRelManager ppSrvPdtRelManager = null;
	private IPPColumnRelManager pPColumnRelManager = null;
	private IPackStyleFileTypeManager packStyleFileTypeManager = null;
	private IProgramFilesManager programFilesManager = null;
	private IAmsStoragePrgRelManager amsstorageprgrelManager = null;
	private IAmsStorageDirManager amsstoragedirManager = null;
	private IProgListDetailManager progListDetailManager = null;
	private IProgramInfoManager programinfoManager = null;
	private IProgListMangManager progListMangManager;
	private IProgListMangDetailManager progListMangDetailManager;
	private IFlowActionManager flowActionManager;
	private IProgListFileDao progListFileDao;
	private IBpmcManager bpmcManager;
	private ICmsSiteManager cmsSiteManager;
	private IProductInfoManager productInfoManager;
	
	private ICmsTransactionManager cmsTransactionManager = null;
	
	public static final Logger cmsLog = Logger.getLogger("Cms");
	
	public ProgPackageServiceImpl() {
		fileopr = new FileOperationImpl();
		
		progTypeManager = (IProgTypeManager)ApplicationContextHolder.webApplicationContext.getBean("progTypeManager");
		packStyleManager = (IPackStyleManager)ApplicationContextHolder.webApplicationContext.getBean("packStyleManager");
		cmsServiceManager = (ICmsServiceManager)ApplicationContextHolder.webApplicationContext.getBean("cmsServiceManager");
		dictManager = (IDictManager)ApplicationContextHolder.webApplicationContext.getBean("dictManager");
		progPackageManager = (IProgPackageManager)ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
		progPPRelManager = (IProgPPRelManager)ApplicationContextHolder.webApplicationContext.getBean("progPPRelManager");
		packageFilesManager = (IPackageFilesManager)ApplicationContextHolder.webApplicationContext.getBean("packageFilesManager");
		progSrvRelManager = (IProgSrvRelManager)ApplicationContextHolder.webApplicationContext.getBean("progSrvRelManager");
		srvColumnManager = (ISrvColumnManager)ApplicationContextHolder.webApplicationContext.getBean("srvColumnManager");
		srvProductManager = (ISrvProductManager)ApplicationContextHolder.webApplicationContext.getBean("srvProductManager");
		ppSrvPdtRelManager = (IPpSrvPdtRelManager)ApplicationContextHolder.webApplicationContext.getBean("ppSrvPdtRelManager");
		pPColumnRelManager = (IPPColumnRelManager)ApplicationContextHolder.webApplicationContext.getBean("pPColumnRelManager");
		packStyleFileTypeManager = (IPackStyleFileTypeManager)ApplicationContextHolder.webApplicationContext.getBean("packStyleFileTypeManager");
		programFilesManager = (IProgramFilesManager)ApplicationContextHolder.webApplicationContext.getBean("programFilesManager");
		amsstorageprgrelManager = (IAmsStoragePrgRelManager)ApplicationContextHolder.webApplicationContext.getBean("amsstorageprgrelManager");
		amsstoragedirManager = (IAmsStorageDirManager)ApplicationContextHolder.webApplicationContext.getBean("amsstoragedirManager");
		progListDetailManager = (IProgListDetailManager)ApplicationContextHolder.webApplicationContext.getBean("progListDetailManager");
		programinfoManager = (IProgramInfoManager)ApplicationContextHolder.webApplicationContext.getBean("programinfoManager");
		progListMangManager = (IProgListMangManager) ApplicationContextHolder.webApplicationContext.getBean("progListMangManager");
		progListMangDetailManager = (IProgListMangDetailManager) ApplicationContextHolder.webApplicationContext.getBean("progListMangDetailManager");
		flowActionManager = (IFlowActionManager) ApplicationContextHolder.webApplicationContext.getBean("flowActionManager");
		progListFileDao = (IProgListFileDao) ApplicationContextHolder.webApplicationContext.getBean("progListFileDao");
		
		this.productInfoManager = (IProductInfoManager) ApplicationContextHolder.webApplicationContext.getBean("productinfoManager");
		this.bpmcManager = (IBpmcManager) ApplicationContextHolder.webApplicationContext.getBean("bpmcManager");
		this.cmsSiteManager = (ICmsSiteManager) ApplicationContextHolder.webApplicationContext.getBean("cmsSiteManager");
		this.getcmspk = (IGetPK) ApplicationContextHolder.webApplicationContext.getBean("getcmspk");
		cmsTransactionManager = (ICmsTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("cmsTransactionManager");
		cmsConfig = new CmsConfig();
	}
	
	
	// 获取节目类型列表，包含样式列表
	@SuppressWarnings("unchecked")
	public List<ProgramTypeDto> getAllProgramTypes(Long styletype) {
		cmsLog.info("Cms -> ProgPackageServiceImpl -> getAllProgramTypes...");
		List<ProgramTypeDto> programTypeDtos = new ArrayList<ProgramTypeDto>();

		try {
			List<ProgType> progTypes = (List<ProgType>) progTypeManager
					.findAll();
			for (int i = progTypes.size() - 1; i >= 0; i--) {
				ProgramTypeDto programTypeDto = new ProgramTypeDto();
				ProgType progType = (ProgType) progTypes.get(i);

				programTypeDto.setProgtypeid(progType.getProgtypeid());
				programTypeDto.setClassname(progType.getClassname());
				programTypeDto.setProperty(progType.getProperty());

				// 获取样式列表
				List<PackStyle> packStyles = (List<PackStyle>) packStyleManager
						.getPackStylesByProgTypeAndStyleType(
								progType.getProgtypeid(), styletype);
				programTypeDto.setPackStyles(packStyles);

				List<String> packStyleXmls = new ArrayList<String>();
				for (int j = 0; j < packStyles.size(); j++) {
					String str = "";
					try {
						// str = new String(
						// packStyles.get(j).getStylexml().getBytes((long)1,(int)(packStyles.get(j).getStylexml().length())),
						// "gb2312"
						// );
						str = packStyles.get(j).getStylexml();
					} catch (Exception e) {
						e.printStackTrace();
					}
					packStyleXmls.add(str);
				}
				programTypeDto.setPackStyleXmls(packStyleXmls);
				programTypeDtos.add(programTypeDto);
			}
		} catch (Exception e) {
			cmsLog.info("Cms -> ProgPackageServiceImpl -> getAllProgramTypes，异常："
					+ e.getMessage());
		}
		cmsLog.info("Cms -> ProgPackageServiceImpl -> getAllProgramTypes returns.");
		return programTypeDtos;
	}

	// 获取节目分类列表，包含服务列表
	@SuppressWarnings("unchecked")
	public List getAllProgramCategories() {
		List programCategoryDtos = new ArrayList();
		List programCategories = dictManager.findByProperty("dictid", "JBFL");

		for (int i = 0; i < programCategories.size(); i++) {
			ProgramCategoryDto programCategoryDto = new ProgramCategoryDto();
			Dict dict = (Dict) programCategories.get(i);

			programCategoryDto.setDicGlobalId(dict.getDicglobalid());
			programCategoryDto.setDictCode(dict.getDictcode());
			programCategoryDto.setDictName(dict.getDictname());

			// 获取服务列表
			List cmsServices = cmsServiceManager
					.getCmsServicesByProgramCategoryId(dict.getDictcode());
			programCategoryDto.setCmsServices(cmsServices);

			programCategoryDtos.add(programCategoryDto);
		}

		return programCategoryDtos;
	}

	// 获取文件列表，根据节目列表
	@SuppressWarnings("rawtypes" )
	public List getProgramFilesByProgramInfos(List programInfos,
			PackStyle packStyle) {
		return getProgramFilesByProgramInfosAndStyleId(programInfos,
				packStyle.getStyleid());
	}

	// 获取文件列表，根据节目列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getProgramFilesByProgramInfosAndStyleId(List programInfos,
			Long styleId) {
		List programFiles = new ArrayList();

		// 获取样式对应的文件类型列表
		List fileTypes = packStyleFileTypeManager.findByProperty("styleid",
				styleId);

		for (int i = 0; i < programInfos.size(); i++) {
			ProgramInfo programInfo = (ProgramInfo) programInfos.get(i);
			List sameProgFiles = new ArrayList();

			// 根据样式和节目编号，查询节目文件
			for (int j = 0; j < fileTypes.size(); j++) {
				PackStyleFileType packStyleFileType = (PackStyleFileType) fileTypes
						.get(j);
				List files = programFilesManager.findbyprogramidfiletype(
						programInfo.getProgramid(),
						packStyleFileType.getFiletypeid());

				for (int k = 0; k < files.size(); k++) {
					sameProgFiles.add(files.get(k));
				}
			}
			programFiles.add(sameProgFiles);
		}

		return programFiles;
	}

	@SuppressWarnings("unused")
	private String getStringByClob(Clob clob) {
		String str = null;
		if (clob == null) {
			return "";
		}
		try {
			String strBuff;
			Reader reader = clob.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();

			strBuff = br.readLine();
			while (strBuff != null) {
				// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
				sb.append(strBuff);
				strBuff = br.readLine();
			}
			str = sb.toString();

			str = clob.getSubString(1, (int) clob.length());
		} catch (IOException ex) {
			cmsLog.info("Failed to get string by clob.");
			cmsLog.info(ex.getMessage());
		} catch (SQLException ex) {
			cmsLog.info("Failed to get string by clob..");
			cmsLog.info(ex.getMessage());
		}
		return str;
	}

	@SuppressWarnings("unused")
	private int copyFileLocalToSmb(String strFileFrom, String strFileTo) {
		cmsLog.info("Cms -> ProgPackageServiceImpl -> copyFile...");
		cmsLog.info("From : " + strFileFrom);
		cmsLog.info("To : " + strFileTo);
		int ret = -1;

		FileOperationImpl fileopr = new FileOperationImpl();

		ret = fileopr.copyFileFromLocalToSmb(strFileFrom, strFileTo);

		cmsLog.info("Cms -> ProgPackageServiceImpl -> copyFile returns.");
		return ret;
	}

	@SuppressWarnings("unused")
	private int copyFileSmbToLocal(String strFileFrom, String strFileTo) {
		cmsLog.info("Cms -> ProgPackageServiceImpl -> copyFileSmbToLocal...");
		cmsLog.info("From : " + strFileFrom);
		cmsLog.info("To : " + strFileTo);
		int ret = -1;

		try {
			int last = 0;
			Long localreadbytes = Long.valueOf(0);
			byte[] bytes = new byte[1024];

			SmbFile fileFrom = new SmbFile(strFileFrom); // 源文件
			File fileTo = new File(strFileTo); // 目标文件

			FileOutputStream fileStreamOut = new FileOutputStream(fileTo, true); // 输出文件
			SmbFileInputStream fileStreamIn = new SmbFileInputStream(fileFrom); // 输入文件

			while ((last = fileStreamIn.read(bytes)) != -1) {
				fileStreamOut.write(bytes, 0, last);
				localreadbytes += last; // 已传输字节数
			}
			fileStreamOut.flush();

			ret = 0;
			cmsLog.info("Copy file successfully : " + strFileFrom + " --> "
					+ strFileTo);
		} catch (IOException ex) {
			ret = 1;
			cmsLog.info("Copy file unsuccessfully : " + strFileFrom + " --> "
					+ strFileTo);
			cmsLog.info(ex.getMessage());
		}

		cmsLog.info("Cms -> ProgPackageServiceImpl -> copyFileSmbToLocal returns.");
		return ret;
	}

	private int deleteSmbFile(String strFile) {
		// 删除文件
		cmsLog.info("Cms -> ProgPackageServiceImpl -> deleteSmbFile...");
		int ireturn = -1;
		FileOperationImpl fileopr = new FileOperationImpl();

		ireturn = fileopr.deleteSmbFile(strFile);

		cmsLog.info("Cms -> ProgPackageServiceImpl -> deleteSmbFile returns.");
		return ireturn;
	}

	@SuppressWarnings("unused")
	private int deleteFile(String strFile) {
		// 删除文件
		cmsLog.info("Cms -> ProgPackageServiceImpl -> deleteFile...");
		int ireturn = -1;
		try {
			File file = new File(strFile);
			file.delete();

			ireturn = 0;
			cmsLog.info("Delete file successfully: " + strFile);
		} catch (Exception ex) {
			ireturn = 1;
			cmsLog.info("Delete file unsuccessfully: " + strFile);
			cmsLog.info(ex.getMessage());
		}

		cmsLog.info("Cms -> ProgPackageServiceImpl -> deleteFile returns.");
		return ireturn;
	}

	// 修改节目包的xml
	@SuppressWarnings("unchecked")
	private ProgPackage modifyProgPackagePpxml(ProgPackage progPackage) {
		// 删除文件
		cmsLog.info("Cms -> ProgPackageServiceImpl -> modifyProgPackagePpxml...");

		List packageFileses = packageFilesManager.findByProperty("productid",
				progPackage.getProductid());
		String xml = progPackage.getPpxml().replaceAll("\u00A0", "\u0020");
		cmsLog.debug("==================================================\n\t得到的xml内容为: "
				+ xml);

		try {
			// 读配置文件
			String sourceFileStclasscode = "NearOnline";

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			// 将String转成parse可以识别的InputSource
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			Document doc = builder.parse(is);
			doc.normalize();

			// 查找cell节点
			cmsLog.info("修改节目包节点(APP)属性...");
			NodeList cells = doc.getElementsByTagName("APP");
			for (int i = 0; i < cells.getLength(); i++) {
				Node cell = cells.item(i);
				Element cellattr = (Element) cells.item(i);
				if (cellattr.hasAttribute("PROGPACKAGEID")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("PROGPACKAGEID").getNodeValue());
					cell.getAttributes().getNamedItem("PROGPACKAGEID")
							.setNodeValue(progPackage.getProductid());
				}
				if (cellattr.hasAttribute("PROGPACKAGENAME")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("PROGPACKAGENAME").getNodeValue());
					cell.getAttributes().getNamedItem("PROGPACKAGENAME")
							.setNodeValue(progPackage.getProductname());
				}
				if (cellattr.hasAttribute("PROGTYPE")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("PROGTYPE").getNodeValue());
					cell.getAttributes().getNamedItem("PROGTYPE")
							.setNodeValue(progPackage.getProgtype());
				}
				if (cellattr.hasAttribute("STYLEID")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("STYLEID").getNodeValue());
					cell.getAttributes().getNamedItem("STYLEID")
							.setNodeValue(progPackage.getStyleid().toString());
				}
				if (cellattr.hasAttribute("SUMFILESIZE")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("SUMFILESIZE").getNodeValue());
					cell.getAttributes().getNamedItem("SUMFILESIZE")
							.setNodeValue(progPackage.getSumfilesize());
				}
				if (cellattr.hasAttribute("UPDATEMANID")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("UPDATEMANID").getNodeValue());
					cell.getAttributes().getNamedItem("UPDATEMANID")
							.setNodeValue(progPackage.getUpdatemanid());
				}
				if (cellattr.hasAttribute("UPDATETIME")) // 判断节点有tag属性
				{
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					// cmsLog.info(cell.getAttributes().getNamedItem("UPDATETIME").getNodeValue());
					cell.getAttributes()
							.getNamedItem("UPDATETIME")
							.setNodeValue(
									sdf.format(progPackage.getUpdatetime()));
				}
				if (cellattr.hasAttribute("EPICODENUMBER")) // 判断节点有tag属性
				{
					// cmsLog.info(cell.getAttributes().getNamedItem("EPICODENUMBER").getNodeValue());
					cell.getAttributes().getNamedItem("EPICODENUMBER")
							.setNodeValue(progPackage.getEpicodenumber());
				}
				cellattr.setAttribute("PROGPACKAGEID",
						progPackage.getProductid());
				cellattr.setAttribute("PROGPACKAGENAME",
						progPackage.getProductname());
				cellattr.setAttribute("PROGTYPE", progPackage.getProgtype());
				cellattr.setAttribute("STYLEID", progPackage.getStyleid()
						.toString());
				cellattr.setAttribute("SUMFILESIZE",
						progPackage.getSumfilesize());
				cellattr.setAttribute("UPDATEMANID",
						progPackage.getUpdatemanid());
				cellattr.setAttribute("UPDATETIME", fileopr
						.convertDateToString(progPackage.getUpdatetime(),
								"yyyy-MM-dd HH:mm:ss"));
				cellattr.setAttribute("EPICODENUMBER",
						progPackage.getEpicodenumber());

				cellattr.setAttribute("PTGLOBALID", progPackage.getPtglobalid());
				cellattr.setAttribute("PRODUCTNAME",
						progPackage.getProductname());
				cellattr.setAttribute("DESCRIPTION",
						progPackage.getDescription());
				cellattr.setAttribute("CATEGORY", progPackage.getCategory());
				cellattr.setAttribute("TITLEBRIEF", progPackage.getTitlebrief());
				cellattr.setAttribute("EPICODEID", progPackage.getEpicodeid());
				cellattr.setAttribute("LENGTHTC", progPackage.getLengthtc());
				cellattr.setAttribute("EPICODENAME",
						progPackage.getEpicodename());
				cellattr.setAttribute("SUBTITLELANGUAGE",
						progPackage.getSubtitlelanguage());
				cellattr.setAttribute("AUDIOLANGUAGE",
						progPackage.getAudiolanguage());
				cellattr.setAttribute("DIRECTOR", progPackage.getDirector());
				cellattr.setAttribute("ACTORS", progPackage.getActors());
				cellattr.setAttribute("SHOOTDATE", progPackage.getShootdate());
				cellattr.setAttribute("ISSUEDATE", progPackage.getIssuedate());
				cellattr.setAttribute("COUNTRYDIST",
						progPackage.getCountrydist());
				cellattr.setAttribute("SUBSCRIBERSTIME", fileopr
						.convertDateToString(progPackage.getSubscriberstime(),
								"yyyy-MM-dd HH:mm:ss"));
				cellattr.setAttribute("SUBSCRIBERETIME", fileopr
						.convertDateToString(progPackage.getSubscriberetime(),
								"yyyy-MM-dd HH:mm:ss"));
				cellattr.setAttribute("INPUTMANID", progPackage.getInputmanid());
				cellattr.setAttribute("INPUTTIME", fileopr.convertDateToString(
						progPackage.getInputtime(), "yyyy-MM-dd HH:mm:ss"));
				cellattr.setAttribute("FILESIZEHI", progPackage.getFilesizehi()
						.toString());
				cellattr.setAttribute("FILESIZELOW", progPackage
						.getFilesizelow().toString());
				cellattr.setAttribute("SUMFILESIZE",
						progPackage.getSumfilesize());
				cellattr.setAttribute("REMARK", progPackage.getRemark());
				cellattr.setAttribute("STATE", progPackage.getState()
						.toString());
				cellattr.setAttribute("DEALSTATE", progPackage.getDealstate()
						.toString());
			}

			cmsLog.info("修改节目包的文件信息(PROGFILE)...");
			cells = doc.getElementsByTagName("PROGFILE");
			for (int i = 0; i < cells.getLength(); i++) {
				Node cell = cells.item(i);
				Element cellattr = (Element) cells.item(i);

				// 先删除原有节点
				cmsLog.info("为节目包删除原有文件节点...");
				if (cell.hasChildNodes()) {
					NodeList oleNodes = cell.getChildNodes();
					for (int j = 0; j < oleNodes.getLength(); j++) {
						Node oldNode = oleNodes.item(j);

						if (oldNode.getNodeName().equalsIgnoreCase("FILE")) {
							cell.removeChild(oldNode);
						}
					}
				}
				cmsLog.info("为节目包添加文件节点...");
				for (int j = 0; j < packageFileses.size(); j++) {
					PackageFiles pf = (PackageFiles) packageFileses.get(j);
					ProgramFiles programFiles = (ProgramFiles) programFilesManager
							.getById(pf.getProgfileid());

					// 创建一个值
					// Text createtext = doc.createTextNode("FILE");

					Element newe = doc.createElement("FILE");

					newe.setAttribute("PROGFILEID", pf.getProgfileid());
					newe.setAttribute("FILETYPEID",
							programFiles.getFiletypeid());
					newe.setAttribute("FILECODE", programFiles.getFilecode());
					newe.setAttribute("FILENAME", programFiles.getFilename());

					// newe.setAttribute("RUNTIME",
					// fileopr.convertDateToString(programFiles.getRuntime(),
					// "yyyy-MM-dd HH:mm:ss"));
					// newe.setAttribute("FILEDES", programFiles.getFiledes());
					// newe.setAttribute("FIGLOBALID",
					// programFiles.getFiglobalid());
					// newe.setAttribute("AUDIOTYPE",
					// programFiles.getAudiotype());
					// newe.setAttribute("SCREEFORMAT",
					// programFiles.getScreeformat());
					// newe.setAttribute("HDCONTENT",
					// programFiles.getHdcontent());
					newe.setAttribute("SUBTITLELANGUAGE",
							programFiles.getSubtitlelanguage());
					newe.setAttribute("DUBBEDLANGUAGE",
							programFiles.getDubbedlanguage());
					newe.setAttribute("FILESIZEHI", programFiles
							.getFilesizehi().toString());
					newe.setAttribute("FILESIZELOW", programFiles
							.getFilesizelow().toString());
					newe.setAttribute("CONTENTFILESIZE",
							programFiles.getContentfilesize());
					newe.setAttribute("CONTENTCHECKSUM",
							programFiles.getContentchecksum());
					// newe.setAttribute("IMAGERATIO",
					// programFiles.getImageratio());
					// newe.setAttribute("ENCRYPTION",
					// programFiles.getEncryption());
					// newe.setAttribute("COPYPROTECTION",
					// programFiles.getCopyprotection());
					// newe.setAttribute("DELMANID",
					// programFiles.getDelmanid());
					// newe.setAttribute("DELDATE",
					// fileopr.convertDateToString(programFiles.getDeldate(),
					// "yyyy-MM-dd HH:mm:ss"));
					// newe.setAttribute("PROGMANAGER",
					// programFiles.getProgmanager());
					// newe.setAttribute("PROGBAK", programFiles.getProgbak());
					// if(programFiles.getProgbkmonth() != null)
					// newe.setAttribute("PROGBKMONTH",
					// programFiles.getProgbkmonth().toString());
					// else
					// newe.setAttribute("PROGBKMONTH", "");
					if (programFiles.getProgrank() != null)
						newe.setAttribute("PROGRANK", programFiles
								.getProgrank().toString());
					else
						newe.setAttribute("PROGRANK", "0");
					// newe.setAttribute("INSTORAGEMANID",
					// programFiles.getInstoragemanid());
					// newe.setAttribute("INSTORAGEDATE",
					// fileopr.convertDateToString(programFiles.getInstoragedate(),
					// "yyyy-MM-dd HH:mm:ss"));
					// newe.setAttribute("NEARFILEPATH",
					// programFiles.getNearfilepath());
					// newe.setAttribute("NEARSTATUS",
					// programFiles.getNearstatus().toString());
					// newe.setAttribute("OFFLINESTATUS",
					// programFiles.getOfflinestatus().toString());
					// newe.setAttribute("ONAIRSTATUS",
					// programFiles.getOnairstatus().toString());
					// newe.setAttribute("EXT1",
					// programFiles.getExt1().toString());
					// newe.setAttribute("EXT2",
					// programFiles.getExt2().toString());
					// newe.setAttribute("ASSESSSUGT",
					// programFiles.getAssesssugt());
					// newe.setAttribute("PLANTIME",
					// fileopr.convertDateToString(programFiles.getPlantime(),
					// "yyyy-MM-dd HH:mm:ss"));
					// newe.setAttribute("OPMANID", programFiles.getOpmanid());
					// newe.setAttribute("PROGSTATUS",
					// programFiles.getProgstatus().toString());
					// newe.setAttribute("ENCODESTATUS",
					// programFiles.getEncodestatus().toString());
					// newe.setAttribute("INDEXSTATUS",
					// programFiles.getIndexstatus().toString());
					// newe.setAttribute("PROCESSINSTID",
					// programFiles.getProcessinstid());
					// newe.setAttribute("ACTIVITYINSTID",
					// programFiles.getActivityinstid());
					// newe.setAttribute("ACTIVITYINSTNAME",
					// programFiles.getActivityinstname());
					/**
					 * HuangBo update by 2011年4月13日 14时28分
					 * 增加文件属性的ContentID属性
					 */
					newe.setAttribute("CONTENTID",
							programFiles.getContentId());
					newe.setAttribute("INPUTMANID",
							programFiles.getInputmanid());
					newe.setAttribute("INPUTTIME", fileopr.convertDateToString(
							programFiles.getInputtime(), "yyyy-MM-dd HH:mm:ss"));
					// // newe.setAttribute("UPDATEMANID", programFiles);
					// // newe.setAttribute("UPDATETIME",
					// fileopr.convertDateToString(programFiles,
					// "yyyy-MM-dd HH:mm:ss"));
					// newe.setAttribute("REMARK", programFiles.getRemark());

					// 从文件位置表获取
					// 返回：List
					// 1 - String
					// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
					// 2 - List<Object[]>
					// (ProgramFiles)Object[0]
					// (AmsStorage)Object[1]
					// (AmsStoragePrgRel)Object[2]
					// (AmsStorageDir)Object[3]
					// (AmsStorageClass)Object[3]
					cmsLog.info("查询文件的filedate...");
					String filedate = "";
					List sourcePaths = packageFilesManager
							.getSourcePathByProgfileidStclasscode(
									programFiles.getProgfileid(),
									sourceFileStclasscode);
					if (sourcePaths != null && sourcePaths.size() >= 2) {
						List list = (List) sourcePaths.get(1);
						if (list.size() > 0) {
							Object[] rows = (Object[]) list.get(0);
							AmsStoragePrgRel amsstpr = (AmsStoragePrgRel) rows[2];

							if (amsstpr.getFiledate() != null) {
								filedate = fileopr.convertDateToString(
										amsstpr.getFiledate(),
										"yyyy-MM-dd HH:mm:ss");
							}
						}
					}
					newe.setAttribute("FILEDATE", filedate);
					cmsLog.info("新的文件FILEDATE：" + filedate);

					// 添加在cell后
					cell.appendChild(newe);
					cmsLog.info("新的文件节点已经添加。");
				}
			}
			String ppxml = XMLtoStr(doc);
			if (!ppxml.equalsIgnoreCase("")) {
				progPackage.setPpxml(ppxml);
				cmsLog.info("已经更新节目包(progPackage)的xml，尚未保存到数据库。");
				/**
				 * 增加节目包所有文件大小的统计
				 * HuangBo addition by 2011年11月17日 11时4分 
				 */
				progPackage.setFilesizehi(this.packageFilesManager.sumOfPackageFileSize(
						progPackage.getProductid()));
			}
		} catch (Exception ex) {
			cmsLog.error(ex.getMessage());
		}

		cmsLog.info("Cms -> ProgPackageServiceImpl -> modifyProgPackagePpxml returns.");
		return progPackage;
	}

	// 20100419 19:41
	// 更新节目包的ppxml字段，同时重新生成节目包的xml文件
	public ProgPackage updateProgPackagePpxmlAndXmlfile(ProgPackage progPackage) {
		cmsLog.info("Cms -> ProgPackageServiceImpl -> updateProgPackagePpxmlAndXmlfile...");

		// 配置文件获取
		String xmlFilecode = "PPXML";
		String stclasscodeNearOnline = "NearOnline";

		ProgPackage pp = (ProgPackage) progPackageManager.getById(progPackage
				.getProductid());
		if (pp != null) {
			cmsLog.info("节目包ID：" + pp.getProductid());
			cmsLog.info("节目包名称：" + pp.getProductname());

			// 查询节目包的xml生成的目标路径
			cmsLog.info("查询节目包的xml生成的目标路径...");
			// 调用方法2，得到xml目前存储路径
			// 返回：List
			// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
			// 2 - List<Object[]>
			// (AmsStorage)Object[0]
			// (AmsStorageDir)Object[1]
			// (AmsStorageClass)Object[2]
			String strXmlFullPath = ""; // xml目标存放全路径，含文件名和后缀
			String strXmlPath = ""; // xml目标存放路径
			String strXmlName = ""; // xml名字
			String filepath = ""; // 节目包ID前10位
			List destpaths = packageFilesManager
					.getDestPathByFilecodeStclasscode(xmlFilecode,
							stclasscodeNearOnline);
			if (destpaths == null || destpaths.size() < 2) {
				cmsLog.info("获取节目包xml目标存放路径失败。");
				return null;
			}
			strXmlPath = (String) destpaths.get(0);
			List rowlist = (List) destpaths.get(1);
			Object[] rows = (Object[]) rowlist.get(0);
			AmsStorage amsst = (AmsStorage) rows[0];
			AmsStorageDir amsstdir = (AmsStorageDir) rows[1];
			AmsStorageClass amsstc = (AmsStorageClass) rows[2];
			strXmlName = progPackage.getProductid() + ".xml";
			filepath = progPackage.getProductid().substring(0, 10);

			// 北京修改，20100204 14:03
			// xml的源文件缺少filepath，但是文件位置表不缺少
			strXmlPath = strXmlPath.replace('\\', '/');
			strXmlPath = fileopr.checkPathFormatRear(strXmlPath, '/');
			strXmlPath += filepath;
			strXmlPath = strXmlPath.replace('\\', '/');
			strXmlPath = fileopr.checkPathFormatRear(strXmlPath, '/');
			// 修改完毕

			// xml文件的目标路径
			strXmlFullPath = strXmlPath + strXmlName;

			pp = modifyProgPackagePpxml(pp);
			progPackageManager.update(pp);
			cmsLog.info("节目包的ppxml字段已经更新。");

			cmsLog.info("准备重新生成节目包的xml文件...");
			cmsLog.info("xml文件目标路径:" + strXmlFullPath);
			if (fileopr.createSmbFile(strXmlFullPath, pp.getPpxml()) != 0) {
				// 写xml文件失败
				// 删除节目包
				cmsLog.warn("写xml文件失败，删除节目包。");
				return null;
			}
		} else {
			cmsLog.warn("节目包不存在。");
			return null;
		}

		cmsLog.info("Cms -> ProgPackageServiceImpl -> updateProgPackagePpxmlAndXmlfile returns.");
		return progPackage;
	}

	// 创建（定义）节目包，输入节目包描述信息、节目类型、样式、节目分类、服务列表、节目列表、文件列表
	public ProgPackage createProgPackage(ProgPackage progPackage, Long styleId,
			List cmsServices, List programs, List progFiles, String ppXml) {
		cmsLog.info("Cms -> ProgPackageServiceImpl -> createProgPackage...");

		// 配置文件，获取
		String xmlFiletype = "XML";
		String xmlFilecode = "PPXML";
		String stclasscodeNearOnline = "NearOnline";

		progPackage.setState(1l);
		progPackage.setDealstate(0l);

		// progPackage.setPpxml(Hibernate.createClob(ppXml));
		progPackage.setPpxml(ppXml);
		// 保存节目包
		progPackage = cmsTransactionManager.saveProgPackage(progPackageManager,
				packStyleFileTypeManager, programFilesManager,
				packageFilesManager, progPPRelManager, pPColumnRelManager,
				srvColumnManager, progSrvRelManager, ppSrvPdtRelManager,
				srvProductManager, progPackage, styleId, cmsServices, programs,
				progFiles);

		// 修改xml中的节目包ID
		progPackage = modifyProgPackagePpxml(progPackage);
		// if(!newPpxml.equalsIgnoreCase(""))
		// {
		// progPackage.setPpxml(newPpxml);
		progPackageManager.update(progPackage);
		cmsLog.info("节目包的ppxml字段已经更新。");
		// }

		// 调用方法2，得到xml目前存储路径
		// 返回：List
		// 1 - String 目标路径() 格式："smb://hc:hc@172.23.19.66/公用/"
		// 2 - List<Object[]>
		// (AmsStorage)Object[0]
		// (AmsStorageDir)Object[1]
		// (AmsStorageClass)Object[2]
		String strXmlFullPath = ""; // xml目标存放全路径，含文件名和后缀
		String strXmlPath = ""; // xml目标存放路径
		String strXmlName = ""; // xml名字
		String filepath = ""; // 节目包ID前10位
		List destpaths = packageFilesManager.getDestPathByFilecodeStclasscode(
				xmlFilecode, stclasscodeNearOnline);
		if (destpaths == null || destpaths.size() < 2) {
			cmsLog.info("获取节目包xml目标存放路径失败。");
			return null;
		}
		strXmlPath = (String) destpaths.get(0);
		List rowlist = (List) destpaths.get(1);
		Object[] rows = (Object[]) rowlist.get(0);
		AmsStorage amsst = (AmsStorage) rows[0];
		AmsStorageDir amsstdir = (AmsStorageDir) rows[1];
		AmsStorageClass amsstc = (AmsStorageClass) rows[2];
		strXmlName = progPackage.getProductid() + ".xml";
		filepath = progPackage.getProductid().substring(0, 10);

		// 北京修改，20100204 14:03
		// xml的源文件缺少filepath，但是文件位置表不缺少
		strXmlPath = strXmlPath.replace('\\', '/');
		strXmlPath = fileopr.checkPathFormatRear(strXmlPath, '/');
		strXmlPath += filepath;
		strXmlPath = strXmlPath.replace('\\', '/');
		strXmlPath = fileopr.checkPathFormatRear(strXmlPath, '/');
		// 修改完毕

		// xml文件的目标路径
		strXmlFullPath = strXmlPath + strXmlName;

		// 生成XML文件，以节目包ID命名
		// 上传XML文件，？？是否上一步直接生成XML文件到上传的目标地址
		cmsLog.info("准备生成节目包的xml文件...xml文件目标路径:" + strXmlFullPath);
		if (fileopr.createSmbFile(strXmlFullPath, ppXml) != 0) {
			// 写xml文件失败
			// 删除节目包
			cmsLog.info("写xml文件失败，删除节目包。");
			deleteProgPackage(progPackage.getProductid());
			return null;
		}

		// 写数据库，到文件表、AMS节目位置表、节目包文件表
		cmsLog.info("准备保存节目包的xml文件记录到数据库...");
		ProgramFiles programFiles = new ProgramFiles();
		// AmsStoragePrgRel amsStoragePrgRel = new AmsStoragePrgRel();
		// PackageFiles packageFiles = new PackageFiles();
		String strMaxPk = programFilesManager
				.findmaxprogramfileidbyprogramidandfiletype(
						progPackage.getProductid(), "010");
		programFiles.setProgfileid(strMaxPk);
		programFiles.setProgramid(progPackage.getProductid());
		programFiles.setFiletypeid(xmlFiletype);
		programFiles.setFilecode(xmlFilecode);
		programFiles.setFilename(strXmlName);
		programFiles.setFilesizehi((long) 0);
		programFiles.setFilesizelow((long) 0);
		programFiles.setContentfilesize("0");
		programFiles.setInputmanid(progPackage.getInputmanid());
		programFiles.setInputtime(new Date());
		// programFiles.setStorageid(stglobalid);
		// programFiles.setStoragedirid(stdirglobalid);
		if (cmsTransactionManager.saveUploadFile(programFilesManager,
				amsstorageprgrelManager, packageFilesManager, programFiles,
				progPackage, amsst, amsstdir, filepath, false) == null) {
			// 失败
			// 删除节目包
			cmsLog.info("saveUploadFile失败，删除节目包。");
			deleteProgPackage(progPackage.getProductid());

			// 删除xml文件
			deleteSmbFile(strXmlFullPath);
			return null;
		}

		ProgPackage pp = updateProgPackagePpxmlAndXmlfile(progPackage);

		if (pp == null) {
			cmsLog.warn("删除节目包记录...");
			deleteProgPackage(progPackage.getProductid());

			cmsLog.warn("删除节目包的xml文件...");
			deleteSmbFile(strXmlFullPath);
		}
		
		Bpmc bpmc = new Bpmc(progPackage.getProductid(), progPackage.getInputmanid(), null, null, 
				null, null, "创建节目包: " + progPackage.getProductname(), "C");
		this.bpmcManager.save(bpmc);

		cmsLog.info("Cms -> ProgPackageServiceImpl -> createProgPackage returns.");
		return progPackage;
	}

	// 修改节目包所有信息，ProgPackage ProgSrvRel ProgPPRel PackageFiles
	public void updateProgPackage(ProgPackage progPackage, List cmsServices,
			List programs, Long styleId) throws Exception {
		
		progPackage = modifyProgPackagePpxml(progPackage);

		cmsTransactionManager.updateProgPackage(progPackageManager,
				progPPRelManager, packageFilesManager,
				packStyleFileTypeManager, programFilesManager,
				progSrvRelManager, pPColumnRelManager, srvColumnManager,
				ppSrvPdtRelManager, srvProductManager, progPackage,
				cmsServices, programs, styleId);

		ProgPackage pp = updateProgPackagePpxmlAndXmlfile(progPackage);

		// ProgPackage pp =
		// (ProgPackage)progPackageManager.getById(progPackage.getProductid());
		// cmsLog.info("查询节目包：" + progPackage.getProductid());
		// pp = modifyProgPackagePpxml(pp);
		// progPackageManager.update(pp);
		// cmsLog.info("修改节目包。");
	}

	// 修改节目包信息：ProgPackage
	public void modifyProgPackage(ProgPackage progPackage, String ppXml) {
		progPackage.setUpdatetime(new Date());
		progPackage.setPpxml(ppXml);
		progPackage = modifyProgPackagePpxml(progPackage);
		progPackageManager.update(progPackage);

		ProgPackage pp = (ProgPackage) progPackageManager.getById(progPackage
				.getProductid());
		cmsLog.info("查询节目包：" + progPackage.getProductid());
		pp = modifyProgPackagePpxml(pp);
		progPackageManager.update(pp);
		cmsLog.info("修改节目包。");
	}

	// 修改节目包服务关系：ProgSrvRel
	public void modifyProgSrvRel(List cmsServices, ProgPackage progPackage) {
		cmsTransactionManager.saveProgSrvRelColumnRelProductCategoryRel(
				progPackageManager, progSrvRelManager, pPColumnRelManager,
				srvColumnManager, ppSrvPdtRelManager, srvProductManager,
				cmsServices, progPackage);
	}

	// 修改节目包节目、节目包文件关系：ProgPPRel、PackageFiles
	public void modifyProgPPRelAndPackageFiles(List programs,
			ProgPackage progPackage, Long styleId) {
		cmsTransactionManager.updateProgPPRelAndPackageFiles(progPPRelManager,
				packageFilesManager, packStyleFileTypeManager,
				programFilesManager, programs, progPackage, styleId);
	}

	// 根据节目包编号 productId ，删除节目包，
	// 同时删除 产品节目包关系：PpSrvPdtRel
	// 删除 栏目节目包关系：PPColumnRel
	// 删除 服务节目包关系：ProgSrvRel
	// 删除 节目包文件关系：PackageFiles
	// 删除 节目包节目关系：ProgPPRel
	public CmsResultDto deleteProgPackage(String productId) {
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> deleteProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		List<String> progSize = this.progListDetailManager.queryScheduleDateByProgPackageId(productId);
		if (0 < progSize.size()) {
			cmsResultDto.setResultCode(1L);
			cmsResultDto.setErrorMessage("节目包存在于编单中, 不允许删除!");
			return cmsResultDto;
		}
		
		cmsResultDto = cmsTransactionManager.deleteProgPackage(
				progPackageManager, ppSrvPdtRelManager, pPColumnRelManager,
				progSrvRelManager, packageFilesManager, progPPRelManager,
				productId);

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> deleteProgPackage returns.");
		return cmsResultDto;
	}

	// 查询节目包
	public CmsResultDto getProgPackage(String productId) {
		// 返回：List
		// 1 - ProgPackage progPackages
		// 2 - String ppxml
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> getProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		List list = new ArrayList();

		cmsLog.debug("查询节目包，输入节目包ID：" + productId);
		ProgPackage progPackage = (ProgPackage) progPackageManager
				.getById(productId);

		if (progPackage != null) {
			cmsLog.debug("查询到节目包，节目包名称：" + progPackage.getProductname());
			// cmsLog.warn("准备修改节目包的ppxml...这里是为了修正早期节目包ppxml字段内容不完整情况。");
			// progPackage = modifyProgPackagePpxml(progPackage);
			// progPackageManager.update(progPackage);
			// cmsLog.debug("节目包的ppxml已经修改。");

			list.add(progPackage);
			list.add(progPackage.getPpxml());

			cmsResultDto.setResultCode(Long.valueOf(0));
			cmsResultDto.setResultObject(list);
		} else {
			// 未查询到节目包
			String str = "没有查询到节目包。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> getProgPackage returns.");
		return cmsResultDto;
	}

	// 查询需要上传的目标地址
	private List getDesPathToSmb(String storageclass, String fileCode) {
		List retlist = new ArrayList();
		String strFilePath = "";
		String storageIP;
		String loginName;
		String loginPwd;
		String mapPath;
		String mapLoginUid;
		String mapLoginPwd;
		String mapLoginDisk;
		String stglobalid; // 存储体ID
		String stdirglobalid; // 存储体目录ID
		String storagedirname;

		Object[] maxdir = amsstoragedirManager
				.findstorageanddirlistbystorageclass(storageclass, fileCode);
		if (maxdir != null && maxdir.length >= 10) {
			storageIP = (String) (maxdir[2]);
			loginName = (String) (maxdir[4]);
			loginPwd = (String) (maxdir[5]);
			mapPath = (String) (maxdir[6]);
			mapLoginUid = (String) (maxdir[7]);
			mapLoginPwd = (String) (maxdir[8]);
			mapLoginDisk = (String) (maxdir[9]);
			stglobalid = (String) (maxdir[0]);
			stdirglobalid = (String) (maxdir[18]);
			storagedirname = (String) maxdir[13];

			strFilePath = "smb://";
			strFilePath += loginName;
			strFilePath += ":";
			strFilePath += loginPwd;
			strFilePath += "@";
			strFilePath += storageIP;
			strFilePath += "/";
			strFilePath += mapPath;
			strFilePath += "/";

			retlist.add(strFilePath);
			retlist.add(stglobalid);
			retlist.add(stdirglobalid);
			retlist.add(storagedirname);
		} else {
			// 未查询到
			cmsLog.debug("未查询到目标存储体的目录。");
			strFilePath = "";
			retlist = null;
		}
		return retlist;
	}

	// 转移文件，上传海报
	public CmsResultDto moveFile(ProgPackage progPackage,
			ProgramFiles programFiles, String strFileFrom, String storageclass) {
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> moveFile...");
		if (cmsLog.isDebugEnabled()) {
			cmsLog.debug("params -----------> start");
			cmsLog.debug("参数[progPackage]: " + progPackage);
			cmsLog.debug("\tproductid: " + progPackage.getProductid());
			cmsLog.debug("\tproductname: " + progPackage.getProductname());
			cmsLog.debug("\tstyleid: " + progPackage.getStyleid());
			cmsLog.debug("\tfilesizehi: " + progPackage.getFilesizehi());
			cmsLog.debug("参数[programFiles]: " + programFiles);
			cmsLog.debug("\tprogfileid: " + programFiles.getProgfileid());
			cmsLog.debug("\tprogramid: " + programFiles.getProgramid());
			cmsLog.debug("\tfilename: " + programFiles.getFilename());
			cmsLog.debug("\tcontentfilesize: " + programFiles.getContentfilesize());
			cmsLog.debug("\tfilecode: " + programFiles.getFilecode());
			cmsLog.debug("\tfiletypeid: " + programFiles.getFiletypeid());
			cmsLog.debug("参数[strFileFrom]: " + strFileFrom);
			cmsLog.debug("参数[storageclass]: " + storageclass);
			cmsLog.debug("params -----------> end");
		}
		CmsResultDto cmsResultDto = new CmsResultDto();

		strFileFrom = strFileFrom.replace('\\', '/');
		strFileFrom = strFileFrom.substring(strFileFrom.lastIndexOf("/") + 1,
				strFileFrom.length());
		// strFileFrom = System.getProperty("user.dir") + "/../" +
		// "webapps/upload/temp/" + strFileFrom;
		strFileFrom = ServerConfigListener.TEMP_PATH + strFileFrom;
		
		File file = new File(strFileFrom);
		cmsLog.debug("附件[ " + strFileFrom + " ]大小为: " + file.length());
		if (100 * 1024 < file.length()) {
			cmsResultDto.setErrorCode(1L);
			cmsResultDto.setErrorMessage(" 海报文件过大, 请更换海报重新上传! ");
			return cmsResultDto;
		}
		programFiles.setContentfilesize(String.valueOf(file.length()));

		cmsResultDto = cmsTransactionManager.savePngOfProgPackage(
				progPackageManager, packageFilesManager, programFilesManager,
				amsstorageprgrelManager, progPackage, programFiles,
				strFileFrom, storageclass);
		cmsLog.debug("准备更新节目包的ppxml字段和xml文件，节目包ID：" + progPackage.getProductid());
		ProgPackage pp = updateProgPackagePpxmlAndXmlfile(progPackage);

		if (pp == null) {
			String str = "更新节目包的ppxml字段和xml文件失败。";
			cmsLog.warn(str);
			cmsResultDto.setErrorDetail(cmsResultDto.getErrorDetail() + str);
		}

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> moveFile returns.");
		return cmsResultDto;
	}

	// 20100108 14:44
	// 删除海报
	public CmsResultDto deletePackageFile(ProgPackage progPackage, // 节目包
			ProgramFiles programFiles, // 文件
			String storageclass // 存储体等级
	) {
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> deletePackageFile...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 删除数据库记录，从 文件表、AMS节目位置表、节目包文件表

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> deletePackageFile returns.");
		return cmsResultDto;
	}

	// 从存储体复制文件到服务器，20100106 15:34
	// 返回所有文件的全路径
	public CmsResultDto moveFileFromStorageToServer(String storageclass, // 存储体等级
			ProgPackage progPackage, // 节目包
			String filecode, // filecode
			String fileType // 后缀
	) {
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> moveFileFromStorageToServer...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		cmsLog.debug("把海报文件从存储复制到服务器的临时目录...");

		List newFilePaths = new ArrayList();

		cmsLog.debug("查询节目包下对应的文件...");
		cmsLog.debug("节目包ID：" + progPackage.getProductid());
		cmsLog.debug("filecode：" + filecode);
		cmsLog.debug("filetype：" + fileType);
		// 根据节目包id、filecode、filetype，查询packagefiles、programfiles表，得到file
		List programfileses = packageFilesManager
				.getProgramFilesesByProductidFilecodeFiletypeid(
						progPackage.getProductid(), filecode, fileType);

		// 得到路径
		String strFilePath = "";
		String strNewPath = "";
		// cmsLog.debug("！！这里代码需要修改，查找源文件路径，用另外一个方法。");
		// List list = getDesPathToSmb(storageclass, filecode);
		// if(list != null)
		// {
		// strFilePath = (String)list.get(0);
		// }
		strNewPath = ServerConfigListener.TEMP_PATH;
		// if(strFilePath == null || strFilePath.equalsIgnoreCase(""))
		// {
		// String str = "路径为空。";
		// cmsLog.debug(str);
		// cmsResultDto.setResultCode(Long.valueOf(1));
		// cmsResultDto.setErrorMessage(str);
		// }
		// else
		if (programfileses != null && programfileses.size() > 0) {
			cmsLog.debug("得到节目包下对应的文件，共" + programfileses.size() + "个。");

			for (int i = 0; i < programfileses.size(); i++) {
				ProgramFiles pf = (ProgramFiles) programfileses.get(i);

				cmsLog.debug("处理第" + (i + 1) + "个文件...");
				cmsLog.debug("节目文件ID：" + pf.getProgfileid());
				cmsLog.debug("文件名称：" + pf.getFilename());

				// 得到文件名
				String strFileName = "";
				String strFileFullPath = "";
				String strNewFullPath = "";

				// 获得文件名，包含后缀
				strFileName = pf.getFilename();

				// 得到输出全路径
				strNewFullPath = strNewPath + strFileName;

				// 得到全路径
				// strFileFullPath = strFilePath + strFileName;
				// 返回：List
				// 1 - String
				// 源文件存放目标路径，格式："smb://hc:hc@172.23.19.66/公用/test.xml"
				// 2 - List<Object[]>
				// (AmsStorage)Object[0]
				// (AmsStoragePrgRel)Object[1]
				// (AmsStorageDir)Object[2]
				// (AmsStorageClass)Object[3]
				cmsLog.debug("查询文件的源路径...");
				List list1 = packageFilesManager
						.getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
								pf.getProgfileid(), storageclass,
								progPackage.getProductid());
				if (list1 != null && list1.size() >= 2) {
					strFileFullPath = (String) list1.get(0);
					cmsLog.debug("得到文件的源路径，准备复制文件...");
					cmsLog.debug("源 - " + strFileFullPath);
					cmsLog.debug("目标 - " + strNewFullPath);

					if (fileopr.checkSmbFileExist(strFileFullPath)) {
						// 源文件存在
						int ret = fileopr.copyFileFromSmbToLocal(
								strFileFullPath, strNewFullPath);
						if (ret == 0) {
							cmsLog.debug("复制文件到服务器成功。");
							newFilePaths.add(strNewFullPath);
						} else {
							String str = "复制文件到服务器失败。";
							cmsLog.warn(str);
							/**
							 * 导致字幕无法预览, 注释之
							 * HuangBo update by 2012年3月1日 10时29分
							 */
//							strNewFullPath = "";
//							cmsResultDto.setResultCode((long) 1);
//							cmsResultDto.setErrorMessage(str);
						}
					} else {
						// 源文件不存在
						String str = "源文件不存在，不复制文件。" + strFileFullPath;
						cmsLog.warn(str);
						/**
						 * 导致字幕无法预览, 注释之
						 * HuangBo update by 2012年3月1日 10时29分
						 */
//						strNewFullPath = "";
//						cmsResultDto.setResultCode((long) 1);
//						cmsResultDto.setErrorMessage(str);
					}
				} else {
					// 海报源文件路径不存在。
					String str = "海报源文件路径不存在。";
					cmsLog.warn(str);
					/**
					 * 导致字幕无法预览, 注释之
					 * HuangBo update by 2012年3月1日 10时29分
					 */
//					strNewFullPath = "";
//					cmsResultDto.setResultCode((long) 1);
//					cmsResultDto.setErrorMessage(str);
				}

				// // 迁移files到本地临时文件夹
				// copyFileSmbToLocal(strFileFullPath, strNewFullPath);
			}
			cmsLog.debug(progPackage.getProductname() + ": " + fileType + " 文件个数---> " + newFilePaths.size());
			if (cmsLog.isDebugEnabled()) {
				cmsLog.debug("\tstart ==========" + progPackage.getProductname() + ": " + fileType);
				for (Object o : newFilePaths) {
					cmsLog.debug("\t\t附件路径: " + o);
				}
				cmsLog.debug("\t  end ==========" + progPackage.getProductname() + ": " + fileType);
			}
			if (0 < newFilePaths.size()) {
				cmsResultDto.setResultObject(newFilePaths);
			} else {
				String message = "节目包[ " + progPackage.getProductid() + " : " 
						+ progPackage.getProductname() + " ] 附件[ " + fileType + " ]丢失! ";
				cmsResultDto.setResultCode(1L);
				cmsResultDto.setErrorMessage(message);
			}
		} else {
			String str = "没有查询到节目文件记录，返回失败。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> moveFileFromStorageToServer returns.");
		return cmsResultDto;
	}

	// ---------------------------------------------------------------------------------------------------

	static public String XMLtoStr(Document document) {
		String result = null;

		if (document != null) {
			StringWriter strWtr = new StringWriter();
			StreamResult strResult = new StreamResult(strWtr);
			TransformerFactory tfac = TransformerFactory.newInstance();
			try {
				Transformer t = tfac.newTransformer();
				t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.setOutputProperty(OutputKeys.METHOD, "xml");
				t.setOutputProperty(
						"{http://xml.apache.org/xslt}indent-amount", "4");
				t.transform(new DOMSource(document.getDocumentElement()),
						strResult);
			} catch (Exception e) {
				System.err.println("XML.toString(Document): " + e);
			}
			result = strResult.getWriter().toString();
		}

		return result;
	}

	// 20100115 13:04
	// 根据 文件code 和 存储体等级，查询得到文件存放目标路径
	public CmsResultDto getDestPathByFilecodeStclasscode(String filecode, // 文件code
			String stclasscode // 存储体等级code
	) {
		// 返回：List
		// 1 - String 目标路径()
		// 2 - List<Object[]>
		// (AmsStorage)Object[0]
		// (AmsStorageDir)Object[1]
		// (AmsStorageClass)Object[2]

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> getDestPathByFilecodeStclasscode...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		if (filecode == null || stclasscode == null) {
			// 获取（新建的）模板的目标路径
			// 从配置文件，读取
			filecode = "NearOnline"; // 模板的filecode
			stclasscode = "MODZIP"; // 新建模板的存储体等级code
		}

		List list = packageFilesManager.getDestPathByFilecodeStclasscode(
				filecode, stclasscode);
		cmsResultDto.setResultObject(list);
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> getDestPathByFilecodeStclasscode returns.");
		return cmsResultDto;
	}

	// 20100304 10:01
	// 修改节目包，包括节目包描述、节目包与服务产品栏目的关系、节目包与文件的关系、海报更新
	@SuppressWarnings("unchecked")
	public CmsResultDto updateProgPackageAllInfo(Boolean updateprogpackage, // 修改节目包信息标识
			ProgPackage progPackage, // 节目包信息，原方法
										// modifyProgPPRelAndPackageFiles、modifyProgSrvRel、modifyProgPackage
			Boolean updatepackagefiles, // 修改节目包节目文件关系标识
			List programs, // 节目列表，原方法 modifyProgPPRelAndPackageFiles
			Long styleId, // 样式ID，原方法 modifyProgPPRelAndPackageFiles
			Boolean updatecmsservice, // 修改节目包服务关系标识
			List cmsServices, // 服务列表，原方法 modifyProgSrvRel
			Boolean updatepng, // 修改海报标识
			List programFileses, // 海报列表，包含了修改和未修改的海报信息
			List fileFroms, // 海报源路径列表，在服务器上
			List storageclasses // 海报目标存储体等级code列表
	) {
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> updateProgPackageAllInfo...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		/**
		 * HuangBo update by 2010年12月3日 10时7分
		 * 1.2 修改 如果节目包状态为播发库, 则不允许修改
		 */
		ProgPackage pPackag = (ProgPackage) this.progPackageManager
				.getById(progPackage.getProductid());
		cmsLog.debug("节目包状态是否允许修改: " + pPackag.getState() + " - "
				+ pPackag.getDealstate() + " | " + (1l == pPackag.getState())
				+ ":" + (0l == pPackag.getDealstate()));
		if (2 < pPackag.getState()) {
			List<String> scheduleDates = new ArrayList<String>();
			List<String> tempScheduleDates = this.progListDetailManager.queryScheduleDateByProgPackageId(
					progPackage.getProductid());
			Long currTime = Long.valueOf(DateUtil.getCurrentDateStr("yyyyMMddHHmmss"));
			for (String scheduleDate : tempScheduleDates) {
				if (Long.valueOf(scheduleDate) > currTime) {
					scheduleDates.add(scheduleDate);
				}
			}
			
			if (0 < scheduleDates.size()) {
				List<String> canEditActions = flowActionManager.getGreaterOrLessAction(this.FINISHPROG, true);
				List<String> progActions = this.progListMangManager.queryActionsByScheduleDates(scheduleDates);
				
				if (!canEditActions.containsAll(progActions)) {
					cmsResultDto.setResultCode(1L);
					String errorMessage = " 节目包存在于流程不正确的编单中, 不允许修改:\r\n ";
					for (String string : scheduleDates) {
						errorMessage += "\t" + string + "\r\n";
					}
					cmsResultDto.setErrorMessage(errorMessage);
					return cmsResultDto;
				}
			}
			
			List<String> fileIds = null;
			try {
				fileIds = ListUtil.getPropertiesList(programFileses, "progfileid");
			} catch (Exception e) {
				cmsLog.error(e);
			}
			ListUtil.removeDuplicate(fileIds);
//			List<String> programFileIds = this.programFilesManager.queryProgramFileIdByProgPackageIdFileCode(
//					progPackage.getProductid(), fileIds);
			
			if (0 < fileIds.size()) {
				progPackage.setState(1L);
				if (!this.removeOnlineFile(progPackage.getProductid(), fileIds)) {
						cmsResultDto.setResultCode(1L);
						cmsResultDto.setErrorMessage(" 节目包正在使用中, 不允许修改! ");
						return cmsResultDto;
				}
			}
		}
		
//		if (1 != pPackag.getState() || 0 != pPackag.getDealstate()) {
//			cmsLog.debug("节目包状态不正确, 不允许修改!");
//			cmsResultDto.setResultCode(1l);
//			cmsResultDto.setErrorMessage("节目包不是缓存库,未处理状态, 不允许修改!");
//			return cmsResultDto;
//		}

//		List<String> sourceFiles = new ArrayList<String>();
//		for (Object object : fileFroms) {
//			if (null == object) {
//				sourceFiles.add(null);
//				continue;
//			}
//			String sourceFile = object.toString().replaceAll("\\\\", "/");
//			sourceFile = sourceFile.substring(sourceFile.lastIndexOf("/") + 1);
//		}

		// 配置文件，获取
		String xmlFilecode = "PPXML";
		String stclasscodeNearOnline = "NearOnline";

		cmsResultDto = cmsTransactionManager.updateProgPackageAllInfo(
				progPackageManager, progSrvRelManager, pPColumnRelManager,
				srvColumnManager, ppSrvPdtRelManager, srvProductManager,
				progPPRelManager, packageFilesManager,
				packStyleFileTypeManager, programFilesManager,
				amsstorageprgrelManager, progListDetailManager,
				updateprogpackage, progPackage, updatepackagefiles, programs,
				styleId, updatecmsservice, cmsServices, updatepng,
				programFileses, fileFroms, storageclasses, xmlFilecode, // 节目包xml的filecode
				stclasscodeNearOnline // 节目包xml文件存放存储体等级
				);
		
		Bpmc bpmc = new Bpmc(progPackage.getProductid(), progPackage.getUpdatemanid(), null, null, 
				null, null, "修改节目包: " + progPackage.getProductname(), "U");
		this.bpmcManager.save(bpmc);

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> updateProgPackageAllInfo returns.");
		return cmsResultDto;
	}

	// 20100322 13:20
	//
	/**
	 * HuangBo update 2010年7月30日 10时35分
	 */
	public CmsResultDto saveProgramInfoProgramFiles(Object progobject,
			Object progfilesobject, String filepath, String sourcePartPath) {
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> saveProgramInfoProgramFiles...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 配置文件
		String stclasscodePrepared = "Prepared"; // 节目录入存储体等级code
		String stclasscodeNearOnline = "NearOnline"; // 缓存库存储体等级code

		ProgramInfo programinfo = (ProgramInfo) progobject;
		ProgramFiles programfiles = (ProgramFiles) progfilesobject;

		CmsResultDto c1 = cmsTransactionManager.saveProgramInfoProgramFiles(
				programinfoManager, programFilesManager, programinfo,
				programfiles);
		if (c1.getResultCode() == 0) {
			cmsLog.debug("保存节目表和节目文件表成功，准备生成迁移任务单...");

			List list = (List) c1.getResultObject();
			if (list != null && list.size() >= 2) {
				programinfo = (ProgramInfo) list.get(0);
				programfiles = (ProgramFiles) list.get(1);

				MigrationServiceImpl migrationService = new MigrationServiceImpl();
				CmsResultDto c2 = migrationService.generateMigrationForProgram(
						programinfo, programfiles, sourcePartPath,
						stclasscodePrepared, filepath, stclasscodeNearOnline);
				if (c2.getResultCode() == 0) {
					cmsLog.debug("生成迁移任务单成功。");
				} else {
					String str = "生成迁移任务单失败。";
					cmsLog.warn(str);
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
				}
			} else {
				String str = "保存节目表和节目文件表失败。";
				cmsLog.warn(str);
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
			}
		} else {
			String str = "保存节目表和节目文件表失败。";
			cmsLog.warn(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> saveProgramInfoProgramFiles returns.");
		return cmsResultDto;
	}

	// 20100608 15:28
	// 根据节目包ID，查询节目包的主文件列表
	public CmsResultDto getProgramfilesByProductid(String productid // 节目包ID
	) {
		// 返回：List<ProgramFiles>
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> getProgramfilesByProductid...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		List list = packageFilesManager.getProgramFilesesByProductidProgrank(
				productid, (long) 1);
		cmsResultDto.setResultObject(list);

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> getProgramfilesByProductid returns.");
		return cmsResultDto;
	}

	// 20100608 16:01
	// 修改节目包，包括节目包描述、节目包与服务产品栏目的关系、节目包与文件的关系、海报更新
	public CmsResultDto updateProgPackageWithoutPrograminfoCmsservice(
			Boolean updateprogpackage, // 修改节目包信息标识
			ProgPackage progPackage, // 节目包信息，原方法
										// modifyProgPPRelAndPackageFiles、modifyProgSrvRel、modifyProgPackage
			Boolean updatepackagefiles, // 修改节目包节目文件关系标识
			List programfiles, // 节目列表，原方法 modifyProgPPRelAndPackageFiles
			Boolean updatecmsservice, // 修改节目包服务关系标识，目标为false
			List cmsServices, // 服务列表，原方法 modifyProgSrvRel
			Boolean updatepng, // 修改海报标识
			List programFileses, // 海报列表，包含了修改和未修改的海报信息
			List fileFroms, // 海报源路径列表，在服务器上
			List storageclasses // 海报目标存储体等级code列表
	) {
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> updateProgPackageWithoutPrograminfoCmsservice...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		// 配置文件，获取
		String xmlFilecode = "PPXML";
		String stclasscodeNearOnline = "NearOnline";

		updatecmsservice = false;

		CmsResultDto list = this.getProgPackageList(progPackage.getProductid());
		if (list.getResultObject() == null) {
			cmsResultDto = cmsTransactionManager
					.updateProgPackageWithoutPrograminfoCmsservice(
							progPackageManager, progSrvRelManager,
							pPColumnRelManager, srvColumnManager,
							ppSrvPdtRelManager, srvProductManager,
							progPPRelManager, packageFilesManager,
							packStyleFileTypeManager, programFilesManager,
							amsstorageprgrelManager, progListDetailManager,
							updateprogpackage, progPackage, updatepackagefiles,
							programfiles, updatecmsservice, cmsServices,
							updatepng, programFileses, fileFroms,
							storageclasses, xmlFilecode, // 节目包xml的filecode
							stclasscodeNearOnline // 节目包xml文件存放存储体等级
					);
			cmsLog.debug("修改成功!");
		} else {
			cmsResultDto.setResultCode(1l);
			cmsResultDto.setResultObject(list);
			cmsLog.debug("不允许修改!");
		}

		cmsLog.debug("Cms -> ProgPackageServiceImpl -> updateProgPackageWithoutPrograminfoCmsservice returns.");
		return cmsResultDto;
	}

	/**
	 * 根据数据导入的节目包ID. 获取该节目包有效日期内. 活动状态不是数据导入 (FU00000080) 的编单号
	 * 
	 * @param progListDetailID
	 * @return 编单号, 活动 List<Object[2]> object[0]: ProgListMang object[1]:
	 *         FlowAction
	 */
	public CmsResultDto getProgPackageList(String progListDetailID) {
		cmsLog.debug("cms -> ProgPackageServiceImpl -> getProgPackageList...");
		CmsResultDto cmsResultDto = new CmsResultDto();

		ProgListDetail progListDetail = (ProgListDetail) this.progListDetailManager
				.getById(progListDetailID);
		cmsLog.debug("获取ProgListDetail");
		if (progListDetail != null) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			String from = df.format(progListDetail.getValidFrom()) + "000000"; // 取得上线日期
			String to = df.format(progListDetail.getValidTo()) + "000000"; // 取得下线日期

			cmsLog.debug("节目包详细存在, 并取得上下线日期...");

			List<Object[]> idsList = progListMangManager
					.getProgListMangsByDate2(from, to, "FU00000080");

			for (int i = 0; i < idsList.size(); i++) {
				Object[] objects = idsList.get(i);
				ProgListMang progListMang = (ProgListMang) objects[0];
				Date date = this.progListFileDao
						.queryPlfByScheduledateAndFileType(
								progListMang.getScheduledate(), 9l);

				// 当播发时间小于等于当前时间, 则表示已经播发或已经开始播发
				if (date != null && date.getTime() <= new Date().getTime()) {
					idsList.remove(i);
				}
			}

			cmsLog.debug("节目包被其它  " + idsList.size() + " 个编单包含!");
			if (idsList.size() > 0) {
				cmsResultDto.setResultCode(0l);
				cmsResultDto.setResultObject(idsList);
			} else {
				cmsResultDto.setResultCode(0l);
				cmsResultDto.setResultObject(null);
			}
		} else {
			cmsResultDto.setResultCode(0l);
			cmsResultDto.setResultObject(null);
			cmsLog.debug("节目包不被其它 编单包含!");
		}
		return cmsResultDto;
	}

	/**
	 * 删除或下线节目包
	 * 
	 * @param id
	 *            节目包ID
	 * @param date
	 *            下线日期 2010-01-01
	 * @return
	 */
	public CmsResultDto deleteOrDownProgPackage(String id, String date) {
		cmsLog.debug("Cms -> ProgPackageServiceImpl -> deleteOrDownProgPackage...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		CmsResultDto idList = this.getProgPackageList(id);
		if (idList.getResultObject() == null) {
			ProgListDetail progListDetail = (ProgListDetail) progListDetailManager.getById(id);
			
			try {
				progListDetail.setValidTo(new SimpleDateFormat("yyyy-MM-dd").parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			progListDetailManager.update(progListDetail);
			
			cmsResultDto.setResultCode(0l);
			cmsResultDto.setErrorMessage("节目包已经下线!");
			
			cmsLog.debug("无其它受影响的编单, 直接修改成功 ...");
		} else {
			cmsResultDto.setResultCode(1l);
			cmsResultDto.setResultObject(idList);
			
			cmsLog.debug("被其它编单包含, 不允许修改...");
		}
		
		return cmsResultDto;
	}
	
	/**
	 * 将报纸压缩文件, 导入为节目包
	 * @param progPackage 节目包
	 * @param programFile 节目包文件
	 * @param portalColumn 栏目
	 * @param filePath 生成的文件路径
	 * @return
	 * @throws ParseException
	 */
	public List<ProgPackage> importPaper2ProgPackage(ProgPackage progPackage, 
			ProgramFiles programFile, PortalColumn portalColumn) throws ParseException {
		String progPackageID = progPackage.getProductid();
		
		programFile.setProgrank(1l);
		programFile.setInputtime(new Date());
		cmsLog.debug("1. 存储文件信息");
		//TODO 保存文件记录
		
		cmsLog.debug("2. 补全节目包属性, 录入时间, 节目包状态, 节目包使用状态, 和主键. 添加节目包记录");
		progPackage.setInputtime(new Date());		// 节目包导入时间
		progPackage.setUpdatetime(new Date());		// 默认节目包最后修改时间
		progPackage.setState(1l);					// 报纸导入后, 节目包的默认状态为[1]缓存库
		progPackage.setDealstate(0l);				// 报纸导入后, 节目包的默认使用状态为[0]未处理
		//TODO 保存节目包记录
		
		cmsLog.debug("3. 添加节目包文件关系记录, 主键自动生成 ");
		PackageFiles packageFiles = new PackageFiles();
		packageFiles.setProductid(progPackage.getProductid());
		packageFiles.setProgfileid(programFile.getProgfileid());
		//TODO 保存节目包文件记录
		
		cmsLog.debug("4. 添加位置表记录, 主键手动生成, 在Manager里面会生成, 因此不需要在此指定");
		AmsStoragePrgRel amsStoragePrgRel = new AmsStoragePrgRel();
		amsStoragePrgRel.setPrglobalid(progPackageID);
		amsStoragePrgRel.setProgfileid(programFile.getProgfileid());
		amsStoragePrgRel.setFilename(programFile.getFilename());
		amsStoragePrgRel.setStglobalid(programFile.getStorageid());
		amsStoragePrgRel.setStdirglobalid(programFile.getStoragedirid());
		amsStoragePrgRel.setFtypeglobalid(programFile.getFilecode());
		amsStoragePrgRel.setInputmanid(programFile.getInputmanid());
		amsStoragePrgRel.setUploadtime(new Date());
		amsStoragePrgRel.setFiledate(new Date());
		amsStoragePrgRel.setInputtime(new Date());
		amsStoragePrgRel.setFilepath(progPackageID.substring(0, 10) + 
				"/" + progPackageID + "/");
		amsStoragePrgRel.setRemark("");
		
		//TODO 保存文件位置表记录
		
		cmsLog.debug("5. 节目包栏目关系表记录, 主键手动生成, 在manager里面会生成");
		PPColumnRel ppColumnRel = new PPColumnRel();
		ppColumnRel.setColumnclassid(portalColumn.getColumnclassid());
		ppColumnRel.setProductid(progPackage.getProductid());
		ppColumnRel.setInputmanid(programFile.getInputmanid());
		ppColumnRel.setInputtime(new Date());
		//TODO 保存节目包栏目关系表记录
		
		cmsLog.debug("将数据保存到数据库!");
		this.cmsTransactionManager.savePager(programFilesManager, programFile,
				progPackageManager, progPackage, packageFilesManager,
				packageFiles, amsstorageprgrelManager, amsStoragePrgRel,
				pPColumnRelManager, ppColumnRel, bpmcManager);
		

		cmsLog.debug("添加完成后根据栏目来查询所有的报纸信息!");
		return null;//queryPaper(portalColumn.getColumnclassid(), null, null);
	}
	
	/**
	 * 根据栏目ID, 节目包录入时间, 节目包名称查询报纸信息
	 * @param portalColumnID 栏目 (PortalColumn) ID
	 * @param time 节目包录入时间, 格式: 2010-09-01
	 * @param progPackageName 节目包名称
	 * @return 报纸节目包集合, 按节目包录入日期降序排序
	 * @throws ParseException 
	 */
	public List<ProgPackage> queryPaper(String portalColumnID, 
			String time, String progPackageName) throws ParseException {
		cmsLog.debug("查询报纸节目包信息!");
		Map<String, Object> map = new HashMap<String, Object>(3);
		String hql = "select p from ProgPackage as p";
		
		if(null != portalColumnID && !"".equals(portalColumnID.trim())) {
			cmsLog.debug("栏目ID存在");
			map.put("portalColumnID", portalColumnID);
			hql += ", PPColumnRel as c where c.columnclassid = :portalColumnID and c.productid = p.productid";
			
			if(null != time && !"".equals(time.trim())) {
				cmsLog.debug("栏目ID存在  [&&] 节目包日期存在");
				Date[] date = parseDate(time);
				map.put("inputTimeStart", date[0]);
				map.put("inputTimeEnd", date[1]);
				hql += " and p.inputtime between :inputTimeStart and :inputTimeEnd";
				
				if(null != progPackageName && !"".equals(progPackageName.trim())) {
					cmsLog.debug("栏目ID存在  [&&] 节目包日期存在 [&&] 节目包名称存在");
					map.put("progPackageName", progPackageName);
					hql += " and p.productname like :progPackageName";
				}
			} else if(null != progPackageName && !"".equals(progPackageName.trim())) {
				cmsLog.debug("栏目ID存在  [&&] 节目包名称存在");
				map.put("progPackageName", progPackageName);
				hql += " and p.productname like :progPackageName";
			}
		} else if (null != time && !"".equals(time.trim())) {
			cmsLog.debug("节目包日期存在");
			Date[] date = parseDate(time);
			map.put("inputTimeStart", date[0]);
			map.put("inputTimeEnd", date[1]);
			hql += " where p.inputtime between :inputTimeStart and :inputTimeEnd";
			
			if(null != progPackageName && !"".equals(progPackageName.trim())) {
				cmsLog.debug("节目包日期存在 [&&] 节目包名称存在");
				map.put("progPackageName", progPackageName);
				hql += " and p.productname like :progPackageName";
			}
		} else if(null != progPackageName && !"".equals(progPackageName.trim())) {
			cmsLog.debug("节目包名称存在");
			map.put("progPackageName", progPackageName);
			hql += " where p.productname like :progPackageName";
		} else {
			cmsLog.debug("栏目ID不存在 [&&] 节目包日期不存在 [&&] 节目包名称不存在");
		}
		
		hql += " order by p.inputtime desc";
		
		List<ProgPackage> list = this.progPackageManager.queryPaper(hql, map);
		for (ProgPackage progPackage : list) {
			progPackage.setShootdate(FileOperationImpl
					.getState(progPackage.getState()));
			progPackage.setIssuedate(FileOperationImpl
					.getDealState(progPackage.getDealstate()));
		}
		
		return list;
	}
	
	/**
	 * 根据日期格式字符串解析出该日期的0点0分, 到第二天的0点0分
	 * @param time 日期字符串, 格式: 2010-01-01
	 * @return Date[2]
	 * date[0]: 字符串代表的当天0点的日期对象
	 * date[1]: 字符串代表的当天24点的日期对象
	 * @throws ParseException 待解析的日期字符格式不正确
	 */
	private Date[] parseDate(String time) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date from = simpleDateFormat.parse(time);
		calendar.setTime(from);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return new Date[] {from, calendar.getTime()};
	}
	
	/**
	 * 获取节目包信息.. (文件ID, 节目包ID, 目录的拼写, 存储体ID, 存储体目录ID)
	 * @param stroageClassCode 存储等级Code
	 * @param fileCode 文件Code
	 * @return Object[]:
	 * object[0]:  文件ID
	 * object[1]:  节目包ID
	 * object[2]:  协议
	 * object[3]:  IP地址
	 * object[4]:  用户名
	 * object[5]:  密码
	 * object[6]:  存储体路径
	 * object[7]:  存储体目录
	 * object[8]:  位置表目录
	 * object[9]:  存储体ID
	 * object[10]: 存储体目录ID
	 */
	public Object[] getProgPackageMessage(String stroageClassCode, 
			String fileCode) {
		Object[] storage = amsstoragedirManager
				.findstorageanddirlistbystorageclass(stroageClassCode, fileCode);
		
		String temp = "R|N";
		String progPackageId = this.getcmspk.GetTablePK("ProgPackage", temp);	// 生成节目包主键
		
		String programInfoId = getcmspk.GetTablePK("ProgramInfo", temp);
		String programFilesId = this.programFilesManager.getProgramFilesID(programInfoId);
		
		String mapPath = (String) storage[6];
		mapPath = mapPath.endsWith("/") 
				? mapPath.substring(0, mapPath.length() - 1) : mapPath;
		String dirNameString = (String) storage[13];
		dirNameString = dirNameString.endsWith("/") 
				? dirNameString.substring(0, dirNameString.length() - 1) 
				: dirNameString;
		
//		String path = "//" + mapPath + "/" + dirNameString + 
//				"/" + progPackageId.substring(0, 10) + 
//				"/" + progPackageId + "/";
		
		return new Object[] {programFilesId, progPackageId, storage[3], 
				storage[2], storage[4], storage[5], mapPath, 
				dirNameString, progPackageId.substring(0, 10),
				storage[0], storage[18]};
	}
	
	/**
	 * 查询所有品牌信息
	 * @return
	 */
	public List<CmsSite> getCmsSites() {
		List<CmsSite> cmsSites = this.cmsSiteManager.queryAllCmsSites();
		return cmsSites;
	}
	
	/**
	 * 查询节目信息 HuangBo addition by 2010年11月18日 17时12分
	 * @param isEmptyProgPackage 是否创建无实体文件节目包
	 * @param timeStart 开始时间, 格式: 2010-09-09
	 * @param timeEnd 结束时间, 格式: 2010-10-10
	 * @param programInfoId 节目ID
	 * @param title 正题名
	 * @param epicodename 系列名称
	 * @param genre 节目分类
	 * @return
	 * @throws ParseException
	 */
	public List<ProgramInfo> getProgramInfos(Boolean isEmptyProgPackage, 
			String timeStart, String timeEnd, String programInfoId, 
			String title, String epicodename, String genre) throws ParseException {
		String hql = "select new com.soaplat.cmsmgr.bean.ProgramInfo(p.programid, p.title, s.stylename, p.description, p.genre, p.progproperty, p.progtype, p.dsflag, p.proginfoxml, p.inputmanid, p.inputtime, pf.filecode, p.styleid) from ProgramInfo as p, ProgramFiles as pf, PackStyle as s where s.styleid = p.styleid and p.programid = pf.programid and ";
		Map<String, Object> map = new HashMap<String, Object>(); 
		List<Long> dsflagList = new ArrayList<Long>();
		dsflagList.add(-1L);	// 节目状态为未导入
		dsflagList.add(1L);		// 节目状态为迁移失败
		
		//TODO 需修改为两种状态都能查出实体文件存在的片花
		if (isEmptyProgPackage) {
			hql += "(p.dsflag in (:dsflag) or pf.filetypeid = 'Clip') ";
		} else {
			hql += "p.dsflag not in (:dsflag)";
			dsflagList.add(0L);
		}
		map.put("dsflag", dsflagList);
		
		if ((null != timeStart && 0 != timeStart.trim().length())
				&& (null != timeEnd && 0 != timeEnd.trim().length())) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.inputtime between :timeStart and :timeEnd ";
			map.put("timeStart", DateUtil.parseDate("yyyy-MM-dd", timeStart));
			map.put("timeEnd", DateUtil.parseDate("yyyy-MM-dd", timeEnd));
		} else {
			if (null != timeStart && 0 != timeStart.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime >= :timeStart ";
				map.put("timeStart", DateUtil.parseDate("yyyy-MM-dd", timeStart));
			}
			
			if (null != timeEnd && 0 != timeEnd.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime <= :timeEnd ";
				map.put("timeEnd", DateUtil.parseDate("yyyy-MM-dd", timeEnd));
			}
		}
		
		if (null != programInfoId && 0 != programInfoId.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.programid like :programInfoId ";
			map.put("programInfoId", "%" + programInfoId + "%");
		}
		
		if (null != title && 0 != title.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.title like :title ";
			map.put("title", "%" + title + "%");
		}
		
		if (null != epicodename && 0 != epicodename.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.epicodename like :epicodename ";
			map.put("epicodename", "%" + epicodename + "%");
		}
		
		if (null != genre && 0 != genre.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.genre = :genre ";
			map.put("genre", genre);
		}
		
		hql += "order by p.inputtime desc";
		cmsLog.debug("查询节目信息HQL: " + hql);
		List<ProgramInfo> programInfos = this.programinfoManager.queryProgramInfos(hql, map);
		List<ProgramInfo> returnProgramInfos = new ArrayList<ProgramInfo>();
		for (ProgramInfo programInfo : programInfos) {
			/**
			 * 如果节目状态不是已入库, 并且是片花. 则不返回给前台该节目记录
			 * 最终返回给前台的节目列表为有实体文件和无实体文件的主文件节目, 实体文件存在的片花节目
			 */
			if (2 != programInfo.getDsflag() 
					&& "Clip".equals(programInfo.getRemark())) {
			} else {
				int dsflag = Integer.parseInt(programInfo.getDsflag().toString());
				programInfo.setRating(ProgramInfoService.getDsflagName(dsflag));
				returnProgramInfos.add(programInfo);
			}
			cmsLog.debug(programInfo.getEpicodename());
		}
		return returnProgramInfos;
	}
	
	public void addProgPackage(ProgPackage progPackage, List<String> programInfoIds, 
			String inputMainId) {
		
	}
	
	/**
	 * 查询节目包信息(编单定义)
	 * @param columnId 栏目编号
	 * @param progPackageId 节目包ID
	 * @param progPackageName 节目包名称
	 * @param timeStart 节目包创建时间起始, 格式: 2010-09-09
	 * @param timeEnd 节目包创建时间结束, 格式:2010-10-10
	 * @return 返回节目包集合
	 * @throws ParseException
	 */
	public List<ProgPackage> queryProgPackages(String columnId, String progPackageId, 
			String progPackageName,	String timeStart, String timeEnd, 
			String onlineSince) throws ParseException {
		cmsLog.debug("参数: 栏目编号            columnId ==> " + columnId);
		cmsLog.debug("参数: 节目包编号       progPackageId ==> " + progPackageId);
		cmsLog.debug("参数: 节目包名称       progPackageName ==> " + progPackageName);
		cmsLog.debug("参数: 录入时间开始  timeStart ==> " + timeStart);
		cmsLog.debug("参数: 录入时间结束  timeEnd ==> " + timeEnd);
		cmsLog.debug("参数: 上线日期            onlineSince ==> " + onlineSince);
		
		String hql = "select new com.soaplat.cmsmgr.bean.ProgPackage(p.productid, p.productname, ps.stylename, pt.classname, p.siteCode, p.styleid, p.inputtime) from ProgPackage as p, ProgType as pt, PackStyle as ps where pt.progtypeid = p.progtype and p.styleid = ps.styleid "; //(select c.siteCode from PortalColumn as c where c.columnclassid = :columnId) ";
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		if (null != columnId && 0 != columnId.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			
			if (columnId.startsWith("PC")) {
				hql += "p.siteCode = (select c.siteCode from PortalColumn as c where c.columnclassid = :columnId) ";
				map.put("columnId", columnId);
			} else if("全".equals(columnId)) {
				hql += "p.siteCode is null ";
			} else {
				hql += "p.siteCode = :columnId ";
				map.put("columnId", columnId);
			}
		}
		
		
		if (null != progPackageId && 0 != progPackageId.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.productid like :progPackageId ";
			map.put("progPackageId", "%" + progPackageId + "%");
		}
		
		if (null != progPackageName && 0 != progPackageName.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.productname like :progPackageName ";
			map.put("progPackageName", "%" + progPackageName + "%");
		}
		
		if ((null != timeStart && 0 != timeStart.trim().length())
				&& (null != timeEnd && 0 != timeEnd.trim().length())) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.inputtime between :timeStart and :timeEnd ";
			map.put("timeStart", DateUtil.parseDate("yyyy-MM-dd", timeStart));
			map.put("timeEnd", DateUtil.parseDate("yyyy-MM-dd", timeEnd));
		} else {
			if (null != timeStart && 0 != timeStart.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime >= :timeStart ";
				map.put("timeStart", DateUtil.parseDate("yyyy-MM-dd", timeStart));
			}
			
			if (null != timeEnd && 0 != timeEnd.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime <= :timeEnd ";
				map.put("timeEnd", DateUtil.parseDate("yyyy-MM-dd", timeEnd));
			}
		}
		
		if (null != onlineSince && 0 != onlineSince.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.subscriberstime = :onlineSince ";
			map.put("onlineSince", DateUtil.parseDate("yyyy-MM-dd", onlineSince));
			hql += "order by p.subscriberstime asc, p.inputtime desc";
		} else {
			hql += "order by p.inputtime desc";
		}
		
		cmsLog.debug("查询节目包HQL: " + hql);
		List<ProgPackage> progPackages = this.progPackageManager.queryPaper(hql, map);
		cmsLog.debug("查询得出节目包数量: " + progPackages.size());
		for (ProgPackage progPackage : progPackages) {
			if (null == progPackage.getSiteCode() 
					|| 0 == progPackage.getSiteCode().trim().length()) {
				progPackage.setSumfilesize("全");
			} else {
				progPackage.setSumfilesize(this.cmsSiteManager.getSiteNameBySiteCode(
						progPackage.getSiteCode()));
			}
		}
		return progPackages;
	}
	
	/**
	 * 查询节目包信息(强制删除)
	 * @param columnId 栏目编号
	 * @param progPackageId 节目包ID
	 * @param progPackageName 节目包名称
	 * @param timeStart 节目包创建时间起始, 格式: 2010-09-09
	 * @param timeEnd 节目包创建时间结束, 格式:2010-10-10
	 * @return 返回节目包集合
	 * @throws ParseException
	 */
	public List<ProgPackage> queryProgPackagesDelete(String columnId, String progPackageId, 
			String progPackageName,	String timeStart, String timeEnd, 
			String onlineSince, String reserves) throws ParseException {
		cmsLog.debug("参数: 栏目编号            columnId ==> " + columnId);
		cmsLog.debug("参数: 节目包编号           progPackageId ==> " + progPackageId);
		cmsLog.debug("参数: 节目包名称           progPackageName ==> " + progPackageName);
		cmsLog.debug("参数: 录入时间开始         timeStart ==> " + timeStart);
		cmsLog.debug("参数: 录入时间结束         timeEnd ==> " + timeEnd);
		cmsLog.debug("参数: 上线日期             onlineSince ==> " + onlineSince);
		cmsLog.debug("参数: 是否强制保留         reserves ==> " + reserves);
		
		String hql = "select new com.soaplat.cmsmgr.bean.ProgPackage(p.productid, p.productname, ps.stylename, pt.classname, p.siteCode, p.styleid, p.inputtime) from ProgPackage as p, ProgType as pt, PackStyle as ps where p.productid in (select pld.productid from ProgListDetail as pld) and pt.progtypeid = p.progtype and p.styleid = ps.styleid "; //(select c.siteCode from PortalColumn as c where c.columnclassid = :columnId) ";
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		if (null != columnId && 0 != columnId.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			
			if (columnId.startsWith("PC")) {
				hql += "p.siteCode = (select c.siteCode from PortalColumn as c where c.columnclassid = :columnId) ";
				map.put("columnId", columnId);
			} else if("全".equals(columnId)) {
				hql += "p.siteCode is null ";
			} else {
				hql += "p.siteCode = :columnId ";
				map.put("columnId", columnId);
			}
		}
		
		
		if (null != progPackageId && 0 != progPackageId.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.productid like :progPackageId ";
			map.put("progPackageId", "%" + progPackageId + "%");
		}
		
		if (null != progPackageName && 0 != progPackageName.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.productname like :progPackageName ";
			map.put("progPackageName", "%" + progPackageName + "%");
		}
		
		if ((null != timeStart && 0 != timeStart.trim().length())
				&& (null != timeEnd && 0 != timeEnd.trim().length())) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.inputtime between :timeStart and :timeEnd ";
			map.put("timeStart", DateUtil.parseDate("yyyy-MM-dd", timeStart));
			map.put("timeEnd", DateUtil.parseDate("yyyy-MM-dd", timeEnd));
		} else {
			if (null != timeStart && 0 != timeStart.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime >= :timeStart ";
				map.put("timeStart", DateUtil.parseDate("yyyy-MM-dd", timeStart));
			}
			
			if (null != timeEnd && 0 != timeEnd.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime <= :timeEnd ";
				map.put("timeEnd", DateUtil.parseDate("yyyy-MM-dd", timeEnd));
			}
		}
		
		if (null != reserves && 0 != reserves.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.ptglobalid = :reserves ";
			map.put("reserves", reserves);
		}
		
		if (null != onlineSince && 0 != onlineSince.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.subscriberstime = :onlineSince ";
			map.put("onlineSince", DateUtil.parseDate("yyyy-MM-dd", onlineSince));
			hql += "order by p.subscriberstime asc, p.inputtime desc";
		} else {
			hql += "order by p.inputtime desc";
		}
		
		cmsLog.debug("查询节目包HQL: " + hql);
		List<ProgPackage> progPackages = this.progPackageManager.queryPaper(hql, map);
		cmsLog.debug("查询得出节目包数量: " + progPackages.size());
		for (ProgPackage progPackage : progPackages) {
			if (null == progPackage.getSiteCode() 
					|| 0 == progPackage.getSiteCode().trim().length()) {
				progPackage.setSumfilesize("全");
			} else {
				progPackage.setSumfilesize(this.cmsSiteManager.getSiteNameBySiteCode(
						progPackage.getSiteCode()));
			}
		}
		return progPackages;
	}
	
	public List<ProgPackage> queryProgPackages(String siteCodeOrColumnId, String progPackageId, 
			String progPackageName, String startTime, String endTime, 
			String progType, String category, Long styleId) throws ParseException {
		cmsLog.debug("参数: 品牌或栏目编号   siteCodeOrColumnId ==> " + siteCodeOrColumnId);
		cmsLog.debug("参数: 节目包编号             progPackageId ==> " + progPackageId);
		cmsLog.debug("参数: 节目包名称             progPackageName ==> " + progPackageName);
		cmsLog.debug("参数: 录入时间开始        startTime ==> " + startTime);
		cmsLog.debug("参数: 录入时间结束        endTime ==> " + endTime);
		cmsLog.debug("参数: 节目包类型             progType ==> " + progType);
		cmsLog.debug("参数: 节目包分类             category ==> " + category);
		cmsLog.debug("参数: 样式编号                  styleId ==> " + styleId);
		
		String hql = "select new com.soaplat.cmsmgr.bean.ProgPackage(p.productid, p.styleid, p.ptglobalid, p.productname, p.description, p.category, p.titlebrief, p.epicodename, p.epicodeid, p.lengthtc, p.licensingWindowStart, p.licensingWindowEnd, p.progtype, p.epicodenumber, p.subtitlelanguage, p.audiolanguage, p.director, p.actors, p.shootdate, p.issuedate, p.countrydist, p.subscriberstime, p.subscriberetime, p.ppxml, p.inputmanid, p.inputtime, ps.stylename, p.filesizehi, p.filesizelow, p.sumfilesize, p.updatemanid, p.updatetime, p.siteCode) from ProgPackage as p, PackStyle as ps where ps.styleid = p.styleid "; //(select c.siteCode from PortalColumn as c where c.columnclassid = :columnId) ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != siteCodeOrColumnId && 0 != siteCodeOrColumnId.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			
			if (siteCodeOrColumnId.startsWith("PC")) {
				hql += "p.siteCode = (select c.siteCode from PortalColumn as c where c.columnclassid = :columnId) ";
				map.put("columnId", siteCodeOrColumnId);
			} else if("全".equals(siteCodeOrColumnId)) {
				hql += "p.siteCode is null ";
			} else {
				hql += "p.siteCode = :columnId ";
				map.put("columnId", siteCodeOrColumnId);
			}
		}
		
		if (null != progPackageId && 0 != progPackageId.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.productid like :progPackageId ";
			map.put("progPackageId", "%" + progPackageId + "%");
		}
		
		if (null != progPackageName && 0 != progPackageName.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.productname like :progPackageName ";
			map.put("progPackageName", "%" + progPackageName + "%");
		}
		
		if ((null != startTime && 0 != startTime.trim().length())
				&& (null != endTime && 0 != endTime.trim().length())) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.inputtime between :timeStart and :timeEnd ";
			Date startTimeDate = -1 != startTime.indexOf("-") 
					? DateUtil.parseDate("yyyy-MM-dd", startTime)
							: DateUtil.parseDate("MM/dd/yyyy", startTime);
			Date endTimeDate = -1 != endTime.indexOf("-")
					? DateUtil.parseDate("yyyy-MM-dd", endTime)
							: DateUtil.parseDate("MM/dd/yyyy", endTime);
			map.put("timeStart", startTimeDate);
			map.put("timeEnd", endTimeDate);
		} else {
			if (null != startTime && 0 != startTime.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime >= :timeStart ";
				Date startTimeDate = -1 != startTime.indexOf("-") 
						? DateUtil.parseDate("yyyy-MM-dd", startTime)
								: DateUtil.parseDate("MM/dd/yyyy", startTime);
				map.put("timeStart", startTimeDate);
			}
			
			if (null != endTime && 0 != endTime.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime <= :timeEnd ";
				Date endTimeDate = -1 != endTime.indexOf("-")
						? DateUtil.parseDate("yyyy-MM-dd", endTime)
								: DateUtil.parseDate("MM/dd/yyyy", endTime);
				map.put("timeEnd", endTimeDate);
			}
		}
		
		if (null != progType && 0 != progType.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.progtype = :progType ";
			map.put("progType", progType);
		}
		
		if (null != category && 0 != category.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.category = :category ";
			map.put("category", category);
		}
		
		if (null != styleId && 0 != styleId) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.styleid = :styleId ";
			map.put("styleId", styleId);
		}
		
		hql += "order by p.inputtime desc";
		cmsLog.debug("查询节目包HQL: " + hql);
		
		List<ProgPackage> list = this.progPackageManager.queryPaper(hql, map);
		cmsLog.debug("查询得出节目包数量: " + list.size());
		for (ProgPackage progPackage : list) {
			if (null == progPackage.getSiteCode() 
					|| 0 == progPackage.getSiteCode().trim().length()) {
				progPackage.setSumfilesize("全");
			} else {
				progPackage.setSumfilesize(this.cmsSiteManager.getSiteNameBySiteCode(
						progPackage.getSiteCode()));
			}
		}
		return list;
	}
	
	/**
	 * 删除一级库小文件
	 * @param progPackageId 节目包ID
	 * @param fileCode 文件类型Code
	 * @return 返回是否允许删除, 或删除是否成功信息.
	 */
	@SuppressWarnings("static-access")
	public boolean removeOnlineFile(String progPackageId, List<String> programFileIds) {
//		List<String> usingProgPackageIds = this.progListDetailManager
//				.queryProgPackageIdsByScheduleDate(DateUtil.getCurrentDateStr(
//						"yyyyMMddHHmmss"));
//		if (usingProgPackageIds.contains(progPackageId)) {
//			return false;
//		}
		
		return this.amsstorageprgrelManager.deleteOnlineFile(
				progPackageId, this.ONLINECLASSCODE, programFileIds);
	}
	
	public List<?> queryPackageProductVos(String progPackageId, String progPackageName,
			String progType, String startTime, String endTime, Long styleId) 
			throws ParseException {
		cmsLog.debug("参数: 节目包ID     progPackageId ==> " + progPackageId);
		cmsLog.debug("参数: 节目包名称         progPackageName ==> " + progPackageName);
		cmsLog.debug("参数: 节目包类型         progType ==> " + progType);
		cmsLog.debug("参数: 录入时间开始    startTime ==> " + startTime);
		cmsLog.debug("参数: 录入时间结束    endTime ==> " + endTime);
		cmsLog.debug("参数: 节目包分类         styleId ==> " + styleId);
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select new com.soaplat.cmsmgr.dto.PackageProductVo(p.productid, p.productname, p.state, p.actors, ps.stylename, p.director, p.inputtime, p.remark, p.remark, p.remark, p.dealstate, p.siteCode, p.remark) from ProgPackage as p, PackStyle as ps where ps.styleid = p.styleid ";
		
		if (null != progPackageId && 0 != progPackageId.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.productid like :progPackageId ";
			params.put("progPackageId", "%" + progPackageId + "%");
		}
		
		if (null != progPackageName && 0 != progPackageName.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.productname like :progPackageName ";
			params.put("progPackageName", "%" + progPackageName + "%");
		}
		
		if (null != progType && 0 != progType.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.progtype = :progType ";
			params.put("progType", progType);
		}
		
		if ((null != startTime && 0 != startTime.trim().length())
				&& (null != endTime && 0 != endTime.trim().length())) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.inputtime between :startTime and :endTime ";
			Date startTimeDate = -1 != startTime.indexOf("-") 
					? DateUtil.parseDate("yyyy-MM-dd", startTime)
							: DateUtil.parseDate("MM/dd/yyyy", startTime);
			Date endTimeDate = -1 != endTime.indexOf("-")
					? DateUtil.parseDate("yyyy-MM-dd", endTime)
							: DateUtil.parseDate("MM/dd/yyyy", endTime);
			params.put("startTime", startTimeDate);
			params.put("endTime", endTimeDate);
		} else {
			if (null != startTime && 0 != startTime.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime >= :startTime ";
				Date startTimeDate = -1 != startTime.indexOf("-") 
						? DateUtil.parseDate("yyyy-MM-dd", startTime)
								: DateUtil.parseDate("MM/dd/yyyy", startTime);
				params.put("startTime", startTimeDate);
			}
			
			if (null != endTime && 0 != endTime.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime <= :endTime ";
				Date endTimeDate = -1 != endTime.indexOf("-")
						? DateUtil.parseDate("yyyy-MM-dd", endTime)
								: DateUtil.parseDate("MM/dd/yyyy", endTime);
				params.put("endTime", endTimeDate);
			}
		}
		
		if (null != styleId && 0 != styleId) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.styleid = :styleId ";
			params.put("styleId", styleId);
		}
		
		hql += "order by p.inputtime desc";
		cmsLog.debug("查询节目包HQL: " + hql);
		
		List<PackageProductVo> packageProductVos = this.progPackageManager
				.queryPackageProductVos(hql, params);
		for (PackageProductVo packageProductVo : packageProductVos) {
			TProductInfo productInfo = this.productInfoManager.queryProductInfoByProgPackageId(packageProductVo.getPackageId());
			if (null != productInfo && null != productInfo.getKeyId()) {
				packageProductVo.setProductId(productInfo.getId());
				packageProductVo.setProductName(productInfo.getProductName());
				packageProductVo.setProductKeyIds(productInfo.getKeyId());
				packageProductVo.setProductEncryptStateCode(productInfo.getEncryptState());
			} else {
				packageProductVo.setProductEncryptStateCode(0L);
			}
			
			if (1 == packageProductVo.getPackageStateCode()
					&& (9 > packageProductVo.getProductEncryptStateCode()
							&& 1 != packageProductVo.getProductEncryptStateCode())
							&& null != packageProductVo.getProductKeyIds()) {
				packageProductVo.setCanEncryptProduct(true);
			} else {
				packageProductVo.setCanEncryptProduct(false);
			}
			
			if (1 == packageProductVo.getPackageStateCode()
					&& (8 < packageProductVo.getProductEncryptStateCode() 
							&& 19 > packageProductVo.getProductEncryptStateCode())) {
				packageProductVo.setCanMigration(true);
			} else {
				packageProductVo.setCanMigration(false);
			}
			
			if (null != packageProductVo.getBrandCode()) {
				packageProductVo.setBrandName(
						this.cmsSiteManager.getSiteNameBySiteCode(packageProductVo.getBrandCode()));
			} else {
				packageProductVo.setBrandName("全");
			}
		}
		
		return packageProductVos;
	}
	
	public List<?> queryPackageProductVos2(String progPackageId, String progPackageName,
			String progType, String startTime, String endTime, Long styleId) 
			throws ParseException {
		cmsLog.debug("参数: 节目包ID     progPackageId ==> " + progPackageId);
		cmsLog.debug("参数: 节目包名称         progPackageName ==> " + progPackageName);
		cmsLog.debug("参数: 节目包类型         progType ==> " + progType);
		cmsLog.debug("参数: 录入时间开始    startTime ==> " + startTime);
		cmsLog.debug("参数: 录入时间结束    endTime ==> " + endTime);
		cmsLog.debug("参数: 节目包分类         styleId ==> " + styleId);
		
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select new com.soaplat.cmsmgr.bean.ProgPackage(p.productid, p.productname, p.siteCode, p.subscriberstime, p.subscriberetime, p.state, p.dealstate, p.inputtime, ps.stylename) from ProgPackage as p, PackStyle as ps where ps.styleid = p.styleid ";
		
		if (null != progPackageId && 0 != progPackageId.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.productid like :progPackageId ";
			params.put("progPackageId", "%" + progPackageId + "%");
		}
		
		if (null != progPackageName && 0 != progPackageName.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.productname like :progPackageName ";
			params.put("progPackageName", "%" + progPackageName + "%");
		}
		
		if (null != progType && 0 != progType.trim().length()) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.progtype = :progType ";
			params.put("progType", progType);
		}
		
		if ((null != startTime && 0 != startTime.trim().length())
				&& (null != endTime && 0 != endTime.trim().length())) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.inputtime between :startTime and :endTime ";
			Date startTimeDate = -1 != startTime.indexOf("-") 
					? DateUtil.parseDate("yyyy-MM-dd", startTime)
							: DateUtil.parseDate("MM/dd/yyyy", startTime);
			Date endTimeDate = -1 != endTime.indexOf("-")
					? DateUtil.parseDate("yyyy-MM-dd", endTime)
							: DateUtil.parseDate("MM/dd/yyyy", endTime);
			params.put("startTime", startTimeDate);
			params.put("endTime", endTimeDate);
		} else {
			if (null != startTime && 0 != startTime.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime >= :startTime ";
				Date startTimeDate = -1 != startTime.indexOf("-") 
						? DateUtil.parseDate("yyyy-MM-dd", startTime)
								: DateUtil.parseDate("MM/dd/yyyy", startTime);
				params.put("startTime", startTimeDate);
			}
			
			if (null != endTime && 0 != endTime.trim().length()) {
				if (hql.contains("where")) {
					hql += "and ";
				} else {
					hql += "where ";
				}
				hql += "p.inputtime <= :endTime ";
				Date endTimeDate = -1 != endTime.indexOf("-")
						? DateUtil.parseDate("yyyy-MM-dd", endTime)
								: DateUtil.parseDate("MM/dd/yyyy", endTime);
				params.put("endTime", endTimeDate);
			}
		}
		
		if (null != styleId && 0 != styleId) {
			if (hql.contains("where")) {
				hql += "and ";
			} else {
				hql += "where ";
			}
			hql += "p.styleid = :styleId ";
			params.put("styleId", styleId);
		}
		
		hql += "order by p.inputtime desc";
		cmsLog.debug("查询节目包HQL: " + hql);
		
		List<ProgPackage> progPackages = this.progPackageManager.queryPaper(hql, params);
		cmsLog.debug("查询得出节目包数量: " + progPackages.size());
		
//		List<PackageProductVo> packageProductVos = this.progPackageManager
//				.queryPackageProductVos(hql, params);
//		for (ProgPackage packageProductVo : progPackages) {
//			if (1 == packageProductVo.getPackageStateCode()
//					&& (9 > packageProductVo.getProductEncryptStateCode()
//							&& 1 != packageProductVo.getProductEncryptStateCode())) {
//				packageProductVo.setCanEncryptProduct(true);
//			} else {
//				packageProductVo.setCanEncryptProduct(false);
//			}
//			
//			if (1 == packageProductVo.getPackageStateCode()
//					&& (8 < packageProductVo.getProductEncryptStateCode() 
//							&& 19 > packageProductVo.getProductEncryptStateCode())) {
//				packageProductVo.setCanMigration(true);
//			} else {
//				packageProductVo.setCanMigration(false);
//			}
//		}
		
		TProductInfo productInfo = null;
		for (ProgPackage progPackage : progPackages) {
			if (null != progPackage.getSiteCode()) {
				progPackage.setSumfilesize(this.cmsSiteManager.getSiteNameBySiteCode(progPackage.getSiteCode()));
			} else {
				progPackage.setSumfilesize("全");
			}
			productInfo = this.productInfoManager.queryProductInfoByProgPackageId(
					progPackage.getProductid());
			if (null != productInfo && null != productInfo.getKeyId()) {
				progPackage.setPtglobalid(productInfo.getKeyId());
//				packageProductVo.setProductId(productInfo.getId());
//				packageProductVo.setProductName(productInfo.getProductName());
//				packageProductVo.setProductKeyIds(productInfo.getKeyId());
//				packageProductVo.setProductEncryptStateCode(productInfo.getEncryptState());
			}
		}
		
		return progPackages;
	}
	
	/**
	 * 初始化节目包使用状态
	 * @param progPackageName 节目包名称
	 * @return
	 */
	public String initDealState(String progPackageName) {
		List<ProgPackage> progPackages = this.progPackageManager.queryProgPackagesByName("%" + progPackageName + "%");
		cmsLog.warn("异常情况重置节目包使用状态: " + progPackageName);
		cmsLog.debug("查询得出节目包数量: " + progPackages.size());
		if (0 < progPackages.size()) {
			for (ProgPackage progPackage : progPackages) {
				if (1 == progPackage.getDealstate()) {
					progPackage.setDealstate(0L);
					try {
						this.progPackageManager.update(progPackage);
						cmsLog.debug("重置节目包[ " + progPackage.getProductname() + " ] - [1 ---> 0]状态成功!");
					} catch (Exception e) {
						cmsLog.error(e);
						cmsLog.warn("重置节目包[ " + progPackage.getProductname() + " ] - [1 ---> 0]状态失败!");
						return "失败!";
					}
				}
			}
			return "成功!";
		}
		return "未找到相关节目包!";
	}
	
	/**
	 * 初始化节目包主文件迁移中状态
	 * @param progPackageName 节目包名称
	 * @return
	 */
	public String initMigrationState(String progPackageName) {
		List<TProductInfo> productInfos = this.productInfoManager.queryProductInfosByProgPackageName("%" + progPackageName + "%");
		cmsLog.warn("异常情况重置节目包迁移中状态: " + progPackageName);
		cmsLog.debug("查询得出产品信息数量: " + productInfos.size());
		if (0 < productInfos.size()) {
			for (TProductInfo productInfo : productInfos) {
				if (11 == productInfo.getEncryptState()) {
					productInfo.setEncryptState(9L);
					try {
						this.progPackageManager.update(productInfo);
						cmsLog.debug("重置节目包迁移中[ " + productInfo.getId() + " ] - [1 ---> 0]状态成功!");
					} catch (Exception e) {
						cmsLog.error(e);
						cmsLog.warn("重置节目包迁移中[ " + productInfo.getId() + " ] - [1 ---> 0]状态失败!");
						return "失败!";
					}
				}
			}
			return "成功!";
		}
		return "未找到相关产品信息!";
	}
	
	/**
	 * 按产品统计节目包强制保留大小
	 * @return List<Object[]> <br/>
	 * object[0]: ProgProduct.KeyName <br/>
	 * object[1]: ProgProduct.KeyId <br/>
	 * object[2]: sum of package size group by ProgProduct.KeyName
	 */
	public List<Object[]> countOfForcedReservesPackageByProduct() {
		return this.progPackageManager.countOfForcedReservesPackageByProduct();
	}
}
