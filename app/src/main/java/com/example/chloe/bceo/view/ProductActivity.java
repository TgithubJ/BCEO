package com.example.chloe.bceo.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chloe.bceo.R;
import com.example.chloe.bceo.model.Product;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;
import com.example.chloe.bceo.util.Image64Base;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductActivity extends AppCompatActivity {
    private Bitmap imagefile;
    private Bitmap decodedImage;
    private String encodedImage;
    private TextView description;
    private TextView title;
    private TextView waiting;
    private TextView price;
    private TextView seller;

    private String MY_URL_STRING = "http://image10.bizrate-images.com/resize?sq=60&uid=2216744464";
    private ImageView iv;

    private Product prod;
    private User user;
    private boolean visibility = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        user = (User) getIntent().getSerializableExtra("user");
        prod = (Product) getIntent().getSerializableExtra("prod");
        visibility = (boolean) getIntent().getBooleanExtra("visibility", true);

        Log.d("[ProductPage] ", prod.toString());
        Log.d("[ProductPage] ", String.valueOf(visibility));
        Log.d("[ProductPage] ", user.getUserName());

        iv = (ImageView) findViewById(R.id.imageView);
        description = (TextView) findViewById(R.id.textView18);
        title = (TextView) findViewById(R.id.textView15);
        price = (TextView) findViewById(R.id.textView16);
        waiting = (TextView) findViewById(R.id.textView17);
        seller = (TextView) findViewById(R.id.textView14);
        Button buyButton = (Button)findViewById(R.id.button2);
        if (!visibility)
            buyButton.setVisibility(View.INVISIBLE);

        setProductInfo(prod);



        HTTPGet httpGet = new HTTPGet();
        String urlStr = httpGet.buildURL("seller_info?product_id=" + prod.getpID());
        String response = httpGet.getResponse(urlStr);
        JSONObject job = null;
        try {
            job = new JSONObject(response);
            seller.setText(job.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                HTTPGet httpGet = new HTTPGet();
                String urlStr = httpGet.buildURL("buy_product?user_id=" + user.getUserID() + "&product_id=" + prod.getpID());
                httpGet.getResponse(urlStr);

                Intent myIntent = new Intent(v.getContext(), OrderActivity.class);
                myIntent.putExtra("user", user);
                startActivity(myIntent);
            }
        });
    }

    private void setProductInfo(Product prod) {
        title.setText(prod.getpName());
        price.setText(Float.toString(prod.getpPrice()));
        waiting.setText(Integer.toString(prod.getpWaiting()));
        description.setText(prod.getpDescription());

        String image_id = Integer.toString(prod.getImageId());

        HTTPGet httpGet = new HTTPGet();
        String urlStr = httpGet.buildURL("images?id=" + image_id);
        String response = httpGet.getResponse(urlStr);
        Log.d("[HTTPGet]", urlStr);
        Log.d("[HTTPGet]", response);

        Bitmap bm = Image64Base.decodeBase64(response);

        iv.setImageBitmap(bm);
    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }
//
//    public static String encodeTobase64(Bitmap image)
//    {
//        Bitmap immagex=image;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] b = baos.toByteArray();
//        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
//
//        Log.e("LOOK", imageEncoded);
//        return imageEncoded;
//    }
//    public static Bitmap decodeBase64(String input)
//    {
//        byte[] decodedByte = Base64.decode(input, 0);
//        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
//    }

}
