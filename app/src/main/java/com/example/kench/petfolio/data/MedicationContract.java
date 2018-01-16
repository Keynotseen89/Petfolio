package com.example.kench.petfolio.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Quinatzin on 1/13/2018.
 */

public final class MedicationContract {

    // Empty constructor
    private MedicationContract(){}

    // constructor authority used for URI
    public static final String MED_CONTENT_AUTHORITY = "com.example.kench.petfolio";

    public static final Uri MED_BASE_CONTENT_URI = Uri.parse("content://" + MED_CONTENT_AUTHORITY);

    // name of the database table path
    public static final String PATH_MEDICATION = "medicationLog";

    public static final class MedicationEntry implements BaseColumns{
        /**
         * The content URI to access the vaccine data in the provider
         */
        public static final Uri MED_CONTENT_URI = Uri.withAppendedPath(MED_BASE_CONTENT_URI, PATH_MEDICATION);



        public static final String TABLE_NAME = "medicationLog";

        public static final String MED_CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + MED_CONTENT_AUTHORITY + "/" + PATH_MEDICATION;

        public static final String MED_CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + MED_CONTENT_AUTHORITY + "/" + PATH_MEDICATION;

        public static final String MED_ID = BaseColumns._ID;
        public static final String COLUMN_MED_DATE = "date";
        public static final String COLUMN_MED_MEDICATION = "medication";
        public static final String COLUMN_MED_DOSAGE = "dosage";
    }
}
