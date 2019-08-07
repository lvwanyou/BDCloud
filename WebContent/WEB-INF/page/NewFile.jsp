<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<style type="text/css">
body, html {
	width: 100%;
	height: 100%;
	margin: 0;
	font-family: "微软雅黑";
}

#allmap {
	width: 100%;
	height: 500px;
}

p {
	margin-left: 5px;
	font-size: 14px;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&amp;ak=GZ1stTvaAFWc9w6HVSM4bEYEsbNhBv0t"></script>
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>template/js/jquery/jquery-2.0.3.min.js"></script>
<script
	src="<%=basePath%>template/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=basePath%>template/bootstrap-dist/js/bootstrap.min.js"></script>
<script src="<%=basePath%>template/js/shengshiqu/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>template/js/shengshiqu/area.js"></script>
<script type="text/javascript"
	src="<%=basePath%>template/js/shengshiqu/location.js"></script>
<script src="<%=basePath%>template/js/shengshiqu/select2.js"></script>
<script
	src="<%=basePath%>template/js/shengshiqu/select2_locale_zh-CN.js"></script>
<script src="<%=basePath%>template/js/cutover/js.js"></script>

<title>显示全景控件</title>
</head>

<script type="text/javascript" src="35ff706fd57d11c141cdefcd58d6562b.js"
	charset="gb2312"></script>
<body>


	<div id="allmap"></div>
	<div style="width: 1024px; margin: 0 auto;">
		<span style="color: #48a5f4; font-size: 20px">查询地址demo:&nbsp;</span>
	</div>
	<div
		style="width: 1024px; margin: auto; margin-top: 10px; margin-left: 500px; margin-bottom: 10px;">
		要查询的地址：<input id="text_" type="text" value="潮州市新桥东路"> <input
			id="button_" type="button" value="查询"
			style="margin-left: 50px; margin-right: 100px;"> 查询结果(经纬度)：<input
			id="result_" type="text">
	</div>
	<div style="width: 1024px; margin: 0 auto;">
		<span style="color: #48a5f4; font-size: 20px;">全景控件点击demo:&nbsp;</span>
	</div>

	<div id="result" style="margin: auto;">
		<button id="setPanoramaByLocation"
			style="margin-left: 500px; margin-top: 10px;">按经纬度展示全景</button>
		<button id="getPanoramaLabels"
			style="margin-left: 500px; margin-top: 10px;">显示后台查询数据</button>
		<div id=" poi" style="margin-left: 500px;">
			POI类型显示: : <select id="setPanoramaPOIType"
				style="size: 20px; margin-top: 10px;" onchange="test.poiType(this)">
				<option value="0">无(隐藏所有POI)</option>
				<option value="1">酒店</option>
				<option value="2">餐饮</option>
				<option value="3">电影院</option>
				<option value="4">公交站点</option>
				<option value="5">室内景点</option>
			</select>
		</div>
	</div>

	<div style="width: 1024px; margin: 0 auto; margin-top: 10px;">
		<span style="color: #48a5f4; font-size: 20px; margin-top: 10px;">拖动地图查看位置、视角变化demo:&nbsp;</span>
	</div>
	<div
		style="width: 1024px; margin: auto; margin-top: 10px; margin-left: 500px; margin-bottom: 10px;">
		<div id="position"></div>
		<div id="version"></div>
	</div>

</body>
<script type="text/javascript">
	window.onload = initMap();
	window.onload = initPanorama();
	var map;
	var panorama;

	/* 初始化地图类 */
	function initMap() {
		map = new BMap.Map('allmap');
		map.centerAndZoom("潮州", 12); //根据城市名设置地图中心点 
		//	map.centerAndZoom(new BMap.Point(120.305456, 31.570037), 12);
		map.enableScrollWheelZoom(true);
		map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用

		map.addControl(new BMap.NavigationControl()); //添加默认缩放平移控件
		map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件

		/*叠加全景图层*/
		map.addTileLayer(new BMap.PanoramaCoverageLayer());

		/*构造全景控件*/
		var stCtrl = new BMap.PanoramaControl();
		stCtrl.setOffset(new BMap.Size(20, 20));
		map.addControl(stCtrl);//添加全景控件  
	}
	/* 初始化全景类 */
	function initPanorama() {
		panorama = new BMap.Panorama('allmap');
		
		panorama.setPov({
			heading : -40,
			pitch : 6
		});
		
		panorama.setOptions({
			indoorSceneSwitchControl : true
		//配置全景室内景切换控件显示
		});

		/*  根据全景图事件得到事件所在的经纬度，得到经纬度后无法判断该经纬度四周的用户的labels;
		此时可根据回调函数，对该用户所处的经纬度和对应的水平视角的角度显示对应的label;this is just a conceive */
		panorama.addEventListener('position_changed', function(e) { //全景位置改变事件
			panoramaCallBack(e);
		});
		panorama.addEventListener('pov_changed', function(e) { //全景视角改变事件
			panoramaCallBack(e);
		});
	}

	//显示label内容
	document.getElementById("getPanoramaLabels").onclick = function() {

		SearchGHDB();
	}
	
	//根据地址查经纬度
	document.getElementById("button_").onclick = function() {
			var keyword = document.getElementById("text_").value;
			searchByStationName(keyword);
	}
	
	//根据经纬度进入全景:: 每次进入会初始化全景类
	document.getElementById("setPanoramaByLocation").onclick = function() {
		initPanorama();
		var lng = document.getElementById("result_").value.split(",")[0];
		var lat = document.getElementById("result_").value.split(",")[1];
		if (typeof (lng) == "undefined" || typeof (lat) == "undefined") {
			alert("请先进行查询地址操作！");
			return;
		}
		panorama.setPosition(new BMap.Point(lng, lat)); //根据经纬度坐标展示全景图	
		//showLabel(lng, lat);
		panorama.setOptions({// 打开“退出全景”按钮
			closeControl : true
		});
	};

	/* 根据经纬度显示Label */
	function showLabel(lng, lat,labelInfo) {
		var labelPosition = new BMap.Point(lng, lat);
		var labelOptions = {
			position : labelPosition,
			altitude : 5
		};//设置标注点的经纬度位置和高度
		
		var content="地址："+labelInfo.addr+"\n"+"产权人姓名: "+labelInfo.name+"\n"+"身份证号: "+labelInfo.identify_num
		+"\n"+"面积: "+labelInfo.area+"\n"+"产权证号: "+labelInfo.property_num+"\n"+"土地年税额: "+labelInfo.annual_tax
		+"\n"+"房产年租金: "+labelInfo.rent+"\n"+"房产税: "+labelInfo.rent_tax+"\n"+"个所: "+labelInfo.personal_income
		+"\n"+"城建教育: "+labelInfo.education_pay+"\n"+"印花: "+labelInfo.stamp_tax+"\n"+"各税合计: "+labelInfo.sum;
		var label = new BMap.PanoramaLabel(content, labelOptions);
		panorama.addOverlay(label);//在全景地图里添加该标注
		panorama.setPov(label.getPov()); //修改点的视角，朝向该label

		label.addEventListener('click', function() { //给标注点注册点击事件
			panorama.setPov({ //修改点的视角
				pitch : 0,
				heading : 0
			});
		});
	}

	function panoramaCallBack(e) { //事件回调函数
		if (e.type == 'onpov_changed') {
			document.getElementById('version').innerHTML = "全景视角为：<br/>"
					+ "水平视角：" + panorama.getPov().heading + "<br/>垂直视角："
					+ panorama.getPov().pitch;
		} else if (e.type == 'onposition_changed') {
			document.getElementById('position').innerHTML = "全景位置点为："
					+ panorama.getPosition().lng + ","
					+ panorama.getPosition().lat;			
			/* 动态生成对应的labels */
				//for 
		// 	initPanorama();		//初始化panorama
			showLabelsWithThreshold(poi.point.lng,poi.point.lat,labelInfos);
		} else {
			location.reload();
		}
	}

	/* 设置全景内的展示的POI类型  */
	document.getElementById("setPanoramaPOIType").onchange = function() {
		var selectValue = this.value;
		switch (selectValue) {
		case '0':
			panorama.setPanoramaPOIType(BMAP_PANORAMA_POI_NONE); //无(隐藏所有POI)
			break;
		case '1':
			panorama.setPanoramaPOIType(BMAP_PANORAMA_POI_HOTEL); //酒店
			panorama.setPov({
				pitch : 6,
				heading : 138
			}); //手动参数，场景内已有该室内景，旋转后可见，现调整角度到该POI点的位置，方便开发者可见
			break;
		case '2':
			panorama.setPanoramaPOIType(BMAP_PANORAMA_POI_CATERING); //餐饮
			panorama.setPov({
				pitch : 6,
				heading : 138
			});
			break;
		case '3':
			panorama.setPanoramaPOIType(BMAP_PANORAMA_POI_MOVIE); //电影院
			panorama.setPov({
				pitch : 6,
				heading : 138
			});
			break;
		case '4':
			panorama.setPanoramaPOIType(BMAP_PANORAMA_POI_TRANSIT); //公交站点，该场景（id为0100220000130902152251545J3）附近无站点POI
			break;
		case '5':
			panorama.setPanoramaPOIType(BMAP_PANORAMA_POI_INDOOR_SCENE); //室内景点
			panorama.setPov({
				pitch : 18.960000000000015,
				heading : 350.16741512558605
			});
			break;
		default:
			//无
		}
	};

	function SearchGHDB(){
		map.clearOverlays();//清空原来的标注
		$.ajax({
			type : "POST",
			url : "<%=basePath%>panoramaSearch.php",
			data : {},
			dataType : "json",
			async : true,
			success : function(data) {
				var items = data.panoramaLabels;
				$.each(items, function(i, item) {
					searchByStationNameForLabels(item.addr,item);
				});
				setLabelInfos(items);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.status);
				alert(textStatus);
				alert(errorThrown);
				alert("获取标签内容失败");
			}
		});
	}

	/* 根据地址查询对应的经纬度   */
	var localSearch = new BMap.LocalSearch(map);
	localSearch.enableAutoViewport(); //允许自动调节窗体大小
	
	function searchByStationName(keyword) {
		map.clearOverlays();//清空原来的标注
		localSearch.setSearchCompleteCallback(function(searchResult) {
			var poi = searchResult.getPoi(0);
			document.getElementById("result_").value = poi.point.lng + ","
			+ poi.point.lat;
			map.centerAndZoom(poi.point, 13);
			var marker = new BMap.Marker(new BMap.Point(poi.point.lng,
					poi.point.lat)); // 创建标注，为要查询的地方对应的经纬度
			map.addOverlay(marker);
			var content = document.getElementById("text_").value
					+ "<br/><br/>经度：" + poi.point.lng + "<br/>纬度："
					+ poi.point.lat;
			var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>"
					+ content + "</p>");
			marker.addEventListener("click", function() {
				this.openInfoWindow(infoWindow);
			});
			marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		});
		localSearch.search(keyword);
	}
		
	var addrs=new Array();
	var lngs=new Array();
	var lats=new Array();
	var index=0;   // labels 的当前下标
	/*  查询地址得到label 并且进行显示  */
	function searchByStationNameForLabels(keyword) {
	//	map.clearOverlays();//清空原来的标注
			addrs.push(keyword);
		localSearch.search(keyword);
	}
	
	/* 设置Labels的信息展示  ，由于callBack  */
	function setLabelInfos(labelInfos){
		localSearch.setSearchCompleteCallback(function(searchResult) {
			var poi = searchResult.getPoi(0);
			//alert(addrs[index]+"::   "+poi.point.lng+"    ---   "+poi.point.lat);
		 		
			map.centerAndZoom(poi.point, 16); 
			var marker = new BMap.Marker(new BMap.Point(poi.point.lng,
					poi.point.lat)); // 创建标注，为要查询的地方对应的经纬度					
			map.addOverlay(marker);
			lngs.push( poi.point.lng);
			lats.push( poi.point.lat);
			var content =addrs[index]
					+ "<br/>经度：" + poi.point.lng + "<br/>纬度："
					+ poi.point.lat;
		    showLabel(poi.point.lng,poi.point.lat,labelInfos[index]);   //将得到的label进行显示
			var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>"
					+ content + "</p>");
			marker.addEventListener("click", function() {
				this.openInfoWindow(infoWindow);
			   // alert(poi.point.lng+"    ---   "+poi.point.lat+"   :  "+labelInfos.length);	
				
				//alert(poi.point.lng+"    ---   "+poi.point.lat);
		
				/* 此处应该做过滤判断 ，  将当前位置加临界阈值内的label 过滤并显示 */
			/* 	for(var i=0;i<labelInfos.length;i++){
					 showLabel(lngs[i],lats[i],labelInfos[i]);				
				} */
			 	initPanorama();		//初始化panorama
				showLabelsWithThreshold(poi.point.lng,poi.point.lat,labelInfos);
	 			panorama.setPosition(new BMap.Point(poi.point.lng,poi.point.lat)); //根据经纬度坐标展示全景图	
				panorama.setOptions({// 打开“退出全景”按钮
		 			closeControl : true
				}); 
			});
			marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
			index++;
		});
	
		var threshold_lng=0.000050;
		var threshold_lat=0.000050;
		function showLabelsWithThreshold(lngPresent,latPresent,labelInfos){
			for(var i=0;i<labelInfos.length;i++){			
			    if(Math.abs(lngs[i]-lngPresent)<threshold_lng && Math.abs(lats[i]-latPresent)<threshold_lat  ){
			    	alert(lngs[i]+"   --- -    "+lngPresent);
			    	showLabel(lngs[i], lats[i],labelInfos[i]);	    	
			    }
			}
		}
	}
</script>

</html>