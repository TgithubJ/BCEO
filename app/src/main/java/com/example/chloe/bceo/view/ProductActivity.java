package com.example.chloe.bceo.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chloe.bceo.DBLayout.DatabaseConnector;
import com.example.chloe.bceo.DBLayout.Read;
import com.example.chloe.bceo.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ProductActivity extends AppCompatActivity {
    private Bitmap imagefile;
    private Bitmap decodedImage;
    private String encodedImage;
    private TextView description;

    private String MY_URL_STRING = "http://image10.bizrate-images.com/resize?sq=60&uid=2216744464";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        iv = (ImageView) findViewById(R.id.imageView);
        description = (TextView) findViewById(R.id.textView18);
        TextView title = (TextView) findViewById(R.id.textView15);
        TextView price = (TextView) findViewById(R.id.textView16);
        TextView waiting = (TextView) findViewById(R.id.textView17);
        TextView seller = (TextView) findViewById(R.id.textView14);
        Button buyButton = (Button)findViewById(R.id.button2);
//        imagefile = BitmapFactory.decodeResource(this.getResources(), R.drawable.androider_01);
//        encodedImage = encodeTobase64(imagefile);
//        decodedImage = decodeBase64(encodedImage);
//        iv.setImageBitmap(decodedImage);
//        description.setText(encodedImage);
//        new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(MY_URL_STRING);

        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        Read databaseReader = new Read();
        Cursor cursor = databaseReader.getOneProduct(1, databaseConnector);
        cursor.moveToFirst();
        title.setText(cursor.getString(1));
        price.setText(Float.toString(cursor.getFloat(2)));
        waiting.setText(Integer.toString(cursor.getInt(4)));
        description.setText(cursor.getString(3));
        decodedImage = decodeBase64(cursor.getString(5));
        iv.setImageBitmap(decodedImage);

        seller.setTypeface(null, Typeface.BOLD_ITALIC);
        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MarketActivity.class);
                startActivity(myIntent);
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), OrderActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
