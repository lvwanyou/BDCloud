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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script type="text/javascript">
	$(document).ready( function(){
		var div_height = $("#wrapper-md-ds").height()-40;
		//alert(div_height);
		$("#ds_div").css("height", div_height+"px");
	});
	
	
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
.datagrid-header td{border:none;}
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
	.tree-title{font-size:14px;height:30px;line-height:30px;}
	.tree-checkbox0,.tree-hit,.tree-checkbox1,.tree-checkbox2{margin-top:5px;}
/* 	.pagination > .active > a, .pagination > .active > span, .pagination > .active > a:hover, .pagination > .active > span:hover, .pagination > .active > a:focus, .pagination > .active > span:focus {
    z-index: 2;
    color: #ffffff;
    background-color: #337ab7;
    border-color: #337ab7;
    cursor: default; */
}
</style>
<script type="text/javascript">
function subForm(pageno){
	$("#pageno").val(pageno);
	$("#searchForm").submit();
}
</script>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">用户管理</h1>
	</div>

	<div id="wrapper-md-ds" class="wrapper-md" style="height:760px;padding-bottom: 20px;">
		<div class="col-md-2" style="padding-right: 20px;margin-left: -15px;height: 500px;">
			<div class="row" style="height: 500px;">
				<div class="panel panel-default" style="height: 760px;">
					<div class="panel-heading" style="margin-bottom:15px;">选择单位</div>
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
									<thead class="br21 b30 c26"
										style="text-align: center; display: none;">
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
					<div id="loadDiv_user" style="text-align: center;margin-top: 10px;position: absolute;left:50%;z-index:99;display: none;">
				 		<img alt="" src="<%=basePath%>template/img/loading3.gif">
					</div>
					<div class="panel-heading">内容</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br04" style="text-align: center;">
							<thead  id="tou" >
								
							</thead>
							<tbody  id="tbcont">
								 	
							</tbody>
						</table>
						
						<!-- 分页 -->
						<div class="alcenter" style="font-size: 14px; padding-top: 20px; padding-bottom: 20px;">
							<div  class="pagecount inline" style="height: 29px; padding-left: 0%;">
								<span id="tot">
								
								</span>
							</div>
							<div class="pagebar inline" style="position: absolute; right: 128px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;" id="pageUL">
								
								</ul>
							</div>
							<div style="float: right;margin-right: 11px;">
								<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages_phoneQuzheng" name="givePages_phoneQuzheng" onkeydown="onKeyDown(event)"/>
								<button type="button" class="btn" onclick="searchUser(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
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
	
	function myloader(param,success,error){
	    $.ajax({
	        url:"<%=basePath %>phone/hemlDataTree.php",
	        data:{
	        },
	        type:"get",
	        dataType:"json",
	        success: function(data){
	            success(data); 
	        }
	    });
	}
	var dsId;
	function onClickCells_ds(field,row){
		var tempid = String(row.id);
		dsId=tempid;
		searchUser(1);
	}
	
	function searchUser(page){
		
		var givePages_phoneQuzheng = $("#givePages_phoneQuzheng").val();
    	if(givePages_phoneQuzheng != ""){
    		page = parseInt(givePages_phoneQuzheng);
    	}
		
		$.ajax({
			type: "POST",
			url: "<%=basePath %>phone/getPhoneData.php",
			dataType : "json",
			data : {
				"pageIndex":page,
				"dsId":dsId
			},							    
			success : function(data) {
				var sum=data.count;//总数
				var sum2=10;//每页个数
				var sum3=parseInt( sum /(sum2+1)+1);//总页数
				//分页开始
				$("#pageUL").html("");
				var tmp = "";
				if(parseInt(page) == 1)
					tmp += '<li id="orderA_5"><a href="javascript:void(0)" onclick="searchUser('+sum3+')">&lt;</a></li >';
				else
					tmp += '<li id="orderA_5"><a href="javascript:void(0)" onclick="searchUser('+ (parseInt(page) - 1) +')">&lt;</a></li >';
				var flag=0;
				for(p = 1; p <= sum3; p++){
					if(p>=page-5 && flag<5){
						if(p == page)
							tmp += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="searchUser('+ p +')">'+p+'</a></li >';
						else
							tmp += '<li id="orderA_5"><a href="javascript:void(0)" onclick="searchUser('+ p +')">'+p+'</a></li >';
						flag++;
					}
				}
				if(page == sum3)
					tmp += '<li id="orderA_5"><a href="javascript:void(0)" onclick="searchUser(1)">&gt;</a></li >';
				else
					tmp += '<li id="orderA_5"><a href="javascript:void(0)" onclick="searchUser('+ (parseInt(page) + 1) +')">&gt;</a></li >';
				$("#pageUL").html(tmp);
				$("#tot").html("共"+sum+"条,当前"+page+"/"+sum3+"页");
				//分页结束
				$("#tou").empty();
				$("#tbcont").empty();
				var touhtml="";
				var conthtml="";
				var phoneList = data.phoneList;
				$.each(phoneList,function(i,item){
					//电子邮箱 QQ邮箱通讯录对象					
					if(dsId=="qqemail_mailList"){
						touhtml='<tr>'+
						'<td  style="text-align:left">名称</td>'+
						'<td  style="text-align:left">邮箱</td>'+
						'<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						'<td  style="text-align:left">'+item.cName+'</td>'+
						'<td  style="text-align:left">'+item.mail+'</td>'+
						'<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//通话记录
					if(dsId=="phone_callLog"){
						touhtml='<tr>'+
						'<td  style="text-align:left">用户名</td>'+
						'<td  style="text-align:left">用户电话</td>'+
						'<td  style="text-align:left">呼叫状态</td>'+
						'<td  style="text-align:left">呼叫时间</td>'+
						'<td  style="text-align:left">呼叫时长</td>'+
						'<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						'<td  style="text-align:left">'+item.name+'</td>'+
						'<td  style="text-align:left">'+item.telPhone+'</td>'+
						'<td  style="text-align:left">'+item.callStatus+'</td>'+
						'<td  style="text-align:left">'+item.callTime+'</td>'+
						'<td  style="text-align:left">'+item.duration+'</td>'+
						'<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//通讯录
					if(dsId=="phone_mailList"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">用户名</td>'+
						 '<td  style="text-align:left">用户手机号</td>'+
						 '<td  style="text-align:left">是否删除</td>'+
						 '<td  style="text-align:left">创建时间</td>'+
						 '<td  style="text-align:left">更新时间</td>'+
						 '<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.name+'</td>'+
						 '<td  style="text-align:left">'+item.telphone.split(";")[0]+'</td>'+
						 '<td  style="text-align:left">'+item.deleteFlag+'</td>'+
						 '<td  style="text-align:left">'+item.createTime+'</td>'+
						 '<td  style="text-align:left">'+item.lastUpdateTime+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//手机信息 短信联系人对象
					if(dsId=="phone_smsContact"){
						touhtml='<tr>'+
						'<td  style="text-align:left">用户名</td>'+
						'<td  style="text-align:left">手机号</td>'+
						'<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						'<td  style="text-align:left">'+item.name+'</td>'+
						'<td  style="text-align:left">'+item.telphone+'</td>'+
						'<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//QQ邮箱 与邮件相关页面对象（垃圾箱 已删除 已发送 收件箱 草稿箱）
					if(dsId=="qqEmail"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">发件人</td>'+
						 '<td  style="text-align:left">收件人</td>'+
						 '<td  style="text-align:left">抄送</td>'+
						 '<td  style="text-align:left">密送</td>'+
						 '<td  style="text-align:left">主题</td>'+
						 '<td  style="text-align:left">内容</td>'+
						 '<td  style="text-align:left">附件</td>'+
						 '<td  style="text-align:left">附件路径</td>'+
						 '<td  style="text-align:left">发送时间</td>'+
						 '<td  style="text-align:left">已读</td>'+
						 '<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.sender+'</td>'+
						 '<td  style="text-align:left">'+item.recipient+'</td>'+
						 '<td  style="text-align:left">'+item.cc+'</td>'+
						 '<td  style="text-align:left">'+item.time+'</td>'+
						 '<td  style="text-align:left">'+item.bcc+'</td>'+
						 '<td  style="text-align:left">'+item.theme+'</td>'+
						 '<td  style="text-align:left">'+item.content+'</td>'+
						 '<td  style="text-align:left">'+item.annex+'</td>'+
						 '<td  style="text-align:left">'+item.annexPath+'</td>'+
						 '<td  style="text-align:left">'+item.read+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//支付宝陌生人、好友页面对象
					if(dsId=="zfb_friends"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">备注名称</td>'+
						 '<td  style="text-align:left">昵称</td>'+
						 '<td  style="text-align:left">全名</td>'+
						 '<td  style="text-align:left">认证状态</td>'+
						 '<td  style="text-align:left">账号</td>'+
						 '<td  style="text-align:left">会员等级</td>'+
						 '<td  style="text-align:left">地区</td>'+
						 '<td  style="text-align:left">签名</td>'+
						 '<td  style="text-align:left">手机联系人</td>'+
						 '<td  style="text-align:left">手机号</td>'+
						 '<td  style="text-align:left">头像</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.memoName+'</td>'+
						 '<td  style="text-align:left">'+item.nickName+'</td>'+
						 '<td  style="text-align:left">'+item.fullName+'</td>'+
						 '<td  style="text-align:left">'+item.authStatus+'</td>'+
						 '<td  style="text-align:left">'+item.acc+'</td>'+
						 '<td  style="text-align:left">'+item.memberLevel+'</td>'+
						 '<td  style="text-align:left">'+item.area+'</td>'+
						 '<td  style="text-align:left">'+item.signature+'</td>'+
						 '<td  style="text-align:left">'+item.contacts+'</td>'+
						 '<td  style="text-align:left">'+item.telephone+'</td>'+
						 '<td  style="text-align:left">'+item.avatar+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					//程序列表页面对象
					if(dsId=="phone_AppList"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">名称</td>'+
						 '<td  style="text-align:left">包名</td>'+
						 '<td  style="text-align:left">版本</td>'+
						 '<td  style="text-align:left">类型</td>'+
						 '<td  style="text-align:left">大小</td>'+
						 '<td  style="text-align:left">更新日期</td>'+
						 '<td  style="text-align:left">安装路径</td>'+
						 '<td  style="text-align:left">权限</td>'+
						 '<td  style="text-align:left">图标</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.name+'</td>'+
						 '<td  style="text-align:left">'+item.packageName+'</td>'+
						 '<td  style="text-align:left">'+item.version+'</td>'+
						 '<td  style="text-align:left">'+item.type+'</td>'+
						 '<td  style="text-align:left">'+item.size+'</td>'+
						 '<td  style="text-align:left">'+item.updateDate+'</td>'+
						 '<td  style="text-align:left">'+item.installPath+'</td>'+
						 '<td  style="text-align:left">'+item.permission+'</td>'+
						 '<td  style="text-align:left">'+item.icon+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					
					//应用程序地理位置页面对象
					if(dsId=="phone_AppLocation"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">应用名称</td>'+
						 '<td  style="text-align:left">用户ID</td>'+
						 '<td  style="text-align:left">用户名</td>'+
						 '<td  style="text-align:left">纬度</td>'+
						 '<td  style="text-align:left">经度</td>'+
						 '<td  style="text-align:left">地点</td>'+
						 '<td  style="text-align:left">详细地址</td>'+
						 '<td  style="text-align:left">时间</td>'+
						 '<td  style="text-align:left">分享对象ID</td>'+
						 '<td  style="text-align:left">来源账号名称</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.appName+'</td>'+
						 '<td  style="text-align:left">'+item.userId+'</td>'+
						 '<td  style="text-align:left">'+item.userName+'</td>'+
						 '<td  style="text-align:left">'+item.latitude+'</td>'+
						 '<td  style="text-align:left">'+item.longitude+'</td>'+
						 '<td  style="text-align:left">'+item.location+'</td>'+
						 '<td  style="text-align:left">'+item.add+'</td>'+
						 '<td  style="text-align:left">'+item.time+'</td>'+
						 '<td  style="text-align:left">'+item.shareObjId+'</td>'+
						 '<td  style="text-align:left">'+item.srcAccId+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					
					//系统日志 应用程序使用记录 页面对象
					if(dsId=="phone_AppUseRecord"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">包名</td>'+
						 '<td  style="text-align:left">最后更新时间</td>'+
						 '<td  style="text-align:left">包路径</td>'+
						 '<td  style="text-align:left">最新修改时间</td>'+
						 '<td  style="text-align:left">最后运行时间</td>'+
						 '<td  style="text-align:left">第一次安装时间</td>'+
						 '<td  style="text-align:left">状态</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.packageName+'</td>'+
						 '<td  style="text-align:left">'+item.lastUpdateTime+'</td>'+
						 '<td  style="text-align:left">'+item.packagePath+'</td>'+
						 '<td  style="text-align:left">'+item.newestModifiedTime+'</td>'+
						 '<td  style="text-align:left">'+item.lastRunTime+'</td>'+
						 '<td  style="text-align:left">'+item.firstInstallTime+'</td>'+
						 '<td  style="text-align:left">'+item.status+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					//浏览器书签页面对象
					if(dsId=="browser_bookmarks"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">标题</td>'+
						 '<td  style="text-align:left">网址</td>'+
						 '<td  style="text-align:left">日期</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.title+'</td>'+
						 '<td  style="text-align:left">'+item.url+'</td>'+
						 '<td  style="text-align:left">'+item.date+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					
					//各个浏览器的实力记录页面对象
					if(dsId=="browser_history"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">标题</td>'+
						 '<td  style="text-align:left">网址</td>'+
						 '<td  style="text-align:left">日期</td>'+
						 '<td  style="text-align:left">访问次数</td>'+
						 '<td  style="text-align:left">最后访问时间</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.title+'</td>'+
						 '<td  style="text-align:left">'+item.url+'</td>'+
						 '<td  style="text-align:left">'+item.date+'</td>'+
						 '<td  style="text-align:left">'+item.times+'</td>'+
						 '<td  style="text-align:left">'+item.lastTime+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					//浏览器Cookies页面对象
					if(dsId=="browser_cookies"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">名称</td>'+
						 '<td  style="text-align:left">修改时间</td>'+
						 '<td  style="text-align:left">最后访问时间</td>'+
						 '<td  style="text-align:left">文件名称</td>'+
						 '<td  style="text-align:left">用户名</td>'+
						 '<td  style="text-align:left">点击次数</td>'+
						 '<td  style="text-align:left">Cookies文件路径</td>'+
						 '<td  style="text-align:left">预览路径</td>'+
						 '<td  style="text-align:left">域名</td>'+
						 '<td  style="text-align:left">路径</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.name+'</td>'+
						 '<td  style="text-align:left">'+item.modifiedDate+'</td>'+
						 '<td  style="text-align:left">'+item.lastTime+'</td>'+
						 '<td  style="text-align:left">'+item.fileName+'</td>'+
						 '<td  style="text-align:left">'+item.userName+'</td>'+
						 '<td  style="text-align:left">'+item.clickTimes+'</td>'+
						 '<td  style="text-align:left">'+item.cookiesPath+'</td>'+
						 '<td  style="text-align:left">'+item.previewPath+'</td>'+
						 '<td  style="text-align:left">'+item.domainName+'</td>'+
						 '<td  style="text-align:left">'+item.path+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					

					//滴滴出行 消息页面对象
					if(dsId=="Didi_Mess"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">发送者ID</td>'+
						 '<td  style="text-align:left">发送者昵称</td>'+
						 '<td  style="text-align:left">接收者ID</td>'+
						 '<td  style="text-align:left">接收者昵称</td>'+
						 '<td  style="text-align:left">消息</td>'+
						 '<td  style="text-align:left">时间</td>'+
						 '<td  style="text-align:left">消息类型</td>'+
						 '<td  style="text-align:left">附件路径</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.senderId+'</td>'+
						 '<td  style="text-align:left">'+item.senderName+'</td>'+
						 '<td  style="text-align:left">'+item.recipientId+'</td>'+
						 '<td  style="text-align:left">'+item.recipientName+'</td>'+
						 '<td  style="text-align:left">'+item.mess+'</td>'+
						 '<td  style="text-align:left">'+item.time+'</td>'+
						 '<td  style="text-align:left">'+item.messType+'</td>'+
						 '<td  style="text-align:left">'+item.annex+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					//滴滴出行 个人信息页面对象
					if(dsId=="Didi_PersonMess"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">手机号码</td>'+
						 '<td  style="text-align:left">当前位置</td>'+
						 '<td  style="text-align:left">当前城市</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.telephone+'</td>'+
						 '<td  style="text-align:left">'+item.add+'</td>'+
						 '<td  style="text-align:left">'+item.city+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}	
					//滴滴出行 个人设置对象
					if(dsId=="Didi_PersonSet"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">家庭地址</td>'+
						 '<td  style="text-align:left">公司地址</td>'+
						 '<td  style="text-align:left">家庭经度</td>'+
						 '<td  style="text-align:left">家庭纬度</td>'+
						 '<td  style="text-align:left">公司经度</td>'+
						 '<td  style="text-align:left">公司维度</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.homeAdd+'</td>'+
						 '<td  style="text-align:left">'+item.compAdd+'</td>'+
						 '<td  style="text-align:left">'+item.homeLongitude+'</td>'+
						 '<td  style="text-align:left">'+item.homeLatitude+'</td>'+
						 '<td  style="text-align:left">'+item.compLongitude+'</td>'+
						 '<td  style="text-align:left">'+item.compLatitude+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					//地图信息 腾讯地图 搜索位置页面对象
					if(dsId=="TMap_LoactionSearch"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">位置ID</td>'+
						 '<td  style="text-align:left">时间</td>'+
						 '<td  style="text-align:left">位置名称</td>'+
						 '<td  style="text-align:left">地址</td>'+
						 '<td  style="text-align:left">经度</td>'+
						 '<td  style="text-align:left">纬度</td>'+
						 '<td  style="text-align:left">备注</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.addId+'</td>'+
						 '<td  style="text-align:left">'+item.time+'</td>'+
						 '<td  style="text-align:left">'+item.addname+'</td>'+
						 '<td  style="text-align:left">'+item.add+'</td>'+
						 '<td  style="text-align:left">'+item.longitude+'</td>'+
						 '<td  style="text-align:left">'+item.latitude+'</td>'+
						 '<td  style="text-align:left">'+item.remarks+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					//手机内的媒体文件（图片 视频）页面对象
					if(dsId=="phone_MediaFile"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">保存路径</td>'+
						 '<td  style="text-align:left">文件名</td>'+
						 '<td  style="text-align:left">大小</td>'+
						 '<td  style="text-align:left">修改时间</td>'+
						 '<td  style="text-align:left">创建时间</td>'+
						 '<td  style="text-align:left">路径</td>'+
						 '<td  style="text-align:left">是否删除</td>'+ 
						 '<td  style="text-align:left">本机拍摄(图片)</td>'+
						 '<td  style="text-align:left">文件md5</td>'+
						 '<td  style="text-align:left">自拍（图片）</td>'+
						 '<td  style="text-align:left">md5加密</td>' ;
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.file+'</td>'+
						 '<td  style="text-align:left">'+item.fileName+'</td>'+
						 '<td  style="text-align:left">'+item.size+'</td>'+
						 '<td  style="text-align:left">'+item.modifiedTime+'</td>'+
						 '<td  style="text-align:left">'+item.createTime+'</td>'+
						 '<td  style="text-align:left">'+item.filePath+'</td>'+
						'<td  style="text-align:left">'+item.deleteflag+'</td>'+ 
						 '<td  style="text-align:left">'+item.localShoot+'</td>'+
						 '<td  style="text-align:left">'+item.fileMd5+'</td>'+
						 '<td  style="text-align:left">'+item.selfTimer+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td>' ;
					}
					//短信 彩信 页面对象
					if(dsId=="phone_Message"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">通讯对象</td>'+
						 '<td  style="text-align:left">发送者</td>'+
						 '<td  style="text-align:left">类型</td>'+
						 '<td  style="text-align:left">时间</td>'+
						 '<td  style="text-align:left">内容</td>'+
						 '<td  style="text-align:left">图片(彩信)</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.otherSide+'</td>'+
						 '<td  style="text-align:left">'+item.sender+'</td>'+
						 '<td  style="text-align:left">'+item.type+'</td>'+
						 '<td  style="text-align:left">'+item.time+'</td>'+
						 '<td  style="text-align:left">'+item.content+'</td>'+
						 '<td  style="text-align:left">'+item.img+'</td></tr>';
					}
					//微信公众号消息
					if(dsId=="WeChat_OfficialAccMess"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">公众号</td>'+
						 '<td  style="text-align:left">标题</td>'+
						 '<td  style="text-align:left">图片</td>'+
						 '<td  style="text-align:left">描述</td>'+
						 '<td  style="text-align:left">链接</td>'+
						 '<td  style="text-align:left">发布时间</td>'+
						 '<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.officialAcc+'</td>'+
						 '<td  style="text-align:left">'+item.title+'</td>'+
						 '<td  style="text-align:left">'+item.pic+'</td>'+
						 '<td  style="text-align:left">'+item.description+'</td>'+
						 '<td  style="text-align:left">'+item.url+'</td>'+
						 '<td  style="text-align:left">'+item.time+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//拍照位置信息页面对象
					if(dsId=="phone_PhotoLocation"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">名称</td>'+
						 '<td  style="text-align:left">大小</td>'+
						 '<td  style="text-align:left">类型</td>'+
						 '<td  style="text-align:left">日期</td>'+
						 '<td  style="text-align:left">路径</td>'+
						 '<td  style="text-align:left">经度</td>'+
						 '<td  style="text-align:left">纬度</td>'+
						 '<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.name+'</td>'+
						 '<td  style="text-align:left">'+item.size+'</td>'+
						 '<td  style="text-align:left">'+item.type+'</td>'+
						 '<td  style="text-align:left">'+item.date+'</td>'+
						 '<td  style="text-align:left">'+item.path+'</td>'+
						 '<td  style="text-align:left">'+item.longitude+'</td>'+
						 '<td  style="text-align:left">'+item.latitude+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//QQ邮箱 账号信息对象
					if(dsId=="qqEmail_IdInfo"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">邮箱地址</td>'+
						 '<td  style="text-align:left">昵称</td>'+
						 '<td  style="text-align:left">密码</td>'+
						 '<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.mail+'</td>'+
						 '<td  style="text-align:left">'+item.nickName+'</td>'+
						 '<td  style="text-align:left">'+item.pwd+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//腾讯地图 搜索路线对象
					if(dsId=="TMap_RouteSearch"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">路线ID</td>'+
						 '<td  style="text-align:left">路线类型</td>'+
						 '<td  style="text-align:left">起点名称</td>'+
						 '<td  style="text-align:left">起点位置</td>'+
						 '<td  style="text-align:left">起点经度</td>'+
						 '<td  style="text-align:left">起点纬度</td>'+
						 '<td  style="text-align:left">终点名称</td>'+
						 '<td  style="text-align:left">终点地址</td>'+
						 '<td  style="text-align:left">终点经度</td>'+
						 '<td  style="text-align:left">终点纬度</td>'+
						 '<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.routeId+'</td>'+
						 '<td  style="text-align:left">'+item.routeType+'</td>'+
						 '<td  style="text-align:left">'+item.startName+'</td>'+
						 '<td  style="text-align:left">'+item.startAdd+'</td>'+
						 '<td  style="text-align:left">'+item.startLongitude+'</td>'+
						 '<td  style="text-align:left">'+item.startLatitude+'</td>'+
						 '<td  style="text-align:left">'+item.endName+'</td>'+
						 '<td  style="text-align:left">'+item.endAdd+'</td>'+
						 '<td  style="text-align:left">'+item.endLongitude+'</td>'+
						 '<td  style="text-align:left">'+item.endLatitude+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//腾讯地图  个人信息对象
					if(dsId=="TMap_PersonMess"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">用户ID</td>'+
						 '<td  style="text-align:left">用户名</td>'+
						 '<td  style="text-align:left">昵称</td>'+
						 '<td  style="text-align:left">生日</td>'+
						 '<td  style="text-align:left">性别</td>'+
						 '<td  style="text-align:left">头像</td>'+
						 '<td  style="text-align:left">邮箱</td>'+
						 '<td  style="text-align:left">电话</td>'+
						 '<td  style="text-align:left">所在地</td>'+
						 '<td  style="text-align:left">签名</td>'+
						 '<td  style="text-align:left">新浪微博ID</td>'+
						 '<td  style="text-align:left">新浪微博昵称</td>'+
						 '<td  style="text-align:left">qq号码</td>'+
						 '<td  style="text-align:left">qq昵称</td>'+
						 '<td  style="text-align:left">淘宝ID</td>'+
						 '<td  style="text-align:left">淘宝昵称</td>'+
						 '<td  style="text-align:left">联通WO+ID</td>'+
						 '<td  style="text-align:left">最近更新时间</td>'+
						 '<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.userId+'</td>'+
						 '<td  style="text-align:left">'+item.userName+'</td>'+
						 '<td  style="text-align:left">'+item.nickName+'</td>'+
						 '<td  style="text-align:left">'+item.birth+'</td>'+
						 '<td  style="text-align:left">'+item.sex+'</td>'+
						 '<td  style="text-align:left">'+item.avatar+'</td>'+
						 '<td  style="text-align:left">'+item.mail+'</td>'+
						 '<td  style="text-align:left">'+item.telephone+'</td>'+
						 '<td  style="text-align:left">'+item.add+'</td>'+
						 '<td  style="text-align:left">'+item.signature+'</td>'+
						 '<td  style="text-align:left">'+item.sinaId+'</td>'+
						 '<td  style="text-align:left">'+item.sinaName+'</td>'+
						 '<td  style="text-align:left">'+item.qq+'</td>'+
						 '<td  style="text-align:left">'+item.qqName+'</td>'+
						 '<td  style="text-align:left">'+item.tbId+'</td>'+
						 '<td  style="text-align:left">'+item.tbName+'</td>'+
						 '<td  style="text-align:left">'+item.CUId+'</td>'+
						 '<td  style="text-align:left">'+item.updateTime+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//短信息
					if(dsId=="phone_shortMsg"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">发件人</td>'+
						 '<td  style="text-align:left">收件人</td>'+
						 '<td  style="text-align:left">发送时间</td>'+
						 '<td  style="text-align:left">接收时间</td>'+
						 '<td  style="text-align:left">发送内容</td>'+
						 '<td  style="text-align:left">接收内容</td>'+
						 '<td  style="text-align:left">原始/删除</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.sender+'</td>'+
						 '<td  style="text-align:left">'+item.addressee+'</td>'+
						 '<td  style="text-align:left">'+item.sendTime+'</td>'+
						 '<td  style="text-align:left">'+item.acceptTime+'</td>'+
						 '<td  style="text-align:left">'+item.sendContent+'</td>'+
						 '<td  style="text-align:left">'+item.acceptContent+'</td>'+
						 '<td  style="text-align:left">'+item.isDelete+'</td></tr>';
					}
					//淘宝搜索历史页面对象
					if(dsId=="taobao_history"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">关键字	</td>'+
						 '<td  style="text-align:left">搜索时间</td>'+
						 '<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.keyWord+'</td>'+
						 '<td  style="text-align:left">'+item.time+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//系统日志中 使用过的号码 页面对象
					if(dsId=="phone_used"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">号码</td>'+
						 '<td  style="text-align:left">ICCID</td>'+
						 '<td  style="text-align:left">最后更新时间</td>'+
						 '<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.number+'</td>'+
						 '<td  style="text-align:left">'+item.iccid+'</td>'+
						 '<td  style="text-align:left">'+item.lastUpdateTime+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//微信通讯录下的页面对象
					if(dsId=="WeChat_Contacts"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">微信ID</td>'+
						 '<td  style="text-align:left">好友账号</td>'+
						 '<td  style="text-align:left">好友昵称</td>'+
						 '<td  style="text-align:left">性别</td>'+
						 '<td  style="text-align:left">个性签名</td>'+
						 '<td  style="text-align:left">备注</td>'+
						 '<td  style="text-align:left">所在地</td>'+
						 '<td  style="text-align:left">头像</td>'+
						 '<td  style="text-align:left">qq账号</td>'+
						 '<td  style="text-align:left">qq昵称</td>'+
						 '<td  style="text-align:left">邮箱地址</td>'+
						 '<td  style="text-align:left">移动电话</td>'+
						 '<td  style="text-align:left">描述</td>'+
						 '<td  style="text-align:left">标签</td>'+
						 '<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.wxId+'</td>'+
						 '<td  style="text-align:left">'+item.friendAcc+'</td>'+
						 '<td  style="text-align:left">'+item.friendNick+'</td>'+
						 '<td  style="text-align:left">'+item.sex+'</td>'+
						 '<td  style="text-align:left">'+item.signature+'</td>'+
						 '<td  style="text-align:left">'+item.remarks+'</td>'+
						 '<td  style="text-align:left">'+item.location+'</td>'+
						 '<td  style="text-align:left">'+item.avatar+'</td>'+
						 '<td  style="text-align:left">'+item.qq+'</td>'+
						 '<td  style="text-align:left">'+item.qqNick+'</td>'+
						 '<td  style="text-align:left">'+item.mail+'</td>'+
						 '<td  style="text-align:left">'+item.telephone+'</td>'+
						 '<td  style="text-align:left">'+item.description+'</td>'+
						 '<td  style="text-align:left">'+item.tab+'</td>'+
						 '<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//微信群相关页面对象
					if(dsId=="WeChat_Group"){
						touhtml='<tr>'+
						 '<td  style="text-align:left">群账号</td>'+
						 '<td  style="text-align:left">群名称</td>'+
						 '<td  style="text-align:left">群主号码/td>'+
						 '<td  style="text-align:left">群主昵称</td>'+
						 '<td  style="text-align:left">成员个数</td>'+
						 '<td  style="text-align:left">我在本群的昵称</td>'+
						 '<td  style="text-align:left">成员昵称</td>'+
						'<td  style="text-align:left">成员账号</td>'+
						'<td  style="text-align:left">成员个性签名</td>'+
						'<td  style="text-align:left">成员所在地</td>'+
						'<td  style="text-align:left">成员头像</td>'+
						'<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						 '<td  style="text-align:left">'+item.groupId+'</td>'+
						 '<td  style="text-align:left">'+item.groupName+'</td>'+
						 '<td  style="text-align:left">'+item.groupLord+'</td>'+
						 '<td  style="text-align:left">'+item.lordNick+'</td>'+
						 '<td  style="text-align:left">'+item.memberNum+'</td>'+
						 '<td  style="text-align:left">'+item.myNick+'</td>'+
						 '<td  style="text-align:left">'+item.memberNick+'</td>'+
						'<td  style="text-align:left">'+item.memberId+'</td>'+
						'<td  style="text-align:left">'+item.memberSignature+'</td>'+
						'<td  style="text-align:left">'+item.memberLocation+'</td>'+
						'<td  style="text-align:left">'+item.memberAvatar+'</td>'+
						'<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//微信媒体文件（图片、音频、视频）页面对象
					if(dsId=="WeChat_MediaFile"){
						touhtml='<tr>'+
						'<td  style="text-align:left">保存路径</td>'+
						'<td  style="text-align:left">文件名</td>'+
						'<td  style="text-align:left">大小</td>'+
						'<td  style="text-align:left">修改时间</td>'+
						'<td  style="text-align:left">创建时间</td>'+
						'<td  style="text-align:left">所在路径</td>'+
						'<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						'<td  style="text-align:left">'+item.file+'</td>'+
						'<td  style="text-align:left">'+item.fileName+'</td>'+
						'<td  style="text-align:left">'+item.size+'</td>'+
						'<td  style="text-align:left">'+item.modifiedTime+'</td>'+
						'<td  style="text-align:left">'+item.createTime+'</td>'+
						'<td  style="text-align:left">'+item.filePath+'</td>'+
						'<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
					//微信 聊天记录
					if(dsId=="WeChat_Message"){
						touhtml='<tr>'+
						'<td  style="text-align:left">通讯对象</td>'+
						'<td  style="text-align:left">发送者</td>'+
						'<td  style="text-align:left">类型</td>'+
						'<td  style="text-align:left">消息类型</td>'+
						'<td  style="text-align:left">内容</td>'+
						'<td  style="text-align:left">时间</td></tr>';
						conthtml+='<tr>'+
						'<td  style="text-align:left">'+item.otherSide+'</td>'+
						'<td  style="text-align:left">'+item.sender+'</td>'+
						'<td  style="text-align:left">'+item.type+'</td>'+
						'<td  style="text-align:left">'+item.messType+'</td>'+
						'<td  style="text-align:left">'+item.content+'</td>'+
						'<td  style="text-align:left">'+item.time+'</td></tr>';
					}
					//位置信息中 Wi-Fi信息 页面对象
					if(dsId=="phone_WiFi"){
						touhtml='<tr>'+
						'<td  style="text-align:left">MAC</td>'+
						'<td  style="text-align:left">名称</td>'+
						'<td  style="text-align:left">最近加入时间</td>'+
						'<td  style="text-align:left">经度</td>'+
						'<td  style="text-align:left">纬度</td>'+
						'<td  style="text-align:left">地平线</td>'+
						'<td  style="text-align:left">探测时间</td>'+
						'<td  style="text-align:left">终端IP地址</td>'+
						'<td  style="text-align:left">加密方式</td>'+
						'<td  style="text-align:left">连接方式</td>'+
						'<td  style="text-align:left">md5加密</td></tr>';
						conthtml+='<tr>'+
						'<td  style="text-align:left">'+item.MAC+'</td>'+
						'<td  style="text-align:left">'+item.name+'</td>'+
						'<td  style="text-align:left">'+item.lastJoinTime+'</td>'+
						'<td  style="text-align:left">'+item.longitude+'</td>'+
						'<td  style="text-align:left">'+item.latitude+'</td>'+
						'<td  style="text-align:left">'+item.horizon+'</td>'+
						'<td  style="text-align:left">'+item.detectionTime+'</td>'+
						'<td  style="text-align:left">'+item.terminalIp+'</td>'+
						'<td  style="text-align:left">'+item.encryption+'</td>'+
						'<td  style="text-align:left">'+item.connType+'</td>'+
						'<td  style="text-align:left">'+item.md5+'</td></tr>';
					}
				});
				$("#givePages_phoneQuzheng").val("");
				$("#tou").append(touhtml);
				$("#tbcont").append(conthtml);
			},
			error : function(data) {
				alert("查询失败，请重试!");
			}
		});
	}
	//回车搜索事件
	function onKeyDown(event){
	   var e = event || window.event || arguments.callee.caller.arguments[0];
	   if(e && e.keyCode==13){ // enter 键
		   searchUser(1);
	   }
	}
	</script>
</body>
</html>