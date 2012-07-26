package com.soaplat.cmsmgr.ConfigService;

import java.util.List;

import com.soaplat.cmsmgr.bean.StyleRel;

public interface IStyleRelService {
	public List addStyleRelAll(StyleRel styleRel);
	public List updateStyleRelAll(StyleRel styleRel);
	public List deleteStyleRelAll(String id);
	public List searchStyleRelAll();
}
