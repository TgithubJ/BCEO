package com.example.chloe.bceo.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chloe.bceo.R;
import com.example.chloe.bceo.model.Product;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;
import com.example.chloe.bceo.util.Image64Base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MarketActivity extends AppCompatActivity {
    private static final String OUTPUT_FILE= Environment.getExternalStorageDirectory().getPath() + "/recordoutput.3gpp";
    private MediaPlayer mediaPlayer;
    ArrayList<Product> gridProdList;
    private User user;
    private String prod_id;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        //Get user
        user = (User) getIntent().getSerializableExtra("user");
        Log.d("[MarketPage]", "User " + user.getUserID() + " received!");

        //Get prod_id
        prod_id = (String) getIntent().getSerializableExtra("pro_id");
        Log.d("[MarketPage]", "prod_id: " + prod_id);

        //Create list of product
        gridProdList = new ArrayList<Product>();


        //Get seller id from db
        String jsonSeller = getSellerIDResponce(prod_id);

        //Parse seller_id
        int seller_id = jsonSellerIDParser(jsonSeller);


        //Query from database
        String json = updateMyProdList(seller_id);
        jsonParser(json);

        //Set grid
        final GridView gridview = (GridView) findViewById(R.id.gridview_market);
        gridview.setAdapter(new gridAdapter(this));
        gridview.setOnItemClickListener(new girdClickListener(MarketActivity.this));


        //Recorder
        try {
            playRecording();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public class gridAdapter extends BaseAdapter {

        private Context mContext;

        public gridAdapter(Context c) {
            // TODO Auto-generated constructor stub
            mContext = c;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return gridProdList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return gridProdList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            View grid;

            if(convertView==null){
                grid = new View(mContext);
                LayoutInflater inflater = getLayoutInflater();
                grid=inflater.inflate(R.layout.fragment_grid_item, parent, false);
            }else{
                grid = (View)convertView;
            }

            ImageView imageView = (ImageView)grid.findViewById(R.id.imagepart);
            TextView textView = (TextView)grid.findViewById(R.id.textpart);
            TextView tv_Price = (TextView)grid.findViewById(R.id.text_price);

            Product prod_tmp = gridProdList.get(position);

            String image_id = Integer.toString(prod_tmp.getImageId());

            HTTPGet httpGet = new HTTPGet();
            String urlStr = httpGet.buildURL("images?id=" + image_id);
            String response = httpGet.getResponse(urlStr);
            Log.d("[HTTPGet]", urlStr);
            Log.d("[HTTPGet]", response);

            Bitmap bm = Image64Base.decodeBase64(response);

            //ImageView
            imageView.setImageBitmap(bm);

            //TextView
            textView.setText(prod_tmp.getpName());
            tv_Price.setText(Float.toString(prod_tmp.getpPrice()));

            return grid;
        }

    }

    public class girdClickListener implements AdapterView.OnItemClickListener {
        Context c;

        public girdClickListener(Context c){
            this.c = c;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Dialog: show which item clicked
            Toast.makeText(c, "Postion: " + position + "\nID: " + id, Toast.LENGTH_SHORT).show();

            //Start product activity
            Intent intent = new Intent(view.getContext(), ProductActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("prod", gridProdList.get(position));
            startActivityForResult(intent, 0);
        }
    }

    void jsonParser(String jsonStr){
        String json = "{'abridged_cast':" + jsonStr + "}";
        Log.d("[jsonParser-MyPage]: ", json);

        JSONObject jsonResponse;
        try {
            ArrayList<String> temp = new ArrayList<String>();
            jsonResponse = new JSONObject(json);
            JSONArray products = jsonResponse.getJSONArray("abridged_cast");

            for(int i = 0; i < products.length(); i++){

                JSONObject p = products.getJSONObject(i);
                String category = p.getString("category");

                int id = Integer.parseInt(p.getString("id"));
                String name = p.getString("name");
                float price = Float.parseFloat(p.getString("price"));
                String description = p.getString("description");
                int waitlist = Integer.parseInt(p.getString("waitlist"));
                int image_id = Integer.parseInt(p.getString("image_id"));
                int group_id = Integer.parseInt(p.getString("group_id"));
                String status = p.getString("status");

                Product prod_tmp = new Product(id, name, price, description, waitlist, image_id, group_id, category, status);
                Log.d("[Product] ", prod_tmp.toString());
                gridProdList.add(prod_tmp);
            }
            Toast.makeText(this, "Json: "+temp, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String getSellerIDResponce(String product_id) {
        HTTPGet httpGet = new HTTPGet();
        String urlStr = httpGet.buildURL("seller_info?product_id=" + product_id);
        String response = httpGet.getResponse(urlStr);
        Log.d("[HTTPGet]", urlStr);
        Log.d("[HTTPGet]", response);
        return response;
    }

    int jsonSellerIDParser(String jsonStr){
        JSONObject job = null;
        String seller_id = "";
        try {
            job = new JSONObject(jsonStr);
            seller_id = job.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(seller_id);
    }

    private String updateMyProdList(int seller_id) {
        HTTPGet httpGet = new HTTPGet();
        String urlStr = httpGet.buildURL("my_sell_product?user_id=" + Integer.toString(seller_id));
        String response = httpGet.getResponse(urlStr);
        Log.d("[HTTPGet]", urlStr);
        Log.d("[HTTPGet]", response);
        return response;
    }

    private void playRecording() throws Exception {
        killMediaPlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(OUTPUT_FILE);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void killMediaPlayer()
    {
        if (mediaPlayer != null)
        {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
