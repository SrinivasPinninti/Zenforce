package com.evoke.zenforce.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChatMessage implements Serializable{

    @SerializedName("itemId")
    long itemId;
    @SerializedName("username")
    String username;
    @SerializedName("message")
    String message;
//    @SerializedName("isSelf")
//    private int isSelf;

    @SerializedName("timestamp")
    private String timestamp;

    public ChatMessage(long itemId, String username, String message, String timestamp) {
        this.itemId = itemId;
        this.username = username;
        this.message = message;
//        this.isSelf = isSelf;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    /*public int isSelf() {
        return isSelf;
    }

    public void setSelf(int isSelf) {
        this.isSelf = isSelf;
    }*/

    public String getTime() {
        return timestamp;
    }
}
