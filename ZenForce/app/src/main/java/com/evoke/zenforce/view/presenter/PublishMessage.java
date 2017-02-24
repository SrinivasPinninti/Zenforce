package com.evoke.zenforce.view.presenter;

import android.util.Log;

import com.evoke.zenforce.model.database.beanentity.MessageEntityBean;
import com.evoke.zenforce.model.database.dao.MessageDAO;
import com.evoke.zenforce.utility.PubnubKeys;
import com.evoke.zenforce.utility.Util;
import com.evoke.zenforce.view.application.ZenForceApplication;
import com.google.gson.Gson;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by spinninti on 2/17/2017.
 */
public class PublishMessage {


    private static final String TAG = "PublishMessage";

    Pubnub pubnub = new Pubnub(PubnubKeys.PUBLISH_KEY, PubnubKeys.SUBSCRIBE_KEY);

    Gson gson = new Gson();

    public interface IPublishListener {

        public void onResultCallback(MessageEntityBean bean);
    }

    private IPublishListener callback;


    public PublishMessage(IPublishListener callback) {
        this.callback = callback;
    }




    public void publish(final MessageEntityBean bean) {

        Log.d(TAG, " publish message called....");

        String chatMsg = gson.toJson(bean);
        JSONObject jsonMessage = null;
        try {
            jsonMessage = new JSONObject(chatMsg);
        } catch (JSONException je) {
            Log.e(TAG, je.toString());
        }

        pubnub.publish(PubnubKeys.CHANNEL_NAME, jsonMessage, new Callback() {
            @Override
            public void successCallback(String channel, Object message) {
                super.successCallback(channel, message);
                Log.d("successCallback", "$$ message :  " + message.toString());
                MessageEntityBean bean = gson.fromJson(message.toString(), MessageEntityBean.class);
                MessageDAO dao = MessageDAO.getSingletonInstance(ZenForceApplication.getInstance());
                dao.insert(bean);
                updateMessageStatus(bean);
                Log.d(TAG, " isSent Val : " + bean.getIsSent());
                callback.onResultCallback(bean);
            }

            @Override
            public void errorCallback(String channel, PubnubError error) {
                super.errorCallback(channel, error);
                Log.e("errorCallback", "## error : " + error);
                Log.e(TAG, " isSent Val : " + bean.getIsSent());
                bean.setIsSent(0);
                bean.setTime(Util.getCurrentTime());
                MessageDAO dao = MessageDAO.getSingletonInstance(ZenForceApplication.getInstance());
                dao.insert(bean);
                callback.onResultCallback(bean);
            }
        });

    }


   /* public void publishOffline(List<MessageEntityBean> offline_messages) {
        Log.d(TAG, " publishOffline invoked.....");
        for(MessageEntityBean msg : offline_messages) {
            Log.d(TAG, " publish offline messages....." + msg.getMessage());
            publish(msg);
        }
    }*/



    private void updateMessageStatus(MessageEntityBean msg) {

        Log.d(TAG, " updateMessageStatus offline messages.....");

        MessageDAO dao = MessageDAO.getSingletonInstance(ZenForceApplication.getInstance());

//        String selection = DbConstants.MessageTable.COLUMN_IS_SENT + " =?";
//        String[] selectionArgs = new String[] { "0" };

        msg.setIsSent(1);
        dao.update(msg, null, null);

    }

}
