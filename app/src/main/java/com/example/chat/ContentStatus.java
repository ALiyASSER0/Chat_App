package com.example.chat;

import android.graphics.drawable.Drawable;

import java.io.Serializable;


public class ContentStatus implements Serializable {
private String name;
private String partChat;
private String img;
    public ContentStatus(){}
    public ContentStatus(String name, String partChat, String img) {
        this.name = name;
        this.partChat = partChat;
        this.img=img;


    }



    public String getName() {
        return name;
    }

    public String getPartChat() {
        return partChat;
    }

    public String getImg() {
        return img;
    }
}
