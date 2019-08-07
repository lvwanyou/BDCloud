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
<style>
.alcenter {text-align: center;}

.inline {display: inline-block;}

.form-inline .form-control {width: auto;}
</style>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">新建案件授权</h1>
	</div>

	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">请再次确认以下待授权人信息</div>
					<div class="panel-body" style="padding: 0px;">
						<input type="hidden" id="userName" value="${user.username}">
						<input type="hidden" name="caseIDStr" id="caseIDStr" value="${caseIDStr }"/>
						<input type="hidden" name="uidStr" id="uidStr" value="${uidStr }"/>
						<table id="datatable" class="table table-striped table-hover"
							style="text-align: center;margin-bottom: 0px; ">
							<thead>
								<tr>
									<th class="alcenter">案件编号</th>
									<th class="alcenter">案件名称</th>
									<th class="alcenter">被授权人姓名</th>
									<th class="alcenter">所在科室</th>
									<th class="alcenter">职务</th>
								</tr>
							</thead>
							<tbody id="tbcont">
								
								<c:forEach items="${caseinfos }" var="caseinfo">
									<c:forEach items="${users }" var="user">
										<tr>
											<td>${caseinfo.caseNum }</td>
											<td>${caseinfo.caseName }</td>
											<td>${user.username }</td>
											<td>${user.section }</td>
											<td>${user.userrole }</td>
										</tr>
									</c:forEach>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="foot_button" align="center" style="margin-top: 54px;">
				<a href="<%=basePath%>admin/case_new_granttwo.php"><button class="btn w-xs btn-default" style="margin-right: 20px;width: 135px;">上一步</button></a>
            	<a href="#" onclick="addGrant();"><button class="btn w-xs btn-info" style="width: 135px;">确定</button></a>
			</div>
		</div>

		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
					</div>
					<div class="modal-body">在这里添加一些文本</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary">提交更改</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 授权成功弹窗 -->
		<%-- <div class="modal fade" id="checkTurstee_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title" id="myModalLabel" style="color: #58666e;border-left: none;">案件授权</h3>
					</div>
					<div class="modal-body">案件授权成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div> --%>
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		function addGrant(){
			var caseIDStr = $("#caseIDStr").val();
			var uidStr = $("#uidStr").val();
			var user= $("#userName").val();
			//alert(user)
	    	var addcasedmul = window.parent.frames['leftFrame'].document.getElementById("listcase");
	    	var listcasedmul = window.parent.frames['leftFrame'].document.getElementById("listxietong");
			$.ajax({
				type : "POST",
				url : "<%=basePath%>admin/addGrant.php",
				data : {
					"caseIDStr":caseIDStr,
					"uidStr":uidStr,
					"user":user
				},
				dataType : "json",
				success : function(data) {
					if(data.res == "succ"){
						alert("案件授权成功！"); 
				    	$(addcasedmul).removeClass("active");
				    	$(listcasedmul).addClass("active");
						window.location.href = "<%=basePath%>admin/case_xietong.php";
						/* $('#checkTurstee_tanchuan').modal('show'); */
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}
	</script>
</body>
</html>