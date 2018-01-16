package com.example.kench.petfolio;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kench.petfolio.data.MedicationContract.MedicationEntry;

/**
 * Created by Quinatzin on 1/13/2018.
 */

public class MedHistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MED_HISTORY_LOADER = 1;

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

        //getLoaderManager().initLoader(0, null, this);

        return view;
    }

  /*  @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       getLoaderManager().initLoader(0, null, this);
    }
*/
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MedicationEntry.MED_ID,
                MedicationEntry.COLUMN_MED_DATE,
                MedicationEntry.COLUMN_MED_MEDICATION
        };

        return new CursorLoader(getActivity(),
                MedicationEntry.MED_CONTENT_URI,
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
