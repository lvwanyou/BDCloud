<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link rel="icon" href="<%=basePath %>template/img/icon_16.ico" mce_href="<%=basePath %>template/img/icon_16.ico" type="image/x-icon">
<meta http-equiv="pragma" content="no-cache" />
<title></title>


</head>

<frameset id="fr" name="fr" rows="63,*" cols="*" framespacing="0" frameborder="no" border="0">
	<frame src="<%=basePath %>admin/top.php" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" title="topFrame"/>
	<frameset id="mfr" name="mfr" cols="203,*" framespacing="0" frameborder="no" border="0">
  		<frame src="<%=basePath %>admin/left.php" name="leftFrame" id="leftFrame" title="leftFrame" />
  		<frame src="<%=basePath %>admin/workbench.php" name="mainFrame" id="mainFrame" title="mainFrame" />
	</frameset>
	
</frameset><noframes></noframes>

<body>
</body>
</html>
