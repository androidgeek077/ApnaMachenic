package com.example.splashscreen.Models;

public class userModel {
    String userid;


    String name;
    String contact;
    String imageurl;
    String mailaddress;
    String mechanictype;
    String experienceyears;
    String userlat;
    String userlong;
    String usertype;

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public userModel(String userid, String name, String contact, String imageurl, String mailaddress, String mechanictype, String experienceyears, String userlat, String userlong, String usertype) {
        this.userid = userid;
        this.name = name;
        this.contact = contact;
        this.imageurl = imageurl;
        this.mailaddress = mailaddress;
        this.mechanictype = mechanictype;
        this.experienceyears = experienceyears;
        this.userlat = userlat;
        this.userlong = userlong;
        this.usertype = usertype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    public String getMechanictype() {
        return mechanictype;
    }

    public void setMechanictype(String mechanictype) {
        this.mechanictype = mechanictype;
    }

    public String getExperienceyears() {
        return experienceyears;
    }

    public void setExperienceyears(String experienceyears) {
        this.experienceyears = experienceyears;
    }

    public String getUserlat() {
        return userlat;
    }

    public void setUserlat(String userlat) {
        this.userlat = userlat;
    }

    public String getUserlong() {
        return userlong;
    }

    public void setUserlong(String userlong) {
        this.userlong = userlong;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public userModel() {
    }
}
