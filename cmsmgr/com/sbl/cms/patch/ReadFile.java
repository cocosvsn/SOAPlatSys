package com.sbl.cms.patch;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ReadFile {

	public static String read(String fileName) {
		try {
			File f = new File(fileName);
			InputStream is = null;
			is = new FileInputStream(f);
			long contentLength = f.length();
			byte[] ba = new byte[(int) contentLength];
			is.read(ba);
			return new String(ba, Charset.forName("gbk"));
		} catch (Exception e) {
			return null;
		}
	}
}
