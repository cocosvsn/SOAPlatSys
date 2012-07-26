/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.bean;

import java.io.Serializable;

/**
 * 文件样式类
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-9-27 上午09:43:15
 */
public class FileStyle implements Serializable {
	private long	id;				// 主键ID, 自增
	private long	styleCode;		// 
	private long	styleId;		// 节目包样式编号
	private String	description;	// 描述( 说明 )
	private String	fileTypeId;		// 文件类型
	private String	validTag;		// 有效标识
	
	public FileStyle() { }
	
	/**
	 * @param styleCode
	 * @param styleId		节目包样式编号
	 * @param fileTypeId	文件类型
	 */
	public FileStyle(long styleCode, long styleId, String fileTypeId) {
		super();
		this.styleCode = styleCode;
		this.styleId = styleId;
		this.fileTypeId = fileTypeId;
		this.validTag = "Y";
	}

	/**
	 * @param id			主键ID, 自增
	 * @param styleCode
	 * @param styleId		节目包样式编号
	 * @param description	描述( 说明 )
	 * @param fileTypeId	文件类型
	 * @param validTag		有效标识
	 */
	public FileStyle(long id, long styleCode, long styleId, String description,
			String fileTypeId, String validTag) {
		super();
		this.id = id;
		this.styleCode = styleCode;
		this.styleId = styleId;
		this.description = description;
		this.fileTypeId = fileTypeId;
		this.validTag = validTag;
	}

	/**
	 * 主键ID, 自增
	 * @return
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * 主键ID, 自增
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getStyleCode() {
		return styleCode;
	}
	
	/**
	 * 
	 * @param styleCode
	 */
	public void setStyleCode(long styleCode) {
		this.styleCode = styleCode;
	}
	
	/**
	 * 节目包样式编号
	 * @return
	 */
	public long getStyleId() {
		return styleId;
	}
	
	/**
	 * 节目包样式编号
	 * @param styleId
	 */
	public void setStyleId(long styleId) {
		this.styleId = styleId;
	}
	
	/**
	 * 描述( 说明 )
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 描述( 说明 )
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 文件类型
	 * @return
	 */
	public String getFileTypeId() {
		return fileTypeId;
	}
	
	/**
	 * 文件类型
	 * @param fileTypeId
	 */
	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}
	
	/**
	 * 有效标识
	 * @return
	 */
	public String getValidTag() {
		return validTag;
	}
	
	/**
	 * 有效标识
	 * @param validTag
	 */
	public void setValidTag(String validTag) {
		this.validTag = validTag;
	}
}
