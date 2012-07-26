package com.soaplat.cmsmgr.managerimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

import com.soaplat.cmsmgr.bean.StyleRel;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IStyleRelManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class StyleRelManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class StyleRelManagerImpl implements IStyleRelManager {
	
	/** The styleReldao. */
	IBaseDAO baseDAO;
	
	/** The getcmspk. */
	IGetPK getcmspk;
	
//	public IProgPackageManager progPackageManager = null;
//	public ProgPackageManager progPackageManager
	
	public StyleRelManagerImpl()
	{
//		progPackageManager = (IProgPackageManager)ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
	}
	
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
	//删除操作
	public void deleteById(String pkid) {

		StyleRel styleRel=(StyleRel)baseDAO.getById(StyleRel.class, pkid);
		if(styleRel!=null){
			baseDAO.delete(styleRel);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List styleRellist=baseDAO.findAll("StyleRel","stylerelid");
		return styleRellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List styleRellist=baseDAO.findByProperty("StyleRel", propertyName, value);
		return styleRellist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		StyleRel styleRel=(StyleRel)baseDAO.getById(StyleRel.class, pkid);
		return styleRel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		StyleRel styleRel=(StyleRel)baseDAO.loadById(StyleRel.class, pkid);
		return styleRel;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		StyleRel styleRel = new StyleRel();
		styleRel = (StyleRel)object;
		
		// 生成新的主键
		// 查找到目前最大的主键值，参数：对象的名字,主键字段的名字
		String strCurMaxPK = baseDAO.getMaxPropertyValue("StyleRel", "stylerelid");
		// 获得新的主键值，参数：对象的名字,当前最大的主键值
		String strMaxPK = getcmspk.GetTablePK("StyleRel", strCurMaxPK);
		// 为对象赋值，主键值
		styleRel.setStylerelid(strMaxPK);
		System.out.println(strMaxPK);
		
		// 保存对象
		baseDAO.save(styleRel);
		
		// 返回新生成的对象
		return baseDAO.getById(StyleRel.class, strMaxPK);
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

	


//	public List addStyleRelAll(StyleRel styleRel) {
//	
//	  baseDAO.save(styleRel);
//	  List list=baseDAO.findAll("StyleRel", "displayorder");
//		return list;
//	}

	
}
