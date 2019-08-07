<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/easyui.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/easyui2.css" /> --%>

    <script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
    <script src="<%=basePath%>template/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var div_height = $("#wrapper-md-ds").height() - 40;
            $("#ds_div").css("height", div_height + "px");

            getAllDepartments();
        });

        function getAllDepartments() {
            $.ajax({
                type: "POST",
                url: "<%=basePath %>admin/getAllDepartments.php",
                dataType: "json",
                data: {},
                success: function (data) {
                    jQuery.each(data, function (i, item) {
                        $("#addpartment").append('<option>' + item.departmentName + '</option>');
                        $("#editpartment").append('<option>' + item.departmentName + '</option>');
                    });
                    $("#addpartment").append('<option></option>');
                    $("#editpartment").append('<option></option>');
                    getAllSections(1);
                    getAllSections(2);
                },
                error: function (data) {
                }
            });
        }

        function getAllSections(type) {
            var department = "";
            if (type == 1) {
                department = $("#addpartment").val();
                $("#addsection").empty();
            } else {
                department = $("#editpartment").val();
                $("#editsection").empty();
            }

            if (department == "") {
                if (type == 1) {
                    $("#addsection").append('<option></option>');
                } else {
                    $("#editsection").append('<option></option>');
                }
            } else {
                $.ajax({
                    type: "POST",
                    url: "<%=basePath %>admin/getAllSections.php",
                    dataType: "json",
                    data: {"department": department},
                    success: function (data) {
                        jQuery.each(data, function (i, item) {
                            if (type == 1) {
                                $("#addsection").append('<option>' + item.sectionName + '</option>');
                            } else {
                                $("#editsection").append('<option>' + item.sectionName + '</option>');
                            }
                        });
                    },
                    error: function (data) {
                    }
                });
            }
        }

        //折叠
        function testOfSearch(obj) {
            var div1 = document.getElementById("searchOfForm");
            if (div1.style.display == "block") {
                div1.style.display = "none";
                obj.src = "<%=basePath %>template/img/downXia.png";
            } else {
                div1.style.display = "block";
                obj.src = "<%=basePath %>template/img/addevidence.png";
            }
        }
    </script>
    <script type="text/javascript" src="<%=basePath%>template/js/jquery.easyui.min.js"></script>
    <style>

        ul {
            list-style: none;
        }

        a {
            text-decoration: none;
        }

        .find_nav {
            position: relative;
            top: 0;
            z-index: 99;
            display: -moz-box;
            display: -webkit-box;
            display: box;

        }

        .ui-autocomplete {
            z-index: 999999 !important;
            height: 100px;
            overflow: scroll;
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
            width: 100%;
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
            margin-right: 14px;
        }

        .row {
            margin-left: 0px;
        }

        .panel-header, .datagrid-header {
            display: none;
        }

        .datagrid-body::-webkit-scrollbar {
            width: 10px;
            height: 10px;
        }

        .datagrid-header td {
            border: none;
        }

        .panel {
            margin-bottom: 20px;
            text-align: inherit;
        }

        .panel-body {
            padding: 15px;
            border: none;
            font-size: 14px;
        }

        .panel-header {
            padding: 0;
            border-width: 0;
            border-top-width: 1px;
        }

        .datagrid-header-row {
            width: 100%;
        }

        .datagrid-body td {
            height: 100%;
            border-width: 0 0 0 0;
        }

        .panel.datagrid.easyui-fluid {
            overflow: hidden;
            text-align: left;
            border: 0;
            margin: 0;
        }

        .col-md-2 .panel-body {
            padding: 0 0 0 0;
        }

        .col-md-2 .panel {
            border-bottom: none;
        }

        #table-modal_adduser .form-group, #table-modal_edituser .form-group {
            width: 50%;
            float: left;
        }

        #table-modal_adduser .form-group .control-label, #table-modal_edituser .form-group .control-label {
            padding-top: 7px;
            padding-right: 0px;
        }

        #table-modal_adduser .form-group .col-lg-8, #table-modal_edituser .form-group .col-lg-8 {
            padding-left: 0px;
        }

        .tree-title {
            cursor: pointer;
            padding: 0 8px;
        }

        .tree-title {
            font-size: 14px;
            height: 30px;
            line-height: 30px;
        }

        .red {
            color: #329933 !important;
        }

        /* 暂存归档 */
        .yellow {
            color: #0000CE !important;
        }

        /* 未处置 */
        .green {
            color: #999900 !important;
        }

        /* 积累归档 */
        .blue {
            color: #FF6768 !important;
        }

        /* 线索经营 */
        .black {
            color: #FF9A00 !important;
        }

        /* 线索转出 */
        .shixiao {
            color: #993267 !important;
        }

        /* 已失效 */
        .lian {
            color: #020000 !important;
        }

        /* 已情报立案 */

        .red1 {
            color: red !important;
        }
    </style>
</head>
<body>
<jsp:include page="common.jsp"></jsp:include>
<div class="bg-light lter b-b wrapper-md">
    <h1 class="m-n h4">线索重复列表</h1>
</div>

<div class="" style="padding-left: 40px;padding-top: 15px;">
    <a href="<%=basePath%>admin/cluelist.php" class="btn w-xs btn-info" style="margin-right: 30px;">返回</a>
</div>

<div id="wrapper-md" class="wrapper-md" style="height:500px;padding-bottom: 20px;">

    <div class="col-md-12" style="margin-bottom:27px;">

        <div id="d1" style="width:100%;">

            <div class="row">
                <div class="panel panel-default">
                    <div id="loadDiv_user"
                         style="text-align: center;margin-top: 10px;position: absolute;left:50%;z-index:99;display: none;">
                        <img alt="" src="<%=basePath%>template/img/loading3.gif">
                    </div>
                    <div class="panel-heading">线索重复列表</div>
                    <div class="panel-body" style="padding: 0 0 15px;">
                        <input type="hidden" name="threadID" id="threadID" value="${threadID}">
                        <table id="datatable" class="table table-striped table-hover br04" style="text-align: center;">
                            <thead>
                            <tr>
                                <th class="alcenter" style="width: 6%"><input type="checkbox" id="ckall"
                                                                              onclick="selectAll()"/></th>
                                <th class="alcenter" style="width: 6%">嫌疑人姓名</th>
                                <th class="alcenter" style="width: 6%">手机号码</th>
                                <th class="alcenter" style="width: 8%">电子邮箱</th>
                                <th class="alcenter" style="width: 6%">单位名称</th>
                                <th class="alcenter" style="width: 8%">地址</th>
                                <th class="alcenter" style="width: 6%">海关注册编号</th>
                                <th class="alcenter" style="width: 6%">线索编号</th>
                                <th class="alcenter" style="width: 6%">线索类型</th>
                                <th class="alcenter" style="width: 6%">作案区域</th>
                                <th class="alcenter" style="width: 8%">线索内容</th>
                                <th class="alcenter" style="width: 6%">创建人</th>
                                <th class="alcenter" style="width:6%">分局</th>
                            </tr>
                            </thead>

                            <tbody id="tbcont">

                            </tbody>
                        </table>
                        <div class="alcenter" style="font-size: 14px">
                            <div class="pagecount inline" style="height: 29px;">
                                <span>共<span id="totalSpan"></span>条，当前<span id="currSpan"></span>页</span>
                            </div>
                            <div class="pagebar inline" style="position: absolute; right: 125px; height: 29px;">
                                <ul id="pageUL" class="pagination pagination-sm" style="margin: 0;">

                                    <li id="main_pages1"></li>

                                    <li id="main_pages"></li>

                                    <li id="main_pages2"></li>
                                </ul>
                            </div>
                            <div style="float: right;margin-right: 11px;">
                                <input class="form-control" type="text"
                                       style="width:52px;height:28px;border-radius:2px; display: inline;"
                                       id="givePages_one" name="givePages_one" onkeydown="onKeyDown_one(event)"/>
                                <button type="button" class="btn" onclick="searchUser(1)"
                                        style="width:52px;height:28px;line-height: 12px;">跳转
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 编辑用户成功弹框 -->
<div class="modal fade" id="table-modal_editUser_success" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 476px;">
        <div class="modal-content">
            <input type="hidden" name="editUser_successes" id="editUser_successes">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
                <h3 class="modal-title c20" id="myModalLabel" style="border-left: none;">编辑线索</h3>
            </div>
            <div class="modal-body">编辑线索成功！</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>

<!--批量导出弹窗 -->
<div class="modal fade" id="tanchuan" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 476px;">
        <div class="modal-content">
            <input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
                <h3 class="modal-title" id="myModalLabel" style="color: #58666e;border-left: none;">批量导出</h3>
            </div>
            <div class="modal-body">请选择要导出的数据！</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!--删除成功弹窗 -->
<div class="modal fade" id="del_tanchuan" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 476px;">
        <div class="modal-content">
            <input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
                <h3 class="modal-title" id="myModalLabel" style="color: #58666e;border-left: none;">删除线索</h3>
            </div>
            <div class="modal-body">删除线索成功！</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer2.jsp"></jsp:include>
</body>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
<script src="<%=basePath%>template/js/cutover/js.js"></script>
<script type="text/javascript">
    $(function () {
        $("#d2").hide();
        searchUser(1);
    })
    var types = "1";

    function d1() {

        types = "1";
        $("#d1").show();
        $("#d2").hide();
        searchUser(1);

    }

    /* 	function d2() {
            types = "2";
            $("#d1").hide();
            $("#d2").show();
            searchunitlist(1);
        } */


    //获取线索id 去数据表查
    var evID = "";

    function addCase(clueID) {
        evID = clueID;
    }

    //分页
    /* 	function subForm(pageno){
            $("#pageno").val(pageno);
            $("#searchForm").submit();
        }
        */
    /* 	function ord(){

        }
        function ord2(){
            //searchunitlist(1);

            document.getElementById("leng").click();
        }  */



    //查询人员

    function selectAll() {
        var ckall = document.getElementById("ckall");
        var ck = document.getElementsByName("cb");
        for (var i = 0; i < ck.length; i++) {
            ck[i].checked = ckall.checked;
        }
    }

    //window.onload = searchUser(1);
    function searchUser(pageno) {
        //var uname = $("#uname").val();
        //	var userprevilige = $("#userprevilige").val();
        //var pageno = $("#pageno").val();
        var name = $("#name").val();
        var threadType = $("#threadType1").val();//线索类型
        var threadSource = $("#threadSource1").val();//线索来源
        var disposal = $("#disposal1").val();//线索处置
        var threadNum = $("#threadNum1").val();//线索编号

        var susItem = $("#susItem1").val();//线索内容
        var status = $("#status1").val();//线索状态 */
        var endDate = $("#endDate").val();
        var startDate = $("#startDate").val();
        var givePages_one = $("#givePages_one").val();
        var regions = $("regions").val();
        if (givePages_one != "") {
            pageno = parseInt(givePages_one);
        }
        $("#pageno").val(pageno);
        $.ajax({
            type: "POST",
            url: "<%=basePath %>list/Involve_Person_repeat.php",
            dataType: "json",
            data: {
                "threadID": $("#threadID").val(),
                "pageno": pageno
            },
            success: function (data) {
                var len = data.resData.length;
                var i = 0;
                var res = data.resData;
                //	var res2 = data.resData2;
                var result = "";
                var tmp;
                var totalPages = data.totalPages;//总页数
                var nowPage = data.nowPage;//当前页数
                var totalNum = data.totalNum;//总数
                var length = 5;  //要显示的分页页数
                var pagesize = 10 //每页显示个数
                var pagenum = totalNum / pagesize;
                if (pagenum % 1 != 0) {
                    pagenum = pagenum + (1 - pagenum % 1);
                }

                $("#totalSpan").text(totalNum);
                $("#currSpan").text(nowPage + "/" + totalPages);
                $("#tbcont").empty();
                var html1;
                var html2;
                for (var i = 0; i < len; i++) {
                    var a = res[i].departmentNamesss55;
                    /* 					var falg = 0;
                                        var falg2 =0;
                                        for(var j =0; j < totalNum; j++){
                                            if(res[i].phone == res2[j].phone){
                                                falg++;
                                                if(falg>=2){
                                                    html1= '<td style="color:red;">'+res[i].phone+'</td>';
                                                    break;
                                                }
                                            }else{
                                                html1= '<td>'+res[i].phone+'</td>';
                                            }
                                        }
                                        for(var k = 0; k < totalNum; k++){
                                            if(res[i].email == res2[k].email){
                                                falg2++;
                                                if(falg2>=2){
                                                    html2= '<td style="color:red;">'+res[i].email+'</td>';
                                                    break;
                                                }
                                            }else{
                                                html2= '<td>'+res[i].email+'</td>';
                                            }
                                        } */
                    var disposalStatus = res[i].disposal;
                    var red = "";
                    if (disposalStatus == "未处置") {
                        red = "yellow";
                    } else if (disposalStatus == "暂存归档") {
                        red = "red";
                    } else if (disposalStatus == "积累归档") {
                        red = "green";
                    } else if (disposalStatus == "线索经营") {
                        red = "blue";
                    } else if (disposalStatus == "线索转出") {
                        red = "black";
                    }


                    var cross = res[i].crossNum;
                    var numbe = "";
                    if (cross > 1) {
                        numbe = "red1";
                    }
                    $("#tbcont").append(
                        '<tr><td><input type="checkbox" value="' + res[i].id + '" name="cb" id="cb" ></td>' +
                        '<td>' + res[i].suspectName + '</td>' +
                        '<td>' + res[i].suspectPhone + '</td>' +
                        '<td>' + res[i].suspectMail + '</td>' +
                        '<td>' + res[i].unitName + '</td>' +
                        '<td>' + res[i].unitAddress + '</td>' +
                        '<td>' + res[i].unitCustomsRegistrationNumber + '</td>' +
                        '<td>' + res[i].threadNum + '</td>' +
                        '<td>' + res[i].threadType + '</td>' +
                        '<td>' + res[i].region + '</td>' +
                        '<td>' + res[i].susItem + '</td>' +
                        '<td>' + res[i].reportPerson + '</td>' +
                        '<td>' + res[i].substation + '</td>' +
                        '</tr>');
                    $("#givePages_one").val("");
                }
                //$("#pageUL").html("");
                $("#main_pages").empty();
                var tmp = "";
                for (var i = 1; i <= length; i++)
                    $("#order").remove();
                if (totalNum < pagesize) {
                    tmp += '<li class="active" id="order"><a href=\"#\" onclick="searchUser(1)">1</a></li>';
                    //var html2 = '<li class="active" id="order"><a href="javascript: void(0);" onclick="showAllTicket(1)">1</a></li >';
                    $("#main_pages").after(tmp);
                } else {

                    if (pageno < pagenum) {
                        if (pageno + length - 1 <= pagenum) {
                            tmp = "";
                            if (pageno - 2 > 0) {
                                for (var i = pageno - 2; i <= pageno + length - 1 - 2; i++) {
                                    if (i == pageno)
                                        tmp += '<li class="active" id="order"><a href="javascript: void(0);" onclick="searchUser(' + i + ')">' + i + '</a></li >';
                                    else
                                        tmp += '<li id="order"><a href="javascript: void(0);" onclick="searchUser(' + i + ')">' + i + '</a></li >';
                                }
                            }
                            else {
                                for (var i = 1; i <= length; i++) {
                                    if (i == pageno)
                                        tmp += '<li class="active" id="order"><a href="javascript: void(0);" onclick="searchUser(' + i + ')">' + i + '</a></li >';
                                    else

                                        tmp += '<li id="order"><a href="javascript: void(0);" onclick="searchUser(' + i + ')">' + i + '</a></li >';
                                }
                            }
                            $("#main_pages").after(tmp);
                        }/* if */
                        else {
                            tmp = "";
                            if (pagenum >= length) {
                                for (var i = pageno - (length - 1 - pagenum + pageno); i <= pagenum; i++) {
                                    if (i == pageno)
                                        tmp += '<li class="active" id="order"><a href="javascript: void(0);" onclick="searchUser(' + i + ')">' + i + '</a></li >';
                                    else
                                        tmp += '<li id="order"><a href="javascript: void(0);" onclick="searchUser(' + i + ')">' + i + '</a></li >';
                                }
                                $("#main_pages").after(tmp);
                            }
                            else {
                                for (var i = 1; i <= pagenum; i++) {
                                    if (i == pageno)
                                        tmp += '<li class="active" id="order"><a href="javascript: void(0);" onclick="searchUser(' + i + ')">' + i + '</a></li >';
                                    else
                                        tmp += '<li id="order"><a href="javascript: void(0);" onclick="searchUser(' + i + ')">' + i + '</a></li >';
                                }
                                $("#main_pages").after(tmp);
                            }
                        }
                    }
                    else {
                        if (pageno == pagenum) {
                            tmp = "";
                            for (var i = pageno - length + 1 > 0 ? pageno - length + 1 : 1; i <= pagenum; i++) {
                                if (i == pageno)
                                    tmp += '<li class="active" id="order"><a href="javascript: void(0);" onclick="searchUser(' + i + ')">' + i + '</a></li >';
                                else
                                    tmp += '<li id="order"><a href="javascript: void(0);" onclick="searchUser(' + i + ')">' + i + '</a></li >';
                            }
                            $("#main_pages").after(tmp);
                        }
                        /* if */
                    }
                }
                $("#main_pages1").empty();
                $("#main_pages2").empty();

                var html5 = '<a href="javascript: void(0);" onclick="searchUser(' + pageno + '-1<1?1:' + pageno + '-1)"><</a>';
                $("#main_pages1").append(html5);
                var html4 = '<a href="javascript: void(0);" onclick="searchUser(' + pageno + '+1>' + pagenum + '?' + pagenum + ':' + pageno + '+1)">></a>';
                $("#main_pages2").append(html4);
                //$("#tbcont").empty();
                /* if(parseInt(nowPage) == 1)
                    tmp += "<li><a href=\"#\" onclick=\"searchUser('" + (parseInt(nowPage)) + "')\">&lt;</a></li>";
                else
                    tmp += "<li><a href=\"#\" onclick=\"searchUser('" + (parseInt(nowPage) - 1) + "')\">&lt;</a></li>";

                var at = Math.floor((parseInt(nowPage)-1)/9);
                at = at*9;
                var full = at + 9;
                var max;
                if(full > totalPages)
                    max = totalPages;
                else
                    max = full;
                var p = 0;
                for(p = at + 1; p <= max; p++){
                    if(p == nowPage)
                        tmp += "<li><a href=\"#\" class=\"active\" onclick=\"searchUser('"+p+"')\">"+p+"</a></li>";
                    else
                        tmp += "<li><a href=\"#\" onclick=\"searchUser('"+p+"')\">"+p+"</a></li>";
                }

                if(nowPage == totalPages)
                    tmp += "<li><a href=\"#\" onclick=\"searchUser('" + (parseInt(nowPage)) + "')\">&gt;</a></li>";
                else
                    tmp += "<li><a href=\"#\" onclick=\"searchUser('" + (parseInt(nowPage) + 1) + "')\">&gt;</a></li>";
                $("#pageUL").html(tmp); */
            },
            error: function (data) {
                $("#loadDiv_user").hide();
                alert("查询失败，请重试!");
            }
        });
    }

    //导出
    function getidss(id) {
        window.location = "<%=basePath %>admin/goClueDetails.php?id=" + id;

    }

    function getOnclick(id) {
        window.location = "<%=basePath %>list/Involve_Person_repeat.php?caseid=" + id;
    }

    function exportFile() {
        //获取所有选中项
        var stunos = [];
        $("input[name='cb']:checked").each(function () {
            stunos.push($(this).val());
        });
        var stunoStr = "";
        //确认导出

        if (stunos.length > 0) {
            var rs = confirm("确定选中所选项?");
            if (rs) {
                for (var i = 0; i < stunos.length; i++) {
                    if (i == 0) {
                        stunoStr += stunos[i];
                    } else {
                        stunoStr += "," + stunos[i];
                    }

                }
                $("#url").attr("href", "<%=basePath %>admin/Exportclue.php?stunoStr=" + stunoStr);
            } else {
                $("#url").attr("href", "javascript:void(0)");
            }
        } else {
            $('#tanchuan').modal('show');
            /* 	alert("请选择需要导出的数据"); */
        }
    }

    //删除线索
    function deleteid(id) {
        if (confirm("确认删除该条线索吗")) {
            $.ajax({
                type: "post",
                url: "<%=basePath%>deleteclue.php",
                dataType: "json",
                data: {
                    "id": id
                },
                success: function (data) {
                    if (data.res == "succ") {
                        $("#del_tanchuan").modal('show');
                        //location.reload();
                        searchUser(1);
                    }

                },
                error: function (data) {
                    alert("删除失败");
                    //alert(JSON.stringify(data));
                }
            });
        } else {
            return false;
        }
    }


    //编辑线索
    function setEditInfo(id, name, phone, emali, region, threadType, susItem) {
        $("#editId").val(id);
        $("#names").val(name);
        $("#phone").val(phone);
        $("#emali").val(emali);
        $("#region").val(region);
        $("#threadType").val(threadType);
        $("#susItem").val(susItem);

        return true;
    }

    //编辑线索
    function editUser() {
        var editId = $("#editId").val();
        var name = $("#names").val();
        var phone = $("#phone").val();
        var emali = $("#emali").val();
        var region = $("#region").val();
        var threadType = $("#threadType").val();
        var susItem = $("#susItem").val();

        if (name == null || name == "") {
            $("#mistake_edit").html("姓名不能为空！");
            return false;
        }
        if (phone == null || phone == "") {
            $("#mistake_edit").html("手机号不能为空！");
            return false;
        }
        if (threadType == null || threadType == "") {
            $("#mistake_edit").html("线索类型不能为空！");
            return false;
        }
        if (susItem == null || susItem == "") {
            $("#mistake_edit").html("涉嫌事项不能为空！");
            return false;
        }

        $.ajax({
            type: "POST",
            url: "<%=basePath %>editclue.php",
            dataType: "json",
            data: {
                "id": editId,
                "name": name,
                "phone": phone,
                "emali": emali,
                "region": region,
                "threadType": threadType,
                "susItem": susItem,
            },
            success: function (data) {
                $('#table-modal_edituser').modal('hide');
                if (data.res == "succ") {
                    searchUser(1);
                    $('#table-modal_editUser_success').modal('show');

                    //searchUser(1);
//					alert("编辑用户成功");
//					window.location.reload();
                    //searchUser(1);
                }
            },
            error: function (data) {
                alert("编辑用户失败，请重试!");
            }
        });
    }


    //跳转至数据列表界面
    function getid(clueid) {
        //var evid = $("#id_right").text(); ${result.agreeStatus == '同意'}
        var agreeStatus1 = document.getElementById("agreeStatuss1" + clueid).innerText;   //是否同意
        var statuss22 = document.getElementById("chooseIDss" + clueid).innerText;
        if (statuss22 == "导入" && agreeStatus1 == "同意") {
            window.location.href = "<%=basePath %>admin/addclue.php?case_summary_id=" + clueid;
        } else if (statuss22 == "查看") {
            window.location.href = "<%=basePath %>admin/clueevidencelist.php?clueid=" + clueid;
        } else {
            alert("此线索未审批通过！");
        }
    }

    //跳转至数据列表界面
    function getid2(clueid) {
        //var evid = $("#id_right").text();
        window.location.href = "<%=basePath %>admin/unitevidencelist.php?clueid=" + clueid;
    }

    //回车搜索事件
    function onKeyDown_one(event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 13) { // enter 键
            searchUser(1);
        }
    }

    //回车搜索事件
    function onKeyDown_two(event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 13) { // enter 键
            searchunitlist(1);
        }
    }

    //回车搜索事件
    function onKeyDown(event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 13) { // enter 键
            searchUser(1);
        }
    }

    //回车搜索事件   _company
    function onKeyDown_company(event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 13) { // enter 键
            searchunitlist(1);
        }
    }

    //弹框显示的案件列表
    function showcase() {
        var casenumorname = document.getElementById("caseinfo").value;
        $.ajax({
            type: "POST",
            url: "<%=basePath%>admin/getCaseOfEmail.php",
            data: {
                "casenumorname": casenumorname
            },
            dataType: "json",
            async: true,
            success: function (data) {
                $("#tbcont").empty();
                var htmlhead = '<tr>' +
                    '<td class="td_left"><input type="checkbox" id="ckall" onclick="selectAll()"/></td>' +
                    '<td class="td_left c12" style="font-weight:600;font-size:14px;">案件编号</td>' +
                    '<td class="td_right c12" style="font-weight:600;font-size:14px;">案件名称</td>' +
                    '</tr>';
                $("#tbcont").append(htmlhead);
                var caseidStrSplit = caseidStr.split(" ");
                var aaaa = "";
                var html = "";
                $.each(data, function (i, item) {
                    var caseflag = 0;
                    for (var j = 0; j < caseidStrSplit.length; j++) {
                        if (caseidStrSplit[j] == item.id) {
                            caseflag = 1;
                        }
                    }
                    if (caseflag == 1) {
                        html += '<tr>' +
                            '<td class="td_left"><input id="caselist' + i + '" type="checkbox" checked="checked" name="idss" value="' + item.caseName + ' ' + item.id + '" onclick="chooseCase(this.id);" />' +
                            '<td class="td_left">' + item.caseNum + '</td>' +
                            '<td class="td_right" id="">' + item.caseName + '</td>' +
                            '</tr>';
                    } else {
                        html += '<tr>' +
                            '<td class="td_left"><input id="caselist' + i + '" type="checkbox"  name="idss" value="' + item.caseName + ' ' + item.id + '" onclick="chooseCase(this.id);" />' +
                            '<td class="td_left">' + item.caseNum + '</td>' +
                            '<td class="td_right" id="">' + item.caseName + '</td>' +
                            '</tr>';
                    }
                });
                $("#tbcont").append(html);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }

    //Ñ¡ÔñËùÓÐ¸´Ñ¡¿ò
    function selectAll() {
        var ckall = document.getElementById("ckall");
        var ck = document.getElementsByName("idss");
        for (var i = 0; i < ck.length; i++) {
            if (ck[i].checked == false) {
                ck[i].checked = ckall.checked;
                selectCase(i);
            }
        }
        if ($("input[name='idss']:checked").length <= $("input[name='idss']").length && ckall.checked == false) {
            for (var j = 0; j < ck.length; j++) {
                if (ck[j].checked == true) {
                    ck[j].checked = false;
                    selectCase(j);
                }
            }
        }

    }

    var caseidStr = "";

    function showChooses(id) {
        var ckall = document.getElementById(id);
        ck[i].checked = ckall.checked;
    }

    //Ñ¡ÔñÄ³¸ö¸´Ñ¡¿ò°¸¼þ½«°¸¼þÐÅÏ¢Õ¹Ê¾ÔÚÒ³ÃæÉÏ
    function chooseCase(id) {
        var caseInfo = document.getElementById(id);
        var caseId = caseInfo.value.split(" ")[1];
        var caseName = caseInfo.value.split(" ")[0];
        if (caseInfo.checked) {
            if (caseidStr == "") {
                caseidStr = caseId;
            } else {
                caseidStr = caseidStr + " " + caseId;
            }
            if (caseName.length > 6) {
                caseName = caseName.substring(0, 6) + "...";
            }
            var html = '<span class="br05 b12 span-1" id="shanchu2' + caseId + '" style="width: 150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; height: 30px;margin-right: 15px;margin-top: 5px;" onclick="deletemainParty(this.id,' + caseId + ')">' + caseName +
                '<img style="vertical-align:middle;" src="<%=basePath%>template/img/x.png" /></span>';
            $("#spans").append(html);
        } else {
            $("#shanchu2" + caseId + "").remove();
            var caseidStrSplit = caseidStr.split(" ");
            var caseidStrNew = "";
            if (caseidStrSplit.length > 0) {
                for (var i = 0; i < caseidStrSplit.length; i++) {
                    var par = caseidStrSplit[i];
                    if (par != caseId) {
                        if (caseidStrNew == "") {
                            caseidStrNew = par;
                        } else {
                            caseidStrNew = caseidStrNew + " " + par;
                        }
                    }
                }
            }
            caseidStr = caseidStrNew;
        }
    }

</script>
</html>