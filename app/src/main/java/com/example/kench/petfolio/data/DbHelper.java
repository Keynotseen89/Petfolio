package com.example.kench.petfolio.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kench.petfolio.data.VaccineContract.VaccineEntry;
import com.example.kench.petfolio.data.MedicationContract.MedicationEntry;

import java.security.MessageDigest;

/**
 * Created by Quinatzin on 1/9/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAB = DbHelper.class.getSimpleName();

    // name of the database
    private static final String DATABASE_NAME = "medical_history.db";

    // version number to upgrade database version
    // each time if you Add, Edit, table, you need to change the version
    private static final int DATABASE_VERSION = 2;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreated used to create a data table with given values
     * _id, date_admin, vaccine_info, tag, data_revaccinated
     */
    @Override
    public void onCreate(SQLiteDatabase database) {

        //Create table for Vaccination Log
         final String SQL_CREATE_PET_TABLE = "CREATE TABLE " + VaccineEntry.TABLE_NAME + " ("
                + VaccineEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VaccineEntry.COLUMN_VACCINE_DATE + " TEXT, "
                + VaccineEntry.COLUMN_VACCINE_INFO + " TEXT, "
                + VaccineEntry.COLUMN_VACCINE_TAG + " TEXT DEFAULT \"N/A\", "
                + VaccineEntry.COLUMN_VACCINE_REDATE + " TEXT); ";

         //Create table for Medication Log
         final String SQL_CREATE_MED_TABLE = "CREATE TABLE " + MedicationEntry.TABLE_NAME + " ("
                 + MedicationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + MedicationEntry.COLUMN_MED_DATE + " TEXT, "
                 + MedicationEntry.COLUMN_MED_MEDICATION + " TEXT, "
                 + MedicationEntry.COLUMN_MED_DOSAGE + " TEXT); ";

         //Execute the SQL statement
        database.execSQL(SQL_CREATE_PET_TABLE);
        database.execSQL(SQL_CREATE_MED_TABLE);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.d(LOG_TAB, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        //Drop table if existed, all data will be gone!!
        database.execSQL("DROP TABLE IF EXISTS " + VaccineEntry.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + MedicationEntry.TABLE_NAME);
        onCreate(database);
    }
}
