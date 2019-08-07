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
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script
		src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
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


</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">PDF检索器</h1>
	</div>
	<div class="container">
		<div class="col-md-12">
			<div class="row" style="margin-top: 1%;">
				<div class="panel panel-default">
					<div class="panel-heading">搜索</div>
					<div class="panel-body">
						<form class="form-inline" role="form" id="searchForm" action="<%=basePath%>admin/getPdfEvidence.php" method="post" onsubmit=" return false">
							<input type="hidden" name="pageno" id="pageno"/>
							<div class="form-group">
								<label for="" class="clabel">文件关键词:</label><input id="evName" name="evName" value=""
									class="form-control" placeholder="请输入..." type="text" style="width:390px;"/>
							</div>
							<button type="button" class="btn btn-info" style="width:80px;" onclick="showAllHandledFile(1)">搜索</button>
							
						</form>
					</div>
				</div>
			</div>
			<div class="row" id="mod1" style="display: none">
				<div class="panel panel-default">
					<div class="panel-heading">文件列表
					</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br04"
							style="text-align: center;">
							<div align="center" class="row"><img src="<%=basePath %>template/img/result.png"></div>
							<div align="center" class="row"><span class="c03">未找到相关文件结果 </span></div>
						</table>
					
					</div>
				</div>
			</div>
			<div class="row"  id="mod2">
				<div class="panel panel-default">
					<div class="panel-heading">文件列表   <span style="float: right;" id="info">
					 <!-- <select style="width:100px; height: 27px;margin-top: -4px;"id="sortType" name="sortType">
						<option>修改时间</option>
						<option>创建时间</option>
						<option>访问时间</option>
						<option>文件大小</option>
						<option>文件名</option>
						<option>文件类型</option>
						已找到12个文件中包含关键词"账单"，点击查看xiang'qin 
					</select> -->
					
					</span> </div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hoverbr04"
							style="text-align: center;">
							<thead>
								<tr>
									<!-- <th class="alcenter"><input type="checkbox" id="ckall" onclick="selectAll()"/></th> -->
									<th class="alcenter">文件名</th>
									<th class="alcenter">文件类型</th>
									<th class="alcenter">文件大小</th>
									<th class="alcenter">创建时间</th>
									<th class="alcenter">访问时间</th>
									<th class="alcenter">修改时间</th>
									<th class="alcenter">所属案件</th>
									<th class="alcenter">操作</th>
								</tr>
							</thead>
							<tbody id="tbcont">
						
					
							</tbody>
							
						</table>
						<div class="alcenter" style="font-size: 14px">
							<div id="total" class="pagecount inline" style="height: 29px;">
								<span id="tot"></span>
							</div>
							<div class="pagebar inline"
								style="position: absolute; right: 10px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;">
									<li id = "pages1"></li>
									
									<li id = "pages"></li>	
				            		
									<li id = "pages2"></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- lyxg -->
		<div class="modal right fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="panel panel-default" style="padding-right: 10px;">
						<div class="panel-body" style="height: 950px;" >
							<div class="panel-heading b21 br004"
								style="padding: 10px;">命中预览</div>
							
								<span id="test1" ></span>
								<div class="panel-heading br004 b22" style="padding: 10px;">
								   <div class="b22" style=" height: 60px;float: left;">
								   <img  id="docTypeyl" class="b22" src="<%=basePath %>template/imgf.png" style="width: 50px;height: 60px;">
								   </div>
								   <div style="margin-top: 17px;margin-left: 65px;"><span id="docfileNameyl"></span>&nbsp;&nbsp;&nbsp;<span id="docdateyl"></span><br><span id="docfileSizeyl" >MB</span>
								    <div style=" padding-left: 300px; padding-bottom: 0px;">
						                  <img class="b22" src="<%=basePath %>template/img/201.png" style="width: 15px;height: 15px;"><a id="url" href="#"><span id="url" onclick="downloaddoc()">下载文件</span></a>
						                   <span id="docfileurl" style=" display:none;"></span>
						                  <img class="b22" src="<%=basePath %>template/img/202.png" style="margin-left: 15px; width: 15px; height: 15px;">
						                  
						                  <a href="#"  target="_blank" id="lookurl" onclick="lookOnline()">在线预览</a>
								         </div> 
								         </div>
						                
								<hr>
								     <div class="b22" style="width: 530px;height: 400px;">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="doccontentyl"></span>
								      
								      </div>

								
								</div>
					</div>
					
				</div>
		
			</div>
				
		</div>
	</div>
	
	<!-- lyxg -->
	</div>
</body>
	
</html>

<script type="text/javascript">
function searchbycondit() {
	$("#searchForm").submit();
}

function getid() {
	var evid = $("#id_right").text();
	window.location.href = "<%=basePath %>getEvidencelist.php?evid="+evid;
	//alert("kdk");
}

function searchone(i) {
	/* <!-- lyxg --> */
	var docfileName = document.getElementById("docdetails1"+i+"").innerText;
	var docdate = document.getElementById("docdetails2"+i+"").innerText;
	var docType = document.getElementById("docdetails3"+i+"").innerText;
	var doccontent = document.getElementById("docdetails4"+i+"").innerText;
	var docfileSize = document.getElementById("docdetails5"+i+"").innerText;
	var docfileurl = document.getElementById("docdetails6"+i+"").innerText;

	document.getElementById("docfileNameyl").innerText= docfileName;
	document.getElementById("docdateyl").innerText= docdate;
	document.getElementById("docTypeyl").innerText= docType;
	var docImg=document.getElementById("docTypeyl")
	docType="temp"+docType;//防止.前面为空时分割不成功
	var strs= new Array(); //定义一数组 
	strs= docType.split("."); //得到类型
	var typeName=strs[1];
	docImg.setAttribute("src", "<%=basePath %>template/img/"+typeName+".png");
	document.getElementById("doccontentyl").innerText= doccontent;
	document.getElementById("docfileSizeyl").innerText= docfileSize+"KB";
	document.getElementById("docfileurl").innerText= docfileurl;
	/* <!-- lyxg --> */
	}





function subForm(pageno){
	$("#pageno").val(pageno);
}
function selectAll(){
	 var ckall=document.getElementById("ckall");
    var ck=document.getElementsByName("ids");
    for(var i=0;i<ck.length;i++){
    	ck[i].checked=ckall.checked;
    }
}

function setHref(){
	var array=document.getElementsByName("ids");
    var str = "";
    var noArr = new Array();
    var ids=new Array();
    for(var i=0;i<array.length;i++){
    	if(array[i].checked){
        	var id = array[i].value;
        	ids.push(id);
        	noArr.push($("#"+id+"_no").text());
         	str += "、"+$("#"+id+"_name").text();
      	}
    }
    if(ids.length===0){
    	alert("请至少选择一个文档！");
    	return false;
    }else{
    	str = str.substring(1);
  	}
    var param = "id="+ids.toString()+"&noStr="+noArr.toString()+"&str="+str;
	param = encodeURI(param);
	return true;
}
	 
window.onload = showAllHandledFile(1);
var dl=1;
//显示在表格文档显示
function showAllHandledFile(pageno){
	var evName =$("#evName").val();
	var caseName =$("#caseName").val();
	var evStatus =$("#evStatus").val();
//	var sortType =$("#sortType").val();
	
	$.ajax({
		type : "POST",
		url : "<%=basePath%>admin/getPdfEvidence.php",
		data : {
			"evName":evName,
			"caseName":caseName,
			"evStatus":evStatus,
			"pageno":pageno/* ,
			"sortType":sortType */
		},
		dataType : "json",
		async: true,
		success : function(data) {
			var docs=data.hits.hits;
			var sizes=10;
			var pagesum=data.hits.total;
			var pagenum = pagesum / sizes;
			var length=5;  //要显示的分页页数
			
			if(pagenum%1!=0){
					pagenum=pagenum+(1-pagenum%1);
			}
			//查找结果信息展示
            $("#pages").empty();
            $("#info").empty();
			if($("#evName").val()!=null&&$("#evName").val()!="")
				var htmlinfo='已找到'+pagesum+'个文件中包含关键词“'+$("#evName").val()+'”，点击查看详情';
			else
				var htmlinfo='已找到'+pagesum+'个文件，点击查看详情';
			$("#info").append(htmlinfo);
			
			//用于删除之前显示的页数，动态添加时id名均设为order
			for(var i=1;i<=length;i++)
				  $("#order").remove();
			
			if(pagesum<sizes){
				var html2 = '<li class="active" id="order"><a href="#" onclick="showAllHandledFile(1)">1</a></li >';
				$("#pages").after(html2);	
				
				
			}else{
			
			if(pageno<pagenum){
				if(pageno+length-1<=pagenum){
					var html2="";
					if(pageno-2>0){
						for(var i =pageno-2;i<=pageno+length-1-2;i++){	
							if(i==pageno)
								html2 += '<li class="active" id="order"><a href="#" onclick="showAllHandledFile('+i+')">'+i+'</a></li >';
								else
					
									html2 += '<li id="order"><a href="#" onclick="showAllHandledFile('+i+')">'+i+'</a></li >';  
					   				   }
						}
					else{
						for(var i =1;i<=length;i++){	
							if(i==pageno)
								html2 += '<li class="active" id="order"><a href="#" onclick="showAllHandledFile('+i+')">'+i+'</a></li >';
								else
					
									html2 += '<li id="order"><a href="#" onclick="showAllHandledFile('+i+')">'+i+'</a></li >';  
					   				   }
					}
				//alert(html2);
				$("#pages").after(html2);
			
				}/* if */
				else{
					var html2="";
					if(pagenum>=length){
					for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){	
						
						 
						 if(i==pageno)
							html2 += '<li class="active" id="order"><a href="#" onclick="showAllHandledFile('+i+')">'+i+'</a></li >';
						else
							html2 += '<li id="order"><a href="#" onclick="showAllHandledFile('+i+')">'+i+'</a></li >'; 
						
					   }
					//alert(html2);
					$("#pages").after(html2);	
					}
					else{
						
						for(var i =1;i<=pagenum;i++){	
							
							  
							 if(i==pageno)
								html2 += '<li class="active" id="order"><a href="#" onclick="showAllHandledFile('+i+')">'+i+'</a></li >';
							else
								html2 += '<li id="order"><a href="#" onclick="showAllHandledFile('+i+')">'+i+'</a></li >';
							 
							
						   }
						$("#pages").after(html2);	
					}
						
				}
			}
			else{
				if(pageno==pagenum){
					var html2="";
				
					for(var i =pageno-length+1>0?pageno-length+1:1;i<=pagenum;i++){	
					
					
					 if(i==pageno)
						html2 += '<li class="active" id="order"><a href="#" onclick="showAllHandledFile('+i+')">'+i+'</a></li >';
					else
						html2 += '<li id="order"><a href="#" onclick="showAllHandledFile('+i+')">'+i+'</a></li >';
						 
				   }
					$("#pages").after(html2);
				}/* if */
			}
		}
	 

			$("#tot").empty();
			$("#pages1").empty();
			$("#pages2").empty();
			var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
			$("#tot").append(html3);
			
			
			var html5 = '<a href="#" onclick="showAllHandledFile('+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
			$("#pages1").append(html5);
			
			var html4 = '<a href="#" onclick="showAllHandledFile('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
			$("#pages2").append(html4);
			
			$("#tbcont").empty();
			
			
			
			
			
			
			
			
			
			
			if(docs.length < 1){
				$("#mod1").show();
				$("#mod2").hide();
			}else{
				$("#mod1").hide();
				$("#mod2").show();
			}
			$.each(docs,function(i,item){
				/* <!-- lyxg --> */
				fileName=item._source.fileName;
				if(evName!=null&&evName!=""){					
					//alert("test");
					var str="/"+evName+"/i";
					var fileName2=fileName.toLowerCase();  //临时的名字，获取索引用	
					var index=fileName2.indexOf(evName.toLowerCase()); 
					var str2=fileName.substring(index,index+evName.length); //用于替换成红色字体显示，防止因大小写不同导致替换结果大小写与原来不一致
					fileName=fileName.replace(eval(str),"<font class='c11'>"+str2+"</font>");		
				} 
				var html = '+<tr>'+
			    '<td>'+fileName+'</td> '+
				'<td>'+item._source.docType +'</td>'+
				'<td>'+item._source.fileSize +'MB'+'</td>'+
				'<td>'+item._source.createDate +'</td>'+
				'<td>'+item._source.editDate +'</td>'+
				'<td>'+item._source.viewDate +'</td>'+
				'<td style=" display:none;">'+item._source.file_download_url +'</td>'+
				'<td>'+item._source.caseName +'</td>'+
			    '<td id="yulang" data-toggle="modal" data-target="#myModal" onclick="searchone('+i+')"><a href="#">预览</a>'+
			    '<span id="docdetails1'+i+'" style=" display:none;">'+item._source.fileName+'</span>'+
				'<span id="docdetails2'+i+'" style=" display:none;">'+item._source.viewDate+'</span>'+
				'<span id="docdetails3'+i+'" style=" display:none;">'+item._source.docType+'</span>'+
				'<span id="docdetails4'+i+'" style=" display:none;">'+item._source.content+'</span>'+
				'<span id="docdetails5'+i+'" style=" display:none;">'+item._source.fileSize+'</span>'+
				'<span id="docdetails6'+i+'" style=" display:none;">'+item._source.file_download_url+'</span>'
			    '</td> '+
			    '<span id="docdetails1'+i+'">'+item._source.fileName+'</span>'+
			'</tr>';
			$("#tbcont").append(html);
			});
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("失败");
		}
	});
}

function downloaddoc(){
	var docpath = document.getElementById("docfileurl").innerText;
	$("#url").attr("href","<%=basePath %>admin/downloadDOC.php?docpath="+docpath);
}

//文档在线预览
function lookOnline(){
	var lookdocpath = document.getElementById("docfileurl").innerText;
	$("#lookurl").attr("href","<%=basePath %>admin/lookOnline.php?lookdocpath="+lookdocpath);
}

</script>