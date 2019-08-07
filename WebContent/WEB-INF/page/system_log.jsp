<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
.alcenter {text-align: center;}

.inline {display: inline-block;}

.form-inline .form-control {width: auto;}

.clabel {margin-right: 14px;}
</style>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">角色管理</h1>
	</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading"><a href="#">登陆日志</a>&nbsp;&nbsp;|&nbsp;&nbsp;操作日志</div>
					<div class="panel-body">
						<form class="form-inline" role="form">
							<div class="form-group">
								<label for="" class="clabel">姓名:</label><input id="" name="" class="form-control"
									placeholder="提示文字" type="text" style="width: 391px;height: 30px;" />
							</div>
							<div class="form-group">
								<label for="" class="clabel">选择日期:</label><input id="" name="" class="form-control"
									placeholder="请输入..." type="text" style="width: 391px;height: 30px;" />
							</div>
							<div class="form-group">
								<label for="" class="clabel">警号:</label><input id="" name="" class="form-control"
									placeholder="请输入..." type="text" style="width: 391px;height: 30px;" />
							</div>
								<button type="button" class="btn btn-default b23 c02"
								style="width: 75px; height: 30px;">搜索</button>
						</form>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">搜索结果</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatable" class="table table-striped table-hover br04"
							style="text-align: center;">
							<thead>
								<tr>
									<th class="alcenter">案件编号</th>
									<th class="alcenter">案件名称</th>
									<th class="alcenter">描案件受理日期</th>
									<th class="alcenter">案件所属城市</th>
									<th class="alcenter">案件类型</th>
									<th class="alcenter">所属科室</th>
									<th class="alcenter">案件负责人</th>
									<th class="alcenter">创建时间</th>
									<th class="alcenter">案件状态</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="tbcont">
								<tr data-toggle="modal" data-target="#myModal">
									<!-- <td align="left">NO1256358</td> -->
									<td>NO1256358</td>
									<td>海关0001案</td>
									<td>2017-06-13</td>
									<td>上海市</td>
									<td>大宗货物</td>
									<td>侦查处</td>
									<td>张三</td>
									<td>2017-06-13</td>
									<td>办案中</td>
								</tr>
								<tr>
									<td>NO1256358</td>
									<td>海关0001案</td>
									<td>2017-06-13</td>
									<td>上海市</td>
									<td>大宗货物</td>
									<td>侦查处</td>
									<td>张三</td>
									<td>2017-06-13</td>
									<td>办案中</td>
								</tr>
								<tr>
									<td>NO1256358</td>
									<td>海关0001案</td>
									<td>2017-06-13</td>
									<td>上海市</td>
									<td>大宗货物</td>
									<td>侦查处</td>
									<td>张三</td>
									<td>2017-06-13</td>
									<td>办案中</td>
								</tr>
								<tr>
									<td>NO1256358</td>
									<td>海关0001案</td>
									<td>2017-06-13</td>
									<td>上海市</td>
									<td>大宗货物</td>
									<td>侦查处</td>
									<td>张三</td>
									<td>2017-06-13</td>
									<td>办案中</td>
								</tr>
								<tr>
								<td>NO1256358</td>
									<td>海关0001案</td>
									<td>2017-06-13</td>
									<td>上海市</td>
									<td>大宗货物</td>
									<td>侦查处</td>
									<td>张三</td>
									<td>2017-06-13</td>
									<td>办案中</td>
								</tr>
								<tr>
									<td>NO1256358</td>
									<td>海关0001案</td>
									<td>2017-06-13</td>
									<td>上海市</td>
									<td>大宗货物</td>
									<td>侦查处</td>
									<td>张三</td>
									<td>2017-06-13</td>
									<td>办案中</td>
								</tr>
								<tr>
									<td>NO1256358</td>
									<td>海关0001案</td>
									<td>2017-06-13</td>
									<td>上海市</td>
									<td>大宗货物</td>
									<td>侦查处</td>
									<td>张三</td>
									<td>2017-06-13</td>
									<td>办案中</td>
								</tr>
								<tr>
									<td>NO1256358</td>
									<td>海关0001案</td>
									<td>2017-06-13</td>
									<td>上海市</td>
									<td>大宗货物</td>
									<td>侦查处</td>
									<td>张三</td>
									<td>2017-06-13</td>
									<td>办案中</td>
								</tr>
								<tr>
									<td>NO1256358</td>
									<td>海关0001案</td>
									<td>2017-06-13</td>
									<td>上海市</td>
									<td>大宗货物</td>
									<td>侦查处</td>
									<td>张三</td>
									<td>2017-06-13</td>
									<td>办案中</td>
								</tr>
								<tr>
								<td>NO1256358</td>
									<td>海关0001案</td>
									<td>2017-06-13</td>
									<td>上海市</td>
									<td>大宗货物</td>
									<td>侦查处</td>
									<td>张三</td>
									<td>2017-06-13</td>
									<td>办案中</td>
								</tr>
							</tbody>
						</table>
						<div class="alcenter" style="font-size: 14px">
							<div class="pagecount inline" style="height: 29px;">
								<span>共48条，当前2/5页</span>
							</div>
							<div class="pagebar inline"
								style="position: absolute; right: 10px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;">
									<li><a href="#">&lt;</a></li>
									<li><a href="#">1</a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">4</a></li>
									<li><a href="#">5</a></li>
									<li><a href="#">&gt;</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div>
			<input type="button" class="b23 c02"
				onclick="javascript:window.location.href='<%=basePath%>admin/edit_case.php';"
				style="width: 135px; height: 30px; border-style: hidden; margin-top: 2%; margin-left: 46%;"
				value="下一步">
		</div>
	</div>
	<jsp:include page="footer2.jsp"></jsp:include>
	<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
</body>
</html>