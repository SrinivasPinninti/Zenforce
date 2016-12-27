
package com.evoke.zenforce.model.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by spinninti on 11/19/2016.
 */

public final class ZenForceContract {

    public static final String CONTENT_AUTHORITY = "com.evoke.zenforce";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PLACE = "place";
    public static final String SELECTION_ID_BASED = BaseColumns._ID + " = ? ";

    private ZenForceContract() {
    }

    public static final class Place implements BaseColumns {


        /**
         * The content URI for this table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PLACE);
        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + CONTENT_AUTHORITY + "/" + PATH_PLACE;
        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + CONTENT_AUTHORITY + "/" + PATH_PLACE;
        /**
         * A projection of all columns in the items table.
         */
//        public static final String[] PROJECTION_ALL = {_ID, DbSchema.COLUMN_NAME, DbSchema.COLUMN_ADDRESS, DbSchema.COLUMN_PLACE_ID};
        /**
         * The default sort order for queries containing NAME fields.
         */
//        public static final String SORT_ORDER_DEFAULT = DbSchema.COLUMN_NAME + " ASC";


    }

    public static final class Visit implements BaseColumns {


    }
}

