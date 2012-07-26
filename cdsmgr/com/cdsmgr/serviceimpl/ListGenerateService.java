package com.cdsmgr.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.cbs.cbsmgr.manageriface.IVodSendManager;
import com.cbs.cbsmgr.manageriface.IVodProductManager;
import com.cbs.cbsmgr.manageriface.IVodProgramPackageRelManager;
import com.cbs.cbsmgr.manageriface.IVodSendDetailManager;
import com.soaplat.cmsmgr.manageriface.IProgramFilesManager;
import com.srmmgr.manageriface.IWebServerManager;
import com.srmmgr.manageriface.IWebSVodSRelManager;
import com.srmmgr.manageriface.IStorageVsRelManager;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.transmgr.LoadBalance;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.srmmgr.bean.*;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;   
import org.jdom.JDOMException;   
import org.jdom.output.XMLOutputter;

public class ListGenerateService {
	public IVodSendManager vodSendManager = null; 
	public IVodProductManager vodProductManager = null;
	public IVodProgramPackageRelManager vodProgramPackageRelManager = null;
	public IProgramFilesManager programfilesManager = null;
	public IWebServerManager webserverManager = null;
	public IWebSVodSRelManager websvodsrelManager = null;
	public IStorageVsRelManager storagevsrelManager = null;
	public IAmsStorageDirManager amsstoragedirManager = null;
	public IVodSendDetailManager vodSendDetailManager = null;
	public IProgramInfoManager programinfoManager = null;
	
	public ListGenerateService() {
		vodSendManager=(IVodSendManager)ApplicationContextHolder.webApplicationContext.getBean("vodSendManager");
		vodProductManager=(IVodProductManager)ApplicationContextHolder.webApplicationContext.getBean("vodProductManager");
		vodProgramPackageRelManager=(IVodProgramPackageRelManager)ApplicationContextHolder.webApplicationContext.getBean("vodProgramPackageRelManager");
		programfilesManager=(IProgramFilesManager)ApplicationContextHolder.webApplicationContext.getBean("programfilesManager");
		webserverManager=(IWebServerManager)ApplicationContextHolder.webApplicationContext.getBean("webserverManager");
		websvodsrelManager = (IWebSVodSRelManager)ApplicationContextHolder.webApplicationContext.getBean("websvodsrelManager");
		storagevsrelManager = (IStorageVsRelManager)ApplicationContextHolder.webApplicationContext.getBean("storagevsrelManager");
		amsstoragedirManager = (IAmsStorageDirManager)ApplicationContextHolder.webApplicationContext.getBean("amsstoragedirManager");
		vodSendDetailManager = (IVodSendDetailManager)ApplicationContextHolder.webApplicationContext.getBean("vodSendDetailManager");
		programinfoManager = (IProgramInfoManager)ApplicationContextHolder.webApplicationContext.getBean("programinfoManager");
	}
	
	@SuppressWarnings("rawtypes")
	public List FindSendList(long dealState) {
		return this.vodSendDetailManager.findvodsendandvodsenddetail(dealState);
	}

	
	@SuppressWarnings("rawtypes")
	public List FindPackageId(String propertyName, Object value) {
		return this.vodProductManager.findByProperty(propertyName, value);
	}

	@SuppressWarnings("rawtypes")
	public List FindProgId(String propertyName, Object value) {
		return this.vodProgramPackageRelManager.findByProperty(propertyName,
				value);
	}

	@SuppressWarnings("rawtypes")
	public List FindProgFiles(String programid, String filetypeid) {
		return this.programfilesManager.findbyprogramidfiletype(programid,
				filetypeid);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List Findprogdes(String programid) {
		// get webserver list
		List webserverlist = webserverManager.findAll();
		String WEBSID = "";
		String VODSID = "";
		String stglobalid = "";
		String xmlstring = "";
		List filedeslist = new ArrayList();
		for (int i = 0; i < webserverlist.size(); i++) {
			WebServer ws = new WebServer();
			ws = (WebServer) webserverlist.get(i);
			WEBSID = ws.getWebsid();
			if (WEBSID != null && WEBSID != "") {
				// getvodlist
				List vodidlist = websvodsrelManager.findByProperty("websid",
						WEBSID);
				// 查找storagelist，每个vodserver的存储目录中取一个放置节目

				for (int j = 0; j < vodidlist.size(); j++) {
					WebSVodSRel wvr = new WebSVodSRel();
					wvr = (WebSVodSRel) vodidlist.get(j);
					VODSID = wvr.getVodsid();
					if (VODSID != null && VODSID != "") {
						// 根据vsid获取存储体和dir列表
						List storageanddirlist = amsstoragedirManager
								.findstorageanddirlistbyvsid(VODSID);
						// 将列表发送至负载均衡目录
						Object[] maxdir = LoadBalance
								.rtnStorage(storageanddirlist);

						filedeslist.add(maxdir);
					}
				}

			}
		}
		return filedeslist;
	}

	@SuppressWarnings("rawtypes")
	public void writetransxml(List filesourcelist, List filedeslist)
			throws IOException, JDOMException {
		Element root = new Element("transfer-table");
		Document Doc = new Document(root);
		String sourceip = "";
		String sourceport = "";
		String sourceuserid = "";
		String sourcepwd = "";
		String sourcefilename = "";
		String sourcemethod = "";
		String sourcedir = "";
		String sourcedirid = "";
		String sourcestorageid = "";
		String sourcefileid = "";
		String sourceprogid = "";

		String desip = "";
		String desport = "";
		String desuserid = "";
		String despwd = "";
		String desfilename = "";
		String desmethod = "";
		String desdir = "";
		String desstorageid = "";
		String desstoragedirid = "";

		// 解析source
		if (filesourcelist.size() > 0) {
			Object[] row = (Object[]) filesourcelist.get(0);
			sourceip = (String) row[2];
			// sourceport = (String) row[1];
			sourceuserid = (String) row[4];
			sourcepwd = (String) row[5];
			sourcefilename = (String) row[20];
			sourcemethod = (String) row[3];
			sourcedir = (String) row[13];
			sourcedirid = (String) row[21];
			sourcestorageid = (String) row[0];
			sourceprogid = (String) row[19];
			sourcefileid = (String) row[18];
		} else {
			return;
		}

		if (filedeslist.size() > 0) {
			// 解析filedes
			Object[] filedes = (Object[]) filedeslist.get(0);
			desip = (String) filedes[2];
			// desport = (String) filedes[3];
			desuserid = (String) filedes[4];
			despwd = (String) filedes[5];
			desfilename = (String) filedes[0];
			desmethod = (String) filedes[3];
			desdir = (String) filedes[13];
			desstorageid = (String) filedes[0];
			desstoragedirid = (String) filedes[18];

			Element transfer_entity = new Element("transfer-entity");
			root.addContent(transfer_entity);
			Element source = new Element("source");
			// //source.setAttribute("method", sourcemethod);
			// source.addContent(new Element("sourceip").setText(sourceip));
			// source.addContent(new Element("sourceport").setText(sourceport));
			// source.addContent(new
			// Element("sourceuserid").setText(sourceuserid));
			// source.addContent(new Element("sourcepwd").setText(sourcepwd));
			// source.addContent(new
			// Element("sourcefilename").setText(sourcefilename));
			source.setAttribute("type", sourcemethod);
			source.setAttribute("hostname", sourceip);
			// source.setAttribute("port", sourceport);
			source.setAttribute("username", sourceuserid);
			source.setAttribute("password", sourcepwd);
			source.setAttribute("sourcestorageid", sourcestorageid);
			source.setAttribute("sourcedirid", sourcedirid);

			// source.addContent(new
			// Element("file").setText(sourcedir+"/"+sourcefilename));
			Element file = new Element("file");
			file.setAttribute("sourceprogid", sourceprogid);
			file.setAttribute("sourcefileid", sourcefileid);
			file.setText(sourcedir + "/" + sourcefilename);
			source.addContent(file);

			transfer_entity.addContent(source);

			Element destination = new Element("destination");
			// destination.setAttribute("method", desmethod);
			// destination.addContent(new
			// Element("destinationip").setText(desip));
			// destination.addContent(new
			// Element("destinationport").setText(desport));
			// destination.addContent(new
			// Element("destinationuserid").setText(desuserid));
			// destination.addContent(new
			// Element("destinationpwd").setText(despwd));
			// destination.addContent(new
			// Element("destinationfilename").setText(desfilename));
			destination.setAttribute("type", desmethod);
			destination.setAttribute("hostname", desip);
			// destination.setAttribute("port", desport);
			destination.setAttribute("username", desuserid);
			destination.setAttribute("password", despwd);
			destination.setAttribute("desstorageid", desstorageid);
			destination.setAttribute("desstoragedirid", desstoragedirid);
			destination.addContent(new Element("file").setText(desdir + "/*"));

			transfer_entity.addContent(destination);

		}
		XMLOutputter XMLOut = new XMLOutputter();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String str = sdf.format(new Date());
		// System.out.println(str);

		XMLOut.output(Doc, new FileOutputStream("d:/" + str + ".xml"));
	}

	@SuppressWarnings("rawtypes")
	public List findstoragedirbystorage(String stdirglobalid, String progfileid) {
		List storagedirinfo = amsstoragedirManager.findstoragedirbystorage(
				stdirglobalid, progfileid);
		return storagedirinfo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findstoragedirandprogid(Object progobject, String stclasscode,
			String filecode) {
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
		Object[] storage = amsstoragedirManager
				.findstorageanddirlistbystorageclass(stclasscode, filecode);

		// 返回：Object[]
		// programid
		// strMaxPK
		Object[] idlist = programinfoManager.getProgAndFilesId(progobject);

		List filelist = new ArrayList();
		filelist.add(storage);
		filelist.add(idlist);
		return filelist;
	}
}
