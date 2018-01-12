package com.example.kench.petfolio.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kench.petfolio.data.VaccineContract.VaccineEntry;

/**
 * Created by Quinatzin on 1/9/2018.
 */

public class VaccineDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAB = VaccineDbHelper.class.getSimpleName();

    // name of the database
    private static final String DATABASE_NAME = "medical_history.db";

    // version of the database
    private static final int DATABASE_VERSION = 1;

    public VaccineDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreated used to create a data table with given values
     * _id, date_admin, vaccine_info, tag, data_revaccinated
     */
    @Override
    public void onCreate(SQLiteDatabase database) {

         String SQL_CREATE_PET_TABLE = "CREATE TABLE " + VaccineEntry.TABLE_NAME + " ("
                + VaccineEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VaccineEntry.COLUMN_VACCINE_DATE + " TEXT, "
                + VaccineEntry.COLUMN_VACCINE_INFO + " TEXT, "
                + VaccineEntry.COLUMN_VACCINE_TAG + " TEXT DEFAULT \"N/A\", "
                + VaccineEntry.COLUMN_VACCINE_REDATE + " TEXT); ";

         //Execute the SQL statement
        database.execSQL(SQL_CREATE_PET_TABLE);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //The database is still at version 1, so there's nothing to be done here yet.
    }
}
