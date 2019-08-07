<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
    <script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
    <script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
    <script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
    

    <script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
    <script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
    <script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>template/js/script_left.js"></script>
    <script src="<%=basePath%>template/js/cutover/js.js"></script>
    <script src="<%=basePath%>template/My97DatePicker/WdatePicker.js"></script>
    <script src="<%=basePath%>template/js/cutover/echarts.js"></script>
</head>
<body>
<jsp:include page="common.jsp"></jsp:include>
<div class="bg-light lter b-b wrapper-md">
    <h1 class="m-n h4">数据类型统计</h1>
</div>
<div class="container">
    <div class="col-md-12" style="margin-bottom:27px;">
        <div class="row" style="margin-top: 1%;">
            <div class="panel panel-default">
                <div class="panel-heading">搜索</div>
                <div class="panel-body">
                    <form class="form-inline" role="form" id="searchForm" action="<%=basePath%>" method="post">
                        <div class="form-group">
                            <label>日期范围：</label>
                            <div class="form-group">
                                <input id="startDate" name="createdTime" value=""
                                       class="form-control" placeholder="请输入起始时间" type="text" style="width:168px;margin-left: -4px;"
                                       onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'new Date()\'}'});"/>
                            </div>
                            <div class="form-group">
                                <label for="" class="clabel">&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</label>
                                <input id="endDate" name="endTime" value=""
                                       class="form-control" placeholder="请输入结束时间" type="text"
                                       onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:new Date()});"/>
                            </div>
                        </div>

                        <a id="url12" href="#">
                            <div class="btn btn-info" style="width:80px;margin-left: 20px;" onclick="gettypestatistics()">搜索</div>
                        </a>
                        <a id="url12_export" href="#">
                            <div class="btn btn-info" style="width:80px;margin-left: 20px;" onclick="exportFile()">导出</div>
                        </a>
                    </form>
                </div>
            </div>
            <div class="panel panel-default" style="width:100%;">
                <div class="panel-body" style="height:300px;">
                    <div id="list" style="height:300px;"></div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">数据导入记录</div>
                <div class="panel-body" style="padding: 0 0 15px;">
                    <table id="datatable" class="table table-striped table-hover br04"
                           style="text-align: center;">
                        <thead>
                        <tr>
                            <th class="alcenter" style="text-align: left">数据类型</th>
                            <th class="alcenter" style="text-align: left">数据大小</th>
                            <th class="alcenter" style="text-align: left">操作</th>
                        </tr>
                        </thead>
                        <tbody id="tbcont">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal right fade" id="myModal" tabindex="-1" style="width: 100%; height: 100%; margin: auto;">
    <div class="panel panel-default" style="width: 60%; height: 80%; margin: 3% auto;">
        <div class="panel-heading">数据列表</div>
        <div class="panel-body" style="height: 90%;overflow-y: scroll;">
            <table id="datatable" class="table table-striped table-hover br04"
                   style="text-align: center;">
                <thead>
                <tr>
                    <th class="alcenter" style="text-align: left;width:20%">数据名称</th>
                    <th class="alcenter" style="text-align: left;width:15%">数据大小</th>
                    <th class="alcenter" style="text-align: left;width:15%">数据类型</th>
                    <th class="alcenter" style="text-align: left;width:15%">关联案件</th>
                    <th class="alcenter" style="text-align: left;width:15%">操作人</th>
                    <th class="alcenter" style="text-align: left;width:20%">导入日期</th>

                </tr>
                </thead>
                <tbody id="tbcont1">

                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="footer2.jsp"></jsp:include>
<%-- <script src="<%=basePath%>template/build/dist/echarts.js"></script> --%>
<script type="text/javascript">


window.onresize =function(){

	    myChart.resize();

	}  
    function conver(limit) {
        var size = "";
        var limit1 = limit * 1024;

        if (limit1 < 0.1 * 1024 * 1024 * 1024) { //如果小于0.1GB转化成MB
            size = (limit1 / (1024 * 1024)).toFixed(4) + "MB";
        } else {
            size = (limit1 / (1024 * 1024)).toFixed(4) + "MB";
        }

        var sizestr = size + "";
        var len = sizestr.indexOf("\.");
        var dec = sizestr.substr(len + 1, 2);

        return sizestr;
    }


    window.onload = gettypestatistics(1);

    function gettypestatistics() {
        // alert()
         var myChart = echarts.init(document.getElementById('list'));
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        window.onresize =function(){

    	    myChart.resize();

    	}
        //alert(startDate);
        $.ajax({
            type: "POST",
            url: "<%=basePath%>chart/typestatistics.php",
            data: {
                "startDate": startDate,
                "endDate": endDate
            },
            dataType: "json",
            async: true,
            success: function (data) {
                var email = data.电子邮件;
                var phone = data.手机取证;
                var doc = data.综合文档;
                var call = data.电子话单;
            /*     var hkdb = data.黑客数据; */
                var pic = data.图片资料;

                var email1 = conver(email);
                var phone1 = conver(phone);
                var doc1 = conver(doc);
                var call1 = conver(call);
/*                 var hkdb1 = conver(hkdb); */
                var pic1 = conver(pic);
                //alert(pic1);
                //alert(email1);
                var email2 = email1.split("M")[0];

                var phone2 = phone1.split("M")[0];
                var doc2 = doc1.split("M")[0];
                var call2 = call1.split("M")[0];
/*                 var hkdb2 = hkdb1.split("M")[0]; */
                var pic2 = pic1.split("M")[0];
                //alert(doc2);

                var da = [];
                var node1 = {
                    value: email2,
                    name: "电子邮件"
                };

                var node3 = {
                    value: doc2,
                    name: "综合文档"
                };
                var node4 = {
                    value: call2,
                    name: "电子话单"
                };
/*                 var node5 = {
                    value: hkdb2,
                    name: "黑客数据"
                }; */
                var node6 = {
                    value: pic2,
                    name: "图片资料"
                };
                da.push(node1);

                da.push(node3);
                da.push(node4);
/*                 da.push(node5); */
                da.push(node6);
                //alert(da.length);
               
                var weatherIcons = {};

                option = {

                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"

                        /*         	 function(a)
                                     {
                                     var relVal = "";
                                     relVal = a[0]+"°C<br/>";
                                     relVal += a[1]+"°C";
                                     return relVal;
                                     } */

                    },
                    title: {
                        left: 'center',
                        text: '数据列表( 单位:MB)',
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
                    series: [
                        {
                            type: 'pie',
                            radius: '65%',
                            center: ['50%', '50%'],
                            selectedMode: 'single',
                            data: da,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)',
                                    label: {
                                        formatter: function (d) {
                                            Number(d.percent).toFixed(5);
                                        }
                                    }
                                }
                            }
                        }
                    ]
                };

                myChart.setOption(option);
                //alert(hkdb);
                $("#tbcont").empty();
               

                var html = '<tr>' +
                    '<td style="text-align: left">电子邮件</td>' +
                    '<td style="text-align: left">' + email1 + '</td>' +
                    '<td style="text-align: left"><a href="#" class="btnA" data-toggle="modal" data-target="#myModal" onclick="showEvidence(1)">查看</a></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td style="text-align: left">综合文档</td>' +
                    '<td style="text-align: left">' + doc1 + '</td>' +
                    '<td style="text-align: left"><a href="#" class="btnA" data-toggle="modal" data-target="#myModal" onclick="showEvidence(2)">查看</a></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td style="text-align: left">电子话单</td>' +
                    '<td style="text-align: left">' + call1 + '</td>' +
                    '<td style="text-align: left"><a href="#" class="btnA" data-toggle="modal" data-target="#myModal" onclick="showEvidence(3)">查看</a></td>' +
                    '</tr>' +
/*                     '<tr>' +
                    '<td style="text-align: left">黑客数据</td>' +
                    '<td style="text-align: left">' + hkdb1 + '</td>' +
                    '<td style="text-align: left"><a href="#" class="btnA" data-toggle="modal" data-target="#myModal" onclick="showEvidence(5)">查看</a></td>' +
                    '</tr>' + */
                    '<tr>' +
                    '<td style="text-align: left">图片资料</td>' +
                    '<td style="text-align: left">' + pic1 + '</td>' +
                    '<td style="text-align: left"><a href="#" class="btnA" data-toggle="modal" data-target="#myModal" onclick="showEvidence(6)">查看</a></td>' +
                    '</tr>'
                    /* 	'<tr data-toggle="modal" data-target="#myModal">'+
                        '<td style="text-align: left">手机取证</td>'+
                        '<td style="text-align: left">'+phone+'</td>'+
                        '</tr>' */;
                $("#tbcont").append(html);
            }

        });
    }


    //window.onload = showEvidence(1);

    function showEvidence(evType) {
    	$("#tbcont1").empty();
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();

        $.ajax({
            type: "POST",
            url: "<%=basePath%>chart/getEvidences.php",
            data: {
                "evType": evType,
                "startDate": startDate,
                "endDate": endDate
            },
            dataType: "json",
            async: true,
            success: function (data) {
                var docs = data.pictureList;

                $("#tbcont1").empty();

                var evidenceList = data.evidenceList;

                //导入日期从新到旧的排列方式
                for (var i = data.evidenceList.length - 1; i >= 0; i--) {
                    var type = data.evidenceList[i].evType;
                    var evType = "";
                    if (type == 1) {
                        evType = "电子邮件";
                    } else if (type == 2) {
                        evType = "综合文档";
                    } else if (type == 3) {
                        evType = "电子话单";
                    } else if (type == 4) {
                        evType = "手机取证";
                    }/*  else if (type == 5) {
                        evType = "黑客数据";
                    } */ else if (type == 6) {
                        evType = "图片资料";
                    }

                    if (data.evidenceList[i].evSize > 0) {
                        var size = conver(data.evidenceList[i].evSize);
                        //alert(size);
                        var html = '<tr>' +
                            '<td style="display: none;" id="evid">' + data.evidenceList[i].id + '</td>' +
                            '<td style="text-align: left">' + data.evidenceList[i].evName + '</td>' +
                            '<td style="text-align: left">' + size + '</td>' +
                            '<td style="text-align: left">' + evType + '</td>' +
                            '<td style="text-align: left">' + data.evidenceList[i].comment + '</td>' +
                            '<td style="text-align: left">' + data.evidenceList[i].evAdmin + '</td>' +
                            '<td style="text-align: left">' + data.evidenceList[i].addTime + '</td>' +
                            '</tr>';

                        $("#tbcont1").append(html);
                    }
                }
                //导入日期从新到旧的排列方式
                /* $.each(evidenceList,function(i,item){
                    var type = item.evType;
                    var evType="";
                    if (type==1) {
                          evType = "电子邮件";
                      } else if (type==2) {
                          evType = "综合文档";
                      } else if (type==3) {
                          evType = "电子话单";
                      } else if (type==4) {
                          evType = "手机取证";
                      } else if (type==5) {
                          evType = "黑客数据";
                      } else if (type==6) {
                          evType = "图片资料";
                      }
                   // alert(item.evName);
                  var html =   '<tr>'+
                      '<td style="display: none;" id="evid">'+item.id+'</td>'+
                      '<td style="text-align: left">'+item.evName+'</td>'+
                      '<td style="text-align: left">'+item.evSize+'kb</td>'+
                      '<td style="text-align: left">'+evType+'</td>'+
                      '<td style="text-align: left">'+item.comment+'</td>'+
                      '<td style="text-align: left">'+item.evAdmin+'</td>'+
                      '<td style="text-align: left">'+item.addTime+'</td>'+
                  '</tr>';

                  $("#tbcont1").append(html);
              });   */

            }
        });
    }


    //
    function exportFile() {

    		
		var startDate =document.getElementById('startDate').value;	
		var endDate =document.getElementById('endDate').value;
        //确认导出
        var rs = confirm("确定导出?");
        if (rs) {
            $("#url12_export").attr("href", "<%=basePath %>admin/Exporthd2.php?startDate="+startDate+"&endDate="+endDate);
        } else {
            $("#url12_export").attr("href", "javascript:void(0)");
        }
    }
    
    /* 图表自适应  start*/
 /* 	 $(window).resize( function(){
 		window.location.reload();
 	} ); */ 
 	/* 图表自适应  end*/
 	
 	
 	
</script>
<%-- <script src="<%=basePath%>template/js/echarts.min.js"></script> --%>
</body>
</html>

 