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
  		<h1 class="m-n h4">新建案件</h1>
	</div>
	
	<div class="b22" style="width: 98%;margin-left: 1%;margin-top: 1%;">
		<div class="success">
			<img alt="" src="<%=basePath %>template/img/success.png">
			<h3 style="margin-left:15px;">新建案件提交成功！</h3>
			<!-- <button type="button" class="btn w-xs btn-info" style="margin-top: 30px;width: 135px;" onclick="getcaseId()">导入数据</button> -->
			<a href="<%=basePath %>admin/importevidence_newcase.php" type="button" class="btn w-xs btn-info" style="width: 135px;" onclick="getCaseId()">导入数据</a>
			<div class="back_caselist"><a class="" href="javascript:" target="mainFrame" onclick="getleft()">返回案件列表</a></div>
		</div>
	</div>
	
	<div class="row" style="width: 48.5%;margin-top: 3%;margin-left: 1%;float:left;" >
				<div class="panel panel-default">
					<div class="panel-heading">案件信息</div>
					<div class="panel-body" style="padding: 0px;height: 256px;">
						<table id="datatable" class="table table-striped table-hover br04">
							<tr><td id="case_id" style="display: none;">${caseID }</td></tr>
								<tr><td class="alcenter_left" style="padding-left: 35px;">案件编号</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">${caseNum}</td></tr>
								<tr><td class="alcenter_left" style="padding-left: 35px;">案件名称</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">${caseName}</td></tr>
								<tr><td class="alcenter_left" style="padding-left: 35px;">案件类型</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">${caseType}</td></tr>
								<tr><td class="alcenter_left" style="padding-left: 35px;">案件所在城市</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">${loc_city}</td></tr>
								<tr><td class="alcenter_left" style="padding-left: 35px;">案件所属科室</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">${section}</td></tr>
								<tr><td class="alcenter_left" style="padding-left: 35px;">办案人员</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">${userName}</td></tr>
								<tr><td class="alcenter_left" style="padding-left: 35px;">办案标签</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">${caselabel}</td></tr>
						</table>
					</div>
					</div>
	</div>
	
	
		<div class="row" style="width: 48.5%;margin-top: 3%;margin-right: 1%;float:right;" >
				<div class="panel panel-default">
					<div class="panel-heading">涉案信息</div>
					<div class="panel-body" style="padding: 0px;height: 256px;">
						<table id="datatable" class="table table-striped table-hover br04">
								<tr><td class="alcenter_left" style="padding-left: 35px;">涉嫌人员</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">${mainParty}</td></tr>
								<tr><td class="alcenter_left" style="padding-left: 35px;">涉嫌组织</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">${relatedCompany}</td></tr>
								<tr><td class="alcenter_left" style="padding-left: 35px;">涉嫌物品</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">${relatedObject}</td></tr>
								<!-- <tr><td class="alcenter_left" style="padding-left: 35px;">手机号码</td><td class="alcenter_right" style="text-align: right;padding-right: 35px;">111111111</td></tr> -->
						</table>
					</div>
				</div>
	</div>
	<jsp:include page="footer2.jsp"></jsp:include>	

	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	//获取新建案件id
	function getCaseId() {
		var case_id = $("#case_id").text();
		//alert(case_id);
		<%-- window.location.href = "<%=basePath %>getCaseSummary.php?case_summary_id="+case_summary_id; --%>
		$.ajax({
			type : "POST",
			url : "<%=basePath%>getNewCaseInfo.php",
			cache:false, 
		    async:false,  
			data : {
				"case_id":case_id,
			},
			dataType : "json",
			success : function(data) {
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	
	function getleft() {           
	    	var addcasedmul = window.parent.frames['leftFrame'].document.getElementById("addcase");
	    	var listcasedmul = window.parent.frames['leftFrame'].document.getElementById("listcase");
	
	    	$(addcasedmul).removeClass("active");
	    	$(listcasedmul).addClass("active");
	    	 window.location.href= "<%=basePath %>getcaselist.php"; 
		
	}
	
	
	</script>
</body>
</html>