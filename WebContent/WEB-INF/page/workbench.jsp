<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%--  <% 
  response.setHeader("refresh","1");// 方法一
  System.out.println("11111");
 %>  --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/cloud-admin.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">

<script src="<%=basePath%>template/js/cutover/echarts.js"></script>
<!-- <script src="https://cdn.bootcss.com/echarts/3.7.2/echarts-en.js"></script> -->
<style>
	.top_div{
		margin-right:1%;
		float:left;
		width:19%;
		height:90px;
	}
	.top_p{
		margin:5px 0px 0px 10px;
		float:left;
		font-size: 36px;	

	}
	.bottom_p{
		font-size: 14px;
		float:right;
		margin:60px 20px 5px 0px;
		
	}
.col-md-12{padding:0px;}
.demo ul{
	list-style:none;
	padding:0px;
	margin:0px;
}
.demo ul li{
	height:30px;
	line-height:30px;
	vertical-align:middle;
}

.sheng{
	max-width:130px;
	text-overflow:ellipsis;
	overflow:hidden;
	white-space:nowrap;	
}

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
.ui-autocomplete{
	z-index:999999 !important;
	height:100px;
	overflow:scroll;
}
/* .tishi{display:none;margin:0 auto;text-align:center;margin-top:100px;color:red;}
@media screen and (max-width: 1050px) {
    .wrapper-md,.b20 {
       
        display:none;
      
        
        
    }
   .tishi{display:inline-block !important;font-size:39px;}  */
}
</style>
<%-- <script src="<%=basePath %>template/js/script_left.js"></script> --%>
<script>
		response.setIntHeader("Refresh", 10);//单位是秒
</script>

</head>


<body>

 <!-- <p class="tishi" >您的浏览器窗口尺寸过小，为了正常访问此页面，请调整窗口尺寸。</p> -->
	<jsp:include page="common.jsp"></jsp:include>


	<div class="wrapper-md" style="overflow:hidden;">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="panel panel-default">
				<form class="form-inline" role="form" id="searchForm" action="<%=basePath%>admin/showAllCaseNum.php" method="post">
				<div class="panel-heading">案件数量统计</div>
				<div class="panel-body" style="padding-top: 15px;" >
					<div class="top_div b01">
						<p class="top_p c02" id="totalNum"></p>
						<p class="bottom_p c02">案件总数</p>
					</div>
					<div class="top_div b02">
						<p class="top_p c02" value="year" id="totalNum1"></p>
						<p class="bottom_p c02">本年度案件总数</p>
					</div>
					<div class="top_div b04">
						<p class="top_p c02" id="totalNum2"></p>
						<p class="bottom_p c02">数据批次总数</p>
					</div>
					<div class="top_div b09">
						<p class="top_p c02" id="totalNum4"></p>
						<p class="bottom_p c02">嫌疑人命中</p>
					</div>
					<div class="top_div b05">
						<p class="top_p c02" id="sum"></p>
						<p class="bottom_p c02" >布控预警命中</p>
					</div>
					
				</div>
				</form>
			</div>
			</div>
			<div class="panel panel-default" style="width:49%; height:50%; float: left;margin-right:20px;">
			<div class="panel-heading">命中预警</div>
							<div class="js-height" style=" position:relative; height:300px;margin-bottom: 0px; overflow-y :hidden;">
									<!-- <div class="panel-heading">资料匹配结果</div> -->
									<div id="datatable" class="table table-striped table-hover br04"
										style="text-align: center;">
										
											<p class="br04" style="padding:0px;margin:0px;line-height:30px;">
												<span style="text-align: left;display:inline-block;width:20%;">匹配日期</span>
												<span style="text-align: left;display:inline-block;width:20%;">命中项</span>
												<span style="text-align: left;display:inline-block;width:20%;">涉嫌案件名称</span>
												<span style="text-align: left;display:inline-block;width:20%;">嫌疑人姓名</span>
												<span style="text-align: left;display:inline-block;width:auto;">命中次数</span>
											</p>
										
											<div class="demo js-scroll">
												<ul id="clues" style="width:100%;">
												
												</ul>
											</div>
										
									
									</div>
								</div>
			</div>
				<div class="panel panel-default" style="width:49%;float: left;">
			<div class="panel-heading">数据增加趋势图</div>
			<div id="main" style="height:300px;">
			<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
			<div id="main" style="height:300px; width: 80%"></div>
    <script type="text/javascript">
    		/* console.log('youChart', echarts) */
    		var myChart = echarts.init(document.getElementById('main')); 
            	var sizes= [];
            	$.ajax({
                    url:"<%=basePath %>admin/SearchData.php",
                    data:{
                    },
                    type:"post",
                    dataType:"json",
                    success: function(data){
                    	//data=[1320, 1132, 601, 234, 120, 90, 20,122, 734, 391, 390, 30,] ;
                    	 //alert(data);
                    	 
                    	// alert(sizes);
                         // 基于准备好的dom，初始化echarts图表
                         
                         option = {
                         	    title : {
                         	        text: '',
                         	        subtext: ''
                         	    },
                         	    tooltip : {
                         	        trigger: 'axis'
                         	    },
                         	    legend: {
                         	         data:['数据','单位/G'] 
                         	    },
                         	    calculable : true,
                         	    xAxis : [
                         	        {
                         	            type : 'category',
                         	            boundaryGap : false,
                         	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                         	        }
                         	    ], 
                         	    yAxis : [
                         	        {
                         	            type : 'value'
                         	        }
                         	    ],
                         	     series : [
                         	 
                         	        {
                         	            name:'数据',
                         	            type:'line',
                         	            smooth:true,
                         	            itemStyle: {normal: {color:'#31AEF2',borderColor:'#4EB9F3',borderWidth:'2px',areaStyle: {type: 'default',color:'rgba(135, 206, 235, 0.5)'},
                         	            					lineStyle:{color:'#4EB9F3'}}},
                         	            
                         	            data: data//[1320, 1132, 601, 234, 120, 90, 20,122, 734, 391, 390, 30,] 
                         	            					
                         	         
                         	        }
                         	    ] 
                         	};
                     
                 
                         // 为echarts对象加载数据 
                        myChart.setOption(option); 
            	   /*     	 $.each(data,function(i,item){
            	       		
            	       		sizes.push(item);
            	       	 }); */
                      
                    }
                }); 
    </script>
			</div>
			
			</div>
			<script>
				/* console.log('resize', myChart); */
				window.onresize = function(){
					myChart.resize();
				}
				</script>		
			
			<div class="panel panel-default" style="width:49%;float: left;margin-right:20px;">
			<div class="panel-heading">黑客数据库趋势图</div>
			<div id="list" style="height:300px;">
			</div>	
		<div class="modal right fade" id="myModal" tabindex="-1"
				style="width: 100%; height: 100%; margin: auto;">
				<div class="panel panel-default"
					style="width: 30%; height: 80%; margin: 3% 20%;">
					
					
					<div class="panel-heading">命中文件列表</div>
					<div class="panel-body" style="width: 100%; height: 90%; overflow-y :auto;">
						<table id="datatable" class="table table-striped table-hover br04"
							style="text-align: center;">
							<thead>
								<tr >
									<th style="text-align: left">文件名称</th>
									<th style="text-align: left">详情</th>
									<th style="text-align: left">下载</th>
								</tr>
							</thead>
							<tbody id="clues2">

							</tbody>
						</table>
					</div>
					
					
					
				</div>
			</div>		
	
		<!-- 文档类型详情弹窗 -->		
		<div class="modal right fade" id="myModal_doc" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="panel panel-default" style="padding-right: 10px;">
						<div class="panel-body" style="height: 950px;" >
							<div class="panel-heading br04 b21"
								style="padding: 10px;">详情</div>
							
								<span id="test1" ></span>
								<div class="panel-heading br04 b22" style="padding: 10px;">
								   <div class="b22" style="height: 60px;float: left;">
								   <img class="b22" id="docTypeyl" src="<%=basePath %>template/img/word.png" style="width: 50px;height: 60px;">
								   </div>
								   <div style="margin-top: 17px;margin-left: 65px;"><span id="docfileNameyl"></span>&nbsp;&nbsp;&nbsp;<span id="docdateyl"></span><br><span id="docfileSizeyl" ></span>
								    <div style=" padding-left: 300px; padding-bottom: 0px;">
								         </div> 
								         </div>
								<hr>
								     <div class="b22" style="width: 530px; height: 300px;">
								        <table id="datatable" class="table table-striped table-hover br004">
								<tbody id="tbcont">
								<!-- es上的_id  -->
									<tr data-toggle="modal" >
										<td class="td_right" id="essssid_right" style="display: none;"></td>
									</tr>
									
									<tr data-toggle="modal">
										<td class="td_left">文件名</td>
										<td class="td_right" id="doc_fileName"></td>
									</tr>
									<tr data-toggle="modal">
										<td class="td_left">文件类型</td>
										<td class="td_right" id="doc_docType"></td>
									</tr>
									<tr data-toggle="modal">
										<td class="td_left">文件大小</td>
										<td class="td_right" id="doc_fileSize"></td>
									</tr>
									<tr data-toggle="modal">
										<td class="td_left">创建时间</td>
										<td class="td_right" id="doc_createDate"></td>
									</tr>
									<tr data-toggle="modal">
										<td class="td_left">访问时间</td>
										<td class="td_right" id="doc_viewDate"></td>
									</tr>
									<tr data-toggle="modal">
										<td class="td_left">修改时间</td>
										<td class="td_right" id="doc_editDate"></td>
									</tr>
									<tr data-toggle="modal">
										<td class="td_left">导入时间</td>
										<td class="td_right" id="doc_importDate"></td>
									</tr>
								</tbody>
							</table>
					</div>
					
					<div class="panel panel-default" style="margin-top:0px;">
						<div class="panel-heading">文档摘要</div>
						<div class="panel-body">
							<div id="doc_content"></div>
							
							</div>
					</div>
					</div>
					</div>
					</div>
				</div>
			</div>
		</div>	
		
		<!-- 邮件类型详情弹窗 -->		
		<div class="modal right fade" id="myModal_email" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="panel panel-default" style="padding-right: 10px;">
						<div class="panel-body" style="height: 950px;" >
							<div class="panel-heading br004 b21" style="padding: 10px;">详情</div>
							
								<span id="test1" ></span>
								<div class="panel-heading" class="alcenter" style="margin-top:10px;margin-left:10;"> 
									邮件内容<span class="modal-title" style="font-size: 22px; width:700px;"></span>
								</div>
								<div class="panel-body br04" style="padding: 0px; height: 100%;">
									<table id="datatable" style=" margin-bottom: 0px; width: 100%;font-size:14px;">
										<tr>
											<td class="c20" style="font-weight: bolder; font-size: 17px; padding-left: 30px; padding-top: 20px;font-weight:600;" id="emailsubject">邮件标题:</td>

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
				 							加载中<img alt="" src="<%=basePath %>template/img/loading2.gif">
				 						</div>
									<div class="_modal-mailcontent-append" id="correctEml_attfile" style="overflow: auto;">
								 		</div>
									</div>
							</div>
					</div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
        // 路径配置
       /*  console.log('list', echarts); */
        	var myChart1 = echarts.init(document.getElementById('list')); 
        
            $.get('<%=basePath%>admin/HackerDB.php').done(function (data) {
            	//alert(data);
            	data = eval('('+data+')'); 
                var sum =data.arrayList;

                // 基于准备好的dom，初始化echarts图表
               
                
                var option = {
                		
                		
                		 title : {
                			 
                		        //text: '本年度案收结趋势图',
                		        //fontStyle:'italic'
                		        //subtext: '纯属虚构'
                		    },
                		    
                    tooltip: {
                        show: true
                    },
                    legend: {
                    	//itemGap:0,
                        data:['单位/万条'],
                        x:'680px',
                     y:'40px'
                    },                 
                    xAxis : [
                        {
                            type : 'category',
                            data : ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
                        }
                    ],
                    yAxis:
                    	[
                    {
                        type: 'value',
                        //splitNumber:1,
                        splitNumber: 10,
                        min: 0,
                        max: data
                    }],

                    series : [
                              
                              
                        {
                        	type:'bar',
                            "name":"黑客数据库：单位/万条",
                            "type":"bar",
                            "barGap":"5",
                            itemStyle: {normal: {color:'#6CE0C7'
            					}},
                            "data":data
                    
                        }
                       /*  {
                        	type:'bar',
                            "name":"结案",
                            "type":"bar",
                            "baryGap":"5",
                            itemStyle: {normal: {color:'#4EB9F3'
        					}},
                            "data":[100, 100, 200,50, 200, 200,100, 100, 200,50, 200, 300]
                  
                        }, */
                        
                    ],
                   
                };
       
                // 为echarts对象加载数据 
                myChart1.setOption(option); 
            	/* 	}*/
            	}); 
        
    </script>  
    	</div>
    	</div>
    	<div class="panel panel-default" style="width:49%;float: left;">
			<div class="panel-heading">数据类型趋势图 &nbsp;(<b>总容量：22T</b> &nbsp;<b id="Surplus">剩余容量：20.7T</b>)</div>
			<div id="yuan" style="height:300px;">

		<script type="text/javascript">
		var myChart2 = echarts.init(document.getElementById('yuan')); 
		// 路径配置
                // 基于准备好的dom，初始化echarts图表
                
                
               var option = {
            		   
            		   title : {
              			 
           		        text: '总容量：',
           		        //fontStyle:'italic'
           		        //subtext: '纯属虚构'
           		    },
                	    tooltip : {
                	        trigger: 'item',
                	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                	    },
                	    legend: {
                	        orient : 'vertical',
                	        x : 'right',
                	        data:['电子邮件','电子话单','图片资料','工作文档','黑客数据库']
                	    },
                	 
                	    calculable : true,
                	    series : [
                	        {
                	            name:'数据来源',
                	            type:'pie',
                	            radius : ['50%', '70%'],
                	            itemStyle : {
                	                normal : {
                	                    label : {
                	                        show : false
                	                    },
                	                    labelLine : {
                	                        show : false
                	                    }
                	                },
                	                emphasis : {
                	                    label : {
                	                        show : true,
                	                        position : 'center',
                	                        textStyle : {
                	                            fontSize : '30',
                	                            fontWeight : 'bold'
                	                        }
                	                    }
                	                }
                	            },
                	            data:[
                	                {value:335, name:'电子邮件'},
                	                {value:310, name:'电子话单'},
                	                {value:234, name:'图片资料'},
                	                {value:135, name:'工作文档'},
                	                {value:1548, name:'黑客数据库'}
                	            ]
                	        }
                	    ]
                	};
               $.get('<%=basePath%>admin/DataType.php').done(function (data) {
        	    	//alert(data);
        	    	data = eval('('+data+')'); 
	    	        var dateTime = [];
	    	        var datesum = [];
	    	        var dateTimeList =data.arrayList;
	    	        var dateTimeList1 =data.arrayList1;
	    	        $("#Surplus").html("剩余容量："+dateTimeList1+"T");
        	    	$.each(dateTimeList,function(i,item){	
        	    		var name1 = item.split(" ")[0];
        	    		var value1 = item.split(" ")[1];
        	    		var node={
        	    				value:value1, 
        	    				name:name1	
        	    		};
        	    		datesum.push(node);
        	    		//alert(dateTime);
        			});
        	    	
        	    	$.each(dateTimeList,function(i,item){	
        	    		var name1 = item.split(" ")[0];
        	    		dateTime.push(name1);
        			});
        	    	
        	        myChart2.setOption({ 
        	        	
        	        	/*  title : {
                  			 
                		        text: '总容量：22T',
                		      
                		        //fontStyle:'italic'
                		       //subtext: '纯属虚构'
                		    },
                		    title1 : {
                     			 
                		        text: '剩余容量：20.5T',
                		      
                		        //fontStyle:'italic'
                		       //subtext: '纯属虚构'
                		    }, */
        	        	  tooltip : {
                  	        trigger: 'item',
                  	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                  	    },
                  	    legend: {
                  	        orient : 'vertical',
                  	        x : 'right',
                  	        data:dateTime
                  	    },
        	        	 calculable : true,
                 	   	 series : [
                 	        {
                 	            name:'数据来源',
                 	            type:'pie',
                 	            radius : ['50%', '70%'],
                 	            itemStyle : {
                 	                normal : {
                 	                    label : {
                 	                        show : false
                 	                    },
                 	                    labelLine : {
                 	                        show : false
                 	                    }
                 	                },
                 	                emphasis : {
                 	                    label : {
                 	                        show : true,
                 	                        position : 'center',
                 	                        textStyle : {
                 	                            fontSize : '30',
                 	                            fontWeight : 'bold'
                 	                        }
                 	                    }
                 	                }
                 	            },
                 	            data:datesum
                 	            	/* [
                	                {value:335, name:'电子邮件'},
                	                {value:310, name:'电子话单'},
                	                {value:234, name:'图片资料'},
                	                {value:135, name:'工作文档'},
                	                {value:1548, name:'黑客数据库'}
                	            ]  */}
                 	    ]
        	        })
                                
       
                // 为echarts对象加载数据 
               // myChart.setOption(option); 
            }
        );
        
    </script>
    </div>
    </div>
	</div>
		
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>

	<script src="<%=basePath%>template/js/shengshiqu/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>template/js/shengshiqu/area.js"></script>
	<script type="text/javascript" src="<%=basePath%>template/js/shengshiqu/location.js"></script>
	<script src="<%=basePath%>template/js/shengshiqu/select2.js"></script>
	<script src="<%=basePath%>template/js/shengshiqu/select2_locale_zh-CN.js"></script>
	<script src="<%=basePath%>template/js/jquery.js"></script>
<script>


window.addEventListener("resize", function () {

    option.chart.resize();

});

window.onresize =function(){
 /*    cityCountTendencyChart.resize();
    mainWhCountTendencyChart.resize();
    littleWhCountTendencyChart.resize();
    cityCountVisionChart.resize();
    mainWhCountVisionChart.resize(); */
    myChart.resize();
    myChart1.resize();
    myChart2.resize();

}  
/* window.onresize = function(){

   setTimeout(function(){
       $('a').innerHTML = Math.floor(Math.random() * 100);
   }, 1000)
} */
//命中预警
window.onload = getClues();
function getClues() {
	 //$("#clues").empty();
	$.ajax({
        url:"<%=basePath %>clueWarn/getClue.php",
        data:{
             "caseId":""     
        },
        type:"post",
        dataType:"json",
        success: function(data){
        	var html="";
	       	 $.each(data,function(i,item){
	       		 html+='<li class="br04" style="overflow:hidden;box-sizing:border-box;">'+
							'<span style="display:inline-block;width:20%;vertical-align:top;text-align:left;padding-left:30px;">'+item.clueTime+'</span>'+
							'<span style="display:inline-block;width:20%;vertical-align:top;" class="sheng">'+item.clue+'</span>'+
							'<span style="display:inline-block;width:20%;vertical-align:top;padding-left:30px;">'+item.caseName+'</span>'+
							'<span style="display:inline-block;width:20%;vertical-align:top;padding-left:30px;">'+item.suspectName+'</span>'+
							'<p style="display:inline-block;width:20%;vertical-align:top;margin:0px;padding-left:30px;"><a class="c09" data-target="#myModal" data-toggle="modal" type="button" onclick="showFile('+i+')" >'+item.num+'</a>'+
							'<span id="fileNames'+i+'" style=" display:none;">'+item.fileName+'</span></p>'+
						'</li>';
	       	 });
           $("#clues").append(html);	
           $("#sum").html(data.length);
           /* 数据小于9条停止滚动 */
            var len = data.length;
            if(len > 9){
            	$('.js-scroll').easyTicker({
                	
                })
            }
            
        }
    });
}
//点击命中次数 显示文件列表
function showFile(num) {
	$("#clues2").empty();
	var fileNames = $("#fileNames"+num).html();
	var files=fileNames.split("↑|↑");
	var html="";
   	 $.each(files,function(i,item){
   		 var tc="myModal_doc";
         var type=item.split(".")[item.split(".").length-1];
         if(type=="eml"){
        	 tc="myModal_email";
         }
         var name=item.split("/")[item.split("/").length-1];
   		 var ite= name.length < 15 ? name : name.substring(0,15)+"...";
   		 var url="'"+item+"'";
   		 html+='<tr>'+
					'<td style="text-align: left;width:50%" >'+ite +'</td>'+
					'<td style="text-align: left" ><a class="c09" data-target="#'+tc+'" data-toggle="modal" type="button"   onclick="getClueDetails('+url+')"  >详情</a></td>'+
					'<td style="text-align: left" ><a class="c09" id="'+num+'url'+i+'"  href="#" type="button"  onclick="downloaddoc(this.id,'+url+')">下载</a></td>'+
				'</tr>';
   	 });
    $("#clues2").append(html);	
}
//命中预警详情
function getClueDetails(url) {
	
	var htmljiazai='<div id="loadDiv" style="text-align: center;margin-top: 20px">加载中<img alt="" src="<%=basePath %>template/img/loading2.gif"></div>';
	$("#emailcontent").html(htmljiazai);
	$.ajax({
       url:"<%=basePath %>clueWarn/getEsClue.php",
       data:{
            "url":url     
       },
       type:"post",
       dataType:"json",
       success: function(data){
    	  var es = data.hits.hits[0];
    	  if(es._index=="es"){
    		  $("#emailsubject").html("邮件标题: "+es._source.subject);
    		  $("#emailtoWho").html("发件人： "+es._source.toWho);
    		  $("#emaildate").html(es._source.date);
    		  $("#emailfromWho").html("收件人： "+es._source.fromWho);
    		 // $("#emailcontent").html(es._source.content);
    		  emailContent(url);
    	  }else{
    		  $("#doc_fileName").html(es._source.fileName);
    		  $("#doc_docType").html(es._source.docType);
    		  $("#doc_fileSize").html(es._source.fileSize);
    		  $("#doc_createDate").html(es._source.createDate);
    		  $("#doc_viewDate").html(es._source.viewDate);
    		  $("#doc_editDate").html(es._source.editDate);
    		  $("#doc_importDate").html(es._source.importDate);
    		  $("#doc_content").html(es._source.content);
    		  //图片替换
    		  document.getElementById("docTypeyl").setAttribute("src", "<%=basePath %>template/img/"+es._source.docType.split(".")[1]+".png");
    	  }
       } ,
		error: function() {
			//alert("失败");
		}
   }); 
}
//邮件原格式展示
function emailContent(emlpath){
	$.ajax({
		type : "POST",
		url : "<%=basePath%>getCorrectEml.php",
		data : {
			"emlpath":emlpath,
			"attachmentname":"",
			"keyword":"",
			"zhengzeType":""
		},
		dataType : "json",
		async: true,
		success : function(data) {
			$("#emailcontent").html(data.resData.content);	
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("失败!");
		}
	});
}
//命中预警文件下载
function downloaddoc(urlid,url){
	//对url进行编码
	var docpath_encode=encodeURI(encodeURI(url));
	$("#"+urlid).attr("href","<%=basePath %>admin/downloadDOC.php?docpath="+docpath_encode);
}
//查询所有的案件总数
window.onload=showCase();
window.setInterval("showCase()",1000); //刷新案件总数 单位为1秒刷新一次
function showCase() {
	//alert(1);
	$.ajax({
       url:"<%=basePath %>admin/showAllCase.php",
       data:{
                 
       },
       type:"post",
       dataType:"json",
       success: function(data){
    	   //alert(data);
    	  $("#totalNum").html(data);
       } ,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("失败");
		}
   });
}

//查询年度案件总数
window.onload=showCaseNum();
window.setInterval("showCaseNum()",1000); //刷新案件总数 单位为1秒刷新一次
function showCaseNum() {
	//alert(1);
	$.ajax({
       url:"<%=basePath %>admin/showAllCaseNum.php",
       data:{
                 
       },
       type:"post",
       dataType:"json",
       success: function(data1){
    	  // alert(data1);
    	  $("#totalNum1").html(data1);
       } ,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("失败");
		}
   });
}

//按数据批次查询
window.onload=showData();
window.setInterval("showData()",1000); //刷新案件总数 单位为1秒刷新一次
function showData() {
	//alert(1);
	$.ajax({
       url:"<%=basePath %>admin/showDataList.php",
       data:{
                 
       },
       type:"post",
       dataType:"json",
       success: function(data2){
    	   //alert(data2);
    	  $("#totalNum2").html(data2);
       } ,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("失败");
		}
   });
}

//嫌疑人命中
//按数据批次查询
window.onload=showSuspect();
window.setInterval("showSuspect()",1000);//刷新案件总数 单位为1秒刷新一次
function showSuspect() {
	//alert(1);
	$.ajax({
       url:"<%=basePath %>admin/showSuspect.php",
       data:{
                 
       },
       type:"post",
       dataType:"json",
       success: function(data4){
    	   //alert(data2);
    	  $("#totalNum4").html(data4);
       } ,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("失败");
		}
   });
}
 window.onload=ShowhackDB();
function ShowhackDB(){
	$.ajax({
		url:"<%=basePath %>admin/HackerDB.php",
		data:{
			
		},
		type:"post",
		dataType:"json",
		success:function(data){
			/* alert(data); */
		},
		error:function(XMLHttpRequest,textStatus,erroeThrown){
			alsert("失败");
		}
	})
}  









/* 命中预警数据滚动效果 */
/* 
 * jQuery - Easy Ticker plugin - v2.0
 * http://www.aakashweb.com/
 * Copyright 2014, Aakash Chakravarthy
 * Released under the MIT License.
 */
 
(function ( $, window, document, undefined ) {
	var li_len = $("#clues li").length;
    var name = "easyTicker",
        defaults = {
			direction: 'up',
			easing: 'swing',
			speed: 'slow',
			interval: 2000,
			height: 'auto',
			visible: 0,
			mousePause: 1,
			controls: {
				up: '',
				down: '',
				toggle: '',
				playText: 'Play',
				stopText: 'Stop'
			}
        };

    // Constructor
    function EasyTicker( el, options ) {
		
		var s = this;
		
        s.opts = $.extend( {}, defaults, options );
        s.elem = $(el);
		s.targ = $(el).children(':first-child');
		s.timer = 0;
		s.mHover = 0;
		s.winFocus = 1;
		
		init();
		start();
		
		$([window, document]).off('focus.jqet').on('focus.jqet', function(){
			s.winFocus = 1;
		}).off('blur.jqet').on('blur.jqet', function(){
			s.winFocus = 0;
		});
		
		if( s.opts.mousePause == 1 ){
			s.elem.mouseenter(function(){
				s.timerTemp = s.timer;
				stop();
			}).mouseleave(function(){
				if( s.timerTemp !== 0 )
					start();
			});
		}
		
		$(s.opts.controls.up).on('click', function(e){
			e.preventDefault();
			moveDir('up');
		});
		
		$(s.opts.controls.down).on('click', function(e){
			e.preventDefault();
			moveDir('down');
		});
		
		$(s.opts.controls.toggle).on('click', function(e){
			e.preventDefault();
			if( s.timer == 0 ) start();
			else stop();
		});
		
		function init(){
			
			s.elem.children().css('margin', 0).children().css('margin', 0);
			
			s.elem.css({
				position : 'relative',
				height: s.opts.height,
				overflow : 'hidden'
			});
			
			s.targ.css({
				'position' : 'absolute',
				'margin' : 0
			});
			
			setInterval( function(){
				adjHeight();
			}, 100);
			
		} // Init Method
		
		function start(){
			s.timer = setInterval(function(){
				if( s.winFocus == 1 ){
					move( s.opts.direction );
				}
			}, s.opts.interval);

			$(s.opts.controls.toggle).addClass('et-run').html(s.opts.controls.stopText);
			
		} // Start method
		
		
		function stop(){
			clearInterval( s.timer );
			s.timer = 0;
			$(s.opts.controls.toggle).removeClass('et-run').html(s.opts.controls.playText);
		}// Stop
		
		
		function move( dir ){
			var sel, eq, appType;
			
			if( !s.elem.is(':visible') ) return;

			if( dir == 'up' ){
				sel = ':first-child';
				eq = '-=';
				appType = 'appendTo';
			}else{
				sel = ':last-child';
				eq = '+=';
				appType = 'prependTo';
			}
		
			var selChild = s.targ.children(sel);
			var height = selChild.outerHeight();
			
			s.targ.stop(true, true).animate({
				'top': eq + height + "px"
			}, s.opts.speed, s.opts.easing, function(){
				
				selChild.hide()[appType]( s.targ ).fadeIn();
				s.targ.css('top', 0);
				
				adjHeight();
				
			});
		}// Move
		
		function moveDir( dir ){
			stop();
			if( dir == 'up' ) move('up'); else move('down'); 
			// start();
		}
		
		function fullHeight(){
			var height = 0;
			var tempDisp = s.elem.css('display'); // Get the current el display value
			
			s.elem.css('display', 'block');
					
			s.targ.children().each(function(){
				height += $(this).outerHeight();
			});
		
			s.elem.css({
				'display' : tempDisp,
				'height' : height
			});
		}
		
		function visHeight( anim ){
			var wrapHeight = 0;
			s.targ.children(':lt(' + s.opts.visible + ')').each(function(){
				wrapHeight += $(this).outerHeight();
			});
			
			if( anim == 1 ){
				s.elem.stop(true, true).animate({height: wrapHeight}, s.opts.speed);
			}else{
				s.elem.css( 'height', wrapHeight);
			}
		}
		
		function adjHeight(){
			if( s.opts.height == 'auto' && s.opts.visible != 0 ){
				anim = arguments.callee.caller.name == 'init' ? 0 : 1;
				visHeight( anim );
			}else if( s.opts.height == 'auto' ){
				fullHeight();
			}
		}
		
		return {
			up: function(){ moveDir('up'); },
			down: function(){ moveDir('down'); },
			start: start,
			stop: stop,
			options: s.opts
		};
		
    }

    // Attach the object to the DOM
    $.fn[name] = function ( options ) {
        return this.each(function () {
            if (!$.data(this, name)) {
                $.data(this, name, new EasyTicker( this, options ));
            }
        });
    };

})( jQuery, window, document );





		







/* 图表自适应  start*/
/* $(window).resize( function(){
	window.location.reload();
} ); */
/* 图表自适应  end*/

</script> 
</body>
</html>