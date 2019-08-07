<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/cloud-admin.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/themes/default.css" id="skin-switcher">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
<link href="<%=basePath%>template/css/shengshiqu/common.css" rel="stylesheet" />
<link href="<%=basePath%>template/css/shengshiqu/select2.css" rel="stylesheet" />
<link href="<%=basePath%>template/css/shengshiqu/color_me.css" rel="stylesheet" />
</head>
<style>
.find_nav_left {
	height: 46px;
	position: relative;
	overflow: hidden;
}
.find_nav_list ul {
	position: relative;
	white-space: nowrap;
	font-size: 0;
	margin-left: 0px;
}

.find_nav_list ul li {
	display: inline-block;
	padding: 0px 13px 0px 13px;
}

.find_nav_list ul li a {
	
	width: 100%;
	height: 100%;
	font-size: 16px;
	text-align: center;
	display:block;
	line-height:50px;
	line-width:100%; 
}

.sideline {
	display: block;
	position: absolute;
	border: 0;
	height: 10px;
	left: 0;
	top: 43px;
	pointer-events: none;
}
</style>
<jsp:include page="common.jsp"></jsp:include>

<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
<script src="<%=basePath%>template/js/shengshiqu/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>template/js/shengshiqu/area.js"></script>
<script type="text/javascript" src="<%=basePath%>template/js/shengshiqu/location.js"></script>
<script src="<%=basePath%>template/js/shengshiqu/select2.js"></script>
<script src="<%=basePath%>template/js/shengshiqu/select2_locale_zh-CN.js"></script>
<script src="<%=basePath%>template/js/cutover/js.js"></script>
<script type="text/javascript">
	
	var type = 0;
	$(function typ0(){
		type=1;
		$("#typess").val(type);
	});
	
	function typ0(){
		type=1;
		$("#typess").val(type);
		//alert($("#typess").val());
	}
	/* function typ1(){
		type=2;
		$("#typess").val(type);
		//alert($("#typess").val());
	} */
	function typ2(){
		type=3;
		$("#typess").val(type);
		//alert($("#typess").val());
	}
	function typ3(){
		type=4;
		$("#typess").val(type);
		//alert($("#typess").val());
	}
	function typ4(){
		type=5;
		$("#typess").val(type);
		//alert($("#typess").val());
	}
	function startsearch(){

		var search_content=$("#big_search_box").val().trim();
		if(search_content==""){
			alert("请输入搜索内容！");
		}else{
			$("#loadingDiv").show();
			$("#searchForm").submit();
		}
	}
</script>
<body>
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">全案搜索</h1>
	</div>

	<div class="wrapper-md" style="height: 93%;width: 100%">
			<div class="panel panel-default" style="height: 100%;width: 100%">
			
					<div style="margin-left: 45%;margin-top: 12%;width: 100%;height: 10%;">
						<img alt="" src="<%=basePath%>template/img/search_list.png">
					</div>
					
					<form id="searchForm" method="post" action="<%=basePath%>SearchResults.php">
						<input type="hidden" name="typess" id="typess" value="">
						<table style="margin-left: 27%;margin-top: 1%;width: 100%;height: 5%;" class="tab">
							 <tr>
				     			 <td>
									<div>
										<div class="find_nav_left">
											<div class="find_nav_list">
										 		<ul id="menu" >
													<li><a class="ali" id="sum" onclick="typ0()">全部</a></li>
													<!-- <li><a class="ali"  onclick="typ1()">黑客数据库</a></li> -->
													<li><a id="leng" class="ali"  onclick="typ3()">邮件</a></li>
													<li><a id="doc"  class="ali"  onclick="typ2()">文档</a></li>
													<li><a class="ali"  onclick="typ4()">图片</a></li>
													<li id="butn" class="sideline"></li>
												</ul>
											</div>
										</div>
									</div>
					  			 </td>
				  			 </tr>
				  			 
				  			 <tr>
				  				<td>
				  			 		<input class="form-control" type="text" name="big_search_box"  style="float:left;width: 40%;padding-right: 10px" id="big_search_box" >
									<input type='button' class="btn btn-info" style="margin-right: 30px;width: 80px;margin-left: 10px;height: 34px"  value="搜索" onclick="startsearch()">
				  				</td>
				  			</tr>
						</table>
				</form>
			</div>
		
		<div id="loadingDiv" aria-hidden="false" style="display: none;" class="bootbox modal fade in" tabindex="-1" role="dialog">
            	<div class="modal-dialog" style="width: 155px;margin-top: 25%;">
            		<div class="modal-content">
            			<div class="modal-body">
            				<div class="bootbox-body">
            					<p class="msg">正在查询，请稍后...</p>
                    			<div><img id="flashAdImg" src="<%=basePath %>template/img/loading.gif" alt="loading" height="16" width="114"></div>
            				</div>
            			</div>
            		</div>
            	</div>
            </div>
	</div>
<jsp:include page="footer2.jsp"></jsp:include>
</body>
</html>