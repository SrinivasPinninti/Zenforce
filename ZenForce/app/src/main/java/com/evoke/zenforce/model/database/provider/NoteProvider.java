package com.evoke.zenforce.model.database.provider;

import com.evoke.zenforce.model.database.DbConstants;

/**
 * Created by spinninti on 11/29/2016.
 */
public class NoteProvider extends BaseProvider{
    @Override
    protected String getTableName() {
        return DbConstants.NoteTable.TABLE_NAME;
    }
}