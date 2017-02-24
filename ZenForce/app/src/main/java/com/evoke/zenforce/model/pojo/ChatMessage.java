package com.evoke.zenforce.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChatMessage implements Serializable{

    @SerializedName("username")
    String username;
    @SerializedName("message")
    String message;
//    @SerializedName("isSelf")
//    private int isSelf;

    @SerializedName("time")
    private String time;

    public ChatMessage(String username, String message, String time) {
        this.username = username;
        this.message = message;
//        this.isSelf = isSelf;
        this.time = time;
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
        return time;
    }
}
