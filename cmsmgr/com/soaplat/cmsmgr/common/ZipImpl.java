package com.soaplat.cmsmgr.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class ZipImpl 
{
	// Zip程序用来将文件压缩为zip文件
	// 压缩时，如果被压缩文件中有名称包含中文的文件或目录
	// 则在WinZip或WinRar中查看压缩后的zip文件时，对应文件或目录的名称显示为乱码
	// 这是Java类库的一个漏洞，压缩文件时对文件和目录的名称的编码方式作了转换
	// 可以通过修改ZipOutputStream类解决这个问题
	
	// 程序启动后
	// （1）选择是压缩还是解压缩zip文件，或是退出程序
	// （2）如果选择压缩
	// 输入要压缩的文件或目录，可以输入多个，文件要包含扩展名
	// 输入压缩文件名，不包含扩展名
	// 显示压缩过程并将压缩过程记入日志文件
	// 压缩完毕转向（1）
	// （3）如果选择解压缩
	// 输入要解压缩的文件，只能输入一个，不包含扩展名
	// 输入放置解压后文件的目录
	// 选择解压缩方式：UnZip1或UnZip2
	// 显示解压缩过程并将解压缩过程记入日志文件
	// 解压缩完毕转向（1）
	// （4）如果选择退出程序，直接退出程序

	// 日志文件格式要求
	// （1）日志文件使用文本文件，以“log”为扩展名
	// （2）日志记录内容包括
	// 程序启动和退出
	// 执行的操作（压缩、解压缩）和操作过程
	// 错误和异常说明
	// （3）程序启动和退出记录以日期时间开头
	// 执行操作（压缩、解压缩）记录以日期时间开头
	// （4）每条日志记录以换行为分隔符
	// 每次程序运行的记录以“一个空行+一行"*"+一个空行”为分隔符
	// 每次操作（压缩、解压缩）的记录以一个空行为分隔符
	
	private static final Logger logger = Logger.getLogger("Cms");
	private static final int BUFFEREDSIZE = 2048;
	
	public void zipFile(File f, int filesParentAbsolutePathLength,
			ZipOutputStream out) throws Exception 
	{
		String filePath = f.getAbsolutePath();
		System.out.println(filePath + "被压缩");
		String zipEntryName = filePath.substring(filesParentAbsolutePathLength);

		if (f.isDirectory()) 
		{
			ZipEntry zipEntry = new ZipEntry(zipEntryName + "/");
			out.putNextEntry(zipEntry);

			File temp[] = f.listFiles();
			int num = temp.length;

			for (int i = 0; i < num; i++)
				zipFile(temp[i], filesParentAbsolutePathLength, out);
		} 
		else 
		{
			ZipEntry zipEntry = new ZipEntry(zipEntryName);
			out.putNextEntry(zipEntry);

			FileInputStream in = new FileInputStream(f);
			byte buf[] = new byte[1024]; // 压缩缓冲区
			int len;

			while ((len = in.read(buf, 0, 1024)) != -1)
				out.write(buf, 0, len);

			in.close();
		}
	}

	public void zipSmbFile(SmbFile f, int filesParentAbsolutePathLength,
			ZipOutputStream out) throws Exception 
	{
//		String filePath = f.getAbsolutePath();
		String filePath = f.getPath();
		System.out.println(filePath + "被压缩");
		String zipEntryName = filePath.substring(filesParentAbsolutePathLength);

		if (f.isDirectory()) 
		{
			ZipEntry zipEntry = new ZipEntry(zipEntryName + "/");
			out.putNextEntry(zipEntry);

			SmbFile temp[] = f.listFiles();
			int num = temp.length;

			for (int i = 0; i < num; i++)
				zipSmbFile(temp[i], filesParentAbsolutePathLength, out);
		} 
		else 
		{
			ZipEntry zipEntry = new ZipEntry(zipEntryName);
			out.putNextEntry(zipEntry);

			SmbFileInputStream in = new SmbFileInputStream(f);
			byte buf[] = new byte[1024]; // 压缩缓冲区
			int len;

			while ((len = in.read(buf, 0, 1024)) != -1)
				out.write(buf, 0, len);

			in.close();
		}
	}
	
	public void zip(
			String filePaths[], 		// filePaths数组存放需要压缩的文件或目录的路径，可以是绝对或相对路径，文件要包含扩展名
			int num, 
			String zipFilePath			// zipFilePath是压缩后的zip文件的路径，可以是绝对或相对路径，包含文件的扩展名
			)
	{
		// filePaths数组存放需要压缩的文件或目录的路径，可以是绝对或相对路径，文件要包含扩展名
		// zipFilePath是压缩后的zip文件的路径，可以是绝对或相对路径，包含文件的扩展名
		
		File files[] = new File[num];
		int i;
		for (i = 0; i < num; i++) 
		{
			files[i] = new File(filePaths[i]);
			filePaths[i] = files[i].getAbsolutePath();
		}

		File zipFile = new File(zipFilePath);

		String str = "";
		for (i = 0; i < num; i++) 
		{
			str += filePaths[i];

			if (i < num - 1)
				str += "、";
		}

		System.out.println("将" + str + "压缩到" + zipFile.getAbsolutePath());

		File parent, temp = null;
		boolean flag = true;

		for (i = 0; i < num; i++) 
		{
			if (!files[i].exists()) 
			{
				System.out.println(filePaths[i] + "不存在");
				flag = false;
			}

			if (i == 0)
				temp = files[i].getAbsoluteFile().getParentFile();
			else 
			{
				parent = files[i].getAbsoluteFile().getParentFile();
				if (!parent.equals(temp)) 
				{
					System.out.println(filePaths[i] + "与" + filePaths[0]
							+ "不在同一目录下");
					flag = false;
				}
			}
		}

		if (flag) 
		{
			String zipFileName = zipFile.getName();
			System.out.println("开始生成" + zipFileName);

			// 如果压缩后的zip文件所在目录尚未创建
			parent = zipFile.getAbsoluteFile().getParentFile();
			if (!parent.exists())
				parent.mkdirs();

			// 对于硬盘某个分区的根目录，getAbsolutePath()方法返回值最后是“/”或“\”（如C:\）
			// 对于非硬盘分区根目录的目录，getAbsolutePath()方法返回值最后是目录名，没有“/”或“\”（如C:\abc）
			// 下面这段代码的作用是：
			// 对于指定的绝对路径字符串，如果其最后一个字符不是“/”或“\”
			// 把它变成最后一个字符是“/”或“\”的绝对路径字符串
			String filesParentAbsolutePath = temp.getAbsolutePath();
			int filesParentAbsolutePathLength = filesParentAbsolutePath
					.length();
			if (!filesParentAbsolutePath.endsWith("/")
					&& !filesParentAbsolutePath.endsWith("\\"))
				filesParentAbsolutePathLength++;

			try 
			{
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
						zipFile));

				for (i = 0; i < num; i++)
					zipFile(files[i], filesParentAbsolutePathLength, out);

				out.close();
				System.out.println(zipFileName + "生成完毕");
			} 
			catch (Exception e) 
			{
				System.out.println(e);
				System.out.println(zipFileName + "生成失败");
			}
		}
	}
	
	public int zipSmb(String filePaths[], // filePaths数组存放需要压缩的文件或目录的路径，可以是绝对或相对路径，文件要包含扩展名
			int num, String zipFilePath // zipFilePath是压缩后的zip文件的路径，可以是绝对或相对路径，包含文件的扩展名
	) {
		// filePaths数组存放需要压缩的文件或目录的路径，可以是绝对或相对路径，文件要包含扩展名
		// zipFilePath是压缩后的zip文件的路径，可以是绝对或相对路径，包含文件的扩展名

		try {
			SmbFile files[] = new SmbFile[num];
			int i;
			for (i = 0; i < num; i++) {
				files[i] = new SmbFile(filePaths[i]);
				filePaths[i] = files[i].getPath();// .getAbsolutePath();
			}
			SmbFile zipFile = new SmbFile(zipFilePath);
			String str = "";
			for (i = 0; i < num; i++) {
				str += filePaths[i];

				if (i < num - 1)
					str += "、";
			}
			System.out.println("将" + str + "压缩到" + zipFile.getPath());// zipFile.getAbsolutePath()
			SmbFile parent, temp = null;
			boolean flag = true;
			for (i = 0; i < num; i++) {
				if (!files[i].exists()) {
					System.out.println(filePaths[i] + "不存在");
					flag = false;
				}

				if (i == 0) {
					temp = new SmbFile(files[i].getParent());
					// temp = files[i].getAbsoluteFile().getParentFile();
				} else {
					// parent = files[i].getAbsoluteFile().getParentFile();
					parent = new SmbFile(files[i].getParent());
					if (!parent.equals(temp)) {
						System.out.println(filePaths[i] + "与" + filePaths[0]
								+ "不在同一目录下");
						flag = false;
					}
				}
			}
			if (flag) {
				String zipFileName = zipFile.getName();
				System.out.println("开始生成" + zipFileName);

				// 如果压缩后的zip文件所在目录尚未创建
				// parent = zipFile.getAbsoluteFile().getParentFile();
				parent = new SmbFile(zipFile.getParent());
				if (!parent.exists())
					parent.mkdirs();

				// 对于硬盘某个分区的根目录，getAbsolutePath()方法返回值最后是“/”或“\”（如C:\）
				// 对于非硬盘分区根目录的目录，getAbsolutePath()方法返回值最后是目录名，没有“/”或“\”（如C:\abc）
				// 下面这段代码的作用是：
				// 对于指定的绝对路径字符串，如果其最后一个字符不是“/”或“\”
				// 把它变成最后一个字符是“/”或“\”的绝对路径字符串
				String filesParentAbsolutePath = temp.getPath();// .getAbsolutePath();
				int filesParentAbsolutePathLength = filesParentAbsolutePath
						.length();
				if (!filesParentAbsolutePath.endsWith("/")
						&& !filesParentAbsolutePath.endsWith("\\")) {
					filesParentAbsolutePathLength++;
				}
				try {
					ZipOutputStream out = new ZipOutputStream(
							new SmbFileOutputStream(zipFile));

					for (i = 0; i < num; i++)
						zipSmbFile(files[i], filesParentAbsolutePathLength, out);

					out.close();
					System.out.println(zipFileName + "生成完毕");
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(zipFileName + "生成失败");
					return 1;
				}
			} else {
				return 1;
			}
		} catch (Exception e) {
			return 1;
		}
		return 0;
	}

	public int zipSmbSinglePath(
			String filePath, 		// filePath存放需要压缩的文件或目录的路径，可以是绝对或相对路径，文件要包含扩展名
			String zipFilePath			// zipFilePath是压缩后的zip文件的路径，可以是绝对或相对路径，包含文件的扩展名
			)
	{
		int ret = -1;
		int num = 1;
		String[] filePaths = new String[1];
		filePaths[0] = filePath;                       
		ret = zipSmb(filePaths, num, zipFilePath);
		return ret;
	}
	
	/**
	 * 压缩共享目录或文件
	 * @param inputFilename	待压缩的目录或文件名
	 * @param zipFilename 压缩后的文件名
	 * @throws IOException
	 */
	public void zip(String inputFilename, String zipFilename)
			throws IOException {
		zip(new SmbFile(inputFilename), zipFilename);
	}

	public void zip(SmbFile inputFile, String zipFilename) throws IOException {
		ZipOutputStream out = new ZipOutputStream(new SmbFileOutputStream(
				zipFilename));

		try {
			logger.debug("待压缩文件或目录名: " + inputFile.getName());
			logger.debug("压缩后的文件名: " + zipFilename);
			zip(inputFile, out, "");
		} catch (IOException e) {
			throw e;
		} finally {
			out.close();
		}
	}
	
	/**
	 * 压缩, 不被外部调用
	 * @param inputFile 待压缩的共享目录或文件
	 * @param out 压缩后的文件名
	 * @param base 基本
	 * @throws IOException
	 */
	private synchronized void zip(SmbFile inputFile, ZipOutputStream out,
			String base) throws IOException {
		logger.debug("当前正在压缩的文件: " + inputFile.getName());
		if (inputFile.isDirectory()) {
			SmbFile[] inputFiles = inputFile.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			//base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < inputFiles.length; i++) {
				zip(inputFiles[i], out, base + inputFiles[i].getName());
			}

		} else {
			if (base.length() > 0) {
				out.putNextEntry(new ZipEntry(base));
			} else {
				out.putNextEntry(new ZipEntry(inputFile.getName()));
			}

			SmbFileInputStream in = new SmbFileInputStream(inputFile);
			try {
				int c;
				byte[] by = new byte[BUFFEREDSIZE];
				while ((c = in.read(by)) != -1) {
					out.write(by, 0, c);
				}
			} catch (IOException e) {
				throw e;
			} finally {
				in.close();
			}
		}
	}
	
	public static void main(String args[]) 
	{
//		new ZipImpl().zip(new String[] {
//				"smb://cms:cms@172.23.19.117/cms/CaOnline/Encrypt/PPRP201008/PPRP20100817145929000250/PRRE20100817142119000843001001/"}, 
//				1, 
//				"smb://cms:cms@172.23.19.117/cms/CaOnline/Encrypt/PPRP201008/PPRP20100817145929000250/PRRE20100817142119000843001001.zip");
		try {
			new ZipImpl().zip(
					"smb://cms:cms@172.23.19.117/cms/CaOnline/Encrypt/PPRP201008/PPRP20100817145929000250/PRRE20100817142119000843001001/", 
					"smb://cms:cms@172.23.19.117/cms/CaOnline/Encrypt/PPRP201008/PPRP20100817145929000250/PRRE20100817142119000843001001.zip");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
