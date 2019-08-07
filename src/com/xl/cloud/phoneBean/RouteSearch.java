package com.xl.cloud.phoneBean;

/**
 * 腾讯地图 搜索路线对象
 * @author hongyongda
 *
 */
public class RouteSearch {
    //路线ID
    private String routeId;
    
    //路线类型
    private String routeType;
    
    //起点名称
    private String startName;
    
    //起点位置
    private String startAdd;
    
    //起点经度
    private String startLongitude;
    
    //起点纬度
    private String startLatitude;
    
    //终点名称
    private String endName;
    
    //终点地址
    private String endAdd;
    
    //终点经度
    private String endLongitude;
    
    //终点纬度
    private String endLatitude;
    
    //md5加密
    private String md5;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getStartAdd() {
        return startAdd;
    }

    public void setStartAdd(String startAdd) {
        this.startAdd = startAdd;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public String getEndAdd() {
        return endAdd;
    }

    public void setEndAdd(String endAdd) {
        this.endAdd = endAdd;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
    
    @Override
    public String toString(){
        return "RouteSearch [routeId=" + routeId 
                + ", routeType=" + routeType
                + ", startName=" + startName
                + ", startAdd=" + startAdd
                + ", startLongitude=" + startLongitude
                + ", startLatitude=" + startLatitude
                + ", endName=" + endName
                + ", endAdd=" + endAdd
                + ", endLongitude=" + endLongitude
                + ", endLatitude=" + endLatitude
                + ", md5=" + md5 + "]";
    }
    
}