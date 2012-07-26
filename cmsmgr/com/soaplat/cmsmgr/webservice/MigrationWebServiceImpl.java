package com.soaplat.cmsmgr.webservice;

import java.net.MalformedURLException;
import java.util.List;

import javax.jws.WebService;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.soaplat.cmsmgr.dto.CmsResultDto;
import com.soaplat.cmsmgr.service.MigrationServiceImpl;
import com.soaplat.cmsmgr.util.SmbFileUtils;

@WebService
public class MigrationWebServiceImpl implements MigrationWebServiceIface {

	public static final Logger cmsLog = Logger.getLogger("Cms");
	
	// 20100130 16:28
	// 完成迁移到一级库，迁移模块完成迁移后，通过webservice调用这个接口
	// 返回：0 - 成功 ； 1 - 失败
	public int finishMigration(
			String transferEntity,			// 迁移文件的Entity，从迁移单xml中得到
			String result,					// 迁移结果，"Y" - 成功；"N" - 失败
			String resultDes,				// 失败原因
			String type						// 0 - 上海； 1 - 北京； 2 - 数据导入@北京
			)
	{
		cmsLog.info("Cms -> MigrationWebServiceImpl -> finishMigration...");
		CmsResultDto cmsResultDto = new CmsResultDto();
		
		/**
		 * HuangBo addition by 2011年11月4日 17时45分
		 * 增加迁移反馈成功的验证, 判断迁移源文件和目标文件大小相同, 如果不同修改迁移反馈结果为失败, 并注明失败原因是文件大小不匹配
		 */
		if ("Y".equals(result)) {
			try {
				if(!assertEqualSize(transferEntity)) {
					result = "N";
				}
			} catch (MalformedURLException e) {
				cmsLog.error(e);
				result = "N";
			} catch (SmbException e) {
				cmsLog.error(e);
				result = "N";
			} catch (DocumentException e) {
				cmsLog.error(e);
				result = "N";
			}
		}
		
		try 
		{
			// 0 - 数据导出@上海； 
			// 1 - 迁移至播发库@北京； 
			// 2 - 数据导入@北京
			// 3 - 节目录入@上海
			if(type != null)
			{
				cmsLog.info("接收到迁移模块反馈，type：" + type);
				if (type.equalsIgnoreCase("0")) 
				{
					cmsLog.info("接收到迁移模块反馈，数据导出@上海(0)...");
					MigrationServiceImpl migrationServiceImpl = new MigrationServiceImpl();
					cmsResultDto = migrationServiceImpl.finishMigrationToBjOnline(
							transferEntity, 
							result, 
							resultDes
							);
				} 
				else if (type.equalsIgnoreCase("1")) 
				{
					cmsLog.info("接收到迁移模块反馈，迁移至播发库@北京(1)...");
					MigrationServiceImpl migrationServiceImpl = new MigrationServiceImpl();
					cmsResultDto = migrationServiceImpl.finishMigrationToOnline(
							transferEntity, 
							result, 
							resultDes
							);
				}
				else if (type.equalsIgnoreCase("2")) 
				{
					cmsLog.info("接收到迁移模块反馈，数据导入@北京(2)...");
					MigrationServiceImpl migrationServiceImpl = new MigrationServiceImpl();
					cmsResultDto = migrationServiceImpl.finishMigrationImportDataToBjNearOnline(
							transferEntity, 
							result, 
							resultDes
							);
				}
				else if (type.equalsIgnoreCase("3"))
				{
//					cmsLog.info("接收到迁移模块反馈，节目录入@上海(3)...");
//					MigrationServiceImpl migrationServiceImpl = new MigrationServiceImpl();
//					cmsResultDto = migrationServiceImpl.finishMigrationForProgram(
//							transferEntity, 
//							result, 
//							resultDes
//							);
					cmsLog.info("接收到迁移模块反馈，节目录入@上海(3)...");
					MigrationServiceImpl migrationServiceImpl = new MigrationServiceImpl();
					cmsResultDto = migrationServiceImpl.updateFinishMigrationForProgramInfo(
							transferEntity, 
							result, 
							resultDes
							);
					
				}
				else 
				{
					String str = "未知类型，返回失败。" + type;
					cmsLog.warn(str);
					cmsResultDto.setResultCode((long) 1);
					cmsResultDto.setErrorMessage(str);
				}
			}
			else
			{
				String str = "类型为空，返回失败。";
				cmsLog.warn(str);
				cmsResultDto.setResultCode((long) 1);
				cmsResultDto.setErrorMessage(str);
			}
		} 
		catch (Exception e) 
		{
			String str = "异常：" + e.getMessage();
			cmsLog.error(str);
			cmsResultDto.setResultCode((long) 1);
			cmsResultDto.setErrorMessage(str);
		}
		cmsLog.info("Cms -> MigrationWebServiceImpl -> finishMigration returns.");
		return cmsResultDto.getResultCode().intValue();
	}
	
	/**
	 * 判断源文件和目标文件大小是否相同
	 * @param xml 迁移任务XML
	 * @return 如果相同返回true, 否则返回false
	 * @throws DocumentException 
	 * @throws MalformedURLException 
	 * @throws SmbException 
	 */
	public static boolean assertEqualSize(String xml) throws DocumentException, MalformedURLException, SmbException {
		boolean equalSizeFlag = false;
		//smb://administrator:dbstar@10.30.1.10/cms/NearOnline/Clip/PRVN201111/PRVN20111104172827000681001001.ts
		Document document = DocumentHelper.parseText(xml);
		Element sourceNode = (Element) document.selectSingleNode("//Source");				// 源
		Element destinationNode = (Element) document.selectSingleNode("//Destination");		// 目标
		
		String sourceSmbFileString  = sourceNode.attributeValue("Type") + "://"
				+ sourceNode.attributeValue("Username") + ":" + sourceNode.attributeValue("Password") + "@"
				+ sourceNode.attributeValue("Hostname") + sourceNode.selectSingleNode("//Source/*").getText();
		String destinationSmbFileString  = destinationNode.attributeValue("Type") + "://"
				+ destinationNode.attributeValue("Username") + ":" + destinationNode.attributeValue("Password") + "@"
				+ destinationNode.attributeValue("Hostname") + destinationNode.selectSingleNode("//Destination/*").getText();
		SmbFile sourceSmbFile = new SmbFile(sourceSmbFileString);
		SmbFile destinationSmbFile = new SmbFile(destinationSmbFileString);
		
		if (sourceSmbFile.exists()
				&& destinationSmbFile.exists()
				&& SmbFileUtils.sizeOf(sourceSmbFile) == SmbFileUtils.sizeOf(destinationSmbFile)) {
			equalSizeFlag = true;
			cmsLog.debug("迁移文件[ " + sourceSmbFileString + " ]\n\t--> [ " + destinationSmbFileString + " ]大小相同!");
		} else {
			cmsLog.warn("迁移文件[ " + sourceSmbFileString + " ]\n\t--> [ " + destinationSmbFileString + " ]大小不相同!");
		}
		return equalSizeFlag;
	}
	
	public static void main(String[] args) throws DocumentException, MalformedURLException, SmbException {
		String xml = "<Transfer-Entity SourceProgid=\"PRVN20111104172827000681\" SourceProgTitle=\"2011年11月4日17时28分片花\" SourceFileId=\"PRVN20111104172827000681001001\" SourceFileName=\"10s04.ts\" SourceFileType=\"H264\" SourceFileCode=\"Clip\" SourceStorageClass=\"Prepared\" DesStorageClass=\"NearOnline\" PriorityDate=\"2011-11-04 00:00:00\" IsCover=\"Y\">" +
				"<Source Type=\"smb\" Hostname=\"10.30.1.10\" Port=\"\" Username=\"administrator\" Password=\"dbstar\" SourcestorageId=\"20090903143323000951\" SourceDirId=\"20100421114210000508\" VariableFilePath=\"\" FileDate=\"2011-11-04 17:28:29\">" +
				"<File>/cms/Prepared/H264/10s04.ts</File>" +
				"</Source>" +
				"<Destination Type=\"smb\" Hostname=\"10.30.1.10\" Port=\"\" Username=\"administrator\" Password=\"dbstar\" DesStorageId=\"20090903143323000953\" DesStorageDirId=\"20101122170242000534\" VariableFilePath=\"PRVN201111/\" FileDate=\"2011-11-04 17:28:29\">" +
				"<File>/cms/NearOnline/Clip/PRVN201111/PRVN20111104172827000681001001.ts</File>" +
				"</Destination></Transfer-Entity>";
		assertEqualSize(xml);
	}
}
