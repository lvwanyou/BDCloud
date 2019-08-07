<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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

.control-label{
	padding-right: 18px;
}
.col-lg-3{
	width: 30%;
}
.col-lg-5{
	padding-left: 0px;
}

.col-md-12 {
	padding: 0px;
}
.im_flag {
	font-size: 18px;
	position: absolute;
	margin-left: -10px;
}
.form-control1 {
    width: 30%;
    height: 34px;
    padding: 6px 12px;
    font-size: 14px;
    line-height: 1.428571429;
    vertical-align: middle;
    background-image: none;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    -webkit-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
}
</style>

<script type="text/javascript">
//修改标签
function editLabelComFirm(){
	var labid=$("#labid").val();
	//alert(labid);
	var label=$("#label").val();
 	
	$.ajax({
		type:"post",
		url:"<%=basePath %>editLabelComFirm.php",
		dataType : "json",
		data:{
			"labid" : labid,
			"label" : label
		},			

		success : function(data) {
			//alert(data.res);
			if (data.res == "succ") {				
//				alert("修改标签成功");
				$('#editLabel_success').modal('show');
				
			}else if(data.res == "exist"){
				//$("#labelWarnSpan").html("该标签已存在！");
			}
		},						
		error : function(data) {
			alert("修改标签失败，请重试!");
		}
	}); 
	
}

function goLabelList() {
	window.location="<%=basePath %>admin/label_list.php";
}
//重置
function resetting(){
    var a = document.getElementsByTagName("input");
    for(var i = 0;i<a.length;i++){
       if(a[i].type == "text"){
          a[i].value = "";
       }
    }
  }
//回车搜索事件 编辑标签
function onKeyDown_editLabel(event){
   var e = event || window.event || arguments.callee.caller.arguments[0];
   if(e && e.keyCode==13){ // enter 键
	   editLabelComFirm();
   }
}
</script>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">编辑标签</h1>
	</div>

	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">标签信息</div>
					<div class="panel-body">
						<%-- <form class="bs-example form-horizontal" 
						action="<%=basePath%>addLabel.php" method="post" id="form1">
							<div class="form-group">
								<label class="col-lg-3 control-label">标签名称</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入标签名" type="text" id="label" name="label">
									<!-- <span class="help-block m-b-none">Example block-level help text here.</span> -->
								</div>
								<div class="col-md-12" style="text-align: center;margin-bottom: 50px;margin-top: 25px;">
									<a href="<%=basePath %>label_list.php" type="button" class="btn w-xs btn-info" style="width: 135px;" onclick="addLabel()">提交</a>
								</div>
							</div>
						</form> --%>
						
						<form class="bs-example form-horizontal"
							action="<%=basePath%>edit_label.php" method="post">
							<input type="hidden" name="labid" id="labid" value="${result.id }">
							<div class="form-group">
								<label class="col-lg-3 control-label">标签名称</label>
								<div class="col-lg-5">
									<input type="text" value="" style="display: none;" />
									<input class="form-control" placeholder="请输入标签名" type="text" onkeydown="onKeyDown_editLabel(event)"
										id="label" name="label" value="${result.label}">
									<!-- <span class="help-block m-b-none">Example block-level help text here.</span> -->
								</div>
							</div>
							
							<div class="form-group" style="margin-top: 54px;">
								<div class="col-lg-offset-5 col-lg-5">
								<a class="btn w-xs btn-info" style="margin-right: 30px;" data-toggle="modal" data-target="#editLabel_success" onclick="return editLabelComFirm()">提交</a>
									<!-- <button class="btn w-xs btn-info"
											style="margin-right: 30px;" onclick="return editLabelComFirm()">提交</button>  -->
									<!-- <button type="reset" class="btn w-xs btn-default">重置</button> -->
									<a href="<%=basePath%>admin/label_list.php" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="return">取消</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改标签成功弹框 -->
		<div class="modal fade" id="editLabel_success" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes2" id="resetPassword_successes2"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c20" id="myModalLabel" style="border-left: none;">修改标签</h3>
					</div>
					<div class="modal-body">修改标签成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal" onclick="goLabelList()">确定</button>
					</div> 
				</div>
			</div>
		</div>
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
</script>
</body>
</html>