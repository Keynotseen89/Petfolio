package com.example.kench.petfolio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.widget.ImageView.ScaleType.CENTER;
import static android.widget.ImageView.ScaleType.FIT_CENTER;
import static android.widget.ImageView.ScaleType.FIT_START;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;        //used for image
    Uri imageUri;               //used to receive image as URI
    Bitmap imageBit;
    Bitmap bitmap2;
    //InputStream inputStream;
    ByteArrayOutputStream bytearrayoutputstream;
    byte[] BYTE;
    private static final int PICK_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // imageView = (ImageView) findViewById(R.id.pet_image_id);
        imageView = (ImageView) findViewById(R.id.pet_image_id);

        Button editButton = (Button) findViewById(R.id.edit_id);



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }


        });

    }//end of onCreate code

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE  && data != null) {


            imageUri = data.getData();

          //  Bitmap bitmap = BitmapFactory.decodeFile("data");


            //imageView.setImageBitmap(BitmapFactory.decodeFile("data"));
            //imageView.setScaleType(FIT_CENTER);
            imageView.setImageURI(imageUri);

//
//            Bundle extras = data.getExtras();
//            imageBit = extras.getParcelable("data");
//            imageView.setImageBitmap(imageBit);




        }
    }


}
