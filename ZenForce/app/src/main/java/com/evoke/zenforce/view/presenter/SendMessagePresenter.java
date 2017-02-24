/*
package com.evoke.zenforce.view.presenter;

import android.util.Log;

import com.evoke.zenforce.model.pojo.ChatMessage;
import com.evoke.zenforce.utility.PubnubKeys;
import com.evoke.zenforce.utility.Util;
import com.google.gson.Gson;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

import org.json.JSONException;
import org.json.JSONObject;

*/
/**
 * Created by spinninti on 2/10/2017.
 *//*

public class SendMessagePresenter extends BasePresenter{

    private static final String TAG = "SendMessage";
    Pubnub pubnub = new Pubnub(PubnubKeys.PUBLISH_KEY, PubnubKeys.SUBSCRIBE_KEY);

    private  Gson gson;


    public interface IMessageCallback {

        void onSuccessCallback(ChatMessage chatMessage);
        void onErrorCallback(String errorMsg);
    }

    public SendMessagePresenter() {
        gson = new Gson();

    }

    public void sendMessage(String message, final IMessageCallback callback) {

        message = gson.toJson(new ChatMessage(Util.USER, message, true, Util.getCurrentTime()));

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(message);
        } catch (JSONException je) {
            Log.e(TAG, je.toString());
        }

        if (jsonObj == null) {
            Log.e(TAG, " error in chat message object..");
            return;
        }




        pubnub.publish(PubnubKeys.CHANNEL_NAME, jsonObj, new Callback() {

            @Override
            public void successCallback(String channel, Object message, String timetoken) {
                super.successCallback(channel, message, timetoken);
            }

            @Override
            public void successCallback(String channel, Object message) {
                super.successCallback(channel, message);
                Log.d("successCallback", "message " + message.toString());

                callback.onSuccessCallback(populateMessage(message));

            }

            @Override
            public void errorCallback(String channel, PubnubError error) {
                super.errorCallback(channel, error);
                callback.onErrorCallback(error.toString());
            }
        });
    }

}
*/
