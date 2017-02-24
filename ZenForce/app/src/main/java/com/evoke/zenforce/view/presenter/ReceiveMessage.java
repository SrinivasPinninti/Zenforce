package com.evoke.zenforce.view.presenter;

/**
 * Created by spinninti on 2/17/2017.
 */
public class ReceiveMessage {

    private static ReceiveMessage mInstance;

    private ReceiveMessage() {}

    public static ReceiveMessage getInstance() {

        if (mInstance == null) {

            synchronized (ReceiveMessage.class) {

                if (mInstance == null) {

                    mInstance = new ReceiveMessage();
                }
            }
        }

        return mInstance;
    }
}
