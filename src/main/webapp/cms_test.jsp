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
<title>����ҳ��</title>
</head>
<body>
���Է��أ�
 	<%
		//ProgListFileServiceImpl progListFileServiceImpl = new ProgListFileServiceImpl();
		//progListFileServiceImpl.test();
		
		//ProgListFileDaoImpl progListFileDaoImpl = new ProgListFileDaoImpl();
		//List list = progListFileDaoImpl.queryPlfByDate("20100101", "2010-12-30", 9l);
		//out.print(list.size());
 		
 		// ������ �޸�id (�ɹ�)
 		//BroadcastXmlServiceImpl broadcastListXmlService = new BroadcastXmlServiceImpl();
 		//broadcastListXmlService.test("2010-12-08", "2010-12-17 10:00:00", "�������ɲ�����");	
 		
 		// ���Թ��ܻ��� (�ɹ�)
 		//PortalColumnServiceImpl portalColumnServiceImpl = new PortalColumnServiceImpl();
 		//portalColumnServiceImpl.test();
 		
 		// ��˵�еĸ������ڷ��� (�ɹ�)
 		PortalServiceImpl portalServiceImpl = new PortalServiceImpl();
 		//portalServiceImpl.test("2011-03-20", "FU00000087");
 		
 		// ��˵�е��������� (�ɹ�)
 		//EncryptServiceImpl encryptServiceImpl = new EncryptServiceImpl();
 		//encryptServiceImpl.test("PPVP20101123100745000062");
 		
 		// �鿴���߽�Ŀ����Ӱ��ı൥��
 		//ProgPackageServiceImpl progPackageServiceImpl = new ProgPackageServiceImpl();
 		//progPackageServiceImpl.test("PD00000004", "2010-05-05");
 		
 		// ���ݴ洢�ȼ���ѯ�����ļ����ļ���ϸ��Ϣ
 		//AmsFileManagerService amsFileManagerService = new AmsFileManagerService();
 		//amsFileManagerService.test();
 		//amsFileManagerService.queryAllFiles("20090903143112000828", "2010-03-18", "2010-03-19", "0");
 		
 		// �ж��Ϻ���Ŀ¼����Ƶ�ļ��Ƿ����
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
		// ��Ŀ��ID, ���Ž��, ��������, ��ƷID, ������ԱID 		
 		//service.testFinishEncrypt("PPVP20101207152907000281", 8, "1", "000000000002", "EncryptModule");
 		//service.testFinishEncrypt("PPVP20101207154343000031", 8, "1", "000000000001", "EncryptModule");
 		//service.testFinishEncrypt("PPVP20101208151237000937", 8, "1", "000000000003", "EncryptModule");
 		//service.testFinishEncrypt("PPVP20101207152907000281", 8, "0", "", "EncryptModule");
 		//service.testFinishEncrypt("PPVP20101207154343000031", 8, "0", "", "EncryptModule");
 		//service.testFinishEncrypt("PPVP20101229160528000446", 8, "", "000000000022", "EncryptModule");
 		
 		// ����
 		PricingService pricingService = new PricingService();
 		pricingService.test();
 		
 		// �൥��У
 		//AmsStoragePrgRelService s = new AmsStoragePrgRelService();
 		//s.test();
 		
 		// ��Ŀ¼��ģ��
 		//ProgramInfoService programInfoService = new ProgramInfoService();
 		//programInfoService.test("20100909");
 		
 		//MigrationServiceImpl migrationServiceImpl = new MigrationServiceImpl();
 		//migrationServiceImpl.test("2010-12-08", "PPVP20101208151237000937", "����Ǩ��");
 		
 		//��Ŀ����ѯ
 		//ProgPackageServiceImpl progPackageServiceImpl = new ProgPackageServiceImpl();
 		//progPackageServiceImpl.test("", "", "", "2010-09-09", "", "");
 		
 		// ��Ʒ����
 		//ProductService productService = new ProductService();
 		//productService.test(); 
 	%>
</body>
</html>