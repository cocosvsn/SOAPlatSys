<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>系统配置</title>
		<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.22.custom.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<!-- 		<link rel="stylesheet" type="text/css" media="screen" href="css/ui.multiselect.css" /> -->
<!-- 		<link rel="stylesheet" type="text/css" media="screen" href="css/jquery.ui.datepicker.css" /> -->
		
		<script type="text/javascript" src="dwr/interface/Config.js"></script>
		<script type="text/javascript" src="dwr/engine.js"></script>
		<script src="js/jquery-1.7.2.js" type="text/javascript"></script>
		<script src="js/jquery-ui-1.8.22.custom.js" type="text/javascript"></script>
		<script src="js/jquery-ui-i18n.js" type="text/javascript"></script>
		<script src="js/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
		<script src="js/grid.locale-cn.js" type="text/javascript"></script>
		<script src="js/ui.multiselect.js" type="text/javascript"></script>
		<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
		<script src="js/jquery.tablednd.js" type="text/javascript"></script>
<!-- 		<script src="js/jquery.contextmenu.js" type="text/javascript"></script> -->
		
		<script type="text/javascript">
			var beforeRowData = null;
			$(document).ready(function() {
				$("#configGrid").jqGrid({        
					caption: "CMS系统配置",
					datatype: function() {
						Config.getAllConfigs(function(data) {
							$("#configGrid").jqGrid('clearGridData');
							for(var i in data)
								$("#configGrid").jqGrid('addRowData', i+1, data[i]);
		 				});
					},
					autowidth: true,
					height: '80%',
				   	rownumbers: true,
					rowNum:100, 
					pager: '#configGridPaper', 
					sortname: 'name', 
					viewrecords: true, 
					sortorder: "desc", 
//					inlineNav: '#configGridPaper',
					toolbar: [true,"top"],
					editurl: "clientArray",
				   	colNames:['名称','值','说明'],
				   	colModel:[
				   		{name:'name',index:'name', width:150,editable: true},
				   		{name:'value',index:'value', width:300, editable: true},
				   		{name:'comment',index:'comment', width:300, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2"}}		
				   	]
				});
				
				$("#configGrid").jqGrid('navGrid', "#configGridPaper", {edit:false,add:false,del:false,search:false});
// 				$("#configGrid").jqGrid('navGrid','#configGridPaper',
// 						{add:false,edit:false,del:false}, //options
// 						{height:280,reloadAfterSubmit:false}, // edit options
// 						{height:280,reloadAfterSubmit:false}, // add options
// 						{reloadAfterSubmit:false}, // del options
// 						{} // search options
// 				);
				$("#configGrid").jqGrid("navButtonAdd", "#configGridPaper", {
					id: "add",
					caption:"",
					title: "添加新记录",
					buttonicon : "ui-icon-plus",
					onClickButton: function() {
						$("#configGrid").jqGrid('addRow', {});
						$("#add").addClass('ui-state-disabled');
						$("#edit").addClass('ui-state-disabled');
						$("#save").removeClass('ui-state-disabled');
						$("#cancel").removeClass('ui-state-disabled');
					}
				}).jqGrid("navButtonAdd", "#configGridPaper", {
					id: "edit",
					caption:"",
					title: "编辑所选记录",
					buttonicon : "ui-icon-pencil",
					onClickButton: function() {
						var selrow = $("#configGrid").jqGrid('getGridParam','selrow');
						if(selrow) {
							beforeRowData = $("#configGrid").jqGrid("getRowData", selrow);
							$("#configGrid").jqGrid('editRow', selrow, {keys: false});
							$("#add").addClass('ui-state-disabled');
							$("#edit").addClass('ui-state-disabled');
							$("#save").removeClass('ui-state-disabled');
							$("#cancel").removeClass('ui-state-disabled');
						} else {
							$.jgrid.viewModal("#alertmod",{gbox:"#gbox_edit",jqm:true});
							$("#jqg_alrt").focus();							
						}
					}
				}).jqGrid("navButtonAdd", "#configGridPaper", {
					id: "save",
					caption:"",
					title: "保存所选编辑记录",
					buttonicon : "ui-icon-disk",
					onClickButton: function() {
						var selrow = $("#configGrid").jqGrid('getGridParam','selrow');
						if(selrow) {
							$("#configGrid").jqGrid('saveRow', selrow, {aftersavefunc:function() {
								var rowData = $("#configGrid").jqGrid("getRowData", selrow);
								// alert(beforeRowData);
								// alert(beforeRowData.name + " = " + rowData.name + " ->" + typeof beforeRowData);
								if(null === beforeRowData 
										|| beforeRowData.name === rowData.name) {
									Config.updateConfig(rowData.name, rowData.value, rowData.comment, function() {
										$("#configGrid").trigger("reloadGrid");
										alert("添加修改配置成功!");
									});
								} else {
									alert("配置项名称不能修改!");
									$("#configGrid").trigger("reloadGrid");
								}
							}});
							$("#save").addClass('ui-state-disabled');
							$("#cancel").addClass('ui-state-disabled');
							$("#add").removeClass('ui-state-disabled');
							$("#edit").removeClass('ui-state-disabled');
						} else {
							$.jgrid.viewModal("#alertmod",{gbox:"#gbox_save",jqm:true});
							$("#jqg_alrt").focus();							
						}
					}
				}).jqGrid("navButtonAdd", "#configGridPaper", {
					id: "cancel",
					caption:"",
					title: "取消保存编辑记录",
					buttonicon : "ui-icon-cancel",
					onClickButton: function() {
						var selrow = $("#configGrid").jqGrid('getGridParam','selrow');
						if(selrow) {
							$("#configGrid").jqGrid('restoreRow', selrow, {});
							$("#save").addClass('ui-state-disabled');
							$("#cancel").addClass('ui-state-disabled');
							$("#add").removeClass('ui-state-disabled');
							$("#edit").removeClass('ui-state-disabled');
						} else {
							$.jgrid.viewModal("#alertmod",{gbox:"#gbox_cancel",jqm:true});
							$("#jqg_alrt").focus();							
						}
					}
				});
				$("#save").addClass('ui-state-disabled');
				$("#cancel").addClass('ui-state-disabled');
			});
		</script>

	</head>
	<body style="font-size: 10px;padding: 0;">
		<div style="width:100%;">
			<table id="configGrid"></table>
			<div id="configGridPaper"></div>
		</div>
	</body>
</html>