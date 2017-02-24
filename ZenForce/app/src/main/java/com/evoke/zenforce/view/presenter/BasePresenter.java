/*
package com.evoke.zenforce.view.presenter;

import android.util.Log;

import com.evoke.zenforce.model.pojo.ChatMessage;

import org.json.JSONObject;

*/
/**
 * Created by spinninti on 2/10/2017.
 *//*

public class BasePresenter {


    private static final String TAG = "BasePresenter";


    public ChatMessage populateMessage(Object message) {

        ChatMessage msgObj = null;
        try {
            JSONObject json = new JSONObject(message.toString());

            String user = json.getString("username");
            String chatMsg = json.getString("message");
            String time = json.getString("time");
            boolean isSelf = json.getBoolean("isSelf");

            Log.d(TAG, "Received msg : " + message.toString());
//            msgObj = new ChatMessage(user, chatMsg, isSelf, time);

        } catch (Exception e) {
            Log.e(TAG, "Exception.......");
            e.printStackTrace();
        }

        return msgObj;
    }
}
*/
