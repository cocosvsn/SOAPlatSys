package com.sbl.cms.patch;

public class JSPatch {
	public static String patch(String str, String type) {
		int index;
		index = str.lastIndexOf(']');
		String rtnString = "";
		boolean flag = "".equals(str.substring(str.indexOf('[') + 1,
				str.lastIndexOf(']')).trim());
		if ("yx_dy".equalsIgnoreCase(type)) {
			rtnString = str.substring(0, index);
			String tmp = ReadFile.read("//patch/data_yx_dy.js");
			tmp = null == tmp ? ReadFile.read("C:/patch/data_yx_dy.js") : tmp;
			if (!flag && null != tmp && !"".equals(tmp)) {
				rtnString += ",";
				rtnString += tmp;
			}
		} else if ("yx_dsj".equalsIgnoreCase(type)) {
			rtnString = str.substring(0, index);
			String tmp = ReadFile.read("//patch/data_yx_dsj.js");
			tmp = null == tmp ? ReadFile.read("C:/patch/data_yx_dsj.js") : tmp;
			if (!flag && null != tmp && !"".equals(tmp)) {
				rtnString += ",";
				rtnString += tmp;
			}
		} else if ("yx_jlp".equalsIgnoreCase(type)) {
			rtnString = str.substring(0, index);
			String tmp = ReadFile.read("//patch/data_yx_jlp.js");
			tmp = null == tmp ? ReadFile.read("C:/patch/data_yx_jlp.js") : tmp;
			if (!flag && null != tmp && !"".equals(tmp)) {
				rtnString += ",";
				rtnString += tmp;
			}
		} else if ("yx_zy".equalsIgnoreCase(type)) {
			rtnString = str.substring(0, index);
			String tmp = ReadFile.read("//patch/data_yx_zy.js");
			tmp = null == tmp ? ReadFile.read("C:/patch/data_yx_jlp.js") : tmp;
			if (!flag && null != tmp && !"".equals(tmp)) {
				rtnString += ",";
				rtnString += tmp;
			}
		} else
			return str;
		return rtnString + str.substring(index);
	}

	public static void main(String[] args) {
		System.out.println(patch("var a = [{}];", "yx_dy"));
	}
}
