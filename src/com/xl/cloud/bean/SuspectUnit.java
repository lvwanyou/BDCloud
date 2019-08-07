package com.xl.cloud.bean;

/**
 * 线索登记的邮箱Bean
 *
 * @author YangChen
 * @create 2017-11-07 16:23
 */

public class SuspectUnit {
    private int id = -1;
    private String name;
    private String address;
    private String customsRegistrationNumber;
    private String threadID;

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomsRegistrationNumber() {
        return customsRegistrationNumber;
    }

    public void setCustomsRegistrationNumber(String customsRegistrationNumber) {
        this.customsRegistrationNumber = customsRegistrationNumber;
    }
}
