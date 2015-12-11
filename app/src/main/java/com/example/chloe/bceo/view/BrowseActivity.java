package com.example.chloe.bceo.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chloe.bceo.Adapter.ProductAdapter;
import com.example.chloe.bceo.R;
import com.example.chloe.bceo.fragment.FragmentBottomMenu;
import com.example.chloe.bceo.model.Product;
import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.util.HTTPGet;
import com.example.chloe.bceo.util.Image64Base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class BrowseActivity extends AppCompatActivity {

    ArrayList<Product> productList;
//    ArrayList<Product> productList = new ArrayList<Product>();
    ArrayList<Product> gridProdList = new ArrayList<Product>();
    ProductAdapter productAdapter;


    Spinner category;
    Button button_filter;
    String filter_category = "all";
    private User user;

    // references to our images
//    private Integer[] mThumbIds = {
//            R.drawable.androider_01,
//            R.drawable.androider_02,
//            R.drawable.androider_03,
//            R.drawable.androider_04,
//            R.drawable.androider_05,
//            R.drawable.androider_06,
//            R.drawable.androider_07,
//            R.drawable.androider_08,
//            R.drawable.androider_09,
//    };

    public class MyAdapter extends BaseAdapter {

        private Context mContext;

        public MyAdapter(Context c) {
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

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        user = (User) getIntent().getSerializableExtra("user");

        FragmentBottomMenu.setUser(user);

        productAdapter = new ProductAdapter();
        productList = productAdapter.getProductList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new MyAdapter(this));
        gridview.setOnItemClickListener(new ItemClickListener(BrowseActivity.this));

        //Get json
        HTTPGet httpGet = new HTTPGet();
        String urlStr = httpGet.buildURL("products?group_id=" + user.getGroupID());
        String jsonString = httpGet.getResponse(urlStr);
        Log.d("[Browse Page] -> URL: ", urlStr);
        Log.d("[Browse Page] -> Json: ", jsonString);

        //Parse json and get products
        jsonParser(jsonString);

        updateGridProduct("all");

        //Spinner to filter items
        category = (Spinner) findViewById(R.id.spinnerCategory);
        button_filter = (Button) findViewById(R.id.button_filter);
        button_filter.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        String text = category.getSelectedItem().toString().toLowerCase();
                        Log.d("[Spinner]", text);

                        updateGridProduct(text);
                        gridview.invalidateViews();
                    }
                });


    }

    private void updateGridProduct(String text) {
        gridProdList.clear();

        for (int i = 0; i< productList.size(); i++){
            Product p = productList.get(i);
            if (text.equals("all") || p.getCategory().equals(text))
                gridProdList.add(p);
        }
    }

    public class ItemClickListener implements AdapterView.OnItemClickListener {
        Context c;

        public ItemClickListener(Context c){
            this.c = c;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Dialog: show which item clicked
            Toast.makeText(c, "Postion: "+ position + "\nID: " + id, Toast.LENGTH_SHORT).show();

            //Start product activity
            Intent intent = new Intent(view.getContext(), ProductActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("prod", productList.get(position));
            startActivityForResult(intent, 0);

        }
    }

    void jsonParser(String jsonStr){
        String json = "{'abridged_cast':" + jsonStr + "}";
        Log.d("[jsonParser]: ", json);

        JSONObject jsonResponse;
        try {
            ArrayList<String> temp = new ArrayList<String>();
            jsonResponse = new JSONObject(json);
            JSONArray products = jsonResponse.getJSONArray("abridged_cast");

            for(int i = 0; i < products.length(); i++){

                JSONObject p = products.getJSONObject(i);
                String category = p.getString("category");

//                if (category.equals("electronics")) {
////                if (filter_category.equals("ALL") || category.equals(filter_category)) {

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
                    productAdapter.addProduct(prod_tmp);

//                    Log.d("[Product] ", prod_tmp.toString());

//                }
            }
            Toast.makeText(this, "Json: "+temp, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        String json="{'abridged_cast':[{'name':'JeffBridges','id':'162655890','characters':['JackPrescott']},{'name':'CharlesGrodin','id':'162662571','characters':['FredWilson']},{'name':'JessicaLange','id':'162653068','characters':['Dwan']},{'name':'JohnRandolph','id':'162691889','characters':['Capt.Ross']},{'name':'ReneAuberjonois','id':'162718328','characters':['Bagley']}]}";
//        Log.d("[Browse Page]->JsonTest: ", json);
//
//        JSONObject jsonResponse;
//        try {
//            ArrayList<String> temp = new ArrayList<String>();
//            jsonResponse = new JSONObject(json);
//            JSONArray movies = jsonResponse.getJSONArray("abridged_cast");
//            for(int i=0;i<movies.length();i++){
//                JSONObject movie = movies.getJSONObject(i);
//                JSONArray characters = movie.getJSONArray("characters");
//                for(int j=0;j<characters.length();j++){
//                    temp.add(characters.getString(j));
//                    Log.d("[Browse Page]->cha: ", characters.getString(j));
//                }
//            }
//            Toast.makeText(this, "Json: "+temp, Toast.LENGTH_LONG).show();
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}

