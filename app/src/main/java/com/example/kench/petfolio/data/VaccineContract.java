package com.example.kench.petfolio.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Quinatzin on 1/9/2018.
 */

public final class VaccineContract {

    // Empty constructor
    private VaccineContract() {}

    // content authority  used for URI
    public static final String CONTENT_AUTHORITY = "com.example.kench.petfolio";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // name of the database table path
    public static final String PATH_VACCINE = "vaccineLog";

    public static final String PATH_MEDICATION = "medicationLog";

    public static final class VaccineEntry implements BaseColumns {
        /**
         * The content URI to access the vaccine data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VACCINE);

        public static final Uri MED_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEDICATION);
        /**
         * The name of the database table
         */
        public static final String TABLE_NAME = "vaccineLog";

        public static final String MED_TABLE_NAME = "medicationLog";
        /**
         * The MIMI type of the {@link #CONTENT_URI} for a
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VACCINE;

        public static final String MED_CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDICATION;
        /**
         *  The MIME type of the {@link #CONTENT_URI} for a single vaccine log
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VACCINE;

        public static final String MED_CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDICATION;

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_VACCINE_DATE = "date_admin";
        public static final String COLUMN_VACCINE_INFO = "vaccine_info";
        public static final String COLUMN_VACCINE_TAG = "tag";
        public static final String COLUMN_VACCINE_REDATE = "date_revaccinated";

        public static final String MED_ID = BaseColumns._ID;
        public static final String COLUMN_MED_DATE = "date";
        public static final String COLUMN_MED_MEDICATION = "medication";
        public static final String COLUMN_MED_DOSAGE = "dosage";
    }
}
