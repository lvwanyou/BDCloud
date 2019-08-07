<%@page import="java.util.UUID"%>
<%@page import="com.xl.cloud.bean.Involve_Person"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<%Involve_Person caseinfo = (Involve_Person)request.getSession().getAttribute("caseinfo2"); 

String uuid="";
String caseCity="";
for(int i=0;i<10;i++){
	 uuid = UUID.randomUUID().toString().replaceAll("-", "");

	}

%>
.control-label {
    padding-left: 233px;
	padding-right: 18px;
}

.col-lg-3 {
	width: 30%;
}

.col-lg-5 {
	padding-left: 0px;
	margin-top: 15px;
}

.btn {
	width: 75px;
	height: 30px;
}

.col-md-12 {
	padding: 0px;
}

th, .alcenter {
	text-align: center;
}

.im_flag {
	font-size: 18px;
	position: absolute;
	margin-left: -10px;
}

</style>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">数据导入</h1>
	</div>
        <div class="" style="padding-left: 20px;padding-top: 15px;">
			<a class="btn btn-default" href="#" onclick="back()">返回</a>
		</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="panel panel-default">
				<div class="panel-heading">案件信息</div>
				<div class="panel-body" style="padding: 0px;"> 
					<table id="datatable" class="table table-striped table-hover br04"
						style="text-align: center; margin-bottom: 0px;">
						<thead>
								<tr>
									<th class="alcenter" style="width: 6%">线索编号</th>
									<th class="alcenter" style="width: 6%">线索类型</th>
									<th class="alcenter" style="width: 8%">线索接受时间</th>
									<th class="alcenter" style="width: 6%">作案时间</th>
									<th class="alcenter" style="width: 8%">线索来源方式</th>
									<th class="alcenter" style="width: 6%">线索内容</th>
									<th class="alcenter" style="width: 6%">创建时间</th>
									<th class="alcenter" style="width: 6%">创建人</th>
									<th class="alcenter" style="width: 6%">线索处置</th>
									<th class="alcenter" style="width: 6%">线索状态</th>
								</tr>
						</thead>
						<tbody id="tbcont">

							<tr data-toggle="modal" data-target="#myModal">
								<td id="case_summary_id" style="display: none;"><%= caseinfo.getId()%></td>
								
								<td><%= caseinfo.getThreadNum()%></td>
								<td><%= caseinfo.getThreadType()%></td>
								
								<td><%= caseinfo.getThreadAcceptDate()%></td>
								<td><%= caseinfo.getCrimeDate()%></td>
								<td><%= caseinfo.getThreadSource()%></td>
								<td><%= caseinfo.getSusItem()%></td>
								<td><%= caseinfo.getCreatedTime()%></td>
								<td><%= caseinfo.getReportPerson()%></td>
								<td><%= caseinfo.getDisposal()%></td>
								<td><%= caseinfo.getStatus()%></td>
								
<%-- 								<td>${result.threadNum }</td>
								<td>${result.threadType }</td>
								<td>${result.threadAcceptDate}</td>
								<td>${result.crimeDate }</td>
								<td>${result.threadSource }</td>
								<td>${result.susItem }</td>
								<td>${result.createdTime }</td>
								<td>${result.reportPerson }</td>
								<td>${result.disposal }</td>
								<td>${result.status }</td> --%>

							</tr>

						</tbody>
					</table>
				</div>
			</div>
			<div class="panel panel-default">
        <div class="panel-body">

            <div id="d1" style="width:100%;">
                    <div class="panel-body" style="padding-top: 35px;">
                        <!-- <form class="bs-example form-horizontal"> -->

                      <!--   <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd"><i
                                    class="im_flag">*</i><span class="Addp" id="person">涉嫌人员</span></label>
                            <div class="col-lg-5">
                                <input class="form-control" placeholder="请输入数据名称" type="text">
                                <input class="form-control" id="addevName" name="addevName"
                                       type="text" onblur="validateAddEvName(this)" placeholder="输入嫌疑人姓名/手机号码/电子邮箱，空格分隔"> <span
                                    id="addevNamelabel" class="empty" style="color:red;"></span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd"><span class="im_flag">*</span>线索类型</label>
                            <div class="col-lg-5">
                                <select class="form-control" id="dataTypes" name="dataTypes">
                                    <option>请选择</option>
                                    <option>涉税</option>
                                    <option>非涉税</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd">涉嫌事项</label>
                            <div class="col-lg-5">
								<textarea id="addcomment" name="addcomment" class="form-control"
                                          rows="5" maxlength="140" placeholder="140字以内"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd"><span class="im_flag"></span>所在地区</label>
                            <div class="col-lg-5" >
                                <div class="col-lg-4" style="padding-left:0px;">
                                    <select class="form-control" id="province" name="province">
                                    </select>
                                </div>
                                <div class="col-lg-4">
                                    <select class="form-control" id="city" name="city">
                                    </select>
                                </div>
                                <div class="col-lg-4">
                                    <select class="form-control" id="town" name="town">
                                    </select>
                                </div>
                            </div>
                        </div> -->

                        <div class="form-group" style="overflow:hidden;">
                        	
                            <label class="col-lg-3 mgAdd" style="text-align:right;line-height:22px;"><span class="Addp"><i class="im_flag">*</i>文件类型</span></label>
                            <div class="col-lg-8">
	                            <input onclick="formatSupport(this)" type="radio" name="radios" value="电子邮件" style="margin-top:0px;vertical-align:middle;">电子邮件&nbsp;&nbsp;
								<input onclick="formatSupport(this)" type="radio" name="radios" value="综合文档" style="margin-top:0px;vertical-align:middle;">综合文档&nbsp;&nbsp;
								<input onclick="formatSupport(this)" type="radio" name="radios" value="电子话单" style="margin-top:0px;vertical-align:middle;">电子话单&nbsp;&nbsp;
								<input onclick="formatSupport(this)" type="radio" name="radios" value="图片资料" style="margin-top:0px;vertical-align:middle;">图片资料&nbsp;&nbsp;
                            	<span id="format1" class="empty"></span>
                            	<span id="format" class="empty"></span>
                            </div>
                        </div>
                        
                        <div class="form-group" style="overflow:hidden;">
                            <label class="col-lg-3 mgAdd" style="text-align:right;line-height:34px;"><span class="Addp"><i class="im_flag">*</i>上传方式</span></label>
                            <div class="col-lg-8">
                                <select style="width:50%;" class="form-control" id="xuanzefangfa2" onchange="xuanzhe()" data-placeholder="Click to Choose...">
                                    <option value=""></option>
                                    <option value="选择本地文件上传">选择本地文件上传</option>
                                    <option value="选择本地文件夹上传">选择本地文件夹上传</option>
                                </select>
                            </div>
                        </div>


                        <div class="form-group" id="upload5" style="display: none;">
                            <form id="importForm4" action="<%=basePath%>UploadClueList.php?clueID=<%= caseinfo.getId()%>&evUUID=<%=uuid%>" method="post" enctype="multipart/form-data">
                               <!--  <label class="col-lg-3 control-label mgAdd" style="text-align:right">所在路径</label> -->
                               	<label class="col-lg-3 mgAdd" style="text-align:right;line-height:34px;">所在路径</label>
                                <div class="col-lg-8">
                                    <!-- <input class="form-control" placeholder="请选择文件目录" type="text"> -->
                                    <input class="form-control" type="file" id="file4" name="file4" value=""
                                           style="opacity: 0; filter: alpha(opacity = 0); width: 50%; padding-top: 12px; cursor: pointer; position: absolute;"
                                           onchange="setFilePath4();"/>
                                    <input class="form-control" id="filePath4" name="filePath4" type="text" style="width: 50%;">
                                    <span style="display:inline;" id="sizetishi0"></span>
                                    <!-- <input class="form-control"
                                    id="filePath4" name="filePath4" type="text">	 -->
                                    <input type="hidden" id="case-num" name="case-num" type="text">
                                    <input type="hidden" id="evidup" name="evidup" value="">
                                </div>
                            </form>
                        </div>
                        
                        <div class="form-group" id="upload6" style="display: none;">
                            <form id="importForm5" action="<%=basePath%>UploadClueList2.php?clueID=<%= caseinfo.getId()%>&evUUID=<%=uuid%>" method="post" enctype="multipart/form-data">
                                <label class="col-lg-3 control-label mgAdd">所在路径</label>
                                <div class="col-lg-5">
                                    <!-- <input class="form-control" placeholder="请选择文件目录" type="text"> -->
                                    <input class="form-control" id="caseNumforhoutai2"
                                           name="caseNumforhoutai 2" type="hidden" value="">
                                    <input type="file" id="file5" name="file5" value=""
                                           style="opacity: 0; filter: alpha(opacity = 0); width: 98%; padding-top: 12px; cursor: pointer; position: absolute;"
                                           multiple="" webkitdirectory="" onchange="setFilePath5();"/>
                                    <input class="form-control" id="filePath5" name="filePath5" type="text">
                                    <span style="display:inline;" id="sizetishi"></span>
                                    <input type="hidden" id="case-num" name="case-num" type="text">
                                    <input type="hidden" id="evidup2" name="evidup2" type="text">
                                </div>
                            </form>
                        </div>

                        <div class="form-group" style="margin-top: 54px;">
                           <div class="col-lg-offset-5 col-lg-5">
                               <button type="submit" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="addEvidence2(this)">提交</button>
                               <button type="reset" class="btn w-xs btn-default" style="margin-right: 30px;" onclick="resetAction()">重置</button>
                           </div>
                        </div>
                        <!-- </form> -->
                    </div>
            </div>
        </div>
        </div>
		</div>
	</div>
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>template/js/jquery.form.js"></script>
	<script>
	var filelength=0;
	$(function(){
		var sum = $("#datatable tr").length;
	});
    var options2;
    $(function () {
        options2 = {

            success: function (data) {
            	
                var evType = $("input[name='radios']:checked").val();
                var fangshi = $("#xuanzefangfa2").val();
                var id = $("#case_summary_id").text();				//线索ID
                var evUUID = '<%=uuid%>';
                var dirPath;
                if (fangshi == "选择本地文件上传") {
                    var dirPath = $("#filePath4").val();
                } else if (fangshi == "选择本地文件夹上传") {
                    var dirPath = $("#filePath5").val();
                }
                $.ajax({
                    type: "POST",
                    url: "<%=basePath%>addInvolve_Person.php",
                    data: {
                        "evType": evType,
                        "dirPath": dirPath,
                        "fangshi": fangshi,
                        "filelength": filelength,
                        "evUUID": evUUID,
                        "status" : 1,
                        "id":id
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.res == "succ") {
                            var evID = data.evID;
                            window.location.href = "<%=basePath%>ThreadManage/evidence_adding_Person.php?cluesIDs3=" + id;
                            $("#evN_now").text(data.oevName);
                        }
                    },
                    error: function (data) {
                        alert("添加检材失败，请重试!");
                    }
                });
            }
        };
    });
	
	function xuanzhe(){
  		var xuanzefangfa=$("#xuanzefangfa2").val(); 
  		if(xuanzefangfa == "选择本地文件上传"){
  			$("#upload4").css('display','none');
  			$("#upload5").css('display','');
  			$("#upload6").css('display','none');
  		}else if(xuanzefangfa == "选择本地文件夹上传"){
  			$("#upload4").css('display','none');
  			$("#upload5").css('display','none');
  			$("#upload6").css('display','');
  		} 
  	}
	
	
	
	function addEvidence2(obj){
		var evTypes = $("input[name='radios']:checked").val();
		if(evTypes=="请选择"){
			alert("请选择数据类型！");
			return false;
		}
		var xuanzefangfa = $("#xuanzefangfa2").val();
		if(xuanzefangfa == "选择本地文件上传"){			
			addEvidence3(obj);
		}else if(xuanzefangfa == "选择本地文件夹上传"){
			addEvidence4(obj);
		}else{
			alert("请选择上传方式！");
			return false;
		}
	} 
	   function back() {
	            window.history.back();
	        }
	function addEvidence3(obj){	
		if($("#filePath4").val()==''){
			alert("请选择文件！");
			return false;
		}
		var ddlr=$("#evType").val();
		// var evName=$("#addevName").val().trim();
/* 		if(evName==null || evName==""){
			$("#addevNamelabel").text("*数据名称不能为空！");
			return false;
		} */
/* 		if(evName.length > 50){
			$("#addevNamelabel").text("*数据名称长度不能超过50！");
			return false;
		} */
	//	$("#addevNamelabel").text("");
		$("#loadingDiv").show();
		$("#importForm4").ajaxForm(options2);
		var casenum1= $("#case-num1").text();
		$("#importForm4").submit();
	}
	function addEvidence4(obj){	
		if($("#filePath5").val()==''){
			alert("请选择文件夹！");
			return false;
		}
		var ddlr=$("#evType").val();
/* 		 var evName=$("#addevName").val().trim();
		if(evName==null || evName==""){
			$("#addevNamelabel").text("*数据名称不能为空！");
			return false;
		} */
/* 		if(evName.length > 50){
			$("#addevNamelabel").text("*数据名称长度不能超过50！");
			return false;
		} */
	//	$("#addevNamelabel").text("");
		$("#loadingDiv").show();
		$("#importForm5").ajaxForm(options2);
		$("#importForm5").submit();
	}
	
 	function validateAddEvName(){
		var evName=$("#addevName").val().trim();
		$.ajax({
			type: "POST",
			url: "<%=basePath%>admin/checkName.php",
			dataType : "json",
			data : {
				"evName" : evName
			},							    
			success : function(data) {
				if (data.res == "exist") {
					$("#addevNamelabel").text("*该数据名称已存在！");
					return false;
				}
			},						
			error : function(data) {
				alert("请刷新后重试!");
			}
		});

/* 		if(evName==null || evName==""){
			$("#addevNamelabel").text("*数据名称不能为空！");
			return false;
		}
		if(evName.length > 50){
			$("#addevNamelabel").text("*数据名称长度不能超过50！");
			return false;
		} */
	//	$("#addevNamelabel").text("");
		
	}
	
	function setFilePath4(){
		filelength=1;
		var filepath = $("#file4").val();
		var evType = $("input[name='radios']:checked").val();
		var dom = document.getElementById("file4");  
		var fileSize =  dom.files[0].size;//文件的大小，单位为字节B
		var fileType =  dom.files[0].name;
		var len = fileType.split("\.").length;
		var houzhui1 = fileType.split("\.")[len-1];
		 if (evType == "电子邮件"){
			if(houzhui1.toLowerCase()!="eml"&&houzhui1.toLowerCase()!="pst"&&houzhui1.toLowerCase()!="msg"){
				$("#caseNumforhoutai2").val("");
				$("#filePath4").val("文件类型不一致,请重新选择!");
				return false
			}
		} 
		if (evType == "综合文档"){
			if(houzhui1.toLowerCase()!="docx" && houzhui1.toLowerCase()!="doc" &&  houzhui1.toLowerCase()!="pdf" && houzhui1.toLowerCase()!="xlsx" && houzhui1.toLowerCase()!="pptx" && houzhui1.toLowerCase()!="txt" && houzhui1.toLowerCase()!="xls" && houzhui1.toLowerCase()!="ppt"){
				$("#caseNumforhoutai2").val("");
				$("#filePath4").val("文件类型不一致,请重新选择!");
				return false
			} 
		}
		 if (evType == "电子话单"){
			if(houzhui1.toLowerCase()!="xls" && houzhui1.toLowerCase()!="xlsx" ){
				$("#caseNumforhoutai2").val("");
				$("#filePath4").val("文件类型不一致,请重新选择!");
				return false
			}
		}
		if (evType == "手机取证"){
			if(houzhui1!="html"){
				$("#caseNumforhoutai2").val("");
				$("#filePath4").val("文件类型不一致,请重新选择!");
				return false
			}
		}
		if (evType == "黑客数据"){	
			if(houzhui1!="txt" && houzhui1!="csv"){
				$("#caseNumforhoutai2").val("");
				$("#filePath4").val("文件类型不一致,请重新选择!");
				return false
			}
		}
		if (evType == "图片资料"){
			if(houzhui1.toLowerCase()!="bmp" && houzhui1.toLowerCase()!="jpg" && houzhui1.toLowerCase()!="jpeg" && houzhui1.toLowerCase()!="gif" && houzhui1.toLowerCase()!="png"){
				$("#caseNumforhoutai2").val("");
				$("#filePath4").val("文件类型不一致,请重新选择!");
				return false
			}
		} 
		
		if(fileSize>500*1024*1024){
			alert("文件超过500MB，请使用客户端进行上传！");
			document.getElementById("sizetishi0").innerHTML ="文件超过500MB，请使用客户端进行上传！";
			$("#caseNumforhoutai2").val("");
			$("#filePath4").val("请重新选择!");
		}else{
			document.getElementById("sizetishi0").innerHTML ="";
			$("#filePath4").val(filepath);
		}
	}
	function setFilePath5(){
		
		var tai2=$("#filePath4").val();
		var splits =tai2.split(".");
		var len = splits.length;
		var evType = $("input[name='radios']:checked").val();
		var houzhui = tai2.split(".")[len-1];
		var typeFlag=0;
		var sizesum=0;
		var dom = document.getElementById("file5");  
		var fileSizes = dom.files;//文件的大小，单位为字节B
		var len = fileSizes.length;
		filelength=len;
		for(var i=0;i<len;i++){
			var size1 = fileSizes[i].size;
			sizesum+=size1;
			
			var filename = fileSizes[i].name;
			var len2 = filename.split("\.").length;
			var houzhui1 = filename.split("\.")[len2-1];
			
			 if (evType == "电子邮件"){
				if(houzhui1.toLowerCase()!="eml"){
					$("#caseNumforhoutai2").val("");
					$("#filePath5").val("文件夹内有类型不一致,请重新选择!");
					return false
				}
			} 
			if (evType == "综合文档"){
				if(houzhui1.toLowerCase()!="docx" && houzhui1.toLowerCase()!="doc" &&  houzhui1.toLowerCase()!="pdf" && houzhui1.toLowerCase()!="xlsx" && houzhui1.toLowerCase()!="pptx" && houzhui1.toLowerCase()!="txt" && houzhui1.toLowerCase()!="xls" && houzhui1.toLowerCase()!="ppt"){
					$("#caseNumforhoutai2").val("");
					$("#filePath5").val("文件夹内有类型不一致,请重新选择!");
					return false
				} 
			}
			 if (evType == "电子话单"){
				if(houzhui1.toLowerCase()!="xls" && houzhui1.toLowerCase()!="xlsx" ){
					$("#caseNumforhoutai2").val("");
					$("#filePath5").val("文件夹内有类型不一致,请重新选择!");
					return false
				}
			}
			if (evType == "手机取证"){
				if(houzhui1!="html"){
					$("#caseNumforhoutai2").val("");
					$("#filePath5").val("文件夹内有类型不一致,请重新选择!");
					return false
				}
			}
			if (evType == "黑客数据"){	
				if(houzhui1!="txt" && houzhui1!="csv"){
					$("#caseNumforhoutai2").val("");
					$("#filePath5").val("文件夹内有类型不一致,请重新选择!");
					return false
				}
			}
			if (evType == "图片资料"){
				if(houzhui1.toLowerCase()!="bmp" && houzhui1.toLowerCase()!="jpg" && houzhui1.toLowerCase()!="jpeg" && houzhui1.toLowerCase()!="gif" && houzhui1.toLowerCase()!="png"){
					$("#caseNumforhoutai2").val("");
					$("#filePath5").val("文件夹内有类型不一致,请重新选择!");
					return false
				}
			}  			 
		}
		if(sizesum>500*1024*1024){
			alert("文件超过500MB，请使用客户端进行上传！");
			document.getElementById("sizetishi").innerHTML ="文件超过500MB，请使用客户端进行上传！";
			$("#caseNumforhoutai2").val("");
			$("#filePath5").val("请重新选择!");
		}else{
			document.getElementById("sizetishi").innerHTML ="";
			var filepath = $("#file5").val();
			var evName = $("#addevName").val();
			$("#caseNumforhoutai2").val(evName);
			$("#filePath5").val(filepath);
		}
		
		
	}
		
	//上传类型提示支持格式
	function formatSupport(obj){
		//$('input:radio:checked').val()
		var evType = $("input[name='radios']:checked").val();
		if (evType == "电子邮件"){
			$("#format1").html('<img id="yjgsImg" src="<%=basePath %>template/img/infotx.png" >');
			$("#format").text("邮件支持格式：.eml");
		}
		if (evType == "综合文档"){
			$("#format1").html('<img id="pdfgsImg" src="<%=basePath %>template/img/infotx.png" >');
			$("#format").text("综合文档支持格式：.doc.docx.pdf.xls.xlsx.ppt.pptx.txt");
		}
		if (evType == "电子话单"){
			$("#format1").html('<img id="hdgsImg" src="<%=basePath %>template/img/infotx.png" >');
			$("#format").text("话单支持格式: .xls.xlsx ");
		}
		if (evType == "手机取证"){
			$("#format1").html('<img id="sjgsImg" src="<%=basePath %>template/img/infotx.png" >');
			$("#format").text("手机取证支持格式：.html");
		}
		if (evType == "黑客数据"){
			$("#format1").html('<img id="hkgsImg" src="<%=basePath %>template/img/infotx.png" >');
			$("#format").text("黑客数据库支持格式：.txt .csv");
		}
		if (evType == "图片资料"){
			$("#format1").html('<img id="tpgsImg" src="<%=basePath %>template/img/infotx.png" >');
			$("#format").text("图片支持格式：.jpg .jpeg .gif .png .bmp");
		}
	}
	
	</script>
		<div id="loadingDiv" aria-hidden="false" style="display: none;" class="bootbox modal fade in" tabindex="-1" role="dialog">
            	<div class="modal-dialog" style="width: 182px;margin-top: 23%;">
            		<div class="modal-content">
            			<div class="modal-body">
            				<div class="bootbox-body">
            					<p class="msg">上传中，请稍后...</p>
                    			<div><img id="flashAdImg" src="<%=basePath %>template/img/loading.gif" alt="loading" height="16" width="115"></div>
            				</div>
            			</div>
            		</div>
            	</div>
        </div>
</body>
</html>