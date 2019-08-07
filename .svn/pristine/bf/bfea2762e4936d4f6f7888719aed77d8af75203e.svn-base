<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%-- <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>

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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.js"></script>
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/jquery.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/jquery.min.js"></script>

<style>
.alcenter {
	text-align: center;
}

.inline {
	display: inline-block;
}
.form-inline .form-control {
	width:auto;
}
.clabel {
	margin-right:14px;
}


.modal.left .modal-dialog, .modal.right .modal-dialog {
	position: fixed;
	margin: auto;
	width: 620px;
	height: 100%;
	-webkit-transform: translate3d(0%, 0, 0);
	-ms-transform: translate3d(0%, 0, 0);
	-o-transform: translate3d(0%, 0, 0);
	transform: translate3d(0%, 0, 0);
}

.modal.left .modal-content, .modal.right .modal-content {
	height: 100%;
	overflow-y: auto;
}

.modal.left .modal-body, .modal.right .modal-body {
	padding: 15px 15px 80px;
}

/*Left*/
.modal.left.fade .modal-dialog {
	left: -320px;
	-webkit-transition: opacity 0.3s linear, left 0.3s ease-out;
	-moz-transition: opacity 0.3s linear, left 0.3s ease-out;
	-o-transition: opacity 0.3s linear, left 0.3s ease-out;
	transition: opacity 0.3s linear, left 0.3s ease-out;
}

.modal.left.fade.in .modal-dialog {
	left: 0;
}

/*Right*/
.modal.right.fade .modal-dialog {
	right: -320px;
	-webkit-transition: opacity 0.3s linear, right 0.3s ease-out;
	-moz-transition: opacity 0.3s linear, right 0.3s ease-out;
	-o-transition: opacity 0.3s linear, right 0.3s ease-out;
	transition: opacity 0.3s linear, right 0.3s ease-out;
}

.modal.right.fade.in .modal-dialog {
	right: 0;
}

/* ----- MODAL STYLE ----- */
.modal-content {
	border-radius: 0;
	border: none;
}
.td_left {
	text-align: left;
}

.td_right {
	text-align: right;
}
.red_num {
	width: 15px;
	height: 15px;
	border-radius:50%;
	float:right;
	text-align:center;
}
</style>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	
<script type="text/javascript">
//分页
function subForm(pageno){
	$("#pageno").val(pageno);
	$("#searchForm").submit();
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
		<h1 class="m-n h4">标记管理</h1>
	</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row" style="margin-top: 1%;">
				<div class="panel panel-default">
					<div class="panel-heading">搜索<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onclick="testOfSearch(this)"></span></div>
					<div class="panel-body" id="searchOfForm" style="display: block;">
						<form class="form-inline" role="form" id="searchForm" action="<%=basePath%>admin/getEvidence.php" method="post">
							<input type="hidden" name="pageno" id="pageno"/>
							<div class="form-group" >
								<label for="" class="clabel">姓名:</label>
								<input type="text" value="" style="display: none;" />
								<input id="name" name="name"
									class="form-control" placeholder="请输入姓名..." type="text" style="width:214px;" onkeydown="onKeyDown(event)"/>
							</div>
							<button type="button" class="btn btn-info" style="width:80px;margin-left:1%" onclick="getSign(1)">搜索</button>
						</form>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">标记列表</div>
	<!--     				<div class="panel-body" id="searchOfForm" style="display: block;padding:10px 10px 0 10px;"> -->
						<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br04" style="text-align: center;">
							<thead>
								<tr>
									<th style="text-align:center;" id = "Turstee" name = "Turstee"  >标记人姓名</th>
									<th style="text-align:center;" id = "Department">所属部门</th>
									<th style="text-align:center;" id = "Session">所属科室</th>
									<th style="text-align:center;" id = "Case" name = "Case">案件名称</th>
									
									<th style="text-align:center;">标记数量</th>
								</tr>
							</thead>
							<tbody id="tbcont">
							
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
							<!-- <div style="float: right;margin-right: 11px;">
								<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages" name="givePages" onkeydown="onKeyDown(event)"/>
								<button type="button" class="btn" onclick="getSign(1)" style="width:52px;height:28px;line-height: 12px;"  onclick="getSign(1)">跳转</button>
							</div> -->
						</div>
					</div>	
			<!-- 	</div> -->
				</div>
			</div>
		
	</div>
	</div>
<jsp:include page="footer2.jsp"></jsp:include>
			
		<!-- 查看分组后的标记列表   通过人名查-->
		<div class="modal right fade" id="myModalGroupBy" tabindex="-1" style="width: 55%; height: auto;margin-left: 5%;margin-top: 5%;">
				<div class="panel panel-default">
					<div class="panel-heading">个人标记列表<a href="#"><img src="<%=basePath %>template/img/x.png" style="width: 19px;height: 19px;float: right;" data-dismiss="modal"></a></div>
					<div style="display: none;" id="urlGroupBy"/></div>
				 	<div class="dialog_content">
				    	
			    		<div id=contGroupBy style="padding: 12px;" >
			    			
			    		</div>
			    	</div>
			    	<!-- 分页开始 -->
						<div class="alcenter" style="font-size: 14px; padding-top: 20px; padding-bottom: 20px;">
							<div id="total_d5_div4" class="pagecount inline" style="height: 29px;">
								<span id="tot1_d5_div4"></span>
							</div>
							<div class="pagebar inline" style="position: absolute; right: 0; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;">
									<li id="pages1_d5_div4"></li>
		
									<li id="pages_d5_div4"></li>
		
									<li id="pages2_d5_div4" style="margin-right: 20px;"></li>
								</ul>
							</div>
						</div>
						<!-- 分页结束 -->
				</div>
		</div>	
		
		<!-- 查看分组后的标记列表      通过案件名称查-->
		<div class="modal right fade" id="myModalGroupByCaseName" tabindex="-1" style="width:55%; height: auto;margin-left: 5%;margin-top: 5%;">
				<div class="panel panel-default">
					<div class="panel-heading">个人标记列表<a href="#"><img src="<%=basePath %>template/img/x.png" style="width: 19px;height: 19px;float: right;" data-dismiss="modal"></a></div>
					<div style="display: none;" id="urlGroupByCaseName"/></div>
				 	<div class="dialog_content">
				    	<div id=contGroupByCaseName style="padding: 12px;">
			    			
			    		</div>
			    	</div>
			    	<!-- 分页开始 -->
			    	<div class="alcenter" style="font-size: 14px; padding-top: 20px; padding-bottom: 20px;">
					<div id="total_d5_div5" class="pagecount inline" style="height: 29px;">
						<span id="tot1_d5_div5"></span>
					</div>
					<div class="pagebar inline" style="position: absolute; right: 0; height: 29px;">
						<ul class="pagination pagination-sm" style="margin: 0;">
							<li id="pages1_d5_div5"></li>

							<li id="pages_d5_div5"></li>

							<li id="pages2_d5_div5" style="margin-right: 20px;"></li>
						</ul>
					</div>
					<!-- <div style="float: right;margin-right: 11px;">
						<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages_totByCaseName" onkeydown="onKeyDown_totByCaseName(event)"/>
						<button type="button" class="btn" onclick="showList22ByCaseName('+i+',1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
					</div> -->
				</div>
						<!-- 分页结束 -->
				</div>
		</div>

		<!-- 邮件类型详情弹窗 -->		
		<div class="modal right fade" id="myModal_email" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 35%;">
				<div class="modal-content">
					<div class="panel panel-default" style="padding-right: 10px;">
						<div class="panel-body" style="overflow-y: auto;">
								<span id="test1" ></span>
								<div class="panel-heading" class="alcenter" style="margin-top:10px;margin-left:10;"> 
									邮件内容<span class="modal-title" style="font-size: 22px; width:700px;"></span>
								</div>
								<div class="panel-body" style="padding: 0px; height: 100%;">
									<table class="br04" id="datatable" style="margin-bottom: 0px; width: 100%;font-size:14px;">
										<tr>
											<td class="c20" style="font-weight: bolder; font-size: 17px; padding-left: 30px; padding-top: 20px;font-weight:600;" id="emailsubject">邮件标题:</td>
										</tr>
										<tr>
											<td class="c20" style="padding-left: 30px; padding-top: 10px;font-weight:600;" id="emailtoWho">发件人：</td>
											<td class="c20" style="padding-right: 30px; text-align: right;” id="emaildate"></td>
										</tr>
										<tr>
											<td class="c20" style="padding-left: 30px; padding-top: 10px; padding-bottom: 20px;font-weight:600;" id="emailfromWho">收件人：</td>
											<td></td>
										</tr>
									</table>
									<div style="padding-top: 30px;padding-left: 30px;" id="emailcontent" >
									<%--  <div id="loadDiv" style="text-align: center;margin-top: 20px">
				 						加载中<img alt="" src="<%=basePath %>template/img/loading2.gif">
				 						</div>
									</div> --%>
										<%-- <div id="loadDiv" style="text-align: center;margin-top: 20px">
										 				<img alt="" src="<%=basePath %>template/img/loading2.gif">
										 			</div>
										</div> --%>
									<div class="_modal-mailcontent-append" id="correctEml_attfile" style="overflow: auto;">
								 		</div>
								</div>
							</div>
					</div>
				</div>
			</div>
		</div>
</div>

<script type="text/javascript">
window.onload = getSign(1);
//获取标记list
function getSign(page){
	/* var givePages_noCaseList = $("#givePages").val();
	if(givePages_noCaseList != ""){
		page = parseInt(givePages_noCaseList);
	}
	$("#pageno").val(page); */
	
	var name=$("#name").val();
	$.ajax({
	       url:"<%=basePath %>emaiExcavatel/signEmail.php",
	       data:{
	            "pageIndex":page,
	            "name":name
	       },
	       type:"post",
	       dataType:"json",
	       success : function(data) {
	    	   $("#tbcont").empty();
	    	   $.each(data,function(i,item){   //<a href="#" onclick="showList22('+i+',1)" data-toggle="modal" data-target="#myModalGroupBy">
		    		var html= '<tr onclick="showList22ByCaseName('+i+',1)" data-toggle="modal" data-target="#myModalGroupByCaseName">'+
								'<td id="showList22Name'+i+'">'+item.name+'</td>'+
								'<td>'+item.department+'</td>'+
								'<td>'+item.section+'</td>'+
								'<td id="showList22CaseName'+i+'">'+item.caseID+'</td>'+
								'<td >'+item.num+'</td>'+
							  '</tr> ';
					$("#tbcont").append(html);
	    	   });
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert("失败"); */
			}
	});
}

function saveBiaojiFromListName(i) {
	
	var esID22 = document.getElementById("esID22" + i).innerText;
	var editContent = $("#textareaContent" + i).val();
	$.ajax({
		type : "POST",
		url : "<%=basePath%>emaiExcavatel/editBiaoJi.php",
		data : {
			"esID22":esID22,
			"editContent":editContent
		},
		dataType : "json",
		async: true,
		success : function(data) {
			alert("修改成功！");
			$("#myModalGroupBy").modal('hide');
			getSign(1);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("标记线索失败!");
		}
	});
}

function delectBiaojiFromListName(i) {
	var delectEsID22 = document.getElementById("esID22" + i).innerText;
	$.ajax({
		type : "POST",
		url : "<%=basePath%>emaiExcavatel/delectBiaoJi.php",
		data : {
			"delectEsID22":delectEsID22
			
		},
		dataType : "json",
		async: true,
		success : function(data) {
			alert("删除成功！");
			$("#allInfo" + i).remove();
			//getSign(1);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("删除线索失败!");
			
		}
	});
}
function showList22ByCaseName(i,pageno) {   //getSign
	var userName = document.getElementById("showList22Name" + i).innerText;
	var caseName = document.getElementById("showList22CaseName" + i).innerText;
	
	$.ajax({
	       url:"<%=basePath %>emaiExcavatel/signEmailByCaseName.php",
	       data:{
	    	   "pageIndex" : pageno,
	    	   "caseName":  caseName,
	    	   "userName" : userName
	       },
	       type:"post",
	       dataType:"json",
	       success : function(data) {
	    	   $("#contGroupByCaseName").empty();
	    	    $("#pages_d5_div5").empty();
	    	    var list = data.hits.hits;
	   			
	    	    //分页开始
			    var sizes=3;
				var pagesum = data.hits.total;
				var pagenum = pagesum / sizes;
				var length=5;  //要显示的分页页数

				if(pagenum%1!=0){
					pagenum=pagenum+(1-pagenum%1);
				}
				$("#pages_d5_div5").empty();
				//用于删除之前显示的页数，动态添加时id名均设为order
				for(var x = 1;x <= length;x++)
					  $("#orderA_5225").remove();
				if(pagesum<sizes){
					
					var html2 = '<li class="active" id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+',1)">1</a></li >';
					$("#pages_d5_div5").after(html2);
				}else{

				if(pageno<pagenum){
					if(pageno+length-1<=pagenum){
						var html2="";
						if(pageno-2>0){
							for(var x =pageno-2;x <= pageno+length-1-2;x++){
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+x+')">'+x+'</a></li >';
								else
									html2 += '<li id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+x+')">'+x+'</a></li >';
						   	}
						}else{
							for(var x =1;x<=length;x++){
								if(x==pageno)
									html2 += '<li class="active" id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+x+')">'+x+'</a></li >';
								else
									html2 += '<li id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+x+')">'+x+'</a></li >';
						   	}
						}
						$("#pages_d5_div5").after(html2);
					}else{
						var html2="";
						if(pagenum>=length){
							for(var x =pageno-(length-1-pagenum+pageno);x<=pagenum;x++){
								if(x==pageno)
									html2 += '<li class="active" id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+x+')">'+x+'</a></li >';
								else
									html2 += '<li id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+x+')">'+x+'</a></li >';
							}
							$("#pages_d5_div5").after(html2);
						}else{
							for(var x = 1;x <= pagenum;x++){
								if(x == pageno)
									html2 += '<li class="active" id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+x+')">'+x+'</a></li >';
								else
									html2 += '<li id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+x+')">'+x+'</a></li >';
							}
							$("#pages_d5_div5").after(html2);
						}
					}
				}else{
					if(pageno==pagenum){
						var html2="";
						for(var x =pageno-length+1>0?pageno-length+1:1;x<=pagenum;x++){
							if(x==pageno)
								html2 += '<li class="active" id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+x+')">'+x+'</a></li >';
							else
								html2 += '<li id="orderA_5225"><a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+x+')">'+x+'</a></li >';
					   	}
					   	$("#pages_d5_div5").after(html2);
					}
				}
			}

				$("#tot1_d5_div5").empty();
				$("#pages1_d5_div5").empty();
				$("#pages2_d5_div5").empty();
				var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
				$("#tot1_d5_div5").append(html3);

				var html5 = '<a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
				$("#pages1_d5_div5").append(html5);
				var html4 = '<a href="javascript:void(0)" onclick="showList22ByCaseName('+i+','+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
				$("#pages2_d5_div5").append(html4);
				//分页结束 
	   			
	    	   	$.each(list,function(i,item){
					var con=item._source.content.replace("<", "(").replace(">", ")");
					var html='<div style="overflow: hidden;" id="allInfoByCaseName'+i+'">'+
									'<span style="display: none;text-align:left;" id="esID22ByCaseName'+i+'">'+item._id+'</span>'+
									'<span style="display: none;text-align:left;" id="url'+i+'">'+item._source.file_download_url+'</span>'+
									'<span style="display: none;text-align:left;" id="contentCase'+i+'">'+con+'</span>'+
									'<div>'+
										'<b class="c08" style="text-align:left;color:#475E9C;margin-left:5px;font_size:30px;" id="contentCase'+i+'" onclick="getClueDetails('+i+')" data-target="#myModal_email" data-toggle="modal">'+item._source.emailTitle+'</b><span>(点击查看原邮件)</span>'+
										'<span class="c08" style="float: right;margin-right:5px;">'+item._source.saveTime+'</span>'+
									'</div>'+
									'<span><textarea class="br03" id="textareaContentCase'+i+'" style="resize:none;width:99%;min-height:70%;margin-left:0.5%;" rows="4">'+item._source.content+'</textarea></span>'+
									
									'<div style="overflow: hidden;text-align:right"">'+
									'<button class="btn btn-info" style="width:60px;height: 25px;line-height:1px;margin-right: 6px;margin-top: 5px;" onclick="saveBiaojiFromListByCaseName('+i+')">保存</button>'+
									'<button class="btn w-xs btn-default" style="width:60px;height: 25px;line-height:1px;margin-right: 6px;margin-top: 5px;border:1px #475E9C solid" onclick="delectBiaojiFromListByCaseName('+i+')">删除</button>'+
									'</div>'+
									'<hr style="margin-top:5px;"/>'+
							'</div> ';
					$("#contGroupByCaseName").append(html);	
				  });  
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert("失败"); */
			}
	});
}
function delectBiaojiFromListByCaseName(i) {
	var esID22ByCaseName = document.getElementById("esID22ByCaseName" + i).innerText;
	
	$.ajax({
		type : "POST",
		url : "<%=basePath%>emaiExcavatel/delectBiaoJiByCaseName.php",
		data : {
			"esID22ByCaseName":esID22ByCaseName
			
		},
		dataType : "json",
		async: true,
		success : function(data) {
			alert("删除成功！");
			$("#allInfoByCaseName" + i).remove();
			//getSign(1);
			
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("删除线索失败!");
			
		}
	});
}

function saveBiaojiFromListByCaseName(i) {
	var esID22ByCaseName = document.getElementById("esID22ByCaseName" + i).innerText;
	var editContentCase = $("#textareaContentCase" + i).val();
	
	$.ajax({
		type : "POST",
		url : "<%=basePath%>emaiExcavatel/editBiaoJiByCaseName.php",
		data : {
			"esID22ByCaseName":esID22ByCaseName,
			"editContentCase":editContentCase
		},
		dataType : "json",
		async: true,
		success : function(data) {
			alert("修改成功！");
			$('#myModalGroupByCaseName').modal('hide');
			getSign(1);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("标记线索失败!");
		}
	});
	
}
//查看
function searchone(idi) {
	var url=$("#url"+idi).html();
	var saveTime=$("#saveTime"+idi).html();
	var content=$("#content"+idi).html();
	
	$("#url").html(url);
	$("#time").html("时间: "+saveTime);
	$("#cont").html(content);
}

//查看原文件
function getClueDetails(i) {
	
	var htmljiazai='<div id="loadDiv" style="text-align: center;margin-top: 20px">加载中<img alt="" src="<%=basePath %>template/img/loading2.gif"></div>';
	$("#emailcontent").html(htmljiazai);
	//var url=$("#url").html();
	var url=$("#url"+i).html();
	$.ajax({
       url:"<%=basePath %>clueWarn/getEsClue.php",
       data:{
            "url":url     
       },
       type:"post",
       dataType:"json",
       success: function(data){
    	  var es = data.hits.hits[0];
   		  $("#emailsubject").html("邮件标题: "+es._source.subject);
   		  $("#emailtoWho").html("发件人： "+es._source.toWho);
   		  $("#emaildate").html(es._source.date);
   		  $("#emailfromWho").html("收件人： "+es._source.fromWho);
   		  //$("#emailcontent").html(es._source.content);
   		  emailContent(i);
       } ,
		error: function() {
			//alert("失败");
		}
   }); 
}
//邮件原格式展示
function emailContent(i){
	var emlpath = $("#url" + i).html();
	$.ajax({
		type : "POST",
		url : "<%=basePath%>getCorrectEml.php",
		data : {
			"emlpath":emlpath,
			"attachmentname":"",
			"keyword":"",
			"zhengzeType":""
		},
		dataType : "json",
		async: true,
		success : function(data) {
			$("#emailcontent").html(data.resData.content);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("失败!");
		}
	});
}

//回车搜索事件  getSign
function onKeyDown(event){
   var e = event || window.event || arguments.callee.caller.arguments[0];
   if(e && e.keyCode==13){ // enter 键
	   getSign(1);
   }
}
</script>
</body>
</html>