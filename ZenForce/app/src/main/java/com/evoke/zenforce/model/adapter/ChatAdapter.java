package com.evoke.zenforce.model.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.pojo.ChatMessage;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private Context context;
    private List<ChatMessage> messagesItems;

    public ChatAdapter(Context context, List<ChatMessage> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * The following list not implemented reusable list items as list items
         * are showing incorrect data Add the solution if you have one
         * */

        ChatMessage msg = messagesItems.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if (msg.getUsername().equals("Manager")) {
            // message belongs to you, so load the right aligned layout
            convertView = mInflater.inflate(R.layout.chat_right,  null);
        } else {
            // message belongs to other person, load the left aligned layout
            convertView = mInflater.inflate(R.layout.chat_left, null);
        }

//        TextView lblFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
        TextView  txtMsg        = (TextView) convertView.findViewById(R.id.txtMsg);
        TextView  timeStamp     = (TextView) convertView.findViewById(R.id.timeStamp);
        ImageView imgResult     = (ImageView) convertView.findViewById(R.id.imgResult);

        // Identifying the message sent/failed
       /* if (messagesItems.get(position).getIsSent() == 1) {  // Message Sent Successfully
            imgResult.setImageResource(R.drawable.sent_48);
        } else {  // Failed to send Message
            imgResult.setImageResource(R.drawable.not_sent_48);
        }*/

        txtMsg.setText(msg.getMessage());
        timeStamp.setText(msg.getTime());

        return convertView;

    }

}