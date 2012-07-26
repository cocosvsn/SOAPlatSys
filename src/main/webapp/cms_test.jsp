<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
	<%@ page import="java.util.*" %>
	<%@ page import="com.soaplat.sysmgr.bean.*" %>
	<%@ page import="com.soaplat.amsmgr.bean.*" %>
	<%@ page import="com.soaplat.sysmgr.serviceimpl.*" %>
	<%@ page import="com.soaplat.cmsmgr.ConfigService.*"  %>
	<%@ page import="com.cbs.cbsmgr.bean.*" %>
	<%@ page import="com.cbs.cbsmgr.serviceimpl.*" %>
	<%@ page import="com.soaplat.cmsmgr.EncryptService.*" %>
	<%@ page import="com.soaplat.cmsmgr.dto.*" %>
	<%@ page import="com.soaplat.cmsmgr.common.*" %>
	<%@ page import="com.soaplat.cmsmgr.EncryptService.*" %>
	<%@ page import="com.soaplat.cmsmgr.service.*" %>
	<%@ page import="com.soaplat.amsmgr.serviceimpl.*" %>
	<%@ page import="com.sbl.common.FlexProxy" %>
	<%@page import="com.sbl.common.UserInf"%>
	<%@page import="com.sbl.common.ParamVo"%>
	<%@page import="com.soaplat.cmsmgr.managerimpl.ProgListFileDaoImpl"%>
<%@page import="com.soaplat.cmsmgr.service.ProductService"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>测试页面</title>
</head>
<body>
测试返回！
 	<%
		//ProgListFileServiceImpl progListFileServiceImpl = new ProgListFileServiceImpl();
		//progListFileServiceImpl.test();
		
		//ProgListFileDaoImpl progListFileDaoImpl = new ProgListFileDaoImpl();
		//List list = progListFileDaoImpl.queryPlfByDate("20100101", "2010-12-30", 9l);
		//out.print(list.size());
 		
 		// 播发单 修改id (成功)
 		//BroadcastXmlServiceImpl broadcastListXmlService = new BroadcastXmlServiceImpl();
 		//broadcastListXmlService.test("2010-12-08", "2010-12-17 10:00:00", "测试生成播发单");	
 		
 		// 试试功能回退 (成功)
 		//PortalColumnServiceImpl portalColumnServiceImpl = new PortalColumnServiceImpl();
 		//portalColumnServiceImpl.test();
 		
 		// 传说中的根据日期发送 (成功)
 		PortalServiceImpl portalServiceImpl = new PortalServiceImpl();
 		//portalServiceImpl.test("2011-03-20", "FU00000087");
 		
 		// 传说中的批量加扰 (成功)
 		//EncryptServiceImpl encryptServiceImpl = new EncryptServiceImpl();
 		//encryptServiceImpl.test("PPVP20101123100745000062");
 		
 		// 查看下线节目包受影响的编单号
 		//ProgPackageServiceImpl progPackageServiceImpl = new ProgPackageServiceImpl();
 		//progPackageServiceImpl.test("PD00000004", "2010-05-05");
 		
 		// 根据存储等级查询所有文件及文件详细信息
 		//AmsFileManagerService amsFileManagerService = new AmsFileManagerService();
 		//amsFileManagerService.test();
 		//amsFileManagerService.queryAllFiles("20090903143112000828", "2010-03-18", "2010-03-19", "0");
 		
 		// 判断上海节目录入视频文件是否存在
 		//AmsStorageDirService amsStorageDirService = new AmsStorageDirService();
 		//amsStorageDirService.test();
 		EncryptServiceImpl service = new EncryptServiceImpl();
 		//service.finishEncrypt("PPRP20100824134824000796", "20090903143323000955|20100112093242000532|PPRP201008\\PPRP20100824134824000796|20100824143953000062;20100824143954000546;", 8l, "", "OP00000034");
 		//service.finishEncrypt("PPVP20100824135849000531", "20090903143323000955|20100112093242000532|PPVP201008\\PPVP20100824135849000531|20100824144010000906;20100824144014000906;20100824144020000031;", 8l, "", "OP00000034");
 		//service.finishEncrypt("PPRN20100831143036000468", "20090903143323000955|20100112093242000532|PPRN201008\\PPRN20100831143036000468|20100831143121000562;", 8l, "", "OP00000034");
 		//service.finishEncrypt("PPRP20100906193653000703", "20090903143323000955|20100112093242000532|PPRP201009\\PPRP20100906193653000703|20100906195402000046;20100906195403000468;20100906195454000281;", 8l, "", "OP00000034");
 		//service.finishEncrypt("PPRP20100906193746000015", "20090903143323000955|20100112093242000532|PPRP201009\\PPRP20100906193746000015|20100906195440000421;20100906195444000546;20100906195449000312;", 8l, "", "OP00000034");
 		//service.finishEncrypt("PPRP20100906193811000625", "20090903143323000955|20100112093242000532|PPRP201009\\PPRP20100906193811000625|20100906195425000281;20100906195429000015;20100906195434000093;", 8l, "", "OP00000034");
 		//service.finishEncrypt("PPRP20100906194118000234", "20090903143323000955|20100112093242000532|PPRP201009\\PPRP20100906194118000234|20100906195409000890;20100906195413000703;20100906195418000796;", 8l, "", "OP00000034");
		//service.test("000000000001", "OP00000034");
		// 节目包ID, 加扰结果, 加扰类型, 产品ID, 操作人员ID 		
 		//service.testFinishEncrypt("PPVP20101207152907000281", 8, "1", "000000000002", "EncryptModule");
 		//service.testFinishEncrypt("PPVP20101207154343000031", 8, "1", "000000000001", "EncryptModule");
 		//service.testFinishEncrypt("PPVP20101208151237000937", 8, "1", "000000000003", "EncryptModule");
 		//service.testFinishEncrypt("PPVP20101207152907000281", 8, "0", "", "EncryptModule");
 		//service.testFinishEncrypt("PPVP20101207154343000031", 8, "0", "", "EncryptModule");
 		//service.testFinishEncrypt("PPVP20101229160528000446", 8, "", "000000000022", "EncryptModule");
 		
 		// 订价
 		PricingService pricingService = new PricingService();
 		pricingService.test();
 		
 		// 编单审校
 		//AmsStoragePrgRelService s = new AmsStoragePrgRelService();
 		//s.test();
 		
 		// 节目录入模块
 		//ProgramInfoService programInfoService = new ProgramInfoService();
 		//programInfoService.test("20100909");
 		
 		//MigrationServiceImpl migrationServiceImpl = new MigrationServiceImpl();
 		//migrationServiceImpl.test("2010-12-08", "PPVP20101208151237000937", "测试迁移");
 		
 		//节目包查询
 		//ProgPackageServiceImpl progPackageServiceImpl = new ProgPackageServiceImpl();
 		//progPackageServiceImpl.test("", "", "", "2010-09-09", "", "");
 		
 		// 产品定义
 		//ProductService productService = new ProductService();
 		//productService.test(); 
 	%>
</body>
</html>