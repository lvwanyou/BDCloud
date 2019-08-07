<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/cloud-admin.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<style>
.success{
	text-align: center;
	padding: 35px;
}
.back_caselist{
	margin-top: 12px;
}
a {
	text-decoration: underline;
	font-size: 14px;
}
.table{
	margin-bottom: 0px;
}

</style>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">新建标签</h1>
	</div>
	
	<div class="b22" style="width: 98%;margin-left: 1%;margin-top: 1%;">
		<div class="success">
			<img alt="" src="<%=basePath %>template/img/success.png">
			<h3 style="margin-left:15px;">新建标签提交成功！</h3>
			<div class="back_caselist"><a href="<%=basePath %>admin/label_list.php">返回标签列表</a></div>
		</div>
	</div>
	<jsp:include page="footer2.jsp"></jsp:include>	

<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
</body>
</html>