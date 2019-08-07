<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/cloud-admin.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/themes/default.css" id="skin-switcher">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<link href="<%=basePath%>template/css/shengshiqu/common.css" rel="stylesheet" />
<link href="<%=basePath%>template/css/shengshiqu/select2.css" rel="stylesheet" />
</head>
<style>
.find_nav_left {
	height: 46px;
	position: relative;
	overflow: hidden;

}
.find_nav_list ul {
	position: relative;
	white-space: nowrap;
	font-size: 0;
}

.find_nav_list ul li {
	display: inline-block;
	padding: 0 13px;
}

.find_nav_list ul li a {
	
	width: 100%;
	height: 100%;
	line-height: 10px;
	font-size: 16px;
	text-align: center;
	display:block;
	line-height:50px;
}

.inline {
	display: inline-block;
}

	#menu, #menu li {
		list-style:none; /* 将默认的列表符号去掉 */
		padding:0; /* 将默认的内边距去掉 */
		margin:0; /* 将默认的外边距去掉 */
		}
#menu li { 
		float:left; /* 往左浮动 */
		padding: 0px 30px 0px 30px;
		}


.menus {
	width:400px;
	height:35px;
 	white-space:nowrap;
	overflow:hidden;
	text-overflow:ellipsis;

}
.spn1 {
padding-left:5.4%;
	font-size: 14px;
}
.span2 {
color:red;
}
.span-1 {
	display: inline-block;
	padding-left: 25px;
	padding-right: 25px;
	margin-top: 20px;
	margin-left: 20px;
	border: 1px solid #FBD5CA;
	width: auto;
	height: 30px;
	text-align: center;
	line-height: 30px;
	border-radius: 2px 12px 2px 2px;
	background-color: #FEF4F1;
}

.menuss{
	width:300px;
	height:35px;
	white-space:nowrap;
	overflow:hidden;
	text-overflow:ellipsis;
}

.sideline {
	display: block;
	position: absolute;
	border: 0;
	height: 10px;
	left: 0;
	top: 43px;
	pointer-events: none;
}

</style>
<body>
	<%-- <jsp:include page="left.jsp"></jsp:include> --%>

	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4" >智能检索</h1>
		<span id="dasousuo" style=" display:none;"></span>
	</div>
 		<div class="" style="padding-left: 20px;padding-top: 15px;float: left;">
			<a href="<%=basePath%>/getGimps.php" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="return">返回上一级</a>
		</div>
		<div class="form-group" style="float: left;margin-left: 20px;">
			<span class="alcenter" style="float: left;margin-top: 23px;">已选案件：</span>
			<div id="spans" style="margin-left: 75px; margin-top: 14px;width:1300px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"></div>
		</div>
	<div class="wrapper-md" style="height: 93%;">
		<div class="col-md-12" style="height: 100%;margin-bottom:27px;">
			
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">
					<span>已找到<span id="nuba"></span>条包含“<span id="text"></span>”的搜索结果</span> 
				
					</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br04" style="text-align: center;">
							<thead>
								<tr>
									<td class="" id = "">
									<div class="find_nav">
									<div class="find_nav_left">
									<div class="find_nav_list">
										<ul id="menu" >
												<!--<li><a  id="sum" class="ali" style="display:none" onclick="Searchdocandemail(1)">全部[0]</a></li>-->
												<!-- <li><a  id="ghdb" class="ali"  onclick="SearchGHDB(1)">黑客数据库[0]</a></li> -->
												<li><a  id="leng" class="ali"  onclick="showAllemail(1)">邮件[0]</a></li>
												<li><a  id="doc" class="ali"  onclick="SearchResultdoc(1)">文档[0]</a></li>
												<li><a  id="jpg"class="ali"  onclick="SearchResultPic(1)">图片[0]</a></li>
												
												<!-- <li><a  id="docdoc"class="ali"  onclick="" >DOC[0]</a></li>
												<li><a  id="pdfpdf"class="ali"  onclick="" >PDF[0]</a></li> -->
												<li id="butn" class="sideline"></li>
										</ul>
									</div>
									</div>
									</div>
									</td>

									<td style="padding-top: 20px;margin: 0px;">
										<button data-target="#myModals" data-toggle="modal" type='button' class="btn btn-info" style="width:120px;" onclick="showcase()" >选择案件</button>
									</td>
								</tr>
							</thead>
						</table>
				
						<div id = "" class="panel-body"style="width: 100%;padding: 0px; height:600px;overflow-y: scroll;">
							<table id = "emails" class="br04" style="width: 100%;table-layout: fixed;">
							
							</table>
						</div>
							<div id="total" style="text-align: center;" class="pagecount inline" style="height: 29px;">
								<span style="margin-left: 700px;" id="tot">
								
								</span>
							</div>
							<div class="pagebar inline" style="position: absolute; right: 134px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;">
									<li id = "pages1"></li>
									
									<li id = "pages"></li>	
				            		
									<li id = "pages2"></li>
								</ul>
							</div>
							<div style="float: right;margin-right: 11px;">
								<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages" name="givePages" onkeydown="onKeyDown(event)"/>
								<button type="button" class="btn" onclick="Searchdocandemail(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
							</div>				
					</div>
				</div>
			</div>
			</div>
		</div>
		
		<!-- 邮件内容 -->
		<div class="modal right fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true" style="overflow-y: auto;">
				
				<div class="panel panel-default" style="width: 60%;height:80%; margin: auto;overflow-y: auto;">
					<div class="panel-heading">
						邮件内容<span style="float: right;"></span>
					</div>
					<div class="panel-body br04"
						style="margin-top: 2%;margin-left: 2%; height: auto;margin-right: 2%;">
						<div id="emailsubject" style="font-weight: bolder;font-size: 17px;">邮件标题</div>
						<!-- <div id="emailurl" style=" display:none;"></div> -->
						
						<div id="emailtoWho">发件人：</div>
						<div id="emailfromWho">收件人：</div>
						<div id="emaildate">时间：</div>
					</div>
					
					<div style="padding-top: 30px;padding-left: 30px;" id="emailcontent" >
				<div id="loadDiv" style="text-align: center;margin-top: 20px">
				 				<img alt="" src="<%=basePath %>template/img/loading2.gif">
				 			</div>
				</div>
				<div class="_modal-mailcontent-append" id="correctEml_attfile" style="overflow: auto;">
				 		</div>
					
				</div>
			</div>
	<div class="modal right fade" id="myModals"  tabindex="-1" style="width:100%;height: 100%;margin: auto;">
		<div class="panel panel-default" style="width:30%;height: 80%;margin:3% auto;" >
			<div class="panel-heading">更改案件</div>
	 			<div class="panel-body" style="width:100%;height: 100%;">
	 					<div class="panel-body" style="width:100%">
							<input id="caseinfo" name="caseinfo" class="form-control" placeholder="搜索案件名称/案件编号" type="text" style="float:left;width:82%;" onkeyup="showcase()"/>
							<button type='button' class="btn btn-info" style="width: 70px;margin-left: 10px;height: 34px" onclick="showcase()">搜索</button>
						</div>
<!-- 						<div class="panel-body" style="width:100%" >
							<span style="float: left;">已选案件：</span> <div id="spans1" style="margin-left: 65px;margin-top: -9px;"></div>
						</div> -->
						<div class="panel-body" style="height: 65%;overflow-y: scroll;">		
								<table id="datatable" class="table table-striped table-hover br04">
									<tbody id="tbcont">
										<tr>
											<td class="td_left"><input type="checkbox" id="ckall" onclick="selectAll()"/></td>
											<td class="td_left">案件编号</td>
											<td class="td_right">案件名称</td>
										</tr>
									</tbody>
								</table>		
						</div>
						<button type='button' class="btn btn-info" style="width: 70px;height: 34px;margin-left: 40%;margin-top: 3%" onclick="checkCase();">选择</button>
				</div>
    	</div>
</div>	
	<jsp:include page="footer2.jsp"></jsp:include>		
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>template/js/cutover/js.js"></script>
	<script type="text/javascript">
	var caseID = new Array();
	var dl=1;
	var typess = '<%=session.getAttribute("typess")%>';
	var big_search_box = '<%=session.getAttribute("big_search_boxx")%>';
	//window.onload = Searchdocandemail(1);
	//window.onload = showcase();
	
	function emltotal(pageno){
		document.getElementById("dasousuo").innerText=big_search_box;
	
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/total.php",
			data : {
				"big_search_box":big_search_box,
			},
			dataType : "json",
			async: true,
			success : function(data) {
				var total=data.total;
				$("#leng").text("邮件["+total+"]");
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("失败");
				 $("#text").text(big_search_box);
			}
		});
	}
	
	
	var picsum=0;
	var emlsum=0;
	var docsum=0;
	var quanmu=0;
	$(function(){
		//查询全部
		if(typess==1){			
			showAllemail(1);
			SearchResultdoc(1);
			SearchResultPic(1); 
		};
		if(typess==3){
			//showAllemail(1);
			//SearchResultdoc(1);
			document.getElementById("doc").click();
		};
		if(typess==4){
			//showAllemail(1);
			document.getElementById("leng").click();
		};
 		 if(typess==5){
 			//SearchResultdoc(1);
 			document.getElementById("jpg").click();
 		}; 
	});
	
	//查询邮件
	
	function showAllemail(pageno){
		document.getElementById("dasousuo").innerText=big_search_box;
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/SearchResultss.php",
			data : {
				"big_search_box":big_search_box,
				"pageno":pageno,
				"caseidStr":caseidStr
			},
			dataType : "json",
			async: true,
			success : function(data) {
				var total=data.total;
				//emlsum = total;
				var items=data.allesDTOList;
				var caseidd = data.caseidlist;
				 $("#text").text(big_search_box);
				 $("#leng").text("邮件["+total+"]");
				  if(emlsum==0){
					 quanmu=parseInt(quanmu)+parseInt(total);
					 $("#nuba").text(quanmu); 
					 emlsum=1;
				  }
					var sizes=8;
					var pagesum=total;
					var pagenum = pagesum / sizes;
					var length=5;  //要显示的分页页数
					
					if(pagenum%1!=0){
						pagenum=pagenum+(1-pagenum%1);
				}
				
				$("#pages").empty();
				$("#emails").empty();
				//用于删除之前显示的页数，动态添加时id名均设为order
				for(var i=1;i<=length;i++)
					  $("#order").remove();
				
				if(pagesum<sizes){
					var html2 = '<li class="active" id="order"><a href="#" onclick="showAllemail(1)">1</a></li >';
					$("#pages").after(html2);	
					
				}else{
				
				if(pageno<pagenum){
					if(pageno+length-1<=pagenum){
						var html2="";
						if(pageno-2>0){
							for(var i =pageno-2;i<=pageno+length-1-2;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="order"><a href="#" onclick="showAllemail('+i+')">'+i+'</a></li >';
									else
						
										html2 += '<li id="order"><a href="#" onclick="showAllemail('+i+')">'+i+'</a></li >';  
						   				   }
							}
						else{
							for(var i =1;i<=length;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="order"><a href="#" onclick="showAllemail('+i+')">'+i+'</a></li >';
									else
						
										html2 += '<li id="order"><a href="#" onclick="showAllemail('+i+')">'+i+'</a></li >';  
						   				   }
						}
					$("#pages").after(html2);
					}
					else{
						var html2="";
						if(pagenum>=length){
						for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){	
							if(i==pageno)
								html2 += '<li class="active" id="order"><a href="#" onclick="showAllemail('+i+')">'+i+'</a></li >';
							else
								html2 += '<li id="order"><a href="#" onclick="showAllemail('+i+')">'+i+'</a></li >'; 
						   }
						$("#pages").after(html2);	
						}
						else{
							
							for(var i =1;i<=pagenum;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="order"><a href="#" onclick="showAllemail('+i+')">'+i+'</a></li >';
								else
									html2 += '<li id="order"><a href="#" onclick="showAllemail('+i+')">'+i+'</a></li >';
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
							html2 += '<li class="active" id="order"><a href="#" onclick="showAllemail('+i+')">'+i+'</a></li >';
						else
							html2 += '<li id="order"><a href="#" onclick="showAllemail('+i+')">'+i+'</a></li >';
					   }
						$("#pages").after(html2);
					}
				}
			}
				$("#tot").empty();
				$("#pages1").empty();
				$("#pages2").empty();
				var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
				$("#tot").append(html3);
				var html5 = '<a href="#" onclick="showAllemail('+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
				$("#pages1").append(html5);
				var html4 = '<a href="#" onclick="showAllemail('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
				$("#pages2").append(html4);
				$("#emails").empty();
			
				$.each(items,function(i,item){
					var html = '<tr>'+
										'<td data-toggle="modal" data-target="#myModal" onclick="showdetails('+i+')" class="menus" style="padding-left:30px;">'+item.subject.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>")+'</td>'+
							   '</tr>'+
									'<tr data-toggle="modal" data-target="#myModal" onclick="showdetails('+i+')" class="br04">'+
										'<td class="menus" id="menus" style="padding-left:30px;" >发件人：'+item.fromWho.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>")+'</td>'+
										'<td class="menus" id="menus" style="padding-left:200px;">收件人：'+item.toWho.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>")+'</td>'+
										'<td class="menus" style="padding-left:200px;">邮件日期：'+item.date+
										'<td class="menus" id="menus" style="padding-left:200px;">所属案件：'+item.caseName.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>")+'</td>'+
										'<span id="emaildetails1'+i+'" style=" display:none;">'+item.subject.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>")+'</span>'+
										'<span id="emaildetails2'+i+'" style=" display:none;">'+item.toWho.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>")+'</span>'+
										'<span id="emaildetails3'+i+'" style=" display:none;">'+item.fromWho.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>")+'</span>'+
										'<span id="emaildetails4'+i+'" style=" display:none;">'+item.date+'</span>'+
										'<span id="emaildetails5'+i+'" style=" display:none;">'+item.content.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>")+'</span>'+ 
										'<span id="emaildetails6'+i+'" style=" display:none;">'+item.attachmentname.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>")+'</span>'+
										'<span id="emaildetails7'+i+'" style=" display:none;">'+item.filedownloadurl+'</span>'+
										'</td>'+
									'</tr>';
				
					$("#emails").append(html);
				});
				$.each(caseidd,function(i,id){
					caseID.push(id.caseID);
				});
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("失败");
				 $("#text").text(big_search_box);
			}
		});
	}
	//查询文档
	
	function SearchResultdoc(pageno){
		document.getElementById("dasousuo").innerText=big_search_box;
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/SearchResultdoc.php",
			data : {
				"big_search_box":big_search_box,
				"pageno":pageno,
				"caseidStr":caseidStr
			},
			dataType : "json",
			async: true,
			success : function(data) {
				var total=data.total;
				//docsum = total;
				var items=data.allesDTOList;
				var caseidd = data.caseidlist;
				$("#text").text(big_search_box);
				$("#doc").text("文档["+total+"]");
				//$("#nuba").text(total);
				if(docsum==0){
					 quanmu=parseInt(quanmu)+parseInt(total);
					 $("#nuba").text(quanmu); 
					 docsum=1;
				  }
				//分页
				var sizes=8;
				var pagesum=total;
				var pagenum = pagesum / sizes;
				var length=5;  //要显示的分页页数
					
				if(pagenum%1!=0){
					pagenum=pagenum+(1-pagenum%1);
				}
			   
				$("#pages").empty();
				$("#emails").empty();
				//用于删除之前显示的页数，动态添加时id名均设为order
				for(var i=1;i<=length;i++)
						$("#order").remove();
					
					if(pagesum<sizes){
					var html2 = '<li class="active" id="order"><a href="#" onclick="SearchResultdoc(1)">1</a></li >';
					$("#pages").after(html2);	
					}else{
					  if(pageno<pagenum){
						if(pageno+length-1<=pagenum){
							var html2="";
							if(pageno-2>0){
								for(var i =pageno-2;i<=pageno+length-1-2;i++){	
									if(i==pageno)
										html2 += '<li class="active" id="order"><a href="#" onclick="SearchResultdoc('+i+')">'+i+'</a></li >';
										else
							
											html2 += '<li id="order"><a href="#" onclick="SearchResultdoc('+i+')">'+i+'</a></li >';  
							   				   }
								}
							else{
								for(var i =1;i<=length;i++){	
									if(i==pageno)
										html2 += '<li class="active" id="order"><a href="#" onclick="SearchResultdoc('+i+')">'+i+'</a></li >';
										else
							
											html2 += '<li id="order"><a href="#" onclick="SearchResultdoc('+i+')">'+i+'</a></li >';  
							   				   }
							}
						$("#pages").after(html2);
					
						}
						else{
							var html2="";
							if(pagenum>=length){
							for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="order"><a href="#" onclick="SearchResultdoc('+i+')">'+i+'</a></li >';
								else
									html2 += '<li id="order"><a href="#" onclick="SearchResultdoc('+i+')">'+i+'</a></li >'; 
							   }
							$("#pages").after(html2);	
							}
							else{
								
								for(var i =1;i<=pagenum;i++){	
									if(i==pageno)
										html2 += '<li class="active" id="order"><a href="#" onclick="SearchResultdoc('+i+')">'+i+'</a></li >';
									else
										html2 += '<li id="order"><a href="#" onclick="SearchResultdoc('+i+')">'+i+'</a></li >';
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
								html2 += '<li class="active" id="order"><a href="#" onclick="SearchResultdoc('+i+')">'+i+'</a></li >';
							else
								html2 += '<li id="order"><a href="#" onclick="SearchResultdoc('+i+')">'+i+'</a></li >';
						   }
							$("#pages").after(html2);
						}
					}
				}
					$("#tot").empty();
					$("#pages1").empty();
					$("#pages2").empty();
					var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
					$("#tot").append(html3);
					var html5 = '<a href="#" onclick="SearchResultdoc('+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
					$("#pages1").append(html5);
					var html4 = '<a href="#" onclick="SearchResultdoc('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
					$("#pages2").append(html4);
					$("#emails").empty();
				$.each(items,function(i,item){
					var html222 = 	
						'<tr">'+
							'<td class="menuss" style=" padding-left:30px;"><a id="lookOnlineOfSearch" onclick="lookOnlineOfDoc('+i+')">'+item.fileName.replace(big_search_box, "<font class='c11 b38' >" + big_search_box + "</font>")+'</a></td>'+
						'</tr>'+
						'<tr class="br04" onclick="lookOnlineOfDoc('+i+')">'+
							'<td class="menuss" id="menus" style="padding-left:30px">文件大小：'+item.fileSize+'kb</td>'+
							'<td class="menuss" id="docType'+i+'">扩展名：'+item.docType+'</td>'+
							'<td class="menuss">案件名称：'+item.caseName+'</td>'+
							'<td class="menuss">案件编号：'+item.caseID+'</td>'+
							'<td class="menuss">导入日期：'+item.editDate+'</td>'+
							'<td class="menuss" id="docIds'+i+'" style="display:none;">'+item.esDocId+'</td>'+
							'<td class="menuss" id="docUrl'+i+'" style="display:none;">'+item.file_download_url+'</td>'+
						'</tr>';
						
					$("#emails").append(html222);
					
				});
				$.each(caseidd,function(i,id){
					caseID.push(id.caseID);
				});
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("失败");
				$("#text").text(big_search_box);
			}
		});
	}
	
	//查询图片

	function SearchResultPic(pageno){
		document.getElementById("dasousuo").innerText=big_search_box;
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/SearchResultPic.php",
			data : {
				"big_search_box":big_search_box,
				"pageno":pageno,
				"caseidStr":caseidStr
			},
			dataType : "json",
			async: true,
			success : function(data) {
				
				var total=data.total;
				//picsum = total;
				var items=data.allesDTOList;
				var caseidd = data.caseidlist;
				$("#text").text(big_search_box);
				$("#jpg").text("图片["+total+"]");
				//$("#nuba").text(total);
				
				if(picsum==0){
					 quanmu=parseInt(quanmu)+parseInt(total);
					 $("#nuba").text(quanmu); 
					 picsum=1;
				  }
				
				//分页
				var sizes=8;
				var pagesum=total;
				var pagenum = pagesum / sizes;
				var length=5;  //要显示的分页页数
				if(pagenum%1!=0){
					pagenum=pagenum+(1-pagenum%1);
				}
		      
				$("#pages").empty();
				$("#emails").empty();
				//用于删除之前显示的页数，动态添加时id名均设为order
				for(var i=1;i<=length;i++)
					  $("#order").remove();
				if(pagesum<sizes){
					var html2 = '<li class="active" id="order"><a href="#" onclick="SearchResultPic(1)">1</a></li >';
					$("#pages").after(html2);	
				}else{
				if(pageno<pagenum){
					if(pageno+length-1<=pagenum){
						var html2="";
						if(pageno-2>0){
							for(var i =pageno-2;i<=pageno+length-1-2;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="order"><a href="#" onclick="SearchResultPic('+i+')">'+i+'</a></li >';
									else
						
										html2 += '<li id="order"><a href="#" onclick="SearchResultPic('+i+')">'+i+'</a></li >';  
						   				   }
							}
						else{
							for(var i =1;i<=length;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="order"><a href="#" onclick="SearchResultPic('+i+')">'+i+'</a></li >';
									else
						
										html2 += '<li id="order"><a href="#" onclick="SearchResultPic('+i+')">'+i+'</a></li >';  
						   				   }
						}
					$("#pages").after(html2);
					}/* if */
					else{
						var html2="";
						if(pagenum>=length){
						for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){	
							if(i==pageno)
								html2 += '<li class="active" id="order"><a href="#" onclick="SearchResultPic('+i+')">'+i+'</a></li >';
							else
								html2 += '<li id="order"><a href="#" onclick="SearchResultPic('+i+')">'+i+'</a></li >'; 
						   }
						$("#pages").after(html2);	
						}
						else{
							for(var i =1;i<=pagenum;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="order"><a href="#" onclick="SearchResultPic('+i+')">'+i+'</a></li >';
								else
									html2 += '<li id="order"><a href="#" onclick="SearchResultPic('+i+')">'+i+'</a></li >';
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
							html2 += '<li class="active" id="order"><a href="#" onclick="SearchResultPic('+i+')">'+i+'</a></li >';
						else
							html2 += '<li id="order"><a href="#" onclick="SearchResultPic('+i+')">'+i+'</a></li >';
					   }
						$("#pages").after(html2);
					}
				}
			}
				$("#tot").empty();
				$("#pages1").empty();
				$("#pages2").empty();
				var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
				$("#tot").append(html3);
				
				var html5 = '<a href="#" onclick="SearchResultPic('+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
				$("#pages1").append(html5);
				
				var html4 = '<a href="#" onclick="SearchResultPic('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
				$("#pages2").append(html4);

				$("#emails").empty();
				
				var html='<tr>';
					$.each(items,function(i,item){
						var srcStr = (item.picDirpath+"").replace("emaildata","picture");
						html+='<td><div><img src="'+srcStr+'" style="width: 200px; height: 250px;margin-top:10px;margin-left:10px;"/></div></td>';
						if((i+1)%4==0){
							html+='</tr><tr>';
						}
					});
				html+='</tr>';
				$("#emails").append(html);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("查询图片失败");
				$("#text").text(big_search_box);
			}
		});
	}
	
	//点击邮件显示详情
	function showdetails(i){
		$("#emailcontent").html('<div id="loadDiv" style="text-align: center;margin-top: 20px"> <img alt="" src="<%=basePath %>template/img/loading2.gif"> </div>');
		$("#correctEml_attfile").empty();
		var emailsubject = document.getElementById("emaildetails1"+i+"").innerHTML;
	 	var emailtoWho = document.getElementById("emaildetails2"+i+"").innerHTML;
		var emailfromWho = document.getElementById("emaildetails3"+i+"").innerHTML;
		var emaildate = document.getElementById("emaildetails4"+i+"").innerHTML;
		var emailcontent = document.getElementById("emaildetails5"+i+"").innerHTML;
		var attachmentname = $("#emaildetails6"+i+"").html();
		var emlpath = $("#emaildetails7"+i+"").html();
		//alert(emailsubject);
		$("#emailsubject").html("邮件标题 : "+emailsubject.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>"));
		$("#emailtoWho").html("收件人 : "+emailtoWho.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>"));
		$("#emailfromWho").html("发件人 : "+emailfromWho.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>"));
		
		$("#emaildate").html("时间："+emaildate.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>"));
		//$("#emailcontent")[0].innerHTML=emailcontent;
		$.ajax({
			type : "POST",
			url : "<%=basePath%>getCorrectEml.php",
			data : {
				"emlpath":emlpath,
				"attachmentname":attachmentname
			},
			dataType : "json",
			async: true,
			success : function(data) {
				$("#loadDiv").hide();
				$("#emailcontent").html(data.resData.content);
				if(emlpath.substring(emlpath.lastIndexOf("."),emlpath.length)==".eml"){
					$("#emailcontent").html(data.resData.content.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>"));
					}else{
						$("#emailcontent").html(emailcontent.replace(big_search_box, "<font class='c11 b38'>" + big_search_box + "</font>"));	
					}
				//$("#emailcontent")[0].innerHTML=data.resData.content;
				
				var len = data.resData.attfile.length;
				var i = 0;
				var reshtml = "";
				reshtml += "<div class=\"_modal-mailcontent-append-item\" style=\"padding-right:0px;float:left;\">附件：</div>"
				for(i = 0 ;i < len; i ++){
					var aJ = data.resData.attfile[i];
					if(aJ.length>6){
						var aJsplit=aJ.substring(0,6)+"...";
					}else{
						var aJsplit=aJ;
					};
					reshtml += "<div class=\"_modal-mailcontent-append-item\" style=\"padding-right:50px;float:left;\">";
					  var docType = aJ.substring(aJ.lastIndexOf(".") + 1);
					    // alert("去除-后的文件名："+docType);
					   
						if(docType=="doc"||docType=="DOC"||docType=="DOCX"||docType=="docx"){
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/doc.png\" /></a>";
						}
						if(docType=="xls"||docType=="XLS"||docType=="XLSX"||docType=="xlsx"){
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/xls.png\" /></a>";
						}
						if(docType=="ppt"||docType=="PPT"||docType=="pptx"||docType=="PPTX"){
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/ppt.png\" /></a>";
						}
						if(docType=="pdf"||docType=="PDF"){
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/pdf.png\" /></a>";
						}
						if(docType=="jpg"||docType=="JPG"||docType=="png"||docType=="PNG"){
							  //alert(weizui);
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/jpg.png\" /></a>";
						}
						if(docType=="txt"||docType=="TXT"){
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/txt.png\" /></a>";
						}
						if(docType=="rar"||docType=="RAR"||docType=="zip"||docType=="ZIP"){
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/rar.png\" /></a>";
						}
						
					
					reshtml += "<div class=\"_modal-mailcontent-append-item-txt\">"+aJsplit+"</div>";
					reshtml += "</div>";
				}
				if(!attachmentname==""||!attachmentname==null){
					$("#correctEml_attfile").html(reshtml);
					}
				return true;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("失败!");
			}
		});	
		//$("#emailsubject").text(emailcontent);
		//document.getElementById("emailtoWho").innerText="发件人 : "+emailtoWho;
		//document.getElementById("emailfromWho").innerText="收件人 : "+emailfromWho;
		//document.getElementById("emaildate").innerText= emaildate;
		//document.getElementById("emailcontent").innerText=emailcontent;
	}
	
	//文档预览    
 	function lookOnlineOfDoc(i) {
 		$("#loadingDiv").show();
		var lookOnlineOfSearchpath = document.getElementById("docUrl"+i).innerText;
		var docType = document.getElementById("docType"+i).innerText;
		if('扩展名：.txt' == docType) {
			var esDocId = document.getElementById("docIds"+i).innerText;
			$.ajax({
				type : "POST",
				url : "<%=basePath%>admin/lookOnlineTxtBigData.php",
				data : {
					"docId":esDocId
				},
				dataType : "json",
				//async: true,
				success : function(data) {
					var httpUrls="<%=basePath%>preview/"+data.res;
					window.open(httpUrls,'_blank','');
					$("#loadingDiv").css("display","none");
				},
				error:function(){
					
				}
			}); 
		}else{
			$.ajax({
				type : "POST",
				url : "<%=basePath%>admin/lookOnlineOfDoc.php",
				data : {
					"lookOnlineOfSearchpath":lookOnlineOfSearchpath
				},
				dataType : "json",
				//async: true,
				success : function(data) {
					var httpUrls="<%=basePath%>preview/"+data.res;
					window.open(httpUrls,'_blank','');
					$("#loadingDiv").css("display","none");
				},
				error:function(){
				}
			}); 
//			$("#loadingDiv").hide();
		}
	}
 	//显示选择案件的案件
 	function showcase(){
		var casenumorname = document.getElementById("caseinfo").value;
		
/* 		caseID.sort(); //先排序
		 var res = [caseID[0]];
		 for(var i = 1; i < caseID.length; i++){
		  if(caseID[i] !== res[res.length - 1]){
		   res.push(caseID[i]);
		  }
		 } */
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/getCaseOfEmail.php",
			data : {
				"casenumorname":casenumorname,
				"big_search_box":big_search_box
				//"caseID":res
			},
			traditional: true,
			dataType : "json",
			async: true,
			success : function(data) {
				$("#tbcont").empty();
				var htmlhead='<tr>'+
									'<td class="td_left"><input type="checkbox" id="ckall" onclick="selectAll()"/></td>'+
									'<td class="td_left">案件编号</td>'+
									'<td class="td_right">案件名称</td>'+
							'</tr>';
				$("#tbcont").append(htmlhead);	
				$.each(data,function(i,item){
					var htmlhead2='<tr data-toggle="modal">';
					var html = '<tr>'+
									'<td class="td_left"><input id="caselist'+i+'" type="checkbox" name="ids" value="'+item.caseName+' '+item.id+'" onclick="showChoose(this.id);" />'+
									'<td class="td_left">'+item.caseNum+'</td>'+
									'<td class="td_right" id="">'+item.caseName+
									'</td>'+
								'</tr>';
					$("#tbcont").append(html);					
				});

			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
 	//选择所有复选框
	function selectAll(){
		 var ckall=document.getElementById("ckall");
	    var ck=document.getElementsByName("ids");
	    for(var i=0;i<ck.length;i++){
	    	ck[i].checked=ckall.checked;
	    }
	}
	function showChoose(id){
		 var ckall=document.getElementById(id);
	    	ck[i].checked=ckall.checked;
	}
	
	//选择某个复选框案件将案件信息展示在页面上
	var caseidStr ="";
	function checkCase(obj){
		$("#caseinfo").val("");
		 caseidStr ="";
	    var CaseInfo = document.getElementsByName("ids");
	    var str = [];
	    for(i=0;i<CaseInfo.length;i++){
	        if(CaseInfo[i].checked){
	            str.push(CaseInfo[i].value.split(" ")[0]+" "+CaseInfo[i].value.split(" ")[1]);
	            if(caseidStr==""){
	            	caseidStr=CaseInfo[i].value.split(" ")[1];
	            }else{
	            	caseidStr=caseidStr+" "+CaseInfo[i].value.split(" ")[1];
	            }
	        }
	    }
	   	if($("#spans").text()!=null || $("#spans").text()!=""){
	   		$("#spans").empty();
	   	}
	   	for (var i = 0; i < str.length; i++) {
	   		var splits = str[i].split(" ");
	   		var	s=splits[0];
	   		var	caseId=splits[1];
	   		
	   		var st = str[i];
			var caseName="";
			if(st.length>0){
				caseName = st.substring(0,6) + "...";
			}
			var html ='<span class="span-1" id="shanchu2'+i+'" style="width: 150px; height: 30px;margin-right: 15px;margin-top: 5px;" onclick="deletemainParty(this.id,'+caseId+')">'+caseName+
			'<img style="float: right; margin-right: 5px; margin-top: 7px;" src="<%=basePath%>template/img/x.png" /></span>';
			$("#spans").append(html);	
		}
	    $("#myModals").modal("hide");
	   	showAllemail(1);
	   	Searchdocandemail(1);
	   	SearchResultdoc(1);
	   
	}
	
	function deletemainParty(id,caseId){
		$("#"+id+"").remove();
		var caseidStrSplit=caseidStr.split(" ");
		var caseidStrNew="";
		if(caseidStrSplit.length>0){
			for(var i=0;i<caseidStrSplit.length;i++){
				var par = caseidStrSplit[i];
				if(par!=caseId){
					if(caseidStrNew==""){
						caseidStrNew=par;
					}else{
						caseidStrNew=caseidStrNew+" "+par;
					}
				}
			}
		}
		caseidStr=caseidStrNew;
	   	showAllemail(1);
	   	Searchdocandemail(1);
	   	SearchResultdoc(1);
	}
	
	//回车搜索事件
	function onKeyDown(event){
	   var e = event || window.event || arguments.callee.caller.arguments[0];
	   if(e && e.keyCode==13){ // enter 键
		   Searchdocandemail(1);
	   }
	}
	</script>
		<div id="loadingDiv" aria-hidden="false" style="display: none;" class="bootbox modal fade in" tabindex="-1" role="dialog">
            	<div class="modal-dialog" style="width: 155px;margin-top: 25%;">
            		<div class="modal-content">
            			<div class="modal-body">
            				<div class="bootbox-body">
            					<p class="msg">正在加载，请稍后...</p>
                    			<div><img id="flashAdImg" src="<%=basePath %>template/img/loading.gif" alt="loading" height="16" width="114"></div>
            				</div>
            			</div>
            		</div>
            	</div>
        </div>
</body>
</html>