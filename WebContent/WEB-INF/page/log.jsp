<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<style>
.alcenter {
	text-align: center;
}

.inline {
	display: inline-block;
}

.form-inline .form-control {
	width: auto;
}

.clabel {
	margin-right: 14px;
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
<script type="text/javascript">

</script>

</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">操作日志</h1>
	</div>
	
    <div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">搜索日志<span style="float: right;">
						<img src="<%=basePath%>template/img/addevidence.png" onclick="testOfSearch(this)"></span>
					</div>
					<div class="panel-body" id="searchOfForm" style="display: block;">
						<form class="form-inline" role="form" id="searchForm" name="form1" action="" method="post">
							<input type="hidden" name="pageno" id="pageno" />
						
								<div class="form-group" style="width:20%">
									<label for="" class="clabel">操作菜单:</label>
									<select class="form-control c21" style="width: 60%; margin-left: -4px;" id="userAction" name="userAction" onkeydown="onKeyDown(event)">
										<option value="" class="c21">请选择...</option>
										<option>新增</option>
										<option>查看</option>
										<option>搜索</option>
										<option>删除</option>
										<option>登录</option>
									</select>
								</div>
							
								<div class="form-group">
									<label for="" class="clabel">精确日期:</label>
									<input id="createdTime" name="createdTime" value="${createdTime }" class="form-control" placeholder="请输入..." type="text"
										style="width: 180px; margin-left: -4px;" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'createdTime\')||\'new Date()\'}'});" onkeydown="onKeyDown(event)"/>
									<%-- 	<label for="" class="clabel">&nbsp;&nbsp;&nbsp;至</label>
									<input id="createdTime" name="endTime" value="${endTime }" class="form-control" placeholder="请输入结束时间" type="text"
										style="width: 180px;" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'createdTime\')}',maxDate:new Date()});" onkeydown="onKeyDown(event)"/> --%>
								</div>
								
								
								<div class="form-group" style="margin-left:20px;">
								<label for="" class="clabel" >操作用户:</label><input class="addWid form-control" style="width:60%;border-radius:2px;" id="userName" name="userName" class="form-control"
									placeholder="请输入..." type="text"  onkeydown="onKeyDown(event)"/>
							</div>
				
								<%-- <div class="form-group" style="width:20%;margin-top:1px;">
									<label for="" class="clabel">所属部门:</label>
									<select class="form-control" style="width: 60%; color: #BFA6A6; margin-left: -4px;" id="department" name="department" onkeydown="onKeyDown(event)">
										<option value="" style="color: #BFA6A6;">请选择...</option>
										<c:forEach items="${depart }" var="section2">
											<option id="caseSections" value="${depart.department}"
												<c:if test="${section2.sectionName==section}">selected</c:if>>${section2.sectionName}</option>
										</c:forEach>
									</select>
								</div> --%>
								
									<button type="button" class="btn btn-info b23 c02"
								style="width: 75px; height: 30px;"
								onclick="showCase(1)">搜索</button>
						
						</form>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span>日志列表</span>
					</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br04"
							style="text-align: center;">
							<thead>
				                <tr>
				                    <th style="text-align: center;">用户名</th>
				                    <th style="text-align: center;">用户行为</th>
				                    <th style="text-align: center;">操作模块</th>
				                    <th style="text-align: center;">时间</th>
				                    <th style="text-align: center;">登录ip</th>
				                  <!--   <th style="text-align: center;">所属部门</th> -->
				                </tr>
			                </thead>
							<tbody id="datatable_tbcont">

							</tbody>
						</table>
						<div class="alcenter" style="font-size: 14px;">
							<div class="pagecount inline" id="casetotal" style="height: 29px;">
								<span id="totCount" style="float: left;">
								
								</span>
							</div>
							<div class="pagebar inline" style="position: absolute; right: 128px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;">
									<li id="casepages"></li>
									<li id="casepages1"></li>
									<li id="casepages2"></li>
								</ul>
							</div>
							<div style="float: right;margin-right: 11px;">
								<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages" name="givePages" onkeydown="onKeyDown(event)""/>
								<button type="button" class="btn" onclick="showCase(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>template/My97DatePicker/WdatePicker.js"></script>
	<script>
	
	
	//显示操作日志详情
	 window.onload = showCase(1);

   //获取域名分析的table展示信息
   function showCase(pageno) {
   	
     
       var givePages = $("#givePages").val();
       var userName = $("#userName").val();
       var userAction = $("#userAction").val();
       var createdTime=$("#createdTime").val();
       var endTime=$("#endTime").val();
       var department=$("#department").val();
       if(givePages!=""){
   		pageno = parseInt(givePages);
   	}
       $.ajax({
           type: "POST",
           url: "<%=basePath%>useLog.php",
           data: {
               "pageno": pageno,
               	"userName":userName,
               	"userAction":userAction,
               	"createdTime":createdTime,
               	"endTime":endTime,
               	"userName":userName
           },
           dataType: "json",
           async: true,
           success: function (data) {
               var sizes = 10;
               var pagesum = data.totalNum;
               var pagenum = pagesum / sizes;
               var length = 5;  //要显示的分页页数

               if (pagenum % 1 != 0) {
                   pagenum = pagenum + (1 - pagenum % 1);
               }


               $("#casepages1").empty();
               $("#totCount").empty();
               //用于删除之前显示的页数，动态添加时id名均设为order
               for (var i = 1; i <= length; i++)
                   $("#order").remove();

               if (pagesum < sizes) {
                   var html2 = '<li class="active" id="order"><a href="#" onclick="showCase(1)">1</a></li >';
                   $("#casepages1").after(html2);


               } else {

                   if (pageno < pagenum) {
                       if (pageno + length - 1 <= pagenum) {
                           var html2 = "";
                           if (pageno - 2 > 0) {
                               for (var i = pageno - 2; i <= pageno + length - 1 - 2; i++) {
                                   if (i == pageno)
                                       html2 += '<li class="active" id="order"><a href="#" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                   else

                                       html2 += '<li id="order"><a href="#" onclick="showCase(' + i + ')">' + i + '</a></li >';
                               }
                           }
                           else {
                               for (var i = 1; i <= length; i++) {
                                   if (i == pageno)
                                       html2 += '<li class="active" id="order"><a href="#" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                   else

                                       html2 += '<li id="order"><a href="#" onclick="showCase(' + i + ')">' + i + '</a></li >';
                               }
                           }
                           //alert(html2);
                           $("#casepages1").after(html2);

                       }/* if */
                       else {
                           var html2 = "";
                           if (pagenum >= length) {
                               for (var i = pageno - (length - 1 - pagenum + pageno); i <= pagenum; i++) {
                                   if (i == pageno)
                                       html2 += '<li class="active" id="order"><a href="#" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                   else
                                       html2 += '<li id="order"><a href="#" onclick="showCase(' + i + ')">' + i + '</a></li >';
                               }
                               $("#casepages1").after(html2);
                           }
                           else {

                               for (var i = 1; i <= pagenum; i++) {


                                   if (i == pageno)
                                       html2 += '<li class="active" id="order"><a href="#" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                   else
                                       html2 += '<li id="order"><a href="#" onclick="showCase(' + i + ')">' + i + '</a></li >';
                               }
                               $("#casepages1").after(html2);
                           }

                       }
                   }
                   else {
                       if (pageno == pagenum) {
                           var html2 = "";

                           for (var i = pageno - length + 1 > 0 ? pageno - length + 1 : 1; i <= pagenum; i++) {
                               if (i == pageno)
                                   html2 += '<li class="active" id="order"><a href="#" onclick="showCase(' + i + ')">' + i + '</a></li >';
                               else
                                   html2 += '<li id="order"><a href="#" onclick="showCase(' + i + ')">' + i + '</a></li >';
                           }
                           $("#casepages1").after(html2);
                       }
                       /* if */
                   }
               }

               $("#casepages1").empty();
               $("#casepages").empty();
               $("#casepages2").empty();
               var html3 = '<span >共' + pagesum + '条，当前' + pageno + '/' + pagenum + '页</span>';
               $("#totCount").append(html3);

               var html5 = '<a href="#" onclick="showCase(' + pageno + '-1<1?1:' + pageno + '-1)"><</a>';
               $("#casepages").append(html5);

               var html4 = '<a href="#" onclick="showCase(' + pageno + '+1>' + pagenum + '?' + pagenum + ':' + pageno + '+1)">></a>';
               $("#casepages2").append(html4);
               $("#datatable_tbcont").empty();
               $.each(data.logs, function (i, item) {
                   var html01 = '+<tr>' +
				                        '<td style="text-align: center;">' + item.name + '</td> ' +
				                        '<td style="text-align: center;">' + item.action + '</td>' +
				                        '<td style="text-align: center;">' + item.module + '</td>' +
				                        '<td style="text-align: center;">' + item.createDate + '</td>'+
				                        '<td style="text-align: center;">' + item.ip + '</td>'+
				                      /*   '<td style="text-align: center;">' + item.department + '</td>'+ */
				                  
                       		  '</tr>+';
                   $("#datatable_tbcont").append(html01);
               });
               $("#givePages").val("");
           },
           error: function (XMLHttpRequest, textStatus, errorThrown) {
               //alert("失败");
           }
       });

   }
	
 //分页
   function subForm(pageno){
   	$("#pageno").val(pageno);
   	getCases1(pageno);
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
	
	//回车搜索事件
	 function onKeyDown(event){
       var e = event || window.event || arguments.callee.caller.arguments[0];
                           
       if(e && e.keyCode==13){ // enter 键
    	   showCase(1);
       }
    }
	</script>
</body>
</html>