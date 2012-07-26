package com.soaplat.cmsmgr.manageriface;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.PackageFiles;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IPackageFilesManager extends IBaseManager {

	public List getPackageFilesesByProductidProgfileidAndProgramid(
			String productId, 
			String progFileId, 
			String programId
			);

	// 20100106 16:11
	// 根据节目包id、filecode、filetype，查询packagefiles、programfiles表，得到file
	public List getProgramFilesesByProductidFilecodeFiletypeid(
			String productid, 
			String filecode, 
			String filetypeid
			);
	
	// 20100111 12:32
	// 根据 节目包id和存储体等级，查询得到文件列表
	public List getProgramfilesesByProductidAndStclasscode(
			String productid, 
			String storageclassid
			);
	
	// 这就是传说中的方法2
	// 20100115 9:58
	// 根据 文件code 和 存储体等级，查询得到文件存放目标路径
	public List getDestPathByFilecodeStclasscode(
			String filecode, 						// 文件code
			String stclasscode						// 存储体等级code
			);
	
	// 这就是传说中的方法3
	// 20100115 14:10
	// 根据文件ID 和 存储体等级，查询，得到文件存放(源)路径
	public List getSourcePathByProgfileidStclasscode(
			String progfileid, 						// 文件code
			String stclasscode						// 存储体等级code
			);
	
	// 这就是传说中的方法11
	// 20100118 10:51
	// 根据文件ID 和 存储体等级，查询，得到文件存放(源)路径（不返回ProgramFiles），
	// 当progfileid传入内容是模板的ID时，在programfiles中无记录
	// 当节目包ID为null或者""时，忽略节目包ID的过滤条件
	public List getSourcePathByProgfileidStclasscodeWithoutProgramFiles(
			String progfileid, 						// 文件code
			String stclasscode,						// 存储体等级code
			String productid						// 节目包ID
			);
	
	// 这就是传说中的方法4
	// 20100115 16:01
	// 根据节目包id、filecode，查询packagefiles、programfiles表，得到file
	public List getProgramFilesesByProductidFilecode(
			String productid, 						// 节目包ID
			String filecode 						// filecode
			);
	
	// 这就是传说中的方法5
	// 20100115 16:04
	// 根据节目包id 和 主文件表示(progrank)，查询packagefiles、programfiles表，得到节目包的文件
	public List getProgramFilesesByProductidProgrank(
			String productid, 						// 节目包ID
			Long progrank 						// 主文件标识，0 - 不是;  1 - 是
			);
	
	// 20100521 13:16
	// 根据节目包id和filetype
	public List getProgramFilesesByProductidFiletype(
			String productid,
			String filetypeid
			);
	
	// 20100608 15:28
	// 根据节目包ID，查询节目包的文件列表
	public List getProgramfilesByProductid(
			String productid				// 节目包ID
			);
	
	/**
	 * 20110107 17:16 1.23 
	 */
	public List getSourcePathByProgfileidStclasscodeFiletypeWithoutProgramFiles(
			String progfileid, 						// 文件code
			String stclasscode,						// 存储体等级code
			String productid,						// 节目包ID
			String filetype
	);
	
	/**
	 * 根据节目包ID 查询节目包文件关系记录
	 * 2010年7月14日 16时55分 Bunco
	 * @param progPackageID
	 * @return List<PackageFiles>
	 */
	public List<PackageFiles> queryPackageFilesByProgPackageID(String progPackageID);

	/**
	 * 根据节目包ID统计节目包所有文件大小
	 * @param progPackageId 节目包编号
	 * @return 节目包所有文件大小总和
	 */
	public Long sumOfPackageFileSize(String progPackageId);
}
