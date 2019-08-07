<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>     
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<head>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>                                                                                                    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="icon" href="<%=basePath %>template/img/icon_16.ico" mce_href="<%=basePath %>template/img/icon_16.ico" type="image/x-icon">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>template/css/login/nine.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath %>template/css/login/cloud-admin.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath %>template/css/login/easyui.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath %>template/css/login/modals.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath %>template/css/login/tabs.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath %>template/css/login/workspace.css" >
	<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
	<script type="text/javascript" src="<%=basePath %>js/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/EasyUI/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script> 
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script
		src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>

	<script src="<%=basePath%>template/js/shengshiqu/jquery.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>template/js/shengshiqu/area.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>template/js/shengshiqu/location.js"></script>
	<script src="<%=basePath%>template/js/shengshiqu/select2.js"></script>
	<script
		src="<%=basePath%>template/js/shengshiqu/select2_locale_zh-CN.js"></script>
<script language="JavaScript"> 
if (window != top) 
top.location.href = location.href; 
</script>
</head>

<html>
<style>
.bg{position:fixed !important;}
	html {
		font-size: 62.5%;/*10 ÷ 16 × 100% = 62.5%*/
		
	}
	body { 
		font-family:"微软雅黑";
		font-size: 1.4rem;/*1.4 × 10px = 14px */
	}
	/* html,body{
		overflow: auto;
	} */
	input:-webkit-autofill{
    -webkit-box-shadow: 0 0 0px 1000px white inset; 
}
/* 	.inputred{
		border-color:#de3c3c !important;
		 -webkit-box-shadow: 0 0 0px 1000px #f8e2e2 inset !important; 
	} */
	
	@media (max-width:1920px){
		.header-{margin-top:0 !important;}
		.huangpu{
			position:absolute;
			width:80%;
			
			
		}
		#denglu{
			width:400px;height:380px;
		}	
		.dengluzi{
			height:68px;
		}
		._modal-content{
			margin:-2% auto;
		}
		#username{
			margin-top:4%;
		}s
		#password{
			margin-top:1%;
		}
		#validate{
			margin-top:3%;
		}
		#imgVerify{
			margin-top:3%;
		}	
		#dengbut{
			margin-top:1%;
		}
		
		
	}
	
	@media (max-width:1600px){
		/* .huangpu{
			width:45%;top:60px;
		} */
		#denglu{
			width:380px;height:370px;
		}
		.dengluzi{
			height:64px;
		}
		._modal-content{
			margin:-5% auto;
		}
		#username{
			margin-top:1%;
		}
		#password{
			margin-top:1%;
		}
		#validate{
			margin-top:1%;
		}
		#imgVerify{
			margin-top:1%;
		}	
		#dengbut{
			margin-top:1%;
		}
	}
	
	@media (max-width:1440px){
		/* .huangpu{
			width:43%;top:70px;
		} */
		#denglu{
			width:350px;height:350px;
		}
		.dengluzi{
			height:60px;
		}
		._modal-content{
			margin:-8% auto;
		}
		#username{
			margin-top:3%;
		}
		#password{
			margin-top:1%;
		}
		#validate{
			margin-top:-1%;
		}
		#imgVerify{
			margin-top:-1%;
		}	
		#dengbut{
			margin-top:-2%;
		}
	}
	@media (max-width:1366px){
		.header-{margin-top:-90px !important;}
		#mgai{margin-top:-50px !important; }
		#msg{padding-top：25px !important;}
	}
	@media (max-width:1280px){
		.header-{margin-top:0 !important;}
		#msg{padding-top:25px !important;}
	}
	
	@media (max-width:1024px){
		#mgai{margin-top:-50px;}
		.huangpu{
			width:75%;top:100px;
		}
		#denglu{
			width:300px;height:335px;
		}
		.dengluzi{
			height:60px;
		}
		._modal-content{
			margin:-8% auto;
		}
		#username{
			margin-top:3%;
		}
		#password{
			margin-top:0%;
		}
		#validate{
			margin-top:-2%;
		}
		#imgVerify{
			margin-top:-2%;
		}	
		#dengbut{
			margin-top:-3%;
		}
		.addTop{margin-top:-10%}
		#msg{padding-top:30px !important;}
		._modal-content-row{height:60px !important;}
	}
	 @media (width:2560px){
	
		/* #denglu{
			width:500px;height:500px;
		}  */
		.header-{margin-top:0 !important;}
		.huangpu{
			position:absolute;
			width:80%;
			
			
		}
		#denglu{
			width:400px;height:380px;
		}	
		.dengluzi{
			height:68px;
		}
		._modal-content{
			margin:-2% auto;
		}
		#username{
			margin-top:4%;
		}s
		#password{
			margin-top:1%;
		}
		#validate{
			margin-top:3%;
		}
		#imgVerify{
			margin-top:3%;
		}	
		#dengbut{
			margin-top:1%;
		}
		
	}
	@media (max-height:600px){
		#mgai{margin-top:-100px;}
	}

	@media (max-width:800px){
		._modal-content-row{height:50px !important;}
		#dengbut{height:3rem !important;}
		#username{margin-top:0 !important;}
		#denglu{width: 250px !important;height: 280px !important;}
		.myadd{font-size:18px !important;margin-top:-10px !important;}
		._modal-content-row {height: 50px !important;}
	}
	.addys{
		font-size:16px;
		color:#fff;
		text-decoration:none;
		cursor:pointer;
		color:#fff !important;
	}
	.addys:hover,.addys:link{color:#fff;text-decoration:none;}
	.addys img{
		vertical-align:middle;
		width:20px;
		height:20px;
		margin-right:5px;
	}
</style>
<script type="text/javascript">

$(document.body).ready(function () {
    //首次获取验证码
    $("#imgVerify").attr("src","<%=basePath%>getVerify.php?"+Math.random());
    //var msg=$("#msg").html();
    //alert(msg);
    /*if(msg.length>0){
    	if(msg=="验证码错误！"){
        	$("#validate").addClass("inputred");
        	
        }else{
        	$("#username").addClass("inputred");
        	$("#password").addClass("inputred");
        }
    }*/
    
});

if(document.addEventListener){//如果是Firefox    
    document.addEventListener("keypress", fireFoxHandler, true);    
}else{    
    document.attachEvent("onkeypress", ieHandler);    
}    
function fireFoxHandler(evt){    
    if (evt.keyCode == 13){    
    	tijiao();
    }    
}    
function ieHandler(evt){    
    if (evt.keyCode == 13){    
    	tijiao();
    }    
}

function getVerify(obj){
	//alert("into two")
    obj.src = "<%=basePath%>getVerify.php?"+Math.random();
}

function checkInput(){
	var uname = $("#username").val();
	var pwd = $("#password").val().trim();
	var validate = $("#validate").val().trim();
	if(uname==null || uname.trim()==""){
		$("#msg").text("用户名不能为空！");
		//alert("用户名不能为空");
		return false;
	}
	if(pwd==null || pwd.trim()==""){
		$("#msg").text("密码不能为空！");
		//alert("密码不能为空");
		return false;
	}
	if(validate==null || validate.trim()==""){
		$("#msg").text("验证码不能为空！");
		//alert("验证码不能为空");
		return false;
	}
	$("#msg").text("");
	//return true;
	//tijiao();
	$("#form1").submit();
}

 function tijiao(){
	/* var oJson = { "function": "GetSn" };
    var sJson = JSON.stringify(oJson);
	    $.ajax({
	        url: 'http://127.0.0.1:17681',
	        timeout : 1000,
	        data: 'json=' + sJson,
	        dataType: "jsonp",
	        jsonp: "callback",
	        jsonpCallback: "jsonpcallback",
	        success: function(ojson) {
	            try {
	                var sjson = JSON.stringify(ojson[0]);
		            	if (ojson[0].ret){
			            	var sn = ojson[0].sn+"";
			            	//alert(sn);
							if(sn.length>5){
								//alert(sn);
								$("#sn").val(sn);
				            	//$("#form1").submit();
				            	checkInput();
							}else{
								alert("未找到加密狗！");
				            	return false;
							}
		            	}
		            	else{
		            		alert("未找到加密狗！");
			            	return false;
		            	}
	            	}
	            	catch(err){
	            		alert("未找到加密狗！");
		            	return false;
	            	}
	        },
	        complete : function(XMLHttpRequest,status){
	        	if(status=='timeout'){
	        		alert("未安装加密狗驱动！");
	        		}
	        	}
	    	
	    });
	    return false; */
	   	checkInput();
	    return false;
	//checkInput();
}
</script>
<body >

<div class="bg">    
<img src="<%=basePath%>template/img/xl01.jpg" style="height:100%;min-height:605px; width:100%;min-width:915px;" >  
</div>
<img id="mgai" src="<%=basePath%>template/img/police222.png" style="position:relative; left:50%; z-index:99;margin-left: -581px;"/>
<div class="header-" style="margin-top:-100px;">
<div style="overflow:hidden" class="addTop">

<div style="height:325px; width:50%;text-align:center;padding-left:5%;display:inline-block;">

<img src="<%=basePath%>template/img/cis_login.png" class="huangpu" style=" position:relative; left:15.5%; z-index:99;"/>
<!-- <div style="height:329px; background:#283355; opacity:0.73;filter:alpha(opacity=80);margin-button:-20px;"></div> -->


</div>

<!-- <div class="title0">
		<img src="img/hg.png" style="float:left;margin-top: 13px;width: 35px;">
		<h1 style="float:left;margin-top: 10px;margin-left: 19px;font-size: 1.8em;">黄埔云海量文件分析系统</h1>  
    </div>
    <div class="title2">
		<h1 style="margin-top: 1rem;font-size: 2.4rem;font-weight: 100;">版本号：V  1.0.332</h1>
	</div> -->
	<div style="position:relative;width:50%;float:right;padding-left:5%;">
	 
	<!-- 用户登录-->
		<!-- <div class="login-model" style="background:red;width:300px;margin-top:100px;"> -->									
				<!-- <div class="modal-header" style="color: #FFFFFF !important;background-color: #3992d0;border-bottom: 1px solid #507aa4;">
				  <h4 class="modal-title" style="font-size: 2.2rem;font-weight:100;">黄埔海量数据分析平台</h4>
				</div> -->
				
				<div class="modal-body b35" id="denglu" style="border-radius:4px;z-index:100;overflow:hidden;">								 													
				 	<div class="b36" style="height:16%; width:100%; margin:0 auto;z-index:-1;  position:absolute; left:0px; top:0px;border-top-left-radius:4px;border-top-right-radius:4px;"></div>
				 	<div ><p class="myadd c10" style="font-size:22px;text-align:center;margin-top:-5px;">登&nbsp;&nbsp;&nbsp;&nbsp;录</p></div>
				 	<div class="_modal-content" style="width:91%;">
				 	
				 	<p id="msg" class="c22" style="font-size:1.2rem;;margin-button:1px;width:296px;padding-left:18px;padding-top:15px;margin:0px;" >${msg }</p>
				 	<form action="<%=basePath%>log.php" name="form1" id="form1"  method="post" style="margin:4px 0 0 0;">
				 		<input type="hidden" name="sn" id="sn">
				 		<div class="_modal-content-row">
				 			<!-- <div class="_modal-content-row-text">用户名：</div> -->
				 			<input id="username" name="username" class="_model-content-row-textbox br23" type="text" style="height:60%;width:95%;margin-left:9px;border-radius:4px;padding-left:16px; " placeholder="用户名" >
				 		</div>
				 		<div class="_modal-content-row">
				 			<!-- <div class="_modal-content-row-text">密码：</div> -->
				 			<input id="password" name="password" class="_model-content-row-textbox br23" type="password" style="height:60%;width:95%;margin-left:9px;border-radius:4px;padding-left:16px;" placeholder="密码">
				 		</div>
				 	    <div class="_modal-content-row">
				 			<input id="validate" name="validate" class="_model-content-row-textbox br23" type="text" style="width:50%;margin-left:9px;height:60%;width:59%;border-radius:4px;padding-left:16px;" placeholder="验证码">
				 			<img id="imgVerify" src="" alt="点击更换验证码" class="_model-content-row-textbox br23 b37" style="width:31%;font-size:1rem;line-height: 30px;height:60%;border-radius:4px;" onclick="getVerify(this);">
				 		</div>
				 		<button type="button" id="dengbut" class="right ml_10 buttontag b06" style="height: 4.4rem;width:95%;margin-right:2%;border-radius:4px; font-size:17px;" onclick="tijiao();">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>		
				 	</form>
				 	</div>
				<!-- </div> -->
				
				
			  </div>
			  <div>
					<h6 class="modal-title c02" style="font-size: 16px;font-weight:100;padding-top:0px;padding-left:0px;">推荐系统环境：谷歌浏览器，分辨率1920*1080</h6>
					<a class="addys" href="<%=basePath %>SystemManage/kehuduanAndliulanqi.php"><img src="<%=basePath%>template/img/ys.png">谷歌浏览器和客户端下载链接</a>
				</div>	
		  </div>
		  </div>
		  <div style="text-align:center; width:100%;position:fixed;bottom:5px;">
			<img src="<%=basePath%>template/img/left.png"><span class="c02">  由勋立云计算提供技术支持  版本号: V1.1.1084  </span><img src="<%=basePath%>template/img/left2.png">
		</div>	
		
		
</div>

</body>
