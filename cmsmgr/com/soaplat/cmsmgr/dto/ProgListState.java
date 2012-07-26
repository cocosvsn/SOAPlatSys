/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.dto;

import java.util.List;
import com.soaplat.cmsmgr.bean.ProgPackage;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-9-9 上午11:57:33
 */
public class ProgListState {
	private String				date;			// 日期
	private int					state;			// 当天的可编辑状态 [0:可编辑,无节目包; 1:可编辑,已有节目包; -1:不可编辑]
	private List<ColumnState>	columnStates;	// 栏目编单状态
	private List<ProgPackage>	progPackages;	// 当天的编单的节目包列表
	
	public ProgListState() { }
	
	public ProgListState(String date) {
		this.date = date;
	}
	
	public ProgListState(String date, int state) {
		this.date = date;
		this.state = state;
	}
	
	/**
	 * @param day 天数
	 * @param state 当天的可编辑状态 [0:可编辑,无节目包; 1:可编辑,已有节目包; -1:不可编辑]
	 * @param progPackages 当天的编单的节目包列表
	 */
	public ProgListState(String date, int state, List<ProgPackage> progPackages) {
		super();
		this.date = date;
		this.state = state;
		this.progPackages = progPackages;
	}

	/**
	 * 日期
	 * @return
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * 日期
	 * @param day
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * 当天的可编辑状态 [0:可编辑,无节目包; 1:可编辑,已有节目包; -1:不可编辑]
	 * @return
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * 当天的可编辑状态 [0:可编辑,无节目包; 1:可编辑,已有节目包; -1:不可编辑]
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	/**
	 * 当天的编单的节目包列表
	 * @return
	 */
	public List<ProgPackage> getProgPackages() {
		return progPackages;
	}
	
	/**
	 * 当天的编单的节目包列表
	 * @param progPackages
	 */
	public void setProgPackages(List<ProgPackage> progPackages) {
		this.progPackages = progPackages;
	}
	
	/**
	 * 栏目编单状态
	 * @param columnStates
	 */
	public void setColumnStates(List<ColumnState> columnStates) {
		this.columnStates = columnStates;
	}
	
	/**
	 * 栏目编单状态
	 * @return
	 */
	public List<ColumnState> getColumnStates() {
		return columnStates;
	}
}
