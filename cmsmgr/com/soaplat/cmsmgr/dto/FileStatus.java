/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.dto;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2011-1-18 下午05:27:35
 */
public enum FileStatus {
	缓存库(1L),
	加扰库(9L),
	加扰中(1L),
	播发库(3L);
	private long value;
	
	private FileStatus(long value) {
		this.value = value;
	}
	
	public static void main(String[] args) {
		System.out.println(FileStatus.valueOf("缓存库").value);
	}
}
