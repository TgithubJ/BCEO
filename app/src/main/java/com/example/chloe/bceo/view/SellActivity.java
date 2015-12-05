package com.example.chloe.bceo.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chloe.bceo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SellActivity extends AppCompatActivity {
    private final static int CAMERA = 66 ;
    private final static int PHOTO = 99 ;
    private Button button;
    private DisplayMetrics mPhone;
    private int imagePos = 0;
    ImageView image_preview;

    private Integer[] imageId = {
            R.id.imageScroll1,
            R.id.imageScroll2,
            R.id.imageScroll3,
            R.id.imageScroll4,
            R.id.imageScroll5,
            R.id.imageScroll6,
            R.id.imageScroll7,
            R.id.imageScroll8,
            R.id.imageScroll9,
            R.id.imageScroll10,
            R.id.imageScroll11,
            R.id.imageScroll12,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        button = (Button) this.findViewById(R.id.button);
        Button buttonCamera = (Button) findViewById(R.id.button_camera);
        Button buttonPhoto = (Button) findViewById(R.id.button_upload);
        image_preview = (ImageView) findViewById(R.id.image_product_preview);


        //Read smartphone resolution
        mPhone = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mPhone);

        //camera button
        buttonCamera.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent_camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent_camera, CAMERA);
            }

        });

        buttonPhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //開啟相簿相片集，須由startActivityForResult且帶入requestCode進行呼叫，原因
//                為點選相片後返回程式呼叫onActivityResult
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PHOTO);
            }
        });

        //Product
        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonClicked(v);
                    }
                }
        );

        //GridView
    }
    public void buttonClicked(View view) {
        // Check values from editTexts
        startActivity(new Intent(view.getContext(), MypageActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == PHOTO && data != null)
        {

            //Get picture uri
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();

            try
            {
                //read bitmap
                //Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                BitmapFactory.Options mOptions = new BitmapFactory.Options();
                //Resize the image to half of size
                mOptions.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,mOptions);

                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if(bitmap.getWidth()>bitmap.getHeight())
                    ScalePic(bitmap, mPhone.heightPixels);
                else ScalePic(bitmap,mPhone.widthPixels);
            }
            catch (FileNotFoundException e)
            {
            }

        } else if (resultCode == RESULT_OK) {
            ImageView iv = (ImageView)findViewById(imageId[imagePos++]);

            //Extract picture from data
            Bundle extras = data.getExtras();
            //Transform data to bitmap
            Bitmap bmp = (Bitmap) extras.get("data");

            //Resize pictures
            int width = bmp.getWidth();
            int height = bmp.getHeight();

            //scale factors
            float scaleWidth = (float) 1.7;
            float scaleHeight = (float) 1.7;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);

            Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix,true);
            iv.setImageBitmap(newbm);

            //Save to SD card
            saveImageSDCard(newbm);

            //set onclick
            iv.setOnClickListener(new imScrollListener(imagePos-1));

        }

        //overwrite activity
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ScalePic(Bitmap bitmap,int phone)
    {
        ImageView mImg = (ImageView)findViewById(imageId[imagePos++]);

        //scaling factor
        float mScale = 1 ;

        //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
        if(bitmap.getWidth() > phone )
        {
            //Calculate scale
            mScale = (float)phone/(float)bitmap.getWidth();

            Matrix mMat = new Matrix() ;
            mMat.setScale(mScale, mScale);

            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap,
                    0,
                    0,
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    mMat,
                    false);
            mImg.setImageBitmap(mScaleBitmap);
        }
        else mImg.setImageBitmap(bitmap);

        //Save image to SD card
        saveImageSDCard(bitmap);

        //set onclick
        mImg.setOnClickListener(new imScrollListener(imagePos-1));
    }

    void saveImageSDCard(Bitmap bmImage){
        String fname = "myFile" + Integer.toString(imagePos-1) + ".PNG";

        String extStorage = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorage, fname);

        try {
            OutputStream outStream = new FileOutputStream(file);
            bmImage.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            Toast.makeText(SellActivity.this,
                    extStorage + "/" + fname,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(SellActivity.this,
                    e.toString(),
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(SellActivity.this,
                    e.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }


    class imScrollListener implements View.OnClickListener{
        int pos;

        public imScrollListener(int pos){
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            loadImageSDCard();
        }

        void loadImageSDCard(){
            String extStorage = Environment.getExternalStorageDirectory().toString();
            String file = new File(extStorage, "myFile"+ Integer.toString(pos) +".PNG").toString();
            Bitmap bm = BitmapFactory.decodeFile(file);
            image_preview.setImageBitmap(bm);

            Toast.makeText(SellActivity.this,
                    extStorage+"/myFile" + Integer.toString(pos) + ".PNG",
                    Toast.LENGTH_LONG).show();
        }
    }
}