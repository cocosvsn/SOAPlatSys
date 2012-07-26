package com.soaplat.sysmgr.serviceiface;

import flex.messaging.io.ArrayCollection;

public interface IFlexBaseService {

	/**
	 * Delete：删除对象
	 * 
	 * @param object
	 *            the object
	 */
	public void delete(ArrayCollection arr);

	/**
	 * Update:更新一组对象
	 * 
	 * @param object
	 *            the object
	 */
	public void update(ArrayCollection arr);

	/**
	 * Save:保存一组对象
	 * 
	 * @param object
	 *            the object
	 */
	public void save(ArrayCollection arr);

}
