package com.example.kench.petfolio;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kench.petfolio.data.VaccineContract;

/**
 * Created by Quinatzin on 1/13/2018.
 */

public class MedHistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MED_HISTORY_LOADER = 0;

    // Global CursorAdapter
    MedCursorAdapter mCursorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_medication_history, container, false);

        ListView medListView = (ListView) view.findViewById(R.id.list_med);
        // Get emptyView id to populate
        View medEmptyView = view.findViewById(R.id.empty_view_med);
        medListView.setEmptyView(medEmptyView);

        //Setup an a Adapter to create a list item for each row of inventory stock
        mCursorAdapter = new MedCursorAdapter(getActivity(), null);
        medListView.setAdapter(mCursorAdapter);

        medListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MedEditorActivity.class);
                Uri currentMedUri = ContentUris.withAppendedId(VaccineContract.VaccineEntry.MED_CONTENT_URI, id);
                intent.setData(currentMedUri);
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(MED_HISTORY_LOADER, null, this);
        return view;
    }

      @Override
      public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);

         getLoaderManager().initLoader(MED_HISTORY_LOADER, null, this);
      }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                VaccineContract.VaccineEntry.MED_ID,
                VaccineContract.VaccineEntry.COLUMN_MED_DATE,
                VaccineContract.VaccineEntry.COLUMN_MED_MEDICATION
        };

        return new CursorLoader(getActivity(),
                VaccineContract.VaccineEntry.MED_CONTENT_URI,
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
