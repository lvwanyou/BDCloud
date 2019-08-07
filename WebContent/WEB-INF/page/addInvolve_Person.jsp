<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/themes/default.css" id="skin-switcher">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
    <%-- <link href="<%=basePath%>template/css/shengshiqu/common.css"
        rel="stylesheet" />
    <link href="<%=basePath%>template/css/shengshiqu/select2.css"
        rel="stylesheet" /> --%>
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

        .labestyle {
            padding-left: 30.8em;
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

        .dropdown-menu {
            display: block;
        }

        #province, #city, #town {
            width: 220px;
            height: 30px;
        }

        .col-lg-6, .col-lg-2 {
            padding-left: 0px;
        }

    </style>
</head>
<body>
<jsp:include page="common.jsp"></jsp:include>

<div class="bg-light lter b-b wrapper-md">
    <h1 class="m-n h4">线索登记</h1>
</div>

<div class="wrapper-md">
    <div class="col-md-12" style="margin-bottom:27px;">
        <div class="panel panel-default">

            <div class="panel-body" style="padding-top: 35px;">

                <form id="form1" class="bs-example form-horizontal"
                      action="<%=basePath%>admin/addInvolve_Person.php" method="post">

                    <%-- <form id="form1" class="bs-example form-horizontal" action="<%=basePath %>addNewCase.php" method="post"> --%>
                    <div style="margin-top: -20px;margin-bottom: -20px; margin-left: 200px;">线索基本信息</div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label"><span
                                class="im_flag c11">*</span>线索编号</label>
                        <div class="col-lg-6">
                            <input class="form-control" placeholder="请输入线索编号" type="text"
                                   id="threadNum" name="threadNum">
                            <span id="addevNumlabel" class="empty c11"></span>

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label"><span
                                class="im_flag c11">*</span>线索类型</label>
                        <div class="col-lg-6">
                            <input type="checkbox" name="threadType" value="走私普通货物">走私普通货物&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="threadType" value="走私枪支">走私枪支&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="threadType" value="走私废物">走私废物&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="threadType" value="走私毒品">走私毒品&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="threadType" value="走私红油">走私红油&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="threadType" value="走私文物">走私文物&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" name="threadType" value="其它" onclick="chk(this)">其它
                            
                            <!-- <button type='button' id="useruser-defined" name="useruser-defined" onclick="userAdd()">自定义</button> -->
                            <input type="hidden" id="threadTypePlus" name="threadTypePlus"/>
                           <input class="form-control" type="text" style="width: 20%;display:inline-block;" id="other22" name="other22" placeholder="请输入其它线索类型...">
                        </div>

                        <script>
                            /* function userAdd() {
                                $("#userInput").attr("type", "text");
                            } */
                            
                            function chk(obj){
                            	  if(obj.checked){
                            	    $("#other22").show();
                            	  }else{
                            	    $("#other22").hide();
                            	    $("#other22").val("");
                            	  }
                            	}
                        </script>
                    </div>


                    <!-- 		<div class="form-group">
                                <label class="col-lg-3 control-label"><span
                                    class="im_flag c11">*</span>案件所属科室</label>
                                <div class="col-lg-5">
                                <select class="form-control c21" id="section" name="section">
                                        <option value="" >请选择...</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-3 control-label"><span
                                    class="im_flag c11">*</span>案件负责人</label>
                                <div class="col-lg-5">
                                    <input class="form-control" placeholder="请输入案件负责人" type="text"
                                        id="trustee" name="trustee" onBlur="validateCaseName()">
                                        <span id="addevNamelabel" class="empty"  style="color:red"></span>
                                </div>
                            </div>
     -->


                    <div class="form-group">
                        <label class="col-lg-3 control-label"><span
                                class="im_flag c11"></span>案件接受日期</label>
                        <div class="col-lg-6">
                            <input class="form-control" placeholder="请输入..." type="text"
                                   id="threadAcceptDate" name="threadAcceptDate" onClick="WdatePicker();">

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label"><span
                                class="im_flag c11"></span>作案日期</label>
                        <div class="col-lg-6">
                            <input class="form-control" placeholder="请输入..." type="text"
                                   id="crimeDate" name="crimeDate" onClick="WdatePicker();">

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">作案区域</label>

                        <div class="col-lg-2" style="display:inline-block;"><select style="width:100%" name="province"
                                                                                    id="province"></select></div>
                        <div class="col-lg-2" style="display:inline-block;"><select style="width:100%" name="city"
                                                                                    id="city"></select></div>
                        <div class="col-lg-2" style="display:inline-block;"><select style="width:100%" name="town"
                                                                                    id="town"></select></div>

                        <input class="form-control" placeholder="输入涉嫌单位名称、地址、海关注册编号,以空格分隔"
                               type="hidden" style="float:left;" id="cityMainParty" name="cityMainParty">

                        <button type='button' class="btn btn-info"
                                style="margin-right: 30px;" onClick="cityOnmainParty()">添加
                        </button>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label"></label>
                        <div class="col-lg-5">
                            <span id="cityLabel2"></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label"><span
                                class="im_flag c11"></span>线索来源</label>
                        <div class="col-lg-6">
                            <input type="radio" name="threadSource" value="海关移交">海关移交&nbsp;&nbsp;
                            <input type="radio" name="threadSource" value="案件深挖扩伸">案件深挖扩伸&nbsp;&nbsp;
                            <input type="radio" name="threadSource" value="举报">举报&nbsp;&nbsp;
                            <input type="radio" name="threadSource" value="在案人员举报">在案人员举报&nbsp;&nbsp;
                            <input type="radio" name="threadSource" value="情报交流">情报交流&nbsp;&nbsp;
                            <input type="radio" name="threadSource" value="其它执法移交">其它执法移交&nbsp;&nbsp;
                            <input type="radio" name="threadSource" value="国际合作">国际合作&nbsp;&nbsp;
                            <input type="radio" name="threadSource" value="其它" id="other23">其它&nbsp;&nbsp;
                            
                            <input class="form-control" type="text" style="width: 16%;display:inline-block;" name="otherText22" id="otherText22" placeholder="请输入其它来源...">
                            <%--<button type='button' id="useruser-defined2" name="useruser-defined" onclick="userAdd2()">自定义
                            </button>
                            <input type="hidden" id="userInput2" name="userInput"/>
                            <input type="hidden" name="threadSourcePlus" id="threadSourcePlus">--%>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label"><span
                                class="im_flag c11">*</span>线索内容</label>
                        <div class="col-lg-6">
                            <textarea class="form-control" rows="5" name="susItem" id="susItem" maxlength="500" placeholder="500字以内"></textarea>
                        </div>
                    </div>

                    <div style="margin-top: 20px;margin-bottom: -20px; margin-left: 200px;">涉案信息</div>
                    <div class="line line-dashed b-b line-lg pull-in"></div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">涉嫌单位</label>
                        <div class="col-lg-5">
                            <input class="form-control" placeholder="输入涉嫌单位名称、地址、海关注册编号,以空格分隔"
                                   type="text" style="float:left;" id="unitMainParty" name="unitMainParty"
                                   onBlur="validateCaseNum2()">
                            <span id="unitMainPartylabel2" class="empty c11"></span><br>
                            <span id="unitMainPartylabel3" class="empty c11"><a></a></span>
                        </div>
                        <button type='button' class="btn btn-info"
                                style="margin-right: 30px;" onClick="unitOnmainParty()">添加
                        </button>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label"></label>
                        <div class="col-lg-5">
                            <span id="unitLabel2"></span>
                        </div>
                    </div>

                    <%--导入功能，由于form中不能嵌套form，为了保证样式，把涉嫌人员的信息隐藏提交--%>
                    <input type="hidden" name="mainPartyHidden" id="mainPartyHidden">
                    <input type="reset" value="reset1" id="reset1" name="reset1" style="display: none">
                </form>

                <form class="form-horizontal" enctype="multipart/form-data" id="uploadForm">
                    <div class="form-group" style="position:relative;">
                        <label class="col-lg-3 control-label">涉嫌人员</label>
                        <div class="col-lg-5">
                            <input class="form-control" placeholder="输入涉嫌人员姓名 电话 邮箱,以空格分隔"
                                   type="text" style="float:left;" id="mainParty" name="mainParty"
                                   onBlur="validateCaseNum2()">
                            <span id="mainPartylabel2" class="empty c11"></span><br>
                            <span id="mainPartylabel4" class="empty c11"></span><br>
                            <span id="mainPartylabel3" class="empty c11"></span>
                        </div>


                        <button type='button' class="btn btn-info"
                                style="margin-right: 30px;" onClick="OnmainParty()">添加
                        </button>

                        <button type="button" class="btn btn-info" onclick="file.click()" style="margin-right: 30px;">
                            导入
                        </button>

                        <input type="file" name="fileLoad" id="file" onchange="uploadFile()"
                               style="visibility: hidden; position: absolute;">

                        <script>
                            var getSuspectID = "";

                            function uploadFile() {
                                $.ajax({
                                    url: '<%=basePath %>exThreadSuspectlist.php',
                                    type: 'POST',
                                    cache: false,
                                    data: new FormData($('#uploadForm')[0]),
                                    processData: false,
                                    contentType: false,
                                    dataType: "json",
                                    success: function (data) {
                                        alert(data.res);
                                        getSuspectID = data.suspectNameID;
                                        var sum = getSuspectID.split("/");
                                        var snm = sum.length -1;
                                       $("#mainPartylabel4").text("*导入"+snm+"条嫌疑人");
                                    },
                                    error: function () {
                                        alert("请使用指定模板进行导入！");
                                    }
                                }).done(function (res) {
                                }).fail(function (res) {
                                });
                            }
                        </script>

                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label"></label>
                        <div class="col-lg-5">
                            <span id="label2"></span>
                        </div>
                    </div>

                    <div class="form-group" style="margin-top: 54px;">
                        <div class="col-lg-offset-5 col-lg-5">
                            <button type="button" class="btn w-xs btn-info" data-toggle="modal"
                                    style="margin-right: 30px;" onclick="addcase()">提交
                            </button>
                            <script>
                                function submitInfo() {
                                    $("#form1").submit();
                                }
                            </script>
                            <button type="reset" class="btn w-xs btn-default" onclick="resetInfo()">重置</button>
                            <script>

                            </script>
                        </div>
                    </div>
                </form>

            </div>

        </div>

    </div>
</div>

<jsp:include page="footer2.jsp"></jsp:include>
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
<%-- <script src="<%=basePath%>template/js/shengshiqu/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>template/js/shengshiqu/area.js"></script>
<script type="text/javascript" src="<%=basePath%>template/js/shengshiqu/location.js"></script>
<script src="<%=basePath%>template/js/shengshiqu/select2.js"></script>
<script src="<%=basePath%>template/js/shengshiqu/select2_locale_zh-CN.js"></script> --%>
<script src="<%=basePath%>template/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>template/js/cutover/NewCity.js"></script>
<script type="text/javascript">
    new PCAS("province", "city", "town", "", "", "");
</script>
<script type="text/javascript">
    var radioStatus = 0;
	$(function(){
	$("#other22").hide();
	$("#otherText22").hide();
	  $("input[type='radio']").click(function(){
	    var id= $(this).attr("value");
	    if(id != "其它"){
	    	$("#otherText22").hide();
            radioStatus = 0;
	    }else{
	    	$("#otherText22").show();
            radioStatus = 1;
	    }
	  });
	});

    var bianhaoflag = 0;
    var mingchengflag = 0;
    //	var renyuanflag=0;
    var danweiflag = 1;
    var wupinflag = 1;


    //新建案件
    var mainPartys = "";
    var unitMainPartys = "";
    var cityMainPartys = "";
    var RelatedCompany = "";
    var RelatedObject = "";

    var threadtypes = "";
    var threadSources = "";


    function addcase() {
        var obj = document.getElementsByName("threadType");//类型
        var obj2 = document.getElementsByName("threadSource");//来源

        /* 判断单选的其它是否被选中*/
        if (radioStatus == 0) {
            $("#other23").attr("name", "threadSource");
            $("#otherText22").attr("name", "");
        } else {
            $("#other23").attr("name", "");
            $("#otherText22").attr("name", "threadSource");
        }

        for(var i=0;i<obj.length;i++){
            if(threadtypes==""){
                if(obj[i].checked){
                	if(obj[i].value=="其它"){
                	}else{
                		threadtypes=obj[i].value;
                	}
                    
                }
            }else{
             if(obj[i].checked){
               if(obj[i].value=="其它"){
                    }else{
                    	threadtypes+=","+obj[i].value;
                    }
                }
            }
        }
        if ($("#other22").val() != "") {
        	if(threadtypes==""){
        		  threadtypes=$("#other22").val();
        	}else{
                threadtypes += ","+$("#other22").val();
        	}

        }
        $("#threadTypePlus").val(threadtypes);

//        for(var i=0;i<obj2.length;i++){
//
//            if(obj2[i].checked){
//                threadSources=obj2[i].value;
//            }
//        }
//        alert("###"+threadSources);
//        if (threadSources == "") {
//            $("#threadSourcePlus").val($("userInput2").val());
//        } else {
//            $("#threadSourcePlus").val(threadSources);
//        }
//
//        alert($("#threadSourcePlus").val());

        var mainParty = mainPartys + getSuspectID;
        var unitMainParty = unitMainPartys;
        var cityMainParty = cityMainPartys;
        if (resetStatus == 0) {
            $("#mainPartyHidden").val(mainParty);
            $("#unitMainParty").val(unitMainParty);
            $("#cityMainParty").val(cityMainParty);
        }
        var threadNum =$("#threadNum").val().trim();
        var threadAcceptDate=$("#threadAcceptDate").val();
        var crimeDate=$("#crimeDate").val();
        var susItem=$("#susItem").val();

     if ($("#threadNum").val().trim().length == 0) {
         alert("线索编号不能为空！");
         return false;
     }

     if (threadTypePlus == "") {
         alert("线索类型不能为空！");
         return false;
     }

     if ($("#susItem").val().trim().length == 0) {
         alert("线索内容不能为空！");
         return false;
     }

        $("#form1").submit();
    }

    //新建案件——添加案件标签
    function biaoqian() {
        $("#label1").empty();
        var x = $("#caselabel").val().split(" ");
        var span;
        //alert(x[1]);
        for (var i = 0; i < x.length; i++) {
            var s = x[i];
            //alert(s);
            "shanchu' + i + '"
            $("#label1").append($("<span id='shanchu' class='br05 b12 span-1' style='width: 88px; height: 28px; margin-right: 20px;'>"
                + s + "<img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png' onclick='deletelabel()' /></span>"));
        }

    }

    function deletelabel() {

        //var x1 = document.getElementById("label").innerText;
        var x1 = $("#shanchu").text();
        var x2 = $("#caselabel").val().replace(x1 + " ", "");
        $("#caselabel").val(x2);
        //alert(x1);
        //alert(x2);
        //$("#label").val().replace(x1，"");

        //alert(x);
        $("shanchu").remove();
    }

    //案件名称格式是否正确
    function validateCaseName() {
        mingchengflag = 0;
        $("#addevNamelabel").text("");
        var evName = $("#caseName").val().trim();
        if (evName == null || evName == "") {
            $("#addevNamelabel").text("*案件名称不能为空！");
            document.getElementsByTagName('input')[1].style.border = '1px solid red';
            mingchengflag = 0;
        }
        if (evName.length > 50) {
            $("#addevNamelabel").text("*案件名称长度不能超过50！");
            mingchengflag = 0;
        }
        if (evName == null || evName == "") {
            $("#addevNamelabel").text("*案件名称不能为空！");
        }
        else {
            document.getElementsByTagName('input')[1].style.border = '1px solid #CFDADD';
            mingchengflag = 1;
        }

    }

    //判断案件编号是否存在
    function validateCaseNum() {
        bianhaoflag = 0;
        $("#addevNumlabel").text("");
        var evNum = $("#caseNum").val().trim();

        evNum = evNum.replace(/[^\x00-\x80]/gi, '');  //去除中文和特殊字符
        $("#caseNum").val(evNum);
//	(/[^\x00-\x80]/gi,'')====(/[^\w\.\/]/ig,'')
        if (evNum.length > 50) {
            $("#addevNumlabel").text("*您的案件编号有误");
            bianhaoflag = 0;
        }
        if (evNum == null || evNum == "") {
            $("#addevNumlabel").text("*您的案件编号有误");
            document.getElementsByTagName('input')[0].style.border = '1px solid red';
            bianhaoflag = 0;
        } else {
            document.getElementsByTagName('input')[0].style.border = '1px solid #CFDADD';
            $.ajax({
                type: "POST",
                url: "<%=basePath %>admin/checkCaseNum.php",
                dataType: "json",
                data: {
                    "caseinfonum": evNum
                },
                success: function (data) {
                    if (data.res == "exist") {
                        $("#addevNumlabel").text("*您的案件编号已存在!");
                        bianhaoflag = 0;
                    } else {
                        bianhaoflag = 1;
                    }
                },
                error: function (data) {
                    alert("网络异常，请重试!");
                    bianhaoflag = 0;
                }
            });
        }
    }

    /*用window.onload调用myfun()*/
    window.onload = onloadd;//不要括号

    //页面加载时要执行的方法 --案件类型和案件标签
    function onloadd() {
        myfun(1);
        showAllCaseType();
        showAllSection();
    }

    //获取案件标签list
    function myfun(pageno) {
        $.ajax({
            type: "POST",
            url: "<%=basePath %>getAllLabel.php",
            dataType: "json",
            data: {
                "page": pageno
            },
            success: function (data) {
                labelList = data.resData;
                var labelDivs = "";
                var noe = "";


                var sizes = 10;
                var pagesum = data.totalNum;

                var pagenum = pagesum / sizes;
                var length = 5;  //要显示的分页页数

                if (pagenum % 1 != 0) {
                    pagenum = pagenum + (1 - pagenum % 1);
                }

                $("#pages").empty();

                //用于删除之前显示的页数，动态添加时id名均设为order
                for (var i = 1; i <= length; i++)
                    $("#order").remove();

                if (pagesum < sizes) {
                    var html2 = '<li class="active" id="order"><a href="#" onclick="myfun(1)">1</a></li >';
                    $("#pages").after(html2);

                } else {

                    if (pageno < pagenum) {
                        if (pageno + length - 1 <= pagenum) {
                            var html2 = "";
                            if (pageno - 2 > 0) {
                                for (var i = pageno - 2; i <= pageno + length - 1 - 2; i++) {
                                    if (i == pageno)
                                        html2 += '<li class="active" id="order"><a href="#" onclick="myfun(' + i + ')">' + i + '</a></li >';
                                    else

                                        html2 += '<li id="order"><a href="#" onclick="myfun(' + i + ')">' + i + '</a></li >';
                                }
                            }
                            else {
                                for (var i = 1; i <= length; i++) {
                                    if (i == pageno)
                                        html2 += '<li class="active" id="order"><a href="#" onclick="myfun(' + i + ')">' + i + '</a></li >';
                                    else

                                        html2 += '<li id="order"><a href="#" onclick="myfun(' + i + ')">' + i + '</a></li >';
                                }
                            }
                            //alert(html2);
                            $("#pages").after(html2);

                        }/* if */
                        else {
                            var html2 = "";
                            if (pagenum >= length) {
                                for (var i = pageno - (length - 1 - pagenum + pageno); i <= pagenum; i++) {
                                    if (i == pageno)
                                        html2 += '<li class="active" id="order"><a href="#" onclick="myfun(' + i + ')">' + i + '</a></li >';
                                    else
                                        html2 += '<li id="order"><a href="#" onclick="myfun(' + i + ')">' + i + '</a></li >';

                                }
                                $("#pages").after(html2);
                            }
                            else {

                                for (var i = 1; i <= pagenum; i++) {


                                    if (i == pageno)
                                        html2 += '<li class="active" id="order"><a href="#" onclick="myfun(' + i + ')">' + i + '</a></li >';
                                    else
                                        html2 += '<li id="order"><a href="#" onclick="myfun(' + i + ')">' + i + '</a></li >';
                                }
                                $("#pages").after(html2);
                            }
                        }
                    }
                    else {
                        if (pageno == pagenum) {
                            var html2 = "";

                            for (var i = pageno - length + 1 > 0 ? pageno - length + 1 : 1; i <= pagenum; i++) {
                                if (i == pageno)
                                    html2 += '<li class="active" id="order"><a href="#" onclick="myfun(' + i + ')">' + i + '</a></li >';
                                else
                                    html2 += '<li id="order"><a href="#" onclick="myfun(' + i + ')">' + i + '</a></li >';
                            }
                            $("#pages").after(html2);
                        }
                        /* if */
                    }
                }

                $("#tot").empty();
                $("#pages1").empty();
                $("#pages2").empty();
                var html3 = '<span >共' + pagesum + '条，当前' + pageno + '/' + pagenum + '页</span>';
                $("#tot").append(html3);

                var html5 = '<a href="#" onclick="myfun(' + pageno + '-1<1?1:' + pageno + '-1)"><</a>';
                $("#pages1").append(html5);

                var html4 = '<a href="#" onclick="myfun(' + pageno + '+1>' + pagenum + '?' + pagenum + ':' + pageno + '+1)">></a>';
                $("#pages2").append(html4);

                $("#label1").empty();
                for (var i = 0; i <= data.resData.length; i++) {
                    var s = '<span id="label_' + data.resData[i].id + '" class="tags" title="' + data.resData[i].label + '" onclick="setLabelArea(this.id)">' + data.resData[i].label + '</span>';
                    if (i % 7 == 1) {
                        $("#label1").append($("<span name='shanchu' class='br05 b12 span-1'>" + s + "</span>"));
                    }
                    if (i % 7 == 2) {
                        $("#label1").append($("<span name='shanchu' class='b13 br06 span-2'>" + s + "</span>"));
                    }
                    if (i % 7 == 3) {
                        $("#label1").append($("<span name='shanchu' class='br11 b11 span-3'>" + s + "</span>"));
                    }
                    if (i % 7 == 4) {
                        $("#label1").append($("<span name='shanchu' class='br07 b14 span-4'>" + s + "</span>"));
                    }
                    if (i % 7 == 5) {
                        $("#label1").append($("<span name='shanchu' class='br08 b15 span-5'>" + s + "</span>"));
                    }
                    if (i % 7 == 6) {
                        $("#label1").append($("<span name='shanchu' class='br09 b17 span-6'>" + s + "</span>"));
                    }
                    if (i % 7 == 7) {
                        $("#label1").append($("<span name='shanchu' class='br10  b18 span-7'>" + s + "</span>"));
                    }
                    if (i % 7 == 0) {
                        $("#label1").append($("<span name='shanchu' class='br11 b11 span-3'>" + s + "</span>"));
                    }
                    /* 				var s = '<div id="label_'+data.resData[i].id+'" class="tags" title="'+data.resData[i].label+'" onclick="setLabelArea(this.id)">'+data.resData[i].label+'</div>';
                                    $("#label1").append($("<span name='shanchu' style='width: 90px; height: 30px; margin-right: 10px;margin-top: 10px;'>"+s+"</span>"));		 */
                }
            },
            error: function (data) {
                alert("获取标签列表失败，请重试!");
            }
        });
    }

    //获取案件标签list
    function isContains(str, substr) {
        return str.indexOf(substr) >= 0;
    }

    //点击案件标签
    function setLabelArea(id) {
        var labelText = $("#" + id + "").html();
        //alert(labelText);
        var oldLabelList = $("#caselabel").val();
        //alert(oldLabelList);
        if (oldLabelList != "" && oldLabelList != null) {
            if (!isContains(oldLabelList, labelText)) {
                $("#caselabel").val(oldLabelList + " " + labelText);
            }
        } else {
            $("#caselabel").val(labelText);
        }
    }

    //查询案件类型list
    function showAllCaseType() {

        $.ajax({
            type: "POST",
            url: "<%=basePath%>showAllCaseType.php",
            data: {
                "evID": 1
            },
            dataType: "json",
            async: true,
            success: function (data) {
                //alert(data);
                $.each(data, function (i, item) {
                    $("#caseType").append('<option>' + item.typeName + '</option>');
                });

            },
        });
    }

    function showAllSection() {

        $.ajax({
            type: "POST",
            url: "<%=basePath%>showAllSection.php",
            data: {
                "evID": 1
            },
            dataType: "json",
            async: true,
            success: function (data) {
                //alert(data);
                $.each(data, function (i, item) {
                    $("#section").append('<option>' + item.sectionName + '</option>');
                });

            },
        });
    }

    function validateCaseNum2() {
        var evNum = $("#mainParty").val().trim();
        var evNum2 = $("#label2").val().trim();
        if (evNum == null || evNum == "") {
            document.getElementsByTagName('input')[0].style.border = '1px solid red';
            return false;
        } else if (evNum2 != null && evNum2 != "") {
            $("#mainPartylabel2").empty();
            $("#mainPartylabel3").empty();
        } else {
            document.getElementsByTagName('input')[0].style.border = '1px solid #CFDADD';
            $("#mainPartylabel2").empty();
            $("#mainPartylabel3").empty();
        }
    }


    //添加嫌疑人
    //.replace(new RegExp(replaceStr,'gm'),' ')
    var deletid = 0;

    function OnmainParty() {
        resetStatus = 0;
//	renyuanflag=0;
        var s = $("#mainParty").val();
        var strArray = s.replace(/\s+/g, " ");
        var getEveryArray = strArray.split("");
        var gets = getEveryArray[getEveryArray.length - 1];
        var num = strArray.split(" ");
        var re = /^1[3|4|5|8][0-9]\d{8}$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
        var nubmer1 = num[1];
        var nubmer2 = num[2];


        var suName = num[0];
        var suPhone = num[1];
        var suEmail = num[2];

        if (gets != " ") {
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

        /* if(getMainPartyInfoSize == 2) {
            if (!re.test(nubmer1)) {
                alert("涉嫌人员填写电话格式错误！");
                return false;
            }
        } */

        if (getMainPartyInfoSize == 2) {
            if (nubmer1.indexOf("@") > 0) {
                if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(nubmer1)) {
                    alert("涉嫌人员填写邮箱格式错误!");
                    return false;
                }
            } else {
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

        if (getMainPartyInfoSize == 3) {
            if (!re.test(nubmer1)) {
                alert("涉嫌人员填写电话格式错误！");
                return false;
            }

            if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(nubmer2)) {
                alert("涉嫌人员填写邮箱格式错误!");
                return false;
            }
        }

        if (s.indexOf("/") >= 0) {
            $("#mainPartylabel2").text("*格式不正确!");
//		renyuanflag=0;
        }
        if (s.indexOf(";") >= 0) {
//		renyuanflag=0;
            $("#mainPartylabel2").text("*格式不正确!");
        }
        if (s.indexOf(",") >= 0) {
//		renyuanflag=0;
            $("#mainPartylabel2").text("*格式不正确!");
        }

        var span;
        if (num[0] != "") {
            $("#label2").append($("<input type='hidden' id='shanchu1" + deletid + "' value='" + strArray + "' type='text'><span id='shanchu2" + deletid + "' class='span-1 br05 b12' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  onclick='deletemainParty(" + deletid + ")'>"
                + strArray + "<img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></span>"));
            if (mainPartys == "") {
                mainPartys = strArray;
            } else {
                mainPartys = mainPartys + "/" + strArray;
            }
            deletid++;
        }
        var pa = $("#mainParty").val().split(" ");
        $("#mainParty").val("");
        var panum = pa[1];
        $.ajax({
            type: "POST",
            url: "<%=basePath %>checkThreadPerson.php",
            dataType: "json",
            data: {
                "SuspectPhone": strArray,
                "suName": suName,
                "suPhone": suPhone,
                "suEmail": suEmail

            },
            success: function (data) {
                //		renyuanflag=1;
                if (data[0] != null && data[0] != "") {
                    $("#mainPartylabel2").text("*该涉嫌人员信息已存在!");
                    $("#mainPartylabel3").text("*涉嫌人员:" + data[0].suspectName + "   手机号:" + data[0].suspectPhone + "   邮箱:" + data[0].suspectMail);
                    /* renyuanflag=0; */

                } else {
                    $("#mainPartylabel2").empty();
                    $("#mainPartylabel3").empty();
                }
            },
            error: function (data) {
                //alert("请刷新后重试!");
//			renyuanflag=0;
                alert("失败的");
            }
        });
    }

    //删除嫌疑人
    function deletemainParty(id) {
        var s = $("#shanchu1" + id + "").val();
        var x1 = $("#shanchu2").text();
        var x2 = $("#mainParty").val().replace(x1 + " ", "");
        $("#mainParty").val(x2);
        $("#shanchu2" + id + "").remove();
        var mainPartys2 = "";
        var mainPartyssplit = mainPartys.split("/");
        if (mainPartyssplit.length != 1) {
            for (var i = 0; i < mainPartyssplit.length; i++) {
                var par = mainPartyssplit[i];
                if (par != s) {
                    if (mainPartys2 == "") {
                        mainPartys2 = par;
                    } else {
                        mainPartys2 = mainPartys2 + "/" + par;
                    }
                }
            }
        }
        mainPartys = mainPartys2;
        if (mainPartys == "" || mainPartys == null) {
//		renyuanflag=0;
        }
        //alert(mainPartys);
    }

    //添加涉嫌单位
    //.replace(new RegExp(replaceStr,'gm'),' ')
    var unitDeletid = 0;

    function unitOnmainParty() {
        resetStatus = 0;
        var s = $("#unitMainParty").val();
        var strArray = s.replace(/\s+/g, " ");
        var getEveryArray = strArray.split("");
        var gets = getEveryArray[getEveryArray.length - 1];
        var num = strArray.split(" ");

        var suName = num[0];
        var suPhone = num[1];
        var suEmail = num[2];

        if (gets != " ") {
            var getMainPartyInfoSize = num.length;
        } else {
            var getMainPartyInfoSize = num.length - 1;
        }

        var span;
        if (num[0] != "") {
            $("#unitLabel2").append($("<input type='hidden' id='unitShanchu1" + unitDeletid + "' value='" + strArray + "' type='text'><span id='unitShanchu2" + unitDeletid + "' class='span-1 br05 b12' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  onclick='unitDeletemainParty(" + unitDeletid + ")'><a style='width:310px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;'>"
                + strArray + "</a><img style='float: right; margin-right: 5px;width:14px;display:inline-block; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></span>"));
            if (unitMainPartys == "") {
                unitMainPartys = strArray;
            } else {
                unitMainPartys = unitMainPartys + "/" + strArray;
            }
            unitDeletid++;
        }
        var pa = $("#unitMainParty").val().split(" ");
        $("#unitMainParty").val("");
        var panum = pa[1];
//        alert("SuspectPhone : " + s);
//        alert("suName : " + suName);
//        alert("suPhone : " + suPhone);
//        alert("suEmail : " + suEmail);
        $.ajax({
            type: "POST",
            url: "<%=basePath %>checkSuspectUnit.php",
            dataType: "json",
            data: {
                "SuspectPhone": strArray,
                "suName": suName,
                "suPhone": suPhone,
                "suEmail": suEmail

            },
            success: function (data) {
                //		renyuanflag=1;
                if (data[0] != null && data[0] != "") {
                    $("#unitMainPartylabel2").text("*该涉嫌单位信息已存在!");
                    $("#unitMainPartylabel3").text("*单位名称:" + data[0].name + "   单位地址:" + data[0].address + "   海关注册编号:" + data[0].customsRegistrationNumber);
                    /* renyuanflag=0; */

                } else {
                    $("#unitMainPartylabel2").empty();
                    $("#unitMainPartylabel3").empty();
                }
            },
            error: function (data) {
                //alert("请刷新后重试!");
//			renyuanflag=0;
                alert("失败的");
            }
        });

    }

    //删除涉嫌单位
    function unitDeletemainParty(id) {
        var s = $("#unitShanchu1" + id + "").val();
        var x1 = $("#unitShanchu2").text();
        var x2 = $("#unitMainParty").val().replace(x1 + " ", "");
        $("#unitMainParty").val(x2);
        $("#unitShanchu2" + id + "").remove();
        var mainPartys2 = "";
        var mainPartyssplit = unitMainPartys.split("/");
        if (mainPartyssplit.length != 1) {
            for (var i = 0; i < mainPartyssplit.length; i++) {
                var par = mainPartyssplit[i];
                if (par != s) {
                    if (mainPartys2 == "") {
                        mainPartys2 = par;
                    } else {
                        mainPartys2 = mainPartys2 + "/" + par;
                    }
                }
            }
        }
        unitMainPartys = mainPartys2;
    }


    var cityDeletid = 0;

    function cityOnmainParty() {
        resetStatus = 0;
        var s = $("#province").val() + " " + $("#city").val() + " " + $("#town").val();
        $("#cityMainParty").val(s);
        var strArray = s.replace(/\s+/g, " ");
        var getEveryArray = strArray.split("");
        var gets = getEveryArray[getEveryArray.length - 1];
        var num = strArray.split(" ");
        var span;
        if (num[0] != "") {
            $("#cityLabel2").append($("<input type='hidden' id='cityShanchu1" + cityDeletid + "' value='" + s + "' type='text'><span id='cityShanchu2" + cityDeletid + "' class='span-1 br05 b12' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  onclick='cityDeletemainParty(" + cityDeletid + ")'>"
                + s + "<img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></span>"));
            if (cityMainPartys == "") {
                cityMainPartys = s;
            } else {
                cityMainPartys = cityMainPartys + "/" + s;
            }
            cityDeletid++;
        }
        var pa = $("#cityMainParty").val().split(" ");
        $("#cityMainParty").val("");
        var panum = pa[1];
    }

    //删除city
    function cityDeletemainParty(id) {
        var s = $("#cityShanchu1" + id + "").val();
        var x1 = $("#cityShanchu2").text();
        var x2 = $("#cityMainParty").val().replace(x1 + " ", "");
        $("#cityMainParty").val(x2);
        $("#cityShanchu2" + id + "").remove();
        var mainPartys2 = "";
        var mainPartyssplit = cityMainPartys.split("/");
        if (mainPartyssplit.length != 1) {
            for (var i = 0; i < mainPartyssplit.length; i++) {
                var par = mainPartyssplit[i];
                if (par != s) {
                    if (mainPartys2 == "") {
                        mainPartys2 = par;
                    } else {
                        mainPartys2 = mainPartys2 + "/" + par;
                    }
                }
            }
        }
        cityMainPartys = mainPartys2;
    }

    /////////////////////////////////////////////////////
    //添加涉案公司
    //.replace(new RegExp(replaceStr,'gm'),' ')
    var deletid_com = 0;

    function OnRelatedCompany() {
        danweiflag = 1;

        $("#danweired").empty();
        danweiflag = 1;
        var danwei = $("#relatedCompany").val();
        var pattern = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（-）——|{}【】‘；：”“'。，、？]";
        for (var i = 0; i < pattern.length; i++) {
            if (danwei.indexOf(pattern[i]) >= 0) {
                $("#danweired").text("*格式不正确!");
                danweiflag = 0;
            }
        }
        if (danweiflag != 0) {
            var s = $("#relatedCompany").val();
            var num = s.split(" ");
            var span;
            if (num[0] != "") {
                for (var i = 0; i < num.length; i++) {

                    if (num[i] != "" && num[i] != " ") {//alert(num.length+"++++"+deletid_com);
                        $("#label_CompanyList").append($("<input type='hidden' id='shanchu1_com" + deletid_com + "' value='" + num[i] + "' type='text'><span id='shanchu2_com" + deletid_com + "' class='span-1 br05 b12' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  >"
                            + num[i] + "<a onclick='deleteRelatedCompany(" + deletid_com + ")'><img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></a></span>"));
                        //alert("s"+num.length);
                        if (RelatedCompany == "") {
                            RelatedCompany = num[i];
                        } else {
                            RelatedCompany = RelatedCompany + " " + num[i];
                        }
                        deletid_com++;
                    }
                }
            }
            var pa = $("#relatedCompany").val().split(" ");
            $("#relatedCompany").val("");
            var panum = pa[1];
        }

    }

    //删除涉案公司
    function deleteRelatedCompany(id) {
        var s = $("#shanchu1_com" + id + "").val();
        var x1 = $("#shanchu2_com").text();
        var x2 = $("#relatedCompany").val().replace(x1 + " ", "");
        $(relatedCompany).val(x2);
        $("#shanchu2_com" + id + "").remove();
        var RelatedCompanysplit = RelatedCompany.split(" ");
        RelatedCompany = "";
        if (RelatedCompanysplit.length != 1) {
            for (var i = 0; i < RelatedCompanysplit.length; i++) {
                var par = RelatedCompanysplit[i];
                if (par != s) {
                    if (RelatedCompany == "") {
                        RelatedCompany = par;
                    } else {
                        RelatedCompany = RelatedCompany + " " + par;
                    }
                }
            }
        }
        if (RelatedCompany == "" || RelatedCompany == null) {
            danweiflag = 0;
        }
        //alert(mainPartys);
    }

    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    //添加涉案物品
    //.replace(new RegExp(replaceStr,'gm'),' ')
    var deletid_relobj = 0;

    function OnRelatedObject() {
        wupinflag = 1;

        $("#wupinred").empty();
        wupinflag = 1;
        var wupin = $("#relatedObject").val();
        var pattern = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（-）——|{}【】‘；：”“'。，、？]";
        for (var i = 0; i < pattern.length; i++) {
            if (wupin.indexOf(pattern[i]) >= 0) {
                $("#wupinred").text("*格式不正确!");
                //alert("943");
                wupinflag = 0;
            }
        }

        if (wupinflag != 0) {

            var s = $("#relatedObject").val();
            var num = s.split(" ");
            var span;
            if (num[0] != "") {
                for (var i = 0; i < num.length; i++) {
                    if (num[i] != "" && num[i] != " ") {
                        $("#label_RelatedObject").append($("<input type='hidden' id='shanchu1_relobj" + deletid_relobj + "' value='" + num[i] + "' type='text'><span id='shanchu2_relobj" + deletid_relobj + "' class='br05 b12 span-1' style='width: 380px; height: 28px; margin-right: 20px;margin-bottom:5px'  >"
                            + num[i] + "<a onclick='deleteRelatedObject(" + deletid_relobj + ")'><img style='float: right; margin-right: 5px; margin-top: 7px;'src='<%=basePath%>template/img/x.png'  /></a></span>"));

                        if (RelatedObject == "") {
                            RelatedObject = num[i];
                        } else {
                            RelatedObject = RelatedObject + " " + num[i];
                        }
                        deletid_relobj++;
                    }
                }
            }
            var pa = $("#relatedObject").val().split(" ");
            $("#relatedObject").val("");
            var panum = pa[1];
        }
    }

    //删除涉案物品
    function deleteRelatedObject(id) {
        var s = $("#shanchu1_relobj" + id + "").val();
        var x1 = $("#shanchu2_relobj").text();
        var x2 = $("#relatedObject").val().replace(x1 + " ", "");
        $("relatedObject").val(x2);
        $("#shanchu2_relobj" + id + "").remove();
        var RelatedObjectsplit = RelatedObject.split(" ");
        RelatedObject = "";
        if (RelatedObjectsplit.length != 1) {
            for (var i = 0; i < RelatedObjectsplit.length; i++) {
                var par = RelatedObjectsplit[i];
                if (par != s) {
                    if (RelatedObject == "") {
                        RelatedObject = par;
                    } else {
                        RelatedObject = RelatedObject + " " + par;
                    }
                }
            }
        }
        if (RelatedObject == "" || RelatedObject == null) {
            wupinflag = 0;
            //alert("915");
        }
        //alert(mainPartys);
    }

    /////////////////////////////////////////////////////

    //涉嫌组织格式判断
    function danwei() {
        $("#danweired").empty();
        danweiflag = 1;
        var danwei = $("#relatedCompany").val();
        var pattern = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（-）——|{}【】‘；：”“'。，、？]";
        for (var i = 0; i < pattern.length; i++) {
            if (danwei.indexOf(pattern[i]) >= 0) {
                $("#danweired").text("*格式不正确!");
                danweiflag = 0;
            }
        }
    }

    //涉嫌物品格式判断
    function wupin() {
        $("#wupinred").empty();
        wupinflag = 1;
        var wupin = $("#relatedObject").val();
        var pattern = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（-）——|{}【】‘；：”“'。，、？]";
        for (var i = 0; i < pattern.length; i++) {
            if (wupin.indexOf(pattern[i]) >= 0) {
                $("#wupinred").text("*格式不正确!");
                //alert("943");
                wupinflag = 0;
            }
        }
    }

    var resetStatus = 0;
    function resetInfo() {
        new PCAS("province", "city", "town", "", "", "");
        $("#mainPartylabel2").empty();
        $("#mainPartylabel3").empty();
        $("#label2").empty();
        $("#label_CompanyList").empty();
        $("#label_RelatedObject").empty();
        $("#reset1").click();

        $("#unitLabel2").empty();
        $("#unitMainPartylabel2").empty();
        $("#unitMainPartylabel3").empty();

        $("#cityLabel2").empty();

        $("#label2").empty();
        $("#mainPartylabel2").empty();
        $("#mainPartylabel3").empty();
        $("#mainPartyHidden").val("");
        resetStatus = 1;
    }


    /* 图表自适应  start*/
    /* $(window).resize( function(){
        window.location.reload();
    } ); */
    /* 图表自适应  end*/
</script>
</body>
</html>