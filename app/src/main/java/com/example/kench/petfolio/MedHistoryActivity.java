package com.example.kench.petfolio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MedHistoryActivity extends AppCompatActivity {

    //Button is used to edit Medical history of petfolio
    //final Button editButton = (Button) findViewById(R.id.button_edit_id);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_history);

        //Button is used to edit Medical history of petfolio
        // Button editButton = (Button) findViewById(R.id.button_edit_id);
        final Button editButton = (Button) findViewById(R.id.button_edit_id);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                editMedHistory();


            }
        });//end of editButton


    }//end of onCreate code

    private void editMedHistory(){


        //This is used to switch from text to edit text
        TextSwitcher switcherDateAdm = (TextSwitcher) findViewById(R.id.switch_date_adm);
        //Switches to next text
        switcherDateAdm.showNext();

        TextView dateAdmText = (TextView) switcherDateAdm.findViewById(R.id.date_adm_text);
        EditText editAdmDate = (EditText) switcherDateAdm.findViewById(R.id.edit_date_adm);
        dateAdmText.setText(editAdmDate.getEditableText());

        //Swtiches to Vac text edit
        TextSwitcher switcherVac = (TextSwitcher) findViewById(R.id.switch_vac_info);
        switcherVac.showNext();
        TextView vacInfo = (TextView) switcherVac.findViewById(R.id.vac_info_text);
        EditText editVacInfo = (EditText) switcherVac.findViewById(R.id.edit_vac_info);
        vacInfo.setText(editVacInfo.getText());


        //Switches to Tag editor
        TextSwitcher switcherTag = (TextSwitcher) findViewById(R.id.switch_tag);
        switcherTag.showNext();
        TextView tagNumber = (TextView) switcherTag.findViewById(R.id.tag_text);
        EditText editTagNumber = (EditText) switcherTag.findViewById(R.id.edit_tag);
        tagNumber.setText(editTagNumber.getText());

        //Switcher to Revaccination Date edit
        TextSwitcher switcherReVac = (TextSwitcher) findViewById(R.id.switch_date_re);
        switcherReVac.showNext();
        TextView reVacDate = (TextView) switcherReVac.findViewById(R.id.date_vac_text);
        EditText editReVacDate = (EditText) switcherReVac.findViewById(R.id.edit_date_re);
        reVacDate.setText(editReVacDate.getText());

        //Switcher for medication Date
        TextSwitcher switcherDateMed = (TextSwitcher) findViewById(R.id.switch_date_med);
        switcherDateMed.showNext();
        TextView dateMed = (TextView) switcherDateMed.findViewById(R.id.med_date_text);
        EditText editDateMed = (EditText) switcherDateMed.findViewById(R.id.edit_med_date);
        dateMed.setText(editDateMed.getText());

        //Switcher for type of Medication given to pet
        TextSwitcher switcherMedMed = (TextSwitcher) findViewById(R.id.switch_med_med);
        switcherMedMed.showNext();
        TextView medTextView = (TextView) switcherMedMed.findViewById(R.id.medication_text);
        EditText editMedText = (EditText) switcherMedMed.findViewById(R.id.edit_med_text);
        medTextView.setText(editMedText.getText());

        //Switcher for amount of doesage for Medication given to pet
        TextSwitcher switcherDosMed = (TextSwitcher) findViewById(R.id.switch_dos_med);
        switcherDosMed.showNext();
        TextView doeMedTextView = (TextView) switcherDosMed.findViewById(R.id.dos_instruction_text);
        EditText editDoeMedText = (EditText) switcherDosMed.findViewById(R.id.edit_dos_instruction);
        doeMedTextView.setText(editDoeMedText.getText());
        //TextSwithcer

        TextSwitcher textSwitcher = (TextSwitcher) findViewById(R.id.text_switcher_id);
        textSwitcher.showNext();
        TextView textIllness = (TextView) textSwitcher.findViewById(R.id.illness_text);
        EditText editIllnessText = (EditText) textSwitcher.findViewById(R.id.edit_illness_text);
        textIllness.setText(editIllnessText.getText());


    }//end of editMedHistory code
}
