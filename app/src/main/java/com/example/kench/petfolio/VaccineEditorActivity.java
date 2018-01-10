package com.example.kench.petfolio;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.widget.EditText;

import com.example.kench.petfolio.data.VaccineContract.VaccineEntry;

/**
 * Created by Quinatzin on 1/9/2018.
 */

public class VaccineEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Identifier for the inventory data loader
     */
    private static final int EXISTING_VACCINE_LOADER = 1;

    // EditText to read Date Text
    private EditText mDateText;

    // EditText to read Vaccine Infor
    private EditText mVaccineInfoText;

    // EditText to read Tag
    private EditText mTagNumber;

    // EditText to read Revaccineted Date
    private EditText mRevaccinatedText;

    // Content URI for the existing inventory item
    private Uri mCurrentVaccineUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_editor);

        Intent intent = getIntent();
        mCurrentVaccineUri = intent.getData();

        if (mCurrentVaccineUri != null) {
            getLoaderManager().initLoader(EXISTING_VACCINE_LOADER, null, this);
        }
        setTitle("Vaccine Date");
        //Final all the relevant views that we will need to read user input from
        mDateText = (EditText) findViewById(R.id.date_admin_id);
        mVaccineInfoText = (EditText) findViewById(R.id.vaccin_info_id);
        mTagNumber = (EditText) findViewById(R.id.tag_id);
        mRevaccinatedText = (EditText) findViewById(R.id.revaccinated_id);
    }

    /**
     * used to populate menu with menu_vaccine.xml
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_menu.xml item
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * used to selection action of menu button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User click on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_edit:
                 editData();
                return true;
            case R.id.action_save:
                 insertDate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        // If this is an existing item hide the save button item.
        if(mCurrentVaccineUri != null){
            MenuItem menuItem = menu.findItem(R.id.action_save);
            menuItem.setVisible(false);
        }
        return true;
    }

    private void insertDate() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String dateString = mDateText.getText().toString().trim();
        String vaccineInforString = mVaccineInfoText.getText().toString().trim();
        String tagNumberString = mTagNumber.getText().toString().trim();
        String redateSTring = mRevaccinatedText.getText().toString().trim();

        // Check if this is supposed to be anew item
        // and check if all the fields in the editor are blank
        if(mCurrentVaccineUri == null &&
                TextUtils.isEmpty(dateString) && TextUtils.isEmpty(vaccineInforString) &&
                TextUtils.isEmpty(tagNumberString) && TextUtils.isEmpty(redateSTring)){
            return;
        }

        //Create a ContentValue object where column names are the key
        ContentValues values = new ContentValues();
        values.put(VaccineEntry.COLUMN_VACCINE_DATE, dateString);
        values.put(VaccineEntry.COLUMN_VACCINE_INFO, vaccineInforString);
        values.put(VaccineEntry.COLUMN_VACCINE_TAG, tagNumberString);
        values.put(VaccineEntry.COLUMN_VACCINE_REDATE, redateSTring);

        if(mCurrentVaccineUri == null){
            Uri newUri = getContentResolver().insert(VaccineEntry.CONTENT_URI, values);
        }
    }

    /**
     *  editDate used to edit existing data entry
     */
    private void editData(){

    }


    /**
     * Creates a Projection of all the columns of data
     * @param i
     * @param bundle
     * @return CursorLoader of projection
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                VaccineEntry._ID,
                VaccineEntry.COLUMN_VACCINE_DATE,
                VaccineEntry.COLUMN_VACCINE_INFO,
                VaccineEntry.COLUMN_VACCINE_TAG,
                VaccineEntry.COLUMN_VACCINE_REDATE
        };
        return new CursorLoader(this,
                mCurrentVaccineUri,
                projection,
                null,
                null,
                null);
    }

    /**
     * Reads information from data from intent
     * displays it onto the screen
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Move through the right data section
        if (data.moveToFirst()) {
            //Find the index of each item in the rows
            int dateColumnIndex = data.getColumnIndex(VaccineEntry.COLUMN_VACCINE_DATE);
            int vaccineInfoColumnIndex = data.getColumnIndex(VaccineEntry.COLUMN_VACCINE_INFO);
            int tagColumnIndex = data.getColumnIndex(VaccineEntry.COLUMN_VACCINE_TAG);
            int revaccineDateColumnIndex = data.getColumnIndex(VaccineEntry.COLUMN_VACCINE_REDATE);

            //Extract the data
            String date = data.getString(dateColumnIndex);
            String vaccineInfo = data.getString(vaccineInfoColumnIndex);
            String tagNumber = data.getString(tagColumnIndex);
            String redate = data.getString(revaccineDateColumnIndex);

            //Set TextField
            mDateText.setText(date);
            mVaccineInfoText.setText(vaccineInfo);
            mTagNumber.setText(tagNumber);
            mRevaccinatedText.setText(redate);

            //Set TextField to none Editable
            mDateText.setKeyListener(null);
            mVaccineInfoText.setKeyListener(null);
            mTagNumber.setKeyListener(null);
            mRevaccinatedText.setKeyListener(null);

        }
    }

    /**
     * Clears out the loader
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out
        mDateText.setText(" ");
        mVaccineInfoText.setText(" ");
        mTagNumber.setText(" ");
        mRevaccinatedText.setText(" ");
    }
}
