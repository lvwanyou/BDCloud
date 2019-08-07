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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/easyui2.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css" />
<script type="text/javascript" src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>template/js/jquery.easyui.min.js"></script>
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

.clabel {
	margin-right: 14px;
}
.panel-header, .datagrid-header{
	display: none;
}
.datagrid-body::-webkit-scrollbar{
			width: 10px;
			height: 10px;
		}
		.datagrid-header td
		{
		    border:none;
		}
		.panel{
		margin-bottom: 0;
	}
	.panel-body{
		padding: 0 0 0 0;
		border: none;
	}
	.panel-header{
		padding:0;
		border-width:0;
		border-top-width:1px;
	}
	.datagrid-header-row{
		width:100%;
	}
	.datagrid-body td{
		height:100%;
		border-width: 0 0 0 0;
	}
	.table > thead > tr > th{
		border-bottom: none;
	}
	.table {
		border-collapse: inherit;
	}
	.table > tbody > tr > td, .table > tfoot > tr > td{border-top: none;}
	span.tree-folder + span.tree-checkbox{
		display: none;
	}
	.tree-checkbox {
		display: none;
	}
	span.tree-indent + span.tree-indent + span.tree-indent + span.tree-indent + span.tree-file + span.tree-checkbox{
		display: inline-block;
	}
	.datagrid-body td[field="sec"]{
		display: none;
	}
	/* .datagrid-view2{right:0;}
	.datagrid-wrap,.datagrid-view,.datagrid-view2,.datagrid-header,.datagrid-body,.datagrid-footer{width:100% !important;} */
</style>


</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
  		<h1 class="m-n h4">新建案件授权</h1>
	</div>
	 <div class="" style="padding-left: 20px;padding-top: 15px;">
			<a href="<%=basePath%>admin/case_new_grant.php" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="return">返回上一级</a>
		</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="row"></div>
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">选择人员</div>
					<div class="panel-body" style="padding: 0 0 300px;">
						<input type="hidden" name="caseIDStr" id="caseIDStr" value="${id }"/>
						<input type="hidden" name="caseNOStr" id="caseNOStr" value="${noStr }"/>
						<div style="margin: 20px 130px 8px 130px;word-break:break-all;">已选案件：<span class="c25" id="showChooseSpan">${str }</span></div>
						<div style="height: 350px;margin: 0px 130px 0px 130px">
							<div class="br13 b08" style="float: left; width: 47%; height: 100%;">
								<table title="Folder Browser" class="easyui-treegrid c26 br21"
									style="width: 100%; height: 100%; float: left; margin-bottom: 0px;font-size: 16px;text-align: center;"
									id="caseTreeGrid_ds"
									data-options="
											<!--  url: '<%=basePath%>json.php',-->
											<!-- url: '<%=basePath%>admin/initUserTree.php',-->
											lines: true,
											checkbox: true,
											idField: 'id',						 
											animate:'true',
											treeField: 'text',
											onClickCell:onClickCells_ds,
											loader:myloader
									">
									<thead class="b30 c26 br21"
										style="font-size: 20px;text-align: center; display: none;">
										<tr>
											<th data-options="field:'text'" style="width: 40%;">
												<!-- <input type="checkbox" id="_REPLACE_MAIN"  class="_table-blue-checkbox-hidden"/> -->
												<span class="c27" style="font-size: 14px"></span>
											</th>
											<th data-options="field:'pri'" style="width: 20%;">
												<span class="c27" font-size: 14px"></span>
											</th>
											<th data-options="field:'sec'" style="display: none;">
												<span class="c27" font-size: 14px"></span>
											</th>
										</tr>
									</thead>
								</table>
							</div>
							<div style="float: left;width: 6%;text-align: center;height: 100%;padding-top: 160px;">
								<img alt="" src="<%=basePath %>template/img/rightmove.png" style="cursor: pointer;">
							</div>
							<div class="br13 b08" style="display: inline-block;width: 47%;height: 100%;">
								<table id="datatable" class="table table-hover" style="text-align: center;">
									<thead>
										<tr>
											<th class="alcenter" style="width: 38%;">姓名</th>
											<th class="alcenter" style="width: 37%;">所在科室</th>
											<th class="alcenter" style="width: 25%;">职务</th>
										</tr>
									</thead>
									<tbody id="tbcont">
									</tbody>
								</table>
							</div>
						</div>
						<div class="foot_button" align="center" style="margin-top: 54px;">
							<a href="<%=basePath%>admin/case_new_grant.php"><button class="btn w-xs btn-default" style="margin-right: 20px;width: 135px;">上一步</button></a>
                			<a id="nextA" href="<%=basePath%>admin/case_grant_confirm.php" onclick="return setHref();"><button class="btn w-xs btn-info" style="width: 135px;">下一步</button></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
 <!-- 选择授权人弹窗 -->
		<div class="modal fade" id="checkTurstee_tanchuan" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 476px;">
				<div class="modal-content">
					<input type="hidden" name="resetPassword_successes5" id="resetPassword_successes5"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"><img alt="" src="<%=basePath %>template/img/close.png"></button>
						<h3 class="modal-title c20" id="myModalLabel" style="border-left: none;">选择授权</h3>
					</div>
					<div class="modal-body">请选择授权人！</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
					</div> 
				</div>
			</div>
		</div>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		var uidArr = new Array();
		
		Array.prototype.indexOf = function(val) {
			for (var i = 0; i < this.length; i++) {
				if (this[i] == val) return i;
			}
			return -1;
		};
		Array.prototype.remove = function(val) {
			var index = this.indexOf(val);
			if (index > -1) {
				this.splice(index, 1);
			}
		};
		
		function myloader(param,success,error){
		    $.ajax({
		        url:"<%=basePath %>admin/initUserTree.php",
		        data:{
		        },
		        type:"get",
		        dataType:"json",
		        success: function(data){
		            success(data); 
		            $(".tree-checkbox").click(function() {
		            	var checkClass = $(this).attr("class");
		            	var id = $(this).parent().parent().parent().attr("node-id");
		            	if(checkClass=='tree-checkbox tree-checkbox0'){
		            		//alert($(this).parent().parent().parent().attr("node-id"));
		            		//alert($(this).parent().find(".tree-title").text());
		            		//datagrid-cell-c1-pri
		            		//alert($(this).parent().parent().parent().find(".datagrid-cell-c1-pri").text());
		            		//datagrid-cell-c1-sec
		            		//alert($(this).parent().parent().parent().find(".datagrid-cell-c1-sec").text());
		            		
		            		var name = $(this).parent().find(".tree-title").text();
		            		var pri = $(this).parent().parent().parent().find(".datagrid-cell-c1-pri").text();
		            		var sec = $(this).parent().parent().parent().find(".datagrid-cell-c1-sec").text();
		            		$("#tbcont").append('<tr id="'+id+'_tr"><td>'+name+'</td><td>'+sec+'</td><td>'+pri+'</td></tr>');
		            		uidArr.push(id);
		            	}else {
		            		if($("#"+id+"_tr")){
		            			$("#"+id+"_tr").remove();
		            			uidArr.remove(id);
		            		}
		            	}
			   	 	});
		        }
		    });
		}
		
		function setHref(){
		    if(uidArr.length===0){
		    	$('#checkTurstee_tanchuan').modal('show');
		    /* 	alert("请至少选择一个人员！"); */
		    	return false;
		    }
		    var caseIDStr = $("#caseIDStr").val();
		    var caseNameStr = $("#showChooseSpan").text();
		    var caseNOStr = $("#caseNOStr").val();
		    var param = "caseIDStr="+caseIDStr+"&caseNOStr="+caseNOStr+"&caseNameStr="+caseNameStr+"&uidStr="+uidArr.toString();
			param = encodeURI(param);
			param = encodeURI(param);
		    $("#nextA").attr("href","<%=basePath%>admin/case_grant_confirm.php?"+param);
			return true;
		}
		
		function onClickCells_ds(field,row){
		}

	</script>

	<!-- <script type="text/javascript">
		$(function() {
			//移到右边
			$('#add').click(function() {
				//先判断是否有选中
				if (!$("#select1 option").is(":selected")) {
					alert("请选择需要移动的选项")
				}
				//获取选中的选项，删除并追加给对方
				else {
					$('#select1 option:selected').appendTo('#select2');
				}
			});

			//移到左边
			$('#remove').click(function() {
				//先判断是否有选中
				if (!$("#select2 option").is(":selected")) {
					alert("请选择需要移动的选项")
				} else {
					$('#select2 option:selected').appendTo('#select1');
				}
			});

			//全部移到右边
			$('#add_all').click(function() {
				//获取全部的选项,删除并追加给对方
				$('#select1 option').appendTo('#select2');
			});

			//全部移到左边
			$('#remove_all').click(function() {
				$('#select2 option').appendTo('#select1');
			});

			//双击选项
			$('#select1').dblclick(function() { //绑定双击事件
				//获取全部的选项,删除并追加给对方
				$("option:selected", this).appendTo('#select2'); //追加给对方
			});

			//双击选项
			$('#select2').dblclick(function() {
				$("option:selected", this).appendTo('#select1');
			});

		});
	</script> -->
<jsp:include page="footer2.jsp"></jsp:include>
</body>
</html>