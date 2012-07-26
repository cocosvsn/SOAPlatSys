/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.amsmgr.managerimpl;

import java.util.Date;
import java.util.List;
import com.soaplat.amsmgr.bean.AmsStorageClass;
import com.soaplat.amsmgr.manageriface.IAmsStorageClassManager;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;

/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-11 下午07:07:56
 */
public class AmsStorageClassManagerImpl implements IAmsStorageClassManager {
	private IBaseDAO baseDAO;
	private IGetPK getamspk;
	
	/**
	 * 删除多条记录AmsStorageClass
	 * @param object
	 */
	public void delete(Object[] object) {
		if(object.length > 0){
			for(int i = 0; i < object.length; i ++){
				this.baseDAO.delete(object[i]);
			}
		}	
	}

	/**
	 * 根据AmsStorageClass主键ID删除
	 * @param pkid AmsStorageClass主键ID
	 */
	public void deleteById(String pkid) {
		this.baseDAO.delete(this.baseDAO.getById(AmsStorageClass.class, pkid));
	}

	/**
	 * 查询所有AmsStorageClass对象
	 * @return 返回AmsStoragePrgRel对象集合
	 */
	public List<?> findAll() {
		return this.baseDAO.findAll("AmsStorageClass","stclassglobalid");
	}

	/**
	 * 根据对象的属性, 查询该属性符合条件的记录
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return 返回指定属性和值, 符合条件的记录
	 */
	public List<?> findByProperty(String propertyName, Object value) {
		return this.baseDAO.findByProperty("AmsStorageClass", propertyName, value);
	}

	/**
	 * 根据AmsStorageClass主键ID查询
	 * @param pkid AmsStorageClass主键ID
	 * @return AmsStorageClass
	 */
	public Object getById(String pkid) {
		return (AmsStorageClass) this.baseDAO.getById(AmsStorageClass.class, pkid);
	}

	/**
	 * 根据AmsStorageClass主键ID查询, 支持延时加载
	 * @param pkid AmsStorageClass主键ID
	 * @return AmsStorageClass
	 */
	public Object loadById(String pkid) {
		return (AmsStorageClass) this.baseDAO.loadById(AmsStorageClass.class, pkid);
	}

	/**
	 * 保存AmsStorageClass对象
	 * @param object AmsStorageClass对象
	 * @return 持久化后的AmsStorageClass
	 */
	public Object save(Object object) {
		AmsStorageClass storageclass = (AmsStorageClass)object;
		storageclass.setInputtime(new Date());
		String strMaxPK = this.baseDAO.getMaxPropertyValue("AmsStorageClass", "stclassglobalid");
		strMaxPK = this.getamspk.GetTablePK("AmsStorageClass",strMaxPK);
		storageclass.setStclassglobalid(strMaxPK);
		this.baseDAO.save(storageclass);
		return this.baseDAO.getById(AmsStorageClass.class, strMaxPK);
	}

	/**
	 * 修改对象AmsStorageClass属性
	 * @param object 修改后的AmsStorageClass对象
	 */
	public void update(Object object) {
		this.baseDAO.update(object);
	}

	/**
	 * 保存对象AmsStorageClass
	 * @param object AmsStorageClass对象数组
	 */
	public void save(Object[] object) {
		if (object.length > 0){
			for(int i = 0; i < object.length; i ++){
				this.save(object[i]);
			}
		}
	}

	/**
	 * 修改对象AmsStorageClass属性
	 * @param object AmsStorageClass对象数组
	 */
	public void update(Object[] object) {
		if (object.length > 0){
			for(int i = 0; i < object.length; i ++){
				this.update(object[i]);
			}
		}
	}

	/**
	 * 把已经设置某些属性的对象作为模板，查询跟这个对象已有属性值相同的列表
	 * @param exampleentity 模板对象
	 * @return 属性相同的对象集合
	 */
	public List<?> findbyExample(Object exampleentity) {
		return this.baseDAO.findbyExample(exampleentity);
	}
	
	/**
	 * 查询所有有效存储等级
	 * @return
	 */
	public List<?> queryAllValidateStorage() {
		return this.baseDAO.queryByNamedQuery("query.AmsStorageClass.by.validate");
	}
	
	/** ---------------- setter and getter ----------------- **/
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetamspk(IGetPK getamspk) {
		this.getamspk = getamspk;
	}
}
