package com.example.chat;


import android.graphics.drawable.Drawable;

public class ContentCall {
private String name;
private String partChat;
private Drawable Img;

    public ContentCall(String name, String partChat, Drawable Img) {
        this.name = name;
        this.partChat = partChat;
        this.Img=Img;


    }



    public String getName() {
        return name;
    }

    public String getPartChat() {
        return partChat;
    }

    public Drawable getImg() {
        return Img;
    }
}
