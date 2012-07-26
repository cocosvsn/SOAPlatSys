/**
 * Copyright © 2012, Bunco All Rights Reserved.
 * Project: HotelVodSys
 * cn.sh.sbl.cms.beans.Config.java
 * Create By: Bunco
 * Create Date: 2012-07-26 16:09:55
 */
package cn.sh.sbl.cms.beans;

import java.io.Serializable;

/**
 * @author Bunco
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0
 * @date 2012-07-26 16:09:55
 * @description (配置)
 */
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 名称(主键)
	 */
	private java.lang.String name;	
	/**
	 * 值
	 */
	private java.lang.String value;	
	/**
	 * 说明
	 */
	private java.lang.String comment;	
	/**
	 * 是否有效
	 */
	private boolean valid;	

	public Config() {}
	
	/**
	 * @param name 名称(主键)
	 * @param value 值
	 * @param comment 说明
	 * @param valid 是否有效
	 */
	public Config(String name, String value, String comment, boolean valid) {
		super();
		this.name = name;
		this.value = value;
		this.comment = comment;
		this.valid = valid;
	}

	/**
	 * @return the name 名称(主键)
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * @param 名称(主键) the name to set
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * @return the value 值
	 */
	public java.lang.String getValue() {
		return value;
	}

	/**
	 * @param 值 the value to set
	 */
	public void setValue(java.lang.String value) {
		this.value = value;
	}

	/**
	 * @return the comment 说明
	 */
	public java.lang.String getComment() {
		return comment;
	}

	/**
	 * @param 说明 the comment to set
	 */
	public void setComment(java.lang.String comment) {
		this.comment = comment;
	}

	/**
	 * @return the valid 是否有效
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param 是否有效 the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * 转换为字符串
	 * @author Bunco
	 */
	public String toString(){
		
		return "{ _name=Config:" + ",name=" + name +",value=" + value +",comment=" + comment +",valid=" + valid + "}";
	}
	
	/**
	 * 转换为 JSON 字符串
	 * @author Bunco
	 */
	public String toJson(){
		return "{ _name:'Config'" + ",name:'" + name + "'" +",value:'" + value + "'" +",comment:'" + comment + "'" +",valid:'" + valid + "'}";
	}
}