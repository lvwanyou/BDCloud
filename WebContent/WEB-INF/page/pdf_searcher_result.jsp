<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>template/css/responsive.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css" />
		<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script
		src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
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
</style>

</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">PDF检索器</a>
			</div>
		</div>
	</nav>
	<div class="container">
		<div class="col-md-12">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">搜索</div>
					<div class="panel-body">
						<form class="form-inline" role="form" id="searchForm" action="<%=basePath%>/searchbycondit.php" method="post">
							<input type="hidden" name="pageno" id="pageno"/>
							<div class="form-group">
								<label for="" class="clabel">文件关键词:</label><input id="caseNum" name="caseNum" value="${caseNum }"
									class="form-control" placeholder="账单" type="text" style="width:390px;"/>
							</div>				
							<button type="button" class="btn btn-info" style="width:80px;" onclick="searchbycondit()">搜索</button>
						</form>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">文件列表
					</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br04"
							style="text-align: center;">
							<div align="center" class="row">
							<img src="<%=basePath %>template/img/result.png">
								</div>
								<div align="center" class="row" >
							<span class="c03" >未找到包含"账单"相关文件结果 </span>
								</div>
						</table>
					
					</div>
				</div>
			</div>
		</div>


		
	</div>


</body>
</html>