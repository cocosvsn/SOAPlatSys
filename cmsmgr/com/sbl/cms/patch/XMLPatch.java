package com.sbl.cms.patch;

public class XMLPatch {

	public static String patch(String str) {
		String[] strs = str.split("</Column>");
		int index;
		str = "";
		for (int i = 0; i < strs.length - 1; i++) {
			if (strs[i].indexOf("yx_dsj") > 0) {
				index = strs[i].indexOf("</ReserveProg>");
				String preXml = ReadFile.read("//patch/data_yx_dsj.txt");
				strs[i] = strs[i].substring(0, index)
						+ null == preXml ? 
								ReadFile.read("C:/patch/data_yx_dsj.txt") : preXml
						+ strs[i].substring(index);
			} else if (strs[i].indexOf("yx_jlp") > 0) {
				index = strs[i].indexOf("</ReserveProg>");
				String preXml = ReadFile.read("//patch/data_yx_jlp.txt");
				strs[i] = strs[i].substring(0, index)
						+ null == preXml ? 
								ReadFile.read("C:/patch/data_yx_jlp.txt") : preXml
						+ strs[i].substring(index);
			} else if (strs[i].indexOf("yx_zy") > 0) {
				index = strs[i].indexOf("</ReserveProg>");
				String preXml = ReadFile.read("//patch/data_yx_zy.txt");
				strs[i] = strs[i].substring(0, index)
						+ null == preXml ? 
								ReadFile.read("C:/patch/data_yx_zy.txt") : preXml
						+ strs[i].substring(index);
			} else if (strs[i].indexOf("yx_dy") > 0) {
				index = strs[i].indexOf("</ReserveProg>");
				String preXml = ReadFile.read("//patch/data_yx_dy.txt");
				strs[i] = strs[i].substring(0, index)
						+ null == preXml ? 
								ReadFile.read("C:/patch/data_yx_dy.txt") : preXml
						+ strs[i].substring(index);
			}
		}
		for (int i = 0; i < strs.length - 1; i++) {
			str += strs[i] + "</Column>";
		}
		str += strs[strs.length - 1];

		return str;
	}

	public static void main(String[] args) {
		patch(ReadFile.read("d:/broadcast.xml"));
	}

}
