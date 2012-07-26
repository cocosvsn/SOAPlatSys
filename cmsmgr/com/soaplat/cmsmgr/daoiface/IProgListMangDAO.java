package com.soaplat.cmsmgr.daoiface;

import java.util.List;

import com.soaplat.cmsmgr.bean.ProgListMang;

public interface IProgListMangDAO {
	public void deleteById(String pkid);
	public List findAll();
	public List findByProperty(String propertyName, Object value);
	public ProgListMang getById(String pkid);
	public ProgListMang loadById(String pkid);
	public ProgListMang save(ProgListMang progListMang);
	public void update(ProgListMang progListMang);
	
}
