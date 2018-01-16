package com.example.kench.petfolio.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kench.petfolio.data.MedicationContract.MedicationEntry;

/**
 * Created by Quinatzin on 1/13/2018.
 */

public class MedicationProvider extends ContentProvider {

    private static final int MEDICATION_LOG = 400;

    private static final int MEDICATION_ID = 401;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MedicationContract.MED_CONTENT_AUTHORITY, MedicationContract.PATH_MEDICATION, MEDICATION_LOG);
        sUriMatcher.addURI(MedicationContract.MED_CONTENT_AUTHORITY, MedicationContract.PATH_MEDICATION + "/#", MEDICATION_ID);
    }

    private DbHelper mDbHelper;

    public static final String LOG_TAG = MedicationProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }


    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // Object of cursor to be used
        Cursor cursor;

        // Uri assigned to matcher to use with switcher statement
        int match = sUriMatcher.match(uri);

        switch (match) {
            case MEDICATION_LOG:
                cursor = database.query(MedicationEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MEDICATION_ID:
                selection = MedicationEntry.MED_ID + " =?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(MedicationEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can not query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MEDICATION_LOG:
                return insertMedicationInfo(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertMedicationInfo(Uri uri, ContentValues contentValues) {
        // Get write able database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // insert the new item with given values
        long id = database.insert(MedicationEntry.TABLE_NAME, null, contentValues);

        // If the ID is -1, then insertion failed. Log an error and return null
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert new row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //Get write able database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        //Track the number of rows that were deleted
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MEDICATION_LOG:
                rowsDeleted = database.delete(MedicationEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MEDICATION_ID:
                selection = MedicationEntry.MED_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(MedicationEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not support for " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MEDICATION_LOG:
                return updateMedication(uri, contentValues, selection, selectionArgs);
            case MEDICATION_ID:
                selection = MedicationEntry.MED_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateMedication(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Updated is not supported for " + uri);
        }
    }

    private int updateMedication(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        if (contentValues.containsKey(MedicationEntry.COLUMN_MED_DATE)) {
            String dataValue = contentValues.getAsString(MedicationEntry.COLUMN_MED_DATE);
            if (dataValue == null) {
                throw new IllegalArgumentException("Date is required");
            }
        }
        if (contentValues.size() == 0) {
            return 0;
        }

        // Otherwise, get write able database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the row update on the database and get the number of rows affected
        int rowsUpdated = database.update(MedicationEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        // if 1 or more rows were updated, notify all the listener
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    /**
     * Return the MIME type of the data for the content URI
     */
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MEDICATION_LOG:
                return MedicationEntry.MED_CONTENT_LIST_TYPE;
            case MEDICATION_ID:
                return MedicationEntry.MED_CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }
}
