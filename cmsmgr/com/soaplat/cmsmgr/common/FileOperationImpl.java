package com.soaplat.cmsmgr.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class FileOperationImpl {
	private List<SmbFile> smbFileList = new ArrayList<SmbFile>(0);

	// int型的返回值：0 - 成功 ; 1 - 失败

	private ZipImpl zipopr = null;
	private UnZip1Impl unzipopr = null;
	public static final Logger cmsLog = Logger.getLogger("Cms");

	public FileOperationImpl() {
		zipopr = new ZipImpl();
		unzipopr = new UnZip1Impl();
	}

	// 20100126 10:36
	// String 转 date
	public Date convertStringToDate(String strdate, // 日期字符串
			String strformat // 日期格式 "yyyy-MM-dd HH:mm:ss"
								// "yyyyMMddHHmmssSSSSSS"
	) {
		// cmsLog.debug("Cms -> FileOperationImpl -> convertStringToDate...");
		Date date = null;
		try {
			if (strdate == null || strformat == null
					|| strdate.equalsIgnoreCase("")
					|| strformat.equalsIgnoreCase("")) {
				cmsLog.warn("Cms -> FileOperationImpl -> convertStringToDate，输入参数为空。");
			} else {
				SimpleDateFormat format = new SimpleDateFormat(strformat);
				date = format.parse(strdate);
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> convertStringToDate - 日期转换失败，详细信息："
							+ e.getMessage());
		}
		// cmsLog.debug("Cms -> FileOperationImpl -> convertStringToDate returns.");
		return date;
	}

	// 20100126 10:36
	// String 转 date
	public String convertDateToString(Date date, // 日期字符串
			String strformat // 日期格式 "yyyy-MM-dd HH:mm:ss"
								// "yyyyMMddHHmmssSSSSSS"
	) {
		// cmsLog.debug("Cms -> FileOperationImpl -> convertDateToString...");
		String strdate = "";
		try {
			if (date != null && strformat != null) {
				SimpleDateFormat format = new SimpleDateFormat(strformat);
				strdate = format.format(date);
			} else {
				cmsLog.warn("Cms -> FileOperationImpl -> convertDateToString - 输入参数为空。");
			}
		} catch (Exception e) {
			strdate = "";
			cmsLog.error("Cms -> FileOperationImpl -> convertDateToString - 日期转换失败，详细信息："
							+ e.getMessage());
		}
		// cmsLog.debug("Cms -> FileOperationImpl -> convertDateToString returns.");
		return strdate;
	}

	// 20100118 17:21
	// 检查字符串末字符，用于判断路径格式，以指定字符结束
	public String checkPathFormatRear(String path, // 路径，格式："smb://hc:hc@172.23.19.66/公用"
			char checkword // 结束字符，格式：'/'
	) {
		// cmsLog.debug("Cms -> FileOperationImpl -> checkPathFormatRear...");
		String newPath = "";

		try {
			if (path != null && !path.equalsIgnoreCase("")) {
				if (path.charAt(path.length() - 1) != checkword) {
					path += "/";
				}
				newPath = path;
			} else if (path == null) {
				newPath = "";
			} else {
				newPath = path;
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> checkPathFormatRear - 异常："
							+ e.getMessage());
		}
		// cmsLog.debug("Cms -> FileOperationImpl -> checkPathFormatRear returns.");
		return newPath;
	}

	// 201000202 10:21
	// 检查字符串首字符，用于判断路径格式，以指定字符开始，输出格式：格式："/ceshi1/30分钟_01.ts"
	public String checkPathFormatFirst(String path, // 路径，格式："ceshi1/30分钟_01.ts"
			char checkword // 结束字符，格式：'/'
	) {
		// cmsLog.debug("Cms -> FileOperationImpl -> checkPathFormatFirst...");
		String newPath = "";

		try {
			if (path != null && !path.equalsIgnoreCase("")) {
				if (path.charAt(0) != checkword) {
					path = "/" + path;
				}
				newPath = path;
			} else if (path == null) {
				newPath = "";
			} else {
				newPath = path;
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> checkPathFormatFirst - 异常："
							+ e.getMessage());
		}
		// cmsLog.debug("Cms -> FileOperationImpl -> checkPathFormatFirst returns.");
		return newPath;
	}

	// 20100119 10:28
	// 删除Smb文件
	public int deleteSmbFile(String strFile // 文件路径，格式："smb://hc:hc@172.23.19.66/公用/20100125.xml"
	) {
		// 删除文件
		// cmsLog.debug("Cms -> FileOperationImpl -> deleteSmbFile...");
		int ireturn = -1;
		try {
			if (checkSmbFileExist(strFile)) {
				SmbFile file = new SmbFile(strFile);
				file.delete();
				cmsLog.debug("Cms -> FileOperationImpl -> deleteSmbFile - 删除文件成功 : "
								+ strFile);
			} else {
				cmsLog.debug("Cms -> FileOperationImpl -> deleteSmbFile - 文件不存在，不需要删除 : "
								+ strFile);
			}
			ireturn = 0;
		} catch (IOException ex) {
			ireturn = 1;
			cmsLog.error("Cms -> FileOperationImpl -> deleteSmbFile - 删除文件失败 : "
							+ strFile);
			cmsLog.error("Cms -> FileOperationImpl -> deleteSmbFile - 异常："
					+ ex.getMessage());
		}

		// cmsLog.debug("Cms -> FileOperationImpl -> deleteSmbFile returns.");
		return ireturn;
	}

	// 20100119 10:28
	// 删除本地文件
	public int deleteLocalFile(String strFile // 文件路径，格式："smb://hc:hc@172.23.19.66/公用/20100125.xml"
	) {
		// 删除文件
		// cmsLog.debug("Cms -> FileOperationImpl -> deleteLocalFile...");
		int ireturn = -1;
		try {
			if (checkFileExist(strFile)) {
				File file = new File(strFile);
				file.delete();
				cmsLog.debug("Cms -> FileOperationImpl -> deleteLocalFile - 删除文件成功 : "
								+ strFile);
			} else {
				cmsLog.debug("Cms -> FileOperationImpl -> deleteLocalFile - 文件不存在，不需要删除 : "
								+ strFile);
			}
			ireturn = 0;
		} catch (Exception ex) {
			ireturn = 1;
			cmsLog.error("Cms -> FileOperationImpl -> deleteLocalFile - 删除文件失败 : "
							+ strFile);
			cmsLog.error("Cms -> FileOperationImpl -> deleteLocalFile - 异常："
					+ ex.getMessage());
		}

		// cmsLog.debug("Cms -> FileOperationImpl -> deleteLocalFile returns.");
		return ireturn;
	}

	// 20100120 10:11
	// 检查目录，如果不存在目录，则创建目录。以"/"结束
	public int checkSmbDir(String dir // 目录，"smb://hc:hc@172.23.19.66/公用/"
	) {
		// cmsLog.debug("Cms -> FileOperationImpl -> checkSmbDir...");
		String head = "";
		String checkDir = "";

		head = dir.substring(0, 3);
		if (dir == null || dir.equalsIgnoreCase("")
				|| !head.equalsIgnoreCase("smb")) {
			cmsLog.warn("Cms -> FileOperationImpl -> checkSmbDir - 输入参数为空或输入不是smb协议，不操作。");
			return 1;
		}

		try {
			checkDir = dir.substring(0, dir.lastIndexOf("/") + 1);
			SmbFile file = new SmbFile(checkDir);
			if (!file.exists()) {
				cmsLog.debug(file + "不存在");
				file.mkdirs();
				cmsLog.debug("Cms -> FileOperationImpl -> checkSmbDir - 目录已经创建。"
						+ checkDir);
			} else {
				cmsLog.debug("Cms -> FileOperationImpl -> checkSmbDir - 目录已经存在，不操作。"
								+ checkDir);
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> checkSmbDir - 异常：", e);
			return 1;
		}
		// cmsLog.debug("Cms -> FileOperationImpl -> checkSmbDir returns.");
		return 0;
	}

	// 20100120 10:11
	// 检查目录，如果不存在目录，则创建目录。以"/"结束
	public int checkLocalDir(String dir // 目录，"smb://hc:hc@172.23.19.66/公用/"
	) {
		// cmsLog.debug("Cms -> FileOperationImpl -> checkLocalDir...");
		// String head = "";
		String checkDir = "";
		// String newdir = "";
		dir = dir.replace('\\', '/');

		// head = dir.substring(0, 3);
		if (dir == null || dir.equalsIgnoreCase("")) {
			cmsLog.warn("Cms -> FileOperationImpl -> checkLocalDir - 输入参数为空，不操作。");
			return 1;
		}

		try {
			checkDir = dir.substring(0, dir.lastIndexOf("/") + 1);
			File file = new File(checkDir);
			if (!file.exists()) {
				System.out.println(file + "不存在");
				file.mkdirs();
				cmsLog.debug("Cms -> FileOperationImpl -> checkLocalDir - 目录已经创建。"
								+ checkDir);
			} else {
				cmsLog.debug("Cms -> FileOperationImpl -> checkLocalDir - 目录已经存在，不操作。"
								+ checkDir);
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> checkLocalDir - 异常："
					+ e.getMessage());
			return 1;
		}
		// cmsLog.debug("Cms -> FileOperationImpl -> checkLocalDir returns.");
		return 0;
	}

	// 20100116 17:06
	// 复制文件，从smb到smb
	public int copyFileFromSmbToSmb(String strFileFrom, // 源文件路径，包含文件名和后缀，格式："smb://hc:hc@172.23.19.66/公用/20100125.xml"
			String strFileTo // 目标文件路径，包含文件名和后缀，格式："smb://hc:hc@172.23.19.66/公用/20100125.xml"
	) {
		cmsLog.debug("Cms -> FileOperationImpl -> copyFileFromSmbToSmb...");
		cmsLog.debug("源 - " + strFileFrom);
		cmsLog.debug("目标 - " + strFileTo);
		int ret = -1;
		String head = "";

		if (strFileFrom == null || strFileTo == null
				|| strFileFrom.equalsIgnoreCase("")
				|| strFileTo.equalsIgnoreCase("")) {
			cmsLog.warn("输入参数为空。");
			ret = 1;
			return ret;
		}
		head = strFileFrom.substring(0, 3);
		if (!head.equalsIgnoreCase("smb")) {
			cmsLog.warn("输入参数格式有误。");
			return 1;
		}
		head = strFileTo.substring(0, 3);
		if (!head.equalsIgnoreCase("smb")) {
			cmsLog.warn("输入参数格式有误。");
			return 1;
		}

		try {
			SmbFile fileFrom = new SmbFile(strFileFrom);
			if (!fileFrom.exists()) {
				cmsLog.error("源文件不存在，返回失败。");
				ret = 1;
				return ret;
			}
			
			if (1 > fileFrom.length()) {
				cmsLog.error("源文件[ " + strFileFrom + " ] 大小为0");
				return 1;
			}

			checkSmbDir(strFileTo);

			deleteSmbFile(strFileTo);

			int last = 0;
			Long localreadbytes = Long.valueOf(0);
			byte[] bytes = new byte[1024];
			SmbFile fileTo = new SmbFile(strFileTo);

			SmbFileOutputStream fileStreamOut = new SmbFileOutputStream(fileTo,
					true);
			SmbFileInputStream fileStreamIn = new SmbFileInputStream(fileFrom);

			while ((last = fileStreamIn.read(bytes)) != -1) {
				fileStreamOut.write(bytes, 0, last);
				localreadbytes += last; // 已传输字节数
			}
			fileStreamOut.flush();
			fileStreamOut.close();

			ret = 0;
			cmsLog.info("复制文件成功 : " + strFileFrom + " --> " + strFileTo);
		} catch (IOException ex) {
			ret = 1;
			cmsLog.error("复制文件失败 : " + strFileFrom + " --> " + strFileTo);
			cmsLog.error("Cms -> FileOperationImpl -> copyFileFromSmbToSmb - 异常："
							+ ex.getMessage());
		}

		cmsLog.debug("Cms -> FileOperationImpl -> copyFileFromSmbToSmb returns.");
		return ret;
	}

	// 20100126 17:19
	public int copyFileFromLocalToSmb(String strFileFrom, String strFileTo) {
		cmsLog.debug("Cms -> FileOperationImpl -> copyFileFromLocalToSmb...");
		cmsLog.debug("源 - " + strFileFrom);
		cmsLog.debug("目标 - " + strFileTo);
		int ret = -1;

		if (strFileFrom == null || strFileTo == null
				|| strFileFrom.equalsIgnoreCase("")
				|| strFileTo.equalsIgnoreCase("")) {
			cmsLog.warn("输入参数为空。");
			ret = 1;
			return ret;
		}

		try {
			File fileFrom = new File(strFileFrom);
			if (!fileFrom.exists()) {
				cmsLog.warn("源文件不存在，返回失败。");
				ret = 1;
				return ret;
			}

			checkSmbDir(strFileTo);

			deleteSmbFile(strFileTo);

			int last = 0;
			Long localreadbytes = Long.valueOf(0);
			byte[] bytes = new byte[1024];
			SmbFile fileTo = new SmbFile(strFileTo);

			SmbFileOutputStream fileStreamOut = new SmbFileOutputStream(fileTo,
					true);
			FileInputStream fileStreamIn = new FileInputStream(fileFrom);

			if (!fileFrom.exists()) {
				cmsLog.warn("源文件不存在，返回失败。");
				ret = 1;
				return ret;
			}
			while ((last = fileStreamIn.read(bytes)) != -1) {
				fileStreamOut.write(bytes, 0, last);
				localreadbytes += last; // 已传输字节数
				// long nowProcess = localreadbytes / step; // 计算当前进度
				// if (nowProcess > process)
				// {
				// process = nowProcess;
				// transObj.setFileProcess(process);
				// instance.syncCtrl.start();
				// logger.debug("传输进度：" + process);
				// }
			}
			fileStreamOut.flush();
			fileStreamOut.close();
			fileStreamIn.close();

			ret = 0;
			cmsLog.info("复制文件成功 : " + strFileFrom + "[ " + fileFrom.length() 
					+ " ] --> " + strFileTo + "[ " + fileTo.length() + " ]");
		} catch (IOException ex) {
			ret = 1;
			cmsLog.error("复制文件失败 : " + strFileFrom + " --> " + strFileTo);
			cmsLog.error("Cms -> FileOperationImpl -> copyFileFromLocalToSmb - 异常：", ex);
		}

		cmsLog.debug("Cms -> FileOperationImpl -> copyFileFromLocalToSmb returns.");
		return ret;
	}

	// 20100305 14:52
	public int copyFileFromSmbToLocal(String strFileFrom, String strFileTo) {
		cmsLog.debug("Cms -> FileOperationImpl -> copyFileFromSmbToLocal...");
		cmsLog.debug("源 - " + strFileFrom);
		cmsLog.debug("目标 - " + strFileTo);
		int ret = -1;

		if (strFileFrom == null || strFileTo == null
				|| strFileFrom.equalsIgnoreCase("")
				|| strFileTo.equalsIgnoreCase("")) {
			cmsLog.warn("输入参数为空。");
			ret = 1;
			return ret;
		}

		try {
			int last = 0;
			Long localreadbytes = Long.valueOf(0);
			byte[] bytes = new byte[1024];

			// 检查源文件是否存在
			if (checkSmbFileExist(strFileFrom)) {
				// 检查目标路径是否存在
				cmsLog.debug("检查目标路径...");
				checkLocalDir(strFileTo);

				// 检查目标文件是否存在，如果存在，则删除
				if (checkFileExist(strFileTo)) {
					// 目标文件已经存在，删除旧文件
					cmsLog.debug("目标文件已经存在，删除旧文件...");
					deleteLocalFile(strFileTo);
				}

				SmbFile fileFrom = new SmbFile(strFileFrom); // 源文件
				File fileTo = new File(strFileTo); // 目标文件

				FileOutputStream fileStreamOut = new FileOutputStream(fileTo,
						true); // 输出文件
				SmbFileInputStream fileStreamIn = new SmbFileInputStream(
						fileFrom); // 输入文件

				while ((last = fileStreamIn.read(bytes)) != -1) {
					fileStreamOut.write(bytes, 0, last);
					localreadbytes += last; // 已传输字节数
				}
				fileStreamOut.flush();
				fileStreamOut.close();
				fileStreamIn.close();

				ret = 0;
				cmsLog.info("复制文件成功 : " + strFileFrom + " --> " + strFileTo);
			} else {
				// 源文件不存在
			}
		} catch (IOException ex) {
			ret = 1;
			cmsLog.error("复制文件失败 : " + strFileFrom + " --> " + strFileTo);
			cmsLog.error("Cms -> FileOperationImpl -> copyFileFromSmbToLocal - 异常："
							+ ex.getMessage());
		}

		cmsLog.debug("Cms -> FileOperationImpl -> copyFileFromSmbToLocal returns.");
		return ret;
	}

	// 20100305 14:52
	public int copyFileFromLocalToLocal(String strFileFrom, String strFileTo) {
		cmsLog.debug("Cms -> FileOperationImpl -> copyFileFromLocalToLocal...");
		cmsLog.debug("源 - " + strFileFrom);
		cmsLog.debug("目标 - " + strFileTo);
		int ret = -1;

		if (strFileFrom == null || strFileTo == null
				|| strFileFrom.equalsIgnoreCase("")
				|| strFileTo.equalsIgnoreCase("")) {
			cmsLog.warn("输入参数为空。");
			ret = 1;
			return ret;
		}

		try {
			int last = 0;
			Long localreadbytes = Long.valueOf(0);
			byte[] bytes = new byte[1024];

			// 检查源文件是否存在
			if (checkSmbFileExist(strFileFrom)) {
				// 检查目标路径是否存在
				cmsLog.debug("检查目标路径...");
				checkLocalDir(strFileTo);

				// 检查目标文件是否存在，如果存在，则删除
				if (checkFileExist(strFileTo)) {
					// 目标文件已经存在，删除旧文件
					cmsLog.debug("目标文件已经存在，删除旧文件...");
					deleteLocalFile(strFileTo);
				}

				File fileFrom = new File(strFileFrom); // 源文件
				File fileTo = new File(strFileTo); // 目标文件

				FileOutputStream fileStreamOut = new FileOutputStream(fileTo,
						true); // 输出文件
				FileInputStream fileStreamIn = new FileInputStream(fileFrom); // 输入文件

				while ((last = fileStreamIn.read(bytes)) != -1) {
					fileStreamOut.write(bytes, 0, last);
					localreadbytes += last; // 已传输字节数
				}
				fileStreamOut.flush();
				fileStreamOut.close();

				ret = 0;
				cmsLog.info("复制文件成功 : " + strFileFrom + " --> " + strFileTo);
			} else {
				// 源文件不存在
			}
		} catch (IOException ex) {
			ret = 1;
			cmsLog.error("复制文件失败 : " + strFileFrom + " --> " + strFileTo);
			cmsLog.error("Cms -> FileOperationImpl -> copyFileFromLocalToLocal - 异常："
							+ ex.getMessage());
		}

		cmsLog.debug("Cms -> FileOperationImpl -> copyFileFromLocalToLocal returns.");
		return ret;
	}

	// 20100118 17:27
	// 生成Smb文件，内容是strword
	public int createSmbFile(String strFileName, // 文件生成目标路径，包含文件名和后缀。格式："smb://hc:hc@172.23.19.66/公用/20100125.xml"
			String strWord // 文件内容字符串
	) {
		cmsLog.debug("Cms -> FileOperationImpl -> createSmbFile...");
		int ireturn = -1;
		String head = "";

		if (strFileName == null || strFileName.equalsIgnoreCase("")) {
			cmsLog.warn("输入参数为空。");
			return 1;
		}
		head = strFileName.substring(0, 3);
		if (!head.equalsIgnoreCase("smb")) {
			cmsLog.warn("输入参数格式有误。");
			return 1;
		}

		try {
			checkSmbDir(strFileName);

			deleteSmbFile(strFileName);

			SmbFile file = new SmbFile(strFileName);
			SmbFileOutputStream fileStream = new SmbFileOutputStream(file, true);
			byte[] bytes = new byte[strWord.length()];
			bytes = strWord.getBytes("utf-8");
			fileStream.write(bytes, 0, bytes.length);
			fileStream.flush();
			fileStream.close();
			ireturn = 0;
			cmsLog.info("创建文件成功 :" + strFileName);
		} catch (IOException ex) {
			ireturn = 1;
			cmsLog.error("创建文件失败 :" + strFileName);
			cmsLog.error("Cms -> FileOperationImpl -> createSmbFile - 异常："
					+ ex.getMessage());
		}

		cmsLog.debug("Cms -> FileOperationImpl -> createSmbFile returns.");
		return ireturn;
	}

	// 20100308 16:39
	// 生成Smb文件，内容是strword
	public int createSmbFileGb2312(String strFileName, // 文件生成目标路径，包含文件名和后缀。格式："smb://hc:hc@172.23.19.66/公用/20100125.xml"
			String strWord // 文件内容字符串
	) {
		cmsLog.debug("Cms -> FileOperationImpl -> createSmbFileGb2312...");
		int ireturn = -1;
		String head = "";

		if (strFileName == null || strFileName.equalsIgnoreCase("")) {
			cmsLog.warn("输入参数为空。");
			return 1;
		}
		head = strFileName.substring(0, 3);
		if (!head.equalsIgnoreCase("smb")) {
			cmsLog.warn("输入参数格式有误。");
			return 1;
		}

		try {
			checkSmbDir(strFileName);

			deleteSmbFile(strFileName);

			SmbFile file = new SmbFile(strFileName);
			SmbFileOutputStream fileStream = new SmbFileOutputStream(file, true);
			byte[] bytes = new byte[strWord.length()];
			bytes = strWord.getBytes("gbk");
			fileStream.write(bytes, 0, bytes.length);
			fileStream.flush();
			fileStream.close();

			ireturn = 0;
			cmsLog.info("创建文件成功 :" + strFileName);
		} catch (IOException ex) {
			ireturn = 1;
			cmsLog.error("创建文件失败 :" + strFileName);
			cmsLog.error("Cms -> FileOperationImpl -> createSmbFileGb2312 - 异常："
							+ ex.getMessage());
		}

		cmsLog.debug("Cms -> FileOperationImpl -> createSmbFileGb2312 returns.");
		return ireturn;
	}

	// 20100125 15:39
	// 改文件或文件夹的名
	public int renameSmbFile(String strFile, // 源文件路径，包含文件名和后缀，格式："smb://hc:hc@172.23.19.66/公用/20100125.xml"
			String newName // 文件新名字，格式："b.xml"
	) {
		// 输入参数：
		// 文件夹
		// smb://administrator:123456@10.0.2.7/monitor/1/
		// 2/
		// 文件
		// smb://administrator:123456@10.0.2.7/monitor/1.txt
		// 2.txt

		cmsLog.debug("Cms -> FileOperationImpl -> renameSmbFile...");
		int ireturn = -1;

		// 判断新文件名
		String fullNewFile = "";
		if (newName == null || newName.equalsIgnoreCase("")) {
			cmsLog.warn("新文件名为空。");
			return 1;
		}

		if (strFile.charAt(strFile.length() - 1) == '/') {
			// 目录
			String str = strFile.substring(0, strFile.length() - 1);
			fullNewFile = strFile.substring(0, str.lastIndexOf("/"));
			fullNewFile = checkPathFormatRear(fullNewFile, '/');
			fullNewFile += newName;
			fullNewFile = fullNewFile.replace('\\', '/');
			fullNewFile = checkPathFormatRear(fullNewFile, '/');
		} else {
			// 文件
			fullNewFile = strFile.substring(0, strFile.lastIndexOf("/") + 1);
			fullNewFile += newName;
		}
		try {
			deleteSmbFile(fullNewFile);

			SmbFile file = new SmbFile(strFile);
			SmbFile newfile = new SmbFile(fullNewFile);
			file.renameTo(newfile);

			ireturn = 0;
			cmsLog.info("改文件名成功。源文件/目录名 - " + strFile);
			cmsLog.info("改文件名成功。新文件/目录名 - " + fullNewFile);
		} catch (IOException ex) {
			ireturn = 1;
			cmsLog.error("改文件名失败。源文件/目录名 - " + strFile);
			cmsLog.error("改文件名失败。新文件/目录名 - " + fullNewFile);
			cmsLog.error("Cms -> FileOperationImpl -> renameSmbFile - 异常："
					+ ex.getMessage());
		}

		cmsLog.debug("Cms -> FileOperationImpl -> renameSmbFile returns.");
		return ireturn;
	}

	// 20100125 15:39
	// 改文件或文件夹的名
	public int renameLocalFile(String strFile, // 源文件路径，包含文件名和后缀，格式："C:/公用/20100125.xml"
			String newName // 文件新名字，格式："b.xml"
	) {
		// 输入参数：
		// 文件夹
		// smb://administrator:123456@10.0.2.7/monitor/1/
		// 2/
		// 文件
		// smb://administrator:123456@10.0.2.7/monitor/1.txt
		// 2.txt

		cmsLog.debug("Cms -> FileOperationImpl -> renameLocalFile...");
		int ireturn = -1;

		// 判断新文件名
		String fullNewFile = "";
		if (newName == null || newName.equalsIgnoreCase("")) {
			cmsLog.warn("新文件名为空。");
			return 1;
		}

		strFile = strFile.replace('\\', '/');

		if (strFile.charAt(strFile.length() - 1) == '/') {
			// 目录
			String str = strFile.substring(0, strFile.length() - 1);
			fullNewFile = strFile.substring(0, str.lastIndexOf("/"));
			fullNewFile = checkPathFormatRear(fullNewFile, '/');
			fullNewFile += newName;
			fullNewFile = fullNewFile.replace('\\', '/');
			fullNewFile = checkPathFormatRear(fullNewFile, '/');
		} else {
			// 文件
			fullNewFile = strFile.substring(0, strFile.lastIndexOf("/") + 1);
			fullNewFile += newName;
		}
		try {
			deleteLocalFile(fullNewFile);

			File file = new File(strFile);
			File newfile = new File(fullNewFile);
			file.renameTo(newfile);

			ireturn = 0;
			cmsLog.info("改文件名成功。源文件/目录名 - " + strFile);
			cmsLog.info("改文件名成功。新文件/目录名 - " + fullNewFile);
		} catch (Exception ex) {
			ireturn = 1;
			cmsLog.error("改文件名失败。源文件/目录名 - " + strFile);
			cmsLog.error("改文件名失败。新文件/目录名 - " + fullNewFile);
			cmsLog.error("Cms -> FileOperationImpl -> renameLocalFile - 异常："
					+ ex.getMessage());
		}

		cmsLog.debug("Cms -> FileOperationImpl -> renameLocalFile returns.");
		return ireturn;
	}

	// 20100222 14:09
	// 返回目录下内容，格式："Test/" "PAL.ts"
	public SmbFile[] listSmbFiles(String strPath // 源文件夹路径，格式："smb://hc:hc@172.23.19.66/公用/"
	) {
		cmsLog.debug("Cms -> FileOperationImpl -> listSmbFiles...");
		SmbFile[] list = null;

		try {
			if (strPath != null && !strPath.equalsIgnoreCase("")) {
				strPath = checkPathFormatRear(strPath, '/');

				SmbFile file = new SmbFile(strPath);
				list = file.listFiles();

				cmsLog.debug("获得目录下" + strPath + "内容：");
				if (list != null) {
					for (int i = 0; i < list.length; i++) {
						SmbFile f = list[i];
						cmsLog.debug(f.getName());
					}
				} else {
					cmsLog.debug("无内容。");
				}
			} else {
				cmsLog.warn("输入参数为空。");
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> listSmbFiles - 异常："
					+ e.getMessage());
		}
		cmsLog.debug("Cms -> FileOperationImpl -> listSmbFiles returns.");
		return list;
	}

	// 20100323 14:20
	// 返回目录下内容，格式："Test/" "PAL.ts"
	public File[] listLocalFiles(String strPath // 源文件夹路径，格式："c:/公用/"
	) {
		cmsLog.debug("Cms -> FileOperationImpl -> listLocalFiles...");
		File[] list = null;

		try {
			if (strPath != null && !strPath.equalsIgnoreCase("")) {
				strPath = checkPathFormatRear(strPath, '/');

				File file = new File(strPath);
				list = file.listFiles();

				cmsLog.debug("获得目录下" + strPath + "内容：");
				if (list != null) {
					for (int i = 0; i < list.length; i++) {
						File f = list[i];
						cmsLog.debug(f.getName());
					}
				} else {
					cmsLog.debug("无内容。");
				}
			} else {
				cmsLog.warn("输入参数为空。");
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> listLocalFiles - 异常："
					+ e.getMessage());
		}
		cmsLog.debug("Cms -> FileOperationImpl -> listLocalFiles returns.");
		return list;
	}

	// 20100222 17:24
	// 判断文件是否存在
	public boolean checkSmbFileExist(String filePath) {
		// cmsLog.debug("Cms -> FileOperationImpl -> checkSmbFileExist...");
		boolean exist = false;

		String head = "";
		String checkDir = "";

		head = filePath.substring(0, 3);
		if (filePath == null || filePath.equalsIgnoreCase("")
				|| !head.equalsIgnoreCase("smb")) {
			cmsLog.warn("Cms -> FileOperationImpl -> checkSmbFileExist - 输入参数为空或输入不是smb协议，不操作。");
			exist = false;
		} else {
			SmbFile file;
			try {
				file = new SmbFile(filePath);
				if (file.exists()) {
					exist = true;
				} else {
					exist = false;
				}
			} catch (Exception e) {
				cmsLog.error("Cms -> FileOperationImpl -> checkSmbFileExist - 异常："
								+ e.getMessage());
				exist = false;
			} finally {
				file = null;
			}
		}

		// cmsLog.debug("Cms -> FileOperationImpl -> checkSmbFileExist returns.");
		return exist;
	}

	// 20100222 17:24
	// 判断文件是否存在
	public boolean checkFileExist(String filePath) {
		// cmsLog.debug("Cms -> FileOperationImpl -> checkFileExist...");
		boolean exist = false;

		String head = "";
		String checkDir = "";

		head = filePath.substring(0, 3);
		if (filePath == null || filePath.equalsIgnoreCase("")) {
			cmsLog.warn("Cms -> FileOperationImpl -> checkFileExist - 输入参数为空，不操作。");
			exist = false;
		} else {
			try {
				File file = new File(filePath);
				if (file.exists()) {
					exist = true;
				} else {
					exist = false;
				}
			} catch (Exception e) {
				cmsLog.error("Cms -> FileOperationImpl -> checkFileExist - 异常："
						+ e.getMessage());
				exist = false;
			}
		}

		// cmsLog.debug("Cms -> FileOperationImpl -> checkFileExist returns.");
		return exist;
	}

	// 20100126 9:34
	// xml转字符串
	public String XMLtoStr(Document document) {
		cmsLog.debug("Cms -> FileOperationImpl -> XMLtoStr...");
		String result = null;

		if (document != null) {
			StringWriter strWtr = new StringWriter();
			StreamResult strResult = new StreamResult(strWtr);
			TransformerFactory tfac = TransformerFactory.newInstance();
			try {
				Transformer t = tfac.newTransformer();
				t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.setOutputProperty(OutputKeys.METHOD, "xml");
				t.setOutputProperty(
						"{http://xml.apache.org/xslt}indent-amount", "4");
				t.transform(new DOMSource(document.getDocumentElement()),
						strResult);
			} catch (Exception e) {
				cmsLog.error("Cms -> FileOperationImpl -> XMLtoStr - 异常："
						+ e.getMessage());
			}
			result = strResult.getWriter().toString();
		}
		cmsLog.debug("Cms -> FileOperationImpl -> XMLtoStr returns.");
		return result;
	}

	public String XMLtoStrGb2312(Document document) {
		cmsLog.debug("Cms -> FileOperationImpl -> XMLtoStrGb2312...");
		String result = null;

		if (document != null) {
			StringWriter strWtr = new StringWriter();
			StreamResult strResult = new StreamResult(strWtr);
			TransformerFactory tfac = TransformerFactory.newInstance();
			try {
				Transformer t = tfac.newTransformer();
				t.setOutputProperty(OutputKeys.ENCODING, "gb2312");
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.setOutputProperty(OutputKeys.METHOD, "xml");
				t.setOutputProperty(
						"{http://xml.apache.org/xslt}indent-amount", "4");
				t.transform(new DOMSource(document.getDocumentElement()),
						strResult);
			} catch (Exception e) {
				cmsLog.error("Cms -> FileOperationImpl -> XMLtoStrGb2312 - 异常："
						+ e.getMessage());
			}
			result = strResult.getWriter().toString();
		}
		cmsLog.debug("Cms -> FileOperationImpl -> XMLtoStrGb2312 returns.");
		return result;
	}

	// 20100131 11:04
	// 压缩
	public int zipSmbSinglePath(String filePath, // filePath存放需要压缩的文件或目录的路径，可以是绝对或相对路径，文件要包含扩展名
			String zipFilePath // zipFilePath是压缩后的zip文件的路径，可以是绝对或相对路径，包含文件的扩展名
	) {
		cmsLog.debug("Cms -> FileOperationImpl -> zipSmbSinglePath...");
		int ret = -1;

		try {
			if (filePath == null || filePath.equalsIgnoreCase("")
					|| zipFilePath == null || zipFilePath.equalsIgnoreCase("")) {
				cmsLog
						.warn("Cms -> FileOperationImpl -> zipSmbSinglePath - 输入参数为空，返回。");
				return 1;
			}
			checkSmbDir(zipFilePath);
			deleteSmbFile(zipFilePath);
			String destpath = zipFilePath.substring(0, zipFilePath
					.lastIndexOf("/"));
			checkSmbDir(checkPathFormatRear(destpath, '/'));
			zipopr.zip(filePath, zipFilePath);
			ret = 0;
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> zipSmbSinglePath - 异常："
					+ e.getMessage());
		}
		cmsLog.debug("Cms -> FileOperationImpl -> zipSmbSinglePath returns.");
		return ret;
	}

	// 20100131 11:04
	// 解压缩
	public int unZipSmb(String filePathSmb, // filePath是需要解压的zip文件的路径，可以是绝对或相对路径，包含文件的扩展名
			String dirPathSmb // dirPath是用来放置解压后文件的目录的路径，可以是绝对或相对路径
	) {
		cmsLog.debug("Cms -> FileOperationImpl -> unZipSmb...");
		int ret = -1;

		try {
			if (filePathSmb == null || filePathSmb.equalsIgnoreCase("")
					|| dirPathSmb == null || dirPathSmb.equalsIgnoreCase("")) {
				cmsLog.warn("Cms -> FileOperationImpl -> unZipSmb - 输入参数为空，返回。");
				return 1;
			}
			checkSmbDir(checkPathFormatRear(dirPathSmb, '/'));
			// 解压缩之前，删除原来目录
			String dirPath = dirPathSmb
					+ filePathSmb.substring(filePathSmb.lastIndexOf("/") + 1,
							filePathSmb.lastIndexOf("."));
			dirPath = checkPathFormatRear(dirPath, '/');
			deleteSmbFile(dirPath);

			ret = unzipopr.unZipSmb(filePathSmb, dirPathSmb);
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> unZipSmb - 异常："
					+ e.getMessage());
		}
		cmsLog.debug("Cms -> FileOperationImpl -> unZipSmb returns.");
		return ret;
	}

	public String getRandomId(int length) {
		String contentid = "";
		Date date = null;

		try {
			if (length <= 0) {
				cmsLog.warn("Cms -> FileOperationImpl -> getRandomId - 输入参数有误。");
				contentid = "";
			} else {
				date = new Date();

				contentid = String.valueOf(date.getTime() / 1000);
				contentid = contentid.substring(contentid.length() - length,
						contentid.length());
			}
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> getRandomId - 异常：");
			contentid = "";
		}

		return contentid;
	}

	// 20100422
	// 根据日期，得到编单id， yyyy-MM-dd --> yyyyMMdd000000
	public String convertDateToScheduleDate(String strdate) {
		String scheduledate = "";
		try {
			Date ddate = convertStringToDate(strdate, "yyyy-MM-dd");
			scheduledate = convertDateToString(ddate, "yyyyMMdd000000");
		} catch (Exception e) {
			cmsLog.error("Cms -> FileOperationImpl -> convertDateToScheduleDate，异常："
							+ e.getMessage());
		}
		return scheduledate;
	}

	/**
	 * 获取存储体等级中所有文件信息
	 * 
	 * @param storageClassPath
	 * @return
	 */
	public List<SmbFile> getSmbFiles(String storageClassPath) {
		// cmsLog.info("获取存储体等级中所有文件信息!       getSmbFiles(\"" + storageClassPath
		// + "\"");
		SmbFile[] smbFiles = null;
		try {
			smbFiles = new SmbFile(storageClassPath).listFiles();
			for (SmbFile smbFile : smbFiles) {
				if (smbFile.isDirectory()) {
					getSmbFiles(smbFile.getPath());
				} else if (!smbFile.isHidden()) {
					this.smbFileList.add(smbFile);
				}
			}
		} catch (MalformedURLException e) {
			cmsLog.error("网络连接错误! =============" + e);
		} catch (SmbException e) {
			cmsLog.error("文件读取错误! =============" + e);
		}

		return this.smbFileList;
	}

	public List<SmbFile> getSmbFileList() {
		return this.smbFileList;
	}
	
	/**
	 * 根据节目包状态码, 取得该节目包所处的存储等级
	 * @param state 节目包状态
	 * @return 返回该节目包所处的存储等级
	 */
	public static String getState(long state) {
		CmsConfig cmsConfig = new CmsConfig();
		String systemArea = cmsConfig.getPropertyByName("SystemArea");
		if ("010".equals(systemArea)) {
			if (0 == state) {
				return "导入区";
			} else if (1 == state) {
				return "缓存库";
			} else if (2 == state) {
				return "加扰库";
			} else if (3 == state) {
				return "播发库";
			} else if(-1 == state) {
				return "未导入";
			} else {
				return "未知" + state;
			}
		} else if ("021".equals(systemArea)) {
			if(-1 == state) {
				return "未导入";
			} else if(1 == state) {
				return "缓存库";
			} else if(9 == state) {
				return "北京缓存库";
			} else {
				return "未知" + state;
			}
		} else {
			return "错误";
		}
	}
	
	public static String getMainFileState(long mainFileCode) {
		if(0 == mainFileCode) {
			return "缓存库";
		} else if(1 == mainFileCode) {
			return "加扰中";
		} else if (7 == mainFileCode) {
			return "加扰库, 复制KEY文件到一级库失败";
		} else if (8 == mainFileCode) {
			return "加扰失败";
		} else if (9 == mainFileCode) {
			return "加扰库";
		} else if (11 == mainFileCode) {
			return "迁移中";
		} else if (18 == mainFileCode) {
			return "迁移失败";
		} else if (19 == mainFileCode) {
			return "播发库";
		} else {
			return "未知库";
		}
	}
	
	/**
	 * 根据节目包的使用状态码, 获取当前节目包的使用状态
	 * @param dealState 节目包处理状态Code
	 * @return 返回节目包的使用状态
	 */
 	public static String getDealState(long dealState) {
		if (0 == dealState) {
			return "未处理";
		} else if (1 == dealState) {
			return "处理";
		} else if (8 == dealState) {
			return "失败";
		} else if (9 == dealState) {
			return "成功";
		} else if(-1 == dealState) {
			return "未导入";
		} else if(-2 == dealState) {
			return "待更新";
		} else {
			return "未知" + dealState;
		}
	}

	public static void main(String[] args) {
		FileOperationImpl fileOperationImpl = new FileOperationImpl();
		fileOperationImpl
				.getSmbFiles("smb://administrator:1@10.0.3.30/Online/");
		System.out.println(fileOperationImpl.getSmbFileList().size());
		for (SmbFile smbFile : fileOperationImpl.getSmbFileList()) {
			System.out.println(smbFile.getName() + " : " + smbFile.getPath());
		}
	}
}
