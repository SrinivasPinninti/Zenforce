package com.evoke.zenforce.model.database.provider;

import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants;

/**
 * Created by spinninti on 11/23/2016.
 */
public class VisitProvider extends BaseProvider {
    private static final String TAG = "VisitProvider";

    @Override
    public String getTableName() {
        Log.v(TAG, " table name : " +DbConstants.VisitTable.TABLE_NAME);
        return DbConstants.VisitTable.TABLE_NAME;
    }

}
