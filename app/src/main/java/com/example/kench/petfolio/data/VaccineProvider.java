package com.example.kench.petfolio.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.kench.petfolio.data.VaccineContract.VaccineEntry;

/**
 * Created by Quinatzin Sintora on 1/9/2018.
 */

public class VaccineProvider extends ContentProvider {
    /**
     * URI matcher code for the content URI for the vaccineLog table
     */
    private static final int VACCINE_LOG = 200;

    /**
     * URI matcher code for the content URI for a single item vaccineLog table
     */
    private static final int VACCINE_ID = 201;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // Used to obtain all the data in the table
        sUriMatcher.addURI(VaccineContract.CONTENT_AUTHORITY, VaccineContract.PATH_VACCINE, VACCINE_LOG);
        // Used to obtain a single data in the table
        sUriMatcher.addURI(VaccineContract.CONTENT_AUTHORITY, VaccineContract.PATH_VACCINE + "/#", VACCINE_ID);
    }

    /**
     * Database helper object
     */
    private VaccineDbHelper mDbHelper;

    /**
     * Tag for the log message
     */
    public static final String LOG_TAG = VaccineProvider.class.getSimpleName();


    /**
     * Initialize the provider and the database helper object
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new VaccineDbHelper(getContext());
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
            case VACCINE_LOG:
                cursor = database.query(VaccineEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case VACCINE_ID:
                selection = VaccineEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(VaccineEntry.TABLE_NAME,
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

    /**
     * Return the MIME type of the data for the content URI
     */
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VACCINE_LOG:
                return VaccineEntry.CONTENT_LIST_TYPE;
            case VACCINE_ID:
                return VaccineEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VACCINE_LOG:
                return insertVaccineInfo(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertVaccineInfo(Uri uri, ContentValues contentValues) {
        //Get Write able database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        //Insert the new item with given values
        long id = database.insert(VaccineEntry.TABLE_NAME, null, contentValues);
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
            case VACCINE_LOG:
                rowsDeleted = database.delete(VaccineEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case VACCINE_ID:
                selection = VaccineEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(VaccineEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not support for " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
