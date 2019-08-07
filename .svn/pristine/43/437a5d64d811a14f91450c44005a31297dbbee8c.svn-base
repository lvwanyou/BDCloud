<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<style>
*{
	margin: 0px;
  padding: 0px;
  font-family: Microsoft Yahei, arial, verdana, sans-serif;
}
.addBtn {
  line-height: 50px;
  text-align: center;
  font-size: 14px;
  cursor: pointer;
  display:block;
  margin:0px auto;
  width:200px;
  height:50px
}

.wrap{padding:0 5px;}
.wflBox {

  margin: 0px auto;
  position: relative;
  margin-top:20px;
}

.wflBox .absolute {
  position: absolute;
}
/* .wflBox .bg {
  display: block;
  position: absolute;
  height: 100%;
  width: 100%;
  top: 0px;
  left: 0px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  z-index: 5;
  background: url(about:blank);
} */

.wflBox .proImg img {
  display: block;
}
.wflBox .proImg .desc {
  padding-bottom: 30px;
  position: relative;
  z-index: 10;
}
.wflBox .proImg .desc p {
  padding: 0em 0.8em;
  font-size: 12px;
}
.wflBox .proImg .desc h5 {
  padding: 0em 1em;
  height: 55px;
  line-height: 55px;
  font-size: 16px;
  font-weight: normal;
}
.wflBox:after {
  content: " ";
  display: block;
  height: 5px;
  width: 100%;
  clear: both;
}


.alcenter {
	text-align: center;
}

.inline {
	display: inline-block;
}
.form-inline .form-control {
	/*width:auto;*/
	max-width: 391px;
	width: 391px;
}
.clabel {
	margin-right:14px;
}

.small_pic{
	margin-top: 5px;
    position: relative;
    width: 19% !important;
}
.AddCheckbox{
	position:absolute;
	top:0px;
	right:0px;
	z-index:2;
}
.AddCheckbox input{width:15px;height:15px;}

#img span{
	margin-left: 42%;
}
.addFot{
	position:absolute;
	bottom:0px;
	display:inline-block;
	height:30px;
	line-height:30px;
	background:rgba(0,0,0,0.5);
	font-size:14px;
	text-align:left;
	padding-left:10px;
	overflow:hidden;
	left:0px;
	width:100%;
	transform:translate(0,-99999px);
	-ms-transform:translate(0,-99999px); 	/* IE 9 */
	-moz-transform:translate(0,-99999px); 	/* Firefox */
	-webkit-transform:translate(0,-99999px);/* Safari 和 Chrome */
	-o-transform:translate(0,-99999px);
}
.addHov:hover .addFot{
	transform:translate(0,0px) ;
	-ms-transform:translate(0,0px); 	/* IE 9 */
	-moz-transform:translate(0,0px); 	/* Firefox */
	-webkit-transform:translate(0,0px);/* Safari 和 Chrome */
	-o-transform:translate(0,0px);
/* 	transition: transform 0.3s ease-in;
	-ms-transform:transform 0.3s ease-in ;
	-moz-transform:transform 0.3s ease-in;
	-webkit-transform:transform 0.3s ease-in;
	-o-transform:transform 0.3s ease-in; */
	
}
</style>
<script type="text/javascript">
function subForm(pageno){
	$("#pageno").val(pageno);
	$("#searchForm").submit();
}
$(document).ready(function() {
	$('div.small_pic a').fancyZoom({scaleImg: true, closeOnClick: true});
});

</script>
</head>
<body >
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">图片挖掘</h1>
	</div>
	<!--选择案件 -->
	<!--搜索和选择案件 合并为一行-->
	<div class="row" style="height:; width: 98%; margin-left: 1%; margin-top: 1%;">
		<div class="form-group" style="float: left;margin-bottom:0px;">
			<span class="alcenter" style="float: left;line-height:35px;">已选案件：</span>
			<div id="spans" style="margin-left: 75px;"></div>
		</div>
			<div>
				<button data-target="#myModal" data-toggle="modal" type='button' class="btn btn-info" style="width:120px;margin-right: 10px; float: right; margin-top: 1px;" onclick="showcase()" >选择案件</button>
			</div>
	</div>
	
	<div class="wrapper-md" style="overflow:hidden;">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">搜索<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onclick="testOfSearch(this)"></span></div>
					<div class="panel-body" id="searchOfForm" style="display: block;padding:10px;">
						<form  style="float:left;padding-right:10px; " class="form-inline" role="form" id="searchForm" action="<%=basePath %>admin/case_xietong.php" method="post">
							<input type="hidden" name="pageno" id="pageno"/>
							<div class="form-group">
							<input type="text" value="" style="display: none;" />
							<input type="text" id="dirName" class="form-control" placeholder="请输入图片名称..." onkeydown="onKeyDown(event)"/>
							</div>
							
							<button type="button" class="btn btn-info c02 b23"
								style="width: 75px; height: 30px;" onclick="showPicture(1)">搜索</button>
								<a id="url" href="#"><div class="btn btn-info c02 b23" style="width: 75px; height: 30px;"onclick="downloadPic()">导出</div></a>
						</form>
						<form enctype="multipart/form-data" style="float:left;margin-left:30px;adding-right:10px; " class="form-inline" role="form" id="importForm4" action="<%=basePath %>uploadShitu.php"	method="post" >
								<div class="form-group" >
								<label for="" class="clabel">上传:</label>
									<input class="form-control" type="file" id="file4" name="file4" value="123"
										style="opacity: 0; filter: alpha(opacity = 0); width: 98%; padding-top: 12px; cursor: pointer; position: absolute;"
										onchange="setFilePath4();" /> 
									<input class="form-control" id="filePath4" name="filePath4" type="text" placeholder="可拖拽图片到此处">
							    </div>
						
							<input type="button" value="以图找图"  class="btn btn-info c02 b23"
								style="width: 75px; height: 30px;" onclick="addEvidence3()" />
						</form>
						
					</div>
				</div>
			</div>
			
				<div class="modal right fade" id="myModal" tabindex="-1" 
				style="width: 100%; height: 100%; margin: auto;">
				<div class="panel panel-default"
					style="width: 30%; height: 80%; margin: 3% auto;">
					<div class="panel-heading">更改案件</div>
					<div class="panel-body" style="width: 100%; height: 100%;">
						<div class="panel-body" style="width: 100%">
							<input id="caseinfo" name="caseinfo" class="form-control"
								placeholder="搜索案件名称/案件编号" type="text"
								style="float: left; width: 75%;"  onkeyup="showcase()"/>
							<button type='button' class="btn btn-info"
								style="width: 70px; margin-left: 10px; height: 34px"
								onclick="showcase()">搜索</button>
						</div>

						<div class="panel-body" style="height: 60%; overflow-y: scroll;">
							<table id="datatable" class="table table-striped table-hover br004">
								<tbody id="tbcont">
									<tr>
										<td class="td_left"><input type="checkbox" id="ckall"
											onclick="selectAll()" /></td>
										<td class="td_left c12" style="font-weight:600;font-size:14px;">案件编号</td>
										<td class="td_right c12" style="font-weight:600;font-size:14px;">案件名称</td>
									</tr>
								</tbody>
							</table>
						</div>
						 <button type='button' class="btn btn-info" data-backdrop='static' data-dismiss="modal"
							style="width: 70px; height: 34px; margin-left: 40%; margin-top: 2%"
							onclick="checkCase();">选择</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">搜索结果</div>
						<div class="wrap" style="padding-left:10px;overflow:hidden;">
							<!-- 瀑布流布局 begin-->  
						  	<div class="wflBox" id="wfl_block" style="overflow:hidden;">
									
						    	   
						      
							</div>  <!-- 瀑布流布局  end-->  
						</div> 
	
	<div class="round_shade_box2" id="zoom2" style="position: absolute; top: 50px; left: 15%; width: 70%; display: none;">
	 <!-- <div style="width:1000px;height:600px;position: fixed;display: table-cell;vertical-align:middle;text-align:center;z-index: 10;"> -->
	 <a style="position:static;*position:absolute;top:50%;">
	  <img id="pic_one2" src="" style="position:fixed;max-width:1000px;max-height:600px;top:30%;left:50%;z-index: 10"onclick="setDisplay()">	
	 </a>	 
	 <!-- </div>	 -->				
	</div>
	<div style="margin:0 auto;text-align:center;margin-bottom:30px;margin-top:10px;">
		<button id="addImg" type="button" class="btn btn-info" style="width:200px;height:40px;display:none;"  onclick="showPicture(0)"> 加载更多&gt;&gt;</button>
	</div> 
					 
					<div class="alcenter" style="font-size: 14px;display:none">
						<div id="total_d5" class="pagecount inline" style="height: 29px;">
							<span id="tot1_d5"></span>
						</div>
						<div class="pagebar inline" style="position: absolute; right: 125px; bottom: 30px; height: 29px;">
							<ul class="pagination pagination-sm" style="margin: 0;">
								<li id = "pages1_d5"></li>
										
								<li id = "pages_d5"></li>
					            		
								<li id = "pages2_d5"></li>
							</ul>
						</div>
						<div style="position: absolute; right: 9px; bottom: 30px;">
								<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages" name="givePages" onkeydown="onKeyDown(event)"/>
								<button type="button" class="btn" onclick="showPicture(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
						</div>
					</div>
					</div>
				</div>
			</div>
		</div>
	
 	<!-- 弹出取消收藏框 -->
	<div class="modal fade" id="cancelFavorite" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" >取消收藏</h4>
				</div>
				<div style="margin-left: 3%;margin-top: 3%;">
					<h5 class="modal-title" >确定取消该图片收藏吗？</h5>
				</div>
				<div class="modal-footer">
					<span id="cancelPicId" style="display:none"></span>
					<span id="cancelPicFlag" style="display:none"></span>
					<span id="cancelPicNumber" style="display:none"></span>
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="cancelFavorite()">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<!-- 弹出框       选择标签 class="modal right fade"  tabindex="-1"  -->
	<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" id="myModalfavorite_img" style="width: 100%; height: 100%; margin: auto;">
				<div class="panel panel-default"
					style="width: 50%; height: 80%; margin: 3% auto;">
					<div class="panel-heading">图片收藏 - 选择标签</div>
					
					<div class="panel-body" style="width: 100%; height: 100%;">
						<div class="panel-body" style="width: 100%">
						<label style="float:left;margin-top:7px;">新建标签：</label>
						<input id="favoLabelNameOfPic" name="favoLabelNameOfPic" class="form-control" placeholder="请输入..." type="text" style="float:left; width: 60%;" />
						<button type="button" class="btn btn-info" style="width: 70px; margin-left: 10px; height: 33px" onclick="addFavoLabelOfPic()">添加</button>
						</div>
						<div class="panel-body br18" style="padding: 0 0 0 20px;overflow-y:auto;height:62%;">
									<span id="tbcontOfDoc" > 
										
								    </span>
						</div>
						<span id="picId" style="display:none"></span>
						<span id="picFlag" style="display:none"></span>
						<span id="picNumber" style="display:none"></span>
						<!-- data-dismiss="modal" -->
						<button type='button' class="btn btn-info" style="width: 65px; height: 30px; margin-left: 39%; margin-top: 3%;"
								onclick="addFavorite();" >确定</button>
								 
						<button type='button' class="btn btn-info" style="width: 65px; height: 30px; margin-left: 8px;margin-top: 3%;"
								data-dismiss="modal">取消</button>
					</div>
				</div>
		</div>
		<!-- 收藏成功弹框 -->
		<div class="modal fade" id="favoritePic_success" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="delId" id="delId"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">图片收藏</h3>
					</div>
					<div class="modal-body">图片收藏成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="favoritePic_success" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" >成功</h4>
				</div>
				<div style="margin-left: 3%;margin-top: 3%;">
					<h5 class="modal-title" >收藏成功！</h5>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
				</div>
			</div>
		</div>
		</div>
	
		<!-- 提示请选择标签弹框 -->
		<div class="modal fade" id="pleaseChooseLabel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="delId" id="delId"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c12" id="myModalLabel" style="border-left: none;">选择标签</h3>
					</div>
					<div class="modal-body">请选择标签类型！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div> 
		
			<div id="loadingDiv" aria-hidden="false" style="display: none;" class="bootbox modal fade in" tabindex="-1" role="dialog">
            	<div class="modal-dialog" style="width: 182px;margin-top: 23%;">
            		<div class="modal-content">
            			<div class="modal-body">
            				<div class="bootbox-body">
            					<p class="msg">匹配中，请稍后...</p>
                    			<div><img id="flashAdImg" src="<%=basePath %>template/img/loading.gif" alt="loading" height="16" width="115"></div>
            				</div>
            			</div>
            		</div>
            	</div>
       	</div>
		<jsp:include page="footer2.jsp"></jsp:include>
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
<script src="<%=basePath%>template/js/cutover/js.js"></script>
<script type="text/javascript" src="<%=basePath%>template/js/jquery-1.2.6.pack.js"></script>
<script src="<%=basePath%>template/js/jquery.form.js"></script>
<script src="<%=basePath%>template/js/script_left.js"></script>
<script src="<%=basePath%>template/js/cutover/echarts.js"></script>
<script src="<%=basePath%>template/js/cutover/dataTool.min.js"></script>
<script type="text/javascript">
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
	function setFilePath4(){
		var filepath = $("#file4").val();
		$("#filePath4").val(filepath);
	}
	var options2;
	var bei=[];
	var imgi=0;
	$(function() {
			options2 = {
		 	success: function (data) {
		 		bei=[];
		 		var urls = data.substring(60,data.length-7).split(",");
				var sizes=10;
				var pageno=1;
				var pagesum=0;
				if(data.length!=67){
					pagesum=urls.length;
				}
				var pagenum = pagesum / sizes;
				var length=10;  //要显示的分页页数
					
				if(pagenum%1!=0){
						pagenum=pagenum+(1-pagenum%1);
				}
				$("#pages_d5").empty();
				//用于删除之前显示的页数，动态添加时id名均设为order
				for(var i=1;i<=length;i++)
					  $("#orderA_5").remove();
				if(pagesum<sizes){
					var html2 = '<li class="active" id="orderA_5"><a href="#" onclick="showPicture(1)">1</a></li >';
					$("#pages_d5").after(html2);	
				}else{
				if(pageno<pagenum){
					if(pageno+length-1<=pagenum){
						var html2="";
						if(pageno-2>0){
							for(var i =pageno-2;i<=pageno+length-1-2;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
									else
						
										html2 += '<li id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';  
						   				   }
							}
						else{
							for(var i =1;i<=length;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
									else
						
										html2 += '<li id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';  
						   				   }
						}
					$("#pages_d5").after(html2);
					}/* if */
					else{
						var html2="";
						if(pagenum>=length){
						for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){	
							 if(i==pageno)
								html2 += '<li class="active" id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
							else
								html2 += '<li id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >'; 
						   }
						$("#pages_d5").after(html2);	
						}
						else{
							for(var i =1;i<=pagenum;i++){	
								 if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
								else
									html2 += '<li id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
							   }
							$("#pages_d5").after(html2);	
						}
					}
				}
				else{
					if(pageno==pagenum){
						var html2="";
						for(var i =pageno-length+1>0?pageno-length+1:1;i<=pagenum;i++){	
						if(i==pageno)
							html2 += '<li class="active" id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
						else
							html2 += '<li id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
					   }
						$("#pages_d5").after(html2);
					}/* if */
				}
			}
				$("#tot1_d5").empty();
				$("#pages1_d5").empty();
				$("#pages2_d5").empty();
				var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
				$("#tot1_d5").append(html3);
				
				var html5 = '<a href="#" onclick="showPicture('+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
				$("#pages1_d5").append(html5);
				
				var html4 = '<a href="#" onclick="showPicture('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
				$("#pages2_d5").append(html4);
				
		 		$("#wfl_block").empty();
		 		$("#loadingDiv").hide();
		 		if(data.length<=67){
		 			$("#wfl_block").append("未找到相关图片!");	
		 		}else{
		 			
		 			$.each(urls,function(i,item){
						var html =   '<div class="small_pic">'+
					                	'<a href="#pic_one2"><img  src="/picture/'+item.substring(14,data.length-2)+'" style="width: 284px;height: 198px; " onclick="showImg('+i+')"/></a><br/>'+item.split("/")[item.split("/").length-1]+'<span id="pictureSrc'+i+'" style=" display:none;">'+item.substring(14,data.length-2)+'</span>'+
						            '</div>';
						$("#wfl_block").append(html);	
					});  
		 		}
				 
				}
			};
		});	
	function addEvidence3(){	
				if($("#filePath4").val()==''){
					alert("请选择文件！");
					//return false;
				}else{
					$("#importForm4").ajaxForm(options2);
					var casenum1= $("#case-num1").text();
					$("#importForm4").submit();
					$("#loadingDiv").show();
				}
			}
 function setDisplay(){
	 var zoomdisplay = document.getElementById("zoom2");
	 zoomdisplay.style.display = "none";
 } 

 /* function setDisplay1(){
	 $("#zoom2").css("display","none");
 }; */
 var imgL = "";
 
 function showImg(i){
	 $.ajax({
			type : "POST",
			url : "<%=basePath%>getImgAction.php",
			data : {
			},
			dataType : "json",
			async: true,
			success : function(data) {
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	 var zoomdisplay = document.getElementById("zoom2");
	 zoomdisplay.style.display = "block";
     var imgRotate2 = document.getElementById("pic_one2");
     
     var src1 = document.getElementById("pictureSrc"+i+"").innerText;
     var sudSrc1 = src1.split("emaildata")[1];
     imgRotate2.src="/picture"+sudSrc1;
     if(imgL!=""){
    	 imgRotate2.style.left = imgL;
     }     
     var imgW = getCss(imgRotate2,"width");
     if(imgL==""){
    	 imgL = getCss(imgRotate2,"left");
     }
     
     var finalLeft = imgL.split("px")[0]/1 - imgW.split("px")[0]/2;
    
     imgRotate2.style.left = finalLeft + "px";
	    
 }
 
 function getCss(o,key){
		return o.currentStyle? o.currentStyle[key] : document.defaultView.getComputedStyle(o,false)[key]; 	
 };
 
 var caseidStr="";
	//从es上查询图片显示在页面上
	window.onload = showPicture(1);
	var dl=1;
	var beipage=1;//页数
	//显示在表格文档显示
	function showPicture(pageno){
		//pageno=1代表 是点击搜索按钮 
		if(pageno==1){
			beipage=1;
			$("#addImg").css("display","inline-block");
		}
		if(beipage>0){
		}else{
			beipage=1;
		}		
		bei=[];
		var dirName =$("#dirName").val();
		var givePages = $("#givePages").val();
		if(givePages!=""){
			pageno = parseInt(givePages);
		}
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/showPictureInfo.php",
			data : {
				"pageIndex":beipage,
				"dirName":dirName,
				"caseidStr":caseidStr
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
				$("#pages_d5").empty();
				//用于删除之前显示的页数，动态添加时id名均设为order
				for(var i=1;i<=length;i++)
					  $("#orderA_5").remove();
				if(pagesum<sizes){
					var html2 = '<li class="active" id="orderA_5"><a href="#" onclick="showPicture(1)">1</a></li >';
					$("#pages_d5").after(html2);	
				}else{
				if(pageno<pagenum){
					if(pageno+length-1<=pagenum){
						var html2="";
						if(pageno-2>0){
							for(var i =pageno-2;i<=pageno+length-1-2;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
									else
						
									html2 += '<li id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';  
						   	}
						}
						else{
							for(var i =1;i<=length;i++){	
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
									else
						
										html2 += '<li id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';  
						   				   }
						}
					$("#pages_d5").after(html2);
					}/* if */
					else{
						var html2="";
						if(pagenum>=length){
						for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){	
							 if(i==pageno)
								html2 += '<li class="active" id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
							else
								html2 += '<li id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >'; 
						   }
						$("#pages_d5").after(html2);	
						}
						else{
							for(var i =1;i<=pagenum;i++){	
								 if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
								else
									html2 += '<li id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
							   }
							$("#pages_d5").after(html2);	
						}
					}
				}
				else{
					if(pageno==pagenum){
						var html2="";
						for(var i =pageno-length+1>0?pageno-length+1:1;i<=pagenum;i++){	
						if(i==pageno)
							html2 += '<li class="active" id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
						else
							html2 += '<li id="orderA_5"><a href="#" onclick="showPicture('+i+')">'+i+'</a></li >';
					   }
						$("#pages_d5").after(html2);
					}/* if */
				}
			}
				$("#tot1_d5").empty();
				$("#pages1_d5").empty();
				$("#pages2_d5").empty();
				var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
				$("#tot1_d5").append(html3);
				
				var html5 = '<a href="#" onclick="showPicture('+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
				$("#pages1_d5").append(html5);
				
				var html4 = '<a href="#" onclick="showPicture('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
				$("#pages2_d5").append(html4);
				if(pageno==1){
					$("#PictureInfoList").empty();
				}
				var imgs=data.hits.hits;
				  if(imgs.length==0){
					  $("#wfl_block").empty();
				  }
				  $.each(imgs,function(i,item){
					 //进页面判断星标是否为1   页面刷新后已收藏的图片还是显示亮的星星
					 var pictureStarFlag=item._source.starFlag;
					 var src="<%=basePath%>"+"template/img/star2.png";
					 if(pictureStarFlag=='1'){
						src="<%=basePath%>"+"template/img/star.png";
					 }	
					 var img = item._source.picDirpath;
					 var img2 = img.split("emaildata")[1];
					/*  var picNam = img2.split("/")[3];
					 if(picNam.length>36){
						 picNam =picNam.substring(picNam.length-36,picNam.length)
					 } */
					 var html =   '<div class="small_pic js-wfl-elem"><label class="AddCheckbox"><input name="picture" type="checkbox" value="'+item._source.picDirpath+'" /></label>'+
									'<a class="addHov br17" href="#pic_one2" style="display:inline-block;position:relative;height:auto;width:100%;line-height:198px;"><img style="position: absolute;left:0" src='+src+' onclick="upImgStar('+i+');" id="picStar'+i+'" style="position: absolute;" data-target="#myModalfavorite_img" data-toggle="modal"/><img onload="loadcomplete()" src="/picture/'+img2+'" style="height: auto;width:100%; " onclick="showImg('+i+')"/>'+
									'<span class="addFot">'+item._source.picname +'</span>'+'</a>'+'<span id="pictureSrc'+i+'" style=" display:none;">'+item._source.picDirpath+'</span>'+
				                	'<span id="pictureStarFlag'+i+'" style=" display:none;">'+item._source.starFlag+'</span>'+
				                	'<span id="pictureId'+i+'" style=" display:none;">'+item._id+'</span>'+ 
				                	
					            '</div>';
					            //var bei1={"imgurl":src,"cont":item._source.picname, "href":"#pic_one2","imgsurl":"/picture"+img2, "inputva":item._source.picDirpath,"size":item._source.picDirpath , "des":item._source.starFlag,"else":item._id,"value":item._id,"sid1":"pictureSrc"+i,"sid2":"pictureStarFlag"+i,"sid3":"pictureId"+i,"sid4":"pictureFavoritePerson"+i,"sid5":"pictureCaseName"+i,"imgid":"picStar"+i,"click":"upImgStar('+i+')","onclick":"showImg("+i+")"};
								
					            var bei1={
							      "idi":imgi, 
								  "starpath":src, 
								  "picpath":"/picture"+img2, 
								  "picId":item._source.picDirpath, 
								  "picNam":item._source.picname, 
								  "picDirpath":item._source.picDirpath, 
								  "starFlag":item._source.starFlag, 
								  "pictureId":item._id
								   };
					            
					            var imgurl="/picture"+img2;
							    var xmlHttp ;
								if (window.ActiveXObject)
								 {
								  xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
								 }
								 else if (window.XMLHttpRequest)
								 {
								  xmlHttp = new XMLHttpRequest();
								 } 
								xmlHttp.open("Get",imgurl,false);
								xmlHttp.send();
								if(xmlHttp.status==404){
								}								
								else{
									bei.push(bei1);
									imgi++;
								}

					          
				$("#PictureInfoList").append(html);	
				});
				$("#givePages").val("");
				 //加载瀑布流
				 //alert("beipage="+beipage+"   pagenum="+pagenum);
				if(beipage<=pagenum){ 
					if(pageno==0){						
						if(beipage==1){
						   $("#wfl_block").empty();
						   _wfl.initData();
						}
						_wfl.createHtml(bei);
						beipage=beipage+1;
					}else{					
						$("#wfl_block").empty();
						_wfl.wfl_times=0;
						_wfl.cacuData =[];
						_wfl.initData();
						_wfl.createHtml(bei);
						beipage=beipage+1;
					}
				} 
				if(beipage > pagenum){
					$("#addImg").css("display","none");
				}
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("失败");
			}
		});
		
	}
	//选择选择案件下的所有复选框
	function selectCase(idi){
		//alert(idi);
 		var casesus="";
		var ckall=document.getElementById("caselist"+idi);
		 var ids_call=document.getElementsByName("ids_call"+idi);
	    for(var i=0;i<ids_call.length;i++){
	    	ids_call[i].checked=ckall.checked;
	    	 var sus1 = ids_call[i].value
	    	 if(ckall.checked){
	    		if(suspectPhoneStr==""){
	            	suspectPhoneStr=sus1;
	            }else{
	            	suspectPhoneStr=suspectPhoneStr+","+sus1;
	            }
	    		casesus=casesus+","+sus1;
	    	}else{
	    		var suspectPhoneStrSplit=suspectPhoneStr.split(",");
				var suspectStrNew="";
				if(suspectPhoneStrSplit.length>0){
					for(var j=0;j<suspectPhoneStrSplit.length;j++){
						var par = suspectPhoneStrSplit[j];
						if(par!=sus1){
							if(suspectStrNew==""){
								suspectStrNew=par;
							}else{
								suspectStrNew=suspectStrNew+","+par;
							}
						}
					}
				}
				suspectPhoneStr=suspectStrNew;
	    	} 
	    }
	    
		var caseInfo = document.getElementById("caselist"+idi);
		 var caseId=caseInfo.value.split(" ")[1];
		 var caseName=caseInfo.value.split(" ")[0];
		 if(caseInfo.checked){
			 if(caseidStr==""){
           	caseidStr=caseId;
           }else{
           	caseidStr=caseidStr+" "+caseId;
           } 
			if(caseName.length>6){
				caseName = caseName.substring(0,6) + "...";
			}
			var html ='<span class="br05 b12 span-1" id="shanchu2'+caseId+'" style="width: 150px; height: 30px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;margin-right: 15px;margin-top: 5px;" onclick="deletemainParty(this.id,'+caseId+')">'+caseName+
			'<img style="vertical-align:middle;" src="<%=basePath%>template/img/x.png" /></span><span id="suspectnum'+caseId+'" style=" display:none;">'+casesus+'</span>';
			$("#spans").append(html);	 
		 }else{
			 $("#shanchu2"+caseId+"").remove();
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
		 }
 		
	}
	//点击弹出收藏或者取消框
	function upImgStar(i){
		
		var pictureId = document.getElementById("pictureId"+i+"").innerText;
		var pictureStarFlag = document.getElementById("pictureStarFlag"+i+"").innerText;
		
		if(pictureStarFlag=='1'){
			$(("#"+'picStar'+i)).attr('data-target',"#cancelFavorite");
			//取消收藏
			$('#cancelPicId').val(pictureId);
			$('#cancelPicNumber').val(i);
			$('#cancelPicFlag').val(pictureStarFlag);
		}else {
			showAllLabelOfDoc();
			//进行收藏
			$(("#"+'picStar'+i)).attr('data-target',"#myModalfavorite_img");
			$('#picId').val(pictureId);
			$('#picNumber').val(i);
			$('#picFlag').val('0');
			$('#myModalfavorite_img').modal('hide');
		} 
	}
	//取消收藏
	function cancelFavorite(){
		var esid=$('#cancelPicId').val();
		var i=$('#cancelPicNumber').val();
		var flag=$('#cancelPicFlag').val();
		doAddOrCancelFavorite(esid,flag,i,'');
	}
	//执行收藏
	function addFavorite(){
		var values=[];
		$("input[name='ids']:checked").each(function(){
			values.push($(this).val());
		});
		if(values.length==0){
//			alert('请先选中类别');
			$('#pleaseChooseLabel').modal('show');
		}else{
			var esid=$('#picId').val();
			var i=$('#picNumber').val();
			var flag=$('#picFlag').val();
			doAddOrCancelFavorite(esid,flag,i,values.toString());
			//alert(hide);
			
		}
	}
	//取消或者收藏
	function doAddOrCancelFavorite(esid,flag,i,values){
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/favoImg.php",
			data : {
				'esid':esid,
				'flag':flag,
				'picLabel':values
			},
			dataType : "json",
			async: true,
			success : function(data) {
				//进行收藏
				if(flag==0){
					var src="<%=basePath%>"+"template/img/star.png";
					$(("#"+'picStar'+i)).attr('src',src);
					document.getElementById("pictureStarFlag"+i+"").innerText=1;
//					alert("收藏成功！");
					$('#favoritePic_success').modal('show');
					
				}
				//取消收藏
				if(flag==1){
					var src="<%=basePath%>"+"template/img/star2.png";
					$(("#"+'picStar'+i)).attr('src',src);
					document.getElementById("pictureStarFlag"+i+"").innerText=0;
				}
				//关闭对话框
//				$('#myModalfavorite').modal('toggle');
				//清空复选框
			   /*  var ck=document.getElementsByName("ids");
			    for(var i=0;i<ck.length;i++){
			    	ck[i].checked=false;
			    } */
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert('请求收藏失败');
			}
		})
	}

	window.onload = showAllLabelOfDoc();
	//查询所有标签
	function showAllLabelOfDoc(){
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/showAllLabelOfDoc.php",
			data : {
//				"casenumorname":casenumorname
			},
			dataType : "json",
			async: true,
			success : function(data) {
				$("#tbcontOfDoc").empty();
				for(var i = data.length - 1; i >= 0;i--){  
					if(i % 7 == 1) {
						var html = '<span>'+
							'<span class="td_left"><input id="caselist'+i+'" type="checkbox"  name="ids" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br05 b12 span-1" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 2) {
						var html = '<span>'+
							'<span class="td_left"><input id="caselist'+i+'" type="checkbox"  name="ids" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="b13 br06 span-2" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 3) {
						var html = '<span>'+
							'<span class="td_left"><input id="caselist'+i+'" type="checkbox"  name="ids" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br11 b11 span-3" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 4) {
						var html = '<span>'+
							'<span class="td_left"><input id="caselist'+i+'" type="checkbox"  name="ids" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br07 b14 span-4" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 5) {
						var html = '<span>'+
							'<span class="td_left"><input id="caselist'+i+'" type="checkbox"  name="ids" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br08 b15 span-5" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 6) {
						var html = '<span>'+
							'<span class="td_left"><input id="caselist'+i+'" type="checkbox"  name="ids" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br09 b17 span-6" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 7) {
						var html = '<span>'+
							'<span class="td_left"><input id="caselist'+i+'" type="checkbox"  name="ids" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br10  b18 span-7" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 0) {
						var html = '<span>'+
							'<span class="td_left"><input id="caselist'+i+'" type="checkbox"  name="ids" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br11 b11 span-3" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						$("#tbcontOfDoc").append(html);	
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
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
	
	function showcase(){
		var casenumorname = document.getElementById("caseinfo").value;
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/getCaseOfEmail.php",
			data : {
				"casenumorname":casenumorname
			},
			dataType : "json",
			async: true,
			success : function(data) {
				$("#tbcont").empty();
				var htmlhead='<tr>'+
					'<td class="td_left"><input type="checkbox" id="ckall" onclick="selectAll()"/></td>'+
					'<td class="td_left c12" style="font-weight:600;font-size:14px;">案件编号</td>'+
					'<td class="td_right c12" style="font-weight:600;font-size:14px;">案件名称</td>'+
				'</tr>';
				$("#tbcont").append(htmlhead);	
				var caseidStrSplit=caseidStr.split(" ");
				var aaaa="";
				var html="";
				$.each(data,function(i,item){
					 	var caseflag=0;
						for(var j=0;j<caseidStrSplit.length;j++){
							if(caseidStrSplit[j]==item.id){
								caseflag=1;
							}
						}
						if(caseflag==1){
							 html+= '<tr>'+
							'<td class="td_left"><input id="caselist'+i+'" type="checkbox" checked="checked" name="idss" value="'+item.caseName+' '+item.id+'" onclick="chooseCase(this.id);" />'+
								'<td class="td_left">'+item.caseNum+'</td>'+
								'<td class="td_right" id="">'+item.caseName+'</td>'+
							'</tr>';
						} else{  
							 html+= '<tr>'+
							'<td class="td_left"><input id="caselist'+i+'" type="checkbox"  name="idss" value="'+item.caseName+' '+item.id+'" onclick="chooseCase(this.id);" />'+
								'<td class="td_left">'+item.caseNum+'</td>'+
								'<td class="td_right" id="">'+item.caseName+'</td>'+
							'</tr>';
						}
				});
				$("#tbcont").append(html);	
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	} 
	
  	//选择所有复选框  	
	function selectAll(){
		var ckall=document.getElementById("ckall");
	    var ck=document.getElementsByName("idss");
	    for(var i=0;i<ck.length;i++){
	    	if(ck[i].checked==false){
	    	ck[i].checked=ckall.checked;
	    	selectCase(i); 
	    	}
		}
	   if($("input[name='idss']:checked").length <= $("input[name='idss']").length && ckall.checked==false){
	    	for(var j=0;j<ck.length;j++){   		
	    		if(ck[j].checked==true){
	    		ck[j].checked=false;
	    		selectCase(j);
	    	}
		 }
    }
	    
}
	function showChooses(id){
		 var ckall=document.getElementById(id);
	     ck[i].checked=ckall.checked;
	} 
	//选择某个复选框案件将案件信息展示在页面上
		function chooseCase(id){
		 var caseInfo = document.getElementById(id);
		 var caseId=caseInfo.value.split(" ")[1];
		 var caseName=caseInfo.value.split(" ")[0];
		 if(caseInfo.checked){
			 if(caseidStr==""){
            	caseidStr=caseId;
            }else{
            	caseidStr=caseidStr+" "+caseId;
            } 
			if(caseName.length>6){
				caseName = caseName.substring(0,6) + "...";
			}
			var html ='<span class="br05 b12 span-1" id="shanchu2'+caseId+'" style="width: 150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; height: 30px;margin-right: 15px;margin-top: 5px;" onclick="deletemainParty(this.id,'+caseId+')">'+caseName+
			'<img style="vertical-align:middle;" src="<%=basePath%>template/img/x.png" /></span>';
			$("#spans").append(html);	 
		 }else{
			 $("#shanchu2"+caseId+"").remove();
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
		 }
	}
	function checkCase(){
		$("#caseinfo").val(""); //设置 为空
	    showPicture(1);
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
		
		showPicture(1)
	}
	
	
	//弹出框    新建标签
	function addFavoLabelOfPic() {
		var favoLabelName = $("#favoLabelNameOfPic").val();
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/addFavoLabel.php",
			data : {
				'favoLabelName':favoLabelName
			},
			dataType : "json",
			async: true,
			success : function(data) {
				showAllLabelOfDoc();
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert('新建标签失败');
			}
		})
	}
//图片导出
function downloadPic() {
	var checkboxs = document.getElementsByName("picture");
	var i = 0;
	var count = 0;
	var len = checkboxs.length;
	var urlstr = "";
	for(i = 0; i < len; i++){
		if(checkboxs[i].checked){	
			if(count == 0){
				urlstr += checkboxs[i].value;
			}else{
				urlstr += ","+checkboxs[i].value;
			}
			count = count + 1;
		}
	}
	if(urlstr=="") {
        alert("请先选择导出图片");
    } else {
        var rs = confirm("确定导出?");
        if (rs) {
        	$("#url").attr("href", "<%=basePath %>admin/downloadPicture.php?picturePath=" + urlstr);
        } else {
            $("#url").attr("href", "javascript:void(0)");
        }
    }
}
//回车搜索事件
function onKeyDown(event){
   var e = event || window.event || arguments.callee.caller.arguments[0];
   if(e && e.keyCode==13){ // enter 键
	   showPicture(1);
   }
}




/**
*
*
*
*
*Waterfall flow layout 
*/
var _wfl = {
    //配置参数
   
    $iBlock : $("#wfl_block"),//瀑布流包裹层 jq对象
    wfl_all_width: 0, //画布总宽
    wfl_count:5, //列数
     wfl_times: 0,
    wfl_padding: 0,//内容之间的间隔 纵向和横向<img onload="loadcomplete()" src="%imgurl%" >'   <img onload="loadcomplete()"  onclick="showImg()" src="%imgurl%" />'
    wfl_fomat:'<div id="%id%" class="js-wfl-elem small_pic absolute" >'
   				+'<label class="AddCheckbox">'
			    +'<input name="picture" type="checkbox" value="%picId%" />'
			    +'</label>'
			    +'<a href="#pic_one2" class="addHov br17" style="display:inline-block;position:relative;height:auto;line-height:198px;">'
			+'<img style="position: absolute;left:0" src="%starpath%" onclick="upImgStar(%idi%);" id="picStar%idi%" data-target="#myModalfavorite_img" data-toggle="modal"/>'
			+'<img onload="loadcomplete()" src="%picpath%" style=" height: auto;width:100%;" onclick="showImg(%idi%)"/>'
			+'<span class="addFot">%picNam%</span>'
			+'</a>'
			+'<span id="pictureSrc%idi%" style=" display:none;">%picDirpath%</span>'
			+'<span id="pictureStarFlag%idi%" style=" display:none;">%starFlag%</span>'
			+'<span id="pictureId%idi%" style=" display:none;">%pictureId%</span>'
			+'</div>',
/*  原来的   	
		     wfl_fomat:'<div id="%id%" class="js-wfl-elem small_pic absolute" style="float:left;">'
		         +' <a href="%href%" class="addHov" style="display:inline-block;position:relative;height:auto;width:100%;border:1px solid #eee;line-height:198px;">'
		         +'    <input type="checkbox" id="%inputid%" name="favoPic_check" value="%value%" style="position: absolute;right:5px;">'
		         +'    <img src="%imgurl%" style="position: absolute;left:0" onclick="%click%" id="%imgid%" data-target="#myPicModalfavorite" data-toggle="modal">'
		         +'    <img src="%imgsurl%" style=" height: auto;width:100%;" onclick="%onclick%">'
		         +'    <span class="addFot">%cont%</span>'
		         +' </a>'
		         +' <span id="%sid1%" style=" display:none;">%title%</span>'
		         +' <span id="%sid2%" style=" display:none;">%logoname%</span>'
		         +' <span id="%sid3%" style=" display:none;">%size%<</span>'
		         +' <span id="%sid4%" style=" display:none;">%des%</span>'
		         +' <span id="%sid5%" style=" display:none;">%else%</span>'
		         +'</div>', */

    wfl_width:0,  //单内容块的宽度
    
    
    cacuData :[],//计算位置的数据记录

       
    idFirstName: "wfl",
    lastId : 0, //动态加载的ID
  
    //获取最初内容对应列上的总高度
    initData: function (data){
      _wfl.$iBlock = $("#wfl_block");// ！ 临时解决加载不出的问题
      _wfl.wfl_all_width =  _wfl.$iBlock.width();
      _wfl.wfl_width =(_wfl.wfl_all_width - ( _wfl.wfl_padding * (_wfl.wfl_count-1) )) / _wfl.wfl_count;  //单内容块的宽度
      var limit = _wfl.wfl_count;
      var i = 0;
      
      if (_wfl.$iBlock.find(".js-wfl-elem ").length == 0){
         
          for(var i =0 ; i< _wfl.wfl_count; i++){
              _wfl.cacuData[_wfl.cacuData.length] = {"colum": "c"+i, "left": ( ( _wfl.wfl_width +  _wfl.wfl_padding ) *  i) , "height":0} ;   //保存单行个列的高度
               limit --
          }
        
      }else{
      
          $.each( _wfl.$iBlock.find(".js-wfl-elem "), function () {
            
            if (limit <= 0) {
              $(this).remove();//第一次加载最多仅限一行 也就是wfl_count的设置值，多余部分删除
              
            }else{        
            
              _wfl.cacuData[_wfl.cacuData.length] = {"colum": "c"+i, "left": ( ( _wfl.wfl_width +  _wfl.wfl_padding ) *  i) , "height": ($(this).height() + _wfl.wfl_padding)} ;   //保存单行个列的高度

            }
             i ++;
             limit --;
          });      
          
      }
      if(data){
         this.createHtml(data);
     }
     
    },
    
    
    sortListByName :function (list ,  cname ){     
      var i,j,k,temp,n = list.length;      
      var list = list;
      for( i = 0; i < n; i++){        
        for( j = 0; j < n; j++ ){
           
            if( list[i][cname] < list[j][cname]  ){
              temp = list[i];
              list[i] = list[j];
              list[j] = temp;   
            }
        }
      }     
    },
    
    
    
    /**
    * @method
    * @param  {array} list 二维数组 当前加载的单行列元素列表
    */
    setColSite : function (list) {
     
      var ilist = list;
      var oblist= [];
      var len = ilist.length > _wfl.wfl_count ? _wfl.wfl_count : ilist.length;
     
      for (var i = 0; i <  len; i++){     //从多个数据中取出限定的列个数进行比较   
         oblist[oblist.length] = ilist.shift();         
      }
      
      var objArr =[];
      for (var j = 0; j < oblist.length; j++) { //将取出的数据整理成有对应高度的数组进行排序
        objArr[objArr.length] = {"id":oblist[j], "height":$("#"+oblist[j]) .height()};        
        
      }
    
      this.sortListByName(_wfl.cacuData, "height");//高度排序
     
      this.sortListByName(objArr, "height");//高度排序  
      
      var k = len-1;
      var maxHeight = 0,hIndex,curElem;
      for( i = 0; i <  len; i++ ){
        curElem =  $("#" + objArr[i].id);
        curElem.css({left: _wfl.cacuData[k].left + "px", top: _wfl.cacuData[k].height + "px",opacity:1});   

       
        _wfl.cacuData[k].height += ( objArr[i].height + _wfl.wfl_padding );
         
        if ( _wfl.cacuData[k].height > maxHeight) {
          maxHeight = _wfl.cacuData[k].height;
          hIndex = k;
        }
        
        k--;
      }
     
      _wfl.$iBlock.css("height",  _wfl.cacuData[hIndex].height + "px")
      
      
      //规定一行判断一次
      if ( ilist.length > 0) {
        _wfl.setColSite( ilist );        
      }
    },
    
    
    
     createHtml : function (jsonList){
       
       var beginId = _wfl.lastId + 1; //建立ID名       
       var loadLen = jsonList.length;
       var idlist = [];
       
       var temStr = "";
       

      // if(loadLen == _wfl.wfl_count){
       
             for(var i = 0; i < loadLen; i++ ){
               jsonList[i]["id"] = _wfl.idFirstName +"_"+beginId;         
               idlist.push(_wfl.idFirstName +"_"+beginId);
               beginId ++ ;
               temStr += _wfl.htmlFormat( _wfl.wfl_fomat, jsonList[i] );
             }
             
             _wfl.lastId = beginId;
             _wfl.wfl_times = loadLen;
           
             _wfl.$iBlock.append(temStr);    
            
              
              var myInterval = setInterval(function(){
                  if (_wfl.wfl_times == 0){
                      clearInterval(myInterval);
                      _wfl.setColSite( idlist );
                  } 
              },1)        
        //}else{
            
          
        //}

     },
     
     
     
      /**
     * 说明：新增字符串format方法
     * 用法. var resultStr = htmlFormat(拥有占位符的字符串,{key:value,key:value....key:value});
     * @methed strFormat
     * @param  {string} str  第一个是具有占位符的字符串，后面的都是加入到对应占位符的阐述
     * @param  {json} obj json格式对象
     * @return {string} 拼接完成的字符串
     */
      htmlFormat:function(str, obj){
        if(obj == undefined || obj == null) {
          return str;
        }			
        var s = str;			
        $.each(obj, function(i) {
          s = s.replace(new RegExp("\\%" + i + "\\%","g"), obj[i]);
        });   
        return s;
      }
     
}


function loadcomplete(){    
    _wfl.wfl_times --;
}
/* 测试数据 */
<%-- var demoJson = {"data":[
                		{
                	   "idi":1, 
                	  "starpath":"<%=basePath%>template/img/star.png", 
                	  "picpath":"/picture/2169/2169e802bede2a/3.jfif", 
                	  "picId":"11", 
                	  "picNam":"111", 
                	  "picDirpath":"/emaildata/2169/2169e802bede2a/3.jfif", 
                	  "starFlag":"1", 
                	  "favoritePerson":"favoritePerson", 
                	  "caseName":"caseName"
                	   },
                	   {
                	   "idi":2, 
                	 "starpath":"<%=basePath%>template/img/star.png",
                	  "picpath":"/picture/2169/2169e802bede2a/10.jpg", 
                	  "picId":"22", 
                	  "picNam":"222", 
                	  "picDirpath":"/emaildata/2169/2169e802bede2a/10.jpg", 
                	  "starFlag":"1", 
                	  "favoritePerson":"favoritePerson", 
                	  "caseName":"caseName"
                	   },
                	   {
                	   "idi":3, 
                	 "starpath":"<%=basePath%>template/img/star.png",
                	  "picpath":"/picture/2160/2160bfb4d06f47/timg.jpg", 
                	  "picId":"33", 
                	  "picNam":"333", 
                	  "picDirpath":"/emaildata/2160/2160bfb4d06f47/timg.jpg", 
                	  "starFlag":"1", 
                	  "favoritePerson":"favoritePerson", 
                	  "caseName":"caseName"
                	   },
                	   {
                	   "idi":4, 
                	 "starpath":"<%=basePath%>template/img/star.png",
                	  "picpath":"/picture/2169/2169e802bede2a/5.jpg", 
                	  "picId":"44", 
                	  "picNam":"444", 
                	  "picDirpath":"/emaildata/2169/2169e802bede2a/5.jpg", 
                	  "starFlag":"1", 
                	  "favoritePerson":"favoritePerson", 
                	  "caseName":"caseName"
                	   },
                	   {
                	   "idi":5, 
                	  "starpath":"<%=basePath%>template/img/star.png",
                	  "picpath":"/picture/2169/2169e802bede2a/1.jfif", 
                	  "picId":"55", 
                	  "picNam":"555", 
                	  "picDirpath":"/emaildata/2169/2169e802bede2a/1.jfif", 
                	  "starFlag":"1", 
                	  "favoritePerson":"favoritePerson", 
                	  "caseName":"caseName"
                	   }

                 ]}; --%> 
 
   //var demoJson = {"data":bei};


</script>
</body>
</html>