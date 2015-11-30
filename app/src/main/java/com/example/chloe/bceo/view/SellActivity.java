package com.example.chloe.bceo.view;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.chloe.bceo.R;

import java.io.FileNotFoundException;

public class SellActivity extends AppCompatActivity {
    private final static int CAMERA = 66 ;
    private final static int PHOTO = 99 ;
    private Button button;
    private DisplayMetrics mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        button = (Button) this.findViewById(R.id.button);
        Button buttonCamera = (Button) findViewById(R.id.button_camera);
        Button buttonPhoto = (Button) findViewById(R.id.button_upload);

        //Read smartphone resolution
        mPhone = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mPhone);

        //camera button
        buttonCamera.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
//                Intent intent_camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent_camera, CAMERA);

                //開啟相機功能，並將拍照後的圖片存入SD卡相片集內，須由startActivityForResult且
//                帶入
//                requestCode進行呼叫，原因為拍照完畢後返回程式後則呼叫onActivityResult
                ContentValues value = new ContentValues();
                value.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        value);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri.getPath());
                startActivityForResult(intent, CAMERA);
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
    }
    public void buttonClicked(View view) {
        // Check values from editTexts
        startActivity(new Intent(view.getContext(), MypageActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == CAMERA || requestCode == PHOTO ) && data != null)
        {
            //Get photo uri
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();

            try
            {
                BitmapFactory.Options mOptions = new BitmapFactory.Options();
                //set size to half
                mOptions.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,mOptions);

                //Determine if the photo is horizontal or vertical.
                if(bitmap.getWidth()>bitmap.getHeight())ScalePic(bitmap,
                        mPhone.heightPixels);
                else ScalePic(bitmap,mPhone.widthPixels);
            }
            catch (FileNotFoundException e)
            {
            }
        }
//
//        if (resultCode == CAMERA && data != null)
//        {
//            ImageView iv = (ImageView)findViewById(R.id.imagecaptured);
//
//            //Extract picture from data
//            Bundle extras = data.getExtras();
//            //Transform data to bitmap
//            Bitmap bmp = (Bitmap) extras.get("data");
//
//            //Resize pictures
//            int width = bmp.getWidth();
//            int height = bmp.getHeight();
//
//            //scale factors
//            float scaleWidth = (float) 1.7;
//            float scaleHeight = (float) 1.7;
//
//            Matrix matrix = new Matrix();
//            matrix.postScale(scaleWidth, scaleHeight);
//
//            Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix,true);
//            iv.setImageBitmap(newbm);
//        }


        //overwrite activity
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ScalePic(Bitmap bitmap,int phone)
    {
        ImageView mImg = (ImageView)findViewById(R.id.imagecaptured);

        //Scale factor
        float mScale = 1 ;

        //If larger then the screen width, scale the pic
        if(bitmap.getWidth() > phone )
        {
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
    }
}
