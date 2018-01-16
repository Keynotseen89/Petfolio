package com.example.kench.petfolio;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.kench.petfolio.data.MedicationContract.MedicationEntry;

/**
 * Created by Quinatzin on 1/15/2018.
 */

public class MedCursorAdapter extends CursorAdapter {

    public MedCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup){
        return LayoutInflater.from(context).inflate(R.layout.list_item_med, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Find the fields to populate in inflate template for Medical History
        TextView dateMedTextView = (TextView) view.findViewById(R.id.date_med_list);
        TextView medicationTextView = (TextView) view.findViewById(R.id.dossage_med_list);

        //Find the column of data that we're interested in
        int dateMedColumnIndex = cursor.getColumnIndex(MedicationEntry.COLUMN_MED_DATE);
        int medicationColumnIndex = cursor.getColumnIndex(MedicationEntry.COLUMN_MED_MEDICATION);

        String dateMed = cursor.getString(dateMedColumnIndex);
        String medicationInfo = cursor.getString(medicationColumnIndex);

        dateMedTextView.setText(dateMed);
        medicationTextView.setText(medicationInfo);
    }
}
