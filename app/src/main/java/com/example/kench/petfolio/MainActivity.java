package com.example.kench.petfolio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;        //used for image
    Uri imageUri;               //used to receive image as URI
    Bitmap imageBit;
    Bitmap bitmap2;
    int imageHeight;
    int imageWidth;
    String imageType;
    FrameLayout frameLayout;
    ByteArrayOutputStream bytearrayoutputstream;
    byte[] BYTE;
    private static final int PICK_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.pet_image_id);
        frameLayout = (FrameLayout) findViewById(R.id.pet_frame_id);

    }//end of onCreate code

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicks on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_edit:
                editPetName();
                return true;
            case R.id.action_save:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Edit the name of owners Pet
    private void editPetName() {


        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        switcher.showNext();
        TextView petNameView = (TextView) switcher.findViewById(R.id.pet_name_id);
        EditText myEditText = (EditText) switcher.findViewById(R.id.hidden_edit_view);
        petNameView.setText(myEditText.getText());

        frameLayout.setBackgroundColor(Color.CYAN);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallery();
            }
        });

    }//end of editPetName code

    private void openGallery() {
        //used to access gallery from phone through external_content

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/*");                         //("image/*") means select any type of image file
//        gallary.putExtra("crop", "true");


//        gallery.putExtra("crop", true);
//        gallery.putExtra("aspectX", 0);
//        gallery.putExtra("aspectY", 0);
//        gallery.putExtra("outputY", 200);
//        gallery.putExtra("outputX", 200);
//        gallery.putExtra("return-data", true);

        startActivityForResult(gallery, PICK_IMAGE);
    }//end of openGallery code


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null) {


            imageUri = data.getData();
            //  Bitmap bitmap = BitmapFactory.decodeFile("data");
            //imageView.setImageBitmap(BitmapFactory.decodeFile("data"));
            //imageView.setScaleType(FIT_CENTER);

            imageView.setImageURI(imageUri);

            frameLayout.setBackgroundColor(Color.TRANSPARENT);

//
//            Bundle extras = data.getExtras();
//            imageBit = extras.getParcelable("data");
//            imageView.setImageBitmap(imageBit);
        }
    }//end of ActivityResult code

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file.
        // This adds menu item to the app bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Opens MedHistoryActivity window in UI
    public void openMedHistory(View view) {
        //Intent intent = new Intent(this, MedHistoryActivity.class);
        Intent intent = new Intent(this, MainMedLog.class);
        startActivity(intent);
    }//end of medHistory code

    //Opend PetInfoActivity window in UI
    public void openPetInfo(View view) {
        Intent openPetInfo = new Intent(this, PetInfoActivity.class);
        startActivity(openPetInfo);
    }

    public void openAboutApp(View view) {
        Intent openAboutApp = new Intent(this, AboutAppActivity.class);
        startActivity(openAboutApp);
    }

}//End of code
