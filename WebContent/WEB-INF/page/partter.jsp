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
		//$("#partterEdit_depat").empty();
		$.ajax({
			type: "POST",
			url: "<%=basePath %>admin/getAllDepartments.php",
			dataType : "json",
			data : {},						
			success : function(data) {
				jQuery.each(data,function(i,item){
					
					$("#addPartterDepat").append('<option onclick="getAllSections(1)">'+item.departmentName+'</option>');
					$("#partterEdit_depat").append('<option onclick="getAllSections(2)">'+item.departmentName+'</option>');
				});
				$("#addPartterDepat").append('<option></option>');
				$("#partterEdit_depat").append('<option></option>');
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
			department=$("#addPartterDepat").val();
			$("#addPartterSection").empty();
		}else{
			department=$("#partterEdit_depat").val();
			$("#partterEdit_section").empty();
		}
		if(department==""){
			if(type==1){
				$("#addPartterSection").append('<option></option>');
			}else{
				$("#partterEdit_section").append('<option></option>');
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
							$("#addPartterSection").append('<option>'+item.sectionName+'</option>');
						}else{
							$("#partterEdit_section").append('<option>'+item.sectionName+'</option>');
						}
					});
				},						
				error : function(data) {
				}
			});
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
	}
</style>
<script type="text/javascript">
function subForm(pageno){
	$("#pageno").val(pageno);
	$("#searchForm").submit();
}

var dsId;
function searchUser(page){
	var uname = $("#uname").val();
	var userprevilige = $("#userprevilige").val();
	
	$("#loadDiv_user").show();
	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/getAllPartter.php",
		dataType : "json",
		data : {
			"uname" : uname,
			"page" : page,
			"userprevilige" : userprevilige,
			"dsId":dsId
		},							    
		success : function(data) {
			$("#loadDiv_user").hide();
			var len = data.resData.length;
			var i = 0;
			var result = "";
			var tmp;
			var totalPages = data.totalPages;
			var nowPage = data.nowPage;
			var totalNum = data.totalNum;
			$("#totalSpan").text(totalNum);
			$("#currSpan").text(nowPage+"/"+totalPages);
			$("#tbcont").empty();
			for(i = 0; i < len; i++)
			{
				$("#tbcont").append('<tr><td>'+data.resData[i].username+'</td>'+
									'<td>'+data.resData[i].policeNO+'</td>'+
									'<td>'+data.resData[i].cardNO+'</td>'+
									'<td>'+data.resData[i].partment+'</td>'+
									'<td><a href="#" class="btnA">编辑</a></td>'+
									'<td><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_deluser" onclick="return setDelId(\''+data.resData[i].id+'\')">删除</a></td>'+
									'</tr>');
			}
			$("#pageUL").html("");
			
			var tmp = "";
			if(parseInt(nowPage) == 1)
				tmp += "<li><a href=\"#\" onclick=\"searchUser('" + (parseInt(nowPage)) + "')\">&lt;</a></li>";
			else
				tmp += "<li><a href=\"#\" onclick=\"searchUser('" + (parseInt(nowPage) - 1) + "')\">&lt;</a></li>";
			
			var at = Math.floor((parseInt(nowPage)-1)/9);
			at = at*9;
			var full = at + 9;
			var max;
			if(full > totalPages)
				max = totalPages;
			else
				max = full;
			var p = 0;
			for(p = at + 1; p <= max; p++){
				if(p == nowPage)
					tmp += "<li><a href=\"#\" class=\"active\" onclick=\"searchUser('"+p+"')\">"+p+"</a></li>";
				else
					tmp += "<li><a href=\"#\" onclick=\"searchUser('"+p+"')\">"+p+"</a></li>";
			}
				
			if(nowPage == totalPages)
				tmp += "<li><a href=\"#\" onclick=\"searchUser('" + (parseInt(nowPage)) + "')\">&gt;</a></li>";
			else
				tmp += "<li><a href=\"#\" onclick=\"searchUser('" + (parseInt(nowPage) + 1) + "')\">&gt;</a></li>";
			$("#pageUL").html(tmp);
		},
		error : function(data) {
			$("#loadDiv_user").hide();
			alert("查询失败，请重试!");
		}
	});
}
//添加角色
function addPartter(){
	var addPartterName=$("#addPartterName").val().trim(); //角色名称
	var addPartterDes=$("#addPartterDes").val().trim();	  //角色描述
	var addPartterDepat=$("#addPartterDepat").val().trim(); //角色部门
	var addPartterSection=$("#addPartterSection").val().trim(); //角色科室
/* 	var addPartterShu=$("#addPartterShu").val().trim(); //数据范围
	var addPartterShou=$("#addPartterShou").val().trim(); //角色授权 */
	if(addPartterName==null || addPartterName==""){
		$("#mistake").html("角色名称不能为空！");
		return false;
	}
	if(addPartterDes==null || addPartterDes==''){
		$("#mistake").html("角色描述不能为空！");
		return false;
	}
	if(addPartterDepat==null || addPartterDepat==""){
		$("#mistake").html("角色部门不能为空！");
		return false;
	}
	if(addPartterSection!=undefined){
		addPartterSection=addPartterSection.trim();
		if(addPartterSection==""){
			$("#mistake").html("角色科室不能为空！");
			return false;
		}
		
	}
	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/addPartter.php",
		dataType : "json",
		data : {
			"addPartterName" : addPartterName,
			"addPartterDes" : addPartterDes,
			"addPartterDepat" : addPartterDepat,
			"addPartterSection" : addPartterSection
			/* "addPartterShu" : addPartterShu,
			"addPartterShou" : addPartterShou */
		},
		success : function(data) {
			$('#table-modal_adduser').modal('hide'); 
			if (data.res == "succ") {
				$('#table-modal_adduser_success').modal('show');
				$("#tbcont").empty();
				showPartter(1);
			}else if(data.res == "exist"){
				$('#table-modal_existuser_succ').modal('show');
//				alert("角色已存在，请重试！");
			}else if(data.res == "nopower"){
				$('#table-modal_nopower_add').modal('show');
//				alert("无此权限！");
			}
		},
		error : function(data) {
			alert("添加角色失败，请重试!");
		}
	});

}

function setDelId(id){
	$("#delId").val(id);
	return true;
}

function delsection(){
	var id = $("#delId").val();
	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/delPartter.php",
		dataType : "json",
		data : {
			"ID" : id
		},							    
		success : function(data) {
			$('#table-modal_deluser').modal('hide');
			if (data.res == "succ") {
				$('#table-modal_deluser_success').modal('show');
				$("#tbcont").empty();
				showPartter(1);
			}
		},						
		error : function(data) {
			$('#table-modal_deluser').modal('hide');
			alert("删除角色失败，请重试!");
		}
	});    
}

function setEditInfo(i){
	var id = $("#id"+i).text();
	var partterName = $("#partterName"+i).text();
	var partterDes = $("#partterDes"+i).text();
	var sectionid = $("#sectionid"+i).text();
	var departid = $("#departid"+i).text();
	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/getAllSections.php",
		dataType : "json",
		data : {"department":departid},						
		success : function(data) {
				jQuery.each(data,function(i,item){
				$("#partterEdit_section").append('<option>'+item.sectionName+'</option>');
			});
		},						
		error : function(data) {
			
		}
	});
	$("#editId").val(id);
	$("#partterEdit_name").val(partterName);
	$("#partterEdit_des").val(partterDes);
	$("#partterEdit_depat").val(departid);
	$("#partterEdit_section").val(sectionid);
	return true;
}

function editPartter(){
	var editId = $("#editId").val();
	var partterEdit_name=$("#partterEdit_name").val().trim();
	var partterEdit_des=$("#partterEdit_des").val();
	var partterEdit_depat=$("#partterEdit_depat").val().trim();
	var partterEdit_section=$("#partterEdit_section").val().trim();
	
	if(partterEdit_name==null || partterEdit_name==""){
		$("#mistake_edit").html("角色名称不能为空！");
		return false;
	}
	if(partterEdit_des==null || partterEdit_des==''){
		$("#mistake_edit").html("角色描述不能为空！");
		return false;
	}
	if(partterEdit_depat==null || partterEdit_depat==""){
		$("#mistake_edit").html("角色部门不能为空！");
		return false;
	}
	if(partterEdit_section==null || partterEdit_section==""){
		$("#mistake_edit").html("角色科室不能为空！");
		return false;
	}
	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/editPartter.php",
		dataType : "json",
		data : {
			"editId": editId,
			"partterEdit_name" : partterEdit_name,
			"partterEdit_des" : partterEdit_des,
			"partterEdit_depat" : partterEdit_depat,
			"partterEdit_section" : partterEdit_section
		},							    
		success : function(data) {
			$('#table-modal_edituser').modal('hide');
			if (data.res == "succ") {
//				alert("编辑角色成功");  
				$('#table-modal_updatauser_success').modal('show');
				$("#tbcont").empty();
				showPartter(1);
//				window.location.reload();
				//searchUser(1);
			}else if(data.res == "exist"){
				$('table-modal_existuser_edit').modal('show');
//				alert("角色已存在，请重试！");  
			}else if(data.res == "nopower"){
				$('#table-modal_nopower_edit').modal('show');
//				alert("无此权限！");
			}
		},						
		error : function(data) {
			alert("编辑角色失败，请重试!");
		}
	});     
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
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">角色管理</h1>
	</div>

	<div class="wrapper-md" style="overflow:hidden;">
		<div class="col-md-12" style="padding-left:0px;margin-bottom:27px;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">搜索科室<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onclick="testOfSearch(this)"></span></div>
					<div class="panel-body" id="searchOfForm" style="display: block;">
						<div class="form-inline" role="form" id="searchForm"  method="post">
							<input type="hidden" name="pageno" id="pageno"/>
							<div class="form-group">
								<label for="" class="clabel">角色名称:</label><input id="uname" name="uname"
									class="form-control" placeholder="请输入..." type="text"/>
							</div>
							<button type="button" class="btn btn-info b23 c02"
								style="width: 75px; height: 30px;" onclick="showPartter(1)">搜索</button>
							<button type="button" class="btn btn-info b23 c02" data-toggle="modal" data-target="#table-modal_adduser"
								style="width: 75px; height: 30px;">新增</button>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="panel panel-default">
					<div id="loadDiv_user" style="text-align: center;margin-top: 10px;position: absolute;left:50%;z-index:99;display: none;">
				 		<img alt="" src="<%=basePath%>template/img/loading3.gif">
					</div>
					<div class="panel-heading">角色列表</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br04"
							style="text-align: center;">
							<thead>
								<tr>
								    <th class="c12" style="font-weight:600;font-size:14px;">角色名称</th>
									<th class="c12" style="font-weight:600;font-size:14px;">角色描述</th>
									<th class="c12" style="font-weight:600;font-size:14px;">创建时间</th>
									<th class="c12" style="font-weight:600;font-size:14px;">所属科室</th>
									<th class="c12" style="font-weight:600;font-size:14px;">所属部门</th>
									<th class="c12" style="font-weight:600;font-size:14px;">数据范围</th>
									<th class="c12" style="font-weight:600;font-size:14px;">创建人</th>
									<th class="c12" style="font-weight:600;font-size:14px;">编辑</th>
									<th class="c12" style="font-weight:600;font-size:14px;">删除</th>
								</tr>
							</thead>
							<tbody id="tbcont">
								
							</tbody>
						</table>
						
					</div>
					<!-- -----------------页脚------------------  -->
			
					<div class="alcenter" style="font-size: 14px">
					<div id="total" class="pagecount inline" style="height: 29px;">
						<span id="tot_d4">
						
						</span>
					</div>
					<div class="pagebar inline" style="position: absolute; right: 126px; height: 29px;">
						<ul class="pagination pagination-sm" style="margin: 0;">
							<li id = "pages1_d4"></li>

							<li id = "pages_d4"></li>

							<li id = "pages2_d4"></li>
						</ul>
					</div>
					<div style="float: right;margin-right: 11px;">
							<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages_parttesss" name="givePages_parttesss" onkeydown="onKeyDown_parttesss(event)"/>
							<button type="button" class="btn" onclick="showkeshi(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
					</div>
				</div>
			
			<!-- -----------------页脚-end-------------------  -->
				</div>
			</div>
		</div>
	</div>

	<!-- 添加角色-->
	  <div class="modal fade" id="table-modal_adduser">							
		<div class="modal-dialog" style="margin-top: 7%;width: 55%;height: 100%;">
		  <div class="modal-content modal-table">
			<div class="modal-header">
			  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			  <h4 class="modal-title" style="font-size: 22px;">添加角色</h4>
			</div>
			<div class="modal-body" style="width: 100%;height:420px;" >	
				<div class="_modal-content" style="margin:15px;">	
					<p id="mistake" class="c22" style="text-align:center;font-size:14px;padding-left:25px;margin-bottom:0;" ></p>
					<div class="form-group">
						<label class="col-lg-3 control-label">角色名称:</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addPartterName" name="addPartterName" onblur="validateobj(this,'科室编号')">
							<span id="addevNumlabel" class="empty c11"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">角色描述:</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addPartterDes" name="addPartterDes" onblur="validateobj(this,'科室名称')">
							<span id="addevNumlabel" class="empty c11"></span>
						</div>
					</div>
                    <div class="form-group">
						<label class="col-lg-3 control-label">所属部门:</label>
						<div class="col-lg-8">
							<select id="addPartterDepat" class="form-control" onchange="getAllSections(1);" >
				 			</select>
						</div>
					</div>	
					<div class="form-group">
						<label class="col-lg-3 control-label">所属科室:</label>
						<div class="col-lg-8">
							<select id="addPartterSection" class="form-control">
				 			</select>
						</div>
					</div>	
					
					<div class="form-group">
						<label class="col-lg-3 control-label">数据范围:</label>
						<div class="col-lg-8">
							<select id="" class="form-control">
								<option>所有数据</option>
								<option>所有部门数据</option>
								<option>所有科室数据</option>
								<option>仅本人数据</option>
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">角色授权:</label>
						<div class="col-lg-8">
							<select id="addPartterSection" class="form-control">
				 			</select>
						</div>
					</div>
					<div class="form-group" style="width: 100%;margin-top: 54px;">
						<div class="col-lg-offset-4 col-lg-5">
							<button type="button" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="addPartter()">添加</button>
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
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">删除角色</h3>
					</div>
					<div class="modal-body">是否删除该角色？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" onclick="delsection()">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left: 16px;">取消</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 删除角色成功弹框 -->
		<div class="modal fade" id="table-modal_deluser_success" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="deluser_successes" id="deluser_successes"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">删除角色</h3>
					</div>
					<div class="modal-body">删除角色成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		<!-- 添加角色成功弹框 -->
		<div class="modal fade" id="table-modal_adduser_success" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="addusers_successes" id="addusers_successes"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">添加角色</h3>
					</div>
					<div class="modal-body">添加角色成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		<!-- 编辑角色成功弹框 -->
		<div class="modal fade" id="table-modal_updatauser_success" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="updatauser_successes" id="updatauser_successes"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">编辑角色</h3>
					</div>
					<div class="modal-body">编辑角色成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		<!-- 添加时    角色已存在弹框 -->
		<div class="modal fade" id="table-modal_existuser_succ" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="existusers_successes" id="existusers_successes"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">角色</h3>
					</div>
					<div class="modal-body">角色已存在，请重试！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		<!-- 编辑时   角色已存在弹框 -->
		<div class="modal fade" id="table-modal_existuser_edit" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="existusers_successes_edit" id="existusers_successes_edit"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">角色</h3>
					</div>
					<div class="modal-body">角色已存在，请重试！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		<!-- 添加时无权限操作弹框 -->
		<div class="modal fade" id="table-modal_nopower_add" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="nopower_add" id="nopower_add"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">权限</h3>
					</div>
					<div class="modal-body">无此权限！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		<!-- 编辑时无权限操作弹框 -->
		<div class="modal fade" id="table-modal_nopower_edit" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="nopower_edit" id="nopower_edit"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">权限</h3>
					</div>
					<div class="modal-body">无此权限！</div>
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
			  <h4 class="modal-title" style="font-size: 22px;">编辑角色</h4>
			</div>
			<div class="modal-body" style="width: 100%;height:420px;" >	
				<div class="_modal-content" style="margin:15px;">	
					<p id="mistake_edit" class="c22" style="text-align:center;font-size:14px;padding-left:25px;margin-bottom:0;" ></p>
					<input type="hidden" name="editId" id="editId">
					<!-- <div class="form-group">
						<label class="col-lg-3 control-label">科室编号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="sectionEdit_num" name="sectionEdit_num" onblur="validateobj(this,'科室名称')">
							<span id="addevNumlabel" class="empty" style="color:red"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">科室名称</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="sectionEdit_name" name="sectionEdit_name" onblur="validateobj(this,'科室名称')">
							<span id="addevNumlabel" class="empty" style="color:red"></span>
						</div>
					</div>
					  <div class="form-group">
						<label class="col-lg-3 control-label">所属部门</label>
						<div class="col-lg-8">
							<select id="departmentedit" class="form-control" onchange="getAllDepartments(1);">
				 			</select>
						</div>
					</div>	 -->
					
					<div class="form-group">
						<label class="col-lg-3 control-label">角色名称:</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="partterEdit_name" name="partterEdit_name" onblur="validateobj(this,'科室编号')">
							<span id="addevNumlabel" class="empty c11"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">角色描述:</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="partterEdit_des" name="partterEdit_des" onblur="validateobj(this,'科室名称')">
							<span id="addevNumlabel" class="empty c11"></span>
						</div>
					</div>
                    <div class="form-group">
						<label class="col-lg-3 control-label">所属部门:</label>
						<div class="col-lg-8">
							<select id="partterEdit_depat" class="form-control" >
								
				 			</select>
						</div>
					</div>	
					<div class="form-group">
						<label class="col-lg-3 control-label">所属科室:</label>
						<div class="col-lg-8">
							<select id="partterEdit_section" class="form-control">
				 			</select>
						</div>
					</div>	
				<!-- 	<div class="form-group">
						<label class="col-lg-3 control-label">数据范围:</label>
						<div class="col-lg-8">
							<select id="partterEdit_shuju" class="form-control">
							<option>技术部</option>
								<option>研发部</option>
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">角色授权:</label>
						<div class="col-lg-8">
							<select id="partterEdit_shou" class="form-control">
							<option>技术部</option>
								<option>研发部</option>
				 			</select>
						</div>
					</div> -->
					
					<div class="form-group" style="width: 100%;margin-top: 54px;">
						<div class="col-lg-offset-4 col-lg-5">
							<button type="button" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="editPartter()">确定</button>
							<button type="reset" class="btn w-xs btn-default" data-dismiss="modal">取消</button>
						</div>
					</div>									
			 	</div>
			</div>
		  </div>
		</div>
	  </div>
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	
	window.onload = showPartter(1);
	function showPartter(pageno){
		var uname=$("#uname").val();
		var givePages_parttesss = $("#givePages_parttesss").val();
		if(givePages_parttesss != ""){
			pageno = parseInt(givePages_parttesss);
		}
		$("#tbcont").empty();
	    $.ajax({
	        url:"<%=basePath %>admin/getAllPartter.php",
	        data:{
	        	"uname":uname,
	        	"pageno":pageno
	        },
	        type:"POST",
	        dataType:"json",
	        success: function(data){
	        	    var sizes=10;
	                var pagesum=data.count;
	                var pagenum = pagesum / sizes;
	                var length=5;  //要显示的分页页数
	                if(pagenum%1!=0){
	                    pagenum=pagenum+(1-pagenum%1);
	                }
	                $("#pages_d4").empty();
	                //用于删除之前显示的页数，动态添加时id名均设为order
	                for(var i=1;i<=length;i++){
	                    $("#order_d4").remove();
	                }

	                if(pagesum<sizes){
	                    var html2 = '<li class="active" id="order_d4"><a href="#" onclick="showPartter(1)">1</a></li >';
	                    $("#pages_d4").after(html2);


	                }else{

	                    if(pageno<pagenum){

	                        if(pageno+length-1<=pagenum){
	                            var html2="";
	                            if(pageno-2>0){
	                                for(var i =pageno-2;i<=pageno+length-1-2;i++){
	                                    if(i==pageno)
	                                        html2 += '<li class="active" id="order_d4"><a href="#" onclick="showPartter('+i+')">'+i+'</a></li >';
	                                    else

	                                        html2 += '<li id="order_d4"><a href="#" onclick="showPartter('+i+')">'+i+'</a></li >';
	                                }
	                            }
	                            else{

	                                for(var i =1;i<=length;i++){
	                                    if(i==pageno)
	                                        html2 += '<li class="active" id="order_d4"><a href="#" onclick="showPartter('+i+')">'+i+'</a></li >';
	                                    else

	                                        html2 += '<li id="order_d4"><a href="#" onclick="showPartter('+i+')">'+i+'</a></li >';
	                                }
	                            }

	                            //alert(html2);
	                            $("#pages_d4").after(html2);

	                        }/* if */
	                        else{
	                            var html2="";
	                            if(pagenum>=length){
	                                for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){


	                                    if(i==pageno)
	                                        html2 += '<li class="active" id="order_d4"><a href="#" onclick="showPartter('+i+')">'+i+'</a></li >';
	                                    else
	                                        html2 += '<li id="order_d4"><a href="#" onclick="showPartter('+i+')">'+i+'</a></li >';

	                                }
	                                //alert(html2);
	                                $("#pages_d4").after(html2);
	                            }
	                            else{
	                                for(var i =1;i<=pagenum;i++){
	                                    if(i==pageno)
	                                        html2 += '<li class="active" id="order_d4"><a href="#" onclick="showPartter('+i+')">'+i+'</a></li >';
	                                    else
	                                        html2 += '<li id="order_d4"><a href="#" onclick="showPartter('+i+')">'+i+'</a></li >';
	                                }
	                                $("#pages_d4").after(html2);
	                            }
	                        }
	                    }
	                    else{
	                        if(pageno==pagenum){
	                            var html2="";
	                            for(var i =pageno-length+1>0?pageno-length+1:1;i<=pagenum;i++){
	                                if(i==pageno)
	                                    html2 += '<li class="active" id="order_d4"><a href="#" onclick="showPartter('+i+')">'+i+'</a></li >';
	                                else
	                                    html2 += '<li id="order_d4"><a href="#" onclick="showPartter('+i+')">'+i+'</a></li >';
	                            }
	                            $("#pages_d4").after(html2);
	                        }/* if */
	                    }
	                }

	               // $("#tot").empty();
	               
	                $("#pages1_d4").empty();
	                $("#pages2_d4").empty();
	                
	                $("#tot_d4").empty();
	                var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
	                $("#tot_d4").append(html3);
	                /* 	var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
	                    $("#tot").append(html3); */


	                var html5 = '<a href="#" onclick="showPartter('+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
	                $("#pages1_d4").append(html5);
	                
	                var html4 = '<a href="#" onclick="showPartter('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
	                $("#pages2_d4").append(html4);

	                $("#tbcont").empty();
	                var logs =data.listfromMysql;
	            $.each(logs,function(i,item){
	            var html='<tr>'+
	                        '<td class="c12" style="text-align:left;display:none;" id="id'+i+'">'+item.id+'</td>'+
					 	    '<td class="c12" style="text-align:left;" id="partterName'+i+'">'+item.partterName+'</td>'+
							'<td class="c12" style="text-align:left;" id="partterDes'+i+'">'+item.partterDes+'</td>'+
							'<td class="c12" style="text-align:left;" id="createTime'+i+'">'+item.createTime+'</td>'+
							
							
							'<td class="c12" style="text-align:left;" id="sectionid'+i+'">'+item.sectionid+'</td>'+
							'<td class="c12" style="text-align:left;"id="departid'+i+'">'+item.departid+'</td>'+
							'<td class="c12" style="text-align:left;" id="partterShou'+i+'">'+item.partterShou+'</td>'+
							'<td class="c12" style="text-align:left;" id="createName'+i+'">'+item.createName+'</td>'+
							'<td class="c12" style="text-align:left;"><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_edituser" onclick="return setEditInfo('+i+')">编辑</a></td>'+
							'<td class="c12" style="text-align:left;"><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_deluser" onclick="return setDelId('+item.id+')">删除</a></td>'+
						'</tr>';
	            $("#tbcont").append(html);	
	            });
	            $("#givePages_parttesss").val("");
	            
	        }
	    });
	}
	
	
	function myloader(param,success,error){
	    $.ajax({
	        url:"<%=basePath %>admin/initDSTree.php",
	        data:{
	        },
	        type:"POST",
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
	
	/*回车事件  */
	$(function () { 
		/* $('#uname').focus(); */ //把焦点放在第一个文本框 
		var $inp = $('#uname'); //所有的input元素 
		$inp.keypress(function (e) { //这里给function一个事件参数命名为e，叫event也行，随意的，e就是IE窗口发生的事件。 
		var key = e.which; //e.which是按键的值 
		if (key == 13) { 
			showPartter(1);
		} 
		}); 
		});
	//回车搜索事件  分页
	function onKeyDown_parttesss(event){
		   var e = event || window.event || arguments.callee.caller.arguments[0];
		   if(e && e.keyCode==13){ // enter 键
			   showPartter(1);
		   }
		}
	</script>
</body>
</html>