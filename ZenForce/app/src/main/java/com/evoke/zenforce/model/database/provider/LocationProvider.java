package com.evoke.zenforce.model.database.provider;

import com.evoke.zenforce.model.database.DbConstants;

public class LocationProvider extends BaseProvider {


    @Override
    protected String getTableName() {
        return DbConstants.PlaceTable.TABLE_NAME;
    }
}
