package com.example.chat;

import android.net.Uri;

public class MessageContent {
    private String message;
    private String SenderID;
    private String MessageID;
    private String image;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MessageContent() {
    }

    public MessageContent(String message, String senderID, String messageID,String type,String image) {
        this.message = message;
        SenderID = senderID;
        MessageID = messageID;
        this.type=type;
    this.image=image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderID() {
        return SenderID;
    }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public String getMessageID() {
        return MessageID;
    }

    public void setMessageID(String messageID) {
        MessageID = messageID;
    }
}