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
<script type="text/javascript">
	$(document).ready( function(){
		var div_height = $("#wrapper-md-ds").height()-40;
		//alert(div_height);
		$("#ds_div").css("height", div_height+"px");
		
		getAllDepartments();
	});
	
	function getAllDepartments(){
		$.ajax({
			type: "POST",
			url: "<%=basePath %>admin/getAllDepartments.php",
			dataType : "json",
			data : {},						
			success : function(data) {
				jQuery.each(data,function(i,item){
					$("#addpartment").append('<option>'+item.departmentName+'</option>');
					$("#editpartment").append('<option>'+item.departmentName+'</option>');
				});
				$("#addpartment").append('<option></option>');
				$("#editpartment").append('<option></option>');
				getAllSections(1);
				getAllSections(2);
			},						
			error : function(data) {
			}
		});
	}
	
	function getAllSections(type){
		var department="";
		if(type == 1){
			department=$("#addpartment").val();
			$("#addsection").empty();
		}else{
			department=$("#editpartment").val();
			$("#editsection").empty();
		}
		
		if(department==""){
			if(type==1){
				$("#addsection").append('<option></option>');
			}else{
				$("#editsection").append('<option></option>');
			}
		}else{
			$.ajax({
				type: "POST",
				url: "<%=basePath %>admin/getAllSections.php",
				dataType : "json",
				data : {"department":department},						
				success : function(data) {
					jQuery.each(data,function(i,item){
						if(type==1){
							$("#addsection").append('<option>'+item.sectionName+'</option>');
						}else{
							$("#editsection").append('<option>'+item.sectionName+'</option>');
						}
					});
				},						
				error : function(data) {
				}
			});
		}
	}
	//折叠
	function testOfSearch(obj){
	  var div1=document.getElementById("searchOfForm");
	  if(div1.style.display=="block"){
	    div1.style.display="none";
	    obj.src="<%=basePath %>template/img/downXia.png";
	  }else{
	    div1.style.display="block";
	    obj.src="<%=basePath %>template/img/addevidence.png";
	  }
	}
</script>
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
	margin-left: 0px;
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
		/* width: 50%; */
		/* float: left; */
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
  		<h1 class="m-n h4">机构管理</h1>
	</div>

	<div id="wrapper-md-ds" class="wrapper-md" style="height:500px;padding-bottom: 20px;">
		<div class="col-md-2" style="padding-right: 20px;margin-left: -15px;height: 500px;">
			<div class="row" style="height: 500px;">
				<div class="panel panel-default" style="height: 500px;">
					<div class="panel-heading" style="margin-bottom:15px;">组织结构</div>
					<div id="ds_div" class="panel-body" style="padding-left:15px;overflow:auto;">
						<table title="Folder Browser" class="easyui-treegrid c26 br21"
									style="width: 100%; height: 100%; float: left; margin-bottom: 0px;font-size: 16px;text-align: center;"
									id="caseTreeGrid_ds"
									data-options="
											<!--  url: '<%=basePath%>json.php',-->
											<!-- url: '<%=basePath%>admin/initDSTree.php',-->
											lines: true,
											checkbox: false,
											idField: 'id',						 
											animate:'true',
											treeField: 'text',
											onClickCell:onClickCells_ds,
											loader:myloader
									">
									<thead class="b30 c26 br21" style="font-size: 20px;text-align: center; display: none;">
										<tr style="height:30px;line-height:30px;">
											<th data-options="field:'text'" style="width: 90%;">
												<!-- <input type="checkbox" id="_REPLACE_MAIN"  class="_table-blue-checkbox-hidden"/> -->
												<span class="c27" style="font-size: 14px"></span>
											</th>
										</tr>
									</thead>
								</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-10">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">搜索<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onclick="testOfSearch(this)"></span></div>
					<div class="panel-body" id="searchOfForm" style="display: block;">
						<form class="form-inline" role="form" id="searchForm" action="<%=basePath %>admin/userManager.php" method="post">
							<input type="hidden" name="pageno" id="pageno"/>
							<div class="form-group">
								<label class="clabel">机构名称:</label>
								<input type="text" id="organizename"  class="form-control" placeholder="请输入..." onkeydown="onKeyDown(event)"/>
							</div>
							
							<button type="button" class="btn btn-info b23 c02"
								style="width: 55px; height: 30px;" onclick="addOrganize1()">搜索</button>
							<button type="button" class="btn btn-info b23 c02" data-toggle="modal" data-target="#addOrganize"
								style="width: 55px; height: 30px;" >新增</button>
						</form>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="panel panel-default">
					<div id="loadDiv_user" style="text-align: center;margin-top: 10px;position: absolute;left:50%;z-index:99;display: none;">
				 		<img alt="" src="<%=basePath%>template/img/loading3.gif">
					</div>
					<div class="panel-heading">用户列表</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br04"
							style="text-align: center;">
							<thead>
								<tr>
									<th class="alcenter">名称</th>
									<th class="alcenter">编号</th>
									<th class="alcenter">所属机构</th>
									<th class="alcenter">备注</th>
									<th class="alcenter">是否可用</th>
									<th class="alcenter">编辑</th>
									<th class="alcenter">删除</th>
									
								</tr>
							</thead>
							<tbody id="tbcont">
								 	
							</tbody>
						</table>
						<div class="alcenter" style="font-size: 14px">
							<div class="pagecount inline" style="height: 29px;">
								<span>共<span id="totalSpan">${totalNum }</span>条，当前<span id="currSpan">${nowPage }/${totalPages }</span>页</span>
							</div>
							<div class="pagebar inline"
								style="position: absolute; right: 10px; height: 29px;">
								<ul id="pageUL" class="pagination pagination-sm" style="margin: 0;">
									<li><a href="#" onclick="searchUser('${nowPage-1<1?1:nowPage-1}')">&lt;</a></li>
									<c:forEach items="${pageList }" var="pageNO">
				            			<li <c:if test="${nowPage==pageNO }">class="active"</c:if>>
				            				<a href="#" onclick="searchUser('${pageNO}')">${pageNO}</a>
				            			</li>
				            		</c:forEach>
									<li><a href="#" onclick="searchUser('${nowPage+1>totalPages?totalPages:nowPage+1}')">&gt;</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 添加用户 -->
	  <div class="modal fade" id="addOrganize">							
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
						<label class="col-lg-3 control-label">机构名称</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addname"  style="margin-bottom: 10px;">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">机构编号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addnumber"  style="margin-bottom: 10px;">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">上级机构</label>
						<div class="col-lg-8">
							<select id="addparent" class="form-control"  style="margin-bottom: 20px;">
								
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">是否可用</label>
						<div class="col-lg-8">
							<select id="addishide" class="form-control"  style="margin-bottom: 20px;">
								<option value="否" selected="selected">否</option>
								<option value="是" selected="selected">是</option>
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">备注</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addremark" style="margin-bottom: 10px;">
						</div>
					</div>
					<div class="form-group" style="width: 100%;margin-top: 54px;">
						<div class="col-lg-offset-4 col-lg-5">
							<button type="reset" class="btn w-xs btn-info" data-dismiss="modal" onclick="addOrganize()">添加</button>
							<button type="reset" class="btn w-xs btn-default" data-dismiss="modal">取消</button>
						</div>
					</div>									
			 	</div>
			</div>
		  </div>
		</div>
	  </div>
	  
	  <div class="modal fade" id="table-modal_deluser" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="delId" id="delId"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">删除用户</h3>
					</div>
					<div class="modal-body">是否删除该用户？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" onclick="delUser()">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left: 16px;">取消</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="table-modal_resetPasswd" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetId" id="resetId"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">重置密码</h3>
					</div>
					<div class="modal-body">是否重置密码？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" onclick="resetPasswd()">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left: 16px;">取消</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 删除成功弹框 -->
		<div class="modal fade" id="table-modal_delUser_success" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="delUser_successes" id="delUser_successes"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">删除用户</h3>
					</div>
					<div class="modal-body">删除用户成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		<!-- 重置密码成功弹框 -->
		<div class="modal fade" id="table-modal_resetPassword_success" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes" id="resetPassword_successes"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">重置密码</h3>
					</div>
					<div class="modal-body">重置密码成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		<!-- 添加用户成功弹框 -->
		<div class="modal fade" id="table-modal_addUser_success" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="addUser_successes" id="addUser_successes"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">添加用户</h3>
					</div>
					<div class="modal-body">添加用户成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		<!-- 编辑用户成功弹框 -->
		<div class="modal fade" id="table-modal_editUser_success" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="editUser_successes" id="editUser_successes"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">编辑用户</h3>
					</div>
					<div class="modal-body">编辑用户成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="table-modal_edituser">							
		<div class="modal-dialog" style="margin-top: 7%;width: 55%;height: 100%;">
		  <div class="modal-content modal-table">
			<div class="modal-header">
			  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			  <h4 class="modal-title" style="font-size: 22px;">编辑用户</h4>
			</div>
			<div class="modal-body" style="width: 100%;height:420px;" >	
				<div class="_modal-content" style="margin:15px;">	
					<p class="c22" id="mistake_edit" style="text-align:center;font-size:14px;padding-left:25px;margin-bottom:0;" ></p>
					<input type="hidden" name="editId" id="editId">
					<div class="form-group">
						<label class="col-lg-3 control-label">用户名</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editusername" name="editusername" onblur="validateobj(this,'用户名')">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">警号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editpoliceno" name="editpoliceno" onblur="validateobj(this,'警号')">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">身份证号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editcardno" name="editcardno" onblur="validateobj(this,'身份证号')">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">部门</label>
						<div class="col-lg-8">
							<select id="editpartment" class="form-control" onchange="getAllSections(2);">
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">科室</label>
						<div class="col-lg-8">
							<select id="editsection" class="form-control">
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">职位</label>
						<div class="col-lg-8">
							<select id="edituserprevilige" class="form-control">
								<option value="科员" selected="selected">科员</option>
				 				 <option >副主任科员</option>
				 					<option>主任科员</option>
				 					<option >副科长</option>
				 					<option>主任科员兼副科长</option>
				 					<option >科长</option>
				 					<option >副调研员</option>
				 					<option >调研员</option>
				 					<option >副处长</option>
				 					<option >处长</option>
				 					<option >分局副局长</option>
				 					<option >分局局长</option>
				 					<option >副局长</option>
				 					<option >局长</option>
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">手机号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="edittelephone" name="edittelephone">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">座机号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editphone" name="editphone">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">邮箱</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editemail" name="editemail">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">加密狗序号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editsn" name="editsn">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">备注</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="edituserremark" name="edituserremark">
						</div>
					</div>
					<div class="form-group" style="width: 100%;margin-top: 54px;">
						<div class="col-lg-offset-4 col-lg-5">
							<button type="button" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="editUser()">确定</button>
							<button type="reset" class="btn w-xs btn-default" data-dismiss="modal">取消</button>
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
	        url:"<%=basePath %>system/getOrganizetree.php",
	        data:{
	        },
	        type:"get",
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
	//get机构list
	window.onload=addOrganize1();
	function addOrganize1(){
		var name=$("#organizename").val();
	   $.ajax({
	        url:"<%=basePath %>system/getOrganize.php",
	        data:{
	        	"name":name
	        },
	        type:"get",
	        dataType:"json",
	        success: function(data){
	        	$("#tbcont").empty();
	        	$("#addparent").empty();
	        	var html = '<option value="0" selected="selected">一级机构</option>';
				$("#addparent").append(html);
	        	$.each(data,function(i,item){
				    //机构列表
				    var html1 = 
       						'<tr>'+
       							'<div style=" display:none;">'+item.id+'</div>'+
								'<td>'+item.organizeName+'</td>'+
								'<td>'+item.number+'</td>'+
								'<td>'+item.parent+'</td>'+
								'<td>'+item.remark+'</td>'+
								'<td>'+item.isHide+'</td>'+
								'<td><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_edituser" onclick="return setEditInfo()">编辑</a></td>'+
								'<td><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_deluser" onclick="return setDelId()">删除</a></td>'+
							'</tr>';
					$("#tbcont").append(html1);	
	        	
	        		//添加弹窗赋值
					var html01 = '<option value="'+item.id+'" >'+item.organizeName+'</option>';
					$("#addparent").append(html01);	
	        	
	        	
	        	});
	        		
	        }
	    });  
	}
	//添加提交
	function addOrganize(){
		var organizeName=$("#addname").val();
		var number=$("#addnumber").val();
		var parent=$("#addparent").val();
		var isHide=$("#addishide").val();
		var remark=$("#addremark").val();
	   $.ajax({
	        url:"<%=basePath %>system/addOrganize.php",
	        data:{
	        	"organizeName":organizeName,
	        	"number":number,
	        	"parent":parent,
	        	"isHide":isHide,
	        	"remark":remark
	        },
	        type:"get",
	        dataType:"json",
	        success: function(data){
	            
	        }
	    });  
	}
</script>
</body>
</html>