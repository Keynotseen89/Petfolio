package com.example.kench.petfolio;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.kench.petfolio.data.VaccineContract;
import com.example.kench.petfolio.data.VaccineContract.VaccineEntry;

/**
 * Created by Quinatzin on 1/9/2018.
 */

public class VaccineCursorAdapter extends CursorAdapter {

    public VaccineCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0 /* flags */);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Find the fields to populate in inflate template
        TextView dateTextView = (TextView) view.findViewById(R.id.date_id);
        TextView vaccTextView = (TextView) view.findViewById(R.id.vaccine_info_id);


        //Find the columns of data that we're interested in
        int dateColumnIndex = cursor.getColumnIndex(VaccineEntry.COLUMN_VACCINE_DATE);
        int vacColumnIndex = cursor.getColumnIndex(VaccineEntry.COLUMN_VACCINE_INFO);

        //Read the inventory attribute from the cursor for the current inventory
        String date = cursor.getString(dateColumnIndex);
        String vaccineInfo = cursor.getString(vacColumnIndex);

        dateTextView.setText(date);
        vaccTextView.setText(vaccineInfo);

    }

}
