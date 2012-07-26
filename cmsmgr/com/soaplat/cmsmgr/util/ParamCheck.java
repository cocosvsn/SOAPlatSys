package com.soaplat.cmsmgr.util;

import org.springframework.util.Assert;

public class ParamCheck {
	private static String errMsg;
	
	public static String getErrMsg() {
		return errMsg;
	}
	
	/**
	 * 是否为为非空字符, 
	 * @param text 不能为 null 且必须至少包含一个非空格的字符
	 * @param message text验证失败的错误信息!
	 * @returntext不为 null且至少包含一个非空格的字符返回真, 反之为假
	 */
	public static boolean hasText(String text, String message) {
		try {
			Assert.hasText(text, message);
			errMsg = null;
			return true;
		} catch (Exception e) {
			errMsg = e.getMessage();
			return false;
		}
	}

	/**
	 * 验证字符串长度是否不为0
	 * @param text 为 null 或长度为 0 时抛出异常
	 * @param message text验证失败的错误信息!
	 * @return text不为 null并且长度不为 0时返回真, 反之为假
	 */
	public static boolean hasLength(String text, String message) {
		try {
			Assert.hasLength(text, message);
			errMsg = null;
			return true;
		} catch (Exception e) {
			errMsg = e.getMessage();
			return false;
		}
	}
	
	/**
	 * 判断字符串长度是否小于最大长度
	 * @param text 待判断字符串
	 * @param max 最大长度
	 * @param message text验证失败的错误信息!
	 * @return 字符串长度小于最大长度则返回真, 否则返回假
	 */
	public static boolean lessThenLength(String text, int max, String message) {
		if (hasLength(text, message) && text.length() < max)
			return true;
		errMsg = message;
		return false;
	}
	
	/**
	 * 判断字符串长度是否小于等于最大长度
	 * @param text 待判断字符串
	 * @param max 最大长度
	 * @param message text验证失败的错误信息!
	 * @return 字符串长度小于等于最大长度则返回真, 否则返回假
	 */
	public static boolean lessThenOrEqualToLength(String text, int max, String message) {
		return lessThenLength(text, max + 1, message);
	}
	
	/**
	 * 判断字符串长度是否大于最小长度
	 * @param text 待判断字符串
	 * @param min 最小长度
	 * @param message text验证失败的错误信息!
	 * @return 字符串长度大于最小长度则返回真, 否则返回假
	 */
	public static boolean greatThenLength(String text, int min, String message) {
		if (hasLength(text, message) && text.length() > min) {
			return true;
		}
		errMsg = message;
		return false;
	}
	
	/**
	 * 判断字符串长度是否大于等于最小长度
	 * @param text 待判断字符串
	 * @param min 最小长度
	 * @param message text验证失败的错误信息!
	 * @return 字符串长度大于等于最小长度则返回真, 否则返回假
	 */
	public static boolean greatThenOrEqualToLength(String text, int min, String message) {
		return lessThenLength(text, min + 1, message);
	}
	
	/**
	 * 判断字符串长度是否大于等于最小长度并且小于等于最大长度
	 * @param text 待判断字符串
	 * @param min 最小长度
	 * @param max 最大长度
	 * @param message text验证失败的错误信息!
	 * @return 字符串长度大于等于最小长度并且小于等于最大长度则返回真, 否则返回假
	 */
	public static boolean betweenLength(String text, int min, int max, String message) {
		return greatThenOrEqualToLength(text, min, message) 
				&& lessThenOrEqualToLength(text, max, message);
	}
	
	public static void main(String[] args) {
		if (!hasText("      ", "长度不合法!"));
			System.out.println(errMsg);
	}
}
