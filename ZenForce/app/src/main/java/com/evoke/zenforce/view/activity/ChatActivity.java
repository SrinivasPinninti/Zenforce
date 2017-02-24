package com.evoke.zenforce.view.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.adapter.ChatAdapter;
import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.MessageEntityBean;
import com.evoke.zenforce.model.database.dao.MessageDAO;
import com.evoke.zenforce.model.pojo.ChatMessage;
import com.evoke.zenforce.utility.Constants;
import com.evoke.zenforce.utility.PubnubKeys;
import com.evoke.zenforce.utility.Util;
import com.evoke.zenforce.view.presenter.PublishMessage;
import com.evoke.zenforce.view.service.PubNubService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String TAG = "ChatActivity";

    //    private static final String USER = "RIGHT";
    private static final int RESULT_SPEECH = 200;



    ArrayList<ChatMessage> chatMessageList;
    ChatAdapter chatAdapter;
    private ListView listViewMessages;

    private ImageView uiIVEnter;
    private EditText uiETMessage;
    private FloatingActionButton uiFabSpk;
    private boolean mSend;

    private PublishMessage publishMessage;

    Gson gson;

    private boolean mServiceBound = false;
    private boolean isSelf = false;

    Pubnub pubnub = new Pubnub(PubnubKeys.PUBLISH_KEY, PubnubKeys.SUBSCRIBE_KEY);


    private PubNubService mService = null;


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.d(TAG, "Service bound...");
            PubNubService.ChatServiceBinder chatServiceBinder = (PubNubService.ChatServiceBinder) service;
            mService = chatServiceBinder.getService();
            mServiceBound = true;
            mService.background();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Service unBound..");
            mServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle bundle = getIntent().getExtras();
        initUI();
        gson = new Gson();

    }

    private void initUI() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Chat");
        listViewMessages = (ListView) findViewById(R.id.list_view_messages);
        uiIVEnter = (ImageView) findViewById(R.id.uiIVEnter);
        uiETMessage = (EditText) findViewById(R.id.epUiETNewNotes);
        uiFabSpk = (FloatingActionButton) findViewById(R.id.uiFabSpk);
        uiFabSpk.setOnClickListener(this);
        uiIVEnter.setOnClickListener(this);
        uiETMessage.addTextChangedListener(this);

        chatMessageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatMessageList);
        listViewMessages.setAdapter(chatAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PubNubService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

        Log.d(TAG, " history CHANNEL_NAME : " + PubnubKeys.CHANNEL_NAME);

        pubnub.history(PubnubKeys.CHANNEL_NAME, 10, false, new Callback() {
            @Override
            public void successCallback(String channel, Object message) {
                super.successCallback(channel, message);
                Log.d(TAG, "Chat history successCallback... " + message.toString());
                populateHistory(message);
            }

            @Override
            public void errorCallback(String channel, PubnubError error) {
                super.errorCallback(channel, error);
                Log.e(TAG, "Chat history errorCallback... " + error.toString());
            }
        });

    }


    private void populateHistory(Object message) {

        ArrayList<ChatMessage> chatMessages= (ArrayList<ChatMessage>) gson.fromJson(message.toString(),
                new TypeToken<ArrayList<ChatMessage>>() {}.getType());

        chatMessageList.addAll(chatMessages);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, " update chat history list from UI thread....");
                chatAdapter.notifyDataSetChanged();
            }
        });

    }


    public void populateMessage(ChatMessage chatMessages) {
        Log.d(TAG, " populateMessage messages.....");
//            JSONObject json = new JSONObject(message.toString());
//            Log.d(TAG, "Received msg : " + message.toString());

            chatMessageList.add(chatMessages);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, " update chat history list from UI thread....");
                    chatAdapter.notifyDataSetChanged();
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(chatReceiver, new IntentFilter(Constants.BROADCAST_ACTION_RECEIVED_MSG));
//        registerReceiver(offlineMsgReceiver, new IntentFilter(Constants.BROADCAST_ACTION_OFFLINE_MSG));
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(chatReceiver);
//        unregisterReceiver(offlineMsgReceiver);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBound) {
            // If a timer is active, foreground the service, otherwise kill the service
            if (mService.isChatServiceRunning()) {
                Log.e(TAG, "start foreground....");
                mService.foreground();
            } else {
                Log.e(TAG, "stop Service....");
                stopService(new Intent(this, PubNubService.class));
            }
            // Unbind the service
            unbindService(connection);
            mServiceBound = false;
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.uiFabSpk:
                Log.d(TAG, "Send......" + mSend);
                if (mSend) {
                    Log.d(TAG, "Send......");
                    String message = uiETMessage.getText().toString();
                    uiETMessage.setText("");

                    if (message != null && !message.isEmpty()) {

                        ChatMessage chatMessage = new ChatMessage(Util.USER, message, Util.getCurrentTime());
                        String chatMsg = gson.toJson(chatMessage);
                        JSONObject jsonMessage = null;
                        try {
                            jsonMessage = new JSONObject(chatMsg);
                        } catch (JSONException je) {
                            Log.e(TAG, je.toString());
                        }
                        uiETMessage.setText("");

                        Log.d(TAG, " publish CHANNEL_NAME : " + PubnubKeys.CHANNEL_NAME);

                        pubnub.publish(PubnubKeys.CHANNEL_NAME, jsonMessage, new Callback() {
                            @Override
                            public void successCallback(String channel, Object message) {
                                super.successCallback(channel, message);
                                Log.d("successCallback", "## success : " + message.toString());
//                                isSelf = true;
//                                ChatMessage chatMessage = gson.fromJson(message.toString(), ChatMessage.class);


//                                Type listType = new TypeToken<List<ChatMessage>>() { }.getType();
//                                List<ChatMessage> chatMessages = new Gson().fromJson(message.toString(), listType);
//                                populateMessage(chatMessages);
                            }

                            @Override
                            public void errorCallback(String channel, PubnubError error) {
                                super.errorCallback(channel, error);
                                Log.e("errorCallback", "## error : " + error.toString());
                            }

                        });
                    }
                } else {
                    // Speak
                    Log.d(TAG, "uiIVSpeak....");
                    Intent speakIntent = new Intent(
                            RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    speakIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                    try {
                        startActivityForResult(speakIntent, RESULT_SPEECH);
                        uiETMessage.setText("");
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult requestCode : " + requestCode);

        switch (requestCode) {
            case RESULT_SPEECH:
                if (resultCode == Activity.RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (uiETMessage != null) uiETMessage.setText(text.get(0));
                }
                break;
        }

    }


    public BroadcastReceiver chatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, " chatReceiver invoked.....");

            if (intent != null) {
                Bundle bundle = intent.getExtras();
                ChatMessage chat = (ChatMessage) bundle.get("ChatMessage");
                Log.d(TAG, " populateMessage chat list.....");
                populateMessage(chat);

            }
        }
    };


   /* public BroadcastReceiver offlineMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, " offlineMsgReceiver invoked.....");

            if (intent != null) {
                Bundle bundle = intent.getExtras();
                List<MessageEntityBean> offline_messages = bundle.getParcelableArrayList("Offline_messages");
                for(MessageEntityBean msg : offline_messages) {
                    Log.d(TAG, " populate offline messages.....");
                    populateMessage(msg);
                    updateMessageStatus(msg);
                }
            }
        }
    };*/


    private void updateMessageStatus(MessageEntityBean msg) {

        Log.d(TAG, " updateMessageStatus offline messages.....");

        MessageDAO dao = MessageDAO.getSingletonInstance(this);

        String selection = DbConstants.MessageTable.COLUMN_IS_SENT + " =?";
        String[] selectionArgs = new String[] { "0" };

        msg.setIsSent(1);
        dao.update(msg, selection, selectionArgs);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            mSend = false;
            uiFabSpk.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_microphone));
        } else {
            mSend = true;
            uiFabSpk.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_send));
        }

    }


   /* private void updateChatList(MessageEntityBean bean) {
        Log.d(TAG, "updateChatList......");
        chatMessageList.add(bean);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, " update chat history list from UI thread....");
                chatAdapter.notifyDataSetChanged();
            }
        });
    }*/
}
