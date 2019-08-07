﻿﻿﻿<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>main</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>template/css/cloud-admin.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>template/css/themes/default.css" id="skin-switcher">
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>template/css/responsive.css">
<!-- STYLESHEETS -->
<!--[if lt IE 9]><script src="js/flot/excanvas.min.js"></script><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script><![endif]-->
<link
	href="<%=basePath %>template/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<!-- ANIMATE -->
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>template/css/animatecss/animate.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>template/js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
<!-- TODO -->
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>template/js/jquery-todo/css/styles.css" />
<!-- FULL CALENDAR -->
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>template/js/fullcalendar/fullcalendar.min.css" />
<!-- GRITTER -->
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>template/js/gritter/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>template/css/color_me.css">
<!-- JQUERY -->
<script src="<%=basePath %>template/js/jquery/jquery-2.0.3.min.js"></script>
<style type="text/css">
.menu_l {
	margin-top: -2px;
}

.menu-text {
	margin-left: 12px;
}

.menu_arrow {
	margin-top: -3px;
	margin-left: 40px;
	width: 5px;
}

.has-sub.open .menu_arrow {
	transform: rotateZ(90deg);
	-ms-transform: rotateZ(90deg); /* IE 9 */
	-moz-transform: rotateZ(90deg); /* Firefox */
	-webkit-transform: rotateZ(90deg); /* Safari 和 Chrome */
	-o-transform: rotateZ(90deg);
}

.sidebar-collapse {
	text-align: center;
	margin-top: 20px;
}
</style>
<script type="text/javascript">
    $(function() {
        $(".sub li").click(
            	function(){
            			$(".sub").find("li").removeClass("active");
            			$("ul").find("li").removeClass("active");
            			var src = $(".singleLi").find("img").attr("src");
            			src = src.replace("_xz.png",".png");
            			$(".singleLi").find("img").attr("src", src);
            			$(this).addClass("active");
            	}
        );
        $(".singleLi").click(
            	function(){
            			$("ul").find("li").removeClass("active");
            			$(".sub").find("li").removeClass("active");
            			var src = $(this).find("img").attr("src");
            			src = src.replace("_xz.png",".png");
            			$(this).addClass("active");
            			src = src.replace(".png","_xz.png");
            			$(this).find("img").attr("src", src);
            	}
        );
    });
    </script>
</head>
<body class="b06">

	<!-- SIDEBAR -->
	<div id="sidebar" class="sidebar">
		<div class="sidebar-menu nav-collapse">
			<!-- SIDEBAR MENU -->
			<ul id="action">
				<li class="singleLi active"><a
					href="<%=basePath %>admin/workbench.php" target="mainFrame"> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/gzt_xz.png" />
						<span class="menu-text">大屏展示</span> <span class="selected"></span>
				</a></li>
				<%--  <li class="has-sub">
								<a href="javascript:;" class="">
								<img class="menu_l" alt="" src="<%=basePath %>template/img/gzt_xz.png"/> <span class="menu-text">工作台</span>
								<img class="menu_arrow" alt="" src="<%=basePath %>template/img/arrow.png"style="float:right;margin-right: 10px;margin-left: 28px;margin-top: 5px;"/>
								</a>
								
								<ul class="sub">
									<li><a class="" href="<%=basePath %>admin/workbench.php" target="mainFrame"><span class="sub-menu-text">工作台</span></a></li>
								 	<li><a class="" href="<%=basePath %>showIndexQueue.php" target="mainFrame"><span class="sub-menu-text">索引队列</span></a></li>
								</ul>
							</li>  --%>
				<%-- <c:if test="${sessionScope.privilege  ne '管理员'}"> --%>
				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/ajgl.png" />
						<span class="menu-text">案件管理</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<c:if
							test="${sessionScope.privilege  eq '科长' || sessionScope.privilege  eq '管理员'}">
							<li id="addcase"><a class=""
								href="<%=basePath %>admin/newcase.php" target="mainFrame"><span
									class="sub-menu-text"><img class="menu_l" alt=""
										src="<%=basePath %>template/img/newCase.png" /> 新建案件</span></a></li>
						</c:if>
						<li id="listcase" onclick="lifun2(this.id)"><a class=""
							href="<%=basePath %>/getcaselist.php" target="mainFrame"><span
								class="sub-menu-text"><img class="menu_l" alt=""
									src="<%=basePath %>template/img/caseList.png" />案件列表</span></a></li>

						<c:if
							test="${sessionScope.privilege  eq '科长' || sessionScope.privilege  eq '管理员'}">
							<li id="listxietong"><a class=""
								href="<%=basePath %>admin/case_xietong.php" target="mainFrame"><span
									class="sub-menu-text"><img class="menu_l" alt=""
										src="<%=basePath %>template/img/caseXie.png"
										style="margin-right: 2px;" />协同办案</span></a></li>
						</c:if>

						<li><a class="" href="<%=basePath %>admin/label_list.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt=""
									src="<%=basePath %>template/img/marka.png" />标签管理</span></a></li>
						<li><a class="" href="<%=basePath %>/getSuspectlist.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt=""
									src="<%=basePath %>template/img/suspact.png" />嫌疑人画像</span></a></li>
						<c:if
							test="${sessionScope.privilege  eq '科长' || sessionScope.privilege  eq '管理员'}">
							<li><a class="" href="<%=basePath %>/showIndexQueue.php"
								target="mainFrame"><span class="sub-menu-text"><img
										class="menu_l" alt=""
										src="<%=basePath %>template/img/index.png"
										style="margin-right: 3px;">索引队列</span></a></li>
						</c:if>

					</ul></li>
				<%--  <li class="has-sub">
								<a href="javascript:;" class="">
								<img class="menu_l" alt="" src="<%=basePath %>template/img/sjgl.png"/> <span class="menu-text">数据管理</span>
								<img class="menu_arrow" alt="" src="<%=basePath %>template/img/arrow.png"/>
								</a>
								<ul class="sub">
								</ul>
							</li> --%>
				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/ajgl.png" />
						<span class="menu-text">线索管理</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<li><a class="" href="<%=basePath %>showAddInvolve.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt=""
									src="<%=basePath %>template/img/invove.png" />线索上报</span></a></li>
						<li><a class="" href="<%=basePath %>admin/cluelist.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt=""
									src="<%=basePath %>template/img/inlist.png" />线索列表</span></a></li>
					</ul></li>
				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/email.png" />
						<span class="menu-text">邮件挖掘</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<!-- <li><a class="" href="<%=basePath %>admin/userManager.php" target="mainFrame"><span class="sub-menu-text">用户管理</span></a></li>  -->
						<li><a class=""
							href="<%=basePath %>admin/email_workbench.php" target="mainFrame"><span
								class="sub-menu-text">邮件挖掘</span></a></li>
						<li><a class="" href="<%=basePath %>admin/signManage.php"
							target="mainFrame"><span class="sub-menu-text">标记管理</span></a></li>
					</ul></li>

				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/noCase.png" />
						<span class="menu-text">无案件数据</span> <img class="menu_arrow"
						alt="" src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<li><a class=""
							href="<%=basePath %>noCaseData/addevidence_NoCase.php"
							target="mainFrame"><span class="sub-menu-text">数据列表</span></a></li>
					</ul></li>
				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/wendang.png" />
						<span class="menu-text">文档挖掘</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<li><a class=""
							href="<%=basePath %>admin/paper_work_list.php" target="mainFrame"><span
								class="sub-menu-text">文档挖掘</span></a></li>
					</ul></li>

				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/tupian.png" />
						<span class="menu-text">图片挖掘</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<li><a class="" href="<%=basePath %>admin/imgworkbench.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" style="margin-right: 2px;" alt=""
									src="<%=basePath %>template/img/tupian.png" />图片挖掘</span></a></li>
						<li><a class="" href="<%=basePath %>admin/newDemoGPS.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt=""
									src="<%=basePath %>template/img/imgps.png" />图片GPS定位</span></a></li>
						<li><a class="" href="<%=basePath %>admin/faceDetect.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt="" src="<%=basePath %>template/img/face.png" />人脸识别</span></a></li>
						<li><a class="" href="<%=basePath %>/panorama.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt="" src="<%=basePath %>template/img/face.png" />全景地图展示</span></a></li>

					</ul></li>

				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt=""
						src="<%=basePath %>template/img/shujuwajue.png" /> <span
						class="menu-text">数据挖掘</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<li><a class="" href="<%=basePath %>admin/dataMining.php"
							target="mainFrame"><span class="sub-menu-text">数据挖掘</span></a></li>
					</ul></li>


				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/heike.png" /><span
						class="menu-text">黑客数据库</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<li><a class="" href="<%=basePath %>/hackerSearch.php"
							target="mainFrame"><span class="sub-menu-text">黑客搜索</span></a></li>

					</ul></li>
				<%--  <li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/heike.png" /><span
						class="menu-text">手机取证</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<li><a class="" href="<%=basePath %>phone/showHtmlData.php"
							target="mainFrame"><span class="sub-menu-text">手机取证</span></a></li>
					</ul></li>  --%>

				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/znjs.png" />
						<span class="menu-text">智能检索</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<li><a class="" href="<%=basePath %>/getGimps.php"
							target="mainFrame"><span class="sub-menu-text">大搜索</span></a></li>
					</ul></li>
				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/tjbb.png" />
						<span class="menu-text">统计报表</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<li><a class="" href="<%=basePath %>admin/typestatistics.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt=""
									src="<%=basePath %>template/img/typel.png" />数据类型统计</span></a></li>
						<li><a class="" href="<%=basePath %>chart/dataTendency.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt=""
									src="<%=basePath %>template/img/typeq.png" />数据趋势统计</span></a></li>
						<li><a class="" href="<%=basePath %>admin/caseStatistics.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt=""
									src="<%=basePath %>template/img/typecase.png" />案件统计</span></a></li>
						<li><a class=""
							href="<%=basePath %>admin/labelStatistics.php" target="mainFrame"><span
								class="sub-menu-text"><img class="menu_l" alt=""
									src="<%=basePath %>template/img/typelabel.png" />标签统计</span></a></li>
						<li><a class=""
							href="<%=basePath %>admin/userActionStatistics.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt=""
									src="<%=basePath %>template/img/typeuser.png" />用户行为统计</span></a></li>
					</ul>
				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/ypgj.png" />
						<span class="menu-text">话单分析</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>

					<ul class="sub">
						<li><a class="" href="<%=basePath %>admin/ticketAnalysis.php"
							target="mainFrame"><span class="sub-menu-text">话单智能分析</span></a></li>
						<%-- <li><a class="" href="<%=basePath %>admin/pdf_searcher.php" target="mainFrame"><span class="sub-menu-text">PDF检索器</span></a></li> --%>
					</ul></li>
				<%-- </c:if>	 --%>
				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt="" src="<%=basePath %>template/img/xt.png" />
						<span class="menu-text">系统管理</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>

					<ul class="sub">
						<c:if test="${sessionScope.privilege  eq '管理员'}">
							<li><a class="" href="<%=basePath %>admin/userManager.php"
								target="mainFrame"><span class="sub-menu-text"><img
										class="menu_l" alt=""
										src="<%=basePath %>template/img/userm.png" />用户管理</span></a></li>
							<li><a class=""
								href="<%=basePath %>admin/sectionmanager.php" target="mainFrame"><span
									class="sub-menu-text"><img class="menu_l" alt=""
										src="<%=basePath %>template/img/secm.png" />科室管理</span></a></li>
							<li><a class=""
								href="<%=basePath %>admin/departmentmanager.php"
								target="mainFrame"><span class="sub-menu-text"><img
										class="menu_l" alt=""
										src="<%=basePath %>template/img/dapartm.png" />部门管理</span></a></li>
							<li><a class="" href="<%=basePath %>admin/partter.php"
								target="mainFrame"><span class="sub-menu-text">角色管理</span></a></li>
						</c:if>
						<li><a class="" href="<%=basePath %>admin/logrizhi.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt="" src="<%=basePath %>template/img/logm.png" />操作日志</span></a></li>
						<li><a class="" href="<%=basePath %>admin/aboutMe.php"
							target="mainFrame"><span class="sub-menu-text"><img
									class="menu_l" alt=""
									src="<%=basePath %>template/img/aboutm.png" />更新日志</span></a></li>
					</ul></li>
				<li class="has-sub"><a href="javascript:;" class=""> <img
						class="menu_l" alt=""
						src="<%=basePath %>template/img/favorite.png" /> <span
						class="menu-text">收藏夹</span> <img class="menu_arrow" alt=""
						src="<%=basePath %>template/img/arrow.png"
						style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;" />
				</a>
					<ul class="sub">
						<li><a class="" href="<%=basePath %>admin/favorite.php"
							target="mainFrame"><span class="sub-menu-text">收藏夹</span></a></li>
					</ul></li>
			</ul>
			<!-- /SIDEBAR MENU -->
		</div>
		<div id="sidebar-collapse" class="sidebar-collapse">
			<!-- <i class="fa fa-bars" data-icon1="fa fa-bars" data-icon2="fa fa-bars"></i> -->
			<img id="sidebar_img" alt="" src="<%=basePath %>template/img/zd.png"
				style="cursor: pointer;">
		</div>
	</div>
	<!-- /SIDEBAR -->
	<!-- JAVASCRIPTS -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- JQUERY UI-->
	<script
		src="<%=basePath %>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<!-- COOKIE -->
	<script type="text/javascript"
		src="<%=basePath %>template/js/jQuery-Cookie/jquery.cookie.min.js"></script>
	<!-- CUSTOM SCRIPT -->
	<script src="<%=basePath %>template/js/script_left.js"></script>
	<script>
		jQuery(document).ready(function() {		
			App.setPage("index");  //Set current page
			App.init(); //Initialise plugins and elements
		});
		
		
		
		$(function() {
	        $(".sub li").click(
	            	function(){
	            			$(".sub").find("li").removeClass("active");
	            			$("ul").find("li").removeClass("active");
	            			var src = $(".singleLi").find("img").attr("src");
	            			src = src.replace("_xz.png",".png");
	            			$(".singleLi").find("img").attr("src", src);
	            			$(this).addClass("active");
	            			
	               			var dapinsrc = $(this).find("img").attr("src");
	               			dapinsrc = dapinsrc.replace(".png","_zx.png");
	               			$(this).find("img").attr("src",dapinsrc);
	            	}
	        );
	        /* $(".sub li").hover(function(){
	        	$(".sub").find("li").removeClass("active");
    			$("ul").find("li").removeClass("active");
    		
    			$(this).addClass("active");
    			
       			var dapinsrc = $(this).find("img").attr("src");
       			
       			dapinsrc = dapinsrc.replace(".png","_zx.png");
       			
       			$(this).find("img").attr("src",dapinsrc);
	        },
	        function(){
	        	$(".sub").find("li").removeClass("active");
    			$("ul").find("li").removeClass("active");
    			
    			
    			
    			$(this).addClass("active");
    			
       			var dapinsrc = $(this).find("img").attr("src");
       			
       			dapinsrc = dapinsrc.replace("_zx.png",".png");
       			
       			$(this).find("img").attr("src",dapinsrc);
	        }
	        ); */
	        $(".singleLi").click(
	            	function(){
	            			$("ul").find("li").removeClass("active");
	            			$(".sub").find("li").removeClass("active");
	            			var src = $(this).find("img").attr("src");
	            			src = src.replace("_xz.png",".png");
	            			$(this).addClass("active");
	            			src = src.replace(".png","_xz.png");
	            			$(this).find("img").attr("src", src);
	            	}
	        );
	    }); 
	</script>
	<script type="text/javascript">
	window.onload = fun();
	function fun(){
		$("#action").empty();
		$.ajax({
	        url:"<%=basePath %>system/getAction.php",
	        data:{
	        },
	        type:"get",
	        dataType:"json",
	        success: function(data){
	        	var html="";
	        	for(var i =data.length-1;i>=0;i--){
	        		if(data[i].parent=="0"){
	        			if(data[i].actionOrder=="1"){
		        			var daping='<li id="dapin" class="singleLi active" onclick="lifun2(0)">'+
				        	'<a href="<%=basePath %>admin/workbench.php" target="mainFrame">'+ 
				        	'<img class="menu_l" alt="" src="<%=basePath %>template/img/gzt_xz.png" />'+
							'<span class="menu-text">大屏展示</span> <span class="selected"></span>'+
							'</a></li>';
				           $("#action").append(daping);
		        		}else{
		        			var html2="";
		        			for(var j =data.length-1;j>=0;j--){
		        				if(data[j].parent==data[i].id){
		        					html2+= '<li id="ul'+i+'li'+j+'" onclick="lifun2(this.id)"><a class="" href="<%=basePath %>'+data[j].route+'" target="mainFrame">'+
											'<img class="menu_l" alt="" src="<%=basePath %>'+data[j].icon+'" style=" margin-right: 6px; "/><span class="sub-menu-text" id="shou" >'+data[j].actionName+'</span>'+
											'</a> </li> ';
		        				}
		        			}
		        			html += 
		        					'<li class="has-sub" >'+
				        			'<a href="javascript:;" class="" onclick="lifun('+i+')"> '+
				        				'<img class="menu_l" alt="" src="<%=basePath %>'+data[i].icon+'" />'+
				        				'<span class="menu-text">'+data[i].actionName+'</span> '+
				        				'<img id="img'+i+'" class="menu_arrow" alt="" src="<%=basePath %>
		template/img/arrow.png" style="float: right; margin-right: 10px; margin-left: 28px; margin-top: 5px;transform: ;" />'
												+ '</a> <ul id="ul'+i+'" class="sub"  >'
												+ html2 + '</ul>' + '</li> ';
									}

								}
							}
							$("#action").append(html);
						}
					});
		};
		function lifun(i) {
			var dis = $("#ul" + i).css("display");
			$(".sub").css("display", "none");
			$(".menu_arrow").css("transform", "");
			if (dis == "none") {
				$("#ul" + i).css("display", "block");
				$("#img" + i).css("transform", "rotateZ(90deg)");
			} else {
				$("#ul" + i).css("display", "none");
				$("#img" + i).css("transform", "");
			}
		};
		var oldid = "";
		function lifun2(id) {
			if (oldid != "") {
				var srcImgold = $("#" + oldid).find("img").attr("src");
				srcImgold = srcImgold.replace("_zx.png", ".png");
				$("#" + oldid).find("img").attr("src", srcImgold);
			}

			if (id != 0) {
				$(".singleLi").removeClass("active");
				var dapinsrc = $("#dapin").find("img").attr("src");
				dapinsrc = dapinsrc.replace("_xz.png", ".png");
				$("#dapin").find("img").attr("src", dapinsrc);
			} else {
				$(".singleLi").addClass("active");
				$(".sub").css("display", "none");
				$(".menu_arrow").css("transform", "");

				var dapinsrc = $("#dapin").find("img").attr("src");
				dapinsrc = dapinsrc.replace(".png", "_xz.png");
				$("#dapin").find("img").attr("src", dapinsrc);
			}
			$(".sub li").removeClass("active");
			$("#" + id).addClass("active");
			var srcImg = $("#" + id).find("img").attr("src");
			if (srcImg.indexOf("_zx.png") <= 0) {
				srcImg = srcImg.replace(".png", "_zx.png");
			}
			$("#" + id).find("img").attr("src", srcImg);
			oldid = id;
		};
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>