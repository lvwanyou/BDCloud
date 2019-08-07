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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script type="text/javascript">
	$(document).ready( function(){
		var div_height = $("#wrapper-md-ds").height()-40;
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
			overflow-x:scroll;
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
		overflow:hidden !important;
	}
	.panel-header{
		padding:0;
		border-width:0;
		border-top-width:1px;
	}
	.datagrid-header-row{
		width:100%;
	}
	.datagrid-body{overflow-x:scroll !important;}
	.datagrid-body td{
		height:100%;
		border-width: 0 0 0 0;
	}
	.datagrid-view1 .datagrid-body {
		  overflow: auto !important;
		}
	.panel.datagrid.easyui-fluid{
		overflow:auto;
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
	.datagrid-btable{width:100% !important;}
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
	.im_flag {
	font-size: 18px;
	position: absolute;
	margin-left: -10px;
}
	/* .tree-title{color:#666;font-size:14px;height:30px;line-height:30px;}
	.tree-checkbox0,.tree-hit,.tree-checkbox1,.tree-checkbox2{margin-top:5px;} */
	.tree-title{font-size:14px;height:30px;line-height:30px;}
	.form-inline .form-control{width:200px;}
	 .datagrid-body::-webkit-scrollbar-thumb{overflow:hidden;}
</style>
<script type="text/javascript">
function subForm(pageno){
	
	$("#pageno").val(pageno);
	$("#searchForm").submit();
}

//get机构list
window.onload=addOrganize2();
function addOrganize2(){
   $.ajax({
        url:"<%=basePath %>system/getOrganize.php",
        data:{
        },
        type:"POST",
        dataType:"json",
        success: function(data){
        	$("#addparent").empty();
        	$("#editallparent").empty();
        	var html = '<option value="0" selected="selected">一级机构</option>';
			$("#addparent").append(html);
			$("#editallparent").append(html);
        	$.each(data,function(i,item){
        		//添加弹窗赋值
				var html01 = '<option value="'+item.id+'" >'+item.organizeName+'</option>';
				$("#addparent").append(html01);	
				$("#editallparent").append(html01);
        	});
        }
    });  
}

//get角色
window.onload = getrole();
function getrole(){
 $.ajax({
      url:"<%=basePath %>system/getAllRole.php",
      data:{
      },
      type:"get",
      dataType:"json",
      success: function(data){
      	$("#userprevilige").empty();//edituserprevilige
      	$("#edituserprevilige").empty();
      	$("#adduserprevilige").empty();
      	var aa="<option value='全部'>全部</option>";
      	$("#userprevilige").append(aa);
      	$.each(data,function(i,item){
		    var html1 = 
		    	'<option value="'+item.id+'" >'+item.roleName+'</option>';
			$("#userprevilige").append(html1);	
			$("#edituserprevilige").append(html1);
			$("#adduserprevilige").append(html1);
			});
      }
  });  
};
var dsId;
function searchUser(page){
	var uname = $("#uname").val();
	var userprevilige = $("#userprevilige").val();
	var givePages_usersss = $("#givePages_usersss").val();
	if(givePages_usersss != ""){
		page = parseInt(givePages_usersss);
	}
	
	$("#loadDiv_user").show();
	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/searchUser.php",
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
			for(i = 0; i < len; i++){
				$("#tbcont").append('<tr><td class="c12" style="font-size:14px;text-align:left;">'+data.resData[i].username+'</td>'+
									'<td class="c12" style="font-size:14px;text-align:left;">'+data.resData[i].policeNO+'</td>'+
									'<td class="c12" style="font-size:14px;text-align:left;">'+data.resData[i].cardNO+'</td>'+
									'<td class="c12" style="font-size:14px;text-align:left;">'+data.resData[i].partment+'</td>'+
									'<td class="c12" style="font-size:14px;text-align:left;">'+data.resData[i].section+'</td>'+
									'<td class="c12" style="font-size:14px;text-align:left;">'+data.resData[i].allParent.split(",")[0]+'</td>'+
									'<td class="c12" style="font-size:14px;text-align:left;">'+data.resData[i].createdTime+'</td>'+
									/* '<td class="c12" style="font-size:14px;text-align:left;">'+data.resData[i].privilege.split("/")[1]+'</td>'+     userrole*/
									'<td class="c12" style="font-size:14px;text-align:left;">'+data.resData[i].privilege+'</td>'+
									
									'<td class="c12" style="font-size:14px;text-align:left;">'+data.resData[i].lastLoginTime+'</td>'+
									'<td style="font-size:14px;text-align:left;"><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_edituser" onclick="return setEditInfo(\''+data.resData[i].id+'\',\''+data.resData[i].username+'\',\''+data.resData[i].policeNO+'\',\''+data.resData[i].cardNO+'\',\''+data.resData[i].partment+'\',\''+data.resData[i].section+'\',\''+data.resData[i].allParent.split(",")[1]+'\',\''+data.resData[i].privilege+'\',\''+data.resData[i].telephone+'\',\''+data.resData[i].phone+'\',\''+data.resData[i].email+'\',\''+data.resData[i].sn+'\',\''+data.resData[i].remark+'\',\''+data.resData[i].privilege.split("/")[0]+'\')">编辑</a></td>'+
									'<td style="font-size:14px;text-align:left;"><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_deluser" onclick="return setDelId(\''+data.resData[i].id+'\')">删除</a></td>'+
									'<td style="font-size:14px;text-align:left;"><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_resetPasswd" onclick="return setResetId(\''+data.resData[i].id+'\')">重置密码</a></td>'+
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
			$("#givePages_usersss").val("");
			//location.reload();
		},
		error : function(data) {
			$("#loadDiv_user").hide();
			//alert("查询失败，请重试!");
		}
	});
}

function addUser(){
	
	var addusername=$("#addusername").val().trim();
	var adduserpsw=$("#adduserpsw").val().trim();	
	var adduserprevilige=$("#adduserprevilige").val();
	var adduserremark=$("#adduserremark").val();
	var addsection = $("#addsection").val();
	var addsn = $("#addsn").val().trim();
	//alert(addsection+","+adduserprevilige);
	var parent=$("#addparent").val();
	var addpoliceno=$("#addpoliceno").val().trim();
	var addcardno=$("#addcardno").val().trim();
	var addpartment=$("#addpartment").val().trim();
	//alert(addsection)
	if(addusername==null || addusername==""){
		$('#userName_tanchuan').modal('show');
		/* $("#mistake").html("用户名不能为空！"); */
		return false;
	}
	if(adduserpsw==null || adduserpsw==''){
		$('#userpsw_tanchuan').modal('show');
	/* 	$("#mistake").html("密码不能为空！"); */
		return false;
	}else if(adduserpsw.length<6){
		$('#userpsw_tanchuan').modal('show');
		/* $("#mistake").html("密码长度最少6位！"); */
		return false;
	}
	if(addpoliceno==null || addpoliceno==""){
		$('#policeno_tanchuan').modal('show');
	/* 	$("#mistake").html("警号不能为空！"); */
		return false;
	}
	/* if(addcardno==null || addcardno==""){
		$('#cardno_tanchuan').modal('show');
		/* $("#mistake").html("身份证号不能为空！"); */
		//return false;
	//} */
	if(addpartment==null || addpartment==""){
		$('#partment_tanchuan').modal('show');
		/* $("#mistake").html("部门不能为空！"); */
		return false;
	}
	/* if(addsection!=undefined){
		addsection = addsection.trim();
		if(addsection==''){
			$('#section_tanchuan').modal('show');
			/* $("#mistake").html("科室不能为空！"); */
			//return false;
		//}
	//} */
	/* if(addsn==null || addsn==""){
		$("#mistake").html("加密狗序号不能为空！");
		return false;
	} */
	
	var addtelephone = $("#addtelephone").val().trim();
	var addphone = $("#addphone").val().trim();
	var addemail = $("#addemail").val().trim();
	if(addsection==null){
		//alert("1"+addsection)
		addsection=-1;
	}
	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/addUser.php",
		dataType : "json",
		data : {
			"adduserremark" : adduserremark,
			"adduserprevilige" : adduserprevilige,
			"adduserpsw" : adduserpsw,
			"addusername" : addusername,
			"addsection" : addsection,
			"addsn": addsn,
			"addpoliceno": addpoliceno,
			"addcardno": addcardno,
			"addpartment": addpartment,
			"parent":parent,
			"addtelephone": addtelephone,
			"addphone": addphone,
			"addemail": addemail
		},							    
		success : function(data) {
			$('#table-modal_adduser').modal('hide');
			$("#addusername").val("");
			$("#adduserprevilige").val("");
			//$("#addsection").val("");
			$("#addsn").val("");
			//$("#addpartment").val("");
			$("#addcardno").val("");
			$("#addpoliceno").val("");
			$("#parent").val("");
			$("#adduserremark").val("");
			$("#addtelephone").val("");
			$("#addphone").val("");
			$("#addemail").val("");
			$("#adduserpsw").val("");
			
			if (data.res == "succ") {
				$('#table-modal_addUser_success').modal('show');
				searchUser(1);
//				alert("添加用户成功");
//				window.location.reload();
				//searchUser(1);
			 	//location.reload(); 
			}
			
			else if(data.res == "exist"){
				/* alert("用户已存在，请重试！"); */
				$('#user_tanchuan').modal('show');
				
			}else if(data.res == "exist2"){
				$('#pid_tanchuan').modal('show');
				
				/* alert("警号已存在，请重试！"); */
			}
			 else if(data.res == "exist3"){
				$('#idcard_tanchuan').modal('show');
				
				/*  alert("身份证号已存在，请重试！");  */
			} 
			else if(data.res == "nopower"){
				alert("无此权限！");
			}
			
		},						
		error : function(data) {
			//alert("添加用户失败，请重试!");
		}
	});     
	
}

function setDelId(id){
	$("#delId").val(id);
	return true;
}

function delUser(){
	var delId = $("#delId").val();
	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/delUser.php",
		dataType : "json",
		data : {
			"delId" : delId
		},							    
		success : function(data) {
			$('#table-modal_deluser').modal('hide');
			if (data.res == "succ") {
				$('#table-modal_delUser_success').modal('show');
//				alert("删除用户成功");
				//getUserInfoByJson(1);
				/*  location.reload();  */
				 searchUser(1); 
				
			}
		},						
		error : function(data) {
			$('#table-modal_deluser').modal('hide');
			alert("删除用户失败，请重试!");
		}
	});    
}

function setResetId(id){
	$("#resetId").val(id);
	return true;
}

function resetPasswd(){
	var resetId = $("#resetId").val();
	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/resetPasswd.php",
		dataType : "json",
		data : {
			"resetId" : resetId
		},							    
		success : function(data) {
			$('#table-modal_resetPasswd').modal('hide');
			if (data.res == "succ") {
				$('#table-modal_resetPassword_success').modal('show');
//				alert("密码重置成功");
			}
		},						
		error : function(data) {
			$('#table-modal_resetPasswd').modal('hide');
			alert("密码重置失败，请重试!");
		}
	});    
}

function setEditInfo(id,username,policeNO,cardNO,partment,section,allParent,privilege,telephone,phone,email,sn,remark,roleid){
	$("#editId").val(id);
	$("#editusername").val(username);
	$("#editpoliceno").val(policeNO);
	$("#editcardno").val(cardNO);
	$("#editpartment").val(partment);
	$("#editsection").empty();
	if(partment==""){
		$("#editsection").append('<option></option>');
	}
	 $.ajax({
		type: "POST",
		url: "<%=basePath %>admin/getAllSections.php",
		dataType : "json",
		data : {"department":partment},						
		success : function(data) {
			jQuery.each(data,function(i,item){
				$("#editsection").append('<option>'+item.sectionName+'</option>');
			});
			$("#editsection").val(section);
		},						
		error : function(data) {
		}
	}); 
	$("#editallparent").val(allParent);
	$("#edituserprevilige").val(roleid);
	$("#edittelephone").val(telephone);
	$("#editphone").val(phone);
	$("#editemail").val(email);
	$("#editsn").val(sn);
	$("#edituserremark").val(remark);
	return true;
}

function editUser(){
	var editId = $("#editId").val();
	var editusername=$("#editusername").val().trim();
	var edituserprevilige=$("#edituserprevilige").val();
	var edituserremark=$("#edituserremark").val();
	var editsection = $("#editsection").val();
	var editsn = $("#editsn").val().trim();
	//alert(addsection+","+adduserprevilige);
	var editpoliceno=$("#editpoliceno").val().trim();
	var editcardno=$("#editcardno").val().trim();
	var editpartment=$("#editpartment").val().trim();
	var editallparent = $("#editallparent").val();
	if(editusername==null || editusername==""){
		$('#userName_tanchuan').modal('show');
		/* $("#mistake_edit").html("用户名不能为空！"); */
		return false;
	}
	if(editpoliceno==null || editpoliceno==""){
		$('#policeno_tanchuan').modal('show');
		/* $("#mistake_edit").html("警号不能为空！"); */
		return false;
	}
	/* if(editcardno==null || editcardno==""){
	//	$('#cardno_tanchuan').modal('show');
	/* 	$("#mistake_edit").html("身份证号不能为空！"); */
		//return false;
	//} */
	if(editpartment==null || editpartment==""){
		$('#partment_tanchuan').modal('show');
	/* 	$("#mistake_edit").html("部门不能为空！"); */
		return false;
	}
	/* if(editsection!=undefined){
		editsection = editsection.trim();
		if(editsection==''){
			$('#section_tanchuan').modal('show');
		/* 	$("#mistake_edit").html("科室不能为空！"); */
			//return false;
		//}
	//} */
	/*
	if(addsn==null || addsn==""){
		$("#mistake").html("加密狗序号不能为空！");
		return false;
	}
	*/
	var edittelephone = $("#edittelephone").val().trim();
	var editphone = $("#editphone").val().trim();
	var editemail = $("#editemail").val().trim();
	
	if(editsection==null){
		//alert("1"+addsection)
		editsection=-1;
	}
	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/editUser.php",
		dataType : "json",
		data : {
			"id": editId,
			"userremark" : edituserremark,
			"userprevilige" : edituserprevilige,
			"username" : editusername,
			"editallparent" : editallparent,
			"section" : editsection,
			"sn": editsn,
			"policeno": editpoliceno,
			"cardno": editcardno,
			"partment": editpartment,
			"telephone": edittelephone,
			"phone": editphone,
			"email": editemail
		},							    
		success : function(data) {
			$('#table-modal_edituser').modal('hide');
			if (data.res == "succ") {
				$('#table-modal_editUser_success').modal('show');
				searchUser(1);
			/* 	 location.reload();  */
//				alert("编辑用户成功");
//				window.location.reload();
				//searchUser(1);
			}else if(data.res == "exist"){
				alert("用户名已存在，请重试！");
			}else if(data.res == "nopower"){
				alert("无此权限！");
			}
		},						
		error : function(data) {
			alert("编辑用户失败，请重试!");
		}
	});     
}

//回车搜索事件
function onKeyDown(event){
   var e = event || window.event || arguments.callee.caller.arguments[0];
   if(e && e.keyCode==13){ // enter 键
	   searchUser('1');
   }
}
//回车搜索事件   跳页
function onKeyDown_userss(event){
   var e = event || window.event || arguments.callee.caller.arguments[0];
   if(e && e.keyCode==13){ // enter 键
	   searchUser(1);
   }
}


var contents2 = $('#ds_div div.datagrid-view2 div.datagrid-header');

contents2.scroll(function () {
	alert(11);
    $('#ds_div div.datagrid-view2 div.datagrid-body').prop({ scrollLeft: this.scrollLeft, scrollTop: this.scrollTop });
    contents2.not(this).prop({ scrollLeft: this.scrollLeft, scrollTop: this.scrollTop });
});


</script>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">用户管理</h1>
	</div>

	<div id="wrapper-md-ds" class="wrapper-md" style="height:auto;padding-bottom: 20px;overflow:hidden;">
		<div style="padding-right: 20px;margin-left: -15px;margin-bottom:27px;width:260px;display:inline-block;">
			<div class="row">
				<div class="panel panel-default" style="height: 550px;">
					<div class="panel-heading" style="margin-bottom:15px;">选择单位</div>
					<div id="ds_div" class="panel-body" style="padding-left:15px;">
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
											loader:myloader,
											fitColumns:false,
											autoScroll: true ,
									">
									<thead class="b30 c26 br21" style="font-size: 20px; text-align: center; display: none;">
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
		<div style="width:83%;position:absolute;display:inline-block;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">搜索用户<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onClick="testOfSearch(this)"></span></div>
					<div class="panel-body" id="searchOfForm" style="display: block;">
						<form class="form-inline" role="form" id="searchForm" action="<%=basePath %>admin/userManager.php" method="post">
							<input type="hidden" name="pageno" id="pageno"/>
							<div class="form-group">
								<label class="clabel">用户名:</label>
								<input type="text" value="" style="display: none;" />
								<input type="text" id="uname" name="uname" class="form-control" placeholder="请输入..." onKeyDown="onKeyDown(event)"/>
							</div>
							<div class="form-group" style="margin-left: 10px;">
								<label for="" class="clabel">角色:</label>
								<select id="userprevilige" name="userprevilige" class="form-control" style="width: 200px;" onkeydown="onKeyDown(event)">
									<option value="全部" selected="selected">全部</option>
									<option value="0">管理员</option>
									<option value="4">科员</option>
				 					<!-- <option value="2">副主任科员</option>
				 					<option value="5">主任科员</option>
				 					<option value="1">副科长</option>
				 					<option value="6">主任科员兼副科长</option> -->
				 					<option value="3">科长</option>
				 					<!-- <option value="10">副调研员</option>
				 					<option value="8">调研员</option>
				 					<option value="11">副处长</option>
				 					<option value="9">处长</option>
				 					<option value="12">分局副局长</option>
				 					<option value="13">分局局长</option>
				 					<option value="14">副局长</option> -->
				 					<option value="7">局长</option>
				 				</select>
							</div>
							<button type="button" class="btn btn-info b23 c02"
								style="width: 55px; height: 30px;" onClick="searchUser('1')">搜索</button>
							<button type="button" class="btn btn-info b23 c02" data-toggle="modal" data-target="#table-modal_adduser" onClick="searchsection()"
								style="width: 55px; height: 30px;">新增</button>
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
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">姓名</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">警号</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">身份证号</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">单位名称</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">所属部门</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">所属机构</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">创建时间</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">角色</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">最近登录时间</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">编辑</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">删除</th>
									<th class="c12" style="font-weight:600;font-size:14px;text-align:left;">重置密码</th>
								</tr>
							</thead>
							<tbody id="tbcont">
			 					 <c:forEach items="${users }" var="item">
								 	<tr>
										<td class="c12" style="text-align:left;">${item.username }</td>
										<td class="c12" style="text-align:left;">${item.policeNO }</td>
										<td class="c12" style="text-align:left;">${item.cardNO }</td>
										<td class="c12" style="text-align:left;">${item.partment }</td>
										<td  class="c12" style="text-align:left;">${item.section }</td>
										<td  class="c12" style="text-align:left;">${item.allParent.split(",")[0] }</td>
										<td class="c12" style="text-align:left;">${item.createdTime }</td>
										<td class="c12" style="text-align:left;">${item.privilege.split("/")[1] }</td>
										<td class="c12" style="text-align:left;">${item.lastLoginTime }</td>
										<td class="c12" style="text-align:left;"><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_edituser" onClick="return setEditInfo('${item.id }','${item.username }','${item.policeNO }','${item.cardNO }','${item.partment }','${item.section }','${item.allParent.split(",")[1] }','${item.privilege }','${item.telephone }','${item.phone }','${item.email }','${item.sn }','${item.remark }','${item.privilege.split("/")[0] }')">编辑</a></td>
										<td class="c12" style="text-align:left;"><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_deluser" onClick="return setDelId('${item.id }')">删除</a></td>
										<td class="c12" style="text-align:left;"><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_resetPasswd" onClick="return setResetId('${item.id }')">重置密码</a></td>
									</tr>
								 </c:forEach> 
							</tbody>
						</table>
						<div class="alcenter" style="font-size: 14px">
							<div class="pagecount inline" style="height: 29px;">
								<span>共<span id="totalSpan">${totalNum }</span>条，当前<span id="currSpan">${nowPage }/${totalPages }</span>页</span>
							</div>
							<div class="pagebar inline" style="position: absolute; right: 126px; height: 29px;">
								<ul id="pageUL" class="pagination pagination-sm" style="margin: 0;">
									<li><a href="#" onClick="searchUser('${nowPage-1<1?1:nowPage-1}')">&lt;</a></li>
									<c:forEach items="${pageList }" var="pageNO">
				            			<li <c:if test="${nowPage==pageNO }">class="active"</c:if>>
				            				<a href="#" onClick="searchUser('${pageNO}')">${pageNO}</a>
				            			</li>
				            		</c:forEach>
									<li><a href="#" onClick="searchUser('${nowPage+1>totalPages?totalPages:nowPage+1}')">&gt;</a></li>
								</ul>
							</div>
							<div style="float: right;margin-right: 11px;">
								<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages_usersss" name="givePages_usersss" onKeyDown="onKeyDown_userss(event)"/>
								<button type="button" class="btn" onClick="searchUser(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 添加用户 -->
	  <div class="modal fade" id="table-modal_adduser">							
		<div class="modal-dialog" style="margin-top: 7%;width: 55%;height: 100%;">
		  <div class="modal-content modal-table">
			<div class="modal-header">
			  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			  <h4 class="modal-title" style="font-size: 22px;">添加用户</h4>
			</div>
			<div class="modal-body" style="width: 100%;height:420px;" >	
				<div class="_modal-content" style="margin:15px;">	
					<p id="mistake" class="c22" style="text-align:center;font-size:14px;padding-left:25px;margin-bottom:0;" ></p>
					<div class="form-group">
						<label class="col-lg-3 control-label"><span
								class="im_flag">*</span>用户名</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addusername" name="addusername" onBlur="validateobj(this,'用户名')">
							<span id="addevNumlabel" class="empty c11"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label"><span
								class="im_flag">*</span>密码</label>
						<div class="col-lg-8">
							<input class="form-control" type="password" id="adduserpsw" name="adduserpsw" onBlur="validatePwd(this)">
							<span id="addevNumlabel" class="empty c11"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label"><span
								class="im_flag">*</span>警号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addpoliceno" name="addpoliceno" onBlur="validatePoliceno()" onblur="validateobj(this,'警号')">
							<span id="addevNumlabel" class="empty c11"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">身份证号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addcardno" name="addcardno">
							<!-- <span id="addevNumlabel" class="empty" style="color:red"></span> -->
						</div>
					</div>
 					<div class="form-group">
						<label class="col-lg-3 control-label"><span
								class="im_flag">*</span>部门</label>
						<div class="col-lg-8">
							<select id="addpartment" class="form-control" onChange="getAllSections(1);">
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">科室</label>
						<div class="col-lg-8">
							<select id="addsection" class="form-control">
								<option value="无" selected="selected">无</option>
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">直属机构</label>
						<div class="col-lg-8">
							<select id="addparent" class="form-control"  style="margin-bottom: 20px;">
								
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">角色</label>
						<div class="col-lg-8">
							<select id="adduserprevilige" class="form-control">
								<option value="科员" selected="selected">科员</option>
				 				<!--     <option >副主任科员</option>
				 					<option >主任科员</option>
				 					<option >副科长</option>
				 					<option >主任科员兼副科长</option> -->
				 					<option >科长</option>
				 					<!-- <option >副调研员</option>
				 					<option >调研员</option>
				 					<option >副处长</option>
				 					<option >处长</option>
				 					<option >分局副局长</option>
				 					<option >分局局长</option>
				 					<option >副局长</option> -->
				 					<option >局长</option>
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">手机号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addtelephone" name="addtelephone">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">座机号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addphone" name="addphone">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">邮箱</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addemail" name="addemail">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">加密狗序号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="addsn" name="addsn">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">备注</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="adduserremark" name="adduserremark">
						</div>
					</div>
					<div class="form-group" style="width: 100%;margin-top: 54px;">
						<div class="col-lg-offset-4 col-lg-5">
							<button type="button" class="btn w-xs btn-info" style="margin-right: 30px;" onClick="addUser()">添加</button>
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
						<button type="button" class="btn btn-info" onClick="delUser()">确定</button>
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
						<button type="button" class="btn btn-info" onClick="resetPasswd()">确定</button>
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
					<p id="mistake_edit" class="c22" style="text-align:center;font-size:14px;padding-left:25px;margin-bottom:0;" ></p>
					<input type="hidden" name="editId" id="editId">
					<div class="form-group">
						<label class="col-lg-3 control-label"><span
								class="im_flag">*</span>用户名</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editusername" name="editusername" onBlur="validateobj(this,'用户名')">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label"><span
								class="im_flag">*</span>警号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editpoliceno" name="editpoliceno" onBlur="validateobj(this,'警号')">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">身份证号</label>
						<div class="col-lg-8">
							<input class="form-control" type="text" id="editcardno" name="editcardno">
						</div>
					</div>
					 <div class="form-group">
						<label class="col-lg-3 control-label"><span
								class="im_flag">*</span>部门</label>
						<div class="col-lg-8">
							<select id="editpartment" class="form-control" onChange="getAllSections(2);">
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
						<label class="col-lg-3 control-label">直属机构</label>
						<div class="col-lg-8">
							<select id="editallparent" class="form-control"  style="margin-bottom: 20px;">
								
				 			</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">角色</label>
						<div class="col-lg-8">
							<select id="edituserprevilige" class="form-control">
								<option value="科员" selected="selected">科员</option>
				 				 <!-- <option >副主任科员</option>
				 					<option>主任科员</option>
				 					<option >副科长</option>
				 					<option>主任科员兼副科长</option> -->
				 					<option >科长</option>
				 					<!-- <option >副调研员</option>
				 					<option >调研员</option>
				 					<option >副处长</option>
				 					<option >处长</option>
				 					<option >分局副局长</option>
				 					<option >分局局长</option>
				 					<option >副局长</option> -->
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
							<button type="button" class="btn w-xs btn-info" style="margin-right: 30px;" onClick="editUser()">确定</button>
							<button type="reset" class="btn w-xs btn-default" data-dismiss="modal">取消</button>
						</div>
					</div>									
			 	</div>
			</div>
		  </div>
		</div>
	  </div>
	  <!-- 用户名弹窗 -->
		<div class="modal fade" id="userName_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">用户名</h3>
					</div>
					<div class="modal-body">用户名不能为空！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		 <!-- 用户密码弹窗 -->
		<div class="modal fade" id="userpsw_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">密码</h3>
					</div>
					<div class="modal-body">用户密码不能为空且不能少于六位！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		 <!-- 警号弹窗 -->
		<div class="modal fade" id="policeno_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">警号</h3>
					</div>
					<div class="modal-body">警号不能为空！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		 <!-- 部门弹窗 -->
		<div class="modal fade" id="partment_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">部门</h3>
					</div>
					<div class="modal-body">部门不能为空！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		 <!-- 用户名重复弹窗 -->
		<div class="modal fade" id="user_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">用户</h3>
					</div>
					<div class="modal-body">该用户已存在！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		 <!-- 警号重复弹窗 -->
		<div class="modal fade" id="pid_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">警号</h3>
					</div>
					<div class="modal-body">该警号已存在！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		 <!-- 身份证重复弹窗 -->
		<div class="modal fade" id="idcard_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">身份证</h3>
					</div>
					<div class="modal-body">该身份证已存在！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	
	var sectid ;
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
		sectid = row.id;
		var tempid = String(row.id);
		dsId=tempid;
		searchUser(1);
	}
	
	
	function searchsection(){
		
	    $.ajax({
	        url:"<%=basePath %>admin/searction.php",
	        data:{
	        	"sectid":sectid
	        },
	        type:"POST",
	        dataType:"json",
	        success: function(data){
	            var departmentName = data.departmentName;
	            var departmentName2 = data.departmentName2;
	            var sectionName2 = data.sectionName;
	            var list3 = data.list3;
	            var list4 = data.list4;
	            if(departmentName == null && departmentName == null && sectionName2 == null){
	            }
	            else if(departmentName != null && sectionName2 == null){
					$("#addpartment").val(departmentName);
					$("#addsection").empty();
					$("#addsection").append('<option></option>');
	            	$.each(list3,function(i,item){
	            		$("#addsection").append('<option>'+item+'</option>');
	            	});
	            }else if(departmentName2 != null && sectionName2 != null){
	            	$("#addpartment").val(departmentName2);
	            	$("#addsection").empty();
	            	$("#addsection").append('<option></option>');
	            	$.each(list4,function(i,item2){
	            		$("#addsection").append('<option>'+item2+'</option>');
	            	});
	            	$("#addsection").val(sectionName2);
	            }
	        }
	    });
	}
</script>
</body>
</html>