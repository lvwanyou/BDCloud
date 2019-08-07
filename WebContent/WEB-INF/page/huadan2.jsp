<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
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

        .c06 clabel {
            margin-right: 14px;
            font-weight: bold;
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
            border-radius: 50%;
            float: right;
            text-align: center;
        }

        .fade_suc, .fade_fas, .fade_poi {
            width: 275px;
            height: 80px;
            border-radius: 12px;
            position: fixed;
            margin: 0 auto;
            margin-top: 94px;
            line-height: 80px;
            display: none;
            left: 50%;
            margin-left: -138px;
        }

        .fade_suc {
            background-color: rgba(0, 165, 47, .5);
        }

        .fade_fas {
            background-color: rgba(242, 23, 23, .5);
        }

        .fade_poi {
            background-color: rgba(71, 94, 156, .5);
        }

        .fade_suc img, .fade_fas img, .fade_poi img {
            width: 40px;
            height: 40px
            display: inline-block;
            margin: 20px 20px 20px 70px;
            vertical-align: middle;
        }

        .fade_poi img {
            margin: 20px 20px 20px 35px;
        }

        .fade_suc span, .fade_fas span, .fade_poi span {
            color: #fff;
            font-size: 16px;
        }
    </style>


</head>
<body>
<jsp:include page="common.jsp"></jsp:include>
<div class="bg-light lter b-b wrapper-md">
    <h1 class="m-n h4">话单分析导入</h1>
</div>

<div class="wrapper-md">
    <div class="col-md-12" style="margin-bottom:27px;">

        <div class="row">
            <div class="panel panel-default">
                <div class="panel-body" style="padding: 0 0 15px;overflow-x:scroll;">
                    <table id="datatable1" cellpadding="0" cellspacing="0" border="0"
                           class="form-inline datatable table table-striped table-bordered table-hover">
                        <input type="hidden" name="caseID" id="caseID" value="${caseID}">
                        <input type="hidden" name="evType" id="evType" value="${evTypeUser}">
                        <input type="hidden" name="evName" id="evName" value="${evNameUser}">
                        <input type="hidden" name="comment" id="comment" value="${commentUser}">
                        <input type="hidden" name="dataTypes" id="dataTypes" value="${dataTypesUser}">
                        <input type="hidden" name="fangshi" id="fangshi" value="${fangshiUser}">
                        <thead>
                        <tr>
                            <c:forEach items="${lObjects }" var="cas" varStatus="idxStatus">
                                <th class="zhongdui" style="width: 5%;">
                                    <select name="selectth" id="${idxStatus }">
                                        <option value=""></option>
                                        <%--<option value="本机姓名">用户姓名</option>--%>
                                        <option value="主叫号码">主叫号码</option>
                                        <option value="对方号码">对方号码</option>
                                        <option value="呼叫类型">呼叫类型</option>
                                        <%--<option value="通话日期">通话日期</option>--%>
                                        <option value="通话时间">通话时间</option>
                                        <option value="通话时长">通话时长</option>
                                        <option value="通话所在地">通话所在地</option>
                                        <option value="通话类型">通话类型</option>
                                        <option value="LAC">地区代码（LAC）</option>
                                        <option value="CID">小区代码（CID）</option>
                                    </select>
                                </th>
                            </c:forEach>
                        </tr>
                        </thead>
                        <tbody id="gonggao">
                        <c:forEach items="${list1 }" var="cas" varStatus="idxStatus">
                            <tr class="gradeX">
                                <c:forEach items="${cas }" var="cas1" varStatus="idxStatus1">
                                    <td>${cas1}</td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        </tfoot>
                    </table>
                    <div style="text-align:center;">
                        <button type="button" class="btn btn-info b23 c02" style="width: 75px; height: 30px;"
                                onclick="addgonggao();">导入
                        </button>
                        <a type="button" class="btn btn-info b23 c02" style="display:inline-block;width: 75px; height: 30px;background: #fff;
    border-color: #ccc;color: #333 !important;margin-left: 20px;" href="javascript:void(0)" onclick="return backUser()">返回</a>
                    </div>
                    <script>
                        function backUser() {
                            window.history.back();
                        }

                        var status = 0;
                        function addgonggao() {
                            var a = new Array();
                            a = document.getElementsByName("selectth");
                            for (var i = 0; i < a.length; i++) {
                                var nameValue = a[i].value;
                                if (nameValue.length != 0) {
                                    status = 1;
                                }
                            }

                            if (status == 0) {
                                alert("请选择类型！");
                                return false;
                            }

                            var selectElements = document.getElementsByName("selectth");
                            var getCaseID = $("#caseID").val();
                            var arrs = new Array();
                            var strs = "";
                            for (var i = 0; i < selectElements.length; i++) {
                                //获取元素的value值
                                if (i == selectElements.length - 1) {
                                    strs = strs + selectElements[i].value;
                                } else {
                                    strs = strs + selectElements[i].value + ";";
                                }
                            }
                            // alert(strs);
                            $("#loadingDiv").show();
                            jQuery.ajax({
                                url: '<%=basePath%>importExcel.php',
//                                    dataType : "json",//数据格式
                                type: "post",//请求方式
                                data: {
                                    "strs": strs,
                                    "caseID": getCaseID,
                                    "evType": $("#evType").val(),
                                    "evName": $("#evName").val(),
                                    "comment": $("#comment").val(),
                                    "dataTypes": $("#dataTypes").val(),
                                    "fangshi": $("#fangshi").val()
                                },
                                success: function (data) { //如何发送成功
                                    alert("提交成功！");
                                    window.location.href = "<%=basePath%>getcaselist.php";
                                },
                                error: function () {

                                    alert("导入失败，请重试！");
                                },
                            });
                        }
                    </script>
                </div>
            </div>
        </div>
    </div>

    <div id="loadingDiv" aria-hidden="false" style="display: none;" class="bootbox modal fade in" tabindex="-1"
         role="dialog">
        <div class="modal-dialog" style="width: 182px;margin-top: 23%;">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="bootbox-body">
                        <p class="msg">提交中，请稍后...</p>
                        <div><img id="flashAdImg" src="<%=basePath %>template/img/loading.gif" alt="loading" height="16"
                                  width="115"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</div>
<!--批量导出弹窗 -->

<!--删除成功弹窗 -->

<jsp:include page="footer2.jsp"></jsp:include>
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>

</body>
</html>