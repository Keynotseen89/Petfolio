package com.example.kench.petfolio.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kench.petfolio.data.VaccineContract.VaccineEntry;


/**
 * Created by Quinatzin on 1/9/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper sInstance;

    public static final String LOG_TAB = DbHelper.class.getSimpleName();

    // name of the database
    private static final String DATABASE_NAME = "medical_history.db";

    // version number to upgrade database version
    // each time if you Add, Edit, table, you need to change the version
    private static final int DATABASE_VERSION = 2;

    public static synchronized DbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally lean an Activity's context.
        // See this article for more information: http://bit.1y/6LRzFx
        if (sInstance == null) {
            sInstance = new DbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_PET_TABLE = "CREATE TABLE " + VaccineEntry.TABLE_NAME + " ("
            + VaccineEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VaccineEntry.COLUMN_VACCINE_DATE + " TEXT, "
            + VaccineEntry.COLUMN_VACCINE_INFO + " TEXT, "
            + VaccineEntry.COLUMN_VACCINE_TAG + " TEXT DEFAULT \"N/A\", "
            + VaccineEntry.COLUMN_VACCINE_REDATE + " TEXT); ";

    private static final String SQL_CREATE_MED_TABLE = "CREATE TABLE " + VaccineEntry.MED_TABLE_NAME + " ("
            + VaccineEntry.MED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VaccineEntry.COLUMN_MED_DATE + " TEXT, "
            + VaccineEntry.COLUMN_MED_MEDICATION + " TEXT, "
            + VaccineEntry.COLUMN_MED_DOSAGE + " TEXT); ";

    /**
     * onCreated used to create a data table with given values
     * _id, date_admin, vaccine_info, tag, data_revaccinated
     */
    @Override
    public void onCreate(SQLiteDatabase database) {

        //Create table for Vaccination Log
       /* String SQL_CREATE_PET_TABLE = "CREATE TABLE IF NOT EXISTS " + VaccineEntry.TABLE_NAME + " ("
                + VaccineEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VaccineEntry.COLUMN_VACCINE_DATE + " TEXT, "
                + VaccineEntry.COLUMN_VACCINE_INFO + " TEXT, "
                + VaccineEntry.COLUMN_VACCINE_TAG + " TEXT DEFAULT \"N/A\", "
                + VaccineEntry.COLUMN_VACCINE_REDATE + " TEXT); ";
                */
        //Create table for Medication Log
        /*String SQL_CREATE_MED_TABLE = "CREATE TABLE IF NOT EXISTS " + MedicationEntry.TABLE_NAME + " ("
                + MedicationEntry.MED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MedicationEntry.COLUMN_MED_DATE + " TEXT, "
                + MedicationEntry.COLUMN_MED_MEDICATION + " TEXT, "
                + MedicationEntry.COLUMN_MED_DOSAGE + " TEXT); ";
            */
        //Execute the SQL statement
        database.execSQL(SQL_CREATE_PET_TABLE);
        database.execSQL(SQL_CREATE_MED_TABLE);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.d(LOG_TAB, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));
        if (oldVersion != newVersion) {
            //Drop table if existed, all data will be gone!!
            database.execSQL("DROP TABLE IF EXISTS " + VaccineEntry.TABLE_NAME);
            database.execSQL("DROP TABLE IF EXISTS " + VaccineEntry.MED_TABLE_NAME);
            onCreate(database);
        }
    }
}
