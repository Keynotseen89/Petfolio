package com.example.kench.petfolio;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.provider.CalendarContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    private KeyListener dateListener, vaccineListener, tagListener, redateListener;

    private boolean mItemHasChanged = false;

    private boolean editButtonClick = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mItemHasChanged = true;
            return false;
        }
    };

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

        editBackground(true);
        /*(mDateText.getBackground().setAlpha(0);
        mVaccineInfoText.getBackground().setAlpha(0);
        mTagNumber.getBackground().setAlpha(0);
        mRevaccinatedText.getBackground().setAlpha(0);*/
        //Set listener to text
        mDateText.setOnTouchListener(mTouchListener);
        mVaccineInfoText.setOnTouchListener(mTouchListener);
        mTagNumber.setOnTouchListener(mTouchListener);
        mRevaccinatedText.setOnTouchListener(mTouchListener);
        //Listener for input when editor is clicked
        dateListener = mDateText.getKeyListener();
        vaccineListener = mVaccineInfoText.getKeyListener();
        tagListener = mTagNumber.getKeyListener();
        redateListener = mRevaccinatedText.getKeyListener();
    }

    /**
     * used to change EditText view able
     *
     * @param hasChanged
     * @return
     */
    public boolean editBackground(boolean hasChanged) {
        if (hasChanged == true) {
            mDateText.getBackground().setAlpha(0);
            mVaccineInfoText.getBackground().setAlpha(0);
            mTagNumber.getBackground().setAlpha(0);
            mRevaccinatedText.getBackground().setAlpha(0);
        } else {
            mDateText.getBackground().setAlpha(255);
            mVaccineInfoText.getBackground().setAlpha(255);
            mTagNumber.getBackground().setAlpha(255);
            mRevaccinatedText.getBackground().setAlpha(255);
        }
        return true;
    }

    /**
     * used to populate menu with menu_vaccine.xml
     *
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
     *
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
                saveData();
                finish();
                return true;
            case android.R.id.home:
                if (!mItemHasChanged) {
                    NavUtils.navigateUpFromSameTask(VaccineEditorActivity.this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NavUtils.navigateUpFromSameTask(VaccineEditorActivity.this);
                            }
                        };
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is an existing item hide the save button item.
        if (mCurrentVaccineUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
            MenuItem menuItemEdit = menu.findItem(R.id.action_edit);
            menuItemEdit.setVisible(false);
        } else if (editButtonClick == false) {
            MenuItem menuItem = menu.findItem(R.id.action_save);
            menuItem.setVisible(false);
            MenuItem menuDelete = menu.findItem(R.id.action_delete);
            menuDelete.setVisible(false);
        } else {
            MenuItem menuItem = menu.findItem(R.id.action_save);
            menuItem.setVisible(true);
            MenuItem menuDelete = menu.findItem(R.id.action_delete);
            menuDelete.setVisible(true);
            MenuItem menuEdit = menu.findItem(R.id.action_edit);
            menuEdit.setVisible(false);
        }
        return true;
    }

    private void saveData() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String dateString = mDateText.getText().toString().trim();
        String vaccineInforString = mVaccineInfoText.getText().toString().trim();
        String tagNumberString = mTagNumber.getText().toString().trim();
        String redateSTring = mRevaccinatedText.getText().toString().trim();

        // Check if this is supposed to be anew item
        // and check if all the fields in the editor are blank
        if (mCurrentVaccineUri == null &&
                TextUtils.isEmpty(dateString) && TextUtils.isEmpty(vaccineInforString) &&
                TextUtils.isEmpty(tagNumberString) && TextUtils.isEmpty(redateSTring)) {
            return;
        }

        //Create a ContentValue object where column names are the key
        ContentValues values = new ContentValues();
        values.put(VaccineEntry.COLUMN_VACCINE_DATE, dateString);
        values.put(VaccineEntry.COLUMN_VACCINE_INFO, vaccineInforString);

        String tagValue = "N/A";
        if (!TextUtils.isEmpty(tagNumberString)) {
            tagValue = tagNumberString;
        }
        values.put(VaccineEntry.COLUMN_VACCINE_TAG, tagValue);
        values.put(VaccineEntry.COLUMN_VACCINE_REDATE, redateSTring);

        // Determine if this a new item by checking mCurrentVaccineUri
        if (mCurrentVaccineUri == null) {
            Uri newUri = getContentResolver().insert(VaccineEntry.CONTENT_URI, values);

            //Show a Toast message depending on whether or not the insertion failed
            if (newUri == null) {
                Toast.makeText(this, "Insertion Failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Insertion successed",
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentVaccineUri, values, null, null);

            //Show a toast message depending whether or not the update was successful
            if (rowsAffected == 0) {
                Toast.makeText(this, "Update failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update successed",
                        Toast.LENGTH_SHORT).show();
            }

        }
        editBackground(true);
    }

    /**
     * editDate used to edit existing data entry
     */
    private void editData() {
        editButtonClick = true;
        editBackground(false);

        mDateText.setKeyListener(dateListener);
        mVaccineInfoText.setKeyListener(vaccineListener);
        mTagNumber.setKeyListener(tagListener);
        mRevaccinatedText.setKeyListener(redateListener);
    }

    @Override
    public void onBackPressed() {
        // If the Values haven't changed, continue with handling back button press
        if (!mItemHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                };
        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    //Discard changes dialog method
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        //Create an AlertDialog.Builder and set the message, and click listeners
        // for the position and nagative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Unsaved Changes");
        builder.setPositiveButton("Discard", discardButtonClickListener);
        builder.setNegativeButton("Keep editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        //Create and show AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        // If this is an existing item hide the save button item.
        if(mCurrentVaccineUri != null){
            MenuItem menuItem = menu.findItem(R.id.action_save);
            menuItem.setVisible(false);
        }
        return true;
    }*/

    /**
     * Creates a Projection of all the columns of data
     *
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
     *
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
     *
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
