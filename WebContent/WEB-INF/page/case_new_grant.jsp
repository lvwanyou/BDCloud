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
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
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

.clabel {
	margin-right: 14px;
}
</style>
<script type="text/javascript">
function subForm(pageno){
	var givePages_xietongs2 = $("#givePages_xietongs2").val();
	if(givePages_xietongs2 != ""){
		pageno = parseInt(givePages_xietongs2);
	} 
	
	$("#pageno").val(pageno);
	$("#searchForm").submit();
}

function selectAll(){
	var ckall=document.getElementById("ckall");
    var ck=document.getElementsByName("ids");
    for(var i=0;i<ck.length;i++){
    	ck[i].checked=ckall.checked;
    }
}

function showChoose(){
	var array=document.getElementsByName("ids");
    var str = "";
    var ids=new Array();
    for(var i=0;i<array.length;i++){
    	if(array[i].checked){
        	var id = array[i].value;
        	ids.push(id);
         	str += "、"+$("#"+id+"_name").text();
      	}
    }
    if(ids.length===0){
    	$("#showChooseDiv").hide();
    	$("#showChooseSpan").text("");
    }else{
    	str = str.substring(1);
    	$("#showChooseSpan").text(str);
    	$("#showChooseDiv").show();
  	}
}

function setHref(){
	var array=document.getElementsByName("ids");
    var str = "";
    var noArr = new Array();
    var ids=new Array();
    for(var i=0;i<array.length;i++){
    	if(array[i].checked){
        	var id = array[i].value;
        	ids.push(id);
        	noArr.push($("#"+id+"_no").text());
         	str += "、"+$("#"+id+"_name").text();
      	}
    }
    if(ids.length===0){
    	$('#checkCase_tanchuan').modal('show');
    	/* alert("请至少选择一个案件！"); */
    	return false;
    }else{
    	str = str.substring(1);
  	}
    var param = "id="+ids.toString()+"&noStr="+noArr.toString()+"&str="+str;
	param = encodeURI(param);
	param = encodeURI(param);
    $("#nextA").attr("href", "<%=basePath%>admin/case_new_granttwo.php?"+param);
	return true;
}

function delSelected(){
	var array=document.getElementsByName("ids");
    var ids=new Array();
    for(var i=0;i<array.length;i++){
    	if(array[i].checked){
        	ids.push(array[i].value);
      	}
    }
    if(ids.length===0){
    	$('#checkCase_tanchuan').modal('show');
    	/* alert("请至少选中一条记录"); */
       return false;
    }else{
  	    if (!confirm("确定都删除吗?")) {
       	return false;
      	}else{
      		delImNearby(ids.toString());
      	}
  	 }
}
//回车搜索事件  分页
function onKeyDown_xietong(event){
	   var e = event || window.event || arguments.callee.caller.arguments[0];
	   if(e && e.keyCode==13){ // enter 键
		   subForm('1');
	   }
	}
	

	 
</script>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">新建案件授权</h1>
	</div>
 		<div class="" style="padding-left: 20px;padding-top: 15px;">
			<a href="<%=basePath%>admin/case_xietong.php" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="return">返回上一级</a>
		</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">选择案件</div>
					<div class="panel-body">
						<form class="form-inline" id="searchForm" action="<%=basePath %>admin/case_new_grant.php" method="post">
							<input type="hidden" name="pageno" id="pageno"/>
							<div class="form-group">
								<input id="searchparam" name="searchparam" value="${searchparam }" class="form-control"
									placeholder="请输入案件名称或编号" type="text" style="width: 391px;height: 30px;" />
							</div>
							<button type="button" class="btn btn-info"
								style="width: 75px; height: 30px;" onclick="subForm('1')">搜索</button>

							<div id="showChooseDiv" class="form-group" style="display: none;margin-left: 15px;">
								已选案件：<span class="c25" id="showChooseSpan"></span>
							</div>
							<!-- <a href="javascript:void(0);" style="text-decoration:underline;">更多条件&gt;</a> -->
						</form>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">案件列表</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br19"
							style="text-align: center;">
							<thead>
								<tr>
									<th class="alcenter"><input type="checkbox" id="ckall" onclick="selectAll()"/></th>
									<th class="alcenter">案件编号</th>
									<th class="alcenter">案件名称</th>
									<th class="alcenter">已授权人</th>
								</tr>
							</thead>
							<tbody id="tbcont">
								
								<c:forEach items="${dataList }" var="item">
								 	<tr>
								 		<td><input type="checkbox" name="ids" value="${item.id }" onclick="showChoose();"/></td>
										<td id="${item.id }_no">${item.caseNum }</td>
										<td id="${item.id }_name">${item.caseName }</td>
										<td>${item.trustee }</td>
									</tr>
								 </c:forEach>
							</tbody>
						</table>
						<div class="alcenter" style="font-size: 14px">
							<div class="pagecount inline" style="height: 29px;">
								<span>共${totalNum }条，当前${nowPage }/${totalPages }页</span>
							</div>
							<div class="pagebar inline" style="position: absolute; right: 126px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;">
									<li><a href="#" onclick="subForm('${nowPage-1<1?1:nowPage-1}')">&lt;</a></li>
									<c:forEach items="${pageList }" var="pageNO">
				            			<li <c:if test="${nowPage==pageNO }">class="active"</c:if>>
				            				<a href="#" onclick="subForm('${pageNO}')">${pageNO}</a>
				            			</li>
				            		</c:forEach>
									<li><a href="#" onclick="subForm('${nowPage+1>totalPages?totalPages:nowPage+1}')">&gt;</a></li>
								</ul>
							</div>
							<div style="float: right;margin-right: 11px;">
								<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages_xietongs2" name="givePages_xietongs2" onkeydown="onKeyDown_xietong(event)"/>
								<button type="button" class="btn" onclick="subForm(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div align="center" style="margin-top: 34px;margin-bottom: 30px;">
				<a id="nextA" class="btn b23 c02" href="<%=basePath%>admin/case_new_granttwo.php" onclick="return setHref();"
					style="width: 135px; height: 30px; border-style: hidden;">下一步</a>
			</div>
		</div>
	</div>
	 <!-- 选择案件弹窗 -->
		<div class="modal fade" id="checkCase_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c20" id="myModalLabel" style="border-left: none;">选择案件</h3>
					</div>
					<div class="modal-body">请先选案件授权！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
<jsp:include page="footer2.jsp"></jsp:include>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
</body>
</html>