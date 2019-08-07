<%@ page language="java" contentType="text/html;  charset=utf-8"
    pageEncoding="utf-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>Insert title here</title>
	<style type="text/css">
  body, html,#map_demo, #tab, #mapfrm {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
  #menu{height:100%;overflow-y:auto}
  td{font-size:14px}
  h4{margin:0;}
  #map_demo1,#map_demo2{height: 50%}
  </style>
<!--离线地图加载  -->
<script type="text/javascript" src="<%=basePath%>template/baidu_map/baidumapv2/baidumap_offline_v2_load.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>template/baidu_map/baidumapv2/css/baidu_map_v2.css"/>

<!--离线地图加载——end  -->
</head>
<body>
<div id="map_demo"></div>
</body>
<script type="text/javascript">  
//百度地图API功能
var map = new BMap.Map("map_demo");
var point = new BMap.Point(116.404, 39.915);
map.centerAndZoom(point, 8);
var marker = new BMap.Marker(point);  // 创建标注
map.addOverlay(marker);               // 将标注添加到地图中
marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
</script>
</html>