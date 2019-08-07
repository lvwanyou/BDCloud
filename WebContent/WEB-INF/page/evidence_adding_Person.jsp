<%@page import="com.xl.cloud.bean.Caseinfo"%>
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
<%
	String evi = (String)request.getSession().getAttribute("evName");
%>

</style>
<link rel="stylesheet" href="<%=basePath%>template/css/style.css">
</head>

<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">数据导入</h1>
	</div>
	<span id="importClueIDs1" style="display: none;">${result }</span>
			<div class="row" style="height: ;width: 98%;margin-left: 1%;margin-top: 1%;">
				<div class="panel panel-default">
					<div class="panel-heading">导入状态</div>
					
					<!-- 数据导入进度条 -->
					<div class="panel-body" style="padding: 0px;">
						<table id="datatable" class="table table-striped table-hover br04" style="margin-bottom: 0px;">
							<thead>
								<tr>
									<th class="alcenter"  style="text-align:left;padding: 20px;">
									
										<div class="progress">
	      									<span id="jindu"  class="blue" style="width: 0%;"><span id="jindu2" >0%</span></span>
	    							    </div>
									<span class="c04" style="margin-left: 15px;font-size: 12px;">用时：</span>
									 <input type="text" id="oT"  readonly=readonly style="border:0;background:transparent;"/>
    								<button id="oA" style=" display:none;" >开始计时</button><!-- style=" display:none;"  -->
                                     <!--  <input type="text" id="getlog" name=""getlog"" value="点击开始加载" onclick="startfun()" > -->
									</th>
								</tr>
							</thead>
						</table>
					</div>
				<input type="hidden" id=evName value="<%=evi%>">
			
				</div>
			</div>
	

	<!-- 上传日志的表格 -->
    <div class="row" style="width: 48.5%;margin-top: ;margin-left: 1%;float:left;" >
				<div class="panel panel-default">
					<div class="panel-heading">上传日志<span style="float: right;"> <img alt="" src="<%=basePath %>template/img/addevidence.png"></span> </div>
					<div class="panel-body" style="padding: 0px;height: 258px;margin-left: 25px;overflow-y:scroll;">
						<div id="showAllFileArea" class="br22" style="width:100%;height:100%;min-height:150px;overflow-y:auto;overflow-x:none;">
								<table id="tableid" class="table table-striped table-bordered table-hover" style="font-size:14px;">
									<tbody id="showAllFileArea_content">

									</tbody>
								</table>
							</div>
					</div>
				</div>
	</div>
	
	<!-- 格式统计表格 -->
		<div class="row" style="width: 48.5%;margin-top: ;margin-right: 1%;float:right; " >
				<div class="panel panel-default">
					<div class="panel-heading">格式统计<span style="float: right;"> <img alt="" src="<%=basePath %>template/img/addevidence.png"></span> </div>
					<div class="panel-body" style="padding: 0px;height: 258px;overflow-y:scroll;">
						<table id="datatables" class="table table-striped table-hover br04"
							style="margin-bottom: 0px;">
							<thead>
								<tr>
									<th class="alcenter"  style="text-align:left;padding-left: 35px;">文件类型</th>
									<th class="alcenter">已上传</th>
									<th class="alcenter">已处理</th>
									<th class="alcenter">已结束</th>
								</tr>
							</thead>
							<tbody id="tbcont">
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">eml</td>
									<td id="uploadedemlCounts">0</td>
									<td id="handledemlCounts">0</td>
									<td id="failedemlCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">txt</td>
									<td id="uploadedtxtCounts">0</td>
									<td id="handledtxtCounts">0</td>
									<td id="failedtxtCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">doc</td>
									<td id="uploadeddocCounts">0</td>
									<td id="handleddocCounts">0</td>
									<td id="faileddocCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">docx</td>
									<td id="uploadeddocxCounts">0</td>
									<td id="handleddocxCounts">0</td>
									<td id="faileddocxCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">pdf</td>
									<td id="uploadedpdfCounts">0</td>
									<td id="handledpdfCounts">0</td>
									<td id="failedpdfCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">xls</td>
									<td id="uploadedxlsCounts">0</td>
									<td id="handledxlsCounts">0</td>
									<td id="failedxlsCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">xlsx</td>
									<td id="uploadedxlsxCounts">0</td>
									<td id="handledxlsxCounts">0</td>
									<td id="failedxlsxCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">ppt</td>
									<td id="uploadedpptCounts">0</td>
									<td id="handledpptCounts">0</td>
									<td id="failedpptCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">pptx</td>
									<td id="uploadedpptxCounts">0</td>
									<td id="handledpptxCounts">0</td>
									<td id="failedpptxCounts">0</td>
								</tr>
								
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">html</td>
									<td id="uploadedhtmlCounts">0</td>
									<td id="handledhtmlCounts">0</td>
									<td id="failedhtmlCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">jpg</td>
									<td id="uploadedjpgCounts">0</td>
									<td id="handledjpgCounts">0</td>
									<td id="failedjpgCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">jpeg</td>
									<td id="uploadedjpegCounts">0</td>
									<td id="handledjpegCounts">0</td>
									<td id="failedjpegCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">gif</td>
									<td id="uploadedgifCounts">0</td>
									<td id="handledgifCounts">0</td>
									<td id="failedgifCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">png</td>
									<td id="uploadedpngCounts">0</td>
									<td id="handledpngCounts">0</td>
									<td id="failedpngCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">bmp</td>
									<td id="uploadedbmpCounts">0</td>
									<td id="handledbmpCounts">0</td>
									<td id="failedbmpCounts">0</td>
								</tr>
								<tr data-toggle="modal" data-target="#myModal" style="text-align: left;">
									<td style="text-align:left;padding-left: 35px;">csv</td>
									<td id="uploadedcsvCounts">0</td>
									<td id="handledcsvCounts">0</td>
									<td id="failedcsvCounts">0</td>
								</tr>
							</tbody>
						</table>
					</div>
			</div>
			<div id="loadingDiv" aria-hidden="false" style="display: none;" class="bootbox modal fade in" tabindex="-1" role="dialog">
            	<div class="modal-dialog" style="width: 182px;margin-top: 23%;">
            		<div class="modal-content">
            			<div class="modal-body">
            				<div class="bootbox-body">
            					<p class="msg">数据整合中，请耐心等待...</p>
                    			<div><img id="flashAdImg" src="<%=basePath %>template/img/loading.gif" alt="loading" height="16" width="115"></div>
            				</div>
            			</div>
            		</div>
            	</div>
        </div>
	</div>
	<!-- 提交按钮 -->
		<div style="text-align: center;padding: 25px;">
                <button type="button" class="btn w-xs btn-info b31 c02" disabled="dasd" id="tijiao" style="margin-right: 30px;" onclick="tiaozhuan()">成功</button>
                <input type="hidden" id="getsums"/>
        </div>
    <jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script>
	$(function(){
		//var sum = document.getElementById('datatable').getElementsByTagName('tr').length;

	});
	
	//上传文件大小
	var daxiao=0;
	//成功之后提交
	function tiaozhuan(){
		$("#loadingDiv").show();    
		var getUploadNum = $("#getsums").val();
		var getSuccessNum = $("#getsums").val();
		var getErrorNum = getUploadNum - getSuccessNum;
		
		var cluIdE = document.getElementById("importClueIDs1").innerText;  //线索ID
		
		window.location.href= "<%=basePath%>admin/cluelist.php?uploadNum="+getUploadNum+"&successNum="+getSuccessNum+"&errorNum="+getErrorNum+"&evSize="+daxiao+"&cluIdE="+cluIdE;
	}	
	//实时更新
	function update(){
		var getUploadNum = $("#getsums").val();
		var getSuccessNum = $("#getsums").val();
		var getErrorNum = getUploadNum - getSuccessNum;
		
		var cluIdE = document.getElementById("importClueIDs1").innerText;  //线索ID
		$.ajax({
			type : "POST",
			url : "<%=basePath%>ThreadManage/updateEvidenceClue.php",
			data : {
				"uploadNum":getUploadNum,
				"successNum":getSuccessNum,
				"errorNum":getErrorNum,
				"evSize":daxiao,
				"cluIdE" : cluIdE
			},
			dataType : "json",
			async: true,
			success : function(data) {
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	var jindutiao = window.setInterval("showHdfsProgressBar()",1000); //进度条
	var rizhi = window.setInterval("showAllHandledFile(1)",1000); //日志
	//window.onload = kaishijishi();//开始计时
	var jsnum=1;
	var jsnum2=1;
	function endjishi(){
		//alert("关闭访问");
		window.clearInterval(jindutiao);
		window.clearInterval(rizhi);
	}	

	//显示上传hdfs进度条 
	function showHdfsProgressBar(){
		//var caseid =0;
		var importClueIDs1 = document.getElementById("importClueIDs1").innerText;  
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/showHdfsProgressBarOfClue.php",
			data : {
//				"evID":1,
				"importClueIDs1":importClueIDs1
			},
			dataType : "json",
			async: true,
			success : function(data) {
				
				var jindunum = data.baifenbi;
				daxiao=data.daxiao;
				//alert(jindunum);
				var jindu = document.getElementById("jindu");
				jindu.style.width=jindunum+"%";
				$("#jindu2").text(jindunum+"%");
				var sum = $("#tableid tr").length;
				//alert("222222"+sum);
				if(jindunum>=95){
					jindu.style.width="100%";
					$("#jindu2").text("100%");
					
					var sum = $("#tableid tr").length;
					//alert("33333"+sum);
					if(jsnum2==1){
						//alert("进度条完成");
						var end=document.getElementById('oA');
						end.click();
						jsnum2=2;
						$("#tijiao").removeAttr("disabled");
						$("#tijiao").css("background","#475e9c");
						endjishi();	
						
					}		
				}else{
					$("#tijiao").css("background","#BFA6A6");
					$("#tijiao").attr("disabled","disabled");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		});
	}
	
	
	//显示在表格左侧（日志）
	function showAllHandledFile(evID){
		//var collectionName="${collectionName}";
		//alert("into showAllHandledFile");
		//var caseid =10;
		var evName = $("#evName").val();
		var importClueIDs1Log = document.getElementById("importClueIDs1").innerText;
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/showAllHandledFile2OfClue.php",
			data : {
				"evID":1,
				"importClueIDs1Log":importClueIDs1Log,
				"evName":evName
			},
			dataType : "json",
			async: true,
			success : function(data) {
				$("#showAllFileArea_content").empty();
				//alert(type);
				var eml=0;
				var txt=0;
				var docx=0;
				var doc=0;
				var pdf=0;
				var xls=0;
				var xlsx=0;
				var ppt=0;
				var pptx=0;
				var html=0;
				var jpg=0;
				var gif=0;
				var png=0;
				var bmp=0;
				var csv=0;
				var jpeg=0;
				//alert(jpg);
				//alert(png);
				$.each(data,function(i,item){
					var leng=data[i].content.split("\.").length;
					var type2=data[i].content.split("\.")[leng-1];
					var type=type2.split("【")[0];
					
					if(type=="eml"){
						eml++;
					}
					if(type=="txt"){
						txt++;
					}
					if(type=="doc"){
						doc++;
					}
					if(type=="docx"){
						docx++;
					}
					if(type=="pdf"){
						pdf++;
					}
					if(type=="xls"){
						xls++;
					}
					if(type=="xlsx"){
						xlsx++;
					}
					if(type=="ppt"){
						ppt++;
					}
					if(type=="pptx"){
						pptx++;
					}
					if(type=="html"){
						html++;
					}
					if(type=="jpg"){
						jpg++;
					}
					if(type=="gif"){
						gif++;
					}
					if(type=="png"){
						png++;
					}
					if(type=="bmp"){
						bmp++;
					}
					if(type=="csv"){
						csv++;
					}
					if(type=="jpeg"){
						jpeg++;
					}
					var htmlemail = '<tr>'+
										'<td id="houzhui'+i+'">'+item.content+'<td>'+
									'</tr>';
									
									//type=item.content.split("\\.")[1];
					$("#showAllFileArea_content").append(htmlemail);					
						if(jsnum==1){						
							 var oDiv2 = document.getElementById('oA');
							oDiv2.click(); 	 					
							jsnum=2;
						}						
				});
				update();//及时更新数据
				var sum = $("#tableid tr").length;
				//alert("12323232q"+sum);
				var sums = sum-1;
				$("#getsums").val(sums);

					$("#uploadedemlCounts").text(eml);
					$("#handledemlCounts").text(eml);
					$("#failedemlCounts").text(eml);
					
		
					$("#uploadedtxtCounts").text(txt);
					$("#handledtxtCounts").text(txt);
					$("#failedtxtCounts").text(txt);	

					$("#uploadeddocCounts").text(doc);
					$("#handleddocCounts").text(doc);
					$("#faileddocCounts").text(doc);
					
					$("#uploadeddocxCounts").text(docx);
					$("#handleddocxCounts").text(docx);
					$("#faileddocxCounts").text(docx);

					$("#uploadedpdfCounts").text(pdf);
					$("#handledpdfCounts").text(pdf);
					$("#failedpdfCounts").text(pdf);
				
					$("#uploadedxlsCounts").text(xls);
					$("#handledxlsCounts").text(xls);
					$("#failedxlsCounts").text(xls);
					
					$("#uploadedxlsxCounts").text(xlsx);
					$("#handledxlsxCounts").text(xlsx);
					$("#failedxlsxCounts").text(xlsx);
	
					$("#uploadedpptCounts").text(ppt);
					$("#handledpptCounts").text(ppt);
					$("#failedpptCounts").text(ppt);
					
					$("#uploadedpptxCounts").text(pptx);
					$("#handledpptxCounts").text(pptx);
					$("#failedpptxCounts").text(pptx);

					$("#uploadedhtmlCounts").text(html);
					$("#handledhtmlCounts").text(html);
					$("#failedhtmlCounts").text(html);
				

					$("#uploadedjpgCounts").text(jpg);
					$("#handledjpgCounts").text(jpg);
					$("#failedjpgCounts").text(jpg);
				

					$("#uploadedjpegCounts").text(jpeg);
					$("#handledjpegCounts").text(jpeg);
					$("#failedjpegCounts").text(jpeg);

					$("#uploadedgifCounts").text(gif);
					$("#handledgifCounts").text(gif);
					$("#failedgifCounts").text(gif);

					$("#uploadedpngCounts").text(png);
					$("#handledpngCounts").text(png);
					$("#failedpngCounts").text(png);


					$("#uploadedbmpCounts").text(bmp);
					$("#handledbmpCounts").text(bmp);
					$("#failedbmpCounts").text(bmp);

					$("#uploadedcsvCounts").text(csv);
					$("#handledcsvCounts").text(csv);
					$("#failedcsvCounts").text(csv);
				
				//$("#showAllFileArea_content").html(data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				//异常处理；
				alert("showHandledFileError");
				//alert(XMLHttpRequest.status);
				//alert(XMLHttpRequest.readyState);
				//alert(textStatus);
			}
		});
	}
	
	
	
	//计时器
    var interval, reg = /^\d$/,
    sleep = 10,
    sum = 0;
	onload = function() {
    oA.onclick = function() {
        if (!interval) {
            interval = setInterval(function() {
                sum++;
                var d = new Date("1111/1/1,0:0:0");
                d.setSeconds(sum);
                var h = d.getHours();
                h = reg.test(h) ? "0" + h + ":" : h + ":"
                var m = d.getMinutes();
                m = reg.test(m) ? "0" + m + ":" : m + ":"
                var s = d.getSeconds();
                s = reg.test(s) ? "0" + s : s;
                oT.value = h + m + s;
            }, sleep);
            this.innerHTML = "停止计时";
        } else {
            clearInterval(interval);
            interval = null;
            this.innerHTML = "开始计时";
        }
        }
	};

</script>
</body>
</html>