package com.example.chloe.bceo.view;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chloe.bceo.R;
import com.example.chloe.bceo.fragment.FragmentBottomMenu;
import com.example.chloe.bceo.model.Product;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;
import com.example.chloe.bceo.util.HTTPPost;
import com.example.chloe.bceo.util.HTTPPut;
import com.example.chloe.bceo.util.Image64Base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SellActivity extends AppCompatActivity {
    private final static int CAMERA = 66 ;
    private final static int PHOTO = 99 ;
    private Button buttonComfirm;
    private DisplayMetrics mPhone;
    private int imagePos = 0;
    ImageView image_preview;

    public User user;
    public Product p;
    String cmd;

    private EditText et_name;
    private EditText et_price;
    private EditText et_description;
    private Spinner spinner_category;
    private Spinner spinner_status;

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

        buttonComfirm = (Button) this.findViewById(R.id.button);
        Button buttonCamera = (Button) findViewById(R.id.button_camera);
        Button buttonPhoto = (Button) findViewById(R.id.button_upload);

        image_preview = (ImageView) findViewById(R.id.image_product_preview);

        et_name = (EditText) findViewById(R.id.editText_name);;
        et_price = (EditText) findViewById(R.id.editText_price);
        et_description = (EditText) findViewById(R.id.editText_description);
        spinner_category = (Spinner) findViewById(R.id.spinnerCategory);
        spinner_status = (Spinner) findViewById(R.id.spinnerStatus);

        //Get user and command
        user = FragmentBottomMenu.getUser();
        cmd = (String) getIntent().getSerializableExtra("cmd");

        //If for update purpose, get a product
        if (cmd.equals("update")){
            p = (Product) getIntent().getSerializableExtra("prod");
            Log.d("[SellPage]", "Product received: id = " + p.getpName() );

            fillDefaultTextAndImage(p);
        }


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
//
//        StrictMode.ThreadPolicy policy =
//                new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        //Product
        buttonComfirm.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        //Save to local db
//                        DatabaseConnector databaseConnector = new DatabaseConnector(this);
//                        Create databaseCreator = new Create();
//                        User testUser = new User();
//                        testUser.setUserID("tj@gmail.com");
//                        testUser.setGroupID("taejin@andrew.cmu.edu");
//                        testUser.setPhoneNum("213-905-0929");
//                        testUser.setPriority(1);
//                        testUser.setUserName("Taejin Chun");
//                        Product testProduct = new Product();
//                        testProduct.setpDescription("This is a test item.");
//                        testProduct.setpName("testItem");
//                        testProduct.setpPrice(23);
//                        testProduct.setpID(1);
//                        testProduct.setpWaiting(2);
//                        Bitmap imagefile = BitmapFactory.decodeResource(this.getResources(), R.drawable.androider_01);
//                        testProduct.setpImage(encodeTobase64(imagefile));
//                        //Drawable testP = testPicture.getDrawable();
//                        databaseCreator.createProduct(testUser, testProduct, databaseConnector);


//                        //To save bitmap to server
//                        image_preview.setImageResource(R.drawable.androider_03);

                        if (cmd.equals("null")){
                            BitmapDrawable bd = (BitmapDrawable) image_preview.getDrawable();
                            int image_id = saveImageOnServerSide(bd.getBitmap());

                            String p_name = et_name.getText().toString();

                            float p_price = Float.parseFloat(et_price.getText().toString());

                            //Upload product details
                            int product_id = uploadProductOnServerSide(7, p_name, (float) p_price, "Have fun!", 0, image_id, 1, "electronics");

                            Toast.makeText(SellActivity.this, "Upload Product Successfully!", Toast.LENGTH_LONG).show();
                        }else{
                            HTTPPut httpPut = new HTTPPut(p);
                            httpPut.execute(p.getpID());

                            Toast.makeText(SellActivity.this, "Update Product Successfully!", Toast.LENGTH_LONG).show();
                        }
//                        //Get image from server
//                        HTTPGet httpGet = new HTTPGet();
//                        String urlStr = httpGet.buildURL("images?id=15");
//                        String response = httpGet.getResponse(urlStr);
//                        Log.d("[HTTPGet]", urlStr);
//                        Log.d("[HTTPGet]", response);

//                        httpGet.getResponseBackground();
//                        image_preview.setImageBitmap(httpGet.bm);

                        //Set imageView
//                        Bitmap bm = Image64Base.decodeBase64(response);
//                        image_preview.setImageBitmap(bm);



                        startActivity(new Intent(v.getContext(), MypageActivity.class));
                    }
                }
        );


    }

    private void fillDefaultTextAndImage(Product p) {
//        BitmapDrawable bd = (BitmapDrawable) image_preview.getDrawable();
//        int image_id = saveImageOnServerSide(bd.getBitmap());

        //Set name
        String p_name = p.getpName();
        et_name.setText(p_name, TextView.BufferType.EDITABLE);

        //Set price
        String p_price = Float.toString(p.getpPrice());
        et_price.setText(p_price, TextView.BufferType.EDITABLE);

        //Set description
        String p_description = p.getpDescription();
        et_description.setText(p_description, TextView.BufferType.EDITABLE);

        //Set spinner
        spinner_status.setSelection(1);
        spinner_category.setSelection(2);

        //Set image
        String image_id = Integer.toString(p.getImageId());
        HTTPGet httpGet = new HTTPGet();
        String urlStr = httpGet.buildURL("images?id=" + image_id);
        String response = httpGet.getResponse(urlStr);
        Log.d("[HTTPGet]", urlStr);
        Log.d("[HTTPGet]", response);

        Bitmap bm = Image64Base.decodeBase64(response);

        //ImageView
        image_preview.setImageBitmap(bm);

    }

    public int uploadProductOnServerSide(int user_id, String pName, float pPrice, String pDescription, int pWaiting, int imageId, int groupId, String category) {
        HTTPPost httpPost = new HTTPPost();
        httpPost.uploadProduct(user_id, pName, pPrice, pDescription, pWaiting, imageId, groupId, category);
        return httpPost.getImage_ID();
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
        mImg.setOnClickListener(new imScrollListener(imagePos - 1));
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

    int saveImageOnServerSide(Bitmap bm) {

        if (bm == null) {
            Toast.makeText(SellActivity.this, "Bitmap not received", Toast.LENGTH_LONG).show();
            return -1;
        }

        String str64Base = Image64Base.encodeTobase64(bm);

        Log.d("[64Base]", str64Base);

        HTTPPost post = new HTTPPost();
        post.executeImageUpload(str64Base);

        while(post.getImage_ID() == 0){}

        return post.getImage_ID();

//        Toast.makeText(SellActivity.this, str64Base, Toast.LENGTH_LONG).show();
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