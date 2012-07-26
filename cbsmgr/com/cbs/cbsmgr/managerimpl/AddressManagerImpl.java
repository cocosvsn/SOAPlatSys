package com.cbs.cbsmgr.managerimpl;

import java.util.List;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.Address;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IAddressManager;

public class AddressManagerImpl implements IAddressManager {

	IBaseDAO baseDAO;
	IGetPK getcbspk;
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		if(object.length > 0)
		{
			for(int i = 0; i < object.length; i++)
			{
				baseDAO.delete(object[i]);
			}
		}
		else
		{
			return;
		}
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		Address address = (Address)baseDAO.getById(Address.class, Long.valueOf(pkid));
		if(address != null)
		{
			baseDAO.delete(address);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List addressList = baseDAO.findAll("Address", "addressId");
		return addressList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List addressList = baseDAO.findByProperty("Address", propertyName, value);
		return addressList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Address address = (Address)baseDAO.getById(Address.class, Long.valueOf(pkid));
		return address;
	}
	
//	public Object getById(Long pkid) {
//		// TODO Auto-generated method stub
//		Address address = (Address)baseDAO.getById(Address.class, pkid);
//		return address;
//	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Address address = (Address)baseDAO.loadById(Address.class, Long.valueOf(pkid));
		return address;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Address address = (Address)object;

//		if(address.getCustomerId() == null
////			|| address.getAddressType() == null
////			|| address.getCountryId() == null
////			|| address.getProvinceId() == null
////			|| address.getCityId() == null
////			|| address.getDistrictId() == null
////			|| address.getReferenceTypeId() == null
//			)
//		{
//			System.out.println("保存失败，输入数据有空值。");
//			return null;
//		}
//		else
//		{
			baseDAO.save(address);
			Long LMaxPK = address.getAddressId();
			System.out.println("保存成功，AddressId：" + LMaxPK.toString());
			return baseDAO.getById(Address.class, LMaxPK);
//		}
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		if (object.length>0)
		{
			for(int i=0;i<object.length;i++)
			{
				this.save(object[i]);
			}
		}
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		baseDAO.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		if (object.length>0)
		{
			for(int i=0;i<object.length;i++)
			{
				this.update(object[i]);
			}
		}
	}

	public void setGetcbspk(IGetPK getcbspk) {
		this.getcbspk = getcbspk;
	}
	
	public void setBaseDAO(IBaseDAO baseDAO) 
	{
		this.baseDAO = baseDAO;
	}

	public List findbyExample(Object exampleentity) {
		List list = baseDAO.findbyExample(exampleentity);
		return list;
	}
}
