package com.vehicleTest.model;


import java.util.Date;

/**
 * Created by zyl on 2017/5/10.
 */
public class UpFile {
    private  String username;
    private  String filename;
    private  Date up_time;
    private  String description;
    private  String zhouzhong;
    private  String zhidong;
    private  String result;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getUp_time() {
        return up_time;
    }

    public void setUp_time(Date up_time) {
        this.up_time = up_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZhouzhong() {
        return zhouzhong;
    }

    public void setZhouzhong(String zhouzhong) {
        this.zhouzhong = zhouzhong;
    }

    public String getZhidong() {
        return zhidong;
    }

    public void setZhidong(String zhidong) {
        this.zhidong = zhidong;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
