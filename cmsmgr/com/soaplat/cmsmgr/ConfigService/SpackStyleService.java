package com.soaplat.cmsmgr.ConfigService;

import java.io.UnsupportedEncodingException;
import java.rmi.server.ObjID;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.soaplat.cmsmgr.bean.PackStyle;
import com.soaplat.cmsmgr.bean.PackStyleFileType;
import com.soaplat.cmsmgr.manageriface.IPackStyleFileTypeManager;
import com.soaplat.cmsmgr.manageriface.IPackStyleManager;
import com.soaplat.cmsmgr.manageriface.IStyleRelManager;
import com.soaplat.sysmgr.bean.Dict;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
/***
 *  
 */
public class SpackStyleService implements ISpackStyleService{

	public IPackStyleManager packStyleManager = null;
	public IPackStyleFileTypeManager packStyleFileTypeManager=null;
	public SpackStyleService()
	{
		packStyleManager = (IPackStyleManager)ApplicationContextHolder.webApplicationContext.getBean("packStyleManager");
		packStyleFileTypeManager = (IPackStyleFileTypeManager)ApplicationContextHolder.webApplicationContext.getBean("packStyleFileTypeManager");	
	}


	//测试OK
	public List addPackStyleAll(PackStyle packStyle, ArrayList array) {
     	packStyleManager.save(packStyle);
		Long styleid=packStyle.getStyleid();
		//判断传入的arrylist是否为空. 不为空执行已下操作
		if(array!=null)
		{
			PackStyleFileType PackStyleFile=new PackStyleFileType();	
			Dict dict;
			for(int i=0;i<array.size();i++)
			{	
				dict=(Dict)array.get(i);
			
			PackStyleFile.setFiletypeid(dict.getDictname());
			
			PackStyleFile.setStyleid(styleid);
			packStyleFileTypeManager.save(PackStyleFile);
			}

		}
		List searchlist=packStyleManager.findAll();
		ArrayList<String> xmls = new ArrayList();
		List ret = new ArrayList();
	
		//转型
	  
	    ret.add(searchlist);
	    ret.add(xmls);
		return ret;
		
	}

/***
 * 需要修改
 */
	public List  delPackStyleAll(String pkid) {
		System.out.println(pkid);

		
		List  packStyleFilelist= packStyleFileTypeManager.findByProperty("styleid",Long.valueOf(pkid));
		System.out.println("PackStyleFilelist :  "+packStyleFilelist);
		for(int i=0;i<packStyleFilelist.size();i++){
			PackStyleFileType PackStyleFileType=(PackStyleFileType)packStyleFilelist.get(i);
			packStyleFileTypeManager.delete(new Object[]{PackStyleFileType});
			System.out.println("删除子表成功");
		}
		packStyleManager.deleteById(pkid);
		System.out.println("删除主表成功");
		
		
		List packstyles = packStyleManager.findAll();
		ArrayList<String> xmls = new ArrayList();
		List ret = new ArrayList();
		
	    ret.add(packstyles);
	    ret.add(xmls);
		return ret;
	}
	//测试已成功  全部查出关系表  
	public List searchPackStyleAll() {
		List searchlist=packStyleManager.findAll();
		ArrayList<String> xmls = new ArrayList();
		List ret = new ArrayList();
		
		    ret.add(searchlist);
		    ret.add(xmls);
			return ret;
	}

	
	public List updatePackStyleAll(PackStyle packStyle, ArrayList array) {
	  
		
		System.out.println(array.size());
		packStyleManager.update(packStyle);
	   Long styleid=packStyle.getStyleid();
	   List list=packStyleFileTypeManager.findByProperty("styleid", packStyle.getStyleid());
	
	   for(int i=0;i<list.size();i++)
	   {
		PackStyleFileType packStyleFileType=(PackStyleFileType)list.get(i);
		packStyleFileTypeManager.delete(new Object[]{packStyleFileType});
	   }

		if(array!=null)
		{
			PackStyleFileType PackStyleFile=new PackStyleFileType();	
			Dict dict;
			for(int i=0;i<array.size();i++)
			{	
				dict=(Dict)array.get(i);
			PackStyleFile.setFiletypeid(dict.getDictname());
			
			PackStyleFile.setStyleid(styleid);
			packStyleFileTypeManager.save(PackStyleFile);
			}

		}

	   	List searchlist=packStyleManager.findAll();
	   	
		ArrayList<String> xmls = new ArrayList();
		List ret = new ArrayList();
	     
	   
	    ret.add(searchlist);
	    ret.add(xmls);
		return ret;
	}

	
   
}
