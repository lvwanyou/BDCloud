<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>     
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<head>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>                                                                                                    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>template/css/login/color_me.css" >
</head>
<html>

<body >
    <div class="b20" style=" text-align:center;padding-top: 17px;padding-bottom:47px;width:100%;bottom:0px;height:84px;">
		<img  src="<%=basePath%>template/img/left.png"><span class="c08" style="font-size:12px;margin: 0 12px;">  由勋立云计算提供技术支持  版本号: V1.1.1085  </span><img src="<%=basePath%>template/img/left2.png">
	</div>
</body>
</html>