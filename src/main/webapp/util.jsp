<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>特殊操作</title>
		<script type="text/javascript" src="dwr/interface/ProgPackageServiceImpl.js"></script>
		<script type="text/javascript" src="dwr/engine.js"></script>
		<script type="text/javascript">
			/**
			 * 重置节目包附件缓存库状态
			 */
			function DealState() {
				ProgPackageServiceImpl.initDealState(document.getElementById("progPackageName").value, function(result) {
					//alert(result);
					document.getElementById("initResult").innerHTML = result;
				});			
			}
			
			/**
			 * 重置主文件迁移中状态
			 */
			function MigrationState() {
				ProgPackageServiceImpl.initMigrationState(document.getElementById("progPackageName").value, function(result) {
					//alert("11111111");
					document.getElementById("initResult").innerHTML = result;
				});
			}
		</script>

	</head>
	<body>
		<div id="progPackage">
			重置节目包使用状态: <br/><input type="text" id="progPackageName">
			<input type="button" value="重置节目包附件缓存库状态" onclick="DealState();" />
			<input type="button" value="重置主文件迁移中状态" onclick="MigrationState();" />
			<label id="initResult" style="color:red"></label>
		</div>
		
		<br />
		<br />
	</body>
</html>