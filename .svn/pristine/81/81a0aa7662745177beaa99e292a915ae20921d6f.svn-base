<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/easyui2.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>template/js/jquery.easyui.min.js"></script>
<style>


 		.control-label {
            padding-left: 233px;
            padding-right: 18px;
        }

        .col-lg-3 {
            width: 30%;
        }

        .col-lg-5 {
            padding-left: 0px;
            margin-top: 15px;
        }

        .btn {
            width: 75px;
            height: 30px;
        }

        .col-md-12 {
            padding: 0px;
        }

        th, .alcenter {
            text-align: center;
        }

        .im_flag {
            font-size: 18px;
            position: absolute;
            margin-left: -10px;
        }
		.form-group label {
            margin-top: 19px;
        }

        /* addStyle By Yangbeibei */
        .mgAdd {
            text-align: right;
        }

        /* .Addp{display:inline-block;line-height:normal;} */
        * {
            margin: 0;
            padding: 0;
            -webkit-tap-highlight-color: rgba(255, 255, 255, 0);
            -webkit-overflow-scrolling: touch;
            overflow-scrolling: touch;
        }

        ul {
            list-style: none;
        }

        a {
            text-decoration: none;
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
	margin-right:14px;
}
.row{
	margin-left: 0px;
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
		margin-bottom: 20px;
		text-align: inherit;
	}
	.panel-body{
		padding: 15px;
		border: none;
		font-size: 14px;
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
	.panel.datagrid.easyui-fluid{
		overflow: hidden;
		text-align: left;
		border: 0;
		margin: 0;
	}
	.col-md-2 .panel-body{
		padding: 0 0 0 0;
	}
	.col-md-2 .panel{
		border-bottom: none;
	}
	#table-modal_adduser .form-group, #table-modal_edituser .form-group{
		width: 50%;
		float: left;
	}
	#table-modal_adduser .form-group .control-label, #table-modal_edituser .form-group .control-label {
		padding-top: 7px;
		padding-right: 0px;
	}
	#table-modal_adduser .form-group .col-lg-8, #table-modal_edituser .form-group .col-lg-8{
		padding-left: 0px;
	}
	.tree-title{
		cursor: pointer;
		padding:0 8px;
	}
	/* .tree-title{color:#666;font-size:14px;height:30px;line-height:30px;}
	.tree-checkbox0,.tree-hit,.tree-checkbox1,.tree-checkbox2{margin-top:5px;} */
	.tree-title{ font-size:14px;height:30px;line-height:30px;}
	.form-inline .form-control{width:200px;}


</style>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
		<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">角色管理</h1>
	</div>
	<div id="d1" class="container">
		<div class="col-md-12">
			<div class="row" style="margin-top: 1%;">
				<div class="panel panel-default">
					<div class="panel-heading">搜索<span style="float: right;"><img src="<%=basePath %>template/img/addevidence.png" onclick="testOfSearch(this)"></span></div>
					<div class="panel-body" id="searchOfForm" style="display: block;">
						<form class="form-inline" role="form" id="searchForm" action="<%=basePath%>admin/getEvidence.php" method="post">
							<input type="hidden" name="pageno" id="pageno"/>
							<div class="form-group" style="width:25%">
								<label for="" class="clabel" style="margin-top:0px;">角色名称:</label>
								<input id="rolename" name="evName" value="" 
									class="form-control" placeholder="请输入文件名..." type="text" style="width:75%" onkeydown="onKeyDown(event)"/>
							</div>
						
							
							<button type="button" class="btn btn-info" style="width:80px;" onclick="getrole(1)">搜索</button>
							<a id="url" href="#"><div  class="btn btn-info" style="width:80px;"onclick="d2()">添加</div></a>
						</form>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="panel panel-default" style=" margin-top: 20px;">
					<div class="panel-heading">角色列表 </div>
					<div class="panel-body" style="padding: 0 0 15px;">
						<table id="datatables" class="table table-striped table-hover br04"
							style="text-align: center;">
							<thead>
								<tr id="ceshi" >
									<th class="alcenter">角色名称</th>
									<th class="alcenter">数据范围</th>
									<!-- <th class="alcenter">所属机构</th> -->
									<!-- <th class="alcenter">部门</th>
									<th class="alcenter">科室</th> -->
									<th class="alcenter">描述</th>
									<th class="alcenter">添加人</th>
									<th class="alcenter">创建时间</th>
									<th class="alcenter">编辑</th>
									<th class="alcenter">删除</th>
									
								</tr>
							</thead>
							<tbody id="tbcont">
							
							</tbody>
						</table>
						<!-- 分页 -->
						<div class="alcenter" style="font-size: 14px; padding-top: 20px; padding-bottom: 20px;">
							<div  class="pagecount inline" style="height: 29px; padding-left: 0%;">
								<span id="tot">
								
								</span>
							</div>
							<div class="pagebar inline" style="position: absolute; right: 128px; height: 29px;">
								<ul class="pagination pagination-sm" style="margin: 0;" id="pageUL">
								
								</ul>
							</div>
							<div style="float: right;margin-right: 11px;">
								<input class="form-control" type="text" style="width:52px;height:28px;border-radius:2px; display: inline;" id="givePages_phoneQuzheng" name="givePages_phoneQuzheng" onkeydown="onKeyDown(event)"/>
								<button type="button" class="btn" onclick="getrole(1)" style="width:52px;height:28px;line-height: 12px;">跳转</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
<!-- 添加页面 -->
		 <div id="d2" style="width:100%;opacity:0">
			<div class="row">
				<div class="panel panel-default" style="margin-left: 0px; margin-top: 20px;">
					<div class="panel-heading">添加角色</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						 <div class="panel-body" style="padding-top: 35px;">
                        <!-- <form class="bs-example form-horizontal"> -->

                        <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd">
                            <i class="im_flag">*</i><span class="Addp" >角色名称</span></label>
                            <div class="col-lg-5" >
                                <input class="form-control" id="addroleName"  type="text" onblur="validateAddEvName(this)" placeholder="输入角色名称"> 
                            </div>
                        </div>
 						<div class="form-group">
                            <label class="col-lg-3 control-label mgAdd">角色描述</label>
                            <div class="col-lg-5">
								<textarea id="adddescribe" class="form-control"  rows="5" maxlength="140" placeholder="140字以内"></textarea>
                            </div>
                        </div>
                    <!--     <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd"><span class="im_flag">*</span>部门</label>
                            <div class="col-lg-5">
                                <select class="form-control" id="addDepartment" onclick="getSection1()">
                                    
                                </select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-3 control-label mgAdd"><span class="im_flag">*</span>科室</label>
                            <div class="col-lg-5">
                                <select class="form-control" id="addSection" >
                                    
                                </select>
                            </div>
                        </div> -->
                           <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd"><span class="im_flag">*</span>数据范围</label>
                            <div class="col-lg-5">
                                <select class="form-control" id="adddataScope" >
                                    <option>个人数据</option>
                                    <option>科室数据</option>
                                    <option>部门数据</option>
                                    <option>所有数据</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd">
                            <i class="im_flag">*</i><span class="Addp" >角色授权</span></label>
                            <div class="col-lg-5">
                                <input class="form-control" id="addevName" name="addevName" type="text" onblur="validateAddEvName(this)" placeholder="勾选下面菜单"> 
                                 <div class="row" style="height: 300px;margin: 0px;">
									<div class="panel panel-default" style="height: 300px;">
										
										<div id="ds_div" class="panel-body" style="padding-left:15px;height: 300px;overflow:auto;padding-top: 0px;">
											<table title="Folder Browser" class="easyui-treegrid c26 br21"
														style="width: 100%; height: 100%; float: left; margin-bottom: 0px; font-size: 16px;text-align: center;"
														id="caseTreeGrid_ds"
														data-options="
																<!--  url: '<%=basePath%>json.php',-->
																<!-- url: '<%=basePath%>admin/initDSTree.php',-->
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
															<tr style="height:30px;line-height:30px;">
																<th data-options="field:'text'" style="width: 90%;">
																	<!-- <input type="checkbox" id="_REPLACE_MAIN"  class="_table-blue-checkbox-hidden"/> -->
																	<span class="c27" style="font-size: 14px"></span>
																</th>
															</tr>
														</thead>
													</table>
										</div>
									</div>
								</div>			
                            </div>
                          
                        </div>
                        
                        <div class="" style="padding-left: 20px;margin-top: 22px;">
                            <span id="format1" class="empty"></span>
                            <span id="format" class="empty"></span>
                        </div>
             
                        <div class="form-group" style="margin-top: 54px;">
                            <div class="col-lg-offset-5 col-lg-5">
                                <button type="submit" class="btn w-xs btn-info"
                                        style="margin-right: 30px;" onclick="addRole()">提交
                                </button>
                                <button type="reset" class="btn w-xs btn-default"
                                        style="margin-right: 30px;" onclick="fanhui()">返回
                                </button>

                            </div>
                        </div>

                        <!-- </form> -->
                    </div>
						
						
						
					</div>
				</div>
			</div>
	</div>

<!-- 编辑页面 -->
		 <div id="d3" style="width:100%;opacity:0">
			<div class="row">
				<div class="panel panel-default" style="margin-left: 0px; margin-top: 20px;">
					<div class="panel-heading">编辑角色</div>
					<div class="panel-body" style="padding: 0 0 15px;">
						 <div class="panel-body" style="padding-top: 35px;">
                        <!-- <form class="bs-example form-horizontal"> -->
						<div style=" display:none;" id="editid" ></div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd">
                            <i class="im_flag">*</i><span class="Addp" >角色名称</span></label>
                            <div class="col-lg-5" >
                                <input class="form-control" id="editroleName"  type="text" > 
                            </div>
                        </div>
 						<div class="form-group">
                            <label class="col-lg-3 control-label mgAdd">角色描述</label>
                            <div class="col-lg-5">
								<textarea id="editdescribe" class="form-control"  rows="5" maxlength="140" placeholder="140字以内"></textarea>
                            </div>
                        </div>
                        <!-- <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd"><span class="im_flag">*</span>直属机构</label>
                            <div class="col-lg-5">
                                <select class="form-control" id="editorganization" >
                                    
                                </select>
                            </div>
                        </div> -->


						<!-- <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd"><span class="im_flag">*</span>部门</label>
                            <div class="col-lg-5">
                                <select class="form-control" id="editDepartment" onclick="getSection2()">
                                    
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd"><span class="im_flag">*</span>科室</label>
                            <div class="col-lg-5">
                                <select class="form-control" id="editSection" >
                                    
                                </select>
                            </div>
                        </div> -->

                           <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd"><span class="im_flag">*</span>数据范围</label>
                            <div class="col-lg-5">
                                <select class="form-control" id="editdataScope" >
                                    <option>个人数据</option>
                                    <option>科室数据</option>
                                    <option>部门数据</option>
                                    <option>所有数据</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label mgAdd">
                            <i class="im_flag">*</i><span class="Addp" >角色授权</span></label>
                            <div class="col-lg-5">
                                <input class="form-control" id="addevName" name="addevName" type="text" onblur="validateAddEvName(this)" placeholder="勾选下面菜单"> 
                                 <div class="row" style="height: 300px;margin: 0px;">
									<div class="panel panel-default" style="height: 300px;">
										
										<div id="ds_div2" class="panel-body" style="padding-left:15px;height: 300px;overflow:auto;padding-top: 0px;">
											<table title="Folder Browser" class="easyui-treegrid c26 br21"
														style="width: 100%; height: 100%; float: left; margin-bottom: 0px; font-size: 16px; text-align: center;"
														id="caseTreeGrid_ds2"
														data-options="
																<!--  url: '<%=basePath%>json.php',-->
																<!-- url: '<%=basePath%>admin/initDSTree.php',-->
																lines: true,
																checkbox: true,
																idField: 'id',						 
																animate:'true',
																treeField: 'text',
																onClickCell:onClickCells_ds,
																loader:myloader
														">
														<thead class="b30 c26 br21"
															style="font-size: 20px; text-align: center; display: none;">
															<tr style="height:30px;line-height:30px;">
																<th data-options="field:'text'" style="width: 90%;">
																	<!-- <input type="checkbox" id="_REPLACE_MAIN"  class="_table-blue-checkbox-hidden"/> -->
																	<span class="c27" style="font-size: 14px"></span>
																</th>
															</tr>
														</thead>
													</table>
										</div>
									</div>
								</div>			
                            </div>
                          
                        </div>
                        
                        <div class="" style="padding-left: 20px;margin-top: 22px;">
                            <span id="format1" class="empty"></span>
                            <span id="format" class="empty"></span>
                        </div>
             
                        <div class="form-group" style="margin-top: 54px;">
                            <div class="col-lg-offset-5 col-lg-5">
                                <button type="submit" class="btn w-xs btn-info"
                                        style="margin-right: 30px;" onclick="editRole()">提交
                                </button>
                                <button type="reset" class="btn w-xs btn-default"
                                        style="margin-right: 30px;" onclick="fanhui()">返回
                                </button>

                            </div>
                        </div>

                        <!-- </form> -->
                    </div>
						
						
						
					</div>
				</div>
			</div>
	</div>
	<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>

			
<script type="text/javascript">

//get机构getOrganize
<%-- $(function (){
   $.ajax({
        url:"<%=basePath %>system/getdDepartment.php",
        data:{
        },
        type:"post",
        dataType:"json",
        success: function(data){       	
        	$("#addDepartment").empty();
        	$("#editDepartment").empty();
        	var html = '<option value="-1" >请选择</option>';
        	$("#addDepartment").append(html);
        	$("#editDepartment").append(html);
        	$.each(data,function(i,item){
				var html01 = '<option value="'+item.id+'" >'+item.departmentName+'</option>';
				$("#addDepartment").append(html01);
				$("#editDepartment").append(html01);
			});
        }
    });  
});


function getSection1(){
	var department = $("#addDepartment").val();
	   $.ajax({
	        url:"<%=basePath %>system/getSection.php",
	        data:{
	        	"department":department
	        },
	        type:"post",
	        dataType:"json",
	        success: function(data){
	       		$("#addSection").empty();
	        	$.each(data,function(i,item){
					var html01 = '<option value="'+item.id+'" >'+item.sectionName+'</option>';
		        	$("#addSection").append(html01);
				});
	        }
	    });  
	};
	function getSection2(){
		var department = $("#editDepartment").val();
		   $.ajax({
		        url:"<%=basePath %>system/getSection.php",
		        data:{
		        "department":department	
		        },
		        type:"post",
		        dataType:"json",
		        success: function(data){
		        	
		        	$("#editSection").empty();
		        	$.each(data,function(i,item){
						var html01 = '<option value="'+item.id+'" >'+item.sectionName+'</option>';
			        	$("#editSection").append(html01);
					});
		        }
		    });  
		}; --%>

//get角色
window.onload = getrole(1);
function getrole(page){
	
	var givePages_phoneQuzheng = $("#givePages_phoneQuzheng").val();
	if(givePages_phoneQuzheng != ""){
		page = parseInt(givePages_phoneQuzheng);
	}
	var name=$("#rolename").val();
 $.ajax({
      url:"<%=basePath %>system/getRole.php",
      data:{
    	  "pageIndex":page,
    	  "name":name
      },
      type:"post",
      dataType:"json",
      success: function(data){
    	    var sum=data.count;//总数
			var sum2=10;//每页个数
			var sum3=parseInt( sum / (sum2+1)+1);//总页数
    	//分页开始
			$("#pageUL").html("");
			var tmp = "";
			if(parseInt(page) == 1)
				tmp += '<li id="orderA_5"><a href="javascript:void(0)" onclick="getrole('+sum3+')">&lt;</a></li >';
			else
				tmp += '<li id="orderA_5"><a href="javascript:void(0)" onclick="getrole('+ (parseInt(page) - 1) +')">&lt;</a></li >';
			var flag=0;
			for(p = 1; p <= sum3; p++){
				if(p>=page-5 && flag<5){
					if(p == page)
						tmp += '<li class="active" id="orderA_5"><a href="javascript:void(0)" onclick="getrole('+ p +')">'+p+'</a></li >';
					else
						tmp += '<li id="orderA_5"><a href="javascript:void(0)" onclick="getrole('+ p +')">'+p+'</a></li >';
					flag++;
				}
			}
			if(page == sum3)
				tmp += '<li id="orderA_5"><a href="javascript:void(0)" onclick="getrole(1)">&gt;</a></li >';
			else
				tmp += '<li id="orderA_5"><a href="javascript:void(0)" onclick="getrole('+ (parseInt(page) + 1) +')">&gt;</a></li >';
			$("#pageUL").html(tmp);
			$("#tot").html("共"+sum+"条,当前"+page+"/"+sum3+"页");
			//分页结束
    	  
    	  
      	$("#tbcont").empty();
      	$.each(data.list,function(i,item){
		    var html1 = 
					'<tr>'+
						'<td style=" display:none;" id="roleid'+i+'" >'+item.id+'</td>'+
						/* '<td style=" display:none;" id="department'+i+'" >'+item.department.split("/")[0]+'</td>'+
						'<td style=" display:none;" id="section'+i+'" >'+item.section.split("/")[0]+'</td>'+ */
						'<td style=" display:none;" id="roleMenu'+i+'" >'+item.roleMenu+'</td>'+
						'<td id="roleName'+i+'" >'+item.roleName+'</td>'+
						'<td id="dataScope'+i+'" >'+item.dataScope+'</td>'+
						/* '<td >'+item.department.split("/")[1]+'</td>'+
						'<td >'+item.section.split("/")[1]+'</td>'+ */
						'<td id="roleDescribe'+i+'" >'+item.roleDescribe+'</td>'+
						'<td>'+item.addMan+'</td>'+
						'<td>'+item.addTime+'</td>'+
						'<td><a href="#" class="btnA" onclick="d3('+i+')">编辑</a></td>'+
						'<td><a href="#" class="btnA" data-toggle="modal" data-target="#table-modal_deluser" onclick="deleteRole('+item.id+')">删除</a></td>'+
					'</tr>';
			$("#tbcont").append(html1);	
			});
      }
  });  
};
var dsId="";
function d2() {
	 $("#d1").hide();
	 $("#d3").hide();
	 $("#d2").show();
	 $("#d2").css("opacity","1");
}
function d3(idi) {
	 $("#d1").hide();
	 $("#d2").hide();
	 $("#d3").show();
	 $("#d3").css("opacity","1");
	 
	 var roleid=$("#roleid"+idi).html();
	 var roleName=$("#roleName"+idi).html();
	 var dataScope=$("#dataScope"+idi).html();
	/*  var department=$("#department "+idi).html();
	 var section=$("#section"+idi).html(); */
	 var roleDescribe=$("#roleDescribe"+idi).html();
	 var roleMenu=$("#roleMenu"+idi).html();
	 $("#editid").val(roleid);
	 $("#editroleName").val(roleName);
	 $("#editdescribe").val(roleDescribe);
	/*  $("#editDepartment").val(department);
	 $("#editSection").val(section); */
	 $("#editdataScope").val(dataScope);
	 var roleMenus=roleMenu.split(",");
	 
	 var oUl=document.getElementById("ds_div2"); 
		var oEle = oUl.getElementsByTagName("*");
	    for (var i = 0; i < oEle.length; i++) {
	    	 if (oEle[i].className == "tree-checkbox tree-checkbox0") {
	    		 var id=oEle[i].parentNode.parentNode.parentNode.getAttribute("node-id");
	    		 for (var j = 0; j < roleMenus.length; j++) {
		    		  if(roleMenus[j]==id){
		    			  oEle[i].className = "tree-checkbox tree-checkbox1" ;
		    		  }
		    	  }
	         }
	    };
	 
}
function fanhui() {
	 $("#d1").show();
	 $("#d2").css("opacity","0");
	 $("#d3").css("opacity","0");
}
//回车搜索事件
function onKeyDown(event){
   var e = event || window.event || arguments.callee.caller.arguments[0];
   if(e && e.keyCode==13){ // enter 键
	   showAllHandledFile(1);
   }
}
function myloader(param,success,error){
    $.ajax({
        url:"<%=basePath %>system/getManage.php",
        data:{
        },
        type:"post",
        dataType:"json",
        success: function(data){
            success(data); 
        }
    });
}
function onClickCells_ds(field,row){
	
	var tempid = String(row.id);
	dsId=tempid;
	//alert(dsId);
	//searchUser(1);
}
//添加角色
function addRole(){
	var addroleName = $("#addroleName").val();
	var adddescribe = $("#adddescribe").val();
	/* var addDepartment = $("#addDepartment").val();
	var addSection = $("#addSection").val(); */
	var adddataScope = $("#adddataScope").val();
	//获取勾选菜单id
	var roleMenu="";
	var oUl=document.getElementById("ds_div"); 
	var oEle = oUl.getElementsByTagName("*");
    for (i = 0; i < oEle.length; i++) {
        if (oEle[i].className == "tree-checkbox tree-checkbox1" || oEle[i].className == "tree-checkbox tree-checkbox2") {
        	var id=oEle[i].parentNode.parentNode.parentNode.getAttribute("node-id");
        	if(roleMenu==""){
        		roleMenu=id;
        	}else{
        		roleMenu+=","+id;
        	}
        }
    };
   
   $.ajax({
        url:"<%=basePath %>system/addRole.php",
        data:{
        	"roleName":addroleName,
        	"describe":adddescribe,
        	"department":"",
        	"section":"",
        	"dataScope":adddataScope,
        	"roleMenu":roleMenu
        },
        type:"post",
        dataType:"json",
        success: function(data){
        	 window.location.href= "<%=basePath%>system/showRole.php";
        },
		error: function() {
			window.location.href= "<%=basePath%>system/showRole.php";
		}
    }); 
   
}
//编辑角色
function editRole(){
	var editid = $("#editid").val();
	var editroleName = $("#editroleName").val();
	var editdescribe = $("#editdescribe").val();
	/* var editDepartment = $("#editDepartment").val();
	var editSection = $("#editSection").val(); */
	var editdataScope = $("#editdataScope").val();
	//获取勾选菜单id
	var roleMenu="";
	var oUl=document.getElementById("ds_div2"); 
	var oEle = oUl.getElementsByTagName("*");
    for (i = 0; i < oEle.length; i++) {
        if (oEle[i].className == "tree-checkbox tree-checkbox1" || oEle[i].className == "tree-checkbox tree-checkbox2") {
        	var id=oEle[i].parentNode.parentNode.parentNode.getAttribute("node-id");
        	if(roleMenu==""){
        		roleMenu=id;
        	}else{
        		roleMenu+=","+id;
        	}
        }
    };
   
   $.ajax({
        url:"<%=basePath %>system/editRole.php",
        data:{
        	"roleid":editid,
        	"roleName":editroleName,
        	"describe":editdescribe,
        	"department":"",
        	"section":"",
        	"dataScope":editdataScope,
        	"roleMenu":roleMenu
        },
        type:"post",
        dataType:"json",
        success: function(data){
        	 window.location.href= "<%=basePath%>system/showRole.php";
        },
		error: function() {
			window.location.href= "<%=basePath%>system/showRole.php";
		}
    }); 
   
}
//删除角色
function deleteRole(roleid){
	  $.ajax({
	        url:"<%=basePath %>system/deleteRole.php",
	        data:{
	        	"roleid":roleid
	        },
	        type:"post",
	        dataType:"json",
	        success: function(data){
	        	 window.location.href= "<%=basePath%>system/showRole.php";
	        },
			error: function() {
				window.location.href= "<%=basePath%>system/showRole.php";
			}
	    }); 
}
</script>

</body>
</html>