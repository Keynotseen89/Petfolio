package com.example.kench.petfolio;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kench.petfolio.data.VaccineContract;
import com.example.kench.petfolio.data.VaccineContract.VaccineEntry;

/**
 * Created by Quinatzin on 1/12/2018.
 */

public class VaccineHistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int VACCINE_LOADER = 0;

    // Global CursorAdapter
    VaccineCursorAdapter mCursorAdapter;
    //ListView petListView;
    //View emptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_vaccine_history, container, false);

        ListView petListView = (ListView) view.findViewById(R.id.list);
        // Get emptyView id to populate
        View emptyView = view.findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        //Setup an a Adapter to create a list item for each row of inventory stock
        mCursorAdapter = new VaccineCursorAdapter(getActivity(), null);
        petListView.setAdapter(mCursorAdapter);

        /*FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VaccineEditorActivity.class);
                startActivity(intent);
            }
        });*/

        //Setup the item click listener
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), VaccineEditorActivity.class);
                Uri currentVaccineUri = ContentUris.withAppendedId(VaccineEntry.CONTENT_URI, id);
                intent.setData(currentVaccineUri);
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User click on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.insert_dummy:
                Log.v("VaccineHistoryFragment.class", "Insert");
                insertVaccineLog();
                return true;
            case R.id.delete_all:
                deleteAll();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /**
     * Helper method to insert dummy data into database
     */
    /*private void insertVaccineLog() {
        //Create a ContentValue object where column names are key
        ContentValues contentValues = new ContentValues();
        contentValues.put(VaccineEntry.COLUMN_VACCINE_DATE, "09-29-2016");
        contentValues.put(VaccineEntry.COLUMN_VACCINE_INFO, "DHLPP/C 1");
        contentValues.put(VaccineEntry.COLUMN_VACCINE_TAG, "N/A");
        contentValues.put(VaccineEntry.COLUMN_VACCINE_REDATE, "10-13-2016");

        //Uri newUri = (getContext().getContentResolver().insert(VaccineEntry.CONTENT_URI, contentValues));
        Uri newUri = getActivity().getContentResolver().insert(VaccineEntry.CONTENT_URI, contentValues);

    }*/

    /*private void deleteAll() {
        int rowsDeleted = (getContext().getContentResolver().delete(VaccineEntry.CONTENT_URI, null, null));
        Log.v(" MedHistoryActivity ", rowsDeleted + " rows deleted from vaccineLog database");
    }*/
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                VaccineContract.VaccineEntry._ID,
                VaccineContract.VaccineEntry.COLUMN_VACCINE_DATE,
                VaccineContract.VaccineEntry.COLUMN_VACCINE_INFO
        };

        return new CursorLoader(getActivity(),
                VaccineContract.VaccineEntry.CONTENT_URI,
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
