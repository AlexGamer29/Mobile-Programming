package com.midterm.minhduc;

import com.google.gson.annotations.SerializedName;

public class Data {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("desc")
    private String desc;

    @SerializedName("timeStamp")
    private String timeStamp;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lng")
    private String lng;

    @SerializedName("addr")
    private String addr;

    @SerializedName("e")
    private String e;

    @SerializedName("zip")
    private String zip;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Data(int id, String title, String desc, String timeStamp, String lat, String lng, String addr, String e, String zip) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.timeStamp = timeStamp;
        this.lat = lat;
        this.lng = lng;
        this.addr = addr;
        this.e = e;
        this.zip = zip;
    }




}
