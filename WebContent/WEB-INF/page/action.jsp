<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<link href="<%=basePath%>template/css/cloud-admin.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/easyui2.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css" />

<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>

<script type="text/javascript" src="<%=basePath%>template/js/jquery.easyui.min.js"></script>
<style>
.alcenter {
	text-align: center;
}

.inline {
	display: inline-block;
}
.form-inline .form-control {
	/*width:auto;*/
	max-width: 391px;
	width: 391px;
}
.clabel {
	margin-right:14px;
}
.row{
	margin-center: 0px;
}
.panel-header, .datagrid-header{
	display: none;
}
.datagrid-body::-webkit-scrollbar{
			width: 10px;
			height: 10px;
		}
		.datagrid-header td
		{
		    border:none;
		}
		.panel{
		margin-bottom: 20px;
		text-align: inherit;
	}
	.panel-body{
		padding: 15px;
		border: none;
		font-size: 14px;
	}
	.panel-header{
		padding:0;
		border-width:0;
		border-top-width:1px;
	}
	.datagrid-header-row{
		width:100%;
	}
	.datagrid-body td{
		background-color: #fff !important;
		height:100%;
		border-width: 0 0 0 0;
	}
	.panel.datagrid.easyui-fluid{
		overflow: hidden;
		text-align: left;
		border: 0;
		margin: 0;
	}
	.col-md-2 .panel-body{
		padding: 0 0 0 0;
	}
	.col-md-2 .panel{
		border-bottom: none;
	}
	#table-modal_adduser .form-group, #table-modal_edituser .form-group{
		width: 50%;
		float: left;
	}
	#table-modal_adduser .form-group .control-label, #table-modal_edituser .form-group .control-label {
		padding-top: 7px;
		padding-right: 0px;
	}
	#table-modal_adduser .form-group .col-lg-8, #table-modal_edituser .form-group .col-lg-8{
		padding-left: 0px;
	}
	.tree-title{
		cursor: pointer;
		padding:0 8px;
	}
	/* .tree-title{color:#666;font-size:14px;height:30px;line-height:30px;}
	.tree-checkbox0,.tree-hit,.tree-checkbox1,.tree-checkbox2{margin-top:5px;} */
	.tree-title{font-size:14px;height:30px;line-height:30px;}
	.form-inline .form-control{width:200px;}
</style>
<script type="text/javascript">

//回车搜索事件
/* function onKeyDown(event){
   var e = event || window.event || arguments.callee.caller.arguments[0];
   if(e && e.keyCode==13){ // enter 键
	   searchUser('1');
   }
} */
</script>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">菜单管理</h1>
	</div>

	<div id="wrapper-md-ds" class="wrapper-md" style="height:500px;padding-bottom: 20px;">
		<div class="col-md-10" style=" width: 100%;">
			
			<div class="row">
				<div class="panel panel-default">
					<div id="loadDiv_user" style="text-align: center;margin-top: 10px;position: absolute;left:50%;z-index:99;display: none;">
				 		<img alt="" src="<%=basePath%>template/img/loading3.gif">
					</div>
					<div class="panel-heading">菜单列表</div>
					<div class="panel-body" style="padding: 0 0 15px;" id="action" >
					
						
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 添加菜单 -->
	  <div class="modal fade" id="addMenu">							
		<div class="modal-dialog" style="margin-top: 7%;width: 55%;height: 100%;">
		  <div class="modal-content modal-table" style="height: 400px;">
			<div class="modal-header">
			  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			  <h4 class="modal-title" style="font-size: 22px;">添加</h4>
			</div>
			<div class="modal-body" style="width: 100%;height:420px;" >	
				<div class="_modal-content" style="margin:15px;">	
					<input type="text" id="parent" style=" display:none;">
					<div class="form-group">
						<label class="col-lg-3 control-label">菜单名称</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addname" name="addname" style="margin-bottom: 10px;">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">图标地址</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addicon" name="addicon" style="margin-bottom: 10px;">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">路由地址</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addroute" name="addroute" style="margin-bottom: 10px;">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">是否隐藏</label>
						<div class="col-lg-8">
							<select id="addishide" class="form-control"  style="margin-bottom: 20px;">
								<option value="是" selected="selected">是</option>
								<option value="否" selected="selected">否</option>
				 			</select>
						</div>
					</div>
			
					<div class="form-group" style="width: 100%;margin-top: 54px;">
						<div class="col-lg-offset-4 col-lg-5">
							<!-- <button type="button" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="addMenu()">添加</button> -->
							<button type="reset" class="btn w-xs btn-info" data-dismiss="modal" onclick="addMenu()">添加</button>
							<button type="reset" class="btn w-xs btn-default" data-dismiss="modal">取消</button>
						</div>
					</div>									
			 	</div>
			</div>
		  </div>
		</div>
	  </div>
<!-- 编辑菜单	 -->
		<div class="modal fade" id="editMenu">							
		<div class="modal-dialog" style="margin-top: 7%;width: 55%;height: 100%;">
		  <div class="modal-content modal-table" style="height: 400px;">
			<div class="modal-header">
			  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			  <h4 class="modal-title" style="font-size: 22px;">编辑</h4>
			</div>
			<div class="modal-body" style="width: 100%;height:420px;" >	
				<div class="_modal-content" style="margin:15px;">	
					<input type="text" id="editid" style=" display:none;">
					<div class="form-group">
						<label class="col-lg-3 control-label">菜单名称</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editname" name="editname" style="margin-bottom: 10px;">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">图标地址</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editicon" name="editicon" style="margin-bottom: 10px;">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">路由地址</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editroute" name="editroute" style="margin-bottom: 10px;">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">是否隐藏</label>
						<div class="col-lg-8">
							<select id="editishide" class="form-control"  style="margin-bottom: 20px;">
								<option value="是" selected="selected">是</option>
								<option value="否" selected="selected">否</option>
				 			</select>
						</div>
					</div>
			
					<div class="form-group" style="width: 100%;margin-top: 54px;">
						<div class="col-lg-offset-4 col-lg-5">
							<!-- <button type="button" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="editMenu()">添加</button> -->
							<button type="reset" class="btn w-xs btn-info" data-dismiss="modal" onclick="editMenu()">提交</button>
							<button type="reset" class="btn w-xs btn-default" data-dismiss="modal" >取消</button>
						</div>
					</div>									
			 	</div>
			</div>
		  </div>
		</div>
	  </div>

	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	function myloader(param,success,error){
	    $.ajax({
	        url:"<%=basePath %>admin/initDSTree.php",
	        data:{
	        },
	        type:"post",
	        dataType:"json",
	        success: function(data){
	            success(data); 
	        }
	    });
	}
	function onClickCells_ds(field,row){
		var tempid = String(row.id);
		dsId=tempid;
		searchUser(1);
	}
	
	
	window.onload = fun();
	function fun(){
		$("#action").empty();
		$.ajax({
	        url:"<%=basePath %>system/getAction.php",
	        data:{
	        },
	        type:"get",
	        dataType:"json",
	        success: function(data){
	        	var touhtml=
					'<table class="table table-striped table-hover br04" style="text-align: center;">'+
						'<thead class="b20">'+
							'<tr>'+
								'<th style="vertical-align: middle; width :20%; text-align:center" class="alcenter">菜单名称</th>'+
								'<th style="vertical-align: middle; width :20%; text-align:center" class="alcenter">图标地址</th>'+
								'<th style="vertical-align: middle; width :20%; text-align:center" class="alcenter">路由地址</th>'+
								'<th style="vertical-align: middle; width :10%; text-align:center" class="alcenter">排序</th>'+
								'<th style="vertical-align: middle; width :10%; text-align:center" class="alcenter">是否隐藏</th>'+
								'<th style="vertical-align: middle; width :10%; text-align:center" class="alcenter"><a>编辑</a></th>'+
								'<th style="vertical-align: middle; width :10%; text-align:center" class="alcenter"><a class="br21" data-target="#addMenu" data-toggle="modal" onclick="addMenu1(0)">新增</a></th>'+
							'</tr>'+
						'</thead>'+
						'<tbody id="tbcont">'+
						'</tbody>'+
					'</table>';
				$("#action").append(touhtml);
	        	
	        	var html="";
	        	for(var i =data.length-1;i>=0;i--){
	        		if(data[i].parent=="0"){
	        			var html2="";
	        			for(var j =data.length-1;j>=0;j--){
	        				if(data[j].parent==data[i].id){
	        					html2+=
	        					'<tr>'+
	        					'<div id="id'+i+'j'+j+'" style=" display:none;">'+data[j].id+'</div>'+
								'<td id="actionName'+i+'j'+j+'" style="vertical-align: middle; width :20%; text-align:center">'+data[j].actionName+'</td>'+
								'<td id="icon'+i+'j'+j+'" style="vertical-align: middle; width :20%; text-align:center">'+data[j].icon+'</td>'+
								'<td id="route'+i+'j'+j+'" style="vertical-align: middle; width :20%; text-align:center">'+data[j].route+'</td>'+
								'<td style="vertical-align: middle; width :10%; text-align:center"><a class="c09">↑</a>&nbsp;&nbsp;&nbsp;<a class="c09">↓</a></td>'+
								'<td id="isHide'+i+'j'+j+'" style="vertical-align: middle; width :10%; text-align:center">'+data[j].isHide+'</td>'+
								'<th style="vertical-align: middle; width :10%; text-align:center" class="alcenter"><a data-target="#editMenu" data-toggle="modal" class="c09" onclick="editMenu1('+i+','+j+')">编辑</a></th>'+
								'<th style="vertical-align: middle; width :10%; text-align:center" class="alcenter"></th>'+
							'</tr>';
	        				}
	        			}
	        			html +=
								'<table class="table table-striped table-hover br04" style="text-align: center;">'+
									'<thead class="b32">'+
										'<tr>'+
											'<div id="id'+i+'" style=" display:none;">'+data[i].id+'</div>'+
											'<th  id="actionName'+i+'" style="vertical-align: middle; width :20%; text-align:center" class="alcenter">'+data[i].actionName+'</th>'+
											'<th  id="icon'+i+'" style="vertical-align: middle; width :20%; text-align:center" class="alcenter">'+data[i].icon+'</th>'+
											'<th  id="route'+i+'" style="vertical-align: middle; width :20%; text-align:center" class="alcenter">'+data[i].route+'</th>'+
											'<th style="vertical-align: middle; width :10%; text-align:center" class="alcenter"><a class="c09">↑</a>&nbsp;&nbsp;&nbsp;<a class="c09">↓</a></th>'+
											'<th  id="isHide'+i+'" style="vertical-align: middle; width :10%; text-align:center" class="alcenter">'+data[i].isHide+'</th>'+
											'<th style="vertical-align: middle; width :10%; text-align:center" class="alcenter"><a data-target="#editMenu" data-toggle="modal" class="c09" onclick="editMenu1('+i+')">编辑</a></th>'+
											'<th style="vertical-align: middle; width :10%; text-align:center" class="alcenter"><a data-target="#addMenu" data-toggle="modal" class="c09" onclick="addMenu1('+data[i].id+')">新增</a></th>'+
										'</tr>'+
									'</thead>'+
									'<tbody id="tbcont">'+
									html2+
									'</tbody>'+
								'</table>';
								
	        		}
	        	}
	        	$("#action").append(html);
	        }
	    });
	};
	//编辑弹窗赋值
	function editMenu1(i,j){
		if(j==null || j=="" && j!=0){
			$("#editid").val($("#id"+i).html());
			$("#editname").val($("#actionName"+i).html());
			$("#editicon").val($("#icon"+i).html());
			$("#editroute").val($("#route"+i).html());
			$("#editishide").val($("#isHide"+i).html());
		}else{
			$("#editid").val($("#id"+i+"j"+j).html());
			$("#editname").val($("#actionName"+i+"j"+j).html());
			$("#editicon").val($("#icon"+i+"j"+j).html());
			$("#editroute").val($("#route"+i+"j"+j).html());
			$("#editishide").val($("#isHide"+i+"j"+j).html());
		}
	}
	//添加辑弹窗赋值
	function addMenu1(parentId){
		$("#parent").val(parentId);
	}
	//编辑提交
	function editMenu(){
		var id=$("#editid").val();
		var actionName=$("#editname").val();
		var icon=$("#editicon").val();
		var route=$("#editroute").val();
		var isHide=$("#editishide").val();
	   $.ajax({
	        url:"<%=basePath %>system/editAction.php",
	        data:{
	        	"id":id,
	        	"actionName":actionName,
	        	"icon":icon,
	        	"route":route,
	        	"isHide":isHide
	        },
	        type:"post",
	        dataType:"json",
	        success: function(data){
	            
	        }
	    });  
	}
	//添加提交
	function addMenu(){
		var actionName=$("#addname").val();
		var icon=$("#addicon").val();
		var route=$("#addroute").val();
		var isHide=$("#addishide").val();
		var parent=$("#parent").val();
	   $.ajax({
	        url:"<%=basePath %>system/addAction.php",
	        data:{
	        	"actionName":actionName,
	        	"icon":icon,
	        	"route":route,
	        	"isHide":isHide,
	        	"parent":parent
	        },
	        type:"post",
	        dataType:"json",
	        success: function(data){
	            
	        }
	    });  
	}
</script>
</body>
</html>