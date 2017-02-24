package com.evoke.zenforce.view.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ChatIntentService extends IntentService {

    private static final String TAG = "ChatIntentService";

    public ChatIntentService() {
        super("ChatIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "onHandleIntent......");
        /*if (intent != null) {

            MessageDAO dao = MessageDAO.getSingletonInstance(this);
            String selection = DbConstants.MessageTable.COLUMN_IS_SENT + " = ?";
            String[] selectionArgs = new String[] { "0" }; // not sent
            Cursor cursor = dao.query(null, selection, selectionArgs, null);

            ArrayList<MessageEntityBean> msgList = new ArrayList<>();

            if (cursor != null && cursor.moveToFirst()) {

                do {

                    MessageEntityBean bean = (MessageEntityBean) dao.populate(cursor);
                    bean.setIsSent(1);
                    bean.setTime(Util.getCurrentTime());
                    msgList.add(bean);

                } while (cursor.moveToNext());
            }


            Intent intent1 = new Intent(Constants.BROADCAST_ACTION_OFFLINE_MSG);
            intent1.putParcelableArrayListExtra("Offline_messages", msgList);
//            intent1.setAction(Constants.BROADCAST_ACTION_OFFLINE_MSG);

//            Log.d(TAG, " Offline_messages sendBroadcast....");
//            LocalBroadcastManager.getInstance(ChatIntentService.this).sendBroadcast(intent1);

        }*/
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
