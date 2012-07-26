package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.Price;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IPriceManager;

public class PriceManagerImpl implements IPriceManager {

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
		Price price = (Price)baseDAO.getById(Price.class, Long.valueOf(pkid));
		if(price != null)
		{
			baseDAO.delete(price);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List priceList = baseDAO.findAll("Price", "priceId");
		return priceList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List priceList = baseDAO.findByProperty("Price", propertyName, value);
		return priceList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Price price = (Price)baseDAO.getById(Price.class, Long.valueOf(pkid));
		return price;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Price price = (Price)baseDAO.loadById(Price.class, Long.valueOf(pkid));
		return price;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Price price = new Price();
		price = (Price)object;
		baseDAO.save(price);
		Long LCurPk = price.getPriceId();
		return baseDAO.getById(Price.class, LCurPk);
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

	public void setGetcbspk(IGetPK getcbspk) 
	{
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
	
	public List getAllPrices()
	{
		Map map = new HashMap();
		List prices = baseDAO.queryByNamedQuery("select_allPrices", map);
		return prices;
	}
}
