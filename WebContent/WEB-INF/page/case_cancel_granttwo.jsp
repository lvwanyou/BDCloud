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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<script type="text/javascript" src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
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

	.panel{
		margin-bottom: 0;
	}
	.panel-body{
		padding: 0 0 0 0;
		border: none;
	}
	.panel-header{
		padding:0;
		border-width:0;
		border-top-width:1px;
	}
	.table > thead > tr > th{border-bottom: none;}
	.table {border-collapse: inherit;}
	.table > tbody > tr > td, .table > tfoot > tr > td{border-top: none;}
</style>

</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">取消案件授权</h1>
	</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row"></div>
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">选择人员</div>
					<div class="panel-body" style="padding: 0 0 300px;">
						<input type="hidden" name="caseIDStr" id="caseIDStr" value="${id }"/>
						<input type="hidden" name="caseNOStr" id="caseNOStr" value="${noStr }"/>
						<div style="margin: 20px 130px 8px 130px;word-break:break-all;">已选案件：<span id="showChooseSpan" class="btnA">${str }</span></div>
						<div style="height: 350px;margin: 0px 130px 0px 130px;">
							<div class="br13 b08" style="float: left; width: 47%; height: 100%;overflow-y:auto;">
								<table class="table table-hover" style="text-align: center;">
									<thead>
										<tr>
											<th class="alcenter" style="width: 38%;">姓名</th>
											<th class="alcenter" style="width: 37%;">所在科室</th>
											<th class="alcenter" style="width: 25%;">职务</th>
										</tr>
									</thead>
									<tbody id="tbcont1">
										<c:forEach items="${users }" var="item">
								 			<tr id="${item.id }">
												<td>${item.username }</td>
												<td>${item.section }</td>
												<td>${item.userrole }</td>
											</tr>
								 		</c:forEach>
									</tbody>
								</table>
							</div>
							<div style="float: left;width: 6%;text-align: center;height: 100%;padding-top: 110px;">
								<div>
									<img id="rightMoveBtn" alt="" src="<%=basePath %>template/img/rightmove.png" style="cursor: pointer;"/>
								</div>
								<div style="margin-top: 90px;">
									<img id="leftMoveBtn" alt="" src="<%=basePath %>template/img/leftmove.png" style="cursor: pointer;"/>
								</div>
							</div>
							<div class="br20 b08" style="display: inline-block;width: 47%;height: 100%;overflow-y:auto;">
								<table id="datatable" class="table table-hover" style="text-align: center;">
									<thead>
										<tr>
											<th class="alcenter" style="width: 38%;">姓名</th>
											<th class="alcenter" style="width: 37%;">所在科室</th>
											<th class="alcenter" style="width: 25%;">职务</th>
										</tr>
									</thead>
									<tbody id="tbcont2">
									</tbody>
								</table>
							</div>
						</div>
						<div class="foot_button" align="center" style="margin-top: 54px;">
							<a href="<%=basePath%>admin/case_cancel_grant.php"><button class="btn w-xs btn-default" style="margin-right: 20px;width: 135px;">上一步</button></a>
                			<a id="nextA" href="<%=basePath%>admin/case_cancel_confirm.php" onclick="return setHref();"><button class="btn w-xs btn-info" style="width: 135px;">下一步</button></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- 选择授权人弹窗 -->
		<div class="modal fade" id="checkTurstee_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c20" id="myModalLabel" style="border-left: none;">选择授权</h3>
					</div>
					<div class="modal-body">请选择授权人！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$('table tr').click(function() {
				var tclass = $(this).attr("class");
				if(tclass=='selected'){
					$(this).attr("class", "");
				}else{
					$(this).attr("class", "selected");
				}
			});
			
			$('#rightMoveBtn').click(function() {
				var trchoose = $('#tbcont1 tr.selected');
				if(trchoose.size()==0){
					alert("请选择需要移动的人员！");
				}else{
					trchoose.attr("class", "");
					trchoose.appendTo('#tbcont2');
				}
			});
			$('#leftMoveBtn').click(function() {
				var trchoose = $('#tbcont2 tr.selected');
				if(trchoose.size()==0){
					alert("请选择需要移动的人员！");
				}else{
					trchoose.attr("class", "");
					trchoose.appendTo('#tbcont1');
				}
			});
		});		
		
		function setHref(){
			var trs = $('#tbcont2 tr');
		    if(trs.size()==0){
		    	$('#checkTurstee_tanchuan').modal('show');
		    	/* alert("请至少选择一个人员！"); */
		    	return false;
		    }
		    var uidArr = new Array();
		    trs.each(function(){
		    	var id = $(this).attr("id");
		        uidArr.push(id);
		    });
		    var caseIDStr = $("#caseIDStr").val();
		    var caseNameStr = $("#showChooseSpan").text();
		    var caseNOStr = $("#caseNOStr").val();
		    var param = "caseIDStr="+caseIDStr+"&caseNOStr="+caseNOStr+"&caseNameStr="+caseNameStr+"&uidStr="+uidArr.toString();
			param = encodeURI(param);
			param = encodeURI(param);
		    $("#nextA").attr("href", "<%=basePath%>admin/case_cancel_confirm.php?"+param);
			return true;
		}
	</script>

	<!-- <script type="text/javascript">
		$(function() {
			//移到右边
			$('#add').click(function() {
				//先判断是否有选中
				if (!$("#select1 option").is(":selected")) {
					alert("请选择需要移动的选项")
				}
				//获取选中的选项，删除并追加给对方
				else {
					$('#select1 option:selected').appendTo('#select2');
				}
			});

			//移到左边
			$('#remove').click(function() {
				//先判断是否有选中
				if (!$("#select2 option").is(":selected")) {
					alert("请选择需要移动的选项")
				} else {
					$('#select2 option:selected').appendTo('#select1');
				}
			});

			//全部移到右边
			$('#add_all').click(function() {
				//获取全部的选项,删除并追加给对方
				$('#select1 option').appendTo('#select2');
			});

			//全部移到左边
			$('#remove_all').click(function() {
				$('#select2 option').appendTo('#select1');
			});

			//双击选项
			$('#select1').dblclick(function() { //绑定双击事件
				//获取全部的选项,删除并追加给对方
				$("option:selected", this).appendTo('#select2'); //追加给对方
			});

			//双击选项
			$('#select2').dblclick(function() {
				$("option:selected", this).appendTo('#select1');
			});

		});
	</script> -->
<jsp:include page="footer2.jsp"></jsp:include>
</body>
</html>