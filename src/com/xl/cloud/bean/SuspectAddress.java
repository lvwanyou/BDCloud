package com.xl.cloud.bean;

/**
 * 线索登记的作案地区信息
 *
 * @author YangChen
 * @create 2017-11-07 16:48
 */

public class SuspectAddress {
    private int id = -1;
    private String province;
    private String city;
    private String town;
    private String region;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
