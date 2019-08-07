<%@page import="com.xl.cloud.bean.Caseinfo"%>
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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/cloud-admin.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
	
<link href="<%=basePath%>template/css/shengshiqu/common.css" rel="stylesheet" />
<link href="<%=basePath%>template/css/shengshiqu/select2.css" rel="stylesheet" />

<link href="<%=basePath%>template/css/cloud-admin.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<script src="<%=basePath%>template/My97DatePicker/WdatePicker.js"></script>
<style>
.control-label {
	padding-right: 18px;
}

.col-lg-3 {
	width: 30%;
}

.col-lg-5 {
	padding-left: 0px;
}

.btn {
	width: 75px;
	height: 30px;
}

.col-md-12 {
	padding: 0px;
}

.im_flag {
	font-size: 18px;
	position: absolute;
	margin-left: -10px;
}


#province, #city, #town {
	width: 100%;
	height: 30px;
}
.col-lg-6,.col-lg-2{padding-left:0px;}

</style>
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
<script src="<%=basePath%>template/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>template/js/jquery.easyui.min.js"></script>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">编辑案件</h1>
	</div>
	
		<div class="" style="padding-left: 20px;padding-top: 15px;">
			<a href="<%=basePath%>getcaselist.php" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="return">返回</a>
		</div>
		 
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="panel panel-default">
				<div class="panel-heading">案件信息&nbsp;&nbsp;|&nbsp;&nbsp;涉案信息</div>
				<div class="panel-body" style="padding-top: 35px;">
				
					<form class="bs-example form-horizontal" id="edit_form">
						<input type="hidden" name="caseid" id="caseid_edit" value="${result.id }">
						<div class="form-group">
							<label class="col-lg-3 control-label">
							<span class="im_flag">*</span>案件编号</label>
							<div class="col-lg-6">
								<input class="form-control" placeholder="请输入案件编号" type="text" name="caseNum" id="caseNum_edit" readonly= "true" value="${result.caseNum }">
							</div><span class="c11" id="caseNumspan"></span>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label"><span
								class="im_flag">*</span>案件名称</label>
							<div class="col-lg-6">
								<input class="form-control" placeholder="请输入案件名称" type="text" name="caseName" id="caseName_edit" value="${result.caseName }" onblur="check()">
							</div><span class="c11" id="caseNamespan"></span>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">案件所在地区</label>
							
							
							<div class="col-lg-2" style="display:inline-block;"><select name="province" style=with:100%" id="province"></select></div> 
							<div class="col-lg-2" style="display:inline-block;"><select name="city" style=with:100%" id="city"></select> </div>
							<div class="col-lg-2" style="display:inline-block;"><select name="town" style=with:100%" id="town"></select></div>

						</div>
						     <div class="form-group">
							<label class="col-lg-3 control-label"><span
								class="im_flag">*</span>案件受理日期</label>
							<div class="col-lg-6">
								<input class="form-control" placeholder="请输入..." type="text"
									id="acceptTime" name="acceptTime"  value="${result.acceptTime }" onClick="WdatePicker();">
									
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label"><span
								class="im_flag">*</span>案件类型</label>
							<div class="col-lg-6">
								<select class="form-control" id="caseType" name="caseType">
									<%-- <option value="${result.caseType }" selected="selected">${result.caseType }</option> --%>
								  	<c:forEach items="${caseTypeList }" var="cType">
										<option value="${cType.typeName}" <c:if test="${cType.typeName==caseType}">selected</c:if>>${cType.typeName}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">案件标签</label>
							<div class="col-lg-6">
								<input class="form-control" placeholder="请输入标签名称" type="text"
									id="caselabel" name="caselabel" value="${result.label }" ><!-- onchange="biaoqian()" -->
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label"></label>
								<div class="col-lg-6">
									<span id="label1" > 
								    </span>
							    </div>	
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">案件状态</label>
							<div class="col-lg-6">
								<select class="form-control" name="status_edit" id="status_edit">
									<option>办案中</option>
									<option>已结案</option>
								</select>
							</div>
						</div>
						
						<div class="line line-dashed b-b line-lg pull-in"></div>
						<div class="form-group">
							<label class="col-lg-3 control-label">涉嫌人员</label>
							<div class="col-lg-6">
								<input class="form-control" placeholder="输入嫌疑人姓名 电话 邮箱 , 以空格分隔"
									type="text" style="float:left;" id="mainParty" name="mainParty" onblur="validateCaseNum2()"  value="${stu }">
									<span id="mainPartylabel2" class="empty c11"></span><br>
									<span id="mainPartylabel3" class="empty c11"></span>
							</div>
								<button type='button' class="btn btn-info"
									style="margin-right: 30px;" onclick="OnmainParty()" >添加</button>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label"></label>
								<div class="col-lg-6">	
							<span id="label2"></span>
							</div>	
						</div>

						<!-- ------------------------- -->
						<div class="form-group">
							<label class="col-lg-3 control-label">涉嫌组织</label>
							<div class="col-lg-6">
								<input class="form-control" placeholder="输入组织名称，多个以空格分隔"
									type="text" style=" float: left;" name="relatedCompany" id="relatedCompany_edit" value="${result.relatedCompany }" onblur="danwei()">
									<span id="danweired" class="empty c11"></span>	
							</div>
							<button type='button' class="btn btn-info"
									style="margin-right: 30px;" onclick="OnRelatedCompany()" >添加</button>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label"></label>
								<div class="col-lg-6">	
							<span id="label_CompanyList"></span>
							</div>	
						</div>
						
						
						

						<div class="form-group">
							<label class="col-lg-3 control-label">涉嫌物品</label>
							<div class="col-lg-6">
								<input class="form-control" placeholder="输入物品名称，多个以空格分隔"
									type="text" style=" float: left;" name="relatedObject" id="relatedObject_edit" value="${result.relatedObject } " onblur="wupin()">
										<span id="wupinred" class="empty c11"></span>
							</div>
							<button type='button' class="btn btn-info"
									style="margin-right: 30px;" onclick="OnRelatedObject()" >添加</button>
							
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label"></label>
								<div class="col-lg-6">	
							<span id="label_RelatedObject"></span>
							</div>	
						</div>
						<!---------------------------  -->
						<div class="form-group" style="margin-top: 54px;">
							<div class="col-lg-offset-5 col-lg-5">
								
								<!-- <button type="submit" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="return editcaseconfirm()">提交</button> -->
								<a class="btn w-xs btn-info" style="margin-right: 30px;" data-toggle="modal" data-target="#editCase_success" onclick="return editcaseconfirm()">提交</a>
								<a class="btn w-xs btn-default" href="<%=basePath %>getcaselist.php">取消</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改案件成功弹框 -->
		<div class="modal fade" id="editCase_success" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes2" id="resetPassword_successes2"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c20" id="myModalLabel" style="border-left: none;">修改案件</h3>
					</div>
					<div class="modal-body">修改案件成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal" onclick="goCaseList()">确定</button>
					</div> 
				</div>
			</div>
		</div>
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>template/js/cutover/NewCity.js"></script>
	
</body>
</html>
<script type="text/javascript">

$(function (){
    var getProvince = "${result.sheng}";
    var getCity = "${result.shi}";
    var getTown = "${result.qu}";
    
    new PCAS("province", "city", "town", getProvince, getCity, getTown);

    });
var deletid=0;   //涉案人ID控制
var deletid_com=0;  //涉案公司ID控制
var deletid_relobj=0;  //涉案物品ID控制
$(function () {
	
	deletid=0;   //涉案人ID控制
	deletid_com=0;  //涉案公司ID控制
	deletid_relobj=0;  //涉案物品ID控制
	//renyuanflag=0;
	var s = $("#mainParty").val();
	var str_company=$("#relatedCompany_edit").val();
	var str_relatedObject=$("#relatedObject_edit").val();
	
	var num = s.split("/");
	
	var num_company=str_company.split(" ");
	
	var num_relatedObject=str_relatedObject.split(" ");
	
	var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
    var nubmer1 = num[1];
	var number1_company=num_company[1];
	var number1_relatedObject=num_relatedObject[1];
    var nubmer2 = num[2];
    var number2_company=num_company[2];
    var number2_relatedObject=num_relatedObject[2];
	var getMainPartyInfoSize = num.length;
	var getRelatedCompanyInfoSize=num_company.length;
	var getrelatedObjectInfoSize=num_relatedObject.length;
	
	
	
//////////////////////////////////////////
	if(str_company.indexOf("/")>= 0){
		$("#danweired").text("*格式不正确!");
		danweiflag=0;
	}if(str_company.indexOf(";")>= 0){
		danweiflag=0;
		$("#danweired").text("*格式不正确!");
	}if(str_company.indexOf(",")>= 0){
		danweiflag=0;
		$("danweired").text("*格式不正确!");
	}
	////////////////////////////////////////////
	if(str_relatedObject.indexOf("/")>= 0){
		$("#wupinred").text("*格式不正确!");
		wupinflag=0;
	}if(str_relatedObject.indexOf(";")>= 0){
		wupinflag=0;
		$("#wupinred").text("*格式不正确!");
	}if(str_relatedObject.indexOf(",")>= 0){
		wupinflag=0;
		$("wupinred").text("*格式不正确!");
	}
	
	var span;
	if(num[0]!=""){
		for(var i=0;i<num.length;i++){
			
			$("#label2").append($("<input type='hidden' id='shanchu1"+deletid+"' value='"+num[i]+"' type='text'><span id='shanchu2"+deletid+"' class='br05 b12 span-1' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  >"
			+num[i]+"<a onclick='deletemainParty("+deletid+")'><img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></a></span>"));
			deletid++;
		}
		
		if(mainPartys==""){
			mainPartys=s;
		}else{
			mainPartys=mainPartys+"/"+s;
		}
		
	}
/////////////////////涉案公司////////////////////////////////
	
	//deletid_com=1;
	if(num_company[0]!=""){
		for(var i=0;i<num_company.length;i++){
			
			if(num_company[i]!=""&&num_company[i]!=" "){
				//alert(num_company[i]);
			//	alert(RelatedCompany);
			$("#label_CompanyList").append($("<input type='hidden' id='shanchu1_com"+deletid_com+"' value='"+num_company[i]+"' type='text'><span id='shanchu2_com"+deletid_com+"' class='br05 b12 span-1' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  >"
					+num_company[i]+"<a onclick='deleteRelatedCompany("+deletid_com+")'><img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></a></span>"));
					if(RelatedCompany==""){
						RelatedCompany=num_company[i];
					}else{
						RelatedCompany=RelatedCompany+" "+num_company[i];
					}
					
					deletid_com++;
			}
			}
		<%-- $("#label_CompanyList").append($("<input type='hidden' id='shanchu1_com"+deletid+"' value='"+str_company+"' type='text'><span id='shanchu2_com"+deletid+"' class='br05 b12 span-1' style='width: 380px; height: 28px; margin-right: 20px;'  onclick='deleteRelatedCompany("+deletid+")'>"
		+str_company+"<img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></span>"));
		if(RelatedCompany==""){
			RelatedCompany=str_company;
		}else{
			RelatedCompany=RelatedCompany+"/"+str_company;
		}
		deletid++; --%>
	}
	//alert("RelatedCompany+++++++++"+RelatedCompany);
	////////////////////涉案物品////////////////////////////////////
	//deletid_relobj=1;
	if(num_relatedObject[0]!=""){
		for(var i=0;i<num_relatedObject.length;i++){
			if(num_relatedObject[i]!=""&&num_relatedObject[i]!=" "){
			$("#label_RelatedObject").append($("<input type='hidden' id='shanchu1_relobj"+deletid_relobj+"' value='"+num_relatedObject[i]+"' type='text'><span id='shanchu2_relobj"+deletid_relobj+"' class='br05 b12 span-1' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  >"
					+num_relatedObject[i]+"<a onclick='deleteRelatedObject("+deletid_relobj+")'><img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></a></span>"));
					if(RelatedObject==""){
						RelatedObject=num_relatedObject[i];
					}else{
						RelatedObject=RelatedObject+" "+num_relatedObject[i];
					}
					deletid_relobj++;
			}
			}
		<%--  $("#label_RelatedObject").append($("<input type='hidden' id='shanchu1_relobj"+deletid+"' value='"+str_relatedObject+"' type='text'><span id='shanchu2_relobj"+deletid+"' class='br05 b12 span-1' style='width: 380px; height: 28px; margin-right: 20px;'  onclick='deleteRelatedObject("+deletid+")'>"
		+str_relatedObject+"<img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></span>"));
		if(RelatedObject==""){
			RelatedObject=str_relatedObject;
		}else{
			RelatedObject=RelatedObject+"/"+str_relatedObject;
		}
		deletid++;  --%>
	}
	
	 var pa=$("#mainParty").val().split(" ");
	 $("#mainParty").val("");
	 $("#relatedCompany_edit").val("");
	 $("#relatedObject_edit").val("");
     var panum = pa[1];
      $.ajax({
		type: "POST",
		url: "<%=basePath %>checkEvName.php",
		dataType : "json",
		data : {
			"SuspectPhone" : s
		},							    
		success : function(data) {
			if (data[0] != null && data[0] !="") {
				$("#mainPartylabel2").text("*该嫌疑人信息已存在!");
				$("#mainPartylabel3").text("*嫌疑人:"+data[0].suspectName+"   联系电话:"+data[0].suspectPhone+"   邮箱:"+data[0].suspectMail);
				/* renyuanflag=0; */
				renyuanflag=1;
			}else{
				$("#mainPartylabel2").empty();
				$("#mainPartylabel3").empty();
				renyuanflag=1;
			}
		},						
		error : function(data) {
			//alert("请刷新后重试!");
			renyuanflag=0;
		}
	});
        })
var bianhaoflag=1;
var mingchengflag=1;
var renyuanflag=1;
var danweiflag=1;
var wupinflag=1;


//修改案件
var mainPartys="";
var RelatedCompany="";
var RelatedObject="";
function editcaseconfirm(){
	var caseid=$("#caseid_edit").val();
	var caseNum=$("#caseNum_edit").val();
 	var caseName=$("#caseName_edit").val();
 	var caseType=$("#caseType").val();
 	
	<%--var sheng =$("#loc_province").select2('data').text;
	if(sheng=="省份"){
		sheng=" ";
	}
	document.getElementById("loc_province2").value = loc_province;
	var shi =$("#loc_city").select2('data').text;
	if(shi=="地级市"){
		shi=" ";
	}
	document.getElementById("loc_city2").value = loc_city;
	var qu =$("#loc_town").select2('data').text; 
	if(qu="市、县、区"){
		qu=" ";
	}
	document.getElementById("loc_town2").value = loc_town;--%>

	var loc_province = $("#province").val();
	var loc_city = $("#city").val();
	var loc_town = $("#town").val();

	var label=$("#caselabel").val();
	var status=$("#status_edit").val();
	var mainParty=mainPartys;
	var relatedCompany=RelatedCompany;
	var relatedObject=RelatedObject; 
	var acceptTime=$("#acceptTime").val();
	//提交时 判断必填项是否都已正确
	if(mingchengflag==0 ||caseName==""){
		alert("请先填写案件名称!");
		return false;
	}
	if(caseType==""){
		alert("请选择案件类型!");
		return false;
	}
	if(acceptTime==""){
		alert("请选择案件受理日期!");
		return false;
	}
	/* if(renyuanflag==0){
		alert("请填写涉嫌人员!");
		return false;
	} */
	

	$.ajax({
		type:"post",
		url:"<%=basePath %>editcaseconfirm.php",
		dataType : "json",
		data:{
			"caseid" : caseid,
			"caseNum" : caseNum,
			"caseName" : caseName,
			"caseType" : caseType,
			"sheng" : loc_province,
			"shi" : loc_city,
			"qu" : loc_town,
			"label" : label,
			"status" : status,
			"mainParty" : mainParty,
			"relatedCompany" : relatedCompany,
			"relatedObject" : relatedObject,
			"acceptTime" : acceptTime,
		},			

		success : function(data) {
			//alert(data.res);
			if (data.res == "succ") {				
//				alert("修改案件成功");
				$('#editCase_success').modal('show');
			}else if(data.res == "exist"){
				//$("#labelWarnSpan").html("该标签已存在！");
			}
		},						
		error : function(data) {
			alert("修改案件失败，请重试!");
		}
	}); 
	
}

function goCaseList() {
	window.location="<%=basePath %>getcaselist.php";
}

function biaoqian(){
	$("#label1").empty();
	var x = $("#caselabel").val().split(" ");
	var span;
	 //alert(x[1]);
	 for (var i = 0; i < x.length; i++) {
		var s = x[i];
		//alert(s);
		"shanchu' + i + '"
		$("#label1").append($("<span id='shanchu' class='br05 b12 span-1' style='width: 88px; height: 28px; margin-right: 20px;'>"
		+s+"<img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png' onclick='deletelabel()' /></span>"));
	}
	 
	// $("#label1").css('display','');
	//Array label = x.toString().split(" ");
	//alert(label[1]);
	//先获取值以空格切分 然后赋值给数组
	
}

function deletelabel(){
	
	//var x1 = document.getElementById("label").innerText;
	var x1 = $("#shanchu").text();
	var x2 = $("#caselabel").val().replace(x1+" ","");
	$("#caselabel").val(x2);
	//alert(x1); 
	//alert(x2);
	//$("#label").val().replace(x1，"");
	
	//alert(x);
	$("shanchu").remove();
}

//案件名称格式是否正确
function validateCaseName(){
	mingchengflag=0;
	var evName=$("#caseName").val().trim();
	if(evName==null || evName==""){
		$("#addevNamelabel").text("*案件名称不能为空！");
		document.getElementsByTagName('input')[1].style.border = '1px solid red';
		mingchengflag=0;
	}if(evName.length > 50){
		$("#addevNamelabel").text("*案件名称长度不能超过50！");
		mingchengflag=0;
	}else{
		document.getElementsByTagName('input')[1].style.border = '1px solid #CFDADD';
		mingchengflag=1;
	}
	
}

function validateCaseNum2(){
	var evNum=$("#mainParty").val().trim();
	var evNum2=$("#label2").val().trim();
	if(evNum==null || evNum==""){
		document.getElementsByTagName('input')[0].style.border = '1px solid red';
		return false;
	}else if(evNum2!=null && evNum2!=""){
		$("#mainPartylabel2").empty();
		$("#mainPartylabel3").empty();
	}else{
		document.getElementsByTagName('input')[0].style.border = '1px solid #CFDADD';
		$("#mainPartylabel2").empty();
		$("#mainPartylabel3").empty();
	}
}


function validateCaseNum(){
	var evNum=$("#caseNum").val().trim();
	if(evNum==null || evNum==""){
		$("#addevNumlabel").text("*您的案件编号有误");
	
		document.getElementsByTagName('input')[0].style.border = '1px solid red';
		return false;
	}else{
		document.getElementsByTagName('input')[0].style.border = '1px solid #CFDADD';
	}
	if(evNum.length > 50){
		$("#addevNumlabel").text("*您的案件编号有误");
		return false;
	}
	$("#addevNumlabel").text("");
	<%-- $.ajax({
		type: "POST",
		url: "<%=basePath %>checkEvName.php",
		dataType : "json",
		data : {
			"evName" : evName
		},							    
		success : function(data) {
			if (data.res == "exist") {
				$("#addevNamelabel").text("*该编号已存在！");
				return false;
			}
		},						
		error : function(data) {
			//alert("请刷新后重试!");
		}
	}); --%>
}

/*用window.onload调用myfun()*/　　
window.onload = myfun;//不要括号
//获取案件标签list
function myfun() 　　{ 　　 	
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
			for(var i=0;i<data.resData.length;i++){			
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
			}
		},						
		error : function(data) {
			alert("获取标签列表失败，请重试!");
		}
	});
	} 　
/*用window.onload调用myfun()*/　　
window.onload = myfun;//不要括号
//获取案件标签list
function isContains(str, substr) {
    return str.indexOf(substr) >= 0;
}
//点击案件类型
function setLabelArea(id){
	var labelText = $("#"+id+"").html();
	var oldLabelList = $("#caselabel").val();
	if(oldLabelList != "" && oldLabelList != null){
		if(!isContains(oldLabelList,labelText)){
			$("#caselabel").val(oldLabelList + " " + labelText);
		}		
	}else{	
		$("#caselabel").val(labelText);
	}	
}

//添加嫌疑人
//.replace(new RegExp(replaceStr,'gm'),' ')
//var deletid=0;
var deletid=0;
function OnmainParty(){
	renyuanflag=0;
	var s = $("#mainParty").val();
	var strArray = s.replace(/\s+/g," ");
	var getEveryArray = strArray.split("");
	var gets = getEveryArray[getEveryArray.length-1];
	var num = strArray.split(" ");
	var re1 = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
	
	var re = /^1[3|4|5|8][0-9]\d{8}$/;
	
    var nubmer1 = num[1];
    var nubmer2 = num[2];
    
    var suName = num[0];
    var suPhone = num[1];
    var suEmail = num[2];
    
    if(gets != " ") {
    	var getMainPartyInfoSize = num.length;
    } else {
		var getMainPartyInfoSize = num.length - 1;
	}
	
	/* if(getMainPartyInfoSize != 3) {
		alert("涉嫌人员填写格式错误！");
		return false;
	}
	if(getMainPartyInfoSize == 1) {
		alert("涉嫌人员填写格式错误！");
		return false;
	}
	
	if (!re.test(nubmer1)) {
        alert("涉嫌人员填写电话格式错误！");
        return false;
    }  
    
    
	
	if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(nubmer2)) {
        alert("涉嫌人员填写邮箱格式错误!");
        return false;
    }*/
    
    if(getMainPartyInfoSize == 2) {
    	if(nubmer1.indexOf("@")>0){
    		if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(nubmer1)) {
                alert("涉嫌人员填写邮箱格式错误!");
                return false;
            }
    	}else{
    		if (!re.test(nubmer1)) {
                alert("涉嫌人员填写电话格式错误！");
                return false;
            }
    	}
    	
    	/* if (!re.test(nubmer1)) {
            alert("涉嫌人员填写电话格式错误！");
            return false;
        } */
	} 
    
    if(getMainPartyInfoSize == 3) {
    	if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(nubmer2)) {
            alert("涉嫌人员填写邮箱格式错误!");
            return false;
        }
	}
	
	
	if(s.indexOf("/")>= 0){
		$("#mainPartylabel2").text("*格式不正确!");
		renyuanflag=0;
	}if(s.indexOf(";")>= 0){
		renyuanflag=0;
		$("#mainPartylabel2").text("*格式不正确!");
	}if(s.indexOf(",")>= 0){
		renyuanflag=0;
		$("#mainPartylabel2").text("*格式不正确!");
	}
	
	var span;
	if(num[0]!=""){
		$("#label2").append($("<input type='hidden' id='shanchu1"+deletid+"' value='"+s+"' type='text'><span id='shanchu2"+deletid+"' class='br05 b12 span-1' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  onclick='deletemainParty("+deletid+")'>"
		+s+"<img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></span>"));
		if(mainPartys==""){
			mainPartys=s;
		}else{
			mainPartys=mainPartys+"/"+s;
		}
		deletid++;
	}
	 var pa=$("#mainParty").val().split(" ");
	 $("#mainParty").val("");
     var panum = pa[1];
      $.ajax({
		type: "POST",
		url: "<%=basePath %>checkEvName.php",
		dataType : "json",
		data : {
			"SuspectPhone" : s,
			"suName" : suName,
			"suPhone" : suPhone,
			"suEmail" : suEmail
		},							    
		success : function(data) {
			renyuanflag=1;
			if (data[0] != null && data[0] !="") {
				$("#mainPartylabel2").text("*该嫌疑人信息已存在!");
				$("#mainPartylabel3").text("*嫌疑人:"+data[0].suspectName+"   联系电话:"+data[0].suspectPhone+"   邮箱:"+data[0].suspectMail);
				/* renyuanflag=0; */
				
			}else{
				$("#mainPartylabel2").empty();
				$("#mainPartylabel3").empty();
			}
		},						
		error : function(data) {
			//alert("请刷新后重试!");
			renyuanflag=0;
			alert("失败的");
		}
	});
}
//删除嫌疑人
function deletemainParty(id){
	var s = $("#shanchu1"+id+"").val();
	var x1 = $("#shanchu2").text();
	var x2 = $("#mainParty").val().replace(x1+" ","");
	$("#mainParty").val(x2);
	$("#shanchu2"+id+"").remove();
	var mainPartys2="";
	var mainPartyssplit=mainPartys.split("/");
	if(mainPartyssplit.length!=1){
		for(var i=0;i<mainPartyssplit.length;i++){
			var par = mainPartyssplit[i];
			if(par!=s){
				if(mainPartys2==""){
					mainPartys2=par;
				}else{
					mainPartys2=mainPartys2+"/"+par;
				}
			}
		}
	}
	mainPartys=mainPartys2;
	if(mainPartys=="" || mainPartys==null){
		renyuanflag=0;
	}
}
/////////////////////////////////////////////////////
//添加涉案公司
//.replace(new RegExp(replaceStr,'gm'),' ')
//var deletid_com=2;
var danweiflag2=1;
function OnRelatedCompany(){
	$("#danweired").empty();
	danweiflag=1;
	var danwei = $("#relatedCompany_edit").val();
	var pattern = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（-）——|{}【】‘；：”“'。，、？]"; 
	for(var i =0;i<pattern.length;i++){
		 if(danwei.indexOf(pattern[i])>= 0){
				$("#danweired").text("*格式不正确!");
				danweiflag2=0;
			}
	}
	if(danweiflag2==1){
		var s = $("#relatedCompany_edit").val();
		var num = s.split(" ");
		var span;
		if(num[0]!=""){
			for(var i=0;i<num.length;i++){
				if(num[i]!=""&&num[i]!=" "){
				$("#label_CompanyList").append($("<input type='hidden' id='shanchu1_com"+deletid_com+"' value='"+num[i]+"' type='text'><span id='shanchu2_com"+deletid_com+"' class='br05 b12 span-1' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  >"
						+num[i]+"<a onclick='deleteRelatedCompany("+deletid_com+")'><img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></a></span>"));
								
				if(RelatedCompany==""){
							RelatedCompany=num[i];
						}else{
							RelatedCompany=RelatedCompany+" "+num[i];
						}
						deletid_com++;
				}
				}
		}
		 var pa=$("#relatedCompany_edit").val().split(" ");
		 $("#relatedCompany_edit").val("");
		 var panum = pa[1];
		 danweiflag=1;
	}

}
//删除涉案公司
function deleteRelatedCompany(id){
	var s = $("#shanchu1_com"+id+"").val();
	var x1 = $("#shanchu2_com").text();
	var x2 = $("#relatedCompany_edit").val().replace(x1+" ","");
	$("relatedCompany_edit").val(x2);
	$("#shanchu2_com"+id+"").remove();
	var RelatedCompanysplit=RelatedCompany.split(" ");
	RelatedCompany="";
	if(RelatedCompanysplit.length!=1){
		for(var i=0;i<RelatedCompanysplit.length;i++){
			var par = RelatedCompanysplit[i];
			if(par!=s){
				if(RelatedCompany==""){
					RelatedCompany=par;
				}else{
					RelatedCompany=RelatedCompany+" "+par;
				}
			}
		}
	}
	if(RelatedCompany=="" || RelatedCompany==null){
		danweiflag=0;
	}
	//alert(mainPartys);
}
/////////////////////////////////////////////////////
/////////////////////////////////////////////////////
//添加涉案物品
//.replace(new RegExp(replaceStr,'gm'),' ')
//var deletid=2;
var wupinflag2=1;
function OnRelatedObject(){
	$("#wupinred").empty();
	wupinflag=1;
	var wupin = $("#relatedObject_edit").val();
	var pattern = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（-）——|{}【】‘；：”“'。，、？]"; 
	for(var i =0;i<pattern.length;i++){
		 if(wupin.indexOf(pattern[i])>= 0){
			 $("#wupinred").text("*格式不正确!");
			 //alert("943");
				wupinflag2=0;
			}
	}
	if(wupinflag2==1){
		var s = $("#relatedObject_edit").val();
		var num = s.split(" ");
		var span;
		if(num[0]!=""){
			for(var i=0;i<num.length;i++){
				if(num[i]!=""&&num[i]!=" "){
				$("#label_RelatedObject").append($("<input type='hidden' id='shanchu1_relobj"+deletid_relobj+"' value='"+num[i]+"' type='text'><span id='shanchu2_relobj"+deletid_relobj+"' class='br05 b12 span-1' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  >"
						+num[i]+"<a onclick='deleteRelatedObject("+deletid_relobj+")'><img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></a></span>"));
								
				if(RelatedObject==""){
							RelatedObject=num[i];
						}else{
							RelatedObject=RelatedObject+" "+num[i];
						}
						deletid_relobj++;
				}
				}
		}
		 var pa=$("#relatedObject_edit").val().split(" ");
		 $("#relatedObject_edit").val("");
	 	var panum = pa[1];
	 	wupinflag=1;
	}
	

}
//删除涉案物品
function deleteRelatedObject(id){
	var s = $("#shanchu1_relobj"+id+"").val();
	var x1 = $("#shanchu2_relobj").text();
	var x2 = $("#relatedObject_edit").val().replace(x1+" ","");
	$("relatedObject_edit").val(x2);
	$("#shanchu2_relobj"+id+"").remove();
	var RelatedObjectsplit=RelatedObject.split(" ");
	RelatedObject="";
	if(RelatedObjectsplit.length!=1){
		for(var i=0;i<RelatedObjectsplit.length;i++){
			var par = RelatedObjectsplit[i];
			if(par!=s){
				if(RelatedObject==""){
					RelatedObject=par;
				}else{
					RelatedObject=RelatedObject+" "+par;
				}
			}
		}
	}
	if(RelatedObject=="" || RelatedObject==null){
		wupinflag=0;
	}
	//alert(mainPartys);
}
/////////////////////////////////////////////////////

//涉嫌组织格式判断
function danwei(){
	$("#danweired").empty();
	danweiflag=1;
	var danwei = $("#relatedCompany_edit").val();
	if(danwei.indexOf("/")>= 0){
		$("#danweired").text("*格式不正确!");
		danweiflag=0;
	}if(danwei.indexOf(";")>= 0){
		danweiflag=0;
		$("#danweired").text("*格式不正确!");
	}if(danwei.indexOf(",")>= 0){
		danweiflag=0;
		$("#danweired").text("*格式不正确!");
	}
}
//涉嫌物品格式判断
function wupin(){
	$("#wupinred").empty();
	wupinflag=1;
	var wupin = $("#relatedObject_edit").val();
	if(wupin.indexOf("/")>= 0){
		$("#wupinred").text("*格式不正确!");
		wupinflag=0;
	}if(wupin.indexOf(";")>= 0){
		$("#wupinred").text("*格式不正确!");
		wupinflag=0;
	}if(wupin.indexOf(",")>= 0){
		$("#wupinred").text("*格式不正确!");
		wupinflag=0;
	}
}
</script>