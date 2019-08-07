<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<style>
.alcenter {
	text-align: center;
}
.inline {
	display: inline-block;
}
.form-inline .form-control {
	width: auto;
}

.c06 clabel {
	margin-right: 14px;
	font-weight:bold;
}

.mybtn {
	position: fixed;
	right: 10px;
	bottom: 20px;
	display: block;
	width: 50px;
	height: 50px;
	border-radius: 50px;
	padding: 0px;
	text-align: center;
	line-height: 50px;
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


.fade_suc,.fade_fas,.fade_poi{
                width:275px;
                height:80px;
                border-radius: 12px;
                position:fixed;
                margin:0 auto;
                margin-top:94px;
                line-height: 80px;
                display:none;
                left:50%;
                margin-left:-138px;
            }
            .fade_suc{background-color:rgba(0,165,47,.5);}
            .fade_fas{background-color:rgba(242,23,23,.5);}
            .fade_poi{background-color:rgba(71,94,156,.5);}
            .fade_suc img,.fade_fas img,.fade_poi img{
                width:40px;
                height:40px
                display:inline-block;
                margin:20px 20px 20px 70px;
                vertical-align: middle;
            }
            .fade_poi img{margin:20px 20px 20px 35px;}
            .fade_suc span,.fade_fas span,.fade_poi span{
                color:#fff;
                font-size:16px;
            }
</style>
<script type="text/javascript">
//删除嫌疑人
function delSuspect(){
	//var checkbox = $("#cb").val();
	var stunos=[];
	$("input[name='cb']:checked").each(function(){
		stunos.push($(this).val());
	});
	var checkbox="";
	//确认导出
	if(stunos.length>0){
		var rs = confirm("确认删除所选择的项？");
		if(rs) {
		for(var i=0;i<stunos.length;i++){
			if(i == 0) {
				checkbox+=stunos[i];
			} else {
				checkbox+=","+stunos[i];
			}
			$.ajax({
				type:"post", 
		        url:"<%=basePath%>admin/delSuspect_page.php",
		        dataType:"json",
		        data:{
		           "checkbox":checkbox         
		        },
		        success: function(data){
		        	//alert(JSON.stringify(data));
		        	if(data.res=="succ"){
		        		alert("删除成功");
		        		
			       		location.reload();
			       		$('input:checkbox').removeAttr('checked');
		        	}
		  
		        },
		     	error: function(data) {
		    		alert("删除失败");
				} 
		   	 });
		}
		}
	}
}

function subForm(pageno){
	var givePages_suspect_list = $("#givePages_suspect_list").val();
	if(givePages_suspect_list != ""){
		pageno = parseInt(givePages_suspect_list);
	}   
	
	$("#pageno").val(pageno);
	$("#searchForm").submit();
}

function searchbycondit() {
	var suspectName=$("#suspectName").val();
	var suspectPhone=$("#suspectPhone").val();
	var suspectMail=$("#suspectMail").val();
	var suspectQQ=$("#suspectQQ").val();
	var suspectIDCardNumber=$("#suspectIDCardNumber").val();
	var suspectPassport=$("#suspectPassport").val();
	if(suspectName!=null&&suspectName!=""||suspectPhone!=null&&suspectPhone!=""||suspectMail!=null&&suspectMail!="" 
			||suspectQQ!=null&&suspectQQ!=""||suspectIDCardNumber!=null&&suspectIDCardNumber!=""||suspectPassport!=null&&suspectPassport!=""){
		$("#searchForm").submit();
		return true;
	}else{
		 $("#js-point").click(function(){
             $(".fade_poi").fadeIn();
        })
         setInterval("$('.fade_poi').fadeOut()",9000);
		return false;
	}
	
	
	
}

function searchone(obj) {
	var id = obj;
	$("#editSuspectA").attr("href","<%=basePath %>editSuspect.php?id="+id);
	$.ajax({
        url:"<%=basePath %>getSuspectOne.php",
        data:{
           "id":id            
        },
        type:"post",
        dataType:"json",
        success: function(data){
           $("#suspectName_right").html(data.json.suspectName);
           /* if(data.suspectSex == 0) {
        	   $("#suspectSex_right").html("男");
           } else {
        	   $("#suspectSex_right").html("女");
           } */
           $("#suspectSex_right").html(data.json.suspectSex);
           /* $("#suspectHomeAddress_right").html(data.suspectProvince+" - "+data.suspectCity+" - "+data.suspectTown); */     
           $("#suspectHomeAddress_right").html(data.json.suspectHomeAddress);     
           $("#suspectPhone_right").html(data.json.suspectPhone);
           $("#suspectMail_right").html(data.json.suspectMail);
           $("#suspectQQ_right").html(data.json.suspectQQ);
           $("#suspectUnitName_right").html(data.json.suspectUnitName);
           $("#suspectUnitAddress_right").html(data.json.suspectUnitAddress);
           $("#createTime_right").html(data.json.createTime);
           $("#suspectIDCardNumber_right").html(data.json.suspectIDCardNumber);
           $("#suspectSocialSecurity_right").html(data.json.suspectSocialSecurity);
           $("#suspectPassport_right").html(data.json.suspectPassport);
           $("#suspectMicroletters_right").html(data.json.suspectMicroletters);
           $("#suspectFacebook_right").html(data.json.suspectFacebook);
           $("#suspectTwitter_right").html(data.json.suspectTwitter);
           
           //性别
            $("#sex").empty();
           var labels = data.json.suspectSex;
           if(labels=="女"){
        	   var sexhtml= '<img class="col-lg-12" src="<%=basePath %>template/img/suspect0.jpg" />'; 
        	   $("#sex").append(sexhtml);
           }else{
        	   var sexhtml= '<img class="col-lg-12"  src="<%=basePath %>template/img/suspect.jpg" />';
        	   $("#sex").append(sexhtml);
           }
           
           //标签
            var labels = data.json.label;
            $("#label1").empty();
            var labelList = labels.split(" ");
            if(labelList!=""){
            	for(var i=0;i<labelList.length;i++){
    				var s = labelList[i];
    				if(i%7 == 1) {
    				$("#label1").append($("<span style='margin-left: 0px;margin-right: 20px;' class='br05 b12 span-1'>"+s+"</span>"));
    				}
    				if(i%7 == 2) {
    				$("#label1").append($("<span  style='margin-left: 0px;margin-right: 20px;'  class='b13 br06 span-2'>"+s+"</span>"));
    				}
    				if(i%7 == 3) {
    				$("#label1").append($("<span  style='margin-left: 0px;margin-right: 20px;'  class='br11 b11 span-3'>"+s+"</span>"));
    				}
    				if(i%7 == 4) {
    				$("#label1").append($("<span  style='margin-left: 0px;margin-right: 20px;'  class='br07 b14 span-4'>"+s+"</span>"));
    				}
    				if(i%7 == 5) {
    				$("#label1").append($("<span  style='margin-left: 0px;margin-right: 20px;'  class='br08 b15 span-5'>"+s+"</span>"));
    				}
    				if(i%7 == 6) {
    				$("#label1").append($("<span  style='margin-left: 0px;margin-right: 20px;'  class='br09 b17 span-6'>"+s+"</span>"));
    				}
    				if(i%7 == 7) {
    				$("#label1").append($("<span  style='margin-left: 0px;margin-right: 20px;'  class='br10  b18 span-7'>"+s+"</span>"));
    				}
    				if(i%7 == 0) {
    				$("#label1").append($("<span  style='margin-left: 0px;margin-right: 20px;'  class='br11 b11 span-3'>"+s+"</span>"));
    				}
    			}
            }
           
           //其他
           var rests = data.json.rests;
           $("#rests").empty();
           var restsList = rests.split("/");
           if(rests!=""){
        	   for(var i=0;i<restsList.length;i++){
            	   var rest = restsList[i];
            	   var rest2 = rest.split(" ");
            	   var name =rest2[0];
            	   var numb =rest2[1];
            	  var restdiv='<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">'+
    							'<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" >'+
    								'<span>'+name+'</span>'+
    								'<span style="float:right;">'+numb+'</span>'+
    							'</div>'+
    						'</div>';
            	  $("#rests").append(restdiv);		
               }
           }
          
         //匹配案件
           var caseinfolist=data.caseinfos;
           $("#main").empty();
           var mainhtml="";
           if(caseinfolist.length>0){
        	  
	           for(var i=0;i<caseinfolist.length;i++){
	        	   var caseinfo = caseinfolist[i];
	        	  mainhtml+='<tr data-toggle="modal" style="color: red;">'+
				        	  ' <td style="text-align: left">'+caseinfo.createdTime+'</td>'+
				        	  ' <td style="text-align: left">'+caseinfo.caseName+'</td>'+
				        	  ' <td style="text-align: left">'+caseinfo.section+'</td>'+
				        	' </tr>';
	           }
           }
           $("#main").append(mainhtml);	
        }
    });
}

function uploadFile(){
    var str = $("#file").val();
    if(str.length!=0){
        var reg = ".*\\.(xls|xlsx)";
        var r = str.match(reg);
        if(r == null){
            alert("对不起，您的文件格式不正确，请重新上传");
        }
        else {
            $('#importForm').submit();
        }
    }
    else {
        alert("请先上传文件");
    }
	/*$("#importForm").submit();*/
}

function toggle(targetid){
    if (document.getElementById){
        target=document.getElementById(targetid);
            if (target.style.display=="block"){
                target.style.display="none";
                $("#moreSS").text("展开更多>");
            } else {
                target.style.display="block";
                $("#moreSS").text("收起↑");
            }
    }
} 

function testId() {
	var getId = $("#suId").val();
	var getName = $("#suName").val();
	alert(getName);
}

function exportFile(){
	//获取所有选中项
	var stunos=[];
	$("input[name='cb']:checked").each(function(){
		stunos.push($(this).val());
	});
	var stunoStr="";
	//确认导出
	if(stunos.length>0){
		var rs = confirm("确定选中所选项?");
		if(rs) {
		for(var i=0;i<stunos.length;i++){
			if(i == 0) {
				stunoStr+=stunos[i];
			} else {
				stunoStr+=","+stunos[i];
			}
			
		}
		$("#url").attr("href","<%=basePath %>ExportWorkSpace.php?docpath="+stunoStr); 
		} else {
            $("#url").attr("href", "javascript:void(0)");
        }
	}else{
		$('#tanchuan').modal('show');
	/* 	alert("请选择需要导出的数据"); */
	}
}

function selectAll(){
	var ckall=document.getElementById("ckall");
    var ck=document.getElementsByName("cb");
    for(var i=0;i<ck.length;i++){
   	   ck[i].checked=ckall.checked;
    }
}

function showChoose(id){
	 var ckall=document.getElementById(id);
	   	ck[i].checked=ckall.checked;
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
//回车搜索事件
function onKeyDown(event){
   var e = event || window.event || arguments.callee.caller.arguments[0];
   if(e && e.keyCode==13){ // enter 键
	   searchbycondit();
   }
}

//回车搜索事件    跳页
function onKeyDown_lists(event){
   var e = event || window.event || arguments.callee.caller.arguments[0];
   if(e && e.keyCode==13){ // enter 键
	   subForm(1);
   }
}
</script>

</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">嫌疑人画像</h1>
	</div>

	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">搜索嫌疑人<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onclick="testOfSearch(this)"></span></div>
					<div class="panel-body" id="searchOfForm" style="display: block;">
						<form class="form-inline" role="form" id = "searchForm" action="<%=basePath%>/getSuspectlist.php" method="post">
							<input type="hidden" name="pageno" id="pageno"/>
							<div class="form-group" style="width:25%">
								<label for="" class="c06 clabel">嫌疑人姓名:</label>
								<input id="suspectName" style="width:60%" name="suspectName" class="form-control" placeholder="请输入..." type="text" 
								    style="width: 390px;" value="${suspectName }" onkeydown="onKeyDown(event)"/>
							</div>
							<div class="form-group" style="width:25%">
								<label for="" class="c06 clabel">手机号码:</label>
								<input id="suspectPhone" style="width:60%" name="suspectPhone" class="form-control" placeholder="请输入..." type="text"
									style="width: 390px;" value="${suspectPhone }" onkeydown="onKeyDown(event)"/>
							</div>
							<div class="form-group" style="width:25%">
								<label for="" class="c06 clabel">邮箱:</label>
								<input id="suspectMail" style="width:60%" name="suspectMail" class="form-control" placeholder="请输入..." type="text" 
									style="width: 390px;" value="${suspectMail }" onkeydown="onKeyDown(event)"/>
							</div>
							
							<button type="submit" id="js-point" class="btn btn-info b06 c02" onclick="searchbycondit()">搜索</button>

							
								 <a href="javascript:void(0);" id="moreSS" style="text-decoration: none;" onclick="toggle('hidden')">更多条件&gt;</a>
							<div id="hidden" style="display: none;">
							<hr>
							<div class="form-group" style="width:25%;">
								<label for="" class="c06 clabel">嫌疑人QQ:</label>
								<input style="width:60%;" id="suspectQQ" name="suspectQQ" class="form-control" placeholder="请输入..." 
									type="text"  value="${suspectQQ }" onkeydown="onKeyDown(event)"/>
							</div>
							<div class="form-group" style="width:25%;">
								<label for="" class="c06 clabel">身份证号:</label>
								<input id="suspectIDCardNumber" name="suspectIDCardNumber" class="form-control" placeholder="请输入..." 
									type="text" style="width: 60%;" value="${suspectIDCardNumber }" onkeydown="onKeyDown(event)"/>
							</div>
							<div class="form-group" style="width:25%;">
								<label for="" class="c06 clabel">护照:</label>
								<input id="suspectPassport" name="suspectPassport" class="form-control" placeholder="请输入..." 
									type="text" style="width: 60%;" value="${suspectPassport }" onkeydown="onKeyDown(event)"/>
							</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		<div class="row">
			
	<form class="form-horizontal" action="exSuspectlist.php" method="POST" enctype="multipart/form-data" id="importForm">
		<a href="<%=basePath %>admin/addSuspect_page.php" class="btn b06 c02"style="margin-right: 30px;width: 150px;">
		新增嫌疑人
		</a>
		<a class="btn b06 c02" onclick="delSuspect()" style="margin-right: 30px; width: 150px;">
		删除嫌疑人
		</a>
		<button type="button" class="btn b06 c02" onclick="file.click()" style="margin-right: 30px;width: 150px;">
		批量导入
		</button>
    	<input type="file" name="fileLoad" id="file" onchange="uploadFile()" style="visibility: hidden; position: absolute;">
		<%-- <a href="<%=basePath %>ExportWorkSpace.php" class="btn b06 c02"style="margin-right: 30px;  width: 150px;">
		批量导出
		</a> --%>
		<a id="url" href="#"><button  class="btn btn-info b06 c02" style="margin-right: 30px;width: 150px;" onclick="exportFile()">批量导出</button></a>
		
	</form>
	
    <div style="margin:20px"></div>
		</div>
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">
					<span>搜索结果</span> 
					</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br04"
							style="text-align: center;">
							<thead>
								<tr>
									<!-- <th style="text-align:left;" id = "suspectCheck" name = "suspectCheck">选择</th> -->
									<th style="text-align:left;"><input type="checkbox" id="ckall" onclick="selectAll()"/></th>
									<th style="text-align:left;" id = "suspectName" name = "suspectName">姓名</th>
									<th style="text-align:left;" id = "suspectPhone" name = "suspectPhone">手机号码</th>
									<th style="text-align:left;" id = "suspectMail" name = "suspectMail">电子邮箱</th>
									<th style="text-align:left;" id = "suspectQQ" name = "suspectQQ">QQ号码</th>
									<th style="text-align:left;" id = "createTime" name = "createTime">创建日期</th>
									<th style="text-align:left;" >操作</th>
								</tr>
							</thead>
							<tbody id="tbcont">
								 <c:forEach items="${logs }" var="si">
									<tr onclick="searchone(${si.id })">
										<td style="display: none;text-align:left;">${si.id }</td>
										<td style="text-align:left;"><input type="checkbox" value="${si.id }" name="cb" id="cb"></td>
										<td style="text-align:left;" data-toggle="modal" data-target="#myModal">${si.suspectName }</td>
										<td style="text-align:left;" data-toggle="modal" data-target="#myModal">${si.suspectPhone }</td>
										<td style="text-align:left;" data-toggle="modal" data-target="#myModal">${si.suspectMail }</td>
										<td style="text-align:left;" data-toggle="modal" data-target="#myModal">${si.suspectQQ }</td>
										<td style="text-align:left;" data-toggle="modal" data-target="#myModal">${si.createTime }</td>
										<td style="text-align:left;" data-toggle="modal" data-target="#myModal" id="${si.id }" >画像</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div style="text-align:center;" style="font-size: 14px">
							<div class="pagecount inline" style="height: 29px;">
								<span>共${totalNum }条，当前${nowPage }/${totalPages }页</span>
							</div>
							<div class="pagebar inline" style="position: absolute; right: 126px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;">
									<li><a href="javascript:void(0)" onclick="subForm('${nowPage-1<1?1:nowPage-1}')">&lt;</a></li>
									<c:forEach items="${pageList }" var="pageNO">
				            			<li <c:if test="${nowPage==pageNO }">class="active"</c:if>>
				            				<a href="javascript:void(0)" onclick="subForm('${pageNO}')">${pageNO}</a>
				            			</li>
				            		</c:forEach>
									<li><a href="javascript:void(0)" onclick="subForm('${nowPage+1>totalPages?totalPages:nowPage+1}')">&gt;</a></li>
								</ul>
							</div>
							<div style="float: right;margin-right: 11px;">
								<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages_suspect_list" name="givePages_suspect_list" onkeydown="onKeyDown_lists(event)"/>
								<button type="button" class="btn" onclick="subForm(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
							</div>
							
						</div>
					</div>
				</div>
			</div>
			<div class="fade_poi" style="display:none;">
					            		<img src="<%=basePath %>template/img/30.png">
					            		<span>请输入搜索的关键词~</span>
								</div>
		</div>

	<!-- 用户画像弹窗 -->
  <div class="modal right fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="padding-right: 10px; overflow-y:auto; width :100%; height:100%;">
			<div class="modal-content">
			
				<div class="bg-light lter b-b wrapper-md"><h1 class="m-n h4">嫌疑人画像</h1></div>
				<div class="wrapper-md" style=" overflow:hidden;">
				<!-- 左侧	 -->
				<div class="col-lg-4 ">
						 <div class="" style="padding-left: 20px;padding-top: 0px;height:6%;">
							<a class="btn btn-default" href="<%=basePath%>getSuspectlist.php" target="mainFrame" style=" margin-left: 7px;">返回</a>
						 </div>
						 
				 	     <div class="col-md-5" style=" width :100%; height:10%; ">
								<span id="label1" > 
									
							    </span>
						 </div>
				
					     <div class="col-md-5" style=" width :100%; height:45%; ">
					    		<div style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 5px;  padding-bottom: 5px;" ><div class="c09" style="text-align:center;height: 50px;font-family : 微软雅黑,宋体;font-size : 1.5em;">基本资料</div></div>
								</div>
					     
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>姓名</span><span id = "suspectName_right" style="float:right;">张三</span></div>
								</div>
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>性别</span><span id = "suspectSex_right" style="float:right;">张三</span></div>
								</div>
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>位置</span><span id = "suspectHomeAddress_right" style="float:right;">张三</span></div>
								</div>
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>身份证号</span><span id = "suspectIDCardNumber_right" style="float:right;">张三</span></div>
								</div>
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>社保号</span><span id = "suspectSocialSecurity_right" style="float:right;">张三</span></div>
								</div>
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>护照号</span><span id = "suspectPassport_right" style="float:right;">张三</span></div>
								</div>
						 </div>
						 
						   <div class="col-md-5" style=" width :100%; height:40%; ">
					    		<div style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 5px;  padding-bottom: 5px;" ><div class="c09" style="text-align:center;height: 50px;font-family : 微软雅黑,宋体;font-size : 1.5em;">社交账号</div></div>
								</div>
					     
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>QQ号</span><span id = "suspectQQ_right" style="float:right;">464984654</span></div>
								</div>
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>微信</span><span id = "suspectMicroletters_right" style="float:right;">essd5445</span></div>
								</div>
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>Facebook</span><span id = "suspectFacebook_right" style="float:right;">zhangsan1963</span></div>
								</div>
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>Twitter</span><span id = "suspectTwitter_right" style="float:right;">zhangsan1963</span></div>
								</div>
						 </div>
				</div>
				<!-- 中间 人物图片	 -->
				<div class="col-lg-4 ">
					<div id="sex" class="col-lg-12 ">
						<img class="col-lg-12" src="<%=basePath %>template/img/suspect1.png" />
					</div>	
				</div>
				
				<!-- 右侧	 -->
				<div class="col-lg-4 ">
				
			 			 <div class="col-md-5" style=" width :100%; height:10%; ">
					    		<div >
										<div class="">
											<!-- <button class="btn"
												style="margin-right: 30px; background-color: #475e9c; color: #fff; width: 150px;">编辑</button> -->
											<a href="<%=basePath%>editSuspect.php" class="btn b06 c02" id="editSuspectA"
											style="margin-right: 30px; width: 150px;">编辑</a>
											<!-- <button class="btn"
												style="background-color: #475e9c; color: #fff; width: 150px;">案件详情</button> -->
										</div>
									</div>
									<div style="margin:20px"></div>
						 </div>
				
					     <div class="col-md-5" style=" width :100%; height:20%; ">
					    		<div style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 5px;  padding-bottom: 5px;" ><div class="c09" style="text-align:center;height: 50px;font-family : 微软雅黑,宋体;font-size : 1.5em;">联系方式</div></div>
								</div>
					     
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>手机号</span><span id = "suspectPhone_right" style="float:right;">1359864645三</span></div>
								</div>
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>电子邮箱</span><span id = "suspectMail_right" style="float:right;">135646465@qq.com</span></div>
								</div>
								
						 </div>
						 
						   <div class="col-md-5" style=" width :100%; height:20%; ">
					    		<div style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 5px;  padding-bottom: 5px;" ><div class="c09" style="text-align:center;height: 50px;font-family : 微软雅黑,宋体;font-size : 1.5em;">所在组织</div></div>
								</div>
					     
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>组织名称</span><span id = "suspectUnitName_right" style="float:right;">某某公司</span></div>
								</div>
								<div class="panel panel-default" style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 10px;padding-bottom: 5px;font-size : 0.6em;" ><span>组织地址</span><span id = "suspectUnitAddress_right" style="float:right;">上海市普陀区</span></div>
								</div>
						  </div> 
						  
						    <div class="col-md-5" style=" width :100%; height:30%; ">
					    		<div style=" position:relative;overflow-y :;margin-bottom: 5px; ">
									<div class="panel-heading"  style=" padding-top: 5px;  padding-bottom: 5px;" ><div class="c09" style="text-align:center;height: 50px;font-family : 微软雅黑,宋体;font-size : 1.5em;">其他</div></div>
								</div>
					        <div id ="rests" >
								
							</div>	
							
								<div class="panel panel-default" style="position:relative;margin-top: 50px; ">
									<div class="panel-heading">资料匹配结果</div>
									<table id="datatable" class="table table-striped table-hover br04"
										style="text-align: center;">
										<thead>
											<tr>
												<th style="text-align: left">匹配日期</th>
												<!-- <th style="text-align: right">命中项</th> -->
												<th style="text-align: left">涉嫌案件名称</th>
												<th style="text-align: left">案件所属科室</th>
											</tr>
										</thead>
										<tbody id="main" >
										
										</tbody>
									</table>
								</div>
						  </div> 
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<!--批量导出弹窗 -->
		<div class="modal fade" id="tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title" id="myModalLabel" style="color: #58666e;border-left: none;">批量导出</h3>
					</div>
					<div class="modal-body">请选择要导出的数据！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div> 
		<!--删除成功弹窗 -->
		<div class="modal fade" id="tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title" id="myModalLabel" style="color: #58666e;border-left: none;">确认删除</h3>
					</div>
					<div class="modal-body">删除成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div> 
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script>
			var a = $("#suspectQQ").val();
			var b = $("#suspectIDCardNumber").val();
			var c = $("#suspectPassport").val();
			
			var d = $("#suspectName").val();
			var e = $("#suspectPhone").val();
			var f = $("#suspectMail").val();
			
			var ss = "" + b + c + a + d + e + f;
			$(document).ready(function() {
				if ("" != ss) {
					$("#hidden").css("display", "block");
				}
			});
			$("#tbcont tr").hover(function() {
				$(this).addClass("hover");
			}, function() {
				$(this).removeClass("hover");
			});
		</script>
</body>
</html>