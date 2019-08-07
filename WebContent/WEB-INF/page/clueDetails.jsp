<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String partmentss2 = session.getAttribute("partment").toString();
	String sectionss2 = session.getAttribute("section").toString();
	String roless2 = session.getAttribute("role").toString();
	String userIDss2 = session.getAttribute("sUserID").toString();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/color_me.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/easyui.css" />
<link href="<%=basePath%>template/css/cloud-admin.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/css/app.css">

<link rel="stylesheet" type="text/css" href="<%=basePath%>template/font-awesome/css/font-awesome.min.css">

<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>template/js/jquery.easyui.min.js"></script> 
<script src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>

<style>
.easyui-progressbar {
	padding: 1px;
}
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
	width: 150px;   
	white-space: nowrap; 
	text-overflow: ellipsis;
	overflow: hidden; 
} 
.madd{display: inline-block;width: 90px;height: 34px;line-height: 34px;border: 1px solid #ccc;font-size: 14px;vertical-align: middle;border-left: none;text-align: center;background-color:#EFF1F3;}
</style>
</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<div class="bg-light lter b-b wrapper-md">
		<h1 class="m-n h4">线索详情页</h1>
	</div>
	<div class="" style="padding-left: 20px; padding-top: 15px;">
		<%-- <a href="<%=basePath %>admin/clueevidencelist.php" class="btn w-xs btn-info" style="margin-right: 30px;">数据列表</a> --%>
	<button class="btn w-xs btn-info" onclick="goShuJuList()">数据列表</button>
	</div>
	<div >
		<table style="border: 1px red solid; display: none;">
			<tr>
				<td>分局名称</td>
				<td>科室名称</td>
				<td>职位</td>
				<td>id</td>
			</tr>
			
			<tr>
				<td id="sessionPartmentNames33"><%= partmentss2%></td>
				<td id="sessionSectionNames33"><%= sectionss2%></td>
				<td id="sessionRoleNames33"><%= roless2%></td>
				<td id="sessionRoleID33"><%= userIDss2%></td>
			</tr>
		
		</table>
	
	</div>
	<div class="wrapper-md">
		<div class="col-md-12" style="margin-bottom:27px;">
			<div class="panel panel-default">
				<div class="panel-heading">线索基本信息</div>
				<div class="panel-body" style="padding: 0px;">
					<table id="datatable" class="table table-striped table-hover br04" style="text-align: center; margin-bottom: 0px;">
						<thead>
							<tr>
								<th>线索编号</th>
								<th>线索类型</th>
								<th>线索接收时间</th>
								<th>作案时间</th>
								<th>线索来源方式</th>
								<th>作案区域</th>
								    <!-- <th>是否同意</th>   -->
								<th>创建时间</th>
								<th>创建人</th>
								<th>分局</th>
								<th>线索状态</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${involve_Personlist.threadNum }</td>
								<td>${involve_Personlist.threadType }</td>
								<td>${involve_Personlist.threadAcceptDate }</td>
								<td>${involve_Personlist.crimeDate }</td>
								<td>${involve_Personlist.threadSource }</td>
								<td>${region }</td>
								<td id="ifAgreess" style="display: none;">${involve_Personlist.agreeStatus }</td> 
								<td>${involve_Personlist.createdTime }</td>
								<td>${involve_Personlist.reportPerson }</td>
								<td>${departMentsNames }</td>
								<td id="clueDisposalS">${involve_Personlist.disposal }</td>
								<td id="clueIds2" style="display: none;">${involve_Personlist.id }</td>
								<td id="fenjuAngQita" style="display: none;">${involve_Personlist.other }</td>
								<td id="otherTextss" style="display: none;">${involve_Personlist.otherText }</td>
								
								<td id="departmentss1" style="display: none;">${involve_Personlist.userPartment }</td>   	<!-- 哪个分局 -->
								<td id="sectionss1" style="display: none;">${involve_Personlist.userSection }</td>			<!-- 哪个科室 -->
								<td id="roless1" style="display: none;">${involve_Personlist.userRole }</td>				<!-- 职位   数据库线索表存的建立线索人的职位数字 -->
								<td id="userIDs" style="display: none;">${involve_Personlist.userID }</td>				<!-- 添加线索人的主键 -->
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">线索内容</div>
                <textarea class="form-control" readonly name="susItem" id="susItem" style="width:100%;">${involve_Personlist.susItem }</textarea>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">涉案信息</div>
					<div class="panel-body" style="padding: 0 0 15px;">
					<div class="form-group" style="margin-top: 15px;">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;">涉嫌单位</label>
                         <div class="col-lg-11">
								<table class="table table-striped table-hover br04" style="table-layout: fixed;">
									<thead>
										<tr>
											<th style="text-align: left;">单位名称</th>
											<th style="text-align: left;">地址</th>
											<th style="text-align: left;">海关号</th>
										</tr>
									</thead>
									<tbody>
											<c:forEach items="${suspectUnit3}" var="suspectUnit3Item">
												<tr>
													<td>${suspectUnit3Item.name }</td>
													<td>${suspectUnit3Item.address }</td>
													<td>${suspectUnit3Item.customsRegistrationNumber }</td>
												</tr>
											</c:forEach>
									</tbody>
								</table>
                         </div>
                    </div>
                    
                    <div class="form-group">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;">涉嫌人员</label>
                         <div class="col-lg-11">
								<table class="table table-striped table-hover br04" style="border: 1 #DCDCDC solid; ">
									<thead>
										<tr>
											<th style="text-align: left;">嫌疑人姓名</th>
											<th style="text-align: left;">手机号码</th>
											<th style="text-align: left;">电子邮箱</th>
											<th style="text-align: left;">身份证</th>
											<th style="text-align: left;">通讯地址</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${suspectInfoList}" var="suspectItem">
											<tr>
												<td>${suspectItem.suspectName }</td>
												<td>${suspectItem.suspectPhone }</td>
												<td>${suspectItem.suspectMail }</td>
												<td>${suspectItem.suspectName }</td>
												<td>${suspectItem.suspectHomeAddress }</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<input type="hidden" id="roleNamess" value="${roless}"/>
                         </div>
                    </div>
			</div>
			</div>
			
			<div class="panel panel-default" id="noChuzhi">
				<div class="panel-heading">线索处置</div>
				<div class="panel-body" style="padding: 0 0 15px;">
					<div class="form-group" style="width: 70%;margin: auto; margin-top: 15px;overflow:hidden;">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;margin:0px;">线索处置</label>
                         <div class="col-lg-9">
                         		<input type="radio" id="radio1" name="radioParam" style="margin-top:0px;vertical-align:middle;" value="暂存归档" onclick="test0(this)"/><label>暂存归档</label>&nbsp;&nbsp;&nbsp;
                         		<input type="radio" name="radioParam" style="margin-top:0px;vertical-align:middle;" value="积累归档" onclick="test1(this)"/><label>积累归档</label>&nbsp;&nbsp;&nbsp;
                         		<input type="radio" name="radioParam" style="margin-top:0px;vertical-align:middle;" value="线索经营" onclick="test2(this)"/><label>线索经营</label>&nbsp;&nbsp;&nbsp;
                         		<input type="radio" name="radioParam" style="margin-top:0px;vertical-align:middle;" value="线索转出" onclick="test3(this)"/><label>线索转出</label>&nbsp;&nbsp;&nbsp;&nbsp;
                         </div>
                         <input type="hidden" id="paramHidden" name="paramHidden"/>
                    </div>
                    
                    <div id="twoDivIfShow">
                    <div class="form-group" style="width: 70%;margin: auto; margin-top: 15px; margin-bottom: 15px;overflow:hidden;display: none;" id="zhuanchudan">
                      <form id="importForm4" action="<%=basePath%>UploadOfClue.php?clueDetailsId=${involve_Personlist.id }" method="post" enctype="multipart/form-data">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;line-height:22px;line-height:34px;margin:0px;">处理单</label>
                         <div class="col-lg-9" style="font-size:0px;">
							<input class="form-control" type="file" id="file4" name="file4"
										style="opacity: 0; filter: alpha(opacity = 0); width: 373px; padding-top: 12px; cursor: pointer; position: absolute;"
										onchange="setFilePath4();" /> 
										<input class="form-control" id="filePath4" name="filePath4" type="text" style="width: 283px;display:inline-block;color:#d81e06;" placeholder="请上传审批手续文件" value="${involve_Personlist.fileName }">
										<a class="madd">请选择文件</a>
							 			<input type="hidden" id="filePathInfo" value="${involve_Personlist.fileUrl }">
										<span style="display:inline;" id="sizetishi0" ></span>
										
										<input type="hidden" id="case-num" name="case-num" type="text">
										<input type="hidden" id="evidup" name="evidup" value=""  >
                         </div>
                       </form>
                    </div>
                    
                    <div class="form-group" style="width: 70%;margin: auto;overflow:hidden;margin-top:10px;" id="zhuanchu">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;">转出</label>
                         <div class="col-lg-9">
								<input id="fenju" type="radio" style="margin-top:0px;vertical-align:middle;" name="fenjuAndOther1" onclick="fenju(this)" value="分局"/>分局
                    			<input id="other" type="radio" style="margin-left: 18px;margin-top:0px;vertical-align:middle;" name="fenjuAndOther1" onclick="other(this)" value="其它"/>其它
                    			<input class="form-control" value="${involve_Personlist.otherText }" type="text" style="width: 22%;display:inline-block;" id="otherText22" placeholder="请输入...">&nbsp;&nbsp;&nbsp;
                    			
                    			<!-- 科室树形菜单 -->
                    			<div style="height: 350px;margin-top: 18px;width: 620px;;" id="shuxingcaidan">
									<div class="br13 b08" style="float: left; width: 60%; height: 100%;">
										<table title="" class="easyui-treegrid c26 br21" style="width: 100%; height: 100%; float: left; 
										font-size: 16px;text-align: center;" id="caseTreeGrid_ds"
											data-options="
													<!--  url: '<%=basePath%>json.php',-->
													<!-- url: '<%=basePath%>admin/initUserTree.php',-->
													lines: true,
													checkbox: true,
													idField: 'id',						 
													animate:'true',
													treeField: 'text',
													onClickCell:onClickCells_ds,
													loader:myloader,
													fitColumns:false,
													autoScroll: true ,
											">
											<thead class="b30 c26 br21" style="font-size: 20px;text-align: center; display: none;">
												<tr>
													<th data-options="field:'text'" style="width: 40%;">
														<span class="c27" style="font-size: 14px"></span>
													</th>
													<th data-options="field:'pri'" style="width: 20%;">
														<span class="c27" style="font-size: 14px"></span>
													</th>
													<th data-options="field:'sec'" style="display: none;">
														<span class="c27" style="font-size: 14px"></span>
													</th>
												</tr>
											</thead>
										</table>
									</div>&nbsp;&nbsp;&nbsp;
									
									<span id="fenjuNames" onclick="delFenju()">${involve_Personlist.substation }<img src="<%=basePath%>template/img/x.png"></span>
									<input type="hidden" id="getSubstationInfo" value="${involve_Personlist.substation }">
						</div>
                       </div>
                    </div>
                   </div> 
				</div>
			</div>
			<!-- 已经处置后的张展示 -->
			<div class="panel panel-default" id="haveChuzhi" style="display: none;">    
				<div class="panel-heading">线索处置</div>
				<div class="panel-body" style="padding: 0 0 15px;">
					<div class="form-group" style="width: 70%;margin: auto; margin-top: 15px;overflow:hidden;">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;">线索处置</label>
                         <div class="col-lg-6">
							<span id="clueChuzhi" style="margin-left: 15px;"><label>${involve_Personlist.disposal }</label></span>
                         </div>
                    </div>
                    <div class="form-group" style="width: 70%;margin: auto; margin-top: 15px;overflow:hidden;">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;">处理单</label>
                         <div class="col-lg-6">
							<span id="fileNameChuLiDAN" style="margin-left: 15px;"><a href="#" onclick="lookOnlineOfClue()">${involve_Personlist.fileName }(点击可查看详情)</a></span>
                         </div>
                    </div>
                    
                    <div class="form-group" style="width: 70%;margin: auto; margin-top: 15px;overflow:hidden;">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;">转出分局</label>
                         <div class="col-lg-6">
							<span id="fenjuTextss" style="margin-left: 15px;">${involve_Personlist.substation }</span>
                         </div>
                    </div>
                    
				</div>
			</div>
			
			
			<div class="panel panel-default" style="display: none;" id="shenpi">
				<div class="panel-heading">线索审批</div>
				<div class="panel-body" style="padding: 0 0 15px;">
					<div class="form-group" style="width: 70%;margin: auto; margin-top: 15px;overflow:hidden;">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;">审批意见:</label>
                         <div class="col-lg-6">
								<textarea id="addcomment" name="addcomment" class="form-control"
                                          rows="5" maxlength="150" placeholder="150字以内..."></textarea>
                         </div>
                    </div>
                    
                    <div class="form-group" style="width: 70%;margin: auto; margin-top: 15px;">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;"></label>
                         <div class="col-lg-6">
								<input id="agree" type="radio" name="ifagrees" value="同意"/>同意
                    			<input id="disagree" type="radio" name="ifagrees" value="不同意"/>不同意
                         </div>
                    </div>
				</div>
			</div>
			
			<div class="panel panel-default" style="display: none;" id="haveShenpi">
				<div class="panel-heading">线索审批意见</div>
				<div class="panel-body" style="padding: 0 0 15px;">
					<div class="form-group" style="width: 70%;margin: auto; margin-top: 15px;overflow:hidden;">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;">审批信息:</label>
                         <div class="col-lg-6">
								<textarea id="addcomment" readonly name="addcomment" class="form-control" rows="5">
								${involve_Personlist.opinionInfo }
								</textarea>
                         </div>
                    </div>
                    
                    <div class="form-group" style="width: 70%;margin: auto; margin-top: 15px;overflow:hidden;">
                         <label class="col-lg-3 control-label mgAdd" style="text-align: center;width: 120px;">是否同意:</label>
                         <div class="col-lg-6">
								${involve_Personlist.agreeStatus }
                         </div>
                    </div>
				</div>
			</div>
			
			<div class="form-group" style="margin-top: 54px;">
                 <div class="col-lg-offset-5 col-lg-5">
                       <!-- <button type="button" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="addChuzhiAndShenpiInfo(this);">提交</button> -->
                       
                       <c:choose>
							<c:when test="${involve_Personlist.agreeStatus == '同意'}">
							
								<button type="button" class="btn w-xs btn-default" style="margin-right: 30px;background-color: #A7A7A7">提交</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn w-xs btn-info" style="margin-right: 30px;" onclick="addChuzhiAndShenpiInfo(this);">提交</button>
							</c:otherwise>
						</c:choose>
                       
                       <!-- <button type="button" class="btn w-xs btn-default" style="margin-right: 30px;">返回</button> -->
                       <a href="<%=basePath %>admin/cluelist.php" class="btn w-xs btn-default" style="margin-right: 30px;" onclick="return">返回</a>
                 </div>
            </div>
		</div>
	</div>
	<div id="loadingDiv" aria-hidden="false" style="display: none;" class="bootbox modal fade in" tabindex="-1" role="dialog">
            	<div class="modal-dialog" style="width: 190px;margin-top: 22%;">
            		<div class="modal-content">
            			<div class="modal-body">
            				<div class="bootbox-body">
            					<p class="msg">正在加载，请稍后...</p>
                    			<div><img id="flashAdImg" src="<%=basePath %>template/img/loading.gif" alt="loading" height="16" width="114"></div>
            				</div>
            			</div>
            		</div>
            	</div>
        </div>
<jsp:include page="footer2.jsp"></jsp:include>
<script type="text/javascript">

//从线索详情页进数据列表
function goShuJuList() {  //clueIds2
	var clID = document.getElementById('clueIds2').innerText;
	window.location.href = "<%=basePath %>admin/clueevidencelist.php?clueid="+clID;
}

//科室树形      oEle[i].nextSbiling
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
            		var name = $(this).parent().find(".tree-title").text();
					var names = name + ","; 
            		$("#fenjuNames").append('<span id="'+id+'">'+names+'</span>');
            		uidArr.push(id);
            	}else {
            		if($("#"+id)){
            			$("#"+id).remove();
            			uidArr.remove(id);
            		}
            	}
	   	 	});
        }
    });
}
function onClickCells_ds(field,row){
	
}

window.setTimeout(hidess,1000); 
function hidess() {
	$("#zhuanchu").hide();
	$("#shuxingcaidan").hide();
}

function delFenju() {
	$("#fenjuNames").html("");
	$("#getSubstationInfo").val("");
}


var roleNames = "";
function test0(obj){

	if(obj.checked){
	    $("#zhuanchudan").hide();
	    $("#zhuanchu").hide();
	 }
}

function test1(obj){
	 if(obj.checked){
	    $("#zhuanchudan").show();
	    $("#zhuanchu").hide();
	 }else{
	   	$("#zhuanchudan").hide();
	 }
}

function test2(obj){
	 if(obj.checked){
	    $("#zhuanchudan").show();
	    $("#zhuanchu").hide();
	 }else{
	   	$("#zhuanchudan").hide();
	 }
}

function test3(obj){
	 if(obj.checked){
		 $("#zhuanchudan").show();
	     $("#zhuanchu").show();
	     $("#otherText22").hide();
	 }else{
		 $("#zhuanchudan").hide();
	   	 $("#zhuanchu").hide();
	 }
}

function fenju(obj){
	 if(obj.checked){
		 $("#shuxingcaidan").show();
		 $("#otherText22").hide();
		 
	 }else{
		 $("#otherText22").show();
	 }
}

function other(obj){
	 if(obj.checked){
		 $("#otherText22").show();
		 $("#shuxingcaidan").hide();
	 }else{
		 $("#otherText22").hide();
	 }
}

$('input[name=checkboxs]').click(function(){
	 $(this).attr('checked','checked').siblings().removeAttr('checked');
	});
	
//处理单上传方法
function addEvidence3(obj){	
	$("#importForm4").submit();
}

function addChuzhiAndShenpiInfo(obj) {
	var substation = $("#fenjuNames").text();
    if (substation.trim().length != 0) {
        $("#getSubstationInfo").val(substation);
	}

	var clueIds2 = document.getElementById('clueIds2').innerText;
	
	var chuzhi = $("input[name='radioParam']:checked").val();     //线索处置   四个按钮
//	$("#clueChuzhi").html(chuzhi);
	
	//同意或不同意
	var ifAgrees =$("input[name='ifagrees']:checked").val();
	var fenjuAndOther =$("input[name='fenjuAndOther1']:checked").val();    //转出下      分局和其他

	var departs21 = $("#getSubstationInfo").val();
	var otherText22 = $('#otherText22').val();
	var shenpi = $("#addcomment").val();           			//审批意见
	var filepath = $('#filePathInfo').val();				//处理单路径
	var fileName22 = $("#filePath4").val();   				//处理单截取出来的文件名
//    alert("输入的其他内容###" + otherText22);
	if(chuzhi == "积累归档" || chuzhi == "线索经营" || chuzhi == "线索转出") {
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/clueHandleAndShenpi.php",
			data : {
				"clueIds2" : clueIds2,
				"chuzhi" : chuzhi,								//四个被勾选的   处置类型
				"fenjuAndOther" : fenjuAndOther,				//分局    和      其他
				"departs21" : departs21,                        //分局下   被勾选的分局名称
				"otherText22" : otherText22,					//输入的其他内容
				"filepath" : filepath,							//处理单路径
				"fileName22" : fileName22,						//处理单截取出来的文件名
				"ifAgrees" : ifAgrees,							//同意      不同意
				"shenpi" : shenpi								//审批内容
			},
			dataType : "json",
			async: true,
			success : function(data) {
				if(data.status == "success"){
					addEvidence3(obj);
					alert("提交成功！");
					window.location.href = "<%=basePath %>admin/cluelist.php";
				}
			},
			error : function (data) {
				alert("提交失败！")
			}
		});
		//addEvidence3(obj);
	} else{
		$.ajax({
			type : "POST",
			url : "<%=basePath%>admin/clueHandleAndShenpi.php",
			data : {
				"clueIds2" : clueIds2,
				"chuzhi" : chuzhi,								//四个被勾选的   处置类型
				"fenjuAndOther" : fenjuAndOther,				//分局    和      其他
				"departs21" : departs21,                        //分局下   被勾选的分局名称
				"otherText22" : otherText22,					//输入的其他内容
				"filepath" : filepath,							//处理单路径
				"fileName22" : fileName22,						//处理单截取出来的文件名
				"ifAgrees" : ifAgrees,							//同意      不同意
				"shenpi" : shenpi								//审批内容
			},
			dataType : "json",
			async: true,
			success : function(data) {
				
				alert("提交成功！");
				window.location.href = "<%=basePath %>admin/cluelist.php";
			},
			error : function (data) {
				alert("提交失败！")
			}
		}); 
	}
	
}
function setFilePath4() {
	var filepath = $("#file4").val();
    $("#filePathInfo").val(filepath);
	var fileName22 = filepath.substring(filepath.lastIndexOf("\\")+1);
	$("#filePath4").val(fileName22);  
}

//处理单预览
function lookOnlineOfClue() {
	$("#loadingDiv").show();
	var clueIDss = document.getElementById('clueIds2').innerText;
	var fileNamess22 = $("#filePath4").val();
	$.ajax({
		type : "POST",
		url : "<%=basePath%>admin/lookOnlineOfClue.php",
		data : {
			"clueIDss" : clueIDss,
			"fileNamess22" : fileNamess22
		},
		dataType : "json",
		async: true,
		success : function(data) {
			
			var httpUrls="<%=basePath%>preview/"+data.res;
			window.open(httpUrls,'_blank','');
			$("#loadingDiv").css("display","none");
		},
	});
}

//权限
window.onload = checkShenPiOfSection();
function checkShenPiOfSection() {
	var departmentss1 = document.getElementById('departmentss1').innerText;							//数据库中的  分局   科室   职位
	var sectionss1 = document.getElementById('sectionss1').innerText;
	var roless1 = document.getElementById('roless1').innerText;

	var roleNames = $('#roleNamess').val();
//	alert("roleNames="+roleNames);
	
	var sessionPartmentNames33 = document.getElementById('sessionPartmentNames33').innerText;		//session中的  分局   科室   职位
//	alert("sessionPartmentNames33="+sessionPartmentNames33);
	var sessionSectionNames33 = document.getElementById('sessionSectionNames33').innerText;
//	alert("sessionSectionNames33="+sessionSectionNames33);
	var sessionRoleNames = document.getElementById('sessionRoleNames33').innerText;    //登录时存在session中的职位数字
	//alert("sessionRoleNames="+sessionRoleNames);
	if(departmentss1 == sessionPartmentNames33 && sectionss1 == sessionSectionNames33){
		$("#shenpi").hide();
	}
}

$(function ifRoleNames(){
	$("#otherText22").hide();
	var ifAgreess = document.getElementById('ifAgreess').innerText;
//	$("input[name='ifagrees'][value='" + ifAgreess + "']").prop("checked", "checked");
	
	var clueDisposalS = document.getElementById('clueDisposalS').innerText;

	$("input[name='radioParam'][value='" + clueDisposalS + "']").prop("checked", "checked");	//默认勾选单选框
	
	var fenjuHeQita = document.getElementById('fenjuAngQita').innerText;
	$("input[name='fenjuAndOther1'][value='" + fenjuHeQita + "']").prop("checked", "checked");   //默认勾选单选框
	
	var roleNames = $('#roleNamess').val(); 			//登录人的职位     处长还是科员
	
	var userIDs = document.getElementById('userIDs').innerText;			//数据库存的登录人的ID
	var sessionuserIDs = document.getElementById('sessionRoleID33').innerText;


	if(clueDisposalS == "未处置" || clueDisposalS == "暂存归档" || userIDs == sessionuserIDs || roleNames == "侦查人员"){
		$("#noChuzhi").show();
		$("#haveChuzhi").hide();
		$("#shenpi").hide();
	}else{
		$("#noChuzhi").hide();
		$("#haveChuzhi").show();
		$("#shenpi").show();
	}
	
 	if(clueDisposalS == "积累归档"){
		$("input[name='radioParam']").attr("disabled","disabled"); 
	}else if(clueDisposalS =="线索经营"){
		$("input[name='radioParam']").attr("disabled","disabled"); 
	}else if(clueDisposalS =="线索转出"){
		$("input[name='radioParam']").attr("disabled","disabled");
	}else{
	}
}); 

$(function (){
	var ifAgreess = document.getElementById('ifAgreess').innerText;
	
	
	if(ifAgreess == "同意" ){
//		$("#noChuzhi").hide();
		$("#haveShenpi").show();
		$("#shenpi").hide();
	}else if(ifAgreess == "不同意"){
		$("#noChuzhi").show();
		$("#haveChuzhi").hide();
		
		$("#haveShenpi").show();
		$("#shenpi").hide();
	}
});
</script>
</body>
</html>