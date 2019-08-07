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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/cloud-admin.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/workspace.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
    <script src="<%=basePath%>template/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
    <style>

        #table-modal_adduser .form-group, #table-modal .form-group {
            width: 50%;
            float: left;
        }

        #table-modal_adduser .form-group .control-label, #table-modal .form-group .control-label {
            padding-top: 7px;
            padding-right: 0px;
        }

        #table-modal_adduser .form-group .col-lg-8, #table-modal .form-group .col-lg-8 {
            padding-left: 0px;
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

        #vert {
            width: 100px;
            height: 20px;
            position: relative;
        }

        #vert ul li {
            float: left;
            position: absolute;
            bottom: 0px;
            text-align: center;
            font-weight: bold;
            width: 100px;
            list-style: none;
        }


        #vert1 {
            width: 100px;
            height: 20px;
            position: relative;
        }

        #vert1 ul li {
            float: left;
            position: absolute;
            bottom: 0px;
            text-align: center;
            font-weight: bold;
            width: 100px;
            list-style: none;
        }

        
    </style>

<link rel="stylesheet" href="<%=basePath%>template/css/style.css">
</head>

<body>
<jsp:include page="common.jsp"></jsp:include>
<div class="bg-light lter b-b wrapper-md">
    <h1 class="m-n h4">标签统计</h1>
</div>

<div class="col-md-12" style="margin-top: 1%;">
    <div class="panel panel-default" style="width:100%;height:350px;">
        
        	<div id="main5" style = "height:350px;"></div>
   
    </div>
</div>

<div class="col-md-12" style="margin-bottom:27px;">
    <form class="form-inline" role="form">
        <label>标签名称：</label>
        <div class="form-group">
        	<input type="text" value="" style="display: none;" />
            <input id="labelName" name="labelName" class="form-control" placeholder="请输入..." onkeydown="onKeyDown(event)"
                   type="text" style="width: 600px; margin-left: -4px;"/>
        </div>

        <button type="button" class="btn btn-info" style="border-radius:5px;width: 75px; height: 33px; margin-bottom: 1px; margin-left: 20px"
                onclick="showCase(1)">搜索
        </button>
        <a id="urlMail" href="javascript:void(0)">
            <div class="btn btn-info" style="border-radius:5px;width: 75px; height: 33px; margin-bottom: 1px;" onclick="exportMail()">导出</div>
        </a>
    </form>
</div>

<div class="col-md-12" style="margin-top: 1%;">
    <div class="panel panel-default">
        <div class="panel-body" style="padding: 0px;">
            <table id="datatable" class="table table-striped table-hover br04" style="margin-bottom: 0px;">
                <thead class="panel-heading">
                <tr>
                    <th class="c12" style="text-align: left;font-weight:600;font-size:14px;">标签名称</th>
                    <th class="c12" style="text-align: left;font-weight:600;font-size:14px;">案件次数</th>
                    <th class="c12" style="text-align: left;font-weight:600;font-size:14px;">嫌疑人次数</th>
                    <th class="c12" style="text-align: left;font-weight:600;font-size:14px;">数据次数</th>
                </tr>
                </thead>
                <tbody class="c12" id="tbcont20Count" style="text-align: left !important;">

                </tbody>
            </table>
        </div>

        <div class="alcenter" style="font-size: 14px; padding-top: 20px; padding-bottom: 20px;">
            <div class="pagecount inline" style="height: 29px; padding-left: 40%;">
                <span id="totCount">
                
                </span>
            </div>
            <div class="pagebar inline" style="position: absolute; right: 138px; height: 29px;">
                <ul class="pagination pagination-sm" style="margin: 0;">
                    <li id="pages1Count"></li>

                    <li id="pagesCount"></li>

                    <li id="pages2Count"></li>
                </ul>
            </div>
            <div style="float: right;margin-right: 11px;">
					<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages" name="givePages" onkeydown="onKeyDown(event)"/>
					<button type="button" class="btn" onclick="showCase(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
			</div>
        </div>
    </div>
</div>
<jsp:include page="footer2.jsp"></jsp:include>
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath%>template/js/jQuery-Cookie/jquery.cookie.min.js"></script>
<script src="<%=basePath%>template/js/script_left.js"></script>
<script src="<%=basePath%>template/js/cutover/js.js"></script>
<script src="<%=basePath%>template/js/cutover/echarts.js"></script>
<script src="<%=basePath%>template/js/cutover/dataTool.min.js"></script>

<script type="text/javascript">

window.onresize = function(){
	myChart.resize();
	}

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main5'));

    myChart.showLoading();
    $.get('<%=basePath%>template/js/cutover/les-miserables.gexf', function (xml) {
        myChart.hideLoading();

        var graph = echarts.dataTool.gexf.parse(xml);
        var categories = [];
        for (var i = 0; i < 9; i++) {
            categories[i] = {};
        }
        graph.nodes.forEach(function (node) {
            node.itemStyle = null;
            node.symbolSize = 10;
            node.value = node.symbolSize;
            node.category = node.attributes.modularity_class;
            // Use random x, y
            node.x = node.y = null;
            node.draggable = true;
        });

        option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
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
            legend: {
                data: ['案件次数', '嫌疑人次数', '数据次数']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: []
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: []
        };
        //异步加载数据
        var labelName = [];
        var labelCaseNum = [];
        var labelSuspectNum = [];
        var labelDataNum = [];
        $.ajax({
            type: "POST",
            url: "<%=basePath%>findLabel.php",
            data: {},
            dataType: "json",
            async: true,
            success: function (data) {
                $.each(data.logs, function (i, item) {

                    //标签为空不在插件中占位
                    if (item.labelCaseNum != 0 || item.labelSupectNum != 0 || item.labelDataNum != 0) {
                        labelName.push(item.labelName);
                        labelCaseNum.push(item.labelCaseNum);
                        labelSuspectNum.push(item.labelSupectNum);
                        labelDataNum.push(item.labelDataNum);
                    }

                    myChart.setOption({
                        xAxis: {
                            data: labelName
                        },
                        series: [

                            {
                                name: '案件次数',
                                type: 'bar',
                                data: labelCaseNum
                            },
                            {
                                name: '嫌疑人次数',
                                type: 'bar',
                                stack: '广告',
                                data: labelSuspectNum
                            },
                            {
                                name: '数据次数',
                                type: 'bar',
                                stack: '广告',
                                data: labelDataNum
                            }
                        ]
                    });

                    //标签为空也在插件中占位的操作
                    /*labelName.push(item.labelName);
                    labelCaseNum.push(item.labelCaseNum);
                    labelSuspectNum.push(item.labelSupectNum);
                    labelDataNum.push(item.labelDataNum);
                myChart.setOption({
                    xAxis:{
                        data:labelName
                    },
                    series:[

                        {
                            name:'案件次数',
                            type:'bar',
                            data:labelCaseNum
                        },
                        {
                            name:'嫌疑人次数',
                            type:'bar',
                            stack: '广告',
                            data:labelSuspectNum
                        },
                        {
                            name:'数据次数',
                            type:'bar',
                            stack: '广告',
                            data:labelDataNum
                        }
                    ]
                });*/

                });
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
               // alert("失败");
            }
        });

        myChart.setOption(option);
    }, 'xml');
</script>
<script>
    window.onload = showCase(1);
    //获取域名分析的table展示信息
    function showCase(pageno) {
        var labelName = $("#labelName").val();
        var givePages = $("#givePages").val();
    	if(givePages!=""){
    		pageno = parseInt(givePages);
    	}
        $.ajax({
            type: "POST",
            url: "<%=basePath%>findLabel.php",
            data: {
                "pageno": pageno,
                "labelName": labelName
            },
            dataType: "json",
            async: true,
            success: function (data) {
                var sizes = 5;
                var pagesum = data.totalNum;
                var pagenum = pagesum / sizes;
                var length = 5;  //要显示的分页页数

                if (pagenum % 1 != 0) {
                    pagenum = pagenum + (1 - pagenum % 1);
                }
                $("#pagesCount").empty();

                //用于删除之前显示的页数，动态添加时id名均设为order
                for (var i = 1; i <= length; i++)
                    $("#order").remove();

                if (pagesum < sizes) {
                    var html2 = '<li class="active" id="order"><a href="javascript:void(0)" onclick="showCase(1)">1</a></li >';
                    $("#pagesCount").after(html2);
                } else {

                    if (pageno < pagenum) {
                        if (pageno + length - 1 <= pagenum) {
                            var html2 = "";
                            if (pageno - 2 > 0) {
                                for (var i = pageno - 2; i <= pageno + length - 1 - 2; i++) {
                                    if (i == pageno)
                                        html2 += '<li class="active" id="order"><a href="javascript:void(0)" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                    else

                                        html2 += '<li id="order"><a href="javascript:void(0)" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                }
                            }
                            else {
                                for (var i = 1; i <= length; i++) {
                                    if (i == pageno)
                                        html2 += '<li class="active" id="order"><a href="javascript:void(0)" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                    else

                                        html2 += '<li id="order"><a href="javascript:void(0)" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                }
                            }
                            $("#pagesCount").after(html2);

                        }/* if */
                        else {
                            var html2 = "";
                            if (pagenum >= length) {
                                for (var i = pageno - (length - 1 - pagenum + pageno); i <= pagenum; i++) {
                                    if (i == pageno)
                                        html2 += '<li class="active" id="order"><a href="javascript:void(0)" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                    else
                                        html2 += '<li id="order"><a href="javascript:void(0)" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                }
                                $("#pagesCount").after(html2);
                            }
                            else {

                                for (var i = 1; i <= pagenum; i++) {


                                    if (i == pageno)
                                        html2 += '<li class="active" id="order"><a href="javascript:void(0)" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                    else
                                        html2 += '<li id="order"><a href="javascript:void(0)" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                }
                                $("#pagesCount").after(html2);
                            }

                        }
                    }
                    else {
                        if (pageno == pagenum) {
                            var html2 = "";

                            for (var i = pageno - length + 1 > 0 ? pageno - length + 1 : 1; i <= pagenum; i++) {
                                if (i == pageno)
                                    html2 += '<li class="active" id="order"><a href="javascript:void(0)" onclick="showCase(' + i + ')">' + i + '</a></li >';
                                else
                                    html2 += '<li id="order"><a href="javascript:void(0)" onclick="showCase(' + i + ')">' + i + '</a></li >';
                            }
                            $("#pagesCount").after(html2);
                        }
                    }
                }

                $("#totCount").empty();
                $("#pages1Count").empty();
                $("#pages2Count").empty();
                var html3 = '<span >共' + pagesum + '条，当前' + pageno + '/' + pagenum + '页</span>';
                $("#totCount").append(html3);

                var html5 = '<a href="javascript:void(0)" onclick="showCase(' + pageno + '-1<1?1:' + pageno + '-1)"><</a>';
                $("#pages1Count").append(html5);

                var html4 = '<a href="javascript:void(0)" onclick="showCase(' + pageno + '+1>' + pagenum + '?' + pagenum + ':' + pageno + '+1)">></a>';
                $("#pages2Count").append(html4);
                $("#tbcont20Count").empty();
                $.each(data.logs, function (i, item) {
                    var html01 = '+<tr>' +
                        '<td style="text-align: left;">' + item.labelName + '</td> ' +
                        '<td style="text-align: left;">' + item.labelCaseNum + '次' + '</td>' +
                        '<td style="text-align: left;">' + item.labelSupectNum + '次' + '</td>' +
                        '<td style="text-align: left;">' + item.labelDataNum + '次' + '</td>' +
                        '</tr>+';
                    $("#tbcont20Count").append(html01);
                });
                $("#givePages").val("");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
               // alert("失败");
            }
        });
    }

    function exportMail() {
        var getResult = confirm("确定导出?");
        if (getResult) {
            $("#urlMail").attr("href", "<%=basePath %>ExportLabelStatistics.php");
        } else {
            $("#urlMail").attr("href", "javascript:void(0)");
        }
    }

  //回车搜索事件
    function onKeyDown(event){
       var e = event || window.event || arguments.callee.caller.arguments[0];
       if(e && e.keyCode==13){ // enter 键
    	   showCase(1);
       }
    }
</script>
</body>
</html>