/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.dto;


/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-9-21 上午09:30:23
 */
public class ColumnState {
	private String	columnID;				// 栏目ID
	private String	columnName;				// 栏目名称
	private int		columnState;			// 栏目状态[0:可编单, -1:不可编单]
	
	public ColumnState() {}
	
	/**
	 * @param columnID 栏目ID
	 * @param columnName 栏目名称
	 * @param columnState 栏目状态[0:可编单, -1:不可编单]
	 */
	public ColumnState(String columnID, String columnName, int columnState) {
		ColumnState.this.columnID = columnID;
		ColumnState.this.columnName = columnName;
		ColumnState.this.columnState = columnState;
	}
	
	/**
	 * 栏目ID
	 * @param columnID
	 */
	public void setColumnID(String columnID) {
		this.columnID = columnID;
	}
	
	/**
	 * 栏目ID
	 * @return
	 */
	public String getColumnID() {
		return columnID;
	}
	
	/**
	 * 栏目名称
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	/**
	 * 栏目名称
	 * @return
	 */
	public String getColumnName() {
		return columnName;
	}
	
	/**
	 * 栏目状态[0:可编单, -1:不可编单]
	 * @param columnState
	 */
	public void setColumnState(int columnState) {
		this.columnState = columnState;
	}
	
	/**
	 * 栏目状态[0:可编单, -1:不可编单]
	 * @return
	 */
	public int getColumnState() {
		return columnState;
	}
}
