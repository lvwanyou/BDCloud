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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/jquery.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/jquery.min.js"></script>
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
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	
<script type="text/javascript">
//分页
function subForm(pageno){
	$("#pageno").val(pageno);
	$("#searchForm").submit();
}

</script>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">更新日志</h1>
	</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
		<form class="form-inline" role="form" id="searchForm" action="<%=basePath%>/admin/aboutMe.php" method="post">
			<input type="hidden" name="pageno" id="pageno"/>
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">日志列表<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onClick="testOfSearch(this)"></span></div>
					<div class="panel-body" id="searchOfForm" style="display: block;padding:10px 10px 0 10px;">
							
						<div class="br04">
					<%-- 	<c:forEach items="${logs }" var="update"> --%>
							<div calss="tit br04" id="tbcont" data-toggle="modal" data-target="#myModal" onClick="searchone(this)">
<%-- 								<img src="<%=basePath %>template/img/mark.png" style="vertical-align:middle">
								<h1 style="font-size:16px;display:inline-block;">版本号:${update.version}<span>(更新时间:${update.updateTime })</span></h1>
								<div class="conts" style="padding-bottom:10px;">
									<h3  style="font-size:16px;margin-top:10px;">更新说明:</h3>
									<p>${update.updateContent}</p>
									<!-- <p>2.敲诈者防护：建立反勒索防护-文档卫士-解密大师的全面产品防线，为您抵御敲诈者病毒</p>
									<p>3.软件管家：改进搜索逻辑，搜软件更精确；新增智能推荐，量身推送您所需要的</p>
									<p style="margin-bottom:10px;">4.更好的支持Win10：支持更多Win10版本，并在主动防御、网盾、优化加速等方面更好支持Win10</p> -->
									<a href="javascript:void(0);" style="text-decoration: none;color:#475e9c" onclick="toggle('hidden')">展开更多></a>
										<div id="hidden" style="display: none;">
											<p></p>
										</div>
								</div> --%>
								<div id="hidden" style="display: none;"></div>
							</div>
					<%-- 		 </c:forEach> --%>
								<%-- <div class="alcenter" style="font-size: 14px">
	 					<div class="pagecount inline" style="height: 29px;">
								<span>共${totalNum }条，当前${nowPage }/${totalPages }页</span>
							</div>
							<div class="pagebar inline"
								style="position: absolute; right: 10px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;">
									<li><a href="#" onClick="subForm('${nowPage-1<1?1:nowPage-1}')">&lt;</a></li>
									<c:forEach items="${pageList }" var="pageNO">
				            			<li <c:if test="${nowPage==pageNO }">class="active"</c:if>>
				            				<a href="#" onClick="subForm('${pageNO}')">${pageNO}</a>
				            			</li>
				            		</c:forEach>
									<li><a href="#" onClick="subForm('${nowPage+1>totalPages?totalPages:nowPage+1}')">&gt;</a></li>
								</ul>
							</div> 
						</div> --%>
						</div>
						<!-- modle1 end-->
					</div>
				</div>
			</div>
			</form>
		</div>
	</div>
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>template/js/jQuery-Cookie/jquery.cookie.min.js"></script>
	<script src="<%=basePath%>template/js/script_left.js"></script>
	<script src="<%=basePath%>template/js/cutover/js.js"></script>
	<script src="<%=basePath%>template/js/cutover/dataTool.min.js"></script>
<script type="text/javascript">

window.onload = searchUser(1);
function searchUser(pageno){
	var pageno = $("#pageno").val();

	$.ajax({
		type: "POST",
		url: "<%=basePath %>admin/aboutlogsss.php",
		dataType : "json",
		data : {
			//"pageno":pageno
		},							    
		success : function(data) {
			//alert(data);
			
			var len = data.resData.length;
			var i = 0;
			var result = data.resData;
		//	alert(result);
			var len = result.length;
			//$("#tbcont").empty();
		//	alert(len);
		//	$("#tbcont").empty();
		var html01
			for(var i =0 ;i<result.length;i++){
				//var cpmtemt =[];
				var cpmtemt = result[i].updateContent.split("/");
				//alert(cpmtemt.length);
				var html01="";
				var html03="";
				for(var j =1;j<=cpmtemt.length;j++){
					
					if(j<3){
						html01 +=  '<p>'+j+cpmtemt[parseInt(j-1)]+'</p>';
					}else{
						html03+='<p>'+j+cpmtemt[parseInt(j-1)]+'</p>';
					}
						
				}
				var html02 ='<div class="br04" style="padding-bottom:10px;">'+
				'<img src="<%=basePath %>template/img/mark.png" style="vertical-align:middle">'+
			  '<h1 style="font-size:16px;display:inline-block;">版本号:'+result[i].version+'<span>(更新时间:'+result[i].updateTime+')</span></h1>'+
			  '<div class="conts" style="padding-bottom:10px;">'+
			  '<h3  style="font-size:16px;margin-top:10px;">更新说明:</h3>'+
			 		 '<div id="tbcont2'+i+'" >'+  
			 		html01+
			  		 '<div id="tbcont3'+i+'" style=" display:none;">'+html03+'</div></div>'+
			  		 '<a href="javascript:void(0);" class="c10" id="ss'+i+'" style="text-decoration: none;" onclick="toggle('+i+')">展开更多>'+
			  		'</a>'+ 
			  		/* '<span id="ss'+i+'" style="text-decoration: none;color:#475e9c" onclick="toggle('+i+')">展开更多>'+
			  		'</span>'+ */
			  '</div>';
			  $("#tbcont").append(html02);
		}

	},
		error : function(data) {
			$("#loadDiv_user").hide();
			alert("查询失败，请重试!");
		}
	});

}


<%-- <span style="float: right;"> <img src="<%=basePath%>template/img/addevidence.png" onclick="testOfSearch(this)"></span> --%>


function subForm(pageno){
	$("#pageno").val(pageno);
	$("#searchForm").submit();
}
	
	//折叠
	function testOfSearch(obj){
	  var div1=document.getElementById("searchOfForm");
	  if(div1.style.display=="block"){
	    div1.style.display="none";
	    obj.src="<%=basePath %>template/img/downXia.png";
	  }else{
	    div1.style.display="block";
	    obj.src="<%=basePath %>template/img/addevidence.png";
	  }
	}
	
  function toggle(targetid){
	    if (document.getElementById){
	        target = document.getElementById("tbcont3"+targetid);
	             if (target.style.display=="block"){
	                target.style.display="none";
					$("#ss"+targetid).text("展开更多>");
	            } else {
	                target.style.display="block";
	                $("#ss"+targetid).text("收起↑");
	           }
	    }
	}    
	

</script>

</body>
</html>