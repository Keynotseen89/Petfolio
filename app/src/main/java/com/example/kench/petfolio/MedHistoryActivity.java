package com.example.kench.petfolio;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;


import com.example.kench.petfolio.data.VaccineContract.VaccineEntry;

public class MedHistoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int VACCINE_LOADER = 0;

    // Global CursorAdapter
    VaccineCursorAdapter mCursorAdapter;

    //Floating action button
    FloatingActionButton fab, fab_med, fab_med_history;

    //Animation for floating action button
    Animation fabOpen, fabClose, fabRotateClockwise, fabRanticlockwise;

    //Check if its open
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_history);
        setTitle("Vaccine Log");
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Setup floating Action Button
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab_med = (FloatingActionButton) findViewById(R.id.floating_med);
        fab_med_history = (FloatingActionButton) findViewById(R.id.floating_med_history);

        //Setup animation
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRanticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {

                    fab_med.startAnimation(fabClose);
                    fab_med_history.startAnimation(fabClose);
                    fab.startAnimation(fabRanticlockwise);
                    fab_med.setClickable(false);
                    fab_med_history.setClickable(false);
                    isOpen = false;
                } else {
                    fab_med.startAnimation(fabOpen);
                    fab_med_history.startAnimation(fabOpen);
                    fab.startAnimation(fabRotateClockwise);
                    fab_med.setClickable(true);
                    fab_med_history.setClickable(true);
                    isOpen = true;
                }
            }
        });*/
        /*fab_med_history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MedHistoryActivity.this, VaccineEditorActivity.class);
                startActivity(intent);
            }
        });*/
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MedHistoryActivity.this, VaccineEditorActivity.class);
                startActivity(intent);
            }
        });
        // Get ListView id populate
        ListView petListView = (ListView) findViewById(R.id.list);

        // Get emptyView id to populate
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        //Setup an a Adapter to create a list item for each row of inventory stock
        mCursorAdapter = new VaccineCursorAdapter(this, null);
        petListView.setAdapter(mCursorAdapter);

        //Setup the item click listener
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(MedHistoryActivity.this, VaccineEditorActivity.class);
                Uri currentVaccineUri = ContentUris.withAppendedId(VaccineEntry.CONTENT_URI, id);
                intent.setData(currentVaccineUri);
                startActivity(intent);
            }
        });
        // Kick start eh loader to display on ListView
        getLoaderManager().initLoader(VACCINE_LOADER, null, this);

    }//end of onCreate code

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_vaccine.xml item
        getMenuInflater().inflate(R.menu.menu_vaccine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User click on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.insert_dummy:
                insertVaccineLog();
                return true;
            case R.id.delete_all:
                deleteAll();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Helper method to insert dummy data into database
     */
    private void insertVaccineLog() {
        //Create a ContentValue object where column names are key
        ContentValues contentValues = new ContentValues();
        contentValues.put(VaccineEntry.COLUMN_VACCINE_DATE, "09-29-2016");
        contentValues.put(VaccineEntry.COLUMN_VACCINE_INFO, "DHLPP/C 1");
        contentValues.put(VaccineEntry.COLUMN_VACCINE_TAG, "N/A");
        contentValues.put(VaccineEntry.COLUMN_VACCINE_REDATE, "10-13-2016");

        Uri newUri = getContentResolver().insert(VaccineEntry.CONTENT_URI, contentValues);
    }

    private void deleteAll() {
        int rowsDeleted = getContentResolver().delete(VaccineEntry.CONTENT_URI, null, null);
        Log.v(" MedHistoryActivity ", rowsDeleted + " rows deleted from vaccineLog database");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                VaccineEntry._ID,
                VaccineEntry.COLUMN_VACCINE_DATE,
                VaccineEntry.COLUMN_VACCINE_INFO
        };

        return new CursorLoader(this,
                VaccineEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

}
