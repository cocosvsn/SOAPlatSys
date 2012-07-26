/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.amsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.amsmgr.bean.AmsStorageDir;
import com.soaplat.amsmgr.manageriface.IAmsStorageDirManager;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.transmgr.LoadBalance;


/**
 * Title 		: 
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-11 下午07:07:56
 */
public class AmsStorageDirManagerImpl implements IAmsStorageDirManager {
	private IBaseDAO baseDAO;
	private IGetPK getamspk;
	
	/**
	 * 删除AmsStorageDir对象, 单个对象删除
	 * @param object 待删除AmsStorageDir对象数组
	 */
	public void delete(Object[] object) {
		if(object.length > 0) {
			for(int i = 0; i < object.length; i ++) {
				baseDAO.delete(object[i]);
			}
		}	
	}

	/**
	 * 根据AmsStorageDir对象主键ID删除
	 * @param pkid AmsStorageDir主键ID
	 */
	public void deleteById(String pkid) {
		this.baseDAO.delete(this.baseDAO.getById(AmsStorageDir.class, pkid));
	}

	/**
	 * 查询所有AmsStorageDir对象
	 * @return 返回AmsStorageDir对象集合
	 */
	public List<?> findAll() {
		return this.baseDAO.findAll("AmsStorageDir","stdirglobalid");
	}

	/**
	 * 根据AmsStorageDir对象指定属性名和属性值, 查询符合条件的对象集合
	 * @param propertyName AmsStorageDir 对象属性名
	 * @param value AmsStorageDir 对象属性值
	 * @return 返回AmsStorageDir对象集合
	 */
	public List<?> findByProperty(String propertyName, Object value) {
		return this.baseDAO.findByProperty("AmsStorageDir", propertyName, value);
	}

	/**
	 * 根据AmsStorageDir对象主键ID, 查询AmsStorageDir对象
	 * @param pkid AmsStorageDir主键ID
	 * @return 返回AmsStorageDir对象
	 */
	public Object getById(String pkid) {
		return (AmsStorageDir) this.baseDAO.getById(AmsStorageDir.class, pkid);
	}

	/**
	 * 根据AmsStorageDir对象主键ID, 查询AmsStorageDir对象, 支持延时加载
	 * @param pkid AmsStorageDir主键ID
	 * @return 返回AmsStorageDir对象
	 */
	public Object loadById(String pkid) {
		return (AmsStorageDir) this.baseDAO.loadById(AmsStorageDir.class, pkid);
	}

	/**
	 * 保存AmsStorageDir对象, 单个AmsStorageDir对象保存
	 * @param object 待持久化的AmsStorageDir对象
	 * @return 返回持久化成功后的AmsStorageDir对象
	 */
	public Object save(Object object) {
		AmsStorageDir storagedir = (AmsStorageDir) object;
		storagedir.setInputtime(new Date());
		String strMaxPK = this.baseDAO.getMaxPropertyValue("AmsStorageDir", "stdirglobalid");
		strMaxPK = this.getamspk.GetTablePK("AmsStorageDir",strMaxPK);
		storagedir.setStdirglobalid(strMaxPK);
		this.baseDAO.save(storagedir);
		return this.baseDAO.getById(AmsStorageDir.class, strMaxPK);
	}

	/**
	 * 修改AmsStorageDir对象属性, 单个对象修改
	 * @param object 属性修改后的AmsStorageDir对象
	 */
	public void update(Object object) {
		this.baseDAO.update(object);
	}


	/**
	 * 保存AmsStorageDir对象, 多个AmsStorageDir对象保存
	 * @param object 待持久化的AmsStorageDir对象数组
	 */
	public void save(Object[] object) {
		if (object.length > 0) {
			for(int i = 0; i < object.length; i++) {
				this.save(object[i]);
			}
		}
	}

	/**
	 * 修改AmsStorageDir对象属性, 多个对象修改
	 * @param object 属性修改后的AmsStorageDir对象数组
	 */
	public void update(Object[] object) {
		if (object.length > 0) {
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
	 * 
	 * @param stdirglobalid
	 * @param progfileid
	 * @return 返回List<Object[22]>
	 * object[0]: AmsStorage.stglobalid
	 * object[1]: AmsStorage.storagename
	 * object[2]: AmsStorage.storageip
	 * object[3]: AmsStorage.storageaccstype
	 * object[4]: AmsStorage.loginname
	 * object[5]: AmsStorage.loginpwd
	 * object[6]: AmsStorage.mappath
	 * object[7]: AmsStorage.maploginuid
	 * object[8]: AmsStorage.maploginpwd
	 * object[9]: AmsStorage.maplogindisk
	 * object[10]: AmsStorage.totalcap
	 * object[11]: AmsStorage.districtid
	 * object[12]: AmsStorage.storagevalid
	 * object[13]: AmsStorageDir.storagedirname
	 * object[14]: AmsStorageDir.dirtotalcap
	 * object[15]: AmsStorageDir.diralarmcap
	 * object[16]: AmsStorageDir.dirfreecap
	 * object[17]: AmsStorageDir.dirvalid
	 * object[18]: ProgramFiles.progfileid 
	 * object[19]: ProgramFiles.programid
	 * object[20]: ProgramFiles.filename
	 * object[21]: AmsStorageDir.stdirglobalid
	 */
	public List<?> findstoragedirbystorage(String stdirglobalid,String progfileid) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("stdirglobalid", stdirglobalid);
		map.put("progfileid", progfileid);
		
		return this.baseDAO.queryByNamedQuery("select_storagedirbystorage",map);
	}
	
	/**
	 * 
	 * @param vodsid 
	 * @return 返回List<Object[19]>
	 * object[0]: AmsStorage.stglobalid
	 * object[1]: AmsStorage.storagename
	 * object[2]: AmsStorage.storageip
	 * object[3]: AmsStorage.storageaccstype
	 * object[4]: AmsStorage.loginname
	 * object[5]: AmsStorage.loginpwd
	 * object[6]: AmsStorage.mappath
	 * object[7]: AmsStorage.maploginuid
	 * object[8]: AmsStorage.maploginpwd
	 * object[9]: AmsStorage.maplogindisk
	 * object[10]: AmsStorage.totalcap
	 * object[11]: AmsStorage.districtid
	 * object[12]: AmsStorage.storagevalid
	 * object[13]: AmsStorageDir.storagedirname
	 * object[14]: AmsStorageDir.dirtotalcap
	 * object[15]: AmsStorageDir.diralarmcap
	 * object[16]: AmsStorageDir.dirfreecap
	 * object[17]: AmsStorageDir.dirvalid,
	 * object[18]: AmsStorageDir.stdirglobalid 
	 */
	public List<?> findstorageanddirlistbyvsid(String vodsid) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("vodsid", vodsid);
		
		return this.baseDAO.queryByNamedQuery("select_storageanddirlistbyvsid",map);
	}
	
	/**
	 * 
	 * @param stclasscode 存储等级Code
	 * @param filecode 文件Code
	 * @return Object[17]
	 * object[0]:  AmsStorage.stglobalid
	 * object[1]:  AmsStorage.storagename
	 * object[2]:  AmsStorage.storageip
	 * object[3]:  AmsStorage.storageaccstype
	 * object[4]:  AmsStorage.loginname
	 * object[5]:  AmsStorage.loginpwd
	 * object[6]:  AmsStorage.mappath
	 * object[7]:  AmsStorage.maploginuid
	 * object[8]:  AmsStorage.maploginpwd
	 * object[9]:  AmsStorage.maplogindisk
	 * object[10]: AmsStorage.totalcap
	 * object[11]: AmsStorage.districtid
	 * object[12]: AmsStorage.storagevalid
	 * object[13]: AmsStorageDir.storagedirname
	 * object[14]: AmsStorageDir.dirtotalcap
	 * object[15]: AmsStorageDir.diralarmcap
	 * object[16]: AmsStorageDir.dirfreecap
	 */
	public Object[] findstorageanddirlistbystorageclass(String stclasscode,String filecode) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("stclasscode", stclasscode);
		map.put("filecode", filecode);
		
		List<?> list=baseDAO.queryByNamedQuery("select_storageanddirlistbystorageclass",map);
		return LoadBalance.rtnStorage(list);
	}
	
	/**
	 * 根据存储体等级Code 和 fileCode, 查存储体和目录.
	 * @param stclasscode 存储体等级Code
	 * @param filecode 文件Code
	 * @return 返回List<Object[3]>
	 * object[0]: AmsStorage
	 * object[1]: AmsStorageDir
	 * object[2]: AmsStorageClass
	 */
	public List<?> getStorageStoragedirsByStclasscodeFilecode(
				String stclasscode,
				String filecode
	) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put("stclasscode", stclasscode);
		map.put("filecode", filecode);
		
		return this.baseDAO.queryByNamedQuery("select_StorageStoragedirsByStclasscodeFilecode",map);
	}
	
	/** ---------------- setter and getter ----------------- **/
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetamspk(IGetPK getamspk) {
		this.getamspk = getamspk;
	}
}
