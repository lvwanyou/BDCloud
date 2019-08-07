/**
*
*
*
*
*Waterfall flow layout 
*/
var _wfl = {
    //配置参数
   
    $iBlock : $("#aa"),//瀑布流包裹层 jq对象
    wfl_all_width: 0, //画布总宽
    wfl_count:4, //列数
     wfl_times: 0,
    wfl_padding: 19,//内容之间的间隔 纵向和横向
    wfl_fomat:'<div id="%id%" class="js-wfl-elem proImg absolute" style="opacity:0">'
                  +'   <a href="%href%" class="bg"></a>'
                  +'   <img onload="loadcomplete()" src="%imgurl%" >'
                  +'   <div class="desc">'
                  +'     <h5>%title%</h5>'
                  +'     <p>%logoname%</p>'
                  +'     <p>%size%</p>'
                  +'     <p>%des%</p>'
                  +'   </div>'
                  +' </div>', //元素的HTML模板
    
    wfl_width:0,  //单内容块的宽度
    
    
    cacuData :[],//计算位置的数据记录

       
    idFirstName: "wfl",
    lastId : 0, //动态加载的ID
  
    //获取最初内容对应列上的总高度
    initData: function (data){
      _wfl.$iBlock = $("#aa");// ！ 临时解决加载不出的问题
      _wfl.wfl_all_width =  _wfl.$iBlock.width();
      _wfl.wfl_width =(_wfl.wfl_all_width - ( _wfl.wfl_padding * (_wfl.wfl_count-1) )) / _wfl.wfl_count;  //单内容块的宽度

      var limit = _wfl.wfl_count;
      var i = 0;
      
      if (_wfl.$iBlock.find(".js-wfl-elem ").length == 0){
         
          for(var i =0 ; i< _wfl.wfl_count; i++){
              _wfl.cacuData[_wfl.cacuData.length] = {"colum": "c"+i, "left": ( ( _wfl.wfl_width +  _wfl.wfl_padding ) *  i) , "height":0} ;   //保存单行个列的高度
               limit --
          }
        
      }else{
      
          $.each( _wfl.$iBlock.find(".js-wfl-elem "), function () {
            
            if (limit <= 0) {
              $(this).remove();//第一次加载最多仅限一行 也就是wfl_count的设置值，多余部分删除
              
            }else{        
            
              _wfl.cacuData[_wfl.cacuData.length] = {"colum": "c"+i, "left": ( ( _wfl.wfl_width +  _wfl.wfl_padding ) *  i) , "height": ($(this).height() + _wfl.wfl_padding)} ;   //保存单行个列的高度

            }
             i ++;
             limit --;
          });      
          
      }
      if(data){
         this.createHtml(data);
     }
     
    },
    
    
    sortListByName :function (list ,  cname ){     
      var i,j,k,temp,n = list.length;      
      var list = list;
      for( i = 0; i < n; i++){        
        for( j = 0; j < n; j++ ){
           
            if( list[i][cname] < list[j][cname]  ){
              temp = list[i];
              list[i] = list[j];
              list[j] = temp;   
            }
        }
      }     
    },
    
    
    
    /**
    * @method
    * @param  {array} list 二维数组 当前加载的单行列元素列表
    */
    setColSite : function (list) {
     
      var ilist = list;
      var oblist= [];
      var len = ilist.length > _wfl.wfl_count ? _wfl.wfl_count : ilist.length;
     
      for (var i = 0; i <  len; i++){     //从多个数据中取出限定的列个数进行比较   
         oblist[oblist.length] = ilist.shift();         
      }
      
      var objArr =[];
      for (var j = 0; j < oblist.length; j++) { //将取出的数据整理成有对应高度的数组进行排序
        objArr[objArr.length] = {"id":oblist[j], "height":$("#"+oblist[j]) .height()};        
        
      }
    
      this.sortListByName(_wfl.cacuData, "height");//高度排序
     
      this.sortListByName(objArr, "height");//高度排序  
      
      var k = len-1;
      var maxHeight = 0,hIndex,curElem;
      for( i = 0; i <  len; i++ ){
        curElem =  $("#" + objArr[i].id);
        curElem.css({left: _wfl.cacuData[k].left + "px", top: _wfl.cacuData[k].height + "px",opacity:1});   

       
        _wfl.cacuData[k].height += ( objArr[i].height + _wfl.wfl_padding );
         
        if ( _wfl.cacuData[k].height > maxHeight) {
          maxHeight = _wfl.cacuData[k].height;
          hIndex = k;
        }
        
        k--;
      }
     
      _wfl.$iBlock.css("height",  _wfl.cacuData[hIndex].height + "px")
      
      
      //规定一行判断一次
      if ( ilist.length > 0) {
        _wfl.setColSite( ilist );        
      }
    },
    
    
    
     createHtml : function (jsonList){
       
       var beginId = _wfl.lastId + 1; //建立ID名       
       var loadLen = jsonList.length;
       var idlist = [];
       
       var temStr = "";
       

      // if(loadLen == _wfl.wfl_count){
       
             for(var i = 0; i < loadLen; i++ ){
               jsonList[i]["id"] = _wfl.idFirstName +"_"+beginId;         
               idlist.push(_wfl.idFirstName +"_"+beginId);
               
               beginId ++ ;
               
               temStr += _wfl.htmlFormat( _wfl.wfl_fomat, jsonList[i] );
             }
             
             _wfl.lastId = beginId;
             _wfl.wfl_times = loadLen;
           
             _wfl.$iBlock.append(temStr);    
            
              
              var myInterval = setInterval(function(){
                  if (_wfl.wfl_times == 0){
                      clearInterval(myInterval);
                      _wfl.setColSite( idlist );
                  } 
              },1)        
        //}else{
            
          
        //}

     },
     
     
     
      /**
     * 说明：新增字符串format方法
     * 用法. var resultStr = htmlFormat(拥有占位符的字符串,{key:value,key:value....key:value});
     * @methed strFormat
     * @param  {string} str  第一个是具有占位符的字符串，后面的都是加入到对应占位符的阐述
     * @param  {json} obj json格式对象
     * @return {string} 拼接完成的字符串
     */
      htmlFormat:function(str, obj){
        if(obj == undefined || obj == null) {
          return str;
        }			
        var s = str;			
        $.each(obj, function(i) {
          s = s.replace(new RegExp("\\%" + i + "\\%","g"), obj[i]);
        });   
        return s;
      }
     
}


function loadcomplete(){    
    _wfl.wfl_times --;
}



var demoJson = {"data":[
   {"imgurl":"images/por1.png", "href":" ", "title":"PVC膜材1", "logoname":"品牌:SCHUCO 旭格1", "size":"尺寸规格：160mm厚" , "des":"技术规格：1电动通风百叶帘可以有效抵抗阳光对室内的辐射热能"}
  ,{"imgurl":"images/pro2.png", "href":" ", "title":"PVC膜材2", "logoname":"品牌:SCHUCO 旭格2", "size":"尺寸规格：260mm厚" , "des":"技术规格：2电动通风百叶帘可以有效抵抗阳光对室内的辐射热能"}
  ,{"imgurl":"images/pro3.png", "href":" ", "title":"PVC膜材3", "logoname":"品牌:SCHUCO 旭格3", "size":"尺寸规格：360mm厚" , "des":"技术规格：3电动通风百叶帘可以有效抵抗阳光对室内的辐射热能"}
  ,{"imgurl":"images/pro2.png", "href":" ", "title":"PVC膜材2", "logoname":"品牌:SCHUCO 旭格2", "size":"尺寸规格：260mm厚" , "des":"技术规格：2电动通风百叶帘可以有效抵抗阳光对室内的辐射热能"}
  ,{"imgurl":"images/pro3.png", "href":" ", "title":"PVC膜材1", "logoname":"品牌:SCHUCO 旭格1", "size":"尺寸规格：160mm厚" , "des":"技术规格：1电动通风百叶帘可以有效抵抗阳光对室内的辐射热能"}
  ,{"imgurl":"images/pro2.png", "href":" ", "title":"PVC膜材2", "logoname":"品牌:SCHUCO 旭格2", "size":"尺寸规格：260mm厚" , "des":"技术规格：2电动通风百叶帘可以有效抵抗阳光对室内的辐射热能"}
  ,{"imgurl":"images/por1.png", "href":" ", "title":"PVC膜材3", "logoname":"品牌:SCHUCO 旭格3", "size":"尺寸规格：360mm厚" , "des":"技术规格：3电动通风百叶帘可以有效抵抗阳光对室内的辐射热能"}
 ]};

var demoJson02 = {"data":[
   {"imgurl":"images/por1.png", "href":" ", "title":"PVC膜材1", "logoname":"品牌:SCHUCO 旭格1", "size":"尺寸规格：160mm厚" , "des":"技术规格：1电动通风百叶帘可以有效抵抗阳光对室内的辐射热能"}
  ,{"imgurl":"images/pro2.png", "href":" ", "title":"PVC膜材2", "logoname":"品牌:SCHUCO 旭格2", "size":"尺寸规格：260mm厚" , "des":"技术规格：2电动通风百叶帘可以有效抵抗阳光对室内的辐射热能"}
  ,{"imgurl":"images/pro3.png", "href":" ", "title":"PVC膜材3", "logoname":"品牌:SCHUCO 旭格3", "size":"尺寸规格：360mm厚" , "des":"技术规格：3电动通风百叶帘可以有效抵抗阳光对室内的辐射热能"}

 ]};

/*
  _wfl.initData();
  $("#addImg").click(function(){
     _wfl.createHtml(demoJson.data);
  })
*/







