package com.example.kench.petfolio;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.KeyListener;
import android.view.Menu;
import android.widget.EditText;

import com.example.kench.petfolio.data.MedicationContract.MedicationEntry;

/**
 * Created by kench on 1/13/2018.
 */

public class MedEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_MED_LOADER = 2;

    private EditText mDateMedText;
    private EditText mMedicationText;
    private EditText mDossageText;

    //Content URI for the existing inventory item
    private Uri mCurrentMedUri;

    private KeyListener mDateListener, mMedListener, mDossageListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_med);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mCurrentMedUri = intent.getData();

        if (mCurrentMedUri != null) {
            getLoaderManager().initLoader(EXISTING_MED_LOADER, null, this);
        }
        setTitle("Medication Info");

        mDateMedText = (EditText) findViewById(R.id.date_med_id);
        mMedicationText = (EditText) findViewById(R.id.medication_id);
        mDossageText = (EditText) findViewById(R.id.dossage_id);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu options from the res/menu/menu_menu.xml item
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        // TODO code for button press back
        super.onBackPressed();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MedicationEntry.MED_ID,
                MedicationEntry.COLUMN_MED_DATE,
                MedicationEntry.COLUMN_MED_MEDICATION,
                MedicationEntry.COLUMN_MED_DOSAGE
        };

        return new CursorLoader(this,
                mCurrentMedUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            //Find the index of the each ite in the rows
            int dateColumnIndex = data.getColumnIndex(MedicationEntry.COLUMN_MED_DATE);
            int medicationColumnIndex = data.getColumnIndex(MedicationEntry.COLUMN_MED_MEDICATION);
            int dossageColumnIndex = data.getColumnIndex(MedicationEntry.COLUMN_MED_DOSAGE);

            //Extract the data
            String date = data.getString(dateColumnIndex);
            String medicationInfo = data.getString(medicationColumnIndex);
            String dossage = data.getString(dossageColumnIndex);

            //Set TextField
            mDateMedText.setText(date);
            mMedicationText.setText(medicationInfo);
            mDateMedText.setText(dossage);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDateMedText.setText("");
        mMedicationText.setText("");
        mDossageText.setText("");
    }
}
