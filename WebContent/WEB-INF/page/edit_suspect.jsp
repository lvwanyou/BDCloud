<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

.control-label {
	padding-right: 18px;
}

.col-lg-3 {
	width: 30%;
}

.col-lg-5 {
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
	-webkit-transition: border-color ease-in-out .15s, box-shadow
		ease-in-out .15s;
	transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
}

#province, #city, #town {
	width: 225px;
	height: 30px;
}

</style>
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script type="text/javascript">
//修改案件
$(function (){
	var getSuspectHomeAddress = "${result.suspectHomeAddress}";
	var strAddress = getSuspectHomeAddress.split("-");
    new PCAS("province", "city", "town", strAddress[0], strAddress[1], strAddress[2]);
    });
    
function editSuspectComFirm(){
	var suspectId=$("#suspectId").val();
	var suspectName=$("#suspectName").val();
	var suspectSex=$("input[name=suspectSex]:checked").val();
	var suspectUnitName=$("#suspectUnitName").val();
	var suspectUnitAddress=$("#suspectUnitAddress").val();
	var suspectPhone=$("#suspectPhone").val();
	var suspectMail=$("#suspectMail").val();
	var suspectQQ=$("#suspectQQ").val(); 
	var createTime=$("#createTime").val(); 
	var suspectIDCardNumber=$("#suspectIDCardNumber").val(); 
	var suspectSocialSecurity=$("#suspectSocialSecurity").val(); 
	var suspectPassport=$("#suspectPassport").val(); 
	var suspectMicroletters=$("#suspectMicroletters").val(); 
	var suspectFacebook=$("#suspectFacebook").val(); 
	var suspectTwitter=$("#suspectTwitter").val(); 
	var loc_province = $("#province").val()+"-";
	var loc_city = $("#city").val()+"-";
	var loc_town = $("#town").val();
	var caselabel = $("#caselabel").val();
	var suspectHomeAddress = loc_province+loc_city+loc_town;
	/* if(suspectHomeAddress == "省份地级市市、县、区") {
		suspectHomeAddress = "";
	}else{
		document.getElementById("suspectHomeAddress").value = suspectHomeAddress;
	}*/
	if(suspectName != "") { 
		 
		$.ajax({
			type:"post",
			url:"<%=basePath %>editSuspectComFirm.php",
			dataType : "json",
			data:{
				"suspectId" : suspectId,
				"suspectName" : suspectName,
				"suspectSex" : suspectSex,
				"suspectHomeAddress" : suspectHomeAddress,
				"suspectUnitName" : suspectUnitName,
				"suspectUnitAddress" : suspectUnitAddress,
				"suspectPhone" : suspectPhone,
				"suspectMail" : suspectMail,
				"suspectQQ" : suspectQQ,
				"suspectIDCardNumber" : suspectIDCardNumber, 
				"suspectSocialSecurity" : suspectSocialSecurity, 
				"suspectPassport" : suspectPassport, 
				"suspectMicroletters" : suspectMicroletters, 
				"suspectFacebook" : suspectFacebook, 
				"suspectTwitter" : suspectTwitter, 
				"caselabel":caselabel
			},			

			success : function(data) {
				//alert(data.res);
				if (data.res == "succ") {				
					alert("修改嫌疑人成功");
					window.location="<%=basePath %>getSuspectlist.php";
				}else if(data.res == "exist"){
					//$("#labelWarnSpan").html("该标签已存在！");
				}
			},						
			error : function(data) {
				alert("修改嫌疑人失败，请重试!");
			}
		}); 
	} else {
		alert("嫌疑人姓名不能为空！");
	}
	
	
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
  
//获取案件标签list
function myfun() {	
	$.ajax({
		type: "POST",
		url: "<%=basePath %>getAllLabel.php",
		dataType : "json",
		data : {
			"page" : '1'
		},							    
		success : function(data) {
			labelList = data.resData;
			var labelDivs = "";
			var noe="";
			for(var i=1;i<=data.resData.length;i++){
				var s = '<span id="label_'+data.resData[i].id+'" class="tags" title="'+data.resData[i].label+'" onclick="setLabelArea(this.id)">'+data.resData[i].label+'</span>';
				if(i%7 == 1) {
				$("#label1").append($("<span name='shanchu' class='br05 b12 span-1'>"+s+"</span>"));
				}
				if(i%7 == 2) {
				$("#label1").append($("<span name='shanchu' class='b13 br06 span-2'>"+s+"</span>"));
				}
				if(i%7 == 3) {
				$("#label1").append($("<span name='shanchu' class='br11 b11 span-3'>"+s+"</span>"));
				}
				if(i%7 == 4) {
				$("#label1").append($("<span name='shanchu' class='br07 b14 span-4'>"+s+"</span>"));
				}
				if(i%7 == 5) {
				$("#label1").append($("<span name='shanchu' class='br08 b15 span-5'>"+s+"</span>"));
				}
				if(i%7 == 6) {
				$("#label1").append($("<span name='shanchu' class='br09 b17 span-6'>"+s+"</span>"));
				}
				if(i%7 == 7) {
				$("#label1").append($("<span name='shanchu' class='br10  b18 span-7'>"+s+"</span>"));
				}
				if(i%7 == 0) {
				$("#label1").append($("<span name='shanchu' class='br11 b11 span-3'>"+s+"</span>"));
				}
/* 				var s = '<div id="label_'+data.resData[i].id+'" class="tags" title="'+data.resData[i].label+'" onclick="setLabelArea(this.id)">'+data.resData[i].label+'</div>';		   
				$("#label1").append($("<span name='shanchu' style='width: 90px; height: 30px; margin-right: 10px;margin-top: 10px;'>"+s+"</span>"));		 */
			}
		},						
		error : function(data) {
			alert("获取标签列表失败，请重试!");
		}
	});
}

//获取案件标签list
function isContains(str, substr) {
    return str.indexOf(substr) >= 0;
}

//点击案件标签
function setLabelArea(id){
	var labelText = $("#"+id+"").html();
	//alert(labelText);
	var oldLabelList = $("#caselabel").val();
	//alert(oldLabelList);
	if(oldLabelList != "" && oldLabelList != null){
		if(!isContains(oldLabelList,labelText)){
			$("#caselabel").val(oldLabelList + " " + labelText);
		}		
	}else{	
		$("#caselabel").val(labelText);
	}	
}
  
function validateSuspectName() {
	var inputName = $("#suspectName").val();
	if(inputName==null || inputName==""){
		$("#addinputNamelabel").text("*姓名不能为空！");
		document.getElementsByTagName('input')[0].style.border = '1px solid red';
	}else{
		document.getElementsByTagName('input')[0].style.border = '1px solid #CFDADD';
		$("#addinputNamelabel").text("");
	}
}
function validateSuspectIDCardNumber() {
	var inputsuspectIDCardNumber = $("#suspectIDCardNumber").val();
	if(inputsuspectIDCardNumber==null || inputsuspectIDCardNumber==""){
		$("#addinputIDCardNumberlabel").text("*身份证号不能为空！");
		document.getElementsByTagName('input')[0].style.border = '1px solid red';
	}else{
		document.getElementsByTagName('input')[0].style.border = '1px solid #CFDADD';
		$("#addinputIDCardNumberlabel").text("");
	}
}
function validateSuspectPhone() {
	var inputPhone = $("#suspectPhone").val();
	if(inputPhone==null || inputPhone==""){
		$("#addinputPhonelabel").text("*手机号码不能为空！");
		document.getElementsByTagName('input')[3].style.border = '1px solid red';
	}else{
		document.getElementsByTagName('input')[3].style.border = '1px solid #CFDADD';
		$("#addinputPhonelabel").text("");
	}
}
function validateSuspectMail() {
	var inputMail = $("#suspectMail").val();
	if(inputMail==null || inputMail==""){
		$("#addinputMaillabel").text("*电子邮箱不能为空！");
		document.getElementsByTagName('input')[4].style.border = '1px solid red';
	}else{
		document.getElementsByTagName('input')[4].style.border = '1px solid #CFDADD';
		$("#addinputMaillabel").text("");
	}
}
/* function validateSuspectQQ() {
	var inputQQ = $("#suspectQQ").val();
	if(inputQQ==null || inputQQ==""){
		$("#addinputQQlabel").text("*QQ号码不能为空！");
		document.getElementsByTagName('input')[5].style.border = '1px solid red';
	}else{
		document.getElementsByTagName('input')[5].style.border = '1px solid #CFDADD';
		$("#addinputQQlabel").text("");
	}
} */
function validateSuspectUnitName() {
	var inputUnitName = $("#suspectUnitName").val();
	if(inputUnitName==null || inputUnitName==""){
		$("#addinputUnitNamelabel").text("*所在组织名称不能为空！");
		document.getElementsByTagName('input')[6].style.border = '1px solid red';
	}else{
		document.getElementsByTagName('input')[6].style.border = '1px solid #CFDADD';
		$("#addinputUnitNamelabel").text("");
	}
}
function validateSuspectUnitAddress() {
	var inputUnitAddress = $("#suspectUnitAddress").val();
	if(inputUnitAddress==null || inputUnitAddress==""){
		$("#addinputUnitAddresslabel").text("*组织地址不能为空！");
		document.getElementsByTagName('input')[7].style.border = '1px solid red';
	}else{
		document.getElementsByTagName('input')[7].style.border = '1px solid #CFDADD';
		$("#addinputUnitAddresslabel").text("");
	}
}


</script>

</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">编辑嫌疑人</h1>
	</div>
	<div class="" style="padding-left: 20px;padding-top: 15px;">
			<a href="<%=basePath%>getSuspectlist.php" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="return">返回上一级</a>
		</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">嫌疑人基本 信息</div>

					<div class="panel-body">
						<form class="bs-example form-horizontal">
						<input type="hidden" name="suspectId" id="suspectId" value="${result.id }">
							<div class="form-group">
								<label class="col-lg-3 control-label"><span
									class="im_flag">*</span>姓名</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入嫌疑人姓名" type="text" id = "suspectName" name = "suspectName" value="${result.suspectName }" onblur="validateSuspectName()"/>
									<!-- <span class="help-block m-b-none">Example block-level help text here.</span> -->
									<span id="addinputNamelabel" class="empty c11" ></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3 control-label">性别</label>
								<div class="col-lg-5">
									<label>
									<input type="radio" value="男" id = "suspectSex" name = "suspectSex" <c:if test="${result.suspectSex == '男' }">checked="checked"</c:if>/>男
									</label> 
									<label>
									<input type="radio" value="女" id = "suspectSex" name = "suspectSex" <c:if test="${result.suspectSex == '女' }">checked="checked"</c:if>/>女 </label>
								</div>
								
							</div>
							
						<div class="form-group">
							<label class="col-lg-3 control-label">位置</label>
							
							<select name="province" id="province"></select> 
							<select name="city" id="city"></select> 
							<select name="town" id="town"></select>
							
							
							<input type="hidden" id= "suspectHomeAddress" name="suspectHomeAddress" value=""  >
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">案件标签</label>
							<div class="col-lg-5">
								<input class="form-control" placeholder="请输入标签名称" type="text"
									id="caselabel" name="caselabel" value="${result.label }"><!-- onchange="biaoqian()" -->
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label"></label>
								<div class="col-lg-5">
									<span id="label1" > 
										
								    </span>
							    </div>	
						</div>
							
							<!-- 身份证号 -->
							<div class="form-group">
								<label class="col-lg-3 control-label">身份证号</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text"  value="${result.suspectIDCardNumber }"
										id="suspectIDCardNumber" name="suspectIDCardNumber" onblur="validateSuspectIDCardNumber()">
										<span id="addinputIDCardNumberlabel" class="empty c11"></span>
								</div>
							</div>
							
							<!--社保  -->
							<div class="form-group">
								<label class="col-lg-3 control-label">社保号</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text" value="${result.suspectSocialSecurity }"
										id="suspectSocialSecurity" name="suspectSocialSecurity" />
										<span id="addinputSocialSecuritylabel" class="empty c11"></span>
								</div>
							</div>
							
							<!--护照  -->
							<div class="form-group">
								<label class="col-lg-3 control-label">护照号</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text"  value="${result.suspectPassport }"
										id="suspectPassport" name="suspectPassport" />
										<span id="addinputPassportlabel" class="empty c11"></span>
								</div>
							</div>
							
							<!-- facebook -->
							<div class="form-group">
								<label class="col-lg-3 control-label">Facebook账号</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text" value="${result.suspectFacebook }"
										id="suspectFacebook" name="suspectFacebook" onblur="validateSuspectPhone()">
										<span id="addinputFacebooklabel" class="empty c11"></span>
								</div>
							</div>
							
							<!-- twitter -->
							<div class="form-group">
								<label class="col-lg-3 control-label">Twitter账号</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text"  value="${result.suspectTwitter }"
										id="suspectTwitter" name="suspectTwitter" onblur="validateSuspectPhone()">
										<span id="addinputtwitterlabel" class="empty c11"></span>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-3 control-label"><span
									class="im_flag"></span>手机号码</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text"  value="${result.suspectPhone }"
										id="suspectPhone" name="suspectPhone" onblur="validateSuspectPhone()">
										<span id="addinputPhonelabel" class="empty c11"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3 control-label">电子邮件</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text"  value="${result.suspectMail }"
										id="suspectMail" name="suspectMail" onblur="validateSuspectMail()">
										<span id="addinputMaillabel" class="empty c11"></span>
								</div>
							</div>
							
							<!-- 微信 -->
							<div class="form-group">
								<label class="col-lg-3 control-label">微信号</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text"  value="${result.suspectMicroletters }"
										id="suspectMicroletters" name="suspectMicroletters" onblur="validateSuspectPhone()">
										<span id="addinputMicroletterslabel" class="empty c11"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3 control-label">QQ号码</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text" id ="suspectQQ"  name ="suspectQQ" value="${result.suspectQQ}" onblur="validateSuspectQQ()">
									<span id="addinputQQlabel" class="empty c11"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3 control-label">所在组织名称</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text" id = "suspectUnitName" name = "suspectUnitName" value="${result.suspectUnitName}" onblur="validateSuspectUnitName()">
									<span id="addinputUnitNamelabel" class="empty c11"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-3 control-label">组织地址</label>
								<div class="col-lg-5">
									<input class="form-control" placeholder="请输入" type="text" id = "suspectUnitAddress" name = "suspectUnitAddress" value="${result.suspectUnitAddress}" onblur="validateSuspectUnitAddress()">
									<span id="addinputUnitAddresslabel" class="empty c11" ></span>
								</div>
							</div>
							<div class="form-group" style="margin-top: 54px;">
								<div class="col-lg-offset-5 col-lg-5">
										<a class="btn w-xs btn-info" style="margin-right: 30px;" onclick="return editSuspectComFirm()">提交</a>
									<a href="<%=basePath%>getSuspectlist.php" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="return">取消</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer2.jsp"></jsp:include>
	
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>template/js/cutover/NewCity.js"></script>
	<script type="text/javascript">
	window.onload = myfun();
</script>
</body>
</html>