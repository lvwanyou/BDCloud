<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<link rel="stylesheet" type="text/css" href="<%=basePath%>template/font-awesome/css/font-awesome.min.css">
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/easyui.css"> --%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">

<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>template/js/jquery.easyui.min.js"></script>
<style>
.easyui-progressbar {padding: 1px;}
/*   .easyui-progressbar-value{  
        background : yellow;  
    }   */
.col-md-12 {
	padding: 0px;
}

th, .alcenter {
	text-align: center;
}

.wrap{  
width: 150px; //设置需要固定的宽度  
white-space: nowrap; //不换行  
text-overflow: ellipsis; //超出部分用....代替  
overflow: hidden; //超出隐藏  
} 
</style>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">数据列表</h1>
	</div>
	<div class="" style="padding-left: 20px; padding-top: 15px;">
		<a href="<%=basePath%>/getcaselist2.php" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="return">返回上一级</a>
	</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="panel panel-default">
				<div class="panel-heading">案件摘要</div>
				<div class="panel-body" style="padding: 0px;">
					<table id="datatable" class="table table-striped table-hover br016"
						style="text-align: center;margin-bottom: 0px;">
						<thead>
							<tr>
								<th class="alcenter">案件编号</th>
								<th class="alcenter">案件名称</th>
								<th class="alcenter">案件所属城市</th>
								<th class="alcenter">案件类型</th>
								<th class="alcenter">案件所属科室</th>
								<th class="alcenter">案件负责人</th>
								<th class="alcenter">案件创建时间</th>
								<th class="alcenter">案件状态</th>
							</tr>
						</thead>
						<tbody id="tbcont">
							<tr data-toggle="modal" data-target="#myModal">
								<td id="case_summary_id" style="display: none;">${result.id }</td>
								<td>${result.caseNum }</td>
								<td>${result.caseName }</td>
								<%-- <td>${result.shi}</td> --%>
								<td><c:if
										test="${result.shi == '市辖区' || result.shi == '市辖县'}">${result.sheng}</c:if>
									<c:if test="${result.shi != '市辖区' && result.shi != '市辖县'}">${result.sheng}${result.shi}</c:if>
								</td>

								<td>${result.caseType }</td>
								<td>${result.section }</td>
								<td>${result.userName }</td>
								<td>${result.createdTime }</td>
								<td>${result.status }</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="" style="text-align: left; margin-bottom: 20px; margin-top: 20px;">
				<a href="<%=basePath%>admin/importevidence2.php" type="button"
					class="btn w-xs btn-info" style="width: 135px;"
					onclick="getcaseId()">导入数据</a>
			</div>
			<input type="hidden" id="userName" value="${user.username}">
			<div class="panel panel-default">
				<div class="panel-heading">数据导入记录</div>
				<div class="panel-body" style="padding: 0 0 15px;">
					<table id="datatable" class="table table-striped table-hover br016"
						style="text-align: center;table-layout: fixed;">
						<thead>
							<tr>
								<th class="alcenter" style="width: 168px;">导入日期</th>
								<th class="alcenter">操作人</th>
								<th class="alcenter">上传方式</th>
								<th class="alcenter">文件大小</th>
								<th class="alcenter">数据名称</th>
								<th class="alcenter">数据描述</th>
								<th class="alcenter">上传数</th>
								<th class="alcenter">成功数</th>
								<th class="alcenter">失败数</th>
								<th class="alcenter">数据来源</th>
								<th class="alcenter">建立索引</th>
                    		</tr>
						</thead>
						<tbody id="tbcont">
							<c:forEach items="${logs }" var="ev">
								<tr>
									<td style="display: none;" id="evid">${ev.id }</td>
									<td>${ev.addTime }</td>
									<td>${ev.evAdmin }</td>
									<td><c:if test="${ev.uptype == 0}">web端上传</c:if> <c:if
											test="${ev.uptype == 1}">web端上传</c:if> <c:if
											test="${ev.uptype == 2}">客户端上传</c:if></td>
									<td><c:if test="${ev.evSize == ''}">0kb</c:if> <c:if
											test="${ev.evSize != ''}">${ev.evSize }kb</c:if></td>
									<td>${ev.evName }</td>
									<td
										style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden;"
										title="${ev.comment }">${ev.comment }</td>
									<td>${ev.uploadNum }</td>
									<td>${ev.successNum }</td>
									<td>${ev.errorNum }</td>

									<td><c:if test="${ev.dataTypes == 1}">移动设备</c:if> <c:if
											test="${ev.dataTypes == 2}">通信运营</c:if> <c:if
											test="${ev.dataTypes == 3}">社交网站</c:if> <c:if
											test="${ev.dataTypes == 4}">音频视频</c:if> <c:if
											test="${ev.dataTypes == 5}">采集数据</c:if> <c:if
											test="${ev.dataTypes == 6}">口供资料</c:if> <c:if
											test="${ev.dataTypes == 7}">其他来源</c:if> <c:if
											test="${ev.dataTypes == -1}">未选择数据来源</c:if>
									</td>

									<td><c:if test="${ev.indexFlag==-1}">
									<div id="imghtml${ev.id }">
										<a href="#"onclick="addSolr('${ev.id }')"  >建立索引</a>
									</div>
									<td id="addIndex${ev.id }" style="display: none;">
									<p></p>
									</td>
									</c:if>
									<c:if test="${ev.indexFlag==1}">已建立索引</c:if>
									<c:if test="${ev.indexFlag==2}">索引建立失败</c:if>
									</td>
									

								</tr>
							</c:forEach>
							    
							</tbody>
						</table>
						<div class="alcenter" style="font-size: 14px">
							<div class="pagecount inline" style="height: 29px;">
								<span>共${totalNum }条，当前${nowPage }/${totalPages }页</span>
							</div>
							<div class="pagebar inline" style="position: absolute; right: 10px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;">
									<li><a href="#" onclick="subForm('${nowPage-1<1?1:nowPage-1}')">&lt;</a></li>
									<c:forEach items="${pageList }" var="pageNO">
				            			<li <c:if test="${nowPage==pageNO }">class="active"</c:if>>
				            				<a href="#" onclick="subForm('${pageNO}')">${pageNO}</a>
				            			</li>
				            		</c:forEach>
									<li><a href="#" onclick="subForm('${nowPage+1>totalPages?totalPages:nowPage+1}')">&gt;</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				 
		<!-- 最底层表格
  		<div class="row" style="height: ;width: 98%;margin-left: 1%;margin-top: 1%;float: left;">  -->
				<div class="panel panel-default">
					<div class="panel-heading">命中预警提示</div>
					<div class="panel-body" style="padding: 0px;">
						<table id="datatable" class="table table-striped table-hover br016"
							style="margin-bottom: 0px;">
							<thead>
								<tr>
									<th style="text-align: left">匹配日期</th>
									<th style="text-align: left">命中项</th>
									<th style="text-align: left">涉嫌案件名称</th>
									<th style="text-align: left">嫌疑人姓名</th>
									<th style="text-align: left">命中次数</th>
								</tr>
							</thead>
							<tbody id="clues">
										
							</tbody>
						</table>
					</div>
				</div>
		</div>
		</div>
<jsp:include page="footer2.jsp"></jsp:include>
</body>
</html>
<script type="text/javascript">
//分页
function subForm(pageno){
	$("#pageno").val(pageno);
	var evid = $("#case_summary_id").text();
	window.location.href = "<%=basePath%>getEvidencelist2.php?evid="+evid+"&pageno="+pageno;
}

function addSolr(obj){
	//alert(1);
	
	var imghtml='<a href="#"  ><i aria-hidden="true"><img style="width:20px;height:20px;" src="<%=basePath%>template/img/loading1.gif"></i></a>';
	$("#imghtml"+obj).html(imghtml);
	startfun(obj,1);
 	<%-- $.ajax({
		type: "POST",
		url: "<%=basePath%>setStartSolrTime.php",
		dataType : "json",
		data : {
			"evID" : obj,
		},							    
		success : function(data) {
			if(data.res == "succ"){
					//$("#imghtml"+obj).html("已建立索引");
				caseID_now = obj;
				startfun(obj,1);
			}else{
				$("#imghtml"+obj).html("建立失败");
			}
		},						
		error : function(data) {
			//$("#imghtml"+obj).html("建立失败");
		}
	});  --%>
}

var stopInterval;
function startallloop(evID){

	if(evID!="" && evID!=0){
		//showunzip=setInterval("showUnzipProcess('"+evID+"')", 1000*30);//已写
		 showUnzipProcess(evID);
		 stopInterval = setInterval("showProcessTest('"+evID+"')", 1000*5); //show progress
	}
}

function showProcessTest(evID) {
	$.ajax({
		type : "POST",
		url :"<%=basePath%>sparkDealProcess.php",
		data : {
			"evID": evID
		},
		dataType : "json",
		async: true,
		success : function(data) {
		   if(data.percent==0){
			    //$("#addIndex"+evID).find("p").html("索引建立失败---!");
			    $("#imghtml"+evID).html("建立失败");
			   clearInterval(stopInterval);
		   }else if(data.percent==1){
			   $("#imghtml"+evID).html("已建立索引");
			   
			  // $("#addIndex"+evID).find("p").html("已完成!");
			   clearInterval(stopInterval);
		   }
		 /*   else if(data.percent==-1){
			   $("#addIndex"+evID).find("p").html("处理中.....!");
		   } */
		   
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
		}
	}); 
}
function showUnzipProcess(evID){
	var userName =$("#userName").val();
 	$.ajax({
		type : "POST",
		url :"<%=basePath%>unzipFinished.php",
		data : {
			"evID": evID,
			"userName":userName
		},
		dataType : "json",
		async: true,
		success : function(data) {
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			//$("#imghtml"+evID).html("建立失败");
		}
	}); 
}
//线索命中
var rizhi = window.setInterval("showClueWarn()",5000); 
function showClueWarn() {
	var caseId = $("#case_summary_id").text();
	$.ajax({
        url:"<%=basePath%>clueWarn/getClue.php",
        data:{
             "caseId":caseId     
        },
        type:"post",
        dataType:"json",
        success: function(data){
        	 $("#clues").empty();
        	 var html="";
	       	 $.each(data,function(i,item){
	       		 html+='<tr >'+
						'<td style="text-align: left">'+item.clueTime+'</td>'+
						'<td class="c11" style="text-align: left;">'+item.clue+'</td>'+
						'<td style="text-align: left">'+item.caseName+'</td>'+
						'<td style="text-align: left">'+item.suspectName+'</td>'+
						'<td style="text-align: left">'+item.num+'</td>'+
					'</tr>';
	       	 });
           $("#clues").append(html);	
        }
    });
}
//页面整体调用
function startfun(evID, type){
	if(evID!=0 && type==1 ){
		+
		//alert("aaaa");
		startallloop(evID);
		$.ajax({
			type : "POST",
			url : "<%=basePath%>getEvidence.php",
			data : {
				"evID":evID,
				"type":1
			},
			dataType : "json",
			async: false,
			success : function(data) {
				//init();
				var collectionName = data.evName;
				if(collectionName!=""){
					init(data.addTime);
					startit();
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		});
	}else{
		$.ajax({
			type : "POST",
			url : "<%=basePath%>getEvidence.php",
			data : {
				"evID":evID,
				"type":0
			},
			dataType : "json",
			success : function(data) {
				var collectionName = data.evName;
				if(collectionName!=""){
					init(data.addTime);
					if(data.percent!=null && data.percent!=""){
						var pint = parseInt(data.percent);
						if(pint > 0){
							$('#progress-bar-data').progressbar('setValue', pint);
							if (pint == 101){
								$("#taskStatus").html("任务失败");
							}else if (pint == 100){
								$("#taskStatus").html("已完成");
								$("#currentState").html("已完成");
							}
						}
					}
					if(data.emlCount!=null && data.emlCount!="-1"){
						$("#uploadedEMLCounts").text(data.emlCount);
						$("#handledEMLCounts").text(data.emlCount);
					}
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	var showAllFileTimer;
	//-------------------------------------------- xl end
	
	$('.dd').nestable();
	
	$('.dd-handle a').on('mousedown', function(e){
		e.stopPropagation();
	});
	
	$('.date-picker').datepicker({autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
	//$('input[name=date-range-picker]').daterangepicker().prev().on(ace.click_event, function(){
	//	$(this).next().focus();
	//});
	
	getCaseData();
	
	$('#timepicker1').timepicker({
		minuteStep: 1,
		showSeconds: true,
		showMeridian: false
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
	$('.easy-pie-chart.percentage')
			.each(
					function() {
						var $box = $(this).closest('.infobox');
						var barColor = $(this).data('color')
								|| (!$box.hasClass('infobox-dark') ? $box
										.css('color')
										: 'rgba(255,255,255,0.95)');
						var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)'
								: '#E2E2E2';
						var size = parseInt($(this).data('size')) || 50;
						$(this)
								.easyPieChart(
										{
											barColor : barColor,
											trackColor : trackColor,
											scaleColor : false,
											lineCap : 'butt',
											lineWidth : parseInt(size / 10),
											animate : /msie\s*(8|7|6)/
													.test(navigator.userAgent
															.toLowerCase()) ? false
													: 1000,
											size : size
										});
					})
	
	$('.sparkline').each(
			function() {
				var $box = $(this).closest('.infobox');
				var barColor = !$box.hasClass('infobox-dark') ? $box
						.css('color') : '#FFF';
				$(this).sparkline('html', {
					tagValuesAttribute : 'data-values',
					type : 'bar',
					barColor : barColor,
					chartRangeMin : $(this).data('min') || 0
				});
			});
	
	var placeholder = $('#piechart-placeholder').css({
		'width' : '90%',
		'min-height' : '150px'
	});
	var data = [ {
		label : "social networks",
		data : 38.7,
		color : "#68BC31"
	}, {
		label : "search engines",
		data : 24.5,
		color : "#2091CF"
	}, {
		label : "ad campaigns",
		data : 8.2,
		color : "#AF4E96"
	}, {
		label : "direct traffic",
		data : 18.6,
		color : "#DA5430"
	}, {
		label : "other",
		data : 10,
		color : "#FEE074"
	} ]
	function drawPieChart(placeholder, data, position) {
		$.plot(placeholder, data, {
			series : {
				pie : {
					show : true,
					tilt : 0.8,
					highlight : {
						opacity : 0.25
					},
					stroke : {
						color : '#fff',
						width : 2
					},
					startAngle : 2
				}
			},
			legend : {
				show : true,
				position : position || "ne",
				labelBoxBorderColor : null,
				margin : [ -30, 15 ]
			},
			grid : {
				hoverable : true,
				clickable : true
			}
		})
	}
	drawPieChart(placeholder, data);
	
	/**
	we saved the drawing function and the data to redraw with different position later when switching to RTL mode dynamically
	so that's not needed actually.
	 */
	placeholder.data('chart', data);
	placeholder.data('draw', drawPieChart);

	var $tooltip = $(
			"<div class='tooltip top in'><div class='tooltip-inner'></div></div>")
			.hide().appendTo('body');
	var previousPoint = null;

	placeholder.on('plothover', function(event, pos, item) {
		if (item) {
			if (previousPoint != item.seriesIndex) {
				previousPoint = item.seriesIndex;
				var tip = item.series['label'] + " : "
						+ item.series['percent'] + '%';
				$tooltip.show().children(0).text(tip);
			}
			$tooltip.css({
				top : pos.pageY + 10,
				left : pos.pageX + 10
			});
		} else {
			$tooltip.hide();
			previousPoint = null;
		}

	});

	var d1 = [];
	for (var i = 0; i < Math.PI * 2; i += 0.5) {
		d1.push([ i, Math.sin(i) ]);
	}

	var d2 = [];
	for (var i = 0; i < Math.PI * 2; i += 0.5) {
		d2.push([ i, Math.cos(i) ]);
	}

	var d3 = [];
	for (var i = 0; i < Math.PI * 2; i += 0.2) {
		d3.push([ i, Math.tan(i) ]);
	}

	var sales_charts = $('#sales-charts').css({
		'width' : '100%',
		'height' : '220px'
	});
	$.plot("#sales-charts", [ {
		label : "Domains",
		data : d1
	}, {
		label : "Hosting",
		data : d2
	}, {
		label : "Services",
		data : d3
	} ], {
		hoverable : true,
		shadowSize : 0,
		series : {
			lines : {
				show : true
			},
			points : {
				show : true
			}
		},
		xaxis : {
			tickLength : 0
		},
		yaxis : {
			ticks : 10,
			min : -2,
			max : 2,
			tickDecimals : 3
		},
		grid : {
			backgroundColor : {
				colors : [ "#fff", "#fff" ]
			},
			borderWidth : 1,
			borderColor : '#555'
		}
	});

	$('#recent-box [data-rel="tooltip"]').tooltip({
		placement : tooltip_placement
	});
	function tooltip_placement(context, source) {
		var $source = $(source);
		var $parent = $source.closest('.tab-content')
		var off1 = $parent.offset();
		var w1 = $parent.width();

		var off2 = $source.offset();
		var w2 = $source.width();

		if (parseInt(off2.left) < parseInt(off1.left)
				+ parseInt(w1 / 2))
			return 'right';
		return 'left';
	}

	$('.dialogs,.comments').slimScroll({
		height : '300px'
	});

	//Android's default browser somehow is confused when tapping on label which will lead to dragging the task
	//so disable dragging when clicking on label
	var agent = navigator.userAgent.toLowerCase();
	if ("ontouchstart" in document && /applewebkit/.test(agent)
			&& /android/.test(agent))
		$('#tasks').on('touchstart', function(e) {
			var li = $(e.target).closest('#tasks li');
			if (li.length == 0)
				return;
			var label = li.find('label.inline').get(0);
			if (label == e.target || $.contains(label, e.target))
				e.stopImmediatePropagation();
		});

	$('#tasks').sortable({
		opacity : 0.8,
		revert : true,
		forceHelperSize : true,
		placeholder : 'draggable-placeholder',
		forcePlaceholderSize : true,
		tolerance : 'pointer',
		stop : function(event, ui) {//just for Chrome!!!! so that dropdowns on items don't appear below other items after being moved
			$(ui.item).css('z-index', 'auto');
		}
	});
	$('#tasks').disableSelection();
	$('#tasks input:checkbox').removeAttr('checked').on('click',
			function() {
				if (this.checked)
					$(this).closest('li').addClass('selected');
				else
					$(this).closest('li').removeClass('selected');
			});
}

//获取案件摘要id
function getcaseId() {
	var case_summary_id = $("#case_summary_id").text();
	<%-- window.location.href = "<%=basePath %>getCaseSummary.php?case_summary_id="+case_summary_id; --%>
	$.ajax({
		type : "POST",
		url : "<%=basePath%>getCaseSummary2.php",
		cache:false, 
	    async:false,  
		data : {
			"case_summary_id":case_summary_id,
		},
		dataType : "json",
		success : function(data) {
			
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}
</script>