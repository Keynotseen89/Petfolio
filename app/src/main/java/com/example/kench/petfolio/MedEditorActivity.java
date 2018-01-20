package com.example.kench.petfolio;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kench.petfolio.data.VaccineContract;

/**
 * Created by kench on 1/13/2018.
 */

public class MedEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_MED_LOADER = 1;

    private EditText mDateMedText;
    private EditText mMedicationText;
    private EditText mDossageText;

    //Content URI for the existing inventory item
    private Uri mCurrentMedUri;

    private KeyListener mDateListener, mMedListener, mDossageListener;

    private boolean mItemHasChanged = false;

    private boolean editMedButtonClick = false;

    private View.OnTouchListener mMedTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mItemHasChanged = true;
            return false;
        }
    };

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

        editBackground(true);

        mDateMedText.setOnTouchListener(mMedTouchListener);
        mMedicationText.setOnTouchListener(mMedTouchListener);
        mDossageText.setOnTouchListener(mMedTouchListener);

        mDateListener = mDateMedText.getKeyListener();
        mMedListener = mMedicationText.getKeyListener();
        mDossageListener = mDossageText.getKeyListener();
    }

    private void editBackground(boolean valueChanged) {
        if (valueChanged == true) {
            mDateMedText.getBackground().setAlpha(0);
            mMedicationText.getBackground().setAlpha(0);
            mDossageText.getBackground().setAlpha(0);
        } else {
            mDateMedText.getBackground().setAlpha(255);
            mMedicationText.getBackground().setAlpha(255);
            mDossageText.getBackground().setAlpha(255);
        }
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu options from the res/menu/menu_menu.xml item
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                editData();
                return true;
            case R.id.action_save:
                saveData();
                finish();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case R.id.home:
                if (!mItemHasChanged) {
                    NavUtils.navigateUpFromSameTask(MedEditorActivity.this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NavUtils.navigateUpFromSameTask(MedEditorActivity.this);
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
        if (mCurrentMedUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
            MenuItem menuItemEdit = menu.findItem(R.id.action_edit);
            menuItemEdit.setVisible(false);
        } else if (editMedButtonClick == false) {
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

    private void editData() {
        setTitle("Editing");
        editMedButtonClick = true;
        editBackground(false);

        mDateMedText.setKeyListener(mDateListener);
        mMedicationText.setKeyListener(mMedListener);
        mDossageText.setKeyListener(mDossageListener);

    }

    private void saveData() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String dateString = mDateMedText.getText().toString().trim();
        String medInforString = mMedicationText.getText().toString().trim();
        String dossageString = mDossageText.getText().toString().trim();

        // Check if this is supposed to be anew item
        // and check if all the fields in the editor are blank
        if (mCurrentMedUri == null &&
                TextUtils.isEmpty(dateString) && TextUtils.isEmpty(medInforString) &&
                TextUtils.isEmpty(dossageString)) {
            return;
        }

        //Create a ContentValue object where column names are the key
        ContentValues values = new ContentValues();
        values.put(VaccineContract.VaccineEntry.COLUMN_MED_DATE, dateString);
        values.put(VaccineContract.VaccineEntry.COLUMN_MED_MEDICATION, medInforString);
        values.put(VaccineContract.VaccineEntry.COLUMN_MED_DOSAGE, dossageString);

        // Determine if this a new item by checking mCurrentVaccineUri
        if (mCurrentMedUri == null) {
            Uri newUri = getContentResolver().insert(VaccineContract.VaccineEntry.MED_CONTENT_URI, values);

            //Show a Toast message depending on whether or not the insertion failed
            if (newUri == null) {
                Toast.makeText(this, getText(R.string.saved_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getText(R.string.save_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentMedUri, values, null, null);

            //Show a toast message depending whether or not the update was successful
            if (rowsAffected == 0) {
                Toast.makeText(this, getText(R.string.update_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getText(R.string.update_successful),
                        Toast.LENGTH_SHORT).show();
            }

        }
        editBackground(true);
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

    /**
     * Prompt the user to confirm that they want to delete this data.
     */
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        //Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the delete of the data
     */
    private void deleteData() {
        if (mCurrentMedUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentMedUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, getText(R.string.delete_failed),
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getText(R.string.delete_successful),
                        Toast.LENGTH_LONG).show();
            }
        }
        //Close the activity
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                VaccineContract.VaccineEntry.MED_ID,
                VaccineContract.VaccineEntry.COLUMN_MED_DATE,
                VaccineContract.VaccineEntry.COLUMN_MED_MEDICATION,
                VaccineContract.VaccineEntry.COLUMN_MED_DOSAGE
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
            int dateColumnIndex = data.getColumnIndex(VaccineContract.VaccineEntry.COLUMN_MED_DATE);
            int medicationColumnIndex = data.getColumnIndex(VaccineContract.VaccineEntry.COLUMN_MED_MEDICATION);
            int dossageColumnIndex = data.getColumnIndex(VaccineContract.VaccineEntry.COLUMN_MED_DOSAGE);

            //Extract the data
            String date = data.getString(dateColumnIndex);
            String medicationInfo = data.getString(medicationColumnIndex);
            String dossage = data.getString(dossageColumnIndex);

            //Set TextField
            mDateMedText.setText(date);
            mMedicationText.setText(medicationInfo);
            mDossageText.setText(dossage);

            //Set TextField to none Editable
            mDateMedText.setKeyListener(null);
            mMedicationText.setKeyListener(null);
            mDossageText.setKeyListener(null);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDateMedText.setText("");
        mMedicationText.setText("");
        mDossageText.setText("");
    }
}
