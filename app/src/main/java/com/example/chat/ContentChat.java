package com.example.chat;


import java.io.Serializable;

public class ContentChat  implements Serializable {
private  String name;
private  String Img;
private  String time;
private String uid;
private String search;

public ContentChat(){}

    public ContentChat(String name, String img, String time, String uid, String search) {
        this.name = name;
        Img = img;
        this.time = time;
        this.uid = uid;
        this.search = search;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
