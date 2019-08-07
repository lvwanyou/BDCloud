<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
	import="com.xl.cloud.bean.*" 	
    %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

.font_style {
   font-size: 20px;
   font-style: italic;
   	height: 10px;
}
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
	
span{width:100%;text-align:center;}

}

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
	$(function typ1(){
		type=2;
		$("#typess").val(type);
	});	
		
	function typ1(){
		window.location.href = "<%=basePath %>hackerSearch.php";
		type=2;
		$("#typess").val(type);
		$("#heikeAndIP").css("display" , "block");
		$("#jizhanshuju").css("display" , "none");
	}
	
	function typ2(){
		type=3;
		$("#typess").val(type);
		//<span class="c17" style="font-size:20px">亿</span>
	    var html='&nbsp;2348&nbsp;<span class="c17" style="font-size:20px">万</span>';
		//$("#qianwan").text(23482657);
		$("#qianwan").html(html);
		$("#heikeAndIP").css("display" , "block");
		$("#jizhanshuju").css("display" , "none");
	}
	
	function typ3(){
		type=4;
		$("#typess").val(type);
		var html='&nbsp;1&nbsp;<span class="c17" style="font-size:20px">亿</span>'+
			 '&nbsp;1793&nbsp;<span class="c17" style="font-size:20px">万</span>';
			 $("#qianwan").html(html);
		//$("#qianwan").text(117934862);
		$("#jizhanshuju").css("display" , "block");
		$("#heikeAndIP").css("display" , "none");
	}
	function startsearch(){
		var search_content=$("#big_search_box").val().trim();
		
//		if(search_content==""){
//			alert("请输入搜索内容！");
//		}else{
			$("#loadingDiv").show();
			$("#searchForm").submit();
//		}
	}
	</script>
<body style="overflow-y:hidden;">
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">外部数据库</h1>
	</div>
	<div class="panel panel-default" style="height: 100%;width: 100%">
		<div style="margin-left: 45%;margin-top: 12%;width: 100%;height: 10%;">
			<img alt="" src="<%=basePath%>template/img/167.png">
		</div>
					<form id="searchForm" method="post" action="<%=basePath%>hackerSearchResults.php">
						<input type="hidden" name="typess" id="typess" value="">
						<table style="margin-left: 27%;margin-top: 1%;width: 100%;height: 5%;" class="tab">
							 <tr>
				     			 <td>

									<div>
										<div class="find_nav_left">
											<div class="find_nav_list">
										 		<ul id="menu" >
													<li><a class="ali"  onclick="typ1()">外部数据库</a></li>
													<li><a class="ali"  onclick="typ2()">IP数据库</a></li>
													<li><a class="ali"  onclick="typ3()">基站数据库</a></li>
													<li id="butn" class="sideline"></li>
												</ul>
											</div>
										</div>
<!-- =======
								<div>
									<div class="find_nav_left">
									<div class="find_nav_list">
								 			<ul id="menu" >
												<li><a class="ali"  onclick="typ1()">外部数据库</a></li>
												<li><a class="ali"  onclick="typ2()">IP数据库</a></li>
												<li><a class="ali"  onclick="typ3()">基站数据库</a></li>
												<li id="butn" class="sideline"></li>
											</ul>
>>>>>>> .r2398 -->
									</div>
					  			 </td>
				  			 </tr>
				  			 
				  			 <tr>
				  				<td id="heikeAndIP">
				  			 		<input class="form-control" type="text" name="big_search_box"  style="float:left;width: 40%;padding-right: 10px" placeholder="请输入关键词，多个请用空格进行分割" id="big_search_box" >
									<input type='button' class="btn btn-info" style="margin-right: 30px;width: 80px;margin-left: 10px;height: 33px"  value="搜索" onclick="startsearch();">
				  				</td>
				  			</tr>
				  			
				  			<tr>
					  			<td id="jizhanshuju" style="display: none;" >
					  			 	<span style="float:left;line-height:34px;margin-right:5px;margin-left:12px;">LAC:</span>
					  			 	<input class="form-control" type="text" name="LAC"  style="float:left;width: 17.5%;padding-right: 12px;" id="LAC" placeholder="请输入正确的基站格式">
					  			 	
					  			 	<span style="float:left;line-height:34px;margin-right:5px;margin-left:7px;">CID:</span>
					  			 	<input class="form-control" type="text" name="CID"  style="float:left;width: 17.5%;padding-right: 12px;" id="CID" placeholder="请输入正确的基站格式">
					  			 	
									<input type='button' class="btn btn-info" style="margin-right: 30px;width: 80px;margin-left: 9px;height: 33px;" value="搜索" onclick="startsearch();">
					  			</td>
				  			</tr>
						</table>
				</form>
				
				<%
				String importtime=session.getAttribute("importtime").toString();		
				%>
					<div style="text-align:center;margin-top: 0%;width: 100%;height: 30%;">	
					<div style="margin-top: 2%;">		
				<span class="c17" style="font-size:20px">总条数 :</span>		
				<span style="font-size:30px  "  id="qianwan">
				<c:if test="${hackcc_sum.yiwei != 0}">&nbsp;${hackcc_sum.yiwei }&nbsp;
				<span class="c17" style="font-size:20px">亿</span>	
				</c:if>    
				<c:if test="${hackcc_sum.wanwei != 0}">&nbsp;${hackcc_sum.wanwei }&nbsp;
				<span class="c17" style="font-size:20px">万</span>	
				</c:if>    
					<c:if test="${hackcc_sum.gewei  != 0 && hackcc_sum.yiwei==0}">&nbsp;${hackcc_sum.gewei  }
				</c:if>   
				</span><span class="c17" style="font-size:20px ">条信息</span>
				</div>
				<br/>
				<div style="margin-top: -1%;">				
				<span class="c17" style="font-size:20px">更新&nbsp;</span>				
				<span style="font-size:30px ">	
				<c:if test="${hackcc.yiwei != 0}">&nbsp;${hackcc.yiwei }&nbsp;
				<span class="c17" style="font-size:20px">亿</span>	
				</c:if>    
				<c:if test="${hackcc.wanwei != 0}">&nbsp;${hackcc.wanwei }&nbsp;
				<span class="c17" style="font-size:20px">万</span>	
				</c:if>    
					<c:if test="${hackcc.gewei  != 0 && hackcc.yiwei==0}">&nbsp;${hackcc.gewei  }
				</c:if>   
				</span>
				<span class="c17" style="font-size:20px ">&nbsp;条信息</span>
				<br/>
				<span style="font-size:10px">更新时间 : <%=importtime%></span>
			</div>
									
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