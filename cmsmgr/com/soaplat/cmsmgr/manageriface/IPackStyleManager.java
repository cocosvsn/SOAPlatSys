package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.cmsmgr.bean.PackStyle;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IPackStyleManager extends IBaseManager {

	public List<PackStyle> getPackStylesByProgTypeAndStyleType(String progtype, Long styletype);
	
	/**
	 * 根据样式名称, 查询指定样式
	 * @param styleName 样式名称
	 * @return PackStyle
	 */
	public PackStyle getPackStyleByName(String styleName);
	
	/**
	 * 根据样式类型查询该类型的所有样式信息
	 * @param styleType 样式类型
	 * @return List<Object[]>
	 * objects[0]:  PackStyle.styleid
	 * objects[1]:  PackStyle.stylename
	 */
	public List<Object[]> getPackaStyleByStyleType(Long styleType);
}
