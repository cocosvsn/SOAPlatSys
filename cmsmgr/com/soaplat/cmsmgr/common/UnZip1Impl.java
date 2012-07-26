package com.soaplat.cmsmgr.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class UnZip1Impl 
{
	// UnZip1和UnZip2两个程序使用了两种方式解压zip文件
	// 解压缩时
	// zip文件本身的文件名可以包含中文，zip文件内的文件的内容也可以包含中文
	// 但在zip文件内不能有名称包含中文的文件和目录，否则解压程序会报错
	// 这是Java类库的一个漏洞，对zip文件内的文件和目录的名称的解读方式有问题
	// 可以通过修改ZipInputStream类解决这个问题
	
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
	
	
	
	public void unZip(
			String filePath, 		// filePath是需要解压的zip文件的路径，可以是绝对或相对路径，包含文件的扩展名
			String dirPath			// dirPath是用来放置解压后文件的目录的路径，可以是绝对或相对路径
			)
	{
		File f = new File(filePath);
		File dir = new File(dirPath);
		filePath = f.getAbsolutePath();

		System.out.println("将" + filePath + "解压到" + dir.getAbsolutePath());

		if (!f.isFile())
			System.out.println(filePath + "文件不存在");
		else {
			String zipFileName = f.getName();
			System.out.println(zipFileName + "开始解压");

			if (!dir.isDirectory())
				dir.mkdirs(); // 创建放置解压后文件的目录

			try {
				ZipInputStream in = new ZipInputStream(new FileInputStream(f));
				FileOutputStream out;
				byte buf[] = new byte[1024]; // 解压缓冲区
				ZipEntry zipEntry; // 获取压缩文件中的文件或目录
				String zipEntryName;
				File file, parent;
				int len;

				while ((zipEntry = in.getNextEntry()) != null) {
					zipEntryName = zipEntry.getName();
					System.out.println(zipFileName + "/" + zipEntryName + "被解压");

					if (zipEntry.isDirectory()) // 如果读出的是目录
						new File(dir, zipEntryName).mkdirs();
					else {
						file = new File(dir, zipEntryName);

						// 如果读出的这个文件所在目录尚未创建
						parent = file.getParentFile();
						if (!parent.exists())
							parent.mkdirs();

						// 如果该文件已存在，直接覆盖
						// 如果该文件不存在，自动创建
						out = new FileOutputStream(file);

						while ((len = in.read(buf, 0, 1024)) != -1)
							out.write(buf, 0, len);

						out.close();
					}
				}

				in.close();
				System.out.println(zipFileName + "解压完毕");
			} catch (Exception e) {
				System.out.println(e);
				System.out.println(zipFileName + "解压失败");
			}
		}
	}
	
	public int unZipSmb(
			String filePathSmb, 		// filePath是需要解压的zip文件的路径，可以是绝对或相对路径，包含文件的扩展名
			String dirPathSmb			// dirPath是用来放置解压后文件的目录的路径，可以是绝对或相对路径
			)
	{
		// 压缩包中的文件名，不能出现中文字符
		try 
		{
			SmbFile f = new SmbFile(filePathSmb);
			SmbFile dir = new SmbFile(dirPathSmb);
			//		filePathSmb = f.getAbsolutePath();
			//		System.out.println("将" + filePathSmb + "解压到" + dir.getAbsolutePath());
			System.out.println("将" + filePathSmb + "解压到" + dir);
			if (!f.isFile())
			{
				System.out.println(filePathSmb + "文件不存在");
				return 1;
			}
			else 
			{
				String zipFileName = f.getName();
				System.out.println(zipFileName + "开始解压");

				if (!dir.isDirectory())
					dir.mkdirs(); // 创建放置解压后文件的目录

				try 
				{
					ZipInputStream in = new ZipInputStream(new SmbFileInputStream(f));
					SmbFileOutputStream out;
					byte buf[] = new byte[1024]; // 解压缓冲区
					ZipEntry zipEntry; // 获取压缩文件中的文件或目录
					String zipEntryName;
					SmbFile file, parent;
					int len;

					while ((zipEntry = in.getNextEntry()) != null) 
					{
						zipEntryName = zipEntry.getName();
						System.out.println(zipFileName + "/" + zipEntryName + "被解压");

						if (zipEntry.isDirectory()) // 如果读出的是目录
							new SmbFile(dir, zipEntryName).mkdirs();
						else 
						{
							file = new SmbFile(dir, zipEntryName);

							// 如果读出的这个文件所在目录尚未创建
							//						parent = file.getParentFile();
							String strparent = file.getParent();
							parent = new SmbFile(strparent);
							if (!parent.exists())
								parent.mkdirs();

							// 如果该文件已存在，直接覆盖
							// 如果该文件不存在，自动创建
							out = new SmbFileOutputStream(file);

							while ((len = in.read(buf, 0, 1024)) != -1)
								out.write(buf, 0, len);

							out.close();
						}
					}

					in.close();
					System.out.println(zipFileName + "解压完毕");
				} 
				catch (Exception e) 
				{
					System.out.println(e);
					e.printStackTrace();
					System.out.println(zipFileName + "解压失败");
					return 1;
				}
			}
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println(e.getMessage());
			return 1;
		}
		return 0;
	}

//	public static void main(String args[]) 
//	{
//		new UnZip1().unZip(args[0], args[1]);
//	}
}
