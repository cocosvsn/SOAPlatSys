<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.soaplat.cmsmgr.service.*"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>特殊情况</title>
		<script type="text/javascript" src="dwr/interface/EncryptService.js"></script>
		<script type="text/javascript" src="dwr/engine.js"></script>
		<script type="text/javascript">
			function startEncryptScan() {
				EncryptService.start(function() {
					document.getElementById("view").innerHTML = "加扰任务扫描模块 ------ 已启动";
				});
			}
			
			function stopEncryptScan() {
				EncryptService.stop(function() {
					document.getElementById("view").innerHTML = "加扰任务扫描模块 ------ 已停止";
				});
			}
		</script>

	</head>
	<body>
		<div id="view">
			加扰任务扫描模块 ------ 已启动
		</div>
		<%
		 	BackgroundEncryptService.start();
		%>
		<br />
		<br />
		<input type="button" value="启动任务扫描" onclick="startEncryptScan();" />
		<br />
		<br />
		<input type="button" value="停止任务扫描" onclick="stopEncryptScan();" />
	</body>
</html>