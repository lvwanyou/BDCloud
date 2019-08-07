<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%></span>
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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
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
</script>
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
	border-radius: 50%;
	float: right;
	text-align: center;
}


.fade_suc,.fade_fas,.fade_poi{
                width:275px;
                height:80px;
                border-radius: 12px;
                position:fixed;
                margin:0 auto;
                margin-top:94px;
                line-height: 80px;
                display:none;
                left:50%;
                margin-left:-138px;
            }
            .fade_suc{background-color:rgba(0,165,47,.5);}
            .fade_fas{background-color:rgba(242,23,23,.5);}
            .fade_poi{background-color:rgba(71,94,156,.5);}
            .fade_suc img,.fade_fas img,.fade_poi img{
                width:40px;
                height:40px
                display:inline-block;
                margin:20px 20px 20px 70px;
                vertical-align: middle;
            }
            .fade_poi img{margin:20px 20px 20px 35px;}
            .fade_suc span,.fade_fas span,.fade_poi span{
                font-size:16px;
            }
</style>
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
//分页
function subForm(pageno){
	var givePages_labelLists = $("#givePages_labelLists").val();
	if(givePages_labelLists != ""){
		pageno = parseInt(givePages_labelLists);
	}
	$("#pageno").val(pageno);
	$("#searchForm").submit();
}

function searchbycondit() {
	var getInput = $("#label").val();
	if(getInput != null && getInput != "") {
		$("#searchForm").submit();
		return true;
	}else{
		/* alert("请输入信息查询！"); */
		 $("#js-point").click(function(){
             $(".fade_poi").fadeIn();
        })
         setInterval("$('.fade_poi').fadeOut()",9000);
		return false;
	}
	
}

function searchone(node) {
	var id = node;
	$("#editLabel").attr("href","<%=basePath%>editLabel.php?id="+id);
	$.ajax({
        url:"<%=basePath%>getLabelone.php",
        data:{
           "id":id             
        },
        type:"post",
        dataType:"json",
        success: function(data){
        	$("#labelId_right").html(data.id);
           $("#labelName_right").html(data.label);
           $("#labelTime_right").html(data.time);
           $("#hiddenId").val(id);
        }
    });
}
function delCase(){
	var getHiddenId = $("#hiddenId").val();
		$.ajax({
			type:"post", 
	        url:"<%=basePath%>admin/delCase.php",
	        dataType:"json",
	        data:{
	           "lab":getHiddenId         
	        },
	        success: function(data){
//	        	$('#confirmDeleteLabel').modal('hide');
	        	if(data.res=="succ"){
//	        		alert("删除成功");
//					$('#deleteLabel_success').modal('show');
		       		location.reload();
	        	}
	  
	        },
	     	error: function(data) {
	    		alert("删除失败");
	    		//alert(JSON.stringify(data));
			} 
	   	 });
		
}

function ifDeleteLabel() {
	
//	var getHiddenId = $("#hiddenId").val();
//	$("#labelID").html(getHiddenId);
	$('#confirmDeleteLabel').modal('show');
}
</script>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">标签管理</h1>
	</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">搜索标签<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onclick="testOfSearch(this)"></span></div>
					<div class="panel-body" id="searchOfForm" style="display: block;">
						<form class="form-inline" role="form" id="searchForm" action="<%=basePath%>/searchByConditOfLabel.php" method="post">
							<input type="hidden" name="pageno" id="pageno" />
							<div class="form-group">
								<label for="" class="clabel">标签名称:</label>
								<input id="label" name="label" class="form-control" placeholder="请输入..." type="text" style="width: 390px;" value="${label }" onkeydown="onKeyDown(event)"/>
							</div>
							<button type="button" id="js-point" class="btn btn-info" onclick="searchbycondit()">搜索</button>
						</form>
					</div>
				</div>
			</div>

			<div class="row">
				<a href="<%=basePath%>admin/addLabel_page.php" class="btn b06 c02"
					style="margin-right: 30px; font-size: 10px; width: 150px;">
					新增标签 </a>
				<div style="margin: 20px"></div>
			</div>

			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">标签云</div>
					<div class="panel-body" style="padding: 0 20px 15px;">
					
						<c:forEach items="${logs }" var="lab" varStatus="count">
							<c:if test="${count.count%7 == 1}">
								<span><span data-toggle="modal" data-target="#myModal"
									name='shanchu' class='br05 b12 span-1' id='${lab.id}'
									onclick="searchone(${lab.id})">${lab.label}</span></span>
							</c:if>
							
							<c:if test="${count.count%7 == 2}">
								<span><span data-toggle="modal" data-target="#myModal"
									name='shanchu' class='b13 br06 span-2' id='${lab.id}'
									onclick="searchone(${lab.id})">${lab.label}</span></span>
							</c:if>
							
							<c:if test="${count.count%7 == 3}">
								<span><span data-toggle="modal" data-target="#myModal"
									name='shanchu' class='br11 b11 span-3' id='${lab.id}'
									onclick="searchone(${lab.id})">${lab.label}</span></span>
							</c:if>
							<c:if test="${count.count%7 == 4}">
								<span><span data-toggle="modal" data-target="#myModal"
									name='shanchu' class='br07 b14 span-4' id='${lab.id}'
									onclick="searchone(${lab.id})">${lab.label}</span></span>
							</c:if>
							
							<c:if test="${count.count%7 == 5}">
								<span><span data-toggle="modal" data-target="#myModal"
									name='shanchu' class='br08 b15 span-5' id='${lab.id}'
									onclick="searchone(${lab.id})">${lab.label}</span></span>
							</c:if>
							
							<c:if test="${count.count%7 == 6}">
								<span><span data-toggle="modal" data-target="#myModal"
									name='shanchu' class='br09 b17 span-6' id='${lab.id}'
									onclick="searchone(${lab.id})">${lab.label}</span></span>
							</c:if>
							<c:if test="${count.count%7 == 7}">
								<span><span data-toggle="modal" data-target="#myModal"
									name='shanchu' class='br10  b18 span-7' id='${lab.id}'
									onclick="searchone(${lab.id})">${lab.label}</span></span>
							</c:if>
							
							<c:if test="${count.count%7 == 0}">
								<span><span data-toggle="modal" data-target="#myModal"
									name='shanchu' class='br09 b17 span-6' id='${lab.id}'
									onclick="searchone(${lab.id})">${lab.label}</span></span>
							</c:if>
							
						</c:forEach>

						<div class="alcenter" style="font-size: 14px">
							<hr/>
							<div class="pagecount inline" style="height: 29px;">
								<span>共${totalNum }条，当前${nowPage }/${totalPages }页</span>
							</div>


							<div class="pagebar inline" style="position: absolute; right: 126px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;">
									<li><a href="#" onclick="subForm('${nowPage-1<1?1:nowPage-1}')">&lt;</a></li>
									<c:forEach items="${pageList }" var="pageNO">
										<li <c:if test="${nowPage==pageNO }">class="active"</c:if>>
											<a href="#" onclick="subForm('${pageNO}')">${pageNO}</a>
										</li>
									</c:forEach>
									<li><a href="#" onclick="subForm('${nowPage+1>totalPages?totalPages:nowPage+1}')">&gt;</a></li>
								</ul>
							</div>
							<div style="float: right;margin-right: 11px;">
								<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages_labelLists" name="givePages_labelLists" onkeydown="onKeyDown_labelfenye(event)"/>
								<button type="button" class="btn" onclick="subForm(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal right fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="panel panel-default" style="padding-right: 10px;">
						<div class="panel-body">

							<div class="panel-heading b22 br004" style="padding: 10px;">标签详情</div>
							<table id="datatable" class="table table-striped table-hover br004" style="text-align: center;">
								<thead>
									<tr>
										<th class="alcenter">标签名称</th>
										<th class="alcenter">创建时间</th>
									</tr>
								</thead>
								<tbody id="tbcont">
									<tr data-toggle="modal">
										<td style="display: none;" id="labelId_right"></td>
										<td class="alcenter" id="labelName_right"> <button id="labelName_right"></button></td>
										<td class="alcenter" id="labelTime_right"></td>
									</tr>
								</tbody>
							</table>


						</div>
						<div style="text-align: center; margin-bottom: 20px;">
							<div class="">
								<button class="btn w-xs btn-info b06 c02" style="margin-right: 20px; width: 60px;"
									data-toggle="modal" data-target="#confirmDeleteLabel" onclick="ifDeleteLabel()">删除</button>
								<a href="<%=basePath%>editLabel.php" class="btn b06 c02" id="editLabel" style="margin-right: 20px;width: 60px;">编辑</a>
								<input type="hidden" id="hiddenId" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
			<div class="fade_poi" style="display:none;">
				<img src="<%=basePath %>template/img/30.png">
				<span>请输入搜索的关键词~</span>
			</div>
	</div>
	<!-- 弹出确认是否删除的框 -->
	<div class="modal fade" id="confirmDeleteLabel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" >删除标签</h4>
				</div>
				<div style="margin-left: 3%;margin-top: 3%;">
					<h5 class="modal-title" >确定删除该标签吗？</h5>
				</div>
				<div class="modal-footer">
					<span id="labelID" ></span>
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="delCase()">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 弹出删除标签成功的框 -->     
	<div class="modal fade" id="deleteLabel_success" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" >删除标签</h4>
				</div>
				<div style="margin-left: 3%;margin-top: 3%;">
					<h5 class="modal-title" >删除成功</h5>
				</div>
				<div class="modal-footer">
					<span id="labelID" ></span>
					<button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="footer2.jsp"></jsp:include>
<script>
	$("#tbcont tr").hover(
		  function () {
		    $(this).addClass("hover");
		  },
		  function () {
		    $(this).removeClass("hover");
		  }
		);
	
	function test(obj) {
		alert(obj);
	}
	
	//回车搜索事件
	function onKeyDown(event){
	   var e = event || window.event || arguments.callee.caller.arguments[0];
	   if(e && e.keyCode==13){ // enter 键
		   searchbycondit();
	   }
	}
	//回车搜索事件  跳页
	function onKeyDown_labelfenye(event){
	   var e = event || window.event || arguments.callee.caller.arguments[0];
	   if(e && e.keyCode==13){ // enter 键
		   subForm();
	   }
	}
</script>
</body>
</html>