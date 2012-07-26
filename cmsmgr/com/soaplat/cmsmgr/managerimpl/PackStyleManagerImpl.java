package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.soaplat.cmsmgr.bean.PackStyle;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IPackStyleManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class PackStyleManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class PackStyleManagerImpl implements IPackStyleManager {
	
	/** The packStyledao. */
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

		PackStyle packStyle=(PackStyle)baseDAO.getById(PackStyle.class, Long.valueOf(pkid));
		if(packStyle!=null){
			//baseDAO.delete(packStyle);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List packStylelist = baseDAO.findAll("PackStyle","styleid");
		return packStylelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List packStylelist = baseDAO.findByProperty("PackStyle", propertyName, value);
		return packStylelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		PackStyle packStyle=(PackStyle)baseDAO.getById(PackStyle.class, Long.valueOf(pkid));
		return packStyle;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		PackStyle packStyle=(PackStyle)baseDAO.loadById(PackStyle.class, Long.valueOf(pkid));
		return packStyle;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		PackStyle packStyle = new PackStyle();
		packStyle = (PackStyle)object;
//		String strCurMaxPK = baseDAO.getMaxPropertyValue("PackStyle", "styleid");
		baseDAO.save(packStyle);
		
		return packStyle;
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
	
	
	public List<PackStyle> getPackStylesByProgTypeAndStyleType(String progtype, Long styletype)
	{
		Map map = new HashMap();
		map.put("progtype", progtype);
		map.put("styletype", styletype);
		List<PackStyle> list = baseDAO.queryByNamedQuery("select_PackStylesByProgTypeAndStyleType", map);
		return list;
	}

	/**
	 * 根据样式名称, 查询指定样式
	 * @param styleName 样式名称
	 * @return PackStyle
	 */
	public PackStyle getPackStyleByName(String styleName) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("styleName", styleName);
		return (PackStyle) this.baseDAO.queryByNamedQuery(
				"query.PackStyle.by.packStyleName", map).get(0);
	}
	
	/**
	 * 根据样式类型查询该类型的所有样式信息
	 * @param styleType 样式类型
	 * @return List<Object[]>
	 * objects[0]:  PackStyle.styleid
	 * objects[1]:  PackStyle.stylename
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getPackaStyleByStyleType(Long styleType) {
		Map<String, Long> map = new HashMap<String, Long>(1);
		map.put("styleType", styleType);
		return this.baseDAO.queryByNamedQuery(
				"query.PackStyle.by.styleType", map);
	}
	
	public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-common.xml",
				"applicationContext-cmsmgrdaobeans.xml",
				"applicationContext-cms-webservice.xml",
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
		IBaseDAO baseDAO = (IBaseDAO) app.getBean("baseDAO");
//		Map<String, String> map = new HashMap<String, String>(1);
//		map.put("styleName", "高清品牌电子报纸节目包样式");
//		PackStyle packStyle = (PackStyle) baseDAO.queryByNamedQuery(
//				"query.PackStyle.by.packStyleName", map).get(0);
//		System.out.println(packStyle);
		
		Map<String, Long> map = new HashMap<String, Long>(1);
		map.put("styleType", 2L);
		List<Object[]> list =  baseDAO.queryByNamedQuery(
				"query.PackStyle.by.styleType", map);
		for (Object[] objects : list) {
			System.out.println(objects[0] + "  :  " + objects[1]);
		}
	}
}
