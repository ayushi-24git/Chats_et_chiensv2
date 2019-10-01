package com.example.dogcat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.net.DatagramPacket;


public class MainActivity extends Activity  {
    public Button uploadbut;
    public Button takepicbut;
    public ImageView image;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGE = 100;
    Uri imageuriupload;
    Uri imageuritake;
    Bitmap bitmap = null;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      
        setContentView(R.layout.activity_main);
       uploadbut = (Button)findViewById(R.id.uploadbutton);
       takepicbut = (Button)findViewById(R.id.mazebutton);
       image = findViewById(R.id.imageshow);
        uploadbut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent gallery = new Intent();
               gallery.setType("image/*");
               gallery.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(gallery,"select pic"), PICK_IMAGE);
               System.out.println("kefj1");

           }});
        
       takepicbut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent gallery = new Intent();
               gallery.setType("image/*");
               gallery.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(gallery,"take a pic"), REQUEST_IMAGE_CAPTURE);
               System.out.println("kefj2");

           }});

    }
   

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageuriupload = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuriupload);
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //image.setImageURI(imageuri);
        }
        switch(requestCode) {
            case PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    DatagramPacket imageReturnedIntent = null;
                    byte[] selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    bitmap = BitmapFactory.decodeFile(filePath);

                    //Now do whatever processing you want to do on it.
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            imageuritake = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuritake);
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //image.setImageURI(imageuri);
        }

    }}
    /*public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }
    public void capture_btn(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }*/





