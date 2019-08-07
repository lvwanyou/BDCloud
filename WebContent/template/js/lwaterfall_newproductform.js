/*jquery版 瀑布流效果
 * author  LEE ,  www.cnlee8.com
 * 调用方式：$(".picList").Lwaterfall({url:"imgbox.js"});
 * 效果采用AJAX 调用JSON格式数据
 * creat 2012.5.25 
 * 默认要有初始数据
 */
var  loadFinish=false,start_num=0;
(function($) {
	$.fn.Lwaterfall = function(options) {
		var $obj = $(this);
		var settings = {
			colWidth : 245,
			indexImage : 0,
			colName : "col",
			colSelector : "single",
			start : 0,
			loadFinish : false,
			url : "",
			imgLoading : "images/loading.gif",
			pageno : 20 
			 
		};
		var opts = $.extend({}, settings, options);
	 
	    $(".page-loading").html("");//首次加载时清空提示
		 
		creatCol();
		getSource();
		var scrollTimer2 = null;
		window.onscroll = function() {
			clearTimeout(scrollTimer2);
			scrollTimer2 = setTimeout(onScroll, 300)
		};
		function colNum() {
			var colNumber = Math.floor($obj.innerWidth() / opts.colWidth);
			if (colNumber < 1) {
				colNumber = 1
			}
			return colNumber
		}
		function creatCol() {
			var colHtml = "";
			for ( var i = 0; i < colNum(); i++) {
				colHtml = colHtml + '<div class="' + opts.colName
						+ '" style="width:' + opts.colWidth + 'px" ></div>'
			}
			$obj.append(colHtml);
			$("." + opts.colName).filter(":last").addClass("last");
			return $("." + opts.colName, $obj)
		}
		function shortCol() {
			var min = $("." + opts.colName).eq(0).outerHeight(), min_key = 0;
			$("." + opts.colName).each(function(i) {
				var oHeight = $(this).outerHeight();
				if (oHeight < min) {
					min = $(this).outerHeight();
					min_key = i
				}
			});
			return min_key
		}
		function getSource() {
			$("." + opts.colSelector).each(function(i) {
				var colIndex = Math.floor(i % colNum());
				$(this).remove().appendTo($("." + opts.colName).eq(colIndex))
			})
		}
		function getElements() {
//			alert( $('#newproductForm').serialize());
			$(".page-loading").html("<img src='" + opts.imgLoading+ "'   >  \u6570\u636e\u52a0\u8f7d\u4e2d...");
			
			$.ajax({
						url : opts.url,
						type : "post",
						data : $('#newproductForm').serialize(),
						dataType : "JSON",
						cache : false,
						success : function(data) {
							var t = eval(data);
							//alert(data+"+"+opts.start);
							if(t!=null&&t!=''&&t!='null'){
								var dataLen = t.length;
								var html = "";
								if (!loadFinish) {
									
									for ( var ii = 0; ii < 10; ii++) {
									 
										if (start_num < dataLen) {
											
											html = '<div class="'
													+ opts.colSelector
													+ '" style="display:none;"  ><a href="'
													+ data[start_num].imgHref
													+ '" target=_blank><img src="'
													+ data[start_num].imgUrl
													+ '" alt="'
													+ data[start_num].imgTitle
													+ '"></a><a href="'
													+ data[start_num].imgHref
													+ '" target=_blank><h2>'
													+ data[start_num].imgTitle
													+ "</h2></a><p>"
													+ data[start_num].imgContent
													+ "</p></div>";
											$("." + opts.colName).eq(shortCol()).append(html);
											start_num += 1;
											$("." + opts.colSelector).fadeIn(500);
										} else {
											
											$(".page-loading")
													.html(
															"\u6570\u636e\u5df2\u52a0\u8f7d\u5b8c\u6bd5\uff01");
											loadFinish = true;
										}
									}
								}
							} else {
								$(".page-loading")
								.html(
										"\u6570\u636e\u5df2\u52a0\u8f7d\u5b8c\u6bd5\uff01");
								loadFinish = true;
							}
							
						},
						error : function(data) {
							$(".page-loading")
									.html(
											"\u6570\u636e\u52a0\u8f7d\u5931\u8d25\uff0c\u8bf7\u91cd\u65b0\u52a0\u8f7d\uff01");
               					loadFinish = false;
						}
					})
		}
		var _scrollTimer = null;
		function onScroll() {
			
			clearTimeout(_scrollTimer);
			_scrollTimer = setTimeout(
					function() {
						var $lowestCol = $("." + opts.colName).eq(shortCol());
						var colBottom = $lowestCol.offset().top + $lowestCol.outerHeight();
						var scrollTop = $(document).scrollTop() || 0;
						var wHeight = $(window).height() || 0;
						//alert(loadFinish);
						//alert(scrollTop - colBottom + wHeight);
						if (scrollTop - colBottom + wHeight >= 150 && !loadFinish) {
							 
							getElements();
						}
					}, 300);
		}
		function insert(ele) {
			opts.start += 1;
			var colIndex = shortCol();
			$("." + opts.colName).eq(colIndex).append(ele);
		}
		function onResize() {
			$("." + opts.colName).remove();
			creatCol();
			window.location.reload();
		}
	};
})(jQuery);

































