package com.soaplat.cmsmgr.ConfigService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.soaplat.cmsmgr.bean.StyleRel;
import com.soaplat.cmsmgr.manageriface.IProgTypeManager;
import com.soaplat.cmsmgr.manageriface.IStyleRelManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;


public class StyleRelService implements IStyleRelService {
	//构造注入
	public IStyleRelManager styleRelManager = null;
	public StyleRelService()
	{
		styleRelManager = (IStyleRelManager)ApplicationContextHolder.webApplicationContext.getBean("styleRelManager");
	}
	
	public List addStyleRelAll(StyleRel styleRel) {
			styleRel.setInputtime(new Date()); 
			styleRelManager.save(styleRel);
			List addlist=styleRelManager.findAll();	
		return addlist;
	}

	public List updateStyleRelAll(StyleRel styleRel) { 
		System.out.println("styrel:"+styleRel.getAtag());
		
		styleRelManager.update(styleRel);
		List updatelist=styleRelManager.findAll();	
		return updatelist;
	}
	//OK

	public List deleteStyleRelAll(String id) {
	   styleRelManager.deleteById(id);
	   List deletelist=styleRelManager.findAll();
	   return deletelist;
	 }
	//Ok
	public List searchStyleRelAll()
	{	List searchlist=styleRelManager.findAll();
		System.out.println("list  size:  "+searchlist.size());
		return searchlist;
	}
	
}
