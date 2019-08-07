<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String onLineUserID = session.getAttribute("sUserID").toString();
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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/cloud-admin.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/workspace.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<script src="<%=basePath%>template/js/jquery.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui.min.js"></script>
<script src="<%=basePath%>template/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>template/js/demo.js"></script>
<!--离线地图加载  -->

<!--离线地图加载——end  -->
<script type="text/javascript" src="../template/baidu_map/baidumapv2/baidumap_offline_v2_load.js"></script>
<link rel="stylesheet" type="text/css" href="../template/baidu_map/baidumapv2/css/baidu_map_v2.css"/>
<!-- <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script> -->

<style>
.choosed{display:inline-block;vertical-align:middle;}
.choosed .infor{
	display:inline-block;
	height:33px;
	line-height:33px;
	background-color:#EBEFF1;
	border-radius:3px;
	border:1px solid #D8E1E3;
	cursor:default;
	position:relative;
	width:100px;
	margin-right:5px;
}
.choosed .infor span{
	position:absolute;
	top:0px;
	left:0px;
	width:80px;
	text-align:left;
	padding:0px 5px;
	height:33px;
	overflow:hidden;
	text-overflow:ellipsis;
	white-space: nowrap;	
}
.choosed .infor a{
	display:inline-block;
	width:20px;
	position:absolute;
	top:0px;
	right:0px;
}
.choosed .infor a img{
	margin-top:7.5px;	
	width:15px;
	height:15px;
}
.sx{
	display:inline-block;
	line-height:33px;
	vertical-align: top;
	cursor:default;
}
.mysheng{
	width: 350px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
/* 高级搜索样式start */
.myconts{overflow: hidden;}
    .addcloss{
        width:20px;
        height:20px;
        line-height: 19px;
        border-radius: 50%;
        font-size:20px;
        font-weight: normal;
        display:inline-block;
    }
    .contLeft,.contRight{
        width:472px;
        height:245px;
        box-sizing:border-box;
    }
    .contLeft{float:left;}
    .contRight{float:right;}
    .contLeft .tit,.contRight .tit{
        height:40px;
        width:100%;
        padding-left: 15px;
        font-size:14px;
    }
    .contLeft .tit h3,.contRight .tit h3{
        font-size: 14px;
        font-weight: normal;
        padding:0px;
        margin:0px;
        line-height: 40px;
    }
    ul{
        padding:0px;
        margin:0px;
        list-style: none;
    }
    .contLeft li{
        display: inline-block;
        height:40px;
        width:100%;
        line-height: 40px;
        cursor:default;
        padding-left: 15px;
    }
    .contLeft li img{margin-right:5px;}
    .contLeft li span{
        font-size:14px;
    }
    .contRight textarea{
        border:none;
        resize:none;
        width:470px;
        height:200px;
        line-height: 40px;
        padding:0px;
        margin:0px;
        padding-left: 20px;
        font-size:14px;
        outline:none
    }
    .mybtn{
        display: inline-block;
        width: 135px;
        height:30px;
        line-height: 30px;
        text-align: center;
        font-size: 12px;
        margin:20px 0 20px 410px;
        border-radius: 3px;
        cursor: pointer;
    }

    .panel-body {
        font-size: 12px;
        padding:0;
    }
tr{cursor:default;}
   .buts{
        height:30px;
        margin-top:20px;
        overflow: hidden;
    }
    .wish-checkbox {
      display: inline-block;
      width: 24px;
      vertical-align: middle;
    }
    .wish-checkbox a {
      display: inline-block;
      width: 100%;
      height: 24px;
      background: url("<%=basePath%>template/img/r (1).png") center center no-repeat;
      cursor: pointer;
      outline: none;
    }
    .wish-checkbox a.actived {
      background: url("<%=basePath%>template/img/r (2).png") center center no-repeat;
    }
    .elsebtns{
        float: right;
    }
    .elsebtns a{
        display:inline-block;
        width:75px;
        height:30px;
        line-height: 30px;
        text-align: center;
        font-size:12px;
        border-radius: 3px;
    }
    .edit{margin-right: 5px;}
    .ebtns{
        height:30px;
        margin:0 auto;
        text-align: center;
        margin-top:40px;
    }
    .ebtns a{
        display:inline-block;
        width:75px;
        height:30px;
        line-height: 30px;
        text-align: center;
        font-size:12px;
        border-radius: 3px;
        cursor:pointer;
    }
    .ebtns .clear{
        margin-right:20px;
    }
    .search{
        display:inline-block;
        width:75px;
        height:30px;
        line-height: 30px;
        text-align: center;
        font-size:12px;
        border-radius: 3px;
        cursor:pointer;
        vertical-align:top;
    }

    .formo span,.formt span,.forml span{
        height: 60px;
        line-height: 60px;
        font-size:14px;
    }
    .formo input,.formt input{
        display:inline-block;
        height: 30px;
        width: 360px;
        padding-left: 15px;
        border-radius: 2px;
        font-size: 12px;
        outline:none;
    }
    .forml input{
        display:inline-block;
        height: 30px;
        width: 290px;
        padding-left: 15px;
        border-radius: 2px;
        font-size: 12px;
        outline:none
    }
    .td_left,.td_right{font-size:14px;}

    input[type="checkbox"]{margin:0px;}
    .tall{margin-top:20px;margin-left:2px;}
    .tall input{
        vertical-align: middle;
        margin-right:5px;
        width: 15px;
        height: 15px;
    }
    label{font-weight: normal;margin-bottom:0px;font-size: 14px}
    .mysize{
        width: 440px;
        height: 30px;
        border-radius: 2px;
    }
    .date{margin-top: 15px;}
    .date input{
        display:inline-block;
        width:367px;
        position: relative;
    }
    .date span{
        font-size:14px;
    }
    .date img{
        width: 16px;
        height: 16px;
        position: absolute;
        right:8px;
        top:7px;
    }
/* 高级搜索样式end */


#table-modal_adduser .form-group, #table-modal .form-group {
	width: 50%;
	float: left;
}

#table-modal_adduser .form-group .control-label, #table-modal .form-group .control-label
	{
	padding-top: 7px;
	padding-right: 0px;
}

#table-modal_adduser .form-group .col-lg-8, #table-modal .form-group .col-lg-8
	{
	padding-left: 0px;
}

.yuan {
-moz-border-radius: 23px;
-webkit-border-radius: 23px;
border-radius: 23px;
height:23px;
width:23px;
text-align:center;
font-family:"微软雅黑";
font-size:10px;
line-height:23px;
display:inline-block;
}


.find_nav {
	position: relative;
	top: 0;
	z-index: 99;
	display: -moz-box;
	display: -webkit-box;
	display: box;
}

.find_nav_left {
	height: 46px;
	position: relative;
	overflow: hidden;
	-moz-box-flex: 1;
	-webkit-box-flex: 1;
	box-flex: 1;
}

.find_nav_list {
	position: absolute;
	/* */
	left: 0;
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
	display: block;
	width: 100%;
	height: 100%;
	line-height: 46px;
	font-size: 14px;
	text-align: center;
}



.sideline {
	display: block;
	position: absolute;
	border: 0;
	height: 2px;
	left: 0;
	top: 43px;
	pointer-events: none;
}

#vert{
                width: 100px;
                height: 20px;
                position: relative;
            }
            #vert ul li{
                float: left;
                position: absolute;
                bottom: 0px;
                background-color: salmon;
                text-align: center;
                font-weight: bold;
                width:100px;
                list-style: none;
            }

.ui-autocomplete{
	z-index:999999 !important;
	height:100px;
	overflow:scroll;
}
table tr td th{font-size:14px !important;}
.close_button{margin:0 auto;}

   .dialog,.dialog1{width: 380px;position: absolute;z-index: 9000;padding-bottom: 10px; display: none;-moz-user-select: none; -webkit-user-select: none;font-size:14px;}
    .dialog .dialog_head,.dialog1 .dialog_head{width: 100%;height:40px;text-align: center;line-height: 40px; cursor: move;}
    .dialog .dialog_content,.dialog1 dialog_content{width: 100%;min-height:300px;}
.mytits{
	height:30px;
	line-height: 30px;
	padding:0 10px;
	box-sizing:border-box;
	overflow:hidden;
}
.mytits h3{
	font-size:14px;
	font-weight:normal;
	display:inline-block;
}
.mytits span{
	font-size:12px;
	float:right;
}
.dialog ,.dialog1{ position:fixed; top:30px; left:150px; min-width:200px; min-height:200px; border:1px solid #666; background:#fff; display:none;}
.dialog .dialog_head,.dialog1 .dialog_head { background:#ddd; height:30px; min-width:200px; cursor:move; }
.dialog .close ,.dialog1 .close{position:absolute; right:5px; top:5px; font-weight:bold; }
.con { padding:20px; }
.bg_change_size { background:url('size2.png') no-repeat; }

#con li{cursor:default;}
#con li:hover{
	background-color:#f0f3f4;
	cursor:default;
}
#con{display:none}

/* 新增左侧栏样式 */
    .enterprise-aside {
  box-sizing: border-box;
  border: 1px solid #ccc;
  padding:0px;
  height:810px;
  overflow-y:scroll;
  overflow-x:hidden;
}
.enterprise-aside ul,
.enterprise-aside li {
  list-style: none;
}
.enterprise-aside .mytit {
  height: 35px;
  line-height: 35px;
  font-size: 14px;
  font-weight: bold;
  color: #475E9C;
  padding-left:10px;
  border-bottom: 1px solid #ccc;
  background: #f2f2f2;
}
.enterprise-aside .list-name {
  position: relative;
  height: 38px;
  line-height: 38px;
  font-size: 14px;
  font-weight: bold;
  color: #475E9C;
  border-bottom: 1px dotted #475E9C;
  cursor: pointer;
  -webkit-user-select: none;
  /* Safari 和 Chrome */
  -moz-user-select: none;
  /* Firefox */
  -ms-user-select: none;
  /* IE 9 */
  user-select: none;
  outline: none;
  padding-left:12px;
}
.enterprise-aside .list-name .arrow-r1,
.enterprise-aside .list-name .arrow-b1 {
  top: 15px;
  right: 10px;
}
.enterprise-aside ul {
  margin: 0px auto;
  width: 100%;
}
.enterprise-aside .nav {
  width: 100%;
}
.enterprise-aside .nav .next {
  display: block;
  width: 100%;
  font-size: 12px;
  text-indent: 0.8em;
  cursor: pointer;
}
.enterprise-aside .nav .mhide{display:none;}
.enterprise-aside .nav a:hover,
.enterprise-aside .nav .selected {
  font-weight: bold;
}
.aselected{background-color:#475E9C;color: #fff;}
.enterprise-aside .hide {
  display: none;
}
.arrow-r1,
.arrow-b1 {
  position: absolute;
  display: block;
  width: 7px;
  height: 7px;
}
.arrow-r1 {
  background: url(<%=basePath%>template/img/arrowR01.jpg) center center no-repeat;
}
.arrow-b1 {
  background: url(<%=basePath%>template/img/arrowB01.jpg) center center no-repeat;
}

.arrowR02 {
  background: url(<%=basePath%>template/img/arrowR02.jpg) center center no-repeat;
}
.arrowB02 {
  background: url(<%=basePath%>template/img/arrowB02.jpg) center center no-repeat;
}

.other p{
	cursor:default;
	position:relative;
	line-height:30px;
	height:30px;
	padding-left: 10px;
    white-space: nowrap;
    width: 200px;
    overflow: hidden;
    text-overflow: ellipsis;
}
.other .next a{
  display:block;
  line-height:30px;
  height:30px;
  font-size:14px;
  padding-left: 27px;
  position:relative;
}
.other .next a img{
	width:16px;
	height:16px;
	vertical-align:middle;
	margin-bottom:6px;
	margin-right:3px;
	
}
.arrowB02,
.arrowR02 {
  position: absolute;
  display: block;
  width: 7px;
  height: 7px;
  left: 0px;
  top: 11px;
}
.other input{
    position: absolute;
    right: 10px;
    top: 9px;
}
</style>
<link rel="stylesheet" href="<%=basePath%>template/css/style.css">
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">邮件挖掘</h1><span id="onLineUserID" style="display:none "><%=onLineUserID %></span>
	</div>
	<div style="overflow:hidden;padding-top:30px;padding-left:5px;background-color:#fff;">
	<!-- 案件列表  begin -->
    <div class="enterprise-aside col-md-2" style="background-color:#fff;">
        <p class="mytit">案件列表</p>
        <ul id ="tbcont">
        </ul>
    </div>
    <!-- 案件列表  end -->
	<div class="hbing col-md-10" style="background-color:#fff;">
		
	<!--选择案件 -->
	<!--搜索和选择案件 合并为一行-->
	<!-- <div class="row" style="margin-left: 1%;border:1px solid #ccc;padding-left:10px;">
		<div class="form-group" style="float: left;margin-bottom:0px;margin:5px 0;">
			<span class="alcenter" style="float: left;line-height:35px;">已选案件：</span>
			<div id="spans" style="margin-left: 75px;"></div>
		</div>
			<div style="margin-top:6px;">
				<button data-target="#myModal" data-toggle="modal" type='button' class="btn btn-info" style="width:120px;margin-right: 10px; float: right; margin-top: 1px;" onclick="showcase()" >选择案件</button>
			</div>
	</div> -->

	<div class="row find_nav br04 b24" style="width: 100% !important; margin-left: 1%;background-color:#fff; ">
		<div class="find_nav_left">
			<div class="find_nav_list">
				<ul>
					<li class="find_nav_cur "><a href="javascript:void(0)" onclick="d1()">电子邮箱工作台</a></li>
					<li><a class="c07" href="javascript:void(0)" onclick="d6()">时间线</a></li>
					<li><a class="c07" href="javascript:void(0)" onclick="d5()">嫌疑人关系图</a></li>
					<li><a class="c07" href="javascript:void(0)" onclick="d2()">收发件分析</a></li>
					<!-- <li><a class="c07" href="javascript:void(0)" onclick="d3()">邮箱关系图</a></li> -->
					<li><a class="c07" href="javascript:void(0)" onclick="d4()">域名分析</a></li>

					<li class="sideline b25"></li>
				</ul>
			</div>
		</div>
	</div>

	<!-- <div class="modal right fade" id="myModal" tabindex="-1" style="width: 100%; height: 100%; margin: auto;">
				<div class="panel panel-default" style="width: 35%; height: 80%; margin: 3% auto;">
					<div class="panel-heading">更改案件</div>
					<div class="panel-body" style="width: 100%; height: 100%;padding:20px;">
						<div class="panel-body" style="width: 100%;margin-top: 15px;">
							<input id="caseinfo" name="caseinfo" class="form-control" placeholder="搜索案件名称/案件编号" type="text"
								style="float: left; width: 75%;" onkeydown="onKeyDown_changeCase(event)"/>
							<button type='button' class="btn btn-info" style="width: 70px; margin-left: 10px; height: 32px"
								onclick="showcase()">搜 索</button>
						</div>

						<div class="panel-body" style="height: 60%; overflow-y: scroll;margin-top: 15px;padding-right:5px;">
							<table id="datatable" class="table table-striped table-hover br004" >
								<tbody id="tbcont" class="c20">
									<tr>
										<td class="td_left c07"></td>
										<td class="td_left c07 c20" style="font-weight:600;font-size:14px;">案件编号</td>
										<td class="td_right c08 c20" style="font-weight:600;font-size:14px;">案件名称</td>
									</tr>
								</tbody>
							</table>
						</div>
						<button type='button' class="btn btn-info" style="width: 70px; height: 32px; margin-left: 42%; margin-top: 6%"
							onclick="checkCase();">选择</button>
					</div>
				</div>
			</div> -->

	<div id="d1" style="margin-bottom:10px;padding:0 10px;">

				<div class="panel-body" style="padding-top: 0px;padding-bottom: 0px;">
					<div name="gp_btnm"  class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'手机号');"><img src="<%=basePath %>template/img/qt1.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">手机号</span><span id="Phone" class="c02 b27 yuan" style="margin-left: 6px; ">0</span></div>
					<div name="gp_btnm"  class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'银行卡号');"><img src="<%=basePath %>template/img/qt4.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">银行卡号</span><span id="regCard" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					<div name="gp_btnm" class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'集装箱号');"><img src="<%=basePath %>template/img/qt12.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">集装箱号</span><span id="regContainer" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					<div name="gp_btnm" class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'邮箱号');"><img src="<%=basePath %>template/img/qt5.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">邮箱账号</span><span id="regEmail" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					<%-- <div name="gp_btnm" class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'发票代码');"><img src="<%=basePath %>template/img/qt24.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">发票代码</span><span id="regStamp" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div> --%>
					<div name="gp_btnm"  class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'车牌号');"><img src="<%=basePath %>template/img/qt6.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">车牌号</span><span id="regLicense" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					<div name="gp_btnm"  class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'身份证号');"><img src="<%=basePath %>template/img/qt2.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">身份证号</span><span id="regSFZ" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					<%-- <div name="gp_btnm"  class="_normal-button"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'GPS');"><img src="<%=basePath %>template/img/qt23.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">卫星定位</span><span id="regGPS" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					 --%><%-- <div name="gp_btnm"  class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'QQ号');"><img src="<%=basePath %>template/img/qt10.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">腾讯QQ</span><span id="QQnumber" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					<div name="gp_btnm" class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'微信');"><img src="<%=basePath %>template/img/qt11.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">腾讯微信</span><span id="weChat" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div> --%>
					<%-- <div name="gp_btnm" class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'推特号');"><img src="<%=basePath %>template/img/qt22.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">推特账号</span><span id="twitter" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					<div name="gp_btnm"  class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'护照编号');"><img src="<%=basePath %>template/img/qt21.png" style="margin-bottom:-2px; "><span  style="margin-left: 6px;">护照编号</span><span id="passport" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					<div name="gp_btnm" class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'支付宝号');"><img src="<%=basePath %>template/img/qt20.png" style="margin-bottom:-2px; "><span  style="margin-left: 6px;">支付宝号</span><span id="zfb" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div> --%>
					<div name="gp_btnm"  class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'固定电话');"><img src="<%=basePath %>template/img/qt3.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">固定电话</span><span id="regTel" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
				<%-- 	<div name="gp_btnm" class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'信用证');"><img src="<%=basePath %>template/img/qt19.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">信用证号</span><span id="regLC" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div> --%>
					<%-- <div name="gp_btnm"  class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'运输车号');"><img src="<%=basePath %>template/img/qt13.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">运输车号</span><span id="regModel" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div> --%>
					<div name="gp_btnm"  class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'价格');"><img src="<%=basePath %>template/img/qt9.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">价格账单</span><span id="regPrice" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					<%-- <div name="gp_btnm" class="_normal-button"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'提单号');"><img src="<%=basePath %>template/img/qt18.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">提单账号</span><span id="regBill" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					 --%><%-- <div name="gp_btnm" class="_normal-button js-change"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'电汇');"><img src="<%=basePath %>template/img/qt14.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">电汇账单</span><span id="regTT" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div> --%>
					<%-- <div name="gp_btnm" id="regPacking" class="_normal-button"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail(1,'装箱单');"><img src="<%=basePath %>template/img/qt9.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">装箱单号</span><span id="regPacking" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div>
					<div name="gp_btnm" id="regContract" class="_normal-button"
						style="cursor: pointer; margin-top: 6px; margin-left: 6px;"
						onclick="showAllemail('1','合同编号');"><img src="<%=basePath %>template/img/qt9.png" style="margin-bottom:-2px; "><span style="margin-left: 6px;">合同编号</span><span id="regContract" class="c02 b27 yuan" style="margin-left: 6px;">0</span></div> --%>
				</div>

</div>

		<div id="d2" style="display: none; margin-top:1%;margin-left:1%;">
			<div class="panel-body">
					<form class="form-inline" role="form">
					<input type="hidden" name="pageno" id="pageno"/>
					<!-- <label class="c08">精确日期：</label>
						<div class="form-group">
						<input id="startDate_d2" name="createdTime" value="${createdTime }"
								class="form-control" placeholder="请输入起始时间" type="text" style="width:168px;margin-left: -4px;" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'});"/>
						</div>
						<div class="form-group">
							<label class="c08" for="" class="clabel">&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</label>
							<input id="endDate_d2" name="endTime"  value="${endTime }"
										    class="form-control" placeholder="请输入结束时间" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()});"/>
						</div> -->
						<div class="form-group">
						<label class="c08">统计次数：</label>
								<input type="text" style="display: none;">
								<input id="XYRconnectNum" name="XYRconnectNum" class="form-control" placeholder="当前默认显示100次以上的排序" type="text" style="width:300px;" onkeydown="onKeyDown_shouFajian(event)"/>
						</div>
				<button type="button" class="btn btn-info" style="width: 75px; height: 33px; margin-bottom: 1px; margin-left: 20px" onclick="showEmailDetil()">分析</button>
				<a id="urlMail_d2" href="#"><div  class="btn btn-info" style="width: 75px; height: 33px; margin-bottom: 1px;" onclick="exportMail_d2()">导出</div></a>
				&nbsp;&nbsp;&nbsp;
			</form>
			</div>
			<div style="overflow:hidden">
				<div class="col-md-5" style="width:50%;float:left;margin-top:1%;padding-left:0px;">
					<div class="panel panel-default" style="position:relative; height:420px; overflow-y :auto;offsetHeight:400px;clientHeight:600px;scrollTop:1000px;" id="scroll" >
						<div class="panel-heading">收件人次数统计</div>
						<table id="datatable" class="table table-striped table-hover br04" style="text-align: center;">
							<thead>
								<tr>
									<th class="c20" style="font-weight:600;text-align: left;width:80%">收件人邮件</th>
									<th class="c20" style="font-weight:600;text-align: right">邮件总次数</th>
								</tr>
							</thead>
							<tbody id="tbcont4">

							</tbody>
						</table>
					</div>
				</div>

				<div class="col-md-5" style="width:50%;float:left;margin-top:1%;">
					<div class="panel panel-default" style="position:relative; height:420px; overflow-y :auto">
						<div class="panel-heading">发件人次数统计</div>
						<table id="datatable" class="table table-striped table-hover br04" style="text-align: center;">
							<thead>
								<tr>
									<th class="c20" style="font-weight:600;text-align: left;width:80%">发件人邮件</th>
									<th class="c20" style="font-weight:600;text-align: right">邮件总次数</th>
								</tr>
							</thead>
							<tbody class="c20" id="email_formWho">

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="d00" style="margin-top: 1%;display: none;">
		<!-- 收发件下面的邮件列表        开头-->
			<div class="row" style="height:; width: 98%; margin-left: 1%; margin-top: 1%;">
			<div id ="d0122" class="panel panel-default" style=" margin-bottom: 5px;overflow: hidden;">
				<div class="panel-heading" style="height: 35px;line-height:normal">搜索<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onclick="testOfSearch22(this)"></span></div>
				<div class="panel-body" id="searchOfForm22" style="display: block;overflow: hidden;">
					<form class="form-inline" role="form" style="padding:10px 0 10px 15px;">
						<div class="form-group">
							<label class="c08">日期范围：</label>
							<div class="form-group">
									<input id="startDate_demain_email22" name="createdTime22" value="" onkeydown="onKeyDown22(event)"
											class="form-control" placeholder="请输入起始时间" type="text" style="width:168px;margin-left: -4px;" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'});"/>
								</div>
								<div class="form-group">
									<label class="c08" for="" class="clabel">&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</label>
									<input id="endDate_demain_email22" name="endTime22"  value="" onkeydown="onKeyDown22(event)"
										    class="form-control" placeholder="请输入结束时间" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()});"/>
								</div>
						</div>
						<div class="form-group">
							<label class="c08" for="" class="clabel">排序:</label>
							<select class="form-control" style=" margin-left: 16px;" id="sortTypeShouFaJian" onkeydown="onKeyDown22(event)">
								<option>日期</option>
								<option>未读</option>
								<option>已读</option>
								<option>星标</option>
								<!-- <option>收件人</option>
								<option>发件人</option> -->
								<option>附件</option>
							</select>
						</div>

						<div class="form-group">
							<input id="emailKeyword22" class="form-control" style="width: 300px; margin-left: 16px;" onkeydown="onKeyDown22(event)"/>
						</div>
						<button type="button" class="btn btn-info" style="width: 75px; height: 33px; margin-bottom: 1px" onclick="getEmailListtofromOfD2(1)">搜索</button>
						<button type="button" class="btn btn-info" style="width: 75px; height: 33px; margin-bottom: 1px" data-target="#myadd" data-toggle="modal" onclick="emptyAllKey22()">高级搜索</button>
					</form>
				</div>
				</div>
	</div>
	<div style="overflow-x :auto;margin-top:13px;">
		<div class="row" style="width: 98%; margin-top:; margin-left: 1%; float: left;">
			<div class="panel panel-default">
				<div class="panel-heading" style="height: 35px;line-height:normal">
					     <span id="emailsumnum22">共搜索到 个结果</span>
					<div style="float: right;">
						<span id="email022" style="color: red;"> 未读 封 </span>
						<span  id="emailnum22">/全部 封</span>
					</div>

				</div>
				<table id="datatable122" class="table table-striped table-hover br04" style="text-align: center;">
							<thead class="c20" id="emailtou22" style="font-weight:600;font-size:14px;">

							</thead>
							<tbody class="c20" id="email22" style="font-size:14px;">

							</tbody>
				</table>

				<div class="alcenter" style="font-size: 14px;padding-top: 20px;padding-bottom: 20px;">
						<div id="total_d522" class="pagecount inline" style="height: 29px; padding-left: 40%;;">
							<span id="tot1_d522"></span>
						</div>
						<div class="pagebar inline" style="position: absolute; right: 132px; height: 29px;">
							<ul class="pagination pagination-sm" style="margin: 0;">
								<li id = "pages1_d522"></li>

								<li id = "pages_d522"></li>

								<li id = "pages2_d522" style=" margin-right: 20px;"></li>
							</ul>
						</div>
						<div style="float: right; margin-right: 11px;">
								<input class="form-control" type="text" style="width: 52px; height: 28px; border-radius: 2px; display: inline;"
									id="givePages_emailsWork22" name="givePages_emailsWork22" onkeydown="onKeyDown_emailsWork22(event)" />
								<button type="button" class="btn" onclick="getEmailListtofromOfD2(1)" style="width: 52px; height: 28px; line-height: 12px;">跳转</button>
						</div>
				</div>
				
	<!-- 收发件页面邮件显示详情 -->			
	<div style="overflow:auto; width:100%;height:100%;margin: auto;" class="modal right fade panel panel-default" id="myModalsD2" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="bg-light lter b-b wrapper-md">
			<h1 class="m-n h4">电子邮件工作台</h1>
			<button id="callout2" type="button" class="btn btn-info" style="width:100px;margin-right: 10px; float: right; margin-top: -25px;" onclick="getInfomationD2()">标记</button>
			<!-- 可移动弹窗 start-->
			<div class="dialog b22" id="dialog2" style="width:35%;">
			    <div class="dialog_head b06 c23" id="move_part2"><h1 style="float: left;padding-left: 18px;line-height:30px;">线索标记</h1>
			    	<a type="button" class="c02" id="close2" style="float:right;line-height:30px;margin-right:10px;">×</a>
			    </div>

			    <div class="dialog_content br16">
			    	<div class="mytits">
			    		<h3 class="c07" id="emailsubjectBiaojiD2" style="display: none;">标题</h3>
			    		<span class="c08" id="caseNamesD2" style="float: left;padding-left: 6px;"></span>
			    		<span class="c08" id="biaojiTimeD2"></span>
			    		
			    		<span class="c08" id="biaojiTotles2D2" style="display: none;"></span>
			    		<span class="c08" id="mailESiDD2" style="display: none;"></span>
			    		
			    		<span class="c08" id="userIDsD2" style="display: none;"></span>

			    		<span class="c08" id="caseIDsD2" style="display: none;"></span>
			    		<span class="c08" id="eSearchIdD2" style="display: none;"></span>
			    		<span class="c08" id="emailTitleD2" style="display: none;"></span>
			    	</div>
			    	<textarea class="br03" style="resize:none;width:99%;min-height:230px;margin-left:0.5%;" id="biaojiContentD2" name="biaojiContentD2" placeholder="请输入线索信息..."></textarea>
			    	<div style="height:40px;">
				    	<button type="button" class="btn btn-info" style="width:100px;margin-right: 6px; float: right; margin-top: 5px;" data-toggle="modal" data-target="" onclick="saveBiaojiD2()">保存</button>
				    </div>
			    </div>

			</div>
			<!-- 可移动弹窗 end-->
			<script type="text/javascript">
						$(document).ready(function() {
						
				          $(".dialog").bg_move({
								move:'.dialog_head',
								size : 6
							});
				             $("#close2").click(function(){
				            	
				            	$(".dialog").css("display","none");
				            }) 
				            $("#callout2").click(function(){
				            
				            	$(".dialog").css("display","inline-block");
				            })
				       
				             $(".addclosed").click(function(){ 
				            	$(".dialog").css("display","none");
				            })  
				            
				        });
			</script>
		</div>
		 <button type="button" class="close" data-dismiss="modal" style="margin-top: 25px;margin-right: 40px;opacity: 0.8;" aria-hidden="true">×</button>
			<div class="panel-heading" class="alcenter" style="margin-top:10px;margin-left:10;">
				邮件内容<span class="modal-title" style="font-size: 22px; width:700px;"></span>
			</div>
				<div class="panel-body" style="padding: 0px; height: 100%;">
					<table id="datatableD2" class="br04"  style="margin-bottom: 0px; width: 100%;font-size:14px;">
						<tr>
							<td class="c20" style="font-weight: bolder; font-size: 17px; padding-left: 30px; padding-top: 20px;font-weight:600;" id="emailsubjectD2">邮件标题</td>
							<td style="padding-top: 20px; text-align: right;padding-right: 30px;">
							<a id="urlD2" href="#"><img src="<%=basePath%>template/img/download.png" onclick="downloadEMLD2()"/></a>&nbsp;&nbsp;&nbsp;
							<span id="emailurlD2" style="display: none;"></span>
							<span id="emailstarD2" style="display: none;"></span>
							<span id="esIdD2" style="display: none;"></span>
							<span id="stariD2" style="display: none;"></span>

							<span id="caseIDsD2" style="display: none;"></span>
							<span id="caseNamesD2" style="display: none;"></span>
						</tr>
						<tr>
							<td class="c20" style="padding-left: 30px; padding-top: 10px;font-weight:600;" id="emailtoWhoD2">发件人：</td>
							<td class="c20" style="padding-right: 30px; text-align: right;" id="emaildateD2"></td>
						</tr>
						<tr>
							<td class="c20" style="padding-left: 30px; padding-top: 10px; padding-bottom: 20px;font-weight:600;" id="emailfromWhoD2">收件人：</td>
							<td></td>
						</tr>
					</table>
				<div style="padding-top: 30px;padding-left: 30px;" id="emailcontentD2" >
				<div id="loadDivD2" style="text-align: center;margin-top: 20px">
				 	<img alt="" src="<%=basePath %>template/img/loading2.gif">
				</div>
				</div>
				<div class="_modal-mailcontent-append" id="correctEml_attfileD2" style="overflow: auto;"></div>
				</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	
	<!-- 收发件下面的邮件列表        结尾-->
		<div id="d3" style="display: none;margin-top:1%;margin-left:1%;">
				<div class="panel-body">
					<form class="form-inline" role="form" id="searchForm" action="" method="post">
						<input type="hidden" name="pageno" id="pageno" />
						<div class="form-group">
							<label class="c08">选择日期范围：</label>
							<div class="form-group">
									<input id="startDate_div3" name="startDate" value=""
											class="form-control" placeholder="请输入起始时间" type="text" style="width:168px;margin-left: -4px;" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'});"/>
								</div>
								<div class="form-group">
									<label class="c08" for="" class="clabel">&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</label>
									<input id="endDate_div3" name="endTime"  value=""
										    class="form-control" placeholder="请输入结束时间" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()});"/>
								</div>
						</div>&nbsp;&nbsp;&nbsp;
					<div class="form-group">
							<label class="c08" for="" class="clabel">关键词:</label>
								<input id="suspectsName_d3" name="suspectsName_d3" class="form-control" placeholder="" type="text" style="width:300px;" onkeydown="onKeyDown_emailPic(event)"/>
						</div>

						<a  href="javascript:void(0)"><div class="btn btn-info" style="width:80px;" onclick="d3();">搜索</div></a>
						<!-- <a  href="javascript:void(0)"><div class="btn btn-info" style="width:80px;"onclick="">导出</div></a> -->
					</form>
				</div>

			 <div class="col-md-5" style="width:100%;float:left;margin-top:1%;" id="xianyiren" >
				<div class="panel panel-default" style="overflow-x:auto;overflow-y:hidden;">
					<div class="panel-heading">邮件关系图</div>
					<div class="panel-body" style="padding-top: 0px">
						<div id="mainCase" style="width:100%; height: 500px;"></div>
					</div>
				</div>
			</div>
		</div>

		<div id="d5" style="display: none;margin-top:1%;margin-left:1%;">
				<div class="panel-body">
					<form class="form-inline" role="form" id="searchForm" action="" method="post">
						<input type="hidden" name="pageno" id="pageno" />
						<div class="form-group">
							<label class="c08" for="" class="clabel">联系次数:</label>
								<input type="text" style="display: none;">
								<input id="connectNum" name="connectNum" class="form-control" placeholder="当前默认显示10次以上的联系" type="text" style="width:300px;" onkeydown="onKeyDown_xianyiren(event)"/>

						</div>

						<a href="javascript:void(0)"><div class="btn btn-info" style="width:80px;" onclick="d5();">搜索</div></a>
						<!-- <a  href="javascript:void(0)"><div class="btn btn-info" style="width:80px;"onclick="">导出</div></a> -->
					</form>
				</div>

			 <div class="col-md-5" style="width:100%;folat:left;margin-top:1%;padding-left:0px;" id="XYRguanxitu">
				<div class="panel panel-default" style="overflow-x:auto;overflow-y:hidden;width:100%;">
					<div class="panel-heading" >嫌疑人关系图</div>
					<div class="panel-body" style="padding-top: 0px">
						<div id="mainSuspect" style="width:100%; height: 500px;">
						
						</div>
					</div>
				</div>
			</div>
			
			<!-- 邮箱关系图下方的搜索框      周武智注释 -->
		<!-- <div class="panel-body">
					<form class="form-inline" role="form" id="searchForm" action="" method="post">
						<input type="hidden" name="pageno" id="pageno" />
						<div class="form-group">
							<label class="c08">选择日期范围：</label>
							<div class="form-group">
									<input id="startDate_div5" name="startDate" value=""
											class="form-control" placeholder="请输入起始时间" type="text" style="width:168px;margin-left: -4px;" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'});"/>
								</div>
								<div class="form-group">
									<label class="c08" for="" class="clabel">&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</label>
									<input id="endDate_div5" name="endTime"  value=""
										    class="form-control" placeholder="请输入结束时间" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()});"/>
								</div>
						</div>&nbsp;&nbsp;&nbsp;
					<div class="form-group">
							<label class="c08" for="" class="clabel">关键词:</label>
								<input id="suspectsName_d5" name="suspectsName_d5" class="form-control" placeholder="" type="text" style="width:300px;" onkeydown="onKeyDown_xianyiren(event)"/>
						</div>
						<a href="javascript:void(0)"><div class="btn btn-info" style="width:80px;" onclick="d5();">搜索</div></a>
						<a  href="javascript:void(0)"><div class="btn btn-info" style="width:80px;"onclick="">导出</div></a>
					</form>
				</div> -->
				<!-- 邮箱关系图下方的搜索框      周武智注释 -->
		</div>
	<div id="d6" style="margin-top: 1%;display: none;">
		<%-- <div class="col-md-12">
			<form class="form-inline" role="form">
				<label class="c08">选择日期范围：</label>
				<div class="form-group">
					<input id="startDate6" name="startDate6" value="${createdTime }"
							class="form-control" placeholder="请输入起始时间" type="text" style="width:168px;margin-left: -4px;" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'});"/>
				</div>
				<div class="form-group">
						<label class="c08" for="" class="clabel">&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</label>
						<input id="endDate6" name="endDate6"  value="${endTime }"
										    class="form-control" placeholder="请输入结束时间" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()});"/>
				</div>

				<div class="form-group">
						<label class="c08">关键词：</label>
						<input id="guanjianci" class="form-control" style="width: 300px; margin-left: 16px;" onkeydown="onKeyDown_shijianxian(event)"/>
				</div>
				<button type="button" class="btn btn-info" style="width: 75px; height: 33px; margin-bottom: 1px" onclick="shijianxian()">搜索</button>
			</form>
		</div> --%>

		 <div class="col-md-5" style="width:100%;folat:left;" id="shijianxian">
				<div class="panel panel-default" style="overflow-x:auto;overflow-y:hidden;width:100%;">
					<div class="panel-heading" >时间线</div>
					<div class="panel-body" style="padding-top: 0px;">
						    <div id="main2" style="width:100%; height: 400px;">
						    
						    </div>
					</div>
				</div>
		</div>
	</div>
	<!-- 公共邮件操作 -->
<div id="d0" style="margin-top: 1%;display: none;">
	<div  class="row"
			style="height:; width: 98%; margin-left: 1%; margin-top: 1%;">
			<div id ="d01" class="panel panel-default" style=" margin-bottom: 5px;">
				<div class="panel-heading" style="height: 35px;line-height:normal" >搜索<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onclick="testOfSearch(this)"></span></div>
				<div class="panel-body" id="searchOfForm" style="display: block;">
					<form class="form-inline" role="form" style="padding:10px 0 10px 15px;">
						<div class="form-group">
							<label class="c08">日期范围：</label>
							<div class="form-group">
									<input id="startDate_demain_email" name="createdTime" value="" onkeydown="onKeyDown(event)"
											class="form-control" placeholder="请输入起始时间" type="text" style="width:168px;margin-left: -4px;" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'});"/>
								</div>
								<div class="form-group">
									<label class="c08" for="" class="clabel">&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</label>
									<input id="endDate_demain_email" name="endTime"  value="" onkeydown="onKeyDown(event)"
										    class="form-control" placeholder="请输入结束时间" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()});"/>
								</div>
						</div>
						<div class="form-group">
							<label class="c08" for="" class="clabel">排序:</label>
							<select class="form-control" style=" margin-left: 16px;" id="sortType" onkeydown="onKeyDown(event)">
								<option>日期</option>
								<option>未读</option>
								<option>已读</option>
								<option>星标</option>
								<!-- <option>收件人</option>
								<option>发件人</option> -->
								<option>附件</option>
							</select>
						</div>

						<div class="form-group" style="position:relative;">
							<input id="emailKeyword" class="form-control" style="    width: 300px; margin-left: 16px;" onkeydown="onKeyDown(event)" oninput="getCookie()"/>				<ul id="con" style="position: absolute;left:16px;width: 300px;border-top: none;list-style-type:none;height:auto !important" class="form-control" ></ul>
						</div>
						<button type="button" class="btn btn-info" style="width: 75px; height: 33px; margin-bottom: 1px" onclick="getAllemail()">搜索</button>
						<button type="button" class="btn btn-info" style="width: 75px; height: 33px; margin-bottom: 1px;margin-right:5px" data-target="#myadd" data-toggle="modal" onclick="emptyAllKey()">高级搜索</button>
						
						
						<div class="choosed" id="filtrate" style="height:33px;">
							<%-- <p class="infor">
								<span>选择内容部分</span>
								<a class="js-closed"><img src="<%=basePath%>template/img/gb.png"></a>
							</p>
							<p class="infor">
								<span>选择内容部分</span>
								<a class="js-closed"><img src="<%=basePath%>template/img/gb.png"></a>
							</p> --%>
							
						</div>
						<span class="sx js-all">清空筛选</span>
						
					</form>
				</div>
				</div>
				</div>
				<!-- 邮件表格 -->
		<div style=" overflow-x :auto;margin-top:13px;">
		<div class="row" style="width: 98%; margin-top:; margin-left: 1%; float: left;">
			<div class="panel panel-default">
				<div class="panel-heading" style="height: 35px;line-height:normal">
					     <span id="emailsumnum">共搜索到 个结果</span>
					<div style="float: right;">
						<span id="email0" style="color: red;"> 未读 封 </span>
						<span  id="emailnum">/全部 封</span>
					</div>

				</div>
				<table id="datatable1" class="table table-striped table-hover br04"
							style="text-align: center;">
							<thead class="c20" id="emailtou" style="font-weight:600;font-size:14px;">

							</thead>
							<tbody class="c20" id="email" style="font-size:14px;">

							</tbody>
				</table>

				<div class="alcenter" style="font-size: 14px;padding-top: 20px;padding-bottom: 20px;">
						<div id="total_d5" class="pagecount inline" style="height: 29px; padding-left: 40%;;">
							<span id="tot1_d5"></span>
						</div>
						<div class="pagebar inline" style="position: absolute; right: 132px; height: 29px;">
							<ul class="pagination pagination-sm" style="margin: 0;">
								<li id = "pages1_d5"></li>

								<li id = "pages_d5"></li>

								<li id = "pages2_d5" style=" margin-right: 20px;"></li>
							</ul>
						</div>
						<div style="float: right; margin-right: 11px;">
								<input class="form-control" type="text" style="width: 52px; height: 28px; border-radius: 2px; display: inline;"
									id="givePages_emailsWork" name="givePages_emailsWork" onkeydown="onKeyDown_emailsWork(event)" />
								<span id="btn_emailsWork"></span>
						</div>
					</div>

		<!-- 邮件详情内容页面 -->
		<div style="overflow:auto; width:100%;height:100%;margin: auto;" class="modal right fade panel panel-default" id="myModals" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="bg-light lter b-b wrapper-md">
			<h1 class="m-n h4">电子邮件工作台</h1>
			<button id="callout" type="button" class="btn btn-info" style="width:100px;margin-right: 10px; float: right; margin-top: -25px;" onclick="getInfomation()">标记</button>
			<!-- 可移动弹窗 start-->
			<div class="dialog b22"  style="width:35%;">
			    <div class="dialog_head b06 c23" id="move_part"><h1 style="float: left;padding-left: 18px;line-height:30px;">线索标记</h1>
			    	<a type="button" class="c02" id="close" style="float:right;line-height:30px;margin-right:10px;">×</a>
			    </div>

			    <div class="dialog_content">
			    	<div class="mytits">
			    		<h3 class="c07" id="emailsubjectBiaoji" style="display: none;">标题</h3>
			    		<span class="c08" id="caseNames" style="position:absolute;left:10px;"></span>
			    		<span class="c08" id="biaojiTime" style="position:absolute;right:10px;"></span>
			    		
			    		<span class="c08" id="caseIDs" style="display: none;"></span>
			    		
			    		<span class="c08" id="userIDs" style="display: none;"></span>
			    		<span class="c08" id="biaojiTotles2" style="display: none;"></span>
			    		<span class="c08" id="mailESiD" style="display: none;"></span>  
			    		<span class="c08" id="eSearchId" style="display: none;"></span>
			    		<span class="c08" id="emailTitle" style="display: none;"></span>
			    		
			    	</div>
			    	<textarea class="br03" style="resize:none;width:99%;min-height:230px;margin-left:0.5%;" id="biaojiContent" name="biaojiContent" placeholder="请输入线索信息..."></textarea>
			    	<div style="height:40px;">
				    	<button type="button" class="btn btn-info" style="width:100px;margin-right: 6px; float: right; margin-top: 5px;" data-toggle="modal" data-target="" onclick="saveBiaoji()">保存</button>
				    </div>
			    </div>
			    
			</div>
			<script type="text/javascript">
						$(document).ready(function() {
						
				          $(".dialog").bg_move({
								move:'.dialog_head',
								size : 6
							});
				             $("#close").click(function(){
				            	
				            	$(".dialog").css("display","none");
				            }) 
				            $("#callout").click(function(){
				            
				            	$(".dialog").css("display","inline-block");
				            })
				       
				             $(".addclosed").click(function(){ 
				            	$(".dialog").css("display","none");
				            })  
				            
				        });
			</script>
					
			<!-- 可移动弹窗 end-->
		</div>
		 <button type="button" class="close addclosed" data-dismiss="modal" style="margin-top: 25px;margin-right: 40px;opacity: 0.8;" aria-hidden="true">×</button>
			<div class="panel-heading" class="alcenter" style="margin-top:10px;margin-left:10;">
				邮件内容<span class="modal-title" style="font-size: 22px; width:700px;"></span>
			</div>
				<div class="panel-body" style="padding: 0px; height: 100%;">
					<table id="datatable" class="br04"  style="margin-bottom: 0px; width: 100%;font-size:14px;">
						<tr>
							<td class="c20" style="font-weight: bolder; font-size: 17px; padding-left: 30px; padding-top: 20px;font-weight:600;" id="emailsubject">邮件标题</td>
							<td style="padding-top: 20px; text-align: right;padding-right: 30px;">
							<a id="url" href="#"><img src="<%=basePath%>template/img/download.png" onclick="downloadEML()" /></a>&nbsp;&nbsp;&nbsp;
							<%-- <img id="stars" src="<%=basePath%>template/img/star2.png" onclick="upemailstar()" /></td> --%>
							<span id="emailurl" style="display: none;"></span>
							<span id="emailstar" style="display: none;"></span>
							<span id="esId" style="display: none;"></span>
							<span id="stari" style="display: none;"></span>

							<span id="caseIDs" style="display: none;"></span>
							<span id="caseNames" style="display: none;"></span>
						</tr>
						<tr>
							<td class="c20" style="padding-left: 30px; padding-top: 10px;font-weight:600;" id="emailtoWho">发件人：</td>
							<td class="c20" style="padding-right: 30px; text-align: right;" id="emaildate"></td>
						</tr>
						<tr>
							<td class="c20" style="padding-left: 30px; padding-top: 10px; padding-bottom: 20px;font-weight:600;" id="emailfromWho">收件人：</td>
							<td></td>
						</tr>
					</table>
				<div style="padding-top: 30px;padding-left: 30px;" id="emailcontent" >
				<div id="loadDiv" style="text-align: center;margin-top: 20px">
				 				<img alt="" src="<%=basePath %>template/img/loading2.gif">
				 			</div>
				</div>
				<div class="_modal-mailcontent-append" id="correctEml_attfile" style="overflow: auto;"></div>
				</div>
	</div>
	</div>
	</div>
	</div>
</div>
	<div id="d4" style="margin-top: 1%;display: none;">
		<div class="col-md-12">
			<form class="form-inline" role="form">
				<label class="c08">选择日期范围：</label>
				<div class="form-group">
					<input id="startDate_demain" name="createdTime" value="${createdTime }"
							class="form-control" placeholder="请输入起始时间" type="text" style="width:168px;margin-left: -4px;" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'});"/>
				</div>
				<div class="form-group">
						<label class="c08" for="" class="clabel">&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</label>
						<input id="endDate_demain" name="endTime"  value="${endTime }"
										    class="form-control" placeholder="请输入结束时间" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()});"/>
				</div>

				<button type="button" class="btn btn-info" style="width: 75px; height: 33px; margin-bottom: 1px; margin-left: 20px" onclick="showDomain()">分析</button>
				<a id="urlMail" href="javascript:void(0)"><div  class="btn btn-info" style="width: 75px; height: 33px; margin-bottom: 1px;"onclick="exportMail()">导出</div></a>
			</form>
		</div>

		<div class="col-md-6" style="margin-top: 1%;">
			<div class="panel panel-default">
				<div class="panel-heading">域名TOP10</div>
				<div class="panel-body" style="padding-top: 0px; overflow-x: scroll;overflow-y: hidden;">
					<div id="main" style="width: 800px; height: 300px;"></div>
				</div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">域名命中次数统计</div>
				<div style="overflow-y: auto; height: 250px;">
					<table id="datatable" class="table table-striped table-hover br04" style="text-align: center;">
						<thead>
							<tr>
								<th class="c20" style="font-weight:600;text-align: left">域名类型</th>
								<th class="c20" style="font-weight:600;text-align: left"></th>
								<th class="c20" style="font-weight:600;text-align: right">命中次数</th>
							</tr>
						</thead>
						<tbody id="getDomainInfo" class="c20">

						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-md-6" style="margin-top: 1%;">
			<div class="panel panel-default">
				<div class="panel-heading">嫌疑人疑似位置图</div>
				<div class="panel-body">
					<div id="container_IpMap" class="col-md-6" 
						style=" height: 630px; width: 100%; border: 1px solid #DDE4E6;overflow-x:auto;overflow-y:hidden;">
					</div>
				</div>
			</div>
			
<script type="text/javascript">
  // 百度地图API功能
	var map = new BMap.Map("container_IpMap", {minZoom:1,maxZoom:12});    // 创建Map实例
	var point= new BMap.Point(35.404,61.915);
	map.centerAndZoom(point,4);  // 初始化地图,设置中心点坐标和地图级别
	//map.addControl(new BMap.MapTypeControl());   //添加地图类型控件 离线只支持电子地图，卫星/三维不支持
	//map.setCurrentCity("北京");          // 设置地图显示的城市 离线地图不支持！！
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
  	map.addControl(new BMap.NavigationControl());   //缩放按钮
	var cr = new BMap.CopyrightControl({anchor: BMAP_ANCHOR_TOP_RIGHT});   //设置版权控件位置
	map.addControl(cr); //添加版权控件
	var bs = map.getBounds();   //返回地图可视区域
	function init_for_ipMap(){
		map.clearOverlays();//清空原来的标注
	  	for(var i=0;i<justAddrs.length;i++){

	 		map.centerAndZoom(new BMap.Point(93.39385,35.179228), 4);
	  		lat=justAddrs[i].split("-")[0];
	  		lon=justAddrs[i].split("-")[1];

	  		point = new BMap.Point(lon,lat);
	  		var marker = new BMap.Marker(point);  // 创建标注
	  		map.addOverlay(marker);               // 将标注添加到地图中
	  		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	  	   }
	     }
	function init_for_ipMap1(){
		map.clearOverlays();//清空原来的标注
	  	for(var i=0;i<justAddrs_onlyOne.length;i++){

	 		map.centerAndZoom(new BMap.Point(93.39385,35.179228), 4);
	  		lat=justAddrs_onlyOne[i].split("-")[0];
	  		lon=justAddrs_onlyOne[i].split("-")[1];

	  		point = new BMap.Point(lon,lat);
	  		var marker = new BMap.Marker(point);  // 创建标注
	  		map.addOverlay(marker);               // 将标注添加到地图中
	  		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	  	   }
	     }
</script>
</div>
		<div class="col-md-12" style="height:; width: 98%; margin-left: 1%; margin-top: 1%;">
			<form class="form-inline" role="form">
				<div class="form-group">
					<label class="c08" for="" class="clabel">排序:</label><select
						class="form-control" style=" margin-left: 16px;"
						id="sortType_div4">
						<option value="日期">日期</option>
						<option>未读</option>
						<option>已读</option>
						<option>星标</option>
						<!-- <option>收件人</option>
						<option>发件人</option> -->
					    <option>附件</option>
						<option>IP</option>
					</select>
				</div>

				<div class="form-group">
					<label class="c08">选择日期范围：</label>
				<div class="form-group">
									<input id="startDate" name="createdTime" value="${createdTime }"
											class="form-control" placeholder="请输入起始时间" type="text" style="width:168px;margin-left: -4px;" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'});"/>
								</div>
								<div class="form-group">
									<label class="c08" for="" class="clabel">&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</label>
									<input id="endDate" name="endTime"  value="${endTime }"
										    class="form-control" placeholder="请输入结束时间" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()});"/>
								</div>
				</div>

				<div class="form-group">
					<label class="c08">关键字/邮箱地址</label>
					<input id="emailKeyword_div4" class="form-control" placeholder="请输入..." style="width: 260px; margin-left: 16px;" onkeydown="onKeyDown_yuming(event)"/>
				</div>
				<button type="button" class="btn btn-info" style="width: 75px; height: 33px; margin-bottom: 1px" onclick="showAllemail_div4(1)">搜索</button>
			</form>
		</div>

		<div class="col-md-12" style="margin-top: 1%;">
			<!-- 左侧表格 -->
			<div class="panel panel-default">
				<div class="panel-heading">
					    <span id="emailsumnum_div4">共搜索到 个结果</span>
					<div style="float: right;">
						<span id="email0_div4" style="color: red;"> 未读 封 </span>
						<span  id="emailnum_div4">/全部 封</span>
					</div>

				</div>
				<table id="datatable1_div4" class="table table-striped table-hover br04" style="text-align: center;">
					<thead id="emailtou_div4" class="c20" style="font-weight:600;">

					</thead>
					<tbody id="email_div4" class="c20">

					</tbody>
				</table>

				<div class="alcenter" style="font-size: 14px; padding-top: 20px; padding-bottom: 20px;">
					<div id="total_d5_div4" class="pagecount inline" style="height: 29px; padding-left: 40%;">
						<span id="tot1_d5_div4"></span>
					</div>
					<div class="pagebar inline" style="position: absolute; right: 118px; height: 29px;">
						<ul class="pagination pagination-sm" style="margin: 0;">
							<li id="pages1_d5_div4"></li>

							<li id="pages_d5_div4"></li>

							<li id="pages2_d5_div4" style="margin-right: 20px;"></li>
						</ul>
					</div>
					<div style="float: right;margin-right: 11px;">
						<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages_div4yuming" name="givePages_div4yuming" onkeydown="onKeyDown_div4yuming(event)"/>
						<button type="button" class="btn" onclick="showAllemail_div4(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
					</div>
				</div>

				<!-- 邮件内容   -->
					<div style="overflow:auto; width:100%;height:100%;margin: auto;" class="modal right fade panel panel-default" id="myModals_div4" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
					<button type="button" class="close addclosed" data-dismiss="modal" aria-hidden="true" style="margin-top: 13px;margin-right: 18px;opacity: 0.8;">×</button>
					<div class="panel-heading" class="alcenter" style="font-size: 18px;"> 邮件内容
					<span class="modal-title" style="font-size: 22px; width: 100%;"></span>
					<button id="callout1" type="button" class="btn btn-info" style="width:100px;margin-right: 10px; float: right; margin-top: 0px;" onclick="getInfomation_div4()">标记</button>
					<!-- 可移动弹窗 start-->
					<div class="dialog1 b22" style="width:35%;">
					    <div class="dialog_head b06 c23" id="move_part"><h1 style="float: left;padding-left: 18px;line-height:30px;">线索标记</h1>
					    	<a type="button" class="c02" id="close1" style="float:right;line-height:30px;margin-right:10px;">×</a>
					    </div>
		
					    <div class="dialog_content">
					    	<div class="mytits">
					    		<h3 class="c07" id="emailsubjectBiaoji_div4" style="display: none;">标题</h3>
					    		<span class="c08" id="caseNames_div4" style="position:absolute;left:10px;"></span>
					    		<span class="c08" id="biaojiTime_div4" style="position:absolute;right:10px;"></span>
					    		
					    		<span class="c08" id="caseIDs_div4" style="display: none;"></span>
					    		<span class="c08" id="userIDs_div4" style="display: none;"></span>
					    		
					    		<span class="c08" id="biaojiTotles2_div4" style="display: none;"></span>
			    				<span class="c08" id="mailESiD_div4" style="display: none;"></span>
			    				<span class="c08" id="eSearchId_div4" style="display: none;"></span>
			    				<span class="c08" id="emailTitle_div4" style="display: none;"></span>
					    	</div>
					    	<textarea class="br03" style="resize:none;width:99%;min-height:230px;margin-left:0.5%;" id="biaojiContent_div4" name="biaojiContent_div4" placeholder="请输入线索信息..."></textarea>
					    	<div style="height:40px;">
						    	<button type="button" class="btn btn-info" style="width:100px;margin-right: 6px; float: right; margin-top: 5px;" data-toggle="modal" data-target="" onclick="saveBiaoji_div4()">保存</button>
						    </div>
					    </div>
					    
					</div> 
				    <script type="text/javascript">
						$(document).ready(function() {
				            $(".dialog1").bg_move({
								move:'.dialog_head',
								size : 6
							});
				            $("#close1").click(function(){
				            	$(".dialog1").css("display","none");
				            })
				         
				            $("#callout1").click(function(){
				            
				            	$(".dialog1").css("display","inline-block");
				            })
				            $(".addclosed").click(function(){ 
				            	
				            	$(".dialog1").css("display","none");
				            }) 
				        });
					</script>
									<!-- 可移动弹窗 end-->

					</div>
					<div class="panel-body" style="padding: 0px; height: 100%;">
						<table id="datatable_div4" class="br04" style="margin-bottom: 0px; width: 100%;">
							<tr>
								<td class="c20" style="font-weight: bolder; font-size: 17px; padding-left: 30px; padding-top: 20px;font-weight:600;" id="emailsubject_div4">邮件标题</td>
								<td style="padding-top: 20px; text-align: right;padding-right: 30px;">
								<a id="url_div4" href="#"> <img id="url_div4" src="<%=basePath%>template/img/download.png" onclick="downloadEML_div4()" /></a>&nbsp;&nbsp;&nbsp;
								<%-- <img id="stars_div4" src="<%=basePath%>template/img/star2.png" onclick="upemailstar_div4()" /></td> --%>
								<span id="emailurl_div4" style="display: none;"></span>
								<span id="emailstar_div4" style="display: none;"></span>
								<!-- <span id=esId_div4 style="display: none;"></span> -->
								<span id="esId_div4" style="display: none;"></span>
								<span id="stari_div4" style="display: none;"></span>

								<span id="caseIDs_div4" style="display: none;"></span>
								<span id="caseNames_div4" style="display: none;"></span>
							</tr>
							<tr>
								<td class="c20" style="padding-left: 30px; padding-top: 10px;font-weight:600;" id="emailtoWho_div4">发件人：</td>
								<td style="padding-right: 30px; text-align: right;" id="emaildate_div4"></td>
							</tr>
							<tr>
								<td class="c20" style="padding-left: 30px; padding-top: 10px; padding-bottom: 20px;font-weight:600;" id="emailfromWho_div4">收件人：</td>
								<td></td>
							</tr>
						</table>
					<!--<div style="padding-top: 30px; padding-left: 30px;" padding-bottom: 405px; id="emailcontent_div4"//删除邮件内容框体的下边距-->
						<div style="padding-top: 30px; padding-left: 30px;" id="emailcontent_div4">
						<div id="loadDiv" style="text-align: center;margin-top: 20px">
				 				<img alt="" src="<%=basePath %>template/img/loading2.gif">
				 			</div>
						</div>
						<div class="_modal-mailcontent-append" id="correctEml_attfile_div4" style="overflow: auto;">
				 		</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<!-- 高级搜索 -->
	<div class="modal" id="myadd" tabindex="-1" >
	    <div class="panel panel-default" style="width: 1000px; height: 800px; margin: 3% auto;">
	        <div class="panel-heading b08 br14" style="padding:5px 15px;">
	            <button type="button" class="close" data-dismiss="modal" style="margin-top:-2px;">
	                <span aria-hidden="true" class="c02 b26 addcloss">×</span>
	                <span class="sr-only">Close</span>
	            </button>
	            <h4 class="modal-title" style="font-size:14px">更多条件</h4>
	        </div>
	        <div class="panel-body" style="width: 100%; height: 100%;padding:20px;">
	           <div class="myconts">
	                <!-- 选择搜索条件start -->
	                <div class="contLeft br13">
	                    <div class="tit b08 br14">
	                        <h3 class="c05">选择搜索条件</h3>
	                    </div>
	                    <ul class="js-bg">
	                        <li class="selected" data-tabFor="reportTable01"><img src="<%=basePath%>template/img/gd (1).png"><span class="c08">or(包含任意关键词)</span></li>
	                        <li data-tabFor="reportTable02"><img src="<%=basePath%>template/img/gd (2).png"><span class="c08">and(包含所有关键词)</span></li>
	                        <li data-tabFor="reportTable03"><img src="<%=basePath%>template/img/gd (3).png"><span class="c08">not(不包含这些关键词)</span></li>
	                        <%-- <li data-tabFor="reportTable04"><img src="<%=basePath%>template/img/gd (4).png"><span>准确关键词</span></li> --%>
	                       <%--  <li data-tabFor="reportTable05"><img src="<%=basePath%>template/img/gd (5).png"><span>文件大小</span></li> --%>
	                        <li data-tabFor="reportTable06"><img src="<%=basePath%>template/img/gd (6).png"><span class="c08">邮件地址</span></li>
	                        <li data-tabFor="reportTable07"><img src="<%=basePath%>template/img/gd (7).png"><span class="c08">日期</span></li>
	                    </ul>

	                </div>
	                <!-- 选择搜索条件end -->
	                <!-- 输入条件值 start -->
	                <div class="contRight br13" id="reportTable01">
	                    <div class="tit b08 br14">
	                        <h3 class="c05">输入条件值</h3>
	                    </div>
	                    <textarea class="c08" id="orKey"  placeholder="请输入关键词，使用空格进行分割"></textarea>
	                </div>
	                <!-- 输入条件值 end -->
	                <!-- 输入条件值 start -->
	                <div class="contRight br13" id="reportTable02">
	                    <div class="tit b08 br14">
	                        <h3 class="c05">输入条件值</h3>
	                    </div>
	                    <textarea class="c08" id="andKey" placeholder="请输入关键词，使用空格进行分割"></textarea>
	                </div>
	                <!-- 输入条件值 end -->
	                <!-- 输入条件值 start -->
	                <div class="contRight br13" id="reportTable03">
	                    <div class="tit b08 br14">
	                        <h3 class="c05">输入条件值</h3>
	                    </div>
	                    <textarea class="c08" id="notKey" placeholder="请输入关键词，使用空格进行分割"></textarea>
	                </div>
	                <!-- 输入条件值 end -->
	                <!-- 输入条件值 start -->
	                <!-- <div class="contRight" id="reportTable04">
	                    <div class="tit">
	                        <h3>输入条件值</h3>
	                    </div>
	                    <textarea id="keyKey" placeholder="请输入关键词，使用空格进行分割"></textarea>
	                </div> -->
	                <!-- 输入条件值 end -->
	                <!-- 请输入文件大小范围 start -->
	                <!-- <div class="contRight" id="reportTable05">
	                    <div class="tit">
	                        <h3>请输入文件大小范围</h3>
	                    </div>
	                    <div style="padding-left:15px;">
	                        <div class="formo c08"><span>文件最小值：</span><input class="form-control" placeholder="请输入..." type="text"></div>
	                        <div class="formt c08"><span>文件最大值：</span><input class="form-control" placeholder="请输入..." type="text"></div>
	                        <p style="color:#999;font-size:14px;margin-top:10px">(例如：100bytes;20kb;30MB)</p>
	                    </div>
	                </div> -->
	                <!-- 请输入文件大小范围 end -->
	                <!-- 输入条件值 start -->
	                <div class="contRight br13" id="reportTable06">
	                    <div class="tit b08 br14">
	                        <h3 class="c05">输入条件值</h3>
	                    </div>
	                    <div style="padding-left:15px;">
	                        <div class="forml">
	                            <span>邮箱地址：</span>
	                            <input class="form-control" placeholder="请输入..." type="text" id="emailAddress">
	                            <a class="search c02 b06" style="vertical-align:middle;" onclick="seekEmail()">搜索</a>
	                        </div>
	                        <div class="panel-body br17" style="height:110px;overflow-y: scroll;">
	                            <table id="datatable" class="table table-hover br012" style="margin-bottom:0px;">
	                                <tbody id="highTbcont">
	                                </tbody>
	                            </table>
	                        </div>

	                        <div class="tall"style="margin-top: 5px;">
	                            <label class="c08" for="get" style="margin-right:20px;">
	                                <input class="c08" type="checkbox" id="get"  onclick="setEmailType(1)">发件箱
	                            </label>
	                            <label class="c08" for="give">
	                                <input class="c08" type="checkbox" id="give"  onclick="setEmailType(2)">收件箱
	                            </label>
	                        </div>
	                    </div>
	                </div>
	                <!-- 输入条件值 end -->

	                <!-- 输入条件值 start -->
	                <div class="contRight br13" id="reportTable07">
	                    <div class="tit b08 br14">
	                        <h3 class="c05">输入条件值</h3>
	                    </div>
	                    <div style="padding-left:15px;margin-top:15px;">
	                    <select class="form-control mysize" style=" height: 34px; "id="timeType">
							<option value="之间">日期之间</option>
							<option value="之外">日期之外</option>
							<option value="之前">日期之前</option>
							<option value="之后">日期之后</option>
						</select>
	                        <div class="date">
	                            <span class="c08">开始日期：</span>
	                            <div style="display:inline-block;position:relative;">
	                                <input id="kaishiTime" class="form-control mysize" placeholder="请选择..." type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'});">
	                                <img src="<%=basePath%>template/img/016.png">
	                            </div>
	                        </div>
	                        <div class="date">
	                            <span class="c08">结束日期：</span>
	                            <div style="display:inline-block;position:relative;">
	                                <input id="jieshuTime" class="form-control mysize" placeholder="请选择..." type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()});">
	                                <img src="<%=basePath%>template/img/016.png">
	                            </div>
	                        </div>
	                    </div>
	                </div>
	                <!-- 输入条件值 end -->

	                <a class="mybtn b06 c02" onclick="addRule()" >添加规则</a>

	                <!-- 规则列表start -->
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <span style="font-size:14px;">规则列表</span>
	                    </div>
	                    <div class="panel-body">
	                        <table class="table table-striped js-table br12" style="margin-bottom:0px;">
	                            <thead>
	                                    <td style="padding-left:25px;">搜索条件</td>
	                                    <td style="text-align:right;padding-right:10px;">值</td>
	                                    <td style="text-align:right;padding-right:5px;;width: 63px;">操作</td>
	                            </thead>
	                            <tbody style="color:#747474" id="endRule" >
	                                <!-- <tr>
	                                    <td style="padding-left:25px;">and(包含任意关键词)</td>
	                                    <td style="text-align:right;padding-right:30px;">你，我</td>
	                                </tr> -->

	                            </tbody>
	                        </table>
	                    </div>
	                </div>
	                <!-- 规则列表end -->
	                <div class="buts">
	                    <div style="display:inline-block;">
	                        <div class="wish-checkbox" onclick="setMatchingType(1)">
	                            <a class="js-checkbox" name="aa"></a>
	                            <input type="checkbox" style="display:none">
	                        </div>
	                        <span class="c08" style="font-size:14px;cursor:pointer" >任意规则</span>
	                    </div>
	                    <div style="display:inline-block;" onclick="setMatchingType(2)">
	                        <div class="wish-checkbox">
	                            <a class="js-checkbox actived" name="aa"></a>
	                            <input type="checkbox" style="display:none">
	                        </div>
	                        <span class="c08" style="font-size:14px;cursor:pointer" >匹配全部</span>
	                    </div>
	                </div>
	                <div class="ebtns">
	                    <a class="clear br13 c05" onclick="emptyAllKey()">清空</a>
	                    <a class="search c02 b06" data-dismiss="modal" onclick="highSeek()">搜索</a>
	                </div>
	           </div>
	        </div>
	    </div>
	</div>
	</div>
	<jsp:include page="footer2.jsp"></jsp:include>
	</div>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>template/js/script_left.js"></script>
	<script src="<%=basePath%>template/js/cutover/js.js"></script>
	<script src="<%=basePath%>template/js/cutover/echarts.js"></script>
	<script src="<%=basePath%>template/js/cutover/dataTool.min.js"></script>
	<script type="text/javascript">
	var caseidyoujian = '<%=request.getAttribute("caseid")%>';
    var casenameEmail = '<%=request.getAttribute("casename")%>';
	/* 高级搜索切换 */
	$(document).ready(function() {

          $(".js-bg li").each(function(){
            var tid = $(this).attr("data-tabFor");
            if (!$(this).hasClass("selected")){
                $("#"+tid).css("display","none");
            }
            $(this).off("click");
            $(this).on("click",function(){
                $(this).siblings("li").each(function(){
                       $(this).removeClass("selected");
                       var sid = $(this).attr("data-tabFor");
                       $("#"+sid).css("display","none");
                })
                $("#"+$(this).attr("data-tabFor")).removeAttr("style");
                $(this).addClass("selected");
            })
        });
    });
	var zhengzeType="";//全局正则类型
	var orKey="";
	var andKey="";
	var notKey="";
	var timeType="";
	var kaishiTime="";
	var jieshuTime="";
	var matchingType="2";
	var fromType="0";
	var toType="0";
	var emailstr="";
	var emailType="收发";
	var emailKeyword = "";//关键词搜索
	//高级搜索-邮箱列表
	function seekEmail(){
		var emailAddress = document.getElementById("emailAddress").value;
		$.ajax({
			type : "POST",
			url : "<%=basePath%>emaiExcavatel/seekEmail.php",
			data : {
				"caseidStr":caseidStr,
				"emailAddress":emailAddress
			},
			dataType : "json",
			async: true,
			success : function(data) {
				$("#highTbcont").empty();
				$.each(data,function(i,item){
					var html="";
					var keylist = item.yuming;
				    $.each(keylist,function(j,item2){
						if(j==keylist.length-1){
							html = '<tr>'+
										'<td class="td_right c08" id="" style="padding-right: 0px;width: 20px;" onclick="shuoEmail('+i+')"><img id="imgRotate'+i+'" class="menu_arrow" alt="" src="<%=basePath%>template/img/+.png"  /></td>'+
										'<td class="td_right c08"><input id="caselist'+i+'" type="checkbox" name="seek_name_1" value="'+item2+'"  /></td>'+
										'<td class="td_right c08">'+item2+'</td>'+
									'</tr>'+html;
						}else{
							html+='<tr name="seekEmail'+i+'" style="display:none ;">'+
									'<td class="td_right c08" style="padding-right: 0px;width: 20px;"></td>'+
									'<td class="td_right c08" id=""><input name="seek_name_2" value="'+item2.substring(item2.indexOf("(")+1, item2.indexOf(")"))+'" type="checkbox" /></td>'+
									'<td class="td_right c08" id="seek_id'+i+'j'+j+'" >'+
										'<span style=" margin-left: 20px; white-space:nowrap;">'+item2+'</span>'+
										'</td>'+
								'</tr>';
						}
					});
				    $("#highTbcont").append(html);
				});
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	//高级搜索-展开域名
	var suspect=0;
	function shuoEmail(i){
		if(suspect==0){
			 var nodes = document.getElementsByName("seekEmail"+i);
			 for(j=0;j<nodes.length;j++){
				 nodes[j].style.display="";
			 }
	        var imgRotate = document.getElementById("imgRotate"+i);
	       // imgRotate.style.transform="rotate(90deg)";
	        imgRotate.src="<%=basePath%>template/img/-.png";
	        suspect=1;
		}else{
			 var nodes = document.getElementsByName("seekEmail"+i);
			 for(j=0;j<nodes.length;j++){
				 nodes[j].style.display="none";
			 }
	        var imgRotate = document.getElementById("imgRotate"+i);
	       // imgRotate.style.transform="";
	        imgRotate.src="<%=basePath%>template/img/+.png";
	        suspect=0;
		}
	}
	//邮件列表-选择收发类型	var fromType="0";
	//var toType="0";
	function setEmailType(type){
		if(type==1){
			if(fromType=="0"){
				fromType="1";
			}else{
				fromType="0";
			}
		}else{
			if(toType=="0"){
				toType="1";
			}else{
				toType="0";
			}
		}
		//emailType
		if(fromType=="0" && toType=="0"){
			emailType="收发";
		}else if(fromType=="1" && toType=="0"){
			emailType="发";
		}if(fromType=="0" && toType=="1"){
			emailType="收";
		}if(fromType=="1" && toType=="1"){
			emailType="收发";
		}
	}
	//高级搜索-添加规则
	function addRule(){
		$("#endRule").empty();
		emailstr="";
		orKey=$("#orKey").val();
		andKey=$("#andKey").val();
		notKey=$("#notKey").val();
		timeType=$("#timeType").val();
		kaishiTime =$("#kaishiTime").val();
		jieshuTime = $("#jieshuTime").val();

		//选择的邮件
	    var emails1 = document.getElementsByName("seek_name_1");
	    for(i=0;i<emails1.length;i++){
	        if(emails1[i].checked){
	            if(emailstr==""){
	            	emailstr=emails1[i].value;
	            }else{
	            	emailstr=emailstr+" "+emails1[i].value;
	            }
	        }
	    }
	    var emails2 = document.getElementsByName("seek_name_2");
	    for(i=0;i<emails2.length;i++){
	        if(emails2[i].checked){
	            if(emailstr==""){
	            	emailstr=emails2[i].value;
	            }else{
	            	emailstr=emailstr+" "+emails2[i].value;
	            }
	        }
	    }


		if(orKey!=""){
			var orKeyHtml='  <tr id="1" > '+
							'  <td style="padding-left:25px;">or(包含任意关键词)</td>'+
			                '  <td style="text-align:right;padding-right:10px;">'+orKey+'</td>'+
			                '  <td style="text-align:right;padding-right:5px;"><a class="c09" onclick="emptyKey(1)">移除</a></td>'+
			            '  </tr>';
			$("#endRule").append(orKeyHtml);
		}
		if(andKey!=""){
			var andKeyHtml='  <tr id="2" > '+
							'  <td style="padding-left:25px;">and(包含所有关键词)</td>'+
			                '  <td style="text-align:right;padding-right:10px;">'+andKey+'</td>'+
			                '  <td style="text-align:right;padding-right:5px;"><a class="c09" onclick="emptyKey(2)">移除</a></td>'+
			            '  </tr>';
			$("#endRule").append(andKeyHtml);
		}
		if(notKey!=""){
			var notKeyHtml='  <tr id="3" > '+
							'  <td style="padding-left:25px;">not(不包含这些关键词)</td>'+
			                '  <td style="text-align:right;padding-right:10px;">'+notKey+'</td>'+
			                '  <td style="text-align:right;padding-right:5px;"><a class="c09" onclick="emptyKey(3)">移除</a></td>'+
			            '  </tr>';
			$("#endRule").append(notKeyHtml);
		}
		 if(emailstr!=""){
				var emailKeyHtml='  <tr id="5" > '+
									'  <td style="padding-left:25px;">邮件地址</td>'+
						            '  <td style="text-align:right;padding-right:10px;">'+emailstr+'-'+emailType+'</td>'+
						            '  <td style="text-align:right;padding-right:5px;"><a class="c09" onclick="emptyKey(5)">移除</a></td>'+
						        '  </tr>';
				$("#endRule").append(emailKeyHtml);
			}
		if(kaishiTime!="" || jieshuTime!=""){
			var timeKeyHtml='  <tr id="4" > '+
								'  <td style="padding-left:25px;">日期</td>'+
					            '  <td style="text-align:right;padding-right:10px;">'+kaishiTime+' '+jieshuTime+' '+timeType+'</td>'+
					            '  <td style="text-align:right;padding-right:5px;"><a class="c09" onclick="emptyKey(4)">移除</a></td>'+
					        '  </tr>';
			$("#endRule").append(timeKeyHtml);
		}



	}
	//高级搜素-移除
	function emptyKey(type){
		$("#"+type).remove();
		if(type==1){
			orKey="";
			$("#orKey").val("");
		}
		if(type==2){
			andKey="";
			$("#andKey").val("");
		}
		if(type==3){
			notKey="";
			$("#notKey").val("");
		}
		if(type==4){
			timeType="";
			kaishiTime="";
			jieshuTime="";
			$("#timeType").val("之间");
			$("#kaishiTime").val("");
			$("#jieshuTime").val("");
		}
		if(type==5){
			fromType="0";
			toType="0";
			emailstr="";
			seekEmail();
		}
	}
	//高级搜素-清空
	function emptyAllKey(){
		orKey="";
		andKey="";
		notKey="";
		timeType="";
		kaishiTime="";
		jieshuTime="";
		fromType="0";
		toType="0";
		emailstr="";

		$("#endRule").empty();
		$("#orKey").val("");
		$("#andKey").val("");
		$("#notKey").val("");
		$("#timeType").val("之间");
		$("#kaishiTime").val("");
		$("#jieshuTime").val("");
		seekEmail();

	}
	//高级搜素-匹配规则
	function setMatchingType(type){
		if(type==1){
			matchingType="1";
		}else{
			matchingType="2";
		}
	}
	//高级搜素-搜索
	function highSeek(){
		if(type == 2){
			getEmailListtofromOfD2(1);//收发件分析的单独的表格加载方法
		}else{
			showAllemail(1,zhengzeType);	//电子邮件数据类型列表加载
		}
	}
	 /*选中背景色*/
    $(".js-bg li").click(function(){
        $(".js-bg li").removeClass("selected");
        $(this).addClass("selected");
    })
     $(".js-table tr").click(function(){
        $(".js-table tr").removeClass("selected");
        $(this).addClass("selected");
    })

  /**
  * 说明：原形checkbox事件加载
  * @method initCheckbox
  * @param undefined
  * @return undefined
  */
$(function(){
      initCheckbox();
    });
function initCheckbox(){
  $temp = $(".js-checkbox");
  $temp.unbind("click");
  $temp.click(function(){
    var $cur = $(this);
    var sameRadio = $cur.attr("name");
    if( $cur.hasClass("actived") ){
      $cur.removeClass("actived");
      $cur.siblings("input[type='checkbox']").get(0).checked = false;

    }else{

      if(sameRadio != undefined && sameRadio != ""){
        $.each($(".js-checkbox[name='"+sameRadio+"']"), function(){
          var _i = $(this);
          _i.siblings("input[type='checkbox']").get(0).checked = false;
          _i.removeClass("actived");
        });
      }
      $cur.addClass("actived");
      $cur.siblings("input[type='checkbox']").get(0).checked = true;

    }

  })
}

	var type=1;//table选择类型
    var getHistory = "";

    $(document).click(function () {
        hideInfo();
    });

    $('#emailKeyword').click(function (e) {
        e.stopPropagation();// 阻止全局事件
        getCookie();
    });

	function d1() {//电子邮箱工作台table
        $("#d1").show();
        $("#d0").show();
        $("#d00").hide();
        $("#d01").show();
        $("#d2").hide();
        $("#d3").hide();
        $("#d4").hide();
        $("#d5").hide();
        $("#d6").hide();
        showAllemail(1,zhengzeType);	//电子邮件数据类型列表加载
        countType();//快速标记获取条数 
        type=1;
    }

    function d2() {//收发件分析table
        $("#d1").hide();
        $("#d3").hide();
        $("#d4").hide();
        $("#d5").hide();
        //$("#d0").show();
        $("#d0").hide();
        $("#d00").show();
        $("#d6").hide();
        $("#d2").css("display", "block");
      //  show();//表格显示发件人排序
        //addresser();//收件人排序
        showEmailDetil();//收发件次数过滤分析统计次数
        getEmailListtofromOfD2(1);	//电子邮件数据类型列表加载 
        type=2;
    }

   /*  function d3() {//邮箱关系图
        $("#d1").hide();
        $("#d2").hide();
        $("#d4").hide();
        $("#d5").hide();
        $("#d6").hide();
        $("#d0").show();
        $("#d00").hide();
        $("#d01").hide();
        $("#d3").css("display", "block");
        contactsAnalyze();//联系人分析插件
        getAllemail();//点击搜索
        type=3;

       // contactsAnalyze2();//嫌疑人关系图
    } */
    function d5() {//嫌疑人关系图table
        $("#d1").hide();
        $("#d2").hide();
        $("#d4").hide();
        $("#d3").hide();
        $("#d6").hide();
        $("#d0").show();
        $("#d00").hide();
        $("#d01").hide();
        $("#d5").css("display", "block");
        showAllemail(1,zhengzeType);	//电子邮件数据类型列表加载
		contactsAnalyze2();//嫌疑人关系图
        type=5;

    }
    function d4() {//域名分析table
    	$("#d0").hide();
    	$("#d00").hide();
        $("#d1").hide();
        $("#d2").hide();
        $("#d3").hide();
        $("#d5").hide();
        $("#d6").hide();
        $("#d4").css("display", "block");
    	showAllemail_div4(1,"");//域名搜索邮件list表格
    	getIpAddress();//调用域名方法
    	showDomain();//获取域名分析的table展示信息 
        type=4;
    }
    function d6() {//时间线table
        $("#d1").hide();
        $("#d2").hide();
        $("#d3").hide();
        $("#d5").hide();
        $("#d4").hide();
        $("#d0").show();
        $("#d01").hide();
        $("#d00").hide();
        $("#d6").show();
		showAllemail(1,zhengzeType);	//电子邮件数据类型列表加载 
		shijianxian();//时间线插件
        type=6;
    }
/*     function selectAll(){
		var ckall=document.getElementById("ckall");
	    var ck=document.getElementsByName("ids");
	    for(var i=0;i<ck.length;i++){
	    	if(ck[i].checked==false){
	    	ck[i].checked=ckall.checked;
	    	selectCase(i);
	    	}
		}
	   if($("input[name='ids']:checked").length <= $("input[name='ids']").length && ckall.checked==false){
	    	for(var j=0;j<ck.length;j++){
	    		if(ck[j].checked==true){
	    		ck[j].checked=false;
	    		selectCase(j);
	    	}
		 }
    }

} */
    <%-- function selectCase(idi, obj){
    	caseidStr="";
    	/* 复选框的单选 */
		$('input[name=ids]').each(function(key, item){
			console.log('item', item);
			if (item !== obj) {
				$(item).attr('checked', false);
				var inputid=item.id;
				inputid=inputid.substring(8,inputid.length);
				var ids_call2=document.getElementsByName("ids_call"+inputid);
				for(var i=0;i<ids_call2.length;i++){
			    	ids_call2[i].checked=false;
				} 
			} else {
				$(this).attr('checked', true);
			}
			
			
		})
    	$("#spans").html("");
    	
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

	} --%>
    
    /* 选择案件显示邮箱 */
    var caseidStr = "";
	var suspectPhoneStr = "";
	//获取域名分析的table展示信息
	function showDomain(){
		var startDate = $("#startDate_demain").val();
		var endDate = $("#endDate_demain").val();
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/getEmail_domain.php",
			data : {
				"caseidStr":caseidStr,
				"startDate":startDate,
				"endDate":endDate,
                "XYRemail":XYRemail,
                "emlType":emlType
			},
			dataType : "json",
			async: true,
			success : function(data) {
				getdomain();
				$("#tbcont3").empty();
                $("#getDomainInfo").empty();
				for(i = 0;i<data.beanStr.length;i++) {
					var html01 = '+<tr>' +
                    '<td style="text-align: left">' + data.beanStr[i].type + '</td> ' +
                    '<td></td> ' +
                    '<td style="text-align: right">' + data.beanStr[i].num + '次'+ '</td>' +
                    '</tr>+';
                	$("#getDomainInfo").append(html01);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert("失败"); */
			}
		});
	}

 	function showChoose(id){
		 var ckall=document.getElementById(id);
	    	//ck[i].checked=ckall.checked;
	}
	//点击更改案件按钮弹出 显示案件标号和案件名称
//	window.onload = panduan();

	$(function panduan(){
		if(caseidyoujian%1 === 0){
			 caseidStr = caseidyoujian;
			getAllemail();//点击搜索
            var html ='<span class="br05 b12 span-1" id="shanchu2'+caseidStr+'" style="width: 150px; height: 30px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;margin-right: 15px;margin-top: 5px;" onclick="deletemainParty(this.id,'+caseidStr+')">'+casenameEmail+
                '<img style="vertical-align:middle;" src="<%=basePath%>template/img/x.png" /></span><span id="suspectnum'+caseidStr+'" style=" display:none;"></span>';
            $("#spans").append(html);

			getAllemail();
		}
	});

	window.onload = showcase(); 	//默认加载案件列表    
	window.onload = d1();
	window.onload = showAllemail(1,zhengzeType);	//电子邮件数据类型列表加载
	window.onload = showDomain();//获取域名分析的table展示信息
	//window.onload = getEmailListtofromOfD2(1);//收发件分析的单独的表格加载方法

	var INIT_CASEID;//默认的第一个案件ID，用于初始化每个页面的初始数据
	var SUS_MAP;//疑似居住地默认第一个嫌疑人手机号
	var XYRemail = "";//全局的嫌疑人邮箱，为了只分析这个邮箱
	var XYRID= "";//全局的嫌疑人id，为了传给嫌疑人关系图

<%-- 	    //选择嫌疑人
	function selectSusp(a,b,call,mainPartyId,obj){
		if(obj.checked){//选择
			if(XYRemail==""){
				XYRemail=call;
			}else{
				XYRemail+=","+call;
			}
			if(XYRID==""){
				XYRID=mainPartyId;
			}else{
				XYRID+=","+mainPartyId;
			}
		}else{//取消选择 
			
			var XYRemailList=XYRemail.split(",");
			var XYRemailNew="";
			if(XYRemailList.length>0){
				for(var i=0;i<XYRemailList.length;i++){
					var par = XYRemailList[i];
					if(par!=call){
						if(XYRemailNew==""){
							XYRemailNew=par;
						}else{
							XYRemailNew=XYRemailNew+","+par;
						}
					}
				}
			}
			XYRemail=XYRemailNew;	
			var	tempXYRID = XYRID+"";//单个嫌疑人id输出为number，在此转换为string
			
			var XYRIDList = new Array();
			if(tempXYRID.indexOf(",")!=-1){
				XYRIDList = tempXYRID.split(",");
			}else{
				XYRIDList[0] = tempXYRID;
			}
			
			//var XYRIDList=XYRID.split(",");
			var XYRIDNew="";
			if(XYRIDList.length>0){
				for(var i=0;i<XYRIDList.length;i++){
					var par = XYRIDList[i];
					if(par!=mainPartyId){
						if(XYRIDNew==""){
							XYRIDNew=par;
						}else{
							XYRIDNew=XYRIDNew+","+par;
						}
					}
				}
			}
			XYRID=XYRIDNew;
			
		}
		
		
		var xz=0;//案件下是否有选嫌疑人  0 无  1 有
		var ckall=document.getElementById("caselist"+a);
		var ids_call=document.getElementsByName("ids_call"+a);
		for(var i=0;i<ids_call.length;i++){
			if(ids_call[i].checked){
				xz+=1;
			}
		}
		 var caseInfo = document.getElementById("caselist"+a);
		 var caseId=caseInfo.value.split(" ")[1];
		 var caseName=caseInfo.value.split(" ")[0];
		if(xz>0){
			ckall.checked=true;
				 if(caseidStr==""){
	           	caseidStr=caseId;
	           	if(caseName.length>6){
					caseName = caseName.substring(0,6) + "...";
				}
				var html ='<span class="br05 b12 span-1" id="shanchu2'+caseId+'" style="width: 150px; height: 30px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;margin-right: 15px;margin-top: 5px;" onclick="deletemainParty(this.id,'+caseId+')">'+caseName+
				'<img style="vertical-align:middle;" src="<%=basePath%>template/img/x.png" /></span><span id="suspectnum'+caseId+'" style=" display:none;">110</span>';
				$("#spans").append(html);
	           }
				
		}else{
			ckall.checked=false;
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
				 XYRemail = "";//清空案件下的 嫌疑人邮箱
				 XYRID= "";//清空案件下的 嫌疑人id
		}
		
	} --%>

	function showcase(){//选择案件，显示案件列表
	//	var casenumorname = document.getElementById("caseinfo").value;
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/getCaseOfEmail.php",
			data : {
			//	"casenumorname":casenumorname
			},
			dataType : "json",
			async: true,
			success : function(data) {
				
				var caseidStrSplit=caseidStr.split(" ");
				var	tempXYRID = XYRID+"";//单个嫌疑人id输出为number，在此转换为string
				
				var XYRIDSplit = new Array();
				if(tempXYRID.indexOf(",")!=-1){
					XYRIDSplit = tempXYRID.split(",");
				}else{
					XYRIDSplit[0] = tempXYRID;
				}
				var html="";
				$.each(data,function(i,item){
					//复制话单开头
					var mainParty1 = item.mainParty
					var mainParty2 = mainParty1.split("/");
					var xyrtotal = item.remark;
					var htmlMainParty="";
					caseId=item.id;
					if(mainParty2.length>0){
						$.each(mainParty2,function(j,item){
							var mainParty4 = item.split(" ");
							var mainPartyName = mainParty4[0];
							var mainPartyCall = mainParty4[2];
							var mainPartyId = mainParty4[3];
							if(SUS_MAP=="")
								SUS_MAP=mainPartyCall;
											var mainPartyCall2 ="'"+mainPartyCall+"'";
											
												htmlMainParty+='<div class="other">'+
							                        '<div style="position:relative;padding-left: 27px;">'+
						                          '<p class="js-else" title="'+mainPartyCall+'"><i class="arrowR02"></i>嫌疑人："'+mainPartyName+'"("'+mainPartyCall+'")</p><input id="checkboxi'+i+'j'+j+'" type="checkbox" value="'+mainPartyCall+'" class="aline" onclick="iselect('+i+','+j+','+caseId+',this);">'+
						                        '</div>'+
						                        '<div class="next mhide">'+
						                            '<div style="position:relative;"><a onclick="showXYREmail('+caseId+','+mainPartyCall2+','+"'收件箱'"+');"><img src="<%=basePath%>template/img/is.png">收件箱</a><input class="checka" name="idsi'+i+'j'+j+'" type="checkbox" onclick="selectNone('+i+','+j+',this);"></div>'+
						                            '<div style="position:relative;"><a onclick="showXYREmail('+caseId+','+mainPartyCall2+','+"'发件箱'"+')"><img src="<%=basePath%>template/img/ifs.png">发件箱</a><input class="checka" name="idsi'+i+'j'+j+'" type="checkbox" onclick="selectNone('+i+','+j+',this);"></div>'+
						                        '</div>'+
						                    '</div>';
										
							
						});
					}
					var ckall=document.getElementById("ckall");
				    var ck=document.getElementsByName("ids");
				  
					if(INIT_CASEID==""){
						INIT_CASEID=item.id;
					}
					if(xyrtotal!=0){
						html += '<li>'+
						'<p class="list-name js-enterprise-aside-btn">'+item.caseName+'<span>('+xyrtotal+')</span><i class="arrow-r1"></i></p>'+
				        '<div class="nav hide">'+htmlMainParty+'</div>'+
				        '</li>';
					}else{
						html += '<li>'+
						'<p class="list-name js-enterprise-aside-btn">'+item.caseName+'<span>('+xyrtotal+')</span><i class="arrow-r1"></i></p>'+
				        '<div class="nav hide"></div>'+
				        '</li>';
					}
					
				});
				$("#tbcont").append(html);
				
				$(function(){
					  initEnterpriseAside();
					  initEnterpriseAside1();
					 
					});
				
				/*选中样式*/
				$(".other .next a").click(function(){
				  $(".other .next a").removeClass("aselected");
				  $(this).addClass("aselected");
				})
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	var emlType = "";
	function showXYREmail(caseID,xyrEamil,emailType){
	    XYRemail = xyrEamil;
	    
		caseidStr = caseID;
		emlType = emailType;
		 if(type==1){//电子邮件工作台table
		    	showAllemail(1,zhengzeType);	//电子邮件数据类型列表加载
		        countType();//快速标记获取条数
		        $(".c02 b27 yuan").text("0");
		    }else if(type==2){//收发件分析table
		    	 show();//表格显示发件人排序
		         addresser();//收件人排序
		         showEmailDetil();//收发件次数过滤分析统计次数
		         getEmailListtofromOfD2(1);	//电子邮件数据类型列表加载
		    }/*  else if(type==3){//邮箱关系图
		    	shijianxian();//时间线插件
		    	showEmailDetil();//收发件次数过滤分析统计次数
		    	showAllemail(1,"");	//电子邮件数据类型列表加载
		    	contactsAnalyze();//联系人分析插件
		    	getAllemail();//点击搜索
		    	getEmailListtofromOfD2(1);//收发件分析的单独的表格加载方法
		    	showAllemail_div4(1,"");//域名搜索邮件list表格
		    }  */else if(type==4){//域名分析table
		    	showAllemail_div4(1,"");//域名搜索邮件list表格
		    	getIpAddress();//调用域名方法
		    	showDomain();//获取域名分析的table展示信息
			}else if(type==5){//嫌疑人关系图table
				showAllemail(1,zhengzeType);	//电子邮件数据类型列表加载
				contactsAnalyze2();//嫌疑人关系图
			}else if(type==6){//时间线table
				showAllemail(1,zhengzeType);	//电子邮件数据类型列表加载
				shijianxian();//时间线插件
			}
	}
	
	

	
	/* 案件列表复选框事件 */
	
       /*  function iselect(a,b,caseid,obj){//其中函数字不能为select 其为JS保留字 					
            var ids = document.getElementsByName("idsi"+a+"j"+b);                            
            var all = document.getElementById("checkboxi"+a+"j"+b);
          //只选择一个嫌疑人    
          caseidStr = caseid;
          var eml = all.value;
			$(".aline").each(function(key, item){
				if (item !== obj){
					$(item).attr('checked', false);
					$(".checka").attr('checked', false);
				
				} else {
					$(this).attr('checked', true);
				
				}
			})
			
          //全选
            for(var i=0;i<ids.length;i++){
                ids[i].checked=all.checked;     
            }
      
		 	if(obj.checked){
 				if(XYRemail==undefined){
					XYRemail = eml;
				}else{
					XYRemail =XYRemail+","+eml;
				} 
			}else{
					var emls = XYRemail.split(",");
			var emlss="";
			if(emls.length>1){
					for(var i=0;i<emls.length;i++){
						var par = emls[i];
						 		if(par!=eml){
							if(emlss==""){
								emlss=par;
							}else{
								emlss=emlss+","+par;
							}
						}
					} 
					XYRemail=emlss;
				} 
				else{
					XYRemail="";
				}
			}
			
			//alert(XYRemail);
			 show();//表格显示发件人排序
	         addresser();//收件人排序
	         showEmailDetil();//收发件次数过滤分析统计次数
	         getEmailListtofromOfD2(1);	//电子邮件数据类型列表加载
        } */
	
	
	var suspect=0;
	function shuoSuspect(i){

		if(suspect==0){
			 var nodes = document.getElementsByName("caseSuspect"+i);

			 for(j=0;j<nodes.length;j++){
				 nodes[j].style.display="";

			 }
	      //  node.style.display=""; */
	        var imgRotate = document.getElementById("imgRotate"+i);
	        imgRotate.style.transform="rotate(90deg)";

	        suspect=1;
		}else{
			 var nodes = document.getElementsByName("caseSuspect"+i);

			 for(j=0;j<nodes.length;j++){
				 nodes[j].style.display="none";

			 }
	        var imgRotate = document.getElementById("imgRotate"+i);
	        imgRotate.style.transform="";
	        suspect=0;
		}

	}

	<%-- //选择某个复选框案件将案件信息展示在页面上
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
			var html ='<span class="br05 b12 span-1" id="shanchu2'+caseId+'" style="width: 150px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;height: 30px;margin-right: 15px;margin-top: 5px;" onclick="deletemainParty(this.id,'+caseId+')">'+caseName+
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
	} --%>
	//选择按钮
	// function checkCase(obj){//选择按钮
	// 	$("#caseinfo").val("");
	// 	$("#startDate_demain_email").val("");//开始时间框设置 为空
	// 	startTime = "";//开始时间设置 为空
	// 	$("#endDate_demain_email").val("");//结束时间框设置 为空
	// 	endTime = "";//结束时间设置 为空
	// 	$("#emailKeyword").val(""); //关键词文本框设置 为空
	// 	emailKeyword = "";//关键词设置 为空
	// 	$(".infor").remove();//多条件框清空
	// 	$(".js-all").css("display","none");//清空筛选隐藏
	//     $("#myModal").modal("hide");
	//     $("#caseinfo").val("");
	//     $(".c02 b27 yuan").text("0");
	//     if(type==1){//电子邮件工作台table
	//     	showAllemail(1,"");	//电子邮件数据类型列表加载
	//         countType();//快速标记获取条数
	//         $(".c02 b27 yuan").text("0");
	//     }else if(type==2){//收发件分析table
	//     	 show();//表格显示发件人排序
	//          addresser();//收件人排序
	//          showEmailDetil();//收发件次数过滤分析统计次数
	//          getEmailListtofromOfD2(1);	//电子邮件数据类型列表加载
	//     }/*  else if(type==3){//邮箱关系图
	//     	shijianxian();//时间线插件
	//     	showEmailDetil();//收发件次数过滤分析统计次数
	//     	showAllemail(1,"");	//电子邮件数据类型列表加载
	//     	contactsAnalyze();//联系人分析插件
	//     	getAllemail();//点击搜索
	//     	getEmailListtofromOfD2(1);//收发件分析的单独的表格加载方法
	//     	showAllemail_div4(1,"");//域名搜索邮件list表格
	//     }  */else if(type==4){//域名分析table
	//     	showAllemail_div4(1,"");//域名搜索邮件list表格
	//     	getIpAddress();//调用域名方法
	//     	showDomain();//获取域名分析的table展示信息
	// 	}else if(type==5){//嫌疑人关系图table
	// 		showAllemail(1,"");	//电子邮件数据类型列表加载
	// 		contactsAnalyze2();//嫌疑人关系图
	// 	}else if(type==6){//时间线table
	// 		shijianxian();//时间线插件
	// 		showAllemail(1,"");	//电子邮件数据类型列表加载
	// 	}
	//
	// }
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
		showAllemail(1,"");
	    if(type==1){
	    	$(".c02 b27 yuan").text("0");
	    	showAllemail(1,"");
	    }else if(type==2){
	    	 show();//表格显示发件人排序
	         addresser();//收件人排序
	         //getAllemail();//点击搜索
	         getEmailListtofromOfD2(1,"");//收发件分析的单独的表格加载方法
	    	/* showEmailDetil();//收发件次数过滤分析统计次数
	    	showAllemail(1,""); */
	    } else if(type==3){
	    	contactsAnalyze();//联系人分析插件
	    } else if(type==4){
	    	showAllemail_div4(1,"");//域名搜索邮件list表格
	    	getIpAddress();//调用域名方法
	    	showDomain();//获取域名分析的table展示信息
		}else if(type==5){//嫌疑人关系图table
			contactsAnalyze2();//嫌疑人关系图加载
		}else if(type==6){
			shijianxian();//时间线插件
			showAllemail(1,"");
		}
	}
	 -->
	//搜索邮件list     第一个tab的搜索框取值

	var read000 = 0;
	var startTime = "";
	var endTime = "";

	//点击搜索
	function getAllemail(){
        var setCookie = $("#emailKeyword").val();
        var getInputItem = setCookie + ",";
		startTime = $("#startDate_demain_email").val();
		endTime = $("#endDate_demain_email").val();
		var emailKey = $("#emailKeyword").val();
		 //多条件筛选内容加载
		var filtratecontent =
			'<p class="infor">'+
			'<span id="screen">'+emailKey+'</span>'+
			'<a class="js-closed"><img src="<%=basePath%>/template/img/gb.png"></a>'+
		'</p>';
		if(emailKey != ''){
			if($(".infor").length <5){
				var screensemailKeyword = emailKeyword.split(" ");//取空格筛选条件内容
				var flag =0;
				for(var i=0;i<screensemailKeyword.length;i++){
					if(emailKey==screensemailKeyword[i]){
						flag=1;
					}
				}
				if(flag!=1){
					if(emailKeyword==""){
						emailKeyword=$("#emailKeyword").val();
					}else{
						emailKeyword += " "+$("#emailKeyword").val();
					}
					$("#filtrate").append(filtratecontent);
					$(".js-all").css("display","inline-block");
				}else{
					alert("条件已存在!");
				}
			}else{
				alert("条件爆满了!");
			}
			showAllemail(1,zhengzeType);//搜索框有值就走查询邮件方法
		}else if(emailKeyword == emailKeyword){$(".js-all").css("display","none");
			showAllemail(1,zhengzeType);//搜索框无值走查询邮件方法
		}
	
		$(".js-closed").click(function(){
			var infor = $(this).parent();
			var screen = $(this).prev().text();//单个筛选条件内容
			var screensplit = emailKeyword.split(" ");//取空格筛选条件内容
			var emailKeyword2="";
			 if(screensplit.length!=0){
				for(var i=0;i<screensplit.length;i++){
					var par = screensplit[i];
					if(par!=screen){
						if(emailKeyword2 == ""){
							emailKeyword2 = par;
						}else{
							emailKeyword2 = emailKeyword2+" "+par;
						}
					}
				}
			}
			emailKeyword = emailKeyword2;
		    showAllemail(1,zhengzeType); 
			infor.remove();
			});
		$(".js-all").click(function(){
			$(".infor").remove();
			$(this).css("display","none");
			emailKeyword = "";
			showAllemail(1,zhengzeType); 	//电子邮件数据类型列表加载
			});
            if (getHistory.indexOf(getInputItem) < 0) {
                getHistory += setCookie + ",";
                document.cookie = escape(getHistory);
			} else {
                getHistory = getHistory.replace(getInputItem, "");
                getHistory += getInputItem;
                document.cookie = escape(getHistory);
			}
	}

    function getCookie() {
        var getInfo = document.cookie;
        getInfo = unescape(getInfo);
        getInfo = getInfo.substring(0, getInfo.length-1);
        if(getInfo != "undefined") {
            $("#con").css("display", "inline-block");
            var everyHistoryInfo = new Array();
            everyHistoryInfo = getInfo.split(",");

            var con = "";
            var getEveryHistoryInfo = "";
            for(var i=everyHistoryInfo.length-1; i >= 0; i--) {
            	if(i>(everyHistoryInfo.length-11)){
            		getEveryHistoryInfo = everyHistoryInfo[i];
                    var getKeyWord = $("#emailKeyword").val();
                    if (everyHistoryInfo[i].indexOf(getKeyWord) >= 0) {
                        con += "<li onclick='getSelectInfo("+i+")' id='liInfo"+i+"'>"+everyHistoryInfo[i]+"</li>";
                    }
            	}
            }
            $("#con").html(con);
        }
    }

    function hideInfo() {
        $("#con").css("display","none");
    }

    function getSelectInfo(i) {
        var getLiValue = $("#liInfo" + i).text();
        $("#emailKeyword").val(getLiValue);
        $("#con").css("display","none");
    }

	var allcount ;
	//电子邮件数据类型列表加载
	function showAllemail(pageno,type){
		$("#emailKeyword").val("");
		zhengzeType=type;
		if(type=="" || type==null){
			type="";
		}
		var test = $("spans").val();
		$("#emailtou").empty();
		var sortType = $("#sortType").val();
		var guanjianci=$("#guanjianci").val();
		emailKeyword3 = $("#suspectsName_d3").val();
		/* emailKeyword = $("#emailKeyword").val();
		startTime = $("#startDate_demain_email").val();
		endTime = $("#endDate_demain_email").val(); */
		$("#email").empty();
		var html0 = '<table class="c20 br04" style=" width: 1600px; height: 90px" >'+
		'<tr>'+
						'</tr>'+
						'<tr>'+
					'</tr>'+
						'<tr>'+
							'<td>正在搜索中...</td>'+
						'</tr>'+
					'</table>';
		$("#email").append(html0);
		var emailurl="<%=basePath%>emaiExcavatel/getEmailList.php";
		if(type!=null && type!=""){//快速标记
			emailurl="<%=basePath%>/quickFlagsType.php";
		}

		var givePages_emailsWork = $("#givePages_emailsWork").val();
    	if(givePages_emailsWork != ""){
    		pageno = parseInt(givePages_emailsWork);
    	}
		$.ajax({
			type : "POST",
			url : emailurl,
			data : {
				"pageIndex":pageno,
				"caseidStr":caseidStr,
				"sortType":sortType,
				"emailKeyword":emailKeyword,
				"emailKeyword3":emailKeyword3,
				"guanjianci":guanjianci,
				"startDate":startTime,
				"endDate":endTime,
				"regexpQuery":type,
				"orKey":orKey,
				"andKey":andKey,
				"notKey":notKey,
				"timeType":timeType,
				"kaishiTime":kaishiTime,
				"jieshuTime":jieshuTime,
				"matchingType":matchingType,
				"emailstr":emailstr,
				"emailType":emailType,
				"XYRemail":XYRemail,
				"emlType":emlType
			},
			dataType : "json",
			async: false,
			success : function(data) {
				var type2="'"+type+"'";
			    var total = data.count;
			    allcount=total;
			    var read00=data.read0;
			    //分页开始
			    var sizes=10;
				var pagesum=total;
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
					var html2 = '<li class="active" id="orderA_5"><a href="#" onclick="showAllemail(1,'+type2+')">1</a></li >';
					$("#pages_d5").after(html2);
					
				}else{
				if(pageno<pagenum){
					if(pageno+length-1<=pagenum){
						var html2="";
						if(pageno-2>0){
							for(var i =pageno-2;i<=pageno+length-1-2;i++){
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="showAllemail('+i+','+type2+')">'+i+'</a></li >';
									else
										html2 += '<li id="orderA_5"><a href="javascript:void(0)" onclick="showAllemail('+i+','+type2+')">'+i+'</a></li >';
						   				   }
							}
						else{
							for(var i =1;i<=length;i++){
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="showAllemail('+i+','+type2+')">'+i+'</a></li >';
									else

										html2 += '<li id="orderA_5"><a href="javascript:void(0)" onclick="showAllemail('+i+','+type2+')">'+i+'</a></li >';
						   				   }
						}

					$("#pages_d5").after(html2);
					
					}else{
						var html2="";
						if(pagenum>=length){
							for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="showAllemail('+i+','+type2+')">'+i+'</a></li >';
								else
									html2 += '<li id="orderA_5"><a href="javascript:void(0)" onclick="showAllemail('+i+','+type2+')">'+i+'</a></li >';
	
							}
							$("#pages_d5").after(html2);
						}else{
							for(var i =1;i<=pagenum;i++){
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="showAllemail('+i+','+type2+')">'+i+'</a></li >';
								else
									html2 += '<li id="orderA_5"><a href="javascript:void(0)" onclick="showAllemail('+i+','+type2+')">'+i+'</a></li >';
							   }
							$("#pages_d5").after(html2);
						}
					}
				}else{
					if(pageno==pagenum){
						var html2="";
						for(var i =pageno-length+1>0?pageno-length+1:1;i<=pagenum;i++){
							if(i==pageno)
								html2 += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="showAllemail('+i+','+type2+')">'+i+'</a></li >';
							else
								html2 += '<li id="orderA_5"><a href="javascript:void(0)" onclick="showAllemail('+i+','+type2+')">'+i+'</a></li >';
					   }
						$("#pages_d5").after(html2);
					}/* if */
				}
			}
				$("#tot1_d5").empty();
				$("#pages1_d5").empty();
				$("#pages2_d5").empty();
				$("#btn_emailsWork").empty();
				var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
				$("#tot1_d5").append(html3);
				var html5 = '<a href="javascript:void(0)" onclick="showAllemail('+pageno+'-1<1?1:'+pageno+'-1,'+type2+')"><</a>';
				$("#pages1_d5").append(html5);

				var html4 = '<a href="javascript:void(0)" onclick="showAllemail('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1,'+type2+')">></a>';
				$("#pages2_d5").append(html4);
				//跳转
				var html5 ='<button id="btn_emailsWork" type="button" class="btn" onclick="showAllemail('+pageno+','+type2+')" style="width: 52px; height: 28px; line-height: 12px;">跳转</button>';
				$("#btn_emailsWork").append(html5);
				//分页结束
			

				if("手机号"==type){
					$("#Phone").text(total<99 ? total:99+"+");
				}else if("银行卡号"==type){
					$("#regCard").text(total<99 ? total:99+"+");
				}else if("集装箱号"==type){
					$("#regContainer").text(total<99 ? total:99+"+");
				}else if("邮箱号"==type){
					$("#regEmail").text(total<99 ? total:99+"+");
				}else if("发票代码"==type){
					$("#regStamp").text(total<99 ? total:99+"+");
				}else if("车牌号"==type){
					$("#regLicense").text(total<99 ? total:99+"+");
				}else if("身份证号"==type){
					$("#regSFZ").text(total<99 ? total:99+"+");
				}else if("GPS"==type){
					$("#regGPS").text(total<99 ? total:99+"+");
				}else if("QQ号"==type){
					$("#QQnumber").text(total<99 ? total:99+"+");
				}else if("微信"==type){
					$("#weChat").text(total<99 ? total:99+"+");
				}else if("推特号"==type){
					$("#twitter").text(total<99 ? total:99+"+");
				}else if("护照编号"==type){
					$("#passport").text(total<99 ? total:99+"+");
				}else if("支付宝号"==type){
					$("#zfb").text(total<99 ? total:99+"+");
				}else if("固定电话"==type){
					$("#regTel").text(total<99 ? total:99+"+");
				}else if("信用证"==type){
					$("#regLC").text(total<99 ? total:99+"+");
				}else if("运输车号"==type){
					$("#regModel").text(total<99 ? total:99+"+");
				}else if("价格"==type){
					$("#regPrice").text(total<99 ? total:99+"+");
				}else if("提单号"==type){
					$("#regBill").text(total<99 ? total:99+"+");
				}else if("电汇"==type){
					$("#regTT").text(total<99 ? total:99+"+");
				}else if("装箱单"==type){
					$("#regPacking").text(total<99 ? total:99+"+");
				}else if("合同编号"==type){
					$("#regContract").text(total<99 ? total:99+"+");
				}
				$("#email").empty();
				$("#emailtou").empty();
				var htmltou ='<tr>'+
								'<th class="alcenter" style="text-align:center;width:70px;">收藏</th>'+
								'<th class="alcenter" style="text-align:center;width:88px;">协同分析</th>'+
								'<th class="alcenter">邮件主题</th>'+
								'<th class="alcenter">发件人</th>'+
								'<th class="alcenter">收件人</th>'+
								'<th class="alcenter">发送日期</th>'+
								'<th class="alcenter" style=" text-align:center; ">附件</th>'+
							'</tr>';

				$("#emailtou").append(htmltou);

				read000=0;
				var sumi=0;
				var emailDTOList = data.emailDTOList;
				 $.each(emailDTOList,function(i,item){
					var towho =item.toWho;//收件人
					//if(towho!=null){
						//var towhos = towho.split(";;");
						//var towhonum = towhos.length;
						//if(towhonum>1){
						//	towho=towhos[0]+"...";
						//}
					//}
					var fujian =item.attachmentname;
					
					var fuj ="";
					//有附件显示
					if(fujian.length>0){
						fuj ='src="<%=basePath%>template/img/fujian.png"';
					}

					var star;
					var star2=item.star;
					if(star2==0){
						star="star2";
					}if(star2==1){
						star="star";
					}
					var sub = item.subject;
					//if(sub.length > 26 ){
						//sub=sub.substring(0,26)+"...";
					//}
					if(emailKeyword!="" && emailKeyword!=null){
						var keylist=emailKeyword.split(" ");
						for(var n =0;n<keylist.length;n++){
							sub=sub.replace(keylist[n], "<font style='color: red;background-color: yellow;'>" + keylist[n] + "</font>");
						}
						//sub=sub.replace(emailKeyword, "<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
					}


					if(orKey!=null && orKey!=""){
						var orKeylist=orKey.split(" ");
						for(var n =0;n<orKeylist.length;n++){
							sub=sub.replace(orKeylist[n], "<font style='color: red;background-color: yellow;'>" + orKeylist[n] + "</font>");
						}
					}
					if(andKey!=null && andKey!=""){
						var andKeylist=andKey.split(" ");
						for(var n =0;n<andKeylist.length;n++){
							sub=sub.replace(andKeylist[n], "<font style='color: red;background-color: yellow;'>" + andKeylist[n] + "</font>");
						}
					}

					var fromstr=item.fromWho;
					var tostr=item.toWho;
					if(emailstr!=null && emailstr!=""){
						var emaillist=emailstr.split(" ");
						for(var n =0;n<emaillist.length;n++){
							fromstr=fromstr.replace(emaillist[n], "<font style='color: red;background-color: yellow;'>" + emaillist[n] + "</font>");
							tostr=tostr.replace(emaillist[n], "<font style='color: red;background-color: yellow;'>" + emaillist[n] + "</font>");
							towho=towho.replace(emaillist[n], "<font style='color: red;background-color: yellow;'>" + emaillist[n] + "</font>");
						}
					}
					var read;
					var read2=item.read;
					if(read2==0){
						read="read";
						var subjectStyle = '<td id ="emailboldSubject'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:left; vertical-align:middle;font-weight:bold; "><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+sub+'</span></td>';
					}if(read2==1){
						read="unread";
						var subjectStyle = '<td id ="emailboldSubject'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:left; vertical-align:middle; "><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+sub+'</span></td>';
					}
					var html =  '<tr>'+
									'<td style="text-align:center; vertical-align: middle;"><img id ="emailStar'+i+'" data-target="#myModalfavorite" data-toggle="modal" onclick="upemailstar('+i+')" src="<%=basePath%>template/img/'+star+'.png"/></td>'+
									'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:center; vertical-align: middle;"><img id = "read'+i+'" src="<%=basePath%>template/img/'+read+'.png" /></td>'+
									subjectStyle+
									'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:left; vertical-align: middle;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+item.fromWho+'</span></td>'+
									'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:left; vertical-align: middle;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+towho+'</span></td>'+
									'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:left; vertical-align:middle; "><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+item.date+'</span></td>'+
									'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:center; vertical-align:middle; "><img id = "fujian'+i+'" '+fuj+' />'+

									'<div id="emaildetails0'+i+'" style=" display:none;">'+item.esID+'</div>'+
									'<div id="emaildetails1'+i+'" style=" display:none;">'+sub+'</div>'+
									'<div id="emaildetails2'+i+'" style=" display:none;">'+fromstr+'</div>'+
									'<div id="emaildetails3'+i+'" style=" display:none;">'+tostr+'</div>'+
									'<div id="emaildetails4'+i+'" style=" display:none;">'+item.date+'</div>'+
									/* '<div id="emaildetails5'+i+'" style=" display:none;">'+item.content+'</div>'+ */
									'<div id="emaildetails6'+i+'" style=" display:none;">'+item.downloadUrl+'</div>'+
									'<div id="emaildetails7'+i+'" style=" display:none;">'+item.read+'</div>'+
									'<div id="emaildetails8'+i+'" style=" display:none;">'+item.star+'</div>'+

									'<div id="emaildetails10'+i+'" style=" display:none;">'+item.caseID+'</div>'+
									'<div id="emaildetails11'+i+'" style=" display:none;">'+item.caseName+'</div>'+

									'<span id="emaildetails9'+i+'" style=" display:none;">'+item.attachmentname+'</span>'+

									'</td>'+
								'</tr>';
					$("#email").append(html);
					sumi=i+1;
				 });
				//清空高级搜索条件
				emptyAllKey();
				$("#givePages_emailsWork").val("");
				read000=read00;
				$("#emailnum").html("/全部"+total+"封");
				$("#emailsumnum").html("共搜索到" + total + "个结果");
				// $("#emailsumnum").html("共搜索到"+total+"个结果");
				   // if(type=="" || type==null){
				    	$("#email0").html("未读"+read00+"封");
				    //}else{
				    	//$("#email0").html("当前未读"+read00+"封");
				    //}
				 if(sumi==0){

					 $("#email").empty();
					 $("#emailtou").empty();
						var html01 = '<table class="br04 c20" style=" width: 100%; height: 90px;" >'+
											'<tr>'+
										'</tr>'+
										'<tr>'+
									'</tr>'+
										'<tr>'+
											'<td>未找到相关内容</td>'+
										'</tr>'+
									'</table>';
						$("#email").append(html01);
				 }
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	
	function showdetailsD2(i){
		$("#emailcontentD2").html('<div id="loadDivD2" style="text-align: center;margin-top: 20px"> <img alt="" src="<%=basePath %>template/img/loading2.gif"> </div>');
		$("#correctEml_attfileD2").empty();
		var esId = $("#emaildetails0D2"+i+"").html();
		var emailsubject = $("#emaildetails1D2"+i+"").html();//document.getElementById("emaildetails1"+i+"").innerHTML;
		var emailtoWho = $("#emaildetails2D2"+i+"").html();
		var emailfromWho = $("#emaildetails3D2"+i+"").html();
		var emaildate = $("#emaildetails4D2"+i+"").html();
		var emailcontent = $("#emaildetails5D2"+i+"").html();
		var emlpath = $("#emaildetails6D2"+i+"").html();
		var read = $("#emaildetails7D2"+i+"").html();
		
		var star = $("#emaildetails8D2"+i+"").html();
		var attachmentname = $("#emaildetails9D2"+i+"").html();

		var caseIDs = $("#emaildetails10D2"+i+"").html();
		var caseNames = $("#emaildetails11D2"+i+"").html();
		//var emailKeyword = $("#emailKeywordD2").val();
		
		$.ajax({
			type : "POST",
			url : "<%=basePath%>getCorrectEml.php",
			data : {
				"emlpath":emlpath,
				"attachmentname":attachmentname,
				"keyword":emailKeyword,
				"zhengzeType":zhengzeType,
				"orKey":orKey,
				"andKey":andKey
			},
			dataType : "json",
			async: true,
			success : function(data) {
				$("#loadDivD2").hide();
				if(emlpath.substring(emlpath.lastIndexOf("."),emlpath.length)==".eml"){
					
					$("#emailcontentD2").html(data.resData.content);
				}else{
					$("#emailcontentD2").html(emailcontent);
				}
				var len = data.resData.attfile.length;
				
				var i = 0;
				var reshtml = "";
				reshtml += "<div class=\"_modal-mailcontent-append-item\" style=\"padding-right:0px;float:left;\">附件：</div>"
						
				for(i = 0;i <len;i++){
					var aJ = data.resData.attfile[i];
					
					if(aJ.length>6){
						var aJsplit=aJ.substring(0,6)+"..."
					}else{
						var aJsplit=aJ;
					};
					reshtml += "<div class=\"_modal-mailcontent-append-item\" style=\"padding-right:50px;float:left;\">";
					
					  var docType = aJ.substring(aJ.lastIndexOf(".") + 1);
					    
						
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
						if(docType=="jpg"||docType=="JPG"||docType=="png"||docType=="PNG"||docType=="tif"||docType=="TIF"){
							  
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/jpg.png\" /></a>";
						}
						if(docType=="txt"||docType=="TXT"||docType=="log"||docType=="LOG"){
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/txt.png\" /></a>";
						}
						if(docType=="rar"||docType=="RAR"||docType=="zip"||docType=="ZIP"){
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/rar.png\" /></a>";
						}

					reshtml += "<div class=\"_modal-mailcontent-append-item-txt\">"+aJsplit+"</div>";
					reshtml += "</div>";
				}
				if(!attachmentname==""||!attachmentname==null){
					$("#correctEml_attfileD2").html(reshtml);
				}
				return true;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("失败!");
			}
		});

		$("#stariD2").html(i);
		$("#esIdD2").html(esId);
		$("#emailsubjectD2").html("邮件标题 : "+emailsubject);
		$("#emailtoWhoD2").html("发件人 : "+emailtoWho);
		$("#emailfromWhoD2").html("收件人 : "+emailfromWho);
		$("#emaildateD2").html(emaildate);
		//$("#emailcontent").html(content);
		$("#emailurlD2").html(emlpath);
		$("#emailstarD2").html(star);

		$("#caseIDsD2").html(caseIDs);
		$("#caseNamesD2").html(caseNames);
		var star2;
		if(star==0){
			star2="star2";
		}if(star==1){
			star2="star";
		}
		var read2;
		if(read==0){
			read2="unread";
		}if(star==1){
			read2="read";
		}
		
		//判断邮件是否已读

		if(read==0){
			//未读数-1
			read000=read000-1;
			//改变图标
			document.getElementById("readD2"+i).src = "<%=basePath%>template/img/unread.png";
			//修改未读书
			$("#email0D2").html("未读"+read000+"封");
			//修改主题显示
			$("#emailboldSubjectD2"+i).css("font-weight","");
			//修改邮件状态
			upemailstatus(1,esId,emlpath,caseIDs);
		}
		
		
	}
	//点击邮件显示详情
	function showdetails(i){
		$("#emailcontent").html('<div id="loadDiv" style="text-align: center;margin-top: 20px"> <img alt="" src="<%=basePath %>template/img/loading2.gif"> </div>');
		$("#correctEml_attfile").empty();
		var esId = $("#emaildetails0"+i+"").html();
		var emailsubject = $("#emaildetails1"+i+"").html();//document.getElementById("emaildetails1"+i+"").innerHTML;
		var emailtoWho = $("#emaildetails2"+i+"").html();
		var emailfromWho = $("#emaildetails3"+i+"").html();
		var emaildate = $("#emaildetails4"+i+"").html();
		var emailcontent = $("#emaildetails5"+i+"").html();
		var emlpath = $("#emaildetails6"+i+"").html();
		var read = $("#emaildetails7"+i+"").html();
		var star = $("#emaildetails8"+i+"").html();
		var attachmentname = $("#emaildetails9"+i+"").html();

		var caseIDs = $("#emaildetails10"+i+"").html();
		var caseNames = $("#emaildetails11"+i+"").html();
		//var emailKeyword = $("#emailKeyword").val();
		$.ajax({
			type : "POST",
			url : "<%=basePath%>getCorrectEml.php",
			data : {
				"emlpath":emlpath,
				"attachmentname":attachmentname,
				"keyword":emailKeyword,
				"zhengzeType":zhengzeType,
				"orKey":orKey,
				"andKey":andKey
			},
			dataType : "json",
			async: true,
			success : function(data) {
				$("#loadDiv").hide();
				if(emlpath.substring(emlpath.lastIndexOf("."),emlpath.length)==".eml"){
					
				$("#emailcontent").html(data.resData.content);
				}else{
					
					$("#emailcontent").html(emailcontent);
				}
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
						if(docType=="jpg"||docType=="JPG"||docType=="png"||docType=="PNG"||docType=="tif"||docType=="TIF"){
							  
							reshtml += "<a href=\"<%=basePath %>downAttachment.php?path="+emlpath+"&name="+data.resData.attfile_encode[i]+"\" title=\""+aJ+"\" class=\"_modal-mailcontent-append-item-img\"><img src=\"<%=basePath %>template/img/jpg.png\" /></a>";
						}
						if(docType=="txt"||docType=="TXT"||docType=="log"||docType=="LOG"){
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

		$("#stari").html(i);
		$("#esId").html(esId);
		$("#emailsubject").html("邮件标题 : "+emailsubject);
		$("#emailtoWho").html("发件人 : "+emailtoWho);
		$("#emailfromWho").html("收件人 : "+emailfromWho);
		$("#emaildate").html(emaildate);
		//$("#emailcontent").html(content);
		$("#emailurl").html(emlpath);
		$("#emailstar").html(star);

		$("#caseIDs").html(caseIDs);
		$("#caseNames").html(caseNames);
		var star2;
		if(star==0){
			star2="star2";
		}if(star==1){
			star2="star";
		}
		var read2;
		if(read==0){
			read2="unread";
		}if(star==1){
			read2="read";
		}
		<%-- document.getElementById("stars").src = "<%=basePath%>template/img/"+star2+".png"; --%>
		//判断邮件是否已读

		if(read==0){
			//未读数-1
			read000=read000-1;
			//改变图标
			document.getElementById("read"+i).src = "<%=basePath%>template/img/unread.png";
			//修改未读书
			$("#email0").html("未读"+read000+"封");
			//修改主题显示
			$("#emailboldSubject"+i).css("font-weight","");
			//修改邮件状态
			upemailstatus(1,esId,emlpath,caseIDs);
		}
	}

	function downloadEML(){
		var emlpath = document.getElementById("emailurl").innerText;
		$("#url").attr("href","<%=basePath %>admin/downloadEML.php?emlpath="+emlpath);
		return false;
	}
	
	function downloadEMLD2(){
		var emlpath = document.getElementById("emailurlD2").innerText;
		
		$("#urlD2").attr("href","<%=basePath %>admin/downloadEML.php?emlpath="+emlpath);
		return false;
	}

	//修改邮件状态
	function upemailstatus(read,esId,emlpath,caseIDs){
		//var url = document.getElementById("emailurl").innerText;
		var read2="";
		if(read==0){
			read2=1;
		}if(read==1){
			read2=0;
		}
		$.ajax({
			type : "POST",
			url : "<%=basePath%>emaiExcavatel/upEmailStatus.php",
			data : {
				"star":"",
				"esId":esId,
				"read":read,
				"emlpath":emlpath,
				"caseIDs":caseIDs
			},
			dataType : "json",
			async: true,
			success : function(data) {
				if(read==0){
					document.getElementById("emailstar").innerText=1;
				}
				if(star==1){
					document.getElementById("emailstar").innerText=0;
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert("失败!"); */
			}
		});
	}

	//点击弹出收藏或者取消框
	function upemailstar(i){
		var emailESId = $("#emaildetails0"+i+"").html();
		var emailStarFlag = $("#emaildetails8"+i+"").html();

		if(emailStarFlag=='1'){
			$(("#"+'emailStar'+i)).attr('data-target',"#cancelFavorite");
			//取消收藏
			$('#cancelPicId').val(emailESId);
			$('#cancelPicNumber').val(i);
			$('#cancelPicFlag').val(emailStarFlag);


		}else {
			//进行收藏
			showAllLabelOfDoc();
			$(("#"+'emailStar'+i)).attr('data-target',"#myModalfavorite");
			$('#picId').val(emailESId);
			$('#picNumber').val(i);
			$('#picFlag').val('0');
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
		$("input[name='ids_favo']:checked").each(function(){
			values.push($(this).val());
		});
		if(values.length==0){
			alert('请先选中标签!');
		}else{
			var esid=$('#picId').val();
			var i=$('#picNumber').val();
			var flag=$('#picFlag').val();
			doAddOrCancelFavorite(esid,flag,i,values.toString());
			//$('#myModalfavorite').close();
			$('#myModalfavorite').modal('hide');
		}
	}
	//取消或者收藏
	function doAddOrCancelFavorite(esid,flag,i,values){
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/favoEmail.php",
			data : {
				'esid':esid,
				'flag':flag,
				'emailLabel':values
			},
			dataType : "json",
			async: true,
			success : function(data) {
				//进行收藏
				if(flag==0){
					var src="<%=basePath%>"+"template/img/star.png";
					$(("#"+'emailStar'+i)).attr('src',src);
					alert("收藏成功！");
					document.getElementById("emaildetails8"+i+"").innerText=1;
				}
				//取消收藏
				if(flag==1){
					var src="<%=basePath%>"+"template/img/star2.png";
					$(("#"+'emailStar'+i)).attr('src',src);
					document.getElementById("emaildetails8"+i+"").innerText=0;
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert('请求收藏失败'); */
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
				$("#tbcontOfDoc_div2").empty();
				$("#tbcontOfDoc_div3").empty();
				$("#tbcontOfDoc_div4").empty();


				for(var i = data.length - 1; i >= 0;i--){
					if(i % 7 == 1) {
						var html = '<span>'+
							'<span class="td_left c07"><input id="caselist'+i+'" type="checkbox"  name="ids_favo" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br05 b12 span-1" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 2) {
						var html = '<span>'+
							'<span class="td_left c07"><input id="caselist'+i+'" type="checkbox"  name="ids_favo" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="b13 br06 span-2" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 3) {
						var html = '<span>'+
							'<span class="td_left c07"><input id="caselist'+i+'" type="checkbox"  name="ids_favo" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br11 b11 span-3" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 4) {
						var html = '<span>'+
							'<span class="td_left c07"><input id="caselist'+i+'" type="checkbox"  name="ids_favo" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br07 b14 span-4" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 5) {
						var html = '<span>'+
							'<span class="td_left c07"><input id="caselist'+i+'" type="checkbox"  name="ids_favo" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br08 b15 span-5" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 6) {
						var html = '<span>'+
							'<span class="td_left c07"><input id="caselist'+i+'" type="checkbox"  name="ids_favo" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br09 b17 span-6" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 7) {
						var html = '<span>'+
							'<span class="td_left c07"><input id="caselist'+i+'" type="checkbox"  name="ids_favo" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br10  b18 span-7" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						if(i % 7 == 0) {
						var html = '<span>'+
							'<span class="td_left c07"><input id="caselist'+i+'" type="checkbox"  name="ids_favo" value="'+data[i].label+'" onclick="showChoose(this.id);" />'+
								'<span class="br11 b11 span-3" id="">'+data[i].label+'</span>'+
							'</span><br/>';
							}
						$("#tbcontOfDoc").append(html);
						$("#tbcontOfDoc_div2").append(html);
						$("#tbcontOfDoc_div3").append(html);
						$("#tbcontOfDoc_div4").append(html);
				}

			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	function saveBiaoji() {
		
		var esIDs2 = $("#esId").text();
		
		/* var emailTitle = document.getElementById("emailTitle").innerText;   //邮件标题1
		var emailurl = document.getElementById("emailurl").innerText;			//路径
		var caseIDs = document.getElementById("caseIDs").innerText;				//案件ID
		var caseNames = document.getElementById("caseNames").innerText;			//案件名称
		var biaojiContent = $("#biaojiContent").val();   						//复制粘贴的线索
		
		var biaojiTotles2 = document.getElementById("biaojiTotles2").innerText;   			//获取该邮件被标记的次数  判断调用哪个后台方法
		var mailESiD = document.getElementById("mailESiD").innerText;  */        //mail上的_id
		var emailTitle = document.getElementById("emailTitle").innerText;
		var emailurl = $("#emailurl").text();
		var caseIDs = $("#caseIDs").text();
		var caseNames = $("#caseNames").text();
		var biaojiContent = $("#biaojiContent").val();
		var biaojiTotles2 = document.getElementById("biaojiTotles2").innerText;
		var mailESiD = document.getElementById("mailESiD").innerText;
		
		var userIDs = document.getElementById("userIDs").innerText;    //标记人添加标记时存到mail里的ID
		var onLineUserID = document.getElementById("onLineUserID").innerText;	//登录人登录时存到session中的ID
		
		var changeUrl = "";
		if(userIDs == onLineUserID){         //大于0  说明一存在    这时是修改      ID相等  说明是同一个人
			changeUrl = "<%=basePath%>emaiExcavatel/updateBiaoJi2.php";     
		}else{
			changeUrl = "<%=basePath%>emaiExcavatel/addBiaoJi.php";
		}
		
		if(biaojiContent == "" || biaojiContent == null) {
			alert("请填写标记线索！");
			return false;
		}else{
			$.ajax({
				type : "POST",
				url : changeUrl,
				data : {
					"caseIDs":caseIDs,
					"caseNames":caseNames,
					"biaojiContent":biaojiContent,
					"emailTitle":emailTitle,
					"emailurl":emailurl,
					"esIDs2" : esIDs2,
					"mailESiD" : mailESiD
				},
				dataType : "json",
				async: true,
				success : function(data) {
					$(".dialog").css("display","none");
//					alert("标记线索成功!");  myModals_div4
					$("#biaojiContent").val("");
					$('#biaojiEvidence_success').modal('show');
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("标记线索失败!");
				}
			});
		} 
	}
	
	//收发件分析   保存标记
	function saveBiaojiD2() {
		var esIDs2 = document.getElementById("esIdD2").innerText;
		//var subject = document.getElementById("emailsubjectBiaoji").innerText;   //邮件标题   
		
		var emailTitle = document.getElementById("emailTitleD2").innerText;   //邮件标题1
		var emailurl = document.getElementById("emailurlD2").innerText;			//路径
		var caseIDs = document.getElementById("caseIDsD2").innerText;				//案件ID
		var caseNames = document.getElementById("caseNamesD2").innerText;			//案件名称
		var biaojiContent = $("#biaojiContentD2").val();   						//复制粘贴的线索
		
		var biaojiTotles2 = document.getElementById("biaojiTotles2D2").innerText;   			//获取该邮件被标记的次数  判断调用哪个后台方法
		var mailESiD = document.getElementById("mailESiDD2").innerText;         //mail上的_id
		
		var userIDs = document.getElementById("userIDsD2").innerText;    //标记人添加标记时存到mail里的ID
		var onLineUserID = document.getElementById("onLineUserID").innerText;	//登录人登录时存到session中的ID
		
		var changeUrl = "";
		if(userIDs == onLineUserID){         //大于0  说明一存在    这时是修改
			changeUrl = "<%=basePath%>emaiExcavatel/updateBiaoJi2.php";     
		}else{
			changeUrl = "<%=basePath%>emaiExcavatel/addBiaoJi.php";
		}
		
		if(biaojiContent == "" || biaojiContent == null) {
			alert("请填写标记线索！");
			return false;
		}else{
			$.ajax({
				type : "POST",
				url : changeUrl,
				data : {
					"caseIDs":caseIDs,
					"caseNames":caseNames,
					"biaojiContent":biaojiContent,
					"emailTitle":emailTitle,
					"emailurl":emailurl,
					"esIDs2" : esIDs2,
					"mailESiD" : mailESiD
				},
				dataType : "json",
				async: true,
				success : function(data) {
					$("#dialogD2").css("display","none");
//					alert("标记线索成功!");  myModals_div4
					$("#biaojiContentD2").val("");
					$('#biaojiEvidence_success').modal('show');
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("标记线索失败!");
				}
			});
		}
	}
	function getInfomation() {//caseIDs
		var esId23 = document.getElementById("esId").innerText;
		
		var emailurl = document.getElementById("emailurl").innerText;
		var emailsubject = document.getElementById("emailsubject").innerText;
		var emailsubject1 = emailsubject.substring(6);
		
		var emaildate = document.getElementById("emaildate").innerText;
		var caseIDs = document.getElementById("caseIDs").innerText;
		var caseNames = document.getElementById("caseNames").innerText;
		$("#emailTitle").html(emailsubject1);
		
		$("#emailsubjectBiaoji").html(emailsubject);
		$("#biaojiTime").html(emaildate);
		$("#caseIDs").html(caseIDs);
		$("#caseNames").html(caseNames);
		
		//$("#esId").html(esId23);
		
		$.ajax({
	        url:"<%=basePath %>admin/editBiaojiOnEmail.php",
	        data:{
	        	"esId23" : esId23
	        },
	        type:"post",
	        dataType:"json",
	        success: function(data){
	        	var totles = data.hits.total;
	        	$("#biaojiTotles2").html(totles);
	        	var lis = data.hits.hits;
	        	
	        	var allBiaojiOfOne = "";
	        	var mailESiD = "";
	        	var userIDs = "";
	        	$.each(lis,function(i,item){
	        		/* var test = new Array();
	        		test = item._source.content.split("\n");
	        		for (var j = 0; j < test.length; j++) {
	        			allBiaojiOfOne += "●" +test[j] + "\n";
	        			
	        		} */
	        		
	        		allBiaojiOfOne += item._source.content + "\n";
	        		mailESiD += item._id;
	        		userIDs += item._source.userID;
				});
	        	$("#biaojiContent").val(allBiaojiOfOne);
	        	$("#mailESiD").html(mailESiD);
	        	$("#userIDs").html(userIDs);
	        }
	    });
	}
	
	function getInfomationD2() {//caseIDs
		var esId23 = document.getElementById("esIdD2").innerText;
		
		var emailurl = document.getElementById("emailurlD2").innerText;
		var emailsubject = document.getElementById("emailsubjectD2").innerText;
		var emailsubject1 = emailsubject.substring(6);
		
		var emaildate = document.getElementById("emaildateD2").innerText;
		var caseIDs = document.getElementById("caseIDsD2").innerText;
		var caseNames = document.getElementById("caseNamesD2").innerText;
		$("#emailTitleD2").html(emailsubject1);
		
		$("#emailsubjectBiaojiD2").html(emailsubject);
		$("#biaojiTimeD2").html(emaildate);
		$("#caseIDsD2").html(caseIDs);
		$("#caseNamesD2").html(caseNames);
		
		//$("#esId").html(esId23);
		
		$.ajax({
	        url:"<%=basePath %>admin/editBiaojiOnEmail.php",
	        data:{
	        	"esId23" : esId23
	        },
	        type:"post",
	        dataType:"json",
	        success: function(data){
	        	var totles = data.hits.total;
	        	$("#biaojiTotles2D2").html(totles);
	        	var lis = data.hits.hits;
	        	
	        	var allBiaojiOfOne = "";
	        	var mailESiD = "";
	        	var userIDs = "";
	        	$.each(lis,function(i,item){
	        		/* var test = new Array();
	        		test = item._source.content.split("\n");
	        		for (var j = 0; j < test.length; j++) {
	        			allBiaojiOfOne += "●" +test[j] + "\n";
	        			
	        		} */
	        		
	        		allBiaojiOfOne += item._source.content + "\n";
	        		mailESiD += item._id;
	        		userIDs += item._source.userID;
				});
	        	$("#biaojiContentD2").val(allBiaojiOfOne);
	        	$("#mailESiDD2").html(mailESiD);
	        	$("#userIDsD2").html(userIDs);
	        }
	    });
	}

	function getInfomation_div4() {//caseIDs
		var esId5 = document.getElementById("esId_div4").innerText;
		
		var emailurl1 = document.getElementById("emailurl_div4").innerText;
		var emaildate1 = document.getElementById("emaildate_div4").innerText;
//		var caseIDs1 = document.getElementById("caseIDs_div4").innerText;
		var caseNames1 = document.getElementById("caseNames_div4").innerText;
		
		var emailsubject1 = document.getElementById("emailsubject_div4").innerText;
		var emailsubject11 = emailsubject1.substring(6);
		
		
		$("#emailTitle_div4").html(emailsubject11);
		$("#emailsubjectBiaoji_div4").html(emailsubject1);
		$("#biaojiTime_div4").html(emaildate1);
		
		//$("#caseIDs_div4").html(caseIDs1);
		$("#caseNames_div4").html(caseNames1);
		$.ajax({
	        url:"<%=basePath %>admin/editBiaojiOnEmail.php",
	        data:{
	        	"esId23" : esId5
	        },
	        type:"post",
	        dataType:"json",
	        success: function(data){
	        	var totles5 = data.hits.total;
	        	
	        	$("#biaojiTotles2_div4").html(totles5);
	        	var lis = data.hits.hits;
	        
	        	var allBiaojiOfOne5 = "";
	        	var mailESiD5 = "";
	        	var userIDs = "";
	        	$.each(lis,function(i,item){
	        		allBiaojiOfOne5 += item._source.content + "\n";
	        		mailESiD5 += item._id;
	        		userIDs += item._source.userID;
				});
	        	
	        	$("#biaojiContent_div4").val(allBiaojiOfOne5);
	        	$("#mailESiD_div4").html(mailESiD5);
	        	$("#userIDs_div4").html(userIDs);
	        }
	    });
	}

	function saveBiaoji_div4() {
		var esIDs5 = $("#esId_div4").text();
		/* var emailurl = document.getElementById("emailurl_div4").innerText;			//路径
		var emailTitle = document.getElementById("emailTitle_div4").innerText;   //邮件标题1
		var caseIDs = document.getElementById("caseIDs_div4").innerText;				//案件ID
		var caseNames = document.getElementById("caseNames_div4").innerText;			//案件名称
		var biaojiContent = $("#biaojiContent_div4").val();   						//复制粘贴的线索
		var biaojiTotles2 = document.getElementById("biaojiTotles2_div4").innerText;   			//获取该邮件被标记的次数  判断调用哪个后台方法
		var mailESiD5 = document.getElementById("mailESiD_div4").innerText;  */         //mail上的_id
		var emailTitle = document.getElementById("emailTitle_div4").innerText;
		var emailurl = $("#emailurl_div4").text();
		var caseIDs = $("#caseIDs_div4").text();
		var caseNames = $("#caseNames_div4").text();
		var biaojiContent = $("#biaojiContent_div4").val();
		var biaojiTotles2 = document.getElementById("biaojiTotles2_div4").innerText;
		var mailESiD5 = document.getElementById("mailESiD_div4").innerText;
		
		var userIDs = document.getElementById("userIDs_div4").innerText;    //标记人添加标记时存到mail里的ID
		var onLineUserID = document.getElementById("onLineUserID").innerText;	//登录人登录时存到session中的ID
		
		var changeUrl = "";
		if(userIDs == onLineUserID){         //大于0  说明一存在    这时是修改
			changeUrl = "<%=basePath%>emaiExcavatel/updateBiaoJi2.php";     
		}else{
			changeUrl = "<%=basePath%>emaiExcavatel/addBiaoJi.php";
		}
		
		if(biaojiContent == "" || biaojiContent == null) {
			alert("请填写标记线索！");
			return false;
		}else{
			$.ajax({
				type : "POST",
				url : changeUrl,
				data : {
					"caseIDs":caseIDs,
					"caseNames":caseNames,
					"biaojiContent":biaojiContent,
					"emailTitle":emailTitle,
					"emailurl":emailurl,
					"esIDs2" : esIDs5,
					"mailESiD" : mailESiD5
				},
				dataType : "json",
				async: true,
				success : function(data) {
					$(".dialog1").css("display","none");
					$('#biaojiEvidence_successDiv4').modal('show');
					$("#biaojiContent_div4").val("");
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("标记线索失败!");
				}
			});
		}
	}
	/* =========================================================================================================
	=========================================嫌疑人关系图=====================================================
	======================================================================================================*/
	function contactsAnalyze2(){
		//startTime = $("#startDate_div5").val();
		//endTime = $("#endDate_div5").val();
		//emailKeyword = $("#suspectsName_d5").val();      //周武智
		
		
		startTimeD5 = startTime;
		endTimeD5 = endTime;
		emailKeywordD5 = emailKeyword;
		
		connectNum = $("#connectNum").val();//联系次数的值
		$.ajax({
			type : "POST",
			url : "<%=basePath%>emaiExcavatel/contactsAnalyze2.php",
			data : {
				"caseidStr":caseidStr,
				"startDate":startTimeD5,
				"endDate":endTimeD5,
				"emailKeywordD5":emailKeywordD5,
				"connectNum":connectNum,
				"XYRID":XYRID,
				"XYRemail":XYRemail,
				"emlType":emlType
			},
			dataType : "json",
			async: true,
			success : function(data) {
				//showAllemail(1); 	//电子邮件数据类型列表加载  //周武智
	 			// 联系人分析的插件显示
				  var nodes = [ ];
				  var links1 = [ ];//连线
				  var a=0;
				  var datanodes=data.nodes;
				  var datalinks=data.links;
				  var cate="";
				  var categorycase="";
				  var categories = [];
				  var categories2 = [];
				 
				  $.each(datanodes,function(i,item1){
					  var caseName=item1.caseName;
					  var category=item1.category;
					  var cat=100;
					  for(var i =0;i<100;i++){
						  if(category==i){
							  cat=i;
						  }
					  }
					  var node={
							    name: item1.name,
					            category: item1.isSuspect,
					            draggable: true,
					            itemStyle: {
					                normal: {
					                    label: {
					                        textStyle: {
					                            color: '#f90'
					                        }
					                    }
					                },
					                emphasis: {
					                    color: "#000"
					                }
					            }
			                    };
					  nodes.push(node);
					 });
				 $.each(datalinks,function(j,item2){
					 var link= {
							    source: item2.fromWho,
					            target: item2.toWho,
					            value: item2.value
							  };
					 links1.push(link);
				 });
				 var myChart8 = echarts.init(document.getElementById('mainSuspect'));
				 option8 = {
				     tooltip: {
				         show: true
				     },
				     legend: {
				         x: "center",
				         //data: categories2
				     },
				      toolbox: {  
				          //show: true,  
				          itemSize: 20,  
				          itemGap: 30,  
				          right: 50,  
				          feature: {  
				              /* dataView: {show:true}, */  
				              saveAsImage: { 
				             	 name:"嫌疑人关系图",
				                  //excludeComponents :['toolbox'],  
				                  pixelRatio: 2  
				              }  
				          }  
				 }, 
				     animation: false,
				     series: [{
				         type: 'graph',
				         animation: 'false',
				         layout: 'circular',
				         symbol: "circle",
				         symbolSize: 50,
				         roam: true, //禁止用鼠标滚轮缩小放大效果
				         edgeSymbol: ['circle', 'arrow'],
				         edgeSymbolSize: [0, 10],
				         // 连接线上的文字
				         focusNodeAdjacency: true, //划过只显示对应关系
				         edgeLabel: {
				             normal: {
				                 show: true,
				                 textStyle: {
				                     fontSize: 20
				                 },
				                 formatter: "{c}"
				             }
				         },
				         categories: [{
				             name: '0',
				             itemStyle: {
				                 normal: {
				                     color: "#009800",
				                 }
				             }
				         }, {
				             name: '1',
				             itemStyle: {
				                 normal: {
				                     color: "#4592FF",
				                 }
				             }
				         }],
				         lineStyle: {
				             normal: {
				                 opacity: 1,
				                 width: 2,
				                 curveness: 0
				             }
				         },
				         // 圆圈内的文字
				         label: {
				             normal: {
				                 show: true
				             }
				         },
				         force: {
				             repulsion: 5000
				         },
				         data: nodes,
				         links: links1
				     }]
				 };
				 myChart8.setOption(option8);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert("失败123"); */
			}
		});
	}
	</script>

	<!-- 弹出取消收藏框      div_1 -->
	<div class="modal fade" id="cancelFavorite" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" >
						取消收藏
					</h4>
				</div>

				<div class="modal-footer">
					<span id="cancelPicId" style="display:none;"></span>
					<span id="cancelPicFlag" style="display:none;"></span>
					<span id="cancelPicNumber" style="display:none;"></span>
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="cancelFavorite()">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<!-- 弹出框       选择标签 class="modal right fade"  tabindex="-1"  -->
	<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" id="myModalfavorite" style="width: 100%; height: 100%; margin: auto;">
				<div class="panel panel-default"
					style="width: 50%; height: 80%; margin: 3% auto;">
					<div class="panel-heading">邮件收藏 - 选择标签</div>

					<div class="panel-body" style="width: 100%; height: 100%;">
						<div class="panel-body" style="width: 100%">
							<label class="c08" style="float:left;margin-top:7px;">新建标签：</label>
							<input id="favoLabelNameOfEmail" name="favoLabelNameOfEmail" class="form-control" placeholder="请输入..." type="text" style="float:left; width: 60%;" />
							<button type="button" class="btn btn-info" style="width: 70px; margin-left: 10px; height: 33px" onclick="addFavoLabelOfEmail()">添加</button>
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

	<!-- 弹出取消收藏框      div_2 -->
	<div class="modal fade" id="cancelFavorite_div2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" >取消收藏</h4>
				</div>

				<div class="modal-footer">
					<span id="cancelPicId_div2" style="display:none"></span>
					<span id="cancelPicFlag_div2" style="display:none"></span>
					<span id="cancelPicNumber_div2" style="display:none"></span>
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="cancelFavorite_div2()">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 弹出框       选择标签 class="modal right fade"  tabindex="-1"    _div2  -->
	<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" id="myModalfavorite_div2"
				style="width: 100%; height: 100%; margin: auto;">
				<div class="panel panel-default" style="width: 50%; height: 80%; margin: 3% auto;">
					<div class="panel-heading">邮件收藏 -选择标签</div>

					<div class="panel-body" style="width: 100%; height: 100%;">
						<div class="panel-body" style="width: 100%">
						<label class="c08" style="float:left;margin-top:7px;">新建标签：</label>
						<input id="favoLabelNameOfEmail_div2" name="favoLabelNameOfEmail_div2" class="form-control" placeholder="请输入..." type="text" style="float:left; width: 60%;" />
						<button type="button" class="btn btn-info" style="width: 70px; margin-left: 10px; height: 33px" onclick="addFavoLabelOfEmail_div2()">添加</button>
						</div>
						<div class="panel-body br18" style="padding: 0 0 0 20px;overflow-y:auto;height:62%;">
									<span id="tbcontOfDoc_div2" >

								    </span>
						</div>
						<span id="picId_div2" style="display:none"></span>
						<span id="picFlag_div2" style="display:none"></span>
						<span id="picNumber_div2" style="display:none"></span>
						<!-- data-dismiss="modal" -->
						<button type='button' class="btn btn-info" style="width: 65px; height: 30px; margin-left: 39%; margin-top: 3%;"
							 	onclick="addFavorite_div2();">确定</button>

						<button type='button' class="btn btn-info" style="width: 65px; height: 30px; margin-left: 8px;margin-top: 3%;"
								data-dismiss="modal">取消</button>
					</div>
				</div>
	</div>

	<!-- 弹出取消收藏框      div_3 -->
	<div class="modal fade" id="cancelFavorite_div3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" >取消收藏</h4>
				</div>

				<div class="modal-footer">
					<span id="cancelPicId_div3" style="display:none"></span>
					<span id="cancelPicFlag_div3" style="display:none"></span>
					<span id="cancelPicNumber_div3" style="display:none"></span>
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="cancelFavorite_div3()">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 弹出框       选择标签 class="modal right fade"  tabindex="-1"    _div3  -->
	<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" id="myModalfavorite_div3"
				style="width: 100%; height: 100%; margin: auto;">
				<div class="panel panel-default" style="width: 50%; height: 80%; margin: 3% auto;">
					<div class="panel-heading">邮件收藏 -选择标签</div>

					<div class="panel-body" style="width: 100%; height: 100%;">
						<div class="panel-body" style="width: 100%">
						<label class="c08" style="float:left;margin-top:7px;">新建标签：</label>
						<input id="favoLabelNameOfEmail_div3" name="favoLabelNameOfEmail_div2" class="form-control" placeholder="请输入..." type="text" style="float:left; width: 60%;" />
						<button type="button" class="btn btn-info" style="width: 70px; margin-left: 10px; height: 33px" onclick="addFavoLabelOfEmail_div3()">添加</button>
						</div>
						<div class="panel-body br18" style="padding: 0 0 0 20px;overflow-y:auto;height:62%;">
									<span id="tbcontOfDoc_div3" >

								    </span>
						</div>
						<span id="picId_div3" style="display:none"></span>
						<span id="picFlag_div3" style="display:none"></span>
						<span id="picNumber_div3" style="display:none"></span>
						<!-- data-dismiss="modal" -->
						<button type='button' class="btn btn-info" style="width: 65px; height: 30px; margin-left: 39%; margin-top: 3%;"
								onclick="addFavorite_div3();">确定</button>

						<button type='button' class="btn btn-info" style="width: 65px; height: 30px; margin-left: 8px;margin-top: 3%;"
								data-dismiss="modal">取消</button>
					</div>
				</div>
	</div>
	<!-- 弹出取消收藏框      div_4 -->
	<div class="modal fade" id="cancelFavorite_div4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" >取消收藏</h4>
				</div>

				<div class="modal-footer">
					<span id="cancelPicId_div4" style="display:none"></span>
					<span id="cancelPicFlag_div4" style="display:none"></span>
					<span id="cancelPicNumber_div4" style="display:none"></span>
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="cancelFavorite_div4()">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 弹出框       选择标签 class="modal right fade"  tabindex="-1"    _div4  -->
	<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" id="myModalfavorite_div4"
				style="width: 100%; height: 100%; margin: auto;">
				<div class="panel panel-default" style="width: 50%; height: 80%; margin: 3% auto;">
					<div class="panel-heading">邮件收藏 -选择标签</div>

					<div class="panel-body" style="width: 100%; height: 100%;">
						<div class="panel-body" style="width: 100%">
						<label class="c08" style="float:left;margin-top:7px;">新建标签：</label>
						<input id="favoLabelNameOfEmail_div4" name="favoLabelNameOfEmail_div4" class="form-control" placeholder="请输入..." type="text" style="float:left; width: 60%;" />
						<button type="button" class="btn btn-info" style="width: 70px; margin-left: 10px; height: 33px" onclick="addFavoLabelOfEmail_div4()">添加</button>
						</div>
						<div class="panel-body br18" style="padding: 0 0 0 20px;overflow-y:auto;height:62%;">
									<span id="tbcontOfDoc_div4" >

								    </span>
						</div>
						<span id="picId_div4" style="display:none"></span>
						<span id="picFlag_div4" style="display:none"></span>
						<span id="picNumber_div4" style="display:none"></span>
						<!-- data-dismiss="modal" -->
						<button type='button' class="btn btn-info" style="width: 65px; height: 30px; margin-left: 39%; margin-top: 3%;"
								onclick="addFavorite_div4();" >确定</button>

						<button type='button' class="btn btn-info" style="width: 65px; height: 30px; margin-left: 8px;margin-top: 3%;"
								data-dismiss="modal">取消</button>
					</div>
				</div>
	</div>

	<!-- 标记线索成功弹框 -->
		<div class="modal fade" id="biaojiEvidence_success" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="biaoji_successes" id="biaoji_successes">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c20" id="myModalLabel" style="border-left: none;">线索标记</h3>
					</div>
					<div class="modal-body">线索标记成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 域名分析   标记线索成功弹框 -->
		<div class="modal fade" id="biaojiEvidence_successDiv4" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="biaoji_successesDiv4" id="biaoji_successesDiv4">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c20" id="myModalLabel" style="border-left: none;">线索标记</h3>
					</div>
					<div class="modal-body">线索标记成功！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div>
				</div>
			</div>
		</div>

	<script type="text/javascript">
	function getdomain() {
	var myChart = echarts.init(document.getElementById('main'));
	// 指定图表的配置项和数据
	var option = {
		color : [ '#3398DB' ],
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : [ {
			type : 'category',
			data : [ ],
			axisTick : {
				alignWithLabel : true
			}
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [ {
			name : '命中次数',
			type : 'bar',
			barWidth : '40%',
			data : [ ]
		} ]
	};
	//异步加载数据
 	var startDate = $("#startDate_demain").val();
	var endDate = $("#endDate_demain").val();
	var dateTime = [];
	var datesum = [];
	$.ajax({
		type : "POST",
		url : "<%=basePath%>admin/getEmail_domain.php",
		data : {
			"caseidStr":caseidStr,
			"startDate":startDate,
			"endDate":endDate,
            "XYRemail":XYRemail,
            "emlType":emlType
		},
		dataType : "json",
		async: true,
		success : function(data) {
			for(i = 0;i<data.beanStr.length;i++) {
				for(i = 0;i<data.beanStr.length;i++) {
                        if (i < 10) {
                            dateTime.push(data.beanStr[i].type);
                            datesum.push(data.beanStr[i].num);
						}
				}
				myChart.setOption({
                    xAxis: {
                        data: dateTime
                    },
                    series: [
                        {
                            //根据名字对应到相应的系列
                            name: "命中次数",
                            data: datesum
                        }
                    ]
                });
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			/* alert("失败"); */
		}
	});
	myChart.setOption(option);
	}
	</script>
	<script type="text/javascript">
	// d2页面的js代码
              //表格显示发件人排序
              //window.onload = show(1);
            	function show(pageno){//表格显示发件人排序
            		
            	
            		$("#email_formWho").empty();
            		var html0 = '<table class="c20 br04" style="width: 100%; height: 50px;" >'+
            					'<tr>'+
            						'</tr>'+
            						'<tr>'+
            					'</tr>'+
            						'<tr>'+
            							'<td>正在搜索中...</td>'+
            						'</tr>'+
            					'</table>';
            		$("#email_formWho").append(html0);
            		var XYRconnectNum = $("#XYRconnectNum").val();
            		$.ajax({
            			type : "POST",
            			url : "<%=basePath%>admin/getEmail_Workbench.php",
            			data : {
            				"pageno":pageno,
            				"caseidStr":caseidStr,
            				"XYRemail":XYRemail,
            				"XYRConnectNum":XYRconnectNum
            			},
            			dataType : "json",
            			async: true,
            			success : function(data) {
            				$("#email_formWho").empty();
            				$.each(data,function(i,item){
            					var str =item.toWho ;
            					var shou="";
            					if(str.length>0){
            						shou = str.substring(0,45) + "...";
            					}
            					var html01 = '+<tr onclick="showList2('+i+')" >'+
					            				    '<td style="text-align: left;width:80%" id="from'+i+'"><p class="mysheng">'+str+'</p></td> '+
					            					'<td style="text-align: right">'+item.emailNum+'</td>'+
					            			 '</tr>+';
            					$("#email_formWho").append(html01);
            				});
            			},
            			error: function(XMLHttpRequest, textStatus, errorThrown) {
            				/* alert("失败"); */
            			}
            		});
            	}
            	//收件人排序
            	function addresser(pageno){
            		$("#tbcont4").empty();
            		var html0 = '<table class="br04 c20" style=" width: 100%; height: 50px;" >'+
            					'<tr>'+
            						'</tr>'+
            						'<tr>'+
            					'</tr>'+
            						'<tr>'+
            							'<td>正在搜索中...</td>'+
            						'</tr>'+
            					'</table>';
            		$("#tbcont4").append(html0);
            		//XYRemail="659810135@qq.com,qin@browah.com";
            		var XYRconnectNum = $("#XYRconnectNum").val();
            		$.ajax({
            			type : "POST",
            			url : "<%=basePath%>admin/getEmail_Work.php",
            			data : {
            				"pageno":pageno,
            				"caseidStr":caseidStr,
            				"XYRemail":XYRemail,
            				"XYRConnectNum":XYRconnectNum
            			},
            			dataType : "json",
            			async: true,
            			success : function(data) {
            				$("#tbcont4").empty();
            				$.each(data,function(i,item){
            					var str =item.formWho;
            					var substr1="";
            					if(str.length>4){
            						substr1 = str.substring(0,45) + "...";
            					}
            					var html01 = '+<tr id="numList2" onclick="showList('+i+')">'+
					            				    '<td style="text-align: left;width:80%" id="to'+i+'"><p class="mysheng">'+str+'</p></td> '+
					            					'<td style="text-align: right" >'+item.emailNum+'</td>'+
            				    			'</tr>+';
            					$("#tbcont4").append(html01);
            				});
            			},
            			error: function(XMLHttpRequest, textStatus, errorThrown) {
            				/* alert("失败"); */
            			}
            		});
            	}
	</script>
	<!-- <script src="http://echarts.baidu.com/build/dist/echarts.js"></script> -->
	<%-- <script type="text/javascript" src="<%=basePath%>template/js/echarts/echarts.min.js"></script>--%>
	<script type="text/javascript" src="<%=basePath%>template/js/echarts/echarts.js"></script> 
    <script type="text/javascript" src="<%=basePath%>template/js/echarts/dataTool.js"></script>
	<!-- ----------------------------------------------------------------------------------------------------------
	---------------------------------------------收发件分析邮件显示------------------------------------------------
	----------------------------------------------------------------------------------------------------------- -->
	   <script type="text/javascript">
						        // 基于准备好的dom，初始化echarts实例   emailKeyword
			function shijianxian(){//时间线插件
				//var startDate6 = $("#startDate6").val();
				//var endDate6=$("#endDate6").val();
				//var guanjianci=$("#guanjianci").val();   原来时间线有搜索框取得关键字     后来去掉后周武智改成了下面的取值方法，取全局变量emailKeyword
				//startTime = startDate6;
				//endTime = endDate6;
				//emailKeyword = guanjianci;
				
				var guanjianci = emailKeyword;
				var startDate6 = startTime;
				var endDate6 = endTime;
				
				$.ajax({
					type : "POST",
					url : "<%=basePath%>Excavateldata/getTimeList.php",
					data : {
						"caseidStr":caseidStr,
						"startDate":startDate6,
						"endDate":endDate6,
						"emailKeyword2":emailKeyword,
						"guanjianci":guanjianci,
						"XYRemail":XYRemail,
						"allcount":allcount
					},
					dataType : "json",
					async: true,
					success : function(data) {
						 //getAllemail();   配合上面的   周武智去掉的
						  
						var myChart2 = echarts.init(document.getElementById('main2'));
						myChart2.on("click",function(params){

							var time = params.name;
							$("#startDate_demain_email").val(time);
							$("#endDate_demain_email").val(time);
							getAllemail();
					/* 	 	var length = haoma.split("：");
							if(length.length>2){
								var duifang = length[1].split(">")[0];
								var xianyiren = length[2];
								$("#StatisticsSuspectName").val(xianyiren);
								$("#StatisticsContactorNumber").val(duifang);
								showAllTicket(1);
							}  */
						});

			        	var timeData = data.hits;
			        /* 	timeData = timeData.map(function (str) {
			        	    return str.replace('2009/', '');
			        	}); */

			        	option = {

			        	    tooltip: {
			        	        trigger: 'axis',
			        	        axisPointer: {
			        	            animation: false
			        	        }
			        	    },
			        	    legend: {
			        	        data:['个数'],
			        	        x: 'left'
			        	    },
			        	    toolbox: {
			        	        feature: {
			        	            dataZoom: {
			        	                yAxisIndex: 'none'
			        	            },
			        	            restore: {},
			        	            saveAsImage: {}
			        	        }
			        	    },
			        	    axisPointer: {
			        	        link: {xAxisIndex: 'all'}
			        	    },
			        	    dataZoom: [
			        	        {
			        	            show: true,
			        	            realtime: true,
			        	            start: 30,
			        	            end: 70,
			        	            xAxisIndex: [0, 1]
			        	        },
			        	        {
			        	            type: 'inside',
			        	            realtime: true,
			        	            start: 30,
			        	            end: 70,
			        	            xAxisIndex: [0, 1]
			        	        }
			        	    ],
			        	    grid: [{
			        	        left: 50,
			        	        right: 50,
			        	        height: '70%'
			        	    }, {
			        	        left: 50,
			        	        right: 50,
			        	        top: '0',
			        	        height: '0'
			        	    }],
			        	    xAxis : [
			        	        {
			        	            type : 'category',
			        	            boundaryGap : false,
			        	            axisLine: {onZero: true},
			        	            data: timeData
			        	        },
			        	        {
			        	            gridIndex: 1,

			        	        }
			        	    ],
			        	    yAxis : [
			        	        {
			        	            name : '个数',
			        	            type : 'value',
			        	        },
			        	        {
			        	            gridIndex: 1,

			        	        }
			        	    ],
			        	    series : [
			        	        {
			        	            name:'个数',
			        	            type:'line',
			        	            symbolSize: 8,
			        	            hoverAnimation: true,
			        	            data:data.count
			        	        }
			        	    ]
			        	};

					  myChart2.setOption(option);
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						//alert("失败");
					}
				});
		}
</script>

	<script>
	var	email_d2=[];
	//收发件次数过滤分析     id=XYRconnectNum
	function showEmailDetil(){//收发件次数过滤分析
		var XYRconnectNum = $("#XYRconnectNum").val();
		show();//表格显示发件人排序
	    addresser();//收件人排序
	}

	</script>

	<script>
	window.onload=showEmail();
	function showEmail(){
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/searchEmail.php",
			data : {
				"caseid":caseid
			},
			dataType : "json",
			async: true,
			success : function(data) {
				var caseid=data.caseid;
			},
		});
	}
	</script>
	<!-- div4域名分析的邮件展示 -->
	<script>
	//邮件下载
	function downloadEML_div4(){
		var emlpath = document.getElementById("emailurl_div4").innerText;
		$("#url_div4").attr("href","<%=basePath %>admin/downloadEML.php?emlpath="+emlpath);
		return false;
	}
	//搜索邮件list
	var read000_div4 = 0;
	function showAllemail_div4(pageno,type){//域名搜索邮件list表格
		$("#emailtou_div4").empty();
		var sortType = $("#sortType_div4").val();
		var emailKeyword = $("#emailKeyword_div4").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();

		var givePages_div4yuming = $("#givePages_div4yuming").val();
		if(givePages_div4yuming != ""){
			pageno = parseInt(givePages_div4yuming);
//			pageno = 1;
		}
		$("#email_div4").empty();
		var html0 = '<table class="br04 c20" style=" width: 1600px; height: 90px;" >'+
					'<tr>'+
						'</tr>'+
						'<tr>'+
					'</tr>'+
						'<tr>'+
							'<td>正在搜索中...</td>'+
						'</tr>'+
					'</table>';
		$("#email_div4").append(html0);
		$.ajax({
			type : "POST",
			url : "<%=basePath%>emaiExcavatel/getEmailList.php",
			data : {
				"pageIndex":pageno,
				"caseidStr":caseidStr,
				"sortType":sortType,
				"emailKeyword":emailKeyword,
				"startDate":startDate,
				"endDate":endDate,
				"regexpQuery":type,
				"XYRemail":XYRemail,
				"emlType":emlType
			},
			dataType : "json",
			async: true,
			success : function(data) {
			    var total = data.count;
			    var read00=data.read0;
			    //分页开始
			    var sizes=10;
				var pagesum=total;
				var pagenum = pagesum / sizes;
				var length=5;  //要显示的分页页数

				if(pagenum%1!=0){
						pagenum=pagenum+(1-pagenum%1);
				}
				$("#pages_d5_div4").empty();
				//用于删除之前显示的页数，动态添加时id名均设为order
				for(var i=1;i<=length;i++)
					  $("#orderA_5_div4").remove();
				if(pagesum<sizes){
					var html2 = '<li class="active" id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4(1)">1</a></li >';
					$("#pages_d5_div4").after(html2);
				}else{

				if(pageno<pagenum){
					if(pageno+length-1<=pagenum){
						var html2="";
						if(pageno-2>0){
							for(var i =pageno-2;i<=pageno+length-1-2;i++){
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4('+i+')">'+i+'</a></li >';
									else
										html2 += '<li id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4('+i+')">'+i+'</a></li >';
						   				   }
							}
						else{
							for(var i =1;i<=length;i++){
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4('+i+')">'+i+'</a></li >';
									else

										html2 += '<li id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4('+i+')">'+i+'</a></li >';
						   				   }
						}
					$("#pages_d5_div4").after(html2);
					}
					else{
						var html2="";
						if(pagenum>=length){
						for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){


							 if(i==pageno)
								html2 += '<li class="active" id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4('+i+')">'+i+'</a></li >';
							else
								html2 += '<li id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4('+i+')">'+i+'</a></li >';
						   }
						$("#pages_d5_div4").after(html2);
						}
						else{
							for(var i =1;i<=pagenum;i++){
								 if(i==pageno)
									html2 += '<li class="active" id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4('+i+')">'+i+'</a></li >';
								else
									html2 += '<li id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4('+i+')">'+i+'</a></li >';
							   }
							$("#pages_d5_div4").after(html2);
						}
					}
				}
				else{
					if(pageno==pagenum){
						var html2="";
						for(var i =pageno-length+1>0?pageno-length+1:1;i<=pagenum;i++){
						 if(i==pageno)
							html2 += '<li class="active" id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4('+i+')">'+i+'</a></li >';
						else
							html2 += '<li id="orderA_5_div4"><a href="javascript:void(0)" onclick="showAllemail_div4('+i+')">'+i+'</a></li >';
					   }
						$("#pages_d5_div4").after(html2);
					}
				}
			}

				$("#tot1_d5_div4").empty();
				$("#pages1_d5_div4").empty();
				$("#pages2_d5_div4").empty();
				var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
				$("#tot1_d5_div4").append(html3);

				var html5 = '<a href="javascript:void(0)" onclick="showAllemail_div4('+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
				$("#pages1_d5_div4").append(html5);
				var html4 = '<a href="javascript:void(0)" onclick="showAllemail_div4('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
				$("#pages2_d5_div4").append(html4);
				//分页结束

				$("#email_div4").empty();
				$("#emailtou_div4").empty();
				var htmltou ='<tr>'+
									'<th class="alcenter" style="text-align:center;width:70px;">收藏</th>'+
									'<th class="alcenter" style="text-align:center;width:88px;">协同分析</th>'+
									'<th class="alcenter">邮件主题</th>'+
									'<th class="alcenter" >发件人</th>'+
									'<th class="alcenter" >收件人</th>'+
									'<th class="alcenter">IP</th>'+
									'<th class="alcenter" >发送日期</th>'+
									'<th class="alcenter" style=" text-align:center;">附件</th>'+
							'</tr>';
				$("#emailtou_div4").append(htmltou);

				read000_div4=0;
				var sumi=0;
				var emailDTOList = data.emailDTOList;
				 $.each(emailDTOList,function(i,item){
					var towho =item.toWho;//收件人
					//if(towho!=null){
						//var towhos = towho.split(";;");
						//var towhonum = towhos.length;
						//if(towhonum>1){
							//towho=towhos[0]+"...";
						//}
					//}
					var multiarea = "("+item.multiarea+")";
					var substr =item.subject ;
					var substr2 =item.fromWho ;
					var substr1 =towho ;
					var fujian =item.attachmentname;
					var fuj ="";
					//有附件显示
					if(fujian.length>0){
						fuj ='src="<%=basePath%>template/img/fujian.png"';
					}
					var star;
					var star2=item.star;
					if(star2==0){
						star="star2";
					}if(star2==1){
						star="star";
					}
					var read;
					var read2=item.read;
					if(read2==0){
						read="read";
						var subjectStyle = '<td id ="emailboldSubject_div4'+i+'" data-toggle="modal" data-target="#myModals_div4" onclick="showdetails_div4('+i+')" style="text-align:left; vertical-align:middle;font-weight:bold;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+substr+'</span></td>';
					}if(read2==1){
						read="unread";
						var subjectStyle = '<td id ="emailboldSubject_div4'+i+'" data-toggle="modal" data-target="#myModals_div4" onclick="showdetails_div4('+i+')" style="text-align:left; vertical-align:middle;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+substr+'</span></td>';
					}
					var html =  '<tr>'+
									'<td style="text-align:center; vertical-align: middle;"><img id ="emailStar_div4'+i+'" data-target="#myModalfavorite_div4" data-toggle="modal" onclick="upemailstar_div4('+i+')" src="<%=basePath%>template/img/'+star+'.png"/></td>'+
									'<td id ="emailbold_div4'+i+'" data-toggle="modal" data-target="#myModals_div4" onclick="showdetails_div4('+i+')" style="text-align:center; vertical-align: middle;"><img id = "read_div4'+i+'" src="<%=basePath%>template/img/'+read+'.png" /></td>'+
									subjectStyle+
									'<td id ="emailbold_div4'+i+'" data-toggle="modal" data-target="#myModals_div4" onclick="showdetails_div4('+i+')" style="text-align:left; vertical-align: middle;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+substr2+'</span></td>'+
									'<td id ="emailbold_div4'+i+'" data-toggle="modal" data-target="#myModals_div4" onclick="showdetails_div4('+i+')" style="text-align:left; vertical-align: middle;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+substr1+'</span></td>'+
									'<td id ="emailbold_div4'+i+'"  onclick="getIpOnlyOneAddress(\''+item.ip+'\')" style="text-align:left; vertical-align: middle;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+item.ip+multiarea+'</span></td>'+
									'<td id ="emailbold_div4'+i+'" data-toggle="modal" data-target="#myModals_div4" onclick="showdetails_div4('+i+')" style="text-align:left; vertical-align:middle;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+item.date+'</span></td>'+
									'<td id ="emailbold_div4'+i+'" data-toggle="modal" data-target="#myModals_div4" onclick="showdetails_div4('+i+')" style="text-align:center; vertical-align:middle;"><img id = "fujian_div4'+i+'" '+fuj+' />'+

									'<div id="emaildetails0_div4'+i+'" style=" display:none;">'+item.esID+'</div>'+
									'<div id="emaildetails1_div4'+i+'" style=" display:none;">'+item.subject+'</div>'+
									'<div id="emaildetails2_div4'+i+'" style=" display:none;">'+item.fromWho+'</div>'+
									'<div id="emaildetails3_div4'+i+'" style=" display:none;">'+item.toWho+'</div>'+
									'<div id="emaildetails4_div4'+i+'" style=" display:none;">'+item.date+'</div>'+
									/* '<div id="emaildetails5_div4'+i+'" style=" display:none;">'+item.content+'</div>'+ */
									'<div id="emaildetails6_div4'+i+'" style=" display:none;">'+item.downloadUrl+'</div>'+
									'<div id="emaildetails7_div4'+i+'" style=" display:none;">'+item.read+'</div>'+
									'<div id="emaildetails8_div4'+i+'" style=" display:none;">'+item.star+'</div>'+

									'<div id="emaildetails9_div4'+i+'" style=" display:none;">'+item.attachmentname+'</div>'+
									'<div id="emaildetails10_div4'+i+'" style=" display:none;">'+item.caseID+'</div>'+
									'<div id="emaildetails11_div4'+i+'" style=" display:none;">'+item.caseName+'</div>'+
									'</td>'+
								'</tr>';
					$("#email_div4").append(html);
					sumi=i+1;
				 });
				 $("#givePages_div4yuming").val("");
				 read000_div4=read00;
				 $("#emailnum_div4").html("/全部"+total+"封");
				 $("#emailsumnum_div4").html("共搜索到" + total + "个结果");
				 $("#email0_div4").html("未读"+read00+"封");
				 if(sumi==0){
					 $("#email_div4").empty();
					 $("#emailtou_div4").empty();
						var html01 = '<table class="br04 c20" style="width: 100%; height: 90px;" >'+
											'<tr>'+
										'</tr>'+
										'<tr>'+
									'</tr>'+
										'<tr>'+
											'<td>未找到相关内容</td>'+
										'</tr>'+
									'</table>';
						$("#email_div4").append(html01);
				 }
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert("失败"); */
			}
		});
	}

	//点击邮件显示详情
	function showdetails_div4(i){
		$("#emailcontent_div4").html('<div id="loadDiv" style="text-align: center;margin-top: 20px"> <img alt="" src="<%=basePath %>template/img/loading2.gif"> </div>');
		$("#correctEml_attfile_div4").empty();
		var esId = $("#emaildetails0_div4"+i+"").html();
		var emailsubject = $("#emaildetails1_div4"+i+"").html();//document.getElementById("emaildetails1"+i+"").innerHTML;
		var emailtoWho = $("#emaildetails2_div4"+i+"").html();
		var emailfromWho = $("#emaildetails3_div4"+i+"").html();
		var emaildate = $("#emaildetails4_div4"+i+"").html();
		var emailcontent = $("#emaildetails5_div4"+i+"").html();
		var emlpath = $("#emaildetails6_div4"+i+"").html();
		var read = $("#emaildetails7_div4"+i+"").html();
		var star = $("#emaildetails8_div4"+i+"").html();
		var attachmentname = $("#emaildetails9_div4"+i+"").html();

		var caseIDs_div4 = $("#emaildetails10_div4"+i+"").html();
		var caseNames_div4 = $("#emaildetails11_div4"+i+"").html();

		//var emailKeyword = $("#emailKeyword_div4").val();

		$.ajax({
			type : "POST",
			url : "<%=basePath%>getCorrectEml.php",
			data : {
				"emlpath":emlpath,
				"attachmentname":attachmentname,
				"keyword":emailKeyword,
				"zhengzeType":zhengzeType
			},
			dataType : "json",
			async: true,
			success : function(data) {
				$("#loadDiv").hide();
				if(emlpath.substring(emlpath.lastIndexOf("."),emlpath.length)==".eml"){
				$("#emailcontent_div4").html(data.resData.content);
				}else{
					$("#emailcontent_div4").html(emailcontent);
				}
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
					reshtml += "<div class=\"_modal-mailcontent-append-item\"style=\"padding-right:50px;float:left;\">";
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
					$("#correctEml_attfile_div4").html(reshtml);
					}
				return true;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("失败!");
			}
		});
		$("#stari_div4").html(i);
		$("#esId_div4").html(esId);
		$("#emailsubject_div4").html("邮件标题 : "+emailsubject);
		$("#emailtoWho_div4").html("发件人 : "+emailtoWho);
		$("#emailfromWho_div4").html("收件人 : "+emailfromWho);
		$("#emaildate_div4").html(emaildate);
		//$("#emailcontent_div4").html(emailcontent);
		$("#emailurl_div4").html(emlpath);
		$("#emailstar_div4").html(star);

		$("#caseIDs_div4").html(caseIDs_div4);
		$("#caseNames_div4").html(caseNames_div4);

		var star2;
		if(star==0){
			star2="star2";
		}if(star==1){
			star2="star";
		}
		var read2;
		if(read==0){
			read2="unread";
		}if(star==1){
			read2="read";
		}
		//判断邮件是否已读
		if(read==0){
			//未读数-1
			read000_div4=read000_div4-1;
			//改变图标
			document.getElementById("read_div4"+i).src = "<%=basePath%>template/img/unread.png";
			//修改未读数
			$("#email0_div4").html("未读"+read000_div4+"封");
			//修改主题显示
			$("#emailboldSubject_div4"+i).css("font-weight","");
			//修改邮件状态
			upemailstatus_div4(1,esId);
		}
	}

	function downloadEML(){
		var emlpath = document.getElementById("emailurl").innerText;
		$("#url").attr("href","<%=basePath %>admin/downloadEML.php?emlpath="+emlpath);
		return false;
	}

	//修改邮件状态
	function upemailstatus_div4(read,esId){
		var read2="";
		if(read==0){
			read2=1;
		}if(read==1){
			read2=0;
		}
		$.ajax({
			type : "POST",
			url : "<%=basePath%>emaiExcavatel/upEmailStatus.php",
			data : {
				"star":"",
				"esId":esId,
				"read":read
			},
			dataType : "json",
			async: true,
			success : function(data) {
				if(read==0){
					document.getElementById("emailstar_div4").innerText=1;
				}
				if(read==1){
					document.getElementById("emailstar_div4").innerText=0;
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert("失败!"); */
			}
		});
	}

	//div4      点击弹出收藏或者取消框
	function upemailstar_div4(i){
		 $("#demo").attr("checked",false);
		var emailESId = $("#emaildetails0_div4"+i+"").html();
		var emailStarFlag = $("#emaildetails8_div4"+i+"").html();

		if(emailStarFlag=='1'){
			$(("#"+'emailStar_div4'+i)).attr('data-target',"#cancelFavorite_div4");
			//取消收藏
			$('#cancelPicId_div4').val(emailESId);
			$('#cancelPicNumber_div4').val(i);
			$('#cancelPicFlag_div4').val(emailStarFlag);
		}else {
 			//进行收藏
			showAllLabelOfDoc();
			$(("#"+'emailStar_div4'+i)).attr('data-target',"#myModalfavorite_div4");
			$('#picId_div4').val(emailESId);
			$('#picNumber_div4').val(i);
			$('#picFlag_div4').val('0');
		}
	}
	//取消收藏
	function cancelFavorite_div4(){
		var esid=$('#cancelPicId_div4').val();
		var i=$('#cancelPicNumber_div4').val();
		var flag=$('#cancelPicFlag_div4').val();
		doAddOrCancelFavorite_div4(esid,flag,i,'');
	}
	//执行收藏
	function addFavorite_div4(){
		var values=[];
		$("input[name='ids_favo']:checked").each(function(){
			values.push($(this).val());
		});
		if(values.length==0){
			alert('请先选中类别');
		}else{
			var esid=$('#picId_div4').val();
			var i=$('#picNumber_div4').val();
			var flag=$('#picFlag_div4').val();
			doAddOrCancelFavorite_div4(esid,flag,i,values.toString());
			$('#myModalfavorite_div4').modal('hide');
		}
	}
	//取消或者收藏
	function doAddOrCancelFavorite_div4(esid,flag,i,values){
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/favoEmail.php",
			data : {
				'esid':esid,
				'flag':flag,
				'emailLabel':values
			},
			dataType : "json",
			async: true,
			success : function(data) {
				//进行收藏
				if(flag==0){
					var src="<%=basePath%>"+"template/img/star.png";
					$(("#"+'emailStar_div4'+i)).attr('src',src);
					alert("收藏成功！");
					document.getElementById("emaildetails8_div4"+i+"").innerText=1;
				}
				//取消收藏
				if(flag==1){
					var src="<%=basePath%>"+"template/img/star2.png";
					$(("#"+'emailStar_div4'+i)).attr('src',src);
					document.getElementById("emaildetails8_div4"+i+"").innerText=0;
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert('请求收藏失败'); */
			}
		})
	}
	</script>
	
	<script>
	justAddrs = new Array();
	function getIpAddress(){//调用域名方法
		justAddrs.splice(0,justAddrs.length);
		var sortType = $("#sortType_div4").val();
		var emailKeyword = $("#emailKeyword_div4").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/getEmail_getIpAddress.php",
			data : {
				"caseidStr":caseidStr,
				"sortType":sortType,
				"emailKeyword":emailKeyword,
				"startDate":startDate,
				"endDate":endDate
			},
			dataType : "json",
			async: true,
			success : function(data) {
				$.each(data,function(i,item){
					justAddrs[i] = item;
				});
				init_for_ipMap();
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert("失败"); */
			}
		});
	}
	justAddrs_onlyOne = new Array();

	function getIpOnlyOneAddress(oneIp){//调用域名方法
		justAddrs_onlyOne.splice(0,justAddrs_onlyOne.length);	
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/getEmail_getOnlyOneIpAddress.php",
			data : {
				"ip":oneIp
			},
			dataType : "json",
			async: true,
			success : function(data) {
				$.each(data,function(i,item){
					justAddrs_onlyOne[i] = item;
				});
				init_for_ipMap1();
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert("失败"); */
			}
		});
	}

	function exportMail() {
        var getResult = confirm("确定导出?");
        if (getResult) {
            $("#urlMail").attr("href","<%=basePath %>ExportDomainMailWorkSpace.php");
        } else {
            $("#urlMail").attr("href", "javascript:void(0)");
        }
	}

	function exportMail_d2() {
        var getResult = confirm("确定导出?");
        if (getResult) {
            $("#urlMail_d2").attr("href","<%=basePath %>ExportToFromEmail.php");
        } else {
            $("#urlMail_d2").attr("href", "javascript:void(0)");
        }
	}
	/* =========================================================================================================
		=========================================联系人分析插件1=====================================================
		======================================================================================================*/
		var startTime = "";
		var endTime = "";
		function contactsAnalyze(){//联系人分析插件
			startTime = $("#startDate_div3").val();
			endTime = $("#endDate_div3").val();
			emailKeyword = $("#suspectsName_d3").val();
			$.ajax({
				type : "POST",
				url : "<%=basePath%>emaiExcavatel/contactsAnalyze.php",
				data : {
					"caseidStr":caseidStr,
					"startDate":startTime,
					"endDate":endTime,
					"suspectsName":emailKeyword
				},
				dataType : "json",
				async: true,
				success : function(data) {
					showAllemail(1,zhengzeType);	//电子邮件数据类型列表加载
					// 联系人分析的插件显示
					 var nodes = [ ];
					 var links = [ ];//连线
					 var a=0;

					 var categorycase="";
					 var categories = [];
					 $.each(data,function(i,item){
						 var size = Math.sqrt(item.value);
						 var caseName=item.caseName;
						 if(i==0){
							 categorycase = caseName;
							var categorie = {
							            name: caseName
							        };
							categories.push(categorie);
						 }else{
							 if(caseName!=categorycase){
								 var categorie2 = {
								            name: caseName
								        };
								 categories.push(categorie2);
							 }
							 categorycase = caseName;
						 }
						var node={
								 id : item.id,
			                     category : item.category*1,
			                     name : "邮箱: "+item.name+"  次数",
			                    // symbol : '11111111111111111111111111',
			                     value : item.value,
			                     draggable: true,
			                     symbolSize :size+20
			                    };

						var to=item.toWho;
						var tos = to.split("/");
						 $.each(tos,function(k,item3){
							 $.each(data,function(j,item2){
								 var name = item2.name;
								 var id = item2.id;
								 if(item3==name){
									 if(item.id!=id){
										 var link= {
													source : item.id,
													target : id
												  };
										 links.push(link);
									 }
								 }
							 });
						 });
						 nodes.push(node);
					 });
						var myChart6 = echarts.init(document.getElementById('mainCase'));
						myChart6.showLoading();
						$.get('<%=basePath%>template/js/cutover/les-miserables.gexf', function(xml) {
							myChart6.hideLoading();
						    var graph = echarts.dataTool.gexf.parse(xml);
						    graph.nodes.forEach(function (node) {
						        node.itemStyle = null;
						        node.symbolSize = 10;
						        node.value = node.symbolSize;
						        node.category = node.attributes.modularity_class;
						        node.x = node.y = null;
						        node.draggable = true;
						    });
						    option = {
						        title: {
						            text: '',
						            subtext: 'Default layout',
						            top: 'bottom',
						            left: 'right'
						        },
						        tooltip: {},
						        legend: [{
						            data: categories.map(function (a) {
						                return a.name;
						            })
						        }],
						        animation: false,
						        series : [
						            {
						                name: '详情',
						                type: 'graph',
						                animation: 'false',
								         layout: 'circular',
						                data:nodes,// graph.nodes,
					                    links:links,// graph.links,
						                categories: categories,
						                roam: true,
						                label: {
						                    normal: {
						                        position: 'right'
						                    }
						                },
						                force: {
						                    repulsion: 100
						                }
						            }
						        ]
						    };

						    myChart6.setOption(option);
						}, 'xml');

				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					/* alert("失败"); */
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
		//弹出框    新建标签div1
		function addFavoLabelOfEmail() {
			var favoLabelName = $("#favoLabelNameOfEmail").val();
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

		//回车搜索事件
 		function onKeyDown(event){
		   var e = event || window.event || arguments.callee.caller.arguments[0];
		   if(e && e.keyCode==13){ // enter 键
			   getAllemail(1);//点击搜索
		   }
		}

		//回车搜索事件    getAllemail   页数跳转
		 function onKeyDown_emailsWork(event){
		   var e = event || window.event || arguments.callee.caller.arguments[0];
		   if(e && e.keyCode==13){ // enter 键
			   showAllemail(1,zhengzeType);	//电子邮件数据类型列表加载
		   }
		}
		//回车搜索事件      域名分析页数跳转         
 		function onKeyDown_div4yuming(event){
		   var e = event || window.event || arguments.callee.caller.arguments[0];
		   if(e && e.keyCode==13){ // enter 键
			   showAllemail_div4(1);//域名搜索邮件list表格
		        getIpAddress();//调用域名方法
		   }
		}

		//回车搜索事件
		function onKeyDown_yuming(event){
		   var e = event || window.event || arguments.callee.caller.arguments[0];
		   if(e && e.keyCode==13){ // enter 键
			   showAllemail_div4(1);//域名搜索邮件list表格
		   }
		}

		//回车搜索事件      时间线    
		function onKeyDown_shijianxian(event){
		   var e = event || window.event || arguments.callee.caller.arguments[0];
		   if(e && e.keyCode==13){ // enter 键
			   shijianxian(1);//时间线插件
		   }
		}
		
		//收发件  统计次数
		function onKeyDown_shouFajian(event){
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if(e && e.keyCode==13){ // enter 键
				showEmailDetil();//收发件次数过滤分析统计次数
			}
		}

		//回车搜索事件      邮件关系图
		function onKeyDown_emailPic(event){
		   var e = event || window.event || arguments.callee.caller.arguments[0];
		   if(e && e.keyCode==13){ // enter 键
			  	d3(1);
		   }
		}

		//回车搜索事件      嫌疑人关系图
		function onKeyDown_xianyiren(event){
		   var e = event || window.event || arguments.callee.caller.arguments[0];
		   if(e && e.keyCode==13){ // enter 键
			  	d5(1);
		   }
		}

		//回车搜索事件      更改案件
		function onKeyDown_changeCase(event){
		   var e = event || window.event || arguments.callee.caller.arguments[0];
		   if(e && e.keyCode==13){ // enter 键
			   showcase(1);
		   }
		}

		//弹出框    新建标签div4
		function addFavoLabelOfEmail_div4() {
			var favoLabelName = $("#favoLabelNameOfEmail_div4").val();
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
		/* 手机号银行卡号 第一次点击加背景色第二次点击取消背景色 */
		$(".js-change").click(function(){
			if($(this).hasClass('selected')){	
				$(this).removeClass("selected");
				$(".js-change").removeClass("selected");
			}else{
				$(".js-change").removeClass("selected");
				$(this).addClass("selected");
			}
			
		}) 

        var tofromtype="";
		var tofromemailstr="";
		var tofromemailstr2="";//嫌疑人的邮箱
		//点击邮箱关系图 圆圈
		//$("#xianyiren").on("click",function(){
			//var email1 = $("#xianyiren").children(":first").children(":first").next().children(":first").children(":first").next().text();
			//var email=email1.split(": ")[1].split("  次")[0];
			/* if(email1.indexOf("<")>0){
				email = email1.split("<")[1].split(">")[0];
			} */
			//$("#emailKeyword").val(email);
			//tofromemailstr=email.replace( "<","&lt;").replace( ">","&gt");
			//tofromemailstr=email;
			//getEmailListtofrom(1);
			//emailKeyword = email;
			//showAllemail(1,"");	//电子邮件数据类型列表加载
		//});
		
		//点击嫌疑人关系图 圆圈
		$("#XYRguanxitu").on("click",function(){
			var email2 = $("#XYRguanxitu").children(":first").children(":first").next().children(":first").children(":first").next().text();
			var email3="";
			var index = email2.lastIndexOf(":");
			var el1 = email2.substring(0,index);//截取分号前半部分
			var el2 = email2.substring(index+1,email2.length);//截取分号后半部分
			var emlStr = el1+"#^#"+el2;//拼接成完整字符串
			var email=emlStr.split("#^#")[0];//与嫌疑人有关的邮箱
			if(email.indexOf(">")>0){
				if(email.split(">")[0].indexOf("&^&")>0){
				    email3=emlStr.split("#^#")[0].split(" ")[0].split("&^&")[1];//尖括号前面的嫌疑人邮箱
					tofromtype="from";
					email=email.split(" ")[2];
				}else{
					email3=emlStr.split("#^#")[0].split(" ")[2].split("&^&")[1];//尖括号后面的嫌疑人邮箱
					tofromtype="to";
					email=email.split(" ")[0];
				}
			}
			tofromemailstr=email;
			tofromemailstr2=email3;
			getEmailListtofrom(1);
		});

		function showList(i){
		//	var emailList=$("#emailName"+i).text();
			//var  emailList=$("#emailName2").text();
			/* emailKeyword =emailList;
			showAllemail(1,""); */	//电子邮件数据类型列表加载
			var to = $("#to"+i).text();
			tofromtype="from";
			//tofromemailstr=to.substring(0,to.length-3).replace( "<","&lt;").replace( ">","&gt");
			tofromemailstr=to;
			//getEmailListtofrom(1);
			getEmailListtofromOfD2(1);//收发件分析的单独的表格加载方法
		}
		function showList2(i){
			
			var emailList=$("#from"+i).text();
			emailList=$("#formEmail"+i).text();
			//var emailList=$("#emailName2").text();
			/* emailKeyword =emailList;
			showAllemail(1,""); */	//电子邮件数据类型列表加载
			var from = $("#from"+i).text();
			tofromtype="to";
			//tofromemailstr=from.substring(0,from.length-3).replace( "<","&lt;").replace( ">","&gt");
			tofromemailstr=from;
			//getEmailListtofrom(1);
			getEmailListtofromOfD2(1);//收发件分析的单独的表格加载方法
		}

	//圆点
	function getEmailListtofrom(pageno){
		var tofromkey="";
		var tofromstartTime="";
		var tofromendTime="";
		if(type==3){
			tofromstartTime = $("#startDate_div3").val();
			tofromendTime = $("#endDate_div3").val();
			tofromkey = $("#suspectsName_d3").val();
		}else if(type==5){
			tofromstartTime = $("#startDate_div5").val();
			tofromendTime = $("#endDate_div5").val();
			tofromkey = $("#suspectsName_d5").val();
		}else if(type==2){
			tofromstartTime = $("#startDate_d2").val();
			tofromendTime = $("#endDate_d2").val();
		}
		var test = $("spans").val();
		$("#emailtou").empty();
		$("#email").empty();
		var html0 = '<table class="c20 br04" style=" width: 100%; height: 90px" >'+
					'<tr>'+
						'</tr>'+
						'<tr>'+
					'</tr>'+
						'<tr>'+
							'<td>正在搜索中...</td>'+
						'</tr>'+
					'</table>';
		$("#email").append(html0);
		var givePages_emailsWork = $("#givePages_emailsWork").val();
    	if(givePages_emailsWork != ""){
    		pageno = parseInt(givePages_emailsWork);
    	}
		
    	//alert(" 1 "+pageno+" 2 "+ caseidStr+" 3  "+tofromemailstr+" 4  "+tofromkey+" 5  "+tofromstartTime+" 6 "+tofromendTime+" 7 "+tofromtype);
		$.ajax({
			type : "POST",
			url : "<%=basePath%>emaiExcavatel/getEmailListtofrom.php",
			data : {
				"pageIndex":pageno,
				"caseidStr":caseidStr,
				"emailstr":tofromemailstr,
				"emailstr2":tofromemailstr2,
				"emailkey":tofromkey,
				"startDate":tofromstartTime,
				"endDate":tofromendTime,
				"tofromType":tofromtype,
				"XYRemail":XYRemail,
				//"emlType" : emlType
			},
			dataType : "json",
			async: true,
			success : function(data) {
			    var total = data.count;
			    var read00=data.read0;
			    //分页开始
			    var sizes=10;
				var pagesum=total;
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
					var html2 = '<li class="active" id="orderA_5"><a href="#" onclick="getEmailListtofrom(1)">1</a></li >';
					$("#pages_d5").after(html2);
					//alert("pageno  "+pageno+"   pagenum "+pagenum+  "  html2 1  :"+html2);
				}else{
				if(pageno<pagenum){
					if(pageno+length-1<=pagenum){
						var html2="";
						if(pageno-2>0){
							for(var i =pageno-2;i<=pageno+length-1-2;i++){
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="getEmailListtofrom('+i+')">'+i+'</a></li >';
								else
									html2 += '<li id="orderA_5"><a href="javascript:void(0)" onclick="getEmailListtofrom('+i+')">'+i+'</a></li >';
						    }
						}else{
							for(var i =1;i<=length;i++){
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="getEmailListtofrom('+i+')">'+i+'</a></li >';
								else
									html2 += '<li id="orderA_5"><a href="javascript:void(0)" onclick="getEmailListtofrom('+i+')">'+i+'</a></li >';
						   	}
						}

					$("#pages_d5").after(html2);
					//alert("pageno  "+pageno+"   pagenum "+pagenum+  "  html2 2  :"+html2);
					}else{
						var html2="";
						if(pagenum>=length){
						for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){
							if(i==pageno)
								html2 += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="getEmailListtofrom('+i+')">'+i+'</a></li >';
							else
								html2 += '<li id="orderA_5"><a href="javascript:void(0)" onclick="getEmailListtofrom('+i+')">'+i+'</a></li >';
						}
						$("#pages_d5").after(html2);
						}else{
							for(var i =1;i<=pagenum;i++){
								if(i==pageno)
									html2 += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="getEmailListtofrom('+i+')">'+i+'</a></li >';
								else
									html2 += '<li id="orderA_5"><a href="javascript:void(0)" onclick="getEmailListtofrom('+i+')">'+i+'</a></li >';
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
							html2 += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="getEmailListtofrom('+i+')">'+i+'</a></li >';
						else
							html2 += '<li id="orderA_5"><a href="javascript:void(0)" onclick="getEmailListtofrom('+i+')">'+i+'</a></li >';
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
				var html5 = '<a href="javascript:void(0)" onclick="getEmailListtofrom('+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
				$("#pages1_d5").append(html5);

				var html4 = '<a href="javascript:void(0)" onclick="getEmailListtofrom('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
				$("#pages2_d5").append(html4);
				//分页结束
				$("#email").empty();
				$("#emailtou").empty();
				var htmltou ='<tr>'+
					'<th class="alcenter" style=" width:70px;text-align:center; ">收藏</th>'+
					'<th class="alcenter" style=" width:88px;text-align:center;">协同分析</th>'+
					'<th class="alcenter">邮件主题</th>'+
					'<th class="alcenter">发件人</th>'+
					'<th class="alcenter">收件人</th>'+
					'<th class="alcenter">发送日期</th>'+
					'<th class="alcenter" style=" text-align:center; ">附件</th>'+
				'</tr>';

				$("#emailtou").append(htmltou);

				read000=0;
				var sumi=0;
				var emailDTOList = data.emailDTOList;
				 $.each(emailDTOList,function(i,item){
					var towho =item.toWho;//收件人
					if(towho!=null){
						var towhos = towho.split(";;");
						var towhonum = towhos.length;
						if(towhonum>1){
							towho=towhos[0]+"...";
						}
					}
					var fujian =item.attachmentname;
					var fuj ="";
					//有附件显示
					if(fujian.length>0){
						fuj ='src="<%=basePath%>template/img/fujian.png"';
					}

					var star;
					var star2=item.star;
					if(star2==0){
						star="star2";
					}if(star2==1){
						star="star";
					}
					var sub = item.subject;
					///if(sub.length > 26 ){
						//sub=sub.substring(0,26)+"...";
					//}
					if(emailKeyword!="" && emailKeyword!=null){
						var keylist=emailKeyword.split(" ");
						for(var n =0;n<keylist.length;n++){
							sub=sub.replace(keylist[n], "<font style='color: red;background-color: yellow;'>" + keylist[n] + "</font>");
						}
						//sub=sub.replace(emailKeyword, "<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
					}


					if(orKey!=null && orKey!=""){
						var orKeylist=orKey.split(" ");
						for(var n =0;n<orKeylist.length;n++){
							sub=sub.replace(orKeylist[n], "<font style='color: red;background-color: yellow;'>" + orKeylist[n] + "</font>");
						}
					}
					if(andKey!=null && andKey!=""){
						var andKeylist=andKey.split(" ");
						for(var n =0;n<andKeylist.length;n++){
							sub=sub.replace(andKeylist[n], "<font style='color: red;background-color: yellow;'>" + andKeylist[n] + "</font>");
						}
					}

					var fromstr=item.fromWho;
					var tostr=item.toWho;
					if(emailstr!=null && emailstr!=""){
						var emaillist=emailstr.split(" ");
						for(var n =0;n<emaillist.length;n++){
							fromstr=fromstr.replace(emaillist[n], "<font style='color: red;background-color: yellow;'>" + emaillist[n] + "</font>");
							tostr=tostr.replace(emaillist[n], "<font style='color: red;background-color: yellow;'>" + emaillist[n] + "</font>");
							towho=towho.replace(emaillist[n], "<font style='color: red;background-color: yellow;'>" + emaillist[n] + "</font>");
						}
					}
					var read;
					var read2=item.read;
					if(read2==0){
						read="read";
						var subjectStyle = '<td id ="emailboldSubject'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:left; vertical-align:middle;font-weight:bold; width :25%; "><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+sub+'</span></td>';
					}if(read2==1){
						read="unread";
						var subjectStyle = '<td id ="emailboldSubject'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:left; vertical-align:middle; width :25%; "><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+sub+'</span></td>';
					}
					var html =  '<tr>'+
									'<td style="text-align:center; vertical-align: middle;"><img id ="emailStar'+i+'" data-target="#myModalfavorite" data-toggle="modal" onclick="upemailstar('+i+')" src="<%=basePath%>template/img/'+star+'.png"/></td>'+
									'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:center; vertical-align: middle;"><img id = "read'+i+'" src="<%=basePath%>template/img/'+read+'.png" /></td>'+
									subjectStyle+
									'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:left; vertical-align: middle; width :15%; "><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+item.fromWho+'</span></td>'+
									'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:left; vertical-align: middle; width :15%; "><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+towho+'</span></td>'+
									'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:left; vertical-align:middle; width :10%; "><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+item.date+'</span></td>'+
									'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:center; vertical-align:middle; width :10%; "><img id = "fujian'+i+'" '+fuj+' />'+

									'<div id="emaildetails0'+i+'" style=" display:none;">'+item.esID+'</div>'+
									'<div id="emaildetails1'+i+'" style=" display:none;">'+sub+'</div>'+
									'<div id="emaildetails2'+i+'" style=" display:none;">'+fromstr+'</div>'+
									'<div id="emaildetails3'+i+'" style=" display:none;">'+tostr+'</div>'+
									'<div id="emaildetails4'+i+'" style=" display:none;">'+item.date+'</div>'+
									/* '<div id="emaildetails5'+i+'" style=" display:none;">'+item.content+'</div>'+ */
									'<div id="emaildetails6'+i+'" style=" display:none;">'+item.downloadUrl+'</div>'+
									'<div id="emaildetails7'+i+'" style=" display:none;">'+item.read+'</div>'+
									'<div id="emaildetails8'+i+'" style=" display:none;">'+item.star+'</div>'+

									'<div id="emaildetails10'+i+'" style=" display:none;">'+item.caseID+'</div>'+
									'<div id="emaildetails11'+i+'" style=" display:none;">'+item.caseName+'</div>'+

									'<span id="emaildetails9'+i+'" style=" display:none;">'+item.attachmentname+'</span>'+

									'</td>'+
								'</tr>';
					$("#email").append(html);
					sumi=i+1;
				 });
				 $("#givePages_emailsWork").val("");
				 read000=read00;
				 $("#emailnum").html("/全部"+total+"封");
				 $("#emailsumnum").html("共搜索到" + total + "个结果");
				// $("#emailsumnum").html("共搜索到"+total+"个结果");
				 $("#email0").html("未读"+read00+"封");
				 if(sumi==0){

					 $("#email").empty();
					 $("#emailtou").empty();
						var html01 = '<table class="br04 c20" style=" width: 100%; height: 90px;" >'+
											'<tr>'+
										'</tr>'+
										'<tr>'+
									'</tr>'+
										'<tr>'+
											'<td>未找到相关内容</td>'+
										'</tr>'+
									'</table>';
						$("#email").append(html01);
				 }
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});


	}

			//快速标记获取条数
				function countType(){
					$.ajax({
						type : "POST",
						url : "<%=basePath%>emaiExcavatel/quickFlagstotal.php",
						data : {
							"caseidStr":caseidStr,
							"XYRemail":XYRemail
						},
						dataType : "json",
						async: true,
						success : function(data) {
							//"手机号"
								$("#Phone").text(data.talfori<99 ? data.talfori:99+"+");
							//"银行卡号"
								$("#regCard").text(data.yhkfori<99 ? data.yhkfori:99+"+");
							//"集装箱号"
								$("#regContainer").text(data.jzfori<99 ? data.jzfori:99+"+");
							//"邮箱号"
								$("#regEmail").text(data.yxfori<99 ? data.yxfori:99+"+");
							//"车牌号"
								$("#regLicense").text(data.cpfori<99 ? data.cpfori:99+"+");
							//"身份证号
								$("#regSFZ").text(data.sfzfori<99 ? data.sfzfori:99+"+");
							//"固定电话"
								$("#regTel").text(data.gddhfori<99 ? data.gddhfori:99+"+");
							//"价格"
								$("#regPrice").text(data.jgfori<99 ? data.jgfori:99+"+");
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							/* alert("失败!"); */
						}
					});	
				} 


			 	/* 收发件分析的单独的方法 */
				function getEmailListtofromOfD2(pageno){//收发件分析的单独的表格加载方法
					var tofromkey="";
					var tofromstartTime="";
					var tofromendTime="";
					
					var selectKey = $("#sortTypeShouFaJian").val();
					
					if(type==3){
						tofromstartTime = $("#startDate_div3").val();
						tofromendTime = $("#endDate_div3").val();
						tofromkey = $("#suspectsName_d3").val();
					}else if(type==5){
						tofromstartTime = $("#startDate_div5").val();
						tofromendTime = $("#endDate_div5").val();
						tofromkey = $("#suspectsName_d5").val();
					}else if(type==2){
						tofromstartTime = $("#startDate_demain_email22").val();
						tofromendTime = $("#endDate_demain_email22").val();   
						tofromkey = $("#emailKeyword22").val();
					}
					var test = $("spans").val();
					$("#emailtou22").empty();
					$("#email22").empty();
					var html0 = '<table class="c20 br04" style=" width: 1600px; height: 90px" >'+
					'<tr>'+
									'</tr>'+
									'<tr>'+
								'</tr>'+
									'<tr>'+
										'<td>正在搜索中...</td>'+
									'</tr>'+
								'</table>';
					$("#email22").append(html0);
					var givePages_emailsWork = $("#givePages_emailsWork22").val();
			    	if(givePages_emailsWork != ""){
			    		pageno = parseInt(givePages_emailsWork);
			    	}
			    	
					$.ajax({
						type : "POST",
						url : "<%=basePath%>emaiExcavatel/getEmailListtofrom2.php",
						data : {
							"pageIndex":pageno,
							"caseidStr":caseidStr,
							"emailstr":tofromemailstr,
							"emailkey":tofromkey,
							"startDate":tofromstartTime,
							"endDate":tofromendTime,
							"tofromType":tofromtype,
							"XYRemail":XYRemail,
							"selectKey" : selectKey
							//"emlType":emlType
						},
						dataType : "json",
						async: true,
						success : function(data) {
						    var total = data.count;
						    var read00=data.read0;
						    //分页开始
						    var sizes=10;
							var pagesum=total;
							var pagenum = pagesum / sizes;
							var length=5;  //要显示的分页页数

							if(pagenum%1!=0){
								pagenum=pagenum+(1-pagenum%1);
							}
							$("#pages_d522").empty();
							//用于删除之前显示的页数，动态添加时id名均设为order
							for(var i=1;i<=length;i++)
								  $("#orderA_522").remove();
							if(pagesum<sizes){
								var html2 = '<li class="active" id="orderA_522"><a href="#" onclick="getEmailListtofromOfD2(1)">1</a></li >';
								$("#pages_d522").after(html2);
							}else{
							if(pageno<pagenum){
								if(pageno+length-1<=pagenum){
									var html2="";
									if(pageno-2>0){
										for(var i =pageno-2;i<=pageno+length-1-2;i++){
											if(i==pageno)
												html2 += '<li class="active" id="orderA_522"><a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+i+')">'+i+'</a></li >';
											else
													html2 += '<li id="orderA_522"><a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+i+')">'+i+'</a></li >';
									   	}
									}else{
										for(var i =1;i<=length;i++){
											if(i==pageno)
												html2 += '<li class="active" id="orderA_522"><a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+i+')">'+i+'</a></li >';
											else
												html2 += '<li id="orderA_522"><a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+i+')">'+i+'</a></li >';
									   	}
									}

								$("#pages_d522").after(html2);
								//alert("pageno  "+pageno+"   pagenum "+pagenum+  "  html2 2  :"+html2);
								}/* if */
								else{
									var html2="";
									if(pagenum>=length){
										for(var i =pageno-(length-1-pagenum+pageno);i<=pagenum;i++){
											 if(i==pageno)
												html2 += '<li class="active" id="orderA_522"><a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+i+')">'+i+'</a></li >';
											else
												html2 += '<li id="orderA_522"><a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+i+')">'+i+'</a></li >';
	
										}
										$("#pages_d522").after(html2);
									}else{
										for(var i =1;i<=pagenum;i++){
											if(i==pageno)
												html2 += '<li class="active" id="orderA_522"><a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+i+')">'+i+'</a></li >';
											else
												html2 += '<li id="orderA_522"><a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+i+')">'+i+'</a></li >';
										}
										$("#pages_d522").after(html2);
									}
								}
							}else{
								if(pageno==pagenum){
									var html2="";
									for(var i =pageno-length+1>0?pageno-length+1:1;i<=pagenum;i++){
										if(i==pageno)
											html2 += '<li class="active" id="orderA_522"><a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+i+')">'+i+'</a></li >';
										else
											html2 += '<li id="orderA_522"><a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+i+')">'+i+'</a></li >';
									   	}
									$("#pages_d522").after(html2);
								}/* if */
							}
						}
							$("#tot1_d522").empty();
							$("#pages1_d522").empty();
							$("#pages2_d522").empty();
							var html3 ='<span >共'+pagesum+'条，当前'+pageno+'/'+pagenum+'页</span>';
							$("#tot1_d522").append(html3);
							var html5 = '<a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+pageno+'-1<1?1:'+pageno+'-1)"><</a>';
							$("#pages1_d522").append(html5);

							var html4 = '<a href="javascript:void(0)" onclick="getEmailListtofromOfD2('+pageno+'+1>'+pagenum+'?'+pagenum+':'+pageno+'+1)">></a>';
							$("#pages2_d522").append(html4);
							//分页结束
							$("#email22").empty();
							$("#emailtou22").empty();
							var htmltou ='<tr>'+
												'<th class="alcenter" style=" width:70px;text-align:center; ">收藏</th>'+
												'<th class="alcenter" style=" width:88px; text-align:center;">协同分析</th>'+
												'<th class="alcenter">邮件主题</th>'+
												'<th class="alcenter">发件人</th>'+
												'<th class="alcenter">收件人</th>'+
												'<th class="alcenter">发送日期</th>'+
												'<th class="alcenter" style=" text-align:center; ">附件</th>'+
										'</tr>';

							$("#emailtou22").append(htmltou);

							read000=0;
							var sumi=0;
							var emailDTOList = data.emailDTOList;
							 $.each(emailDTOList,function(i,item){
								var towho =item.toWho;//收件人
								if(towho!=null){
									var towhos = towho.split(";;");
									var towhonum = towhos.length;
									if(towhonum>1){
										towho=towhos[0]+"...";
									}
								}
								var fujian =item.attachmentname;
								var fuj ="";
								//有附件显示
								if(fujian.length>0){
									fuj ='src="<%=basePath%>template/img/fujian.png"';
								}

								var star;
								var star2=item.star;
								if(star2==0){
									star="star2";
								}if(star2==1){
									star="star";
								}
								var sub = item.subject;
								///if(sub.length > 26 ){
									//sub=sub.substring(0,26)+"...";
								//}
								if(emailKeyword!="" && emailKeyword!=null){
									var keylist=emailKeyword.split(" ");
									for(var n =0;n<keylist.length;n++){
										sub=sub.replace(keylist[n], "<font style='color: red;background-color: yellow;'>" + keylist[n] + "</font>");
									}
									//sub=sub.replace(emailKeyword, "<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
								}


								if(orKey!=null && orKey!=""){
									var orKeylist=orKey.split(" ");
									for(var n =0;n<orKeylist.length;n++){
										sub=sub.replace(orKeylist[n], "<font style='color: red;background-color: yellow;'>" + orKeylist[n] + "</font>");
									}
								}
								if(andKey!=null && andKey!=""){
									var andKeylist=andKey.split(" ");
									for(var n =0;n<andKeylist.length;n++){
										sub=sub.replace(andKeylist[n], "<font style='color: red;background-color: yellow;'>" + andKeylist[n] + "</font>");
									}
								}

								var fromstr=item.fromWho;
								var tostr=item.toWho;
								if(emailstr!=null && emailstr!=""){
									var emaillist=emailstr.split(" ");
									for(var n =0;n<emaillist.length;n++){
										fromstr=fromstr.replace(emaillist[n], "<font style='color: red;background-color: yellow;'>" + emaillist[n] + "</font>");
										tostr=tostr.replace(emaillist[n], "<font style='color: red;background-color: yellow;'>" + emaillist[n] + "</font>");
										towho=towho.replace(emaillist[n], "<font style='color: red;background-color: yellow;'>" + emaillist[n] + "</font>");
									}
								}
								var read;
								var read2 = item.read;
								
								if(read2 == 0){
									read = "read";
									var subjectStyle = '<td id ="emailboldSubjectD2'+i+'" data-toggle="modal" data-target="#myModalsD2" onclick="showdetailsD2('+i+')" style="text-align:left; vertical-align:middle;font-weight:bold; width :25%; "><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+sub+'</span></td>';
								}if(read2==1){
									read="unread";
									var subjectStyle = '<td id ="emailboldSubjectD2'+i+'" data-toggle="modal" data-target="#myModalsD2" onclick="showdetailsD2('+i+')" style="text-align:left; vertical-align:middle; width :25%; "><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+sub+'</span></td>';
								}
								
								var html =  '<tr>'+
												'<td style="text-align:center; vertical-align: middle;"><img id ="emailStar'+i+'" data-target="#myModalfavorite" data-toggle="modal" onclick="upemailstar('+i+')" src="<%=basePath%>template/img/'+star+'.png"/></td>'+
												'<td id ="emailbold'+i+'" data-toggle="modal" data-target="#myModals" onclick="showdetails('+i+')" style="text-align:center; vertical-align: middle;"><img id = "read'+i+'" src="<%=basePath%>template/img/'+read+'.png" /></td>'+
												subjectStyle+
												'<td id ="emailboldD2'+i+'" data-toggle="modal" data-target="#myModalsD2" onclick="showdetailsD2('+i+')" style="text-align:left; vertical-align: middle; width :15%;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+item.fromWho+'</span></td>'+
												'<td id ="emailboldD2'+i+'" data-toggle="modal" data-target="#myModalsD2" onclick="showdetailsD2('+i+')" style="text-align:left; vertical-align: middle; width :15%;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+towho+'</span></td>'+
												'<td id ="emailboldD2'+i+'" data-toggle="modal" data-target="#myModalsD2" onclick="showdetailsD2('+i+')" style="text-align:left; vertical-align:middle; width :10%;"><span style="display:inline-block;width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">'+item.date+'</span></td>'+
												'<td id ="emailboldD2'+i+'" data-toggle="modal" data-target="#myModalsD2" onclick="showdetailsD2('+i+')" style="text-align:center; vertical-align:middle; width :10%;"><img id = "fujian'+i+'" '+fuj+' />'+

												'<div id="emaildetails0D2'+i+'" style=" display:none;">'+item.esID+'</div>'+
												'<div id="emaildetails1D2'+i+'" style=" display:none;">'+sub+'</div>'+
												'<div id="emaildetails2D2'+i+'" style=" display:none;">'+fromstr+'</div>'+
												'<div id="emaildetails3D2'+i+'" style=" display:none;">'+tostr+'</div>'+
												'<div id="emaildetails4D2'+i+'" style=" display:none;">'+item.date+'</div>'+
												/* '<div id="emaildetails5'+i+'" style=" display:none;">'+item.content+'</div>'+ */
												'<div id="emaildetails6D2'+i+'" style=" display:none;">'+item.downloadUrl+'</div>'+
												'<div id="emaildetails7D2'+i+'" style=" display:none;">'+item.read+'</div>'+
												'<div id="emaildetails8D2'+i+'" style=" display:none;">'+item.star+'</div>'+

												'<div id="emaildetails10D2'+i+'" style=" display:none;">'+item.caseID+'</div>'+
												'<div id="emaildetails11D2'+i+'" style=" display:none;">'+item.caseName+'</div>'+
												'<span id="emaildetails9D2'+i+'" style=" display:none;">'+item.attachmentname+'</span>'+
												'</td>'+
											'</tr>';
								$("#email22").append(html);
								sumi=i+1;
							 });
							 emptyAllKey();
							 $("#givePages_emailsWork22").val("");
							 read000=read00;
							 $("#emailnum22").html("/全部"+total+"封");
							 $("#emailsumnum22").html("共搜索到" + total + "个结果");
							 //$("#emailsumnum").html("共搜索到"+total+"个结果");
							 $("#email022").html("未读"+read00+"封");
							 if(sumi==0){

								 $("#email22").empty();
								 $("#emailtou22").empty();
									var html01 = '<table class="br04 c20" style=" width: 100%; height: 90px;" >'+
														'<tr>'+
													'</tr>'+
													'<tr>'+
												'</tr>'+
													'<tr>'+
														'<td>未找到相关内容</td>'+
													'</tr>'+
												'</table>';
									$("#email22").append(html01);
							 }
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
						}
					});
				}
			 	
			 	
			 	
			 	
				/*新增左侧栏 start*/
				
				  /*一级展开*/  
				  function initEnterpriseAside() {
				  var $temp = $(".js-enterprise-aside-btn");
				  $temp.off("click");
				  $temp.on("click",function(){
				  
				    var $cur = $(this);
				    var $curNext = $cur.next(".nav");
				    /* $(".js-enterprise-aside-btn").find("i").removeClass("arrow-b1");
				    $curNext.addClass("hide"); */

				    if($curNext.hasClass("hide")){
				         $cur.find("i").addClass("arrow-b1");
				         
				        $curNext.removeClass("hide");
				        
				       
				    }else{
				      $cur.find("i").removeClass("arrow-b1");
				        $curNext.addClass("hide");
				    } 
				  })
				  
				}


				  /*二级展开*/
				  function initEnterpriseAside1() {
				  var $temp = $(".js-else");
				  $temp.off("click");
				  $temp.on("click",function(){
				   
				    var $cur = $(this);
				    var $curNext = $cur.parent().next(".next");
				    if($curNext.hasClass("mhide")){
				         $cur.find("i").addClass("arrowB02");
				        $curNext.removeClass("mhide");
				    }else{
				      $cur.find("i").removeClass("arrowB02");
				        $curNext.addClass("mhide");

				    }
				    
				  })
				  
				}
				
			
				  /*新增左侧栏 end*/
					
					/* 案件列表复选框事件 */
												
					var olda="";
			            function iselect(a,b,caseid,obj){ //其中函数字不能为select 其为JS保留字 					
			                var ids = document.getElementsByName("idsi"+a+"j"+b);                            
			                var all = document.getElementById("checkboxi"+a+"j"+b);
			               
			                caseidStr = caseid;
			                var eml = all.value;
			                
							if(a==olda){//这次和上次选的是同一个案件 下的嫌疑人 可以多选
				
							}else{//否则 先全部取消 在选择
								 $(".aline").each(function(key, item){
										if (item !== obj) {
											$(item).attr('checked', false);
											 $(".checka").attr('checked', false);
											
										} else {
											$(this).attr('checked', true);
										} 
										/* if($(".aline").attr("checked")){alert(1)} */
										/* alert($(".aline").attr("checked")) */
									 }) 
							}
			              olda=a;
								
							
			              //全选
			                for(var i=0;i<ids.length;i++){
			                    ids[i].checked=all.checked;     
			                }
			              
			              
			            	if(obj.checked){
			     				if(XYRemail==undefined || XYRemail==""){
			    					XYRemail = eml;
			    				}else{
			    					XYRemail+=","+eml;
			    				} 
			    			}else{
			    					var emls = XYRemail.split(",");
			    				var emlss="";
			    				if(emls.length>1){
			    					for(var j=0;j<emls.length;j++){
			    						var par = emls[j];
			    						 if(par!=eml){
			    							if(emlss==""){
			    								emlss=par;
			    							}else{
			    								emlss+=","+par;
			    							}
			    						}
			    					} 
			    					XYRemail=emlss;
			    				} 
			    				else{
			    					XYRemail="";
			    					caseidStr=-1;
			    				}
			    			}
			    			
			    			//alert(XYRemail);
			    			 show();//表格显示发件人排序
			    	         addresser();//收件人排序
			    	         showEmailDetil();//收发件次数过滤分析统计次数
			    	         getEmailListtofromOfD2(1);	//电子邮件数据类型列表加载
			            }
						//内容选一个与全不选处理
			            function selectNone(a,b,obj){
			                    
			            	var ids = document.getElementsByName("idsi"+a+"j"+b);                            
		                    var all = document.getElementById("checkboxi"+a+"j"+b); 		                    
			                    if(a==olda){//	                    				                    
				                    for(var i=0;i<ids.length;i++){
				                        if($("input[name='idsi"+a+"j"+b+"']:checked").length == 0){
				                            all.checked=false;   
				                        } else{all.checked=true;}    
				                    }
			    				}else{//否则 先全部取消 在选择
			    					
			    					 $(".aline").each(function(key, item){
			    							if (item !== obj) {
			    								$(item).attr('checked', false);
			    								 $(".checka").attr('checked', false);		    								
			    							} else {
			    								$(this).attr('checked', true);
			    							} 
			    							/* if($(".aline").attr("checked")){alert(1)} */
			    							/* alert($(".aline").attr("checked")) */		    							
			    						 }) 
			    							 obj.checked=true;   
						                     all.checked=true;
			    						
			    				}
			    		              olda=a;
			            }
		            <!-- session设置值 -->
		            <%
		            String emailKeyword = request.getParameter("emailKeyword");//session存取关键词
		            String caseID = request.getParameter("caseID");//session存取案件ID
		           
		            session.setAttribute("emailKeyword", emailKeyword);
		            String emailKeywords = (String) session.getAttribute("emailKeyword");
		            %>			
				
</script>
<script src="<%=basePath%>template/js/demo.js"></script>
</body>
</html>