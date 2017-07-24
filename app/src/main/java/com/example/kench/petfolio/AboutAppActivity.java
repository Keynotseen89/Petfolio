package com.example.kench.petfolio;

/**
 * Created by kench on 7/18/2017.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutAppActivity extends AppCompatActivity {

    String outputText = "\n" +
            "This application is used to keep track"
            + "\tof owners Pets information." +
            "\tMedical History, Pet information, \n" +
            "Favorite toys.\n" +
            "\n" +
            "\n" +
            "\tCreated by Quinatzin Sintora.";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        TextView aboutTextView = (TextView)findViewById(R.id.about_app_text);
        aboutTextView.setText(outputText);
    }
}
