<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>话单分析</title>
<meta name="description" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<!-- STYLESHEETS -->
<!--[if lt IE 9]><script src="js/flot/excanvas.min.js"></script><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script><![endif]-->
<link rel="stylesheet" type="text/css" href="css/cloud-admin.css">
<link rel="stylesheet" type="text/css" href="css/themes/default.css"
	id="skin-switcher">
<link rel="stylesheet" type="text/css" href="css/responsive.css">

<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
<!-- JQUERY UI-->
<link rel="stylesheet" type="text/css"
	href="js/jquery-ui-1.10.3.custom/css/custom-theme/jquery-ui-1.10.3.custom.min.css" />
<!-- DATE RANGE PICKER -->
<link rel="stylesheet" type="text/css"
	href="js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
<!-- DATA TABLES -->
<link rel="stylesheet" type="text/css"
	href="js/datatables/media/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css"
	href="js/datatables/media/assets/css/datatables.min.css" />
<link rel="stylesheet" type="text/css"
	href="js/datatables/extras/TableTools/media/css/TableTools.min.css" />
</head>
<body>
	<form id="newgonggaoform" name="newgonggaoform"
		action="<%=basePath%>upload.php" enctype="multipart/form-data"
		method="post" class="form-horizontal" role="form">
		<!-- <div class="form-group">
			<label class="col-sm-3 control-label">用户手机号所在列</label>
			<div class="col-sm-7">
				<input class="form-control" id="col" name="col" type="text">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">用户手机号第一条数据所在行</label>
			<div class="col-sm-7">
				<textarea class="form-control" id="row" name="row"></textarea>
			</div>
		</div> -->
		<div class="form-group">
			<label class="col-sm-3 control-label">Excel表格</label>
			<div class="col-sm-7">
				<input class="form-control" id="upfile" name="upfile" type="file">
			</div>
		</div>

	</form>
	<button type="button" class="btn btn-success" onclick="addgonggao();">提交</button>

<form id="newgonggaoform1" name="newgonggaoform1"
		action="<%=basePath%>upload1.php" enctype="multipart/form-data"
		method="post" class="form-horizontal" role="form">
		<!-- <div class="form-group">
			<label class="col-sm-3 control-label">用户手机号所在列</label>
			<div class="col-sm-7">
				<input class="form-control" id="col" name="col" type="text">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">用户手机号第一条数据所在行</label>
			<div class="col-sm-7">
				<textarea class="form-control" id="row" name="row"></textarea>
			</div>
		</div> -->
		<div class="form-group">
			<label class="col-sm-3 control-label">Excel表格</label>
			<div class="col-sm-7">
				<input class="form-control" webkitdirectory directory id="upfile1" name="upfile1" type="file">
			</div>
		</div>

	</form>
	<button type="button" class="btn btn-success" onclick="addgonggao1();">提交</button>


	<script src="js/jquery/jquery-2.0.3.min.js"></script>
	<!-- JQUERY UI-->
	<script
		src="js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<!-- BOOTSTRAP -->
	<script src="bootstrap-dist/js/bootstrap.min.js"></script>


	<!-- DATE RANGE PICKER -->
	<script src="js/bootstrap-daterangepicker/moment.min.js"></script>

	<script src="js/bootstrap-daterangepicker/daterangepicker.min.js"></script>
	<!-- SLIMSCROLL -->
	<script type="text/javascript"
		src="js/jQuery-slimScroll-1.3.0/jquery.slimscroll.min.js"></script>
	<script type="text/javascript"
		src="js/jQuery-slimScroll-1.3.0/slimScrollHorizontal.min.js"></script>
	<!-- BLOCK UI -->
	<script type="text/javascript"
		src="js/jQuery-BlockUI/jquery.blockUI.min.js"></script>
	<!-- DATA TABLES -->
	<script type="text/javascript"
		src="js/datatables/media/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="js/datatables/media/assets/js/datatables.min.js"></script>
	<script type="text/javascript"
		src="js/datatables/extras/TableTools/media/js/TableTools.min.js"></script>
	<script type="text/javascript"
		src="js/datatables/extras/TableTools/media/js/ZeroClipboard.min.js"></script>
	<!-- COOKIE -->
	<script type="text/javascript"
		src="js/jQuery-Cookie/jquery.cookie.min.js"></script>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>
	<!-- CUSTOM SCRIPT -->
	<script src="js/script.js"></script>
	<script type="text/javascript">
		function addgonggao() {
			var upfile = $("#upfile").val();
			if (upfile != "" && upfile != null) {
				$("#newgonggaoform").submit();

			} else {
				alert("文件不能为空");
			}

		}
		
		function addgonggao1() {
			var upfile = $("#upfile1").val();
			if (upfile != "" && upfile != null) {
				$("#newgonggaoform1").submit();

			} else {
				alert("文件不能为空");
			}

		}
	</script>
</body>
</html>